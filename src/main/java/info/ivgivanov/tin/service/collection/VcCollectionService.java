package info.ivgivanov.tin.service.collection;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vmware.vim25.ArrayOfExtension;
import com.vmware.vim25.DynamicProperty;
import com.vmware.vim25.Extension;
import com.vmware.vim25.InvalidNameFaultMsg;
import com.vmware.vim25.InvalidPropertyFaultMsg;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.ObjectContent;
import com.vmware.vim25.ObjectSpec;
import com.vmware.vim25.OptionValue;
import com.vmware.vim25.PropertyFilterSpec;
import com.vmware.vim25.PropertySpec;
import com.vmware.vim25.RetrieveOptions;
import com.vmware.vim25.RetrieveResult;
import com.vmware.vim25.RuntimeFaultFaultMsg;
import com.vmware.vim25.ServiceContent;

import info.ivgivanov.tin.model.VcConnection;
import info.ivgivanov.tin.model.dto.VcenterServerDto;
import info.ivgivanov.tin.model.external.release.VcenterRelease;
import info.ivgivanov.tin.model.tin.TinVcenterExtension;
import info.ivgivanov.tin.repository.ReleaseNamesRepository;

@Service
public class VcCollectionService {
	
	@Autowired
	ReleaseNamesRepository releaseNamesRepository;
	
	public VcenterServerDto collectVcenterServerInfo(VcConnection vcConnection) {
		
		ServiceContent serviceContent = vcConnection.getServiceContent();
		List<OptionValue> advSettings = new ArrayList<>();
		ArrayOfExtension extensionsTemp = new ArrayOfExtension();
		List<TinVcenterExtension> extensions = new ArrayList<>();
		
		
		ManagedObjectReference vcSettings = serviceContent.getSetting();
		ManagedObjectReference propertyCollector = serviceContent.getPropertyCollector();
		RetrieveResult extManagerResult = new RetrieveResult();
		
		ObjectSpec objectSpec = new ObjectSpec();
        objectSpec.setObj(serviceContent.getExtensionManager());
        objectSpec.setSkip(false);

        PropertySpec propertySpecExtManager = new PropertySpec();
        propertySpecExtManager.setType("ExtensionManager");
        propertySpecExtManager.getPathSet().add("extensionList");
        propertySpecExtManager.setAll(false);

        PropertyFilterSpec propertyFilterSpec = new PropertyFilterSpec();
        propertyFilterSpec.getObjectSet().add(objectSpec);
        propertyFilterSpec.getPropSet().add(propertySpecExtManager);
        
        List<PropertyFilterSpec> propertyFilterSpecList = new ArrayList<PropertyFilterSpec>();
        propertyFilterSpecList.add(propertyFilterSpec);

        RetrieveOptions retrieveOptions = new RetrieveOptions();
		
		try {
			
			advSettings = vcConnection.getVimPort().queryOptions(vcSettings, null);
			extManagerResult = vcConnection.getVimPort().retrievePropertiesEx(propertyCollector, propertyFilterSpecList, retrieveOptions);
			
		} catch (InvalidPropertyFaultMsg e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeFaultFaultMsg e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidNameFaultMsg e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Optional<OptionValue> hostnameUrlOpVal = advSettings.stream()
				  .filter(opVal -> opVal.getKey().equals("config.vpxd.hostnameUrl"))
				  .findFirst();
		
		String hostnameUrl  = (hostnameUrlOpVal.isPresent()) ? hostnameUrlOpVal.get().getValue().toString() : "Unknown";
		
		if (extManagerResult != null) {
            for (ObjectContent objectContent : extManagerResult.getObjects()) {
            	List<DynamicProperty> properties = objectContent.getPropSet();
            	for (DynamicProperty property : properties) {
            		extensionsTemp = (ArrayOfExtension)property.getVal();
            	}
            }
		
		}
		
		for (Extension ext : extensionsTemp.getExtension()) {
			extensions.add(new TinVcenterExtension(ext.getDescription().getLabel(), ext.getDescription().getSummary(), ext.getKey(), ext.getVersion()));
		}
		
		try {
			vcConnection.getVimPort().logout(vcConnection.getServiceContent().getSessionManager());
			System.out.println("Logged out from vCenter Server");
		} catch (RuntimeFaultFaultMsg e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (releaseNamesRepository.getVcenterReleaseNames() != null && releaseNamesRepository.getVcenterReleaseNames().get(serviceContent.getAbout().getBuild()) != null) {
			return new VcenterServerDto(hostnameUrl, serviceContent.getAbout(), extensions, advSettings, Instant.now().toEpochMilli(), releaseNamesRepository.getVcenterReleaseNames().get(serviceContent.getAbout().getBuild()));
		} else {
			return new VcenterServerDto(hostnameUrl, serviceContent.getAbout(), extensions, advSettings, Instant.now().toEpochMilli(), new VcenterRelease("Unknown"));
		}
		
	}
}
