package com.wch.guava.executor;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

public class DataServiceImpl implements DataService {
	@Override
	public ListenableFuture read(RowKey rowKey) {
		
		ListenableFuture<RowKey> future = new ListenableFuture<RowKey>() {
			@Override
			public void addListener(Runnable listener, Executor executor) {
				// TODO Auto-generated method stub
			}

			@Override
			public boolean cancel(boolean mayInterruptIfRunning) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isCancelled() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isDone() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public RowKey get() throws InterruptedException, ExecutionException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public RowKey get(long timeout, TimeUnit unit)
					throws InterruptedException, ExecutionException, TimeoutException {
				// TODO Auto-generated method stub
				return null;
			}
		};
		return future;
	}
}
