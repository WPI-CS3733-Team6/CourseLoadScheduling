package org.dselent.scheduling.server.dao;
import java.util.List;

import org.dselent.scheduling.server.model.Departments;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentsDao extends Dao<Departments>{
	public int updateDepartments(List<String> columnNameList, List<Object> newValueList, List<QueryTerm> queryTermList);
}
