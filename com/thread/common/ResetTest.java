package com.thread.common;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ResetTest {

	public static void main(String[] args) {
		ResetTest obj = new ResetTest();
		ResetTest.FinalResultListener objFinal = obj.new FinalResultListener();
		CyclicBarrier cb = new CyclicBarrier(2, objFinal);

		obj.new One(cb, "t1");
		obj.new Two(cb, "t2");
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
		}

		cb.reset();
		System.out.println("****** Subrata -> middle");

		//obj.new One(cb, "t3");
		//obj.new Two(cb, "t4");

	}

	class One extends Thread {
		CyclicBarrier cb = null;

		public One(CyclicBarrier cb, String name) {
			this.cb = cb;
			setName(name);
			start();
		}

		@Override
		public void run() {
			boolean val = true;
			while (val) {
				try {
					Thread.sleep(500);
					System.out.println("****** Subrata -> One aftre 500 ::"
							+ Thread.currentThread().getName());
					cb.await();

					// result found , so other threads need to be killed.
					// Thread.sleep(100);
					// cb.reset();

				} catch (InterruptedException | BrokenBarrierException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					System.out.println("****** Subrata -> one exception..." + e.getMessage());
					val = false;
				}
			}
		}

	}

	class Two extends Thread {
		CyclicBarrier cb = null;

		public Two(CyclicBarrier cb, String name) {
			this.cb = cb;
			setName(name);
			start();
		}

		@Override
		public void run() {
			boolean val = true;
			while (val) {
				try {
					Thread.sleep(1000);
					System.out.println("****** Subrata -> Two aftre 1000 ::"
							+ Thread.currentThread().getName());
					// cb.reset();
					cb.await();
				} catch (InterruptedException | BrokenBarrierException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					System.out.println("****** Subrata -> 2 error ::"
							+ e.getMessage());
					val = false;
				}
			}
		}

	}

	class FinalResultListener implements Runnable {
		@Override
		public void run() {
			System.out.println("****** Subrata -> Final result....");
		}

	}

}
