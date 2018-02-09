package org.dselent.scheduling.server.service;

import java.sql.SQLException;
import java.util.ArrayList;

import org.dselent.scheduling.server.dto.CourseDto;

public interface CurrentCoursesService {
	public ArrayList<CourseDto> currentCourses() throws SQLException, Exception;
}
