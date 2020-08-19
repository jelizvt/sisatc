package com.sat.sisat.determinacion.vehicular.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.sat.sisat.common.dao.GeneralDao;
import com.sat.sisat.common.security.UserSession;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.SATParameterFactory;
import com.sat.sisat.determinacion.vehicular.dto.DatosDeterminacionValoresDTO;
import com.sat.sisat.determinacion.vehicular.dto.DatosExisteDeterminacionDTO;
import com.sat.sisat.determinacion.vehicular.dto.DatosInafecDTO;
import com.sat.sisat.determinacion.vehicular.dto.DatosNecesariosDeterDTO;
import com.sat.sisat.determinacion.vehicular.dto.DeudaPagosDTO;
import com.sat.sisat.determinacion.vehicular.dto.DeudaValoresDTO;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.persistence.entity.CdDeuda;
import com.sat.sisat.persistence.entity.CdDeudaHistorica;
import com.sat.sisat.persistence.entity.DtDeterminacion;
import com.sat.sisat.persistence.entity.DtDeterminacionValor;
import com.sat.sisat.persistence.entity.DtDeterminacionVehicular;
import com.sat.sisat.persistence.entity.DtTasaVehicular;

public class DeterminacionVehicularDao extends GeneralDao {

	/**
	 * Obtiene el monto que publica el MEF anualmente para cagegoria,marca y
	 * modelo de vehículo
	 * 
	 * @param modeloVehiculoId
	 *            Modelo de vehículo.
	 * @param periodoAfectacion
	 *            Año de afectación.
	 * @param periodoFabricacion
	 *            Año de fabricación del vehículo.
	 * @return
	 */
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
			int periodoAfectacion) throws SisatException {
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
			if(monto==null){
				throw new SisatException("No se puede calcular porque no existe valor ajustado."+
						(new StringBuffer()).append("Verificar valor referencial para categoría ").append(categoriaId)
						.append(" , marca ").append(marcaVehiculoId)
						.append(" y modelo ").append(modeloVehiculoId)
						.append(" para el vehículo en el año de afectacion ").append(periodoAfectacion)
						.append(" y año fabricacion ").append(periodoAfectacion - 1).toString());
			}
			return monto;
		}catch(SisatException ex){
			throw ex;
		}
		catch (Exception ex) {
			// TODO : Controller Exception
			System.out.println("ERROR: " + ex);
			
		}
		return null;
	}

	/**
	 * Obtiene los datos necesarios para generar la determinación vehicular
	 * 
	 * @param djvehicularId
	 *            Identificador de la declaración jurada
	 * @return
	 * @throws SisatException 
	 */
	public DatosNecesariosDeterDTO getDatosNecesariosDeterminar(
			int djvehicularId) throws SisatException {
		try {
			StringBuilder SQL = new StringBuilder(
					"SELECT djv.categoria_vehiculo_id, djv.marca_vehiculo_id, djv.modelo_vehiculo_id, ");
			SQL.append("djv.anno_ini_afectacion, djv.anno_fin_afectacion, djv.anno_afectacion, YEAR(fecha_adquisicion) as anho_adquisicion, ");
			SQL.append("ve.anno_fabricacion, djv.val_adq_soles, djv.val_adq_otra_moneda, djv.porc_propiedad, djv.persona_id, ");
			SQL.append("djv.tipo_moneda_id, djv.djvehicular_previo_id, ");
			SQL.append("CASE WHEN MONTH(djv.fecha_ins_registros)=1 AND DAY(djv.fecha_ins_registros)=1 THEN YEAR(djv.fecha_ins_registros) ELSE YEAR(djv.fecha_ins_registros)+1 END AS anno_ini_afec_contrib, ");
			SQL.append("djv.rv_motivo_declaracion_id, ");
			SQL.append("djv.vehiculo_id, ");
			SQL.append("case isnull(djv.fiscalizado,0)when 0 then 0 else djv.fiscalizado end as fiscalizado, ");
			SQL.append("case isnull(djv.fisca_aceptada,0)when 0 then 0 else djv.fisca_aceptada end as fisca_aceptada, ");
			SQL.append("case isnull(djv.fisca_cerrada,0)when 0 then 0 else djv.fisca_cerrada end as fisca_cerrada ");
			SQL.append("FROM ").append(Constante.schemadb).append(".rv_djvehicular djv ");
			SQL.append("INNER JOIN ").append(Constante.schemadb).append(".rv_vehiculo ve ON ve.vehiculo_id=djv.vehiculo_id ");
			SQL.append("WHERE djv.djvehicular_id=").append(djvehicularId);

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
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
				
				datos.setFiscalizado(rs.getString("fiscalizado"));
				datos.setFiscalizadoAceptado(rs.getString("fisca_aceptada"));
				datos.setFiscalizadoCerrado(rs.getString("fisca_cerrada"));
			}
			return datos;
		} catch (Exception ex) {
			throw new SisatException(ex.getMessage());
		}		
	}

	/**
	 * Obtiene los datos de inafactación por condiciones especiales del
	 * contribuyente
	 * 
	 * @param contribId
	 *            Identificador del contribuyente.
	 * @param periodo
	 *            Año de afectación.
	 * @return
	 */
	public DatosInafecDTO getInafecContrib(int contribId, int periodo) {
		try {
			StringBuilder SQL = new StringBuilder("SELECT ce.fecha_inicio, ce.fecha_fin, cc.tipo, cc.valor FROM ").append(Constante.schemadb).append(".gn_condicion_especial ce ");
			SQL.append("INNER JOIN ").append(Constante.schemadb).append(".dt_condi_especial_contri cc ON cc.tipo_cond_especial_id = ce.tipo_cond_especial_id ");
			SQL.append("WHERE ce.persona_id=").append(contribId);
			SQL.append(" AND ce.estado='1' AND ce.tipo_cond_especial_id IN ");
			SQL.append("(SELECT cec.tipo_cond_especial_id FROM ").append(Constante.schemadb).append(".dt_condi_especial_contri cec WHERE cec.periodo=");
				SQL.append(periodo).append(" AND cec.concepto_id=").append(Constante.CONCEPTO_VEHICULAR + ")");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();

			DatosInafecDTO datos = null;
			while (rs.next()) {
				datos = new DatosInafecDTO();
				datos.setFechaInicio(rs.getDate("fecha_inicio"));
				datos.setFechaFin(rs.getDate("fecha_fin"));
				datos.setTipo(rs.getString("tipo"));
				datos.setValor(rs.getBigDecimal("valor"));
			}
			return datos;
		} catch (Exception ex) {
			// TODO : Controller Exception
			System.out.println("ERROR: " + ex);
		}
		return null;
	}

	/**
	 * Obtiene los datos de inafectación por condiciones especiales del
	 * vehículo.
	 * 
	 * @param djvehicularId
	 *            Identificador de la declaración jurada.
	 * @return
	 */
	public DatosInafecDTO getInafecVehic(int djvehicularId) {
		try {
			StringBuilder SQL = new StringBuilder(
					"SELECT dcv.fecha_inicial, dcv.fecha_final, dcv.tipo_inafectacion, dcv.valor_inafectacion FROM ").append(Constante.schemadb).append(".rv_det_cond_vehiculo dcv ");
			SQL.append("WHERE dcv.djvehicular_id=").append(djvehicularId);

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();

			DatosInafecDTO datos = null;
			while (rs.next()) {
				datos = new DatosInafecDTO();
				datos.setFechaInicio(rs.getDate("fecha_inicial"));
				datos.setFechaFin(rs.getDate("fecha_final"));
				datos.setTipo(rs.getString("tipo_inafectacion"));
				datos.setValor(rs.getBigDecimal("valor_inafectacion"));
			}
			return datos;
		} catch (Exception ex) {
			// TODO : Controller Exception
			System.out.println("ERROR: " + ex);
		}
		return null;
	}

	/**
	 * Obtiene tasa vehicular e impuesto mínimo
	 * 
	 * @param anioAfec
	 *            Año de afectación
	 * @return
	 */
	public DtTasaVehicular getTasaVehicular(Integer anioAfec) {
		try {
			//return find(anioAfec, DtTasaVehicular.class);
			return find(anioAfec, DtTasaVehicular.class);
		} catch (Exception ex) {
			// TODO : Controller Exception
			System.out.println("ERROR: " + ex);
		}
		return null;
	}

	/**
	 * Obtiene datos basicos de una determinación que existe.
	 * 
	 * @param conceptoId
	 *            Identificador del concepto al que efecta la determinación
	 *            (Predial, Vehicular, ...)
	 * @param djReferenciaId
	 *            Identificador de la declaración jurada dependiendo del
	 *            concepto.
	 * @return
	 */
	public DatosExisteDeterminacionDTO getDatosExisteDeterminacion(
			int conceptoId, int djReferenciaId) {
		try {
			StringBuilder SQL = new StringBuilder(
					"SELECT dt.determinacion_id,dt.base_imponible,dt.base_exonerada,dt.base_afecta,dt.impuesto,dt.estado FROM ").append(Constante.schemadb).append(".dt_determinacion dt ");
			SQL.append("WHERE dt.concepto_id=" ).append( conceptoId).append(" AND dt.djreferencia_id=" ).append( djReferenciaId).append(" AND dt.estado='1'");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();

			DatosExisteDeterminacionDTO datos = null;
			while (rs.next()) {
				datos = new DatosExisteDeterminacionDTO();
				datos.setDeterminacionId(rs.getInt("determinacion_id"));
				datos.setBaseImponible(rs.getBigDecimal("base_imponible"));
				datos.setBaseExonerada(rs.getBigDecimal("base_exonerada"));
				datos.setBaseAfecta(rs.getBigDecimal("base_afecta"));
				datos.setImpuesto(rs.getBigDecimal("impuesto"));
				datos.setEstado(rs.getString("estado"));
			}
			return datos;
		} catch (Exception ex) {
			// TODO : Controller Exception
			System.out.println("ERROR: " + ex);
		}
		return null;
	}

	/**
	 * Guarda la determinación en la base de datos. Con la condicion que no
	 * tienen declaraciones previas o si tienen pero la previa no tiene
	 * determinación.
	 * 
	 * @param deter
	 *            Determinación
	 * @return PK de determinación
	 */
	public int guardarDeterminacion(DtDeterminacion deter) {
		try {
			int deterId = ObtenerCorrelativoTabla(
					"dt_determinacion", 1);

			StringBuilder SQL = new StringBuilder(
					"INSERT INTO ").append(Constante.schemadb).append(".dt_determinacion(determinacion_id,persona_id,anno_determinacion,concepto_id,subconcepto_id,base_imponible,base_exonerada,base_afecta,impuesto,");
			SQL.append("nro_cuotas,porc_propiedad,djreferencia_id,fecha_actualizacion,estado,usuario_id,fecha_registro,terminal,fiscalizado, fisca_aceptada, fisca_cerrada) VALUES(");
			SQL.append(deterId);
			SQL.append("," ).append( deter.getPersonaId());
			SQL.append("," ).append( deter.getAnnoDeterminacion());
			SQL.append("," ).append( deter.getConceptoId());
			SQL.append("," ).append( deter.getSubconceptoId());
			SQL.append("," ).append( deter.getBaseImponible());
			SQL.append("," ).append( deter.getBaseExonerada());
			SQL.append("," ).append( deter.getBaseAfecta());
			SQL.append("," ).append( deter.getImpuesto());
			SQL.append("," ).append( deter.getNroCuotas());
			SQL.append("," ).append( deter.getPorcPropiedad());
			SQL.append("," ).append( deter.getDjreferenciaId());
			SQL.append(",'" ).append( deter.getFechaActualizacion());
			SQL.append("','" ).append( deter.getEstado());
			SQL.append("'," ).append( deter.getUsuarioId());
			SQL.append(",'" ).append(deter.getFechaRegistro());
			SQL.append("','" ).append( deter.getTerminal());
			SQL.append("','" ).append( deter.getFiscalizado());
			SQL.append("','" ).append( deter.getFiscaAceptada());
			SQL.append("','" ).append( deter.getFiscaCerrada());
			SQL.append("')");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.execute();

			return deterId;
		} catch (Exception ex) {
			// TODO : Controller Exception
			System.out.println("ERROR: " + ex);
		}
		return 0;
	}

	/**
	 * Guarda la determinación en la base de datos. Para la condición: Existe
	 * deuda para su declaración previa, No existen valores y no existen pagos.
	 * 
	 * @param deter
	 *            Determinación
	 * @return PK de determinación
	 */
	public int guardarDeterminacion2(DtDeterminacion deter) {
		try {
			int deterId = ObtenerCorrelativoTabla(
					"dt_determinacion", 1);

			StringBuilder SQL = new StringBuilder(
					"INSERT INTO ").append(Constante.schemadb).append(".dt_determinacion(determinacion_id,persona_id,anno_determinacion,concepto_id,subconcepto_id,base_imponible,base_exonerada,base_afecta,impuesto,");
			SQL.append("nro_cuotas,porc_propiedad,djreferencia_id,base_imponible_anterior,base_exonerada_anterior,base_afecta_anterior,impuesto_anterior,impuesto_diferencia,");
			SQL.append("fecha_actualizacion,estado,usuario_id,fecha_registro,terminal) VALUES(");
			SQL.append(deterId);
			SQL.append("," ).append( deter.getPersonaId());
			SQL.append("," ).append( deter.getAnnoDeterminacion());
			SQL.append("," ).append( deter.getConceptoId());
			SQL.append("," ).append( deter.getSubconceptoId());
			SQL.append("," ).append( deter.getBaseImponible());
			SQL.append("," ).append( deter.getBaseExonerada());
			SQL.append("," ).append( deter.getBaseAfecta());
			SQL.append("," ).append( deter.getImpuesto());
			SQL.append("," ).append( deter.getNroCuotas());
			SQL.append("," ).append( deter.getPorcPropiedad());
			SQL.append("," ).append( deter.getDjreferenciaId());
			SQL.append("," ).append( deter.getBaseImponibleAnterior());
			SQL.append("," ).append( deter.getBaseExoneradaAnterior());
			SQL.append("," ).append( deter.getBaseAfectaAnterior());
			SQL.append("," ).append( deter.getImpuestoAnterior());
			SQL.append("," ).append( deter.getImpuestoDiferencia());
			SQL.append(",'" ).append( deter.getFechaActualizacion());
			SQL.append("','" ).append( deter.getEstado());
			SQL.append("'," ).append( deter.getUsuarioId());
			SQL.append(",'" ).append( deter.getFechaRegistro());
			SQL.append("','" ).append( deter.getTerminal());
			SQL.append("')");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.execute();

			return deterId;
		} catch (Exception ex) {
			// TODO : Controller Exception
			System.out.println("ERROR: " + ex);
		}
		return 0;
	}

	public boolean actualizarDeterminancion() {
		return false;
	}

	/**
	 * Guarda la determinación vehicular en la base de datos.
	 * 
	 * @param deterv
	 *            Determinación
	 * @return
	 */
	public int guardarDeterminacionVehicular(DtDeterminacionVehicular deterv) {
		try {
			int detervId = ObtenerCorrelativoTabla(
					"dt_determinacion_vehicular", 1);
			StringBuilder SQL = new StringBuilder(
					"INSERT INTO ").append(Constante.schemadb).append(".dt_determinacion_vehicular(");
			SQL.append("deter_vehicular_id,determinacion_id,nro_referencia,tipo_moneda_id,valor_adquisicion_soles,valor_adquisicion_moneda,");
			SQL.append("valor_referencial,tasa,base_imponible,base_exonerada,base_afecta,valor_impuesto,categoria_vehiculo_id,");
			SQL.append("marca_vehiculo_id,modelo_vehiculo_id,estado,usuario_id,fecha_registro,terminal) VALUES(");
			SQL.append(detervId);
			SQL.append("," ).append( deterv.getDeterminacionId());
			SQL.append("," ).append( deterv.getNroReferencia());
			SQL.append("," ).append( deterv.getTipoMonedaId());
			SQL.append("," ).append( deterv.getValorAdquisicionSoles());
			SQL.append("," ).append( deterv.getValorAdquisicionMoneda());
			SQL.append("," ).append( deterv.getValorReferencial());
			SQL.append("," ).append( deterv.getTasa());
			SQL.append("," ).append( deterv.getBaseImponible());
			SQL.append("," ).append( deterv.getBaseExonerada());
			SQL.append("," ).append( deterv.getBaseAfecta());
			SQL.append("," ).append( deterv.getValorImpuesto());
			SQL.append("," ).append( deterv.getCategoriaVehiculoId());
			SQL.append("," ).append( deterv.getMarcaVehiculoId());
			SQL.append("," ).append( deterv.getModeloVehiculoId());
			SQL.append(",'" ).append( deterv.getEstado());
			SQL.append("'," ).append( deterv.getUsuarioId());
			SQL.append(",'" ).append( deterv.getFechaRegistro());
			SQL.append("','" ).append( deterv.getTerminal());
			SQL.append("')");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.execute();

			return detervId;
		} catch (Exception ex) {
			// TODO : Controller Exception
			System.out.println("ERROR: " + ex);
		}
		return 0;
	}

	public boolean actualizarDeterminacionVehicular() {
		return false;
	}

	/**
	 * Guarda deuda para una determinación
	 * 
	 * @param dt
	 *            Deuda
	 * @return
	 */
	public int guardarDeuda(CdDeuda dt) {
		try {
			
			int deudaId = ObtenerCorrelativoTabla("cd_deuda", 1);
			StringBuilder SQL = new StringBuilder("INSERT INTO ").append(Constante.schemadb).append(".cd_deuda(");
			SQL.append("deuda_id,tipo_deuda_id,persona_id,concepto_id,subconcepto_id,determinacion_id,anno_deuda,fecha_emision,fecha_vencimiento,nro_cuota,");
			SQL.append("monto_original,insoluto,reajuste,derecho_emision,interes_mensual,interes_anual,interes_capitalizado,total_deuda,insoluto_cancelado,");
			SQL.append("reajuste_cancelado,derecho_emision_cancelado,interes_mensual_cancelado,interes_capi_cancelado,total_cancelado,nro_referencia,");
			SQL.append("nro_cuenta_banco,usuario_id,estado,fecha_registro,terminal,descuento,estado_deuda_id) VALUES(");
			SQL.append(deudaId);
			SQL.append("," ).append( dt.getTipoDeudaId());
			SQL.append("," ).append( dt.getPersonaId());
			SQL.append("," ).append( dt.getConceptoId());
			SQL.append("," ).append( dt.getSubconceptoId());
			SQL.append("," ).append( dt.getDeterminacionId());
			SQL.append("," ).append( dt.getAnnoDeuda());
			SQL.append(",'" ).append( dt.getFechaEmision());
			SQL.append("','" ).append( dt.getFechaVencimiento());
			SQL.append("'," ).append( dt.getNroCuota());
			SQL.append("," ).append( dt.getMontoOriginal());
			SQL.append("," ).append( dt.getInsoluto());
			SQL.append(",0,");
			SQL.append(dt.getDerechoEmision());
			SQL.append(",0,0,0");
			SQL.append("," + dt.getTotalDeuda());
			SQL.append(",0,0,0,0,0,0");
			SQL.append("," + dt.getNroReferencia());
			if (dt.getNroCuentaBanco() == null) {
				SQL.append(",null");
			} else {
				SQL.append(",'" + dt.getNroCuentaBanco() + "'");
			}

			SQL.append("," + dt.getUsuarioId());
			SQL.append(",'1'");
			SQL.append(",'" + dt.getFechaRegistro());
			SQL.append("','" + dt.getTerminal());
			SQL.append("',0," + dt.getEstadoDeudaId());
			SQL.append(")");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.execute();

			return deudaId;
		} catch (Exception ex) {
			// TODO : Controller Exception
			System.out.println("ERROR: " + ex);
		}
		return 0;
	}

	public boolean actualizarDeuda() {
		return false;
	}

	/**
	 * Guarda deuda histórica
	 * 
	 * @param dh
	 *            Deuda histórica
	 * @return
	 */
	public int guardarDeudaHistorica(CdDeudaHistorica dh) {
		try {
			int historicoId = ObtenerCorrelativoTabla(
					"cd_deuda_historica", 1);
			StringBuilder SQL = new StringBuilder(
					"INSERT INTO ").append(Constante.schemadb).append(".cd_deuda_historica(");
			SQL.append("historica_id,deuda_id,tipo_movimiento_id,determinacion_id,persona_id,fecha_movimiento,tipo_deuda,");
			SQL.append("fecha_vencimiento,insoluto,total,usuario_id,estado,fecha_registro,terminal,descuento) VALUES(");
			SQL.append(historicoId);
			SQL.append("," ).append( dh.getId().getDeudaId());
			SQL.append(",").append(dh.getTipoMovimientoId());
			SQL.append("," ).append( dh.getDeterminacionId());
			SQL.append("," ).append( dh.getPersonaId());
			SQL.append(",'").append( dh.getFechaMovimiento());
			SQL.append("'," ).append( dh.getTipoDeuda());
			SQL.append(",'" ).append( dh.getFechaVencimiento());
			SQL.append("'," ).append( dh.getInsoluto());
			SQL.append("," ).append( dh.getTotal());
			SQL.append("," ).append( dh.getUsuarioId());
			SQL.append(",'" ).append( dh.getEstado());
			SQL.append("','" ).append( dh.getFechaRegistro());
			SQL.append("','" ).append( dh.getTerminal());
			SQL.append("',0)");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.execute();

			return historicoId;
		} catch (Exception ex) {
			// TODO : Controller Exception
			System.out.println("ERROR: " + ex);
		}
		return 0;
	}

	public boolean actualizarDeudaHistorica() {
		return false;
	}

	/**
	 * Obtiene datos de los pagos hechos a las deudas de una determinación para
	 * saber si tiene deudas pagadas.
	 * 
	 * @param determinacionId
	 *            Identificador de determinación
	 * @return
	 */
	public DeudaPagosDTO getDeudaPagos(int determinacionId) {
		try {
			StringBuilder SQL = new StringBuilder(
					"SELECT SUM(deu.total_deuda) AS totalDeuda, SUM(deu.total_cancelado) AS totalCancelado, SUM(deu.total_deuda-deu.total_cancelado) AS deudaMenosCancelado FROM ").append(Constante.schemadb).append(".cd_deuda deu ");
			SQL.append("WHERE deu.determinacion_id=" ).append( determinacionId);

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();

			DeudaPagosDTO pago = null;
			while (rs.next()) {
				pago = new DeudaPagosDTO();
				pago.setTotalDeuda(rs.getBigDecimal("totalDeuda"));
				pago.setTotalCancelado(rs.getBigDecimal("totalCancelado"));
				pago.setDeudaMenosCancelado(rs
						.getBigDecimal("deudaMenosCancelado"));
			}
			return pago;
		} catch (Exception ex) {
			// TODO : Controller Exception
			System.out.println("ERROR: " + ex);
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
	public List<DeudaValoresDTO> getDeudaValores(int persona,int concepto,int anio,int vehiculo) {
		try {
			StringBuilder SQL = new StringBuilder(
			"SELECT deu.deuda_id, ISNULL(deu.flag_cc,'0') flag_cc,ISNULL(deu.flag_op,'0')flag_op FROM ")
			.append(Constante.schemadb).append(".cd_deuda deu  INNER JOIN  dt_determinacion ON deu.determinacion_id = dt_determinacion.determinacion_id  INNER JOIN ")
			.append(Constante.schemadb).append(".rv_djvehicular ON dt_determinacion.djreferencia_id = rv_djvehicular.djvehicular_id");
			SQL.append(" WHERE deu.persona_id=" ).append( persona).append(" AND deu.concepto_id = ").append( concepto).append(" AND deu.anno_deuda =")
			.append( anio).append(" and deu.estado=1 and rv_djvehicular.vehiculo_id=").append( vehiculo);   

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();

			List<DeudaValoresDTO> listaValores = new ArrayList<DeudaValoresDTO>();
			while (rs.next()) {
				DeudaValoresDTO val = new DeudaValoresDTO();
				val.setDeudaId(rs.getInt("deuda_id"));
				val.setFlagCoactiva(rs.getString("flag_cc"));
				val.setFlagOp(rs.getString("flag_op"));
				listaValores.add(val);
			}
			return listaValores;
		} catch (Exception ex) {
			// TODO : Controller Exception
			System.out.println("ERROR: " + ex);
		}
		return null;
	}

	/**
	 * Cambia el estado de una determinación vehicular según la determinación
	 * 
	 * @param determinacionId
	 *            Identificador de la determinación
	 * @param estado
	 *            Nuevo estado
	 */
	public void cambiarEstadoDeterminacionVehic(int determinacionId,
			String estado) throws SisatException {
		try {
			StringBuilder SQL = new StringBuilder(
					"UPDATE ").append(Constante.schemadb).append(".dt_determinacion_vehicular SET estado='").append( estado ).append( "' ");
			SQL.append("WHERE determinacion_id=" + determinacionId);

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.execute();

		} catch (Exception ex) {
			String msg = "Error cambiando estado de la determinación de vehiculos";
			System.out.println(msg + ex); // TODO: Log
			throw new SisatException(msg);
		}
	}

	public void actualizarReajusteIntereses(int deudaId, BigDecimal reajuste,
			BigDecimal interesCapitalizado, BigDecimal interesSimple,
			Timestamp fechaInteres, Timestamp fechaReajuste)
			throws SisatException {
		try {
			StringBuilder SQL = new StringBuilder(
					"UPDATE ").append(Constante.schemadb).append(".cd_deuda SET reajuste=reajuste+" + reajuste);
			SQL.append(", interes_capitalizado=interes_capitalizado+"
					).append( interesCapitalizado);
			SQL.append(", interes_mensual=interes_mensual+" ).append( interesSimple);
			SQL.append(", fecha_interes='" ).append( fechaInteres);
			SQL.append("', fecha_reajuste='" ).append( fechaReajuste);
			SQL.append("' WHERE deuda_id=" ).append( deudaId);

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.execute();

		} catch (Exception ex) {
			String msg = "Error actualizando reajuste e interes";
			System.out.println(msg + ex); // TODO: Log
			throw new SisatException(msg);
		}
	}

	/**
	 * Inputa una deuda a partir de otra, actualiza deuda, generá historico y
	 * guarda el pago
	 * 
	 * @param deudaPreviaId
	 * @param monto
	 * @param deudaId
	 * @param fechaPago
	 * @param usuarioId
	 * @param terminal
	 * @return
	 * @throws SisatException
	 */
	public BigDecimal inputaDeudaSinValores(int deudaPreviaId,
			BigDecimal monto, int deudaId, int usuarioId, String terminal)
			throws SisatException {
		try {
			StringBuilder SQL = new StringBuilder("{call ").append(Constante.schemadb).append(".stp_inputarDeudaSinValores(?,?,?,?,?)}");
			BigDecimal salida = null;
			CallableStatement cs = connect().prepareCall(SQL.toString());
			cs.setInt(1, deudaPreviaId);
			cs.setBigDecimal(2, monto);
			cs.setInt(3, deudaId);
			cs.setInt(4, usuarioId);
			cs.setString(5, terminal);
			ResultSet rs = cs.executeQuery();
			while (rs.next()) {
				salida = rs.getBigDecimal(1);
			}
			return salida;
		} catch (Exception ex) {
			String msg = "No se ha podido generar y recuperar reajuste";
			System.out.println(msg + ex); // TODO:Log
			throw new SisatException(msg);
		}
	}
	
	public BigDecimal duplicarDjAnho(int anho, int usuarioId, String terminal) throws SisatException {

		BigDecimal nroDjVehiculares = new BigDecimal(0);
		try {
			StringBuilder SQL = new StringBuilder("{call ").append(Constante.schemadb).append(".spt_gen_masiva_DjVehicular(?,?,?)}");

			CallableStatement cs = connect().prepareCall(SQL.toString());
			cs.setInt(1, anho);
			cs.setInt(2, usuarioId);
			cs.setString(3, terminal);
			ResultSet rs = cs.executeQuery();
			while (rs.next()) {
				nroDjVehiculares = rs.getBigDecimal(1);
			}

			return nroDjVehiculares;
		} catch (Exception ex) {
			String msg = "No se pudo realizar la duplicacion de la declaraciones juradas";
			System.out.println(msg + ex);
			throw new SisatException(msg);
		}
	}
	
	public List<Integer> getDjVehicularesSinDeterminar(int anhoDeterminacion,int indiceInf, int indiceSup) {
		try {
			//Código comentado en FEB-2017, no existe referencia de inserción en las tablas wh_saldos.dbo.deberian_veh y wh_saldos.dbo.estan_veh:
//			StringBuilder SQL = new StringBuilder();
			
//			SQL.append("  select v.djvehicular_id,v.djvehicular_nro,v.persona_id from dbo.rv_djvehicular v ");
//			SQL.append("  inner join (  ");
//			SQL.append("  select d.persona_id,d.vehiculo_id "); 
//			SQL.append("  from wh_saldos.dbo.deberian_veh d  ");
//			SQL.append("  left join wh_saldos.dbo.estan_veh e on (d.persona_id=e.persona_id and d.vehiculo_id=e.vehiculo_id) "); 
//			SQL.append("  where e.vehiculo_id is null  ");
//			SQL.append("  ) d on (v.vehiculo_id=d.vehiculo_id and v.persona_id=d.persona_id and v.anno_afectacion=2017) "); 
//			SQL.append("  order by v.persona_id  ");
			
			StringBuilder SQL = new StringBuilder("{call ").append(Constante.schemadb).append(".spt_gen_masiva_DjDeterminar(?,?,?)}");
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, anhoDeterminacion);
			pst.setInt(2, indiceInf);
			pst.setInt(3, indiceSup);
			ResultSet rs = pst.executeQuery();

			List<Integer> listaValores = new ArrayList<Integer>();
			while (rs.next()) {
				
				Integer val = rs.getInt("djvehicular_id");				
				listaValores.add(val);
			}
			return listaValores;			
			
		} catch (Exception ex) {
			
			System.out.println("ERROR: " + ex);
		}
		return null;
	}
	
	public List<Integer> getDjVehicularesPorAnho(int anhoDeterminacion,int indiceInf, int indiceSup) {
		try {
			
			
			//select * from(
			//			select ROW_NUMBER() OVER (ORDER BY persona_id) AS RowNumber, * from mp_persona)a
			//			where RowNumber BETWEEN 101 AND 200
			//			
			// select rv_dj.djvehicular_id from rv_djvehicular rv_dj where rv_dj.anno_afectacion = 2012	
			StringBuilder SQL = new StringBuilder(
					"select djvehicular_id from(	select ROW_NUMBER() OVER (ORDER BY rv_dj.djvehicular_id) AS RowNumber, rv_dj.djvehicular_id  FROM ")
					.append(Constante.schemadb).append(".rv_djvehicular rv_dj ");
			
			//SQL.append(" inner join dbo.rv_vehiculo  rv_ve on rv_dj.vehiculo_id = rv_ve.vehiculo_id  ");
			
			SQL.append("WHERE ");
			
			SQL.append(" rv_dj.anno_fabricacion is not null and ");
			SQL.append(" rv_dj.categoria_vehiculo_id is not null and ");
			SQL.append(" rv_dj.marca_vehiculo_id is not null and ");
			SQL.append(" rv_dj.modelo_vehiculo_id is not null and");
			SQL.append(" rv_dj.fecha_adquisicion is not null and ");
			SQL.append(" rv_dj.tipo_moneda_id is not null and ");
			SQL.append(" rv_dj.val_adq_soles is not null and ");
			//SQL.append(" rv_dj.rv_motivo_declaracion_id = 10 and ");
			
			SQL.append(" rv_dj.anno_afectacion = " ).append( anhoDeterminacion).append(" )a where RowNumber BETWEEN ")
				.append(indiceInf).append(" AND ").append(indiceSup);

			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();

			List<Integer> listaValores = new ArrayList<Integer>();
			while (rs.next()) {
				
				Integer val = rs.getInt("djvehicular_id");				
				listaValores.add(val);
			}
			return listaValores;			
			
		} catch (Exception ex) {
			
			System.out.println("ERROR: " + ex);
		}
		return null;
	}
	
	public int getCountDjVehicularesPorAnho(int anhoDeterminacion) {
		try {
			
			
			//select * from(
//			select ROW_NUMBER() OVER (ORDER BY persona_id) AS RowNumber, * from mp_persona)a
//			where RowNumber BETWEEN 101 AND 200
			// select rv_dj.djvehicular_id from rv_djvehicular rv_dj where rv_dj.anno_afectacion = 2012
			
			StringBuilder SQL = new StringBuilder(
					"select count(*) as nroDj  FROM ").append(Constante.schemadb).append(".rv_djvehicular rv_dj ");
			
			//SQL.append(" inner join dbo.rv_vehiculo  rv_ve on rv_dj.vehiculo_id = rv_ve.vehiculo_id  ");
			
			SQL.append("WHERE ");
			
			SQL.append(" rv_dj.anno_fabricacion is not null and ");
			SQL.append(" rv_dj.categoria_vehiculo_id is not null and ");
			SQL.append(" rv_dj.marca_vehiculo_id is not null and ");
			SQL.append(" rv_dj.modelo_vehiculo_id is not null and");
			SQL.append(" rv_dj.fecha_adquisicion is not null and ");
			SQL.append(" rv_dj.tipo_moneda_id is not null and ");
			SQL.append(" rv_dj.val_adq_soles is not null and ");
			//SQL.append(" rv_dj.rv_motivo_declaracion_id = 10 and ");
			
			SQL.append(" rv_dj.anno_afectacion = " ).append( anhoDeterminacion);

			//System.out.println(SQL.toString());
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();

			List<Integer> listaValores = new ArrayList<Integer>();
			while (rs.next()) {
				
				int val = rs.getInt("nroDj");				
				return val;
			}
						
			
		} catch (Exception ex) {
			
			System.out.println("ERROR: " + ex);
		}
		return 0;
	}
	
	public int getCountDjVehicularesSinDeterminar(int anhoDeterminacion) {
		try {
			
			
			//select * from(
//			select ROW_NUMBER() OVER (ORDER BY persona_id) AS RowNumber, * from mp_persona)a
//			where RowNumber BETWEEN 101 AND 200
			// select rv_dj.djvehicular_id from rv_djvehicular rv_dj where rv_dj.anno_afectacion = 2012
			
			StringBuilder SQL = new StringBuilder(
					"select count(*) as nroDj  FROM ").append(Constante.schemadb).append(".rv_djvehicular rv_dj ");
			
			//SQL.append(" inner join dbo.rv_vehiculo  rv_ve on rv_dj.vehiculo_id = rv_ve.vehiculo_id  ");
			
			SQL.append("WHERE ");
			
			SQL.append(" rv_dj.anno_fabricacion is not null and ");
			SQL.append(" rv_dj.categoria_vehiculo_id is not null and ");
			SQL.append(" rv_dj.marca_vehiculo_id is not null and ");
			SQL.append(" rv_dj.modelo_vehiculo_id is not null and");
			SQL.append(" rv_dj.fecha_adquisicion is not null and ");
			SQL.append(" rv_dj.tipo_moneda_id is not null and ");
			SQL.append(" rv_dj.val_adq_soles is not null and ");
			SQL.append(" rv_dj.rv_motivo_declaracion_id = 10 and ");
			
			SQL.append(" rv_dj.anno_afectacion = " ).append( anhoDeterminacion);

			//System.out.println(SQL.toString());
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();

			List<Integer> listaValores = new ArrayList<Integer>();
			while (rs.next()) {
				
				int val = rs.getInt("nroDj");				
				return val;
			}
						
			
		} catch (Exception ex) {
			
			System.out.println("ERROR: " + ex);
		}
		return 0;
	}
	
	public void descargoDeudaDJAnhoAfectacion(Integer djVehicularIdDescargo, Integer personaId, Integer vehiculoId, Integer anhoAfectacion, UserSession userSession) throws SisatException {
		
		
		List<Integer> listaDeterminacionId = new ArrayList<Integer>();
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("select dt.determinacion_id ");
		sb.append("from dt_determinacion dt ");
		sb.append("inner join rv_djvehicular djv on dt.djreferencia_id = djv.djvehicular_id and dt.concepto_id = 2 and dt.estado = 1 "); 
		sb.append("where djv.persona_id = ? and djv.vehiculo_id= ? and djv.anno_afectacion = ?" );
		
		
		PreparedStatement pst;
		try {
			pst = connect().prepareStatement(sb.toString());
			pst.setInt(1, personaId.intValue());
			pst.setInt(2, vehiculoId.intValue());
			pst.setInt(3, anhoAfectacion.intValue());
			
			ResultSet rs = pst.executeQuery();

			
			while (rs.next()) {
				listaDeterminacionId.add(rs.getInt("determinacion_id"));
			
			}
		
		} catch (Exception e) {
		
			throw new SisatException(e.getMessage());
		}
		
//		Parametros store
//		
//		@determinacionId int,
//		@personaId int,
//		@djVehicularId int,
//		@usuarioId int,
//		@terminal varchar(50)
//		
		String sqlDescargo = "dbo.stp_rv_descargo_deuda_dj_vehicular ?,?,?,?,?";
		/**
		 * Recorriendo todas las determinaciones, en flujo normal */
		for(Integer determinacionId:listaDeterminacionId){
			System.out.println(determinacionId);
			
			try {
				pst = connect().prepareStatement(sqlDescargo);
				pst.setInt(1, determinacionId.intValue());
				pst.setInt(2, personaId.intValue());
				pst.setInt(3, djVehicularIdDescargo.intValue());
				pst.setInt(4, userSession.getUsuarioId().intValue());
				pst.setString(5, userSession.getTerminal());
				
				pst.execute();
			} catch (SQLException e) {
				System.err.println("Error en el descargo de deuda de una DJ");
				e.printStackTrace();
			}			
		}		
	}

	public Integer actualizarDeterminacionPrevia(Integer determinacion,Integer usuario,String estado)throws Exception{
		int result = 0;
		try {
			StringBuffer SQL = new StringBuffer();

				SQL.append(" UPDATE " + SATParameterFactory.getDBNameScheme()
						+ ".dt_determinacion ");
				SQL.append(" SET usuario_id =?,estado =?,fecha_actualizacion=?");
				SQL.append(" where determinacion_id = ? ");
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, usuario);
			pst.setString(2,estado);
			pst.setTimestamp(3, DateUtil.getCurrentDate());
			pst.setInt(4,determinacion);

			result = pst.executeUpdate();
		} catch (Exception e) {
			throw (e);
		}
		return result;
		
	}
	
	public Integer actualizarDeterminacionVehicularPrevia(Integer determinacion,Integer usuario,String estado)throws Exception{
		int result = 0;
		try {
			StringBuffer SQL = new StringBuffer();

				SQL.append(" UPDATE " + SATParameterFactory.getDBNameScheme()
						+ ".dt_determinacion_vehicular ");
				SQL.append(" SET usuario_id =?,estado =?");
				SQL.append(" where determinacion_id = ? ");
			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, usuario);
			pst.setString(2,estado);
			pst.setInt(3,determinacion);

			result = pst.executeUpdate();
		} catch (Exception e) {
			throw (e);
		}
		return result;
		
	}
	
	public DatosExisteDeterminacionDTO getDatosExisteDeterminacionActiva(int conceptoId, int personaId,int anio,int vehiculo_id ) 
	{
		
		try {
			StringBuilder SQL = new StringBuilder(
					"SELECT dt.determinacion_id,dt.base_imponible,dt.base_exonerada,dt.base_afecta,dt.impuesto,dt.estado FROM ").append(Constante.schemadb).append(".dt_determinacion dt inner join rv_djvehicular djv on dt.djreferencia_id=djv.djvehicular_id");
			
			SQL.append("WHERE dt.concepto_id=" ).append( conceptoId).append(" AND dt.persona_id=" ).append( personaId).append(" AND dt.estado='1' and anno_determinacion=").append(anio);
			SQL.append(" and djv.vehiculo_id= ").append(vehiculo_id);
			

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();

			DatosExisteDeterminacionDTO datos = null;
			while (rs.next()) {
				datos = new DatosExisteDeterminacionDTO();
				datos.setDeterminacionId(rs.getInt("determinacion_id"));
				datos.setBaseImponible(rs.getBigDecimal("base_imponible"));
				datos.setBaseExonerada(rs.getBigDecimal("base_exonerada"));
				datos.setBaseAfecta(rs.getBigDecimal("base_afecta"));
				datos.setImpuesto(rs.getBigDecimal("impuesto"));
				datos.setEstado(rs.getString("estado"));
			}
			return datos;
		} catch (Exception ex) {
			// TODO : Controller Exception
			System.out.println("ERROR: " + ex);
		}
		return null;
	}
	
	public int guardarDeterminacionVariacion(DtDeterminacionValor variacion) {
		try {
			boolean result;
			StringBuilder SQL = new StringBuilder(
					"INSERT INTO ").append(Constante.schemadb).append(".dt_determinacion_valor(");
			SQL.append("determinacion_id, persona_id, anno_determinacion, concepto_id, subconcepto_id, base_imponible, impuesto,");
			SQL.append("determinacion_id_var, persona_id_var, anno_determinacion_var, concepto_id_var,  subconcepto_id_var, base_imponible_var, impuesto_var,");
			SQL.append("flag_aumento, usuario_id, estado,fecha_registro_determinacion, fecha_registro_determinacion_var, fecha_registro, terminal) VALUES(");
			SQL.append(variacion.getDeterminacionId());
			SQL.append("," ).append( variacion.getPersonaId());
			SQL.append("," ).append( variacion.getAnnoDeterminacion());
			SQL.append("," ).append( variacion.getConceptoId());
			SQL.append("," ).append( variacion.getSubconceptoId());
			SQL.append(",'" ).append( variacion.getBaseImponible());
			SQL.append("','" ).append( variacion.getImpuesto());
			SQL.append("'," ).append( variacion.getDeterminacionIdVariacion());
			SQL.append("," ).append( variacion.getPersonaIdVariacion());
			SQL.append("," ).append( variacion.getAnnoDeterminacionVariacion());
			SQL.append("," ).append( variacion.getConceptoIdVariacion());
			SQL.append("," ).append( variacion.getSubconceptoIdVariacion());
			SQL.append(",'" ).append( variacion.getBaseImponibleVariacion());
			SQL.append("','" ).append( variacion.getImpuestoVariacion());
			SQL.append("'," ).append( variacion.getFlagAumento());
			SQL.append("," ).append( variacion.getUsuarioId());
			SQL.append("," ).append( variacion.getEstado());
			SQL.append(",'" ).append( variacion.getFechaRegistroDt());
			SQL.append("','" ).append( variacion.getFechaRegistroDtVariacion());
			SQL.append("','" ).append( variacion.getFechaRegistro());
			SQL.append("','" ).append( variacion.getTerminal());
			SQL.append("')");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			result =pst.execute();

			//return variacion.getDeterminacionValorId();
		} catch (Exception ex) {
			// TODO : Controller Exception
			System.out.println("ERROR: " + ex);
		}
		return 0;
	}
	
	public DtDeterminacion getDatosPreviaDeterminacionActiva(int determinacionPrevia) {
		try {
			StringBuilder SQL = new StringBuilder(
			"SELECT dt.determinacion_id,dt.anno_determinacion,dt.persona_id,dt.concepto_id,dt.subconcepto_id,dt.base_imponible,dt.impuesto,dt.fecha_registro FROM ").append(Constante.schemadb).append(".dt_determinacion dt ");
			SQL.append("WHERE dt.determinacion_id=" ).append(determinacionPrevia);

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();

			DtDeterminacion datos = null;
			while (rs.next()) {
				datos = new DtDeterminacion();
				datos.setDeterminacionId(rs.getInt("determinacion_id"));
				datos.setAnnoDeterminacion(rs.getInt("anno_determinacion"));
				datos.setPersonaId(rs.getInt("persona_id"));
				datos.setConceptoId(rs.getInt("concepto_id"));
				datos.setSubconceptoId(rs.getInt("subconcepto_id"));
				datos.setBaseImponible(rs.getBigDecimal("base_imponible"));
				datos.setImpuesto(rs.getBigDecimal("impuesto"));
				datos.setFechaRegistro(rs.getTimestamp("fecha_registro"));
			}
			return datos;
		} catch (Exception ex) {
			// TODO : Controller Exception
			System.out.println("ERROR: " + ex);
		}
		return null;
	}
	
	public DatosDeterminacionValoresDTO getMontoDeterminacionValor(int persona,int concepto,int anio) {
		try {
			StringBuilder SQL = new StringBuilder(
			"SELECT    isnull(sum( d.monto_original),0) monto FROM ");
			SQL.append("(SELECT  deu.determinacion_id, deu.monto_original FROM cd_deuda AS deu ");
			SQL.append("WHERE deu.persona_id=" ).append(persona).append(" AND deu.concepto_id=").append(concepto).append(" AND deu.anno_deuda=").append(anio);
			SQL.append(" AND deu.estado = 1 AND deu.flag_op = 1 GROUP BY deu.determinacion_id, deu.monto_original) AS d ");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();

			DatosDeterminacionValoresDTO datos = null;
			while (rs.next()) {
				datos = new DatosDeterminacionValoresDTO();
				datos.setImpuesto(rs.getBigDecimal("monto"));
			}
			return datos;
		} catch (Exception ex) {
			// TODO : Controller Exception
			System.out.println("ERROR: " + ex);
		}
		return null;
	}
	public DeudaPagosDTO getDeudaValoresPagos(int determinacionId) {
		try {
			StringBuilder SQL = new StringBuilder(
//					"SELECT SUM(deu.total_deuda) AS totalDeuda, isnull(SUM(deu.total_cancelado),0) AS totalCancelado, SUM(deu.total_deuda-deu.total_cancelado) AS deudaMenosCancelado FROM ").append(Constante.schemadb).append(".cd_deuda deu ");
//			SQL.append("WHERE deu.determinacion_id=" ).append( determinacionId).append(" and (deu.flag_op<>1 or deu.flag_op is null)and deu.estado=1");

					"SELECT SUM(deu.total_deuda) AS totalDeuda, isnull(SUM(deu.total_cancelado),0) AS totalCancelado, SUM(deu.total_deuda-deu.total_cancelado) AS deudaMenosCancelado FROM ").append(Constante.schemadb).append(".cd_deuda deu ");
			SQL.append("WHERE deu.determinacion_id=" ).append( determinacionId).append(" and (deu.flag_op<>1 or deu.flag_op is null)and deu.estado=1 and deu.concepto_id=2 and deu.determinacion_id not in (");
			SQL.append("SELECT  d.determinacion_id FROM (SELECT  de.determinacion_id, de.monto_original FROM cd_deuda AS de WHERE de.determinacion_id=").append(determinacionId);
			SQL.append("AND de.concepto_id=2 AND de.estado = 1 AND de.flag_op = 1 GROUP BY de.determinacion_id, de.monto_original)AS d )");
					
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();

			DeudaPagosDTO pago = null;
			while (rs.next()) {
				pago = new DeudaPagosDTO();
				pago.setTotalDeuda(rs.getBigDecimal("totalDeuda"));
				pago.setTotalCancelado(rs.getBigDecimal("totalCancelado"));
				pago.setDeudaMenosCancelado(rs
						.getBigDecimal("deudaMenosCancelado"));
			}
			return pago;
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
	
	public DatosDeterminacionValoresDTO getDeterminacionValores(int persona,int concepto,int anio,int determinacion) {
		try {
			StringBuilder SQL = new StringBuilder(
			"SELECT  d.determinacion_id FROM ");
			SQL.append("(SELECT  deu.determinacion_id, deu.monto_original FROM cd_deuda AS deu ");
			SQL.append("WHERE deu.persona_id=" ).append(persona).append(" AND deu.concepto_id=").append(concepto).append(" AND deu.anno_deuda=").append(anio);
			SQL.append(" AND deu.estado = 1 AND deu.flag_op = 1 GROUP BY deu.determinacion_id, deu.monto_original) AS d ");
			SQL.append("where d.determinacion_id=").append(determinacion);
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();

			DatosDeterminacionValoresDTO datos = null;
			while (rs.next()) {
				datos = new DatosDeterminacionValoresDTO();
				datos.setDeterminacionId(rs.getInt("determinacion_id"));
			}
			return datos;
		} catch (Exception ex) {
			// TODO : Controller Exception
			System.out.println("ERROR: " + ex);
		}
		return null;
	}

}