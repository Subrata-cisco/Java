package com.thread.exchanger;

import java.util.concurrent.Exchanger;

/*
 * To exchange objects among a pair of threads we use exchanger.
 */
public class ExchangeDemo {
	
	public static void main(String[] args) {
		ExchangeDemo demo = new ExchangeDemo();
		Exchanger<String> chnger = new Exchanger<String>();
		
		CustomerThread t1 = new CustomerThread(chnger);
		t1.start();
		ShopKeeperThread t2 = new ShopKeeperThread(chnger);
		t2.start();
		
	}

}


class CustomerThread extends Thread {
    Exchanger<String> custDialog = null;
    CustomerThread(Exchanger<String> custDialog){
    	this.custDialog = custDialog;
    }
	
	@Override
	public void run() {
		String interaction = null;
		try {
			interaction = custDialog.exchange("Excuse me !!");
			System.out.println("**********Subrata :: Customer ::" +interaction);
//			interaction = custDialog.exchange("Need one pen !!");
//			System.out.println("**********Subrata :: Customer ::" +interaction);
//			interaction = custDialog.exchange(" ");
//			System.out.println("**********Subrata :: Customer ::" +interaction);
		} catch (InterruptedException e) {
			// interrupted
		}
	}
	
}

class ShopKeeperThread extends Thread {
    Exchanger<String> shopKeeperDialog = null;
    ShopKeeperThread(Exchanger<String> custDialog){
    	this.shopKeeperDialog = custDialog;
    }
	
	@Override
	public void run() {
		String interaction = null;
		try {
			interaction = shopKeeperDialog.exchange("Yes sir !!");
			System.out.println("**********Subrata :: ShopKeeper ::" +interaction);
//			interaction = shopKeeperDialog.exchange("Renold is fine ??");
//			System.out.println("**********Subrata :: ShopKeeper ::" +interaction);
//			interaction = shopKeeperDialog.exchange(" 100 RS !!");
//			System.out.println("**********Subrata :: ShopKeeper ::" +interaction);
		} catch (InterruptedException e) {
			// interrupted
		}
	}
	
}