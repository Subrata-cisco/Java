package com.thread.executor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author subratas
 *
 */
public class ThreadPoolExecutorExample{
	
	
	public static void main(String[] args) {
		RejectedExecutionHandler rejectionHandler = new RejectedExecutionHandlerImpl(); 
		ThreadFactory threadFactory = Executors.defaultThreadFactory();
		
		ThreadPoolExecutor executorPool = new ThreadPoolExecutor(2, 4, 10,
				TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2),
				threadFactory, rejectionHandler);
		
		for(int i = 0;i<10;i++){
			executorPool.execute(new Worker(i+1));
		}
		
		try {
			Thread.sleep(10000L);
		} catch (InterruptedException e) {
			
		}
		executorPool.shutdownNow();
	}
}

class RejectedExecutionHandlerImpl implements RejectedExecutionHandler{
	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		System.out.println("Rejected "+r.toString());
	}
}

class Worker implements Runnable {
	int cmdNo = -1;
	
	Worker(int no){
		this.cmdNo = no;
	}
	
	@Override
	public void run() {
		try {
			System.out.println("Worker.run() cmdNo ::"+cmdNo);
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			// nothing...
		}
	}

	@Override
	public String toString() {
		return "Worker [cmdNo=" + cmdNo + "]";
	}
}


