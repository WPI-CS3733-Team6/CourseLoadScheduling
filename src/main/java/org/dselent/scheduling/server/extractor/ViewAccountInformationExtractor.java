package org.dselent.scheduling.server.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.model.ViewAccountInformation;

public class ViewAccountInformationExtractor extends Extractor<List<ViewAccountInformation>>
{
	@Override
	public List<ViewAccountInformation> extractData(ResultSet rs) throws SQLException
	{
		List<ViewAccountInformation> resultList = new ArrayList<>();

		while(rs.next())
		{
			ViewAccountInformation result = new ViewAccountInformation();

			result.setUserName(rs.getString(ViewAccountInformation.getColumnName(ViewAccountInformation.Columns.USER_NAME)));
			result.setFirstName(rs.getString(ViewAccountInformation.getColumnName(ViewAccountInformation.Columns.FIRST_NAME)));
			result.setLastName(rs.getString(ViewAccountInformation.getColumnName(ViewAccountInformation.Columns.LAST_NAME)));
			result.setEmail(rs.getString(ViewAccountInformation.getColumnName(ViewAccountInformation.Columns.EMAIL)));
			result.setPhoneNum(rs.getLong(ViewAccountInformation.getColumnName(ViewAccountInformation.Columns.PHONE_NUM)));
			
			if(rs.wasNull()) {
				result.setPhoneNum(null);
			}
			
			result.setSecondaryEmail(rs.getString(ViewAccountInformation.getColumnName(ViewAccountInformation.Columns.SECONDARY_EMAIL)));
			result.setReqCourses(rs.getInt(ViewAccountInformation.getColumnName(ViewAccountInformation.Columns.REQ_COURSES)));
			
			if(rs.wasNull());
			{
				result.setReqCourses(0);
			}
			result.setRemaining(rs.getInt(ViewAccountInformation.getColumnName(ViewAccountInformation.Columns.REMAINING)));
			
			if(rs.wasNull());
			{
				result.setRemaining(0);
			}
		
			resultList.add(result);
		}
			
		return resultList;
	}

}