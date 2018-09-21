package org.nico.ourbatis.exception;

public class EmptyCollectionException extends RuntimeException{

	private static final long serialVersionUID = -5819302738797447168L;

	public EmptyCollectionException() {
		super();
	}

	public EmptyCollectionException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EmptyCollectionException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmptyCollectionException(String message) {
		super(message);
	}

	public EmptyCollectionException(Throwable cause) {
		super(cause);
	}

}
