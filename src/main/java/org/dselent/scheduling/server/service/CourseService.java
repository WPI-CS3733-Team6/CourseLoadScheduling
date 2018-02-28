package org.dselent.scheduling.server.service;

import java.sql.SQLException;
import org.dselent.scheduling.server.dto.CourseDto;
import org.dselent.scheduling.server.dto.CourseListDto;
import org.dselent.scheduling.server.dto.CourseScheduleDto;
import org.dselent.scheduling.server.dto.CourseInstanceDto;
import org.dselent.scheduling.server.dto.CourseInstanceListDto;
import org.dselent.scheduling.server.dto.CourseInstanceSearchResultsDto;
import org.dselent.scheduling.server.dto.CourseSectionDto;
import org.dselent.scheduling.server.dto.CourseSectionListDto;
import org.springframework.stereotype.Service;

@Service
public interface CourseService {
	public CourseListDto courses() throws SQLException, Exception;
	
	public Integer createCourse(CourseDto newCourse) throws Exception;
	
	public Integer editCourse(CourseDto newCourse) throws Exception;
	
	public Integer deleteCourse(Integer courseId) throws Exception;
	
	public CourseDto getCourse(Integer courseId) throws Exception;
	
	public CourseDto getCourseByNum(String courseNum) throws Exception;
	
	public Integer createInstance(CourseInstanceDto newInstance) throws Exception;
	
	public Integer editInstance(CourseInstanceDto newInstance) throws Exception;
	
	public Integer deleteInstance(Integer instanceId) throws Exception;
	
	public Integer deleteCourseInstances(Integer courseId) throws Exception;
	
	public CourseInstanceDto getInstance(Integer instanceId) throws Exception;
	
	public CourseInstanceListDto getCourseInstances(Integer courseId) throws Exception;
	
	public CourseInstanceSearchResultsDto SearchInstances(String subject, String term, String level) throws Exception;
	
	public Integer createSection(CourseSectionDto newSection, CourseScheduleDto newSchedule) throws Exception;
	
	public Integer editSection(CourseSectionDto newSection) throws Exception;
	
	public Integer deleteSection(Integer sectionId) throws Exception;
	
	public Integer deleteInstanceSections(Integer instanceId) throws Exception;
	
	public CourseSectionDto getSectionById(Integer sectionId) throws Exception;
	
	public CourseSectionListDto getInstanceSections(Integer instanceId) throws Exception;
	
	public void addToCart(Integer user_id, Integer instance_id) throws Exception;
	
	
}