package com.thread.threadpool.custom;

public interface ResultListener<T> {
	public void finish(T obj);
	public void error(Exception ex);
}
