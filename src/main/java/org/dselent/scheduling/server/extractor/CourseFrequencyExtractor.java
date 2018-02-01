package org.dselent.scheduling.server.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.model.CourseFrequency;

public class CourseFrequencyExtractor extends Extractor<List<CourseFrequency>> {
	
	@Override
	public List<CourseFrequency> extractData(ResultSet rs) throws SQLException {
		
		List<CourseFrequency> resultList = new ArrayList<>();
		
		while(rs.next()) {
			
			CourseFrequency result = new CourseFrequency();
			
			result.setId(rs.getInt(CourseFrequency.getColumnName(CourseFrequency.Columns.ID)));
			result.setCourseId(rs.getInt(CourseFrequency.getColumnName(CourseFrequency.Columns.COURSE_ID)));
			
			if(rs.wasNull()) {
				result.setId(null);
				result.setCourseId(null);
			}
			
			result.setReqFrequency(rs.getString(CourseFrequency.getColumnName(CourseFrequency.Columns.REQ_FREQUENCY)));
			result.setCreatedAt(rs.getTimestamp(CourseFrequency.getColumnName(CourseFrequency.Columns.CREATED_AT)));
			result.setUpdatedAt(rs.getTimestamp(CourseFrequency.getColumnName(CourseFrequency.Columns.UPDATED_AT)));
			
			resultList.add(result);
		}
		
		return resultList;
	}	
}