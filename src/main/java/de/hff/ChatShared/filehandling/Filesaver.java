package de.hff.ChatShared.filehandling;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

public class Filesaver extends Thread {

	private boolean running;
	private BlockingQueue<byte[]> buffer;
	private File file;
	private long receivedBytes;
	private Semaphore lock = new Semaphore(1);

	public Filesaver(File file) throws FileNotFoundException {
		this.file = file;
		this.buffer = new LinkedBlockingQueue<>();
	}

	@Override
	public synchronized void start() {
		this.running = true;
		super.start();
	}

	public void savePackage(byte[] pack) throws IOException {
		if (getState() == State.NEW) {
			start();
		} else if (getState() == State.TERMINATED) {
			throw new IllegalStateException("Filesaver-Thread has stopped running");
		}

		try {
			receivedBytes += pack.length;
			buffer.put(pack);
			lock.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public long getReceivedBytes() {
		return receivedBytes;
	}

	public void endSave() {
		this.running = false;
		lock.release();
	}

	@Override
	public void run() {
		OutputStream out = null;
		try {

			out = new BufferedOutputStream(new FileOutputStream(file));
			while (running || buffer.size() != 0) {
				lock.acquire();
				while (buffer.size() > 0) {
					out.write(buffer.take());

				}
			}
			out.flush();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if (out != null)
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	public boolean isRunning() {
		return isAlive();
	}

}