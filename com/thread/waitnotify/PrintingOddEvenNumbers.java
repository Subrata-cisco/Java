package com.thread.waitnotify;
/**
 * Print odd even number using 2 thread.
 * @author Subrata Saha.
 *
 */
public class PrintingOddEvenNumbers {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		DataPrinter data = new DataPrinter();
		Thread t1 = new Thread(new EvenNumberPrinter(data));
		t1.setName("Subrata");
		t1.start();
		
		Thread t2 = new Thread(new OddNumberPrinter(data));
		t2.setName("saha");
		t2.start();
		
	}
	

}

class DataPrinter {
	int arr[] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
	int index = 0;
	private boolean flip = true;
	
	
	public boolean processAlternateDataOne() throws InterruptedException {
		synchronized (arr) {
			while(flip){
				arr.wait();
			}
			flip = true;
			arr.notifyAll();
			return printData();			
		}
	}
	
	public boolean processAlternateDataTwo() throws InterruptedException {
		synchronized (arr) {
			while(!flip){
				arr.wait();
			}
			flip = false;
			arr.notifyAll();
			return printData();			
		}
	}
	
	public boolean printData(){
		if(index < arr.length){
			System.out.println("value is ::"+arr[index]+" ::"+Thread.currentThread().getName());
			index++;
			return true;
		}else{
			return false;
		}
	}
	
	
}

class EvenNumberPrinter implements Runnable {

	DataPrinter data = null;
	
	public EvenNumberPrinter(DataPrinter data){
		this.data = data;
	}
	
	@Override
	public void run() {
		try {
			boolean retunValue = true;
			while(retunValue){
				retunValue = data.processAlternateDataOne();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}

class OddNumberPrinter implements Runnable {
	DataPrinter data = null;
	
	public OddNumberPrinter(DataPrinter data){
		this.data = data;
	}
	
	@Override
	public void run() {
		try {
			boolean retunValue = true;
			while(retunValue){
				retunValue = data.processAlternateDataTwo();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}