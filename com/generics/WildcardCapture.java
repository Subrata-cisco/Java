package com.generics;

import java.util.List;

/*
the compiler processes the i input parameter as being of type Object. 
When the foo method invokes List.set(int, E), the compiler is not able 
to confirm the type of object that is being inserted into the list, 
and an error is produced. When this type of error occurs it typically means 
that the compiler believes that you are assigning the wrong type to a variable.
*/
public class WildcardCapture {
	
	void foo(List<?> i) {
		fooHelper(i);
		
        // i.set(0, i.get(0));
    }
	
	private static <T> void fooHelper(List<T> list) {
        list.set(0, list.get(0));
    }

}
