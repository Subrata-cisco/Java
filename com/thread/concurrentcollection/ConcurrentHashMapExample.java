package com.thread.concurrentcollection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*
 * The ConcurrentHashMap is very similar to the java.util.HashTable class, except that ConcurrentHashMap offers better 
 * concurrency than HashTable does. 
 * 1) ConcurrentHashMap does not lock the Map while you are reading from it. 
 * 2) ConcurrentHashMap does not lock the entire Map when writing to it. It only locks the part of the Map 
 that is being written to, internally. [i.e segments]
 * 3) ConcurrentHashMap does not throw ConcurrentModificationException if the ConcurrentHashMap is changed while being iterated.
 * 4) It has thread safe iterator but the Iterator is not designed to be used by more than one thread though.
 * 5) It uses Lock for critical section data but not synchronized .
 * 6) It is worth knowing is use of putIfAbsent() method.
 *    [because, during put operation whole map is not locked, and while one thread is putting value, other thread's get() 
 *    call can still return null which result in one thread overriding value inserted by other thread ]
 * 7) ConcurrentHashMap doesn't allow null as key or value.
 * 8) [initialCapacity,loadFactor,concurrencyLevel = default is 16, The idea is that the number of shards should equal the number
 *  of concurrent writer threads that you normally see] best practice = 8, 0.9f, 1
 */
public class ConcurrentHashMapExample {
	public static void main(String[] args) {

		// ConcurrentHashMap
		Map<String, String> myMap = new ConcurrentHashMap<String, String>();
		myMap.put("1", "1");
		myMap.put("2", "1");
		myMap.put("3", "1");
		myMap.put("4", "1");
		myMap.put("5", "1");
		myMap.put("6", "1");
		System.out.println("ConcurrentHashMap before iterator: " + myMap);
		Iterator<String> it = myMap.keySet().iterator();

		while (it.hasNext()) {
			String key = it.next();
			if (key.equals("3"))
				myMap.put(key + "new", "new3");
		}
		System.out.println("ConcurrentHashMap after iterator: " + myMap);

		// HashMap
		myMap = new HashMap<String, String>();
		myMap.put("1", "1");
		myMap.put("2", "1");
		myMap.put("3", "1");
		myMap.put("4", "1");
		myMap.put("5", "1");
		myMap.put("6", "1");
		System.out.println("HashMap before iterator: " + myMap);
		Iterator<String> it1 = myMap.keySet().iterator();

		while (it1.hasNext()) {
			String key = it1.next();
			if (key.equals("3"))
				myMap.put(key + "new", "new3"); // this will give problem...
		}
		System.out.println("HashMap after iterator: " + myMap);
	}
}
