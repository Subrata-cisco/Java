package com.thread.cyclicbarrier.genie;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
/**
 * File I/O operations.
 * @author Subrata Saha
 *
 */
public class FileProcessingUtility {
	
	public static void createNewFile(String batchFileName,
			String batchSetupPath, Map<String, String> variableMap)
			throws SetupGenerationException {
		PrintWriter writer = null;
		File newBatchSetupFile = new File(batchSetupPath);
		boolean isCreated = newBatchSetupFile.mkdir();

		if (isCreated) {
			batchSetupPath = batchSetupPath + File.separator + batchFileName
					+ "_setup.csv";
			newBatchSetupFile = new File(batchSetupPath);
			try {
				isCreated = newBatchSetupFile.createNewFile();
				if (isCreated) {
					writer = new PrintWriter(newBatchSetupFile, "UTF-8");
					Iterator<String> it = variableMap.keySet().iterator();
					while (it.hasNext()) {
						String key = it.next();
						writer.println(variableMap.get(key));
					}
					writer.close();
				}
			} catch (IOException e) {
				throw new SetupGenerationException(
						"Error generating new Setup file ::" + batchSetupPath,
						e.getMessage());
			}
		}
	}
	
	public static Properties getSetupProperty(String fileName) throws SetupGenerationException{
		Properties properties = new Properties();
		
		try {
			InputStream input = FileProcessingUtility.class.getResourceAsStream(fileName);	
			properties.load(input);  
		} catch (IOException e) {
			System.out.println("Sorry, unable to find " + fileName);
			throw new SetupGenerationException("Property file not found", "Setup.properties not found in class path !!");
		}
		return properties;
	}

	/**
	 * Read the content of a file and put it in HashMap as key , so that the searching is done using hashing
	 * technique for fast retrieval.
	 * 
	 * @param file
	 * @return
	 */
	public static void readFileContent(File file,boolean extractKey,Map<String, String> setUpCache) throws SetupGenerationException{
		FileReader reader = null;
		BufferedReader br = null;
		try {
			reader = new FileReader(file);
			br = new BufferedReader(reader);
			String data = null;
			while((data = br.readLine()) != null){
				if(!data.startsWith(Constants.XML_COMMENT) && !data.isEmpty()){
					if(extractKey){
						String variable = extractVariableNameFromSetupFiles(data);
						setUpCache.put(variable,data);
					}else{
						setUpCache.put(data,data);
					}
				}
			}
		} catch (IOException e) {
			System.out.println("**** ERROR happened while reading the file , output may be incorrect !!"+e.getMessage());
			throw new SetupGenerationException("IOException",e.getMessage());
		}finally{
			
			try {
				if (reader != null) {
					reader.close();
					reader = null;
				}

				if (br != null) {
					br.close();
					br = null;
				}
			} catch (Exception ex) {
               // digest it.
			}
			
		}
	}

	private static String extractVariableNameFromSetupFiles(String key) {
		String variable = null;
		if(key != null && !key.isEmpty()){
			String[] words = key.split(",");
			if (words != null
					&& words.length == Constants.SETUP_LINE_SIZE_HAVING_VARIABLE
					&& checkIfVariable(words[1])) {
				variable = words[1];
			}
		}
		return variable;
	}
	
	public static void extractVariable(List<String> filePath,Map<String,String> map) throws SetupGenerationException{
		for(String path : filePath){
			File file = new File(path);
			if(file.exists()){
				extractVariable(file,map);
			}
		}
	}
	
	public static void extractVariable(File file,Map<String,String> map) throws SetupGenerationException{
		FileReader reader = null;
		BufferedReader br = null;
		String data = null;
		
		try {
			reader = new FileReader(file);
			br = new BufferedReader(reader);
			
			while((data = br.readLine()) != null){
				if(!data.startsWith(Constants.XML_COMMENT) && !data.isEmpty()){
					StringTokenizer token = new StringTokenizer(data, ",");
					while(token.hasMoreElements()){
						String word = (String) token.nextElement();
						if(checkIfVariable(word)){
							map.put(word, "");
						}
					}
				}
			}
			
		} catch (IOException e) {
			System.out.println("**** ERROR happened while reading the file , output may be incorrect !!"+e.getMessage());
			throw new SetupGenerationException("IOException",e.getMessage());
		}finally{
			
			try {
				if (reader != null) {
					reader.close();
					reader = null;
				}

				if (br != null) {
					br.close();
					br = null;
				}
			} catch (Exception ex) {
               // digest it.
			}
			
		}
	}
	

	private static boolean isNumber(String string) {
		Pattern numberPattern = Pattern.compile("^[0-9]+$");    
	    return string != null && numberPattern.matcher(string).matches();
	}
	
	private static boolean checkIfVariable(String word) {
		boolean isInUpperCase = true;
		
		if (word == null
				|| (word != null && (word.equalsIgnoreCase("true") || (word
						.equalsIgnoreCase("false"))))  || isNumber(word)) {
			return false;
		}
		
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			if (Character.isLetter(c) && !Character.isUpperCase(c)) {
				isInUpperCase = false;
			}
		}
		return isInUpperCase;
	}

}
