package com.wch.guava;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
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
		        .maximumSize(1000)
		        .expireAfterWrite(10, TimeUnit.MINUTES)
		        .removalListener(new RemovalListener<String, Object>() {
					@Override
					public void onRemoval(RemovalNotification<String, Object> notification) {
						//处理删除缓存的 Key Value
					}
				} )
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
	}
}
