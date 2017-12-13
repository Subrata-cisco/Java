package com.generics;

import java.util.Arrays;
import java.util.List;

public class WildCards {
	
	public static void main(String[] args) {
		WildCards.sumOfList(Arrays.asList(1, 2, 3));
		WildCards.sumOfList(Arrays.asList(1.1f, 2.5f, 3.3f));
	}

	public static double sumOfList(List<? extends Number> list) {
		double s = 0.0;
		for (Number n : list)
			s += n.doubleValue();
		return s;
	}

}
