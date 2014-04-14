package com.thread.common;

/*
 * This is also called binary semaphore...
 */
public class Mutax {
	private boolean locked = false;

	Mutax(int initial) {
		locked = (initial == 0);
	}

	public synchronized void acquire()  {
		while (locked) {
			try {
				wait();
			} catch (InterruptedException e) {
				notify();
			}
		}
		locked = true;
	}

	public synchronized void release() {
		if (locked) {
			notify();
		}
		locked = false;
	}
}
