package com.sat.sisat.fiscalizacion.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.text.Format;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.mail.Session;

import org.jboss.security.auth.spi.Users.User;

import com.sat.sisat.cobranzacoactiva.dto.FindCcRecTipo;
import com.sat.sisat.common.dao.GeneralDao;
import com.sat.sisat.common.security.UserSession;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.SATParameterFactory;
import com.sat.sisat.controlycobranza.dto.FindCcLote;
import com.sat.sisat.controlycobranza.dto.FindCcLoteDetalleActo;
import com.sat.sisat.controlycobranza.dto.MpFiscalizador;
import com.sat.sisat.controlycobranza.dto.MpFiscalizadorArea;
import com.sat.sisat.controlycobranza.dto.MpFiscalizadorDto;
import com.sat.sisat.determinacion.vehicular.dto.DatosNecesariosDeterDTO;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.fiscalizacion.dto.DatosNecesariosDeclaracionDTO;
import com.sat.sisat.fiscalizacion.dto.FindInpscDocTipo;
import com.sat.sisat.fiscalizacion.dto.FindInspeccionByDetalle;
import com.sat.sisat.fiscalizacion.dto.FindInspeccionByHorario;
import com.sat.sisat.fiscalizacion.dto.FindInspeccionById;
import com.sat.sisat.fiscalizacion.dto.FindInspeccionByIdAsociada;
import com.sat.sisat.fiscalizacion.dto.FindInspeccionByResultado;
import com.sat.sisat.fiscalizacion.dto.FindInspeccionDj;
import com.sat.sisat.fiscalizacion.dto.FindInspeccionDocCargoTipo;
import com.sat.sisat.fiscalizacion.dto.FindInspeccionHistorial;
import com.sat.sisat.fiscalizacion.dto.FindInspeccionHistorialDetalle;
import com.sat.sisat.persistence.entity.CcLote;
import com.sat.sisat.persistence.entity.DtDeterminacion;
import com.sat.sisat.persistence.entity.DtTasaVehicular;
import com.sat.sisat.persistence.entity.RpFiscalizacionHorario;
import com.sat.sisat.persistence.entity.RpFiscalizacionPrograma;
import com.sat.sisat.persistence.entity.RpFiscalizacionProgramaDetalle;
import com.sat.sisat.persistence.entity.RvCategoriaVehiculo;
import com.sat.sisat.persistence.entity.RvClaseVehiculo;
import com.sat.sisat.persistence.entity.RvDjvehicular;
import com.sat.sisat.persistence.entity.RvMarca;
import com.sat.sisat.persistence.entity.RvModeloVehiculo;
import com.sat.sisat.persistence.entity.RvModeloVehiculoPK;
import com.sat.sisat.persistence.entity.RvOmisosDetalleVehicular;
import com.sat.sisat.persistence.entity.RvOmisosVehicular;
import com.sat.sisat.persistence.entity.RvSustentoVehicular;
import com.sat.sisat.persistence.entity.RvTipoCarroceria;
import com.sat.sisat.persistence.entity.RvTipoMotor;
import com.sat.sisat.persistence.entity.RvVehiculo;
import com.sat.sisat.predial.dto.FindRpDjPredial;
import com.sat.sisat.vehicular.dto.AnexosDeclaVehicDTO;
import com.sat.sisat.vehicular.dto.BuscarPersonaDTO;
import com.sat.sisat.vehicular.dto.MarcaModeloTemporalDTO;


public class FiscalizacionBusinessDao extends GeneralDao {
	
	
	public List<FindInpscDocTipo> getAllTipoDoc()throws Exception{
		List<FindInpscDocTipo> lista=new LinkedList<FindInpscDocTipo>();
		try{
			
			String SQL = new String("exec dbo.stp_getTipoDocumentoInspeccion");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			ResultSet rs=pst.executeQuery();
			
			while(rs.next()){
				FindInpscDocTipo obj=new FindInpscDocTipo(); 
				
				obj.setTipoDocumentoId(rs.getInt("tipo_documento_id"));
				obj.setDescripcionTipoDocumento(rs.getString("descripcion"));
//				obj.setValorCorrelativoDocumento(rs.getInt("valor_actual"));
				
				lista.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	
	
	public List<RpFiscalizacionPrograma> getAllTipoPrograma()throws Exception{
		List<RpFiscalizacionPrograma> lista=new LinkedList<RpFiscalizacionPrograma>();
		try{
			
			String SQL = new String("select programa_id,nombre_programa from rp_fiscalizacion_programa where estado=1");

			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			ResultSet rs=pst.executeQuery();
			
			while(rs.next()){
				RpFiscalizacionPrograma obj=new RpFiscalizacionPrograma(); 
				
				obj.setProgramaId(rs.getInt("programa_id"));
				obj.setNombrePrograma(rs.getString("nombre_programa"));
				
				lista.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	
	public List<MpFiscalizador> getAllInspector()throws Exception{
		List<MpFiscalizador> lista=new LinkedList<MpFiscalizador>();
		try{
			
			String SQL = new String("select inspector_id,nombre_inspector from mp_fiscalizador where estado=1");

			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			ResultSet rs=pst.executeQuery();
			
			while(rs.next()){
				MpFiscalizador obj=new MpFiscalizador(); 
				
				obj.setIdfiscalizador(rs.getInt("inspector_id"));
				obj.setNombresApellidos(rs.getString("nombre_inspector"));
				
				lista.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	
	
	public List<RpFiscalizacionHorario> getAllHorario()throws Exception{
		List<RpFiscalizacionHorario> lista=new LinkedList<RpFiscalizacionHorario>();
		try{
			
			String SQL = new String("select horario_id,intervalo_horario from rp_fiscalizacion_horario where estado=1");

			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			ResultSet rs=pst.executeQuery();
			
			while(rs.next()){
				RpFiscalizacionHorario obj=new RpFiscalizacionHorario(); 
				
				obj.setHorarioId(rs.getInt("horario_id"));
				obj.setIntervaloHorario(rs.getString("intervalo_horario"));
				//obj.setNombreHorario(rs.getString("nombre_horario"));
				
				lista.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	
	public List<FindInpscDocTipo> getCorrelativo(Integer tipoDocId) throws Exception {
		List<FindInpscDocTipo> list = new ArrayList<FindInpscDocTipo>();

		try {
			String SQL = new String("stp_getCorrelativoDocumentoInspeccion ?");
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, tipoDocId);
			
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				//valorCorrelativo
				FindInpscDocTipo obj = new FindInpscDocTipo();
				
				
				obj.setValorCorrelativo(rs.getString("nro_requerimiento"));
				
				
				list.add(obj);

			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}
	
	public int guardarRequerimiento (Integer tipoDocumento,String nroCorrelativo,Integer tipoPrograma,Integer nombreInspector, Date fechaInspeccion,
			Integer codPersona,String observacion, String dirPersona,Date fechaNotifica,String anioInspeccion,
			String tipoDocumentoRef,String nroDocumentoRef,Integer omiso,Integer estado,Integer usuarioId,String terminal) throws Exception {

		int salida = 0;
		try {
			CallableStatement cs = connect().prepareCall(
					"{call dbo.spt_generaRequerimientoInspeccion(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			
			cs.setInt(1, tipoDocumento);
			cs.setString(2, nroCorrelativo);
			cs.setInt(3, tipoPrograma);
			cs.setInt(4, nombreInspector);
			cs.setTimestamp(5, DateUtil.dateToSqlTimestamp(fechaInspeccion));
			cs.setInt(6, codPersona);
			cs.setString(7, observacion);
			cs.setString(8, dirPersona);
			
			if (fechaNotifica == null) {
				cs.setString(9,null);
			} else{
				
				cs.setTimestamp(9, DateUtil.dateToSqlTimestamp(fechaNotifica));
				
			}
			cs.setString(10,anioInspeccion);
			cs.setString(11,tipoDocumentoRef);
			cs.setString(12,nroDocumentoRef);
			cs.setInt(13, omiso);
			cs.setInt(14, estado);
			cs.setInt(15, usuarioId);
			cs.setString(16, terminal);
			
			cs.execute();
			
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
		}
		return salida;
	}
	
	public int guardarRequerimientoDetalle (Integer inspectorId, String anio,Integer djId,Integer predioId,Integer usuarioId,String terminal,Integer inspcc_id,Date fechaInspeccion
			,Integer ubicacion,Integer sector,Integer tipoVia, Integer via,String manzana,String cuadra,String lado,String predioDir) throws Exception {

		int salida = 0;
		try {
			CallableStatement cs = connect().prepareCall(
					"{call dbo.spt_generaRequerimientoInspeccionDetalle(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			
			cs.setInt(1, inspectorId);
			cs.setString(2, anio);
			cs.setInt(3, djId);
			cs.setInt(4, predioId);
			cs.setTimestamp(5, DateUtil.dateToSqlTimestamp(fechaInspeccion));
			cs.setInt(6, inspcc_id);
			
			cs.setInt(7, ubicacion);
			cs.setInt(8, sector);
			cs.setInt(9, tipoVia);
			cs.setInt(10, via);
			cs.setString(11, manzana);
			cs.setString(12, cuadra);
			cs.setString(13, lado);
			cs.setString(14, predioDir);
			
			
			cs.setInt(15, usuarioId);
			cs.setString(16, terminal);
			

			cs.execute();
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
		}
		return salida;
	}
	
	public int guardarDetalleFip (Integer inspectorId, String anio,Integer djId,Integer predioId,
			Integer ubicacionId,Integer tipoViaId,Integer viaId,Integer sectorId,String manzana,String cuadra,String lado,
			Date fechaInspeccion,Integer inspcc_id,String direccion,String sector,String via,String tipoVia,String lugar,
			Integer inspectorIdAr,Date fechaInspeccionAr,Integer usuarioId,String terminal
			) throws Exception {

		int salida = 0;
		try {
			CallableStatement cs = connect().prepareCall(
					"{call dbo.spt_generaFIPInspeccionDetalle(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			cs.setInt(1, inspectorId);
			cs.setString(2, anio);
			cs.setInt(3, djId);
			cs.setInt(4, predioId);
			cs.setInt(5, ubicacionId);
			cs.setInt(6, tipoViaId);
			cs.setInt(7, viaId);
			cs.setInt(8, sectorId);
			cs.setString(9, manzana);
			cs.setString(10, cuadra);
			cs.setString(11, lado);
			cs.setTimestamp(12, DateUtil.dateToSqlTimestamp(fechaInspeccion));
			cs.setInt(13, inspcc_id);
			cs.setString(14, direccion);
			cs.setString(15, sector);
			cs.setString(16, via);
			cs.setString(17, tipoVia);
			cs.setString(18, lugar);
			cs.setInt(19, inspectorIdAr);
					
			if (fechaInspeccionAr == null) 
			{
				cs.setString(20,null);
			} else{
				
				cs.setTimestamp(20, DateUtil.dateToSqlTimestamp(fechaInspeccionAr));
				
			}
			
			cs.setInt(21, usuarioId);
			cs.setString(22, terminal);
			

			cs.execute();
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
		}
		return salida;
	}
	
	public List<FindInspeccionHistorial> getAllInspeccionesByPrograma(Integer programaId) throws Exception {
		List<FindInspeccionHistorial> list = new ArrayList<FindInspeccionHistorial>();

		try {
			String SQL = new String("stp_getRequerimientoInspeccionByPrograma ?");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, programaId);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				FindInspeccionHistorial obj = new FindInspeccionHistorial();
				
				
				obj.setInspeccionId(rs.getInt("inspeccion_id"));
				obj.setTipoDocumentoId(rs.getInt("tipo_documento_id"));
				obj.setTipoDocumentoNombre(rs.getString("tipo_documento_nombre"));
				obj.setNroRequerimiento(rs.getString("nro_requerimiento"));
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setApellidosNombres(rs.getString("apellidos_nombres"));
				obj.setProgramaNombre(rs.getString("nombre_programa"));
				obj.setAnioInspeccion(rs.getString("anio_inspeccion"));

				list.add(obj);

			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}
	
	
	public List<FindInspeccionHistorial> getAllInspecciones() throws Exception {
		List<FindInspeccionHistorial> list = new ArrayList<FindInspeccionHistorial>();

		try {
			String SQL = new String("stp_getAllRequerimientoInspeccion");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				FindInspeccionHistorial obj = new FindInspeccionHistorial();
				
				
				obj.setInspeccionId(rs.getInt("inspeccion_id"));
				obj.setTipoDocumentoId(rs.getInt("tipo_documento_id"));
				obj.setTipoDocumentoNombre(rs.getString("tipo_documento_nombre"));
				obj.setNroRequerimiento(rs.getString("nro_requerimiento"));
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setApellidosNombres(rs.getString("apellidos_nombres"));
				obj.setPersonaDomicilioFiscal(rs.getString("domicilio_persona"));
				obj.setPersonaDni(rs.getString("dni_persona"));
				obj.setUbicacionId(rs.getInt("ubicacion_id"));
				obj.setSectorId(rs.getInt("sector_id"));
				obj.setViaId(rs.getInt("via_id"));
				obj.setSectorNombre(rs.getString("nombre_sector"));
				obj.setViaNombre(rs.getString("nombre_via"));
				obj.setManzanaNombre(rs.getString("nombre_manzana"));
				obj.setProgramaId(rs.getInt("programa_id"));
				obj.setProgramaNombre(rs.getString("nombre_programa"));
				obj.setEstado(rs.getInt("estado"));
				obj.setEstadoDescripcion(rs.getString("estado_descripcion"));
				obj.setFechaEmision(rs.getDate("fecha_emision"));//
				obj.setFechaInspeccion(rs.getDate("fecha_inspeccion"));
				obj.setFechaNotificacion(rs.getDate("fecha_notificacion"));//
				obj.setInspectorId(rs.getInt("inspector_id"));
				obj.setInspectorNombre(rs.getString("nombre_inspector"));
				obj.setInspectorDni(rs.getString("dni_inspector"));
				obj.setResultadoId(rs.getInt("tipo_documento_id_resultado"));
				obj.setResultadoNombre(rs.getString("resultado_descripcion"));
				obj.setResultadoNumero(rs.getString("nro_documento_resultado"));
				obj.setFechaResultado(rs.getDate("fecha_notificacion_resultado"));
				obj.setEsquelaId(rs.getInt("tipo_documento_id_esquela"));
				obj.setEsquelaNombre(rs.getString("esquela_descripcion"));
				obj.setEsquelaNumero(rs.getString("nro_esquela"));
				obj.setFechaEsquela(rs.getDate("fecha_notificacion_esquela"));
				obj.setArId(rs.getInt("tipo_documento_id_reprogramacion"));
				obj.setArNombre(rs.getString("ar_descripcion"));
				obj.setArNumero(rs.getString("nro_reprogramacion"));
			    obj.setFechaAr(rs.getDate("fecha_generacion_reprogramacion"));
				obj.setDjNumero(rs.getString("numero_dj"));
				obj.setDjFecha(rs.getDate("fecha_dj"));
				obj.setCondicion(rs.getString("condicion"));
				obj.setObservaciones(rs.getString("observaciones"));
				obj.setNombreUsuario(rs.getString("nombre_usuario"));//
				obj.setFechaRegistro(rs.getDate("fecha_registro"));
				obj.setInspectorIdAr(rs.getInt("inspector_id_ar"));
				
				obj.setAnioInspeccion(rs.getString("anio_inspeccion"));
				obj.setDocAsocId(rs.getInt("tipo_documento_ref_id"));
				obj.setDocAsocNombre(rs.getString("descripcion"));
				obj.setDocAsocNumero(rs.getString("nro_documento_ref"));
				
			
				obj.setArFipId(rs.getInt("tipo_documento_id_resultado_ar"));
				obj.setArFipNumero(rs.getString("nro_documento_resultado_ar"));
				obj.setFechaArFip(rs.getDate("fecha_notificacion_resultado_ar"));
				
				obj.setPaquete(rs.getInt("paquete"));
				obj.setAnnioPaquete(rs.getInt("annio_paquete"));
				obj.setExpediente(rs.getInt("expediente"));
			

				list.add(obj);

			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}
	
		
	public List<BuscarPersonaDTO> findPersona(Integer persId,String nombres)
			throws Exception {
		List<BuscarPersonaDTO> lista = new ArrayList<BuscarPersonaDTO>();
		try {
			
			StringBuilder SQL = new StringBuilder("SELECT TOP 100 per.persona_id,tdi.descrpcion_corta,per.nro_docu_identidad,per.apellidos_nombres,per.razon_social,per.ape_paterno,per.ape_materno,per.primer_nombre,per.segundo_nombre,per.tipo_doc_identidad_id, dir.direccion_completa ");
			SQL.append(" FROM ").append(Constante.schemadb).append(".mp_persona per  ");
			SQL.append(" INNER JOIN ").append(Constante.schemadb)
					.append(".mp_tipo_docu_identidad AS tdi ON tdi.tipo_doc_identidad_id = per.tipo_doc_identidad_id");
			SQL.append(" INNER JOIN ").append(Constante.schemadb)
			.append(".mp_direccion dir ON per.persona_id = dir.persona_id");
			SQL.append(" WHERE per.estado='1' and dir.estado='1'");
			if (persId != null) {
				SQL.append("AND per.persona_id =").append(persId);
			} else {
				if (nombres != null) {
					SQL.append(" AND per.apellidos_nombres LIKE '%").append(nombres).append("%'");
				}
			}
						
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				BuscarPersonaDTO obj = new BuscarPersonaDTO();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setTipoDocIdentidad(rs.getString("descrpcion_corta"));
				obj.setNroDocuIdentidad(rs.getString("nro_docu_identidad"));
				obj.setApellidosNombres(rs.getString("apellidos_nombres"));
				obj.setRazonSocial(rs.getString("razon_social"));
				obj.setApellidoPaterno(rs.getString("ape_paterno"));
				obj.setApellidoMaterno(rs.getString("ape_materno"));
				obj.setPrimerNombre(rs.getString("primer_nombre"));
				obj.setSegundoNombre(rs.getString("segundo_nombre"));
				obj.setTipodocumentoIdentidadId(rs.getInt("tipo_doc_identidad_id"));
				obj.setDireccionCompleta(rs.getString("direccion_completa"));
				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		//return lista;
		if(lista!=null&&lista.size()>0){
			return lista;
		}else 
			return null;
	}
	
	public List<BuscarPersonaDTO> findPersona(int tipoDocuIdentidadId, String nroDocuIdentidad) throws Exception {
		List<BuscarPersonaDTO> lista = new ArrayList<BuscarPersonaDTO>();
		try {
			StringBuilder SQL = new StringBuilder("SELECT TOP 100 per.persona_id,tdi.descrpcion_corta,per.nro_docu_identidad,per.apellidos_nombres,per.razon_social,per.ape_paterno,per.ape_materno,per.primer_nombre,per.segundo_nombre,per.tipo_doc_identidad_id, dir.direccion_completa");
			SQL.append(" FROM ").append(Constante.schemadb).append(".mp_persona per  ");
			SQL.append(" INNER JOIN ").append(Constante.schemadb)
					.append(".mp_tipo_docu_identidad tdi ON tdi.tipo_doc_identidad_id=per.tipo_doc_identidad_id ");
			SQL.append(" INNER JOIN ").append(Constante.schemadb)
					.append(".mp_direccion dir ON per.persona_id = dir.persona_id");
			SQL.append(" WHERE per.estado='1' and dir.estado='1'");
			if (tipoDocuIdentidadId > 0 && nroDocuIdentidad != null && nroDocuIdentidad.trim().length() > 0)
				SQL.append(" AND per.tipo_doc_identidad_id=").append(tipoDocuIdentidadId)
						.append(" and per.nro_docu_identidad='").append(nroDocuIdentidad).append("'");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				BuscarPersonaDTO obj = new BuscarPersonaDTO();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setTipoDocIdentidad(rs.getString("descrpcion_corta"));
				obj.setNroDocuIdentidad(rs.getString("nro_docu_identidad"));
				obj.setApellidosNombres(rs.getString("apellidos_nombres"));
				obj.setRazonSocial(rs.getString("razon_social"));
				obj.setApellidoPaterno(rs.getString("ape_paterno"));
				obj.setApellidoMaterno(rs.getString("ape_materno"));
				obj.setPrimerNombre(rs.getString("primer_nombre"));
				obj.setSegundoNombre(rs.getString("segundo_nombre"));
				obj.setTipodocumentoIdentidadId(rs.getInt("tipo_doc_identidad_id"));
				obj.setDireccionCompleta(rs.getString("direccion_completa"));
				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		//return lista;
		if(lista!=null&&lista.size()>0){
			return lista;
		}else 
			return null;
	}
	
	public List<FindInspeccionDj> getDeclaracionesInsp(Integer persona_id) throws Exception {
		List<FindInspeccionDj> list =  new ArrayList<FindInspeccionDj>();
		try{
			StringBuilder SQL = new StringBuilder("dbo.spt_getDjInspeccion ?");
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, persona_id);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){
				
				FindInspeccionDj obj = new FindInspeccionDj();
				
				obj.setPredioId(rs.getInt("predio_id"));
				obj.setDjId(rs.getInt("dj_id"));
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setAnnoDj(rs.getInt("anno_dj"));
				obj.setDescripcionTipoPredio(rs.getString("tipo_predio_desc"));
				obj.setEstado(rs.getInt("estado"));
				obj.setFlagDjAnno(rs.getInt("flag_dj_anno"));
				obj.setEstadoFiscalizado(rs.getInt("fiscalizado"));
				obj.setPredioIdAntiguo(rs.getString("codigo_predio"));
				obj.setDescripcionCondicionPredio(rs.getString("descripcion"));
				obj.setDescripcionCortaTipoPredio(rs.getString("tipo_predio"));
				obj.setPorcPropiedad(rs.getBigDecimal("porc_propiedad"));
				obj.setAreaTerreno(rs.getDouble("area_terreno"));
				obj.setAreaTerrenoHas(rs.getDouble("area_terreno_comun"));
				obj.setFechaDeclaracion(rs.getDate("fecha_declaracion"));
				obj.setFechaAdquisicion(rs.getDate("fecha_adquisicion"));
				obj.setFechaRegistro(rs.getDate("fecha_registro"));
				obj.setReqInspeccion(rs.getInt("reqInspeccion"));
				obj.setNroreqInspeccion(rs.getString("nroreqInspeccion"));
				
				list.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		return list;
	}
	
	/**Se agregó dj_id, para que el registro de la dj en rp_fiscalizacion_inspeccion sea en el mismo Form:"registropredio.xhtml"*/
	public List<FindInspeccionDj> getDeclaracionesInspById(Integer inspId,Integer personaId,Integer djId) throws Exception {
		List<FindInspeccionDj> list =  new ArrayList<FindInspeccionDj>();
		try{
			StringBuilder SQL = new StringBuilder("dbo.stp_getDjInspeccionByIdRequerimiento ?,?,?");
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			
			pst.setInt(1, inspId);
			pst.setInt(2, personaId);
			pst.setInt(3, djId);
			
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){
				
				FindInspeccionDj obj = new FindInspeccionDj();
				
				obj.setPredioId(rs.getInt("predio_id"));
				obj.setDjId(rs.getInt("dj_id"));
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setAnnoDj(rs.getInt("anno_dj"));
				obj.setFechaDeclaracion(rs.getDate("fecha_declaracion"));
				obj.setFechaAdquisicion(rs.getDate("fecha_adquisicion"));
				obj.setDescripcionCortaTipoPredio(rs.getString("tipo_predio"));
				obj.setDescripcionTipoPredio(rs.getString("tipo_predio_desc"));
				obj.setPredioIdAntiguo(rs.getString("codigo_anterior"));
				obj.setPorcPropiedad(rs.getBigDecimal("porc_propiedad"));
				obj.setAreaTerreno(rs.getDouble("area_terreno"));
				obj.setAreaTerrenoHas(rs.getDouble("area_terreno_comun"));
				obj.setEstadoFiscalizado(rs.getInt("fiscalizado"));
				obj.setEstado(rs.getInt("estado"));
				obj.setFlagDjAnno(rs.getInt("flag_dj_anno"));
				obj.setFechaRegistro(rs.getDate("fecha_registro"));
				obj.setReqInspeccion(rs.getInt("reqInspeccion"));
				obj.setUbicacionId(rs.getInt("ubicacion_id"));
				obj.setTipoViaId(rs.getInt("tipo_via_id"));
				obj.setViaId(rs.getInt("via_id"));
				obj.setVia(rs.getString("via"));
				obj.setTipoVia(rs.getString("tipo_via"));
				obj.setSectorId(rs.getInt("sector_id"));
				obj.setSector(rs.getString("sector"));
				obj.setManzana(rs.getString("numero_manzana"));
				obj.setCuadra(rs.getString("numero_cuadra"));
				obj.setLadoCuadra(rs.getString("lado_cuadra"));
				obj.setLugar(rs.getString("lugar"));
				obj.setDireccionPredio(rs.getString("direccion_completa"));

//				obj.setNroreqInspeccion(rs.getString("nroreqInspeccion"));
//				obj.setDescripcionCondicionPredio(rs.getString("descripcion"));
				
				
				list.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		//return list;
		if(list!=null&&list.size()>0){
			return list;
		}else 
			return null;
	}
	
	/**Se agregó este método, para que el registro de la dj en rp_fiscalizacion_inspeccion sea en el mismo Form:"registropredio.xhtml"*/
	public List<FindInspeccionDj> getDeclaracionesInspByPersona(Integer personaId,Integer predioId,Integer anioId) throws Exception {
		List<FindInspeccionDj> list =  new ArrayList<FindInspeccionDj>();
		try{
			StringBuilder SQL = new StringBuilder("dbo.stp_getRequerimientoInspeccionByDjPersona ?,?,?");
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			
			pst.setInt(1, personaId);
			pst.setInt(2, predioId);
			pst.setInt(3, anioId);
			
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){
				
				FindInspeccionDj obj = new FindInspeccionDj();
				
				obj.setReqInspeccion(rs.getInt("inspeccion_id"));
				obj.setViaId(rs.getInt("tipo_documento_id"));
				obj.setNroreqInspeccion(rs.getString("nro_requerimiento"));
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setEstado(rs.getInt("inspector_id"));
				obj.setFechaRegistro(rs.getDate("fecha_inspeccion"));
				obj.setAnnoDj(rs.getInt("inspector_id_ar"));
				obj.setFechaDeclaracion(rs.getDate("fecha_inspeccion_ar"));
				obj.setFlagDjAnno(rs.getInt("tipo_documento_id_resultado"));
				obj.setDescripcionTipoPredio(rs.getString("descripcion_requerimiento"));
				obj.setNroResultadoInspeccion(rs.getString("nro_documento_resultado"));

				
				list.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		//return list;
		if(list!=null&&list.size()>0){
			return list;
		}else 
			return null;
	}
	
	
	
	public List<FindInspeccionDj> getDeclaracionesInspeccionById(Integer inspId) throws Exception {
		List<FindInspeccionDj> list =  new ArrayList<FindInspeccionDj>();
		try{
			StringBuilder SQL = new StringBuilder("dbo.stp_getRequerimientoInspeccionByDjAsociada ?");
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, inspId);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){
				
				FindInspeccionDj obj = new FindInspeccionDj();
				
				obj.setDjId(rs.getInt("dj_id"));
				obj.setPredioId(rs.getInt("predio_id"));
				obj.setAnnoDj(rs.getInt("anio_inspeccion"));
				obj.setDireccionPredio(rs.getString("predio_direccion"));
				obj.setSector(rs.getString("predio_sector"));
				obj.setUbicacionId(rs.getInt("ubicacion_id"));

				list.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		//return list;
		if(list!=null&&list.size()>0){
			return list;
		}else 
			return null;
	}
	
	public List<FindInspeccionDj> getPrediosInspById(Integer inspId) throws Exception {
		List<FindInspeccionDj> list =  new ArrayList<FindInspeccionDj>();
		try{
			StringBuilder SQL = new StringBuilder("dbo.stp_getPredioInspeccionByIdRequerimiento ?");
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, inspId);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){
				
				FindInspeccionDj obj = new FindInspeccionDj();
				
				obj.setReqInspeccion(rs.getInt("inspeccion_id"));
				obj.setPredioId(rs.getInt("predio_id"));
				obj.setDjId(rs.getInt("dj_id"));
				obj.setAnnoDj(rs.getInt("anio_inspeccion"));
				obj.setDireccionPredio(rs.getString("predio_direccion"));

				list.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
				
		if(list!=null&&list.size()>0){
			return list;
		}else 
			return null;
	}
	
	public List<FindRpDjPredial> getPrediosInspeccionById(Integer inspId) throws Exception {
		List<FindRpDjPredial> list =  new ArrayList<FindRpDjPredial>();
		try{
			StringBuilder SQL = new StringBuilder("dbo.stp_getPredioInspeccionByIdRequerimiento ?");
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, inspId);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){
				
				FindRpDjPredial obj = new FindRpDjPredial();
				
				obj.setPersonaId(rs.getInt("inspeccion_id"));
				obj.setPredioId(rs.getInt("predio_id"));
				obj.setDjId(rs.getInt("dj_id"));
				obj.setAnioDj(rs.getString("anio_inspeccion"));
				obj.setDireccionCompleta(rs.getString("predio_direccion"));
				
				obj.setCodigoAnterior(rs.getString("codigo_anterior"));
				obj.setTipoPredio(rs.getString("tipo_predio_desc"));
				obj.setPorcPropiedad(rs.getBigDecimal("porc_propiedad"));
				obj.setAreaTerreno(rs.getDouble("area_terreno"));
				obj.setAreaTerrenoHas(rs.getDouble("area_terreno_has"));
				obj.setFechaAdquisicion(rs.getString("fecha_adquisicion"));
				obj.setFechaDeclaracion(rs.getTimestamp("fecha_declaracion"));
				obj.setFiscalizado(rs.getString("fiscalizado"));
				obj.setEstado(rs.getString("estado_dj"));
				obj.setMotivoDeclaracionId(rs.getString("motivo_declaracion_id"));
				obj.setMotivoDescargoId(rs.getInt("motivo_descargo_id"));
				obj.setSelected(true);

				list.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
				
		if(list!=null&&list.size()>0){
			return list;
		}else 
			return null;
	}
	
	public int getUltimaInspeccion() {  //(int persona)
		int salida = 0;
		try {
			CallableStatement cs = connect().prepareCall(
					"{call stp_getIdInspeccion }");
			//cs.setInt(1, persona);
			
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
	
//	public List<FindInspeccionDj> getInspeccionById(Integer persona_id) throws Exception {
//		
//	}
//	public List<RpFiscalizacionInspeccion> getUltimaInspeccion(Integer persona) throws Exception {
//		List<RpFiscalizacionInspeccion> list = new ArrayList<RpFiscalizacionInspeccion>();
//
//		try {
//			String SQL = new String("stp_getIdInspeccion ?");
//			
//			PreparedStatement pst = connect().prepareStatement(SQL.toString());
//			pst.setInt(1, persona);
//			
//			ResultSet rs = pst.executeQuery();
//			while (rs.next()) {
//				//valorCorrelativo
//				RpFiscalizacionInspeccion obj = new RpFiscalizacionInspeccion();
//				
//				obj.setInspeccionId(rs.getInt("inspeccion_id"));
//								
//				list.add(obj);
//
//			}
//		} catch (Exception e) {
//			throw (e);
//		}
//		return list;
//	}
//	
	
	
	public List<FindInspeccionById> getInspeccionById(Integer inspId) throws Exception {
		List<FindInspeccionById> list =  new ArrayList<FindInspeccionById>();
		try{
			StringBuilder SQL = new StringBuilder("dbo.stp_getRequerimientoInspeccionById ?");
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, inspId);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){
				
				FindInspeccionById obj = new FindInspeccionById();
				
				
				obj.setInspeccionId(rs.getInt("inspeccion_id"));
				obj.setTipoDocumentoId(rs.getInt("tipo_documento_id"));
				obj.setTipoDocumentoNombre(rs.getString("tipo_documento_nombre"));
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setApellidosNombres(rs.getString("apellidos_nombres"));
				obj.setProgramaId(rs.getInt("programa_id"));
				obj.setInspectorId(rs.getInt("inspector_id"));
			
				
				list.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		return list;
	}
	
	public List<FindInspeccionByHorario> getInspeccionesHorario(Integer inspId) throws Exception {
		List<FindInspeccionByHorario> list =  new ArrayList<FindInspeccionByHorario>();
		try{
			StringBuilder SQL = new StringBuilder("dbo.stp_getRequerimientoInspeccionByHorario ?");
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, inspId);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){
				
				FindInspeccionByHorario obj = new FindInspeccionByHorario();

				obj.setInspeccionId(rs.getInt("inspeccion_id"));
				obj.setHorarioDetalleId(rs.getInt("horario_detalle_id"));
				obj.setHorarioId(rs.getInt("horario_id"));
				obj.setNombreHorario(rs.getString("nombre_horario"));
				obj.setIntervaloHorario(rs.getString("intervalo_horario"));

				list.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		return list;
	}
	
	public List<FindInspeccionByHorario> getInspeccionesHorarioAr(Integer inspId) throws Exception {
		List<FindInspeccionByHorario> list =  new ArrayList<FindInspeccionByHorario>();
		try{
			StringBuilder SQL = new StringBuilder("dbo.stp_getRequerimientoInspeccionByHorarioAr ?");
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, inspId);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){
				
				FindInspeccionByHorario obj = new FindInspeccionByHorario();

				obj.setInspeccionId(rs.getInt("inspeccion_id"));
				obj.setHorarioDetalleId(rs.getInt("horario_detalle_id"));
				obj.setHorarioId(rs.getInt("horario_id"));
				obj.setNombreHorario(rs.getString("nombre_horario"));
				obj.setIntervaloHorario(rs.getString("intervalo_horario"));

				list.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		//return list;
		if(list!=null&&list.size()>0){
			return list;
		}else
		  return null;
	}
	
	public List<FindInspeccionByDetalle> getInspeccionesDetalle(Integer inspId) throws Exception {
		List<FindInspeccionByDetalle> list =  new ArrayList<FindInspeccionByDetalle>();
		try{
			StringBuilder SQL = new StringBuilder("dbo.stp_getRequerimientoInspeccionByDetalle ?");
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, inspId);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){
				
				FindInspeccionByDetalle obj = new FindInspeccionByDetalle();
				
				
				obj.setInspeccionId(rs.getInt("inspeccion_id"));
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setApellidosNombres(rs.getString("apellidos_nombres"));
				obj.setDireccionFiscal(rs.getString("direccion_completa"));
				obj.setInspectorId(rs.getInt("inspector_id"));
				obj.setApellidosNombresInspector(rs.getString("nombres_apellidos"));
				obj.setFechaInspeccion(rs.getDate("fecha_inspeccion"));
			
				
				list.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		//return list;
		
		if(list!=null&&list.size()>0){
			return list;
		}else
		  return null;
	}
	
	public List<FindInspeccionDocCargoTipo> getAllTipoDocCargo()throws Exception{
		List<FindInspeccionDocCargoTipo> lista=new LinkedList<FindInspeccionDocCargoTipo>();
		try{
			
			String SQL = new String("exec dbo.stp_getTipoDocumentoCargoInspeccion");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			ResultSet rs=pst.executeQuery();
			
			while(rs.next()){
				FindInspeccionDocCargoTipo obj=new FindInspeccionDocCargoTipo(); 
				
				obj.setTipoDocumentoId(rs.getInt("tipo_documento_id"));
				obj.setDescripcionCortaTipoDocumento(rs.getString("descripcion_corta"));
//				obj.setValorCorrelativoDocumento(rs.getInt("valor_actual"));
				
				lista.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	
	public List<FindInspeccionDocCargoTipo> getCorrelativoCargo(Integer tipoDocId) throws Exception {
		List<FindInspeccionDocCargoTipo> list = new ArrayList<FindInspeccionDocCargoTipo>();

		try {
			String SQL = new String("stp_getCorrelativoDocumentoInspeccion ?");
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, tipoDocId);
			
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				//valorCorrelativo
				FindInspeccionDocCargoTipo obj = new FindInspeccionDocCargoTipo();
				
				
				obj.setValorCorrelativo(rs.getString("nro_requerimiento"));
				
				
				list.add(obj);

			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}
	
	public String correlativo(Integer tipoDocId)throws Exception {
		String errorMessage = new String();
		Connection cn = connect();
		try {
			String query = "{ call " + SATParameterFactory.getDBNameScheme()
					+ ".stp_getCorrelativoDocumentoInspeccion(?,?) }";
			// cn.setAutoCommit(false);
			CallableStatement oCallableStatement = cn.prepareCall(query);
			oCallableStatement.registerOutParameter(1, Types.VARCHAR);
			oCallableStatement.setInt(2, tipoDocId);
			
			oCallableStatement.execute();
			errorMessage = oCallableStatement.getString(1);
		} catch (Exception e) {
			throw (e);
		}
		return errorMessage;
	}
	
	
	
//	public int actualizarRequerimiento(Integer recId,Integer noNotificacionId, Integer notificadorId,Date fechaNotifica ) throws Exception {
//		int result = 0;
//		try {
//			StringBuffer SQL = new StringBuffer();
//			
//			
//				SQL.append(" UPDATE " + SATParameterFactory.getDBNameScheme()
//						+ ".rp_fiscalizacion_inspeccion ");
//				SQL.append(" SET motivo_notificacion_id =?,notificador_id =?,fecha_notificacion=?");
//				SQL.append(" where rec_id = ? ");
//			
//			PreparedStatement pst = connect().prepareStatement(SQL.toString());
//			pst.setInt(1,noNotificacionId);
//			pst.setInt(2,notificadorId);
//			pst.setTimestamp(3, DateUtil.dateToSqlTimestamp(fechaNotifica));
//			pst.setInt(4,recId );
//			
//			result = pst.executeUpdate();
//		} catch (Exception e) {
//			throw (e);
//		}
//		return result;
//	}
	
//	public int actualizarRequerimientoNotificacion (Date fechaNotifica,Integer inspId) throws Exception {
//
//		int salida = 0;
//		try {
//			CallableStatement cs = connect().prepareCall(
//					"{call dbo.spt_actualizaRequerimientoInspeccionNotifica(?,?)}");
//			
//			cs.setTimestamp(1, DateUtil.dateToSqlTimestamp(fechaNotifica));
//			cs.setInt(2, inspId);
//			
//		
//			cs.execute();
//		} catch (Exception ex) {
//			System.out.println("ERROR: " + ex);
//		}
//		return salida;
//	}
	
	public int actualizarRequerimientoNotificacion (Integer nombreInspector, Date fechaInspeccion,
			Integer codPersona,String observacion, String dirPersona,Date fechaNotifica,Integer estado,Integer inspId,
			Integer usuarioId,String terminal) throws Exception {

		int salida = 0;
		try {
			CallableStatement cs = connect().prepareCall(
					"{call dbo.spt_actualizaRequerimientoInspeccionNotifica(?,?,?,?,?,?,?,?,?,?)}");
			
			cs.setInt(1, codPersona);
			cs.setInt(2, nombreInspector);
			cs.setTimestamp(3, DateUtil.dateToSqlTimestamp(fechaInspeccion));
			
			if (fechaNotifica == null) 
			{
				cs.setString(4,null);
			} else{
				
				cs.setTimestamp(4, DateUtil.dateToSqlTimestamp(fechaNotifica));
				
			}

			cs.setString(5, observacion);
			cs.setString(6, dirPersona);

			cs.setInt(7, estado);
			cs.setInt(8, inspId);
			cs.setInt(9, usuarioId);
			cs.setString(10, terminal);
			


			cs.execute();
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
		}
		return salida;
	}
	public int decargarOmniso(int omisoId,String justificacion){
		int salida=0;
		try{
			CallableStatement cs = connect().prepareCall(
					"{call dbo.spt_descargarCarteraOmiso(?,?)}");
			cs.setInt(1, omisoId);
			cs.setString(2, justificacion);
			cs.execute();			
			
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
		}
		return salida;
	}
	

	public List<Integer> estadisticaLote(int loteId){
	
				List<Integer> list = new ArrayList<Integer>();

			try {
							
				String SQL = new String("stp_rv_obtener_estadistica_lote_vehicular ?");

				PreparedStatement pst = connect().prepareStatement(SQL.toString());
				pst.setInt(1, loteId);
				ResultSet rs = pst.executeQuery();				
				while (rs.next()) {

					list.add(rs.getInt("activo"));
					list.add(rs.getInt("declarado"));
					list.add(rs.getInt("requerido"));
					list.add(rs.getInt("descargado"));
					list.add(rs.getInt("total"));

				}												
			} catch (Exception ex) {
				System.out.println("ERROR: " + ex);
			}
			return list;
	}		
			
	
	
	public int actualizarRequerimiento (
			Integer tipoDocumento,String nroDocumentoResultado, Date fechaNotResultado,
			Integer tipoEsquela,String nroDocumentoEsquela, Date fechaNotEsquela,
			Integer tipoAr,String nroDocumentoAr, 
			Integer usuarioId,String terminal,Integer inspId,Integer estado,Date fechaNotAr) throws Exception {

		int salida = 0;
		try {
			CallableStatement cs = connect().prepareCall(
					"{call dbo.spt_actualizaRequerimientoInspeccion(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			
			cs.setInt(1, tipoDocumento);
			cs.setString(2, nroDocumentoResultado);
			
			if (tipoDocumento == 0) {
				cs.setString(3,null);
			} else{
				
				cs.setTimestamp(3, DateUtil.dateToSqlTimestamp(fechaNotResultado));
				
			}
			
			cs.setInt(4, tipoEsquela);
			cs.setString(5, nroDocumentoEsquela);
			cs.setTimestamp(6, DateUtil.dateToSqlTimestamp(fechaNotEsquela));
			cs.setInt(7, tipoAr);
			cs.setString(8, nroDocumentoAr);
			cs.setInt(9, usuarioId);
			cs.setString(10, terminal);
			cs.setInt(11, inspId);
			cs.setInt(12, estado);
			
			if (tipoAr == 0) {
				cs.setString(13,null);
			} else{
				
				cs.setTimestamp(13, DateUtil.dateToSqlTimestamp(fechaNotAr));
				
			}
			
		
			cs.execute();
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
		}
		return salida;
	}
	
	public int actualizarRequerimientoDetalle(Date fechaAr,Integer inspectorArId,Date fechaActualiza,Integer inspId) throws Exception {
		int result = 0;
		try {
			StringBuffer SQL = new StringBuffer();
			
			
				SQL.append(" UPDATE " + SATParameterFactory.getDBNameScheme()
						+ ".rp_fiscalizacion_detalle_inspeccion ");
				SQL.append(" SET fecha_inspeccion_ar =?,inspector_id_ar =?,fecha_actualizacion=?");
				SQL.append(" where inspeccion_id = ? ");
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setTimestamp(1, DateUtil.dateToSqlTimestamp(fechaAr));
			pst.setInt(2,inspectorArId);
			pst.setTimestamp(3, DateUtil.dateToSqlTimestamp(fechaActualiza));
			pst.setInt(4,inspId );
			
			result = pst.executeUpdate();
		} catch (Exception e) {
			throw (e);
		}
		return result;
	}
	
	public int actualizarRequerimientoArFIP(Integer fipId,String fipNro,Date fechagenera,Date fechanotifica,Integer usuarioId,String terminal,Integer inspId) throws Exception {
		int result = 0;	

		try {
			StringBuffer SQL = new StringBuffer();
			
			
				SQL.append(" UPDATE " + SATParameterFactory.getDBNameScheme()
						+ ".rp_fiscalizacion_inspeccion ");
				SQL.append(" SET tipo_documento_id_resultado_ar =?,nro_documento_resultado_ar =?,fecha_generacion_resultado_ar=?,fecha_notificacion_resultado_ar=?");
				SQL.append(" ,estado=3,usuario_id_actualiza=?,terminal_actualiza=? ");
				SQL.append(" where inspeccion_id = ? ");
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1,fipId);
			pst.setString(2,fipNro);
			pst.setTimestamp(3, DateUtil.dateToSqlTimestamp(fechagenera));
			pst.setTimestamp(4, DateUtil.dateToSqlTimestamp(fechanotifica));
			pst.setInt(5,usuarioId);
			pst.setString(6,terminal);
			pst.setInt(7,inspId );

			
			result = pst.executeUpdate();
		} catch (Exception e) {
			throw (e);
		}
		return result;
	}
	
	
	public List<FindInspeccionByResultado> getAllInspeccionesResultado(Integer inspId) throws Exception {
		List<FindInspeccionByResultado> list = new ArrayList<FindInspeccionByResultado>();

		try {
			String SQL = new String("stp_getRequerimientoInspeccionByResultado ?");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, inspId);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				FindInspeccionByResultado obj = new FindInspeccionByResultado();
				
				
				obj.setInspeccionId(rs.getInt("inspeccion_id"));
				obj.setNroDocumento(rs.getString("nro_Documento"));
				//obj.setTipoDoc(rs.getString("descripcion"));
				obj.setTipoDocResultado(rs.getString("dr"));
				obj.setNroDocumentoResultadoFip(rs.getString("nro_documento_resultado"));
				//obj.setNroDocumentoResultadoFip(rs.getString("nroAinr"));
				obj.setFechaFip(rs.getDate("fecha_notificacion_resultado"));
				obj.setDocumentoId(rs.getInt("id"));
				//obj.setFechaAinr(rs.getDate("fechaAinr"));
//				obj.setTipoDocEsquela(rs.getString("dea"));
//				obj.setNroDocumentoEsquela(rs.getString("nro_esquela"));
//				obj.setFechaNotificacionEsquela(rs.getDate("fecha_notificacion_esquela"));
//				obj.setTipoDocAr(rs.getString("dar"));
//				obj.setNroDocumentoAr(rs.getString("nro_reprogramacion"));
//				obj.setFechaNotificacionAr(rs.getDate("fecha_generacion_reprogramacion"));

				list.add(obj);

			}
		} catch (Exception e) {
			throw (e);
		}
		if(list!=null&&list.size()>0){
			return list;
		}else
		return null;
		//return list;
	}
	
	
	public List<FindInspeccionByHorario> getInspeccionesHorarioByResultado(Integer inspId) throws Exception {

		
		List<FindInspeccionByHorario> list =  new ArrayList<FindInspeccionByHorario>();
			
		try{
			StringBuilder SQL = new StringBuilder("dbo.stp_getRequerimientoInspeccionByHorarioDetalle ?");
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, inspId);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){
				
				FindInspeccionByHorario obj = new FindInspeccionByHorario();
				
				
				obj.setInspeccionId(rs.getInt("inspeccion_id"));
				obj.setHorarioDetalleId(rs.getInt("horario_detalle_id"));
				obj.setHorarioId(rs.getInt("horario_id"));
				obj.setNombreHorario(rs.getString("nombre_horario"));
				obj.setIntervaloHorario(rs.getString("intervalo_horario"));
				obj.setEstadoId(rs.getInt("estado"));
				obj.setDescripcionEstado(rs.getString("descripcion_estado"));
				obj.setFechaRegistro(rs.getDate("fecha_registro"));
				obj.setUsuarioId(rs.getInt("usuario_id"));
				obj.setNombreUsuario(rs.getString("nombre_usuario"));
			
				
				list.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		return list;
	}
	
	public ArrayList<FindInspeccionHistorial> findInspeccion(Integer contribuyenteId,String correlativo,String direccion,String apellidos,Integer dniId,String dniNumero) throws Exception {
 
		ArrayList<FindInspeccionHistorial> list =  new ArrayList<FindInspeccionHistorial>();
		try{
		StringBuffer stBuffer = new StringBuffer();
		stBuffer.append(" select insp.inspeccion_id, insp.tipo_documento_id, doc.descripcion AS tipo_documento_nombre, ");
		stBuffer.append(" CASE insp.tipo_documento_id WHEN 50 THEN insp.nro_requerimiento WHEN 4 THEN insp.nro_carta END AS nro_requerimiento, insp.persona_id, per.apellidos_nombres, insp.domicilio_fiscal AS domicilio_persona,  ");
		stBuffer.append(" per.nro_docu_identidad AS dni_persona, 0 AS ubicacion_id, 0 AS sector_id, 0 AS via_id, '' AS nombre_sector, '' AS nombre_via, '' AS nombre_manzana, insp.programa_id, prog.nombre_programa, ");
		stBuffer.append(" insp.estado,CASE insp.estado WHEN '1' THEN 'Emitido' WHEN '2' THEN 'Notificado' WHEN '3' THEN 'Con inspección' WHEN '4' THEN 'Con Acta de inspección no realizada' WHEN '5' THEN 'Con declaración jurada sin pago' ");
		stBuffer.append(" WHEN '6' THEN 'Con declaración jurada con pago ' WHEN '7' THEN 'Conforme' WHEN '8' THEN 'Con requerimiento de sustento de reparos y notificado' WHEN '9' THEN 'Con resultado de requerimiento de sustentación de reparos notificado' ");
		stBuffer.append(" WHEN '10' THEN 'Con resultado de requerimiento' WHEN '11' THEN 'Con valores emitidos - Multa' WHEN '12' THEN 'Con valores emitidos - RD' WHEN '13' THEN 'Emitidos por anular' WHEN '14' THEN 'Con informe final' WHEN '15' THEN 'Archivado' END AS estado_descripcion, ");
		stBuffer.append(" insp.fecha_emision, insp.fecha_notificacion, insp.inspector_id, perf.nombre_inspector AS nombre_inspector, ");
		stBuffer.append(" perf.nro_dni AS dni_inspector, insp.tipo_documento_id_resultado, docResultado.descripcion_corta AS resultado_descripcion, insp.nro_documento_resultado, insp.fecha_notificacion_resultado, ");
		stBuffer.append(" insp.tipo_documento_id_esquela, docEsquela.descripcion AS esquela_descripcion, insp.nro_esquela, insp.fecha_notificacion_esquela, insp.tipo_documento_id_reprogramacion, ");
		stBuffer.append(" docAr.descripcion_corta AS ar_descripcion, insp.nro_reprogramacion, insp.fecha_generacion_reprogramacion, '' AS numero_dj, insp.fecha_notificacion_resultado AS fecha_dj, 'Abierto' AS condicion, ");
		stBuffer.append(" insp.glosa AS observaciones, u.nombre_usuario, insp.fecha_registro ");
		stBuffer.append(" from ").append(Constante.schemadb).append(".rp_fiscalizacion_inspeccion AS insp  ");
		stBuffer.append(" inner join ").append(Constante.schemadb).append(".gn_tipo_documento AS doc ON insp.tipo_documento_id = doc.tipo_documento_id "); 
		stBuffer.append(" inner join ").append(Constante.schemadb).append(".mp_persona AS per ON insp.persona_id = per.persona_id  ");
		stBuffer.append(" inner join ").append(Constante.schemadb).append(".rp_fiscalizacion_programa AS prog ON insp.programa_id = prog.programa_id ");  
		stBuffer.append(" inner join ").append(Constante.schemadb).append(".sg_usuario AS u ON insp.usuario_id = u.usuario_id ");
		stBuffer.append(" inner join ").append(Constante.schemadb).append(".mp_fiscalizador AS perf ON insp.inspector_id = perf.inspector_id ");
		stBuffer.append(" left outer join ").append(Constante.schemadb).append(".gn_tipo_documento AS docAr ON insp.tipo_documento_id_reprogramacion = docAr.tipo_documento_id ");
		stBuffer.append(" left outer join ").append(Constante.schemadb).append(".gn_tipo_documento AS docEsquela ON insp.tipo_documento_id_esquela = docEsquela.tipo_documento_id "); 
		stBuffer.append(" left outer join ").append(Constante.schemadb).append(".gn_tipo_documento AS docResultado ON insp.tipo_documento_id_resultado = docResultado.tipo_documento_id");
		stBuffer.append(" where insp.estado NOT IN ('13','0') ");
		
		if(contribuyenteId!=null&&contribuyenteId>0){
		        	stBuffer.append(" and insp.persona_id=").append(contribuyenteId).append("");
		}
		else
			if (direccion!=null&&direccion.trim().length()>0){
				stBuffer.append(" and insp.domicilio_fiscal like '%").append(direccion).append("%' ");
		}else
			if (correlativo!=null&&correlativo.trim().length()>0){
				stBuffer.append(" and insp.nro_requerimiento like '%").append(correlativo).append("%' or insp.nro_carta like'%").append(correlativo).append("%' ");
		}else
			if (apellidos!=null&&apellidos.trim().length()>0){
				stBuffer.append(" and per.apellidos_nombres='").append(apellidos).append("'");
		}else
			if(dniId!=null&&dniId>0){
				if(dniNumero!=null&&!dniNumero.isEmpty()){
					stBuffer.append("and per.tipo_doc_identidad_id=").append(dniId).append(" and per.nro_docu_identidad=").append(dniNumero).append("");
				}
			}

//		else
//		 if (docreqId!=null&&docreqId>0){
//			if (docreqId==Constante.TIPO_DOC_CARTA_ID){
//				    stBuffer.append(" and insp.tipo_documento_id=? and insp.nro_carta=? ");
//				}else if (docreqId==Constante.TIPO_DOC_REQ_ID){
//					stBuffer.append(" and insp.tipo_documento_id=? and insp.nro_requerimiento=? ");
//				}if (docreqId==Constante.TIPO_DOC_CARTA_MULT_ID){
//				    stBuffer.append(" and insp.tipo_documento_id=? and insp.nro_carta=? ");
//				}
//		}
		
			PreparedStatement pstm = connect().prepareStatement(stBuffer.toString());
			if(contribuyenteId!=null&&contribuyenteId>0){
				;
			}
			else
			if(direccion!=null&&direccion.trim().length()>0){
				;
			}else
				if (correlativo!=null&&correlativo.trim().length()>0){
					;
			}else
				if (apellidos!=null&&apellidos.trim().length()>0){
					;
			}else
				if(dniId!=null&&dniId>0){
					if(dniNumero!=null&&!dniNumero.isEmpty()){
						;
					}
				}
			
			
//			else
//			if(docreqId!=null&&correlativo!=null&&correlativo.trim().length()>0){
//				pstm.setInt(1, docreqId);
//				pstm.setString(2, correlativo);
//			}
			
			ResultSet rs = pstm.executeQuery();
			
			while(rs.next()){
				FindInspeccionHistorial obj = new FindInspeccionHistorial();
				obj.setInspeccionId(rs.getInt("inspeccion_id"));
				obj.setTipoDocumentoId(rs.getInt("tipo_documento_id"));
				obj.setTipoDocumentoNombre(rs.getString("tipo_documento_nombre"));
				obj.setNroRequerimiento(rs.getString("nro_requerimiento"));
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setApellidosNombres(rs.getString("apellidos_nombres"));
				obj.setPersonaDomicilioFiscal(rs.getString("domicilio_persona"));
				obj.setPersonaDni(rs.getString("dni_persona"));
				obj.setUbicacionId(rs.getInt("ubicacion_id"));
				obj.setSectorId(rs.getInt("sector_id"));
				obj.setViaId(rs.getInt("via_id"));
				obj.setSectorNombre(rs.getString("nombre_sector"));
				obj.setViaNombre(rs.getString("nombre_via"));
				obj.setManzanaNombre(rs.getString("nombre_manzana"));
				obj.setProgramaId(rs.getInt("programa_id"));
				obj.setProgramaNombre(rs.getString("nombre_programa"));
				obj.setEstado(rs.getInt("estado"));
				obj.setEstadoDescripcion(rs.getString("estado_descripcion"));
				obj.setFechaEmision(rs.getDate("fecha_emision"));//
				obj.setFechaNotificacion(rs.getDate("fecha_notificacion"));//
				obj.setInspectorId(rs.getInt("inspector_id"));
				obj.setInspectorNombre(rs.getString("nombre_inspector"));
				obj.setInspectorDni(rs.getString("dni_inspector"));
				obj.setResultadoId(rs.getInt("tipo_documento_id_resultado"));
				obj.setResultadoNombre(rs.getString("resultado_descripcion"));
				obj.setResultadoNumero(rs.getString("nro_documento_resultado"));
				obj.setFechaResultado(rs.getDate("fecha_notificacion_resultado"));
				obj.setEsquelaId(rs.getInt("tipo_documento_id_esquela"));
				obj.setEsquelaNombre(rs.getString("esquela_descripcion"));
				obj.setEsquelaNumero(rs.getString("nro_esquela"));
				obj.setFechaEsquela(rs.getDate("fecha_notificacion_esquela"));
				obj.setArId(rs.getInt("tipo_documento_id_reprogramacion"));
				obj.setArNombre(rs.getString("ar_descripcion"));
				obj.setArNumero(rs.getString("nro_reprogramacion"));
				obj.setFechaAr(rs.getDate("fecha_generacion_reprogramacion"));
				obj.setDjNumero(rs.getString("numero_dj"));
				obj.setDjFecha(rs.getDate("fecha_dj"));
				obj.setCondicion(rs.getString("condicion"));
				obj.setObservaciones(rs.getString("observaciones"));
				obj.setNombreUsuario(rs.getString("nombre_usuario"));//
				obj.setFechaRegistro(rs.getDate("fecha_registro"));
				list.add(obj);
				
			}
				
			
	}catch(Exception e){
		throw(e);
	}
		if(list!=null&&list.size()>0){
			return list;
		}else
		return null;
	}
	
		public List<FindInspeccionHistorial> getInspeccionByCorrelativo(String correlativo, Integer tipo) throws Exception {
			List<FindInspeccionHistorial> list =  new ArrayList<FindInspeccionHistorial>();
			
				try{
					StringBuilder SQL = new StringBuilder("dbo.stp_getRequerimientoInspeccionByCorrelativo ?,?");
					
					PreparedStatement pst = connect().prepareStatement(SQL.toString());
					pst.setString(1, correlativo);
					pst.setInt(2, tipo);
					ResultSet rs = pst.executeQuery();
					
					while(rs.next()){
				
						FindInspeccionHistorial obj = new FindInspeccionHistorial();
				
						obj.setInspeccionId(rs.getInt("inspeccion_id"));
						if (tipo==Constante.TIPO_DOC_CARTA_ID||tipo==Constante.TIPO_DOC_REQ_ID||tipo==Constante.TIPO_DOC_CARTA_MULT_ID){
							obj.setTipoDocumentoId(rs.getInt("tipo_documento_id"));
							obj.setNroRequerimiento(rs.getString("nro_requerimiento"));
						}else if (tipo==Constante.TIPO_DOC_FIP_ID||tipo==Constante.TIPO_DOC_AINR_ID){
							obj.setResultadoId(rs.getInt("tipo_documento_id_resultado"));
							obj.setResultadoNumero(rs.getString("nro_documento_resultado"));
						}else if (tipo==Constante.TIPO_DOC_AR_ID){
							obj.setArId(rs.getInt("tipo_documento_id_reprogramacion"));
							obj.setArNumero(rs.getString("nro_reprogramacion"));
						}else if (tipo==Constante.TIPO_DOC_EA_ID){
							obj.setEsquelaId(rs.getInt("tipo_documento_id_esquela"));
							obj.setEsquelaNumero(rs.getString("nro_esquela"));
						}
						

				list.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
				if(list!=null&&list.size()>0){
					return list;
				}else
				return null;
		//return list;
	}
		
		public List<FindInspeccionByHorario> getHorarioByInspector(Integer inspector,Date fechaInspeccion,Integer horaInspeccion) throws Exception {
		//public String getHorarioByInspector(Integer inspector,Date fechaInspeccion,Integer horaInspeccion) throws Exception {
		    //String resultado = "Si";
			List<FindInspeccionByHorario> list =  new ArrayList<FindInspeccionByHorario>();
			
				try{
					StringBuilder SQL = new StringBuilder("dbo.stp_getHoraInspeccionByInspector ?,?,?");
					
					PreparedStatement pst = connect().prepareStatement(SQL.toString());
					pst.setInt(1, inspector);
					pst.setTimestamp(2, DateUtil.dateToSqlTimestamp(fechaInspeccion));
					pst.setInt(3, horaInspeccion);
					
					ResultSet rs = pst.executeQuery();
					
					while(rs.next()){
				
						FindInspeccionByHorario obj = new FindInspeccionByHorario();
				
						obj.setInspeccionId(rs.getInt("inspeccion_id"));
						obj.setUsuarioId(rs.getInt("inspector_id"));
						obj.setFechaRegistro(rs.getDate("fecha_inspeccion"));
						obj.setHorarioDetalleId(rs.getInt("horario_detalle_id"));
						obj.setHorarioId(rs.getInt("horario_id"));
						obj.setIntervaloHorario(rs.getString("intervalo_horario"));
						obj.setEstadoId(rs.getInt("estado"));
						obj.setNombreUsuario(rs.getString("inspector"));

				list.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
				if(list!=null&&list.size()>0){
					return list;
					//return resultado;
				}else 
					//return resultado="No";
				return null;
				
		//return list;
	}	
		
		public List<FindInspeccionHistorialDetalle> getAllInspeccionesDetalle(Integer inspId) throws Exception {
			List<FindInspeccionHistorialDetalle> list = new ArrayList<FindInspeccionHistorialDetalle>();

			try {
				String SQL = new String("stp_getAllRequerimientoInspeccionDetalle ?");

				PreparedStatement pst = connect().prepareStatement(SQL.toString());
				pst.setInt(1, inspId);
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {

					FindInspeccionHistorialDetalle obj = new FindInspeccionHistorialDetalle();
					
					
					obj.setInspeccionId(rs.getInt("inspeccion_id"));
					obj.setTipoDocumentoId(rs.getInt("tipo_documento_id"));
					obj.setNroRequerimiento(rs.getString("nro_Documento"));
					obj.setPersonaId(rs.getInt("persona_id"));
					obj.setApellidosNombres(rs.getString("apellidos_nombres"));
					obj.setPersonaDomicilioFiscal(rs.getString("domicilio_fiscal"));
					obj.setResultadoId(rs.getInt("tipo_documento_id_resultado"));
					obj.setResultadoNumero(rs.getString("nro_documento_resultado"));
					obj.setArId(rs.getInt("tipo_documento_id_reprogramacion"));
					obj.setArNumero(rs.getString("nro_reprogramacion"));
					obj.setInspeccionDetId(rs.getInt("inspeccion_detalle_id"));
					obj.setPredioId(rs.getInt("predio_id"));
					obj.setDjId(rs.getInt("dj_id"));
					obj.setDjAnio(rs.getInt("anio_inspeccion"));

					list.add(obj);

				}
			} catch (Exception e) {
				throw (e);
			}
			return list;
		}	
		
		public List<FindInspeccionDj> getInspeccionesByPredio(Integer personaId,Integer inspId) throws Exception {
			List<FindInspeccionDj> list =  new ArrayList<FindInspeccionDj>();
			try{
				StringBuilder SQL = new StringBuilder("dbo.stp_getRequerimientoInspeccionByPredio ?,?");
				
				PreparedStatement pst = connect().prepareStatement(SQL.toString());
				pst.setInt(1, personaId);
				pst.setInt(2, inspId);
				ResultSet rs = pst.executeQuery();
				
				while(rs.next()){
					
					FindInspeccionDj obj = new FindInspeccionDj();
					
					obj.setPredioId(rs.getInt("codigo_predio"));
					obj.setDireccionPredio(rs.getString("direccion_completa"));
					obj.setDescripcionCortaTipoPredio(rs.getString("tipo_predio"));
					
//					obj.setDjId(rs.getInt("dj_id"));
//					obj.setPersonaId(rs.getInt("persona_id"));
//					obj.setAnnoDj(rs.getInt("anno_dj"));
//					obj.setDescripcionTipoPredio(rs.getString("tipo_predio_desc"));
//					obj.setEstado(rs.getInt("estado"));
//					obj.setFlagDjAnno(rs.getInt("flag_dj_anno"));
//					obj.setEstadoFiscalizado(rs.getInt("fiscalizado"));
//					obj.setPredioIdAntiguo(rs.getString("codigo_predio"));
//					obj.setDescripcionCondicionPredio(rs.getString("descripcion"));
//					obj.setDescripcionCortaTipoPredio(rs.getString("tipo_predio"));
//					obj.setPorcPropiedad(rs.getBigDecimal("porc_propiedad"));
//					obj.setAreaTerreno(rs.getDouble("area_terreno"));
//					obj.setAreaTerrenoHas(rs.getDouble("area_terreno_comun"));
//					obj.setFechaDeclaracion(rs.getDate("fecha_declaracion"));
//					obj.setFechaAdquisicion(rs.getDate("fecha_adquisicion"));
//					obj.setFechaRegistro(rs.getDate("fecha_registro"));
//					obj.setReqInspeccion(rs.getInt("reqInspeccion"));
//					obj.setNroreqInspeccion(rs.getString("nroreqInspeccion"));
					
					list.add(obj);
				}
			}catch(Exception e){
				throw(e);
			}
			return list;
		}
		public List<RpFiscalizacionProgramaDetalle> getAllAnios(Integer programaId)throws Exception{
			List<RpFiscalizacionProgramaDetalle> lista=new LinkedList<RpFiscalizacionProgramaDetalle>();
			try{
				
				String SQL = new String("select programa_detalle_id,anio_fiscalizacion from dbo.rp_fiscalizacion_detalle_programa where programa_id=? and estado=1 order by anio_fiscalizacion desc");

				
				PreparedStatement pst=connect().prepareStatement(SQL.toString());
				pst.setInt(1, programaId);
				ResultSet rs=pst.executeQuery();
				
				while(rs.next()){
					RpFiscalizacionProgramaDetalle obj=new RpFiscalizacionProgramaDetalle(); 
					
					obj.setProgramaDetalleId(rs.getInt("programa_detalle_id"));
					obj.setAnioFiscalizacion(rs.getString("anio_fiscalizacion"));
					//obj.setNombreHorario(rs.getString("nombre_horario"));
				
					
					lista.add(obj);
				}
			}catch(Exception e){
				throw(e);
			}
			return lista;
		}
		
		public List<RpFiscalizacionProgramaDetalle> getAllAniosById(Integer inspId)throws Exception{
			List<RpFiscalizacionProgramaDetalle> lista=new LinkedList<RpFiscalizacionProgramaDetalle>();
			try{
//				String SQL = new String("(SELECT strval  FROM dbo.SPLIT ((select anio_inspeccion from dbo.rp_fiscalizacion_inspeccion where inspeccion_id =?), ',') AS SPLIT_1)");
				String SQL = new String("select anio_inspeccion from dbo.rp_fiscalizacion_inspeccion where inspeccion_id =?");
				
				PreparedStatement pst=connect().prepareStatement(SQL.toString());
				pst.setInt(1, inspId);
				ResultSet rs=pst.executeQuery();
				
				while(rs.next()){
					RpFiscalizacionProgramaDetalle obj=new RpFiscalizacionProgramaDetalle(); 
					
					//obj.setProgramaDetalleId(rs.getInt("programa_detalle_id"));
					obj.setAnioFiscalizacion(rs.getString("anio_inspeccion"));
					//obj.setNombreHorario(rs.getString("nombre_horario"));
				
					
					lista.add(obj);
				}
			}catch(Exception e){
				throw(e);
			}
			return lista;
		}
		
		public List<RpFiscalizacionProgramaDetalle> getAllAniosReq()throws Exception{
			List<RpFiscalizacionProgramaDetalle> lista=new LinkedList<RpFiscalizacionProgramaDetalle>();
			try{
				
				String SQL = new String("stp_getAnioInspeccion");

				
				PreparedStatement pst=connect().prepareStatement(SQL.toString());
				ResultSet rs=pst.executeQuery();
				
				while(rs.next()){
					RpFiscalizacionProgramaDetalle obj=new RpFiscalizacionProgramaDetalle(); 
					
					obj.setProgramaDetalleId(rs.getInt("id"));
					obj.setAnioFiscalizacion(rs.getString("lista_anios"));
					
				
					
					lista.add(obj);
				}
			}catch(Exception e){
				throw(e);
			}
			return lista;
		}
		
		public int getUltimoPrograma() {  //int persona
			int salida = 0;
			try {
				CallableStatement cs = connect().prepareCall(
						"{call stp_getIdPrograma }");
				//cs.setInt(1, persona);
				
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
		
		public List<RpFiscalizacionPrograma> getAllPrograma() throws Exception {
			List<RpFiscalizacionPrograma> list = new ArrayList<RpFiscalizacionPrograma>();

			try {
				String SQL = new String("stp_getAllProgramaInspeccion");

				PreparedStatement pst = connect().prepareStatement(SQL.toString());
				
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {

					RpFiscalizacionPrograma obj = new RpFiscalizacionPrograma();
					
					
					obj.setProgramaId(rs.getInt("programa_id"));
					obj.setNombrePrograma(rs.getString("nombre_programa"));
					obj.setTermninal(rs.getString("periodo"));
					obj.setEstado(rs.getString("estado"));
					obj.setDescripcionCortaPrograma(rs.getString("nombre_usuario"));

					list.add(obj);

				}
			} catch (Exception e) {
				throw (e);
			}
			if(list!=null&&list.size()>0){
				return list;
			}else
			return null;
			//return list;
		}
		
		public ArrayList<RpFiscalizacionPrograma> findPrograma(String programa) throws Exception {
			 
			ArrayList<RpFiscalizacionPrograma> list =  new ArrayList<RpFiscalizacionPrograma>();
			try{
			StringBuffer stBuffer = new StringBuffer();
			stBuffer.append(" SELECT rp_fiscalizacion_programa.programa_id,");
			stBuffer.append(" rp_fiscalizacion_programa.nombre_programa,  ");
			stBuffer.append(" dbo.get_programa_x_inspeccion(rp_fiscalizacion_programa.programa_id) AS periodo, ");
			stBuffer.append(" rp_fiscalizacion_programa.estado, ");
			stBuffer.append(" sg_usuario.nombre_usuario ");
			stBuffer.append(" from ").append(Constante.schemadb).append(".rp_fiscalizacion_programa  ");
			stBuffer.append(" inner join ").append(Constante.schemadb).append(".sg_usuario ON rp_fiscalizacion_programa.usuario_id = sg_usuario.usuario_id "); 
			stBuffer.append(" where ");
			
			if(programa!=null&&programa.trim().length()>0){
				stBuffer.append("rp_fiscalizacion_programa.nombre_programa like '%").append(programa).append("%' ");
			}
			
				PreparedStatement pstm = connect().prepareStatement(stBuffer.toString());
				if(programa!=null&&programa.trim().length()>0){
					//pstm.setString(1, programa);
					;
				}
				
				
				ResultSet rs = pstm.executeQuery();
				
				while(rs.next()){
					RpFiscalizacionPrograma obj = new RpFiscalizacionPrograma();
					obj.setProgramaId(rs.getInt("programa_id"));
					obj.setNombrePrograma(rs.getString("nombre_programa"));
					obj.setTermninal(rs.getString("periodo"));
					obj.setEstado(rs.getString("estado"));
					obj.setDescripcionCortaPrograma(rs.getString("nombre_usuario"));
					list.add(obj);
					
				}
					
				
		}catch(Exception e){
			throw(e);
		}
			return list;
		}
		
		
		public List<RpFiscalizacionProgramaDetalle> getAllProgramaAniosById(Integer programaId) throws Exception {
			List<RpFiscalizacionProgramaDetalle> list =  new ArrayList<RpFiscalizacionProgramaDetalle>();
			try{
				StringBuilder SQL = new StringBuilder("dbo.stp_getRequerimientoProgramaById ?");
				
				PreparedStatement pst = connect().prepareStatement(SQL.toString());
				pst.setInt(1, programaId);
				ResultSet rs = pst.executeQuery();
				
				while(rs.next()){
					
					RpFiscalizacionProgramaDetalle obj = new RpFiscalizacionProgramaDetalle();

					obj.setProgramaId(rs.getInt("programa_id"));
					obj.setAnioFiscalizacion(rs.getString("anio_fiscalizacion"));

					list.add(obj);
				}
			}catch(Exception e){
				throw(e);
			}
			return list;
		}	
		
		public int actualizarPrograma(String descripcion,Integer programaId) throws Exception {
			int result = 0;
			try {
		
	       
			       CallableStatement cs = connect().prepareCall("{call dbo.spt_actualizaRequerimientoPrograma(?,?)}");
			       cs.setString(1,descripcion);
			       cs.setInt(2,programaId );
			       cs.execute();
				
	
			} catch (Exception e) {
				throw (e);
			}
			return result;
		}
		
		public List<MpFiscalizadorDto> getAllInspectores() throws Exception {
			List<MpFiscalizadorDto> list = new ArrayList<MpFiscalizadorDto>();

			try {
				String SQL = new String("stp_getAllFiscalizadorInspeccion");
				

				PreparedStatement pst = connect().prepareStatement(SQL.toString());
				
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {

					MpFiscalizadorDto obj = new MpFiscalizadorDto();
					
					
					obj.setIdfiscalizador(rs.getInt("inspector_id"));
					obj.setCodigo(rs.getString("codigo"));
					obj.setNombresApellidos(rs.getString("nombre_inspector"));
					obj.setDni(rs.getString("nro_dni"));
					obj.setDireccion(rs.getString("nombre_direccion"));
					obj.setTermninal(rs.getString("unidad"));
					obj.setFini(rs.getDate("fecha_inicio"));
					obj.setFfin(rs.getDate("fecha_fin"));
					obj.setCelular(rs.getString("nro_celular"));
				

					list.add(obj);

				}
			} catch (Exception e) {
				throw (e);
			}
			if(list!=null&&list.size()>0){
				return list;
			}else
			return null;
			//return list;
		}
		
		public List<MpFiscalizadorArea> getAllTipoArea()throws Exception{
			List<MpFiscalizadorArea> lista=new LinkedList<MpFiscalizadorArea>();
			try{
				
				String SQL = new String("select unidad_id,descripcion from gn_unidad where estado=1 order by unidad_id");

				
				PreparedStatement pst=connect().prepareStatement(SQL.toString());
				ResultSet rs=pst.executeQuery();
				
				while(rs.next()){
					MpFiscalizadorArea obj=new MpFiscalizadorArea(); 
					
					obj.setUnidad_id(rs.getInt("unidad_id"));
					obj.setDescripcion(rs.getString("descripcion"));
					
					lista.add(obj);
				}
			}catch(Exception e){
				throw(e);
			}
			return lista;
		}
		
		public List<MpFiscalizadorDto> getAllInspectoresById(Integer inspeId) throws Exception {
			List<MpFiscalizadorDto> list = new ArrayList<MpFiscalizadorDto>();

			try {
				String SQL = new String("stp_getAllFiscalizadorInspeccionById ?");

				PreparedStatement pst = connect().prepareStatement(SQL.toString());
				pst.setInt(1,inspeId);
				
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {

					MpFiscalizadorDto obj = new MpFiscalizadorDto();
					
					
					obj.setIdfiscalizador(rs.getInt("inspector_id"));
					obj.setCodigo(rs.getString("codigo"));
					obj.setNombresApellidos(rs.getString("nombre_inspector"));
					obj.setDni(rs.getString("nro_dni"));
					obj.setDireccion(rs.getString("nombre_direccion"));
					obj.setTermninal(rs.getString("unidad"));
					obj.setFini(rs.getDate("fecha_inicio"));
					obj.setFfin(rs.getDate("fecha_fin"));
					obj.setCelular(rs.getString("nro_celular"));
					obj.setUnidad_id (rs.getInt("unidad_id"));

					list.add(obj);

				}
			} catch (Exception e) {
				throw (e);
			}
			if(list!=null&&list.size()>0){
				return list;
			}else
			return null;
			//return list;
		}
		
		public ArrayList<MpFiscalizadorDto> findInspector(String nombre) throws Exception {
			 
			ArrayList<MpFiscalizadorDto> list =  new ArrayList<MpFiscalizadorDto>();
			try{
			StringBuffer stBuffer = new StringBuffer();
			stBuffer.append(" SELECT f.inspector_id,f.codigo,f.nombre_inspector,f.nro_dni,f.nombre_direccion ");
			stBuffer.append(" ,f.nro_celular ");
			stBuffer.append(" ,f.fecha_inicio,f.fecha_fin,f.unidad_id ");
			stBuffer.append(" ,a.descripcion as unidad,f.estado,f.usuario_id,f.fecha_registro ");
			stBuffer.append(" ,f.terminal ");
			stBuffer.append(" from ").append(Constante.schemadb).append(".mp_fiscalizador AS f  ");
			stBuffer.append(" inner join ").append(Constante.schemadb).append(".gn_unidad AS a ON f.unidad_id = a.unidad_id "); 
			stBuffer.append(" where f.estado = 1 ");
			
			if(nombre!=null&&nombre.trim().length()>0){
				stBuffer.append("and  f.nombre_inspector like '%").append(nombre).append("%' ");
			}
			
				PreparedStatement pstm = connect().prepareStatement(stBuffer.toString());
				if(nombre!=null&&nombre.trim().length()>0){
					//pstm.setString(1, programa);
					;
				}
				
				
				ResultSet rs = pstm.executeQuery();
				
				while(rs.next()){
					MpFiscalizadorDto obj = new MpFiscalizadorDto();
					obj.setIdfiscalizador(rs.getInt("inspector_id"));
					obj.setCodigo(rs.getString("codigo"));
					obj.setNombresApellidos(rs.getString("nombre_inspector"));
					obj.setDni(rs.getString("nro_dni"));
					obj.setDireccion(rs.getString("nombre_direccion"));
					obj.setTermninal(rs.getString("unidad"));
					obj.setFini(rs.getDate("fecha_inicio"));
					obj.setFfin(rs.getDate("fecha_fin"));
					obj.setCelular(rs.getString("nro_celular"));
					obj.setUnidad_id(rs.getInt("unidad_id"));
					list.add(obj);
					
				}
					
				
		}catch(Exception e){
			throw(e);
		}
			return list;
		}
		
		public int actualizarInspector(String codigo, String nombre,String dni, String telf,String direccion,Date inicio,Date fin,
				Integer unidad, Integer id) throws Exception {
			int result = 0;
			try {
				StringBuffer SQL = new StringBuffer();
				
				
					SQL.append(" UPDATE " + SATParameterFactory.getDBNameScheme()
							+ ".mp_fiscalizador ");
					SQL.append(" set codigo=?, nombre_inspector=?, nro_dni=?, nro_celular=?,");
					SQL.append(" nombre_direccion=?, fecha_inicio=?, fecha_fin=?, unidad_id=? ");
					SQL.append(" where inspector_id = ? ");
				
				PreparedStatement pst = connect().prepareStatement(SQL.toString());
				pst.setString(1,codigo);
				pst.setString(2,nombre);
				pst.setString(3,dni);
				pst.setString(4,telf);
				pst.setString(5,direccion);
				pst.setTimestamp(6, DateUtil.dateToSqlTimestamp(inicio));
				pst.setTimestamp(7, DateUtil.dateToSqlTimestamp(fin));
				pst.setInt(8,unidad);
				pst.setInt(9,id );
				
				result = pst.executeUpdate();
			} catch (Exception e) {
				throw (e);
			}
			return result;
		}
				
		public List<FindInspeccionByIdAsociada> getInspeccionByIdAsociada(Integer inspId) throws Exception {
			List<FindInspeccionByIdAsociada> list =  new ArrayList<FindInspeccionByIdAsociada>();
			try{
				StringBuilder SQL = new StringBuilder("dbo.stp_getRequerimientoInspeccionByIdAsociada ?");
				
				PreparedStatement pst = connect().prepareStatement(SQL.toString());
				pst.setInt(1, inspId);
				ResultSet rs = pst.executeQuery();
				
				while(rs.next()){
					
					FindInspeccionByIdAsociada obj = new FindInspeccionByIdAsociada();
					
					
					obj.setInspeccionId(rs.getInt("inspeccion_id"));
					obj.setTipoDocumentoId(rs.getInt("tipo_documento_id"));
					obj.setPersonaId(rs.getInt("persona_id"));
					obj.setInspectorId(rs.getInt("inspector_id"));
					obj.setFechaInspeccion(rs.getDate("fecha_inspeccion"));
					obj.setInspectorIdAr(rs.getInt("inspector_id_ar"));
					obj.setFechaInspeccionAr(rs.getDate("fecha_inspeccion_ar"));
					obj.setTipoDocumentoIdResultado(rs.getInt("tipo_documento_id_resultado"));
				
					
					list.add(obj);
				}
			}catch(Exception e){
				throw(e);
			}
			return list;
		}
		/*
		 * VEHICULAR
		 */
		
		public List<FindCcLote> getAllLotes() throws Exception {
			List<FindCcLote> list = new ArrayList<FindCcLote>();

			try {
				String SQL = new String("stp_rv_obtener_lote_vehicular");

				PreparedStatement pst = connect().prepareStatement(SQL.toString());
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {

					FindCcLote obj = new FindCcLote();
					
					obj.setLoteId(rs.getInt("lote_id"));
					obj.setTipoLoteId(rs.getInt("tipo_lote_id"));
					obj.setAnnoLote(rs.getInt("anno_lote"));
					obj.setDescripcionLote(rs.getString("descripcion_lote"));
					obj.setFechaRegistro(rs.getTimestamp("fecha_registro"));
					obj.setNombreEjecutor(rs.getString("nombre_usuario"));

					list.add(obj);

				}
			} catch (Exception e) {
				throw (e);
			}
			return list;
		}
		
		public List<RvOmisosVehicular> getDetalleOmiso(int omisoId) throws Exception{
			List<RvOmisosVehicular> list = new ArrayList<RvOmisosVehicular>();
			try{
				String SQL = new String("stp_rv_obtener_omiso_vehicular ?");

				PreparedStatement pst = connect().prepareStatement(SQL.toString());
				pst.setInt(1, omisoId);
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {
					RvOmisosVehicular obj = new RvOmisosVehicular();					
					obj.setVehicularOmisosId(rs.getInt("vehicular_omisos_id"));
					obj.setLoteId(rs.getInt("lote_id"));
					obj.setCodigo(rs.getInt("codigo"));
					obj.setPersonaId(rs.getInt("persona_id"));
					obj.setPropietario(rs.getString("propietario"));
					obj.setPlaca(rs.getString("placa"));
					obj.setPlacaAntigua(rs.getString("placa_antigua"));
					obj.setMarcaDescripcion(rs.getString("marca_descripcion"));
					obj.setModeloDescripcion(rs.getString("modelo_descripcion"));
					obj.setAnnoAfectacion( rs.getInt("anno_afectacion"));
					obj.setAnnoFabricacion(rs.getInt("anno_fabricacion"));
					obj.setDjvehicularNro (rs.getString("djvehicular_nro"));
					obj.setEstadoDescripcion(rs.getString("estado_descripcion"));
					obj.setFechaInscripcion(rs.getTimestamp("fecha_inscripcion"));
					obj.setEstado(rs.getString("estado"));
					obj.setGlosa(rs.getString("glosa"));
					list.add(obj);

				}												
			}catch (Exception e) {
				throw (e);
			}
			return list;			
		};	
		
		public List<RvOmisosVehicular> getAllDetalleLotes(int loteId) throws Exception {
			List<RvOmisosVehicular> list = new ArrayList<RvOmisosVehicular>();

			try {
				String SQL = new String("stp_rv_obtener_detalle_lote_vehicular ?");

				PreparedStatement pst = connect().prepareStatement(SQL.toString());
				pst.setInt(1, loteId);
			
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {

					RvOmisosVehicular obj = new RvOmisosVehicular();
					
					obj.setVehicularOmisosId(rs.getInt("vehicular_omisos_id"));
					obj.setLoteId(rs.getInt("lote_id"));
					obj.setCodigo(rs.getInt("codigo"));
					obj.setPersonaId(rs.getInt("persona_id"));
					obj.setPropietario(rs.getString("propietario"));
					obj.setPlaca(rs.getString("placa"));
					obj.setPlacaAntigua(rs.getString("placa_antigua"));
					obj.setMarcaDescripcion(rs.getString("marca_descripcion"));
					obj.setModeloDescripcion(rs.getString("modelo_descripcion"));
					obj.setAnnoAfectacion( rs.getInt("anno_afectacion"));
					obj.setAnnoFabricacion(rs.getInt("anno_fabricacion"));
					obj.setDjvehicularNro (rs.getString("djvehicular_nro"));
					obj.setEstadoDescripcion(rs.getString("estado_descripcion"));
					obj.setEstado(rs.getString("estado"));
					obj.setGlosa(rs.getString("glosa"));
					obj.setFechaInscripcion(rs.getTimestamp("fecha_inscripcion"));

					list.add(obj);

				}
			} catch (Exception e) {
				throw (e);
			}
			return list;
		}
		
		public List<RpFiscalizacionPrograma> getAllTipoProgramaVehicular()throws Exception{
			List<RpFiscalizacionPrograma> lista=new LinkedList<RpFiscalizacionPrograma>();
			try{
				
				String SQL = new String("select programa_id,nombre_programa from rp_fiscalizacion_programa where estado=1 and concepto_id=2");

				
				PreparedStatement pst=connect().prepareStatement(SQL.toString());
				ResultSet rs=pst.executeQuery();
				
				while(rs.next()){
					RpFiscalizacionPrograma obj=new RpFiscalizacionPrograma(); 
					
					obj.setProgramaId(rs.getInt("programa_id"));
					obj.setNombrePrograma(rs.getString("nombre_programa"));
					
					lista.add(obj);
				}
			}catch(Exception e){
				throw(e);
			}
			return lista;
		}
		
		public int guardarRequerimientoVehicular (int loteId,int programaId,int usuarioId,String terminal) throws Exception {

			int salida = 0;
			try {
				CallableStatement cs = connect().prepareCall(
						"{call dbo.stp_rv_genera_requerimiento_inspeccion(?,?,?,?)}");
				
				cs.setInt(1, loteId);
				cs.setInt(2, programaId);
				cs.setInt(3, usuarioId);
				cs.setString(4, terminal);
				
				cs.execute();
				
			} catch (Exception ex) {
				System.out.println("ERROR: " + ex);
			}
			return salida;
		}
		
		public List<RvOmisosDetalleVehicular> getAllDetalleOmisosVehicular(int inspeccionId) throws Exception{
			List<RvOmisosDetalleVehicular> list = new ArrayList<RvOmisosDetalleVehicular>();
			try {
				String SQL = new String("stp_rv_obtener_detalle_omisos_vehicular ?");

				PreparedStatement pst = connect().prepareStatement(SQL.toString());
				pst.setInt(1,inspeccionId);
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {

					RvOmisosDetalleVehicular obj = new RvOmisosDetalleVehicular();					
					obj.setVehicularOmisosDetalleId(rs.getInt("vehicular_omisos_detalle_id"));
					obj.setRequerimientoId(rs.getInt("requerimiento_id"));
					obj.setAnnoDeterminacion(rs.getInt("anno_determinacion"));
					obj.setTasa(rs.getBigDecimal("tasa"));					
					obj.setBaseImponible(rs.getBigDecimal("base_imponible"));				
					obj.setBaseExonerada(rs.getBigDecimal("base_exonerada"));
					obj.setBaseAfecta(rs.getBigDecimal("base_afecta"));
					obj.setImpuesto(rs.getBigDecimal("impuesto"));
					obj.setMarcaId(rs.getInt("marca_vehiculo_id"));
					obj.setCategoriaId(rs.getInt("categoria_vehiculo_id"));					
					obj.setModeloId(rs.getInt("modelo_vehiculo_id"));
					obj.setEstado(rs.getString("estado"));
					obj.setUsuarioId(rs.getInt("usuario_id"));
					obj.setFechaRegistro(rs.getTimestamp("fecha_registro"));
					obj.setTerminal(rs.getString("terminal"));	
					obj.setDescripcionCategoria(rs.getString("descripcion_categoria"));
					obj.setDescripcionMarca(rs.getString("descripcion_marca"));
					obj.setDescripcionModelo(rs.getString("descripcion_modelo"));					
					list.add(obj);
				}
			} catch (Exception e) {
				throw (e);
			}
			return list;								
		};
		
//		public List<FindInspeccionHistorial> getAllInspeccionesLoteVehicular(int loteId) throws Exception{
//			List<FindInspeccionHistorial> list = new ArrayList<FindInspeccionHistorial>();
//
//			try {
//				String SQL = new String("stp_rv_obtener_requerimiento_inspeccion_lote ?");
//
//				PreparedStatement pst = connect().prepareStatement(SQL.toString());
//				pst.setInt(1,loteId);
//				ResultSet rs = pst.executeQuery();
//				while (rs.next()) {
//
//					FindInspeccionHistorial obj = new FindInspeccionHistorial();
//					
//					
//					obj.setInspeccionId(rs.getInt("requerimiento_id"));
//					obj.setTipoDocumentoId(rs.getInt("tipo_documento_id"));
//					obj.setTipoDocumentoNombre(rs.getString("tipo_documento_nombre"));
//					obj.setNroRequerimiento(rs.getString("nro_requerimiento"));
//					obj.setPersonaId(rs.getInt("persona_id"));
//					obj.setApellidosNombres(rs.getString("apellidos_nombres"));
//					//obj.setPersonaDomicilioFiscal(rs.getString("domicilio_persona"));
//					//obj.setPersonaDni(rs.getString("dni_persona"));
//					obj.setProgramaId(rs.getInt("programa_id"));
//					obj.setProgramaNombre(rs.getString("nombre_programa"));
//					obj.setEstado(rs.getInt("estado"));
//					obj.setFechaEmision(rs.getDate("fecha_emision"));//
//					obj.setFechaInspeccion(rs.getDate("fecha_inspeccion"));
//					obj.setFechaNotificacion(rs.getDate("fecha_notificacion"));//
//					obj.setNombreUsuario(rs.getString("nombre_usuario"));//
//					obj.setFechaRegistro(rs.getDate("fecha_registro"));
//					obj.setAnioInspeccion(rs.getString("anio_inspeccion"));
//					obj.setDistritoId(rs.getInt("distrito_id"));
//					obj.setDistritoNombre(rs.getString("nombre_distrito"));
//					obj.setPersonaDomicilioFiscal(rs.getString("direccion_completa"));
//					obj.setAnioInspeccion(rs.getString("anio_inspeccion"));
//					obj.setPlaca(rs.getString("placa"));
//					obj.setPlaca_anterior(rs.getString("placa_antigua"));
//					list.add(obj);
//
//				}
//			} catch (Exception e) {
//				throw (e);
//			}
//			return list;
//		};			
		
		public List<FindInspeccionHistorial> getAllInspeccionesVehicular(int loteId) throws Exception {
			List<FindInspeccionHistorial> list = new ArrayList<FindInspeccionHistorial>();

			try {
				String SQL = new String("stp_rv_obtener_requerimiento_inspeccion ?");

				PreparedStatement pst = connect().prepareStatement(SQL.toString());
				pst.setInt(1,loteId);
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {

					FindInspeccionHistorial obj = new FindInspeccionHistorial();
					
					
					obj.setInspeccionId(rs.getInt("requerimiento_id"));
					obj.setTipoDocumentoId(rs.getInt("tipo_documento_id"));
					obj.setTipoDocumentoNombre(rs.getString("tipo_documento_nombre"));
					obj.setNroRequerimiento(rs.getString("nro_requerimiento"));
					obj.setPersonaId(rs.getInt("persona_id"));
					obj.setApellidosNombres(rs.getString("apellidos_nombres"));
					//obj.setPersonaDomicilioFiscal(rs.getString("domicilio_persona"));
					//obj.setPersonaDni(rs.getString("dni_persona"));
					obj.setProgramaId(rs.getInt("programa_id"));
					obj.setProgramaNombre(rs.getString("nombre_programa"));
					obj.setEstado(rs.getInt("estado"));
					obj.setFechaEmision(rs.getDate("fecha_emision"));//
					obj.setFechaInspeccion(rs.getDate("fecha_inspeccion"));
					obj.setFechaNotificacion(rs.getDate("fecha_notificacion"));//
					obj.setNombreUsuario(rs.getString("nombre_usuario"));//
					obj.setFechaRegistro(rs.getDate("fecha_registro"));
					obj.setAnioInspeccion(rs.getString("anio_inspeccion"));
					obj.setDistritoId(rs.getInt("distrito_id"));
					obj.setDistritoNombre(rs.getString("nombre_distrito"));
					obj.setPlaca(rs.getString("placa"));
					obj.setPlaca_anterior(rs.getString("placa_antigua"));
					obj.setEstadoDescripcion(rs.getString("estado_descripcion"));
					
					list.add(obj);

				}
			} catch (Exception e) {
				throw (e);
			}
			return list;
		}
				/**
				 * Generacion del Impuesto Vehicular::
				 */
		
		public List<RvOmisosVehicular> getAllDetalleLotes2(int loteId,int estado) throws Exception {
			List<RvOmisosVehicular> list = new ArrayList<RvOmisosVehicular>();

			try {
				String SQL = new String("stp_rv_obtener_detalle_lote_requerimiento ?,?");

				PreparedStatement pst = connect().prepareStatement(SQL.toString());
				pst.setInt(1, loteId);
				pst.setInt(2, estado);
			
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {

					RvOmisosVehicular obj = new RvOmisosVehicular();
					
					obj.setVehicularOmisosId(rs.getInt("vehicular_omisos_id"));
					obj.setLoteId(rs.getInt("lote_id"));
					obj.setCodigo(rs.getInt("codigo"));
					obj.setPersonaId(rs.getInt("persona_id"));
					obj.setPropietario(rs.getString("propietario"));
					obj.setPlaca(rs.getString("placa"));
					obj.setPlacaAntigua(rs.getString("placa_antigua"));
					obj.setMarcaDescripcion(rs.getString("marca_descripcion"));
					obj.setModeloDescripcion(rs.getString("modelo_descripcion"));
					obj.setAnnoAfectacion( rs.getInt("anno_afectacion"));
					obj.setAnnoFabricacion(rs.getInt("anno_fabricacion"));
					obj.setDjvehicularNro (rs.getString("djvehicular_nro"));
					obj.setEstadoDescripcion(rs.getString("estado_descripcion"));
					obj.setEstado(rs.getString("estado"));
					obj.setGlosa(rs.getString("glosa"));
					obj.setFechaInscripcion(rs.getTimestamp("fecha_inscripcion"));
					obj.setCarroceriaId(rs.getInt("requerimiento_id"));

					list.add(obj);

				}
			} catch (Exception e) {
				throw (e);
			}
			return list;
		}
		
		public DatosNecesariosDeterDTO getDatosNecesariosDeterminar(int omisoId,int loteId) throws SisatException {
			try {
				
				String SQL = new String("stp_rv_obtener_datos_determinar ?,?");

				PreparedStatement pst = connect().prepareStatement(SQL.toString());
				pst.setInt(1, omisoId);
				pst.setInt(2, loteId);
				
				ResultSet rs = pst.executeQuery();
				DatosNecesariosDeterDTO datos = null;
				while (rs.next()) {
					datos = new DatosNecesariosDeterDTO();
					datos.setCategVehicId(rs.getInt("categoria_vehiculo_id"));
					datos.setMarcaVehicId(rs.getInt("marca_vehiculo_id"));
					datos.setModeloVehicId(rs.getInt("modelo_vehiculo_id"));
					datos.setAnioIniAfec(rs.getInt("anno_ini_afectacion"));
					datos.setAnioFinAfec(rs.getInt("anno_fin_afectacion"));
					datos.setAnioAfec(rs.getInt("anno_afectacion"));
					datos.setAnioFabric(rs.getInt("anno_fabricacion"));
					datos.setValorAdquiSoles(rs.getBigDecimal("val_adq_soles"));
					datos.setValorAdquiOtraMoneda(rs
							.getBigDecimal("val_adq_otra_moneda"));
					datos.setPorcentajePropiedad(rs.getBigDecimal("porc_propiedad"));
					datos.setPersonaId(rs.getInt("persona_id"));
					datos.setTipoMonedaId(rs.getInt("tipo_moneda_id"));
					Object djPrevio = rs.getObject("djvehicular_previo_id");
					if (djPrevio != null) {
						datos.setDjPrevioId(Integer.parseInt(djPrevio.toString()));
					}
					datos.setAnioIniAfecContrib(rs.getInt("anno_ini_afec_contrib"));
					datos.setMotivoDeclaracionId(rs.getInt("rv_motivo_declaracion_id"));
					datos.setVehiculoId(rs.getInt("vehiculo_id"));
					
					datos.setAnhoAdquision(rs.getInt("anho_adquisicion"));
				}
				return datos;
			} catch (Exception ex) {
				throw new SisatException(ex.getMessage());
			}		
		}
		
		public DtTasaVehicular getTasaVehicular(Integer anioAfec) {
			try {
				return find(anioAfec, DtTasaVehicular.class);
			} catch (Exception ex) {
				// TODO : Controller Exception
				System.out.println("ERROR: " + ex);
			}
			return null;
		}
		
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
		
		public BigDecimal getValorMEF(int categoriaId,int marcaVehiculoId, int modeloVehiculoId, int periodoAfectacion,
				int periodoFabricacion) {
			try {
				StringBuilder SQL = new StringBuilder(
						"SELECT vr.valor_referencial FROM ").append(Constante.schemadb).append(".dt_valor_referencial vr");
				SQL.append(" WHERE ");
				SQL.append(" vr.categoria_vehiculo_id=").append(categoriaId);
				SQL.append(" AND vr.marca_vehiculo_id=").append(marcaVehiculoId);
				SQL.append(" AND vr.modelo_vehiculo_id=").append(modeloVehiculoId);
				SQL.append(" AND vr.periodo_afectacion=" ).append( periodoAfectacion);
				SQL.append(" AND vr.periodo_fabricacion=" ).append( periodoFabricacion);

				PreparedStatement pst = connect().prepareStatement(SQL.toString());
				ResultSet rs = pst.executeQuery();

				BigDecimal valorAjuste = null;
				while (rs.next()) {
					valorAjuste = rs.getBigDecimal("valor_referencial");
				}
				return valorAjuste;
			} catch (Exception ex) {
				
				System.out.println("ERROR: " + ex);
			}
			return null;
		}
		
		
		/**
		 * Obtiene el monto del anio con menor antiguedad de los valores que publica
		 * el MEF anualmente para cagegoria,marca y modelo de vehículo.
		 * 
		 * @param modeloVehiculoId
		 *            Modelo de vehículo
		 * @param periodoAfectacion
		 *            Año de afectación
		 * @return
		 */
		public BigDecimal getMontoAnioMenorAntig(int categoriaId, int  marcaVehiculoId, int modeloVehiculoId,
				int periodoAfectacion) {
			try {
				StringBuilder SQL = new StringBuilder("SELECT vr.valor_referencial FROM ").append(Constante.schemadb).append(".dt_valor_referencial vr");
				SQL.append(" WHERE ");
				SQL.append(" vr.categoria_vehiculo_id =").append(categoriaId);
				SQL.append(" AND vr.marca_vehiculo_id =").append(marcaVehiculoId);
				SQL.append(" AND vr.modelo_vehiculo_id =" ).append( modeloVehiculoId);
				SQL.append(" AND vr.periodo_afectacion =" ).append( periodoAfectacion);
				SQL.append(" AND vr.periodo_fabricacion =" ).append( (periodoAfectacion - 1));

				PreparedStatement pst = connect().prepareStatement(SQL.toString());
				ResultSet rs = pst.executeQuery();

				BigDecimal monto = null;
				while (rs.next()) {
					monto = rs.getBigDecimal("valor_referencial");
				}
//				if(monto==null){
//					throw new SisatException("No se puede calcular porque no existe valor ajustado."+
//							(new StringBuffer()).append("Verificar valor referencial para categoría ").append(categoriaId)
//							.append(" , marca ").append(marcaVehiculoId)
//							.append(" y modelo ").append(modeloVehiculoId)
//							.append(" para el vehículo en el año de afectacion ").append(periodoAfectacion)
//							.append(" y año fabricacion ").append(periodoAfectacion - 1).toString());
//				}
				return monto;
			}
//			catch(SisatException ex){
//				throw ex;
//			}
			catch (Exception ex) {
				// TODO : Controller Exception
				System.out.println("ERROR: " + ex);
				
			}
			return null;
		}

		public int guardarImpuestoVehicular (RvOmisosDetalleVehicular deter) throws Exception {

			int salida = 0;
			try {
				CallableStatement cs = connect().prepareCall(
						"{call dbo.stp_rv_genera_impuesto_inspeccion(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				
				cs.setInt(1, deter.getRequerimientoId());
				cs.setInt(2, deter.getAnnoDeterminacion());
				cs.setBigDecimal(3, deter.getTasa());
				cs.setBigDecimal(4, deter.getBaseImponible());
				cs.setBigDecimal(5, deter.getBaseExonerada());
				cs.setBigDecimal(6, deter.getBaseAfecta());
				cs.setBigDecimal(7, deter.getImpuesto());
				cs.setInt(8, deter.getMarcaId());
				cs.setInt(9, deter.getCategoriaId());
				cs.setInt(10, deter.getModeloId());
				cs.setString(11, deter.getEstado());
				cs.setInt(12, deter.getUsuarioId());
				cs.setString(13, deter.getTerminal());
				
				cs.execute();
				
			} catch (Exception ex) {
				System.out.println("ERROR: " + ex);
			}
			return salida;
		}
		
		public int actualizaEstadoRequerimiento (Integer requerimientoId, Integer omisoId,Integer estado) throws Exception {

			int salida = 0;
			try {
				CallableStatement cs = connect().prepareCall("{call dbo.stp_rv_actualiza_estado_inspeccion(?,?,?)}");
				
				cs.setInt(1, requerimientoId);
				cs.setInt(2, omisoId);
				cs.setInt(3, estado);
				cs.execute();
				
			} catch (Exception ex) {
				System.out.println("ERROR: " + ex);
			}
			return salida;
		}
		
		/**
		 * Generacion de la Declaración Jurada::
		 */
		
		public DatosNecesariosDeclaracionDTO getDatosNecesariosDeclaracion(int omisoId,int loteId) throws SisatException {
			try {
				
				String SQL = new String("stp_rv_obtener_datos_declaracion ?,?");

				PreparedStatement pst = connect().prepareStatement(SQL.toString());
				pst.setInt(1, omisoId);
				pst.setInt(2, loteId);
				
				ResultSet rs = pst.executeQuery();
				DatosNecesariosDeclaracionDTO datosDj = null;
				while (rs.next()) {
					datosDj = new DatosNecesariosDeclaracionDTO();
					datosDj.setCondicionVehiculoId(rs.getInt("condicion_vehiculo_id"));
					datosDj.setTipoTransmisionId(rs.getInt("tipo_transmision_id"));
					datosDj.setTipoTraccionId(rs.getInt("tipo_traccion_id"));
					datosDj.setMarcaVehicId(rs.getInt("marca_vehiculo_id"));
					datosDj.setCategVehicId(rs.getInt("categoria_vehiculo_id"));
					datosDj.setModeloVehicId(rs.getInt("modelo_vehiculo_id"));
					datosDj.setTipoMotorId(rs.getInt("tipo_motor_id"));
					datosDj.setPlaca(rs.getString("placa"));
					datosDj.setNumMotor(rs.getString("num_motor"));
					datosDj.setAnioFabric(rs.getInt("anno_fabricacion"));
					datosDj.setFechaInsRegistros(rs.getTimestamp("fecha_inscripcion"));
					datosDj.setNumCilindros(rs.getInt("numero_cilindros"));
					datosDj.setPesoBruto(rs.getInt("peso_bruto"));
					datosDj.setTipoCarroceriaId(rs.getInt("carroceria_id"));
					datosDj.setClaseVehiculoId(rs.getInt("clase_vehiculo_id"));
					datosDj.setTipoMonedaId(rs.getInt("tipo_moneda_id"));
					datosDj.setTipoPropiedadId(rs.getInt("tipo_propiedad_id"));
					datosDj.setPersonaId(rs.getInt("persona_id"));
					datosDj.setTipoAdquisicionId(rs.getInt("tipo_adquisicion_id"));
					datosDj.setMotivoDeclaracionId(rs.getInt("rv_motivo_declaracion_id"));
					datosDj.setNotariaId(rs.getInt("notaria_id"));
					datosDj.setAnioAfec(rs.getInt("anno_ini_afectacion"));
					datosDj.setAnioFinAfec(rs.getInt("anno_fin_afectacion"));
					datosDj.setAnioAfec(rs.getInt("anno_afectacion"));
					datosDj.setNumTarjetaPropiedad(rs.getString("num_tarjeta_propiedad"));
					datosDj.setFechaAdquisicion(rs.getTimestamp("fecha_adquisicion"));
					datosDj.setValorAdquiSoles(rs.getBigDecimal("val_adq_soles"));
					datosDj.setPorcentajePropiedad(rs.getBigDecimal("porc_propiedad"));
					datosDj.setEstado(rs.getString("estado"));
					datosDj.setGlosa(rs.getString("glosa"));
				}
				return datosDj;
			} catch (Exception ex) {
				throw new SisatException(ex.getMessage());
			}		
		}
		
		public Integer guardarVehiculo(RvVehiculo vehiculo) throws SisatException {

			Integer vehiculoId = null;
			try {
				vehiculoId = ObtenerCorrelativoTabla("rv_vehiculo", 1);

				if (vehiculoId.intValue() == 0) {
					vehiculoId = null;
				}

				StringBuilder SQL = new StringBuilder("INSERT INTO "
						+ SATParameterFactory.getDBNameScheme() + ".rv_vehiculo(");
				SQL.append("vehiculo_id,condicion_vehiculo_id,tipo_transmision_id,tipo_traccion_id,marca_vehiculo_id,");
				SQL.append("categoria_vehiculo_id,modelo_vehiculo_id,tipo_motor_id,placa,num_motor,");
				SQL.append("anno_fabricacion,fecha_ins_registros,num_cilindros,peso_bruto,usuario_id,");
				SQL.append("fecha_actualizacion,fecha_registro,terminal,tipo_carroceria_id,clase_vehiculo_id) ");
				SQL.append("VALUES(");
				SQL.append(vehiculoId + ",");
				SQL.append(vehiculo.getCondicionVehiculoId() + ",");
				SQL.append(vehiculo.getTipoTransmisionId() == 0 ? "null,"
						: vehiculo.getTipoTransmisionId() + ",");
				SQL.append(vehiculo.getTipoTraccionId() == 0 ? "null," : vehiculo
						.getTipoTraccionId() + ",");
				SQL.append(vehiculo.getMarcaVehiculoId() + ",");
				SQL.append(vehiculo.getCategoriaVehiculoId() + ",");
				SQL.append(vehiculo.getModeloVehiculoId() + ",");
				SQL.append(vehiculo.getTipoMotorId() == 0 ? "null,'" : vehiculo
						.getTipoMotorId() + ",'");
				SQL.append(vehiculo.getPlaca() + "','");
				SQL.append(vehiculo.getNumMotor() + "',");
				SQL.append(vehiculo.getAnnoFabricacion() + ",?,");
				SQL.append(vehiculo.getNumCilindros() == 0 ? "null," : vehiculo
						.getNumCilindros() + ",");
				SQL.append(vehiculo.getPesoBruto() == 0 ? "null," : vehiculo
						.getPesoBruto() + ",");
				SQL.append(vehiculo.getUsuarioId() + ",GETDATE(),GETDATE(),'");
				SQL.append(vehiculo.getTerminal() + "',");
				SQL.append(vehiculo.getTipoCarroceriaId() == 0 ? "null," : vehiculo
						.getTipoCarroceriaId() + ",");
				SQL.append(vehiculo.getClaseVehiculoId() + ")");

				PreparedStatement pst = connect().prepareStatement(SQL.toString());

				pst.setDate(1, new java.sql.Date(vehiculo.getFechaInsRegistros()
						.getTime()));
				pst.executeUpdate();

			} catch (Exception ex) {
				vehiculoId = null;
				throw new SisatException(ex.getMessage());
			}
			return vehiculoId;
		}
		
		public RvDjvehicular guardarDJVehicular(RvDjvehicular dj) throws SisatException {
			int djId = -1;
			int result = -1;
			String nroDoc = null;
			try {
				djId = ObtenerCorrelativoTabla("rv_djvehicular", 1);
				dj.setDjvehicularId(djId);
				nroDoc = ObtenerCorrelativoDocumento("rv_djvehicular",
						"djvehicular_nro");
				dj.setDjvehicularNro(nroDoc);

				StringBuilder SQL = new StringBuilder("INSERT INTO "
						+ SATParameterFactory.getDBNameScheme()
						+ ".rv_djvehicular(");
				SQL.append("djvehicular_id,tipo_traccion_id,tipo_transmision_id,tipo_motor_id,tipo_moneda_id,tipo_propiedad_id,");
				SQL.append("marca_vehiculo_id,categoria_vehiculo_id,modelo_vehiculo_id,num_motor,anno_fabricacion,fecha_ins_registros,");
				SQL.append("num_cilindros,peso_bruto,tipo_carroceria_id,clase_vehiculo_id,");
				SQL.append("persona_id,tipo_adquisicion_id,rv_motivo_declaracion_id,condicion_vehiculo_id,notaria_id,vehiculo_id,fecha_declaracion,");
				SQL.append("anno_ini_afectacion,anno_fin_afectacion,anno_afectacion,num_tarjeta_propiedad,");
				SQL.append("fecha_adquisicion,val_adq_soles,val_adq_otra_moneda,tipo_cambio_id,porc_propiedad,anno_declaracion,usuario_id,fecha_actualizacion,");
				SQL.append("estado,fecha_registro,terminal,flag_dj_anno, fecha_descargo, glosa, djvehicular_nro,requerimiento_id, fiscalizado, fisca_aceptada, fisca_cerrada) ");
				SQL.append("VALUES(");
				SQL.append(djId + ",");
				SQL.append(dj.getTipoTraccionId() == 0 ? "null," : dj.getTipoTraccionId() + ",");
				SQL.append(dj.getTipoTransmisionId() == 0 ? "null," : dj.getTipoTransmisionId() + ",");
				SQL.append(dj.getTipoMotorId() == 0 ? "null," : dj.getTipoMotorId()	+ ",");
				SQL.append(dj.getTipoMonedaId() + ",");
				SQL.append(dj.getTipoPropiedadId() + ",");
				SQL.append(dj.getMarcaVehiculoId() + ",");
				SQL.append(dj.getCategoriaVehiculoId() + ",");
				SQL.append(dj.getModeloVehiculoId() + ",'");
				SQL.append(dj.getNumMotor() + "',");
				SQL.append(dj.getAnnoFabricacion() + ",?,");
				SQL.append(dj.getNumCilindros() == 0 ? "null," : dj.getNumCilindros() + ",");
				SQL.append(dj.getPesoBruto() == 0 ? "null," : dj.getPesoBruto()	+ ",");
				SQL.append(dj.getTipoCarroceriaId() == 0 ? "null," : dj.getTipoCarroceriaId() + ",");
				SQL.append(dj.getClaseVehiculoId() == 0 ? "null," : dj.getClaseVehiculoId() + ",");
				SQL.append(dj.getPersonaId() + ",");
				SQL.append(dj.getTipoAdquisicionId() + ",");
				SQL.append(dj.getRvMotivoDeclaracionId() + ",");
				SQL.append(dj.getCondicionVehiculoId() + ",");
				SQL.append(dj.getNotariaId() + ",");
				SQL.append(dj.getVehiculoId() + ",?,");
				SQL.append(dj.getAnnoIniAfectacion() + ",");
				SQL.append(dj.getAnnoFinAfectacion() + ",");
				SQL.append(dj.getAnnoAfectacion() == 0 ? "null,'" : dj.getAnnoAfectacion() + ",'");
				SQL.append(dj.getNumTarjetaPropiedad() + "', ? ,");
				SQL.append(dj.getValAdqSoles() + ",");
				SQL.append(dj.getValAdqOtraMoneda() == null ? "null,null," : dj.getValAdqOtraMoneda() + "," + dj.getTipoCambioId() + ",");
				SQL.append(dj.getPorcPropiedad() + ",");
				SQL.append(dj.getAnnoDeclaracion() + ",");
				SQL.append(dj.getUsuarioId() + ",GETDATE(),'");
				SQL.append(dj.getEstado() + "',GETDATE(),'");
				SQL.append(dj.getTerminal() + "', '");
				SQL.append(dj.getFlagDjAnno()+"', ? ,");
				if(dj.getGlosa() != null){
					SQL.append(" '" );		
					SQL.append(dj.getGlosa()+"'," );
				}else{
					SQL.append(" NULL," );				
				}
				SQL.append("'".concat(nroDoc).concat("',"));
				SQL.append(dj.getRequerimientoId()+ ",");
				SQL.append(dj.getFiscalizado()+ ",");
				SQL.append(dj.getFiscaAceptada()+ ",");
				SQL.append(dj.getFiscaCerrada()+ ")");
				
					
				PreparedStatement pst = connect().prepareStatement(SQL.toString());
				pst.setDate(1, new java.sql.Date(dj.getFechaInsRegistros().getTime()));
				pst.setDate(2, new java.sql.Date(dj.getFechaDeclaracion().getTime()));
				pst.setDate(3, new java.sql.Date(dj.getFechaAdquisicion().getTime()));
				
				if(dj.getFechaDescargo() != null){
					pst.setDate(4, new java.sql.Date(dj.getFechaDescargo().getTime()));
				}else{
					pst.setDate(4, null);
				}
				
				result = pst.executeUpdate();
			} catch (Exception ex) {
				throw new SisatException(ex.getMessage());
			}

			if (result != 1) {
				dj = null;
			}

			return dj;
		}
		
		public void guardarDocAnexosDjVehicular(RvSustentoVehicular sv) {
			try {
				
					int sustenId = ObtenerCorrelativoTabla("rv_sustento_vehicular",
							1);
					StringBuilder SQL = new StringBuilder("INSERT INTO "
							+ SATParameterFactory.getDBNameScheme()
							+ ".rv_sustento_vehicular(");
					SQL.append("djvehicular_id,sustento_id,doc_sustentatorio_id,nro_documento,referencia,content_id,fecha_registro,usuario_id,terminal) ");
					SQL.append("VALUES(");
					SQL.append(sv.getId().getDjvehicularId() + "," + sustenId + ","
							+ sv.getDocSustentatorioId() + ",'" + sv.getNroDocumento()
							+ "',");
					if (sv.getReferencia() != null && sv.getContentId() != null) {
						SQL.append("'" + sv.getReferencia() + "',"
								+ sv.getContentId() + ",'");
					} else {
						SQL.append(null + "," + null + ",'");
					}
					SQL.append(sv.getFechaRegistro() + "'," + sv.getUsuarioId()
							+ ",'" + sv.getTerminal() + "')");

					PreparedStatement pst = connect().prepareStatement(
							SQL.toString());
					pst.executeUpdate();
				
			} catch (Exception ex) {
				// Controller exception
				System.out.println("ERROR: " + ex);
			}
		}
		
		public boolean copiarDjvAOtroAnio(int djvId, int anioCrear, int usuarioId,
				String terminal) {
			try {
				CallableStatement cs = connect().prepareCall(
						"{call stp_copiarDjFiscalizadavehicularAOtroAnio(?,?,?,?)}");
				cs.setInt(1, djvId);
				cs.setInt(2, anioCrear);
				cs.setInt(3, usuarioId);
				cs.setString(4, terminal);
				cs.executeUpdate();
				return true;
			} catch (Exception ex) {
				// TODO : Handle exception
				//System.out.println("copiarDjvAOtroAnio: " + ex);
			}
			return false;
		}
		
		public List<FindCcLote> getAllLoteOmiso() throws Exception {
			List<FindCcLote> list = new ArrayList<FindCcLote>();

			try {
				String SQL = new String("stp_rv_obtener_lote_vehicular_notificada");

				PreparedStatement pst = connect().prepareStatement(SQL.toString());
//				pst.setInt(1, programaId);
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {

					FindCcLote obj = new FindCcLote();
					
					
					obj.setLoteId(rs.getInt("lote_id"));
					obj.setAnnoLote(rs.getInt("anno_lote"));
					obj.setDescripcionLote(rs.getString("descripcion_lote"));
					obj.setFechaRegistro(rs.getTimestamp("fecha_registro"));
					obj.setTipoLoteId(rs.getInt("cantidad"));
					obj.setNroDocumentos(rs.getInt("cantidad_con_requeriento"));
					obj.setNroPersonas(rs.getInt("cantidad_notificada"));
					obj.setTipoActoId(rs.getInt("cantidad_actos"));
			

					list.add(obj);

				}
			} catch (Exception e) {
				throw (e);
			}
			return list;
		}
		
		public int actualizarDjOmisaVehicular (Integer loteId,Integer usuarioId,String terminal) throws Exception {
			int salida = 0;
			try {
				CallableStatement cs = connect().prepareCall(
						"{call dbo.stp_rv_genera_dj_inspeccion(?,?,?)}");
				
				cs.setInt(1, loteId);
				cs.setInt(2, usuarioId);
				cs.setString(3, terminal);
				cs.execute();
				
			} catch (Exception ex) {
				System.out.println("ERROR: " + ex);
			}
			return salida;

		}
		
		public List<RvDjvehicular> getAllDjOmiso(Integer loteId) throws Exception {
			List<RvDjvehicular> list = new ArrayList<RvDjvehicular>();

			try {
				String SQL = new String("stp_rv_obtener_dj_omisa_vehicular ?");

				PreparedStatement pst = connect().prepareStatement(SQL.toString());
				pst.setInt(1, loteId);
			
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {

					RvDjvehicular obj = new RvDjvehicular();
					
					obj.setDjvehicularId(rs.getInt("djvehicular_id"));
					obj.setPersonaId(rs.getInt("persona_id"));
					obj.setAnnoAfectacion( rs.getInt("anno_afectacion"));
					obj.setDjvehicularNro (rs.getString("djvehicular_nro"));

					list.add(obj);

				}
			} catch (Exception e) {
				throw (e);
			}
			return list;
		}
		
		//////
		public List<RvClaseVehiculo> getAllClaseVehiculos()throws Exception{
			List<RvClaseVehiculo> lista=new LinkedList<RvClaseVehiculo>();
			try{
				
				//String SQL = new String("select clase_vehiculo_id,descripcion from rv_clase_vehiculo where estado=1");
				String SQL = "SELECT clase_vehiculo_id, descripcion FROM "
						+ SATParameterFactory.getDBNameScheme()
						+ ".rv_clase_vehiculo where estado=1 ORDER BY descripcion ASC";
				
				PreparedStatement pst=connect().prepareStatement(SQL.toString());
				ResultSet rs=pst.executeQuery();
				
				while(rs.next()){
					RvClaseVehiculo obj=new RvClaseVehiculo(); 
					obj.setClaseVehiculoId(rs.getInt("clase_vehiculo_id"));
					obj.setDescripcion(rs.getString("descripcion"));										
					lista.add(obj);
				}
			}catch(Exception e){
				throw(e);
			}
			return lista;
		}	
		public List<RvCategoriaVehiculo> getAllCategoriaVehiculos()throws Exception{
			List<RvCategoriaVehiculo> lista=new LinkedList<RvCategoriaVehiculo>();
			try{
				
				//String SQL = new String("select categoria_vehiculo_id,descripcion from rv_categoria_vehiculo where estado=1");
				String SQL = "SELECT categoria_vehiculo_id, descripcion FROM "
						+ SATParameterFactory.getDBNameScheme()
						+ ".rv_categoria_vehiculo where estado=1 ORDER BY descripcion ASC";
				
				PreparedStatement pst=connect().prepareStatement(SQL.toString());
				ResultSet rs=pst.executeQuery();
				
				while(rs.next()){
					RvCategoriaVehiculo obj=new RvCategoriaVehiculo(); 
					obj.setCategoriaVehiculoId(rs.getInt("categoria_vehiculo_id"));
					obj.setDescripcion(rs.getString("descripcion"));										
					lista.add(obj);
				}
			}catch(Exception e){
				throw(e);
			}
			return lista;
		}	
		public List<RvMarca> getAllMarcaVehiculos()throws Exception{
			List<RvMarca> lista=new LinkedList<RvMarca>();
			try{
				
				String SQL = new String("select marca_vehiculo_id,descripcion from rv_marca where estado=1");				
				PreparedStatement pst=connect().prepareStatement(SQL.toString());
				ResultSet rs=pst.executeQuery();
				
				while(rs.next()){
					RvMarca obj=new RvMarca(); 
					obj.setMarcaVehiculoId(rs.getInt("marca_vehiculo_id"));
					obj.setDescripcion(rs.getString("descripcion"));										
					lista.add(obj);
				}
			}catch(Exception e){
				throw(e);
			}
			return lista;
		}
		public List<RvTipoMotor> getAllMotorVehiculos()throws Exception{
			List<RvTipoMotor> lista=new LinkedList<RvTipoMotor>();
			try{
				
				String SQL = new String("select tipo_motor_id,descripcion from rv_tipo_motor where estado=1");				
				PreparedStatement pst=connect().prepareStatement(SQL.toString());
				ResultSet rs=pst.executeQuery();
				
				while(rs.next()){
					RvTipoMotor obj=new RvTipoMotor(); 					
					obj.setTipoMotorId(rs.getInt("tipo_motor_id"));
					obj.setDescripcion(rs.getString("descripcion"));										
					lista.add(obj);
				}
			}catch(Exception e){
				throw(e);
			}
			return lista;
		}

		
		public List<RvTipoCarroceria> getAllCarroceriaVehiculos()throws Exception{
			List<RvTipoCarroceria> lista=new LinkedList<RvTipoCarroceria>();
			try{
				
				String SQL = new String("select tipo_carroceria_id,descripcion from rv_tipo_carroceria where estado=1");				
				PreparedStatement pst=connect().prepareStatement(SQL.toString());
				ResultSet rs=pst.executeQuery();
				
				while(rs.next()){
					RvTipoCarroceria obj=new RvTipoCarroceria(); 										
					obj.setTipoCarroceriaId(rs.getInt("tipo_carroceria_id"));
					obj.setDescripcion(rs.getString("descripcion"));										
					lista.add(obj);
				}
			}catch(Exception e){
				throw(e);
			}
			return lista;
		}
		
		//////
		/***
		 * Detalle de Imp. Vehicular
		 */
		public List<RvOmisosDetalleVehicular> getAllDetalleVerificacion(int reqId) throws Exception {
			List<RvOmisosDetalleVehicular> lista = new LinkedList<RvOmisosDetalleVehicular>();
			try {
				
				String SQL = new String("stp_rv_obtener_detalle_verificacion ?");

				PreparedStatement pst = connect().prepareStatement(SQL.toString());
				pst.setInt(1, reqId);
				ResultSet rs = pst.executeQuery();

				while (rs.next()) {
					RvOmisosDetalleVehicular obj = new RvOmisosDetalleVehicular();

					obj.setVehicularOmisosDetalleId(rs.getInt("vehicular_omisos_detalle_id"));
					obj.setRequerimientoId(rs.getInt("requerimiento_id"));
					obj.setAnnoDeterminacion(rs.getInt("anno_determinacion"));
					obj.setTasa(rs.getBigDecimal("tasa"));					
					obj.setBaseImponible(rs.getBigDecimal("base_imponible"));				
					obj.setBaseExonerada(rs.getBigDecimal("base_exonerada"));
					obj.setBaseAfecta(rs.getBigDecimal("base_afecta"));
					obj.setImpuesto(rs.getBigDecimal("impuesto"));
					obj.setMarcaId(rs.getInt("marca_vehiculo_id"));
					obj.setCategoriaId(rs.getInt("categoria_vehiculo_id"));					
					obj.setModeloId(rs.getInt("modelo_vehiculo_id"));
					obj.setEstado(rs.getString("estado"));
					obj.setUsuarioId(rs.getInt("usuario_id"));
					obj.setFechaRegistro(rs.getTimestamp("fecha_registro"));
					obj.setTerminal(rs.getString("terminal"));	
					obj.setDescripcionCategoria(rs.getString("descripcion_categoria"));
					obj.setDescripcionMarca(rs.getString("descripcion_marca"));
					obj.setDescripcionModelo(rs.getString("descripcion_modelo"));	

					lista.add(obj);
				}
			} catch (Exception e) {
				throw (e);
			}
			return lista;
		}
		
		public List<RvMarca> findRvMarca(int categoria) throws Exception {
			List<RvMarca> lista = new ArrayList<RvMarca>();
			try {
				StringBuilder SQL = new StringBuilder(
						"SELECT DISTINCT(mo.marca_vehiculo_id), ma.descripcion FROM "
								+ SATParameterFactory.getDBNameScheme()
								+ ".rv_modelo_vehiculo mo ");
				SQL.append("INNER JOIN "
						+ SATParameterFactory.getDBNameScheme()
						+ ".rv_marca ma ON ma.marca_vehiculo_id = mo.marca_vehiculo_id ");
				SQL.append("WHERE mo.estado=1 and ma.estado=1 and mo.categoria_vehiculo_id=" + categoria
						+ " ORDER BY ma.descripcion ASC");

				PreparedStatement pst = connect().prepareStatement(SQL.toString());
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {
					RvMarca obj = new RvMarca();
					obj.setMarcaVehiculoId(rs.getInt("marca_vehiculo_id"));
					obj.setDescripcion(rs.getString("descripcion"));
					lista.add(obj);
				}
			} catch (Exception e) {
				throw (e);
			}
			return lista;
		}
		
		public List<RvModeloVehiculo> getAllRvModeloVehiculo(int marcaVehiculoId,
				int categoriaVehiculoId) throws Exception {
			List<RvModeloVehiculo> lista = new ArrayList<RvModeloVehiculo>();
			try {
				String SQL = "SELECT marca_vehiculo_id, categoria_vehiculo_id, modelo_vehiculo_id, descripcion "
						+ "FROM "
						+ SATParameterFactory.getDBNameScheme()
						+ ".rv_modelo_vehiculo "
						+ "WHERE marca_vehiculo_id="
						+ marcaVehiculoId
						+ "AND estado=1 AND categoria_vehiculo_id="
						+ categoriaVehiculoId + "  ORDER BY descripcion ASC";

				PreparedStatement pst = connect().prepareStatement(SQL);
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {
					RvModeloVehiculo obj = new RvModeloVehiculo();
					RvModeloVehiculoPK id = new RvModeloVehiculoPK();
					id.setMarcaVehiculoId(rs.getInt("marca_vehiculo_id"));
					id.setCategoriaVehiculoId(rs.getInt("categoria_vehiculo_id"));
					id.setModeloVehiculoId(rs.getInt("modelo_vehiculo_id"));
					obj.setId(id);
					obj.setDescripcion(rs.getString("descripcion"));
					lista.add(obj);
				}
			} catch (Exception e) {
				throw (e);
			}
			return lista;
		}
		
	///stp_rv_obtener_anno_fabricacion
		
		public DatosNecesariosDeterDTO getAllAnioFabricacion(int reqId,int anio)throws Exception{
			try{
				
				String SQL = new String("stp_rv_obtener_anno_fabricacion ?,?");

				PreparedStatement pst = connect().prepareStatement(SQL.toString());
				pst.setInt(1, reqId);
				pst.setInt(2, anio);

				ResultSet rs = pst.executeQuery();
				DatosNecesariosDeterDTO datosDet = null;
				while(rs.next()){
					datosDet = new DatosNecesariosDeterDTO();
					datosDet.setDjPrevioId(rs.getInt("requerimiento_id"));
					datosDet.setAnioFabric(rs.getInt("anno_fabricacion"));	
				}
				return datosDet;
			}catch(Exception e){
				throw(e);
			}
		}	
		
			public BigDecimal getMontoAnioMenorAntigDetalle(int categoriaId,
					int marcaVehiculoId, int modeloVehiculoId, int periodoAfectacion)
					throws SisatException {
				try {
					StringBuilder SQL = new StringBuilder(
							"SELECT vr.valor_referencial FROM ").append(
							Constante.schemadb).append(".dt_valor_referencial vr");
					SQL.append(" WHERE ");
					SQL.append(" vr.categoria_vehiculo_id =").append(categoriaId);
					SQL.append(" AND vr.marca_vehiculo_id =").append(marcaVehiculoId);
					SQL.append(" AND vr.modelo_vehiculo_id =").append(modeloVehiculoId);
					SQL.append(" AND vr.periodo_afectacion =")
							.append(periodoAfectacion);
					SQL.append(" AND vr.periodo_fabricacion =").append(
							(periodoAfectacion - 1));
		
					PreparedStatement pst = connect().prepareStatement(SQL.toString());
					ResultSet rs = pst.executeQuery();
		
					BigDecimal monto = null;
					while (rs.next()) {
						monto = rs.getBigDecimal("valor_referencial");
					}
					if (monto == null) {
//							throw new SisatException(
//								"No se puede calcular porque no existe valor ajustado."
//										+ (new StringBuffer())
//												.append("Verificar valor referencial para categoría ")
//												.append(categoriaId)
//												.append(" , marca ")
//												.append(marcaVehiculoId)
//												.append(" y modelo ")
//												.append(modeloVehiculoId)
//												.append(" para el vehículo en el año de afectacion ")
//												.append(periodoAfectacion)
//												.append(" y año fabricacion ")
//												.append(periodoAfectacion - 1)
//												.toString());
					}
					return monto;
				} catch (SisatException ex) {
					throw ex;
				} catch (Exception ex) {
					// TODO : Controller Exception
					System.out.println("ERROR: " + ex);
		
				}
				return null;
			}
			public int actualizarUbicacion(int reqId,int paquete,int annioPaquete,int expediente) throws Exception{
				int salida=0;
				try {
					CallableStatement cs = connect().prepareCall(
							"{call dbo.stp_rp_actualiza_ubicacion_inspeccion(?,?,?,?)}");
					cs.setInt(1,reqId );
					cs.setInt(2,paquete );
					cs.setInt(3,annioPaquete );
					cs.setInt(4,expediente );
					cs.execute();									
					
				}catch (Exception ex) {
					System.out.println("ERROR: " + ex);
				}
				return salida;
			}
			
			public int actualizarImpuestoVehicular (RvOmisosDetalleVehicular deter) throws Exception {

				int salida = 0;
				try {
					CallableStatement cs = connect().prepareCall(
							"{call dbo.stp_rv_actualiza_impuesto_inspeccion(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
					
					cs.setInt(1, deter.getRequerimientoId());
					cs.setInt(2, deter.getAnnoDeterminacion());
					cs.setBigDecimal(3, deter.getTasa());
					cs.setBigDecimal(4, deter.getBaseImponible());
					cs.setBigDecimal(5, deter.getBaseExonerada());
					cs.setBigDecimal(6, deter.getBaseAfecta());
					cs.setBigDecimal(7, deter.getImpuesto());
					cs.setInt(8, deter.getMarcaId());
					cs.setInt(9, deter.getCategoriaId());
					cs.setInt(10, deter.getModeloId());
					cs.setString(11, deter.getEstado());
					cs.setInt(12, deter.getUsuarioId());
					cs.setString(13, deter.getTerminal());
					cs.setInt(14, deter.getVehicularOmisosDetalleId());
					
					cs.execute();
					
				} catch (Exception ex) {
					System.out.println("ERROR: " + ex);
				}
				return salida;
			}
			
			public DatosNecesariosDeclaracionDTO getDatosNecesariosDeclaracionDetalle(int reqDetId,int reqId,int anio) throws Exception {
				try {
					
					String SQL = new String("stp_rv_obtener_datos_declaracion_detalle ?,?,?");

					PreparedStatement pst = connect().prepareStatement(SQL.toString());
					pst.setInt(1, reqDetId);
					pst.setInt(2, reqId);
					pst.setInt(3, anio);
					
					ResultSet rs = pst.executeQuery();
					DatosNecesariosDeclaracionDTO datosDj = null;
					while (rs.next()) {
						datosDj = new DatosNecesariosDeclaracionDTO();
						datosDj.setCondicionVehiculoId(rs.getInt("condicion_vehiculo_id"));
						datosDj.setTipoTransmisionId(rs.getInt("tipo_transmision_id"));
						datosDj.setTipoTraccionId(rs.getInt("tipo_traccion_id"));
						datosDj.setMarcaVehicId(rs.getInt("marca_vehiculo_id"));
						datosDj.setCategVehicId(rs.getInt("categoria_vehiculo_id"));
						datosDj.setModeloVehicId(rs.getInt("modelo_vehiculo_id"));
						datosDj.setTipoMotorId(rs.getInt("tipo_motor_id"));
						datosDj.setPlaca(rs.getString("placa"));
						datosDj.setNumMotor(rs.getString("num_motor"));
						datosDj.setAnioFabric(rs.getInt("anno_fabricacion"));
						datosDj.setFechaInsRegistros(rs.getTimestamp("fecha_inscripcion"));
						datosDj.setNumCilindros(rs.getInt("numero_cilindros"));
						datosDj.setPesoBruto(rs.getInt("peso_bruto"));
						datosDj.setTipoCarroceriaId(rs.getInt("carroceria_id"));
//						datosDj.setClaseVehiculoId(rs.getInt("clase_vehiculo_id"));
						datosDj.setTipoMonedaId(rs.getInt("tipo_moneda_id"));
						datosDj.setTipoPropiedadId(rs.getInt("tipo_propiedad_id"));
						datosDj.setPersonaId(rs.getInt("persona_id"));
						datosDj.setTipoAdquisicionId(rs.getInt("tipo_adquisicion_id"));
						datosDj.setMotivoDeclaracionId(rs.getInt("rv_motivo_declaracion_id"));
						datosDj.setNotariaId(rs.getInt("notaria_id"));
						datosDj.setAnioAfec(rs.getInt("anno_ini_afectacion"));
						datosDj.setAnioFinAfec(rs.getInt("anno_fin_afectacion"));
						datosDj.setAnioAfec(rs.getInt("anno_afectacion"));
						datosDj.setNumTarjetaPropiedad(rs.getString("num_tarjeta_propiedad"));
						datosDj.setFechaAdquisicion(rs.getTimestamp("fecha_adquisicion"));
						datosDj.setValorAdquiSoles(rs.getBigDecimal("val_adq_soles"));
						datosDj.setPorcentajePropiedad(rs.getBigDecimal("porc_propiedad"));
						datosDj.setEstado(rs.getString("estado"));
						datosDj.setGlosa(rs.getString("glosa"));
					}
					return datosDj;
				}catch(Exception e){
					throw(e);
				}
				
			}
			
			public Integer getVehiculoFiscalizado(int reqId ) throws Exception {

				Integer vehiculoId = null;
				try {
					
					StringBuilder SQL = new StringBuilder("dbo.stp_rv_obtener_dj_vehiculo ?");
					
					PreparedStatement pst = connect().prepareStatement(SQL.toString());
					pst.setInt(1, reqId);
					ResultSet rs = pst.executeQuery();
					
					while(rs.next()){
						
						RvDjvehicular obj = new RvDjvehicular();
						
						obj.setRequerimientoId(rs.getInt("requerimiento_id"));
						obj.setVehiculoId(rs.getInt("vehiculo_id"));
						vehiculoId=obj.getVehiculoId();
					}

				}catch(Exception e){
				vehiculoId = null;
				throw(e);
			}
				return vehiculoId;
			}
			
			//--==CRAMIREZ==--
				public List<FindCcLote> getAllLotesVehicular() throws Exception {
					List<FindCcLote> list = new ArrayList<FindCcLote>();
	
					try {
						String SQL = new String("stp_rv_obtener_lote_vehicular_masivo");
	
						PreparedStatement pst = connect().prepareStatement(SQL.toString());
						ResultSet rs = pst.executeQuery();
						while (rs.next()) {
	
							FindCcLote obj = new FindCcLote();
							
							obj.setLoteId(rs.getInt("lote_id"));
							obj.setTipoLoteId(rs.getInt("tipo_lote_id"));
							obj.setAnnoLote(rs.getInt("anno_lote"));
							obj.setDescripcionLote(rs.getString("descripcion_lote"));
							obj.setFechaRegistro(rs.getTimestamp("fecha_registro"));
							obj.setNombreEjecutor(rs.getString("nombre_usuario"));
	
							list.add(obj);
	
						}
					} catch (Exception e) {
						throw (e);
					}
					return list;
				}
				
				public List<Integer> estadisticaLoteMasivo(int loteId){
					
					List<Integer> list = new ArrayList<Integer>();

					try {
						
									
						String SQL = new String("stp_rv_obtener_estadistica_lote_vehicular_masiva ?");
	
						PreparedStatement pst = connect().prepareStatement(SQL.toString());
						pst.setInt(1, loteId);
						ResultSet rs = pst.executeQuery();				
						while (rs.next()) {
	
							list.add(rs.getInt("cant_lote"));
							list.add(rs.getInt("cant_con_requerimiento"));
							list.add(rs.getInt("cant_con_notificacion"));
							list.add(rs.getInt("cant_ins_servicio"));
							list.add(rs.getInt("cant_RD"));
							list.add(rs.getInt("cant_RM"));
							list.add(rs.getInt("cant_pagaron"));
							list.add(rs.getInt("cant_no_corresponde"));
							
	
						}												
					} catch (Exception ex) {
						System.out.println("ERROR: " + ex);
					}
					return list;
			}
				
				public List<MarcaModeloTemporalDTO> getMarcaModeloTemporal(int loteId, int tipo){
					
					List<MarcaModeloTemporalDTO> list = new ArrayList<MarcaModeloTemporalDTO>();

					try {
						
									
						String SQL = new String("stp_rv_obtener_marca_modelo_temporal ?,?");
	
						PreparedStatement pst = connect().prepareStatement(SQL.toString());
						pst.setInt(1, loteId);
						pst.setInt(2, tipo);
						
						ResultSet rs = pst.executeQuery();				
						while (rs.next()) {
							MarcaModeloTemporalDTO obj = new MarcaModeloTemporalDTO();
							
							obj.setDescripcion(rs.getString("descripcion"));

							list.add(obj);
						}												
					} catch (Exception ex) {
						System.out.println("ERROR: " + ex);
					}
					return list;
			}
}
