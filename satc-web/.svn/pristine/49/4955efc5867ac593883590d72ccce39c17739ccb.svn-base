package com.sat.sisat.tramitedocumentario.managed.popup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.persistence.entity.MpTipoDocuIdentidad;
import com.sat.sisat.persona.business.PersonaBoRemote;
import com.sat.sisat.persona.dto.ParamBusquedaPersonaDTO;
import com.sat.sisat.tramitedocumentario.managed.RegistroTramiteManaged;
import com.sat.sisat.vehicular.business.VehicularBoRemote;
import com.sat.sisat.vehicular.dto.BuscarPersonaDTO;

@ManagedBean
@ViewScoped
public class BuscarPersonaPopupManaged extends BaseManaged {

	private static final long serialVersionUID = 6860634771670968735L;

	@EJB
	PersonaBoRemote personaBo;

	@EJB
	VehicularBoRemote vehicularBo;

	private int selectedOptBusc = 1;

	private String apePatBusc;
	private String apeMatBusc;
	private String nombresBusc;
	private String razonSocialBusc;

	private List<SelectItem> lstTipoDoc = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapMpTipoDocuIdentidad = new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIMpTipoDocuIdentidad = new HashMap<Integer, String>();
	private String selectedTipoDocBusc;

	private String nroDocBusc;

	private List<BuscarPersonaDTO> lstPersonas = new ArrayList<BuscarPersonaDTO>();

	private BuscarPersonaDTO personaDTOSeleccionada;
	
	private ParamBusquedaPersonaDTO paramBusquedaPersonaDTO = new ParamBusquedaPersonaDTO();
	

	// Variables reusabilidad
	private String pantallaUso;
	private String destinoRefresh;

	public BuscarPersonaPopupManaged() {

	}

	@PostConstruct
	public void init() {
		try {

			System.out.println(this.getUser());

			List<MpTipoDocuIdentidad> lstTD = new ArrayList<MpTipoDocuIdentidad>();
			lstTD = personaBo.getAllMpTipoDocumento();
			Iterator<MpTipoDocuIdentidad> itTD = lstTD.iterator();
			while (itTD.hasNext()) {
				MpTipoDocuIdentidad objTD = itTD.next();
				lstTipoDoc.add(new SelectItem(objTD.getDescrpcionCorta()));
				mapMpTipoDocuIdentidad.put(objTD.getDescrpcionCorta(), objTD.getTipoDocIdentidadId());
				mapIMpTipoDocuIdentidad.put(objTD.getTipoDocIdentidadId(), objTD.getDescrpcionCorta());
			}
		} catch (Exception ex) {
			WebMessages.messageError(ex.getMessage());
		}
	}

	public void changeOpcionBusc(ActionEvent ev) {
		limpiarBusc();
	}

	private void limpiarBusc() {
		apePatBusc = null;
		apeMatBusc = null;
		nombresBusc = null;
		razonSocialBusc = null;
		selectedTipoDocBusc = null;
		nroDocBusc = null;
	}

	public void buscar() {
		try {
			lstPersonas.clear();

			lstPersonas = personaBo.busquedaPersona(paramBusquedaPersonaDTO);

		} catch (Exception ex) {
			WebMessages.messageError(ex.getMessage());
		}
	}

	public void buscarPersona(String tipoDocumento, String numeroDocumento) {
		setSelectedOptBusc(3);
		setSelectedTipoDocBusc(tipoDocumento);
		setNroDocBusc(numeroDocumento);
		buscar();
	}

	public void seleccionarPersona() {

		RegistroTramiteManaged registroTramiteManaged = (RegistroTramiteManaged) getManaged("registroTramiteManaged");
		
		registroTramiteManaged.getTdRepresentante().setApellidosNombres(personaDTOSeleccionada.getApellidosNombres());
		registroTramiteManaged.getTdRepresentante().setDni(personaDTOSeleccionada.getNroDocuIdentidad());		
		registroTramiteManaged.getTdRepresentante().setDireccion(personaDTOSeleccionada.getDireccionCompleta());
		
	}
	
	public void limpiar(){
		
		this.paramBusquedaPersonaDTO = new ParamBusquedaPersonaDTO();
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
		this.apePatBusc = apePatBusc;
	}

	public String getApeMatBusc() {
		return apeMatBusc;
	}

	public void setApeMatBusc(String apeMatBusc) {
		this.apeMatBusc = apeMatBusc;
	}

	public String getNombresBusc() {
		return nombresBusc;
	}

	public void setNombresBusc(String nombresBusc) {
		this.nombresBusc = nombresBusc;
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

	public BuscarPersonaDTO getPersonaDTOSeleccionada() {
		return personaDTOSeleccionada;
	}

	public void setPersonaDTOSeleccionada(BuscarPersonaDTO personaDTOSeleccionada) {
		this.personaDTOSeleccionada = personaDTOSeleccionada;
	}

	public ParamBusquedaPersonaDTO getParamBusquedaPersonaDTO() {
		return paramBusquedaPersonaDTO;
	}

	public void setParamBusquedaPersonaDTO(ParamBusquedaPersonaDTO paramBusquedaPersonaDTO) {
		this.paramBusquedaPersonaDTO = paramBusquedaPersonaDTO;
	}


}
