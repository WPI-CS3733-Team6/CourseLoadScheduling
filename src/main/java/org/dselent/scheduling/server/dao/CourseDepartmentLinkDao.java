package org.dselent.scheduling.server.dao;

import java.util.List;

import org.dselent.scheduling.server.model.CourseDepartmentLink;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseDepartmentLinkDao extends Dao<CourseDepartmentLink> {
	public int updateCourseDepartmentLink(List<String> columnNameList, List<Object> newValueList, List<QueryTerm> queryTermList);
}
