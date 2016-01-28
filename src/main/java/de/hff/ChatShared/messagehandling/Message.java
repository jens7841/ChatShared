package de.hff.ChatShared.messagehandling;

import java.io.UnsupportedEncodingException;

public class Message {

	public static final String ENCODING = "UTF-8";
	private byte[] message;
	private MessageType type;

	public Message(byte[] message, MessageType type) {
		this.message = message;
		this.type = type;
	}

	public Message(String message, MessageType type) {

		try {

			this.message = message.getBytes(ENCODING);
			this.type = type;

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public MessageType getType() {
		return type;
	}

	public byte[] getBytes() {
		return message;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Message) {
			Message msg = (Message) obj;
			if (msg.getType() == getType() && msg.getBytes().equals(getBytes())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {

		try {
			return new String(getBytes(), ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new String(getBytes());
	}

}