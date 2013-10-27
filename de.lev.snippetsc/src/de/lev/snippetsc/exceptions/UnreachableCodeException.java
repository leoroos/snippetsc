package de.lev.snippetsc.exceptions;

public class UnreachableCodeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnreachableCodeException() {
		super();

	}

	public UnreachableCodeException(String message) {
		super(message);

	}

	public UnreachableCodeException(String message, Throwable cause) {
		super(message, cause);

	}

	public UnreachableCodeException(Throwable cause) {
		super(cause);

	}

}
