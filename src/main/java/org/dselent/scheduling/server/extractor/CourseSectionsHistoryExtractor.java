package org.dselent.scheduling.server.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.model.CourseSectionsHistory;

public class CourseSectionsHistoryExtractor extends Extractor<List<CourseSectionsHistory>> {
	
	@Override
	public List<CourseSectionsHistory> extractData(ResultSet rs) throws SQLException {
		
		List<CourseSectionsHistory> resultList = new ArrayList<>();
		
		while(rs.next()) {
			
			CourseSectionsHistory result = new CourseSectionsHistory();
			
			result.setId(rs.getInt(CourseSectionsHistory.getColumnName(CourseSectionsHistory.Columns.ID)));
			
			if(rs.wasNull()) {
				result.setId(null);
			}
			
			result.setCourse_id(rs.getString(CourseSectionsHistory.getColumnName(CourseSectionsHistory.Columns.COURSE_ID)));
			result.setCourse_name(rs.getString(CourseSectionsHistory.getColumnName(CourseSectionsHistory.Columns.COURSE_NAME)));
			result.setCourse_num(rs.getString(CourseSectionsHistory.getColumnName(CourseSectionsHistory.Columns.COURSE_NUM)));
			
			result.setSection_num(rs.getInt(CourseSectionsHistory.getColumnName(CourseSectionsHistory.Columns.SECTION_NUM)));
			result.setExpected_pop(rs.getInt(CourseSectionsHistory.getColumnName(CourseSectionsHistory.Columns.EXPECTED_POP)));
			
			if(rs.wasNull()) {
				result.setSection_num(null);
				result.setExpected_pop(null);
			}
			
			result.setTerm(rs.getString(CourseSectionsHistory.getColumnName(CourseSectionsHistory.Columns.TERM)));
			
			result.setCreatedAt(rs.getTimestamp(CourseSectionsHistory.getColumnName(CourseSectionsHistory.Columns.CREATED_AT)));
			result.setUpdatedAt(rs.getTimestamp(CourseSectionsHistory.getColumnName(CourseSectionsHistory.Columns.UPDATED_AT)));
			
			resultList.add(result);
		}
		
		return resultList;
	}
}