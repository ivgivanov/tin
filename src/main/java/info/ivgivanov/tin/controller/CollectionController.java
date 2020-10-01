package info.ivgivanov.tin.controller;

import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vmware.vim25.InvalidLocaleFaultMsg;
import com.vmware.vim25.InvalidLoginFaultMsg;
import com.vmware.vim25.RuntimeFaultFaultMsg;

import info.ivgivanov.tin.model.VcAuthentication;
import info.ivgivanov.tin.model.VcConnection;
import info.ivgivanov.tin.model.dto.ClusterReferenceListDto;
import info.ivgivanov.tin.model.dto.HostSystemListDto;
import info.ivgivanov.tin.model.dto.VcenterServerDto;
import info.ivgivanov.tin.repository.ReleaseNamesRepository;
import info.ivgivanov.tin.service.collection.CollectionService;

@RestController
public class CollectionController {
	
	@Autowired
	private CollectionService collectionService;
	
	@Autowired
	ReleaseNamesRepository releaseNamesRepository;
	
	@PostMapping("/authenticate")
	void createSession(@RequestBody VcAuthentication vcAuth, HttpSession session) throws KeyManagementException, NoSuchAlgorithmException, UnknownHostException, RuntimeFaultFaultMsg, InvalidLoginFaultMsg, InvalidLocaleFaultMsg {
		
		VcConnection vcConnection = new VcConnection(vcAuth);
		vcConnection.getVimPort().logout(vcConnection.getServiceContent().getSessionManager());
		System.out.println("Logged out from vCenter Server");
		session.setAttribute("auth", vcAuth);
	
	  }
	
	
	@GetMapping("/collection/vcenter")
	VcenterServerDto vcInfo(HttpSession session) throws KeyManagementException, NoSuchAlgorithmException, RuntimeFaultFaultMsg, InvalidLoginFaultMsg, InvalidLocaleFaultMsg, UnknownHostException {
		VcAuthentication vcAuth = (VcAuthentication) session.getAttribute("auth");
		VcConnection vcConnection = new VcConnection(vcAuth);
		
		return collectionService.collectVc(vcConnection);
	
	  }
	
	@GetMapping("/collection/hosts")
	public HostSystemListDto getHostsInfo(HttpSession session) throws KeyManagementException, NoSuchAlgorithmException, UnknownHostException, RuntimeFaultFaultMsg, InvalidLoginFaultMsg, InvalidLocaleFaultMsg {
	
		VcAuthentication vcAuth = (VcAuthentication) session.getAttribute("auth");
		VcConnection vcConnection = new VcConnection(vcAuth);
		
		return collectionService.collectHosts(vcConnection);	
		
	}
	
	@GetMapping("/collection/clusters")
	public ClusterReferenceListDto getClustersInfo(HttpSession session) throws KeyManagementException, NoSuchAlgorithmException, UnknownHostException, RuntimeFaultFaultMsg, InvalidLoginFaultMsg, InvalidLocaleFaultMsg {
	
		VcAuthentication vcAuth = (VcAuthentication) session.getAttribute("auth");
		VcConnection vcConnection = new VcConnection(vcAuth);
		
		return collectionService.collectClusters(vcConnection);	
		
	}
	
	@GetMapping("/collection/vcenter-releases")
	void getVcenterReleases() {
		releaseNamesRepository.collectVcenterReleases();
	}
	
	@GetMapping("/collection/host-releases")
	void getHostReleases() {
		releaseNamesRepository.collectHostReleases();
	}
	
}
