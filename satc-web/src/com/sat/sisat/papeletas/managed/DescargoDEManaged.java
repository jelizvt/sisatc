package com.sat.sisat.papeletas.managed;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.FacesUtil;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.descargo.business.DescargoBoRemote;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.papeleta.dto.FindPapeletas;
import com.sat.sisat.persistence.entity.CdDescargo;
import com.sat.sisat.persistence.entity.GnTipoDocumento;
import com.sat.sisat.persistence.entity.PaDescargoDe;

@ManagedBean
@ViewScoped
public class DescargoDEManaged extends BaseManaged implements Serializable {

	private static final long serialVersionUID = -1953592327390761744L;
	
	@EJB	
	private DescargoBoRemote descargoBo;
	
	private List<FindPapeletas> listCtaCte = new ArrayList<FindPapeletas>();
	
	private FindPapeletas deudaDTOSeleccionada = new FindPapeletas();
	
	private BigDecimal insolutoCancelado = new BigDecimal(0);
	private BigDecimal derechoEmisionCancelado = new BigDecimal(0);
	private BigDecimal totalDeudaCancelada = new BigDecimal(0);
	
	private int deudaId;
	
	private List<SelectItem> lstTipoDocumento;
	
	private HashMap<String, Integer> mapGnTipodocumento = new HashMap<String, Integer>();
	
	private int tipoDescargo;
	private Integer tipoDocumentoId;
	private String nroDocumento;
	private Date fechaDocumento;
	private String observacion;
	
	private BigDecimal totalPrescribir;
	private BigDecimal totalCompensar;
	private BigDecimal totalDescargo;

	private String tipoDocumento;
	
	private BigDecimal montoACompensar;
	
	private String numeroPapeleta;
	
	private String usuario;
	
	@PostConstruct
	public void initialize(){
		try {
			List<GnTipoDocumento> list = descargoBo.obtenerTipoDocumentos();
			lstTipoDocumento = new ArrayList<SelectItem>();			

			for (GnTipoDocumento it : list) {
				lstTipoDocumento.add(new SelectItem(it.getDescripcion(), String.valueOf(it.getTipoDocumentoId())));
				mapGnTipodocumento.put(it.getDescripcion(), it.getTipoDocumentoId());
			}
			
			usuario = getSessionManaged().getUsuarioLogIn().getNombreUsuario();
			
		} catch (SisatException ex) {
			addErrorMessage(ex.getMessage());
		} catch (Exception ex) {
			String msg = "No se ha podido cargar la cuenta corriente";
			System.out.println(msg + ex);
			addErrorMessage(msg);
		}
	}
	
	public void limpiar(){
		this.tipoDocumentoId=1;
		this.nroDocumento="";
		this.fechaDocumento=null;
		this.observacion="";
		this.totalPrescribir = new BigDecimal(0);
		this.totalCompensar = new BigDecimal(0);
		this.tipoDocumento="Acta";
	}

	public void buscar() throws Exception{
		try{
			if(getNumeroPapeleta()!=null && !getNumeroPapeleta().isEmpty()){
				FindPapeletas findPapeleta = new FindPapeletas();
				findPapeleta.setNumPapeleta(getNumeroPapeleta());
				listCtaCte = descargoBo.buscarPapeletasCriteriaDE(findPapeleta);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void inicioDescargo(){
		if(validaPapeleta()){
			limpiar();
			totalDescargo= new BigDecimal(0);
			for (FindPapeletas de : listCtaCte) {
				if (de.isSelected()) {
					totalDescargo=BigDecimal.valueOf(de.getMontoMulta()).add(totalDescargo);
				}
			}
		}
	}
	
	public void descargo() {
		try{
			if(validaPapeleta()){
				totalDescargo = new BigDecimal(0);
				for (FindPapeletas de : listCtaCte) {
					if (de.isSelected()) {
						totalDescargo=BigDecimal.valueOf(de.getMontoMulta()).add(totalDescargo);
						
						PaDescargoDe paDescargo = new PaDescargoDe();
						//Eliminar Deudas y agregar registro
						paDescargo.setTipoDocumentoId(tipoDocumentoId);
						paDescargo.setNroDocumento(nroDocumento);
						paDescargo.setFechaDocumento(fechaDocumento);	
						paDescargo.setObservacion("Servidor :"+usuario+". Obsv: "+observacion);
						paDescargo.setTipoDescargo(Constante.TIPO_DESCARGO_DESCARGO);
						paDescargo.setEstado(Constante.ESTADO_DESCARGADO);
						paDescargo.setFechaRegistro(DateUtil.getCurrentDate());				
						paDescargo.setTotalDescargado(totalDescargo);
						//--
						paDescargo.setPapeletaId(de.getPapeletaId());
						paDescargo.setInteres(BigDecimal.ZERO);
						paDescargo.setReajuste(BigDecimal.ZERO);
						paDescargo.setTotalDeuda(BigDecimal.ZERO);
						paDescargo.setFechaRegistroDeuda(de.getFechaInfraccion());
						//--
						
						descargoBo.descargarPapeletasDE(paDescargo,de.getPapeletaId());
						listCtaCte = descargoBo.buscarPapeletasCriteriaDE(de);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public Boolean validaPapeleta(){
		if(listCtaCte!=null&&listCtaCte.size()>0){
			FindPapeletas de=listCtaCte.get(0);
			if (de.isSelected()) {
				if (de.getEstado().equals(Constante.ESTADO_PAPELETA_DESCARGADO)) {
					WebMessages.messageError("La papeleta seleccionada esta Descargado");
					return Boolean.FALSE;
				}
			}else{
				WebMessages.messageError("Seleccione la papeleta a descargar");
				return Boolean.FALSE;
			}
		}else{
			WebMessages.messageError("Seleccione la papeleta a descargar");
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
	
	public void valueChangeListenerItem(FindPapeletas deudaDTO){
		if(deudaDTO.isSelected()){
			if(deudaDTOSeleccionada.equals(deudaDTO)){
				deudaDTOSeleccionada = new FindPapeletas();
			}
		}
		else{
			deudaDTOSeleccionada = deudaDTO;
		}
	}
	
	public void changeListenerCmbTipoDocumento(ValueChangeEvent event){
		String cmbValueSelect = (String) event.getNewValue();
		Integer id = mapGnTipodocumento.get(cmbValueSelect);
		if(id != null){
			this.tipoDocumentoId = id;
		}		
	}
	
	public String salir(){
		FacesUtil.closeSession("DescargoDeudasManaged");
		return sendRedirectPrincipal();
	}
	
	
	public List<FindPapeletas> getListCtaCte() {
		return listCtaCte;
	}
	
	
	public DescargoBoRemote getDescargoBo() {
		return descargoBo;
	}

	public void setDescargoBo(DescargoBoRemote descargoBo) {
		this.descargoBo = descargoBo;
	}

	public FindPapeletas getDeudaDTOSeleccionada() {
		return deudaDTOSeleccionada;
	}

	public void setDeudaDTOSeleccionada(FindPapeletas deudaDTOSeleccionada) {
		this.deudaDTOSeleccionada = deudaDTOSeleccionada;
	}

	public BigDecimal getInsolutoCancelado() {
		return insolutoCancelado;
	}

	public void setInsolutoCancelado(BigDecimal insolutoCancelado) {
		this.insolutoCancelado = insolutoCancelado;
	}

	public BigDecimal getDerechoEmisionCancelado() {
		return derechoEmisionCancelado;
	}

	public void setDerechoEmisionCancelado(BigDecimal derechoEmisionCancelado) {
		this.derechoEmisionCancelado = derechoEmisionCancelado;
	}

	public BigDecimal getTotalDeudaCancelada() {
		return totalDeudaCancelada;
	}

	public void setTotalDeudaCancelada(BigDecimal totalDeudaCancelada) {
		this.totalDeudaCancelada = totalDeudaCancelada;
	}

	public int getDeudaId() {
		return deudaId;
	}

	public void setDeudaId(int deudaId) {
		this.deudaId = deudaId;
	}

	public List<SelectItem> getLstTipoDocumento() {
		return lstTipoDocumento;
	}

	public void setLstTipoDocumento(List<SelectItem> lstTipoDocumento) {
		this.lstTipoDocumento = lstTipoDocumento;
	}
	
	public int getTipoDescargo() {
		return tipoDescargo;
	}

	public void setTipoDescargo(int tipoDescargo) {
		this.tipoDescargo = tipoDescargo;
	}

	public Integer getTipoDocumentoId() {
		return tipoDocumentoId;
	}

	public void setTipoDocumentoId(Integer tipoDocumentoId) {
		this.tipoDocumentoId = tipoDocumentoId;
	}

	public String getNroDocumento() {
		return nroDocumento;
	}

	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}

	public Date getFechaDocumento() {
		return fechaDocumento;
	}

	public void setFechaDocumento(Date fechaDocumento) {
		this.fechaDocumento = fechaDocumento;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public DescargoDEManaged(){
		getSessionManaged().setLinkRegresar("/sisat/papeletas/descargopapeletas.xhtml");
	}

	public BigDecimal getTotalPrescribir() {
		return totalPrescribir;
	}

	public void setTotalPrescribir(BigDecimal totalPrescribir) {
		this.totalPrescribir = totalPrescribir;
	}
	
	public BigDecimal getTotalCompensar() {
		return totalCompensar;
	}


	public void setTotalCompensar(BigDecimal totalCompensar) {
		this.totalCompensar = totalCompensar;
	}	

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public BigDecimal getTotalDescargo() {
		return totalDescargo;
	}

	public void setTotalDescargo(BigDecimal totalDescargo) {
		this.totalDescargo = totalDescargo;
	}

	public BigDecimal getMontoACompensar() {
		return montoACompensar;
	}

	public void setMontoACompensar(BigDecimal montoACompensar) {
		this.montoACompensar = montoACompensar;
	}
	
	public String getNumeroPapeleta() {
		return numeroPapeleta;
	}

	public void setNumeroPapeleta(String numeroPapeleta) {
		this.numeroPapeleta = numeroPapeleta;
	}
}
