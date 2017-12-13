package com.thread.blockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public class DifferentBlockingQueues {
	public static void main(String[] args) throws InterruptedException {
		
		BlockingQueue<String> queues ;
		
		queues = new ArrayBlockingQueue<>(100);
		
		queues = new LinkedBlockingQueue<String>();       //unbounded  : actually bounded by Integer.MAX
		queues = new LinkedBlockingQueue<String>(100);    //bounded
		
		//All elements inserted into the PriorityBlockingQueue must implement the java.lang.Comparable interface. 
		queues   = new PriorityBlockingQueue<>(); // DEFAULT_INITIAL_CAPACITY = 11;
		
		// Useful in Scheduling algorithm.
		DelayQueue queue = new DelayQueue();
	    Delayed element1 = new DelayedElement();
	    queue.put(element1);
	    Delayed element2 = queue.take();
	    
	    BlockingDeque<String> deque = new LinkedBlockingDeque<String>();
	    deque = new LinkedBlockingDeque<String>();
	}
}

class DelayedElement implements Delayed {

	@Override
	public int compareTo(Delayed o) {
		return 0;
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return 5;
	}
	
}
