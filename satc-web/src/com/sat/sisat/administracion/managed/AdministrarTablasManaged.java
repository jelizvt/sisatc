package com.sat.sisat.administracion.managed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.richfaces.component.html.HtmlComboBox;
import org.richfaces.model.selection.SimpleSelection;

import com.sat.sisat.administracion.business.AdministracionBoRemote;
import com.sat.sisat.administracion.dto.CampoDTO;
import com.sat.sisat.administracion.dto.FiltroColunmaDTO;
import com.sat.sisat.administracion.dto.ModuloDTO;
import com.sat.sisat.administracion.dto.RowLstDataDTO;
import com.sat.sisat.administracion.dto.TablaDTO;
import com.sat.sisat.common.business.GeneralBoRemote;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.exception.SisatException;

/**
 * @author Miguel Macias
 * @version 0.1
 * @since 31/07/2011 La clase AdministracionBoRemote.java ha sido creada con el
 *        proposito de administrar los servicios para mostrar, editar y crear
 *        nuevos registros en las tablas maestras.
 */
@ManagedBean
@ViewScoped
public class AdministrarTablasManaged extends BaseManaged {

	private static final long serialVersionUID = 6634700805086359618L;

	@EJB
	AdministracionBoRemote administracionBo;

	@EJB
	GeneralBoRemote generalBo;

	private List<SelectItem> lstModulos = new ArrayList<SelectItem>();

	private List<SelectItem> lstTablas = new ArrayList<SelectItem>();

	private String cmbValueTabla;

	private List<String> lstColumnasSelectTable = new ArrayList<String>();
	
	private List<CampoDTO> lstColumnasSelectTable1 = new ArrayList<CampoDTO>();
	
	private List<String> listColumnasFiltroSelect = new ArrayList<String>();
	
	/**
	 * Variable que contiene la lista de columnas de la tabla seleccionada que seran mostradas en la grilla
	 */
	private List<CampoDTO> listColumnasSelect = new ArrayList<CampoDTO>();
	
	private List<CampoDTO> listCmbFiltroSelect = new ArrayList<CampoDTO>();

	private List<String> lstColumnas = new ArrayList<String>();

	private List<RowLstDataDTO> lstData = new ArrayList<RowLstDataDTO>();

	private List<CampoDTO> lstFieldEditablesTable = new ArrayList<CampoDTO>();

	private HtmlComboBox cmbtabla;

	private HashMap<String, TablaDTO> mapTablaDTO = new HashMap<String, TablaDTO>();

	private HashMap<String, ModuloDTO> mapModulosDTO = new HashMap<String, ModuloDTO>();

	private int currentRow;

	private RowLstDataDTO rowLstDataDTO;

	private boolean flagEdicion = false;

	private TablaDTO tablaDTO;

	private boolean flagMostarButtonNuevo = false;

	private boolean flagMostarButtonDuplicar = false;

	private SimpleSelection lstSelectionData;

	private int cantRegistrosDuplicar = 0;

	private ModuloDTO moduloDTO;
	
	private List<FiltroColunmaDTO> listFiltroColunmaDTOs = new ArrayList<FiltroColunmaDTO>();
	
	private FiltroColunmaDTO filtroSeleccionado = new FiltroColunmaDTO();	
	
	private HashMap<String, CampoDTO > mapColumna = new HashMap<String, CampoDTO>();

	public AdministrarTablasManaged() {

	}

	@PostConstruct
	public void init() {
		List<ModuloDTO> lst = null;
		try {
			lst = administracionBo.getLstModulos();
		} catch (SisatException e) {

			WebMessages.messageError(e.getMessage());
		}

		if(lst.size() > 0){
			
			ModuloDTO moduloDTO_0 = new ModuloDTO();
			moduloDTO_0.setModuloId(0);
			moduloDTO_0.setDescripcion("Todos");
			lstModulos.add(new SelectItem(moduloDTO_0.getDescripcion(), Integer.toString(moduloDTO_0.getModuloId())));
			mapModulosDTO.put(moduloDTO_0.getDescripcion(), moduloDTO_0);
		}		
		
		for (ModuloDTO moduloDTO : lst) {
			lstModulos.add(new SelectItem(moduloDTO.getDescripcion(), Integer.toString(moduloDTO.getModuloId())));
			mapModulosDTO.put(moduloDTO.getDescripcion(), moduloDTO);
		}

	}

	public void listenerChangeComboBoxModulo(ValueChangeEvent event) {
		debug("updateComboBoxTablas - inicio");

		String cmbValueSelect = (String) event.getNewValue();
		moduloDTO = mapModulosDTO.get(cmbValueSelect);
		
		if(moduloDTO == null){
			WebMessages.messageError("El módulo ingresado no existe");
			return;
		}

		List<TablaDTO> lst = null;
		try {
			lst = administracionBo.getLstTabla(moduloDTO.getModuloId());
			listCmbFiltroSelect.clear();
			listColumnasSelect.clear();
			lstData.clear();
			
			flagMostarButtonNuevo = false;
			tablaDTO = null;
			
		} catch (SisatException e) {

			WebMessages.messageError(e.getMessage());
		}

		lstTablas.clear();
		mapTablaDTO.clear();

		for (TablaDTO tablaDTO : lst) {
			lstTablas.add(new SelectItem(tablaDTO.getDescripcionCorta(), Integer.toString(tablaDTO.getCodTabla())));
			mapTablaDTO.put(tablaDTO.getDescripcionCorta(), tablaDTO);
		}

		cmbValueTabla = "";

		debug("updateComboBoxTablas - fin");
	}

	/**
	 * Método que actualiza la grilla de datos segun la tabla seleccionada en el
	 * combobox
	 * 
	 * @param event
	 *            Contiene el valor actual(tabla) seleccionada
	 */
	public void listenerChangeTabla(ValueChangeEvent event) {

		debug("listenerChangeTabla - inicio");		

		String cmbValueSelect = (String) event.getNewValue();

		tablaDTO = mapTablaDTO.get(cmbValueSelect);		
		
		if(tablaDTO == null){
			
			flagMostarButtonNuevo = false;
			lstColumnasSelectTable1.clear();
			listColumnasSelect.clear();
			listCmbFiltroSelect.clear();
			lstData.clear();
			
			WebMessages.messageError("La tabla ingresada no existe");
			return;
		}
		try {
			
			flagMostarButtonNuevo = true;
			
			// reiniciando el filtro
			listFiltroColunmaDTOs.clear();
			
			// cargando cabecera grilla
			lstColumnas.clear();
			lstColumnas = administracionBo.getLstLabelsHeadersTable(tablaDTO);

			lstColumnasSelectTable.clear();
			
			//obteniendo las columnas para ser mostradas en la grilla
			lstColumnasSelectTable  = administracionBo.getLstParamsSelectTable(tablaDTO);
			
			lstColumnasSelectTable1.clear();
			lstColumnasSelectTable1 = administracionBo.getLstParamsSelectTable1(tablaDTO);
			
			
			loadMapColumna(lstColumnasSelectTable1);
			
			// limpiamos el fitro de seleccion de columnas
			listColumnasFiltroSelect.clear();
			
			//actualizamos el filtro de columas
			listColumnasFiltroSelect.addAll(lstColumnasSelectTable);
			
			//actualizando las columnas de las tabla para ser mostrada
			listColumnasSelect.clear();
			listColumnasSelect.addAll(lstColumnasSelectTable1);
			
			//agregando las columnas para filtrar contenido
			listCmbFiltroSelect.clear();
			listCmbFiltroSelect.addAll(lstColumnasSelectTable1);

			// cargando data grilla
			lstData.clear();
			lstData = administracionBo.getGridOfTabla(lstColumnasSelectTable, tablaDTO, listFiltroColunmaDTOs);
			
			lstFieldEditablesTable.clear();			
			lstFieldEditablesTable = administracionBo.getFieldsEditables(tablaDTO);
			
			

		} catch (SisatException e) {

			WebMessages.messageError(e.getMessage());
		}

		debug("listenerChangeTabla - fin");
	}

	/**
	 * Metodo para el guardado de una entrada en la tabla actual
	 */
	public void saveEntryTable() {
		debug("saveEntryTable - inicio");		

		setLstData(null);
		setLstData(new ArrayList<RowLstDataDTO>());

		// verificacion q al menos un campo este llenado
		boolean breakSave = true;
		for (CampoDTO campoDTO : lstFieldEditablesTable) {
			if (campoDTO.getValorCampoUpdate() != null && !campoDTO.getNombreCampo().equals("estado")) {
				breakSave = false;
			}
		}

		if (breakSave != true) {

			try {

				// guardando nuevo registro de la tabla
				administracionBo.saveEntryTable(lstFieldEditablesTable, tablaDTO);

				// actualizando la grilla de datos

				// cargando cabecera grilla
				setLstColumnasSelectTable(administracionBo.getLstParamsSelectTable(tablaDTO));

				// cargando data grilla
				setLstData(administracionBo.getGridOfTabla(lstColumnasSelectTable, tablaDTO, listFiltroColunmaDTOs));

				// reiniciando los campos editables para un registro/edicion
				setLstFieldEditablesTable(administracionBo.getFieldsEditables(tablaDTO));

			} catch (SisatException e) {

				// e.printStackTrace();
				WebMessages.messageError(e.getMessage());

			}
		}

		flagEdicion = false;

		debug("saveEntryTable - fin");
	}

	/**
	 * Metodo que carga los datos en el formulario para poder realizar la
	 * edicion de un registro en la tabla actual
	 */
	public void seleccionarItem() {
		debug("seleccionarItem - inicio");

		try {
			lstFieldEditablesTable = administracionBo.getUpdateFieldsEditables(tablaDTO, rowLstDataDTO,
					lstFieldEditablesTable);
		} catch (SisatException e) {

			WebMessages.messageError(e.getMessage());
		}

		debug("seleccionarItem - fin");
	}

	public void clearEntryTable() {
		debug("clearEntryTable - inicio");

		try {
			setLstFieldEditablesTable(administracionBo.getFieldsEditables(tablaDTO));
		} catch (SisatException e) {

			WebMessages.messageError(e.getMessage());
		}

		flagEdicion = false;
		debug("clearEntryTable - fin");
	}

	public void updateEntryTable() {
		debug("updateEntryTable - inicio");

		setLstData(null);
		setLstData(new ArrayList<RowLstDataDTO>());

		try {

			// actualizando un registro de la tabla

			administracionBo.updateEntryTable(lstFieldEditablesTable, tablaDTO, rowLstDataDTO);

			// actualizando la grilla de datos

			// cargando data grilla
			setLstData(administracionBo.getGridOfTabla(lstColumnasSelectTable, tablaDTO, listFiltroColunmaDTOs));

			// reiniciando los campos editables para un registro/edicion
			setLstFieldEditablesTable(administracionBo.getFieldsEditables(tablaDTO));

		} catch (SisatException e) {

			WebMessages.messageError(e.getMessage());
		}

		flagEdicion = false;

		debug("updateEntryTable - fin");
	}

	public void duplicar() {
		debug("duplicar - inicio");

		List<Integer> lstKeyRows = new ArrayList<Integer>();

		Iterator<Object> iterator = this.lstSelectionData.getKeys();
		while (iterator.hasNext()) {
			Integer key = (Integer) iterator.next();
			RowLstDataDTO row = (RowLstDataDTO) this.lstData.get(key);

			lstKeyRows.add(new Integer(row.getKey()));
		}

		try {
			administracionBo.duplicatedEntryTable(lstKeyRows, tablaDTO, lstFieldEditablesTable);

			// actualizando la grilla de datos
			// cargando data grilla
			setLstData(administracionBo.getGridOfTabla(lstColumnasSelectTable, tablaDTO, listFiltroColunmaDTOs));

		} catch (SisatException e) {

			WebMessages.messageError(e.getMessage());
		}

		debug("duplicar - fin");
	}

	public void takeSelection() {

		setFlagMostarButtonDuplicar(true);
		cantRegistrosDuplicar = this.lstSelectionData.size();

	}

	/**
	 * Método que llena los combobox en los cuales se tiene FK 
	 * @param campoDTO
	 * @return
	 */
	public Map<String, String> getMapForId(CampoDTO campoDTO) {

		Map<String, String> dataComboBox = new LinkedHashMap<String, String>();

		try {
			dataComboBox = administracionBo.getDataComboBoxCampo(campoDTO);
		} catch (SisatException e) {

			WebMessages.messageError(e.getMessage());
		}

		return dataComboBox;
	}
	
	
	@SuppressWarnings("unchecked")
	public void listenerChangeValueFiltroColumns(ValueChangeEvent event){
		
		debug("listenerChangeValueFiltroColumns - inicio");		
		
		listColumnasFiltroSelect.clear();				
		listColumnasFiltroSelect = (List<String>)event.getNewValue();	
		
		// cargando data grilla
		try {
			setLstData(administracionBo.getGridOfTabla(listColumnasFiltroSelect, tablaDTO, listFiltroColunmaDTOs));
		} catch (SisatException e) {
			
			WebMessages.messageError(e.getMessage());
		}
		
		debug("listenerChangeValueFiltroColumns - fin");
	}

	
	public void agregarFiltroColumna(){
		debug("agregarFiltroColumna - inicio");
		
		if(getFiltroSeleccionado().getValorFiltro()== null || getFiltroSeleccionado().getValorFiltro().trim().equals("")){
			WebMessages.messageError("Ingrese una cadena para filtrar");
			return;
		}
		
		listFiltroColunmaDTOs.add(getFiltroSeleccionado());		
		listCmbFiltroSelect.remove(getFiltroSeleccionado().getCampoDTO());		
		filtroSeleccionado = new FiltroColunmaDTO();
		
		try {
			lstData = administracionBo.getGridOfTabla(lstColumnasSelectTable, tablaDTO, listFiltroColunmaDTOs);
		} catch (SisatException e) {
			 
			WebMessages.messageError(e.getMessage());
		}
		
		debug("agregarFiltroColumna - fin");
	}
	
	public void quitarFiltroColumna(){
		
		debug("quitarFiltroColumna - inicio");
		
		int index = listColumnasSelect.indexOf(filtroSeleccionado.getCampoDTO());
		listCmbFiltroSelect.add(index, filtroSeleccionado.getCampoDTO());
		
		listFiltroColunmaDTOs.remove(filtroSeleccionado);
		try {
			lstData = administracionBo.getGridOfTabla(lstColumnasSelectTable, tablaDTO, listFiltroColunmaDTOs);
		} catch (SisatException e) {
			filtroSeleccionado = new FiltroColunmaDTO();
			WebMessages.messageError(e.getMessage());
		}		
		
		filtroSeleccionado = new FiltroColunmaDTO();
		debug("quitarFiltroColumna - fin");
	}
	
	
	private void loadMapColumna(List<CampoDTO> listCampoDTOs){
		
		mapColumna.clear();
		
		for(CampoDTO campo:listCampoDTOs){
			mapColumna.put(campo.getAlias(), campo);
		}		
	}
	
	
	public List<SelectItem> getLstTablas() {
		return lstTablas;
	}

	public void setLstTablas(List<SelectItem> lstTablas) {
		this.lstTablas = lstTablas;
	}

	public String getCmbValueTabla() {
		return cmbValueTabla;
	}

	public void setCmbValueTabla(String cmbValueTabla) {
		this.cmbValueTabla = cmbValueTabla;
	}

	public HtmlComboBox getCmbtabla() {
		return cmbtabla;
	}

	public void setCmbtabla(HtmlComboBox cmbtabla) {
		this.cmbtabla = cmbtabla;
	}

	public List<CampoDTO> getLstFieldEditablesTable() {
		return lstFieldEditablesTable;
	}

	public void setLstFieldEditablesTable(List<CampoDTO> lstFieldEditablesTable) {
		this.lstFieldEditablesTable = lstFieldEditablesTable;
	}

	public int getCurrentRow() {
		return currentRow;
	}

	public void setCurrentRow(int currentRow) {
		this.currentRow = currentRow;
	}

	public List<RowLstDataDTO> getLstData() {
		return lstData;
	}

	public void setLstData(List<RowLstDataDTO> lstData) {
		this.lstData = lstData;
	}

	public RowLstDataDTO getRowLstDataDTO() {
		return rowLstDataDTO;
	}

	public void setRowLstDataDTO(RowLstDataDTO rowLstDataDTO) {
		this.rowLstDataDTO = rowLstDataDTO;
	}

	public boolean isFlagEdicion() {
		return flagEdicion;
	}

	public void setFlagEdicion(boolean flagEdicion) {
		this.flagEdicion = flagEdicion;
	}

	public TablaDTO getTablaDTO() {
		return tablaDTO;
	}

	public void setTablaDTO(TablaDTO tablaDTO) {
		this.tablaDTO = tablaDTO;
	}

	public boolean isFlagMostarButtonNuevo() {
		return flagMostarButtonNuevo;
	}

	public SimpleSelection getLstSelectionData() {
		return lstSelectionData;
	}

	public void setLstSelectionData(SimpleSelection lstSelectionData) {
		this.lstSelectionData = lstSelectionData;
	}

	public boolean isFlagMostarButtonDuplicar() {
		return flagMostarButtonDuplicar;
	}

	public void setFlagMostarButtonDuplicar(boolean flagMostarButtonDuplicar) {
		this.flagMostarButtonDuplicar = flagMostarButtonDuplicar;

	}

	public int getCantRegistrosDuplicar() {
		return cantRegistrosDuplicar;
	}

	public void setCantRegistrosDuplicar(int cantRegistrosDuplicar) {
		this.cantRegistrosDuplicar = cantRegistrosDuplicar;
	}

	public List<String> getLstColumnas() {
		return lstColumnas;
	}

	public void setLstColumnas(List<String> lstColumnas) {
		this.lstColumnas = lstColumnas;
	}

	public List<SelectItem> getLstModulos() {
		return lstModulos;
	}

	public void setLstModulos(List<SelectItem> lstModulos) {
		this.lstModulos = lstModulos;
	}

	public ModuloDTO getModuloDTO() {
		return moduloDTO;
	}

	public void setModuloDTO(ModuloDTO moduloDTO) {
		this.moduloDTO = moduloDTO;
	}

	public List<String> getLstColumnasSelectTable() {
		return lstColumnasSelectTable;
	}

	public void setLstColumnasSelectTable(List<String> lstColumnasSelectTable) {
		this.lstColumnasSelectTable = lstColumnasSelectTable;
	}

	public List<String> getListColumnasFiltroSelect() {
		return listColumnasFiltroSelect;
	}

	public void setListColumnasFiltroSelect(List<String> listColumnasFiltroSelect) {
		this.listColumnasFiltroSelect = listColumnasFiltroSelect;
	}

	public List<CampoDTO> getLstColumnasSelectTable1() {
		return lstColumnasSelectTable1;
	}

	public void setLstColumnasSelectTable1(List<CampoDTO> lstColumnasSelectTable1) {
		this.lstColumnasSelectTable1 = lstColumnasSelectTable1;
	}

	public List<FiltroColunmaDTO> getListFiltroColunmaDTOs() {
		return listFiltroColunmaDTOs;
	}

	public void setListFiltroColunmaDTOs(List<FiltroColunmaDTO> listFiltroColunmaDTOs) {
		this.listFiltroColunmaDTOs = listFiltroColunmaDTOs;
	}

	public FiltroColunmaDTO getFiltroSeleccionado() {
		
		if(filtroSeleccionado == null){
			filtroSeleccionado = new FiltroColunmaDTO();
		}
		
		
		return filtroSeleccionado;
	}

	public void setFiltroSeleccionado(FiltroColunmaDTO filtroSeleccionado) {
		this.filtroSeleccionado = filtroSeleccionado;
	}

	public HashMap<String, CampoDTO > getMapColumna() {
		return mapColumna;
	}

	public void setMapColumna(HashMap<String, CampoDTO > mapColumna) {
		this.mapColumna = mapColumna;
	}	

	public List<CampoDTO> getListCmbFiltroSelect() {
		return listCmbFiltroSelect;
	}

	public void setListCmbFiltroSelect(List<CampoDTO> listCmbFiltroSelect) {
		this.listCmbFiltroSelect = listCmbFiltroSelect;
	}

	public List<CampoDTO> getListColumnasSelect() {
		return listColumnasSelect;
	}

	public void setListColumnasSelect(List<CampoDTO> listColumnasSelect) {
		this.listColumnasSelect = listColumnasSelect;
	}

}
