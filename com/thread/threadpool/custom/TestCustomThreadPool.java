package com.thread.threadpool.custom;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestCustomThreadPool {

	public static void main(String[] args) throws InterruptedException {

		ExecutorService service = Executors.newFixedThreadPool(2);
		CompletionService<Integer> threadService = new ExecutorCompletionService<Integer>(service);

		long b = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			threadService.submit(new CustomCallable(i));
		}
		service.shutdown();
		System.out.println("time taken by Completion Service "
				+ (System.currentTimeMillis() - b));

		DefaultResultListener result = new DefaultResultListener();
		CustomThreadPool<Integer> newPool = new CustomThreadPool<Integer>(2, result);
		long a = System.currentTimeMillis();
		int cc = 0;

		for (int i = 0; i < 10000; i++) {
			cc = cc + i;
		}

		System.out.println("time taken without any pool "
				+ (System.currentTimeMillis() - a));

		a = System.currentTimeMillis();

		for (int i = 0; i < 10000; i++) {
			newPool.submit(new CustomCallable(i));
		}

		newPool.stop();
		System.out.println("time taken by myThreadPool "
				+ (System.currentTimeMillis() - a));
	}

}

class CustomCallable implements Callable<Integer> {
	int index = -1;

	public CustomCallable(int index) {
		this.index = index;
	}

	@Override
	public Integer call() throws Exception {
		return index;
	}
}
