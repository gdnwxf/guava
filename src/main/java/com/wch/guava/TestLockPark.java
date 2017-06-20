package com.wch.guava;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.LockSupport;
import java.util.function.Function;
import java.util.function.Predicate;

public class TestLockPark {
	
	private static int  [] object = {1,2};
	
	public static void main(String[] args) {
		
//		testLambda();
		
		System.out.println("线程 temp 开始 ");
		  Thread temp =  new Thread() {
		    	@Override
		    	public void run() {
		    		System.out.println(" 阻塞 开始 !!!");
		    		LockSupport.park(this);
		    		System.out.println(" 阻塞 结束 !!!");
		    		
		    		System.out.println("do work");
		    		TestLockPark.doWork();
		    	}
		    };
		  temp.start();
		  
		  Thread thread =  new Thread(()-> {
				 try {
					 Object blocker = LockSupport.getBlocker(temp);
					 System.out.println(blocker);
				    	Thread.sleep(5000);
				    	System.out.println(" -- 解锁 开始");
				    	LockSupport.unpark(temp);
				    	System.out.println(" -- 解锁 结束");
				    } catch (InterruptedException e) {
				    	e.printStackTrace();
				    }
			});
		    thread.start();
		    System.out.println("线程 temp 结束 ");
		
		
		
	
	}
	
	public static void doWork() {
		System.out.println("执行dowork");
	}

	private static void testLambda() {
		List<BigDecimal> prices = Arrays.asList(new BigDecimal("1"),new BigDecimal("2"));
		final BigDecimal totalOfDiscountedPrices =
				prices.stream()
				.filter(price -> price.compareTo(BigDecimal.valueOf(20)) > 0)
				.map(price -> price.multiply(BigDecimal.valueOf(0.9)))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
				System.out.println("Total of discounted prices: " + totalOfDiscountedPrices);
				
				MyTest mt2 = String::substring;
				System.out.println(mt2.test("Java I Love you", 2, 9));
				mt2 = (a,b,c) -> a.substring(b, c);
				System.out.println(mt2.test("Java I Love you", 2, 9));
				
				Predicate p = (t) -> {
					System.out.println(t); 
					return false;
					};
				p.test("dsadsa");
//				{()->System.out.println("Hello Lambda Expressions");};
	}
	
	
	
	
	 
}

@FunctionalInterface
interface MyTest{
   String test(String a,int b,int c);
}
