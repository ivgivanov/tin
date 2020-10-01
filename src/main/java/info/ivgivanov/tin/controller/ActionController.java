package info.ivgivanov.tin.controller;

import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vmware.vim25.InvalidLocaleFaultMsg;
import com.vmware.vim25.InvalidLoginFaultMsg;
import com.vmware.vim25.RuntimeFaultFaultMsg;

import info.ivgivanov.tin.model.VcAuthentication;
import info.ivgivanov.tin.model.VcConnection;
import info.ivgivanov.tin.model.dto.CheckResultListDto;
import info.ivgivanov.tin.model.dto.ClusterReferenceDto;
import info.ivgivanov.tin.service.action.ActionService;

@RestController
public class ActionController {
	
	@Autowired
	public ActionService actionService;
	
	
	@PostMapping("/action/vmotion-compatibility")
	CheckResultListDto checkVmotion(@RequestBody ClusterReferenceDto cluster, HttpSession session) throws KeyManagementException, NoSuchAlgorithmException, RuntimeFaultFaultMsg, InvalidLoginFaultMsg, InvalidLocaleFaultMsg, UnknownHostException {
		
		VcAuthentication vcAuth = (VcAuthentication) session.getAttribute("auth");
		VcConnection vcConnection = new VcConnection(vcAuth);
		
		return actionService.checkVmotion(vcConnection, cluster);
	
	  }
	

}
