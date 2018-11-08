package com.mx.solinte.projectname.utils;

import java.net.URL;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class Debug {
	/**
	 * Costructor de la clase
	 */
	private Debug() {
	}

	private static Logger logger;
	private static boolean debug = false;
	/**
	 * Metodo para debugear la la clase
	 */
	static {

		URL url = Debug.class.getClassLoader().getResource("log4j.xml");
		DOMConfigurator.configure(url);

		logger = Logger.getRootLogger();

		setLogger("com.opensymphony");
		setLogger("org.hibernate.cache");
		setLogger("org.hibernate.loader");
		setLogger("org.springframework");
		setLogger("org.hibernate.transaction");
		setLogger("org.hibernate.jdbc");
		setLogger("org.hibernate.cfg");
		setLogger("org.hibernate.persister");
		setLogger("org.hibernate.util");
		setLogger("org.hibernate.connection");
		setLogger("org.hibernate.event");
		setLogger("org.hibernate.pretty");
		setLogger("org.hibernate.engine");
		setLogger("org.hibernate.type");
		setLogger("org.hibernate.impl");
		setLogger("org.hibernate.validator");
		setLogger("org.hibernate.hql");
		setLogger("org.hibernate.annotations");
		setLogger("org.hibernate.dialect.Dialect");
		setLogger("org.hibernate.exception.SQLExceptionConverterFactory");
		setLogger("com.mchange.v2");
		setLogger("org.hibernate.id");

		logger = Logger.getRootLogger();
		logger.setLevel(Level.DEBUG);
		logger.info("Prueba de Logg");

	}

	/**
	 * Metodo que asiga el logger
	 * 
	 * @param packages
	 *            - paquetes
	 */
	private static void setLogger(String packages) {
		logger = Logger.getLogger(packages);
		logger.setLevel(Level.FATAL);
	}

	/**
	 * Metodo para asignar el debug
	 * 
	 * @param klass
	 *            - clase
	 * 
	 */
	public static void debug(Class klass, Object s) {
		logger.debug(klass.getName() + ":" + s);
		if (s instanceof Throwable) {
			logger.error(klass.getName() + ":" + s, (Throwable) s);
		}
	}

	/**
	 * Metodo para el debug
	 */
	public static void debug(Object s) {
		logger.debug(s);
		if (s instanceof Throwable) {
			logger.error(s, (Throwable) s);
		}
	}

	/**
	 * Metodo para la informacion
	 * 
	 * @param klass
	 *            - clase
	 * @param obj
	 *            - objeto
	 */
	public static void info(Class klass, Object obj) {
		logger.info(klass.getName() + ":" + obj);
		if (obj instanceof Throwable) {
			logger.error(klass.getName() + ":" + obj, (Throwable) obj);
		}
	}

	/**
	 * Metodo para informacion
	 * 
	 * @param obj
	 *            - objeto
	 */
	public static void info(Object obj) {
		logger.info(obj);
		if (obj instanceof Throwable) {
			logger.error(obj, (Throwable) obj);
		}
	}

	/**
	 * Metodo si existe error
	 * 
	 * @param obj
	 *            - objeto
	 */
	public static void error(Object obj) {
		logger.info(obj);
		if (obj instanceof Throwable) {
			logger.error(obj, (Throwable) obj);
		}
	}

	/**
	 * Metodo de error en la clase
	 * 
	 * @param klass
	 *            - clase
	 * @param obj
	 *            - objeto
	 */
	public static void error(Class klass, Object obj) {
		logger.error(klass.getName() + ":" + obj);
		if (obj instanceof Throwable) {
			logger.error(klass.getName() + ":" + obj, (Throwable) obj);
		}
	}

	/**
	 * @return the logger
	 */
	public static Logger getLogger() {
		return logger;
	}

	/**
	 * @param logger
	 *            the logger to set
	 */
	public static void setLogger(Logger logger) {
		Debug.logger = logger;
	}

	/**
	 * @return the debug
	 */
	public static boolean isDebug() {
		return debug;
	}

	/**
	 * @param debug
	 *            the debug to set
	 */
	public static void setDebug(boolean debug) {
		Debug.debug = debug;
	}

}
