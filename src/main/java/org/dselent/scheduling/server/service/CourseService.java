package org.dselent.scheduling.server.service;

import java.sql.SQLException;
import java.util.ArrayList;

import org.dselent.scheduling.server.dto.CourseDto;
import org.springframework.stereotype.Service;

@Service
public interface CourseService {
	//Get a list of all courses in the system
	public ArrayList<CourseDto> courses() throws SQLException, Exception;
	//Get the details for the specified course
	public CourseDto findById(Integer courseId) throws Exception;
}