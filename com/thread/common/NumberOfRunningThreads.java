package com.thread.common;

public class NumberOfRunningThreads {

	
	public static void main(String[] args) {
		int nbThreads =  Thread.getAllStackTraces().keySet().size();
		System.out.println("NumberOfRunningThreads.main()  Total number of threads in your VM ::"+nbThreads);
		
		int nbRunning = 0;
		for (Thread t : Thread.getAllStackTraces().keySet()) {
		    if (t.getState()==Thread.State.RUNNABLE) nbRunning++;
		}
		System.out.println("NumberOfRunningThreads.main() All threads currently executing ::"+nbRunning);
	}
}
