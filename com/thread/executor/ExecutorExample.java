package com.thread.executor;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * What is it : Used to execute thread from a pool.
 * 
 * Alternative of : NA
 * 
 * When to use : Given task to be finished by some thread pool having threads in
 * it. using [newFixedThreadPool , newCachedThreadPool]
 * 
 * Typically we use the newCachedThreadPool() when we need the thread to be
 * spawned immediately, even if all of the threads currently running are busy.
 * e.g spawning timer tasks. The number of concurrent
 * jobs are immaterial because we never spawn very many but I want them to run
 * when they are requested and I want them to re-use dormant threads.So Every time 
 * you submit a job to the pool, do you want it to start running immediately? 
 * If this is true then we should use a newCachedThreadPool().
 * 
 * We used newFixedThreadPool() when I want to limit the number of concurrent
 * tasks running at any one point to maximize performance and not swamp application
 * server. For example if I am processing 100k lines from a file, one line at a
 * time, I don't want each line to start a new thread but I want some level of
 * concurrency so I allocate (for example) 10 fixed threads to run the tasks until 
 * the pool is exhausted. So you want to limit the amount of concurrency going on 
 * to maximize the performance of your box then you should run a fixed pool size.
 * 
 * 
 * Example description : Printing the hashmap elements by different threads.
 * ConcurrentHashMap.
 * 
 * @author {Subrata Saha(saha.subrata@gmail.com)}
 * 
 */
public class ExecutorExample {

	public static void main(String[] args) {
		ExecutorExample example = new ExecutorExample();
		example.finishAllWorkUsingFixedThreadPool();
	}
	
	ConcurrentHashMap<Integer, int[]> map = new ConcurrentHashMap();
	
	private void finishAllWorkUsingFixedThreadPool(){
		int arr1[] = {1,2,3};
		int arr2[] = {4,5,6};
		int arr3[] = {7,8,9};
		int arr4[] = {10,11,12};
		int arr5[] = {13,14,15};
		
		map.put(1, arr1);
		map.put(2, arr2);
		map.put(3, arr3);
		map.put(4, arr4);
		map.put(5, arr5);
		
		final int noOfThreads = 2;
	    final int noOfTasks = map.size();
	    
	    ExecutorService service = Executors.newFixedThreadPool(noOfThreads); // Fixed no of threads
	    //ExecutorService service = Executors.newCachedThreadPool(); // jvm will take care of required no of threads.
	    
	    // queue some tasks 
	    for (int i = 0; i < noOfTasks; i++) {
	        service.submit(new ThreadTask(i+1, map));
	    }
	}
}

class ThreadTask implements Runnable {
	private final int id;
	ConcurrentHashMap<Integer, int[]> map;

	public ThreadTask(int nextId, ConcurrentHashMap<Integer, int[]> map) {
		this.id = nextId;
		this.map = map;
	}

	public void run() {
		int[] arr = map.get(id);
		if(arr != null){
			int sum = 0;
			for(int no : arr){
				sum += no;
			}
			System.out.println("***************** arr sum is ::"+sum+" printed by ::"+Thread.currentThread().getName());
		}else{
			System.out.println("***************** arr is null");
		}
	}
}

