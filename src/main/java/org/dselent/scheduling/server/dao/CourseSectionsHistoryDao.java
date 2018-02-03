package org.dselent.scheduling.server.dao;

import java.util.List;

import org.dselent.scheduling.server.model.CourseSectionsHistory;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseSectionsHistoryDao extends Dao<CourseSectionsHistory>{
	public int updateCourseSectionsHistory(List<String> columnNameList, List<Object> newValueList, List<QueryTerm> queryTermList);
}
