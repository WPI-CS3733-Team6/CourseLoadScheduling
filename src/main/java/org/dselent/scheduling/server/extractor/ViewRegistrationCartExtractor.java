package org.dselent.scheduling.server.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.model.ViewRegistrationCart;

public class ViewRegistrationCartExtractor extends Extractor<List<ViewRegistrationCart>>
{
	@Override
	public List<ViewRegistrationCart> extractData(ResultSet rs) throws SQLException
	{
		List<ViewRegistrationCart> resultList = new ArrayList<>();

		while(rs.next())
		{
			ViewRegistrationCart result = new ViewRegistrationCart();

			result.setCourseName(rs.getString(ViewRegistrationCart.getColumnName(ViewRegistrationCart.Columns.COURSE_NAME)));
		
			resultList.add(result);
		}
			
		return resultList;
	}

}