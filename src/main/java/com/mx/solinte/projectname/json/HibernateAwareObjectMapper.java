package com.mx.solinte.projectname.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

/**
 * Mapper para objetos Hibernate en RestFull.
 *
 */
public class HibernateAwareObjectMapper extends ObjectMapper {
	/**
	 * Metodo para mapear los objetos con hibernate
	 */
	public HibernateAwareObjectMapper() {
        Hibernate4Module hm = new Hibernate4Module();
        registerModule(hm);
    }
}
