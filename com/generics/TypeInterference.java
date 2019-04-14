package com.generics;

public class TypeInterference {

	public static void main(String[] args) {
		java.util.ArrayList<Box<Integer>> listOfIntegerBoxes = new java.util.ArrayList<>();
		
		// This is how it is called.
		TypeInterference.<Integer>addBox(Integer.valueOf(10), listOfIntegerBoxes);
		TypeInterference.addBox(Integer.valueOf(10), listOfIntegerBoxes);
	}

	public static <U> void addBox(U u, java.util.List<Box<U>> boxes) {
		Box<U> box = new Box<>();
		box.set(u);
		boxes.add(box);
	}

}

class Box<T> {
	public void set(T t) {
		// TODO Auto-generated method stub
	}
}
