package org.dselent.scheduling.server.service;

import java.sql.SQLException;
import java.util.List;

import org.dselent.scheduling.server.dto.InboxMessageDto;
import org.springframework.stereotype.Service;

@Service
public interface HomeService {

	//If user_id belongs to admin, return list of messages in admin inbox,
	//else return null
	public List<InboxMessageDto> load(String user_id) throws SQLException, Exception;
}
