package com.basic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class FileReadingWriting {
	
	public void listFilesForFolder(final File folder) {
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	            System.out.println(fileEntry.getName());
	        }
	    }
	}

	
	public static void main(String[] args) {
		try {
			FileReader reader = new FileReader(new File("C:\\java\t1.txt"));
			FileInputStream fs = new FileInputStream("C:\\java\t1.txt");

			
			BufferedReader br = new BufferedReader(reader);
			String data = br.readLine() ;
			
			PrintWriter pw = new PrintWriter(new File("c://java/txt"));
			pw.println("Subrata");
			pw.flush();
			pw.close();
			
			
		} catch ( /*FileNotFoundException |*/ IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
