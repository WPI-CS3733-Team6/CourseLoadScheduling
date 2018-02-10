package org.dselent.scheduling.server.controller.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dselent.scheduling.server.controller.CourseController;
import org.dselent.scheduling.server.dto.CourseDto;
import org.dselent.scheduling.server.dto.CourseInstanceDto;
import org.dselent.scheduling.server.dto.CourseSectionDto;
import org.dselent.scheduling.server.miscellaneous.JsonResponseCreator;
import org.dselent.scheduling.server.requests.CourseCreate;
import org.dselent.scheduling.server.requests.CourseDetails;
import org.dselent.scheduling.server.requests.CourseEdit;
import org.dselent.scheduling.server.requests.CourseInstanceCreate;
import org.dselent.scheduling.server.requests.CourseInstanceEdit;
import org.dselent.scheduling.server.requests.CourseSectionCreate;
import org.dselent.scheduling.server.requests.CourseSectionEdit;
import org.dselent.scheduling.server.requests.Courses;
import org.dselent.scheduling.server.requests.Login;
import org.dselent.scheduling.server.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	
	@Override
	public ResponseEntity<String> courseEdit(Map<String,String> request) throws Exception {
		String response;
		List<Object> success = new ArrayList<Object>();
		
		Integer courseId = Integer.parseInt(request.get(CourseEdit.getBodyName(CourseEdit.BodyKey.COURSE_ID)));
		String courseName = request.get(CourseEdit.getBodyName(CourseEdit.BodyKey.COURSE_NAME));
		String courseNum = request.get(CourseEdit.getBodyName(CourseEdit.BodyKey.COURSE_NUM));
		Boolean courseLevel = Boolean.parseBoolean(request.get(CourseEdit.getBodyName(CourseEdit.BodyKey.LEVEL)));
		String courseType = request.get(CourseEdit.getBodyName(CourseEdit.BodyKey.TYPE));
		String courseDesc = request.get(CourseEdit.getBodyName(CourseEdit.BodyKey.COURSE_DESCRIPTION));
		
		CourseDto.Builder builder = CourseDto.builder();
		CourseDto courseDto = builder.withCourse_description(courseDesc)
				.withCourse_name(courseName)
				.withCourse_num(courseNum)
				.withLevel(courseLevel)
				.withType(courseType)
				.withId(courseId)
				.build();
		
		courseService.editCourse(courseDto);
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, success);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<String> instanceEdit(Map<String,String> request) throws Exception {
		String response;
		List<Object> success = new ArrayList<Object>();
		
		String instanceTerm = request.get(CourseInstanceEdit.getBodyName(CourseInstanceEdit.BodyKey.TERM));
		Integer courseId = Integer.parseInt(request.get(CourseInstanceEdit.getBodyName(CourseInstanceEdit.BodyKey.COURSE_ID)));
		Integer instanceId = Integer.parseInt(request.get(CourseInstanceEdit.getBodyName(CourseInstanceEdit.BodyKey.ID)));
		
		CourseInstanceDto.Builder builder = CourseInstanceDto.builder();
		CourseInstanceDto instanceDto = builder.withCourse_id(courseId)
				.withId(instanceId)
				.withTerm(instanceTerm)
				.build();
		
		courseService.editInstance(instanceDto);
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, success);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> sectionEdit(Map<String, String> request) throws Exception {
		String response;
		List<Object> success = new ArrayList<Object>();
		
		Integer expected_pop = Integer.parseInt(request.get(CourseSectionEdit.getBodyName(CourseSectionEdit.BodyKey.EXPECTED_POP)));
		Integer sectionId = Integer.parseInt(request.get(CourseSectionEdit.getBodyName(CourseSectionEdit.BodyKey.ID)));
		Integer instanceId = Integer.parseInt(request.get(CourseSectionEdit.getBodyName(CourseSectionEdit.BodyKey.INSTANCE_ID)));
		
		CourseSectionDto.Builder builder = CourseSectionDto.builder();
		CourseSectionDto sectionDto = builder.withExpected_pop(expected_pop)
				.withId(sectionId)
				.withInstance_id(instanceId)
				.build();
		
		courseService.editSection(sectionDto);
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, success);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	public ResponseEntity<String> courseCreate (@RequestBody Map<String,String> request) throws Exception {
		String response;
		List<Object> success = new ArrayList<Object>();
		
		Integer courseId = Integer.parseInt(request.get(CourseCreate.getBodyName(CourseCreate.BodyKey.COURSE_ID)));
		String courseName = request.get(CourseCreate.getBodyName(CourseCreate.BodyKey.COURSE_NAME));
		String courseNum = request.get(CourseCreate.getBodyName(CourseCreate.BodyKey.COURSE_NUM));
		Boolean courseLevel = Boolean.parseBoolean(request.get(CourseCreate.getBodyName(CourseCreate.BodyKey.LEVEL)));
		String courseType = request.get(CourseCreate.getBodyName(CourseCreate.BodyKey.TYPE));
		String courseDesc = request.get(CourseCreate.getBodyName(CourseCreate.BodyKey.COURSE_DESCRIPTION));
		
		CourseDto.Builder builder = CourseDto.builder();
		CourseDto courseDto = builder.withCourse_description(courseDesc)
				.withCourse_name(courseName)
				.withCourse_num(courseNum)
				.withLevel(courseLevel)
				.withType(courseType)
				.withId(courseId)
				.build();
		
		courseService.createCourse(courseDto);
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, success);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
	public ResponseEntity<String> courseInstanceCreate (@RequestBody Map<String,String> request) throws Exception {
		String response;
		List<Object> success = new ArrayList<Object>();
		
		Integer courseId = Integer.parseInt(request.get(CourseInstanceCreate.getBodyName(CourseInstanceCreate.BodyKey.COURSE_ID)));
		String instanceTerm = request.get(CourseInstanceCreate.getBodyName(CourseInstanceCreate.BodyKey.TERM));
		
		CourseInstanceDto.Builder builder = CourseInstanceDto.builder();
		CourseInstanceDto instanceDto = builder.withCourse_id(courseId)
				.withTerm(instanceTerm)
				.build();
		
		courseService.createInstance(instanceDto);
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, success);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
	public ResponseEntity<String> courseSectionCreate (@RequestBody Map<String,String> request) throws Exception {
		String response;
		List<Object> success = new ArrayList<Object>();
		
		Integer instanceId = Integer.parseInt(request.get(CourseSectionCreate.getBodyName(CourseSectionCreate.BodyKey.INSTANCE_ID)));
		Integer expectedPop = Integer.parseInt(request.get(CourseSectionCreate.getBodyName(CourseSectionCreate.BodyKey.EXPECTED_POP)));
		
		CourseSectionDto.Builder builder = CourseSectionDto.builder();
		CourseSectionDto sectionDto = builder.withExpected_pop(expectedPop)
				.withInstance_id(instanceId)
				.build();
		
		courseService.createSection(sectionDto);
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, success);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
}
