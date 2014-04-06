package com.thread.deadlock;

public class TestDeadLock {
 
	
	public static void main(String[] args) {
		DeadLock lock = new DeadLock();
		Thread[] thread1 = new Thread[100]; 
		Thread[] thread2 = new Thread[100];
		
		for(int i=0;i<100;i++){
			thread1[i] = new Thread(new Reader(lock));
			thread2[i] = new Thread(new Writer(lock));

			thread1[i].start();
			thread2[i].start();
		}
		
		// Read , Write should have printed 200 times but it printed only 156 times. // depends on execution
	}
}

class Reader implements Runnable {
	DeadLock lock;
	Reader(DeadLock lock){
		this.lock = lock;
	}
	public void run() {
		lock.read();
	}
}


class Writer implements Runnable {
	DeadLock lock;
	Writer(DeadLock lock){
		this.lock = lock;
	}
	public void run() {
		lock.write();
	}
}
