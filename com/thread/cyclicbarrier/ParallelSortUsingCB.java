package com.thread.cyclicbarrier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.RandomAccess;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

public class ParallelSortUsingCB<E extends Comparable<E>> {
	private final int noThreads = Runtime.getRuntime().availableProcessors();
	private final int noSamplesPerThread = 16;
	private final AtomicLong randSeed = new AtomicLong(System.nanoTime());
	private volatile int stageNo = 0;
	private final int dataSize;
	private final List<E> data;
	private final List<E> splitPoints = new ArrayList<E>(noSamplesPerThread
			* noThreads);
	private final List<List<E>> bucketsToSort;
	private final ReadWriteLock dataLock;

	private final CyclicBarrier barrier = new CyclicBarrier(noThreads + 1,
			new Runnable() {
				public void run() {
					System.out.println("****** Subrata -> Finisghed...");
					sortStageComplete();
				}
			});

	public ParallelSortUsingCB(List data, ReadWriteLock dataLock) {
		if (!(data instanceof RandomAccess))
			throw new IllegalArgumentException("List must be random access");
		this.data = data;
		this.dataLock = dataLock;
		this.dataSize = data.size();
		List<List<E>> tempList = new ArrayList<List<E>>(noThreads);
		for (int i = 0; i < noThreads; i++) {
			tempList.add(new ArrayList(dataSize / noThreads));
		}
		bucketsToSort = Collections.unmodifiableList(tempList);
	}

	private void sortStageComplete() {
		try {
			switch (stageNo) {
			case 0:
				amalgamateSplitPointData();
				break;
			case 1:
				clearData();
				break;
			case 2:
				combineBuckets();
				break;
			default:
				throw new RuntimeException("Don't expect to be "
						+ " called at stage " + stageNo);
			}
			stageNo++;
		} catch (RuntimeException rte) {
			completionStageError = rte;
			throw rte;
		}
	}

	private volatile RuntimeException completionStageError;

	private void gatherSplitPointSample(List data, int startPos, int endPos) {
		Random rand = new Random(randSeed.getAndAdd(17));
		List sample = new ArrayList(noSamplesPerThread);
		Lock l = dataLock.readLock();
		l.lock();
		try {
			for (int i = 0; i < noSamplesPerThread; i++) {
				int n = rand.nextInt(endPos - startPos) + startPos;
				sample.add(data.get(n));
			}
		} finally {
			l.unlock();
		}
		synchronized (splitPoints) {
			splitPoints.addAll(sample);
		}
	}

	private void amalgamateSplitPointData() {
		synchronized (splitPoints) {
			List<E> spl = new ArrayList<E>(splitPoints);
			Collections.sort(spl);
			splitPoints.clear();
			for (int i = 1; i < noThreads; i++) {
				splitPoints.add(spl.get(i * noSamplesPerThread));
			}
		}
	}

	private void clearData() {
		Lock lck = dataLock.writeLock();
		lck.lock();
		try {
			data.clear();
		} finally {
			lck.unlock();
		}
	}

	private void assignItemsToBuckets(List<E> data, int threadNo, int startPos,
			int endPos) {
		List<E> spl;
		synchronized (splitPoints) {
			spl = new ArrayList(splitPoints);
		}
		ArrayList<ArrayList<E>> bucketData = new ArrayList<ArrayList<E>>(
				noThreads);
		for (int i = 0; i < noThreads; i++) {
			bucketData.add(new ArrayList(dataSize / (noThreads * noThreads)));
		}

		Lock lck = dataLock.readLock();
		lck.lock();
		try {
			for (int i = startPos; i < endPos; i++) {
				E item = data.get(i);
				int bucket = Collections.binarySearch(spl, item);
				if (bucket < 0)
					bucket = (-bucket) - 1;
				if (bucket >= noThreads)
					bucket = noThreads - 1;
				bucketData.get(bucket).add(item);
			}
		} finally {
			lck.unlock();
		}
		for (int i = 0; i < noThreads; i++) {
			List l = bucketsToSort.get(i);
			synchronized (l) {
				l.addAll(bucketData.get(i));
			}
		}
	}

	private void combineBuckets() {
		Lock lck = dataLock.writeLock();
		lck.lock();
		try {
			for (int i = 0; i < noThreads; i++) {
				List l = bucketsToSort.get(i);
				synchronized (l) {
					data.addAll(l);
				}
			}
		} finally {
			lck.unlock();
		}
	}

	public void sort() throws InterruptedException {
		// See if it's not worth doing a parallel sort
		Lock l = dataLock.writeLock();
		l.lockInterruptibly();
		try {
			if (data.size() < noSamplesPerThread * 4 * noThreads) {
				Collections.sort(data);
				return;
			}
		} finally {
			l.unlock();
		}

		// Start sorter threads going
		List<SorterThread> threads = new ArrayList<SorterThread>(noThreads);
		for (int i = 0; i < noThreads; i++) {
			SorterThread thr = new SorterThread(i);
			threads.add(thr);
			thr.start();
		}

		// Wait for sorter threads
		try {
			barrier.await();
			barrier.await();
			barrier.await();
		} catch (BrokenBarrierException bb) {
			// Find the error that caused the broken barrier
			for (int i = 0; i < noThreads; i++) {
				SorterThread thr = threads.get(i);
				Throwable t = thr.error;
				if (t != null)
					throw new RuntimeException("Error during sort", t);
			}
			if (completionStageError != null)
				throw completionStageError;
			else
				throw new RuntimeException("Misc error during sort", bb);
		}
	}

	class SorterThread extends Thread {
		private final int threadNo;
		private volatile Throwable error;

		SorterThread(int no) {
			this.threadNo = no;
		}

		public void run() {
			try {
				double div = (double) dataSize / noThreads;
				int startPos = (int) (div * threadNo), endPos = (int) (div * (threadNo + 1));

				gatherSplitPointSample(data, startPos, endPos);
				barrier.await();
				assignItemsToBuckets(data, threadNo, startPos, endPos);
				barrier.await();
				sortMyBucket();
				barrier.await();
			} catch (InterruptedException e) {
			} catch (BrokenBarrierException e) {
			} catch (Throwable t) {
				this.error = t;
				Thread.currentThread().interrupt();
				try {
					barrier.await();
				} catch (Exception e) {
				}
			}
		}

		private void sortMyBucket() {
			List<E> l = bucketsToSort.get(threadNo);
			synchronized (l) {
				Collections.sort(l);
			}
		}
	}

}
