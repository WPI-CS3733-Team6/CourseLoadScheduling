package org.dselent.scheduling.server.controller.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dselent.scheduling.server.controller.HomeController;
import org.dselent.scheduling.server.dto.InboxMessageDto;
import org.dselent.scheduling.server.miscellaneous.JsonResponseCreator;
import org.dselent.scheduling.server.requests.Home;
import org.dselent.scheduling.server.requests.HomeHandleMessage;
import org.dselent.scheduling.server.requests.ReportProblem;
import org.dselent.scheduling.server.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class HomeControllerImpl implements HomeController{

	@Autowired
	private HomeService homeService;

	public ResponseEntity<String> home (@RequestBody Map<String,String> request) throws Exception {
		String response = "";

		Integer userId = Integer.parseInt(request.get(Home.getBodyName(Home.BodyKey.USER_ID)));

		List<InboxMessageDto> mailList = homeService.load(userId);
		ArrayList<String> content = new ArrayList<String>();
		ArrayList<Integer> id = new ArrayList<Integer>();
		ArrayList<Integer> senderId = new ArrayList<Integer>();
		ArrayList<Integer> status = new ArrayList<Integer>();
		ArrayList<String> subject = new ArrayList<String>();
		
		for (int i = 0; i < mailList.size(); i++) {
			InboxMessageDto message = mailList.get(i);
			content.add(message.getContent());
			id.add(message.getMessageId());
			senderId.add(message.getSenderId());
			status.add(message.getStatus());
			subject.add(message.getSubject());
		}
		
		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put("content", content);
		keys.put("id", id);
		keys.put("senderId", senderId);
		keys.put("status", status);
		keys.put("subject", subject);
		
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, keys);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> homeHandleMessage(@RequestBody Map<String, String> request) throws Exception {
		String response = "";
		Integer mailId = Integer.parseInt(request.get(HomeHandleMessage.getBodyName(HomeHandleMessage.BodyKey.MESSAGE_ID)));
		Boolean isApproved = Boolean.parseBoolean(request.get(HomeHandleMessage.getBodyName(HomeHandleMessage.BodyKey.DECISION)));

		homeService.handleMessage(mailId, isApproved);
		
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, response);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> reportProblem(@RequestBody Map<String, String> request) throws Exception {
		String response = "";
		List<Object> success = new ArrayList<Object>();

		String description = request.get(ReportProblem.getBodyName(ReportProblem.BodyKey.DESCRIPTION));
		String subject = request.get(ReportProblem.getBodyName(ReportProblem.BodyKey.SUBJECT));
		Integer senderId = Integer.parseInt(request.get(ReportProblem.getBodyName(ReportProblem.BodyKey.SENDER_ID)));
		
		homeService.reportProblem(subject, description, senderId);
		
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, success);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
}