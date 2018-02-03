package org.dselent.scheduling.server.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.model.ViewCourseSummaries;

public class ViewCourseSummariesExtractor extends Extractor<List<ViewCourseSummaries>>
{
	@Override
	public List<ViewCourseSummaries> extractData(ResultSet rs) throws SQLException
	{
		List<ViewCourseSummaries> resultList = new ArrayList<>();

		while(rs.next())
		{
			ViewCourseSummaries result = new ViewCourseSummaries();

			result.setMeetingDays(rs.getString(ViewCourseSummaries.getColumnName(ViewCourseSummaries.Columns.MEETING_DAYS)));
			result.setCourseName(rs.getString(ViewCourseSummaries.getColumnName(ViewCourseSummaries.Columns.COURSE_NAME)));
			result.setCourseNum(rs.getString(ViewCourseSummaries.getColumnName(ViewCourseSummaries.Columns.COURSE_NUM)));
		
			resultList.add(result);
		}
			
		return resultList;
	}

}