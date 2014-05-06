package com.java8.streams;

import java.util.ArrayList;
import java.util.List;

public class StreamReductionExample {

	public static void main(String[] args) {
		StreamReductionExample example = new StreamReductionExample();
		Person p1 =  example. new Person("Subrata",32,"M");
		Person p2 =  example. new Person("Ajay",30,"F");
		Person p3 =  example. new Person("Ajay",35,"M");
		Person p4 =  example. new Person("Ajay",33,"F");
		Person p5 =  example. new Person("Ajay",54,"M");
		Person p6 =  example. new Person("Ajay",47,"F");
		
		List<Person> list = new ArrayList<>();
		list.add(p1);
		list.add(p2);
		list.add(p3);
		list.add(p4);
		list.add(p5);
		list.add(p6);
		
		double avgAgeForMale = list.stream().filter(p->p.getSex().equals("M")).mapToInt(Person::getAge).average().getAsDouble();
		double avgAgeForFeMale = list.stream().filter(p->p.getSex().equals("F")).mapToInt(Person::getAge).average().getAsDouble();
		
		System.out.println(" avgAgeForMale ::"+avgAgeForMale+" avgAgeForFeMale ::"+avgAgeForFeMale);
		
		double sumAgeForMale = list.stream().filter(p->p.getSex().equals("M")).mapToInt(Person::getAge).sum();
		double sumAgeForFeMale = list.stream().filter(p->p.getSex().equals("F")).mapToInt(Person::getAge).sum();
		
		System.out.println(" sumAgeForMale ::"+sumAgeForMale+" sumAgeForFeMale ::"+sumAgeForFeMale);
		
		double maxAgeForMale = list.stream().filter(p->p.getSex().equals("M")).mapToInt(Person::getAge).max().getAsInt();
		double maxAgeForFeMale = list.stream().filter(p->p.getSex().equals("F")).mapToInt(Person::getAge).max().getAsInt();

		System.out.println(" maxAgeForMale ::"+maxAgeForMale+" maxAgeForFeMale ::"+maxAgeForFeMale);
		
		double maxAgeForMaleLamda = list.stream().filter(p->p.getSex().equals("M")).mapToInt(Person::getAge).reduce(0, (a,b)->a+b);
		double maxAgeForFeMaleLamda = list.stream().filter(p->p.getSex().equals("F")).mapToInt(Person::getAge).reduce(0, (a,b)->a+b);
		
		System.out.println(" maxAgeForMaleLamda ::"+maxAgeForMaleLamda+" maxAgeForFeMaleLamda ::"+maxAgeForFeMaleLamda);

	}

	class Person {
		String name;
		int age;
		String sex;

		public Person(String name, int age,String sex) {
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
		
		public String getSex(){
			return sex;
		}

	}
}
