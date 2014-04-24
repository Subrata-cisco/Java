package com.thread.phaser;

import java.util.concurrent.Phaser;

/*
 * A reusable synchronization barrier, similar in functionality to CyclicBarrier and CountDownLatch but supporting more flexible usage.
 * Registration - The number of parties registered to synchronize on a phaser may vary over time. 
 * Synchronization - Like a CyclicBarrier, a Phaser may be repeatedly awaited.
 * Arrival - Methods arrive() and arriveAndDeregister() record arrival. 
 * Waiting - Method awaitAdvance(int) requires an argument indicating an arrival phase number, 
 *    and returns when the phaser advances to (or is already at) a different phase. 
 * Termination - A phaser may enter a termination state, that may be checked using method isTerminated()
 * Tiering - Phasers may be tiered (i.e., constructed in tree structures) to reduce contention.
 * 
 * Dynamic number of parties
 * Reusable
 * Advanceable : phaser.arrive() that how it advance
 */

public class PhaserExample {
	public static void main(String[] args) {
		Phaser delOrder = new Phaser(1);
		System.out.println("Starting to process the order.");
		new Worker(delOrder, "cook");
		new Worker(delOrder, "waiter");
		new Worker(delOrder, "attendent");
		for (int i = 1; i <= 3; i++) { // 3 order
			delOrder.arriveAndAwaitAdvance();
			System.out.println("order " + i + " completed");
		}
		delOrder.arriveAndDeregister();
		System.out.println("Process completed");
	}
}

class Worker extends Thread {
	Phaser workerPhaser;

	Worker(Phaser workerPhaser, String name) {
		this.setName(name);
		this.workerPhaser = workerPhaser;
		workerPhaser.register();// this where thread register
		this.start();
	}

	public void run() {
		for (int i = 1; i <= 3; i++) { // 3 food items
			System.out.println(getName() + "  doing this work for order " + i);
			if (i == 3) { // no food items
				workerPhaser.arriveAndDeregister(); // work completed move to
													// next order
			} else {
				workerPhaser.arriveAndAwaitAdvance();// wait for other to
														// complete their work
			}
			try {
				Thread.sleep(5000);// to simulate processing time
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
