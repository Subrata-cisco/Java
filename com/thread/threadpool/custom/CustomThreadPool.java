package com.thread.threadpool.custom;

import java.util.LinkedList;
import java.util.concurrent.Callable;

/*
 * Custom thread pool implementation.... Idea is
 * 1) Keep one Linked list to keep all threads
 * 2) Another Linked list for keeping all the task.
 * 3) 
 * 
 */
public class CustomThreadPool<V> {
	private Object waitLock = new Object();
	public Object getWaitLock() {
		return waitLock;
	}

	private final LinkedList<CustomThread<V>> threads;
	private final LinkedList<Callable<V>> tasks;
	private volatile boolean shutDown;
	private ResultListener<V> resultListener;

	public CustomThreadPool(int size, ResultListener<V> myResultListener) {
		tasks = new LinkedList<Callable<V>>();
		threads = new LinkedList<CustomThread<V>>();
		shutDown = false;
		resultListener = myResultListener;
		for (int i = 0; i < size; i++) {
			CustomThread<V> myThread = new CustomThread<V>();
			myThread.setPool(this);
			threads.add(myThread);
			myThread.start();
		}

	}

	public ResultListener<V> getResultListener() {
		return resultListener;
	}

	public void setResultListener(ResultListener<V> resultListener) {
		this.resultListener = resultListener;
	}

	public boolean isShutDown() {
		return shutDown;
	}

	public int getThreadPoolSize() {
		return threads.size();
	}

	public synchronized Callable<V> removeFromQueue() {
		return tasks.poll();
	}

	public synchronized void addToTasks(Callable<V> callable) {
		tasks.add(callable); 
	}

	public void submit(Callable<V> callable) {
		if (!shutDown) {
			addToTasks(callable);
			synchronized (this.waitLock) {
				waitLock.notify();
			}
		} else {
			System.out.println("task is rejected.. Pool shutDown executed");
		}
    }

	public void stop() {
		for (CustomThread<V> mythread : threads) {
			mythread.shutdown();
		}

		synchronized (this.waitLock) {
			waitLock.notifyAll();
		}

		for (CustomThread<V> mythread : threads) {
			try {
				mythread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
