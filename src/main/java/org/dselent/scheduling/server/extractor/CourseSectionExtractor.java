package org.dselent.scheduling.server.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.model.CourseSection;

public class CourseSectionExtractor extends Extractor<List<CourseSection>> {
	
	@Override
	public List<CourseSection> extractData(ResultSet rs) throws SQLException {
		
		List<CourseSection> resultList = new ArrayList<>();
		
		while(rs.next()) {
			
			CourseSection result = new CourseSection();
			
			result.setId(rs.getInt(CourseSection.getColumnName(CourseSection.Columns.ID)));
			
			if(rs.wasNull()) {
				result.setId(null);
			}
			
			result.setInstanceId(rs.getInt(CourseSection.getColumnName(CourseSection.Columns.INSTANCE_ID)));
			result.setSectionNum(rs.getInt(CourseSection.getColumnName(CourseSection.Columns.SECTION_NUM)));
			result.setExpectedPop(rs.getInt(CourseSection.getColumnName(CourseSection.Columns.EXPECTED_POP)));
			
			if(rs.wasNull()) {
				result.setSectionNum(null);
				result.setExpectedPop(null);
			}
			
			result.setCreatedAt(rs.getTimestamp(CourseSection.getColumnName(CourseSection.Columns.CREATED_AT)));
			result.setUpdatedAt(rs.getTimestamp(CourseSection.getColumnName(CourseSection.Columns.UPDATED_AT)));
			
			resultList.add(result);
		}
		
		return resultList;
	}
}