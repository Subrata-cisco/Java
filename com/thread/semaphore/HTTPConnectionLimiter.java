package com.thread.semaphore;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class HTTPConnectionLimiter {
	private final Semaphore semaphore;

	private HTTPConnectionLimiter(int maxConcurrentRequests) {
		semaphore = new Semaphore(maxConcurrentRequests);
	}

	public void acquire() throws InterruptedException, IOException {
		boolean lockAcquired = false;
		lockAcquired = semaphore.tryAcquire();
		// semaphore.acquire();
		System.out.println("**********Subrata > permits ::"
				+ /* semaphore.drainPermits() + */" queue length ::"
				+ semaphore.getQueueLength() + " has queued ::"
				+ semaphore.hasQueuedThreads() + " isFair ::"
				+ semaphore.isFair());
		System.out
				.println("**********Subrata :: Create Connection.... lockAcquired ::"
						+ lockAcquired);
	}

	public void release() {
		try {
			System.out.println("**********Subrata :: Release the connection.");
		} finally {
			semaphore.release();
		}
	}

	public static void main(String[] args) throws InterruptedException,
			IOException {
		HTTPConnectionLimiter limiter = new HTTPConnectionLimiter(5);

		// though we are creating 7 acquire only five will be served and other 2
		// will be waiting for ever until some body calls release.
		limiter.acquire();
		limiter.acquire();
		limiter.acquire();
		limiter.acquire();
		limiter.acquire();
		limiter.acquire();
		limiter.acquire();

		// Thread.sleep(1000);

		System.out
				.println("**********Subrata :: Lets release some open connection !!");
		limiter.release();
		limiter.release();

	}
}

// Naive Semaphore implementation.
class CountingSemaphore {
	private int value = 0;
	private int waitCount = 0;
	private int notifyCount = 0;

	public CountingSemaphore(int initial) {
		if (initial > 0) {
			value = initial;
		}
	}

	public synchronized void waitForNotify() {
		if (value <= waitCount) {
			waitCount++;
			try {
				do {
					wait();
				} while (notifyCount == 0);
			} catch (InterruptedException e) {
				notify();
			} finally {
				waitCount--;
			}
			notifyCount--;
		}
		value--;
	}

	public synchronized void notifyToWakeup() {
		value++;
		if (waitCount > notifyCount) {
			notifyCount++;
			notify();
		}
	}
}

// an Mutax implementation.
class BinarySemaphore {
	private boolean locked = false;

	BinarySemaphore(int initial) {
		locked = (initial == 0);
	}

	public synchronized void waitForNotify() throws InterruptedException {
		while (locked) {
			wait();
		}
		locked = true;
	}

	public synchronized void notifyToWakeup() {
		if (locked) {
			notify();
		}
		locked = false;
	}
}

/**
 * 
 * Anoter good way of creating Resource.
 */
class ResourcePool<T> {
	int MAX_RESOURCES = 10;
	private final Semaphore sem = new Semaphore(MAX_RESOURCES, true); // true means fairness policy i.e threads waiting for long time will get chance.
	private final Queue<T> resources = new ConcurrentLinkedQueue<T>();

	public T getResource(long maxWaitMillis) throws InterruptedException,
			Exception {

		// First, get permission to take or create a resource
		boolean lockRecived = sem.tryAcquire(maxWaitMillis, TimeUnit.MILLISECONDS);
		if(!lockRecived){
			return null;
		}

		// Then, actually take one if available...
		T res = resources.poll();
		if (res != null)
			return res;

		// ...or create one if none available
		try {
			return createResource();
		} catch (Exception e) {
			// Don't hog the permit if we failed to create a resource!
			sem.release();
			throw new Exception(e);
		}
	}

	public void returnResource(T res) {
		resources.add(res);
		sem.release();
	}

	public T createResource() {
		System.out.println("**********Subrata :: Created new Resource !!");
		return (T) new Object();
	}
}
