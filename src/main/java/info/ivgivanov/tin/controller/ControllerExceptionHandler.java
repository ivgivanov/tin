package info.ivgivanov.tin.controller;

import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vmware.vim25.InvalidLocaleFaultMsg;
import com.vmware.vim25.InvalidLoginFaultMsg;
import com.vmware.vim25.RuntimeFaultFaultMsg;

@RestControllerAdvice
public class ControllerExceptionHandler {
	
	@ExceptionHandler(value = {UnknownHostException.class})
	  public ResponseEntity unknownHostException(Exception ex) {
		return new ResponseEntity<>("Unknown vCenter Server address", HttpStatus.FORBIDDEN);
	  }
	
	@ExceptionHandler(value = {InvalidLoginFaultMsg.class})
	  public ResponseEntity invalidCredentialsException(Exception ex) {
		return new ResponseEntity<>("Incorrect username or password", HttpStatus.FORBIDDEN);
	  }
	
	@ExceptionHandler(value = {KeyManagementException.class, NoSuchAlgorithmException.class, RuntimeFaultFaultMsg.class, InvalidLocaleFaultMsg.class})
	  public ResponseEntity generalLoginException(Exception ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
	  }

}
