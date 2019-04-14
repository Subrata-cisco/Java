package thread.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 
 * What it is : Thread are waiting for each other for further processing.
 *
 * Alternative of :
 *
 * When to use : It can be used to perform final part of task once individual
 * tasks are completed. The barrier can be re used by calling reset.
 *
 * Example description : Suppose we want to download a big file from internet
 * then we can divide the entire into some part and distribute those part among
 * different thread to start download separately and only when all the download
 * is finished then we can start merging all the parts to get the original file.
 * 
 *
 * @author Subrata Saha (saha.subrata@gmail.com)
 *
 */
public class CBUsingSimpleExample {

	CyclicBarrier cb = null;
	volatile int count = 0;
	String[][] atoms = null;

	private int getLength() {
		int length = -1;
		if (atoms != null) {
			length = atoms.length;
		}
		return length;
	}

	private void initialize() {

		atoms = new String[][] {

		{ "H", "HE", "A", "C", "H", "H", "o", "o" },
				{ "H", "HE", "A", "C", "H", "H", "o", "o" },
				{ "H", "HE", "A", "C", "H", "H", "o", "o" },
				{ "H", "HE", "A", "C", "H", "H", "o", "o" },
				{ "H", "HE", "A", "C", "H", "H", "o", "o" },
				{ "H", "HE", "A", "C", "H", "H", "o", "o" },
				{ "H", "HE", "A", "C", "H", "H", "o", "o" },
				{ "H", "HE", "A", "C", "H", "H", "o", "o" },
				{ "H", "HE", "A", "C", "H", "H", "o", "o" },
				{ "H", "HE", "A", "C", "H", "H", "o", "o" },
				{ "H", "HE", "A", "C", "H", "H", "o", "o" },
				{ "H", "HE", "A", "C", "H", "H", "o", "o" },

		};

		cb = new CyclicBarrier(atoms.length, new Runnable() {
			@Override
			public void run() {
				System.out
						.println("********** Subrata :: All download finished , start merging them back times !!");
			}
		});

	}

	/**
	 * Method to to process each line of Atoms.
	 */
	private void startDownLoadTheRowArray(int index) {
		System.out.println("**********Subrata :: Download part ::" + index
				+ " by ::" + Thread.currentThread().getName());
	}

	private class Worker implements Runnable {

		@Override
		public void run() {
			// Doing one kind of operation on particular row.
			startDownLoadTheRowArray(count++);
			// Here one wait call means once all the thread are done the above
			// processing they will get a call on CyclicBarrier run method.
			// So if we want to multiple operation we have to call multiple
			// await after doing that operation and we get that many call to CB
			// run method.
			waitOnBarrier();
		}

		private void waitOnBarrier() {
			try {
				cb.await();
			} catch (InterruptedException e) {
				System.out.println("**********Subrata :: "
						+ Thread.currentThread().getName()
						+ " InterruptedException !!");
			} catch (BrokenBarrierException e) {
				System.out.println("**********Subrata :: "
						+ Thread.currentThread().getName()
						+ " BrokenBarrierException !!");
			}
		}

		private void doSomeOtherTask() {
			System.out
					.println("**********Subrata :: CBUsingSimpleExample.Worker.doSomeOtherTask() by ::"
							+ cb.getNumberWaiting());
			waitOnBarrier();

		}

	}

	public static void main(String[] args) {
		CBUsingSimpleExample cbExample = new CBUsingSimpleExample();
		cbExample.initialize();

		CBUsingSimpleExample.Worker worker = cbExample.new Worker();

		for (int i = 0; i < cbExample.getLength(); i++) {
			Thread t1 = new Thread(worker);
			t1.start();
		}

		// cbExample.cb.reset();

		/*
		 * CBUsingSimpleExample.Worker worker1 = cbExample.new Worker();
		 * 
		 * for(int i=0 ;i<cbExample.getLength();i++){ Thread t1 = new
		 * Thread(worker1); t1.start(); }
		 */

		System.out.println("**********Subrata :: Main Thread finished !!");
	}

}
