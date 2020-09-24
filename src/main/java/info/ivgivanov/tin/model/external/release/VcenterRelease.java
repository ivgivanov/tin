package info.ivgivanov.tin.model.external.release;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VcenterRelease {
	
	private String build;
	private String fullName;
	private String releaseDate;
	private String installerBuild;
	private String osType;
	private String releaseNotes;
	
	
	public String getBuild() {
		return build;
	}
	public void setBuild(String build) {
		this.build = build;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	public String getInstallerBuild() {
		return installerBuild;
	}
	public void setInstallerBuild(String installerBuild) {
		this.installerBuild = installerBuild;
	}
	public String getOsType() {
		return osType;
	}
	public void setOsType(String osType) {
		this.osType = osType;
	}
	public String getReleaseNotes() {
		return releaseNotes;
	}
	public void setReleaseNotes(String releaseNotes) {
		this.releaseNotes = releaseNotes;
	}
	
	public VcenterRelease() {
		
	}
	
	public VcenterRelease(String status) {
		this.setBuild(status);
		this.setFullName(status);
		this.setInstallerBuild(status);
		this.setOsType(status);
		this.setReleaseDate(status);
		this.setReleaseNotes(status);
	}
	

}
