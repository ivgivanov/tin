package info.ivgivanov.tin.model.external.release;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VcenterReleaseList {

	private List<VcenterRelease> vcenterReleases;

	public List<VcenterRelease> getVcenterReleases() {
		return vcenterReleases;
	}

	public void setVcenterReleases(List<VcenterRelease> vcenterReleases) {
		this.vcenterReleases = vcenterReleases;
	}

	
	
	
}
