package org.dselent.scheduling.server.controller;

import java.util.Map;

import org.dselent.scheduling.server.requests.CourseDetails;
import org.dselent.scheduling.server.requests.Courses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/courses")
public interface CourseController {
	
	@RequestMapping(method=RequestMethod.POST, value=Courses.REQUEST_NAME)
	public ResponseEntity<String> courses (@RequestBody Map<String,String> request) throws Exception;
	
	@RequestMapping(method=RequestMethod.POST, value=CourseDetails.REQUEST_NAME)
	public ResponseEntity<String> courseDetails (@RequestBody Map<String,String> request) throws Exception;
}
