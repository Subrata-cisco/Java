package com.thread.concurrentcollection;

import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentNavigableMapExample {

	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		ConcurrentNavigableMap<String,String> map = new ConcurrentSkipListMap<>();  
		

		map.put("1", "one"); 
		map.put("2", "two");
		map.put("3", "three");

		ConcurrentNavigableMap<String,String> headMap = map.headMap("2");     // map containg only 1
		ConcurrentNavigableMap<String,String> tailMap = map.tailMap("2");     //  map containg only 3
		ConcurrentNavigableMap<String,String> subMap = map.subMap("2", "3");  // map containg only 2 
	}
}
