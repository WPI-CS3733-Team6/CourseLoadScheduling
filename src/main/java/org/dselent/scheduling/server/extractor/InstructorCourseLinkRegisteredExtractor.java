package org.dselent.scheduling.server.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.model.InstructorCourseLinkRegistered;

public class InstructorCourseLinkRegisteredExtractor extends Extractor<List<InstructorCourseLinkRegistered>> {
	
	@Override
	public List<InstructorCourseLinkRegistered> extractData(ResultSet rs) throws SQLException {
		
		List<InstructorCourseLinkRegistered> resultList = new ArrayList<>();
		
		while(rs.next()) {
			
			InstructorCourseLinkRegistered result = new InstructorCourseLinkRegistered();
			
			result.setId(rs.getInt(InstructorCourseLinkRegistered.getColumnName(InstructorCourseLinkRegistered.Columns.ID)));
			
			if(rs.wasNull()) {
				result.setId(null);
			}
			
			result.setInstructor_id(rs.getInt(InstructorCourseLinkRegistered.getColumnName(InstructorCourseLinkRegistered.Columns.INSTRUCTOR_ID)));
			result.setSection_id(rs.getInt(InstructorCourseLinkRegistered.getColumnName(InstructorCourseLinkRegistered.Columns.SECTION_ID)));
			
			if(rs.wasNull()) {
				result.setInstructor_id(null);
				result.setSection_id(null);
			}
			
			result.setDeleted(rs.getBoolean(InstructorCourseLinkRegistered.getColumnName(InstructorCourseLinkRegistered.Columns.DELETED)));
			
			result.setCreated_at(rs.getTimestamp(InstructorCourseLinkRegistered.getColumnName(InstructorCourseLinkRegistered.Columns.CREATED_AT)));
			result.setUpdated_at(rs.getTimestamp(InstructorCourseLinkRegistered.getColumnName(InstructorCourseLinkRegistered.Columns.UPDATED_AT)));
			
			resultList.add(result);
		}
		
		return resultList;
	}
}