package com.thread.waitnotify;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The Producer and Consumer program using superb Condition class.
 * @author subratas
 *
 */
public class ProducerConsumerUsingCondition {

	
	public static final int ALLOWED_SIZE = 5;
	
	public static void main(String[] args) {
		
		System.out.println("Main Started");
		
		Lock lock  = new ReentrantLock();
		Condition full = lock.newCondition();
		Condition empty = lock.newCondition();
		Queue<Integer> queue = new LinkedList<>();

		//ProducerConsumerUsingCondition obj = new ProducerConsumerUsingCondition();
		
		Thread pThread = new Thread(new ProducerPP(lock,full,empty,queue));
		Thread cThread = new Thread(new ConsumerCC(lock,full,empty,queue));
		
		pThread.start();
		cThread.start();
		
		try {
			Thread.currentThread().sleep(1000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(pThread.isAlive()){
			pThread.interrupt();
		}
		
		if(cThread.isAlive()){
			cThread.interrupt();
		}
		
		System.out.println("Main Finished");
	}

	
}

class ProducerPP implements Runnable {
	
	Lock lock  = null;
	Condition full = null;
	Condition empty = null;
	Queue<Integer> queue = null;
	Random rd = new Random();
	
	public ProducerPP(Lock lock,Condition full,Condition empty,Queue<Integer> queue){
		 this.lock  = lock;
		 this.full = full;
		 this.empty = empty;
		 this.queue = queue;
	}
	
	@Override
	public void run() {
		for(int i=0;i<100;i++){
			produce(i+1);
		}
		System.out.println("Producer done !!");
	}
	
	private void produce(int i){
		try{
			lock.lock();
			
			while(queue.size() == ProducerConsumerUsingCondition.ALLOWED_SIZE){
				System.out.println("Queue is full , so wait !!");
				full.await();
			}
			
			System.out.println("Producing.....");
			queue.add(i);
			
			empty.signalAll();
			
		} catch (InterruptedException e) {
			
		}finally {
			lock.unlock();
		}
	}
}

class ConsumerCC implements Runnable {
	Lock lock  = null;
	Condition whoWasWaitingOnFull = null;
	Condition whoWasWaitingOnEmpty = null;
	Queue<Integer> queue = null;
	
	public ConsumerCC(Lock lock,Condition full,Condition empty,Queue<Integer> queue){
		 this.lock  = lock;
		 this.whoWasWaitingOnFull = full;
		 this.whoWasWaitingOnEmpty = empty;
		 this.queue = queue;
	}
	
	@Override
	public void run() {
		boolean status = true;
		while(status){
			status = consume();
		}
		System.out.println("Consumer done !!");
	}
	
	private boolean consume(){
		boolean status = true;
		try{
			lock.lock();
			while(queue.isEmpty()){
				System.out.println("Queue is empty , so consumer is waiting !!");
				whoWasWaitingOnEmpty.await();
			}
			
			while(!queue.isEmpty()){
				System.out.println("Consuming :: "+queue.poll());
			}
			
			whoWasWaitingOnFull.signalAll();
		} catch (InterruptedException e) {
			status = false;
		}finally {
			lock.unlock();
		}
		return status;
	}
}
