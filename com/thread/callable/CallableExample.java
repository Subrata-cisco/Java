package com.thread.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 
 * What is it : Use Callable to Return Results From Runnables.
 * 
 * Alternative of : Creating a new thread , waiting for it to get it completed and then get the result by some 
 * notification method. Because run method does not return anything , nor it can throw any checked excepion.
 * 
 * When to use : Fetching some thing from Network , doing expensive calculation where exception may happen.
 * 
 * Example description : Given an integer array , we need to compute the sum.
 *
 * @author {Subrata Saha(saha.subrata@gmail.com)}
 *
 */
public class CallableExample {

	int arr[] = {1,4,7,9,3,5};
	int result = 0;
	
	//////////////////////////////////////////////////////////////////////////////////////
	/* If there is no callable then probably we can do in this way...  */
	Runnable runnable = new Runnable(){
		public void run() {
			result = 0;
			for(int i : arr){
				result = result + i;
				try {
					// taking some more time for doing the calculation while not releasing the thread.
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	};
	
	private void nonCallableExample() throws InterruptedException{
		 Thread t = new Thread(runnable);
		 t.start();
		 t.join(); // it is waiting for the job to be finished and then only we get the result.
		 
		 System.out.println("***************** Print result using runnable::"+result);
	}
	//////////////////////////////////////////////////////////////////////////////////////
	
    //////////////////////////////////////////////////////////////////////////////////////
	/* With callable the waiting and getting the result is taken care by java API itself*/
	
	Callable<Integer> callable = new Callable<Integer>(){
		public Integer call() throws Exception {
			int cresult = 0; // We dont need any global variable to save the result.
			for(int i : arr){
				cresult = cresult + i;
				try {
					// taking some more time for doing the calculation while not releasing the thread.
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return cresult;
		}
	};
	
	private void callableExample() throws InterruptedException, ExecutionException{
		ExecutorService service = Executors.newFixedThreadPool(3);
		Future<Integer> future = service.submit(callable); // cancel, isDone , isCancelled can be performed.
		
		int resultIs = future.get(); // It is a blocking call and waiting is taken care inside the API
		System.out.println("***************** Print result using runnable::"+resultIs);
	}
    //////////////////////////////////////////////////////////////////////////////////////
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		CallableExample example = new CallableExample();
		
		example.nonCallableExample();
		
		example.callableExample();
	}

}
