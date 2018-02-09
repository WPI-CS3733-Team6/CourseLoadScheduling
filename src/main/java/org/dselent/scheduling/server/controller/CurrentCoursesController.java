package org.dselent.scheduling.server.controller;

import java.util.Map;

import org.dselent.scheduling.server.requests.CurrentCourses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@RequestMapping("/currentCourses")
public interface CurrentCoursesController {

	 @RequestMapping(method=RequestMethod.POST, value=CurrentCourses.REQUEST_NAME)
	 public ResponseEntity<String> currentCourses (@RequestBody Map<String,String> request) throws Exception;
}
