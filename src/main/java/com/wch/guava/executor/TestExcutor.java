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
 * <pre>ListenableFuture
 * 
 * 方法	描述	参考
	transform(ListenableFuture<A>, AsyncFunction<A, B>, Executor)*	返回一个新的ListenableFuture ，该ListenableFuture 返回的result是由传入的AsyncFunction 参数指派到传入的 ListenableFuture中.	transform(ListenableFuture<A>, AsyncFunction<A, B>)
	transform(ListenableFuture<A>, Function<A, B>, Executor)	返回一个新的ListenableFuture ，该ListenableFuture 返回的result是由传入的Function 参数指派到传入的 ListenableFuture中.	transform(ListenableFuture<A>, Function<A, B>)
	allAsList(Iterable<ListenableFuture<V>>)	返回一个ListenableFuture ，该ListenableFuture 返回的result是一个List，List中的值是每个ListenableFuture的返回值，假如传入的其中之一fails或者cancel，这个Future fails 或者canceled	allAsList(ListenableFuture<V>...)
	successfulAsList(Iterable<ListenableFuture<V>>)	返回一个ListenableFuture ，该Future的结果包含所有成功的Future，按照原来的顺序，当其中之一Failed或者cancel，则用null替代	successfulAsList(ListenableFuture<V>...)
 * 
 * 
 * </pre>
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

