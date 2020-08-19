package com.sat.sisat.obligacion.managed;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.richfaces.component.html.HtmlFileUpload;
import org.richfaces.model.selection.SimpleSelection;

import com.sat.sisat.caja.dto.DeudaDTO;
import com.sat.sisat.common.business.GeneralBoRemote;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.SATWEBParameterFactory;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.menus.business.MenuBoRemote;
import com.sat.sisat.menus.dto.SimpleMenuDTO;
import com.sat.sisat.obligacion.business.ObligacionBoRemote;
import com.sat.sisat.obligacion.dto.MultaDTO;
import com.sat.sisat.obligacion.dto.ObligacionDTO;
import com.sat.sisat.obligacion.dto.SubConceptoDTO;
import com.sat.sisat.persistence.CrudServiceBean;
import com.sat.sisat.predial.business.RegistroPrediosBoRemote;
import com.sat.sisat.predial.dto.FindRpDjPredial;
import com.sat.sisat.vehicular.business.VehicularBoRemote;
import com.sat.sisat.vehicular.dto.BuscarVehicularDTO;

/**
 * @author Miguel Macias
 * @version 0.1
 * @since 31/07/2012 La clase ObligacionManaged.java encargada de toda la
 *        interaccion del usuario con la aplicacion
 */
@ManagedBean
@ViewScoped
public class EmisionMultasManaged extends BaseManaged {

	private static final long serialVersionUID = -6993981915534906197L;

	private int TIPOPREDIO = 1;
	private int TIPOVEHICULO = 2;

	private int MULTAS = 12;
	@SuppressWarnings("unused")
	private int COSTAS = 5;
	@SuppressWarnings("unused")
	private int GASTOS = 6;

	private int modoGastoMonto = 1;

	private int tipoReferenciaOblg = TIPOPREDIO;

	private String codidoPlacaReferenciaObg;

	private String direccionPredio;

	private String anhoAfectacion;

	private String cmbUnidad = "Dpto. Servicios al Contribuyente";

	private String cmbConcepto;

	private String cmbSubConcepto;

	private String cmbTributoAsociado;

	private String cmbTasa;

	private String montoTasa;

	private String nroValor;

	private String fechaEmisionInfraccion;

	private String fechaVencimiento;

	private boolean flagViewDetalle = false;

	private List<ObligacionDTO> listObligacionDTOs = new ArrayList<ObligacionDTO>();;

	private List<BuscarVehicularDTO> listVehicularDTOs = new ArrayList<BuscarVehicularDTO>();

	private List<FindRpDjPredial> listDjPredials = new ArrayList<FindRpDjPredial>();

	private SubConceptoDTO subConceptoDTO;

	private List<SelectItem> listSubConceptoDTOItems = new ArrayList<SelectItem>();

	private HashMap<String, SubConceptoDTO> mapSupConcepto = new HashMap<String, SubConceptoDTO>();

	private ObligacionDTO obligacionDTO;

	private HtmlFileUpload fileUpload;

	private SimpleSelection selectObligacionDTO;

	private List<MultaDTO> lstMultaDTOs;

	private MultaDTO multaDTOSeleccionada = new MultaDTO();
	BigDecimal totalAPagar = new BigDecimal(0);
	BigDecimal totalDscto = new BigDecimal(0);
	BigDecimal totalSubTotal = new BigDecimal(0);
	BigDecimal totalInteres = new BigDecimal(0);
	BigDecimal totalMonto = new BigDecimal(0);

	@EJB
	VehicularBoRemote vehicularBo;

	@EJB
	RegistroPrediosBoRemote registroPrediosBo;

	@EJB
	ObligacionBoRemote obligacionBo;

	@EJB
	GeneralBoRemote generalBo;
	
	@EJB
	MenuBoRemote menuBo;
	
	// INICIO PERMISOS PARA EL MODULO -=CRAMIREZ=-
		private List<SimpleMenuDTO> listPermisosSubmenu = new ArrayList<SimpleMenuDTO>();
		private boolean permisoAgregarRegistrar;
		private boolean permisoNotificar;
	// FIN PERMISOS PARA EL MODULO
			
	public EmisionMultasManaged() {

	}

	@PostConstruct
	public void init() {
		permisosMenu();
		this.obligacionDTO = new ObligacionDTO();
		this.obligacionDTO.setPersonaId(getSessionManaged().getContribuyente()
				.getPersonaId());
		this.obligacionDTO.setUnidadId(61);

		try {
			listVehicularDTOs = vehicularBo.findDjVehicular(getSessionManaged()
					.getContribuyente().getPersonaId());
			listDjPredials = registroPrediosBo
					.getRpDjpredial(null, null, null, null, null, null, null,
							null, null, null, null, getSessionManaged()
									.getContribuyente().getPersonaId(), true);

			List<SubConceptoDTO> listSubConceptoDTOs = new ArrayList<SubConceptoDTO>();

			getObligacionDTO().setConceptoId(this.MULTAS);
			getObligacionDTO().setConceptoDescripcion("MULTAS");
			listSubConceptoDTOs = obligacionBo
					.getSubConceptoMultas(obligacionDTO.getAnnoAfectacion());

			listSubConceptoDTOItems = null;
			listSubConceptoDTOItems = new ArrayList<SelectItem>();

			for (SubConceptoDTO subConceptoDTO : listSubConceptoDTOs) {

				listSubConceptoDTOItems.add(new SelectItem(subConceptoDTO
						.getDescripcionCorta(), subConceptoDTO
						.getDescripcionCorta()));
				mapSupConcepto.put(subConceptoDTO.getDescripcionCorta(),
						subConceptoDTO);
			}

			lstMultaDTOs = obligacionBo.buscarMultas(obligacionDTO);

			sumarMultas(lstMultaDTOs);

		} catch (Exception e) {

			WebMessages.messageError(e.getMessage());
		}

	}
	
	public void permisosMenu() {	
		try {
			int submenuId = Constante.EMITIR_MULTAS_ADMINISTRATIVAS;
			
			int permisoAgregarRegistrarId = Constante.AGREGAR_REGISTRAR;
			int permisoNotificarId = Constante.NOTIFICAR;
			
			permisoAgregarRegistrar = false;
			permisoNotificar = false;
			
			
			listPermisosSubmenu  = menuBo.getAccesosSubmenuUsuario( getSessionManaged().getUsuarioLogIn().getUsuarioId() , submenuId);
			
			Iterator<SimpleMenuDTO> menuIterar = listPermisosSubmenu.iterator();
			while (menuIterar.hasNext()) {
				SimpleMenuDTO lsm = menuIterar.next();
				if(lsm.getItemId() == permisoAgregarRegistrarId) {
					permisoAgregarRegistrar = true;
				}
				if(lsm.getItemId() == permisoNotificarId) {
					permisoNotificar = true;
				}
			}
			
		} catch (SisatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Listener que detectar el cambio en el combobox sub-concepto
	 * 
	 * @param event
	 */
	public void changeListenerSubConcepto(ValueChangeEvent event) {

		debug("listenerSubConcepto - inicio");

		String key = (String) event.getNewValue();

		subConceptoDTO = mapSupConcepto.get(key);
		cmbSubConcepto = key;

		if (subConceptoDTO != null) {

			getObligacionDTO().setMonto(subConceptoDTO.getValor());
			getObligacionDTO().setSubConceptoId(
					subConceptoDTO.getSubconceptoId());
			getObligacionDTO().setSubConceptoDescripcion(
					subConceptoDTO.getDescripcion());

		}

		debug("listenerSubConcepto - fin");
	}

	/**
	 * Método que limpia el campo <code>codidoPlacaReferenciaObg</code> para
	 * quitar la eleccion de un predio o un vehiculo
	 */
	public void quitarPredioVehiculo() {
		debug("inicio - quitarPredioVehiculo");

		getObligacionDTO().setCodigoPlacaReferencia("");

		debug("fin - quitarPredioVehiculo");

	}

	public void generarMulta() throws Exception {

		try {
			Calendar calendar = Calendar.getInstance();
			Integer diasHabiles = obligacionBo.obtenerDiasHabiles(
					obligacionDTO.getFechaAdquision(), calendar.getTime());
			if (diasHabiles > 5) {
				/** Generando las multas */
				obligacionBo.generarMulta(getSessionManaged().getContribuyente().getPersonaId(), obligacionDTO);
				Integer loteId = generalBo.ObtenerCorrelativoTabla("cc_lote", 1);
				obligacionBo.generarResolucionMulta(loteId - 1,
						getSessionManaged().getContribuyente().getPersonaId(),
						obligacionDTO.getUnidadId(), getUser().getUsuarioId(),
						getUser().getTerminal(),
						obligacionDTO.getSubConceptoId());

				/**
				 * Buscando las multas recientemente generadas tambien trae
				 * multas pendientes que contienen el mismo tipo de multa
				 */
				lstMultaDTOs = obligacionBo.buscarMultas(obligacionDTO);

				sumarMultas(lstMultaDTOs);

				Integer unidadId = obligacionDTO.getUnidadId();

				/** Reiniciando el DTO de obligaciones */
				this.obligacionDTO = new ObligacionDTO();
				this.obligacionDTO.setPersonaId(getSessionManaged().getContribuyente().getPersonaId());
				obligacionDTO.setConceptoId(this.MULTAS);
				obligacionDTO.setConceptoDescripcion("MULTAS");
				obligacionDTO.setUnidadId(unidadId);

				/**
				 * Reiniciando los combobox de subconceptos y tributos asociados
				 */
				cmbSubConcepto = null;
				cmbTributoAsociado = null;
			} else {
				addErrorMessage(getMsg("Aun no le corresponde generarle multa."));
			}

		} catch (SisatException e) {
			WebMessages.messageError(e.getMessage());
		}

	}

	public void valueChangeListenerItem(MultaDTO multaDTO) {
		if (multaDTO.isSelected()) {
			if (multaDTOSeleccionada.equals(multaDTO)) {
				multaDTOSeleccionada = new MultaDTO();
			}
		} else {
			multaDTOSeleccionada = multaDTO;
		}
	}

	public void notificarResolucionMulta() throws SisatException {
		int cuentaSeleccion = 0;
		for (MultaDTO de : lstMultaDTOs) {
			if (de.isSelected()) {
				obligacionBo.notificarResolucionMulta(de.getNroRsMulta());
				cuentaSeleccion = cuentaSeleccion + 1;
			}
		}
		if (cuentaSeleccion == 0) {
			WebMessages.messageInfo("No hay Multas a Notificar. Verifique!!!");
		} else {
			WebMessages.messageInfo("Se Han Notificado " + cuentaSeleccion
					+ " Multas Seleccionadas");
		}
	}

	public void generacionRmPendientesPagoServicios() {
		try {
			if (listVehicularDTOs != null || listDjPredials != null) {
				java.sql.Connection connection = null;
				// java.sql.Connection connec = null;
				try {
					CrudServiceBean connj = CrudServiceBean.getInstance();
					connection = connj.connectJasper();
					// CrudServiceBean conxi = CrudServiceBean.getInstance();
					// connec = conxi.connectImage();
					String path_context = FacesContext.getCurrentInstance()
							.getExternalContext().getRealPath("/");
					String path_report = path_context + "/sisat/reportes/";
					String path_imagen = path_context
							+ "/sisat/reportes/imagen/";
					HashMap<String, Object> parameters = new HashMap<String, Object>();
					// parameters.put("SUBREPORT_CONNECTION", connec);
					parameters.put("p_persona_id", getSessionManaged()
							.getContribuyente().getPersonaId());
					parameters.put("p_responsable", getSessionManaged()
							.getUsuarioLogIn().getNombreUsuario());
					parameters.put("ruta_imagen", path_imagen);
					// parameters.put("p_periodo",
					// findCcLoteItem.getAnnoLote());
					// parameters.put("REPORT_LOCALE", new Locale("en",
					// "ENGLISH"));
					JasperPrint jasperPrint = JasperFillManager
							.fillReport(
									(SATWEBParameterFactory.getPathReporte() + "rm_resolucion_multa_servicios.jasper"),
									parameters, connection);
					ByteArrayOutputStream output = new ByteArrayOutputStream();
					JasperExportManager.exportReportToPdfStream(jasperPrint,
							output);
					HttpServletResponse response = (HttpServletResponse) FacesContext
							.getCurrentInstance().getExternalContext()
							.getResponse();
					response.setContentType("application/pdf");
					response.addHeader("Content-Disposition",
							"attachment;filename=rm_sinpagar_servicios.pdf");
					response.setContentLength(output.size());
					ServletOutputStream servletOutputStream = response
							.getOutputStream();
					servletOutputStream.write(output.toByteArray(), 0,
							output.size());
					servletOutputStream.flush();
					servletOutputStream.close();
					FacesContext.getCurrentInstance().responseComplete();
				} catch (Exception e) {
					e.printStackTrace();
					WebMessages.messageError(e.getMessage());
				} finally {
					try {
						if (connection != null) {
							connection.close();
							connection = null;
						}
					} catch (Exception e) {
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void generacionRmPendientesPagoFiscalizacion() {
		try {
			if (listVehicularDTOs != null || listDjPredials != null) {
				java.sql.Connection connection = null;
				// java.sql.Connection connec = null;
				try {
					CrudServiceBean connj = CrudServiceBean.getInstance();
					connection = connj.connectJasper();
					// CrudServiceBean conxi = CrudServiceBean.getInstance();
					// connec = conxi.connectImage();
					String path_context = FacesContext.getCurrentInstance()
							.getExternalContext().getRealPath("/");
					String path_report = path_context + "/sisat/reportes/";
					String path_imagen = path_context
							+ "/sisat/reportes/imagen/";
					HashMap<String, Object> parameters = new HashMap<String, Object>();
					// parameters.put("SUBREPORT_CONNECTION", connec);
					parameters.put("p_persona_id", getSessionManaged()
							.getContribuyente().getPersonaId());
					parameters.put("p_responsable", getSessionManaged()
							.getUsuarioLogIn().getNombreUsuario());
					parameters.put("ruta_imagen", path_imagen);
					// parameters.put("p_periodo",
					// findCcLoteItem.getAnnoLote());
					// parameters.put("REPORT_LOCALE", new Locale("en",
					// "ENGLISH"));
					JasperPrint jasperPrint = JasperFillManager
							.fillReport(
									(SATWEBParameterFactory.getPathReporte() + "rm_resolucion_multa_fiscalizacion.jasper"),
									parameters, connection);
					ByteArrayOutputStream output = new ByteArrayOutputStream();
					JasperExportManager.exportReportToPdfStream(jasperPrint,
							output);
					HttpServletResponse response = (HttpServletResponse) FacesContext
							.getCurrentInstance().getExternalContext()
							.getResponse();
					response.setContentType("application/pdf");
					response.addHeader("Content-Disposition",
							"attachment;filename=rm_sinpagar_fiscalizacion.pdf");
					response.setContentLength(output.size());
					ServletOutputStream servletOutputStream = response
							.getOutputStream();
					servletOutputStream.write(output.toByteArray(), 0,
							output.size());
					servletOutputStream.flush();
					servletOutputStream.close();
					FacesContext.getCurrentInstance().responseComplete();
				} catch (Exception e) {
					e.printStackTrace();
					WebMessages.messageError(e.getMessage());
				} finally {
					try {
						if (connection != null) {
							connection.close();
							connection = null;
						}
					} catch (Exception e) {
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void changeListenerComboBoxTributoAsociado(ValueChangeEvent event) {

		String cmbValueSelect = (String) event.getNewValue();

		if (cmbValueSelect != null && cmbValueSelect.equals("Predial")) {
			obligacionDTO.setConceptoIdTributoReferencia(1);
		} else if (cmbValueSelect != null && cmbValueSelect.equals("Vehicular")) {
			obligacionDTO.setConceptoIdTributoReferencia(2);
		}
	}

	public void changeListenerComboBoxUnidad(ValueChangeEvent event) {

		String cmbValueSelect = (String) event.getNewValue();

		if (cmbValueSelect != null
				&& cmbValueSelect.equals("Dpto. Servicios al Contribuyente")) {
			obligacionDTO.setUnidadId(61);
		} else if (cmbValueSelect != null
				&& cmbValueSelect
						.equals("Dpto. Control y Cobranza de la Deuda")) {
			obligacionDTO.setUnidadId(62);

		} else if (cmbValueSelect != null
				&& cmbValueSelect.equals("Dpto. Fiscalización y Censo Predial")) {
			obligacionDTO.setUnidadId(64);
		}
	}

	public void sumarMultas(List<MultaDTO> lstMultaDTOs) {

		totalAPagar = new BigDecimal(0);
		totalDscto = new BigDecimal(0);
		totalSubTotal = new BigDecimal(0);
		totalInteres = new BigDecimal(0);
		totalMonto = new BigDecimal(0);

		for (MultaDTO m : lstMultaDTOs) {
			totalAPagar = totalAPagar.add(m.getMontoConDscto());
			totalDscto = totalDscto.add(m.getMontoDescuento());
			totalSubTotal = totalSubTotal.add(m.getMontoSinDscto());
			totalInteres = totalInteres.add(m.getInteres());
			totalMonto = totalMonto.add(m.getMonto());
		}
	}

	public String getDireccionPredio() {
		return direccionPredio;
	}

	public void setDireccionPredio(String direccionPredio) {
		this.direccionPredio = direccionPredio;
	}

	public String getAnhoAfectacion() {
		return anhoAfectacion;
	}

	public void setAnhoAfectacion(String anhoAfectacion) {
		this.anhoAfectacion = anhoAfectacion;
	}

	public String getMontoTasa() {
		return montoTasa;
	}

	public void setMontoTasa(String montoTasa) {
		this.montoTasa = montoTasa;
	}

	public String getFechaEmisionInfraccion() {
		return fechaEmisionInfraccion;
	}

	public void setFechaEmisionInfraccion(String fechaEmisionInfraccion) {
		this.fechaEmisionInfraccion = fechaEmisionInfraccion;
	}

	public String getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(String fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public String getNroValor() {
		return nroValor;
	}

	public void setNroValor(String nroValor) {
		this.nroValor = nroValor;
	}

	public String getCodidoPlacaReferenciaObg() {
		return codidoPlacaReferenciaObg;
	}

	public void setCodidoPlacaReferenciaObg(String codidoPlacaReferenciaObg) {
		this.codidoPlacaReferenciaObg = codidoPlacaReferenciaObg;
	}

	public List<BuscarVehicularDTO> getListVehicularDTOs() {
		return listVehicularDTOs;
	}

	public void setListVehicularDTOs(List<BuscarVehicularDTO> listVehicularDTOs) {
		this.listVehicularDTOs = listVehicularDTOs;
	}

	public int getTipoReferenciaOblg() {
		return tipoReferenciaOblg;
	}

	public void setTipoReferenciaOblg(int tipoReferenciaOblg) {
		this.tipoReferenciaOblg = tipoReferenciaOblg;
	}

	public int getTIPOPREDIO() {
		return TIPOPREDIO;
	}

	public int getTIPOVEHICULO() {
		return TIPOVEHICULO;
	}

	public List<FindRpDjPredial> getListDjPredials() {
		return listDjPredials;
	}

	public void setListDjPredials(List<FindRpDjPredial> listDjPredials) {
		this.listDjPredials = listDjPredials;
	}

	public SubConceptoDTO getSubConceptoDTO() {
		return subConceptoDTO;
	}

	public void setSubConceptoDTO(SubConceptoDTO subConceptoDTO) {
		this.subConceptoDTO = subConceptoDTO;
	}

	public List<SelectItem> getListSubConceptoDTOItems() {
		return listSubConceptoDTOItems;
	}

	public void setListSubConceptoDTOItems(
			List<SelectItem> listSubConceptoDTOItems) {
		this.listSubConceptoDTOItems = listSubConceptoDTOItems;
	}

	public int getModoGastoMonto() {
		return modoGastoMonto;
	}

	public void setModoGastoMonto(int modoGastoMonto) {
		this.modoGastoMonto = modoGastoMonto;
	}

	public ObligacionDTO getObligacionDTO() {
		if (obligacionDTO == null) {
			obligacionDTO = new ObligacionDTO();
		}

		return obligacionDTO;
	}

	public void setObligacionDTO(ObligacionDTO obligacionDTO) {
		this.obligacionDTO = obligacionDTO;
	}

	public HtmlFileUpload getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(HtmlFileUpload fileUpload) {
		this.fileUpload = fileUpload;
	}

	public List<ObligacionDTO> getListObligacionDTOs() {
		return listObligacionDTOs;
	}

	public void setListObligacionDTOs(List<ObligacionDTO> listObligacionDTOs) {
		this.listObligacionDTOs = listObligacionDTOs;
	}

	public String getCmbConcepto() {
		return cmbConcepto;
	}

	public void setCmbConcepto(String cmbConcepto) {
		this.cmbConcepto = cmbConcepto;
	}

	public String getCmbSubConcepto() {
		return cmbSubConcepto;
	}

	public void setCmbSubConcepto(String cmbSubConcepto) {
		this.cmbSubConcepto = cmbSubConcepto;
	}

	public String getCmbTasa() {
		return cmbTasa;
	}

	public void setCmbTasa(String cmbTasa) {
		this.cmbTasa = cmbTasa;
	}

	public SimpleSelection getSelectObligacionDTO() {
		return selectObligacionDTO;
	}

	public void setSelectObligacionDTO(SimpleSelection selectObligacionDTO) {
		this.selectObligacionDTO = selectObligacionDTO;
	}

	public boolean isFlagViewDetalle() {
		return flagViewDetalle;
	}

	public void setFlagViewDetalle(boolean flagViewDetalle) {
		this.flagViewDetalle = flagViewDetalle;
	}

	public List<MultaDTO> getLstMultaDTOs() {
		return lstMultaDTOs;
	}

	public void setLstMultaDTOs(List<MultaDTO> lstMultaDTOs) {
		this.lstMultaDTOs = lstMultaDTOs;
	}

	public String getCmbTributoAsociado() {
		return cmbTributoAsociado;
	}

	public void setCmbTributoAsociado(String cmbTributoAsociado) {
		this.cmbTributoAsociado = cmbTributoAsociado;
	}

	public String getCmbUnidad() {
		return cmbUnidad;
	}

	public void setCmbUnidad(String cmbUnidad) {
		this.cmbUnidad = cmbUnidad;
	}

	public BigDecimal getTotalAPagar() {
		return totalAPagar;
	}

	public void setTotalAPagar(BigDecimal totalAPagar) {
		this.totalAPagar = totalAPagar;
	}

	public BigDecimal getTotalDscto() {
		return totalDscto;
	}

	public void setTotalDscto(BigDecimal totalDscto) {
		this.totalDscto = totalDscto;
	}

	public BigDecimal getTotalSubTotal() {
		return totalSubTotal;
	}

	public void setTotalSubTotal(BigDecimal totalSubTotal) {
		this.totalSubTotal = totalSubTotal;
	}

	public BigDecimal getTotalInteres() {
		return totalInteres;
	}

	public void setTotalInteres(BigDecimal totalInteres) {
		this.totalInteres = totalInteres;
	}

	public BigDecimal getTotalMonto() {
		return totalMonto;
	}

	public void setTotalMonto(BigDecimal totalMonto) {
		this.totalMonto = totalMonto;
	}

	public List<SimpleMenuDTO> getListPermisosSubmenu() {
		return listPermisosSubmenu;
	}

	public void setListPermisosSubmenu(List<SimpleMenuDTO> listPermisosSubmenu) {
		this.listPermisosSubmenu = listPermisosSubmenu;
	}

	public boolean isPermisoAgregarRegistrar() {
		return permisoAgregarRegistrar;
	}

	public void setPermisoAgregarRegistrar(boolean permisoAgregarRegistrar) {
		this.permisoAgregarRegistrar = permisoAgregarRegistrar;
	}

	public boolean isPermisoNotificar() {
		return permisoNotificar;
	}

	public void setPermisoNotificar(boolean permisoNotificar) {
		this.permisoNotificar = permisoNotificar;
	}
	
}
