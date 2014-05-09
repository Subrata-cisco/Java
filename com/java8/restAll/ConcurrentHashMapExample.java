package com.java8.restAll;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/**
 * computeIfAbsent has amazing implementation just check it out.
 * @author Subrata Saha.
 *
 */
public class ConcurrentHashMapExample {
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++)
			System.out.println("f(" + i + ") = " + fibonacci(i));

		System.out.println(cache);
	}

	static Map<Integer, Integer> cache = new ConcurrentHashMap<>();

	static int fibonacci(int i) {
		if (i == 0)
			return i;

		if (i == 1)
			return 1;

		return cache.computeIfAbsent(i, (key) -> {
			System.out.println("Value not found so start calculating it for key :: " + key);
			return fibonacci(i - 2) + fibonacci(i - 1);
		});
	}
}
