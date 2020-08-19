package com.sat.sisat.common.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
//import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.persistence.CrudServiceBean;
import com.sat.sisat.persistence.entity.GnTipoCambio;
//import com.sat.sisat.tramitedocumentario.managed.Connection;

public class GeneralDao extends CrudServiceBean {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sat.sisat.common.dao.inter#obtenerTipoCambioDia(int)
	 */
	public GnTipoCambio obtenerTipoCambioDia(int tipoMonedaId) {
		try {
			StringBuilder SQL = new StringBuilder(
					"SELECT * FROM ").append(Constante.schemadb).append(".gn_tipo_cambio ");
			SQL.append("WHERE tipo_moneda_id=" + tipoMonedaId + " AND fecha='"
					+ DateUtil.getCurrentDate() + "'");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				GnTipoCambio obj = new GnTipoCambio();
				obj.setTipoCambioId(rs.getInt("tipo_cambio_id"));
				obj.setValorCompra(rs.getBigDecimal("valor_compra"));
				obj.setValorVenta(rs.getBigDecimal("valor_venta"));
				obj.setFecha(rs.getTimestamp("fecha"));
				obj.setTipoMonedaId(rs.getInt("tipo_moneda_id"));
				return obj;
			}
		} catch (Exception ex) {
			// TODO : Controller exception
			System.out.println("ERROR : " + ex);
		}
		return null;
	}

	public GnTipoCambio obtenerTipoCambio(int tipoCambioId) {
		try {
			StringBuilder SQL = new StringBuilder(
					"SELECT * FROM ").append(Constante.schemadb).append(".gn_tipo_cambio ");
			SQL.append("WHERE tipo_cambio_id=" + tipoCambioId);

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				GnTipoCambio obj = new GnTipoCambio();
				obj.setTipoCambioId(rs.getInt("tipo_cambio_id"));
				obj.setValorCompra(rs.getBigDecimal("valor_compra"));
				obj.setValorVenta(rs.getBigDecimal("valor_venta"));
				obj.setFecha(rs.getTimestamp("fecha"));
				obj.setTipoMonedaId(rs.getInt("tipo_moneda_id"));
				return obj;
			}
		} catch (Exception ex) {
			// TODO : Controller exception
			System.out.println("ERROR : " + ex);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sat.sisat.common.dao.inter#ObtenerCorrelativoTabla(java.lang.String,
	 * int)
	 */
	public int ObtenerCorrelativoTabla(String tabla, int cantidad) {
		int salida = 0;
		try {
			CallableStatement cs = connect().prepareCall(
					"{call spt_obtener_correlativo_return(?,?)}");
			cs.setString(1, tabla);
			cs.setInt(2, cantidad);
			ResultSet rs = cs.executeQuery();
			while (rs.next()) {
				salida = rs.getInt(1);
			}
		} catch (Exception ex) {
			// TODO: Controller exception
			System.out.println("ERROR: " + ex);
		}
		return salida;
	}
	
	public Integer obtenerCorrelativoTabla(String tabla){
		try {
			CallableStatement cs = connect().prepareCall("{call stp_gn_obtenercorrelativotabla(?)}");
			cs.setString(1, tabla);
			ResultSet rs = cs.executeQuery();
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception ex) {
			String msg = "No se ha podido recuperar el correlativo para la tabla " + tabla;
			System.out.println(msg + ex);
		}
		return null;
	}
	
	public Integer obtenerCorrelativoDoc(String tabla, int anno){
		try {
			CallableStatement cs = connect().prepareCall("{call stp_gn_obtenercorrelativotablaDOC(?,?)}");
			 
			cs.setString(1, tabla);
			cs.setInt(2, anno);
			ResultSet rs = cs.executeQuery();
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception ex) {
			String msg = "No se ha podido recuperar el correlativo para la tabla " + tabla;
			System.out.println(msg + ex);
		}
		return null;
	}
		
	public Integer obtenerIdMaximo(String tabla, int anno){
		try {
			CallableStatement cs = connect().prepareCall("{call stp_gn_obtenerIdMaximoTdExp(?,?)}");
			cs.setString(1, tabla);
			cs.setInt(2, anno);
			ResultSet rs = cs.executeQuery();
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception ex) {
			String msg = "No se ha podido recuperar el id maximo ";
			System.out.println(msg + ex);
		}
		return null;
	}
	
	public Integer obtenerCorrelativoTablabyPeriodo(String tabla, int anno){
		try {
			CallableStatement cs = connect().prepareCall("{call stp_gn_obtenercorrelativotablabyperiodo(?,?)}");
			cs.setString(1, tabla);
			cs.setInt(2, anno);
			ResultSet rs = cs.executeQuery();
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception ex) {
			String msg = "No se ha podido recuperar el correlativo para la tabla " + tabla;
			System.out.println(msg + ex);
		}
		return null;
	}
	
	public Integer obtenerCorrelativoTablaRegistroNuevoExpediente(String tabla, Integer anno){
		try {
			CallableStatement cs = connect().prepareCall("{call stp_gn_obtenercorrelativotabla_nuevotramite(?,?)}");
			cs.setString(1, tabla);
			cs.setInt(2, anno);
			ResultSet rs = cs.executeQuery();
			while (rs.next()) {
				return rs.getInt(1) + 1;
			}
		} catch (Exception ex) {
			String msg = "No se ha podido recuperar el correlativo para la tabla " + tabla;
			System.out.println(msg + ex);
		}
		return null;
	}
	
	public Integer obtenerCorrelativoTablaRegistroDJAdult(String tabla, Integer anno){
		try {
			CallableStatement cs = connect().prepareCall("{call stp_gn_obtenercorrelativotabla_nuevotramite_dJ(?,?)}");
			cs.setString(1, tabla);
			cs.setInt(2, anno);
			ResultSet rs = cs.executeQuery();
			while (rs.next()) {
				return rs.getInt(1) + 1;
			}
		} catch (Exception ex) {
			String msg = "No se ha podido recuperar el correlativo para la tabla Dj" + tabla;
			System.out.println(msg + ex);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sat.sisat.common.dao.inter#obtenerTerminalCliente()
	 */
	public String obtenerTerminalCliente() throws Exception {
		String ip = "";
		try {
			StringBuilder SQL = new StringBuilder("SELECT ").append(Constante.schemadb).append(".terminal_cliente()  ");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next())
				ip = rs.getString(1);
			return ip;

		} catch (Exception e) {
			throw (e);
		}

	}
	
	public String obtenerCorrelativoDocumento(String tabla, String columna){
		try {
			CallableStatement cs = connect().prepareCall("{call stp_gn_obtenercorrelativodoc(?,?)}");
			cs.setString(1, tabla);
			cs.setString(2, columna);
			ResultSet rs = cs.executeQuery();
			while (rs.next()) {
				return rs.getString(1);
			}
		} catch (Exception ex) {
			String msg = "No se ha podido obtener el correlativo de documento para la tabla " + tabla;
			System.out.println(msg + ex);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sat.sisat.common.dao.inter#ObtenerCorrelativoDocumento(java.lang.
	 * String, java.lang.String)
	 */
	public String ObtenerCorrelativoDocumento(String tabla, String columna)throws Exception {
		String salida = "";
		try {
			CallableStatement cs = connect().prepareCall("{call spt_obtener_correlativo_doc_return(?,?)}");
			cs.setString(1, tabla);
			cs.setString(2, columna);
			ResultSet rs = cs.executeQuery();
			while (rs.next()) {
				salida = rs.getString(1);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return salida;
	}

	/**
	 * Obtiene el valor de uit para el año especificado
	 * 
	 * @param anio
	 *            Año
	 * @return
	 */
	public BigDecimal getUitAnio(int anio) {
		try {
			StringBuilder SQL = new StringBuilder("SELECT uit FROM ").append(Constante.schemadb).append(".gn_uit ");
			SQL.append("WHERE anno_uit=" + anio + " AND estado='1'");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();

			BigDecimal monto = null;
			while (rs.next()) {
				monto = rs.getBigDecimal("uit");
			}
			return monto;
		} catch (Exception ex) {
			// TODO : Controller Exception
			System.out.println("ERROR: " + ex);
		}
		return null;
	}

	/*
	 * funcion que trae el interes simple de un capital Autor: IVO TANTAMANGO
	 */
	public BigDecimal obtenerInteresSimple(BigDecimal capital, Date fechaInteres, Date fechaVencimiento, Date fechaConsulta)
			throws Exception {

		BigDecimal interesSimple = new BigDecimal(0);
		try {
			CallableStatement cs = connect().prepareCall("{? = call fnGN_interesSimple(?, ?, ?, ?)}");
			
			cs.registerOutParameter(1, Types.NUMERIC);

		    // Set the value for the IN parameter
			cs.setBigDecimal(2, capital);
			if(fechaInteres!=null){
				cs.setDate(3, new java.sql.Date(fechaInteres.getTime()));
			}else {
				cs.setDate(3, null);
			}
						
			cs.setDate(4, new java.sql.Date(fechaVencimiento.getTime()));
			cs.setDate(5, new java.sql.Date(fechaConsulta.getTime()));
			
		    cs.execute();
		    interesSimple = cs.getBigDecimal(1);
		    
		    return interesSimple.setScale(2,BigDecimal.ROUND_UP);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}

		
	}

	public int ObtenerCorrelativoDocumentoPorUnidadYTipo(int unidad, int tipo, int incremento)
			throws SisatException {
		 int salida = 0;
		try {
			
			CallableStatement cs = connect().prepareCall("{call spt_sel_GenerarCorrelativoDocumentoByUnidadYTipo(?,?,?) }");
			cs.setInt(1, unidad);
			cs.setInt(2, tipo);
			cs.setInt(3, incremento);
			ResultSet rs = cs.executeQuery();
			while (rs.next()) {
				salida = rs.getInt(1);
			}
		} catch (Exception ex) {
			
			throw new SisatException(ex.getMessage());
		}
		return salida;
	}
	
	public int ObtenerCodigoDocumentoPorTramo(int tipo, Date fecha)
			throws SisatException {
		 int salida = 0;
		try {
			
			CallableStatement cs = connect().prepareCall("{call spt_sel_ObtenerCodigoDocumentoByTramo(?,?) }");
			cs.setInt(1, tipo);
			cs.setDate(2, new java.sql.Date(fecha.getTime()));
			ResultSet rs = cs.executeQuery();
			while (rs.next()) {
				salida = rs.getInt(1);
			}
		} catch (Exception ex) {
			
			throw new SisatException(ex.getMessage());
		}
		return salida;
	}

	public int ObtenerCodigoUnidadPorTramo(int tipo, Date fecha)
			throws SisatException {
		 int salida = 0;
		try {
			
			CallableStatement cs = connect().prepareCall("{call spt_sel_ObtenerCodigoUnidadByTramo(?,?) }");
			cs.setInt(1, tipo);
			cs.setDate(2, new java.sql.Date(fecha.getTime()));
			ResultSet rs = cs.executeQuery();
			while (rs.next()) {
				salida = rs.getInt(1);
			}
		} catch (Exception ex) {
			
			throw new SisatException(ex.getMessage());
		}
		return salida;
	}
	
	
	
}
