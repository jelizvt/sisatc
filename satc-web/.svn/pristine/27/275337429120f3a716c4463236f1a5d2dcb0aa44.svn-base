package com.sat.sisat.papeletas.managed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.papeleta.business.PapeletaBoRemote;
import com.sat.sisat.persistence.entity.GnPersona;
import com.sat.sisat.persistence.entity.MpTipoDocuIdentidad;
import com.sat.sisat.persistence.entity.TgPersona;
import com.sat.sisat.persona.business.PersonaBoRemote;
import com.sat.sisat.vehicular.managed.BuscarPersonaManaged;

@ManagedBean
@ViewScoped
public class RegistroPersonasPapeletasManaged extends BaseManaged {
	
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
	private String direccionCompleta;
	
	private List<SelectItem> lstTipoDocumento = new ArrayList<SelectItem>();
	private Map<String,Integer> mapTipoDocumento = new HashMap<String,Integer>();
	private Boolean isPersonaNatural;
	
	private HtmlComboBox cmbTipoDocumento = new HtmlComboBox();
	private HtmlInputText txtNumDocumento = new HtmlInputText();
	private HtmlInputText txtApePaterno = new HtmlInputText();
	private HtmlInputText txtApeMaterno = new HtmlInputText();
	private HtmlInputText txtPrimerNombre = new HtmlInputText();
	private HtmlInputText txtSegundoNombre = new HtmlInputText();
	private HtmlInputText txtRazonSocial  = new HtmlInputText();
	
	private String maxlengthNroDocumento;
	
	public RegistroPersonasPapeletasManaged(){
		
	}
	
	@PostConstruct
	public void cargaPaginaRegistro(){
		try {
			//MpTipoDocuIdentidad
			List<MpTipoDocuIdentidad> lstTD = new ArrayList<MpTipoDocuIdentidad>();
			lstTD = papeletaBo.getAllMpTipoDocumento();
			Iterator<MpTipoDocuIdentidad> itTD = lstTD.iterator();
			while (itTD.hasNext()) {
				MpTipoDocuIdentidad objTD = itTD.next();
				lstTipoDocumento.add(new SelectItem(objTD.getDescrpcionCorta()));
				mapTipoDocumento.put(objTD.getDescrpcionCorta(),objTD.getTipoDocIdentidadId());
			}
			
			isPersonaNatural = Boolean.TRUE;
			cmbTipoDocumento.setValue(Constante.TIPO_DOCUMENTO_DNI);
			maxlengthNroDocumento="8";
			limpiar();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
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
	}
	
	public Boolean existeDocumento(){
		return true;
	}
	
	public void registrarPersona(){
		try{
			if(validaNumDocumento(getNumDocumentPersona())){
				
					GnPersona persona=new GnPersona();
					if(getTipoDocumento().equals(Constante.TIPO_DOCUMENTO_RUC)){
						persona=new GnPersona();
						persona.setTipoDocumentoId(Integer.valueOf(mapTipoDocumento.get(getTipoDocumento())));
						persona.setNroDocIdentidad(getNumDocumentPersona());
						persona.setRazonSocial(getRazonSocial());
						persona.setDomicilioCompleto(getDireccionCompleta());
						
						//cc: persona.setUsuarioId(Constante.USUARIO_ID);
						persona.setEstado(Constante.ESTADO_ACTIVO);
						//cc: persona.setFechaRegistro(DateUtil.getCurrentDate());
						//cc: persona.setTerminalRegistro(Constante.TERMINAL);
					}else{
						persona=new GnPersona();
						persona.setTipoDocumentoId(Integer.valueOf(mapTipoDocumento.get(getTipoDocumento())));
						persona.setNroDocIdentidad(getNumDocumentPersona());
						persona.setPrimerNombre(getPrimerNombre());
						persona.setSegundoNombre(getSegundoNombre());
						persona.setApePaterno(getApellidoPaterno());
						persona.setApeMaterno(getApellidoMaterno());
						persona.setDomicilioCompleto(getDireccionCompleta());
						persona.setApellidosNombres(getApellidoPaterno()+" "+getApellidoMaterno()+", "+getPrimerNombre()+" "+getSegundoNombre());
						
						//cc: persona.setUsuarioId(Constante.USUARIO_ID);
						persona.setEstado(Constante.ESTADO_ACTIVO);
						//cc: persona.setFechaRegistro(DateUtil.getCurrentDate());
						//cc: persona.setTerminalRegistro(Constante.TERMINAL);
					}
					Integer Id=papeletaBo.savePersona(persona);
					if(Id==Constante.RESULT_SUCCESS){
						BuscarPersonaManaged obj = (BuscarPersonaManaged)getManaged("buscarPersonaManaged");
						obj.buscarPersona(getTipoDocumento(), getNumDocumentPersona());
						limpiar();
					}else{
						String mensaje="La Persona ";
						if(getTipoDocumento().trim().equals("RUC")){
							mensaje="La Razon social ";
						}
						WebMessages.messageError(mensaje+" con "+getTipoDocumento()+" "+getNumDocumentPersona()+" ya se encuentra registrada ");
					}
					
			}else{
				WebMessages.messageError("Ingrese Numero de documento valido o S/N");
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
				Pattern patt = Pattern.compile("[0-9]{"+maxlengthNroDocumento+"}");
				Matcher m = patt.matcher(valorCampo);
				return m.matches();
			}
		}else{
			return false;
		}
	}
	
	public void buscarPersonaDNI(){
		try{
			if(getCmbTipoDocumento().getValue()!=null&&getCmbTipoDocumento().getValue().equals(Constante.TIPO_DOCUMENTO_DNI)){
				if(getTxtNumDocumento().getSubmittedValue()!=null&&getTxtNumDocumento().getSubmittedValue().toString().length()==8){
					TgPersona persona=personaBo.buscarPersonaReniec(getTxtNumDocumento().getSubmittedValue().toString().trim());	
					if(persona!=null){
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
						getTxtPrimerNombre().setValue("");
						getTxtSegundoNombre().setValue("");
						getTxtApePaterno().setValue("");
						getTxtApeMaterno().setValue("");
						
						setPrimerNombre("");
						setSegundoNombre("");
						setApellidoPaterno("");
						setApellidoMaterno("");
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void limpiar(){
		cmbTipoDocumento.setValue(Constante.TIPO_DOCUMENTO_DNI);
		txtApeMaterno.setValue("");
		txtApePaterno.setValue("");
		txtNumDocumento.setValue("");
		txtPrimerNombre.setValue("");
		txtRazonSocial.setValue("");
		txtSegundoNombre.setValue("");
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
	
	public String getDireccionCompleta() {
		return direccionCompleta;
	}

	public void setDireccionCompleta(String direccionCompleta) {
		this.direccionCompleta = direccionCompleta;
	}
}
