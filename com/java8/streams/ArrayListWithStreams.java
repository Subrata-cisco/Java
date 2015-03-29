package com.java8.streams;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
/**
 * Using Streams option in java 8
 * @author Subrata Saha.
 *
 */
public class ArrayListWithStreams {
	public static void main(String[] args) {
		//countItemWhoseSizeIsGreaterThan4();
		//countItemWhoseSizeIsGreaterThan4_usingLamda();
		//example1();
		example2();
		
	}
	
	private static void example2() {
		List<String> terms = Arrays.asList("Command", "Adapter", "Strategy",
				"Builder");
		Collections.shuffle(terms);
		System.out.print(terms.stream().limit((long) (2 + Math.random() * 2))
				.collect(Collectors.joining(",")));

	}
	
	private static void example1(){
		Arrays.stream(new int[]{1, 2, 3, 4, 5, 6})
        .parallel()
        .max()
        .ifPresent(System.out::println);
	}

	/*
	 * Using Stream in old java style
	 */
	private static void countItemWhoseSizeIsGreaterThan4() {
		Collection<String> myList = Arrays.asList("Hello", "Java", "Subrata",
				"Saha");
		long countLongStrings = myList.stream().filter(new Predicate<String>() {
			@Override
			public boolean test(String t) {
				return t.length() > 4;
			}
		}).count();
		System.out.println(" countLongStrings ::" + countLongStrings);
	}

	/*
	 * Using streams with lamda style.
	 */
	private static void countItemWhoseSizeIsGreaterThan4_usingLamda() {
		Collection<String> myList = Arrays.asList("Hello","Java","Subrata","Saha");
		long countLongStrings = myList.stream().filter(item->item.length()>4).count();
		System.out.println(" countLongStrings ::"+countLongStrings);
	}
}
