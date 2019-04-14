package com.thread.syncqueue;

import java.util.concurrent.SynchronousQueue;
/**
 * The order can be reversed for producer and consumer.
 * It is just a handover to another thread.
 * Exactly one item in the queue.
 * It supports fair policy.
 * 
 * @author Subrata Saha (ssaha2)
 *
 */
public class SynchronousQueueExample {
	public static void main(String[] args) {
		final SynchronousQueue<String> queue = new SynchronousQueue<String>();
		
		
		Thread producer = new Thread("PRODUCER") {
			public void run() {
				String event = "FOUR";
				try {
					queue.put(event);
					System.out.printf("[%s] published event : %s %n", Thread.currentThread().getName(), event);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		producer.start();

		Thread consumer = new Thread("CONSUMER") {
			public void run() {
				try {
					String event = queue.take();
					System.out.printf("[%s] consumed event : %s %n", Thread.currentThread().getName(), event);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		consumer.start();

	}
}
