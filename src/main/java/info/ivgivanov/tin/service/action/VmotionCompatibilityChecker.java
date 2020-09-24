package info.ivgivanov.tin.service.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.vmware.vim25.ArrayOfCheckResult;
import com.vmware.vim25.CheckResult;
import com.vmware.vim25.DynamicProperty;
import com.vmware.vim25.InvalidPropertyFaultMsg;
import com.vmware.vim25.LocalizedMethodFault;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.ObjectContent;
import com.vmware.vim25.ObjectSpec;
import com.vmware.vim25.PropertyFilterSpec;
import com.vmware.vim25.PropertySpec;
import com.vmware.vim25.RetrieveOptions;
import com.vmware.vim25.RetrieveResult;
import com.vmware.vim25.RuntimeFaultFaultMsg;
import com.vmware.vim25.TaskInfo;

import info.ivgivanov.tin.model.VcConnection;

@Service
public class VmotionCompatibilityChecker {
	
	public void checkVmotionCompatibility(VcConnection vcConnection) {
		
		ManagedObjectReference vmProvChecker = vcConnection.getServiceContent().getVmProvisioningChecker();
		
		// TEMP
		ManagedObjectReference myVm = new ManagedObjectReference();
        myVm.setType("VirtualMachine");
        myVm.setValue("vm-16");

        ManagedObjectReference myHost = new ManagedObjectReference();
        myHost.setType("HostSystem");
        myHost.setValue("host-10");

        List<ManagedObjectReference> vmList = new ArrayList<ManagedObjectReference>();
        vmList.add(myVm);
        List<ManagedObjectReference> hostList = new ArrayList<ManagedObjectReference>();
        hostList.add(myHost);
		//
		
		try {
			ManagedObjectReference task = vcConnection.getVimPort().queryVMotionCompatibilityExTask(vmProvChecker, vmList, hostList);
			System.out.println(task.getValue());
			
			ManagedObjectReference propertyCollector = vcConnection.getServiceContent().getPropertyCollector();

            ObjectSpec objectSpec = new ObjectSpec();
            objectSpec.setObj(task);
            objectSpec.setSkip(false);

            PropertySpec propertySpecCluster = new PropertySpec();
            propertySpecCluster.setType("Task");
            propertySpecCluster.getPathSet().add("info");
            propertySpecCluster.setAll(false);

            PropertyFilterSpec propertyFilterSpec = new PropertyFilterSpec();
            propertyFilterSpec.getObjectSet().add(objectSpec);
            propertyFilterSpec.getPropSet().add(propertySpecCluster);

            List<PropertyFilterSpec> propertyFilterSpecList = new ArrayList<PropertyFilterSpec>();
            propertyFilterSpecList.add(propertyFilterSpec);

            RetrieveOptions retrieveOptions = new RetrieveOptions();

            RetrieveResult result = vcConnection.getVimPort().retrievePropertiesEx(propertyCollector, propertyFilterSpecList, retrieveOptions);

            Set<String> problematicVms = new HashSet<String>();

            if (result != null) {
                for (ObjectContent objectContent : result.getObjects()) {
                    List<DynamicProperty> properties = objectContent.getPropSet();
                    for (DynamicProperty property : properties) {
                        TaskInfo taskInfo = (TaskInfo)property.getVal();
                        System.out.println(taskInfo.getState().value());
                        TimeUnit.SECONDS.sleep(5);
                        System.out.println(taskInfo.getState().value());
                        ArrayOfCheckResult checkRes = (ArrayOfCheckResult) taskInfo.getResult();
                        for (CheckResult checkResult : checkRes.getCheckResult()) {
                            if (checkResult.getError().size() > 0) {
                                problematicVms.add(checkResult.getVm().getValue());
                                System.out.println("Unable to vMotion VM "+checkResult.getVm().getValue()+" to host "+checkResult.getHost().getValue());
                                System.out.println("Reasons:");
                                for (LocalizedMethodFault error : checkResult.getError()) {
                                    System.out.println(error.getLocalizedMessage());
                                }
                            }
                        }

                        System.out.println("vMotion not possible for VMs: "+problematicVms);
                    }
                }
            }
			
			
		} catch (RuntimeFaultFaultMsg e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidPropertyFaultMsg e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
