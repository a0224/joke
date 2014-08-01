package com.joke.interceptor;

public class JsonHandlerException extends Exception {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4788951533205831941L;

	public JsonHandlerException() {
		super();
	}

	public JsonHandlerException(String message) {
		super(message);
	}

	public JsonHandlerException(String message, Throwable cause) {
		super(message, cause);
	}

	public JsonHandlerException(Throwable cause) {
		super(cause);
	}
}
