package com.sat.sisat.determinacion.common.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.sat.sisat.common.dao.GeneralDao;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.SATParameterFactory;
import com.sat.sisat.determinacion.common.dto.CjPagoDTO;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.persistence.CrudServiceBean;
import com.sat.sisat.persistence.entity.DtCuotaConcepto;
import com.sat.sisat.persistence.entity.DtFechaVencimiento;

public class DeterminacionDao extends GeneralDao {

	/**
	 * Obtiene el número de cuotas dependiendo del subconcepto (Predial,
	 * Vehicular, Arbitrio01, Arbitrio02...).
	 * 
	 * @param subConceptoId
	 *            Identificador del subconcepto.
	 * @param periodo
	 *            Año
	 * @return
	 */
	public DtCuotaConcepto getCuotasConcepto(int subConceptoId, int periodo) {
		try {
			StringBuilder SQL = new StringBuilder("SELECT cc.nro_cuotas, cc.monto_derecho_emision, cc.cuota_derecho_emision FROM ").append(Constante.schemadb).append(".dt_cuota_concepto cc ");
			SQL.append("WHERE cc.subconcepto_id=").append(subConceptoId);
			SQL.append(" AND cc.periodo=").append(periodo).append(" AND cc.estado='1'");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();

			DtCuotaConcepto cuota = null;
			while (rs.next()) {
				cuota = new DtCuotaConcepto();
				cuota.setNroCuotas(rs.getInt("nro_cuotas"));
				cuota.setMontoDerechoEmision(rs
						.getBigDecimal("monto_derecho_emision"));
				cuota.setCuotaDerechoEmision(rs.getInt("cuota_derecho_emision"));
			}
			return cuota;
		} catch (Exception ex) {
			// TODO : Controller Exception
			System.out.println("ERROR: " + ex);
		}
		return null;
	}

	/**
	 * Obtiene las fechas de vencimiento de cada una de las cuotas para un
	 * subconcepto de un determinado año
	 * 
	 * @param subConceptoId
	 *            Identificador de subconcepto (Predial, Vehicular, Arbitrio01,
	 *            Arbitrio02...).
	 * @param periodo
	 *            Año.
	 * @return
	 */
	public List<DtFechaVencimiento> getFechaVencimiento(int subConceptoId,
			int periodo) {
		try {
			StringBuilder SQL = new StringBuilder(
					"SELECT fv.vencimiento_id, fv.cuota, fv.fecha_vencimiento FROM ").append(Constante.schemadb).append(".dt_fecha_vencimiento fv ");
			SQL.append("WHERE fv.subconcepto_id=").append(subConceptoId);
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

	/**
	 * Cambia el estado de una determinación
	 * 
	 * @param determinacionId
	 *            Identificador de la determinación
	 * @param estado
	 *            Nuevo estado
	 */
	public void cambiarEstadoDeterminacion(int determinacionId, String estado) {
		try {
			StringBuilder SQL = new StringBuilder(
			"UPDATE ").append(Constante.schemadb).append(".dt_determinacion SET estado='").append(estado).append("' ");
			SQL.append("WHERE determinacion_id=" + determinacionId);

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.execute();

		} catch (Exception ex) {
			// TODO : Controller Exception
			System.out.println("ERROR: " + ex);
		}
	}

	/**
	 * Cambia el estado de todas las deudas de una determinación
	 * 
	 * @param determinacionId
	 *            Identificador de determinación
	 * @param estado
	 *            Nuevo estado
	 */
	public void cambiarEstadoDeudasDeter(int determinacionId, String estado) {
		try {
			StringBuilder SQL = new StringBuilder(
			"UPDATE ").append(Constante.schemadb).append(".cd_deuda SET estado='").append(estado).append("' ");
			SQL.append("WHERE determinacion_id=").append(determinacionId);

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.execute();

		} catch (Exception ex) {
			// TODO : Controller Exception
			System.out.println("ERROR: " + ex);
		}
	}

	/**
	 * Cambia el estado de todas las deudas historicas de una determinación
	 * 
	 * @param determinacionId
	 *            Identificador de determinación
	 * @param estado
	 *            Nuevo estado
	 */
	public void cambiarEstadoDeudaHistoricaDeter(int determinacionId,
			String estado) {
		try {
			StringBuilder SQL = new StringBuilder(
					"UPDATE ").append(Constante.schemadb).append(".cd_deuda_historica SET estado='").append(estado).append("' ");
			SQL.append("WHERE determinacion_id=").append(determinacionId);

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.execute();

		} catch (Exception ex) {
			// TODO : Controller Exception
			System.out.println("ERROR: " + ex);
		}
	}

	public List<CjPagoDTO> getPagosDeuda(int deudaId) throws SisatException {
		try {
			StringBuilder SQL = new StringBuilder(
					"SELECT pago_id, fecha_pago FROM ").append(Constante.schemadb).append(".cj_pago ");
			SQL.append("WHERE deuda_id=").append(deudaId).append(" AND estado='1'");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();

			List<CjPagoDTO> lista = new ArrayList<CjPagoDTO>();

			while (rs.next()) {
				CjPagoDTO obj = new CjPagoDTO();
				obj.setPagoId(rs.getInt("pago_id"));
				obj.setFechaPago(rs.getTimestamp("fecha_pago"));
				lista.add(obj);
			}
			return lista;
		} catch (Exception ex) {
			String msg = "Error recuperando pagos de la deuda";
			System.out.println(msg + ex); // TODO: Log
			throw new SisatException(msg);
		}
	}

	/**
	 * Obtiene PK de una deuda segun la determinación y el número de cuota
	 * 
	 * @param deterId
	 *            PK
	 * @param nroCuota
	 *            Número de cuota (1, 2, 3 o 4)
	 * @return
	 */
	public int getIdDeudaPrevia(int deterId, int nroCuota) {
		try {
			StringBuilder SQL = new StringBuilder(
					"SELECT deuda_id FROM ").append(Constante.schemadb).append(".cd_deuda ");
			SQL.append("WHERE determinacion_id=").append(deterId);
			SQL.append(" AND nro_cuota=").append(nroCuota);
			SQL.append(" AND estado='1'");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();

			int salida = 0;
			while (rs.next()) {
				salida = rs.getInt("deuda_id");
			}
			return salida;
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return 0;
	}
}
