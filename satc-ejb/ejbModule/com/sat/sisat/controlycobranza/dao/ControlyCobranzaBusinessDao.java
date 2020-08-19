package com.sat.sisat.controlycobranza.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Query;

import com.sat.sisat.common.dao.GeneralDao;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.SATParameterFactory;
import com.sat.sisat.controlycobranza.dto.CarteraItemDTO;
import com.sat.sisat.controlycobranza.dto.CcRecAcumulada;
import com.sat.sisat.controlycobranza.dto.FindCcActo;
import com.sat.sisat.controlycobranza.dto.FindCcCuotasAgrupadasLote;
import com.sat.sisat.controlycobranza.dto.FindCcLote;
import com.sat.sisat.controlycobranza.dto.FindCcLoteConcepto;
import com.sat.sisat.controlycobranza.dto.FindCcLoteDetalleActo;
import com.sat.sisat.controlycobranza.dto.FindCcLoteDetalleActoDeuda;
import com.sat.sisat.controlycobranza.dto.FindCcLoteOrdenImpresion;
import com.sat.sisat.controlycobranza.dto.FindCcLoteSector;
import com.sat.sisat.controlycobranza.dto.FindCcLoteTipoPersona;
import com.sat.sisat.controlycobranza.dto.FindCcRec;
import com.sat.sisat.controlycobranza.dto.FindDetalleLoteDescargo;
import com.sat.sisat.controlycobranza.dto.FindLoteDescargo;
import com.sat.sisat.controlycobranza.dto.FindNotificacion;
import com.sat.sisat.controlycobranza.dto.FindPeriodoCuota;
import com.sat.sisat.controlycobranza.dto.FindPersonaDescargo;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.notificaciones.dto.NotificacionesDTO;
import com.sat.sisat.persistence.entity.CcFirmante;
import com.sat.sisat.persistence.entity.CcLoteCuota;
import com.sat.sisat.persistence.entity.CcReclamo;
import com.sat.sisat.persistence.entity.CcTipoActo;
import com.sat.sisat.persistence.entity.CcTipoAgrupamiento;
import com.sat.sisat.persistence.entity.CcTipoLote;
import com.sat.sisat.persistence.entity.CcTipoOrdenImpresion;
import com.sat.sisat.persistence.entity.DtFechaVencimiento;
import com.sat.sisat.persistence.entity.GnConcepto;
import com.sat.sisat.persistence.entity.GnSector;
import com.sat.sisat.persistence.entity.GnSubconcepto;
import com.sat.sisat.persistence.entity.NoDetalleMasivaDigiNotif;
import com.sat.sisat.persistence.entity.NoDetalleMasivaNotificacion;
import com.sat.sisat.persistence.entity.NoMotivoNotificacion;
import com.sat.sisat.persistence.entity.NoNotificacion;
import com.sat.sisat.persistence.entity.NoNotificador;
import com.sat.sisat.persistence.entity.NoRelacionPersona;
import com.sat.sisat.persistence.entity.PaDocuAnexo;
import com.sat.sisat.persistence.entity.RvFiscalizacionNotificacion;
import com.sat.sisat.persistence.entity.SgUsuario;
import com.sat.sisat.predial.dto.FindPersona;
import com.sat.sisat.reportes.dto.ReporteNotificacionDTO;

public class ControlyCobranzaBusinessDao extends GeneralDao {
	
	
	
	public ArrayList<GnSector> getAllSector()
			throws Exception {
		// corregido
		ArrayList<GnSector> list = new ArrayList<GnSector>();
		
		try {
			String SQL = "[sp_list_gn_sector]";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			GnSector obj = new GnSector();
			
			while (rs.next()) {
				
				obj = new GnSector();
				
				obj.setSectorId(rs.getInt("sector_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				obj.setDescripcionCorta(rs.getString("descripcion_corta"));
				

				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}
	
	//Generar valores
	public Boolean registrarActoOpPredialGeneral  (Integer loteID, Integer usuarioID,String terminal)throws Exception {
		Boolean salida = false;
		
		try {
			CallableStatement cs = connect().prepareCall("{call dbo.spt_cc_genera_actoOP_predial_general(?,?,?)}");
			cs.setInt(1, loteID);
			cs.setInt(2, usuarioID);
			cs.setString(3, terminal);
			salida=cs.execute();
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
		}
		
		return salida;
	}	
			
	
	//Con este procedimiento se podrán obviar el resto de preliminar
	public List<FindCcLoteDetalleActo> getAllFindCcLotePreliminarOpPredialGeneral(
			Integer conceptoId, Integer anio, String cuotas,
			BigDecimal montoMin, BigDecimal montoMax, Integer personaId, Integer sectorID,Integer tipoUbicacion,Integer usuarioID)
			throws Exception {
		
		/*tipoUbicacion
		 	1. Ubicables
			2. Inubicables S/N
			3. inuibcables Otros
		 */
		
		List<FindCcLoteDetalleActo> list = new ArrayList<FindCcLoteDetalleActo>();
		try {
			String SQL = "sp_preliminar_OP_predial_general ?,?,?,?,?,?,?,?,?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, conceptoId);
			pst.setInt(2, anio);
			pst.setString(3, cuotas);
			pst.setBigDecimal(4, montoMin);
			pst.setBigDecimal(5, montoMax);
			pst.setInt(6, personaId);
			pst.setInt(7, sectorID);
			pst.setInt(8, tipoUbicacion);
			pst.setInt(9, usuarioID);
			
			
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FindCcLoteDetalleActo obj = new FindCcLoteDetalleActo();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setDescripcionPersona(rs.getString("datos_propietario"));
				obj.setDireccion(rs.getString("direccion_completa"));
				obj.setAnioCuota(rs.getInt("anio"));
				obj.setDescripcionConcepto(rs.getString("concepto"));
				obj.setNroCuota(rs.getInt("cuota"));
				obj.setInsoluto(rs.getBigDecimal("insoluto"));
				obj.setDerechoEmision(rs.getBigDecimal("emision"));
				obj.setReajuste(rs.getBigDecimal("reajuste"));
				obj.setInteresSimple(rs.getBigDecimal("interes"));
				obj.setDeudaTotal(rs.getBigDecimal("total"));
				obj.setSector(rs.getString("sector"));
				
				
				// obj.setNroActo(rs.getString("nro_acto"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}
	
	
	

	public List<FindCcLoteDetalleActo> getAllFindCcLotePreliminar(
			Integer lote_id, Integer TipoActoId) throws Exception {
		// corregido
		List<FindCcLoteDetalleActo> list = new ArrayList<FindCcLoteDetalleActo>();
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append(" select p.persona_id,p.nro_docu_identidad,p.apellidos_nombres,pd.domicilio_completo,t.descripcion sector, ");
			SQL.append(" sum(de.insoluto) insoluto,sum(de.reajuste) reajuste,sum(de.derecho_emision) derecho_emision,sum(de.interes_capitalizado) interes_capitalizado,sum(de.interes_simp) interes_simp,sum(de.total_deuda) total_deuda ");
			SQL.append(" from dbo.cd_saldos de  ");
			SQL.append(" inner join dbo.mp_persona p on (de.persona_id=p.persona_id) ");
			SQL.append(" inner join dbo.mp_persona_domicilio pd on (pd.persona_id=p.persona_id and pd.estado='1') ");
			SQL.append(" inner join dbo.mp_direccion d on (d.persona_id=p.persona_id and pd.direccion_id=pd.direccion_id and d.estado='1') ");
			SQL.append(" inner join dbo.gn_ubicacion u on (u.ubicacion_id=d.ubicacion_id) ");
			SQL.append(" inner join dbo.gn_sector_lugar s on (s.sector_lugar_id=u.sector_lugar_id) ");
			SQL.append(" inner join dbo.gn_sector t on (t.sector_id=s.sector_id) ");
			SQL.append(" where p.estado='1'  ");
			SQL.append(" and p.tipo_persona_id in (select tipo_persona_id from dbo.cc_lote_tipo_persona tp where tp.lote_id=? and tp.estado='1') ");
			SQL.append(" and s.sector_id in (select ls.sector_id from dbo.cc_lote_sector ls where ls.lote_id=? and ls.estado='1') ");
			SQL.append(" and de.anno_deuda*100+de.nro_cuota in (select ls.anno_cuota*100+ls.cuota from dbo.cc_lote_cuota ls where ls.lote_id=? and ls.estado='1') ");
			SQL.append(" and de.subconcepto_id in (select lc.subconcepto_id from dbo.cc_lote_concepto lc where lc.lote_id=? and lc.estado='1') ");
			SQL.append(" group by p.persona_id,p.nro_docu_identidad,p.apellidos_nombres,pd.domicilio_completo,t.descripcion ");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, lote_id);
			pst.setInt(2, lote_id);
			pst.setInt(3, lote_id);
			pst.setInt(4, lote_id);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FindCcLoteDetalleActo obj = new FindCcLoteDetalleActo();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setNroDocumento(rs.getString("nro_docu_identidad"));
				obj.setDescripcionPersona(rs.getString("apellidos_nombres"));
				obj.setDeudaTotal(rs.getBigDecimal("total_deuda"));
				obj.setDireccion(rs.getString("domicilio_completo"));
				obj.setSector(rs.getString("sector"));
				obj.setInsoluto(rs.getBigDecimal("insoluto"));
				obj.setReajuste(rs.getBigDecimal("reajuste"));
				obj.setDerechoEmision(rs.getBigDecimal("derecho_emision"));
				obj.setInteresCapitalizado(rs
						.getBigDecimal("interes_capitalizado"));
				obj.setInteresSimple(rs.getBigDecimal("interes_simp"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public List<DtFechaVencimiento> getAllMpCuotasAnio(Integer conceptoId,
			Integer periodo) throws Exception {
		List<DtFechaVencimiento> lista = new LinkedList<DtFechaVencimiento>();
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append("select fv.cuota,fv.fecha_vencimiento from ")
					.append(Constante.schemadb)
					.append(".dt_fecha_vencimiento fv where fv.concepto_id=? and fv.periodo=?");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, conceptoId);
			pst.setInt(2, periodo);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				DtFechaVencimiento obj = new DtFechaVencimiento();
				obj.setCuota(rs.getInt("cuota"));
				obj.setFechaVencimiento(rs.getTimestamp("fecha_vencimiento"));
				lista.add(obj);

			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	public List<DtFechaVencimiento> getAllMpCuotasxLoteIngresadas(Integer loteId)
			throws Exception {
		List<DtFechaVencimiento> lista = new LinkedList<DtFechaVencimiento>();
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append("select anno_cuota, cuota from cc_lote_cuota where lote_id=?");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, loteId);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				DtFechaVencimiento obj = new DtFechaVencimiento();
				obj.setCuota(rs.getInt("anno_cuota"));
				obj.setCuota(rs.getInt("cuota"));
				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	public List<FindCcLoteDetalleActo> getAllFindCcLotePreliminarRD(
			Integer personaId, Integer periodo) throws Exception {
		// corregido
		List<FindCcLoteDetalleActo> list = new ArrayList<FindCcLoteDetalleActo>();
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append("  select p.persona_id,p.nro_docu_identidad,p.apellidos_nombres,pd.domicilio_completo,t.descripcion sector, ");
			SQL.append("  sum(tf.base_imponible) base_imponible_fisca,sum(tf.base_afecta) base_afecta_fisca,sum(tf.impuesto) impuesto_fisca, ");
			SQL.append("  isnull(sum(tt.base_imponible),0) base_imponible,isnull(sum(tt.base_afecta),0) base_afecta,isnull(sum(tt.impuesto),0) impuesto ");
			SQL.append("  from dbo.mp_persona p  ");
			SQL.append("  inner join dbo.mp_persona_domicilio pd on (pd.persona_id=p.persona_id and pd.estado='1') ");
			SQL.append("  inner join dbo.mp_direccion d on (d.persona_id=p.persona_id and pd.direccion_id=pd.direccion_id and d.estado='1') ");
			SQL.append("  inner join dbo.gn_ubicacion u on (u.ubicacion_id=d.ubicacion_id)  ");
			SQL.append("  inner join dbo.gn_sector_lugar s on (s.sector_lugar_id=u.sector_lugar_id) ");
			SQL.append("  inner join dbo.gn_sector t on (t.sector_id=s.sector_id)  ");
			SQL.append("  inner join dbo.dt_determinacion tf on (tf.persona_id=p.persona_id and tf.anno_determinacion=? and tf.estado='1' and tf.fiscalizado='1' and tf.fisca_aceptada='0' and tf.fisca_cerrada='1' and tf.concepto_id=1)  ");
			SQL.append("  left join dbo.dt_determinacion tt on (tt.persona_id=p.persona_id and tt.anno_determinacion=? and tt.estado='1' and tt.fiscalizado='0' and tt.concepto_id=1) ");
			SQL.append("  where p.estado='1' ");
			SQL.append("  and p.persona_id=? ");
			SQL.append("  group by p.persona_id,p.nro_docu_identidad,p.apellidos_nombres,pd.domicilio_completo,t.descripcion  ");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, periodo);
			pst.setInt(2, periodo);
			pst.setInt(3, personaId);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FindCcLoteDetalleActo obj = new FindCcLoteDetalleActo();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setNroDocumento(rs.getString("nro_docu_identidad"));
				obj.setDescripcionPersona(rs.getString("apellidos_nombres"));
				obj.setDireccion(rs.getString("domicilio_completo"));
				obj.setSector(rs.getString("sector"));

				obj.setBaseImponible(rs.getBigDecimal("base_imponible"));
				obj.setBaseAfecta(rs.getBigDecimal("base_afecta"));
				obj.setImpuesto(rs.getBigDecimal("impuesto"));

				obj.setBaseImponibleFisca(rs
						.getBigDecimal("base_imponible_fisca"));
				obj.setBaseAfectaFisca(rs.getBigDecimal("base_afecta_fisca"));
				obj.setImpuestoFisca(rs.getBigDecimal("impuesto_fisca"));

				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public List<FindCcLoteDetalleActo> getAllFindCcLotePreliminarRsSn()
			throws Exception {
		// corregido lote SN
		List<FindCcLoteDetalleActo> list = new ArrayList<FindCcLoteDetalleActo>();
		try {
			String SQL = "sp_preliminar_rs_sn";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FindCcLoteDetalleActo obj = new FindCcLoteDetalleActo();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setNroDocumento(rs.getString("nro_doc_identidad"));
				obj.setDescripcionPersona(rs.getString("apellidos_nombres"));
				obj.setDireccion(rs.getString("direccion_completa"));
				obj.setPlaca(rs.getString("placa"));
				obj.setNroPapeleta(rs.getString("nro_papeleta"));
				obj.setMontoMulta(rs.getBigDecimal("monto_multa"));

				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public List<FindCcLoteDetalleActo> getAllFindCcLotePreliminarRsUbicables()
			throws Exception {
		// corregido
		List<FindCcLoteDetalleActo> list = new ArrayList<FindCcLoteDetalleActo>();
		try {
			String SQL = "sp_preliminar_rs_ubicables";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FindCcLoteDetalleActo obj = new FindCcLoteDetalleActo();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setNroDocumento(rs.getString("nro_doc_identidad"));
				obj.setDescripcionPersona(rs.getString("apellidos_nombres"));
				obj.setDireccion(rs.getString("direccion_completa"));
				obj.setPlaca(rs.getString("placa"));
				obj.setNroPapeleta(rs.getString("nro_papeleta"));
				obj.setMontoMulta(rs.getBigDecimal("monto_multa"));

				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public List<FindCcLoteDetalleActo> getAllFindCcLotePreliminarRsOtros()
			throws Exception {
		// corregido
		List<FindCcLoteDetalleActo> list = new ArrayList<FindCcLoteDetalleActo>();
		try {
			String SQL = "sp_preliminar_rs_otros";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FindCcLoteDetalleActo obj = new FindCcLoteDetalleActo();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setNroDocumento(rs.getString("nro_doc_identidad"));
				obj.setDescripcionPersona(rs.getString("apellidos_nombres"));
				obj.setDireccion(rs.getString("direccion_completa"));
				obj.setPlaca(rs.getString("placa"));
				obj.setNroPapeleta(rs.getString("nro_papeleta"));
				obj.setMontoMulta(rs.getBigDecimal("monto_multa"));

				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public List<FindCcLoteDetalleActo> getAllFindCcLotePreliminarOpVehicular(
			Integer conceptoId, Integer anio, String cuotas,
			BigDecimal montoMin, BigDecimal montoMax, Integer personaId)
			throws Exception {
		List<FindCcLoteDetalleActo> list = new ArrayList<FindCcLoteDetalleActo>();
		try {
			String SQL = "sp_preliminar_OP ?,?,?,?,?,?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, conceptoId);
			pst.setInt(2, anio);
			pst.setString(3, cuotas);
			pst.setBigDecimal(4, montoMin);
			pst.setBigDecimal(5, montoMax);
			pst.setInt(6, personaId);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FindCcLoteDetalleActo obj = new FindCcLoteDetalleActo();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setDescripcionPersona(rs.getString("datos_propietario"));
				obj.setDireccion(rs.getString("direccion_completa"));
				obj.setAnioCuota(rs.getInt("anio"));
				obj.setDescripcionConcepto(rs.getString("concepto"));
				obj.setNroCuota(rs.getInt("cuota"));
				obj.setInsoluto(rs.getBigDecimal("insoluto"));
				obj.setDerechoEmision(rs.getBigDecimal("emision"));
				obj.setReajuste(rs.getBigDecimal("reajuste"));
				obj.setInteresSimple(rs.getBigDecimal("interes"));
				obj.setDeudaTotal(rs.getBigDecimal("total"));
				// obj.setNroActo(rs.getString("nro_acto"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public List<FindCcLoteDetalleActo> getAllFindCcLotePreliminarOpVehicularSN(
			Integer conceptoId, Integer anio, String cuotas,
			BigDecimal montoMin, BigDecimal montoMax, Integer personaId)
			throws Exception {
		List<FindCcLoteDetalleActo> list = new ArrayList<FindCcLoteDetalleActo>();
		try {
			String SQL = "sp_preliminar_OP_inublicables_sn ?,?,?,?,?,?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, conceptoId);
			pst.setInt(2, anio);
			pst.setString(3, cuotas);			
			pst.setBigDecimal(4, montoMin);
			pst.setBigDecimal(5, montoMax);
			pst.setInt(6, personaId);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FindCcLoteDetalleActo obj = new FindCcLoteDetalleActo();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setDescripcionPersona(rs.getString("datos_propietario"));
				obj.setDireccion(rs.getString("direccion_completa"));
				obj.setAnioCuota(rs.getInt("anio"));
				obj.setDescripcionConcepto(rs.getString("concepto"));
				obj.setNroCuota(rs.getInt("cuota"));
				obj.setInsoluto(rs.getBigDecimal("insoluto"));
				obj.setDerechoEmision(rs.getBigDecimal("emision"));
				obj.setReajuste(rs.getBigDecimal("reajuste"));
				obj.setInteresSimple(rs.getBigDecimal("interes"));
				obj.setDeudaTotal(rs.getBigDecimal("total"));
				// obj.setNroActo(rs.getString("nro_acto"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public List<FindCcLoteDetalleActo> getAllFindCcLotePreliminarOpVehicularOtros(
			Integer conceptoId, Integer anio, String cuotas,
			BigDecimal montoMin, BigDecimal montoMax, Integer personaId)
			throws Exception {
		List<FindCcLoteDetalleActo> list = new ArrayList<FindCcLoteDetalleActo>();
		try {
			String SQL = "sp_preliminar_OP_inubicables_otros ?,?,?,?,?,?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, conceptoId);
			pst.setInt(2, anio);
			pst.setString(3, cuotas);
			pst.setBigDecimal(4, montoMin);
			pst.setBigDecimal(5, montoMax);
			pst.setInt(6, personaId);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FindCcLoteDetalleActo obj = new FindCcLoteDetalleActo();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setDescripcionPersona(rs.getString("datos_propietario"));
				obj.setDireccion(rs.getString("direccion_completa"));
				obj.setAnioCuota(rs.getInt("anio"));
				obj.setDescripcionConcepto(rs.getString("concepto"));
				obj.setNroCuota(rs.getInt("cuota"));
				obj.setInsoluto(rs.getBigDecimal("insoluto"));
				obj.setDerechoEmision(rs.getBigDecimal("emision"));
				obj.setReajuste(rs.getBigDecimal("reajuste"));
				obj.setInteresSimple(rs.getBigDecimal("interes"));
				obj.setDeudaTotal(rs.getBigDecimal("total"));
				// obj.setNroActo(rs.getString("nro_acto"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public List<FindCcLoteDetalleActo> getAllFindCcLotePreliminarOpPredial(
			Integer conceptoId, Integer anio, String cuotas,
			BigDecimal montoMin, BigDecimal montoMax, Integer personaId)
			throws Exception {
		List<FindCcLoteDetalleActo> list = new ArrayList<FindCcLoteDetalleActo>();
		try {
			String SQL = "sp_preliminar_OP_predial ?,?,?,?,?,?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, conceptoId);
			pst.setInt(2, anio);
			pst.setString(3, cuotas);
			pst.setBigDecimal(4, montoMin);
			pst.setBigDecimal(5, montoMax);
			pst.setInt(6, personaId);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FindCcLoteDetalleActo obj = new FindCcLoteDetalleActo();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setDescripcionPersona(rs.getString("datos_propietario"));
				obj.setDireccion(rs.getString("direccion_completa"));
				obj.setAnioCuota(rs.getInt("anio"));
				obj.setDescripcionConcepto(rs.getString("concepto"));
				obj.setNroCuota(rs.getInt("cuota"));
				obj.setInsoluto(rs.getBigDecimal("insoluto"));
				obj.setDerechoEmision(rs.getBigDecimal("emision"));
				obj.setReajuste(rs.getBigDecimal("reajuste"));
				obj.setInteresSimple(rs.getBigDecimal("interes"));
				obj.setDeudaTotal(rs.getBigDecimal("total"));
				// obj.setNroActo(rs.getString("nro_acto"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public List<FindCcLoteDetalleActo> getAllFindCcLotePreliminarOpPredialSn(
			Integer conceptoId, Integer anio, String cuotas,
			BigDecimal montoMin, BigDecimal montoMax, Integer personaId)
			throws Exception {
		List<FindCcLoteDetalleActo> list = new ArrayList<FindCcLoteDetalleActo>();
		try {
			String SQL = "sp_preliminar_OP_predial_sn ?,?,?,?,?,?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, conceptoId);
			pst.setInt(2, anio);
			pst.setString(3, cuotas);
			pst.setBigDecimal(4, montoMin);
			pst.setBigDecimal(5, montoMax);
			pst.setInt(6, personaId);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FindCcLoteDetalleActo obj = new FindCcLoteDetalleActo();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setDescripcionPersona(rs.getString("datos_propietario"));
				obj.setDireccion(rs.getString("direccion_completa"));
				obj.setAnioCuota(rs.getInt("anio"));
				obj.setDescripcionConcepto(rs.getString("concepto"));
				obj.setNroCuota(rs.getInt("cuota"));
				obj.setInsoluto(rs.getBigDecimal("insoluto"));
				obj.setDerechoEmision(rs.getBigDecimal("emision"));
				obj.setReajuste(rs.getBigDecimal("reajuste"));
				obj.setInteresSimple(rs.getBigDecimal("interes"));
				obj.setDeudaTotal(rs.getBigDecimal("total"));
				// obj.setNroActo(rs.getString("nro_acto"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public List<FindCcLoteDetalleActo> getAllFindCcLotePreliminarOpPredialOtros(
			Integer conceptoId, Integer anio, String cuotas,
			BigDecimal montoMin, BigDecimal montoMax, Integer personaId)
			throws Exception {
		List<FindCcLoteDetalleActo> list = new ArrayList<FindCcLoteDetalleActo>();
		try {
			String SQL = "sp_preliminar_OP_predial_otros ?,?,?,?,?,?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, conceptoId);
			pst.setInt(2, anio);
			pst.setString(3, cuotas);
			pst.setBigDecimal(4, montoMin);
			pst.setBigDecimal(5, montoMax);
			pst.setInt(6, personaId);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FindCcLoteDetalleActo obj = new FindCcLoteDetalleActo();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setDescripcionPersona(rs.getString("datos_propietario"));
				obj.setDireccion(rs.getString("direccion_completa"));
				obj.setAnioCuota(rs.getInt("anio"));
				obj.setDescripcionConcepto(rs.getString("concepto"));
				obj.setNroCuota(rs.getInt("cuota"));
				obj.setInsoluto(rs.getBigDecimal("insoluto"));
				obj.setDerechoEmision(rs.getBigDecimal("emision"));
				obj.setReajuste(rs.getBigDecimal("reajuste"));
				obj.setInteresSimple(rs.getBigDecimal("interes"));
				obj.setDeudaTotal(rs.getBigDecimal("total"));
				// obj.setNroActo(rs.getString("nro_acto"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public int registrarActo(Integer lote_id) throws Exception {
		int salida = 0;
		try {
			CallableStatement cs = connect().prepareCall(
					"{call dbo.spt_cc_genera_acto(?)}");
			cs.setInt(1, lote_id);
			cs.execute();
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
		}
		return salida;
	}

	public int registrarActoRD(Integer lote_id, Integer personaId,
			Integer periodo) throws Exception {
		int salida = 0;
		try {
			CallableStatement cs = connect().prepareCall(
					"{call dbo.spt_cc_genera_actoRD(?,?,?)}");
			cs.setInt(1, lote_id);
			cs.setInt(2, personaId);
			cs.setInt(3, periodo);
			cs.execute();
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
		}
		return salida;
	}

	public int regeneraActoRD(Integer lote_id, Integer personaId,
			Integer periodo) throws Exception {
		int salida = 0;
		try {
			CallableStatement cs = connect().prepareCall(
					"{call dbo.spt_cc_re_genera_actoRD(?,?,?)}");
			cs.setInt(1, lote_id);
			cs.setInt(2, personaId);
			cs.setInt(3, periodo);
			cs.execute();
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
		}
		return salida;
	}

	public int registrarActoRsSn(Integer lote_id, Integer periodo,
			Integer usuarioId, String terminal) throws Exception {// S/N
		int salida = 0;
		try {
			CallableStatement cs = connect().prepareCall(
					"{call dbo.spt_cc_genera_actoRS(?,?,?,?)}");
			cs.setInt(1, lote_id);
			cs.setInt(2, periodo);
			cs.setInt(3, usuarioId);
			cs.setString(4, terminal);
			cs.execute();
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
		}
		return salida;
	}

	public int registrarActoRsOtros(Integer lote_id, Integer periodo,
			Integer usuarioId, String terminal) throws Exception {
		int salida = 0;
		try {
			CallableStatement cs = connect().prepareCall(
					"{call dbo.spt_cc_genera_actoRS_otros(?,?,?,?)}");
			cs.setInt(1, lote_id);
			cs.setInt(2, periodo);
			cs.setInt(3, usuarioId);
			cs.setString(4, terminal);
			cs.execute();
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
		}
		return salida;
	}

	public int registrarActoRsNoPecuniariasUbicables(Integer lote_id,
			Integer periodo, Integer usuarioId, String terminal)
			throws Exception {
		int salida = 0;
		try {
			CallableStatement cs = connect()
					.prepareCall(
							"{call dbo.spt_cc_genera_rs_nopecuniaria_ubicables(?,?,?,?)}");
			cs.setInt(1, lote_id);
			cs.setInt(2, periodo);
			cs.setInt(3, usuarioId);
			cs.setString(4, terminal);
			cs.execute();
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
		}
		return salida;
	}

	public int registrarActoRsNoPecuniariasSinNumero(Integer lote_id,
			Integer periodo, Integer usuarioId, String terminal)
			throws Exception {
		int salida = 0;
		try {
			CallableStatement cs = connect()
					.prepareCall(
							"{call dbo.spt_cc_genera_rs_nopecuniaria_sinnumero(?,?,?,?)}");
			cs.setInt(1, lote_id);
			cs.setInt(2, periodo);
			cs.setInt(3, usuarioId);
			cs.setString(4, terminal);
			cs.execute();
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
		}
		return salida;
	}

	public int registrarActoRsNoPecuniariasOtros(Integer lote_id,
			Integer periodo, Integer usuarioId, String terminal)
			throws Exception {
		int salida = 0;
		try {
			CallableStatement cs = connect().prepareCall(
					"{call dbo.spt_cc_genera_rs_nopecuniaria_otros(?,?,?,?)}");
			cs.setInt(1, lote_id);
			cs.setInt(2, periodo);
			cs.setInt(3, usuarioId);
			cs.setString(4, terminal);
			cs.execute();
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
		}
		return salida;
	}

	public int registrarActoRsUbicables(Integer lote_id, Integer periodo,
			Integer usuarioId, String terminal) throws Exception {
		int salida = 0;
		try {
			CallableStatement cs = connect().prepareCall(
					"{call dbo.spt_cc_genera_actoRS_ubicables(?,?,?,?)}");
			cs.setInt(1, lote_id);
			cs.setInt(2, periodo);
			cs.setInt(3, usuarioId);
			cs.setString(4, terminal);
			cs.execute();
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
		}
		return salida;
	}

	public int registrarActoOP(Integer lote_id, Integer anio,
			Integer conceptoId, String cuotas, Integer usuarioId,
			Integer personaId) throws Exception {
		int salida = 0;
		try {
			CallableStatement cs = connect().prepareCall(
					"{call dbo.spt_cc_genera_actoOP(?,?,?,?,?,?)}");
			cs.setInt(1, lote_id);
			cs.setInt(2, anio);
			cs.setInt(3, conceptoId);
			cs.setString(4, cuotas);
			cs.setInt(5, usuarioId);
			cs.setInt(6, personaId);
			cs.execute();
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
		}
		return salida;
	}

	public int registrarActoOPInubicalbesSN(Integer lote_id, Integer anio,
			Integer conceptoId, String cuotas, Integer usuarioId,
			Integer personaId) throws Exception {
		int salida = 0;
		try {
			CallableStatement cs = connect()
					.prepareCall(
							"{call dbo.spt_cc_genera_actoOP_Inubicables_sn(?,?,?,?,?,?)}");
			cs.setInt(1, lote_id);
			cs.setInt(2, anio);
			cs.setInt(3, conceptoId);
			cs.setString(4, cuotas);
			cs.setInt(5, usuarioId);
			cs.setInt(6, personaId);
			cs.execute();
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
		}
		return salida;
	}

	public int registrarActoOpUbicablesPredial(Integer lote_id, Integer anio,
			Integer conceptoId, String cuotas, Integer usuarioId,
			Integer personaId) throws Exception {
		int salida = 0;
		try {
			CallableStatement cs = connect().prepareCall(
					"{call dbo.spt_cc_genera_actoOP_predial(?,?,?,?,?,?)}");
			cs.setInt(1, lote_id);
			cs.setInt(2, anio);
			cs.setInt(3, conceptoId);
			cs.setString(4, cuotas);
			cs.setInt(5, usuarioId);
			cs.setInt(6, personaId);
			cs.execute();
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
		}
		return salida;
	}

	public int registrarActoOpSnPredial(Integer lote_id, Integer anio,
			Integer conceptoId, String cuotas, Integer usuarioId,
			Integer personaId) throws Exception {
		int salida = 0;
		try {
			CallableStatement cs = connect().prepareCall(
					"{call dbo.spt_cc_genera_actoOP_predial_SN(?,?,?,?,?,?)}");
			cs.setInt(1, lote_id);
			cs.setInt(2, anio);
			cs.setInt(3, conceptoId);
			cs.setString(4, cuotas);
			cs.setInt(5, usuarioId);
			cs.setInt(6, personaId);
			cs.execute();
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
		}
		return salida;
	}

	public int registrarActoOpOtrosPredial(Integer lote_id, Integer anio,
			Integer conceptoId, String cuotas, Integer usuarioId,
			Integer personaId) throws Exception {
		int salida = 0;
		try {
			CallableStatement cs = connect()
					.prepareCall(
							"{call dbo.spt_cc_genera_actoOP_predial_otros(?,?,?,?,?,?)}");
			cs.setInt(1, lote_id);
			cs.setInt(2, anio);
			cs.setInt(3, conceptoId);
			cs.setString(4, cuotas);
			cs.setInt(5, usuarioId);
			cs.setInt(6, personaId);
			cs.execute();
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
		}
		return salida;
	}

	public int registrarActoOPInubicablesOtros(Integer lote_id, Integer anio,
			Integer conceptoId, String cuotas, Integer usuarioId,
			Integer personaId) throws Exception {
		int salida = 0;
		try {
			CallableStatement cs = connect()
					.prepareCall(
							"{call dbo.spt_cc_genera_actoOP_Inubicables_otros(?,?,?,?,?,?)}");
			cs.setInt(1, lote_id);
			cs.setInt(2, anio);
			cs.setInt(3, conceptoId);
			cs.setString(4, cuotas);
			cs.setInt(5, usuarioId);
			cs.setInt(6, personaId);
			cs.execute();
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
		}
		return salida;
	}

	public List<FindCcLoteDetalleActo> getAllFindCcLoteFinal(Integer lote_id,
			Integer TipoActoId) throws Exception {
		List<FindCcLoteDetalleActo> list = new ArrayList<FindCcLoteDetalleActo>();
		try {
			StringBuffer SQL = new StringBuffer();

			SQL.append(" select p.persona_id,p.nro_docu_identidad,p.apellidos_nombres,pd.domicilio_completo,t.descripcion sector, a.nro_acto,a.anno_acto, ");
			SQL.append(" sum(de.insoluto) insoluto,sum(de.reajuste) reajuste,sum(de.derecho_emision) derecho_emision,sum(de.interes_capitalizado) interes_capitalizado,sum(de.interes_mensual) interes_simp,sum(de.total_deuda) total_deuda ");
			SQL.append(" from  ");
			SQL.append(" dbo.cc_acto_deuda de ");
			SQL.append(" inner join dbo.cc_acto a on (de.persona_id=a.persona_id) ");
			SQL.append(" inner join dbo.mp_persona p on (a.persona_id=p.persona_id)  ");
			SQL.append(" inner join dbo.mp_persona_domicilio pd on (pd.persona_id=p.persona_id and pd.estado='1') ");
			SQL.append(" inner join dbo.mp_direccion d on (d.persona_id=p.persona_id and pd.direccion_id=pd.direccion_id and d.estado='1') ");
			SQL.append(" inner join dbo.gn_ubicacion u on (u.ubicacion_id=d.ubicacion_id)  ");
			SQL.append(" inner join dbo.gn_sector_lugar s on (s.sector_lugar_id=u.sector_lugar_id) ");
			SQL.append(" inner join dbo.gn_sector t on (t.sector_id=s.sector_id)  ");
			SQL.append(" where a.lote_id=?");
			SQL.append(" group by p.persona_id,p.nro_docu_identidad,p.apellidos_nombres,pd.domicilio_completo,t.descripcion,a.nro_acto,a.anno_acto ");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, lote_id);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FindCcLoteDetalleActo obj = new FindCcLoteDetalleActo();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setNroDocumento(rs.getString("nro_docu_identidad"));
				obj.setDescripcionPersona(rs.getString("apellidos_nombres"));
				obj.setDeudaTotal(rs.getBigDecimal("total_deuda"));
				obj.setDireccion(rs.getString("domicilio_completo"));
				obj.setSector(rs.getString("sector"));
				obj.setInsoluto(rs.getBigDecimal("insoluto"));
				obj.setReajuste(rs.getBigDecimal("reajuste"));
				obj.setDerechoEmision(rs.getBigDecimal("derecho_emision"));
				obj.setInteresCapitalizado(rs
						.getBigDecimal("interes_capitalizado"));
				obj.setInteresSimple(rs.getBigDecimal("interes_simp"));
				obj.setNroActo(rs.getString("nro_acto"));
				obj.setAnnoLote(rs.getInt("anno_acto"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public List<FindCcLoteDetalleActo> getAllFindCcLoteFinalRD(Integer lote_id)
			throws Exception {
		List<FindCcLoteDetalleActo> list = new ArrayList<FindCcLoteDetalleActo>();
		try {
			StringBuffer SQL = new StringBuffer();

			SQL.append(" select p.persona_id,p.nro_docu_identidad,p.apellidos_nombres,pd.domicilio_completo,t.descripcion sector, a.nro_acto,a.anno_acto, ");
			SQL.append(" sum(de.insoluto) insoluto,sum(de.reajuste) reajuste,sum(de.derecho_emision) derecho_emision,sum(de.interes_capitalizado) interes_capitalizado,sum(de.interes_mensual) interes_simp,sum(de.total_deuda) total_deuda, ");
			SQL.append(" sum(tf.base_imponible) base_imponible_fisca,sum(tf.base_afecta) base_afecta_fisca,sum(tf.impuesto) impuesto_fisca,  ");
			SQL.append(" sum(isnull(tt.base_imponible,0)) base_imponible,sum(isnull(tt.base_afecta,0)) base_afecta,sum(isnull(tt.impuesto,0)) impuesto  ");
			SQL.append(" from    ");
			SQL.append(" dbo.cc_acto_deuda de ");
			SQL.append(" inner join dbo.cc_acto a on (de.persona_id=a.persona_id and de.lote_id=a.lote_id) ");
			SQL.append(" inner join dbo.mp_persona p on (a.persona_id=p.persona_id)    ");
			SQL.append(" inner join dbo.mp_persona_domicilio pd on (pd.persona_id=p.persona_id and pd.estado='1') ");
			SQL.append(" inner join dbo.mp_direccion d on (d.persona_id=p.persona_id and pd.direccion_id=pd.direccion_id and d.estado='1') ");
			SQL.append(" inner join dbo.gn_ubicacion u on (u.ubicacion_id=d.ubicacion_id)    ");
			SQL.append(" inner join dbo.gn_sector_lugar s on (s.sector_lugar_id=u.sector_lugar_id) ");
			SQL.append(" inner join dbo.gn_sector t on (t.sector_id=s.sector_id)    ");
			SQL.append(" inner join dbo.dt_determinacion tf on (tf.persona_id=p.persona_id and tf.anno_determinacion=a.anno_acto and tf.estado='1' and tf.fiscalizado='1' and tf.fisca_aceptada='0' and tf.fisca_cerrada='1' and tf.concepto_id=1)  ");
			SQL.append(" left join dbo.dt_determinacion tt on (tt.persona_id=p.persona_id and tt.anno_determinacion=a.anno_acto and tt.estado='1' and tt.fiscalizado='0' and tt.concepto_id=1)  ");
			SQL.append(" where a.lote_id=? ");
			SQL.append(" group by p.persona_id,p.nro_docu_identidad,p.apellidos_nombres,pd.domicilio_completo,t.descripcion,a.nro_acto,a.anno_acto    ");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, lote_id);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FindCcLoteDetalleActo obj = new FindCcLoteDetalleActo();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setNroDocumento(rs.getString("nro_docu_identidad"));
				obj.setDescripcionPersona(rs.getString("apellidos_nombres"));
				obj.setDeudaTotal(rs.getBigDecimal("total_deuda"));
				obj.setDireccion(rs.getString("domicilio_completo"));
				obj.setSector(rs.getString("sector"));
				obj.setInsoluto(rs.getBigDecimal("insoluto"));
				obj.setReajuste(rs.getBigDecimal("reajuste"));
				obj.setDerechoEmision(rs.getBigDecimal("derecho_emision"));
				obj.setInteresCapitalizado(rs
						.getBigDecimal("interes_capitalizado"));
				obj.setInteresSimple(rs.getBigDecimal("interes_simp"));
				obj.setNroActo(rs.getString("nro_acto"));
				obj.setAnnoLote(rs.getInt("anno_acto"));

				obj.setBaseImponible(rs.getBigDecimal("base_imponible"));
				obj.setBaseAfecta(rs.getBigDecimal("base_afecta"));
				obj.setImpuesto(rs.getBigDecimal("impuesto"));

				obj.setBaseImponibleFisca(rs
						.getBigDecimal("base_imponible_fisca"));
				obj.setBaseAfectaFisca(rs.getBigDecimal("base_afecta_fisca"));
				obj.setImpuestoFisca(rs.getBigDecimal("impuesto_fisca"));

				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public List<FindCcLoteDetalleActo> getAllFindCcLoteFinalRS(Integer lote_id)
			throws Exception {
		List<FindCcLoteDetalleActo> list = new ArrayList<FindCcLoteDetalleActo>();
		try {
			String SQL = "sp_final_lote_rs ?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, lote_id);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FindCcLoteDetalleActo obj = new FindCcLoteDetalleActo();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setNroDocumento(rs.getString("nro_doc_identidad"));
				obj.setDescripcionPersona(rs.getString("apellidos_nombres"));
				obj.setDireccion(rs.getString("direccion_completa"));
				obj.setPlaca(rs.getString("placa"));
				obj.setNroPapeleta(rs.getString("nro_papeleta"));
				obj.setMontoMulta(rs.getBigDecimal("monto_deuda"));
				obj.setNroActo(rs.getString("nro_acto"));
				obj.setEstadoDeuda(rs.getString("estado_deuda"));
				obj.setDeudaPendiente(rs.getBigDecimal("total_pendiente_pago"));
				obj.setFechaUltimoPago(rs.getTimestamp("fecha_ultimo_pago"));
				obj.setTipoMulta(rs.getString("tipo_multa"));
				obj.setFechaNotificacion(rs.getTimestamp("fecha_notificacion"));
				obj.setTipoNotificacion(rs.getString("motivo_notificacion"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public List<FindCcLoteDetalleActo> getAllFindCcLoteFinalOP(Integer lote_id)
			throws Exception {
		List<FindCcLoteDetalleActo> list = new ArrayList<FindCcLoteDetalleActo>();
		try {
			String SQL = "sp_final_lote_OP ?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, lote_id);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				FindCcLoteDetalleActo obj = new FindCcLoteDetalleActo();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setDescripcionPersona(rs.getString("datos_contribuyente"));
				obj.setDireccion(rs.getString("direccion_completa"));
				obj.setAnioCuota(rs.getInt("anno_deuda"));
				obj.setDescripcionConcepto(rs.getString("descripcion_corta"));
				obj.setNroCuota(rs.getInt("nro_cuota"));
				obj.setDeudaTotal(rs.getBigDecimal("total_deuda"));
				obj.setInsoluto(rs.getBigDecimal("insoluto"));
				obj.setDerechoEmision(rs.getBigDecimal("derecho_emision"));
				obj.setReajuste(rs.getBigDecimal("reajuste"));
				obj.setInteresSimple(rs.getBigDecimal("interes_mensual"));
				obj.setNroActo(rs.getString("nro_acto"));
				obj.setSector(rs.getString("sector"));
				
				list.add(obj);
				
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public List<FindCcCuotasAgrupadasLote> getAllFindCuotasAgrupadasxLote(
			Integer lote_id) throws Exception {
		List<FindCcCuotasAgrupadasLote> list = new ArrayList<FindCcCuotasAgrupadasLote>();
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append(" SELECT distinct c.anno_cuota,c.lote_id, STUFF((Select ','+convert(varchar(100),cuota)");
			SQL.append("  from dbo.cc_lote_cuota T1 where T1.lote_id=? ");
			SQL.append("  FOR XML PATH('')),1,1,'') as cuotas FROM dbo.cc_lote_cuota c where c.lote_id=?");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, lote_id);
			pst.setInt(2, lote_id);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				FindCcCuotasAgrupadasLote obj = new FindCcCuotasAgrupadasLote();
				obj.setLoteId(rs.getInt("lote_id"));
				obj.setAnioDeuda(rs.getInt("anno_cuota"));
				obj.setCuotas(rs.getString("cuotas"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public int actualizaEstadoLote(Integer loteId, String estado)
			throws Exception {
		int result = 0;
		try {
			StringBuffer SQL = new StringBuffer(
					" UPDATE "
							+ SATParameterFactory.getDBNameScheme()
							+ ".cc_lote_sector SET estado = ?  where lote_id = ? and estado='1' ");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setString(1, estado);
			pst.setInt(2, loteId);
			result = pst.executeUpdate();

			SQL = new StringBuffer(
					" UPDATE "
							+ SATParameterFactory.getDBNameScheme()
							+ ".cc_lote_tipo_persona SET estado = ?  where lote_id = ? and estado='1' ");
			pst = connect().prepareStatement(SQL.toString());
			pst.setString(1, estado);
			pst.setInt(2, loteId);
			result = pst.executeUpdate();

			SQL = new StringBuffer(
					" UPDATE "
							+ SATParameterFactory.getDBNameScheme()
							+ ".cc_lote_concepto SET estado = ?  where lote_id = ? and estado='1' ");
			pst = connect().prepareStatement(SQL.toString());
			pst.setString(1, estado);
			pst.setInt(2, loteId);
			result = pst.executeUpdate();

			SQL = new StringBuffer(
					" UPDATE "
							+ SATParameterFactory.getDBNameScheme()
							+ ".cc_lote_cuota SET estado = ?  where lote_id = ? and estado='1' ");
			pst = connect().prepareStatement(SQL.toString());
			pst.setString(1, estado);
			pst.setInt(2, loteId);
			result = pst.executeUpdate();

		} catch (Exception e) {
			throw (e);
		}
		return result;
	}

	public List<FindCcLoteSector> getAllFindCcLoteSector(Integer lote_id)
			throws Exception {
		List<FindCcLoteSector> list = new ArrayList<FindCcLoteSector>();
		try {
			String sql = " select ls.lote_id,ls.lote_sector_id,s.sector_id,s.descripcion sector from "
					+ SATParameterFactory.getDBNameScheme()
					+ ".Cc_lote_sector ls "
					+ " inner join "
					+ SATParameterFactory.getDBNameScheme()
					+ ".gn_sector s on s.sector_id=ls.sector_id "
					+ " where  ls.estado =? and ls.lote_id=?   ";
			PreparedStatement pst = connect().prepareStatement(sql);
			pst.setString(1, Constante.ESTADO_ACTIVO);
			pst.setInt(2, lote_id);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FindCcLoteSector obj = new FindCcLoteSector();
				obj.setLoteId(rs.getInt("lote_id"));
				obj.setLoteSectorId(rs.getInt("lote_sector_id"));
				obj.setSectorId(rs.getInt("sector_id"));
				obj.setSector(rs.getString("sector"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public List<FindCcLoteTipoPersona> getAllFindCcLoteTipoPersona(
			Integer lote_id) throws Exception {
		List<FindCcLoteTipoPersona> list = new ArrayList<FindCcLoteTipoPersona>();
		try {
			String sql = " select ltp.lote_id,ltp.lote_tipo_persona_id,ltp.tipo_persona_id,tp.descripcion tipo_persona from "
					+ SATParameterFactory.getDBNameScheme()
					+ ".cc_lote_tipo_persona ltp "
					+ " inner join "
					+ SATParameterFactory.getDBNameScheme()
					+ ".mp_tipo_persona tp on tp.tipo_persona_id=ltp.tipo_persona_id "
					+ " where  ltp.estado =? and ltp.lote_id=?   ";
			PreparedStatement pst = connect().prepareStatement(sql);
			pst.setString(1, Constante.ESTADO_ACTIVO);
			pst.setInt(2, lote_id);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FindCcLoteTipoPersona obj = new FindCcLoteTipoPersona();
				obj.setLoteId(rs.getInt("lote_id"));
				obj.setLoteTipoPersonaId(rs.getInt("lote_tipo_persona_id"));
				obj.setTipoPersonaId(rs.getInt("tipo_persona_id"));
				obj.setTipoPersona(rs.getString("tipo_persona"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public List<CcLoteCuota> getAllFindCcLoteCuota(Integer lote_id)
			throws Exception {
		Query q = null;
		List<CcLoteCuota> result = null;
		try {
			StringBuffer SQL = new StringBuffer(" select * from ").append(
					Constante.schemadb).append(
					".cc_lote_cuota l where l.lote_id=?1 and l.estado =?2 ");
			q = em.createNativeQuery(SQL.toString(), CcLoteCuota.class);
			q.setParameter(1, lote_id);
			q.setParameter(2, Constante.ESTADO_ACTIVO);
			result = q.getResultList();
			return result;
		} catch (Exception e) {
			q = null;
			return result;
		}
	}

	public List<FindCcLoteConcepto> getAllFindCcLoteConcepto(Integer lote_id)
			throws Exception {
		Query q = null;
		List<FindCcLoteConcepto> list = new ArrayList<FindCcLoteConcepto>();
		try {
			StringBuffer SQL = new StringBuffer(
					" select lc.lote_id,lc.lote_acto_id,lc.concepto_id,lc.subconcepto_id,c.descripcion concepto,sc.descripcion subconcepto ");
			SQL.append(" from dbo.cc_lote_concepto lc  ");
			SQL.append(" inner join dbo.gn_concepto c on c.concepto_id= lc.concepto_id ");
			SQL.append(" inner join dbo.gn_subconcepto sc on sc.subconcepto_id=lc.subconcepto_id ");
			SQL.append(" where  lc.lote_id=?1 and lc.estado='1' ");

			q = em.createNativeQuery(SQL.toString());
			q.setParameter(1, lote_id);
			List<Object[]> lista1 = q.getResultList();
			for (Object[] o : lista1) {
				FindCcLoteConcepto obj = new FindCcLoteConcepto();
				obj.setLoteId((Integer) o[0]);
				obj.setLoteActoId((Integer) o[1]);
				obj.setConceptoId((Integer) o[2]);
				obj.setSubconceptoId((Integer) o[3]);
				obj.setConcepto((String) o[4]);
				obj.setSubconcepto((String) o[5]);
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public FindPersona getSectorIdByPersonaId(Integer personaId)
			throws Exception {
		FindPersona persona = null;
		try {
			StringBuffer sqld = new StringBuffer();
			sqld.append(" select p.persona_id,t.sector_id,p.tipo_persona_id ");
			sqld.append(" from  dbo.mp_persona p  ");
			sqld.append(" inner join dbo.mp_persona_domicilio pd on (pd.persona_id=p.persona_id and pd.estado='1') ");
			sqld.append(" inner join dbo.mp_direccion d on (d.persona_id=p.persona_id and pd.direccion_id=pd.direccion_id and d.estado='1') ");
			sqld.append(" inner join dbo.gn_ubicacion u on (u.ubicacion_id=d.ubicacion_id) ");
			sqld.append(" inner join dbo.gn_sector_lugar s on (s.sector_lugar_id=u.sector_lugar_id) ");
			sqld.append(" inner join dbo.gn_sector t on (t.sector_id=s.sector_id) ");
			sqld.append(" where p.persona_id=?");

			PreparedStatement pst = connect().prepareStatement(sqld.toString());
			pst.setInt(1, personaId);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				persona = new FindPersona();
				persona.setPersonaId(rs.getInt("persona_id"));
				persona.setSectorId(rs.getInt("sector_id"));
				persona.setTipoPersonaId(rs.getInt("tipo_persona_id"));
			}
		} catch (Exception e) {
			throw (e);
		}
		return persona;
	}

	public FindPersona getSectorIdByPersonaInfractorId() throws Exception {
		FindPersona persona = null;
		try {
			StringBuffer sqld = new StringBuffer();
			sqld.append(" select p.persona_id,t.sector_id,p.tipo_persona_id ");
			sqld.append(" from  dbo.mp_persona p  ");
			sqld.append(" inner join dbo.mp_persona_domicilio pd on (pd.persona_id=p.persona_id and pd.estado='1') ");
			sqld.append(" inner join dbo.mp_direccion d on (d.persona_id=p.persona_id and pd.direccion_id=pd.direccion_id and d.estado='1') ");
			sqld.append(" inner join dbo.gn_ubicacion u on (u.ubicacion_id=d.ubicacion_id) ");
			sqld.append(" inner join dbo.gn_sector_lugar s on (s.sector_lugar_id=u.sector_lugar_id) ");
			sqld.append(" inner join dbo.gn_sector t on (t.sector_id=s.sector_id) ");
			sqld.append(" where p.persona_id=?");

			PreparedStatement pst = connect().prepareStatement(sqld.toString());
			// pst.setInt(1, personaId);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				persona = new FindPersona();
				persona.setPersonaId(rs.getInt("persona_id"));
				persona.setSectorId(rs.getInt("sector_id"));
				persona.setTipoPersonaId(rs.getInt("tipo_persona_id"));
			}
		} catch (Exception e) {
			throw (e);
		}
		return persona;
	}

	public List<CcTipoLote> getAllCcTipoLote() throws Exception {
		Query q = null;
		List<CcTipoLote> result = null;
		try {
			StringBuffer sql = new StringBuffer("select * FROM ").append(
					Constante.schemadb).append(
					".cc_tipo_lote where estado =?1 ");
			q = em.createNativeQuery(sql.toString(), CcTipoLote.class);
			q.setParameter(1, Constante.ESTADO_ACTIVO);
			result = q.getResultList();
			return result;
		} catch (Exception e) {
			q = null;
			return result;
		}
	}

	public List<CcTipoActo> getAllCcTipoActo(Integer cctipolote_id)
			throws Exception {
		Query q = null;
		List<CcTipoActo> result = null;
		try {
			StringBuffer sql = new StringBuffer(" select * FROM ").append(
					Constante.schemadb).append(
					".cc_tipo_acto where estado=?1 and tipo_lote_id=?2 ");
			q = em.createNativeQuery(sql.toString(), CcTipoActo.class);
			q.setParameter(1, Constante.ESTADO_ACTIVO);
			q.setParameter(2, cctipolote_id);
			result = q.getResultList();
			return result;
		} catch (Exception e) {
			q = null;
			return result;
		}
	}

	public List<GnConcepto> getAllGnConcepto(Integer cctipoacto_id)
			throws Exception {
		Query q = null;
		List<GnConcepto> result = null;
		try {
			StringBuffer sql = new StringBuffer("  select gn.* from ").append(
					Constante.schemadb).append(".cc_tipo_acto_concepto tac ");
			sql.append(" inner join ")
					.append(Constante.schemadb)
					.append(".cc_tipo_acto ta on tac.tipo_acto_id=ta.tipo_acto_id ");
			sql.append(" inner join ")
					.append(Constante.schemadb)
					.append(".gn_concepto gn on tac.concepto_id=gn.concepto_id ");
			sql.append(" where gn.estado=?1 and ta.tipo_acto_id=?2 ");
			q = em.createNativeQuery(sql.toString(), GnConcepto.class);
			q.setParameter(1, Constante.ESTADO_ACTIVO);
			q.setParameter(2, cctipoacto_id);
			result = q.getResultList();
			return result;
		} catch (Exception e) {
			q = null;
			return result;
		}
	}

	public List<CcFirmante> getAllCcFirmantes(Integer lote_id) throws Exception {
		Query q = null;
		List<CcFirmante> result = null;
		try {
			StringBuffer SQL = new StringBuffer(" select f.* from ").append(
					Constante.schemadb).append(".cc_lote_firma lf ");
			SQL.append(" inner join cc_firmantes f on lf.firmante_id=f.firmante_id ");
			SQL.append(" where lf.lote_id=?1 ");
			q = em.createNativeQuery(SQL.toString(), CcFirmante.class);
			q.setParameter(1, lote_id);
			result = q.getResultList();
			return result;
		} catch (Exception e) {
			q = null;
			return result;
		}
	}

	public List<GnSubconcepto> getAllGnSubConcepto(Integer gnconcepto_id)
			throws Exception {
		Query q = null;
		List<GnSubconcepto> result = null;
		try {
			StringBuffer SQL = new StringBuffer("select * from ").append(
					Constante.schemadb).append(
					".gn_subconcepto where estado=?1 and concepto_id=?2 ");
			q = em.createNativeQuery(SQL.toString(), GnSubconcepto.class);
			q.setParameter(1, Constante.ESTADO_ACTIVO);
			q.setParameter(2, gnconcepto_id);
			result = q.getResultList();
			return result;
		} catch (Exception e) {
			q = null;
			return result;
		}
	}

	public List<GnSector> getAllGnSector() throws Exception {
		Query q = null;
		List<GnSector> result = null;
		try {
			StringBuffer SQL = new StringBuffer(" select * FROM ").append(
					Constante.schemadb).append(".gn_sector where estado =?1 ");
			q = em.createNativeQuery(SQL.toString(), GnSector.class);
			q.setParameter(1, Constante.ESTADO_ACTIVO);
			result = q.getResultList();
			return result;
		} catch (Exception e) {
			q = null;
			return result;
		}
	}

	public List<FindCcLote> getAllFindCcLote(String nroLote,
			String fechaRegistro, String estadolote, Integer tipoCobranza)
			throws Exception {
		List<FindCcLote> list = new ArrayList<FindCcLote>();
		StringBuffer sqld = new StringBuffer("");
		try {
			sqld.append(" select l.lote_id,l.tipo_lote_id,l.anno_lote,l.fecha_registro,CONVERT(time(0),l.fecha_registro) hora_registro, ");
			sqld.append(" tl.descripcion tipo_lote,ta.descripcion tipo_acto,ta.tipo_acto_id,a.agrupado_bien,a.agrupado_acto,a.fecha_vencimiento, a.agrupado_cuota, ");
			sqld.append(" case when l.tipo_lote_generacion='1' then 'Preliminar'  when l.tipo_lote_generacion='2' then 'Final' end estado_tipo_lote_generacion, ");
			sqld.append(" case when l.flag_generacion='1' then 'Programado'  when l.flag_generacion='2' then 'Finalizado' end estado_generacion, ");
			sqld.append(" l.tipo_lote_generacion,l.flag_generacion, ");
			sqld.append(" lc.concepto_id,c.descripcion concepto, ");
			sqld.append(" r.monto_deuda,r.total_registros ");
			sqld.append(" FROM dbo.cc_lote l   ");
			sqld.append(" inner join dbo.cc_tipo_lote tl on (tl.tipo_lote_id=l.tipo_lote_id  ) ");
			sqld.append(" inner join dbo.cc_lote_acto a on (a.lote_id=l.lote_id  and a.estado='1') ");
			sqld.append(" inner join dbo.cc_tipo_acto ta on ta.tipo_acto_id=a.tipo_acto_id   ");
			sqld.append(" inner join dbo.cc_lote_concepto lc on l.lote_id=lc.lote_id and a.lote_acto_id=lc.lote_acto_id ");
			sqld.append(" inner join dbo.gn_concepto c on c.concepto_id=lc.concepto_id ");
			sqld.append(" left join (select a.lote_id,sum(a.monto_deuda) monto_deuda,count(1) total_registros from dbo.cc_acto a where a.estado=1 group by a.lote_id) r on (r.lote_id=l.lote_id) ");
			sqld.append(" where l.estado='1'  ");
			sqld.append(" order by l.lote_id desc");

			PreparedStatement pst = connect().prepareStatement(sqld.toString());
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
				obj.setAgrupacionBien(rs.getString("agrupado_bien"));
				obj.setAgrupacionActo(rs.getString("agrupado_acto"));
				obj.setFechaVencimiento(rs.getTimestamp("fecha_vencimiento"));
				obj.setAgrupacionCuota(rs.getString("agrupado_cuota"));
				obj.setEstadoTipoLoteGeneracion(rs
						.getString("estado_tipo_lote_generacion"));
				obj.setEstadoGeneracion(rs.getString("estado_generacion"));
				obj.setTipo_lote_generacion(rs
						.getString("tipo_lote_generacion"));
				obj.setFlag_generacion(rs.getString("flag_generacion"));
				obj.setConceptoId(rs.getInt("concepto_id"));
				obj.setConcepto(rs.getString("concepto"));
				obj.setTotalDeuda(rs.getBigDecimal("monto_deuda"));
				obj.setNroDocumentos(rs.getInt("total_registros"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public List<FindCcLote> getAllFindCcLoteRS(String nroLote,
			String fechaRegistro, String estadolote, Integer tipoCobranza)
			throws Exception {
		List<FindCcLote> list = new ArrayList<FindCcLote>();
		StringBuffer sqld = new StringBuffer("");
		try {
			sqld.append(" select l.lote_id,l.tipo_lote_id,l.anno_lote,l.fecha_registro,CONVERT(time(0),l.fecha_registro) hora_registro, ");
			sqld.append(" tl.descripcion tipo_lote,ta.descripcion tipo_acto,ta.tipo_acto_id,a.agrupado_bien,a.agrupado_acto,a.fecha_vencimiento, a.agrupado_cuota, ");
			sqld.append(" case when l.tipo_lote_generacion='1' then 'Preliminar'  when l.tipo_lote_generacion='2' then 'Final' end estado_tipo_lote_generacion, ");
			sqld.append(" case when l.flag_generacion='1' then 'Programado'  when l.flag_generacion='2' then 'Finalizado' end estado_generacion, ");
			sqld.append(" l.tipo_lote_generacion,l.flag_generacion, ");
			sqld.append(" lc.concepto_id,c.descripcion concepto, ");
			sqld.append(" r.monto_deuda,r.total_registros, ");
			sqld.append(" case when l.flag_ubicables=2 then 'Ubic.' when l.flag_ubicables=1 then 'Otros' when isnull(l.flag_ubicables,0)=0 then 'S/N'	end flag_ubicables,l.usuario_id  ");
			sqld.append(" FROM dbo.cc_lote l   ");
			sqld.append(" inner join dbo.cc_tipo_lote tl on (tl.tipo_lote_id=l.tipo_lote_id  ) ");
			sqld.append(" inner join dbo.cc_lote_acto a on (a.lote_id=l.lote_id  and a.estado='1') ");
			sqld.append(" inner join dbo.cc_tipo_acto ta on ta.tipo_acto_id=a.tipo_acto_id   ");
			sqld.append(" inner join dbo.cc_lote_concepto lc on l.lote_id=lc.lote_id and a.lote_acto_id=lc.lote_acto_id ");
			sqld.append(" inner join dbo.gn_concepto c on c.concepto_id=lc.concepto_id ");
			sqld.append(" left join (select a.lote_id,sum(a.monto_deuda) monto_deuda,count(1) total_registros from dbo.cc_acto a where a.estado=1 group by a.lote_id) r on (r.lote_id=l.lote_id) ");
			sqld.append(" where l.estado='1' and ta.tipo_acto_id=8");
			sqld.append(" order by l.lote_id desc");

			PreparedStatement pst = connect().prepareStatement(sqld.toString());
			// pst.setString(1, nroLote);
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
				obj.setAgrupacionBien(rs.getString("agrupado_bien"));
				obj.setAgrupacionActo(rs.getString("agrupado_acto"));
				obj.setFechaVencimiento(rs.getTimestamp("fecha_vencimiento"));
				obj.setAgrupacionCuota(rs.getString("agrupado_cuota"));
				obj.setEstadoTipoLoteGeneracion(rs
						.getString("estado_tipo_lote_generacion"));
				obj.setEstadoGeneracion(rs.getString("estado_generacion"));
				obj.setTipo_lote_generacion(rs
						.getString("tipo_lote_generacion"));
				obj.setFlag_generacion(rs.getString("flag_generacion"));
				obj.setConceptoId(rs.getInt("concepto_id"));
				obj.setConcepto(rs.getString("concepto"));
				obj.setTotalDeuda(rs.getBigDecimal("monto_deuda"));
				obj.setNroDocumentos(rs.getInt("total_registros"));
				obj.setFlagUbicables(rs.getString("flag_ubicables"));
				obj.setUsuarioId(rs.getInt("usuario_id"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public List<FindCcLote> getAllFindCcLoteRsNoPecuniaria(String nroLote)
			throws Exception {
		List<FindCcLote> list = new ArrayList<FindCcLote>();
		try {
			String SQL = "sp_loteRS_NoPecuniaria ?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setString(1, nroLote);

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
				obj.setAgrupacionBien(rs.getString("agrupado_bien"));
				obj.setAgrupacionActo(rs.getString("agrupado_acto"));
				obj.setFechaVencimiento(rs.getTimestamp("fecha_vencimiento"));
				obj.setAgrupacionCuota(rs.getString("agrupado_cuota"));
				obj.setEstadoTipoLoteGeneracion(rs
						.getString("estado_tipo_lote_generacion"));
				obj.setEstadoGeneracion(rs.getString("estado_generacion"));
				obj.setTipo_lote_generacion(rs
						.getString("tipo_lote_generacion"));
				obj.setFlag_generacion(rs.getString("flag_generacion"));
				obj.setConceptoId(rs.getInt("concepto_id"));
				obj.setConcepto(rs.getString("concepto"));
				obj.setTotalDeuda(rs.getBigDecimal("monto_deuda"));
				obj.setNroDocumentos(rs.getInt("total_registros"));
				obj.setFlagUbicables(rs.getString("flag_ubicables"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public List<FindCcLoteDetalleActo> getAllFindPreliminarRsNoPecuniariaUbic()
			throws Exception {
		List<FindCcLoteDetalleActo> list = new ArrayList<FindCcLoteDetalleActo>();
		try {
			String SQL = "sp_preliminar_rs_noPecuniaria_ubic";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FindCcLoteDetalleActo obj = new FindCcLoteDetalleActo();
				obj.setPersonaId(rs.getInt("cod_infractor"));
				obj.setNroDocumento(rs.getString("doc_infractor"));
				obj.setDescripcionPersona(rs.getString("datos_infractor"));
				obj.setDireccion(rs.getString("direccion_infractor"));
				obj.setPlaca(rs.getString("placa"));
				obj.setNroPapeleta(rs.getString("nro_papeleta"));
				obj.setMontoMulta(rs.getBigDecimal("monto_multa"));

				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public List<FindCcLoteDetalleActo> getAllPreliminarRsNoPecSn()
			throws Exception {
		List<FindCcLoteDetalleActo> list = new ArrayList<FindCcLoteDetalleActo>();
		try {
			String SQL = "sp_preliminar_rs_noPecuniaria_sn";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FindCcLoteDetalleActo obj = new FindCcLoteDetalleActo();
				obj.setPersonaId(rs.getInt("cod_infractor"));
				obj.setNroDocumento(rs.getString("doc_infractor"));
				obj.setDescripcionPersona(rs.getString("datos_infractor"));
				obj.setDireccion(rs.getString("direccion_infractor"));
				obj.setPlaca(rs.getString("placa"));
				obj.setNroPapeleta(rs.getString("nro_papeleta"));
				obj.setMontoMulta(rs.getBigDecimal("monto_multa"));

				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public List<FindCcLoteDetalleActo> getAllPreliminarRsNoPecOtros()
			throws Exception {
		List<FindCcLoteDetalleActo> list = new ArrayList<FindCcLoteDetalleActo>();
		try {
			String SQL = "sp_preliminar_rs_noPecuniaria_otros";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FindCcLoteDetalleActo obj = new FindCcLoteDetalleActo();
				obj.setPersonaId(rs.getInt("cod_infractor"));
				obj.setNroDocumento(rs.getString("doc_infractor"));
				obj.setDescripcionPersona(rs.getString("datos_infractor"));
				obj.setDireccion(rs.getString("direccion_infractor"));
				obj.setPlaca(rs.getString("placa"));
				obj.setNroPapeleta(rs.getString("nro_papeleta"));
				obj.setMontoMulta(rs.getBigDecimal("monto_multa"));

				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public List<FindCcLote> getAllFindCcLoteOP(String nroLote,
			String fechaRegistro, String estadolote, Integer tipoCobranza)
			throws Exception {
		List<FindCcLote> list = new ArrayList<FindCcLote>();
		StringBuffer sqld = new StringBuffer("");
		try {
			sqld.append(" select l.lote_id,l.tipo_lote_id,l.anno_lote,l.fecha_registro,CONVERT(time(0),l.fecha_registro) hora_registro, ");
			sqld.append(" tl.descripcion tipo_lote,ta.descripcion tipo_acto,ta.tipo_acto_id,a.agrupado_bien,a.agrupado_acto,a.fecha_vencimiento, a.agrupado_cuota, ");
			sqld.append(" case when l.tipo_lote_generacion='1' then 'Preliminar'  when l.tipo_lote_generacion='2' then 'Final' end estado_tipo_lote_generacion, ");
			sqld.append(" case when l.flag_generacion='1' then 'Programado'  when l.flag_generacion='2' then 'Finalizado' end estado_generacion, ");
			sqld.append(" l.tipo_lote_generacion,l.flag_generacion, ");
			sqld.append(" lc.concepto_id,c.descripcion concepto, ");
			sqld.append(" r.monto_deuda,r.total_registros,l.monto_inicio,l.monto_fin ,");
			sqld.append(" case when l.flag_ubicables=2 then 'Ubic.' when l.flag_ubicables=1 then 'Otros' when isnull(l.flag_ubicables,0)=0 then 'S/N'	end flag_ubicables ");
			sqld.append(" FROM dbo.cc_lote l ");
			sqld.append(" inner join dbo.cc_tipo_lote tl on (tl.tipo_lote_id=l.tipo_lote_id  ) ");
			sqld.append(" inner join dbo.cc_lote_acto a on (a.lote_id=l.lote_id  and a.estado='1') ");
			sqld.append(" inner join dbo.cc_tipo_acto ta on ta.tipo_acto_id=a.tipo_acto_id   ");
			sqld.append(" inner join dbo.cc_lote_concepto lc on l.lote_id=lc.lote_id and a.lote_acto_id=lc.lote_acto_id ");
			sqld.append(" inner join dbo.gn_concepto c on c.concepto_id=lc.concepto_id ");
			sqld.append(" left join (select a.lote_id,sum(a.monto_deuda) monto_deuda,count(1) total_registros from dbo.cc_acto a  where a.estado=1 group by a.lote_id) r on (r.lote_id=l.lote_id) ");
			sqld.append(" where l.estado='1' and ta.tipo_acto_id=3 ");
			sqld.append(" order by l.lote_id desc");

			PreparedStatement pst = connect().prepareStatement(sqld.toString());
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
				obj.setAgrupacionBien(rs.getString("agrupado_bien"));
				obj.setAgrupacionActo(rs.getString("agrupado_acto"));
				obj.setFechaVencimiento(rs.getTimestamp("fecha_vencimiento"));
				obj.setAgrupacionCuota(rs.getString("agrupado_cuota"));
				obj.setEstadoTipoLoteGeneracion(rs
						.getString("estado_tipo_lote_generacion"));
				obj.setEstadoGeneracion(rs.getString("estado_generacion"));
				obj.setTipo_lote_generacion(rs
						.getString("tipo_lote_generacion"));
				obj.setFlag_generacion(rs.getString("flag_generacion"));
				obj.setConceptoId(rs.getInt("concepto_id"));
				obj.setConcepto(rs.getString("concepto"));
				obj.setTotalDeuda(rs.getBigDecimal("monto_deuda"));
				obj.setNroDocumentos(rs.getInt("total_registros"));
				obj.setMontoInicio(rs.getBigDecimal("monto_inicio"));
				obj.setMontoFin(rs.getBigDecimal("monto_fin"));
				obj.setFlagUbicables(rs.getString("flag_ubicables"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public List<FindCcLote> getAllFindCcLoteContrib(Integer loteId,
			Integer personaId, Integer tipoCobranza) throws Exception {
		List<FindCcLote> list = new ArrayList<FindCcLote>();
		StringBuffer sqld = new StringBuffer("");
		try {
			sqld.append(" select l.lote_id,l.tipo_lote_id,l.anno_lote,l.fecha_registro,CONVERT(time(0),l.fecha_registro) hora_registro, ");
			sqld.append(" tl.descripcion tipo_lote,ta.descripcion tipo_acto,ta.tipo_acto_id,a.agrupado_bien,a.agrupado_acto,a.fecha_vencimiento, a.agrupado_cuota, ");
			sqld.append(" case when l.tipo_lote_generacion='1' then 'Preliminar'  when l.tipo_lote_generacion='2' then 'Final' end estado_tipo_lote_generacion,  ");
			sqld.append(" case when l.flag_generacion='1' then 'Programado'  when l.flag_generacion='2' then 'Finalizado' end estado_generacion,  ");
			sqld.append(" l.tipo_lote_generacion,l.flag_generacion,  ");
			sqld.append(" lc.concepto_id,c.descripcion concepto,  ");
			sqld.append(" r.monto_deuda,r.total_registros  ");
			sqld.append(" ,t.nro_acto,t.persona_id  ");
			sqld.append(" FROM dbo.cc_lote l    ");
			sqld.append(" inner join dbo.cc_tipo_lote tl on (tl.tipo_lote_id=l.tipo_lote_id  ) ");
			sqld.append(" inner join dbo.cc_lote_acto a on (a.lote_id=l.lote_id  and a.estado='1')  ");
			sqld.append(" inner join dbo.cc_tipo_acto ta on ta.tipo_acto_id=a.tipo_acto_id    ");
			sqld.append(" inner join dbo.cc_lote_concepto lc on l.lote_id=lc.lote_id and a.lote_acto_id=lc.lote_acto_id ");
			sqld.append(" inner join dbo.gn_concepto c on c.concepto_id=lc.concepto_id  ");
			sqld.append(" inner join dbo.cc_acto t on (t.lote_id=l.lote_id and t.concepto_id=1 and t.tipo_acto_id=ta.tipo_acto_id) ");
			sqld.append(" left join (select a.lote_id,sum(a.monto_deuda) monto_deuda,count(1) total_registros from dbo.cc_acto a group by a.lote_id) r on (r.lote_id=l.lote_id) ");
			sqld.append(" where l.estado='1' and t.estado='1' ");
			if (loteId != null && loteId != 0) {
				sqld.append(" and l.lote_id=? ");
			} else if (personaId != null && personaId != 0) {
				sqld.append(" and t.persona_id=? ");
			}
			sqld.append(" order by l.lote_id desc ");

			PreparedStatement pst = connect().prepareStatement(sqld.toString());
			if (loteId != null && loteId != 0) {
				pst.setInt(1, loteId);
			} else if (personaId != null && personaId != 0) {
				pst.setInt(1, personaId);
			}

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
				obj.setAgrupacionBien(rs.getString("agrupado_bien"));
				obj.setAgrupacionActo(rs.getString("agrupado_acto"));
				obj.setFechaVencimiento(rs.getTimestamp("fecha_vencimiento"));
				obj.setAgrupacionCuota(rs.getString("agrupado_cuota"));
				obj.setEstadoTipoLoteGeneracion(rs
						.getString("estado_tipo_lote_generacion"));
				obj.setEstadoGeneracion(rs.getString("estado_generacion"));
				obj.setTipo_lote_generacion(rs
						.getString("tipo_lote_generacion"));
				obj.setFlag_generacion(rs.getString("flag_generacion"));
				obj.setConceptoId(rs.getInt("concepto_id"));
				obj.setConcepto(rs.getString("concepto"));
				obj.setTotalDeuda(rs.getBigDecimal("monto_deuda"));
				obj.setNroDocumentos(rs.getInt("total_registros"));
				// --
				obj.setNroValor(rs.getString("nro_acto"));
				obj.setPersonaId(rs.getInt("persona_id"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public FindCcLote findCcLote(Integer lote_id) throws Exception {
		FindCcLote obj = null;
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append(" select  l.lote_id,l.tipo_lote_id,l.anno_lote,l.fecha_registro,CONVERT(time(0),l.fecha_registro) hora_registro, ");
			sql.append("  tl.descripcion tipo_lote,ta.descripcion tipo_acto,ta.tipo_acto_id,a.agrupado_bien,a.agrupado_acto,a.fecha_vencimiento, ");
			sql.append("  a.agrupado_cuota,f.apellidos_nombres,lf.lote_firma_id,lf.firmante_id, ");
			sql.append("  documentos_generados = case when l.tipo_lote_generacion='1'  ");
			sql.append(" 	 then  (select COUNT(*) from ")
					.append(Constante.schemadb)
					.append(".cc_acto_tmp where lote_id=l.lote_id)  ");
			sql.append(" 	 when l.tipo_lote_generacion='2'  ");
			sql.append(" 	 then  (select COUNT(*) from ")
					.append(Constante.schemadb)
					.append(".cc_acto where lote_id=l.lote_id) end, ");
			sql.append("  nro_contribuyentes = case when l.tipo_lote_generacion='1'  ");
			sql.append(" 	 then  (select COUNT(persona_id) from ")
					.append(Constante.schemadb)
					.append(".cc_acto_tmp where lote_id=l.lote_id)  ");
			sql.append(" 	 when l.tipo_lote_generacion='2'  ");
			sql.append(" 	 then  (select COUNT(persona_id) from ")
					.append(Constante.schemadb)
					.append(".cc_acto where lote_id=l.lote_id) end, ");
			sql.append("  deuda = case when l.tipo_lote_generacion='1'  ");
			sql.append(" 	 then  (select SUM(monto_deuda) from ")
					.append(Constante.schemadb)
					.append(".cc_acto_tmp where lote_id=l.lote_id ) ");
			sql.append(" 	 when l.tipo_lote_generacion='2'  ");
			sql.append(" 	 then  (select SUM(monto_deuda) from ")
					.append(Constante.schemadb)
					.append(".cc_acto where lote_id=l.lote_id ) end, ");
			sql.append("  ls.schedule_id,ls.fecha_schedule,ls.hora_schedule,  ");
			sql.append("  estado_tipo_lote_generacion= case  when l.tipo_lote_generacion='1' then 'Preliminar'  ");
			sql.append("          when l.tipo_lote_generacion='2' then 'Final' end,  ");
			sql.append("  estado_generacion= case  when l.tipo_lote_generacion='1' then 'Programada'  ");
			sql.append("          when l.tipo_lote_generacion='2' then 'Finalizada' end ,l.flag_generacion,l.tipo_lote_generacion ");
			sql.append("   ,r.reporte,r.ruta_w_reporte,r.ruta_l_reporte,r.ruta_w_impresion,r.ruta_l_impresion ");
			sql.append("    FROM ").append(Constante.schemadb)
					.append(".cc_lote l  ");
			sql.append("  inner join ")
					.append(Constante.schemadb)
					.append(".cc_tipo_lote tl on tl.tipo_lote_id=l.tipo_lote_id  ");
			sql.append("  inner join ").append(Constante.schemadb)
					.append(".cc_lote_acto a on a.lote_id=l.lote_id  ");
			sql.append("  inner join ")
					.append(Constante.schemadb)
					.append(".cc_tipo_acto ta on ta.tipo_acto_id=a.tipo_acto_id  ");
			sql.append("  inner join ")
					.append(Constante.schemadb)
					.append(".cc_lote_concepto lc on l.lote_id=lc.lote_id and a.lote_acto_id=lc.lote_acto_id  ");
			sql.append("  left join ").append(Constante.schemadb)
					.append(".cc_lote_firma lf on lf.lote_id=l.lote_id ");
			sql.append("  left join cc_firmantes f on lf.firmante_id=f.firmante_id ");
			sql.append("  left join ")
					.append(Constante.schemadb)
					.append(".cc_lote_schedule ls on ls.lote_id=l.lote_id and ls.estado=? ");
			sql.append("  left join ")
					.append(Constante.schemadb)
					.append(".gn_reportes r on a.tipo_acto_id=r.tipo_acto_id and r.estado='1' ");
			sql.append(" where l.estado =? and l.lote_id=? ");
			String sqlCond = "   and r.concepto_id IS NOT NULL AND (r.concepto_id=lc.concepto_id) ";
			String sqlCond2 = "   and r.concepto_id IS NULL ";
			StringBuffer sql1 = new StringBuffer(sql);
			sql.append(sqlCond);
			sql1.append(sqlCond2);
			sql.append(" union ".concat(sql1.toString()));
			PreparedStatement pst = connect().prepareStatement(sql.toString());
			pst.setString(1, Constante.ESTADO_ACTIVO);
			pst.setString(2, Constante.ESTADO_ACTIVO);
			pst.setInt(3, lote_id);
			pst.setString(4, Constante.ESTADO_ACTIVO);
			pst.setString(5, Constante.ESTADO_ACTIVO);
			pst.setInt(6, lote_id);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				obj = new FindCcLote();
				obj.setLoteId(rs.getInt("lote_id"));
				obj.setTipoLoteId(rs.getInt("tipo_lote_id"));
				obj.setAnnoLote(rs.getInt("anno_lote"));
				obj.setFechaRegistro(rs.getTimestamp("fecha_registro"));
				obj.setHoraRegistro(rs.getString("hora_registro"));
				obj.setTipoLote(rs.getString("tipo_lote"));
				obj.setTipoActo(rs.getString("tipo_acto"));
				obj.setTipoActoId(rs.getInt("tipo_acto_id"));
				obj.setAgrupacionBien(rs.getString("agrupado_bien"));
				obj.setFechaVencimiento(rs.getTimestamp("fecha_vencimiento"));
				obj.setAgrupacionCuota(rs.getString("agrupado_cuota"));
				obj.setAgrupacionActo(rs.getString("agrupado_acto"));
				obj.setNombreEjecutor(rs.getString("apellidos_nombres"));
				obj.setLoteFirmaId(rs.getInt("lote_firma_id"));
				obj.setEjecutorId(rs.getInt("firmante_id"));
				obj.setNroDocumentos(rs.getInt("documentos_generados"));
				obj.setNroPersonas(rs.getInt("nro_contribuyentes"));
				obj.setTotalDeuda(rs.getBigDecimal("deuda"));
				obj.setScheduleId(rs.getInt("schedule_id"));
				obj.setFechaSchedule(rs.getTimestamp("fecha_schedule"));
				obj.setHora_schedule(rs.getString("hora_schedule"));
				obj.setEstadoTipoLoteGeneracion(rs
						.getString("estado_tipo_lote_generacion"));
				obj.setEstadoGeneracion(rs.getString("estado_generacion"));
				obj.setFlag_generacion(rs.getString("flag_generacion"));
				obj.setTipo_lote_generacion(rs
						.getString("tipo_lote_generacion"));
				obj.setReporte(rs.getString("reporte"));
				obj.setRuta_w_reporte(rs.getString("ruta_w_reporte"));
				obj.setRuta_l_reporte(rs.getString("ruta_l_reporte"));
				obj.setRuta_w_impresion(rs.getString("ruta_w_impresion"));
				obj.setRuta_l_impresion(rs.getString("ruta_l_impresion"));
			}
		} catch (Exception e) {
			throw (e);
		}
		return obj;

	}

	public GnSubconcepto findGnSubConcepto(Integer gnconcepto_id,
			String descripcion) throws Exception {
		Query q = null;
		try {
			StringBuffer SQL = new StringBuffer("select * from ")
					.append(Constante.schemadb)
					.append(".gn_subconcepto where estado=?1 and concepto_id=?2 and descripcion=?3 ");
			q = em.createNativeQuery(SQL.toString(), GnSubconcepto.class);

			q.setParameter(1, Constante.ESTADO_ACTIVO);
			q.setParameter(2, gnconcepto_id);
			q.setParameter(3, descripcion);
			GnSubconcepto temp = (GnSubconcepto) q.getSingleResult();
			return temp;
		} catch (Exception e) {
			throw (e);
		}

	}

	public List<FindPeriodoCuota> findDtPeriodoCuota(Integer conceptoId,
			Integer[] lstGnSubConceptoSeleccionados, Integer tipoActoId)
			throws Exception {
		List<FindPeriodoCuota> list = new ArrayList<FindPeriodoCuota>();
		try {
			if (conceptoId != null) {
				StringBuffer SQL = new StringBuffer(
						"select distinct fv.periodo , fv.cuota from ")
						.append(Constante.schemadb)
						.append(".dt_fecha_vencimiento fv where fv.estado=? and fv.concepto_id=? ");
				if (tipoActoId == Constante.TIPO_ACTO_RECOR_DEU_POR_VENCER_ID)
					SQL = new StringBuffer(
							"select top 1 fv.periodo , fv.cuota from ")
							.append(Constante.schemadb)
							.append(".dt_fecha_vencimiento fv where fv.estado=? and fv.concepto_id=? and fv.fecha_vencimiento >=CURRENT_TIMESTAMP ");
				if (tipoActoId == Constante.TIPO_ACTO_RECOR_DEU_POR_VENCER_ID
						&& conceptoId == 4) {
					FindPeriodoCuota obj = new FindPeriodoCuota();
					obj.setPeriodo(DateUtil.getAnioActual());
					obj.setCuota(1);
					list.add(obj);
				}
				if (lstGnSubConceptoSeleccionados != null
						&& lstGnSubConceptoSeleccionados.length > 0) {
					SQL.append(" and(");
					for (int i = 0; i < lstGnSubConceptoSeleccionados.length; i++) {
						if (i == 0)
							SQL.append(lstGnSubConceptoSeleccionados[i] + " =?");
						else
							SQL.append("or " + lstGnSubConceptoSeleccionados[i]
									+ " =?");
					}

					SQL.append(")");
				}
				SQL.append(" order by fv.periodo,fv.cuota");

				PreparedStatement pst = connect().prepareStatement(
						SQL.toString());
				Integer temp1 = 1;
				pst.setString(temp1, Constante.ESTADO_ACTIVO);
				Integer temp2 = 2;
				pst.setInt(temp2, conceptoId);
				if (lstGnSubConceptoSeleccionados != null
						&& lstGnSubConceptoSeleccionados.length > 0) {
					for (Integer p : lstGnSubConceptoSeleccionados) {
						pst.setInt(temp2++, p);
					}
				}
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {

					FindPeriodoCuota obj = new FindPeriodoCuota();
					obj.setPeriodo(rs.getInt("periodo"));
					obj.setCuota(rs.getInt("cuota"));
					list.add(obj);
				}
			}
		} catch (Exception e) {
			throw (e);
		}

		return list;
	}

	public List<CcTipoOrdenImpresion> getAllCcTipoOrdenImpresion()
			throws Exception {
		Query q = null;
		List<CcTipoOrdenImpresion> result = null;
		try {
			StringBuffer SQL = new StringBuffer(" select * FROM ").append(
					Constante.schemadb).append(
					".cc_tipo_orden_impresion where estado =?1 ");
			q = em.createNativeQuery(SQL.toString(), CcTipoOrdenImpresion.class);
			q.setParameter(1, Constante.ESTADO_ACTIVO);
			result = q.getResultList();
			return result;
		} catch (Exception e) {
			q = null;
			return result;
		}
	}

	public List<FindCcLoteConcepto> getAllFindCcLoteConcepto(Integer nro_lote,
			Integer anno) throws Exception {
		Query q = null;
		List<FindCcLoteConcepto> list = new ArrayList<FindCcLoteConcepto>();
		try {
			StringBuffer SQL = new StringBuffer(
					"   select l.anno_lote,lc.lote_id,lc.lote_acto_id,lc.concepto_id,lc.subconcepto_id,c.descripcion concepto,sc.descripcion subconcepto from ")
					.append(Constante.schemadb).append(".cc_lote_concepto lc ");
			SQL.append(" inner join ").append(Constante.schemadb)
					.append(".cc_lote l on l.lote_id=lc.lote_id ");
			SQL.append(" inner join ")
					.append(Constante.schemadb)
					.append(".cc_lote_acto la on la.lote_acto_id=lc.lote_acto_id ");
			SQL.append(" inner join ").append(Constante.schemadb)
					.append(".gn_concepto c on c.concepto_id= lc.concepto_id ");
			SQL.append(" inner join ")
					.append(Constante.schemadb)
					.append(".gn_subconcepto sc on sc.subconcepto_id=lc.subconcepto_id ");
			SQL.append("  where  l.estado =?1 and l.lote_id =?2 and l.anno_lote =?3 ");
			q = em.createNativeQuery(SQL.toString());
			q.setParameter(1, Constante.ESTADO_ACTIVO);
			q.setParameter(2, nro_lote);
			q.setParameter(3, anno);
			List<Object[]> lista1 = q.getResultList();
			for (Object[] o : lista1) {
				FindCcLoteConcepto obj = new FindCcLoteConcepto();
				obj.setAnnoLote((Integer) o[0]);
				obj.setLoteId((Integer) o[1]);
				obj.setLoteActoId((Integer) o[2]);
				obj.setConceptoId((Integer) o[3]);
				obj.setSubconceptoId((Integer) o[4]);
				obj.setConcepto((String) o[5]);
				obj.setSubconcepto((String) o[6]);
				list.add(obj);

			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public List<CcFirmante> findCcFirmantesRS(Integer tipo_acto_id)
			throws Exception {
		Query q = null;
		List<CcFirmante> result = null;
		try {
			StringBuffer SQL = new StringBuffer("  select f.* from ")
					.append(Constante.schemadb)
					.append(".cc_firmantes f inner join ")
					.append(Constante.schemadb)
					.append(".cc_firmantes_tipo_acto fta on f.firmante_id=fta.firmante_id  where fta.tipo_acto_id=?3");
			SQL.append(" order by fta.defecto");
			q = em.createNativeQuery(SQL.toString(), CcFirmante.class);
			q.setParameter(1, tipo_acto_id);
			result = q.getResultList();
			return result;
		} catch (Exception e) {
			q = null;
			return result;

		}
	}

	public List<CcFirmante> findCcFirmantes(Integer tipo_acto_id,
			Integer concepto_id) throws Exception {
		Query q = null;
		List<CcFirmante> result = null;
		try {
			StringBuffer SQL = new StringBuffer("  select f.* from ")
					.append(Constante.schemadb)
					.append(".cc_firmantes f inner join ")
					.append(Constante.schemadb)
					.append(".cc_firmantes_tipo_acto fta on f.firmante_id=fta.firmante_id  where fta.tipo_acto_id=?1  and f.estado=?2");
			if (concepto_id != null && concepto_id > 0)
				SQL.append(" and fta.concepto_id=?3");
			SQL.append(" order by fta.defecto");
			q = em.createNativeQuery(SQL.toString(), CcFirmante.class);
			q.setParameter(1, tipo_acto_id);
			q.setParameter(2, Constante.ESTADO_ACTIVO);
			if (concepto_id != null && concepto_id > 0)
				q.setParameter(3, concepto_id);
			result = q.getResultList();
			return result;
		} catch (Exception e) {
			q = null;
			return result;
		}

	}

	public List<CcFirmante> findCcFirmantesREC(Integer tipo_acto_id)
			throws Exception {
		Query q = null;
		List<CcFirmante> result = null;
		try {
			StringBuffer SQL = new StringBuffer(" select f.* from ")
					.append(Constante.schemadb)
					.append(".cc_firmantes f  inner join ")
					.append(Constante.schemadb)
					.append(".cc_firmantes_tipo_acto fta on f.firmante_id=fta.firmante_id ");
			SQL.append(" inner join ")
					.append(Constante.schemadb)
					.append(".cc_tipo_acto_rec tar on fta.tipo_acto_id=tar.tipo_acto_id  where tar.tipo_acto_id=?1  and f.estado=?2 order by fta.defecto desc");
			q = em.createNativeQuery(SQL.toString(), CcFirmante.class);
			q.setParameter(1, tipo_acto_id);
			q.setParameter(2, Constante.ESTADO_ACTIVO);
			result = q.getResultList();
			return result;
		} catch (Exception e) {
			q = null;
			return result;
		}

	}

	public List<PaDocuAnexo> findCcDocuAnexoLote(Integer lote_id)
			throws Exception {
		Query q = null;
		List<PaDocuAnexo> result = null;
		try {
			StringBuffer SQL = new StringBuffer(" select da.* FROM ").append(
					Constante.schemadb).append(".cc_acto a  ");
			SQL.append(" inner join ").append(Constante.schemadb)
					.append(".cc_acto_deuda ad on a.acto_id=ad.acto_id ");
			SQL.append(" inner join ")
					.append(Constante.schemadb)
					.append(".pa_papeleta pa on ad.nro_referencia=pa.papeleta_id ");
			SQL.append(" inner join ")
					.append(Constante.schemadb)
					.append(".pa_docu_anexo da on da.papeleta_id=pa.papeleta_id and da.estado='1' and da.tipo_documento_id=1 and da.content_id>0 ");
			SQL.append(" WHERE a.lote_id=?");
			q = em.createNativeQuery(SQL.toString(), PaDocuAnexo.class);
			q.setParameter(1, lote_id);
			result = q.getResultList();
			return result;
		} catch (Exception e) {
			q = null;
			return result;
		}

	}

	public List<CcTipoAgrupamiento> getAllCcTipoAgrupamiento() throws Exception {
		Query q = null;
		List<CcTipoAgrupamiento> result = null;
		try {
			String sql = " select * FROM "
					+ SATParameterFactory.getDBNameScheme()
					+ ".cc_tipo_agrupamiento where estado =?1 ";
			q = em.createNativeQuery(sql, CcTipoAgrupamiento.class);
			q.setParameter(1, Constante.ESTADO_ACTIVO);
			result = q.getResultList();
			return result;
		} catch (Exception e) {
			q = null;
			return result;
		}
	}

	public List<SgUsuario> getAllSgUsuario() {
		List<SgUsuario> lista = new ArrayList<SgUsuario>();
		try {
			String SQL = "SELECT usuario_id, apellidos, nombres, nombre_usuario, estado FROM "
					+ SATParameterFactory.getDBNameScheme()
					+ ".sg_usuario ORDER BY apellidos";

			PreparedStatement pst = connect().prepareStatement(SQL);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				SgUsuario obj = new SgUsuario();
				obj.setUsuarioId(rs.getInt("usuario_id"));
				obj.setApellidos(rs.getString("apellidos"));
				obj.setNombres(rs.getString("nombres"));
				obj.setNombreUsuario(rs.getString("nombre_usuario"));
				obj.setEstado(rs.getString("estado"));
				lista.add(obj);
			}
		} catch (Exception e) {
			System.out.println("No ha sido posible obtener los usuarios");
		}
		return lista;
	}

	public List<FindCcLoteOrdenImpresion> getAllCcLoteOrdenImpresion(
			Integer lote_id) throws Exception {
		List<FindCcLoteOrdenImpresion> list = new ArrayList<FindCcLoteOrdenImpresion>();
		try {
			String sql = " select lt.lote_tipo_orden_impresion_id,lt.lote_id,lt.tipo_orden_impresion_id,lt.orden,toi.descripcion tipo_orden_impresion from "
					+ SATParameterFactory.getDBNameScheme()
					+ ".cc_lote_tipo_orden_impresion lt "
					+ " inner join "
					+ SATParameterFactory.getDBNameScheme()
					+ ".cc_tipo_orden_impresion toi on toi.tipo_orden_impresion_id=lt.tipo_orden_impresion_id "
					+ " where lt.lote_id=? order by lt.orden ";
			PreparedStatement pst = connect().prepareStatement(sql);
			pst.setInt(1, lote_id);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FindCcLoteOrdenImpresion obj = new FindCcLoteOrdenImpresion();
				obj.setLoteTipoOrdenImpresionId(rs
						.getInt("lote_tipo_orden_impresion_id"));
				obj.setLoteId(rs.getInt("lote_id"));
				obj.setTipoOrdenImpresionId(rs
						.getInt("tipo_orden_impresion_id"));
				obj.setOrden(rs.getInt("orden"));
				obj.setTipoOrdenImpresion(rs.getString("tipo_orden_impresion"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public int actualizarEstadoLote(Integer loteId,
			String tipo_lote_generacion, String flag_generacion)
			throws Exception {
		int result = 0;
		try {
			StringBuffer SQL = new StringBuffer();

			SQL.append(" UPDATE " + SATParameterFactory.getDBNameScheme()
					+ ".cc_lote ");
			SQL.append(" SET tipo_lote_generacion = ?,  ");
			SQL.append(" flag_generacion = ? where lote_id=? ");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setString(1, tipo_lote_generacion);
			pst.setString(2, flag_generacion);
			pst.setInt(3, loteId);

			result = pst.executeUpdate();
		} catch (Exception e) {
			throw (e);
		}
		return result;
	}

	public int darBajaSchedule(String estado, Integer loteId) throws Exception {
		int result = 0;
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append(" UPDATE " + SATParameterFactory.getDBNameScheme()
					+ ".cc_lote_schedule ");
			SQL.append(" SET estado = ?  ");
			SQL.append(" where lote_id = ? and estado='1' ");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setString(1, estado);
			pst.setInt(2, loteId);
			result = pst.executeUpdate();
		} catch (Exception e) {
			throw (e);
		}
		return result;
	}

	public List<FindCcLoteDetalleActoDeuda> getAllFindCcLoteDetalleActoDeuda(
			Integer lote_id, Integer acto_id, String tipoLoteGeneracion)
			throws Exception {
		List<FindCcLoteDetalleActoDeuda> list = new ArrayList<FindCcLoteDetalleActoDeuda>();
		try {
			String sql1 = "";
			String sql2 = "";
			if (tipoLoteGeneracion
					.compareTo(Constante.TIPO_LOTE_GENERACION_PRELIMINAR) == 0) {
				sql1 = " from "
						+ SATParameterFactory.getDBNameScheme()
						+ ".cc_acto_tmp  a "
						+ " inner join "
						+ SATParameterFactory.getDBNameScheme()
						+ ".cc_acto_deuda_tmp ad on a.acto_id=ad.acto_id and a.lote_id=ad.lote_id  ";
				sql2 = "((select MAX(acto_deuda_id) from "
						+ SATParameterFactory.getDBNameScheme()
						+ ".cc_acto_deuda_tmp)+1)  acto_deuda_id,'','',sum(ad.descuento)  ";
			}
			if (tipoLoteGeneracion
					.compareTo(Constante.TIPO_LOTE_GENERACION_FINAL) == 0) {
				sql1 = " from "
						+ SATParameterFactory.getDBNameScheme()
						+ ".cc_acto  a "
						+ " inner join "
						+ SATParameterFactory.getDBNameScheme()
						+ ".cc_acto_deuda ad on a.acto_id=ad.acto_id and a.lote_id=ad.lote_id  ";
				sql2 = "(select MAX(acto_deuda_id) from "
						+ SATParameterFactory.getDBNameScheme()
						+ ".cc_acto_deuda)+1,'','',sum(ad.descuento)  ";
			}
			String sql = "   select l.lote_id,l.anno_lote,d.anno_deuda,d.nro_cuota "
					+ " ,d.concepto_id,(select descripcion from "
					+ SATParameterFactory.getDBNameScheme()
					+ ".gn_concepto where concepto_id=d.concepto_id) concepto "
					+ " ,d.subconcepto_id,(select descripcion from "
					+ SATParameterFactory.getDBNameScheme()
					+ ".gn_subconcepto where concepto_id=d.concepto_id and subconcepto_id=d.subconcepto_id) subConcepto  "
					+ " ,ad.reajuste,ad.insoluto,ad.interes_anual,ad.derecho_emision,ad.total_debitos, ad.acto_deuda_id,"
					+ " descripcion =  case ad.concepto_id   "
					+ " when 2 then (select p.placa            "
					+ " from "
					+ SATParameterFactory.getDBNameScheme()
					+ ".dt_determinacion de              "
					+ " inner join "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_djvehicular v on (de.djreferencia_id = v.djvehicular_id)         "
					+ " inner join "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_vehiculo p on (p.vehiculo_id = v.vehiculo_id)                  "
					+ " where de.persona_id = a.persona_id                  "
					+ " and de.determinacion_id = d.determinacion_id)    "
					+ " when 3 then (select di.direccion_completa                 "
					+ " from "
					+ SATParameterFactory.getDBNameScheme()
					+ ".dt_determinacion de                  "
					+ " inner join "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rp_djdireccion di on (di.dj_id =de.djreferencia_id  )  and di.estado='1' "
					+ " where de.persona_id = a.persona_id           "
					+ " and de.determinacion_id = d.determinacion_id ) "
					+ " when 4 then (select 'Papel,Placa: '+ pa.nro_papeleta + ', ' + pa.placa "
					+ " from "
					+ SATParameterFactory.getDBNameScheme()
					+ ".dt_determinacion de      "
					+ " inner join  "
					+ SATParameterFactory.getDBNameScheme()
					+ ".pa_papeleta pa on (pa.papeleta_id = de.djreferencia_id  ) "
					+ " where de.persona_id = a.persona_id           "
					+ " and de.determinacion_id = d.determinacion_id ) "
					+ " else ''    end,  "
					+ " case when ad.concepto_id=3 then (select cast(a.descipcion_corta as varchar(100)), + ',' from (select  distinct cu.descripcion_corta "
					+ " from "
					+ SATParameterFactory.getDBNameScheme()
					+ ".dt_determinacion de "
					+ " inner join "
					+ SATParameterFactory.getDBNameScheme()
					+ ".cd_deuda deu on de.determinacion_id=deu.determinacion_id and deu.estado='1' "
					+ "  inner join "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rp_djarbitrios a on de.djreferencia_id=a.dj_id "
					+ " inner join "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rp_djuso u on a.djarbitrio_id=u.djarbitrio_id  and u.estado=1 "
					+ " inner join "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rp_tipo_uso tu on u.tipo_uso_id=tu.tipo_uso_id and tu.estado=1 "
					+ " inner join "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rp_categoria_uso cu on tu.categoria_uso_id=cu.categoria_uso_id and cu.estado=1 "
					+ "   where   de.determinacion_id=d.determinacion_id "
					+ " and  de.concepto_id=3 and (de.subconcepto_id=31 or de.subconcepto_id=33) and de.estado=1) a FOR XML PATH('') ) "
					+ " else '' end uso,ad.descuento "
					+ " "
					+ sql1
					+ ""
					+ " inner join "
					+ SATParameterFactory.getDBNameScheme()
					+ ".cc_lote l on l.lote_id=a.lote_id  "
					+ " inner join "
					+ SATParameterFactory.getDBNameScheme()
					+ ".cd_deuda d on d.deuda_id=ad.deuda_id  "
					+ " where  l.lote_id=? and a.acto_id=?"
					+ " union"
					+ " select 0,0,0,0,0,'',0,'',SUM(ad.reajuste),SUM(ad.insoluto),SUM(ad.interes_anual),SUM(ad.derecho_emision),sum(ad.total_debitos),"
					+ sql2
					+ ""
					+ " "
					+ sql1
					+ ""
					+ " inner join "
					+ SATParameterFactory.getDBNameScheme()
					+ ".cc_lote l on l.lote_id=a.lote_id"
					+ " inner join "
					+ SATParameterFactory.getDBNameScheme()
					+ ".cd_deuda d on d.deuda_id=ad.deuda_id"
					+ " where l.lote_id=? and a.acto_id=?"
					+ " order by acto_deuda_id";
			PreparedStatement pst = connect().prepareStatement(sql);
			pst.setInt(1, lote_id);
			pst.setInt(2, acto_id);
			pst.setInt(3, lote_id);
			pst.setInt(4, acto_id);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FindCcLoteDetalleActoDeuda obj = new FindCcLoteDetalleActoDeuda();
				obj.setLoteId(rs.getInt("lote_id"));
				obj.setAnnoLote(rs.getInt("anno_lote"));
				obj.setAnnoDeuda(rs.getInt("anno_deuda"));
				obj.setCuota(rs.getInt("nro_cuota"));
				obj.setConceptoId(rs.getInt("concepto_id"));
				obj.setConcepto(rs.getString("concepto"));
				obj.setSubConceptoId(rs.getInt("subconcepto_id"));
				obj.setSubConcepto(rs.getString("subConcepto"));
				obj.setReajuste(rs.getBigDecimal("reajuste"));
				obj.setInsoluto(rs.getBigDecimal("insoluto"));
				obj.setInteresAnual(rs.getBigDecimal("interes_anual"));
				obj.setDerechoEmision(rs.getBigDecimal("derecho_emision"));
				obj.setDeudaTotal(rs.getBigDecimal("total_debitos"));
				obj.setDescripcion(rs.getString("descripcion"));
				obj.setUso(rs.getString("uso"));
				obj.setDescuento(rs.getBigDecimal("descuento"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public List<FindCcLote> getAllFindCcLoteImprimir() throws Exception {
		List<FindCcLote> list = new ArrayList<FindCcLote>();
		try {
			String sql = "select lote_id,anno_lote from  "
					+ SATParameterFactory.getDBNameScheme()
					+ ".cc_lote where impresion=1 and estado=?";
			PreparedStatement pst = connect().prepareStatement(sql);
			pst.setString(1, Constante.ESTADO_ACTIVO);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FindCcLote obj = new FindCcLote();
				obj.setLoteId(rs.getInt("lote_id"));
				obj.setAnnoLote(rs.getInt("anno_lote"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public int terminarImpresionLote(Integer loteId, Integer anio)
			throws Exception {
		int result = 0;
		try {

			StringBuffer SQL = new StringBuffer();
			SQL.append(" UPDATE " + SATParameterFactory.getDBNameScheme()
					+ ".cc_lote ");
			SQL.append(" SET impresion = ?  ");
			SQL.append(" where lote_id = ? and anno_lote=? ");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setString(1, Constante.ESTADO_INACTIVO);
			pst.setInt(2, loteId);
			pst.setInt(3, anio);
			result = pst.executeUpdate();
		} catch (Exception e) {
			throw (e);
		}
		return result;
	}

	public int imprimirLote(Integer loteId, Integer anio) throws Exception {
		int result = 0;
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append(" UPDATE " + SATParameterFactory.getDBNameScheme()
					+ ".cc_lote ");
			SQL.append(" SET impresion = ?  ");
			SQL.append(" where lote_id = ? and anno_lote=? ");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setString(1, Constante.ESTADO_ACTIVO);
			pst.setInt(2, loteId);
			pst.setInt(3, anio);
			result = pst.executeUpdate();
		} catch (Exception e) {
			throw (e);
		}
		return result;
	}

	public String rutaimpresion(Integer tipoActoId) throws Exception {
		String temp = "";
		try {
			String os = System.getProperty("os.name");
			if (os.contains("Windows")) {
				temp = "ruta_w_impresion";
			} else if (os.contains("Linux")) {
				temp = "ruta_l_impresion";
			}
			StringBuffer SQL = new StringBuffer();
			SQL.append(" select " + temp + " from "
					+ SATParameterFactory.getDBNameScheme()
					+ ".gn_reportes where estado=? and tipo_acto_id=?");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setString(1, Constante.ESTADO_ACTIVO);
			pst.setInt(2, tipoActoId);
			ResultSet rs = pst.executeQuery();
			rs.next();
			return rs.getString(temp);

		} catch (Exception e) {
			throw (e);
		}
	}

	public int registrarLoteConceptoTodos(Integer lote_id,
			String agrupadoCuota, String agrupadoBien, Integer usuarioId) {
		int salida = 0;
		try {
			CallableStatement cs = connect().prepareCall(
					"{call spt_cc_lote_concepto_todos(?,?,?,?)}");
			cs.setInt(1, lote_id);
			cs.setString(2, agrupadoCuota);
			cs.setString(3, agrupadoBien);
			cs.setInt(4, usuarioId);
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

	public int registrarLoteCuotaTodos(Integer lote_id, Integer usuarioId) {
		int salida = 0;
		try {
			CallableStatement cs = connect().prepareCall(
					"{call spt_cc_lote_cuota_todos(?,?)}");
			cs.setInt(1, lote_id);
			cs.setInt(2, usuarioId);
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

	public List<FindCcActo> getAllFindCcActo(String nroLote, String nroActoRec,
			Integer persona_id, Integer tipoLote) throws Exception {
		List<FindCcActo> list = new ArrayList<FindCcActo>();
		try {
			String sql = "";
			String sqlCondLoteActo = "";
			String sqlCondLoteRec = "";
			String sqlCondNroActo = "";
			String sqlCondNroRec = "";
			String sqlCondPerActo = "";
			String sqlCondPerRec = "";
			String sqlTipoLote = "";
			if (nroLote != null) {
				if (nroLote.compareTo("") != 0) {
					Integer anno = Integer.parseInt(nroLote.substring(nroLote
							.length() - 4));
					Integer lote_id = Integer.parseInt(nroLote.substring(0,
							nroLote.length() - 4));
					sqlCondLoteActo = " and a.lote_id=" + lote_id
							+ " and a.anno_acto=" + anno + "";
					sqlCondLoteRec = " and r.lote_id=" + lote_id
							+ " and r.anno_rec=" + anno + "";
				}
			}
			if (nroActoRec != null) {
				if (nroActoRec.compareTo("") != 0) {
					sqlCondNroActo = " and a.nro_acto='" + nroActoRec + "'";
					sqlCondNroRec = " and r.nro_rec='" + nroActoRec + "'";
				}
			}
			if (persona_id != null && persona_id > 0) {
				sqlCondPerActo = " and a.persona_id=" + persona_id;
				sqlCondPerRec = " and r.persona_id=" + persona_id;
			}
			if (tipoLote != null && tipoLote > 0) {
				sqlTipoLote = " and a.persona_id=?";
				sqlTipoLote = " and r.persona_id=?";
			}
			sql = " SELECT val.acto_id	,val.lote_id,val.anno_acto,val.anno_deuda,val.nro_acto	,val.persona_id	,val.nro_cargo_notificacion	,val.fecha_notificacion"
					+ "	,val.fecha_emision	,val.monto_deuda,val.tipo_acto,val.concepto,val.notificado,val.fecha_registro_notif	,val.notificador"
					+ "	,val.tipo_notificacion	,val.rec_id ,val.registrador FROM (SELECT a.acto_id	,a.lote_id	,a.anno_acto,a.nro_acto	,a.persona_id"
					+ ",a.nro_cargo_notificacion,a.fecha_notificacion,a.fecha_emision,a.monto_deuda	,UPPER(ta.descripcion) tipo_acto ,UPPER(c.descripcion) concepto"
					+ "	,CASE WHEN a.fecha_notificacion IS NULL	THEN 0 	ELSE 1	END notificado"
					+ "	,per.apellidos_nombres notificador	,ntf.fecha_registro fecha_registro_notif ,moti.descripcion tipo_notificacion,0 rec_id "
					+ ",(select nombre_usuario from sg_usuario where usuario_id= ntf.usuario_id) registrador, (select top 1 anno_deuda from cc_acto_deuda where acto_id = a.acto_id) anno_deuda FROM cc_acto a"
					+ "	LEFT JOIN no_notificacion ntf ON (a.acto_id = ntf.acto_id and ntf.estado=1) LEFT JOIN no_motivo_notificacion moti ON moti.motivo_notificacion_id = ntf.motivo_notificacion_id"
					+ "	LEFT JOIN no_notificador per ON per.notificador_id = ntf.notificador_id	INNER JOIN cc_tipo_acto ta ON a.tipo_acto_id = ta.tipo_acto_id"
					+ "	LEFT JOIN gn_concepto c ON a.concepto_id = c.concepto_id WHERE a.estado = 1 "
					+ sqlCondLoteActo
					+ sqlCondNroActo
					+ sqlCondPerActo
					+ " union "
					+ " SELECT 0 acto_id ,r.lote_id	,r.anno_rec	,r.nro_rec	,r.persona_id,r.nro_notificacion nro_cargo_notificacion	,r.fecha_notificacion"
					+ "	,r.fecha_emision,r.deuda_total	,UPPER(tr.descripcion_corta) tipo_acto, UPPER((	SELECT TOP 1 con.descripcion FROM cc_rec_acto ra"
					+ "	INNER JOIN cc_acto cc ON cc.acto_id = ra.acto_id INNER JOIN gn_concepto con ON con.concepto_id = cc.concepto_id	WHERE ra.rec_id = r.rec_id"
					+ "	)) concepto	,CASE WHEN r.fecha_notificacion IS NULL	THEN 0	ELSE 1	END notificado,notif.apellidos_nombres notificador"
					+ "	,r.fecha_notificacion fecha_registro_notif	,mot.descripcion tipo_notificacion	,r.rec_id ,'' registrador, (select top 1 anno_deuda from cc_rec_deuda where rec_id = r.rec_id) anno_deuda FROM cc_rec r"
					+ "	INNER JOIN cc_tipo_rec tr ON r.tipo_rec_id = tr.tipo_rec_id	LEFT JOIN no_motivo_notificacion mot ON mot.motivo_notificacion_id = r.motivo_notificacion_id"
					+ "	LEFT JOIN no_notificador notif ON notif.notificador_id = r.notificador_id"
					+ "	WHERE r.estado = 1 "
					+ sqlCondLoteRec
					+ sqlCondNroRec
					+ sqlCondPerRec
					+ " )val ORDER BY val.lote_id 	,val.fecha_emision DESC";
			PreparedStatement pst = connect().prepareStatement(sql);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				FindCcActo obj = new FindCcActo();
				obj.setActoId(rs.getInt("acto_id"));
				obj.setLoteId(rs.getInt("lote_id"));
				obj.setAnnoActo(rs.getInt("anno_acto"));
				obj.setAnnoDeuda(rs.getInt("anno_deuda"));
				obj.setNroActo(rs.getString("nro_acto"));
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setNroCargoNotificacion(rs
						.getString("nro_cargo_notificacion"));
				obj.setFechaNotificacion(rs.getTimestamp("fecha_notificacion"));
				obj.setFechaEmision(rs.getTimestamp("fecha_emision"));
//				obj.setFechaCancelacion(rs.getTimestamp("fecha_cancelacion"));
				obj.setMontoDeuda(rs.getBigDecimal("monto_deuda"));
				obj.setTipoActo(rs.getString("tipo_acto"));
				obj.setConcepto(rs.getString("concepto"));
				obj.setNotificado(rs.getInt("notificado"));				
				obj.setFechaCancelacion(rs.getTimestamp("fecha_registro_notif"));
				obj.setApellidosNombres(rs.getString("notificador"));
				obj.setTipoNotificacion(rs.getString("tipo_notificacion"));
				obj.setRecId(rs.getInt("rec_id"));
				obj.setRegistrador(rs.getString("registrador"));
				// obj.setContenId(rs.getLong("conten_id"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public FindCcActo findCcActo(String flag_cc, Integer acto_id,
			Integer persona_id) throws Exception {
		FindCcActo obj;
		try {
			String sql = "";
			sql = "select a.acto_id,(SELECT CAST(a2.nro_cuota AS VARCHAR(10)), + ',' from "
					+ "  (SELECT distinct d.nro_cuota" + "  from "
					+ SATParameterFactory.getDBNameScheme()
					+ ".cc_acto a1"
					+ "  inner join "
					+ SATParameterFactory.getDBNameScheme()
					+ ".cc_acto_deuda ad1 on a1.acto_id=ad1.acto_id"
					+ "  inner join "
					+ SATParameterFactory.getDBNameScheme()
					+ ".cd_deuda d on ad1.deuda_id=d.deuda_id"
					+ "  where a1.acto_id=a.acto_id) as a2 "
					+ "  FOR XML PATH('') ) AS Cuotas, a.lote_id,a.anno_acto,a.nro_acto,a.persona_id, a.nro_cargo_notificacion,a.fecha_notificacion,a.fecha_emision,a.fecha_cancelacion,a.monto_deuda"
					+ "  ,ta.descripcion tipo_acto,c.descripcion concepto,(case when  a.fecha_notificacion is null then 0 else 1 end) notificado,rec_id=0,nro_rec='',deuda_total=0,tipoActoRec=''  "
					+ "  from "
					+ SATParameterFactory.getDBNameScheme()
					+ ".cc_acto a "
					+ "  inner join "
					+ SATParameterFactory.getDBNameScheme()
					+ ".cc_tipo_acto ta on a.tipo_acto_id=ta.tipo_acto_id"
					+ "  left join  "
					+ SATParameterFactory.getDBNameScheme()
					+ ".gn_concepto c on a.concepto_id=c.concepto_id "
					+ "  where a.estado=? "
					+ "  and a.acto_id=? and a.persona_id=? "
					+ "   union 	select a.acto_id,(SELECT CAST(a2.nro_cuota AS VARCHAR(10)), + ',' from  "
					+ "	(SELECT distinct d.nro_cuota "
					+ "	from "
					+ SATParameterFactory.getDBNameScheme()
					+ ".cc_rec_acto ra "
					+ "	inner join  "
					+ SATParameterFactory.getDBNameScheme()
					+ ".cc_acto a1 on ra.acto_id=a1.acto_id "
					+ "	inner join "
					+ SATParameterFactory.getDBNameScheme()
					+ ".cc_acto_deuda ad1 on a1.acto_id=ad1.acto_id "
					+ "	inner join "
					+ SATParameterFactory.getDBNameScheme()
					+ ".cd_deuda d on ad1.deuda_id=d.deuda_id "
					+ "	where rec_id=ra1.rec_id) as a2 "
					+ "	FOR XML PATH('') ) AS Cuotas, a.lote_id,a.anno_acto,a.nro_acto,a.persona_id, a.nro_cargo_notificacion,a.fecha_notificacion,a.fecha_emision,a.fecha_cancelacion,a.monto_deuda "
					+ "	,tarec.descripcion tipo_acto,c.descripcion concepto,(case when  a.fecha_notificacion is null then 0 else 1 end) notificado "
					+ "	,ra1.rec_id,r.nro_rec,r.deuda_total,tipoActoRec=ta.descripcion "
					+ "	from "
					+ SATParameterFactory.getDBNameScheme()
					+ ".cc_rec r  "
					+ "	inner join "
					+ SATParameterFactory.getDBNameScheme()
					+ ".cc_rec_acto ra1 on ra1.rec_id=r.rec_id "
					+ "	inner join "
					+ SATParameterFactory.getDBNameScheme()
					+ ".cc_acto a on ra1.acto_id=a.acto_id  inner join cc_tipo_acto tarec on a.tipo_acto_id=tarec.tipo_acto_id"
					+ "	inner join "
					+ SATParameterFactory.getDBNameScheme()
					+ ".cc_tipo_acto ta on ra1.tipo_acto_id=ta.tipo_acto_id  "
					+ "	left join  "
					+ SATParameterFactory.getDBNameScheme()
					+ ".gn_concepto c on a.concepto_id=c.concepto_id "
					+ "	where "
					+ "	a.estado=?  "
					+ "	and ra1.rec_id=? and r.persona_id=?  "
					+ "	order by a.lote_id desc";
			PreparedStatement pst = connect().prepareStatement(sql);
			pst.setString(1, Constante.ESTADO_ACTIVO);
			pst.setInt(2, acto_id);
			pst.setInt(3, persona_id);
			pst.setString(4, Constante.ESTADO_ACTIVO);
			pst.setInt(5, acto_id);
			pst.setInt(6, persona_id);
			ResultSet rs = pst.executeQuery();
			rs.next();
			obj = new FindCcActo();
			obj.setActoId(rs.getInt("acto_id"));
			obj.setCuotas(rs.getString("Cuotas"));
			obj.setLoteId(rs.getInt("lote_id"));
			obj.setAnnoActo(rs.getInt("anno_acto"));
			obj.setNroActo(rs.getString("nro_acto"));
			obj.setPersonaId(rs.getInt("persona_id"));
			obj.setNroCargoNotificacion(rs.getString("nro_cargo_notificacion"));
			obj.setFechaNotificacion(rs.getTimestamp("fecha_notificacion"));
			obj.setFechaEmision(rs.getTimestamp("fecha_emision"));
			obj.setFechaCancelacion(rs.getTimestamp("fecha_cancelacion"));
			obj.setMontoDeuda(rs.getBigDecimal("monto_deuda"));
			obj.setTipoActo(rs.getString("tipo_acto"));
			obj.setConcepto(rs.getString("concepto"));
			obj.setNotificado(rs.getInt("notificado"));
			obj.setNroRec(rs.getString("nro_rec"));
			obj.setRecId(rs.getInt("rec_id"));
			obj.setDeudaTotalRecActo(rs.getBigDecimal("deuda_total"));
			obj.setTipoActoRec(rs.getString("tipoActoRec"));
		} catch (Exception e) {
			throw (e);
		}
		return obj;
	}

	public List<NoMotivoNotificacion> getAlNoMotivoNotificacion(Integer flagUbicacion)
			throws Exception {
		List<NoMotivoNotificacion> list = new ArrayList<NoMotivoNotificacion>();
		try {
			String sql = " select * from "
					+ SATParameterFactory.getDBNameScheme()
					+ ".no_motivo_notificacion where estado=? and ubicacion_id=?";
			PreparedStatement pst = connect().prepareStatement(sql);
			pst.setString(1, Constante.ESTADO_ACTIVO);
			pst.setInt(2, flagUbicacion);
			ResultSet rs = pst.executeQuery();
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
			String sql = " select notificador_id,apellidos_nombres from "
					+ SATParameterFactory.getDBNameScheme()
					+ ".no_notificador where estado=1";
			PreparedStatement pst = connect().prepareStatement(sql);
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

	public List<NoRelacionPersona> getAlNoRelacionPersona() throws Exception {
		List<NoRelacionPersona> list = new ArrayList<NoRelacionPersona>();
		try {
			String sql = " select * from "
					+ SATParameterFactory.getDBNameScheme()
					+ ".no_relacion_persona where estado=?";
			PreparedStatement pst = connect().prepareStatement(sql);
			pst.setString(1, Constante.ESTADO_ACTIVO);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				NoRelacionPersona obj = new NoRelacionPersona();
				obj.setRelacionNotificacionId(rs
						.getInt("relacion_notificacion_id"));
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
	public int actualizarRequerimientoVehicular(RvFiscalizacionNotificacion notificacion) throws Exception{
		int result = 0;
		try {
			StringBuffer SQL = new StringBuffer();
			if (notificacion.getRequerimientoId() > 0) {
				SQL.append(" UPDATE " + SATParameterFactory.getDBNameScheme()
						+ ".rv_fiscalizacion_inspeccion");
				SQL.append(" SET fecha_notificacion = ?");
				SQL.append(" where requerimiento_id = ? ");				
			}
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setTimestamp(1, notificacion.getFechaNotificacion());
			pst.setInt(2, notificacion.getRequerimientoId());
			result = pst.executeUpdate();
		} catch (Exception e) {
			throw (e);
		}
		return result;
	}
	public int actualizarActo(NoNotificacion notificacion) throws Exception {
		int result = 0;
		try {
			StringBuffer SQL = new StringBuffer();
			if (notificacion.getActoId() != null
					&& notificacion.getActoId() > 0) {
				SQL.append(" UPDATE " + SATParameterFactory.getDBNameScheme()
						+ ".cc_acto ");
				SQL.append(" SET fecha_notificacion = ?,notificacion_id =?  ");
				SQL.append(" where acto_id = ? ");
				SQL.append(" update pa 	SET pa.fecha_recepcion=? from pa_resolucion_papeleta pa");
				SQL.append(" inner join  cc_acto cc on  cc.papeleta_id=pa.papeleta_id and cc.acto_id=?");
				
				SQL.append(" UPDATE " + SATParameterFactory.getDBNameScheme()
						+ ".no_notificacion ");
				SQL.append(" SET estado = 9 ");
				SQL.append(" where notificacion_id <> ?  and acto_id = ? ");	
			}

			if (notificacion.getRecId() != null && notificacion.getRecId() > 0) {
				SQL.append(" UPDATE " + SATParameterFactory.getDBNameScheme()
						+ ".cc_rec ");
				SQL.append(" SET fecha_notificacion = ?,nro_notificacion =?  ");
				SQL.append(" where rec_id = ? ");
			}

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setTimestamp(1, notificacion.getFechaNotificacion());
			pst.setInt(2, notificacion.getNotificacionId());
			if (notificacion.getActoId() != null
					&& notificacion.getActoId() > 0) {
				pst.setInt(3, notificacion.getActoId());
				pst.setTimestamp(4, notificacion.getFechaNotificacion());
				pst.setInt(5, notificacion.getActoId());
				pst.setInt(6, notificacion.getNotificacionId());
				pst.setInt(7, notificacion.getActoId());
			}
			if (notificacion.getRecId() != null && notificacion.getRecId() > 0) {
				pst.setInt(3, notificacion.getRecId());
			}
			result = pst.executeUpdate();
		} catch (Exception e) {
			throw (e);
		}
		return result;
	}

	public List<FindCcRec> getAllFindCcLoteRec(Integer lote_id,
			Integer TipoActoId, String tipoLoteGeneracion) throws Exception {
		List<FindCcRec> list = new ArrayList<FindCcRec>();
		try {
			String sqlCond = "";
			String sql = "";
			String sqlOrder = "";
			String recActo = "";
			String sqlCondRec = "";
			if (tipoLoteGeneracion
					.compareTo(Constante.TIPO_LOTE_GENERACION_PRELIMINAR) == 0) {
				sqlCond = " ,RecId=r.rec_tmp_id  from "
						+ SATParameterFactory.getDBNameScheme()
						+ ".cc_rec_tmp r";
				sqlOrder = " order by r.rec_tmp_id";
				recActo = " " + SATParameterFactory.getDBNameScheme()
						+ ".cc_rec_acto_tmp";
				sqlCondRec = "  ra.rec_tmp_id=r.rec_tmp_id ";
			}
			if (tipoLoteGeneracion
					.compareTo(Constante.TIPO_LOTE_GENERACION_FINAL) == 0) {
				sqlCond = " ,RecId=r.rec_id  from "
						+ SATParameterFactory.getDBNameScheme() + ".cc_rec r";
				sqlOrder = "order by r.rec_id";
				recActo = " " + SATParameterFactory.getDBNameScheme()
						+ ".cc_rec_acto";
				sqlCondRec = "  ra.rec_id=r.rec_id ";
			}
			if (TipoActoId != Constante.TIPO_ACTO_RESOLUCION_SANCION_ID) {
				sql = "  SELECT NroDocumento=c.nro_docu_identidad,persona=c.apellidos_nombres, "
						+ "  AnnoRec=r.anno_rec,TipoActoId=ta.tipo_acto_id,TipoActoDes=ta.descripcion, "
						+ "  PersonaId=r.persona_id, "
						+ "  NroCargoNotificacion=r.nro_notificacion, "
						+ "  Fecha_emision=r.fecha_emision,NroResolucion=r.nro_rec,DeudaTotal=r.deuda_total, "
						+ "  LoteId=r.lote_id,pd.domicilio_completo,"
						+ "  tipo_acto_rec=(select descripcion from "
						+ SATParameterFactory.getDBNameScheme()
						+ ".cc_tipo_acto where tipo_acto_id=tar.tipo_acto_rec),r.nro_expediente"
						+ sqlCond
						+ "  inner join "
						+ SATParameterFactory.getDBNameScheme()
						+ ".cc_lote_acto la on r.lote_id=la.lote_id"
						+ "  inner join "
						+ SATParameterFactory.getDBNameScheme()
						+ ".cc_tipo_acto ta on la.tipo_acto_id=ta.tipo_acto_id "
						+ "  inner join "
						+ SATParameterFactory.getDBNameScheme()
						+ ".cc_tipo_acto_rec tar on ta.tipo_acto_id=tar.tipo_acto_id"
						+ "  INNER JOIN "
						+ SATParameterFactory.getDBNameScheme()
						+ ".mp_persona c ON (r.persona_id=c.persona_id) "
						+ "  left join "
						+ SATParameterFactory.getDBNameScheme()
						+ ".mp_persona_domicilio pd on pd.persona_id=c.persona_id and pd.flag_fiscal='1'  and pd.estado='1'"
						+ "  where r.lote_id=? " + sqlOrder;
			} else {
				sql = "  SELECT NroDocumento=per.nro_doc_identidad,persona=per.ape_paterno+ ' '+ per.ape_materno+ ' '+ per.primer_nombre+ ' '+ per.segundo_nombre, "
						+ "  AnnoRec=r.anno_rec,TipoActoId=ta.tipo_acto_id,TipoActoDes=ta.descripcion, "
						+ "  PersonaId=r.persona_id, "
						+ "  NroCargoNotificacion=r.nro_notificacion, "
						+ "  Fecha_emision=r.fecha_emision,NroResolucion=r.nro_rec,DeudaTotal=r.deuda_total, "
						+ "  loteId=r.lote_id,domicilio_completo=p.direccion_completa, "
						+ "  tipo_acto_rec=(select descripcion from "
						+ SATParameterFactory.getDBNameScheme()
						+ ".cc_tipo_acto where tipo_acto_id=tar.tipo_acto_rec),r.nro_expediente "
						+ sqlCond
						+ "  inner join "
						+ SATParameterFactory.getDBNameScheme()
						+ ".cc_lote_acto la on r.lote_id=la.lote_id   "
						+ "  inner join "
						+ SATParameterFactory.getDBNameScheme()
						+ ".cc_tipo_acto ta on la.tipo_acto_id=ta.tipo_acto_id    "
						+ "  inner join "
						+ SATParameterFactory.getDBNameScheme()
						+ ".cc_tipo_acto_rec tar on ta.tipo_acto_id=tar.tipo_acto_id  "
						+ "  inner join "
						+ SATParameterFactory.getDBNameScheme()
						+ ".pa_persona per on r.persona_id=per.persona_id   "
						+ "  inner join "
						+ SATParameterFactory.getDBNameScheme()
						+ ".pa_direccion p on p.persona_id =per.persona_id and p.papeleta_id in(  "
						+ "  select top 1 ad.nro_referencia  from "
						+ recActo
						+ " ra  "
						+ "  inner join cc_acto a on ra.acto_id=a.acto_id  "
						+ "  inner join cc_acto_deuda ad on a.acto_id=ad.acto_id "
						+ "  where "
						+ sqlCondRec
						+ " and ra.lote_id="
						+ lote_id
						+ ") and p.estado=1 "
						+ "  where r.lote_id=? " + sqlOrder;
			}

			PreparedStatement pst = connect().prepareStatement(sql);
			pst.setInt(1, lote_id);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FindCcRec obj = new FindCcRec();
				obj.setNroDocumento(rs.getString("NroDocumento"));
				obj.setDescripcionPersona(rs.getString("persona"));
				obj.setAnnoRec(rs.getInt("AnnoRec"));
				obj.setRecId(rs.getInt("RecId"));
				obj.setTipoActoId(rs.getInt("TipoActoId"));
				obj.setTipoActo(rs.getString("TipoActoDes"));
				obj.setPersonaId(rs.getInt("personaId"));
				obj.setNroCargoNotificacion(rs
						.getString("NroCargoNotificacion"));
				obj.setFechaEmision(rs.getTimestamp("Fecha_emision"));
				obj.setNroRec(rs.getString("NroResolucion"));
				obj.setDeudaTotal(rs.getBigDecimal("DeudaTotal"));
				obj.setLoteId(rs.getInt("LoteId"));
				obj.setDireccion(rs.getString("domicilio_completo"));
				obj.setTipoActoRecDescrip(rs.getString("tipo_acto_rec"));
				obj.setNroExpediente(rs.getString("nro_expediente"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public List<FindCcActo> getAllCcActoRec(Integer lote_id, Integer rec_id,
			String tipoLoteGeneracion) throws Exception {
		List<FindCcActo> list = new ArrayList<FindCcActo>();
		try {
			String sql = " select a.acto_id,a.lote_id,a.anno_acto,a.nro_acto,a.persona_id, a.nro_cargo_notificacion,a.fecha_notificacion,a.fecha_emision,a.fecha_cancelacion,"
					+ " a.monto_deuda,case when  a.fecha_notificacion is null then 0 else 1 end notificado"
					+ " ,deuda_total_rec_acto=ra.deuda_total from ";
			String sqlRec;
			String sqlRecCond;
			if (tipoLoteGeneracion
					.compareTo(Constante.TIPO_LOTE_GENERACION_PRELIMINAR) == 0) {
				sqlRec = " "
						+ SATParameterFactory.getDBNameScheme()
						+ ".cc_rec_tmp r inner join "
						+ SATParameterFactory.getDBNameScheme()
						+ ".cc_rec_acto_tmp ra on r.rec_tmp_id=ra.rec_tmp_id and r.lote_id=ra.lote_id ";
				sqlRecCond = "and r.rec_tmp_id =?  ";
			} else {
				sqlRec = " "
						+ SATParameterFactory.getDBNameScheme()
						+ ".cc_rec r inner join "
						+ SATParameterFactory.getDBNameScheme()
						+ ".cc_rec_acto ra on r.rec_id=ra.rec_id and r.lote_id=ra.lote_id ";
				sqlRecCond = "and r.rec_id =?  ";
			}
			sql = sql + sqlRec + " inner join "
					+ SATParameterFactory.getDBNameScheme()
					+ ".cc_acto a on ra.acto_id=a.acto_id where r.lote_id =? "
					+ sqlRecCond;
			PreparedStatement pst = connect().prepareStatement(sql);
			pst.setInt(1, lote_id);
			pst.setInt(2, rec_id);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FindCcActo obj = new FindCcActo();
				obj.setActoId(rs.getInt("acto_id"));
				obj.setLoteId(rs.getInt("lote_id"));
				obj.setAnnoActo(rs.getInt("anno_acto"));
				obj.setNroActo(rs.getString("nro_acto"));
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setNroCargoNotificacion(rs
						.getString("nro_cargo_notificacion"));
				obj.setFechaNotificacion(rs.getTimestamp("fecha_notificacion"));
				obj.setFechaEmision(rs.getTimestamp("fecha_emision"));
				obj.setFechaCancelacion(rs.getTimestamp("fecha_cancelacion"));
				obj.setMontoDeuda(rs.getBigDecimal("monto_deuda"));
				obj.setNotificado(rs.getInt("notificado"));
				obj.setDeudaTotalRecActo(rs
						.getBigDecimal("deuda_total_rec_acto"));
				list.add(obj);
			}
			return list;
		} catch (Exception e) {
			System.out.println("error");
			// q = null;
			return list;
		}
	}

	public String notificarMasiva(String directorio, String nombreArchivo,
			Integer masiva_notificacion_id, Integer usuarioId, String terminal)
			throws Exception {
		String errorMessage = new String();
		Connection cn = connect();
		try {
			String query = "{ call " + SATParameterFactory.getDBNameScheme()
					+ ".sp_notificar_masivo(?,?,?,?,?,?) }";
			// cn.setAutoCommit(false);
			CallableStatement oCallableStatement = cn.prepareCall(query);
			oCallableStatement.registerOutParameter(1, Types.VARCHAR);
			oCallableStatement.setString(2, directorio);
			oCallableStatement.setString(3, nombreArchivo);
			oCallableStatement.setInt(4, masiva_notificacion_id);
			oCallableStatement.setInt(5, usuarioId);
			oCallableStatement.setString(6, terminal);
			oCallableStatement.execute();
			errorMessage = oCallableStatement.getString(1);
		} catch (Exception e) {
			throw (e);
		}
		return errorMessage;
	}

	public ArrayList<NoDetalleMasivaNotificacion> getAllNoDetalleMasivaNotificacion(
			Integer noMasivaNotificacionId) throws Exception {
		Query q = null;
		ArrayList<NoDetalleMasivaNotificacion> result = null;
		try {
			String sql = " select * from "
					+ SATParameterFactory.getDBNameScheme()
					+ ".no_detalle_masiva_notificacion where masiva_notificacion_id =?1 ";
			q = em.createNativeQuery(sql, NoDetalleMasivaNotificacion.class);
			q.setParameter(1, noMasivaNotificacionId);
			result = (ArrayList<NoDetalleMasivaNotificacion>) q.getResultList();
			// (ArrayList<PaCargaDetalleLote>)result;
			return result;
		} catch (Exception e) {
			q = null;

			return result;
		}
	}

	public List<FindNotificacion> getAllFindNoNotificacion(
			Integer noMasivaNotificacionId) throws Exception {
		List<FindNotificacion> list = new ArrayList<FindNotificacion>();
		try {
			String sql = " select d.correlativo, d.detalle_Masiva_Notificacion_Id,d.masiva_Notificacion_Id,d.acto_Id,d.nro_acto,d.nro_rec,d.fecha_notificacion,d.motivo_notificacion_id,d.error_code,d.error_message,mn.descripcion,notificador=n.apellidos_nombres  from "
					+ SATParameterFactory.getDBNameScheme()
					+ ".no_detalle_masiva_notificacion d "
					+ " left join "
					+ SATParameterFactory.getDBNameScheme()
					+ ".no_motivo_notificacion mn on d.motivo_notificacion_id=mn.motivo_notificacion_id "
					+ " left join "
					+ SATParameterFactory.getDBNameScheme()
					+ ".no_notificador n on d.codigo_notificador=n.notificador_id "
					+ " where d.masiva_notificacion_id=? ";
			PreparedStatement pst = connect().prepareStatement(sql);
			pst.setInt(1, noMasivaNotificacionId);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FindNotificacion obj = new FindNotificacion();
				obj.setCorrelativo(rs.getInt("correlativo"));
				obj.setDetalleMasivaNotificacionId(rs
						.getInt("detalle_Masiva_Notificacion_Id"));
				obj.setMasivaNotificacionId(rs.getInt("masiva_Notificacion_Id"));
				obj.setActoId(rs.getInt("acto_Id"));
				obj.setNroActo(rs.getString("nro_acto"));
				obj.setNroRec(rs.getString("nro_rec"));
				obj.setFechaNotificacion(rs.getString("fecha_notificacion"));
				obj.setMotivoNotificacionId(rs.getInt("motivo_notificacion_id"));
				obj.setMotivoNotificacion(rs.getString("descripcion"));
				obj.setNotificador(rs.getString("notificador"));
				obj.setErrorCode(rs.getString("error_code"));
				obj.setErrorMessage(rs.getString("error_message"));
				list.add(obj);
			}
			return list;
		} catch (Exception e) {
			System.out.println("error");
			return list;
		}
	}

	public NotificacionesDTO findNoNotificacionDTO(String nroDocumento)
			throws Exception {
		NotificacionesDTO obj = new NotificacionesDTO();
		try {
			String sql = " select n.fecha_notificacion,n.notificacion_id,nro_documento=ISNULL(a.nro_acto,r.nro_rec),persona_id=ISNULL(a.persona_id,r.persona_id) "
					+ " ,tipo_acto=isnull(ta.descripcion,(select top 1 ta1.descripcion from "
					+ SATParameterFactory.getDBNameScheme()
					+ ".cc_rec_acto ra  "
					+ " inner join "
					+ SATParameterFactory.getDBNameScheme()
					+ ".cc_tipo_acto ta1 on ra.tipo_acto_id=ta1.tipo_acto_id where ra.rec_id=r.rec_id ) ),n.conten_id "
					+ " from "
					+ SATParameterFactory.getDBNameScheme()
					+ ".no_notificacion n "
					+ " left join "
					+ SATParameterFactory.getDBNameScheme()
					+ ".cc_acto a on n.acto_id=a.acto_id "
					+ " left join "
					+ SATParameterFactory.getDBNameScheme()
					+ ".cc_rec r on n.rec_id=r.rec_id "
					+ " left join "
					+ SATParameterFactory.getDBNameScheme()
					+ ".cc_tipo_acto ta on a.tipo_acto_id=ta.tipo_acto_id "
					+ "where a.nro_acto=substring('"
					+ nroDocumento
					+ "',0,17) or r.nro_rec=substring('"
					+ nroDocumento
					+ "',0,17)";
			PreparedStatement pst = connect().prepareStatement(sql);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				obj.setFechaNotificacion(rs.getTimestamp("fecha_notificacion"));
				obj.setNotificacionesId(rs.getInt("notificacion_id"));
				obj.setNroDocumento(rs.getString("nro_documento"));
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setTipoActo(rs.getString("tipo_acto"));
				obj.setContenId(rs.getLong("conten_id"));
			}
			return obj;
		} catch (Exception e) {
			System.out.println("error");
			return obj;
		}
	}

	public int actualizarNotificacionContenId(Integer notificacionId,
			Long contenId) throws Exception {
		int result = 0;
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append(" UPDATE " + SATParameterFactory.getDBNameScheme()
					+ ".no_notificacion ");
			SQL.append(" SET conten_id=?  ");
			SQL.append(" where notificacion_id=? ");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setLong(1, contenId);
			pst.setInt(2, notificacionId);
			result = pst.executeUpdate();
		} catch (Exception e) {
			throw (e);
		}
		return result;
	}

	public List<NoDetalleMasivaDigiNotif> getAllNoDetalleMasivaDigiNotif(
			Integer masivaDigiNotifId, String estado) throws Exception {
		Query q = null;
		List<NoDetalleMasivaDigiNotif> result = null;
		try {
			String sql = " select * FROM "
					+ SATParameterFactory.getDBNameScheme()
					+ ".no_detalle_masiva_digi_notif where masiva_digi_notif_id=?1 and estado =?2 ";
			q = em.createNativeQuery(sql, NoDetalleMasivaDigiNotif.class);
			q.setParameter(1, masivaDigiNotifId);
			q.setParameter(2, estado);
			result = q.getResultList();
			return result;
		} catch (Exception e) {
			q = null;
			return result;
		}
	}

	public List<CcRecAcumulada> findRecAcumulables() throws Exception {
		List<CcRecAcumulada> lista = new ArrayList<CcRecAcumulada>();
		try {
			StringBuilder SQL = new StringBuilder(
					"select r.persona_id,cantidad= COUNT(r.persona_id) from "
							+ SATParameterFactory.getDBNameScheme()
							+ ".cc_rec r  inner join "
							+ SATParameterFactory.getDBNameScheme()
							+ ".no_notificacion n on r.rec_id=n.rec_id  where r.rec_id_acumulacion is null "
							+ "group by r.persona_id "
							+ "having COUNT(r.persona_id)>1");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				CcRecAcumulada obj = new CcRecAcumulada();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setCantidad(rs.getInt("cantidad"));
				obj.setSeleccionado(Boolean.FALSE);
				obj.setDescripcion("Rec Acumulada - " + rs.getInt("persona_id"));
				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	public List<CcRecAcumulada> findRecAcumulables(Integer personaId)
			throws Exception {
		List<CcRecAcumulada> lista = new ArrayList<CcRecAcumulada>();
		try {
			StringBuilder SQL = new StringBuilder(
					"select r.rec_id, r.persona_id,r.nro_rec,tipo_rec=tr.descripcion_corta,n.conten_id from "
							+ SATParameterFactory.getDBNameScheme()
							+ ".cc_rec r inner join "
							+ SATParameterFactory.getDBNameScheme()
							+ ".cc_tipo_rec tr on r.tipo_rec_id=tr.tipo_rec_id "
							+ " inner join "
							+ SATParameterFactory.getDBNameScheme()
							+ ".no_notificacion n on r.rec_id=n.rec_id  where r.rec_id_acumulacion is null and persona_id=?");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, personaId);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				CcRecAcumulada obj = new CcRecAcumulada();

				obj.setRecId(rs.getInt("rec_id"));
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setTipoRec(rs.getString("tipo_rec"));
				obj.setSeleccionado(Boolean.FALSE);
				obj.setDescripcion(rs.getString("tipo_rec") + " - "
						+ rs.getString("nro_rec"));
				obj.setContenId(rs.getLong("conten_id"));
				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	public String acumularRecs(Integer persona_id, String recIds,
			Integer anno_rec, Integer usuarioId, String terminal)
			throws Exception {
		String nroRec = new String();
		Connection cn = connect();
		try {
			String query = "{ call " + SATParameterFactory.getDBNameScheme()
					+ ".stp_gen_REC_acumulada(?,?,?,?,?,?) }";
			CallableStatement oCallableStatement = cn.prepareCall(query);
			oCallableStatement.registerOutParameter(1, Types.VARCHAR);
			oCallableStatement.setInt(2, persona_id);
			oCallableStatement.setString(3, recIds);
			oCallableStatement.setInt(4, anno_rec);
			oCallableStatement.setInt(5, usuarioId);
			oCallableStatement.setString(6, terminal);
			oCallableStatement.execute();
			nroRec = oCallableStatement.getString(1);
		} catch (Exception e) {
			throw (e);
		}
		return nroRec;
	}

	/**
	 * Rd Arbitrios
	 */
	public List<FindCcLote> getAllFindCcLoteRDArbitrios(Integer loteId,
			Integer periodo, Integer tipoCobranza) throws Exception {
		List<FindCcLote> list = new ArrayList<FindCcLote>();
		StringBuffer sqld = new StringBuffer("");
		try {
			sqld.append(" select l.lote_id,l.tipo_lote_id,l.anno_lote,l.fecha_registro,CONVERT(time(0),l.fecha_registro) hora_registro, ");
			sqld.append(" tl.descripcion tipo_lote,ta.descripcion tipo_acto,ta.tipo_acto_id,a.agrupado_bien,a.agrupado_acto,a.fecha_vencimiento, a.agrupado_cuota, ");
			sqld.append(" case when l.tipo_lote_generacion='1' then 'Preliminar'  when l.tipo_lote_generacion='2' then 'Final' end estado_tipo_lote_generacion, ");
			sqld.append(" case when l.flag_generacion='1' then 'Programado'  when l.flag_generacion='2' then 'Finalizado' end estado_generacion, ");
			sqld.append(" l.tipo_lote_generacion,l.flag_generacion, ");
			sqld.append(" lc.concepto_id,c.descripcion concepto, ");
			sqld.append(" r.monto_deuda,r.total_registros ");
			sqld.append(" FROM dbo.cc_lote l ");
			sqld.append(" inner join dbo.cc_tipo_lote tl on (tl.tipo_lote_id=l.tipo_lote_id  ) ");
			sqld.append(" inner join dbo.cc_lote_acto a on (a.lote_id=l.lote_id  and a.estado='1') ");
			sqld.append(" inner join dbo.cc_tipo_acto ta on ta.tipo_acto_id=a.tipo_acto_id ");
			sqld.append(" inner join dbo.cc_lote_concepto lc on l.lote_id=lc.lote_id and a.lote_acto_id=lc.lote_acto_id ");
			sqld.append(" inner join dbo.gn_concepto c on c.concepto_id=lc.concepto_id ");
			sqld.append(" left join (select a.lote_id,sum(a.monto_deuda) monto_deuda,count(1) total_registros from dbo.cc_acto a group by a.lote_id) r on (r.lote_id=l.lote_id) ");
			sqld.append(" where l.estado='1' and lc.concepto_id=3 ");

			if (loteId != null && loteId > Constante.RESULT_PENDING) {
				sqld.append(" and l.lote_id=? ");
			} else if (periodo != null && periodo > Constante.RESULT_PENDING) {
				sqld.append(" and l.anno_lote=? ");
			}
			sqld.append(" order by l.lote_id desc ");

			PreparedStatement pst = connect().prepareStatement(sqld.toString());
			if (loteId != null && loteId > Constante.RESULT_PENDING) {
				pst.setInt(1, loteId);
			} else if (periodo != null && periodo > Constante.RESULT_PENDING) {
				pst.setInt(1, periodo);
			}

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
				obj.setAgrupacionBien(rs.getString("agrupado_bien"));
				obj.setAgrupacionActo(rs.getString("agrupado_acto"));
				obj.setFechaVencimiento(rs.getTimestamp("fecha_vencimiento"));
				obj.setAgrupacionCuota(rs.getString("agrupado_cuota"));
				obj.setEstadoTipoLoteGeneracion(rs
						.getString("estado_tipo_lote_generacion"));
				obj.setEstadoGeneracion(rs.getString("estado_generacion"));
				obj.setTipo_lote_generacion(rs
						.getString("tipo_lote_generacion"));
				obj.setFlag_generacion(rs.getString("flag_generacion"));
				obj.setConceptoId(rs.getInt("concepto_id"));
				obj.setConcepto(rs.getString("concepto"));
				obj.setTotalDeuda(rs.getBigDecimal("monto_deuda"));
				obj.setNroDocumentos(rs.getInt("total_registros"));
				// --
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public List<FindCcLoteDetalleActo> getAllFindCcLotePreliminarRDArbitrios(
			Integer periodo, Double montoMinimo, Integer estadoDireccionId)
			throws Exception {
		List<FindCcLoteDetalleActo> list = new ArrayList<FindCcLoteDetalleActo>();
		try {
//			StringBuffer SQL = new StringBuffer();
//			SQL.append(" select p.persona_id,persona.nro_docu_identidad,persona.apellidos_nombres,pd.domicilio_completo,t.descripcion sector,tv.descripcion tipo_via,v.descripcion via,d.numero, ");
//			SQL.append(" p.total_insoluto,p.total_interes_capitalizado,p.total_interes_simple,p.total_deuda_arbitrios ");
//			SQL.append(" from ( ");
//			SQL.append(" select  ");
//			SQL.append(" p.persona_id, ");
//			SQL.append(" sum(l.total_deuda) total_deuda_arbitrios, ");
//			SQL.append(" sum(l.insoluto) total_insoluto, ");
//			SQL.append(" sum(l.interes_capitalizado) total_interes_capitalizado, ");
//			SQL.append(" sum(l.interes_simp) total_interes_simple ");
//			SQL.append(" from dbo.mp_persona p  ");
//			SQL.append(" inner join dbo.cd_saldos l on (l.persona_id=p.persona_id and l.concepto_id=3 and l.estado_original=1 and l.estado_deuda=2 and isnull(l.flag_rda,0)=0) ");
//			SQL.append(" where p.estado='1' and l.anno_deuda=? ");
//			SQL.append(" group by p.persona_id ");
//			SQL.append(" ) p  ");
//			SQL.append(" inner join dbo.mp_persona persona on (persona.persona_id=p.persona_id) ");
//			SQL.append(" inner join dbo.mp_persona_domicilio pd on (pd.persona_id=p.persona_id and pd.estado='1') ");
//			SQL.append(" inner join dbo.mp_direccion d on (d.persona_id=p.persona_id and pd.direccion_id=pd.direccion_id and d.estado='1') ");
//			SQL.append(" inner join dbo.gn_ubicacion u on (u.ubicacion_id=d.ubicacion_id)    ");
//			SQL.append(" inner join dbo.gn_sector_lugar s on (s.sector_lugar_id=u.sector_lugar_id) ");
//			SQL.append(" inner join dbo.gn_sector t on (t.sector_id=s.sector_id)   ");
//			SQL.append(" inner join dbo.gn_via v on (v.via_id=u.via_id)  ");
//			SQL.append(" inner join dbo.gn_tipo_via tv on (tv.tipo_via_id=v.tipo_via_id)  ");
//			if (estadoDireccionId == 1) {
//				SQL.append(" and u.ubicacion_id>0 ");
//			} else if (estadoDireccionId == 0) {
//				SQL.append(" and u.ubicacion_id=0 ");
//			}
//			SQL.append(" order by t.descripcion,tv.descripcion,v.descripcion,d.numero,p.persona_id asc ");

			String SQL = "sp_preliminar_RD_arbitrios ?,?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, periodo);
			pst.setInt(2, estadoDireccionId);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FindCcLoteDetalleActo obj = new FindCcLoteDetalleActo();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setNroDocumento(rs.getString("nro_docu_identidad"));
				obj.setDescripcionPersona(rs.getString("apellidos_nombres"));
				obj.setDireccion(rs.getString("domicilio_completo"));
				obj.setSector(rs.getString("sector"));
				obj.setNumero(rs.getString("numero"));
				obj.setTipoVia(rs.getString("tipo_via"));
				obj.setVia(rs.getString("via"));
				obj.setInsoluto(rs.getBigDecimal("total_insoluto"));
				obj.setInteresSimple(rs.getBigDecimal("total_interes_simple"));
				obj.setInteresCapitalizado(rs
						.getBigDecimal("total_interes_capitalizado"));
				obj.setDeudaTotal(rs.getBigDecimal("total_deuda_arbitrios"));

				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public int registrarActoRDArbitrios(Integer lote_id, Integer periodo,
			Double montoMinimo, Integer estadoDireccionId) throws Exception {
		int salida = 0;
		try {
			CallableStatement cs = connect().prepareCall(
					"{call dbo.spt_cc_genera_actoRDArbitrios(?,?,?,?)}");
			cs.setInt(1, lote_id);
			cs.setInt(2, periodo);
			cs.setDouble(3, montoMinimo);
			cs.setInt(4, estadoDireccionId);
			cs.execute();
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
		}
		return salida;
	}

	public List<FindCcLoteDetalleActo> getAllFindCcLoteFinalRDArbitrios(
			Integer lote_id) throws Exception {
		List<FindCcLoteDetalleActo> list = new ArrayList<FindCcLoteDetalleActo>();
		try {
			StringBuffer SQL = new StringBuffer();

			SQL.append(" select p.persona_id,p.nro_docu_identidad,p.apellidos_nombres,pd.domicilio_completo,t.descripcion sector,tv.descripcion tipo_via,v.descripcion via,d.numero, ");
			SQL.append(" sum(l.insoluto) total_insoluto,sum(l.interes_capitalizado) total_interes_capitalizado,sum(l.interes_simple) total_interes_simple,sum(l.total_deuda) total_deuda_arbitrios, ");
			SQL.append(" acto.nro_acto,u.ubicacion_id ");
			SQL.append(" from dbo.mp_persona p ");
			SQL.append(" inner join dbo.cc_acto acto on (acto.persona_id=p.persona_id) ");
			SQL.append(" inner join dbo.mp_persona_domicilio pd on (pd.persona_id=p.persona_id and pd.estado='1') ");
			SQL.append(" inner join dbo.mp_direccion d on (d.persona_id=p.persona_id and pd.direccion_id=pd.direccion_id and d.estado='1') ");
			SQL.append(" inner join dbo.gn_ubicacion u on (u.ubicacion_id=d.ubicacion_id) ");
			SQL.append(" inner join dbo.gn_sector_lugar s on (s.sector_lugar_id=u.sector_lugar_id) ");
			SQL.append(" inner join dbo.gn_sector t on (t.sector_id=s.sector_id) ");
			SQL.append(" inner join dbo.gn_via v on (v.via_id=u.via_id) ");
			SQL.append(" inner join dbo.gn_tipo_via tv on (tv.tipo_via_id=v.tipo_via_id) ");
			SQL.append(" inner join ( ");
			SQL.append(" select ad.persona_id,sum(ad.insoluto) insoluto,sum(ad.interes_capitalizado) interes_capitalizado,sum(ad.interes_simple) interes_simple,sum(ad.total_deuda)total_deuda ");
			SQL.append(" from dbo.cc_acto_deuda ad where ad.concepto_id=3 and ad.lote_id=? ");
			SQL.append(" group by ad.persona_id  ");
			SQL.append(" ) l on (l.persona_id=p.persona_id) ");
			SQL.append(" where p.estado='1' and acto.lote_id=? ");
			SQL.append(" group by p.persona_id,p.nro_docu_identidad,p.apellidos_nombres,pd.domicilio_completo,t.descripcion,tv.descripcion,v.descripcion,acto.nro_acto,d.numero,u.ubicacion_id ");
			SQL.append(" order by t.descripcion,tv.descripcion,v.descripcion,d.numero,p.persona_id ");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, lote_id);
			pst.setInt(2, lote_id);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FindCcLoteDetalleActo obj = new FindCcLoteDetalleActo();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setNroDocumento(rs.getString("nro_docu_identidad"));
				obj.setDescripcionPersona(rs.getString("apellidos_nombres"));
				obj.setDireccion(rs.getString("domicilio_completo"));
				obj.setSector(rs.getString("sector"));
				obj.setTipoVia(rs.getString("tipo_via"));
				obj.setVia(rs.getString("via"));
				obj.setNumero(rs.getString("numero"));
				obj.setInsoluto(rs.getBigDecimal("total_insoluto"));
				obj.setInteresSimple(rs.getBigDecimal("total_interes_simple"));
				obj.setInteresCapitalizado(rs
						.getBigDecimal("total_interes_capitalizado"));
				obj.setDeudaTotal(rs.getBigDecimal("total_deuda_arbitrios"));
				obj.setNroActo(rs.getString("nro_acto"));
				obj.setUbicacionId(rs.getInt("ubicacion_id"));

				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	/**
	 * Esquelas
	 */
	public List<FindCcLoteDetalleActo> getAllFindCcLotePreliminarEsquelasPorVencer(
			Integer periodo, Integer estadoDireccionId, Double montoMinimo)
			throws Exception {
		// corregido
		List<FindCcLoteDetalleActo> list = new ArrayList<FindCcLoteDetalleActo>();
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append(" SELECT m.RowNum,m.persona_id,m.apellidos_nombres,m.tipo_documento,isnull(m.nro_documento,'')nro_documento,m.domicilio_completo,m.sector,m.via,m.tipo_via, ");
			SQL.append(" m.predial,m.vehicular,m.arbitrios,m.total_deuda,m.predial_con_dscto,m.vehicular_con_dscto,m.arbitrios_con_dscto,m.total_deuda_con_dscto ");
			SQL.append(" FROM ( ");
			SQL.append(" select ");
			SQL.append(" ROW_NUMBER()Over(Order by s.descripcion,tv.descripcion,v.descripcion,mp_direc.numero,p.persona_id Asc) As RowNum, ");
			SQL.append(" a.persona_id, p.apellidos_nombres,tdi.descripcion tipo_documento,p.nro_docu_identidad nro_documento, mp_dir.domicilio_completo,s.descripcion sector,v.descripcion via,tv.descripcion tipo_via, ");
			SQL.append(" a.predial, a.vehicular, a.arbitrios,(a.predial+a.vehicular+a.arbitrios) total_deuda, ");
			SQL.append(" a.predial_con_dscto, a.vehicular_con_dscto, a.arbitrios_con_dscto,(a.predial_con_dscto+a.vehicular_con_dscto+a.arbitrios_con_dscto) total_deuda_con_dscto ");
			SQL.append(" from ( ");
			SQL.append(" select ");
			SQL.append(" d2.persona_id, ");
			SQL.append(" COALESCE(SUM(case when d2.concepto_id=1 then isnull(d2.insoluto,0) + isnull(d2.derecho_emision,0) + isnull(d2.reajuste,0) +  isnull(d2.interes_capitalizado,0) + isnull(d2.interes_simple,0) else 0.00 end),0.00) predial, ");
			SQL.append(" COALESCE(SUM(case when d2.concepto_id=2 then isnull(d2.insoluto,0) + isnull(d2.derecho_emision,0) + isnull(d2.reajuste,0) +  isnull(d2.interes_capitalizado,0) + isnull(d2.interes_simple,0) else 0.00 end),0.00) vehicular, ");
			SQL.append(" COALESCE(SUM(case when d2.concepto_id=3 then isnull(d2.insoluto,0) + isnull(d2.derecho_emision,0) + isnull(d2.reajuste,0) +  isnull(d2.interes_capitalizado,0) + isnull(d2.interes_simple,0) else 0.00 end),0.00) arbitrios, ");
			SQL.append(" COALESCE(SUM(case when d2.concepto_id=1 then isnull(d2.insoluto_con_dscto,0) + isnull(d2.derecho_emision,0) + isnull(d2.reajuste,0) +  isnull(d2.interes_capitalizado_con_dscto,0) + isnull(d2.interes_simple_con_dscto,0) else 0.00 end),0.00) predial_con_dscto, ");
			SQL.append(" COALESCE(SUM(case when d2.concepto_id=2 then isnull(d2.insoluto_con_dscto,0) + isnull(d2.derecho_emision,0) + isnull(d2.reajuste,0) +  isnull(d2.interes_capitalizado_con_dscto,0) + isnull(d2.interes_simple_con_dscto,0) else 0.00 end),0.00) vehicular_con_dscto, ");
			SQL.append(" COALESCE(SUM(case when d2.concepto_id=3 then isnull(d2.insoluto_con_dscto,0) + isnull(d2.derecho_emision,0) + isnull(d2.reajuste,0) +  isnull(d2.interes_capitalizado_con_dscto,0) + isnull(d2.interes_simple_con_dscto,0) else 0.00 end),0.00) arbitrios_con_dscto, ");
			SQL.append(" MIN(d2.anho_deuda) as min_anho_deuda ");
			SQL.append(" from dbo.cd_deudas_dscto d2 ");
			SQL.append(" where d2.concepto_id in (1,2,3) ");
			SQL.append(" and ( (d2.anho_deuda=? and d2.nro_cuota IN (SELECT distinct cuota FROM dbo.dt_fecha_vencimiento WHERE periodo=? and concepto_id in (1,2,3) and fecha_vencimiento<=getdate())) or (d2.anho_deuda<?) ) ");
			SQL.append(" group by d2.persona_id ");
			SQL.append(" ) a ");
			SQL.append(" inner join dbo.mp_persona p on a.persona_id = p.persona_id ");
			SQL.append(" inner join dbo.mp_tipo_docu_identidad tdi on (tdi.tipo_doc_identidad_id=p.tipo_doc_identidad_id) ");
			SQL.append(" inner join dbo.mp_persona_domicilio mp_dir on mp_dir.persona_id = a.persona_id ");
			SQL.append(" inner join dbo.mp_direccion mp_direc on mp_direc.direccion_id = mp_dir.direccion_id ");
			SQL.append(" inner join dbo.gn_ubicacion ub on mp_direc.ubicacion_id = ub.ubicacion_id ");
			SQL.append(" inner join dbo.gn_sector_lugar sec_lug on sec_lug.sector_lugar_id = ub.sector_lugar_id ");
			SQL.append(" inner join dbo.gn_sector s on sec_lug.sector_id = s.sector_id ");
			SQL.append(" inner join dbo.gn_via v on (v.via_id=ub.via_id) ");
			SQL.append(" inner join dbo.gn_tipo_via tv on (tv.tipo_via_id=v.tipo_via_id) ");
			if (estadoDireccionId == 1) {
				SQL.append(" where ub.ubicacion_id>0 ");
			} else if (estadoDireccionId == 0) {
				SQL.append(" where ub.ubicacion_id=0 ");
			}
			SQL.append(" and a.min_anho_deuda=? ");
			SQL.append(" ) m  where m.total_deuda>=?  ");
			SQL.append(" ORDER BY m.RowNum ");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, periodo);
			pst.setInt(2, periodo);
			pst.setInt(3, periodo);
			pst.setInt(4, periodo);
			pst.setDouble(5, montoMinimo);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FindCcLoteDetalleActo obj = new FindCcLoteDetalleActo();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setDescripcionPersona(rs.getString("apellidos_nombres"));
				obj.setTipoDocumento(rs.getString("tipo_documento"));
				obj.setNroDocumento(rs.getString("nro_documento"));
				obj.setDireccion(rs.getString("domicilio_completo"));
				obj.setSector(rs.getString("sector"));
				obj.setTotalDeudaPredial(rs.getBigDecimal("predial"));
				obj.setTotalDeudaVehicular(rs.getBigDecimal("vehicular"));
				obj.setTotalDeudaArbitrios(rs.getBigDecimal("arbitrios"));
				obj.setDeudaTotal(rs.getBigDecimal("total_deuda"));
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public List<FindCcLoteDetalleActo> getAllFindCcLotePreliminarEsquelasVencida(
			Integer periodo, Integer estadoDireccionId, Double montoMinimo)
			throws Exception {
		// corregido
		List<FindCcLoteDetalleActo> list = new ArrayList<FindCcLoteDetalleActo>();
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append(" SELECT m.RowNum,m.persona_id,m.apellidos_nombres,m.tipo_documento,isnull(m.nro_documento,'')nro_documento,m.domicilio_completo,m.sector,m.via,m.tipo_via, ");
			SQL.append(" m.predial,m.vehicular,m.arbitrios,m.total_deuda,m.predial_con_dscto,m.vehicular_con_dscto,m.arbitrios_con_dscto,m.total_deuda_con_dscto ");
			SQL.append(" FROM ( ");
			SQL.append(" select ");
			SQL.append(" ROW_NUMBER()Over(Order by s.descripcion,tv.descripcion,v.descripcion,mp_direc.numero,p.persona_id Asc) As RowNum, ");
			SQL.append(" a.persona_id, p.apellidos_nombres,tdi.descripcion tipo_documento,p.nro_docu_identidad nro_documento, mp_dir.domicilio_completo,s.descripcion sector,v.descripcion via,tv.descripcion tipo_via, ");
			SQL.append(" a.predial, a.vehicular, a.arbitrios,(a.predial+a.vehicular+a.arbitrios) total_deuda, ");
			SQL.append(" a.predial_con_dscto, a.vehicular_con_dscto, a.arbitrios_con_dscto,(a.predial_con_dscto+a.vehicular_con_dscto+a.arbitrios_con_dscto) total_deuda_con_dscto ");
			SQL.append(" from ( ");
			SQL.append(" select ");
			SQL.append(" d2.persona_id, ");
			SQL.append(" COALESCE(SUM(case when d2.concepto_id=1 then isnull(d2.insoluto,0) + isnull(d2.derecho_emision,0) + isnull(d2.reajuste,0) +  isnull(d2.interes_capitalizado,0) + isnull(d2.interes_simple,0) else 0.00 end),0.00) predial, ");
			SQL.append(" COALESCE(SUM(case when d2.concepto_id=2 then isnull(d2.insoluto,0) + isnull(d2.derecho_emision,0) + isnull(d2.reajuste,0) +  isnull(d2.interes_capitalizado,0) + isnull(d2.interes_simple,0) else 0.00 end),0.00) vehicular, ");
			SQL.append(" COALESCE(SUM(case when d2.concepto_id=3 then isnull(d2.insoluto,0) + isnull(d2.derecho_emision,0) + isnull(d2.reajuste,0) +  isnull(d2.interes_capitalizado,0) + isnull(d2.interes_simple,0) else 0.00 end),0.00) arbitrios, ");
			SQL.append(" COALESCE(SUM(case when d2.concepto_id=1 then isnull(d2.insoluto_con_dscto,0) + isnull(d2.derecho_emision,0) + isnull(d2.reajuste,0) +  isnull(d2.interes_capitalizado_con_dscto,0) + isnull(d2.interes_simple_con_dscto,0) else 0.00 end),0.00) predial_con_dscto, ");
			SQL.append(" COALESCE(SUM(case when d2.concepto_id=2 then isnull(d2.insoluto_con_dscto,0) + isnull(d2.derecho_emision,0) + isnull(d2.reajuste,0) +  isnull(d2.interes_capitalizado_con_dscto,0) + isnull(d2.interes_simple_con_dscto,0) else 0.00 end),0.00) vehicular_con_dscto, ");
			SQL.append(" COALESCE(SUM(case when d2.concepto_id=3 then isnull(d2.insoluto_con_dscto,0) + isnull(d2.derecho_emision,0) + isnull(d2.reajuste,0) +  isnull(d2.interes_capitalizado_con_dscto,0) + isnull(d2.interes_simple_con_dscto,0) else 0.00 end),0.00) arbitrios_con_dscto, ");
			SQL.append(" MIN(d2.anho_deuda) as min_anho_deuda ");
			SQL.append(" from dbo.cd_deudas_dscto d2 ");
			SQL.append(" where d2.concepto_id in (1,2,3) ");
			SQL.append(" and ( (d2.anho_deuda=? and d2.nro_cuota IN (SELECT distinct cuota FROM dbo.dt_fecha_vencimiento WHERE periodo=? and concepto_id in (1,2,3) and fecha_vencimiento<=getdate())) or (d2.anho_deuda<?) ) ");
			SQL.append(" group by d2.persona_id ");
			SQL.append(" ) a ");
			SQL.append(" inner join dbo.mp_persona p on a.persona_id = p.persona_id ");
			SQL.append(" inner join dbo.mp_tipo_docu_identidad tdi on (tdi.tipo_doc_identidad_id=p.tipo_doc_identidad_id) ");
			SQL.append(" inner join dbo.mp_persona_domicilio mp_dir on mp_dir.persona_id = a.persona_id ");
			SQL.append(" inner join dbo.mp_direccion mp_direc on mp_direc.direccion_id = mp_dir.direccion_id ");
			SQL.append(" inner join dbo.gn_ubicacion ub on mp_direc.ubicacion_id = ub.ubicacion_id ");
			SQL.append(" inner join dbo.gn_sector_lugar sec_lug on sec_lug.sector_lugar_id = ub.sector_lugar_id ");
			SQL.append(" inner join dbo.gn_sector s on sec_lug.sector_id = s.sector_id ");
			SQL.append(" inner join dbo.gn_via v on (v.via_id=ub.via_id) ");
			SQL.append(" inner join dbo.gn_tipo_via tv on (tv.tipo_via_id=v.tipo_via_id) ");
			if (estadoDireccionId == 1) {
				SQL.append(" where ub.ubicacion_id>0 ");
			} else if (estadoDireccionId == 0) {
				SQL.append(" where ub.ubicacion_id=0 ");
			}
			SQL.append(" and a.min_anho_deuda<? ");
			SQL.append(" ) m  where m.total_deuda>=?  ");
			SQL.append(" ORDER BY m.RowNum ");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, periodo);
			pst.setInt(2, periodo);
			pst.setInt(3, periodo);
			pst.setInt(4, periodo);
			pst.setDouble(5, montoMinimo);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FindCcLoteDetalleActo obj = new FindCcLoteDetalleActo();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setDescripcionPersona(rs.getString("apellidos_nombres"));
				obj.setTipoDocumento(rs.getString("tipo_documento"));
				obj.setNroDocumento(rs.getString("nro_documento"));
				obj.setDireccion(rs.getString("domicilio_completo"));
				obj.setSector(rs.getString("sector"));

				obj.setTotalDeudaPredial(rs.getBigDecimal("predial"));
				obj.setTotalDeudaVehicular(rs.getBigDecimal("vehicular"));
				obj.setTotalDeudaArbitrios(rs.getBigDecimal("arbitrios"));
				obj.setDeudaTotal(rs.getBigDecimal("total_deuda"));

				obj.setTotalDeudaPredialDcto(rs
						.getBigDecimal("predial_con_dscto"));
				obj.setTotalDeudaVehicularDcto(rs
						.getBigDecimal("vehicular_con_dscto"));
				obj.setTotalDeudaArbitriosDcto(rs
						.getBigDecimal("arbitrios_con_dscto"));
				obj.setDeudaTotalDcto(rs.getBigDecimal("total_deuda_con_dscto"));

				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public List<CarteraItemDTO> obtenerCarteraItems(Integer carteraDeudaId)
			throws SisatException {

		List<CarteraItemDTO> listCarteraItemDTOs = new ArrayList<CarteraItemDTO>();
		try {
			String SQL = "stp_cc_reporte_deuda_cartera ?";
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, carteraDeudaId);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				CarteraItemDTO carteraItemDTO = new CarteraItemDTO();

				carteraItemDTO.setPersonaId(rs.getInt("persona_id"));
				carteraItemDTO.setAnhoDeuda(rs.getInt("anho_deuda"));
				carteraItemDTO
						.setDeterminacionId(rs.getInt("determinacion_id"));
				carteraItemDTO.setInsoluto(rs.getBigDecimal("insoluto"));
				carteraItemDTO.setReajuste(rs.getBigDecimal("reajuste"));
				carteraItemDTO.setIntereses(rs.getBigDecimal("intereses"));
				carteraItemDTO.setTotalDeuda(rs.getBigDecimal("total_deuda"));
				carteraItemDTO.setTotalCancelado(rs
						.getBigDecimal("total_cancelado"));

				listCarteraItemDTOs.add(carteraItemDTO);
			}
		} catch (Exception e) {
			throw new SisatException(e.getMessage());
		}

		return listCarteraItemDTOs;
	}

	/**
	 * Reclamos
	 */
	public List<FindCcActo> getAllFindCcActoReclamos(String nroActo,
			Integer personaId) throws Exception {
		List<FindCcActo> list = new ArrayList<FindCcActo>();
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append(" SELECT a.acto_id,a.lote_id,a.nro_acto,a.persona_id,p.apellidos_nombres,n.fecha_notificacion,a.fecha_emision,a.fecha_cancelacion,a.monto_deuda,");
			SQL.append(" ta.descripcion tipo_acto,c.descripcion concepto,a.anno_acto,r.fecha_reclamo,isnull(r.reclamo_id,0) reclamo_id, ");
			SQL.append(" isnull(r.nro_documento,'') nro_documento,isnull(r.estado_reclamo_id,-1) estado_reclamo_id ");
			SQL.append(" FROM dbo.cc_acto a   ");
			SQL.append(" inner join dbo.cc_tipo_acto ta on (a.tipo_acto_id=ta.tipo_acto_id) ");
			SQL.append(" inner join dbo.gn_concepto c on (a.concepto_id=c.concepto_id)  ");
			SQL.append(" inner join dbo.gn_persona p on (p.persona_id=a.persona_id)  ");
			SQL.append(" left join dbo.no_notificacion n on (a.nro_cargo_notificacion=n.notificacion_id) ");
			SQL.append(" left join dbo.cc_reclamo r on (r.acto_id=a.acto_id) ");
			if (nroActo != null && nroActo.trim().length() > 0) {
				SQL.append(" WHERE a.nro_acto=? ");
			}
			if (personaId != null && personaId > 0) {
				SQL.append(" WHERE a.persona_id=? ");
			}
			SQL.append(" ORDER BY a.acto_id ASC ");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			if (nroActo != null && nroActo.trim().length() > 0) {
				pst.setString(1, nroActo);
			}
			if (personaId != null && personaId > 0) {
				pst.setInt(1, personaId);
			}

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				FindCcActo obj = new FindCcActo();
				obj.setActoId(rs.getInt("acto_id"));
				obj.setLoteId(rs.getInt("lote_id"));
				obj.setNroActo(rs.getString("nro_acto"));
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setFechaNotificacion(rs.getTimestamp("fecha_notificacion"));
				obj.setFechaEmision(rs.getTimestamp("fecha_emision"));
				obj.setFechaCancelacion(rs.getTimestamp("fecha_cancelacion"));
				obj.setMontoDeuda(rs.getBigDecimal("monto_deuda"));
				obj.setTipoActo(rs.getString("tipo_acto"));
				obj.setConcepto(rs.getString("concepto"));
				obj.setAnnoActo(rs.getInt("anno_acto"));
				obj.setApellidosNombres(rs.getString("apellidos_nombres"));
				obj.setFechaReclamo(rs.getTimestamp("fecha_reclamo"));
				obj.setReclamoId(rs.getInt("reclamo_id"));
				// --
				obj.setNroDocumentoReclamo(rs.getString("nro_documento"));
				obj.setEstadoReclamoId(rs.getInt("estado_reclamo_id"));

				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public void actualizarActo(String esReclamado, CcReclamo reclamo)
			throws Exception {
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append("UPDATE dbo.cc_acto SET flag_reclamado=? WHERE acto_id=? AND persona_id=?");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setString(1, esReclamado);
			pst.setInt(2, reclamo.getActoId());
			pst.setInt(3, reclamo.getPersonaId());
			pst.executeUpdate();
		} catch (Exception e) {
			throw (e);
		}
	}

	public void actualizarDeuda(String esReclamado, CcReclamo reclamo)
			throws Exception {
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append(" update dbo.cd_deuda set flag_reclamado=? where deuda_id IN ( ");
			SQL.append(" 	select deuda_id from dbo.cc_acto_deuda where acto_id=? and persona_id=? ");
			SQL.append(" )and persona_id=?");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setString(1, esReclamado);
			pst.setInt(2, reclamo.getActoId());
			pst.setInt(3, reclamo.getPersonaId());
			pst.setInt(4, reclamo.getPersonaId());
			pst.executeUpdate();
		} catch (Exception e) {
			throw (e);
		}
	}
	/**
	 * Generación de RD's Vehiculares
	 */
	
	public List<FindCcLoteDetalleActo> getAllFindCcLotePreliminarRDVehicular(Integer loteId)throws Exception {
		List<FindCcLoteDetalleActo> list = new ArrayList<FindCcLoteDetalleActo>();
		try {
			String SQL = new String("stp_rv_obtener_lote_preliminarRD_vehicular ?");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, loteId);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FindCcLoteDetalleActo obj = new FindCcLoteDetalleActo();
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setNroDocumento(rs.getString("nro_docu_identidad"));
				obj.setDescripcionPersona(rs.getString("apellidos_nombres"));
				obj.setDireccion(rs.getString("domicilio_completo"));
				obj.setSector(rs.getString("sector"));
				obj.setNumero(rs.getString("numero"));
				obj.setTipoVia(rs.getString("tipo_via"));
				obj.setVia(rs.getString("via"));
				obj.setInsoluto(rs.getBigDecimal("total_insoluto"));
				obj.setDerechoEmision(rs.getBigDecimal("total_emision"));
				obj.setReajuste(rs.getBigDecimal("total_reajuste"));
				obj.setInteresCapitalizado(rs.getBigDecimal("total_interes_capitalizado"));
				obj.setDeudaTotal(rs.getBigDecimal("total_deuda_vehicular"));
			
			
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}
	
	public int registrarActoRDVehicular(Integer lote_id) throws Exception {
		int salida = 0;
		try {
			CallableStatement cs = connect().prepareCall(
					"{call dbo.spt_cc_genera_actoRDVehicular(?)}");
			cs.setInt(1, lote_id);
			cs.execute();
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
		}
		return salida;
	}

	public List<FindCcLoteDetalleActo> getAllFindCcLoteFinalRDVehicular(Integer lote_id) throws Exception {
		List<FindCcLoteDetalleActo> list = new ArrayList<FindCcLoteDetalleActo>();
		try {
			StringBuffer SQL = new StringBuffer();

			SQL.append(" select p.persona_id,p.nro_docu_identidad,p.apellidos_nombres,pd.domicilio_completo,t.descripcion sector,tv.descripcion tipo_via,v.descripcion via,d.numero, ");
			SQL.append(" sum(l.insoluto) total_insoluto,sum(l.derecho_emision)total_emision,sum(l.reajuste)total_reajuste,sum(l.interes_capitalizado) total_interes_capitalizado,sum(l.interes_simple) total_interes_simple,sum(l.total_deuda) total_deuda_vehicular, ");
			SQL.append(" acto.nro_acto,u.ubicacion_id ");
			SQL.append(" from dbo.mp_persona p ");
			SQL.append(" inner join dbo.cc_acto acto on (acto.persona_id=p.persona_id) ");
			SQL.append(" inner join dbo.mp_persona_domicilio pd on (pd.persona_id=p.persona_id and pd.estado='1') ");
			SQL.append(" inner join dbo.mp_direccion d on (d.persona_id=p.persona_id and pd.direccion_id=pd.direccion_id and d.estado='1') ");
			SQL.append(" inner join dbo.gn_ubicacion u on (u.ubicacion_id=d.ubicacion_id) ");
			SQL.append(" inner join dbo.gn_sector_lugar s on (s.sector_lugar_id=u.sector_lugar_id) ");
			SQL.append(" inner join dbo.gn_sector t on (t.sector_id=s.sector_id) ");
			SQL.append(" inner join dbo.gn_via v on (v.via_id=u.via_id) ");
			SQL.append(" inner join dbo.gn_tipo_via tv on (tv.tipo_via_id=v.tipo_via_id) ");
			SQL.append(" inner join ( ");
			SQL.append(" select ad.persona_id,sum(ad.insoluto) insoluto,sum(ad.interes_capitalizado) interes_capitalizado,sum(ad.interes_simple) interes_simple,sum(ad.total_deuda)total_deuda,");
			SQL.append(" sum(ad.derecho_emision)derecho_emision,sum(ad.reajuste)reajuste  from dbo.cc_acto_deuda ad where ad.concepto_id=2 and ad.lote_id=? ");
			SQL.append(" group by ad.persona_id  ");
			SQL.append(" ) l on (l.persona_id=p.persona_id) ");
			SQL.append(" where p.estado='1' and acto.lote_id=? ");
			SQL.append(" group by p.persona_id,p.nro_docu_identidad,p.apellidos_nombres,pd.domicilio_completo,t.descripcion,tv.descripcion,v.descripcion,acto.nro_acto,d.numero,u.ubicacion_id ");
			SQL.append(" order by t.descripcion,tv.descripcion,v.descripcion,d.numero,p.persona_id ");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, lote_id);
			pst.setInt(2, lote_id);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FindCcLoteDetalleActo obj = new FindCcLoteDetalleActo();
				
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setNroDocumento(rs.getString("nro_docu_identidad"));
				obj.setDescripcionPersona(rs.getString("apellidos_nombres"));
				obj.setDireccion(rs.getString("domicilio_completo"));
				obj.setSector(rs.getString("sector"));
				obj.setTipoVia(rs.getString("tipo_via"));
				obj.setVia(rs.getString("via"));
				obj.setNumero(rs.getString("numero"));
				obj.setInsoluto(rs.getBigDecimal("total_insoluto"));
				obj.setDerechoEmision(rs.getBigDecimal("total_emision"));
				obj.setReajuste(rs.getBigDecimal("total_reajuste"));
				obj.setInteresCapitalizado(rs.getBigDecimal("total_interes_capitalizado"));
				obj.setDeudaTotal(rs.getBigDecimal("total_deuda_vehicular"));
				obj.setNroActo(rs.getString("nro_acto"));
				obj.setUbicacionId(rs.getInt("ubicacion_id"));

				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public int registrarActoRDVehicularIndividual(Integer loteId,Integer personaId,Integer reqId,Integer determinacionId) throws Exception {
		int salida = 0;
		try {
			CallableStatement cs = connect().prepareCall(
					"{call dbo.spt_cc_genera_actoRDVehicular_x_Contribuyente(?,?,?,?)}");
			cs.setInt(1, loteId);
			cs.setInt(2, personaId);
			cs.setInt(3, reqId);
			cs.setInt(4, determinacionId);
			
			cs.execute();
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
		}
		return salida;
	}
	
	public List<FindCcLote> getAllFindCcLoteRDVehicular() throws Exception {
		List<FindCcLote> list = new ArrayList<FindCcLote>();
		StringBuffer sqld = new StringBuffer("");
		try {
			sqld.append(" select l.lote_id,l.tipo_lote_id,l.anno_lote,l.fecha_registro,CONVERT(time(0),l.fecha_registro) hora_registro, ");
			sqld.append(" tl.descripcion tipo_lote,ta.descripcion tipo_acto,ta.tipo_acto_id,a.agrupado_bien,a.agrupado_acto,a.fecha_vencimiento, a.agrupado_cuota, ");
			sqld.append(" case when l.tipo_lote_generacion='1' then 'Preliminar'  when l.tipo_lote_generacion='2' then 'Final' end estado_tipo_lote_generacion, ");
			sqld.append(" case when l.flag_generacion='1' then 'Programado'  when l.flag_generacion='2' then 'Finalizado' end estado_generacion, ");
			sqld.append(" l.tipo_lote_generacion,l.flag_generacion, ");
			sqld.append(" lc.concepto_id,c.descripcion concepto, ");
			sqld.append(" r.monto_deuda,r.total_registros ");
			sqld.append(" FROM dbo.cc_lote l ");
			sqld.append(" inner join dbo.cc_tipo_lote tl on (tl.tipo_lote_id=l.tipo_lote_id  ) ");
			sqld.append(" inner join dbo.cc_lote_acto a on (a.lote_id=l.lote_id  and a.estado='1') ");
			sqld.append(" inner join dbo.cc_tipo_acto ta on ta.tipo_acto_id=a.tipo_acto_id ");
			sqld.append(" inner join dbo.cc_lote_concepto lc on l.lote_id=lc.lote_id and a.lote_acto_id=lc.lote_acto_id ");
			sqld.append(" inner join dbo.gn_concepto c on c.concepto_id=lc.concepto_id ");
			sqld.append(" left join (select a.lote_id,sum(a.monto_deuda) monto_deuda,count(1) total_registros from dbo.cc_acto a group by a.lote_id) r on (r.lote_id=l.lote_id) ");
			sqld.append(" where l.estado='1' and lc.concepto_id=2 and ta.tipo_acto_id=4 ");
			sqld.append(" order by l.lote_id desc ");

			PreparedStatement pst = connect().prepareStatement(sqld.toString());
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
				obj.setAgrupacionBien(rs.getString("agrupado_bien"));
				obj.setAgrupacionActo(rs.getString("agrupado_acto"));
				obj.setFechaVencimiento(rs.getTimestamp("fecha_vencimiento"));
				obj.setAgrupacionCuota(rs.getString("agrupado_cuota"));
				obj.setEstadoTipoLoteGeneracion(rs
						.getString("estado_tipo_lote_generacion"));
				obj.setEstadoGeneracion(rs.getString("estado_generacion"));
				obj.setTipo_lote_generacion(rs
						.getString("tipo_lote_generacion"));
				obj.setFlag_generacion(rs.getString("flag_generacion"));
				obj.setConceptoId(rs.getInt("concepto_id"));
				obj.setConcepto(rs.getString("concepto"));
				obj.setTotalDeuda(rs.getBigDecimal("monto_deuda"));
				obj.setNroDocumentos(rs.getInt("total_registros"));
				// --
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}
	
	/**
	 * -=CRAMIREZ=-
	 */
	public List<FindPersonaDescargo> getAllPersonaDescargo(Integer persona_id, Date fecha_Inicio, Date fecha_Fin, int tipo)
			throws Exception {
		List<FindPersonaDescargo> list = new ArrayList<FindPersonaDescargo>();
		
		StringBuffer sqld = new StringBuffer("");
		try {
			sqld.append("{ call usp_list_lotes_persona_descargos(?,?,?,?) }");
			CallableStatement pst = connect().prepareCall(sqld.toString());
              if(persona_id == null){persona_id=0;}
              
              java.sql.Timestamp fecInicio = null;
      			java.sql.Timestamp fecFin = null;
      			
      			fecInicio = new java.sql.Timestamp(DateUtil.moverHoraAlInicioDelDia(fecha_Inicio).getTime());
    			fecFin = new java.sql.Timestamp(DateUtil.moverHoraAlFinalDelDia(fecha_Fin).getTime());
    			
              pst.setInt(1, persona_id);
			  pst.setTimestamp(2, fecInicio);
			  pst.setTimestamp(3, fecFin);
			  pst.setInt(4, tipo);
			  ResultSet rs = pst.executeQuery();
			  
			  

			  
			while (rs.next()) {
				FindPersonaDescargo obj = new FindPersonaDescargo();
				obj.setCodigo(rs.getInt("persona_id"));
				obj.setContribuyente(rs.getString("contribuyente"));
				obj.setNombre_usuario(rs.getString("nombre_usuario"));
				obj.setDescripcionEstado(rs.getString("descripcion_estado"));
				obj.setEstado(rs.getInt("estado"));
				obj.setFecha_registro(rs.getTimestamp("fecha_registro"));
				obj.setUsuario_id(rs.getInt("usuario_id"));
				obj.setDias(rs.getInt("dias"));
				
				list.add(obj);
			}
		} catch (Exception e) {
			System.out.println("Error : "+e.getMessage());
			throw (e); 
		}
		return list;
	}
	
	
	public List<FindLoteDescargo> getAllLoteDescargo(Integer codigo, Integer usuario_id, Date fecha_registro)
			throws Exception {
		List<FindLoteDescargo> list = new ArrayList<FindLoteDescargo>();
		
		StringBuffer sqld = new StringBuffer("");
		try {
			sqld.append("{ call usp_list_lotes_descargos(?,?,?) }");
			CallableStatement pst = connect().prepareCall(sqld.toString());
              if(codigo == null){codigo=0;}
              if(usuario_id == null){usuario_id=0;}
              java.sql.Timestamp fechRegistro = null;
      			
      			fechRegistro = new java.sql.Timestamp(DateUtil.moverHoraAlInicioDelDia(fecha_registro).getTime());
    			
              pst.setInt(1, codigo);
			  pst.setInt(2, usuario_id);
			  pst.setTimestamp(3, fechRegistro);
			  ResultSet rs = pst.executeQuery();
			  
			  

			  
			while (rs.next()) {
				FindLoteDescargo obj = new FindLoteDescargo();
				obj.setLote_descargo_id(rs.getInt("lote_descargo_id"));
				obj.setTipo_descargo_id(rs.getInt("tipo_descargo_id"));
				obj.setNro_lote(rs.getInt("nro_lote"));
				obj.setEstado(rs.getInt("estado"));
				obj.setMonto_total_descargo_preliminar(rs.getBigDecimal("monto_total_descargo_preliminar"));
				obj.setMonto_total_descargado(rs.getBigDecimal("monto_total_descargado"));	
				obj.setUsuario_id(rs.getInt("usuario_id"));
				obj.setTerminal(rs.getString("terminal"));
				obj.setFecha_registro(rs.getTimestamp("fecha_registro"));
				obj.setUsuario_upd_id(rs.getInt("usuario_upd_id"));
				obj.setTerminal_upd_id(rs.getString("terminal_upd_id"));
				obj.setFecha_upd(rs.getTimestamp("fecha_upd"));
				obj.setTipo_descargo(rs.getString("tipo_descargo"));
				obj.setDescripcion_estado(rs.getString("descripcion_estado"));
				obj.setNombre_usuario(rs.getString("nombre_usuario"));	
				obj.setNombre_usuario_valido(rs.getString("nombre_usuario_valido"));
				obj.setPersona_id(rs.getInt("persona_id"));
				obj.setApellidos_nombres(rs.getString("apellidos_nombres"));
				
				obj.setNombre_usuario_rev(rs.getString("nombre_usuario_revierte"));
				obj.setFecha_rev(rs.getTimestamp("fecha_rev"));
				obj.setTerminal_rev_id(rs.getString("terminal_rev_id"));
				obj.setObservaciones(rs.getString("observaciones"));
				
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}
	
	
	public List<FindDetalleLoteDescargo> getAllDetalleLoteDescargo(Integer lote_descargo_id)
			throws Exception {
		List<FindDetalleLoteDescargo> list = new ArrayList<FindDetalleLoteDescargo>();
		
		StringBuffer sqld = new StringBuffer("");
		try {
			sqld.append("{ call usp_list_detalle_lotes_descargos(?) }");
			CallableStatement pst = connect().prepareCall(sqld.toString());
      			
    			
              pst.setInt(1, lote_descargo_id);
			  ResultSet rs = pst.executeQuery();
			  
			  

			  
			while (rs.next()) {
				FindDetalleLoteDescargo obj = new FindDetalleLoteDescargo();
				
				obj.setDescargo_id(rs.getInt("descargo_deuda_id"));
				obj.setTipo_descargo(rs.getInt("tipo_descargo"));
				obj.setTipo_documento_id(rs.getInt("tipo_documento_id"));
				obj.setNro_documento(rs.getString("nro_documento"));
				obj.setFecha_documento(rs.getTimestamp("fecha_documento"));
				obj.setObservacion(rs.getString("observacion"));				
				obj.setTotal_descargado(rs.getBigDecimal("total_descargado"));				
				obj.setDeuda_id(rs.getInt("deuda_id"));				
				obj.setInteres(rs.getBigDecimal("interes"));				
				obj.setReajuste(rs.getBigDecimal("reajuste"));				
				obj.setTotal_deuda(rs.getBigDecimal("total_deuda"));				
				obj.setFecha_registro_deuda(rs.getTimestamp("fecha_registro_deuda"));				
				obj.setEstado(rs.getString("estado"));				
				obj.setUsuario_id(rs.getInt("usuario_id"));				
				obj.setFecha_registro(rs.getTimestamp("fecha_registro"));
				obj.setTerminal(rs.getString("terminal"));
				obj.setPersona_id(rs.getInt("persona_id"));
				obj.setPersona__transfire_id(rs.getInt("persona__transfire_id"));
				obj.setRecibo_id(rs.getString("recibo_id"));
				obj.setLote_descargo_id(rs.getInt("lote_descargo_id"));
				obj.setConcepto(rs.getString("concepto"));
				obj.setSub_concepto(rs.getString("sub_concepto"));
				obj.setNro_cuota(rs.getInt("nro_cuota"));
				obj.setAnno_deuda(rs.getInt("anno_deuda"));
				obj.setInsoluto(rs.getBigDecimal("insoluto"));	
				obj.setDescripcion_tipo_documento(rs.getString("descripcion_tipo_documento"));
				obj.setDescripcion_tipo_descargo(rs.getString("descripcion_tipo_descargo"));
				obj.setContribuyente(rs.getString("contribuyente"));
				obj.setUsuario(rs.getString("usuario"));
				obj.setNro_lote(rs.getInt("nro_lote"));
				obj.setEstado_descargo(rs.getString("estado_descargo"));
				obj.setIsSelect(false);
				
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}

	public void confirmarDescargo(Integer lote_descargo_id, Integer usuario_id,String terminal, String deudas_id, Integer tipo_operacion, Integer personaId, Date fecha, Integer usuarioLote, String observaciones)
			throws Exception {
		
		StringBuffer sqld = new StringBuffer("");
		try {
			sqld.append("{ call usp_descargar_deuda_lotes_descargos(?,?,?,?,?,?,?,?,?) }");
			CallableStatement pst = connect().prepareCall(sqld.toString());   
			  java.sql.Timestamp fechRegistro = null;
			  fechRegistro = new java.sql.Timestamp(DateUtil.moverHoraAlInicioDelDia(fecha).getTime());
    			
              pst.setInt(1, lote_descargo_id);
              pst.setInt(2, usuario_id);
              pst.setString(3, terminal);
              pst.setString(4, deudas_id);
              pst.setInt(5, tipo_operacion);
              pst.setInt(6, personaId);
              pst.setTimestamp(7, fechRegistro);
              pst.setInt(8, usuarioLote);
              pst.setString(9, observaciones);
			   pst.execute();
			  
			  

		} catch (Exception e) {
			String msg = "No se ha podido validar. ".concat(e
					.getMessage());
			throw new SisatException(msg);
	
		}
	}
	
	
	public List<ReporteNotificacionDTO> getAllNotificaciones(Date fechaInicio, Date fechaFin)
			throws Exception {
		
		List<ReporteNotificacionDTO> list = new ArrayList<ReporteNotificacionDTO>();
		
		StringBuffer sqld = new StringBuffer("");
		try {
			
			java.sql.Timestamp fecInicio = null;
  			java.sql.Timestamp fecFin = null;
  			
  			
			fecInicio = new java.sql.Timestamp(DateUtil.moverHoraAlInicioDelDia(fechaInicio).getTime());
			fecFin = new java.sql.Timestamp(DateUtil.moverHoraAlFinalDelDia(fechaFin).getTime());
			
			sqld.append("{ call stp_genera_reporte_notificaciones(?,?) }");
			CallableStatement pst = connect().prepareCall(sqld.toString());	
				pst.setTimestamp(1, fecInicio);
				pst.setTimestamp(2, fecFin);
              
			  ResultSet rs = pst.executeQuery();
			  
			while (rs.next()) {
				ReporteNotificacionDTO obj = new ReporteNotificacionDTO();
				
				obj.setNotificador(rs.getString("apellidos_nombres"));
				obj.setTipoNotificacion(rs.getString("forma_notificacion"));
				obj.setTipoValor(rs.getString("tipo"));
				obj.setNroValor(rs.getString("nro_valor"));
				obj.setFechaNotificacion(rs.getTimestamp("fecha_notificacion"));
				obj.setFechaRegistro(rs.getTimestamp("fecha_registro"));
				obj.setUsuarioRegistro(rs.getString("nombre_usuario"));				
				
				
				list.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return list;
	}
}