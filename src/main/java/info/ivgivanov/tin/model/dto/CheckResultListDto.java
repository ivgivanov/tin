package info.ivgivanov.tin.model.dto;

import java.util.ArrayList;
import java.util.List;

public class CheckResultListDto {
	
	List<CheckResultDto> checkResultDto;
	private Long collectionTimestamp;
	private Integer hostCount;
	private Integer vmCount;

	public List<CheckResultDto> getCheckResultDto() {
		return checkResultDto;
	}

	public void setCheckResultDto(List<CheckResultDto> checkResultDto) {
		this.checkResultDto = checkResultDto;
	}
	
	public Long getCollectionTimestamp() {
		return collectionTimestamp;
	}

	public void setCollectionTimestamp(Long collectionTimestamp) {
		this.collectionTimestamp = collectionTimestamp;
	}
	

	public Integer getHostCount() {
		return hostCount;
	}

	public void setHostCount(Integer hostCount) {
		this.hostCount = hostCount;
	}

	public Integer getVmCount() {
		return vmCount;
	}

	public void setVmCount(Integer vmCount) {
		this.vmCount = vmCount;
	}
	

	public CheckResultListDto() {
		setCheckResultDto(new ArrayList<>());
	}
	
}
