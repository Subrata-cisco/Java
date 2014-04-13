package com.thread.threadpool.custom;

import java.util.concurrent.Callable;

public class CustomThread<V> extends Thread {

	private CustomThreadPool<V> pool;
	private boolean active = true;

	public boolean isActive() {
		return active;
	}

	public void setPool(CustomThreadPool<V> p) {
		pool = p;
	}

	public void run() {
		ResultListener<V> result = pool.getResultListener();
		Callable<V> task;
		while (true) {
			task = pool.removeFromQueue();
			if (task != null) {
				try {
					V output = task.call();
					result.finish(output);
				} catch (Exception e) {
					result.error(e);
				}
			} else {
				if (!isActive())
					break;
				else {
					synchronized (pool.getWaitLock()) {
						try {
							pool.getWaitLock().wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	void shutdown() {
		active = false;
	}
}
