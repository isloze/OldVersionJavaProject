package com.mx.solinte.projectname.security.dao;


import org.springframework.security.core.userdetails.UserDetailsService;

import com.mx.solinte.projectname.security.dto.User;

/**
 * Interfaz con el metodo para buscar usuario por nombre
 * @author solinte
 *
 */
public interface UserDao extends UserDetailsService {

	/**
	 * Metodo para buscar usuario por nombre
	 * @param name - String nombre del usuario
	 * @return User - usuario encontrado
	 */
	User findByName(String name);

}