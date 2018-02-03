package org.dselent.scheduling.server.model;

import java.sql.JDBCType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dselent.scheduling.server.model.CourseSchedule.Columns;

public class ViewCourseScheduleInformation extends Model{
	
	//table name
	public static final String TABLE_NAME = "view_course_schedule_info";
	
	//column names
	public static enum Columns
	{
		MEETING_DAYS,
		COURSE_NUM,
		COURSE_NAME,
		TIME_START,
		TIME_END,
		DEPT_NAME
	}
	
	// enum list
	private static final List<Columns> COLUMN_LIST = new ArrayList<>();
	
	// type mapping
	private static final Map<Columns, JDBCType> COLUMN_TYPE_MAP = new HashMap<>();
	
	static
	{
		for(Columns key : Columns.values())
		{
			COLUMN_LIST.add(key);
		}
		
		COLUMN_TYPE_MAP.put(Columns.MEETING_DAYS, JDBCType.VARCHAR);
		COLUMN_TYPE_MAP.put(Columns.COURSE_NUM, JDBCType.VARCHAR);
		COLUMN_TYPE_MAP.put(Columns.COURSE_NAME, JDBCType.VARCHAR);
		COLUMN_TYPE_MAP.put(Columns.TIME_START, JDBCType.INTEGER);
		COLUMN_TYPE_MAP.put(Columns.TIME_END, JDBCType.INTEGER);
		COLUMN_TYPE_MAP.put(Columns.DEPT_NAME, JDBCType.VARCHAR);
	};
	
	// attributes
	

	private String courseNum;
	private String courseName;
	private String meetingDays;
	private String deptName;
	private Integer timeStart;
	private Integer timeEnd;
	
	
	public static JDBCType getColumnType(Columns column)
	{
		return COLUMN_TYPE_MAP.get(column);
	}
	
	public static String getColumnName(Columns column)
	{
		return column.toString().toLowerCase();
	}
	
	public static List<String> getColumnNameList()
	{
		List<String> columnNameList = new ArrayList<>();
		
		for(Columns column : COLUMN_LIST)
		{
			columnNameList.add(getColumnName(column));
		}
		
		return columnNameList;
	}
	
	public String getMeetingDays() {
		return meetingDays;
	}
	public void setMeetingDays(String meetingDays) {
		this.meetingDays = meetingDays;
	}
	public String getCourseNum() {
		return courseNum;
	}
	public void setCourseNum(String courseNum) {
		this.courseNum = courseNum;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public Integer getTimeStart() {
		return timeStart;
	}
	public void setTimeStart(Integer timeStart) {
		this.timeStart = timeStart;
	}
	public Integer getTimeEnd() {
		return timeEnd;
	}
	public void setTimeEnd(Integer timeEnd) {
		this.timeEnd = timeEnd;
	}
	public Integer getDeptName() {
		return timeEnd;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((courseNum == null) ? 0 : courseNum.hashCode());
		result = prime * result + ((meetingDays == null) ? 0 : meetingDays.hashCode());
		result = prime * result + ((courseName == null) ? 0 : courseName.hashCode());
		result = prime * result + ((deptName == null) ? 0 : deptName.hashCode());
		result = prime * result + ((timeStart == null) ? 0 : timeStart.hashCode());
		result = prime * result + ((timeEnd == null) ? 0 : timeEnd.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ViewCourseScheduleInformation other = (ViewCourseScheduleInformation) obj;
		if (courseNum == null) {
			if (other.courseNum != null)
				return false;
		} else if (!courseNum.equals(other.courseNum))
			return false;
		if (courseName == null) {
			if (other.courseName != null)
				return false;
		} else if (!courseName.equals(other.courseName))
			return false;
		if (meetingDays == null) {
			if (other.meetingDays != null)
				return false;
		} else if (!meetingDays.equals(other.meetingDays))
			return false;
		if (deptName == null) {
			if (other.deptName != null)
				return false;
		} else if (!deptName.equals(other.deptName))
			return false;
		if (timeStart == null) {
			if (other.timeStart != null)
				return false;
		} else if (!timeStart.equals(other.timeStart))
			return false;
		if (timeEnd == null) {
			if (other.timeEnd != null)
				return false;
		} else if (!timeEnd.equals(other.timeEnd))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "CourseSection [courseName=" + courseName + ", courseNum=" + courseNum
				+ ", meetingDays=" + meetingDays + ", deptName=" + deptName + 
				", timeStart=" + timeStart + ", timeEnd=" + timeEnd + "]";
	}
	
	
}
