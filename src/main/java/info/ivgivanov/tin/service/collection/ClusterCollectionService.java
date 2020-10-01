package info.ivgivanov.tin.service.collection;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vmware.vim25.DynamicProperty;
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
import info.ivgivanov.tin.model.dto.ClusterReferenceDto;
import info.ivgivanov.tin.model.dto.ClusterReferenceListDto;
import info.ivgivanov.tin.utils.CollectionUtils;

@Service
public class ClusterCollectionService {
	
	public ClusterReferenceListDto collectClusters(VcConnection vcConnection) {
		
		
		Long timestamp = Instant.now().toEpochMilli();
		List<ClusterReferenceDto> clusters = new ArrayList<>();
		
		ServiceContent serviceContent = vcConnection.getServiceContent();
		ManagedObjectReference propertyCollector = serviceContent.getPropertyCollector();
		
		ObjectSpec objectSpec = new ObjectSpec();
        objectSpec.setObj(serviceContent.getRootFolder());
        objectSpec.getSelectSet().addAll(CollectionUtils.buildFullTraversal());
        objectSpec.setSkip(false);
        
        PropertySpec propertySpecCluster = new PropertySpec();
        propertySpecCluster.setType("ClusterComputeResource");
        propertySpecCluster.getPathSet().add("name");

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
            	ClusterReferenceDto cluster = new ClusterReferenceDto();
            	
                List<DynamicProperty> properties = objectContent.getPropSet();
                
                cluster.setName((String)properties.get(0).getVal());
                cluster.setMoRef(objectContent.getObj());
                
                clusters.add(cluster);
                
            }

        }
		
		
		return new ClusterReferenceListDto(clusters, timestamp);
		
	}

}
