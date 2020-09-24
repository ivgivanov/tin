package info.ivgivanov.tin.service.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import info.ivgivanov.tin.model.VcConnection;

@Service
public class ActionService {
	
	@Autowired
	public VmotionCompatibilityChecker vmotionCompatibilityChecker;
	
	
	public void checkVmotion(VcConnection vcConnection) {
		
		vmotionCompatibilityChecker.checkVmotionCompatibility(vcConnection);
		
	}
	

}
