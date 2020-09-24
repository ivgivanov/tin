package info.ivgivanov.tin.model.external.release;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HostReleaseWrapper {
	
	private HostReleaseList data;
	private Long timestamp;
	private String jsonUpdatedTime;
	
	
	public HostReleaseList getData() {
		return data;
	}
	public void setData(HostReleaseList data) {
		this.data = data;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public String getJsonUpdatedTime() {
		return jsonUpdatedTime;
	}
	public void setJsonUpdatedTime(String jsonUpdatedTime) {
		this.jsonUpdatedTime = jsonUpdatedTime;
	}
	
	

}
