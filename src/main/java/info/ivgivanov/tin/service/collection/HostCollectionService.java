package info.ivgivanov.tin.service.collection;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vmware.vim25.DynamicProperty;
import com.vmware.vim25.HostConfigInfo;
import com.vmware.vim25.HostHardwareInfo;
import com.vmware.vim25.InvalidPropertyFaultMsg;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.ObjectContent;
import com.vmware.vim25.ObjectSpec;
import com.vmware.vim25.PropertyFilterSpec;
import com.vmware.vim25.PropertySpec;
import com.vmware.vim25.RetrieveOptions;
import com.vmware.vim25.RetrieveResult;
import com.vmware.vim25.RuntimeFaultFaultMsg;
import com.vmware.vim25.ServiceContent;

import info.ivgivanov.tin.model.VcConnection;
import info.ivgivanov.tin.model.dto.HostSystemDto;
import info.ivgivanov.tin.model.dto.HostSystemListDto;
import info.ivgivanov.tin.model.external.release.HostRelease;
import info.ivgivanov.tin.repository.ReleaseNamesRepository;
import info.ivgivanov.tin.utils.CollectionUtils;

@Service
public class HostCollectionService {
	
	@Autowired
	ReleaseNamesRepository releaseNamesRepository;

	
	public HostSystemListDto collectHosts(VcConnection vcConnection) {
		
		Long timestamp = Instant.now().toEpochMilli();
		List<HostSystemDto> hosts = new ArrayList<>();
		ServiceContent serviceContent = vcConnection.getServiceContent();
		ManagedObjectReference propertyCollector = serviceContent.getPropertyCollector();

        ObjectSpec objectSpec = new ObjectSpec();
        objectSpec.setObj(serviceContent.getRootFolder());
        objectSpec.getSelectSet().addAll(CollectionUtils.buildFullTraversal());
        objectSpec.setSkip(false);

        PropertySpec propertySpecCluster = new PropertySpec();
        propertySpecCluster.setType("HostSystem");
        //propertySpecCluster.getPathSet().add("config");
        //propertySpecCluster.getPathSet().add("hardware");
        propertySpecCluster.setAll(true);

        PropertyFilterSpec propertyFilterSpec = new PropertyFilterSpec();
        propertyFilterSpec.getObjectSet().add(objectSpec);
        propertyFilterSpec.getPropSet().add(propertySpecCluster);

        List<PropertyFilterSpec> propertyFilterSpecList = new ArrayList<PropertyFilterSpec>();
        propertyFilterSpecList.add(propertyFilterSpec);

        RetrieveOptions retrieveOptions = new RetrieveOptions();

        RetrieveResult result = null;
        try {
            result = vcConnection.getVimPort().retrievePropertiesEx(propertyCollector, propertyFilterSpecList, retrieveOptions);
        } catch (InvalidPropertyFaultMsg invalidPropertyFaultMsg) {
            invalidPropertyFaultMsg.printStackTrace();
        } catch (RuntimeFaultFaultMsg runtimeFaultFaultMsg) {
            runtimeFaultFaultMsg.printStackTrace();
        }
        
        
        if (result != null) {
            for (ObjectContent objectContent : result.getObjects()) {
                //System.out.println("moid: "+objectContent.getObj().getValue());
            	HostSystemDto host = new HostSystemDto();
            	
                List<DynamicProperty> properties = objectContent.getPropSet();
                for (DynamicProperty property : properties) {
                    //System.out.println(property.getName());
                    if (property.getName().equals("config")) {
                    	host.setConfig((HostConfigInfo)property.getVal());
                    } else if (property.getName().equals("hardware")) {
                    	host.setHardware((HostHardwareInfo)property.getVal());
                    } else if (property.getName().equals("name")) {
                    	host.setName((String)property.getVal());
                    }
                    
                }
                if (host.getConfig() != null && releaseNamesRepository.getHostReleaseNames() != null && releaseNamesRepository.getHostReleaseNames().get(host.getConfig().getProduct().getBuild()) != null) {
                	host.setHostRelease(releaseNamesRepository.getHostReleaseNames().get(host.getConfig().getProduct().getBuild()));
                } else {
                	host.setHostRelease(new HostRelease("Unknown"));
                }
                
                hosts.add(host);
                
            }

        }
        
        try {
			vcConnection.getVimPort().logout(vcConnection.getServiceContent().getSessionManager());
		} catch (RuntimeFaultFaultMsg e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return new HostSystemListDto(hosts, timestamp);
	}

}
