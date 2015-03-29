package com.thread.projects.desktopsearching;

import java.io.File;
import java.util.concurrent.BlockingQueue;

public class FileIndexer implements Runnable {

	private final BlockingQueue<File> filesToBeProcessed; 
	
	public FileIndexer(BlockingQueue<File> queue){
	   this.filesToBeProcessed = queue;	
	}
	
	@Override
	public void run() {
		while(true){
			try {
				indexTheFile(filesToBeProcessed.take());
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}

	private void indexTheFile(File take) throws InterruptedException {
		System.out.println("Index the file :"+take.getName());
		Thread.sleep(100L);
	}

}
