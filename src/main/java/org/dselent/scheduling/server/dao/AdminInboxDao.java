package org.dselent.scheduling.server.dao;
import java.util.List;

import org.dselent.scheduling.server.model.AdminInbox;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminInboxDao extends Dao<AdminInbox>{
	public int updateAdminInbox(List<String> columnNameList, List<Object> newValueList, List<QueryTerm> queryTermList);
}
