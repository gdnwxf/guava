package com.wch.guava;

import com.google.common.base.Optional;

/**
 * 提醒对null 的检查
 * @author qinghao
 *
 */
public class TestNull {

	public static void main(String[] args) {

		Optional<Integer> possible = Optional.of(5);

		System.out.println(possible.isPresent()); // returns true

		System.out.println(possible.get()); // returns 5

	}
}
