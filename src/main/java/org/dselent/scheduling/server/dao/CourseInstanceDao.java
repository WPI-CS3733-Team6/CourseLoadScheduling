package org.dselent.scheduling.server.dao;
import java.util.List;

import org.dselent.scheduling.server.model.CourseInstance;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseInstanceDao extends Dao<CourseInstance>{
	public int updateCourseInstance(List<String> columnNameList, List<Object> newValueList, List<QueryTerm> queryTermList);
}
