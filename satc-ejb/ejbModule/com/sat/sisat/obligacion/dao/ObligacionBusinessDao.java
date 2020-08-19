package com.sat.sisat.obligacion.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Query;

import com.sat.sisat.administracion.parameter.ParameterLoader;
import com.sat.sisat.cobranzacoactiva.dto.DetencionDeudaDTO;
import com.sat.sisat.common.dao.GeneralDao;
import com.sat.sisat.common.security.UserSession;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.SATParameterFactory;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.obligacion.dto.MultaDTO;
import com.sat.sisat.obligacion.dto.ObligacionDTO;
import com.sat.sisat.obligacion.dto.SubConceptoDTO;
import com.sat.sisat.persistence.entity.CcTipoActo;
import com.sat.sisat.persistence.entity.DtFechaVencimiento;

/**
 * @author Miguel Macias
 * @version 0.1
 * @since 29/07/2012 La clase ObligacionBusinessDao.java ha sido creada con el
 *        proposito de la obtension y persistencia de datos relaciona con las
 *        tablas dt_obligacion, dt_determinacion, cd_deuda, cd_deuda_historica
 */
public class ObligacionBusinessDao extends GeneralDao {

	public final int TIPO_DOCU_RESOLUCION_MULTA = 16;

	final int COSTAS = 5;
	final int GASTOS = 6;
	final int EPND = 11;
	final int MULTAS = 12;
	final int MULTAS_DRTPE = 13;
	final int MULTAS_MPC = 14; 		/** * Permitirá el cobro de Multas generadas en la MPC - Noviembre 2016 * */
	final int MULTAS_DRTC = 15;

	final String TIPO_ACTO_DESCRIPCION_CORTA_RESOLUCION_MULTA = "RM";

	/**
	 * Método que obtiene los sub-conceptos de Costas asociado a un año
	 * 
	 * @param anho
	 * @return
	 * @throws SisatException
	 */
	public List<SubConceptoDTO> getSubConceptoCostas(int anho,Integer subconceptoId)
			throws SisatException {

		List<SubConceptoDTO> listSubConceptoDTOs = new ArrayList<SubConceptoDTO>();

		StringBuilder sb = new StringBuilder(800);

		sb.append("SELECT ct.concepto_id, ");
		sb.append("ct.subconcepto_id, ");
		sb.append("ct.periodo, ");
		sb.append("sb.descripcion, ");
		sb.append("sb.etiqueta, ");
		sb.append("ct.valor, ");
		sb.append("ct.porcentaje_uit ");
		sb.append("FROM ");
		sb.append(SATParameterFactory.getDBNameScheme());
		sb.append(".");
		sb.append("gn_concepto_tasa ct INNER JOIN ");
		sb.append(SATParameterFactory.getDBNameScheme());
		sb.append(".gn_subconcepto sb ON ct.concepto_id = sb.concepto_id AND ");
		sb.append("ct.subconcepto_id = sb.subconcepto_id WHERE ct.periodo = ");
		sb.append(anho).append(" ");
		sb.append("and sb.concepto_id = 5 and ct.estado=1 and sb.subconcepto_id= "); // id de concepto COSTAS
		sb.append(subconceptoId).append(" ");
		sb.append("ORDER BY sb.descripcion_corta");

		String sql = sb.toString();

		PreparedStatement pst;

		try {
			pst = connect().prepareStatement(sql);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				SubConceptoDTO subConceptoDTO = new SubConceptoDTO();

				subConceptoDTO.setConceptoId(rs.getInt(1));
				subConceptoDTO.setSubconceptoId(rs.getInt(2));
				subConceptoDTO.setPeriodo(rs.getInt(3));
				subConceptoDTO.setDescripcion(rs.getString(4));
				subConceptoDTO.setDescripcionCorta(rs.getString(5));
				subConceptoDTO.setValor(rs.getBigDecimal(6));
				subConceptoDTO.setPorcentajeUit(rs.getBigDecimal(7));

				listSubConceptoDTOs.add(subConceptoDTO);
			}

		} catch (SQLException e) {

			throw new SisatException(e.getMessage(), e.getCause());
		} catch (SisatException e) {

			throw e;
		}

		return listSubConceptoDTOs;
	}
	
	/**Metodo para retornas todas las costas para el caso de convenio con el Ministerio de Trabajo*/
	public List<SubConceptoDTO> getSubConceptoCostas(int anho)
			throws SisatException {

		List<SubConceptoDTO> listSubConceptoDTOs = new ArrayList<SubConceptoDTO>();

		StringBuilder sb = new StringBuilder(800);

		sb.append("SELECT ct.concepto_id, ");
		sb.append("ct.subconcepto_id, ");
		sb.append("ct.periodo, ");
		sb.append("sb.descripcion, ");
		sb.append("sb.etiqueta, ");
		sb.append("ct.valor, ");
		sb.append("ct.porcentaje_uit ");
		sb.append("FROM ");
		sb.append(SATParameterFactory.getDBNameScheme());
		sb.append(".");
		sb.append("gn_concepto_tasa ct INNER JOIN ");
		sb.append(SATParameterFactory.getDBNameScheme());
		sb.append(".gn_subconcepto sb ON ct.concepto_id = sb.concepto_id AND ");
		sb.append("ct.subconcepto_id = sb.subconcepto_id WHERE ct.periodo = ");
		sb.append(anho).append(" ");
		sb.append("and sb.concepto_id = 5 "); // id de concepto COSTAS
		sb.append("ORDER BY sb.descripcion_corta");

		String sql = sb.toString();

		PreparedStatement pst;

		try {
			pst = connect().prepareStatement(sql);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				SubConceptoDTO subConceptoDTO = new SubConceptoDTO();

				subConceptoDTO.setConceptoId(rs.getInt(1));
				subConceptoDTO.setSubconceptoId(rs.getInt(2));
				subConceptoDTO.setPeriodo(rs.getInt(3));
				subConceptoDTO.setDescripcion(rs.getString(4));
				subConceptoDTO.setDescripcionCorta(rs.getString(5));
				subConceptoDTO.setValor(rs.getBigDecimal(6));
				subConceptoDTO.setPorcentajeUit(rs.getBigDecimal(7));

				listSubConceptoDTOs.add(subConceptoDTO);
			}

		} catch (SQLException e) {

			throw new SisatException(e.getMessage(), e.getCause());
		} catch (SisatException e) {

			throw e;
		}

		return listSubConceptoDTOs;
	}
	
	/**
	 * Método que obtiene los sub-conceptos de Costas asociado a un año para embargos 
	 * usado para vehicular como para las RS
	 * 
	 * @param anho
	 * @return
	 * @throws SisatException
	 */
	public List<SubConceptoDTO> getSubConceptoCostasEmbargo(int anho)
			throws SisatException {

		List<SubConceptoDTO> listSubConceptoDTOs = new ArrayList<SubConceptoDTO>();

		StringBuilder sb = new StringBuilder(800);

		sb.append("SELECT ct.concepto_id, ");
		sb.append("ct.subconcepto_id, ");
		sb.append("ct.periodo, ");
		sb.append("sb.descripcion, ");
		sb.append("sb.etiqueta, ");
		sb.append("ct.valor, ");
		sb.append("ct.porcentaje_uit ");
		sb.append("FROM ");
		sb.append(SATParameterFactory.getDBNameScheme());
		sb.append(".");
		sb.append("gn_concepto_tasa ct INNER JOIN ");
		sb.append(SATParameterFactory.getDBNameScheme());
		sb.append(".gn_subconcepto sb ON ct.concepto_id = sb.concepto_id AND ");
		sb.append("ct.subconcepto_id = sb.subconcepto_id WHERE ct.periodo = ");
		sb.append(anho).append(" ");
		sb.append("and sb.concepto_id = 5 and ct.estado=1 and sb.subconcepto_id in(109,110,111,112) "); // id de concepto COSTAS
		sb.append("ORDER BY sb.descripcion_corta");

		String sql = sb.toString();

		PreparedStatement pst;

		try {
			pst = connect().prepareStatement(sql);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				SubConceptoDTO subConceptoDTO = new SubConceptoDTO();

				subConceptoDTO.setConceptoId(rs.getInt(1));
				subConceptoDTO.setSubconceptoId(rs.getInt(2));
				subConceptoDTO.setPeriodo(rs.getInt(3));
				subConceptoDTO.setDescripcion(rs.getString(4));
				subConceptoDTO.setDescripcionCorta(rs.getString(5));
				subConceptoDTO.setValor(rs.getBigDecimal(6));
				subConceptoDTO.setPorcentajeUit(rs.getBigDecimal(7));

				listSubConceptoDTOs.add(subConceptoDTO);
			}

		} catch (SQLException e) {

			throw new SisatException(e.getMessage(), e.getCause());
		} catch (SisatException e) {

			throw e;
		}

		return listSubConceptoDTOs;
	}

	/**
	 * Método que obtiene los sub-conceptos de Gastos asociado a un año
	 * 
	 * @param anho
	 * @return
	 * @throws SisatException
	 */
	public List<SubConceptoDTO> getSubConceptoGastos(int anho)
			throws SisatException {

		List<SubConceptoDTO> listSubConceptoDTOs = new ArrayList<SubConceptoDTO>();

		StringBuilder sb = new StringBuilder(200);

		sb.append("SELECT concepto_id, ");
		sb.append("subconcepto_id, ");
		sb.append("descripcion, ");
		sb.append("etiqueta ");
		sb.append("FROM ");
		sb.append(SATParameterFactory.getDBNameScheme());
		sb.append(".gn_subconcepto ");
		sb.append("WHERE concepto_id = 6 "); // id de concepto GASTOS
		sb.append("ORDER BY descripcion_corta");

		String sql = sb.toString();

		PreparedStatement pst;

		try {
			pst = connect().prepareStatement(sql);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				SubConceptoDTO subConceptoDTO = new SubConceptoDTO();

				subConceptoDTO.setConceptoId(rs.getInt(1));
				subConceptoDTO.setSubconceptoId(rs.getInt(2));
				subConceptoDTO.setDescripcion(rs.getString(3));
				subConceptoDTO.setDescripcionCorta(rs.getString(4));

				listSubConceptoDTOs.add(subConceptoDTO);
			}

		} catch (SQLException e) {

			throw new SisatException(e.getMessage(), e.getCause());
		} catch (SisatException e) {

			throw e;
		}

		return listSubConceptoDTOs;
	}

	public Integer obtenerDiasHabiles(Timestamp a, Timestamp b) {
		Integer i = null;
		try {
			String SQL = "select dbo.fnGN_getDiasHabiles (?,?)";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setTimestamp(1, a);
			pst.setTimestamp(2, b);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				i = rs.getInt(1);
			}
		} catch (Exception ex) {
			String msg = "No se ha podido recuperar los dias habiles ";
			System.out.println(msg + ex);
		}
		return i;

	}

	/**
	 * Método que obtiene los sub-conceptos de Gastos asociado a un año
	 * 
	 * @param anho
	 * @return
	 * @throws SisatException
	 */
	public List<SubConceptoDTO> getSubConceptoMULTASDRTPE(int anho)
			throws SisatException {

		List<SubConceptoDTO> listSubConceptoDTOs = new ArrayList<SubConceptoDTO>();

		StringBuilder sb = new StringBuilder(200);

		sb.append("SELECT concepto_id, ");
		sb.append("subconcepto_id, ");
		sb.append("descripcion, ");
		sb.append("etiqueta ");
		sb.append("FROM ");
		sb.append(SATParameterFactory.getDBNameScheme());
		sb.append(".gn_subconcepto ");
		sb.append("WHERE concepto_id = 13 "); // id de concepto GASTOS
		sb.append("ORDER BY descripcion_corta");

		String sql = sb.toString();

		PreparedStatement pst;

		try {
			pst = connect().prepareStatement(sql);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				SubConceptoDTO subConceptoDTO = new SubConceptoDTO();

				subConceptoDTO.setConceptoId(rs.getInt(1));
				subConceptoDTO.setSubconceptoId(rs.getInt(2));
				subConceptoDTO.setDescripcion(rs.getString(3));
				subConceptoDTO.setDescripcionCorta(rs.getString(4));

				listSubConceptoDTOs.add(subConceptoDTO);
			}

		} catch (SQLException e) {

			throw new SisatException(e.getMessage(), e.getCause());
		} catch (SisatException e) {

			throw e;
		}

		return listSubConceptoDTOs;
	}

	/**
	 * Método que obtiene los sub-conceptos de Multas asociado a un año
	 * 
	 * @param anho
	 * @return
	 * @throws SisatException
	 */
	@SuppressWarnings("unchecked")
	public List<SubConceptoDTO> getSubConceptoMultas(int anho)
			throws SisatException {

		List<SubConceptoDTO> listSubConceptoDTOs = new ArrayList<SubConceptoDTO>();

		StringBuilder sb = new StringBuilder(200);

		sb.append("SELECT concepto_id as conceptoId, ");
		sb.append("subconcepto_id as subconceptoId, ");
		sb.append("descripcion as descripcion, null as periodo, null as porcentajeUit, null as valor,");
		sb.append("etiqueta as descripcionCorta ");
		sb.append("FROM ");
		sb.append(SATParameterFactory.getDBNameScheme());
		sb.append(".gn_subconcepto ");
		sb.append("WHERE concepto_id = 12 and subconcepto_id != 120");
		sb.append("ORDER BY descripcion_corta");

		String sql = sb.toString();

		try {

			Query q = em.createNativeQuery(sql, SubConceptoDTO.class);

			listSubConceptoDTOs = q.getResultList();

		} catch (RuntimeException e) {
			throw new SisatException(e.getMessage(), e.getCause());
		}
		return listSubConceptoDTOs;
	}

	/**
	 * Método que obtiene los sub-conceptos de EPND asociado a un año
	 * 
	 * @param anho
	 * @return
	 * @throws SisatException
	 */
	public List<SubConceptoDTO> getSubConceptoEPND(int anho)
			throws SisatException {

		List<SubConceptoDTO> listSubConceptoDTOs = new ArrayList<SubConceptoDTO>();

		StringBuilder sb = new StringBuilder(200);

		sb.append("SELECT concepto_id, ");
		sb.append("subconcepto_id, ");
		sb.append("descripcion, ");
		sb.append("etiqueta ");
		sb.append("FROM ");
		sb.append(SATParameterFactory.getDBNameScheme());
		sb.append(".gn_subconcepto ");
		sb.append("WHERE concepto_id = 11 ");
		sb.append("ORDER BY descripcion_corta");

		String sql = sb.toString();

		PreparedStatement pst;

		try {
			pst = connect().prepareStatement(sql);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				SubConceptoDTO subConceptoDTO = new SubConceptoDTO();

				subConceptoDTO.setConceptoId(rs.getInt(1));
				subConceptoDTO.setSubconceptoId(rs.getInt(2));
				subConceptoDTO.setDescripcion(rs.getString(3));
				subConceptoDTO.setDescripcionCorta(rs.getString(4));

				listSubConceptoDTOs.add(subConceptoDTO);
			}

		} catch (SQLException e) {

			throw new SisatException(e.getMessage(), e.getCause());
		} catch (SisatException e) {

			throw e;
		}

		return listSubConceptoDTOs;
	}

	/**
	 * Método que guarda las obligaciones y registra la deuda generada asi como
	 * el historico de esta.
	 * 
	 * @param listObligacionDTOs
	 * @param personaId
	 * @throws SisatException
	 */
	public void saveObligaciones(List<ObligacionDTO> listObligacionDTOs,
			int personaId, UserSession userSession) throws SisatException {

		// Consideraciones
		// Se esta tomando el tipoDeuda del tipo AUTOGENERADO tanto para la
		// tabla cd_deuda
		// como para la tabla cd_deuda_historico
		
		int usuarioId;
		String terminal;
		
		final int TIPO_DEUDA = 1;
		// asi como para el tipo_movimiento GENERADO
		final int TIPO_MOVIMIENTO = 1;
		
		usuarioId = userSession.getUsuarioId();
		terminal = userSession.getTerminal();

		Connection con; // variables de instancia
		PreparedStatement stat = null;
		con = connect();

		StringBuilder sbDeterminacion = new StringBuilder();

		sbDeterminacion.append("INSERT INTO ");
		sbDeterminacion.append(SATParameterFactory.getDBNameScheme());
		sbDeterminacion.append(".[dt_determinacion] ");
		sbDeterminacion.append("([determinacion_id] "); // -- 1
		sbDeterminacion.append(",[persona_id] "); // -- 2
		sbDeterminacion.append(",[anno_determinacion] "); // -- 3
		sbDeterminacion.append(",[concepto_id] "); // -- 4
		sbDeterminacion.append(",[subconcepto_id] "); // -- 5
		sbDeterminacion.append(",[base_imponible] "); // -- 6
		sbDeterminacion.append(",[base_exonerada] "); // -- 7
		sbDeterminacion.append(",[base_afecta] "); // -- 8
		sbDeterminacion.append(",[impuesto] "); // -- 9
		sbDeterminacion.append(",[nro_cuotas] "); // -- 10
		sbDeterminacion.append(",[djreferencia_id] "); // -- 11
		sbDeterminacion.append(",[estado] "); // -- 12
		sbDeterminacion.append(",[usuario_id] "); // -- 13
		sbDeterminacion.append(",[fecha_registro] "); // -- 14
		sbDeterminacion.append(",[terminal]) "); // -- 15

		sbDeterminacion.append(" VALUES ");

		sbDeterminacion.append("(? "); // -- 1
		sbDeterminacion.append(",? "); // -- 2
		sbDeterminacion.append(",? "); // -- 3
		sbDeterminacion.append(",? "); // -- 4
		sbDeterminacion.append(",? "); // -- 5
		sbDeterminacion.append(",? "); // -- 6
		sbDeterminacion.append(",? "); // -- 7
		sbDeterminacion.append(",? "); // -- 8
		sbDeterminacion.append(",? "); // -- 9
		sbDeterminacion.append(",? "); // -- 10
		sbDeterminacion.append(",? "); // -- 11
		sbDeterminacion.append(",1 "); // -- 12
		sbDeterminacion.append(","+usuarioId+" "); // -- 13 //usuario_id
		sbDeterminacion.append(",GETDATE() "); // -- 14
		sbDeterminacion.append(",'"+terminal+"')"); // -- 15

		StringBuilder sbActo = new StringBuilder();

		sbActo.append("INSERT INTO ");
		sbActo.append(SATParameterFactory.getDBNameScheme());
		sbActo.append(".[cc_acto]");
		sbActo.append("([lote_id]"); // -- 1
		sbActo.append(",[acto_id]"); // -- 2
		sbActo.append(",[tipo_acto_id]"); // -- 3
		sbActo.append(",[concepto_id]"); // -- 4
		sbActo.append(",[subconcepto_id]"); // -- 5
		sbActo.append(",[tipo_persona_id]"); // -- 6
		sbActo.append(",[persona_id]"); // -- 7
		sbActo.append(",[anno_acto]"); // -- 8
		sbActo.append(",[fecha_emision]"); // -- 9
		sbActo.append(",[nro_acto]"); // -- 10
		sbActo.append(",[nro_resolucion]"); // -- 11
		sbActo.append(",[monto_deuda]"); // -- 12
		sbActo.append(",[tipo_documento]"); // -- 13
		sbActo.append(",[acto_persona_id]"); // -- 14
		sbActo.append(",[flag_origen]"); // -- 15
		sbActo.append(",[usuario_id]"); // -- 16
		sbActo.append(",[estado]"); // -- 17
		sbActo.append(",[fecha_registro]"); // -- 18
		sbActo.append(",[terminal]"); // -- 19
		sbActo.append(")");
		sbActo.append("VALUES");
		sbActo.append("(?"); // -- 1 <lote_id, int,>
		sbActo.append(",?"); // -- 2 <acto_id, int,>
		sbActo.append(",?"); // -- 3 <tipo_acto_id, int,>
		sbActo.append(",?"); // -- 4 <concepto_id, int,>
		sbActo.append(",?"); // -- 5 <subconcepto_id, int,>
		sbActo.append(",?"); // -- 6 <tipo_persona_id, int,>
		sbActo.append(",?"); // -- 7 <persona_id, int,>
		sbActo.append(",?"); // -- 8 <anno_acto, int,>
		sbActo.append(",?"); // -- 9 <fecha_emision, datetime,>
		sbActo.append(",?"); // -- 10 <nro_acto, varchar(20),>
		sbActo.append(",?"); // -- 11 <nro_resolucion, varchar(20),>
		sbActo.append(",?"); // -- 12 <monto_deuda, numeric(20,2),>
		sbActo.append(",?"); // -- 13 <tipo_documento, int,>
		sbActo.append(",?"); // -- 14 <acto_persona_id, int,>
		sbActo.append(",2"); // -- 15 <flag_origen, char(1),>
		sbActo.append(","+usuarioId+" "); // -- 16 usuario
		sbActo.append(",1"); // -- 17 estado
		sbActo.append(",GETDATE()"); // -- 18 fecha registro
		sbActo.append(",'"+terminal+"'"); // -- 19 terminal
		sbActo.append(")");

		StringBuilder sbDeuda = new StringBuilder();

		sbDeuda.append("INSERT ");
		sbDeuda.append(SATParameterFactory.getDBNameScheme());
		sbDeuda.append(".[cd_deuda] ");
		sbDeuda.append("([deuda_id] "); // -- 1
		sbDeuda.append(",[tipo_deuda_id] "); // -- 2
		sbDeuda.append(",[persona_id] "); // -- 3
		sbDeuda.append(",[concepto_id] "); // -- 4
		sbDeuda.append(",[subconcepto_id] "); // -- 5
		sbDeuda.append(",[determinacion_id] "); // -- 6
		sbDeuda.append(",[anno_deuda] "); // -- 7
		sbDeuda.append(",[fecha_emision] "); // -- 8
		sbDeuda.append(",[fecha_vencimiento] "); // -- 9
		sbDeuda.append(",[nro_cuota] "); // -- 10
		sbDeuda.append(",[monto_original] "); // -- 11
		sbDeuda.append(",[insoluto] "); // -- 12
		sbDeuda.append(",[reajuste] "); // -- 13
		sbDeuda.append(",[derecho_emision] "); // -- 14
		sbDeuda.append(",[total_deuda] "); // -- 15
		sbDeuda.append(",[flag_cc] "); // -- 16
		sbDeuda.append(",[nro_referencia] "); // --17
		sbDeuda.append(",[derecho_emision_cancelado] "); // -- 18
		sbDeuda.append(",[anno_documento] "); // -- 19
		sbDeuda.append(",[nro_documento_id] "); // -- 20
		sbDeuda.append(",[estado_deuda_id] "); // -- 21
		sbDeuda.append(",[usuario_id] "); // -- 22
		sbDeuda.append(",[estado] "); // -- 23
		sbDeuda.append(",[fecha_registro] "); // -- 24
		sbDeuda.append(",[terminal] "); // -- 25
		sbDeuda.append(",[papeleta_id] "); // -- 26
		sbDeuda.append(",[nro_papeleta] "); // -- 27
		sbDeuda.append(",[rec_id] ) "); // -- 28
		sbDeuda.append("VALUES ");
		sbDeuda.append("(? "); // -- 1
		sbDeuda.append(",? "); // -- 2
		sbDeuda.append(",? "); // -- 3
		sbDeuda.append(",? "); // -- 4
		sbDeuda.append(",? "); // -- 5
		sbDeuda.append(",? "); // -- 6
		sbDeuda.append(",? "); // -- 7
		sbDeuda.append(",? "); // -- 8 fecha_emision
		sbDeuda.append(",? "); // -- 9 fecha_vencimiento
		sbDeuda.append(",? "); // -- 10
		sbDeuda.append(",? "); // -- 11
		sbDeuda.append(",? "); // -- 12
		sbDeuda.append(",? "); // -- 13
		sbDeuda.append(",? "); // -- 14
		sbDeuda.append(",? "); // -- 15
		sbDeuda.append(",? "); // -- 16
		sbDeuda.append(",? "); // -- 17
		sbDeuda.append(",? "); // -- 18
		sbDeuda.append(",? "); // -- 19
		sbDeuda.append(",? "); // -- 20
		sbDeuda.append(",1 "); // -- 21 estado_deuda_id
		sbDeuda.append(","+usuarioId+" "); // -- 22 usuario_id
		sbDeuda.append(",1 "); // -- 23 estado
		sbDeuda.append(",GETDATE() "); // -- 24
		sbDeuda.append(",'"+terminal+"'"); // -- 25
		sbDeuda.append(",? "); // -- 26
		sbDeuda.append(",? "); // -- 27
		sbDeuda.append(",? )"); // -- 28  rec_id
		StringBuilder sbActoDeuda = new StringBuilder();

		sbActoDeuda.append("INSERT INTO ");
		sbActoDeuda.append(SATParameterFactory.getDBNameScheme());
		sbActoDeuda.append(".[cc_acto_deuda]");
		sbActoDeuda.append("([lote_id]"); // -- 1
		sbActoDeuda.append(",[acto_id]"); // -- 2
		sbActoDeuda.append(",[concepto_id]"); // -- 3
		sbActoDeuda.append(",[subconcepto_id]"); // -- 4
		sbActoDeuda.append(",[anno_deuda]"); // -- 5
		sbActoDeuda.append(",[fecha_vencimiento]"); // -- 6
		sbActoDeuda.append(",[interes_anual]"); // -- 7
		sbActoDeuda.append(",[interes_capitalizado]"); // -- 8
		sbActoDeuda.append(",[interes_mensual]"); // -- 9
		sbActoDeuda.append(",[insoluto]"); // -- 10
		sbActoDeuda.append(",[reajuste]"); // -- 11
		sbActoDeuda.append(",[area_uso]"); // -- 12
		sbActoDeuda.append(",[deuda_id]"); // -- 13
		sbActoDeuda.append(",[base_imponible]"); // -- 14
		sbActoDeuda.append(",[derecho_emision]"); // -- 15
		sbActoDeuda.append(",[costas]"); // -- 16
		sbActoDeuda.append(",[total_debitos] "); // -- 17
		sbActoDeuda.append(",[usuario_id]"); // -- 18
		sbActoDeuda.append(",[estado]"); // -- 19
		sbActoDeuda.append(",[fecha_registro]"); // -- 20
		sbActoDeuda.append(",[terminal] )"); // -- 21
		sbActoDeuda.append("VALUES ");
		sbActoDeuda.append("(? "); // -- 1 <lote_id, int,>
		sbActoDeuda.append(",? "); // -- 2 <acto_id, int,>
		sbActoDeuda.append(",? "); // -- 3 <concepto_id, int,>
		sbActoDeuda.append(",? "); // -- 4 <subconcepto_id, int,>
		sbActoDeuda.append(",? "); // -- 5 <anno_deuda, int,>
		sbActoDeuda.append(",? "); // -- 6 <fecha_vencimiento, datetime,>
		sbActoDeuda.append(",? "); // -- 7 <interes_anual, numeric(18,2),>
		sbActoDeuda.append(",? "); // -- 8 <interes_capitalizado,
									// numeric(18,2),>
		sbActoDeuda.append(",? "); // -- 9 <interes_mensual, numeric(18,2),>
		sbActoDeuda.append(",? "); // -- 10 <insoluto, numeric(18,2),>
		sbActoDeuda.append(",? "); // -- 11 <reajuste, numeric(18,2),>
		sbActoDeuda.append(",? "); // -- 12 <area_uso, numeric(10,2),>
		sbActoDeuda.append(",? "); // -- 13 <deuda_id, int,>
		sbActoDeuda.append(",? "); // -- 14 <base_imponible, numeric(18,2),>
		sbActoDeuda.append(",? "); // -- 15 <derecho_emision, numeric(18,2),>
		sbActoDeuda.append(",? "); // -- 16 <costas, numeric(18,2),>
		sbActoDeuda.append(",? "); // -- 17 <total_debitos, numeric(18,2),>)
		sbActoDeuda.append(","+usuarioId+" "); // -- 18 <usuario_id, int,>
		sbActoDeuda.append(",1 "); // -- 19 <estado, char(1),>
		sbActoDeuda.append(",GETDATE() "); // -- 20 <fecha_registro, datetime,>
		sbActoDeuda.append(",'"+terminal+"')"); // -- 21 <terminal,
															// varchar(20),>

		StringBuilder sbDeudaHistorico = new StringBuilder();

		sbDeudaHistorico.append("INSERT INTO ");
		sbDeudaHistorico.append(SATParameterFactory.getDBNameScheme());
		sbDeudaHistorico.append(".[cd_deuda_historica] ");
		sbDeudaHistorico.append("([deuda_id] "); // -- 1
		sbDeudaHistorico.append(",[historica_id] "); // -- 2
		sbDeudaHistorico.append(",[tipo_movimiento_id] "); // -- 3
		sbDeudaHistorico.append(",[determinacion_id] "); // -- 4
		sbDeudaHistorico.append(",[persona_id] "); // -- 5
		sbDeudaHistorico.append(",[fecha_movimiento] "); // -- 6
		sbDeudaHistorico.append(",[tipo_deuda] "); // -- 7
		sbDeudaHistorico.append(",[fecha_vencimiento] "); // -- 8
		sbDeudaHistorico.append(",[insoluto] "); // -- 9
		sbDeudaHistorico.append(",[total] "); // -- 10
		sbDeudaHistorico.append(",[usuario_id] "); // -- 11
		sbDeudaHistorico.append(",[estado] "); // -- 12
		sbDeudaHistorico.append(",[fecha_registro] "); // -- 13
		sbDeudaHistorico.append(",[terminal]) "); // -- 14
		sbDeudaHistorico.append("VALUES ");
		sbDeudaHistorico.append("(? "); // -- 1
		sbDeudaHistorico.append(",? "); // -- 2
		sbDeudaHistorico.append(",? "); // -- 3
		sbDeudaHistorico.append(",? "); // -- 4
		sbDeudaHistorico.append(",? "); // -- 5
		sbDeudaHistorico.append(",? "); // -- 6
		sbDeudaHistorico.append(",? "); // -- 7
		sbDeudaHistorico.append(",? "); // -- 8
		sbDeudaHistorico.append(",? "); // -- 9
		sbDeudaHistorico.append(",? "); // -- 10
		sbDeudaHistorico.append(","+usuarioId+" "); // -- 11 //usuario
		sbDeudaHistorico.append(",1 "); // -- 12 //estado
		sbDeudaHistorico.append(",GETDATE() "); // -- 13
		sbDeudaHistorico.append(",'"+terminal+"')"); // -- 14

		StringBuilder sbArchivo = new StringBuilder();

		sbArchivo.append("INSERT INTO ");
		sbArchivo.append(SATParameterFactory.getDBNameScheme());
		sbArchivo.append(".[cc_archivo] ");
		sbArchivo.append("([archivo_id] "); // -- 1
		sbArchivo.append(",[tipo_acto_id] "); // -- 2
		sbArchivo.append(",[nro_acto] "); // -- 3
		sbArchivo.append(",[ruta_archivo] "); // -- 4
		sbArchivo.append(",[estado] "); // -- 5
		sbArchivo.append(",[usuario_id] "); // -- 6
		sbArchivo.append(",[fecha_registro] "); // -- 7
		sbArchivo.append(",[terminal]) "); // -- 8
		sbArchivo.append("VALUES ");
		sbArchivo.append("(? "); // -- 1 <archivo_id, int,>
		sbArchivo.append(",? "); // -- 2 <tipo_acto_id, int,>
		sbArchivo.append(",? "); // -- 3 <nro_acto, varchar(20),>
		sbArchivo.append(",? "); // -- 4 <ruta_archivo, varchar(100),>
		sbArchivo.append(",1 "); // -- 5 <estado, char(1),>
		sbArchivo.append(","+usuarioId+" "); // -- 6 <usuario_id, int,>
		sbArchivo.append(",GETDATE() "); // -- 7 <fecha_registro, datetime,>
		sbArchivo.append(",'"+terminal+"')"); // -- 8 <terminal,
														// varchar(20),>)

		try {
			if (con.getAutoCommit()) {

				con.setAutoCommit(false);
			}

			String sqlDeterminacion = sbDeterminacion.toString();
			String sqlActo = sbActo.toString();
			String sqlDeuda = sbDeuda.toString();
			String sqlActoDeuda = sbActoDeuda.toString();
			String sqlDeudaHistorico = sbDeudaHistorico.toString();
			String sqlArchivo = sbArchivo.toString();

			int determinacionId = 0;
			int actoId = 0;
			int deudaId = 0;
			int deudaHistoricoId = 0;
			int archivo_id = 0;

			for (ObligacionDTO obligacionDTO : listObligacionDTOs) {

				/*********************** Insercion en la tabla dt_determinacion ******************************/
				stat = con.prepareCall(sqlDeterminacion);

				determinacionId = ObtenerCorrelativoTabla("dt_determinacion", 1);

				stat.setInt(1, determinacionId);
				stat.setInt(2, personaId);
				stat.setInt(3, Calendar.getInstance().get(Calendar.YEAR)); // año
																			// actual
				stat.setInt(4, obligacionDTO.getConceptoId());
				stat.setInt(5, obligacionDTO.getSubConceptoId());
				stat.setBigDecimal(6, obligacionDTO.getMonto()); // base_imponible
				stat.setBigDecimal(7, new BigDecimal(0)); // base_exonerada
				stat.setBigDecimal(8, obligacionDTO.getMonto()); 
				// base afecta  = base_imponible - base_exonerada
				stat.setBigDecimal(9, new BigDecimal(0)); // impuesto
				stat.setInt(10, 1); // nro de cuotas

				// insertar el id de la declaracion juarada del predio o del
				// vehiculo en caso de
				// MULTAS

				if (obligacionDTO.getCodigoPlacaReferencia() != null) {
					stat.setInt(11, obligacionDTO.getDjReferencia());
				} else {
					stat.setNull(11, Types.INTEGER);
				}

				stat.execute();

				CcTipoActo ccTipoActo = null;

				/********************** registro en cc_acto **************************/
				/***** registro solo en el **/
				/**** caso de que sea MULTA (Infracciones) **/
				if (obligacionDTO.getConceptoId() == MULTAS) {

					/**
					 * Obteniendo la unidad organica a partir del numero de
					 * valor ingresado, se asume que el valor ingresado es
					 * correcto en forma y esta validado en las unidades
					 * organizacionales asi como el tipo de documento.
					 */
					int unidad = Integer.parseInt(obligacionDTO.getNroValor()
							.split("-")[0]);

					stat = con.prepareCall(sqlActo);

					actoId = ObtenerCorrelativoTabla("cc_acto", 1);

					int nroActo = ObtenerCorrelativoDocumentoPorUnidadYTipo(
							unidad, TIPO_DOCU_RESOLUCION_MULTA, 1);

					DecimalFormat decimalFormatUndTipoDoc = new DecimalFormat(
							ParameterLoader.getParameter(
									"patternUnidad_TipoDoc").concat("-"));
					DecimalFormat decimalFormatSecuence = new DecimalFormat(
							ParameterLoader.getParameter("patternSequenceDoc"));

					String nro_acto = decimalFormatUndTipoDoc
							.format(unidad)
							.concat(decimalFormatUndTipoDoc
									.format(TIPO_DOCU_RESOLUCION_MULTA))
							.concat(decimalFormatSecuence.format(nroActo));

					ccTipoActo = (CcTipoActo) em
							.createQuery(
									"from CcTipoActo c where c.descripcionCorta = :descripcionCorta")
							.setParameter("descripcionCorta",
									TIPO_ACTO_DESCRIPCION_CORTA_RESOLUCION_MULTA)
							.getSingleResult();

					// -- 1 <lote_id, int,> ingresando el
					// valor 0 designado por defecto
					stat.setInt(1, 0);
					// -- 2 <acto_id, int,>
					stat.setInt(2, actoId);
					// -- 3 <tipo_acto_id, int,>
					stat.setInt(3, ccTipoActo.getTipoActoId());
					// -- 4 <concepto_id, int,>
					stat.setInt(4, obligacionDTO.getConceptoId());
					// -- 5 <subconcepto_id, int,>
					stat.setInt(5, obligacionDTO.getSubConceptoId());
					// -- 6 <tipo_persona_id, int,>
					stat.setNull(6, Types.INTEGER);
					// -- 7 <anno_acto, int,>
					stat.setInt(7, personaId);
					// -- 7 <anno_acto, int,>
					stat.setInt(8, obligacionDTO.getAnnoAfectacion());
					// -- 8 <fecha_emision, datetime,>
					stat.setDate(9, new java.sql.Date(obligacionDTO
							.getFechaEmision().getTime()));
					// -- 9 <nro_acto, varchar(20),>
					stat.setString(10, nro_acto);
					// -- 10 <nro_resolucion, varchar(20),>
					stat.setString(11, obligacionDTO.getNroValor());
					// -- 11 <monto_deuda, numeric(20,2),>
					stat.setBigDecimal(12, obligacionDTO.getMonto());
					// -- 12 <tipo_documento, int,>
					stat.setInt(13, 16);
					// -- 13 <acto_persona_id, int,> *****revisar*****
					stat.setNull(14, Types.INTEGER);

					stat.execute();

					if (obligacionDTO.getFileUpload() != null) {

						stat = con.prepareCall(sqlArchivo);
						archivo_id = ObtenerCorrelativoTabla("cc_archivo", 1);

						// <archivo_id, int,>
						stat.setInt(1, archivo_id);
						// <tipo_acto_id, int,>
						stat.setInt(2, ccTipoActo.getTipoActoId());
						// <nro_acto, varchar(20),>
						stat.setString(3, nro_acto);
						// <ruta_archivo, varchar(100),>
						String pathFile = moverArchivo(obligacionDTO);
						stat.setString(4, pathFile);

						stat.execute();
					}

				}

				/*********************** Insercion en la tabla cd_deuda ******************************/

				deudaId = ObtenerCorrelativoTabla("cd_deuda", 1);

				stat = con.prepareCall(sqlDeuda);
				stat.setInt(1, deudaId);
				stat.setInt(2, TIPO_DEUDA); // -- 2 tipo deuda
				stat.setInt(3, personaId); // persona_id
				stat.setInt(4, obligacionDTO.getConceptoId()); // concepto_id
				stat.setInt(5, obligacionDTO.getSubConceptoId()); // subconcepto_id
				stat.setInt(6, determinacionId); // determinacion_id
				stat.setInt(7, obligacionDTO.getAnnoAfectacion()); // anno_deuda

				stat.setInt(10, 1); // nro_cuotas
				stat.setBigDecimal(11, obligacionDTO.getMonto()); // monto_original
				stat.setBigDecimal(12, obligacionDTO.getMonto()); // insoluto
				stat.setBigDecimal(13, new BigDecimal(0)); // reajuste
				stat.setBigDecimal(14, new BigDecimal(0)); // derecho emision
				stat.setBigDecimal(15, obligacionDTO.getMonto()); // total_deuda
				// derecho emision cancelado
				stat.setBigDecimal(18, new BigDecimal(0)); 
				stat.setInt(23, obligacionDTO.getIdReferenciaREC());
				switch (obligacionDTO.getConceptoId()) {

				// caso COSTAS
				case COSTAS:
					stat.setDate(8, new java.sql.Date(obligacionDTO
							.getFechaEmision().getTime())); // fecha_emision
					stat.setDate(9, new java.sql.Date(obligacionDTO
							.getFechaVencimiento().getTime())); // fecha_vencimiento

					stat.setNull(16, Types.CHAR); // flag_cc
					stat.setInt(17, obligacionDTO.getIdReferenciaREC()); // nro_referencia

					stat.setNull(19, Types.INTEGER); // anno_documento
					stat.setNull(20, Types.INTEGER); // nro_documento_id

					if (obligacionDTO.getPapeletaId() != null) {
						stat.setInt(21, obligacionDTO.getPapeletaId());
					} else {
						stat.setNull(21, Types.INTEGER);
					}
					if (obligacionDTO.getNroPapeleta() != null) {

						stat.setString(22, obligacionDTO.getNroPapeleta());
					} else {
						stat.setNull(22, Types.VARCHAR);
					}
//					stat.setInt(28, obligacionDTO.getIdReferenciaREC());
					break;

				// caso GASTOS
				case GASTOS:

					stat.setDate(8, new java.sql.Date(obligacionDTO
							.getFechaLiquidacion().getTime())); // fecha_emision
					stat.setDate(9, new Date(Calendar.getInstance().getTime()
							.getTime())); // fecha_vencimiento

					stat.setNull(16, Types.CHAR); // flag_cc
					stat.setInt(17, obligacionDTO.getIdReferenciaREC()); // nro_referencia

					stat.setNull(19, Types.INTEGER); // anno_documento
					stat.setNull(20, Types.INTEGER); // nro_documento_id

					if (obligacionDTO.getPapeletaId() != null) {
						stat.setInt(21, obligacionDTO.getPapeletaId());
					} else {
						stat.setNull(21, Types.INTEGER);
					}
					if (obligacionDTO.getNroPapeleta() != null) {

						stat.setString(22, obligacionDTO.getNroPapeleta());
					} else {
						stat.setNull(22, Types.VARCHAR);
					}
//					stat.setInt(28, obligacionDTO.getIdReferenciaREC());
					break;

				// caso EPND
				case EPND:

					stat.setDate(8, new Date(Calendar.getInstance().getTime()
							.getTime())); // fecha_emision
					stat.setDate(9, new java.sql.Date(obligacionDTO
							.getFechaVencimiento().getTime())); // fecha_vencimiento

					stat.setNull(16, Types.CHAR); // flag_cc
					stat.setNull(17, Types.INTEGER); // nro_referencia

					stat.setNull(19, Types.INTEGER); // anno_documento
					stat.setNull(20, Types.INTEGER); // nro_documento_id
					stat.setNull(21, Types.INTEGER); // papeleta_id
					stat.setNull(22, Types.VARCHAR); // nro_papeleta
//					stat.setInt(28, obligacionDTO.getIdReferenciaREC());
					break;

				// caso MULTAS
				case MULTAS:

					stat.setDate(8, new Date(Calendar.getInstance().getTime()
							.getTime())); // fecha_emision
					stat.setDate(9, new Date(Calendar.getInstance().getTime()
							.getTime())); // fecha_vencimiento

					stat.setString(16,
							Integer.toString(ccTipoActo.getTipoActoId())); // flag_cc
					stat.setNull(17, Types.INTEGER);// stat.setNull(17, actoId);
													// //nro_referencia

					stat.setInt(19, obligacionDTO.getAnnoAfectacion()); // anno_documento
					stat.setInt(20, actoId); // nro_documento_id
					stat.setNull(21, Types.INTEGER); // papeleta_id
					stat.setNull(22, Types.VARCHAR); // nro_papeleta
//					stat.setInt(28, obligacionDTO.getIdReferenciaREC());
					break;

				case MULTAS_DRTPE:

					stat.setDate(8, new java.sql.Date(obligacionDTO
							.getFechaLiquidacion().getTime())); // fecha_emision
					stat.setDate(9, new Date(Calendar.getInstance().getTime()
							.getTime())); // fecha_vencimiento

					stat.setNull(16, Types.CHAR); // flag_cc
					stat.setInt(17, obligacionDTO.getIdReferenciaREC()); // nro_referencia

					stat.setInt(19, obligacionDTO.getAnnoAfectacion()); // anno_documento
					stat.setNull(20, Types.INTEGER); // nro_documento_id

					stat.setNull(21, Types.INTEGER); // papeleta_id
					stat.setNull(22, Types.VARCHAR); // nro_papeleta
//					stat.setInt(28, obligacionDTO.getIdReferenciaREC());
					break;
				/**
				 * Permitirá el cobro de Multas generadas en la MPC - Noviembre 2016
				 * */
				case MULTAS_MPC:

					stat.setDate(8, new java.sql.Date(obligacionDTO
							.getFechaLiquidacion().getTime())); // fecha_emision
					stat.setDate(9, new Date(Calendar.getInstance().getTime()
							.getTime())); // fecha_vencimiento

					stat.setNull(16, Types.CHAR); // flag_cc
					stat.setInt(17, obligacionDTO.getIdReferenciaREC()); // nro_referencia

					stat.setInt(19, obligacionDTO.getAnnoAfectacion()); // anno_documento
					stat.setNull(20, Types.INTEGER); // nro_documento_id

					stat.setNull(21, Types.INTEGER); // papeleta_id
					stat.setNull(22, Types.VARCHAR); // nro_papeleta
					break;
					
				case MULTAS_DRTC:

					stat.setDate(8, new java.sql.Date(obligacionDTO
							.getFechaLiquidacion().getTime())); // fecha_emision
					stat.setDate(9, new Date(Calendar.getInstance().getTime()
							.getTime())); // fecha_vencimiento

					stat.setNull(16, Types.CHAR); // flag_cc
					stat.setInt(17, obligacionDTO.getIdReferenciaREC()); // nro_referencia

					stat.setInt(19, obligacionDTO.getAnnoAfectacion()); // anno_documento
					stat.setNull(20, Types.INTEGER); // nro_documento_id

					stat.setNull(21, Types.INTEGER); // papeleta_id
					stat.setNull(22, Types.VARCHAR); // nro_papeleta
					
				}
				stat.execute();

				/********************** cc_acto_deuda **********************************************/
				/***** registro solo en el caso de que sea MULTA (Infracciones) **/
				if (obligacionDTO.getConceptoId() == MULTAS) {

					stat = con.prepareCall(sqlActoDeuda);

					// -- 1 <lote_id, int,>
					stat.setInt(1, 0);
					// -- 2 <acto_id, int,>
					stat.setInt(2, actoId);
					// -- 3 <concepto_id, int,>
					stat.setInt(3, obligacionDTO.getConceptoId());
					// -- 4 <subconcepto_id, int,>
					stat.setInt(4, obligacionDTO.getSubConceptoId());
					// -- 5 <anno_deuda, int,>
					stat.setInt(5, obligacionDTO.getAnnoAfectacion());
					// -- 6 <fecha_vencimiento, datetime,>
					stat.setDate(6, new java.sql.Date(obligacionDTO
							.getFechaInfraccion().getTime()));
					// -- 7 <interes_anual, numeric(18,2),>
					stat.setBigDecimal(7, new BigDecimal(0));
					// -- 8 <interes_capitalizado, numeric(18,2),>
					stat.setBigDecimal(8, new BigDecimal(0));
					// -- 9 <interes_mensual, numeric(18,2),>
					stat.setBigDecimal(9, new BigDecimal(0));
					// -- 10 <insoluto, numeric(18,2),>
					stat.setBigDecimal(10, obligacionDTO.getMonto());
					// -- 11 <reajuste, numeric(18,2),>
					stat.setBigDecimal(11, new BigDecimal(0));
					// -- 12 <area_uso, numeric(10,2),>
					stat.setBigDecimal(12, new BigDecimal(0));
					// -- 13 <deuda_id, int,>
					stat.setInt(13, deudaId);
					// -- 14<base_imponible, numeric(18,2),>
					stat.setBigDecimal(14, new BigDecimal(0));
					// -- 15<derecho_emision, numeric(18,2),>
					stat.setBigDecimal(15, new BigDecimal(0));
					// -- 16<costas, numeric(18,2),>
					stat.setNull(16, Types.NUMERIC);
					// -- 17 <total_debitos, numeric(18,2),>)
					stat.setBigDecimal(17, new BigDecimal(0));

					// -- 18 <usuario_id, int,>
					// -- 19 <estado, char(1),>
					// -- 20 <fecha_registro, datetime,>
					// -- 21 <terminal, varchar(20),>

					stat.execute();
				}

				/*********************** Insercion en la tabla cd_deuda_historico ******************************/
				deudaHistoricoId = ObtenerCorrelativoTabla(
						"cd_deuda_historica", 1);

				stat = con.prepareCall(sqlDeudaHistorico);
				stat.setInt(1, deudaId);
				stat.setInt(2, deudaHistoricoId);
				stat.setInt(3, TIPO_MOVIMIENTO); // tipo_movimiento_id =
													// generado
				stat.setInt(4, determinacionId);
				stat.setInt(5, personaId);
				stat.setDate(6, new Date(Calendar.getInstance().getTime()
						.getTime())); // fecha
										// movimiento
				stat.setInt(7, TIPO_DEUDA); // tipo deuda
				stat.setDate(8, new Date(Calendar.getInstance().getTime()
						.getTime())); // fecha
										// vencimiento
				stat.setBigDecimal(9, obligacionDTO.getMonto());
				stat.setBigDecimal(10, obligacionDTO.getMonto());

				stat.execute();
			}

			con.commit();

		} catch (SQLException e) {
			try {
				con.rollback();
				// delete todos los archivos movidos
			} catch (SQLException ex) {
				throw new SisatException(ex);
			}
			throw new SisatException(e.getMessage(), e.getCause());
		} catch (RuntimeException e) {
			try {
				con.rollback();
				// delete todos los archivos movidos
			} catch (SQLException ex) {
				throw new SisatException(ex);
			}
			throw new SisatException(e.getMessage(), e.getCause());
		} catch (Exception e) {
			try {
				con.rollback();
				// delete todos los archivos movidos
			} catch (SQLException ex) {
				throw new SisatException(ex);
			}
			throw new SisatException(e.getMessage(), e.getCause());
		} finally {

			try {
				// restauramos la conexion al autocommit
				if (con != null) {
					con.setAutoCommit(true);
				}
				// cerramos el objeto stat
				if (stat != null) {
					stat.close();
				}
			} catch (SQLException e) {
				throw new SisatException(e.getMessage(), e.getCause());
			}
		}
	}

	private String moverArchivo(ObligacionDTO obligacionDTO)
			throws SisatException {

		String path = ParameterLoader.getParameter("repositoriovalores");

		if (path == null || path.equals("")) {
			throw new SisatException(
					"No se encuentra configurado la ruta del repositorio de Valores, contáctese con el Administrador.");
		} else {
			File repositorio = new File(path);
			if (!repositorio.exists()) {
				throw new SisatException(
						"La ruta de acceso al repositorio de Valores es incorrecta, contáctese con el Administrador.");
			}
		}

		String name = path.concat(File.separator).concat(
				obligacionDTO.getNroValor());
		String extension = obligacionDTO.getFileUpload().getContentType()
				.split("/")[1];

		name = name.concat(".").concat(extension);

		File destFile = new File(name);

		try {
			copyFile(obligacionDTO.getFileUpload().getFile(), destFile);

			obligacionDTO.getFileUpload().getFile().delete();
		} catch (IOException e) {
			throw new SisatException(e.getMessage(), e.getCause());
		}

		return destFile.getName();
	}

	@SuppressWarnings("resource")
	private void copyFile(File sourceFile, File destFile) throws IOException {
		if (!destFile.exists()) {
			destFile.createNewFile();
		}

		FileChannel source = null;
		FileChannel destination = null;
		try {
			source = new FileInputStream(sourceFile).getChannel();
			destination = new FileOutputStream(destFile).getChannel();

			// previous code: destination.transferFrom(source, 0,
			// source.size());
			// to avoid infinite loops, should be:
			long count = 0;
			long size = source.size();
			while ((count += destination.transferFrom(source, count, size
					- count)) < size)
				;
		} finally {
			if (source != null) {
				source.close();
			}
			if (destination != null) {
				destination.close();
			}
		}
	}

	public boolean checkUnidadOrganica(String unidadOrganica)
			throws SisatException {

		int unidad_organica_id = Integer.parseInt(unidadOrganica);

		StringBuilder sb = new StringBuilder();

		sb.append("select COUNT(*)  from ");
		sb.append(SATParameterFactory.getDBNameScheme()).append(
				".gn_unidad gn_u ");
		sb.append("where gn_u.unidad_id = ");
		sb.append(unidad_organica_id);

		/**
		 * DCCD: Departamento de Control y Cobranza de la Deuda DFCP:
		 * Departamento de Fiscalización y Censo Predial
		 */
		sb.append(" and desc_corta in ('DCCD','DFCP')");

		String sql = sb.toString();

		int cant = 0;

		try {
			Query query = em.createNativeQuery(sql);

			cant = (Integer) query.getSingleResult();

		} catch (RuntimeException e) {

			throw new SisatException(e.getMessage(), e.getCause());
		}

		return (cant > 0) ? true : false;
	}

	@SuppressWarnings("unchecked")
	public List<Integer> getTipoDocumentoByDescripcion(
			List<String> listTipoDocumentoDescripcion) throws SisatException {

		StringBuilder sb = new StringBuilder();

		sb.append("select t.tipo_documento_id  from ");
		sb.append(SATParameterFactory.getDBNameScheme()).append(
				".gn_tipo_documento t ");

		sb.append("where t.descripcion in ('");

		for (String s : listTipoDocumentoDescripcion) {
			sb.append(s).append("','");
		}

		if (listTipoDocumentoDescripcion.size() > 0) {
			int p = sb.indexOf(",'");
			sb.delete(p - 1, p + 1);
		} else {
			sb.append("'");
		}

		sb.append(")");

		String sql = sb.toString();

		List<Integer> listId = new ArrayList<Integer>();

		try {
			Query query = em.createNativeQuery(sql);

			listId = query.getResultList();

		} catch (RuntimeException e) {

			throw new SisatException(e.getMessage());
		}

		return listId;
	}

	public List<MultaDTO> buscarMultas(ObligacionDTO obligacionDTO,
			Integer usuarioId) throws SisatException {

		String sql = "stp_ob_obtener_multas ?,?,?,?,?";

		PreparedStatement pst;

		List<MultaDTO> lstMultaDTOs = new ArrayList<MultaDTO>();
		try {
			pst = connect().prepareStatement(sql);
			pst.setTimestamp(1, DateUtil.getCurrentDate());
			pst.setInt(2, usuarioId);
			pst.setInt(3, obligacionDTO.getPersonaId());
			pst.setInt(4, 12);
			if (obligacionDTO.getSubConceptoId() != null) {
				pst.setInt(5, obligacionDTO.getSubConceptoId());
			} else {
				pst.setNull(5, Types.INTEGER);
			}

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				MultaDTO multaDTO = new MultaDTO();
				multaDTO.setPersonaId(rs.getInt("persona_id"));
				multaDTO.setDeudaId(rs.getInt("deuda_id"));
				multaDTO.setConceptoId(rs.getInt("concepto_id"));
				multaDTO.setSubConceptoId(rs.getInt("subconcepto_id"));
				multaDTO.setSubConcepto(rs.getString("subconcepto"));
				multaDTO.setNroRsMulta(rs.getString("nro_rs_multa"));
				multaDTO.setSubConcepto(rs.getString("subconcepto"));
				multaDTO.setAnho(rs.getInt("anyo"));
				multaDTO.setValorUit(rs.getBigDecimal("uit"));
				multaDTO.setPorcentajeSancion(rs.getBigDecimal("porcentaje"));
				multaDTO.setMonto(rs.getBigDecimal("insoluto"));
				multaDTO.setInteres(rs.getBigDecimal("interes"));
				multaDTO.setMontoSinDscto(rs.getBigDecimal("sub_total"));
				multaDTO.setMontoDescuento(rs.getBigDecimal("descuento"));
				multaDTO.setMontoConDscto(rs.getBigDecimal("total"));
				multaDTO.setFechaEmision(new java.util.Date(rs.getTimestamp(
						"fecha_emision").getTime()));
				multaDTO.setFechaVencimiento(new java.util.Date(rs
						.getTimestamp("fecha_vencimiento").getTime()));
				multaDTO.setUnidad(rs.getString("unidad"));
				multaDTO.setFechaNotificacion( rs.getTimestamp("fecha_notificacion") != null?(new java.util.Date(rs.getTimestamp("fecha_notificacion").getTime())):null);
				lstMultaDTOs.add(multaDTO);

			}

		} catch (SQLException e) {

			throw new SisatException(e.getMessage(), e.getCause());
		} catch (SisatException e) {

			throw e;
		}
		return lstMultaDTOs;
	}

	public List<DetencionDeudaDTO> buscarDetenciones(Integer personaId,Integer anioDeuda,Integer papeletaId)
			throws SisatException {

		List<DetencionDeudaDTO> lstDetencionDeudaDTOs = new ArrayList<DetencionDeudaDTO>();
		String sql = "stp_cc_obtener_deudas_coactiva ?,?,?";
		PreparedStatement pst;

		try {
			pst = connect().prepareStatement(sql);
			pst.setInt(1, personaId);
			pst.setInt(2, anioDeuda);
			pst.setInt(3, papeletaId);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				DetencionDeudaDTO detencionDeudaDTO = new DetencionDeudaDTO();

				detencionDeudaDTO.setPersonaId(rs.getInt("persona_id"));
				detencionDeudaDTO.setConceptoId(rs.getInt("concepto_id"));
				detencionDeudaDTO.setSubConceptoId(rs.getInt("subconcepto_id"));
				detencionDeudaDTO.setAnho(rs.getInt("anho"));
				detencionDeudaDTO.setDescripcionConcepto(rs
						.getString("descripcion_corta"));
				// detencionDeudaDTO.setFechaDetencion(rs.getDate("fecha_detencion"));
				if (rs.getTimestamp("fecha_detencion") != null) {
					detencionDeudaDTO.setFechaDetencion(new java.util.Date(rs
							.getTimestamp("fecha_detencion").getTime()));
				} else {
					detencionDeudaDTO.setFechaDetencion(null);
				}
				detencionDeudaDTO.setEstado(rs.getInt("estado"));
				detencionDeudaDTO.setUsuarioActualizacion(rs
						.getString("nombre_usuario"));

				if (rs.getTimestamp("fecha_actualizacion") != null) {
					detencionDeudaDTO.setFechaActualizacion(new java.util.Date(
							rs.getTimestamp("fecha_actualizacion").getTime()));
				} else {
					detencionDeudaDTO.setFechaActualizacion(null);
				}
				detencionDeudaDTO.setPlaca(rs.getString("placa"));
				detencionDeudaDTO.setNroPapeleta(rs.getString("nro_papeleta"));
				detencionDeudaDTO.setDeterminacionId(rs
						.getInt("determinacion_id"));

				lstDetencionDeudaDTOs.add(detencionDeudaDTO);
			}

		} catch (SQLException e) {

			throw new SisatException(e.getMessage(), e.getCause());
		} catch (SisatException e) {

			throw e;
		}

		return lstDetencionDeudaDTOs;
	}

	public List<DetencionDeudaDTO> buscarDetencionesPorPapeletaId(
			Integer papeletaId) throws SisatException {

		List<DetencionDeudaDTO> lstDetencionDeudaDTOs = new ArrayList<DetencionDeudaDTO>();

		String sql = "stp_cc_obtener_papeletas_detenidas ?";

		PreparedStatement pst;

		try {
			pst = connect().prepareStatement(sql);

			pst.setInt(1, papeletaId);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				DetencionDeudaDTO detencionDeudaDTO = new DetencionDeudaDTO();

				detencionDeudaDTO.setPersonaId(rs.getInt("persona_id"));
				detencionDeudaDTO.setConceptoId(rs.getInt("concepto_id"));
				detencionDeudaDTO.setSubConceptoId(rs.getInt("subconcepto_id"));
				detencionDeudaDTO.setAnho(rs.getInt("anho"));
				detencionDeudaDTO.setDescripcionConcepto(rs
						.getString("descripcion_corta"));
				detencionDeudaDTO.setFechaDetencion(new java.util.Date(rs
						.getTimestamp("fecha_detencion").getTime()));
				detencionDeudaDTO.setEstado(rs.getInt("estado"));
				detencionDeudaDTO.setUsuarioActualizacion(rs
						.getString("nombre_usuario"));

				if (rs.getTimestamp("fecha_actualizacion") != null) {
					detencionDeudaDTO.setFechaActualizacion(new java.util.Date(
							rs.getTimestamp("fecha_actualizacion").getTime()));
				} else {
					detencionDeudaDTO.setFechaActualizacion(null);
				}

				detencionDeudaDTO.setPlaca(rs.getString("placa"));

				detencionDeudaDTO.setNroPapeleta(rs.getString("nro_papeleta"));
				detencionDeudaDTO.setDeterminacionId(rs
						.getInt("determinacion_id"));

				lstDetencionDeudaDTOs.add(detencionDeudaDTO);
			}

		} catch (SQLException e) {

			throw new SisatException(e.getMessage(), e.getCause());
		} catch (SisatException e) {

			throw e;
		}

		return lstDetencionDeudaDTOs;
	}

	public void desactivarDetencion(Integer personaId, Integer conceptoId,
			Integer subConceptoId, Integer anho, Integer determinacionId,String nroExped,
			UserSession userSession) throws SisatException {
		try{
		CallableStatement cs = connect().prepareCall(
				"{call dbo.stp_desbloquea_deudas_coactiva(?,?,?,?,?,?,?,?)}");
			
			cs.setInt(1, personaId);
			cs.setInt(2, conceptoId);
			cs.setInt(3, subConceptoId);
			cs.setInt(4, anho);
			cs.setInt(5, determinacionId);
			cs.setString(6, nroExped);
			cs.setInt(7, userSession.getUsuarioId().intValue());
			cs.setString(8, userSession.getTerminal());

			cs.execute();

		} catch (SQLException e) {

			throw new SisatException(e.getMessage(), e.getCause());
		} catch (SisatException e) {

			throw e;
		}

	}

	public void desactivarDetencionPapeleta(String nroPapeleta,
			UserSession userSession) throws SisatException {

		StringBuffer sb = new StringBuffer();

		sb.append("update cc_detencion_deuda set estado = 2, usuario_id_actualizacion = ?, fecha_actualizacion= ?,terminal = ? ");
		sb.append(" where nro_papeleta = ? and concepto_id = 4 ");
		sb.append(" and estado = 1");

		PreparedStatement pst;

		try {
			pst = connect().prepareStatement(sb.toString());

			pst.setInt(1, userSession.getUsuarioId().intValue());
			pst.setTimestamp(2, DateUtil.getCurrentDate());
			pst.setString(3, userSession.getTerminal());
			pst.setString(4, nroPapeleta);

			pst.execute();

		} catch (SQLException e) {

			throw new SisatException(e.getMessage(), e.getCause());
		} catch (SisatException e) {

			throw e;
		}

	}

	public void activarDetencion(Integer personaId, Integer conceptoId,
			Integer subConceptoId, Integer anho, Integer determinacionId,String nroExped,
			UserSession userSession) throws SisatException {
		try {
		CallableStatement cs = connect().prepareCall(
				"{call dbo.stp_bloquea_deudas_coactiva(?,?,?,?,?,?,?,?)}");
			
		cs.setInt(1, personaId);
		cs.setInt(2, conceptoId);
		cs.setInt(3, subConceptoId);
		cs.setInt(4, anho);
		cs.setInt(5, determinacionId);
		cs.setString(6, nroExped);
		cs.setInt(7, userSession.getUsuarioId().intValue());
		cs.setString(8, userSession.getTerminal());

			cs.execute();

		} catch (SQLException e) {

			throw new SisatException(e.getMessage(), e.getCause());
		} catch (SisatException e) {

			throw e;
		}
	}

	public void notificarResolucionMulta(String nroActo, UserSession userSession)			throws SisatException {

		StringBuffer sb = new StringBuffer();

		sb.append("UPDATE cc_acto set comentario=?,fecha_notificacion=? where nro_acto=? ");
		

		PreparedStatement pst;

		try {
			pst = connect().prepareStatement(sb.toString());
			
			pst.setString(1, "Notificado en Servicios por "+userSession.getUsuario()+" desde "+userSession.getTerminal());
			pst.setTimestamp(2, DateUtil.getCurrentDate());
			pst.setString(3, nroActo);	
			
			pst.execute();

		} catch (SQLException e) {

			throw new SisatException(e.getMessage(), e.getCause());
		} catch (SisatException e) {

			throw e;
		}
	}

	public int generarResolucionMulta(Integer loteId, Integer personaId,
			Integer unidad, Integer usuarioId, String terminal,
			Integer subConceptoId) throws Exception {
		int salida = 0;
		try {
			CallableStatement cs = connect().prepareCall(
					"{call stp_cc_genera_multas(?, ?, ?, ?, ?, ?)}");
			cs.setInt(1, loteId);
			cs.setInt(2, personaId);
			cs.setInt(3, unidad);
			cs.setInt(4, usuarioId);
			cs.setString(5, terminal);
			cs.setInt(6, subConceptoId);
//			cs.setInt(7, loteVehId);
			cs.execute();

		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
		}
		return salida;
	}

	public void activarDetencionPapeleta(String nroPapeleta,
			UserSession userSession) throws SisatException {

		StringBuffer sb = new StringBuffer();

		sb.append("update cc_detencion_deuda set estado = 1, usuario_id_actualizacion = ?, fecha_actualizacion= ?,terminal = ? ");
		sb.append(" where nro_papeleta = ? and concepto_id = 4 ");
		sb.append(" and estado = 2");

		PreparedStatement pst;

		try {
			pst = connect().prepareStatement(sb.toString());

			pst.setInt(1, userSession.getUsuarioId().intValue());
			pst.setTimestamp(2, DateUtil.getCurrentDate());
			pst.setString(3, userSession.getTerminal());
			pst.setString(4, nroPapeleta);

			pst.execute();

		} catch (SQLException e) {

			throw new SisatException(e.getMessage(), e.getCause());
		} catch (SisatException e) {

			throw e;
		}

	}
	
	public List<DtFechaVencimiento> getFechaVencimiento(int conceptoId,int periodo) {
		try {
			StringBuilder SQL = new StringBuilder(
					"SELECT fv.vencimiento_id, fv.cuota, fv.fecha_vencimiento FROM ").append(Constante.schemadb).append(".dt_fecha_vencimiento fv ");
			SQL.append("WHERE fv.concepto_id=").append(conceptoId);
			SQL.append(" AND fv.periodo=").append(periodo).append(" AND fv.estado='1' ORDER BY fv.cuota");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();

			List<DtFechaVencimiento> lista = new ArrayList<DtFechaVencimiento>();

			while (rs.next()) {
				DtFechaVencimiento fv = new DtFechaVencimiento();
				fv.setVencimientoId(rs.getInt("vencimiento_id"));
				fv.setCuota(rs.getInt("cuota"));
				fv.setFechaVencimiento(rs.getTimestamp("fecha_vencimiento"));
				lista.add(fv);
			}
			return lista;
		} catch (Exception ex) {

		}
		return null;
	}
	
	public List<SubConceptoDTO> getSubConceptoMULTA(int tipo)throws SisatException {

		List<SubConceptoDTO> listSubConceptoDTOs = new ArrayList<SubConceptoDTO>();

		StringBuilder sb = new StringBuilder(200);

		sb.append("SELECT concepto_id, ");
		sb.append("subconcepto_id, ");
		sb.append("descripcion, ");
		sb.append("etiqueta ");
		sb.append("FROM ");
		sb.append(SATParameterFactory.getDBNameScheme());
		sb.append(".gn_subconcepto ");
		sb.append("WHERE concepto_id = ? "); // id de concepto Multas MPC
		sb.append("ORDER BY descripcion_corta");

		String sql = sb.toString();

		PreparedStatement pst;

		try {
			pst = connect().prepareStatement(sql);
			pst.setInt(1,tipo);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				SubConceptoDTO subConceptoDTO = new SubConceptoDTO();

				subConceptoDTO.setConceptoId(rs.getInt(1));
				subConceptoDTO.setSubconceptoId(rs.getInt(2));
				subConceptoDTO.setDescripcion(rs.getString(3));
				subConceptoDTO.setDescripcionCorta(rs.getString(4));

				listSubConceptoDTOs.add(subConceptoDTO);
			}

		} catch (SQLException e) {

			throw new SisatException(e.getMessage(), e.getCause());
		} catch (SisatException e) {

			throw e;
		}

		return listSubConceptoDTOs;
	}

}
