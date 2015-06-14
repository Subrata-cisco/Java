package com.basic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CustomComparatorUsingMultipleFields {
	
	public static void main(String[] args) {
		
		Employee e1 = new Employee("A","C");
		Employee e2 = new Employee("B","C");
		Employee e3 = new Employee("A","B");
		Employee e4 = new Employee("","B");
		
		List<Employee> list = new ArrayList<>();
		list.add(e1);
		list.add(e2);
		list.add(e3);
		list.add(e4);
		
		Collections.sort(list);
		
	}
}


class EmployeeFirstNameComparator implements Comparator<Employee> {

	@Override
	public int compare(Employee o1, Employee o2) {
		//return o1.firstName.compareTo(o2.firstName);
		return o1.compareTo(o2);
	}
}

class Employee implements Comparable<Object> {
	String firstName;
	String lastName;
	
	public Employee(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		return true;
	}

	@Override
	public int compareTo(Object o) {
		Employee emp = (Employee) o;
		int i = emp.firstName.compareTo(this.firstName);
		if(i == 0){
			i = emp.lastName.compareTo(this.lastName);
		}
		return i;
	}
	
}
