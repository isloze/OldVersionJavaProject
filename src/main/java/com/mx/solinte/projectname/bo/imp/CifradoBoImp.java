package com.mx.solinte.projectname.bo.imp;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mx.solinte.projectname.bo.CifradoBo;
import com.mx.solinte.projectname.exception.AplicacionException;
/**
 * Clase de el metodo implementado para obtener la lleve del cifrado
 * 
 * @author solinte
 *
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CifradoBoImp implements CifradoBo {


	/**
	 * Consulta la llave para cifrar una cadena.
	 * 
	 * @param key
	 *            - Llave del cifrado
	 * @return secretKey -Llave secreta
	 * @throws MantenimientoException
	 *             - Excepcion que se controla en el metodo del mantenimiento
	 */
	@Override
	public String getSecretKey(String key) throws AplicacionException {
		String secretKey = "";

		return secretKey;

	}

}
