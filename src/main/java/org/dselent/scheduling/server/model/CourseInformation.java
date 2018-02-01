package org.dselent.scheduling.server.model;

import java.sql.JDBCType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseInformation extends Model {
	
	//table name
	public static final String TABLE_NAME = "course_information";
	
	//column names
	public static enum Columns {
		ID,
		COURSE_NUM,
		COURSE_NAME,
		TYPE,
		LEVEL,
		DEPT,
		NUM_SECTIONS,
		REQ_FREQUENCY
	}
	
	//enum list
	private static final List<Columns> COLUMN_LIST = new ArrayList<>();
	
	// type mapping
	private static final Map<Columns, JDBCType> COLUMN_TYPE_MAP = new HashMap<>();
	
	static
	{
		for(Columns key: Columns.values()) {
			COLUMN_LIST.add(key);
		}
		
		COLUMN_TYPE_MAP.put(Columns.ID, JDBCType.INTEGER);
		COLUMN_TYPE_MAP.put(Columns.COURSE_NUM, JDBCType.VARCHAR);
		COLUMN_TYPE_MAP.put(Columns.COURSE_NAME, JDBCType.VARCHAR);
		COLUMN_TYPE_MAP.put(Columns.TYPE, JDBCType.VARCHAR);
		COLUMN_TYPE_MAP.put(Columns.LEVEL, JDBCType.BOOLEAN);
		COLUMN_TYPE_MAP.put(Columns.DEPT, JDBCType.VARCHAR);
		COLUMN_TYPE_MAP.put(Columns.NUM_SECTIONS, JDBCType.INTEGER);
		COLUMN_TYPE_MAP.put(Columns.REQ_FREQUENCY, JDBCType.INTEGER);
	};
	
	//attributes
	
	private Integer id;
	private String courseNum;
	private String courseName;
	private String type;
	private Boolean level;
	private String dept;
	private Integer numSections;
	private Integer reqFrequency;
	
	//methods
	
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
	public String getCourse_num() {
		return courseNum;
	}
	public void setCourse_num(String course_num) {
		this.courseNum = course_num;
	}
	public String getCourse_name() {
		return courseName;
	}
	public void setCourse_name(String course_name) {
		this.courseName = course_name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Boolean getLevel() {
		return level;
	}
	public void setLevel(Boolean level) {
		this.level = level;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public Integer getNum_sections() {
		return numSections;
	}
	public void setNum_sections(Integer num_sections) {
		this.numSections = num_sections;
	}
	public Integer getReq_frequency() {
		return reqFrequency;
	}
	public void setReq_frequency(Integer req_frequency) {
		this.reqFrequency = req_frequency;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((courseName == null) ? 0 : courseName.hashCode());
		result = prime * result + ((courseNum == null) ? 0 : courseNum.hashCode());
		result = prime * result + ((dept == null) ? 0 : dept.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((level == null) ? 0 : level.hashCode());
		result = prime * result + ((numSections == null) ? 0 : numSections.hashCode());
		result = prime * result + ((reqFrequency == null) ? 0 : reqFrequency.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		CourseInformation other = (CourseInformation) obj;
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
		if (dept == null) {
			if (other.dept != null)
				return false;
		} else if (!dept.equals(other.dept))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (level == null) {
			if (other.level != null)
				return false;
		} else if (!level.equals(other.level))
			return false;
		if (numSections == null) {
			if (other.numSections != null)
				return false;
		} else if (!numSections.equals(other.numSections))
			return false;
		if (reqFrequency == null) {
			if (other.reqFrequency != null)
				return false;
		} else if (!reqFrequency.equals(other.reqFrequency))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "CourseInformation [id=" + id + ", course_num=" + courseNum + ", course_name=" + courseName + ", type="
				+ type + ", level=" + level + ", dept=" + dept + ", num_sections=" + numSections + ", req_frequency="
				+ reqFrequency + "]";
	}
	
}
