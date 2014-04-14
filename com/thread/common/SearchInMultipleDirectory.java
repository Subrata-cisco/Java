package com.thread.common;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * There are multiple directory and we need to search for a file name 
 * if it is found then all threads need to stop and result should be published.
 * 
 * e.g I have multiple movie directory or may be recursive also, i need to find 
 * if some movie is present or not. 
 * 
 * 
 * [By the way this is example of showing for Threads, it will very optimized to use ]
 *  BTree for this implementation.]
 *  
 *  Not completed Yet , getting bore !!!
 * 
 */
public class SearchInMultipleDirectory {
	
	/*
	 * This class will create all the individual file path of the 
	 */
	class FilePathBuilder implements Callable<String> {
		
		String dirPath;
		public FilePathBuilder(String path){
			this.dirPath = path;
		}
		
		@Override
		public String call() {
			// Iterate through  "dirPath" and put all inner filepath to pathHolder.
			
			return null;
		}
	}
	
	public static void main(String[] args) {
		SearchInMultipleDirectory obj = new SearchInMultipleDirectory();
		
		String[] filepath =  {"E:\\Subrata\\personal\\movies"};
		int totalNoOfDirectory = -1;
		int totalThreads = 10;
		ConcurrentHashMap<Integer,String> map = new ConcurrentHashMap<>();
		int index = 0;
		
		
		if(filepath[0] != null){
			File path = new File(filepath[0]);
			if(path.isDirectory()){
				totalNoOfDirectory = path.list().length;
				for(String eachPath : path.list()){
					map.put(++index, eachPath);
				}
			}else{
				// process the file and check if the name is matching and return.
				return;
			}
			
		}
		
		CyclicBarrier cbForFilePath = new CyclicBarrier(totalNoOfDirectory , new Runnable(){
			@Override
			public void run() {
				System.out.println("**********Subrata :: Total file path is captured in map !!");
				
			}
		});
		
		ExecutorService filePathBuilderSVC = Executors.newFixedThreadPool(totalNoOfDirectory);
		
		SearchInMultipleDirectory.FilePathBuilder builder = obj.new FilePathBuilder(filepath[0]);
		filePathBuilderSVC.submit(builder);
		
		filePathBuilderSVC.shutdown();
	}
	

}
