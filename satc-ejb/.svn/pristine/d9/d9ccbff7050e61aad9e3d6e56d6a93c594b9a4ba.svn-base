package com.sat.sisat.administracion.business;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.sat.sisat.administracion.dto.CampoDTO;
import com.sat.sisat.administracion.dto.ModuloDTO;
import com.sat.sisat.administracion.dto.RowLstDataDTO;
import com.sat.sisat.administracion.dto.TablaDTO;
import com.sat.sisat.exception.SisatException;

/**
 * @author Miguel Macias 
 * @version 0.1
 * @since 31/07/2012
 * La clase AdministracionBoRemote.java ha sido creada con el proposito de administrar los servicios para mostrar, 
 * editar y crear nuevos registros en las tablas maestras.
*/

@Local
public interface AdministracionBoLocal {
	
	/**
	 * Método que obtiene el listado de los modulos
	 * @return
	 * @throws SisatException
	 */
	public List<ModuloDTO> getLstModulos() throws SisatException;
	
	/**
	 * Método que obtiene el listado de tablas maestras
	 * @return
	 * @throws SisatException
	 */
	public List<TablaDTO> getLstTabla(int moduloId) throws SisatException;	
	
	/**
	 * Obtiene los campos de una tabla maestra que pueden ser mostrados
	 * @param tablaDTO Contiene la informacion relacionada con la tabla maestra
	 * @return
	 * @throws SisatException
	 */
	public List<String> getLstParamsSelectTable(TablaDTO tablaDTO) throws SisatException;
	
	public List<CampoDTO> getLstParamsSelectTable1(TablaDTO tablaDTO) throws SisatException;
	
	/**
	 * Método que obtiene las etiquetas de las columnas para una tabla maestra
	 * @param tablaDTO Contiene la informacion relacionada a la tabla maestra
	 * @return
	 * @throws SisatException
	 */
	public List<String> getLstLabelsHeadersTable(TablaDTO tablaDTO) throws SisatException;

	/**
	 * Método que obtiene la data de una tabla maestra
	 * @param lstParamsSelect Contiene las columnas que pueden ser mostradas de la tabla
	 * @param tablaDTO Contiene la informacion de la tabla maestra
	 * @return
	 * @throws SisatException
	 */
	public List<RowLstDataDTO> getGridOfTabla(List<String> lstParamsSelect, TablaDTO tablaDTO) throws SisatException;

	/**
	 * Método que obtiene el listado de campos editables de un registro de una tabla maestra
	 * @param tablaDTO Contiene los datos de la tabla maestra
	 * @return
	 * @throws SisatException
	 */
	public List<CampoDTO> getFieldsEditables(TablaDTO tablaDTO) throws SisatException;

	/**
	 * Metodo que crea un registro en una tabla
	 * 
	 * @param lstFieldsEditables Lista de campos editables de la tabla
	 * @throws SisatException
	 */
	public void saveEntryTable(List<CampoDTO> lstFieldsEditables, TablaDTO tablaDTO) throws SisatException;

	/**
	 * Metodo que obtiene los campos actualizables de un registro y cargando la
	 * data actual en dichos campos.
	 * 
	 * @param tablaDTO Contiene la informacion de la tabla maestra
	 * @param rowLstDataDTO 
	 * @return
	 * @throws SisatException
	 */
	public List<CampoDTO> getUpdateFieldsEditables(TablaDTO tablaDTO, RowLstDataDTO rowLstDataDTO,
			List<CampoDTO> lstFieldEditablesTable) throws SisatException;

	/**
	 * Metodo que realiza la actualización de un registro
	 * 
	 * @param lstFieldsEditables Contiene los datos actualizados
	 * @param tablaDTO Contiene la informacion sobre la tabla a la que pertenece el registro
	 * @param rowLstDataDTO Contiene el registro actual seleccionado
	 * @throws SisatException
	 */
	public void updateEntryTable(List<CampoDTO> lstFieldsEditables, TablaDTO tablaDTO, RowLstDataDTO rowLstDataDTO)
			throws SisatException;

	/**
	 * Método para realizar el proceso de duplicacion de registros en una tabla maestra
	 * @param lstKeyRows Contiene el listado de id de los registros a duplicarse
	 * @param tablaDTO Contiene la informacion de la tabla maestra
	 * @param lstFieldEditablesTable Contiene informacion sobre los campos que pueden ser duplicados
	 * @throws SisatException
	 */
	public void duplicatedEntryTable(List<Integer> lstKeyRows, TablaDTO tablaDTO, List<CampoDTO> lstFieldEditablesTable)
			throws SisatException;
	
	/**
	 * Método para la obtención de datos de las claves foráneas para una tabla (FK)  
	 * @param campoDTO Contiene la información del campo es es FK
	 * @return
	 * @throws SisatException
	 */
	public Map<String, String> getDataComboBoxCampo(CampoDTO campoDTO) throws SisatException;
	
	
}
