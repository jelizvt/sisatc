package com.sat.sisat.papeleta.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;



import com.sat.sisat.common.dao.GeneralDao;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;


import com.sat.sisat.exception.SisatException;
import com.sat.sisat.papeleta.dto.CargaLoteDTO;
import com.sat.sisat.papeleta.dto.ConsultaPapeletaDTO;
import com.sat.sisat.papeleta.dto.FindPapeletas;
import com.sat.sisat.papeleta.dto.GridDetalleLote;
import com.sat.sisat.papeleta.dto.HistoricoPapeletaDTO;
import com.sat.sisat.papeleta.dto.RecordPapeletaDTO;
import com.sat.sisat.papeleta.dto.ResultadoCargaLoteDTO;
import com.sat.sisat.papeleta.dto.UbicacionFiscalDTO;
import com.sat.sisat.papeletas.dto.PapeletaDTO;
import com.sat.sisat.papeletas.dto.ResolucionDTO;
import com.sat.sisat.persistence.entity.GnAuditoriaOperacion;
import com.sat.sisat.persistence.entity.GnLugar;
import com.sat.sisat.persistence.entity.GnSector;
import com.sat.sisat.persistence.entity.GnTipoAgrupamiento;
import com.sat.sisat.persistence.entity.GnTipoEdificacion;
import com.sat.sisat.persistence.entity.GnTipoIngreso;
import com.sat.sisat.persistence.entity.GnTipoInterior;
import com.sat.sisat.persistence.entity.GnTipoVia;
import com.sat.sisat.persistence.entity.MpClaseLicencia;
import com.sat.sisat.persistence.entity.MpPersona;
import com.sat.sisat.persistence.entity.MpTipoDocuIdentidad;
import com.sat.sisat.persistence.entity.PaCargaLote;
import com.sat.sisat.persistence.entity.PaDireccion;
import com.sat.sisat.persistence.entity.PaDocuAnexo;
import com.sat.sisat.persistence.entity.PaInfraccion;
import com.sat.sisat.persistence.entity.PaLey;
import com.sat.sisat.persistence.entity.PaMedioProbatorio;
import com.sat.sisat.persistence.entity.PaNivelGravedad;
import com.sat.sisat.persistence.entity.PaPersona;
import com.sat.sisat.persistence.entity.RvMarca;
import com.sat.sisat.persistence.entity.RvModeloVehiculo;
import com.sat.sisat.predial.dto.FindPersona;
import com.sat.sisat.vehicular.dto.BuscarPersonaDTO;

public class PapeletaBusinessDao extends GeneralDao {
	
	public List<GnSector> getAllGnSector()throws Exception{
		List<GnSector> lista=new LinkedList<GnSector>();
		try{ 
			StringBuffer SQL=new StringBuffer("select sector_id,descripcion from ").append(Constante.schemadb).append(".gn_sector order by descripcion asc");
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				GnSector obj=new GnSector(); 
				obj.setSectorId(rs.getInt("sector_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				
				lista.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	
	
	public List<PaLey> getAllLeyInfracciones() throws Exception {
		List<PaLey> list = new ArrayList<PaLey>();
		try{
			
			StringBuffer SQL=new StringBuffer("select ley_id, descripcion, descripcion_corta from  ").append(Constante.schemadb).append(".pa_ley where estado = '1'");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				PaLey obj = new PaLey();
				obj.setLeyId(rs.getInt(1));
				obj.setDescripcion(rs.getString(2));
				obj.setDescripcionCorta(rs.getString(3));
				list.add(obj);
			}
		}catch(Exception e ){
			throw(e);
		}
		return list;
	}
	
	public List<FindPapeletas> buscarPapeletasCriteria(FindPapeletas findPapeleta) throws Exception {
		//ORIGEN M:MASIVO / I:INDIVIDUAL
		//ESTADO 0:INACTIVO(PENDIENTE DE VERIFICACION) 1:ACTIVO 3:PAGADO (DESCARGO) 9:ELIMINADO  
		List<FindPapeletas> list =  new ArrayList<FindPapeletas>();
		StringBuffer stBuffer = new StringBuffer();
		stBuffer.append(" select TOP 500 p.nro_papeleta, p.fecha_infraccion, peri.persona_id infractorId,  gnperi.apellidos_nombres infractor, ");
		stBuffer.append(" gnperp.apellidos_nombres propietario,  p.placa,   i.descripcion_corta infraccion , ");
		stBuffer.append(" p.papeleta_id,   p.origen,  r.numero_resolucion, s.descripcion estado_resolucion, p.monto_multa, p.estado, p.num_licencia, ");
		stBuffer.append(" td.descrpcion_corta desc_tipo_documento, peri.nro_doc_identidad,p.glosa,p.placa_anterior,u.nombre_usuario,uc.nombre_usuario usuario_actualiza ");
		stBuffer.append(" ,r.fecha_recepcion fecha_notificacion ");
		stBuffer.append(" ,r.fecha_emision ");
		stBuffer.append(" from ").append(Constante.schemadb).append(".pa_papeleta p  ");
		stBuffer.append(" inner join ").append(Constante.schemadb).append(".pa_infraccion i on (p.infraccion_id = i.infraccion_id ) "); 
		stBuffer.append(" inner join ").append(Constante.schemadb).append(".pa_incidencia c on (c.papeleta_id=p.papeleta_id and c.estado='1') ");
		stBuffer.append(" inner join ").append(Constante.schemadb).append(".sg_usuario uc on (uc.usuario_id = c.usuario_id )  ");
		stBuffer.append(" left join ").append(Constante.schemadb).append(".pa_persona peri on (p.persona_infractor_id = peri.persona_id ) ");  
		stBuffer.append(" left join ").append(Constante.schemadb).append(".gn_persona gnperi on (gnperi.persona_id=p.persona_infractor_id) ");
		stBuffer.append(" left join ").append(Constante.schemadb).append(".pa_persona perp on (p.persona_propietario_id = perp.persona_id )   ");
		stBuffer.append(" left join ").append(Constante.schemadb).append(".gn_persona gnperp on (gnperp.persona_id=p.persona_propietario_id) ");
		stBuffer.append(" left join ").append(Constante.schemadb).append(".mp_tipo_docu_identidad td on (td.tipo_doc_identidad_id=peri.tipo_doc_identidad) "); 
		stBuffer.append(" left join ").append(Constante.schemadb).append(".pa_resolucion_papeleta r on(r.papeleta_id=p.papeleta_id)  and r.estado='1' ");
		stBuffer.append(" left join ").append(Constante.schemadb).append(".pa_estado_resol s on (s.estado_resol_id=r.estado_resolucion_id) ");
		stBuffer.append(" inner join ").append(Constante.schemadb).append(".sg_usuario u on (u.usuario_id = p.usuario_id ) ");
		stBuffer.append(" where p.estado NOT IN ('9','0') ");
		
		if(findPapeleta.getNumPapeleta()!= null && !findPapeleta.getNumPapeleta().isEmpty()){
			stBuffer.append("and p.nro_papeleta = ? ");
		}
		else if(findPapeleta.getPlaca()!= null && !findPapeleta.getPlaca().isEmpty()){
			stBuffer.append("and p.placa = ? ");
		}
		else if(findPapeleta.getInfractorId()!= null && findPapeleta.getInfractorId()>Constante.RESULT_PENDING){
			stBuffer.append("and isnull(peri.persona_id,0) = ? ");
		}
		else if(findPapeleta.getTipoDocumentoId()!=null&&findPapeleta.getTipoDocumentoId()>0){
			if(findPapeleta.getNumeroDocumento()!=null&&!findPapeleta.getNumeroDocumento().isEmpty()){
				stBuffer.append("and peri.tipo_doc_identidad = ?  and peri.nro_doc_identidad=? ");	
			}
		}else{
			stBuffer.append("and peri.persona_id=-1 ");
		}
		stBuffer.append("order by p.papeleta_id ");
		
		int contador = 0;
		try{
			PreparedStatement pstm = connect().prepareStatement(stBuffer.toString());
			if(findPapeleta.getNumPapeleta()!= null && !findPapeleta.getNumPapeleta().isEmpty()){
				pstm.setString(++contador, findPapeleta.getNumPapeleta());
			}
			else if(findPapeleta.getPlaca()!= null && !findPapeleta.getPlaca().isEmpty()){
				pstm.setString(++contador, findPapeleta.getPlaca());
			}
			else if(findPapeleta.getInfractorId()!= null && findPapeleta.getInfractorId()>Constante.RESULT_PENDING){
				pstm.setInt(++contador, findPapeleta.getInfractorId());
			}
			else if(findPapeleta.getInfraccionId()>0){
				pstm.setInt(++contador, findPapeleta.getInfraccionId());
			}
			else if(findPapeleta.getTipoDocumentoId()!=null&&findPapeleta.getTipoDocumentoId()>0){
				if(findPapeleta.getNumeroDocumento()!=null){
					pstm.setInt(++contador, findPapeleta.getTipoDocumentoId());
					pstm.setString(++contador, findPapeleta.getNumeroDocumento());
				}
			}
			else if(findPapeleta.getNumeroLicencia()!=null){
				pstm.setString(++contador, findPapeleta.getNumeroLicencia());
			}
			ResultSet rs = pstm.executeQuery();
			
			while(rs.next()){
				FindPapeletas obj = new FindPapeletas();
				obj.setNumPapeleta(rs.getString("nro_papeleta"));
				obj.setFechaInfraccion(rs.getTimestamp("fecha_infraccion"));
				obj.setInfractorId(rs.getInt("infractorId"));
				obj.setInfractor(rs.getString("infractor"));
				obj.setPropietario(rs.getString("propietario"));
				obj.setPlaca(rs.getString("placa"));
				obj.setInfraccion(rs.getString("infraccion"));
				obj.setPapeletaId(rs.getInt("papeleta_id"));
				obj.setOrigen(rs.getString("origen"));
				obj.setNumOficio(rs.getString("numero_resolucion"));
				obj.setEstadoDesc(rs.getString("estado_resolucion"));
				obj.setMontoMulta(rs.getDouble("monto_multa"));
				obj.setEstadoPapeleta(rs.getString("estado"));
				obj.setEstado(rs.getString("estado"));
				obj.setNumeroLicencia(rs.getString("num_licencia"));
				obj.setDescTipoDocumento(rs.getString("desc_tipo_documento"));
				obj.setNumeroDocumento(rs.getString("nro_doc_identidad"));
				obj.setRegistrador(rs.getString("nombre_usuario"));
				obj.setUsuarioActualiza(rs.getString("usuario_actualiza"));
				//Actualizaicon. Muestra la fecha de emision y la fecha de notificacion de la resolucion
				obj.setFechaNotificacion(rs.getTimestamp("fecha_notificacion"));
				obj.setFechaEmision(rs.getTimestamp("fecha_emision"));
				list.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		return list;
	}
	
	public List<PaInfraccion> getAllInfracciones(PaInfraccion infraccion,
			String fechaInfraccionTramos) throws Exception {
		List<PaInfraccion> list = new ArrayList<PaInfraccion>();
		try {
			System.out.println("fechaInfraccionTramos : "+ fechaInfraccionTramos);
			System.out.println("infraccion.getLeyId() : "+ infraccion.getLeyId());
			
//			StringBuffer sql = new StringBuffer(
//					"select i.infraccion_id,i.ley_id,i.tipo_infraccion_id,i.nivel_gravedad_id, i.multa_uit, i.puntos,  i.descripcion, rtrim(ltrim(i.descripcion_corta)) as descripcion_corta from ");
//			sql.append(Constante.schemadb).append(".pa_infraccion i  ");
//			sql.append("INNER JOIN ").append(Constante.schemadb).append(".pa_tramo_infraccion  pti ON pti.tramoinfraccion_id = i.tramoinfraccion_id");			
//			sql.append(" where i.estado = '1'");
			if (infraccion.getLeyId() != null && infraccion.getLeyId() != 0) {
//				sql = sql.append(" and i.ley_id = ? AND pti.fecha_inicio <= ? AND pti.fecha_fin >= ?");
			}
			if (infraccion.getInfraccionId() != null && infraccion.getInfraccionId() != 0) {
//				sql = sql.append(" and i.infraccion_id = ? AND pti.fecha_inicio <= ? AND pti.fecha_fin >= ? ");
			}
//			sql = sql.append("order by  i.descripcion_corta ASC");
//			PreparedStatement pstm = connect().prepareStatement(sql.toString());
			String SQL = "listarTipoInfracciones ?, ? ,?, ?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			
			if (infraccion.getLeyId() != null && infraccion.getLeyId() != 0) {		
				pst.setInt(1,0 );	
				pst.setDate(2,DateUtil.dateToSqlDate(DateUtil.convertStringToDate(fechaInfraccionTramos)));
				pst.setDate(3,DateUtil.dateToSqlDate(DateUtil.convertStringToDate(fechaInfraccionTramos)));
				pst.setInt(4,infraccion.getLeyId() );				
			}
			if (infraccion.getInfraccionId() != null
					&& infraccion.getInfraccionId() != 0) {
				pst.setInt(1, infraccion.getInfraccionId());
				pst.setDate(2,DateUtil.dateToSqlDate(DateUtil.convertStringToDate(fechaInfraccionTramos)));
				pst.setDate(3,DateUtil.dateToSqlDate(DateUtil.convertStringToDate(fechaInfraccionTramos)));
//				pst.setString(2,fechaInfraccionTramos );
//				pst.setString(3,fechaInfraccionTramos );
				pst.setInt(4,0 );	
				
			}
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				PaInfraccion obj = new PaInfraccion();
				obj.setInfraccionId(rs.getInt("infraccion_id"));
				obj.setLeyId(rs.getInt("ley_id"));
				obj.setTipoInfraccionId(rs.getInt("tipo_infraccion_id"));
				obj.setNivelGravedadId(rs.getInt("nivel_gravedad_id"));
				obj.setMultaUit(rs.getBigDecimal("multa_uit"));
				obj.setPuntos(rs.getInt("puntos"));
				obj.setDescripcion(rs.getString("descripcion_corta") + " - "
						+ rs.getString("descripcion"));
				obj.setDescripcionCorta(rs.getString("descripcion_corta")
						.trim());
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}
	
	public List<MpTipoDocuIdentidad> getAllMpTipoDocumento() throws Exception {
		List<MpTipoDocuIdentidad> list= new ArrayList<MpTipoDocuIdentidad>();
		try {
			StringBuffer sql = new StringBuffer("select tipo_doc_identidad_id, descripcion, descrpcion_corta from  "); 
					 sql.append(Constante.schemadb).append(".mp_tipo_docu_identidad");
			PreparedStatement pst = connect().prepareStatement(sql.toString());
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				MpTipoDocuIdentidad obj = new MpTipoDocuIdentidad();
				obj.setTipoDocIdentidadId(rs.getInt(1));
				obj.setDescripcion(rs.getString(2));
				obj.setDescrpcionCorta(rs.getString(3));
				list.add(obj);
			}
		}catch(Exception e) {
			throw(e);
		}
		return list;
	}
	
	public FindPersona existePersona(String nroDocumento,Integer tipoDocumentoId,Integer personaId) throws Exception {
		FindPersona persona=new FindPersona(); 
		try {
			/*
			StringBuffer SQL = new StringBuffer("select p.persona_id,p.apellidos_nombres,d.descripcion tipo_documento,p.nro_doc_identidad "); 
			SQL.append(" from dbo.gn_persona p  ");
			SQL.append(" inner join dbo.mp_tipo_docu_identidad d on (d.tipo_doc_identidad_id=p.tipo_documento_id) ");
			SQL.append(" where p.nro_doc_identidad=? AND p.tipo_documento_id=? AND p.persona_id!=? "); 
					 
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setString(1, nroDocumento);
			pst.setInt(2, tipoDocumentoId);
			pst.setInt(3, personaId);
			
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				FindPersona persona=new 
				
				list.add(obj);
			}*/
		}catch(Exception e) {
			throw(e);
		}
		return persona;
	}
	
	public List<BuscarPersonaDTO> buscaPersona(MpPersona persona) throws Exception {
		
		List<BuscarPersonaDTO> lista = new LinkedList<BuscarPersonaDTO>();
		
		
		
		try{
		StringBuilder SQL = new StringBuilder("SELECT per.persona_id,tdi.descrpcion_corta,per.nro_docu_identidad,per.apellidos_nombres,per.razon_social  FROM ").append(Constante.schemadb).append(".mp_persona per ");
		SQL.append("INNER JOIN ").append(Constante.schemadb).append(".mp_tipo_docu_identidad tdi ON tdi.tipo_doc_identidad_id=per.tipo_doc_identidad_id ");
		SQL.append("WHERE per.persona_id = ? ");
		
//		System.out.println(SQL.toString());
//		System.out.println(persona.getPersonaId());
		
		
		PreparedStatement pst = connect().prepareStatement(SQL.toString());
		pst.setInt(1,persona.getPersonaId());
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
			BuscarPersonaDTO obj = new BuscarPersonaDTO();
			obj.setPersonaId(rs.getInt("persona_id"));
			obj.setTipoDocIdentidad(rs.getString("descrpcion_corta"));
			obj.setNroDocuIdentidad(rs.getString("nro_docu_identidad"));
			obj.setApellidosNombres(rs.getString("apellidos_nombres"));
			obj.setRazonSocial(rs.getString("razon_social"));
			lista.add(obj);
		}
		}catch(Exception e){
			throw(e);
		}
		
		return lista;
	}
	
	public List<RvModeloVehiculo> getAllRvModeloVehiculo() throws Exception{
		List<RvModeloVehiculo> list= new ArrayList<RvModeloVehiculo>();
		try {
			StringBuilder sql = new StringBuilder("select marca_vehiculo_id,categoria_vehiculo_id,modelo_vehiculo_id,descripcion from ");
					sql.append(Constante.schemadb).append(".rv_modelo_vehiculo");
			PreparedStatement pst = connect().prepareStatement(sql.toString());
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				RvModeloVehiculo obj = new RvModeloVehiculo();
				obj.getId().setMarcaVehiculoId(rs.getInt(1));
				obj.getId().setCategoriaVehiculoId(rs.getInt(2));
				obj.getId().setModeloVehiculoId(rs.getInt(3));
				obj.setDescripcion(rs.getString(4));
				list.add(obj);
			}
		}catch(Exception e) {
			throw(e);
		}
		return list;
	}

	public List<GnTipoEdificacion> getAllGnTipoEdificacion()throws Exception{
		List<GnTipoEdificacion> lista=new LinkedList<GnTipoEdificacion>();
		try{
			StringBuilder sql = new StringBuilder("select tipo_edificacion_id,descripcion,descripcion_corta  from ").append(Constante.schemadb).append(".gn_tipo_edificacion order by descripcion asc");
			
			PreparedStatement pst=connect().prepareStatement(sql.toString());
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				GnTipoEdificacion obj=new GnTipoEdificacion(); 
				obj.setTipoEdificacionId(rs.getInt("tipo_edificacion_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				obj.setDescripcionCorta(rs.getString("descripcion_corta"));
				
				lista.add(obj);
			}
			
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	
	public List<GnTipoIngreso> getAllGnTipoIngreso()throws Exception{
		List<GnTipoIngreso> lista=new LinkedList<GnTipoIngreso>();
		try{
			StringBuilder sql = new StringBuilder("select tipo_ingreso_id,descripcion,descripcion_corta from ").append(Constante.schemadb).append(".gn_tipo_ingreso order by descripcion asc");
			
			PreparedStatement pst=connect().prepareStatement(sql.toString());
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				GnTipoIngreso obj=new GnTipoIngreso(); 
				obj.setTipoIngresoId(rs.getInt("tipo_ingreso_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				obj.setDescripcionCorta(rs.getString("descripcion_corta"));
				
				lista.add(obj);
			}
			
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	
	public List<GnTipoInterior> getAllGnTipoInterior()throws Exception{
		List<GnTipoInterior> lista=new LinkedList<GnTipoInterior>();
		try{
			StringBuilder sql = new StringBuilder("select tipo_interior_id,descripcion,descripcion_corta from ").append(Constante.schemadb).append(".gn_tipo_interior order by descripcion asc");
			
			PreparedStatement pst=connect().prepareStatement(sql.toString());
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				GnTipoInterior obj=new GnTipoInterior(); 
				obj.setTipoInteriorId(rs.getInt("tipo_interior_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				obj.setDescripcionCorta(rs.getString("descripcion_corta"));
				
				lista.add(obj);
			}
			
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	
	public List<GnTipoAgrupamiento> getAllGnTipoAgrupamiento()throws Exception{
		List<GnTipoAgrupamiento> lista=new LinkedList<GnTipoAgrupamiento>();
		try{
			StringBuilder sql = new StringBuilder("select tipo_agrupamiento_id,descripcion,descripcion_corta from ").append(Constante.schemadb).append(".gn_tipo_agrupamiento order by descripcion asc");
			
			PreparedStatement pst=connect().prepareStatement(sql.toString());
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				GnTipoAgrupamiento obj=new GnTipoAgrupamiento(); 
				obj.setTipoAgrupamientoId(rs.getInt("tipo_agrupamiento_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				obj.setDescripcionCorta(rs.getString("descripcion_corta"));
				
				lista.add(obj);
			}
			
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	
	public List<GnTipoVia> getAllGnTipoVia()throws Exception{
		List<GnTipoVia> lista=new LinkedList<GnTipoVia>();
		try{
			StringBuilder sql = new StringBuilder("SELECT descripcion,descripcion_corta,tipo_via_id FROM ").append(Constante.schemadb).append(".gn_tipo_via order by descripcion asc");
			
			PreparedStatement pst=connect().prepareStatement(sql.toString());
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				GnTipoVia obj=new GnTipoVia(); 
				obj.setTipoViaId(rs.getInt("tipo_via_id"));
				obj.setDescripcionCorta(rs.getString("descripcion_corta"));
				obj.setDescripcion(rs.getString("descripcion"));
				lista.add(obj);
			}
			
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	
	public int getNextMpDireccionId()throws Exception{
		int result=0;
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("select coalesce(max(direccion_id)+1,1) as next_id from ").append(Constante.schemadb).append(".mp_direccion");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			ResultSet rs=pst.executeQuery();
			if(rs.next()){
				result=rs.getInt("next_id");
			}
		}catch(Exception e){
			throw(e);
		}
		return result;
	}
	
	public int getNextMpPersonaDomicilioId()throws Exception{
		int result=0;
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("select coalesce(max(domicilio_persona_id)+1,1) as next_id from ").append(Constante.schemadb).append(".mp_persona_domicilio");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			ResultSet rs=pst.executeQuery();
			if(rs.next()){
				result=rs.getInt("next_id");
			}
		}catch(Exception e){
			throw(e);
		}
		return result;
	}
	
	public int generarNuevoLote() throws Exception{
		int result = 0;
		try{
			StringBuffer sql = new StringBuffer();
			sql.append("select coalesce(max(corr_oficio)+1,1) as next_lote_id from ").append(Constante.schemadb).append(".pa_carga_lotes ");

			PreparedStatement pst=connect().prepareStatement(sql.toString());
			ResultSet rs=pst.executeQuery();
			if(rs.next()){
				result=rs.getInt("next_lote_id");
			}
			
		}catch(Exception e){
			throw(e);
		}
		
		return result;
	}
	
	public int getNextPapeleta() throws Exception{
		int result = 0;
		try{
			StringBuffer sql = new StringBuffer();
			sql.append("select coalesce(max(papeleta_id)+1,1) as next_papeleta_id from ").append(Constante.schemadb).append(".pa_papeleta p ");

			PreparedStatement pst=connect().prepareStatement(sql.toString());
			ResultSet rs=pst.executeQuery();
			if(rs.next()){
				result=rs.getInt("next_papeleta_id");
			}
			
		}catch(Exception e){
			throw(e);
		}
		
		return result;
	}

	public ArrayList<PaDocuAnexo> getAllPaDocuAnexo(int papeletaId) throws  Exception{
		ArrayList<PaDocuAnexo> list = new ArrayList<PaDocuAnexo>();
		StringBuffer stBuffer = new StringBuffer();
		stBuffer.append("select a.docu_anexo_id,a.referencia,a.numero_documento,a.tipo_documento_id,p.descripcion,a.papeleta_id,a.content_id from ").append(Constante.schemadb).append(".pa_docu_anexo a "); 
		stBuffer.append("inner join ").append(Constante.schemadb).append(".pa_medio_probatorio p on (p.medio_probatorio_id=a.tipo_documento_id) ");
		stBuffer.append("where a.papeleta_id=? and a.estado='").append(Constante.ESTADO_ACTIVO).append("'");
		
		PreparedStatement pstm = connect().prepareStatement(stBuffer.toString());
		pstm.setInt(1,papeletaId);
		ResultSet rs = pstm.executeQuery();
		while(rs.next()){
			PaDocuAnexo anexo=new PaDocuAnexo();
			anexo.setDocuAnexoId(rs.getInt("docu_anexo_id"));
			anexo.setPapeletaId(rs.getInt("papeleta_id"));
			anexo.setNumeroDocumento(rs.getString("numero_documento"));
			anexo.setReferencia(rs.getString("referencia"));
			anexo.setTipoDocumentoId(rs.getInt("tipo_documento_id"));
			anexo.setTipoDocumento(rs.getString("descripcion"));
			anexo.setContentId(rs.getBigDecimal("content_id"));
			list.add(anexo);
		}
		return list;
	}
	
	public String iniciarProcesoCargaLote(String directorio, String nombreArchivo,Integer cargaLoteId,Integer procesoCargaId) throws Exception{
		String errorMessage=new String();
		Connection cn = connect();
		try{ StringBuilder query = new StringBuilder("{ call ").append(Constante.schemadb).append(".sp_carga_lotes_bulk(?,?,?,?,?) }");
			 //cn.setAutoCommit(false);
	         CallableStatement oCallableStatement = cn.prepareCall(query.toString());
	         oCallableStatement.registerOutParameter(1, Types.VARCHAR);
	         oCallableStatement.setString(2,directorio);
	         oCallableStatement.setString(3,nombreArchivo);
	         oCallableStatement.setInt(4,cargaLoteId);
	         oCallableStatement.setInt(5,procesoCargaId);
	         oCallableStatement.execute();
	         errorMessage=oCallableStatement.getString(1);
//	         System.out.println("cargaLoteId "+cargaLoteId+" / "+"procesoCargaId "+procesoCargaId);
		 }catch(Exception e ){
			 System.out.println("Error en el proceso de carga masiva papeletas -===============================================-");
			 e.printStackTrace();
			 throw (e);
		 }
		return errorMessage;
	}
	
	public ArrayList<FindPersona> getFindPersona()throws Exception{
		ArrayList<FindPersona> lista=new ArrayList<FindPersona>();
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("SELECT persona_id,tipo_persona_id,tipo_doc_identidad_id,nro_docu_identidad,ape_paterno,ape_materno,primer_nombre,razon_social,apellidos_nombres FROM ").append(Constante.schemadb).append(".mp_persona "); 
			SQL.append("where tipo_persona_id=1 ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				FindPersona obj=new FindPersona();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setTipoPersonaId(rs.getInt("tipo_persona_id"));
				obj.setTipoDocIdentidadId(rs.getInt("tipo_doc_identidad_id"));
				obj.setNroDocuIdentidad(rs.getString("nro_docu_identidad"));
				obj.setApePaterno(rs.getString("ape_paterno"));
				obj.setApeMaterno(rs.getString("ape_materno"));
				obj.setPrimerNombre(rs.getString("primer_nombre"));
				obj.setRazonSocial(rs.getString("razon_social"));
				obj.setApellidosNombres(rs.getString("apellidos_nombres"));
				lista.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	
	
	public Integer papeletaDeterminada(Integer papeletaId) throws Exception
	{
		//Verificamos si la papeleta esta determinada.		
		String SQL;
		SQL="select count(d.deuda_id) cantidad from cd_deuda d inner join pa_papeleta  p on d.papeleta_id=p.papeleta_id where d.estado='1' and d.papeleta_id=?";
		
		PreparedStatement pstm = connect().prepareStatement(SQL);
		pstm.setInt(1,papeletaId);
		ResultSet rs = pstm.executeQuery();
		
		Integer cantidad=0;
		if(rs.next()) cantidad = rs.getInt("cantidad");	
		
		
		
		return cantidad==null ? 0 : cantidad;
		
	}
	
	
	public BigDecimal obtenerUIT(Integer anio) throws Exception{
		BigDecimal obj=BigDecimal.ZERO;
		StringBuffer stBuffer= new StringBuffer(2000);
		stBuffer.append("SELECT uit from ").append(Constante.schemadb).append(".gn_uit where anno_uit = ?");
		PreparedStatement pstm = connect().prepareStatement(stBuffer.toString());
		pstm.setInt(1,anio);
		ResultSet rs = pstm.executeQuery();
		if(rs.next()){
			obj = rs.getBigDecimal("uit");	
		}
		return obj;
	}
	
	public PaInfraccion buscarInfraccion(PaInfraccion infraccion) throws Exception {
		// TODO Auto-generated method stub
		try {
		StringBuffer stBuffer = new StringBuffer(1000);
		stBuffer.append("SELECT p.* from ").append(Constante.schemadb).append(".pa_infraccion p where p.infraccion_id = ?");
		PreparedStatement pstm = connect().prepareStatement(stBuffer.toString());
		pstm.setInt(1,infraccion.getInfraccionId());
		ResultSet rs = pstm.executeQuery();
		rs.next();
		infraccion.setMultaUit(rs.getBigDecimal("multa_uit"));
		infraccion.setTipoInfraccionId(rs.getInt("tipo_infraccion_id"));
		infraccion.setNivelGravedadId(rs.getInt("nivel_gravedad_id"));
		
		}catch(Exception e ){
			throw(e);
		}
		return infraccion;
	}
	
	public List<MpClaseLicencia> getAllMpClaseLicencia()  throws Exception{
		List<MpClaseLicencia> list= new ArrayList<MpClaseLicencia>();
		try {
			StringBuffer sql = new StringBuffer("select clase_licencia_id, descripcion,descripcion_corta from ").append(Constante.schemadb).append(".mp_clase_licencia");
			PreparedStatement pst = connect().prepareStatement(sql.toString());
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				MpClaseLicencia obj = new MpClaseLicencia();
				obj.setClaseLicenciaId(rs.getInt(1));
				obj.setDescripcion(rs.getString(2));
				obj.setDescripcionCorta(rs.getString(3));
				list.add(obj);
			}
		}catch(Exception e) {
			throw(e);
		}
		return list;
	}
	
	public List<RvMarca> getAllRvMarcaVehiculo() throws Exception{
		List<RvMarca> list= new ArrayList<RvMarca>();
		try {
			StringBuffer sql = new StringBuffer("select marca_vehiculo_id, descripcion from ").append(Constante.schemadb).append(".rv_marca");
			PreparedStatement pst = connect().prepareStatement(sql.toString());
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				RvMarca obj = new RvMarca();
				obj.setMarcaVehiculoId(rs.getInt(1));
				obj.setDescripcion(rs.getString(2));
				list.add(obj);
			}
		}catch(Exception e) {
			throw(e);
		}
		return list;
	}
	
	public List<PaMedioProbatorio> getAllMedioProbatorio () throws Exception {
		List<PaMedioProbatorio> list = new ArrayList<PaMedioProbatorio>();
		try{
			StringBuffer sql = new StringBuffer("select medio_probatorio_id, descripcion, descripcion_corta from ").append(Constante.schemadb).append(".pa_medio_probatorio where estado = 'A'");
			PreparedStatement pstm = connect().prepareStatement(sql.toString());
			ResultSet rs = pstm.executeQuery();
			while(rs.next()){
				PaMedioProbatorio obj = new PaMedioProbatorio();
				obj.setMedioProbatorioId(rs.getInt(1));
				obj.setDescripcion(rs.getString(2));
				list.add(obj);
			}
					
		}catch(Exception e ){
			throw(e);
		}
		return list;
	}
	
	public List<PaNivelGravedad> getAllNivelGravedad(PaInfraccion infraccion) throws Exception{
		List<PaNivelGravedad> list = new ArrayList<PaNivelGravedad>();
		try{
			StringBuffer sql = new StringBuffer("select nivel_gravedad_id, descripcion,  descripcion_corta from ").append(Constante.schemadb).append(".pa_nivel_gravedad where estado = 'A' ");
			if(infraccion.getNivelGravedadId()!=0){
				sql=sql.append("and nivel_gravedad_id = ").append(infraccion.getNivelGravedadId());
			}
			PreparedStatement pstm = connect().prepareStatement(sql.toString());
			ResultSet rs = pstm.executeQuery();
			while(rs.next()){
				PaNivelGravedad obj = new PaNivelGravedad();
				obj.setNivelGravedadId(rs.getInt(1));
				obj.setDescripcion(rs.getString(2));
				list.add(obj);
			}
			
		}catch(Exception e){
			throw(e);
		}
		return list;
	}

	public List<MpPersona> listarPersonasxDni(String numeroDocumento) throws Exception  {
		List<MpPersona> list = new ArrayList<MpPersona>();
		try {
			StringBuffer stB = new 	StringBuffer(200);
			stB.append("select tipo_persona_id, tipo_doc_identidad_id ,")
			.append("ISNULL(ape_paterno,'') apePaterno,")
			.append("ISNULL(ape_materno,'') apeMaterno,")
			.append("ISNULL(primer_nombre,'') primerNombre,")
			.append("ISNULL(segundo_nombre,'') segundoNombre,")
			.append("ISNULL(razon_social,'') razonSocial,")
			.append("nro_docu_identidad ")
			.append("from ").append(Constante.schemadb).append(".mp_persona ") 
			.append("where nro_docu_identidad like '%")
			.append(numeroDocumento)
			.append("%'")
			;
			
			PreparedStatement pstmt = connect().prepareStatement(stB.toString());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				MpPersona per = new MpPersona();
				per.setTipoPersonaId(rs.getInt(1));
				per.setTipoDocIdentidadId(rs.getInt(2));
				per.setApePaterno(rs.getString(3));
				per.setApeMaterno(rs.getString(4));
				per.setPrimerNombre(rs.getString(5)); 
				per.setSegundoNombre(rs.getString(6));
				per.setRazonSocial(rs.getString(7));
				per.setNroDocuIdentidad(rs.getString(8));
				per.setApellidosNombres(per.getApePaterno() + per.getApeMaterno() + per.getPrimerNombre() + per.getSegundoNombre());
				//per.setConcatenadoNombres(per.getApePaterno() + per.getApeMaterno() + per.getPrimerNombre() + per.getSegundoNombre());
				list.add(per);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			throw(e);
		}
		
		return list;
	
	}
	
	public MpPersona findPersona(int personaId) throws Exception {
		MpPersona obj = null;
		try {
			StringBuilder SQL = new StringBuilder("SELECT per.persona_id,per.nro_docu_identidad,per.apellidos_nombres,per.razon_social,");
					SQL.append("per.ape_paterno, per.ape_materno, per.primer_nombre, per.segundo_nombre  FROM ").append(Constante.schemadb).append(".mp_persona per ");
					SQL.append("WHERE per.persona_id = ").append(personaId);
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				obj = new MpPersona();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setNroDocuIdentidad(rs.getString("nro_docu_identidad"));
				obj.setApellidosNombres(rs.getString("apellidos_nombres"));
				obj.setRazonSocial(rs.getString("razon_social"));
				obj.setApePaterno(rs.getString("ape_paterno"));
				obj.setApeMaterno(rs.getString("ape_materno"));
				obj.setPrimerNombre(rs.getString("primer_nombre"));
				obj.setSegundoNombre(rs.getString("segundo_nombre"));
			}
		} catch (Exception e) {
			throw (e);
		}
		return obj;
	}
	
	public int guardarPersona(MpPersona persona) throws Exception {
		int result = 0;
		try{
			StringBuffer stBuffer= new StringBuffer(1000);
			stBuffer.append("INSERT INTO ").append(Constante.schemadb).append(".mp_persona (persona_id, tipo_persona_id,");
			stBuffer.append("tipo_doc_identidad_id, nro_docu_identidad, ape_paterno, ape_materno, primer_nombre, segundo_nombre,");
			stBuffer.append("apellidos_nombres) VALUES(?,?,?,?,?,?,?,?,?)");
			
			PreparedStatement pstm = connect().prepareStatement(stBuffer.toString());
			pstm.setInt(1,persona.getPersonaId());
			pstm.setInt(2,persona.getTipoPersonaId());
			pstm.setInt(3,persona.getTipoDocIdentidadId());
			pstm.setString(4,persona.getNroDocuIdentidad());
			pstm.setString(5,persona.getApePaterno());
			pstm.setString(6,persona.getApeMaterno());
			pstm.setString(7,persona.getPrimerNombre());
			pstm.setString(8,persona.getSegundoNombre());
			pstm.setString(9,persona.getApellidosNombres());
			
			result = pstm.executeUpdate();
		}catch(Exception e){
			throw(e);
		}
		return result;
	}
	
	/*
	 * cchaucca:PITS
	 */
	public BuscarPersonaDTO getPersonaPapeleta(Integer personaId,Integer papeletaId,String esInfractor)throws Exception{
		BuscarPersonaDTO obj = null;
		try {
			StringBuilder SQL = new StringBuilder();
			SQL.append("SELECT p.estado,p.usuario_id,p.fecha_registro,p.terminal, p.persona_id,t.tipo_doc_identidad_id,t.descripcion tipo_doc_identidad,p.nro_doc_identidad, isnull(g.apellidos_nombres,'') apellidos_nombres,isnull(g.razon_social,'') razon_social, ");
			SQL.append("g.ape_paterno,g.ape_materno,g.primer_nombre,g.segundo_nombre, d.direccion_completa AS domicilio_completo, ");
			SQL.append("(select COUNT(*) from ").append(Constante.schemadb).append(".mp_persona mp where mp.persona_id=p.persona_id) as es_contribuyente ");  
			SQL.append(",d.direccion_id,d.tipo_via_id,d.via_id,d.lugar_id,d.sector_id,d.numero ");
			SQL.append(",p.clase_licencia_id,p.num_licencia ");
			SQL.append("FROM ").append(Constante.schemadb).append(".pa_persona p ");
			SQL.append("INNER JOIN ").append(Constante.schemadb).append(".gn_persona g ON (p.persona_id=g.persona_id) ");  
			SQL.append("left JOIN ").append(Constante.schemadb).append(".pa_direccion d ON (d.persona_id=p.persona_id and d.papeleta_id=? and d.estado='1' and d.es_infractor=? ) ");////Correccion de flag es infractor a la tabla pa_direccion  
			SQL.append("left JOIN ").append(Constante.schemadb).append(".mp_tipo_docu_identidad t ON (t.tipo_doc_identidad_id=p.tipo_doc_identidad) ");
			SQL.append("WHERE p.persona_id=? and p.estado='1'  ");
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, papeletaId);
			pst.setString(2, esInfractor);////Correccion de flag es infractor a la tabla pa_direccion
			pst.setInt(3, personaId);
			
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				obj = new BuscarPersonaDTO();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setTipodocumentoIdentidadId(rs.getInt("tipo_doc_identidad_id"));
				
				obj.setTipoDocIdentidad(rs.getString("tipo_doc_identidad"));
				obj.setNroDocuIdentidad(rs.getString("nro_doc_identidad"));
				obj.setApellidosNombres(rs.getString("apellidos_nombres"));
				
				obj.setRazonSocial(rs.getString("razon_social"));
				obj.setClaseLicenciaId(rs.getInt("clase_licencia_id"));
				obj.setNumLicencia(rs.getString("num_licencia"));
				
				obj.setApellidoPaterno(rs.getString("ape_paterno"));
				obj.setApellidoMaterno(rs.getString("ape_materno"));
				obj.setPrimerNombre(rs.getString("primer_nombre"));
				obj.setSegundoNombre(rs.getString("segundo_nombre"));
				
				obj.setDireccionCompleta(rs.getString("domicilio_completo"));
				obj.setEsContribuyente(rs.getInt("es_contribuyente")==0?Boolean.FALSE:Boolean.TRUE);
				//--
				obj.setDireccionId(rs.getInt("direccion_id"));
				obj.setTipoViaId(rs.getInt("tipo_via_id"));
				obj.setViaId(rs.getInt("via_id"));
				obj.setLugarId(rs.getInt("lugar_id"));
				obj.setSectorId(rs.getInt("sector_id"));
				obj.setNumero(rs.getString("numero"));
				
				
				obj.setTerminal(rs.getString("terminal"));
				obj.setFechaRegistro(rs.getTimestamp("fecha_registro"));				
				obj.setUsuarioId(rs.getInt("usuario_id"));
				obj.setEstado(rs.getString("estado"));
				
			}
		}catch (Exception e) {
			throw (e);
		}
		return obj;
	}
	
	public boolean getInfractorContribuyente(Integer personaId)
			throws Exception {
		BuscarPersonaDTO obj = null;
		try {
			StringBuilder SQL = new StringBuilder();
			SQL.append(" select persona_id from mp_persona where persona_id=? and estado=1");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, personaId);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				obj = new BuscarPersonaDTO();
				obj.setPersonaId(rs.getInt("persona_id"));
			}
		} catch (Exception e) {
			throw (e);
		}
		if (obj != null) {
			return true;
		} else {
			return false;
		}

	}
	
	public List<CargaLoteDTO> buscarLotes(PaCargaLote lote) throws Exception{
		List<CargaLoteDTO> list = new ArrayList<CargaLoteDTO>();
		StringBuffer SQL= new StringBuffer();
		SQL.append("select c.carga_lotes_id,c.num_oficio,c.fec_oficio,c.fec_recepcion,c.num_expediente,c.estado,c.origen,c.num_expediente,c.cantidad, ");
		SQL.append("p.cant_papeletas,p.definitivo,p.no_coincide,p.registrado,p.en_verificacion ");
		SQL.append("from ").append(Constante.schemadb).append(".pa_carga_lotes  c    ");
		SQL.append("inner join ( ");
		SQL.append("select dt.carga_lotes_id, ");
		SQL.append("COUNT(1) cant_papeletas, ");
		SQL.append("SUM(case when (pt.estado='3' or pt.estado='4') then 1 else 0 end) as definitivo, ");
		SQL.append("SUM(case when pt.estado='2' then 1 else 0 end) as no_coincide, ");
		SQL.append("SUM(case when pt.estado='1' then 1 else 0 end) as registrado, ");
		SQL.append("SUM(case when pt.estado='P' then 1 else 0 end) as en_verificacion ");
		SQL.append("from ").append(Constante.schemadb).append(".pa_carga_detalle_lotes dt  ");
		SQL.append("INNER JOIN ").append(Constante.schemadb).append(".pa_papeleta pt  on (dt.papeleta_id = pt.papeleta_id) "); 
		SQL.append("group by dt.carga_lotes_id ");
		SQL.append(") p on (p.carga_lotes_id=c.carga_lotes_id) where c.estado='1' ");
		
		if(lote.getNumOficio()!=null && !lote.getNumOficio().trim().equals("")){
			SQL.append("and c.num_oficio = ? ");
		}
		else if(lote.getFecOficio()!=null ){
			SQL.append("and CONVERT(VARCHAR,c.fec_oficio,103) = ? ");
		}
		else if(lote.getFecRecepcion()!=null){
			SQL.append("and CONVERT(VARCHAR,c.fec_recepcion,103) = ? ");
		}
		else if(lote.getNumExpediente()!=null && !lote.getNumExpediente().trim().equals("")){
			SQL.append("and c.num_expediente = ? ");
		}
		SQL.append("order by c.carga_lotes_id desc");		
		
		PreparedStatement pstm = connect().prepareStatement(SQL.toString());
		int cont=0;
		if(lote.getNumOficio()!=null && !lote.getNumOficio().trim().equals("")){
			pstm.setString(++cont, lote.getNumOficio());
		}
		else if(lote.getFecOficio()!=null ){
			pstm.setString(++cont, DateUtil.convertDateToString(lote.getFecOficio()));
		}
		else if(lote.getFecRecepcion()!=null){
			pstm.setString(++cont, DateUtil.convertDateToString(lote.getFecRecepcion()));
		}
		else if(lote.getNumExpediente()!=null && !lote.getNumExpediente().trim().equals("")){
			pstm.setString(++cont, lote.getNumExpediente());
		}
		
		ResultSet rs = pstm.executeQuery();
		while(rs.next()){
			CargaLoteDTO obj = new CargaLoteDTO();
			obj.setCargaLotesId(rs.getInt("carga_lotes_id"));
			obj.setNumOficio(rs.getString("num_oficio"));
			obj.setFecOficio(rs.getTimestamp("fec_oficio"));
			obj.setFecRecepcion(rs.getTimestamp("fec_recepcion"));
			obj.setNumExpediente(rs.getString("num_expediente"));
			obj.setCantidad(rs.getInt("cant_papeletas"));
			obj.setCantidadOficio(rs.getInt("cantidad"));
			obj.setEstado(rs.getInt("estado"));
			obj.setOrigen(rs.getString("origen"));
			//--
			obj.setCantDefinitivo(rs.getInt("definitivo"));
			obj.setCantEnVerificacion(rs.getInt("en_verificacion"));
			obj.setCantNoCoincide(rs.getInt("no_coincide"));
			obj.setCantRegistrado(rs.getInt("registrado"));
			list.add(obj);
		}
		return list;
	}
	
	public List<GridDetalleLote> buscarPapeletas(Integer cargaLotesId,String operacion) throws Exception{
		List<GridDetalleLote> lista = new ArrayList<GridDetalleLote>();
		try{
			StringBuffer SQL = new StringBuffer();
			SQL.append(" select d.carga_detalle_lotes_id,c.carga_lotes_id, c.num_oficio,  p.infraccion_id,p.nro_papeleta,per.ape_paterno,per.ape_materno,  ");
			SQL.append(" per.primer_nombre, per.segundo_nombre, p.fecha_infraccion, p.placa, p.papeleta_id,   p.estado, i.descripcion descripcion_estado,  ");
			SQL.append(" i.descripcion_corta descripcion_corta_infraccion,l.descripcion_corta descripcion_corta_ley, p.ley_id,   ");
			SQL.append(" i.infraccion_id,per.apellidos_nombres apellidos_nombres    ");
			SQL.append(" from ").append(Constante.schemadb).append(".pa_carga_detalle_lotes d   ");
			SQL.append(" INNER JOIN  ").append(Constante.schemadb).append(".pa_carga_lotes c on (c.carga_lotes_id = d.carga_lotes_id ) ");
			SQL.append(" INNER JOIN ").append(Constante.schemadb).append(".pa_papeleta p  on (d.papeleta_id = p.papeleta_id  )  ");
			SQL.append(" INNER JOIN ").append(Constante.schemadb).append(".pa_infraccion i ON (i.infraccion_id = p.infraccion_id )   "); 
			SQL.append(" INNER JOIN ").append(Constante.schemadb).append(".pa_ley l ON (i.ley_id = l.ley_id )  ");
			SQL.append(" LEFT JOIN ").append(Constante.schemadb).append(".gn_persona per on (p.persona_infractor_id = per.persona_id )   ");  
			SQL.append(" where c.carga_lotes_id=? ");
			
			if(operacion.equals(Constante.OPERACION_LOTE_PAPELETA_AGREGAR)){
				SQL.append(" and (p.estado!='9') ");	//TODOS
			}else if(operacion.equals(Constante.OPERACION_LOTE_PAPELETA_DIGITAR)){
				SQL.append(" and p.estado!='9' and p.estado!='P' and p.estado!='3' ");//SOLO REGISTRADO e INCOINCIDENTE
			}else if(operacion.equals(Constante.OPERACION_LOTE_PAPELETA_VERFICAR)){
				SQL.append(" and p.estado='2' ");//SOLO INCOINCIDENTE
			}
			SQL.append(" order by d.carga_detalle_lotes_id ");
			
			int cont=0;
			PreparedStatement pstm = connect().prepareStatement(SQL.toString());
			pstm.setInt(++cont, cargaLotesId);
			if(operacion.equals(Constante.OPERACION_LOTE_PAPELETA_AGREGAR)){
				//pstm.setString(++cont, Constante.ESTADO_PAPELETA_REGISTRADO);	
				//pstm.setString(++cont, Constante.ESTADO_PAPELETA_PENDIENTE);
			}else if(operacion.equals(Constante.OPERACION_LOTE_PAPELETA_DIGITAR)){
				//pstm.setString(++cont, Constante.ESTADO_PAPELETA_REGISTRADO);
			}else if(operacion.equals(Constante.OPERACION_LOTE_PAPELETA_VERFICAR)){
				//pstm.setString(++cont, Constante.ESTADO_PAPELETA_REGISTRADO);
			}
			
			ResultSet rs = pstm.executeQuery();
			
			while(rs.next()){
				GridDetalleLote obj = new GridDetalleLote();
				obj.setCargaDetalleLotesId(rs.getInt("carga_detalle_lotes_id"));
				obj.setCargaLotesId(rs.getInt("carga_lotes_id"));
				obj.setNumOficio(rs.getString("num_oficio"));
				obj.setNumPapeleta(rs.getString("nro_papeleta"));
				obj.setApellidoPaternoInfractor(rs.getString("ape_paterno"));
				obj.setApellidoMaternoInfractor(rs.getString("ape_materno"));
				obj.setPrimerNombreInfractor(rs.getString("primer_nombre"));
				obj.setSegundoNombreInfractor(rs.getString("segundo_nombre"));
				obj.setFechaInfraccion(rs.getTimestamp("fecha_infraccion"));
				obj.setPlaca(rs.getString("placa"));
				obj.setPapeletaId(rs.getInt("papeleta_id"));
				obj.setEstado(rs.getString("estado"));
				obj.setLey(rs.getString("descripcion_corta_ley"));
				obj.setLeyId(rs.getInt("ley_id"));
				obj.setInfraccionId(rs.getInt("infraccion_id"));
				obj.setInfraccion(rs.getString("descripcion_corta_infraccion"));
				obj.setNombresApellidosInfractor(rs.getString("apellidos_nombres"));
				//--
				if(obj.getPapeletaId()!=null&&obj.getPapeletaId()>Constante.RESULT_PENDING)
					obj.setHistorico(getDetalleCambios(obj.getPapeletaId()));
				lista.add(obj);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw(e);
		}
		
		return lista;
	}
	
	public HistoricoPapeletaDTO getDetalleCambios(Integer papeletaId)throws Exception{
		HistoricoPapeletaDTO historia=null;
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append(" SELECT i.infraccion_id,i.descripcion_corta infraccion_desc,  t.nro_papeleta, t.placa,t.persona_infractor_id,p.apellidos_nombres,t.num_licencia,CONVERT(VARCHAR,t.fecha_infraccion,103) fecha_infraccion ");
			SQL.append(" FROM  ( ");
			SQL.append(" select   ");
			SQL.append(" case when p.infraccion_id!=h.infraccion_id then h.infraccion_id else NULL end infraccion_id, ");  
			SQL.append(" case when p.nro_papeleta!=h.nro_papeleta then h.nro_papeleta else NULL end nro_papeleta,    ");
			SQL.append(" case when p.placa!=h.placa then h.placa else NULL end placa,  ");
			SQL.append(" case when p.persona_infractor_id !=h.persona_infractor_id then h.persona_infractor_id else NULL end persona_infractor_id, ");   
			SQL.append(" case when p.num_licencia!=h.num_licencia then h.num_licencia else NULL end num_licencia,   ");
			SQL.append(" case when p.fecha_infraccion !=h.fecha_infraccion then h.fecha_infraccion else NULL end fecha_infraccion ");  
			SQL.append(" from ").append(Constante.schemadb).append(".pa_papeleta p   ");
			SQL.append(" inner join ").append(Constante.schemadb).append(".pa_papeleta_historico h on (p.papeleta_id=h.papeleta_id) ");  
			SQL.append(" where h.papeleta_historico_id =(select MIN(h.papeleta_historico_id) from ").append(Constante.schemadb).append(".pa_papeleta_historico h where h.papeleta_id=p.papeleta_id)  and p.papeleta_id=? ");  
			SQL.append(" ) t   ");
			SQL.append(" left join ").append(Constante.schemadb).append(".pa_infraccion i on (t.infraccion_id=i.infraccion_id) ");  
			SQL.append(" left join ").append(Constante.schemadb).append(".gn_persona p on (p.persona_id=t.persona_infractor_id) ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1,papeletaId);
			//--
			ResultSet rs=pst.executeQuery();
			
			if(rs.next()){
				historia=new HistoricoPapeletaDTO();
				historia.setInfraccion(rs.getString("infraccion_desc"));
				historia.setNropapeleta(rs.getString("nro_papeleta"));
				historia.setPlaca_vehiculo(rs.getString("placa"));
				historia.setInfractor(rs.getString("apellidos_nombres"));
				historia.setNum_licencia(rs.getString("num_licencia"));
				historia.setFecha_infraccion(rs.getString("fecha_infraccion"));
			}
		}catch(Exception e){
			throw(e);
		}
		return historia;
	}

	public Integer getPaPapeletaHistoricoIdOrig(Integer papeletaId)throws Exception{
		Integer paPapeletaHistoricoId=Constante.RESULT_PENDING;
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append(" select MIN(h.papeleta_historico_id) papeleta_historico_id from ").append(Constante.schemadb).append(".pa_papeleta_historico h where h.papeleta_id=? ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1,papeletaId);
			//--
			ResultSet rs=pst.executeQuery();
			if(rs.next()){
				paPapeletaHistoricoId=rs.getInt("papeleta_historico_id");
			}
		}catch(Exception e){
			throw(e);
		}
		return paPapeletaHistoricoId;
	}
	
	public void generarResolucion(Integer resolucionId)throws Exception{
		try{
			StringBuffer query = new StringBuffer("{ call ").append(Constante.schemadb).append(".sp_genera_resolucion(?)}");
			 CallableStatement oCallableStatement = connect().prepareCall(query.toString());
	         oCallableStatement.setInt(1, resolucionId);
	         oCallableStatement.execute();
		 }catch(Exception e ){
			 e.printStackTrace();
		 }
	}
	
	public ArrayList<ResolucionDTO> listarResolucion(Integer resolucionId) throws Exception{
		ArrayList<ResolucionDTO> lista = new ArrayList<ResolucionDTO>();
		try{
			StringBuffer SQL = new StringBuffer();
			SQL.append(" select r.resolucion_papeleta_id,p.papeleta_id,r.numero_resolucion,CONVERT(date,r.fecha_emision,103) fecha_emision,p.persona_propietario_id, "); 
			SQL.append(" gprop.persona_id prop_persona_id,gprop.ape_paterno+' '+gprop.ape_materno prop_apellidos,gprop.primer_nombre+' '+gprop.segundo_nombre prop_nombres,").append(Constante.schemadb).append(".getDireccion(p.persona_propietario_id) prop_domicilio_completo, "); 
			SQL.append(" ginfr.persona_id infr_persona_id,ginfr.ape_paterno+' '+ginfr.ape_materno infr_apellidos,ginfr.primer_nombre+' '+ginfr.segundo_nombre infr_nombres,").append(Constante.schemadb).append(".getDireccion(p.persona_infractor_id) infr_domicilio_completo,  ");
			SQL.append(" i.descripcion_corta infraccion,p.monto_multa,c.monto_multa as monto_sancion,e.descripcion as estado,p.num_licencia,i.puntos,p.nro_papeleta,p.placa  "); 
			SQL.append(" from ").append(Constante.schemadb).append(".pa_papeleta p  "); 
			SQL.append(" inner join ").append(Constante.schemadb).append(".pa_resolucion_papeleta r on (r.papeleta_id=p.papeleta_id) ");
			SQL.append(" inner join ").append(Constante.schemadb).append(".pa_estado_resol e on (e.estado_resol_id=r.estado_resolucion_id) ");
			SQL.append(" inner join ").append(Constante.schemadb).append(".pa_infraccion i on (i.infraccion_id=p.infraccion_id)  "); 
			SQL.append(" inner join ").append(Constante.schemadb).append(".pa_incidencia c on (c.persona_id=p.persona_infractor_id and c.papeleta_id=p.papeleta_id and c.estado='1') "); 
			SQL.append(" left join ").append(Constante.schemadb).append(".pa_persona gprop on (gprop.persona_id=p.persona_propietario_id) "); 
			SQL.append(" left join ").append(Constante.schemadb).append(".pa_persona ginfr on (ginfr.persona_id=p.persona_infractor_id) "); 
			SQL.append(" where r.estado='").append(Constante.ESTADO_ACTIVO).append("' and p.estado='").append(Constante.ESTADO_PAPELETA_DEFINITIVO).append("' and r.resolucion_id=? ");
			SQL.append(" order by r.resolucion_papeleta_id asc ");
			
			PreparedStatement pstm = connect().prepareStatement(SQL.toString());
			pstm.setInt(1, resolucionId);
			
			ResultSet rs = pstm.executeQuery();
			
			while(rs.next()){
				ResolucionDTO obj = new ResolucionDTO();
				obj.setResolucionId(rs.getString("resolucion_papeleta_id"));
				obj.setPapeletaId(rs.getString("papeleta_id"));
				obj.setNroResolucion(rs.getString("numero_resolucion"));
				obj.setFechaResolucion(rs.getString("fecha_emision"));
				obj.setCodigoPropietario(rs.getString("persona_propietario_id"));
					obj.setApePropietario(rs.getString("prop_apellidos"));
					obj.setNombrePropietario(rs.getString("prop_nombres"));
					obj.setDireccion1Propietario(subString(rs.getString("prop_domicilio_completo"),Boolean.TRUE));
					obj.setDireccion2Propietario(subString(rs.getString("prop_domicilio_completo"),Boolean.FALSE));
					obj.setApellidoInfractor(rs.getString("infr_apellidos"));
					obj.setNombreInfractor(rs.getString("infr_nombres"));
					obj.setDireccion1Infractor(subString(rs.getString("infr_domicilio_completo"),Boolean.TRUE));
					obj.setDireccion2Infractor(subString(rs.getString("infr_domicilio_completo"),Boolean.FALSE));
				obj.setInfraccion(rs.getString("infraccion"));
				obj.setMontoInfraccion(rs.getString("monto_multa"));
				obj.setMontoSancion(rs.getString("monto_sancion"));
				obj.setEstadoSancion(rs.getString("estado"));
				obj.setNroLicencia(rs.getString("num_licencia"));
				obj.setPuntos(rs.getString("puntos")+" Puntos");
				obj.setNroPlaca(rs.getString("placa"));
				obj.setNroPapeleta(rs.getString("nro_papeleta"));
				//--
				obj.setPapeletaFileName(obj.getNroPapeleta()+".jpg");
				obj.setResolucionFileName(obj.getNroResolucion()+".pdf");
				
				lista.add(obj);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw(e);
		}
		return lista;
	}

	public String subString(String cadena,Boolean isLeft){
		try{
			if(cadena!=null){
				int index=(cadena.length()/2);
				int indexFrom=cadena.indexOf(" ", index);
				if(cadena.indexOf("Lugar")>0){
					indexFrom=cadena.indexOf("Lugar", 0);
				}
				
				if(isLeft){
					return cadena.substring(0, indexFrom);	
				}else{
					return cadena.substring(indexFrom,cadena.length());
				}	
			}	
		}catch(Exception e){
			return cadena;
		}
		return "";
	}
	
	public Boolean existGnPersona(Integer personaId,Integer tipoDocumento,String numeroDocumento)throws Exception{
		Boolean exist=Boolean.FALSE;
		if(numeroDocumento.equals("S/N")){
			return Boolean.FALSE;
		}
		try{
			StringBuffer stBuffer= new StringBuffer("SELECT COUNT(*) FROM ").append(Constante.schemadb).append(".gn_persona per WHERE per.estado='1' AND per.tipo_documento_id=").append(tipoDocumento).append(" and per.nro_doc_identidad='").append(numeroDocumento).append("' AND per.persona_id!=?");
			PreparedStatement pstm = connect().prepareStatement(stBuffer.toString());
			pstm.setInt(1, personaId);
			
			ResultSet rs= pstm.executeQuery();
			if(rs.next()){
				Integer result=rs.getInt(1);
				if(result>0)
					exist=Boolean.TRUE;
			}
		}catch(Exception e){
			throw(e);
		}
		return exist;
	}
	
	public Boolean existPaPersona(Integer personaId)throws Exception{
		Boolean exist=Boolean.FALSE;
		try{
			StringBuffer stBuffer= new StringBuffer("SELECT COUNT(*) FROM ").append(Constante.schemadb).append(".pa_persona per WHERE per.persona_id=? ");
			PreparedStatement pstm = connect().prepareStatement(stBuffer.toString());
			pstm.setInt(1, personaId);
			ResultSet rs= pstm.executeQuery();
			if(rs.next()){
				Integer result=rs.getInt(1);
				if(result>0)
					exist=Boolean.TRUE;
			}
		}catch(Exception e){
			throw(e);
		}
		return exist;
	}
	
	public Integer eliminarDocAnexo(Integer papeletaId)throws Exception{
		int result=0;
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append(" UPDATE ").append(Constante.schemadb).append(".pa_docu_anexo SET estado = '9' WHERE papeleta_id=? ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1,papeletaId);
			//--
			result=pst.executeUpdate();
		}catch(Exception e){
			throw(e);
		}
		return result;
	}
	
	public void auditarPersona(PaPersona paPersona,BuscarPersonaDTO paPersonaOriginal,String terminal,Integer usuarioId) throws Exception
	{
		
		if (paPersonaOriginal.getPersonaId() == null) return;
		
		Connection cn = connect();	
		
		
		 if (paPersona.getNumLicencia()== null );
		 		paPersona.setNumLicencia(paPersonaOriginal.getNumLicencia());
		 		
		 if (paPersona.getClaseLicenciaId()==null)
			 paPersona.setClaseLicenciaId(paPersonaOriginal.getClaseLicenciaId());
		 
		 if (paPersona.getRazonSocial()==null)
			 paPersona.setRazonSocial(paPersonaOriginal.getRazonSocial());
		
		try{ 
			
			StringBuilder query = new StringBuilder("{call dbo.stp_pa_actualizar_persona (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			        
	        CallableStatement cs = cn.prepareCall(query.toString());
	        
	        cs.setInt("persona_id",paPersona.getPersonaId());
	        cs.setInt("tipo_doc_identidad",paPersona.getTipoDocumentoId()); 
	        cs.setString("nro_doc_identidad",paPersona.getNroDocIdentidad());
	        
	        cs.setString("primer_nombre",paPersona.getPrimerNombre());
	        cs.setString("segundo_nombre",paPersona.getSegundoNombre());
	        
	        cs.setString("ape_paterno",paPersona.getApePaterno());
	        cs.setString("ape_materno",paPersona.getApeMaterno());	       
	        cs.setString("estado",paPersonaOriginal.getEstado());
	        cs.setString("terminal",paPersonaOriginal.getTerminal() );	         
	        cs.setInt("tipo_documento_id",paPersona.getTipoDocumentoId());
	        cs.setString("num_licencia",paPersona.getNumLicencia());//nulo
	        cs.setInt("clase_licencia_id",paPersona.getClaseLicenciaId());//nulo
	        
	        
	        cs.setString("razon_social",paPersona.getRazonSocial());//nulo
	        cs.setInt("usuario_id_cambio",usuarioId);
	        cs.setString("terminal_cambio",terminal);	  //nulo       
	        cs.setInt("tipo_doc_identidad_original",paPersonaOriginal.getTipodocumentoIdentidadId()); //getTipoDocIdentidadId()); //tipodocumentoIdentidadId	Integer  (id=463)	
	        cs.setString("nro_doc_identidad_original",paPersonaOriginal.getNroDocuIdentidad());
	        cs.setString("primer_nombre_original",paPersonaOriginal.getPrimerNombre());
	        cs.setString("segundo_nombre_original",paPersonaOriginal.getSegundoNombre());
	        cs.setString("ape_paterno_original",paPersonaOriginal.getApellidoPaterno());
	        cs.setString("ape_materno_original",paPersonaOriginal.getApellidoMaterno());
	        cs.setInt("tipo_documento_id_original",paPersonaOriginal.getTipodocumentoIdentidadId());
	        cs.setString("num_licencia_original",paPersonaOriginal.getNumLicencia());// vacio
	        cs.setInt("clase_licencia_id_original",paPersonaOriginal.getClaseLicenciaId()); 
	        cs.setString("razon_social_original",paPersonaOriginal.getRazonSocial());	        
	        cs.setString("estado_original",paPersonaOriginal.getEstado());
	        cs.execute();
	         
	         //errorMessage=cs.getString(1);
//	         System.out.println("cargaLoteId "+cargaLoteId+" / "+"procesoCargaId "+procesoCargaId);
		 }catch(Exception e ){
			 System.out.println("Error ");
			 e.printStackTrace();
			 throw (e);
		 }
		
		//return errorMessage;		
	}
	
	
	public ArrayList<ResultadoCargaLoteDTO> cantidadErrorCargaDetalleToles(Integer cargaLotesId,Integer procesoCargaId)throws Exception{
		ArrayList<ResultadoCargaLoteDTO> lista=new ArrayList<ResultadoCargaLoteDTO>(); 
		try{
			StringBuffer stBuffer= new StringBuffer();
			stBuffer.append(" SELECT d.carga_lotes_id,max(l.archivo) archivo,COUNT(CASE WHEN d.estado = '9' THEN 1 END) cantidad_error,COUNT(CASE WHEN d.estado = '1' THEN 1 END) cantidad_sucess,COUNT(*) cantidad_total ");
			stBuffer.append(" FROM ").append(Constante.schemadb).append(".pa_carga_detalle_lotes d  ");
			stBuffer.append(" inner join ").append(Constante.schemadb).append(".pa_carga_lotes l on (l.carga_lotes_id=d.carga_lotes_id) "); 
			stBuffer.append(" where d.carga_lotes_id=? ");
			stBuffer.append(" group by d.carga_lotes_id");
			PreparedStatement pstm = connect().prepareStatement(stBuffer.toString());
			pstm.setInt(1, cargaLotesId);
			
			ResultSet rs= pstm.executeQuery();
			while(rs.next()){
				ResultadoCargaLoteDTO resultado=new ResultadoCargaLoteDTO();
				resultado.setArchivoCarga(rs.getString("archivo"));
				resultado.setCantidadError(rs.getInt("cantidad_error"));
				resultado.setCantidadSubidos(rs.getInt("cantidad_sucess"));
				resultado.setTotalRegistros(rs.getInt("cantidad_total"));
				resultado.setCargaLoteId(rs.getInt("carga_lotes_id"));
				lista.add(resultado);
			}
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	
	public void actualizarEstadoPapeleta(Integer papeletaId,String estado)throws Exception{
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append(" UPDATE ").append(Constante.schemadb).append(".pa_papeleta SET estado = ? WHERE papeleta_id=? ");
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setString(1,estado);
			pst.setInt(2,papeletaId);
			pst.executeUpdate();
		}catch(Exception e){
			throw(e);
		}
	}

	public void actualizarEstadoPaDireccion(Integer papeletaId,String estado)throws Exception{
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append(" UPDATE ").append(Constante.schemadb).append(".pa_direccion SET estado = ? WHERE papeleta_id=? ");
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setString(1,estado);
			pst.setInt(2,papeletaId);
			pst.executeUpdate();
		}catch(Exception e){
			throw(e);
		}
	}
	
	public Integer getReincidencia(Integer incidenciaId,Integer personaId,Integer infraccionId,Timestamp fechaInfraccion,Integer periodo)throws Exception{
		int result=0;
		try{
//			StringBuffer SQL=new StringBuffer();
//			SQL.append(" SELECT COUNT(*) AS incidencias FROM ").append(Constante.schemadb).append(".pa_incidencia p ");
//			//SQL.append(" WHERE p.estado='1' and p.persona_id=? AND p.infraccion_id=? AND ABS(datediff(month,p.fecha_infraccion,?))<=? AND p.fecha_infraccion<=? AND p.incidencia_id!=? AND p.persona_id!="+Constante.CONDUCTOR_AUSENTE+" AND p.persona_id!="+Constante.CONDUCTOR_SIN_NOMBRE);
//			SQL.append(" WHERE p.estado='1' and p.persona_id=? AND p.infraccion_id=? AND ABS(datediff(day,p.fecha_infraccion,?))<=? AND p.fecha_infraccion<=? AND p.incidencia_id!=? AND p.persona_id!="+Constante.CONDUCTOR_AUSENTE+" AND p.persona_id!="+Constante.CONDUCTOR_SIN_NOMBRE);
//			SQL.append(" AND p.flag_firme='1' ");//Coreccion de firmeza de puntos 			
			String SQL = "stp_reincidencia_papeletas ?,?,?,?";

			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1,personaId);
			pst.setInt(2,infraccionId);
			pst.setTimestamp(3, fechaInfraccion);
//			pst.setInt(4,periodo); 
//			pst.setInt(4,365);//Un a�o se considera de 365 dias//el computo de la reincidencia es a 12 meses (1 a�o) computado en dias 365 dias, luego de cometida la infracci�n, 
//			pst.setTimestamp(4, fechaInfraccion);
			pst.setInt(4, incidenciaId);
			
			ResultSet rs=pst.executeQuery();
			if(rs.next()){
				result=rs.getInt(1);
			}
		}catch(Exception e){
			throw(e);
		}
		return result;
	}
	
	public Integer getPuntosAcum(Integer personaId,Timestamp fechaInfraccion,Integer periodo)throws Exception{
		int result=0;
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append(" SELECT SUM(isnull(p.puntos,0)) AS puntos FROM ").append(Constante.schemadb).append(".pa_incidencia p WHERE p.estado='1' and p.persona_id=? and datediff(month,p.fecha_infraccion,GETDATE())<=? AND p.fecha_infraccion<=? AND p.persona_id!="+Constante.CONDUCTOR_AUSENTE+" AND p.persona_id!="+Constante.CONDUCTOR_SIN_NOMBRE);
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1,personaId);
			pst.setInt(2,periodo);
			pst.setTimestamp(3, fechaInfraccion);
			//--
			ResultSet rs=pst.executeQuery();
			if(rs.next()){
				result=rs.getInt("puntos");
			}
		}catch(Exception e){
			throw(e);
		}
		return result;
	}
	
	public List<GnLugar> getAllGnLugar(Integer viaId)throws Exception{
		List<GnLugar> lista=new LinkedList<GnLugar>();
		try{
			StringBuffer stBuffer= new StringBuffer();
			stBuffer.append(" select distinct l.lugar_id,l.descripcion,l.usuario_id,l.estado,l.fecha_registro,l.terminal from ").append(Constante.schemadb).append(".gn_ubicacion u ");
			stBuffer.append(" inner join ").append(Constante.schemadb).append(".gn_sector_lugar sl on (sl.sector_lugar_id=u.sector_lugar_id) ");
			stBuffer.append(" inner join ").append(Constante.schemadb).append(".gn_lugar l on (sl.lugar_id=l.lugar_id) ");
			stBuffer.append(" where u.via_id=? and u.estado='").append(Constante.ESTADO_ACTIVO).append("' order by descripcion");
			
			PreparedStatement pstm = connect().prepareStatement(stBuffer.toString());
			pstm.setInt(1, viaId);
			
			ResultSet rs= pstm.executeQuery();
			while(rs.next()){
				GnLugar obj=new GnLugar();
				obj.setLugarId(rs.getInt("lugar_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				lista.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	
	public UbicacionFiscalDTO getUbicacionFiscal(Integer personaId)throws Exception{
		UbicacionFiscalDTO ubicacion=null;
		try{
			StringBuffer stBuffer= new StringBuffer();
			stBuffer.append(" select d.direccion_id,d.direccion_completa,u.via_id,v.tipo_via_id,s.lugar_id,d.numero from ").append(Constante.schemadb).append(".mp_direccion d ");
			stBuffer.append(" inner join ").append(Constante.schemadb).append(".gn_ubicacion u  on (d.ubicacion_id=u.ubicacion_id) ");
			stBuffer.append(" inner join ").append(Constante.schemadb).append(".gn_via v  on (v.via_id=u.via_id) ");
			stBuffer.append(" inner join ").append(Constante.schemadb).append(".gn_sector_lugar s on (s.sector_lugar_id=u.sector_lugar_id) ");
			stBuffer.append(" where d.persona_id =? and d.estado='1'");
			
			PreparedStatement pstm = connect().prepareStatement(stBuffer.toString());
			pstm.setInt(1, personaId);
			
			ResultSet rs= pstm.executeQuery();
			while(rs.next()){
				ubicacion=new UbicacionFiscalDTO();
				ubicacion.setDireccionId(rs.getInt("direccion_id"));
				ubicacion.setDireccionCompleta(rs.getString("direccion_completa"));
				ubicacion.setViaId(rs.getInt("via_id"));
				ubicacion.setTipoViaId(rs.getInt("tipo_via_id"));
				ubicacion.setLugarId(rs.getInt("lugar_id"));
				ubicacion.setNumero(rs.getString("numero"));
			}
		}catch(Exception e){
			throw(e);
		}
		return ubicacion;
	}
	
	public String concatenarDomicilio(PaDireccion direccion,HashMap<Integer,String> mapIGnTipoVia,HashMap<Integer,String> mapIGnVia){
		String strDireccion= "";
		String strDesDom= "";

		//'Concatenacion de Domicilio
		//'Tipo Via
		if(direccion.getTipoViaId()!=null&&direccion.getTipoViaId()>0){
			strDesDom = getDenominacion(mapIGnTipoVia,direccion.getTipoViaId()) ;
		}
			
		if(strDireccion.trim().length()>0){
			strDesDom =strDesDom+ " " + strDireccion;
		}																			
	
		if(direccion.getViaId()!=null&&direccion.getViaId()>0){
			strDesDom = strDesDom+ " " +getDenominacion(mapIGnVia,direccion.getViaId());
		}
			
		if(strDireccion.trim().length()>0){
			strDesDom =strDesDom+ " " + strDireccion;
		}
		
		//Numero 1
		if(direccion.getNumero()!=null && direccion.getNumero().trim().length()>0){
			strDireccion = "Nro "+direccion.getNumero();
		}
		
		if(strDireccion.trim().length()>0){
			strDesDom =strDesDom+ " " + strDireccion;
		}
		
		if(strDireccion.trim().length()>0){
			strDesDom =strDesDom+ ", CAJAMARCA" ;
		}
		
		return strDesDom;
	}

	private String getDenominacion(HashMap<Integer, String> map,int TipoViaId){
		return map.get(TipoViaId);
	}
	
	public ArrayList<PapeletaDTO> listarPapeletas(Integer cargaLotesId)throws Exception{
		ArrayList<PapeletaDTO> lista=new ArrayList<PapeletaDTO>();
		try{
			StringBuffer stBuffer= new StringBuffer();
			stBuffer.append(" select p.papeleta_id,p.nro_papeleta from ").append(Constante.schemadb).append(".pa_papeleta p ");
			stBuffer.append(" inner join ").append(Constante.schemadb).append(".pa_carga_detalle_lotes l on (p.papeleta_id=l.papeleta_id) ");
			stBuffer.append(" where l.carga_lotes_id=? and p.estado='").append(Constante.ESTADO_PAPELETA_PENDIENTE).append("'");
			
			PreparedStatement pstm = connect().prepareStatement(stBuffer.toString());
			pstm.setInt(1, cargaLotesId);
			
			ResultSet rs= pstm.executeQuery();
			while(rs.next()){
				PapeletaDTO obj=new PapeletaDTO();
				obj.setPapeletaId(rs.getInt("papeleta_id"));
				obj.setNroPapeleta(rs.getString("nro_papeleta"));
				lista.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	
	public ArrayList<RecordPapeletaDTO> getRecordVehiculo(String placa)throws Exception{
		ArrayList<RecordPapeletaDTO> lista=new ArrayList<RecordPapeletaDTO>();
		try{
			StringBuffer SQL= new StringBuffer();
			SQL.append(" select top 500 p.persona_infractor_id,p.persona_propietario_id,p.papeleta_id,p.placa,p.nro_papeleta,convert(varchar,p.fecha_infraccion,103) as fecha_infraccion, ");
			SQL.append(" pinfr.apellidos_nombres nombre_infractor, ");
			SQL.append(" pprop.apellidos_nombres nombre_propietario, ");
			SQL.append(" pprop.razon_social razon_social_propietario,  ");
			SQL.append(" pprop.tipo_documento_id,pprop.nro_doc_identidad, ");
			SQL.append(" ").append(Constante.schemadb).append(".getDireccion(p.persona_propietario_id) direccion_propietario, ");
			SQL.append(" i.descripcion_corta infraccion,n.descripcion nivel_gravedad,c.puntos,c.monto_multa,c.reincidente, ");
			SQL.append(" ").append(Constante.schemadb).append(".getEstadoPapeleta(p.estado) estado_papeleta, ");
			SQL.append(" rp.numero_resolucion,er.descripcion estado_resolucion ");
			SQL.append(" from ").append(Constante.schemadb).append(".pa_incidencia c ");
			SQL.append(" inner join ").append(Constante.schemadb).append(".pa_papeleta p on (c.persona_id=p.persona_infractor_id and c.papeleta_id=p.papeleta_id) ");
			SQL.append(" inner join ").append(Constante.schemadb).append(".pa_infraccion i on (i.infraccion_id=c.infraccion_id) ");
			SQL.append(" inner join ").append(Constante.schemadb).append(".pa_nivel_gravedad n on (n.nivel_gravedad_id=i.nivel_gravedad_id) ");
			
			SQL.append(" inner join ").append(Constante.schemadb).append(".gn_persona pinfr on (pinfr.persona_id=p.persona_infractor_id)  ");
			SQL.append(" left join ").append(Constante.schemadb).append(".gn_persona pprop on (pprop.persona_id=p.persona_propietario_id) ");
			
			SQL.append(" left join ").append(Constante.schemadb).append(".pa_resolucion_papeleta rp on (rp.papeleta_id=p.papeleta_id and rp.estado='1') ");
			SQL.append(" left join ").append(Constante.schemadb).append(".pa_estado_resol er on (er.estado_resol_id=rp.estado) ");
			SQL.append(" where p.placa=? and p.estado!='9' and c.estado!='9' ");
			SQL.append(" order by p.papeleta_id desc");
			
			PreparedStatement pstm = connect().prepareStatement(SQL.toString());
			pstm.setString(1, placa);
			
			ResultSet rs= pstm.executeQuery();
			Integer item=0;
			while(rs.next()){
				RecordPapeletaDTO obj=new RecordPapeletaDTO();
				obj.setItem(++item);
				obj.setPapeleId(rs.getInt("papeleta_id"));
				obj.setPlaca(rs.getString("placa"));
				obj.setNroPapeleta(rs.getString("nro_papeleta"));
				obj.setFechaInfraccion(rs.getString("fecha_infraccion"));
				obj.setApellidosNombresInfractor(rs.getString("nombre_infractor"));
				obj.setApellidosNombresPropietario(rs.getString("nombre_propietario"));
				obj.setDireccionPropietario(rs.getString("direccion_propietario"));
				obj.setInfraccion(rs.getString("infraccion"));
				obj.setNivelGravedad(rs.getString("nivel_gravedad"));
				obj.setPuntos(rs.getInt("puntos"));
				obj.setMontoMulta(rs.getDouble("monto_multa"));
				obj.setReincidencia(rs.getInt("reincidente"));
				obj.setEstadoPapeleta(rs.getString("estado_papeleta"));
				obj.setNroResolucion(rs.getString("numero_resolucion"));
				obj.setEstadoResolucion(rs.getString("estado_resolucion"));
				obj.setPersonaInfractorId(rs.getInt("persona_infractor_id"));
				obj.setPersonaPropietarioId(rs.getInt("persona_propietario_id"));
				obj.setRazonSocial(rs.getString("razon_social_propietario"));
				obj.setTipoDocumentoId(rs.getInt("tipo_documento_id"));
				obj.setNroDocumento(rs.getString("nro_doc_identidad"));
				lista.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	

	public ArrayList<RecordPapeletaDTO> getAllRecordVehiculo(String placa)throws Exception{
		ArrayList<RecordPapeletaDTO> lista=new ArrayList<RecordPapeletaDTO>();
		try{
			StringBuffer SQL= new StringBuffer();
			SQL.append(" select p.persona_infractor_id,p.persona_propietario_id,p.papeleta_id,p.placa,p.nro_papeleta,convert(varchar,p.fecha_infraccion,103) as fecha_infraccion, ");
			SQL.append(" pinfr.apellidos_nombres nombre_infractor, ");
			SQL.append(" pprop.apellidos_nombres nombre_propietario, ");
			SQL.append(" ").append(Constante.schemadb).append(".getDireccion(p.persona_propietario_id) direccion_propietario, ");
			SQL.append(" i.descripcion_corta infraccion,n.descripcion nivel_gravedad,c.puntos,c.monto_multa,c.reincidente, ");
			SQL.append(" ").append(Constante.schemadb).append(".getEstadoPapeleta(p.estado) estado_papeleta, ");
			SQL.append(" rp.numero_resolucion,er.descripcion estado_resolucion ");
			SQL.append(" from ").append(Constante.schemadb).append(".pa_incidencia c ");
			SQL.append(" inner join ").append(Constante.schemadb).append(".pa_papeleta p on (c.persona_id=p.persona_infractor_id and c.papeleta_id=p.papeleta_id) ");
			SQL.append(" inner join ").append(Constante.schemadb).append(".pa_infraccion i on (i.infraccion_id=c.infraccion_id) ");
			SQL.append(" inner join ").append(Constante.schemadb).append(".pa_nivel_gravedad n on (n.nivel_gravedad_id=i.nivel_gravedad_id) ");
			SQL.append(" inner join ").append(Constante.schemadb).append(".gn_persona pinfr on (pinfr.persona_id=p.persona_infractor_id)  ");
			SQL.append(" left join ").append(Constante.schemadb).append(".gn_persona pprop on (pprop.persona_id=p.persona_propietario_id) ");
			SQL.append(" left join ").append(Constante.schemadb).append(".pa_resolucion_papeleta rp on (rp.papeleta_id=p.papeleta_id and rp.estado='1') ");
			SQL.append(" left join ").append(Constante.schemadb).append(".pa_estado_resol er on (er.estado_resol_id=rp.estado) ");
			SQL.append(" where p.estado!=9 and p.placa=? and c.estado='1' ");
			SQL.append(" order by p.papeleta_id desc");
			
			PreparedStatement pstm = connect().prepareStatement(SQL.toString());
			pstm.setString(1, placa);
			
			ResultSet rs= pstm.executeQuery();
			Integer item=0;
			while(rs.next()){
				RecordPapeletaDTO obj=new RecordPapeletaDTO();
				obj.setItem(++item);
				obj.setPapeleId(rs.getInt("papeleta_id"));
				obj.setPlaca(rs.getString("placa"));
				obj.setNroPapeleta(rs.getString("nro_papeleta"));
				obj.setFechaInfraccion(rs.getString("fecha_infraccion"));
				obj.setApellidosNombresInfractor(rs.getString("nombre_infractor"));
				obj.setApellidosNombresPropietario(rs.getString("nombre_propietario"));
				obj.setDireccionPropietario(rs.getString("direccion_propietario"));
				obj.setInfraccion(rs.getString("infraccion"));
				obj.setNivelGravedad(rs.getString("nivel_gravedad"));
				obj.setPuntos(rs.getInt("puntos"));
				obj.setMontoMulta(rs.getDouble("monto_multa"));
				obj.setReincidencia(rs.getInt("reincidente"));
				obj.setEstadoPapeleta(rs.getString("estado_papeleta"));
				obj.setNroResolucion(rs.getString("numero_resolucion"));
				obj.setEstadoResolucion(rs.getString("estado_resolucion"));
				obj.setPersonaInfractorId(rs.getInt("persona_infractor_id"));
				obj.setPersonaPropietarioId(rs.getInt("persona_propietario_id"));
				lista.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	
	public ArrayList<RecordPapeletaDTO> getRecordInfractor(Integer tipoDocumento,String nroDocumento,String nroLicencia,Integer propietarioId,Integer infractorId)throws Exception{
		ArrayList<RecordPapeletaDTO> lista=new ArrayList<RecordPapeletaDTO>();
		try{
			StringBuffer SQL= new StringBuffer();
			SQL.append(" select TOP 500 p.persona_infractor_id,p.persona_propietario_id,p.papeleta_id,p.placa,p.nro_papeleta,convert(varchar,p.fecha_infraccion,103) as fecha_infraccion,i.descripcion_corta infraccion,n.descripcion nivel_gravedad,c.puntos,c.monto_multa,c.reincidente, ").append(Constante.schemadb).append(".getEstadoPapeleta(p.estado) estado_papeleta, ");
			SQL.append(" gninfr.apellidos_nombres nombre_infractor,pinfr.tipo_doc_identidad,pinfr.nro_doc_identidad,p.num_licencia, ");
			SQL.append(" rp.numero_resolucion,er.descripcion estado_resolucion ");
			SQL.append(" from ").append(Constante.schemadb).append(".pa_incidencia c ");
			SQL.append(" inner join ").append(Constante.schemadb).append(".pa_papeleta p on (c.persona_id=p.persona_infractor_id and c.papeleta_id=p.papeleta_id) ");
			SQL.append(" inner join ").append(Constante.schemadb).append(".pa_infraccion i on (i.infraccion_id=c.infraccion_id) ");
			SQL.append(" inner join ").append(Constante.schemadb).append(".pa_nivel_gravedad n on (n.nivel_gravedad_id=i.nivel_gravedad_id) ");
			
			SQL.append(" inner join ").append(Constante.schemadb).append(".gn_persona gninfr on (gninfr.persona_id=p.persona_infractor_id) ");
			SQL.append(" inner join ").append(Constante.schemadb).append(".pa_persona pinfr on (pinfr.persona_id=gninfr.persona_id) ");
			
			SQL.append(" left join ").append(Constante.schemadb).append(".pa_resolucion_papeleta rp on (rp.papeleta_id=p.papeleta_id and rp.estado='1') ");
			SQL.append(" left join ").append(Constante.schemadb).append(".pa_estado_resol er on (er.estado_resol_id=rp.estado) ");
			SQL.append(" where p.estado!='9' and c.estado!='9' ");
			if(tipoDocumento!=null&&tipoDocumento>0
					&&nroDocumento!=null&&nroDocumento.trim().length()>0){
				SQL.append(" and pinfr.tipo_doc_identidad=? and pinfr.nro_doc_identidad=?  ");
			}else if(nroLicencia!=null&&nroLicencia.trim().length()>0){
				SQL.append(" and p.num_licencia=?   ");	
			}else if(propietarioId!=null&&propietarioId>0){
				SQL.append(" and p.persona_propietario_id=? ");	
			}else if(infractorId!=null&&infractorId>0){
				SQL.append(" and p.persona_infractor_id=?   ");
			}
			SQL.append(" order by p.papeleta_id desc");
			
			PreparedStatement pstm = connect().prepareStatement(SQL.toString());
			if(tipoDocumento!=null&&nroDocumento!=null){
				pstm.setInt(1, tipoDocumento);
				pstm.setString(2, nroDocumento);
			}else if(nroLicencia!=null){
				pstm.setString(1, nroLicencia);
			}else if(propietarioId!=null){
				pstm.setInt(1, propietarioId);
			}else if(infractorId!=null){
				pstm.setInt(1, infractorId);
			}
			ResultSet rs= pstm.executeQuery();
			Integer item=0;
			while(rs.next()){
				RecordPapeletaDTO obj=new RecordPapeletaDTO();
				obj.setItem(++item);
				obj.setPapeleId(rs.getInt("papeleta_id"));
				obj.setPlaca(rs.getString("placa"));
				obj.setNroPapeleta(rs.getString("nro_papeleta"));
				obj.setFechaInfraccion(rs.getString("fecha_infraccion"));
				obj.setApellidosNombresInfractor(rs.getString("nombre_infractor"));
				//obj.setApellidosNombresPropietario(rs.getString("nombre_propietario"));
				//obj.setDireccionPropietario(rs.getString("direccion_propietario"));
				obj.setTipoDocumento(rs.getString("tipo_doc_identidad"));
				obj.setNroDocumento(rs.getString("nro_doc_identidad"));
				obj.setNroLicencia(rs.getString("num_licencia"));
				obj.setInfraccion(rs.getString("infraccion"));
				obj.setNivelGravedad(rs.getString("nivel_gravedad"));
				obj.setPuntos(rs.getInt("puntos"));
				obj.setMontoMulta(rs.getDouble("monto_multa"));
				obj.setReincidencia(rs.getInt("reincidente"));
				obj.setEstadoPapeleta(rs.getString("estado_papeleta"));
				obj.setNroResolucion(rs.getString("numero_resolucion"));
				obj.setEstadoResolucion(rs.getString("estado_resolucion"));
				obj.setPersonaInfractorId(rs.getInt("persona_infractor_id"));
				obj.setPersonaPropietarioId(rs.getInt("persona_propietario_id"));
				lista.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}

	public ArrayList<RecordPapeletaDTO> getAllRecordInfractor(Integer tipoDocumento,String nroDocumento,String nroLicencia,Integer propietarioId,Integer infractorId)throws Exception{
		ArrayList<RecordPapeletaDTO> lista=new ArrayList<RecordPapeletaDTO>();
		try{
			StringBuffer SQL= new StringBuffer();
			SQL.append(" select p.persona_infractor_id,p.persona_propietario_id,p.papeleta_id,p.placa,p.nro_papeleta,convert(varchar,p.fecha_infraccion,103) as fecha_infraccion,i.descripcion_corta infraccion,n.descripcion nivel_gravedad,c.puntos,c.monto_multa,c.reincidente,").append(Constante.schemadb).append(".getEstadoPapeleta(p.estado) estado_papeleta, ");
			SQL.append(" gninfr.apellidos_nombres nombre_infractor,pinfr.tipo_doc_identidad,pinfr.nro_doc_identidad,p.num_licencia, ");
			SQL.append(" rp.numero_resolucion,er.descripcion estado_resolucion ");
			SQL.append(" from ").append(Constante.schemadb).append(".pa_incidencia c ");
			SQL.append(" inner join ").append(Constante.schemadb).append(".pa_papeleta p on (c.persona_id=p.persona_infractor_id and c.papeleta_id=p.papeleta_id) ");
			SQL.append(" inner join ").append(Constante.schemadb).append(".pa_infraccion i on (i.infraccion_id=c.infraccion_id) ");
			SQL.append(" inner join ").append(Constante.schemadb).append(".pa_nivel_gravedad n on (n.nivel_gravedad_id=i.nivel_gravedad_id) ");
			
			SQL.append(" inner join ").append(Constante.schemadb).append(".gn_persona gninfr on (gninfr.persona_id=p.persona_infractor_id) ");
			SQL.append(" inner join ").append(Constante.schemadb).append(".pa_persona pinfr on (pinfr.persona_id=gninfr.persona_id) ");
			
			SQL.append(" left join ").append(Constante.schemadb).append(".pa_resolucion_papeleta rp on (rp.papeleta_id=p.papeleta_id and rp.estado='1') ");
			SQL.append(" left join ").append(Constante.schemadb).append(".pa_estado_resol er on (er.estado_resol_id=rp.estado) ");
			SQL.append(" where p.estado!=9 and c.estado='1' ");
			if(tipoDocumento!=null&&tipoDocumento>0
					&&nroDocumento!=null&&nroDocumento.trim().length()>0){
				SQL.append(" and pinfr.tipo_doc_identidad=? and pinfr.nro_doc_identidad=?  ");
			}else if(nroLicencia!=null&&nroLicencia.trim().length()>0){
				SQL.append(" and p.num_licencia=?   ");	
			}else if(propietarioId!=null&&propietarioId>0){
				SQL.append(" and p.persona_propietario_id=? ");	
			}else if(infractorId!=null&&infractorId>0){
				SQL.append(" and p.persona_infractor_id=?   ");
			}
			SQL.append(" order by p.papeleta_id desc");
			
			PreparedStatement pstm = connect().prepareStatement(SQL.toString());
			if(tipoDocumento!=null&&nroDocumento!=null){
				pstm.setInt(1, tipoDocumento);
				pstm.setString(2, nroDocumento);
			}else if(nroLicencia!=null){
				pstm.setString(1, nroLicencia);
			}else if(propietarioId!=null){
				pstm.setInt(1, propietarioId);
			}else if(infractorId!=null){
				pstm.setInt(1, infractorId);
			}
			ResultSet rs= pstm.executeQuery();
			Integer item=0;
			while(rs.next()){
				RecordPapeletaDTO obj=new RecordPapeletaDTO();
				obj.setItem(++item);
				obj.setPapeleId(rs.getInt("papeleta_id"));
				obj.setPlaca(rs.getString("placa"));
				obj.setNroPapeleta(rs.getString("nro_papeleta"));
				obj.setFechaInfraccion(rs.getString("fecha_infraccion"));
				obj.setApellidosNombresInfractor(rs.getString("nombre_infractor"));
				//obj.setApellidosNombresPropietario(rs.getString("nombre_propietario"));
				//obj.setDireccionPropietario(rs.getString("direccion_propietario"));
				obj.setTipoDocumento(rs.getString("tipo_doc_identidad"));
				obj.setNroDocumento(rs.getString("nro_doc_identidad"));
				obj.setNroLicencia(rs.getString("num_licencia"));
				obj.setInfraccion(rs.getString("infraccion"));
				obj.setNivelGravedad(rs.getString("nivel_gravedad"));
				obj.setPuntos(rs.getInt("puntos"));
				obj.setMontoMulta(rs.getDouble("monto_multa"));
				obj.setReincidencia(rs.getInt("reincidente"));
				obj.setEstadoPapeleta(rs.getString("estado_papeleta"));
				obj.setNroResolucion(rs.getString("numero_resolucion"));
				obj.setEstadoResolucion(rs.getString("estado_resolucion"));
				obj.setPersonaInfractorId(rs.getInt("persona_infractor_id"));
				obj.setPersonaPropietarioId(rs.getInt("persona_propietario_id"));
				lista.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	
	/*datos del infractor : inicio*/
	public BuscarPersonaDTO getInfractorByPersonaId(Integer personaId)throws Exception{
		BuscarPersonaDTO obj = null;
		try {
			StringBuilder SQL = new StringBuilder();
			SQL.append(" SELECT p.persona_id,ISNULL(t.tipo_doc_identidad_id,1) tipo_doc_identidad_id,ISNULL(t.descripcion,'') tipo_doc_identidad,ISNULL(p.nro_doc_identidad,'') nro_doc_identidad, "); 
			SQL.append(" ISNULL(p.ape_paterno,'') ape_paterno,ISNULL(p.ape_materno,'') ape_materno,ISNULL(p.primer_nombre,'') primer_nombre,ISNULL(p.segundo_nombre,'') segundo_nombre, ISNULL(p.apellidos_nombres,'') apellidos_nombres,ISNULL(s.num_licencia,'') num_licencia  ");
			SQL.append(" FROM dbo.gn_persona p  ");
			SQL.append(" LEFT JOIN dbo.pa_persona s ON (s.persona_id=p.persona_id) ");
			SQL.append(" LEFT JOIN dbo.mp_tipo_docu_identidad t ON (t.tipo_doc_identidad_id=p.tipo_documento_id) ");    
			SQL.append(" WHERE p.persona_id=? and p.estado='1' ");
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, personaId);
			
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				obj = new BuscarPersonaDTO();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setTipodocumentoIdentidadId(rs.getInt("tipo_doc_identidad_id"));
				obj.setTipoDocIdentidad(rs.getString("tipo_doc_identidad"));
				obj.setNroDocuIdentidad(rs.getString("nro_doc_identidad"));
				obj.setApellidosNombres(rs.getString("apellidos_nombres"));
				
				obj.setApellidoPaterno(rs.getString("ape_paterno"));
				obj.setApellidoMaterno(rs.getString("ape_materno"));
				obj.setPrimerNombre(rs.getString("primer_nombre"));
				obj.setSegundoNombre(rs.getString("segundo_nombre"));
				
				obj.setNumLicencia(rs.getString("num_licencia"));
			}
		}catch (Exception e) {
			throw (e);
		}
		return obj;
	}
	
	public BuscarPersonaDTO getInfractorByDocumento(Integer tipoDocumento,String nroDocumentoIdentidad)throws Exception{
		BuscarPersonaDTO obj = null;
		try {
			StringBuilder SQL = new StringBuilder();
			SQL.append(" SELECT p.persona_id,ISNULL(t.tipo_doc_identidad_id,1) tipo_doc_identidad_id,ISNULL(t.descripcion,'') tipo_doc_identidad,ISNULL(p.nro_doc_identidad,'') nro_doc_identidad, "); 
			SQL.append(" ISNULL(p.ape_paterno,'') ape_paterno,ISNULL(p.ape_materno,'') ape_materno,ISNULL(p.primer_nombre,'') primer_nombre,ISNULL(p.segundo_nombre,'') segundo_nombre, ISNULL(p.apellidos_nombres,'') apellidos_nombres,ISNULL(s.num_licencia,'') num_licencia  ");
			SQL.append(" FROM dbo.gn_persona p  ");
			SQL.append(" LEFT JOIN dbo.pa_persona s ON (s.persona_id=p.persona_id) ");
			SQL.append(" LEFT JOIN dbo.mp_tipo_docu_identidad t ON (t.tipo_doc_identidad_id=p.tipo_documento_id) ");    
			SQL.append(" WHERE t.tipo_doc_identidad_id=? and p.nro_doc_identidad=? and p.estado='1' ");
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, tipoDocumento);
			pst.setString(2, nroDocumentoIdentidad);
			
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				obj = new BuscarPersonaDTO();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setTipodocumentoIdentidadId(rs.getInt("tipo_doc_identidad_id"));
				obj.setTipoDocIdentidad(rs.getString("tipo_doc_identidad"));
				obj.setNroDocuIdentidad(rs.getString("nro_doc_identidad"));
				obj.setApellidosNombres(rs.getString("apellidos_nombres"));
				
				obj.setApellidoPaterno(rs.getString("ape_paterno"));
				obj.setApellidoMaterno(rs.getString("ape_materno"));
				obj.setPrimerNombre(rs.getString("primer_nombre"));
				obj.setSegundoNombre(rs.getString("segundo_nombre"));
				
				obj.setNumLicencia(rs.getString("num_licencia"));
			}
		}catch (Exception e) {
			throw (e);
		}
		return obj;
	}
	
	public BuscarPersonaDTO getInfractorByLicencia(String nroLicencia)throws Exception{
		BuscarPersonaDTO obj = null;
		try {
			StringBuilder SQL = new StringBuilder();
			SQL.append(" SELECT p.persona_id,ISNULL(t.tipo_doc_identidad_id,1) tipo_doc_identidad_id,ISNULL(t.descripcion,'') tipo_doc_identidad,ISNULL(p.nro_doc_identidad,'') nro_doc_identidad, "); 
			SQL.append(" ISNULL(p.ape_paterno,'') ape_paterno,ISNULL(p.ape_materno,'') ape_materno,ISNULL(p.primer_nombre,'') primer_nombre,ISNULL(p.segundo_nombre,'') segundo_nombre, ISNULL(p.apellidos_nombres,'') apellidos_nombres,ISNULL(s.num_licencia,'') num_licencia  ");
			SQL.append(" FROM dbo.gn_persona p  ");
			SQL.append(" LEFT JOIN dbo.pa_persona s ON (s.persona_id=p.persona_id) ");
			SQL.append(" LEFT JOIN dbo.mp_tipo_docu_identidad t ON (t.tipo_doc_identidad_id=p.tipo_documento_id) ");    
			SQL.append(" WHERE s.num_licencia=? and p.estado='1' ");
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setString(1, nroLicencia);
			
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				obj = new BuscarPersonaDTO();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setTipodocumentoIdentidadId(rs.getInt("tipo_doc_identidad_id"));
				obj.setTipoDocIdentidad(rs.getString("tipo_doc_identidad"));
				obj.setNroDocuIdentidad(rs.getString("nro_doc_identidad"));
				obj.setApellidosNombres(rs.getString("apellidos_nombres"));
				
				obj.setApellidoPaterno(rs.getString("ape_paterno"));
				obj.setApellidoMaterno(rs.getString("ape_materno"));
				obj.setPrimerNombre(rs.getString("primer_nombre"));
				obj.setSegundoNombre(rs.getString("segundo_nombre"));
				
				obj.setNumLicencia(rs.getString("num_licencia"));
			}
		}catch (Exception e) {
			throw (e);
		}
		return obj;
	}
	
	public PaDireccion getPaDireccion(Integer personaId)throws Exception{
		PaDireccion obj = null;
		try {
			StringBuilder SQL = new StringBuilder();
			SQL.append(" SELECT TOP 1 a.direccion_id,a.tipo_via_id,a.via_id,a.lugar_id,a.sector_id,a.direccion_completa,a.numero,a.persona_id,a.papeleta_id,a.estado "); 
			SQL.append(" FROM dbo.pa_direccion a  ");
			SQL.append(" INNER JOIN dbo.pa_papeleta p ON (a.papeleta_id=p.papeleta_id) ");
			SQL.append(" WHERE a.persona_id=? AND a.estado='1' ");
			SQL.append(" ORDER BY p.fecha_infraccion DESC ");
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, personaId);
			
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				obj = new PaDireccion();
				obj.setDireccionId(rs.getInt("direccion_id"));
				obj.setTipoViaId(rs.getInt("tipo_via_id"));
				obj.setViaId(rs.getInt("via_id"));
				obj.setLugarId(rs.getInt("lugar_id"));
				obj.setSectorId(rs.getInt("sector_id"));
				obj.setDireccionCompleta(rs.getString("direccion_completa"));
				obj.setNumero(rs.getString("numero"));
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setPapeletaId(rs.getInt("papeleta_id"));
				obj.setEstado(rs.getString("estado"));
			}
		}catch (Exception e) {
			throw (e);
		}
		return obj;
	}
	
	/*datos del infractor : fin*/
	
	public List<ConsultaPapeletaDTO> consultarPapeletas(String nroPapeleta, Date fechaInicio, Date fechaFin)throws SisatException{		
		
		List<ConsultaPapeletaDTO> consultaPapeletaDTOs = new ArrayList<ConsultaPapeletaDTO>();
		
		try {
			CallableStatement cs = connect().prepareCall("{call stp_pa_consulta_papeleta(?,?,?)}");
			if(nroPapeleta !=null){
				cs.setString(1, nroPapeleta);
			}else{
				cs.setNull(1, java.sql.Types.NULL);
			}
			
			if(fechaInicio != null){
				cs.setTimestamp(2, new Timestamp(fechaInicio.getTime()));
			}else{
				cs.setNull(2, java.sql.Types.NULL);
			}
			if(fechaFin != null){
				cs.setTimestamp(3, new Timestamp(fechaFin.getTime()));
			}else{
				cs.setNull(3, java.sql.Types.NULL);
			}
			
			ResultSet rs = cs.executeQuery();
			while (rs.next()) {
				ConsultaPapeletaDTO consultaPapeletaDTO = new ConsultaPapeletaDTO();
				
				consultaPapeletaDTO.setNroPapeleta(rs.getString("nro_papeleta"));
				consultaPapeletaDTO.setFechaInfraccion(rs.getDate("fecha_infraccion"));
				consultaPapeletaDTO.setCodigo(rs.getString("codigo"));
				consultaPapeletaDTO.setInfractor(rs.getString("infractor"));
				consultaPapeletaDTO.setTipoDoc(rs.getString("tipo_doc"));
				consultaPapeletaDTO.setNroDoc(rs.getString("num_doc"));
				consultaPapeletaDTO.setPlaca(rs.getString("placa"));
				consultaPapeletaDTO.setInfraccion(rs.getString("infraccion"));
				consultaPapeletaDTO.setEstado(rs.getString("estado"));
				consultaPapeletaDTO.setNroResolucion(rs.getString("nro_resolucion"));
				consultaPapeletaDTO.setFechaNotificacion(rs.getDate("fecha_notificacion")==null?null:new Date(rs.getDate("fecha_notificacion").getTime()));
				consultaPapeletaDTO.setEstadoResolucion(rs.getString("estado_resolucion"));
				consultaPapeletaDTO.setMontoMulta(rs.getBigDecimal("monto_multa"));
				consultaPapeletaDTO.setMontoPago(rs.getBigDecimal("monto_pagado"));
				
				if (rs.getTimestamp("fecha_pago") != null) {
					consultaPapeletaDTO.setFechaPago(new Date(rs.getTimestamp("fecha_pago").getTime()));
				} else {
					consultaPapeletaDTO.setFechaPago(null);
				}
				if (rs.getInt("recibo_id") > 0) {
					consultaPapeletaDTO.setReciboId(rs.getInt("recibo_id"));
				} else {
					consultaPapeletaDTO.setReciboId(null);
				}
				if (rs.getInt("lote_id") > 0) {
					consultaPapeletaDTO.setLoteId(rs.getInt("lote_id"));
				} else {
					consultaPapeletaDTO.setLoteId(null);
				}
				consultaPapeletaDTO.setNroRecibo(rs.getString("nro_recibo"));
				if (rs.getTimestamp("fecha_actualizacion") != null) {
					consultaPapeletaDTO.setFechaActualizacion(new Date(rs.getTimestamp("fecha_actualizacion").getTime()));
				} else {
					consultaPapeletaDTO.setFechaActualizacion(null);
				}	
				consultaPapeletaDTO.setResponsable(rs.getString("responsable"));
				
				consultaPapeletaDTOs.add(consultaPapeletaDTO);				
			}
		} catch (Exception ex) {			
			throw new SisatException(ex.getMessage());
		}
		
		return consultaPapeletaDTOs;
	}
	
	public List<ConsultaPapeletaDTO> consultarPapeletaDescargo(String nroPapeleta,Date fechaInicio, Date fechaFin)throws Exception{
		List<ConsultaPapeletaDTO> list =  new ArrayList<ConsultaPapeletaDTO>();

		StringBuilder SQL = new StringBuilder("dbo.stp_pa_obtener_condicion_deuda ?,?,?");
		
		PreparedStatement pst = connect().prepareStatement(SQL.toString());
		pst.setString(1, nroPapeleta);
		
		if(nroPapeleta !=null){pst.setString(1, nroPapeleta);}else{pst.setNull(1, java.sql.Types.NULL);}
		if(fechaInicio != null){pst.setTimestamp(2, new Timestamp(fechaInicio.getTime()));}else{pst.setNull(2, java.sql.Types.NULL);}
		if(fechaFin != null){pst.setTimestamp(3, new Timestamp(fechaFin.getTime()));}else{pst.setNull(3, java.sql.Types.NULL);}

		ResultSet rs = pst.executeQuery();
		
			while (rs.next()) {
				ConsultaPapeletaDTO obj = new ConsultaPapeletaDTO();
					obj.setTipoDescargo(rs.getString("tipo_descargo"));
					obj.setCodigo(rs.getString("persona_id"));
					obj.setTipoDoc(rs.getString("tipo_documento"));
					obj.setNroDoc(rs.getString("nro_documento"));
					obj.setDescripcionDescargo(rs.getString("observacion"));
					obj.setMontoMulta(rs.getBigDecimal("total_deuda"));
					obj.setFechaDocumentoDescargo(rs.getDate("fecha_documento"));
					obj.setResponsable(rs.getString("nombre_usuario"));
					obj.setFechaActualizacion(rs.getDate("fecha_registro"));
					obj.setNroPapeleta(rs.getString("nro_papeleta"));;
					list.add(obj);
			}
			return list;
	}	
	
	public BigDecimal obtenerSancionInfraccion(Integer infraccionId, Integer incidencia) throws Exception{
		BigDecimal obj=BigDecimal.ZERO;
			StringBuilder SQL = new StringBuilder("dbo.stp_pa_obtener_sancion_infraccion ?,?");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, infraccionId);
			pst.setInt(2, incidencia);
			ResultSet rs = pst.executeQuery();
			if(rs.next()){
				obj = rs.getBigDecimal("sancion");	
			}
			return obj;
	}
	
	public int guardarOperacionAuditoria (GnAuditoriaOperacion auditoria) throws Exception {
		int salida = 0;

		try {
			CallableStatement cs = connect().prepareCall(  "{call dbo.spt_generaOperacionAuditoria(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				if(auditoria.getTablaId()!=null){
					cs.setInt(1,auditoria.getTablaId());
				}else{
					cs.setInt(1,0);
				}
					cs.setString(2,auditoria.getTablaNombre());
				if(auditoria.getPersonaId()!=null){
					cs.setInt(3,auditoria.getPersonaId());
				}else{
					cs.setInt(3,0);
				}
							
				cs.setString(4,auditoria.getContribuyente());
				if(auditoria.getTipoDocumentoId()!=null){
					cs.setInt(5,auditoria.getTipoDocumentoId());
				}else{
					cs.setInt(5,0);
				}
				cs.setString(6,auditoria.getNroDocIdentidad());
				cs.setString(7,auditoria.getCodigoOperacion());
				if(auditoria.getPredioId()!=null){
					cs.setInt(8,auditoria.getPredioId());
				}else{
					cs.setInt(8,0);
				}
				cs.setString(9,auditoria.getPlaca());
				if(auditoria.getMotivoDescargoId()!=null){
					cs.setInt(10,auditoria.getMotivoDescargoId());
				}else{
					cs.setInt(10,0);
				}
				if(auditoria.getMotivoDeclaracionId()!=null){
				cs.setInt(11,auditoria.getMotivoDeclaracionId());
				}else{
					cs.setInt(11,0);
				}
				cs.setString(12,auditoria.getEstado());
				if(auditoria.getAnio()!=null){
					cs.setInt(13,auditoria.getAnio());
				}else{
					cs.setInt(13,0);
				}
				
				if(auditoria.getMonto()!=null){
					cs.setDouble(14,auditoria.getMonto());
				}else{
					cs.setDouble(14,0);
				}
				cs.setInt(15,auditoria.getTipoOperacion());
				cs.setInt(16,auditoria.getUsuarioId());
				cs.setString(17,auditoria.getTerminalRegistro());

				cs.execute();
			
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
		}
		return salida;
	}


}