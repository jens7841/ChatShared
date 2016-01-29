package de.hff.ChatShared.filehandling;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import de.hff.ChatShared.messagehandling.Message;
import de.hff.ChatShared.messagehandling.MessageType;
import de.hff.ChatShared.messagehandling.messageoutput.MessageSender;

public class Uploader extends Thread {

	private TransferFile file;
	private MessageSender sender;
	private int bufferSize;

	public Uploader(MessageSender sender, TransferFile file, int bufferSize) {
		this.bufferSize = bufferSize;
		this.file = file;
		this.sender = sender;
	}

	@Override
	public void run() {
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(file.getFile()));

			byte[] buffer = new byte[bufferSize];
			double expectedPackages = (double) file.getFile().length() / bufferSize;

			int packages = 0;

			do {

				int readBytes = in.read(buffer);
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				DataOutputStream out = new DataOutputStream(byteArrayOutputStream);
				out.writeInt(file.getId());
				out.writeInt(readBytes);
				out.write(buffer);

				sender.sendMessage(new Message(byteArrayOutputStream.toByteArray(), MessageType.DOWNLOAD_PACKAGE));
				packages++;

			} while (packages < expectedPackages);

			in.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}