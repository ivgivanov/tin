package info.ivgivanov.tin.model.dto;

import com.vmware.vim25.CheckResult;

public class CheckResultDto {
	
	public String hostName;
	public String vmName;
	public CheckResult checkResult;
	
	
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getVmName() {
		return vmName;
	}
	public void setVmName(String vmName) {
		this.vmName = vmName;
	}
	public CheckResult getCheckResult() {
		return checkResult;
	}
	public void setCheckResult(CheckResult checkResult) {
		this.checkResult = checkResult;
	}
	
	

}
