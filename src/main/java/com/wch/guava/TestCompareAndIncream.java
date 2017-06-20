package com.wch.guava;

import java.lang.reflect.Field;
import sun.misc.Unsafe;

public class TestCompareAndIncream {

	private  volatile int a = 1;
 

	private static Unsafe getUnsafeInstance()
			throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
		theUnsafeInstance.setAccessible(true);
		return (Unsafe) theUnsafeInstance.get(Unsafe.class);
	}

	public static void main(String[] args) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
	     final TestCompareAndIncream testCompareAndIncream  = new TestCompareAndIncream();
		   new Thread( ) {
	        	public void run() {
	        		try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};
					testCompareAndIncream.a = 3;
	        	};
	        } .start();
	        
	   
		 Unsafe unsafe = getUnsafeInstance();  
		 
		 Field appleField = TestCompareAndIncream.class.getDeclaredField("a");  
		 long objectFieldOffset = unsafe.objectFieldOffset(appleField);
	        System.out.println("Location of Apple: " + unsafe.staticFieldOffset(appleField));  
	  
	        unsafe.compareAndSwapInt(TestCompareAndIncream.class, objectFieldOffset, 3, 10);
	        System.out.println(" 等待分隔符 ---- ");
	        System.out.println( testCompareAndIncream.a);
	        
	}
}
