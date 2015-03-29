package com.java8.streams;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Stream;
/**
 * Stream API are available with File Input Output API as well.
 * Lets find out the different usages.
 * @author Subrata Saha.
 *
 */
public class StreamsWithFileOperation {
	public static void main(String[] args) throws IOException  {
		StreamsWithFileOperation obj = new StreamsWithFileOperation();
		obj.example7();
		//obj.example2();
		//obj.example3();  implements
		//obj.example4();
		//obj.example5();
		//obj.example6();
	}
	
	
	private  void example7() throws IOException{
		try (Stream<String> st = Files.lines(Paths.get("C:\\Users\\subratas\\Desktop\\Channel_Lineup_Master.csv"))) {
			st.map(s -> s.toString()).forEach(line -> calculate(line));
		}
	}
	
	HashMap<String,String> map = new HashMap<>();
	public void calculate(String sto) {
		if (!sto.startsWith("#") && !sto.startsWith(",") && sto.length() != 0) {
			String[] pp = sto.split(",");
			String ll = pp[2];
			if (ll != null) {
				ll = ll.trim();
				if (!map.containsKey(ll)) {
					map.put(ll, pp[1]);
				} else {
					System.out.println(" Value :: "+ll + " ---  Key ::["+map.get(ll)+"] --- new Key ::[" + pp[1]+"] ");
				}
			}
		}
	}

		
	
	private void example6() throws IOException{
		Files.lines(new File("C:\\Subrata\\RestartController.java").toPath())
	     .map(s -> s.trim())
	     .filter(s -> !s.isEmpty())
	     .forEach(System.out::println);
	}
	
	/**
	 * We can also “walk” a whole file hierarchy by descending into directories using the new Files.walk() method.
	 * @throws IOException
	 */
	private void example5() throws IOException{
		Files.walk(new File(".").toPath())
	     .filter(p -> !p.getFileName()
	                    .toString().startsWith("."))
	     .forEach(System.out::println);
	}
	
	/**
	 * We can limit the number of findings.
	 * @throws IOException
	 */
	private void example4() throws IOException{
		Files.list(new File(".").toPath())
	     .filter(p -> !p.getFileName().toString().startsWith("."))
	     .limit(3)
	     .forEach(System.out::println);
	}
	
	/*
	 * We can list the files with Path object.
	 */
	private void example3() throws IOException{
		Files.list(new File("C:\\Subrata\\").toPath())
	     .forEach(System.out::println);
	}

	/**
	 * From Pattern stream is also available.
	 */
	private void example2(){
		Pattern patt = Pattern.compile(",");
		 patt.splitAsStream("a,b,c")
		     .forEach(System.out::println);
	}
	
	/**
	 * Indtread of BufferedReader we can use the folowing simple thing to read files.
	 * @throws IOException
	 */
	private void example1() throws IOException{
		try (Stream<String> st = Files.lines(Paths.get("C:\\Subrata\\RestartController.java"))) {
			st.forEach(System.out::println);
		}
	}
	
	
}
