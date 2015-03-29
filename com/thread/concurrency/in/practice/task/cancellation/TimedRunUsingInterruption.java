package com.thread.concurrency.in.practice.task.cancellation;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Dont interrupt a borrowed thread because we dont know what is the interruption policy there.
 * @author Subrata s
 *
 */
public class TimedRunUsingInterruption {

	
	private static final ScheduledExecutorService executorService = null;
	
	
	/**
	 * Here we are scheduling for timing out after some specified time and
	 * when time out happens it shall stop the work by interrupting the another thread.
	 * 
	 * it is not good because the method dont know the interruption policy of the current thread
	 * which is borrowed.
	 * 
	 * @param r
	 * @param timeout
	 * @param unit
	 */
	public static void timedRun1(Runnable r, long timeout, TimeUnit unit){
		
		final Thread taskThread = Thread.currentThread();
		
		Runnable task = new Runnable(){
			public void run(){
				taskThread.interrupt();
			}
		};
		
		executorService.schedule(task, timeout, unit);
	}
	
	/**
	 * Correct way of implementing it.
	 * @param r
	 * @param timeout
	 * @param unit
	 */
	public static void timedRun(Runnable r, long timeout, TimeUnit unit) throws InterruptedException{
		
		class RethrowableTask implements Runnable {
			private volatile Throwable t;
			
			public void run(){
				try {
					r.run();
				} catch (Throwable e) {
					this.t = e;
				}
			}
			
			void rethrow() throws InterruptedException{
				if(t != null){
					// you may check whether it is InterruptedException or some other exception etc
					throw new InterruptedException();
				}
			}
		}
		
		RethrowableTask taskObject = new RethrowableTask();
		final Thread taskThread = new Thread(taskObject); 
		taskThread.start();
		
		
		Runnable task = new Runnable(){
			public void run(){
				taskThread.interrupt();
			}
		};
		
		executorService.schedule(task, timeout, unit);
		taskThread.join(unit.toMillis(timeout));
		taskObject.rethrow();
	}
	
	/**
	 * Safest way of implementing timed run out using Future object. 
	 * 
	 * @param r
	 * @param timeout
	 * @param unit
	 * @throws InterruptedException
	 */
	public static void timedRunUsingFuture(Runnable r, long timeout, TimeUnit unit) throws InterruptedException{
		
		Future<?> futureTask = executorService.submit(r);
		
		try {
			futureTask.get(timeout,unit);
		} catch ( TimeoutException e) {
			throw new InterruptedException();
		} catch (ExecutionException e){
			
		} finally {
			futureTask.cancel(true);
		}
		
	}
	
}
