package com.java8.streams;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;

/**
 * Reduction technique using collect method.
 * 
 * @author Subrata Saha.
 *
 */
public class StreamCollector {
	public static void main(String[] args) {

		Person p1 = new Person("Subrata", 32, "M");
		Person p2 = new Person("Sunita", 30, "F");
		Person p3 = new Person("Manoj", 35, "M");
		Person p4 = new Person("Papiya", 33, "F");
		Person p5 = new Person("John", 54, "M");
		Person p6 = new Person("Kavita", 47, "F");

		List<Person> list = new ArrayList<>();
		list.add(p1);
		list.add(p2);
		list.add(p3);
		list.add(p4);
		list.add(p5);
		list.add(p6);

		/////////////////////Custom implementation///////////////////////////
		AverageCalculator averageCollect = list
				.stream()
				.filter(p -> p.getSex() == "M")
				.map(Person::getAge)
				.collect(AverageCalculator::new, AverageCalculator::accept,
						AverageCalculator::combine);

		System.out.println("Average age of male members: "
				+ averageCollect.average());

		/////////////////////////Collectors.toList()///////////////////////////
		
		List<String> namesOfMaleMembersCollect = list.stream()
				.filter(p -> p.getSex().equals("F")).map(p -> p.getName())
				.collect(Collectors.toList());
		System.out.println("  All Female are as folows :: ");
		for (String name : namesOfMaleMembersCollect) {
			System.out.print("  Name ::" + name);
		}
		System.out.println();	
		
		////////////////////////groupingBy/////////////////////////////////////
		Map<String , List<Person>> byGender =
			    list
			        .stream()
			        .collect(
			            Collectors.groupingBy(Person::getSex));
		
		List<Person> male = byGender.get("M");
		List<Person> female = byGender.get("F");
		
		System.out.println("Male aftre group-by ::"+male);
		System.out.println("Female aftre group-by ::"+female);
		
		//////////////////////////groupingBy////////////////////////////////////
		
		Map<String, List<String>> namesByGender = list.stream().collect(
				Collectors.groupingBy(Person::getSex, Collectors.mapping(
						Person::getName, Collectors.toList())));
		
		List<String> maleName = namesByGender.get("M");
		List<String> femaleName = namesByGender.get("F");
		
		System.out.println("Male names using collectors mapping ::"+maleName);
		System.out.println("FeMale names using collectors mapping ::"+femaleName);
		
		/////////////////////////////////////////////////////////////////////////
		Map<String, Integer> totalAgeByGender = list.stream().collect(
				Collectors.groupingBy(Person::getSex,
						Collectors.reducing(0, Person::getAge, Integer::sum)));
		
		Integer maleTotal = totalAgeByGender.get("M");
		Integer femaleTotal = totalAgeByGender.get("F");
		
		System.out.println("Male total age using reducing ::"+maleTotal);
		System.out.println("FeMale total age using reducing ::"+femaleTotal);		
		///////////////////////////////////////////////////////////////////////////
		Map<String, Double> averageAgeByGender = list
			    .stream()
			    .collect(
			        Collectors.groupingBy(
			            Person::getSex,                      
			            Collectors.averagingInt(Person::getAge)));
		
		Double maleavg = averageAgeByGender.get("M");
		Double femaleavg = averageAgeByGender.get("F");
		
		System.out.println("Male avg age using averagingInt ::"+maleavg);
		System.out.println("FeMale avg age using averagingInt ::"+femaleavg);	
		
		//////////////////////////////////////////////////////////////////////////
		
		Map<Boolean, List<Person>> boysOrGirls =
				         list.stream()
				                 .collect(Collectors.partitioningBy(s -> s.getSex().equals("M")));
		
		List<Person> maleUsingPar = boysOrGirls.get(true);
		List<Person> femaleUsingPar = boysOrGirls.get(false);
		
		System.out.println("Male using partitioning ::"+maleUsingPar);
		System.out.println("Female using partitioning ::"+femaleUsingPar);
		
		/////////////////////////////////////////////////////////////////////////
		Map<String, String> namesByGenderUsingJoining = list.stream().collect(
				Collectors.groupingBy(Person::getSex, Collectors.mapping(
						Person::getName, Collectors.joining("->")  )));
		
		String allMaleNameDelSeparated = namesByGenderUsingJoining.get("M");
		String allFeMaleNameDelSeparated = namesByGenderUsingJoining.get("F");
		
		System.out.println("All male names joined by custom delimeter(->) using joining::"+allMaleNameDelSeparated);
		System.out.println("All female names joined by custom delimeter(->) using joining ::"+allFeMaleNameDelSeparated);
		
		
		///////////////////////////////////////////////////////////////////////////
		
		//Collectors.averaging***
		//Collectors.collectingAndThen(downstream, finisher)
		//Collectors.counting()
		//Collectors.groupingBy(classifier)
		//Collectors.groupingByConcurrent(classifier)
		//Collectors.joining()
		//Collectors.mapping(mapper, downstream)
		//Collectors.maxBy(comparator)
		//Collectors.maxBy(comparator)
		//Collectors.partitioningBy(predicate)
		//Collectors.reducing(op)
		//Collectors.summarizingDouble(mapper)
		//Collectors.toCollection(collectionFactory)
		//Collectors.toConcurrentMap(keyMapper, valueMapper)
		//Collectors.toList()
		//Collectors.toSet()
		//Collectors.toMap(keyMapper, valueMapper)
		
	}
}

class AverageCalculator implements IntConsumer {
	private int total = 0;
	private int count = 0;

	public double average() {
		return count > 0 ? ((double) total) / count : 0;
	}

	public void accept(int i) {
		total += i;
		count++;
	}

	public void combine(AverageCalculator other) {
		total += other.total;
		count += other.count;
	}
}
