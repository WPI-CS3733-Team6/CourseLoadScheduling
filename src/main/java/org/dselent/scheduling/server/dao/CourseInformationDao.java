package org.dselent.scheduling.server.dao;
import java.util.List;

import org.dselent.scheduling.server.model.CourseInformation;
import org.dselent.scheduling.server.sqlutils.QueryTerm;

public interface CourseInformationDao extends Dao<CourseInformation>{	
	public int updateCourseInformation(List<String> columnNameList, List<Object> newValueList, List<QueryTerm> queryTermList);
}
