package info.ivgivanov.tin.model.dto;

import java.util.List;

public class ClusterReferenceListDto {
	
	
	private List<ClusterReferenceDto> clusters;
	private Long collectionTimestamp;
	
	public List<ClusterReferenceDto> getClusters() {
		return clusters;
	}
	private void setClusters(List<ClusterReferenceDto> clusters) {
		this.clusters = clusters;
	}
	public Long getCollectionTimestamp() {
		return collectionTimestamp;
	}
	private void setCollectionTimestamp(Long collectionTimestamp) {
		this.collectionTimestamp = collectionTimestamp;
	}
	
	public ClusterReferenceListDto (List<ClusterReferenceDto> clusters, Long timestamp) {
		
		this.setClusters(clusters);
		this.setCollectionTimestamp(timestamp);
		
	}

}
