package com.mx.solinte.projectname.security.dao.imp;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mx.solinte.projectname.bo.CifradoBo;
import com.mx.solinte.projectname.constants.ConstantesGeneral;
import com.mx.solinte.projectname.security.dao.UserDao;
import com.mx.solinte.projectname.security.dto.User;

/**
 * Clase que valida al usuario que se quiere conectar a los servicios Rest.
 * @author solinte
 *
 */
@Repository("jpaUserDao")
@Transactional(propagation = Propagation.REQUIRED)
public class JpaUserDao implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private CifradoBo cifradoBo;

	/**
	 * Metodo que se utiliza para buscar un usuario por su nombre de usuario
	 * @param username - Nombre de usuario para la busqueda
	 * @return El usuario encontrado por nombre de usuario
	 */
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		User user = this.findByName(username);
		
		if (null == user) {
			throw new UsernameNotFoundException("El usuario con nombre "
					+ username + " no ha sido encontrado");
		}

		return user;
	}

	/**
	 * Metodo que se utiliza para buscar un usuario por su nombre de usuario
	 * @param name - Nombre de usuario para la busqueda
	 * @return El usuario encontrado por nombre de usuario
	 */
	@Override
	public User findByName(String name) {
		
		User usr = null;
		try {
			String clave = cifradoBo.getSecretKey(name);
			

			if (clave == null) {
				return null;
			} else {
				
				usr = new User(ConstantesGeneral.PROJECT, clave);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return usr;
	}

}
