package org.dselent.scheduling.server.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.dselent.scheduling.server.model.CourseDepartmentLink;

public class CourseDepartmentLinkExtractor extends Extractor<List<CourseDepartmentLink>>{
	@Override
	public List<CourseDepartmentLink> extractData(ResultSet rs) throws SQLException
	{
		List<CourseDepartmentLink> resultList = new ArrayList<>();

		while(rs.next())
		{
			CourseDepartmentLink result = new CourseDepartmentLink();
			
			result.setId(rs.getInt(CourseDepartmentLink.getColumnName(CourseDepartmentLink.Columns.ID)));
			result.setCourseId(rs.getInt(CourseDepartmentLink.getColumnName(CourseDepartmentLink.Columns.COURSE_ID)));
			result.setDeptId(rs.getInt(CourseDepartmentLink.getColumnName(CourseDepartmentLink.Columns.DEPT_ID)));
			
			if(rs.wasNull())
			{
				result.setId(null);
				result.setCourseId(null);
				result.setDeptId(null);
			}
			
			result.setCreatedAt(rs.getTimestamp(CourseDepartmentLink.getColumnName(CourseDepartmentLink.Columns.CREATED_AT)));
			result.setUpdatedAt(rs.getTimestamp(CourseDepartmentLink.getColumnName(CourseDepartmentLink.Columns.UPDATED_AT)));
		
			resultList.add(result);
		}
			
		return resultList;
	}
}
