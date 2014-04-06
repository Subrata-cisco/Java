package com.thread.reentrant;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DoorLockUsingLock {

	private int counter = 0;
	private Thread owner = null;
	private Lock lock = new ReentrantLock();
	private Condition lockCondition = lock.newCondition();

	public void lockItDown() throws InterruptedException {
		lock.lockInterruptibly();
		try {
			while ((counter > 0) && (owner != Thread.currentThread())) {
				lockCondition.await();
			}
			counter++;
			owner = Thread.currentThread();
		} finally {
			lock.unlock();
		}
	}

	public void lockItDownUninterruptibly() {
		lock.lock();
		try {
			while ((counter > 0) && (owner != Thread.currentThread())) {
				lockCondition.awaitUninterruptibly();
			}
			counter++;
			owner = Thread.currentThread();
		} finally {
			lock.unlock();
		}
	}

	public boolean tryLockItDown(long timeout, TimeUnit unit)
			throws InterruptedException {
		long time = unit.toNanos(timeout);
		long end = System.nanoTime() + time;
		boolean success = lock.tryLock(timeout, unit);
		if (!success) {
			return false;
		}
		try {
			time = end - System.nanoTime();
			while ((counter > 0) && (owner != Thread.currentThread())
					&& (time > 0)) {
				lockCondition.await(time, TimeUnit.NANOSECONDS);
				time = end - System.nanoTime();
			}
			if (time > 0) {
				counter++;
				owner = Thread.currentThread();
				return true;
			}
			return false;
		} finally {
			lock.unlock();
		}
	}

	public void unlockIt() throws IllegalMonitorStateException {
		lock.lock();
		try {
			if (counter == 0) {
				throw new IllegalMonitorStateException();
			}
			if (owner != Thread.currentThread()) {
				throw new IllegalMonitorStateException();
			}
			counter--;
			if (counter == 0) {
				owner = null;
				lockCondition.signal();
			}
		} finally {
			lock.unlock();
		}
	}
}
