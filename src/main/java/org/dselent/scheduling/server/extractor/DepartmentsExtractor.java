package org.dselent.scheduling.server.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.model.Departments;
import org.dselent.scheduling.server.model.UserRoles;

public class DepartmentsExtractor extends Extractor<List<Departments>> {
	
	@Override
	public List<Departments> extractData(ResultSet rs) throws SQLException {
		
		List<Departments> resultList = new ArrayList<>();
		
		while(rs.next()) {
			
			Departments result = new Departments();
			
			result.setId(rs.getInt(Departments.getColumnName(Departments.Columns.ID)));
			
			result.setCreatedAt(rs.getTimestamp(Departments.getColumnName(Departments.Columns.CREATED_AT)));
			
			result.setUpdatedAt(rs.getTimestamp(Departments.getColumnName(Departments.Columns.UPDATED_AT)));
			
			result.setDeptName(rs.getString(Departments.getColumnName(Departments.Columns.DEPT_NAME)));
			
			resultList.add(result);
		}
		
		return resultList;
	}
	
}
