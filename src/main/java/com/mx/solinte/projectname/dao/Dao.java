package com.mx.solinte.projecname.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.exception.ConstraintViolationException;

import com.mx.solinte.projectname.exception.AplicacionException;
import com.mx.solinte.projectname.utils.CriteriaEstrategia;

/**
 * 
 * @author solinte
 *
 */
public interface Dao<T> {
	/**
	 * Metodo para eliminar el dao
	 */
	void deleteDao(Object entity);

	/**
	 * Metodo unir 2 daos 
	 */
	<T> Conjunction example2conjunctionDao(T entity) throws AplicacionException;

	/**
	 * Metodo para ehecutar un query
	 */
	int executeForSqlDao(String sql) throws AplicacionException;

	/**
	 * Metodo de que obtiene una lista de Doa
	 */
	List<T> findDao(Class<?> eclass) throws AplicacionException;

	/**
	 * Metodo que obtiene un alista de daos y su Criteria
	 */
	List<T> findDao(Class<?> eclass, CriteriaEstrategia interceptor) throws AplicacionException;

	/**
	 * Metodo que obtiene todas la entitys
	 */
	List<T> findAllEntitysDao(Class<?> entityClass) throws AplicacionException;

	/**
	 * Metodo que obtien las clases criteria de los daos
	 */
	List<T> findByClassCriterionDao(Class<?> entity, Criterion... volatiles) throws AplicacionException;

	/**
	 * Metodos que obtiene el valor selializable de los daos
	 */
	T findByIdDao(Serializable valueId, Class<?> entityClass, String... fetch) throws AplicacionException;

	/**
	 * Metodo que obtiene el numero de identificacion de los daos
	 */
	T findintByIdDao(Integer valueId, Class<?> entityClass, String... fetch) throws AplicacionException;

	/**
	 * Metodo que obtiene el id del fetch de los daos
	 */
	T findByIdFetchDao(Long valueId, Class<?> entityClass, String... fetch) throws AplicacionException;

	/**
	 * Metodo que hace un merge de una entity
	 */
	T merge(Object entity) throws ConstraintViolationException, AplicacionException;

	/**
	 * Metodo que guarda una entity
	 */
	T save(Object entity) throws AplicacionException;

	/**
	 * Metodo que revisa si la clase es la unica
	 */
	T unique(Class<?> eclass, CriteriaEstrategia estrategia) throws AplicacionException;

	/**
	 * Metodo que elimina una entity
	 */
	boolean deleteEntity(Class<?> entityClass, Long id);

	/**
	 * El flush a la base de datos
	 */
	void flush();

}