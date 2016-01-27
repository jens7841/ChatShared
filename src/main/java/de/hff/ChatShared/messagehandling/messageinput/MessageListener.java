package de.hff.ChatShared.messagehandling.messageinput;

import de.hff.ChatShared.messagehandling.Message;

public interface MessageListener {

	public void receiveMessage(Message message);

}