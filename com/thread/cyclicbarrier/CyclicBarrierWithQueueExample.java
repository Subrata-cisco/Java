package com.thread.cyclicbarrier;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
Problem to be solved.....
Basically one update should do the following steps:

Determine all documents to update (partial updates also possible)
Flag all those documents with a unique tag (using UUID for example)
Run updates concurrently and for each updated document and remove the update tag
When all update threads are done, remove all still tagged documents and wait for the next update
**/

public class CyclicBarrierWithQueueExample {

	private static BlockingQueue<String> queue;

	private static CyclicBarrier barrier;

	private static String updateTag;

	private static String poison = "+++ stop +++";

	public static void main(String[] args) {
		// prepare update thread pool
		int numberOfThreads = 4;
		queue = new LinkedBlockingQueue<String>();
		barrier = new CyclicBarrier(numberOfThreads, new Runnable() {
			public void run() {
				// delete all not updated with updateTag
				// ...
				log("****** Update done, remove not updated: updateTag= " + updateTag);
				updateTag = null;
			}
		});
		ExecutorService executor = Executors.newFixedThreadPool(4);
		for (int i = 0; i < numberOfThreads; i++) {
			executor.submit(new Worker(i, poison, queue, barrier));
		}

		// import all
		barrier.reset();
		List<String> sqlPerLanguge = Collections.nCopies(5, "language");
		updateTag = UUID.randomUUID().toString();
		log("Update all, flagged all with updateTag: updateTag= " + updateTag);
		for (String language : sqlPerLanguge) {
			// create a sql query per language
			try {
				queue.put(language);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// add poison for every thread
		for (int i = 0; i < numberOfThreads; i++) {
			try {
				queue.put(poison);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// wait for next update (NOT needed in real circumstances)
		while (updateTag != null) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}

		// update some pks
		barrier.reset();
		List<String> somePKs = Collections.nCopies(50, "pk");
		updateTag = UUID.randomUUID().toString();
		log("Update some pks, flagged all products for pks with updateTag: updateTag= "
				+ updateTag);
		for (String pk : somePKs) {
			// create a sql query per 10 products
			try {
				queue.put(pk);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// add poison for every thread
		for (int i = 0; i < numberOfThreads; i++) {
			try {
				queue.put(poison);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// wait for next update (NOT needed in real circumstances)
		while (updateTag != null) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}

		// shutdown
		log("shutdown");
		executor.shutdownNow();
	}

	public static void log(String str) {
		System.out.println(str);
	}

	private static class Worker implements Runnable {

		private int id;
		private String badValue;
		private BlockingQueue<String> queue;
		private CyclicBarrier barrier;

		public Worker(int id, String poison, BlockingQueue<String> queue,
				CyclicBarrier barrier) {
			this.id = id;
			this.badValue = poison;
			this.queue = queue;
			this.barrier = barrier;
		}

		public void run() {
			boolean run = true;
			while (run) {
				try {
					try {
						String query = queue.take();
						if (query.equals(badValue)) {
							barrier.await();
						} else {
							log("Thread executes update: id= " + id+" text ::"+query);
							Thread.sleep(100);
						}
					} catch (BrokenBarrierException e) {
						e.printStackTrace();
						run = false;
					}
				} catch (InterruptedException e) {
					run = false;
				}
			}
		}
	}
}
