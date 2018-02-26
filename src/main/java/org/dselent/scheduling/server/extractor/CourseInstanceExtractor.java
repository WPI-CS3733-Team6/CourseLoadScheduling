package org.dselent.scheduling.server.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.model.CourseInstance;

public class CourseInstanceExtractor extends Extractor<List<CourseInstance>>{
	@Override
	public List<CourseInstance> extractData(ResultSet rs) throws SQLException {
		
		List<CourseInstance> resultList = new ArrayList<>();
		
		while(rs.next()) {
			
			CourseInstance result = new CourseInstance();
			
			result.setId(rs.getInt(CourseInstance.getColumnName(CourseInstance.Columns.ID)));
			
			result.setCourseId(rs.getInt(CourseInstance.getColumnName(CourseInstance.Columns.COURSE_ID)));
			result.setTerm(rs.getString(CourseInstance.getColumnName(CourseInstance.Columns.TERM)));
			
			if(rs.wasNull()) {
				result.setTerm(null);
			}
			
			result.setDeleted(rs.getBoolean(CourseInstance.getColumnName(CourseInstance.Columns.DELETED)));
			result.setCreatedAt(rs.getTimestamp(CourseInstance.getColumnName(CourseInstance.Columns.CREATED_AT)));
			result.setUpdatedAt(rs.getTimestamp(CourseInstance.getColumnName(CourseInstance.Columns.UPDATED_AT)));
			
			resultList.add(result);
		}
		
		return resultList;
	}
}
