package com.thread.blockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumerUsingBlockingQueue {

	public static void main(String[] args) throws InterruptedException {
		BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(100);
		ProducerConsumerUsingBlockingQueue con = new ProducerConsumerUsingBlockingQueue();
		
		Producer p = new Producer(queue);    
		Consumer c = new Consumer(queue);  
		
		
		Thread t1 = new Thread(p);
		Thread t2 = new Thread(c); 
		
		t1.start();
		t2.start();
		
		t1.join();
		t2.join();
		
		System.out.println("(DONE)");
	} 
}

class Producer implements Runnable {
	private BlockingQueue queue;
	
	public Producer(BlockingQueue queue) {
		super();
		this.queue = queue;
	}

	@Override
	public void run() {
		int i = 0;
		while(i<99){  
        	try {
        		String val = "value "+i;
				queue.put(val);
				System.out.println(" Produced ::"+val);
				i++;
			} catch (InterruptedException e) {
			}
        }
		try {
			queue.put("Bye");
		} catch (InterruptedException e) {
		}
	}
}

class Consumer implements Runnable {
	private BlockingQueue queue;
	public Consumer(BlockingQueue queue) {
		super();
		this.queue = queue;
	}

	@Override
	public void run() {
		String val = null; 
		try {
			while((val = (String)queue.take()) != "Bye"){
				System.out.println(" Consumed ::"+val);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
