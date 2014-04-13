package com.thread.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * What it is :Condition can be considered as a separation of monitor methods
 * (wait(), notify() & notifyAll()). For each ReentrantLock we can define a set
 * of conditions and based on that we can make the threads waiting.
 * 
 * Alternative of : (wait(), notify() & notifyAll())
 * 
 * When to use :
 * 
 * Example description :
 * 
 * @author Subrata Saha (saha.subrata@gmail.com)
 * 
 */

public class ConditionExample {

	public static void main(String[] args) {

		ConditionExample conditionExample = new ConditionExample();
		FixedBuffer fb = conditionExample.new FixedBuffer(5);
		
		new Thread(conditionExample.new Producer(fb)).start();
		new Thread(conditionExample.new Consumer(fb)).start();
	}

	/*
	 * The Producer class to produce string......
	 */
	class Producer implements Runnable {

		FixedBuffer fb;

		Producer(FixedBuffer fb) {
			this.fb = fb;
		}

		@Override
		public void run() {
			for (int i = 0; i < 100; i++) {
				try {
					fb.deposit("Subrata");
				} catch (InterruptedException e) {
					System.out
							.println("**********Subrata :: Deposit Interupted !!");
				}
			}

		}

	}

	/*
	 * The consumer class to consume the text.
	 */
	class Consumer implements Runnable {

		FixedBuffer fb;

		Consumer(FixedBuffer fb) {
			this.fb = fb;
		}

		@Override
		public void run() {
			for (int i = 0; i < 100; i++) {
				try {
					String consumed = fb.fetch();
					System.out.println("**********Subrata :: Consumed ::"
							+ consumed);
				} catch (InterruptedException e) {
					System.out
							.println("**********Subrata :: Deposit Interupted !!");
				}
			}

		}

	}

	/*
	 * Fixed Queue of some size where insertion is not possible if it is full.
	 */
	public class FixedBuffer {
		private final String[] buffer;
		private final int capacity;

		private int front;
		private int rear;
		private int count;

		private final Lock lock = new ReentrantLock();

		private final Condition notFull = lock.newCondition();
		private final Condition notEmpty = lock.newCondition();

		public FixedBuffer(int capacity) {
			super();
			this.capacity = capacity;
			buffer = new String[capacity];
		}

		public void deposit(String data) throws InterruptedException {
			lock.lock();

			try {
				while (count == capacity) {
					System.out.println("**********Subrata :: Waiting to be deposited ::"+data);
					notFull.await();
				}

				buffer[rear] = data;
				System.out.println("**********Subrata :: Deposited ::" + data);
				rear = (rear + 1) % capacity;
				count++;

				notEmpty.signal();
			} finally {
				lock.unlock();
			}
		}

		public String fetch() throws InterruptedException {
			lock.lock();

			try {
				while (count == 0) {
					System.out.println("**********Subrata :: waiting to consumed data ::");
					notEmpty.await();
				}

				String result = buffer[front];
				front = (front + 1) % capacity;
				count--;

				notFull.signal();

				return result;
			} finally {
				lock.unlock();
			}
		}
	}
}
