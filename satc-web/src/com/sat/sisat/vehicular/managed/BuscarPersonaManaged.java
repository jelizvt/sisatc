package com.sat.sisat.vehicular.managed;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.FacesUtil;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.papeletas.managed.BuscarPapeletasManaged;
import com.sat.sisat.papeletas.managed.RecordInfraccionInfractorManaged;
import com.sat.sisat.papeletas.managed.RegistroPapeletasManaged;
import com.sat.sisat.persistence.entity.MpTipoDocuIdentidad;
import com.sat.sisat.persona.business.PersonaBoRemote;
import com.sat.sisat.predial.managed.DescargoPredialManaged;
import com.sat.sisat.predial.managed.RegistroPredioManaged;
import com.sat.sisat.vehicular.business.VehicularBoRemote;
import com.sat.sisat.vehicular.cns.ReusoFormCns;
import com.sat.sisat.vehicular.dto.BuscarPersonaDTO;

@ManagedBean
@ViewScoped
public class BuscarPersonaManaged extends BaseManaged {

	private static final long serialVersionUID = 6860634771670968735L;

	@EJB
	PersonaBoRemote personaBo;

	@EJB
	VehicularBoRemote vehicularBo;

	private int selectedOptBusc = 4;
	private Integer codigoPersBusc;
	private String apePatBusc;
	private String apeMatBusc;
	private String nombresBusc;
	private String razonSocialBusc;

	private List<SelectItem> lstTipoDoc = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapMpTipoDocuIdentidad = new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIMpTipoDocuIdentidad = new HashMap<Integer, String>();
	private String selectedTipoDocBusc;

	private String nroDocBusc;

	private List<BuscarPersonaDTO> lstPersonas;

	// Variables reusabilidad
	private String pantallaUso;
	private String destinoRefresh;

	
	public BuscarPersonaManaged() {

	}

	@PostConstruct
	public void init() {
		try {
			List<MpTipoDocuIdentidad> lstTD = new ArrayList<MpTipoDocuIdentidad>();
			lstTD = personaBo.getAllMpTipoDocumento();
			Iterator<MpTipoDocuIdentidad> itTD = lstTD.iterator();
			while (itTD.hasNext()) {
				MpTipoDocuIdentidad objTD = itTD.next();
				lstTipoDoc.add(new SelectItem(objTD.getDescrpcionCorta()));
				mapMpTipoDocuIdentidad.put(objTD.getDescrpcionCorta(),objTD.getTipoDocIdentidadId());
				mapIMpTipoDocuIdentidad.put(objTD.getTipoDocIdentidadId(),objTD.getDescrpcionCorta());
			}
		} catch (Exception ex) {
			// TODO : Controlar excepción
			System.out.println("ERROR: " + ex);
		}
	}

	public NuevadjRegistroManaged getNuevadjRegistroManaged() {
		return (NuevadjRegistroManaged) getManaged("nuevadjRegistroManaged");
	}

	public DescargoVehicularManaged getDescargoVehicularManaged() {
		return (DescargoVehicularManaged) getManaged("descargoVehicularManaged");
	}
	public DescargoPredialManaged getDescargoPredialManaged() {
		return (DescargoPredialManaged) getManaged("descargoPredialManaged");
	}

	public void changeOpcionBusc(ActionEvent ev) {
		limpiarBusc();
	}

	private void limpiarBusc() {
		codigoPersBusc=null;
		apePatBusc = null;
		apeMatBusc = null;
		nombresBusc = null;
		razonSocialBusc = null;
		selectedTipoDocBusc = null;
		nroDocBusc = null;
	}

	public void buscarPersona() {
		buscar();
	}
	//cchaucca:ini 28/05/12
	public void buscar() {
		try {
			lstPersonas = new ArrayList<BuscarPersonaDTO>();
			if (selectedOptBusc == 4||selectedOptBusc == 1) {
				lstPersonas = personaBo.findPersona(codigoPersBusc,apePatBusc, apeMatBusc,nombresBusc);
			} 
			 else if (selectedOptBusc == 2) {
				lstPersonas = personaBo.findPersona(razonSocialBusc);
			} else {
				int tipoDocIdenti = -1;
				if (selectedTipoDocBusc != null&&selectedTipoDocBusc.trim().length()>0) {
					tipoDocIdenti = mapMpTipoDocuIdentidad.get(selectedTipoDocBusc);
				}
				lstPersonas = personaBo.findPersona(tipoDocIdenti, nroDocBusc);
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void buscarPersona(String tipoDocumento,String numeroDocumento){
		setSelectedOptBusc(3);
		setSelectedTipoDocBusc(tipoDocumento);
		setNroDocBusc(numeroDocumento);
		buscar();
	}
	
	//cchaucca:fin 28/05/12
		
	public void seleccionarPersona(ActionEvent ev) {
		try {
			UIComponent comp = ev.getComponent().getParent().getParent();
			if (comp != null) {
				UIData uiData = (UIData) comp;
				BuscarPersonaDTO bpd = (BuscarPersonaDTO) uiData.getRowData();
				if (pantallaUso.equals(ReusoFormCns.REGISTRO_VEHICULAR)) {
					if (!getNuevadjRegistroManaged().existeTransfEnLista(
							bpd.getPersonaId())) {
						getNuevadjRegistroManaged().getLstTransferentes().add(
								bpd);
					}
				} else if (pantallaUso.equals(ReusoFormCns.DESCARGO_VEHICULAR)) {
					if (!getDescargoVehicularManaged().existeTransfEnLista(
							bpd.getPersonaId())) {
						bpd.setPorcentaje(BigDecimal.ZERO);
						getDescargoVehicularManaged().getLstTransferentes().add(bpd);
						// getDescargoVehicularManaged().igualarPorcentajes();
					}
				}
				//03/05/2012 cchaucca :ini
				else if (pantallaUso!=null&&pantallaUso.equals(ReusoFormCns.DESCARGO_PREDIAL)) {
					//Permitir añadir el mismo comprador en descargo predial					
					if (!getDescargoPredialManaged().existeTransfEnLista(bpd.getPersonaId())) {
						getDescargoPredialManaged().getLstTransferentes().add(bpd);
					}else{
						BuscarPersonaDTO bpd_existente = new BuscarPersonaDTO(); 
						
						bpd_existente.setPersonaId(bpd.getPersonaId());
						bpd_existente.setApellidoPaterno(bpd.getApellidoPaterno());
						bpd_existente.setApellidoMaterno(bpd.getApellidoMaterno());
						bpd_existente.setApellidosNombres(bpd.getApellidosNombres());
						bpd_existente.setRazonSocial(bpd.getRazonSocial());
						bpd_existente.setTipoDocIdentidad(bpd.getTipoDocIdentidad());
						bpd_existente.setTipoDocIdentidadId(bpd.getTipodocumentoIdentidadId());
						bpd_existente.setNroDocuIdentidad(bpd.getNroDocuIdentidad());
						
						getDescargoPredialManaged().getLstTransferentes().add(bpd_existente);
					}
				}
				else if (pantallaUso!=null&&pantallaUso.equals(ReusoFormCns.REGISTRO_PAPELETAS)) {
					if(!bpd.getTipodocumentoIdentidadId().equals(Constante.TIPO_DOCUMENTO_RUC_ID)){
						String personaPapeleta=(String)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "personaPapeleta");
						RegistroPapeletasManaged registroPapeletasManaged=(RegistroPapeletasManaged)getManaged("registroPapeletasManaged");
						registroPapeletasManaged.copiaPersona(bpd,personaPapeleta);	
					}else{
						WebMessages.messageError("Infractor no puede ser Persona Jurídica");
					}
				}
				else if (pantallaUso!=null&&pantallaUso.equals(ReusoFormCns.REGISTRO_PREDIAL)) {
					RegistroPredioManaged registroPredialManaged=(RegistroPredioManaged)getManaged("registroPredioManaged");
					registroPredialManaged.setTransferente(bpd);
				}
				//03/05/2012 cchaucca :fin
				else if (pantallaUso!=null&&pantallaUso.equals(ReusoFormCns.RECORD_INFR_PAPELETAS)) {
					if(!bpd.getTipodocumentoIdentidadId().equals(Constante.TIPO_DOCUMENTO_RUC_ID)){
						String personaPapeleta=(String)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "personaPapeleta");
						RecordInfraccionInfractorManaged recordInfractorManaged=(RecordInfraccionInfractorManaged)getManaged("recordInfraccionInfractorManaged");
						recordInfractorManaged.copiaPersona(bpd,personaPapeleta);	
					}else{
						WebMessages.messageError("Infractor no puede ser Persona Jurídica");
					}
				}
				else if (pantallaUso!=null&&pantallaUso.equals(ReusoFormCns.BUSQU_INFR_PAPELETAS)) {
					if(!bpd.getTipodocumentoIdentidadId().equals(Constante.TIPO_DOCUMENTO_RUC_ID)){
						BuscarPapeletasManaged recordInfractorManaged=(BuscarPapeletasManaged)getManaged("buscarPapeletasManaged");
						recordInfractorManaged.copiaPersona(bpd);	
					}else{
						WebMessages.messageError("Infractor no puede ser Persona Jurídica");
					}
				}
			}
		} catch (Exception ex) {
			// TODO : controlar excepción
		}
	}

	public int getSelectedOptBusc() {
		return selectedOptBusc;
	}

	public void setSelectedOptBusc(int selectedOptBusc) {
		this.selectedOptBusc = selectedOptBusc;
	}

	public String getApePatBusc() {
		return apePatBusc;
	}

	public void setApePatBusc(String apePatBusc) {
		this.apePatBusc = apePatBusc.trim();
	}

	public String getApeMatBusc() {
		return apeMatBusc;
	}

	public void setApeMatBusc(String apeMatBusc) {
		this.apeMatBusc = apeMatBusc.trim();
	}

	public String getNombresBusc() {
		return nombresBusc;
	}

	public void setNombresBusc(String nombresBusc) {
		this.nombresBusc = nombresBusc.trim();
	}

	public String getRazonSocialBusc() {
		return razonSocialBusc;
	}

	public void setRazonSocialBusc(String razonSocialBusc) {
		this.razonSocialBusc = razonSocialBusc;
	}

	public String getSelectedTipoDocBusc() {
		return selectedTipoDocBusc;
	}

	public void setSelectedTipoDocBusc(String selectedTipoDocBusc) {
		this.selectedTipoDocBusc = selectedTipoDocBusc;
	}

	public String getNroDocBusc() {
		return nroDocBusc;
	}

	public void setNroDocBusc(String nroDocBusc) {
		this.nroDocBusc = nroDocBusc;
	}

	public List<SelectItem> getLstTipoDoc() {
		return lstTipoDoc;
	}

	public List<BuscarPersonaDTO> getLstPersonas() {
		return lstPersonas;
	}

	public String getPantallaUso() {
		return pantallaUso;
	}

	public void setPantallaUso(String pantallaUso) {
		this.pantallaUso = pantallaUso;
	}

	public String getDestinoRefresh() {
		return destinoRefresh;
	}

	public void setDestinoRefresh(String destinoRefresh) {
		this.destinoRefresh = destinoRefresh;
	}

	public Integer getCodigoPersBusc() {
		return codigoPersBusc;
	}

	public void setCodigoPersBusc(Integer codigoPersBusc) {
		this.codigoPersBusc = codigoPersBusc;
	}



}
