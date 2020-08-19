/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sat.sisat.persistence;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import com.sat.sisat.common.dao.SATCConnection;

/**
 *
 * @author cchaucca
 */
public interface CrudService {
	public void setEntityManager(EntityManager em);
	public void setDataSource(DataSource ds);
	public void setDataSourcePers(DataSource dsPers);
	public void setConn(SATCConnection conn);
	
	<T> T create(T t);
    <T> T find(Object id,Class<T> type);
    <T> T update(T t);
    void delete(Object t);
    List<Object> findByNamedQuery(String queryName);
    List<Object> findByNamedQuery(String queryName,int resultLimit);
    List<Object> findByNamedQuery(String namedQueryName, Map<String,Object> parameters);
    List<Object> findByNamedQuery(String namedQueryName, Map<String,Object> parameters,int resultLimit);
}
