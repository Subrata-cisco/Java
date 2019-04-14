package thread.countdownlatch.startserver;

import java.util.concurrent.CountDownLatch;
/**
 * Class to check the database health by creating a connection etc.
 * @author Subrata Saha (saha.subrata@gmail.com)
 *
 */
public class DatabaseServiceHealthChecker extends AbstractServerHealthChecker {

	public DatabaseServiceHealthChecker(CountDownLatch latch){
		super("Database Server",latch);
	}
	
	@Override
	public boolean isDeviceReady() {
		System.out.println("**********Subrata :: Checking " + getServiceName());
		try{
			Thread.sleep(5000);
		}catch(InterruptedException ex){
			// nothing
		}
		System.out.println("**********Subrata :: Service "+getServiceName()+" is up !!");
		return true;
	}

}
