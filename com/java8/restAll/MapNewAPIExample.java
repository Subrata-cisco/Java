package com.java8.restAll;

import java.util.HashMap;
import java.util.Map;

public class MapNewAPIExample {
	public static void main(String[] args) throws Exception {
		System.out.println("\ncompute()");
		System.out.println("---------");
		compute();

		System.out.println("\nforEach()");
		System.out.println("---------");
		forEach();

		System.out.println("\ngetOrDefault()");
		System.out.println("------------");
		getOrDefault();

		System.out.println("\nmerge()");
		System.out.println("---------");
		merge();
	}

	private static void compute() {
		Map<String, Integer> map = map();

		System.out.println(map.compute("A", (k, v) -> v == null ? 42 : v + 41));
		System.out.println(map);

		System.out.println(map.compute("X", (k, v) -> v == null ? 42 : v + 41));
		System.out.println(map);
	}

	private static void forEach() {
		Map<String, Integer> map = map();

		map.forEach((k, v) -> System.out.println(k + "=" + v));
	}

	private static void getOrDefault() {
		Map<String, Integer> map = map();

		System.out.println(map.getOrDefault("X", 42));

		// Pay attention to nulls!
		map.put("X", null);
		try {
			System.out.println(map.getOrDefault("X", 21) + 21);
		} catch (NullPointerException nope) {
			nope.printStackTrace();
		}
	}

	private static void merge() {
		Map<String, Integer> map = map();

		map.put("X", 1);
		System.out.println(map.merge("X", 1, (v1, v2) -> null));
		System.out.println(map);
	}

	private static Map<String, Integer> map() {
		Map<String, Integer> map = new HashMap<>();
		map.put("A", 1);
		map.put("B", 2);
		map.put("C", 3);
		return map;
	}
}
