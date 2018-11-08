package com.mx.solinte.projectname.security.dto;

/**
 * Clase de Tranfer.
 * 
 * @author Xpertys
 *
 */
public class TokenTransfer
{

	private final String token;


	/**
	 * @param token
	 */
	public TokenTransfer(String token)
	{
		this.token = token;
	}


	/**
	 * @return Regresa el Token
	 */
	public String getToken()
	{
		return this.token;
	}

}