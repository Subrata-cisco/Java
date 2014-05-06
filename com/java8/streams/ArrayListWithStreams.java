package com.java8.streams;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;
/**
 * Using Streams option in java 8
 * @author Subrata Saha.
 *
 */
public class ArrayListWithStreams {
	public static void main(String[] args) {
		countItemWhoseSizeIsGreaterThan4();
		countItemWhoseSizeIsGreaterThan4_usingLamda();
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
