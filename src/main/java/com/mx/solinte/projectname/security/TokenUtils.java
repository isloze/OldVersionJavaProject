package com.mx.solinte.projectname.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.codec.Hex;

/**
 * Clase para creacion de tokens.
 * 
 * @author solinte
 *
 */
public class TokenUtils {

	public static final String MAGIC_KEY = "obfuscate";

	/**
	 * Metodo de entrada para creacion de tokens.
	 * 
	 * @param userDetails
	 *            - detalles del usuario
	 * @return token - casteo del token a string
	 */
	public static String createToken(UserDetails userDetails) {

		// SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");

		// Date resultdate = new Date(System.currentTimeMillis());

		/* Expires in one hour */
		long expires = System.currentTimeMillis() + (900000);// 15 min realizar
																// verificar
																// tiempo
		// Date fec = new Date(expires);

		StringBuilder tokenBuilder = new StringBuilder();
		tokenBuilder.append(userDetails.getUsername());
		tokenBuilder.append(":");
		tokenBuilder.append(expires);
		tokenBuilder.append(":");
		tokenBuilder.append(TokenUtils.computeSignature(userDetails, expires));

		return tokenBuilder.toString();
	}

	/**
	 * Creacion de la firma del token.
	 * 
	 * @param userDetails
	 *            - detalles del usuario
	 * @param expires
	 *            - expirado
	 * @return token - cadena de string del token
	 */
	public static String computeSignature(UserDetails userDetails, long expires) {
		StringBuilder signatureBuilder = new StringBuilder();
		signatureBuilder.append(userDetails.getUsername());
		signatureBuilder.append(":");
		signatureBuilder.append(expires);
		signatureBuilder.append(":");
		signatureBuilder.append(userDetails.getPassword());
		signatureBuilder.append(":");
		signatureBuilder.append(TokenUtils.MAGIC_KEY);

		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("No esta disponible el Algotimo MD5!");
		}

		return new String(Hex.encode(digest.digest(signatureBuilder.toString().getBytes())));
	}

	/**
	 * Metodo para obtener el token del nombre del usuario
	 * 
	 * @param authToken
	 *            - autentificacion de token
	 * @return null - valor nulo
	 */
	public static String getUserNameFromToken(String authToken) {
		if (null == authToken) {
			return null;
		}

		String[] parts = authToken.split(":");
		return parts[0];
	}

	/**
	 * VAlida que el token sea correcto.
	 * 
	 * @param authToken
	 *            - autentificacion del token
	 * @param userDetails
	 *            - detalles del usuario
	 * @return token - valor booleano
	 */
	public static boolean validateToken(String authToken, UserDetails userDetails) {
		String[] parts = authToken.split(":");
		long expires = Long.parseLong(parts[1]);
		String signature = parts[2];

		if (expires < System.currentTimeMillis()) {
			return false;
		}

		return signature.equals(TokenUtils.computeSignature(userDetails, expires));
	}
}