package org.dselent.scheduling.server.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.model.CourseSchedule;

public class CourseScheduleExtractor extends Extractor<List<CourseSchedule>> {

	@Override
	public List<CourseSchedule> extractData(ResultSet rs) throws SQLException {
		
		List<CourseSchedule> resultList = new ArrayList<>();
		
		while(rs.next()) {
			
			CourseSchedule result = new CourseSchedule();
			
			result.setId(rs.getInt(CourseSchedule.getColumnName(CourseSchedule.Columns.ID)));
			result.setSectionId(rs.getInt(CourseSchedule.getColumnName(CourseSchedule.Columns.SECTION_ID)));
			
			if(rs.wasNull()) {
				result.setId(null);
				result.setSectionId(null);
			}
			
			result.setType(rs.getString(CourseSchedule.getColumnName(CourseSchedule.Columns.LECTURE_TYPE)));
			result.setMeetingDays(rs.getString(CourseSchedule.getColumnName(CourseSchedule.Columns.MEETING_DAYS)));
			result.setTimeStart(rs.getInt(CourseSchedule.getColumnName(CourseSchedule.Columns.TIME_START)));
			result.setTimeEnd(rs.getInt(CourseSchedule.getColumnName(CourseSchedule.Columns.TIME_END)));
			
			result.setCreatedAt(rs.getTimestamp(CourseSchedule.getColumnName(CourseSchedule.Columns.CREATED_AT)));
			result.setUpdatedAt(rs.getTimestamp(CourseSchedule.getColumnName(CourseSchedule.Columns.UPDATED_AT)));
			
			resultList.add(result);
		}
		
		return resultList;
	}
}