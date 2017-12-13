package com.generics;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GenericRestrictions {
	
	public static void main(String[] args) {
		
		NoMix();
		
		
		//Cannot Declare Static Fields Whose Types are Type Parameters
		
		
		
		
	}

	// Cannot Instantiate Generic Types with Primitive Types
	private static void NoMix() {
		// compile-time error
		// Pair<int, char> p = new Pair<>(8, 'a'); 
		Pair<Integer, Character> p = new Pair<>(8, 'a');
	}
	
	// Cannot Create Instances of Type Parameters
	public static <E> void append(List<E> list, Class<E> cls) throws Exception {
		//  E elem = new E();  // compile-time error
		
	    E elem = cls.newInstance();   // OK
	    list.add(elem);
	}
	
	//Cannot Declare Static Fields Whose Types are Type Parameters
	public class MobileDevice<T> {
	    //private static T os;

	    // ...
	}
	
	//Cannot Use Casts or instanceof with Parameterized Types
	public static <E> void rtti(List<E> list) {
	   /* 
	    
	    if (list instanceof ArrayList<Integer>) {  // compile-time error
	        // ...
	    }
	    
	    */
		
		List<Integer> li = new ArrayList<>();
		//List<Number>  ln = (List<Number>) li;  // compile-time error
	}
	public static void rtti2(List<?> list) {
	    if (list instanceof ArrayList<?>) {  // OK; instanceof requires a reifiable type
	        // ...
	    }
	}
	
	//Cannot Create Arrays of Parameterized Types
	//List<Integer>[] arrayOfLists = new List<Integer>[2]; 
	
	
	//Cannot Create, Catch, or Throw Objects of Parameterized Types
	//You can, however, use a type parameter in a throws clause:
	class Parser<T extends Exception> {
	    public void parse(File file) throws T {     // OK
	        // ...
	    }
	}
	
	//Cannot Overload a Method Where the Formal Parameter Types of Each 
	//Overload Erase to the Same Raw Type
	// Extends Throwable indirectly
	//class MathException<T> extends Exception { /* ... */ }    // compile-time error

	// Extends Throwable directly
	//class QueueFullException<T> extends Throwable { /* ... */ // compile-time error
	/*public static <T extends Exception, J> void execute(List<J> jobs) {
	    try {
	        for (J job : jobs) {}
	            // ...
	    } catch (T e) {   // compile-time error
	        // ...
	    }
	}*/
	public class Example {
	   // public void print(Set<String> strSet) { }
	   // public void print(Set<Integer> intSet) { }
	}
}


class Pair<K, V> {

    private K key;
    private V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    // ...
}