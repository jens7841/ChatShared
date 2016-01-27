package de.hff.ChatShared.messagehandling.messageinput;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hff.ChatShared.messagehandling.Message;

public class MessageReceiver {

	private List<MessageListener> messageListener;
	private MessageInputStream messageInputStream;

	public MessageReceiver(MessageInputStream messageInputStream) {
		messageListener = new ArrayList<>();
		this.messageInputStream = messageInputStream;

	}

	public void receiveMessage() throws IOException {

		Message message = messageInputStream.readMessage();

		for (MessageListener listener : messageListener) {
			listener.receiveMessage(message);
		}

	}

	public void addListener(MessageListener messageListener) {
		this.messageListener.add(messageListener);
	}

}