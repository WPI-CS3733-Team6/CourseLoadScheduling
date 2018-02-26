package org.dselent.scheduling.server.controller.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dselent.scheduling.server.controller.RegistrationCartController;
import org.dselent.scheduling.server.dto.RegistrationCartDto;
import org.dselent.scheduling.server.miscellaneous.JsonResponseCreator;
import org.dselent.scheduling.server.requests.RegistrationCart;
import org.dselent.scheduling.server.requests.RegistrationCartRemoveCourse;
import org.dselent.scheduling.server.requests.SubmitCart;
import org.dselent.scheduling.server.service.RegistrationCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class RegistrationCartControllerImplementation implements RegistrationCartController {
	
	@Autowired
	private RegistrationCartService registrationCartService;

	@Override
	public ResponseEntity<String> registrationCart(@RequestBody Map<String, String> request) throws Exception {
		
		String response = "";
		List<Object> success = new ArrayList<Object>();
		
		Integer user_id = Integer.parseInt(request.get(RegistrationCart.getBodyName(RegistrationCart.BodyKey.USER_ID)));
		
		List<RegistrationCartDto> cartList = registrationCartService.registrationCart(user_id);
		
		success.add(cartList);
		
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, success);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<String> removeCourse (@RequestBody Map<String,String> request) throws Exception {
		
		String response = "";
		List<Object> success = new ArrayList<Object>();
		
		Integer instanceId = Integer.parseInt(request.get(RegistrationCartRemoveCourse.getBodyName(RegistrationCartRemoveCourse.BodyKey.INSTANCE_ID)));
		
		registrationCartService.removeCourse(instanceId);
		
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, success);
		return new ResponseEntity<String>(response, HttpStatus.OK);
		
	}

	@Override
	public ResponseEntity<String> submitCart (@RequestBody Map<String,String> request) throws Exception {
		
		String response = "";
		List<Object> success = new ArrayList<Object>();
		
		Integer userId = Integer.parseInt(request.get(SubmitCart.getBodyName(SubmitCart.BodyKey.USER_ID)));
		
		registrationCartService.removeCourse(userId);
		
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, success);
		return new ResponseEntity<String>(response, HttpStatus.OK);
		
	}
}
