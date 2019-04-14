package com.custom.collection;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.Callable;

public class CustomThreadPool<V> {
	private Object waitLock = new Object();
	public Object getWaitLock() {
		return waitLock;
	}

	private final LinkedList<CustomThread<V>> threads;
	private final LinkedList<Callable<V>> tasks;
	private volatile boolean shutDown;
	private DefaultResultListener<V> resultListener;

	public CustomThreadPool(int size, DefaultResultListener<V> myResultListener) {
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

	public DefaultResultListener<V> getResultListener() {
		return resultListener;
	}

	public void setResultListener(DefaultResultListener<V> resultListener) {
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


class CustomThread<V> extends Thread {

	private CustomThreadPool<V> pool;
	private boolean active = true;

	public boolean isActive() {
		return active;
	}

	public void setPool(CustomThreadPool<V> p) {
		pool = p;
	}

	public void run() {
		DefaultResultListener<V> result = pool.getResultListener();
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

class DefaultResultListener<T>{

	public void finish(Object obj) {

	}

	public void error(Exception ex) {
		ex.printStackTrace();
	}
}
//------------------------------------------Test above functionality ------------------------------------------------//
class CustomCallable implements Callable<Integer> {
	int index = -1;

	public CustomCallable(int index) {
		this.index = index;
	}

	@Override
	public Integer call() throws Exception {
		return index;
	}
}

class TestClass {
	public static void main(String[] args) {
		ArrayList al;
		DefaultResultListener result = new DefaultResultListener();
		
		CustomThreadPool<Integer> newPool = new CustomThreadPool<Integer>(2, result);
		for (int i = 0; i < 10000; i++) {
			newPool.submit(new CustomCallable(i));
		}
		newPool.stop();
	}
}

