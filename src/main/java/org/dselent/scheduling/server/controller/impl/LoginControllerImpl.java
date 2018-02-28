package org.dselent.scheduling.server.controller.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.dselent.scheduling.server.controller.LoginController;
import org.dselent.scheduling.server.dto.UserInfoDto;
import org.dselent.scheduling.server.miscellaneous.JsonResponseCreator;
import org.dselent.scheduling.server.requests.Login;
import org.dselent.scheduling.server.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
public class LoginControllerImpl implements LoginController{
	
	@Autowired
	private LoginService loginService;
	
	public ResponseEntity<String> login (@RequestBody Map<String,String> request) throws SQLException, Exception {
		
		String response = "";
		
		String username = (request.get(Login.getBodyName(Login.BodyKey.USER_NAME)));
		String password = (request.get(Login.getBodyName(Login.BodyKey.PASSWORD)));
		
		UserInfoDto userInfo;
		
		userInfo = loginService.login(username, password);
		
		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put("userId",userInfo.getUserId());
		if (userInfo.getUserId() != 0) {
			keys.put("email",userInfo.getEmail());
			keys.put("phoneNum",userInfo.getPhoneNum());
			keys.put("firstName",userInfo.getFirstName());
			keys.put("lastName",userInfo.getLastName());
			keys.put("reqCourses",userInfo.getReqCourses());
			keys.put("secondaryEmail",userInfo.getSecondaryEmail());
			keys.put("userName",userInfo.getUserName());
		}
		
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, keys);
		
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
}
 