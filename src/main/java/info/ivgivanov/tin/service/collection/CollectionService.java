package info.ivgivanov.tin.service.collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import info.ivgivanov.tin.model.VcConnection;
import info.ivgivanov.tin.model.dto.ClusterReferenceListDto;
import info.ivgivanov.tin.model.dto.HostSystemListDto;
import info.ivgivanov.tin.model.dto.VcenterServerDto;

@Service
public class CollectionService {
	
	@Autowired
	private VcCollectionService vcCollectionService;
	
	@Autowired
	private HostCollectionService hostCollectionService;
	
	@Autowired
	private ClusterCollectionService clusterCollectionService;
	

	public VcenterServerDto collectVc(VcConnection vcConnection) {
		
		return vcCollectionService.collectVcenterServerInfo(vcConnection);
	}
	
	public HostSystemListDto collectHosts(VcConnection vcConnection) {
		return hostCollectionService.collectHosts(vcConnection);
	}
	
	public ClusterReferenceListDto collectClusters(VcConnection vcConnection) {
		return clusterCollectionService.collectClusters(vcConnection);
	}
	

}
