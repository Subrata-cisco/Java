package com.thread.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 
 * What is it : Spawing a thread by both submit and execute.
 * 
 * Alternative of : submit vs execute And newFixedThreadPool vs newCachedThreadPool 
 * 
 * When to use :
 * 
 * Example description : creates two threads and submits 5 tasks in total. After
 * each thread completes its task, it takes the next one, In the code above, I
 * use executor.submit Method submit extends base method
 * 
 * Executor.execute(java.lang.Runnable) by creating and returning a Future that
 * can be used to cancel execution and/or wait for completion. Methods invokeAny
 * and invokeAll perform the most commonly useful forms of bulk execution,
 * executing a collection of tasks and then waiting for at least one, or all, to
 * complete. (Class ExecutorCompletionService can be used to write customized
 * variants of these methods.)
 * 
 * @author {Subrata Saha(saha.subrata@gmail.com)}
 * 
 */
public class ExecutorSubmitExecuteExample {

	public static void main(String[] args) {

		Boolean isCompleted = false;

		ExecutorService executor = Executors.newFixedThreadPool(3);

		for (int i = 0; i < 10; i++) { // executing 10 task
			executor.execute(new SmallTask(i));
			
			//Future future = executor.submit(new SmallTask(i));
			// using Future handle you can cancel,isDone,isTermi etc can be checked.
		}
		
		
		executor.shutdown(); // no new task is accepted but accepted tasl shall continue

		System.out.println("All tasks submitted.");

		try {
			// wait for 100 seconds to finish
			isCompleted = executor.awaitTermination(100, TimeUnit.SECONDS); 
				
		    // interrupt thread all the threads now if execution not yet finished
			executor.shutdownNow(); 
		} catch (InterruptedException e) {
			
		}

		if (isCompleted) {
			System.out.println("All tasks completed.");
		} else {
			System.out.println("Timeout " + Thread.currentThread().getName());
		}
	}

}

class SmallTask implements Runnable {

	private int id;

	public SmallTask(int id) {
		this.id = id;
	}

	public void run() {
		System.out.println("Starting: " + id);

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			System.out.println("  Thread "+ Thread.currentThread().getName()+"Interrupted !!");
		}
		
		System.out.println("Completed: " + id);
	}
}
