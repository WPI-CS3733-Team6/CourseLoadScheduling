package org.dselent.scheduling.server.dao;

import java.util.List;

import org.dselent.scheduling.server.model.InstructorCourseLinkRegistered;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorCourseLinkRegisteredDao extends Dao<InstructorCourseLinkRegistered>{
	public int updateInstructorCourseLinkRegistered(List<String> columnNameList, List<Object> newValueList, List<QueryTerm> queryTermList);
}
