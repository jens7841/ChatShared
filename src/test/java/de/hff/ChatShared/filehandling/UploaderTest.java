package de.hff.ChatShared.filehandling;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;

import de.hff.ChatShared.messagehandling.Message;
import de.hff.ChatShared.messagehandling.MessageType;
import de.hff.ChatShared.messagehandling.messageinput.MessageInputStream;
import de.hff.ChatShared.messagehandling.messageoutput.MessageOutputstream;
import de.hff.ChatShared.messagehandling.messageoutput.MessageSender;
import junit.framework.Assert;

public class UploaderTest {

	@Test
	public void test() throws IOException, InterruptedException {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		MessageOutputstream out = new MessageOutputstream(byteOut);
		MessageSender sender = new MessageSender(out);

		File file = new File("test.txt");
		FileWriter writer = new FileWriter(file);
		writer.write("HALLO");
		writer.close();

		int expectedId = 1;
		TransferFile transferFile = new TransferFile(file, file.length(), expectedId);
		Uploader uploader = new Uploader(sender, transferFile, 1024 * 1024);
		uploader.start();
		Thread.sleep(2000);

		MessageInputStream messageIn = new MessageInputStream(new ByteArrayInputStream(byteOut.toByteArray()));
		Message message = null;
		message = messageIn.readMessage();

		DataInputStream in = new DataInputStream(new ByteArrayInputStream(message.getBytes()));
		MessageType type = message.getType();
		int id = in.readInt();
		// int packLen = in.readInt();
		// byte[] pack = new byte[packLen];
		// in.readFully(pack);

		Assert.assertEquals(expectedId, id);
	}

}
