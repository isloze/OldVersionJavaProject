package com.mx.solinte.projectname.dao.imp;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.FlushMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mx.solinte.projecname.dao.Dao;
import com.mx.solinte.projectname.exception.AplicacionException;
import com.mx.solinte.projectname.utils.CriteriaEstrategia;
import com.mx.solinte.projectname.utils.Reflexion;
import com.mx.solinte.projectname.utils.StringTokenMode;

/**
 * Clase con objetos genericos para acceso a datos en Base de Datos.
 * 
 * @author solinte
 *
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class JpaDao<T> implements Dao<T> {

	@Autowired
	private SessionFactory sessionFactory;

	private Object entity;

	/**
	 * Metodo agregar un Tokens
	 * 
	 * @param fieldName
	 *            - Nombre del Archivo
	 * @param string
	 *            - Cadena de caracteres
	 * @param junction
	 *            - Funcion
	 */
	private void addTokens(String fieldName, String string, Conjunction junction) {

		StringTokenizer tokenizer = new StringTokenizer(string, " ");
		while (tokenizer.hasMoreTokens()) {
			junction.add(Restrictions.like(fieldName, tokenizer.nextToken(), MatchMode.ANYWHERE));
		}
	}

	/**
	 * Metodo de eliminar el dao de la entity
	 */
	@Override
	public void deleteDao(Object entity) {
		sessionFactory.getCurrentSession().setFlushMode(FlushMode.COMMIT);
		sessionFactory.getCurrentSession().delete(entity);

		// } catch (HibernateException e) {
		// System.out.println("EXceptiooooooon"+e);
		// throw new MantenimientoException(this.getClass().getName()
		// + ".delete(Object entity)", e);
		// } catch (Exception ex){
		// throw new MantenimientoException(this.getClass().getName()
		// + ".delete(Object entity)", ex);
		// }
	}

	/**
	 * Metodo de unir 2 funciones de entitys
	 */
	@Override
	public <T> Conjunction example2conjunctionDao(T entity) throws AplicacionException {
		try {
			Conjunction junction = new Conjunction();
			if (entity != null) {

				List<Field> fields = getFieldsPersist(entity.getClass().getDeclaredFields());

				for (Field field : fields) {

					Object test = Reflexion.invocaGet(field, entity);

					if (test == null || test instanceof List || test instanceof Set) {
						continue;
					}

					String fieldName = field.getName();
					if (test instanceof String) {
						addTokens(fieldName, test.toString(), junction);
					} else {
						junction.add(Restrictions.eq(fieldName, test));
					}
				}
			}
			return junction;
		} catch (Exception e) {
			throw new AplicacionException(this.getClass().getName() + ".example2conjunction(T entity)", e);
		}

	}

	/**
	 * Metodo de ejecutar los querys
	 */

	@Override
	public int executeForSqlDao(String sql) throws AplicacionException {
		try {
			return sessionFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();
		} catch (Exception e) {
			throw new AplicacionException(this.getClass().getName() + ".executeForSql(String sql)", e);
		}
	}

	/**
	 * Metodo para encontrar una clase
	 */
	@Override
	public List<T> findDao(Class<?> eclass) throws AplicacionException {
		try {
			return findDao(eclass, null);
		} catch (Exception e) {
			throw new AplicacionException(this.getClass().getName() + ".find(Class<?> eclass)", e);
		}
	}

	/**
	 * Metodo pata encontrar un Dao
	 */

	@Override
	public List<T> findDao(Class<?> eclass, CriteriaEstrategia interceptor) throws AplicacionException {
		try {
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(eclass);
			if (interceptor != null) {
				interceptor.estrategia(criteria);
			}
			List<T> list = criteria.list();
			return list;
		} catch (Exception e) {
			throw new AplicacionException(
					this.getClass().getName() + ".find(Class<?> eclass, CriteriaEstrategia interceptor)", e);
		}
	}

	/**
	 * Metodo para encontrar todas las entitys de los daos
	 */

	@Override
	public List<T> findAllEntitysDao(Class<?> entityClass) throws AplicacionException {
		try {
			return sessionFactory.getCurrentSession().createCriteria(entityClass).list();
		} catch (Exception e) {
			throw new AplicacionException(this.getClass().getName() + ".findAllEntitys(Class<?> entityClass)", e);
		}
	}

	/**
	 * Metdod para encontar las clases con su criteria dao
	 */

	@Override
	public List<T> findByClassCriterionDao(Class<?> entity, Criterion... volatiles) throws AplicacionException {
		try {
			Criteria cri = sessionFactory.getCurrentSession().createCriteria(entity);
			for (Criterion crit : volatiles) {
				cri.add(crit);
			}
			return cri.list();
		} catch (Exception e) {
			System.out.println(e);
			throw new AplicacionException(
					this.getClass().getName() + ".findByClassCriterion(Class<?> entity, Criterion... volatiles)", e);
		}
	}

	/**
	 * Metodo para encontrar un Dao por su id
	 */

	@Override
	public T findByIdDao(Serializable valueId, Class<?> entityClass, String... fetch) throws AplicacionException {
		try {
			Criteria cri = sessionFactory.getCurrentSession().createCriteria(entityClass);
			cri.add(Restrictions.idEq(valueId));
			for (String string : fetch) {
				cri.setFetchMode(string, FetchMode.JOIN);
			}
			return (T) cri.uniqueResult();
		} catch (Exception e) {
			throw new AplicacionException(this.getClass().getName()
					+ ".findById(Serializable valueId, Class<?> entityClass, String... fetch)", e);
		}
	}

	/**
	 * Metodo para encontara un fech con el id del dao
	 */

	@Override
	public T findByIdFetchDao(Long valueId, Class<?> entityClass, String... fetch) throws AplicacionException {
		try {
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(entityClass);
			criteria.add(Restrictions.idEq(valueId));

			String[] propiedades = null;
			for (String string : fetch) {
				propiedades = string.split("\\.");

				if (propiedades.length == 1) {
					criteria.setFetchMode(string, FetchMode.JOIN);
				} else {
					criteria.createCriteria(string, CriteriaSpecification.LEFT_JOIN);
				}
			}

			return (T) criteria.uniqueResult();
		} catch (Exception e) {
			throw new AplicacionException(
					this.getClass().getName() + ".findByIdFetch(Long valueId, Class<?> entityClass, String... fetch)",
					e);
		}
	}

	/**
	 * Metodo para hacer un merge a una entity
	 */

	@Override
	public T merge(Object entity) throws ConstraintViolationException, AplicacionException {
		try {
			sessionFactory.getCurrentSession().setFlushMode(FlushMode.COMMIT);
			return (T) sessionFactory.getCurrentSession().merge(entity);
		} catch (Exception e) {
			throw new AplicacionException(this.getClass().getName() + ".merge(Object entity)", e);
		}
	}

	/**
	 * Metodo para guardar una entity
	 */

	@Override
	public T save(Object entity) throws AplicacionException {
		try {
			sessionFactory.getCurrentSession().setFlushMode(FlushMode.COMMIT);
			Serializable id = sessionFactory.getCurrentSession().save(entity);
			sessionFactory.getCurrentSession().flush();
			this.entity = sessionFactory.getCurrentSession().load(entity.getClass(), id);
			return (T) this.entity;
		} catch (Exception e) {
			System.out.println(e);
			throw new AplicacionException(this.getClass().getName() + ".save(Object entity)", e);
		}
	}

	/**
	 * Metodo para encontrar una unica clase
	 */
	@Override
	public T unique(Class<?> eclass, CriteriaEstrategia interceptor) throws AplicacionException {
		try {
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(eclass);

			interceptor.estrategia(criteria);

			criteria.setMaxResults(1);
			return (T) criteria.uniqueResult();
		} catch (Exception e) {
			throw new AplicacionException(
					this.getClass().getName() + ".unique(Class<?> eclass, CriteriaEstrategia interceptor)", e);
		}
	}

	/**
	 * Metodo para unir entitys
	 * 
	 * @param entity
	 *            - clase entidad
	 * @param type
	 *            - tipo de clase
	 * @return junction - funcion
	 * @throws AplicacionException
	 *             - Excepcion que se controla en el metodo del mantenimiento
	 */

	public Conjunction exampleToConjunction(Object entity, StringTokenMode type) throws AplicacionException {
		try {
			Conjunction junction = null;
			if (entity != null) {
				junction = new Conjunction();
				List<Field> fields = getFieldsPersist(entity.getClass().getDeclaredFields());
				for (Field field : fields) {

					Object test = Reflexion.invocaGet(field, entity);

					if (test == null || test instanceof List || test instanceof Set) {
						continue;
					}
					String fieldName = field.getName();
					if (test instanceof String) {
						junction.add(addTokens(fieldName, test.toString(), type));
					} else {
						junction.add(Restrictions.eq(fieldName, test));
					}

				}
			}
			return junction;
		} catch (Exception e) {
			throw new AplicacionException(
					this.getClass().getName() + ".exampleToConjunction(Object entity, StringTokenMode type)", e);
		}

	}

	/**
	 * Metodo para obtener los archivos persistentes
	 * 
	 * @param fields
	 *            - Archivos
	 * @return junction - funcion
	 */

	public List<Field> getFieldsPersist(Field[] fields) {
		List<Field> fieldsClean = new ArrayList<Field>();
		for (Field field : fields) {
			if (Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers())
					|| Modifier.isTransient(field.getModifiers())) {
				continue;
			}
			fieldsClean.add(field);
		}
		return fieldsClean;
	}

	/**
	 * Metodo para agregar un token a la junction
	 * 
	 * @param fieldName
	 *            - nombre del archivo
	 * @param string
	 *            - cadena de caracter
	 * @param type
	 *            - tipo de archivo
	 * @return junction - funcion
	 * @throws AplicacionException
	 *             - Excepcion que se controla en el metodo del mantenimiento
	 */
	public Junction addTokens(String fieldName, String string, StringTokenMode type) throws AplicacionException {
		try {
			Junction junction = type.createJunction();
			StringTokenizer tokenizer = new StringTokenizer(string, " ");
			while (tokenizer.hasMoreTokens()) {
				junction.add(Restrictions.like(fieldName, tokenizer.nextToken().trim(), MatchMode.ANYWHERE));
			}

			return junction;
		} catch (Exception e) {
			throw new AplicacionException(
					this.getClass().getName() + ".addTokens(String fieldName, String string, StringTokenMode type)", e);
		}
	}

	/**
	 * Metodo para obtener el nombre del metodo
	 * 
	 * @param nombreField
	 *            - nombre del archivo
	 * @return nombreMetodo - Nombre del Metodo
	 * @throws AplicacionException
	 *             - Excepcion que se controla en el metodo del mantenimiento
	 */
	public String setMas(String nombreField) throws AplicacionException {
		try {
			String may = nombreField.substring(0, 1);
			may = may.toUpperCase();
			String nombreMetodo = may + nombreField.substring(1, nombreField.length());
			return nombreMetodo;
		} catch (Exception e) {
			throw new AplicacionException(this.getClass().getName() + ".setMas(String nombreField)", e);
		}
	}

	/**
	 * Metodo para encontrar un numero entero del id del dao
	 */
	@Override
	public T findintByIdDao(Integer valueId, Class<?> entityClass, String... fetch) throws AplicacionException {
		try {
			Criteria cri = sessionFactory.getCurrentSession().createCriteria(entityClass);
			cri.add(Restrictions.idEq(valueId));

			for (String string : fetch) {
				cri.setFetchMode(string, FetchMode.JOIN);
			}

			return (T) cri.uniqueResult();
		} catch (Exception e) {
			throw new AplicacionException(
					this.getClass().getName() + ".findintById(Integer valueId, Class<?> entityClass, String... fetch)",
					e);
		}
	}

	/**
	 * Metodo para eliminar una entidad
	 */
	@Override
	public boolean deleteEntity(Class<?> entityClass, Long id) {
		Object o = sessionFactory.getCurrentSession().load(entityClass, id);
		this.deleteDao(o);

		return true;
	}

	/**
	 * Metodo del flush a la base de datos
	 */
	@Override
	public void flush() {
		sessionFactory.getCurrentSession().flush();
	}

	/**
	 * @return the entity
	 */
	public Object getEntity() {
		return entity;
	}

	/**
	 * @param entity
	 *            the entity to set
	 */
	public void setEntity(Object entity) {
		this.entity = entity;
	}

	/**
	 * @return the sessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * @param sessionFactory
	 *            the sessionFactory to set
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}