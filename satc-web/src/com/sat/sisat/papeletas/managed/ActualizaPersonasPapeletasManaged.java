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
import javax.faces.component.html.HtmlSelectOneRadio;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.richfaces.component.html.HtmlComboBox;
import org.richfaces.component.html.HtmlInputText;

import com.sat.sisat.administracion.business.AdministracionBoRemote;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.ValidateInput;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.consumoWS.ConsultaReniecManaged;
import com.sat.sisat.papeleta.business.PapeletaBoRemote;
import com.sat.sisat.persistence.entity.GnLugar;
import com.sat.sisat.persistence.entity.GnTipoVia;
import com.sat.sisat.persistence.entity.GnVia;
import com.sat.sisat.persistence.entity.MpTipoDocuIdentidad;
import com.sat.sisat.persistence.entity.PaDireccion;
import com.sat.sisat.persistence.entity.PaPersona;
import com.sat.sisat.persistence.entity.TgPersona;
import com.sat.sisat.persona.business.PersonaBoRemote;
import com.sat.sisat.vehicular.dto.BuscarPersonaDTO;
import com.sat.sisatc.seguridad.dto.ConsultaReniecDTO;

import pe.gob.reniec.ws.ResultadoConsulta;

@ManagedBean
@ViewScoped
public class ActualizaPersonasPapeletasManaged extends BaseManaged {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	PapeletaBoRemote papeletaBo;
	
	@EJB
	PersonaBoRemote personaBo;
	
	@EJB
	AdministracionBoRemote administracionBo;
	
	private ResultadoConsulta resultadoConsultaReniec = new ResultadoConsulta();	
	
	private String tipoDocumento;
	private String numDocumentPersona;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String primerNombre;
	private String segundoNombre;
	private String razonSocial;
	private Integer personaId;
	private Integer direccionId;
	
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
	private HtmlInputText txtDireccionCompleta  = new HtmlInputText();
	private HtmlInputText txtNumero = new HtmlInputText();
	
	private String direccionCompleta;
	private String maxlengthNroDocumento;
	
	//Direccion
	private HtmlComboBox cmbtipovia;
	private List<SelectItem> lsttipovia=new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapGnTipoVia=new HashMap<String, Integer>();
	private HashMap<Integer,String> mapIGnTipoVia=new HashMap<Integer,String>();

	private HtmlComboBox cmbvia;
	private List<SelectItem> lstvia=new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapGnVia=new HashMap<String, Integer>();
	private HashMap<Integer,String> mapIGnVia=new HashMap<Integer,String>();
	
	private String numero;
		
	private Integer selectedOptBusc;
	
	private String actualizaPersona;
	private Boolean esContribuyente;
	private Boolean esSinNumero;
	public ActualizaPersonasPapeletasManaged(){
		
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
			cmbTipoDocumento.setValue(Constante.TIPO_DOCUMENTO_DNI);
			setTipoDocumento(Constante.TIPO_DOCUMENTO_DNI);
			maxlengthNroDocumento="8";
//			esContribuyente = (Boolean) getSessionMap().get("esContribuyente");
			//GnTipoVia
	        List<GnTipoVia> lstGnTipoVia=papeletaBo.getAllGnTipoVia();
			Iterator<GnTipoVia> it = lstGnTipoVia.iterator();  
			lsttipovia = new ArrayList<SelectItem>();
			
	        while (it.hasNext()){
	        	GnTipoVia obj = it.next();  
	        	if(obj.getTipoViaId()!=0){//Diferente de otros
		        	lsttipovia.add(new SelectItem(obj.getDescripcion(),String.valueOf(obj.getTipoViaId())));
		        	mapGnTipoVia.put(obj.getDescripcion(), obj.getTipoViaId());
		        	mapIGnTipoVia.put(obj.getTipoViaId(),obj.getDescripcion());
	        	}
	        }
	        setSelectedOptBusc(1);
			limpiar();
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void changeOpcionBusc(ValueChangeEvent event){
		 HtmlSelectOneRadio radio=(HtmlSelectOneRadio) event.getComponent();
		 if(radio!=null&&radio.getValue()!=null){
			 setSelectedOptBusc(Integer.valueOf(radio.getValue().toString()));
		 }
		 limpiarBusc();
	}

	private void limpiarBusc() {
		getCmbtipovia().setValue("");
		getCmbvia().setValue("");
		getTxtDireccionCompleta().setValue("");
		getTxtNumero().setValue("");
		setNumero("");
		setDireccionCompleta("");
	}
	
	public void selectTipoVia(ValueChangeEvent e){
		try{
			HtmlComboBox combo = (HtmlComboBox) e.getComponent();
			if(combo.getValue()!=null&&!combo.getValue().toString().isEmpty()){
				loadVia(combo.getValue().toString());
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	private void loadVia(String value){
		try{
			if(value!=null){
				Integer tipoViaId=mapGnTipoVia.get(value);
				
				List<GnVia> lstGnVia=papeletaBo.getAllGnVia(tipoViaId);
				Iterator<GnVia> it = lstGnVia.iterator();  
				lstvia = new ArrayList<SelectItem>();
				 
		        while (it.hasNext()){
		        	GnVia obj = it.next();  
		        	lstvia.add(new SelectItem(obj.getDescripcion(),String.valueOf(obj.getViaId())));
		        	mapGnVia.put(obj.getDescripcion(), obj.getViaId());
		        	mapIGnVia.put(obj.getViaId(),obj.getDescripcion());
		        }	
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void checkEsSinNumero(ValueChangeEvent event) {
		Boolean check = (Boolean) event.getNewValue();
		PaDireccion direccion=new PaDireccion();
		if (check == Boolean.TRUE) {
			setNumero("S/N");
			this.txtNumero.setValue("S/N");
			direccion.setNumero("S/N");
			setEsSinNumero(Boolean.TRUE);
		} else {
			setEsSinNumero(Boolean.FALSE);			
			direccion.setNumero(numero);
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
	
	
	public void consultaReniec() 
	{		
		
		ConsultaReniecManaged consultaReniec=new ConsultaReniecManaged();
		resultadoConsultaReniec=new ResultadoConsulta();	
		
		String dni;
		dni=numDocumentPersona;//getTxtNumDocumento().getSubmittedValue().toString();
		
		consultaReniec.setDniConsulta(dni);		
				
		
		
		try {
			
			//Consulta WebService.			
			resultadoConsultaReniec=consultaReniec.consultaWS();
			
			//Necesitamos registrar los datos de auditoria
			ConsultaReniecDTO datos = new ConsultaReniecDTO();

			datos.setDniConsulta(getSessionManaged().getUsuarioLogIn().getDniUsuario());
			datos.setDniConsultado(consultaReniec.getDniConsulta());
			datos.setUsuarioID(getSessionManaged().getUsuarioLogIn().getUsuarioId());
			datos.setTerminal(getSessionManaged().getTerminalLogIn());

			administracionBo.registrarConsultaReniec(datos);
			//Fin de registro de auditoria.
			
			//String rutaFotoReniec;
			//rutaFotoReniec=consultaReniec.getRutaImagen();
			
			String pNombre,sNombre="";
			Integer indice;
			
			setApellidoPaterno(resultadoConsultaReniec.getDatosPersona().getApPrimer());
			setApellidoMaterno(resultadoConsultaReniec.getDatosPersona().getApSegundo());
			setPrimerNombre(resultadoConsultaReniec.getDatosPersona().getPrenombres());
			
			indice=resultadoConsultaReniec.getDatosPersona().getPrenombres().indexOf(" ");
			
			//Error
			if (indice== -1) return;
			
			
			if (indice==0 ) 
			{
				//Un solo nombre
				pNombre=resultadoConsultaReniec.getDatosPersona().getPrenombres();
				
			}
			else
			{
				pNombre=resultadoConsultaReniec.getDatosPersona().getPrenombres().substring(0, indice);
				sNombre=resultadoConsultaReniec.getDatosPersona().getPrenombres().substring(indice + 1,resultadoConsultaReniec.getDatosPersona().getPrenombres().length() );	
			}
			
			
			//getTxtPrimerNombre().setValue(primerNombre);
			//getTxtSegundoNombre().setValue(segundoNombre);
			
			setPrimerNombre(pNombre);
			setSegundoNombre(sNombre);	
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// TODO: handle finally clause
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
		cmbTipoDocumento.setValue(Constante.TIPO_DOCUMENTO_DNI);
		txtApeMaterno.setValue("");
		txtApePaterno.setValue("");
		txtNumDocumento.setValue("");
		txtPrimerNombre.setValue("");
		txtRazonSocial.setValue("");
		txtSegundoNombre.setValue("");
		txtDireccionCompleta.setValue("");
		
		setApellidoMaterno("");
		setApellidoPaterno("");
		setNumDocumentPersona("");
		setPrimerNombre("");
		setRazonSocial("");
		setSegundoNombre("");
		
		if(getCmbtipovia()!=null)
			getCmbtipovia().setValue("");
		
		if(getCmbvia()!=null)
			getCmbvia().setValue("");
		
		setNumero("");
	}

	/**
	 * Solo se permite modificar a las personas que no son contribuyentes 
	 * es decir que no esten registrados en MP_PERSONA /MP_DIRECCION
	 */
	public void registrarPersona() {
		try {
			if (validaNumDocumento(getNumDocumentPersona())) {
					if (validaDireccion()) {
						if (validaApellidosNombres()) {
							PaPersona persona = new PaPersona();
							if (getActualizaPersona() != null && getActualizaPersona().equals("N")) {
								persona.setPersonaId(Constante.RESULT_PENDING);
							} else {
								persona.setPersonaId(getPersonaId() == null ? Constante.RESULT_PENDING: getPersonaId());
							}

							persona.setTipoDocumentoId(Integer.valueOf(mapTipoDocumento.get(getTipoDocumento())));
							persona.setNroDocIdentidad(getNumDocumentPersona());
							

							if (persona.getTipoDocumentoId() == Constante.TIPO_DOCUMENTO_RUC_ID) {
								persona.setRazonSocial(getRazonSocial());
							} else {
								persona.setPrimerNombre(getPrimerNombre());
								persona.setSegundoNombre(getSegundoNombre());
								persona.setApePaterno(getApellidoPaterno());
								persona.setApeMaterno(getApellidoMaterno());
							}

							PaDireccion direccion = new PaDireccion();
							direccion.setDireccionId(getDireccionId() == null ? Constante.RESULT_PENDING: getDireccionId());
							if (getSelectedOptBusc().intValue() == 1) {
								direccion.setTipoViaId(mapGnTipoVia.get(getCmbtipovia().getValue()));
								direccion.setViaId(mapGnVia.get(getCmbvia().getValue()));
								direccion.setLugarId(null);
								direccion.setSectorId(null);
								direccion.setNumero(numero);
								direccion.setPersonaId(persona.getPersonaId());

								String direccionCompleta = papeletaBo.getDireccionCompleta(direccion,mapIGnTipoVia, mapIGnVia);
								if (direccionCompleta != null && direccionCompleta.trim().length() > 0) {
									direccion.setDireccionCompleta(direccionCompleta);
								}
							} else {
								direccion.setDireccionCompleta(getDireccionCompleta());
							}

							BuscarPersonaDTO personaDto = getPersonaDto(persona, direccion);
							String personaPapeleta = (String) getSessionMap().get("personaPapeleta");
							if (personaPapeleta != null
									&& personaPapeleta.equals("I")) {
								RegistroPapeletasManaged registro = (RegistroPapeletasManaged) getManaged("registroPapeletasManaged");
								registro.setDatosInfractor(personaDto);
							} else if (personaPapeleta != null
									&& personaPapeleta.equals("P")) {
								RegistroPapeletasManaged registro = (RegistroPapeletasManaged) getManaged("registroPapeletasManaged");
								registro.setDatosPropietario(personaDto);
							}
							limpiar();
							esContribuyente = (Boolean) getSessionMap().get("esContribuyente");
							if (esContribuyente == true) {	
								WebMessages.messageError("Es un Contribeyente s�lo se Actualiz� la Direcci�n para Papeletas, los otros datos es por DJ");
							} 
							
						} else {
							WebMessages
									.messageError("Apellidos y nombres no valido");
						}
					} else {
						WebMessages
								.messageError("Especifique la direccion de la persona");
					}
			} else {
				WebMessages
						.messageError("Número de documento de identidad no valido");
			}
		} catch (Exception e) {
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
	
	public boolean validaDireccion(){
		if(getCmbtipovia().getValue()!=null&&getCmbtipovia().getValue().toString().trim().length()>0&&
				getCmbvia().getValue()!=null&&getCmbvia().getValue().toString().trim().length()>0&&
						numero!=null&&numero.trim().length()>0){
			return true;				
		}else if(getTxtDireccionCompleta().getValue()!=null&&getTxtDireccionCompleta().getValue().toString().trim().length()>0){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean validaApellidosNombres(){
		if(getTipoDocumento().trim().equals("RUC")){
			if(getRazonSocial()!=null&&!getRazonSocial().isEmpty()&&getRazonSocial().trim().length()>0){
				return true;
			}else{
				return false;
			}
		}else{
			if(getPrimerNombre()!=null&&ValidateInput.validateFirstName(getPrimerNombre().trim())&&
					getApellidoPaterno()!=null&&ValidateInput.validateLastName(getApellidoPaterno().trim())&&
							getApellidoMaterno()!=null&&ValidateInput.validateLastName(getApellidoMaterno().trim())){
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
	}
	
	public BuscarPersonaDTO getPersonaDto(PaPersona persona,PaDireccion direccion){
		BuscarPersonaDTO personaDto=new BuscarPersonaDTO();
		personaDto.setPersonaId(persona.getPersonaId());
		personaDto.setTipoDocIdentidad(mapITipoDocumento.get(persona.getTipoDocumentoId()));
			personaDto.setTipodocumentoIdentidadId(persona.getTipoDocumentoId());
			personaDto.setNroDocuIdentidad(persona.getNroDocIdentidad());
		if(persona.getTipoDocumentoId()==Constante.TIPO_DOCUMENTO_RUC_ID){
			personaDto.setRazonSocial(persona.getRazonSocial());
		}else{
			personaDto.setApellidosNombres(persona.getApePaterno()+" "+persona.getApeMaterno()+" "+persona.getPrimerNombre()+" "+persona.getSegundoNombre());
			personaDto.setApellidoPaterno(persona.getApePaterno());
			personaDto.setApellidoMaterno(persona.getApeMaterno());
			personaDto.setPrimerNombre(persona.getPrimerNombre());
			personaDto.setSegundoNombre(persona.getSegundoNombre());	
		}
		personaDto.setDireccionCompleta(direccion.getDireccionCompleta());
		personaDto.setDireccionId(direccion.getDireccionId());
		//--
		personaDto.setTipoViaId(direccion.getTipoViaId());
		personaDto.setViaId(direccion.getViaId());
		personaDto.setLugarId(direccion.getLugarId());
		personaDto.setNumero(direccion.getNumero());
		return personaDto;
	}
	
	public void setProperty(BuscarPersonaDTO persona){
		if(persona!=null){
			cmbTipoDocumento.setValue(persona.getTipoDocIdentidad());
			txtApeMaterno.setValue(persona.getApellidoMaterno());
			txtApePaterno.setValue(persona.getApellidoPaterno());
			txtNumDocumento.setValue(persona.getNroDocuIdentidad());
			txtPrimerNombre.setValue(persona.getPrimerNombre());
			txtRazonSocial.setValue(persona.getRazonSocial());
			txtSegundoNombre.setValue(persona.getSegundoNombre());
			txtDireccionCompleta.setValue(persona.getDireccionCompleta());
			//--
			setTipoDocumento(persona.getTipoDocIdentidad());
			setDireccionCompleta(persona.getDireccionCompleta());
			setNumero(persona.getNroDocuIdentidad());
			setRazonSocial(persona.getRazonSocial());
			setSegundoNombre(persona.getSegundoNombre());
			setPrimerNombre(persona.getPrimerNombre());
			setApellidoMaterno(persona.getApellidoMaterno());
			setApellidoPaterno(persona.getApellidoPaterno());
			setNumDocumentPersona(persona.getNroDocuIdentidad());
			//--
			//--Datos de la direccion
			if(getCmbtipovia()!=null){
				if(persona.getTipoViaId()!=null){
					getCmbtipovia().setValue(mapIGnTipoVia.get(persona.getTipoViaId()));
					if(getCmbtipovia().getValue()!=null)
						loadVia(getCmbtipovia().getValue().toString());
				}
			}
			
			if(getCmbvia()!=null){
				if(persona.getViaId()!=null){
					getCmbvia().setValue(mapIGnVia.get(persona.getViaId()));
				}
			}
			
			
			if(persona.getTipoDocIdentidad()!=null&&persona.getTipoDocIdentidad().equals("RUC")){
				setIsPersonaNatural(Boolean.FALSE);
			}else{
				setIsPersonaNatural(Boolean.TRUE);
			}
			
			setNumero(persona.getNumero());
			//--
			if(getCmbtipovia().getValue()!=null&&getCmbtipovia().getValue().toString().trim().length()>0&&
					getCmbvia().getValue()!=null&&getCmbvia().getValue().toString().trim().length()>0&&
							numero!=null&&numero.trim().length()>0){
				setSelectedOptBusc(1);
			}else{
				setSelectedOptBusc(2);
			}
			//--
			setPersonaId(persona.getPersonaId());
			setDireccionId(persona.getDireccionId());
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
	
	public HtmlInputText getTxtDireccionCompleta() {
		return txtDireccionCompleta;
	}

	public void setTxtDireccionCompleta(HtmlInputText txtDireccionCompleta) {
		this.txtDireccionCompleta = txtDireccionCompleta;
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

	public HtmlComboBox getCmbtipovia() {
		return cmbtipovia;
	}

	public void setCmbtipovia(HtmlComboBox cmbtipovia) {
		this.cmbtipovia = cmbtipovia;
	}

	public List<SelectItem> getLsttipovia() {
		return lsttipovia;
	}

	public void setLsttipovia(List<SelectItem> lsttipovia) {
		this.lsttipovia = lsttipovia;
	}

	public HashMap<String, Integer> getMapGnTipoVia() {
		return mapGnTipoVia;
	}

	public void setMapGnTipoVia(HashMap<String, Integer> mapGnTipoVia) {
		this.mapGnTipoVia = mapGnTipoVia;
	}

	public HtmlComboBox getCmbvia() {
		return cmbvia;
	}

	public void setCmbvia(HtmlComboBox cmbvia) {
		this.cmbvia = cmbvia;
	}

	public List<SelectItem> getLstvia() {
		return lstvia;
	}

	public void setLstvia(List<SelectItem> lstvia) {
		this.lstvia = lstvia;
	}

	public HashMap<String, Integer> getMapGnVia() {
		return mapGnVia;
	}

	public void setMapGnVia(HashMap<String, Integer> mapGnVia) {
		this.mapGnVia = mapGnVia;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
	public Integer getDireccionId() {
		return direccionId;
	}

	public void setDireccionId(Integer direccionId) {
		this.direccionId = direccionId;
	}
	
	public String getDireccionCompleta() {
		return direccionCompleta;
	}

	public void setDireccionCompleta(String direccionCompleta) {
		this.direccionCompleta = direccionCompleta;
	}
	public Integer getSelectedOptBusc() {
		return selectedOptBusc;
	}

	public void setSelectedOptBusc(Integer selectedOptBusc) {
		this.selectedOptBusc = selectedOptBusc;
	}
	
	public HtmlInputText getTxtNumero() {
		return txtNumero;
	}

	public void setTxtNumero(HtmlInputText txtNumero) {
		this.txtNumero = txtNumero;
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

	public Boolean getEsSinNumero() {
		return esSinNumero;
	}

	public void setEsSinNumero(Boolean esSinNumero) {
		this.esSinNumero = esSinNumero;
	}

	public Boolean getEsContribuyente() {
		return esContribuyente;
	}

	public void setEsContribuyente(Boolean esContribuyente) {
		this.esContribuyente = esContribuyente;
	}
}
