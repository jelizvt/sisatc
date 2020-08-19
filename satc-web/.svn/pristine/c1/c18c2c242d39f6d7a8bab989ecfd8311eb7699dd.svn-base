package com.sat.sisat.papeletas.managed;

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

import com.sat.sisat.coactiva.managed.RemateVehiculosManaged;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.FacesUtil;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.persistence.entity.MpTipoDocuIdentidad;
import com.sat.sisat.persona.business.PersonaBoRemote;
import com.sat.sisat.predial.managed.DescargoPredialManaged;
import com.sat.sisat.predial.managed.RegistroPredioManaged;
import com.sat.sisat.vehicular.business.VehicularBoRemote;
import com.sat.sisat.vehicular.cns.ReusoFormCns;
import com.sat.sisat.vehicular.dto.BuscarPersonaDTO;
import com.sat.sisat.vehicular.managed.DescargoVehicularManaged;
import com.sat.sisat.vehicular.managed.NuevadjRegistroManaged;

@ManagedBean
@ViewScoped
public class BuscarPersonaPapeletasManaged extends BaseManaged {

	private static final long serialVersionUID = 6860634771670968735L;

	@EJB
	PersonaBoRemote personaBo;

	@EJB
	VehicularBoRemote vehicularBo;

	private int selectedOptBusc = 4;
	private Integer codigoPersBusc;

	private String apeNom;

	private List<SelectItem> lstTipoDoc = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapMpTipoDocuIdentidad = new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIMpTipoDocuIdentidad = new HashMap<Integer, String>();
	private String selectedTipoDocBusc;

	private String nroDocBusc;

	private List<BuscarPersonaDTO> lstPersonas;

	// Variables reusabilidad
	private String pantallaUso;
	private String destinoRefresh;

	public BuscarPersonaPapeletasManaged() {

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
				mapMpTipoDocuIdentidad.put(objTD.getDescrpcionCorta(),
						objTD.getTipoDocIdentidadId());
				mapIMpTipoDocuIdentidad.put(objTD.getTipoDocIdentidadId(),
						objTD.getDescrpcionCorta());
			}
			setSelectedTipoDocBusc("DNI");
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
		codigoPersBusc = null;
		apeNom = null;
		selectedTipoDocBusc = null;
		nroDocBusc = null;
		setSelectedTipoDocBusc("DNI");
	}

	public void buscarPersona() {
		buscar();
	}

	// cchaucca:ini 28/05/12
	public void buscar() {
		try {
			lstPersonas = new ArrayList<BuscarPersonaDTO>();
			if (selectedOptBusc == 4 || selectedOptBusc == 1) {
				lstPersonas = personaBo.findPersonaPapeletas(codigoPersBusc,
						apeNom);
			} else {
				int tipoDocIdenti = -1;
				if (selectedTipoDocBusc != null
						&& selectedTipoDocBusc.trim().length() > 0) {
					tipoDocIdenti = mapMpTipoDocuIdentidad
							.get(selectedTipoDocBusc);
				}
				lstPersonas = personaBo.findPersonaPapeletas(tipoDocIdenti,
						nroDocBusc);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void buscarPersona(String tipoDocumento, String numeroDocumento) {
		setSelectedOptBusc(3);
		setSelectedTipoDocBusc(tipoDocumento);
		setNroDocBusc(numeroDocumento);
		buscar();
	}

	// cchaucca:fin 28/05/12

	public void seleccionarPersona(ActionEvent ev) {
		try {
			UIComponent comp = ev.getComponent().getParent().getParent();
			if (comp != null) {
				UIData uiData = (UIData) comp;
				BuscarPersonaDTO bpd = (BuscarPersonaDTO) uiData.getRowData();
				// Correccion de direcciones de propietario
				if (pantallaUso != null
						&& pantallaUso.equals(ReusoFormCns.REGISTRO_PAPELETAS)) {
					String personaPapeleta = (String) getSessionMap().get(
							"personaPapeleta");
					if (personaPapeleta != null && personaPapeleta.equals("I")) {
						if (!bpd.getTipodocumentoIdentidadId().equals(
								Constante.TIPO_DOCUMENTO_RUC_ID)) {
							// String
							// personaPapeleta=(String)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(),
							// "personaPapeleta");
							RegistroPapeletasManaged registroPapeletasManaged = (RegistroPapeletasManaged) getManaged("registroPapeletasManaged");
							registroPapeletasManaged.copiaPersona(bpd,
									personaPapeleta);
						} else {
							WebMessages
									.messageError("Infractor no puede ser Persona Jurídica");
						}
					} else if (personaPapeleta != null
							&& personaPapeleta.equals("P")) {
						RegistroPapeletasManaged registroPapeletasManaged = (RegistroPapeletasManaged) getManaged("registroPapeletasManaged");
						registroPapeletasManaged.copiaPersona(bpd,
								personaPapeleta);
					}
				}

				else if (pantallaUso != null
						&& pantallaUso
								.equals(ReusoFormCns.RECORD_INFR_PAPELETAS)) {
					if (!bpd.getTipodocumentoIdentidadId().equals(
							Constante.TIPO_DOCUMENTO_RUC_ID)) {
						String personaPapeleta = (String) FacesUtil
								.getSessionMapValue(
										FacesContext.getCurrentInstance(),
										"personaPapeleta");
						RecordInfraccionInfractorManaged recordInfractorManaged = (RecordInfraccionInfractorManaged) getManaged("recordInfraccionInfractorManaged");
						recordInfractorManaged.copiaPersona(bpd,
								personaPapeleta);
					} else {
						WebMessages
								.messageError("Infractor no puede ser Persona Jurídica");
					}
				} else if (pantallaUso != null
						&& pantallaUso
								.equals(ReusoFormCns.DOSAJE_INFR_PAPELETAS)) {
					if (!bpd.getTipodocumentoIdentidadId().equals(
							Constante.TIPO_DOCUMENTO_RUC_ID)) {
						RegistroDosajeEtilicoManaged registroDosajeEtilicoManaged = (RegistroDosajeEtilicoManaged) getManaged("registroDosajeEtilicoManaged");
						registroDosajeEtilicoManaged.copiaPersona(bpd);
					} else {
						WebMessages
								.messageError("Infractor no puede ser Persona Jurídica");
					}
				} else if (pantallaUso != null
						&& pantallaUso
								.equals(ReusoFormCns.BUSQU_INFR_PAPELETAS)) {
					if (!bpd.getTipodocumentoIdentidadId().equals(
							Constante.TIPO_DOCUMENTO_RUC_ID)) {
						BuscarPapeletasManaged recordInfractorManaged = (BuscarPapeletasManaged) getManaged("buscarPapeletasManaged");
						recordInfractorManaged.copiaPersona(bpd);
					} else {
						WebMessages
								.messageError("Infractor no puede ser Persona Jurídica");
					}
				} else if (pantallaUso != null
						&& pantallaUso
								.equals(ReusoFormCns.BUSQU_PER_REMATE_VEHICULO)) {
					if (!bpd.getTipodocumentoIdentidadId().equals(
							Constante.TIPO_DOCUMENTO_RUC_ID)) {
						RemateVehiculosManaged remateVehiculosManaged = (RemateVehiculosManaged) getManaged("remateVehiculosManaged");
						remateVehiculosManaged.copiaPersona(bpd);
					} else {
						WebMessages
								.messageError("Infractor no puede ser Persona Jurídica");
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public int getSelectedOptBusc() {
		return selectedOptBusc;
	}

	public void setSelectedOptBusc(int selectedOptBusc) {
		this.selectedOptBusc = selectedOptBusc;
	}

	public String getApeNom() {
		return apeNom;
	}

	public void setApeNom(String apeNom) {
		this.apeNom = apeNom.trim();
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
