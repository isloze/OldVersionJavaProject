package com.mx.solinte.projectname.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * Clase que regresa el mensaje de error en la autenticacion del token.
 * 
 * @author solinte
 *
 */
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {
	/**
	 * Metodo para obtener la autentificacion del token
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
				"Unauthorized: La autenticacion de Token no es valida.");
	}

}