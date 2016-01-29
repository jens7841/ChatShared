package de.hff.ChatShared.filehandling;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import de.hff.ChatShared.messagehandling.Message;
import de.hff.ChatShared.messagehandling.MessageType;
import de.hff.ChatShared.messagehandling.messageinput.MessageInputStream;
import de.hff.ChatShared.messagehandling.messageoutput.MessageOutputstream;
import de.hff.ChatShared.messagehandling.messageoutput.MessageSender;

public class UploaderTest {

	private ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
	private MessageOutputstream out = new MessageOutputstream(byteOut);
	private MessageSender sender = new MessageSender(out);
	private File file = new File("test.txt");

	@Test
	public void testSinglePackage() throws IOException, InterruptedException {

		FileWriter writer = new FileWriter(file);
		String hallo = "HALLO";
		writer.write(hallo);
		writer.close();

		int expectedId = 1;
		TransferFile transferFile = new TransferFile(file, file.length(), expectedId);
		Uploader uploader = new Uploader(sender, transferFile, 1024 * 1024);
		uploader.start();

		while (uploader.isAlive()) {
			Thread.yield();
		}

		MessageInputStream messageIn = new MessageInputStream(new ByteArrayInputStream(byteOut.toByteArray()));
		Message message = null;
		message = messageIn.readMessage();

		DataInputStream in = new DataInputStream(new ByteArrayInputStream(message.getBytes()));
		MessageType type = message.getType();
		int id = in.readInt();
		int packLen = in.readInt();
		byte[] pack = new byte[packLen];
		in.readFully(pack);

		Assert.assertEquals(expectedId, id);
		Assert.assertEquals(file.length(), packLen);
		Assert.assertArrayEquals(hallo.getBytes(), pack);
	}

	@Test
	public void testMultiPackage() {

	}
}
