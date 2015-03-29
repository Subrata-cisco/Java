package com.thread.projects.desktopsearching;

import java.io.File;
import java.util.HashSet;
import java.util.concurrent.BlockingQueue;

/**
 * Files and Directory to be searched from a root directory.
 * @author subratas
 *
 */
public class FileCrawler implements Runnable {

	private final File root;
	private BlockingQueue<File> queue;
	private HashSet<File> requestedForIndexing;
	
	public FileCrawler(File root,BlockingQueue<File> queue){
		this.root = root;
		this.queue = queue;
		requestedForIndexing = new HashSet<File>();
	}
	
	@Override
	public void run() {
		try{
			crawl(root);
		}catch(InterruptedException ex){
			Thread.currentThread().interrupt();
		} finally{
			cleanGeneratingFileNames();
		}
	}
	
	public void cleanup(){
		if(queue != null){
			queue.clear();
			queue = null;
		}
		
		if(requestedForIndexing != null){
			requestedForIndexing.clear();
			requestedForIndexing = null;
		}
	}
	
	public void cleanGeneratingFileNames(){
		if(requestedForIndexing != null){
			requestedForIndexing.clear();
			requestedForIndexing = null;
		}
	}

	private void crawl(File root) throws InterruptedException {
		File[] entries = root.listFiles();
		if(entries != null){
			for(File file : entries){
				if(file.isDirectory()){
					crawl(file);
				}else if(!isAlreadyRequestedForIndexing(file)){
					queue.add(file);
					requestedForIndexing.add(file);
				}
			}
		}
	}

	private boolean isAlreadyRequestedForIndexing(File file) {
		return requestedForIndexing.contains(file);
	}

}
