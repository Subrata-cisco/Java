package com.thread.common;

import java.util.LinkedList;
import java.util.List;

public class CustomBlockingQueue {
  List<Integer> list = null;
  final int MAX_SIZE = 10;
  
  public CustomBlockingQueue(){
	  list = new LinkedList<Integer>();
  }
  
  public void enQueue(int val){
	  synchronized (list) {
		while(list.size() == MAX_SIZE){
			try {
				list.wait();
			} catch (InterruptedException e) {
				list.notify();
			}
		}
		
		list.add(list.size(), val);
		list.notifyAll();
	  }
  }
  
  public void deQueue(int val){
	  synchronized (list) {
		while(list.size() == 0){
			try {
				list.wait();
			} catch (InterruptedException e) {
				list.notify();
			}
		}
		
		list.remove(0);
		list.notifyAll();
	  }
  }
	
}
