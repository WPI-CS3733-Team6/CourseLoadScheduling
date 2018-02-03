package org.dselent.scheduling.server.dao;
import java.util.List;

import org.dselent.scheduling.server.model.CourseSchedule;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseScheduleDao extends Dao<CourseSchedule>{
	public int updateCourseSchedule(List<String> columnNameList, List<Object> newValueList, List<QueryTerm> queryTermList);
}
