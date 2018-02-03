package org.dselent.scheduling.server.dao;
import java.util.List;

import org.dselent.scheduling.server.model.CourseInformation;
import org.dselent.scheduling.server.sqlutils.QueryTerm;

public interface CourseInformationDao extends Dao<CourseInformation>{
	
	//Updates a course, supports multiple fields
	public int updateCourse(List<String> columnName, List<Object> newValue, List<QueryTerm> queryTermList);
}
