package info.ivgivanov.tin.model.external.release;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HostRelease {
	
	private String build;
	private String minorRelease;
	private String updateRelease;
	private String releaseLevel;
	private String releaseFullName;
	private String friendlyName;
	private String patchRelease;
	private String releaseDate;
	private String imageProfile;
	
	
	public String getBuild() {
		return build;
	}
	public void setBuild(String build) {
		this.build = build;
	}
	public String getMinorRelease() {
		return minorRelease;
	}
	public void setMinorRelease(String minorRelease) {
		this.minorRelease = minorRelease;
	}
	public String getUpdateRelease() {
		return updateRelease;
	}
	public void setUpdateRelease(String updateRelease) {
		this.updateRelease = updateRelease;
	}
	public String getReleaseLevel() {
		return releaseLevel;
	}
	public void setReleaseLevel(String releaseLevel) {
		this.releaseLevel = releaseLevel;
	}
	public String getReleaseFullName() {
		return releaseFullName;
	}
	public void setReleaseFullName(String releaseFullName) {
		this.releaseFullName = releaseFullName;
	}
	public String getFriendlyName() {
		return friendlyName;
	}
	public void setFriendlyName(String friendlyName) {
		this.friendlyName = friendlyName;
	}
	public String getPatchRelease() {
		return patchRelease;
	}
	public void setPatchRelease(String patchRelease) {
		this.patchRelease = patchRelease;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	public String getImageProfile() {
		return imageProfile;
	}
	public void setImageProfile(String imageProfile) {
		this.imageProfile = imageProfile;
	}
	
	public HostRelease() {
		
	}
	
	public HostRelease(String status) {
		this.setBuild(status);
		this.setFriendlyName(status);
		this.setImageProfile(status);
		this.setMinorRelease(status);
		this.setPatchRelease(status);
		this.setReleaseDate(status);
		this.setReleaseFullName(status);
		this.setReleaseLevel(status);
		this.setUpdateRelease(status);
	}
	

}
