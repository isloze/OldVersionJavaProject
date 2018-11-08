/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mx.solinte.projectname.utils;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;

/**
 * 
 * Crea una disjuncion en hibernate.
 */
public enum StringTokenMode {

    CONJUNCTION, DISJUNCTION;
/**
 * Metodo para crear una disjucnion en hibernate
 * @return la conjuntion
 */
    public Junction createJunction() {
	if (StringTokenMode.this.ordinal() == CONJUNCTION.ordinal()) {
	    return new Conjunction();
	} else {
	    return Restrictions.disjunction();
	}
    }
}
