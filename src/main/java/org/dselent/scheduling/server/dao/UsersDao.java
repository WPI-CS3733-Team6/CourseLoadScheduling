package org.dselent.scheduling.server.dao;

import java.util.List;

import org.dselent.scheduling.server.model.User;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersDao extends Dao<User>
{
	//Updates a user and inserts original user into history
	public int updateUser(List<String> columnName, List<Object> newValue, List<QueryTerm> queryTermList);
}
