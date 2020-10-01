package info.ivgivanov.tin.service.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import info.ivgivanov.tin.model.VcConnection;
import info.ivgivanov.tin.model.dto.CheckResultListDto;
import info.ivgivanov.tin.model.dto.ClusterReferenceDto;

@Service
public class ActionService {
	
	@Autowired
	public VmotionCompatibilityChecker vmotionCompatibilityChecker;
	
	
	public CheckResultListDto checkVmotion(VcConnection vcConnection, ClusterReferenceDto cluster) {
		
		return vmotionCompatibilityChecker.checkVmotionCompatibility(vcConnection, cluster);
		
	}
	

}
