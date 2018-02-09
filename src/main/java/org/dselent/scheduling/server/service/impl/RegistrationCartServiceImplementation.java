package org.dselent.scheduling.server.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.dselent.scheduling.server.dao.InstructorCourseLinkCartDao;
import org.dselent.scheduling.server.dao.InstructorsDao;
import org.dselent.scheduling.server.dao.UsersDao;
import org.dselent.scheduling.server.dto.RegistrationCartDto;
import org.dselent.scheduling.server.service.RegistrationCartService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import org.dselent.scheduling.server.model.InstructorCourseLinkCart;

@Service
public class RegistrationCartServiceImplementation implements RegistrationCartService {
	
	@Autowired
	private InstructorCourseLinkCartDao instructorCourseLinkCartDao;
	private UsersDao usersDao;
	private InstructorsDao instructorsDao;
	
	public RegistrationCartServiceImplementation() {
		//
	}

	@Override
	public List<RegistrationCartDto> registrationCart(String user_id) throws SQLException {
		
		
		
		return null;
	}

}
