package com.sat.sisat.papeletas.managed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.richfaces.component.html.HtmlComboBox;
import org.richfaces.component.html.HtmlInputText;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.ValidateInput;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.papeleta.business.PapeletaBoRemote;
import com.sat.sisat.persistence.entity.MpTipoDocuIdentidad;
import com.sat.sisat.persistence.entity.PaPersona;
import com.sat.sisat.persistence.entity.TgPersona;
import com.sat.sisat.persona.business.PersonaBoRemote;
import com.sat.sisat.vehicular.dto.BuscarPersonaDTO;

@ManagedBean
@ViewScoped
public class ActualizaPersonasDosajeEtilicoManaged extends BaseManaged {
	
	@EJB
	PapeletaBoRemote papeletaBo;
	
	@EJB
	PersonaBoRemote personaBo;
	
	private String tipoDocumento;
	private String numDocumentPersona;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String primerNombre;
	private String segundoNombre;
	private String razonSocial;
	private Integer personaId;
	
	private List<SelectItem> lstTipoDocumento = new ArrayList<SelectItem>();
	private Map<String,Integer> mapTipoDocumento = new HashMap<String,Integer>();
	private Map<Integer,String> mapITipoDocumento = new HashMap<Integer,String>();
	private Boolean isPersonaNatural;
	
	private HtmlComboBox cmbTipoDocumento = new HtmlComboBox();
	private HtmlInputText txtNumDocumento = new HtmlInputText();
	private HtmlInputText txtApePaterno = new HtmlInputText();
	private HtmlInputText txtApeMaterno = new HtmlInputText();
	private HtmlInputText txtPrimerNombre = new HtmlInputText();
	private HtmlInputText txtSegundoNombre = new HtmlInputText();
	private HtmlInputText txtRazonSocial  = new HtmlInputText();
	
	private String maxlengthNroDocumento;
	
	private String actualizaPersona;
	
	public ActualizaPersonasDosajeEtilicoManaged(){
		
	}
	
	@PostConstruct
	public void cargaPaginaRegistro(){
		try {
			List<MpTipoDocuIdentidad> lstTD = new ArrayList<MpTipoDocuIdentidad>();
			lstTD = papeletaBo.getAllMpTipoDocumento();
			Iterator<MpTipoDocuIdentidad> itTD = lstTD.iterator();
			while (itTD.hasNext()) {
				MpTipoDocuIdentidad objTD = itTD.next();
				lstTipoDocumento.add(new SelectItem(objTD.getDescrpcionCorta()));
				mapTipoDocumento.put(objTD.getDescrpcionCorta(),objTD.getTipoDocIdentidadId());
				mapITipoDocumento.put(objTD.getTipoDocIdentidadId(), objTD.getDescrpcionCorta());
			}
			isPersonaNatural = Boolean.TRUE;
			getCmbTipoDocumento().setValue(Constante.TIPO_DOCUMENTO_DNI);
			maxlengthNroDocumento="8";
			limpiar();
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private Boolean sinDocumento;
	
	public void selectTipoDocumento(ValueChangeEvent e){
		HtmlComboBox combo = (HtmlComboBox) e.getComponent();
		if(combo.getValue().toString().equals("RUC")){
			isPersonaNatural = Boolean.FALSE;
		}else{
			isPersonaNatural = Boolean.TRUE;
		}
		
		if(combo.getValue().toString().equals("DNI")){
			maxlengthNroDocumento="8";
		}else if(combo.getValue().toString().equals("RUC")){
			maxlengthNroDocumento="11";
		}else{
			maxlengthNroDocumento="15";
		}
		
		if(combo.getValue().toString().equals("SIN DOCUM.")){
			setNumDocumentPersona("-");
			txtNumDocumento.setValue("-");
			setSinDocumento(Boolean.TRUE);
		}else{
			setNumDocumentPersona("");
			txtNumDocumento.setValue("");
			setSinDocumento(Boolean.FALSE);
		}
	}
	
	public void buscarPersonaDNI(){
		try{
			if(getCmbTipoDocumento().getValue()!=null&&getCmbTipoDocumento().getValue().equals(Constante.TIPO_DOCUMENTO_DNI)){
				if(getTxtNumDocumento().getSubmittedValue()!=null&&getTxtNumDocumento().getSubmittedValue().toString().length()==8){
					TgPersona persona=personaBo.buscarPersonaReniec(getTxtNumDocumento().getSubmittedValue().toString());	
					if(persona!=null){
						//getTxtPrimerNombre().setValue(persona.getNombre());
						String[] temp=persona.getNombre().split("\\s+");
						getTxtPrimerNombre().setValue(temp.length>0?temp[0]:"");
						getTxtSegundoNombre().setValue(temp.length>1?temp[1]:"");
						
						getTxtApePaterno().setValue(persona.getApe_pat());
						getTxtApeMaterno().setValue(persona.getApe_mat());
						
						setPrimerNombre(temp.length>0?temp[0]:"");
						setSegundoNombre(temp.length>1?temp[1]:"");
						
						setApellidoPaterno(persona.getApe_pat());
						setApellidoMaterno(persona.getApe_mat());
					}else{
						//limpiar();
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void limpiar(){
		getCmbTipoDocumento().setValue(Constante.TIPO_DOCUMENTO_DNI);
		getTxtApeMaterno().setValue("");
		getTxtApePaterno().setValue("");
		getTxtNumDocumento().setValue("");
		getTxtPrimerNombre().setValue("");
		getTxtRazonSocial().setValue("");
		getTxtSegundoNombre().setValue("");
		
		setApellidoMaterno("");
		setApellidoPaterno("");
		setNumDocumentPersona("");
		setPrimerNombre("");
		setRazonSocial("");
		setSegundoNombre("");
	}

	public void registrarPersona(){
		try{
			if(validaNumDocumento(getNumDocumentPersona())){
				if(validaApellidosNombres()){
					PaPersona persona=new PaPersona();
					if(getActualizaPersona()!=null&&getActualizaPersona().equals("N")){
						persona.setPersonaId(Constante.RESULT_PENDING);//Es nuevo registro
					}else{
						persona.setPersonaId(getPersonaId()==null?Constante.RESULT_PENDING:getPersonaId());	
					}
					
					persona.setTipoDocumentoId(Integer.valueOf(mapTipoDocumento.get(getTipoDocumento())));
					persona.setNroDocIdentidad(getNumDocumentPersona());
					persona.setPrimerNombre(getPrimerNombre());
					persona.setSegundoNombre(getSegundoNombre());
					persona.setApePaterno(getApellidoPaterno());
					persona.setApeMaterno(getApellidoMaterno());
				
					BuscarPersonaDTO personaDto=getPersonaDto(persona);
					String personaPapeleta = (String)getSessionMap().get("personaPapeleta");
					if(personaPapeleta!=null&&personaPapeleta.equals("I")){
						RegistroDosajeEtilicoManaged registro = (RegistroDosajeEtilicoManaged) getManaged("registroDosajeEtilicoManaged");
						registro.setDatosInfractor(personaDto);	
					}
					limpiar();
				}else{
					WebMessages.messageError("Apellidos y nombres no valido");
				}
			}else{
				WebMessages.messageError("Número de documento de identidad no valido");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean validaNumDocumento(String valorCampo){
		if(valorCampo!=null){
			if(valorCampo.trim().compareTo("S/N")==0){
				return true;
			}else{
				if(getTipoDocumento().trim().equals("DNI")&&valorCampo.trim().length()==8){
					return true;
				}else if(getTipoDocumento().trim().equals("RUC")&&valorCampo.trim().length()==11){
					return true;
				}else if(!getTipoDocumento().trim().equals("DNI")&&!getTipoDocumento().trim().equals("RUC")&&valorCampo.trim().length()<=15){
					return true;
				}else{
					return false;
				}
			}
		}else{
			return false;
		}
	}
	
	public boolean validaApellidosNombres(){
		if(ValidateInput.validateFirstName(getPrimerNombre().trim())&&
				ValidateInput.validateLastName(getApellidoPaterno().trim())&&
				ValidateInput.validateLastName(getApellidoMaterno().trim())){
			if(getSegundoNombre()!=null&&getSegundoNombre().trim().length()>0){
				if(ValidateInput.validateFirstName(getSegundoNombre().trim())){
					return true;
				}else{
					return false;
				}
			}else{
				return true;
			}
		}else{
			return false;
		}
	}
	
	public BuscarPersonaDTO getPersonaDto(PaPersona persona){
		BuscarPersonaDTO personaDto=new BuscarPersonaDTO();
		personaDto.setPersonaId(persona.getPersonaId());
		personaDto.setTipoDocIdentidad(mapITipoDocumento.get(persona.getTipoDocumentoId()));
		personaDto.setTipodocumentoIdentidadId(persona.getTipoDocumentoId());
		personaDto.setNroDocuIdentidad(persona.getNroDocIdentidad());
		personaDto.setApellidosNombres(persona.getApePaterno()+" "+persona.getApeMaterno()+" "+persona.getPrimerNombre()+" "+persona.getSegundoNombre());
		personaDto.setApellidoPaterno(persona.getApePaterno());
		personaDto.setApellidoMaterno(persona.getApeMaterno());
		personaDto.setPrimerNombre(persona.getPrimerNombre());
		personaDto.setSegundoNombre(persona.getSegundoNombre());
		return personaDto;
	}
	
	public void setProperty(BuscarPersonaDTO persona){
		if(persona!=null){
			getCmbTipoDocumento().setValue(persona.getTipoDocIdentidad());
			getTxtApeMaterno().setValue(persona.getApellidoMaterno());
			getTxtApePaterno().setValue(persona.getApellidoPaterno());
			getTxtNumDocumento().setValue(persona.getNroDocuIdentidad());
			getTxtPrimerNombre().setValue(persona.getPrimerNombre());
			getTxtRazonSocial().setValue(persona.getRazonSocial());
			getTxtSegundoNombre().setValue(persona.getSegundoNombre());
			
			setPersonaId(persona.getPersonaId());
		}
	}
	
	
	public String getPrimerNombre() {
		return primerNombre;
	}


	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}


	public String getSegundoNombre() {
		return segundoNombre;
	}


	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}


	public Boolean getIsPersonaNatural() {
		return isPersonaNatural;
	}

	public void setIsPersonaNatural(Boolean isPersonaNatural) {
		this.isPersonaNatural = isPersonaNatural;
	}
	
	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNumDocumentPersona() {
		return numDocumentPersona;
	}

	public void setNumDocumentPersona(String numDocumentPersona) {
		this.numDocumentPersona = numDocumentPersona;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public List<SelectItem> getLstTipoDocumento() {
		return lstTipoDocumento;
	}

	public void setLstTipoDocumento(List<SelectItem> lstTipoDocumento) {
		this.lstTipoDocumento = lstTipoDocumento;
	}


	public HtmlComboBox getCmbTipoDocumento() {
		return cmbTipoDocumento;
	}


	public void setCmbTipoDocumento(HtmlComboBox cmbTipoDocumento) {
		this.cmbTipoDocumento = cmbTipoDocumento;
	}


	public HtmlInputText getTxtNumDocumento() {
		return txtNumDocumento;
	}


	public void setTxtNumDocumento(HtmlInputText txtNumDocumento) {
		this.txtNumDocumento = txtNumDocumento;
	}


	public HtmlInputText getTxtApePaterno() {
		return txtApePaterno;
	}


	public void setTxtApePaterno(HtmlInputText txtApePaterno) {
		this.txtApePaterno = txtApePaterno;
	}


	public HtmlInputText getTxtApeMaterno() {
		return txtApeMaterno;
	}


	public void setTxtApeMaterno(HtmlInputText txtApeMaterno) {
		this.txtApeMaterno = txtApeMaterno;
	}


	public HtmlInputText getTxtPrimerNombre() {
		return txtPrimerNombre;
	}


	public void setTxtPrimerNombre(HtmlInputText txtPrimerNombre) {
		this.txtPrimerNombre = txtPrimerNombre;
	}


	public HtmlInputText getTxtSegundoNombre() {
		return txtSegundoNombre;
	}


	public void setTxtSegundoNombre(HtmlInputText txtSegundoNombre) {
		this.txtSegundoNombre = txtSegundoNombre;
	}


	public HtmlInputText getTxtRazonSocial() {
		return txtRazonSocial;
	}


	public void setTxtRazonSocial(HtmlInputText txtRazonSocial) {
		this.txtRazonSocial = txtRazonSocial;
	}
	
	public String getMaxlengthNroDocumento() {
		return maxlengthNroDocumento;
	}

	public void setMaxlengthNroDocumento(String maxlengthNroDocumento) {
		this.maxlengthNroDocumento = maxlengthNroDocumento;
	}
	
	public Integer getPersonaId() {
		return personaId;
	}

	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}
	
	public PapeletaBoRemote getPapeletaBo() {
		return papeletaBo;
	}

	public void setPapeletaBo(PapeletaBoRemote papeletaBo) {
		this.papeletaBo = papeletaBo;
	}

	public PersonaBoRemote getPersonaBo() {
		return personaBo;
	}

	public void setPersonaBo(PersonaBoRemote personaBo) {
		this.personaBo = personaBo;
	}

	public Map<String, Integer> getMapTipoDocumento() {
		return mapTipoDocumento;
	}

	public void setMapTipoDocumento(Map<String, Integer> mapTipoDocumento) {
		this.mapTipoDocumento = mapTipoDocumento;
	}

	public Map<Integer, String> getMapITipoDocumento() {
		return mapITipoDocumento;
	}

	public void setMapITipoDocumento(Map<Integer, String> mapITipoDocumento) {
		this.mapITipoDocumento = mapITipoDocumento;
	}

	public String getActualizaPersona() {
		return actualizaPersona;
	}

	public void setActualizaPersona(String actualizaPersona) {
		this.actualizaPersona = actualizaPersona;
	}
	
	public Boolean getSinDocumento() {
		return sinDocumento;
	}

	public void setSinDocumento(Boolean sinDocumento) {
		this.sinDocumento = sinDocumento;
	}
}
