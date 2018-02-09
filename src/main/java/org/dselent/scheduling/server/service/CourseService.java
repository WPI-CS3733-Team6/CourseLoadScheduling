package org.dselent.scheduling.server.service;

import java.sql.SQLException;
import org.springframework.stereotype.Service;

@Service
public interface CourseService {
	public Integer courses() throws SQLException, Exception;
}