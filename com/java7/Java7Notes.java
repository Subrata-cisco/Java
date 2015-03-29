package com.java7;


public class Java7Notes {
	
	
	
	/*
	 * 
	 * 
	 * 1) Type inference
	 * 
	 * Before JDK 1.7 introduce a new operator <<, known as diamond operator to
	 * making type inference available for constructors as well. Prior to Java
	 * 7, type inference is only available for methods, and Joshua Bloch has
	 * rightly predicted in Effective Java 2nd Edition, it’s now available for
	 * constructor as well. Prior JDK 7, you type more to specify types on both
	 * left and right hand side of object creation expression, but now it only
	 * needed on left hand side, as shown in below example.
	 * 
	 * Prior JDK 7 Map<String, List<String>> employeeRecords = new
	 * HashMap<String, List<String>>(); List<Integer> primes = new
	 * ArrayList<Integer>();
	 * 
	 * In JDK 7 Map<String, List<String>> employeeRecords = new HashMap<>();
	 * List<Integer> primes = new ArrayList<>();
	 * 
	 * So you have to type less in Java 7, while working with Collections, where
	 * we heavily use Generics. See here for more detailed information on
	 * diamond operator in Java.
	 * 
	 * 
	 * 2) String in Switch
	 * 
	 * Before JDK 7, only integral types can be used as selector for switch-case
	 * statement. In JDK 7, you can use a String object as the selector. For
	 * example, String state = "NEW";
	 * 
	 * switch (day) { case "NEW": System.out.println("Order is in NEW state");
	 * break; case "CANCELED": System.out.println("Order is Cancelled"); break;
	 * case "REPLACE": System.out.println("Order is replaced successfully");
	 * break; case "FILLED": System.out.println("Order is filled"); break;
	 * default: System.out.println("Invalid");
	 * 
	 * } equals() and hashcode() method from java.lang.String is used in
	 * comparison, which is case-sensitive. Benefit of using String in switch is
	 * that, Java compiler can generate more efficient code than using nested
	 * if-then-else statement. See here for more detailed information of how to
	 * use String on Switch case statement.
	 * 
	 * 
	 * 3) Automatic Resource Management
	 * 
	 * Before JDK 7, we need to use a finally block, to ensure that a resource
	 * is closed regardless of whether the try statement completes normally or
	 * abruptly, for example while reading files and streams, we need to close
	 * them into finally block, which result in lots of boiler plate and messy
	 * code, as shown below : public static void main(String args[]) {
	 * FileInputStream fin = null; BufferedReader br = null; try { fin = new
	 * FileInputStream("info.xml"); br = new BufferedReader(new
	 * InputStreamReader(fin)); if (br.ready()) { String line1 = br.readLine();
	 * System.out.println(line1); } } catch (FileNotFoundException ex) {
	 * System.out.println("Info.xml is not found"); } catch (IOException ex) {
	 * System.out.println("Can't read the file"); } finally { try { if (fin !=
	 * null) fin.close(); if (br != null) br.close(); } catch (IOException ie) {
	 * System.out.println("Failed to close files"); } } }
	 * 
	 * Look at this code, how many lines of boiler codes?
	 * 
	 * Now in Java 7, you can use try-with-resource feature to automatically
	 * close resources, which implements AutoClosable and Closeable interface
	 * e.g. Streams, Files, Socket handles, database connections etc. JDK 7
	 * introduces a try-with-resources statement, which ensures that each of the
	 * resources in try(resources) is closed at the end of the statement by
	 * calling close() method of AutoClosable. Now same example in Java 7 will
	 * look like below, a much concise and cleaner code :
	 * 
	 * public static void main(String args[]) { 
	 *   try ( FileInputStream fin = new FileInputStream("info.xml"); 
	 *         BufferedReader br = new BufferedReader(new InputStreamReader(fin));)  { 
	 *            if (br.ready()) { 
	 *                String line1 = br.readLine(); System.out.println(line1); 
	 *            } 
	 *   } catch (FileNotFoundException ex) { 
	 *     System.out.println("Info.xml is not found");
	 *   } catch (IOException ex) { 
	 *     System.out.println("Can't read the file"); 
	 *   } 
	 * }
	 * 
	 * Since Java is taking care of closing opened resources including files and
	 * streams, may be no more leaking of file descriptors and probably an end
	 * to file descriptor error. Even JDBC 4.1 is retrofitted as AutoClosable
	 * too.
	 * 
	 * 4) Fork Join Framework
	 * 
	 * The fork/join framework is an implementation of the ExecutorService
	 * interface that allows you to take advantage of multiple processors
	 * available in modern servers. It is designed for work that can be broken
	 * into smaller pieces recursively. The goal is to use all the available
	 * processing power to enhance the performance of your application. As with
	 * any ExecutorService implementation, the fork/join framework distributes
	 * tasks to worker threads in a thread pool. The fork join framework is
	 * distinct because it uses a work-stealing algorithm, which is very
	 * different than producer consumer algorithm. Worker threads that run out
	 * of things to do can steal tasks from other threads that are still busy.
	 * The centre of the fork/join framework is the ForkJoinPool class, an
	 * extension of the AbstractExecutorService class. ForkJoinPool implements
	 * the core work-stealing algorithm and can execute ForkJoinTask processes.
	 * You can wrap code in a ForkJoinTask subclass like RecursiveTask (which
	 * can return a result) or RecursiveAction. See here for some more
	 * information on fork join framework in Java.
	 * 
	 * 
	 * 5) Underscore in Numeric literals
	 * 
	 * In JDK 7, you could insert underscore(s) '_' in between the digits in an
	 * numeric literals (integral and floating-point literals) to improve
	 * readability. This is especially valuable for people who uses large
	 * numbers in source files, may be useful in finance and computing domains.
	 * For example,
	 * 
	 * int billion = 1_000_000_000; // 10^9 long creditCardNumber =
	 * 1234_4567_8901_2345L; //16 digit number long ssn = 777_99_8888L; double
	 * pi = 3.1415_9265; float pif = 3.14_15_92_65f;
	 * 
	 * You can put underscore at convenient points to make it more readable, for
	 * examples for large amounts putting underscore between three digits make
	 * sense, and for credit card numbers, which are 16 digit long, putting
	 * underscore after 4th digit make sense, as they are printed in cards. By
	 * the way remember that you cannot put underscore, just after decimal
	 * number or at the beginning or at the end of number. For example,
	 * following numeric literals are invalid, because of wrong placement of
	 * underscore:
	 * 
	 * double pi = 3._1415_9265; // underscore just after decimal point long
	 * creditcardNum = 1234_4567_8901_2345_L; //underscore at the end of number
	 * long ssn = _777_99_8888L; //undersocre at the beginning
	 * 
	 * See my post about how to use underscore on numeric literals for more
	 * information and use case.
	 * 
	 * 6) Catching Multiple Exception Type in Single Catch Block
	 * 
	 * Java 7 Feature Revisited BookIn JDK 7, a single catch block can handle
	 * more than one exception types.
	 * 
	 * For example, before JDK 7, you need two catch blocks to catch two
	 * exception types although both perform identical task:
	 * 
	 * try {
	 * 
	 * ......
	 * 
	 * } catch(ClassNotFoundException ex) { ex.printStackTrace(); }
	 * catch(SQLException ex) { ex.printStackTrace(); }
	 * 
	 * In JDK 7, you could use one single catch block, with exception types
	 * separated by '|'.
	 * 
	 * try {
	 * 
	 * ......
	 * 
	 * } catch(ClassNotFoundException|SQLException ex) {
	 * 
	 * ex.printStackTrace();
	 * 
	 * }
	 * 
	 * By the way, just remember that Alternatives in a multi-catch statement
	 * cannot be related by sub classing. For example a multi-catch statement
	 * like below will throw compile time error :
	 * 
	 * try {
	 * 
	 * ......
	 * 
	 * } catch (FileNotFoundException | IOException ex) {
	 * 
	 * ex.printStackTrace();
	 * 
	 * }
	 * 
	 * Alternatives in a multi-catch statement cannot be related by sub
	 * classing, it will throw error at compile time :
	 * java.io.FileNotFoundException is a subclass of alternative
	 * java.io.IOException at Test.main(Test.java:18)
	 * 
	 * see here to learn more about improved exception handling in Java SE 7.
	 * 
	 * 
	 * 7) Binary Literals with prefix "0b"
	 * 
	 * In JDK 7, you can express literal values in binary with prefix '0b' (or
	 * '0B') for integral types (byte, short, int and long), similar to C/C++
	 * language. Before JDK 7, you can only use octal values (with prefix '0')
	 * or hexadecimal values (with prefix '0x' or '0X').
	 * 
	 * int mask = 0b01010000101;
	 * 
	 * or even better
	 * 
	 * int binary = 0B0101_0000_1010_0010_1101_0000_1010_0010;
	 * 
	 * 
	 * 8) Java NIO 2.0
	 * 
	 * Java SE 7 introduced java.nio.file package and its related package,
	 * java.nio.file.attribute, provide comprehensive support for file I/O and
	 * for accessing the default file system. It also introduced the Path class
	 * which allow you to represent any path in operating system. New File
	 * system API complements older one and provides several useful method
	 * checking, deleting, copying, and moving files. for example, now you can
	 * check if a file is hidden in Java. You can also create symbolic and hard
	 * links from Java code. JDK 7 new file API is also capable of searching for
	 * files using wild cards. You also get support to watch a directory for
	 * changes. I would recommend to check Java doc of new file package to learn
	 * more about this interesting useful feature.
	 * 
	 * 
	 * 9) G1 Garbage Collector
	 * 
	 * JDK 7 introduced a new Garbage Collector known as G1 Garbage Collection,
	 * which is short form of garbage first. G1 garbage collector performs
	 * clean-up where there is most garbage. To achieve this it split Java heap
	 * memory into multiple regions as opposed to 3 regions in the prior to Java
	 * 7 version (new, old and permgen space). It's said that G1 is quite
	 * predictable and provides greater through put for memory intensive
	 * applications.
	 * 
	 * 
	 * 10) More Precise Rethrowing of Exception
	 * 
	 * The Java SE 7 compiler performs more precise analysis of re-thrown
	 * exceptions than earlier releases of Java SE. This enables you to specify
	 * more specific exception types in the throws clause of a method
	 * declaration. before JDK 7, re-throwing an exception was treated as
	 * throwing the type of the catch parameter. For example, if your try block
	 * can throw ParseException as well as IOException. In order to catch all
	 * exceptions and rethrow them, you would have to catch Exception and
	 * declare your method as throwing an Exception. This is sort of obscure
	 * non-precise throw, because you are throwing a general Exception type
	 * (instead of specific ones) and statements calling your method need to
	 * catch this general Exception. This will be more clear by seeing following
	 * example of exception handling in code prior to Java 1.7
	 * 
	 * public void obscure() throws Exception{ try { new
	 * FileInputStream("abc.txt").read(); new
	 * SimpleDateFormat("ddMMyyyy").parse("12-03-2014"); } catch (Exception ex)
	 * { System.out.println("Caught exception: " + ex.getMessage()); throw ex; }
	 * }
	 * 
	 * From JDK 7 onwards you can be more precise while declaring type of
	 * Exception in throws clause of any method. This precision in determining
	 * which Exception is thrown from the fact that, If you re-throw an
	 * exception from a catch block, you are actually throwing an exception type
	 * which:
	 * 
	 * 1) your try block can throw, 2) has not handled by any previous catch
	 * block, and 3) is a subtype of one of the Exception declared as catch
	 * parameter
	 * 
	 * This leads to improved checking for re-thrown exceptions. You can be more
	 * precise about the exceptions being thrown from the method and you can
	 * handle them a lot better at client side, as shown in following example :
	 * 
	 * public void precise() throws ParseException, IOException { try { new
	 * FileInputStream("abc.txt").read(); new
	 * SimpleDateFormat("ddMMyyyy").parse("12-03-2014"); } catch (Exception ex)
	 * { System.out.println("Caught exception: " + ex.getMessage()); throw ex; }
	 * } The Java SE 7 compiler allows you to specify the exception types
	 * ParseException and IOException in the throws clause in the preciese()
	 * method declaration because you can re-throw an exception that is a
	 * super-type of any of the types declared in the throws, we are throwing
	 * java.lang.Exception, which is super class of all checked Exception. Also
	 * in some places you will see final keyword with catch parameter, but that
	 * is not mandatory any more.
	 * 
	 * That's all about what you can revise in JDK 7. All these new features of
	 * Java 7 are very helpful in your goal towards clean code and developer
	 * productivity. With lambda expression introduced in Java 8, this goal to
	 * cleaner code in Java has reached another milestone. Let me know, if you
	 * think I have left out any useful feature of Java 1.7, which you think
	 * should be here.
	 * 
	 * P.S. If you love books then you may like Java 7 New features Cookbook
	 * from Packet Publication as well.
	 * 
	 * 
	 * Read more:
	 * http://javarevisited.blogspot.com/2014/04/10-jdk-7-features-to-
	 * revisit-before-you.html#ixzz32keAlJcb
	 */
}
