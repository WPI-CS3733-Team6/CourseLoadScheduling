package org.dselent.scheduling.server.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.model.ViewCourseScheduleInformation;

public class ViewCourseScheduleInformationExtractor extends Extractor<List<ViewCourseScheduleInformation>>
{
	@Override
	public List<ViewCourseScheduleInformation> extractData(ResultSet rs) throws SQLException
	{
		List<ViewCourseScheduleInformation> resultList = new ArrayList<>();

		while(rs.next())
		{
			ViewCourseScheduleInformation result = new ViewCourseScheduleInformation();

			result.setMeetingDays(rs.getString(ViewCourseScheduleInformation.getColumnName(ViewCourseScheduleInformation.Columns.MEETING_DAYS)));
			result.setCourseName(rs.getString(ViewCourseScheduleInformation.getColumnName(ViewCourseScheduleInformation.Columns.COURSE_NAME)));
			result.setCourseNum(rs.getString(ViewCourseScheduleInformation.getColumnName(ViewCourseScheduleInformation.Columns.COURSE_NUM)));
			result.setTimeStart(rs.getInt(ViewCourseScheduleInformation.getColumnName(ViewCourseScheduleInformation.Columns.TIME_START)));
			result.setTimeEnd(rs.getInt(ViewCourseScheduleInformation.getColumnName(ViewCourseScheduleInformation.Columns.TIME_END)));
			result.setDeptName(rs.getString(ViewCourseScheduleInformation.getColumnName(ViewCourseScheduleInformation.Columns.DEPT_NAME)));
			
		
			resultList.add(result);
		}
			
		return resultList;
	}

}