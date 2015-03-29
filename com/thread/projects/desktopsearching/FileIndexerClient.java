package com.thread.projects.desktopsearching;

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FileIndexerClient {
	public static void main(String[] args) {
		BlockingQueue<File> queue = new LinkedBlockingQueue<File>();
		String rootFolder = "E:\\Subrata";
		File root = new File(rootFolder);
		
		new Thread(new FileCrawler(root, queue)).start();
		
		for(int i=0;i<25;i++){
			new Thread(new FileIndexer(queue)).start();
		}
	}
}
