package org.dselent.scheduling.server.controller.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dselent.scheduling.server.controller.LoginController;
import org.dselent.scheduling.server.miscellaneous.JsonResponseCreator;
import org.dselent.scheduling.server.requests.Home;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
public class HomeControllerImpl implements LoginController{

	//@Autowired
	//private HomeService homeService;

	public ResponseEntity<String> login (@RequestBody Map<String,String> request) throws JsonProcessingException, SQLException {

		String response = "";
		List<Object> success = new ArrayList<Object>();

		String user_id = (request.get(Home.getBodyName(Home.BodyKey.USER_ID)));

		//homeService.load(user_id);
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, success);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	public ResponseEntity<String> HomeHandleMessage (@RequestBody Map<String,String> request) throws JsonProcessingException, SQLException {

		String response = "";
		List<Object> success = new ArrayList<Object>();

		String sender_id = (request.get(Home.getBodyName(Home.BodyKey.USER_ID)));

		//homeService.load(user_id);
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, success);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
	public ResponseEntity<String> ReportProblem (@RequestBody Map<String,String> request) throws JsonProcessingException, SQLException {

		String response = "";
		List<Object> success = new ArrayList<Object>();

		String sender_id = (request.get(Home.getBodyName(Home.BodyKey.USER_ID)));

		//homeService.load(user_id);
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, success);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
}