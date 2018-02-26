package org.dselent.scheduling.server.service;

import java.sql.SQLException;
import java.util.List;

import org.dselent.scheduling.server.dto.InboxMessageDto;
import org.springframework.stereotype.Service;

@Service
public interface HomeService {

	//If user_id belongs to admin, return list of messages in admin inbox,
	//else return null
	public void handleMessage(Integer mailId, Boolean isApproved) throws SQLException, Exception;
	
	public List<InboxMessageDto> load(Integer user_id) throws Exception ;
	
	public void reportProblem(String probType, String description, Integer senderId) throws SQLException, Exception;
}
