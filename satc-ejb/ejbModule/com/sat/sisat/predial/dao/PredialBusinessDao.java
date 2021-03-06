package com.sat.sisat.predial.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.sat.sisat.common.dao.GeneralDao;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.Util;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.persistence.entity.GnLugar;
import com.sat.sisat.persistence.entity.GnSector;
import com.sat.sisat.persistence.entity.GnTipoAgrupamiento;
import com.sat.sisat.persistence.entity.GnTipoDenoUrbana;
import com.sat.sisat.persistence.entity.GnTipoEdificacion;
import com.sat.sisat.persistence.entity.GnTipoIngreso;
import com.sat.sisat.persistence.entity.GnTipoInterior;
import com.sat.sisat.persistence.entity.GnTipoVia;
import com.sat.sisat.persistence.entity.GnVia;
import com.sat.sisat.persistence.entity.MpCondiEspePredio;
import com.sat.sisat.persistence.entity.RjComponenteConstruccion;
import com.sat.sisat.persistence.entity.RjDocuAnexo;
import com.sat.sisat.persistence.entity.RjEstadoConservacion;
import com.sat.sisat.persistence.entity.RjMes;
import com.sat.sisat.persistence.entity.RjTipoDepreciacion;
import com.sat.sisat.persistence.entity.RpAltitud;
import com.sat.sisat.persistence.entity.RpCategoriaObra;
import com.sat.sisat.persistence.entity.RpCategoriaRustico;
import com.sat.sisat.persistence.entity.RpCondicionPropiedad;
import com.sat.sisat.persistence.entity.RpDjarbitrio;
import com.sat.sisat.persistence.entity.RpDjconstruccion;
import com.sat.sisat.persistence.entity.RpDjdireccion;
import com.sat.sisat.persistence.entity.RpDjpredial;
import com.sat.sisat.persistence.entity.RpDjuso;
import com.sat.sisat.persistence.entity.RpDjusoDetalle;
import com.sat.sisat.persistence.entity.RpInstalacionDj;
import com.sat.sisat.persistence.entity.RpMaterialPredominante;
import com.sat.sisat.persistence.entity.RpOtrosFrente;
import com.sat.sisat.persistence.entity.RpTipoAdquisicion;
import com.sat.sisat.persistence.entity.RpTipoNivel;
import com.sat.sisat.persistence.entity.RpTipoObra;
import com.sat.sisat.persistence.entity.RpTipoTierraRustico;
import com.sat.sisat.persistence.entity.RpTipoUso;
import com.sat.sisat.persistence.entity.RpUbicacionPredio;
import com.sat.sisat.persistence.entity.TgTabla;
import com.sat.sisat.predial.dto.FindDireccion;
import com.sat.sisat.predial.dto.FindPersona;
import com.sat.sisat.predial.dto.FindRpDjPredial;
import com.sat.sisat.predial.dto.FindRpTipoObraDTO;
import com.sat.sisat.predial.dto.FotoPredioConstruccionesDTO;
import com.sat.sisat.predial.dto.FotoPredioDTO;
import com.sat.sisat.predial.dto.FotoPredioInspeccionDTO;
import com.sat.sisat.predial.dto.FotoPredioInstalacionesDTO;
import com.sat.sisat.predial.dto.ListRpDjPredial;
import com.sat.sisat.predial.dto.RelacionadosDTO;
import com.sat.sisat.predial.dto.UbicacionDTO;
import com.sat.sisat.vehicular.dto.AnexosDeclaVehicDTO;
import com.sat.sisat.vehicular.dto.BuscarPersonaDTO;

public class PredialBusinessDao extends GeneralDao{
	
	public ArrayList<FindRpDjPredial> getRpDjpredial(String apellidosNombres,String razonSocial,Integer tipoDocIdentidad,String numeroDocIdentidad,String codigoPredio,
			Integer tipoViaId,Integer viaId,Integer sectorId,Integer lugarId,String direccion,
			Integer djId,Integer personaId,Boolean esPropietario)throws Exception{
		ArrayList<FindRpDjPredial> lista=new ArrayList<FindRpDjPredial>();
		
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append(" select j.anno_dj,j.dj_id,tdi.descrpcion_corta tipo_documento,pe.nro_docu_identidad,stp.descripcion_corta tipo_persona,pe.apellidos_nombres,pe.razon_social,j.tipo_predio,p.codigo_predio,j.porc_propiedad, ");
			SQL.append(" tv.descripcion tipo_via,v.descripcion via,u.numero_manzana,u.numero_cuadra,u.lado_cuadra,s.descripcion sector,lu.descripcion lugar, isnull(d.direccion_completa,j.desc_domicilio) direccion_completa, ");
			SQL.append(" pe.persona_id,p.predio_id,p.codigo_anterior,j.fecha_declaracion,convert(varchar(10),j.fecha_adquisicion,103) fecha_adquisicion,(case j.tipo_predio when 'U' then 'Urbano' when 'R' then 'Rustico' else NULL end) as tipo_predio_desc,s.sector_id,tdi.tipo_doc_identidad_id,stp.tipo_persona_id,v.distrito_id, ");
			SQL.append(" j.motivo_declaracion_id,m.descripcion motivo_declaracion,j.fiscalizado,pe.ape_materno,pe.ape_paterno,pe.primer_nombre,pe.segundo_nombre,j.estado estado_dj, pe.telefono ");
			SQL.append(" ,c.descripcion condicion_propiedad, v.via_id, tv.tipo_via_id, j.area_terreno ,j.area_terreno_has,u.ubicacion_id, j.motivo_descargo_id, d.nombre_edificiacion ");
			SQL.append(" from ").append(Constante.schemadb).append(".rp_djpredial j  ");
			SQL.append(" inner join ").append(Constante.schemadb).append(".mp_predio p on (p.predio_id=j.predio_id) ");  
			SQL.append(" inner join ").append(Constante.schemadb).append(".rv_motivo_declaracion m on (m.motivo_declaracion_id=j.motivo_declaracion_id) ");
			SQL.append(" inner join ").append(Constante.schemadb).append(".rp_condicion_propiedad c on (c.condicion_propiedad_id=j.condicion_propiedad_id) ");
			SQL.append(" left join ").append(Constante.schemadb).append(".rp_djdireccion d on (d.dj_id=j.dj_id and d.estado='1') ");
			SQL.append(" left join ").append(Constante.schemadb).append(".gn_ubicacion u on (u.ubicacion_id=d.ubicacion_id)  ");
			SQL.append(" left join ").append(Constante.schemadb).append(".gn_via v on(v.via_id=d.via_id) ");
			SQL.append(" left join ").append(Constante.schemadb).append(".gn_tipo_via tv on(tv.tipo_via_id=v.tipo_via_id) ");     
			SQL.append(" left join ").append(Constante.schemadb).append(".gn_sector_lugar sl on(sl.sector_lugar_id=u.sector_lugar_id) ");  
			SQL.append(" left join ").append(Constante.schemadb).append(".gn_sector s on(s.sector_id=sl.sector_id)  ");
			SQL.append(" left join ").append(Constante.schemadb).append(".gn_lugar lu on(lu.lugar_id=sl.lugar_id)  ");
			SQL.append(" inner join ").append(Constante.schemadb).append(".mp_persona pe on (pe.persona_id=j.persona_id) "); 
			SQL.append(" inner join ").append(Constante.schemadb).append(".mp_tipo_docu_identidad tdi on (pe.tipo_doc_identidad_id=tdi.tipo_doc_identidad_id) "); 
			SQL.append(" inner join ").append(Constante.schemadb).append(".mp_tipo_persona stp on (pe.tipo_persona_id=stp.tipo_persona_id)   ");
			SQL.append(" inner join (  ");
			SQL.append(" SELECT MAX(p.dj_id) dj_anno_id,p.predio_id,p.persona_id FROM ").append(Constante.schemadb).append(".rp_djpredial p WHERE ( (p.estado='").append(Constante.ESTADO_ACTIVO).append("' and p.flag_dj_anno='").append(Constante.FLAG_DJ_ANIO_ACTIVO).append("' )");  
			SQL.append("or p.estado='").append(Constante.ESTADO_PENDIENTE).append("' ) ");
			SQL.append(" and p.anno_dj= ").append("(SELECT MAX (rp.anno_dj) FROM ").append(Constante.schemadb).append(".rp_djpredial rp WHERE rp.estado = p.estado and rp.flag_dj_anno = p.flag_dj_anno and rp.persona_id = p.persona_id  and rp.predio_id = p.predio_id ) ");
			SQL.append(" group by p.predio_id,p.persona_id  ");
			SQL.append(" ) ult_dj on (ult_dj.persona_id=j.persona_id and ult_dj.predio_id=j.predio_id and ult_dj.dj_anno_id=j.dj_id) "); 
			if(esPropietario){
				SQL.append(" where ((j.flag_dj_anno='").append(Constante.FLAG_DJ_ANIO_ACTIVO).append("' and j.estado='").append(Constante.ESTADO_ACTIVO).append("') or ( j.estado='").append(Constante.ESTADO_PENDIENTE).append("')) ");
			}else{
				SQL.append(" where j.flag_dj_anno='").append(Constante.FLAG_DJ_ANIO_ACTIVO).append("' and j.estado='").append(Constante.ESTADO_ACTIVO).append("' and j.motivo_declaracion_id!=4 ");	
			}
			if(personaId!=null&&personaId>0){
				SQL.append(" and pe.persona_id=").append(String.valueOf(personaId));
			}
			if(apellidosNombres!=null&&apellidosNombres.trim().length()>0){
				SQL.append(" and pe.apellidos_nombres like '").append(apellidosNombres).append("%' ");
			}else if(razonSocial!=null&&razonSocial.trim().length()>0){
				SQL.append(" and pe.razon_social like '%"+razonSocial+"%' ");
			}else if(tipoDocIdentidad!=null&&tipoDocIdentidad>0&&numeroDocIdentidad!=null&&numeroDocIdentidad.trim().length()>0){
				SQL.append(" and pe.tipo_doc_identidad_id=? and pe.nro_docu_identidad=? ");
			}else if(codigoPredio!=null&&codigoPredio.trim().length()>0){
				SQL.append(" and p.predio_id=? ");
			}else if(tipoViaId!=null&&tipoViaId>0&&viaId!=null&&viaId>0){
				SQL.append(" and tv.tipo_via_id=? and v.via_id=? ");
			}else if(sectorId!=null&&sectorId>0&&lugarId!=null&&lugarId>0){
				SQL.append(" and s.sector_id=? and lu.lugar_id=? ");
			}else if(direccion!=null&&direccion.trim().length()>0){
				SQL.append(" and d.direccion_completa like '%").append(direccion).append("%' ");
			}else if(djId!=null&&djId>0){
				SQL.append(" and j.dj_id=? ");
			}
			SQL.append(" order by j.predio_id asc, j.dj_id desc ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			if(apellidosNombres!=null&&apellidosNombres.trim().length()>0){
				;
			}else if(razonSocial!=null&&razonSocial.trim().length()>0){
				;
			}else if(tipoDocIdentidad!=null&&tipoDocIdentidad>0&&numeroDocIdentidad!=null&&numeroDocIdentidad.trim().length()>0){
				pst.setInt(1, tipoDocIdentidad);
				pst.setString(2, numeroDocIdentidad);
			}else if(codigoPredio!=null&&codigoPredio.trim().length()>0){
				pst.setString(1, codigoPredio);
			}else if(tipoViaId!=null&&tipoViaId>0&&viaId!=null&&viaId>0){
				pst.setInt(1, tipoViaId);
				pst.setInt(2, viaId);
			}else if(sectorId!=null&&sectorId>0&&lugarId!=null&&lugarId>0){
				pst.setInt(1, sectorId);
				pst.setInt(2, lugarId);
			}else if(direccion!=null&&direccion.trim().length()>0){
				;
			}else if(djId!=null&&djId>0){
				pst.setInt(1, djId);
			}
			
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				FindRpDjPredial obj=new FindRpDjPredial();
				obj.setPredioId(rs.getInt("predio_id"));
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setDjId(rs.getInt("dj_id"));
				obj.setCodigoAnterior(rs.getString("codigo_anterior"));
				obj.setFechaDeclaracion(rs.getTimestamp("fecha_declaracion"));
				obj.setFechaAdquisicion(rs.getString("fecha_adquisicion"));
				obj.setPorcPropiedad(rs.getBigDecimal("porc_propiedad"));
				obj.setTipoPredio(rs.getString("tipo_predio"));
				obj.setTipoPredioDesc(rs.getString("tipo_predio_desc"));
				obj.setCodigoPredio(rs.getString("codigo_predio"));
				obj.setDireccionCompleta(rs.getString("direccion_completa"));
				obj.setNombreEdificiacion(rs.getString("nombre_edificiacion"));			
				obj.setMotivoDeclaracion(rs.getString("motivo_declaracion"));
				obj.setMotivoDeclaracionId(rs.getString("motivo_declaracion_id"));
				obj.setSectorId(rs.getInt("sector_id"));
				obj.setManzana(rs.getString("numero_manzana"));
				obj.setSector(rs.getString("sector"));
				obj.setFiscalizado(rs.getString("fiscalizado"));
				obj.setAnioDj(rs.getString("anno_dj"));
				obj.setDescripcionDocIdentidad(rs.getString("tipo_documento"));
				obj.setNumeroDocIdentidad(rs.getString("nro_docu_identidad"));
				obj.setDescripcionTipoPersona(rs.getString("tipo_persona"));
				obj.setApellidosNombres(rs.getString("apellidos_nombres"));
				obj.setRazonSocial(rs.getString("razon_social"));
				obj.setTipoVia(rs.getString("tipo_via"));
				obj.setViadescripcion(rs.getString("via"));
				obj.setManzana(rs.getString("numero_manzana"));
				obj.setCuadra(rs.getString("numero_cuadra"));
				obj.setLado(rs.getString("lado_cuadra"));
				obj.setSector(rs.getString("sector"));				
				obj.setEstado(rs.getString("estado_dj"));
				obj.setCondicionPropiedad(rs.getString("condicion_propiedad"));
				obj.setTipoDocIdentidad(rs.getInt("tipo_doc_identidad_id"));
				obj.setTelefonoPersona(rs.getString("telefono"));
				obj.setTipoViaId(rs.getInt("tipo_via_id"));
				obj.setViaId(rs.getInt("via_id"));
				obj.setAreaTerreno(rs.getDouble("area_terreno"));
				obj.setAreaTerrenoHas(rs.getDouble("area_terreno_has"));
				obj.setUbicacionId(rs.getInt("ubicacion_id"));
				obj.setMotivoDescargoId(rs.getInt("motivo_descargo_id"));
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
			StringBuffer SQL=new StringBuffer("SELECT descripcion,descripcion_corta,tipo_via_id FROM ").append(Constante.schemadb).append(".gn_tipo_via order by descripcion asc");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
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
	//").append(Constante.schemadb).append(".rp_ubicacion_predio
	public List<RpUbicacionPredio> getAllRpUbicacionPredio()throws Exception{
		List<RpUbicacionPredio> lista=new LinkedList<RpUbicacionPredio>();
		try{
			StringBuffer SQL=new StringBuffer("SELECT ubicacion_predio_id,descripcion,descripcion_corta FROM ").append(Constante.schemadb).append(".rp_ubicacion_predio order by descripcion asc");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				RpUbicacionPredio obj=new RpUbicacionPredio(); 
				obj.setUbicacionPredioId(rs.getInt("ubicacion_predio_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				obj.setDescripcionCorta(rs.getString("descripcion_corta"));
				lista.add(obj);
			}
			
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	
	public List<RpTipoAdquisicion> getAllRpTipoAdquisicion()throws Exception{
		List<RpTipoAdquisicion> lista=new LinkedList<RpTipoAdquisicion>();
		try{
			StringBuffer SQL=new StringBuffer("select tipo_adquisicion_id,descripcion from ").append(Constante.schemadb).append(".rp_tipo_adquisicion order by descripcion asc");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				RpTipoAdquisicion obj=new RpTipoAdquisicion(); 
				obj.setTipoAdquisicionId(rs.getInt("tipo_adquisicion_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				lista.add(obj);
			}
			
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	//Registro de datos de la construccion
	//Tipo nivel
	public List<RpTipoNivel> getAllRpTipoNivel()throws Exception{
		List<RpTipoNivel> lista=new LinkedList<RpTipoNivel>();
		try{
			StringBuffer SQL=new StringBuffer("SELECT tipo_nivel_id,descripcion,descripcion_corta FROM ").append(Constante.schemadb).append(".rp_tipo_nivel order by descripcion asc");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				RpTipoNivel obj=new RpTipoNivel(); 
				obj.setTipoNivelId(rs.getInt("tipo_nivel_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				obj.setDescripcionCorta(rs.getString("descripcion_corta"));
				
				lista.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	//Mes enero - diciembre
	
	//Area comun construida
	
	//--UBICACION DE PREDIO
	//GnTipoAgrupamiento
	public List<GnTipoAgrupamiento> getAllGnTipoAgrupamiento()throws Exception{
		List<GnTipoAgrupamiento> lista=new LinkedList<GnTipoAgrupamiento>();
		try{
			StringBuffer SQL=new StringBuffer("select tipo_agrupamiento_id,descripcion,descripcion_corta from ").append(Constante.schemadb).append(".gn_tipo_agrupamiento order by descripcion asc");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
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
	
	//GnTipoEdificacion
	public List<GnTipoEdificacion> getAllGnTipoEdificacion()throws Exception{
		List<GnTipoEdificacion> lista=new LinkedList<GnTipoEdificacion>();
		try{
			StringBuffer SQL=new StringBuffer("select tipo_edificacion_id,descripcion,descripcion_corta  from ").append(Constante.schemadb).append(".gn_tipo_edificacion order by descripcion asc");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
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
	//GnTipoIngreso
	public List<GnTipoIngreso> getAllGnTipoIngreso()throws Exception{
		List<GnTipoIngreso> lista=new LinkedList<GnTipoIngreso>();
		try{
			StringBuffer SQL=new StringBuffer("select tipo_ingreso_id,descripcion,descripcion_corta from ").append(Constante.schemadb).append(".gn_tipo_ingreso order by descripcion asc");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
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
	//GnTipoInterior
	public List<GnTipoInterior> getAllGnTipoInterior()throws Exception{
		List<GnTipoInterior> lista=new LinkedList<GnTipoInterior>();
		try{
			StringBuffer SQL=new StringBuffer("select tipo_interior_id,descripcion,descripcion_corta from ").append(Constante.schemadb).append(".gn_tipo_interior order by descripcion asc");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
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
	
	//GnSector
	public List<GnSector> getAllGnSector()throws Exception{
		List<GnSector> lista=new LinkedList<GnSector>();
		try{
			String SQL=new String("select sector_id,descripcion from gn_sector order by descripcion asc");
			
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
	//Buscar vias 
	//public ArrayList<FindGnVia> findGnVia(Integer tipo_via_id,Integer sector_id,String descripcion)throws Exception{
	public ArrayList<UbicacionDTO> findGnVia(Integer tipo_via_id,Integer sector_id,String descripcion)throws Exception{
		ArrayList<UbicacionDTO> lista=new ArrayList<UbicacionDTO>();
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("SELECT ");
			SQL.append("").append(Constante.schemadb).append(".lpad(s.sector_id,2)+'.'+  ");//--sector
			SQL.append("").append(Constante.schemadb).append(".lpad(l.lugar_id,3)+'.'+  ");//--lugar
			SQL.append("").append(Constante.schemadb).append(".lpad(tv.tipo_via_id,2)+'.'+  ");//--tipovia
			SQL.append("").append(Constante.schemadb).append(".lpad(v.via_id,4)+'.'+  ");//--via
			SQL.append("").append(Constante.schemadb).append(".lpad(u.numero_cuadra,4)+'.'+ ");// --numero cuadra
			SQL.append("convert(varchar,u.lado_cuadra)+'.'+  ");//--lado
			SQL.append("").append(Constante.schemadb).append(".lpad(u.numero_manzana,5) as codigocatastral ");//--manzana
			SQL.append(",u.ubicacion_id,s.sector_id,s.descripcion sector,l.descripcion lugar,tv.descripcion tipo_via,v.descripcion via,u.numero_cuadra numero_cuadra,u.lado_cuadra lado,u.numero_manzana,v.via_id ");
			SQL.append("FROM ").append(Constante.schemadb).append(".gn_ubicacion u ");
			SQL.append("INNER JOIN ").append(Constante.schemadb).append(".gn_via v on (v.via_id=u.via_id) ");
			SQL.append("INNER JOIN ").append(Constante.schemadb).append(".gn_tipo_via tv on (tv.tipo_via_id=v.tipo_via_id) ");
			SQL.append("INNER JOIN ").append(Constante.schemadb).append(".gn_sector_lugar sl on (sl.sector_lugar_id=u.sector_lugar_id) ");
			SQL.append("INNER JOIN ").append(Constante.schemadb).append(".gn_sector s on (sl.sector_id=s.sector_id) ");
			SQL.append("INNER JOIN ").append(Constante.schemadb).append(".gn_lugar l on (l.lugar_id=sl.lugar_id) ");
			SQL.append("WHERE 1=1 ");
			if(tipo_via_id!=null&&tipo_via_id>0)
				SQL.append(" and tv.tipo_via_id=").append(tipo_via_id); 
			if(sector_id!=null&&sector_id>0)
				SQL.append(" and sl.sector_id=").append(sector_id); 
			if(descripcion!=null&&descripcion.trim().length()>0)
				SQL.append(" and v.descripcion like UPPER('%").append(descripcion).append("%')");
			SQL.append(" order by u.ubicacion_id ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				UbicacionDTO obj=new UbicacionDTO(); 
				obj.setUbicacionId(rs.getInt("ubicacion_id"));
				obj.setSectorId(rs.getInt("sector_id"));
				obj.setSector(rs.getString("sector"));
				obj.setLugar(rs.getString("lugar"));
				obj.setTipoVia(rs.getString("tipo_via"));
				obj.setVia(rs.getString("via"));
				obj.setNumeroCuadra(rs.getInt("numero_cuadra"));
				obj.setLado(rs.getInt("lado"));
				obj.setNumeroManzana(rs.getInt("numero_manzana"));
				obj.setCodigoCatastral(rs.getString("codigocatastral"));
				obj.setViaid(rs.getInt("via_id"));
				
				lista.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	
	//--
	public List<RpCondicionPropiedad> getAllRpCondicionPropiedad()throws Exception{
		List<RpCondicionPropiedad> lista=new LinkedList<RpCondicionPropiedad>();
		try{
			StringBuffer SQL=new StringBuffer("SELECT descripcion,condicion_propiedad_id FROM ").append(Constante.schemadb).append(".rp_condicion_propiedad order by descripcion asc");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				RpCondicionPropiedad obj=new RpCondicionPropiedad(); 
				obj.setCondicionPropiedadId(rs.getInt("condicion_propiedad_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				lista.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}

	public List<MpCondiEspePredio> getAllMpCondiEspePredio()throws Exception{
		List<MpCondiEspePredio> lista=new LinkedList<MpCondiEspePredio>();
		try{
			StringBuffer SQL=new StringBuffer("SELECT descripcion,cond_espe_predio_id FROM ").append(Constante.schemadb).append(".mp_condi_espe_predio order by descripcion asc");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				MpCondiEspePredio obj=new MpCondiEspePredio(); 
				obj.setCondEspePredioId(rs.getInt("cond_espe_predio_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				lista.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	//PANTALLA DATOS DE LA CONSTRUCCION
	//Tipo Nivel
	//public List<RpTipoNivel> getAllRpTipoNivel()
	//Mes construccion
	public List<RjMes> getAllRjMes()throws Exception{
		List<RjMes> lista=new LinkedList<RjMes>();
		try{
			StringBuffer SQL=new StringBuffer("SELECT mes_id,descripcion,estado FROM ").append(Constante.schemadb).append(".rj_mes order by mes_id asc");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				RjMes obj=new RjMes(); 
				obj.setMesId(rs.getString("mes_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				obj.setEstado(rs.getString("estado"));
				lista.add(obj);
			}
			
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}	
	//Area comun contr. :
	//RjComponenteConstruccion
	public List<RjComponenteConstruccion> getAllRjComponenteConstruccion(int anioVigencia,int tipoComponente)throws Exception{
		List<RjComponenteConstruccion> lista=new LinkedList<RjComponenteConstruccion>();
		try{
			StringBuffer SQL=new StringBuffer(); 
			SQL.append("select cc.cate_construccion_id,cc.descripcion,cc.Categoria,vu.periodo,vu.tipo_componente_id ");
			SQL.append("from ").append(Constante.schemadb).append(".rj_componente_construccion cc INNER JOIN ").append(Constante.schemadb).append(".de_valor_construccion vu  ");
			SQL.append("ON (cc.tipo_componente_id=vu.tipo_componente_id AND cc.cate_construccion_id=vu.cate_construccion_id AND cc.periodo = vu.periodo) ");
			SQL.append("where vu.periodo=? AND vu.tipo_componente_id=? ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, anioVigencia);
			pst.setInt(2, tipoComponente);
			
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				RjComponenteConstruccion obj=new RjComponenteConstruccion(); 
				obj.setCateConstruccionId(rs.getInt("cate_construccion_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				obj.setCategoria(rs.getString("Categoria"));
				obj.setPeriodo(rs.getInt("periodo"));
				obj.setTipoComponenteId(rs.getInt("tipo_componente_id"));
				
				lista.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
		
	//Area comun contr. :
	//rj_unidad_medida_acc
	public List<TgTabla> getAllTgTabla(String descripcion)throws Exception{
		List<TgTabla> lista=new LinkedList<TgTabla>();
		try{
			//validacion a la bd relacionada 31/07/2012
			StringBuffer SQL=new StringBuffer("select campo1,campo2 from ").append(Constante.schemadb).append(".tg_tabla where nombre_corto='"+descripcion+"' AND estado='1'");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				TgTabla obj=new TgTabla(); 
				obj.setCampo1(rs.getString("campo1"));
				obj.setCampo2(rs.getString("campo2"));
				lista.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	//Material Predominante : falta datos
	//rp_material_predominante
	//RpMaterialPredominante
	public List<RpMaterialPredominante> getAllRpMaterialPredominante()throws Exception{
		List<RpMaterialPredominante> lista=new LinkedList<RpMaterialPredominante>();
		try{
			StringBuffer SQL=new StringBuffer("SELECT mat_predominante_id,descripcion,descripcion_corta FROM ").append(Constante.schemadb).append(".rp_material_predominante order by descripcion asc");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				RpMaterialPredominante obj=new RpMaterialPredominante(); 
				obj.setMatPredominanteId(rs.getInt("mat_predominante_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				obj.setDescripcionCorta(rs.getString("descripcion_corta"));
				lista.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	
	//Estado de concervacion : falta datos
	//rj_estado_concervacion
	//RjEstadoConservacion
	public List<RjEstadoConservacion> getAllRjEstadoConservacion()throws Exception{
		List<RjEstadoConservacion> lista=new LinkedList<RjEstadoConservacion>();
		try{
			StringBuffer SQL=new StringBuffer("SELECT conservacion_id,descripcion FROM ").append(Constante.schemadb).append(".rj_estado_conservacion order by descripcion asc");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				RjEstadoConservacion obj=new RjEstadoConservacion(); 
				obj.setConservacionId(rs.getInt("conservacion_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				lista.add(obj);
			}
			
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	
	//Clasificacion depreciacion : falta datos
	//rj_tipo_depreciacion
	//RjTipoDepreciacion
	public List<RjTipoDepreciacion> getAllRjTipoDepreciacion()throws Exception{
		List<RjTipoDepreciacion> lista=new LinkedList<RjTipoDepreciacion>();
		try{
			StringBuffer SQL=new StringBuffer("SELECT tipo_depreciacion_id,descripcion,descripcion_corta FROM ").append(Constante.schemadb).append(".rp_tipo_depreciacion order by descripcion asc");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				RjTipoDepreciacion obj=new RjTipoDepreciacion(); 
				obj.setTipoDepreciacionId(rs.getInt("tipo_depreciacion_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				obj.setDescCorta(rs.getString("descripcion_corta"));
				lista.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	
	public ArrayList<RpDjconstruccion> getAllRpDjconstruccion(Integer dj_id,Integer periodo)throws Exception{
		ArrayList<RpDjconstruccion> lista=new ArrayList<RpDjconstruccion>();
		try{
			
			StringBuilder SQL = new StringBuilder("exec stp_rp_getAllRpDjconstruccion ?,?");
			
//			StringBuffer SQL=new StringBuffer(); 
//			SQL.append("SELECT c.dj_id,c.construccion_id,c.clasi_depreciacion_id,c.conservacion_id,c.tipo_nivel_id ");
//			SQL.append(",c.mat_predominante_id,c.tipo_nivel_id,c.nro_nivel,c.seccion ");
//			SQL.append(",c.anno_construccion,c.mes_construccion,c.area_construccion,c.tipo_unidad_medida,c.area_comun_construccion ");
//			SQL.append(",c.muros,c.techo,c.pisos,c.puertas,c.revestimientos ");
//			SQL.append(",c.bannos,c.electricos,c.referencia, c.anno_actualizacion, ");
//			SQL.append("t.descripcion dentiponivel, ");
//			SQL.append("m.descripcion denmatpredominante, ");
//			SQL.append("v.descripcion denestadoconservacion, ");
//			SQL.append("d.descripcion dentipodepreciacion, ");
//			SQL.append("dbo.getComponenteConstruccion("+periodo+",1,c.muros) denmuros, ");
//			SQL.append("dbo.getComponenteConstruccion("+periodo+",2,c.techo) dentecho, ");
//			SQL.append("dbo.getComponenteConstruccion("+periodo+",3,c.pisos) denpisos, ");
//			SQL.append("dbo.getComponenteConstruccion("+periodo+",4,c.puertas) denpuertas, ");
//			SQL.append("dbo.getComponenteConstruccion("+periodo+",5,c.revestimientos) denrevestimiento, ");
//			SQL.append("dbo.getComponenteConstruccion("+periodo+",6,c.bannos) denbannos, ");
//			SQL.append("dbo.getComponenteConstruccion("+periodo+",7,c.electricos) denelectrico ");
//			SQL.append("FROM ").append(Constante.schemadb).append(".rp_djconstruccion c ");
//			SQL.append("left join ").append(Constante.schemadb).append(".rp_tipo_nivel t on (t.tipo_nivel_id=c.tipo_nivel_id) ");
//			SQL.append("left join ").append(Constante.schemadb).append(".rp_material_predominante m on (m.mat_predominante_id=c.mat_predominante_id) ");
//			SQL.append("left join ").append(Constante.schemadb).append(".rj_estado_conservacion v on(v.conservacion_id=c.conservacion_id) ");
//			SQL.append("left join ").append(Constante.schemadb).append(".rp_tipo_depreciacion d on(d.tipo_depreciacion_id=c.clasi_depreciacion_id) ");
//			
//			SQL.append("inner join ( SELECT nro_nivel, seccion, max(cj.anno_actualizacion) as anno_actualizacion FROM rp_djconstruccion cj ");
//			SQL.append(" WHERE cj.dj_id = ?");
//			SQL.append(" GROUP BY nro_nivel,seccion) as djc on c.nro_nivel = djc.nro_nivel and c.seccion = djc.seccion and djc.anno_actualizacion=c.anno_actualizacion ");
//			
//			SQL.append("where dj_id=? and c.estado='").append(Constante.ESTADO_ACTIVO).append("'");			
//			SQL.append(" order by nro_nivel, seccion");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, dj_id);
			pst.setInt(2, periodo);
			
			ResultSet rs=pst.executeQuery();
			int item=1;
			while (rs.next()) {
				RpDjconstruccion obj = new RpDjconstruccion();
				obj.setItem(item);
				obj.setDjId(rs.getInt("dj_id"));
				obj.setConstruccionId(rs.getInt("construccion_id"));
				obj.setClasiDepreciacionId(rs.getInt("clasi_depreciacion_id"));
				obj.setConservacionId(rs.getInt("conservacion_id"));
				obj.setTipoNivelId(rs.getInt("tipo_nivel_id"));
				obj.setMatPredominanteId(rs.getInt("mat_predominante_id"));
				obj.setTipoNivelId(rs.getInt("tipo_nivel_id"));
				obj.setNroNivel(rs.getInt("nro_nivel"));
				obj.setAnnoConstruccion(rs.getInt("anno_construccion"));
				obj.setAnnoActualizacion(rs.getInt("anno_actualizacion"));
				obj.setMesConstruccion(rs.getString("mes_construccion"));
				obj.setAreaConstruccion(rs.getBigDecimal("area_construccion"));
				obj.setTipoUnidadMedida(rs.getString("tipo_unidad_medida"));
				obj.setAreaComunConstruccion(rs
						.getBigDecimal("area_comun_construccion"));
				obj.setMuros(rs.getInt("muros"));
				obj.setTecho(rs.getInt("techo"));
				obj.setPisos(rs.getInt("pisos"));
				obj.setPuertas(rs.getInt("puertas"));
				obj.setRevestimientos(rs.getInt("revestimientos"));
				obj.setBannos(rs.getInt("bannos"));
				obj.setElectricos(rs.getInt("electricos"));
				obj.setReferencia(rs.getString("referencia"));
				obj.setDentiponivel(rs.getString("dentiponivel"));
				obj.setDenmatpredominante(rs.getString("denmatpredominante"));
				obj.setDenestadoconservacion(rs
						.getString("denestadoconservacion"));
				obj.setDentipodepreciacion(rs.getString("dentipodepreciacion"));
				obj.setDenmuros(rs.getString("denmuros"));
				obj.setDentecho(rs.getString("dentecho"));
				obj.setDenpisos(rs.getString("denpisos"));
				obj.setDenpuertas(rs.getString("denpuertas"));
				obj.setDenrevestimiento(rs.getString("denrevestimiento"));
				obj.setDenbannos(rs.getString("denbannos"));
				obj.setDenelectrico(rs.getString("denelectrico"));
				obj.setSeccion(rs.getString("seccion"));

				lista.add(obj);
				item++;
			}
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	public ArrayList<RpDjconstruccion> getAllRpDjconstruccion2(Integer dj_id,Integer periodo)throws Exception{
		ArrayList<RpDjconstruccion> lista=new ArrayList<RpDjconstruccion>();
		try{
			
			StringBuilder SQL = new StringBuilder("exec stp_rp_getAllRpDjconstruccion2 ?,?");		
		
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, dj_id);
			pst.setInt(2, periodo);
			
			ResultSet rs=pst.executeQuery();
			int item=1;
			while (rs.next()) {
				RpDjconstruccion obj = new RpDjconstruccion();
				obj.setItem(item);
				obj.setDjId(rs.getInt("dj_id"));
				obj.setConstruccionId(rs.getInt("construccion_id"));
				obj.setClasiDepreciacionId(rs.getInt("clasi_depreciacion_id"));
				obj.setConservacionId(rs.getInt("conservacion_id"));
				obj.setTipoNivelId(rs.getInt("tipo_nivel_id"));
				obj.setMatPredominanteId(rs.getInt("mat_predominante_id"));
				obj.setTipoNivelId(rs.getInt("tipo_nivel_id"));
				obj.setNroNivel(rs.getInt("nro_nivel"));
				obj.setAnnoConstruccion(rs.getInt("anno_construccion"));
				obj.setAnnoActualizacion(rs.getInt("anno_actualizacion"));
				obj.setMesConstruccion(rs.getString("mes_construccion"));
				obj.setAreaConstruccion(rs.getBigDecimal("area_construccion"));
				obj.setTipoUnidadMedida(rs.getString("tipo_unidad_medida"));
				obj.setAreaComunConstruccion(rs
						.getBigDecimal("area_comun_construccion"));
				obj.setMuros(rs.getInt("muros"));
				obj.setTecho(rs.getInt("techo"));
				obj.setPisos(rs.getInt("pisos"));
				obj.setPuertas(rs.getInt("puertas"));
				obj.setRevestimientos(rs.getInt("revestimientos"));
				obj.setBannos(rs.getInt("bannos"));
				obj.setElectricos(rs.getInt("electricos"));
				obj.setReferencia(rs.getString("referencia"));
				obj.setDentiponivel(rs.getString("dentiponivel"));
				obj.setDenmatpredominante(rs.getString("denmatpredominante"));
				obj.setDenestadoconservacion(rs
						.getString("denestadoconservacion"));
				obj.setDentipodepreciacion(rs.getString("dentipodepreciacion"));
				obj.setDenmuros(rs.getString("denmuros"));
				obj.setDentecho(rs.getString("dentecho"));
				obj.setDenpisos(rs.getString("denpisos"));
				obj.setDenpuertas(rs.getString("denpuertas"));
				obj.setDenrevestimiento(rs.getString("denrevestimiento"));
				obj.setDenbannos(rs.getString("denbannos"));
				obj.setDenelectrico(rs.getString("denelectrico"));
				obj.setSeccion(rs.getString("seccion"));

				lista.add(obj);
				item++;
			}
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	public String getFlagDjAnno(Integer dj,Integer periodo,Integer predioid)throws Exception{
		String flagDjAnno=null;
		try{
			
			StringBuffer SQL=new StringBuffer();
			SQL.append("select flag_dj_anno from ").append(Constante.schemadb).append(".rp_djpredial where dj_id=? and anno_dj=? and predio_id=?");
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, dj);
			pst.setInt(2, periodo);
			pst.setInt(3, predioid);
			//--
			ResultSet rs=pst.executeQuery();
			if(rs.next()){
				flagDjAnno=rs.getString("flag_dj_anno");
			}
		}catch(Exception e){
			throw(e);
		}
		return flagDjAnno;
	}

	public RpDjconstruccion getRpDjconstruccion(Integer djId,Integer construccionId,Integer periodo)throws Exception{
		RpDjconstruccion obj=null;
		try{
			StringBuffer SQL=new StringBuffer(); 
			SQL.append("SELECT c.dj_id,c.construccion_id,c.clasi_depreciacion_id,c.conservacion_id,c.tipo_nivel_id ");
			SQL.append(",c.mat_predominante_id,c.tipo_nivel_id,c.nro_nivel,c.seccion ");
			SQL.append(",c.anno_construccion,c.mes_construccion,c.area_construccion,c.tipo_unidad_medida,c.area_comun_construccion,c.area_terreno ");
			SQL.append(",c.muros,c.techo,c.pisos,c.puertas,c.revestimientos ");
			SQL.append(",c.bannos,c.electricos,c.referencia, c.anno_actualizacion,  ");
			SQL.append("t.descripcion dentiponivel, ");
			SQL.append("m.descripcion denmatpredominante, ");
			SQL.append("v.descripcion denestadoconservacion, ");
			SQL.append("d.descripcion dentipodepreciacion, ");
			SQL.append("dbo.getComponenteConstruccion(").append(periodo).append(",1,c.muros) denmuros, ");
			SQL.append("dbo.getComponenteConstruccion(").append(periodo).append(",2,c.techo) dentecho, ");
			SQL.append("dbo.getComponenteConstruccion(").append(periodo).append(",3,c.pisos) denpisos, ");
			SQL.append("dbo.getComponenteConstruccion(").append(periodo).append(",4,c.puertas) denpuertas, ");
			SQL.append("dbo.getComponenteConstruccion(").append(periodo).append(",5,c.revestimientos) denrevestimiento, ");
			SQL.append("dbo.getComponenteConstruccion(").append(periodo).append(",6,c.bannos) denbannos, ");
			SQL.append("dbo.getComponenteConstruccion(").append(periodo).append(",7,c.electricos) denelectrico ");
			SQL.append("FROM ").append(Constante.schemadb).append(".rp_djconstruccion c ");
			SQL.append("left join ").append(Constante.schemadb).append(".rp_tipo_nivel t on (t.tipo_nivel_id=c.tipo_nivel_id) ");
			SQL.append("left join ").append(Constante.schemadb).append(".rp_material_predominante m on (m.mat_predominante_id=c.mat_predominante_id) ");
			SQL.append("left join ").append(Constante.schemadb).append(".rj_estado_conservacion v on(v.conservacion_id=c.conservacion_id) ");
			SQL.append("left join ").append(Constante.schemadb).append(".rp_tipo_depreciacion d on(d.tipo_depreciacion_id=c.clasi_depreciacion_id) ");
			SQL.append("where dj_id=? and construccion_id=? and c.estado='"+Constante.ESTADO_ACTIVO+"'");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, djId);
			pst.setInt(2, construccionId);
			
			ResultSet rs=pst.executeQuery();
			if(rs.next()){
				obj=new RpDjconstruccion();
				obj.setDjId(rs.getInt("dj_id"));
				obj.setConstruccionId(rs.getInt("construccion_id"));
				obj.setClasiDepreciacionId(rs.getInt("clasi_depreciacion_id"));
				obj.setConservacionId(rs.getInt("conservacion_id"));
				obj.setTipoNivelId(rs.getInt("tipo_nivel_id"));
					obj.setMatPredominanteId(rs.getInt("mat_predominante_id"));
					obj.setTipoNivelId(rs.getInt("tipo_nivel_id"));
					obj.setNroNivel(rs.getInt("nro_nivel"));
				obj.setAnnoConstruccion(rs.getInt("anno_construccion"));	
				obj.setAnnoActualizacion(rs.getInt("anno_actualizacion"));
				obj.setMesConstruccion(rs.getString("mes_construccion"));
				obj.setAreaConstruccion(rs.getBigDecimal("area_construccion"));
				obj.setTipoUnidadMedida(rs.getString("tipo_unidad_medida"));
				obj.setAreaComunConstruccion(rs.getBigDecimal("area_comun_construccion"));
					obj.setMuros(rs.getInt("muros"));
					obj.setTecho(rs.getInt("techo"));
					obj.setPisos(rs.getInt("pisos"));
					obj.setPuertas(rs.getInt("puertas"));
					obj.setRevestimientos(rs.getInt("revestimientos"));
				obj.setBannos(rs.getInt("bannos"));
				obj.setElectricos(rs.getInt("electricos"));
				obj.setReferencia(rs.getString("referencia"));
				
				obj.setDentiponivel(rs.getString("dentiponivel"));
				obj.setDenmatpredominante(rs.getString("denmatpredominante"));
				obj.setDenestadoconservacion(rs.getString("denestadoconservacion"));
				obj.setDentipodepreciacion(rs.getString("dentipodepreciacion"));
				obj.setDenmuros(rs.getString("denmuros"));
				obj.setDentecho(rs.getString("dentecho"));
				obj.setDenpisos(rs.getString("denpisos"));
				obj.setDenpuertas(rs.getString("denpuertas"));
				obj.setDenrevestimiento(rs.getString("denrevestimiento"));
				obj.setDenbannos(rs.getString("denbannos"));
				obj.setDenelectrico(rs.getString("denelectrico"));
				
				obj.setAreaTerreno(rs.getBigDecimal("area_terreno"));
				obj.setSeccion(rs.getString("seccion"));
				
			}
		}catch(Exception e){
			throw(e);
		}
		return obj;
	}

	public List<GnTipoDenoUrbana> getAllGnTipoDenoUrbana()throws Exception{
		List<GnTipoDenoUrbana> lista=new LinkedList<GnTipoDenoUrbana>();
		try{
			StringBuffer SQL=new StringBuffer("SELECT tipo_deno_id,descripcion,descripcion_corta FROM ").append(Constante.schemadb).append(".gn_tipo_deno_urbana order by descripcion asc");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				GnTipoDenoUrbana obj=new GnTipoDenoUrbana(); 
				obj.setTipoDenoId(rs.getInt("tipo_deno_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				obj.setDescripcionCorta(rs.getString("descripcion_corta"));
				lista.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	
	public List<RpTipoTierraRustico> getAllRpTipoTierraRustico()throws Exception{
		List<RpTipoTierraRustico> lista=new LinkedList<RpTipoTierraRustico>();
		try{
			StringBuffer SQL=new StringBuffer("select tipo_tierra_id,descripcion,estado from ").append(Constante.schemadb).append(".rp_tipo_tierra_rustico order by tipo_tierra_id asc");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				RpTipoTierraRustico obj=new RpTipoTierraRustico(); 
				obj.setTipoTierraId(rs.getInt("tipo_tierra_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				lista.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}

	public List<RpAltitud> getAllRpAltitud()throws Exception{
		List<RpAltitud> lista=new LinkedList<RpAltitud>();
		try{
			StringBuffer SQL=new StringBuffer("select altitud_id,descripcion,descripcion_corta,estado from ").append(Constante.schemadb).append(".rp_altitud order by altitud_id asc");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				RpAltitud obj=new RpAltitud(); 
				obj.setAltitudId(rs.getInt("altitud_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				obj.setDescripcionCorta(rs.getString("descripcion_corta"));
				lista.add(obj);
			}
			
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}

	public List<RpCategoriaRustico> getAllRpCategoriaRustico()throws Exception{
		List<RpCategoriaRustico> lista=new LinkedList<RpCategoriaRustico>();
		try{
			StringBuffer SQL=new StringBuffer("select categoria_rustico_id,descripcion from ").append(Constante.schemadb).append(".rp_categoria_rustico order by descripcion asc");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				RpCategoriaRustico obj=new RpCategoriaRustico(); 
				obj.setCategoriaRusticoId(rs.getInt("categoria_rustico_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				lista.add(obj);
			}
			
			
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}

	public int existDjDireccion(Integer dj_id)throws Exception{
		int result=0;
		try{
			StringBuffer SQL=new StringBuffer("select COUNT(*) as cantidad from ").append(Constante.schemadb).append(".rp_djdireccion d where  d.dj_id=?");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1,dj_id);
			
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				result=rs.getInt("cantidad");
			}
			
		}catch(Exception e){
			throw(e);
		}
		return result;
	}

	public int getDjDireccionActive(Integer dj_id)throws Exception{
		int result=0;
		try{
			StringBuffer SQL=new StringBuffer("select max(d.direccion_id) as direccion_id from ").append(Constante.schemadb).append(".rp_djdireccion d where  d.dj_id=? and d.estado='").append(Constante.ESTADO_ACTIVO).append("';");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1,dj_id);
			
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				result=rs.getInt("direccion_id");
			}
		}catch(Exception e){
			throw(e);
		}
		return result;
	}

	public int desactiveDirecciones(Integer dj_id,Integer direccion_id)throws Exception{
		int result=0;
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append(" UPDATE ").append(Constante.schemadb).append(".rp_djdireccion ");
			SQL.append(" SET estado = '").append(Constante.ESTADO_INACTIVO).append("' ");
			SQL.append(" WHERE dj_id=? and direccion_id!=? ");

			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1,dj_id);//a
			pst.setInt(2,direccion_id);//a
			//--
			result=pst.executeUpdate();
		}catch(Exception e){
			throw(e);
		}
		return result;
	}
	
	public int desactiveTransferentes(Integer dj_id)throws Exception{
		int result=0;
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append(" UPDATE ").append(Constante.schemadb).append(".rp_transferencia_propiedad ");
			SQL.append(" SET estado = '").append(Constante.ESTADO_INACTIVO).append("' ");
			SQL.append(" WHERE dj_id=? ");

			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1,dj_id);//a
			//--
			result=pst.executeUpdate();
		}catch(Exception e){
			throw(e);
		}
		return result;
	}
	
	public int existDjConstruccion(Integer dj_id)throws Exception{
		int result=0;
		try{
			StringBuffer SQL=new StringBuffer("select COUNT(*) cantidad from ").append(Constante.schemadb).append(".rp_djconstruccion d where  d.dj_id=? and d.estado='").append(Constante.ESTADO_ACTIVO).append("'");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1,dj_id);
			
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				result=rs.getInt("cantidad");
			}
			
		}catch(Exception e){
			throw(e);
		}
		return result;
	}
	
	public int getUltimoConstruccionId(Integer dj_id)throws Exception{
		int result=0;
		try{
			StringBuffer SQL=new StringBuffer("SELECT MAX(construccion_id) as id FROM ").append(Constante.schemadb).append(".rp_djconstruccion where dj_id=?");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1,dj_id);
			
			ResultSet rs=pst.executeQuery();
			if(rs.next()){
				result=rs.getInt("id");
			}
		}catch(Exception e){
			throw(e);
		}
		return result;
	}

	public GnVia getGnVia(Integer ViaId)throws Exception{
		GnVia gnVia=new GnVia();
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("SELECT t.tipo_via_id,t.descripcion,t.via_id,tv.descripcion descripciontipovia "); 
			SQL.append("FROM ").append(Constante.schemadb).append(".gn_via t inner join ").append(Constante.schemadb).append(".gn_tipo_via tv on (tv.tipo_via_id=t.tipo_via_id) ");
			SQL.append("WHERE t.via_id=? ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, ViaId);
			ResultSet rs=pst.executeQuery();
			if(rs.next()){
				gnVia=new GnVia();
				gnVia.setViaId(rs.getInt("via_id"));
				gnVia.setDescripcion(rs.getString("descripcion"));
				gnVia.setTipoViaId(rs.getInt("tipo_via_id"));
				gnVia.setDescripcionTipoVia(rs.getString("descripciontipovia"));
			}
		}catch(Exception e){
			throw(e);
		}
		return gnVia;
	}
	
	private String DescripcionAbreviada(HashMap<Integer, String> map,int id){
		return map.get(id);
	}
	
	private String getDenominacionTipoVia(HashMap<Integer, String> map,int TipoViaId){
		return map.get(TipoViaId);
	}
	private String getDenominacionDenUrbana(HashMap<Integer, String> map,int TipoDenUrbanaId){
		return map.get(TipoDenUrbanaId);
	}
	
	public String ConcatenarDomicilioUrbano(RpDjdireccion direccion,
			HashMap<Integer,String> mapIGnTipoEdificacion,
			HashMap<Integer,String> mapIGnTipoInterior,
			HashMap<Integer,String> mapIGnTipoIngreso,
			HashMap<Integer,String> mapIGnTipoAgrupamiento,
			HashMap<Integer,String> mapIGnTipoVia,
			HashMap<Integer,String> mapIGnTipoDenUrbana,String denominacionSector,Integer numeroCuadra){
		String strDireccion= "";
		String strDesDom= "";

		//Numero 1
		if(direccion.getNumero()!=null && direccion.getNumero().trim().length()>0)
		{
			strDireccion = "Nro "+direccion.getNumero();
			//Letra 1
			if(direccion.getLetra()!=null&&direccion.getLetra().trim().length()>0){
				strDireccion = strDireccion+"-"+direccion.getLetra();
			}
			//Numero 2
			if(direccion.getNumero2()!=null && direccion.getNumero2().trim().length()>0){
				strDireccion = strDireccion+"/"+direccion.getNumero2();	
			}
			//Letra 2
			if(direccion.getLetra2()!=null&&direccion.getLetra2().trim().length()>0){
				strDireccion = strDireccion+"-"+direccion.getLetra2();
			}
		}
		
				
		//Tipo Edificio
		if(direccion.getTipoEdificacionId()!=null&&direccion.getTipoEdificacionId()>0){
			strDireccion =strDireccion +" " + DescripcionAbreviada(mapIGnTipoEdificacion, direccion.getTipoEdificacionId());
			//Nombre del Edificio
			if(direccion.getNombreEdificiacion()!=null &&direccion.getNombreEdificiacion().trim().length()>0){
				strDireccion = strDireccion +" " + direccion.getNombreEdificiacion();	
			}
		}
			
		//Piso
		if(direccion.getPiso()!=null &&direccion.getPiso().trim().length()>0){
			strDireccion = strDireccion +" " + "Piso " + direccion.getPiso();
		}
			
		//Tipo Interior
		if(direccion.getTipoInteriorId()!=null&&direccion.getTipoInteriorId()>0){
			strDireccion = strDireccion+" " + DescripcionAbreviada(mapIGnTipoInterior, direccion.getTipoInteriorId());
			//'Numero Interior
			if(direccion.getNumeroInterior()!=null &&direccion.getNumeroInterior().trim().length()>0)
				strDireccion =strDireccion+ " " + direccion.getNumeroInterior();
			//'Letra Interior
			if(direccion.getLetraInterior()!=null&&direccion.getLetraInterior().trim().length()>0)
				strDireccion =strDireccion+ "-" + direccion.getLetraInterior().trim();	
		}
			
		//'Tipo Ingreso
		if(direccion.getTipoIngresoId()!=null&&direccion.getTipoIngresoId()>0){
			strDireccion =strDireccion+ " " + DescripcionAbreviada(mapIGnTipoIngreso, direccion.getTipoIngresoId());
			//'Nombre Ingreso
			if(direccion.getNombreIngreso()!=null&&direccion.getNombreIngreso().trim().length()>0)
				strDireccion = strDireccion+" " + direccion.getNombreIngreso().trim();
		}
			
		//'Manzana
		if(direccion.getManzana()!=null&&direccion.getManzana().trim().length()>0){
			strDireccion =strDireccion+ " " + "Mz " + direccion.getManzana().trim();
		}

		//'Lote
		if(direccion.getLote()!=null&&direccion.getLote().trim().length()>0){
			strDireccion = strDireccion+" " + "Lt " + direccion.getLote().trim();
		}
			
		//'Tipo Agrupamiento
		if(direccion.getTipoAgrupamientoId()!=null&&direccion.getTipoAgrupamientoId()>0){
			strDireccion =strDireccion+ " " + DescripcionAbreviada(mapIGnTipoAgrupamiento, direccion.getTipoAgrupamientoId());
			if(direccion.getNombreAgrupamiento()!=null&&direccion.getNombreAgrupamiento().trim().length()>0){
				strDireccion = strDireccion+" " + direccion.getNombreAgrupamiento().trim();	
			}
		}
		
		//'Concatenacion de Domicilio
		//'Tipo Via
		if(direccion.getVia()!=null&&direccion.getVia().getTipoViaId()>0){
			strDesDom = getDenominacionTipoVia(mapIGnTipoVia,direccion.getVia().getTipoViaId()) + " " + direccion.getVia().getDescripcion();
		}
			
		if(strDireccion.trim().length()>0){
			strDesDom =strDesDom+ " " + strDireccion;
		}																			
	
		//'Denominacion Urbana
		if(direccion.getDenominacionUrbana()!=null&&direccion.getDenominacionUrbana().getDenoId()>0){
			if(strDesDom.trim().length()>0){
				strDesDom=strDesDom+"-"+getDenominacionDenUrbana(mapIGnTipoDenUrbana,direccion.getDenominacionUrbana().getTipoDenoId())+ " "+direccion.getDenominacionUrbana().getDescripcion();
			}else{
				strDesDom=getDenominacionDenUrbana(mapIGnTipoDenUrbana,direccion.getDenominacionUrbana().getTipoDenoId())+ " "+direccion.getDenominacionUrbana().getDescripcion();
			}
		}
						
		
		//'Referencia
		if(direccion.getReferencia().trim().length()>0)
		{
			if(strDesDom.trim().length()>0){
				if(direccion.getUbigeo().getDistrito().trim().length()>0)
				{
					if(strDesDom.trim().length()>0){
						strDesDom=strDesDom+", "+direccion.getUbigeo().getDistrito();
					}else{
						strDesDom=direccion.getUbigeo().getDistrito();
					}
				}
				strDesDom =strDesDom+ " - Ref: " + direccion.getReferencia();
			}else{
				//'Distrito
				strDesDom=direccion.getReferencia().trim();
				if(direccion.getUbigeo().getDistrito().trim().length()>0){
					if(strDesDom.trim().length()>0){
						//strDesDom=strDesDom+", "+direccion.getUbigeo().getDistrito();
						/*cchaucca 10/01/2014 : se retira la denominacion del sector tributario de la direccion contenada*/
						//strDesDom=strDesDom+", Cdra: "+numeroCuadra+", Sector: "+denominacionSector+", "+direccion.getUbigeo().getDistrito();
						strDesDom=strDesDom+", Cdra: "+numeroCuadra+", "+direccion.getUbigeo().getDistrito();
					}else{
						//strDesDom=direccion.getUbigeo().getDistrito();
						/*cchaucca 10/01/2014 : se retira la denominacion del sector tributario de la direccion contenada*/
						//strDesDom=", Cdra: "+numeroCuadra+", Sector: "+denominacionSector+", "+direccion.getUbigeo().getDistrito();
						strDesDom=", Cdra: "+numeroCuadra+", "+direccion.getUbigeo().getDistrito();
					}
				}
			}
		}else{
			//distrito
			if(direccion.getUbigeo().getDistrito().trim().length()>0)
			{
				if(strDesDom.trim().length()>0){
					//strDesDom=strDesDom+", "+direccion.getUbigeo().getDistrito();
					/*cchaucca 10/01/2014 : se retira la denominacion del sector tributario de la direccion contenada*/
					//strDesDom=strDesDom+", Cdra: "+numeroCuadra+", Sector: "+denominacionSector+", "+direccion.getUbigeo().getDistrito();
					strDesDom=strDesDom+", Cdra: "+numeroCuadra+", "+direccion.getUbigeo().getDistrito();
				}else{
					//strDesDom=direccion.getUbigeo().getDistrito();
					/*cchaucca 10/01/2014 : se retira la denominacion del sector tributario de la direccion contenada*/
					//strDesDom=", Cdra: "+numeroCuadra+", Sector: "+denominacionSector+", "+direccion.getUbigeo().getDistrito();
					strDesDom=", Cdra: "+numeroCuadra+", "+direccion.getUbigeo().getDistrito();
				}
			}
		}
		
		//strDesDom = strDesDom + ", Cdra: "+numeroCuadra+", Sector: "+denominacionSector;
		
		return strDesDom;
	}

	public ArrayList<ListRpDjPredial> getListRpDjpredial(Integer predioId,Integer personaId)throws Exception{
		ArrayList<ListRpDjPredial> lista=new ArrayList<ListRpDjPredial>();
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("SELECT d.dj_id,d.predio_id,d.anno_dj,d.area_terreno,d.area_terreno_has,d.fecha_adquisicion,d.fecha_declaracion,d.porc_propiedad, ");//--> linea original
//			SQL.append("SELECT d.dj_id,d.predio_id,d.anno_dj,d.area_terreno,d.area_terreno_has,d.fecha_adquisicion,dbo.fm_co_formato_fecha(d.fecha_adquisicion) fecha_adquisicion2,d.fecha_declaracion,d.porc_propiedad, "); //Modificado por ccchauca Marzo 2016 y Comentado:Nov-2016 (por error)
			SQL.append("d.ubicacion_predio_id,isnull(u.descripcion,d.desc_domicilio) ubicacion_predio, ");
			SQL.append("d.condicion_propiedad_id,c.descripcion condicion_propiedad, ");
			SQL.append("d.tipo_adquisicion_id,t.descripcion tipo_adquisicion, ");
			SQL.append("d.usuario_id, o.nombre_usuario, ");
			SQL.append("d.motivo_declaracion_id, m.descripcion motivo_declaracion, ");
			SQL.append("d.fiscalizado,d.fisca_aceptada,d.fisca_cerrada,d.flag_dj_anno,d.codigo_predio,d.estado, ");
			SQL.append("d.es_descargo, d.fecha_descargo, d.motivo_descargo_id, md.descripcion motivo_descargo, d.glosa, d.notaria_id, n.nombre_notaria, d.tipo_predio, ");//datos de descargo
			SQL.append("isnull(dj.direccion_completa,d.desc_domicilio) direccion_completa "); //direccion completa
			SQL.append("FROM ").append(Constante.schemadb).append(".rp_djpredial d ");
			SQL.append("left join ").append(Constante.schemadb).append(".rp_ubicacion_predio u on (u.ubicacion_predio_id=d.ubicacion_predio_id) ");//no existe cuando es rustico
			SQL.append("inner join ").append(Constante.schemadb).append(".rp_condicion_propiedad c on (c.condicion_propiedad_id=d.condicion_propiedad_id) ");
			SQL.append("left join ").append(Constante.schemadb).append(".rp_tipo_adquisicion t on(t.tipo_adquisicion_id=d.tipo_adquisicion_id) ");
			SQL.append("left join ").append(Constante.schemadb).append(".sg_usuario o on(o.usuario_id=d.usuario_id) ");
			SQL.append("left join ").append(Constante.schemadb).append(".rv_motivo_declaracion m on (m.motivo_declaracion_id=d.motivo_declaracion_id) ");//no registrado aun en el sistema
			SQL.append("left join ").append(Constante.schemadb).append(".gn_notaria n on (n.notaria_id = d.notaria_id) ");//notaria
			SQL.append("left join ").append(Constante.schemadb).append(".rv_motivo_descargo md on (md.motivo_descargo_id = d.motivo_descargo_id) ");//motivo de descargo
			SQL.append("left join ").append(Constante.schemadb).append(".rp_djdireccion dj on (dj.dj_id=d.dj_id and dj.estado='1') "); //para tener la direccion completa
			SQL.append("WHERE d.predio_id=? and d.persona_id=? and d.estado NOT IN ('").append(Constante.ESTADO_ELIMINADO).append("') order by d.anno_dj desc, d.dj_id desc");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, predioId);
			pst.setInt(2, personaId);
			
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				ListRpDjPredial obj=new ListRpDjPredial();
				obj.setDjId(rs.getInt("dj_id"));
				obj.setAnioDj(rs.getString("anno_dj"));
				obj.setAreaTerreno(rs.getBigDecimal("area_terreno"));
				obj.setAreaTerrenoHas(rs.getBigDecimal("area_terreno_has"));
				obj.setFechaAdquisicion(rs.getString("fecha_adquisicion"));
				obj.setFechaDeclaracion(rs.getTimestamp("fecha_declaracion"));
				obj.setPorcPropiedad(rs.getBigDecimal("porc_propiedad"));
				obj.setUbicacionPredio(rs.getString("ubicacion_predio"));//--
				obj.setCondicionPropiedad(rs.getString("condicion_propiedad"));//--
				obj.setTipoAdquisicion(rs.getString("tipo_adquisicion"));//--
				obj.setUsuario(rs.getString("nombre_usuario"));//--
				obj.setMotivoDeclaracion(rs.getString("motivo_declaracion"));
				obj.setFiscalizado(rs.getString("fiscalizado"));
				obj.setFiscaAceptada(rs.getString("fisca_aceptada"));
				obj.setFiscaCerrada(rs.getString("fisca_cerrada"));
				obj.setPredioId(rs.getInt("predio_id"));
				obj.setFlagDjAnno(rs.getString("flag_dj_anno").trim().equalsIgnoreCase("1")?Boolean.TRUE:Boolean.FALSE);
				obj.setCodigoPredio(rs.getString("codigo_predio"));
				//obj.setEstado(rs.getString("estado").trim().equals(Constante.ESTADO_ACTIVO)?"DEFINITIVO":"PENDIENTE");
				obj.setEstado(rs.getString("estado"));
				obj.setMotivoDeclaracionId(rs.getInt("motivo_declaracion_id"));
				//--
				obj.setPoseePendiente(existeDeclaracionPendiente(personaId,Util.toInteger(obj.getAnioDj()),predioId,Boolean.FALSE));
				obj.setPoseePendienteFisca(existeDeclaracionPendiente(personaId,Util.toInteger(obj.getAnioDj()),predioId,Boolean.TRUE));
				//Descargo
				obj.setEsDescargoFlag(rs.getString("es_descargo"));
				obj.setFechaDescargo(rs.getTimestamp("fecha_descargo"));				
				obj.setMontivoDescargoId(rs.getInt("motivo_descargo_id"));
				obj.setMotivoDescargo(rs.getString("motivo_descargo"));
				obj.setGlosa(rs.getString("glosa"));
				obj.setNotariaId(rs.getInt("notaria_id"));
				obj.setNombreNotaria(rs.getString("nombre_notaria"));				
				obj.setTipoPredio(rs.getString("tipo_predio"));
				obj.setDireccionCompleta(rs.getString("direccion_completa"));
				lista.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	
	public ArrayList<ListRpDjPredial> getListRpDjpredialTodos(Integer predioId,Integer personaId)throws Exception{
		ArrayList<ListRpDjPredial> lista=new ArrayList<ListRpDjPredial>();
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("SELECT d.dj_id,d.predio_id,d.anno_dj,d.area_terreno,d.area_terreno_has,d.fecha_adquisicion,d.fecha_declaracion,d.porc_propiedad, ");
			SQL.append("d.ubicacion_predio_id,isnull(u.descripcion,d.desc_domicilio) ubicacion_predio, ");
			SQL.append("d.condicion_propiedad_id,c.descripcion condicion_propiedad, ");
			SQL.append("d.tipo_adquisicion_id,t.descripcion tipo_adquisicion, ");
			SQL.append("d.usuario_id, o.nombre_usuario, ");
			SQL.append("d.motivo_declaracion_id, m.descripcion motivo_declaracion, ");
			SQL.append("d.fiscalizado,d.fisca_aceptada,d.fisca_cerrada,d.flag_dj_anno,d.codigo_predio,d.estado,d.motivo_declaracion_id, ");
			SQL.append("d.tipo_predio, d.glosa ");
			SQL.append("FROM ").append(Constante.schemadb).append(".rp_djpredial d ");
			SQL.append("left join ").append(Constante.schemadb).append(".rp_ubicacion_predio u on (u.ubicacion_predio_id=d.ubicacion_predio_id) ");//no existe cuando es rustico
			SQL.append("inner join ").append(Constante.schemadb).append(".rp_condicion_propiedad c on (c.condicion_propiedad_id=d.condicion_propiedad_id) ");
			SQL.append("left join ").append(Constante.schemadb).append(".rp_tipo_adquisicion t on(t.tipo_adquisicion_id=d.tipo_adquisicion_id) ");
			SQL.append("left join ").append(Constante.schemadb).append(".sg_usuario o on(o.usuario_id=d.usuario_id) ");
			SQL.append("left join ").append(Constante.schemadb).append(".rv_motivo_declaracion m on (m.motivo_declaracion_id=d.motivo_declaracion_id) ");//no registrado aun en el sistema
			SQL.append("WHERE d.predio_id=? and d.persona_id=? order by d.anno_dj desc, d.dj_id desc");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, predioId);
			pst.setInt(2, personaId);
			
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				ListRpDjPredial obj=new ListRpDjPredial();
				obj.setDjId(rs.getInt("dj_id"));
				obj.setAnioDj(rs.getString("anno_dj"));
				obj.setAreaTerreno(rs.getBigDecimal("area_terreno"));
				obj.setFechaAdquisicion(rs.getString("fecha_adquisicion"));
				obj.setFechaDeclaracion(rs.getTimestamp("fecha_declaracion"));
				obj.setPorcPropiedad(rs.getBigDecimal("porc_propiedad"));
				obj.setUbicacionPredio(rs.getString("ubicacion_predio"));//--
				obj.setCondicionPropiedad(rs.getString("condicion_propiedad"));//--
				obj.setTipoAdquisicion(rs.getString("tipo_adquisicion"));//--
				obj.setUsuario(rs.getString("nombre_usuario"));//--
				obj.setMotivoDeclaracion(rs.getString("motivo_declaracion"));
				obj.setFiscalizado(rs.getString("fiscalizado"));
				obj.setFiscaAceptada(rs.getString("fisca_aceptada"));
				obj.setFiscaCerrada(rs.getString("fisca_cerrada"));
				obj.setPredioId(rs.getInt("predio_id"));
				obj.setFlagDjAnno(rs.getString("flag_dj_anno").trim().equalsIgnoreCase("1")?Boolean.TRUE:Boolean.FALSE);
				obj.setCodigoPredio(rs.getString("codigo_predio"));
				//obj.setEstado(rs.getString("estado").trim().equals(Constante.ESTADO_ACTIVO)?"DEFINITIVO":"PENDIENTE");
				obj.setEstado(rs.getString("estado"));
				obj.setMotivoDeclaracionId(rs.getInt("motivo_declaracion_id"));
				//--
				obj.setPoseePendiente(existeDeclaracionPendiente(personaId,Util.toInteger(obj.getAnioDj()),predioId,Boolean.FALSE));
				obj.setPoseePendienteFisca(existeDeclaracionPendiente(personaId,Util.toInteger(obj.getAnioDj()),predioId,Boolean.TRUE));
				//--
				obj.setAreaTerrenoHas(rs.getBigDecimal("area_terreno_has"));
				obj.setTipoPredio(rs.getString("tipo_predio"));
				obj.setGlosa(rs.getString("glosa"));
				lista.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	
	public FindPersona getFindPersona(Integer personaId)throws Exception{
		FindPersona persona=null;
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("SELECT p.persona_id,p.tipo_persona_id,p.tipo_doc_identidad_id,p.nro_docu_identidad,p.ape_paterno,p.ape_materno,p.primer_nombre,p.razon_social,p.apellidos_nombres ");
			SQL.append(",t.descrpcion_corta desctipodocidentidad,stp.descripcion descsubtipopersona,tp.descripcion desctipopersona "); 
			SQL.append(",d.domicilio_completo ");
			SQL.append("FROM ").append(Constante.schemadb).append(".mp_persona p ");   
			SQL.append("INNER JOIN ").append(Constante.schemadb).append(".mp_tipo_docu_identidad t ON (t.tipo_doc_identidad_id=p.tipo_doc_identidad_id) ");   
			SQL.append("INNER JOIN ").append(Constante.schemadb).append(".mp_subtipo_persona stp ON (stp.subtipo_persona_id=p.subtipo_persona_id)  "); 
			SQL.append("INNER JOIN ").append(Constante.schemadb).append(".mp_tipo_persona tp ON (tp.tipo_persona_id=stp.tipo_persona_id) ");   
			SQL.append("INNER JOIN ").append(Constante.schemadb).append(".mp_persona_domicilio d ON (d.persona_id=p.persona_id and d.flag_fiscal='1') ");  
			SQL.append("WHERE p.persona_id=? ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, personaId);
			
			ResultSet rs=pst.executeQuery();
			if(rs.next()){
				persona=new FindPersona();
				persona.setPersonaId(rs.getInt("persona_id"));
				persona.setTipoPersonaId(rs.getInt("tipo_persona_id"));
				persona.setTipoDocIdentidadId(rs.getInt("tipo_doc_identidad_id"));
				persona.setNroDocuIdentidad(rs.getString("nro_docu_identidad"));
				persona.setApePaterno(rs.getString("ape_paterno"));
				persona.setApeMaterno(rs.getString("ape_materno"));
				persona.setPrimerNombre(rs.getString("primer_nombre"));
				persona.setRazonSocial(rs.getString("razon_social"));
				persona.setApellidosNombres(rs.getString("apellidos_nombres"));
				persona.setDenTipoDocIdentidad(rs.getString("desctipodocidentidad"));
				persona.setDescTipoPersona(rs.getString("desctipopersona"));
				persona.setDescSubTipoPersona(rs.getString("descsubtipopersona"));
				persona.setDireccionCompleta(rs.getString("domicilio_completo"));
			}
		}catch(Exception e){
			throw(e);
		}
		return persona;
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
	
	public ArrayList<FindPersona> getFindEmpresas()throws Exception{
		ArrayList<FindPersona> lista=new ArrayList<FindPersona>();
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("SELECT persona_id,tipo_persona_id,tipo_doc_identidad_id,nro_docu_identidad,ape_paterno,ape_materno,primer_nombre,razon_social,apellidos_nombres FROM ").append(Constante.schemadb).append(".mp_persona "); 
			SQL.append("where tipo_persona_id=2 ");
			
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
	
	public Integer getPersonaId(Integer tipDoc,String numeroDoc)throws Exception{
		Integer personaId=0;
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("SELECT persona_id FROM ").append(Constante.schemadb).append(".mp_persona "); 
			SQL.append("where tipo_doc_identidad_id=? and nro_docu_identidad=? ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, tipDoc);
			pst.setString(2, numeroDoc);
			
			ResultSet rs=pst.executeQuery();
			if(rs.next()){
				personaId=rs.getInt("persona_id");
			}
		}catch(Exception e){
			throw(e);
		}
		return personaId;
	}
	
	public FindDireccion verificaDireccion(Integer PredioId,Integer idVia,String numero1, String letra1,String numero2, String letra2,Integer edificacionId,String edificacion,Integer interiorId,String interior,String referencia)throws Exception{
		FindDireccion direccion=null;
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("select d.via_id,d.numero,d.letra,d.numero2,d.letra2,j.codigo_predio,j.desc_domicilio,j.fecha_declaracion, j.predio_id, j.persona_id, j.codigo_anterior ");
			SQL.append("from ").append(Constante.schemadb).append(".rp_djpredial j ");
			SQL.append("inner join ").append(Constante.schemadb).append(".rp_djdireccion d on (d.dj_id=j.dj_id and d.estado='").append(Constante.ESTADO_ACTIVO).append("') "); 
			SQL.append("where j.estado='").append(Constante.ESTADO_ACTIVO).append("' and j.flag_dj_anno='1' ");
			SQL.append("and d.via_id=? and j.predio_id!=? ");
			if(numero1!=null&&numero1.trim().length()>0){
				SQL.append("and UPPER(ltrim(rtrim(d.numero)))=UPPER(ltrim(rtrim(?))) ");	
			}
			 
			if(letra1!=null&&letra1.trim().length()>0){
				SQL.append("and UPPER(ltrim(rtrim(d.letra)))=UPPER(ltrim(rtrim(?)))  ");
			}
			
			if(numero2!=null&&numero2.trim().length()>0){
				SQL.append("and UPPER(ltrim(rtrim(d.numero2)))=UPPER(ltrim(rtrim(?)))  ");
			}
			
			if(letra2!=null&&letra2.trim().length()>0){
				SQL.append("and UPPER(ltrim(rtrim(d.letra2)))=UPPER(ltrim(rtrim(?)))  ");
			}
			
			if(edificacionId!=null&&edificacionId>0){
				SQL.append("and d.tipo_edificacion_id=?  ");
			}
			
			if(edificacion!=null&&edificacion.trim().length()>0){
				SQL.append("and UPPER(ltrim(rtrim(d.nombre_edificiacion)))=UPPER(ltrim(rtrim(?)))  ");
			}
			
			if(interiorId!=null&&interiorId>0){
				SQL.append("and d.tipo_interior_id=?  ");
			}
			
			if(interior!=null&&interior.trim().length()>0){
				SQL.append("and UPPER(ltrim(rtrim(d.numero_interior)))=UPPER(ltrim(rtrim(?)))  ");
			}
			
			if(referencia!=null&&referencia.trim().length()>0){
				SQL.append("and UPPER(ltrim(rtrim(d.referencia)))=UPPER(ltrim(rtrim(?)))  ");
			}
			
			int p=1;
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(p, idVia);
			pst.setInt(++p, PredioId);
			if(numero1!=null&&numero1.trim().length()>0){
				pst.setString(++p, numero1);	
			}
			 
			if(letra1!=null&&letra1.trim().length()>0){
				pst.setString(++p, letra1);
			}
			
			if(numero2!=null&&numero2.trim().length()>0){
				pst.setString(++p, numero2);
			}
			
			if(letra2!=null&&letra2.trim().length()>0){
				pst.setString(++p, letra2);
			}
			
			if(edificacionId!=null&&edificacionId>0){
				pst.setInt(++p, edificacionId);
			}
			
			if(edificacion!=null&&edificacion.trim().length()>0){
				pst.setString(++p, edificacion);
			}
			
//
			if(interiorId!=null&&interiorId>0){
				pst.setInt(++p, interiorId);
			}
			
			if(interior!=null&&interior.trim().length()>0){
				pst.setString(++p, interior);
			}
			
			if(referencia!=null&&referencia.trim().length()>0){
				pst.setString(++p, referencia);
			}
			
			ResultSet rs=pst.executeQuery();
			if(rs.next()){
				direccion=new FindDireccion();
				direccion.setViaId(rs.getInt("via_id"));
				direccion.setNumero(rs.getString("numero"));
				direccion.setLetra(rs.getString("letra"));
				direccion.setNumero2(rs.getString("numero2"));
				direccion.setLetra2(rs.getString("letra2"));
				direccion.setCodigoPredio(rs.getString("codigo_predio"));
				direccion.setCodigoAnterior(rs.getString("codigo_anterior"));
				direccion.setPredioId(rs.getString("predio_id"));
				direccion.setDescDomicilio(rs.getString("desc_domicilio"));
				direccion.setFechaDeclaracion(rs.getTimestamp("fecha_declaracion"));
				direccion.setPersonaId(rs.getString("persona_id"));
			}
		}catch(Exception e){
			throw(e);
		}
		return direccion;
	}
	//--
	public List<RpTipoObra> getAllRpCategoriaObra()throws Exception{
		List<RpTipoObra> lista=new LinkedList<RpTipoObra>();
		try{
			StringBuffer SQL=new StringBuffer("SELECT tipo_obra_id,descripcion FROM ").append(Constante.schemadb).append(".rp_tipo_obra ORDER by descripcion asc");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				RpTipoObra obj=new RpTipoObra(); 
				obj.setTipoObraId(rs.getInt("tipo_obra_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				lista.add(obj);
			}
			
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	
	public List<RpTipoObra> getAllRpTipoObra(Integer categoriaObraId)throws Exception{
		List<RpTipoObra> lista=new LinkedList<RpTipoObra>();
		try{
			StringBuffer SQL=new StringBuffer("SELECT tipo_obra_id,descripcion FROM ").append(Constante.schemadb).append(".rp_tipo_obra WHERE categoria_obra_id=? AND estado='1' ORDER by descripcion asc");
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, categoriaObraId);
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				RpTipoObra obj=new RpTipoObra(); 
				obj.setTipoObraId(rs.getInt("tipo_obra_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				lista.add(obj);
			}
			
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	
	public RpInstalacionDj getRpInstalacionDj(int djId,int instalacionId)throws Exception{
		RpInstalacionDj instalacion=null;
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("SELECT i.dj_id,instalacion_id,i.tipo_obra_id,i.anno_instalacion,i.mes_instalacion,i.valor_instalacion,i.referencia, "); 
			SQL.append("m.descripcion mesdescripcion,t.descripcion tipoinstalaciondescripcion,i.numero_documento, ");
			SQL.append("i.nro_nivel,i.area_terreno,i.mat_predominante_id,i.conservacion_id,i.clasi_depreciacion_id, ");
			SQL.append("c.categoria_obra_id ");
			SQL.append("FROM ").append(Constante.schemadb).append(".rp_instalacion_dj i "); 
			SQL.append("inner join ").append(Constante.schemadb).append(".rj_mes m on (m.mes_id=i.mes_instalacion) "); 
			SQL.append("inner join ").append(Constante.schemadb).append(".rp_tipo_obra t on (t.tipo_obra_id=i.tipo_obra_id) "); 
			SQL.append("inner join ").append(Constante.schemadb).append(".rp_categoria_obra c on (c.categoria_obra_id=t.categoria_obra_id) ");
			SQL.append("where i.dj_id=? and i.instalacion_id=? and i.estado='").append(Constante.ESTADO_ACTIVO).append("' ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, djId);
			pst.setInt(2, instalacionId);
			
			ResultSet rs=pst.executeQuery();
			int item=1;
			if(rs.next()){
				instalacion=new RpInstalacionDj(); 
				instalacion.setDjId(rs.getInt("dj_id"));
				instalacion.setInstalacionId(rs.getInt("instalacion_id"));
				instalacion.setAnnoInstalacion(rs.getInt("anno_instalacion"));
				instalacion.setMesInstalacion(rs.getString("mes_instalacion"));
				instalacion.setReferencia(rs.getString("referencia"));
				instalacion.setTipoObraId(rs.getInt("tipo_obra_id"));
				instalacion.setValorInstalacion(rs.getBigDecimal("valor_instalacion"));
				instalacion.setTipoInstalacion(rs.getString("tipoinstalaciondescripcion"));
				instalacion.setMes(rs.getString("referencia"));
				instalacion.setNumeroDocumento(rs.getString("numero_documento"));
				instalacion.setNroNivel(rs.getInt("nro_nivel"));
				instalacion.setAreaTerreno(rs.getBigDecimal("area_terreno"));
				instalacion.setMatPredominanteId(rs.getInt("mat_predominante_id"));
				instalacion.setConservacionId(rs.getInt("conservacion_id"));
				instalacion.setClasiDepreciacionId(rs.getInt("clasi_depreciacion_id"));
				instalacion.setCategoriaObraId(rs.getInt("categoria_obra_id"));
				
				instalacion.setItem(item++);
			}
		}catch(Exception e){
			throw(e);
		}
		return instalacion;
	}
	
	public RpOtrosFrente getRpOtrosFrente(int djId,int otroFrenteId)throws Exception{
		RpOtrosFrente frente=null;
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("SELECT o.dj_id,o.otro_frente_id,o.nro_puerta_1,o.nro_puerta_2,o.nro_puerta_3 "); 
			SQL.append(",o.nro_puerta_4,o.referencia,o.ubicacion_id,o.numero1,  ");
			SQL.append("v.descripcion viadescripcion,t.descripcion tipoviadescripcion "); 
			SQL.append("FROM ").append(Constante.schemadb).append(".rp_otros_frentes o  ");
			SQL.append("INNER JOIN ").append(Constante.schemadb).append(".gn_ubicacion u on (u.ubicacion_id=o.ubicacion_id) ");
			SQL.append("INNER JOIN ").append(Constante.schemadb).append(".gn_via v on (v.via_id=u.via_id)  ");
			SQL.append("INNER JOIN ").append(Constante.schemadb).append(".gn_tipo_via t on (t.tipo_via_id=v.tipo_via_id) "); 
			SQL.append("WHERE o.dj_id=? and o.otro_frente_id=? and o.estado='").append(Constante.ESTADO_ACTIVO).append("'  ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, djId);
			pst.setInt(2, otroFrenteId);
			
			ResultSet rs=pst.executeQuery();
			int item=1;
			while(rs.next()){
				frente=new RpOtrosFrente(); 
				frente.setDjId(rs.getInt("dj_id"));
				frente.setOtroFrenteId(rs.getInt("otro_frente_id"));
				frente.setNroPuerta1(rs.getString("nro_puerta_1"));
				frente.setNroPuerta2(rs.getString("nro_puerta_2"));
				frente.setNroPuerta3(rs.getString("nro_puerta_3"));
				frente.setNroPuerta4(rs.getString("nro_puerta_4"));
				frente.setReferencia(rs.getString("referencia"));
				frente.setTipoVia(rs.getString("tipoviadescripcion"));
				frente.setNombreVia(rs.getString("viadescripcion"));
				frente.setUbicacionId(rs.getInt("ubicacion_id"));
				frente.setNumero1(rs.getString("numero1"));
			}
		}catch(Exception e){
			throw(e);
		}
		return frente;
	}
	
	public RpDjuso getRpDjuso(int arbitrioId,int usoId)throws Exception{
		RpDjuso uso=null;
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("select u.djarbitrio_id,u.djuso_id,u.tipo_uso_id,u.area_uso,u.mes_inicio,u.mes_fin,u.anno_afectacion, ");
			SQL.append("t.descripcion tipousodescripcion ");
			SQL.append("from ").append(Constante.schemadb).append(".rp_djuso u ");
			SQL.append("inner join ").append(Constante.schemadb).append(".rp_tipo_uso t on (u.tipo_uso_id=t.tipo_uso_id) ");
			SQL.append("where u.djarbitrio_id=? and u.djuso_id=? and u.estado='").append(Constante.ESTADO_ACTIVO).append("' ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, arbitrioId);
			pst.setInt(2, usoId);
			
			ResultSet rs=pst.executeQuery();
			int item=1;
			while(rs.next()){
				uso=new RpDjuso(); 
				uso.setDjarbitrioId(rs.getInt("djarbitrio_id"));
				uso.setDjusoId(rs.getInt("djuso_id"));
				uso.setAnnoAfectacion(rs.getInt("anno_afectacion"));
				uso.setAreaUso(rs.getBigDecimal("area_uso"));
				uso.setMesFin(rs.getString("mes_fin"));
				uso.setMesInicio(rs.getString("mes_inicio"));
				uso.setTipoUsoId(rs.getInt("tipo_uso_id"));
				uso.setTipoUsoDescripcion(rs.getString("tipousodescripcion"));
			}
		}catch(Exception e){
			throw(e);
		}
		return uso;
	}
	
	public RjDocuAnexo getRjDocuAnexo(int DjId,int docuAnexoId)throws Exception{
		RjDocuAnexo docuAnexo=new RjDocuAnexo();
		try{
			StringBuffer SQL=new StringBuffer();
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, DjId);
			pst.setInt(2, docuAnexoId);
			
			ResultSet rs=pst.executeQuery();
			int item=1;
			if(rs.next()){
				docuAnexo=new RjDocuAnexo(); 
			}
		}catch(Exception e){
			throw(e);
		}
		return docuAnexo;
	}
	
	/*
	public int actualizarRpInstalacionDj(RpInstalacionDj rpInstalacionDj)throws Exception{
		int result=0;
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("UPDATE ").append(Constante.schemadb).append(".rp_instalacion_dj ");
			SQL.append("SET tipo_obra_id = ? ");
			SQL.append(",anno_instalacion = ? ");
			SQL.append(",mes_instalacion = ? ");
			SQL.append(",valor_instalacion = ? ");
			SQL.append(",referencia = ? ");
			SQL.append(",usuario_id = ? ");
			SQL.append(",estado = ? ");
			SQL.append(",fecha_registro = ? ");
			SQL.append(",terminal = ? ");
			SQL.append(",numero_documento = ? ");
			SQL.append("WHERE dj_id=? and instalacion_id=? ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1,rpInstalacionDj.getTipoObraId());
			pst.setInt(2,rpInstalacionDj.getAnnoInstalacion());
			pst.setString(3,rpInstalacionDj.getMesInstalacion());
			pst.setBigDecimal(4,rpInstalacionDj.getValorInstalacion());
			pst.setString(5,rpInstalacionDj.getReferencia());
			pst.setInt(6,rpInstalacionDj.getUsuarioId());//b
			pst.setString(7,rpInstalacionDj.getEstado());
			pst.setTimestamp(8,rpInstalacionDj.getFechaRegistro());
			pst.setString(9,rpInstalacionDj.getTerminal());
			pst.setString(10,rpInstalacionDj.getNumeroDocumento());
			pst.setInt(11,rpInstalacionDj.getDjId());//a
			pst.setInt(12,rpInstalacionDj.getInstalacionId());//a
			//--
			result=pst.executeUpdate();
		}catch(Exception e){
			throw(e);
		}
		return result;
	}
	*/
	
	public int guardarRpInstalacionDj(RpInstalacionDj rpInstalacionDj)throws Exception{
		int result=0;
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("INSERT INTO ").append(Constante.schemadb).append(".rp_instalacion_dj (dj_id,tipo_obra_id,anno_instalacion,mes_instalacion,valor_instalacion,referencia ");
			SQL.append(",usuario_id,estado,fecha_registro,terminal,numero_documento) ");
			SQL.append("VALUES(?,?,?,?,?,? ");
			SQL.append(",?,?,?,?,?) ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setObject(1,rpInstalacionDj.getDjId());//a
			pst.setObject(2,rpInstalacionDj.getTipoObraId());
			pst.setObject(3,rpInstalacionDj.getAnnoInstalacion());
			pst.setString(4,rpInstalacionDj.getMesInstalacion());
			pst.setBigDecimal(5,rpInstalacionDj.getValorInstalacion());
			pst.setString(6,rpInstalacionDj.getReferencia());
				pst.setObject(7,rpInstalacionDj.getUsuarioId());//b
				pst.setString(8,rpInstalacionDj.getEstado());
				pst.setTimestamp(9,rpInstalacionDj.getFechaRegistro());
				pst.setString(10,rpInstalacionDj.getTerminal());
				pst.setString(11,rpInstalacionDj.getNumeroDocumento());
			//--
			result=pst.executeUpdate();
		}catch(Exception e){
			throw(e);
		}
		return result;
	}
	
	public ArrayList<RpInstalacionDj> getAllRpInstalacionDj(int djId)throws Exception{
		ArrayList<RpInstalacionDj> lista=new ArrayList<RpInstalacionDj>();
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("SELECT i.dj_id,instalacion_id,i.tipo_obra_id,i.anno_instalacion,ltrim(rtrim(i.mes_instalacion)) mes_instalacion");
			SQL.append(",i.valor_instalacion,i.referencia, ");
			SQL.append("m.descripcion mesdescripcion,t.descripcion tipoinstalaciondescripcion,i.numero_documento,i.nro_nivel,i.area_terreno,");
			SQL.append("i.valor_obra,i.valor_unitario_deprec,i.mat_predominante_id,i.conservacion_id,i.clasi_depreciacion_id ");
			SQL.append("FROM ").append(Constante.schemadb).append(".rp_instalacion_dj i ");
			SQL.append("inner join ").append(Constante.schemadb).append(".rj_mes m on (m.mes_id=i.mes_instalacion) ");
			SQL.append("inner join ").append(Constante.schemadb).append(".rp_tipo_obra t on (t.tipo_obra_id=i.tipo_obra_id) ");
			SQL.append("where i.dj_id=? and i.estado='").append(Constante.ESTADO_ACTIVO).append("' ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, djId);
			
			ResultSet rs=pst.executeQuery();
			int item=1;
			while(rs.next()){
				RpInstalacionDj instalacion=new RpInstalacionDj(); 
				instalacion.setDjId(rs.getInt("dj_id"));
				instalacion.setInstalacionId(rs.getInt("instalacion_id"));
				instalacion.setAnnoInstalacion(rs.getInt("anno_instalacion"));
				instalacion.setMesInstalacion(rs.getString("mes_instalacion"));
				instalacion.setReferencia(rs.getString("referencia"));
				instalacion.setTipoObraId(rs.getInt("tipo_obra_id"));
				instalacion.setValorInstalacion(rs.getBigDecimal("valor_instalacion"));
				instalacion.setTipoInstalacion(rs.getString("tipoinstalaciondescripcion"));
				instalacion.setMes(rs.getString("mesdescripcion"));
				instalacion.setNumeroDocumento(rs.getString("numero_documento"));
				instalacion.setNroNivel(rs.getInt("nro_nivel"));
				instalacion.setAreaTerreno(rs.getBigDecimal("area_terreno"));
				instalacion.setValorObra(rs.getBigDecimal("valor_obra"));
				instalacion.setValorUnitarioDepreciado(rs.getBigDecimal("valor_unitario_deprec"));
				instalacion.setMatPredominanteId(rs.getInt("mat_predominante_id"));
				instalacion.setConservacionId(rs.getInt("conservacion_id"));
				instalacion.setClasiDepreciacionId(rs.getInt("clasi_depreciacion_id"));
				instalacion.setItem(item++);
				lista.add(instalacion);
			}
			
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	
	public int guardarRpOtrosFrente(RpOtrosFrente rpOtrosFrente)throws Exception{
		int result=0;
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("INSERT INTO ").append(Constante.schemadb).append(".rp_otros_frentes(dj_id,usuario_id,estado,fecha_registro,terminal,ubicacion_id,numero1,frente) ");
			SQL.append("VALUES(?,?,?,?,?,?,?,?) ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1,rpOtrosFrente.getDjId());//a
			pst.setInt(2,rpOtrosFrente.getUsuarioId());
			pst.setString(3,rpOtrosFrente.getEstado());
			pst.setTimestamp(4,rpOtrosFrente.getFechaRegistro());
			pst.setString(5,rpOtrosFrente.getTerminal());
			pst.setInt(6,rpOtrosFrente.getUbicacionId());
			pst.setString(7,rpOtrosFrente.getNumero1());
			pst.setBigDecimal(8,rpOtrosFrente.getFrente());
			//--
			result=pst.executeUpdate();
		}catch(Exception e){
			throw(e);
		}
		return result;
	}
	
	public ArrayList<RpOtrosFrente> getAllRpOtrosFrente(int djId)throws Exception{
		ArrayList<RpOtrosFrente> lista=new ArrayList<RpOtrosFrente>();
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("SELECT o.dj_id,o.otro_frente_id,o.nro_puerta_1,o.nro_puerta_2,o.nro_puerta_3 ,o.nro_puerta_4,o.referencia, ");
			SQL.append("o.ubicacion_id,o.numero1, v.descripcion viadescripcion,t.descripcion tipoviadescripcion,o.frente ");
			SQL.append("FROM ").append(Constante.schemadb).append(".rp_otros_frentes o ");
			SQL.append("INNER JOIN ").append(Constante.schemadb).append(".gn_ubicacion u on (u.ubicacion_id=o.ubicacion_id) ");
			SQL.append("INNER JOIN ").append(Constante.schemadb).append(".gn_via v on (u.via_id=v.via_id) ");
			SQL.append("INNER JOIN ").append(Constante.schemadb).append(".gn_tipo_via t on (t.tipo_via_id=v.tipo_via_id) ");
			SQL.append("WHERE o.dj_id=? and o.estado='").append(Constante.ESTADO_ACTIVO).append("'  ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, djId);
			
			ResultSet rs=pst.executeQuery();
			int item=1;
			while(rs.next()){
				RpOtrosFrente frente=new RpOtrosFrente(); 
				frente.setDjId(rs.getInt("dj_id"));
				frente.setOtroFrenteId(rs.getInt("otro_frente_id"));
				frente.setNroPuerta1(rs.getString("nro_puerta_1"));
				frente.setNroPuerta2(rs.getString("nro_puerta_2"));
				frente.setNroPuerta3(rs.getString("nro_puerta_3"));
				frente.setNroPuerta4(rs.getString("nro_puerta_4"));
				frente.setReferencia(rs.getString("referencia"));
				frente.setTipoVia(rs.getString("tipoviadescripcion"));
				frente.setNombreVia(rs.getString("viadescripcion"));
				frente.setUbicacionId(rs.getInt("ubicacion_id"));
				frente.setNumero1(rs.getString("numero1"));
				frente.setFrente(rs.getBigDecimal("frente"));
				frente.setItem(item++);
				lista.add(frente);
			}
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}

	public List<RpTipoUso> getAllRpTipoUso()throws Exception{            
		List<RpTipoUso> lista=new LinkedList<RpTipoUso>();
		try{
			StringBuffer SQL=new StringBuffer("SELECT tipo_uso_id,descripcion FROM ").append(Constante.schemadb).append(".rp_tipo_uso WHERE estado='").append(Constante.ESTADO_ACTIVO).append("' ORDER BY descripcion");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				RpTipoUso obj=new RpTipoUso(); 
				obj.setTipoUsoId(rs.getInt("tipo_uso_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				lista.add(obj);
			}
			
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}	
	
	public List<RpTipoUso> getAllRpTipoUsos(int anio)throws Exception{                
		List<RpTipoUso> lista=new LinkedList<RpTipoUso>();
		try{
			StringBuilder SQL = new StringBuilder("exec stp_getAllTipoUso ?");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, anio);
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				RpTipoUso obj=new RpTipoUso(); 
				obj.setTipoUsoId(rs.getInt("tipo_uso_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				lista.add(obj);
			}
			
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}	
	
	public List<RpTipoUso> getAllRpTipoUsoSinTerreno(int anio)throws Exception{
		List<RpTipoUso> lista=new LinkedList<RpTipoUso>();
		try{
//			StringBuffer SQL=new StringBuffer("SELECT tipo_uso_id,descripcion FROM ").append(Constante.schemadb).append(".rp_tipo_uso WHERE estado='").append(Constante.ESTADO_ACTIVO).append("' and tipo_uso_id<>44 ORDER BY descripcion");
			StringBuilder SQL = new StringBuilder("exec stp_getAllTipoUso_Terreno ?");
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, anio);
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				RpTipoUso obj=new RpTipoUso(); 
				obj.setTipoUsoId(rs.getInt("tipo_uso_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				lista.add(obj);
			}
			
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	
	public int actualizaAreaUsoRpDjuso(RpDjuso uso)throws Exception{
		int result=0;
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append(" UPDATE ").append(Constante.schemadb).append(".rp_djuso ");
			SQL.append(" SET area_uso = ? ");
			SQL.append(" WHERE djarbitrio_id=? and djuso_id=?");

			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setBigDecimal(1,uso.getAreaUso());//a
			pst.setInt(2,uso.getDjarbitrioId());//a
			pst.setInt(3,uso.getDjusoId());//a
			//--
			result=pst.executeUpdate();
		}catch(Exception e){
			throw(e);
		}
		return result;
	}
	
	public ArrayList<RpDjuso> getAllRpDjuso(int arbitrioId)throws Exception{
		ArrayList<RpDjuso> lista=new ArrayList<RpDjuso>(); 
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("select u.djarbitrio_id,u.djuso_id,u.tipo_uso_id,u.area_uso,rtrim(ltrim(u.mes_inicio)) mes_inicio,rtrim(ltrim(u.mes_fin)) mes_fin,u.anno_afectacion, t.descripcion tipousodescripcion,mi.descripcion desc_mes_inicio,mf.descripcion desc_mes_fin ");
			SQL.append("from ").append(Constante.schemadb).append(".rp_djuso u  ");
			SQL.append("inner join ").append(Constante.schemadb).append(".rp_tipo_uso t on (u.tipo_uso_id=t.tipo_uso_id) "); 
			SQL.append("left join ").append(Constante.schemadb).append(".rj_mes mi on (mi.mes_id=u.mes_inicio) ");
			SQL.append("left join ").append(Constante.schemadb).append(".rj_mes mf on (mf.mes_id=u.mes_fin) ");
			SQL.append("where u.djarbitrio_id=? and u.estado='").append(Constante.ESTADO_ACTIVO).append("'  ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, arbitrioId);
			ResultSet rs=pst.executeQuery();
			int item=1;
			while(rs.next()){
				RpDjuso uso=new RpDjuso();
				uso.setItem(item++);
				uso.setDjarbitrioId(rs.getInt("djarbitrio_id"));
				uso.setDjusoId(rs.getInt("djuso_id"));
				uso.setAnnoAfectacion(rs.getInt("anno_afectacion"));
				uso.setAreaUso(rs.getBigDecimal("area_uso"));
				uso.setMesFin(rs.getString("desc_mes_fin"));
				uso.setMesInicio(rs.getString("desc_mes_inicio"));
				uso.setMesFinId(rs.getString("mes_fin"));
				uso.setMesInicioId(rs.getString("mes_inicio"));
				uso.setTipoUsoId(rs.getInt("tipo_uso_id"));
				uso.setTipoUsoDescripcion(rs.getString("tipousodescripcion"));
				lista.add(uso);
			}
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	
	public ArrayList<RpDjuso> getAllRpDjusoNuevo(int arbitrioId)throws Exception{
		ArrayList<RpDjuso> lista=new ArrayList<RpDjuso>(); 
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("select u.djarbitrio_id,u.djuso_id,t.uso_id as tipo_uso_id,u.area_uso,rtrim(ltrim(u.mes_inicio)) mes_inicio,rtrim(ltrim(u.mes_fin)) mes_fin,u.anno_afectacion, t.descripcion tipousodescripcion,mi.descripcion desc_mes_inicio,mf.descripcion desc_mes_fin ");
			SQL.append("from ").append(Constante.schemadb).append(".rp_djuso u  ");
			SQL.append("inner join ").append(Constante.schemadb).append(".rp_tipo_uso t on (u.tipo_uso_id=t.tipo_uso_id) "); 
			SQL.append("left join ").append(Constante.schemadb).append(".rj_mes mi on (mi.mes_id=u.mes_inicio) ");
			SQL.append("left join ").append(Constante.schemadb).append(".rj_mes mf on (mf.mes_id=u.mes_fin) ");
			SQL.append("where u.djarbitrio_id=? and u.estado='").append(Constante.ESTADO_ACTIVO).append("'  ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, arbitrioId);
			ResultSet rs=pst.executeQuery();
			int item=1;
			while(rs.next()){
				RpDjuso uso=new RpDjuso();
				uso.setItem(item++);
				uso.setDjarbitrioId(rs.getInt("djarbitrio_id"));
				uso.setDjusoId(rs.getInt("djuso_id"));
				uso.setAnnoAfectacion(rs.getInt("anno_afectacion"));
				uso.setAreaUso(rs.getBigDecimal("area_uso"));
				uso.setMesFin(rs.getString("desc_mes_fin"));
				uso.setMesInicio(rs.getString("desc_mes_inicio"));
				uso.setMesFinId(rs.getString("mes_fin"));
				uso.setMesInicioId(rs.getString("mes_inicio"));
				uso.setTipoUsoId(rs.getInt("tipo_uso_id"));
				uso.setTipoUsoDescripcion(rs.getString("tipousodescripcion"));
				lista.add(uso);
			}
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	
	//ITANTAMANGO
	public ArrayList<RjDocuAnexo> getAllRjDocuAnexo(int djId)throws Exception{
		ArrayList<RjDocuAnexo> lista=new ArrayList<RjDocuAnexo>(); 
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("SELECT x.docu_anexo_id,x.dj_id,x.referencia,x.numero_documento,x.usuario_id,x.content_id ");
			SQL.append(",x.fecha_actualizacion,x.fecha_registro,x.terminal,x.tipo_documento_id,d.descripcion tipodocumentodescripcion ");
			SQL.append("FROM ").append(Constante.schemadb).append(".rj_docu_anexo x  ");
			SQL.append("inner join ").append(Constante.schemadb).append(".rv_documento_sustentatorio d on(d.doc_sustentatorio_id=x.tipo_documento_id) "); 
			SQL.append("where x.dj_id=? and x.estado='").append(Constante.ESTADO_ACTIVO).append("' order by x.docu_anexo_id ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, djId);
			ResultSet rs=pst.executeQuery();
			int item=1;
			while(rs.next()){
				RjDocuAnexo obj=new RjDocuAnexo();
				obj.setDocuAnexoId(rs.getInt("docu_anexo_id"));
				obj.setDjId(rs.getInt("dj_id"));
				obj.setNumeroDocumento(rs.getString("numero_documento"));
				obj.setReferencia(rs.getString("referencia"));
				obj.setTipoDocumentoId(rs.getInt("tipo_documento_id"));
				obj.setTipoDocumento(rs.getString("tipodocumentodescripcion"));
				obj.setContentId(rs.getInt("content_id"));
				obj.setItem(item++);
				lista.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	

	public ArrayList<RelacionadosDTO> getAllRelacionados(Integer personaId) throws Exception{
		ArrayList<RelacionadosDTO> lista=new ArrayList<RelacionadosDTO>(); 
		try{
			//System.out.println("DAO getAllRelacionados");
			StringBuffer SQL=new StringBuffer();
			SQL.append("select distinct(r.apellidos_nombres) as nombres,tr.descripcion as relacion,td.descripcion as tipo_doc,r.nro_docu_identidad,r.porc_participacion ");
			SQL.append("FROM ").append(Constante.schemadb).append(".mp_relacionado r ");
			SQL.append("INNER JOIN ").append(Constante.schemadb).append(".mp_tipo_relacion tr ON (tr.tipo_relacion_id=r.tipo_relacion_id) ");
			SQL.append("INNER JOIN ").append(Constante.schemadb).append(".mp_tipo_docu_identidad td ON td.tipo_doc_identidad_id=r.tipo_doc_identidad_id ");
			SQL.append("WHERE persona_id=? AND r.estado='1'");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			//System.out.println("SQL"+SQL.toString());
			pst.setInt(1, personaId);
			
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				RelacionadosDTO relacion=new RelacionadosDTO(); 
				relacion.setNombres(rs.getString("nombres"));
				relacion.setRelacion(rs.getString("relacion"));
				relacion.setTipoDoc(rs.getString("tipo_doc"));
				relacion.setNumeroDocuIdentidad(rs.getString("nro_docu_identidad"));
				relacion.setPorcParticipacion(rs.getBigDecimal("porc_participacion"));
				
				lista.add(relacion);
			}
			
			//System.out.println("cantidad de relacionados"+lista.size());
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	
	public int deleteRjDocuAnexo(int djId,int docuAnexoId)throws Exception{
		int result=0;
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append(" UPDATE ").append(Constante.schemadb).append(".rj_docu_anexo SET estado='9' WHERE dj_id=? and docu_anexo_id=? ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1,djId);
			pst.setInt(2,docuAnexoId);
			//--
			result=pst.executeUpdate();
		}catch(Exception e){
			throw(e);
		}
		return result;
	}
	
	public int deleteRpDjconstruccion(int djId,int construccionId)throws Exception{
		int result=0;
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append(" UPDATE ").append(Constante.schemadb).append(".rp_djconstruccion SET estado='9' WHERE dj_id=? and construccion_id=? ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1,djId);
			pst.setInt(2,construccionId);
			//--
			result=pst.executeUpdate();
			if(result>0){
				StringBuffer SQL2=new StringBuffer();
				SQL2.append(" UPDATE ").append(Constante.schemadb).append(".rp_djuso_detalle SET estado='9' WHERE construccion_id=? ");
				
				PreparedStatement pst2=connect().prepareStatement(SQL2.toString());
				pst2.setInt(1,construccionId);
				int result2=pst2.executeUpdate();
			}
		}catch(Exception e){
			throw(e);
		}
		return result;
	}
	
	public int deleteRpDjuso(int djarbitrioId,int djusoId)throws Exception{
		int result=0;
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append(" UPDATE ").append(Constante.schemadb).append(".rp_djuso SET estado='9' WHERE djarbitrio_id=? and djuso_id=? ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1,djarbitrioId);
			pst.setInt(2,djusoId);
			//--
			result=pst.executeUpdate();
			if(result>0){
				StringBuffer SQL2=new StringBuffer();
				SQL2.append(" UPDATE ").append(Constante.schemadb).append(".rp_djuso_detalle SET estado='9' WHERE djuso_id=? ");
				
				PreparedStatement pst2=connect().prepareStatement(SQL2.toString());
				pst2.setInt(1,djusoId);
				int result2=pst2.executeUpdate();
			}
		}catch(Exception e){
			throw(e);
		}
		return result;
	}
	
	public int deleteRpOtrosFrente(int djId,int otroFrenteId)throws Exception{
		int result=0;
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append(" UPDATE ").append(Constante.schemadb).append(".rp_otros_frentes SET estado='9' WHERE dj_id=?  and otro_frente_id=? ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1,djId);
			pst.setInt(2,otroFrenteId);
			//--
			result=pst.executeUpdate();
		}catch(Exception e){
			throw(e);
		}
		return result;
	}

	public int deleteRpInstalacionDj(int djId,int instalacionId)throws Exception{
		int result=0;
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append(" UPDATE ").append(Constante.schemadb).append(".rp_instalacion_dj SET estado='9' WHERE dj_id=? AND instalacion_id=? ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1,djId);
			pst.setInt(2,instalacionId);
			//--
			result=pst.executeUpdate();
		}catch(Exception e){
			throw(e);
		}
		return result;
	}
	
	public Integer getDjArbitrioId(int djId)throws Exception{
		Integer djArbitrioId=null;
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append(" SELECT djarbitrio_id FROM ").append(Constante.schemadb).append(".rp_djarbitrios where dj_id=? ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1,djId);
			
			//--
			ResultSet rs=pst.executeQuery();
			if(rs.next()){
				djArbitrioId=rs.getInt("djarbitrio_id");
			}
			
		}catch(Exception e){
			throw(e);
		}
		return djArbitrioId;
	}
	
	public int guardarDjArbitrioId(RpDjarbitrio rpDjarbitrio)throws Exception{
		int result=0;
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("INSERT INTO ").append(Constante.schemadb).append(".rp_djarbitrios(dj_id,fecha_registro,estado,usuario_id,terminal) ");
			SQL.append("VALUES(?,?,?,?,?) ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1,rpDjarbitrio.getDjId());
			pst.setTimestamp(2,rpDjarbitrio.getFechaRegistro());
			pst.setString(3,rpDjarbitrio.getEstado());
			pst.setInt(4,rpDjarbitrio.getUsuarioId());
			pst.setString(5,rpDjarbitrio.getTerminal());

			//--
			result=pst.executeUpdate();
		}catch(Exception e){
			throw(e);
		}
		return result;
	}

	public int guardarRpDjuso(RpDjuso rpDjuso)throws Exception{
		int result=0;
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append(" INSERT INTO ").append(Constante.schemadb).append(".rp_djuso(djarbitrio_id,tipo_uso_id,area_uso,mes_inicio,mes_fin ");
			SQL.append(" ,anno_afectacion,usuario_id,estado,fecha_registro,terminal) ");
			SQL.append(" VALUES(?,?,?,?,? ");
			SQL.append(" ,?,?,?,?,?) ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1,rpDjuso.getDjarbitrioId());//a
			pst.setInt(2,rpDjuso.getTipoUsoId());
			pst.setBigDecimal(3,rpDjuso.getAreaUso());
			pst.setString(4,rpDjuso.getMesInicioId());
			pst.setString(5,rpDjuso.getMesFinId());
				pst.setInt(6,rpDjuso.getAnnoAfectacion());
				pst.setInt(7,rpDjuso.getUsuarioId());
				pst.setString(8,rpDjuso.getEstado());
				pst.setTimestamp(9,rpDjuso.getFechaRegistro());
				pst.setString(10,rpDjuso.getTerminal());
			//--
			result=pst.executeUpdate();
		}catch(Exception e){
			throw(e);
		}
		return result;
	}
	
	public int getUltimoDjUsoId(Integer djarbitrio_id)throws Exception{
		int result=0;
		try{
			StringBuffer SQL=new StringBuffer("SELECT MAX(djuso_id) as id FROM ").append(Constante.schemadb).append(".rp_djuso where djarbitrio_id=?");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1,djarbitrio_id);
			
			ResultSet rs=pst.executeQuery();
			if(rs.next()){
				result=rs.getInt("id");
				
			}
			
		}catch(Exception e){
			throw(e);
		}
		return result;
	}
	
	public int actualizaRpDjuso(RpDjuso rpDjuso)throws Exception{
		int result=0;
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append(" UPDATE ").append(Constante.schemadb).append(".rp_djuso SET  ");
			SQL.append(" tipo_uso_id = ? ");
			SQL.append(" ,area_uso = ? ");
			SQL.append(" ,mes_inicio = ? ");
			SQL.append(" ,mes_fin = ? ");
			SQL.append(" ,anno_afectacion = ? ");
			SQL.append(" ,usuario_id = ? ");
			SQL.append(" ,estado = ? ");
			SQL.append(" ,fecha_registro = ? ");
			SQL.append(" ,terminal = ? ");
			SQL.append(" WHERE djuso_id=? AND djarbitrio_id=? ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1,rpDjuso.getTipoUsoId());
			pst.setBigDecimal(2,rpDjuso.getAreaUso());
			pst.setString(3,rpDjuso.getMesInicioId());
			pst.setString(4,rpDjuso.getMesFinId());
			pst.setInt(5,rpDjuso.getAnnoAfectacion());
			pst.setInt(6,rpDjuso.getUsuarioId());
			pst.setString(7,rpDjuso.getEstado());
			pst.setTimestamp(8,rpDjuso.getFechaRegistro());
			pst.setString(9,rpDjuso.getTerminal());
			pst.setInt(10,rpDjuso.getDjusoId());//a
			pst.setInt(11,rpDjuso.getDjarbitrioId());//a	
			//--
			result=pst.executeUpdate();
		}catch(Exception e){
			throw(e);
		}
		return result;
	}
	
	public int deleteRpDjUsoDetalle(int rpDjusoId)throws Exception{
		int result=0;
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append(" UPDATE ").append(Constante.schemadb).append(".rp_djuso_detalle SET estado='9' WHERE djuso_id=? ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, rpDjusoId);
			//--
			result=pst.executeUpdate();
		}catch(Exception e){
			throw(e);
		}
		return result;
	}
	
	public RpDjusoDetalle getRpDjusoDetalle(int dJUsoId,int construccionId)throws Exception{
		RpDjusoDetalle detalle=null;
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("SELECT djuso_detalle_id,djuso_id,construccion_id,area_uso,mes_inicio,mes_fin, glosa  ");
			SQL.append(",usuario_id,estado,fecha_registro,terminal ");
			SQL.append("FROM ").append(Constante.schemadb).append(".rp_djuso_detalle ");
			SQL.append("WHERE djuso_id=? AND construccion_id=? AND estado='").append(Constante.ESTADO_ACTIVO).append("' ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, dJUsoId);
			pst.setInt(2, construccionId);
			ResultSet rs=pst.executeQuery();
			int item=1;
			if(rs.next()){
				detalle=new RpDjusoDetalle(); 
				detalle.setDjusoDetalleId(rs.getInt("djuso_detalle_id"));
				detalle.setDjusoId(rs.getInt("djuso_id"));
				detalle.setConstruccionId(rs.getInt("construccion_id"));
				detalle.setAreaUso(rs.getBigDecimal("area_uso"));
				detalle.setMesInicio(rs.getString("mes_inicio"));
				detalle.setMesFin(rs.getString("mes_fin"));
				detalle.setGlosa(rs.getString("glosa"));
				detalle.setUsuarioId(rs.getInt("usuario_id"));
				detalle.setEstado(rs.getString("estado"));
				detalle.setFechaRegistro(rs.getTimestamp("fecha_registro"));
				detalle.setTerminal(rs.getString("terminal"));
			}
		}catch(Exception e){
			throw(e);
		}
		return detalle;
	}
	
	public ArrayList<RpDjusoDetalle> getAllRpDjusoDetalle(int dJUsoId)throws Exception{
		ArrayList<RpDjusoDetalle> lista=new ArrayList<RpDjusoDetalle>();
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("SELECT d.djuso_detalle_id,d.djuso_id,d.construccion_id,d.area_uso,d.mes_inicio,d.mes_fin ,d.usuario_id,d.estado,d.fecha_registro,d.terminal ");
			SQL.append(",c.nro_nivel,t.descripcion,c.area_construccion,c.seccion, d.glosa ");
			SQL.append("FROM ").append(Constante.schemadb).append(".rp_djuso_detalle d ");
			SQL.append("inner join ").append(Constante.schemadb).append(".rp_djconstruccion c on(c.construccion_id=d.construccion_id) ");
			SQL.append("inner join ").append(Constante.schemadb).append(".rp_tipo_nivel t on(t.tipo_nivel_id=c.tipo_nivel_id) ");
			SQL.append("WHERE d.djuso_id=? AND d.estado='"+Constante.ESTADO_ACTIVO).append("' and c.estado='").append(Constante.ESTADO_ACTIVO).append("'");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, dJUsoId);
			ResultSet rs=pst.executeQuery();
			int item=1;
			while(rs.next()){
				RpDjusoDetalle detalle=new RpDjusoDetalle(); 
				detalle.setDjusoDetalleId(rs.getInt("djuso_detalle_id"));
				detalle.setDjusoId(rs.getInt("djuso_id"));
				detalle.setConstruccionId(rs.getInt("construccion_id"));
				detalle.setAreaUso(rs.getBigDecimal("area_uso"));
				detalle.setMesInicio(rs.getString("mes_inicio"));
				detalle.setMesFin(rs.getString("mes_fin"));
				detalle.setGlosa(rs.getString("glosa"));
				detalle.setUsuarioId(rs.getInt("usuario_id"));
				detalle.setEstado(rs.getString("estado"));
				detalle.setFechaRegistro(rs.getTimestamp("fecha_registro"));
				detalle.setTerminal(rs.getString("terminal"));
				detalle.setDescripcionnivel(rs.getString("descripcion"));
				detalle.setNumeronivel(rs.getString("nro_nivel"));
				detalle.setAreaConstruccion(rs.getBigDecimal("area_construccion"));
				
				lista.add(detalle);
			}
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}

	public BigDecimal getAreaUsada(int djarbitrioId,int construccionId)throws Exception{
		BigDecimal result=new BigDecimal(0);
		try{
			StringBuffer SQL=new StringBuffer();
			//Para predios Urbanos
			if(construccionId>Constante.RESULT_PENDING){
				SQL.append("select sum(d.area_uso) areausada from ").append(Constante.schemadb).append(".rp_djuso_detalle d ");
				SQL.append("inner join ").append(Constante.schemadb).append(".rp_djuso u on (u.djuso_id=d.djuso_id) ");
				SQL.append("where u.djarbitrio_id=? and d.construccion_id=? and d.estado='").append(Constante.ESTADO_ACTIVO).append("' and u.estado='").append(Constante.ESTADO_ACTIVO).append("' ");
			}else{
				//Para predios Rusticos
				SQL.append("select sum(u.area_uso) areausada from ").append(Constante.schemadb).append(".rp_djuso u where u.djarbitrio_id=? and u.estado='1'  ");	
			}
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, djarbitrioId);
			if(construccionId>Constante.RESULT_PENDING){
				pst.setInt(2, construccionId);	
			}
			
			ResultSet rs=pst.executeQuery();
			
			if(rs.next()){
				result=rs.getBigDecimal("areausada");
			}
		}catch(Exception e){
			throw(e);
		}
		return result;
	}
	
	public BigDecimal getAreaUsadaRecalculo(int djusoId,int djarbitrioId,int construccionId)throws Exception{
		BigDecimal result=new BigDecimal(0);
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("select sum(d.area_uso) areausada from ").append(Constante.schemadb).append(".rp_djuso_detalle d ");
			SQL.append("inner join ").append(Constante.schemadb).append(".rp_djuso u on (u.djuso_id=d.djuso_id) ");
			SQL.append("where u.djarbitrio_id=? and d.construccion_id=? and u.djuso_id=? and d.estado='").append(Constante.ESTADO_ACTIVO).append("' and u.estado='").append(Constante.ESTADO_ACTIVO).append("' ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, djarbitrioId);
			pst.setInt(2, construccionId);
			pst.setInt(3, djusoId);
			ResultSet rs=pst.executeQuery();
			
			if(rs.next()){
				result=rs.getBigDecimal("areausada");
			}
			
		}catch(Exception e){
			throw(e);
		}
		return result;
	}
	
	/**
	 *  PROCESO DE DESCARGO  
	 *  TRANSFIRIENTE REGISTRA DESCARGO
	 *  REGISTRA ADQUIRIENTES
		inserta ").append(Constante.schemadb).append(".rv_djvehicular de propietario anterior descargo DEFINITIVO
		inserta ").append(Constante.schemadb).append(".rv_djvehicular de propietario nuevo inscripcion PENDIENTE
		
		(PENDIENTE)
		PROCESO DE INSCRIPCION DE ALGO QUE YA EXISTE 
		ADQUIRIENTE REGISTRA INSCRIPCION
		REGISTRA TRANSFIRIENTES
		inserta ").append(Constante.schemadb).append(".rv_djvehicular de propietario nuevo inscripcion DEFINITIVO
		inserta ").append(Constante.schemadb).append(".rv_djvehicular de propietario anterior descargo PENDIENTE
	 * @return
	 * @throws Exception
	 */
	
	public boolean cambiarEstadoDjv(int djId,String estado,String flagDjAnno) {
		int res = 0;
		try {
			StringBuffer SQL=new StringBuffer("UPDATE ").append(Constante.schemadb).append(".rp_djpredial SET estado=?,flag_dj_anno=? WHERE dj_id=?");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setString(1, estado);
			pst.setString(2, flagDjAnno);
			pst.setInt(3, djId);
			res = pst.executeUpdate();
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
			// TODO : Controller exception
		}
		if (res == 1) {
			return true;
		}
		return false;
	}

	public List<BuscarPersonaDTO> getTransferentePropiedad(Integer djId,String TipoTransferencia)throws Exception {
		List<BuscarPersonaDTO> lista=new ArrayList<BuscarPersonaDTO>();
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("SELECT t.transferencia_id,t.persona_id,p.apellidos_nombres,p.razon_social,p.tipo_doc_identidad_id,d.descripcion,p.nro_docu_identidad,porcentaje,t.descargo_auto ");
			SQL.append("FROM ").append(Constante.schemadb).append(".rp_transferencia_propiedad t ");
			SQL.append("INNER JOIN ").append(Constante.schemadb).append(".mp_persona p ON (p.persona_id=t.persona_id) ");
			SQL.append("INNER JOIN ").append(Constante.schemadb).append(".mp_tipo_docu_identidad d ON (d.tipo_doc_identidad_id=p.tipo_doc_identidad_id) ");
			SQL.append("WHERE t.tipo=? AND t.estado=").append(Constante.ESTADO_ACTIVO).append(" AND t.dj_id=?  ");
			SQL.append("ORDER BY t.transferencia_id DESC ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setString(1, TipoTransferencia);
			pst.setInt(2, djId);
			
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				BuscarPersonaDTO persona=new BuscarPersonaDTO(); 
				persona.setPersonaId(rs.getInt("persona_id"));
				persona.setTipoDocIdentidad(rs.getString("descripcion"));
				persona.setNroDocuIdentidad(rs.getString("nro_docu_identidad"));
				persona.setApellidosNombres(rs.getString("apellidos_nombres"));
				persona.setRazonSocial(rs.getString("razon_social"));
				persona.setPorcentaje(rs.getBigDecimal("porcentaje"));
				persona.setDescargoAutomatico(rs.getString("descargo_auto"));
				lista.add(persona);
			}
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	
	public List<BuscarPersonaDTO> getTransferentePropiedadReImpresion(Integer djId,String TipoTransferencia)throws Exception {
		List<BuscarPersonaDTO> lista=new ArrayList<BuscarPersonaDTO>();
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("SELECT t.transferencia_id,t.persona_id,p.apellidos_nombres,p.razon_social,p.tipo_doc_identidad_id,d.descripcion,p.nro_docu_identidad,porcentaje,t.descargo_auto ");
			SQL.append("FROM ").append(Constante.schemadb).append(".rp_transferencia_propiedad t ");
			SQL.append("INNER JOIN ").append(Constante.schemadb).append(".mp_persona p ON (p.persona_id=t.persona_id) ");
			SQL.append("INNER JOIN ").append(Constante.schemadb).append(".mp_tipo_docu_identidad d ON (d.tipo_doc_identidad_id=p.tipo_doc_identidad_id) ");
			SQL.append("WHERE t.tipo=? ").append(" AND t.dj_id=?  ");
			SQL.append("ORDER BY t.transferencia_id DESC  ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setString(1, TipoTransferencia);
			pst.setInt(2, djId);
			
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				BuscarPersonaDTO persona=new BuscarPersonaDTO(); 
				persona.setPersonaId(rs.getInt("persona_id"));
				persona.setTipoDocIdentidad(rs.getString("descripcion"));
				persona.setNroDocuIdentidad(rs.getString("nro_docu_identidad"));
				persona.setApellidosNombres(rs.getString("apellidos_nombres"));
				persona.setRazonSocial(rs.getString("razon_social"));
				persona.setPorcentaje(rs.getBigDecimal("porcentaje"));
				persona.setDescargoAutomatico(rs.getString("descargo_auto"));
				lista.add(persona);
			}
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	
	public List<AnexosDeclaVehicDTO> getDocumentosAnexos(Integer djId)throws Exception{
		List<AnexosDeclaVehicDTO> lista=new ArrayList<AnexosDeclaVehicDTO>();
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("SELECT s.sustento_id,d.doc_sustentatorio_id,d.descripcion,s.nro_documento FROM ").append(Constante.schemadb).append(".rp_sustento_predial s ");
			SQL.append("INNER JOIN ").append(Constante.schemadb).append(".rv_documento_sustentatorio d ON (d.doc_sustentatorio_id=s.doc_sustentatorio_id) ");
			SQL.append("AND s.dj_id=? ");
			SQL.append("ORDER BY sustento_id DESC ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, djId);
			
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				AnexosDeclaVehicDTO docanexo=new AnexosDeclaVehicDTO(); 
				docanexo.setDocSustentatorioId(rs.getInt("doc_sustentatorio_id"));
				docanexo.setDescripcionDoc(rs.getString("descripcion"));
				docanexo.setNomDocAdjunto(rs.getString("nro_documento"));
				lista.add(docanexo);
			}
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	
	//caltamirano:ini
		public boolean updateDjPredial(int djId, Integer djAnteriorId, String fiscalizado, String fiscaAceptada, String fiscaCerrada){
			try{
				StringBuilder SQL = new StringBuilder("UPDATE ").append(Constante.schemadb).append(".rp_djpredial SET fiscalizado='"+fiscalizado);
				SQL.append("', fisca_aceptada='").append(fiscaAceptada);
				SQL.append("', fisca_cerrada='").append(fiscaCerrada).append("'");
				
				if(djAnteriorId!=null){
					SQL.append(", id_anterior=").append(djAnteriorId);
				}
				
				SQL.append(" WHERE dj_id=").append(djId);
				PreparedStatement pst=connect().prepareStatement(SQL.toString());
				int result=pst.executeUpdate();
				if(result>0){
					return true;
				}
			}catch(Exception ex){
				String msg = "No es posible actualizar declaración jurada predial";
				System.out.println(msg+ex);
			}
			return false;
		}
		
		public boolean isDjpAtYear(int year, int personaId, int predioId){
			try{
				StringBuilder SQL = new StringBuilder("SELECT COUNT(*) AS numRecords FROM ").append(Constante.schemadb).append(".rp_djpredial ");
				SQL.append("WHERE anno_dj = "+year);
				SQL.append(" AND flag_dj_anno='").append(Constante.ESTADO_ACTIVO).append("' AND persona_id=").append(personaId).append(" AND predio_id = ").append(predioId);
				
				PreparedStatement pst=connect().prepareStatement(SQL.toString());
				ResultSet rs=pst.executeQuery();
				if(rs.next()){
					int numRecords = rs.getInt("numRecords");
					if(numRecords==0){
						return false;
					}
				}
			}catch(Exception ex){
				String msg = "No es posible actualizar declaración jurada predial";
				System.out.println(msg+ex);
			}
			return true;
		}
		
		public RpDjpredial getDjpActivoAnio(int personId, int predioId, int year){
			try{
				StringBuilder SQL = new StringBuilder("SELECT TOP 1 dj_id, motivo_declaracion_id FROM ").append(Constante.schemadb).append(".rp_djpredial ");
				SQL.append("WHERE persona_id=").append(personId).append(" AND predio_id = ").append(predioId).append(" AND anno_dj = ").append(year ).append(" AND flag_dj_anno = '" ).append(Constante.FLAG_DJ_ANIO_ACTIVO).append("'");
				PreparedStatement pst=connect().prepareStatement(SQL.toString());
				ResultSet rs=pst.executeQuery();
				
				RpDjpredial obj = null;
				if(rs.next()){
					obj = new RpDjpredial();
					obj.setDjId(rs.getInt("dj_id"));
					obj.setMotivoDeclaracionId(rs.getInt("motivo_declaracion_id"));
				}
				return obj;
			}catch(Exception ex){
				String msg = "No es posible obtener declaración jurada predial";
				System.out.println(msg+ex);
			}
			return null;
		}
		//caltamirano:fin
		
		public Integer getUltimoRpDjpredial(Integer predioId,Integer periodo,Integer djId,Integer personaId)throws Exception{
			Integer dj=Constante.RESULT_PENDING;
			try{
				StringBuffer SQL=new StringBuffer();
				SQL.append("SELECT MAX(p.dj_id) dj_anno_id FROM ").append(Constante.schemadb).append(".rp_djpredial p WHERE p.predio_id=? AND p.anno_dj=? ");
				SQL.append(" AND p.estado='1' AND p.flag_dj_anno='1' ");//Registro vigente
				SQL.append(" AND p.dj_id!=? ");//Diferente a la dj actual
				SQL.append(" AND p.persona_id=? ");//Diferente a la dj actual
				
				PreparedStatement pst=connect().prepareStatement(SQL.toString());
				pst.setInt(1, predioId);
				pst.setInt(2, periodo);
				pst.setInt(3, djId);
				pst.setInt(4, personaId);
				//--
				ResultSet rs=pst.executeQuery();
				
				if(rs.next()){
					dj=rs.getInt("dj_anno_id");
				}
			}catch(Exception e){
				throw(e);
			}
			return dj;
		}
		
		public int actualizaEstadoRpDjpredial(Integer dj,String estado,String flag_dj_anno)throws Exception{
			int result=0;
			try{
				StringBuffer SQL=new StringBuffer();
				SQL.append("UPDATE ").append(Constante.schemadb).append(".rp_djpredial SET estado=?,flag_dj_anno=? WHERE dj_id=? ");
				PreparedStatement pst=connect().prepareStatement(SQL.toString());
				pst.setString(1, estado);
				pst.setString(2, flag_dj_anno);
				pst.setInt(3, dj);
				//--
				result=pst.executeUpdate();
			}catch(Exception e){
				throw(e);
			}
			return result;
		}
		
		public ArrayList<GnLugar> findGnLugar(Integer sectorId)throws Exception{
			ArrayList<GnLugar> lista=new ArrayList<GnLugar>();
			try{
				StringBuffer SQL=new StringBuffer();
				SQL.append(" select l.lugar_id,l.descripcion from ").append(Constante.schemadb).append(".gn_lugar l ");
				SQL.append(" inner join ").append(Constante.schemadb).append(".gn_sector_lugar sl on (sl.lugar_id=l.lugar_id) ");
				SQL.append(" where sl.sector_id=? order by l.descripcion ");
				
				PreparedStatement pst=connect().prepareStatement(SQL.toString());
				pst.setInt(1, sectorId);
				ResultSet rs=pst.executeQuery();
				while(rs.next()){
					GnLugar lugar=new GnLugar(); 
					lugar.setLugarId(rs.getInt("lugar_id"));
					lugar.setDescripcion(rs.getString("descripcion"));
					lista.add(lugar);
				}
			}catch(Exception e){
				throw(e);
			}
			return lista;
		}

				
	
	/**
	 * Valida que para hacer una copia de una dj predial a otro año, este afecto al contribuyente.
	 * 
	 * @param djpId Identificador de la declaración jurada predial.
	 * @param anioCrear Año al que se desea hacer la copía.
	 * @return Verdadero o falso dependiendo si el contribuyente esta afecto o no.
	 */
	public boolean estaEnRangoAfecContrib(int djpId, int anioCrear){
		try {
			StringBuilder SQL = new StringBuilder("SELECT CASE WHEN MONTH(CONVERT(DATE,fecha_adquisicion,103))=1 AND DAY(CONVERT(DATE,fecha_adquisicion,103))=1 THEN YEAR(CONVERT(DATE,fecha_adquisicion,103)) ");
			SQL.append("ELSE YEAR(CONVERT(DATE,fecha_adquisicion,103))+1 END AS anno_ini_afec_contrib FROM ").append(Constante.schemadb).append(".rp_djpredial WHERE dj_id="+djpId);
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				int ini = rs.getInt("anno_ini_afec_contrib");
				if(anioCrear >= ini){
					return true;
				}else{
					return false;
				}
			}
		} catch (Exception ex) {
			System.out.println("No se ha podido determinar la fecha de de inicio de afectación del contribuyente: " + ex);
		}
		return false;
	}
	
	public Boolean existeDeclaracionPendiente(Integer personaId,Integer annoDj,Integer predioId,Boolean esFiscalizado)throws Exception{
		Boolean result=Boolean.FALSE;
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append(" select COUNT(1) from ").append(Constante.schemadb).append(".rp_djpredial r where persona_id=? and anno_dj=? and predio_id=? and estado='").append(Constante.ESTADO_PENDIENTE).append("' and flag_dj_anno='").append(Constante.FLAG_DJ_ANIO_INACTIVO).append("' ");
			if(esFiscalizado){
				SQL.append(" and fiscalizado='").append(Constante.FISCALIZADO_SI).append("'");
			}
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, personaId);
			pst.setInt(2, annoDj);
			pst.setInt(3, predioId);
			
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				Integer cantidad=rs.getInt(1);
				if(cantidad>0){
					result=Boolean.TRUE;
				}
			}
		}catch(Exception e){
			throw(e);
		}
		return result;
	}
	
	// Buscar vias
	// public ArrayList<FindGnVia> findGnVia(Integer tipo_via_id,Integer sector_id,String
	// descripcion)throws Exception{
	public ArrayList<UbicacionDTO> findGnViaV2(Integer tipoViaId, Integer viaId, String descripcion)
			throws SisatException {
		ArrayList<UbicacionDTO> lista = new ArrayList<UbicacionDTO>();
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append("SELECT ");
			SQL.append(Constante.schemadb).append(".lpad(s.sector_id,2)+'.'+  ");// --sector
			SQL.append(Constante.schemadb).append(".lpad(l.lugar_id,3)+'.'+  ");// --lugar
			SQL.append(Constante.schemadb).append(".lpad(tv.tipo_via_id,2)+'.'+  ");// --tipovia
			SQL.append(Constante.schemadb).append(".lpad(v.via_id,4)+'.'+  ");// --via
			SQL.append(Constante.schemadb).append(".lpad(u.numero_cuadra,4)+'.'+ ");// --numero cuadra
			SQL.append("convert(varchar,u.lado_cuadra)+'.'+  ");// --lado
			SQL.append(Constante.schemadb).append(".lpad(u.numero_manzana,5) as codigocatastral ");// --manzana
			SQL.append(",u.ubicacion_id,s.sector_id,s.descripcion sector,l.descripcion lugar,tv.descripcion tipo_via,v.descripcion via,u.numero_cuadra numero_cuadra,u.lado_cuadra lado,u.numero_manzana,v.via_id ");
			SQL.append("FROM ").append(Constante.schemadb).append(".gn_ubicacion u ");
			SQL.append("INNER JOIN ").append(Constante.schemadb).append(".gn_via v on (v.via_id=u.via_id) ");
			SQL.append("INNER JOIN ").append(Constante.schemadb).append(".gn_tipo_via tv on (tv.tipo_via_id=v.tipo_via_id) ");
			SQL.append("INNER JOIN ").append(Constante.schemadb).append(".gn_sector_lugar sl on (sl.sector_lugar_id=u.sector_lugar_id) ");
			SQL.append("INNER JOIN ").append(Constante.schemadb).append(".gn_sector s on (sl.sector_id=s.sector_id) ");
			SQL.append("INNER JOIN ").append(Constante.schemadb).append(".gn_lugar l on (l.lugar_id=sl.lugar_id) ");

			StringBuffer sb = new StringBuffer();

			if (tipoViaId != null && tipoViaId != 0) {
				sb.append(" tv.tipo_via_id = ").append(tipoViaId).append(" and ");
			}
			if (viaId != null && viaId != 0) {
				sb.append(" v.via_id = ").append(viaId).append(" and ");
			}
			if (descripcion != null && descripcion.trim().length() > 0) {
				sb.append(" v.descripcion like UPPER('%" + descripcion + "%')").append(" and ");
			}

			if (sb.length() > 0) {
				SQL.append("WHERE ");
				int index = sb.lastIndexOf(" and ");

				if (index > -1) {
					sb = sb.delete(index, index + 4);
				}
				SQL.append(sb.toString());
			}
			SQL.append("and sl.estado=1");
			SQL.append(" order by u.ubicacion_id ");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				UbicacionDTO obj = new UbicacionDTO();
				obj.setUbicacionId(rs.getInt("ubicacion_id"));
				obj.setSectorId(rs.getInt("sector_id"));
				obj.setSector(rs.getString("sector"));
				obj.setLugar(rs.getString("lugar"));
				obj.setTipoVia(rs.getString("tipo_via"));
				obj.setVia(rs.getString("via"));
				obj.setNumeroCuadra(rs.getInt("numero_cuadra"));
				obj.setLado(rs.getInt("lado"));
				obj.setNumeroManzana(rs.getInt("numero_manzana"));
				obj.setCodigoCatastral(rs.getString("codigocatastral"));
				obj.setViaid(rs.getInt("via_id"));

				lista.add(obj);
			}
		} catch (Exception e) {
			throw new SisatException(e.getMessage(), e);
		}
		return lista;
	}
	
	//Modulo para Eliminar la Declaracion Jurada de un Predio - JAbril
	public void eliminarDJPredio(ListRpDjPredial djpredial, int personaId, int usuario_id, String terminal)throws SisatException{
		try{
			//Verificar que la DJ del predio no tiene deuda asociada
			
			//Predial
			StringBuffer SQLpredial=new StringBuffer();
			SQLpredial.append(" select distinct dp.determinacion_id from ").append(Constante.schemadb).append(".dt_determinacion d ");			
			SQLpredial.append(" inner join ").append(Constante.schemadb).append(".dt_determinacion_predio dp on (d.determinacion_id = dp.determinacion_id) ");
			SQLpredial.append(" inner join ").append(Constante.schemadb).append(".cd_deuda cd on (cd.determinacion_id = d.determinacion_id and cd.estado='").append(Constante.ESTADO_ACTIVO).append("' )");
			SQLpredial.append(" where d.estado = '").append(Constante.ESTADO_ACTIVO).append("' ");
			SQLpredial.append(" and d.persona_id = ? and d.anno_determinacion = ? and dp.dj_id = ? and d.impuesto>0");
			
			PreparedStatement pstPredial=connect().prepareStatement(SQLpredial.toString());
			pstPredial.setInt(1, personaId);
			pstPredial.setString(2, djpredial.getAnioDj());
			pstPredial.setInt(3, djpredial.getDjId());
			
			ResultSet rsPredial=pstPredial.executeQuery();
			int determinacionPredial = 0;
			
			while(rsPredial.next()){
				determinacionPredial = rsPredial.getInt("determinacion_id");				
			}
			
			//Arbitrios
			StringBuffer SQLarbitrios=new StringBuffer();
			SQLarbitrios.append(" select distinct d.determinacion_id from ").append(Constante.schemadb).append(".dt_determinacion d ");	
			SQLarbitrios.append(" inner join ").append(Constante.schemadb).append(".cd_deuda cd on (cd.determinacion_id = d.determinacion_id and cd.estado='").append(Constante.ESTADO_ACTIVO).append("' )");
			SQLarbitrios.append(" where d.estado = '").append(Constante.ESTADO_ACTIVO).append("' ");
			SQLarbitrios.append(" and d.persona_id = ? and d.anno_determinacion = ? and d.djreferencia_id = ? and d.impuesto>0");
			
			PreparedStatement pstArbitrios=connect().prepareStatement(SQLarbitrios.toString());
			pstArbitrios.setInt(1, personaId);
			pstArbitrios.setString(2, djpredial.getAnioDj());
			pstArbitrios.setInt(3, djpredial.getDjId());
			
			ResultSet rsArbitrios=pstArbitrios.executeQuery();
			List<Integer> listDeterminacionArbitrios = new ArrayList<Integer>(0);
			Integer determinacionArbitrios = 0;
			String lstDetArb = "";
			
			while(rsArbitrios.next()){
				determinacionArbitrios = rsArbitrios.getInt("determinacion_id");
				if(determinacionArbitrios != null && determinacionArbitrios != 0){
					listDeterminacionArbitrios.add(determinacionArbitrios);
					lstDetArb = lstDetArb.concat(determinacionArbitrios.toString()).concat(".");
				}				
			}
			
			if(determinacionPredial != 0 || listDeterminacionArbitrios.size() > 0)
			{
				//tiene una determinacion-deuda asociada y no puede eliminar DJ			
				throw new SisatException(" << No puede ser eliminada la DJ debido a que tiene una determinacion y una deuda asociada. Determinacion: >>" + determinacionPredial + "."+lstDetArb);
				
			}else{
				//Eliminar DJ colocando estado=9 y flag_dj_anno = 0
				
				StringBuffer SQL = new StringBuffer();
				SQL.append(" UPDATE ").append(Constante.schemadb).append(".rp_djpredial ");
				SQL.append(" SET estado = '").append(Constante.ESTADO_ELIMINADO).append("', ");
				SQL.append(" flag_dj_anno = ").append(Constante.FLAG_DJ_ANIO_INACTIVO).append(", ");
				SQL.append(" glosa = '").append(djpredial.getGlosa()).append("', ");
				SQL.append(" usuario_id = ").append(usuario_id).append(", ");
				SQL.append(" terminal = '").append(terminal).append("' ");						
				SQL.append(" where dj_id = ? and anno_dj=? and predio_id=? ");

				PreparedStatement pst=connect().prepareStatement(SQL.toString());
				pst.setInt(1,djpredial.getDjId());
				pst.setString(2,djpredial.getAnioDj());			
				pst.setInt(3,djpredial.getPredioId());
				//--
				pst.executeUpdate();
				
				//Anular registros de la Transferencia Propiedad para el caso de descargos
				desactiveTransferentes(djpredial.getDjId());

				//actualizar el anterior registro como estado activo y flag_dj_anno 1 (Si estado NO es pendiente)
				if(djpredial.getEstado().compareToIgnoreCase("2") != 0){
					
					StringBuffer SQL2=new StringBuffer();
					SQL2.append(" select ISNULL(MAX(dj_id),0) dj_id_previo from ").append(Constante.schemadb).append(".rp_djpredial ");			
					SQL2.append(" where anno_dj=? and predio_id=? and dj_id != ? and persona_id=? and estado = '0'");
					
					PreparedStatement pst2=connect().prepareStatement(SQL2.toString());
					pst2.setString(1,djpredial.getAnioDj());
					pst2.setInt(2,djpredial.getPredioId());
					pst2.setInt(3,djpredial.getDjId());
					pst2.setInt(4,personaId);
					ResultSet rs=pst2.executeQuery();
					
					int dj_id_previo = 0; //dj_id del previo				
					
					while(rs.next()){
						dj_id_previo = rs.getInt("dj_id_previo");					
					}
					
					if( dj_id_previo != 0){
						StringBuffer SQL1 = new StringBuffer();
						SQL1.append(" UPDATE ").append(Constante.schemadb).append(".rp_djpredial ");
						SQL1.append(" SET estado = '").append(Constante.ESTADO_ACTIVO).append("', ");
						SQL1.append(" flag_dj_anno = ").append(Constante.FLAG_DJ_ANIO_ACTIVO);					
						SQL1.append(" WHERE dj_id=? and anno_dj=? and predio_id=? ");

						PreparedStatement pst1=connect().prepareStatement(SQL1.toString());
						pst1.setInt(1,dj_id_previo);
						pst1.setString(2,djpredial.getAnioDj());
						pst1.setInt(3,djpredial.getPredioId());
						//--
						pst1.executeUpdate();
					}
									
				}
				//Para los descargos 4 = total 
				if(djpredial.getMotivoDeclaracionId() == 4){
					
					//Buscar comprador
					StringBuffer SQLComprador1=new StringBuffer();
					SQLComprador1.append(" select dj_id dj_id_comprador from ").append(Constante.schemadb).append(".rp_djpredial ");			
					SQLComprador1.append(" where anno_dj = ? and predio_id = ? and dj_id > ? and persona_id != ? and estado = '2' ");
					
					PreparedStatement pstComprador1=connect().prepareStatement(SQLComprador1.toString());
					pstComprador1.setString(1,djpredial.getAnioDj());
					pstComprador1.setInt(2,djpredial.getPredioId());
					pstComprador1.setInt(3,djpredial.getDjId());
					pstComprador1.setInt(4,personaId);
					
					ResultSet rs=pstComprador1.executeQuery();
					
					Integer dj_id_comprador = 0; //dj_id para el comprador
					ArrayList<Integer> dj_id_compradorList = new ArrayList<Integer>(); 
					
					while(rs.next()){
						dj_id_comprador = rs.getInt("dj_id_comprador");
						dj_id_compradorList.add(dj_id_comprador);
					}
					
					if( !dj_id_compradorList.isEmpty()){
						for(Integer dj: dj_id_compradorList)
						{
							// Anular la DJ del comprador para el mismo anio si existe
							StringBuffer SQLComprador = new StringBuffer();
							SQLComprador.append(" UPDATE ").append(Constante.schemadb).append(".rp_djpredial ");
							SQLComprador.append(" SET estado = '").append(Constante.ESTADO_ELIMINADO).append("', ");
							SQLComprador.append(" flag_dj_anno = ").append(Constante.FLAG_DJ_ANIO_INACTIVO).append(", ");
							SQLComprador.append(" glosa = '").append(djpredial.getGlosa()).append("', ");
							SQLComprador.append(" usuario_id = ").append(usuario_id).append(", ");
							SQLComprador.append(" terminal = '").append(terminal).append("' ");						
							SQLComprador.append(" where dj_id = ? and anno_dj=? and predio_id=? ");

							PreparedStatement pstComprador=connect().prepareStatement(SQLComprador.toString());
							pstComprador.setInt(1,dj);
							pstComprador.setString(2,djpredial.getAnioDj());			
							pstComprador.setInt(3,djpredial.getPredioId());
							//--
							pstComprador.executeUpdate();
							
							//Anular registros de la Transferencia Propiedad
							desactiveTransferentes(dj);
							
						}
					}
				}
				
			}

		}
		catch(Exception e){
			throw new SisatException(e.getMessage());			
		}
	}
	
	public void inactivarDJPredio(ListRpDjPredial djpredial, int personaId, int usuario_id, String terminal)throws SisatException{
		try{			
			// Inactivar una DJ colocando estado=0 y flag_dj_anno = 0
			
			//Verificar que exista una DDJJ activa diferente para poder inactivar
			StringBuffer SQL2=new StringBuffer();
			SQL2.append(" select count(*) estados_activos from ").append(Constante.schemadb).append(".rp_djpredial ");			
			SQL2.append(" where anno_dj=? and predio_id=? and dj_id != ? and persona_id=? and estado = '1'");
			
			PreparedStatement pst2=connect().prepareStatement(SQL2.toString());
			pst2.setString(1,djpredial.getAnioDj());
			pst2.setInt(2,djpredial.getPredioId());
			pst2.setInt(3,djpredial.getDjId());
			pst2.setInt(4,personaId);
			ResultSet rs=pst2.executeQuery();
			
			int estadosActivos = 0; //cantidad de DDJJ que esten activas para el año diferentes a la DDJJ				
			
			while(rs.next()){
				estadosActivos = rs.getInt("estados_activos");					
			}
			if(estadosActivos > 0){
			
				StringBuffer SQL = new StringBuffer();
				SQL.append(" UPDATE ").append(Constante.schemadb).append(".rp_djpredial ");
				SQL.append(" SET estado = '").append(Constante.ESTADO_INACTIVO).append("', ");
				SQL.append(" flag_dj_anno = ").append(Constante.FLAG_DJ_ANIO_INACTIVO).append(", ");
				SQL.append(" glosa = '").append(djpredial.getGlosa()).append("', ");
				SQL.append(" usuario_id = ").append(usuario_id).append(", ");
				SQL.append(" terminal = '").append(terminal).append("' ");
				SQL.append(" where dj_id = ? and anno_dj=? and predio_id=? and persona_id=? ");

				PreparedStatement pst = connect().prepareStatement(SQL.toString());
				pst.setInt(1, djpredial.getDjId());
				pst.setString(2, djpredial.getAnioDj());
				pst.setInt(3, djpredial.getPredioId());
				pst.setInt(4, personaId);
				// --
				pst.executeUpdate();
				
			}else{
				throw new SisatException("No se puede inactivar la DDJJ " + djpredial.getDjId() + " porque no hay otra DDJJ activa para el año "+djpredial.getAnioDj());
			}

			
	
		}
		catch(Exception e){
			throw new SisatException(e.getMessage());			
		}
	}
	
	public ArrayList<UbicacionDTO> findGnUbicacion(Integer tipoViaId, Integer viaId, Integer sectorId, Integer lugarId)
			throws SisatException {
		ArrayList<UbicacionDTO> lista = new ArrayList<UbicacionDTO>();
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append("SELECT ");
			SQL.append(Constante.schemadb).append(".lpad(s.sector_id,2)+'.'+  ");// --sector
			SQL.append(Constante.schemadb).append(".lpad(l.lugar_id,3)+'.'+  ");// --lugar
			SQL.append(Constante.schemadb).append(".lpad(tv.tipo_via_id,2)+'.'+  ");// --tipovia
			SQL.append(Constante.schemadb).append(".lpad(v.via_id,4)+'.'+  ");// --via
			SQL.append(Constante.schemadb).append(".lpad(u.numero_cuadra,4)+'.'+ ");// --numero cuadra
			SQL.append("convert(varchar,u.lado_cuadra)+'.'+  ");// --lado
			SQL.append(Constante.schemadb).append(".lpad(u.numero_manzana,5) as codigocatastral ");// --manzana
			SQL.append(",u.ubicacion_id, s.sector_id ,s.descripcion sector, l.descripcion lugar ,tv.descripcion tipo_via ,v.descripcion via ,u.numero_cuadra numero_cuadra, u.lado_cuadra lado, u.numero_manzana, v.via_id ");
			SQL.append(",l.lugar_id, s.sector_id, sl.sector_lugar_id ");
			SQL.append("FROM ").append(Constante.schemadb).append(".gn_ubicacion u ");
			SQL.append("INNER JOIN ").append(Constante.schemadb).append(".gn_via v on (v.via_id=u.via_id) ");
			SQL.append("INNER JOIN ").append(Constante.schemadb).append(".gn_tipo_via tv on (tv.tipo_via_id=v.tipo_via_id) ");
			SQL.append("INNER JOIN ").append(Constante.schemadb).append(".gn_sector_lugar sl on (sl.sector_lugar_id=u.sector_lugar_id) ");
			SQL.append("INNER JOIN ").append(Constante.schemadb).append(".gn_sector s on (sl.sector_id=s.sector_id) ");
			SQL.append("INNER JOIN ").append(Constante.schemadb).append(".gn_lugar l on (l.lugar_id=sl.lugar_id) ");

			StringBuffer sb = new StringBuffer();

			if (tipoViaId != null && tipoViaId != 0) {
				sb.append(" tv.tipo_via_id = ").append(tipoViaId).append(" and ");
			}
			if (viaId != null && viaId != 0) {
				sb.append(" v.via_id = ").append(viaId).append(" and ");
			}
			if (sectorId != null && sectorId != 0) {
				sb.append(" sl.sector_id = ").append(sectorId).append(" and ");
			}
			if (lugarId != null && viaId != 0) {
				sb.append(" sl.lugar_id = ").append(lugarId).append(" and ");
			}
			

			if (sb.length() > 0) {
				SQL.append("WHERE ");
				int index = sb.lastIndexOf(" and ");

				if (index > -1) {
					sb = sb.delete(index, index + 4);
				}
				SQL.append(sb.toString());
			}

			SQL.append(" order by s.descripcion, l.descripcion, tv.descripcion, v.descripcion, u.numero_cuadra, u.lado_cuadra, u.numero_manzana ");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				UbicacionDTO obj = new UbicacionDTO();
				obj.setUbicacionId(rs.getInt("ubicacion_id"));
				obj.setSectorId(rs.getInt("sector_id"));
				obj.setSector(rs.getString("sector"));
				obj.setLugar(rs.getString("lugar"));
				obj.setTipoVia(rs.getString("tipo_via"));
				obj.setVia(rs.getString("via"));
				obj.setNumeroCuadra(rs.getInt("numero_cuadra"));
				obj.setLado(rs.getInt("lado"));
				obj.setNumeroManzana(rs.getInt("numero_manzana"));
				obj.setCodigoCatastral(rs.getString("codigocatastral"));
				obj.setViaid(rs.getInt("via_id"));
				obj.setSectorId(rs.getInt("sector_id"));
				obj.setLugarId(rs.getInt("lugar_id"));
				obj.setSectorLugarId(rs.getInt("sector_lugar_id"));
				
				lista.add(obj);
			}
		} catch (Exception e) {
			throw new SisatException(e.getMessage(), e);
		}
		return lista;
	}
	
	public void agregarUbicacion(UbicacionDTO nuevaUbicacion, Integer usuarioId, String terminal) throws SisatException{		
		try{
			StringBuffer SQL1=new StringBuffer();			
			SQL1.append(" select sector_lugar_id from ").append(Constante.schemadb).append(".gn_sector_lugar ");			
			SQL1.append(" where sector_id = ? and lugar_id = ? and estado = '1' ");
			
			PreparedStatement pst1=connect().prepareStatement(SQL1.toString());
			pst1.setInt(1,nuevaUbicacion.getSectorId());
			pst1.setInt(2,nuevaUbicacion.getLugarId());
						
			ResultSet rs=pst1.executeQuery();
			
			while(rs.next()){
				nuevaUbicacion.setSectorLugarId(rs.getInt("sector_lugar_id"));				
			}
			
			StringBuffer SQL=new StringBuffer();
			SQL.append(" INSERT INTO ").append(Constante.schemadb).append(".gn_ubicacion (ubicacion_id,	sector_lugar_id, via_id, lado_cuadra,  ");
			SQL.append(" numero_cuadra, numero_manzana, usuario_id, estado, fecha_registro, terminal ) ");
			SQL.append(" VALUES( ?,?,?,?,?,?,?,?,?,? )");
			
			
			PreparedStatement pst;

			pst = connect().prepareStatement(SQL.toString());
			
			
			pst.setInt(1,nuevaUbicacion.getUbicacionId());//a
			pst.setInt(2,nuevaUbicacion.getSectorLugarId());
			pst.setInt(3,nuevaUbicacion.getViaid());
			pst.setInt(4,nuevaUbicacion.getLado());
			pst.setInt(5,nuevaUbicacion.getNumeroCuadra());
			pst.setInt(6,nuevaUbicacion.getNumeroManzana());
			pst.setInt(7,usuarioId);
			pst.setString(8,"1");			
			pst.setTimestamp(9,DateUtil.getCurrentDate());
			pst.setString(10,terminal);
			
			//--
			pst.executeUpdate();
			
			
		} catch (Exception e) {
			throw new SisatException("No se ha agregado la nueva ubicacion. ".concat(e.getMessage()), e);
		}
	}
	
	public ArrayList<UbicacionDTO> findGnUbicacionById(Integer ubicacionId)
			throws SisatException {
		ArrayList<UbicacionDTO> lista = new ArrayList<UbicacionDTO>();
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append("SELECT ");
			SQL.append(Constante.schemadb).append(".lpad(s.sector_id,2)+'.'+  ");// --sector
			SQL.append(Constante.schemadb).append(".lpad(l.lugar_id,3)+'.'+  ");// --lugar
			SQL.append(Constante.schemadb).append(".lpad(tv.tipo_via_id,2)+'.'+  ");// --tipovia
			SQL.append(Constante.schemadb).append(".lpad(v.via_id,4)+'.'+  ");// --via
			SQL.append(Constante.schemadb).append(".lpad(u.numero_cuadra,4)+'.'+ ");// --numero cuadra
			SQL.append("convert(varchar,u.lado_cuadra)+'.'+  ");// --lado
			SQL.append(Constante.schemadb).append(".lpad(u.numero_manzana,5) as codigocatastral ");// --manzana
			SQL.append(",u.ubicacion_id, s.sector_id ,s.descripcion sector, l.descripcion lugar ,tv.descripcion tipo_via ,v.descripcion via ,u.numero_cuadra numero_cuadra, u.lado_cuadra lado, u.numero_manzana, v.via_id ");
			SQL.append(",l.lugar_id, s.sector_id, sl.sector_lugar_id ");
			SQL.append("FROM ").append(Constante.schemadb).append(".gn_ubicacion u ");
			SQL.append("INNER JOIN ").append(Constante.schemadb).append(".gn_via v on (v.via_id=u.via_id) ");
			SQL.append("INNER JOIN ").append(Constante.schemadb).append(".gn_tipo_via tv on (tv.tipo_via_id=v.tipo_via_id) ");
			SQL.append("INNER JOIN ").append(Constante.schemadb).append(".gn_sector_lugar sl on (sl.sector_lugar_id=u.sector_lugar_id) ");
			SQL.append("INNER JOIN ").append(Constante.schemadb).append(".gn_sector s on (sl.sector_id=s.sector_id) ");
			SQL.append("INNER JOIN ").append(Constante.schemadb).append(".gn_lugar l on (l.lugar_id=sl.lugar_id) ");
	
			if (ubicacionId != null && ubicacionId != 0) {
				SQL.append("WHERE u.ubicacion_id = ").append(ubicacionId);				
			}
			
			SQL.append(" order by s.descripcion, l.descripcion, tv.descripcion, v.descripcion, u.numero_cuadra, u.lado_cuadra, u.numero_manzana ");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				UbicacionDTO obj = new UbicacionDTO();
				obj.setUbicacionId(rs.getInt("ubicacion_id"));
				obj.setSectorId(rs.getInt("sector_id"));
				obj.setSector(rs.getString("sector"));
				obj.setLugar(rs.getString("lugar"));
				obj.setTipoVia(rs.getString("tipo_via"));
				obj.setVia(rs.getString("via"));
				obj.setNumeroCuadra(rs.getInt("numero_cuadra"));
				obj.setLado(rs.getInt("lado"));
				obj.setNumeroManzana(rs.getInt("numero_manzana"));
				obj.setCodigoCatastral(rs.getString("codigocatastral"));
				obj.setViaid(rs.getInt("via_id"));
				obj.setSectorId(rs.getInt("sector_id"));
				obj.setLugarId(rs.getInt("lugar_id"));
				obj.setSectorLugarId(rs.getInt("sector_lugar_id"));
				
				lista.add(obj);
			}
		} catch (Exception e) {
			throw new SisatException(e.getMessage(), e);
		}
		return lista;
	}
	
	public ArrayList<FindRpDjPredial> getRpDjpredial2(String apellidosNombres,String razonSocial,Integer tipoDocIdentidad,String numeroDocIdentidad,String codigoPredio,
			Integer tipoViaId,Integer viaId,Integer sectorId,Integer lugarId,String direccion,
			Integer djId,Integer personaId, String codAntSatcaj, String codigoAnterior,  Integer numeroCuadra, Integer lado, Integer numeroManzana, String numeroVia,  Boolean esPropietario)throws Exception{
		ArrayList<FindRpDjPredial> lista=new ArrayList<FindRpDjPredial>();
		
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append(" select j.anno_dj,j.dj_id,tdi.descrpcion_corta tipo_documento,pe.nro_docu_identidad,stp.descripcion_corta tipo_persona,pe.apellidos_nombres,pe.razon_social,j.tipo_predio,p.codigo_predio,j.porc_propiedad, ");
			SQL.append(" tv.descripcion tipo_via,v.descripcion via,u.numero_manzana,u.numero_cuadra,u.lado_cuadra,s.descripcion sector,lu.descripcion lugar, isnull(d.direccion_completa,j.desc_domicilio) direccion_completa, ");
			SQL.append(" pe.persona_id,p.predio_id,p.codigo_anterior,j.fecha_declaracion,convert(varchar(10),j.fecha_adquisicion,103) fecha_adquisicion,(case j.tipo_predio when 'U' then 'Urbano' when 'R' then 'Rustico' else NULL end) as tipo_predio_desc,s.sector_id,tdi.tipo_doc_identidad_id,stp.tipo_persona_id,v.distrito_id, ");
			SQL.append(" j.motivo_declaracion_id,m.descripcion motivo_declaracion,j.fiscalizado,pe.ape_materno,pe.ape_paterno,pe.primer_nombre,pe.segundo_nombre,j.estado estado_dj, pe.telefono ");
			SQL.append(" ,c.descripcion condicion_propiedad, v.via_id, tv.tipo_via_id, j.area_terreno ,j.area_terreno_has,u.ubicacion_id, j.motivo_descargo_id, d.nombre_edificiacion ");
			SQL.append(" from ").append(Constante.schemadb).append(".rp_djpredial j  ");
			SQL.append(" inner join ").append(Constante.schemadb).append(".mp_predio p on (p.predio_id=j.predio_id) ");  
			SQL.append(" inner join ").append(Constante.schemadb).append(".rv_motivo_declaracion m on (m.motivo_declaracion_id=j.motivo_declaracion_id) ");
			SQL.append(" inner join ").append(Constante.schemadb).append(".rp_condicion_propiedad c on (c.condicion_propiedad_id=j.condicion_propiedad_id) ");
			SQL.append(" left join ").append(Constante.schemadb).append(".rp_djdireccion d on (d.dj_id=j.dj_id and d.estado='1') ");
			SQL.append(" left join ").append(Constante.schemadb).append(".gn_ubicacion u on (u.ubicacion_id=d.ubicacion_id)  ");
			SQL.append(" left join ").append(Constante.schemadb).append(".gn_via v on(v.via_id=d.via_id) ");
			SQL.append(" left join ").append(Constante.schemadb).append(".gn_tipo_via tv on(tv.tipo_via_id=v.tipo_via_id) ");     
			SQL.append(" left join ").append(Constante.schemadb).append(".gn_sector_lugar sl on(sl.sector_lugar_id=u.sector_lugar_id) ");  
			SQL.append(" left join ").append(Constante.schemadb).append(".gn_sector s on(s.sector_id=sl.sector_id)  ");
			SQL.append(" left join ").append(Constante.schemadb).append(".gn_lugar lu on(lu.lugar_id=sl.lugar_id)  ");
			SQL.append(" inner join ").append(Constante.schemadb).append(".mp_persona pe on (pe.persona_id=j.persona_id) "); 
			SQL.append(" inner join ").append(Constante.schemadb).append(".mp_tipo_docu_identidad tdi on (pe.tipo_doc_identidad_id=tdi.tipo_doc_identidad_id) "); 
			SQL.append(" inner join ").append(Constante.schemadb).append(".mp_tipo_persona stp on (pe.tipo_persona_id=stp.tipo_persona_id)   ");
			SQL.append(" inner join (  ");
			SQL.append(" SELECT MAX(p.dj_id) dj_anno_id,p.predio_id,p.persona_id FROM ").append(Constante.schemadb).append(".rp_djpredial p WHERE ( (p.estado='").append(Constante.ESTADO_ACTIVO).append("' and p.flag_dj_anno='").append(Constante.FLAG_DJ_ANIO_ACTIVO).append("' )");  
			SQL.append("or p.estado='").append(Constante.ESTADO_PENDIENTE).append("' ) ");
			SQL.append(" and p.anno_dj= ").append("(SELECT MAX (rp.anno_dj) FROM ").append(Constante.schemadb).append(".rp_djpredial rp WHERE rp.estado = p.estado and rp.flag_dj_anno = p.flag_dj_anno and rp.persona_id = p.persona_id  and rp.predio_id = p.predio_id ) ");
			SQL.append(" group by p.predio_id,p.persona_id  ");
			SQL.append(" ) ult_dj on (ult_dj.persona_id=j.persona_id and ult_dj.predio_id=j.predio_id and ult_dj.dj_anno_id=j.dj_id) "); 
			if(esPropietario){
				SQL.append(" where ((j.flag_dj_anno='").append(Constante.FLAG_DJ_ANIO_ACTIVO).append("' and j.estado='").append(Constante.ESTADO_ACTIVO).append("') or ( j.estado='").append(Constante.ESTADO_PENDIENTE).append("')) ");
			}
			//PARA LA BÙSQUEDA DE PREDIOS EN LAS DJ DE ALCABALAS:
			//COMENTADO POR MJPA
//			else{SQL.append(" where j.flag_dj_anno='").append(Constante.FLAG_DJ_ANIO_ACTIVO).append("' and j.estado='").append(Constante.ESTADO_ACTIVO).append("' and j.motivo_declaracion_id !=4 ");	
//			}
			
			if(personaId!=null&&personaId>0){
				SQL.append(" and pe.persona_id=").append(String.valueOf(personaId));
			}
			if(apellidosNombres!=null&&apellidosNombres.trim().length()>0){
				SQL.append(" and pe.apellidos_nombres like '").append(apellidosNombres).append("%' ");
			}else if(razonSocial!=null&&razonSocial.trim().length()>0){
				SQL.append(" and pe.razon_social like '%"+razonSocial+"%' ");
			}else if(tipoDocIdentidad!=null&&tipoDocIdentidad>0&&numeroDocIdentidad!=null&&numeroDocIdentidad.trim().length()>0){
				SQL.append(" and pe.tipo_doc_identidad_id=? and pe.nro_docu_identidad=? ");
			}else if(codigoPredio!=null&&codigoPredio.trim().length()>0){
				SQL.append(" and p.predio_id=? ");
			}else if(tipoViaId!=null&&tipoViaId>0&&viaId!=null&&viaId>0){
				SQL.append(" and tv.tipo_via_id=? and v.via_id=? ");
				
				if(numeroVia != null && !numeroVia.isEmpty()){
					SQL.append(" and d.numero = '").append(numeroVia).append("' ");
				}
				
				if(numeroCuadra!=null && numeroCuadra.intValue()>0 && lado != null && lado.intValue()>0 && numeroManzana != null &&  numeroManzana.intValue()>0){
					SQL.append(" and u.numero_cuadra = ").append(numeroCuadra).append(" and u.lado_cuadra = ").append(lado).append(" and u.numero_manzana = ").append(numeroManzana);
				}else if(numeroCuadra!=null && numeroCuadra.intValue()>0){
					SQL.append(" and u.numero_cuadra = ").append(numeroCuadra);
				}else if(lado != null && lado.intValue()>0){
					SQL.append(" and u.lado_cuadra = ").append(lado);
				}else if(numeroManzana != null &&  numeroManzana.intValue()>0){
					SQL.append(" and u.numero_manzana = ").append(numeroManzana);
				}
					
			}else if(sectorId!=null&&sectorId>0&&lugarId!=null&&lugarId>0){
				SQL.append(" and s.sector_id=? and lu.lugar_id=? ");
				
				if(tipoViaId!=null&&tipoViaId>0&&viaId!=null&&viaId>0){
					SQL.append(" and tv.tipo_via_id=? and v.via_id=? ");
					
					if(numeroVia != null && !numeroVia.isEmpty()){
						SQL.append(" and d.numero = '").append(numeroVia).append("' ");
					}
					
					if(numeroCuadra!=null && numeroCuadra.intValue()>0 && lado != null && lado.intValue()>0 && numeroManzana != null &&  numeroManzana.intValue()>0){
						SQL.append(" and u.numero_cuadra = ").append(numeroCuadra).append(" and u.lado_cuadra = ").append(lado).append(" and u.numero_manzana = ").append(numeroManzana);
					}else if(numeroCuadra!=null && numeroCuadra.intValue()>0){
						SQL.append(" and u.numero_cuadra = ").append(numeroCuadra);
					}else if(lado != null && lado.intValue()>0){
						SQL.append(" and u.lado_cuadra = ").append(lado);
					}else if(numeroManzana != null &&  numeroManzana.intValue()>0){
						SQL.append(" and u.numero_manzana = ").append(numeroManzana);
					}
				}
				
			}else if(direccion!=null&&direccion.trim().length()>0){
				SQL.append(" and d.direccion_completa like '%").append(direccion).append("%' ");
			}else if(djId!=null&&djId>0){
				SQL.append(" and j.dj_id=? ");
			}else if(codAntSatcaj!=null&&!codAntSatcaj.isEmpty()){
				SQL.append(" and pe.cod_ant_satcaj='").append(codAntSatcaj).append("'");
			}else if(codigoAnterior!=null&&!codigoAnterior.isEmpty()){
				SQL.append(" and p.codigo_anterior='").append(codigoAnterior).append("'");
			}else if(numeroManzana != null &&  numeroManzana.intValue()>0){
				SQL.append(" and u.numero_manzana = ").append(numeroManzana);
			}
			//SQL.append(" order by j.predio_id asc, j.dj_id desc ");
			SQL.append(" order by tv.descripcion, v.descripcion, d.numero ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			if(apellidosNombres!=null&&apellidosNombres.trim().length()>0){
				;
			}else if(razonSocial!=null&&razonSocial.trim().length()>0){
				;
			}else if(tipoDocIdentidad!=null&&tipoDocIdentidad>0&&numeroDocIdentidad!=null&&numeroDocIdentidad.trim().length()>0){
				pst.setInt(1, tipoDocIdentidad);
				pst.setString(2, numeroDocIdentidad);
			}else if(codigoPredio!=null&&codigoPredio.trim().length()>0){
				pst.setString(1, codigoPredio);
			}else if(tipoViaId!=null&&tipoViaId>0&&viaId!=null&&viaId>0){
				pst.setInt(1, tipoViaId);
				pst.setInt(2, viaId);
			}else if(sectorId!=null&&sectorId>0&&lugarId!=null&&lugarId>0){
				pst.setInt(1, sectorId);
				pst.setInt(2, lugarId);
			}else if(direccion!=null&&direccion.trim().length()>0){
				;
			}else if(djId!=null&&djId>0){
				pst.setInt(1, djId);
			}
			
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				FindRpDjPredial obj=new FindRpDjPredial();
				obj.setPredioId(rs.getInt("predio_id"));
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setDjId(rs.getInt("dj_id"));
				obj.setCodigoAnterior(rs.getString("codigo_anterior"));
				obj.setFechaDeclaracion(rs.getTimestamp("fecha_declaracion"));
				obj.setFechaAdquisicion(rs.getString("fecha_adquisicion"));
				obj.setPorcPropiedad(rs.getBigDecimal("porc_propiedad"));
				obj.setTipoPredio(rs.getString("tipo_predio"));
				obj.setTipoPredioDesc(rs.getString("tipo_predio_desc"));
				obj.setCodigoPredio(rs.getString("predio_id"));
				obj.setDireccionCompleta(rs.getString("direccion_completa"));
				obj.setNombreEdificiacion(rs.getString("nombre_edificiacion"));			
				obj.setMotivoDeclaracion(rs.getString("motivo_declaracion"));
				obj.setMotivoDeclaracionId(rs.getString("motivo_declaracion_id"));
				obj.setSectorId(rs.getInt("sector_id"));
				obj.setManzana(rs.getString("numero_manzana"));
				obj.setSector(rs.getString("sector"));
				obj.setFiscalizado(rs.getString("fiscalizado"));
				obj.setAnioDj(rs.getString("anno_dj"));
				obj.setDescripcionDocIdentidad(rs.getString("tipo_documento"));
				obj.setNumeroDocIdentidad(rs.getString("nro_docu_identidad"));
				obj.setDescripcionTipoPersona(rs.getString("tipo_persona"));
				obj.setApellidosNombres(rs.getString("apellidos_nombres"));
				obj.setRazonSocial(rs.getString("razon_social"));
				obj.setTipoVia(rs.getString("tipo_via"));
				obj.setViadescripcion(rs.getString("via"));
				obj.setManzana(rs.getString("numero_manzana"));
				obj.setCuadra(rs.getString("numero_cuadra"));
				obj.setLado(rs.getString("lado_cuadra"));
				obj.setSector(rs.getString("sector"));				
				obj.setEstado(rs.getString("estado_dj"));
				obj.setCondicionPropiedad(rs.getString("condicion_propiedad"));
				obj.setTipoDocIdentidad(rs.getInt("tipo_doc_identidad_id"));
				obj.setTelefonoPersona(rs.getString("telefono"));
				obj.setTipoViaId(rs.getInt("tipo_via_id"));
				obj.setViaId(rs.getInt("via_id"));
				obj.setAreaTerreno(rs.getDouble("area_terreno"));
				obj.setAreaTerrenoHas(rs.getDouble("area_terreno_has"));
				obj.setUbicacionId(rs.getInt("ubicacion_id"));
				obj.setMotivoDescargoId(rs.getInt("motivo_descargo_id"));
				lista.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}

	//PARA LA BÚSQUEDA DE PREDIOS EN "REGISTRO DE DJ":
	//MJPA
	
	public ArrayList<FindRpDjPredial> getRpDjpredial3(String apellidosNombres,String razonSocial,Integer tipoDocIdentidad,String numeroDocIdentidad,String codigoPredio,
			Integer tipoViaId,Integer viaId,Integer sectorId,Integer lugarId,String direccion,
			Integer djId,Integer personaId, String codAntSatcaj, String codigoAnterior,  Integer numeroCuadra, Integer lado, Integer numeroManzana, String numeroVia,  Boolean esPropietario)throws Exception{
		ArrayList<FindRpDjPredial> lista=new ArrayList<FindRpDjPredial>();
		
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append(" select j.anno_dj,j.dj_id,tdi.descrpcion_corta tipo_documento,pe.nro_docu_identidad,stp.descripcion_corta tipo_persona,pe.apellidos_nombres,pe.razon_social,j.tipo_predio,p.codigo_predio,j.porc_propiedad, ");
			SQL.append(" tv.descripcion tipo_via,v.descripcion via,u.numero_manzana,u.numero_cuadra,u.lado_cuadra,s.descripcion sector,lu.descripcion lugar, isnull(d.direccion_completa,j.desc_domicilio) direccion_completa, ");
			SQL.append(" pe.persona_id,p.predio_id,p.codigo_anterior,j.fecha_declaracion,convert(varchar(10),j.fecha_adquisicion,103) fecha_adquisicion,(case j.tipo_predio when 'U' then 'Urbano' when 'R' then 'Rustico' else NULL end) as tipo_predio_desc,s.sector_id,tdi.tipo_doc_identidad_id,stp.tipo_persona_id,v.distrito_id, ");
			SQL.append(" j.motivo_declaracion_id,m.descripcion motivo_declaracion,j.fiscalizado,pe.ape_materno,pe.ape_paterno,pe.primer_nombre,pe.segundo_nombre,j.estado estado_dj, pe.telefono ");
			SQL.append(" ,c.descripcion condicion_propiedad, v.via_id, tv.tipo_via_id, j.area_terreno ,j.area_terreno_has,u.ubicacion_id, j.motivo_descargo_id, d.nombre_edificiacion ");
			SQL.append(" from ").append(Constante.schemadb).append(".rp_djpredial j  ");
			SQL.append(" inner join ").append(Constante.schemadb).append(".mp_predio p on (p.predio_id=j.predio_id) ");  
			SQL.append(" inner join ").append(Constante.schemadb).append(".rv_motivo_declaracion m on (m.motivo_declaracion_id=j.motivo_declaracion_id) ");
			SQL.append(" inner join ").append(Constante.schemadb).append(".rp_condicion_propiedad c on (c.condicion_propiedad_id=j.condicion_propiedad_id) ");
			SQL.append(" left join ").append(Constante.schemadb).append(".rp_djdireccion d on (d.dj_id=j.dj_id and d.estado='1') ");
			SQL.append(" left join ").append(Constante.schemadb).append(".gn_ubicacion u on (u.ubicacion_id=d.ubicacion_id)  ");
			SQL.append(" left join ").append(Constante.schemadb).append(".gn_via v on(v.via_id=d.via_id) ");
			SQL.append(" left join ").append(Constante.schemadb).append(".gn_tipo_via tv on(tv.tipo_via_id=v.tipo_via_id) ");     
			SQL.append(" left join ").append(Constante.schemadb).append(".gn_sector_lugar sl on(sl.sector_lugar_id=u.sector_lugar_id) ");  
			SQL.append(" left join ").append(Constante.schemadb).append(".gn_sector s on(s.sector_id=sl.sector_id)  ");
			SQL.append(" left join ").append(Constante.schemadb).append(".gn_lugar lu on(lu.lugar_id=sl.lugar_id)  ");
			SQL.append(" inner join ").append(Constante.schemadb).append(".mp_persona pe on (pe.persona_id=j.persona_id) "); 
			SQL.append(" inner join ").append(Constante.schemadb).append(".mp_tipo_docu_identidad tdi on (pe.tipo_doc_identidad_id=tdi.tipo_doc_identidad_id) "); 
			SQL.append(" inner join ").append(Constante.schemadb).append(".mp_tipo_persona stp on (pe.tipo_persona_id=stp.tipo_persona_id)   ");
			SQL.append(" inner join (  ");
			SQL.append(" SELECT MAX(p.dj_id) dj_anno_id,p.predio_id,p.persona_id FROM ").append(Constante.schemadb).append(".rp_djpredial p WHERE ( (p.estado='").append(Constante.ESTADO_ACTIVO).append("' and p.flag_dj_anno='").append(Constante.FLAG_DJ_ANIO_ACTIVO).append("' )");  
			SQL.append("or p.estado='").append(Constante.ESTADO_PENDIENTE).append("' ) ");
			SQL.append(" and p.anno_dj= ").append("(SELECT MAX (rp.anno_dj) FROM ").append(Constante.schemadb).append(".rp_djpredial rp WHERE rp.estado = p.estado and rp.flag_dj_anno = p.flag_dj_anno and rp.persona_id = p.persona_id  and rp.predio_id = p.predio_id ) ");
			SQL.append(" group by p.predio_id,p.persona_id  ");
			SQL.append(" ) ult_dj on (ult_dj.persona_id=j.persona_id and ult_dj.predio_id=j.predio_id and ult_dj.dj_anno_id=j.dj_id) "); 
			if(esPropietario){
				SQL.append(" where ((j.flag_dj_anno='").append(Constante.FLAG_DJ_ANIO_ACTIVO).append("' and j.estado='").append(Constante.ESTADO_ACTIVO).append("') or ( j.estado='").append(Constante.ESTADO_PENDIENTE).append("')) ");
			}
			
			else{SQL.append(" where j.flag_dj_anno='").append(Constante.FLAG_DJ_ANIO_ACTIVO).append("' and j.estado='").append(Constante.ESTADO_ACTIVO).append("' and j.motivo_declaracion_id !=4 ");	
			}
			
			if(personaId!=null&&personaId>0){
				SQL.append(" and pe.persona_id=").append(String.valueOf(personaId));
			}
			if(apellidosNombres!=null&&apellidosNombres.trim().length()>0){
				SQL.append(" and pe.apellidos_nombres like '").append(apellidosNombres).append("%' ");
			}else if(razonSocial!=null&&razonSocial.trim().length()>0){
				SQL.append(" and pe.razon_social like '%"+razonSocial+"%' ");
			}else if(tipoDocIdentidad!=null&&tipoDocIdentidad>0&&numeroDocIdentidad!=null&&numeroDocIdentidad.trim().length()>0){
				SQL.append(" and pe.tipo_doc_identidad_id=? and pe.nro_docu_identidad=? ");
			}else if(codigoPredio!=null&&codigoPredio.trim().length()>0){
				SQL.append(" and p.predio_id=? ");
			}else if(tipoViaId!=null&&tipoViaId>0&&viaId!=null&&viaId>0){
				SQL.append(" and tv.tipo_via_id=? and v.via_id=? ");
				
				if(numeroVia != null && !numeroVia.isEmpty()){
					SQL.append(" and d.numero = '").append(numeroVia).append("' ");
				}
				
				if(numeroCuadra!=null && numeroCuadra.intValue()>0 && lado != null && lado.intValue()>0 && numeroManzana != null &&  numeroManzana.intValue()>0){
					SQL.append(" and u.numero_cuadra = ").append(numeroCuadra).append(" and u.lado_cuadra = ").append(lado).append(" and u.numero_manzana = ").append(numeroManzana);
				}else if(numeroCuadra!=null && numeroCuadra.intValue()>0){
					SQL.append(" and u.numero_cuadra = ").append(numeroCuadra);
				}else if(lado != null && lado.intValue()>0){
					SQL.append(" and u.lado_cuadra = ").append(lado);
				}else if(numeroManzana != null &&  numeroManzana.intValue()>0){
					SQL.append(" and u.numero_manzana = ").append(numeroManzana);
				}
					
			}else if(sectorId!=null&&sectorId>0&&lugarId!=null&&lugarId>0){
				SQL.append(" and s.sector_id=? and lu.lugar_id=? ");
				
				if(tipoViaId!=null&&tipoViaId>0&&viaId!=null&&viaId>0){
					SQL.append(" and tv.tipo_via_id=? and v.via_id=? ");
					
					if(numeroVia != null && !numeroVia.isEmpty()){
						SQL.append(" and d.numero = '").append(numeroVia).append("' ");
					}
					
					if(numeroCuadra!=null && numeroCuadra.intValue()>0 && lado != null && lado.intValue()>0 && numeroManzana != null &&  numeroManzana.intValue()>0){
						SQL.append(" and u.numero_cuadra = ").append(numeroCuadra).append(" and u.lado_cuadra = ").append(lado).append(" and u.numero_manzana = ").append(numeroManzana);
					}else if(numeroCuadra!=null && numeroCuadra.intValue()>0){
						SQL.append(" and u.numero_cuadra = ").append(numeroCuadra);
					}else if(lado != null && lado.intValue()>0){
						SQL.append(" and u.lado_cuadra = ").append(lado);
					}else if(numeroManzana != null &&  numeroManzana.intValue()>0){
						SQL.append(" and u.numero_manzana = ").append(numeroManzana);
					}
				}
				
			}else if(direccion!=null&&direccion.trim().length()>0){
				SQL.append(" and d.direccion_completa like '%").append(direccion).append("%' ");
			}else if(djId!=null&&djId>0){
				SQL.append(" and j.dj_id=? ");
			}else if(codAntSatcaj!=null&&!codAntSatcaj.isEmpty()){
				SQL.append(" and pe.cod_ant_satcaj='").append(codAntSatcaj).append("'");
			}else if(codigoAnterior!=null&&!codigoAnterior.isEmpty()){
				SQL.append(" and p.codigo_anterior='").append(codigoAnterior).append("'");
			}else if(numeroManzana != null &&  numeroManzana.intValue()>0){
				SQL.append(" and u.numero_manzana = ").append(numeroManzana);
			}
			//SQL.append(" order by j.predio_id asc, j.dj_id desc ");
			SQL.append(" order by tv.descripcion, v.descripcion, d.numero ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			if(apellidosNombres!=null&&apellidosNombres.trim().length()>0){
				;
			}else if(razonSocial!=null&&razonSocial.trim().length()>0){
				;
			}else if(tipoDocIdentidad!=null&&tipoDocIdentidad>0&&numeroDocIdentidad!=null&&numeroDocIdentidad.trim().length()>0){
				pst.setInt(1, tipoDocIdentidad);
				pst.setString(2, numeroDocIdentidad);
			}else if(codigoPredio!=null&&codigoPredio.trim().length()>0){
				pst.setString(1, codigoPredio);
			}else if(tipoViaId!=null&&tipoViaId>0&&viaId!=null&&viaId>0){
				pst.setInt(1, tipoViaId);
				pst.setInt(2, viaId);
			}else if(sectorId!=null&&sectorId>0&&lugarId!=null&&lugarId>0){
				pst.setInt(1, sectorId);
				pst.setInt(2, lugarId);
			}else if(direccion!=null&&direccion.trim().length()>0){
				;
			}else if(djId!=null&&djId>0){
				pst.setInt(1, djId);
			}
			
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				FindRpDjPredial obj=new FindRpDjPredial();
				obj.setPredioId(rs.getInt("predio_id"));
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setDjId(rs.getInt("dj_id"));
				obj.setCodigoAnterior(rs.getString("codigo_anterior"));
				obj.setFechaDeclaracion(rs.getTimestamp("fecha_declaracion"));
				obj.setFechaAdquisicion(rs.getString("fecha_adquisicion"));
				obj.setPorcPropiedad(rs.getBigDecimal("porc_propiedad"));
				obj.setTipoPredio(rs.getString("tipo_predio"));
				obj.setTipoPredioDesc(rs.getString("tipo_predio_desc"));
				obj.setCodigoPredio(rs.getString("predio_id"));
				obj.setDireccionCompleta(rs.getString("direccion_completa"));
				obj.setNombreEdificiacion(rs.getString("nombre_edificiacion"));			
				obj.setMotivoDeclaracion(rs.getString("motivo_declaracion"));
				obj.setMotivoDeclaracionId(rs.getString("motivo_declaracion_id"));
				obj.setSectorId(rs.getInt("sector_id"));
				obj.setManzana(rs.getString("numero_manzana"));
				obj.setSector(rs.getString("sector"));
				obj.setFiscalizado(rs.getString("fiscalizado"));
				obj.setAnioDj(rs.getString("anno_dj"));
				obj.setDescripcionDocIdentidad(rs.getString("tipo_documento"));
				obj.setNumeroDocIdentidad(rs.getString("nro_docu_identidad"));
				obj.setDescripcionTipoPersona(rs.getString("tipo_persona"));
				obj.setApellidosNombres(rs.getString("apellidos_nombres"));
				obj.setRazonSocial(rs.getString("razon_social"));
				obj.setTipoVia(rs.getString("tipo_via"));
				obj.setViadescripcion(rs.getString("via"));
				obj.setManzana(rs.getString("numero_manzana"));
				obj.setCuadra(rs.getString("numero_cuadra"));
				obj.setLado(rs.getString("lado_cuadra"));
				obj.setSector(rs.getString("sector"));				
				obj.setEstado(rs.getString("estado_dj"));
				obj.setCondicionPropiedad(rs.getString("condicion_propiedad"));
				obj.setTipoDocIdentidad(rs.getInt("tipo_doc_identidad_id"));
				obj.setTelefonoPersona(rs.getString("telefono"));
				obj.setTipoViaId(rs.getInt("tipo_via_id"));
				obj.setViaId(rs.getInt("via_id"));
				obj.setAreaTerreno(rs.getDouble("area_terreno"));
				obj.setAreaTerrenoHas(rs.getDouble("area_terreno_has"));
				obj.setUbicacionId(rs.getInt("ubicacion_id"));
				obj.setMotivoDescargoId(rs.getInt("motivo_descargo_id"));
				lista.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	public Integer getNumeroManzanaByUbicacionId(Integer ubicacionId) throws SisatException{
		Integer numeroManzana = 0;
		try{
			StringBuffer SQL1=new StringBuffer();			
			SQL1.append(" select numero_manzana from ").append(Constante.schemadb).append(".gn_ubicacion ");			
			SQL1.append(" where ubicacion_id = ? and estado = '1' ");
			
			PreparedStatement pst1=connect().prepareStatement(SQL1.toString());
			pst1.setInt(1,ubicacionId);		
						
			ResultSet rs=pst1.executeQuery();
			
			while(rs.next()){
				numeroManzana = rs.getInt("numero_manzana");				
			}
		}catch (Exception e) {
			throw new SisatException(e.getMessage(), e);
		}
		return numeroManzana;
	}

	public ArrayList<UbicacionDTO> findGnViaV3(Integer numeroManzana, String descripcion)
			throws SisatException {
		ArrayList<UbicacionDTO> lista = new ArrayList<UbicacionDTO>();
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append("SELECT ");
			SQL.append(Constante.schemadb).append(".lpad(s.sector_id,2)+'.'+  ");// --sector
			SQL.append(Constante.schemadb).append(".lpad(l.lugar_id,3)+'.'+  ");// --lugar
			SQL.append(Constante.schemadb).append(".lpad(tv.tipo_via_id,2)+'.'+  ");// --tipovia
			SQL.append(Constante.schemadb).append(".lpad(v.via_id,4)+'.'+  ");// --via
			SQL.append(Constante.schemadb).append(".lpad(u.numero_cuadra,4)+'.'+ ");// --numero cuadra
			SQL.append("convert(varchar,u.lado_cuadra)+'.'+  ");// --lado
			SQL.append(Constante.schemadb).append(".lpad(u.numero_manzana,5) as codigocatastral ");// --manzana
			SQL.append(",u.ubicacion_id,s.sector_id,s.descripcion sector,l.descripcion lugar,tv.descripcion tipo_via,v.descripcion via,u.numero_cuadra numero_cuadra,u.lado_cuadra lado,u.numero_manzana,v.via_id ");
			SQL.append("FROM ").append(Constante.schemadb).append(".gn_ubicacion u ");
			SQL.append("INNER JOIN ").append(Constante.schemadb).append(".gn_via v on (v.via_id=u.via_id) ");
			SQL.append("INNER JOIN ").append(Constante.schemadb).append(".gn_tipo_via tv on (tv.tipo_via_id=v.tipo_via_id) ");
			SQL.append("INNER JOIN ").append(Constante.schemadb).append(".gn_sector_lugar sl on (sl.sector_lugar_id=u.sector_lugar_id) ");
			SQL.append("INNER JOIN ").append(Constante.schemadb).append(".gn_sector s on (sl.sector_id=s.sector_id) ");
			SQL.append("INNER JOIN ").append(Constante.schemadb).append(".gn_lugar l on (l.lugar_id=sl.lugar_id) ");

			StringBuffer sb = new StringBuffer();

			if (numeroManzana != null && numeroManzana != 0) {
				sb.append(" u.numero_manzana = ").append(numeroManzana).append(" and ");
			}
			
			if (descripcion != null && descripcion.trim().length() > 0) {
				sb.append(" v.descripcion like UPPER('%" + descripcion + "%')").append(" and ");
			}

			if (sb.length() > 0) {
				SQL.append("WHERE ");
				int index = sb.lastIndexOf(" and ");

				if (index > -1) {
					sb = sb.delete(index, index + 4);
				}
				SQL.append(sb.toString());
			}

			SQL.append(" order by u.ubicacion_id ");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				UbicacionDTO obj = new UbicacionDTO();
				obj.setUbicacionId(rs.getInt("ubicacion_id"));
				obj.setSectorId(rs.getInt("sector_id"));
				obj.setSector(rs.getString("sector"));
				obj.setLugar(rs.getString("lugar"));
				obj.setTipoVia(rs.getString("tipo_via"));
				obj.setVia(rs.getString("via"));
				obj.setNumeroCuadra(rs.getInt("numero_cuadra"));
				obj.setLado(rs.getInt("lado"));
				obj.setNumeroManzana(rs.getInt("numero_manzana"));
				obj.setCodigoCatastral(rs.getString("codigocatastral"));
				obj.setViaid(rs.getInt("via_id"));

				lista.add(obj);
			}
		} catch (Exception e) {
			throw new SisatException(e.getMessage(), e);
		}
		return lista;
	}

	public int obtenerAnnioMax(Integer per, Integer idPredio) {
		int result=0;
		try {
			StringBuilder SQL = new StringBuilder(
					"SELECT MAX(rp_djpredial.anno_dj) as anno_dj FROM ").append(Constante.schemadb).append(".rp_djpredial ");
			SQL.append("WHERE  rp_djpredial.estado='1'and rp_djpredial.flag_dj_anno='1' and rp_djpredial.predio_id="+ idPredio + " and rp_djpredial.persona_id="+ per); 
					
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
						
			ResultSet rs = pst.executeQuery();
			System.out.println(rs);
			if (rs.next()) {
				result=rs.getInt("anno_dj");
			}
		} catch (Exception ex) {
			// TODO : Controller exception
			System.out.println("ERROR : " + ex);
			
		}
		return result;
	}
	
	/**
	 * Obtiene la cantidad de niveles y secciones con la misma denominacion 
	 */
	public Integer getExisteMismoNivel(Integer DjId,Integer construccionId,Integer nroNivel,String seccion)throws Exception{
		Integer resultado=Constante.RESULT_PENDING; 
		try{
			StringBuffer SQL=new StringBuffer("select count(*) cantidad_duplicados from dbo.rp_djconstruccion where dj_id=? and construccion_id!=? and nro_nivel=? and rtrim(ltrim(seccion))=? and estado='1'");
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, DjId);
			pst.setInt(2, construccionId);
			pst.setInt(3, nroNivel);
			pst.setString(4, seccion);
			
			ResultSet rs=pst.executeQuery();
			if(rs.next()){
				resultado=rs.getInt("cantidad_duplicados");
			}
		}catch(Exception e){
			throw(e);
		}
		return resultado;
	}
	public Integer getExisteMismoNivel(Integer DjId,Integer nroNivel,String seccion)throws Exception{
		Integer resultado=Constante.RESULT_PENDING; 
		try{
			StringBuffer SQL=new StringBuffer("select count(*) cantidad_duplicados from dbo.rp_djconstruccion where dj_id=? and nro_nivel=? and rtrim(ltrim(seccion))=? and estado='1'");
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, DjId);
			pst.setInt(2, nroNivel);
			pst.setString(3, seccion);
			
			ResultSet rs=pst.executeQuery();
			if(rs.next()){
				resultado=rs.getInt("cantidad_duplicados");
			}
		}catch(Exception e){
			throw(e);
		}
		return resultado;
	}
	

	public Integer getReqInspeccionByDj(Integer djId)throws Exception{
		Integer resultado=Constante.RESULT_PENDING; 
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("SELECT     count(det.dj_id) AS dj_inspeccion FROM ").append(Constante.schemadb).append(".rp_djpredial dj INNER JOIN rp_fiscalizacion_detalle_inspeccion det ON dj.dj_id = det.dj_id where dj.dj_id=?");
			SQL.append(" AND det.estado=1 AND predio_flag=0");//Registro vigente

			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, djId);
				//--
			ResultSet rs=pst.executeQuery();
			
			if(rs.next()){
				resultado=rs.getInt("dj_inspeccion");
			}
		}catch(Exception e){
			throw(e);
		}
		return resultado;
	}
	
	public List<FindRpTipoObraDTO> getAllRpCaregoriaObraXIdMV(
			Integer idAnteriorTO) throws Exception {
		List<FindRpTipoObraDTO> lista = new LinkedList<FindRpTipoObraDTO>();
		try {
			int anioBase = 2013;
			String SQL = new String(
					"	SELECT CO.categoria_obra_id	,co.descripcion	,co.unidad_medida,tp.id_min_vivienda"
							+ "	FROM rp_categoria_obra co INNER JOIN rp_tipo_obra tp ON tp.categoria_obra_id = co.categoria_obra_id"
							+ "	where co.periodo="
							+ anioBase
							+ " and tp.id_min_vivienda="
							+ idAnteriorTO
							+ "and co.estado=1 and tp.estado=1 and tp.tipo_obra_id not in(341,342)"
							+ "	GROUP BY CO.categoria_obra_id ,co.descripcion,co.unidad_medida,tp.id_min_vivienda");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FindRpTipoObraDTO obj = new FindRpTipoObraDTO();
				obj.setCategoriaObraId(rs.getInt("categoria_obra_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				obj.setUnidadMedida(rs.getString("unidad_medida"));
				obj.setIdMinVivienda(rs.getInt("id_min_vivienda"));
				lista.add(obj);
			}

		} catch (Exception e) {
			throw (e);
		}
		return  lista;
	}
	
	public Integer getAllTramo(int anio)throws Exception{
		Integer resultado=Constante.RESULT_PENDING; 
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("select tu.tramo_id ");
			SQL.append("from ").append(Constante.schemadb).append(".rp_tramo_uso tu  ");
			SQL.append("where tu.estado = '1' AND year(tu.fecha_inicio) <=? AND year(tu.fecha_fin) >=?");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, anio);
			pst.setInt(2, anio);
			ResultSet rs=pst.executeQuery();
			if(rs.next()){
				resultado=rs.getInt("tramo_id");
			}
		}catch(Exception e){
			throw(e);
		}
		return resultado;
	}
	
	public ArrayList<RpDjuso> getAllRpDjusoTramos(int arbitrioId)throws Exception{
		ArrayList<RpDjuso> lista=new ArrayList<RpDjuso>(); 
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("select u.djarbitrio_id,u.djuso_id,t.uso_id as tipo_uso_id,u.area_uso,rtrim(ltrim(u.mes_inicio)) mes_inicio,rtrim(ltrim(u.mes_fin)) mes_fin,u.anno_afectacion, t.descripcion tipousodescripcion,mi.descripcion desc_mes_inicio,mf.descripcion desc_mes_fin ");
			SQL.append("from ").append(Constante.schemadb).append(".rp_djuso u  ");
			SQL.append("inner join ").append(Constante.schemadb).append(".rp_tipo_uso t on (u.tipo_uso_id=t.tipo_uso_id) "); 
			SQL.append("left join ").append(Constante.schemadb).append(".rj_mes mi on (mi.mes_id=u.mes_inicio) ");
			SQL.append("left join ").append(Constante.schemadb).append(".rj_mes mf on (mf.mes_id=u.mes_fin) ");
			SQL.append("where u.djarbitrio_id=? and u.estado='").append(Constante.ESTADO_ACTIVO).append("'  ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, arbitrioId);
			ResultSet rs=pst.executeQuery();
			int item=1;
			while(rs.next()){
				RpDjuso uso=new RpDjuso();
				uso.setItem(item++);
				uso.setDjarbitrioId(rs.getInt("djarbitrio_id"));
				uso.setDjusoId(rs.getInt("djuso_id"));
				uso.setAnnoAfectacion(rs.getInt("anno_afectacion"));
				uso.setAreaUso(rs.getBigDecimal("area_uso"));
				uso.setMesFin(rs.getString("desc_mes_fin"));
				uso.setMesInicio(rs.getString("desc_mes_inicio"));
				uso.setMesFinId(rs.getString("mes_fin"));
				uso.setMesInicioId(rs.getString("mes_inicio"));
				uso.setTipoUsoId(rs.getInt("tipo_uso_id"));
				uso.setTipoUsoDescripcion(rs.getString("tipousodescripcion"));
				lista.add(uso);
			}
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	
	public BigDecimal obtenerPorcentajeDepreciacion(int depreciacionId,int annoDeterminacion,int materialId,int conservacionId,int antiguedad)throws Exception{
		BigDecimal porcentajeDepreciacion=new BigDecimal(0);
		try{

			StringBuffer SQL=new StringBuffer();
			SQL.append("SELECT a.depreciacion_id,a.anno_determinacion, ");
			SQL.append(" a.tipo_uso_id, a.antiguedad_id, b.anno_inicio, b.anno_fin, ");
			SQL.append(" a.tipo_material_id, a.conservacion_id, a.porcentaje  ");
			SQL.append(" FROM ").append(Constante.schemadb).append(".dt_depreciacion a ");
			SQL.append(" inner join ").append(Constante.schemadb).append(".de_antiguedad b on(a.antiguedad_id=b.antiguedad_id) ");
			SQL.append(" WHERE a.estado='").append(Constante.ESTADO_ACTIVO).append("' AND a.anno_determinacion=? ");
			SQL.append(" AND a.tipo_uso_id=? AND a.tipo_material_id=? and a.conservacion_id=? and (?>=b.anno_inicio and ?<=b.anno_fin) ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, annoDeterminacion);
			pst.setInt(2, depreciacionId);
			pst.setInt(3, materialId);
			pst.setInt(4, conservacionId);
			pst.setInt(5, antiguedad);
			pst.setInt(6, antiguedad);
			
			ResultSet rs=pst.executeQuery();
			if(rs.next()){
				porcentajeDepreciacion= rs.getBigDecimal("porcentaje");
			}
		}catch(Exception e){
			throw(e);
		}
		return porcentajeDepreciacion;
	}
	
	public List<RpMaterialPredominante> getMaterialPredominanteObra(Integer tipoObraId, Integer periodo)throws Exception{
		List<RpMaterialPredominante> lista=new LinkedList<RpMaterialPredominante>();
		try{
			String SQL = new String("stp_rp_obtener_material_obra ?,?");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, tipoObraId);
			pst.setInt(2, periodo);
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				RpMaterialPredominante obj=new RpMaterialPredominante(); 
				obj.setMatPredominanteId(rs.getInt("mat_predominante_id"));
				obj.setDescripcion(rs.getString("material"));
				lista.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	
	
	public List<FotoPredioDTO> getFotoPredio(Integer predioId)throws Exception{
		List<FotoPredioDTO> lista=new LinkedList<FotoPredioDTO>();
		try{
			String SQL = new String("mp_get_fotos_predio ?");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, predioId);
			ResultSet rs=pst.executeQuery();
			
			while(rs.next()){
				FotoPredioDTO obj=new FotoPredioDTO(); 
				
				obj.setFotoPredioId(rs.getInt("foto_predio_id"));				
				obj.setPredioId(rs.getInt("predio_id"));
				obj.setPredioWebId(rs.getInt("predio_web_id"));
				obj.setUrl(rs.getString("url"));
				obj.setFuente(rs.getInt("fuente"));
				
				obj.setEstado(rs.getInt("estado"));
				
				lista.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	
	
	public List<FotoPredioConstruccionesDTO> getFotoPredioConstrucciones(Integer predioId)throws Exception{
		List<FotoPredioConstruccionesDTO> lista=new LinkedList<FotoPredioConstruccionesDTO>();
		try{
			String SQL = new String("mp_get_info_predio ?");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, predioId);
			ResultSet rs=pst.executeQuery();
			
			while(rs.next()){
				FotoPredioConstruccionesDTO obj=new FotoPredioConstruccionesDTO(); 
	
				obj.setOrden(rs.getInt("orden"));				
				obj.setNivel(rs.getInt("nivel"));
				obj.setSeccion(rs.getString("seccion"));
				obj.setAnnoConstruccion(rs.getInt("anno_construccion"));
				obj.setCategorias(rs.getString("categorias"));
				obj.setAreaConstruccion(rs.getString("area_construccion"));
				obj.setValorConstruccion(rs.getString("valor_construccion"));
				obj.setArea_terreno(rs.getString("area_terreno"));
				obj.setBase_afecta(rs.getString("base_afecta"));
				obj.setArancel(rs.getString("arancel"));
				obj.setInspeccion(rs.getBoolean("inspeccion"));
				
				lista.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	
	public List<FotoPredioInstalacionesDTO> getFotoPredioInstalaciones(Integer predioId)throws Exception{
		List<FotoPredioInstalacionesDTO> lista=new LinkedList<FotoPredioInstalacionesDTO>();
		try{
			String SQL = new String("mp_get_info_instalaciones_predio ?");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, predioId);
			ResultSet rs=pst.executeQuery();
			
			while(rs.next()){
				FotoPredioInstalacionesDTO obj=new FotoPredioInstalacionesDTO(); 
				
				obj.setAnnoInstalacion(rs.getInt("anno_instalacion"));				
				obj.setMesInstalacion(rs.getInt("mes_instalacion"));
				obj.setTipoObra_id(rs.getInt("tipo_obra_id"));
				obj.setValorInstalacion(rs.getString("valor_instalacion"));
				obj.setMesdescripcion(rs.getString("mesdescripcion"));
				obj.setTipoinstalaciondescripcion(rs.getString("tipoinstalaciondescripcion"));
				obj.setNroNivel(rs.getString("nro_nivel"));
				obj.setAreaTerreno(rs.getString("area_terreno"));
				
				lista.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		return lista;
	}
	
	public int registrarFotoInspeccion(int opcion, int personaId,int predioId, int fotoInspecionId, int inspectorId, int flagValida, String glosa, int usuarioId, String terminal)throws SisatException {
		int respuesta = 0;
		
		try {
			String sql = "[usp_foto_inspeccion] ?,?,?,?,?,?,?,?,?";
            CallableStatement pst = connect().prepareCall(sql.toString());
            pst.setInt(1, opcion);
            pst.setInt(2, personaId);
            pst.setInt(3, predioId);
            pst.setInt(4, fotoInspecionId);
            pst.setInt(5, inspectorId);
            pst.setInt(6, flagValida);
            pst.setString(7, glosa);
            pst.setInt(8, usuarioId);
            pst.setString(9, terminal);
            pst.execute();
		
           
		} catch (Exception e) {
			System.out.println("Error al registrar de la inspeccion : " + e.getMessage() );
			System.out.println(": " + e) ;
		}
		
		return respuesta;
	}
	
	public  List<FotoPredioInspeccionDTO> getFotoInspeccion(int opcion, int personaId,int predioId, int fotoInspecionId, int inspectorId, int flagValida, String glosa, int usuarioId, String terminal)throws SisatException {
	
			
		List<FotoPredioInspeccionDTO> lista=new LinkedList<FotoPredioInspeccionDTO>();
		try {
			String sql = "[usp_foto_inspeccion] ?,?,?,?,?,?,?,?,?";
            CallableStatement pst = connect().prepareCall(sql.toString());
            pst.setInt(1, opcion);
            pst.setInt(2, personaId);
            pst.setInt(3, predioId);
            pst.setInt(4, fotoInspecionId);
            pst.setInt(5, inspectorId);
            pst.setInt(6, flagValida);
            pst.setString(7, glosa);
            pst.setInt(8, usuarioId);
            pst.setString(9, terminal);
            
            ResultSet rs=pst.executeQuery();
			
			while(rs.next()){
				FotoPredioInspeccionDTO obj=new FotoPredioInspeccionDTO(); 
				
				obj.setFotoInspeccionId(rs.getInt("foto_inspeccion_id"));	
				obj.setPersonaId(rs.getInt("persona_id"));	
				obj.setApellidosNombres(rs.getString("apellidos_nombres"));	
				obj.setDireccionCompleta(rs.getString("direccion_completa"));	
				obj.setPredioId(rs.getInt("predio_id"));	
				obj.setDireccionPredio(rs.getString("direccion_predio"));
				obj.setDiasBaneja(rs.getInt("dias_baneja"));
				obj.setDiasAsignado(rs.getInt("dias_asignado"));
				obj.setEstado(rs.getInt("estado"));
				
				obj.setNombreUsuarioRegistro(rs.getString("nombre_usuario_registro"));
				obj.setFechaRegistro(rs.getString("fecha_registro"));
				obj.setNombreUsuarioAsigna(rs.getString("nombre_usuario_asigna"));
				obj.setFechaAsigna(rs.getString("fecha_asigna"));
				obj.setNombreUsuarioCierra(rs.getString("nombre_usuario_cierra"));
				obj.setFechaCierra(rs.getString("fecha_cierra"));
				
				obj.setFlagActualizado(rs.getBoolean("flag_actualizado"));
				
				obj.setNombreInspector(rs.getString("nombre_inspector"));
				
				lista.add(obj);
			}
			
		} catch (Exception e) {
			System.out.println("Error al registrar de la inspeccion : " + e.getMessage() );
			System.out.println(": " + e) ;
		}
		
		return lista;
	}
	
	
	
	
}