package info.ivgivanov.tin.model.dto;

import com.vmware.vim25.HostConfigInfo;
import com.vmware.vim25.HostHardwareInfo;

import info.ivgivanov.tin.model.external.release.HostRelease;

public class HostSystemDto {
	
	private String name;
	private HostConfigInfo config;
	private HostHardwareInfo hardware;
	private HostRelease hostRelease;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public HostConfigInfo getConfig() {
		return config;
	}
	public void setConfig(HostConfigInfo config) {
		this.config = config;
	}
	public HostHardwareInfo getHardware() {
		return hardware;
	}
	public void setHardware(HostHardwareInfo hardware) {
		this.hardware = hardware;
	}
	public HostRelease getHostRelease() {
		return hostRelease;
	}
	public void setHostRelease(HostRelease hostRelease) {
		this.hostRelease = hostRelease;
	}
	

}
