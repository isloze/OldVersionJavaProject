/**
 * 
 */
package com.mx.solinte.projectname.cors;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

/**
 * @author Xpertys
 * 
 * Clase Cors para filtrado de peticiones HTTML, para api Rest.
 *
 */
@Component
public class CORSFilter implements Filter { 

	/**
	 *  Metodo de Inicializacion del Filtro CORS. 
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	/**
	 *  Metodo donde se inicializan las cabeceras de las peticiones HTTP y como se filtran.
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Credentials", "false");
		response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Content-Type, x-xsrf-token, x-auth-token");
		chain.doFilter(req, res);

	}

	/**
	 * Destructor del Filtro CORS.
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
