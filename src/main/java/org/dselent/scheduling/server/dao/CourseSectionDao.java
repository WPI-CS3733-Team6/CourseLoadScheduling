package org.dselent.scheduling.server.dao;
import java.util.List;

import org.dselent.scheduling.server.model.CourseSection;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseSectionDao extends Dao<CourseSection>{
	public int updateCourseSection(List<String> columnNameList, List<Object> newValueList, List<QueryTerm> queryTermList);
}
