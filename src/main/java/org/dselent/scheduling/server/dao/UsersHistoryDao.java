package org.dselent.scheduling.server.dao;

import java.util.List;

import org.dselent.scheduling.server.model.UsersHistory;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersHistoryDao extends Dao<UsersHistory>{
	public int updateUsersHistory(List<String> columnNameList, List<Object> newValueList, List<QueryTerm> queryTermList);
}
