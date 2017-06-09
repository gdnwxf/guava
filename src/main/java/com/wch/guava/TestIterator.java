package com.wch.guava;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.AbstractIterator;

/**
 * 
 * @author qinghao
 *
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
