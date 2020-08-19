package com.sat.sisat.controlycobranza.dto;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConexionJasper {

	Connection con;

	public Connection getCon() {
		
		InitialContext ic;
		try {
			ic = new InitialContext();
			DataSource con2 =  (DataSource) ic.lookup("java:/SATCDS");
			
			return con2.getConnection();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

	public void setCon(Connection con) {
		this.con = con;
	}
	
	public static ConexionJasper obtener(){
		
		return new ConexionJasper();
	}
	
}
