package org.dselent.scheduling.server.model;

import java.sql.JDBCType;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseSectionsHistory extends Model{

	//table name
	public static final String TABLE_NAME = "course_sections_history";

	//column names
	public static enum Columns
	{
		ID,
		COURSE_ID,
		COURSE_NAME,
		COURSE_NUM,
		SECTION_NUM,
		TERM,
		EXPECTED_POP,
		CREATED_AT,
		UPDATED_AT,
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

		COLUMN_TYPE_MAP.put(Columns.ID, JDBCType.INTEGER);
		COLUMN_TYPE_MAP.put(Columns.COURSE_ID, JDBCType.INTEGER);
		COLUMN_TYPE_MAP.put(Columns.COURSE_NAME, JDBCType.VARCHAR);
		COLUMN_TYPE_MAP.put(Columns.COURSE_NUM, JDBCType.VARCHAR);
		COLUMN_TYPE_MAP.put(Columns.SECTION_NUM, JDBCType.INTEGER);
		COLUMN_TYPE_MAP.put(Columns.TERM, JDBCType.VARCHAR);
		COLUMN_TYPE_MAP.put(Columns.EXPECTED_POP, JDBCType.INTEGER);
		COLUMN_TYPE_MAP.put(Columns.CREATED_AT, JDBCType.TIMESTAMP_WITH_TIMEZONE);
		COLUMN_TYPE_MAP.put(Columns.UPDATED_AT, JDBCType.TIMESTAMP_WITH_TIMEZONE);
	};

	// attributes
	
	private Integer id;
	private String courseId;
	private String courseName;
	private String courseNum;
	private Integer sectionNum;
	private String term;
	private Integer expectedPop;
	private Timestamp createdAt;
	private Timestamp updatedAt;
	
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

	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseNum() {
		return courseNum;
	}

	public void setCourseNum(String courseNum) {
		this.courseNum = courseNum;
	}

	public Integer getSectionNum() {
		return sectionNum;
	}

	public void setSectionNum(Integer sectionNum) {
		this.sectionNum = sectionNum;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public Integer getExpectedPop() {
		return expectedPop;
	}

	public void setExpectedPop(Integer expectedPop) {
		this.expectedPop = expectedPop;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((courseId == null) ? 0 : courseId.hashCode());
		result = prime * result + ((courseName == null) ? 0 : courseName.hashCode());
		result = prime * result + ((courseNum == null) ? 0 : courseNum.hashCode());
		result = prime * result + ((expectedPop == null) ? 0 : expectedPop.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
		result = prime * result + ((sectionNum == null) ? 0 : sectionNum.hashCode());
		result = prime * result + ((term == null) ? 0 : term.hashCode());
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
		CourseSectionsHistory other = (CourseSectionsHistory) obj;
		if (courseId == null) {
			if (other.courseId != null)
				return false;
		} else if (!courseId.equals(other.courseId))
			return false;
		if (courseName == null) {
			if (other.courseName != null)
				return false;
		} else if (!courseName.equals(other.courseName))
			return false;
		if (courseNum == null) {
			if (other.courseNum != null)
				return false;
		} else if (!courseNum.equals(other.courseNum))
			return false;
		if (expectedPop == null) {
			if (other.expectedPop != null)
				return false;
		} else if (!expectedPop.equals(other.expectedPop))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		if (sectionNum == null) {
			if (other.sectionNum != null)
				return false;
		} else if (!sectionNum.equals(other.sectionNum))
			return false;
		if (term == null) {
			if (other.term != null)
				return false;
		} else if (!term.equals(other.term))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "courseSectionsHistory [id=" + id + ", courseId=" + courseId + ", courseName=" + courseName
				+ ", courseNum=" + courseNum + ", sectionNum=" + sectionNum + ", term=" + term + ", expectedPop="
				+ expectedPop + ", createdAt=" + createdAt + "updatedAt=" + updatedAt + "]";
	}
}
