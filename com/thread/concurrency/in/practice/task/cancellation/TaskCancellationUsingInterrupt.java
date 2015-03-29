package com.thread.concurrency.in.practice.task.cancellation;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.thread.concurrency.in.practice.task.cancellation.TaskCancellingUsingVolatileField.WorkerTask;

public class TaskCancellationUsingInterrupt {

	public static void main(String[] args) {
		
		WorkerTask task = new TaskCancellationUsingInterrupt().new WorkerTask(new LinkedBlockingQueue<>());
		
		Thread workThread = new Thread(task);
		workThread.start();
		
		
		try {
			Thread.currentThread().sleep(1000L);
		} catch (InterruptedException e) {
			
		} finally {
			task.cancel();
		}
		
		
	}
	
	class WorkerTask extends Thread {

	    private final BlockingQueue<BigInteger> queue ;
	    
	    WorkerTask(BlockingQueue<BigInteger> queue){
	    	this.queue = queue;
	    }
	    
		@Override
		public void run() {
			BigInteger b = BigInteger.ONE;
			
			try {
			
			  // Cancellation policy implementation. Never use volatile variable here it will be buggy because of 	
			  // TaskCancellingUsingVolatileField run method explanation.	
			  while(!Thread.currentThread().isInterrupted()){
				  queue.put(b.nextProbablePrime());
			  }
				
			} catch (InterruptedException e) {
				// it is ok as we have taken care of the cancellation policy above.
			}

		}

		public void cancel() {
			interrupt();
		}

	}
}



