package com.sat.sisat.vehicular.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.sat.sisat.common.dao.GeneralDao;
import com.sat.sisat.common.dto.PermisoDTO;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.SATParameterFactory;
import com.sat.sisat.determinacion.vehicular.dto.DatosNecesariosDeterDTO;
import com.sat.sisat.determinacion.vehicular.dto.DeudaValoresDTO;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.fiscalizacion.dto.DatosNecesariosDeclaracionDTO;
import com.sat.sisat.persistence.entity.DtDeterminacion;
import com.sat.sisat.persistence.entity.GnNotaria;
import com.sat.sisat.persistence.entity.GnTipoMoneda;
import com.sat.sisat.persistence.entity.RvAdquisicion;
import com.sat.sisat.persistence.entity.RvCategoriaVehiculo;
import com.sat.sisat.persistence.entity.RvClaseVehiculo;
import com.sat.sisat.persistence.entity.RvCondicionPropiedad;
import com.sat.sisat.persistence.entity.RvCondicionVehiculo;
import com.sat.sisat.persistence.entity.RvDetCondVehiculo;
import com.sat.sisat.persistence.entity.RvDjvehicular;
import com.sat.sisat.persistence.entity.RvMarca;
import com.sat.sisat.persistence.entity.RvModeloVehiculo;
import com.sat.sisat.persistence.entity.RvModeloVehiculoPK;
import com.sat.sisat.persistence.entity.RvMotivoDeclaracion;
import com.sat.sisat.persistence.entity.RvMotivoDescargo;
import com.sat.sisat.persistence.entity.RvSustentoVehicular;
import com.sat.sisat.persistence.entity.RvTipoCarroceria;
import com.sat.sisat.persistence.entity.RvTipoMotor;
import com.sat.sisat.persistence.entity.RvTipoTraccion;
import com.sat.sisat.persistence.entity.RvTipoTransmision;
import com.sat.sisat.persistence.entity.RvTransferenciaPropiedad;
import com.sat.sisat.persistence.entity.RvVehiculo;
import com.sat.sisat.vehicular.dto.AnexosDeclaVehicDTO;
import com.sat.sisat.vehicular.dto.BuscarPersonaDTO;
import com.sat.sisat.vehicular.dto.BuscarVehicularDTO;
import com.sat.sisat.vehicular.dto.CabCarteraVehicularDTO;
import com.sat.sisat.vehicular.dto.CarteraVehiculosDTO;
import com.sat.sisat.vehicular.dto.DistritoDTO;
import com.sat.sisat.vehicular.dto.DocumentoSustentatorioDTO;
import com.sat.sisat.vehicular.dto.HistoricoVehiculodjDTO;
import com.sat.sisat.vehicular.dto.RvVehiculoDTO;
import com.sat.sisat.vehicular.dto.VehiculoExistenteDTO;

public class VehicularBusinessDao extends GeneralDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sat.sisat.vehicular.dao.Interfaz#getAllRvClaseVehiculo()
	 */
	public List<RvClaseVehiculo> getAllRvClaseVehiculo() throws Exception {
		List<RvClaseVehiculo> lista = new ArrayList<RvClaseVehiculo>();
		try {
			String SQL = "SELECT clase_vehiculo_id, descripcion FROM "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_clase_vehiculo ORDER BY descripcion ASC";

			PreparedStatement pst = connect().prepareStatement(SQL);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				RvClaseVehiculo obj = new RvClaseVehiculo();
				obj.setClaseVehiculoId(rs.getInt("clase_vehiculo_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sat.sisat.vehicular.dao.Interfaz#getAllRvCategoriaVehiculo()
	 */
	public List<RvCategoriaVehiculo> getAllRvCategoriaVehiculo()
			throws Exception {
		List<RvCategoriaVehiculo> lista = new ArrayList<RvCategoriaVehiculo>();
		try {
			String SQL = "SELECT categoria_vehiculo_id, descripcion FROM "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_categoria_vehiculo ORDER BY descripcion ASC";

			PreparedStatement pst = connect().prepareStatement(SQL);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				RvCategoriaVehiculo obj = new RvCategoriaVehiculo();
				obj.setCategoriaVehiculoId(rs.getInt("categoria_vehiculo_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sat.sisat.vehicular.dao.Interfaz#findRvMarca(int)
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sat.sisat.vehicular.dao.Interfaz#getAllRvModeloVehiculo(int,
	 * int)
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sat.sisat.vehicular.dao.Interfaz#getAllRvCondicionVehiculo()
	 */
	public List<RvCondicionVehiculo> getAllRvCondicionVehiculo()
			throws Exception {
		List<RvCondicionVehiculo> lista = new ArrayList<RvCondicionVehiculo>();
		try {
			String SQL = "SELECT condicion_vehiculo_id, descripcion FROM "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_condicion_vehiculo ORDER BY descripcion ASC";

			PreparedStatement pst = connect().prepareStatement(SQL);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				RvCondicionVehiculo obj = new RvCondicionVehiculo();
				obj.setCondicionVehiculoId(rs.getInt("condicion_vehiculo_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sat.sisat.vehicular.dao.Interfaz#getAllRvTipoCarroceria()
	 */
	public List<RvTipoCarroceria> getAllRvTipoCarroceria() throws Exception {
		List<RvTipoCarroceria> lista = new ArrayList<RvTipoCarroceria>();
		try {
			String SQL = "SELECT tipo_carroceria_id, descripcion FROM "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_tipo_carroceria ORDER BY descripcion ASC";

			PreparedStatement pst = connect().prepareStatement(SQL);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				RvTipoCarroceria obj = new RvTipoCarroceria();
				obj.setTipoCarroceriaId(rs.getInt("tipo_carroceria_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sat.sisat.vehicular.dao.Interfaz#getAllRvTipoTransmision()
	 */
	public List<RvTipoTransmision> getAllRvTipoTransmision() throws Exception {
		List<RvTipoTransmision> lista = new ArrayList<RvTipoTransmision>();
		try {
			String SQL = "SELECT tipo_transmision_id, descripcion FROM "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_tipo_transmision ORDER BY descripcion ASC";

			PreparedStatement pst = connect().prepareStatement(SQL);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				RvTipoTransmision obj = new RvTipoTransmision();
				obj.setTipoTransmisionId(rs.getInt("tipo_transmision_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sat.sisat.vehicular.dao.Interfaz#getAllRvTipoTraccion()
	 */
	public List<RvTipoTraccion> getAllRvTipoTraccion() throws Exception {
		List<RvTipoTraccion> lista = new ArrayList<RvTipoTraccion>();
		try {
			String SQL = "SELECT tipo_traccion_id, descripcion FROM "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_tipo_traccion ORDER BY descripcion ASC";

			PreparedStatement pst = connect().prepareStatement(SQL);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				RvTipoTraccion obj = new RvTipoTraccion();
				obj.setTipoTraccionId(rs.getInt("tipo_traccion_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sat.sisat.vehicular.dao.Interfaz#getAllRvTipoMotor()
	 */
	public List<RvTipoMotor> getAllRvTipoMotor() throws Exception {
		List<RvTipoMotor> lista = new ArrayList<RvTipoMotor>();
		try {
			String SQL = "SELECT tipo_motor_id, descripcion FROM "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_tipo_motor ORDER BY descripcion ASC";

			PreparedStatement pst = connect().prepareStatement(SQL);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				RvTipoMotor obj = new RvTipoMotor();
				obj.setTipoMotorId(rs.getInt("tipo_motor_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sat.sisat.vehicular.dao.Interfaz#getAllRvDocumentoSustentatorio()
	 */
	public List<DocumentoSustentatorioDTO> getAllRvDocumentoSustentatorio()
			throws Exception {
		List<DocumentoSustentatorioDTO> lista = new ArrayList<DocumentoSustentatorioDTO>();
		try {
			String SQL = "SELECT doc_sustentatorio_id, descripcion FROM "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_documento_sustentatorio ORDER BY descripcion ASC";

			PreparedStatement pst = connect().prepareStatement(SQL);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				DocumentoSustentatorioDTO obj = new DocumentoSustentatorioDTO();
				obj.setDocSustentatorioId(rs.getInt("doc_sustentatorio_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sat.sisat.vehicular.dao.Interfaz#getAllRvMotivoDescargo()
	 */
	public List<RvMotivoDescargo> getAllRvMotivoDescargo() throws Exception {
		List<RvMotivoDescargo> lista = new ArrayList<RvMotivoDescargo>();
		try {
			String SQL = "SELECT motivo_descargo_id, descripcion FROM "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_motivo_descargo ORDER BY motivo_descargo_id ASC";

			PreparedStatement pst = connect().prepareStatement(SQL);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				RvMotivoDescargo obj = new RvMotivoDescargo();
				obj.setMotivoDescargoId(rs.getInt("motivo_descargo_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sat.sisat.vehicular.dao.Interfaz#getVehiculoByPlaca(java.lang.String)
	 */
	public VehiculoExistenteDTO getVehiculoByPlaca(String placa)
			throws Exception {
		VehiculoExistenteDTO vehiculo = null;
		try {
			StringBuilder SQL = new StringBuilder(
					"SELECT ve.vehiculo_id,ve.anno_fabricacion,ca.categoria_vehiculo_id,ca.descripcion, ");
			SQL.append("cv.condicion_vehiculo_id,cv.descripcion,ve.fecha_actualizacion,ve.fecha_ins_registros,ve.fecha_registro, ");
			SQL.append("ma.marca_vehiculo_id,ma.descripcion,mo.modelo_vehiculo_id,mo.descripcion,ve.num_cilindros,ve.num_motor, ");
			SQL.append("ve.peso_bruto,ve.placa,ve.terminal,tm.tipo_motor_id,tm.descripcion,tt.tipo_traccion_id,tt.descripcion, ");
			SQL.append("ttn.tipo_transmision_id,ttn.descripcion,ve.usuario_id,tc.tipo_carroceria_id,tc.descripcion, ");
			SQL.append("clsv.clase_vehiculo_id,clsv.descripcion FROM "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_vehiculo ve ");
			SQL.append("INNER JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_categoria_vehiculo ca ON ca.categoria_vehiculo_id = ve.categoria_vehiculo_id ");
			SQL.append("INNER JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_condicion_vehiculo cv ON cv.condicion_vehiculo_id = ve.condicion_vehiculo_id ");
			SQL.append("INNER JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_marca ma ON ma.marca_vehiculo_id = ve.marca_vehiculo_id ");
			SQL.append("INNER JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_modelo_vehiculo mo ON mo.modelo_vehiculo_id = ve.modelo_vehiculo_id AND mo.categoria_vehiculo_id=ca.categoria_vehiculo_id AND mo.marca_vehiculo_id = ma.marca_vehiculo_id ");
			SQL.append("LEFT JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_tipo_motor tm ON tm.tipo_motor_id = ve.tipo_motor_id ");
			SQL.append("LEFT JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_tipo_traccion tt ON tt.tipo_traccion_id = ve.tipo_traccion_id ");
			SQL.append("LEFT JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_tipo_transmision ttn ON ttn.tipo_transmision_id = ve.tipo_transmision_id ");
			SQL.append("LEFT JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_tipo_carroceria tc ON tc.tipo_carroceria_id = ve.tipo_carroceria_id ");
			SQL.append("INNER JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_clase_vehiculo clsv ON clsv.clase_vehiculo_id = ve.clase_vehiculo_id ");
			SQL.append("WHERE ve.placa='" + placa + "'");

			// String SQL = "SELECT * FROM "+
			// SATParameterFactory.getDBNameScheme() +
			// ".rv_vehiculo WHERE placa='"
			// + placa + "'";

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				vehiculo = new VehiculoExistenteDTO();
				vehiculo.setVehiculoId(rs.getInt(1));
				vehiculo.setAnnoFabricacion(rs.getInt(2));
				vehiculo.setCategoriaVehiculoId(rs.getInt(3));
				vehiculo.setCategoria(rs.getString(4));
				vehiculo.setCondicionVehiculoId(rs.getInt(5));
				vehiculo.setCondicionVehiculo(rs.getString(6));
				vehiculo.setFechaActualizacion(rs.getTimestamp(7));
				vehiculo.setFechaInsRegistros(rs.getTimestamp(8));
				vehiculo.setFechaRegistro(rs.getTimestamp(9));
				vehiculo.setMarcaVehiculoId(rs.getInt(10));
				vehiculo.setMarca(rs.getString(11));
				vehiculo.setModeloVehiculoId(rs.getInt(12));
				vehiculo.setModelo(rs.getString(13));
				vehiculo.setNumCilindros(rs.getInt(14));
				vehiculo.setNumMotor(rs.getString(15));
				vehiculo.setPesoBruto(rs.getInt(16));
				vehiculo.setPlaca(rs.getString(17));
				vehiculo.setTerminal(rs.getString(18));
				vehiculo.setTipoMotorId(rs.getInt(19));
				vehiculo.setTipoMotor(rs.getString(20));
				vehiculo.setTipoTraccionId(rs.getInt(21));
				vehiculo.setTipoTraccion(rs.getString(22));
				vehiculo.setTipoTransmisionId(rs.getInt(23));
				vehiculo.setTipoTransmision(rs.getString(24));
				vehiculo.setUsuarioId(rs.getInt(25));
				vehiculo.setTipoCarroceriaId(rs.getInt(26));
				vehiculo.setTipoCarroceria(rs.getString(27));
				vehiculo.setClaseVehiculoId(rs.getInt(28));
				vehiculo.setClaseVehiculo(rs.getString(29));
			}
		} catch (Exception e) {
			throw (e);
		}
		return vehiculo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sat.sisat.vehicular.dao.Interfaz#getVehiculoByNumMotor(java.lang.
	 * String)
	 */
	public VehiculoExistenteDTO getVehiculoByNumMotor(String numMotor)
			throws Exception {
		VehiculoExistenteDTO vehiculo = null;
		try {
			StringBuilder SQL = new StringBuilder(
					"SELECT ve.vehiculo_id,ve.anno_fabricacion,ca.categoria_vehiculo_id,ca.descripcion, ");
			SQL.append("cv.condicion_vehiculo_id,cv.descripcion,ve.fecha_actualizacion,ve.fecha_ins_registros,ve.fecha_registro, ");
			SQL.append("ma.marca_vehiculo_id,ma.descripcion,mo.modelo_vehiculo_id,mo.descripcion,ve.num_cilindros,ve.num_motor, ");
			SQL.append("ve.peso_bruto,ve.placa,ve.terminal,tm.tipo_motor_id,tm.descripcion,tt.tipo_traccion_id,tt.descripcion, ");
			SQL.append("ttn.tipo_transmision_id,ttn.descripcion,ve.usuario_id,tc.tipo_carroceria_id,tc.descripcion, ");
			SQL.append("clsv.clase_vehiculo_id,clsv.descripcion FROM "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_vehiculo ve ");
			SQL.append("INNER JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_categoria_vehiculo ca ON ca.categoria_vehiculo_id = ve.categoria_vehiculo_id ");
			SQL.append("INNER JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_condicion_vehiculo cv ON cv.condicion_vehiculo_id = ve.condicion_vehiculo_id ");
			SQL.append("INNER JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_marca ma ON ma.marca_vehiculo_id = ve.marca_vehiculo_id ");
			SQL.append("INNER JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_modelo_vehiculo mo ON mo.modelo_vehiculo_id = ve.modelo_vehiculo_id AND mo.categoria_vehiculo_id=ca.categoria_vehiculo_id AND mo.marca_vehiculo_id = ma.marca_vehiculo_id ");
			SQL.append("LEFT JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_tipo_motor tm ON tm.tipo_motor_id = ve.tipo_motor_id ");
			SQL.append("LEFT JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_tipo_traccion tt ON tt.tipo_traccion_id = ve.tipo_traccion_id ");
			SQL.append("LEFT JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_tipo_transmision ttn ON ttn.tipo_transmision_id = ve.tipo_transmision_id ");
			SQL.append("LEFT JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_tipo_carroceria tc ON tc.tipo_carroceria_id = ve.tipo_carroceria_id ");
			SQL.append("INNER JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_clase_vehiculo clsv ON clsv.clase_vehiculo_id = ve.clase_vehiculo_id ");
			SQL.append("WHERE ve.num_motor='" + numMotor + "'");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				vehiculo = new VehiculoExistenteDTO();
				vehiculo.setVehiculoId(rs.getInt(1));
				vehiculo.setAnnoFabricacion(rs.getInt(2));
				vehiculo.setCategoriaVehiculoId(rs.getInt(3));
				vehiculo.setCategoria(rs.getString(4));
				vehiculo.setCondicionVehiculoId(rs.getInt(5));
				vehiculo.setCondicionVehiculo(rs.getString(6));
				vehiculo.setFechaActualizacion(rs.getTimestamp(7));
				vehiculo.setFechaInsRegistros(rs.getTimestamp(8));
				vehiculo.setFechaRegistro(rs.getTimestamp(9));
				vehiculo.setMarcaVehiculoId(rs.getInt(10));
				vehiculo.setMarca(rs.getString(11));
				vehiculo.setModeloVehiculoId(rs.getInt(12));
				vehiculo.setModelo(rs.getString(13));
				vehiculo.setNumCilindros(rs.getInt(14));
				vehiculo.setNumMotor(rs.getString(15));
				vehiculo.setPesoBruto(rs.getInt(16));
				vehiculo.setPlaca(rs.getString(17));
				vehiculo.setTerminal(rs.getString(18));
				vehiculo.setTipoMotorId(rs.getInt(19));
				vehiculo.setTipoMotor(rs.getString(20));
				vehiculo.setTipoTraccionId(rs.getInt(21));
				vehiculo.setTipoTraccion(rs.getString(22));
				vehiculo.setTipoTransmisionId(rs.getInt(23));
				vehiculo.setTipoTransmision(rs.getString(24));
				vehiculo.setUsuarioId(rs.getInt(25));
				vehiculo.setTipoCarroceriaId(rs.getInt(26));
				vehiculo.setTipoCarroceria(rs.getString(27));
				vehiculo.setClaseVehiculoId(rs.getInt(28));
				vehiculo.setClaseVehiculo(rs.getString(29));
			}
		} catch (Exception e) {
			throw (e);
		}
		return vehiculo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sat.sisat.vehicular.dao.Interfaz#getAllRvMotivoDeclaracion()
	 */
	public List<RvMotivoDeclaracion> getAllRvMotivoDeclaracion()
			throws Exception {
		List<RvMotivoDeclaracion> lista = new ArrayList<RvMotivoDeclaracion>();
		try {
			String SQL = "SELECT motivo_declaracion_id, descripcion FROM "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_motivo_declaracion ORDER BY descripcion";

			PreparedStatement pst = connect().prepareStatement(SQL);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				RvMotivoDeclaracion obj = new RvMotivoDeclaracion();
				obj.setMotivoDeclaracionId(rs.getInt("motivo_declaracion_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sat.sisat.vehicular.dao.Interfaz#getAllRvAdquisicion()
	 */
	public List<RvAdquisicion> getAllRvAdquisicion() throws Exception {
		List<RvAdquisicion> lista = new ArrayList<RvAdquisicion>();
		try {
			String SQL = "SELECT tipo_adquisicion_id, descripcion FROM "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_adquisicion ORDER BY descripcion";

			PreparedStatement pst = connect().prepareStatement(SQL);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				RvAdquisicion obj = new RvAdquisicion();
				obj.setTipoAdquisicionId(rs.getInt("tipo_adquisicion_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sat.sisat.vehicular.dao.Interfaz#getAllRvCondicionPropiedad()
	 */
	public List<RvCondicionPropiedad> getAllRvCondicionPropiedad()
			throws Exception {
		List<RvCondicionPropiedad> lista = new ArrayList<RvCondicionPropiedad>();
		try {
			String SQL = "SELECT tipo_propiedad_id, descripcion FROM "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_condicion_propiedad ORDER BY descripcion";

			PreparedStatement pst = connect().prepareStatement(SQL);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				RvCondicionPropiedad obj = new RvCondicionPropiedad();
				obj.setTipoPropiedadId(rs.getInt("tipo_propiedad_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sat.sisat.vehicular.dao.Interfaz#getAllGnTipoMoneda()
	 */
	public List<GnTipoMoneda> getAllGnTipoMoneda() throws Exception {
		List<GnTipoMoneda> lista = new ArrayList<GnTipoMoneda>();
		try {
			String SQL = "SELECT tipo_moneda_id, descripcion FROM "
					+ SATParameterFactory.getDBNameScheme()
					+ ".gn_tipo_moneda where estado = '1' ORDER BY descripcion";

			PreparedStatement pst = connect().prepareStatement(SQL);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				GnTipoMoneda obj = new GnTipoMoneda();
				obj.setTipoMonedaId(rs.getInt("tipo_moneda_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sat.sisat.vehicular.dao.Interfaz#getAllGnNotaria()
	 */
	public List<GnNotaria> getAllGnNotaria() throws Exception {
		List<GnNotaria> lista = new ArrayList<GnNotaria>();
		try {
			String SQL = "SELECT notaria_id, nombre_notaria FROM "
					+ SATParameterFactory.getDBNameScheme()
					+ ".gn_notaria ORDER BY nombre_notaria";

			PreparedStatement pst = connect().prepareStatement(SQL);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				GnNotaria obj = new GnNotaria();
				obj.setNotariaId(rs.getInt("notaria_id"));
				obj.setNombreNotaria(rs.getString("nombre_notaria"));
				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	// Buscar persona
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sat.sisat.vehicular.dao.Interfaz#findPersona(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	public List<BuscarPersonaDTO> findPersona(String apePat, String apeMat,
			String nombres) throws Exception {
		List<BuscarPersonaDTO> lista = new ArrayList<BuscarPersonaDTO>();
		try {
			StringBuilder SQL = new StringBuilder(
					"SELECT per.persona_id,tdi.descrpcion_corta,per.nro_docu_identidad,per.apellidos_nombres,per.razon_social  FROM "
							+ SATParameterFactory.getDBNameScheme()
							+ ".mp_persona per ");
			SQL.append("INNER JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".mp_tipo_docu_identidad tdi ON tdi.tipo_doc_identidad_id=per.tipo_doc_identidad_id ");
			SQL.append("WHERE per.estado='1' AND per.ape_paterno LIKE '%"
					+ apePat + "%' AND per.ape_materno LIKE '%" + apeMat
					+ "%' AND per.primer_nombre LIKE '%" + nombres + "%'");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
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
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sat.sisat.vehicular.dao.Interfaz#findPersona(java.lang.String)
	 */
	public List<BuscarPersonaDTO> findPersona(String razonSocial)
			throws Exception {
		List<BuscarPersonaDTO> lista = new ArrayList<BuscarPersonaDTO>();
		try {
			StringBuilder SQL = new StringBuilder(
					"SELECT per.persona_id,tdi.descrpcion_corta,per.nro_docu_identidad,per.apellidos_nombres,per.razon_social  FROM "
							+ SATParameterFactory.getDBNameScheme()
							+ ".mp_persona per ");
			SQL.append("INNER JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".mp_tipo_docu_identidad tdi ON tdi.tipo_doc_identidad_id=per.tipo_doc_identidad_id ");
			SQL.append("WHERE per.estado='1' AND per.razon_social LIKE '%"
					+ razonSocial + "%'");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
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
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sat.sisat.vehicular.dao.Interfaz#findPersona(int,
	 * java.lang.String)
	 */
	public List<BuscarPersonaDTO> findPersona(int tipoDocuIdentidadId,
			String nroDocuIdentidad) throws Exception {
		List<BuscarPersonaDTO> lista = new ArrayList<BuscarPersonaDTO>();
		try {
			StringBuilder SQL = new StringBuilder(
					"SELECT per.persona_id,tdi.descrpcion_corta,per.nro_docu_identidad,per.apellidos_nombres,per.razon_social  FROM "
							+ SATParameterFactory.getDBNameScheme()
							+ ".mp_persona per ");
			SQL.append("INNER JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".mp_tipo_docu_identidad tdi ON tdi.tipo_doc_identidad_id=per.tipo_doc_identidad_id ");
			SQL.append("WHERE per.estado='1' AND per.tipo_doc_identidad_id="
					+ tipoDocuIdentidadId + " AND per.nro_docu_identidad='"
					+ nroDocuIdentidad + "'");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
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
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sat.sisat.vehicular.dao.Interfaz#guardarVehiculo(com.sat.sisat.
	 * persistence.entity.RvVehiculo)
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sat.sisat.vehicular.dao.Interfaz#actualizarDatosVehiculo(com.sat.
	 * sisat.persistence.entity.RvVehiculo)
	 */
	public void actualizarDatosVehiculo(RvVehiculo vehiculo) {
		try {
			StringBuilder SQL = new StringBuilder("UPDATE "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_vehiculo SET ");
			SQL.append("condicion_vehiculo_id="
					+ vehiculo.getCondicionVehiculoId());
			if (vehiculo.getTipoTransmisionId() != 0) {
				SQL.append(",tipo_transmision_id="
						+ vehiculo.getTipoTransmisionId());
			}
			if (vehiculo.getTipoTraccionId() != 0) {
				SQL.append(",tipo_traccion_id=" + vehiculo.getTipoTraccionId());
			}
			SQL.append(",marca_vehiculo_id=" + vehiculo.getMarcaVehiculoId());
			SQL.append(",categoria_vehiculo_id="
					+ vehiculo.getCategoriaVehiculoId());
			SQL.append(",modelo_vehiculo_id=" + vehiculo.getModeloVehiculoId());
			if (vehiculo.getTipoMotorId() != 0) {
				SQL.append(",tipo_motor_id=" + vehiculo.getTipoMotorId());
			}
			SQL.append(",placa='" + vehiculo.getPlaca());
			if(vehiculo.getPlacaAnterior() != null && !vehiculo.getPlacaAnterior().isEmpty()){
				SQL.append("',placa_anterior='" + vehiculo.getPlacaAnterior());
			}			
			SQL.append("',num_motor='" + vehiculo.getNumMotor());
			SQL.append("',anno_fabricacion='" + vehiculo.getAnnoFabricacion());
			SQL.append("',fecha_ins_registros='"
					+ vehiculo.getFechaInsRegistros());
			if (vehiculo.getNumCilindros() != 0) {
				SQL.append("',num_cilindros='" + vehiculo.getNumCilindros());
			}
			if (vehiculo.getPesoBruto() != 0) {
				SQL.append("',peso_bruto='" + vehiculo.getPesoBruto());
			}
			SQL.append("',usuario_id=" + vehiculo.getUsuarioId());
			SQL.append(",fecha_actualizacion='"
					+ vehiculo.getFechaActualizacion());
			// SQL.append("',fecha_registro='" + vehiculo.getFechaRegistro());
			SQL.append("',terminal='" + vehiculo.getTerminal());
			if (vehiculo.getTipoCarroceriaId() != 0) {
				SQL.append("',tipo_carroceria_id='"
						+ vehiculo.getTipoCarroceriaId());
			}
			SQL.append("',clase_vehiculo_id=" + vehiculo.getClaseVehiculoId());
			SQL.append(" WHERE vehiculo_id=" + vehiculo.getVehiculoId());

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.executeUpdate();

		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
			// FIXME : Controller exception
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sat.sisat.vehicular.dao.Interfaz#guardarDJVehicular(com.sat.sisat
	 * .persistence.entity.RvDjvehicular)
	 */
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
			SQL.append("estado,fecha_registro,terminal,flag_dj_anno, fecha_descargo, glosa, djvehicular_nro) ");
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
			SQL.append("'".concat(nroDoc).concat("')"));
			
				
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sat.sisat.vehicular.dao.Interfaz#guardaDetCondVehiculo(com.sat.sisat
	 * .persistence.entity.RvDetCondVehiculo)
	 */
	public int guardaDetCondVehiculo(RvDetCondVehiculo dcv) {
		int dcvId = -1;
		try {
			dcvId = ObtenerCorrelativoTabla("rv_det_cond_vehiculo", 1);

			StringBuilder SQL = new StringBuilder("INSERT INTO "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_det_cond_vehiculo(");
			SQL.append("djvehicular_id,detalle_condicion_id,fecha_documento,fecha_inicial,fecha_final,usuario_id,fecha_actualizacion,");
			SQL.append("tipo_documento_id,nro_documento,tipo_inafectacion,valor_inafectacion,glosa) ");
			SQL.append("VALUES(");
			SQL.append(dcv.getDjvehicularId() + ",");
			SQL.append(dcvId + ",'");
			SQL.append(dcv.getFechaDocumento() + "','");
			SQL.append(dcv.getFechaInicial() + "','");
			SQL.append(dcv.getFechaFinal() + "',");
			SQL.append(dcv.getUsuarioId() + ",'");
			SQL.append(dcv.getFechaActualizacion() + "',");
			SQL.append(dcv.getTipoDocumentoId() + ",'");
			SQL.append(dcv.getNroDocumento() + "','");
			SQL.append(dcv.getTipoInafectacion() + "',");
			SQL.append(dcv.getValorInafectacion() + ",'");
			SQL.append(dcv.getGlosa() + "')");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.executeUpdate();

		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
			// FIXME : Controller exception
		}
		return dcvId;
	}

	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sat.sisat.vehicular.dao.Interfaz#actualizaDetCondVehiculo(com.sat
	 * .sisat.persistence.entity.RvDetCondVehiculo)
	 */
	public void actualizaDetCondVehiculo(RvDetCondVehiculo dcv) {
		try {
			StringBuilder SQL = new StringBuilder("UPDATE "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_det_cond_vehiculo SET ");
			SQL.append("fecha_documento='" + dcv.getFechaDocumento());
			SQL.append("',fecha_inicial='" + dcv.getFechaInicial());
			SQL.append("',fecha_final='" + dcv.getFechaFinal());
			SQL.append("',usuario_id=" + dcv.getUsuarioId());
			SQL.append(",fecha_actualizacion='" + dcv.getFechaActualizacion());
			SQL.append("',tipo_documento_id=" + dcv.getTipoDocumentoId());
			SQL.append(",nro_documento='" + dcv.getNroDocumento());
			SQL.append("',tipo_inafectacion='" + dcv.getTipoInafectacion());
			SQL.append("',valor_inafectacion=" + dcv.getValorInafectacion());
			SQL.append(",glosa='" + dcv.getGlosa());
			SQL.append("' WHERE detalle_condicion_id="
					+ dcv.getDetalleCondicionId());

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.executeUpdate();

		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
			// FIXME : Controller exception
		}
	}

	public void eliminarDetCondVehiculo(int id) {
		try {
			StringBuilder SQL = new StringBuilder("DELETE FROM "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_det_cond_vehiculo ");
			SQL.append("WHERE djvehicular_id=" + id);

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.executeUpdate();

		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
			// FIXME : Controller exception
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sat.sisat.vehicular.dao.Interfaz#obtenerDetCondVehiculo(int)
	 */
	public RvDetCondVehiculo obtenerDetCondVehiculo(int djVehicularId) {
		try {
			String SQL = "SELECT * FROM "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_det_cond_vehiculo WHERE djvehicular_id="
					+ djVehicularId;

			PreparedStatement pst = connect().prepareStatement(SQL);
			ResultSet rs = pst.executeQuery();

			RvDetCondVehiculo dcv = null;
			if (rs.next()) {
				dcv = new RvDetCondVehiculo();
				dcv.setDjvehicularId(rs.getInt("djvehicular_id"));
				dcv.setDetalleCondicionId(rs.getInt("detalle_condicion_id"));
				dcv.setFechaDocumento(rs.getTimestamp("fecha_documento"));
				dcv.setFechaInicial(rs.getTimestamp("fecha_inicial"));
				dcv.setFechaFinal(rs.getTimestamp("fecha_final"));
				dcv.setUsuarioId(rs.getInt("usuario_id"));
				dcv.setFechaActualizacion(rs
						.getTimestamp("fecha_actualizacion"));
				dcv.setTipoDocumentoId(rs.getInt("tipo_documento_id"));
				dcv.setNroDocumento(rs.getString("nro_documento"));
				dcv.setTipoInafectacion(rs.getString("tipo_inafectacion"));
				dcv.setValorInafectacion(rs.getBigDecimal("valor_inafectacion"));
				dcv.setGlosa(rs.getString("glosa"));
			}
			return dcv;
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
			// FIXME : Controller exception
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sat.sisat.vehicular.dao.Interfaz#guardarDescargoDJVehicular(com.sat
	 * .sisat.persistence.entity.RvDjvehicular)
	 */
	public int guardarDescargoDJVehicular(RvDjvehicular dj) throws SisatException {
		int djId = -1;
		int result = -1;
		try {
			djId = ObtenerCorrelativoTabla("rv_djvehicular", 1);
			String nroDoc = ObtenerCorrelativoDocumento("rv_djvehicular",
					"djvehicular_nro");

			StringBuilder SQL = new StringBuilder("INSERT INTO ");
			SQL.append(SATParameterFactory.getDBNameScheme());
			SQL.append(".rv_djvehicular(");
			SQL.append("djvehicular_id,");
			SQL.append("tipo_traccion_id,");
			SQL.append("tipo_transmision_id,");
			SQL.append("tipo_motor_id,");
			SQL.append("marca_vehiculo_id," );
			SQL.append("categoria_vehiculo_id," );
			SQL.append("modelo_vehiculo_id," );
			SQL.append("num_motor," );
			SQL.append("anno_fabricacion," );
			SQL.append("fecha_ins_registros,");
			SQL.append("anno_afectacion,"); // agregando el campo para poder ser utilizado como filtro para los descargos de deudas
			SQL.append("num_cilindros," );
			SQL.append("peso_bruto,");
			SQL.append("tipo_carroceria_id,");
			SQL.append("clase_vehiculo_id,");
			SQL.append("persona_id,");
			SQL.append("motivo_descargo_id,");
			SQL.append("rv_motivo_declaracion_id,");
			SQL.append("condicion_vehiculo_id,");
			SQL.append("notaria_id,");
			SQL.append("vehiculo_id,");
			SQL.append("fecha_declaracion,");
			SQL.append("anno_declaracion,");
			SQL.append("usuario_id,");
			SQL.append("fecha_actualizacion,");
			SQL.append("estado,");
			SQL.append("fecha_registro,");
			SQL.append("terminal,");
			SQL.append("djvehicular_nro, ");
			SQL.append("glosa, ");
			SQL.append("val_adq_soles, ");
			SQL.append("fecha_descargo) ");
			
			SQL.append("VALUES(");
			
//			SQL.append(djId).append(",");
//			SQL.append(dj.getTipoTraccionId() == 0 ? "null," : dj.getTipoTraccionId() + ",");
//			SQL.append(dj.getTipoTransmisionId() == 0 ? "null," : dj.getTipoTransmisionId() + ",");
//			SQL.append(dj.getTipoMotorId() == 0 ? "null," : dj.getTipoMotorId()+ ",");
//			SQL.append(dj.getMarcaVehiculoId()).append(",");
//			SQL.append(dj.getCategoriaVehiculoId()).append(",");
//			SQL.append(dj.getModeloVehiculoId()).append(",'");
//			SQL.append(dj.getNumMotor()).append("',");
//			SQL.append(dj.getAnnoFabricacion()).append(",'");
//			SQL.append(dj.getFechaInsRegistros()).append("',");
//			SQL.append(dj.getAnnoAfectacion()).append(","); // agregando el campo para poder ser utilizado como filtro para los descargos de deudas
//			SQL.append(dj.getNumCilindros() == 0 ? "null," : dj.getNumCilindros() + ",");
//			SQL.append(dj.getPesoBruto() == 0 ? "null," : dj.getPesoBruto() + ",");
//			SQL.append(dj.getTipoCarroceriaId() == 0 ? "null," : dj.getTipoCarroceriaId() + ",");
//			SQL.append(dj.getClaseVehiculoId() == 0 ? "null," : dj.getClaseVehiculoId() + ",");
//			SQL.append(dj.getPersonaId()).append(",");
//			SQL.append(dj.getRvMotivoDescargo().getMotivoDescargoId()).append(",");
//			SQL.append(dj.getRvMotivoDeclaracionId()).append(",");
//			SQL.append(dj.getCondicionVehiculoId()).append(",");
//			SQL.append(dj.getNotariaId()).append(",");
//			SQL.append(dj.getVehiculoId()).append(",'");
//			SQL.append(dj.getFechaDeclaracion()).append("','");
//			SQL.append(dj.getAnnoDeclaracion()).append("',");
//			SQL.append(dj.getUsuarioId()).append(",'");
//			SQL.append(dj.getFechaActualizacion()).append("','");
//			SQL.append(dj.getEstado()).append("','");
//			SQL.append(dj.getFechaRegistro()).append("','");
//			SQL.append(dj.getTerminal()).append("','");
//			SQL.append(nroDoc).append("','");
//			SQL.append(dj.getFechaDescargo()).append("')");

			
			SQL.append(djId).append(",");
			SQL.append(dj.getTipoTraccionId() == 0 ? "null," : dj.getTipoTraccionId() + ",");
			SQL.append(dj.getTipoTransmisionId() == 0 ? "null," : dj.getTipoTransmisionId() + ",");
			SQL.append(dj.getTipoMotorId() == 0 ? "null," : dj.getTipoMotorId()+ ",");
			SQL.append(dj.getMarcaVehiculoId()).append(",");
			SQL.append(dj.getCategoriaVehiculoId()).append(",");
			SQL.append(dj.getModeloVehiculoId()).append(",'");
			SQL.append(dj.getNumMotor()).append("',");
			SQL.append(dj.getAnnoFabricacion()).append(",");
			SQL.append("convert(datetime,'").append((dj.getFechaInsRegistros())).append("',111").append("),");
			SQL.append(dj.getAnnoAfectacion()).append(","); // agregando el campo para poder ser utilizado como filtro para los descargos de deudas
			SQL.append(dj.getNumCilindros() == 0 ? "null," : dj.getNumCilindros() + ",");
			SQL.append(dj.getPesoBruto() == 0 ? "null," : dj.getPesoBruto() + ",");
			SQL.append(dj.getTipoCarroceriaId() == 0 ? "null," : dj.getTipoCarroceriaId() + ",");
			SQL.append(dj.getClaseVehiculoId() == 0 ? "null," : dj.getClaseVehiculoId() + ",");
			SQL.append(dj.getPersonaId()).append(",");
			SQL.append(dj.getRvMotivoDescargo().getMotivoDescargoId()).append(",");
			SQL.append(dj.getRvMotivoDeclaracionId()).append(",");
			SQL.append(dj.getCondicionVehiculoId()).append(",");
			SQL.append(dj.getNotariaId()).append(",");
			SQL.append(dj.getVehiculoId()).append(",");
			SQL.append("convert(datetime,'").append((dj.getFechaDeclaracion())).append("',111").append("),'");
			SQL.append(dj.getAnnoDeclaracion()).append("',");
			SQL.append(dj.getUsuarioId()).append(",");
			SQL.append("convert(datetime,'").append((dj.getFechaActualizacion())).append("',111").append("),'");
			SQL.append(dj.getEstado()).append("',");
			SQL.append("convert(datetime,'").append((dj.getFechaRegistro())).append("',111").append("),'");
			SQL.append(dj.getTerminal()).append("','");
			SQL.append(nroDoc).append("','");
			SQL.append(dj.getGlosa()).append("',");
			SQL.append(dj.getValAdqSoles()).append(",");
			SQL.append("convert(datetime,'").append((dj.getFechaDescargo())).append("',111)").append(")");

			
			PreparedStatement pst = connect().prepareStatement(SQL.toString());

			result = pst.executeUpdate();
		} catch (Exception ex) {
			throw new SisatException(ex.getMessage(), ex.getCause());
		}

		if (result != 1) {
			djId = -1;
		}

		return djId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sat.sisat.vehicular.dao.Interfaz#actualizarDJVehicular(com.sat.sisat
	 * .persistence.entity.RvDjvehicular)
	 */
	public void actualizarDJVehicular(RvDjvehicular dj) throws SisatException {
		try {
			StringBuilder SQL = new StringBuilder("UPDATE "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_djvehicular SET ");
			if (dj.getTipoTraccionId() != 0) {
				SQL.append("tipo_traccion_id=" + dj.getTipoTraccionId() + ",");
			}
			if (dj.getTipoTransmisionId() != 0) {
				SQL.append("tipo_transmision_id=" + dj.getTipoTransmisionId()
						+ ",");
			}
			if (dj.getTipoMotorId() != 0) {
				SQL.append("tipo_motor_id=" + dj.getTipoMotorId() + ",");
			}
			SQL.append("tipo_moneda_id=" + dj.getTipoMonedaId() + ",");
			SQL.append("tipo_propiedad_id=" + dj.getTipoPropiedadId());
			SQL.append(",marca_vehiculo_id=" + dj.getMarcaVehiculoId());
			SQL.append(",categoria_vehiculo_id=" + dj.getCategoriaVehiculoId());
			SQL.append(",modelo_vehiculo_id=" + dj.getModeloVehiculoId());
			SQL.append(",num_motor='" + dj.getNumMotor());
			SQL.append("',anno_fabricacion=" + dj.getAnnoFabricacion());
			SQL.append(",fecha_ins_registros='" + dj.getFechaInsRegistros()
					+ "'");
			if (dj.getNumCilindros() != 0) {
				SQL.append(",num_cilindros=" + dj.getNumCilindros());
			}
			if (dj.getPesoBruto() != 0) {
				SQL.append(",peso_bruto=" + dj.getPesoBruto());
			}
			if (dj.getTipoCarroceriaId() != 0) {
				SQL.append(",tipo_carroceria_id=" + dj.getTipoCarroceriaId());
			}
			if (dj.getClaseVehiculoId() != 0) {
				SQL.append(",clase_vehiculo_id=" + dj.getClaseVehiculoId());
			}
			SQL.append(",persona_id=" + dj.getPersonaId());
			SQL.append(",tipo_adquisicion_id=" + dj.getTipoAdquisicionId());
			SQL.append(",rv_motivo_declaracion_id="
					+ dj.getRvMotivoDeclaracionId());
			SQL.append(",condicion_vehiculo_id=" + dj.getCondicionVehiculoId());
			SQL.append(",notaria_id=" + dj.getNotariaId());
			SQL.append(",vehiculo_id=" + dj.getVehiculoId());
			SQL.append(",fecha_declaracion='" + dj.getFechaDeclaracion());
			SQL.append("',anno_ini_afectacion=" + dj.getAnnoIniAfectacion());
			SQL.append(",anno_fin_afectacion=" + dj.getAnnoFinAfectacion());
			if (dj.getAnnoAfectacion() != 0) {
				SQL.append(",anno_afectacion=" + dj.getAnnoAfectacion());
			}
			SQL.append(",num_tarjeta_propiedad='" + dj.getNumTarjetaPropiedad());
			SQL.append("',fecha_adquisicion='" + dj.getFechaAdquisicion());
			SQL.append("',val_adq_soles=" + dj.getValAdqSoles());
			if (dj.getValAdqOtraMoneda() != null) {
				SQL.append(",val_adq_otra_moneda=" + dj.getValAdqOtraMoneda());
				SQL.append(",tipo_cambio_id=" + dj.getTipoCambioId());
			} else {
				SQL.append(",val_adq_otra_moneda=null");
				SQL.append(",tipo_cambio_id=null");
			}
			SQL.append(",porc_propiedad=" + dj.getPorcPropiedad());
			SQL.append(",anno_declaracion=" + dj.getAnnoDeclaracion());
			
			SQL.append(",fiscalizado=" + dj.getFiscalizado());
			SQL.append(",fisca_aceptada=" + dj.getFiscaAceptada());
			SQL.append(",fisca_cerrada=" + dj.getFiscaCerrada());
			
			SQL.append(",usuario_id=" + dj.getUsuarioId());
			SQL.append(",fecha_actualizacion='" + dj.getFechaActualizacion());
			SQL.append("',estado='" + dj.getEstado());
			// SQL.append("',fecha_registro='" + dj.getFechaRegistro());
			SQL.append("',terminal='" + dj.getTerminal());
			if(dj.getGlosa() != null && !dj.getGlosa().isEmpty()){			
				SQL.append("',glosa='" + dj.getGlosa());			
			}
			if(dj.getDjvehicularNro() != null){
				SQL.append("',djvehicular_nro='" + dj.getDjvehicularNro());
			}
			SQL.append("' WHERE djvehicular_id=" + dj.getDjvehicularId());

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.executeUpdate();

		} catch (Exception ex) {
			// Controller exception
			throw new SisatException(ex.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sat.sisat.vehicular.dao.Interfaz#eliminarTransferentesDJ(int)
	 */
	public void eliminarTransferentesDJ(int djvehicularId) {
		try {
			String SQL = "DELETE FROM " + SATParameterFactory.getDBNameScheme()
					+ ".rv_transferencia_propiedad WHERE djvehicular_id="
					+ djvehicularId;

			PreparedStatement pst = connect().prepareStatement(SQL);
			pst.executeUpdate();

		} catch (Exception ex) {
			// Controller exception
			System.out.println("ERROR: " + ex);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sat.sisat.vehicular.dao.Interfaz#guardarTransferenteAdquiriente(java
	 * .util.List, com.sat.sisat.persistence.entity.RvTransferenciaPropiedad)
	 */
	public void guardarTransferenteAdquiriente(
			List<BuscarPersonaDTO> transfers, RvTransferenciaPropiedad tp) {
		try {
			for (BuscarPersonaDTO per : transfers) {
				int transId = ObtenerCorrelativoTabla(
						"rv_transferencia_propiedad", 1);
				StringBuilder SQL = new StringBuilder("INSERT INTO "
						+ SATParameterFactory.getDBNameScheme()
						+ ".rv_transferencia_propiedad(");
				SQL.append("transferencia_id,djvehicular_id,persona_id,tipo,fecha_actualizacion,usuario_id,fecha_registro,estado,terminal) ");
				SQL.append("VALUES(");
				SQL.append(transId + "," + tp.getDjvehicularId() + ","
						+ per.getPersonaId() + ",'" + tp.getTipo() + "','"
						+ tp.getFechaActualizacion() + "',");
				SQL.append(tp.getUsuarioId() + ",'" + tp.getFechaRegistro()
						+ "','" + tp.getEstado() + "','" + tp.getTerminal()
						+ "')");

				PreparedStatement pst = connect().prepareStatement(
						SQL.toString());
				pst.executeUpdate();
			}
		} catch (Exception ex) {
			// Controller exception
			System.out.println("ERROR: " + ex);
		}
	}
	public void guardarTransferente(RvTransferenciaPropiedad tp) throws SisatException {
		try {
			
				int transId = ObtenerCorrelativoTabla("rv_transferencia_propiedad", 1);
				StringBuilder SQL = new StringBuilder("INSERT INTO "
						+ SATParameterFactory.getDBNameScheme()
						+ ".rv_transferencia_propiedad(");
				SQL.append("transferencia_id,djvehicular_id,persona_id,tipo,fecha_actualizacion,usuario_id,fecha_registro,estado,terminal) ");
				SQL.append("VALUES(");
				SQL.append(transId + "," + tp.getDjvehicularId() + ","
						+ tp.getPersonaId() + ",'" + tp.getTipo() + "','"
						+ tp.getFechaActualizacion() + "',");
				SQL.append(tp.getUsuarioId() + ",'" + tp.getFechaRegistro()
						+ "','" + tp.getEstado() + "','" + tp.getTerminal()
						+ "')");

				PreparedStatement pst = connect().prepareStatement(
						SQL.toString());
				pst.executeUpdate();
			
		} catch (Exception ex) {
			// Controller exception
			System.out.println("ERROR: " + ex);
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sat.sisat.vehicular.dao.Interfaz#eliminarAnexosDJ(int)
	 */
	public void eliminarAnexosDJ(int djvehicularId) {
		try {
			String SQL = "DELETE FROM " + SATParameterFactory.getDBNameScheme()
					+ ".rv_sustento_vehicular WHERE djvehicular_id="
					+ djvehicularId;

			PreparedStatement pst = connect().prepareStatement(SQL);
			pst.executeUpdate();

		} catch (Exception ex) {
			// Controller exception
			System.out.println("ERROR: " + ex);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sat.sisat.vehicular.dao.Interfaz#guardarDocAnexosDjVehicular(java
	 * .util.List, com.sat.sisat.persistence.entity.RvSustentoVehicular)
	 */
	public void guardarDocAnexosDjVehicular(List<AnexosDeclaVehicDTO> anexos,
			RvSustentoVehicular sv) {
		try {
			for (AnexosDeclaVehicDTO an : anexos) {
				int sustenId = ObtenerCorrelativoTabla("rv_sustento_vehicular",
						1);
				StringBuilder SQL = new StringBuilder("INSERT INTO "
						+ SATParameterFactory.getDBNameScheme()
						+ ".rv_sustento_vehicular(");
				SQL.append("djvehicular_id,sustento_id,doc_sustentatorio_id,nro_documento,referencia,content_id,fecha_registro,usuario_id,terminal) ");
				SQL.append("VALUES(");
				SQL.append(sv.getId().getDjvehicularId() + "," + sustenId + ","
						+ an.getDocSustentatorioId() + ",'" + an.getGlosaDoc()
						+ "',");
				if (an.getNomDocAdjunto() != null && an.getContentId() != null) {
					SQL.append("'" + an.getNomDocAdjunto() + "',"
							+ an.getContentId() + ",'");
				} else {
					SQL.append(null + "," + null + ",'");
				}
				SQL.append(sv.getFechaRegistro() + "'," + sv.getUsuarioId()
						+ ",'" + sv.getTerminal() + "')");

				PreparedStatement pst = connect().prepareStatement(
						SQL.toString());
				pst.executeUpdate();
			}
		} catch (Exception ex) {
			// Controller exception
			System.out.println("ERROR: " + ex);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sat.sisat.vehicular.dao.Interfaz#generarDJ(int)
	 */
	public boolean activarDJVehicular(int djId) throws SisatException {
		int res = 0;
		try {

//			String nroDoc = ObtenerCorrelativoDocumento("rv_djvehicular",
//					"djvehicular_nro");

			String SQL = "UPDATE " + SATParameterFactory.getDBNameScheme()
					+ ".rv_djvehicular SET estado='1' WHERE djvehicular_id=" + djId;
			PreparedStatement pst = connect().prepareStatement(SQL);
			res = pst.executeUpdate();
		} catch (Exception ex) {
			throw new SisatException(ex.getMessage());
		}

		if (res == 1) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sat.sisat.vehicular.dao.Interfaz#findDjVehicular(int)
	 */
	public List<BuscarVehicularDTO> findDjVehicular(int personaId)
			throws Exception {
		List<BuscarVehicularDTO> lista = new ArrayList<BuscarVehicularDTO>();
		try {
				//			StringBuilder SQL = new StringBuilder(
				//					"SELECT ve.placa, ve.num_motor AS nromotor, ca.descripcion AS categoria, ma.descripcion AS marca, mo.descripcion AS modelo, ");
				//			SQL.append("djv.djvehicular_id AS djId, md.descripcion AS motivodecla, djv.fecha_declaracion AS fecha, djv.estado, ve.fecha_ins_registros AS fechaInsReg, ");
				//			SQL.append("ve.anno_fabricacion AS anioFabric, ve.vehiculo_id AS vehicId, md.motivo_declaracion_id AS motivoDeclaId, djvehicular_nro AS djNro, djv.fecha_adquisicion, djv.fecha_descargo,djv.requerimiento_id FROM "
				//					+ SATParameterFactory.getDBNameScheme()
				//					+ ".rv_djvehicular djv ");
				//			SQL.append("INNER JOIN " + SATParameterFactory.getDBNameScheme()
				//					+ ".rv_vehiculo ve ON ve.vehiculo_id = djv.vehiculo_id ");
				//			SQL.append("INNER JOIN "
				//					+ SATParameterFactory.getDBNameScheme()
				//					+ ".rv_modelo_vehiculo mo ON mo.modelo_vehiculo_id = djv.modelo_vehiculo_id AND mo.categoria_vehiculo_id=djv.categoria_vehiculo_id AND mo.marca_vehiculo_id = djv.marca_vehiculo_id ");
				//			SQL.append("INNER JOIN "
				//					+ SATParameterFactory.getDBNameScheme()
				//					+ ".rv_marca ma ON ma.marca_vehiculo_id = ve.marca_vehiculo_id ");
				//			SQL.append("INNER JOIN "
				//					+ SATParameterFactory.getDBNameScheme()
				//					+ ".rv_categoria_vehiculo ca ON ca.categoria_vehiculo_id = ve.categoria_vehiculo_id ");
				//			SQL.append("INNER JOIN "
				//					+ SATParameterFactory.getDBNameScheme()
				//					+ ".rv_motivo_declaracion md ON md.motivo_declaracion_id = djv.rv_motivo_declaracion_id ");
				//			
				//			SQL.append("INNER JOIN ( ");
				//			SQL.append(" SELECT MAX(d.djvehicular_id) ultdj, v.placa, d.persona_id ");
				//			SQL.append("	from ").append(SATParameterFactory.getDBNameScheme()).append(".rv_djvehicular d ");
				//			SQL.append("		inner join ").append(SATParameterFactory.getDBNameScheme()).append(".rv_vehiculo v ON v.vehiculo_id = d.vehiculo_id ");
				//			SQL.append("		where  (d.estado='1' OR d.estado='2' OR d.estado='3') AND d.flag_dj_anno = 1 ");
				//			//SQL.append("		where  d.estado !='9' AND d.flag_dj_anno = 1 ");
				//			SQL.append("		group by v.placa, d.persona_id ");
				//			SQL.append(" ) ult_dj on(ult_dj.persona_id = djv.persona_id and ult_dj.placa = ve.placa and ult_dj.ultdj = djv.djvehicular_id) ");
				//			
				//			SQL.append("WHERE djv.persona_id=" + personaId);
				//			SQL.append(" AND (djv.estado='1' OR djv.estado='2' OR djv.estado='3') AND djv.flag_dj_anno = 1  ORDER BY ve.placa");
				//			//SQL.append(" AND djv.estado != '9' AND djv.flag_dj_anno = 1  ORDER BY ve.placa");

			StringBuilder SQL = new StringBuilder("dbo.stp_rv_obtener_findDjVehicular ?");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, personaId);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				BuscarVehicularDTO obj = new BuscarVehicularDTO();
				obj.setDjVehicularId(rs.getInt("djId"));
				obj.setMotivoDecla(rs.getString("motivodecla"));
				obj.setPlaca(rs.getString("placa"));
				obj.setNroMotor(rs.getString("nromotor"));
				obj.setCategoria(rs.getString("categoria"));
				obj.setMarca(rs.getString("marca"));
				obj.setModelo(rs.getString("modelo"));
				obj.setEstado(rs.getString("estado"));
				obj.setFecha(rs.getTimestamp("fecha"));
				obj.setFechaInsReg(rs.getTimestamp("fechaInsReg"));
				obj.setAnioFabric(rs.getString("anioFabric"));
				obj.setVehiculoId(rs.getInt("vehicId"));
				obj.setMotivoDeclaId(rs.getInt("motivoDeclaId"));
				obj.setDjvehicularNro(rs.getString("djNro"));
				obj.setFechaAdquisicion(rs.getTimestamp("fecha_adquisicion"));
				obj.setRequerimientoId(rs.getInt("requerimiento_id"));
				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sat.sisat.vehicular.dao.Interfaz#findDjVehicular(int,
	 * java.lang.String)
	 */
	public List<BuscarVehicularDTO> findDjVehicular(int personaId, String placa)
			throws Exception {
		List<BuscarVehicularDTO> lista = new ArrayList<BuscarVehicularDTO>();
		try {
			StringBuilder SQL = new StringBuilder(
					"SELECT ve.placa, ve.num_motor AS nromotor, ca.descripcion AS categoria, ma.descripcion AS marca, mo.descripcion AS modelo, ");
			SQL.append("djv.djvehicular_id AS djId, md.descripcion AS motivodecla, djv.fecha_declaracion AS fecha, djv.estado, ve.fecha_ins_registros AS fechaInsReg, ");
			SQL.append("ve.anno_fabricacion AS anioFabric, ve.vehiculo_id AS vehicId, md.motivo_declaracion_id AS motivoDeclaId, djvehicular_nro AS djNro, djv.fecha_adquisicion,djv.djvehicular_previo_id FROM "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_djvehicular djv ");
			SQL.append("INNER JOIN " + SATParameterFactory.getDBNameScheme()
					+ ".rv_vehiculo ve ON ve.vehiculo_id = djv.vehiculo_id ");
			// SQL.append("INNER JOIN "+ SATParameterFactory.getDBNameScheme() +
			// ".rv_modelo_vehiculo mo ON mo.modelo_vehiculo_id = ve.modelo_vehiculo_id AND mo.categoria_vehiculo_id=djv.categoria_vehiculo_id AND mo.marca_vehiculo_id = djv.marca_vehiculo_id ");
			SQL.append("INNER JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_modelo_vehiculo mo ON mo.modelo_vehiculo_id = ve.modelo_vehiculo_id ");
			SQL.append("INNER JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_marca ma ON ma.marca_vehiculo_id = ve.marca_vehiculo_id ");
			SQL.append("INNER JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_categoria_vehiculo ca ON ca.categoria_vehiculo_id = ve.categoria_vehiculo_id ");
			SQL.append("INNER JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_motivo_declaracion md ON md.motivo_declaracion_id = djv.rv_motivo_declaracion_id ");
			SQL.append("WHERE djv.persona_id=" + personaId + " AND ve.placa='"
					+ placa + "'");
			SQL.append(" AND (djv.estado='1' OR djv.estado='2' OR djv.estado='3'OR djv.estado='6')");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				BuscarVehicularDTO obj = new BuscarVehicularDTO();
				obj.setDjVehicularId(rs.getInt("djId"));
				obj.setMotivoDecla(rs.getString("motivodecla"));
				obj.setPlaca(rs.getString("placa"));
				obj.setNroMotor(rs.getString("nromotor"));
				obj.setCategoria(rs.getString("categoria"));
				obj.setMarca(rs.getString("marca"));
				obj.setModelo(rs.getString("modelo"));
				obj.setEstado(rs.getString("estado"));
				obj.setFecha(rs.getTimestamp("fecha"));
				obj.setFechaInsReg(rs.getTimestamp("fechaInsReg"));
				obj.setAnioFabric(rs.getString("anioFabric"));
				obj.setVehiculoId(rs.getInt("vehicId"));
				obj.setMotivoDeclaId(rs.getInt("motivoDeclaId"));
				obj.setDjvehicularNro(rs.getString("djNro"));
				obj.setDjVehicularPrevioId(rs.getInt("djvehicular_previo_id"));
				obj.setFechaAdquisicion(rs.getTimestamp("fecha_adquisicion"));
				lista.add(obj);
			}
		} catch (Exception e) {
			// TODO : Controller exception
			throw (e);
		}
		return lista;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sat.sisat.vehicular.dao.Interfaz#findDjVehicularById(int)
	 */
	public RvDjvehicular findDjVehicularById(int id) throws SisatException {
		try {
			String SQL = "SELECT * FROM "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_djvehicular WHERE djvehicular_id=" + id;
			PreparedStatement pst = connect().prepareStatement(SQL);
			ResultSet rs = pst.executeQuery();
			RvDjvehicular obj = null;
			while (rs.next()) {
				obj = new RvDjvehicular();
				obj.setDjvehicularId(rs.getInt("djvehicular_id"));
				obj.setTipoTraccionId(rs.getInt("tipo_traccion_id"));
				obj.setTipoTransmisionId(rs.getInt("tipo_transmision_id"));
				obj.setTipoMotorId(rs.getInt("tipo_motor_id"));
				obj.setTipoMonedaId(rs.getInt("tipo_moneda_id"));
				obj.setTipoPropiedadId(rs.getInt("tipo_propiedad_id"));
				obj.setMarcaVehiculoId(rs.getInt("marca_vehiculo_id"));
				obj.setCategoriaVehiculoId(rs.getInt("categoria_vehiculo_id"));
				obj.setModeloVehiculoId(rs.getInt("modelo_vehiculo_id"));
				obj.setNumMotor(rs.getString("num_motor"));
				obj.setAnnoFabricacion(rs.getInt("anno_fabricacion"));
				obj.setFechaInsRegistros(rs.getTimestamp("fecha_ins_registros"));
				obj.setNumCilindros(rs.getInt("num_cilindros"));
				obj.setPesoBruto(rs.getInt("peso_bruto"));
				obj.setTipoCarroceriaId(rs.getInt("tipo_carroceria_id"));
				obj.setClaseVehiculoId(rs.getInt("clase_vehiculo_id"));
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setTipoAdquisicionId(rs.getInt("tipo_adquisicion_id"));
				obj.setRvMotivoDeclaracionId(rs
						.getInt("rv_motivo_declaracion_id"));
				obj.setCondicionVehiculoId(rs.getInt("condicion_vehiculo_id"));
				obj.setNotariaId(rs.getInt("notaria_id"));
				obj.setVehiculoId(rs.getInt("vehiculo_id"));
				RvMotivoDescargo md = new RvMotivoDescargo();
				md.setMotivoDescargoId(rs.getInt("motivo_descargo_id"));
				obj.setRvMotivoDescargo(md);
				obj.setFechaDeclaracion(rs.getTimestamp("fecha_declaracion"));
				obj.setNumTarjetaPropiedad(rs
						.getString("num_tarjeta_propiedad"));
				obj.setFechaAdquisicion(rs.getTimestamp("fecha_adquisicion"));
				obj.setValAdqSoles(rs.getBigDecimal("val_adq_soles"));
				obj.setValAdqOtraMoneda(rs.getBigDecimal("val_adq_otra_moneda"));
				obj.setTipoCambioId(rs.getInt("tipo_cambio_id"));
				obj.setPorcPropiedad(rs.getBigDecimal("porc_propiedad"));
				//quitando la siguiente linea debio a que causaba confusion entre el anho de afectacion y el anho de la DJ, 
				//por consiguiente se quita anho DJ quedando solo en vez de eso solo anho_afectacion
				//obj.setAnnoDeclaracion(rs.getString("anno_declaracion"));
				obj.setAnnoDeclaracion(String.valueOf(rs.getInt("anno_afectacion")));
				obj.setUsuarioId(rs.getInt("usuario_id"));
				obj.setFechaActualizacion(rs
						.getTimestamp("fecha_actualizacion"));
				obj.setEstado(rs.getString("estado"));
				obj.setFechaRegistro(rs.getTimestamp("fecha_registro"));
				obj.setTerminal(rs.getString("terminal"));
				obj.setDjvehicularNro(rs.getString("djvehicular_nro"));
				obj.setFlagDjAnno(rs.getString("flag_dj_anno"));
				obj.setDjvehicularPrevioId(rs.getInt("djvehicular_previo_id"));
				obj.setFechaAdquisicion(rs.getTimestamp("fecha_adquisicion"));
				obj.setGlosa(rs.getString("glosa"));
				obj.setAnnoIniAfectacion(rs.getInt("anno_ini_afectacion"));
				obj.setAnnoFinAfectacion(rs.getInt("anno_fin_afectacion"));
				obj.setAnnoAfectacion(rs.getInt("anno_afectacion"));
				obj.setFechaDescargo(rs.getTimestamp("fecha_descargo"));
			}
			return obj;
		} catch (Exception ex) {
			throw new SisatException(ex.getMessage());
		}	
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sat.sisat.vehicular.dao.Interfaz#findVehiculoById(int)
	 */
	public RvVehiculo findVehiculoById(int id) {
		try {
			String SQL = "SELECT * FROM "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_vehiculo WHERE vehiculo_id=" + id;
			PreparedStatement pst = connect().prepareStatement(SQL);
			ResultSet rs = pst.executeQuery();
			RvVehiculo obj = null;
			while (rs.next()) {
				obj = new RvVehiculo();
				obj.setVehiculoId(rs.getInt("vehiculo_id"));
				obj.setCondicionVehiculoId(rs.getInt("condicion_vehiculo_id"));
				obj.setTipoTransmisionId(rs.getInt("tipo_transmision_id"));
				obj.setTipoTraccionId(rs.getInt("tipo_traccion_id"));
				obj.setMarcaVehiculoId(rs.getInt("marca_vehiculo_id"));
				obj.setCategoriaVehiculoId(rs.getInt("categoria_vehiculo_id"));
				obj.setModeloVehiculoId(rs.getInt("modelo_vehiculo_id"));
				obj.setTipoMotorId(rs.getInt("tipo_motor_id"));
				obj.setPlaca(rs.getString("placa"));
				obj.setPlacaAnterior(rs.getString("placa_anterior"));
				obj.setNumMotor(rs.getString("num_motor"));
				obj.setAnnoFabricacion(rs.getInt("anno_fabricacion"));
				obj.setFechaInsRegistros(rs.getTimestamp("fecha_ins_registros"));
				obj.setNumCilindros(rs.getInt("num_cilindros"));
				obj.setPesoBruto(rs.getInt("peso_bruto"));
				obj.setUsuarioId(rs.getInt("usuario_id"));
				obj.setFechaActualizacion(rs
						.getTimestamp("fecha_actualizacion"));
				obj.setFechaRegistro(rs.getTimestamp("fecha_registro"));
				obj.setTerminal(rs.getString("terminal"));
				obj.setTipoCarroceriaId(rs.getInt("tipo_carroceria_id"));
				obj.setClaseVehiculoId(rs.getInt("clase_vehiculo_id"));
			}
			return obj;
		} catch (Exception ex) {
			// TODO : Controller Exception
			System.out.println("ERROR: " + ex);
		}
		return null;
	}

	public RvVehiculoDTO findVehiculoDTOById(int id) {
		try {
			StringBuilder SQL = new StringBuilder(
					"SELECT ve.*, cv.descripcion AS desCondEspVehic, ttr.descripcion AS desTipoTransmi, ttc.descripcion AS desTipoTracci, ");
			SQL.append("mar.descripcion AS desMarca, cat.descripcion AS desCatVehic, mo.descripcion AS desModelo, tm.descripcion AS desTipoMotor, ");
			SQL.append("tc.descripcion AS desTipoCarroce, clv.descripcion AS desClaseVehic FROM "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_vehiculo ve ");
			SQL.append("INNER JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_condicion_vehiculo cv ON cv.condicion_vehiculo_id = ve.condicion_vehiculo_id ");
			SQL.append("LEFT JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_tipo_transmision ttr ON ttr.tipo_transmision_id = ve.tipo_transmision_id ");
			SQL.append("LEFT JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_tipo_traccion ttc ON ttc.tipo_traccion_id = ve.tipo_traccion_id ");
			SQL.append("INNER JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_marca mar ON mar.marca_vehiculo_id = ve.marca_vehiculo_id ");
			SQL.append("INNER JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_categoria_vehiculo cat ON cat.categoria_vehiculo_id = ve.categoria_vehiculo_id ");
			SQL.append("INNER JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_modelo_vehiculo mo ON mo.modelo_vehiculo_id = ve.modelo_vehiculo_id ");
			SQL.append("LEFT JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_tipo_motor tm ON tm.tipo_motor_id = ve.tipo_motor_id ");
			SQL.append("LEFT JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_tipo_carroceria tc ON tc.tipo_carroceria_id = ve.tipo_carroceria_id ");
			SQL.append("LEFT JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_clase_vehiculo clv ON clv.clase_vehiculo_id = ve.clase_vehiculo_id ");
			SQL.append("WHERE vehiculo_id=" + id);
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			RvVehiculoDTO obj = null;
			while (rs.next()) {
				obj = new RvVehiculoDTO();
				obj.setVehiculoId(rs.getInt("vehiculo_id"));
				obj.setCondicionVehiculoId(rs.getInt("condicion_vehiculo_id"));
				obj.setTipoTransmisionId(rs.getInt("tipo_transmision_id"));
				obj.setTipoTraccionId(rs.getInt("tipo_traccion_id"));
				obj.setMarcaVehiculoId(rs.getInt("marca_vehiculo_id"));
				obj.setCategoriaVehiculoId(rs.getInt("categoria_vehiculo_id"));
				obj.setModeloVehiculoId(rs.getInt("modelo_vehiculo_id"));
				obj.setTipoMotorId(rs.getInt("tipo_motor_id"));
				obj.setPlaca(rs.getString("placa"));
				obj.setNumMotor(rs.getString("num_motor"));
				obj.setAnnoFabricacion(rs.getInt("anno_fabricacion"));
				obj.setFechaInsRegistros(rs.getTimestamp("fecha_ins_registros"));
				obj.setNumCilindros(rs.getInt("num_cilindros"));
				obj.setPesoBruto(rs.getInt("peso_bruto"));
				obj.setUsuarioId(rs.getInt("usuario_id"));
				obj.setFechaActualizacion(rs
						.getTimestamp("fecha_actualizacion"));
				obj.setFechaRegistro(rs.getTimestamp("fecha_registro"));
				obj.setTerminal(rs.getString("terminal"));
				obj.setTipoCarroceriaId(rs.getInt("tipo_carroceria_id"));
				obj.setClaseVehiculoId(rs.getInt("clase_vehiculo_id"));
				obj.setDesCondEspVehic(rs.getString("desCondEspVehic"));
				obj.setDesTipoTransmi(rs.getString("desTipoTransmi"));
				obj.setDesTipoTracci(rs.getString("desTipoTracci"));
				obj.setDesMarca(rs.getString("desMarca"));
				obj.setDesCatVehic(rs.getString("desCatVehic"));
				obj.setDesModelo(rs.getString("desModelo"));
				obj.setDesTipoMotor(rs.getString("desTipoMotor"));
				obj.setDesTipoCarroce(rs.getString("desTipoCarroce"));
				obj.setDesClaseVehic(rs.getString("desClaseVehic"));
			}
			return obj;
		} catch (Exception ex) {
			// TODO : Controller Exception
			System.out.println("ERROR: " + ex);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sat.sisat.vehicular.dao.Interfaz#findTransferentes(int)
	 */
	public List<BuscarPersonaDTO> findTransferentes(int djvehicularId) {
		List<BuscarPersonaDTO> lista = new ArrayList<BuscarPersonaDTO>();
		try {
			StringBuilder SQL = new StringBuilder(
					"SELECT per.persona_id, di.descrpcion_corta, per.nro_doc_identidad, per.apellidos_nombres, per.razon_social  FROM "
							+ SATParameterFactory.getDBNameScheme()
							+ ".rv_transferencia_propiedad tp ");
			SQL.append("INNER JOIN " + SATParameterFactory.getDBNameScheme()
					+ ".gn_persona per ON per.persona_id = tp.persona_id ");
			SQL.append("INNER JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".mp_tipo_docu_identidad di ON di.tipo_doc_identidad_id = per.tipo_documento_id ");
			SQL.append("WHERE tp.djvehicular_id=" + djvehicularId);

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				BuscarPersonaDTO obj = new BuscarPersonaDTO();
				obj.setPersonaId(rs.getInt(1));
				obj.setTipoDocIdentidad(rs.getString(2));
				obj.setNroDocuIdentidad(rs.getString(3));
				obj.setApellidosNombres(rs.getString(4));
				obj.setRazonSocial(rs.getString(5));
				lista.add(obj);
			}
			return lista;
		} catch (Exception ex) {
			// TODO : Controller Exception
			System.out.println("ERROR: " + ex);
		}
		return null;
	}

	public List<BuscarPersonaDTO> findDjTransferentes(int djVehicularId) {
		List<BuscarPersonaDTO> lista = new ArrayList<BuscarPersonaDTO>();
		try {
			StringBuilder SQL = new StringBuilder(
					"SELECT per.persona_id, di.descrpcion_corta, per.nro_doc_identidad, per.apellidos_nombres, per.razon_social  FROM "
							+ SATParameterFactory.getDBNameScheme()
							+ ".rv_transferencia_propiedad tp ");
			SQL.append("INNER JOIN " + SATParameterFactory.getDBNameScheme()
					+ ".gn_persona per ON per.persona_id = tp.persona_id ");
			SQL.append("INNER JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".mp_tipo_docu_identidad di ON di.tipo_doc_identidad_id = per.tipo_documento_id ");
			SQL.append("WHERE tp.djvehicular_id=" + djVehicularId );

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				BuscarPersonaDTO obj = new BuscarPersonaDTO();
				obj.setPersonaId(rs.getInt(1));
				obj.setTipoDocIdentidad(rs.getString(2));
				obj.setNroDocuIdentidad(rs.getString(3));
				obj.setApellidosNombres(rs.getString(4));
				obj.setRazonSocial(rs.getString(5));
				lista.add(obj);
			}
			return lista;
		} catch (Exception ex) {
			// TODO : Controller Exception
			System.out.println("ERROR: " + ex);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sat.sisat.vehicular.dao.Interfaz#findAnexos(int)
	 */
	public List<AnexosDeclaVehicDTO> findAnexos(int djvehicularId) {
		List<AnexosDeclaVehicDTO> lista = new ArrayList<AnexosDeclaVehicDTO>();
		try {
			StringBuilder SQL = new StringBuilder(
					"SELECT ds.doc_sustentatorio_id, ds.descripcion, sv.nro_documento, sv.referencia, sv.content_id FROM "
							+ SATParameterFactory.getDBNameScheme()
							+ ".rv_sustento_vehicular sv ");
			SQL.append("INNER JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_documento_sustentatorio ds ON ds.doc_sustentatorio_id = sv.doc_sustentatorio_id ");
			SQL.append("WHERE sv.djvehicular_id=" + djvehicularId);

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				int cont = 1;
				AnexosDeclaVehicDTO obj = new AnexosDeclaVehicDTO();
				obj.setDocSustentatorioId(rs.getInt(cont++));
				obj.setDescripcionDoc(rs.getString(cont++));
				obj.setGlosaDoc(rs.getString(cont++));
				obj.setNomDocAdjunto(rs.getString(cont++));
				obj.setContentId(rs.getInt(cont++));
				lista.add(obj);
			}
			return lista;
		} catch (Exception ex) {
			// TODO : Controller Exception
			System.out.println("ERROR: " + ex);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sat.sisat.vehicular.dao.Interfaz#existeVehiculoPlaca(java.lang.String
	 * )
	 */
	public Integer existeVehiculoPlaca(String placa) {
		try {
			String SQL = "SELECT ve.vehiculo_id FROM "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_vehiculo ve WHERE ve.placa='" + placa
					+ "' ORDER BY ve.vehiculo_id DESC";
			PreparedStatement pst = connect().prepareStatement(SQL);
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				int vehicId = rs.getInt("vehiculo_id");
				return vehicId;
			}
		} catch (Exception ex) {
			// TODO : Controller Exception
			System.out.println("ERROR: " + ex);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sat.sisat.vehicular.dao.Interfaz#existeVehiculoMotor(java.lang.String
	 * )
	 */
	public Integer existeVehiculoMotor(String nroMotor) {
		try {
			String SQL = "SELECT ve.vehiculo_id FROM "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_vehiculo ve WHERE ve.num_motor='" + nroMotor
					+ "' ORDER BY ve.vehiculo_id DESC";
			PreparedStatement pst = connect().prepareStatement(SQL);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				return rs.getInt("vehiculo_id");
			}
		} catch (Exception ex) {
			// TODO : Controller Exception
			System.out.println("ERROR: " + ex);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sat.sisat.vehicular.dao.Interfaz#findDjVehicular(int, int)
	 */
	public List<BuscarVehicularDTO> findDjVehicular(int personaId,
			int vehiculoId) throws Exception {
		List<BuscarVehicularDTO> lista = new ArrayList<BuscarVehicularDTO>();
		try {
			StringBuilder SQL = new StringBuilder(
					"SELECT TOP 1 ve.placa, ve.num_motor AS nromotor, ca.descripcion AS categoria, ma.descripcion AS marca, mo.descripcion AS modelo, ");
			SQL.append("djv.djvehicular_id AS djId, md.descripcion AS motivodecla, djv.fecha_declaracion AS fecha, djv.estado, ve.fecha_ins_registros AS fechaInsReg, ");
			SQL.append("ve.anno_fabricacion AS anioFabric, ve.vehiculo_id AS vehicId, md.motivo_declaracion_id AS motivoDeclaId, ");
			// SQL.append("contribuyente = CASE WHEN per.apellidos_nombres IS NULL THEN per.razon_social ELSE per.apellidos_nombres END, ");
			SQL.append("per.apellidos_nombres AS contribuyente, ");
			SQL.append("djvehicular_nro AS djNro, djv.porc_propiedad, djv.persona_id FROM "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_djvehicular djv ");
			SQL.append("INNER JOIN " + SATParameterFactory.getDBNameScheme()
					+ ".rv_vehiculo ve ON ve.vehiculo_id = djv.vehiculo_id ");
			SQL.append("INNER JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_modelo_vehiculo mo ON mo.modelo_vehiculo_id = ve.modelo_vehiculo_id AND mo.categoria_vehiculo_id=djv.categoria_vehiculo_id AND mo.marca_vehiculo_id = djv.marca_vehiculo_id ");
			SQL.append("INNER JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_marca ma ON ma.marca_vehiculo_id = ve.marca_vehiculo_id ");
			SQL.append("INNER JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_categoria_vehiculo ca ON ca.categoria_vehiculo_id = ve.categoria_vehiculo_id ");
			SQL.append("INNER JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_motivo_declaracion md ON md.motivo_declaracion_id = djv.rv_motivo_declaracion_id ");
			SQL.append("INNER JOIN " + SATParameterFactory.getDBNameScheme()
					+ ".mp_persona per ON per.persona_id = djv.persona_id ");
			SQL.append("WHERE per.persona_id=" + personaId
					+ " AND ve.vehiculo_id=" + vehiculoId);
			SQL.append(" AND djv.estado<>'" + Constante.ESTADO_INACTIVO + "' and djv.estado <> '9'");   	//excluye de la busqueda la DJ inactivas y las DJ eliminadas logicamente, todas las demas  
			SQL.append(" ORDER BY djv.djvehicular_id DESC");											// como pendientes de inscripcion y activas se consideran

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				BuscarVehicularDTO obj = new BuscarVehicularDTO();
				obj.setDjVehicularId(rs.getInt("djId"));
				obj.setMotivoDecla(rs.getString("motivodecla"));
				obj.setPlaca(rs.getString("placa"));
				obj.setNroMotor(rs.getString("nromotor"));
				obj.setCategoria(rs.getString("categoria"));
				obj.setMarca(rs.getString("marca"));
				obj.setModelo(rs.getString("modelo"));
				obj.setEstado(rs.getString("estado"));
				obj.setFecha(rs.getTimestamp("fecha"));
				obj.setFechaInsReg(rs.getTimestamp("fechaInsReg"));
				obj.setAnioFabric(rs.getString("anioFabric"));
				obj.setVehiculoId(rs.getInt("vehicId"));
				obj.setMotivoDeclaId(rs.getInt("motivoDeclaId"));
				obj.setCodPersona(rs.getInt("persona_id"));
				obj.setContribuyente(rs.getString("contribuyente"));
				obj.setDjvehicularNro(rs.getString("djNro"));
				obj.setPorcentajeDecla(rs.getBigDecimal("porc_propiedad"));
				lista.add(obj);
			}
		} catch (Exception e) {
			// TODO : Controller exception
			throw (e);
		}
		return lista;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sat.sisat.vehicular.dao.Interfaz#findDjVehicularVehicId(int)
	 */
	public List<BuscarVehicularDTO> findDjVehicularVehicId(int vehiculoId)
			throws Exception {
		List<BuscarVehicularDTO> lista = new ArrayList<BuscarVehicularDTO>();
		try {
			StringBuilder SQL = new StringBuilder(
					"SELECT TOP 1 ve.placa, ve.num_motor AS nromotor, ca.descripcion AS categoria, ma.descripcion AS marca, ");
			SQL.append("modelo =(SELECT mo.descripcion FROM "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_modelo_vehiculo mo WHERE mo.modelo_vehiculo_id = djv.modelo_vehiculo_id AND mo.categoria_vehiculo_id = djv.categoria_vehiculo_id AND mo.marca_vehiculo_id = djv.marca_vehiculo_id), ");
			SQL.append("djv.djvehicular_id AS djId, md.descripcion AS motivodecla, djv.fecha_declaracion AS fecha, djv.estado, ve.fecha_ins_registros AS fechaInsReg, ");
			SQL.append("ve.anno_fabricacion AS anioFabric, ve.vehiculo_id AS vehicId, md.motivo_declaracion_id AS motivoDeclaId, ");
			SQL.append("contribuyente = CASE WHEN per.apellidos_nombres IS NULL THEN per.razon_social ELSE per.apellidos_nombres END, ");
			SQL.append("djvehicular_nro AS djNro, djv.porc_propiedad FROM "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_djvehicular djv ");
			SQL.append("INNER JOIN " + SATParameterFactory.getDBNameScheme()
					+ ".rv_vehiculo ve ON ve.vehiculo_id = djv.vehiculo_id ");
			SQL.append("INNER JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_marca ma ON ma.marca_vehiculo_id = djv.marca_vehiculo_id ");
			SQL.append("INNER JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_categoria_vehiculo ca ON ca.categoria_vehiculo_id = djv.categoria_vehiculo_id ");
			SQL.append("INNER JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_motivo_declaracion md ON md.motivo_declaracion_id = djv.rv_motivo_declaracion_id ");
			SQL.append("INNER JOIN " + SATParameterFactory.getDBNameScheme()
					+ ".mp_persona per ON per.persona_id = djv.persona_id ");
			SQL.append("WHERE ve.vehiculo_id=" + vehiculoId);
			SQL.append(" AND (djv.estado = '" + Constante.ESTADO_ACTIVO
					+ "' OR djv.estado='" + Constante.ESTADO_PENDIENTE + "')");
			SQL.append(" ORDER BY djv.djvehicular_nro DESC");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				BuscarVehicularDTO obj = new BuscarVehicularDTO();
				obj.setDjVehicularId(rs.getInt("djId"));
				obj.setMotivoDecla(rs.getString("motivodecla"));
				obj.setPlaca(rs.getString("placa"));
				obj.setNroMotor(rs.getString("nromotor"));
				obj.setCategoria(rs.getString("categoria"));
				obj.setMarca(rs.getString("marca"));
				obj.setModelo(rs.getString("modelo"));
				obj.setEstado(rs.getString("estado"));
				obj.setFecha(rs.getTimestamp("fecha"));
				obj.setFechaInsReg(rs.getTimestamp("fechaInsReg"));
				obj.setAnioFabric(rs.getString("anioFabric"));
				obj.setVehiculoId(rs.getInt("vehicId"));
				obj.setMotivoDeclaId(rs.getInt("motivoDeclaId"));
				obj.setContribuyente(rs.getString("contribuyente"));
				obj.setDjvehicularNro(rs.getString("djNro"));
				obj.setPorcentajeDecla(rs.getBigDecimal("porc_propiedad"));
				lista.add(obj);
			}
		} catch (Exception e) {
			// TODO : Controller exception
			throw (e);
		}
		return lista;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sat.sisat.vehicular.dao.Interfaz#cambiarEstadoDjv(int,
	 * java.lang.String)
	 */
	public boolean cambiarEstadoDjv(int djId, String estado) {
		int res = 0;
		try {
			String SQL = "UPDATE " + SATParameterFactory.getDBNameScheme()
					+ ".rv_djvehicular SET estado='" + estado
					+ "' WHERE djvehicular_id=" + djId;
			PreparedStatement pst = connect().prepareStatement(SQL);
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

	public boolean cambiarFlagDjAnio(int djId, String flag) {
		int res = 0;
		try {
			String SQL = "UPDATE " + SATParameterFactory.getDBNameScheme()
					+ ".rv_djvehicular SET flag_dj_anno='" + flag
					+ "' WHERE djvehicular_id=" + djId;
			PreparedStatement pst = connect().prepareStatement(SQL);
			res = pst.executeUpdate();
		} catch (Exception ex) {
			System.out.println("cambiarFlagDjAnio: " + ex);
			// TODO : Controller exception
		}
		if (res == 1) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sat.sisat.vehicular.dao.Interfaz#findHistoricoVehiculoDj(int,
	 * int)
	 */
	public List<HistoricoVehiculodjDTO> findHistoricoVehiculoDj(int personaId,
			String placa) throws SisatException {
		List<HistoricoVehiculodjDTO> lista = new ArrayList<HistoricoVehiculodjDTO>();
		try {
			StringBuilder SQL = new StringBuilder(
					"SELECT djv.djvehicular_id AS djId, djvehicular_nro AS djNro, md.motivo_declaracion_id AS motivoDeclaId, ");
			// quitando anho declaracion por anho afectacion
			//SQL.append("md.descripcion AS motivodecla, djv.anno_declaracion, djv.anno_ini_afectacion, djv.anno_fin_afectacion, ");
			SQL.append("md.descripcion AS motivodecla, djv.anno_afectacion, djv.anno_ini_afectacion, djv.anno_fin_afectacion, ");
			SQL.append("djv.anno_afectacion, djv.fecha_declaracion, nt.nombre_notaria AS notaria, djv.estado, djv.flag_dj_anno, o.nombre_usuario, ");
			SQL.append("determinado=(SELECT CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 end  FROM "
					+ SATParameterFactory.getDBNameScheme()
					+ ".dt_determinacion WHERE estado='1' and concepto_id="
					+ Constante.CONCEPTO_VEHICULAR
					+ " AND djreferencia_id=djv.djvehicular_id), "); 
			SQL.append(" djv.fecha_adquisicion, djv.fecha_descargo,");
			SQL.append("anno_ini_afec_contrib=CASE WHEN MONTH(fecha_ins_registros)=1 AND DAY(fecha_ins_registros)=1 THEN YEAR(fecha_ins_registros) ELSE YEAR(fecha_ins_registros)+1 END, ");
			SQL.append(" anho_descargo=YEAR(djv.fecha_descargo), ");
			SQL.append(" djv.fecha_registro, djv.persona_id, djv.vehiculo_id, djv.glosa,djv.fiscalizado,djv.fisca_aceptada,djv.fisca_cerrada ");
			SQL.append("FROM " + SATParameterFactory.getDBNameScheme()
					+ ".rv_djvehicular djv ");
			SQL.append("INNER JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_motivo_declaracion md ON md.motivo_declaracion_id = djv.rv_motivo_declaracion_id ");
			SQL.append("INNER JOIN " + SATParameterFactory.getDBNameScheme()
					+ ".gn_notaria nt ON nt.notaria_id = djv.notaria_id ");
			SQL.append("left join ").append(Constante.schemadb).append(".sg_usuario o on(o.usuario_id=djv.usuario_id) ");
			SQL.append("WHERE djv.persona_id=" + personaId
					+ " AND djv.vehiculo_id IN(SELECT vehiculo_id FROM "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_vehiculo WHERE placa='" + placa + "')");
			// Mostrando los pendiente de actualizacion
			SQL.append(" AND djv.estado not in ('").append(Constante.ESTADO_ELIMINADO).append("','").append(Constante.ESTADO_PENDIENTE_ACTUALIZACION).append("')");
//			SQL.append(" AND djv.estado not in ('").append(Constante.ESTADO_ELIMINADO).append("')");
			SQL.append(" ORDER BY djv.anno_afectacion desc, djv.djvehicular_id desc");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				HistoricoVehiculodjDTO obj = new HistoricoVehiculodjDTO();
				obj.setDjVehicularId(rs.getInt("djId"));
				obj.setDjvehicularNro(rs.getString("djNro"));
				obj.setMotivoDeclaId(rs.getInt("motivoDeclaId"));
				obj.setMotivoDecla(rs.getString("motivodecla"));
				
				// quitando anho declaracion por anho afectacion
				//obj.setAnioDecla(rs.getString("anno_declaracion"));
				obj.setAnioDecla(rs.getString("anno_afectacion"));
				obj.setAnioIniAfec(rs.getString("anno_ini_afectacion"));
				obj.setAnioFinAfec(rs.getString("anno_fin_afectacion"));
				obj.setAnioAfec(rs.getString("anno_afectacion"));
				obj.setFechaDecla(rs.getTimestamp("fecha_declaracion"));
				obj.setNotaria(rs.getString("notaria"));
				obj.setEstado(rs.getString("estado"));
				obj.setFlagDjAnio(rs.getString("flag_dj_anno"));
				String deter = rs.getString("determinado");
				obj.setDeterminado(deter.equals("1") ? true : false);
				obj.setAnioIniAfecContrib(rs.getString("anno_ini_afec_contrib"));
				obj.setUsuario(rs.getString("nombre_usuario"));
				obj.setFechaAdquisicion(rs.getTimestamp("fecha_adquisicion"));
				obj.setFechaDescargo(rs.getTimestamp("fecha_descargo"));
				obj.setAnhoDescargo(rs.getString("anho_descargo"));
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setVehiculoId(rs.getInt("vehiculo_id"));
				obj.setGlosa(rs.getString("glosa"));
				
				if(obj.getFechaRegistro() != null){
					obj.setFechaRegistro(new Date(rs.getTimestamp("fecha_registro").getTime()));
				}else{
					obj.setFechaRegistro(null);
				}
				
				obj.setFiscalizado(rs.getString("fiscalizado"));
				obj.setFiscaAceptada(rs.getString("fisca_aceptada"));
				obj.setFiscaCerrada(rs.getString("fisca_cerrada"));
				lista.add(obj);
			}
		} catch (Exception ex) {			
			throw new SisatException(ex.getMessage());
		}
		return lista;
	}

	public int duplicarDjvActualizar(int oldDjvId, int userId) throws Exception {
		int salida = 0;
		try {
			CallableStatement cs = connect().prepareCall(
					"{call sp_duplicadjvehicular(?,?)}");
			cs.setInt(1, oldDjvId);
			cs.setInt(2, userId);
			ResultSet rs = cs.executeQuery();
			while (rs.next()) {
				salida = rs.getInt(1);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return salida;
	}

	public boolean existeDjvEnAnio(int djvId, int contribId, int anioCrear) {
		try {
			StringBuilder SQL = new StringBuilder(
					"SELECT COUNT(*) AS nroRegistros FROM "
							+ SATParameterFactory.getDBNameScheme()
							+ ".rv_djvehicular ");
			SQL.append("WHERE vehiculo_id=(SELECT vehiculo_id FROM "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_djvehicular WHERE djvehicular_id=" + djvId + ") ");
			// agregando el estado 1 para dj activas
			SQL.append("AND persona_id=" + contribId
					+ " AND estado = 1 AND flag_dj_anno='1' AND anno_afectacion=" + anioCrear);

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				int nroReg = rs.getInt("nroRegistros");
				if (nroReg > 0) {
					return true;
				} else {
					return false;
				}
			}
		} catch (Exception ex) {
			// TODO : Controller Exception
			System.out.println("existeDjvEnAnio: " + ex);
		}
		return true;
	}

	public boolean estaEnRangoAfecVehic(int djvId, int anioCrear) {
		try {
			StringBuilder SQL = new StringBuilder("SELECT ");
			SQL.append("CASE WHEN (anno_ini_afectacion<=" + anioCrear
					+ " AND anno_fin_afectacion>=" + anioCrear
					+ ") THEN 1 ELSE 0 END AS resultado ");
			SQL.append("FROM " + SATParameterFactory.getDBNameScheme()
					+ ".rv_djvehicular ");
			SQL.append("WHERE djvehicular_id=" + djvId);

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				int res = rs.getInt("resultado");
				if (res == 1) {
					return true;
				} else {
					return false;
				}
			}
		} catch (Exception ex) {
			// TODO : Controller Exception
			System.out.println("validarAnioCrearAfecVehic: " + ex);
		}
		return false;
	}

	public boolean estaEnRangoAfecContrib(int djvId, int anioCrear) {
		try {

			// cambiando logica, el periodo de pagos de impuesto por vehicular
			// solo se realiza una vez independientemente del cambio de dueño,
			// por lo tanto
			// descartando como parametro la fecha de adquision
			// StringBuilder SQL = new
			// StringBuilder("SELECT CASE WHEN MONTH(fecha_adquisicion)=1 AND DAY(fecha_adquisicion)=1 THEN YEAR(fecha_adquisicion) ");
			// SQL.append("ELSE YEAR(fecha_adquisicion)+1 END AS anno_ini_afec_contrib, anno_fin_afectacion FROM "+
			// SATParameterFactory.getDBNameScheme() +
			// ".rv_djvehicular WHERE djvehicular_id="+djvId);

			StringBuilder SQL = new StringBuilder(
					"SELECT CASE WHEN MONTH(fecha_ins_registros)=1 AND DAY(fecha_ins_registros)=1 THEN YEAR(fecha_ins_registros) ");
			SQL.append("ELSE YEAR(fecha_ins_registros)+1 END AS anno_ini_afec_contrib, anno_fin_afectacion FROM "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_djvehicular WHERE djvehicular_id=" + djvId);

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				int ini = rs.getInt("anno_ini_afec_contrib");
				int fin = rs.getInt("anno_fin_afectacion");
				if (anioCrear >= ini && anioCrear <= fin) {
					return true;
				} else {
					return false;
				}
			}
		} catch (Exception ex) {
			// TODO : Controller Exception
			System.out.println("djsAnio: " + ex);
		}
		return false;
	}

	public boolean copiarDjvAOtroAnio(int djvId, int anioCrear, int usuarioId,
			String terminal) {
		try {
			CallableStatement cs = connect().prepareCall(
					"{call stp_copiarDjvehicularAOtroAnio(?,?,?,?)}");
			cs.setInt(1, djvId);
			cs.setInt(2, anioCrear);
			cs.setInt(3, usuarioId);
			cs.setString(4, terminal);
			cs.executeUpdate();
			return true;
		} catch (Exception ex) {
			// TODO : Handle exception
			System.out.println("copiarDjvAOtroAnio: " + ex);
		}
		return false;
	}

	public boolean isVehiculoAssociToDj(int vehiculoId) {
		try {
			StringBuilder SQL = new StringBuilder(
					"SELECT COUNT(*) AS numRecords FROM rv_djvehicular djv WHERE djv.vehiculo_id="
							+ vehiculoId);

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				int nRec = rs.getInt("numRecords");
				if (nRec > 0) {
					return true;
				}
			}
		} catch (Exception ex) {
			// TODO : Handle exception
			System.out.println("isVehiculoAsociatedToDj: " + ex);
		}
		return false;
	}

	/**
	 * ITANTAMANGO
	 * 
	 * @param codigoCont
	 * @param placa
	 * @param apellidosNombres
	 * @param razon
	 * @param tipoDocIdentidad
	 * @param numeroDocIdentidad
	 * @return
	 * @throws Exception
	 */
	public List<BuscarVehicularDTO> findDjVehicular(String codigoCont,
			String placa, String apellidosNombres, String razon)
			throws Exception {
		List<BuscarVehicularDTO> lista = new ArrayList<BuscarVehicularDTO>();
		try {
			StringBuilder SQL = new StringBuilder(
					"SELECT per.persona_id,per.apellidos_nombres,per.razon_social, ve.placa, ve.num_motor AS nromotor, ca.descripcion AS categoria, ma.descripcion AS marca, mo.descripcion AS modelo, ");
			SQL.append("djv.djvehicular_id AS djId, md.descripcion AS motivodecla, djv.fecha_declaracion AS fecha, djv.estado, ve.fecha_ins_registros AS fechaInsReg, ");
			SQL.append("ve.anno_fabricacion AS anioFabric, ve.vehiculo_id AS vehicId, md.motivo_declaracion_id AS motivoDeclaId, djvehicular_nro AS djNro, djv.fecha_adquisicion FROM "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_djvehicular djv ");
			SQL.append("INNER JOIN " + SATParameterFactory.getDBNameScheme()
					+ ".rv_vehiculo ve ON ve.vehiculo_id = djv.vehiculo_id ");
			SQL.append("INNER JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_modelo_vehiculo mo ON mo.modelo_vehiculo_id = ve.modelo_vehiculo_id AND mo.categoria_vehiculo_id=djv.categoria_vehiculo_id AND mo.marca_vehiculo_id = djv.marca_vehiculo_id ");
			SQL.append("INNER JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_marca ma ON ma.marca_vehiculo_id = ve.marca_vehiculo_id ");
			SQL.append("INNER JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_categoria_vehiculo ca ON ca.categoria_vehiculo_id = ve.categoria_vehiculo_id ");
			SQL.append("INNER JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_motivo_declaracion md ON md.motivo_declaracion_id = djv.rv_motivo_declaracion_id ");
			SQL.append("INNER JOIN " + SATParameterFactory.getDBNameScheme()
					+ ".mp_persona per on per.persona_id=djv.persona_id ");
			SQL.append("WHERE (djv.estado='1' OR djv.estado='2' OR djv.estado='3') ");
			if (codigoCont != null && codigoCont.trim().length() > 0) {
				SQL.append("AND djv.persona_id=? ");
			}
			if (apellidosNombres != null
					&& apellidosNombres.trim().length() > 0) {
				SQL.append("AND per.apellidos_nombres like '%"
						+ apellidosNombres + "%'");
			}
			if (razon != null && razon.trim().length() > 0) {
				SQL.append("AND per.razon_social like '%" + razon + "%'");
			}

			if (placa != null && placa.trim().length() > 0) {
				SQL.append(" AND ve.placa=?");
			}
			SQL.append(" ORDER BY ve.vehiculo_id");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			int cont = 1;
			if (codigoCont != null && codigoCont.trim().length() > 0) {
				pst.setInt(cont++, Integer.parseInt(codigoCont));
			}
			if (placa != null && placa.trim().length() > 0) {
				pst.setString(cont++, placa);
			}

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				BuscarVehicularDTO obj = new BuscarVehicularDTO();
				obj.setCodPersona(rs.getInt("persona_id"));
				if (rs.getString("apellidos_nombres") != null
						|| rs.getString("apellidos_nombres").length() > 0) {
					obj.setNomPropietario(rs.getString("apellidos_nombres"));
				} else if (rs.getString("razon_social") != null
						|| rs.getString("razon_social").length() > 0) {
					obj.setNomPropietario(rs.getString("razon_social"));
				}
				obj.setDjVehicularId(rs.getInt("djId"));
				obj.setMotivoDecla(rs.getString("motivodecla"));
				obj.setPlaca(rs.getString("placa"));
				obj.setNroMotor(rs.getString("nromotor"));
				obj.setCategoria(rs.getString("categoria"));
				obj.setMarca(rs.getString("marca"));
				obj.setModelo(rs.getString("modelo"));
				obj.setEstado(rs.getString("estado"));
				obj.setFecha(rs.getTimestamp("fecha"));
				obj.setFechaInsReg(rs.getTimestamp("fechaInsReg"));
				obj.setAnioFabric(rs.getString("anioFabric"));
				obj.setVehiculoId(rs.getInt("vehicId"));
				obj.setMotivoDeclaId(rs.getInt("motivoDeclaId"));
				obj.setDjvehicularNro(rs.getString("djNro"));
				obj.setFechaAdquisicion(rs.getTimestamp("fecha_adquisicion"));
				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}

	/**
	 * Autor:ITANTAMANGO
	 * 
	 * @param periodoAfecta
	 * @return
	 * @throws Exception
	 */
	public List<RvModeloVehiculo> getAllRvModeloVehiculoByPeriodoAfecta(
			int periodoAfecta, int marcaVehiculoId, int categoriaVehiculoId)
			throws Exception {

		List<RvModeloVehiculo> lista = new ArrayList<RvModeloVehiculo>();

		try {

			StringBuffer SQL = new StringBuffer();

			SQL.append("SELECT mo.marca_vehiculo_id, mo.categoria_vehiculo_id, mo.modelo_vehiculo_id, mo.descripcion from "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_modelo_vehiculo mo ");
			SQL.append("inner join dt_valor_referencial vr on vr.modelo_vehiculo_id=mo.modelo_vehiculo_id ");
			SQL.append("where mo.estado=1 and mo.categoria_vehiculo_id=" + categoriaVehiculoId);
			SQL.append(" and mo.marca_vehiculo_id=" + marcaVehiculoId);
			SQL.append(" and vr.periodo_afectacion=" + periodoAfecta);
			SQL.append(" group by mo.marca_vehiculo_id, mo.categoria_vehiculo_id, mo.modelo_vehiculo_id, mo.descripcion ");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
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

	/**
	 * Autor: ITANTAMANGO
	 * 
	 * @param categoria
	 * @param periodoAfecta
	 * @return
	 * @throws Exception
	 */
	public List<RvMarca> findRvMarcaByPeriodoAfecta(int categoria,
			int periodoAfecta) throws Exception {
		List<RvMarca> lista = new ArrayList<RvMarca>();
		try {
			StringBuffer SQL = new StringBuffer();
			SQL.append("select ma.marca_vehiculo_id,ma.descripcion from "
					+ SATParameterFactory.getDBNameScheme() + ".rv_marca ma ");
			SQL.append("inner join "
					+ SATParameterFactory.getDBNameScheme()
					+ ".dt_valor_referencial vr on vr.marca_vehiculo_id=ma.marca_vehiculo_id");
			SQL.append(" and vr.periodo_afectacion=" + periodoAfecta);
			SQL.append(" and vr.categoria_vehiculo_id=" + categoria);
			SQL.append(" group by ma.marca_vehiculo_id,ma.descripcion");
			SQL.append(" ORDER BY ma.descripcion ASC");

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
	
	/**
	 * Metodo que obtiene la dj vehicular activa en base al codigo del contribuyente, codigo del vehiculo y anho de afectacion
	 * @param personaId
	 * @param vehiculoId
	 * @param anhoAfectacion
	 * @return
	 * @throws SisatException
	 */
	public RvDjvehicular findDjVehicularByPersonaIdVehiculoIdAnhoAfectacion(int personaId,
			int vehiculoId,
			int anhoAfectacion) throws SisatException {

		RvDjvehicular obj = null;

		try {
			String SQL = "SELECT * FROM "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_djvehicular WHERE  persona_id = ? and vehiculo_id = ? and anno_afectacion = ? and (estado = 1 or estado = 0) and flag_dj_anno = 1";
			PreparedStatement pst = connect().prepareStatement(SQL);
			pst.setInt(1, personaId);
			pst.setInt(2, vehiculoId);
			pst.setInt(3, anhoAfectacion);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				obj = new RvDjvehicular();
				obj.setDjvehicularId(rs.getInt("djvehicular_id"));
				obj.setTipoTraccionId(rs.getInt("tipo_traccion_id"));
				obj.setTipoTransmisionId(rs.getInt("tipo_transmision_id"));
				obj.setTipoMotorId(rs.getInt("tipo_motor_id"));
				obj.setTipoMonedaId(rs.getInt("tipo_moneda_id"));
				obj.setTipoPropiedadId(rs.getInt("tipo_propiedad_id"));
				obj.setMarcaVehiculoId(rs.getInt("marca_vehiculo_id"));
				obj.setCategoriaVehiculoId(rs.getInt("categoria_vehiculo_id"));
				obj.setModeloVehiculoId(rs.getInt("modelo_vehiculo_id"));
				obj.setNumMotor(rs.getString("num_motor"));
				obj.setAnnoFabricacion(rs.getInt("anno_fabricacion"));
				obj.setFechaInsRegistros(rs.getTimestamp("fecha_ins_registros"));
				obj.setNumCilindros(rs.getInt("num_cilindros"));
				obj.setPesoBruto(rs.getInt("peso_bruto"));
				obj.setTipoCarroceriaId(rs.getInt("tipo_carroceria_id"));
				obj.setClaseVehiculoId(rs.getInt("clase_vehiculo_id"));
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setTipoAdquisicionId(rs.getInt("tipo_adquisicion_id"));
				obj.setRvMotivoDeclaracionId(rs.getInt("rv_motivo_declaracion_id"));
				obj.setCondicionVehiculoId(rs.getInt("condicion_vehiculo_id"));
				obj.setNotariaId(rs.getInt("notaria_id"));
				obj.setVehiculoId(rs.getInt("vehiculo_id"));
				RvMotivoDescargo md = new RvMotivoDescargo();
				md.setMotivoDescargoId(rs.getInt("motivo_descargo_id"));
				obj.setRvMotivoDescargo(md);
				obj.setFechaDeclaracion(rs.getTimestamp("fecha_declaracion"));
				obj.setNumTarjetaPropiedad(rs.getString("num_tarjeta_propiedad"));
				obj.setFechaAdquisicion(rs.getTimestamp("fecha_adquisicion"));
				obj.setValAdqSoles(rs.getBigDecimal("val_adq_soles"));
				obj.setValAdqOtraMoneda(rs.getBigDecimal("val_adq_otra_moneda"));
				obj.setTipoCambioId(rs.getInt("tipo_cambio_id"));
				obj.setPorcPropiedad(rs.getBigDecimal("porc_propiedad"));
				obj.setAnnoDeclaracion(rs.getString("anno_declaracion"));
				obj.setUsuarioId(rs.getInt("usuario_id"));
				obj.setFechaActualizacion(rs.getTimestamp("fecha_actualizacion"));
				obj.setEstado(rs.getString("estado"));
				obj.setFechaRegistro(rs.getTimestamp("fecha_registro"));
				obj.setTerminal(rs.getString("terminal"));
				obj.setDjvehicularNro(rs.getString("djvehicular_nro"));
				obj.setFlagDjAnno(rs.getString("flag_dj_anno"));
				obj.setDjvehicularPrevioId(rs.getInt("djvehicular_previo_id"));
				obj.setFechaAdquisicion(rs.getTimestamp("fecha_adquisicion"));
				obj.setGlosa(rs.getString("glosa"));
				obj.setAnnoIniAfectacion(rs.getInt("anno_ini_afectacion"));
				obj.setAnnoFinAfectacion(rs.getInt("anno_fin_afectacion"));
				obj.setAnnoAfectacion(rs.getInt("anno_afectacion"));
			}

		} catch (Exception ex) {
			throw new SisatException(ex.getMessage());
		}

		return obj;
	}
	
	/**
	 * Metodo que obtiene la dj vehicular activa en base al codigo del contribuyente, codigo del vehiculo y anho de afectacion
	 * @param personaId
	 * @param vehiculoId
	 * @param anhoAfectacion
	 * @return
	 * @throws SisatException
	 */
	public RvDjvehicular findDjVehicularByPersonaIdVehiculoIdAnhoAdquision(int personaId,
			int vehiculoId,
			int anhoAfectacion) throws SisatException {

		RvDjvehicular obj = null;

		try {
			String SQL = "SELECT * FROM "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_djvehicular WHERE  persona_id = ? and vehiculo_id = ? and anno_afectacion = ? and (estado = 1 or estado = 0 or estado = 3) and flag_dj_anno = 1";
			PreparedStatement pst = connect().prepareStatement(SQL);
			pst.setInt(1, personaId);
			pst.setInt(2, vehiculoId);
			pst.setInt(3, anhoAfectacion);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				obj = new RvDjvehicular();
				obj.setDjvehicularId(rs.getInt("djvehicular_id"));
				obj.setTipoTraccionId(rs.getInt("tipo_traccion_id"));
				obj.setTipoTransmisionId(rs.getInt("tipo_transmision_id"));
				obj.setTipoMotorId(rs.getInt("tipo_motor_id"));
				obj.setTipoMonedaId(rs.getInt("tipo_moneda_id"));
				obj.setTipoPropiedadId(rs.getInt("tipo_propiedad_id"));
				obj.setMarcaVehiculoId(rs.getInt("marca_vehiculo_id"));
				obj.setCategoriaVehiculoId(rs.getInt("categoria_vehiculo_id"));
				obj.setModeloVehiculoId(rs.getInt("modelo_vehiculo_id"));
				obj.setNumMotor(rs.getString("num_motor"));
				obj.setAnnoFabricacion(rs.getInt("anno_fabricacion"));
				obj.setFechaInsRegistros(rs.getTimestamp("fecha_ins_registros"));
				obj.setNumCilindros(rs.getInt("num_cilindros"));
				obj.setPesoBruto(rs.getInt("peso_bruto"));
				obj.setTipoCarroceriaId(rs.getInt("tipo_carroceria_id"));
				obj.setClaseVehiculoId(rs.getInt("clase_vehiculo_id"));
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setTipoAdquisicionId(rs.getInt("tipo_adquisicion_id"));
				obj.setRvMotivoDeclaracionId(rs.getInt("rv_motivo_declaracion_id"));
				obj.setCondicionVehiculoId(rs.getInt("condicion_vehiculo_id"));
				obj.setNotariaId(rs.getInt("notaria_id"));
				obj.setVehiculoId(rs.getInt("vehiculo_id"));
				RvMotivoDescargo md = new RvMotivoDescargo();
				md.setMotivoDescargoId(rs.getInt("motivo_descargo_id"));
				obj.setRvMotivoDescargo(md);
				obj.setFechaDeclaracion(rs.getTimestamp("fecha_declaracion"));
				obj.setNumTarjetaPropiedad(rs.getString("num_tarjeta_propiedad"));
				obj.setFechaAdquisicion(rs.getTimestamp("fecha_adquisicion"));
				obj.setValAdqSoles(rs.getBigDecimal("val_adq_soles"));
				obj.setValAdqOtraMoneda(rs.getBigDecimal("val_adq_otra_moneda"));
				obj.setTipoCambioId(rs.getInt("tipo_cambio_id"));
				obj.setPorcPropiedad(rs.getBigDecimal("porc_propiedad"));
				obj.setAnnoDeclaracion(rs.getString("anno_declaracion"));
				obj.setUsuarioId(rs.getInt("usuario_id"));
				obj.setFechaActualizacion(rs.getTimestamp("fecha_actualizacion"));
				obj.setEstado(rs.getString("estado"));
				obj.setFechaRegistro(rs.getTimestamp("fecha_registro"));
				obj.setTerminal(rs.getString("terminal"));
				obj.setDjvehicularNro(rs.getString("djvehicular_nro"));
				obj.setFlagDjAnno(rs.getString("flag_dj_anno"));
				obj.setDjvehicularPrevioId(rs.getInt("djvehicular_previo_id"));
				obj.setFechaAdquisicion(rs.getTimestamp("fecha_adquisicion"));
				obj.setGlosa(rs.getString("glosa"));
				obj.setAnnoIniAfectacion(rs.getInt("anno_ini_afectacion"));
				obj.setAnnoFinAfectacion(rs.getInt("anno_fin_afectacion"));
				obj.setAnnoAfectacion(rs.getInt("anno_afectacion"));
			}

		} catch (Exception ex) {
			throw new SisatException(ex.getMessage());
		}

		return obj;
	}
	
	public void eliminarDJVehicular(HistoricoVehiculodjDTO djvehicular, Integer usuarioId, String terminal) throws SisatException{
		try {
			if(!djvehicular.isDeterminado()){
				//Eliminacion de la DDJD vehicular
				StringBuffer SQL = new StringBuffer();
				SQL.append(" UPDATE ").append(Constante.schemadb).append(".rv_djvehicular ");
				SQL.append(" SET estado = '").append(Constante.ESTADO_ELIMINADO).append("', ");
				SQL.append(" flag_dj_anno = ").append(Constante.FLAG_DJ_ANIO_INACTIVO).append(", ");
				SQL.append(" glosa = '").append(djvehicular.getGlosa()).append("', ");
				SQL.append(" usuario_id = ").append(usuarioId).append(", ");
				SQL.append(" terminal = '").append(terminal).append("' ");						
				SQL.append(" where djvehicular_id = ? and djvehicular_nro=? ");

				PreparedStatement pst=connect().prepareStatement(SQL.toString());
				pst.setInt(1,djvehicular.getDjVehicularId());
				pst.setString(2,djvehicular.getDjvehicularNro());			
				
				pst.executeUpdate();
				//Activacion de la anterior DDJJ. como estado activo y flag_dj_anno 1 (Si estado NO es pendiente)
				if(djvehicular.getEstado().compareToIgnoreCase("2") != 0){
					StringBuffer SQL2=new StringBuffer();
					SQL2.append(" select ISNULL(MAX(djvehicular_id),0) djvehicula_id_previo from  ").append(Constante.schemadb).append(".rv_djvehicular ");			
					SQL2.append(" where anno_afectacion=? and vehiculo_id=? and persona_id=? and djvehicular_id != ?  and estado = '0' and flag_dj_anno='0'");
					
					PreparedStatement pst2=connect().prepareStatement(SQL2.toString());
					pst2.setString(1,djvehicular.getAnioAfec());
					pst2.setInt(2,djvehicular.getVehiculoId());
					pst2.setInt(3,djvehicular.getPersonaId());
					pst2.setInt(4,djvehicular.getDjVehicularId());
					
					ResultSet rs=pst2.executeQuery();
					
					int djvehicula_id_previo = 0; //dj_id del previo				
					
					while(rs.next()){
						djvehicula_id_previo = rs.getInt("djvehicula_id_previo");					
					}
					
					if( djvehicula_id_previo != 0){
						StringBuffer SQL1 = new StringBuffer();
						SQL1.append(" UPDATE ").append(Constante.schemadb).append(".rv_djvehicular ");
						SQL1.append(" SET estado = '").append(Constante.ESTADO_ACTIVO).append("', ");
						SQL1.append(" flag_dj_anno = ").append(Constante.FLAG_DJ_ANIO_ACTIVO);					
						SQL1.append(" WHERE djvehicular_id=? ");

						PreparedStatement pst1=connect().prepareStatement(SQL1.toString());
						pst1.setInt(1,djvehicula_id_previo);
						pst1.executeUpdate();
					}
									
				}
				
			}else{
				//tiene una determinacion-deuda asociada y no puede eliminar DJ			
				throw new SisatException(" << No puede ser eliminada la DJ debido a que tiene una determinacion y una deuda asociada.  >>" );
			}
			
			
		} catch (Exception ex) {
			throw new SisatException(ex.getMessage());
		}
		
	}
	
	public List<HistoricoVehiculodjDTO> findHistoricoVehiculoDjTodo(int personaId,
			String placa) throws SisatException {
		List<HistoricoVehiculodjDTO> lista = new ArrayList<HistoricoVehiculodjDTO>();
		try {
			StringBuilder SQL = new StringBuilder(
					"SELECT djv.djvehicular_id AS djId, djvehicular_nro AS djNro, md.motivo_declaracion_id AS motivoDeclaId, ");
			// quitando anho declaracion por anho afectacion
			//SQL.append("md.descripcion AS motivodecla, djv.anno_declaracion, djv.anno_ini_afectacion, djv.anno_fin_afectacion, ");
			SQL.append("md.descripcion AS motivodecla, djv.anno_afectacion, djv.anno_ini_afectacion, djv.anno_fin_afectacion, ");
			SQL.append("djv.anno_afectacion, djv.fecha_declaracion, nt.nombre_notaria AS notaria, djv.estado, djv.flag_dj_anno, o.nombre_usuario, ");
			SQL.append("determinado=(SELECT CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 end  FROM "
					+ SATParameterFactory.getDBNameScheme()
					+ ".dt_determinacion WHERE  estado='1' and concepto_id="
					+ Constante.CONCEPTO_VEHICULAR
					+ " AND djreferencia_id=djv.djvehicular_id), "); 
			SQL.append(" djv.fecha_adquisicion, djv.fecha_descargo,");
			SQL.append("anno_ini_afec_contrib=CASE WHEN MONTH(fecha_ins_registros)=1 AND DAY(fecha_ins_registros)=1 THEN YEAR(fecha_ins_registros) ELSE YEAR(fecha_ins_registros)+1 END, ");
			SQL.append(" anho_descargo=YEAR(djv.fecha_descargo), ");
			SQL.append(" djv.fecha_registro, djv.persona_id, djv.vehiculo_id, djv.glosa ");
			SQL.append("FROM " + SATParameterFactory.getDBNameScheme()
					+ ".rv_djvehicular djv ");
			SQL.append("INNER JOIN "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_motivo_declaracion md ON md.motivo_declaracion_id = djv.rv_motivo_declaracion_id ");
			SQL.append("INNER JOIN " + SATParameterFactory.getDBNameScheme()
					+ ".gn_notaria nt ON nt.notaria_id = djv.notaria_id ");
			SQL.append("left join ").append(Constante.schemadb).append(".sg_usuario o on(o.usuario_id=djv.usuario_id) ");
			SQL.append("WHERE djv.persona_id=" + personaId
					+ " AND djv.vehiculo_id IN(SELECT vehiculo_id FROM "
					+ SATParameterFactory.getDBNameScheme()
					+ ".rv_vehiculo WHERE placa='" + placa + "')");	
			SQL.append(" AND djv.estado not in ('").append(Constante.ESTADO_PENDIENTE_ACTUALIZACION).append("')");			
			SQL.append(" ORDER BY djv.anno_afectacion desc, djv.djvehicular_id desc");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				HistoricoVehiculodjDTO obj = new HistoricoVehiculodjDTO();
				obj.setDjVehicularId(rs.getInt("djId"));
				obj.setDjvehicularNro(rs.getString("djNro"));
				obj.setMotivoDeclaId(rs.getInt("motivoDeclaId"));
				obj.setMotivoDecla(rs.getString("motivodecla"));
				
				// quitando anho declaracion por anho afectacion
				//obj.setAnioDecla(rs.getString("anno_declaracion"));
				obj.setAnioDecla(rs.getString("anno_afectacion"));
				obj.setAnioIniAfec(rs.getString("anno_ini_afectacion"));
				obj.setAnioFinAfec(rs.getString("anno_fin_afectacion"));
				obj.setAnioAfec(rs.getString("anno_afectacion"));
				obj.setFechaDecla(rs.getTimestamp("fecha_declaracion"));
				obj.setNotaria(rs.getString("notaria"));
				obj.setEstado(rs.getString("estado"));
				obj.setFlagDjAnio(rs.getString("flag_dj_anno"));
				String deter = rs.getString("determinado");
				obj.setDeterminado(deter.equals("1") ? true : false);
				obj.setAnioIniAfecContrib(rs.getString("anno_ini_afec_contrib"));
				obj.setUsuario(rs.getString("nombre_usuario"));
				obj.setFechaAdquisicion(rs.getTimestamp("fecha_adquisicion"));
				obj.setFechaDescargo(rs.getTimestamp("fecha_descargo"));
				obj.setAnhoDescargo(rs.getString("anho_descargo"));
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setVehiculoId(rs.getInt("vehiculo_id"));
				obj.setGlosa(rs.getString("glosa"));
				
				if(obj.getFechaRegistro() != null){
					obj.setFechaRegistro(new Date(rs.getTimestamp("fecha_registro").getTime()));
				}else{
					obj.setFechaRegistro(null);
				}
				
				lista.add(obj);
			}
		} catch (Exception ex) {			
			throw new SisatException(ex.getMessage());
		}
		return lista;
	}
	
	public List<DeudaValoresDTO> getValorVehicular(Integer personaId,String anio,Integer djId) throws Exception {
		List<DeudaValoresDTO> list =  new ArrayList<DeudaValoresDTO>();
		
			try{
				StringBuilder SQL = new StringBuilder("dbo.stp_getValorDeudaVehicular ?,?,?");
				
				PreparedStatement pst = connect().prepareStatement(SQL.toString());
				pst.setInt(1, personaId);
				pst.setString(2, anio);
				pst.setInt(3, djId);
				
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

//	public int guardarOperacionAuditoria (GnAuditoriaOperacion auditoria) throws Exception {
//		int salida = 0;
//
//		try {
//			CallableStatement cs = connect().prepareCall(  "{call dbo.spt_generaOperacionAuditoria(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
//				if(auditoria.getTablaId()!=null){
//					cs.setInt(1,auditoria.getTablaId());
//				}else{
//					cs.setInt(1,0);
//				}
//					cs.setString(2,auditoria.getTablaNombre());
//				if(auditoria.getPersonaId()!=null){
//					cs.setInt(3,auditoria.getPersonaId());
//				}else{
//					cs.setInt(3,0);
//				}
//							
//				cs.setString(4,auditoria.getContribuyente());
//				if(auditoria.getTipoDocumentoId()!=null){
//					cs.setInt(5,auditoria.getTipoDocumentoId());
//				}else{
//					cs.setInt(5,0);
//				}
//				cs.setString(6,auditoria.getNroDocIdentidad());
//				cs.setString(7,auditoria.getCodigoOperacion());
//				if(auditoria.getPredioId()!=null){
//					cs.setInt(8,auditoria.getPredioId());
//				}else{
//					cs.setInt(8,0);
//				}
//				cs.setString(9,auditoria.getPlaca());
//				if(auditoria.getMotivoDescargoId()!=null){
//					cs.setInt(10,auditoria.getMotivoDescargoId());
//				}else{
//					cs.setInt(10,0);
//				}
//				if(auditoria.getMotivoDeclaracionId()!=null){
//				cs.setInt(11,auditoria.getMotivoDeclaracionId());
//				}else{
//					cs.setInt(11,0);
//				}
//				cs.setString(12,auditoria.getEstado());
//				if(auditoria.getAnio()!=null){
//					cs.setInt(13,auditoria.getAnio());
//				}else{
//					cs.setInt(13,0);
//				}
//				
//				if(auditoria.getMonto()!=null){
//					cs.setDouble(14,auditoria.getMonto());
//				}else{
//					cs.setDouble(14,0);
//				}
//				cs.setInt(15,auditoria.getTipoOperacion());
//				cs.setInt(16,auditoria.getUsuarioId());
//				cs.setString(17,auditoria.getTerminalRegistro());
//
//				cs.execute();
//			
//		} catch (Exception ex) {
//			System.out.println("ERROR: " + ex);
//		}
//		return salida;
//	}
	
	public List<RvMotivoDeclaracion> getAllRvMotivoDeclaracionByDj(int djId,int anio)
			throws Exception {
		List<RvMotivoDeclaracion> lista = new ArrayList<RvMotivoDeclaracion>();
		try {
			StringBuilder SQL = new StringBuilder("stp_getMotivoDeclaracionByDj ?,?");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, djId);
			pst.setInt(2, anio);
			
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				RvMotivoDeclaracion obj = new RvMotivoDeclaracion();
				obj.setMotivoDeclaracionId(rs.getInt("motivo_declaracion_id"));
				obj.setDescripcion(rs.getString("motivo"));
				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}
	
	public DatosNecesariosDeclaracionDTO obtenerReqDj(int djVehicularId) {
		try {
			StringBuilder SQL = new StringBuilder("stp_rv_obtener_dj_lote_vehicular ?");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, djVehicularId);
			ResultSet rs = pst.executeQuery();

			DatosNecesariosDeclaracionDTO dcv = null;
			if (rs.next()) {
				dcv = new DatosNecesariosDeclaracionDTO();
				dcv.setLoteId(rs.getInt("lote_id"));
				dcv.setDjId(rs.getInt("djvehicular_id"));
				dcv.setReqId(rs.getInt("requerimiento_id"));
				dcv.setPersonaId(rs.getInt("persona_id"));
				dcv.setPlaca(rs.getString("placa"));
				dcv.setFechaAdquisicion(rs.getTimestamp("fecha_adquisicion"));
				dcv.setNroReq(rs.getString("nro_requerimiento"));
			}
			return dcv;
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
			// FIXME : Controller exception
		}
		return null;
	}
	
	public DtDeterminacion obtenerDeterminacionByDj(int djVehicularId,int personaId) {
		try {
			StringBuilder SQL = new StringBuilder("stp_rv_obtener_determinacion_vehicular ?,?");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, djVehicularId);
			pst.setInt(2, personaId);
			
			ResultSet rs = pst.executeQuery();

			DtDeterminacion dcv = null;
			if (rs.next()) {
				dcv = new DtDeterminacion();
				dcv.setDeterminacionId(rs.getInt("determinacion_id"));
				dcv.setFiscalizado(rs.getString("fiscalizado"));
				dcv.setFiscaAceptada(rs.getString("fisca_aceptada"));
				dcv.setFiscaCerrada(rs.getString("fisca_cerrada"));

			}
			return dcv;
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
			// FIXME : Controller exception
		}
		return null;
	}
	
	
	public List<CarteraVehiculosDTO> getCarteraVehiculos(int lote_id,int sector_id,int persona_id,String nro_req,int distrito_id, String placa)
			throws Exception {
		List<CarteraVehiculosDTO> lista = new ArrayList<CarteraVehiculosDTO>();
		try {
			StringBuilder SQL = new StringBuilder("usp_list_cartera_vehicular ?,?,?,?,?,?");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, lote_id);
			pst.setInt(2, sector_id);
			pst.setInt(3, persona_id);
			pst.setString(4, nro_req);
			pst.setInt(5, distrito_id);
			pst.setString(6, placa);
			
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				CarteraVehiculosDTO obj = new CarteraVehiculosDTO();
				
				obj.setVehicularOmisosId(rs.getInt("vehicular_omisos_id"));
				obj.setSectorId(rs.getInt("sector_id"));
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setRequerimientoId(rs.getInt("requerimiento_id"));
				obj.setTipoDocumentoId(rs.getInt("tipo_documento_id"));
				obj.setPlaca(rs.getString("placa"));
				obj.setNroRequerimiento(rs.getString("nro_requerimiento"));
				obj.setAnioInspeccion(rs.getString("anio_inspeccion"));
				obj.setTipoDocumentoId(rs.getInt("tipo_documento_id"));
				obj.setTipoDocumentoId(rs.getInt("tipo_documento_id"));
				obj.setDistritoId(rs.getInt("distrito_id"));
				obj.setDistritoDescripcion(rs.getString("descripcion"));
				obj.setPropietario(rs.getString("propietario"));
				obj.setDireccionCompleta(rs.getString("direccion_completa"));
				obj.setProgramaId(rs.getInt("programa_id"));
				obj.setNombrePrograma(rs.getString("nombre_programa"));
				obj.setFechaInscripcion(rs.getString("fecha_inscripcion"));
				obj.setFechaPropiedad(rs.getString("fecha_propiedad"));
				obj.setNroPartida(rs.getString("nro_partida"));
				obj.setSector(rs.getString("sector"));
				
				
				obj.setVehiculoId(rs.getInt("vehiculo_id"));				
				obj.setMarcaVehiculoId(rs.getInt("marca_vehiculo_id"));
				obj.setCategoriaVehiculoId(rs.getInt("categoria_vehiculo_id"));
				obj.setModeloVehiculoId(rs.getInt("modelo_vehiculo_id"));
				obj.setClaseVehiculoId(rs.getInt("clase_vehiculo_id"));
				obj.setCarroceriaId(rs.getInt("tipo_carroceria_id"));
				obj.setTipoMotorId(rs.getInt("tipo_motor_id"));
				obj.setNumeroCilindros(rs.getDouble("num_cilindros"));
				
				obj.setAnnoAfectacion(rs.getInt("anno_afectacion"));
				obj.setAnnoFabricacion(rs.getInt("anno_fabricacion"));
				
				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}
	
	public List<DistritoDTO> getDistritos() throws Exception {
		
		List<DistritoDTO> lista = new ArrayList<DistritoDTO>();
		
		StringBuffer sentenciaSQL=new StringBuffer();
		sentenciaSQL.append("SELECT * FROM ").append(Constante.schemadb).append(".gn_distrito where estado=1");

		PreparedStatement pst = connect().prepareStatement(sentenciaSQL.toString());
		ResultSet rs = pst.executeQuery();

		while (rs.next()) {
			DistritoDTO obj = new DistritoDTO();
			
			obj.setDpto_id(rs.getInt("dpto_id"));
			obj.setProvincia_id(rs.getInt("provincia_id"));
			obj.setDistrito_id(rs.getInt("distrito_id"));
			obj.setDescripcion(rs.getString("descripcion"));
			obj.setCodigo_postal(rs.getString("codigo_postal"));
			
			lista.add(obj);
		}

		return lista;
	}
	
	public int getNuevaCarteraVehiculos(int lote_id, List<CabCarteraVehicularDTO> lstTemporal, int usuario_id ,String terminal)throws Exception {
		
		Iterator<CabCarteraVehicularDTO> it = lstTemporal.iterator();
		
		while (it.hasNext()) {
			CabCarteraVehicularDTO p = it.next();			
			
			StringBuilder SQL = new StringBuilder("t_registrar_temporal_cartera_vehicular ?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?,?,?");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());	
			pst.setInt(1, p.getOrden());
		    pst.setString(2, p.getSunarp());		    
		    pst.setString(3, p.getPlaca());		    
		    pst.setString(4, p.getPartida());
		    pst.setString(5, p.getFecha_insc_vehiculo());
		    pst.setString(6, p.getFecha_adqui_xprop());
		    pst.setString(7, p.getFecha_fin_afectacion());
		    pst.setString(8, p.getPropietario());
		    pst.setString(9, p.getTipo_doc());
		    pst.setInt(10, p.getTipo_doc_identidad_id());
		    
		    pst.setString(11, p.getDocumento());
		    pst.setString(12, p.getDireccion());
		    pst.setString(13, p.getDistrito_propietario());
		    pst.setInt(14, p.getDpto_id());
		    pst.setInt(15, p.getProvincia_id());
		    pst.setInt(16, p.getDistrito_id());
		    pst.setInt(17, p.getCod_contribuyente_personas());
		    pst.setString(18, p.getTipo_persona());
		    pst.setInt(19, p.getTipo_persona_id());
		    pst.setInt(20, p.getSubtipo_persona_id());
		    
		    pst.setString(21, p.getRelacionado());
		    pst.setString(22, p.getDni_relacionado());
		    pst.setString(23, p.getTipo_relacion());
		    pst.setInt(24, p.getTipo_relacion_id());
		    pst.setString(25, p.getMarca());
		    pst.setString(26, p.getModelo());
		    pst.setString(27, p.getCarroceria());
		    pst.setString(28, p.getMotor());
		    pst.setString(29, p.getSerie());
		    pst.setInt(30, p.getAno_fab_veh());
		    
		    pst.setString(31, p.getCombustible());
		    pst.setString(32, p.getCilindrada());
		    pst.setString(33, p.getColor1());
		    pst.setString(34, p.getColor2());		    
		    pst.setString(35, p.getColor3());
		    pst.setString(36, p.getNo_tarj());
		    pst.setString(37, p.getCategoria());
		    pst.setString(38, p.getTipo_uso_vehiculo());
		    pst.setDouble(39, p.getMonto_dolares());
		    pst.setDouble(40, p.getMonto_soles());
		    
		    pst.setInt(41, p.getMarca_vehiculo_id());
		    pst.setInt(42, p.getCarroceria_vehiculo_id());		    
		    pst.setInt(43, p.getTipo_motor());
		    pst.setString(44, p.getCategoria());
		    pst.setInt(45, p.getCategoria_id());
		    pst.setString(46, p.getMode_vehiculo_cat());
		    pst.setInt(47, p.getMode_vehiculo_id());
		    pst.setInt(48, lote_id);
		    pst.setInt(49, usuario_id);
		    pst.setString(50, terminal);
			
			//pst.executeQuery();
		    pst.executeUpdate();
			
			
		}
		
		
		//GENERACION DE REQUERIMIENTOS
			StringBuilder SQLREQ = new StringBuilder("t_registrar_omisos_vehicular ?,?,?");
			PreparedStatement pstReq = connect().prepareStatement(SQLREQ.toString());
			pstReq.setInt(1, lote_id);
			pstReq.setInt(2, usuario_id);		    
			pstReq.setString(3,terminal);
			
			//pstReq.executeUpdate();
			ResultSet rs = pstReq.executeQuery();

			while (rs.next()) {
				
				StringBuilder SQLPERVEH = new StringBuilder("t_registrar_personas_vehicular ?,?,?,?,?,?,?,?");
				PreparedStatement pstPerVeh = connect().prepareStatement(SQLPERVEH.toString());
				
				pstPerVeh.setInt(1,lote_id);
				pstPerVeh.setInt(2, rs.getInt("vehicular_omisos_id"));
				pstPerVeh.setInt(3, rs.getInt("persona_id"));		    
				pstPerVeh.setString(4,rs.getString("nro_docu_identidad"));
				pstPerVeh.setString(5,rs.getString("nro_partida"));
				pstPerVeh.setString(6,rs.getString("placa"));
				pstPerVeh.setInt(7,usuario_id);
				pstPerVeh.setString(8,terminal);
				
				pstPerVeh.executeUpdate();
				
			}
		
		return lote_id;
	}
	
	public void actualizarVehiculoOmiso(int marcaVehiculoId,int categoriaVehiculoId,int modeloVehiculoId
							,int claseVehiculoId,int carroceriaId,int tipoMotorId, Double numeroCilindros, 
							int vehiculoId,int usuarioId, String terminal, Timestamp fechaUpd) {
		try {
			
			StringBuilder SQL = new StringBuilder("UPDATE " + SATParameterFactory.getDBNameScheme()+ ".rv_vehiculo SET ");
			if (marcaVehiculoId != 0) {
				SQL.append("marca_vehiculo_id=" + marcaVehiculoId);
			}
			if (categoriaVehiculoId != 0) {
				SQL.append(",categoria_vehiculo_id=" + categoriaVehiculoId);
			}
			if (modeloVehiculoId != 0) {
				SQL.append(",modelo_vehiculo_id=" + modeloVehiculoId);
			}
			if (tipoMotorId != 0) {
				SQL.append(",tipo_motor_id=" + tipoMotorId);
			}

			if (numeroCilindros != 0) {
				SQL.append(",num_cilindros=" + numeroCilindros);
			}
			
			if (carroceriaId != 0) {
				SQL.append(",tipo_carroceria_id="
						+ carroceriaId);
			}
			if (carroceriaId != 0) {
				SQL.append(",clase_vehiculo_id=" + claseVehiculoId);
			}
			
			SQL.append(",usuario_id=" + usuarioId);
			SQL.append(",fecha_actualizacion='"+ fechaUpd);
			SQL.append("',terminal='" + terminal);
			
			SQL.append("' WHERE vehiculo_id=" + vehiculoId);

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.executeUpdate();

		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
			// FIXME : Controller exception
		}
	}
	
	
	
	public List<CarteraVehiculosDTO> getCarteraVehiculosEditarMasivo(int lote_id,  String marca, String modelo) throws Exception {
		List<CarteraVehiculosDTO> lista = new ArrayList<CarteraVehiculosDTO>();
		try {
			StringBuilder SQL = new StringBuilder("usp_list_cartera_vehicular_editar_masiva ?,?,?");

			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, lote_id);
			pst.setString(2, marca);
			pst.setString(3, modelo);
			
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				CarteraVehiculosDTO obj = new CarteraVehiculosDTO();
				
				obj.setVehicularOmisosId(rs.getInt("vehicular_omisos_id"));
				obj.setSectorId(rs.getInt("sector_id"));
				obj.setPersonaId(rs.getInt("persona_id"));
				obj.setRequerimientoId(rs.getInt("requerimiento_id"));
				obj.setTipoDocumentoId(rs.getInt("tipo_documento_id"));
				obj.setPlaca(rs.getString("placa"));
				obj.setNroRequerimiento(rs.getString("nro_requerimiento"));
				obj.setAnioInspeccion(rs.getString("anio_inspeccion"));
				obj.setTipoDocumentoId(rs.getInt("tipo_documento_id"));
				obj.setTipoDocumentoId(rs.getInt("tipo_documento_id"));
				obj.setDistritoId(rs.getInt("distrito_id"));
				obj.setDistritoDescripcion(rs.getString("descripcion"));
				obj.setPropietario(rs.getString("propietario"));
				obj.setDireccionCompleta(rs.getString("direccion_completa"));
				obj.setProgramaId(rs.getInt("programa_id"));
				obj.setNombrePrograma(rs.getString("nombre_programa"));
				obj.setFechaInscripcion(rs.getString("fecha_inscripcion"));
				obj.setFechaPropiedad(rs.getString("fecha_propiedad"));
				obj.setNroPartida(rs.getString("nro_partida"));
				obj.setSector(rs.getString("sector"));
				obj.setVehiculoId(rs.getInt("vehiculo_id"));				
				obj.setMarcaVehiculoId(rs.getInt("marca_vehiculo_id"));
				obj.setCategoriaVehiculoId(rs.getInt("categoria_vehiculo_id"));
				obj.setModeloVehiculoId(rs.getInt("modelo_vehiculo_id"));
				obj.setClaseVehiculoId(rs.getInt("clase_vehiculo_id"));
				obj.setCarroceriaId(rs.getInt("tipo_carroceria_id"));
				obj.setTipoMotorId(rs.getInt("tipo_motor_id"));
				obj.setNumeroCilindros(rs.getDouble("num_cilindros"));
				obj.setAnnoAfectacion(rs.getInt("anno_afectacion"));
				obj.setAnnoFabricacion(rs.getInt("anno_fabricacion"));
				
				obj.setMarcaDescripcion(rs.getString("marca_descripcion"));
				obj.setCategoriaDescripcion(rs.getString("categoria_descripcion"));
				obj.setModeloDescripcion(rs.getString("modelo_descripcion"));
				obj.setClaseDescripcion(rs.getString("clase_descripcion"));
				obj.setTipoCarroceriaDescripcion(rs.getString("tipo_carroceria_descripcion"));
				
				obj.setMarcaDescripcionTemporal(rs.getString("marca_descripcion_temporal"));
				obj.setModeloDescripcionTemporal(rs.getString("modelo_descripcion_temporal"));
				
				obj.setSelected(false);
				
				
				
				lista.add(obj);
			}
		} catch (Exception e) {
			throw (e);
		}
		return lista;
	}
	
	
}
