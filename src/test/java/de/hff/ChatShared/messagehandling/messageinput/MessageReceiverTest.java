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

		ByteArrayInputStream in = new ByteArrayInputStream(getMessageInput(text.getBytes("UTF-8")));

		MessageReceiver messageReceiver = new MessageReceiver(new MessageInputStream(in));

		messageReceiver.addListener(new MessageListener() {

			@Override
			public void receiveMessage(Message message) {

				/** Test */
				Assert.assertEquals(text.toString(), message.toString());

			}
		});

		messageReceiver.receiveMessage();
	}

	private byte[] getMessageInput(byte[] input) {
		int textLength = input.length;
		byte[] byteInput = ByteBuffer.allocate(textLength + 5).put((byte) MessageType.CHAT_MESSAGE.getTypeNumber())
				.putInt(textLength).put(input).array();
		return byteInput;
	}

}