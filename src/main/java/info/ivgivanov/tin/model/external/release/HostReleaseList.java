package info.ivgivanov.tin.model.external.release;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HostReleaseList {
	
	private List<HostRelease> esxiReleases;

	public List<HostRelease> getEsxiReleases() {
		return esxiReleases;
	}

	public void setEsxiReleases(List<HostRelease> esxiReleases) {
		this.esxiReleases = esxiReleases;
	}
	
	

}
