package com.java8.lamda;

/**
 * Utility class to be used for demonstrating java 8 new features.
 * 
 * @author Subrata Saha.
 *
 */
public class Person {
	String firstName;
	String lasName;

	public Person(String firstName, String lasName) {
		super();
		this.firstName = firstName;
		this.lasName = lasName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Override
	public String toString() {
		return "Person [firstName=" + firstName + ", lasName=" + lasName + "]";
	}

	public String getLasName() {
		return lasName;
	}

	public void setLasName(String lasName) {
		this.lasName = lasName;
	}
	
	public static int personCompare(Person a1, Person a2) {
	    int  val =  a1.firstName.compareTo(a2.firstName);
	    if(val == 0){
	    	val =  a1.lasName.compareTo(a2.lasName);
	    }
	    return val;
	 }
}