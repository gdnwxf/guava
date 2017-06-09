package com.wch.guava;

import java.util.Map;

import javax.swing.text.html.parser.Entity;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableClassToInstanceMap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Maps;
import com.google.common.collect.MutableClassToInstanceMap;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.RangeSet;
import com.google.common.collect.Table;
import com.google.common.collect.TreeRangeMap;
import com.google.common.collect.TreeRangeSet;


/**
 *
 * 
 * <pre>
 * 
 *  HashBasedTable：本质上用HashMap<R, HashMap<C, V>>实现；
 *	TreeBasedTable：本质上用TreeMap<R, TreeMap<C,V>>实现；
 *	ImmutableTable：本质上用ImmutableMap<R, ImmutableMap<C, V>>实现；注：ImmutableTable对稀疏或密集的数据集都有优化。
 *	ArrayTable：要求在构造时就指定行和列的大小，本质上由一个二维数组实现，以提升访问速度和密集Table的内存利用率。ArrayTable与其他Table的工作原理有点不同，请参见Javadoc了解详情。
 *  </pre>
 *  
 * @author qinghao
 *
 *
 */
public class TestMultiset {

	public static void main(String[] args) {
		Map<String, Integer> nameToId = Maps.newHashMap();
		Map<Integer, String> idToName = Maps.newHashMap();

		nameToId.put("Bob", 42);
		nameToId.put("Bob", 42);
		idToName.put(42, "Bob");
		idToName.put(42, "Bob");
		//如果"Bob"和42已经在map中了，会发生什么?
		//如果我们忘了同步两个map，会有诡异的bug发生...
		
		

		/**
		 * 矩阵
		 */
		Table<Vertex, Vertex, Double> weightedGraph = HashBasedTable.create();
		Vertex v1 = new Vertex();
		Vertex v2 = new Vertex();
		Vertex v3 = new Vertex();
		weightedGraph.put(v1, v2, 4d);
		weightedGraph.put(v1, v3, 20d);
		weightedGraph.put(v2, v3, 5d);

		Map<Vertex, Double> row = weightedGraph.row(v1); // returns a Map mapping v2 to 4, v3 to 20
		Map<Vertex, Double> column = weightedGraph.column(v3); // returns a Map mapping v1 to 20, v2 to 
		
		
		System.out.println(row);
		System.out.println(column);
		
		
		
		/**
		 * rangeSet的处理
		 * 
		 */
		RangeSet<Integer> rangeSet = TreeRangeSet.create();
		rangeSet.add(Range.closed(1, 10)); // {[1,10]}
		rangeSet.add(Range.closedOpen(11, 15));//不相连区间:{[1,10], [11,15)}
		rangeSet.add(Range.closedOpen(15, 20)); //相连区间; {[1,10], [11,20)}
		rangeSet.add(Range.openClosed(0, 0)); //空区间; {[1,10], [11,20)}
		rangeSet.remove(Range.open(5, 10)); //分割[1, 10]; {[1,5], [10,10], [11,20)}
		
		/**
		 * rangeMap的处理
		 */
		RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
		rangeMap.put(Range.closed(1, 10), "foo"); //{[1,10] => "foo"}
		rangeMap.put(Range.open(3, 6), "bar"); //{[1,3] => "foo", (3,6) => "bar", [6,10] => "foo"}
		rangeMap.put(Range.open(10, 20), "foo"); //{[1,3] => "foo", (3,6) => "bar", [6,10] => "foo", (10,20) => "foo"}
		rangeMap.remove(Range.closed(5, 11)); //{[1,3] => "foo", (3,5) => "bar", (11,20) => "foo"}
		
		/**
		 * classinstanceMap的处理
		 */
		ClassToInstanceMap<Number> numberDefaults=MutableClassToInstanceMap.create();
		ClassToInstanceMap<Number> immutableClassToInstance = ImmutableClassToInstanceMap.of(Number.class, 321321);
		numberDefaults.putInstance(Integer.class, Integer.valueOf(0));
		
		/**
		 * 不可改变的集合
		 * <String,Number>builder() 实现泛型的结构
		 */
		ImmutableMap<String, Number> im = ImmutableMap.<String,Number>builder().put("321321", 1231).build();
		
		
	}
	
}

class Vertex {
 
}
