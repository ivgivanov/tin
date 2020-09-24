package info.ivgivanov.tin.model.dto;

import java.util.List;

public class HostSystemListDto {
	
	private List<HostSystemDto> hosts;
	private Long collectionTimestamp;
	
	
	public List<HostSystemDto> getHosts() {
		return hosts;
	}
	private void setHosts(List<HostSystemDto> hosts) {
		this.hosts = hosts;
	}
	public Long getCollectionTimestamp() {
		return collectionTimestamp;
	}
	private void setCollectionTimestamp(Long collectionTimestamp) {
		this.collectionTimestamp = collectionTimestamp;
	}
	
	
	public HostSystemListDto(List<HostSystemDto> hosts, Long timestamp) {
		this.setHosts(hosts);
		this.setCollectionTimestamp(timestamp);
	}
	
}
