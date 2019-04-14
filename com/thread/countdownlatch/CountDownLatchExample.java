package thread.countdownlatch;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import thread.countdownlatch.startserver.AbstractServerHealthChecker;
import thread.countdownlatch.startserver.DatabaseServiceHealthChecker;
import thread.countdownlatch.startserver.MailServiceHealthChecker;

/**
 * 
 * What it is : Checks the completion of the child threads if the size of the
 * created children is known. It enables a thread to wait for completion of
 * child threads. But there is no waiting amongst the children until they finish
 * each others tasks. Children may execute asynchronously and after their work
 * is done will exit making a count down.
 *
 * Alternative of :
 *
 * When to use : To completed severals task before starting the main task. And
 * all those several task is not dependent on each other tasks i.e they are
 * mutually independent. Dont use it : when there are multiple big parallel work
 * to be done but on one thread failure you dont want to proceed with the
 * operation because it cant signal another thread to stop/cancel/wait etc.
 *
 * Example description : Before making the server enable to the user check if
 * the important services are up and running.
 *
 * @author Subrata Saha (saha.subrata@gmail.com)
 *
 */
public class CountDownLatchExample {

	public static void main(String[] args) throws InterruptedException,
			ExecutionException {

		CountDownLatch latch = new CountDownLatch(2);
		// if this no is not matching countdown call then the main thread will
		// wait for ever.
		// "**********Subrata :: Waiting for the task is over..." will never
		// come !!!

		ExecutorService service = Executors.newFixedThreadPool(2);
		// ideally this should be same as no of work.

		ArrayList<AbstractServerHealthChecker> list = new ArrayList<AbstractServerHealthChecker>();
		list.add(new MailServiceHealthChecker(latch));
		list.add(new DatabaseServiceHealthChecker(latch));

		ArrayList<Future<Boolean>> futureList = new ArrayList<Future<Boolean>>();

		for (AbstractServerHealthChecker svc : list) {
			futureList.add(service.submit(svc));
		}
		System.out.println("**********Subrata :: Submitted the task...");

		try {
			latch.await();
		} catch (InterruptedException e) {
			// interrupted
		}
		System.out
				.println("**********Subrata :: Waiting for the task is over...");

	}

}
