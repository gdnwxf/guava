package com.wch.guava;

import java.util.ArrayList;

import com.google.common.base.CharMatcher;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multiset;

/**
 * Functions[函数]和Predicates[断言]
 * @author wch
 *
 */
public class TestFunction {

	public static void main(String[] args) {
		Function<String, Integer> lengthFunction = new Function<String, Integer>() {
			public Integer apply(String string) {
				return string.length();
			}
		};

		lengthFunction.apply("你好啊");

		Predicate<String> allCaps = new Predicate<String>() {
			public boolean apply(String string) {
				return CharMatcher.JAVA_UPPER_CASE.matchesAllOf(string);
			}
		};
		java.util.List<String> strings = new ArrayList<String>() {
			{
				add("ABC");
				add("1231");
				add("1231");
				add("1231");
			}
		};
		Multiset<Integer> lengths = HashMultiset
				.create(Iterables.transform(Iterables.filter(strings, allCaps), lengthFunction));
		//第二种方式
		lengths = HashMultiset.create(

				FluentIterable.from(strings)

						.filter(new Predicate<String>() {

							public boolean apply(String string) {

								return CharMatcher.JAVA_UPPER_CASE.matchesAllOf(string);

							}

						})

						.transform(new Function<String, Integer>() {

							public Integer apply(String string) {

								return string.length();

							}

						}));
		 lengths = HashMultiset.create();
		 
		 //第3种方式
		for (String string : strings) {

		  if (CharMatcher.JAVA_UPPER_CASE.matchesAllOf(string)) {
		      lengths.add(string.length());
		  }
		
		}

		
		// 返回全大写字母的格式的字符串长度的数组的
		System.out.println(lengths);
		
	}
}
