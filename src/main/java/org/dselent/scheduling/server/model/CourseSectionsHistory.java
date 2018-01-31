package org.dselent.scheduling.server.model;

import java.sql.JDBCType;
import java.sql.Timestamp;
import java.time.Instant;
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
		MODIFIED_AT,
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
		COLUMN_TYPE_MAP.put(Columns.	MODIFIED_AT, JDBCType.TIMESTAMP_WITH_TIMEZONE);
	};

	// attributes
	
	private Integer id;
	private String course_id;
	private Integer course_name;
	private String course_num;
	private Integer section_num;
	private Boolean term;
	private Instant expected_pop;
	private Instant modified_at;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCourse_id() {
		return course_id;
	}
	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}
	public Integer getCourse_name() {
		return course_name;
	}
	public void setCourse_name(Integer course_name) {
		this.course_name = course_name;
	}
	public String getCourse_num() {
		return course_num;
	}
	public void setCourse_num(String course_num) {
		this.course_num = course_num;
	}
	public Integer getSection_num() {
		return section_num;
	}
	public void setSection_num(Integer section_num) {
		this.section_num = section_num;
	}
	public Boolean getTerm() {
		return term;
	}
	public void setTerm(Boolean term) {
		this.term = term;
	}
	public Instant getExpected_pop() {
		return expected_pop;
	}
	public void setExpected_pop(Instant expected_pop) {
		this.expected_pop = expected_pop;
	}
	public Instant getModified_at() {
		return modified_at;
	}
	public void setModified_at(Instant modified_at) {
		this.modified_at = modified_at;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((course_id == null) ? 0 : course_id.hashCode());
		result = prime * result + ((course_name == null) ? 0 : course_name.hashCode());
		result = prime * result + ((course_num == null) ? 0 : course_num.hashCode());
		result = prime * result + ((expected_pop == null) ? 0 : expected_pop.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((modified_at == null) ? 0 : modified_at.hashCode());
		result = prime * result + ((section_num == null) ? 0 : section_num.hashCode());
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
		if (course_id == null) {
			if (other.course_id != null)
				return false;
		} else if (!course_id.equals(other.course_id))
			return false;
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
		if (expected_pop == null) {
			if (other.expected_pop != null)
				return false;
		} else if (!expected_pop.equals(other.expected_pop))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (modified_at == null) {
			if (other.modified_at != null)
				return false;
		} else if (!modified_at.equals(other.modified_at))
			return false;
		if (section_num == null) {
			if (other.section_num != null)
				return false;
		} else if (!section_num.equals(other.section_num))
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
		return "courseSectionsHistory [id=" + id + ", course_id=" + course_id + ", course_name=" + course_name
				+ ", course_num=" + course_num + ", section_num=" + section_num + ", term=" + term + ", expected_pop="
				+ expected_pop + ", modified_at=" + modified_at + "]";
	}

	

}
