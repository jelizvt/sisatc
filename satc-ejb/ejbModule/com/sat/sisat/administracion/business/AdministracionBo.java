package com.sat.sisat.administracion.business;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

import com.sat.sisat.administracion.dao.AdministracionBusinessDao;
import com.sat.sisat.administracion.dto.CampoDTO;
import com.sat.sisat.administracion.dto.FiltroColunmaDTO;
import com.sat.sisat.administracion.dto.ModuloDTO;
import com.sat.sisat.administracion.dto.RowLstDataDTO;
import com.sat.sisat.administracion.dto.TablaDTO;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.predial.business.BaseBusiness;
import com.sat.sisatc.seguridad.dto.ConsultaReniecDTO;
import com.sat.sisatc.seguridad.dto.ConsultaSunarpDTO;

/**
 * @author Miguel Macias
 * @version 0.1
 * @since 31/07/2012 La clase AdministracionBoRemote.java ha sido creada con el
 *        proposito de administrar los servicios para mostrar, editar y crear
 *        nuevos registros en las tablas maestras.
 */
@Stateless
public class AdministracionBo extends BaseBusiness implements AdministracionBoRemote {

	private static final long serialVersionUID = 3891164156214870507L;

	private AdministracionBusinessDao service;

	public AdministracionBo() {

	}

	@PostConstruct
	public void initialize() {
		this.service = new AdministracionBusinessDao();
		setDataManager(service);
	}

	public AdministracionBusinessDao getService() {
		return this.service;
	}

	@Override
	public List<TablaDTO> getLstTabla(int moduloId) throws SisatException {

		return getService().getLstTabla(moduloId);
	}

	@Override
	public List<ModuloDTO> getLstModulos() throws SisatException {

		return getService().getLstModulos();
	}

	@Override
	public List<RowLstDataDTO> getGridOfTabla(List<String> lstParamsSelect, TablaDTO tablaDTO,
			List<FiltroColunmaDTO> listFiltroColunmaDTOs) throws SisatException {

		return this.service.getGridDataTable1(lstParamsSelect, tablaDTO, listFiltroColunmaDTOs);

	}

	@Override
	public List<String> getLstParamsSelectTable(TablaDTO tablaDTO) throws SisatException {

		List<String> lstParamsSelect = this.service.getLstParamsSelectTable(tablaDTO, "nombre_campo");
		return lstParamsSelect;
	}

	@Override
	public List<String> getLstLabelsHeadersTable(TablaDTO tablaDTO) throws SisatException {

		List<String> lstLabelsHeadersTable = this.service.getLstParamsSelectTable(tablaDTO, "alias");
		return lstLabelsHeadersTable;
	}

	@Override
	public List<CampoDTO> getFieldsEditables(TablaDTO tablaDTO) throws SisatException {

		return this.service.getFieldsEditables(tablaDTO);
	}

	@Override
	public void saveEntryTable(List<CampoDTO> lstFieldsEditables, TablaDTO tablaDTO) throws SisatException {

		this.service.saveEntryTable(lstFieldsEditables, tablaDTO,getUser());

	}

	@Override
	public List<CampoDTO> getUpdateFieldsEditables(TablaDTO tablaDTO, RowLstDataDTO rowLstDataDTO,
			List<CampoDTO> lstFieldEditablesTable) throws SisatException {

		return this.service.getUpdateFieldsEditables(tablaDTO, rowLstDataDTO, lstFieldEditablesTable);

	}

	@Override
	public void updateEntryTable(List<CampoDTO> lstFieldsEditables, TablaDTO tablaDTO, RowLstDataDTO rowLstDataDTO)
			throws SisatException {

		this.service.updateEntryTable(lstFieldsEditables, tablaDTO, rowLstDataDTO);
	}

	@Override
	public void duplicatedEntryTable(List<Integer> lstKeyRows, TablaDTO tablaDTO, List<CampoDTO> lstFieldEditablesTable)
			throws SisatException {
		this.service.duplicatedEntryTable(lstKeyRows, tablaDTO, lstFieldEditablesTable,getUser());
	}

	@Override
	public Map<String, String> getDataComboBoxCampo(CampoDTO campoDTO) throws SisatException {

		return this.service.getDataComboBoxCampo(campoDTO);
	}

	@Override
	public List<CampoDTO> getLstParamsSelectTable1(TablaDTO tablaDTO) throws SisatException {

		return this.service.getFieldsGrid(tablaDTO);

	}

	@Override
	public Boolean registrarConsultaReniec(ConsultaReniecDTO datos) throws Exception {
		// TODO Auto-generated method stub
		return this.service.registrarConsultaReniec(datos);
	}

	@Override
	public Boolean registrarConsultaSunarp(ConsultaSunarpDTO datos) throws Exception {
		// TODO Auto-generated method stub
		return this.service.registrarConsultaSunarp(datos);
	}

}
