package com.io;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

  /*
   
   Byte Array to InputStream and OutputStream

Here is our sample program, which first take a byte array from String for testing purpose. Anyway, always provide character encoding when converting String to bytes and vice-versa. To recreate scenario, I have created a static method, which converts an InputStream to String. This method is called streamToString(), which takes an InputStream and character encoding for reading text. We recreate the same String, which we had previously converted into byte array. Next part of this example shows, how can you write a byte array to an OutputStream. By the way, if you are not using try-with-resource statement, then don't forget to close Streams once you are through with it. It can be argued that, whether a method, which accepts an InputStream should close it or not, but I have closed it to be on safe side. Let me know what is your thought on this, because there is example of IO utility classes in both Apache Commons IO and Google Guava which closes stream and which doesn't close the stream passed to them.

/**
 * Java Program to convert byte array to InputStream and OutputStream in Java.
 * Uses ByteArrayInputStream and ByteArrayOutputStream examples.
 * @author Javin Paul
 */
public class ByteArrayToStream {
    public static void main(String args[]) {
        String str = "Google is GOD";
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);        
        // Creating InputStream from byte array
        // ByteArrayInputStream is sub-class of InputStream
        InputStream is = new ByteArrayInputStream(bytes);
        String stringFromBytes = streamToString(is, StandardCharsets.UTF_8);
        System.out.println("String recreated from bytes : " + str);        
        // Writing byte array to OutputStream
        OutputStream os = new ByteArrayOutputStream();
        try {
            os.write(bytes);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
     * Read String from InputStream and closes it
     */
    public static String streamToString(InputStream is, Charset encoding) {
        BufferedReader br = new BufferedReader(new InputStreamReader(is, encoding));
        StringBuilder sb = new StringBuilder(1024);
        try {
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
        } catch (IOException io) {
            System.out.println("Failed to read from Stream");
            io.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException ioex) {
                System.out.println("Failed to close Streams");
                ioex.printStackTrace();
            }
        }
        return sb.toString();
    }
}
/*
Output:
String recreated from bytes : Google is GOD

You can see how our streamToString() method has converted InputStream to String, but real thing is before that, we have used ByteArrayInputStream to convert our byte array to InputStream in Java. As I said ByteArrayInputStream is subclass of InputStream, you can pass or use it whenever a InputStream is required. This is also clear from class hierarchy diagram taken from java.io package, which shows different implementation of InputStream from JDK library.




That's all about how to convert byte array to InputStream in Java. It might sound difficult initially, because of limited knowledge of java.io package, but it’s become very simple once you know there is a class called ByteArrayInputStream. Since it’s a child class of InputStream, you can pass it around in place of InputStream. Don't forget to provide character encoding if you are converting bytes to characters and close streams once you are done with them.
 


Read more: http://javarevisited.blogspot.com/2014/04/how-to-convert-byte-array-to-inputstream-outputstream-java-example.html#ixzz32keocTfY
   
   
   */

