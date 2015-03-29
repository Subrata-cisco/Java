package com.thread.cyclicbarrier.genie;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Generate the Setup file for batch files for all the variable being used in the individual test files
 * mentioned in the batch file on parallel.
 * 
 * @author Subrata Saha
 *
 */
public class SetupGenerationForBatchFile {
	
	private static CyclicBarrier barrier;
	private static  List<String> splitPoints = null;
	
	public static void main(String[] args) {
		new SetupGenerationForBatchFile().start();
	}
	
	private void start(){
		Properties properties = null;
		Map<String, String> setUpCache = new HashMap<String,String>();
		try {
			properties = loadClientProperty();
			loadAllSetups(properties, setUpCache);
			splitBatchFolderToBeProcessed(properties);
			initiateTheProcess(setUpCache);
		} catch (SetupGenerationException e) {
			System.out.println("*************** ERROR in generating batch setup files "+e.getMessage());
		} 
	}

	private void initiateTheProcess(Map<String, String> setUpCache) {
		int noThreads = 1;
		
		if(splitPoints.size()/Constants.THREAD_LOAD != 0){
			noThreads = splitPoints.size()/Constants.THREAD_LOAD;
		}
		
		barrier = new CyclicBarrier(noThreads, new Runnable() {
			public void run() {
				System.out.println("********** All thread finished !! *************");
			}
		});
		
		int startingPoint = 0;
		ExecutorService executor = Executors.newFixedThreadPool(noThreads);
		for (int i = 0; i < noThreads; i++) {
			executor.submit(new Worker(startingPoint, setUpCache, barrier,splitPoints));
			startingPoint += Constants.THREAD_LOAD;
		}
		
		executor.shutdown();
		while(!executor.isTerminated()){
			// wait;
		}
		executor.shutdownNow(); 
	}
	

	private  void splitBatchFolderToBeProcessed(Properties properties) throws SetupGenerationException {
		File batchFilefolder = new File(properties.getProperty("BATCH_FILE_FOLDER"));
		
		if(!batchFilefolder.isDirectory()){
			String error =  "Given file path "+properties.getProperty("BATCH_FILE_FOLDER")+" is not a batch file folder !!";
			throw new SetupGenerationException(error, error);
		} 
		
		splitPoints  = new ArrayList<String>(batchFilefolder.listFiles().length);
		for (final File fileEntry : batchFilefolder.listFiles()) {
			splitPoints.add(fileEntry.getAbsolutePath());
		}
	}

	private  void loadAllSetups(Properties properties,
			Map<String, String> setUpCache) throws SetupGenerationException {
		
		// read setup folders
		String path = properties.getProperty("SET_UP_FOLDER_PATH");
		File setupFolder = new File(path);
		
		if(!setupFolder.isDirectory()){
			String error = " Given file path "+path+" is not a batch file setup folder !!";
			throw new SetupGenerationException(error, error);
		} 
		
		for (final File fileEntry : setupFolder.listFiles()) {
			try {
				FileProcessingUtility.readFileContent(fileEntry,true,setUpCache);
			} catch (SetupGenerationException e) {
				throw new SetupGenerationException("Error loadAllSetups", e.getMessage());
			}
		}
		
	}

	private  Properties loadClientProperty() throws SetupGenerationException {
		Properties props = new Properties();
		try {
			props  = FileProcessingUtility.getSetupProperty("setup.properties");
		} catch (Exception e) {
			throw new SetupGenerationException(" Erro loading property", e.getMessage());
		}
		return props;
	}
}


