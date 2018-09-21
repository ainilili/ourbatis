package org.nico.ourbatis.exception;

public class OurbatisException extends RuntimeException{

	private static final long serialVersionUID = -5819302738797447168L;

	public OurbatisException() {
		super();
	}

	public OurbatisException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public OurbatisException(String message, Throwable cause) {
		super(message, cause);
	}

	public OurbatisException(String message) {
		super(message);
	}

	public OurbatisException(Throwable cause) {
		super(cause);
	}

}
