package com.wch.guava;

import java.util.List;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.PeekingIterator;

/**
 * 它能让你事先窥视[peek()]到下一次调用next()返回的元素。
 * @author qinghao
 *
 */
public class TestPeekingIterator {

	public static void main(String[] args) {
		
		List<String> result = Lists.newArrayList();
		List<String> sources = Lists.newArrayList("dsadsa" ,"123","123");
		PeekingIterator<String> iter = Iterators.peekingIterator(sources.iterator());
		while (iter.hasNext()) {
		    String current = iter.next();
		    while (iter.hasNext() && iter.peek().equals(current)) {
		        //跳过重复的元素
		        iter.next();
		    }
		    result.add(current);
		}
		System.out.println(result);
	}
}
