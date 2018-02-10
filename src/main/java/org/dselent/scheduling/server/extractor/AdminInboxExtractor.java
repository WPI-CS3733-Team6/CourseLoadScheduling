package org.dselent.scheduling.server.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.model.AdminInbox;

public class AdminInboxExtractor extends Extractor<List<AdminInbox>>
{
	@Override
	public List<AdminInbox> extractData(ResultSet rs) throws SQLException
	{
		List<AdminInbox> resultList = new ArrayList<>();

		while(rs.next())
		{
			AdminInbox result = new AdminInbox();
			
			result.setId(rs.getInt(AdminInbox.getColumnName(AdminInbox.Columns.ID)));
			result.setId(rs.getInt(AdminInbox.getColumnName(AdminInbox.Columns.INBOX_USER)));
			result.setId(rs.getInt(AdminInbox.getColumnName(AdminInbox.Columns.SENDER)));
			
			if(rs.wasNull())
			{
				result.setId(null);
				result.setInboxUser(null);
				result.setSender(null);
			}
			
			result.setSubjectLine(rs.getString(AdminInbox.getColumnName(AdminInbox.Columns.SUBJECT_LINE)));
			result.setContent(rs.getString(AdminInbox.getColumnName(AdminInbox.Columns.CONTENT)));
			result.setStatus(rs.getInt(AdminInbox.getColumnName(AdminInbox.Columns.STATUS)));
			
			if(rs.wasNull())
			{
				result.setStatus(null);
			}
			
			result.setCreatedAt(rs.getTimestamp(AdminInbox.getColumnName(AdminInbox.Columns.CREATED_AT)));
			result.setUpdatedAt(rs.getTimestamp(AdminInbox.getColumnName(AdminInbox.Columns.UPDATED_AT)));
		
			resultList.add(result);
		}
			
		return resultList;
	}

}
