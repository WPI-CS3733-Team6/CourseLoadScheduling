package org.dselent.scheduling.server.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.model.UserRoles;

public class UserRolesExtractor extends Extractor<List<UserRoles>>
{
	@Override
	public List<UserRoles> extractData(ResultSet rs) throws SQLException
	{
		List<UserRoles> resultList = new ArrayList<>();

		while(rs.next())
		{
			UserRoles result = new UserRoles();
				
			result.setId(rs.getInt(UserRoles.getColumnName(UserRoles.Columns.ID)));
			
			if(rs.wasNull())
			{
				result.setId(null);
			}
			
			result.setRole(rs.getInt(UserRoles.getColumnName(UserRoles.Columns.ROLE)));
			
			result.setCreatedAt(rs.getTimestamp(UserRoles.getColumnName(UserRoles.Columns.CREATED_AT)));
			result.setUpdatedAt(rs.getTimestamp(UserRoles.getColumnName(UserRoles.Columns.UPDATED_AT)));
		
			resultList.add(result);
		}
			
		return resultList;
	}

}
