package org.dselent.scheduling.server.service;

import java.sql.SQLException;
import java.util.ArrayList;

import org.dselent.scheduling.server.dto.CourseDto;
import org.springframework.stereotype.Service;

@Service
public interface CourseService {
	public ArrayList<CourseDto> courses() throws SQLException, Exception;
}