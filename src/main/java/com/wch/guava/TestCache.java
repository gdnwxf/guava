package com.wch.guava;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.cache.Weigher;
import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.ImmutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;

/**
 * 
 * @author qinghao
 * 缓存 可以设置的参数
 * 
 * <pre>
  int initialCapacity = UNSET_INT;
  int concurrencyLevel = UNSET_INT;
  long maximumSize = UNSET_INT;
  long maximumWeight = UNSET_INT;
  Weigher<? super K, ? super V> weigher;

  Strength keyStrength;
  Strength valueStrength;

  long expireAfterWriteNanos = UNSET_INT;
  long expireAfterAccessNanos = UNSET_INT;
  long refreshNanos = UNSET_INT;

  Equivalence<Object> keyEquivalence;
  Equivalence<Object> valueEquivalence;
	</pre>
 * 
 *
 */
public class TestCache {

	public static void main(String[] args) throws ExecutionException {
		
		/**
		 * 
		 */
		LoadingCache<String, Object> graphs = CacheBuilder.newBuilder()
				//设置最大长度
		        //.maximumSize(1000)
		        //  设置写后失效
		        .expireAfterWrite(10, TimeUnit.MINUTES)
		        //   设置访问后失效
		        .expireAfterAccess(10, TimeUnit.MINUTES)
		        .removalListener(new RemovalListener<String, Object>() {
					@Override
					public void onRemoval(RemovalNotification<String, Object> notification) {
						//处理删除缓存的 Key Value
					}
				} )
		        // 设置权重值
		        .maximumWeight(1<<30)
		        .weigher(new Weigher<String, Object>() {
		            public int weigh(String k, Object g) {
		                return g.hashCode(); 
		            }
		        })
		        .build(
		            new CacheLoader<String, Object>() {
		            	//Graph 提供有向图 和 无向图 
		                public Graph load(String key) throws RuntimeException {
		                    return createExpensiveGraph(key);
		                }

						private Graph createExpensiveGraph(String key) {
							// TODO Auto-generated method stub
							
							/**
							 * 	ConfigurableMutableGraph  可配置的 
							 *  ImmutableGraph			  不可配置的
							 *  
							 */
							Graph<String> graph =  GraphBuilder.undirected().build();
							// GraphBuilder.directed().build(); 有向图
							// directed 有向  无向 
							//构建 不可变集合
						    ImmutableValueGraph<String, Object>  x =   ImmutableValueGraph.copyOf(ValueGraphBuilder.directed().build());	 
							graph.nodes().add(key);
							return graph;
						}
		        });
		
		
		graphs.put("nihao", "你好");

		System.out.println(graphs.get("nihao"));
		System.out.println(graphs.getIfPresent("xxx"));
		// 
		System.out.println(graphs.get("nihao1" ,
				new Callable<Object>() {
			/**
			 * 基于异步的调用  如果没有key值存在 时候才掉的
			 * <pre>
			 * 不管有没有自动加载功能，都支持get(K, Callable<V>)方法。
			 * 这个方法返回缓存中相应的值，或者用给定的Callable运算并把结果加入到缓存中。
			 * 在整个加载方法完成前，缓存项相关的可观察状态都不会更改。
			 * 这个方法简便地实现了模式"如果有缓存则返回；否则运算、缓存、然后返回"。
			 * </pre>
			 */
			public Object call() throws Exception {
				System.out.println("你的靠");
				return new Object();
			}
			
		})  );
		
		
		
		
		
		
	}
}
