package info.ivgivanov.tin.model;

import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.xml.ws.BindingProvider;

import com.vmware.vim25.InvalidLocaleFaultMsg;
import com.vmware.vim25.InvalidLoginFaultMsg;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.RuntimeFaultFaultMsg;
import com.vmware.vim25.ServiceContent;
import com.vmware.vim25.VimPortType;
import com.vmware.vim25.VimService;

import info.ivgivanov.tin.config.SslManager;

public class VcConnection {
	
	private String address;
    private VimPortType vimPort;
    private ServiceContent serviceContent;

    
    public String getAddress() {
		return address;
	}


	private void setAddress(String address) {
		this.address = address;
	}


	public VimPortType getVimPort() {
		return vimPort;
	}


	private void setVimPort(VimPortType vimPort) {
		this.vimPort = vimPort;
	}


	public ServiceContent getServiceContent() {
		return serviceContent;
	}


	private void setServiceContent(ServiceContent serviceContent) {
		this.serviceContent = serviceContent;
	}
	
	public VcConnection(VcAuthentication vcAuth) throws RuntimeFaultFaultMsg, InvalidLoginFaultMsg, InvalidLocaleFaultMsg, KeyManagementException, NoSuchAlgorithmException, UnknownHostException {

		//System.out.println("Authentication to vCenter Server");
		
		String address = "https://"+vcAuth.getAddress()+"/sdk";
		
        VimService vimService = new VimService();
        VimPortType vimPort = vimService.getVimPort();
        

        Map<String, Object> ctxt = ((BindingProvider) vimPort).getRequestContext();
        ctxt.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, address);
        ctxt.put(BindingProvider.SESSION_MAINTAIN_PROPERTY, true);

        // Disable all SSL trust security
        SslManager.trustEveryone();

        ManagedObjectReference serviceInstance = new ManagedObjectReference();
        serviceInstance.setType("ServiceInstance");
        serviceInstance.setValue("ServiceInstance");

        ServiceContent serviceContent = vimPort.retrieveServiceContent(serviceInstance);
        vimPort.login(serviceContent.getSessionManager(), vcAuth.getUsername(), vcAuth.getPassword(), null);
        this.setAddress(address);
        this.setVimPort(vimPort);
        this.setServiceContent(serviceContent);

    }

}
