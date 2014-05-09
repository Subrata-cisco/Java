package com.java8.lamda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 *  Valid syntax for Lamda ::
 * 1. () -> System.out.println(this)
 * 2. (String str) -> System.out.println(str)
 * 3.  str -> System.out.println(str)
 * 4. (String s1, String s2) -> { return s2.length() - s1.length(); }
 * 5. (s1, s2) -> s2.length() - s1.length()
 * 
 * @author Subrata Saha.
 *
 */
public class LamdaUsingInterface {
	public static void main(String[] args) {
		LamdaUsingInterface obj = new LamdaUsingInterface();
		obj.example1();
		obj.example2();
		obj.example3();
		obj.example4();
	}
	
	private void example4(){
		List<Integer> number = new ArrayList<>();
		number.add(10);number.add(12);number.add(20);number.add(32);number.add(29);number.add(5);
		System.out.println(" Add all is::"+add(number,n->true));
		System.out.println(" Add nothing is::"+add(number,n->false));
		System.out.println(" Add only > 30 is::"+add(number,n->n>30));
	}
	
	public int add(List<Integer> numList, Predicate<Integer> predicate) {
		int sum = 0;
		for (int number : numList) {
			if (predicate.test(number)) {
				sum += number;
			}
		}
		return sum;
	}
	
	private void example3(){
		Person p1 = new Person("Subrata", "Saha");
		Person p2 = new Person("Subrata", "Adam");
		Person p3 = new Person("Bejoy", "Das");
		Person p4 = new Person("Kankank", "Bose");
		
		Person[] pArray = {p1,p2,p3,p4};
		
		Arrays.sort(pArray,Person::personCompare);
		
		List<Person> list = Arrays.asList(pArray);
		list.forEach(System.out::println);
	}
	
	private void example2(){
		new Thread (() -> {
            System.out.println(" Creating thread with out Run impl  as run is in functional interface.  ");
       }).start();  
	}
	
	private void example1(){
		LamdaTest test = s -> { return "Hello"+s; };
		System.out.println(" First test result ::"+test.test(" Subrata !!"));
		test.testAnother();
	}
}

@FunctionalInterface
interface LamdaTest {
	String test(String text);
	default void testAnother(){
		System.out.println(" In another default method.");
	}
}


