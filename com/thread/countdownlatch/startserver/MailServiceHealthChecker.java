package com.thread.countdownlatch.startserver;

import java.util.concurrent.CountDownLatch;

public class MailServiceHealthChecker extends AbstractServerHealthChecker {
	
	public MailServiceHealthChecker(CountDownLatch latch){
		super("Mail Server",latch);
	}

	public boolean isDeviceReady() {
		System.out.println("**********Subrata :: Checking " + getServiceName());
		try{
			Thread.sleep(10000);
		}catch(InterruptedException ex){
			
		}
		System.out.println("**********Subrata :: Service "+getServiceName()+" is up !!");
		return true;
	}

}
