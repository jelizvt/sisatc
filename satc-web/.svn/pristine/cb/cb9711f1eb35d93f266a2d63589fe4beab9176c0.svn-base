package com.sat.sisat.tramitedocumentario.managed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.awt.Desktop;
import java.io.*;
import java.io.File;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.persistence.entity.GnTipoDocumento;
import com.sat.sisat.persistence.entity.GnUnidad;
import com.sat.sisat.persistence.entity.TdDataValor;
import com.sat.sisat.persistence.entity.TdDocumentoAnexo;
import com.sat.sisat.persistence.entity.TdDocumentoTramite;
import com.sat.sisat.persistence.entity.TdEstadoExpediente;
import com.sat.sisat.persistence.entity.TdExpediente;
import com.sat.sisat.persistence.entity.TdRepresentante;
import com.sat.sisat.persistence.entity.TdRequisitoExpediente;
import com.sat.sisat.persistence.entity.TdResolucion;
import com.sat.sisat.persistence.entity.TdResultado;
import com.sat.sisat.persistence.entity.TdTipoTramite;
import com.sat.sisat.persistence.entity.TdSituacionExpediente;
import com.sat.sisat.persona.business.PersonaBoRemote;
import com.sat.sisat.predial.dto.UbicacionDTO;
import com.sat.sisat.tramitedocumentario.bussiness.TramiteDocumentarioBoRemote;
import com.sat.sisat.tramitedocumentario.dto.BusquedaExpedienteDTO;
import com.sat.sisat.tramitedocumentario.dto.ItemBandejaEntradaDTO;
import com.sat.sisat.tramitedocumentario.dto.ResolutorDTO;
import com.sat.sisat.usuario.dto.UsuarioDTO;
import com.sat.sisat.vehicular.dto.BuscarPersonaDTO;
import com.sat.sisat.common.managed.SessionManaged;

@ManagedBean
@ViewScoped
public class BandejaEntradaAreaManaged extends BaseManaged {

	private static final long serialVersionUID = -3957530154771801773L;

	private BusquedaExpedienteDTO busquedaExpedienteDTO = new BusquedaExpedienteDTO();

	@EJB
	TramiteDocumentarioBoRemote tramiteDocumentarioBo;

	@EJB
	PersonaBoRemote personaBo;

	private TdExpediente tdExpediente = new TdExpediente();

	private TdRepresentante tdRepresentante = new TdRepresentante();

	private ItemBandejaEntradaDTO itemSelect;

	private int indexSelectItem = -1;

	private List<ItemBandejaEntradaDTO> listItemBandejaEntradaDTOs = new ArrayList<ItemBandejaEntradaDTO>();

	private List<ExpedientesAsignadosDTO> listExpedientesAsignadosDTOs = new ArrayList<ExpedientesAsignadosDTO>();

	private List<TdTipoTramite> listTdTipoTramites;

	private List<TdDocumentoTramite> listTdDocumentoTramites;

	private List<GnUnidad> listGnUnidad;

	//private TdExpediente expedienteSeleccionado;

	private BuscarPersonaDTO contribuyente;

	private HashMap<String, Integer> listProcedimientos;

	private UbicacionDTO ubicacionDTOContribuyente;

	private UbicacionDTO ubicacionDTORepresentante;

	private Boolean mostrarExpediente = Boolean.FALSE;

	private List<TdDataValor> listTdDataValors = new ArrayList<TdDataValor>();

	private List<TdRequisitoExpediente> listTdRequisitoExpedientes = new ArrayList<TdRequisitoExpediente>();
	
	private List<TdEstadoExpediente> listEstadoExpedientes = new ArrayList<TdEstadoExpediente>();

	private List<TdSituacionExpediente> listSituacionExpedientes = new ArrayList<TdSituacionExpediente>(); //Para mostrar situacion de expediente en oficina derivada

	private List<TdSituacionExpediente> listOdSituacionExpediente;
	
	private Integer unidadIdADesignar;

	private TdResolucion resolucion = new TdResolucion();

	private TdResultado resultado = new TdResultado();

	private List<TdDocumentoAnexo> listDocumentoAnexos = new ArrayList<TdDocumentoAnexo>();

	private List<ResolutorDTO> listResolutorDTOs = new ArrayList<ResolutorDTO>();

	private HashMap<Integer, String> hashMapEstadoExpediente;

	private Integer resolutorIdSeleccionado;
	
	private boolean vistaExpedienteSimple = false;

	/** Variables para el control de la tabla de anexos */
	private int indexTblDocumentoAnexo = -1;

	private List<GnTipoDocumento> listGnTipoDocumentos = new ArrayList<GnTipoDocumento>();

	private int nroExpedientesPorGuardar = 0;
	UsuarioDTO usuarioDTO=new UsuarioDTO();

	@PostConstruct
	public void init() {
		
		/**
		 * Inicializacion de componentes, estos seran solo lectura por cuestiones practicas
		 * */
		try {
			//this.busquedaExpedienteDTO.setFechaRecepcion(DateUtil.getCurrentDateOnly()); //ya no se tomara la fecha para cargar expedientes de área

			/**
			 * Seteando los documentos para la unidad deseada, en caso no tenga unidad traera todos
			 * los documentos
			 */
//			this.busquedaExpedienteDTO.setUnidadId(getSessionManaged().getUsuarioLogIn().getUnidadId());
			//this.busquedaExpedienteDTO.setUnidadId(usuarioDTO.getUnidadId());

			this.busquedaExpedienteDTO.setUnidadId(getSessionManaged().getUnidadId());

			//Mostrar expedientes por Unidad y rol
			/*if (getSessionManaged().getRolUnidad() ==1){			
				this.busquedaExpedienteDTO.setSituacionExpediente(Constante.SITUACION_EXPEDIENTE_DIGITALIZADO);
			} else {*/
				this.busquedaExpedienteDTO.setSituacionExpediente(Constante.SITUACION_EXPEDIENTE_DERIVADO);
				this.busquedaExpedienteDTO.setUsuarioExp(getSessionManaged().getUsuarioLogIn().getUsuarioId());
			//}
			
			listItemBandejaEntradaDTOs = tramiteDocumentarioBo.obtenerExpedientes(busquedaExpedienteDTO);

			listTdTipoTramites = tramiteDocumentarioBo.getAllTipoTramites();

			listTdDocumentoTramites = tramiteDocumentarioBo.getAllDocumentoTramites();

			listGnUnidad = tramiteDocumentarioBo.getAllGnUnidad();

			setListProcedimientos(tramiteDocumentarioBo.getAllProcedimientos());

			listGnTipoDocumentos = tramiteDocumentarioBo.obtenerTiposDocumentos();

			listResolutorDTOs = tramiteDocumentarioBo.obtenerResolutores(getSessionManaged().getUnidadId());
			
			//listResolutorDTOs = tramiteDocumentarioBo.obtenerResolutores(getUsuarioDTO().getUnidadId());

			//hashMapEstadoExpediente = tramiteDocumentarioBo.obtenerEstadosExpediente();
			
			listEstadoExpedientes = tramiteDocumentarioBo.obtenerEstadoExpedientes();

			listSituacionExpedientes = tramiteDocumentarioBo.obtenerSituacionExpedientes();
			
			listOdSituacionExpediente = tramiteDocumentarioBo.getOdSituacionExpedientes();

		} catch (SisatException e) {
			e.printStackTrace();
		}

	}

	public String selectItem() {

		setItemSelect(listItemBandejaEntradaDTOs.get(indexSelectItem));

		return "/sisat/principal.xhtml?faces-redirect=true";
	}

	public void verExpediente(Integer expedienteId) {

		try {
			
			vistaExpedienteSimple = false;
			
			tdExpediente = tramiteDocumentarioBo.obtenerExpediente(expedienteId);

			tdRepresentante = tramiteDocumentarioBo.obtenerRepresentante(tdExpediente.getRepresentanteId());

			listTdDataValors = tramiteDocumentarioBo.obtenerDataValorExpediente(expedienteId);

			listTdRequisitoExpedientes = tramiteDocumentarioBo.obtenerRequisitosExpediente(expedienteId);

			listDocumentoAnexos = tramiteDocumentarioBo.obtenerDocumentosAnexos(expedienteId);

			if (tdExpediente.getEstadoExpediente() == Constante.ESTADO_EXPEDIENTE_RESUELTO
					|| tdExpediente.getEstadoExpediente() == Constante.ESTADO_EXPEDIENTE_PEND_APROBACION) {
				resolucion = tramiteDocumentarioBo.obtenerResolucion(tdExpediente.getExpedienteId());
			} else {
				resolucion = new TdResolucion();
			}
			
			if(tdExpediente.getEstadoExpediente() == Constante.ESTADO_EXPEDIENTE_ATENDIDO){
				resultado = tramiteDocumentarioBo.obtenerResultado(expedienteId);
			}else{
				resultado = new TdResultado();
			}
			
			if (tdExpediente.getContribuyenteId() != null) {
				List<BuscarPersonaDTO> lista = personaBo.findTraDocPersona(tdExpediente.getContribuyenteId(),
						null,
						null,
						null,
						null,
						null,
						null,
						null,
						null,
						null,
						null);

				if (lista.size() > 0) {
					contribuyente = lista.get(0);
				}
			}

			mostrarExpediente = Boolean.TRUE;
			
			if(tdExpediente.getProcedimientoId() == 3 ||tdExpediente.getProcedimientoId() == 2 ){
				vistaExpedienteSimple = true;
			}

		} catch (SisatException e) {

			WebMessages.messageError(e.getMessage());

		} catch (Exception e) {

			WebMessages.messageError(e.getMessage());
		}

	}

	public void listenerValueChangeTipoTramite(ValueChangeEvent e) {

		Integer tipoTramiteId = (Integer) e.getNewValue();
		try {
			if (tipoTramiteId != null) {

				listTdDocumentoTramites = tramiteDocumentarioBo.getAllDocumentoTramitesByTipoTramite(tipoTramiteId);

			}
		} catch (SisatException e1) {
			WebMessages.messageError(e1.getMessage());
		}
	}

	public void filtar() {

		try {
			listItemBandejaEntradaDTOs = tramiteDocumentarioBo.obtenerExpedientes(busquedaExpedienteDTO);

			/** Reinicio de componentes */
			this.tdExpediente = new TdExpediente();
			this.unidadIdADesignar = null;
			this.mostrarExpediente = Boolean.FALSE;

		} catch (SisatException e) {
			WebMessages.messageError(e.getMessage());
		}

	}

	public void limpiar() {

		this.busquedaExpedienteDTO = new BusquedaExpedienteDTO();
		/**
		 * Seteando los documentos para la unidad deseada, en caso no tenga unidad traera todos los
		 * documentos
		 */
//		this.busquedaExpedienteDTO.setUnidadId(getSessionManaged().getUsuarioLogIn().getUnidadId());
//		this.busquedaExpedienteDTO.setUnidadId(getUsuarioDTO().getUnidadId());
		this.busquedaExpedienteDTO.setUnidadId(getSessionManaged().getUnidadId());

		/** Reinicio de componentes */
		this.tdExpediente = new TdExpediente();
		this.unidadIdADesignar = null;
		this.mostrarExpediente = Boolean.FALSE;
	}

	public void reasignarExpediente() {

		try {
			tramiteDocumentarioBo.reasignarExpediente(tdExpediente, unidadIdADesignar);

			listItemBandejaEntradaDTOs = tramiteDocumentarioBo.obtenerExpedientes(busquedaExpedienteDTO);

			this.tdExpediente = new TdExpediente();
			this.unidadIdADesignar = null;
			this.mostrarExpediente = Boolean.FALSE;

			WebMessages.messageInfo("Derivacion realizada correctamente.");
		} catch (SisatException e) {
			WebMessages.messageError(e.getMessage());
		}
	}

	public void guardarResolucion() {

		try {

			/**
			 * Agregando el campo unidadId del usuario actual, mas adelante se llenaran los campos
			 * de auditoria
			 */
//			this.resolucion.setUnidadId(getSessionManaged().getUsuarioLogIn().getUnidadId());
			this.resolucion.setUnidadId(getUsuarioDTO().getUnidadId());
			this.resolucion.setFlagProyectoResolucion(Boolean.FALSE);

			tramiteDocumentarioBo.guardarResolucion(tdExpediente, resolucion);
			listItemBandejaEntradaDTOs = tramiteDocumentarioBo.obtenerExpedientes(busquedaExpedienteDTO);

			this.tdExpediente = new TdExpediente();
			this.unidadIdADesignar = null;
			this.mostrarExpediente = Boolean.FALSE;

			WebMessages.messageInfo("Resolución guardada satisfactoriamente.");
		} catch (SisatException e) {
			WebMessages.messageError(e.getMessage());
		}
	}

	public void guardarResultado() {

		try {

			/**
			 * Agregando el campo unidadId del usuario actual, mas adelante se llenaran los campos
			 * de auditoria
			 */
//			this.resultado.setUnidadId(getSessionManaged().getUsuarioLogIn().getUnidadId());
			this.resultado.setUnidadId(getUsuarioDTO().getUnidadId());
		
			tramiteDocumentarioBo.guardarResultado(tdExpediente, this.resultado);
			listItemBandejaEntradaDTOs = tramiteDocumentarioBo.obtenerExpedientes(busquedaExpedienteDTO);

			this.tdExpediente = new TdExpediente();
			this.unidadIdADesignar = null;
			this.mostrarExpediente = Boolean.FALSE;

			WebMessages.messageInfo("Resolución guardada satisfactoriamente.");
		} catch (SisatException e) {
			WebMessages.messageError(e.getMessage());
		}
	}

	public void guardarProyectoResolucion() {

		try {

			/**
			 * Agregando el campo unidadId del usuario actual, mas adelante se llenaran los campos
			 * de auditoria
			 */
//			this.resolucion.setUnidadId(getSessionManaged().getUsuarioLogIn().getUnidadId());
			//this.resolucion.setUnidadId(getUsuarioDTO().getUnidadId());
			this.resolucion.setUnidadId(getSessionManaged().getRolUnidad());
		
			/**
			 * El campo flagproyectoresolucion por defecto todas las resoluciones son proyectos,
			 * salvo que sea resuelta directamente por en encargado del area son directamente
			 * resolucion definitivas
			 */

			tramiteDocumentarioBo.guardarProyectoResolucion(tdExpediente, resolucion);
			listItemBandejaEntradaDTOs = tramiteDocumentarioBo.obtenerExpedientes(busquedaExpedienteDTO);

			this.tdExpediente = new TdExpediente();
			this.unidadIdADesignar = null;
			this.mostrarExpediente = Boolean.FALSE;

			WebMessages.messageInfo("Resolución guardada satisfactoriamente.");
		} catch (SisatException e) {
			WebMessages.messageError(e.getMessage());
		}
	}

	public void guardarDocumentosAnexos() {

		try {
			tramiteDocumentarioBo.guardarDocumentosAnexos(this.tdExpediente, this.listDocumentoAnexos);

			/** Volviendo a consultar los expedientes */
			listDocumentoAnexos = tramiteDocumentarioBo.obtenerDocumentosAnexos(this.tdExpediente.getExpedienteId());
			this.nroExpedientesPorGuardar = 0;
			WebMessages.messageInfo("Se han agregado satisfactoriamente los documentos anexos.");
		} catch (SisatException e) {
			WebMessages.messageError(e.getMessage());
		}

	}

	public void changeRowItemDocumentoAnexo() {
		listDocumentoAnexos.get(this.indexTblDocumentoAnexo).setEnEdicion(true);
	}

	public void quitRowItemDocumentoAnexo() {
		listDocumentoAnexos.remove(this.indexTblDocumentoAnexo);

		this.nroExpedientesPorGuardar = this.nroExpedientesPorGuardar - 1;
	}

	public void addRowDataDocumentoAnexo() {

		TdDocumentoAnexo tdDocumentoAnexo = new TdDocumentoAnexo();

		tdDocumentoAnexo.setPendienteGuardado(Boolean.TRUE);
		listDocumentoAnexos.add(tdDocumentoAnexo);
		this.nroExpedientesPorGuardar = this.nroExpedientesPorGuardar + 1;

	}

	public void addItemDocumentoAnexo() {

		TdDocumentoAnexo _tdDocumentoAnexo = listDocumentoAnexos.get(indexTblDocumentoAnexo);
		_tdDocumentoAnexo.setEnEdicion(false);
		_tdDocumentoAnexo.setFechaDocumento(DateUtil.getCurrentDateOnly());
//		_tdDocumentoAnexo.setUnidadId(getSessionManaged().getUsuarioLogIn().getUnidadId());
		_tdDocumentoAnexo.setUnidadId(getSessionManaged().getUnidadId());
		_tdDocumentoAnexo.setUnidadId(getUsuarioDTO().getUnidadId());
		
	}

	public void asignarExpediente() {

		try {
			tramiteDocumentarioBo.asignarExpedienteAResolutor(this.tdExpediente, this.resolutorIdSeleccionado);

			WebMessages.messageInfo("Asignacion realizada correctamente.");

			/** Reiniciando componentes y aplicando cambios */
			listItemBandejaEntradaDTOs = tramiteDocumentarioBo.obtenerExpedientes(busquedaExpedienteDTO); 
			
			this.tdExpediente = new TdExpediente();
			this.unidadIdADesignar = null;
			this.mostrarExpediente = Boolean.FALSE;

		} catch (SisatException e) {
			WebMessages.messageError(e.getMessage());
		}

	}

	public List<ItemBandejaEntradaDTO> getListItemBandejaEntradaDTOs() {
		return listItemBandejaEntradaDTOs;
	}

	public void setListItemBandejaEntradaDTOs(List<ItemBandejaEntradaDTO> listItemBandejaEntradaDTOs) {
		this.listItemBandejaEntradaDTOs = listItemBandejaEntradaDTOs;
	}

	public int getIndexSelectItem() {
		return indexSelectItem;
	}

	public void setIndexSelectItem(int indexSelectItem) {
		this.indexSelectItem = indexSelectItem;
	}

	public ItemBandejaEntradaDTO getItemSelect() {
		return itemSelect;
	}

	public void setItemSelect(ItemBandejaEntradaDTO itemSelect) {
		this.itemSelect = itemSelect;
	}

	public List<ExpedientesAsignadosDTO> getListExpedientesAsignadosDTOs() {
		return listExpedientesAsignadosDTOs;
	}

	public void setListExpedientesAsignadosDTOs(List<ExpedientesAsignadosDTO> listExpedientesAsignadosDTOs) {
		this.listExpedientesAsignadosDTOs = listExpedientesAsignadosDTOs;
	}

	public List<TdTipoTramite> getListTdTipoTramites() {
		return listTdTipoTramites;
	}

	public void setListTdTipoTramites(List<TdTipoTramite> listTdTipoTramites) {
		this.listTdTipoTramites = listTdTipoTramites;
	}

	public List<TdDocumentoTramite> getListTdDocumentoTramites() {
		return listTdDocumentoTramites;
	}

	public void setListTdDocumentoTramites(List<TdDocumentoTramite> listTdDocumentoTramites) {
		this.listTdDocumentoTramites = listTdDocumentoTramites;
	}

	public List<GnUnidad> getListGnUnidad() {
		return listGnUnidad;
	}

	public UsuarioDTO getUsuarioDTO() {
		return usuarioDTO;
	}

	public void setUsuarioDTO(UsuarioDTO usuarioDTO) {
		this.usuarioDTO = usuarioDTO;
	}

	public void setListGnUnidad(List<GnUnidad> listGnUnidad) {
		this.listGnUnidad = listGnUnidad;
	}

//	public TdExpediente getExpedienteSeleccionado() {
//		return expedienteSeleccionado;
//	}
//
//	public void setExpedienteSeleccionado(TdExpediente expedienteSeleccionado) {
//		this.expedienteSeleccionado = expedienteSeleccionado;
//	}

	public BuscarPersonaDTO getContribuyente() {
		return contribuyente;
	}

	public void setContribuyente(BuscarPersonaDTO contribuyente) {
		this.contribuyente = contribuyente;
	}

	public UbicacionDTO getUbicacionDTOContribuyente() {
		return ubicacionDTOContribuyente;
	}

	public void setUbicacionDTOContribuyente(UbicacionDTO ubicacionDTOContribuyente) {
		this.ubicacionDTOContribuyente = ubicacionDTOContribuyente;
	}

	public UbicacionDTO getUbicacionDTORepresentante() {
		return ubicacionDTORepresentante;
	}

	public void setUbicacionDTORepresentante(UbicacionDTO ubicacionDTORepresentante) {
		this.ubicacionDTORepresentante = ubicacionDTORepresentante;
	}

	public TdExpediente getTdExpediente() {
		return tdExpediente;
	}

	public void setTdExpediente(TdExpediente tdExpediente) {
		this.tdExpediente = tdExpediente;
	}

	public Boolean getMostrarExpediente() {
		return mostrarExpediente;
	}

	public void setMostrarExpediente(Boolean mostrarExpediente) {
		this.mostrarExpediente = mostrarExpediente;
	}

	public List<TdDataValor> getListTdDataValors() {
		return listTdDataValors;
	}

	public void setListTdDataValors(List<TdDataValor> listTdDataValors) {
		this.listTdDataValors = listTdDataValors;
	}

	public List<TdRequisitoExpediente> getListTdRequisitoExpedientes() {
		return listTdRequisitoExpedientes;
	}

	public void setListTdRequisitoExpedientes(List<TdRequisitoExpediente> listTdRequisitoExpedientes) {
		this.listTdRequisitoExpedientes = listTdRequisitoExpedientes;
	}

	public BusquedaExpedienteDTO getBusquedaExpedienteDTO() {
		return busquedaExpedienteDTO;
	}

	public void setBusquedaExpedienteDTO(BusquedaExpedienteDTO busquedaExpedienteDTO) {
		this.busquedaExpedienteDTO = busquedaExpedienteDTO;
	}

	public Integer getUnidadIdADesignar() {
		return unidadIdADesignar;
	}

	public void setUnidadIdADesignar(Integer unidadIdADesignar) {
		this.unidadIdADesignar = unidadIdADesignar;
	}

	public TdResolucion getResolucion() {
		return resolucion;
	}

	public void setResolucion(TdResolucion resolucion) {
		this.resolucion = resolucion;
	}

	public TdRepresentante getTdRepresentante() {
		return tdRepresentante;
	}

	public void setTdRepresentante(TdRepresentante tdRepresentante) {
		this.tdRepresentante = tdRepresentante;
	}

	public List<TdDocumentoAnexo> getListDocumentoAnexos() {
		return listDocumentoAnexos;
	}

	public void setListDocumentoAnexos(List<TdDocumentoAnexo> listDocumentoAnexos) {
		this.listDocumentoAnexos = listDocumentoAnexos;
	}

	public int getIndexTblDocumentoAnexo() {
		return indexTblDocumentoAnexo;
	}

	public void setIndexTblDocumentoAnexo(int indexTblDocumentoAnexo) {
		this.indexTblDocumentoAnexo = indexTblDocumentoAnexo;
	}

	public List<GnTipoDocumento> getListGnTipoDocumentos() {
		return listGnTipoDocumentos;
	}

	public void setListGnTipoDocumentos(List<GnTipoDocumento> listGnTipoDocumentos) {
		this.listGnTipoDocumentos = listGnTipoDocumentos;
	}

	public int getNroExpedientesPorGuardar() {
		return nroExpedientesPorGuardar;
	}

	public void setNroExpedientesPorGuardar(int nroExpedientesPorGuardar) {
		this.nroExpedientesPorGuardar = nroExpedientesPorGuardar;
	}

	public List<ResolutorDTO> getListResolutorDTOs() {
		return listResolutorDTOs;
	}

	public void setListResolutorDTOs(List<ResolutorDTO> listResolutorDTOs) {
		this.listResolutorDTOs = listResolutorDTOs;
	}

	public Integer getResolutorIdSeleccionado() {
		return resolutorIdSeleccionado;
	}

	public void setResolutorIdSeleccionado(Integer resolutorIdSeleccionado) {
		this.resolutorIdSeleccionado = resolutorIdSeleccionado;
	}

//	public HashMap<Integer, String> getHashMapEstadoExpediente() {
//		return hashMapEstadoExpediente;
//	}
//
//	public void setHashMapEstadoExpediente(HashMap<Integer, String> hashMapEstadoExpediente) {
//		this.hashMapEstadoExpediente = hashMapEstadoExpediente;
//	}

	public HashMap<String, Integer> getListProcedimientos() {
		return listProcedimientos;
	}

	public void setListProcedimientos(HashMap<String, Integer> listProcedimientos) {
		this.listProcedimientos = listProcedimientos;
	}

	/**
	 * @return the resultado
	 */
	public TdResultado getResultado() {
		return resultado;
	}

	/**
	 * @param resultado
	 *            the resultado to set
	 */
	public void setResultado(TdResultado resultado) {
		this.resultado = resultado;
	}

	/**
	 * @return the vistaExpedienteSimple
	 */
	public boolean isVistaExpedienteSimple() {
		return vistaExpedienteSimple;
	}

	/**
	 * @param vistaExpedienteSimple the vistaExpedienteSimple to set
	 */
	public void setVistaExpedienteSimple(boolean vistaExpedienteSimple) {
		this.vistaExpedienteSimple = vistaExpedienteSimple;
	}

	public List<TdEstadoExpediente> getListEstadoExpedientes() {
		return listEstadoExpedientes;
	}

	public void setListEstadoExpedientes(List<TdEstadoExpediente> listEstadoExpedientes) {
		this.listEstadoExpedientes = listEstadoExpedientes;
	}

	public List<TdSituacionExpediente> getListSituacionExpedientes() {
		return listSituacionExpedientes;
	}

	public void setListSituacionExpedientes(List<TdSituacionExpediente> listSituacionExpedientes) {
		this.listSituacionExpedientes = listSituacionExpedientes;
	}
	
	public List<TdSituacionExpediente> getListOdSituacionExpediente() {
		return listOdSituacionExpediente;
	}

	public void setListOdSituacionExpediente(List<TdSituacionExpediente> listOdSituacionExpediente) {
		this.listOdSituacionExpediente = listOdSituacionExpediente;
	}
	// public Integer getExpedienteId() {
	// return expedienteId;
	// }
	//
	// public void setExpedienteId(Integer expedienteId) {
	// this.expedienteId = expedienteId;
	// }

}
