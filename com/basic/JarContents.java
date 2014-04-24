package com.basic;

import java.util.zip.*;
import java.io.*;
import java.util.*;

/**
 * Find the jar file name having the some class name in the path given.
 * @author subratas
 *
 */
public class JarContents {
	public static void main(String[] args) {
		File jarDir = new File("C:/Users/subratas/.m2/repository");
		if (jarDir.isDirectory()) {
			recurseDirs(jarDir);
		}
	}

	public static void recurseDirs(File jarDir) {

		File[] files = jarDir.listFiles();
		boolean found = false;
		for (int i = 0; i < files.length; i++) {
			File file = files[i];

			if (file.isDirectory()) {
				recurseDirs(file);
			}

			String fileName = file.getName();
			String filePath = file.getPath();

			if (fileName.endsWith(".jar")) {

				try {
					ZipFile zipFile = new ZipFile(file);
					Enumeration zipEntries = zipFile.entries();
					
					while (zipEntries.hasMoreElements()) {
						ZipEntry zipEntryFile = (ZipEntry) zipEntries
								.nextElement();

						String zipEntryFileName = zipEntryFile.getName();

						if (zipEntryFileName
								.equals("javax/validation/Valid.class")) {
							System.out.println(zipEntryFileName +" found in Jar name :: \n"+filePath);
							found = true;
							break;
						}
					}
					
				} catch (Exception e) {
					System.out.println("File not a zip file");
				}
			}
		}
		
		if (!found) {
			System.out.println(" Class not found in the path !!");
		}
	}
}
