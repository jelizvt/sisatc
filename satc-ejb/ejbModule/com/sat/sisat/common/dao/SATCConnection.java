package com.sat.sisat.common.dao;

import java.io.Serializable;
import java.sql.Connection;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class SATCConnection implements Serializable{
	
	private Connection connection=null;
	private Connection connectionPers=null;
	
	@PostConstruct
    public void initialize() {
		
	}
	
	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	public Connection getConnectionPers() {
		return connectionPers;
	}

	public void setConnectionPers(Connection connectionPers) {
		this.connectionPers = connectionPers;
	}

	@PreDestroy
	public void finalize(){
		try{
			if(connection!=null){
				connection.close();
				connection=null;
			}
			
			if(connectionPers!=null){
				connectionPers.close();
				connectionPers=null;
			}
		}catch(Exception e){
			e.printStackTrace();
			connection=null;
			connectionPers=null;
		}
	}
}
