package com.basic;

import java.lang.reflect.Array;

/**
 * Generics Example. [What is not possible in generics and not so popular use case.]
 * 
 * @author Subrata Saha.
 *
 */
public class GenericsExample {

}



class Node<T,K> {
	//T id;
	K[] values ;
	
	Node(int val){
		//id = val;
		// the following thing is not possible because compiler don't know how to allocate memory for unknown type.
		// values = new T[5];
		values = (K[]) Array.newInstance(Integer.class, val);
	}
}
