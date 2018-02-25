package org.dselent.scheduling.server.service;

import java.sql.SQLException;
import java.util.ArrayList;

import org.dselent.scheduling.server.dto.CourseInstanceDto;
import org.dselent.scheduling.server.dto.CourseScheduleDto;
import org.dselent.scheduling.server.model.CourseSchedule;
import org.springframework.stereotype.Service;

@Service
public interface CurrentCoursesService {
	public ArrayList<CourseInstanceDto> currentCourses(Integer user_id) throws SQLException, Exception;
	
	public ArrayList<CourseScheduleDto> detailedSchedule(Integer userId) throws SQLException, Exception;
	
	public CourseSchedule getScheduleFromSection(Integer section_id) throws Exception;
	
	public ArrayList<CourseInstanceDto> findCourseInstances(Integer instructor_id) throws SQLException;
}
