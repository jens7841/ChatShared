package de.hff.ChatShared.messagehandling.messageinput;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

import de.hff.ChatShared.messagehandling.Message;
import de.hff.ChatShared.messagehandling.MessageType;

public class MessageInputStream extends FilterInputStream {

	public MessageInputStream(InputStream in) {
		super(new DataInputStream(new BufferedInputStream(in)));
	}

	public Message readMessage() throws IOException {
		DataInputStream input = (DataInputStream) in;

		byte[] arr;
		int messageType = input.read();
		if (messageType == -1)
			throw new IOException("Stream closed");
		arr = new byte[input.readInt()];
		input.readFully(arr);
		return new Message(arr, MessageType.getType(messageType));
	}

}