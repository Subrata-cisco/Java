package com.thread.executor;

import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceVarity {
	
	public static void main(String[] args) {
		ScheduledExecutorService  es = Executors.newScheduledThreadPool(3);
		es.scheduleAtFixedRate(new Worker(), 4, 5, TimeUnit.SECONDS);
	}

	void allDefn() throws InterruptedException {
		Executors.newFixedThreadPool(3);
		Executors.newCachedThreadPool();

		ThreadFactory threadFactory = null;
		Executors.newCachedThreadPool(threadFactory);

		Executors.newScheduledThreadPool(3);

		Executors.newWorkStealingPool();

		Runnable runnableCommand = null;
		Executors.callable(runnableCommand);

		ExecutorService executorService = null;

		executorService.execute(runnableCommand); // no return result
		executorService.submit(runnableCommand); // returns a Future object.

		Set<Callable<String>> tasks = null;
		executorService.invokeAll(tasks); // Set of task throws RejectedExecutionException..

		executorService.shutdown();
		executorService.shutdownNow();
	}
}

class Worker implements Runnable {
	@Override
	public void run() {
		System.out.println("ExecutorServiceVarity.worker.run()");
	}
}
