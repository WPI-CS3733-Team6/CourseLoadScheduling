package org.dselent.scheduling.server.service;

import java.sql.SQLException;
import java.util.ArrayList;

import org.dselent.scheduling.server.dto.CourseInstanceDto;
import org.dselent.scheduling.server.dto.CourseScheduleDto;

public interface CurrentCoursesService {
	public ArrayList<CourseInstanceDto> currentCourses(Integer user_id) throws SQLException, Exception;
}
