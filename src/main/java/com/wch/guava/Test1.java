package com.wch.guava;

public class Test1 {

	public static void main(String[] args) {
		
		 Boolean [] element= {true,false ,false}; 
		Boolean  result = false; 
		
		 for (Boolean boolean1 : element) {
			result |= boolean1;
			System.out.println(result);
		}
	}
}
