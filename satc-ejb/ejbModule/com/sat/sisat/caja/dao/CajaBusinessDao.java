package com.sat.sisat.caja.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import com.sat.sisat.caja.dto.AgenciaOperacionDTO;
import com.sat.sisat.caja.dto.AgenciaUsuarioDTO;
import com.sat.sisat.caja.dto.CajaAperturaDTO;
import com.sat.sisat.caja.dto.CajeroDTO;
import com.sat.sisat.caja.dto.CajeroRecaudacionDTO;
import com.sat.sisat.caja.dto.CjCajaCuadreEntity;
import com.sat.sisat.caja.dto.CjCobranza;
import com.sat.sisat.caja.dto.CjGenerico;
import com.sat.sisat.caja.dto.CjMotivos;
import com.sat.sisat.caja.dto.CjPapeletaDTO;
import com.sat.sisat.caja.dto.CjPapeletaEntity;
import com.sat.sisat.caja.dto.CjPartidaEntity;
import com.sat.sisat.caja.dto.CjPersona;
import com.sat.sisat.caja.dto.CjReciboDetalleEntity;
import com.sat.sisat.caja.dto.CjReciboEntity;
import com.sat.sisat.caja.dto.CjReciboPagoEntity;
import com.sat.sisat.caja.dto.CjTipoDocumento;
import com.sat.sisat.caja.dto.CjTupaDTO;
import com.sat.sisat.caja.dto.DeudaDTO;
import com.sat.sisat.caja.dto.NumeroALetra;
import com.sat.sisat.caja.dto.PagoTupaReferenciaDTO;
import com.sat.sisat.caja.dto.ReciboPagoDTO;
import com.sat.sisat.caja.dto.ReciboPagoDescuentoDetalleDTO;
import com.sat.sisat.caja.dto.ReciboPagoDetalleDTO;
import com.sat.sisat.caja.dto.ReciboPagoDetallePieDTO;
import com.sat.sisat.caja.dto.ReciboPagoFormaPagoDTO;
import com.sat.sisat.caja.dto.ReporteCuentaDTO;
import com.sat.sisat.caja.dto.ResumenConceptosDTO;
import com.sat.sisat.caja.dto.TramoSaldoDTO;
import com.sat.sisat.caja.dto.consultaRecibojava;
import com.sat.sisat.cobranzacoactiva.dto.ResumenDeudasCobranzaCoactivaDTO;
import com.sat.sisat.common.dao.GeneralDao;
import com.sat.sisat.common.dto.GenericDTO;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.SATParameterFactory;
import com.sat.sisat.estadocuenta.dto.CrConstanciaNoAdeudo;
import com.sat.sisat.estadocuenta.dto.CrGeneralDTO;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.obligacion.dto.ResumenObligacionDTO;
import com.sat.sisat.persistence.entity.CjAgencia;
import com.sat.sisat.persistence.entity.CjAgenciaOperacion;
import com.sat.sisat.persistence.entity.CjAgenciaUsuario;
import com.sat.sisat.persistence.entity.CjCajaApertura;
import com.sat.sisat.persistence.entity.CjPagoTupa;
import com.sat.sisat.persistence.entity.CjReciboDetalle;
import com.sat.sisat.persistence.entity.CjReciboPago;
import com.sat.sisat.persistence.entity.GnTipoCambio;

public class CajaBusinessDao extends GeneralDao {

	/**
	 * Permite aperturar y cerrar una agencia de para caja.
	 * 
	 * @param agenOper Datos de la apertura de agencia.
	 * @return PK, generado.
	 * @throws SisatException 
	 */
	public Integer aperturarCerrarAgencia(AgenciaOperacionDTO agenOper) throws SisatException{
		try {
			String sql = "sptCJ_pro_AperturaAgencia ?,?,?,?,?,?";
			CallableStatement pst = connect().prepareCall(sql.toString());
			pst.setInt(1, agenOper.getAgenciaId());
			pst.setInt(2, agenOper.getUsuarioId());
			pst.setString(3, agenOper.getTerminal());
			pst.setString(4, agenOper.getFlagOperacion());
			pst.setTimestamp(5, DateUtil.getCurrentDate());
			pst.registerOutParameter(6, java.sql.Types.INTEGER);

			pst.executeUpdate();
			return pst.getInt(6);
		} catch (Exception e) {
			String msg = "No ha sido posible realizar la operacion de apertura o cierre de agencia. ";
			throw new SisatException(msg.concat(e.getMessage()));
		}
	}
	
	/**
	 * Obtiene datos de una agencia y su estado de apertura, si el usuario tiene acceso a la agencia.
	 * 
	 * @param agenciaId Identificador de agencia.
	 * @param usuarioId Identificador de usuario.
	 * @param terminal Terminal.
	 * @return Datos de agencia y su estado.
	 */
	public AgenciaOperacionDTO obtenerEstadoAgenSupervisor(int agenciaId, int usuarioId, String terminal){
		try{
			String SQL = "sptCj_sel_AgenciaAbierta ?,?,?";
			PreparedStatement pst = connect().prepareStatement(SQL);
			pst.setInt(1, agenciaId);
			pst.setInt(2, usuarioId);
			pst.setString(3, terminal);
			
			ResultSet rs = pst.executeQuery();
			if(rs.next()){
				AgenciaOperacionDTO ao = new AgenciaOperacionDTO();
				ao.setAgenciaId(rs.getInt("agencia_id"));
				ao.setAgenciaOperacionId(rs.getInt("agencia_operacion_id"));
				if(rs.getObject("fecha_apertura") != null){
					ao.setFechaApertura(rs.getDate("fecha_apertura"));
				}
				if(rs.getObject("fecha_cierre") != null){
					ao.setFechaCierre(rs.getDate("fecha_cierre"));
				}
				ao.setEstado(rs.getString("estado"));
				ao.setNombreAgencia(rs.getString("nombre_agencia"));
				ao.setEstadoDes(rs.getString("estado_des"));
				return ao;
			}
		}catch(Exception ex){
			String msg = "No se ha podido obtener el estado actual de la agencia";
			System.out.println(msg + ex);
		}
		return null;
	}
	
	/**
	 * Obtiene todas las agencias operativas.
	 * 
	 * @return Lista con datos de agencias.
	 */
	public List<AgenciaUsuarioDTO> getAllAgenciasActivas(){
		List<AgenciaUsuarioDTO> lista = new ArrayList<AgenciaUsuarioDTO>();
		try{
			String SQL = "SELECT agencia_id, nombre_agencia, nombre_corto FROM "+ SATParameterFactory.getDBNameScheme() + ".cj_agencia WHERE estado=" + Constante.ESTADO_ACTIVO;
			
			PreparedStatement pst = connect().prepareStatement(SQL);
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				AgenciaUsuarioDTO obj = new AgenciaUsuarioDTO();
				obj.setAgenciaId(rs.getInt("agencia_id"));
				obj.setNombreAgencia(rs.getString("nombre_agencia"));
				obj.setNombreCortoAgencia(rs.getString("nombre_corto"));
				lista.add(obj);
			}
		}catch(Exception ex){
			String msg = "No ha sido posible recuperar las agencias";
			System.out.println(msg + ex);
		}
		return lista;
	}

	// Cargando los NOMBRES DE LA AGENCIA

	public List<CjAgencia> obtenerNombreAgencia() {

		List<CjAgencia> lista = new ArrayList<CjAgencia>();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ");
			sql.append("agencia_id as AgenciaId,");
			sql.append("nombre_agencia as NombreAgencia ");
			sql.append("from "+ SATParameterFactory.getDBNameScheme() + ".cj_agencia");

			PreparedStatement pst = connect().prepareStatement(sql.toString());

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				CjAgencia objNA = new CjAgencia();
				objNA.setAgenciaId(rs.getInt("AgenciaId"));
				objNA.setNombreAgencia(rs.getString("NombreAgencia"));
				lista.add(objNA);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		return lista;

	}

	/* verifica si el usuario de caja esta abierto */
	public String ValidarUsuarioCaja(int cajero_id, String terminal)
			throws SQLException {

		String salida = "";
		String sql = "sptCJ_sel_AperturaCaja ?,?";

		try {

			PreparedStatement pst = connect().prepareStatement(sql);
			pst.setInt(1, cajero_id);
			pst.setString(2, terminal);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				salida = rs.getString("Mensaje");
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		return salida;

	}

	/**
	 * Permite aperturar o cerrar caja segun FlagOper (A=apertura, C=ciere).
	 * 
	 * @param cajAper Datos para apertura o cierre.
	 * @return Identificador de apertura de caja.
	 */
	public Integer aperturarCerrarCaja(CajaAperturaDTO cajAper) {
		try {
			String SQL = "sptCJ_pro_AperturaCaja ?,?,?,?,?,?";
			CallableStatement pst = connect().prepareCall(SQL);

			pst.setInt(1, cajAper.getAgenciaId());
			pst.setInt(2, cajAper.getAgenciaOperacionId());
			pst.setInt(3, cajAper.getUsuarioId());
			pst.setString(4, cajAper.getTerminal());
			pst.setString(5, cajAper.getFlagOper());
			pst.registerOutParameter(6, java.sql.Types.INTEGER);

			pst.execute();

			return pst.getInt(6);
		} catch (Exception ex) {
			String msg = "No ha sido posible aperturar o cerrar la caja";
			System.out.println(msg + ex);
		}
		return null;
	}

	/**
	 * Obtiene el estado actual de un cajero si esta aperturado o cerrado.
	 * 
	 * @param agenciaId Identificador de agencia.
	 * @param agenciaOperacionId Identificador de agencia operacion (agencia aperturada o cerrada).
	 * @param usuarioId Identificador de usuario.
	 * @param terminal Terminal de acceso.
	 * @return Datos de la apertura de caja.
	 */
	public CajaAperturaDTO obtenerEstadoActualCaja(int agenciaId, int agenciaOperacionId, int usuarioId, String terminal) {
		try {
			String sql = "sptCj_sel_CajaAbierta ?,?,?,?";

			PreparedStatement pst = connect().prepareStatement(sql.toString());
			pst.setInt(1, agenciaId);
			pst.setInt(2, agenciaOperacionId);
			pst.setInt(3, usuarioId);
			pst.setString(4, terminal);

			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				CajaAperturaDTO cap = new CajaAperturaDTO();
				cap.setAperturaId(rs.getInt("apertura_id"));
				cap.setAgenciaId(rs.getInt("agencia_id"));
				cap.setUsuarioId(rs.getInt("usuario_id"));
				cap.setAgenciaOperacionId(rs.getInt("agencia_operacion_id"));
				cap.setFechaApertura(rs.getDate("fecha_apertura"));
				Object fechaC = rs.getObject("fecha_cierre");
				if(fechaC != null){
					cap.setFechaCierre(rs.getDate("fecha_cierre"));
				}
				cap.setEstado(rs.getString("estado"));
				cap.setTerminal(rs.getString("terminal"));
				cap.setUsuarioDes(rs.getString("usuario_des"));
				cap.setAgenciaDes(rs.getString("agencia_des"));
				cap.setEstadoDes(rs.getString("estado_des"));
				return cap;
			}
		} catch (Exception ex) {
			String msg = "No se ha podido recuperar el estado actual de caja";
			System.out.println(msg + ex);
		}
		return null;
	}

	// ------------------------------------------------------------------------------------------------------------

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sat.sisat.caja.dao.inter#guardarInicio(com.sat.sisat.persistence.
	 * entity.CjAgencia)
	 */

	// PARA VENTANA DE
	// AGENCIA*************************************************************************************************
	public int guardarInicio(CjAgencia agencia) {
		int result = 0;
		try {

			StringBuffer sentenciaSQL = new StringBuffer();
			sentenciaSQL.append("INSERT INTO "+ SATParameterFactory.getDBNameScheme() + ".cj_agencia (");
			sentenciaSQL
					.append("agencia_id,nombre_agencia,nombre_corto,estado,");
			sentenciaSQL.append("usuario_id,fecha_registro,terminal)");
			sentenciaSQL.append("VALUES (?,?,?,?, ");
			sentenciaSQL.append("?,?,?,GETDATE(),? ");

			PreparedStatement pst = connect().prepareStatement(
					sentenciaSQL.toString());
			pst.setString(1, "1");
			pst.setString(2, agencia.getNombreAgencia().toString());
			pst.setString(3, agencia.getNombreCorto().toString());
			pst.setString(4, "1");
			pst.setInt(5, agencia.getUsuarioId()); // pst.setString(5, "1");
			pst.setString(6, "1");
			pst.setTimestamp(7, agencia.getFechaRegistro());

			result = pst.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sat.sisat.caja.dao.inter#guardarFinalizarAgencia(com.sat.sisat.
	 * persistence.entity.CjAgencia)
	 */
	public int guardarFinalizarAgencia(CjAgencia agencia) {

		int result = 0;
		try {

			StringBuffer sentenciaSQL = new StringBuffer();

			sentenciaSQL.append("UPDATE "+ SATParameterFactory.getDBNameScheme() + ".cj_agencia");
			sentenciaSQL.append("SET fecha_registro=?, estado='2'");
			sentenciaSQL.append("WHERE usuario_id=? AND");
			sentenciaSQL.append("nombre_corto=?");
			sentenciaSQL.append("nombre_agencia");
			sentenciaSQL.append("terminal");

			PreparedStatement pst = connect().prepareStatement(
					sentenciaSQL.toString());
			pst.setTimestamp(1, agencia.getFechaRegistro());
			pst.setInt(2, agencia.getUsuarioId());
			pst.setString(3, "1");
			pst.setString(4, "1");
			pst.setString(5, "1");

			result = pst.executeUpdate();

		} catch (Exception e) {
			result = 1;
			e.printStackTrace();

		}
		return result;
	}

	/*
	 * CajaCobranza
	 * (non-Javadoc)************************************************
	 * *************
	 * ******************************************************************
	 * 
	 * @see com.sat.sisat.caja.dao.inter#ObtenerDatPersona(int, int,
	 * java.lang.String)
	 */
	public CjPersona ObtenerDatosPersona(int persona_id, int cajeroId) {
		try {
			String sql = "stpCJ_get_Contribuyente ?,?";
			PreparedStatement pst = connect().prepareStatement(sql.toString());

			pst.setInt(1, persona_id);
			if (cajeroId > 0)
				pst.setInt(2, cajeroId);
			else
				pst.setInt(2, java.sql.Types.NULL);

			ResultSet rs = pst.executeQuery();
			CjPersona oPersona = null;
			while (rs.next()) {
				oPersona = new CjPersona();
				oPersona.setPersona_id(rs.getInt("persona_id"));

				oPersona.setAppellidosNombres(rs.getString("apellidos_nombres"));
				oPersona.setTipoDocuIdenId(rs.getInt("tipo_doc_identidad_id"));
				oPersona.setTipoDocuIdenDes(rs.getString("TipoDocIden"));
				oPersona.setNroDocuIden(rs.getString("nro_docu_identidad"));
				//oPersona.setMontoCobrar(rs.getBigDecimal("monto_cobrar"));

			}
			return oPersona;
		} catch (Exception ex) {
			String msg = "No se ha podido obtener datos de contribuyente";
			System.out.println(msg + ex);
		}
		return null;
	}

	/**
	 * Obtiene una lista de formas de pago.
	 * 
	 * @return Lista con formas de pago
	 */
	public List<GenericDTO> obtenerFormaPago() {

		List<GenericDTO> lista = new ArrayList<GenericDTO>();

		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append("SELECT forma_pago_id, descripcion ");
			SQL.append("FROM "+ SATParameterFactory.getDBNameScheme() + ".cj_forma_pago where estado = '1'");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				GenericDTO obj = new GenericDTO();
				obj.setId(rs.getInt("forma_pago_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				lista.add(obj);
			}
		} catch (Exception ex) {
			String msg = "No se ha podido recuperar las formas de pago";
			System.out.println(msg + ex);
		}
		return lista;
	}
	
	/**
	 * Obtiene una lista de formas de pago sin considerar bono cajamarquino.
	 * 
	 * @return Lista con formas de pago sin bono cajamarquino.
	 */
	public List<GenericDTO> obtenerFormaPagoSinBonoCajam() {

		List<GenericDTO> lista = new ArrayList<GenericDTO>();

		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append("SELECT forma_pago_id, descripcion ");
			SQL.append("FROM "+ SATParameterFactory.getDBNameScheme() + ".cj_forma_pago WHERE forma_pago_id NOT IN("+Constante.FORMA_PAGO_BONO_CAJAM+")");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				GenericDTO obj = new GenericDTO();
				obj.setId(rs.getInt("forma_pago_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				lista.add(obj);
			}
		} catch (Exception ex) {
			String msg = "No se ha podido recuperar las formas de pago sin bono cajamarquino";
			System.out.println(msg + ex);
		}
		return lista;
	}

	// obtener de la base de Datos Tipo de Moneda

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sat.sisat.caja.dao.inter#obtenerTipoMoneda()
	 */
	public List<CjCobranza> obtenerTipoMoneda() {

		List<CjCobranza> lista = new ArrayList<CjCobranza>();

		try {

			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ");
			sql.append(" tipo_moneda_id as TipoMonedaId,");
			sql.append(" descripcion as Descripcion ");
			sql.append("From "+ SATParameterFactory.getDBNameScheme() + ".gn_tipo_moneda");

			PreparedStatement pst = connect().prepareStatement(sql.toString());

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				CjCobranza objTM = new CjCobranza();

				objTM.setTipoMonedaId(rs.getInt("TipoMonedaId"));
				objTM.setTipoMonedaDes(rs.getString("Descripcion"));

				lista.add(objTM);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista;
	}

	// Obtener de la Base de Datos de Tipo de Banco
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sat.sisat.caja.dao.inter#obtenerTipoBanco()
	 */
	public List<GenericDTO> obtenerTipoBanco() {

		List<GenericDTO> lista = new ArrayList<GenericDTO>();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ");
			sql.append("banco_id,");
			sql.append("nombre_banco ");
			sql.append("FROM "+ SATParameterFactory.getDBNameScheme() + ".cj_banco");

			PreparedStatement pst = connect().prepareStatement(sql.toString());

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				GenericDTO ObjTB = new GenericDTO();
				ObjTB.setId(rs.getInt("banco_id"));
				ObjTB.setDescripcion(rs.getString("nombre_banco"));
				lista.add(ObjTB);
			}
		} catch (Exception ex) {
			String msg = "No se ha podido obtener los tipos de bancos";
			System.out.println(msg + ex);
		}
		return lista;
	}

	// Obtener de la Base de Datos de Tipo de Tarjeta

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sat.sisat.caja.dao.inter#obtenerTipoTarjeta()
	 */
	public List<GenericDTO> obtenerTipoTarjeta() {

		List<GenericDTO> lista = new ArrayList<GenericDTO>();

		try {

			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ");
			sql.append("tipo_tarjeta_id,");
			sql.append("descripcion ");
			sql.append("FROM "+ SATParameterFactory.getDBNameScheme() + ".cj_tipo_tarjeta");

			PreparedStatement pst = connect().prepareStatement(sql.toString());

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				GenericDTO objTT = new GenericDTO();
				objTT.setId(rs.getInt("tipo_tarjeta_id"));
				objTT.setDescripcion(rs.getString("descripcion"));
				lista.add(objTT);
			}
		} catch (Exception ex) {
			String msg = "No se ha podido recuperar tipos de tarjeta";
			System.out.println(msg + ex);
		}
		return lista;
	}

	
	public List<CjTupaDTO> ObtenerTasaTupa() {
		List<CjTupaDTO> lista = new ArrayList<CjTupaDTO>();
		try {
			String sql = "stpCj_sel_TupaPeriodo";
			PreparedStatement pst = connect().prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				CjTupaDTO objTP = new CjTupaDTO();
				objTP.setTupaId(rs.getInt("tupa_id"));
				objTP.setDescripcion(rs.getString("descripcion"));
				objTP.setTasa(rs.getBigDecimal("tasa"));
				objTP.setSubTotal(rs.getBigDecimal("tasa"));
				objTP.setPeriodo(rs.getInt("periodo"));
				lista.add(objTP);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return lista;
	}

	// OBTENER EL TIPO DE CAMBIO

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sat.sisat.caja.dao.inter#obtenerTipoCambioDia(int)
	 */
	public GnTipoCambio obtenerTipoCambioDia(int cmbValTipoMonedaId) {
		try {
			StringBuilder SQL = new StringBuilder(
					"SELECT * FROM "+ SATParameterFactory.getDBNameScheme() + ".gn_tipo_cambio ");
			SQL.append("WHERE tipo_moneda_id=" + cmbValTipoMonedaId
					+ " AND fecha='" + DateUtil.getCurrentDate() + "'");

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
			// TODO: handle exception
			System.out.println("ERROR : " + ex);
		}

		return null;
	}
	
	/**
	 * Guarda un recibo de pago
	 * 
	 * @param rp Recibo de pago
	 * @return Identificador del recibo
	 * @throws SisatException
	 */
	public Integer guardarCjReciboPago(CjReciboPago rp) throws SisatException{
		try{
			Integer reciboId = obtenerCorrelativoTabla("cj_recibo_pago");
			String numRecibo = obtenerCorrelativoDocumento("cj_recibo_pago", "nro_recibo");
			
			BigDecimal montoCalculado = ((rp.getMontoACobrar().compareTo(rp.getMontoCobrado()) >= 0 )? rp.getMontoCobrado() : rp.getMontoACobrar());
			
			String montoLetras = NumeroALetra.Convertir(montoCalculado.toString(), true);
			
			if(reciboId == null){
				throw new SisatException("No se ha podido obtener correlativo para \"cj_recibo_pago\"");
			}
			if(numRecibo == null){
				throw new SisatException("No se ha podido obtener correlativo para recibo de \"Caja\"");
			}
			rp.setReciboId(reciboId);
			rp.setNroRecibo(numRecibo);
			rp.setMontoLetra(montoLetras);
			create(rp);
			return reciboId;
		}catch(SisatException ex){
			throw ex;
		}catch(Exception ex){
			String msg = "Ha ocurrido un error guardando el recibo de pago";
			System.out.println(msg + ex);
			throw new SisatException(msg);
		}
	}
	
	/**
	 * Guarda un detalle de recibo de pago.
	 * 
	 * @param drp Detalle de recibo de pago.
	 * @throws SisatException
	 */
	public void guardarCjReciboDetalle(CjReciboDetalle drp) throws SisatException{
		try{
			create(drp);
		}catch(Exception ex){
			String msg = "Ha ocurrido un error guardando el detalle del recibo de pago";
			System.out.println(msg + ex);
			throw new SisatException(msg);
		}
	}
	
	/**
	 * Ejecuta el procedimiento de imputación de la deuda (pago de deudas).
	 * @param reciboId Identificador del recibo.
	 * @param cajeroId Persona que registra el pago.
	 * @param personaId Contribuyente al que afecta su estado de cuenta.
	 * @param montoCobrado Monto en soles que se ingresa.
	 * @param terminal PC que registra.
	 * @throws SisatException
	 */
	public void cobrarDeuda(String deudasId, int reciboId, int cajeroId, int personaId, BigDecimal montoCobrado, String terminal) throws SisatException{
		try{
			
			String query = "{call "+ SATParameterFactory.getDBNameScheme() + ".spt_CJ_CobrarDeuda('"+deudasId+"',"+reciboId+","+cajeroId+","+personaId+","+montoCobrado+",'"+terminal+"')}";
			
			Query q = em.createNativeQuery(query);				
			q.executeUpdate();
			
			//Integer resp = (Integer) q.getFirstResult();
			
			//System.out.println(resp);
			
			/*if(resp == null || resp.intValue() == 0){
				throw new Exception("Error en la imputacion del pago");
			}*/
			
		}catch (NoResultException e) {
			// TODO: handle exception
			e.printStackTrace();
		}catch (NonUniqueResultException e) {
			// TODO: handle exception
			e.printStackTrace();
		}catch(Exception ex){
			String msg = "Ha ocurrido un error en el procedimiento spt_cj_cobrardeuda";
			System.out.println(msg + ex);
			throw new SisatException(msg);
		}
	}

	/**
	 * Guarda pagos por conceptos tupa.
	 * 
	 * @param pt Objeto con datos a pagar
	 * @param cajeroId Identificador del cajero que realiza la operación.
	 * @throws SisatException
	 */
	public void cobrarDeudaTupa(CjPagoTupa pt,int cajeroId) throws SisatException {
		try {
			String SQL = "SELECT apertura_id FROM "+ SATParameterFactory.getDBNameScheme() + ".cj_caja_apertura WHERE usuario_id = " + cajeroId + " AND estado = '1'";
			Query q = em.createNativeQuery(SQL);
			Integer aperturaId = (Integer)q.getSingleResult();
			
			if(aperturaId == null){
				throw new SisatException("La caja está cerrada y no acepta pagos.");
			}
			Integer pagoTupaId = obtenerCorrelativoTabla("cj_pago_tupa");
			if(pagoTupaId == null){
				throw new SisatException("No se ha podido obtener el correlativo para \"cj_pago_tupa\"");
			}
			pt.setPagoTupaId(pagoTupaId);
			pt.setAperturaId(aperturaId);
			em.persist(pt);
		}catch(SisatException ex){
			throw ex;
		}
		catch (Exception ex) {
			String msg = "No se ha podido guardar el pago";
			System.out.println(msg + ex);
			throw new SisatException(msg);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sat.sisat.caja.dao.inter#obtenerPersona(int, int,
	 * java.lang.String)
	 */
	
	
	
	
	
	public ArrayList<CjPersona> obtenerPersona(int persona_id,
			String apellidoPat, String apellidoMat, String primerNombre,
			String segundoNombre, String razonSocial, int tipo_docu_iden_id,
			String nroDocumento, String nroPapeleta, String nroPlaca, String apellidosNombres,
			String codAnterior) {

		CjPersona persona = null;
		ArrayList<CjPersona> lista = new ArrayList<CjPersona>();
		try {
			
			String sql = "stpCJ_sel_Contribuyente ?,?,?,?,?,?,?,?,?,?,?,?";

			PreparedStatement pst = connect().prepareStatement(sql.toString());
			// Codigo
			if (persona_id > 0)
				pst.setInt(1, persona_id);
			else
				pst.setNull(1, java.sql.Types.NULL);
			// apellido_pat
			if (apellidoPat != "")
				pst.setString(2, apellidoPat);
			else
				pst.setNull(2, java.sql.Types.NULL);
			// apellido_mat
			if (apellidoMat != "")
				pst.setString(3, apellidoMat);
			else
				pst.setNull(3, java.sql.Types.NULL);
			// primer_nombre
			if (primerNombre != "")
				pst.setString(4, primerNombre);
			else
				pst.setNull(4, java.sql.Types.NULL);
			// segundo_nombre
			if (segundoNombre != "")
				pst.setString(5, segundoNombre);
			else
				pst.setNull(5, java.sql.Types.NULL);
			// razon_social
			if (razonSocial != "")
				pst.setString(6, razonSocial);
			else
				pst.setNull(6, java.sql.Types.NULL);
			// tipo documento
			if (tipo_docu_iden_id > 0)
				pst.setInt(7, tipo_docu_iden_id);
			else
				pst.setNull(7, java.sql.Types.NULL);
			// nro documento
			if (nroDocumento != "")
				pst.setString(8, nroDocumento);
			else
				pst.setNull(8, java.sql.Types.NULL);
			// papeleta
			if (nroPapeleta != "")
				pst.setString(9, nroPapeleta);
			else
				pst.setNull(9, java.sql.Types.NULL);
			// placa
			if (nroPlaca != "")
				pst.setString(10, nroPlaca);
			else
				pst.setNull(10, java.sql.Types.NULL);
			// apellidosNombres
			if (apellidosNombres != "")
				pst.setString(11, apellidosNombres);
			else
				pst.setNull(11, java.sql.Types.NULL);
			// codAnterior
			if (codAnterior != "")
				pst.setString(12, codAnterior);
			else
				pst.setNull(12, java.sql.Types.NULL);
			
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				persona = new CjPersona();
				persona.setPersona_id(rs.getInt("persona_id"));
				persona.setNombreCompleto(rs.getString("apellidos_nombres"));
				persona.setTipoDocuIdenId(rs.getInt("tipo_doc_identidad_id"));
				persona.setTipoDocuIdenDes(rs.getString("TipoDocIden"));
				persona.setNroDocuIden(rs.getString("nro_docu_identidad"));
				persona.setNroPapeleta(rs.getString("nro_papeleta"));
				persona.setPlaca(rs.getString("placa"));
				persona.setDomicilio(rs.getString("domicilio"));
				persona.setDeudaId(rs.getInt("deuda_id"));
				lista.add(persona);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	// Obtener de la Base de Datos TipoDocumento
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sat.sisat.caja.dao.inter#obtenerTipoDocumento()
	 */
	public List<CjTipoDocumento> obtenerTipoDocumento() {

		List<CjTipoDocumento> lista = new ArrayList<CjTipoDocumento>();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ");
			sql.append("tipo_doc_identidad_id as tipoDocuIdenId,");
			sql.append("descrpcion_corta as tipoDocuIdenDes ");
			sql.append("from "+ SATParameterFactory.getDBNameScheme() + ".mp_tipo_docu_identidad");

			PreparedStatement pst = connect().prepareStatement(sql.toString());

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				CjTipoDocumento objTD = new CjTipoDocumento();
				objTD.setTipoDoc(rs.getInt("tipoDocuIdenId"));
				objTD.setDescripcioncorta(rs.getString("tipoDocuIdenDes"));
				lista.add(objTD);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	/**
	 * Obtiene los subconceptos que existen en las deudas de un contribuyente.
	 * 
	 * @param personaId
	 *            Identificador del contribuyente.
	 * @return Lista de subconceptos.
	 */
	public List<GenericDTO> obtenerConceptosDeuda(int personaId) {

		List<GenericDTO> lista = new ArrayList<GenericDTO>();
		try {
			String SQL = "stpCJ_sel_SubConcepto ?";

			PreparedStatement pst = connect().prepareStatement(SQL);
			pst.setInt(1, personaId);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				GenericDTO item = new GenericDTO();
				item.setId(rs.getInt("concepto_id"));
				item.setDescripcion(rs.getString("descripcion"));
				lista.add(item);
			}
		} catch (Exception ex) {
			String msg = "No se ha podido recuperar los conceptos";
			System.out.println(msg + ex);
		}
		return lista;
	}

	/*
	 * PARA NRO DE CUOTAS (non-Javadoc)
	 * 
	 * @see com.sat.sisat.caja.dao.inter#obtenerNroCuotas()
	 */
	public List<GenericDTO> obtenerCuotas(int personaId) {

		List<GenericDTO> lista = new ArrayList<GenericDTO>();

		try {
			String sql = "stpCJ_sel_NroCuota ?";

			PreparedStatement pst = connect().prepareStatement(sql.toString());
			pst.setInt(1, personaId);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				GenericDTO item = new GenericDTO();
				item.setId(rs.getInt("cuota"));
				item.setDescripcion(rs.getString("cuota"));
				lista.add(item);
			}
		} catch (Exception ex) {
			String msg = "No se ha podido obtener los nÃºmeros de cuotas";
			System.out.println(msg + ex);
		}
		return lista;
	}

	
	public List<GenericDTO> obtenerPredios(int personaId) {

		List<GenericDTO> lista = new ArrayList<GenericDTO>();

		try {
			String sql = "stpCJ_sel_Predios_Id ?";

			PreparedStatement pst = connect().prepareStatement(sql.toString());
			pst.setInt(1, personaId);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				GenericDTO item = new GenericDTO();
				item.setId(rs.getInt("predio_id"));
				item.setDescripcion(String.valueOf(rs.getInt("predio_id")));
				lista.add(item);
			}
		} catch (Exception ex) {
			String msg = "No se ha podido obtener los números de cuotas";
			System.out.println(msg + ex);
		}
		return lista;
	}
	
	public List<GenericDTO> obtenerUso(int personaId){

		List<GenericDTO> lista = new ArrayList<GenericDTO>();

		try {
			String sql = "stpCJ_sel_Uso ?";

			PreparedStatement pst = connect().prepareStatement(sql.toString());
			pst.setInt(1, personaId);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				GenericDTO item = new GenericDTO();
				item.setId(rs.getInt("categoria_uso_id"));
				
				item.setDescripcion((rs.getString("uso") == null ?"No especificado":rs.getString("uso")));
				lista.add(item);
			}
		} catch (Exception ex) {
			String msg = "No se ha podido obtener los números de cuotas";
			System.out.println(msg + ex);
		}
		return lista;
	}
	
	public List<GenericDTO> obtenerPlacas(int personaId) {

		List<GenericDTO> lista = new ArrayList<GenericDTO>();

		try {
			String sql = "stpCJ_sel_Placas ?";

			PreparedStatement pst = connect().prepareStatement(sql.toString());
			pst.setInt(1, personaId);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				GenericDTO item = new GenericDTO();
				
				item.setDescripcion(rs.getString("placa"));
				lista.add(item);
			}
		} catch (Exception ex) {
			String msg = "No se ha podido obtener las Placas";
			System.out.println(msg + ex);
		}
		return lista;
	}
	/**
	 * Obtiene una lista de aÃ±os en los que existe deudas para un contribuyente.
	 * 
	 * @param personaId
	 *            Identificador del contribuyente.
	 * @return Lista de aÃ±os.
	 */
	public List<GenericDTO> obtenerAniosDeuda(int personaId) {

		List<GenericDTO> lista = new ArrayList<GenericDTO>();

		try {
			String sql = "stpCJ_sel_NroAnyo ?";

			PreparedStatement pst = connect().prepareStatement(sql.toString());
			pst.setInt(1, personaId);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				GenericDTO item = new GenericDTO();
				item.setId(rs.getInt("Anyo"));
				item.setDescripcion(rs.getString("Anyo"));
				lista.add(item);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	/*
	 * IMPUESTO VEHICULAR (non-Javadoc)
	 * 
	 * @see com.sat.sisat.caja.dao.inter#obtenerVehiculo(int)
	 */
	public ArrayList<CjGenerico> obtenerVehiculo(int personaId) {

		CjGenerico item = null;

		ArrayList<CjGenerico> lista = new ArrayList<CjGenerico>();

		try {

			String sql = "stpCJ_sel_Vehiculos ?";
			PreparedStatement pst = connect().prepareStatement(sql.toString());
			pst.setInt(1, personaId);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {

				item = new CjGenerico();
				item.setVehiculoId(rs.getInt("vehiculo_id"));
				item.setPlaca(rs.getString("placa"));
				item.setCategoria(rs.getString("categoria_des"));
				item.setMarca(rs.getString("marca_des"));
				item.setModelo(rs.getString("modelo_des"));
				lista.add(item);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista;
	}

	/*
	 * PARA PREDIOS CON TRIBUTOS (non-Javadoc)
	 * 
	 * @see com.sat.sisat.caja.dao.inter#obtenerPredio(int)
	 */
	public ArrayList<CjGenerico> obtenerPredio(int personaId) {

		CjGenerico item = null;

		ArrayList<CjGenerico> lista = new ArrayList<CjGenerico>();

		try {
			String sql = "stpCJ_sel_Predios ?";
			PreparedStatement pst = connect().prepareStatement(sql.toString());
			pst.setInt(1, personaId);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				item = new CjGenerico();
				item.setPredioId(rs.getInt("predio_id"));
				item.setDireccion(rs.getString("direccion_completa"));
				lista.add(item);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
		
	/*
	 * PARA MOSTAR EN LA VENTANA CUENTA CORRIENTE -PARA PAPELETAS Y PARA LA
	 * VENTANA CAJACOBRANZA
	 * 
	 * //infraccion| nroPapeleta|| placa|| monto a los 7 || monto de 7 a 15||
	 * monto mayor a 15|| COmentario
	 */

	public ArrayList<CjPapeletaEntity> obtenerPapeletaVehicular(int personaId,
			String nroPapeleta) {

		CjPapeletaEntity item = null;

		ArrayList<CjPapeletaEntity> lista = new ArrayList<CjPapeletaEntity>();
		try {
			String sql = "stpCJ_Obtener_Papeleta ?,?";
			PreparedStatement pst = connect().prepareStatement(sql.toString());
			pst.setInt(1, personaId);
			pst.setString(2, nroPapeleta);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {

				item = new CjPapeletaEntity();

				item.setFechaInfraccion(rs.getTimestamp("fechaInfraccion"));
				item.setNro_papeleta("nro_papeleta");
				item.setPlaca("placa");
				item.setMonto7(rs.getBigDecimal("monto_7_dias"));
				item.setMonto7a15(rs.getBigDecimal("monto_7_15_dias"));
				item.setMontoMayora15(rs.getBigDecimal("monto_mayor_15"));
				item.setObservacion("comentario");
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return lista;

	}

	/**
	 * Obtiene un resumen de una papeleta.
	 * 
	 * @param personaId Identificador de un contribuyente deudor por infracciÃ³n de transito.
	 * @param nroPapeleta NÃºmero de papeleta apicada.
	 * @return Una lista con una o mas papeletas de un contribuyente.
	 */
	public List<CjPapeletaDTO> obtenerPapeletaResumen(int personaId, String nroPapeleta) {
		
		List<CjPapeletaDTO> lista = new ArrayList<CjPapeletaDTO>();

		try {
			String sql = "stpCJ_Obtener_Papeleta ?,?";
			PreparedStatement pst = connect().prepareStatement(sql);
			pst.setInt(1, personaId);
			if (nroPapeleta != "")
				pst.setString(2, nroPapeleta);
			else
				pst.setNull(2, java.sql.Types.NULL);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				CjPapeletaDTO item = new CjPapeletaDTO();

				item.setPapeletaId(rs.getInt("papeleta_id"));
				item.setNroPapeleta(rs.getString("nro_papeleta"));
				item.setPlaca(rs.getString("placa"));
				item.setFechaInfraccion(rs.getDate("fecha_infraccion"));
				item.setInfractor(rs.getString("infractor_des"));
				item.setDeudaId(rs.getInt("deuda_id"));
				item.setDescInfraccion(rs.getString("desc_infraccion"));
				item.setNroResolucion(rs.getString("nro_resolucion"));
				lista.add(item);
			}
		} catch (Exception ex) {
			String msg = "No se ha podido recuperar los datos de papeleta";
			System.out.println(msg + ex);
		}
		return lista;
	}

	public List<CjPapeletaDTO> obtenerPapeletaResumenPorDeudasId(String deudasId, Integer flagModulo) throws SisatException{
		
		List<CjPapeletaDTO> lista = new ArrayList<CjPapeletaDTO>();

		try {
			String sql = "stpCJ_Obtener_Papeleta_por_deuda_id ?,?";
			PreparedStatement pst = connect().prepareStatement(sql);
			pst.setString(1, deudasId);
			pst.setInt(2, flagModulo);
			
			ResultSet rs = pst.executeQuery();
			
			while (rs.next()) {
				CjPapeletaDTO item = new CjPapeletaDTO();

				item.setPapeletaId(rs.getInt("papeleta_id"));
				item.setNroPapeleta(rs.getString("nro_papeleta"));
				item.setPlaca(rs.getString("placa"));
				item.setFechaInfraccion(rs.getDate("fecha_infraccion"));
				item.setInfractor(rs.getString("infractor_des"));
				item.setDeudaId(rs.getInt("deuda_id"));
				item.setDescInfraccion(rs.getString("desc_infraccion"));
				item.setNroResolucion(rs.getString("nro_resolucion"));
				item.setFechaNotificacion(rs.getDate("fecha_notificacion") == null ? null : new Date(rs.getDate("fecha_notificacion").getTime()));
				lista.add(item);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			String msg = "No se ha podido recuperar los datos de papeleta. ";
			throw new SisatException(msg.concat(ex.getMessage()));			
		}
		return lista;
	}

	public List<ResumenObligacionDTO> obtenerResumenObligacionesPorDeudasId(String deudasId, Integer flagModulo) throws SisatException{
		
		List<ResumenObligacionDTO> lista = new ArrayList<ResumenObligacionDTO>();

		try {
			String sql = "stp_cj_obtener_resumen_obligacion_por_deuda_id ?,?";
			PreparedStatement pst = connect().prepareStatement(sql);
			pst.setString(1, deudasId);
			pst.setInt(2, flagModulo);
			
			ResultSet rs = pst.executeQuery();
			
			while (rs.next()) {
				ResumenObligacionDTO item = new ResumenObligacionDTO();

				item.setConceptoAsString(rs.getString("descripcion"));
				item.setDeudaId(rs.getInt("deuda_id"));
				item.setFechaLiquidacion(new Date(rs.getDate("fecha_emision").getTime()));
				item.setMonto(rs.getBigDecimal("insoluto"));
				item.setNroPapeleta(rs.getString("nro_papeleta"));
				item.setNroValor(rs.getString("nro_valor"));
				lista.add(item);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			String msg = "No se ha podido recuperar los datos de papeleta. ";
			throw new SisatException(msg.concat(ex.getMessage()));			
		}
		return lista;
	}

	
	
	/**
	 * Obtiene toda la cuenta conrriente de un contribuyente.
	 * 
	 * @param cajeroId Identificador del usuario del sistema (cajero) que hace la consulta.
	 * @param personaId Identificador del contribuyente.
	 * @param fechaConsulta Fecha de consulta.
	 * @return Lista de deudas de un contribuyente.
	 * @throws SisatException Ocurre si hay un problema en el procedimiento almacenado stp_cj_buscardeuda
	 */
	public List<DeudaDTO> obtenerDeuda(int cajeroId, int personaId, String listAnios, String listConceptos, String listSubconc,	String listCuotas, Timestamp fechaConsulta) throws SisatException {

		List<DeudaDTO> lista = new ArrayList<DeudaDTO>();

		try {
			String SQL = "stp_cj_buscardeuda ?,?,?,?,?,?,?";
			PreparedStatement pst = connect().prepareStatement(SQL);
			pst.setInt(1, cajeroId);
			pst.setInt(2, personaId);
			pst.setString(3, listAnios);
			pst.setString(4, listConceptos);
			pst.setString(5, listSubconc);
			pst.setString(6, listCuotas);
			pst.setTimestamp(7, fechaConsulta);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				DeudaDTO item = new DeudaDTO();
				item.setDeudaId(rs.getInt("deuda_id"));
				item.setAnioDeuda(rs.getInt("anyo"));
				item.setNumCuota(rs.getInt("cuota"));
				item.setInsoluto(rs.getBigDecimal("insoluto"));
				item.setReajuste(rs.getBigDecimal("reajuste"));
				item.setDerechoEmi(rs.getBigDecimal("derecho_emision"));
				item.setInteresCapi(rs.getBigDecimal("interes_capitalizado"));
				item.setInteresSimp(rs.getBigDecimal("interes_simple"));
				item.setInteres(rs.getBigDecimal("interes"));
				item.setSubtotal(rs.getBigDecimal("sub_total"));
				item.setTasaDcto(rs.getBigDecimal("tasa_descuento"));
				item.setDescuento(rs.getBigDecimal("descuento"));
				item.setTotalDeuda(rs.getBigDecimal("total"));
				item.setFechaVencim(rs.getDate("fecha_vencimiento"));
				item.setFechaInteres(rs.getDate("fecha_interes"));
				item.setDeterminacionId(rs.getInt("determinacion_id"));
				item.setConceptoId(rs.getInt("concepto_id"));
				item.setConcepto(rs.getString("concepto"));
				item.setSubconceptoId(rs.getInt("subconcepto_id"));
				item.setSubconcepto(rs.getString("subconcepto"));
				item.setPlacaVe(rs.getString("placa_ve"));
				item.setPlacaPa(rs.getString("placa_pa"));
				item.setNumPapeleta(rs.getString("num_papeleta"));
				item.setDireccion(rs.getString("direccion"));
				item.setUso(rs.getString("uso"));
				item.setPersonaId(rs.getInt("persona_id"));
				item.setCajeroId(rs.getInt("agencia_usuario_id"));
				lista.add(item);
			}
		} catch (Exception ex) {
			String msg = "No se ha podido recuperar la deuda";
			System.out.println(msg + ex);
			throw new SisatException(msg);
		}
		return lista;
	}
	
	public List<DeudaDTO> obtenerDeudasPorDeudaId(int cajeroId, String listDeudasId, Timestamp fechaConsulta) throws SisatException {

		List<DeudaDTO> lista = new ArrayList<DeudaDTO>();

		try {
			String SQL = "stp_cj_buscardeuda_por_deudas_id ?,?,?";
			PreparedStatement pst = connect().prepareStatement(SQL);
			pst.setInt(1, cajeroId);			
			pst.setString(2, listDeudasId);			
			pst.setTimestamp(3, fechaConsulta);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				DeudaDTO item = new DeudaDTO();
				item.setDeudaId(rs.getInt("deuda_id"));
				item.setAnioDeuda(rs.getInt("anyo"));
				item.setNumCuota(rs.getInt("cuota"));
				item.setInsoluto(rs.getBigDecimal("insoluto"));
				item.setReajuste(rs.getBigDecimal("reajuste"));
				item.setDerechoEmi(rs.getBigDecimal("derecho_emision"));
				item.setInteresCapi(rs.getBigDecimal("interes_capitalizado"));
				item.setInteresSimp(rs.getBigDecimal("interes_simple"));
				item.setInteres(rs.getBigDecimal("interes"));
				item.setSubtotal(rs.getBigDecimal("sub_total"));
				item.setTasaDcto(rs.getBigDecimal("tasa_descuento"));
				item.setDescuento(rs.getBigDecimal("descuento"));
				item.setTotalDeuda(rs.getBigDecimal("total"));
				item.setFechaVencim(rs.getDate("fecha_vencimiento"));
				item.setFechaInteres(rs.getDate("fecha_interes"));
				item.setDeterminacionId(rs.getInt("determinacion_id"));
				item.setConceptoId(rs.getInt("concepto_id"));
				item.setConcepto(rs.getString("concepto"));
				item.setSubconceptoId(rs.getInt("subconcepto_id"));
				item.setSubconcepto(rs.getString("subconcepto"));
				item.setPlacaVe(rs.getString("placa_ve"));
				item.setPlacaPa(rs.getString("placa_pa"));
				item.setNumPapeleta(rs.getString("num_papeleta"));
				item.setDireccion(rs.getString("direccion"));
				item.setUso(rs.getString("uso"));
				item.setPersonaId(rs.getInt("persona_id"));
				item.setCajeroId(rs.getInt("agencia_usuario_id"));
				lista.add(item);
			}
		} catch (Exception ex) {
			String msg = "No se ha podido recuperar la deuda";
			System.out.println(msg + ex);
			throw new SisatException(msg);
		}
		return lista;
	}

	
	
	public List<DeudaDTO> obtenerDeudaFiltrada(int cajeroId,
			int personaId,
			String listAnios,
			String listConceptos,
			String listSubconc,
			String listCuotas,
			String listPlacas,
			String listPredios,
			String listUso,
			Timestamp fechaConsulta) throws SisatException {

		List<DeudaDTO> lista = new ArrayList<DeudaDTO>();

		try {
			String SQL = "stp_cj_buscardeuda_filtrada ?,?,?,?,?,?,?,?,?,?";
			PreparedStatement pst = connect().prepareStatement(SQL);
			pst.setInt(1, cajeroId);
			pst.setInt(2, personaId);
			pst.setString(3, listAnios);
			pst.setString(4, listConceptos);
			pst.setString(5, listSubconc);
			pst.setString(6, listCuotas);
			pst.setString(7, listPlacas);
			pst.setString(8, listPredios);
			pst.setString(9, listUso);
			pst.setTimestamp(10, fechaConsulta);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				DeudaDTO item = new DeudaDTO();
				item.setDeudaId(rs.getInt("deuda_id"));
				item.setAnioDeuda(rs.getInt("anyo"));
				item.setNumCuota(rs.getInt("cuota"));
				item.setInsoluto(rs.getBigDecimal("insoluto"));
				item.setReajuste(rs.getBigDecimal("reajuste"));
				item.setDerechoEmi(rs.getBigDecimal("derecho_emision"));
				item.setInteresCapi(rs.getBigDecimal("interes_capitalizado"));
				item.setInteresSimp(rs.getBigDecimal("interes_simple"));
				item.setInteres(rs.getBigDecimal("interes"));
				item.setSubtotal(rs.getBigDecimal("sub_total"));
				item.setTasaDcto(rs.getBigDecimal("tasa_descuento"));
				item.setDescuento(rs.getBigDecimal("descuento"));
				item.setTotalDeuda(rs.getBigDecimal("total"));
				item.setFechaVencim(rs.getDate("fecha_vencimiento"));
				item.setFechaInteres(rs.getDate("fecha_interes"));
				item.setDeterminacionId(rs.getInt("determinacion_id"));
				item.setConceptoId(rs.getInt("concepto_id"));
				item.setConcepto(rs.getString("concepto"));
				item.setSubconceptoId(rs.getInt("subconcepto_id"));
				item.setSubconcepto(rs.getString("subconcepto"));
				item.setPlacaVe(rs.getString("placa_ve"));
				item.setPlacaPa(rs.getString("placa_pa"));
				item.setNumPapeleta(rs.getString("num_papeleta"));
				item.setDireccion(rs.getString("direccion"));
				item.setUso(rs.getString("uso"));
				item.setPersonaId(rs.getInt("persona_id"));
				item.setCajeroId(rs.getInt("agencia_usuario_id"));
				lista.add(item);
			}
		} catch (Exception ex) {
			String msg = "No se ha podido recuperar la deuda";
			System.out.println(msg + ex);
			throw new SisatException(msg);
		}
		return lista;
	}
	/**
	 * Obtiene las deudas de un contribuyente aplicando un descuento, segÃºn la selecciÃ³n hecha para el pago.
	 * 
	 * @param deudas Identificadores de las deudas seleccionadas para pagar.
	 * @param fechaPago Fecha en que se realiza el pago.
	 * @param cajeroId Identificador del cajero que registra el pago.
	 * @return
	 */
	public List<DeudaDTO> obetenerDeudaConDsctos(String deudas, Date fechaPago, int cajeroId, Integer flagSimulador){
		List<DeudaDTO> lista = new ArrayList<DeudaDTO>();
		try{
			String SQL = "stp_cj_buscardeuda_condscto ?,?,?,?";
			PreparedStatement pst = connect().prepareStatement(SQL);
			pst.setString(1, deudas);
			pst.setDate(2, DateUtil.dateToSqlDate(fechaPago));
			pst.setInt(3, cajeroId);
			pst.setInt(4, flagSimulador);
			
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				DeudaDTO item = new DeudaDTO();
				item.setDeudaId(rs.getInt("deuda_id"));
				item.setAnioDeuda(rs.getInt("anyo"));
				item.setNumCuota(rs.getInt("cuota"));
				item.setInsoluto(rs.getBigDecimal("insoluto"));
				item.setReajuste(rs.getBigDecimal("reajuste"));
				item.setDerechoEmi(rs.getBigDecimal("derecho_emision"));
				item.setInteresCapi(rs.getBigDecimal("interes_capitalizado"));
				item.setInteresSimp(rs.getBigDecimal("interes_simple"));
				item.setInteres(rs.getBigDecimal("interes"));
				item.setSubtotal(rs.getBigDecimal("sub_total"));
				item.setTasaDcto(rs.getBigDecimal("tasa_descuento"));
				item.setDescuento(rs.getBigDecimal("descuento"));
				item.setTotalDeuda(rs.getBigDecimal("total"));
				item.setFechaVencim(rs.getDate("fecha_vencimiento"));
				item.setFechaInteres(rs.getDate("fecha_interes"));
				item.setDeterminacionId(rs.getInt("determinacion_id"));
				item.setConceptoId(rs.getInt("concepto_id"));
				item.setConcepto(rs.getString("concepto"));
				item.setSubconceptoId(rs.getInt("subconcepto_id"));
				item.setSubconcepto(rs.getString("subconcepto"));
				item.setPlacaVe(rs.getString("placa_ve"));
				item.setPlacaPa(rs.getString("placa_pa"));
				item.setNumPapeleta(rs.getString("num_papeleta"));
				item.setDireccion(rs.getString("direccion"));
				item.setUso(rs.getString("uso"));
				item.setPersonaId(rs.getInt("persona_id"));
				item.setCajeroId(rs.getInt("agencia_usuario_id"));
				item.setTotalPosible(rs.getBigDecimal("total_posible"));
				lista.add(item);
			}
		}catch(Exception ex){
			String msg = "No se ha podido recuperar las deudas con descuento";
			System.out.println(msg + ex);
		}
		return lista;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sat.sisat.caja.dao.inter#guardarInicio(com.sat.sisat.persistence.
	 * entity.CjAgenciaOperacion, java.lang.String)
	 */
	public int guardarInicio(CjAgenciaOperacion oAgenciaOperacion,
			String estadoForm) {
		int result = 0;
		try {
			if (estadoForm.equals("A")) {
				StringBuffer sentenciaSQL = new StringBuffer();
				sentenciaSQL.append("INSERT INTO "+ SATParameterFactory.getDBNameScheme() + ".cj_agencia_operacion (");
				sentenciaSQL
						.append(" agencia_id,agencia_operacion_id,fecha_apertura,fecha_cierre");
				sentenciaSQL
						.append(" estado,usuario_id,fecha_registro,terminal) ");
				sentenciaSQL.append(" VALUES (?,?,?,?, ");
				sentenciaSQL.append(" ?,GETDATE(),? ");

				PreparedStatement pst = connect().prepareStatement(
						sentenciaSQL.toString());
				//pst.setInt(1, oAgenciaOperacion.getUsuarioId());
				//pst.setInt(2, oAgenciaOperacion.getAgenciaOperacionId());
				pst.setTimestamp(3, oAgenciaOperacion.getFechaApertura());
				pst.setTimestamp(4, oAgenciaOperacion.getFechaCierre());
				pst.setString(5, oAgenciaOperacion.getEstado());
				//pst.setInt(6, oAgenciaOperacion.getUsuarioId());
				pst.setTimestamp(7, oAgenciaOperacion.getFechaRegistro());
				pst.setString(8, oAgenciaOperacion.getTerminal());

				result = pst.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// guardarFinalizar
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sat.sisat.caja.dao.inter#guardarFinalizar(com.sat.sisat.persistence
	 * .entity.CjAgenciaOperacion)
	 */
	public int guardarFinalizar(CjAgenciaOperacion oAgenciaOperacion) {
		int result = 0;
		try {
			StringBuffer sentenciaSQL = new StringBuffer();
			sentenciaSQL.append("UPDATE "+ SATParameterFactory.getDBNameScheme() + ".cj_agencia_operacion ");
			sentenciaSQL
					.append("SET fecha_cierre = ?,estado = ?, usuario_id = ?, terminal = ? ");
			sentenciaSQL.append("WHERE agencia_id = ? AND ");
			sentenciaSQL.append(" agencia_operacion_id = ? ");

			PreparedStatement pst = connect().prepareStatement(
					sentenciaSQL.toString());

			pst.setTimestamp(1, oAgenciaOperacion.getFechaCierre());
			pst.setString(2, oAgenciaOperacion.getEstado());// pst.setString(2,
			//pst.setInt(3, oAgenciaOperacion.getUsuarioId());
			//pst.setString(4, oAgenciaOperacion.getTerminal());
			//pst.setInt(5, oAgenciaOperacion.getAgenciaId());
			//pst.setInt(6, oAgenciaOperacion.getAgenciaOperacionId());

			result = pst.executeUpdate();

		} catch (Exception e) {
			result = 1;
			e.printStackTrace();

		}
		return result;
	}

	// Del Extorno
	public ArrayList<CjReciboEntity> ObtenerListaOperacion(String nroRecibo,
			int personaId, int tipoDocumentoId, String nroDocumento,
			Timestamp fechaInicio, Timestamp fechaFin) {

		CjReciboEntity item = null;

		ArrayList<CjReciboEntity> lista = new ArrayList<CjReciboEntity>();

		try {
			String sql = "stpCj_sel_Recibo ?,?,?,?,?,?";
			PreparedStatement pst = connect().prepareStatement(sql.toString());

			if (nroRecibo != "")
				pst.setString(1, nroRecibo);
			else
				pst.setNull(1, java.sql.Types.NULL);
			if (personaId > 0)
				pst.setInt(2, personaId);
			else
				pst.setNull(2, java.sql.Types.NULL);
			if (tipoDocumentoId > 0)
				pst.setInt(3, tipoDocumentoId);
			else
				pst.setNull(3, java.sql.Types.NULL);
			if (nroDocumento != "")
				pst.setString(4, nroDocumento);
			else
				pst.setNull(4, java.sql.Types.NULL);

			if (fechaInicio == null)
				pst.setNull(5, java.sql.Types.NULL);
			else
				pst.setTimestamp(5, fechaInicio);

			/*
			 * if(DateUtil.getFromDateTime(fechaInicio))pst.setNull(5,
			 * java.sql.Types.NULL); else pst.setTimestamp(5, fechaInicio);
			 */

			if (fechaFin == null)
				pst.setNull(6, java.sql.Types.NULL);
			else
				pst.setTimestamp(6, fechaFin);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				item = new CjReciboEntity();

				item.setReciboId(rs.getInt("recibo_id"));
				item.setNroRecibo(rs.getString("nro_recibo"));
				item.setPersonaId(rs.getInt("persona_id"));
				item.setNombrePersona(rs.getString("apellidos_nombres"));
				item.setTipoDocIdentidadDes(rs
						.getString("tipo_doc_identidad_des"));
				item.setNroDocIdentidad(rs.getString("nro_docu_identidad"));
				item.setFechaRecibo(rs.getTimestamp("fecha_recibo"));
				item.setMontoCobrado(rs.getBigDecimal("monto_cobrado"));
				lista.add(item);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	// CUADRE DE CAJA

	public ArrayList<CjCajaCuadreEntity> ObtenerOperacionesCuadre(int usuario_id) throws SisatException {

		CjCajaCuadreEntity item = null;

		ArrayList<CjCajaCuadreEntity> lista = new ArrayList<CjCajaCuadreEntity>();

		try {
			String sql = "sptCj_sel_ListaCuadre ?";
			PreparedStatement pst = connect().prepareStatement(sql.toString());
			pst.setInt(1, usuario_id);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {

				item = new CjCajaCuadreEntity();
				item.setAperturaId(rs.getInt("apertura_id"));
				item.setFormaPagoId(rs.getInt("forma_pago_id"));
				item.setFormaPagoDes(rs.getString("forma_pago_des"));
				item.setMonedaDes(rs.getString("moneda_des"));
				item.setTipoCambio(rs.getBigDecimal("tipo_cambio"));
				item.setMontoMoneda(rs.getBigDecimal("monto"));
				item.setMontoTotal(rs.getBigDecimal("monto_total"));
				lista.add(item);

			}
		} catch (Exception e) {
			throw new SisatException(e.getMessage(),e.getCause());
		}
		return lista;
	}

	/**
	 *  Guarda el cuadre de caja en la base de datos.
	 *  
	 * @param listaCuadre Lista del cuadre de caja.
	 * @return
	 */
	public boolean grabarCuadre(ArrayList<CjCajaCuadreEntity> listaCuadre) {
		try {
			// Insertando en el detalle
			for (CjCajaCuadreEntity oCajaCuadre : listaCuadre) {

				StringBuilder SQL = new StringBuilder();

				SQL.append("INSERT INTO "+ SATParameterFactory.getDBNameScheme() + ".cj_caja_cuadre (");
				SQL.append("cuadre_id,apertura_id,forma_pago_id,");
				SQL.append("monto_cuadre,usuario_id,estado,");
				SQL.append("fecha_registro,terminal, motivo_cuadre_id, tipo_cuadre_id)");
				SQL.append("VALUES(?,?,?,?,?,?,GETDATE(),?,?,?)");

				PreparedStatement pst = connect().prepareStatement(SQL.toString());

				int cajaCuadreId = obtenerCorrelativoTabla("cj_caja_cuadre");
				
				pst.setInt(1, cajaCuadreId);
				pst.setInt(2, oCajaCuadre.getAperturaId());
				pst.setInt(3, oCajaCuadre.getFormaPagoId());
				pst.setBigDecimal(4, oCajaCuadre.getMontoCuadre());
				pst.setInt(5, oCajaCuadre.getUsuarioId());
				pst.setString(6, oCajaCuadre.getEstado());
				pst.setString(7, oCajaCuadre.getTerminal());
				pst.setInt(8, oCajaCuadre.getMotivoCuadreId());
				pst.setInt(9, oCajaCuadre.getTipoCuadreId());
				pst.executeUpdate();
			}
			return true;
		} catch (Exception ex) {
			String msg = "No se ha podido registrar el cuadre de caja";
			System.out.println(msg + ex);
		}
		return false;
	}

	// Extorno
	public CjReciboEntity ObtenerDatosRecibo(int recibo_id) {

		CjReciboEntity oReciboPagoEntity = null;
		try {
			String sql = "sptCj_obtener_datos_recibo ?";
			PreparedStatement pst = connect().prepareStatement(sql.toString());

			pst.setInt(1, recibo_id);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				oReciboPagoEntity = new CjReciboEntity();
				oReciboPagoEntity.setReciboId(rs.getInt("recibo_id"));
				oReciboPagoEntity.setNroRecibo(rs.getString("nro_recibo"));
				oReciboPagoEntity.setTipoDocIdentidadDes(rs
						.getString("tipo_doc_identidad_des"));
				oReciboPagoEntity.setPersonaId(rs.getInt("persona_id"));
				oReciboPagoEntity.setNroDocIdentidad(rs
						.getString("nro_docu_identidad"));
				oReciboPagoEntity.setNombrePersona(rs.getString("persona_des"));
				oReciboPagoEntity.setFechaRecibo(rs
						.getTimestamp("fecha_recibo"));
				oReciboPagoEntity.setMontoCobrado(rs
						.getBigDecimal("monto_cobrado"));
				oReciboPagoEntity.setEstado(rs.getString("estado"));
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return oReciboPagoEntity;

	}

	public int ExtornarPago(int recibo_id, int usuario_id, String obs_extorno,
			String terminal) throws SQLException, SisatException {

		int resultado = 0;
		Connection cn = connect();
		try {
			cn.setAutoCommit(false); // ABRE LA TRANSACCION
			String sql = "sptCJ_ExtornarPago ?,?,?,?";

			// PreparedStatement pst =
			// connect().prepareStatement(sql.toString());
			CallableStatement pst = cn.prepareCall(sql.toString());
			pst.setInt(1, recibo_id);
			pst.setInt(2, usuario_id);
			pst.setString(3, obs_extorno);
			pst.setString(4, terminal);

			// ResultSet rs = pst.executeQuery();
			pst.execute();
			cn.commit();// CIERRA LA TRANSACCION

		} catch (Exception e) {
			// TODO: handle exception
			cn.rollback();// rollback
			e.printStackTrace();
		}
		return resultado;

	}

	// Obtener Datos de Recibo detalle EXTORNO

	public ArrayList<CjReciboDetalleEntity> ObtenerDatosReciboDetalle(
			int recibo_id) {

		ArrayList<CjReciboDetalleEntity> lista = new ArrayList<CjReciboDetalleEntity>();

		try {
			String sql = "stpCj_obtener_datos_recibo_detalle ?";
			PreparedStatement pst = connect().prepareStatement(sql.toString());

			pst.setInt(1, recibo_id);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				CjReciboDetalleEntity oCjReciboDetalle = new CjReciboDetalleEntity();
				oCjReciboDetalle
						.setFormaPago(rs.getString("forma_pago_des"));
				oCjReciboDetalle.setMoneda_des(rs.getString("moneda_des"));
				oCjReciboDetalle.setTipoCambio(rs.getBigDecimal("tipo_cambio"));
				oCjReciboDetalle.setMonto(rs.getBigDecimal("monto"));
				oCjReciboDetalle.setMontoTotalSoles(rs
						.getBigDecimal("monto_total_soles"));
				lista.add(oCjReciboDetalle);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return lista;
	}

	public ArrayList<CjReciboPagoEntity> ObtenerDatosReciboPago(int recibo_id) {

		ArrayList<CjReciboPagoEntity> lista = new ArrayList<CjReciboPagoEntity>();

		try {
			String sql = "stpCj_obtener_datos_recibo_pago ?";
			PreparedStatement pst = connect().prepareStatement(sql.toString());

			pst.setInt(1, recibo_id);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				CjReciboPagoEntity oCjReciboPago = new CjReciboPagoEntity();
				oCjReciboPago.setTributo(rs.getString("concepto_des"));
				oCjReciboPago.setCuota(rs.getString("cuota"));
				oCjReciboPago.setInsoluto(rs.getBigDecimal("insoluto"));
				oCjReciboPago.setReajuste(rs.getBigDecimal("reajuste"));
				oCjReciboPago.setInteres(rs.getBigDecimal("interes_mensual"));
				oCjReciboPago.setDerechoEmision(rs
						.getBigDecimal("derecho_emision"));
				oCjReciboPago.setTotal(rs.getBigDecimal("total"));
				lista.add(oCjReciboPago);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return lista;
	}

	// Motivo de extorno

	public List<CjMotivos> obtenerMotivoExtorno() {

		List<CjMotivos> lista = new ArrayList<CjMotivos>();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ");
			sql.append("Motivo_extorno_id as MotivoId,");
			sql.append("descripcion as Descripcion ");
			sql.append("from "+ SATParameterFactory.getDBNameScheme() + ".cj_Motivo_extorno");

			PreparedStatement pst = connect().prepareStatement(sql.toString());

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				CjMotivos objTM = new CjMotivos();
				objTM.setMotivo_extorno_id(rs.getInt("MotivoId"));
				objTM.setDescripcionExtorno(rs.getString("Descripcion"));
				lista.add(objTM);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	// Motivo de Cierre Cuadre

	public List<CjMotivos> obtenerMotivoCuadre() {

		List<CjMotivos> lista = new ArrayList<CjMotivos>();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ");
			sql.append("Motivo_cuadre_id as MotivoCuadreId,");
			sql.append("descripcion as Descripcion ");
			sql.append("from "+ SATParameterFactory.getDBNameScheme() + ".cj_Motivo_cuadre");

			PreparedStatement pst = connect().prepareStatement(sql.toString());

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				CjMotivos objTM = new CjMotivos();
				objTM.setMotivo_cuadre_id(rs.getInt("MotivoCuadreId"));
				objTM.setDescripcionCierreCaja(rs.getString("Descripcion"));
				lista.add(objTM);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	
	public Integer temporal() {

		Integer reciboID=0;

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select  recibo_id  from tempo_recibo_caja ");
			

			PreparedStatement pst = connect().prepareStatement(sql.toString());

			ResultSet rs = pst.executeQuery();
			
			
			while (rs.next()) {
				
				reciboID=rs.getInt("recibo_id");
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reciboID;
	}
	
	
	/**
	 * Obtiene los datos principales de un recibo de pago
	 * 
	 * @param reciboId Identificador de recibo
	 * @return Datos principales de recibo
	 */
	public ReciboPagoDTO obtenerDatosReciboPago(int reciboId){
		
		ReciboPagoDTO item = null;
		try {
			
			/** Store procedure para la obtencion de datos del recibo de pago*/
			String SQL = "sptCJ_sel_ReciboCaja ?";
			
			/** Consulta para la obtencion del subtotal sin descuentos u otro beneficio*/
			StringBuffer sbQuery = new StringBuffer("select (SUM(p.monto_pago) + SUM(p.monto_beneficio)) as subTotal ");
			sbQuery.append("from ");
			sbQuery.append(SATParameterFactory.getDBNameScheme().concat(".cj_pago p where p.recibo_id = ?"));			
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, reciboId);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				item = new ReciboPagoDTO();
				item.setReciboId(rs.getInt("recibo_id"));
				item.setPersonaId(rs.getInt("persona_id"));
				item.setNroRecibo(rs.getString("nro_recibo"));
				item.setPersonaDes(rs.getString("persona_des"));
				item.setFechaRecibo(new Date(rs.getTimestamp("fecha_recibo").getTime()));
				item.setMontoACobrar(rs.getBigDecimal("monto_a_cobrar"));
				item.setMontoDescuento(rs.getBigDecimal("monto_descuento"));
				item.setMontoVuelto(rs.getBigDecimal("monto_vuelto"));
				item.setMontoCobrado(rs.getBigDecimal("monto_cobrado"));
				item.setDireccion(rs.getString("direccion"));
				item.setCajeroDes(rs.getString("cajero_des"));
				item.setPagadoPor(rs.getString("pagado_por"));
				item.setMontoLetras(rs.getString("monto_letra"));
				item.setEstaExtornado(rs.getString("esta_extornado").equals("1")?Boolean.TRUE:Boolean.FALSE);
				item.setUsuarioIdExtorno(rs.getInt("usuario_id_extorno"));
				item.setUsuarioAsStringExtorno(rs.getString("usuario_id_as_string_extorno"));
				item.setObservacionExtorno(rs.getString("obs_extorno"));
				item.setFechaExtorno((rs.getTimestamp("fecha_extorno")== null)?null:new Date(rs.getTimestamp("fecha_extorno").getTime()));
				item.setMensaje(rs.getString("mensaje"));
				
				break;
			}			
			
			PreparedStatement pst2 = connect().prepareStatement(sbQuery.toString());
			pst2.setInt(1, reciboId);

			ResultSet rs2 = pst2.executeQuery();
			while (rs2.next()) {				
				item.setMontoSubTotal(rs2.getBigDecimal(1));
				break;
			}			
			
		} catch (Exception ex) {
			String msg = "No se ha podido obtener datos del recibo";
			System.out.println(msg + ex);
		}		
		
		return item;
	}
	
	/**
	 * Obtiene los datos principales de un recibo de pago tupa
	 * 
	 * @param reciboId Identificador de recibo
	 * @return Datos principales de recibo
	 */
	public ReciboPagoDTO obtenerDatosReciboPagoTupa(int reciboId){
		try {
			StringBuilder SQL = new StringBuilder("SELECT r.recibo_id, r.nro_recibo, r.fecha_recibo, r.monto_a_cobrar,");
			SQL.append("r.monto_descuento, r.monto_vuelto, r.monto_cobrado, r.monto_letra,");
			SQL.append("cajero_des = (SELECT TOP 1 nombre_usuario FROM "+ SATParameterFactory.getDBNameScheme() + ".sg_usuario WHERE usuario_id = r.usuario_id),");
			SQL.append("pagado_por = r.referencia ");
			SQL.append(", case when r.usuario_id_extorno is not null then '1' else '0' end as esta_extornado ");
			SQL.append(", case when r.usuario_id_extorno is not null then (SELECT nombre_usuario FROM dbo.sg_usuario WHERE usuario_id = r.usuario_id_extorno) else null end as usuario_id_as_string_extorno "); 
			SQL.append(", r.usuario_id_extorno ");
			SQL.append(", r.fecha_extorno ");
			SQL.append(", r.obs_extorno ");
			SQL.append("FROM "+ SATParameterFactory.getDBNameScheme() + ".cj_recibo_pago r WHERE r.recibo_id = " + reciboId);
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				ReciboPagoDTO item = new ReciboPagoDTO();
				item.setReciboId(rs.getInt("recibo_id"));
				item.setNroRecibo(rs.getString("nro_recibo"));
				item.setFechaRecibo(new Date(rs.getTimestamp("fecha_recibo").getTime()));
				item.setMontoACobrar(rs.getBigDecimal("monto_a_cobrar"));
				item.setMontoDescuento(rs.getBigDecimal("monto_descuento"));
				item.setMontoVuelto(rs.getBigDecimal("monto_vuelto"));
				item.setMontoCobrado(rs.getBigDecimal("monto_cobrado"));
				item.setMontoLetras(rs.getString("monto_letra"));
				item.setCajeroDes(rs.getString("cajero_des"));
				item.setPagadoPor(rs.getString("pagado_por"));
				item.setEstaExtornado(rs.getString("esta_extornado").equals("1")?Boolean.TRUE:Boolean.FALSE);
				item.setUsuarioIdExtorno(rs.getInt("usuario_id_extorno"));
				item.setUsuarioAsStringExtorno(rs.getString("usuario_id_as_string_extorno"));
				item.setObservacionExtorno(rs.getString("obs_extorno"));
				item.setFechaExtorno((rs.getTimestamp("fecha_extorno")== null)?null:new Date(rs.getTimestamp("fecha_extorno").getTime()));
				return item;
			}
		} catch (Exception ex) {
			String msg = "No se ha podido obtener datos del recibo";
			System.out.println(msg + ex);
		}
		return null;
	}

	/**
	 * Obtiene las deudas para una papeleta especÃ­fica y los guarda en una tabla temporal.
	 * 
	 * @param cajeroId Identificador de usuario de sistema con perfil de cajero.
	 * @param personaId Contribuyente con deuda de papeleta.
	 * @param papeletaId Identificador de la papeleta.
	 * @param fechaConsulta Fecha de consulta.
	 */
	public void obtenerDeudaPapeleta(int cajeroId, int personaId, int papeletaId, Timestamp fechaConsulta) {
		try {
			String sql = "stpCj_sel_BuscarDeudaPapeleta ?,?,?,?";
			PreparedStatement pst = connect().prepareStatement(sql);
			pst.setInt(1, cajeroId);
			pst.setInt(2, personaId);
			pst.setInt(3, papeletaId);
			pst.setTimestamp(4, fechaConsulta);
			pst.executeUpdate();
		} catch (Exception ex) {
			String msg = "No se ha podido obtener las deudas de la papeleta " + papeletaId;
			System.out.println(msg + ex);
		}
	}
	
	/**
	 * Obtiene la(s) forma(s) de pago de un recibo.
	 * 
	 * @param reciboId Identificador de recibo.
	 * @return
	 */
	public List<ReciboPagoFormaPagoDTO> getFormasPagoRecibo(int reciboId){
		List<ReciboPagoFormaPagoDTO> lista = new ArrayList<ReciboPagoFormaPagoDTO>();
		try {
			String SQL = "sptCJ_sel_ReciboDetalleCaja ?";
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, reciboId);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				ReciboPagoFormaPagoDTO item = new ReciboPagoFormaPagoDTO();
				item.setReciboId(rs.getInt("recibo_id"));
				item.setReciboDetalleId(rs.getInt("recibo_detalle_id"));
				item.setFormaPagoDes(rs.getString("forma_pago_des"));
				item.setMonedaDes(rs.getString("moneda_des"));
				item.setBancoDes(rs.getString("banco_des"));
				item.setTarjetaDes(rs.getString("tarjeta_des"));
				item.setMonto(rs.getBigDecimal("monto"));
				item.setMontoTotalSoles(rs.getBigDecimal("monto_total_soles"));
				item.setNroCheque(rs.getString("nro_cheque"));
				item.setObservacion(rs.getString("observacion"));
				lista.add(item);
			}
		} catch (Exception ex) {
			String msg = "No se ha podido recuperar la(s) forma(s) de pago del recibo " + reciboId;
			System.out.println(msg + ex);
		}
		return lista;
	}
	
	/**
	 * Obtiene la lista de agencias que tiene un usuario dependiendo si es cajero o supervisor.
	 * 
	 * @param usuarioId Identificador de usuario.
	 * @param tipoRol Supervisor (S) o Cajero (C)
	 * @return Lista de agencias.
	 */
	public List<GenericDTO> obtenerAgenciasUsuario(int usuarioId, String tipoRol){
		List<GenericDTO> lista = new ArrayList<GenericDTO>();
		try {
			StringBuilder SQL = new StringBuilder("SELECT au.agencia_id, a.nombre_agencia FROM "+ SATParameterFactory.getDBNameScheme() + ".cj_agencia_usuario au ");
			SQL.append("INNER JOIN "+ SATParameterFactory.getDBNameScheme() + ".cj_agencia a ON a.agencia_id = au.agencia_id ");
			SQL.append("WHERE au.usuario_id=" + usuarioId + " AND au.tipo_rol='" + tipoRol + "' ");
			SQL.append("AND au.estado='" + Constante.ESTADO_ACTIVO + "' AND a.estado = '" + Constante.ESTADO_ACTIVO + "'");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				GenericDTO item = new GenericDTO();
				item.setId(rs.getInt("agencia_id"));
				item.setDescripcion(rs.getString("nombre_agencia"));
				lista.add(item);
			}
		} catch (Exception ex) {
			String msg = "No se ha podido recuperar las agencias del usuario";
			System.out.println(msg + ex);
		}
		return lista;
	}
	
	/**
	 * Obtiene la agencia y el cajero (operaciÃ³n para el suario ingresado).
	 * 
	 * @param usuarioId Usuario con perfil de cajero.
	 * @param terminal Terminal de donde se accede al sistema.
	 * @return Datos de agencia operaciÃ³n.
	 */
	public AgenciaOperacionDTO obtenerAgenciaOperacion(int usuarioId, String terminal){
		try{
			String SQL = "sptCj_obtener_AgenciaOperacionCajero ?,?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, usuarioId);
			pst.setString(2, terminal);
			
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				AgenciaOperacionDTO obj = new AgenciaOperacionDTO();
				obj.setAgenciaId(rs.getInt("agencia_id"));
				obj.setAgenciaOperacionId(rs.getInt("agencia_operacion_id"));
				obj.setNombreAgencia(rs.getString("nombre_agencia"));
				obj.setUsuarioDes(rs.getString("usuario_des"));
				return obj;
			}
		}catch(Exception ex){
			String msg = "No ha sido posible recuperar datos de agencia operaciÃ³n";
			System.out.println(msg + ex);
		}
		return null;
	}

	// para lista de Operaciones del caja cuadre

	// public abstract ArrayList<CjReciboPagoEntity>
	// obtenerListadoOperaciones(int cajero_id, Timestamp fecha_inicio,
	// Timestamp fecha_fin){

	
	
	
	public ArrayList<consultaRecibojava> consultarRecibo(String nro_recibo ,int periodo ) throws SisatException	
	{
		
		ArrayList<consultaRecibojava> listaOperacion = new ArrayList<consultaRecibojava>();
		
		try {
			String sql = "sp_pagosNumRecibo ?, ?";
			PreparedStatement pst = connect().prepareStatement(sql.toString());

			pst.setString(1, nro_recibo);
			pst.setInt(2, periodo);			
			

			ResultSet rs = pst.executeQuery();
			consultaRecibojava item;
			
			while (rs.next()) {
				
				item = new consultaRecibojava();				
			
				
				item.setRecibo_id(rs.getInt("recibo_id"));
				item.setTipo_recibo(rs.getString("tipo_recibo"));
				item.setFlag_extorno(rs.getString("flag_extorno"));				
				item.setNro_recibo(rs.getString("nro_recibo"));
				item.setFecha_pago((new Date(rs.getTimestamp("fecha_pago").getTime())));				
				item.setDatos(rs.getString("datos"));
				item.setTributo(rs.getString("tributo"));
				item.setCuotas(rs.getString("cuota"));
				item.setPeriodo(rs.getInt("periodo"));				
				item.setPersona_id(rs.getInt("persona_id"));
				item.setNro_papeleta(rs.getString("nro_papeleta"));
				item.setMonto_deuda(rs.getBigDecimal("monto_deuda"));
				item.setMonto_descuento(rs.getBigDecimal("monto_descuento"));
				item.setMonto_pagado(rs.getBigDecimal("monto_pagado"));
				item.setTotal_deuda(rs.getBigDecimal("total_deuda"));
				item.setTotal_descuento(rs.getBigDecimal("total_descuento"));
				item.setTotal_pagado(rs.getBigDecimal("total_pagado"));			
				
				
				listaOperacion.add(item);
			}

		} catch (Exception e) {
			
			throw new SisatException(e.getMessage(),e.getCause());
		}
		
		return listaOperacion;
		
	}
	
	
	public ArrayList<CjReciboPagoEntity> obtenerListadoOperaciones(int cajero_id, Date fecha_inicio, Date fecha_fin) throws SisatException {

		ArrayList<CjReciboPagoEntity> listaOperacion = new ArrayList<CjReciboPagoEntity>();
		try {
			String sql = "stp_cj_obtener_lista_operaciones ?, ?, ?";
			PreparedStatement pst = connect().prepareStatement(sql.toString());

			pst.setInt(1, cajero_id);
			pst.setTimestamp(2, new Timestamp(fecha_inicio.getTime()));
			pst.setTimestamp(3, new Timestamp(fecha_fin.getTime()));

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				CjReciboPagoEntity item = new CjReciboPagoEntity();
				item.setReciboId(rs.getInt("recibo_id"));
				item.setNroRecibo(rs.getString("nro_recibo"));
				item.setFechaRecibo(new Date(rs.getTimestamp("fecha_recibo").getTime()));
				item.setTipoOperacion(rs.getString("tipo_operacion"));
				item.setMontoCobrado(rs.getBigDecimal("monto_cobrado"));				
				item.setTipoRecibo(rs.getString("tipo_recibo"));
				item.setEstado(rs.getString("estado"));
				item.setEsExtornable(rs.getBoolean("es_extornable"));
				item.setNombreUsuario(rs.getString("nombre_usuario"));
				listaOperacion.add(item);
			}

		} catch (Exception e) {
			
			throw new SisatException(e.getMessage(),e.getCause());
		}
		return listaOperacion;
	}
	
	/**
	 * Obtiene datos de agencia usuario que es cajero activo.
	 * 
	 * @param usuarioId Identificador de usuario.
	 * @return Datos de agencia usuario.
	 */
	public AgenciaUsuarioDTO getAgenUsuarioCajero(int usuarioId){
		try{
			StringBuilder SQL = new StringBuilder("SELECT au.agencia_usuario_id,au.usuario_id,au.agencia_id,");
			SQL.append("au.tipo_rol,au.fecha_inicio,au.fecha_fin,au.ip_asignada,au.estado ");
			SQL.append("FROM "+ SATParameterFactory.getDBNameScheme() + ".cj_agencia_usuario au ");
			SQL.append("WHERE au.usuario_id=" + usuarioId + " AND au.tipo_rol='" + Constante.TIPO_ROL_CAJERO + "' AND au.estado = '" + Constante.ESTADO_ACTIVO + "'");
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			if(rs.next()){
				AgenciaUsuarioDTO au = new AgenciaUsuarioDTO();
				au.setAgenciaUsuarioId(rs.getInt("agencia_usuario_id"));
				au.setUsuarioId(rs.getInt("usuario_id"));
				au.setAgenciaId(rs.getInt("agencia_id"));
				au.setTipoRol(rs.getString("tipo_rol"));
				au.setFechaInicio(rs.getDate("fecha_inicio"));
				Object obj = rs.getDate("fecha_fin");
				if(obj!=null){
					au.setFechaFin(rs.getDate("fecha_fin"));
				}
				au.setIpAsignada(rs.getString("ip_asignada"));
				au.setEstado(rs.getString("estado"));
				return au;
			}
		}catch(Exception ex){
			String msg = "No se ha podido recuperar la agencia del usuario";
			System.out.println(msg + ex);
		}
		return null;
	}

	/**
	 * Permite saber si una agencia estÃ¡ aperturada o no.
	 * 
	 * @param agenciaId Identificador de la agencia.
	 * @return Verdadero o falso, dependiendo de si la agencia estÃ¡ aperturada.
	 */
	public boolean isAgenciaAperturada(int agenciaId) {
		try {
			StringBuilder SQL = new StringBuilder("SELECT ao.estado FROM "+ SATParameterFactory.getDBNameScheme() + ".cj_agencia_operacion ao ");
			SQL.append("WHERE ao.agencia_id = " + agenciaId + " AND ao.estado='" + Constante.ESTADO_ACTIVO + "' AND ao.fecha_cierre IS NULL");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception ex) {
			String msg = "Ha ocurrido un error";
			System.out.println(msg + ex);
		}
		return false;
	}

	/**
	 * Obtiene datos que permiten saber si la caja de un usuario esta aperturada o no.
	 * 
	 * @param usuarioId Identificador de usuario.
	 * @param agenciaId Identificador de agencia.
	 * @return Datos de caja aperturada.
	 */
	public CjCajaApertura getCajaAperturada(int usuarioId, int agenciaId) {
		try {
			StringBuilder SQL = new StringBuilder("SELECT ca.apertura_id,ca.agencia_id,ca.agencia_operacion_id,ca.fecha_apertura,ca.estado ");
			SQL.append("FROM "+ SATParameterFactory.getDBNameScheme() + ".cj_caja_apertura ca ");
			SQL.append("WHERE ca.usuario_id=" + usuarioId + " AND ca.agencia_id=" + agenciaId);
			SQL.append(" AND ca.estado='" + Constante.ESTADO_ACTIVO + "' AND ca.fecha_cierre IS NULL");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			
			if (rs.next()) {
				CjCajaApertura ca = new CjCajaApertura();
				ca.setAperturaId(rs.getInt("apertura_id"));
				ca.setAgenciaId(rs.getInt("agencia_id"));
				ca.setAgenciaOperacionId(rs.getInt("agencia_operacion_id"));
				ca.setFechaApertura(rs.getTimestamp("fecha_apertura"));
				ca.setEstado(rs.getString("estado"));
				return ca;
			}
		} catch (Exception ex) {
			String msg = "Ha ocurrido un error";
			System.out.println(msg + ex);
		}
		return null;
	}

	/*
	 * public ArrayList<CjReciboPagoEntity> obtenerListadoOperaciones( int
	 * cajero_id, Timestamp fecha_inicio, Timestamp fecha_fin) {
	 * 
	 * ArrayList<CjReciboPagoEntity> listaOperacion = new
	 * ArrayList<CjReciboPagoEntity>(); try { String sql =
	 * "sptCj_sel_ListaOperaciones ?,?,?"; PreparedStatement pst =
	 * connect().prepareStatement(sql.toString());
	 * 
	 * pst.setInt(1, cajero_id); pst.setTimestamp(2, fecha_inicio);
	 * pst.setTimestamp(3, fecha_fin);
	 * 
	 * ResultSet rs = pst.executeQuery(); while (rs.next()) { CjReciboPagoEntity
	 * item = new CjReciboPagoEntity();
	 * item.setNroRecibo(rs.getString("nro_recibo"));
	 * item.setFechaRecibo(rs.getTimestamp("fecha_recibo"));
	 * item.setMontoCobrado(rs.getBigDecimal("monto_cobrado"));
	 * item.setTipoOperacion(rs.getString("tipo_operacion"));
	 * listaOperacion.add(item); }
	 * 
	 * } catch (Exception e) { // TODO: handle exception e.printStackTrace(); }
	 * return listaOperacion;
	 * 
	 * }
	 */
	public ArrayList<CjPartidaEntity> ObtenerReportePartidaDiaria(
			int cajero_id, Date fecha_inicio, Date fecha_fin) {

		ArrayList<CjPartidaEntity> listaPartidaDiaria = new ArrayList<CjPartidaEntity>();

		try {
			String sql = "stpCj_sel_ReporteDiario ?,?,?";
			PreparedStatement pst = connect().prepareStatement(sql.toString());

			pst.setInt(1, cajero_id);
			pst.setDate(2, new java.sql.Date(fecha_inicio.getTime()));
			pst.setDate(3, new java.sql.Date(fecha_fin.getTime()));
			
			
			
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {// ***
				CjPartidaEntity item = new CjPartidaEntity();
				item.setNroPartida(rs.getString("nro_partida"));
				item.setDescripcion(rs.getString("descripcion"));
				item.setPeriodo(rs.getString("periodo"));
				item.setMonto(rs.getBigDecimal("monto"));
				item.setDescuento(rs.getBigDecimal("descuento"));
				item.setMontoNeto(rs.getBigDecimal("monto_neto"));
				listaPartidaDiaria.add(item);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return listaPartidaDiaria;
	}

	/**
	 * Guarda las deudas filtradas a una tabla temporal
	 * 
	 * @param lstDeudas
	 *            Lista de deudas
	 * @return verdadero o false segun el resultado de la acciÃ³n
	 */
	public boolean guardarTmpDeuda(List<DeudaDTO> lstDeudas) {
		try {
			if (lstDeudas == null) {
				return false;
			}

			String delete = "DELETE FROM "+ SATParameterFactory.getDBNameScheme() + ".cj_tmp_deuda WHERE agencia_usuario_id = "	+ lstDeudas.get(0).getCajeroId() + " AND persona_id = "	+ lstDeudas.get(0).getPersonaId();
			PreparedStatement pst = connect().prepareStatement(delete);
			pst.executeUpdate();

			for (DeudaDTO de : lstDeudas) {
				StringBuilder SQL = new StringBuilder("INSERT INTO "+ SATParameterFactory.getDBNameScheme() + ".cj_tmp_deuda(");
				SQL.append("agencia_usuario_id,persona_id,deuda_id,subconcepto_id,subconcepto,anyo,observacion,cuota,insoluto,reajuste,derecho_emision,");
				SQL.append("interes_capitalizado,interes_simple,interes,sub_total,determinacion_id,fecha_vencimiento,tasa_descuento,descuento) ");
				SQL.append("VALUES(");
				SQL.append(de.getCajeroId() + "," + de.getPersonaId() + ","	+ de.getDeudaId() + "," + de.getSubconceptoId() + ",'"	+ de.getSubconcepto() + "',");
				SQL.append(de.getAnioDeuda() + ",NULL," + de.getNumCuota() + "," + de.getInsoluto() + "," + de.getReajuste() + "," + de.getDerechoEmi() + ",");
				SQL.append(de.getInteresCapi() + "," + de.getInteresSimp() + "," + de.getInteres() + "," + de.getTotalDeuda() + "," + de.getDeterminacionId());
				if (de.getFechaVencim() == null) {
					SQL.append(",NULL,");
				} else {
					SQL.append(",CONVERT(DATETIME,'" + DateUtil.dateToSqlTimestamp(de.getFechaVencim()) + "',121),");
				}
				SQL.append(de.getTasaDcto() + "," + de.getDescuento() + ")");
				PreparedStatement pst2 = connect().prepareStatement(SQL.toString());
				pst2.executeUpdate();
			}
			return true;
		} catch (Exception ex) {
			String msg = "No se ha podido insertar deudas a la tabla temporal";
			System.out.println(msg + ex);
		}
		return false;
	}
	
	/**
	 * Elimina las deudas temporales de un cajero cuando Ã©ste deja la aplicaciÃ³n.
	 * @param cajeroId Identificador del cajero (usuario).
	 * @return
	 */
	public boolean eliminarTmpDeuda(int cajeroId){
		try{
			String SQL = "DELETE FROM "+ SATParameterFactory.getDBNameScheme() + ".cj_tmp_deuda WHERE agencia_usuario_id = " + cajeroId;
			PreparedStatement pst = connect().prepareStatement(SQL);
			pst.executeUpdate();
			String SQL2 = "DELETE FROM "+ SATParameterFactory.getDBNameScheme() + ".cj_tmp_deuda_condscto WHERE agencia_usuario_id = " + cajeroId;
			PreparedStatement pst2 = connect().prepareStatement(SQL2);
			pst2.executeUpdate();
			return true;
		}catch(Exception ex){
			System.out.println("No se ha podido eliminar datos de la temporal de deuda");
		}
		return false;
	}

//	/**
//	 * Obtiene el detalle del recibo de pago para los casos de impuestos y arbitrios.
//	 * 
//	 * @param reciboId PK. del recibo de pago.
//	 * @return Lista con el detalle.
//	 */
//	public List<ReciboPagoDetallePieDTO> searchReciboDetalle(int reciboId) {
//		List<ReciboPagoDetallePieDTO> lista = new ArrayList<ReciboPagoDetallePieDTO>();
//		try {
//			StringBuilder SQL = new StringBuilder();
//			SQL.append("SELECT DISTINCT d.concepto_id, subcon = CASE WHEN d.concepto_id = 3 AND d.subconcepto_id IN(30,31) THEN('30,31') ELSE CAST(d.subconcepto_id AS VARCHAR) END,");
//			SQL.append("referencia_id = ");
//			SQL.append("CASE d.concepto_id ");
//			SQL.append("WHEN 1 THEN NULL ");
//			SQL.append("WHEN 2 THEN (SELECT ve.vehiculo_id FROM "+ SATParameterFactory.getDBNameScheme() + ".rv_djvehicular djv INNER JOIN "+ SATParameterFactory.getDBNameScheme() + ".rv_vehiculo ve ON ve.vehiculo_id = djv.vehiculo_id WHERE djv.djvehicular_id = dt.djreferencia_id) ");
//			SQL.append("WHEN 3 THEN (SELECT p.predio_id FROM "+ SATParameterFactory.getDBNameScheme() + ".rp_djpredial djp INNER JOIN "+ SATParameterFactory.getDBNameScheme() + ".mp_predio p ON p.predio_id = djp.predio_id WHERE djp.dj_id = dt.djreferencia_id) ");
//			SQL.append("WHEN 4 THEN (SELECT pap.papeleta_id FROM "+ SATParameterFactory.getDBNameScheme() + ".pa_papeleta pap WHERE pap.papeleta_id = dt.djreferencia_id) ");
//			SQL.append("WHEN 7 THEN (SELECT p.predio_id FROM "+ SATParameterFactory.getDBNameScheme() + ".rp_djpredial djp INNER JOIN "+ SATParameterFactory.getDBNameScheme() + ".mp_predio p ON p.predio_id = djp.predio_id WHERE djp.dj_id = dt.djreferencia_id) ");
//			SQL.append("ELSE NULL ");
//			SQL.append("END,");
//			SQL.append("referencia = ");
//			SQL.append("CASE d.concepto_id ");
//			SQL.append("WHEN 1 THEN NULL ");
//			SQL.append("WHEN 2 THEN 'Placa:' + (SELECT ve.placa FROM "+ SATParameterFactory.getDBNameScheme() + ".rv_djvehicular djv INNER JOIN "+ SATParameterFactory.getDBNameScheme() + ".rv_vehiculo ve ON ve.vehiculo_id = djv.vehiculo_id WHERE djv.djvehicular_id = dt.djreferencia_id) ");
//			SQL.append("WHEN 3 THEN 'Predio:' + (SELECT p.codigo_predio FROM "+ SATParameterFactory.getDBNameScheme() + ".rp_djpredial djp INNER JOIN "+ SATParameterFactory.getDBNameScheme() + ".mp_predio p ON p.predio_id = djp.predio_id WHERE djp.dj_id = dt.djreferencia_id) ");
//			SQL.append("WHEN 4 THEN ");
//			SQL.append("'Papeleta/Placa/Resolucion:' + (SELECT pap.nro_papeleta + '/' + ISNULL(pap.placa,'') FROM "+ SATParameterFactory.getDBNameScheme() + ".pa_papeleta pap WHERE pap.papeleta_id = dt.djreferencia_id) + '/' + ");
//			SQL.append("ISNULL((SELECT TOP 1 ac.nro_acto FROM "+ SATParameterFactory.getDBNameScheme() + ".cc_acto_deuda ad INNER JOIN "+ SATParameterFactory.getDBNameScheme() + ".cc_acto ac ON ac.acto_id = ad.acto_id WHERE ad.deuda_id = d.deuda_id AND ac.estado='1' AND ac.tipo_acto_id = 8),'') ");
//			SQL.append("WHEN 5 THEN 'REC:' + (SELECT re.nro_rec FROM "+ SATParameterFactory.getDBNameScheme() + ".cc_rec re WHERE re.rec_id = d.nro_documento_id)");
//			SQL.append("WHEN 6 THEN 'REC:' + (SELECT re.nro_rec FROM "+ SATParameterFactory.getDBNameScheme() + ".cc_rec re WHERE re.rec_id = d.nro_documento_id)");
//			SQL.append("WHEN 7 THEN 'Predio:' + + (SELECT p.codigo_predio FROM "+ SATParameterFactory.getDBNameScheme() + ".rp_djpredial djp INNER JOIN "+ SATParameterFactory.getDBNameScheme() + ".mp_predio p ON p.predio_id = djp.predio_id WHERE djp.dj_id = dt.djreferencia_id) ");
//			SQL.append("WHEN 11 THEN 'Referencia:' ");
//			SQL.append("WHEN 12 THEN 'Nro Valor:' + (SELECT ac.nro_acto FROM "+ SATParameterFactory.getDBNameScheme() + ".cc_acto_deuda ad INNER JOIN "+ SATParameterFactory.getDBNameScheme() + ".cc_acto ac ON ac.acto_id = ad.acto_id WHERE ad.deuda_id = d.deuda_id) ");
//			SQL.append("ELSE NULL ");
//			SQL.append("END FROM "+ SATParameterFactory.getDBNameScheme() + ".cj_pago p ");
//			SQL.append("INNER JOIN "+ SATParameterFactory.getDBNameScheme() + ".cd_deuda d ON d.deuda_id = p.deuda_id ");
//			SQL.append("INNER JOIN "+ SATParameterFactory.getDBNameScheme() + ".dt_determinacion dt ON dt.determinacion_id = d.determinacion_id ");
//			SQL.append("WHERE p.recibo_id=" + reciboId);
//			SQL.append(" ORDER BY d.concepto_id");
//
//			PreparedStatement pst = connect().prepareStatement(SQL.toString());
//			ResultSet rs = pst.executeQuery();
//			while(rs.next()) {
//				ReciboPagoDetallePieDTO item = new ReciboPagoDetallePieDTO();
//				item.setConceptoId(rs.getInt("concepto_id"));
//				item.setSubconceptosId(rs.getString("subcon"));
//				item.setReferenciaId(rs.getString("referencia_id"));
//				String refer = rs.getString("referencia");
//				if(refer != null){
//					item.setReferenciaLabel(refer.substring(0,refer.indexOf(':')));
//					item.setReferencia(refer.substring(refer.indexOf(':') + 1, refer.length()));
//				}else{
//					item.setReferenciaLabel(null);
//					item.setReferencia(null);
//				}
//				
//				StringBuilder SQLD = new StringBuilder();
//				if(item.getSubconceptosId().equals("20")){
//					SQLD.append("SELECT sc.descripcion_corta + '-' + CAST(anno_deuda AS VARCHAR) 'concepto',d.nro_cuota 'cuota', p.insoluto 'monto', p.reajuste, p.interes, ");
//					SQLD.append("p.derecho_emision 'emision', p.monto_pago 'total' FROM "+ SATParameterFactory.getDBNameScheme() + ".cj_pago p ");
//					SQLD.append("INNER JOIN "+ SATParameterFactory.getDBNameScheme() + ".cd_deuda d ON d.deuda_id = p.deuda_id ");
//					SQLD.append("INNER JOIN "+ SATParameterFactory.getDBNameScheme() + ".gn_subconcepto sc ON sc.subconcepto_id = d.subconcepto_id ");
//					SQLD.append("INNER JOIN "+ SATParameterFactory.getDBNameScheme() + ".dt_determinacion dt ON dt.determinacion_id = d.determinacion_id ");
//					SQLD.append("INNER JOIN "+ SATParameterFactory.getDBNameScheme() + ".rv_djvehicular djv ON djv.djvehicular_id = dt.djreferencia_id ");
//					SQLD.append("WHERE d.concepto_id = " + item.getConceptoId());
//					SQLD.append(" AND d.subconcepto_id IN(" + item.getSubconceptosId() + ") AND p.recibo_id = " + reciboId);
//					SQLD.append(" AND djv.vehiculo_id = " + item.getReferenciaId());
//					SQLD.append(" ORDER BY d.anno_deuda");
//				}else if(item.getSubconceptosId().equals("30,31")){
//					SQLD.append("SELECT 'LP-' + CAST(anno_deuda AS VARCHAR) 'concepto',d.nro_cuota 'cuota', SUM(p.insoluto) 'monto', SUM(p.reajuste) 'reajuste', SUM(p.interes) 'interes', ");
//					SQLD.append("SUM(p.derecho_emision) 'emision', SUM(p.monto_pago) 'total' FROM "+ SATParameterFactory.getDBNameScheme() + ".cj_pago p ");
//					SQLD.append("INNER JOIN "+ SATParameterFactory.getDBNameScheme() + ".cd_deuda d ON d.deuda_id = p.deuda_id ");
//					SQLD.append("INNER JOIN "+ SATParameterFactory.getDBNameScheme() + ".gn_subconcepto sc ON sc.subconcepto_id = d.subconcepto_id ");
//					SQLD.append("WHERE d.concepto_id = " + item.getConceptoId());
//					SQLD.append(" AND d.subconcepto_id IN(" + item.getSubconceptosId() + ") AND p.recibo_id = " + reciboId);
//					SQLD.append(" GROUP BY d.nro_cuota,d.anno_deuda ");
//					SQLD.append("ORDER BY d.anno_deuda");
//				}else{
//					SQLD.append("SELECT sc.descripcion_corta + '-' + CAST(anno_deuda AS VARCHAR) 'concepto',d.nro_cuota 'cuota', p.insoluto 'monto', p.reajuste, p.interes, ");
//					SQLD.append("p.derecho_emision 'emision', p.monto_pago 'total' FROM "+ SATParameterFactory.getDBNameScheme() + ".cj_pago p ");
//					SQLD.append("INNER JOIN "+ SATParameterFactory.getDBNameScheme() + ".cd_deuda d ON d.deuda_id = p.deuda_id ");
//					SQLD.append("INNER JOIN "+ SATParameterFactory.getDBNameScheme() + ".gn_subconcepto sc ON sc.subconcepto_id = d.subconcepto_id ");
//					SQLD.append("WHERE d.concepto_id = " + item.getConceptoId());
//					SQLD.append(" AND d.subconcepto_id IN(" + item.getSubconceptosId() + ") AND p.recibo_id = " + reciboId);
//					SQLD.append(" ORDER BY d.anno_deuda");
//				}
//				PreparedStatement pstd = connect().prepareStatement(SQLD.toString());
//				ResultSet rsd = pstd.executeQuery();
//				List<ReciboPagoDetalleDTO> listaDet = new ArrayList<ReciboPagoDetalleDTO>();
//				while(rsd.next()){
//					ReciboPagoDetalleDTO re = new ReciboPagoDetalleDTO();
//					re.setConcepto(rsd.getString("concepto"));
//					re.setCuota(rsd.getString("cuota"));
//					re.setMonto(rsd.getBigDecimal("monto"));
//					re.setReajuste(rsd.getBigDecimal("reajuste"));
//					re.setInteres(rsd.getBigDecimal("interes"));
//					re.setEmision(rsd.getBigDecimal("emision"));
//					re.setTotal(rsd.getBigDecimal("total"));
//					listaDet.add(re);
//				}
//				item.setListReciboPagoDetalle(listaDet);
//				lista.add(item);
//			}
//		} catch (Exception ex) {
//			String msg = "No se ha podido obtener los datos del recibo de pago";
//			System.out.println(msg + ex);
//		}
//		return lista;
//	}
//	
	public List<ReciboPagoDetallePieDTO> searchReciboDetalle(int reciboId) throws SisatException {
		List<ReciboPagoDetallePieDTO> lista = new ArrayList<ReciboPagoDetallePieDTO>();
		try {
			//Obteniendo la informacion de los conceptos cancelados ordenados mediante años y agrupados por determinacion
			StringBuilder SQL = new StringBuilder();			
			// cambiando a store la consulta de detalle del recibo
			SQL.append("exec stp_cj_obtener_detalle_recibo ?");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, reciboId);
			
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				
				ReciboPagoDetallePieDTO item = new ReciboPagoDetallePieDTO();
				item.setDeterminacionId(rs.getInt("determinacion_id"));
				item.setConceptoId(rs.getInt("concepto_id"));
				item.setSubconceptosId(rs.getString("subconcepto_id"));
				item.setReferenciaId(rs.getString("referencia_id"));
				String refer = rs.getString("referencia");
				if(refer != null){
					item.setReferenciaLabel(refer.substring(0,refer.indexOf(':')));
					item.setReferencia(refer.substring(refer.indexOf(':') + 1, refer.length()));
				}else{
					item.setReferenciaLabel(null);
					item.setReferencia(null);
				}
				String SQLD = "dbo.stp_cj_obtener_deudas_recibo ?,?";
				PreparedStatement pstd = connect().prepareStatement(SQLD.toString());
				pstd.setInt(1, item.getDeterminacionId());
				pstd.setInt(2, reciboId);
			
				ResultSet rsd=pstd.executeQuery();
				List<ReciboPagoDetalleDTO> listaDet = new ArrayList<ReciboPagoDetalleDTO>();
				
				StringBuffer sqlCuotas  = new StringBuffer();
				
				// consulta para la obtencion de cuotas para cada determinacion
				sqlCuotas.append("select d.nro_cuota ");
				sqlCuotas.append(" FROM "+ SATParameterFactory.getDBNameScheme() + ".cj_pago p ");
				sqlCuotas.append(" INNER JOIN "+ SATParameterFactory.getDBNameScheme() + ".cd_deuda d ON d.deuda_id = p.deuda_id ");				
				sqlCuotas.append(" WHERE "); 
				sqlCuotas.append(" d.determinacion_id =").append(item.getDeterminacionId());
				sqlCuotas.append(" AND p.recibo_id = " + reciboId);
				sqlCuotas.append(" AND d.anno_deuda = ?");
					
				
				while (rsd.next()) {
					ReciboPagoDetalleDTO re = new ReciboPagoDetalleDTO();
					int anhoDeuda = rsd.getInt("anno_deuda");
					re.setConcepto(rsd.getString("concepto"));

					PreparedStatement pstdCuotas = connect().prepareStatement(sqlCuotas.toString());
					pstdCuotas.setInt(1, anhoDeuda);
					ResultSet rsdCuotas = pstdCuotas.executeQuery();
					List<Integer> lstCuotas = new ArrayList<Integer>();
					while (rsdCuotas.next()) {
						lstCuotas.add(rsdCuotas.getInt(1));
					}
					re.setCuota(parseCuotas(lstCuotas.toArray(new Integer[lstCuotas.size()])));					
					re.setMonto(rsd.getBigDecimal("monto"));
					re.setReajuste(rsd.getBigDecimal("reajuste"));
					re.setInteres(rsd.getBigDecimal("interes"));
					re.setEmision(rsd.getBigDecimal("emision"));
					re.setTotal(rsd.getBigDecimal("total"));
					listaDet.add(re);
				}
				item.setListReciboPagoDetalle(listaDet);
				lista.add(item);
			}
		} catch (Exception ex) {
			String msg = "No se ha podido obtener los datos del recibo de pago";
			System.out.println(msg + ex);
			throw new SisatException(ex.getMessage());
		}
		return lista;
	}
	
	private  String parseCuotas(Integer [] cuotas){
		
		StringBuffer sb = new StringBuffer();
		Integer cuotaInferior;
		Integer cuotaSuperior;
		Integer pivot = cuotas[0];
		
		cuotaInferior = cuotaSuperior = cuotas[0];
		
		for(Integer s:cuotas){
			cuotaSuperior = s;
			if(!cuotaSuperior.equals(pivot)){
				if(cuotaInferior == pivot-1){
					sb.append(cuotaInferior).append(",");
				}else{
					sb.append(cuotaInferior+"-"+(pivot-1)).append(",");
				}
								
				pivot = cuotaSuperior = s;
				cuotaInferior = cuotaSuperior;
				pivot++;
			}else{				
				pivot++;
			}
			
		}
		if(cuotaInferior == cuotaSuperior){
			sb.append(cuotaInferior);
		}else{
			sb.append(cuotaInferior+"-"+cuotaSuperior);
		}		
		return sb.toString();
	}
	
	/**
	 * Obtiene el detalle del recibo de pago para los casos tupa.
	 * 
	 * @param reciboId PK. del recibo de pago.
	 * @return Lista con el detalle del recibo.
	 */
	public List<ReciboPagoDetalleDTO> searchReciboDetalleTupa(int reciboId) {
		List<ReciboPagoDetalleDTO> listaDetalle = new ArrayList<ReciboPagoDetalleDTO>();
		try {
			StringBuilder SQL = new StringBuilder("SELECT t.descripcion, p.monto_pago, p.cantidad FROM "+ SATParameterFactory.getDBNameScheme() + ".cj_pago_tupa p ");
			SQL.append("INNER JOIN "+ SATParameterFactory.getDBNameScheme() + ".cj_tupa t ON t.tupa_id = p.tupa_id ");
			SQL.append("WHERE p.recibo_id = " + reciboId);
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				ReciboPagoDetalleDTO item = new ReciboPagoDetalleDTO();

				item.setConcepto(rs.getString("descripcion"));				
				item.setTotal(rs.getBigDecimal("monto_pago"));
				item.setCantidad(rs.getInt("cantidad"));
				listaDetalle.add(item);
			}
		} catch (Exception ex) {
			String msg = "Ha ocurrido un error obteniendo los datos del recibo";
			System.out.println(msg + ex);
		}
		return listaDetalle;
	}
	
	/**
	 * Cambia el estado de agencia_usuario segun el usuario.
	 * 
	 * @param usuarioId PK. Usuario
	 * @param estado.
	 * @return
	 */
	public boolean cambiarEstadoAgenciaUsuario(int usuarioId, String estado){
		try{
			String SQL = "UPDATE "+ SATParameterFactory.getDBNameScheme() + ".cj_agencia_usuario SET estado = '" + estado + "' WHERE usuario_id = " + usuarioId;
			PreparedStatement pst = connect().prepareStatement(SQL);
			pst.executeUpdate();
			return true;
		}catch(Exception ex){
			String msg = "No se ha podido cambiar el estado de agencia_usuario";
			System.out.println(msg + ex);
		}
		return false;
	}
	
	/**
	 * Cambia el estado de agencia_suario a todos los registros con excepcion de los que tienen id en la lista de ids que se envÃ­a.
	 * 
	 * @param ids Conjunto de id, que no cambia de estado.
	 * @return
	 */
	public boolean cambiarEstadoAgenciaUsuario(int usuarioId, String ids, String estado){
		try{
			String SQL = "UPDATE "+ SATParameterFactory.getDBNameScheme() + ".cj_agencia_usuario SET estado = '" + estado + "' WHERE usuario_id = " + usuarioId + " AND agencia_usuario_id NOT IN(" + ids + ")";
			PreparedStatement pst = connect().prepareStatement(SQL);
			pst.executeUpdate();
			return true;
		}catch(Exception ex){
			String msg = "No se ha podido cambiar el estado de agencia_usuario";
			System.out.println(msg + ex);
		}
		return false;
	}
	
	/**
	 * Registra o actualiza una agencia_usuario.
	 * 
	 * @param agenciaUsuario Datos a ingresar.
	 * @return PK, de la agencia usuario modificada o registrada.
	 * @throws SisatException 
	 */
	public Integer registrarAgenciaUsuario(CjAgenciaUsuario agenciaUsuario) throws SisatException{
		try{
			// check if one active
			StringBuilder SQL = new StringBuilder("SELECT agencia_usuario_id, agencia_id, usuario_id, tipo_rol, fecha_fin, estado ");
			SQL.append("FROM "+ SATParameterFactory.getDBNameScheme() + ".cj_agencia_usuario WITH(NOLOCK)  WHERE agencia_id = " + agenciaUsuario.getCjAgencia().getAgenciaId());
			SQL.append(" AND usuario_id = " + agenciaUsuario.getSgUsuario().getUsuarioId());
			SQL.append(" AND tipo_rol='" + agenciaUsuario.getTipoRol() + "' ");
			SQL.append(" AND estado = '" + Constante.ESTADO_ACTIVO + "'");
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			if(rs.next()){
				int auId = rs.getInt("agencia_usuario_id");
				Date fechaFin = rs.getDate("fecha_fin");
				Date fechaFinNueva = agenciaUsuario.getFechaFin();
				String UPD = "";
				if(fechaFin != null){
					if(fechaFinNueva != null && fechaFin.getTime() < fechaFinNueva.getTime()){
						UPD = "UPDATE "+ SATParameterFactory.getDBNameScheme() + ".cj_agencia_usuario SET fecha_fin = '" + DateUtil.dateToSqlDate(fechaFinNueva) + "', ip_asignada='" + agenciaUsuario.getIpAsignada() + "' WHERE agencia_usuario_id = " + auId;
					}else{
						UPD = "UPDATE "+ SATParameterFactory.getDBNameScheme() + ".cj_agencia_usuario SET fecha_fin = NULL, ip_asignada='" + agenciaUsuario.getIpAsignada() + "' WHERE agencia_usuario_id = " + auId;
					}
				}else{
					if(fechaFinNueva == null){
						UPD = "UPDATE "+ SATParameterFactory.getDBNameScheme() + ".cj_agencia_usuario SET ip_asignada='" + agenciaUsuario.getIpAsignada() + "' WHERE agencia_usuario_id = " + auId;
					}else{
						UPD = "UPDATE "+ SATParameterFactory.getDBNameScheme() + ".cj_agencia_usuario SET fecha_fin = '" + DateUtil.dateToSqlDate(fechaFinNueva) + "', ip_asignada='" + agenciaUsuario.getIpAsignada() + "' WHERE agencia_usuario_id = " + auId;
					}
				}
				PreparedStatement pstUpd = connect().prepareStatement(UPD);
				pstUpd.executeUpdate();
				return auId;
			}else{
				Integer id = obtenerCorrelativoTabla("cj_agencia_usuario");
				
				StringBuilder QUERY = new StringBuilder("INSERT INTO "+ SATParameterFactory.getDBNameScheme() + ".cj_agencia_usuario(agencia_usuario_id,usuario_id,");
				QUERY.append("agencia_id,tipo_rol,fecha_inicio,fecha_fin,ip_asignada,estado,fecha_registro,terminal) VALUES(" + id);
				QUERY.append("," + agenciaUsuario.getSgUsuario().getUsuarioId() + "," + agenciaUsuario.getCjAgencia().getAgenciaId());
				QUERY.append(",'" + agenciaUsuario.getTipoRol() + "'");
				if(agenciaUsuario.getFechaInicio() == null){
					QUERY.append(",NULL");
				}else{
					QUERY.append(", ? ");
				}
				if(agenciaUsuario.getFechaFin() == null){
					QUERY.append(",NULL");
				}else{
					QUERY.append(", ? ");
				}
				QUERY.append(",'" + agenciaUsuario.getIpAsignada() + "','" + agenciaUsuario.getEstado() + "'");
				QUERY.append(",'" + agenciaUsuario.getFechaRegistro() + "','" + agenciaUsuario.getTerminal() + "')");
				
				PreparedStatement pstSave = connect().prepareStatement(QUERY.toString());
				if(agenciaUsuario.getFechaInicio() != null){
				pstSave.setDate(1,  new java.sql.Date(agenciaUsuario.getFechaInicio().getTime()) );
				}
				if(agenciaUsuario.getFechaFin() != null){
					pstSave.setDate(2, new java.sql.Date(agenciaUsuario.getFechaFin().getTime()));
				}
				pstSave.executeUpdate();
				
				return id;
			}
		}catch(Exception ex){
			String msg = "No ha sido posible guardar los datos de agencia usuario";
			throw new SisatException(msg,ex.getCause());
		}
	}
	
	/**
	 * Obtiene las agencias que tiene un suario.
	 * 
	 * @param usuarioId PK. Usuaroi
	 * @return Lista con datos de agencias asignadas a un usuario.
	 */
	public List<AgenciaUsuarioDTO> getAgenciaUsuario(int usuarioId){
		List<AgenciaUsuarioDTO> lista = new ArrayList<AgenciaUsuarioDTO>();
		try{
			StringBuilder SQL = new StringBuilder("select au.*, a.nombre_agencia, a.nombre_corto FROM "+ SATParameterFactory.getDBNameScheme() + ".cj_agencia_usuario au ");
			SQL.append("INNER JOIN "+ SATParameterFactory.getDBNameScheme() + ".cj_agencia a ON a.agencia_id = au.agencia_id ");
			SQL.append("WHERE au.usuario_id = " + usuarioId + " AND au.estado = '" + Constante.ESTADO_ACTIVO + "'");
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				AgenciaUsuarioDTO au = new AgenciaUsuarioDTO();
				au.setAgenciaUsuarioId(rs.getInt("agencia_usuario_id"));
				au.setUsuarioId(rs.getInt("usuario_id"));
				au.setAgenciaId(rs.getInt("agencia_id"));
				au.setTipoRol(rs.getString("tipo_rol"));
				au.setFechaInicio(rs.getDate("fecha_inicio"));
				au.setFechaFin(rs.getDate("fecha_fin"));
				au.setIpAsignada(rs.getString("ip_asignada"));
				au.setEstado(rs.getString("estado"));
				au.setFechaRegistro(rs.getDate("fecha_registro"));
				au.setTerminal(rs.getString("terminal"));
				au.setNombreAgencia("nombre_agencia");
				au.setNombreCortoAgencia("nombre_corto");
				lista.add(au);
			}
		}catch(Exception ex){
			String msg = "No se ha podido recuperar agencia_usuario";
			System.out.println(msg + ex);
		}
		return lista;
	}
	
	public List<CajeroDTO> obtenerCajeros(Date fechaInicio, Date fechaFin) throws SisatException {

		List<CajeroDTO> listCajeroDTOs = new ArrayList<CajeroDTO>();

		//formato de fecha en ingles debido a que el usuario de la base de datos se esta trabajando en ingles
		SimpleDateFormat sm = new SimpleDateFormat("MM/dd/yyyy");

		StringBuffer sb = new StringBuffer();

		sb.append("select u.nombre_usuario, ca.fecha_apertura,ca.fecha_cierre, ca.apertura_id, ca.estado,  ");
		sb.append("estadoDescripcion = ");
		sb.append("CASE ca.estado ");
		sb.append(" WHEN 1 THEN 'Abierto'");
		sb.append(" WHEN 2 THEN 'Cerrado'");
		sb.append(" END ");
		sb.append("from cj_caja_apertura ca inner join sg_usuario u on ca.usuario_id = u.usuario_id ");
		sb.append("where ca.fecha_apertura >= CONVERT(datetime,'");
		sb.append(sm.format(fechaInicio));
		sb.append(" 00:00:00') and ca.fecha_apertura < CONVERT(datetime,'");
		sb.append(sm.format(fechaFin));
		sb.append(" 23:59:59') and ca.estado in(1,2) order by ca.estado desc, u.nombre_usuario");

		PreparedStatement pst;
		try {
			pst = connect().prepareStatement(sb.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				CajeroDTO cajeroDTO = new CajeroDTO();
				cajeroDTO.setNombreUsuario(rs.getString(1));
				cajeroDTO.setFechaApertura(new Date((rs.getTimestamp(2)).getTime()));
				if(rs.getTimestamp(3) != null){
					cajeroDTO.setFechaCierre(new Date((rs.getTimestamp(3)).getTime()));
				}else{
					cajeroDTO.setFechaCierre(null);
				}
				cajeroDTO.setAperturaId(rs.getInt(4));
				cajeroDTO.setEstado(rs.getInt(5));
				cajeroDTO.setEstadoDescripcion(rs.getString(6));

				listCajeroDTOs.add(cajeroDTO);
			}

		} catch (SQLException e) {
			
			e.printStackTrace();
			throw new SisatException(e.getMessage());
		} catch (SisatException e) {
			throw e;
		}

		return listCajeroDTOs;
	}
	
	
	public List<CajeroRecaudacionDTO> obtenerCajerosRecaudacion(Date fechaInicio, Date fechaFin) throws SisatException {

		List<CajeroRecaudacionDTO> listCajeroRecaudacionDTOs = new ArrayList<CajeroRecaudacionDTO>();

		PreparedStatement pst;
		try {
			String SQL = "stp_cj_reporte_cajeros ?, ?";

			pst = connect().prepareStatement(SQL.toString());
			pst.setTimestamp(1, new Timestamp(fechaInicio.getTime()));
			pst.setTimestamp(2, new Timestamp(fechaFin.getTime()));

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				CajeroRecaudacionDTO cajeroRecaudacionDTO = new CajeroRecaudacionDTO();
				cajeroRecaudacionDTO.setNombreUsuario(rs.getString("nombre_usuario"));
				cajeroRecaudacionDTO.setAperturaId(rs.getInt("apertura_id"));
				cajeroRecaudacionDTO.setMonto(rs.getBigDecimal("monto"));
				cajeroRecaudacionDTO.setEstado(rs.getString("estado"));
				listCajeroRecaudacionDTOs.add(cajeroRecaudacionDTO);
			}

		} catch (Exception e) {
			throw new SisatException(e.getMessage());
		}

		return listCajeroRecaudacionDTOs;
	}
	
	public List<ReciboPagoDescuentoDetalleDTO> getDetalleDescuentoRecibo(int reciboId){
		List<ReciboPagoDescuentoDetalleDTO> lista = new ArrayList<ReciboPagoDescuentoDetalleDTO>();
		try {
			String SQL = "sptCJ_sel_ReciboDescuentoDetalleCaja ?";
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, reciboId);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				ReciboPagoDescuentoDetalleDTO item = new ReciboPagoDescuentoDetalleDTO();
				item.setReciboId(rs.getInt("recibo_id"));
				item.setMontoDescuento(rs.getBigDecimal("total_dscto"));
				item.setReferencia(rs.getString("referencia"));
				lista.add(item);
			}
		} catch (Exception ex) {
			String msg = "No se ha podido recuperar la(s) forma(s) de pago del recibo " + reciboId;
			System.out.println(msg + ex);
		}
		return lista;
	}

	public List<ResumenConceptosDTO> getResumenConcepto(String idDeudasACobrar,Integer personaId,Integer periodo){
		List<ResumenConceptosDTO> lista = new ArrayList<ResumenConceptosDTO>();
		try {
			StringBuilder SQL = new StringBuilder("SELECT d.subconcepto_id,d.anno_deuda, "); 
			SQL.append("case when d.concepto_id=3 then (select predio_id from "+ SATParameterFactory.getDBNameScheme() + ".rp_djpredial where dj_id=d.nro_referencia) else 0 end predio_id, "); 
			SQL.append("max(d.insoluto-d.insoluto_cancelado) montoCuota,count(*) cuotas, sb.descripcion subconcepto ");
			SQL.append("FROM "+ SATParameterFactory.getDBNameScheme() + ".cd_deuda d ");
			SQL.append("inner join "+ SATParameterFactory.getDBNameScheme() + ".gn_subconcepto sb on (sb.subconcepto_id=d.subconcepto_id) ");
			SQL.append("inner join "+ SATParameterFactory.getDBNameScheme() + ".cj_benef_bonocajam bc on (bc.persona_id=d.persona_id) ");
			SQL.append("where d.deuda_id in ("+idDeudasACobrar+") and d.persona_id =? ");
			SQL.append("and bc.estado='1' and bc.periodo=? ");
			SQL.append("group by d.concepto_id,d.subconcepto_id,sb.descripcion,d.anno_deuda,d.nro_referencia");
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, personaId);
			pst.setInt(2, periodo);
			
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				ResumenConceptosDTO item = new ResumenConceptosDTO();
				item.setSubConceptoId(rs.getInt("subconcepto_id"));
				item.setAnnoDeuda(rs.getInt("anno_deuda"));
				item.setPredioId(rs.getInt("predio_id"));
				item.setCuotas(rs.getInt("cuotas"));
				item.setSubConcepto(rs.getString("subconcepto"));
				item.setMontoCuota(rs.getBigDecimal("montoCuota"));
				
				lista.add(item);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return lista;
	}
	
	
	public Integer esBenefBonoCajam(Integer personaId,Integer periodo){
		Integer benefBonoCajamId=0;
		try {
			StringBuilder SQL = new StringBuilder("SELECT benef_bonocajam_id FROM dbo.cj_benef_bonocajam b WHERE b.persona_id=? and b.estado='1' and b.periodo=? "); 
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, personaId);
			pst.setInt(2, periodo);
			
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				benefBonoCajamId=rs.getInt("benef_bonocajam_id");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return benefBonoCajamId;
	}
	
	public int buscarSupervisor(String usuarioSupervisor, String passwordSupervisor)  throws SisatException
	{
		int result = 0;
			try{
				StringBuilder SQL = new StringBuilder("SELECT u.estado estado "); 
				SQL.append("FROM "+ SATParameterFactory.getDBNameScheme() + ".cj_agencia_usuario age_usu ");
				SQL.append("	inner join "+ SATParameterFactory.getDBNameScheme() + ".sg_usuario u on (age_usu.usuario_id = u.usuario_id) ");				
				SQL.append("WHERE age_usu.estado = 1 and  age_usu.agencia_id = 1 and age_usu.tipo_rol = 'S' ");
				SQL.append("and (DATEDIFF(dd,age_usu.fecha_inicio,GETDATE()) >= 0) and ((DATEDIFF(dd,GETDATE(),age_usu.fecha_fin) >=0) or age_usu.fecha_fin is null) ");
				SQL.append("and u.nombre_usuario = ? and u.clave = ? ");
				
				PreparedStatement pst = connect().prepareStatement(SQL.toString());
				pst.setString(1, usuarioSupervisor);
				pst.setString(2, passwordSupervisor);
				
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {					
					result = rs.getInt("estado");					
				}
			}catch (Exception ex) {
				ex.printStackTrace();
				
			}
		return result;
	}
	
	public int extornarRecibo(int recibo_id, String obs_extorno, int usuario_id, String terminal) throws SQLException, SisatException {

		int resultado = 0;
		Connection cn = connect();
		try {			
			String sql = "stp_cj_extornar_recibo ?,?,?,?";

			// PreparedStatement pst =
			// connect().prepareStatement(sql.toString());
			CallableStatement pst = cn.prepareCall(sql.toString());
			pst.setInt(1, recibo_id);			
			pst.setString(2, obs_extorno);
			pst.setInt(3, usuario_id);
			pst.setString(4, terminal);

			// ResultSet rs = pst.executeQuery();
			pst.execute();
			
			resultado = 1;

		} catch (Exception e) {
			// TODO: handle exception			
			//e.printStackTrace();
			throw new SisatException(e.getMessage());
		}
		return resultado;

	}

	public int anularPorSistemaRecibo(int recibo_id, String obs_extorno, int usuario_id, String terminal) throws SQLException, SisatException {

		int resultado = 0;
		Connection cn = connect();
		try {			
			String sql = "stp_cj_anular_por_sistema_recibo ?,?,?,?";

			// PreparedStatement pst =
			// connect().prepareStatement(sql.toString());
			CallableStatement pst = cn.prepareCall(sql.toString());
			pst.setInt(1, recibo_id);			
			pst.setString(2, obs_extorno);
			pst.setInt(3, usuario_id);
			pst.setString(4, terminal);

			// ResultSet rs = pst.executeQuery();
			pst.execute();
			
			resultado = 1;

		} catch (Exception e) {
			// TODO: handle exception			
			//e.printStackTrace();
			throw new SisatException(e.getMessage());
		}
		return resultado;

	}	
	
	public List<ReporteCuentaDTO> obtenerReporteCuentas(Date fechaInicio, Date fechaFin) throws SisatException {

		List<ReporteCuentaDTO> listReporteCuentaDTOs = new ArrayList<ReporteCuentaDTO>();

		Connection cn = connect();
		try {
			String sql = "stp_cj_reporte_partidas_cuentas ?,?";

			CallableStatement pst = cn.prepareCall(sql.toString());
			pst.setTimestamp(1, new java.sql.Timestamp(fechaInicio.getTime()));
			pst.setTimestamp(2, new java.sql.Timestamp(fechaFin.getTime()));

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {					
				ReporteCuentaDTO reporteCuentaDTO = new ReporteCuentaDTO();
				
				reporteCuentaDTO.setBloque(rs.getInt("bloque"));
				reporteCuentaDTO.setDescripcionCuenta(rs.getString("descripcion_cuenta"));
				reporteCuentaDTO.setColumnaParcial1(rs.getBigDecimal("columna_parcial_1"));
				reporteCuentaDTO.setColumnaParcial2(rs.getBigDecimal("columna_parcial_2"));
				reporteCuentaDTO.setMonto(rs.getBigDecimal("monto"));
				listReporteCuentaDTOs.add(reporteCuentaDTO);
				
			}

		} catch (Exception e) {

			throw new SisatException(e.getMessage());
		}

		return listReporteCuentaDTOs;
	}
	
	public List<PagoTupaReferenciaDTO> buscarPersonaContribuyente(String p) throws SisatException{		
		
		List<PagoTupaReferenciaDTO> lista = new ArrayList<PagoTupaReferenciaDTO>();
		
		try {
			StringBuilder SQL = new StringBuilder("stp_cj_obtener_persona_contribuyente ?");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setString(1, (p).concat("%"));
			

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				PagoTupaReferenciaDTO pagoTupaReferenciaDTO = new PagoTupaReferenciaDTO();
				pagoTupaReferenciaDTO.setPersonaId(rs.getInt("persona_id"));
				pagoTupaReferenciaDTO.setApellidosNombres(rs.getString("apellidos_nombres"));
				pagoTupaReferenciaDTO.setDniRuc(rs.getString("nro_doc"));
				pagoTupaReferenciaDTO.setFuente(rs.getString("fuente"));
				lista.add(pagoTupaReferenciaDTO);
				//lista.add(rs.getString("apellidos_nombres"));
			}
		} catch (Exception ex) {
			
			throw new SisatException(ex.getMessage());
		}
		return lista;		
	}
	
	public void limpiarCjTmpDeudaCajero(int cajeroId) throws SisatException{
		
		try {
			StringBuilder SQL = new StringBuilder("exec stp_cj_limpiar_data_cajero ?"); 
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, cajeroId);			
			pst.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new SisatException(ex.getMessage());
		}
	}
	
	public CjPersona cargarContribuyentePorPlaca(String placa) throws SisatException {

		CjPersona persona = null;
		try {

			StringBuilder SQL = new StringBuilder("stp_cj_buscar_contribuyente_por_placa ?");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setString(1, placa);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				persona = new CjPersona();

				persona.setPersona_id(rs.getInt(1));
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new SisatException(ex.getMessage());
		}

		return persona;
	}
	
	public List<ResumenDeudasCobranzaCoactivaDTO> verificarDeudasEnCobranzaCoactiva(String deudas)
			throws SisatException {

		List<ResumenDeudasCobranzaCoactivaDTO> lstResumenDTOs = new ArrayList<ResumenDeudasCobranzaCoactivaDTO>();
		try {
			String sql = "stp_cc_obtener_resumen_deudas_detenidas ?";
			PreparedStatement pst = connect().prepareStatement(sql);
			pst.setString(1, deudas);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				ResumenDeudasCobranzaCoactivaDTO resumenDTO = new ResumenDeudasCobranzaCoactivaDTO();

				resumenDTO.setConceptoDescripcion(rs.getString("descripcion_corta"));
				resumenDTO.setAnho(rs.getInt("anho"));
				resumenDTO.setNroCuotas(rs.getInt("nro_cuotas"));
				resumenDTO.setNroPapeleta(rs.getString("nro_papeleta"));
				lstResumenDTOs.add(resumenDTO);
			}

		} catch (Exception ex) {
			throw new SisatException(ex.getMessage());
		}

		return lstResumenDTOs;
	}
	
	public String busquedaContribEnAtencion(Integer usuarioId, Integer personaId, Integer deudaId)
			throws SisatException {

		String resp = null;
		try {
			
			StringBuilder sb = new StringBuilder();
			
			sb.append("select distinct u.nombre_usuario from cj_tmp_deuda dtmp ");
			sb.append("inner join sg_usuario u on dtmp.agencia_usuario_id = u.usuario_id ");
			sb.append("where dtmp.persona_id = ?");			
			
			PreparedStatement pst = connect().prepareStatement(sb.toString());			
			pst.setInt(1, personaId );
			//pst.setInt(1, deudaId );

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				resp= rs.getString(1);
			}

		} catch (Exception ex) {
			throw new SisatException(ex.getMessage());
		}

		return resp;
	}

	
	public List<String> busquedaDeudaEnAtencion(String deudasId)
			throws SisatException {

		List<String> listResp = new ArrayList<String>();
		try {
			
			StringBuilder sb = new StringBuilder();
			
			sb.append("select distinct u.nombre_usuario from cj_tmp_deuda dtmp ");
			sb.append("inner join sg_usuario u on dtmp.agencia_usuario_id = u.usuario_id ");
			sb.append("where dtmp.deuda_id in (select * from dbo.SPLIT(?,','))");			
			
			PreparedStatement pst = connect().prepareStatement(sb.toString());			
			pst.setString(1, deudasId );
			//pst.setInt(1, deudaId );

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				listResp.add(rs.getString(1));
			}

		} catch (Exception ex) {
			throw new SisatException(ex.getMessage());
		}

		return listResp;
	}
	
	public List<CjReciboPagoEntity> busquedaRecibosPorReferencia(String referencia) throws SisatException {

		List<CjReciboPagoEntity> listaOperacion = new ArrayList<CjReciboPagoEntity>();
		try {
			String sql = "stp_cj_buscar_recibo_por_referencia ?";
			PreparedStatement pst = connect().prepareStatement(sql.toString());
			pst.setString(1, referencia);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				CjReciboPagoEntity item = new CjReciboPagoEntity();
				item.setReciboId(rs.getInt("recibo_id"));
				item.setNroRecibo(rs.getString("nro_recibo"));
				item.setFechaRecibo(new Date(rs.getTimestamp("fecha_recibo").getTime()));
				item.setTipoOperacion(rs.getString("tipo_operacion"));
				item.setMontoCobrado(rs.getBigDecimal("monto_cobrado"));				
				item.setEstado(rs.getString("estado"));
				item.setTipoRecibo(rs.getString("tipo_recibo"));				
				item.setReferencia(rs.getString("referencia"));
				item.setNombreUsuario(rs.getString("nombre_usuario"));
				listaOperacion.add(item);
			}

		} catch (Exception e) {
			
			throw new SisatException(e.getMessage(),e.getCause());
		}
		return listaOperacion;
	}
	
	public List<TramoSaldoDTO> obtenerTramos(){
		List<TramoSaldoDTO> lista = new ArrayList<TramoSaldoDTO>();
		try{
			String SQL = "SELECT fecha_computo_tramo_id,CONVERT(varchar,CONVERT(date,fecha_computo_inicio),103) + ' - ' + CONVERT(varchar,CONVERT(date,fecha_computo_fin),103) as tramo FROM cd_fecha_computo_tramo WHERE estado = 1 ORDER BY fecha_computo_fin desc";
			
			PreparedStatement pst = connect().prepareStatement(SQL);
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				TramoSaldoDTO obj = new TramoSaldoDTO();
				obj.setTramoId(rs.getInt("fecha_computo_tramo_id"));
				obj.setTramoDescripcion(rs.getString("tramo"));				
				lista.add(obj);
			}
		}catch(Exception ex){
			String msg = "No ha sido posible recuperar los tramos";
			System.out.println(msg + ex);
		}
		return lista;
	}

	public Integer registrarConstancia(CrConstanciaNoAdeudo constancia) throws Exception{
		Integer result=Constante.RESULT_PENDING;
		try {
			String SQL = "{call dbo.stp_cr_registrar_constancia(?,?,?,?,?,?,?,?,?,?)}";
			CallableStatement cs = connect().prepareCall(SQL.toString());
			cs.setInt(1, constancia.getPersonaId());
			cs.setInt(2, constancia.getReciboId());
			cs.setString(3, constancia.getDocidentidad());			
			//cramirez
			cs.setString(4, constancia.getAnio());
			cs.setString(5, constancia.getSubconceptoId());
			cs.setString(6, constancia.getReferencia());
			cs.setInt(7, constancia.getTipoConstancia());
			cs.setString(8, constancia.getCuotas());
			//--------
			cs.setInt(9, constancia.getUsuarioId());
			cs.setString(10, constancia.getTerminal());
			
			cs.execute();
			result=Constante.RESULT_SUCCESS;
		} catch (Exception e) {
			throw (e);
		}
		return result;		
	}
	
	public List<CrGeneralDTO> obtenerAnio(Integer personaId,String tipoPredio)throws Exception{
		List<CrGeneralDTO> lista=new LinkedList<CrGeneralDTO>();
		try{
			
			String SQL = "stp_rc_obtener_anno_dj ?,?";

			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, personaId);
			pst.setString(2, tipoPredio);
			ResultSet rs=pst.executeQuery();
			
			while(rs.next()){
				CrGeneralDTO obj=new CrGeneralDTO(); 
				obj.setId(rs.getInt("id"));
				obj.setDescripcion(rs.getString("anno"));
				lista.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	
	public List<CrGeneralDTO> obtenerPredios(Integer personaId, Integer anio)throws Exception{
		List<CrGeneralDTO> lista=new LinkedList<CrGeneralDTO>();
		try{
			
			String SQL = "stp_rc_obtener_predios_record ?, ?";

			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, personaId);
			pst.setInt(2, anio);
			ResultSet rs=pst.executeQuery();
			
			while(rs.next()){
				CrGeneralDTO obj=new CrGeneralDTO(); 
				obj.setId(rs.getInt("id"));
				obj.setDescripcion(rs.getString("descripcion"));
				obj.setGlosa(rs.getString("predio_id"));
				lista.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	
	
	public int registrarRecord(int persona_id,String documento,String anio ,String predios_id,int tipo_record,int recibo_id,int usuario_id,String terminal)throws Exception{
		List<CrGeneralDTO> lista=new LinkedList<CrGeneralDTO>();
		int result = 0;
		try{
			
			String SQL = "stp_cr_registrar_record ?, ?, ?, ?, ?, ?, ?, ?";

			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, persona_id);
			pst.setString(2, documento);
			pst.setString(3, anio);
			pst.setString(4, predios_id);
			pst.setInt(5, tipo_record);
			pst.setInt(6, recibo_id);
			pst.setInt(7, usuario_id);
			pst.setString(8, terminal);
			
			result = pst.executeUpdate();
			
			
		}catch(Exception e){
			throw(e);
		}
		
		return result;

	}
}