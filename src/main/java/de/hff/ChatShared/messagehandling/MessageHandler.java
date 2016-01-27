package de.hff.ChatShared.messagehandling;

import de.hff.ChatShared.messagehandling.messageinput.MessageListener;

public interface MessageHandler extends MessageListener {

	public void handleMessage(Message message);

}