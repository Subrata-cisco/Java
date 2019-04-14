package com.generics;

import java.util.ArrayList;
import java.util.List;
/*
You can add null.
You can invoke clear.
You can get the iterator and invoke remove.
You can capture the wildcard and write elements that you've read from the list.
*/
public class ReadOnlyGenerics {

	public static void main(String[] args) {
		List<EvenNumber> le = new ArrayList<>();
		List<? extends NaturalNumber> ln = le;
		
		// compile-time error because it is read only
		//ln.add(new NaturalNumber(35));  
	}
}

class NaturalNumber {
	private int i;
	public NaturalNumber(int i) {
		this.i = i;
	}
}

class EvenNumber extends NaturalNumber {
	public EvenNumber(int i) {
		super(i);
	}
}
