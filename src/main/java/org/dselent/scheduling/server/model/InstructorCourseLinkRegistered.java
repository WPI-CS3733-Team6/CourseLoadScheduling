package org.dselent.scheduling.server.model;

import java.sql.JDBCType;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dselent.scheduling.server.model.CourseInformation.Columns;

public class InstructorCourseLinkRegistered extends Model {
	
	//table names
	public static final String TABLE_NAME = "instructor_course_link_registered";
	
	//column names
	public static enum Columns {
		ID,
		INSTRUCTOR_ID,
		SECTION_ID,
		DELETED,
		CREATED_AT,
		UPDATED_AT
	}
	
	//enum list
	private static final List<Columns> COLUMN_LIST = new ArrayList<>();
	
	// type mapping
	private static final Map<Columns, JDBCType> COLUMN_TYPE_MAP = new HashMap<>();
	
	static {
		for(Columns key: Columns.values()) {
			COLUMN_LIST.add(key);
		}
		
		COLUMN_TYPE_MAP.put(Columns.ID, JDBCType.INTEGER);
		COLUMN_TYPE_MAP.put(Columns.INSTRUCTOR_ID, JDBCType.INTEGER);
		COLUMN_TYPE_MAP.put(Columns.SECTION_ID, JDBCType.INTEGER);
		COLUMN_TYPE_MAP.put(Columns.DELETED, JDBCType.BOOLEAN);
		COLUMN_TYPE_MAP.put(Columns.CREATED_AT, JDBCType.TIMESTAMP);
		COLUMN_TYPE_MAP.put(Columns.UPDATED_AT, JDBCType.TIMESTAMP);	
	}
	
	//attributes
	
	private Integer id;
	private Integer instructor_id;
	private Integer section_id;
	private Boolean deleted;
	private Timestamp created_at;
	private Timestamp updated_at;
	
	//methods
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getInstructor_id() {
		return instructor_id;
	}
	public void setInstructor_id(Integer instructor_id) {
		this.instructor_id = instructor_id;
	}
	public Integer getSection_id() {
		return section_id;
	}
	public void setSection_id(Integer section_id) {
		this.section_id = section_id;
	}
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	public Timestamp getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}
	public Timestamp getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(Timestamp updated_at) {
		this.updated_at = updated_at;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((created_at == null) ? 0 : created_at.hashCode());
		result = prime * result + ((deleted == null) ? 0 : deleted.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((instructor_id == null) ? 0 : instructor_id.hashCode());
		result = prime * result + ((section_id == null) ? 0 : section_id.hashCode());
		result = prime * result + ((updated_at == null) ? 0 : updated_at.hashCode());
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
		InstructorCourseLinkRegistered other = (InstructorCourseLinkRegistered) obj;
		if (created_at == null) {
			if (other.created_at != null)
				return false;
		} else if (!created_at.equals(other.created_at))
			return false;
		if (deleted == null) {
			if (other.deleted != null)
				return false;
		} else if (!deleted.equals(other.deleted))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (instructor_id == null) {
			if (other.instructor_id != null)
				return false;
		} else if (!instructor_id.equals(other.instructor_id))
			return false;
		if (section_id == null) {
			if (other.section_id != null)
				return false;
		} else if (!section_id.equals(other.section_id))
			return false;
		if (updated_at == null) {
			if (other.updated_at != null)
				return false;
		} else if (!updated_at.equals(other.updated_at))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "InstructorCourseLinkRegistered [id=" + id + ", instructor_id=" + instructor_id + ", section_id="
				+ section_id + ", deleted=" + deleted + ", created_at=" + created_at + ", updated_at=" + updated_at
				+ "]";
	}
	
}
