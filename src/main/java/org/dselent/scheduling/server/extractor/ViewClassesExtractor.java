package org.dselent.scheduling.server.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.model.ViewClasses;

public class ViewClassesExtractor extends Extractor<List<ViewClasses>>
{
	@Override
	public List<ViewClasses> extractData(ResultSet rs) throws SQLException
	{
		List<ViewClasses> resultList = new ArrayList<>();

		while(rs.next())
		{
			ViewClasses result = new ViewClasses();

			result.setExpectedPop(rs.getInt(ViewClasses.getColumnName(ViewClasses.Columns.EXPECTED_POP)));
			result.setMeetingDays(rs.getString(ViewClasses.getColumnName(ViewClasses.Columns.MEETING_DAYS)));
			result.setCourseName(rs.getString(ViewClasses.getColumnName(ViewClasses.Columns.COURSE_NAME)));
			result.setCourseNum(rs.getString(ViewClasses.getColumnName(ViewClasses.Columns.COURSE_NUM)));
			result.setDeleted(rs.getBoolean(ViewClasses.getColumnName(ViewClasses.Columns.DELETED)));
		
			resultList.add(result);
		}
			
		return resultList;
	}

}