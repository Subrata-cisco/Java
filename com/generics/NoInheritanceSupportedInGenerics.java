package com.generics;

public class NoInheritanceSupportedInGenerics {

	public static void main(String[] args) {
		Box1<Integer> box = new Box1<>();
		box.set(2);
		
		// not possible because inheritance not allowed in generics.
		// box.boxTest(box);  
	}

}

class Box1<T> {
	public void set(T t) {
		// TODO Auto-generated method stub
	}
	public void boxTest(Box<Number> n) {
	/* ... */ }
}