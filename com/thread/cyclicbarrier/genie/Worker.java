package com.thread.cyclicbarrier.genie;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CyclicBarrier;

/**
 * Thread implementation to process all the files on parallel.
 * 
 * @author Subrata Saha
 *
 */
public class Worker implements Runnable {
	
	// has all setup variables read from all set up files
	private Map<String, String> cache;
	
	// starting point of the range of the batch file to be process by the thread.
	private int  sp;
	
	private CyclicBarrier barrier;
	
	// sp will be the index in this list for which it should work.
	private List<String> splitPoints = null;

	public Worker(int sp,Map<String, String> cache,CyclicBarrier barrier,List<String> splitPoints) {
		this.sp = sp;
		this.cache = cache;
		this.barrier = barrier;
		this.splitPoints = splitPoints;
	}
	
	private void processFile(String path) throws SetupGenerationException, IOException {
		File file = null;
		 Map<String,String> variableMap = new HashMap<String,String>();
		
		if(path != null){
			 file = new File(path);
		}
		
		if(file.exists()){
			System.out.println(file.getName());
			int rootPathIndex = path.indexOf(Constants.ROOT_FOLDER_NAME);
			if(rootPathIndex < 0){
				throw new SetupGenerationException("Constants.ROOT_FOLDER_NAME", Constants.ROOT_FOLDER_NAME +" not found in the path "+path);
			}
			String rootPath = path.substring(0,rootPathIndex+Constants.ROOT_FOLDER_NAME.length());
			System.out.println(" rootPath ::"+rootPath);
			try {
				 // read the batch file.
				 Map<String,String> testFiles = new HashMap<String,String>();
				 FileProcessingUtility.readFileContent(file,false,testFiles);
				 
				 // get all the test file path from the batch file.
				 List<String> allFilePathForOneBatch = createAllFilePathForBatchFile(testFiles,rootPath);
				 
				 //read all the possible variable from each test file and save it to allVariables.
				 FileProcessingUtility.extractVariable(allFilePathForOneBatch, variableMap);
				 
				 // prepare the map containing all the variable and its values, ready to be written in new set up file.
				 prepareVariableToGenerateSetupFile(variableMap);
				 
				 // lets write this file .
				 generateSetupFile(path, variableMap);
				 
			} catch (SetupGenerationException e) {
				throw new SetupGenerationException("Error generating file ::"+path, e.getMessage());
			}
		}
	}

	private void generateSetupFile(String path, Map<String, String> variableMap)
			throws SetupGenerationException {
		
		 int index = path.lastIndexOf(File.separator);
		 int csvIndex = path.indexOf(Constants.CSV_EXTN);
		 String batchFileName = path.substring(index+1,csvIndex);
		 String batchSetupPath = path.substring(0,index)+File.separator+"batchsetups";//
		 
		 FileProcessingUtility.createNewFile(batchFileName, batchSetupPath, variableMap);
	}
	
	
	
	private void prepareVariableToGenerateSetupFile(Map<String,String> variableMap){
		Iterator<String> it =  variableMap.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			if(cache.containsKey(key)){
				variableMap.put(key, cache.get(key));
			}
		}
	}
	
	private List<String> createAllFilePathForBatchFile(Map<String,String> testFiles,String rootPath){
		List<String> allFilePathForOneBatch = new ArrayList<String>();
		Iterator<String> it = testFiles.keySet().iterator();
		 while(it.hasNext()){
			 String fileName = it.next();
			 if(!fileName.isEmpty()){
				 fileName = fileName.substring(fileName.indexOf(Constants.ROOT_FOLDER_NAME)+Constants.ROOT_FOLDER_NAME.length(),fileName.length());
				 fileName = rootPath + fileName;
				 allFilePathForOneBatch.add(fileName);
			 }
		 }
		 return allFilePathForOneBatch;
	}

	public void run() {
		boolean run = true;
		int currentIndex = sp;
		while (run) {
			try {
					if(currentIndex < splitPoints.size() && currentIndex < (sp + Constants.THREAD_LOAD) ){
						String filePath = splitPoints.get(sp);
						processFile(filePath);
						currentIndex ++ ;
						System.out.println(Thread.currentThread().getName()+" File path processed ::"+filePath);
					}else{
						barrier.await();
						run  = false;
					}
				
			} catch (Exception e) {
				run = false;
			}
		}
	}
}
