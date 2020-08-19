package com.sat.sisat.cobranzacoactiva.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.sat.sisat.cobranzacoactiva.dto.TipoDocumentoDescargo;
import com.sat.sisat.cobranzacoactiva.dto.CarteraExigibilidad;
import com.sat.sisat.cobranzacoactiva.dto.CoCartera;
import com.sat.sisat.cobranzacoactiva.dto.ControlExpediente;
import com.sat.sisat.cobranzacoactiva.dto.GeneracionMasivaRecDTO;
import com.sat.sisat.cobranzacoactiva.dto.DetalleCostas;
import com.sat.sisat.cobranzacoactiva.dto.EjecutorCoactivo;
import com.sat.sisat.cobranzacoactiva.dto.ExpedienteCoactivo;
import com.sat.sisat.cobranzacoactiva.dto.FindCcLoteDetalleActoExp;
import com.sat.sisat.cobranzacoactiva.dto.FindCcLoteExigible;
import com.sat.sisat.cobranzacoactiva.dto.FindCcRecHistorico;
import com.sat.sisat.cobranzacoactiva.dto.FindCcRecTipo;
import com.sat.sisat.cobranzacoactiva.dto.GestionCostas;
import com.sat.sisat.cobranzacoactiva.dto.GestionEventos;
import com.sat.sisat.cobranzacoactiva.dto.GestionValores;
import com.sat.sisat.cobranzacoactiva.dto.InformeTransferido;
import com.sat.sisat.cobranzacoactiva.dto.InformeTransferidoDetalle;
import com.sat.sisat.cobranzacoactiva.dto.ListadoArea;
import com.sat.sisat.cobranzacoactiva.dto.ListadoEstadoTransferencia;
import com.sat.sisat.cobranzacoactiva.dto.SituacionCartera;
import com.sat.sisat.cobranzacoactiva.dto.SituacionDeuda;
import com.sat.sisat.cobranzacoactiva.dto.SituacionExigibilidad;
import com.sat.sisat.common.dao.GeneralDao;
import com.sat.sisat.common.dto.GenericDTO;
import com.sat.sisat.common.security.UserSession;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.SATParameterFactory;
import com.sat.sisat.common.util.Util;
import com.sat.sisat.controlycobranza.dto.FindCcLote;
import com.sat.sisat.controlycobranza.dto.FindCcRec;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.obligacion.dto.ObligacionDTO;
import com.sat.sisat.persistence.entity.GnRemate;

public class CobranzaCoactivaBusinessDao extends GeneralDao {
	
	
	public List<TipoDocumentoDescargo> getAllTipoDocumento() throws Exception
	{
		List<TipoDocumentoDescargo> lista = new ArrayList<TipoDocumentoDescargo>(); 
		
		try {
			String SQL = "dbo.stp_co_listado_tipo_documento";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());			
			ResultSet rs = pst.executeQuery();			
			TipoDocumentoDescargo obj;
			
			while (rs.next()) {
				obj = new TipoDocumentoDescargo();
				obj.setTipoDocumentoId(rs.getInt("tipo_documento_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				obj.setDescripcionCorta(rs.getString("descripcion_corta"));
				obj.setCodigoDocumento(rs.getInt("codigo_documento"));
				obj.setEstado(rs.getString("estado"));
				lista.add(obj);
			}
			
		} catch (Exception e) {
			throw (e);
		}
		
		return lista;
		
		
	}
	
	
	//Descargamos costos / gastos
	public Integer descargaCosta (GestionCostas gestioncostas,Integer tipoDocumentoId,String nroDocumento,Date fechaDocumento,String observacion,String terminal,Integer usuarioId) throws Exception
	{
		
		try {
			CallableStatement cs = connect().prepareCall("{call dbo.stp_co_descargo_costas(?,?,?,?,?,?,?)}");			
			
			cs.setInt(1, gestioncostas.getDeudaId() );
			cs.setInt(2, tipoDocumentoId);
			cs.setString(3, nroDocumento);
			cs.setDate(4, DateUtil.dateToSqlDate(fechaDocumento));
			cs.setString(5,observacion );
			cs.setString(6, terminal);
			cs.setInt(7, usuarioId);
			cs.execute();
			
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
			return 0;
		}
		
		return 1;
		
	}
	
	
	//Lista todas las REC activas, por generacion_masiva_id
	public List<FindCcRec> listaRecMasiva(Integer generacionmasivaid)
	throws Exception{
		
		List<FindCcRec> lista =new ArrayList<FindCcRec>();
		StringBuffer sql = new StringBuffer();;
		sql.append("select  rec_id,expediente_id,nro_expediente,persona_id,apellidos_nombres,"
				+ "direccion,anno_rec,deuda_total,nro_rec,fecha_emision,tipo_rec_id,tipo_documento_id,"
				+ "generacion_masiva_id  from tv_co_rec_data_basica where generacion_masiva_id=? order by rec_id");		
		
		PreparedStatement pst;
		
		try 
		{
			FindCcRec obj;
			
			pst = connect().prepareStatement(sql.toString());
			pst.setInt(1, generacionmasivaid);
			ResultSet rs = pst.executeQuery();
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");			

			while (rs.next()) {
				obj=new FindCcRec();
				
				obj.setRecId(rs.getInt(1));
				obj.setExpedienteId(rs.getInt(2));
				obj.setNroExpediente(rs.getString(3));
				obj.setPersonaId(rs.getInt(4));
				obj.setApellidosNombresPersona(rs.getString(5));
				obj.setDireccion(rs.getString(6));				
				obj.setAnnoRec(rs.getInt(7));
				obj.setDeudaTotal(rs.getBigDecimal(8));
				obj.setNroRec(rs.getString(9));
				obj.setFechaEmision(rs.getTimestamp(10));
				obj.setTipoRecId(rs.getInt(11));
				obj.setTipoDocumentoId(rs.getInt(12));				
				obj.setFechaEmisionFormato(sdf.format(obj.getFechaEmision()));
				
				lista.add(obj);
				
							
			}
			
		} 
		catch (Exception e) {
			throw (e);
		}
		
		return lista;
		
	}
	
	
	//Tributos
	public List<GenericDTO> getTributos ()
			throws Exception {

				List<GenericDTO> lista=new ArrayList<GenericDTO>();		
						
				StringBuffer sql = new StringBuffer();;
				sql.append("select concepto_id,descripcion_corta,case when concepto_id =4 then 2 else 1 end as materia_id "
						+ "from gn_concepto where concepto_id in (1,2,3,4)");		
				
				
				PreparedStatement pst;
				
				try {
					GenericDTO obj;
					
					pst = connect().prepareStatement(sql.toString());
					ResultSet rs = pst.executeQuery();
					Integer materia_id;

					while (rs.next()) {
						obj=new GenericDTO();
						
						obj.setId(rs.getInt(1));				
						obj.setDescripcion(rs.getString(2));
						
						materia_id=rs.getInt(3);
						//FALSE = NO TRIBUTARIA
						//TRUE  = TRIBUTARIA						
						obj.setEstado( materia_id != 4 );
						
						lista.add(obj);
					}
					
				} catch (Exception e) {
					throw (e);
				}
				
				return lista;
	}
	
	
	//Lista todas las generaciones de rec masivamente por usuario.
	public List<GeneracionMasivaRecDTO> getGeneracionMasivaRec (Integer usuario_id)
	throws Exception {

		List<GeneracionMasivaRecDTO> lista=new ArrayList<GeneracionMasivaRecDTO>();		
				
		StringBuffer sql = new StringBuffer();;
		sql.append("select generacion_masiva_id,fecha,hora,g.tipo_rec_id,"
				+ "g.usuario_id ,descripcion,materia_id,cantidad_rec,archivo_jasper  "
				+ "from tv_co_generacion_masiva_rec g inner join co_jasper_tipo_rec j on g.jasper_id=j.jasper_id order by fecha desc, hora desc");		
		
		
		PreparedStatement pst;
		
		try {
			GeneracionMasivaRecDTO obj;
			
			pst = connect().prepareStatement(sql.toString());
			//pst.setInt(1, usuario_id);
			ResultSet rs = pst.executeQuery();
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String  texto,fechaConFormato  ;

			while (rs.next()) {
				obj=new GeneracionMasivaRecDTO();
				
				obj.setGeneracionMasivaId(rs.getInt(1));				
				obj.setFecha(rs.getDate(2));				
				obj.setHora(rs.getTime(3));				
				obj.setTipoRecId(rs.getInt(4));

				obj.setUsuarioId(rs.getInt(5));
				obj.setDescripcion(rs.getString(6));	
				obj.setMateriaId(rs.getInt(7));
				obj.setCantidadRec(rs.getInt(8));
				
				obj.setArchivoJasper(rs.getString(9));

				fechaConFormato = sdf.format(obj.getFecha());

				texto=obj.getGeneracionMasivaId().toString() + fechaConFormato +" Tipo: "+ obj.getDescripcion()+" ("+obj.getCantidadRec().toString()+")";
				
				obj.setTextoMostrar(texto);						
				lista.add(obj);
			}
			
		} catch (Exception e) {
			throw (e);
		}
		
		return lista;
		
	}
	
	public Boolean contribEnCobranzaCoactiva(Integer personaId)
			throws SisatException {
		Boolean respuesta = Boolean.FALSE;
		String sql = "select case when COUNT(*) > 0 then 1 else 0 end from cd_deuda where persona_id = ? and flag_cc = 1 AND flag_detencion=1";

		// "select case when COUNT(*) > 0 then 1 else 0 end from cc_detencion_deuda where persona_id = ? and estado = 1";

		PreparedStatement pst;
		try {
			pst = connect().prepareStatement(sql);
			pst.setInt(1, personaId);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				respuesta = rs.getBoolean(1);
			}
		} catch (SQLException e) {

			throw new SisatException(e.getMessage());
		} catch (SisatException e) {

			throw e;
		}

		return respuesta;
	}

	public Boolean resultadoDjsSinDeterminar(Integer personaId)
			throws SisatException, SQLException {

		Boolean respuesta = Boolean.FALSE;

		try {

			String SQL = "spt_cc_verificaDjsSinDeterminar ?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, personaId);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				respuesta = rs.getBoolean(1);

			}
		} catch (SQLException e) {

			throw new SisatException(e.getMessage());

		}
		return respuesta;
	}

	public List<FindCcLoteDetalleActoExp> getAllFindCcLoteExp(Integer lote_id)
			throws Exception {
		List<FindCcLoteDetalleActoExp> list = new ArrayList<FindCcLoteDetalleActoExp>();

		try {
			StringBuilder SQL = new StringBuilder(
					"exec dbo.stp_getAllDeudaExigibleDetalle ?");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, lote_id);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				FindCcLoteDetalleActoExp obj = new FindCcLoteDetalleActoExp();

				obj.setDeudaExigibleId(rs.getInt("deuda_exigible_id"));
				obj.setLoteId(rs.getInt("lote_id"));
				obj.setTipoActoId(rs.getInt("tipo_acto_id"));
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setActoId(rs.getInt("acto_id"));
				obj.setTipoActo(rs.getString("descripcion"));
				obj.setApellidosNombres(rs.getString("apellidos_nombres"));
				obj.setTipoActoDescripcionCorta(rs
						.getString("descripcion_corta"));
				obj.setNroActo(rs.getString("nro_acto"));
				obj.setAnnoLote(rs.getInt("anno_lote"));
				obj.setMontoDeuda(rs.getBigDecimal("monto_deuda"));
				obj.setAnnoDeuda(rs.getInt("anno_deuda"));
				obj.setDeuda(rs.getBigDecimal("deuda"));
				obj.setPago(rs.getBigDecimal("pago"));
				obj.setConceptoId(rs.getInt("concepto_id"));
				obj.setConcepto(rs.getString("concepto"));
				obj.setNroDocumento(rs.getString("nroDocumento"));
				obj.setDireccion(rs.getString("direccion"));
				obj.setSector(rs.getString("sector"));
				obj.setEstado_deuda(rs.getString("estado_deuda"));
				obj.setNroExpediente(rs.getString("nro_expediente"));
				obj.setLoteRecId(rs.getInt("lote_rec_id"));
				obj.setTipoDocumentoId(rs.getInt("tipo_documento_id"));
				obj.setFechaPago(rs.getTimestamp("fecha_pago"));
				obj.setUltimaRecEmitida(rs.getString("ultima_rec"));
				obj.setPapeletaId(rs.getInt("papeleta_id"));
				obj.setNroPapeleta(rs.getString("numero_papeleta"));
				obj.setUltimaTipoRecId(rs.getInt("tipo_rec_id"));
				obj.setFechaNotificacion(rs.getTimestamp("fecha_notificacion"));
				obj.setEstadoExpediente(rs.getString("estado_expediente"));
				if (obj.getTipoActoId()==10 && obj.getConceptoId()==2){
					obj.setPlaca(rs.getString("placa"));
				}
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public List<FindCcLoteExigible> getAllFindLote() throws Exception {
		List<FindCcLoteExigible> list = new ArrayList<FindCcLoteExigible>();

		try {
			String SQL = new String("stp_getAllDeudaExigible");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				FindCcLoteExigible obj = new FindCcLoteExigible();
				obj.setLoteId(rs.getInt("lote_id"));
				obj.setAnnoLote(rs.getInt("anno_lote"));
				obj.setDescripcionCorta(rs.getString("descripcion_corta"));
				obj.setTipoActoId(rs.getInt("tipo_acto_id"));
				obj.setFechaRegistro(rs.getString("fecha_registro"));
				obj.setUsuario(rs.getString("nombre_usuario"));
				obj.setTipoActoAnterior(rs.getString("concepto_tipo_acto"));
				obj.setHoraRegistro(rs.getString("hora_registro"));
				obj.setCantidadValores(rs.getInt("total_registros"));
				obj.setMontoDeuda(rs.getBigDecimal("monto_deuda"));
				obj.setEstadoLote(rs.getString("estado_lote"));
				obj.setNroRegConExpediente(rs.getInt("con_expediente"));

				list.add(obj);

			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public List<FindCcLoteDetalleActoExp> getAllExpedientesAcumulados(
			Integer personaId) throws Exception {
		List<FindCcLoteDetalleActoExp> list = new ArrayList<FindCcLoteDetalleActoExp>();

		try {
			String SQL = new String("stp_getAllExpedientesAcumulados ?");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, personaId);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				FindCcLoteDetalleActoExp obj = new FindCcLoteDetalleActoExp();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setConceptoId(rs.getInt("concepto_id"));
				obj.setLoteRecId(rs.getInt("lote_id"));
				obj.setDeudaExigibleId(rs.getInt("deuda_exigible_id"));
				obj.setLoteIdAnterior(rs.getInt("lote_exigible_id"));
				obj.setNroExpediente(rs.getString("nro_expediente"));
				obj.setAnnoDeuda(rs.getInt("anno_deuda"));
				obj.setMontoDeuda(rs.getBigDecimal("deuda_total"));
				obj.setTipoActoDescripcionCorta(rs
						.getString("descripcion_corta"));
				obj.setPlaca(rs.getString("placa"));
				obj.setNroPapeleta(rs.getString("nro_papeleta"));
				obj.setPapeletaId(rs.getInt("papeleta_id"));
				obj.setApellidosNombres(rs.getString("apellidos_nombres"));
				obj.setActoId(rs.getInt("acto_id"));
				obj.setTipoDocumentoId(rs.getInt("tipo_documento_id"));
				obj.setEstado_deuda(rs.getString("estado_deuda"));
				obj.setNroActo(rs.getString("nro_acto"));
				obj.setEstadoExpediente(rs.getString("estado_expediente"));
				obj.setMotivoCancelacion(rs.getString("motivo_cancelacion"));
				obj.setResolucionCancelacion(rs
						.getString("resolucion_cancelacion"));
				obj.setFechaCancelacion(rs.getTimestamp("fecha_cancelacion"));
				obj.setUsuarioCancelacion(rs.getString("usuario_cancelacion"));
				list.add(obj);

			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public List<FindCcLoteDetalleActoExp> getAllExpedientesXPlaca(String placa)
			throws Exception {
		List<FindCcLoteDetalleActoExp> list = new ArrayList<FindCcLoteDetalleActoExp>();

		try {
			String SQL = new String("stp_getAllExpedientesAcumuladosXPlaca ?");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setString(1, placa);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				FindCcLoteDetalleActoExp obj = new FindCcLoteDetalleActoExp();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setConceptoId(rs.getInt("concepto_id"));
				obj.setLoteRecId(rs.getInt("lote_id"));
				obj.setDeudaExigibleId(rs.getInt("deuda_exigible_id"));
				obj.setLoteIdAnterior(rs.getInt("lote_exigible_id"));
				obj.setNroExpediente(rs.getString("nro_expediente"));
				obj.setAnnoDeuda(rs.getInt("anno_deuda"));
				obj.setMontoDeuda(rs.getBigDecimal("deuda_total"));
				obj.setTipoActoDescripcionCorta(rs.getString("descripcion_corta"));
				obj.setPlaca(rs.getString("placa"));
				obj.setNroPapeleta(rs.getString("nro_papeleta"));
				obj.setPapeletaId(rs.getInt("papeleta_id"));
				obj.setApellidosNombres(rs.getString("apellidos_nombres"));
				obj.setActoId(rs.getInt("acto_id"));
				obj.setTipoDocumentoId(rs.getInt("tipo_documento_id"));
				obj.setEstado_deuda(rs.getString("estado_deuda"));
				obj.setNroActo(rs.getString("nro_acto"));
				obj.setEstadoExpediente(rs.getString("estado_expediente"));
				obj.setMotivoCancelacion(rs.getString("motivo_cancelacion"));
				obj.setResolucionCancelacion(rs.getString("resolucion_cancelacion"));
				obj.setFechaCancelacion(rs.getTimestamp("fecha_cancelacion"));
				obj.setUsuarioCancelacion(rs.getString("usuario_cancelacion"));
				list.add(obj);

			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public List<FindCcLoteDetalleActoExp> getAllDeudaExigibleOpPredialVehicular(
			Integer tipoActo, Integer conceptoId, Integer anio,
			BigDecimal montoMinimo, Integer flagUbicacion) throws Exception {
		List<FindCcLoteDetalleActoExp> list = new ArrayList<FindCcLoteDetalleActoExp>();
		try {
			String SQL = "sp_deuda_exigible_op_predial_vehicular ?,?,?,?,?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, tipoActo);
			pst.setInt(2, conceptoId);
			pst.setInt(3, anio);
			pst.setBigDecimal(4, montoMinimo);
			pst.setInt(5, flagUbicacion);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FindCcLoteDetalleActoExp obj = new FindCcLoteDetalleActoExp();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setApellidosNombres(rs.getString("datos_persona"));
				obj.setDireccion(rs.getString("direccion_completa"));
				obj.setTipoActo(rs.getString("tipo_acto"));
				obj.setNroActo(rs.getString("nro_acto"));
				obj.setAnnoDeuda(rs.getInt("anno_acto"));
				obj.setMontoDeuda(rs.getBigDecimal("monto_deuda"));
				obj.setFechaNotificacion(rs.getTimestamp("fecha_notificacion"));
				obj.setEstado_deuda(rs.getString("estado_deuda"));
				obj.setObservacion(rs.getString("observacion"));
				obj.setActoId(rs.getInt("acto_id"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public List<FindCcLoteDetalleActoExp> getAllDeudaExigibleRdArbitriosyRm(
			Integer tipoActo, Integer conceptoId, Integer anio,
			BigDecimal montoMinimo, Integer flagUbicacion) throws Exception {
		List<FindCcLoteDetalleActoExp> list = new ArrayList<FindCcLoteDetalleActoExp>();
		try {
			String SQL = "sp_deuda_exigible_rd_arbitrios_rm ?,?,?,?,?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, tipoActo);
			pst.setInt(2, conceptoId);
			pst.setInt(3, anio);
			pst.setBigDecimal(4, montoMinimo);
			pst.setInt(5, flagUbicacion);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FindCcLoteDetalleActoExp obj = new FindCcLoteDetalleActoExp();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setApellidosNombres(rs.getString("datos_persona"));
				obj.setDireccion(rs.getString("direccion_completa"));
				obj.setTipoActo(rs.getString("tipo_acto"));
				obj.setNroActo(rs.getString("nro_acto"));
				obj.setAnnoDeuda(rs.getInt("anno_acto"));
				obj.setMontoDeuda(rs.getBigDecimal("monto_deuda"));
				obj.setFechaNotificacion(rs.getTimestamp("fecha_notificacion"));
				obj.setEstado_deuda(rs.getString("estado_deuda"));
				obj.setObservacion(rs.getString("observacion"));
				obj.setActoId(rs.getInt("acto_id"));

				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public List<FindCcLoteDetalleActoExp> getAllDeudaExigibleRdPredial(
			Integer tipoActo, Integer conceptoId, Integer anio,
			BigDecimal montoMinimo, Integer flagUbicacion) throws Exception {
		List<FindCcLoteDetalleActoExp> list = new ArrayList<FindCcLoteDetalleActoExp>();
		try {
			String SQL = "sp_deuda_exigible_rd_predial ?,?,?,?,?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, tipoActo);
			pst.setInt(2, conceptoId);
			pst.setInt(3, anio);
			pst.setBigDecimal(4, montoMinimo);
			pst.setInt(5, flagUbicacion);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FindCcLoteDetalleActoExp obj = new FindCcLoteDetalleActoExp();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setApellidosNombres(rs.getString("datos_persona"));
				obj.setDireccion(rs.getString("direccion_completa"));
				obj.setTipoActo(rs.getString("tipo_acto"));
				obj.setNroActo(rs.getString("nro_acto"));
				obj.setAnnoDeuda(rs.getInt("anno_acto"));
				obj.setMontoDeuda(rs.getBigDecimal("monto_deuda"));
				obj.setFechaNotificacion(rs.getTimestamp("fecha_notificacion"));
				obj.setEstado_deuda(rs.getString("estado_deuda"));
				obj.setObservacion(rs.getString("observacion"));
				obj.setActoId(rs.getInt("acto_id"));

				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public List<FindCcLoteDetalleActoExp> getAllDeudaExigibleRs(
			Integer tipoActo, Integer conceptoId, Integer anio,
			BigDecimal montoMinimo, Integer flagUbicacion) throws Exception {
		List<FindCcLoteDetalleActoExp> list = new ArrayList<FindCcLoteDetalleActoExp>();
		try {
			String SQL = "sp_deuda_exigible_rs ?,?,?,?,?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, tipoActo);
			pst.setInt(2, conceptoId);
			pst.setInt(3, anio);
			pst.setBigDecimal(4, montoMinimo);
			pst.setInt(5, flagUbicacion);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FindCcLoteDetalleActoExp obj = new FindCcLoteDetalleActoExp();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setApellidosNombres(rs.getString("datos_persona"));
				obj.setDireccion(rs.getString("direccion_completa"));
				obj.setTipoActo(rs.getString("tipo_acto"));
				obj.setNroActo(rs.getString("nro_acto"));
				obj.setAnnoDeuda(rs.getInt("anno_acto"));
				obj.setMontoDeuda(rs.getBigDecimal("monto_deuda"));
				obj.setFechaNotificacion(rs.getTimestamp("fecha_notificacion"));
				obj.setEstado_deuda(rs.getString("estado_deuda"));
				obj.setObservacion(rs.getString("observacion"));
				obj.setActoId(rs.getInt("acto_id"));

				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public List<FindCcLote> getAllFindCcLoteDeudaExigible(Integer loteId,Integer annoLote) throws Exception {
		List<FindCcLote> list = new ArrayList<FindCcLote>(); 
		
		try {
			String SQL = "dbo.reporte_ccLoteDeudaExigible ?,?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, loteId==null?0:loteId);
			pst.setInt(2, annoLote==null?0:annoLote);
			
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FindCcLote obj = new FindCcLote();
				obj.setLoteId(rs.getInt("lote_id"));
				obj.setTipoLoteId(rs.getInt("tipo_lote_id"));
				obj.setAnnoLote(rs.getInt("anno_lote"));
				obj.setFechaRegistro(rs.getTimestamp("fecha_registro"));
				obj.setHoraRegistro(rs.getString("hora_registro"));
				obj.setTipoLote(rs.getString("tipo_lote"));
				obj.setTipoActo(rs.getString("tipo_acto"));
				obj.setTipoActoId(rs.getInt("tipo_acto_id"));
				obj.setConcepto(rs.getString("concepto"));
				obj.setEstadoTipoLoteGeneracion(rs
						.getString("estado_tipo_lote_generacion"));
				obj.setEstadoGeneracion(rs.getString("estado_generacion"));
				obj.setTipo_lote_generacion(rs
						.getString("tipo_lote_generacion"));
				obj.setFlag_generacion(rs.getString("flag_generacion"));
				obj.setTotalDeuda(rs.getBigDecimal("monto_deuda"));
				obj.setNroDocumentos(rs.getInt("total_registros"));
				// obj.setFlagUbicables(rs.getString("flag_ubicables"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public int registrarActoDeudaExigibleOPPredialVehicular(Integer loteId,
			Integer periodo, Integer conceptoId, Integer tipoActoId,
			BigDecimal montoMinimo, Integer flagUbicable, Integer usuarioId,
			String terminal) throws Exception {
		int salida = 0;
		try {
			CallableStatement cs = connect()
					.prepareCall(
							"{call dbo.stp_genera_deuda_exigible_op_predial_vehicular(?,?,?,?,?,?,?,?)}");
			cs.setInt(1, loteId);
			cs.setInt(2, periodo);
			cs.setInt(3, conceptoId);
			cs.setInt(4, tipoActoId);
			cs.setBigDecimal(5, montoMinimo);
			cs.setInt(6, flagUbicable);
			cs.setInt(7, usuarioId);
			cs.setString(8, terminal);
			cs.execute();
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
		}
		return salida;
	}

	public int registrarActoDeudaExigibleRdArbitriosRm(Integer loteId,
			Integer periodo, Integer conceptoId, Integer tipoActoId,
			BigDecimal montoMinimo, Integer flagUbicable, Integer usuarioId,
			String terminal) throws Exception {
		int salida = 0;
		try {
			CallableStatement cs = connect()
					.prepareCall(
							"{call dbo.stp_genera_deuda_exigible_rd_arbitrios_rm(?,?,?,?,?,?,?,?)}");
			cs.setInt(1, loteId);
			cs.setInt(2, periodo);
			cs.setInt(3, conceptoId);
			cs.setInt(4, tipoActoId);
			cs.setBigDecimal(5, montoMinimo);
			cs.setInt(6, flagUbicable);
			cs.setInt(7, usuarioId);
			cs.setString(8, terminal);
			cs.execute();
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
		}
		return salida;
	}

	public int registrarActoDeudaExigibleRdPredial(Integer loteId,
			Integer periodo, Integer conceptoId, Integer tipoActoId,
			BigDecimal montoMinimo, Integer flagUbicable, Integer usuarioId,
			String terminal) 
					throws Exception {
		int salida = 0;
		try {
			CallableStatement cs = connect()
					.prepareCall(
							"{call dbo.stp_genera_deuda_exigible_rd_predial(?,?,?,?,?,?,?,?)}");
			cs.setInt(1, loteId);
			cs.setInt(2, periodo);
			cs.setInt(3, conceptoId);
			cs.setInt(4, tipoActoId);
			cs.setBigDecimal(5, montoMinimo);
			cs.setInt(6, flagUbicable);
			cs.setInt(7, usuarioId);
			cs.setString(8, terminal);
			cs.execute();
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
		}
		return salida;
	}

	public int registrarActoDeudaExigibleRs(Integer loteId, Integer periodo,
			Integer conceptoId, Integer tipoActoId, BigDecimal montoMinimo,
			Integer flagUbicable, Integer usuarioId, String terminal)
			throws Exception {
		int salida = 0;
		try {
			CallableStatement cs = connect().prepareCall(
					"{call dbo.stp_genera_deuda_exigible_rs(?,?,?,?,?,?,?,?)}");
			cs.setInt(1, loteId);
			cs.setInt(2, periodo);
			cs.setInt(3, conceptoId);
			cs.setInt(4, tipoActoId);
			cs.setBigDecimal(5, montoMinimo);
			cs.setInt(6, flagUbicable);
			cs.setInt(7, usuarioId);
			cs.setString(8, terminal);
			cs.execute();
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
		}
		return salida;
	}

	public List<FindCcLoteDetalleActoExp> getAllFindDetalleFinalDeudaExigible(
			Integer loteId) throws Exception {
		List<FindCcLoteDetalleActoExp> list = new ArrayList<FindCcLoteDetalleActoExp>();

		try {
			StringBuilder SQL = new StringBuilder(
					"exec dbo.stp_getAllFinalDeudaExigible ?");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, loteId);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				FindCcLoteDetalleActoExp obj = new FindCcLoteDetalleActoExp();
				obj.setDeudaExigibleId(rs.getInt("deuda_exigible_id"));
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setApellidosNombres(rs.getString("datos_persona"));
				obj.setDireccion(rs.getString("direccion_completa"));
				obj.setTipoActo(rs.getString("tipo_acto"));
				obj.setNroActo(rs.getString("nro_acto"));
				obj.setAnnoDeuda(rs.getInt("anno_acto"));
				obj.setMontoDeuda(rs.getBigDecimal("monto_deuda"));
				obj.setFechaNotificacion(rs.getTimestamp("fecha_notificacion"));
				obj.setEstado_deuda(rs.getString("estado_deuda"));
				obj.setActoId(rs.getInt("acto_id"));
				obj.setLoteId(rs.getInt("lote_id"));
				obj.setEstadoDeudaExigible(rs.getString("estado_exigible"));
				obj.setFechaPago(rs.getTimestamp("fecha_pago"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public boolean eliminarDeudaExigible(Integer deudaExigibleId,
			Integer loteId, Integer actoId, String administrado,
			String nroActo, String motivodescargo, Integer usuarioId,
			String terminal) throws Exception {
		boolean result = false;
		try {
			CallableStatement cs = connect().prepareCall("{call dbo.sp_elimina_deuda_exigible(?,?,?,?,?,?,?,?)}");
			cs.setInt(1, deudaExigibleId);
			cs.setInt(2, loteId);
			cs.setInt(3, actoId);
			cs.setString(4, administrado);
			cs.setString(5, nroActo);
			cs.setString(6, motivodescargo);
			cs.setInt(7, usuarioId);
			cs.setString(8, terminal);

			result = cs.execute();
		} catch (Exception e) {
			throw (e);
		}
		return result;
	}

	public List<FindCcRecHistorico> getAllFindRecHistorico(Integer loteId,
			Integer deudaExId, Integer personaId) throws Exception {
		List<FindCcRecHistorico> list = new ArrayList<FindCcRecHistorico>();

		try {
			String SQL = new String("stp_getAllHistorialREC ?,?,?");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, loteId);
			pst.setInt(2, deudaExId);
			pst.setInt(3, personaId);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				FindCcRecHistorico obj = new FindCcRecHistorico();
				obj.setDeudaExigibleId(rs.getInt("deuda_exigible_id"));
				obj.setRecId(rs.getInt("rec_id"));
				obj.setTipoRecId(rs.getInt("tipo_rec_id"));
				obj.setDescripcionCortaRec(rs.getString("descripcion_corta"));
				obj.setDescripcionRec(rs.getString("descripcion"));
				obj.setLoteId(rs.getInt("lote_id"));
				obj.setLoteRecId(rs.getInt("lote_rec_id"));
				// obj.setFechaRegistro(rs.getTimestamp("fecha_registro"));
				obj.setAnioRec(rs.getInt("anio_rec"));
				obj.setFechaEmision(rs.getTimestamp("fecha_emision"));
				obj.setFechaNotificacion(rs.getTimestamp("fecha_notificacion"));
				obj.setMontoDeuda(rs.getBigDecimal("deuda_total"));
				obj.setUsuarioId(rs.getInt("usuario_id"));
				obj.setNombreUsuario(rs.getString("nombre_usuario"));
				obj.setNroExpediente(rs.getString("nro_expediente"));
				obj.setMotivoNotificacionId(rs.getInt("motivo_notificacion_id"));
				obj.setNotificadorId(rs.getInt("notificador_id"));
				// obj.setAnioDeuda(rs.getInt("anno_deuda"));
				obj.setSubConceptoId(rs.getInt("sub_concepto_id"));
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setUsuarioNotificacion(rs.getString("usuario_notificacion"));

				list.add(obj);

			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public int generarRecInicio(Integer loteId, Integer loteExigibleId,
			Integer nroRegistros, Integer usuarioId, String terminal)
			throws Exception {
		int salida = 0;
		try {
			CallableStatement cs = connect().prepareCall(
					"{call dbo.spt_cc_genera_rec_inicio(?,?,?,?,?)}");
			cs.setInt(1, loteId);
			cs.setInt(2, loteExigibleId);
			cs.setInt(3, nroRegistros);
			cs.setInt(4, usuarioId);
			cs.setString(5, terminal);
			cs.execute();
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
		}
		return salida;
	}

	public List<FindCcRecTipo> getAllTipoRec(Boolean esPdf) throws Exception {
		List<FindCcRecTipo> lista = new LinkedList<FindCcRecTipo>();
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append("SELECT tr.tipo_rec_id, tr.descripcion as descripcionTipoRec,tr.descripcion_corta as descripcionCortaTipoRec,subc.subconcepto_id,isnull(documento_pdf,1) documento_pdf"); 
			SQL.append(" FROM dbo.cc_tipo_rec tr  ");
			SQL.append(" INNER JOIN dbo.gn_subconcepto subc ON tr.sub_concepto_id = subc.subconcepto_id and subc.concepto_id=5 "); 
			SQL.append(" WHERE tipo_rec_id>=100 and  tr.estado=1 and subc.estado=1 ");
			if(esPdf==Boolean.TRUE)
				SQL.append(" AND isnull(tr.documento_pdf,0)=1 ");
			SQL.append(" ORDER BY tr.tipo_rec_id ASC ");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				FindCcRecTipo obj = new FindCcRecTipo();

				obj.setTipoRecId(rs.getInt("tipo_rec_id"));
				obj.setDescripcionTipoRec(rs.getString("descripcionTipoRec"));
				obj.setDescripcionCortaTipoRec(rs.getString("descripcionCortaTipoRec"));
				obj.setDocumentoPdf(rs.getInt("documento_pdf"));

				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}
//	public List<FindCcRecTipo> getAllTipoRec() throws Exception {
//		List<FindCcRecTipo> lista = new LinkedList<FindCcRecTipo>();
//		try {
//			String SQL = new String(
//					"SELECT cc_tipo_rec.tipo_rec_id, subc.descripcion as descripcionTipoRec,subc.etiqueta as descripcionCortaTipoRec,subc.subconcepto_id FROM cc_tipo_rec INNER JOIN gn_subconcepto subc ON cc_tipo_rec.sub_concepto_id = subc.subconcepto_id WHERE (cc_tipo_rec.tipo_rec_id <> 1) AND (cc_tipo_rec.estado = 1) ORDER BY cc_tipo_rec.tipo_rec_id ASC");
//
//			PreparedStatement pst = connect().prepareStatement(SQL.toString());
//			ResultSet rs = pst.executeQuery();
//
//			while (rs.next()) {
//				FindCcRecTipo obj = new FindCcRecTipo();
//
//				obj.setTipoRecId(rs.getInt("tipo_rec_id"));
//				obj.setDescripcionTipoRec(rs.getString("descripcionTipoRec"));
//				obj.setDescripcionCortaTipoRec(rs
//						.getString("descripcionCortaTipoRec"));
//
//				lista.add(obj);
//			}
//		} catch (Exception e) {
//			throw (e);
//		}
//		return lista;
//	}

	public int generarRec(Integer loteRecId, Integer anioRec,
			Integer personaRecId, String nroExpedienteRec, Integer anioDeuda,
			Integer tipoRec, Integer tipoDocRec, Integer loteExId,
			Integer actoRecId, Integer usuarioId, String terminal)
			throws Exception {

		int salida = 0;
		try {
			CallableStatement cs = connect().prepareCall(
					"{call dbo.spt_cc_genera_rec(?,?,?,?,?,?,?,?,?,?,?)}");
			cs.setInt(1, loteRecId);
			cs.setInt(2, anioRec);
			cs.setInt(3, personaRecId);
			cs.setString(4, nroExpedienteRec);
			cs.setInt(5, anioDeuda);
			cs.setInt(6, tipoRec);
			cs.setInt(7, tipoDocRec);
			cs.setInt(8, loteExId);
			cs.setInt(9, actoRecId);
			cs.setInt(10, usuarioId);
			cs.setString(11, terminal);

			cs.execute();
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
		}
		return salida;
	}

	// Actualiza Notificaciones de cada Rec
	public int actualizarNotificacionRec(Integer recId,Integer noNotificacionId, Integer notificadorId,Date fechaNotifica, Integer usuarioId) throws Exception {
		int result = 0;
		try {
			StringBuffer SQL = new StringBuffer();

			SQL.append(" UPDATE " + SATParameterFactory.getDBNameScheme()
					+ ".cc_rec ");
			SQL.append(" SET motivo_notificacion_id =?,notificador_id =?,fecha_notificacion=?, usuario_notificacion=?");
			SQL.append(" where rec_id = ? ");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, noNotificacionId);
			pst.setInt(2, notificadorId);
			pst.setTimestamp(3, DateUtil.dateToSqlTimestamp(fechaNotifica));
			pst.setInt(4, usuarioId);
			pst.setInt(5, recId);

			result = pst.executeUpdate();
		} catch (Exception e) {
			throw (e);
		}
		return result;
	}

	public int actualizarRecXCancelacion(String motivoCancelacion,
			String resolucionCancelacion, String estado, String nroExpediente,
			Integer codPersona, String usuarioCancelacion, Integer usuarioId,
			String terminal) throws Exception {
		int result = 0;
		try {
			CallableStatement cs = connect().prepareCall("{call dbo.stp_actualizar_rec_x_cancelacion(?,?,?,?,?,?,?,?)}");

			cs.setString(1, motivoCancelacion);
			cs.setString(2, resolucionCancelacion);
			cs.setString(3, estado);
			cs.setString(4, nroExpediente);
			cs.setInt(5, codPersona);
			cs.setString(6, usuarioCancelacion);
			cs.setInt(7, usuarioId);
			cs.setString(8, terminal);
			cs.execute();
		} catch (Exception e) {
			throw (e);
		}
		return result;
	}

	public int actualizarEmisionRecMigradas(Integer recId, Date fechaEmision)
			throws Exception {
		int result = 0;
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append(" UPDATE " + SATParameterFactory.getDBNameScheme()+ ".cc_rec ");
			SQL.append(" SET fecha_emision =?");
			SQL.append(" where rec_id = ? ");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setTimestamp(1, DateUtil.dateToSqlTimestamp(fechaEmision));
			pst.setInt(2, recId);

			result = pst.executeUpdate();
		} catch (Exception e) {
			throw (e);
		}
		return result;
	}

	public List<FindCcRecTipo> getAllRecsMasivasPorLote(Integer loteId)
			throws Exception {
		List<FindCcRecTipo> list = new ArrayList<FindCcRecTipo>();

		try {
			String SQL = new String("reporte_tiporecs_generadas_por_lote ?");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, loteId);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FindCcRecTipo obj = new FindCcRecTipo();
				obj.setLoteId(rs.getInt("lote_id"));
				obj.setTipoRecId(rs.getInt("tipo_rec_id"));
				obj.setDescripcionTipoRec(rs.getString("descripcion"));
				obj.setFechaEmisionRec(rs.getDate("fecha_emision"));
				obj.setNroRecsGeneradas(rs.getInt("nro_recs_generadas"));
				obj.setFechaNotificacionRec(rs.getDate("fecha_notificacion"));
				obj.setConceptoId(rs.getInt("concepto_id"));
				obj.setConceptoDescripcion(rs.getString("concepto"));
				obj.setUsuarioId(rs.getInt("usuario_id"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public List<ObligacionDTO> getAllCostasXTipoRec(Integer personaId,
			Integer recId) throws Exception {
		List<ObligacionDTO> list = new ArrayList<ObligacionDTO>();

		try {
			String SQL = new String("sp_listar_costasXtipo_rec ?,?");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, personaId);
			pst.setInt(2, recId);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				ObligacionDTO obj = new ObligacionDTO();
				obj.setConceptoDescripcion(rs.getString("concepto"));
				obj.setSubConceptoDescripcion(rs.getString("descripcion"));
				obj.setFechaEmision(rs.getDate("fecha_emision"));
				obj.setFechaVencimiento(rs.getDate("fecha_vencimiento"));
				obj.setResponsable(rs.getString("responsable"));
				obj.setMonto(rs.getBigDecimal("cc_total_deuda"));

				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public void insertarJustificacion(Integer personaId, Integer conceptoId,
			Integer subConceptoId, Integer anho, Integer determinacionId,
			String nroExped, String justificacion, UserSession userSession)
			throws SisatException {
		try {
			CallableStatement cs = connect()
					.prepareCall(
							"{call dbo.stp_justificacion_deudas_coactiva(?,?,?,?,?,?,?,?,?)}");

			cs.setInt(1, personaId);
			cs.setInt(2, conceptoId);
			cs.setInt(3, subConceptoId);
			cs.setInt(4, anho);
			cs.setInt(5, determinacionId);
			cs.setString(6, nroExped);
			cs.setString(7, justificacion);
			cs.setInt(8, userSession.getUsuarioId().intValue());
			cs.setString(9, userSession.getTerminal());

			cs.execute();

		} catch (SQLException e) {

			throw new SisatException(e.getMessage(), e.getCause());
		} catch (SisatException e) {

			throw e;
		}
	}

	public List<FindCcLoteDetalleActoExp> getAllReporteExpedientes(
			Integer tipoDeuda, Integer tipoRecId) throws Exception {
		List<FindCcLoteDetalleActoExp> list = new ArrayList<FindCcLoteDetalleActoExp>();

		try {
			String SQL = new String("getAllReporteExpedientes ?,?");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, tipoDeuda);
			pst.setInt(2, tipoRecId);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FindCcLoteDetalleActoExp obj = new FindCcLoteDetalleActoExp();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setApellidosNombres(rs.getString("administrado"));
				obj.setDireccion(rs.getString("domicilio_fiscal"));
				obj.setTipoActo(rs.getString("tipo_valor"));
				obj.setConcepto(rs.getString("concepto"));
				obj.setPlaca(rs.getString("placa"));
				obj.setNroActo(rs.getString("nro_valor"));
				obj.setNroExpediente(rs.getString("nro_expediente"));
				obj.setEstadoExpediente(rs.getString("estado_expediente"));
				obj.setFechaEmision(rs.getTimestamp("fecha_emision"));
				obj.setFechaNotificacion(rs.getTimestamp("fecha_notificacion"));
				obj.setDeuda(rs.getBigDecimal("deuda_rec"));
				obj.setMontoDeuda(rs.getBigDecimal("deuda_saldos"));
				obj.setEstado_deuda(rs.getString("estado_deuda"));
				obj.setAnnoDeuda(rs.getInt("anno_deuda"));
				obj.setDeudaTotalDcto(rs.getBigDecimal("monto_pago"));
				obj.setFechaPago(rs.getTimestamp("fecha_pago"));
				obj.setFechaCancelacion(rs.getTimestamp("fecha_cancelacion"));
				obj.setUltimaRecEmitida(rs.getString("ultima_rec_emitida"));

				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public List<FindCcLoteDetalleActoExp> getAllExpedientesXCodigoAnterior(
			String codigoAnterior) throws Exception {
		List<FindCcLoteDetalleActoExp> list = new ArrayList<FindCcLoteDetalleActoExp>();

		try {
			String SQL = new String(
					"stp_getAllExpedientesAcumuladosCodAnterior ?");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setString(1, codigoAnterior);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				FindCcLoteDetalleActoExp obj = new FindCcLoteDetalleActoExp();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setConceptoId(rs.getInt("concepto_id"));
				obj.setLoteRecId(rs.getInt("lote_id"));
				obj.setDeudaExigibleId(rs.getInt("deuda_exigible_id"));
				obj.setLoteIdAnterior(rs.getInt("lote_exigible_id"));
				obj.setNroExpediente(rs.getString("nro_expediente"));
				obj.setAnnoDeuda(rs.getInt("anno_deuda"));
				obj.setMontoDeuda(rs.getBigDecimal("deuda_total"));
				obj.setTipoActoDescripcionCorta(rs
						.getString("descripcion_corta"));
				obj.setPlaca(rs.getString("placa"));
				obj.setNroPapeleta(rs.getString("nro_papeleta"));
				obj.setPapeletaId(rs.getInt("papeleta_id"));
				obj.setApellidosNombres(rs.getString("apellidos_nombres"));
				obj.setActoId(rs.getInt("acto_id"));
				obj.setTipoDocumentoId(rs.getInt("tipo_documento_id"));
				obj.setEstado_deuda(rs.getString("estado_deuda"));
				obj.setNroActo(rs.getString("nro_acto"));
				obj.setEstadoExpediente(rs.getString("estado_expediente"));
				obj.setMotivoCancelacion(rs.getString("motivo_cancelacion"));
				obj.setResolucionCancelacion(rs
						.getString("resolucion_cancelacion"));
				obj.setFechaCancelacion(rs.getTimestamp("fecha_cancelacion"));
				obj.setUsuarioCancelacion(rs.getString("usuario_cancelacion"));
				list.add(obj);

			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public GnRemate insertarDetalleRemate(GnRemate remate)
			throws SisatException {
		try {
			CallableStatement cs = connect().prepareCall(
					"{call dbo.insertarDetalleRemate(?,?,?,?,?,?,?,?)}");
			cs.setInt(1, remate.getPropietarioId());
			cs.setString(2, remate.getPlaca());
			cs.setBigDecimal(3, remate.getMontoAdjudicado());
			cs.setDate(4, DateUtil.dateToSqlDate(remate.getFechaRemate()));
			cs.setTimestamp(5, remate.getFechaRegistro());
			cs.setInt(6, remate.getUsuarioId());
			cs.setString(7, remate.getTerminal());
			cs.setString(8, remate.getSustento());

			cs.execute();
		} catch (SQLException e) {
			throw new SisatException(e.getMessage(), e.getCause());
		} catch (SisatException e) {

			throw e;
		}
		return null;
	}
	
	public List<FindCcLoteDetalleActoExp> getAllDeudaExigibleRdVehicular(
			Integer tipoActo, Integer conceptoId, Integer anio,
			BigDecimal montoMinimo, Integer flagUbicacion) throws Exception {
		List<FindCcLoteDetalleActoExp> list = new ArrayList<FindCcLoteDetalleActoExp>();
		try {
			String SQL = "sp_deuda_exigible_rd_vehicular ?,?,?,?,?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, tipoActo);
			pst.setInt(2, conceptoId);
			pst.setInt(3, anio);
			pst.setBigDecimal(4, montoMinimo);
			pst.setInt(5, flagUbicacion);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FindCcLoteDetalleActoExp obj = new FindCcLoteDetalleActoExp();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setApellidosNombres(rs.getString("datos_persona"));
				obj.setDireccion(rs.getString("direccion_completa"));
				obj.setTipoActo(rs.getString("tipo_acto"));
				obj.setNroActo(rs.getString("nro_acto"));
				obj.setAnnoDeuda(rs.getInt("anno_acto"));
				obj.setMontoDeuda(rs.getBigDecimal("monto_deuda"));
				obj.setFechaNotificacion(rs.getTimestamp("fecha_notificacion"));
				obj.setEstado_deuda(rs.getString("estado_deuda"));
				obj.setObservacion(rs.getString("observacion"));

				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}
	
	public int registrarActoDeudaExigibleRdVehicular(Integer loteId,
			Integer periodo, Integer conceptoId, Integer tipoActoId,
			BigDecimal montoMinimo, Integer flagUbicable, Integer usuarioId,
			String terminal) throws Exception {
		int salida = 0;
		try {
			CallableStatement cs = connect()
					.prepareCall(
							"{call dbo.stp_genera_deuda_exigible_rd_vehicular(?,?,?,?,?,?,?,?)}");
			cs.setInt(1, loteId);
			cs.setInt(2, periodo);
			cs.setInt(3, conceptoId);
			cs.setInt(4, tipoActoId);
			cs.setBigDecimal(5, montoMinimo);
			cs.setInt(6, flagUbicable);
			cs.setInt(7, usuarioId);
			cs.setString(8, terminal);
			cs.execute();
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
		}
		return salida;
	}
	
	/**
	 * Modulo Coactiva V2 ::Inicio
	 */
	public Integer registraCarteraExigible(String listActoId,Integer loteId,Integer usuarioId,String terminal)throws Exception{
		Integer result=Constante.RESULT_PENDING;
		try {
			String SQL = "{call dbo.stp_co_cartera_exigible_registra(?,?,?,?)}";
			CallableStatement cs = connect().prepareCall(SQL.toString());
			cs.setString(1, listActoId);
			cs.setInt(2, loteId);
			cs.setInt(3, usuarioId);
			cs.setString(4, terminal);
			cs.executeUpdate();
			result=Constante.RESULT_SUCCESS;
		} catch (Exception e) {
			throw (e);
		}
		return result;		
	}
	public Integer retiraCarteraExigible(Integer actoId,Integer motivoId,String resenaRetiro,Integer loteId,Integer usuarioId,String terminal)throws Exception{
		Integer result=Constante.RESULT_PENDING;
		try {
			String SQL = "{call dbo.stp_co_cartera_exigible_retira(?,?,?,?,?,?)}";
			CallableStatement cs = connect().prepareCall(SQL.toString());
			cs.setInt(1, actoId);
			cs.setInt(2, motivoId);
			cs.setString(3, resenaRetiro);
			cs.setInt(4, loteId);
			cs.setInt(5, usuarioId);
			cs.setString(6, terminal);
			cs.executeUpdate();
			result=Constante.RESULT_SUCCESS;
		} catch (Exception e) {
			throw (e);
		}
		return result;		
	}

	public List<InformeTransferido> buscarInformeTransferido(Integer loteId, Integer periodoDeuda,
			Integer estadoTransferenciaId) throws Exception {
		List<InformeTransferido> list = new ArrayList<InformeTransferido>();
		try {
			String SQL = "dbo.stp_co_consulta_transferencia ?,?,?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, Util.nvl(loteId,0));
			pst.setInt(2, Util.nvl(periodoDeuda,0));
			pst.setInt(3, Util.nvl(estadoTransferenciaId,0));
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				InformeTransferido obj = new InformeTransferido();
				obj.setLoteTransferenciaId(rs.getInt("lote_transferencia_id"));
				obj.setFechaEmision(rs.getString("fecha_emision"));
				obj.setHoraEmision(rs.getString("hora_emision"));
				obj.setUsuarioEmision(rs.getString("nombre_usuario"));
				obj.setNroLoteOrigen(rs.getString("lote_id"));
				obj.setEstadoTransferencia(rs.getString("estado_transferencia"));
				obj.setPeriodoDeuda(rs.getString("periodo_deuda"));
				obj.setTipoValor(rs.getString("tipo_acto"));
				obj.setConcepto(rs.getString("concepto"));
				obj.setCantidadValores(rs.getInt("cant_registros"));
				obj.setCantidadRecibido(rs.getInt("cant_recibido"));
				obj.setCantidadDevuelto(rs.getInt("cant_devuelto"));
				obj.setTotalExigible(rs.getInt("total_exigible"));
				obj.setEstadoTransferenciaId(rs.getInt("estado_transferencia_id"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}
	
	public List<InformeTransferidoDetalle> buscarDetalleInformeTransferido(Integer loteTransferenciaId)throws Exception {
		List<InformeTransferidoDetalle> list = new ArrayList<InformeTransferidoDetalle>();
		try {
			String SQL = "dbo.stp_co_consulta_transferencia_det ?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, Util.nvl(loteTransferenciaId,0));
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				InformeTransferidoDetalle obj = new InformeTransferidoDetalle();
				obj.setTipoValor(rs.getString("tipo_acto"));
				obj.setNroValor(rs.getString("nro_acto"));
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setApellidosNombresPersona(rs.getString("apellidos_nombres"));
				obj.setDireccionFiscal(rs.getString("direccion_completa"));
				obj.setMontoDeuda(rs.getDouble("monto_deuda_transferido"));
				obj.setExigibilidad(rs.getString("exigibilidad"));
				obj.setEstadoRecepcion(rs.getString("estado_recepcion"));
				obj.setEstadoDeuda(rs.getString("estado_deuda"));
				obj.setConcepto(rs.getString("concepto"));
				obj.setPeriodo(rs.getInt("periodo"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}
	
	public List<ListadoArea> listarArea()throws Exception {
		List<ListadoArea> list = new ArrayList<ListadoArea>();
		try {
			String SQL = new String("exec dbo.stp_co_listado_area");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				ListadoArea obj = new ListadoArea();
				obj.setUnidadId(rs.getInt("unidad_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}
	
	public List<ListadoEstadoTransferencia> listarEstadoTransferencia()throws Exception {
		List<ListadoEstadoTransferencia> list = new ArrayList<ListadoEstadoTransferencia>();
		try {
			String SQL = "stp_co_listado_estado_transferencia";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				ListadoEstadoTransferencia obj = new ListadoEstadoTransferencia();
				obj.setEstadoTransferenciaId(rs.getInt("estado_transferencia_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public List<InformeTransferidoDetalle> listarValoresRecibidos(Integer loteTransferenciaId)throws Exception{
		List<InformeTransferidoDetalle> list = new ArrayList<InformeTransferidoDetalle>();
		try {
			String SQL = "dbo.stp_co_consulta_transferencia_det_recibir ?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, Util.nvl(loteTransferenciaId,0));
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				InformeTransferidoDetalle obj = new InformeTransferidoDetalle();
				obj.setLoteTransferenciaDetalleId(rs.getInt("lote_transferencia_detalle_id"));
				obj.setTipoValor(rs.getString("tipo_acto"));
				obj.setNroValor(rs.getString("nro_acto"));
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setActoId(rs.getInt("acto_id"));
				obj.setApellidosNombresPersona(rs.getString("apellidos_nombres"));
				obj.setDireccionFiscal(rs.getString("direccion_completa"));
				obj.setMontoDeuda(rs.getDouble("monto_deuda_transferido"));
				obj.setExigibilidad(rs.getString("exigibilidad"));
				obj.setEstadoRecepcion(rs.getString("estado_recepcion"));
				obj.setConcepto(rs.getString("concepto"));
				obj.setPeriodo(rs.getInt("anno_acto"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
		
	}
	
	public List<InformeTransferidoDetalle> listarValoresDevueltos(Integer loteTransferenciaId)throws Exception{
		List<InformeTransferidoDetalle> list = new ArrayList<InformeTransferidoDetalle>();
		try {
			String SQL = "dbo.stp_co_consulta_transferencia_det_devolver ?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, Util.nvl(loteTransferenciaId,0));
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				InformeTransferidoDetalle obj = new InformeTransferidoDetalle();
				obj.setLoteTransferenciaDetalleId(rs.getInt("lote_transferencia_detalle_id"));
				obj.setTipoValor(rs.getString("tipo_acto"));
				obj.setNroValor(rs.getString("nro_acto"));
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setActoId(rs.getInt("acto_id"));
				obj.setApellidosNombresPersona(rs.getString("apellidos_nombres"));
				obj.setMontoDeuda(rs.getDouble("monto_deuda_transferido"));
				obj.setExigibilidad(rs.getString("exigibilidad"));
				obj.setMotivoDevolucion(Util.getString(rs.getString("motivo_devolucion")));
				obj.setObservacion(Util.getString(rs.getString("observacion_devolucion")));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}
	
	public Integer registraDevolucionValor(Integer loteTransferenciaDetalleId,Integer motivoDevolucionId,String observacionDevolucion, Integer usuarioId,String terminal)throws Exception {
		Integer result=Constante.RESULT_PENDING;
		
		try {
			String SQL = "{call dbo.stp_co_registra_devolucion_valor (?,?,?,?,?)}";
			CallableStatement cs = connect().prepareCall(SQL.toString());
			cs.setInt(1, Util.nvl(loteTransferenciaDetalleId,0));
			cs.setInt(2, Util.nvl(motivoDevolucionId,0));
			cs.setString(3, Util.nvl(observacionDevolucion,""));
			cs.setInt(4, usuarioId);
			cs.setString(5, terminal);
			int rowCount=cs.executeUpdate();
			if(rowCount>0){
				result=Constante.RESULT_SUCCESS;
			}
		} catch (Exception e) {
			throw (e);
		}
		return result;
	}
	
	public Integer registraRecepcionInforme(Integer loteTransferenciaId,Integer usuarioId,String terminal)throws Exception{
		Integer result=Constante.RESULT_PENDING;
		
		try {
			String SQL = "{call dbo.stp_co_registra_recepcion_informe (?,?,?)}";
			CallableStatement cs = connect().prepareCall(SQL.toString());
			cs.setInt(1, Util.nvl(loteTransferenciaId,0));
			cs.setInt(2, usuarioId);
			cs.setString(3, terminal);
			
			int rowCount=cs.executeUpdate();
			if(rowCount>0){
				result=Constante.RESULT_SUCCESS;
			}
		} catch (Exception e) {
			throw (e);
		}
		return result;
	}
	
	public List<GenericDTO> listarMotivoDevolucion()throws Exception {
		List<GenericDTO> list = new ArrayList<GenericDTO>();
		try {
			String SQL = "dbo.stp_co_listado_motivo_devolucion";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				GenericDTO obj = new GenericDTO();
				obj.setId(rs.getInt("motivo_devolucion_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}
	
	/*
	 * esto para el reporte excel de la pantalla de recepcion de valores
	 */
	public List<InformeTransferidoDetalle> listarEstadoRecepcionValores(Integer loteTransferenciaId)throws Exception{
		List<InformeTransferidoDetalle> list = new ArrayList<InformeTransferidoDetalle>();
		try {
			String SQL = "dbo.stp_co_consulta_estado_recepcion ?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, Util.nvl(loteTransferenciaId,0));
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				InformeTransferidoDetalle obj = new InformeTransferidoDetalle();
				obj.setTipoValor(rs.getString("tipo_acto"));
				obj.setNroValor(rs.getString("nro_acto"));
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setApellidosNombresPersona(rs.getString("apellidos_nombres"));
				obj.setDireccionFiscal(rs.getString("direccion_completa"));
				obj.setMontoDeuda(rs.getDouble("monto_deuda_transferido"));
				obj.setExigibilidad(rs.getString("exigibilidad"));
				obj.setEstadoRecepcion(rs.getString("estado_recepcion"));
				/*
				completar
				*/
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
		
	}

	/**
	 * Pantalla de busqueda de cartera exigibilidad 
	 */

	public List<GenericDTO> listarSituacionCartera()throws Exception {
		List<GenericDTO> list = new ArrayList<GenericDTO>();
		try {
			String SQL = "dbo.stp_co_listado_situacion_cartera";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				GenericDTO obj = new GenericDTO();
				obj.setId(rs.getInt("situacion_cartera_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}
	
	public List<GenericDTO> listarEjecutorCoactivo()throws Exception {
		List<GenericDTO> list = new ArrayList<GenericDTO>();
		try {
			String SQL = "dbo.stp_co_listado_ejecutor_coactivo";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				GenericDTO obj = new GenericDTO();
				obj.setId(rs.getInt("usuario_id"));
				obj.setDescripcion(rs.getString("ejecutor_coactivo"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}
	
	
	public List<GenericDTO> listarAuxiliarCoactivo()throws Exception {
		List<GenericDTO> list = new ArrayList<GenericDTO>();
		try {
			String SQL = "dbo.stp_co_listado_ejecutor_coactvo";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				GenericDTO obj = new GenericDTO();
				obj.setId(rs.getInt("usuario_id"));
				obj.setDescripcion(rs.getString("ejecutor_coactivo"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}
	
	

	public List<CarteraExigibilidad> buscarCarteraExigibilidad(String nroCartera,Integer usuarioCoactivoId,Integer situacionCarteraId,Integer materiaId)throws Exception{
		List<CarteraExigibilidad> list = new ArrayList<CarteraExigibilidad>();
		try {
			String SQL = "dbo.stp_co_consulta_cartera_ex ?,?,?,?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setString(1, Util.nvl(nroCartera,"0"));
			pst.setInt(2, Util.nvl(usuarioCoactivoId,0));
			pst.setInt(3, Util.nvl(situacionCarteraId,0));
			pst.setInt(4, Util.nvl(materiaId,0));
			
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				CarteraExigibilidad obj = new CarteraExigibilidad();
				obj.setCarteraId(rs.getInt("cartera_id"));
				obj.setNroCartera(rs.getString("nro_cartera"));
				obj.setFechaAsignacion(rs.getString("fecha_asignacion"));
				obj.setHoraAsignacion(rs.getString("hora_asignacion"));
				obj.setEjecutorCoactivo(Util.getString(rs.getString("ejecutor_coactivo")));
				obj.setCantidadRegistros(rs.getInt("cant_registros"));
				obj.setTotalExigible(rs.getDouble("total_exigible"));
				obj.setSituacionCartera(rs.getString("situacion_cartera"));
				obj.setSituacionCarteraId(rs.getInt("situacion_cartera_id"));
				obj.setUsuarioCoactivoId(rs.getInt("usuario_coactivo_id"));
				obj.setMateriaId(rs.getInt("materia_id"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
		
	}
	
	public List<CarteraExigibilidad> buscarCarteraMedidaCautelar(String nroCartera,Integer usuarioCoactivoId,Integer situacionCarteraId)throws Exception{
		List<CarteraExigibilidad> list = new ArrayList<CarteraExigibilidad>();
		try {
			String SQL = "dbo.stp_co_consulta_cartera_mc ?,?,?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setString(1, Util.nvl(nroCartera,"0"));
			pst.setInt(2, Util.nvl(usuarioCoactivoId,0));
			pst.setInt(3, Util.nvl(situacionCarteraId,0));
			
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				CarteraExigibilidad obj = new CarteraExigibilidad();
				obj.setCarteraId(rs.getInt("cartera_id"));
				obj.setNroCartera(rs.getString("nro_cartera"));
				obj.setFechaAsignacion(rs.getString("fecha_asignacion"));
				obj.setHoraAsignacion(rs.getString("hora_asignacion"));
				obj.setEjecutorCoactivo(Util.getString(rs.getString("ejecutor_coactivo")));
				obj.setCantidadRegistros(rs.getInt("cant_registros"));
				obj.setTotalExigible(rs.getDouble("total_exigible"));
				obj.setSituacionCartera(rs.getString("situacion_cartera"));
				obj.setSituacionCarteraId(rs.getInt("situacion_cartera_id"));
				obj.setUsuarioCoactivoId(rs.getInt("usuario_coactivo_id"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
		
	}
	
	public Integer registrarCarteraExigibilidad(Integer carteraId,Integer coactivoId,Integer tipoCarteraId,Integer materiaId,String terminal,Integer usuarioId)throws Exception{
		try {
			String SQL = "dbo.stp_co_registra_cartera_ex ?,?,?,?,?,?,?";
			CallableStatement cs = connect().prepareCall(SQL.toString());
			cs.setInt(1, Util.nvl(carteraId,0));
			cs.setInt(2, Util.nvl(coactivoId,0));
			cs.setInt(3, Util.nvl(tipoCarteraId,0));
			cs.setInt(4, Util.nvl(materiaId,0));
			cs.setString(5, terminal);
			cs.setInt(6, usuarioId);
			cs.registerOutParameter(7, java.sql.Types.INTEGER);
			
			boolean rowCount=cs.execute();
			if(rowCount){
				carteraId=cs.getInt(7);
			}
		} catch (Exception e) {
			throw (e);
		}
		return carteraId;		
	}
	
	public Integer registrarCarteraMedidaCautelar(Integer carteraId,Integer coactivoId,Integer tipoCarteraId,Integer materiaId,String terminal,Integer usuarioId)throws Exception{
		try {
			String SQL = "dbo.stp_co_registra_cartera_mc ?,?,?,?,?,?,?";
			CallableStatement cs = connect().prepareCall(SQL.toString());
			cs.setInt(1, Util.nvl(carteraId,0));
			cs.setInt(2, Util.nvl(coactivoId,0));
			cs.setInt(3, Util.nvl(tipoCarteraId,0));
			cs.setInt(4, Util.nvl(materiaId,0));
			cs.setString(5, terminal);
			cs.setInt(6, usuarioId);
			cs.registerOutParameter(7, java.sql.Types.INTEGER);
			
			boolean rowCount=cs.execute();
			if(rowCount){
				carteraId=cs.getInt(7);
			}
		} catch (Exception e) {
			throw (e);
		}
		return carteraId;		
	}
	
	/**
	 *Pantalla de creacion de cartera de valores 
	 */
	public List<GenericDTO> listarTipoPersona()throws Exception {
		List<GenericDTO> list = new ArrayList<GenericDTO>();
		try {
			String SQL = "dbo.stp_co_listado_tipo_persona";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				GenericDTO obj = new GenericDTO();
				obj.setId(rs.getInt("tipo_persona_id"));
				obj.setDescripcion(rs.getString("descripcion_corta"));
				list.add(obj);
			}
		} catch (Exception e) {
				throw (e);
		}
		return list;
	}
	public List<GenericDTO> listarTipoActo()throws Exception {
		List<GenericDTO> list = new ArrayList<GenericDTO>();
		try {
			String SQL = "dbo.stp_co_listado_tipo_acto";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				GenericDTO obj = new GenericDTO();
				obj.setId(rs.getInt("tipo_acto_id"));
				obj.setDescripcion(rs.getString("descripcion_corta"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}
	
	public List<GenericDTO> listarConcepto()throws Exception {
		List<GenericDTO> list = new ArrayList<GenericDTO>();
		try {
			String SQL = "dbo.stp_co_listado_concepto";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				GenericDTO obj = new GenericDTO();
				obj.setId(rs.getInt("concepto_id"));
				obj.setDescripcion(rs.getString("descripcion_corta"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}
	
	public List<GenericDTO> listarSubConcepto(Integer conceptoId)throws Exception {
		List<GenericDTO> list = new ArrayList<GenericDTO>();
		try {
			String SQL = "dbo.stp_co_listado_subconcepto ?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, Util.nvl(conceptoId,0));
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				GenericDTO obj = new GenericDTO();
				obj.setId(rs.getInt("subconcepto_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}
	
	public List<InformeTransferidoDetalle> seleccionaCarteraExigibilidad(Integer periodo,Integer tipoPersonaId,Integer tipoDeuda,Integer conceptoId,Integer subconceptoId,Integer tipoActoId,String nroActo,Integer personaId,Double montoMin,Double montoMax,Integer carteraId)throws Exception{
		List<InformeTransferidoDetalle> list = new ArrayList<InformeTransferidoDetalle>();
		try {
			String SQL = "dbo.stp_co_selecciona_cartera_ex ?,?,?,?,?,?,?,?,?,?,?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, Util.nvl(periodo,0));
			pst.setInt(2, Util.nvl(tipoPersonaId,0));
			pst.setInt(3, Util.nvl(tipoDeuda,0));
			pst.setInt(4, Util.nvl(conceptoId,0));
			pst.setInt(5, Util.nvl(subconceptoId,0));
			pst.setInt(6, Util.nvl(tipoActoId,0));
			pst.setString(7, Util.nvl(nroActo,""));
			pst.setInt(8, Util.nvl(personaId,0));
			pst.setDouble(9, Util.nvl(montoMin,0.0));
			pst.setDouble(10, Util.nvl(montoMax,0.0));
			pst.setDouble(11, Util.nvl(carteraId,0));
			
			ResultSet rs = pst.executeQuery();
			InformeTransferidoDetalle obj ;
			
			while (rs.next()) {
				obj = new InformeTransferidoDetalle();
				obj.setLoteTransferenciaDetalleId(rs.getInt("lote_transferencia_detalle_id"));
				obj.setNroInforme(rs.getString("nro_informe"));
				obj.setTipoValor(rs.getString("tipo_acto"));
				obj.setNroValor(rs.getString("nro_acto"));
				obj.setFechaEmision(rs.getString("fecha_emision"));
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setApellidosNombresPersona(rs.getString("apellidos_nombres"));
				obj.setPeriodo(rs.getInt("anno_acto"));
				obj.setConcepto(rs.getString("concepto"));
				obj.setSubConcepto(rs.getString("subconcepto"));
				obj.setMontoDeuda(rs.getDouble("monto_deuda_transferido"));
				obj.setCoactivoSugerido(rs.getString("coactivo_sugerido"));
				obj.setExigibilidad(rs.getString("exigibilidad"));
				obj.setLoteId(rs.getInt("lote_id"));
				obj.setEstadoDeuda(rs.getString("estado_deuda"));
				
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}
	
	
	/**
	 * 5.Gestion de cartera de valores asignada
	 * Acumula/Desacumula cartera de valores
	 */
	
	public List<InformeTransferidoDetalle> consultarValoresCartera(Integer carteraId)throws Exception {
		List<InformeTransferidoDetalle> list = new ArrayList<InformeTransferidoDetalle>();
		try {
			String SQL = "dbo.stp_co_consulta_valores_acum ?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, Util.nvl(carteraId,0));
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				InformeTransferidoDetalle obj = new InformeTransferidoDetalle();
				obj.setTipoValor(rs.getString("tipo_acto"));
				obj.setNroValor(rs.getString("nro_acto"));
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setApellidosNombresPersona(rs.getString("apellidos_nombres"));
				obj.setPeriodo(rs.getInt("anno_acto"));
				obj.setConcepto(rs.getString("concepto"));
				obj.setSubConcepto(rs.getString("subconcepto"));
				obj.setMontoDeuda(rs.getDouble("total_exigible"));
				obj.setSituacion(rs.getString("situacion"));
				obj.setActoId(rs.getInt("acto_id"));
				//Completar
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}
	
	public List<InformeTransferidoDetalle> consultarValoresCartera(Integer carteraId,Integer personaId)throws Exception {
		List<InformeTransferidoDetalle> list = new ArrayList<InformeTransferidoDetalle>();
		try {
			String SQL = "dbo.stp_co_consulta_valores_persona ?,?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, Util.nvl(carteraId,0));
			pst.setInt(2, Util.nvl(personaId,0));
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				InformeTransferidoDetalle obj = new InformeTransferidoDetalle();
				obj.setProspectoId(rs.getInt("prospecto_id"));
				obj.setTipoValor(rs.getString("tipo_acto"));
				obj.setNroValor(rs.getString("nro_acto"));
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setApellidosNombresPersona(rs.getString("apellidos_nombres"));
				obj.setPeriodo(rs.getInt("anno_acto"));
				obj.setConcepto(rs.getString("concepto"));
				obj.setSubConcepto(rs.getString("subconcepto"));
				obj.setMontoDeuda(rs.getDouble("total_exigible"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}
	
	public Integer registraAcumulacion(String listaProspectoId,Integer carteraId,Integer personaId,String terminal,Integer usuarioId)throws Exception{
		Integer result=Constante.RESULT_PENDING;
		try {
			String SQL = "{call dbo.stp_co_registra_acumulacion(?,?,?,?,?)}";
			CallableStatement cs = connect().prepareCall(SQL.toString());
			cs.setString(1, Util.nvl(listaProspectoId,""));
			cs.setInt(2, Util.nvl(carteraId,0));
			cs.setInt(3, Util.nvl(personaId,0));
			cs.setInt(4, usuarioId);
			cs.setString(5, terminal);
			cs.executeUpdate();
			result=Constante.RESULT_SUCCESS;
		} catch (Exception e) {
			throw (e);
		}
		return result;		
	}
	
	public Integer registraAcumulacionExpedienteEnCartera(String listaProspectoId,Integer carteraId,Integer personaId,String terminal,Integer usuarioId)throws Exception{
		Integer result=Constante.RESULT_PENDING;
		try {
			String SQL = "{call dbo.stp_co_registra_acumulacion_expediente_cartera(?,?,?,?,?)}";
			CallableStatement cs = connect().prepareCall(SQL.toString());
			cs.setString(1, Util.nvl(listaProspectoId,""));
			cs.setInt(2, Util.nvl(carteraId,0));
			cs.setInt(3, Util.nvl(personaId,0));
			cs.setInt(4, usuarioId);
			cs.setString(5, terminal);
			cs.executeUpdate();
			result=Constante.RESULT_SUCCESS;
		} catch (Exception e) {
			throw (e);
		}
		return result;		
	}
	
	public Integer registraDesAcumulacion(Integer carteraId,Integer personaId,String terminal,Integer usuarioId)throws Exception{
		Integer result=Constante.RESULT_PENDING;
		try {
			String SQL = "{call dbo.stp_co_registra_desacumulacion(?,?,?,?)}";
			CallableStatement cs = connect().prepareCall(SQL.toString());
			cs.setInt(1, Util.nvl(carteraId,0));
			cs.setInt(2, Util.nvl(personaId,0));
			cs.setInt(3, usuarioId);
			cs.setString(4, terminal);
			cs.executeUpdate();
			result=Constante.RESULT_SUCCESS;
		} catch (Exception e) {
			throw (e);
		}
		return result;		
	}
	
	public Integer registraAcumulacionTodo(Integer carteraId,Integer usuarioId,String terminal)throws Exception{
		Integer result=Constante.RESULT_PENDING;
		try {
			String SQL = "{call dbo.stp_co_registra_acumulacion_todo(?,?,?)}";
			CallableStatement cs = connect().prepareCall(SQL.toString());
			cs.setInt(1, Util.nvl(carteraId,0));
			cs.setInt(2, usuarioId);
			cs.setString(3, terminal);
			cs.executeUpdate();
			result=Constante.RESULT_SUCCESS;
		} catch (Exception e) {
			throw (e);
		}
		return result;		
	}
	
	public Integer registraAcumulacionExpedienteTodo(Integer carteraId,Integer usuarioId,String terminal)throws Exception{
		Integer result=Constante.RESULT_PENDING;
		try {
			String SQL = "{call dbo.stp_co_registra_acumulacion_expediente_todo(?,?,?)}";
			CallableStatement cs = connect().prepareCall(SQL.toString());
			cs.setInt(1, Util.nvl(carteraId,0));
			cs.setInt(2, usuarioId);
			cs.setString(3, terminal);
			cs.executeUpdate();
			result=Constante.RESULT_SUCCESS;
		} catch (Exception e) {
			throw (e);
		}
		return result;		
	}
	
	public Integer registraDesAcumulacionTodo(Integer carteraId,Integer usuarioId,String terminal)throws Exception{
		Integer result=Constante.RESULT_PENDING;
		try {
			String SQL = "{call dbo.stp_co_registra_desacumulacion_todo(?,?,?)}";
			CallableStatement cs = connect().prepareCall(SQL.toString());
			cs.setInt(1, Util.nvl(carteraId,0));
			cs.setInt(2, usuarioId);
			cs.setString(3, terminal);
			cs.executeUpdate();
			result=Constante.RESULT_SUCCESS;
		} catch (Exception e) {
			throw (e);
		}
		return result;		
	}
	
	public Integer registraDesAcumulacionExpedienteTodo(Integer carteraId,Integer usuarioId,String terminal)throws Exception{
		Integer result=Constante.RESULT_PENDING;
		try {
			String SQL = "{call dbo.stp_co_registra_desacumulacion_expediente_todo(?,?,?)}";
			CallableStatement cs = connect().prepareCall(SQL.toString());
			cs.setInt(1, Util.nvl(carteraId,0));
			cs.setInt(2, usuarioId);
			cs.setString(3, terminal);
			cs.executeUpdate();
			result=Constante.RESULT_SUCCESS;
		} catch (Exception e) {
			throw (e);
		}
		return result;		
	}
	
	public Integer registraExpedientes(Integer carteraId,Integer usuarioId,String terminal)throws Exception{
		Integer result=Constante.RESULT_PENDING;
		try {
			String SQL = "{call dbo.stp_co_registra_expediente(?,?,?)}";
			CallableStatement cs = connect().prepareCall(SQL.toString());
			cs.setInt(1, Util.nvl(carteraId,0));
			cs.setInt(2, usuarioId);
			cs.setString(3, terminal);
			cs.execute();
			result=Constante.RESULT_SUCCESS;
		} catch (Exception e) {
			throw (e);
		}
		return result;		
	}
	
	public Integer registraProspectoExigibilidad(String listaDetalleId,Integer carteraId,Integer usuarioId,String terminal)throws Exception{
		Integer result=Constante.RESULT_PENDING;
		try {
			String SQL = "{call dbo.stp_co_registra_prospecto_ex(?,?,?,?)}";
			CallableStatement cs = connect().prepareCall(SQL.toString());
			cs.setString(1, Util.nvl(listaDetalleId,""));
			cs.setInt(2, Util.nvl(carteraId,0));
			cs.setInt(3, usuarioId);
			cs.setString(4, terminal);
			cs.executeUpdate();
			result=Constante.RESULT_SUCCESS;
		} catch (Exception e) {
			throw (e);
		}
		return result;		
	}
	
	public Integer registraProspectoMedidaCautelar(String listaDetalleId,Integer carteraId,Integer usuarioId,String terminal)throws Exception{
		Integer result=Constante.RESULT_PENDING;
		try {
			String SQL = "{call dbo.stp_co_registra_prospecto_mc(?,?,?,?)}";
			CallableStatement cs = connect().prepareCall(SQL.toString());
			cs.setString(1, Util.nvl(listaDetalleId,""));
			cs.setInt(2, Util.nvl(carteraId,0));
			cs.setInt(3, usuarioId);
			cs.setString(4, terminal);
			cs.executeUpdate();
			result=Constante.RESULT_SUCCESS;
		} catch (Exception e) {
			throw (e);
		}
		return result;		
	}
	
	public List<InformeTransferidoDetalle> seleccionaProspectoExigibilidad(Integer carteraId)throws Exception {
		List<InformeTransferidoDetalle> list = new ArrayList<InformeTransferidoDetalle>();
		try {
			String SQL = "dbo.stp_co_selecciona_prospecto_ex ?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, Util.nvl(carteraId,0));
			ResultSet rs = pst.executeQuery();
			
			
			while (rs.next()) {
				InformeTransferidoDetalle obj = new InformeTransferidoDetalle();
				obj.setTipoValor(rs.getString("tipo_acto"));
				obj.setNroValor(rs.getString("nro_acto"));
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setApellidosNombresPersona(rs.getString("apellidos_nombres"));
				obj.setDireccionFiscal(rs.getString("direccion_completa"));
				obj.setPeriodo(rs.getInt("anno_acto"));
				obj.setConcepto(rs.getString("concepto"));
				obj.setSubConcepto(rs.getString("subconcepto"));
				obj.setMontoDeuda(rs.getDouble("monto_deuda"));
				list.add(obj);
			}
			
			
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}
	/*
	public List<ExpedienteCoactivo> gestionConsultaCartera(String nroCartera,String nroExpediente,String nroPapeleta,Integer personaId,Integer materiaId,Integer coactivoId)throws Exception {
		List<ExpedienteCoactivo> list = new ArrayList<ExpedienteCoactivo>();
		
		ExpedienteCoactivo obj = new ExpedienteCoactivo();
		
		try {
			String SQL = "dbo.stp_co_gestion_consulta_cartera ?,?,?,?,?,?";
			//PreparedStatement pst = connect().prepareCall(SQL.toString());
			
			CallableStatement pst = connect().prepareCall(SQL.toString());
			pst.setString(1, Util.nvl(nroCartera,""));
			
			//Asignamos	la variable out
			pst.registerOutParameter(2,java.sql.Types.VARCHAR);
			
			pst.setString(3, Util.nvl(nroPapeleta,""));
			pst.setInt(4, Util.nvl(personaId,0));
			pst.setInt(5, Util.nvl(materiaId,0));
			pst.setInt(6, Util.nvl(coactivoId,0));			
			
			pst.execute();			
			
			//Obtenemos la variable out
			String num=pst.getString(2);
					
			
			obj.setNroExpediente(num);
			obj.setTotalDeuda(100);
			
			
			list.add(obj);
		

		} catch (SQLException e) {
			obj = new ExpedienteCoactivo();
			
			obj.setNroExpediente(e.getMessage());
			obj.setTotalDeuda(100);
			list.add(obj);
			
			System.out.println("Message:  " + e.getMessage());      
			
			
			//throw (e);
			
			
		}
		
		obj.setNroExpediente("único");
		obj.setTotalDeuda(300);
		list.add(obj);
		
		
		return list;
	}
	*/
	
	
	public void setEstadoBloqueoDeuda (Integer recId, String valor,Integer usuarioId ) throws Exception 
	{
		
		String SQL = "dbo.stp_co_desbloquear_deuda ?,?,?";
		PreparedStatement pst = connect().prepareStatement(SQL.toString());
		
		pst.setInt(1, recId);
		pst.setString(2, valor);
		pst.setInt(3, usuarioId);
		
		
		try {
			pst.executeQuery();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}
	
	 public List<ExpedienteCoactivo> gestionConsultaCartera(String placa,String nroCartera,String nroExpediente,String nroPapeleta,Integer personaId,Integer materiaId,Integer coactivoId)throws Exception {
		List<ExpedienteCoactivo> list = new ArrayList<ExpedienteCoactivo>();
		
		System.out.println("============================");
		System.out.println(placa);
		System.out.println(nroCartera);
		System.out.println(nroExpediente);
		System.out.println(nroPapeleta);
		System.out.println(personaId);
		System.out.println(materiaId);
		System.out.println(coactivoId);
		
		
		try {
			String SQL = "dbo.stp_co_gestion_consulta_cartera ?,?,?,?,?,?,?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setString(1, Util.nvl(nroCartera,""));
			pst.setString(2, Util.nvl(nroExpediente,""));
			pst.setString(3, Util.nvl(nroPapeleta,""));
			pst.setInt(4, Util.nvl(personaId,0));
			pst.setInt(5, Util.nvl(materiaId,0));
			pst.setInt(6, Util.nvl(coactivoId,0));
			pst.setString(7, Util.nvl(placa,""));
			
			ResultSet rs = pst.executeQuery();
			ExpedienteCoactivo obj = new ExpedienteCoactivo();
			
			Integer deudaBloqeuada;
			
			
			while (rs.next()) {
				obj = new ExpedienteCoactivo();
				obj.setExpedienteId(rs.getInt("expediente_id"));
				obj.setNroExpediente(rs.getString("nro_expediente"));
				obj.setTotalDeuda(rs.getDouble("total_monto_deuda"));
				obj.setApellidosNombresPersona(rs.getString("apellidos_nombres"));
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setCarteraId(rs.getInt("cartera_id"));
				obj.setRecId(rs.getInt("rec_id"));				
				obj.setSituacion(rs.getString("situacion_exp"));
				
				obj.setFechaemision(rs.getDate("fechaEmisionRec"));
				obj.setNroRec(rs.getString("nro_rec"));
				obj.setTipoREC(rs.getString("tipo_rec"));
				obj.setFechaNotificacion(rs.getString("fecha_notificacionREC"));
				
				deudaBloqeuada=rs.getInt("is_deuda_bloqueada");
				
				obj.setDeudaBloqueada(deudaBloqeuada>0);
				
				
				
				
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	  
	

	public List<ExpedienteCoactivo> seleccionaCarteraMedidaCautelar(Integer periodo,Integer tipoPersonaId,Integer tipoDeuda,
			Double montoMin,Double montoMax,String nroExpediente,Integer personaId,Integer carteraId,Integer coactivoId)throws Exception{
		List<ExpedienteCoactivo> list = new ArrayList<ExpedienteCoactivo>();
		try {
			String SQL = "dbo.stp_co_selecciona_cartera_mc ?,?,?,?,?,?,?,?,?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, Util.nvl(periodo,0));
			pst.setInt(2, Util.nvl(tipoPersonaId,0));
			pst.setInt(3, Util.nvl(tipoDeuda,0));
			pst.setDouble(4, Util.nvl(montoMin,0.0));
			pst.setDouble(5, Util.nvl(montoMax,0.0));
			pst.setString(6, Util.nvl(nroExpediente,""));
			pst.setInt(7, Util.nvl(personaId,0));
			pst.setInt(8, Util.nvl(carteraId,0));
			pst.setInt(9, Util.nvl(coactivoId,0));
			
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				ExpedienteCoactivo obj = new ExpedienteCoactivo();
				obj.setExpedienteId(rs.getInt("expediente_id"));
				obj.setNroExpediente(rs.getString("nro_expediente"));
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setApellidosNombresPersona(rs.getString("apellidos_nombres"));
				obj.setExigibilidad(rs.getString("exigibilidad"));
				obj.setDireccion(rs.getString("direccion_completa"));
				obj.setTotalDeuda(rs.getDouble("deuda_total"));
				obj.setNroRec(rs.getString("nro_rec"));
				obj.setFechaRec(rs.getString("fecha_rec"));
				
				obj.setFechaNotificacion(rs.getString("fecha_notificacion"));
				obj.setAnnoExpediente(rs.getInt("periodo_expediente"));
				
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}
	
	public List<ExpedienteCoactivo> seleccionaProspectoMedidaCautelar(Integer carteraId)throws Exception {
		List<ExpedienteCoactivo> list = new ArrayList<ExpedienteCoactivo>();
		try {
			String SQL = "dbo.stp_co_selecciona_prospecto_mc ?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, Util.nvl(carteraId,0));
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				ExpedienteCoactivo obj = new ExpedienteCoactivo();
				obj.setExpedienteId(rs.getInt("expediente_id"));
				obj.setNroExpediente(rs.getString("nro_expediente"));
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setApellidosNombresPersona(rs.getString("apellidos_nombres"));
				obj.setExigibilidad(rs.getString("exigibilidad"));
				obj.setDireccion(rs.getString("direccion"));
				obj.setTotalDeuda(rs.getDouble("deuda_total"));
				
				obj.setNroRec(rs.getString("nro_rec"));
				obj.setFechaRec(rs.getString("fecha_rec"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}
	
	public List<ExpedienteCoactivo> consultarExpedientesCartera(Integer carteraId)throws Exception {
		List<ExpedienteCoactivo> list = new ArrayList<ExpedienteCoactivo>();
		try {
			String SQL = "dbo.stp_co_consulta_expe_acum ?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, Util.nvl(carteraId,0));
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				ExpedienteCoactivo obj = new ExpedienteCoactivo();
				obj.setExpedienteId(rs.getInt("expediente_id"));
				obj.setNroExpediente(rs.getString("nro_expediente"));
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setApellidosNombresPersona(rs.getString("apellidos_nombres"));
				obj.setDireccion(rs.getString("direccion"));
				obj.setAnnoRec(rs.getInt("anno_rec"));
				obj.setTotalDeuda(rs.getDouble("deuda_total"));
				obj.setNroRec(rs.getString("nro_rec"));
				obj.setSituacion(rs.getString("situacion"));
				
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}
	
	public List<ExpedienteCoactivo> consultarExpedientesCartera(Integer carteraId,Integer personaId)throws Exception {
		List<ExpedienteCoactivo> list = new ArrayList<ExpedienteCoactivo>();
		try {
			String SQL = "dbo.stp_co_consulta_expe_persona ?,?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, Util.nvl(carteraId,0));
			pst.setInt(2, Util.nvl(personaId,0));
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				ExpedienteCoactivo obj = new ExpedienteCoactivo();
				obj.setExpedienteId(rs.getInt("expediente_id"));
				obj.setNroExpediente(rs.getString("nro_expediente"));
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setApellidosNombresPersona(rs.getString("apellidos_nombres"));
				obj.setTotalDeuda(rs.getDouble("deuda_total"));
				obj.setNroRec(rs.getString("nro_rec"));
				obj.setTotalDeuda(rs.getDouble("deuda_total"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public List<GenericDTO> listarUsuario()throws Exception {
		List<GenericDTO> list = new ArrayList<GenericDTO>();
		try {
			String SQL = "dbo.stp_co_listado_usuarios";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				GenericDTO obj = new GenericDTO();
				obj.setId(rs.getInt("usuario_id"));
				obj.setDescripcion(rs.getString("apellidos_nombres"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public List<EjecutorCoactivo> consultarEjecutorCoactivo()throws Exception {
		List<EjecutorCoactivo> list = new ArrayList<EjecutorCoactivo>();
		try {
			String SQL = "dbo.stp_co_listado_coactivo";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				EjecutorCoactivo obj = new EjecutorCoactivo();
				obj.setUsuarioId(rs.getInt("usuario_id"));
				obj.setApellidosNombres(rs.getString("apellidos_nombres"));
				obj.setUsuarioCargoId(rs.getInt("usuario_cargo_id"));
				obj.setNombreUsuario(rs.getString("nombre_usuario"));
				obj.setUsuarioAuxId(rs.getInt("usuario_aux_id"));
				obj.setApellidosNombresAux(rs.getString("apellidos_nombres_aux"));
				obj.setNroRegistro(rs.getString("nro_registro"));
				obj.setNroRegistroAux(rs.getString("nro_registro_aux"));
				obj.setMateriaId(rs.getInt("materia_id"));
				obj.setEstado(rs.getInt("estado")==1?Boolean.TRUE:Boolean.FALSE);
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public Integer registraUsuarioCargo(Integer usuarioCargoId,Integer usuarioId,Integer cargoId,Integer usuarioAuxId,Integer estado,
			String nroRegistro,String nroregistroAux,
			Integer materiaId,
			Integer usuarioRegistroId,String terminal)throws Exception{
		Integer result=Constante.RESULT_PENDING;
		try {
			String SQL = "{call dbo.stp_co_registra_usuario_cargo(?,?,?,?,?,?,?,?,?,?)}";
			CallableStatement cs = connect().prepareCall(SQL.toString());
			cs.setInt(1, Util.nvl(usuarioCargoId,0));
			cs.setInt(2, Util.nvl(usuarioId,0));
			cs.setInt(3, Util.nvl(cargoId,0));
			cs.setString(4, Util.nvl(nroRegistro,""));			
			cs.setInt(5, Util.nvl(usuarioAuxId,0));
			cs.setString(6, Util.nvl(nroregistroAux,""));
			cs.setInt(7, Util.nvl(estado,0));
			cs.setInt(8, usuarioRegistroId);
			cs.setString(9, terminal);
			cs.setInt(10, Util.nvl(materiaId,0));
			cs.execute();
			result=Constante.RESULT_SUCCESS;
		} catch (Exception e) {
			throw (e);
		}
		return result;		
	}

	public List<GenericDTO> consultarPeriodoCostas()throws Exception {
		List<GenericDTO> list = new ArrayList<GenericDTO>();
		try {
			String SQL = "dbo.stp_co_consulta_costas_periodo";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				GenericDTO obj = new GenericDTO();
				obj.setId(rs.getInt("periodo"));
				obj.setDescripcion(rs.getString("periodo"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public List<DetalleCostas> consultarDetalleCostas(Integer periodo)throws Exception {
		List<DetalleCostas> list = new ArrayList<DetalleCostas>();
		try {
			String SQL = "dbo.stp_co_consulta_costas_detalle ?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, Util.nvl(periodo,0));
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				DetalleCostas obj = new DetalleCostas();
				obj.setConceptoTasaId(rs.getInt("concepto_tasa_id"));
				obj.setConceptoId(rs.getInt("concepto_id"));
				obj.setSubconceptoId(rs.getInt("subconcepto_id"));
				obj.setPeriodo(rs.getInt("periodo"));
				obj.setDescripcion(rs.getString("descripcion"));
				obj.setEtiqueta(rs.getString("etiqueta"));
				obj.setDescripcionCorta(rs.getString("descripcion_corta"));
				obj.setAbreviacion(rs.getString("abreviacion"));
				obj.setValor(rs.getDouble("valor"));
				obj.setPorcentajeUit(rs.getDouble("porcentaje_uit"));
				obj.setEstado(rs.getInt("estado")==1?Boolean.TRUE:Boolean.FALSE);
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public Integer registraCosta(Integer conceptoTasaId,Integer conceptoId,Integer subConceptoId,Integer periodo,Double valor,Integer estado)throws Exception{
		Integer result=Constante.RESULT_PENDING;
		try {
			String SQL = "{call dbo.stp_co_registra_costa(?,?,?,?,?,?)}";
			CallableStatement cs = connect().prepareCall(SQL.toString());
			cs.setInt(1, Util.nvl(conceptoTasaId,0));
			cs.setInt(2, Util.nvl(conceptoId,0));
			cs.setInt(3, Util.nvl(subConceptoId,0));
			cs.setInt(4, Util.nvl(periodo,0));
			cs.setDouble(5, Util.nvl(valor,0.0));
			cs.setInt(6, Util.nvl(estado,0));
			cs.executeUpdate();
			result=Constante.RESULT_SUCCESS;
		} catch (Exception e) {
			throw (e);
		}
		return result;		
	}
	
	public Integer duplicaCostaPeriodo(Integer periodoNuevo,Integer periodo,Integer usuarioId,String terminal)throws Exception{
		Integer result=Constante.RESULT_PENDING;
		try {
			String SQL = "{call dbo.stp_co_duplica_costa(?,?,?,?)}";
			CallableStatement cs = connect().prepareCall(SQL.toString());
			cs.setInt(1, Util.nvl(periodoNuevo,0));
			cs.setInt(2, Util.nvl(periodo,0));
			cs.setInt(3, usuarioId);
			cs.setString(4, terminal);
			cs.executeUpdate();
			result=Constante.RESULT_SUCCESS;
		} catch (Exception e) {
			throw (e);
		}
		return result;		
	}

	public List<GenericDTO> consultarMotivoDevolucion()throws Exception {
		List<GenericDTO> list = new ArrayList<GenericDTO>();
		try {
			String SQL = "dbo.stp_co_consulta_motivo_devolucion";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				GenericDTO obj = new GenericDTO();
				obj.setId(rs.getInt("id"));
				obj.setDescripcion(rs.getString("descripcion"));
				obj.setEstado(rs.getInt("estado")==1?Boolean.TRUE:Boolean.FALSE);
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}
	public List<GenericDTO> consultarMotivoDesacumula()throws Exception {
		List<GenericDTO> list = new ArrayList<GenericDTO>();
		try {
			String SQL = "dbo.stp_co_consulta_motivo_desacumula";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				GenericDTO obj = new GenericDTO();
				obj.setId(rs.getInt("id"));
				obj.setDescripcion(rs.getString("descripcion"));
				obj.setEstado(rs.getInt("estado")==1?Boolean.TRUE:Boolean.FALSE);
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}
	public List<GenericDTO> consultarTipoGestion()throws Exception {
		List<GenericDTO> list = new ArrayList<GenericDTO>();
		try {
			String SQL = "dbo.stp_co_consulta_tipo_gestion";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				GenericDTO obj = new GenericDTO();
				obj.setId(rs.getInt("id"));
				obj.setDescripcion(rs.getString("descripcion"));
				obj.setEstado(rs.getInt("estado")==1?Boolean.TRUE:Boolean.FALSE);
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public Integer registraTipoGestion(Integer id,String descripcion,Integer estado,Integer usuarioId,String terminal)throws Exception{
		Integer result=Constante.RESULT_PENDING;
		try {
			String SQL = "{call dbo.stp_co_registra_tipo_gestion(?,?,?,?,?)}";
			CallableStatement cs = connect().prepareCall(SQL.toString());
			cs.setInt(1, Util.nvl(id,0));
			cs.setString(2, Util.nvl(descripcion,""));
			cs.setInt(3, Util.nvl(estado,0));
			cs.setInt(4, usuarioId);
			cs.setString(5, terminal);
			cs.executeUpdate();
			result=Constante.RESULT_SUCCESS;
		} catch (Exception e) {
			throw (e);
		}
		return result;		
	}
	
	public Integer registraMotivoDevolucion(Integer id,String descripcion,Integer estado,Integer usuarioId,String terminal)throws Exception{
		Integer result=Constante.RESULT_PENDING;
		try {
			String SQL = "{call dbo.stp_co_registra_motivo_devolucion(?,?,?,?,?)}";
			CallableStatement cs = connect().prepareCall(SQL.toString());
			cs.setInt(1, Util.nvl(id,0));
			cs.setString(2, Util.nvl(descripcion,""));
			cs.setInt(3, Util.nvl(estado,0));
			cs.setInt(4, usuarioId);
			cs.setString(5, terminal);
			cs.executeUpdate();
			result=Constante.RESULT_SUCCESS;
		} catch (Exception e) {
			throw (e);
		}
		return result;		
	}
	public Integer registraMotivoDesacumula(Integer id,String descripcion,Integer estado,Integer usuarioId,String terminal)throws Exception{
		Integer result=Constante.RESULT_PENDING;
		try {
			String SQL = "{call dbo.stp_co_registro_motivo_desacumula(?,?,?,?,?)}";
			CallableStatement cs = connect().prepareCall(SQL.toString());
			cs.setInt(1, Util.nvl(id,0));
			cs.setString(2, Util.nvl(descripcion,""));
			cs.setInt(3, Util.nvl(estado,0));
			cs.setInt(4, usuarioId);
			cs.setString(5, terminal);
			cs.executeUpdate();
			result=Constante.RESULT_SUCCESS;
		} catch (Exception e) {
			throw (e);
		}
		return result;		
	}
	
	public Integer retiraValorCartera(Integer carteraId,Integer actoId,Integer usuarioId,String terminal)throws Exception{
		Integer result=Constante.RESULT_PENDING;
		try {
			String SQL = "{call dbo.stp_co_retira_valor_ex(?,?,?,?)}";
			CallableStatement cs = connect().prepareCall(SQL.toString());
			cs.setInt(1, Util.nvl(carteraId,0));
			cs.setInt(2, Util.nvl(actoId,0));
			cs.setInt(3, usuarioId);
			cs.setString(4, terminal);
			cs.executeUpdate();
			result=Constante.RESULT_SUCCESS;
		} catch (Exception e) {
			throw (e);
		}
		return result;
	}
	
	public Integer retiraExpedienteCartera(Integer carteraId,Integer prospectoId,Integer usuarioId,String terminal)throws Exception{
		Integer result=Constante.RESULT_PENDING;
		try {
			String SQL = "{call dbo.stp_co_retira_expediente_mc(?,?,?,?)}";
			CallableStatement cs = connect().prepareCall(SQL.toString());
			cs.setInt(1, Util.nvl(carteraId,0));
			cs.setInt(2, Util.nvl(prospectoId,0));
			cs.setInt(3, usuarioId);
			cs.setString(4, terminal);
			cs.executeUpdate();
			result=Constante.RESULT_SUCCESS;
		} catch (Exception e) {
			throw (e);
		}
		return result;
	}
	
	public Integer reasignarCartera(Integer carteraId,Integer coactivoId,Integer coactivoAsignarId,Integer usuarioId,String terminal)throws Exception{
		Integer result=Constante.RESULT_PENDING;
		try {
			String SQL = "{call dbo.stp_co_reasigna_cartera(?,?,?,?,?)}";
			CallableStatement cs = connect().prepareCall(SQL.toString());
			cs.setInt(1, Util.nvl(carteraId,0));
			cs.setInt(2, Util.nvl(coactivoId,0));
			cs.setInt(3, Util.nvl(coactivoAsignarId,0));
			cs.setInt(4, usuarioId);
			cs.setString(5, terminal);
			cs.executeUpdate();
			result=Constante.RESULT_SUCCESS;
		} catch (Exception e) {
			throw (e);
		}
		return result;
	}
	
	public Integer registraObsGestion(Integer gestionExpedienteId,String observacion,Integer usuarioId,String terminal)throws Exception{
		Integer result=Constante.RESULT_PENDING;
		try {
			String SQL = "{call dbo.stp_co_registra_obs_gestion(?,?,?,?)}";
			CallableStatement cs = connect().prepareCall(SQL.toString());
			cs.setInt(1, Util.nvl(gestionExpedienteId,0));
			cs.setString(2, Util.nvl(observacion,""));
			cs.setInt(3, usuarioId);
			cs.setString(4, terminal);
			cs.executeUpdate();
			result=Constante.RESULT_SUCCESS;
		} catch (Exception e) {
			throw (e);
		}
		return result;
	}
	
	public List<GestionValores> gestionConsultaValores(Integer expedienteId)throws Exception {
		List<GestionValores> list = new ArrayList<GestionValores>();
		try {
			String SQL = "dbo.stp_co_gestion_consulta_valores ?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, Util.nvl(expedienteId,0));
			ResultSet rs = pst.executeQuery();
			
			GestionValores obj;
			
			while (rs.next()) {
				obj = new GestionValores();
				obj.setExpedienteId(rs.getInt("expediente_id"));
				obj.setTipoValor(rs.getString("tipo_valor"));
				obj.setNroActo(rs.getString("nro_acto"));
				obj.setMontoDeuda(rs.getDouble("monto_deuda"));
				obj.setExigibiliad(rs.getString("exigibilidad"));
				obj.setPeriodoActo(rs.getString("anno_acto"));
				obj.setNroExpediente(rs.getString("nro_expediente"));
				obj.setDeudaActual(rs.getDouble("pendiente_pago"));
				obj.setEstadoDeuda(rs.getString("estado_deuda"));
				obj.setConcepto(rs.getString("concepto"));
				obj.setPlacaNroPap(rs.getString("placa_nro_pap"));
				
				
				list.add(obj);
			}
			
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}
	
	public List<GestionEventos> gestionConsultaEventosExpediente(Integer expedienteId)throws Exception {
		List<GestionEventos> list = new ArrayList<GestionEventos>();
		try {
			String SQL = "dbo.stp_co_gestion_consulta_gestion ?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, Util.nvl(expedienteId,0));
			ResultSet rs = pst.executeQuery();
			GestionEventos obj = new GestionEventos();
			
			
			
			while (rs.next()) {
				obj = new GestionEventos();
				
				obj.setExpedienteId(rs.getInt("expediente_id"));
				obj.setFechaGestion(rs.getString("fecha_registro"));
				obj.setTipoGestion(rs.getString("tipo_gestion"));
				obj.setResenaGestion(rs.getString("resena_gestion"));
				obj.setFechaNotificacion(rs.getString("fecha_notificacion"));
				obj.setResponsable(rs.getString("responsable"));
				obj.setRecId(rs.getInt("rec_id"));
				obj.setTipoRecId(rs.getInt("tipo_rec_id"));
				obj.setNroRec(rs.getString("nro_rec"));
				obj.setTipoDocumentoId(rs.getInt("tipo_documento_id"));
				obj.setFechaNotificacion(rs.getString("fecha_notificacion"));
				obj.setTipoGestionId(rs.getInt("tipo_gestion_id"));
				obj.setGestionExpedienteId(rs.getInt("gestion_expediente_id"));
				obj.setObservacion(rs.getString("observacion"));
				obj.setRecDocumentoId(rs.getInt("rec_documento_id"));
				obj.setDocumentoPdf(rs.getInt("documento_pdf"));
				
				
				
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public List<GenericDTO> listarTipoGestion()throws Exception {
		List<GenericDTO> list = new ArrayList<GenericDTO>();
		try {
			String SQL = new String("exec dbo.stp_co_listado_tipo_gestion");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				GenericDTO obj = new GenericDTO();
				obj.setId(rs.getInt("tipo_gestion_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}
	

	public Integer registraGestionExpediente(Integer expedienteId,Integer tipoGestionId,String resena,Date fechaGestion,Integer usuarioId,String terminal)throws Exception{
		Integer result=Constante.RESULT_PENDING;
		try {
			String SQL = "{call dbo.stp_co_gestion_registra_gestion(?,?,?,?,?,?)}";
			CallableStatement cs = connect().prepareCall(SQL.toString());
			cs.setInt(1, Util.nvl(expedienteId,0));
			cs.setInt(2, Util.nvl(tipoGestionId,0));
			cs.setString(3, Util.nvl(resena,""));
			cs.setInt(4, usuarioId);
			cs.setString(5, terminal);
			cs.setTimestamp(6, DateUtil.dateToSqlTimestamp(fechaGestion));
			int rowCount=cs.executeUpdate();
			result=Constante.RESULT_SUCCESS;
		} catch (Exception e) {
			throw (e);
		}
		return result;		
	}
	
	public Integer registraGestionCartera(Integer carteraId,Integer tipoGestionId,String resena,Integer usuarioId,String terminal)throws Exception{
		Integer result=Constante.RESULT_PENDING;
		try {
			String SQL = "{call dbo.stp_co_gestion_registra_gestion_cartera(?,?,?,?,?)}";
			CallableStatement cs = connect().prepareCall(SQL.toString());
			cs.setInt(1, Util.nvl(carteraId,0));
			cs.setInt(2, Util.nvl(tipoGestionId,0));
			cs.setString(3, Util.nvl(resena,""));
			cs.setInt(4, usuarioId);
			cs.setString(5, terminal);
			cs.executeUpdate();
			result=Constante.RESULT_SUCCESS;
		} catch (Exception e) {
			throw (e);
		}
		return result;		
	}
	
	public Integer registraResenaGestion(Integer carteraId,Integer tipoGestionId,String resena,Integer usuarioId,String terminal)throws Exception{
		Integer result=Constante.RESULT_PENDING;
		try {
			String SQL = "{call dbo.stp_co_gestion_registra_gestion_cartera(?,?,?,?,?)}";
			CallableStatement cs = connect().prepareCall(SQL.toString());
			cs.setInt(1, Util.nvl(carteraId,0));
			cs.setInt(2, Util.nvl(tipoGestionId,0));
			cs.setString(3, Util.nvl(resena,""));
			cs.setInt(4, usuarioId);
			cs.setString(5, terminal);
			cs.executeUpdate();
			result=Constante.RESULT_SUCCESS;
		} catch (Exception e) {
			throw (e);
		}
		return result;		
	}
	
	public Integer registraRecCartera(Integer carteraId,Integer tipoRecId,Integer usuarioId,String terminal)throws Exception{
		Integer result=Constante.RESULT_PENDING;
		try {
			String SQL = "{call dbo.stp_co_gestion_registra_rec_cartera(?,?,?,?)}";
			CallableStatement cs = connect().prepareCall(SQL.toString());
			cs.setInt(1, Util.nvl(carteraId,0));
			cs.setInt(2, Util.nvl(tipoRecId,0));
			cs.setInt(3, usuarioId);
			cs.setString(4, terminal);
			cs.executeUpdate();
			result=Constante.RESULT_SUCCESS;
		} catch (Exception e) {
			throw (e);
		}
		return result;		
	}
	
	
	public List<ExpedienteCoactivo> consultarExpedientesPersonaAcum(Integer personaId,Integer expedienteId)throws Exception {
		List<ExpedienteCoactivo> list = new ArrayList<ExpedienteCoactivo>();
		try {
			String SQL = "dbo.stp_co_gestion_consulta_expe_persona_acum ?,?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, Util.nvl(personaId,0));
			pst.setInt(2, Util.nvl(expedienteId,0));
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				ExpedienteCoactivo obj = new ExpedienteCoactivo();
				obj.setExpedienteId(rs.getInt("expediente_id"));
				obj.setProspectoId(Constante.RESULT_PENDING);
				obj.setNroExpediente(rs.getString("nro_expediente"));
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setApellidosNombresPersona(rs.getString("apellidos_nombres"));
				obj.setTotalDeuda(rs.getDouble("deuda_total"));
				obj.setFechaNotificacion(rs.getString("fecha_notificacion"));
				obj.setNroRec(rs.getString("nro_rec"));
				obj.setTotalDeuda(rs.getDouble("deuda_total"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}
	
	public List<ExpedienteCoactivo> consultarExpedientesPersonaDesAcum(Integer personaId,Integer expedienteId)throws Exception {
		List<ExpedienteCoactivo> list = new ArrayList<ExpedienteCoactivo>();
		try {
			String SQL = "dbo.stp_co_gestion_consulta_expe_persona_desacum ?,?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, Util.nvl(personaId,0));
			pst.setInt(2, Util.nvl(expedienteId,0));
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				ExpedienteCoactivo obj = new ExpedienteCoactivo();
				obj.setExpedienteId(rs.getInt("expediente_id"));
				obj.setProspectoId(Constante.RESULT_PENDING);
				obj.setNroExpediente(rs.getString("nro_expediente"));
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setApellidosNombresPersona(rs.getString("apellidos_nombres"));
				obj.setTotalDeuda(rs.getDouble("deuda_total"));
				obj.setFechaNotificacion(rs.getString("fecha_notificacion"));
				obj.setNroRec(rs.getString("nro_rec"));
				obj.setTotalDeuda(rs.getDouble("deuda_total"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public Integer registraAcumulacionExpediente(String listaProspectoId,Integer expedienteId,Integer personaId,String terminal,Integer usuarioId)throws Exception{
		Integer result=Constante.RESULT_PENDING;
		try {
			String SQL = "{call dbo.stp_co_registra_acumulacion_expediente(?,?,?,?,?)}";
			CallableStatement cs = connect().prepareCall(SQL.toString());
			cs.setString(1, Util.nvl(listaProspectoId,""));
			cs.setInt(2, Util.nvl(expedienteId,0));
			cs.setInt(3, Util.nvl(personaId,0));
			cs.setInt(4, usuarioId);
			cs.setString(5, terminal);
			cs.executeUpdate();
			result=Constante.RESULT_SUCCESS;
		} catch (Exception e) {
			throw (e);
		}
		return result;		
	}

	public Integer registraDesAcumulacionExpediente(String listaProspectoId,Integer expedienteId,Integer personaId,String terminal,Integer usuarioId)throws Exception{
		Integer result=Constante.RESULT_PENDING;
		try {
			String SQL = "{call dbo.stp_co_registra_desacumulacion_expediente(?,?,?,?,?)}";
			CallableStatement cs = connect().prepareCall(SQL.toString());
			cs.setString(1, Util.nvl(listaProspectoId,""));
			cs.setInt(2, Util.nvl(expedienteId,0));
			cs.setInt(3, Util.nvl(personaId,0));
			cs.setInt(4, usuarioId);
			cs.setString(5, terminal);
			cs.executeUpdate();
			result=Constante.RESULT_SUCCESS;
		} catch (Exception e) {
			throw (e);
		}
		return result;		
	}
	
	public Integer registraRecExpediente(String listExpedienteId,Integer conceptoID,Integer tipoRecId,Integer usuarioId,Integer usuario_ejecutor_id,Integer usuario_id_auxiliar,String terminal)throws Exception{
		Integer result=Constante.RESULT_PENDING;
		try {
			String SQL = "{call dbo.stp_co_gestion_genera_rec(?,?,?,?,?,?,?)}";
			CallableStatement cs = connect().prepareCall(SQL.toString());
			
			cs.setString(1, Util.nvl(listExpedienteId,""));
			cs.setInt(2, conceptoID);
			cs.setInt(3, tipoRecId);
			cs.setInt(4, usuarioId);
			cs.setInt(5, usuario_ejecutor_id);
			cs.setInt(6, usuario_id_auxiliar);
			cs.setString(7, terminal);
			
			
			boolean rowCount=cs.execute();
			if(rowCount){
				result=Constante.RESULT_SUCCESS;
			}
		} catch (Exception e) {
			throw (e);
		}
		return result;		
	}
	
	public Integer registraCostasExpediente(Integer expedienteId,Integer recId,String terminal,Integer usuarioId)throws Exception{
		Integer result=Constante.RESULT_PENDING;
		try {
			String SQL = "{call dbo.stp_co_gestion_registra_costas(?,?,?,?)}";
			CallableStatement cs = connect().prepareCall(SQL.toString());
			cs.setInt(1, Util.nvl(expedienteId,0));
			cs.setInt(2, Util.nvl(recId,0));
			cs.setString(3, terminal);
			cs.setInt(4, usuarioId);
			cs.execute();
			result=Constante.RESULT_SUCCESS;
		} catch (Exception e) {
			throw (e);
		}
		return result;
	}
	
	
	
	public Integer registraGastosExpediente(Integer expedienteId,Double montoGasto,Date fechaGasto,String resenaGasto,String comprobanteGasto,Integer recId,String terminal,Integer usuarioId)throws Exception{
		Integer result=Constante.RESULT_PENDING;
		try {
			String SQL = "{call dbo.stp_co_gestion_registra_gastos(?,?,?,?,?,?,?,?)}";
			CallableStatement cs = connect().prepareCall(SQL.toString());
			cs.setInt(1, Util.nvl(expedienteId,0));
			cs.setDouble(2, Util.nvl(montoGasto,0.0));
			cs.setTimestamp(3, DateUtil.dateToSqlTimestamp(fechaGasto));
			cs.setString(4, Util.nvl(resenaGasto,""));
			cs.setString(5, Util.nvl(comprobanteGasto,""));
			cs.setInt(6, Util.nvl(recId,0));
			cs.setString(7, terminal);
			cs.setInt(8, usuarioId);
			cs.execute();
			result=Constante.RESULT_SUCCESS;
		} catch (Exception e) {
			throw (e);
		}
		return result;
	}
	
	public List<GestionCostas> gestionConsultaCostasExpediente(Integer expedienteId)throws Exception {
		List<GestionCostas> list = new ArrayList<GestionCostas>();
		try {
			String SQL = "dbo.stp_co_gestion_consulta_costas ?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, Util.nvl(expedienteId,0));
			ResultSet rs = pst.executeQuery();
			GestionCostas obj; 
			
			while (rs.next()) {
				obj= new GestionCostas();
				obj.setRecId(rs.getInt("rec_id"));
				obj.setFechaEmision(rs.getString("fecha_emision"));
				obj.setConcepto(rs.getString("concepto"));
				obj.setSubConcepto(rs.getString("subconcepto"));
				obj.setMontoDeuda(rs.getDouble("total_deuda"));
				obj.setNroExpediente(rs.getString("nro_expediente"));
				obj.setNroRec(rs.getString("nro_rec"));
				obj.setEstadoDeuda(rs.getString("estado_deuda"));
				obj.setDeudaId(rs.getInt("deuda_id"));
				
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}
	
	public List<ExpedienteCoactivo> notificacionConsultaExpediente(String nroCartera,String nroExpediente,String nroPapeleta,Integer personaId,Integer ultimaRec)throws Exception {
		List<ExpedienteCoactivo> list = new ArrayList<ExpedienteCoactivo>();
		try {
			String SQL = "dbo.stp_co_notifica_consulta_expediente ?,?,?,?,?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setString(1, Util.nvl(nroCartera,""));
			pst.setString(2, Util.nvl(nroExpediente,""));
			pst.setString(3, Util.nvl(nroPapeleta,""));
			pst.setInt(4, Util.nvl(personaId,0));
			pst.setInt(5,ultimaRec);
			
			ResultSet rs = pst.executeQuery();
			ExpedienteCoactivo obj;
			String fechaEmision;
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
			
			while (rs.next()) {
				obj = new ExpedienteCoactivo();
				
				obj.setExpedienteId(rs.getInt("expediente_id"));
				obj.setNroExpediente(rs.getString("nro_expediente"));
				obj.setTotalDeuda(rs.getDouble("total_monto_deuda"));
				obj.setApellidosNombresPersona(rs.getString("apellidos_nombres_persona"));
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setRecId(rs.getInt("rec_id"));
				obj.setNroRec(rs.getString("nro_rec"));
				obj.setFechaExpediente(rs.getString("fecha_registro"));
				obj.setFechaNotificacion(rs.getString("fecha_notificacion"));
				obj.setApellidosNombresNotificador(rs.getString("apellidos_nombres_notificador"));
				obj.setFormaNotificacion(rs.getString("forma_notificacion"));
				obj.setUsuario(rs.getString("nombre_usuario"));
				obj.setNotificacionId(rs.getInt("notificacion_id"));
				obj.setFechaemision(rs.getDate("fecha_emision"));
				
				fechaEmision=sdf.format(obj.getFechaemision());
				obj.setFechaFormatoEmision(fechaEmision);				
				
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}
	
	//Creado por : Omar
	//Objetivo: Actualziar los datos de notificación de REC. 
	public Integer actualizarNotificacion(Integer  recId,Integer motivoNotificacionId,Integer notificadorId,Date fechaNotificacion,int usuario_id,String terminal)throws Exception
	{
		Integer result=Constante.RESULT_PENDING;
		try {
			
			String SQL = "{call dbo.stp_co_notificacion_actualiza (?,?,?,?,?,?)}";			
			CallableStatement cs = connect().prepareCall(SQL.toString());
			cs.setInt(1, Util.nvl(recId,0));
			cs.setInt(2, Util.nvl(motivoNotificacionId,0));
			cs.setInt(3, Util.nvl(notificadorId,0));
			cs.setTimestamp(4, DateUtil.dateToSqlTimestamp(fechaNotificacion));
			cs.setInt(5, usuario_id);
			cs.setString(6, terminal);
			
			cs.executeUpdate();
			result=Constante.RESULT_SUCCESS;
			
		} catch (Exception e) {
			throw (e);
		}
		return result;
	}
	
	public Integer registraNotificacionExpediente(Integer motivoNotificacionId,Integer notificadorId,Date fechaNotificacion,Integer recId,Integer usuarioId,String terminal)throws Exception{
		Integer result=Constante.RESULT_PENDING;
		try {
			String SQL = "{call dbo.stp_co_notifica_registra(?,?,?,?,?,?)}";
			CallableStatement cs = connect().prepareCall(SQL.toString());
			cs.setInt(1, Util.nvl(motivoNotificacionId,0));
			cs.setInt(2, Util.nvl(notificadorId,0));
			cs.setTimestamp(3, DateUtil.dateToSqlTimestamp(fechaNotificacion));
			cs.setInt(4, Util.nvl(recId,0));
			cs.setInt(5, usuarioId);
			cs.setString(6, terminal);
			cs.executeUpdate();
			result=Constante.RESULT_SUCCESS;
		} catch (Exception e) {
			throw (e);
		}
		return result;
	}
	
	public List<SituacionExigibilidad> reporteSituacionExigibilidad(Integer loteId,java.util.Date fechaDesde,java.util.Date fechaHasta,Integer materiaId)throws Exception {
		List<SituacionExigibilidad> list = new ArrayList<SituacionExigibilidad>();
		try {
			String SQL = "dbo.stp_co_reporte_situacion_exigibilidad ?,?,?,?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, Util.nvl(loteId,0));
			pst.setTimestamp(2, DateUtil.dateToSqlTimestamp(fechaDesde));
			pst.setTimestamp(3, DateUtil.dateToSqlTimestamp(fechaHasta));
			pst.setInt(4, Util.nvl(materiaId,0));
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				SituacionExigibilidad obj = new SituacionExigibilidad();
				obj.setLoteId(rs.getInt("lote_id"));
				obj.setTipoValor(rs.getString("tipo_acto"));
				obj.setCantidadValores(rs.getInt("cantidad_valores"));
				obj.setTotalDeudaPagada(rs.getDouble("deuda_pagada"));
				obj.setTotalDeudaRecibida(rs.getDouble("deuda_transferido"));
				obj.setCantidadValoresPendiente(rs.getInt("cantidad_valores_pendiente"));
				obj.setPeriodoDeuda(rs.getInt("periodo_deuda"));
				obj.setConcepto(rs.getString("concepto"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}
	
	public List<SituacionCartera> reporteSituacionCartera(Integer carteraId,java.util.Date fechaDesde,java.util.Date fechaHasta,Integer materiaId)throws Exception {
		List<SituacionCartera> list = new ArrayList<SituacionCartera>();
		try {
			String SQL = "dbo.stp_co_reporte_situacion_cartera ?,?,?,?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, Util.nvl(carteraId,0));
			pst.setTimestamp(2, DateUtil.dateToSqlTimestamp(fechaDesde));
			pst.setTimestamp(3, DateUtil.dateToSqlTimestamp(fechaHasta));
			pst.setInt(4, Util.nvl(materiaId,0));
			ResultSet rs = pst.executeQuery();
			SituacionCartera obj;
			
			while (rs.next()) {
				obj= new SituacionCartera();
				obj.setLoteId(rs.getInt("lote_id"));
				obj.setCarteraId(rs.getInt("cartera_id"));
				obj.setTipoValor(rs.getString("tipo_acto"));
				obj.setNroValor(rs.getString("nro_acto"));
				obj.setDeudaRecibida(rs.getDouble("monto_deuda"));
				obj.setEstadoGestion(rs.getString("situacion_cartera"));
				obj.setPeriodo(rs.getInt("anno_acto"));
				obj.setCoactivo(rs.getString("coactivo"));
				obj.setCostas(rs.getDouble("total_deuda_costas"));
				obj.setCostasPagada(rs.getDouble("deuda_cancelado_costas"));
				obj.setDeudaPagadaPreCoactiva(rs.getDouble("total_cancelado_valor_precoactiva"));
				obj.setDeudaPagadaCoactiva(rs.getDouble("total_cancelado_valor"));
				obj.setTotalDeudaValor(rs.getDouble("total_deuda_valor"));
				obj.setTotalCanceladoValor(rs.getDouble("total_cancelado_valor"));
				obj.setEstadoDeuda(rs.getString("estado_deuda"));
				obj.setConcepto(rs.getString("concepto"));
				//--
				obj.setApellidosNombresInfractor(rs.getString("datos_infractor"));
				obj.setCodigoInfractor(rs.getString("persona_infractor_id"));
				obj.setNroPlaca(rs.getString("placa"));
				obj.setNroPapeleta(rs.getString("nro_papeleta"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}
	
	public List<ControlExpediente> reporteControlExpediente(String nroExpediente,Integer coactivoId,java.util.Date fechaDesde,java.util.Date fechaHasta,Integer materiaId)throws Exception {
		List<ControlExpediente> list = new ArrayList<ControlExpediente>();
		try {
			String SQL = "dbo.stp_co_reporte_control_expediente ?,?,?,?,?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setString(1, Util.nvl(nroExpediente,""));
			pst.setInt(2, Util.nvl(coactivoId,0));
			pst.setTimestamp(3, DateUtil.dateToSqlTimestamp(fechaDesde));
			pst.setTimestamp(4, DateUtil.dateToSqlTimestamp(fechaHasta));
			pst.setInt(5, Util.nvl(materiaId,0));
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				ControlExpediente obj = new ControlExpediente();
				
				obj.setExpedienteId(rs.getInt("expediente_id"));
				obj.setNroExpediente(rs.getString("nro_expediente"));
				obj.setFechaRegistro(rs.getString("fecha_registro"));	
				
				obj.setTipoValor(rs.getString("tipo_acto"));
				obj.setConcepto(rs.getString("concepto"));
				obj.setPeriodoValor(rs.getString("anno_acto"));
				obj.setNroValor(rs.getString("nro_acto"));
				obj.setDeudaValor(rs.getDouble("monto_deuda"));
				
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setApellidosNombres(rs.getString("apellidos_nombres"));
				
				obj.setCantidadRC(rs.getInt("cantidad_rc"));
				obj.setTipoUltimaRC(rs.getString("tipo_rec"));
				obj.setFechaNotificaUltimaRC(rs.getString("fecha_notificacion_rc"));
				obj.setNroRC(rs.getString("nro_rec"));
				
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}
	
	public List<SituacionDeuda> reporteSituacionDeuda(Integer carteraId,Integer personaId)throws Exception {
		List<SituacionDeuda> list = new ArrayList<SituacionDeuda>();
		try {
			String SQL = "dbo.stp_co_reporte_situacion_deuda ?,?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, Util.nvl(carteraId,0));
			pst.setInt(2, Util.nvl(personaId,0));
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				SituacionDeuda obj = new SituacionDeuda();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setApellidosNombres(rs.getString("apellidos_nombres"));
				obj.setTipoActo(rs.getString("tipo_acto"));
				obj.setNroActo(rs.getString("nro_acto"));
				obj.setAnnoActo(rs.getInt("anno_acto"));
				obj.setInsoluto(rs.getDouble("insoluto"));
				obj.setReajuste(rs.getDouble("reajuste"));
				obj.setIntereses(rs.getDouble("intereses"));
				obj.setDerechoEmision(rs.getDouble("derecho_emision"));
				obj.setTotalDeuda(rs.getDouble("total_deuda"));
				
				obj.setNroExpediente(rs.getString("nro_expediente"));
				obj.setFechaRegistro(rs.getString("fecha_registro"));
				obj.setNroREC(rs.getString("nro_rec"));
				obj.setTipoRec(rs.getString("tipo_rec"));
				
				obj.setCoactivo(rs.getString("coactivo"));
				obj.setEstadoDeuda(rs.getString("estado_deuda"));
				obj.setTotalCancelado(rs.getDouble("total_cancelado"));
				
				obj.setConcepto(rs.getString("concepto"));
				obj.setFechaNotificacion(rs.getString("fecha_notificacion"));
				
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}
	
	
	public CoCartera consultaCartera(String nroCartera)throws Exception {
		CoCartera obj=new CoCartera(); 
		try {
			String SQL = "dbo.stp_co_consulta_cartera ?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setString(1, Util.nvl(nroCartera,""));
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				obj.setCarteraId(rs.getInt("cartera_id"));
				obj.setUltTipoRecId(rs.getInt("ult_tipo_rec_id"));
			}
		} catch (Exception e) {
			throw (e);
		}
		return obj;
	}
	
	public String validaGeneraRec(Integer expedienteId,Integer tipoRecId)throws Exception {
		String mensaje=null; 
		try {
			String SQL = "dbo.stp_co_gestion_valida_genera_rec ?,?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, Util.nvl(expedienteId,0));
			pst.setInt(2, Util.nvl(tipoRecId,0));
			
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				mensaje=rs.getString("mensaje");
			}
		} catch (Exception e) {
			throw (e);
		}
		return mensaje;
	}
	/**
	 * Modulo Coactiva V2 ::Fin
	 */
}