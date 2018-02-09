package org.dselent.scheduling.server.controller;

import java.util.Map;

import org.dselent.scheduling.server.requests.RegistrationCart;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface RegistrationCartController {
	
	 @RequestMapping(method=RequestMethod.POST, value=RegistrationCart.REQUEST_NAME)
	 public ResponseEntity<String> login (@RequestBody Map<String,String> request) throws Exception;
	
}
