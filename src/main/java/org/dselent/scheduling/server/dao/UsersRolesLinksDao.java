package org.dselent.scheduling.server.dao;

import java.util.List;

import org.dselent.scheduling.server.model.UsersRolesLink;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRolesLinksDao extends Dao<UsersRolesLink>{
	public int updateUsersRolesLink(List<String> columnNameList, List<Object> newValueList, List<QueryTerm> queryTermList);
}
