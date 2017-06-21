package com.wch.guava;

import java.lang.reflect.Field;
import sun.misc.Unsafe;

/**
 * unsafe用法
 * @author qinghao
 *
 */
public class TestCompareAndIncream {

	private static volatile int a = 1;
	// private static final Unsafe unsafe = Unsafe.getUnsafe();

	private static Unsafe getUnsafeInstance()
			throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
		theUnsafeInstance.setAccessible(true);
		return (Unsafe) theUnsafeInstance.get(Unsafe.class);
	}

	public static void main(String[] args)
			throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		final TestCompareAndIncream testCompareAndIncream = new TestCompareAndIncream();
		new Thread() {
			public void run() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				;
				System.out.println(" 设置成three ");
				testCompareAndIncream.a = 3;
				System.out.println(" 设置成 " + testCompareAndIncream.a);
			};
		}.start();

		Unsafe unsafe = getUnsafeInstance();

		Field appleField = TestCompareAndIncream.class.getDeclaredField("a");
		long objectFieldOffset = unsafe.staticFieldOffset(appleField);
		System.out.println("Location of Apple: " + unsafe.staticFieldOffset(appleField));
		boolean compareAndSwapInt = false;
		System.out.println("循环设置值 ");
		do {
			compareAndSwapInt = unsafe.compareAndSwapInt(TestCompareAndIncream.class, objectFieldOffset, 3, 10);
			System.out.println(" 设置中 ");
		} while (!compareAndSwapInt);

		System.out.println("等待分隔符 ---- ");
		System.out.println(testCompareAndIncream.a);

	}
}
