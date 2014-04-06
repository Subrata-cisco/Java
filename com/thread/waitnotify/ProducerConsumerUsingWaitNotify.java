package com.thread.waitnotify;

import java.util.Vector;

public class ProducerConsumerUsingWaitNotify {
	public static void main(String args[]) {
		Vector sharedQueue = new Vector();
		int size = 50;
		Thread prodThread = new Thread(new Producer(sharedQueue, size),
				"Producer");
		Thread consThread = new Thread(new Consumer(sharedQueue, size),
				"Consumer");
		prodThread.start();
		consThread.start();
	}
}

class Producer implements Runnable {

	private final Vector sharedQueue;
	private final int SIZE;

	public Producer(Vector sharedQueue, int size) {
		this.sharedQueue = sharedQueue;
		this.SIZE = size;
	}

	@Override
	public void run() {
		int i = 0;
		while (true) {
			System.out.println("Produced: " + i);
			try {
				produce(i);
			} catch (InterruptedException ex) {
			}
			i++;
		}
	}

	private void produce(int i) throws InterruptedException {

		// wait if queue is full
		while (sharedQueue.size() == SIZE) {
			synchronized (sharedQueue) {
				System.out.println("Queue is full "
						+ Thread.currentThread().getName()
						+ " is waiting , size: " + sharedQueue.size());

				sharedQueue.wait();
			}
		}

		// producing element and notify consumers
		synchronized (sharedQueue) {
			sharedQueue.add(i);
			sharedQueue.notifyAll();
		}
	}
}

class Consumer implements Runnable {

	private final Vector sharedQueue;
	private final int SIZE;

	public Consumer(Vector sharedQueue, int size) {
		this.sharedQueue = sharedQueue;
		this.SIZE = size;
	}

	@Override
	public void run() {
		while (true) {
			try {
				System.out.println("Consumed: " + consume());
				Thread.sleep(50);
			} catch (InterruptedException ex) {
			}

		}
	}

	private int consume() throws InterruptedException {
		// wait if queue is empty
		while (sharedQueue.isEmpty()) {
			synchronized (sharedQueue) {
				System.out.println("Queue is empty "
						+ Thread.currentThread().getName()
						+ " is waiting , size: " + sharedQueue.size());

				sharedQueue.wait();
			}
		}

		// Otherwise consume element and notify waiting producer
		synchronized (sharedQueue) {
			sharedQueue.notifyAll();
			return (Integer) sharedQueue.remove(0);
		}
	}
}
