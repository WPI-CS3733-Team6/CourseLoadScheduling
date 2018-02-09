package org.dselent.scheduling.server.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.dto.CourseInstanceDto;
import org.dselent.scheduling.server.dto.CourseScheduleDto;
import org.dselent.scheduling.server.model.CourseSchedule;

public interface DetailedScheduleService {

	public List<CourseSchedule> detailedSchedule(ArrayList<CourseInstanceDto> courseInstances) throws SQLException, Exception;
}
