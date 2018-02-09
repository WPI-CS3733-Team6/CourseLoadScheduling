package org.dselent.scheduling.server.controller.impl;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.miscellaneous.JsonResponseCreator;
import org.dselent.scheduling.server.controller.RegistrationCartController;
import org.dselent.scheduling.server.dto.RegistrationCartDto;
import org.springframework.http.ResponseEntity;
import org.dselent.scheduling.server.requests.RegistrationCart;
import org.dselent.scheduling.server.service.RegistrationCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

public class RegistrationCartControllerImplementation implements RegistrationCartController {
	
	@Autowired
	private RegistrationCartService registrationCartService;

	@Override
	public ResponseEntity<String> registrationCart(@RequestBody Map<String, String> request) throws Exception {
		
		String response = "";
		List<Object> success = new ArrayList<Object>();
		
		String user_id = request.get(RegistrationCart.getBodyName(RegistrationCart.BodyKey.USER_ID));
		
		List<RegistrationCartDto> cartList = registrationCartService.registrationCart(user_id);
		
		success.add(cartList);
		
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, success);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

}
