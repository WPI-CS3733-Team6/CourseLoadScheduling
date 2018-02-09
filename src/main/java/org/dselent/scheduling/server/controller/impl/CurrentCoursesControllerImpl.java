package org.dselent.scheduling.server.controller.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dselent.scheduling.server.controller.CurrentCoursesController;
import org.dselent.scheduling.server.dto.CourseDto;
import org.dselent.scheduling.server.miscellaneous.JsonResponseCreator;
import org.dselent.scheduling.server.service.CurrentCoursesService;
import org.dselent.scheduling.server.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class CurrentCoursesControllerImpl implements CurrentCoursesController{
	
	@Autowired
	private CourseService courseService;
	
	@Autowired	
	private CurrentCoursesService currentCoursesService;
	
	@Override
	public ResponseEntity<String> currentCourses (@RequestBody Map<String,String> request) throws Exception {
		
		String response = "";
		List<Object> success = new ArrayList<Object>();
		
		List <CourseDto> currentCoursesList = courseService.courses();
		success.add(currentCoursesList);
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, success);
		
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

}
