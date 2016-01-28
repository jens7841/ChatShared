package de.hff.ChatShared.messagehandling.messageoutput;

import java.io.IOException;

import de.hff.ChatShared.messagehandling.Message;

public class MessageSender {

	private MessageOutputstream messageOutputstream;

	public MessageSender(MessageOutputstream messageOutputstream) {
		this.messageOutputstream = messageOutputstream;
	}

	public synchronized void sendMessage(Message message) throws IOException {
		messageOutputstream.writeMessage(message);
	}

}