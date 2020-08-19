package com.sat.sisat.predial.business;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.ejb.EJB;

import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import com.sat.sisat.common.dao.SATCConnection;
import com.sat.sisat.common.security.TracingInterceptor;
import com.sat.sisat.common.security.UserSession;
import com.sat.sisat.persistence.CrudService;

@Interceptors(TracingInterceptor.class)
public class BaseBusiness implements Serializable {
	
	private static final long serialVersionUID = -4293021808028594468L;

	@PersistenceContext(unitName="satcconn")
	public EntityManager em;
	
	@Resource(mappedName="java:/SATCDS")
	private DataSource ds;
	
	@Resource(mappedName="java:/PERSDS") 
	public DataSource dsPers;
	
	@EJB
	SATCConnection conn;
	
	@Inject
	UserSession user; 
	
	public void setDataManager(CrudService service){
		service.setEntityManager(em);
		service.setDataSource(ds);
		service.setDataSourcePers(dsPers);
		service.setConn(conn);
	}

	public UserSession getUser() {
		return user;
	}
}

