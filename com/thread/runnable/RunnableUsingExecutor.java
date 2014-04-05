package com.thread.runnable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * What is it : Used for running as part of java thread.
 *
 * Alternative of : Nothing (basic feature to run a thread)
 *
 * When to use : To execute as part of new thread where it wont return any value or wont throw any checked exception.
 *
 * Example description : Simply calculating the sum of array and silently printing it.
 *
 * @author {Subrata Saha(saha.subrata@gmail.com)}
 *
 */
public class RunnableUsingExecutor {
	
	int arr[] = {1,4,7,9,3,5};
	
	
	//////////////////////////////////////////////////////////////////////////////////////
	/* create the runnable object */
	Runnable runnable = new Runnable(){
		int result = 0;
		public void run() {
			result = 0;
			for(int i : arr){
				result = result + i;
			}
			System.out.println("***************** The result is ::"+result);
		}
	};
	
	private void executeTheRunnable(){
		ExecutorService service = Executors.newFixedThreadPool(4);
		service.submit(runnable);
	}
	
	public static void main(String[] args) {
		RunnableUsingExecutor executor = new RunnableUsingExecutor();
		executor.executeTheRunnable();
	}

}

