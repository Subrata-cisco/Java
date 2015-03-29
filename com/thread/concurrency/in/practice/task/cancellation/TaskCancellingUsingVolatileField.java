package com.thread.concurrency.in.practice.task.cancellation;

/**
 * Lets print the numbers for 5 second and then close a task.
 * 
 * When to use it : whem the run method critical operation is not a blocking one.
 * 
 * @author subratas
 *
 */
public class TaskCancellingUsingVolatileField {

	public static void main(String[] args)  {
		
		WorkerTask task = new TaskCancellingUsingVolatileField().new WorkerTask();
		
		Thread workThread = new Thread(task);
		workThread.start();
		
		
		try {
			Thread.currentThread().sleep(1000L);
		} catch (InterruptedException e) {
		} finally {
			task.cancel();
		}
	}
	
	
	
	class WorkerTask implements Runnable {

		volatile boolean cancelled = false;

		@Override
		public void run() {
			
			// if there is a blocking call in while loop and this thread being cancelled , then it may not be cancelled at all.
			// e.g = producer is full and waiting , consumer cancels it for some reason and it also stops itself and queue is 
			// still full because the producer will never awake up and while loop will never be checked. Boooooommmmmmm.....
			while (!cancelled) {
				System.out.println("Print .....");
			}

		}

		public void cancel() {
			cancelled = true;
		}

	}
	
}


