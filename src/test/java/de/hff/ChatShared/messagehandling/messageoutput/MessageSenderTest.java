package de.hff.ChatShared.messagehandling.messageoutput;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.junit.Assert;
import org.junit.Test;

import de.hff.ChatShared.messagehandling.Message;
import de.hff.ChatShared.messagehandling.MessageType;

public class MessageSenderTest {

	@Test
	public void test() throws IOException {
		MessageType messageType = MessageType.CHAT_MESSAGE;
		String text = "Hi ÖÄÜ?öäü#+``´´/*-*\\";
		Message message = new Message(text, messageType);

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		MessageSender messageSender = new MessageSender(new MessageOutputstream(out));

		messageSender.sendMessage(message);

		byte[] expected = ByteBuffer.allocate(message.getBytes().length + 5).put((byte) messageType.getTypeNumber())
				.putInt(message.getBytes().length).put(message.getBytes()).array();

		/** Test */
		Assert.assertArrayEquals(expected, out.toByteArray());

	}

}