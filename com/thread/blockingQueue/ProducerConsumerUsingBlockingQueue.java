package com.thread.blockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumerUsingBlockingQueue {

	public static void main(String[] args) {
		BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(100);
		ProducerConsumerUsingBlockingQueue con = new ProducerConsumerUsingBlockingQueue();
		
		ProducerConsumerUsingBlockingQueue.Producer p = new ProducerConsumerUsingBlockingQueue(). new Producer(queue);    
		ProducerConsumerUsingBlockingQueue.Consumer c = new ProducerConsumerUsingBlockingQueue(). new Consumer(queue);  
		
		
		Thread t1 = new Thread(p);
		Thread t2 = new Thread(c); 
		
		t1.start();
		t2.start();
	} 
	
	
	class Producer implements Runnable {
		BlockingQueue queue;
		
		public Producer(BlockingQueue queue) {
			super();
			this.queue = queue;
		}

		@Override
		public void run() {
			int i = 0;
			while(true){  
            	try {
            		String val = "value "+i;
					queue.put(val);
					System.out.println(" Produced ::"+val);
					i++;
				} catch (InterruptedException e) {
				}
            }
		}
	}
	
	class Consumer implements Runnable {
		BlockingQueue queue;
		public Consumer(BlockingQueue queue) {
			super();
			this.queue = queue;
		}

		@Override
		public void run() {
			while(!queue.isEmpty()){
				try {
					System.out.println(" Consumed ::"+queue.take());
				} catch (InterruptedException e) {
				}
			}
		}
	}
}
