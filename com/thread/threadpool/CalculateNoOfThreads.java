package com.thread.threadpool;

import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 
 * What is it :How to find out the total number of threads is possible with the
 * underlying machine. we don’t want threads waiting if we have the capacity to
 * process them but we also don’t want to launch more threads than we have the
 * capacity to manage.
 * 
 * Alternative of : NA
 * 
 * When to use : To check the total number threads of the underlying machine.
 * 
 * Example description : Will find out the total threads supported. Do you know
 * Little’s Law ?? No of threads = incoming rate * WaitingTime
 * 
 * @author {Subrata Saha(saha.subrata@gmail.com)}
 * 
 */
public class CalculateNoOfThreads  {

	private void calculateThreadCount() throws InterruptedException{
		for (int i = 0; i < 1000; i++) {
			AsynchronousTask task = new AsynchronousTask(i, "IO2", 30);
			task.run();
		}
		System.gc(); System.gc(); System.gc(); System.gc(); System.gc(); System.gc(); System.gc();
		System.gc(); System.gc(); System.gc(); System.gc(); System.gc(); System.gc(); System.gc(); 
		Thread.sleep(100);
		long start = System.nanoTime();
		long cpu = ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime();
		for (int i = 0; i < 1000; i++) {
			AsynchronousTask task = new AsynchronousTask(i, "IO2", 30);
			task.run();
		}
		start = System.nanoTime() - start;
		cpu = ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime() - cpu;
		long wait = start - cpu;
		BigDecimal waitTime = new BigDecimal(wait);
		BigDecimal computeTime = new BigDecimal(cpu);
		BigDecimal numberOfCPU = new BigDecimal(Runtime.getRuntime().availableProcessors());
		BigDecimal targetUtilization = new BigDecimal(100);
		BigDecimal optimalthreadcount = numberOfCPU.multiply(targetUtilization).multiply(new BigDecimal(1).add(waitTime.divide(computeTime, RoundingMode.HALF_UP)));
		System.out.println("Number of CPU: "+numberOfCPU);
		System.out.println("Target utilization: "+targetUtilization);
		System.out.println("Elapsed time: "+start);
		System.out.println("Compute time: "+cpu);
		System.out.println("Wait time: "+wait);
		System.out.println("Formula: " + numberOfCPU + " * " + targetUtilization + " * (1 + " + waitTime + " / " + computeTime + ")");
		System.out.println("Optimal thread count: "+ optimalthreadcount);
	}

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		CalculateNoOfThreads calculator = new CalculateNoOfThreads();
		calculator.calculateThreadCount();
	}

}


