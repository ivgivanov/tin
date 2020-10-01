package info.ivgivanov.tin.model.dto;

import java.util.ArrayList;
import java.util.List;

public class CheckResultListDto {
	
	List<CheckResultDto> checkResultDto;
	private Long collectionTimestamp;

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

	public CheckResultListDto() {
		setCheckResultDto(new ArrayList<>());
	}
	
}
