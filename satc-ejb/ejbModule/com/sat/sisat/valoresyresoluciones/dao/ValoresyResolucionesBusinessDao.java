package com.sat.sisat.valoresyresoluciones.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.sat.sisat.common.dao.GeneralDao;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.SATParameterFactory;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.persistence.entity.GnCondicionEspecial;
import com.sat.sisat.persistence.entity.MpCondicionEspecialRequerimiento;
import com.sat.sisat.persistence.entity.MpCondicionEspecialRequisito;
import com.sat.sisat.persistence.entity.MpInspeccionCondicionEspecial;
import com.sat.sisat.persistence.entity.MpTipoCondicionEspecial;
import com.sat.sisat.persistence.entity.MpTipoDocumentoCondicionEspecial;
import com.sat.sisat.persistence.entity.NoMotivoNotificacion;
import com.sat.sisat.persistence.entity.NoNotificador;
import com.sat.sisat.persistence.entity.ReEstadoResolucion;
import com.sat.sisat.persistence.entity.TdRequisitoExpediente;
import com.sat.sisat.predial.dto.MpRequerimientoCondicionEspecialDTO;
import com.sat.sisat.predial.dto.SustentoCondicionEspecialDTO;
import com.sat.sisat.valoresyresoluciones.dto.BuscarActoCoactivoDTO;
import com.sat.sisat.valoresyresoluciones.dto.BuscarActoOrdinarioDTO;
import com.sat.sisat.valoresyresoluciones.dto.BuscarDescargoDTO;
import com.sat.sisat.valoresyresoluciones.dto.DatosActoDTO;
import com.sat.sisat.valoresyresoluciones.dto.PagosActoDTO;

public class ValoresyResolucionesBusinessDao extends GeneralDao{

	public List<BuscarActoOrdinarioDTO> getAllActosOrdinarios(Integer personaId,String nroActo) throws Exception{
		
		List<BuscarActoOrdinarioDTO> lista= new ArrayList<BuscarActoOrdinarioDTO>();
		
			StringBuffer SQL= new StringBuffer();
			SQL.append(" select ca.acto_id, ca.tipo_acto_id, ta.descripcion TipoActo,ca.nro_acto,ca.fecha_emision,ca.nro_resolucion, ");
			SQL.append(" ca.fecha_notificacion ,gc.descripcion concepto , ca.monto_deuda total ");
			
			//SQL.append(",dbo.fnGN_interesSimple(ca.monto_deuda,null,,getdate())");
			
			SQL.append(" ,(select sum(ad.total_debitos) from ").append(Constante.schemadb).append(".cc_acto_deuda ad where ad.acto_id=ca.acto_id) ");
			SQL.append("-(select SUM(spa.monto_pago) from ").append(Constante.schemadb).append(".cj_pago spa ");
			SQL.append("inner join ").append(Constante.schemadb).append(".cd_deuda scd on scd.deuda_id=spa.deuda_id  ");
			SQL.append("inner join ").append(Constante.schemadb).append(".cc_acto_deuda sad on sad.deuda_id=scd.deuda_id  "); 
			SQL.append("inner join ").append(Constante.schemadb).append(".cc_acto sca on sca.acto_id=sad.acto_id  ");
			SQL.append("where sca.acto_id=ca.acto_id ) saldo  ");
			
			SQL.append(",(select isnull(SUM(spa.monto_pago),0.0)"); 
			SQL.append("from ").append(Constante.schemadb).append(".cj_pago spa  ");
			SQL.append("inner join ").append(Constante.schemadb).append(".cd_deuda scd on scd.deuda_id=spa.deuda_id  ");
			SQL.append("inner join ").append(Constante.schemadb).append(".cc_acto_deuda sad on sad.deuda_id=scd.deuda_id  ");
			SQL.append("inner join ").append(Constante.schemadb).append(".cc_acto sca on sca.acto_id=sad.acto_id  ");
			SQL.append("where sca.acto_id=ca.acto_id )pago ");
			
			
			SQL.append("from ").append(Constante.schemadb).append(".cc_acto ca ");
			SQL.append("inner join ").append(Constante.schemadb).append(".cc_tipo_acto ta on ta.tipo_acto_id=ca.tipo_acto_id "); 
			SQL.append("inner join ").append(Constante.schemadb).append(".gn_concepto gc on gc.concepto_id=ca.concepto_id  ");
			
			SQL.append(" where ca.estado=1 and ca.persona_id= ?");
			
			if(nroActo!=null && nroActo.trim().length()>0){
			SQL.append(" and ca.nro_acto= ?");	
			}
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			
			int cont=1;
			if(personaId!=null){
				pst.setInt(cont, personaId);
			}
			if(nroActo!=null && nroActo.trim().length()>0){
				pst.setString(++cont, nroActo.trim());
			}
			
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				BuscarActoOrdinarioDTO obj= new BuscarActoOrdinarioDTO();
				obj.setActoId(rs.getInt("acto_id"));
				obj.setDescTipoActo(rs.getString("TipoActo"));
				obj.setNroActo(rs.getString("nro_acto"));
				obj.setFechaEmision(rs.getTimestamp("fecha_emision"));
				obj.setNroResolucion(rs.getString("nro_resolucion"));
				obj.setFechaNotificacion(rs.getTimestamp("fecha_notificacion"));
				obj.setDescConcepto(rs.getString("concepto"));
				obj.setTotal(rs.getBigDecimal("total"));
				obj.setSaldo(rs.getBigDecimal("saldo"));
				obj.setPago(rs.getBigDecimal("pago"));
				obj.setTotalActualizado(getTotalDeudaActo(personaId, obj.getActoId(),DateUtil.getCurrentDate()));
				obj.setSaldoActualizado(obj.getTotalActualizado().subtract(obj.getPago()));
				if(obj.getSaldoActualizado().compareTo(new BigDecimal(0))<=0){
					obj.setSaldoActualizado(new BigDecimal("0.00"));
				}
				lista.add(obj);
			}
			
			return lista;

	}
	
	/**metodo que obtiene el total de deuda de un acto
	 * Author:ITANTAMANGO
	 * @param personaId
	 * @param actoId
	 * @param fechaConsulta
	 * @return
	 * @throws Exception
	 */
	public BigDecimal getTotalDeudaActo(Integer personaId,Integer actoId, Timestamp fechaConsulta)throws Exception{
		
		BigDecimal totalActual= new BigDecimal(0);
		try {
						
			StringBuffer SQL= new StringBuffer();
			SQL.append("exec  stp_cj_buscardeuda_acto ?,?,?");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, personaId);
			pst.setInt(2, actoId);
			pst.setTimestamp(3, fechaConsulta);
			
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				totalActual=rs.getBigDecimal("totalActual");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error: "+e);
		}
		
		return totalActual;
	}
	
	public List<BuscarActoCoactivoDTO> getAllActosCoactivos(Integer personaId,String nroRec)throws Exception{
		
		List<BuscarActoCoactivoDTO> lista= new ArrayList<BuscarActoCoactivoDTO>();
		
//		StringBuffer sentenciaSQL= new StringBuffer();
//		sentenciaSQL.append("select cr.nro_rec, cr.fecha_rec, cr.deuda_total total ");
//		sentenciaSQL.append("from ").append(Constante.schemadb).append(".cc_rec cr where cr.estado=1 and cr.persona_id=? ");
//		
//		if(nroRec!=null && nroRec.trim().length()>0){
//			sentenciaSQL.append(" and cr.nro_rec= ?");	
//		}
		
		StringBuilder sentenciaSQL = new StringBuilder("stp_cd_obtener_actos_coactivos ?,?"); /*Ago-2016*/
		PreparedStatement pst = connect().prepareStatement(sentenciaSQL.toString());
		
		int cont=1;
		if(personaId!=null){
			pst.setInt(cont, personaId);
		}
		if(nroRec!=null && nroRec.trim().length()>0){
			pst.setString(++cont, nroRec.trim());
		}
		else { /*Ago-2016*/
			pst.setString(++cont, nroRec==null?null:nroRec);
		}
		ResultSet rs=pst.executeQuery();
		while(rs.next()){
			BuscarActoCoactivoDTO obj= new BuscarActoCoactivoDTO();
			obj.setNroRec(rs.getString("nro_rec"));
			obj.setFechaRec(rs.getTimestamp("fecha_rec"));
			obj.setDeudaTotal(rs.getBigDecimal("total"));
			obj.setFechaNotificacionRec(rs.getTimestamp("fecha_notificacion"));/*Ago-2016*/
			obj.setDeudaCosta(rs.getBigDecimal("total_costas"));
			obj.setFechaCosta(rs.getTimestamp("fecha_registro"));
			obj.setEstadoRec(rs.getString("estado_expediente"));
			obj.setTipoActo(rs.getString("tipo_acto"));
			obj.setLoteExigible(rs.getInt("lote_exigible_id"));
			obj.setLoteRec(rs.getInt("lote_id"));
			obj.setAnioRec(rs.getInt("anno_deuda"));
			obj.setTipoRec(rs.getString("tipo_rec"));
			
			lista.add(obj);
		}
		return lista;
	}

	public List<DatosActoDTO> getAllDatosActo(int actoId )throws Exception{
		List<DatosActoDTO> lista= new ArrayList<DatosActoDTO>();
		
		StringBuffer sentenciaSQL = new StringBuffer();
		
		sentenciaSQL.append("select cn.descripcion_corta concepto,de.deuda_id, de.nro_cuota , de.anno_deuda ,ad.acto_id, ad.fecha_vencimiento,ca.fecha_emision, ");
		sentenciaSQL.append(" ad.base_imponible, ad.insoluto, ad.derecho_emision, ad.reajuste, ");
		sentenciaSQL.append(" ad.interes_capitalizado+ad.interes_anual interes, ad.total_debitos total, ca.nro_acto ");
		sentenciaSQL.append(" ,isnull((select sum(pa.monto_pago) from cj_pago pa where pa.deuda_id=de.deuda_id ),0) pago");
		sentenciaSQL.append(" ,ad.total_debitos-isnull((select sum(pa.monto_pago) from cj_pago pa where pa.deuda_id=de.deuda_id),0) saldo");
		sentenciaSQL.append(" from ").append(Constante.schemadb).append(".cc_acto_deuda ");
		sentenciaSQL.append(" ad inner join ").append(Constante.schemadb).append(".cc_acto ca on ca.acto_id=ad.acto_id ");
		sentenciaSQL.append(" inner join ").append(Constante.schemadb).append(".cd_deuda de on ad.deuda_id=de.deuda_id ");
		sentenciaSQL.append(" inner join ").append(Constante.schemadb).append(".gn_concepto cn on cn.concepto_id=de.concepto_id ");
		sentenciaSQL.append(" where ca.acto_id=? ");
		
		PreparedStatement pst = connect().prepareStatement(sentenciaSQL.toString());
		
		int cont=1;
		pst.setInt(cont, actoId);
		
		ResultSet rs=pst.executeQuery();
		while(rs.next()){
			DatosActoDTO obj= new DatosActoDTO();
			obj.setConcepto(rs.getString("concepto"));
			obj.setActoId(rs.getInt("acto_id"));
			obj.setNroCuota(rs.getInt("nro_cuota"));
			obj.setAnioDeuda(rs.getInt("anno_deuda"));
			obj.setFechaVenc(rs.getTimestamp("fecha_vencimiento"));
			obj.setFechaEmi(rs.getTimestamp("fecha_emision"));
			obj.setBaseImpo(rs.getBigDecimal("base_imponible"));
			obj.setInsoluto(rs.getBigDecimal("insoluto"));
			obj.setDerechoEmi(rs.getBigDecimal("derecho_emision"));
			obj.setReajuste(rs.getBigDecimal("reajuste"));
			obj.setInteres(rs.getBigDecimal("interes"));
//			obj.setInterCapit(rs.getBigDecimal("interes_capitalizado"));
//			obj.setInterAnual(rs.getBigDecimal("interes_anual"));
			obj.setTotal(rs.getBigDecimal("total"));
			obj.setNroActo(rs.getString("nro_acto"));
			obj.setDeudaId(rs.getInt("deuda_id"));
			if(rs.getBigDecimal("saldo").compareTo(new BigDecimal(0))<0){
				obj.setSaldo(new BigDecimal(0.00));
			}else{
				obj.setSaldo(rs.getBigDecimal("saldo"));	
			}
			
			obj.setPagos(rs.getBigDecimal("pago"));
			
			lista.add(obj);
		}
		
		return lista;
	}
	
	public List<PagosActoDTO> getPagosActo(int deudaId)throws Exception{
		List<PagosActoDTO> lista = new ArrayList<PagosActoDTO>();
		StringBuffer sentenciaSQL = new StringBuffer();
		
		sentenciaSQL.append("select pa.recibo_id,pa.deuda_id,rp.nro_recibo,gc.descripcion concepto,de.nro_cuota,pa.monto_pago,year(pa.fecha_pago) anio ");
		sentenciaSQL.append(" from ").append(Constante.schemadb).append(".cj_pago pa inner join ").append(Constante.schemadb).append(".cj_recibo_pago rp on rp.recibo_id=pa.recibo_id ");
		sentenciaSQL.append(" inner join ").append(Constante.schemadb).append(".cc_acto_deuda ad on ad.deuda_id=pa.deuda_id inner join ").append(Constante.schemadb).append(".gn_concepto gc on gc.concepto_id=ad.concepto_id ");
		sentenciaSQL.append(" inner join ").append(Constante.schemadb).append(".cd_deuda de on de.deuda_id=ad.deuda_id where pa.deuda_id=? ");

		PreparedStatement pst = connect().prepareStatement(sentenciaSQL.toString());
		
		int cont=1;
		pst.setInt(cont, deudaId);

		ResultSet rs=pst.executeQuery();
		while(rs.next()){
			PagosActoDTO obj=new PagosActoDTO();
			obj.setNroRecibo(rs.getString("nro_recibo"));
			obj.setConcepto(rs.getString("concepto"));
			obj.setNroCuota(rs.getInt("nro_cuota"));
			obj.setMontoPago(rs.getBigDecimal("monto_pago"));
			obj.setAnio(rs.getInt("anio"));

			lista.add(obj);
		}
		
		return lista;
	}
	
	/** Beneficio Tributario (Pensionista)
	 * */
		public List<MpCondicionEspecialRequisito> getAllRequisitoByCondicionEspecial(int tipo,int codigo) throws Exception {
			List<MpCondicionEspecialRequisito> lista = new ArrayList<MpCondicionEspecialRequisito>();
			try {
				StringBuilder SQL = new StringBuilder("stp_ce_getRequisitoByCondicion ?,?");
														
				PreparedStatement pst = connect().prepareStatement(SQL.toString());
				pst.setInt(1, tipo);
				pst.setInt(2, codigo);
	
				ResultSet rs = pst.executeQuery();
	
				while (rs.next()) {
					MpCondicionEspecialRequisito obj = new MpCondicionEspecialRequisito();
					obj.setCondEspecialRequisitoId(rs.getInt("condicion_especial_requisito_id"));
					obj.setTipoCondEspecId(rs.getInt("tipo_cond_especial_id"));
					obj.setRequisitoId(rs.getInt("requisito_id"));
					obj.setDescripcion(rs.getString("descripcion"));
					obj.setFlag(rs.getInt("flag_obligatorio"));
					lista.add(obj);
				}
			} catch (Exception e) {
				throw (e);
			}
			return lista;
		}

		public Integer registrarRequerimiento(Integer codigo,Integer tipoCondicion, String contribuyente, String direccion,Integer tipoPresentacion,Integer usuarioId, String terminal) throws Exception {
			int resultado = 0;
			try {
				CallableStatement pst = connect().prepareCall("{call dbo.stp_ce_registrar_requerimiento(?,?,?,?,?,?,?,?)}");
				pst.setInt(1, codigo);
				pst.setInt(2, tipoCondicion);
				pst.setString(3, contribuyente);
				pst.setString(4, direccion);
				pst.setInt(5, tipoPresentacion);
				pst.setInt(6, usuarioId);
				pst.setString(7, terminal);
				pst.registerOutParameter(8, Types.INTEGER);
				pst.execute();
				resultado=pst.getInt(8);
				//resultado = pst.executeUpdate();
//				pst.execute();
			} catch (Exception e) {
				throw (e);
			}
			return resultado;//Constante.RESULT_SUCCESS;
		}
	
		public Integer registrarSustento(Integer codigo, Integer condicionRequisito, String glosa,Integer flag,Integer id, Integer usuarioId,String terminal) throws Exception {
			try {
				CallableStatement pst = connect().prepareCall("{call dbo.stp_ce_registrar_sustento(?,?,?,?,?,?,?)}");
				pst.setInt(1, codigo);
				pst.setInt(2, condicionRequisito);
				pst.setString(3, glosa);
				pst.setInt(4, flag);
				pst.setInt(5, id);
				pst.setInt(6, usuarioId);
				pst.setString(7, terminal);
		
				pst.execute();
			} catch (Exception e) {
				throw (e);
			}
			return Constante.RESULT_SUCCESS;
		}

		public List<SustentoCondicionEspecialDTO> getAllRequisitoByCondicionByPersona(int codigo) throws Exception {
			List<SustentoCondicionEspecialDTO> lista = new ArrayList<SustentoCondicionEspecialDTO>();
			try {
				StringBuilder SQL = new StringBuilder("stp_ce_getRequisitoByPersona ?");
														
				PreparedStatement pst = connect().prepareStatement(SQL.toString());
				pst.setInt(1, codigo);
		
				ResultSet rs = pst.executeQuery();
		
				while (rs.next()) {
					SustentoCondicionEspecialDTO obj = new SustentoCondicionEspecialDTO();
					obj.setCondicionRequisitoId(rs.getInt("condicion_especial_requisito_id"));
					lista.add(obj);
				}
			} catch (Exception e) {
				throw (e);
			}
			return lista;
		}

		public List<MpTipoCondicionEspecial> getAllMpTipoCondicionEspecial(Integer tipoPersonaId, Integer subTipoPersonaId) throws Exception {
			List<MpTipoCondicionEspecial> lista = new LinkedList<MpTipoCondicionEspecial>();
			try {
				StringBuilder SQL = new StringBuilder("stp_ce_getCondicionEspecial ?,?,?");
				PreparedStatement pst = connect().prepareStatement(SQL.toString());
				pst.setInt(1, tipoPersonaId);
				pst.setString(2, Constante.ESTADO_ACTIVO);
				pst.setInt(3, subTipoPersonaId);
				ResultSet rs = pst.executeQuery();
		
				while (rs.next()) {
					MpTipoCondicionEspecial obj = new MpTipoCondicionEspecial();
					obj.setTipoCondEspecialId(rs.getInt("tipo_cond_especial_id"));
					obj.setDescripcion(rs.getString("descripcion"));
					obj.setEstado(rs.getString("estado"));
					obj.setUsuarioId(rs.getInt("usuario_id"));
					obj.setFechaRegistro(rs.getTimestamp("fecha_registro"));
					obj.setTerminal(rs.getString("terminal"));
					lista.add(obj);
				}
			} catch (Exception e) {
				throw (e);
			}
			return lista;
		}

		public List<ReEstadoResolucion> getAllEstadoCondicionEspecial(int id) throws Exception {
			List<ReEstadoResolucion> lista = new ArrayList<ReEstadoResolucion>();
			try {
				StringBuilder SQL = new StringBuilder("stp_ce_getEstadoByCondicion ?");
				PreparedStatement pst = connect().prepareStatement(SQL.toString());
				pst.setInt(1, id);
		
				ResultSet rs = pst.executeQuery();
		
				while (rs.next()) {
					ReEstadoResolucion obj = new ReEstadoResolucion();
					
					obj.setEstadoResolucionId(rs.getInt("estado_resolucion_id"));
					obj.setDescripcion(rs.getString("descripcion"));
					lista.add(obj);
				}
			} catch (Exception e) {
				throw (e);
			}
			return lista;
		}
	
		public List<MpRequerimientoCondicionEspecialDTO> getAllBandejaCondicionEspecial(int codigo) throws Exception {
			List<MpRequerimientoCondicionEspecialDTO> lista = new ArrayList<MpRequerimientoCondicionEspecialDTO>();
			try {
				StringBuilder SQL = new StringBuilder("stp_ce_getBandejaRequisitoByPersona ?");
														
				PreparedStatement pst = connect().prepareStatement(SQL.toString());
				pst.setInt(1, codigo);
		
				ResultSet rs = pst.executeQuery();
		
				while (rs.next()) {
					MpRequerimientoCondicionEspecialDTO obj = new MpRequerimientoCondicionEspecialDTO();
					
					obj.setCondicionEspecialId(rs.getInt("condicion_especial_id"));
					obj.setTipoDocResol(rs.getInt("tipo_doc_resol"));
					obj.setNombreDocResol(rs.getString("nombre_doc_resol"));
					obj.setNroDocResol(rs.getString("nro_doc_resol"));
					obj.setTipoInafectDocResol(rs.getInt("tipo_ce_doc_resol"));
					obj.setCondicion(rs.getString("condicion"));
					obj.setFecIniDocResol(rs.getTimestamp("fec_ini_doc_resol"));
					obj.setFecFinDocResol(rs.getTimestamp("fec_fin_doc_resol"));
					obj.setPorcentaje(rs.getDouble("porcentaje"));
					obj.setFecDocResol(rs.getTimestamp("fecha_doc_resol"));
					obj.setEstadoDocResol(rs.getString("estado_doc_resol"));
					obj.setFecRegDocResol(rs.getTimestamp("fec_reg_doc_resol"));
					obj.setEstadoResol(rs.getString("estado_resol"));
					obj.setCondicionRequerimientoId(rs.getInt("requerimiento_ce_id"));
					obj.setPersonaId(rs.getInt("persona_id"));
					obj.setTipoAsuntoId(rs.getInt("tipo_asunto_id"));
					obj.setMateriaTributaria(rs.getString("materia_tributaria"));
//					obj.setAsuntoId(rs.getInt("asunto_id"));
//					obj.setTipoAsunto(rs.getString("tipo_asunto"));
					obj.setNroExpediente(rs.getString("nro_expediente"));
					obj.setNroRequerimiento(rs.getString("nro_requerimiento"));
					obj.setFechaInicio(rs.getTimestamp("fecha_inicio_recepcion"));
					obj.setFechaFin(rs.getTimestamp("fecha_fin_recepcion"));
					obj.setFechaNotificacion(rs.getTimestamp("fecha_notificacion"));
					obj.setFechaFinInspeccion(rs.getTimestamp("fecha_fin_inspeccion"));
					obj.setEstadoResolucion(rs.getInt("estado_resolucion_id"));
					obj.setEstadoDescripcionResolucion(rs.getString("estado"));
					obj.setDias(rs.getInt("dias_transcurridos"));
					obj.setEstadoRequerimiento(rs.getInt("estado_ce_id"));
					obj.setEstadoDescripcionRequerimiento(rs.getString("estado_requerimiento"));
					obj.setFlagDocumentacion(rs.getInt("flag_documentacion"));
					obj.setFlagInspeccion(rs.getInt("flag_inspeccion"));
					obj.setFlagSituacion(rs.getInt("flag_situacion"));
					obj.setFlagRequerimiento(rs.getInt("flag_requerimiento"));
					lista.add(obj);
				}
			} catch (Exception e) {
				throw (e);
			}
			return lista;
		}
		
		public List<NoMotivoNotificacion> getAlNoMotivoNotificacion(Integer flagUbicacion)throws Exception {
			List<NoMotivoNotificacion> list = new ArrayList<NoMotivoNotificacion>();
			try {
				StringBuilder SQL = new StringBuilder("stp_ce_getMotivoNotificacion ?,?");
				PreparedStatement pst = connect().prepareStatement(SQL.toString());
				ResultSet rs = pst.executeQuery();
				pst.setString(1, Constante.ESTADO_ACTIVO);
				pst.setInt(2, flagUbicacion);
				while (rs.next()) {
					NoMotivoNotificacion obj = new NoMotivoNotificacion();
					obj.setMotivoNotificacionId(rs.getInt("motivo_notificacion_id"));
					obj.setDescripcion(rs.getString("descripcion"));
					obj.setUsuarioId(rs.getInt("usuario_id"));
					obj.setEstado(rs.getString("estado"));
					obj.setFechaRegistro(rs.getTimestamp("fecha_registro"));
					obj.setTerminal(rs.getString("terminal"));
					list.add(obj);
				}
			} catch (Exception e) {
				throw (e);
			}
			return list;
		}
		
		public List<NoNotificador> getAllNotificador() throws Exception {
			List<NoNotificador> list = new ArrayList<NoNotificador>();
			try {
				StringBuilder SQL = new StringBuilder("stp_ce_getNotificador");
				PreparedStatement pst = connect().prepareStatement(SQL.toString());
				ResultSet rs = pst.executeQuery();
				
				while (rs.next()) {
					NoNotificador obj = new NoNotificador();
					obj.setNotificadorId(rs.getInt("notificador_id"));
					obj.setApellidosNombres(rs.getString("apellidos_nombres"));
					list.add(obj);
				}
			} catch (Exception e) {
				throw (e);
			}
			return list;
		}
		
		public Integer registrarNotificacion(Integer requerimiento,Integer motivo, Integer notificador,Date fecha_notificacion,String observacion,Integer tipo,Integer usuarioId,String terminal) throws Exception {
				try {
					CallableStatement pst = connect().prepareCall("{call dbo.stp_ce_registrar_notificacion(?,?,?,?,?,?,?,?)}");
					pst.setInt(1, requerimiento);
					pst.setInt(2, motivo);
//					pst.setInt(3, notificador==null?0:notificador);
					pst.setInt(3, notificador);
					pst.setDate(4,DateUtil.dateToSqlDate(fecha_notificacion));
					pst.setString(5, observacion);
					pst.setInt(6, tipo);
					pst.setInt(7, usuarioId);
					pst.setString(8, terminal);
			
					pst.execute();
				} catch (Exception e) {
					throw (e);
				}
				return Constante.RESULT_SUCCESS;
		}
		
		public List<MpTipoDocumentoCondicionEspecial> getAllMpTipoDocumentoCondicionEspecial()throws Exception {
			List<MpTipoDocumentoCondicionEspecial> lista = new LinkedList<MpTipoDocumentoCondicionEspecial>();
			try {
				StringBuilder SQL = new StringBuilder("stp_ce_getTipoDocumento ?");
				PreparedStatement pst = connect().prepareStatement(SQL.toString());
				pst.setString(1, Constante.ESTADO_ACTIVO);

				ResultSet rs = pst.executeQuery();
				
				while (rs.next()) {
					MpTipoDocumentoCondicionEspecial obj = new MpTipoDocumentoCondicionEspecial();
					obj.setTipoDocumentoCondicionEspecialId(rs
							.getInt("tipo_documento_condicion_especial_id"));
					obj.setDescripcion(rs.getString("descripcion"));
					obj.setDescripcionCorta(rs.getString("descripcion_corta"));
					obj.setCodigoDocumento(rs.getString("codigo_documento"));
					obj.setEstado(rs.getString("estado"));
					lista.add(obj);
				}
			} catch (Exception e) {
				throw (e);
			}
			return lista;
		}
		
		public String getAllCorrelativoCondicionEspecial(Integer tipoDocId)throws Exception {
			String correlativo;
			try {
				String query = "{ call dbo.stp_ce_getCorrelativoByDocumento(?,?) }";
				CallableStatement pst = connect().prepareCall(query);
				pst.setInt(1, tipoDocId);
				pst.registerOutParameter(2, Types.VARCHAR);
				pst.execute();
				correlativo = pst.getString(2);
						
			} catch (Exception e) {
				throw (e);
			}
			return correlativo;
		}
		
		public Integer registrarResolucion(GnCondicionEspecial condicionEspecial,String tabla,Integer codigo,Integer usuarioId,String terminal) throws Exception {
			try {
				CallableStatement pst = connect().prepareCall("{call dbo.stp_ce_registrar_resolucion(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				pst.setInt(1, codigo);
				pst.setInt(2, condicionEspecial.getTipoDocumento());
				pst.setInt(3, condicionEspecial.getRequerimientoId());
				pst.setString(4,condicionEspecial.getNroDocumento());
				pst.setTimestamp(5, condicionEspecial.getFechaInicio());
				pst.setTimestamp(6, condicionEspecial.getFechaFin());
				pst.setTimestamp(7, condicionEspecial.getFechaDocumento());
				pst.setDouble(8, condicionEspecial.getPorcentaje());
				pst.setString(9,tabla);
				pst.setInt(10, condicionEspecial.getEstadoResolucionId());
				pst.setString(11,condicionEspecial.getGlosa());
				pst.setInt(12, usuarioId);
				pst.setString(13, terminal);
		
				pst.execute();
			} catch (Exception e) {
				throw (e);
			}
			return Constante.RESULT_SUCCESS;
		}
		
		
		public List<MpCondicionEspecialRequisito> obtenerRequisitosExpediente(Integer requerimientoId,Integer codigo) throws Exception {
			List<MpCondicionEspecialRequisito> listaRequisitos = new ArrayList<MpCondicionEspecialRequisito>();
			try{                                               
				StringBuilder SQL = new StringBuilder("stp_ce_getRequisitoByRequerimiento ?,?");

				PreparedStatement pst = connect().prepareStatement(SQL.toString());
				pst.setInt(1, requerimientoId);
				pst.setInt(2, codigo);
		
				ResultSet rs = pst.executeQuery();
			
					while (rs.next()) {
						MpCondicionEspecialRequisito r = new MpCondicionEspecialRequisito();
						
						r.setCondEspecialRequisitoId(rs.getInt("condicion_especial_requisito_id"));
						r.setDescripcion(rs.getString("descripcion"));
						r.setSelected(rs.getBoolean("flag_presentado"));
						r.setFlag(rs.getInt("flag_obligatorio"));
						r.setGlosa(rs.getString("glosa"));
	
						listaRequisitos.add(r);
					}

				} catch (Exception e) {
					throw (e);
				}
			return listaRequisitos;
		 }
		
		public List<MpTipoCondicionEspecial> obtenerCondicionExpediente(Integer requerimientoId) throws Exception {
			List<MpTipoCondicionEspecial> lista = new LinkedList<MpTipoCondicionEspecial>();
			try {
				StringBuilder SQL = new StringBuilder("stp_ce_getRequisitoByTipoCondicion ?");
				PreparedStatement pst = connect().prepareStatement(SQL.toString());
				pst.setInt(1, requerimientoId);

				ResultSet rs = pst.executeQuery();
		
				while (rs.next()) {
					MpTipoCondicionEspecial obj = new MpTipoCondicionEspecial();
					obj.setTipoCondEspecialId(rs.getInt("tipo_cond_especial_id"));
					obj.setDescripcion(rs.getString("descripcion"));
					obj.setEstado(rs.getString("estado"));
					obj.setUsuarioId(rs.getInt("usuario_id"));
					obj.setFechaRegistro(rs.getTimestamp("fecha_registro"));
					obj.setTerminal(rs.getString("terminal"));
					lista.add(obj);
				}
			} catch (Exception e) {
				throw (e);
			}
			return lista;
			}
		
		public Integer actualizarSustento(Integer codigo, Integer condicionRequisito, String glosa,Integer flag,Integer id, Integer usuarioId,
				   String terminal,Integer tipo) throws Exception {
				try {
					CallableStatement pst = connect().prepareCall("{call dbo.stp_ce_actualizar_sustento(?,?,?,?,?,?,?,?)}");
					pst.setInt(1, codigo);
					pst.setInt(2, condicionRequisito);
					pst.setString(3, glosa);
					pst.setInt(4, flag);
					pst.setInt(5, id);
					pst.setInt(6, usuarioId);
					pst.setString(7, terminal);
					pst.setInt(8, tipo);
//					pst.executeUpdate();
					pst.execute();
				} catch (Exception e) {
					throw (e);
				}
				return Constante.RESULT_SUCCESS;
			}
		
		public Integer cierreRequerimiento(Integer codigo,Integer id, Integer usuarioId,String terminal) throws Exception {
				try {
					CallableStatement pst = connect().prepareCall("{call dbo.stp_ce_actualizar_requerimiento(?,?,?,?)}");
					pst.setInt(1, codigo);
					pst.setInt(2, id);
					pst.setInt(3, usuarioId);
					pst.setString(4, terminal);
//					pst.executeUpdate();
					pst.execute();
				} catch (Exception e) {
					throw (e);
				}
				return Constante.RESULT_SUCCESS;
			}
		
		public Integer registrarInspecion(MpInspeccionCondicionEspecial mpInspeccionCondicionEspecial, Integer tipo) throws Exception {
			try {
				CallableStatement pst = connect().prepareCall("{call dbo.stp_ce_registrar_inspeccion(?,?,?,?,?,?)}");
				pst.setInt(1, mpInspeccionCondicionEspecial.getRequerimientoId());
				pst.setString(2, mpInspeccionCondicionEspecial.getDescripcion());
				pst.setInt(3, mpInspeccionCondicionEspecial.getFlagSituacion());
				pst.setInt(4, mpInspeccionCondicionEspecial.getUsuarioId());
				pst.setString(5, mpInspeccionCondicionEspecial.getTerminal());
				pst.setInt(6,tipo);
//				pst.executeUpdate();
				pst.execute();
			} catch (Exception e) {
				throw (e);
			}
			return Constante.RESULT_SUCCESS;
		}
		
		/** Condición de Deuda : Descargada/Prescrita/Compensada/Rectificada
		 * */
		
		public List<BuscarDescargoDTO> obtenerCondicionDeuda(Integer personaId) throws Exception {
			List<BuscarDescargoDTO> lista = new LinkedList<BuscarDescargoDTO>();
			try {									 
				StringBuilder SQL = new StringBuilder("stp_cd_obtener_condicion_deuda ?");
				PreparedStatement pst = connect().prepareStatement(SQL.toString());
				pst.setInt(1, personaId);
	
				ResultSet rs = pst.executeQuery();
	
				while (rs.next()) {
					BuscarDescargoDTO obj = new BuscarDescargoDTO();
					obj.setCondicionDeuda(rs.getString("tipo_descargo"));
					obj.setPersonaId(rs.getInt("persona_id"));
					obj.setDocumentoDeuda(rs.getString("tipo_documento"));
					obj.setNroDocumento(rs.getString("nro_documento"));
					obj.setObservacion(rs.getString("observacion"));
					obj.setConcepto(rs.getString("concepto"));
					obj.setAnio(rs.getInt("anno_deuda"));
					obj.setCuota(rs.getInt("nro_cuota"));
					obj.setInsoluto(rs.getBigDecimal("insoluto"));
					obj.setInteres(rs.getBigDecimal("interes"));
					obj.setReajuste(rs.getBigDecimal("reajuste"));
					obj.setTotalDeuda(rs.getBigDecimal("total_deuda"));
					obj.setTotalDescargado(rs.getBigDecimal("total_descargado"));
					obj.setFechaDocumento(rs.getTimestamp("fecha_documento"));
					obj.setFechaRegistro(rs.getTimestamp("fecha_registro"));
					obj.setUsuario(rs.getString("nombre_usuario"));
					lista.add(obj);
				}
			} catch (Exception e) {
				throw (e);
			}
			return lista;
		}
}
