package org.dselent.scheduling.server.model;

import java.sql.JDBCType;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dselent.scheduling.server.model.User.Columns;

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
	private String course_num;
	private String course_name;
	private String type;
	private Boolean level;
	private String dept;
	private Integer num_sections;
	private Integer req_frequency;
	
	//methods
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCourse_num() {
		return course_num;
	}
	public void setCourse_num(String course_num) {
		this.course_num = course_num;
	}
	public String getCourse_name() {
		return course_name;
	}
	public void setCourse_name(String course_name) {
		this.course_name = course_name;
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
		return num_sections;
	}
	public void setNum_sections(Integer num_sections) {
		this.num_sections = num_sections;
	}
	public Integer getReq_frequency() {
		return req_frequency;
	}
	public void setReq_frequency(Integer req_frequency) {
		this.req_frequency = req_frequency;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((course_name == null) ? 0 : course_name.hashCode());
		result = prime * result + ((course_num == null) ? 0 : course_num.hashCode());
		result = prime * result + ((dept == null) ? 0 : dept.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((level == null) ? 0 : level.hashCode());
		result = prime * result + ((num_sections == null) ? 0 : num_sections.hashCode());
		result = prime * result + ((req_frequency == null) ? 0 : req_frequency.hashCode());
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
		if (course_name == null) {
			if (other.course_name != null)
				return false;
		} else if (!course_name.equals(other.course_name))
			return false;
		if (course_num == null) {
			if (other.course_num != null)
				return false;
		} else if (!course_num.equals(other.course_num))
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
		if (num_sections == null) {
			if (other.num_sections != null)
				return false;
		} else if (!num_sections.equals(other.num_sections))
			return false;
		if (req_frequency == null) {
			if (other.req_frequency != null)
				return false;
		} else if (!req_frequency.equals(other.req_frequency))
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
		return "CourseInformation [id=" + id + ", course_num=" + course_num + ", course_name=" + course_name + ", type="
				+ type + ", level=" + level + ", dept=" + dept + ", num_sections=" + num_sections + ", req_frequency="
				+ req_frequency + "]";
	}
	
}
