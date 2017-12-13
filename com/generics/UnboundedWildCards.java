package com.generics;

import java.util.Arrays;
import java.util.List;

public class UnboundedWildCards {
	
	public static void main(String[] args) {
		// i can pass
		printList(Arrays.asList(1,2,3));
		printList(Arrays.asList("a","b","c"));
	}
	
	public static void printList(List<?> list) {
	    for (Object elem: list)
	        System.out.print(elem + " ");
	    System.out.println();
	}

}
