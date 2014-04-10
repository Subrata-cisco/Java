package com.thread.countdownlatch;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.thread.countdownlatch.startserver.AbstractServerHealthChecker;
import com.thread.countdownlatch.startserver.DatabaseServiceHealthChecker;
import com.thread.countdownlatch.startserver.MailServiceHealthChecker;

/**
 * 
 * What it is :
 *
 * Alternative of :
 *
 * When to use :
 *
 * Example description :
 *
 * @author Subrata Saha (saha.subrata@gmail.com)
 *
 */
public class CountDownLatchExample {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		CountDownLatch latch = new CountDownLatch(2);
		ExecutorService service = Executors.newFixedThreadPool(2);
		
		ArrayList<AbstractServerHealthChecker> list = new ArrayList<AbstractServerHealthChecker>();
		list.add(new MailServiceHealthChecker(latch));
		list.add(new DatabaseServiceHealthChecker(latch));
		
		ArrayList<Future> futureList = new ArrayList<Future>();
		
		for(AbstractServerHealthChecker svc: list){
			futureList.add(service.submit(svc));
		}
		System.out.println("**********Subrata :: Submitted the task...");
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			// interrupted
		}
		System.out.println("**********Subrata :: Waiting for the task is over...");
		
		//for(Future future: futureList){
			//System.out.println("**********Subrata :: Get :: "+future.get());
		//}
		
	}

}
