package de.hff.ChatShared.messagehandling.messageinput;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;

import org.junit.Assert;
import org.junit.Test;

import de.hff.ChatShared.messagehandling.Message;
import de.hff.ChatShared.messagehandling.MessageType;

public class MessageReceiverTest {

	@Test
	public void testTextMessage() throws Throwable {

		String text = "Hallo Wie gehts!? öäüÖÄÜ´´``*'+# /*-*\\";
		MessageType messageType = MessageType.CHAT_MESSAGE;

		ByteArrayInputStream in = new ByteArrayInputStream(getMessageInput(text.getBytes("UTF-8"), messageType));

		MessageReceiver messageReceiver = new MessageReceiver(new MessageInputStream(in));

		messageReceiver.addListener(new MessageListener() {

			@Override
			public void receiveMessage(Message message) {

				/** Test */
				Assert.assertEquals(text.toString(), message.toString());
				Assert.assertEquals(messageType, message.getType());

			}
		});

		messageReceiver.receiveMessage();
	}

	private byte[] getMessageInput(byte[] input, MessageType messageType) {
		int textLength = input.length;
		byte[] byteInput = ByteBuffer.allocate(textLength + 5).put((byte) messageType.getTypeNumber())
				.putInt(textLength).put(input).array();
		return byteInput;
	}

}