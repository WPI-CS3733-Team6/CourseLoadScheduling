package org.dselent.scheduling.server.dao;

import java.util.List;

import org.dselent.scheduling.server.model.UserRoles;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRolesDao extends Dao<UserRoles>
{
	public int updateUserRoles(List<String> columnNameList, List<Object> newValueList, List<QueryTerm> queryTermList);	
}
