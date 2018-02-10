package org.dselent.scheduling.server.controller.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dselent.scheduling.server.controller.CourseController;
import org.dselent.scheduling.server.dto.CourseDto;
import org.dselent.scheduling.server.miscellaneous.JsonResponseCreator;
import org.dselent.scheduling.server.requests.CourseDetails;
import org.dselent.scheduling.server.requests.Courses;
import org.dselent.scheduling.server.requests.Login;
import org.dselent.scheduling.server.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

public class CourseControllerImpl implements CourseController{
	
	@Autowired
	private CourseService courseService;
	
	@Override
	public ResponseEntity<String> courses(Map<String, String> request) throws Exception {
		String response;
		List<Object> success = new ArrayList<Object>();
		
		List <CourseDto> courseList = courseService.courses();
		
		success.add(courseList);
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, success);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<String> courseDetails(Map<String,String> request) throws Exception {
		String response;
		List<Object> success = new ArrayList<Object>();
		
		Integer courseId = Integer.parseInt(request.get(CourseDetails.getBodyName(CourseDetails.BodyKey.COURSE_ID)));
		CourseDto course = courseService.findById(courseId);
		
		success.add(course);
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, success);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
}
