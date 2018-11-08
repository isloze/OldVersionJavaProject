package com.mx.solinte.projectname.security.dto;

import java.util.Map;


/**
 * Objeto de Usuaio Transfer.
 * 
 * @author Xpertys
 *
 */
public class UserTransfer
{

	/**
	 * Propiedad de Nombre.
	 */
	private final String name;

	/**
	 * Propiedad de Roles.
	 */
	private final Map<String, Boolean> roles;


	public UserTransfer(String userName, Map<String, Boolean> roles)
	{
		this.name = userName;
		this.roles = roles;
	}


	public String getName()
	{
		return this.name;
	}


	public Map<String, Boolean> getRoles()
	{
		return this.roles;
	}

}