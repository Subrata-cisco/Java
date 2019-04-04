package thread.countdownlatch.startserver;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public abstract class AbstractServerHealthChecker implements Callable<Boolean>  {

	CountDownLatch latch = null;
	String serviceName = null;
	
	public AbstractServerHealthChecker(String svcName,CountDownLatch latch){
		this.latch = latch;
		this.serviceName = svcName;
	}
	
	public Boolean call() throws Exception {
		boolean isUp = false;
		try {
			isDeviceReady();
			isUp = true;
		} catch (Throwable e) {
			// catch error.
		}finally{
			// we should do this in finally or else it can be a deadlock if calling method gives exception in some way.
			latch.countDown(); 
		}
		return isUp;
	}
	
	public String getServiceName(){
		return this.serviceName;
	}

	public abstract boolean isDeviceReady();
	
}
