package com.sat.sisat.tramitedocumentario.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.sat.sisat.common.dao.GeneralDao;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.persistence.entity.TdDocumentoAnexo;
import com.sat.sisat.persistence.entity.TdDocumentoTramite;
import com.sat.sisat.persistence.entity.TdRequisito;
import com.sat.sisat.persistence.entity.TdRequisitoExpediente;
import com.sat.sisat.tramitedocumentario.dto.BusquedaExpedienteDTO;
import com.sat.sisat.tramitedocumentario.dto.DeclaracionJuradaAdultDTO;
import com.sat.sisat.tramitedocumentario.dto.ItemBandejaEntradaDTO;
import com.sat.sisat.tramitedocumentario.dto.ItemHistoricoEntradaDTO;
import com.sat.sisat.tramitedocumentario.dto.ItemSeguimientoEntradaDTO;
import com.sat.sisat.tramitedocumentario.dto.ItemSeguimientoExpedienteDTO;
import com.sat.sisat.tramitedocumentario.dto.RelacionadosDTO;
import com.sat.sisat.tramitedocumentario.dto.RequisitoExpededienteDTO;
import com.sat.sisat.tramitedocumentario.dto.ResolutorDTO;
import com.sat.sisat.tramitedocumentario.dto.ResumenReporteExpedientesDTO;

public class TramiteDocumentarioDao extends GeneralDao {

	public List<ItemBandejaEntradaDTO> obtenerExpedientes(BusquedaExpedienteDTO busquedaExpedienteDTO)
			throws SisatException {

		List<ItemBandejaEntradaDTO> listItemBandejaEntrada = new ArrayList<ItemBandejaEntradaDTO>();

		String q = "exec stp_td_obtener_expedientes ?,?,?,?,?,?,?,?,?,?,?"; //Se agrega criterio de situacion
		PreparedStatement pstm;
		try {
			pstm = connect().prepareStatement(q);

			if (busquedaExpedienteDTO.getNroExpediente() != null && !busquedaExpedienteDTO.getNroExpediente().isEmpty()) {
				pstm.setString(1, busquedaExpedienteDTO.getNroExpediente());
			} else {
				pstm.setNull(1, Types.VARCHAR);
			}
			
			if (busquedaExpedienteDTO.getNroExpedienteAnterior() != null && !busquedaExpedienteDTO.getNroExpedienteAnterior().isEmpty()) {
				pstm.setString(2, busquedaExpedienteDTO.getNroExpedienteAnterior());
			} else {
				pstm.setNull(2, Types.VARCHAR);
			}

			if (busquedaExpedienteDTO.getFechaRecepcion() != null) {
				pstm.setTimestamp(3, new Timestamp(busquedaExpedienteDTO.getFechaRecepcion().getTime()));
			} else {
				pstm.setNull(3, Types.TIMESTAMP);
			}
			if (busquedaExpedienteDTO.getUnidadId() != null) {
				pstm.setInt(4, busquedaExpedienteDTO.getUnidadId().intValue());
			} else {
				pstm.setNull(4, Types.INTEGER);
			}
			if (busquedaExpedienteDTO.getProcedimientoId() != null && busquedaExpedienteDTO.getProcedimientoId().intValue()>0) {
				pstm.setInt(5, busquedaExpedienteDTO.getProcedimientoId().intValue());
			} else {
				pstm.setNull(5, Types.INTEGER);
			}
			if (busquedaExpedienteDTO.getTipoTramiteId() != null && busquedaExpedienteDTO.getTipoTramiteId().intValue()>0) {
				pstm.setInt(6, busquedaExpedienteDTO.getTipoTramiteId().intValue());
			} else {
				pstm.setNull(6, Types.INTEGER);
			}
			if (busquedaExpedienteDTO.getDocumentoTramiteId() != null && busquedaExpedienteDTO.getDocumentoTramiteId().intValue()>0) {
				pstm.setInt(7, busquedaExpedienteDTO.getDocumentoTramiteId().intValue());
			} else {
				pstm.setNull(7, Types.INTEGER);
			}
			if (busquedaExpedienteDTO.getEstadoExpediente() != null ) {
				pstm.setInt(8, busquedaExpedienteDTO.getEstadoExpediente().intValue());
			} else {
				pstm.setNull(8, Types.INTEGER);
			}
			
			if (busquedaExpedienteDTO.getNombreContribuyente() != null && !busquedaExpedienteDTO.getNombreContribuyente().isEmpty()) {
				pstm.setString(9, busquedaExpedienteDTO.getNombreContribuyente());
			} else {
				pstm.setNull(9, Types.VARCHAR);
			}
			//nuevos criterios de busqueda
			if (busquedaExpedienteDTO.getSituacionExpediente() != null ) {
				pstm.setInt(10, busquedaExpedienteDTO.getSituacionExpediente().intValue());
			} else {
				pstm.setNull(10, Types.INTEGER);
			}
			if (busquedaExpedienteDTO.getUsuarioExp() != null ) {
				pstm.setInt(11, busquedaExpedienteDTO.getUsuarioExp().intValue());
			} else {
				pstm.setNull(11, Types.INTEGER);
			}
			
			ResultSet rs = pstm.executeQuery();
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
			Date fechaPresentacion = null;
			
			while (rs.next()) {
				ItemBandejaEntradaDTO item = new ItemBandejaEntradaDTO();
				
				/** Para obtener el anio actual de la fecha de presentacion y concatenar luego en Expedente generico*/
				fechaPresentacion = new Date(rs.getTimestamp("fecha_presentacion").getTime());
								
				item.setExpedienteId(rs.getInt("expediente_id"));
				item.setNroExpedienteGenerico(rs.getString("nro_expediente_generico")+'-'+dateFormat.format(fechaPresentacion));
				item.setCodExpediente(rs.getString("nro_expediente"));
				item.setNroExpedienteAnterior(rs.getString("nro_expediente_anterior"));
				item.setProcedimientoId(rs.getInt("procedimiento_id"));
				item.setProcedimientoAsString(rs.getString("procedimiento_desc"));				
				item.setTipoTramite(rs.getString("tipo_tramite"));
				item.setReferenciaOrDocumentoTramiteAsString(rs.getString("referencia_docu_tramite"));
				item.setEstado(rs.getInt("estado"));
				item.setFechaRecepcion(new Date(rs.getTimestamp("fecha_presentacion").getTime()));
				item.setNombreContribuyente(rs.getString("nombres_apellidos_final"));
				if(rs.getTimestamp("fecha_asignacion") != null){
					item.setFechaAsignacion(new Date(rs.getTimestamp("fecha_asignacion").getTime()));
				}else{
					item.setFechaAsignacion(null);
				}
				
				item.setArea(rs.getString("unidad_as_string"));
				item.setEstadoExpediente(rs.getInt("estado_expediente"));
				item.setSituacionExpediente(rs.getInt("situacion_expediente"));
				item.setUsuarioAsString(rs.getString("usuario_as_string"));
				item.setUsuarioRegistrador(rs.getString("nombre_usuario"));
				
				listItemBandejaEntrada.add(item);
			}

		} catch (SQLException e) {
			throw new SisatException(e.getMessage());
		}

		return listItemBandejaEntrada;
	}
	
	public List<DeclaracionJuradaAdultDTO> obtenerDjAdult(DeclaracionJuradaAdultDTO busquedaDjAdultDTO)
			throws SisatException {

		List<DeclaracionJuradaAdultDTO> listDj = new ArrayList<DeclaracionJuradaAdultDTO>();

		String q = "exec stp_td_Dj_obtener_DjAdult ?,?,?,?,?,?,?,?,?,?,?"; //Se agrega criterio de situacion
		PreparedStatement pstm;
		try {
			pstm = connect().prepareStatement(q);

			if (busquedaDjAdultDTO.getNroExpediente() != null && !busquedaDjAdultDTO.getNroExpediente().isEmpty()) {
				pstm.setString(1, busquedaDjAdultDTO.getNroExpediente());
			} else {
				pstm.setNull(1, Types.VARCHAR);
			}
			
			if (busquedaDjAdultDTO.getNroExpedienteAnterior() != null && !busquedaDjAdultDTO.getNroExpedienteAnterior().isEmpty()) {
				pstm.setString(2, busquedaDjAdultDTO.getNroExpedienteAnterior());
			} else {
				pstm.setNull(2, Types.VARCHAR);
			}

			if (busquedaDjAdultDTO.getFechaRegistro() != null) {
				pstm.setTimestamp(3, new Timestamp(busquedaDjAdultDTO.getFechaRegistro().getTime()));
			} else {
				pstm.setNull(3, Types.TIMESTAMP);
			}
			if (busquedaDjAdultDTO.getUnidadId() != null) {
				pstm.setInt(4, busquedaDjAdultDTO.getUnidadId().intValue());
			} else {
				pstm.setNull(4, Types.INTEGER);
			}
			if (busquedaDjAdultDTO.getProcedimientoId() != null && busquedaDjAdultDTO.getProcedimientoId().intValue()>0) {
				pstm.setInt(5, busquedaDjAdultDTO.getProcedimientoId().intValue());
			} else {
				pstm.setNull(5, Types.INTEGER);
			}
			if (busquedaDjAdultDTO.getTipoTramiteId() != null && busquedaDjAdultDTO.getTipoTramiteId().intValue()>0) {
				pstm.setInt(6, busquedaDjAdultDTO.getTipoTramiteId().intValue());
			} else {
				pstm.setNull(6, Types.INTEGER);
			}
			if (busquedaDjAdultDTO.getDocuTramiteId() != null && busquedaDjAdultDTO.getDocuTramiteId().intValue()>0) {
				pstm.setInt(7, busquedaDjAdultDTO.getDocuTramiteId().intValue());
			} else {
				pstm.setNull(7, Types.INTEGER);
			}
			if (busquedaDjAdultDTO.getEstadoExpediente() != null ) {
				pstm.setInt(8, busquedaDjAdultDTO.getEstadoExpediente().intValue());
			} else {
				pstm.setNull(8, Types.INTEGER);
			}
			
			if (busquedaDjAdultDTO.getNombreContribuyente() != null && !busquedaDjAdultDTO.getNombreContribuyente().isEmpty()) {
				pstm.setString(9, busquedaDjAdultDTO.getNombreContribuyente());
			} else {
				pstm.setNull(9, Types.VARCHAR);
			}
			//nuevos criterios de busqueda
			if (busquedaDjAdultDTO.getSituacionExpediente() != null ) {
				pstm.setInt(10, busquedaDjAdultDTO.getSituacionExpediente().intValue());
			} else {
				pstm.setNull(10, Types.INTEGER);
			}
			if (busquedaDjAdultDTO.getUsuarioId() != null ) {
				pstm.setInt(11, busquedaDjAdultDTO.getUsuarioId().intValue());
			} else {
				pstm.setNull(11, Types.INTEGER);
			}
			
			ResultSet rs = pstm.executeQuery();
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
			Date fechaPresentacion = null;
			
			while (rs.next()) {
				DeclaracionJuradaAdultDTO item = new DeclaracionJuradaAdultDTO();
				
				/** Para obtener el anio actual de la fecha de presentacion y concatenar luego en Expedente generico*/
				fechaPresentacion = new Date(rs.getTimestamp("fecha_presentacion").getTime());
								
				item.setDjId(rs.getInt("dj_id"));
				item.setNroExpedienteGenerico(rs.getString("nro_expediente_generico")+'-'+dateFormat.format(fechaPresentacion));
				item.setNroExpediente(rs.getString("nro_expediente"));
				//item.setNroExpedienteAnterior(rs.getString("nro_expediente_anterior"));
				item.setProcedimientoId(rs.getInt("procedimiento_id"));
				item.setProcedimientoAsString(rs.getString("procedimiento_desc"));				
				item.setTipoTramite(rs.getString("tipo_tramite"));
				item.setReferenciaOrDocumentoTramiteAsString(rs.getString("referencia_docu_tramite"));
				item.setEstado(rs.getInt("estado"));
				item.setFechaPresentacion(new Date(rs.getTimestamp("fecha_presentacion").getTime()));
				item.setNombreContribuyente(rs.getString("nombres_apellidos_final"));
			//	if(rs.getTimestamp("fecha_asignacion") != null){
			//		item.setFechaAsignacion(new Date(rs.getTimestamp("fecha_asignacion").getTime()));
			//	}else{
			//		item.setFechaAsignacion(null);
			//	}
				
				item.setArea(rs.getString("unidad_as_string"));
				item.setEstadoExpediente(rs.getInt("estado_expediente"));
				item.setSituacionExpediente(rs.getInt("situacion_expediente"));
				item.setUsuarioAsString(rs.getString("usuario_as_string"));
			//	item.setUsuarioRegistrador(rs.getString("nombre_usuario"));
				
				listDj.add(item);
			}

		} catch (SQLException e) {
			throw new SisatException(e.getMessage());
		}

		return listDj;
	}
	
	//Servicios
	public List<ItemBandejaEntradaDTO> obtenerExpedientesServAdult(BusquedaExpedienteDTO busquedaExpedienteDTO)
			throws SisatException {

		List<ItemBandejaEntradaDTO> listItemBandejaEntrada = new ArrayList<ItemBandejaEntradaDTO>();

		String q = "exec stp_td_obtener_expedientes_servicios_adulto ?,?,?,?,?,?,?,?,?,?,?"; //Se agrega criterio de situacion
		PreparedStatement pstm;
		try {
			pstm = connect().prepareStatement(q);

			if (busquedaExpedienteDTO.getNroExpediente() != null && !busquedaExpedienteDTO.getNroExpediente().isEmpty()) {
				pstm.setString(1, busquedaExpedienteDTO.getNroExpediente());
			} else {
				pstm.setNull(1, Types.VARCHAR);
			}
			
			if (busquedaExpedienteDTO.getNroExpedienteAnterior() != null && !busquedaExpedienteDTO.getNroExpedienteAnterior().isEmpty()) {
				pstm.setString(2, busquedaExpedienteDTO.getNroExpedienteAnterior());
			} else {
				pstm.setNull(2, Types.VARCHAR);
			}

			if (busquedaExpedienteDTO.getFechaRecepcion() != null) {
				pstm.setTimestamp(3, new Timestamp(busquedaExpedienteDTO.getFechaRecepcion().getTime()));
			} else {
				pstm.setNull(3, Types.TIMESTAMP);
			}
			if (busquedaExpedienteDTO.getUnidadId() != null) {
				pstm.setInt(4, busquedaExpedienteDTO.getUnidadId().intValue());
			} else {
				pstm.setNull(4, Types.INTEGER);
			}
			if (busquedaExpedienteDTO.getProcedimientoId() != null && busquedaExpedienteDTO.getProcedimientoId().intValue()>0) {
				pstm.setInt(5, busquedaExpedienteDTO.getProcedimientoId().intValue());
			} else {
				pstm.setNull(5, Types.INTEGER);
			}
			if (busquedaExpedienteDTO.getTipoTramiteId() != null && busquedaExpedienteDTO.getTipoTramiteId().intValue()>0) {
				pstm.setInt(6, busquedaExpedienteDTO.getTipoTramiteId().intValue());
			} else {
				pstm.setNull(6, Types.INTEGER);
			}
			if (busquedaExpedienteDTO.getDocumentoTramiteId() != null && busquedaExpedienteDTO.getDocumentoTramiteId().intValue()>0) {
				pstm.setInt(7, busquedaExpedienteDTO.getDocumentoTramiteId().intValue());
			} else {
				pstm.setNull(7, Types.INTEGER);
			}
			if (busquedaExpedienteDTO.getEstadoExpediente() != null ) {
				pstm.setInt(8, busquedaExpedienteDTO.getEstadoExpediente().intValue());
			} else {
				pstm.setNull(8, Types.INTEGER);
			}
			
			if (busquedaExpedienteDTO.getNombreContribuyente() != null && !busquedaExpedienteDTO.getNombreContribuyente().isEmpty()) {
				pstm.setString(9, busquedaExpedienteDTO.getNombreContribuyente());
			} else {
				pstm.setNull(9, Types.VARCHAR);
			}
			//nuevos criterios de busqueda
			if (busquedaExpedienteDTO.getSituacionExpediente() != null ) {
				pstm.setInt(10, busquedaExpedienteDTO.getSituacionExpediente().intValue());
			} else {
				pstm.setNull(10, Types.INTEGER);
			}
			if (busquedaExpedienteDTO.getUsuarioExp() != null ) {
				pstm.setInt(11, busquedaExpedienteDTO.getUsuarioExp().intValue());
			} else {
				pstm.setNull(11, Types.INTEGER);
			}
			
			ResultSet rs = pstm.executeQuery();
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
			Date fechaPresentacion = null;
			
			while (rs.next()) {
				ItemBandejaEntradaDTO item = new ItemBandejaEntradaDTO();
				
				/** Para obtener el anio actual de la fecha de presentacion y concatenar luego en Expedente generico*/
				fechaPresentacion = new Date(rs.getTimestamp("fecha_presentacion").getTime());
								
				item.setExpedienteId(rs.getInt("expediente_id"));
				item.setNroExpedienteGenerico(rs.getString("nro_expediente_generico")+'-'+dateFormat.format(fechaPresentacion));
				item.setCodExpediente(rs.getString("nro_expediente"));
				item.setNroExpedienteAnterior(rs.getString("nro_expediente_anterior"));
				item.setProcedimientoId(rs.getInt("procedimiento_id"));
				item.setProcedimientoAsString(rs.getString("procedimiento_desc"));				
				item.setTipoTramite(rs.getString("tipo_tramite"));
				item.setReferenciaOrDocumentoTramiteAsString(rs.getString("referencia_docu_tramite"));
				item.setEstado(rs.getInt("estado"));
				item.setFechaRecepcion(new Date(rs.getTimestamp("fecha_presentacion").getTime()));
				item.setNombreContribuyente(rs.getString("nombres_apellidos_final"));
				if(rs.getTimestamp("fecha_asignacion") != null){
					item.setFechaAsignacion(new Date(rs.getTimestamp("fecha_asignacion").getTime()));
				}else{
					item.setFechaAsignacion(null);
				}
				
				item.setArea(rs.getString("unidad_as_string"));
				item.setEstadoExpediente(rs.getInt("estado_expediente"));
				item.setSituacionExpediente(rs.getInt("situacion_expediente"));
				item.setUsuarioAsString(rs.getString("usuario_as_string"));
				item.setUsuarioRegistrador(rs.getString("nombre_usuario"));
				
				listItemBandejaEntrada.add(item);
			}

		} catch (SQLException e) {
			throw new SisatException(e.getMessage());
		}

		return listItemBandejaEntrada;
	}
	
	//para historico expediente
	public List<ItemHistoricoEntradaDTO> obtenerExpedientesh(Integer expedienteId)
			throws SisatException {

		List<ItemHistoricoEntradaDTO> listItemHistoricoEntrada = new ArrayList<ItemHistoricoEntradaDTO>();

		String h = "exec stp_td_obtener_expedientesh ?";
		PreparedStatement pstm;
		try {
			pstm = connect().prepareStatement(h);

			if (expedienteId != null && expedienteId>0) {
				pstm.setInt(1, expedienteId);
			} else {
				pstm.setNull(1, Types.INTEGER);
			}			
			
			ResultSet rs = pstm.executeQuery();
						
			while (rs.next()) {
				ItemHistoricoEntradaDTO item = new ItemHistoricoEntradaDTO();
				
				item.setExpedienteTransaccionId(rs.getInt("expediente_transaccion_id"));
				item.setFechaMovimiento(new Date(rs.getTimestamp("fecha_movimiento").getTime()));
				item.setExpedienteId(rs.getInt("expediente_id"));
				item.setProcedimiento(rs.getString("procedimiento"));
				item.setTramite(rs.getString("tramite"));
				item.setExpediente(rs.getString("expediente"));
				item.setNroExpediente(rs.getString("nro_expediente"));
				item.setNroExpedienteAnterior(rs.getString("nro_expediente_anterior"));
				item.setRemitente(rs.getString("remitente"));
				item.setDocRemite(rs.getString("doc_remite"));
				item.setFirmante(rs.getString("firmante"));
				item.setDocFirma(rs.getString("doc_firma"));
				item.setAreaDescripcion(rs.getString("area_descripcion"));
				item.setDescCorta(rs.getString("desc_corta"));
				item.setObservacion(rs.getString("observacion"));
				item.setFechaRegistro(new Date (rs.getTimestamp("fecha_registro").getTime()));
				item.setFechaPresentacion(new Date (rs.getTimestamp("fecha_presentacion").getTime()));
				item.setFechaActualizacion(new Date (rs.getTimestamp("fecha_actualizacion").getTime()));
				item.setNroFolios(rs.getInt("nro_folios"));
				item.setReferencia(rs.getString("referencia"));
				item.setEstado(rs.getInt("estado"));
				item.setEstadoExpediente(rs.getString("estado_expediente"));
				item.setSituacionExpediente(rs.getString("situacion_expediente"));
				item.setNroFedat(rs.getInt("nro_fedat"));
				item.setAsunto(rs.getString("asunto"));
				item.setUsuario(rs.getString("usuario"));
				item.setUsuarioCompleto(rs.getString("usuario_completo"));
								
				listItemHistoricoEntrada.add(item);
			}

		} catch (SQLException e) {
			throw new SisatException(e.getMessage());
		}

		return listItemHistoricoEntrada;
	}

	//para seguimiento expediente detalle
	public List<ItemSeguimientoEntradaDTO> seguimientoExpediente(Integer expedienteId)
			throws SisatException {

		List<ItemSeguimientoEntradaDTO> listItemSeguimientoEntrada = new ArrayList<ItemSeguimientoEntradaDTO>();
		
		String h = "exec stp_td_seguimiento_expedientes_detalle ?";
		PreparedStatement pstm;
		try {
			pstm = connect().prepareStatement(h);

			if (expedienteId != null && expedienteId>0) {
				pstm.setInt(1, expedienteId);
			} else {
				pstm.setNull(1, Types.INTEGER);
			}			
			
			ResultSet rs = pstm.executeQuery();
						
			while (rs.next()) {
				ItemSeguimientoEntradaDTO item = new ItemSeguimientoEntradaDTO();
				
				item.setExpedienteId(rs.getInt("expediente_id"));
				item.setFechaSeg(new Date(rs.getTimestamp("fecha_seg").getTime()));
				item.setOficinaOrigen(rs.getString("oficina_origen"));
				item.setOficinaOrigenDes(rs.getString("oficina_origen_des"));
				item.setOficinaDestino(rs.getString("oficina_destino"));
				item.setOficinaDestinoDes(rs.getString("oficina_destino_des"));
				item.setUsuarioOrigen(rs.getString("usuario_origen"));
				item.setUsuarioOrigenDes(rs.getString("usuario_origen_des"));
				item.setUsuarioDestino(rs.getString("usuario_destino"));
				item.setUsuarioDestinoDes(rs.getString("usuario_destino_des"));
				item.setObsevacionExpe(rs.getString("observacion_expe"));
				item.setSituacionExpediente(rs.getString("situacion_expediente"));
				item.setEstadoExpediente(rs.getString("estado_expediente"));
								
				listItemSeguimientoEntrada.add(item);
			}

		} catch (SQLException e) {
			throw new SisatException(e.getMessage());
		}

		return listItemSeguimientoEntrada;
	}
	
	//para seguimiento expediente detalle cabecera
		public List<ItemSeguimientoExpedienteDTO> seguimientoExpedientec(Integer expedienteId)
				throws SisatException {

			List<ItemSeguimientoExpedienteDTO> listItemSeguimientoExpediente= new ArrayList<ItemSeguimientoExpedienteDTO>();
			
			String h = "exec stp_td_seguimiento_expedientes_cabecera ?";
			PreparedStatement pstm;
			try {
				pstm = connect().prepareStatement(h);

				if (expedienteId != null && expedienteId>0) {
					pstm.setInt(1, expedienteId);
				} else {
					pstm.setNull(1, Types.INTEGER);
				}			
				
				ResultSet rs = pstm.executeQuery();
							
				while (rs.next()) {
					ItemSeguimientoExpedienteDTO item = new ItemSeguimientoExpedienteDTO();
										
					item.setExpedienteId(rs.getInt("expediente_id"));
					item.setExpediente(rs.getString("expediente"));
					item.setNroExpediente(rs.getString("nro_expediente"));
					item.setFechaRegistro(new Date(rs.getTimestamp("fecha_registro").getTime()));
					item.setProcedimiento(rs.getString("procedimiento"));
					item.setTramite(rs.getString("tramite"));
					item.setRemitente(rs.getString("remitente"));
					item.setDocRemite(rs.getString("doc_remite"));
					item.setFirmante(rs.getString("firmante"));
					item.setDocFirma(rs.getString("doc_firma"));
					item.setDireccion(rs.getString("direccion"));
					item.setCargo(rs.getString("cargo"));
					item.setFechaPresentacion(new Date(rs.getTimestamp("fecha_presentacion").getTime()));
					item.setNroExpedienteAnterior(rs.getString("nro_expediente_anterior"));
					item.setDocumento(rs.getString("documento"));
					item.setAsunto(rs.getString("asunto"));
					item.setFechaActualizacion(new Date(rs.getTimestamp("fecha_actualizacion").getTime()));
					item.setNroFolios(rs.getInt("nro_folios"));
					item.setNroExpFedat(rs.getInt("nro_exp_fedat"));
					item.setUsuario(rs.getString("usuario"));
					item.setUsuarioDes(rs.getString("usuario_des"));
					item.setNombreArchivo(rs.getString("archivo_nombre"));
					item.setUbicacionArchivo(rs.getString("archivo_ubicacion"));
					
					listItemSeguimientoExpediente.add(item);
				}

			} catch (SQLException e) {
				throw new SisatException(e.getMessage());
			}

			return listItemSeguimientoExpediente;
		}
	
	public List<TdDocumentoTramite> getAllDocumentoTramitesByTipoTramite(Integer tipoTramiteId) throws SisatException {

		List<TdDocumentoTramite> listTdDocumentoTramites = new ArrayList<TdDocumentoTramite>();

		StringBuffer sb = new StringBuffer();

		sb.append("select tdt.* from td_documento_tramite tdt ");
		sb.append("inner join td_tipo_tramite_documento ttd on tdt.docu_tramite_id = ttd.docu_tramite_id ");
		sb.append("inner join td_tipo_tramite tt on tt.tipo_tramite_id = ttd.tipo_tramite_id ");
		sb.append(" where tt.tipo_tramite_id = ?");
		PreparedStatement pstm;
		try {
			pstm = connect().prepareStatement(sb.toString());
			pstm.setInt(1, tipoTramiteId.intValue());

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				TdDocumentoTramite tdDocumentoTramite = new TdDocumentoTramite();

				tdDocumentoTramite.setDocuTramiteId(rs.getInt("docu_tramite_id"));
				tdDocumentoTramite.setDescCorta(rs.getString("desc_corta"));
				tdDocumentoTramite.setDescripcion(rs.getString("descripcion"));
				tdDocumentoTramite.setEstado(rs.getString("estado"));
				tdDocumentoTramite.setFechaRegistro(new Date(rs.getTimestamp("fecha_registro").getTime()));
				tdDocumentoTramite.setUsuarioId(rs.getInt("usuario_id"));
				tdDocumentoTramite.setTerminal(rs.getString("terminal"));

				listTdDocumentoTramites.add(tdDocumentoTramite);
			}

		} catch (SQLException e) {
			throw new SisatException(e.getMessage());
		}

		return listTdDocumentoTramites;
	}

	//Servicios Adulto mayor
	
	public List<TdDocumentoTramite> getDocumentoTramitesByTipoTramiteAdult(Integer tipoTramiteId) throws SisatException {

		List<TdDocumentoTramite> listTdDocumentoTramites = new ArrayList<TdDocumentoTramite>();

		StringBuffer sb = new StringBuffer();

		sb.append("select tdt.* from td_documento_tramite tdt ");
		sb.append("inner join td_tipo_tramite_documento ttd on tdt.docu_tramite_id = ttd.docu_tramite_id ");
		sb.append("inner join td_tipo_tramite tt on tt.tipo_tramite_id = ttd.tipo_tramite_id ");
		sb.append(" where tt.tipo_tramite_id = ?");
		PreparedStatement pstm;
		try {
			pstm = connect().prepareStatement(sb.toString());
			pstm.setInt(1, tipoTramiteId.intValue());

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				TdDocumentoTramite tdDocumentoTramite = new TdDocumentoTramite();

				tdDocumentoTramite.setDocuTramiteId(rs.getInt("docu_tramite_id"));
				tdDocumentoTramite.setDescCorta(rs.getString("desc_corta"));
				tdDocumentoTramite.setDescripcion(rs.getString("descripcion"));
				tdDocumentoTramite.setEstado(rs.getString("estado"));
				tdDocumentoTramite.setFechaRegistro(new Date(rs.getTimestamp("fecha_registro").getTime()));
				tdDocumentoTramite.setUsuarioId(rs.getInt("usuario_id"));
				tdDocumentoTramite.setTerminal(rs.getString("terminal"));

				listTdDocumentoTramites.add(tdDocumentoTramite);
			}

		} catch (SQLException e) {
			throw new SisatException(e.getMessage());
		}

		return listTdDocumentoTramites;
	}
	
	public List<TdRequisito> getRequisitosByTipoTramiteDocumentoTramite(Integer tipoTramiteId, Integer docuTramiteId)
			throws SisatException {

		List<TdRequisito> listTdRequistos = new ArrayList<TdRequisito>();

		StringBuffer sb = new StringBuffer();

		sb.append("select td_req.* from td_requisito td_req ");
		sb.append("inner join td_tipo_tramite_documento_requisito td_tdr on td_tdr.requisito_id = td_req.requisito_id ");
		sb.append("where td_tdr.estado = 1 and td_tdr.tipo_tramite_id = ? and td_tdr.docu_tramite_id = ? ");

		PreparedStatement pstm;
		try {
			pstm = connect().prepareStatement(sb.toString());
			pstm.setInt(1, tipoTramiteId.intValue());
			pstm.setInt(2, docuTramiteId.intValue());

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				TdRequisito tdRequisito = new TdRequisito();

				tdRequisito.setRequisitoId(rs.getInt("requisito_id"));
				tdRequisito.setDescripcion(rs.getString("descripcion"));
				tdRequisito.setDescCorta(rs.getString("desc_corta"));
				tdRequisito.setUsuarioId(rs.getInt("usuario_id"));
				tdRequisito.setEstado(rs.getString("estado"));
				tdRequisito.setFechaRegistro(new Date(rs.getTimestamp(("fecha_registro")).getTime()));
				tdRequisito.setTerminal(rs.getString("terminal"));

				listTdRequistos.add(tdRequisito);
			}

		} catch (SQLException e) {
			throw new SisatException(e.getMessage());
		}

		return listTdRequistos;
	}

	public List<TdRequisitoExpediente> obtenerRequisitosExpediente(Integer expedienteId) throws SisatException {

		List<TdRequisitoExpediente> listTdRequisitoExpedientes = new ArrayList<TdRequisitoExpediente>();

		StringBuffer sb = new StringBuffer();

		sb.append("SELECT ");
		sb.append("td_req.descripcion as descripcion_requisito, ");
		sb.append("td_req_ex.requisito_expediente_id, ");
		sb.append("td_req_ex.requisito_id, ");
		sb.append("td_req_ex.expediente_id, ");
		sb.append("td_req_ex.flag_presentado, ");
		sb.append("td_req_ex.flag_subsanado, ");
		sb.append("td_req_ex.nro_dias_plazo, ");
		sb.append("td_req_ex.glosa, ");
		sb.append("td_req_ex.usuario_id, ");
		sb.append("td_req_ex.estado, ");
		sb.append("td_req_ex.fecha_registro, ");
		sb.append("td_req_ex.terminal ");
		sb.append("FROM td_requisito_expediente td_req_ex ");
		sb.append("INNER JOIN td_requisito td_req ON td_req_ex.requisito_id = td_req.requisito_id ");
		sb.append("where td_req_ex.estado = 1 and expediente_id = ? ");

		PreparedStatement pstm;
		try {
			pstm = connect().prepareStatement(sb.toString());
			pstm.setInt(1, expedienteId.intValue());

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				TdRequisitoExpediente tdRequisitoExpediente = new TdRequisitoExpediente();

				tdRequisitoExpediente.setRequisitoExpedienteId(rs.getInt("requisito_expediente_id"));
				tdRequisitoExpediente.setDescripcionRequisito(rs.getString("descripcion_requisito"));
				tdRequisitoExpediente.setRequisitoId(rs.getInt("requisito_id"));
				tdRequisitoExpediente.setExpedienteId(rs.getInt("expediente_id"));
				tdRequisitoExpediente.setFlagPresentado(rs.getBoolean("flag_presentado"));
				tdRequisitoExpediente.setFlagSubsanado(rs.getString("flag_subsanado"));
				tdRequisitoExpediente.setNroDiasPlazo(rs.getInt("nro_dias_plazo"));
				tdRequisitoExpediente.setGlosa(rs.getString("glosa"));
				tdRequisitoExpediente.setUsuarioId(rs.getInt("usuario_id"));
				tdRequisitoExpediente.setEstado(rs.getString("estado"));
				tdRequisitoExpediente.setFechaRegistro(new Date(rs.getTimestamp(("fecha_registro")).getTime()));
				tdRequisitoExpediente.setTerminal(rs.getString("terminal"));

				listTdRequisitoExpedientes.add(tdRequisitoExpediente);
			}

		} catch (SQLException e) {
			throw new SisatException(e.getMessage());
		}

		return listTdRequisitoExpedientes;
	}

	public List<TdDocumentoAnexo> obtenerDocumentosAnexos(Integer expedienteId) throws SisatException {

		List<TdDocumentoAnexo> listTdDocumentoAnexos = new ArrayList<TdDocumentoAnexo>();

		try {
			StringBuilder sb = new StringBuilder();

			sb.append("SELECT tdda.*, u.desc_corta, us.nombre_usuario  FROM td_documento_anexo tdda");
			sb.append(" INNER JOIN td_expediente_documento_anexo tdeda ON tdda.documento_anexo_id = tdeda.documento_anexo_id ");
			sb.append(" LEFT JOIN gn_unidad u ON u.unidad_id = tdda.unidad_id  ");
			sb.append(" INNER JOIN sg_usuario us ON us.usuario_id = tdda.usuario_id ");
			sb.append(" WHERE tdeda.estado = 1 AND tdeda.expediente_id = ?");

			PreparedStatement pstm;
			pstm = connect().prepareStatement(sb.toString());
			pstm.setInt(1, expedienteId);

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				TdDocumentoAnexo tdDocumentoAnexo = new TdDocumentoAnexo();

				tdDocumentoAnexo.setDocumentoAnexoId(rs.getInt("documento_anexo_id"));
				tdDocumentoAnexo.setNroDocumento(rs.getString("nro_documento"));
				tdDocumentoAnexo.setTipoDocumentoId(rs.getInt("tipo_documento_id"));
				tdDocumentoAnexo.setNroFolio(rs.getInt("nro_folio"));
				tdDocumentoAnexo.setFechaDocumento(new Date(rs.getTimestamp("fecha_documento").getTime()));
				tdDocumentoAnexo.setUnidadId(rs.getInt("unidad_id") > 0 ? rs.getInt("unidad_id") : null);
				tdDocumentoAnexo.setEstado(rs.getString("estado"));
				tdDocumentoAnexo.setContentId(rs.getInt("content_id") > 0 ? rs.getInt("content_id") : null);
				tdDocumentoAnexo.setUsuarioId(rs.getInt("usuario_id"));
				tdDocumentoAnexo.setFechaRegistro(new Date(rs.getTimestamp("fecha_registro").getTime()));
				tdDocumentoAnexo.setTerminal(rs.getString("terminal"));
				tdDocumentoAnexo.setUnidadAsString(rs.getString("desc_corta"));
				tdDocumentoAnexo.setUsuarioAsString(rs.getString("nombre_usuario"));
				tdDocumentoAnexo.setEnEdicion(false);

				listTdDocumentoAnexos.add(tdDocumentoAnexo);
			}

		} catch (SQLException e) {

			throw new SisatException(e.getMessage());
		}

		return listTdDocumentoAnexos;

	}

	public List<ResolutorDTO> obtenerResolutores(Integer unidadId) throws SisatException {

		List<ResolutorDTO> listResolutorDTOs = new ArrayList<ResolutorDTO>();

		StringBuilder sb = new StringBuilder();

		sb.append(" select ");
		sb.append(" td_resol.resolutor_id, ");
		sb.append(" td_resol.unidad_id, ");
		sb.append(" td_resol.usuario_resolutor_id , ");
		sb.append(" td_resol.fecha_inicio, ");
		sb.append(" td_resol.fecha_fin,  ");
		sb.append(" u.nombre_usuario, ");
		sb.append(" unidad.descripcion ");
		sb.append(" from td_resolutor td_resol ");
		sb.append(" inner join sg_usuario u on td_resol.usuario_resolutor_id = u.usuario_id ");
		sb.append(" inner join gn_unidad unidad on unidad.unidad_id = td_resol.unidad_id ");
		sb.append(" where td_resol.unidad_id = ? and td_resol.rol= 2 and td_resol.estado = 1"); //muestra solo resolutores
		//sb.append(" where td_resol.unidad_id = ?"); //muestra todos los usuarios del área

		PreparedStatement pstm;
		try {

			pstm = connect().prepareStatement(sb.toString());
			pstm.setInt(1, unidadId);

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				ResolutorDTO resolutorDTO = new ResolutorDTO();
				resolutorDTO.setResolutorId(rs.getInt("resolutor_id"));
				resolutorDTO.setUnidadId(rs.getInt("unidad_id"));
				resolutorDTO.setUsuarioResolutorId(rs.getInt("usuario_resolutor_id"));
				resolutorDTO.setFechaInicio(new Date(rs.getTimestamp("fecha_inicio").getTime()));
				resolutorDTO.setFechaFin(new Date(rs.getTimestamp("fecha_fin").getTime()));
				resolutorDTO.setNombreUsuario(rs.getString("nombre_usuario"));
				resolutorDTO.setUnidadAsDescripcion(rs.getString("descripcion"));

				listResolutorDTOs.add(resolutorDTO);

			}

		} catch (SQLException e) {
			throw new SisatException(e.getMessage());
		} catch (SisatException e) {
			throw new SisatException(e.getMessage());
		}

		return listResolutorDTOs;
	}
	
	public List<ResumenReporteExpedientesDTO> obtenerResumenExpedientes(Date fechaInicio, Date fechaFin)
			throws SisatException {
		List<ResumenReporteExpedientesDTO> listResolutorDTOs = new ArrayList<ResumenReporteExpedientesDTO>();

		String q = "stp_td_reporte_resumen_expediente ?, ?";
		PreparedStatement pstm;
		try {

			pstm = connect().prepareStatement(q);
			
			Timestamp fini,ffin;
			
			fini = new Timestamp(fechaInicio.getTime());
			ffin = new Timestamp(fechaFin.getTime());
					
			pstm.setTimestamp(1, new Timestamp(fechaInicio.getTime()));
			pstm.setTimestamp(2, new Timestamp(fechaFin.getTime()));

			ResultSet rs = pstm.executeQuery();
			Integer aumentoTotal = 0;
						
			while (rs.next()){
				ResumenReporteExpedientesDTO reporteExpedientesDTO = new ResumenReporteExpedientesDTO();
	
				reporteExpedientesDTO.setCantidad(rs.getInt("cantidad"));
				aumentoTotal = aumentoTotal + rs.getInt("cantidad");
				reporteExpedientesDTO.setTotal(aumentoTotal);
				reporteExpedientesDTO.setUnidadAsString(rs.getString("unidad_as_string"));
				reporteExpedientesDTO.setUnidadId(rs.getInt("unidad_id"));
	
				listResolutorDTOs.add(reporteExpedientesDTO);
			
			}

		} catch (SQLException e) {
			throw new SisatException(e.getMessage());
		} catch (SisatException e) {
			throw new SisatException(e.getMessage());
		}

		return listResolutorDTOs;
	}
	
	//para reporte de expediente con flag (ya impreso)
	public List<ResumenReporteExpedientesDTO> obtenerResumenExpedienteshr(Date fechaInicio, Date fechaFin, Integer ofiOrigen, Integer ofiDestino, Integer procedimientoExpediente, Integer tipoTramite, Integer impreso, Integer situacionExpediente)
			throws SisatException {
		List<ResumenReporteExpedientesDTO> listResolutorDTOs = new ArrayList<ResumenReporteExpedientesDTO>();

		String q = "stp_td_reporte_resumen_expedientehr ?, ?, ?, ?, ?, ?, ?, ?";
		PreparedStatement pstm;
		try {

			pstm = connect().prepareStatement(q);
								
			pstm.setTimestamp(1, new Timestamp(fechaInicio.getTime()));
			pstm.setTimestamp(2, new Timestamp(fechaFin.getTime()));
			if (ofiOrigen != 0) {
				pstm.setInt(3, ofiOrigen.intValue());
			} else {
				pstm.setNull(3, Types.INTEGER);
			}			
			if (ofiDestino != 0) {
				pstm.setInt(4, ofiDestino.intValue());
			} else {
				pstm.setNull(4, Types.INTEGER);
			}			
			if (procedimientoExpediente != 0) {
				pstm.setInt(5, procedimientoExpediente.intValue());
			} else {
				pstm.setNull(5, Types.INTEGER);
			}
			if (tipoTramite != 0) {
				pstm.setInt(6, tipoTramite.intValue());
			} else {
				pstm.setNull(6, Types.INTEGER);
			}
			if (impreso != null) {
				if (impreso == 2) {
					pstm.setNull(7, Types.INTEGER);
				} else {
					pstm.setInt(7, impreso.intValue());}
			} else {
				pstm.setInt(7, 1); //sino se selecciona opcion mostrara no impresos (report=1)
			}
			if (situacionExpediente != null) {
				if (situacionExpediente == 0){
					pstm.setNull(8, Types.INTEGER);
				} else{
					pstm.setInt(8, situacionExpediente.intValue());}
			} else {
				pstm.setInt(8, 11); //sino se selecciona opcion mostrara derivados (situacion_expediente=11)
			}
						
			ResultSet rs = pstm.executeQuery();
			Integer aumentoTotal = 0;
						
			while (rs.next()){
				ResumenReporteExpedientesDTO reporteExpedientesDTO = new ResumenReporteExpedientesDTO();
	
				reporteExpedientesDTO.setCantidad(rs.getInt("cantidad"));
				aumentoTotal = aumentoTotal + rs.getInt("cantidad");
				reporteExpedientesDTO.setTotal(aumentoTotal);
				reporteExpedientesDTO.setUnidadAsString(rs.getString("oficina"));
				reporteExpedientesDTO.setUnidadId(rs.getInt("unidad_id"));
	
				listResolutorDTOs.add(reporteExpedientesDTO);
			
			}

		} catch (SQLException e) {
			throw new SisatException(e.getMessage());
		} catch (SisatException e) {
			throw new SisatException(e.getMessage());
		}

		return listResolutorDTOs;
	}
	
	//verificar que no existe el remitente en la tabla gn_persona
	public Integer CantidadRemitente(String apeNom, String nroDoc){
		try {
			CallableStatement cs = connect().prepareCall("{call stp_td_existe_remitente(?,?)}");
			cs.setString(1, apeNom);
			cs.setString(2, nroDoc);
			ResultSet rs = cs.executeQuery();
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception ex) {
			String msg = "No se ha podido verificar si existe el Remitente actual";
			System.out.println(msg + ex);
		}
		return null;
	}
	
	//Guardar Remitente en  la tabla gn_persona
	public String NuevoRemitente( 
								String primerNombre,
								String segundoNombre,
								String apellidoPaterno,
								String apellidoMaterno,
								String razonSocial,
								String nroDoc,
								String direcCompleta,
								int tipoDoc,
								int usuarioId,
								String terminal
								)
								throws SQLException{
							try {
								CallableStatement ingreso = connect().prepareCall("{call stp_td_inserta_remitente(?,?,?,?,?,?,?,?,?,?)}");
								ingreso.setString(1, primerNombre);
								ingreso.setString(2, segundoNombre);
								ingreso.setString(3, apellidoPaterno);
								ingreso.setString(4, apellidoMaterno);
								ingreso.setString(5, razonSocial);
								ingreso.setString(6, nroDoc);
								ingreso.setString(7, direcCompleta);
								ingreso.setInt(8, tipoDoc);
								ingreso.setInt(9, usuarioId);
								ingreso.setString(10, terminal);
								ingreso.execute();
							} catch (Exception ex) {
								String msg = "No se ha podido recuperar Insertar Nuevo remitente";
								System.out.println(msg + ex);
							}
							return null;
					}

	//actualiza td_expediente en tabla sg_correlativo_doc 
	public Integer actualizaValorNroExpedienteGenerico(int anno)
			{
		try {
			CallableStatement ingreso = connect().prepareCall("{call stp_td_actualiza_correlativo_tablaDOC(?)}");		
			ingreso.setInt(1, anno);
			ingreso.execute();
		} catch (Exception ex) {
			String msg = "No se ha podido actualizar correlativo para Expediente";
			System.out.println(msg + ex);
		}
		return null;
	}

	//obtiene correlativo actual de td_expediente en tabla sg_correlativo_doc 
	public Integer obtenerCorrelativoDocTdExpediente(int anno){
		try {
			CallableStatement cs = connect().prepareCall("{call stp_td_actualizacorrelativo_gn_correlativo_doc(?)}");
			 			
			cs.setInt(1, anno);
			ResultSet rs = cs.executeQuery();
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception ex) {
			String msg = "No se ha podido recuperar el correlativo para la tabla td_expediente";
			System.out.println(msg + ex);
		}
		return null;
	}
	
	//obtiene correlativo actual de td_dj en tabla sg_correlativo_doc 
		public Integer obtenerCorrelativoDocTdDj(int anno){
			try {
				CallableStatement cs = connect().prepareCall("{call stp_td_Dj_actualizacorrelativo_gn_correlativo_doc(?)}");
				 			
				cs.setInt(1, anno);
				ResultSet rs = cs.executeQuery();
				while (rs.next()) {
					return rs.getInt(1);
				}
			} catch (Exception ex) {
				String msg = "No se ha podido recuperar el correlativo para la tabla td_dj";
				System.out.println(msg + ex);
			}
			return null;
		}
	
		//actualiza td_Dj en tabla sg_correlativo_doc 
		public Integer actualizaValorNroExpedienteGenericoDj(int anno)
				{
			try {
				CallableStatement ingreso = connect().prepareCall("{call stp_td_Dj_actualiza_correlativo_tablaDOC(?)}");		
				ingreso.setInt(1, anno);
				ingreso.execute();
			} catch (Exception ex) {
				String msg = "No se ha podido actualizar correlativo para DJ";
				System.out.println(msg + ex);
			}
			return null;
		}
		
		//actualiza td_Dj_resolucion en tabla sg_correlativo_doc 
				public Integer actualizaValorResolucionDj(int anno)
						{
					try {
						CallableStatement ingreso = connect().prepareCall("{call stp_td_Dj_resolucion_actualiza_correlativo_tablaDOC(?)}");		
						ingreso.setInt(1, anno);
						ingreso.execute();
					} catch (Exception ex) {
						String msg = "No se ha podido actualizar correlativo para DJ";
						System.out.println(msg + ex);
					}
					return null;
				}
	
	public DeclaracionJuradaAdultDTO nuevaDjAdulto(Date fechaRecepcion,
			String correlativoDJ,
			int tramiteId,
			int documentoTramiteId,
			String nroExpedienteGenerico,
			String nroExpediente,
			int nroFolios,
			int contribuyenteId,
			String nroDocIdentContr,
			String apellidosNombresContr,
			String direccionFiscalContr,
			String observacion,
			String nroResolucion,
			int usuarioId,
			int estadoDJ,
			String terminal,
			int situacionDJ,
			int procedimientoId,
			int unidadId,
			int estadoExpediente,
			int resolutorId,
			String tipoBien,
			BigDecimal porcBenif,
			int iniAnnoBenif
			) throws SQLException{

		
		try {
			
			
			CallableStatement dj = connect().prepareCall("{call sp_DJ_adultoPensionista_insertar_dj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			dj.setTimestamp(1,new Timestamp(fechaRecepcion.getTime()));
			dj.setString(2, correlativoDJ);
			dj.setInt(3, tramiteId);
			dj.setInt(4, documentoTramiteId);
			dj.setString(5, nroExpedienteGenerico);
			dj.setString(6, nroExpediente);
			dj.setInt(7, nroFolios);
			dj.setInt(8, contribuyenteId);
			dj.setString(9, nroDocIdentContr);
			dj.setString(10, apellidosNombresContr);
			dj.setString(11, direccionFiscalContr);
			dj.setString(12, observacion);
			dj.setString(13, nroResolucion);
			dj.setInt(14, usuarioId);
			dj.setInt(15, estadoDJ);
			dj.setString(16, terminal);                           
			dj.setInt(17, situacionDJ);
			
			dj.setInt(18, procedimientoId);
			dj.setInt(19, unidadId);
			dj.setInt(20, estadoExpediente);
			dj.setInt(21, resolutorId);
			
			if (tipoBien != null) {
				dj.setString(22, tipoBien);
			} else {
				dj.setNull(22, Types.VARCHAR);
			}
			//dj.setString(22, tipoBien);
			dj.setBigDecimal(23, porcBenif);
			dj.setInt(24, iniAnnoBenif);

	
			dj.execute();
		
		} catch (SisatException ex) {
			String msg = "No se ha podido recuperar Insertar DJ";
			System.out.println(msg + ex);
		}
		return null;
		
		
	}
	
	public DeclaracionJuradaAdultDTO nuevoConyugeDjAdult(
				
			int djId,
			int relacionadoId,
			String nroDocIdentConyuge,
			String apellidosNombresConyuge,
			BigDecimal porcentajePartConyuge,
			Boolean fallecidoConyuge,
			int requisitoPartId,
			Date fechaPartidaDefuncion,
			int requisitoSucId,
			Date fechaSucesionIntestada,
			BigDecimal cuotaIdeal,
			int contribuyenteId,
			int usuarioId,
			String terminal			
			
			) throws SQLException{

		
		try {
			
			System.out.println("Llego el conyuge al DAO");
			CallableStatement dj = connect().prepareCall("{call sp_DJ_adultoPensionista_insertar_djConyuge(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			dj.setInt(1, djId);
			if (relacionadoId > 0) {
				dj.setInt(2, relacionadoId);
			} else {
				dj.setNull(2, Types.INTEGER);
			}
			//dj.setInt(2, relacionadoId);
			if (nroDocIdentConyuge != null) {
				dj.setString(3, nroDocIdentConyuge);
			} else {
				dj.setNull(3, Types.VARCHAR);
			}
			//dj.setString(3, nroDocIdentConyuge);
			if (apellidosNombresConyuge != null) {
				dj.setString(4, apellidosNombresConyuge);
			} else {
				dj.setNull(4, Types.VARCHAR);
			}
			//dj.setString(4, apellidosNombresConyuge);
			if (porcentajePartConyuge != null) {
				dj.setBigDecimal(5, porcentajePartConyuge);
			} else {
				dj.setNull(5, Types.DECIMAL);
			}
			//dj.setBigDecimal(5, porcentajePartConyuge);
			dj.setBoolean(6, fallecidoConyuge);
			
			if (requisitoPartId > 0) {
				dj.setInt(7, requisitoPartId);
			} else {
				dj.setNull(7, Types.INTEGER);
			}
			//dj.setInt(7, requisitoPartId);
			
			if (fechaPartidaDefuncion != null) {
				dj.setTimestamp(8, new Timestamp(fechaPartidaDefuncion.getTime()));
			} else {
				dj.setNull(8, Types.TIMESTAMP);
			}
			
			//dj.setTimestamp(8,new Timestamp(fechaPartidaDefuncion.getTime()));
			
			if (requisitoSucId > 0) {
				dj.setInt(9, requisitoSucId);
			} else {
				dj.setNull(9, Types.INTEGER);
			}
			//dj.setInt(9, requisitoSucId);
			
			if (fechaSucesionIntestada != null) {
				dj.setTimestamp(10, new Timestamp(fechaSucesionIntestada.getTime()));
			} else {
				dj.setNull(10, Types.TIMESTAMP);
			}
			//dj.setTimestamp(10,new Timestamp(fechaSucesionIntestada.getTime()));
			if (cuotaIdeal != null) {
				dj.setBigDecimal(11, cuotaIdeal);
			} else {
				dj.setNull(11, Types.DECIMAL);
			}
			
			
			//dj.setBigDecimal(11, cuotaIdeal);
			dj.setInt(12, contribuyenteId);
			dj.setInt(13, usuarioId);
			dj.setString(14, terminal);
			
			
			
			dj.execute();

		} catch (SisatException ex) {
			String msg = "No se ha podido recuperar Insertar DJ Conyuge";
			System.out.println(msg + ex);
		}
		return null;
		
		
	}
	
	public DeclaracionJuradaAdultDTO nuevaResolucionDj(
		      String nroResolucion 
		      ,int djId
		      ,int usuarioId
		      ,String terminal) throws SQLException{
		
		try {
			System.out.println("Llego la resolución al DAO");
			CallableStatement dj = connect().prepareCall("{call sp_DJ_adultoPensionista_insertar_djResolucion(?,?,?,?)}");
			
			dj.setString(1, nroResolucion);
			dj.setInt(2, djId);
		//	dj.setString(3, glosa);
			dj.setInt(3, usuarioId);
			dj.setString(4, terminal);

			dj.execute();

		} catch (Exception ex) {
			String msg = "No se ha podido recuperar Insertar DJ resolución";
			System.out.println(msg + ex);
			}
		
		
				return null;
		
	}
	
	public DeclaracionJuradaAdultDTO nuevaPropUnicaDjAdult(
			
			int djId,
			Boolean vivienda,
			Boolean negocio, 
			int contribuyenteId, 
			int usuarioId, 
			String terminal
			
			) throws SQLException{

		
		try {
			
			System.out.println("Llego la propiedad al DAO");
			CallableStatement dj = connect().prepareCall("{call sp_DJ_adultoPensionista_insertar_djUnicaPropiedad(?,?,?,?,?,?)}");

			dj.setInt(1, djId);
			dj.setBoolean(2, vivienda);
			dj.setBoolean(3, negocio);
		//	dj.setBoolean(4, licenciaFuncionamiento);
			dj.setInt(4, contribuyenteId);
			dj.setInt(5, usuarioId);
			dj.setString(6, terminal);
			
			dj.execute();

		} catch (SisatException ex) {
			String msg = "No se ha podido recuperar Insertar DJ única propiedad";
			System.out.println(msg + ex);
		}
		return null;
		
		
	}
	
	public RequisitoExpededienteDTO nuevoRequisitoExpedienteDjAdult(
			
			int requisitoId,
			Boolean flagPresentado,
			String glosa,
			int usuarioId,
			String terminal,
			int djId
			
			) throws SQLException{

		
		try {
			
			System.out.println("Llego el requisito de expediente al DAO");
			CallableStatement dj = connect().prepareCall("{call sp_DJ_adultoPensionista_insertar_requisitoExpediente(?,?,?,?,?,?)}");

			dj.setInt(1, requisitoId);
			dj.setBoolean(2, flagPresentado);
			dj.setString(3, glosa);
			dj.setInt(4, usuarioId);
			dj.setString(5, terminal);
			dj.setInt(6, djId);
			
			
			dj.execute();

		} catch (SisatException ex) {
			String msg = "No se ha podido recuperar Insertar DJ requisito expediente";
			System.out.println(msg + ex);
		}
		return null;
		
		
	}
	
	public  Integer obtenerDjId(int contribuyenteId) {
		
		int djIdCont = 0;

		
		try {
			
			String sql = "sp_DJ_adultoPensionista_obtenerIdDj ?";
			PreparedStatement pst = connect().prepareStatement(sql.toString());

			pst.setInt(1, contribuyenteId);
			
			ResultSet rs = pst.executeQuery();
			
			if (rs.next()) {
				DeclaracionJuradaAdultDTO item = new DeclaracionJuradaAdultDTO();
				
				item.setDjId(rs.getInt("dj_id"));
				
				djIdCont = item.getDjId();	
				
				//djIdCont.add(item);
			}
			
			} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return djIdCont;
		
	}
	
public  Integer obtenerDjTipoDocTramiteId(int contribuyenteId) {
		
		int djDocId = 0;

		
		try {
			
			String sql = "sp_DJ_adultoPensionista_obtenerIdDj ?";
			PreparedStatement pst = connect().prepareStatement(sql.toString());

			pst.setInt(1, contribuyenteId);
			
			ResultSet rs = pst.executeQuery();
			
			if (rs.next()) {
				DeclaracionJuradaAdultDTO item = new DeclaracionJuradaAdultDTO();
				
				item.setDocuTramiteId(rs.getInt("docu_tramite_id"));
				
				djDocId = item.getDocuTramiteId();	
				
				//djIdCont.add(item);
			}
			
			
			} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return djDocId;
		
	}
	
public  Integer obtenerResolutorDjId(int usuarioLogueadoId) {
		
		int djIdRes = 0;

		
		try {
			
			String sql = "sp_DJ_adultoPensionista_obtenerIdResolutorDj ?";
			PreparedStatement pst = connect().prepareStatement(sql.toString());

			pst.setInt(1, usuarioLogueadoId);
			
			ResultSet rs = pst.executeQuery();
			
			if (rs.next()) {
				ResolutorDTO item = new ResolutorDTO();
				
				item.setResolutorId(rs.getInt("resolutor_id"));
				
				djIdRes = item.getResolutorId();	
				
				//djIdCont.add(item);
			}
			
			} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return djIdRes;
		
	}
	
	public ArrayList<RelacionadosDTO> GetRelacionadosNew(int persona_id) {

		ArrayList<RelacionadosDTO> listaRelacionados = new ArrayList<RelacionadosDTO>();

		try {
			String sql = "stp_dt_HR_subreporte_relacionados ?";
			PreparedStatement pst = connect().prepareStatement(sql.toString());

			pst.setInt(1, persona_id);	
			
			
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {// ***
				RelacionadosDTO item = new RelacionadosDTO();
				
				item.setItem(rs.getInt("item"));
				item.setRelacionadoId(rs.getInt("relacionado_id"));
				item.setTiporelacion(rs.getString("relacionado"));
				item.setPorcParticipacion(rs.getBigDecimal("porc_participacion"));
				item.setApellidosNombres(rs.getString("apellidos_nombres"));
				item.setTipoDocIdentidad(rs.getString("descripcion"));
				item.setNroDocuIdentidad(rs.getString("nro_docu_identidad"));
				listaRelacionados.add(item);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return listaRelacionados;
	}
	
	
}
