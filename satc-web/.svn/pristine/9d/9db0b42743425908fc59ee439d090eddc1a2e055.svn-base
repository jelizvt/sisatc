package com.sat.sisat.fiscalizacion.managed;

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
import com.sat.sisat.fiscalizacion.business.FiscalizacionBoRemote;
import com.sat.sisat.papeletas.managed.BuscarPapeletasManaged;
import com.sat.sisat.papeletas.managed.RecordInfraccionInfractorManaged;
import com.sat.sisat.papeletas.managed.RegistroDosajeEtilicoManaged;
import com.sat.sisat.papeletas.managed.RegistroPapeletasManaged;
import com.sat.sisat.persistence.entity.MpTipoDocuIdentidad;
import com.sat.sisat.persona.business.PersonaBoRemote;
import com.sat.sisat.predial.managed.DescargoPredialManaged;
import com.sat.sisat.vehicular.cns.ReusoFormCns;
import com.sat.sisat.vehicular.dto.BuscarPersonaDTO;
import com.sat.sisat.vehicular.managed.DescargoVehicularManaged;
import com.sat.sisat.vehicular.managed.NuevadjRegistroManaged;

@ManagedBean
@ViewScoped
public class BuscarRequerimientoContribuyenteManaged extends BaseManaged {

	private static final long serialVersionUID = 6860634771670968735L;

	@EJB
	PersonaBoRemote personaBo;

	@EJB
	FiscalizacionBoRemote fiscalizarBo;
	

	private int selectedOptBusc = 4;
	private Integer codigoPersBusc;
	
	private String apeNom;

	private List<SelectItem> lstTipoDoc = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapMpTipoDocuIdentidad = new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIMpTipoDocuIdentidad = new HashMap<Integer, String>();
	private String selectedTipoDocBusc;

	private String nroDocBusc;

	private List<BuscarPersonaDTO> listarPersonas;

	// Variables reusabilidad
	private String pantallaUso;
	private String destinoRefresh;
	
	private BuscarPersonaDTO personaDTO;

	public BuscarRequerimientoContribuyenteManaged() {
		
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
			setSelectedTipoDocBusc("DNI");
		} catch (Exception ex) {
			// TODO : Controlar excepción
			System.out.println("ERROR: " + ex);
		}
	}

	public void changeOpcionBusc(ActionEvent ev) {
		limpiarBusc();
	}

	private void limpiarBusc() {
		codigoPersBusc=null;
		apeNom = null;
		selectedTipoDocBusc = null;
		nroDocBusc = null;
		setSelectedTipoDocBusc("DNI");
	}

	public void buscarPersona() {
		buscar();
	}
	
	public void buscar() {
		try {
			listarPersonas = new ArrayList<BuscarPersonaDTO>();
			if (selectedOptBusc == 4||selectedOptBusc == 1) {
				listarPersonas = fiscalizarBo.findPersona(codigoPersBusc,apeNom);
			} 
			else {
				int tipoDocIdenti = -1;
				if (selectedTipoDocBusc != null&&selectedTipoDocBusc.trim().length()>0) {
					tipoDocIdenti = mapMpTipoDocuIdentidad.get(selectedTipoDocBusc);
				}
				listarPersonas = fiscalizarBo.findPersona(tipoDocIdenti, nroDocBusc);
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
	

	public void seleccionarPersona(ActionEvent ev) {
		try {
			UIComponent comp = ev.getComponent().getParent().getParent();
			if (comp != null) {
				UIData uiData = (UIData) comp;
				BuscarPersonaDTO bpd = (BuscarPersonaDTO) uiData.getRowData();
									
				
				if (pantallaUso!=null&&pantallaUso.equals(ReusoFormCns.BUSQU_PER_INSPECCION)) {
					
					
					   BusquedaRequerimientoManaged busquedaReqManaged=(BusquedaRequerimientoManaged)getManaged("busquedaRequerimientoManaged");
					   busquedaReqManaged.copiaPersona(bpd);
						

						//controlReqManaged.setOcultarBoton(false);

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

	public List<BuscarPersonaDTO> getListarPersonas() {
		return listarPersonas;
	}

	public void setListarPersonas(List<BuscarPersonaDTO> listarPersonas) {
		this.listarPersonas = listarPersonas;
	}

	public PersonaBoRemote getPersonaBo() {
		return personaBo;
	}

	public void setPersonaBo(PersonaBoRemote personaBo) {
		this.personaBo = personaBo;
	}

	public BuscarPersonaDTO getPersonaDTO() {
		return personaDTO;
	}

	public void setPersonaDTO(BuscarPersonaDTO personaDTO) {
		this.personaDTO = personaDTO;
	}

}
