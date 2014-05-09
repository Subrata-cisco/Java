package com.java8.streams;

import java.time.Instant;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class ArrayListWithStreams2 {
	public static void main(String[] args) {
		//Example 1 : Counting Empty String
		List<String> strList = Arrays.asList("abc", "", "bcd", "", "defg", "jk");
		long count = strList.stream().filter(x -> x.isEmpty()).count();
		
		//Example 2 : Count String whose length is more than three
		long num = strList.stream().filter(x -> x.length()> 3).count();
		
		//Example 3 : Count number of String which starts with "a"
		long count1= strList.stream().filter(x -> x.startsWith("a")).count();
		
		//Example 4 : Remove all empty Strings from List
		List<String> filtered = strList.stream().filter(x -> !x.isEmpty()).collect(Collectors.toList());
		
		//Example 5 : Create a List with String more than 2 characters
		List<String> filtered2 = strList.stream().filter(x -> x.length()> 2).collect(Collectors.toList());
		
		//Example 6 : Convert String to capital case and join them using coma
		List<String> G7 = Arrays.asList("USA", "Japan", "France", "Germany", "Italy", "U.K.","Canada");
		String G7Countries = G7.stream().map(x -> x.toUpperCase()).collect(Collectors.joining(", "));
		
		//Example 7 : Create List of square of all distinct numbers
		List<Integer> numbers = Arrays.asList(9, 10, 3, 4, 7, 3, 4);
		List<Integer> distinct = numbers.stream().map( i -> i*i).distinct().collect(Collectors.toList());
		
		//Example 8:  Get count, min, max, sum, and average for numbers
		List<Integer> primes = Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29);
		IntSummaryStatistics stats = primes.stream().mapToInt((x) -> x).summaryStatistics();
		System.out.println(" stats ::"+stats);
		
		
	}
}
