package org.dselent.scheduling.server.controller;

import java.util.Map;

import org.dselent.scheduling.server.requests.RegistrationCart;
import org.dselent.scheduling.server.requests.RegistrationCartRemoveCourse;
import org.dselent.scheduling.server.requests.SubmitCart;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/registrationCart")
public interface RegistrationCartController {
	
	 @RequestMapping(method=RequestMethod.POST, value=RegistrationCart.REQUEST_NAME)
	 public ResponseEntity<String> registrationCart (@RequestBody Map<String,String> request) throws Exception;
	 
	 @RequestMapping(method=RequestMethod.POST, value=RegistrationCartRemoveCourse.REQUEST_NAME)
	 public ResponseEntity<String> removeCourse (@RequestBody Map<String,String> request) throws Exception;
	 
	 @RequestMapping(method=RequestMethod.POST, value=SubmitCart.REQUEST_NAME)
	 public ResponseEntity<String> submitCart (@RequestBody Map<String,String> request) throws Exception;
	
}
