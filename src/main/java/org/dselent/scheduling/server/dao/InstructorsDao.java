package org.dselent.scheduling.server.dao;

import java.util.List;

import org.dselent.scheduling.server.model.Instructor;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorsDao extends Dao<Instructor>
{
	public int updateInstructor(List<String> columnNameList, List<Object> newValueList, List<QueryTerm> queryTermList);
}
