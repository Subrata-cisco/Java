package com.thread.deadlock;

/**
 * 
 * What is it : Creating a deadlock situation.
 *
 * Alternative of : NA [Do you know stavation ?]
 *
 * When to use : just an demonstartion of bad which you should be aware of !!
 *
 * Example description : creating 2 resource and synchronization order is reverse.
 *
 * @author {Subrata Saha(saha.subrata@gmail.com)}
 *
 */
public class DeadLock {
  
	Resource1 r1 = new Resource1();
	Resource2 r2 = new Resource2();
	
	
	public int read(){
		synchronized(r1){
			synchronized(r2){
				System.out.println("*************** SubrataDeadLock.read()");
				return (r1.getValue() + r2.getValue());
			}
		}
	}
	
	public void write(){
		synchronized(r2){
			synchronized(r1){
				System.out.println("*************** SubrataDeadLock.write()");
				 r2.setValue(1) ;
				 r1.setValue(2);
			}
		}
	}
}

class Resource1 {
	int value;
	public int getValue(){
		return value;
	}
	
	public void setValue(int val){
		value = val;
	}
}

class Resource2 {
	int value;
	public int getValue(){
		return value;
	}
	
	public void setValue(int val){
		value = val;
	}
}
