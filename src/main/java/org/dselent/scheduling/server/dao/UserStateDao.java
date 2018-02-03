package org.dselent.scheduling.server.dao;

import java.util.List;

import org.dselent.scheduling.server.model.UserState;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStateDao extends Dao<UserState>{
	public int updateUserState(List<String> columnNameList, List<Object> newValueList, List<QueryTerm> queryTermList);
}
