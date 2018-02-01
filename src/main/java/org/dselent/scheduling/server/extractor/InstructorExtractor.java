package org.dselent.scheduling.server.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.model.Instructor;

public class InstructorExtractor extends Extractor<List<Instructor>>
{
	@Override
	public List<Instructor> extractData(ResultSet rs) throws SQLException
	{
		List<Instructor> resultList = new ArrayList<>();

		while(rs.next())
		{
			Instructor result = new Instructor();
			
			result.setId(rs.getInt(Instructor.getColumnName(Instructor.Columns.ID)));
			result.setUserId(rs.getInt(Instructor.getColumnName(Instructor.Columns.USER_ID)));
			result.setReqCourses(rs.getInt(Instructor.getColumnName(Instructor.Columns.REQ_COURSES)));
			
			if(rs.wasNull())
			{
				result.setId(null);
				result.setUserId(null);
				result.setReqCourses(null);
			}
		
			resultList.add(result);
		}
			
		return resultList;
	}

}
