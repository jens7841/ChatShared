package de.hff.ChatShared.filehandling;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FileManagerTest {

	private static final String TEMP_FOLDER = "src/test/resources/tmpFolder/";

	private FileManager fileManager;

	private TransferFile file1;
	private TransferFile file2;

	@Before
	public void initialization() throws IOException {
		fileManager = new FileManager(TEMP_FOLDER);
		File file1 = writeData(new File(TEMP_FOLDER + "/testFile1.txt"), "TestFile1-txt");
		this.file1 = new TransferFile(file1, file1.length(), 1);

		File file2 = writeData(new File(TEMP_FOLDER + "/testFile2.txt"), "TestFile1-txt");
		this.file2 = new TransferFile(file2, file2.length(), 2);
	}

	@Test
	public void testDownload() {

		fileManager.addDownloadFile(file1);
		Filesaver filesaver = fileManager.getFilesaver(file1);

		Assert.assertNotEquals(null, filesaver);
		Assert.assertEquals(1, fileManager.getDownloadCounter());
		Assert.assertEquals(0, fileManager.getUploadCounter());

		fileManager.addDownloadFile(file2);
		Assert.assertEquals(2, fileManager.getDownloadCounter());

		Assert.assertEquals(file1, fileManager.getDownloadFile(file1.getId()));

	}

	@Test
	public void testUpload() {

		fileManager.addUploadFiles(file1);
		Assert.assertEquals(1, fileManager.getUploadCounter());

		fileManager.addUploadFiles(file2);
		Assert.assertEquals(2, fileManager.getUploadCounter());

		Assert.assertEquals(file1, fileManager.getUploadFile(file1.getId()));
		Assert.assertEquals(file2, fileManager.getUploadFile(file2.getId()));

		fileManager.clearTempFolder();

		Assert.assertEquals(false, file1.getFile().exists());
		Assert.assertEquals(false, file2.getFile().exists());

	}

	private File writeData(File file, String data) throws IOException {
		file.mkdirs();
		file.delete();

		FileWriter writer = new FileWriter(file);
		writer.write(data);
		writer.close();

		return file;
	}

}