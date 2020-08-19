/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sat.sisat.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PreDestroy;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.sql.DataSource;

import com.sat.sisat.common.dao.SATCConnection;
import com.sat.sisat.exception.SisatException;

public class CrudServiceBean implements CrudService {

	public EntityManager em;
	private DataSource ds;
	private DataSource dsPers;
	private SATCConnection conn;

	// Datasource for Managed
	private DataSource dsJasper;
	private DataSource dsImage;

	private static CrudServiceBean instance = null;

	public CrudServiceBean() {
		try {
			InitialContext ic = new InitialContext();
			dsJasper = (DataSource) ic.lookup("java:/SATCJASPER");
			dsImage = (DataSource) ic.lookup("java:/IMAGEBD");
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static CrudServiceBean getInstance() {
		if (instance == null) {
			instance = new CrudServiceBean();
		}
		return instance;
	}

	public Connection connectJasper() throws Exception {
		return dsJasper.getConnection();
	}

	public Connection connectImage() throws SQLException {
		return dsImage.getConnection();
	}

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	public void setDataSource(DataSource ds) {
		this.ds = ds;
	}

	public void setDataSourcePers(DataSource dsPers) {
		this.dsPers = dsPers;
	}

	public void setConn(SATCConnection conn) {
		this.conn = conn;
	}

	public boolean isValidConnection(Connection conn) throws Exception {
		if (conn == null || conn.isClosed()) {
			return false;
		}
		return true;
	}

	public Connection connect() throws SisatException {
		try {
			if (!isValidConnection(conn.getConnection())) {
				conn.setConnection(ds.getConnection());
			}
		} catch (SQLException e) {
			throw new SisatException(e.getMessage(), e.getCause());
		} catch (Exception e) {
			throw new SisatException(e.getMessage(), e.getCause());
		}
		return conn.getConnection();
	}

	public Connection connectPersona() throws SisatException {
		try {
			if (!isValidConnection(conn.getConnectionPers())) {
				conn.setConnectionPers(dsPers.getConnection());
			}
		} catch (Exception e) {
			throw new SisatException(e.getMessage(), e.getCause());
		}
		return conn.getConnectionPers();
	}

	@PreDestroy
	public void close() {
		try {
			if (conn != null) {
				if (conn.getConnectionPers() != null) {
					conn.getConnectionPers().close();
					conn.setConnectionPers(null);
				}
				if (conn.getConnection() != null) {
					conn.getConnection().close();
					conn.setConnection(null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn = null;
		}
	}

	public <T> T create(T t) {
		this.em.persist(t);
		this.em.flush();
		// this.em.refresh(t);
		return t;
	}

	public <T> T find(Object id, Class<T> type) {
		return (T) this.em.find(type, id);
	}

	public void delete(Object t) {
		Object ref = this.em.getReference(t.getClass(), t);
		this.em.remove(ref);
	}

	public <T> T update(T t) {
		return (T) this.em.merge(t);
	}

	public List<Object> findByNamedQuery(String namedQueryName) {
		return this.em.createNamedQuery(namedQueryName).getResultList();
	}

	public Object findSingleByNamedQuery(String namedQueryName) {
		return this.em.createNamedQuery(namedQueryName).getSingleResult();
	}

	public Object findSingleByNamedQuery(String namedQueryName, Map<String, Object> parameters) {
		return findSingleByNamedQuery(namedQueryName, parameters, 0);
	}

	public List<Object> findByNamedQuery(String namedQueryName, Map<String, Object> parameters) {
		return findByNamedQuery(namedQueryName, parameters, 0);
	}

	public List<Object> findByNamedQuery(String queryName, int resultLimit) {
		return this.em.createNamedQuery(queryName).setMaxResults(resultLimit).getResultList();
	}

	public List<Object> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) {
		Set<Map.Entry<String, Object>> rawParameters = parameters.entrySet();
		Query query = this.em.createNamedQuery(namedQueryName);
		if (resultLimit > 0)
			query.setMaxResults(resultLimit);
		for (Map.Entry<String, Object> entry : rawParameters) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		return query.getResultList();
	}

	public Object findSingleByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) {
		Set<Map.Entry<String, Object>> rawParameters = parameters.entrySet();
		Query query = this.em.createNamedQuery(namedQueryName);
		if (resultLimit > 0)
			query.setMaxResults(resultLimit);
		for (Map.Entry<String, Object> entry : rawParameters) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		return query.getSingleResult();
	}
}
