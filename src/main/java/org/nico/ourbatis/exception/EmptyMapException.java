package org.nico.ourbatis.exception;

public class EmptyMapException extends RuntimeException{

	private static final long serialVersionUID = -5819302738797447168L;

	public EmptyMapException() {
		super();
	}

	public EmptyMapException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EmptyMapException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmptyMapException(String message) {
		super(message);
	}

	public EmptyMapException(Throwable cause) {
		super(cause);
	}

}
