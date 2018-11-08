package com.mx.solinte.projectname.bo;

import com.mx.solinte.projectname.exception.AplicacionException;
/**
 * Interface CifradoBO contine un servicio para la consulta del cifrado
 * 
 * @author solinte
 *
 */
public interface CifradoBo {
	
	/**
	 * Metodo para la consulta la llave secreta utilzando la capa CifradoBO
	 */
	public String getSecretKey(String key) throws AplicacionException;
		
}
