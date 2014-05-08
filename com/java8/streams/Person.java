package com.java8.streams;
/**
 * Utility class to be used for demonstrating java 8 new features.
 * @author Subrata Saha.
 *
 */
public class Person {
	String name;
	int age;
	String sex;

	public Person(String name, int age, String sex) {
		super();
		this.name = name;
		this.age = age;
		this.sex = sex;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public String getSex() {
		return sex;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + "]";
	}
}
