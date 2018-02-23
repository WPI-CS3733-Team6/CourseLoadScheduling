package org.dselent.scheduling.server.service;

import java.sql.SQLException;
import java.util.ArrayList;

import org.dselent.scheduling.server.dto.CourseDto;
import org.dselent.scheduling.server.dto.CourseInstanceDto;
import org.dselent.scheduling.server.dto.CourseSectionDto;
import org.springframework.stereotype.Service;

@Service
public interface CourseService {
	//Get a list of all courses in the system
	public ArrayList<CourseDto> courses() throws SQLException, Exception;
	
	//Get the details for the specified course
	public CourseDto findCourseById(Integer courseId) throws Exception;
	
	//Update the specified course with the given information
	//return the number of changed rows
	public Integer editCourse(CourseDto newCourse) throws Exception;
	
	public Integer editInstance(CourseInstanceDto newInstance) throws Exception;
	
	public Integer editSection(CourseSectionDto newSection) throws Exception;
	
	public Integer createCourse(CourseDto newCourse) throws Exception;
	
	public Integer createInstance(CourseInstanceDto newInstance) throws Exception;
	
	public Integer createSection(CourseSectionDto newSection) throws Exception;
	
	public void addToCart(Integer user_id, Integer instance_id) throws Exception;
	
	public ArrayList<CourseDto> courseSearch(String course_name, String course_num) throws Exception;
	
	public ArrayList<CourseInstanceDto> deleteCourseInstance(Integer user_id) throws SQLException;
}