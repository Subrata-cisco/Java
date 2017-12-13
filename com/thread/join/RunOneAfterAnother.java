package com.thread.join;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class RunOneAfterAnother {

	public static void main(String[] args) throws InterruptedException {

		Thread t1 = new Thread(new FirstThread());

		Thread t2 = new Thread(new SecondThread());

		Thread t3 = new Thread(new ThirdThread());

		t1.start();
		t1.join();

		t2.start();
		t2.join();

		t3.start();
		t3.join();

		// Create a queue of threads and pop them and join them..
	}

}

class Worker implements Runnable {

	BlockingQueue<Integer> q = new LinkedBlockingQueue<>();
	Worker next = null; // next worker in the chain

	public void setNext(Worker t) {
		this.next = t;
	}

	public void accept(int i) {
		q.add(i);
	}

	@Override
	public void run() {
		while (true) {
			int i;
			try {
				i = q.take(); // this blocks the queue to fill-up
				System.out.println(Thread.currentThread().getName() + i);
				if (next != null) {
					next.accept(i + 1); // Pass the next number to the next worker
				}
				Thread.sleep(500); // Just sleep to notice the printing.
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class PrintNumbersSequentially {

	void startThread() {
		Worker w1 = new Worker();
		Worker w2 = new Worker();
		Worker w3 = new Worker();

		w1.setNext(w2);
		w2.setNext(w3);
		w3.setNext(w1);

		new Thread(w1, "Thread-1: ").start();
		new Thread(w2, "Thread-2: ").start();
		new Thread(w3, "Thread-3: ").start();

		// Till here all the threads have started, but no action takes place as the
		// queue is not filled for any worker. So Just filling up one worker.
		w1.accept(100);
	}
}

class FirstThread implements Runnable {

	@Override
	public void run() {
		System.out.println("FirstThread.run()");
		try {
			Thread.sleep(3000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

class SecondThread implements Runnable {

	@Override
	public void run() {
		System.out.println("SecondThread.run()");
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

class ThirdThread implements Runnable {

	@Override
	public void run() {
		System.out.println("ThirdThread.run()");
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
