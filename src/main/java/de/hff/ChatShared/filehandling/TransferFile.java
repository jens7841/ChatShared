package de.hff.ChatShared.filehandling;

import java.io.File;

public class TransferFile {

	private File file;
	private long expectedLength;
	private int id;

	public TransferFile(File file, long expectedLength, int id) {
		this.file = file;
		this.expectedLength = expectedLength;
		this.id = id;
	}

	public File getFile() {
		return file;
	}

	public long getExpectedLength() {
		return expectedLength;
	}

	public int getId() {
		return id;
	}
}