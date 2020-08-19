package com.sat.sisat.obligacion.managed;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.richfaces.component.html.HtmlFileUpload;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;
import org.richfaces.model.selection.SimpleSelection;

import com.sat.sisat.cobranzacoactiva.dto.DetencionDeudaDTO;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.FileUpload;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.obligacion.business.ObligacionBoRemote;
import com.sat.sisat.obligacion.dto.ObligacionDTO;
import com.sat.sisat.obligacion.dto.SubConceptoDTO;
import com.sat.sisat.persistence.entity.PaPapeleta;
import com.sat.sisat.predial.business.RegistroPrediosBoRemote;
import com.sat.sisat.predial.dto.FindRpDjPredial;
import com.sat.sisat.vehicular.business.VehicularBoRemote;
import com.sat.sisat.vehicular.dto.BuscarVehicularDTO;

/**
 * @author Miguel Macias 
 * @version 0.1
 * @since 31/07/2012
 * La clase ObligacionManaged.java encargada de toda la interaccion del usuario con la aplicacion
*/
@ManagedBean
@ViewScoped
public class ObligacionPapeletasManaged extends BaseManaged {

	private static final long serialVersionUID = -6993981915534906197L;

	
	
	private int  TIPOPREDIO = 1;	
	private int  TIPOVEHICULO = 2;	
	
	
	private int MULTAS = 12;
	private int COSTAS = 5;
	private int GASTOS = 6;
	private int EPND = 11;	
	
	private int modoGastoMonto = 1;	
	
	private int tipoReferenciaOblg = TIPOPREDIO;	

	private String codidoPlacaReferenciaObg;

	private String direccionPredio;

	private String anhoAfectacion;

	private String cmbConcepto;
	
	private String cmbSubConcepto;
	
	private String cmbTasa;	

	private String montoTasa;

	private String nroValor;

	private String fechaEmisionInfraccion;

	private String fechaVencimiento;
	
	private boolean flagValido = false;
	
	private boolean flagViewDetalle = false;

	private List<ObligacionDTO> listObligacionDTOs = new ArrayList<ObligacionDTO>();;

	private List<BuscarVehicularDTO> listVehicularDTOs = new ArrayList<BuscarVehicularDTO>();
	
	private List<FindRpDjPredial> listDjPredials = new ArrayList<FindRpDjPredial>();
	
	private SubConceptoDTO subConceptoDTO;	

	private List<SelectItem> listSubConceptoDTOItems = new ArrayList<SelectItem>();
	
	private HashMap<String, SubConceptoDTO> mapSupConcepto = new HashMap<String, SubConceptoDTO>();
	
	private ObligacionDTO obligacionDTO;
	
	private HtmlFileUpload fileUpload;
	
	private BigDecimal montoTotal = new BigDecimal(0);
	
	private SimpleSelection selectObligacionDTO;
	
	private String nroPapeleta;
	
	private Integer factor = 1;
	@EJB
	VehicularBoRemote vehicularBo;
	
	@EJB
	RegistroPrediosBoRemote registroPrediosBo;
	
	@EJB
	
	ObligacionBoRemote obligacionBo;

	private List<DetencionDeudaDTO> lstDetencionDeudaDTOs = new ArrayList<DetencionDeudaDTO>();
	
	private PaPapeleta paPapeleta = null;
	
	public ObligacionPapeletasManaged() {

	}

	@PostConstruct
	public void init() {
		
		try {
			
			
			
			
			paPapeleta= (PaPapeleta) getSessionManaged().getSessionMap().get("paPapeleta");
			
			getSessionManaged().getSessionMap().remove("paPapeleta");
			
			getSessionManaged().getContribuyente().setPersonaId(paPapeleta.getPersonaInfractorId());
			
			
			listVehicularDTOs = vehicularBo.findDjVehicular(getSessionManaged().getContribuyente().getPersonaId());			
			
		
			lstDetencionDeudaDTOs = obligacionBo.buscarDetencionesPorPapeletaId(paPapeleta.getPapeletaId());
			
		} catch (Exception e) {

			WebMessages.messageError(e.getMessage());
		}		
 
	}

	/**
	 * Método que agrega una obligación a la grilla para luego ser procesada
	 */
	public void agregar() {		
		
		
//		if(getObligacionDTO().getConceptoId() == MULTAS){
//			
//			String[] arrayNroValor = new String[3];
//			
//			arrayNroValor = obligacionDTO.getNroValor().split("-");
//			
//			boolean validUnidad = false;
//			
//			boolean validTipoDocumento = false;
//			
//			try {
//				validUnidad = obligacionBo.checkUnidadOrganica(arrayNroValor[0]);
//				validTipoDocumento = obligacionBo.checkTipoDocumentoMulta(Integer.parseInt(arrayNroValor[1]));
//				
//				if(!validUnidad){
//					
//					WebMessages.messageError("Error en el Nro Valor, codigo de unidad no corresponde");	
//					return;
//				}else if(!validTipoDocumento){
//
//					WebMessages.messageError("Error en el Nro Valor, codigo de tipo documento incorrecto");
//					return;
//				}
//				
//			} catch (SisatException e) {				
//				WebMessages.messageError(e.getMessage());
//				return;
//			}			
//
//			
//		}else if (getObligacionDTO().getConceptoId() == COSTAS || getObligacionDTO().getConceptoId() == GASTOS) {
//			
////			try {
////				
////				//verificando la existencia de la rec
////				/**  Quitando la validacion debido a que aun no se tiene las rec's cargadas */
//////				CcRec rec = obligacionBo.obtenerRecByNroRec(getObligacionDTO().getReferenciaREC(), 
//////														getSessionManaged().getContribuyente().getPersonaId());
//////				if(rec == null){
//////					WebMessages.messageError("La referencia REC ingresada no existe");
//////					return;
//////				}
////				
////				//getObligacionDTO().setIdReferenciaREC(rec.getRecId());
////				/**  Quitando la validacion debido a que aun no se tiene las rec's cargadas */
////				getObligacionDTO().setIdReferenciaREC(0);
////				
////			} catch (SisatException e) {
////				
////				WebMessages.messageError(e.getMessage());
////				return;
////			}
//		} 		
		
		getObligacionDTO().setNroPapeleta(paPapeleta.getNroPapeleta());
		getObligacionDTO().setPapeletaId(paPapeleta.getPapeletaId());
		
		if (getObligacionDTO().getConceptoId() != EPND) {
			/**
			 * Realizando el parche, a solicitud de Sonia para el cambio de fecha emision por fecha
			 * liquidacion y la eliminacion de fecha de vencimiento
			 */
			obligacionDTO.setFechaVencimiento(obligacionDTO.getFechaEmision());
		}
		
		listObligacionDTOs.add(this.obligacionDTO);
		
		montoTotal = montoTotal.add(obligacionDTO.getMonto());
		
		subConceptoDTO = null;
		cmbSubConcepto = null;
		
		//resguardando el anno de afectacion
		int annoAfectacion = obligacionDTO.getAnnoAfectacion();
		
		//reinicializando la data de ObligacionDTO
		obligacionDTO = new ObligacionDTO();
		
		//restahurando anno de afectacion
		obligacionDTO.setAnnoAfectacion(annoAfectacion);
		
		
		flagValido= true;
		setFlagViewDetalle(false);
		subConceptoDTO = null;		
		cmbSubConcepto = null;
		listSubConceptoDTOItems = new ArrayList<SelectItem>();
		cmbConcepto = null;
		factor = 1;
		
	}

	/**
	 * Quita el elemento i de la grilla de obligaciones
	 * @param i
	 */
	public void quitar() {
		
		debug("quitar - inicio");
		
		Iterator<Object> iter = selectObligacionDTO.getKeys();
		
		while(iter.hasNext()){
			int i = (Integer)iter.next();
			montoTotal = montoTotal.subtract(listObligacionDTOs.get(i).getMonto());
			listObligacionDTOs.remove(i);			
		}		
		
		debug("quitar - fin");
	}

	/**
	 * Listener para detectar el cambio en el combobox Concepto
	 * @param e
	 */
	public void changeListenerValueCmbConcepto(ValueChangeEvent e) {

		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage();

		if (getObligacionDTO().getAnnoAfectacion() <= 0) {

			message.setSummary("Debe ingresar un año de afectación");
			message.setDetail(" * Debe ingresar un año de afectación");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage("formobligacion:inputanhoafectacion", message);

		} else {

			cmbConcepto = (String) e.getNewValue();

			try {

				listSubConceptoDTOItems = new ArrayList<SelectItem>();

				List<SubConceptoDTO> listSubConceptoDTOs = new ArrayList<SubConceptoDTO>();
				

				subConceptoDTO = null;
				cmbSubConcepto = null;
				cmbTasa = null;
				setFlagViewDetalle(false);
				modoGastoMonto = 1;
				// resguardando el anno de afectacion
				int annoAfectacion = obligacionDTO.getAnnoAfectacion();

				// reinicializando la data de ObligacionDTO
				obligacionDTO = new ObligacionDTO();

				// restahurando anno de afectacion
				obligacionDTO.setAnnoAfectacion(annoAfectacion);

				if (cmbConcepto.equals("COSTAS")) {
					getObligacionDTO().setConceptoId(this.COSTAS);
					getObligacionDTO().setConceptoDescripcion("COSTAS");
					listSubConceptoDTOs = obligacionBo.getSubConceptoCostas(obligacionDTO.getAnnoAfectacion());
				}

				
				if (cmbConcepto.equals("GASTOS")) {
					getObligacionDTO().setConceptoId(this.GASTOS);
					getObligacionDTO().setConceptoDescripcion("GASTOS");
					listSubConceptoDTOs = obligacionBo.getSubConceptoGastos(obligacionDTO.getAnnoAfectacion());
				}

				if (cmbConcepto.equals("MULTAS")) {
					getObligacionDTO().setConceptoId(this.MULTAS);
					getObligacionDTO().setConceptoDescripcion("MULTAS");
					listSubConceptoDTOs = obligacionBo.getSubConceptoMultas(obligacionDTO.getAnnoAfectacion());
				}

				if (cmbConcepto.equals("EPND")) {
					getObligacionDTO().setConceptoId(this.EPND);
					getObligacionDTO().setConceptoDescripcion("EPND");
					listSubConceptoDTOs = obligacionBo.getSubConceptoEPND(obligacionDTO.getAnnoAfectacion());
				}

				listSubConceptoDTOItems = null;
				listSubConceptoDTOItems = new ArrayList<SelectItem>();

				for (SubConceptoDTO subConceptoDTO : listSubConceptoDTOs) {

					listSubConceptoDTOItems.add(new SelectItem(subConceptoDTO.getDescripcionCorta(), subConceptoDTO
							.getDescripcionCorta()));
					mapSupConcepto.put(subConceptoDTO.getDescripcionCorta(), subConceptoDTO);
				}

				if (listSubConceptoDTOs.size() == 0 && !cmbConcepto.isEmpty()) {
					WebMessages.messageInfo("No se encontraron subconceptos asociados al año de afectación ingresado y al concepto elegido");
					cmbConcepto = null;

				}
			} catch (SisatException e1) {

				WebMessages.messageError(e1.getMessage());
			}

		}

	}
	
	public void changeListenerAnnoAfectacion() {

		debug("changeListenerAnnoAfectacion - inicio");

				
		flagValido = true;
		flagViewDetalle = false;
		subConceptoDTO = null;
		
		cmbConcepto = null;
		cmbSubConcepto = null;
		
		listSubConceptoDTOItems = new ArrayList<SelectItem>();
		
		
		// resguardando el anno de afectacion
		int annoAfectacion = obligacionDTO.getAnnoAfectacion();		
		
		//htmlCmbConcepto = new HtmlComboBox();

		// reinicializando la data de ObligacionDTO
		obligacionDTO = new ObligacionDTO();

		// restahurando anno de afectacion
		obligacionDTO.setAnnoAfectacion(annoAfectacion);
		
		cmbTasa = null;
		
		
		debug("changeListenerAnnoAfectacion - fin");
	}
	
	
	/**
	 * Método que limpia el campo <code>codidoPlacaReferenciaObg</code> para quitar la eleccion de un predio o un vehiculo
	 */
	public void quitarPredioVehiculo() {
		debug("inicio - quitarPredioVehiculo");

		getObligacionDTO().setCodigoPlacaReferencia("");

		debug("fin - quitarPredioVehiculo");

	}
	
	/**
	 * Listener que detectar el cambio en el combobox sub-concepto
	 * @param event
	 */
	public void changeListenerSubConcepto(ValueChangeEvent event) {

		debug("listenerSubConcepto - inicio");

		String key = (String) event.getNewValue();

		subConceptoDTO = mapSupConcepto.get(key);
		cmbSubConcepto = key;

		if (subConceptoDTO != null) {

			getObligacionDTO().setTasa(subConceptoDTO.getValor());
			getObligacionDTO().setSubConceptoId(subConceptoDTO.getSubconceptoId());
			getObligacionDTO().setSubConceptoDescripcion(subConceptoDTO.getDescripcionCorta());
			if(getObligacionDTO().getTasa()!= null){
				getObligacionDTO().setMonto(this.obligacionDTO.getTasa().multiply(new BigDecimal(factor)));
			}
			setFlagViewDetalle(true);
		}

		debug("listenerSubConcepto - fin");
	}
	
	
	public List<SelectItem> getListItemConcepto() {

		debug("getListItemConcepto - inicio");

		List<SelectItem> listSelectItems = new ArrayList<SelectItem>();

		listSelectItems.add(new SelectItem("COSTAS", String.valueOf(this.COSTAS)));
		listSelectItems.add(new SelectItem("EPND", String.valueOf(this.EPND)));
		listSelectItems.add(new SelectItem("GASTOS", String.valueOf(this.GASTOS)));
//		listSelectItems.add(new SelectItem("MULTAS", String.valueOf(this.MULTAS)));		
		

		debug("getListItemConcepto - fin");

		return listSelectItems;
	}
	
	/**
	 * Listener que detecta cuando se ha terminado de subir un archivo a la aplicacion
	 * @param event
	 */
	public void uploadListenerFileValorPDF(UploadEvent event){
		
		UploadItem item = event.getUploadItem();
		FileUpload fileUpload = new FileUpload();
		fileUpload.setFile(item.getFile());
		fileUpload.setFileName(event.getUploadItem().getFileName());
		fileUpload.setContentType(event.getUploadItem().getContentType());

		obligacionDTO.setArchivo(item.getFile());
		obligacionDTO.setFileUpload(fileUpload);
		 
	}
	
	/**
	 * Listener que detecta el cambio en el combobox tasa en la obligacion EPND
	 * @param event
	 */
	public void changeListenerCmbTasaEPND(ValueChangeEvent event) {
		debug("changeListenerCmbTasaEPND - inicio");

		String q = (String) event.getNewValue();

		q = q.replace("%", "");

		BigDecimal b = new BigDecimal(q);

		b = b.divide(new BigDecimal("100"));

		getObligacionDTO().setTasa(b);

		if (getObligacionDTO().getBaseImponible() != null) {

			b = b.multiply(getObligacionDTO().getBaseImponible());

			getObligacionDTO().setImpuesto(b);

			getObligacionDTO().setMonto(b);
		}

		debug("changeListenerCmbTasaEPND - fin");
	}
	
	public void changeListenerSelectOneRadioCosta(ValueChangeEvent event){
		debug("changeListenerCmbTasaEPND - inicio");

		if (modoGastoMonto == 1) {
			getObligacionDTO().setTasa(subConceptoDTO.getValor());
			getObligacionDTO().setMonto(this.obligacionDTO.getTasa().multiply(new BigDecimal(factor)));
		}else{
			getObligacionDTO().setTasa(subConceptoDTO.getValor());
			getObligacionDTO().setMonto(this.obligacionDTO.getTasa().multiply(new BigDecimal(factor)));
		}
		
		debug("changeListenerCmbTasaEPND - fin");		
	}
	
	/**
	 * Método que detecta el cambio en el campo base imponible, para luego actualizar el valor que aparecera en el campo
	 * impuesto.
	 * @param event
	 */
	public void changeListenerInputBaseImponibleEPND(ValueChangeEvent event) {
		debug("changeListenerInputBaseImponibleEPND - inicio");

		BigDecimal b = (BigDecimal) event.getNewValue();		

		getObligacionDTO().setBaseImponible(b);

		if (getObligacionDTO().getTasa() != null) {

			BigDecimal tasa = getObligacionDTO().getTasa();

			BigDecimal temp = tasa.multiply(getObligacionDTO().getBaseImponible());

			getObligacionDTO().setImpuesto(temp);
			getObligacionDTO().setMonto(temp);

		}

		debug("changeListenerInputBaseImponibleEPND - fin");
	}
	
	/**
	 * Método que realiza el registro que todos los items de la grilla, asi como el registro de las deudas generadas.
	 */
	public void guardarItemsObligacion() {
		debug("guardarObligacion - inicio");

		try {
			obligacionBo.saveObligaciones(listObligacionDTOs, getSessionManaged().getContribuyente().getPersonaId());

			flagValido = true;
			flagViewDetalle = false;

			subConceptoDTO = null;
			cmbSubConcepto = null;
			listSubConceptoDTOItems = new ArrayList<SelectItem>();
			cmbConcepto = null;
			obligacionDTO = null;
			cmbTasa = null;

			listObligacionDTOs.clear();
			montoTotal = new BigDecimal(0);

			//WebMessages.messageInfo("Se han registrado las obligaciones satisfactoriamente");

		} catch (SisatException e) {

			WebMessages.messageError(e.getMessage());
		}

		debug("guardarObligacion - fin");
	}
	
	public void changeListenerFechaEmisionCostas() {

		debug("changeListenerFechaEmisionCostas - inicio");
		
		Date d = (Date) getObligacionDTO().getFechaEmision().clone();
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.DAY_OF_MONTH, 1);
		d = c.getTime();

		getObligacionDTO().setFechaVencimiento(d);

		debug("changeListenerFechaEmisionCostas - fin");
	}
	
	
	public void desactivarDetencion() {

		try {
			obligacionBo.desactivarDetencionPapeleta(getPaPapeleta().getNroPapeleta());
			
			lstDetencionDeudaDTOs.clear();
			
			lstDetencionDeudaDTOs = obligacionBo.buscarDetencionesPorPapeletaId(getPaPapeleta().getPapeletaId());
			
		} catch (SisatException e) {

			WebMessages.messageError(e.getMessage());
		}

	}
	
	public void activarDetencion() {

		try {
			obligacionBo.activarDetencionPapeleta(getPaPapeleta().getNroPapeleta());
			
			lstDetencionDeudaDTOs.clear();
			
			lstDetencionDeudaDTOs = obligacionBo.buscarDetencionesPorPapeletaId(getPaPapeleta().getPapeletaId());
			
		} catch (SisatException e) {

			WebMessages.messageError(e.getMessage());
		}

	}
	
	public void changeListenerInputIdFactor(ValueChangeEvent event){		
		
		Integer b = (Integer) event.getNewValue();
		
		if(b != null){
			this.factor = b;
			this.obligacionDTO.setMonto(this.obligacionDTO.getTasa().multiply(new BigDecimal(factor)));
		}else{
			this.factor = 0;
			this.obligacionDTO.setMonto(this.obligacionDTO.getTasa().multiply(new BigDecimal(factor)));			
		}
		
	}
	
	public void changeListenerInputIdTasa(ValueChangeEvent event){		
		
		BigDecimal b = (BigDecimal) event.getNewValue();
		
		if(b != null){
			obligacionDTO.setTasa(b);
			this.obligacionDTO.setMonto(this.obligacionDTO.getTasa().multiply(new BigDecimal(factor)));
		}else{
			obligacionDTO.setTasa(BigDecimal.ZERO);
			this.obligacionDTO.setMonto(this.obligacionDTO.getTasa().multiply(new BigDecimal(factor)));			
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

	public boolean isFlagValido() {
		return flagValido;
	}

	public void setFlagValido(boolean flagValido) {
		this.flagValido = flagValido;
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

	public void setListSubConceptoDTOItems(List<SelectItem> listSubConceptoDTOItems) {
		this.listSubConceptoDTOItems = listSubConceptoDTOItems;
	}

	public int getModoGastoMonto() {
		return modoGastoMonto;
	}

	public void setModoGastoMonto(int modoGastoMonto) {
		this.modoGastoMonto = modoGastoMonto;
	}
	
	public ObligacionDTO getObligacionDTO() {
		if(obligacionDTO == null){
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



	public BigDecimal getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(BigDecimal montoTotal) {
		this.montoTotal = montoTotal;
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

	public List<DetencionDeudaDTO> getLstDetencionDeudaDTOs() {
		return lstDetencionDeudaDTOs;
	}

	public void setLstDetencionDeudaDTOs(List<DetencionDeudaDTO> lstDetencionDeudaDTOs) {
		this.lstDetencionDeudaDTOs = lstDetencionDeudaDTOs;
	}

	public Integer getFactor() {
		return factor;
	}

	public void setFactor(Integer factor) {
		this.factor = factor;
	}

	public String getNroPapeleta() {
		return nroPapeleta;
	}

	public void setNroPapeleta(String nroPapeleta) {
		this.nroPapeleta = nroPapeleta;
	}

	public PaPapeleta getPaPapeleta() {
		return paPapeleta;
	}

	public void setPaPapeleta(PaPapeleta paPapeleta) {
		this.paPapeleta = paPapeleta;
	}	
	
}
