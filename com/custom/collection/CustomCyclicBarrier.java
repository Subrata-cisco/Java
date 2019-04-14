package com.custom.collection;

public class CustomCyclicBarrier {

	int initialParties; // total parties
	int partiesAwait; // parties yet to arrive
	Runnable cyclicBarrrierEvent;

	public CustomCyclicBarrier(int parties, Runnable cyclicBarrrierEvent) {
		initialParties = parties;
		partiesAwait = parties;
		this.cyclicBarrrierEvent = cyclicBarrrierEvent;
	}

	public synchronized void await() throws InterruptedException {
		// decrements awaiting parties by 1.
		partiesAwait--;

		// If the current thread is not the last to arrive, thread will wait.
		if (partiesAwait > 0) {
			this.wait();
		}
		/*
		 * If the current thread is last to arrive, notify all waiting threads,
		 * and launch event
		 */
		else {
			/*
			 * All parties have arrive, make partiesAwait equal to
			 * initialParties, so that CyclicBarrier could become cyclic.
			 */
			partiesAwait = initialParties;

			notifyAll(); // notify all waiting threads

			cyclicBarrrierEvent.run(); // launch event
		}
	}

}
