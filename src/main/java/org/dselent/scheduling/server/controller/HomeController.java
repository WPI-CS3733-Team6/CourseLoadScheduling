package org.dselent.scheduling.server.controller;

import java.util.Map;

import org.dselent.scheduling.server.requests.Home;
import org.dselent.scheduling.server.requests.HomeHandleMessage;
import org.dselent.scheduling.server.requests.ReportProblem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface HomeController {

	@RequestMapping(method=RequestMethod.POST, value=Home.REQUEST_NAME)
	public ResponseEntity<String> login (@RequestBody Map<String,String> request) throws Exception;
	
	@RequestMapping(method=RequestMethod.POST, value=HomeHandleMessage.REQUEST_NAME)
	public ResponseEntity<String> homeHandleMessage (@RequestBody Map<String,String> request) throws Exception;
	
	@RequestMapping(method=RequestMethod.POST, value=ReportProblem.REQUEST_NAME)
	public ResponseEntity<String> reportProblem (@RequestBody Map<String,String> request) throws Exception;
}
