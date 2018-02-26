package org.dselent.scheduling.server.controller.impl;

import java.util.HashMap;
import java.util.Map;

import org.dselent.scheduling.server.controller.CourseController;
import org.dselent.scheduling.server.dto.CourseDto;
import org.dselent.scheduling.server.dto.CourseInstanceDto;
import org.dselent.scheduling.server.dto.CourseInstanceListDto;
import org.dselent.scheduling.server.dto.CourseListDto;
import org.dselent.scheduling.server.dto.CourseSectionDto;
import org.dselent.scheduling.server.miscellaneous.JsonResponseCreator;
import org.dselent.scheduling.server.requests.CourseCreate;
import org.dselent.scheduling.server.requests.CourseDetails;
import org.dselent.scheduling.server.requests.CourseDetailsAddToCart;
import org.dselent.scheduling.server.requests.CourseEdit;
import org.dselent.scheduling.server.requests.CourseInstanceCreate;
import org.dselent.scheduling.server.requests.CourseInstanceEdit;
import org.dselent.scheduling.server.requests.CourseSearch;
import org.dselent.scheduling.server.requests.CourseSectionCreate;
import org.dselent.scheduling.server.requests.CourseSectionEdit;
import org.dselent.scheduling.server.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class CourseControllerImpl implements CourseController{
	
	@Autowired
	private CourseService courseService;
	
	@Override
	public ResponseEntity<String> courses(@RequestBody Map<String, String> request) throws Exception {
		String response;
		
		CourseListDto courseList = courseService.courses();
		
		Map<String, Object> keys = new HashMap<String, Object>();
		
		keys.put("id", courseList.getId());
		keys.put("courseName", courseList.getCourse_name());
		keys.put("courseNum", courseList.getCourse_num());
		keys.put("courseDesc", courseList.getCourse_description());
		keys.put("level", courseList.getLevel());
		keys.put("type", courseList.getType());
		keys.put("instanceNo", courseList.getInstanceNo());

		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, keys);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<String> courseDetails(@RequestBody Map<String,String> request) throws Exception {
		String response;
		
		Integer courseId = Integer.parseInt(request.get(CourseDetails.getBodyName(CourseDetails.BodyKey.COURSE_ID)));
		CourseDto course = courseService.getCourse(courseId);
		
		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put("id", course.getId());
		keys.put("courseName", course.getCourse_name());
		keys.put("courseNum", course.getCourse_num());
		keys.put("courseDesc", course.getCourse_description());
		keys.put("level", course.getLevel());
		keys.put("type", course.getType());
		keys.put("instanceNo", course.getInstanceNo());
		
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, keys);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<String> courseEdit(@RequestBody Map<String,String> request) throws Exception {
		String response;
		
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
				.withInstanceNo(0)
				.build();
		
		Integer changedRows = courseService.editCourse(courseDto);
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, changedRows);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<String> instanceEdit(@RequestBody Map<String,String> request) throws Exception {
		String response;
		
		String instanceTerm = request.get(CourseInstanceEdit.getBodyName(CourseInstanceEdit.BodyKey.TERM));
		Integer courseId = Integer.parseInt(request.get(CourseInstanceEdit.getBodyName(CourseInstanceEdit.BodyKey.COURSE_ID)));
		Integer instanceId = Integer.parseInt(request.get(CourseInstanceEdit.getBodyName(CourseInstanceEdit.BodyKey.ID)));
		
		CourseInstanceDto.Builder builder = CourseInstanceDto.builder();
		CourseInstanceDto instanceDto = builder.withCourse_id(courseId)
				.withId(instanceId)
				.withTerm(instanceTerm)
				.build();
		
		Integer changedRows = courseService.editInstance(instanceDto);
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, changedRows);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> sectionEdit(@RequestBody Map<String, String> request) throws Exception {
		String response;
		
		Integer expected_pop = Integer.parseInt(request.get(CourseSectionEdit.getBodyName(CourseSectionEdit.BodyKey.EXPECTED_POP)));
		Integer sectionId = Integer.parseInt(request.get(CourseSectionEdit.getBodyName(CourseSectionEdit.BodyKey.ID)));
		Integer instanceId = Integer.parseInt(request.get(CourseSectionEdit.getBodyName(CourseSectionEdit.BodyKey.INSTANCE_ID)));
		
		CourseSectionDto.Builder builder = CourseSectionDto.builder();
		CourseSectionDto sectionDto = builder.withExpected_pop(expected_pop)
				.withId(sectionId)
				.withInstance_id(instanceId)
				.build();
		
		Integer changedRows = courseService.editSection(sectionDto);
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, changedRows);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> courseCreate (@RequestBody Map<String,String> request) throws Exception {
		String response;
		
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
				.build();
		
		Integer changedRows = courseService.createCourse(courseDto);
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, changedRows);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<String> courseInstanceCreate (@RequestBody Map<String,String> request) throws Exception {
		String response;
		
		Integer courseId = Integer.parseInt(request.get(CourseInstanceCreate.getBodyName(CourseInstanceCreate.BodyKey.COURSE_ID)));
		String instanceTerm = request.get(CourseInstanceCreate.getBodyName(CourseInstanceCreate.BodyKey.TERM));
		
		CourseInstanceDto.Builder builder = CourseInstanceDto.builder();
		CourseInstanceDto instanceDto = builder.withCourse_id(courseId)
				.withTerm(instanceTerm)
				.build();
		
		Integer changedRows = courseService.createInstance(instanceDto);
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, changedRows);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<String> courseSectionCreate (@RequestBody Map<String,String> request) throws Exception {
		String response;
		
		Integer instanceId = Integer.parseInt(request.get(CourseSectionCreate.getBodyName(CourseSectionCreate.BodyKey.INSTANCE_ID)));
		Integer expectedPop = Integer.parseInt(request.get(CourseSectionCreate.getBodyName(CourseSectionCreate.BodyKey.EXPECTED_POP)));
		
		CourseSectionDto.Builder builder = CourseSectionDto.builder();
		CourseSectionDto sectionDto = builder.withExpected_pop(expectedPop)
				.withInstance_id(instanceId)
				.build();
		
		Integer changedRows = courseService.createSection(sectionDto);
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, changedRows);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> courseDetailsAddToCart(@RequestBody Map<String, String> request) throws Exception {
		String response = "";
		Integer filler = 0;
		
		Integer userId = Integer.parseInt(request.get(CourseDetailsAddToCart.getBodyName(CourseDetailsAddToCart.BodyKey.USER_ID)));
		Integer instanceId = Integer.parseInt(request.get(CourseDetailsAddToCart.getBodyName(CourseDetailsAddToCart.BodyKey.INSTANCE_ID)));
		
		courseService.addToCart(userId, instanceId);
		
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, filler);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> CourseSearch(@RequestBody Map<String, String> request) throws Exception {
		String response = "";
		
		String subject = request.get(CourseSearch.getBodyName(CourseSearch.BodyKey.SUBJECT));
		String term = request.get(CourseSearch.getBodyName(CourseSearch.BodyKey.TERM));
		String level = request.get(CourseSearch.getBodyName(CourseSearch.BodyKey.LEVEL));
		
		CourseInstanceListDto instanceList = courseService.SearchInstances(subject, term, level);
		
		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put("id", instanceList.getId());
		keys.put("courseId", instanceList.getCourse_id());
		keys.put("term", instanceList.getTerm());
		keys.put("sectionNum", instanceList.getSectionNo());
		
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, keys);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
}
