/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mx.solinte.projectname.utils;

import org.hibernate.Criteria;

/**
 * 
 * Clase abstracta para una estrategia de hibernate.
 */
public abstract class CriteriaEstrategia {
	/**
	 * Metodo para la estrategia en hibernate
	 */
	public abstract void estrategia(Criteria cliteria);
}
