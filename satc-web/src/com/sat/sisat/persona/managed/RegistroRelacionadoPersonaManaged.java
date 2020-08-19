package com.sat.sisat.persona.managed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.richfaces.component.html.HtmlComboBox;

import com.sat.sisat.administracion.business.AdministracionBoRemote;
import com.sat.sisat.common.business.GeneralBoRemote;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.Util;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.consumoWS.ConsultaReniecManaged;
import com.sat.sisat.persistence.entity.MpRelacionado;
import com.sat.sisat.persistence.entity.MpTipoDocuIdentidad;
import com.sat.sisat.persistence.entity.MpTipoRelacion;
import com.sat.sisat.persistence.entity.TgPersona;
import com.sat.sisat.persona.business.PersonaBoRemote;
import com.sat.sisatc.seguridad.dto.ConsultaReniecDTO;

import pe.gob.reniec.ws.ResultadoConsulta;
@ManagedBean
@ViewScoped
public class RegistroRelacionadoPersonaManaged extends BaseManaged {

	@EJB
	PersonaBoRemote personaBo;
	@EJB
	GeneralBoRemote generalBo;
	
	@EJB	
	AdministracionBoRemote administracionBo;

	
	private HtmlComboBox cmbMpTipoRelacion;
	private List<SelectItem> lstMpTipoRelacion=new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapMpTipoRelacion=new HashMap<String, Integer>();
	private HashMap<Integer,String> mapIMpTipoRelacion=new HashMap<Integer,String>();
	
	private String cmbValueMpTipoRelacion;
	private List<MpTipoRelacion> lMpTipoRelacion = null;	
	
	private HtmlComboBox cmbtipodocumentoidentidad;
	private List<SelectItem> lsttipodocumentoidentidad = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRpTipodocumentoidentidad = new HashMap<String, Integer>();
	private HashMap<Integer,String> mapIRpTipodocumentoidentidad = new HashMap<Integer,String>();
	
	private String cmbValuetipodocumentoidentidad;
	private List<MpTipoDocuIdentidad> lMptipodocumentoidentidad = null;
	
	private Integer mpTipoRelacionId;
	private Integer tipodocumentoIdentidadId;
	   
	private MpRelacionado mpRelacionado;
	
	private Integer tipoAccion=Constante.TIPO_ACCION_NUEVO;
	
	
	private String rutaFotoReniec;
	

	private ResultadoConsulta resultadoConsultaReniec = new ResultadoConsulta();
	
	
	

	public RegistroRelacionadoPersonaManaged(){
		
	}
	
	public void verMpRelacionadoPersona(){
		try{
			if(mpRelacionado!=null){
				setCmbValueMpTipoRelacion(mapIMpTipoRelacion.get(mpRelacionado.getTipoRelacionId()));
				mpTipoRelacionId = mpRelacionado.getTipoRelacionId();
				setCmbValuetipodocumentoidentidad(mapIRpTipodocumentoidentidad.get(mpRelacionado.getMpTipoDocIdentidadId()));
				tipodocumentoIdentidadId = mpRelacionado.getMpTipoDocIdentidadId();
				tipoAccion=null;
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	@PostConstruct
	public void init(){
		mpRelacionado=new MpRelacionado();
		try{
			//MpTipoRelacion
	        List<MpTipoRelacion> lstMpTipoPersona=personaBo.getAllMpTipoRelacion();
			Iterator<MpTipoRelacion> it = lstMpTipoPersona.iterator();  
			lstMpTipoRelacion=new ArrayList<SelectItem>();
			 
	        while (it.hasNext()){
	        	MpTipoRelacion obj = it.next();  
	        	lstMpTipoRelacion.add(new SelectItem(obj.getDescripcion(),String.valueOf(obj.getTipoRelacionId())));  
	        	mapMpTipoRelacion.put(obj.getDescripcion().trim(), obj.getTipoRelacionId());
	        	mapIMpTipoRelacion.put(obj.getTipoRelacionId(),obj.getDescripcion().trim());
	        }
	        //MpTipoDocuIdentidad
	        List<MpTipoDocuIdentidad> lstMpTipoDocuIdentidad=personaBo.findMpTipoDocuIdentidad(1, null); // Tipo de Persona Natural
			Iterator<MpTipoDocuIdentidad> it1 = lstMpTipoDocuIdentidad.iterator();  
			lsttipodocumentoidentidad=new ArrayList<SelectItem>();
			 
	        while (it1.hasNext()){
	        	MpTipoDocuIdentidad obj = it1.next();  
	        	lsttipodocumentoidentidad.add(new SelectItem(obj.getDescripcion(),String.valueOf(obj.getTipoDocIdentidadId())));  
	        	mapRpTipodocumentoidentidad.put(obj.getDescripcion().trim(), obj.getTipoDocIdentidadId());
	        	mapIRpTipodocumentoidentidad.put(obj.getTipoDocIdentidadId(), obj.getDescripcion().trim());
	        }
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	
	public void consultaReniec() throws Exception 
	{		
		/*
		if (mpRelacionado.getMpTipoDocIdentidadId() !=1  && mpRelacionado.getMpTipoDocIdentidadId() !=12) //DNi y LE			
			{
			WebMessages.messageError("Válido solo para búsuqedas por documento de tipo DNI.");
			return;
			}
		*/
				
		ConsultaReniecManaged consultaReniec=new ConsultaReniecManaged();
		consultaReniec.setDniConsulta(mpRelacionado.getNroDocuIdentidad());
		
		//Necesitamos registrar los datos de auditoria
		ConsultaReniecDTO datos = new ConsultaReniecDTO();

		datos.setDniConsulta(getSessionManaged().getUsuarioLogIn().getDniUsuario());
		datos.setDniConsultado(consultaReniec.getDniConsulta());
		datos.setUsuarioID(getSessionManaged().getUsuarioLogIn().getUsuarioId());
		datos.setTerminal(getSessionManaged().getTerminalLogIn());

		administracionBo.registrarConsultaReniec(datos);
		//Fin de registro de auditoria.
		

		resultadoConsultaReniec=new ResultadoConsulta();		
		
		
		
		try {
			
			//Consulta WebService.			
			resultadoConsultaReniec=consultaReniec.consultaWS();
			
			rutaFotoReniec=consultaReniec.getRutaImagen();
			
			String primerNombre,segundoNombre="";
			Integer indice;
			
			mpRelacionado.setApePaterno(resultadoConsultaReniec.getDatosPersona().getApPrimer());
			mpRelacionado.setApeMaterno(resultadoConsultaReniec.getDatosPersona().getApSegundo());			
			mpRelacionado.setPrimerNombre(resultadoConsultaReniec.getDatosPersona().getPrenombres());
			
			indice=resultadoConsultaReniec.getDatosPersona().getPrenombres().indexOf(" ");
			
			//Error
			if (indice== -1) return;
			
			
			if (indice==0 ) 
			{
				//Un solo nombre
				primerNombre=resultadoConsultaReniec.getDatosPersona().getPrenombres();
				
			}
			else
			{
				primerNombre=resultadoConsultaReniec.getDatosPersona().getPrenombres().substring(0, indice);
				segundoNombre=resultadoConsultaReniec.getDatosPersona().getPrenombres().substring(indice + 1,resultadoConsultaReniec.getDatosPersona().getPrenombres().length() );	
			}
			
			
					
			mpRelacionado.setPrimerNombre(primerNombre);
			mpRelacionado.setSegundoNombre(segundoNombre);		
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// TODO: handle finally clause
		}
		
	}
	
	
	
	
	
	/*Load  de los Combos*/
	public void loadMpTipoRelacionById(ValueChangeEvent event) {
		try{
		    HtmlComboBox combo=(HtmlComboBox) event.getComponent(); 
		    String value=combo.getValue().toString();
			if(value!=null){
				mpTipoRelacionId=(Integer)mapMpTipoRelacion.get(value);
				if(mpTipoRelacionId!=null){
					lMpTipoRelacion=personaBo.findMpTipoRelacion(mpTipoRelacionId);	
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	public void loadMpTipoDocumentoIdentidadById(ValueChangeEvent event) {
		try{
			HtmlComboBox combo=(HtmlComboBox) event.getComponent(); 
			String value=combo.getValue().toString();
			
			if(value!=null){
				tipodocumentoIdentidadId = (Integer)mapRpTipodocumentoidentidad.get(value);
				
				if(tipodocumentoIdentidadId!=null){
					lMptipodocumentoidentidad=personaBo.findMpTipoDocuIdentidad(1, null); // tipo Persona Natural	
				}
			}
			mpRelacionado.setNroDocuIdentidad("");
			
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	public void editMpRelacionadoPersona(){
		try{
			if(mpRelacionado!=null){
				setCmbValueMpTipoRelacion(mapIMpTipoRelacion.get(mpRelacionado.getTipoRelacionId()));
				mpTipoRelacionId = mpRelacionado.getTipoRelacionId();
				setCmbValuetipodocumentoidentidad(mapIRpTipodocumentoidentidad.get(mpRelacionado.getMpTipoDocIdentidadId()));
				tipodocumentoIdentidadId = mpRelacionado.getMpTipoDocIdentidadId();
				tipoAccion=Constante.TIPO_ACCION_EDITAR;
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public void nuevoMpRelacionadoPersona(){
		try{
			limpiar();
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public void limpiar(){
		mpRelacionado = new MpRelacionado();
		setCmbValueMpTipoRelacion("");
		setCmbValuetipodocumentoidentidad("");
		tipoAccion=Constante.TIPO_ACCION_NUEVO;
		mpTipoRelacionId=null;
		tipodocumentoIdentidadId=null;
	}
	
	public boolean validarPersona(){
		try{	
			//	FindMpPersona findMpPersonaTemp=new FindMpPersona();
		    if(mpTipoRelacionId==null){ 
		    	addErrorMessage("Ingrese Tipo Relacion");
		    	return false;
			}
		    if(tipodocumentoIdentidadId==null){ 
		    	addErrorMessage("Ingrese Tipo Documento");
		    	return false;
			}
		
			if(getCmbValuetipodocumentoidentidad().compareToIgnoreCase(Constante.TIPO_DOCUMENTO_DNI)==0){
				if(mpRelacionado.getNroDocuIdentidad().length()!=Constante.CANTIDAD_DIGITOS_DNI){
				 addErrorMessage(getMsg("mp.errornrodocumentoidentidaddni"));
				return false;
				}
			}
			RegistroPersonaManaged registroPersonaManaged = (RegistroPersonaManaged) getManaged("registroPersonaManaged");
			if(registroPersonaManaged.getCmbValuesubtipopersona()!=null)
			if(registroPersonaManaged.getCmbValuesubtipopersona().toString().compareTo(Constante.SUB_TIPO_PERSONA_SUC_INDIVISA)==0)
			{		
					String dniMpPersona=registroPersonaManaged.getMpPersona().getNroDocuIdentidad();
					if(dniMpPersona==null){
						addErrorMessage("Ingrese Documento de Identidad del Contribuyente");
						return false;
					}
						
					if(dniMpPersona.compareTo(mpRelacionado.getNroDocuIdentidad())==0){
						addErrorMessage(getMsg("mp.errornrodocumentoidentidadsucesion"));
						return false;
					}
			}
			
		
			}catch(Exception e){
				e.printStackTrace();
				WebMessages.messageFatal(e);
			}
			return true;
		
	}
	
	/*
	public void verificarRelacionadoPersona(){
	try{	
		if(validarPersona()){
			TgPersona persona=personaBo.buscarPersonaReniec(mpRelacionado.getNroDocuIdentidad());
			if(persona !=null){
				mpRelacionado.setPrimerNombre(persona.getNombre());
				mpRelacionado.setApePaterno(persona.getApe_pat());
				mpRelacionado.setApeMaterno(persona.getApe_mat());
			}
		}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	*/
	
	public void salir(){
		limpiar();
		setCmbMpTipoRelacion(null);
	}
	
	public void actualizarRelacionado()throws Exception{
		try{
			/*
			if(findMpRelacionado.getEstado().compareTo(Constante.ESTADO_PENDIENTE)==0)
				personaBo.actualizarEstadoMpRelacionado(Constante.ESTADO_ELIMINADO, findMpRelacionado.getPersonaId(),findMpRelacionado.getRelacionadoId());
			if(findMpRelacionado.getEstado().compareTo(Constante.ESTADO_ACTIVO)==0)
				personaBo.actualizarEstadoMpRelacionado(Constante.ESTADO_PENDIENTE_ACTUALIZACION, findMpRelacionado.getPersonaId(),findMpRelacionado.getRelacionadoId());
			*/
			guardar();
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public void actualizar()throws Exception{
		try{ 
			 if(validarDatos() && validarPersona()){
				mpRelacionado.setTipoRelacionId(mpTipoRelacionId);
				mpRelacionado.setMpTipoDocIdentidadId(tipodocumentoIdentidadId);
				//mpRelacionado.setUsuarioId(Constante.USUARIO_ID);
				//mpRelacionado.setTerminal(generalBo.obtenerTerminalCliente());
				mpRelacionado.setEstado(Constante.ESTADO_ACTIVO);
				int result=personaBo.ActualizarMpRelacionado(mpRelacionado);
				if(result>0){
					RegistroPersonaManaged registroPersonaManaged = (RegistroPersonaManaged) getManaged("registroPersonaManaged");
					registroPersonaManaged.loadRelacionados(mpRelacionado);
					limpiar();
				}
			 }
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		
	}
		
	public void guardar()throws Exception{
		try{  
			  if(validarDatos() && validarPersona()){	
				RegistroPersonaManaged registroPersonaManaged = (RegistroPersonaManaged) getManaged("registroPersonaManaged");
				
				mpRelacionado.setTipoRelacionId(mpTipoRelacionId);
				mpRelacionado.setMpTipoDocIdentidadId(tipodocumentoIdentidadId);
				mpRelacionado.setApellidosNombres(mpRelacionado.getApePaterno()+" "+mpRelacionado.getApeMaterno()+"; "+mpRelacionado.getPrimerNombre()+" "+mpRelacionado.getSegundoNombre());
				mpRelacionado.setEstado(Constante.ESTADO_ACTIVO);
				registroPersonaManaged.loadRelacionados(mpRelacionado);
				limpiar();
				//}
			  }
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public  boolean validarVacios(){
	     boolean error=false;
	    
	     
	     //quitando espacios en blanco de las variales
	      String  primerNombre=mpRelacionado.getPrimerNombre().trim();
	      String  apePaterno=mpRelacionado.getApePaterno().trim();
	      String  apeMaterno=mpRelacionado.getApeMaterno().trim();
	      //validando que no esten vacias.
	        if(primerNombre.isEmpty())
	           error= true;
	        if(apePaterno.isEmpty())
	           error= true;
	        if(apeMaterno.isEmpty())
		           error= true;
	   
	      return error;
	}
	
	public Boolean validarDatos()throws Exception{
		try{
			if(mpTipoRelacionId==null){ 
		    	addErrorMessage("Ingrese Tipo Relacion");
		    	return false;
			}
		    if(tipodocumentoIdentidadId==null){ 
		    	addErrorMessage("Ingrese Tipo Documento");
		    	return false;
			}
		    if(mpRelacionado.getNroDocuIdentidad()==null || mpRelacionado.getNroDocuIdentidad()==""){
		    	 addErrorMessage("Ingrese Nro de Documento");
			    	return false;
		     }
		     
		     if(mpRelacionado.getApePaterno()==null || mpRelacionado.getApePaterno()==""){
		    	 addErrorMessage("Ingrese Apellido Paterno");
			    	return false;
		     }
		     if(mpRelacionado.getApeMaterno()==null || mpRelacionado.getApeMaterno()==""){
		    	 addErrorMessage("Ingrese Apellido Materno");
			    	return false;
		     }
		     if(mpRelacionado.getPrimerNombre()==null || mpRelacionado.getPrimerNombre()==""){
		    	 addErrorMessage("Ingrese Primer Nombre");
			    	return false;
		     }
			if(validarVacios()){
				 addErrorMessage(getMsg("mp.espacioblanco"));
				return false;
			}
			if(mpRelacionado.getEmail().trim().length()>0){
				if(!Util.isEmail(mpRelacionado.getEmail())){
				 addErrorMessage(getMsg("mp.erroremail"));
				 return false;
				}
			}
			if(getCmbValuetipodocumentoidentidad().compareTo(Constante.TIPO_DOCUMENTO_DNI)==0){
					if(mpRelacionado.getNroDocuIdentidad().length()!=Constante.CANTIDAD_DIGITOS_DNI){
					 addErrorMessage(getMsg("mp.errornrodocumentoidentidaddni"));
					 return false;
					}
				 }
			RegistroPersonaManaged registroPersonaManaged = (RegistroPersonaManaged) getManaged("registroPersonaManaged");
			if(registroPersonaManaged.getMpPersona().getTipoPersonaId()==null){
				addErrorMessage(getMsg("mp.errortipocontribuyente"));
				return false;
			}
			if(registroPersonaManaged.getMpPersona().getSubtipoPersonaId()==null){
				addErrorMessage(getMsg("mp.errorsubtipocontribuyente"));
				return false;
			}
			if(registroPersonaManaged.getMpPersona().getTipoDocIdentidadId()==null){
				addErrorMessage(getMsg("mp.errortipodocumento")+" del Contribuyente");
				return false;
			}
			
			if(registroPersonaManaged.getMpPersona().getSubtipoPersonaId()==Constante.SUB_TIPO_PERSONA_MENOR_EDAD_ID)
				if(mpTipoRelacionId==Constante.TIPO_RELACIONADO_CONYUGE_ID){
					addErrorMessage(getMsg("mp.errormenoredadconyuge"));
					return false;
				}
			if(registroPersonaManaged.getMpPersona().getTipoPersonaId()==Constante.TIPO_PERSONA_JURIDICA_ID){
			//	int tempPersona=personaBo.verificarRelacionadoTipoPersonaJuridica(registroPersonaManaged.getMpPersona().getPersonaId());
			//	if(tempPersona==0){
					if(mpTipoRelacionId!=Constante.TIPO_RELACIONADO_REPRESENTANTE_LEGAL_ID && mpTipoRelacionId!=Constante.TIPO_RELACIONADO_ADMINISTRADOR_ID){
						addErrorMessage(getMsg("mp.errorrelacionadopersonajuridica"));
						return false;
					}
			//	}
			}
			if(registroPersonaManaged.getMpPersona().getSubtipoPersonaId()==Constante.SUB_TIPO_PERSONA_SUC_INDIVISA_ID){
				if(mpRelacionado.getNroDocuIdentidad().compareTo(registroPersonaManaged.getMpPersona().getNroDocuIdentidad())==0){
					addErrorMessage(getMsg("mp.errornrodocumentoidentidadsucesion"));
					return false;
				}
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		return true;
	}
	
	public void actualizarLstTipoDoc(Integer tipoPersonaId, Integer subTipoPersonaId){
		try{//MpTipoDocuIdentidad
	        List<MpTipoDocuIdentidad> lstMpTipoDocuIdentidad=personaBo.findMpTipoDocuIdentidad(tipoPersonaId, subTipoPersonaId); // Tipo de Persona Natural
			Iterator<MpTipoDocuIdentidad> it1 = lstMpTipoDocuIdentidad.iterator();  
			lsttipodocumentoidentidad=new ArrayList<SelectItem>();
			 
	        while (it1.hasNext()){
	        	MpTipoDocuIdentidad obj = it1.next();  
	        	lsttipodocumentoidentidad.add(new SelectItem(obj.getDescripcion(),String.valueOf(obj.getTipoDocIdentidadId())));  
	        	mapRpTipodocumentoidentidad.put(obj.getDescripcion().trim(), obj.getTipoDocIdentidadId());
	        	mapIRpTipodocumentoidentidad.put(obj.getTipoDocIdentidadId(), obj.getDescripcion().trim());
	        }
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	
	public HtmlComboBox getCmbMpTipoRelacion() {
		return cmbMpTipoRelacion;
	}
	public void setCmbMpTipoRelacion(HtmlComboBox cmbMpTipoRelacion) {
		this.cmbMpTipoRelacion = cmbMpTipoRelacion;
	}
	public List<SelectItem> getLstMpTipoRelacion() {
		return lstMpTipoRelacion;
	}
	public void setLstMpTipoRelacion(List<SelectItem> lstMpTipoRelacion) {
		this.lstMpTipoRelacion = lstMpTipoRelacion;
	}
	public HashMap<String, Integer> getMapMpTipoRelacion() {
		return mapMpTipoRelacion;
	}
	public void setMapMpTipoRelacion(HashMap<String, Integer> mapMpTipoRelacion) {
		this.mapMpTipoRelacion = mapMpTipoRelacion;
	}
	public String getCmbValueMpTipoRelacion() {
		return cmbValueMpTipoRelacion;
	}
	public void setCmbValueMpTipoRelacion(String cmbValueMpTipoRelacion) {
		this.cmbValueMpTipoRelacion = cmbValueMpTipoRelacion;
	}
	public List<MpTipoRelacion> getlMpTipoRelacion() {
		return lMpTipoRelacion;
	}
	public void setlMpTipoRelacion(List<MpTipoRelacion> lMpTipoRelacion) {
		this.lMpTipoRelacion = lMpTipoRelacion;
	}
	public Integer getMpTipoRelacionId() {
		return mpTipoRelacionId;
	}
	public void setMpTipoRelacionId(Integer mpTipoRelacionId) {
		this.mpTipoRelacionId = mpTipoRelacionId;
	}
	public HtmlComboBox getCmbtipodocumentoidentidad() {
		return cmbtipodocumentoidentidad;
	}
	public void setCmbtipodocumentoidentidad(HtmlComboBox cmbtipodocumentoidentidad) {
		this.cmbtipodocumentoidentidad = cmbtipodocumentoidentidad;
	}
	public List<SelectItem> getLsttipodocumentoidentidad() {
		return lsttipodocumentoidentidad;
	}
	public void setLsttipodocumentoidentidad(List<SelectItem> lsttipodocumentoidentidad) {
		this.lsttipodocumentoidentidad = lsttipodocumentoidentidad;
	}
	public HashMap<String, Integer> getMapRpTipodocumentoidentidad() {
		return mapRpTipodocumentoidentidad;
	}
	public void setMapRpTipodocumentoidentidad(
			HashMap<String, Integer> mapRpTipodocumentoidentidad) {
		this.mapRpTipodocumentoidentidad = mapRpTipodocumentoidentidad;
	}
	public String getCmbValuetipodocumentoidentidad() {
		return cmbValuetipodocumentoidentidad;
	}
	public void setCmbValuetipodocumentoidentidad(
			String cmbValuetipodocumentoidentidad) {
		this.cmbValuetipodocumentoidentidad = cmbValuetipodocumentoidentidad;
	}
	public List<MpTipoDocuIdentidad> getlMptipodocumentoidentidad() {
		return lMptipodocumentoidentidad;
	}
	public void setlMptipodocumentoidentidad(List<MpTipoDocuIdentidad> lMptipodocumentoidentidad) {
		this.lMptipodocumentoidentidad = lMptipodocumentoidentidad;
	}
	public Integer getTipodocumentoIdentidadId() {
		return tipodocumentoIdentidadId;
	}
	public void setTipodocumentoIdentidadId(Integer tipodocumentoIdentidadId) {
		this.tipodocumentoIdentidadId = tipodocumentoIdentidadId;
	}
	public MpRelacionado getMpRelacionado() {
		return mpRelacionado;
	}
	public void setMpRelacionado(MpRelacionado mpRelacionado) {
		this.mpRelacionado = mpRelacionado;
	}

	public Integer getTipoAccion() {
		return tipoAccion;
	}
	public void setTipoAccion(Integer tipoAccion) {
		this.tipoAccion = tipoAccion;
	}
	
	public ResultadoConsulta getResultadoConsultaReniec() {
		return resultadoConsultaReniec;
	}

	public void setResultadoConsultaReniec(ResultadoConsulta resultadoConsultaReniec) {
		this.resultadoConsultaReniec = resultadoConsultaReniec;
	}
	
	public String getRutaFotoReniec() {
		return rutaFotoReniec;
	}

	public void setRutaFotoReniec(String rutaFotoReniec) {
		this.rutaFotoReniec = rutaFotoReniec;
	}
	
}
