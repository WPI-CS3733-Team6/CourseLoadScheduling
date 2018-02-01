package org.dselent.scheduling.server.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.model.InstructorCourseLinkCart;

public class InstructorCourseLinkCartExtractor extends Extractor<List<InstructorCourseLinkCart>> {
	
	@Override
	public List<InstructorCourseLinkCart> extractData(ResultSet rs) throws SQLException {
		
		List<InstructorCourseLinkCart> resultList = new ArrayList<>();
		
		while(rs.next()) {
			
			InstructorCourseLinkCart result = new InstructorCourseLinkCart();
			
			result.setId(rs.getInt(InstructorCourseLinkCart.getColumnName(InstructorCourseLinkCart.Columns.ID)));
			
			if(rs.wasNull()) {
				result.setId(null);
			}
			
			result.setInstructor_id(rs.getInt(InstructorCourseLinkCart.getColumnName(InstructorCourseLinkCart.Columns.INSTRUCTOR_ID)));
			result.setSection_id(rs.getInt(InstructorCourseLinkCart.getColumnName(InstructorCourseLinkCart.Columns.SECTION_ID)));
			
			if(rs.wasNull()) {
				result.setInstructor_id(null);
				result.setSection_id(null);
			}
			
			result.setStatus(rs.getInt(InstructorCourseLinkCart.getColumnName(InstructorCourseLinkCart.Columns.STATUS)));
			
			result.setCreated_at(rs.getTimestamp(InstructorCourseLinkCart.getColumnName(InstructorCourseLinkCart.Columns.CREATED_AT)));
			result.setUpdated_at(rs.getTimestamp(InstructorCourseLinkCart.getColumnName(InstructorCourseLinkCart.Columns.UPDATED_AT)));
			
			resultList.add(result);
		}
		
		return resultList;
	}
}