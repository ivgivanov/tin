package info.ivgivanov.tin.model.dto;

import com.vmware.vim25.ManagedObjectReference;

public class ClusterReferenceDto {
	
	public String name;
	public ManagedObjectReference moRef;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public ManagedObjectReference getMoRef() {
		return moRef;
	}
	public void setMoRef(ManagedObjectReference moRef) {
		this.moRef = moRef;
	}

}
