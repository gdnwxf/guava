package com.wch.guava;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.AbstractIterator;
import com.google.common.collect.AbstractSequentialIterator;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.PeekingIterator;

/**
 * 
 * @author qinghao
 * <pre>
 * AbstractIterator继承了UnmodifiableIterator
 * ，所以禁止实现remove()方法。
 * 如果你需要支持remove()的迭代器，就不应该继承AbstractIterator。
 * </pre>
 *
 */
public class TestIterator {

	public static void main(String[] args) {
		
	  List<String> list = new ArrayList<String>() {
		  {
			 add("dsadsa");
			 add(null);
			 add("dsadsa");
			 add("=====");
			 add(null);
			 add("dsadsa");
		  }
	  };
	  
	  
	  Iterator<String> skipNulls = skipNulls(list.iterator());
	  
	  while (skipNulls.hasNext()) {
		  System.out.println(skipNulls.next().length());
	  }
	
	  //当遇到null 的值的时候就会停止
	  Iterator<Integer> powersOfTwo = new AbstractSequentialIterator<Integer>(1) { // 注意初始值1!
		    protected Integer computeNext(Integer previous) {
		        return (previous == 1 << 30) ? null : previous * 2;
		    }
		};
		System.out.println("-------");
		
		while (powersOfTwo.hasNext()) {
			
			System.out.println(powersOfTwo.next());
		}
		
		System.out.println(1 << 30);
		
		System.out.println(1 == 1 << 30);
		
		
		/**
		 * <pre>
		 *    通过内部状态实现 peek() ;
		 *    if (!hasPeeked) {
		 *      return iterator.next();
		 *    }
		 *    E result = peekedElement;
		 *    hasPeeked = false;
		 *    peekedElement = null;
		 * </pre>
		 * 
		 * PreOrderIterator  
		 * BreadthFirstIterator
		 * PeekingIterator
		 * 
		 */
		List<String> result = Lists.newArrayList("123","acv","jhsdajdsa");
		PeekingIterator<String> iter = Iterators.peekingIterator(result.iterator());
		while (iter.hasNext()) {
			String current = iter.next();
		    while (iter.hasNext() && iter.peek().equals(current)) {
		        //跳过重复的元素
		        iter.next();
		    }
		    System.out.println("current" + current);
		}
		 
		 
	}
	
	
	/**
	 * <pre>
	 *  包装器跳过 null 值
	 * </pre>
	 * @param in
	 * @return
	 */
	public static Iterator<String> skipNulls(final Iterator<String> in) {
	    return new AbstractIterator<String>() {
	        protected String computeNext() {
	            while (in.hasNext()) {
	                String s = in.next();
	                if (s != null) {
	                    return s;
	                }
	            }
	            return endOfData();
	        }
	    };
	}
}
