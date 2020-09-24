package info.ivgivanov.tin.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import info.ivgivanov.tin.model.external.release.HostRelease;
import info.ivgivanov.tin.model.external.release.HostReleaseWrapper;
import info.ivgivanov.tin.model.external.release.VcenterRelease;
import info.ivgivanov.tin.model.external.release.VcenterReleaseWrapper;

@Component
public class ReleaseNamesRepository {
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static final String VCENTER_REPOSITORY_URL = "https://www.virten.net/repo/vcenterReleases.json";
	private static final String HOST_REPOSITORY_URL = "https://www.virten.net/repo/esxiReleases.json";
	private Map<String, HostRelease> hostReleaseNames;
	private Map<String, VcenterRelease> vcenterReleaseNames;
	
	public Map<String, VcenterRelease> getVcenterReleaseNames() {
		return vcenterReleaseNames;
	}

	private void setVcenterReleaseNames(Map<String, VcenterRelease> vcenterReleaseNames) {
		this.vcenterReleaseNames = vcenterReleaseNames;
	}

	public Map<String, HostRelease> getHostReleaseNames() {
		return hostReleaseNames;
	}
	public void setHostReleaseNames(Map<String, HostRelease> hostReleaseNames) {
		this.hostReleaseNames = hostReleaseNames;
	}
	
	public void collectVcenterReleases() {
		
		Map<String, VcenterRelease> mapResult = new HashMap<>();
		
		VcenterReleaseWrapper vcenterReleaseWrapper = restTemplate.getForObject(VCENTER_REPOSITORY_URL, VcenterReleaseWrapper.class);
		
		for (VcenterRelease vcenterRelease : vcenterReleaseWrapper.getData().getVcenterReleases()) {
			mapResult.put(vcenterRelease.getBuild(), vcenterRelease);
		}
		
		this.setVcenterReleaseNames(mapResult);
	}
	
public void collectHostReleases(){
		
		Map<String, HostRelease> mapResult = new HashMap<>();
		
		HostReleaseWrapper hostReleaseWrapper = restTemplate.getForObject(HOST_REPOSITORY_URL, HostReleaseWrapper.class);
		
		for (HostRelease hostRelease : hostReleaseWrapper.getData().getEsxiReleases()) {
			mapResult.put(hostRelease.getBuild(), hostRelease);
		}
		
		this.setHostReleaseNames(mapResult);
	}

}
