package com.thread.threadpool.custom;

public class DefaultResultListener<T> implements ResultListener<T> {

	@Override
	public void finish(Object obj) {

	}

	@Override
	public void error(Exception ex) {
		ex.printStackTrace();
	}

}
