package com.sat.sisat.administracion.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.sat.sisat.administracion.dto.CampoDTO;
import com.sat.sisat.administracion.dto.FiltroColunmaDTO;
import com.sat.sisat.administracion.dto.ModuloDTO;
import com.sat.sisat.administracion.dto.RowLstDataDTO;
import com.sat.sisat.administracion.dto.TablaDTO;
import com.sat.sisat.common.dao.GeneralDao;
import com.sat.sisat.common.security.UserSession;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.SATParameterFactory;
import com.sat.sisat.exception.SisatException;
import com.sat.sisatc.seguridad.dto.ConsultaReniecDTO;
import com.sat.sisatc.seguridad.dto.ConsultaSunarpDTO;

/**
 * @author Miguel Macias
 * @version 0.1
 * @since 31/07/2012 La clase AdministracionBusinessDao.java ha sido creada con
 *        el proposito de administrar los servicios para mostrar, editar y crear
 *        nuevos registros en las tablas maestras. Ademas de exponer listados
 *        sobre cabeceras y contenido de una tabla maestra.
 */
public class AdministracionBusinessDao extends GeneralDao {

	private static String MOSTRAR = "1";
	@SuppressWarnings("unused")
	private static String OCULTAR = "0";

	final int TIPO_CONTROL_CALENDAR = 2;
	final int TIPO_CONTROL_INPUTTEXT = 1;
	final int TIPO_CONTROL_CHECKBOX = 3;
	final int TIPO_CONTROL_COMBOBOX = 4;

	final int TIPO_DATO_INT = 1;
	final int TIPO_DATO_VARCHAR = 3;
	final int TIPO_DATO_CHAR = 4;
	final int TIPO_DATO_DATETIME = 5;
	final int TIPO_DATO_DATE = 6;
	final int TIPO_DATO_NUMERIC = 2;

	final int TIPO_CAMPO_PRIMARY = 1;
	final int TIPO_CAMPO_FOREIGN = 2;
	final int TIPO_CAMPO_NORMAL = 3;
	final int TIPO_CAMPO_PRIMARYFOREIGN = 4;
	
	
	public Boolean registrarConsultaSunarp(ConsultaSunarpDTO datos) throws Exception {
		try {

			String SQL = new String("{call dbo.sp_registrar_mp_consulta_sunarp (?,?,?,?,?,?)}");
			CallableStatement cs = connect().prepareCall(SQL);

			cs.setString(1, datos.getApePaterno());
			cs.setString(2, datos.getApeMaterno());
			cs.setString(3, datos.getNombres());
			cs.setString(4,datos.getRazonSocial());
			cs.setInt(5, datos.getUsuarioID());
			cs.setString(6, datos.getTerminal());

			cs.execute();

		} catch (Exception e) {
			System.out.println("ERROR: " + e.getLocalizedMessage());
			return Boolean.FALSE;
		}

		return Boolean.TRUE;

	}

	
	
	

	public Boolean registrarConsultaReniec(ConsultaReniecDTO datos) throws Exception {
		try {

			String SQL = new String("{call dbo.sp_registrar_mp_consulta_reniec (?,?,?,?)}");
			CallableStatement cs = connect().prepareCall(SQL);

			cs.setString(1, datos.getDniConsulta());
			cs.setString(2, datos.getDniConsultado());
			cs.setInt(3, datos.getUsuarioID());
			cs.setString(4, datos.getTerminal());

			cs.execute();

		} catch (Exception e) {
			System.out.println("ERROR: " + e.getLocalizedMessage());
			return Boolean.FALSE;
		}

		return Boolean.TRUE;

	}

	public List<ModuloDTO> getLstModulos() throws SisatException {
		List<ModuloDTO> list = new ArrayList<ModuloDTO>();

		String sql = (new StringBuilder()).append("SELECT ").append("modulo_id, descripcion ").append("FROM ")
				.append(SATParameterFactory.getDbNombre().concat("."))
				.append(SATParameterFactory.getDbScheme().concat(".mt_modulo order by descripcion ")).toString();
		PreparedStatement pst;

		try {
			pst = connect().prepareStatement(sql);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				ModuloDTO obj = new ModuloDTO();
				obj.setModuloId(rs.getInt(1));
				obj.setDescripcion(rs.getString(2));
				list.add(obj);
			}

		} catch (SQLException e) {

			throw new SisatException(e.getMessage(), e.getCause());
		}

		return list;
	}

	/**
	 * Método que obtiene la lista de tablas disponibles para poder realizar
	 * operaciones
	 * 
	 * @return
	 * @throws SisatException
	 */
	public List<TablaDTO> getLstTabla(int moduloId) throws SisatException {

		List<TablaDTO> list = new ArrayList<TablaDTO>();

		StringBuilder sbGetListTablas = (new StringBuilder()).append("SELECT ")
				.append("modulo_id, tabla_id, tipo_tabla_id, descripcion, descripcion_corta ").append("FROM ")
				.append(SATParameterFactory.getDBNameScheme().concat(".mt_tabla mttabla"));

		StringBuilder sbGetListTablas2 = (new StringBuilder()).append("SELECT ")
				.append("modulo_id, tabla_id, tipo_tabla_id, descripcion, descripcion_corta ").append("FROM ")
				.append(SATParameterFactory.getDBNameScheme()).append(".mt_tabla mttabla");

		if (moduloId != 0) {
			sbGetListTablas.append(" WHERE mttabla.modulo_id = ").append(moduloId).append(" and estado = 1");
		}

		sbGetListTablas.append("order by descripcion_corta").toString();
		PreparedStatement pst;

		try {
			pst = connect().prepareStatement(sbGetListTablas.toString());

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				TablaDTO obj = new TablaDTO();
				obj.setCodModulo(rs.getInt(1));
				obj.setCodTabla(rs.getInt(2));
				obj.setCodTipoTabla(rs.getInt(3));
				obj.setDescripcion(rs.getString(4));
				obj.setDescripcionCorta(rs.getString(5));
				list.add(obj);
			}

		} catch (SQLException e) {

			throw new SisatException(e.getMessage(), e.getCause());
		}

		return list;
	}

	/**
	 * Método que obtiene la lista de campos de una tabla para ser mostrados en la
	 * grilla
	 * 
	 * @param tablaDTO Contiene la informacion de la tabla de la cual se quiere
	 *                 obtener los campos
	 * @return
	 * @throws SisatException
	 */
	public List<String> getLstParamsSelectTable(TablaDTO tablaDTO, String param) throws SisatException {

		List<String> lstParamsSelectTable = new ArrayList<String>();

		String sql = (new StringBuilder()).append("SELECT ").append(param).append(" FROM ")
				.append(SATParameterFactory.getDBNameScheme().concat("."))
				.append("mt_campo WHERE mt_campo.flag_mostrar_grid = '1' ").append("AND mt_campo.tabla_id = ")
				.append(tablaDTO.getCodTabla()).append(" ").append("AND mt_campo.modulo_id = ")
				.append(tablaDTO.getCodModulo()).append(" order by campo_id").toString();

		PreparedStatement pst;

		try {
			pst = connect().prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				lstParamsSelectTable.add(rs.getString(1));
			}

		} catch (SQLException e) {

			throw new SisatException(e.getMessage(), e.getCause());
		}

		return lstParamsSelectTable;
	}

	/**
	 * Método que obtiene toda la data de una tabla para ser mostrada en una grilla
	 * 
	 * @param lstParamsSelect Contiene la informacion sobre que campos pueden ser
	 *                        mostrados
	 * @param tablaDTO        Contiene la información de la tabla de la cual se
	 *                        desea mostrar su data
	 * @return
	 * @throws SisatException
	 */
	public List<RowLstDataDTO> getGridDataTable(List<String> lstParamsSelect, TablaDTO tablaDTO) throws SisatException {

		// obteniendo el listado de campos PK

		String columnsPK = getColumnPK(tablaDTO);

		List<RowLstDataDTO> gridData = null;

		int sizeLstParamsSelect = lstParamsSelect.size();

		List<CampoDTO> listCampoDTOs = getCampos(lstParamsSelect, tablaDTO);

		StringBuilder sb = new StringBuilder();

		sb.append("SELECT ");

		sb.append(columnsPK).append(", ");

		for (CampoDTO campoDTO : listCampoDTOs) {

			if (campoDTO.getTipoCampoId() == TIPO_CAMPO_FOREIGN) {
				sb.append(campoDTO.getNombreTablaPadre().concat(".").concat(campoDTO.getFiltroDatosPadre()));
				sb.append(", ");
			} else {
				sb.append(tablaDTO.getDescripcion().concat(".").concat(campoDTO.getNombreCampo()));
				sb.append(", ");
			}
		}

		if (sizeLstParamsSelect > 0) {

			gridData = new ArrayList<RowLstDataDTO>();
			int i = sb.lastIndexOf(", ");
			sb.deleteCharAt(i);

			sb.append("FROM ");
			sb.append(SATParameterFactory.getDBNameScheme().concat("."));
			sb.append(tablaDTO.getDescripcion());

			sb.append(" ").append(tablaDTO.getDescripcion());

			for (CampoDTO campoDTO : listCampoDTOs) {

				if (campoDTO.getTipoCampoId() == TIPO_CAMPO_FOREIGN) {
					sb.append(" inner join ");
					sb.append(SATParameterFactory.getDBNameScheme().concat("."));
					sb.append(campoDTO.getNombreTablaPadre()).append(" ").append(campoDTO.getNombreTablaPadre());
					sb.append(" on ");

					sb.append(tablaDTO.getDescripcion()).append(".").append(campoDTO.getNombreCampo()).append(" = ");
					sb.append(campoDTO.getNombreTablaPadre()).append(".").append(campoDTO.getMascara());
				}
			}

			sb.append(" ORDER BY ");

			sb.append(tablaDTO.getDescripcion()).append(".");
			sb.append(columnsPK);

			String sql = sb.toString();

			PreparedStatement pst;

			try {
				pst = connect().prepareStatement(sql);
				ResultSet rs = pst.executeQuery();

				while (rs.next()) {

					RowLstDataDTO rowData = new RowLstDataDTO();

					int k = 1;

					rowData.setKey(rs.getInt(k));

					k++;

					List<String> row = new ArrayList<String>();

					int niter = sizeLstParamsSelect + 1;

					for (; k <= niter; k++) {
						row.add(rs.getString(k));
					}

					rowData.setRowData(row);

					gridData.add(rowData);
				}

			} catch (SQLException e) {

				throw new SisatException(e.getMessage(), e.getCause());

			} catch (Exception e) {

				throw new SisatException(e.getMessage(), e.getCause());
			}

		}

		return gridData;
	}

	/**
	 * Nueva version de la obtension de los datos de una tabla parametro
	 * seleccionada
	 * 
	 * @param lstParamsSelect       Lista de columnas de la tabla seleccionada
	 * @param tablaDTO              Informacion de la tabla parámetro seleccionada
	 * @param listFiltroColunmaDTOs
	 * @return
	 * @throws SisatException
	 */
	public List<RowLstDataDTO> getGridDataTable1(List<String> lstParamsSelect, TablaDTO tablaDTO,
			List<FiltroColunmaDTO> listFiltroColunmaDTOs) throws SisatException {

		List<RowLstDataDTO> gridData = null;

		int sizeLstParamsSelect = 0;

		CampoDTO campoPK = getCampoPK(tablaDTO);

		List<CampoDTO> listCampoNuevos = new ArrayList<CampoDTO>();
		List<CampoDTO> listCampoNuevos1 = getFieldsGridPFK(tablaDTO);
		List<CampoDTO> listCampoNuevos2 = getFieldsGrid_NORMAL(tablaDTO);

		listCampoNuevos.add(campoPK);
		listCampoNuevos.addAll(listCampoNuevos1);
		listCampoNuevos.addAll(listCampoNuevos2);

		sizeLstParamsSelect = listCampoNuevos.size();

		StringBuilder sb = new StringBuilder();

		sb.append("SELECT ");

		sb.append(tablaDTO.getDescripcion()).append(".").append(campoPK.getNombreCampo()).append(", ");

		for (CampoDTO campoDTO : listCampoNuevos) {

			if (campoDTO.getTipoCampoId() == TIPO_CAMPO_FOREIGN
					|| campoDTO.getTipoCampoId() == TIPO_CAMPO_PRIMARYFOREIGN) {
				sb.append(campoDTO.getNombreTablaPadre().concat(".").concat(campoDTO.getFiltroDatosPadre()));
				sb.append(", ");
			} else {
				sb.append(tablaDTO.getDescripcion().concat(".").concat(campoDTO.getNombreCampo()));
				sb.append(", ");
			}
		}

		if (sizeLstParamsSelect > 0) {

			gridData = new ArrayList<RowLstDataDTO>();
			int i = sb.lastIndexOf(", ");
			sb.deleteCharAt(i);

			sb.append("FROM ");
			sb.append(SATParameterFactory.getDBNameScheme().concat("."));
			sb.append(tablaDTO.getDescripcion());

			sb.append(" ").append(tablaDTO.getDescripcion());

			for (CampoDTO campoDTO : listCampoNuevos) {

				// para campos foreign key y primary foreign
				if (campoDTO.getTipoCampoId() == TIPO_CAMPO_FOREIGN
						|| campoDTO.getTipoCampoId() == TIPO_CAMPO_PRIMARYFOREIGN) {

					String[] campos = campoDTO.getNombreCampo().split(",");
					String[] camposFK = campoDTO.getMascara().split(",");

					int z = camposFK.length;

					sb.append(" inner join ");
					sb.append(SATParameterFactory.getDBNameScheme().concat("."));
					sb.append(campoDTO.getNombreTablaPadre()).append(" ").append(campoDTO.getNombreTablaPadre());
					sb.append(" on ");

					for (int j = 0; j < z; j++) {

						sb.append(tablaDTO.getDescripcion()).append(".").append(campos[j].trim()).append(" = ");
						sb.append(campoDTO.getNombreTablaPadre()).append(".").append(camposFK[j].trim());
						sb.append(" AND ");
					}

					int iter = sb.lastIndexOf("AND");

					sb.delete(iter, iter + 3);
				}
			}

			if (listFiltroColunmaDTOs.size() > 0) {

				sb.append(" WHERE ");

				for (FiltroColunmaDTO filtro : listFiltroColunmaDTOs) {
					String valorFiltro = filtro.getValorFiltro().replace("*", "%");

					if (filtro.getCampoDTO().getTipoCampoId() == TIPO_CAMPO_FOREIGN
							|| filtro.getCampoDTO().getTipoCampoId() == TIPO_CAMPO_PRIMARYFOREIGN) {

						sb.append(filtro.getCampoDTO().getNombreTablaPadre().concat(".")
								.concat(filtro.getCampoDTO().getFiltroDatosPadre()));
						sb.append(" LIKE ");
						sb.append("'").append(valorFiltro).append("' ");
						sb.append("AND ");
					} else {
						sb.append(tablaDTO.getDescripcion().concat(".").concat(filtro.getCampoDTO().getNombreCampo()));
						sb.append(" LIKE ");
						sb.append("'").append(valorFiltro).append("' ");
						sb.append("AND ");
					}
				}

				int iter = sb.lastIndexOf("AND");

				sb.delete(iter, iter + 3);
			}

			sb.append(" ORDER BY ");

			sb.append(tablaDTO.getDescripcion()).append(".");
			sb.append(campoPK.getNombreCampo());

			String sql = sb.toString();

			// System.out.println(sql);

			PreparedStatement pst;

			int n = sizeLstParamsSelect + 1;

			try {
				pst = connect().prepareStatement(sql);
				ResultSet rs = pst.executeQuery();

				while (rs.next()) {

					RowLstDataDTO rowData = new RowLstDataDTO();

					int k = 1;

					rowData.setKey(rs.getInt(k));

					k++;

					List<String> row = new ArrayList<String>();

					for (; k <= n; k++) {
						row.add(rs.getString(k));
					}

					rowData.setRowData(row);

					gridData.add(rowData);
				}

			} catch (SQLException e) {

				throw new SisatException(e.getMessage().concat("SQL: ").concat(sql), e.getCause());

			} catch (Exception e) {

				throw new SisatException(e.getMessage(), e.getCause());
			}

		}

		return gridData;
	}

	public List<CampoDTO> getCampos(List<String> lstParamsSelect, TablaDTO tablaDTO) throws SisatException {

		List<CampoDTO> listCampoDTO = new ArrayList<CampoDTO>();

		StringBuilder sbSql = new StringBuilder();

		sbSql.append("SELECT ");
		sbSql.append("tipo_campo_id, ");
		sbSql.append("nombre_campo, ");
		sbSql.append("nombre_tabla_padre, ");
		sbSql.append("mascara, ");
		sbSql.append("filtro_datos_padre ");
		sbSql.append("FROM ");
		sbSql.append(SATParameterFactory.getDBNameScheme().concat("."));
		sbSql.append("mt_campo mt_campo ");
		sbSql.append("WHERE mt_campo.tabla_id = ");
		sbSql.append(tablaDTO.getCodTabla());
		sbSql.append(" and mt_campo.nombre_campo = ? ");

		String sql = sbSql.toString();
		PreparedStatement pst;

		try {
			pst = connect().prepareStatement(sql);

			for (String s : lstParamsSelect) {
				pst.setString(1, s);
				ResultSet rs = pst.executeQuery();

				while (rs.next()) {
					CampoDTO campoDTO = new CampoDTO();

					campoDTO.setTipoCampoId(rs.getInt(1));
					campoDTO.setNombreCampo(rs.getString(2));
					campoDTO.setNombreTablaPadre(rs.getString(3));
					campoDTO.setMascara(rs.getString(4));
					campoDTO.setFiltroDatosPadre(rs.getString(5));
					listCampoDTO.add(campoDTO);
				}
			}

		} catch (SQLException e) {

			throw new SisatException(e.getMessage(), e.getCause());

		} catch (Exception e) {

			throw new SisatException(e.getMessage(), e.getCause());
		}

		return listCampoDTO;

	}

	/**
	 * Método para la obtencion de campos editables, estos campos seran mostrados en
	 * los formularios de edicion y/o actualizacion
	 * 
	 * @param tablaDTO Contiene la informacion de la tabla de la cual se quiere
	 *                 obtener los campos
	 * @return
	 * @throws SisatException
	 */
	public List<CampoDTO> getFieldsEditables(TablaDTO tablaDTO) throws SisatException {

		List<CampoDTO> lstFieldsEditablesTable = new ArrayList<CampoDTO>();

		List<CampoDTO> lstPFK = getFieldsEditablesPFK(tablaDTO);

		for (CampoDTO campoDTO : lstPFK) {
			lstFieldsEditablesTable.add(campoDTO);
		}

		List<CampoDTO> lstFK_NORMAL = getFieldsEditablesFK_NORMAL(tablaDTO);

		for (CampoDTO campoDTO : lstFK_NORMAL) {
			lstFieldsEditablesTable.add(campoDTO);
		}

		return lstFieldsEditablesTable;
	}

	public List<CampoDTO> getFieldsGridPFK(TablaDTO tablaDTO) throws SisatException {

		List<CampoDTO> lstFieldsGrid = new ArrayList<CampoDTO>();

		List<String> tablesPFK = new ArrayList<String>();

		StringBuilder sbSql = new StringBuilder();

		sbSql.append("SELECT DISTINCT  ");
		sbSql.append("nombre_tabla_padre FROM ");
		sbSql.append(SATParameterFactory.getDBNameScheme().concat("."));
		sbSql.append("mt_campo ");
		sbSql.append("WHERE ");
		sbSql.append("tabla_id = ");
		sbSql.append(tablaDTO.getCodTabla());
		sbSql.append(" AND tipo_campo_id in (2, 4) AND mt_campo.flag_mostrar_grid = 1");

		// Obteniendo las tabla padres PFK

		String sql = sbSql.toString();
		PreparedStatement pst;

		try {
			pst = connect().prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				tablesPFK.add(rs.getString(1));
			}
		} catch (SQLException e) {
			throw new SisatException(e.getMessage(), e.getCause());
		}

		if (tablesPFK.size() > 0) {

			StringBuilder sbQuery = new StringBuilder();
			sbQuery.append("SELECT ");
			sbQuery.append("tipo_campo_id, "); // --1
			sbQuery.append("nombre_campo, "); // --2
			sbQuery.append("alias, "); // --3
			sbQuery.append("nombre_tabla_padre, "); // --4
			sbQuery.append("mascara, "); // --5
			sbQuery.append("filtro_datos_padre, "); // --6
			sbQuery.append("descripcion "); // --7
			sbQuery.append("FROM ");
			sbQuery.append(SATParameterFactory.getDBNameScheme().concat("."));
			sbQuery.append("mt_campo ");
			sbQuery.append("WHERE ");
			sbQuery.append("mt_campo.flag_mostrar_grid = ").append(MOSTRAR).append(" ");
			sbQuery.append("AND tipo_campo_id in (2, 4) ");
			sbQuery.append("AND tabla_id = ").append(tablaDTO.getCodTabla());
			sbQuery.append(" AND nombre_tabla_padre = ?");

			String sqlQuery = sbQuery.toString();

			try {

				pst = connect().prepareStatement(sqlQuery);

				for (String colPFK : tablesPFK) {
					CampoDTO campoDTO = new CampoDTO();

					pst.setString(1, colPFK);
					ResultSet rs = pst.executeQuery();
					while (rs.next()) {

						campoDTO.setTipoCampoId(rs.getInt(1));

						if (campoDTO.getNombreCampo() != null && !campoDTO.getNombreCampo().isEmpty()) {
							campoDTO.setNombreCampo(campoDTO.getNombreCampo() + ", " + rs.getString(7));
						} else {
							campoDTO.setNombreCampo(rs.getString(2));
						}

						campoDTO.setAlias(rs.getString(3));
						campoDTO.setNombreTablaPadre(rs.getString(4));

						if (campoDTO.getMascara() != null && !campoDTO.getMascara().isEmpty()) {
							campoDTO.setMascara(campoDTO.getMascara() + ", " + rs.getString(5));
						} else {
							campoDTO.setMascara(rs.getString(5));
						}

						campoDTO.setFiltroDatosPadre(rs.getString(6));
						campoDTO.setDescripcion(rs.getString(7));

					}

					lstFieldsGrid.add(campoDTO);
				}

			} catch (SQLException e) {

				throw new SisatException(e.getMessage(), e.getCause());
			}

		}
		return lstFieldsGrid;
	}

	public List<CampoDTO> getFieldsGrid_NORMAL(TablaDTO tablaDTO) throws SisatException {

		List<CampoDTO> lstFieldsGrid = new ArrayList<CampoDTO>();

		StringBuilder sbQuery = new StringBuilder();
		sbQuery.append("SELECT ");
		sbQuery.append("tipo_campo_id, "); // --1
		sbQuery.append("nombre_campo, "); // --2
		sbQuery.append("alias, "); // --3
		sbQuery.append("nombre_tabla_padre, "); // --4
		sbQuery.append("mascara, "); // --5
		sbQuery.append("filtro_datos_padre, "); // --6
		sbQuery.append("descripcion "); // --7
		sbQuery.append("FROM ");
		sbQuery.append(SATParameterFactory.getDBNameScheme().concat("."));
		sbQuery.append("mt_campo ");
		sbQuery.append("WHERE ");
		sbQuery.append("mt_campo.flag_mostrar_grid = ").append(MOSTRAR).append(" ");
		sbQuery.append("AND tipo_campo_id = 3 ");
		sbQuery.append("AND tabla_id = ").append(tablaDTO.getCodTabla());

		String sqlQuery = sbQuery.toString();

		PreparedStatement pst;

		try {
			pst = connect().prepareStatement(sqlQuery);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				CampoDTO campoDTO = new CampoDTO();

				campoDTO.setTipoCampoId(rs.getInt(1));
				campoDTO.setNombreCampo(rs.getString(2));
				campoDTO.setAlias(rs.getString(3));
				campoDTO.setNombreTablaPadre(rs.getString(4));
				campoDTO.setMascara(rs.getString(5));
				campoDTO.setFiltroDatosPadre(rs.getString(6));
				campoDTO.setDescripcion(rs.getString(7));

				lstFieldsGrid.add(campoDTO);
			}

		} catch (SQLException e) {

			throw new SisatException(e.getMessage(), e.getCause());
		}

		return lstFieldsGrid;
	}

	public List<CampoDTO> getFieldsGrid(TablaDTO tablaDTO) throws SisatException {

		List<CampoDTO> listCampo = new ArrayList<CampoDTO>();

		listCampo.add(getCampoPK(tablaDTO));

		listCampo.addAll(getFieldsGridPFK(tablaDTO));

		listCampo.addAll(getFieldsGrid_NORMAL(tablaDTO));

		return listCampo;
	}

	/**
	 * 
	 * @param tablaDTO
	 * @return
	 * @throws SisatException
	 */
	public List<CampoDTO> getFieldsEditablesPFK(TablaDTO tablaDTO) throws SisatException {

		List<CampoDTO> lstFieldsEditablesTable = new ArrayList<CampoDTO>();

		List<String> tablesPFK = new ArrayList<String>();

		String sql = (new StringBuilder()).append("SELECT DISTINCT  ").append("nombre_tabla_padre ").append("FROM ")
				.append(SATParameterFactory.getDBNameScheme().concat(".")).append("mt_campo ")
				.append("WHERE tabla_id = ").append(tablaDTO.getCodTabla())
				.append(" AND tipo_campo_id = 4 AND mt_campo.flag_mostrar_control = 1").toString();

		// Obteniendo las tabla padres PFK
		PreparedStatement pst;

		try {
			pst = connect().prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				tablesPFK.add(rs.getString(1));
			}
		} catch (SQLException e) {
			throw new SisatException(e.getMessage(), e.getCause());
		}

		if (tablesPFK.size() > 0) {

			String sql1 = (new StringBuilder()).append("SELECT ").append("modulo_id, ").append("tabla_id, ")
					.append("campo_id, ").append("tipo_campo_id, ").append("tipo_control_id, ").append("tipo_dato_id, ")
					.append("nombre_campo, ").append("descripcion_corta, ").append("descripcion, ")
					.append("alias, tamano, ").append("mascara, ").append("filtro_datos_padre, ")
					.append("nombre_tabla_padre, ").append("estado, ").append("flag_mostrar_control, ")
					.append("flag_mostrar_grid ").append("FROM ")
					.append(SATParameterFactory.getDBNameScheme().concat(".")).append("mt_campo ").append("WHERE ")
					.append("mt_campo.flag_mostrar_control = '").append(MOSTRAR).append("'")
					.append(" AND mt_campo.tabla_id = ").append(tablaDTO.getCodTabla())
					.append(" AND mt_campo.modulo_id = ").append(tablaDTO.getCodModulo())
					.append(" AND tipo_campo_id = 4 ").append(" AND mt_campo.nombre_tabla_padre = ?").toString();
			try {

				pst = connect().prepareStatement(sql1);

				for (String colPFK : tablesPFK) {
					CampoDTO campoDTO = new CampoDTO();

					pst.setString(1, colPFK);
					ResultSet rs = pst.executeQuery();
					while (rs.next()) {
						campoDTO.setModuloId(rs.getInt(1));
						campoDTO.setTablaId(rs.getInt(2));
						campoDTO.setCampoId(rs.getInt(3));
						campoDTO.setTipoCampoId(rs.getInt(4));
						campoDTO.setTipoControlId(rs.getInt(5));
						campoDTO.setTipoDatoId(rs.getInt(6));

						if (campoDTO.getNombreCampo() != null && !campoDTO.getNombreCampo().isEmpty()) {
							campoDTO.setNombreCampo(campoDTO.getNombreCampo() + ", " + rs.getString(7));
						} else {
							campoDTO.setNombreCampo(rs.getString(7));
						}

						campoDTO.setDescripcionCorta(rs.getString(8));
						campoDTO.setDescripcion(rs.getString(9));
						campoDTO.setAlias(rs.getString(10));
						campoDTO.setTamano(rs.getInt(11));

						if (campoDTO.getMascara() != null && !campoDTO.getMascara().isEmpty()) {
							campoDTO.setMascara(campoDTO.getMascara() + ", " + rs.getString(12));
						} else {
							campoDTO.setMascara(rs.getString(12));
						}

						campoDTO.setFiltroDatosPadre(rs.getString(13));

						campoDTO.setNombreTablaPadre(rs.getString(14));
						campoDTO.setEstado(rs.getString(15).charAt(0));

						if (rs.getString(16).compareTo(MOSTRAR) == 0) {
							campoDTO.setFlagMostrarControl(true);
						}

						if (rs.getString(17).compareTo(MOSTRAR) == 0) {
							campoDTO.setFlagMostrarGrid(true);
						}
					}

					lstFieldsEditablesTable.add(campoDTO);
				}

			} catch (SQLException e) {

				throw new SisatException(e.getMessage(), e.getCause());
			}

		}
		return lstFieldsEditablesTable;
	}

	/**
	 * 
	 * @param tablaDTO
	 * @return
	 * @throws SisatException
	 */
	public List<CampoDTO> getFieldsEditablesFK_NORMAL(TablaDTO tablaDTO) throws SisatException {

		List<CampoDTO> lstFieldsEditablesTable = new ArrayList<CampoDTO>();

		String sql = (new StringBuilder()).append("SELECT ").append("modulo_id, ").append("tabla_id, ")
				.append("campo_id, ").append("tipo_campo_id, ").append("tipo_control_id, ").append("tipo_dato_id, ")
				.append("nombre_campo, ").append("descripcion_corta, ").append("descripcion, ").append("alias, ")
				.append("tamano, ").append("mascara, ").append("filtro_datos_padre, ").append("nombre_tabla_padre, ")
				.append("estado, ").append("flag_mostrar_control, ").append("flag_mostrar_grid ").append("FROM ")
				.append(SATParameterFactory.getDBNameScheme().concat(".")).append("mt_campo ")
				.append("WHERE mt_campo.flag_mostrar_control = '").append(MOSTRAR).append("'")
				.append("AND mt_campo.tabla_id = ").append(tablaDTO.getCodTabla()).append("AND mt_campo.modulo_id = ")
				.append(tablaDTO.getCodModulo()).append(" AND tipo_campo_id in (2, 3)").toString();

		PreparedStatement pst;

		try {
			pst = connect().prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				CampoDTO campoDTO = new CampoDTO();

				campoDTO.setModuloId(rs.getInt(1));
				campoDTO.setTablaId(rs.getInt(2));
				campoDTO.setCampoId(rs.getInt(3));
				campoDTO.setTipoCampoId(rs.getInt(4));
				campoDTO.setTipoControlId(rs.getInt(5));
				campoDTO.setTipoDatoId(rs.getInt(6));
				campoDTO.setNombreCampo(rs.getString(7));
				campoDTO.setDescripcionCorta(rs.getString(8));
				campoDTO.setDescripcion(rs.getString(9));
				campoDTO.setAlias(rs.getString(10));
				campoDTO.setTamano(rs.getInt(11));
				campoDTO.setMascara(rs.getString(12));
				campoDTO.setFiltroDatosPadre(rs.getString(13));
				campoDTO.setNombreTablaPadre(rs.getString(14));
				campoDTO.setEstado(rs.getString(15).charAt(0));

				if (rs.getString(16).compareTo(MOSTRAR) == 0) {
					campoDTO.setFlagMostrarControl(true);
				}

				if (rs.getString(17).compareTo(MOSTRAR) == 0) {
					campoDTO.setFlagMostrarGrid(true);
				}

				lstFieldsEditablesTable.add(campoDTO);
			}

		} catch (SQLException e) {

			throw new SisatException(e.getMessage(), e.getCause());
		}

		return lstFieldsEditablesTable;
	}

	/**
	 * Metodo que crea o actualiza un registro en una tabla
	 * 
	 * @param lstFieldsEditables Lista de campos editables de la tabla
	 * @param isUpdate           Flag que nos indica si es una actualizacion (true)
	 *                           o un nuevo registro (false)
	 * @throws SisatException
	 */
	public void saveEntryTable(List<CampoDTO> lstFieldsEditables, TablaDTO tablaDTO, UserSession user)
			throws SisatException {

		String columnPK = getColumnPK(tablaDTO);

		int i_sequence = this.getIdSequence(tablaDTO, columnPK, user);

		// armando el sql de insercion

		StringBuilder sbHeader = new StringBuilder();

		StringBuilder sbData = new StringBuilder();

		StringBuilder sbFields = new StringBuilder();

		sbHeader.append("INSERT INTO ");
		sbHeader.append(SATParameterFactory.getDBNameScheme().concat("."));
		sbHeader.append(tablaDTO.getDescripcion());
		sbData.append(" VALUES (");

		// asumimos que el usuario no tiene que ingresar la primary key, debido a que
		// podria modificarla mas adenlante

		// campo para insercion de la primary key
		sbFields.append("(").append(columnPK);

		sbData.append(i_sequence);

		for (CampoDTO campoDTO : lstFieldsEditables) {

			if (campoDTO.isFlagMostrarControl() && !campoDTO.getValorCampoUpdate().isEmpty()) {

				switch (campoDTO.getTipoDatoId()) {

				// caso el campo sea un int

				case TIPO_DATO_INT:
					// caso que el control sea un checkbox permitiendo asi
					// solo 2 valores posibles
					if (campoDTO.getTipoControlId() == TIPO_CONTROL_CHECKBOX) {

						if (campoDTO.getValorCampoUpdate().equals("true")) {
							sbData.append(", ").append(Constante.ESTADO_ACTIVO);

							sbFields.append(", ").append(campoDTO.getNombreCampo());
						} else {
							sbData.append(", ").append(Constante.ESTADO_INACTIVO);

							sbFields.append(", ").append(campoDTO.getNombreCampo());
						}

						// caso que el control sea un inputtext
					} else {
						sbData.append(", ").append(campoDTO.getValorCampoUpdate());

						sbFields.append(", ").append(campoDTO.getNombreCampo());
					}
					break;

				// caso que el campo sea un varchar
				case TIPO_DATO_VARCHAR:

					sbData.append(", '").append(campoDTO.getValorCampoUpdate()).append("'");

					sbFields.append(", ").append(campoDTO.getNombreCampo());

					break;
				// caso que el campo sea un char
				case TIPO_DATO_CHAR:

					// caso que el control sea un checkbox permitiendo asi
					// solo 2 valores posibles
					if (campoDTO.getTipoControlId() == TIPO_CONTROL_CHECKBOX) {

						if (campoDTO.getValorCampoUpdate().equals("true")) {
							sbData.append(", ").append(Constante.ESTADO_ACTIVO);

							sbFields.append(", ").append(campoDTO.getNombreCampo());
						} else {
							sbData.append(", ").append(Constante.ESTADO_INACTIVO);

							sbFields.append(", ").append(campoDTO.getNombreCampo());
						}

						// caso que el control sea un inputtext
					} else {
						sbData.append(", '").append(campoDTO.getValorCampoUpdate()).append("'");

						sbFields.append(", ").append(campoDTO.getNombreCampo());
					}
					break;

				// caso que sea formato fecha
				case TIPO_DATO_DATETIME:

					String fecha = "";
					SimpleDateFormat formatter;
					SimpleDateFormat formatter2;
					Date date;

					formatter = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy", Locale.US);

					formatter2 = new SimpleDateFormat(Constante.FORMATO_FECHA, Locale.US);

					try {
						date = (Date) formatter.parse(campoDTO.getValorCampoUpdate());
						fecha = formatter2.format(date);
					} catch (ParseException e) {
						throw new SisatException(e.getMessage(), e.getCause());

					}

					sbData.append(", ").append("(SELECT CONVERT (datetime, '");
					sbData.append(fecha).append("', 103))");

					sbFields.append(", ").append(campoDTO.getNombreCampo());
					break;

				// caso que sea formato fecha
				case TIPO_DATO_DATE:

					String fecha1 = "";
					SimpleDateFormat formatter1;
					SimpleDateFormat formatter21;
					Date date1;

					formatter1 = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy", Locale.US);

					formatter21 = new SimpleDateFormat(Constante.FORMATO_FECHA, Locale.US);

					try {
						date1 = (Date) formatter1.parse(campoDTO.getValorCampoUpdate());
						fecha1 = formatter21.format(date1);
					} catch (ParseException e) {
						throw new SisatException(e.getMessage(), e.getCause());

					}

					sbData.append(", ").append("(SELECT CONVERT (datetime, '");
					sbData.append(fecha1).append("', 103))");

					sbFields.append(", ").append(campoDTO.getNombreCampo());
					break;

				// caso numeric
				case TIPO_DATO_NUMERIC:

					sbData.append(", ").append(campoDTO.getValorCampoUpdate());

					sbFields.append(", ").append(campoDTO.getNombreCampo());
					break;
				default:
					break;
				}
			}
		}

		sbFields.append(", ").append("usuario_id");
		sbData.append(", ").append(user.getUsuarioId());

		sbFields.append(", ").append("fecha_registro");
		sbData.append(", ").append("GETDATE()");

		sbFields.append(", ").append("terminal");
		sbData.append(", ").append("'").append(user.getTerminal()).append("'");

		sbFields.append(")");
		sbData.append(")");

		String sql = sbHeader.toString().concat(sbFields.toString()).concat(sbData.toString());
		// ejecutando la insercion

		try {
			PreparedStatement pst = connect().prepareStatement(sql);

			pst.execute();

		} catch (SQLException e) {

			throw new SisatException(e.getMessage(), e.getCause());
		}

	}

	/**
	 * Método que permite la obtención de campos editables asi como sus valores
	 * actuales y que seran mostrados en los formularios de actualización
	 * 
	 * @param tablaDTO               Contiene la informacion de la tabla sobre la
	 *                               cual se obtendran los campos.
	 * @param rowLstDataDTO          Contiene la informacion del registro que
	 *                               deseamos obtener sus campos editables
	 * @param lstFieldEditablesTable Contiene la lista de campos editables pero no
	 *                               poseen los valores cargados
	 * @return Listado de campos editables de una tabla maestra
	 * @throws SisatException
	 */
	public List<CampoDTO> getUpdateFieldsEditables(TablaDTO tablaDTO, RowLstDataDTO rowLstDataDTO,
			List<CampoDTO> lstFieldEditablesTable) throws SisatException {

		StringBuilder sb = new StringBuilder();

		sb.append("SELECT ");

		for (CampoDTO campoDTO : lstFieldEditablesTable) {

			sb.append(campoDTO.getNombreCampo());
			sb.append(", ");

		}

		int i = sb.lastIndexOf(", ");
		sb.deleteCharAt(i);

		sb.append("FROM ");
		sb.append(SATParameterFactory.getDBNameScheme().concat("."));
		sb.append(tablaDTO.getDescripcion());
		sb.append(" WHERE ");

		// aqui se necesita la primary key para realizar la busqueda

		String columnPK = getColumnPK(tablaDTO);

		sb.append(columnPK);
		sb.append(" = ");
		sb.append(rowLstDataDTO.getKey());

		String sql = sb.toString();
		try {
			PreparedStatement pst = connect().prepareStatement(sql);

			ResultSet rs = pst.executeQuery();

			int j = 1;

			while (rs.next()) {
				for (CampoDTO campoDTO : lstFieldEditablesTable) {

					switch (campoDTO.getTipoControlId()) {

					// caso de control calendar
					case TIPO_CONTROL_CALENDAR:

						Date d = rs.getDate(j++);
						if (d != null) {
							SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constante.FORMATO_FECHA,
									Locale.US);

							campoDTO.setValorCampoUpdate(simpleDateFormat.format(d));
						} else {
							campoDTO.setValorCampoUpdate("");
						}

						break;

					// caso de control inputtext
					case TIPO_CONTROL_INPUTTEXT:

						campoDTO.setValorCampoUpdate(rs.getString(j++));
						break;

					// caso de control checkbox
					case TIPO_CONTROL_CHECKBOX:
						// comparando si el campo en la base de datos es un char o un int
						if (campoDTO.getTipoDatoId() == TIPO_DATO_CHAR || campoDTO.getTipoDatoId() == TIPO_DATO_INT) {

							// no consideramos el estado ESTADO_ELIMINADO ya que
							// por req no se pueden eliminar registros de estas tablas
							boolean b = (rs.getString(j++).compareTo(Constante.ESTADO_ACTIVO) == 0) ? true : false;

							campoDTO.setValorCampoUpdate(String.valueOf(b));
						}

						break;

					// caso de control combobox
					case TIPO_CONTROL_COMBOBOX:

						/** Para casos donde el campo sea primary foreing key */
						if (campoDTO.getTipoCampoId() == 4) {
							String[] tokens = campoDTO.getNombreCampo().split(",");

							int nTokens = tokens.length;
							String t = null;
							/** Movemos la cantidad de tokens necesario para realizar la actualizacion */
							nTokens = j + nTokens - 1;

							for (; j <= nTokens; j++) {
								if (t == null) {
									t = rs.getString(j);
								} else {
									t = t.concat(", ").concat(rs.getString(j));
								}
							}

							campoDTO.setValorCampoUpdate(t);

						} else {
							campoDTO.setValorCampoUpdate(rs.getString(j++));
						}

						break;

					default:
						break;
					}

				}
			}

		} catch (SQLException e) {

			throw new SisatException(e.getMessage(), e.getCause());
		}

		catch (Exception e) {

			throw new SisatException(e.getMessage(), e.getCause());
		}

		return lstFieldEditablesTable;
	}

	/**
	 * Método para la actualización de una entrada en una tabla
	 * 
	 * @param lstFieldsEditables Contiene los campos actualizables
	 * @param tablaDTO           Contiene la informacion de la tabla sobre la cual
	 *                           se realizar la actualizacion del registro
	 * @param rowLstDataDTO      Contiene la información del registro a actualizar
	 *                           (id)
	 * @throws SisatException
	 */
	public void updateEntryTable(List<CampoDTO> lstFieldsEditables, TablaDTO tablaDTO, RowLstDataDTO rowLstDataDTO)
			throws SisatException {

		// obteniendo los campos PK

		String columnPK = getColumnPK(tablaDTO);

		// armando el sql de actualizacion
		StringBuilder sb = new StringBuilder();

		sb.append("UPDATE ");
		sb.append(SATParameterFactory.getDBNameScheme().concat("."));
		sb.append(tablaDTO.getDescripcion());
		sb.append(" SET ");

		for (CampoDTO campoDTO : lstFieldsEditables) {

			if (campoDTO.isFlagMostrarControl() && !campoDTO.getValorCampoUpdate().isEmpty()) {

				if (campoDTO.getTipoCampoId() == 4) {

					String[] tokenColumns = campoDTO.getNombreCampo().split(",");

					String[] tokenData = campoDTO.getValorCampoUpdate().split(",");

					int i = 0;
					for (String s : tokenColumns) {
						sb.append(s).append(" = ");

						sb.append(tokenData[i++]).append(", ");
					}

				} else {

					switch (campoDTO.getTipoDatoId()) {

					// caso el campo sea un int
					case TIPO_DATO_INT:
						// caso que el control sea un checkbox permitiendo asi
						// solo 2 valores posibles
						if (campoDTO.getTipoControlId() == 3) {

							if (campoDTO.getValorCampoUpdate().equals("true")) {

								sb.append(campoDTO.getNombreCampo()).append(" = ");

								sb.append(Constante.ESTADO_ACTIVO).append(", ");
							} else {

								sb.append(campoDTO.getNombreCampo()).append(" = ");

								sb.append(Constante.ESTADO_INACTIVO).append(", ");
							}

							// caso que el control sea un inputtext
						} else {
							sb.append(campoDTO.getNombreCampo()).append(" = ");

							sb.append(campoDTO.getValorCampoUpdate()).append(", ");
						}
						break;

					// caso que el campo sea un varchar
					case TIPO_DATO_VARCHAR:

						sb.append(campoDTO.getNombreCampo()).append(" = ");

						sb.append("'").append(campoDTO.getValorCampoUpdate()).append("'").append(", ");
						break;
					// caso que el campo sea un char
					case TIPO_DATO_CHAR:

						// caso que el control sea un checkbox permitiendo asi
						// solo 2 valores posibles
						if (campoDTO.getTipoControlId() == 3) {

							if (campoDTO.getValorCampoUpdate().equals("true")) {

								sb.append(campoDTO.getNombreCampo()).append(" = ");

								sb.append(Constante.ESTADO_ACTIVO).append(", ");
							} else {

								sb.append(campoDTO.getNombreCampo()).append(" = ");

								sb.append(Constante.ESTADO_INACTIVO).append(", ");
							}

							// caso que el control sea un inputtext
						} else {

							sb.append(campoDTO.getNombreCampo()).append(" = ");

							sb.append(" '").append(campoDTO.getValorCampoUpdate()).append("'").append(", ");
						}
						break;

					// caso que sea formato fecha
					case TIPO_DATO_DATETIME:

						String fecha = "";
						SimpleDateFormat formatter;
						SimpleDateFormat formatter2;
						Date date;

						formatter = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy", Locale.US);

						formatter2 = new SimpleDateFormat(Constante.FORMATO_FECHA, Locale.US);

						try {
							if (campoDTO.getValorCampoUpdate().isEmpty()) {
								date = Calendar.getInstance().getTime();
							} else {
								date = (Date) formatter.parse(campoDTO.getValorCampoUpdate());
							}

							fecha = formatter2.format(date);
						} catch (ParseException e) {
							throw new SisatException(e.getMessage(), e.getCause());

						}

						sb.append(campoDTO.getNombreCampo()).append(" = ");

						sb.append("(SELECT CONVERT (datetime, '");
						sb.append(fecha).append("', 103))").append(", ");
						break;

					// caso que sea formato fecha
					case TIPO_DATO_DATE:

						String fecha1 = "";
						SimpleDateFormat formatter1;
						SimpleDateFormat formatter21;
						Date date1;

						formatter1 = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy", Locale.US);

						formatter21 = new SimpleDateFormat(Constante.FORMATO_FECHA, Locale.US);

						try {
							if (campoDTO.getValorCampoUpdate().isEmpty()) {
								date1 = Calendar.getInstance().getTime();
							} else {
								date1 = (Date) formatter1.parse(campoDTO.getValorCampoUpdate());
							}

							fecha1 = formatter21.format(date1);
						} catch (ParseException e) {
							throw new SisatException(e.getMessage(), e.getCause());

						}

						sb.append(campoDTO.getNombreCampo()).append(" = ");

						sb.append("(SELECT CONVERT (datetime, '");
						sb.append(fecha1).append("', 103))").append(", ");
						break;

					// caso numeric
					case TIPO_DATO_NUMERIC:

						sb.append(campoDTO.getNombreCampo()).append(" = ");

						sb.append(campoDTO.getValorCampoUpdate()).append(", ");
						break;
					default:
						break;
					}
				}
			}
		}

		int i = sb.lastIndexOf(", ");
		sb.deleteCharAt(i);

		sb.append(" WHERE ");

		sb.append(columnPK);
		sb.append(" = ");
		sb.append(rowLstDataDTO.getKey());

		String sql = sb.toString();

		// ejecutando la actualizacion
		try {
			PreparedStatement pst = connect().prepareStatement(sql);

			pst.execute();

		} catch (SQLException e) {

			throw new SisatException(e.getMessage(), e.getCause());
		}

	}

	/**
	 * Método que realiza la duplicacion de un o varios registros en una tabla
	 * 
	 * @param lstKeyRows             Contiene los id (PK) de los registros a
	 *                               duplicar
	 * @param tablaDTO               Contiene la informacion de la tabla sobre la
	 *                               cual se realizara la duplicación
	 * @param lstFieldEditablesTable Contiene la informacion sobre que campos en la
	 *                               tabla pueden ser duplicados
	 * @throws SisatException
	 */
	public void duplicatedEntryTable(List<Integer> lstKeyRows, TablaDTO tablaDTO, List<CampoDTO> lstFieldEditablesTable,
			UserSession user) throws SisatException {

		StringBuilder sb = new StringBuilder();
		StringBuilder sbSelect = new StringBuilder();

		String columnPK = getColumnPK(tablaDTO);

		checkSequence(tablaDTO, columnPK, user);

		sb.append("INSERT INTO ");
		sb.append(SATParameterFactory.getDBNameScheme().concat("."));
		sb.append(tablaDTO.getDescripcion()).append(" ");

		sb.append("(");

		sb.append(columnPK).append(" ");

		for (CampoDTO campoDTO : lstFieldEditablesTable) {

			String nombreCampo = campoDTO.getNombreCampo();
			if (!nombreCampo.equals("estado")) {
				sb.append(", ").append(nombreCampo);
				sbSelect.append(", ").append(nombreCampo);
			}
		}

		sb.append(", usuario_id");
		sb.append(", fecha_registro");
		sb.append(", estado");
		sb.append(", terminal ");

		sb.append(") ");

		sb.append("SELECT ");
		sb.append("? ");
		sb.append(sbSelect.toString());

		sb.append(", ").append(user.getUsuarioId());
		sb.append(", GETDATE()");
		sb.append(", 0");
		sb.append(", '").append(user.getTerminal()).append("' ");

		sb.append(" FROM ");
		sb.append(SATParameterFactory.getDBNameScheme().concat("."));
		sb.append(tablaDTO.getDescripcion());
		sb.append(" WHERE ");

		sb.append(SATParameterFactory.getDBNameScheme().concat("."));
		sb.append(tablaDTO.getDescripcion());
		sb.append(".").append(columnPK).append(" = ");
		sb.append("? ");

		String sql = sb.toString();

		Connection con; // variables de instancia
		PreparedStatement stat = null;
		con = connect();

		try {
			if (con.getAutoCommit()) {

				con.setAutoCommit(false);
			}

			stat = con.prepareCall(sql);

			// revisar para multiples PK
			for (Integer i : lstKeyRows) {
				stat.setInt(1, getIdSequence(tablaDTO, columnPK, user));
				stat.setInt(2, i.intValue());
				stat.execute();
			}

			con.commit();

		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException ex) {
				throw new SisatException(ex);
			}
			throw new SisatException(e.getMessage(), e.getCause());
		} finally {

			try {
				// restauramos la conexion al autocommit
				if (con != null) {
					con.setAutoCommit(true);
				}
				// cerramos el objeto stat
				if (stat != null) {
					stat.close();
				}
			} catch (SQLException e) {
				throw new SisatException(e.getMessage(), e.getCause());
			}
		}
	}

	/**
	 * Método que verifica si una tabla maestra contiene un secuenciador asociado si
	 * no posee el secuenciador, entonces es creado.
	 * 
	 * @param tablaDTO
	 * @param mascara
	 * @throws SisatException
	 */
	private void checkSequence(TablaDTO tablaDTO, String mascara, UserSession user) throws SisatException {

		int i_sequence = this.ObtenerCorrelativoTabla(tablaDTO.getDescripcion(), 0);

		if (i_sequence == 0) {
			StringBuilder sbSequence = new StringBuilder();
			sbSequence.append("INSERT INTO ");
			sbSequence.append(SATParameterFactory.getDBNameScheme().concat(".gn_correlativo"));
			sbSequence.append("(correlativo_id ");
			sbSequence.append(", tabla");
			sbSequence.append(", estado");
			sbSequence.append(", valor_inicial");
			sbSequence.append(", valor_actual");
			sbSequence.append(", valor_final");
			sbSequence.append(", usuario_id");
			sbSequence.append(", fecha_registro");
			sbSequence.append(", fecha_actualizacion");
			sbSequence.append(", terminal");
			sbSequence.append(", anno) ");

			sbSequence.append("VALUES ");
			sbSequence.append(" ((SELECT max(correlativo_id)+1  FROM ")
					.append(SATParameterFactory.getDBNameScheme().concat(".gn_correlativo)"));

			sbSequence.append(", '").append(tablaDTO.getDescripcion()).append("'");

			sbSequence.append(", 1");
			sbSequence.append(", 1");

			// verificando si la tabla a registar el secuenciador tiene
			// registros.
			String sqlCountReg = "SELECT COUNT (*) FROM ".concat(SATParameterFactory.getDBNameScheme().concat("."))
					.concat(tablaDTO.getDescripcion());

			int index = 0;
			try {
				PreparedStatement pst = connect().prepareStatement(sqlCountReg);

				ResultSet rs = pst.executeQuery();

				while (rs.next()) {
					index = rs.getInt(1);
				}

			} catch (SQLException e) {

				throw new SisatException(e.getMessage(), e.getCause());
			}

			// fin de la verificacion de los registros

			if (index == 0) {
				sbSequence.append(", 0 ");
			} else {
				sbSequence.append(", (SELECT max(").append(mascara).append(") FROM ")
						.append(SATParameterFactory.getDBNameScheme().concat("."));
				sbSequence.append(tablaDTO.getDescripcion()).append(")");
			}

			sbSequence.append(", 9999");
			sbSequence.append(", ").append(user.getUsuarioId());
			sbSequence.append(", GETDATE()");
			sbSequence.append(", GETDATE()");
			sbSequence.append(", '").append(user.getTerminal()).append("'");
			sbSequence.append(", ").append(DateUtil.getAnioActual()).append(")");

			String sqlSequence = sbSequence.toString();

			try {
				PreparedStatement pst = connect().prepareStatement(sqlSequence);

				pst.execute();

			} catch (SQLException e) {

				throw new SisatException(e.getMessage(), e.getCause());
			}
		}
	}

	/**
	 * Metodo para la obtencion de id de una tabla maestra mediante un secuenciador
	 * registrado en la tabla gn_correlativo si no esta registrada esa tabla en la
	 * tabla gn_correlativo, se crea un nuevo registro con un valor incial 0, valor
	 * final 9999 y valor actual de 1
	 * 
	 * @param tablaDTO Contiene los datos de la tabla
	 * @return id siguiente de la secuencia
	 * @throws SisatException
	 */
	private int getIdSequence(TablaDTO tablaDTO, String campoPK, UserSession user) throws SisatException {
		// llamada al secuenciador
		int i_sequence = this.ObtenerCorrelativoTabla(tablaDTO.getDescripcion(), 1);

		if (i_sequence == 0) {
			// no existe el secuenciador asociado a la tabla
			// creando el secuenciador

			StringBuilder sbSequence = new StringBuilder();
			sbSequence.append("INSERT INTO ");
			sbSequence.append(SATParameterFactory.getDBNameScheme().concat(".gn_correlativo"));
			sbSequence.append("(correlativo_id ");
			sbSequence.append(", tabla");
			sbSequence.append(", estado");
			sbSequence.append(", valor_inicial");
			sbSequence.append(", valor_actual");
			sbSequence.append(", valor_final");
			sbSequence.append(", usuario_id");
			sbSequence.append(", fecha_registro");
			sbSequence.append(", fecha_actualizacion");
			sbSequence.append(", terminal");
			sbSequence.append(", anno) ");

			sbSequence.append("VALUES ");
			sbSequence.append(" ((SELECT max(correlativo_id)+1  FROM ")
					.append(SATParameterFactory.getDBNameScheme().concat(".gn_correlativo)"));

			sbSequence.append(", '").append(tablaDTO.getDescripcion()).append("'");

			sbSequence.append(", 1");
			sbSequence.append(", 1");

			// verificando si la tabla a registar el secuenciador tiene
			// registros.
			String sqlCountReg = "SELECT COUNT (*) FROM ".concat(SATParameterFactory.getDBNameScheme().concat("."))
					.concat(tablaDTO.getDescripcion());

			int index = 0;
			try {
				PreparedStatement pst = connect().prepareStatement(sqlCountReg);

				ResultSet rs = pst.executeQuery();

				while (rs.next()) {
					index = rs.getInt(1);
				}

			} catch (SQLException e) {

				throw new SisatException(e.getMessage(), e.getCause());
			}

			// fin de la verificacion de los registros

			if (index == 0) {
				sbSequence.append(", 0 ");
			} else {
				sbSequence.append(", (SELECT max(").append(campoPK).append(") FROM ")
						.append(SATParameterFactory.getDBNameScheme().concat("."));
				sbSequence.append(tablaDTO.getDescripcion()).append(")");
			}

			sbSequence.append(", 9999");
			sbSequence.append(", ").append(user.getUsuarioId());
			sbSequence.append(", GETDATE()");
			sbSequence.append(", GETDATE()");
			sbSequence.append(", '").append(user.getTerminal()).append("'");
			sbSequence.append(", ").append(DateUtil.getAnioActual()).append(")");

			String sqlSequence = sbSequence.toString();

			try {
				PreparedStatement pst = connect().prepareStatement(sqlSequence);

				pst.execute();

			} catch (SQLException e) {

				throw new SisatException(e.getMessage(), e.getCause());
			}

			i_sequence = this.ObtenerCorrelativoTabla(tablaDTO.getDescripcion(), 1);
		}

		return i_sequence;
	}

	/**
	 * Método para la obtención de los nombres de los campos que son PK
	 * 
	 * @param tablaDTO Contiene la información de la tabla
	 * @return
	 * @throws SisatException
	 */
	private String getColumnPK(TablaDTO tablaDTO) throws SisatException {

		String columnsPK = null;

		String sqlmascara = (new StringBuilder()).append("SELECT  nombre_campo ").append("FROM ")
				.append(SATParameterFactory.getDBNameScheme().concat(".")).append("mt_campo WHERE ")
				.append("mt_campo.tabla_id = ").append(tablaDTO.getCodTabla()).append(" AND mt_campo.modulo_id = ")
				.append(tablaDTO.getCodModulo()).append("AND tabla_id = ").append(tablaDTO.getCodTabla()).append(" ")
				.append("AND tipo_campo_id = 1") // 1 debido a que el campo de tipo 1 es PRIMARY KEY
				.toString();

		PreparedStatement pstMascara;

		try {
			pstMascara = connect().prepareStatement(sqlmascara);
			ResultSet rs = pstMascara.executeQuery();
			while (rs.next()) {
				columnsPK = rs.getString(1);
				break;
			}

		} catch (SQLException e) {

			throw new SisatException(e.getMessage(), e.getCause());
		}

		return columnsPK;
	}

	private CampoDTO getCampoPK(TablaDTO tablaDTO) throws SisatException {

		StringBuilder sbQuery = (new StringBuilder());
		sbQuery.append("SELECT ");
		sbQuery.append("tipo_campo_id, "); // --1
		sbQuery.append("nombre_campo, "); // --2
		sbQuery.append("alias, "); // --3
		sbQuery.append("nombre_tabla_padre, "); // --4
		sbQuery.append("mascara, "); // --5
		sbQuery.append("filtro_datos_padre, "); // --6
		sbQuery.append("descripcion "); // --7
		sbQuery.append("FROM ");
		sbQuery.append(SATParameterFactory.getDBNameScheme().concat("."));
		sbQuery.append("mt_campo WHERE ");
		sbQuery.append("mt_campo.tabla_id = ");
		sbQuery.append(tablaDTO.getCodTabla());
		sbQuery.append(" AND modulo_id = ");
		sbQuery.append(tablaDTO.getCodModulo());
		sbQuery.append(" AND tabla_id = ");
		sbQuery.append(tablaDTO.getCodTabla());
		sbQuery.append(" AND tipo_campo_id = 1"); // 1 debido a que el campo de tipo 1 es PRIMARY KEY

		String sqlQuery = sbQuery.toString();

		PreparedStatement pstMascara;

		CampoDTO campoDTO = null;
		try {
			pstMascara = connect().prepareStatement(sqlQuery);
			ResultSet rs = pstMascara.executeQuery();

			while (rs.next()) {
				campoDTO = new CampoDTO();
				campoDTO.setTipoCampoId(rs.getInt(1));
				campoDTO.setNombreCampo(rs.getString(2));
				campoDTO.setAlias(rs.getString(3));
				campoDTO.setNombreTablaPadre(rs.getString(4));
				campoDTO.setMascara(rs.getString(5));
				campoDTO.setFiltroDatosPadre(rs.getString(6));
				campoDTO.setDescripcion(rs.getString(7));

				break;
			}

		} catch (SQLException e) {

			throw new SisatException(e.getMessage(), e.getCause());
		}

		if (campoDTO == null) {
			throw new SisatException("La tabla no contiene una PK");
		}

		return campoDTO;
	}

	/**
	 * Método para la obtención de datos de las claves foráneas para una tabla (FK)
	 * 
	 * @param campoDTO Contiene la información del campo es es FK
	 * @return
	 * @throws SisatException
	 */
	public Map<String, String> getDataComboBoxCampo(CampoDTO campoDTO) throws SisatException {

		Map<String, String> dataComboBox = new LinkedHashMap<String, String>();

		String sql = (new StringBuilder()).append("SELECT ").append(campoDTO.getMascara()).append(", ")
				.append(campoDTO.getFiltroDatosPadre()).append(" ").append("FROM ")
				.append(SATParameterFactory.getDBNameScheme().concat(".")).append(campoDTO.getNombreTablaPadre())
				.toString();

		String[] tokens = campoDTO.getMascara().split(",");

		int nTokens = tokens.length;

		PreparedStatement pst;

		try {
			pst = connect().prepareStatement(sql);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {

				int k = 1;
				String t = null;

				for (; k <= nTokens; k++) {
					if (t == null) {
						t = rs.getString(k);
					} else {
						t = t.concat(", ").concat(rs.getString(k));
					}
				}

				dataComboBox.put(rs.getString(k), t);

			}

		} catch (SQLException e) {
			throw new SisatException(e.getMessage(), e.getCause());
		}

		return dataComboBox;
	}

}
