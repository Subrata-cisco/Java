package com.java8.lamda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 1) syntax of a lambda expression is “parameters -> body”
 * 2) Rules :
 *       Declaring the types of the parameters is optional.
 *	     Using parentheses around the parameter is optional if you have only one parameter.
 *	     Using curly braces is optional (unless you need multiple statements).
 *	     The “return” keyword is optional if you have a single expression that returns a value.
 *	 
 * @author Subrata Saha.
 *
 */
public class LamdaExampleOnList {
	
	public static void main(String[] args) {
		String [] names = {"Bejoy","Subrata","Kartik","Ajay","Reeta"};
		List<String> list = Arrays.asList(names);
		
		////////////  print all the name ////////////
		list.forEach(s->System.out.println(s));
		System.out.println("***************************");
	    list.forEach(System.out::println);
	    System.out.println("***************************");
		
		/////////// sort the list ///////////////////
		Collections.sort(list, (s1,s2)-> s1.compareTo(s2));
		list.forEach(System.out::println);
		System.out.println("***************************");
		
		list.sort(Comparator.comparingInt(String::length)); // sorting by length of the string.
		list.forEach(System.out::println);
		System.out.println("***************************");
		
		
		/////////////////// Sorting by Person class /////////////////////////////////
		Person p1 = new Person("Subrata", "Saha");
		Person p2 = new Person("Subrata", "Adam");
		Person p3 = new Person("Bejoy", "Das");
		Person p4 = new Person("Kankank", "Bose");
		List<Person> personList = new ArrayList<>();
		personList.add(p1);personList.add(p2);personList.add(p3);personList.add(p4);
		personList.sort(Comparator.comparing(Person::getFirstName).thenComparing(Person::getLasName));
		personList.forEach(System.out::println);
		System.out.println("***************************");
	}

}
