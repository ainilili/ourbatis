package org.nico.ourbatis.exception;

public class EmptyArrayException extends RuntimeException{

	private static final long serialVersionUID = -5819302738797447168L;

	public EmptyArrayException() {
		super();
	}

	public EmptyArrayException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EmptyArrayException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmptyArrayException(String message) {
		super(message);
	}

	public EmptyArrayException(Throwable cause) {
		super(cause);
	}

}
