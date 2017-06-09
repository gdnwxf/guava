package com.wch.guava;

public class TestInterface {

	public static void main(String[] args) {
		MyInterface myInterface =  new Son();
		
		myInterface.buildStringX("dsadsa", "321213");
		
		MyInterface.buildString("sdhahdsa", "-------------");
		
		
		Son son  =  new Son();
		son.buildStringX("------------", "xzjxjzjxz");
	}
}


class Son implements MyInterface {

	@Override
	public void add() {
		// TODO Auto-generated method stub
		System.out.println("add 方法");
	}
	
}

interface MyInterface{
	
	void add() ;
	
	public static String buildString(String key,String value) {
		return key +" +++ " + value;
	}
	

	public default String buildStringX(String key,String value) {
		return key +" ****  " + value;
	}
}