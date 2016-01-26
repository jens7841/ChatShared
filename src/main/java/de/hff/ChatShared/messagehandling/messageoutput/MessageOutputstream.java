package de.hff.ChatShared.messagehandling.messageoutput;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import de.hff.ChatShared.messagehandling.Message;

public class MessageOutputstream extends FilterOutputStream {

	private DataOutputStream output;

	public MessageOutputstream(OutputStream out) {
		super(out);
		output = new DataOutputStream(new BufferedOutputStream(out));
	}

	public void writeMessage(Message message) throws IOException {
		output.write(message.getType().getTypeNumber());
		output.writeInt(message.getBytes().length);
		output.write(message.getBytes());
		output.flush();
	}

}