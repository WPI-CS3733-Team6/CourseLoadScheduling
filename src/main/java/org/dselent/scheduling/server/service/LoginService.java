package org.dselent.scheduling.server.service;

import java.sql.SQLException;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {

	//Check if entered authentication information is valid
	//return 1 on successful login, else return 0
	public Integer login(String username, String password) throws SQLException, Exception;
}
