package org.dselent.scheduling.server.dao;
import org.dselent.scheduling.server.model.AdminInbox;

public interface AdminInboxDao extends Dao<AdminInbox>{

	//Update the status field of the specified mail
	//True for read, False for unread
	int markAsRead(int id, boolean newStatus);
	//Add functions as needed
}
