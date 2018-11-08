package com.mx.solinte.projectname.exception;

/**
 * Exception para el sistema de Inventarios.
 *
 */

public class AplicacionException extends RuntimeException {
	
	private static final long serialVersionUID = 143434342545656L;

	/**
	 * Metodo constructor sin argumentos
	 */
	public AplicacionException() {
		super();
	}

	/**
	 * Metodo constructor con dos parametros
	 * @param arg0 - String
	 * @param arg1 - Throwable
	 */
	public AplicacionException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * Metodo constructor con un parametro String
	 * @param arg0 - String
	 */
	public AplicacionException(String arg0) {
		super(arg0);
	}

	/**
	 * Metodo constructor con un parametro Throwable
	 * @param arg0 - Throwable
	 */
	public AplicacionException(Throwable arg0) {
		super(arg0);
	}

}
