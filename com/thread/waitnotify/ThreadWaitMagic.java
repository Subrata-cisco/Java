public class ThreadWaitMagic {
	public static void main(String[] args) {
		Lock lock = new Lock();
		
		Thread t1 = new Thread(new ReadThread(lock));
		t1.setName("Read");
		
		Thread t2 = new Thread(new WriteThread(lock));
		t2.setName("Write");

		t1.start();
		t2.start();
	}
}

class Lock {
	boolean read = true;
	boolean write = false;

	public void setReadLock() {
		read = true;
		write = false;
	}

	public void setWriteLock() {
		read = false;
		write = true;
	}

	public boolean readAllowed() {
		return read;
	}

	public boolean writeAllowed() {
		return write;
	}
}

class ReadThread implements Runnable {

	Lock lock = null;

	ReadThread(Lock lock) {
		this.lock = lock;
	}

	@Override
	public void run() {
		while (true) {
			synchronized (lock) {
				try {
					while (lock.writeAllowed()) {
						lock.wait();
					}
				} catch (InterruptedException e) {

				}

				System.out.println("ThreadRead.run(1) : "+Thread.currentThread().getName());
				lock.setWriteLock();
				lock.notifyAll();
			}
		}

	}

}

class WriteThread implements Runnable {
	Lock lock = null;

	WriteThread(Lock lock) {
		this.lock = lock;
	}

	@Override
	public void run() {
		while (true) {
			synchronized (lock) {
				try {
					while (lock.readAllowed()) {
						lock.wait();
					}
				} catch (InterruptedException e) {

				}

				System.out.println("ThreadRead.run(2) : "+Thread.currentThread().getName());
				lock.setReadLock();
				lock.notifyAll();
			}
		}

	}

}
