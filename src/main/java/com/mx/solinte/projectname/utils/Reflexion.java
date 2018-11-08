package com.mx.solinte.projectname.utils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.mx.solinte.projectname.exception.AplicacionException;


/**
 * Clase de utilerias de Reflexion.
 *
 */
public class Reflexion {
	/**
	 * Constructor de la clase
	 */
	private Reflexion() {
	}

	/**
	 * Metodo que asigan el id
	 * 
	 * @param obj
	 *            - objeto
	 * @param idIntroducir
	 *            - id a introducir
	 */
	static public void setId(Object obj, Object idIntroducir) {
		Field field = getField(obj.getClass(), "id");
		Method method = creaSet(field, obj.getClass());
		try {
			method.invoke(obj, new Object[] { idIntroducir });
		} catch (Exception ex) {
			Debug.getLogger().error(ex.getMessage(), ex);
			throw new AplicacionException(ex.getMessage());
		}
	}

	/**
	 * 
	 * @param obj
	 *            - objeto
	 * @return el archivo y el objeto
	 */
	static public Serializable getId(Object obj) {
		Field field = getField(obj.getClass(), "id");
		return (Serializable) invocaGet(field, obj);
	}

	/**
	 * 
	 * @param eclass
	 *            - clase
	 * @param theId
	 *            - el id
	 * @return id - el id
	 */
	public static Serializable getId(Class eclass, String theId) {

		Serializable id = null;
		String field = null;
		try {
			field = eclass.getDeclaredField("id").getType().getSimpleName();
		} catch (Exception e) {
			Debug.getLogger().error(e.getMessage(), e);
			return null;
		}

		if ("String".equals(field)) {
			id = theId;
		} else if ("Long".equals(field)) {
			id = Long.valueOf(theId);
		} else if ("Integer".equals(field)) {
			id = Integer.valueOf(theId);
		}
		return id;
	}

	/**
	 * Metodo que pasa en mayuscula el nombre del Campo (Field)
	 * 
	 * @param field
	 *            - archivo
	 * @return String - nombre del metodo
	 */
	static public String mas(Field field) {
		String nombreField = field.getName();
		String may = nombreField.substring(0, 1);
		may = may.toUpperCase();
		String nombreMetodo = may + nombreField.substring(1, nombreField.length());
		return nombreMetodo;
	}

	/**
	 * Metodo para invocar un get
	 * 
	 * @param field
	 *            - archivo
	 * @param obj
	 *            - objeto
	 * @return objetc - objeto del archivo
	 */
	static public Object invocaGet(Field field, Object obj) {
		Method get = creaGet(field, obj.getClass());
		Object object = null;
		try {
			object = get.invoke(obj);
		} catch (Exception ex) {
			Debug.getLogger().error(ex.getMessage(), ex);
			throw new AplicacionException(ex.getMessage());
		}
		return object;
	}

	/**
	 * Metodo pata crear un get
	 * 
	 * @param field
	 *            - archivo
	 * @param klass
	 *            - clase
	 * @return m - metodo a retornar
	 */
	static public Method creaGet(Field field, Class klass) {
		String nombreMetodo = null;
		nombreMetodo = "get" + mas(field);
		Method m = null;
		try {
			m = klass.getMethod(nombreMetodo);
		} catch (Exception ex) {
			Debug.getLogger().error(ex.getMessage(), ex);
			throw new AplicacionException(ex.getMessage());
		}
		return m;
	}

	/**
	 * Metodo para crear un set
	 * 
	 * @param field
	 *            - archivo
	 * @param klass
	 *            - clase
	 * @return m - valor nulo o metodo
	 */
	static public Method creaSet(Field field, Class klass) {
		String nombreMetodo = "set" + mas(field);
		Class parametros[] = { field.getType() };
		Method m = null;
		try {
			m = klass.getMethod(nombreMetodo, parametros);
		} catch (Exception ex) {
			Debug.getLogger().error(ex.getMessage(), ex);
			throw new AplicacionException(ex.getMessage());
		}
		return m;
	}

	/**
	 * Metodo que regresa un valor booleano si exite el archivo
	 * 
	 * @param field
	 *            - archivo
	 * @param a
	 *            - valor booleano
	 * @return valor booleano
	 */
	public static boolean existe(Field field, Class a) {
		try {
			Field f = getField(a, field.getName());
			if (f != null) {
				return true;
			}
		} catch (Exception ex) {
			Debug.getLogger().error(ex.getMessage(), ex);
			return false;
		}
		return false;
	}

	/**
	 * Metodo que regresa un valor booleano si existe el atributo
	 * 
	 * @param a
	 *            - valor booleano
	 * @param atributo
	 *            - atributo
	 * @return valor booleano
	 */
	public static boolean existeAtributo(Class a, String atributo) {
		try {
			Field[] m = a.getDeclaredFields();
			for (int i = 0; i < m.length; i++) {
				if (m[i].getName().equals(atributo)) {
					return true;
				}
			}

		} catch (Exception ex) {
			Debug.getLogger().error(ex.getMessage(), ex);
			return false;
		}
		return false;
	}

	/**
	 * Metodo para obtenr un archivo
	 * 
	 * @param a
	 *            - valor booleano
	 * @param name
	 *            - nombre del archivo
	 * @return valor nulo
	 */
	public static Field getField(Class a, String name) {
		Field f[] = getFields(a);
		for (int i = 0; i < f.length; i++) {
			if (f[i].getName().equals(name)) {
				return f[i];
			}
		}
		return null;
	}

	/**
	 * Metdo para lo archivos
	 * 
	 * @param c
	 *            - la clase del archivo
	 * @return f - el archivo
	 */
	public static Field[] getFields(Class c) {

		Field f[] = c.getDeclaredFields();
		Field fRes[] = null;
		if (c.getSuperclass().getPackage().getName().equals(c.getPackage().getName())) {
			Field f1[] = getFields(c.getSuperclass());
			if (f1 != null && f1.length > 0) {
				fRes = new Field[f.length + f1.length];
				int j = 0;
				for (int i = 0; i < f1.length; i++) {
					fRes[j++] = f1[i];
				}
				for (int i = 0; i < f.length; i++) {
					fRes[j++] = f[i];
				}
			}
		} else {
			return f;
		}
		if (fRes != null) {
			return fRes;
		} else {
			return f;
		}
	}

	/**
	 * 
	 * @param klass
	 *            - clase
	 * @param metodo
	 *            - metodo
	 * @return valor nulo
	 */
	public static Method getMethod(Class klass, String metodo) {
		Method m[] = getMethods(klass);
		for (int i = 0; i < m.length; i++) {
			if (m[i].getName().equals(metodo)) {
				return m[i];
			}
		}
		return null;
	}

	/**
	 * 
	 * @param klass
	 *            - clase
	 * @return m o mRest - Resultado del metodo
	 */
	private static Method[] getMethods(Class klass) {
		Method m[] = klass.getDeclaredMethods();
		Method mRes[] = null;
		if (klass.getSuperclass().getPackage().getName().equals(klass.getPackage().getName())) {
			Method m1[] = getMethods(klass.getSuperclass());
			if (m1 != null && m1.length > 0) {
				mRes = new Method[m.length + m1.length];
				int j = 0;
				for (int i = 0; i < m1.length; i++) {
					mRes[j++] = m1[i];
				}
				for (int i = 0; i < m.length; i++) {
					mRes[j++] = m[i];
				}
			}
		} else {
			return m;
		}
		if (mRes != null) {
			return mRes;
		} else {
			return m;
		}
	}

	/**
	 * Metodo que compara si existe el metodo
	 * 
	 * @param klass
	 *            - clase
	 * @param metodo
	 *            - metodo
	 * @return Valor booleano
	 */
	public static boolean existeMetodo(Class klass, String metodo) {
		Method m[] = getMethods(klass);
		for (int i = 0; i < m.length; i++) {
			Method method = m[i];
			if (method.getName().equals(metodo)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Metodopar invocar otro metodo
	 * 
	 * @param obj
	 *            - objeto
	 * @param metodo
	 *            - metodo
	 * @return Valor booleano
	 */
	public static Object invocaMetodo(Object obj, String metodo) {
		Method m = getMethod(obj.getClass(), metodo);
		Object o = null;
		try {
			o = m.invoke(obj);
		} catch (Exception ex) {
			Debug.getLogger().error(ex.getMessage(), ex);
			return null;
		}
		return o;
	}

	/**
	 * Metodo para invocar un set
	 * 
	 * @param field
	 *            - archivo
	 * @param copy
	 *            - copia del Objeto
	 * @param obj1
	 *            - Objeto1
	 */
	public static void invocaSet(Field field, Object copy, Object obj1) {
		Method m = creaSet(field, copy.getClass());
		try {
			m.invoke(copy, new Object[] { obj1 });
		} catch (Exception ex) {
			throw new AplicacionException(ex.getMessage());
		}
	}

	/**
	 * Metodo para saber si es manejable el archivo
	 * 
	 * @param field
	 *            - archivo
	 * @return - Valor booleano
	 */
	public static boolean isManejable(Field field) {
		if (Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers())) {
			return false;
		}
		return true;
	}

}
