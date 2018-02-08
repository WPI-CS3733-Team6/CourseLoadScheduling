package org.dselent.scheduling.server.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.dao.UsersDao;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.User;
import org.dselent.scheduling.server.service.LoginService;
import org.dselent.scheduling.server.sqlutils.ColumnOrder;
import org.dselent.scheduling.server.sqlutils.ComparisonOperator;
import org.dselent.scheduling.server.sqlutils.LogicalOperator;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginServiceImplementation implements LoginService{

	@Autowired
	private UsersDao usersDao;
	
	public LoginServiceImplementation() {
		//
	}
	
	//Check if entered authentication information is valid
	//return 1 on successful login, else return 0
	@Transactional
	@Override
	public Integer login(String username, String password) throws Exception {
		//Confirm that user exists
		List<String> columnNameList = new ArrayList<String>();
		columnNameList.add(User.getColumnName(User.Columns.ENCRYPTED_PASSWORD));
		columnNameList.add(User.getColumnName(User.Columns.SALT));
		
		List<QueryTerm> queryTermList = new ArrayList<>();
		QueryTerm userStateQuery = new QueryTerm();
		userStateQuery.setValue(0);
		userStateQuery.setColumnName(User.getColumnName(User.Columns.USER_STATE_ID));
		userStateQuery.setComparisonOperator(ComparisonOperator.EQUAL);
		queryTermList.add(userStateQuery);
		
		QueryTerm usernameQuery = new QueryTerm();
		usernameQuery.setValue(username);
		usernameQuery.setColumnName(User.getColumnName(User.Columns.USER_NAME));
		usernameQuery.setComparisonOperator(ComparisonOperator.EQUAL);
		usernameQuery.setLogicalOperator(LogicalOperator.AND);
		queryTermList.add(usernameQuery);
		
		List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
		
		List<User> results = usersDao.select(columnNameList, queryTermList, orderByList);
		
		//Check if we only get one user back
		if (results.size() != 1)
			throw new Exception("Testing");
		
		//Get user's encrypted credentials
		User user = results.get(0);
		String salt = user.getSalt();
		String correctPassword = user.getEncryptedPassword();

		//Encrypt entered password
		String saltedPassword = password + salt;
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String enteredPassword = passwordEncoder.encode(saltedPassword);
		
		//If passwords match, return 1, else return 0
		if (correctPassword.equals(enteredPassword))
			return 1;
		throw new Exception("Invalid password");
	}

}
