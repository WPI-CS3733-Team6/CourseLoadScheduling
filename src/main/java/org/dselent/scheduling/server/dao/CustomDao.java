package org.dselent.scheduling.server.dao;

import java.util.List;

import org.dselent.scheduling.server.model.User;
import org.dselent.scheduling.server.model.ViewAccountInformation;
import org.springframework.stereotype.Repository;

/**
 * Interface for all daos for custom queries.
 * 
 * @author dselent
 *
 */
@Repository
public interface CustomDao
{
	// custom queries here
	
	public List<User> getAllUsersWithRole(int roleId);

	public ViewAccountInformation getAccountInformationWithUserId(Integer userId);
}
