package com.wch.guava;

import java.io.IOException;
import java.sql.SQLException;

import com.google.common.base.Throwables;

/**
 * 对异常的封装
 * @author wch
 *
 */
public class TestExceptions {

	public static void main(String[] args) throws IOException, SQLException {
		try {
		    someMethodThatCouldThrowAnything();
		    throw new SQLException("sql excetion") ;
		} catch (IKnowWhatToDoWithThisException e) {
		    handle(e);
		} catch (Throwable t) {
		    Throwables.throwIfInstanceOf(t, IOException.class);
		    Throwables.throwIfInstanceOf(t, SQLException.class);
		    //处理多重异常的情况
		    Throwables.throwIfUnchecked(t);
		}
		
		
	}
	
	private static void someMethodThatCouldThrowAnything() throws IKnowWhatToDoWithThisException {
		
		
	}

	private static void handle(IKnowWhatToDoWithThisException e) {
		 
		
	}
}

class IKnowWhatToDoWithThisException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public IKnowWhatToDoWithThisException() {
		// TODO Auto-generated constructor stub
	}
	public IKnowWhatToDoWithThisException(String name) {
		// TODO Auto-generated constructor stub
		super(name);
	}
	
}
