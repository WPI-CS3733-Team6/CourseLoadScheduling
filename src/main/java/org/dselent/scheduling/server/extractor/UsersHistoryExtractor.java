package org.dselent.scheduling.server.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.model.UsersHistory;

public class UsersHistoryExtractor extends Extractor<List<UsersHistory>> {
	@Override
	public List<UsersHistory> extractData(ResultSet rs) throws SQLException	{
		List<UsersHistory> resultList = new ArrayList<>();

		while(rs.next()) {
			UsersHistory result = new UsersHistory();
				
			result.setId(rs.getInt(UsersHistory.getColumnName(UsersHistory.Columns.ID)));
			result.setUserId(rs.getInt(UsersHistory.getColumnName(UsersHistory.Columns.ID)));
			
			if(rs.wasNull()) {
				result.setId(null);
				result.setUserId(null);
			}
			
			result.setUserName(rs.getString(UsersHistory.getColumnName(UsersHistory.Columns.USER_NAME)));
			result.setFirstName(rs.getString(UsersHistory.getColumnName(UsersHistory.Columns.FIRST_NAME)));
			result.setLastName(rs.getString(UsersHistory.getColumnName(UsersHistory.Columns.LAST_NAME)));
			result.setEmail(rs.getString(UsersHistory.getColumnName(UsersHistory.Columns.EMAIL)));
			result.setPhoneNum(rs.getLong(UsersHistory.getColumnName(UsersHistory.Columns.PHONE_NUM)));
			
			if(rs.wasNull()) {
				result.setPhoneNum(null);
			}
			
			result.setSecondaryEmail(rs.getString(UsersHistory.getColumnName(UsersHistory.Columns.SECONDARY_EMAIL)));
			result.setEncryptedPassword(rs.getString(UsersHistory.getColumnName(UsersHistory.Columns.ENCRYPTED_PASSWORD)));
			result.setSalt(rs.getString(UsersHistory.getColumnName(UsersHistory.Columns.SALT)));
			
			result.setUserStateId(rs.getInt(UsersHistory.getColumnName(UsersHistory.Columns.USER_STATE_ID)));
			result.setUserRole(rs.getInt(UsersHistory.getColumnName(UsersHistory.Columns.USER_ROLE)));
			
			if(rs.wasNull()) {
				result.setUserStateId(null);
				result.setUserRole(null);
			}
			
			result.setCreatedAt(rs.getTimestamp(UsersHistory.getColumnName(UsersHistory.Columns.CREATED_AT)));
		
			resultList.add(result);
		}
			
		return resultList;
	}
}
