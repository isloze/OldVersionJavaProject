package com.mx.solinte.projectname.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mx.solinte.projectname.constants.UriConstantes;
import com.mx.solinte.projectname.security.TokenUtils;
import com.mx.solinte.projectname.security.dao.imp.JpaUserDao;
import com.mx.solinte.projectname.security.dto.TokenTransfer;
import com.mx.solinte.projectname.security.dto.UserTransfer;


/**
 * Clase de Recursos de Configuaracion de Usuario, para validacion de Acceso
 * 
 * @author Xpertys
 *
 */
@RestController
@RequestMapping(UriConstantes.URI_USER_AUTH)
public class AccesoController {

	/**
	 * Inject userService
	 */
	@Autowired
	private JpaUserDao jpaUserDao;

	/** Inject authManager. */
	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authManager;
	
	/**
	 * Servicio Rest para realizar la ruta a Home de Prueba.
	 * 
	 * @param locale - Objeto Locale para fecha
	 * @param model - Objeto Model para fecha
	 * @return home - Regresa la Ruta Home de prueba
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formString = dateFormat.format(date);
		model.addAttribute("serverTime", formString);
		
		return "home.jsp";
	}

	/**
	 * Servicio Rest que recupera el usuario que esta logeado.
	 * @return UserTransfer - Contiene el usuario y los roles.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public UserTransfer getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		
		if (principal instanceof String
				&& ((String) principal).equals("anonymousUser")) {
			throw new WebApplicationException(401);
		}
		UserDetails userDetails = (UserDetails) principal;

		return new UserTransfer(userDetails.getUsername(),
				this.createRoleMap(userDetails));
	}

	/**
	 * Servicio Rest que Autentica un usuario y crea un token de autenticacion.
	 * 
	 * @param username
	 *            Nombre del usuario.
	 * @param password
	 *            Password del usuario.
	 * @return TokenTransfer - Contiene el Token generado.
	 */
	@RequestMapping(value = UriConstantes.URI_AUTHENTICATE, method=RequestMethod.POST)
	public TokenTransfer authenticate(@RequestParam("username") String username,
			@RequestParam("password") String password) {
		
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		Authentication authentication = this.authManager.authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		UserDetails userDetails = this.jpaUserDao.loadUserByUsername(username);
		
		return new TokenTransfer(TokenUtils.createToken(userDetails));
	}

	/**
	 * Metodo que crea un mapa de roles para un usuario
	 * @param userDetails - Detalle de un usuario
	 * @return roles - Mapa que contiene los perisos por roles
	 */
	private Map<String, Boolean> createRoleMap(UserDetails userDetails) {
		Map<String, Boolean> roles = new HashMap<String, Boolean>();
		for (GrantedAuthority authority : userDetails.getAuthorities()) {
			roles.put(authority.getAuthority(), Boolean.TRUE);
		}

		return roles;
	}

}