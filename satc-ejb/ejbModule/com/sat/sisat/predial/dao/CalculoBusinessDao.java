package com.sat.sisat.predial.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Query;

import com.sat.sisat.administracion.dto.CampoDTO;
import com.sat.sisat.common.dao.GeneralDao;
import com.sat.sisat.common.dto.TipoUsoDTO;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.SATParameterFactory;
import com.sat.sisat.determinacion.vehicular.dto.DatosInafecDTO;
import com.sat.sisat.determinacion.vehicular.dto.DeudaPagosDTO;
import com.sat.sisat.determinacion.vehicular.dto.DeudaValoresDTO;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.expediente.dto.FindExpedienteByPersona;
import com.sat.sisat.fiscalizacion.dto.FindInpscDocTipo;
import com.sat.sisat.persistence.entity.CdDeuda;
import com.sat.sisat.persistence.entity.DtDeterminacion;
import com.sat.sisat.persistence.entity.DtMatrizRecojo;
import com.sat.sisat.persistence.entity.RpDjconstruccion;
import com.sat.sisat.persistence.entity.RpDjdireccion;
import com.sat.sisat.persistence.entity.RpDjpredial;
import com.sat.sisat.persistence.entity.RpDjuso;
import com.sat.sisat.persistence.entity.RpInstalacionDj;
import com.sat.sisat.predial.dto.DatosDeterminacionPredialValoresDTO;
import com.sat.sisat.predial.dto.DeterminacionArbitriosDTO;
import com.sat.sisat.predial.dto.DeudaPagosPredialDTO;
import com.sat.sisat.predial.dto.DtDeterminacionConstruccionDTO;
import com.sat.sisat.predial.dto.DtDeterminacionDTO;
import com.sat.sisat.predial.dto.DtDeterminacionInstalacionDTO;
import com.sat.sisat.predial.dto.DtDeterminacionPeriodoDTO;
import com.sat.sisat.predial.dto.DtDeterminacionPredioDTO;
import com.sat.sisat.predial.dto.DtDeterminacionResArbDTO;
import com.sat.sisat.predial.dto.RecojoArbitriosDTO;
import com.sat.sisat.predial.dto.SeguridadArbitriosDTO;

public class CalculoBusinessDao extends GeneralDao {
	
	public ArrayList<RpDjpredial> determinacionPredialGet_dj(Integer personaId,Integer periodo,String FlagDjAnno) throws Exception
	{
		ArrayList<RpDjpredial> list = new ArrayList<RpDjpredial>();

		try {
			String SQL = new String("sp_determinacion_predial_select_dj ?,?,?");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, personaId);
			pst.setInt(2, periodo);
			pst.setString(3, FlagDjAnno);
			
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				RpDjpredial obj = new RpDjpredial();
				
				obj.setDjId(rs.getInt("dj_id"));
				obj.setPredioId(rs.getInt("predio_id"));
				obj.setMotivoDescargoId(rs.getInt("motivo_descargo_id"));
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setUbicacionPredioId(rs.getInt("ubicacion_predio_id"));
				obj.setAltitudId(rs.getInt("altitud_id"));
				obj.setAnnoDj(rs.getInt("anno_dj"));
				obj.setTipoPredio(rs.getString("tipo_predio"));
				obj.setPorcPropiedad(rs.getBigDecimal("porc_propiedad"));
				obj.setFechaDeclaracion(rs.getTimestamp("fecha_declaracion"));
				obj.setFechaAdquisicion(rs.getDate("fecha_adquisicion"));
				obj.setCodigoPredio(rs.getString("codigo_predio"));
				obj.setAreaTerreno(rs.getBigDecimal("area_terreno"));
				obj.setAreaTerrenoComun(rs.getBigDecimal("area_terreno_comun"));
				obj.setFrente(rs.getBigDecimal("frente"));
				
				//obj.setfondo(rs.getBigDecimal("fondo"));
				
				obj.setFechaDescargo(rs.getTimestamp("fecha_descargo"));
				obj.setNroModificacon(rs.getInt("nro_modificacon"));
				obj.setNombrePredio(rs.getString("nombre_predio"));
				obj.setGlosa(rs.getString("glosa"));
				obj.setEstado(rs.getString("estado"));
				obj.setFlagDjAnno(rs.getString("flag_dj_anno"));
				obj.setUsuarioId(rs.getInt("usuario_id"));
				obj.setTerminal(rs.getString("terminal"));
				obj.setFechaActualizacion(rs.getTimestamp("fecha_actualizacion"));
				obj.setTerminalRegistro(rs.getString("terminal_registro"));
				obj.setFechaRegistro(rs.getTimestamp("fecha_registro"));
				obj.setIdAnterior(rs.getString("id_anterior"));
				obj.setCodigoAnterior(rs.getString("codigo_anterior"));
				obj.setEsHabitable(rs.getString("es_habitable"));
				obj.setCondicionPropiedadId(rs.getInt("condicion_propiedad_id"));
				obj.setTipoAdquisicionId(rs.getInt("tipo_adquisicion_id"));
				obj.setCondEspePredioId(rs.getInt("cond_espe_predio_id"));
				obj.setTipoTierraId(rs.getInt("tipo_tierra_id"));
				obj.setCategoriaRusticoId(rs.getInt("categoria_rustico_id"));
				obj.setMotivoDeclaracionId(rs.getInt("motivo_declaracion_id"));
				obj.setFiscalizado(rs.getString("fiscalizado"));
				obj.setNotariaId(rs.getInt("notaria_id"));
				obj.setFiscaAceptada(rs.getString("fisca_aceptada"));
				obj.setFiscaCerrada(rs.getString("fisca_cerrada"));
				obj.setTipoUsoRusticoId(rs.getInt("tipo_uso_rustico_id"));

				obj.setEsDescargo(rs.getString("es_descargo"));
				obj.setAreaTerrenoHas(rs.getBigDecimal("area_terreno_has"));
				obj.setDescDomicilio(rs.getString("desc_domicilio"));
				obj.setAreaTerrenoComunHas(rs.getBigDecimal("area_terreno_comun_has"));
				obj.setSecano(rs.getString("secano"));
				obj.setFrenteOk(rs.getString("frente_ok"));
				obj.setFrenteAreaComun(rs.getBigDecimal("frente_area_comun"));
				obj.setDistanciaAPredio(rs.getBigDecimal("distancia_a_predio"));
				obj.setPorcentajeParticipacion(rs.getBigDecimal("porcentaje_participacion"));
				obj.setFrenteVia(rs.getString("frente_via"));
				
				
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}
	
	
	public List<RpInstalacionDj> getOtrasInstalaciones(RpDjpredial rpDjPredial,Integer periodo)throws Exception{//ok
		List<RpInstalacionDj> lInstalaciones=new LinkedList<RpInstalacionDj>();
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append(" SELECT dj.dj_id, ");
			SQL.append(" dj.predio_id, ");
			SQL.append(" o.instalacion_id, ");
			SQL.append(" o.tipo_obra_id, ");
			SQL.append(" o.anno_instalacion, ");
			SQL.append(" o.mes_instalacion, ");
			SQL.append(" o.valor_instalacion  ");
			SQL.append(" FROM ").append(Constante.schemadb).append(".rp_instalacion_dj o "); 
			SQL.append(" INNER JOIN ").append(Constante.schemadb).append(".rp_djpredial dj ON ( o.dj_id = dj.dj_id) "); 
			SQL.append(" WHERE dj.persona_id = ? ");
			SQL.append(" AND dj.predio_id IN ( ? )   ");
			SQL.append(" AND dj.flag_dj_anno IN ('1','3') AND dj.estado IN ('1','X') "); 
			SQL.append(" AND dj.anno_dj = ? ");
			SQL.append(" AND o.anno_instalacion < ? ");
			SQL.append(" AND dj.dj_id = ? ");
			SQL.append(" AND o.estado != '9' ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, rpDjPredial.getPersonaId());
			pst.setInt(2, rpDjPredial.getPredioId());
			pst.setInt(3, rpDjPredial.getAnnoDj());
			pst.setInt(4, periodo);
			pst.setInt(5, rpDjPredial.getDjId());
			
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				RpInstalacionDj instalacion=new RpInstalacionDj(); 
				instalacion.setDjId(rs.getInt("dj_id"));
				instalacion.setInstalacionId(rs.getInt("instalacion_id"));
				instalacion.setTipoObraId(rs.getInt("tipo_obra_id"));
				instalacion.setAnnoInstalacion(rs.getInt("anno_instalacion"));
				instalacion.setMesInstalacion(rs.getString("mes_instalacion"));
				instalacion.setValorInstalacion(rs.getBigDecimal("valor_instalacion"));
				lInstalaciones.add(instalacion);
			}
		}catch(Exception e){
			throw(e);
		}
		return lInstalaciones;
	}
	
	public List<RpInstalacionDj> getOtrasInstalacionesConAnioFiscal(RpDjpredial rpDjPredial,Integer periodo)throws Exception{//ok
		List<RpInstalacionDj> lInstalaciones=new LinkedList<RpInstalacionDj>();
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append(" SELECT dj.dj_id, ");
			SQL.append(" dj.predio_id, ");
			SQL.append(" o.instalacion_id, ");
			SQL.append(" o.tipo_obra_id, ");
			SQL.append(" o.anno_instalacion, ");
			SQL.append(" o.mes_instalacion, ");
			SQL.append(" o.valor_instalacion  ");
			SQL.append(" FROM ").append(Constante.schemadb).append(".rp_instalacion_dj o "); 
			SQL.append(" INNER JOIN ").append(Constante.schemadb).append(".rp_djpredial dj ON ( o.dj_id = dj.dj_id) "); 
			SQL.append(" WHERE dj.persona_id = ? ");
			SQL.append(" AND dj.predio_id IN ( ? )   ");
			SQL.append(" AND dj.flag_dj_anno IN ('1','3') AND dj.estado IN ('1','X') "); 
			SQL.append(" AND dj.anno_dj = ? ");
			SQL.append(" AND o.anno_instalacion <= ? ");
			SQL.append(" AND dj.dj_id = ? ");
			SQL.append(" AND o.estado != '9' ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, rpDjPredial.getPersonaId());
			pst.setInt(2, rpDjPredial.getPredioId());
			pst.setInt(3, rpDjPredial.getAnnoDj());
			pst.setInt(4, periodo);
			pst.setInt(5, rpDjPredial.getDjId());
			
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				RpInstalacionDj instalacion=new RpInstalacionDj(); 
				instalacion.setDjId(rs.getInt("dj_id"));
				instalacion.setInstalacionId(rs.getInt("instalacion_id"));
				instalacion.setTipoObraId(rs.getInt("tipo_obra_id"));
				instalacion.setAnnoInstalacion(rs.getInt("anno_instalacion"));
				instalacion.setMesInstalacion(rs.getString("mes_instalacion"));
				instalacion.setValorInstalacion(rs.getBigDecimal("valor_instalacion"));
				lInstalaciones.add(instalacion);
			}
		}catch(Exception e){
			throw(e);
		}
		return lInstalaciones;
	}
		
	public ArrayList<Integer> getAllRpDJpredialId(Integer periodo,Integer personaIncioId,Integer personaFinId)throws Exception{
		ArrayList<Integer> lDjPredial=new ArrayList<Integer>();
		try{
			
			/*
			SQL.append(" SELECT a.dj_id FROM ").append(Constante.schemadb).append(".rp_djpredial a WHERE a.estado='1' AND  a.flag_Dj_Anno='1' AND "); 
			SQL.append(" year(CONVERT(date,a.fecha_Adquisicion,103))<=? AND a.anno_Dj=? ");
			SQL.append(" AND a.persona_id>=? AND a.persona_id<=? ");
			SQL.append(" ORDER BY a.persona_Id ASC ");
			*/
			
			/*
			StringBuffer SQL=new StringBuffer();
			SQL.append(" SELECT a.dj_id,a.anno_Dj,a.persona_id, ");
			SQL.append(" (select COUNT(*) from ").append(Constante.schemadb).append(".rp_djpredial c "); 
			SQL.append(" where c.predio_id=a.predio_id and c.anno_Dj=2019 and c.persona_id=a.persona_id ");
			SQL.append(" and c.estado='1' AND c.flag_Dj_Anno='1') tiene2014 ");
			SQL.append(" FROM ").append(Constante.schemadb).append(".rp_djpredial a WHERE a.estado='1' AND  a.flag_Dj_Anno='1' AND "); 
			SQL.append(" year(CONVERT(date,a.fecha_Adquisicion,103))<=? AND a.anno_Dj=? ");
			SQL.append(" AND a.persona_id>=? AND a.persona_id<=? and a.motivo_declaracion_id!=4 ");
			SQL.append(" ORDER BY a.persona_Id ASC ");*/
			
			String SQL = new String("sp_cuponera_select_dj ?,?,?");
			
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, periodo);
			//pst.setInt(2, periodo);
			pst.setInt(2, personaIncioId);	
			pst.setInt(3, personaFinId);
			
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				if(rs.getInt("tiene2014")==0){
					lDjPredial.add(rs.getInt("dj_id"));	
				}else{
					System.out.println("Existe declaracion jurada para el periodo "+periodo.toString() +"persona_id "+rs.getInt("persona_id")+" declaracion jurada "+rs.getInt("dj_id"));
				}
			}
		}catch(Exception e){
			throw(e);
		}
		return lDjPredial;
	}
	
	public ArrayList<Integer> getAllPersonaId(Integer periodo,Integer inicioDjId,Integer finDjId)throws Exception{
		ArrayList<Integer> lPersona=new ArrayList<Integer>();
		try{
			/*StringBuffer SQL=new StringBuffer();
			SQL.append(" SELECT DISTINCT a.persona_id FROM ").append(Constante.schemadb).append(".rp_djpredial a WHERE a.estado='1' AND  a.flag_Dj_Anno='1' AND "); 
			SQL.append(" year(CONVERT(date,a.fecha_Adquisicion,103))<=? AND a.anno_Dj=? ");
			SQL.append(" AND a.persona_id>=? AND a.persona_id<=? ");
			SQL.append(" ORDER BY a.persona_Id ASC ");*/
			
			String SQL = new String("sp_cuponera_select_persona ?,?,?");			
				 
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, periodo);			
			pst.setInt(2, inicioDjId);
			pst.setInt(3, finDjId);
			
			
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				lPersona.add(rs.getInt("persona_id"));
			}
		}catch(Exception e){
			throw(e);
		}
		return lPersona;
	}
	
	public ArrayList<CampoDTO> getAllParchePersonaId(Integer periodo,Integer inicioDjId,Integer finDjId)throws Exception{
		ArrayList<CampoDTO> lPersona=new ArrayList<CampoDTO>();
		try{
			StringBuffer SQL=new StringBuffer();
			/*
			SQL.append(" select m.persona_id,m.dj_id from ( ");
			SQL.append(" select pp.persona_id,pp.dj_id,u.tipo_uso_id,count(*) cantidad ");
			SQL.append(" from dbo.rp_djuso u ");
			SQL.append(" inner join dbo.rp_djarbitrios aa on (aa.djarbitrio_id=u.djarbitrio_id and aa.estado='1') ");
			SQL.append(" inner join dbo.rp_djpredial pp on (pp.dj_id=aa.dj_id and pp.estado='1' and pp.flag_dj_anno='1' and pp.anno_dj=2014 and pp.tipo_predio='U') ");
			SQL.append(" where u.estado='1' and u.anno_afectacion=2014 and u.tipo_uso_id!=23 and u.tipo_uso_id!=44 and u.tipo_uso_id!=9 ");
			SQL.append(" group by pp.persona_id,pp.dj_id,u.tipo_uso_id ");
			SQL.append(" )m left join ( ");
			SQL.append(" select persona_id,djreferencia_id,tipo_uso_id,count(*) cantidad ");
			SQL.append(" from dbo.dt_determinacion  ");
			SQL.append(" where concepto_id=3 and anno_determinacion=2014 and estado='1' and subconcepto_id=31 ");  
			SQL.append(" group by persona_id,djreferencia_id,tipo_uso_id ");
			SQL.append(" ) a on (m.persona_id=a.persona_id and m.tipo_uso_id=a.tipo_uso_id and m.cantidad=a.cantidad and a.djreferencia_id=m.dj_id) ");
			SQL.append(" where a.tipo_uso_id is null ");
			*/				 
			SQL.append(" select m.persona_id,m.dj_id,m.tipo_uso_id,m.cantidad,a.tipo_uso_id,a.cantidad from ( ");
			SQL.append(" select pp.persona_id,pp.dj_id,u.tipo_uso_id,count(*) cantidad ");
			SQL.append(" from dbo.rp_djuso u ");
			SQL.append(" inner join dbo.rp_djarbitrios aa on (aa.djarbitrio_id=u.djarbitrio_id and aa.estado='1') ");
			SQL.append(" inner join dbo.rp_djpredial pp on (pp.dj_id=aa.dj_id and pp.estado='1' and pp.flag_dj_anno='1' and pp.anno_dj=2016 and pp.tipo_predio='U') ");
			SQL.append(" where u.estado='1' and u.anno_afectacion=2016 and u.tipo_uso_id!=23 and u.tipo_uso_id!=44 and u.tipo_uso_id!=9 ");
			SQL.append(" group by pp.persona_id,pp.dj_id,u.tipo_uso_id ");
			SQL.append(" )m left join ( ");
			SQL.append(" select persona_id,djreferencia_id,tipo_uso_id,count(*) cantidad ");
			SQL.append(" from dbo.dt_determinacion  ");
			SQL.append(" where concepto_id=3 and anno_determinacion=2016 and estado='1' and subconcepto_id=31 ");  
			SQL.append(" group by persona_id,djreferencia_id,tipo_uso_id ");
			SQL.append(" ) a on (m.persona_id=a.persona_id and m.tipo_uso_id=a.tipo_uso_id and m.cantidad=a.cantidad and a.djreferencia_id=m.dj_id) ");
			SQL.append(" where a.tipo_uso_id is null ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				
				CampoDTO basura=new CampoDTO();
				basura.setCampoId(rs.getInt("persona_id"));
				basura.setTablaId(rs.getInt("dj_id"));
				
				lPersona.add(basura);
			}
		}catch(Exception e){
			throw(e);
		}
		return lPersona; 
	}
	
	
	public Double getValorArancelRustico(Integer periodo,Integer tipoTierraId,Integer categoriaRusticoId,Integer altitudId)throws Exception{
		//SELECT valor_arancel FROM ").append(Constante.schemadb).append(".dt_arancel_rustico  WHERE periodo=2004 AND tipo_tierra_id = 1 AND categoria_rustico_id =1 AND altitud_id  =1
		Double arancelRustico=Double.valueOf(0);
		try{
			StringBuffer SQL=new StringBuffer();
			System.out.println("altitudId : "+altitudId);
			SQL.append("SELECT valor_arancel FROM ").append(Constante.schemadb).append(".dt_arancel_rustico WHERE periodo=? AND tipo_tierra_id=? AND categoria_rustico_id=? ");
			if(altitudId != 0){
				SQL.append(" AND altitud_id=?");
			}
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, periodo);
			pst.setInt(2, tipoTierraId);
			pst.setObject(3, categoriaRusticoId);
			if(altitudId != 0){
				System.out.println("Entreee");
				pst.setObject(4, altitudId);
			}
			
			ResultSet rs=pst.executeQuery();
			if(rs.next()){
				System.out.println("holas");
				arancelRustico= rs.getDouble("valor_arancel");
			}
		}catch(Exception e){
			throw(e);
		}
		return arancelRustico;
	}
	
	public Integer getCategoriaUso2013(Integer tipoUsoId)throws Exception{
		Integer categoriaUsoId=Constante.RESULT_PENDING;
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("SELECT categoria_uso_id FROM ").append(Constante.schemadb).append(".rp_categoria_tipo_uso2013 WHERE tipo_uso_id=?");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, tipoUsoId);
			
			ResultSet rs=pst.executeQuery();
			if(rs.next()){
				categoriaUsoId= rs.getInt("categoria_uso_id");
			}
		}catch(Exception e){
			throw(e);
		}
		return categoriaUsoId;
	}
	
	public Integer getCategoriaSeguridadTipoUso2013(Integer tipoUsoId)throws Exception{
		Integer categoriaUsoId=Constante.RESULT_PENDING;
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("SELECT categoria_uso_seguridad_id AS categoria_uso_id FROM ").append(Constante.schemadb).append(".rp_categoria_seguridad_tipo_uso2013 where tipo_uso_id=?");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, tipoUsoId);
			
			ResultSet rs=pst.executeQuery();
			if(rs.next()){
				categoriaUsoId= rs.getInt("categoria_uso_id");
			}
		}catch(Exception e){
			throw(e);
		}
		return categoriaUsoId;
	}
	
	
	public Double getValorArancelUrbano(Integer periodo,RpDjdireccion direccion)throws Exception{
		Double valorArancel=Double.valueOf(0);
		try{
			StringBuffer SQL=new StringBuffer();
			if(direccion.getUbicacionId()>0){
				SQL.append("SELECT valor_arancel FROM ").append(Constante.schemadb).append(".dt_arancel_ubicacion WHERE ubicacion_id = ? AND periodo = ?");
			}
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			if(direccion.getUbicacionId()>0){
				pst.setInt(1, direccion.getUbicacionId());
				pst.setInt(2, periodo);
				
				ResultSet rs=pst.executeQuery();
				if(rs.next()){
					valorArancel= rs.getDouble("valor_arancel");
				}	
			}
		}catch(Exception e){
			throw(e);
		}
		return valorArancel;
	}
	
	public double obtenerValorUnitarioComponente(int periodoId,int tipoComponenteId,int categoriaComponenteId)throws Exception{
		//SELECT valor_unitario FROM de_valor_construccion where  periodoId,int tipoComponenteId,int categoriaComponenteId
		Double valorUnitario=Double.valueOf(0);
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("SELECT c.valor_unitario FROM ").append(Constante.schemadb).append(".de_valor_construccion c WHERE c.periodo=? AND c.tipo_componente_id=? AND c.cate_construccion_id=?");
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, periodoId);
			pst.setInt(2, tipoComponenteId);
			pst.setInt(3, categoriaComponenteId);
			
			ResultSet rs=pst.executeQuery();
			if(rs.next()){
				valorUnitario= rs.getDouble("valor_unitario");
			}
		}catch(Exception e){
			throw(e);
		}
		return valorUnitario;
	}
	
	public double obtenerPorcentajeDepreciacion(int tipo_depreciacion_id,int anno_determinacion,int tipo_material_id,int conservacion_id,int antiguedad)throws Exception{
		Double porcentajeDepreciacion=Double.valueOf(0);
		try{
			//validacion a la bd relacionada 31/07/2012
			StringBuffer SQL=new StringBuffer();
			SQL.append("SELECT a.depreciacion_id,a.anno_determinacion, ");
			SQL.append(" a.tipo_uso_id, a.antiguedad_id, b.anno_inicio, b.anno_fin, ");
			SQL.append(" a.tipo_material_id, a.conservacion_id, a.porcentaje  ");
			SQL.append(" FROM ").append(Constante.schemadb).append(".dt_depreciacion a ");
			SQL.append(" inner join ").append(Constante.schemadb).append(".de_antiguedad b on(a.antiguedad_id=b.antiguedad_id) ");
			SQL.append(" WHERE a.estado='").append(Constante.ESTADO_ACTIVO).append("' AND a.anno_determinacion=? ");
			SQL.append(" AND a.tipo_uso_id=? AND a.tipo_material_id=? and a.conservacion_id=? and (?>=b.anno_inicio and ?<=b.anno_fin) ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, anno_determinacion);
			pst.setInt(2, tipo_depreciacion_id);
			pst.setInt(3, tipo_material_id);
			pst.setInt(4, conservacion_id);
			pst.setInt(5, antiguedad);
			pst.setInt(6, antiguedad);
			
			ResultSet rs=pst.executeQuery();
			if(rs.next()){
				porcentajeDepreciacion= rs.getDouble("porcentaje");
			}
		}catch(Exception e){
			throw(e);
		}
		return porcentajeDepreciacion;
	}
	
	public List<RpDjconstruccion> getConstruccion(RpDjpredial rpDjPredial,Integer periodo)throws Exception{
		List<RpDjconstruccion> lConstruccion=new LinkedList<RpDjconstruccion>();
		try{
			
			StringBuilder SQL = new StringBuilder("exec stp_rp_getConstruccion " + rpDjPredial.getPersonaId() +","+ rpDjPredial.getPredioId() +","+ periodo +","+ rpDjPredial.getDjId());

//			StringBuffer SQL=new StringBuffer();				
//			SQL.append(" SELECT dj.dj_id,  dj.predio_id,  c.construccion_id,  c.mat_predominante_id,  c.conservacion_id,  c.tipo_nivel_id,  c.nro_nivel,c.seccion, ");  
//			SQL.append(" c.clasi_depreciacion_id,  c.anno_construccion,  c.mes_construccion,   c.area_construccion,  c.area_comun_construccion,  c.tipo_unidad_medida, ");
//			SQL.append(" c.muros,  c.techo,  c.pisos,  c.puertas,   c.revestimientos,  c.bannos,  c.electricos, c.tipo_nivel_id, c.anno_actualizacion  ");
//			SQL.append(" FROM ").append(Constante.schemadb).append(".rp_djconstruccion c  "); 
//			SQL.append(" INNER JOIN ").append(Constante.schemadb).append(".rp_djpredial dj ON ( c.dj_id = dj.dj_id) "); 
//			SQL.append(" WHERE dj.persona_id = ?  AND  ");
//			SQL.append(" dj.flag_dj_anno IN ('1','3') AND  ");
//			SQL.append(" dj.predio_id=? AND ");
//			SQL.append(" dj.estado IN ('1','X')  AND "); 
//			SQL.append(" dj.anno_dj = ?  AND  ");
//			SQL.append(" dj.dj_id=? AND");
//			SQL.append(" c.anno_construccion < ? AND ");
//			SQL.append(" c.anno_actualizacion < ? AND ");     		
//			SQL.append(" c.estado != '9' ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
//			pst.setInt(1, rpDjPredial.getPersonaId());
//			pst.setInt(2, rpDjPredial.getPredioId());
//			pst.setInt(3, periodo);
//			pst.setInt(4, rpDjPredial.getDjId());			
//			pst.setInt(5, periodo);
//			pst.setInt(6, periodo);
			
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				RpDjconstruccion obj=new RpDjconstruccion(); 
				obj.setDjId(rs.getInt("dj_id"));
				obj.setConstruccionId(rs.getInt("construccion_id"));
				obj.setMatPredominanteId(rs.getInt("mat_predominante_id"));
				obj.setConservacionId(rs.getInt("conservacion_id"));
				obj.setTipoNivelId(rs.getInt("tipo_nivel_id"));
				obj.setNroNivel(rs.getInt("nro_nivel"));
				obj.setClasiDepreciacionId(rs.getInt("clasi_depreciacion_id"));
				obj.setAnnoConstruccion(rs.getInt("anno_construccion"));
				obj.setAnnoActualizacion(rs.getInt("anno_actualizacion"));
				obj.setMesConstruccion(rs.getString("mes_construccion"));
				obj.setAreaConstruccion(rs.getBigDecimal("area_construccion"));
				obj.setAreaComunConstruccion(rs.getBigDecimal("area_comun_construccion"));
				obj.setTipoUnidadMedida(rs.getString("tipo_unidad_medida"));
				obj.setMuros(rs.getInt("muros"));
				obj.setTecho(rs.getInt("techo"));
				obj.setPisos(rs.getInt("pisos"));
				obj.setPuertas(rs.getInt("puertas"));
				obj.setRevestimientos(rs.getInt("revestimientos"));
				obj.setBannos(rs.getInt("bannos"));
				obj.setElectricos(rs.getInt("electricos"));
			
				obj.setTipoNivelId(rs.getInt("tipo_nivel_id"));
				obj.setSeccion(rs.getString("seccion"));
				
				lConstruccion.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		return lConstruccion;
	}
	
	public List<RpDjconstruccion> getConstruccionConAnioFiscal(RpDjpredial rpDjPredial,Integer periodo)throws Exception{
		List<RpDjconstruccion> lConstruccion=new LinkedList<RpDjconstruccion>();
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append(" SELECT dj.dj_id,  dj.predio_id,  c.construccion_id,  c.mat_predominante_id,  c.conservacion_id,  c.tipo_nivel_id,  c.nro_nivel,c.seccion, ");  
			SQL.append(" c.clasi_depreciacion_id,  c.anno_construccion,  c.mes_construccion,   c.area_construccion,  c.area_comun_construccion,  c.tipo_unidad_medida, ");
			SQL.append(" c.muros,  c.techo,  c.pisos,  c.puertas,   c.revestimientos,  c.bannos,  c.electricos, c.tipo_nivel_id ");
			SQL.append(" FROM ").append(Constante.schemadb).append(".rp_djconstruccion c  "); 
			SQL.append(" INNER JOIN ").append(Constante.schemadb).append(".rp_djpredial dj ON ( c.dj_id = dj.dj_id) "); 
			SQL.append(" WHERE dj.persona_id = ?  AND  ");
			SQL.append(" dj.flag_dj_anno IN ('1','3') AND  ");
			SQL.append(" dj.predio_id=? AND ");
			SQL.append(" dj.estado IN ('1','X')  AND "); 
			SQL.append(" dj.anno_dj = ?  AND  ");
			SQL.append(" c.anno_construccion <= ? AND ");
			SQL.append(" dj.dj_id=? ");
			SQL.append(" AND c.estado != '9' ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, rpDjPredial.getPersonaId());
			pst.setInt(2, rpDjPredial.getPredioId());
			pst.setInt(3, periodo);
			pst.setInt(4, periodo);
			pst.setInt(5, rpDjPredial.getDjId());
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				RpDjconstruccion obj=new RpDjconstruccion(); 
				obj.setDjId(rs.getInt("dj_id"));
				obj.setConstruccionId(rs.getInt("construccion_id"));
				obj.setMatPredominanteId(rs.getInt("mat_predominante_id"));
				obj.setConservacionId(rs.getInt("conservacion_id"));
				obj.setTipoNivelId(rs.getInt("tipo_nivel_id"));
				obj.setNroNivel(rs.getInt("nro_nivel"));
				obj.setClasiDepreciacionId(rs.getInt("clasi_depreciacion_id"));
				obj.setAnnoConstruccion(rs.getInt("anno_construccion"));
				obj.setMesConstruccion(rs.getString("mes_construccion"));
				obj.setAreaConstruccion(rs.getBigDecimal("area_construccion"));
				obj.setAreaComunConstruccion(rs.getBigDecimal("area_comun_construccion"));
				obj.setTipoUnidadMedida(rs.getString("tipo_unidad_medida"));
				obj.setMuros(rs.getInt("muros"));
				obj.setTecho(rs.getInt("techo"));
				obj.setPisos(rs.getInt("pisos"));
				obj.setPuertas(rs.getInt("puertas"));
				obj.setRevestimientos(rs.getInt("revestimientos"));
				obj.setBannos(rs.getInt("bannos"));
				obj.setElectricos(rs.getInt("electricos"));
			
				obj.setTipoNivelId(rs.getInt("tipo_nivel_id"));
				obj.setSeccion(rs.getString("seccion"));
				
				lConstruccion.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		return lConstruccion;
	}
	
	public DatosInafecDTO getInafecContrib(int contribId, int periodo) {
		DatosInafecDTO datos = null;
		try {
			StringBuilder SQL = new StringBuilder();
			SQL.append("SELECT ce.fecha_inicio, ce.fecha_fin, cc.tipo, cc.valor,cc.uso_predio_id ,ce.tipo_cond_especial_id ");
			SQL.append("FROM ").append(Constante.schemadb).append(".gn_condicion_especial ce ");
			SQL.append("INNER JOIN ").append(Constante.schemadb).append(".dt_condi_especial_contri cc ON cc.tipo_cond_especial_id = ce.tipo_cond_especial_id "); 
			SQL.append("WHERE ce.estado='1' AND cc.concepto_id=1 AND cc.periodo=? AND ce.persona_id=? ");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1,periodo);
			pst.setInt(2,contribId);
			
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				datos = new DatosInafecDTO();
				datos.setFechaInicio(rs.getDate("fecha_inicio"));
				datos.setFechaFin(rs.getDate("fecha_fin"));
				datos.setTipo(rs.getString("tipo"));
				datos.setValor(rs.getBigDecimal("valor"));
				datos.setUsoPredioId(rs.getInt("uso_predio_id"));
				datos.setTipoCondEspecialId(rs.getInt("tipo_cond_especial_id"));
			}

		} catch (Exception ex) {
			// TODO : Controller Exception
			System.out.println("ERROR: " + ex);
		}
		return datos;
	}
	
	public List<RpDjuso> getRpDjusoByDjId(Integer djId){
		List<RpDjuso> lUsos = new LinkedList<RpDjuso>();
		try {
			StringBuilder SQL = new StringBuilder();
			SQL.append("select us.djarbitrio_id,us.djuso_id,us.tipo_uso_id,us.area_uso,us.mes_inicio, ");
			SQL.append("us.mes_fin,us.anno_afectacion,us.usuario_id,us.estado,us.fecha_registro,us.terminal ");
			SQL.append("from ").append(Constante.schemadb).append(".rp_djuso us ");
			SQL.append("inner join ").append(Constante.schemadb).append(".rp_djarbitrios cv on (cv.djarbitrio_id=us.djarbitrio_id) ");
			SQL.append("where cv.dj_id=? and us.estado='1' and cv.estado='1' ");
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1,djId);
			
			ResultSet rs = pst.executeQuery();

			while(rs.next()) {
				RpDjuso uso = new RpDjuso();
				uso.setDjarbitrioId(rs.getInt("djarbitrio_id"));
				uso.setDjusoId(rs.getInt("djuso_id"));
				uso.setTipoUsoId(rs.getInt("tipo_uso_id"));
				uso.setAreaUso(rs.getBigDecimal("area_uso"));
				uso.setMesInicio(rs.getString("mes_inicio"));
				uso.setMesFin(rs.getString("mes_fin"));
				uso.setAnnoAfectacion(rs.getInt("anno_afectacion"));
				lUsos.add(uso);
			}
		} catch (Exception ex) {
			// TODO : Controller Exception
			System.out.println("ERROR: " + ex);
		}
		return lUsos;
	}
	
	public int actualizaEstadoCdDeuda(Integer determinacionId,String estado)throws Exception{
		int result=0;
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("UPDATE ").append(Constante.schemadb).append(".cd_deuda SET estado=? WHERE determinacion_id=?");/**Se corrigio para incluir en la actualizacion los SI SI SI*/
//			SQL.append("UPDATE ").append(Constante.schemadb).append(".cd_deuda SET estado=? WHERE determinacion_id=? and estado='1'");
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setString(1, estado);
			pst.setInt(2, determinacionId);
			result=pst.executeUpdate();
		}catch(Exception e){
			throw(e);
		}
		return result;
	}
	
	public List<CdDeuda> recuperarFlagCdDeuda(Integer determinacionId,String estado)throws Exception{

		List<CdDeuda> result=new ArrayList<CdDeuda>();
		try{
			String SQL=new String();
			SQL="select flag_cc,flag_detencion,fecha_detencion_deuda from cd_deuda WHERE determinacion_id=? and estado=?";
//			SQL.append("UPDATE ").append(Constante.schemadb).append(".cd_deuda SET estado=? WHERE determinacion_id=? and estado='1'");
			
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, determinacionId);
			pst.setString(2, estado);
			
			ResultSet rs = pst.executeQuery();

			while(rs.next()) {
				CdDeuda obj= new CdDeuda();
				obj.setFlagCc(rs.getString("flag_cc"));
				obj.setFlagDetencion(rs.getString("flag_detencion"));
				obj.setFechaDtencion(rs.getTimestamp("fecha_detencion_deuda"));
				result.add(obj);
				return result;
			}
		}catch(Exception e){
			throw(e);
		}
		return result;
	}
	
	public int actualizaEstadoDtDeterminacionPredio(Integer determinacionId,String estado)throws Exception{
		int result=0;
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("UPDATE ").append(Constante.schemadb).append(".dt_determinacion_predio SET estado=? WHERE determinacion_id=?");
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setString(1, estado);
			pst.setInt(2, determinacionId);
			result=pst.executeUpdate();
		}catch(Exception e){
			throw(e);
		}
		return result;
	}
	
	public int actualizaEstadoDtDeterminacionConstruccion(Integer determinacionId,String estado)throws Exception{
		int result=0;
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("UPDATE ").append(Constante.schemadb).append(".dt_determinacion_construccion SET estado=? WHERE determinacion_id=? and estado='1'");
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setString(1, estado);
			pst.setInt(2, determinacionId);
			result=pst.executeUpdate();
		}catch(Exception e){
			throw(e);
		}
		return result;
	}
	public int actualizaEstadoDtDeterminacionArbitrios(Integer djId,String estado)throws Exception{
		int result=0;
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("UPDATE ").append(Constante.schemadb).append(".dt_determinacion_arbitrios SET estado=? WHERE dj_id=? and estado='1'");
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setString(1, estado);
			pst.setInt(2, djId);
			result=pst.executeUpdate();
		}catch(Exception e){
			throw(e);
		}
		return result;
	}
	
	public ArrayList<DtDeterminacionPredioDTO> getAllDtDeterminacionPredioDj(Integer determinacionId)throws Exception{
		ArrayList<DtDeterminacionPredioDTO> lDeterminacion = new ArrayList<DtDeterminacionPredioDTO>();
		try {
			StringBuilder SQL = new StringBuilder();
			SQL.append("select dj.fecha_declaracion,dj.dj_id,dj.predio_id,dj.motivo_declaracion_id,dj.tipo_predio, ");
			SQL.append("dj.codigo_predio,dj.fecha_adquisicion fecha_adquisicion,dj.porc_propiedad,dj.desc_domicilio,d.deter_predio_id, ");
			//--
			SQL.append("d.area_terreno, d.area_terreno_has, d.arancel,d.valor_terreno,d.valor_construccion,d.valor_instalacion, ");
			SQL.append("d.valor_predio,d.base_imponible,d.base_exonerada,d.base_afecta,d.valor_impuesto, ");
			SQL.append("d.flag_inafectacion,d.valor_inafectacion, dj.secano, ");
			//--
			SQL.append("dj.estado,dj.fiscalizado,dj.fisca_aceptada,dj.fisca_cerrada ");
			//--
			SQL.append("from ").append(Constante.schemadb).append(".dt_determinacion_predio d ");
			SQL.append("inner join ").append(Constante.schemadb).append(".dt_determinacion p on (d.determinacion_id=p.determinacion_id) ");
			SQL.append("inner join ").append(Constante.schemadb).append(".rp_djpredial dj on (dj.dj_id=d.dj_id) ");
			SQL.append("where p.determinacion_id=? order by dj.dj_id desc ");
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1,determinacionId);
			
			ResultSet rs = pst.executeQuery();

			while(rs.next()) {
				DtDeterminacionPredioDTO obj = new DtDeterminacionPredioDTO();
				obj.setFechaDeclaracion(rs.getTimestamp("fecha_declaracion"));
				obj.setDjId(rs.getInt("dj_id"));
				obj.setPredioId(rs.getInt("predio_id"));
				obj.setMotivoDeclaracionId(rs.getInt("motivo_declaracion_id"));
				obj.setTipoPredio(rs.getString("tipo_predio").equals("U")?"URBANA":"RUSTICA");
				obj.setCodigoPredio(rs.getString("codigo_predio"));
				
				//obj.setFechaAdquisicion(rs.getString("fecha_adquisicion"));
				obj.setFechaAdquisicion(rs.getDate("fecha_adquisicion"));
				
				
				obj.setPorcPropiedad(rs.getDouble("porc_propiedad"));
				obj.setDescDomicilio(rs.getString("desc_domicilio"));
				obj.setDeterPredioId(rs.getInt("deter_predio_id"));
				//--
				obj.setAreaTerreno(rs.getDouble("area_terreno"));
				obj.setAreaTerrenoHas(rs.getDouble("area_terreno_has"));
				obj.setArancel(rs.getDouble("arancel"));
				obj.setValorTerreno(rs.getDouble("valor_terreno"));
				obj.setValorConstruccion(rs.getDouble("valor_construccion"));
				obj.setValorInstalacion(rs.getDouble("valor_instalacion"));
				obj.setValorPredio(rs.getDouble("valor_predio"));
				obj.setBaseImponible(rs.getDouble("base_imponible"));
				obj.setBaseExonerada(rs.getDouble("base_exonerada"));
				obj.setBaseAfecta(rs.getDouble("base_afecta"));
				obj.setValorImpuesto(rs.getDouble("valor_impuesto"));
				obj.setFlagInafectacion(rs.getString("flag_inafectacion"));
				obj.setValorInafectacion(rs.getDouble("valor_inafectacion"));
				obj.setSecano(rs.getString("secano"));
				//--
				obj.setEstado(rs.getString("estado"));
				obj.setFiscalizado(rs.getString("fiscalizado"));
				obj.setFiscaAceptada(rs.getString("fisca_aceptada"));
				obj.setFiscaCerrada(rs.getString("fisca_cerrada"));
				//--
				lDeterminacion.add(obj);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return lDeterminacion;
	}
	
	public ArrayList<DtDeterminacionConstruccionDTO> getAllDtDeterminacionConstruccion(Integer deterPredioId,Integer anio)throws Exception{
		ArrayList<DtDeterminacionConstruccionDTO> lDeterminacion = new ArrayList<DtDeterminacionConstruccionDTO>();
		try {
			StringBuilder SQL = new StringBuilder();
			SQL.append(" select tdep.descripcion tipo_depresiacion, djc.referencia, djc.anno_construccion, djc.anno_actualizacion, ec.descripcion estado_concervacion,mp.descripcion material_predominante, ");
			SQL.append(" djc.nro_nivel, djc.seccion, ("+(anio-1)+"-djc.anno_construccion) antiguedad,tn.descripcion, ");  
			SQL.append(" ").append(Constante.schemadb).append(".getValorConstruccion("+anio+",1,djc.muros) muros,").append(Constante.schemadb).append(".getValorConstruccion("+anio+",2,djc.techo) techo, ");
			SQL.append(" ").append(Constante.schemadb).append(".getValorConstruccion("+anio+",3,djc.pisos) pisos,  ").append(Constante.schemadb).append(".getValorConstruccion("+anio+",4,djc.puertas) puertas, ");
			SQL.append(" ").append(Constante.schemadb).append(".getValorConstruccion("+anio+",5,djc.revestimientos) revestimientos,").append(Constante.schemadb).append(".getValorConstruccion("+anio+",6,djc.bannos) bannos, "); 
			SQL.append(" ").append(Constante.schemadb).append(".getValorConstruccion("+anio+",7,djc.electricos) electricos,  dc.valor_unitario, ");
			SQL.append(" dc.valor_incremento, ");
			SQL.append(" dc.valor_unitario_depre,djc.area_construccion,dc.valor_area_construida,dc.valor_area_comun, "); 
			SQL.append(" dc.valor_construccion,dp.deter_predio_id ");
			SQL.append(" from ").append(Constante.schemadb).append(".dt_determinacion_construccion dc "); 
			SQL.append(" inner join ").append(Constante.schemadb).append(".dt_determinacion_predio dp on (dp.deter_predio_id=dc.deter_predio_id) ");  
			SQL.append(" inner join ").append(Constante.schemadb).append(".rp_djconstruccion djc on (djc.construccion_id=dc.construccion_id)  ");
			SQL.append(" inner join ").append(Constante.schemadb).append(".rp_tipo_nivel tn on (tn.tipo_nivel_id=djc.tipo_nivel_id)   ");
			SQL.append(" inner join ").append(Constante.schemadb).append(".rj_estado_conservacion ec on (ec.conservacion_id=djc.conservacion_id) ");
			SQL.append(" inner join ").append(Constante.schemadb).append(".rp_material_predominante mp on (mp.mat_predominante_id=djc.mat_predominante_id) ");
			SQL.append(" inner join ").append(Constante.schemadb).append(".rp_tipo_depreciacion tdep on (tdep.tipo_depreciacion_id=djc.clasi_depreciacion_id) ");
			SQL.append(" where dp.deter_predio_id=? and djc.anno_construccion<"+anio+" order by djc.nro_nivel asc  ");
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1,deterPredioId);
			
			ResultSet rs = pst.executeQuery();

			while(rs.next()) {
				DtDeterminacionConstruccionDTO obj = new DtDeterminacionConstruccionDTO();
				obj.setNroNivel(rs.getInt("nro_nivel"));
				obj.setSeccion(rs.getString("seccion"));
				obj.setAntiguedad(rs.getInt("antiguedad"));
				obj.setDescripcionNivel(rs.getString("descripcion"));
				obj.setReferencia(rs.getString("referencia"));
					obj.setMuros(rs.getString("muros"));
					obj.setTecho(rs.getString("techo"));
					obj.setPisos(rs.getString("pisos"));
					obj.setPuertas(rs.getString("puertas"));
					obj.setRevestimiento(rs.getString("revestimientos"));
					obj.setBannos(rs.getString("bannos"));
					obj.setElectricos(rs.getString("electricos"));
				obj.setValorUnitario(rs.getDouble("valor_unitario"));
				obj.setIncremento(rs.getDouble("valor_incremento"));
				obj.setValorUnitarioDepre(rs.getDouble("valor_unitario_depre"));
				obj.setAreaConstruccion(rs.getDouble("area_construccion"));
				obj.setValorAreaConstruida(rs.getDouble("valor_area_construida"));
				obj.setValorAreaComun(rs.getDouble("valor_area_comun"));
				obj.setValorConstruccion(rs.getDouble("valor_construccion"));
				obj.setDeterPredioId(rs.getInt("deter_predio_id"));
				//--
				obj.setAnnoConstruccion(rs.getInt("anno_construccion"));
				obj.setAnnoActualizacion(rs.getInt("anno_actualizacion"));
				obj.setEstadoConcervacion(rs.getString("estado_concervacion"));
				obj.setMaterialPredominante(rs.getString("material_predominante"));
				obj.setTipoDepreciacion(rs.getString("tipo_depresiacion"));
				//--
				lDeterminacion.add(obj);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return lDeterminacion;
	}
	
	public ArrayList<DtDeterminacionInstalacionDTO> getAllDtDeterminacionInstalacion(Integer deterPredioId,Integer periodo)throws Exception{
		ArrayList<DtDeterminacionInstalacionDTO> lDeterminacion = new ArrayList<DtDeterminacionInstalacionDTO>();
		try {
			StringBuilder SQL = new StringBuilder();
			SQL.append(" select tipo.tipo_obra_id, tipo.descripcion, ins.area_terreno, cat.unidad_medida, tipo.valor_unitario as valor_unitario_1,unitario.valor_unitario,ins.anno_instalacion,ins.valor_instalacion,").append(Constante.schemadb).append(".getValorOficializacion(?) facto_oficializacion from ").append(Constante.schemadb).append(".dt_determinacion_predio dp ");
			SQL.append(" inner join ").append(Constante.schemadb).append(".dt_determinacion_instalacion di on (dp.deter_predio_id=di.deter_predio_id) ");
			SQL.append(" inner join ").append(Constante.schemadb).append(".rp_instalacion_dj  ins on (ins.instalacion_id=di.instalacion_id) ");
			SQL.append(" inner join ").append(Constante.schemadb).append(".rp_tipo_obra tipo on (tipo.tipo_obra_id=ins.tipo_obra_id) ");
			SQL.append(" inner join ").append(Constante.schemadb).append(".rp_tipo_obra_periodo unitario on (unitario.tipo_obra_id=ins.tipo_obra_id) ");
			SQL.append(" inner join ").append(Constante.schemadb).append(".rp_categoria_obra cat on (cat.categoria_obra_id = tipo.categoria_obra_id) ");
			SQL.append(" where dp.deter_predio_id=? and unitario.periodo=? order by ins.anno_instalacion asc ");
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1,periodo-1);
			pst.setInt(2,deterPredioId);
			pst.setInt(3,periodo);
			
			ResultSet rs = pst.executeQuery();

			while(rs.next()) {
				DtDeterminacionInstalacionDTO obj = new DtDeterminacionInstalacionDTO();
				obj.setTipoObraId(rs.getInt("tipo_obra_id"));
				obj.setTipoConstruccion(rs.getString("descripcion"));				
				obj.setAreaTerreno(rs.getBigDecimal("area_terreno"));
				obj.setUnidadMedida(rs.getString("unidad_medida"));
				obj.setValorUnitario(rs.getBigDecimal("valor_unitario"));
				obj.setAnioConstruccion(rs.getInt("anno_instalacion"));				
				obj.setFactorInafectacion(rs.getDouble("facto_oficializacion"));
				obj.setValorInstalacion(rs.getDouble("valor_instalacion"));
				lDeterminacion.add(obj);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return lDeterminacion;
	}
	
	public List<Integer> getAniosDeterminacion(Integer personaId){
		ArrayList<Integer> lAnioDeterminacion = new ArrayList<Integer>();
		try {
			StringBuilder SQL = new StringBuilder();
			SQL.append(" SELECT distinct a.anno_dj FROM ").append(Constante.schemadb).append(".rp_djpredial a WHERE a.estado='").append(Constante.ESTADO_ACTIVO).append("' AND a.flag_dj_anno='").append(Constante.FLAG_DJ_ANIO_ACTIVO).append("' AND a.persona_id=? ");
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1,personaId);
			
			ResultSet rs = pst.executeQuery();

			while(rs.next()) {
				lAnioDeterminacion.add(Integer.valueOf(rs.getInt("anno_dj")));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return lAnioDeterminacion;
	}
	
	public ArrayList<DtDeterminacionResArbDTO> getAllDtDeterminacionArbitrios(Integer personaId,Integer periodo)throws Exception{
		ArrayList<DtDeterminacionResArbDTO> lDeterminacion = new ArrayList<DtDeterminacionResArbDTO>();
		try {
			StringBuilder SQL = new StringBuilder();
			SQL.append(" SELECT isnull(a.determinacion_arbitrios_id,0) determinacion_arbitrios_id,p.dj_id,p.anno_dj,p.persona_id,p.desc_domicilio,p.fecha_declaracion, ");
			SQL.append(" isnull(d.determinacion_id_30,0) determinacion_id_30, ");
			SQL.append(" isnull(d.determinacion_id_31,0) determinacion_id_31, ");
			SQL.append(" isnull(d.determinacion_id_32,0) determinacion_id_32, ");
			SQL.append(" isnull(d.determinacion_id_33,0) determinacion_id_33, ");
			SQL.append(" a.arbitrio_limpieza,a.arbitrio_parques,a.arbitrio_recojo,a.arbitrio_seguridad,p.predio_id "); 
			SQL.append(" FROM (SELECT p.dj_id,p.anno_dj,p.persona_id,d.direccion_completa as desc_domicilio,p.fecha_declaracion,p.predio_id FROM ").append(Constante.schemadb).append(".rp_djpredial p "); 
			SQL.append(" INNER JOIN ").append(Constante.schemadb).append(".rp_djdireccion d on (d.dj_id=p.dj_id and d.estado='1') ");
			SQL.append(" WHERE p.estado='1' AND p.flag_dj_anno='1' ) p ");
			SQL.append(" LEFT JOIN (select d.djreferencia_id,  ");
			SQL.append(" max(case when d.subconcepto_id=30 then d.determinacion_id end) determinacion_id_30, ");
			SQL.append(" max(case when d.subconcepto_id=31 then d.determinacion_id end) determinacion_id_31, ");
			SQL.append(" max(case when d.subconcepto_id=32 then d.determinacion_id end) determinacion_id_32, ");
			SQL.append(" max(case when d.subconcepto_id=33 then d.determinacion_id end) determinacion_id_33 ");
			SQL.append(" from ").append(Constante.schemadb).append(".dt_determinacion d  ");
			SQL.append(" where d.estado='1' and d.concepto_id=3 "); 
			SQL.append(" group by d.djreferencia_id) d  ON (p.dj_id=d.djreferencia_id) ");
			SQL.append(" LEFT JOIN ").append(Constante.schemadb).append(".dt_determinacion_arbitrios a on (a.dj_id=p.dj_id and a.estado='1') ");
			SQL.append(" WHERE p.persona_id=? and p.anno_dj=? ");
			SQL.append(" ORDER BY dj_id DESC ");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1,personaId);
			pst.setInt(2,periodo);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				DtDeterminacionResArbDTO obj= new DtDeterminacionResArbDTO();
				obj.setDjId(rs.getInt("dj_id"));
				obj.setAnnoDj(rs.getInt("anno_dj"));
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setDescDomicilio(rs.getString("desc_domicilio"));
				obj.setFechaDeclaracion(rs.getTimestamp("fecha_declaracion"));
				obj.setBarridoDeterminacionId(rs.getInt("determinacion_id_30"));
				obj.setRecojoDeterminacionId(rs.getInt("determinacion_id_31"));
				obj.setParquesDeterminacionId(rs.getInt("determinacion_id_32"));
				obj.setSeguridadDeterminacionId(rs.getInt("determinacion_id_33"));
				//--
				obj.setArbitrioLimpieza(rs.getDouble("arbitrio_limpieza"));
				obj.setArbitrioParques(rs.getDouble("arbitrio_parques"));
				obj.setArbitrioRecojo(rs.getDouble("arbitrio_recojo"));
				obj.setArbitrioSeguridad(rs.getDouble("arbitrio_seguridad"));
				//--
				obj.setDeterminacionArbitriosId(rs.getInt("determinacion_arbitrios_id"));
				obj.setPredioId(rs.getInt("predio_id"));
				lDeterminacion.add(obj);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return lDeterminacion;
	}
	
	public int actualizaEstadoDtDeterminacionArbitrio(Integer determinacionId,String estado)throws Exception{
		int result=0;
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("update ").append(Constante.schemadb).append(".dt_determinacion_recojo set estado=? where determinacion_arbitrios_id=? and estado='1'");
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setString(1, estado);
			pst.setInt(2, determinacionId);
			result=pst.executeUpdate();
			
			StringBuffer SQL2=new StringBuffer();
			SQL2.append("update ").append(Constante.schemadb).append(".dt_determinacion_seguridad set estado=? where determinacion_arbitrios_id=? and estado='1'");
			PreparedStatement pst2=connect().prepareStatement(SQL2.toString());
			pst2.setString(1, estado);
			pst2.setInt(2, determinacionId);
			result=pst2.executeUpdate();
			
		}catch(Exception e){
			throw(e);
		}
		return result;
	}
	
	public DeterminacionArbitriosDTO getDeterminacionArbitriosDTO(Integer djId)throws Exception{
		DeterminacionArbitriosDTO calculo = null;
		try {
			StringBuilder SQL = new StringBuilder();
			SQL.append("select a.determinacion_arbitrios_id,d.dj_id,d.anno_dj,d.desc_domicilio,l.frecuencia frecuencia_limpieza,l.tasa_ml_anual,d.frente,c.descipcion_corta grupo_cercania,s.descripcion_corta zona_seguridad,c.costo_anual,r.frecuencia frecuencia_recojo, ");
			SQL.append("a.arbitrio_limpieza,a.arbitrio_parques,a.arbitrio_recojo,a.arbitrio_seguridad, ");
			SQL.append("a.grupo_cercania_id,a.frecuencia_limpieza_id,a.frecuencia_recojo_id,a.zona_seguridad_id ");
			SQL.append("from ").append(Constante.schemadb).append(".dt_determinacion_arbitrios a "); 
			SQL.append("inner join ").append(Constante.schemadb).append(".rp_djpredial d on (a.dj_id=d.dj_id and d.estado='1') ");
			SQL.append("inner join ").append(Constante.schemadb).append(".dt_frecuencia_limpieza l on (l.frecuencia_limpieza_id=a.frecuencia_limpieza_id) ");
			SQL.append("inner join ").append(Constante.schemadb).append(".dt_frecuencia_recojo r on (r.frecuencia_recojo_id=a.frecuencia_recojo_id) ");
			SQL.append("inner join ").append(Constante.schemadb).append(".dt_grupo_cercania c on (c.grupo_cercania_id=a.grupo_cercania_id) ");
			SQL.append("inner join ").append(Constante.schemadb).append(".dt_zona_seguridad s on (s.zona_seguridad_id=a.zona_seguridad_id) ");
			SQL.append("where a.dj_id=? and a.estado='1' ");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1,djId);
			ResultSet rs = pst.executeQuery();
			
			if (rs.next()) {
				calculo = new DeterminacionArbitriosDTO();
				calculo.setDeterminacionArbitriosId(rs.getInt("determinacion_arbitrios_id"));
				calculo.setArbitrioLimpieza(rs.getBigDecimal("arbitrio_limpieza"));
		        calculo.setArbitrioParques(rs.getBigDecimal("arbitrio_parques"));
		        calculo.setArbitrioRecojo(rs.getBigDecimal("arbitrio_recojo"));
		        calculo.setArbitrioSeguridad(rs.getBigDecimal("arbitrio_seguridad"));
		        calculo.setFrecuenciaLimpieza(rs.getInt("frecuencia_limpieza"));
		        calculo.setTasaMlAnualLimpieza(rs.getBigDecimal("tasa_ml_anual"));
		        calculo.setFrenteMlLimpieza(rs.getBigDecimal("frente"));
		    	//Calculo arbitrio de Recojo
		        calculo.setFrecuenciaRecojo(rs.getInt("frecuencia_recojo"));
		        //calculo.setlRecojoArbitrio(lRecojoArbitrio);
		    	//Calculo arbitrio Parques
		    	calculo.setGrupoCercaniaParques(rs.getString("grupo_cercania"));
		    	calculo.setCostoAnualParques(rs.getBigDecimal("costo_anual"));
		        //Calculo arbitrio de Seguridad
		    	calculo.setZonaSeguridad(rs.getString("zona_seguridad"));
		    	//calculo.setlSeguridadArbitrio(lSeguridadArbitrio);
		    	//General
		    	calculo.setDjId(rs.getInt("dj_id"));
		    	calculo.setAnnoDeterminacion(rs.getInt("anno_dj"));
		    	calculo.setDireccionCompleta(rs.getString("desc_domicilio"));
		    	//--
		    	calculo.setGrupoCercaniaParquesId(rs.getInt("grupo_cercania_id"));
		    	calculo.setFrecuenciaLimpiezaId(rs.getInt("frecuencia_limpieza_id"));
		    	calculo.setFrecuenciaRecojoId(rs.getInt("frecuencia_recojo_id"));
		    	calculo.setZonaSeguridadId(rs.getInt("zona_seguridad_id"));
			}

		} catch (Exception e) {
			throw(e);
		}
		return calculo;
	}
	
	public List<SeguridadArbitriosDTO> getAllSeguridadArbitrios(Integer determinacionArbitriosId)throws Exception{
		List<SeguridadArbitriosDTO> lDeterminacion = new LinkedList<SeguridadArbitriosDTO>();
		try {
			StringBuilder SQL = new StringBuilder();
			SQL.append("select u.descripcion_corta,s.tasa_anual,u.categoria_uso_id ");
			SQL.append("from ").append(Constante.schemadb).append(".dt_determinacion_seguridad s ");
			SQL.append("inner join ").append(Constante.schemadb).append(".rp_categoria_uso u on (s.categoria_uso_id=u.categoria_uso_id) ");
			SQL.append("where s.determinacion_arbitrios_id=? and s.estado='1' ");
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1,determinacionArbitriosId);
			
			ResultSet rs = pst.executeQuery();

			while(rs.next()) {
				SeguridadArbitriosDTO obj= new SeguridadArbitriosDTO();
				obj.setUso(rs.getString("descripcion_corta"));
				obj.setTasaAnual(rs.getBigDecimal("tasa_anual"));
				obj.setCategoriaUsoId(rs.getInt("categoria_uso_id"));
				lDeterminacion.add(obj);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return lDeterminacion;
	}
	
	public List<RecojoArbitriosDTO> getAllRecojoArbitrios(Integer determinacionArbitriosId)throws Exception{
		List<RecojoArbitriosDTO> lDeterminacion = new LinkedList<RecojoArbitriosDTO>();
		try {
			StringBuilder SQL = new StringBuilder();
			SQL.append("select u.descripcion_corta,r.area_m2,r.costo_m2_anual,u.categoria_uso_id ");
			SQL.append("from ").append(Constante.schemadb).append(".dt_determinacion_recojo r ");
			SQL.append("inner join ").append(Constante.schemadb).append(".rp_categoria_uso u on (r.categoria_uso_id=u.categoria_uso_id) ");
			SQL.append("where r.determinacion_arbitrios_id=? and r.estado='1' ");
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1,determinacionArbitriosId);
			
			ResultSet rs = pst.executeQuery();

			while(rs.next()) {
				RecojoArbitriosDTO obj= new RecojoArbitriosDTO();
				obj.setUso(rs.getString("descripcion_corta"));
				obj.setAreaM2(rs.getBigDecimal("area_m2"));
				obj.setCostoM2Anual(rs.getBigDecimal("costo_m2_anual"));
				obj.setCategoriaUsoId(rs.getInt("categoria_uso_id"));
				lDeterminacion.add(obj);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return lDeterminacion;
	}
	
	/**
	 * Obtiene el listado de los anios en los cuales el contribuyente posee DJ para su determinación
	 * @param personaId
	 * @param esFiscalizado
	 * @return
	 * @throws Exception
	 */
	public ArrayList<DtDeterminacionPeriodoDTO> getAllDtDeterminacionPeriodo(Integer personaId,Boolean esFiscalizado)throws Exception{
		ArrayList<DtDeterminacionPeriodoDTO> lDeterminacion = new ArrayList<DtDeterminacionPeriodoDTO>();
		try {
			StringBuilder SQL = new StringBuilder();
			
			SQL.append(" SELECT DISTINCT case when isnull(a.es_descargo,'0')='1' and year(CONVERT(date,a.fecha_descargo,103))=a.anno_dj then 0 else isnull(r.redetermina,-1) end redetermina, ");
			SQL.append(" a.anno_dj,isnull(n.determinacion_id,0) determinacion_id,n.anno_determinacion,n.fecha_registro,n.base_imponible,n.base_exonerada,n.base_afecta,n.impuesto,n.nro_cuotas,n.persona_id,n.fiscalizado,n.fisca_aceptada,n.fisca_cerrada,n.estado ");   
			SQL.append(" FROM  (  ");
			SQL.append(" SELECT DISTINCT  p.anno_dj,p.persona_id,p.fecha_descargo,p.es_descargo FROM ").append(Constante.schemadb).append(".rp_djpredial p WHERE p.estado='1' AND p.flag_dj_anno='1' "); 
			SQL.append(" ) a  ");
			SQL.append(" LEFT JOIN (select d.determinacion_id,d.anno_determinacion,d.fecha_registro,d.base_imponible,d.base_exonerada,d.base_afecta,d.impuesto,d.nro_cuotas,d.persona_id,d.fiscalizado,d.fisca_aceptada,d.fisca_cerrada,d.estado ");    
			SQL.append(" from ").append(Constante.schemadb).append(".dt_determinacion d    ");
			SQL.append(" where (d.estado='1' or d.estado='2') and d.concepto_id='1' "); 
			SQL.append(" ) n ON (a.anno_dj=n.anno_determinacion and a.persona_id=n.persona_id) "); 
			SQL.append(" LEFT JOIN ( select d.determinacion_id,sum(case when p.estado='1' then 0 else 1 end) redetermina from ").append(Constante.schemadb).append(".dt_determinacion_predio d ");  
			SQL.append(" inner join ").append(Constante.schemadb).append(".rp_djpredial p on (p.dj_id=d.dj_id)  ");
			SQL.append(" group by d.determinacion_id  ");
			SQL.append(" ) r ON (r.determinacion_id=n.determinacion_id) "); 
			SQL.append(" WHERE a.persona_id=? ");
			SQL.append(" ORDER BY a.anno_dj DESC  ");   
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1,personaId);
			
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				DtDeterminacionPeriodoDTO obj = new DtDeterminacionPeriodoDTO();
				obj.setAnnoDj(rs.getString("anno_dj"));
				if(rs.getInt("determinacion_id")>0){
					obj.getDeterminacion().setDeterminacionId(rs.getInt("determinacion_id"));
					//INICIO ITANTAMANGO
					obj.getDeterminacion().setCantRusticos(getCantTipoPredioPorDeterminacion(obj.getDeterminacion().getDeterminacionId(), Constante.TIPO_PREDIO_RUSTICO));
					obj.getDeterminacion().setCantUrbanos(getCantTipoPredioPorDeterminacion(obj.getDeterminacion().getDeterminacionId(), Constante.TIPO_PREDIO_URBANO));
					//FIN ITANTAMANGO					
					obj.getDeterminacion().setAnnoDeterminacion(rs.getObject("anno_determinacion")==null?null:rs.getInt("anno_determinacion"));
					obj.getDeterminacion().setFechaRegistro(rs.getTimestamp("fecha_registro"));
					obj.getDeterminacion().setBaseImponible(rs.getBigDecimal("base_imponible"));
					obj.getDeterminacion().setBaseExonerada(rs.getBigDecimal("base_exonerada"));
					obj.getDeterminacion().setBaseAfecta(rs.getBigDecimal("base_afecta"));
					obj.getDeterminacion().setImpuesto(rs.getBigDecimal("impuesto"));
					obj.getDeterminacion().setNroCuotas(rs.getObject("nro_cuotas")==null?null:rs.getInt("nro_cuotas"));
					obj.getDeterminacion().setPersonaId(rs.getObject("persona_id")==null?null:rs.getInt("persona_id"));
					obj.getDeterminacion().setFiscalizado(rs.getString("fiscalizado"));
					obj.getDeterminacion().setFiscaAceptada(rs.getString("fisca_aceptada"));
					obj.getDeterminacion().setFiscaCerrada(rs.getString("fisca_cerrada"));
					obj.getDeterminacion().setEstado(rs.getString("estado"));
				}
				int cantidad=0;
				if(rs.getObject("anno_determinacion")!=null){
					cantidad=getCantidadDjAgregado(personaId, rs.getInt("anno_determinacion"));
				}
				obj.setRedetermina(rs.getInt("redetermina")+cantidad);
				obj.setPersonaId(personaId);
				lDeterminacion.add(obj);
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return lDeterminacion;
	}
	
	
	public int getCantidadDjAgregado(Integer personaId, Integer periodo){
		Integer cantidad=0;
		try {
			StringBuilder SQL = new StringBuilder();
			SQL.append("select COUNT(*) cantidad_add from ( ");
			SQL.append("select dj_id from ").append(Constante.schemadb).append(".rp_djpredial where estado='1' and persona_id=? and flag_dj_anno='1' ");
			SQL.append("and year(CONVERT(date,fecha_adquisicion,103))<=? and anno_dj=? and isnull(es_descargo,'0')='0' and motivo_declaracion_id!=").append(Constante.MOTIVO_DECLARACION_MIGRACION);
			SQL.append(" EXCEPT ");
			SQL.append("select d.dj_id from ").append(Constante.schemadb).append(".dt_determinacion_predio d ");  
			SQL.append("inner join ").append(Constante.schemadb).append(".dt_determinacion m on (m.determinacion_id=d.determinacion_id) ");
			SQL.append("where m.persona_id=? and m.estado='1' and m.anno_determinacion=? ");
			SQL.append(") a");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, personaId);
			pst.setInt(2, periodo);
			pst.setInt(3, periodo);
			pst.setInt(4, personaId);
			pst.setInt(5, periodo);
			
			ResultSet rs = pst.executeQuery();

			if(rs.next()) {
				cantidad=rs.getInt("cantidad_add");
			}
		} catch (Exception ex) {
			//throw(ex);
			ex.printStackTrace();
		}
		return cantidad;
	}
	
	/**
	 * Obtiene datos de los pagos hechos a las deudas de una determinación para
	 * saber si tiene deudas pagadas.
	 * 
	 * @param determinacionId
	 *            Identificador de determinación
	 * @return
	 */
	public DeudaPagosDTO getDeudaPagos(int determinacionId) throws Exception{
		try {
			StringBuilder SQL = new StringBuilder("exec stp_obtener_totales_deuda_cancelado_determinacion_por_anho " + determinacionId);

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();			

			DeudaPagosDTO pago = new DeudaPagosDTO();
			while (rs.next()) {
				//pago = new DeudaPagosDTO();
				pago.setTotalDeuda(pago.getTotalDeuda().add(rs.getBigDecimal("totalDeuda")));
				pago.setTotalCancelado(pago.getTotalCancelado().add(rs.getBigDecimal("totalCancelado")));
				pago.setDeudaMenosCancelado(pago.getTotalDeuda().subtract(pago.getTotalCancelado()));
			}
			return pago;
		} catch (Exception ex) {
			//throw(ex);
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * Obtiene los valores de las deudas de una determinación para saber si la
	 * deuda esta en coactiva
	 * 
	 * @param determinacionId
	 *            Identificador de determinación
	 * @return
	 */
	public List<DeudaValoresDTO> getDeudaValores(int persona,int concepto, int anio) throws Exception{
	//public List<DeudaValoresDTO> getDeudaValores(int determinacionId) throws Exception{
		try {
			StringBuilder SQL = new StringBuilder(
					"SELECT deu.deuda_id, isnull(deu.flag_cc,'0') flag_cc,isnull(deu.flag_op,'0') flag_op FROM ").append(Constante.schemadb).append(".cd_deuda deu ");
			SQL.append("WHERE deu.persona_id=" ).append( persona).append(" AND concepto_id = ").append( concepto).append(" AND anno_deuda =").append( anio).append(" and estado=1");
			
			//SQL.append("WHERE deu.determinacion_id=" + determinacionId); //::Comentado el 05-12-2014, Búsqueda en todos los registros de la deuda.
			//Agregué:: [ isnull(deu.flag_op,'0') flag_op ]:: 25-11-14

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();

			List<DeudaValoresDTO> listaValores = new ArrayList<DeudaValoresDTO>();
			while (rs.next()) {
				DeudaValoresDTO val = new DeudaValoresDTO();
				val.setDeudaId(rs.getInt("deuda_id"));
				val.setFlagCoactiva(rs.getString("flag_cc"));
				val.setFlagOp(rs.getString("flag_op"));//25-11-14
				listaValores.add(val);
			}
			return listaValores;
		} catch (Exception ex) {
			// TODO : Controller Exception
			ex.printStackTrace();
		}
		return null;
	}
	
	public int getCantTipoPredioPorDeterminacion(int determinaId, String tipoPredio) throws Exception{
		int cant=0;
		try {
			StringBuilder SQL = new StringBuilder("select COUNT(*) cantidad from ").append(Constante.schemadb).append(".dt_determinacion p inner join ").append(Constante.schemadb).append(".dt_determinacion_predio d on (p.determinacion_id=d.determinacion_id)");
			SQL.append("inner join ").append(Constante.schemadb).append(".rp_djpredial dj on (dj.dj_id=d.dj_id) where dj.tipo_predio='").append(tipoPredio).append("' and p.determinacion_id=").append(determinaId);
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				cant=rs.getInt("cantidad");
			}


		} catch (Exception e) {
			System.err.println(e);
			throw(e);
		}
		return cant;
	}
	
	public List<DtDeterminacion> checkDtDeterminacion(List<DtDeterminacion> list)
			throws Exception {
		try {
			if(list.size()>0){
				for(int i=0; list.size()>i;i++){
					int cantR=getCantTipoPredioPorDeterminacion(list.get(i).getDeterminacionId(), Constante.TIPO_PREDIO_RUSTICO);
					int cantU=getCantTipoPredioPorDeterminacion(list.get(i).getDeterminacionId(), Constante.TIPO_PREDIO_URBANO);
					list.get(i).setCantRusticos(cantR);
					list.get(i).setCantUrbanos(cantU);
				}
			}
			
			
		} catch (Exception e) {
			System.err.println(e);
			throw(e);		
		}
		return list;
	}
	
	public List<CdDeuda> getAllCdDeuda(Integer predioId,Integer personaId,Integer anioDeuda)throws Exception{
		Query q=null;
		List<CdDeuda> result=null;
		try{
			StringBuilder SQL = new StringBuilder();
			SQL.append(" select c.* from ").append(Constante.schemadb).append(".cd_deuda c  ");
			SQL.append(" inner join ").append(Constante.schemadb).append(".dt_determinacion_predio d on(d.determinacion_id=c.determinacion_id) ");
			SQL.append(" where d.estado='1' and c.persona_id=? and c.anno_deuda=? and d.predio_id=? ");
			q  = em.createNativeQuery(SQL.toString(),CdDeuda.class);
			q.setParameter(1,personaId);
			q.setParameter(2,anioDeuda);
			q.setParameter(3,predioId);
			result = q.getResultList();
			return  result;
		}catch(Exception e){
		    q = null;
	        return  result;
	    }
	}
	
	public String selectDeterminacionInactivar(Integer personaId,Integer periodo)throws Exception{
		String lstDeterminacion="";
		try {
			StringBuilder SQL = new StringBuilder();
			SQL.append(" select isnull(STUFF( ");
			SQL.append(" (select ','+convert(varchar(100),determinacion_id) from dbo.Dt_Determinacion where ");   
			SQL.append(" determinacion_id not in (   ");
			SQL.append(" SELECT max(determinacion_id) FROM dbo.Dt_Determinacion m WHERE persona_Id=? ");
			SQL.append(" AND anno_Determinacion=? AND concepto_Id=1 and subconcepto_id=10   ");
			SQL.append(" AND estado='1' AND ((fiscalizado='0' and fisca_aceptada='0' and fisca_cerrada='0') or (fiscalizado='1' and fisca_aceptada='1' and fisca_cerrada='1')) ");
			SQL.append(" )  ");
			SQL.append(" AND persona_Id=? AND anno_Determinacion=? AND concepto_Id=1 AND subconcepto_id=10 ");
			SQL.append(" AND estado='1'  ");
			SQL.append("and determinacion_id not in (SELECT  deuda.determinacion_id FROM cd_deuda AS deuda");//Se agregó esta línea para que no inactive las determinaciones con valores
			SQL.append(" WHERE deuda.persona_id=? AND deuda.concepto_id=1 AND deuda.anno_deuda=?");//Se agregó esta línea para que no inactive las determinaciones con valores
			SQL.append(" AND deuda.estado = 1 AND deuda.flag_op = 1 GROUP BY deuda.determinacion_id)");//Se agregó esta línea para que no inactive las determinaciones con valores
			SQL.append(" FOR XML PATH('')),1,1,''),0) AS lstDeterminacionId  ");
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, personaId);
			pst.setInt(2, periodo);
			pst.setInt(3, personaId);
			pst.setInt(4, periodo);
			pst.setInt(5, personaId);
			pst.setInt(6, periodo);
			
			ResultSet rs = pst.executeQuery();
			if(rs.next()){
				lstDeterminacion=rs.getString("lstDeterminacionId");
			}
		} catch (Exception e) {
			System.err.println(e);
			throw(e);
		}
		return lstDeterminacion;
	}
	
	public void inactivaDeterminacionAnterior(Integer personaId,Integer periodo)throws Exception{
		try{
			String lstDeterminacionId=selectDeterminacionInactivar(personaId,periodo);
			
			StringBuilder SQL = new StringBuilder();//Dt_Determinacion
			SQL.append(" update dbo.dt_determinacion set estado='9' where determinacion_id in (").append(lstDeterminacionId).append(")");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.executeUpdate();
			
			//Inactiva deuda anterior::inicio
			SQL = new StringBuilder();//dt_determinacion_construccion
			SQL.append(" update dbo.cd_deuda set estado='9', fecha_actualizacion=getdate() ");
			SQL.append(" where persona_id=? and concepto_id=1 and anno_deuda=? and estado='1' ");  
			SQL.append(" and cc_total_deuda > isnull(cc_total_cancelado, 0) AND estado = 1 ");
			SQL.append(" and determinacion_id IN (").append(lstDeterminacionId).append(") ");
			pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, personaId);
			pst.setInt(2, periodo);
			pst.executeUpdate();
			//Inactiva deuda anterior::fin
			
			SQL = new StringBuilder();//dt_determinacion_construccion
			SQL.append(" update dbo.dt_determinacion_construccion set estado='9' where determinacion_id in (").append(lstDeterminacionId).append(")");
			pst = connect().prepareStatement(SQL.toString());
			pst.executeUpdate();
			
			SQL = new StringBuilder();//dt_determinacion_instalacion
			SQL.append(" update dbo.dt_determinacion_instalacion set estado='9' where determinacion_id in (").append(lstDeterminacionId).append(")");
			pst = connect().prepareStatement(SQL.toString());
			pst.executeUpdate();
			
			SQL = new StringBuilder();//dt_determinacion_predio
			SQL.append(" update dbo.dt_determinacion_predio set estado='9' where determinacion_id in (").append(lstDeterminacionId).append(")");
			pst = connect().prepareStatement(SQL.toString());
			pst.executeUpdate();
			
		}catch(Exception e){
			throw(e);
	    }
	}
	
	public List<DtDeterminacion> getAllDtDeterminacionArbitriosPredio(Integer predioId,Integer personaId,Integer periodo,Integer conceptoId)throws Exception{
		Query q=null;
		List<DtDeterminacion> result=null;
		try{
			StringBuilder SQL = new StringBuilder();
			SQL.append(" SELECT m.* FROM ").append(Constante.schemadb).append(".Dt_Determinacion m "); 
			SQL.append(" WHERE m.estado='1' AND m.concepto_Id=? AND m.anno_Determinacion=? AND "); 
			SQL.append(" m.persona_Id=? ");
			SQL.append(" and m.djreferencia_id in ( ");
			SQL.append(" select d.dj_id from ").append(Constante.schemadb).append(".rp_djpredial d "); 
			SQL.append(" WHERE d.predio_id=? and d.persona_id=? ");
			//Se corrige el inconveniente de la doble deterterminacion de arbitrios
			//SQL.append(" WHERE d.predio_id=? and d.persona_id=? and d.estado='1' ");
			SQL.append(" and d.anno_dj=? ");
			SQL.append(" ) ");
			
			q  = em.createNativeQuery(SQL.toString(),DtDeterminacion.class);
			q.setParameter(1,conceptoId);
			q.setParameter(2,periodo);
			q.setParameter(3,personaId);
			q.setParameter(4,predioId);
			q.setParameter(5,personaId);
			q.setParameter(6,periodo);
			
			result = q.getResultList();
			
		}catch(Exception e){
			e.printStackTrace();
		    q = null;
	    }
		return  result;
	}
	
	public ArrayList<Integer> getAllPersonaIds(Integer personaId1, Integer personaId2) throws Exception{
		ArrayList<Integer> ltsPersonasId = new ArrayList<Integer>();
		try{
			StringBuilder SQL = new StringBuilder();
			SQL.append(" SELECT m.persona_id FROM ").append(Constante.schemadb).append(".mp_persona m "); 
			SQL.append(" WHERE m.estado='1' AND persona_id>=? AND persona_id<=? "); 
			SQL.append(" order by persona_id asc ");
			
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, personaId1);
			pst.setInt(2, personaId2);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){
				Integer personaId;
				personaId = rs.getInt("persona_id");
				ltsPersonasId.add(personaId);
			}
			
		}catch(Exception e){
			System.err.println(e);
			throw(e);
	    }		
		return ltsPersonasId;
	}
	
	public Double obtenerArancelOtrosFrentes(Integer ubicacionId, Integer periodo)throws Exception{
		Double valorArancel=Double.valueOf(0);
		try{
			StringBuffer SQL=new StringBuffer();
			if(ubicacionId > 0){
				SQL.append("SELECT valor_arancel FROM ").append(Constante.schemadb).append(".dt_arancel_ubicacion WHERE ubicacion_id = ? AND periodo = ? and estado='").append(Constante.ESTADO_ACTIVO).append("'");
			}
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			if(ubicacionId > 0){
				pst.setInt(1, ubicacionId);
				pst.setInt(2, periodo);
				
				ResultSet rs=pst.executeQuery();
				if(rs.next()){
					valorArancel= rs.getDouble("valor_arancel");
				}	
			}
		}catch(Exception e){
			throw(e);
		}
		return valorArancel;
	}

	public int getNroCuotasByAnio(int anio, Integer conceptoId,	Integer subconceptoId) throws SisatException{
		int nroCuotas = 0;
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("SELECT nro_cuotas FROM ").append(Constante.schemadb).append(".dt_cuota_concepto where concepto_id=? and subconcepto_id=? AND periodo = ?");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			
			pst.setInt(1, conceptoId);			
			pst.setInt(2, subconceptoId);
			pst.setInt(3, anio);
			
			ResultSet rs=pst.executeQuery();
			if(rs.next()){
				nroCuotas= rs.getInt("nro_cuotas");
			}	
		} catch (Exception e) {
			throw new SisatException("ERROR en la tabla dt_cuota_concepto. ".concat(e.getMessage()), e);
		}
		return nroCuotas;
	}

	public Timestamp getFechaVenciminetoByCuotaAnio(int anio, Integer conceptoId, Integer subconceptoId, int nroCuota) throws SisatException{
		Timestamp fechaVencimiento = null;
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("SELECT fecha_vencimiento FROM ").append(Constante.schemadb).append(".dt_fecha_vencimiento where periodo=? and concepto_id=? and subconcepto_id=? and cuota=?");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			
			pst.setInt(1, anio);
			pst.setInt(2, conceptoId);
			pst.setInt(3, subconceptoId);			
			pst.setInt(4, nroCuota);
				
			ResultSet rs=pst.executeQuery();
			if(rs.next()){
				fechaVencimiento= rs.getTimestamp("fecha_vencimiento");
			}	
		} catch (Exception e) {
			throw new SisatException("ERROR en la tabla dt_fecha_vencimiento. ".concat(e.getMessage()), e);
		}
		return fechaVencimiento;
	}

	public ArrayList<DtDeterminacionDTO> getArbitriosGenerados(Integer personaId, Integer djId) throws SisatException {
		ArrayList<DtDeterminacionDTO> lstArbitriosGenerados = new ArrayList<DtDeterminacionDTO>();
		try{
			StringBuilder SQL = new StringBuilder();
			SQL.append(" SELECT d.determinacion_id, d.persona_id, d.anno_determinacion, d.concepto_id, d.subconcepto_id, d.impuesto, d.nro_cuotas, ");
			SQL.append(" d.djreferencia_id, d.estado, d.usuario_id, d.fecha_registro,  d.terminal, o.nombre_usuario, r.predio_id FROM ").append(Constante.schemadb).append(".dt_determinacion d ");
			SQL.append(" inner join ").append(Constante.schemadb).append(".rp_djpredial r on (r.dj_id = d.djreferencia_id) ");
			SQL.append(" left join ").append(Constante.schemadb).append(".sg_usuario o on (o.usuario_id = d.usuario_id) ");
			SQL.append(" WHERE d.persona_id=? AND d.concepto_id= 3 AND d.estado= '1' AND d.djreferencia_id = ? ");		
			SQL.append(" order by d.djreferencia_id, d.anno_determinacion, d.subconcepto_id asc ");
			
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, personaId);	
			pst.setInt(2, djId);			
			
			
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){
				DtDeterminacionDTO determinacion = new DtDeterminacionDTO();
				
				determinacion.setDeterminacionId(rs.getInt("determinacion_id"));
				determinacion.setPersonaId(rs.getInt("persona_id"));
				determinacion.setAnnoDeterminacion(rs.getInt("anno_determinacion"));
				determinacion.setConceptoId(rs.getInt("concepto_id"));
				determinacion.setSubconceptoId(rs.getInt("subconcepto_id"));
				determinacion.setImpuesto(rs.getBigDecimal("impuesto"));
				determinacion.setNroCuotas(rs.getInt("nro_cuotas"));
				determinacion.setDjreferenciaId(rs.getInt("djreferencia_id"));
				determinacion.setEstado(rs.getString("estado"));
				determinacion.setUsuarioId(rs.getInt("usuario_id"));
				determinacion.setFechaRegistro(rs.getTimestamp("fecha_registro"));
				determinacion.setTerminal(rs.getString("terminal"));
				determinacion.setNombreUsuario(rs.getString("nombre_usuario"));
				determinacion.setPredioId(rs.getInt("predio_id"));
				
				lstArbitriosGenerados.add(determinacion);
			}
			
		}catch(Exception e){
			throw new SisatException("ERROR en la tabla dt_determinacion. ".concat(e.getMessage()), e);
	    }	
		return lstArbitriosGenerados;
	}

	public Integer getDjByPredioId(Integer personaId, Integer predioId,	Integer anio)  throws SisatException{
		Integer djId = 0;
		try{
			StringBuilder SQL = new StringBuilder();
			SQL.append(" SELECT MAX(dj_id) dj_id  FROM ").append(Constante.schemadb).append(".rp_djpredial ");
			SQL.append(" WHERE persona_id=? and predio_id=? and anno_dj=? and estado='1' and flag_dj_anno=1 ");		
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, personaId);	
			pst.setInt(2, predioId);			
			pst.setInt(3, anio);	
			
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){						
				djId = rs.getInt("dj_id");				
			}
			
		}catch(Exception e){
			throw new SisatException("ERROR en la tabla dt_determinacion. ".concat(e.getMessage()), e);
	    }	
		return djId;
	}

	/**
	 * Determinacion masiva del 2014
	 */
	
	public Integer getCategoriaUso2014(Integer tipoUsoId)throws Exception{
		Integer categoriaUsoId=Constante.RESULT_PENDING;
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("SELECT categoria_uso_id FROM ").append(Constante.schemadb).append(".rp_categoria_tipo_uso2014 WHERE tipo_uso_id=?");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, tipoUsoId);
			
			ResultSet rs=pst.executeQuery();
			if(rs.next()){
				categoriaUsoId= rs.getInt("categoria_uso_id");
			}
		}catch(Exception e){
			throw(e);
		}
		return categoriaUsoId;
	}
	public List<TipoUsoDTO> getCategoriaUso2014All()throws Exception{
		List<TipoUsoDTO> lst=new LinkedList<TipoUsoDTO>();
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("SELECT categoria_uso_id,tipo_uso_id FROM ").append(Constante.schemadb).append(".rp_categoria_tipo_uso2014 ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				TipoUsoDTO obj=new TipoUsoDTO (rs.getInt("tipo_uso_id"),rs.getInt("categoria_uso_id"));
				lst.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		return lst;
	}
	
	
	public Integer getCategoriaSeguridadTipoUso2014(Integer tipoUsoId)throws Exception{
		Integer categoriaUsoId=Constante.RESULT_PENDING;
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("SELECT categoria_uso_seguridad_id AS categoria_uso_id FROM ").append(Constante.schemadb).append(".rp_categoria_seguridad_tipo_uso2014 where tipo_uso_id=?");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, tipoUsoId);
			
			ResultSet rs=pst.executeQuery();
			if(rs.next()){
				categoriaUsoId= rs.getInt("categoria_uso_id");
			}
		}catch(Exception e){
			throw(e);
		}
		return categoriaUsoId;
	}
	
	
	public List<TipoUsoDTO> getCategoriaSeguridadTipoUso2014All()throws Exception{
		List<TipoUsoDTO> lst=new LinkedList<TipoUsoDTO>();
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("SELECT categoria_uso_seguridad_id AS categoria_uso_id,tipo_uso_id FROM ").append(Constante.schemadb).append(".rp_categoria_seguridad_tipo_uso2014");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				TipoUsoDTO obj=new TipoUsoDTO (rs.getInt("tipo_uso_id"),rs.getInt("categoria_uso_id"));
				lst.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		return lst;
	}
	
	public List<DeudaValoresDTO> getValorCoactiva(Integer codigo,String anio,Integer tipo,Integer determinacion) throws Exception {
			List<DeudaValoresDTO> list =  new ArrayList<DeudaValoresDTO>();
			
				try{
					StringBuilder SQL = new StringBuilder("dbo.stp_getValorDeuda ?,?,?,?");
					
					PreparedStatement pst = connect().prepareStatement(SQL.toString());
					pst.setInt(1, codigo);
					pst.setString(2, anio);
					pst.setInt(3, tipo);
					pst.setInt(4, determinacion);
					
					ResultSet rs = pst.executeQuery();
					
					while(rs.next()){
				
						DeudaValoresDTO obj = new DeudaValoresDTO();
				
						obj.setDeudaId(rs.getInt("cantidad"));
						

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
	
	public List<DeudaValoresDTO> getValorArbitrios(Integer codigo,Integer anio,Integer determinacion) throws Exception {
		List<DeudaValoresDTO> list =  new ArrayList<DeudaValoresDTO>();
		
			try{
				StringBuilder SQL = new StringBuilder("dbo.stp_getValorDeudaArbitrios ?,?,?");
				
				PreparedStatement pst = connect().prepareStatement(SQL.toString());
				pst.setInt(1, codigo);
				pst.setInt(2, anio);
				pst.setInt(3, determinacion);
				
				ResultSet rs = pst.executeQuery();
				
				while(rs.next()){
			
					DeudaValoresDTO obj = new DeudaValoresDTO();
			
					obj.setDeudaId(rs.getInt("cantidad"));
					

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
	
//getDeudaValoresArbitrios:: Devuelve el flag_rda en 1 si la deuda está asociada a un un valor RD ARBITRIOS :: 28-11-14
	public List<DeudaValoresDTO> getDeudaValoresArbitrios(int determinacionId) throws Exception{
		try {
			StringBuilder SQL = new StringBuilder(
					"SELECT deu.deuda_id, isnull(deu.flag_cc,'0') flag_cc,isnull(deu.flag_rda,'0') flag_rda FROM ").append(Constante.schemadb).append(".cd_deuda deu ");
			SQL.append("WHERE deu.determinacion_id=" + determinacionId);

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();

			List<DeudaValoresDTO> listaValores = new ArrayList<DeudaValoresDTO>();
			while (rs.next()) {
				DeudaValoresDTO val = new DeudaValoresDTO();
				val.setDeudaId(rs.getInt("deuda_id"));
				val.setFlagCoactiva(rs.getString("flag_cc"));
				val.setFlagOp(rs.getString("flag_rda"));
				listaValores.add(val);
			}
			return listaValores;
		} catch (Exception ex) {
			// TODO : Controller Exception
			ex.printStackTrace();
		}
		return null;
	}
	
	public Integer actualizarEstadoDeterminacion(Integer determinacion,String estado)throws Exception{
		int result = 0;
		try {
			StringBuffer SQL = new StringBuffer();

			SQL.append(" UPDATE ").append(Constante.schemadb).append(".dt_determinacion set estado =?,fecha_actualizacion=? where determinacion_id = ?");
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setString(1,estado);
			pst.setTimestamp(2, DateUtil.getCurrentDate());
			pst.setInt(3,determinacion);

			result = pst.executeUpdate();
		} catch (Exception e) {
			throw (e);
		}
		return result;
		
	}
	
	public List<DtDeterminacion> getAllDtDeterminacionArbitriosSubconcepto(Integer predioId,Integer personaId,Integer periodo,Integer conceptoId,Integer subconceptoId)throws Exception{
		Query q=null;
		List<DtDeterminacion> result=null;
		try{
			StringBuilder SQL = new StringBuilder();
			SQL.append(" SELECT m.* FROM ").append(Constante.schemadb).append(".Dt_Determinacion m "); 
			SQL.append(" WHERE m.estado='1' AND m.concepto_Id=? AND m.anno_Determinacion=? AND "); 
			SQL.append(" m.persona_Id=? ");
			SQL.append(" and m.djreferencia_id in ( ");
			SQL.append(" select d.dj_id from ").append(Constante.schemadb).append(".rp_djpredial d "); 
			SQL.append(" WHERE d.predio_id=? and d.persona_id=? ");
			SQL.append(" and d.anno_dj=? ");
			SQL.append(" )and m.subconcepto_id=? ");
			
			q  = em.createNativeQuery(SQL.toString(),DtDeterminacion.class);
			q.setParameter(1,conceptoId);
			q.setParameter(2,periodo);
			q.setParameter(3,personaId);
			q.setParameter(4,predioId);
			q.setParameter(5,personaId);
			q.setParameter(6,periodo);
			q.setParameter(7,subconceptoId);
			
			result = q.getResultList();
			
		}catch(Exception e){
			e.printStackTrace();
		    q = null;
	    }
		return  result;
	}
	public DatosDeterminacionPredialValoresDTO getMontoDeterminacionValor(int persona,int concepto,int anio) {
		try {
//			StringBuilder SQL = new StringBuilder(
//			"SELECT    isnull(sum( d.monto_original),0) monto FROM ");
//			SQL.append("(SELECT  deu.determinacion_id, deu.monto_original FROM cd_deuda AS deu ");
//			SQL.append("WHERE deu.persona_id=" ).append(persona).append(" AND deu.concepto_id=").append(concepto).append(" AND deu.anno_deuda=").append(anio);
//			SQL.append(" AND deu.estado = 1 AND deu.flag_op = 1 GROUP BY deu.determinacion_id, deu.monto_original) AS d ");
			
			StringBuilder SQL = new StringBuilder("dbo.stp_getMontoDeterminacionConValor ?,?,?");
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
							  pst.setInt(1, persona);
						      pst.setInt(2, concepto);
						      pst.setInt(3, anio);
			ResultSet rs = pst.executeQuery();

			DatosDeterminacionPredialValoresDTO datos = null;
			while (rs.next()) {
				datos = new DatosDeterminacionPredialValoresDTO();
				datos.setImpuesto(rs.getBigDecimal("monto"));
			}
			return datos;
		} catch (Exception ex) {
			// TODO : Controller Exception
			System.out.println("ERROR: " + ex);
		}
		return null;
	}
	public Integer actualizarDiffDeterminacion(Integer determinacion,BigDecimal diferencia)throws Exception{
		int result = 0;
		try {
			StringBuffer SQL = new StringBuffer();

				SQL.append(" UPDATE " + SATParameterFactory.getDBNameScheme()
						+ ".dt_determinacion ");
				SQL.append(" SET impuesto_diferencia=").append(diferencia);
				SQL.append(" where determinacion_id =").append(determinacion);
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.execute();

			result = pst.executeUpdate();
		} catch (Exception e) {
			throw (e);
		}
		return result;
		
	}
	
	public DeudaPagosPredialDTO getDeudaValoresPagos(int persona,int anio,int determinacionId) {
		try {
			StringBuilder SQL = new StringBuilder(
					"SELECT SUM(deu.total_deuda) AS totalDeuda, isnull(SUM(deu.total_cancelado),0) AS totalCancelado, SUM(deu.total_deuda-deu.total_cancelado) AS deudaMenosCancelado FROM ").append(Constante.schemadb).append(".cd_deuda deu ");
			SQL.append("WHERE deu.determinacion_id=" ).append( determinacionId).append(" and (deu.flag_op<>1 or deu.flag_op is null)and deu.estado=1 and deu.concepto_id=1 and deu.determinacion_id not in (");
			SQL.append("SELECT  d.determinacion_id FROM (SELECT  de.determinacion_id, de.monto_original FROM cd_deuda AS de WHERE de.persona_id= ").append(persona).append("AND de.anno_deuda= ").append(anio);
			SQL.append("AND de.concepto_id=1 AND de.estado = 1 AND de.flag_op = 1 GROUP BY de.determinacion_id, de.monto_original)AS d )");
			//and deu.determinacion_id!=2632447
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();

			DeudaPagosPredialDTO pago = null;
			while (rs.next()) {
				pago = new DeudaPagosPredialDTO();
				pago.setTotalDeuda(rs.getBigDecimal("totalDeuda"));
				pago.setTotalCancelado(rs.getBigDecimal("totalCancelado"));
				pago.setDeudaMenosCancelado(rs
						.getBigDecimal("deudaMenosCancelado"));
			}
			return pago;
//			if(pago!=null){
//				return (DeudaPagosPredialDTO)pago;
//			}
//			return null; 
			
		} catch (Exception ex) {
			// TODO : Controller Exception
			System.out.println("ERROR: " + ex);
		}
			return null;
		
	}
	public List<DatosDeterminacionPredialValoresDTO> getDeterminacionValores(int persona,int concepto,int anio,int determinacion) throws Exception {
		List<DatosDeterminacionPredialValoresDTO> lDtsinValor=new LinkedList<DatosDeterminacionPredialValoresDTO>();
		try {
			StringBuilder SQL = new StringBuilder(
			"SELECT  d.determinacion_id FROM cd_deuda AS d INNER JOIN dt_determinacion  dt ON d.determinacion_id = dt.determinacion_id");
			SQL.append(" WHERE (d.determinacion_id NOT IN  (SELECT determinacion_id FROM cd_deuda AS deu ");
			SQL.append(" WHERE   persona_id =" ).append(persona).append(" AND concepto_id=").append(concepto).append(" AND anno_deuda=").append(anio);
			SQL.append(" and estado = 1 AND flag_op = 1 GROUP BY determinacion_id)) ");
			SQL.append(" and d.persona_id=").append(persona).append(" and d.concepto_id=").append(concepto).append(" AND d.anno_deuda=").append(anio).append(" AND d.estado = 1 and d.determinacion_id!=").append(determinacion);
			SQL.append(" GROUP BY d.determinacion_id");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				DatosDeterminacionPredialValoresDTO datos = new DatosDeterminacionPredialValoresDTO();
				datos.setDeterminacionId(rs.getInt("determinacion_id"));
				lDtsinValor.add(datos);
			}

		}catch(Exception e){
			throw(e);
		}
		return lDtsinValor;
//		if(lDtsinValor!=null&&lDtsinValor.size()>0){
//			return lDtsinValor;
//		}else 
//			return null;
	}
	
	public DtDeterminacion getMinDeterminacion(Integer personaId,Integer periodo,Integer concepto,String estado)throws Exception{
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("SELECT m.* FROM dt_determinacion m WHERE m.persona_id=? AND m.anno_determinacion=? AND m.concepto_Id=? and m.estado=? and m.determinacion_id in ( ");
			SQL.append("select min(d.determinacion_id)determinacion_id from( SELECT  deuda.determinacion_id FROM cd_deuda AS deuda INNER JOIN dt_determinacion dt ON deuda.determinacion_id = dt.determinacion_id");
			SQL.append(" WHERE deuda.persona_id=?  AND deuda.anno_deuda=? AND deuda.concepto_id=? AND deuda.estado = ? and dt.estado=1");
			SQL.append(" AND deuda.flag_op = 1 GROUP BY deuda.determinacion_id)d)");

			PreparedStatement pst=connect().prepareStatement(SQL.toString());
				pst.setInt(1, personaId);
				pst.setInt(2, periodo);
				pst.setInt(3, concepto);
				pst.setString(4, estado);
				pst.setInt(5, personaId);
				pst.setInt(6, periodo);
				pst.setInt(7, concepto);
				pst.setString(8, estado);
			
			ResultSet rs=pst.executeQuery();
			DtDeterminacion pago = null;

			while (rs.next()) {
				pago = new DtDeterminacion();
				pago.setDeterminacionId(rs.getInt("determinacion_id"));
				pago.setPersonaId(rs.getInt("persona_id"));
				pago.setAnnoDeterminacion(rs.getInt("anno_determinacion"));
				pago.setConceptoId(rs.getInt("concepto_id"));
				pago.setSubconceptoId(rs.getInt("subconcepto_id"));
				pago.setBaseImponible(rs.getBigDecimal("base_imponible"));
				pago.setBaseExonerada(rs.getBigDecimal("base_exonerada"));
				pago.setBaseAfecta(rs.getBigDecimal("base_afecta"));
				pago.setImpuesto(rs.getBigDecimal("impuesto"));
				pago.setNroCuotas(rs.getInt("nro_cuotas"));
				pago.setEstado(rs.getString("estado"));
				pago.setFechaRegistro(rs.getTimestamp("fecha_registro"));
			}
			if(pago!=null){
				return (DtDeterminacion)pago;
			}
			return null; 
		}catch(Exception e){
			throw(e);
		}
	
	}
	
	public List<DeudaValoresDTO> getDeudaValoresRd(int persona,int concepto, int anio) throws Exception{
		//public List<DeudaValoresDTO> getDeudaValores(int determinacionId) throws Exception{
			try {
				StringBuilder SQL = new StringBuilder(
						"SELECT deu.deuda_id, isnull(deu.flag_cc,'0') flag_cc,isnull(deu.flag_rdp,'0') flag_rdp FROM ").append(Constante.schemadb).append(".cd_deuda deu ");
				SQL.append("WHERE deu.persona_id=" ).append( persona).append(" AND concepto_id = ").append( concepto).append(" AND anno_deuda =").append( anio).append(" and estado=1");
				
				PreparedStatement pst = connect().prepareStatement(SQL.toString());
				ResultSet rs = pst.executeQuery();

				List<DeudaValoresDTO> listaValores = new ArrayList<DeudaValoresDTO>();
				while (rs.next()) {
					DeudaValoresDTO val = new DeudaValoresDTO();
					val.setDeudaId(rs.getInt("deuda_id"));
					val.setFlagCoactiva(rs.getString("flag_cc"));
					val.setFlagOp(rs.getString("flag_op"));//25-11-14
					listaValores.add(val);
				}
				return listaValores;
			} catch (Exception ex) {
				// TODO : Controller Exception
				ex.printStackTrace();
			}
			return null;
		}
	public Integer actualizarFlagVariacionDeterminacion(Integer determinacion,String estado)throws Exception{
		int result = 0;
		try {
			StringBuffer SQL = new StringBuffer();

			SQL.append(" UPDATE ").append(Constante.schemadb).append(".dt_determinacion set flag_variacion_valor=? where determinacion_id = ?");
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setString(1,estado);
			pst.setInt(2,determinacion);

			result = pst.executeUpdate();
		} catch (Exception e) {
			throw (e);
		}
		return result;
		
	}
	
	/**
	 * Determinacion arbitrios 2015
	 */
	public Integer getCategoriaUso2015(Integer tipoUsoId)throws Exception{
		Integer categoriaUsoId=Constante.RESULT_PENDING;
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("SELECT categoria_uso_id FROM ").append(Constante.schemadb).append(".rp_categoria_tipo_uso2015 WHERE tipo_uso_id=?");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, tipoUsoId);
			
			ResultSet rs=pst.executeQuery();
			if(rs.next()){
				categoriaUsoId= rs.getInt("categoria_uso_id");
			}
		}catch(Exception e){
			throw(e);
		}
		return categoriaUsoId;
	}
	public List<TipoUsoDTO> getCategoriaUso2015All()throws Exception{
		List<TipoUsoDTO> lst=new LinkedList<TipoUsoDTO>();
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("SELECT categoria_uso_id,tipo_uso_id FROM ").append(Constante.schemadb).append(".rp_categoria_tipo_uso2015 ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				TipoUsoDTO obj=new TipoUsoDTO (rs.getInt("tipo_uso_id"),rs.getInt("categoria_uso_id"));
				lst.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		return lst;
	}
	
	public Integer getCategoriaSeguridadTipoUso2015(Integer tipoUsoId)throws Exception{
		Integer categoriaUsoId=Constante.RESULT_PENDING;
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("SELECT categoria_uso_seguridad_id AS categoria_uso_id FROM ").append(Constante.schemadb).append(".rp_categoria_seguridad_tipo_uso2015 where tipo_uso_id=?");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, tipoUsoId);
			
			ResultSet rs=pst.executeQuery();
			if(rs.next()){
				categoriaUsoId= rs.getInt("categoria_uso_id");
			}
		}catch(Exception e){
			throw(e);
		}
		return categoriaUsoId;
	}
	
	
	public List<TipoUsoDTO> getCategoriaSeguridadTipoUso2015All()throws Exception{
		List<TipoUsoDTO> lst=new LinkedList<TipoUsoDTO>();
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("SELECT categoria_uso_seguridad_id AS categoria_uso_id,tipo_uso_id FROM ").append(Constante.schemadb).append(".rp_categoria_seguridad_tipo_uso2015");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				TipoUsoDTO obj=new TipoUsoDTO (rs.getInt("tipo_uso_id"),rs.getInt("categoria_uso_id"));
				lst.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		return lst;
	}
	
	public int getUsoPensionista(Integer djId){
		Integer usoCochera=0;
		try {
			StringBuilder SQL = new StringBuilder();
			SQL.append("select uso.tipo_uso_id ");
			SQL.append("from ").append(Constante.schemadb).append(".rp_djarbitrios arbitrio INNER JOIN rp_djuso uso ON arbitrio.djarbitrio_id = uso.djarbitrio_id");
			SQL.append(" where arbitrio.dj_id=? and uso.estado='1' and arbitrio.estado='1' ");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, djId);
						
			ResultSet rs = pst.executeQuery();

			if(rs.next()) {
				usoCochera=rs.getInt("tipo_uso_id");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return usoCochera;
	}
	
	public int getCantidadUsoPensionista(Integer djId){
		Integer cantidad=0;
		try {
			StringBuilder SQL = new StringBuilder();
			SQL.append("select  COUNT(uso.tipo_uso_id)cantidad ");
			SQL.append("from ").append(Constante.schemadb).append(".rp_djarbitrios arbitrio INNER JOIN rp_djuso uso ON arbitrio.djarbitrio_id = uso.djarbitrio_id");
			SQL.append(" where arbitrio.dj_id=? and uso.estado='1' and arbitrio.estado='1' ");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, djId);
						
			ResultSet rs = pst.executeQuery();

			if(rs.next()) {
				cantidad=rs.getInt("cantidad");
			}
		} catch (Exception ex) {
			//throw(ex);
			ex.printStackTrace();
		}
		return cantidad;
	}

	/**
	 * Determinacion masiva -Arbitrios 2016
	 */
	public Integer getCategoriaUso2016(Integer tipoUsoId,Integer periodo)throws Exception{
		Integer categoriaUsoId=Constante.RESULT_PENDING;
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("SELECT categoria_uso_id FROM ").append(Constante.schemadb).append(".rp_categoria_recojo WHERE tipo_uso_id=? and periodo=?");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, tipoUsoId);
			pst.setInt(2, periodo);
			
			ResultSet rs=pst.executeQuery();
			if(rs.next()){
				categoriaUsoId= rs.getInt("categoria_uso_id");
			}
		}catch(Exception e){
			throw(e);
		}
		return categoriaUsoId;
	}
	
	public List<TipoUsoDTO> getCategoriaUso2016All(Integer periodo)throws Exception{
		List<TipoUsoDTO> lst=new LinkedList<TipoUsoDTO>();
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("SELECT categoria_uso_id,tipo_uso_id FROM ").append(Constante.schemadb).append(".rp_categoria_recojo where periodo=? ");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, periodo);
			
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				TipoUsoDTO obj=new TipoUsoDTO (rs.getInt("tipo_uso_id"),rs.getInt("categoria_uso_id"));
				lst.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		return lst;
	}
	
	public Integer getCategoriaSeguridadTipoUso2016(Integer tipoUsoId,Integer periodo)throws Exception{
		Integer categoriaUsoId=Constante.RESULT_PENDING;
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("SELECT categoria_uso_id AS categoria_uso_id FROM ").append(Constante.schemadb).append(".rp_categoria_seguridad where tipo_uso_id=? and periodo=?");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, tipoUsoId);
			pst.setInt(2, periodo);
			
			ResultSet rs=pst.executeQuery();
			if(rs.next()){
				categoriaUsoId= rs.getInt("categoria_uso_id");
			}
		}catch(Exception e){
			throw(e);
		}
		return categoriaUsoId;
	}

	public List<TipoUsoDTO> getCategoriaSeguridadTipoUso2016All(Integer periodo)throws Exception{
		List<TipoUsoDTO> lst=new LinkedList<TipoUsoDTO>();
		try{
			StringBuffer SQL=new StringBuffer();
			SQL.append("SELECT categoria_uso_id AS categoria_uso_id,tipo_uso_id FROM ").append(Constante.schemadb).append(".rp_categoria_seguridad where periodo=?");
			
			PreparedStatement pst=connect().prepareStatement(SQL.toString());
			pst.setInt(1, periodo);
			
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				TipoUsoDTO obj=new TipoUsoDTO (rs.getInt("tipo_uso_id"),rs.getInt("categoria_uso_id"));
				lst.add(obj);
			}
		}catch(Exception e){
			throw(e);
		}
		return lst;
	}

}
