package org.dselent.scheduling.server.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.model.CourseInformation;

public class CourseInformationExtractor extends Extractor<List<CourseInformation>> {
	
	@Override
	public List<CourseInformation> extractData(ResultSet rs) throws SQLException
	{
		List<CourseInformation> resultList = new ArrayList<>();

		while(rs.next())
		{
			CourseInformation result = new CourseInformation();
				
			result.setId(rs.getInt(CourseInformation.getColumnName(CourseInformation.Columns.ID)));

			if(rs.wasNull())
			{
				result.setId(null);
			}
			
			result.setCourseName(rs.getString(CourseInformation.getColumnName(CourseInformation.Columns.COURSE_NAME)));
			
			result.setCourseNum(rs.getString(CourseInformation.getColumnName(CourseInformation.Columns.COURSE_NUM)));
			
			result.setType(rs.getString(CourseInformation.getColumnName(CourseInformation.Columns.TYPE)));
			
			result.setLevel(rs.getBoolean(CourseInformation.getColumnName(CourseInformation.Columns.LEVEL)));

			if(rs.wasNull())
			{
				result.setLevel(null);
			}

			result.setCourseDescription(rs.getString(CourseInformation.getColumnName(CourseInformation.Columns.COURSE_DESCRIPTION)));

			if(rs.wasNull())
			{
				result.setCourseDescription(null);
			}

			resultList.add(result);
		}
			
		return resultList;
	}

	
}
