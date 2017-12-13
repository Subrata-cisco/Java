package com.thread.threadfactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * The thread facory can be used to do some custom modification. e.g put all the
 * thread as daemon
 * 
 * @author Subrata Saha (ssaha2)
 *
 */
public class ThreadFactoryExample {

	public static void main(String[] args) {

		// Lamda way
		final ThreadFactory threadFactory = runnable -> {
			final Thread thread = new Thread(runnable, "HelloWorldThread");
			thread.setDaemon(true);
			return thread;
		};

		final ExecutorService executor = Executors.newCachedThreadPool(threadFactory);
		executor.submit(
				() -> System.out.println("Hello World is runnning as daemon ? " + Thread.currentThread().isDaemon()));
	}

}

/**
 * Non Lamda way
 * 
 * @author Subrata Saha (ssaha2)
 *
 */
class CustomThreadFactory implements ThreadFactory {

	@Override
	public Thread newThread(Runnable r) {
		final Thread thread = new Thread(r, "HelloWorldThread");
		thread.setDaemon(true);
		return thread;
	}

}
