package com.sat.sisat.imputacion.dao;

import java.sql.CallableStatement;

import com.sat.sisat.common.util.SATParameterFactory;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.persistence.CrudServiceBean;

public class ImputacionDao extends CrudServiceBean {

	public void inputarDeudaSinValores(int deterPreviaId, int deterId,int usuarioId, String terminal) throws SisatException {
		try {
			StringBuffer SQL=new StringBuffer("{call "+ SATParameterFactory.getDBNameScheme() + ".stp_inputarDeudaSinValores(?,?,?,?)}");
			CallableStatement cs = connect().prepareCall(SQL.toString());
			cs.setInt(1, deterPreviaId);
			cs.setInt(2, deterId);
			cs.setInt(3, usuarioId);
			cs.setString(4, terminal);
			cs.executeUpdate();
		} catch (Exception ex) {
			String msg = "No se ha podido inputar deudas anteriores";
			System.out.println(msg + ": " + ex);
			throw new SisatException(msg);
		}
	}
	
	public void inputarDeudaFiscalizada(int deterId,int usuarioId, String terminal) throws SisatException {
		try {
			StringBuffer SQL=new StringBuffer("{call "+ SATParameterFactory.getDBNameScheme() + ".stp_inputarDeudaFiscalizada(?,?,?)}");
			CallableStatement cs = connect().prepareCall(SQL.toString());
			cs.setInt(1, deterId);
			cs.setInt(2, usuarioId);
			cs.setString(3, terminal);
			cs.executeUpdate();
		} catch (Exception ex) {
			String msg = "No se ha podido inputar deudas anteriores";
			System.out.println(msg + ": " + ex);
			throw new SisatException(msg);
		}
	}
	
	public void inputarDeudaSinValor(int deterPreviaId, int deterId,int usuarioId, String terminal) throws SisatException {
		try {
			StringBuffer SQL=new StringBuffer("{call "+ SATParameterFactory.getDBNameScheme() + ".stp_inputarDeudaSinValor(?,?,?,?)}");
			CallableStatement cs = connect().prepareCall(SQL.toString());
			cs.setInt(1, deterPreviaId);
			cs.setInt(2, deterId);
			cs.setInt(3, usuarioId);
			cs.setString(4, terminal);
			cs.executeUpdate();
		} catch (Exception ex) {
			String msg = "No se ha podido inputar deudas anteriores";
			System.out.println(msg + ": " + ex);
			throw new SisatException(msg);
		}
	}
	
}
