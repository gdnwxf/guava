package com.wch.guava.executor;
 
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

/**
 * <pre>ListenableFuture</pre>
 * @author qinghao
 *
 */
public class TestExcutor {
	
	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(10);
		ListeningExecutorService service = MoreExecutors.listeningDecorator(executor);
		ListenableFuture<RowKey> explosion = service.submit(new Callable<RowKey>() {
		  public RowKey call() {
		    return pushBigRedButton();
		  }
		});
		
		/**
		 * 回调函数
		 */
//		explosion.addListener(listener, executor);
		Futures.addCallback(explosion, new FutureCallback<RowKey>() {
		@Override
		public void onSuccess(RowKey result) {
			// TODO Auto-generated method stub
			System.out.println(" success ");
		}

		@Override
		public void onFailure(Throwable t) {
			// TODO Auto-generated method stub
			System.out.println(" failure ");
			
		}
		},executor);
		
 
		
		/**
		 * 实现链式监听的处理
		 * 
		 */
//		ListenableFuture rowKeyFuture = service.lookUp(query);
		DataService dataService = new DataServiceImpl();
		AsyncFunction<RowKey, QueryResult> queryFunction =
		new AsyncFunction<RowKey, QueryResult>() {
		public ListenableFuture apply(RowKey rowKey) {
//			return dataService.read(rowKey);
				return explosion;
			}
		};
		
		ListenableFuture queryFuture = Futures.transformAsync(explosion, queryFunction, executor);
		
		System.out.println(" ===============  ");
		
		Futures.addCallback(queryFuture, new FutureCallback<RowKey>() {
			@Override
			public void onSuccess(RowKey result) {
				// TODO Auto-generated method stub
				System.out.println(" success ");
			}

			@Override
			public void onFailure(Throwable t) {
				// TODO Auto-generated method stub
				System.out.println(" failure ");
				
			}
			},executor);
	}

	protected static RowKey pushBigRedButton() {
		System.out.println(" call Explosion");
		return null;
	}
}

