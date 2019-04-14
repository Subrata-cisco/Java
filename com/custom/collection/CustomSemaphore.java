package com.custom.collection;

public class CustomSemaphore {
	private int permits;

	public CustomSemaphore(int permits) {
		this.permits = permits;
	}

	
	public synchronized void acquire() throws InterruptedException {
		// Acquires a permit, if permits is greater than 0 decrements
		// the number of available permits by 1.
		if (permits > 0) {
			permits--;
		}
		// permit is not available wait, when thread
		// is notified it decrements the permits by 1
		else {
			this.wait();
			permits--;
		}
	}
	
	public synchronized void release() {
		// increases the number of available permits by 1.
		permits++;

		// If permits are greater than 0, notify waiting threads.
		if (permits > 0)
			this.notify();
	}
}
