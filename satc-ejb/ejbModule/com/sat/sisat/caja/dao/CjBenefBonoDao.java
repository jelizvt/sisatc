package com.sat.sisat.caja.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import com.sat.sisat.caja.dto.CjBenefBono;
import com.sat.sisat.common.dao.GeneralDao;


public class CjBenefBonoDao extends GeneralDao{

	
	private EntityManager em;
	private DataSource ds;
	

	public CjBenefBonoDao(EntityManager em, DataSource ds) {
		super();
		this.em = em;
		this.ds = ds;
	}
	
	public <T> T create(T t) {
		this.em.persist(t);
		this.em.flush(); 		
		return t;
	}

}
