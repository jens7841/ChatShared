package de.hff.ChatShared.filehandling;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class FileManager {

	private List<TransferFile> uploadFiles;
	private Map<TransferFile, Filesaver> downloadFilesaver;

	private int uploadCounter;
	private int downloadCounter;

	private File tempFolder;

	public FileManager(String tempFolderPath) {

		this.tempFolder = new File(tempFolderPath);
		if (tempFolder.exists() && !tempFolder.isDirectory())
			throw new IllegalStateException("Tempfolder must be a directory");

		uploadFiles = new ArrayList<>();
		downloadFilesaver = new HashMap<>();

	}

	public void addUploadFiles(TransferFile file) {
		uploadFiles.add(file);
		uploadCounter++;
	}

	public void addDownloadFile(TransferFile file) {
		Filesaver filesaver = new Filesaver(file.getFile());
		downloadFilesaver.put(file, filesaver);
		downloadCounter++;
	}

	public TransferFile getDownloadFile(int id) {
		for (Entry<TransferFile, Filesaver> entry : downloadFilesaver.entrySet()) {
			TransferFile transferFile = entry.getKey();
			if (transferFile.getId() == id)
				return transferFile;
		}
		return null;
	}

	public TransferFile getUploadFile(int id) {
		for (TransferFile transferFile : uploadFiles) {
			if (transferFile.getId() == id)
				return transferFile;
		}
		return null;
	}

	public Filesaver getFilesaver(TransferFile file) {
		return downloadFilesaver.get(file);
	}

	public void clearTempFolder() {
		deleteFolder(tempFolder);
		tempFolder.mkdirs();
	}

	private void deleteFolder(File path) {
		for (File file : path.listFiles()) {
			if (file.isDirectory())
				deleteFolder(file);
			file.delete();
		}
	}

	public File getTempFolder() {
		return tempFolder;
	}

	public int getDownloadCounter() {
		return downloadCounter;
	}

	public int getUploadCounter() {
		return uploadCounter;
	}

}