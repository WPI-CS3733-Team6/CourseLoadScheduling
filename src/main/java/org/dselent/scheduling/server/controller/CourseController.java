package org.dselent.scheduling.server.controller;

import java.util.Map;

import org.dselent.scheduling.server.requests.CourseSearch;
import org.dselent.scheduling.server.requests.CourseCreate;
import org.dselent.scheduling.server.requests.CourseDetails;
import org.dselent.scheduling.server.requests.CourseDetailsAddToCart;
import org.dselent.scheduling.server.requests.CourseEdit;
import org.dselent.scheduling.server.requests.CourseInstanceCreate;
import org.dselent.scheduling.server.requests.CourseInstanceEdit;
import org.dselent.scheduling.server.requests.CourseSectionCreate;
import org.dselent.scheduling.server.requests.CourseSectionEdit;
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
	
	@RequestMapping(method=RequestMethod.POST, value=CourseEdit.REQUEST_NAME)
	public ResponseEntity<String> courseEdit (@RequestBody Map<String,String> request) throws Exception;
	
	@RequestMapping(method=RequestMethod.POST, value=CourseInstanceEdit.REQUEST_NAME)
	public ResponseEntity<String> instanceEdit (@RequestBody Map<String,String> request) throws Exception;
	
	@RequestMapping(method=RequestMethod.POST, value=CourseSectionEdit.REQUEST_NAME)
	public ResponseEntity<String> sectionEdit (@RequestBody Map<String,String> request) throws Exception;
	
	@RequestMapping(method=RequestMethod.POST, value=CourseCreate.REQUEST_NAME)
	public ResponseEntity<String> courseCreate (@RequestBody Map<String,String> request) throws Exception;
	
	@RequestMapping(method=RequestMethod.POST, value=CourseInstanceCreate.REQUEST_NAME)
	public ResponseEntity<String> courseInstanceCreate (@RequestBody Map<String,String> request) throws Exception;
	
	@RequestMapping(method=RequestMethod.POST, value=CourseSectionCreate.REQUEST_NAME)
	public ResponseEntity<String> courseSectionCreate (@RequestBody Map<String,String> request) throws Exception;
	
	@RequestMapping(method=RequestMethod.POST, value=CourseDetailsAddToCart.REQUEST_NAME)
	public ResponseEntity<String> courseDetailsAddToCart (@RequestBody Map<String,String> request) throws Exception;
	
	@RequestMapping(method=RequestMethod.POST, value=CourseSearch.REQUEST_NAME)
	public ResponseEntity<String> CourseSearch (@RequestBody Map<String,String> request) throws Exception;
}
