package com.jira.exception;

public class DateException extends Exception{

	private static final long serialVersionUID = 3128889919680306148L;

	public DateException() {
		super();
		
	}

	public DateException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public DateException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public DateException(String arg0) {
		super(arg0);
	}

	public DateException(Throwable arg0) {
		super(arg0);
	}

}
