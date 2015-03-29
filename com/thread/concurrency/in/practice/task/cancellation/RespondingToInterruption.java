package com.thread.concurrency.in.practice.task.cancellation;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;

/**
 * We have 2 ways.
 * 1. Propagate the InteruptedException.
 * 2. Restore the interruption.
 * 
 * @author Subrata s
 *
 */
public class RespondingToInterruption {
	private BlockingQueue<BigInteger> queue;
	
	/*
	 * propagating the interrupted status.
	 */
	public BigInteger getNextPrimeNumber1() throws InterruptedException{
		return queue.take();
	}
	
	/**
	 * restoring the interruption status before exit.
	 * @return
	 */
	public BigInteger getNextPrimeNumber2() {
		BigInteger bi = null;
		boolean isinterrupted = false;
		
		// So we have non interruptable loop which wants to get at least sum as 100 and 
		// we know queue has lot many elements which can sum up as 100 , so unit of work = 100 at least.
		while(bi.intValue() != 100){
			try { 
				bi.add(queue.take());
			} catch (InterruptedException e) {
				isinterrupted = true;
			} finally {
				if(isinterrupted){
					Thread.currentThread().interrupt();
				}
			}
		}
		return bi;
	}
}
