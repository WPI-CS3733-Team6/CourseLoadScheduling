package org.dselent.scheduling.server.model;

import java.sql.JDBCType;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseInstance extends Model{
	//table name
	public static final String TABLE_NAME = "course_instance";

	//column names
	public static enum Columns
	{
		ID,
		COURSE_ID,
		TERM,
		DELETED,
		CREATED_AT,
		UPDATED_AT
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
		COLUMN_TYPE_MAP.put(Columns.TERM, JDBCType.VARCHAR);
		COLUMN_TYPE_MAP.put(Columns.DELETED, JDBCType.BOOLEAN);
		COLUMN_TYPE_MAP.put(Columns.CREATED_AT, JDBCType.TIMESTAMP_WITH_TIMEZONE);
		COLUMN_TYPE_MAP.put(Columns.UPDATED_AT, JDBCType.TIMESTAMP_WITH_TIMEZONE);
	};

	// attributes

	private Integer id;
	private Integer courseId;
	private String term;
	private Boolean deleted;
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

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
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
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((deleted == null) ? 0 : deleted.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((term == null) ? 0 : term.hashCode());
		result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
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
		CourseInstance other = (CourseInstance) obj;
		if (courseId == null) {
			if (other.courseId != null)
				return false;
		} else if (!courseId.equals(other.courseId))
			return false;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
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
		if (term == null) {
			if (other.term != null)
				return false;
		} else if (!term.equals(other.term))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CourseInstance [id=" + id + ", courseId=" + courseId + ", term=" + term + ", deleted=" + deleted
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}

}
