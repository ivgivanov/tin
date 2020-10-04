package info.ivgivanov.tin.service.action;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.vmware.vim25.ArrayOfCheckResult;
import com.vmware.vim25.ArrayOfManagedObjectReference;
import com.vmware.vim25.CheckResult;
import com.vmware.vim25.DynamicProperty;
import com.vmware.vim25.HostRuntimeInfo;
import com.vmware.vim25.InvalidPropertyFaultMsg;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.ObjectContent;
import com.vmware.vim25.ObjectSpec;
import com.vmware.vim25.PropertyFilterSpec;
import com.vmware.vim25.PropertySpec;
import com.vmware.vim25.RetrieveOptions;
import com.vmware.vim25.RetrieveResult;
import com.vmware.vim25.RuntimeFaultFaultMsg;
import com.vmware.vim25.TaskInfo;

import info.ivgivanov.tin.controller.VmotionCheckPrerunException;
import info.ivgivanov.tin.model.VcConnection;
import info.ivgivanov.tin.model.dto.CheckResultDto;
import info.ivgivanov.tin.model.dto.CheckResultListDto;
import info.ivgivanov.tin.model.dto.ClusterReferenceDto;

@Service
public class VmotionCompatibilityChecker {
	
	public CheckResultListDto checkVmotionCompatibility(VcConnection vcConnection, ClusterReferenceDto cluster) {
		
		List<ManagedObjectReference> vmList = new ArrayList<ManagedObjectReference>();
		List<ManagedObjectReference> hostList = new ArrayList<ManagedObjectReference>();
		 
		CheckResultListDto vmotionTestResult = new CheckResultListDto();
		
		RetrieveResult clusterResult = collectFromVc(vcConnection, cluster.getMoRef(), false, Arrays.asList("host"));
		
		if (clusterResult != null) {
			
			List<ManagedObjectReference> hostListTemp = new ArrayList<ManagedObjectReference>();
			
			for (ObjectContent objectContent : clusterResult.getObjects()) {
				List<DynamicProperty> properties = objectContent.getPropSet();
				
				ArrayOfManagedObjectReference hostsMorefs = (ArrayOfManagedObjectReference)properties.get(0).getVal();
				hostListTemp = hostsMorefs.getManagedObjectReference();
				
			}
			
			for (ManagedObjectReference hostMoRef : hostListTemp) {
				
				RetrieveResult infoFromHost = collectFromVc(vcConnection, hostMoRef, false, Arrays.asList("vm","runtime"));
				
				if (infoFromHost != null) {
					for (ObjectContent objectContent : infoFromHost.getObjects()) {
						List<DynamicProperty> properties = objectContent.getPropSet();
						for (DynamicProperty property : properties) {
							if (property.getName().equals("vm")) {
								ArrayOfManagedObjectReference vmsMorefs = (ArrayOfManagedObjectReference)property.getVal();
								vmList.addAll(vmsMorefs.getManagedObjectReference());
							} else if (property.getName().equals("runtime")) {
								HostRuntimeInfo runtimeInfo = (HostRuntimeInfo)property.getVal();
								if (runtimeInfo.getConnectionState().toString().equals("CONNECTED")) {
									hostList.add(hostMoRef);
								}
							}
						}
					}
				}
				
			}
			
		}
		
		
		//Checking vMotion Compatibility
		
		if (vmList.size() == 0 || hostList.size() == 0) {
			throw new VmotionCheckPrerunException("{\"hosts\": "+hostList.size()+", \"vms\": "+vmList.size()+"}");
		}
		
		ManagedObjectReference vmProvChecker = vcConnection.getServiceContent().getVmProvisioningChecker();
		
		try {
			ManagedObjectReference task = vcConnection.getVimPort().queryVMotionCompatibilityExTask(vmProvChecker, vmList, hostList);
			
			RetrieveResult taskResult = null;
			
			do {
				taskResult = collectFromVc(vcConnection, task, false, Arrays.asList("info"));
				
			} while (!taskFinished(vcConnection, taskResult));
			
			vmotionTestResult.setHostCount(hostList.size());
			vmotionTestResult.setVmCount(vmList.size());

            Set<String> problematicVms = new HashSet<String>();

            if (taskResult != null) {
                for (ObjectContent objectContent : taskResult.getObjects()) {
                    List<DynamicProperty> properties = objectContent.getPropSet();
                    for (DynamicProperty property : properties) {
                        TaskInfo taskInfo = (TaskInfo)property.getVal(); 
                        ArrayOfCheckResult checkRes = (ArrayOfCheckResult) taskInfo.getResult();
                        if (checkRes != null) {
                        	for (CheckResult checkResult : checkRes.getCheckResult()) {
                            	
                            	if (checkResult.getError().size() > 0 || checkResult.getWarning().size() > 0) {
                            		
    								CheckResultDto vmotionCheckError = new CheckResultDto();
    								vmotionCheckError.setCheckResult(checkResult);
    								vmotionCheckError.setHostName(getMoRefName(vcConnection, checkResult.getHost()));
    								vmotionCheckError.setVmName(getMoRefName(vcConnection, checkResult.getVm()));
    								problematicVms.add(vmotionCheckError.getVmName());
    								vmotionTestResult.getCheckResultDto().add(vmotionCheckError);
    								
                                }
                            	
                            }
                        }

                    }
                }
                
                vmotionTestResult.setCollectionTimestamp(Instant.now().toEpochMilli());
                
                return vmotionTestResult;
                
            }
			
			
		} catch (RuntimeFaultFaultMsg e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	private RetrieveResult collectFromVc(VcConnection vcConnection, ManagedObjectReference object, boolean collectAll, List<String> props) {
		
		ManagedObjectReference propertyCollector = vcConnection.getServiceContent().getPropertyCollector();
		
		ObjectSpec objectSpec = new ObjectSpec();
		objectSpec.setObj(object);
		objectSpec.setSkip(false);
		
		PropertySpec propertySpec = new PropertySpec();
		propertySpec.setType(object.getType());
		propertySpec.setAll(collectAll);
		
		if (!collectAll) {
			propertySpec.getPathSet().addAll(props);
		}
		
		PropertyFilterSpec propertyFilterSpec = new PropertyFilterSpec();
		propertyFilterSpec.getObjectSet().add(objectSpec);
		propertyFilterSpec.getPropSet().add(propertySpec);
		
		List<PropertyFilterSpec> propertyFilterSpecList = new ArrayList<PropertyFilterSpec>();
		propertyFilterSpecList.add(propertyFilterSpec);
		
		
		RetrieveOptions retrieveOptions = new RetrieveOptions();
		
		RetrieveResult result = null;
		
		try {
			result = vcConnection.getVimPort().retrievePropertiesEx(propertyCollector, propertyFilterSpecList, retrieveOptions);
			
		} catch (InvalidPropertyFaultMsg e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeFaultFaultMsg e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
		
	}
	
	
	private String getMoRefName(VcConnection vcConnection, ManagedObjectReference object) {
		
		String objectName = object.getValue();
		ManagedObjectReference propertyCollector = vcConnection.getServiceContent().getPropertyCollector();
		
		ObjectSpec objectSpec = new ObjectSpec();
		objectSpec.setObj(object);
		objectSpec.setSkip(false);
		
		PropertySpec propertySpec = new PropertySpec();
		propertySpec.setType(object.getType());
		propertySpec.getPathSet().add("name");
		propertySpec.setAll(false);
		
		PropertyFilterSpec propertyFilterSpec = new PropertyFilterSpec();
		propertyFilterSpec.getObjectSet().add(objectSpec);
		propertyFilterSpec.getPropSet().add(propertySpec);
		
		List<PropertyFilterSpec> propertyFilterSpecList = new ArrayList<PropertyFilterSpec>();
		propertyFilterSpecList.add(propertyFilterSpec);
		
		
		RetrieveOptions retrieveOptions = new RetrieveOptions();
		
		RetrieveResult result = null;
		
		try {
			result = vcConnection.getVimPort().retrievePropertiesEx(propertyCollector, propertyFilterSpecList, retrieveOptions);
			
			if (result != null) {
				
				for (ObjectContent objectContent : result.getObjects()) {
					List<DynamicProperty> properties = objectContent.getPropSet();
					
					objectName = (String)properties.get(0).getVal();
					
				}
				
			}
			
			
		} catch (InvalidPropertyFaultMsg e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeFaultFaultMsg e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return objectName;
		
	}
	
	private Boolean taskFinished(VcConnection vcConnection, RetrieveResult taskResult) {
		
		if (taskResult == null) {
			return true;
		}
		
		for (ObjectContent objectContent : taskResult.getObjects()) {
		
			 List<DynamicProperty> properties = objectContent.getPropSet();
			 for (DynamicProperty property : properties) {
			
				 TaskInfo taskInfo = (TaskInfo)property.getVal();
				 if (taskInfo.getState().value().equals("queued") || taskInfo.getState().value().equals("running")) {
					 return false;
				 }
				 
			 } 
			 
		}
		
		return true;
	}
	

}
