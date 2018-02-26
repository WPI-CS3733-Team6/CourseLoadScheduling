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
			
			result.setInstanceId(rs.getInt(CourseSectionsHistory.getColumnName(CourseSectionsHistory.Columns.SECTION_ID)));
			result.setInstanceId(rs.getInt(CourseSectionsHistory.getColumnName(CourseSectionsHistory.Columns.INSTANCE_ID)));
			result.setExpectedPop(rs.getInt(CourseSectionsHistory.getColumnName(CourseSectionsHistory.Columns.EXPECTED_POP)));
			
			if(rs.wasNull()) {
				result.setExpectedPop(null);
			}
			result.setUpdatedAt(rs.getTimestamp(CourseSectionsHistory.getColumnName(CourseSectionsHistory.Columns.UPDATED_AT)));
			
			resultList.add(result);
		}
		
		return resultList;
	}
}