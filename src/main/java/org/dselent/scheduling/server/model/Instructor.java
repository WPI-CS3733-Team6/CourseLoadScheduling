package org.dselent.scheduling.server.model;

import java.sql.JDBCType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dselent.scheduling.server.model.UserState.Columns;

public class Instructor extends Model{
	
	//table name
	public static final String TABLE_NAME = "instructors";
	
	//column names
	public static enum Columns
	{
		ID,
		USER_ID,
		REQ_COURSES
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
		COLUMN_TYPE_MAP.put(Columns.USER_ID, JDBCType.INTEGER);
		COLUMN_TYPE_MAP.put(Columns.REQ_COURSES, JDBCType.INTEGER);
	};
	
	// attributes
	
	private Integer id;
	private Integer userId;
	private Integer reqCourses;
	
	// methods
	
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
	
	public int getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public int getUserId()
	{
		return userId;
	}

	public void setUserId(int userId)
	{
		this.userId = userId;
	}

	public int getReqCourses()
	{
		return reqCourses;
	}

	public void reqCourses(int reqCourses)
	{
		this.reqCourses = reqCourses;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((reqCourses == null) ? 0 : reqCourses.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (!(obj instanceof Instructor))
		{
			return false;
		}
		Instructor other = (Instructor) obj;
		if (userId == null)
		{
			if (other.userId != null)
			{
				return false;
			}
		}
		else if (!userId.equals(other.userId))
		{
			return false;
		}
		if (id == null)
		{
			if (other.id != null)
			{
				return false;
			}
		}
		else if (!id.equals(other.id))
		{
			return false;
		}
		if (reqCourses == null)
		{
			if (other.reqCourses != null)
			{
				return false;
			}
		}
		else if (!reqCourses.equals(other.reqCourses))
		{
			return false;
		}
		return true;
	}
	
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("UserStates [id=");
		builder.append(id);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", reqCourses=");
		builder.append(reqCourses);
		builder.append("]");
		return builder.toString();
	}
}
