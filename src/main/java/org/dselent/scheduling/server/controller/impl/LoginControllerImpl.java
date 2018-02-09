package org.dselent.scheduling.server.controller.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dselent.scheduling.server.controller.LoginController;
import org.dselent.scheduling.server.miscellaneous.JsonResponseCreator;
import org.dselent.scheduling.server.requests.Login;
import org.dselent.scheduling.server.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class LoginControllerImpl implements LoginController{
	
	@Autowired
	private LoginService loginService;
	
	public ResponseEntity<String> login (@RequestBody Map<String,String> request) throws Exception {
		
		String response = "";
		List<Object> success = new ArrayList<Object>();
		
		String username = (request.get(Login.getBodyName(Login.BodyKey.USER_NAME)));
		String password = (request.get(Login.getBodyName(Login.BodyKey.PASSWORD)));
		
		loginService.login(username, password);
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, success);
		
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
}
