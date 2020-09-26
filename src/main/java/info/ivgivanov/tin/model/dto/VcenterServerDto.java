package info.ivgivanov.tin.model.dto;

import java.util.List;

import com.vmware.vim25.AboutInfo;
import com.vmware.vim25.OptionValue;

import info.ivgivanov.tin.model.external.release.VcenterRelease;
import info.ivgivanov.tin.model.tin.TinVcenterExtension;

public class VcenterServerDto {
	
	private String hostname;
	private AboutInfo about;
	private List<TinVcenterExtension> extensions;
	private List<OptionValue> advancedSettings;
	private Long collectionTimestamp;
	private VcenterRelease vcenterRelease;
	

	public String getHostname() {
		return hostname;
	}

	private void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public AboutInfo getAbout() {
		return about;
	}

	private void setAbout(AboutInfo about) {
		this.about = about;
	}
	
	
	public List<TinVcenterExtension> getExtensions() {
		return extensions;
	}

	private void setExtensions(List<TinVcenterExtension> extensions) {
		this.extensions = extensions;
	}

	public List<OptionValue> getAdvancedSettings() {
		return advancedSettings;
	}

	private void setAdvancedSettings(List<OptionValue> advancedSettings) {
		this.advancedSettings = advancedSettings;
	}
	

	public Long getCollectionTimestamp() {
		return collectionTimestamp;
	}

	private void setCollectionTimestamp(Long collectionTimestamp) {
		this.collectionTimestamp = collectionTimestamp;
	}
	

	public VcenterRelease getVcenterRelease() {
		return vcenterRelease;
	}

	private void setVcenterRelease(VcenterRelease vcenterRelease) {
		this.vcenterRelease = vcenterRelease;
	}

	public VcenterServerDto(String hostname, AboutInfo about, List<TinVcenterExtension> ext, List<OptionValue> advSettings, Long timestamp, VcenterRelease vcRelease) {
		this.setHostname(hostname);
		this.setAbout(about);
		this.setExtensions(ext);
		this.setAdvancedSettings(advSettings);
		this.setCollectionTimestamp(timestamp);
		this.setVcenterRelease(vcRelease);
	}

}
