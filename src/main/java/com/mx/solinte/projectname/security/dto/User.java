package com.mx.solinte.projectname.security.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Clase Objeto de User
 * 
 * @author solinte
 *
 */
@Entity
public class User implements UserDetails, Serializable {

	/**
	 * Nombre de serie de la clase
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Propiedad id del usuario
	 */
	private Long id;
	/**
	 * Propiedad nombre del usuario
	 */
	private String name;
	/**
	 * Propiedad password del usuario
	 */
	private String password;
	/**
	 * Propiedad cadena set de roles
	 */
	private Set<String> roles = new HashSet<String>();

	/**
	 * Constructor de la clase
	 */
	protected User() {
		/* Reflection instantiation */
	}

	/**
	 * Asignar el nombre y la contrase�a
	 * 
	 * @param name
	 *            - nombre del usuario
	 * @param passwordHash
	 *            - Contrase�a
	 */
	public User(String name, String passwordHash) {
		this.name = name;
		this.password = passwordHash;
	}

	/**
	 * @return id - Id del usuario
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * Asignar el id del usuario
	 * 
	 * @param id
	 *            - id del usuario
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 
	 * @return name - nombre del usuario
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Asignar el nombre del usuario
	 * 
	 * @param name
	 *            - nombre del usuario
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return roles - rol del usuario
	 */
	public Set<String> getRoles() {
		return this.roles;
	}

	/**
	 * Asignar el rol del usuario
	 * 
	 * @param roles
	 *            - rol del usuario
	 */
	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	/**
	 * Asiganr el rol del usuario
	 * 
	 * @param role
	 *            - rol del usuario
	 */
	public void addRole(String role) {
		this.roles.add(role);
	}

	/**
	 * 
	 * @return password - contrase�a del usuario
	 */
	@Override
	public String getPassword() {
		return this.password;
	}

	/**
	 * Asignar la contrase�a del usuario
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	// public Collection<? extends GrantedAuthority> getAuthorities() {
	// Set<String> roles = this.getRoles();
	//
	// if (roles == null) {
	// return Collections.emptyList();
	// }
	//
	// Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
	// for (String role : roles) {
	// authorities.add(new SimpleGrantedAuthority(role));
	// }
	//
	// return authorities;
	// }
	
	/**
	 * @return Una coleccion de autorizacion
	 */
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		grantedAuthorities.add(new SimpleGrantedAuthority("user"));
		return grantedAuthorities;
	}

	/**
	 * 
	 * @return name - nombre del usuario
	 */
	@Override
	public String getUsername() {
		return this.name;
	}

	/**
	 * 
	 * @return valor booleano
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * @return valor booleano
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * @return valor booleano
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * @return valor booleano
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}

}