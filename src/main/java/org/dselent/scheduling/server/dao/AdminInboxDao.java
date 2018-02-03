package org.dselent.scheduling.server.dao;
import java.util.List;

import org.dselent.scheduling.server.model.AdminInbox;
import org.dselent.scheduling.server.sqlutils.QueryTerm;

public interface AdminInboxDao extends Dao<AdminInbox>{

	public int updateMail(List<String> columnNameList, List<Object> newValueList, List<QueryTerm> queryTermList);
}
