package com.sat.sisat.alcabala.managed;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.richfaces.component.html.HtmlComboBox;
import org.richfaces.component.html.HtmlInputText;

import com.sat.sisat.common.business.GeneralBoRemote;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.Util;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.persistence.entity.GnCondicionEspecial;
import com.sat.sisat.persistence.entity.MpDireccion;
import com.sat.sisat.persistence.entity.MpPersona;
import com.sat.sisat.persistence.entity.MpPersonaDomicilio;
import com.sat.sisat.persistence.entity.MpSituacionEmpresarial;
import com.sat.sisat.persistence.entity.MpSubtipoPersona;
import com.sat.sisat.persistence.entity.MpTipoCondicionEspecial;
import com.sat.sisat.persistence.entity.MpTipoDocuIdentidad;
import com.sat.sisat.persistence.entity.MpTipoDocumentoCondicionEspecial;
import com.sat.sisat.persistence.entity.MpTipoPersona;
import com.sat.sisat.persistence.entity.TgPersona;
import com.sat.sisat.persona.business.PersonaBoRemote;
import com.sat.sisat.predial.dto.FindMpDireccion;
import com.sat.sisat.predial.dto.FindMpPersona;
import com.sat.sisat.predial.dto.FindMpRelacionado;
import com.sat.sisat.vehicular.dto.BuscarPersonaDTO;

/**
 * @author Pc
 * RegistroPersonaManaged
 */
@ManagedBean
@ViewScoped
public class AgregarTransferenteAlcabalaManaged extends BaseManaged {
	
	@EJB
	PersonaBoRemote personaBo;
	@EJB
	GeneralBoRemote generalBo;
	private MpPersona mpPersona= new MpPersona();
	private GnCondicionEspecial gnCondicionEspecial=new GnCondicionEspecial();

	private HtmlComboBox cmbtipopersona;
	private List<SelectItem> lsttipopersona = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRpTipopersona = new HashMap<String, Integer>();
	private String cmbValuetipopersona;
	private List<MpTipoPersona> lMpTipoPersona = null;
	private List<MpTipoPersona> lMpTipoPersonatemp = new ArrayList<MpTipoPersona>();

	private HtmlComboBox cmbsubtipopersona;
	private List<SelectItem> lstsubtipopersona = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRpsubTipopersona = new HashMap<String, Integer>();
	private String cmbValuesubtipopersona;
	private List<MpSubtipoPersona> lMpsubtipoPersona = null;

	private HtmlComboBox cmbtipodocumentoidentidad;
	private List<SelectItem> lsttipodocumentoidentidad = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRpTipodocumentoidentidad = new HashMap<String, Integer>();
	private String cmbValuetipodocumentoidentidad;
	private List<MpTipoDocuIdentidad> lMptipodocumentoidentidad = null;

	private HtmlComboBox cmbtipocondicionespecial;
	private List<SelectItem> lsttipocondicionespecial = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapMpTipocondicionespecial = new HashMap<String, Integer>();
	private String cmbValuetipocondicionespecial;
	private List<MpTipoCondicionEspecial> lMptipocondicionespecial = null;
	
	private HtmlComboBox cmbtipodocumentoCondicionEspecial;
	private List<SelectItem> lsttipodocumentoCondicionEspecial= new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapMpTipodocumentoCondicionEspecial = new HashMap<String, Integer>();
	private String cmbValuetipodocumentoCondicionEspecial;
	
	private HtmlComboBox cmbMpSituacionEmpresarial;
	private List<SelectItem> lstMpSituacionEmpresarial = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapMpSituacionEmpresarial = new HashMap<String, Integer>();
	private String cmbValueMpSituacionEmpresarial;
	private List<MpSituacionEmpresarial> lMpSituacionEmpresarial = null;

	private Integer tipoPersonaId;
	private Integer subtipoPersonaId;
	private Integer tipodocumentoId;
	private Integer tipoCondicionEspecialId;
	private Integer tipodocumentoIdentidadId;
	private Integer situacionEmpresarialId;
	private Integer registroDomicilio=0; 
	private String tipoDomicilio=Constante.DIRECCION_FISCAL;
	private Integer tipoAccion=Constante.TIPO_ACCION_NUEVO;
	private Boolean generadoDJ;
	private Boolean istipodocumentoadicional;
	private Boolean istipopersonaNatural;
	private Boolean israzonsocial;
	private Boolean issucesionindivisa;
	private Boolean issituacionempresarial;
	private Boolean istipocondicionespecial;
	private Boolean isDomiciliosIngresados;
	private Boolean isPersonaexiste;
	private Boolean isSubtipoPersona;
	private Boolean isTipoDocumentoIdentidad;
	private Boolean isRelacionados;
	private Boolean isAccionRealizada=Boolean.TRUE;
	private Boolean tipoAccionVistaGuardarActualizar=Boolean.FALSE;
	
	private java.util.Date fechaInscripcion;
	private java.util.Date fechaNacoConst;
	private java.util.Date fechaInicioCond;
	private java.util.Date fechaDocumento;
	private java.util.Date fechaFinCond;
	private java.util.Date fechaIniSituacionE;
	private java.util.Date fechaFinSituacionE;
	private java.util.Date fechaEmisionDoc;
	private java.util.Date fechaDefuncion;
	
	private ArrayList<FindMpRelacionado> recordsRelacionado;
	private ArrayList<FindMpDireccion> recordsDireccion;
	private FindMpPersona finMpPersonaItem;
	private FindMpRelacionado findMpRelacionadoItem;
	
	private MpDireccion mpDireccion;
	private MpPersonaDomicilio mpPersonaDomicilio;
	
	private FindMpPersona	findMpPersona;
	
	//Buscar persona desde RENIEC
	private HtmlInputText txtNumDocumento = new HtmlInputText();
	private HtmlInputText txtApePaterno = new HtmlInputText();
	private HtmlInputText txtApeMaterno = new HtmlInputText();
	private HtmlInputText txtPrimerNombre = new HtmlInputText();
	private HtmlInputText txtSegundoNombre = new HtmlInputText();
	
	public AgregarTransferenteAlcabalaManaged() {
		getSessionManaged().setLinkRegresar(
				"/sisat/persona/buscarpersona.xhtml");
	}
	
	@PostConstruct
	public void init(){
		try{ 
			
			
			
			
			mpDireccion=new MpDireccion();
			mpPersonaDomicilio=new MpPersonaDomicilio();
				mpPersona= new MpPersona();
				Object obj = getSessionMap().get("nuevoPersona");
				if (obj != null) {
				mpPersona = (MpPersona)obj;
				getSessionMap().remove("nuevoPersona");
				}
				recordsDireccion = new ArrayList<FindMpDireccion>();
				recordsRelacionado = new ArrayList<FindMpRelacionado>();
				istipodocumentoadicional = Boolean.FALSE;
				istipopersonaNatural = Boolean.FALSE;
				israzonsocial = Boolean.FALSE;
				issucesionindivisa=Boolean.FALSE;
				issituacionempresarial=Boolean.FALSE;
				istipocondicionespecial=Boolean.FALSE;
				isPersonaexiste= Boolean.FALSE;
				isDomiciliosIngresados=Boolean.FALSE;
				isSubtipoPersona=Boolean.TRUE;
				isTipoDocumentoIdentidad=Boolean.TRUE;
				isRelacionados=Boolean.TRUE;
				isAccionRealizada=Boolean.TRUE;
				generadoDJ=Constante.NO_GENERADO_DJ;
				
				//MpTipoPersona
		        List<MpTipoPersona> lstMpTipoPersona=personaBo.getAllMpTipoPersona();
		        lMpTipoPersona=lstMpTipoPersona;
				Iterator<MpTipoPersona> it = lstMpTipoPersona.iterator();  
				lsttipopersona=new ArrayList<SelectItem>();
				 
		        while (it.hasNext()){
		        	MpTipoPersona obj01 = it.next();  
		        	lsttipopersona.add(new SelectItem(obj01.getDescripcion(),String.valueOf(obj01.getTipoPersonaId())));  
		        	mapRpTipopersona.put(obj01.getDescripcion().trim(), obj01.getTipoPersonaId());
		        }
		        //MpTipoDocumento
		        List<MpTipoDocumentoCondicionEspecial> lGntipodocumento=personaBo.getAllMpTipoDocumentoCondicionEspecial();
				Iterator<MpTipoDocumentoCondicionEspecial> it2 = lGntipodocumento.iterator();  
				setLsttipodocumentoCondicionEspecial(new ArrayList<SelectItem>());
				 
		        while (it2.hasNext()){
		        	MpTipoDocumentoCondicionEspecial obj1 = it2.next();  
		        	getLsttipodocumentoCondicionEspecial().add(new SelectItem(obj1.getDescripcion(),String.valueOf(obj1.getTipoDocumentoCondicionEspecialId())));  
		        	mapMpTipodocumentoCondicionEspecial.put(obj1.getDescripcion().trim(), obj1.getTipoDocumentoCondicionEspecialId());
		        }
		
				setCmbValueMpSituacionEmpresarial(Constante.SITUACION_EMPRESARIAL_DEFECTO);
				setCmbValuetipocondicionespecial(Constante.CONDICION_ESPECIAL_DEFECTO);
				gnCondicionEspecial=new GnCondicionEspecial();
				/**Ingreso Para editar*/
				finMpPersonaItem= new FindMpPersona();
				Object objtemp = getSessionMap().get("finMpPersonaItem");
				
				if (objtemp != null) {
					finMpPersonaItem = (FindMpPersona)objtemp;
					getSessionMap().remove("finMpPersonaItem");
					mpPersona = new MpPersona();
					mpPersona = finMpPersonaItem.getMpPersona();
					fechaNacoConst=mpPersona.getFechaDeclaracion();
					tipoAccion=Constante.TIPO_ACCION_EDITAR;
					obtenerDatos();
				
				}
				findMpRelacionadoItem= new FindMpRelacionado();
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	//cchaucca 02/07/2012 obtiene datos de la base de reniec
	public void buscarPersonaDNI(){
		try{
			//if(verificarPersona()){
					if(validaNroDocumento()){
							if(getCmbtipodocumentoidentidad().getValue()!=null&&getCmbtipodocumentoidentidad().getValue().equals(Constante.TIPO_DOCUMENTO_DNI)){
								if(getTxtNumDocumento().getSubmittedValue()!=null&&getTxtNumDocumento().getSubmittedValue().toString().length()==8){
									TgPersona persona=personaBo.buscarPersonaReniec(getTxtNumDocumento().getSubmittedValue().toString());	
									if(persona!=null){
										getTxtPrimerNombre().setValue(persona.getNombre());
										getTxtApePaterno().setValue(persona.getApe_pat());
										getTxtApeMaterno().setValue(persona.getApe_mat());
										
										getMpPersona().setApePaterno(persona.getApe_pat());
										getMpPersona().setApeMaterno(persona.getApe_mat());
										getMpPersona().setPrimerNombre(persona.getNombre());
									}else{
										getTxtPrimerNombre().setValue("");
										getTxtSegundoNombre().setValue("");
										getTxtApePaterno().setValue("");
										getTxtApeMaterno().setValue("");
										
										getMpPersona().setApePaterno("");
										getMpPersona().setApeMaterno("");
										getMpPersona().setPrimerNombre("");
									}
								}
							}
					}
			//}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	
	public void obtenerDatos(){
		try{
			
				if(mpPersona.getFlagNotificaEmail().compareTo(Constante.FLAG_ACTIVO)==0)
					 mpPersona.setNotificaEmail(true);
				else if(mpPersona.getFlagNotificaEmail().compareTo(Constante.FLAG_INACTIVO)==0)
				     mpPersona.setNotificaEmail(false);
			    tipoPersonaId =mpPersona.getTipoPersonaId();
				setCmbValuetipopersona(finMpPersonaItem.getTipoPersona());
				subtipoPersonaId = finMpPersonaItem.getSubtipoPersonaId();
				setCmbValuesubtipopersona(finMpPersonaItem.getSubtipopersona());
				
				if(getCmbValuetipopersona().compareTo(Constante.TIPO_PERSONA_JURIDICA)==0 || getCmbValuetipopersona().toString().compareTo(Constante.TIPO_PERSONA_PATRIMONIO)==0){
					istipodocumentoadicional=Boolean.TRUE;
					israzonsocial=Boolean.TRUE;
				    istipopersonaNatural=Boolean.FALSE;}
				else if(getCmbValuetipopersona().toString().compareTo(Constante.TIPO_PERSONA_NATURAL)==0 || getCmbValuetipopersona().toString().compareTo(Constante.TIPO_PERSONA_SOC_CONYUGAL)==0){
					istipodocumentoadicional=Boolean.FALSE;
					istipopersonaNatural=Boolean.TRUE;
					israzonsocial=Boolean.FALSE;
				    }
				if(finMpPersonaItem.getSituacionEmpresarial()!=null){
					if(getCmbValuetipopersona().compareTo(Constante.TIPO_PERSONA_JURIDICA)==0 || getCmbValuetipopersona().toString().compareTo(Constante.TIPO_PERSONA_PATRIMONIO)==0){
						if(finMpPersonaItem.getSituacionEmpresarial().compareTo(Constante.SITUACION_EMPRESARIAL_DEF_PERJURID_PATRIAUTO)==0){
							setCmbValueMpSituacionEmpresarial(Constante.SITUACION_EMPRESARIAL_DEF_PERJURID_PATRIAUTO);
							issituacionempresarial=Boolean.FALSE;
							situacionEmpresarialId=finMpPersonaItem.getSituacionEmpresarialId();
						}else if(finMpPersonaItem.getSituacionEmpresarial().compareTo(Constante.SITUACION_EMPRESARIAL_DEF_PERJURID_PATRIAUTO)!=0){
							setCmbValueMpSituacionEmpresarial(finMpPersonaItem.getSituacionEmpresarial());
							situacionEmpresarialId=finMpPersonaItem.getSituacionEmpresarialId();
							issituacionempresarial=Boolean.TRUE;
						    setFechaIniSituacionE(finMpPersonaItem.getFechaSituacionEmpresarial());
						    setFechaFinSituacionE(finMpPersonaItem.getFechaFinSituacionEmpresarial());
						    setFechaEmisionDoc(finMpPersonaItem.getFechaEmisionSituacionEmpresarial());
						    fechaIniSituacionE=finMpPersonaItem.getFechaSituacionEmpresarial();
						    fechaFinSituacionE=finMpPersonaItem.getFechaFinSituacionEmpresarial();
						    fechaEmisionDoc=finMpPersonaItem.getFechaEmisionSituacionEmpresarial();
						}
					}
					else if(getCmbValuetipopersona().toString().compareTo(Constante.TIPO_PERSONA_NATURAL)==0 || getCmbValuetipopersona().toString().compareTo(Constante.TIPO_PERSONA_SOC_CONYUGAL)==0){
							if(finMpPersonaItem.getSituacionEmpresarial().compareTo(Constante.SITUACION_EMPRESARIAL_DEFECTO)==0){
								setCmbValueMpSituacionEmpresarial(Constante.SITUACION_EMPRESARIAL_DEFECTO);
								issituacionempresarial=Boolean.FALSE;
								situacionEmpresarialId=finMpPersonaItem.getSituacionEmpresarialId();
							}else if(finMpPersonaItem.getSituacionEmpresarial().compareTo(Constante.SITUACION_EMPRESARIAL_DEFECTO)!=0){
								setCmbValueMpSituacionEmpresarial(finMpPersonaItem.getSituacionEmpresarial());
								situacionEmpresarialId=finMpPersonaItem.getSituacionEmpresarialId();
								issituacionempresarial=Boolean.TRUE;
							    setFechaIniSituacionE(finMpPersonaItem.getFechaSituacionEmpresarial());
							    setFechaFinSituacionE(finMpPersonaItem.getFechaFinSituacionEmpresarial());
							    setFechaEmisionDoc(finMpPersonaItem.getFechaEmisionSituacionEmpresarial());
							}
					}
				}else if(finMpPersonaItem.getSituacionEmpresarial()==null){		
							setCmbValueMpSituacionEmpresarial(Constante.SITUACION_EMPRESARIAL_DEFECTO);
							issituacionempresarial=Boolean.FALSE;
							situacionEmpresarialId=Constante.SITUACION_EMPRESARIAL_ID_DEFECTO;}
				gnCondicionEspecial=personaBo.findCondicionEspecial(mpPersona);
				if(gnCondicionEspecial==null){
					setCmbValuetipocondicionespecial(Constante.CONDICION_ESPECIAL_DEFECTO);
					istipocondicionespecial=Boolean.FALSE;
					gnCondicionEspecial=new GnCondicionEspecial();
				}else if(gnCondicionEspecial!=null){
					MpTipoCondicionEspecial tempce=new MpTipoCondicionEspecial();
					tempce = personaBo.finMpTipoCondicionEspecial(gnCondicionEspecial.getTipoCondEspecialId());
					//setCmbValuetipocondicionespecial(finMpPersonaItem.getTipoCondicionEspecial());
					if(tempce!=null)
					setCmbValuetipocondicionespecial(tempce.getDescripcion());
					istipocondicionespecial=Boolean.TRUE;
					tipoCondicionEspecialId=finMpPersonaItem.getTipoCondicionEspecialId();
				    setFechaDocumento(gnCondicionEspecial.getFechaDocumento());
				    setFechaInicioCond(gnCondicionEspecial.getFechaInicio());
				    setFechaFinCond(gnCondicionEspecial.getFechaFin());
				    setCmbValuetipodocumentoCondicionEspecial(finMpPersonaItem.getTipodocumento());
				}
				
				
				if(getCmbValuesubtipopersona().compareTo(Constante.SUB_TIPO_PERSONA_SUC_INDIVISA)==0){
					issucesionindivisa=Boolean.TRUE;
					fechaDefuncion=finMpPersonaItem.getFechaDefuncion();}
				else if(getCmbValuesubtipopersona().compareTo(Constante.SUB_TIPO_PERSONA_SUC_INDIVISA)!=0){ 
					issucesionindivisa=Boolean.FALSE;}
				
				tipodocumentoIdentidadId = finMpPersonaItem.getTipoDocIdentidadId();
				setCmbValuetipodocumentoidentidad(finMpPersonaItem.getTipoDocumentoIdentidad());
				
				tipodocumentoId = finMpPersonaItem.getTipodocumentoId();
				setCmbValuetipodocumentoCondicionEspecial(finMpPersonaItem.getTipodocumento());
				
				
				recordsRelacionado=personaBo.findMpRelacionadoPersona(finMpPersonaItem.getPersonaId());
			    recordsDireccion=personaBo.finMpDireccionPersona(finMpPersonaItem.getPersonaId());
			    if(mpPersona.getNroDj()!=null&&mpPersona.getNroDj()>0&&recordsDireccion.size()==0)
			    {
			    			recordsDireccion=personaBo.finMpDireccionPersona(finMpPersonaItem.getPersonaId(),Constante.ESTADO_PENDIENTE_ACTUALIZACION);
				    	if(recordsDireccion.size()==0){
				    		recordsDireccion=personaBo.finMpDireccionPersona(finMpPersonaItem.getPersonaId(),Constante.ESTADO_ELIMINADO);
				    		personaBo.actualizarEstadoMpDireccion(recordsDireccion.get(0).getDireccionId(), Constante.ESTADO_ACTIVO);
							personaBo.actualizarEstadoMpPersonaDomicilio(recordsDireccion.get(0).getPersonaDomicilio(), Constante.ESTADO_ACTIVO);
							recordsDireccion = new ArrayList<FindMpDireccion>();
							recordsDireccion=personaBo.finMpDireccionPersona(finMpPersonaItem.getPersonaId());
						 }
			    	    if(recordsDireccion.size()>0){
			    	    	personaBo.actualizarEstadoMpDireccion(recordsDireccion.get(0).getDireccionId(), Constante.ESTADO_ACTIVO);
							personaBo.actualizarEstadoMpPersonaDomicilio(recordsDireccion.get(0).getPersonaDomicilio(), Constante.ESTADO_ACTIVO);
							recordsDireccion = new ArrayList<FindMpDireccion>();
							recordsDireccion=personaBo.finMpDireccionPersona(finMpPersonaItem.getPersonaId());
						}
			    }
			    
			    
		        if(recordsDireccion.size()>0){
		        	isDomiciliosIngresados=Boolean.TRUE;
	
		        }
		        if(finMpPersonaItem.getNroDj()==null || finMpPersonaItem.getNroDj()<=0){
		        	generadoDJ=Constante.NO_GENERADO_DJ;
		        }else
		        	generadoDJ=Constante.GENERADO_DJ;
		        viewcmbMpSubtipoPersona();
		        viewcmbMpCondicionContribuyente();
		        viewcmbMpSituacionEmpresarial();
		        viewcmbtipodocumento();
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	

	public void viewcmbMpSubtipoPersona(){

	try{
		//MpSubtipoPersona
		if(tipoPersonaId!=null){
	        List<MpSubtipoPersona> lstMpsubTipoPersona=personaBo.finMpSubtipoPersona(tipoPersonaId);
			Iterator<MpSubtipoPersona> it = lstMpsubTipoPersona.iterator();  
			lstsubtipopersona=new ArrayList<SelectItem>();
			 
	        while (it.hasNext()){
	        	MpSubtipoPersona obj = it.next();  
	        	lstsubtipopersona.add(new SelectItem(obj.getDescripcion(),String.valueOf(obj.getSubtipoPersonaId())));  
	        	mapRpsubTipopersona.put(obj.getDescripcion().trim(), obj.getSubtipoPersonaId());
	        	
	        }
		}
	}catch(Exception e){
		e.printStackTrace();
		WebMessages.messageFatal(e);			
	}
	
	
}

	public void viewcmbMpCondicionContribuyente(){

	try{
		//MpTipoCondicionEspecial
	        List<MpTipoCondicionEspecial> lMptipocondicionespecial=personaBo.getAllMpTipoCondicionEspecial(tipoPersonaId,subtipoPersonaId);
			Iterator<MpTipoCondicionEspecial> it1 = lMptipocondicionespecial.iterator();  
			lsttipocondicionespecial=new ArrayList<SelectItem>();
			 
	        while (it1.hasNext()){
	        	MpTipoCondicionEspecial obj1 = it1.next();  
	        	lsttipocondicionespecial.add(new SelectItem(obj1.getDescripcion(),String.valueOf(obj1.getTipoCondEspecialId())));  
	        	mapMpTipocondicionespecial.put(obj1.getDescripcion().trim(), obj1.getTipoCondEspecialId());
	        }      
	        if(getCmbValuetipocondicionespecial()==null || getCmbValuetipocondicionespecial().toString().compareTo("")==0){
	        		setCmbValuetipocondicionespecial(Constante.CONDICION_ESPECIAL_DEFECTO);
	        		istipocondicionespecial=Boolean.FALSE;
	        }
	        if(getCmbValuesubtipopersona().toString().compareTo(Constante.SUB_TIPO_PERSONA_MENOR_EDAD)==0){
	        	setCmbValuetipocondicionespecial(Constante.CONDICION_ESPECIAL_DEFECTO);
	        	tipoCondicionEspecialId=Constante.CONDICION_ESPECIAL_DEFECTO_ID;
	        	
        		istipocondicionespecial=Boolean.FALSE;
	        }
	}catch(Exception e){
		e.printStackTrace();
		WebMessages.messageFatal(e);			
	}
	
	
}

	public void viewcmbMpSituacionEmpresarial(){

	try{
		 //MpSituacionEmpresarial
		if(tipoPersonaId!=null){
	        List<MpSituacionEmpresarial> lMpSituacionEmpresarial=personaBo.getAllMpSituacionEmpresarial(tipoPersonaId);
			Iterator<MpSituacionEmpresarial> it3 = lMpSituacionEmpresarial.iterator();  
			lstMpSituacionEmpresarial=new ArrayList<SelectItem>();
			 
	        while (it3.hasNext()){
	        	MpSituacionEmpresarial obj1 = it3.next();  
	        	lstMpSituacionEmpresarial.add(new SelectItem(obj1.getDescripcion(),String.valueOf(obj1.getSituacionEmpresarialId())));  
	        	mapMpSituacionEmpresarial.put(obj1.getDescripcion().trim(), obj1.getSituacionEmpresarialId());
	        }
		}
	}catch(Exception e){
		e.printStackTrace();
		WebMessages.messageFatal(e);			
	}
	
	
}

	public void viewcmbtipodocumento(){
	try{//MpTipoDocumento
		  List<MpTipoDocuIdentidad> lstMpTipoDocuIdentidad=personaBo.findMpTipoDocuIdentidad(tipoPersonaId, subtipoPersonaId);
		Iterator<MpTipoDocuIdentidad> it1 = lstMpTipoDocuIdentidad.iterator();  
		lsttipodocumentoidentidad=new ArrayList<SelectItem>();
		 
        while (it1.hasNext()){
        	MpTipoDocuIdentidad obj = it1.next();  
        	lsttipodocumentoidentidad.add(new SelectItem(obj.getDescripcion(),String.valueOf(obj.getTipoDocIdentidadId())));  
        	mapRpTipodocumentoidentidad.put(obj.getDescripcion().trim(), obj.getTipoDocIdentidadId());
        	
        }
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	
}

	/*load de listas/*/
	public void loadRelacionados(int personaId){
	try{
		recordsRelacionado=personaBo.findMpRelacionadoPersona(personaId);
	}catch(Exception e){
		e.printStackTrace();
		WebMessages.messageFatal(e);
	}
}

	public void loadDirecciones(int personaId){
	try{
		recordsDireccion=personaBo.finMpDireccionPersona(personaId);
		
	    
	}catch(Exception e){
		e.printStackTrace();
		WebMessages.messageFatal(e);
	}
	}
	public void loadDireccionesTemporales(int personaId){
	try{
		recordsDireccion=personaBo.finMpDireccionPersona(personaId);
	
	}catch(Exception e){
		e.printStackTrace();
		WebMessages.messageFatal(e);
	}
}

/* Load de los Combos  */
	public void loadTipoPersonaById(ValueChangeEvent event) {
	try{
		HtmlComboBox combo=(HtmlComboBox) event.getComponent(); 
	    String value=combo.getValue().toString();
	    if(value!=null){
			tipoPersonaId = (Integer)mapRpTipopersona.get(value);
			setCmbValuetipopersona(value);
			if(tipoPersonaId != null){
				lMpTipoPersona=personaBo.findMpTipoPersona(tipoPersonaId);	
			}
			mpPersona.setTipoPersonaId(tipoPersonaId);
		}
		cmbValuesubtipopersona="";
		cmbValuetipodocumentoidentidad="";
		mpPersona.setNroDocuIdentidad("");
		mpPersona.setNotificaEmail(false);
		viewcmbMpSubtipoPersona();
		  //if(tipoAccion.compareTo(Constante.TIPO_ACCION_NUEVO)==0)
		  //	 viewcmbtipodocumento();
		viewcmbMpSituacionEmpresarial();
		setCmbValueMpSituacionEmpresarial(Constante.SITUACION_EMPRESARIAL_DEFECTO);
		setCmbValuetipocondicionespecial(Constante.CONDICION_ESPECIAL_DEFECTO);
		issituacionempresarial=Boolean.FALSE;
		istipocondicionespecial=Boolean.FALSE;
		if(value!=null){
				if(value.compareTo(Constante.TIPO_PERSONA_JURIDICA)==0){
					istipodocumentoadicional=Boolean.TRUE;
					israzonsocial=Boolean.TRUE;
				    istipopersonaNatural=Boolean.FALSE;
				    isSubtipoPersona=Boolean.TRUE;
				    isTipoDocumentoIdentidad=Boolean.TRUE;
				    isRelacionados=Boolean.TRUE;
				    setCmbValueMpSituacionEmpresarial(Constante.SITUACION_EMPRESARIAL_DEF_PERJURID_PATRIAUTO);
				    situacionEmpresarialId=Constante.SITUACION_EMPRESARIAL_ID_DEFECTO_PERJURID_PATRIAUTO;
					}
				else if(value.toString().compareTo(Constante.TIPO_PERSONA_NATURAL)==0){
					istipodocumentoadicional=Boolean.FALSE;
					istipopersonaNatural=Boolean.TRUE;
					israzonsocial=Boolean.FALSE;
					isSubtipoPersona=Boolean.TRUE;
					isTipoDocumentoIdentidad=Boolean.TRUE;
					isRelacionados=Boolean.TRUE;
					situacionEmpresarialId=Constante.SITUACION_EMPRESARIAL_ID_DEFECTO;
						
				    }
				else if(value.toString().compareTo(Constante.TIPO_PERSONA_SOC_CONYUGAL)==0){
					isSubtipoPersona=Boolean.TRUE;
					istipodocumentoadicional=Boolean.FALSE;
					istipopersonaNatural=Boolean.TRUE;
					israzonsocial=Boolean.FALSE;
					isTipoDocumentoIdentidad=Boolean.TRUE;
					isRelacionados=Boolean.TRUE;
					  situacionEmpresarialId=Constante.SITUACION_EMPRESARIAL_ID_DEFECTO;
				    }
				else if(value.toString().compareTo(Constante.TIPO_PERSONA_PATRIMONIO)==0){
					istipodocumentoadicional=Boolean.TRUE;
					israzonsocial=Boolean.TRUE;
				    istipopersonaNatural=Boolean.FALSE;
				    isSubtipoPersona=Boolean.TRUE;
				    isTipoDocumentoIdentidad=Boolean.TRUE;
				    isRelacionados=Boolean.TRUE;
				    setCmbValueMpSituacionEmpresarial(Constante.SITUACION_EMPRESARIAL_DEF_PERJURID_PATRIAUTO);
				    situacionEmpresarialId=Constante.SITUACION_EMPRESARIAL_ID_DEFECTO_PERJURID_PATRIAUTO;
					
				}
		}
	}catch(Exception e){
		e.printStackTrace();
		WebMessages.messageFatal(e);			
	}
}

	public void loadSubtipoPersonaById(ValueChangeEvent event) {
	try{
		   HtmlComboBox combo=(HtmlComboBox) event.getComponent(); 
		    String value=combo.getValue().toString();
			if(value!=null){
			subtipoPersonaId=(Integer)mapRpsubTipopersona.get(value);
			mpPersona.setSubtipoPersonaId(subtipoPersonaId);
			setCmbValuesubtipopersona(value);
			if(tipoPersonaId!=null){
				lMpsubtipoPersona=personaBo.finMpSubtipoPersona(tipoPersonaId);	
			}
	
		}
			
		if(tipoAccion.compareTo(Constante.TIPO_ACCION_NUEVO)==0){
				viewcmbtipodocumento();
				mpPersona.setNroDocuIdentidad(null);
				}
		 viewcmbMpCondicionContribuyente();
    	setCmbValuetipocondicionespecial(Constante.CONDICION_ESPECIAL_DEFECTO);
    	tipoCondicionEspecialId=Constante.CONDICION_ESPECIAL_DEFECTO_ID;
    	istipocondicionespecial=Boolean.FALSE;
		gnCondicionEspecial=new GnCondicionEspecial();
    	
		// 
		 isTipoDocumentoIdentidad=Boolean.TRUE;
		 isRelacionados=Boolean.TRUE;	
		 issucesionindivisa=Boolean.FALSE;
		if(value.compareTo(Constante.SUB_TIPO_PERSONA_SOCIEDAD_IRREGULAR)==0){
			 isTipoDocumentoIdentidad=Boolean.TRUE;
			 isRelacionados=Boolean.TRUE;	
			 issucesionindivisa=Boolean.FALSE;
		}
		else if(value.compareTo(Constante.SUB_TIPO_PERSONA_PERSONA_INDIVIDUAL)==0){
			isTipoDocumentoIdentidad=Boolean.TRUE;
			issucesionindivisa=Boolean.FALSE;
			isRelacionados=Boolean.FALSE;
			}
		else if(value.compareTo(Constante.SUB_TIPO_PERSONA_SUC_INDIVISA)==0){
			isTipoDocumentoIdentidad=Boolean.TRUE;
			issucesionindivisa=Boolean.TRUE;
			isRelacionados=Boolean.TRUE;
			}
		
	
	
	}catch(Exception e){
		e.printStackTrace();
		WebMessages.messageFatal(e);			
	}
}

	public void loadTipoTipoDocumentoIdentidadById(ValueChangeEvent event) {
	try{
		   HtmlComboBox combo=(HtmlComboBox) event.getComponent(); 
		    String value=combo.getValue().toString();
		
		if(value!=null){
			tipodocumentoIdentidadId = (Integer)mapRpTipodocumentoidentidad.get(value);
			setCmbValuetipodocumentoidentidad(value);
			if(tipodocumentoIdentidadId!=null){
				lMptipodocumentoidentidad=personaBo.findMpTipoDocuIdentidad(tipoPersonaId, subtipoPersonaId);	
			}
		}
		if(!getCmbValuetipodocumentoidentidad().equalsIgnoreCase(Constante.TIPO_DOCUMENTO_RUC)){
			istipodocumentoadicional=Boolean.FALSE;
		}else istipodocumentoadicional=Boolean.TRUE;
		mpPersona.setTipoDocIdentidadId(tipodocumentoIdentidadId);
		mpPersona.setNroDocuIdentidad("");
	}catch(Exception e){
		e.printStackTrace();
		WebMessages.messageFatal(e);			
	}
}

	public void loadTipoTipoCondicionEspecialById(ValueChangeEvent event) {
	try{
		   HtmlComboBox combo=(HtmlComboBox) event.getComponent(); 
		    String value=combo.getValue().toString();
		  if(value!=null&&value.trim().length()>0){
				tipoCondicionEspecialId = (Integer)mapMpTipocondicionespecial.get(value);
				setCmbValuetipocondicionespecial(value);
		
				if(tipoCondicionEspecialId!=null){
					lMptipocondicionespecial=personaBo.findMpTipoCondicionEspecial(tipoCondicionEspecialId);	
				 }
			    if(value.compareTo(Constante.CONDICION_ESPECIAL_DEFECTO)==0){
				       istipocondicionespecial=Boolean.FALSE;}
				else{ istipocondicionespecial=Boolean.TRUE; }
	     }
	}catch(Exception e){
		e.printStackTrace();
		WebMessages.messageFatal(e);			
	}
}

	public void loadMpTipoDocumentoCondicionEspecialById(ValueChangeEvent event) {
	try{
			   HtmlComboBox combo=(HtmlComboBox) event.getComponent(); 
		    String value=combo.getValue().toString();
		
		    if(value!=null&&value.trim().length()>0){
			tipodocumentoId = (Integer)mapMpTipodocumentoCondicionEspecial.get(value);
			setCmbValuetipodocumentoCondicionEspecial(value);
			if(tipodocumentoId!=null){
				//setlMptipodocumentoCondicionEspecial(personaBo.findGnTipoDocumento(tipodocumentoId));	
			}
		}

	}catch(Exception e){
		e.printStackTrace();
		WebMessages.messageFatal(e);			
	}
}

	public void loadMpSituacionEmpresarialById(ValueChangeEvent event) {
	try{
			HtmlComboBox combo=(HtmlComboBox) event.getComponent(); 
		    String value=combo.getValue().toString();
		    if(value!=null&&value.trim().length()>0){
				situacionEmpresarialId = (Integer)mapMpSituacionEmpresarial.get(value);
				setCmbValueMpSituacionEmpresarial(value);
				if(situacionEmpresarialId!=null){
						lMpSituacionEmpresarial=personaBo.findMpSituacionEmpresarial(situacionEmpresarialId);	
					}
				if(value.compareTo(Constante.SITUACION_EMPRESARIAL_DEFECTO)==0 || value.compareTo(Constante.SITUACION_EMPRESARIAL_DEF_PERJURID_PATRIAUTO)==0){
					
					issituacionempresarial=Boolean.FALSE;}
				else{ issituacionempresarial=Boolean.TRUE; }
	    }
	}catch(Exception e){
		e.printStackTrace();
		WebMessages.messageFatal(e);			
	}
}
/*Fin de load Combos*/
	

	public boolean validaNroDocumento(){
		if(tipoAccion.compareTo(Constante.TIPO_ACCION_EDITAR)!=0){
			String nroDoc=mpPersona.getNroDocuIdentidad();
				if(getTxtNumDocumento().getSubmittedValue()!=null){
				 nroDoc=getTxtNumDocumento().getSubmittedValue().toString();
				 mpPersona.setNroDocuIdentidad(nroDoc);
				}
				 if(cmbValuetipodocumentoidentidad.compareTo(Constante.TIPO_DOCUMENTO_DNI)==0){
					for(int i=0;i<Constante.LISTA_DNI_INCORRECTOS_LENGHT;i++){
						if(nroDoc.toString().compareTo(Constante.LISTA_DNI_INCORRECTOS[i])==0){
							addErrorMessage(getMsg("mp.errornrodocumentoidentidaddni"));
							return false;
						}
					}
				}
				if(cmbValuetipodocumentoidentidad.compareTo(Constante.TIPO_DOCUMENTO_RUC)==0){
					for(int i=0;i<Constante.LISTA_RUC_INCORRECTOS_LENGHT;i++){
						if(nroDoc.toString().compareTo(Constante.LISTA_RUC_INCORRECTOS[i])==0){
							addErrorMessage(getMsg("mp.errornrodocumentoidentidadruc"));
							return false;
						}
					}
				}
		}
		return true;
	}
	
	public boolean validarDatosMpPersona()throws Exception{
		try{
			if(tipoPersonaId==null){
				addErrorMessage(getMsg("mp.errortipocontribuyente"));
			     return false;
			}
			if(subtipoPersonaId==null){
				addErrorMessage(getMsg("mp.errorsubtipocontribuyente"));
			     return false;
			}
			if(tipodocumentoIdentidadId==null){
				addErrorMessage(getMsg("mp.errortipodocumento"));
			     return false;
			}
			
			if(!validaNroDocumento())
			   return false;
			
			if(getCmbValuetipodocumentoidentidad()==null || getCmbValuetipodocumentoidentidad().trim().length()==0){
				 addErrorMessage(getMsg("mp.errordocumentoidentidad"));
			     return false;
			}
			if(getCmbValuetipodocumentoidentidad().compareTo(Constante.TIPO_DOCUMENTO_DNI)==0){
				if(mpPersona.getNroDocuIdentidad().length()!=Constante.CANTIDAD_DIGITOS_DNI){
				 addErrorMessage(getMsg("mp.errornrodocumentoidentidaddni"));
				 return false;
				 }
			 } 
			if(getCmbValuetipodocumentoidentidad().compareTo(Constante.TIPO_DOCUMENTO_RUC)==0){
				if(mpPersona.getNroDocuIdentidad().length()!=Constante.CANTIDAD_DIGITOS_RUC){
				 addErrorMessage(getMsg("mp.errornrodocumentoidentidadruc"));
			     return false ;
				}
				if(mpPersona.getNroDocuIdentidad().substring(0,2).compareTo("10")!=0 && mpPersona.getNroDocuIdentidad().substring(0,2).compareTo("20")!=0){
					 addErrorMessage(getMsg("mp.errorformatonrodocumentoidentidadruc"));
				     return false ;
				}
			}
			if(getCmbValuetipopersona().compareTo(Constante.TIPO_PERSONA_JURIDICA)==0){
				if(mpPersona.getRazonSocial()==null || mpPersona.getRazonSocial().trim().length()==0){
					 addErrorMessage(getMsg("mp.razonsocialiningresar"));
					 return false;
				}
				if(recordsRelacionado.size()==0){
					 addErrorMessage(getMsg("mp.relacionado"));
					 return false;
				}
				if(getCmbValuesubtipopersona().toString().compareTo(Constante.SUB_TIPO_PERSONA_SOCIEDAD_REGULAR)==0 || getCmbValuesubtipopersona().toString().compareTo(Constante.SUB_TIPO_PERSONA_SOCIEDAD_IRREGULAR)==0){
					if(recordsRelacionado.size()>0){
						Integer tempR=0;
						for(int i=0;i<recordsRelacionado.size();i++){
							FindMpRelacionado temp = recordsRelacionado.get(i);
		 					 if(temp.getRelacionadoId()==Constante.TIPO_RELACIONADO_PADREOTUTOR_ID){
								//  addErrorMessage(getMsg("mp.errormenoredadconyuge"));
								 // return false;
		 						 tempR=1;
		 						 break;
							}
						}
						if(tempR==0){
							addErrorMessage(getMsg("mp.relacionado"));
							 return false;
						}	
					}
				}
				if(!verificarSituacionEmpresarial(Constante.TIPO_PERSONA_JURIDICA,Constante.SITUACION_EMPRESARIAL_ID_DEFECTO_PERJURID_PATRIAUTO))
					return false;
			}
			if(getCmbValuetipopersona().toString().compareTo(Constante.TIPO_PERSONA_NATURAL)==0){
				if(mpPersona.getApePaterno()==null || mpPersona.getApePaterno().trim().length()==0){
					 addErrorMessage(getMsg("mp.apellidopaternosiningresar"));
					 return false;
				}
				if(mpPersona.getApeMaterno()==null || mpPersona.getApeMaterno().trim().length()==0){
					 addErrorMessage(getMsg("mp.apellidomaternosiningresar"));
					 return false;
				}
				if(mpPersona.getPrimerNombre()==null || mpPersona.getPrimerNombre().trim().length()==0){
					 addErrorMessage(getMsg("mp.primernombresiningresar"));
					 return false;
				}
				if(getCmbValuesubtipopersona().toString().compareTo(Constante.SUB_TIPO_PERSONA_PERSONA_INDIVIDUAL)!=0)
					if(recordsRelacionado.size()==0){
						 addErrorMessage(getMsg("mp.relacionado"));
						 return false;
					}
				if(getCmbValuesubtipopersona().toString().compareTo(Constante.SUB_TIPO_PERSONA_MENOR_EDAD)==0){
					if(recordsRelacionado.size()>0){
						for(int i=0;i<recordsRelacionado.size();i++){
							FindMpRelacionado temp = recordsRelacionado.get(i);
		 					 if(temp.getRelacionadoDescripcion().compareTo(Constante.TIPO_RELACIONADO_CONYUGE)==0){
								  addErrorMessage(getMsg("mp.errormenoredadconyuge"));
								  return false;
							}
						}
						
					}
				}
				if(getCmbValuesubtipopersona().toString().compareTo(Constante.SUB_TIPO_PERSONA_SUC_INDIVISA)==0){
					if(recordsRelacionado.size()>0){
						for(int i=0;i<recordsRelacionado.size();i++){
							FindMpRelacionado temp = recordsRelacionado.get(i);
		 					 if(temp.getMpTipoDocIdentidadId() == tipodocumentoIdentidadId && temp.getNroDocuIdentidad3().compareTo(mpPersona.getNroDocuIdentidad())==0){
								  addErrorMessage("Un Relacionado no puede tener el mismo Tipo y Nro de Doc. que el Contribuyente en Suc. Indivisa ");
								  return false;
							}
						}
						
					}
				}
			   }
			else if(getCmbValuetipopersona().toString().compareTo(Constante.TIPO_PERSONA_SOC_CONYUGAL)==0){
				if(mpPersona.getApeMaterno()==null || mpPersona.getApeMaterno().trim().length()==0){
					 addErrorMessage(getMsg("mp.apellidomaternosiningresar"));
					 return false;
					}
					if(mpPersona.getPrimerNombre()==null || mpPersona.getPrimerNombre().trim().length()==0){
						 addErrorMessage(getMsg("mp.primernombresiningresar"));
						 return false;
					}
					Boolean existeConyuge=Boolean.FALSE;
					for(int i=0;i<recordsRelacionado.size();i++){
						FindMpRelacionado temp = recordsRelacionado.get(i);
	 					 if(temp.getRelacionadoDescripcion().compareTo(Constante.TIPO_RELACIONADO_CONYUGE)==0){
							  existeConyuge=Boolean.TRUE;
							  break;
						}
					}
					if(!existeConyuge){
						addErrorMessage(getMsg("mp.conyugeiningresar"));
					    return false;	
					}
			    }
			else if(getCmbValuetipopersona().toString().compareTo(Constante.TIPO_PERSONA_PATRIMONIO)==0){
				if(mpPersona.getRazonSocial()==null || mpPersona.getRazonSocial().trim().length()==0){
					 addErrorMessage(getMsg("mp.razonsocialiningresar"));
					 return false;
				}
				if(recordsRelacionado.size()==0){
					 addErrorMessage(getMsg("mp.relacionado"));
					 return false;
				}
				if(!verificarSituacionEmpresarial(Constante.TIPO_PERSONA_PATRIMONIO,Constante.SITUACION_EMPRESARIAL_ID_DEFECTO_PERJURID_PATRIAUTO))
					return false;
			}

			if(mpPersona.getNotificaEmail()==true && mpPersona.getEmail().trim().length()==0){
				addErrorMessage(getMsg("mp.erroremail"));
				return false;	
			}
						
			if(mpPersona.getEmail().trim().length()>0){
				if(!Util.isEmail(mpPersona.getEmail())){
				 addErrorMessage(getMsg("mp.erroremail"));
				 return false;
				}
			}
			
			if(!isDomiciliosIngresados){
				addErrorMessage(getMsg("mp.domiciliosiningresar"));
				return false ;
				
			}
			if(cmbValuetipocondicionespecial.trim().length()==0){
				addErrorMessage(getMsg("mp.condicionespecial"));
				return false ;
			}
			if(cmbValueMpSituacionEmpresarial.trim().length()==0){
				addErrorMessage(getMsg("mp.situacionempresarial"));
				return false ;
			}
			if(issucesionindivisa){
				if(mpPersona.getNroPartidaDefuncion()==null || mpPersona.getNroPartidaDefuncion().compareTo("")==0){
					addErrorMessage(getMsg("mp.errornropartida"));
					return false ;
				}
				if(fechaDefuncion==null){
					addErrorMessage(getMsg("mp.errorfechaDefuncion"));
					return false ;
				}
				if(fechaDefuncion.compareTo(new Date()) > 0){
					addErrorMessage(getMsg("mp.errorfechaDefuncionmayoractual"));
					return false ;
				}
				
			}
			if(fechaNacoConst!=null)
				if(fechaNacoConst.compareTo(new Date())>0){
				 addErrorMessage(getMsg("mp.errorfechanacconst"));
				 return false ;
			}
			if(fechaDocumento!=null)
				if(fechaDocumento.compareTo(new Date())>0){
				 addErrorMessage(getMsg("mp.errorfechadoc"));
				 return false ;
			}
			if(fechaEmisionDoc!=null)
				if(fechaEmisionDoc.compareTo(new Date())>0){
				 addErrorMessage(getMsg("mp.errorfechaemisiondoc"));
				 return false ;
			}

			if(!verificarCondicionEspecial())
			return false;
			
			if(mpPersona.getTwitter().trim().compareTo("")==0)
			mpPersona.setTwitter(null);
			
			if(mpPersona.getFacebook().trim().compareTo("")==0)
			mpPersona.setFacebook(null);
				
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
		return true;
		
	}
	           
	public Boolean verificarSituacionEmpresarial(String tipoPersona,Integer valorPorDefecto){
		Boolean temp= true;
		if(situacionEmpresarialId!=null)
			if(getCmbValuetipopersona().compareTo(tipoPersona)==0 &&  situacionEmpresarialId!=valorPorDefecto){
				if(fechaIniSituacionE==null){
					addErrorMessage(getMsg("mp.errorfechainiciosituacionempresarial"));
					return false ;
				}
				if(fechaFinSituacionE==null){
					addErrorMessage(getMsg("mp.errorfechafinsituacionempresarial"));
					return false ;
				}
				if(mpPersona.getTipoDocSustSituacionEmpresarial().compareTo("")==0){
					addErrorMessage(getMsg("mp.errortipodocsusituacionempresarial"));
					return false ;
				}
				if(mpPersona.getNroDocSustSituacionEmpresarial().compareTo("")==0){
					addErrorMessage(getMsg("mp.errornrodocsituacionempresarial"));
					return false ;
				}
				if(fechaEmisionDoc==null){
					addErrorMessage(getMsg("mp.errorfechaemisionsituacionempresarial"));
					return false ;
				}
				if(fechaIniSituacionE!=null && fechaFinSituacionE!=null){
					   if (fechaIniSituacionE.compareTo(fechaFinSituacionE) > 0){
						   addErrorMessage(getMsg("mp.errorMayorfechaInicioSituacion"));
							return false ;
						}
				    }
				if(fechaEmisionDoc.compareTo(fechaIniSituacionE) > 0){
					addErrorMessage(getMsg("mp.errorMayorfechaemisionFISituacion"));
					return false ;
				}
				if(fechaEmisionDoc.compareTo(fechaFinSituacionE) > 0){
					addErrorMessage(getMsg("mp.errorMayorfechaemisionFFSituacion"));
					return false ;
				}
			}
		return temp;
	}
	
	public Boolean verificarCondicionEspecial(){
		Boolean temp= true;
		if(tipoCondicionEspecialId!=null)
		if(tipoCondicionEspecialId!=Constante.CONDICION_ESPECIAL_DEFECTO_ID){
			if(tipodocumentoId==null || tipodocumentoId==0){
				addErrorMessage(getMsg("mp.errortipodocumento"));
				return false ;
			}
			if(gnCondicionEspecial.getNroDocumento()==null || gnCondicionEspecial.getNroDocumento().compareTo("")==0){
				addErrorMessage(getMsg("mp.errornrodocumento"));
				return false ;
			}
			if(fechaDocumento==null){
				addErrorMessage(getMsg("mp.errorfechadocumento"));
				return false ;
			}
			if(fechaInicioCond==null){
				addErrorMessage(getMsg("mp.errorfechainicond"));
				return false ;
			}
			if(fechaFinCond==null){
				addErrorMessage(getMsg("mp.errorfechafincond"));
				return false ;
			}
			if(tipoCondicionEspecialId != Constante.TIPO_CONDICION_ESPECIAL_PENSIONISTA_ID){
				if(fechaDocumento.compareTo(fechaInicioCond)>0){
					addErrorMessage(getMsg("mp.errorMayorfechadocumentofechaInicioCondicion"));
					return false ;
				}
			}
			if(tipoCondicionEspecialId != Constante.TIPO_CONDICION_ESPECIAL_PENSIONISTA_ID){
				if(fechaDocumento.compareTo(fechaFinCond)>0){
					addErrorMessage(getMsg("mp.errorMayorfechadocumentofechaFinCondicion"));
					return false ;
				}
			}
			if(fechaInicioCond!=null && fechaFinCond!=null){
				   if (fechaInicioCond.compareTo(fechaFinCond) > 0){
					   addErrorMessage(getMsg("mp.errorMayorfechaInicioCondicion"));
						return false ;
					}
			    }
		}
		return temp;
	}
	
	

	
	public boolean verificarPersona(){
	try{	
		isPersonaexiste=Boolean.FALSE;
		if(tipoPersonaId==null){
			addErrorMessage(getMsg("mp.errortipocontribuyente"));
			return false;
		}
		if(subtipoPersonaId==null){
			addErrorMessage(getMsg("mp.errorsubtipocontribuyente"));
			return false;
		}
		if(tipodocumentoIdentidadId==null){
			addErrorMessage(getMsg("mp.errordocumentoidentidad"));
			return false;
		}
			
		if(getCmbValuetipodocumentoidentidad().compareTo(Constante.TIPO_DOCUMENTO_DNI)==0){
			if(getTxtNumDocumento().getSubmittedValue().toString().length()!=Constante.CANTIDAD_DIGITOS_DNI){
			 addErrorMessage(getMsg("mp.errornrodocumentoidentidaddni"));
			 return false;
			}
		 } 
		if(getCmbValuetipodocumentoidentidad().compareTo(Constante.TIPO_DOCUMENTO_RUC)==0){
			if(getTxtNumDocumento().getSubmittedValue().toString().length()!=Constante.CANTIDAD_DIGITOS_RUC){
			 addErrorMessage(getMsg("mp.errornrodocumentoidentidadruc"));
			 return false;
			}
			if(getTxtNumDocumento().getSubmittedValue().toString().substring(0,2).compareTo("10")!=0 && getTxtNumDocumento().getSubmittedValue().toString().substring(0,2).compareTo("20")!=0){
				 addErrorMessage(getMsg("mp.errorformatonrodocumentoidentidadruc"));
				 return false;
			}
		}
		
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
	    }
	return true;
	}
		
	
	public boolean verificarPersonaPorDNI(String dni){
		try{
			isPersonaexiste=Boolean.FALSE;
			FindMpPersona findMpPersonaTemp=personaBo.findmpPersona(dni);
			if(findMpPersonaTemp !=null){
				Integer a=findMpPersonaTemp.getPersonaId();
				Integer b=mpPersona.getPersonaId();
				if(a.compareTo(b)!=0){
						addErrorMessage(getMsg("Persona Con Dni: "+dni+" Existe"));
						addErrorMessage(getMsg("Código: "+findMpPersonaTemp.getPersonaId()));
						addErrorMessage(getMsg("Nombres: "+findMpPersonaTemp.getApellidosNombres()));
						isPersonaexiste=Boolean.TRUE;
			     }
			}
		   return isPersonaexiste;
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		return isPersonaexiste;
	}
	public boolean verificarPersonaPorDocumento(String nroDoc){
		try{
			isPersonaexiste=Boolean.FALSE;
			FindMpPersona findMpPersonaTemp=personaBo.findmpPersona(tipodocumentoIdentidadId,nroDoc);
			if(findMpPersonaTemp !=null){
				Integer a=findMpPersonaTemp.getPersonaId();
				Integer b=mpPersona.getPersonaId();
				
				if(a.compareTo(b)!=0){
						addErrorMessage(getMsg("Persona Con Tipo Doc: "+findMpPersonaTemp.getTipoDocumentoIdentidad()+" N°: "+nroDoc+"  Existe"));
						addErrorMessage(getMsg("Codigo: "+findMpPersonaTemp.getPersonaId()));
						addErrorMessage(getMsg("Nombres: "+findMpPersonaTemp.getApellidosNombres()));
						isPersonaexiste=Boolean.TRUE;
			     }
			}
		   return isPersonaexiste;
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		return isPersonaexiste;
	}

    

	public MpPersona getMpPersona() {
		return mpPersona;
	}
		
	public void setMpPersona(MpPersona mpPersona) {
		this.mpPersona = mpPersona;
	}	
	
	public HtmlComboBox getCmbtipopersona() {
		return cmbtipopersona;
	}	
	
	public void setCmbtipopersona(HtmlComboBox cmbtipopersona) {
		this.cmbtipopersona = cmbtipopersona;
	}	
	
	public List<SelectItem> getLsttipopersona() {
		return lsttipopersona;
	}	
	
	public void setLsttipopersona(List<SelectItem> lsttipopersona) {
		this.lsttipopersona = lsttipopersona;
	}	
	
	public HashMap<String, Integer> getMapRpTipopersona() {
		return mapRpTipopersona;
	}	
	
	public void setMapRpTipopersona(HashMap<String, Integer> mapRpTipopersona) {
		this.mapRpTipopersona = mapRpTipopersona;
	}
		
	public String getCmbValuetipopersona() {
		return cmbValuetipopersona;
	}
		
	public void setCmbValuetipopersona(String cmbValuetipopersona) {
		this.cmbValuetipopersona = cmbValuetipopersona;
	}
		
	public HtmlComboBox getCmbsubtipopersona() {
		return cmbsubtipopersona;
	}
		
	public void setCmbsubtipopersona(HtmlComboBox cmbsubtipopersona) {
		this.cmbsubtipopersona = cmbsubtipopersona;
	}	
	
	public List<SelectItem> getLstsubtipopersona() {
		return lstsubtipopersona;
	}	
	
	public void setLstsubtipopersona(List<SelectItem> lstsubtipopersona) {
		this.lstsubtipopersona = lstsubtipopersona;
	}	
	
	public HashMap<String, Integer> getMapRpsubTipopersona() {
		return mapRpsubTipopersona;
	}	
	
	public void setMapRpsubTipopersona(HashMap<String, Integer> mapRpsubTipopersona) {
		this.mapRpsubTipopersona = mapRpsubTipopersona;
	}	
	
	public String getCmbValuesubtipopersona() {
		return cmbValuesubtipopersona;
	}	
	
	public void setCmbValuesubtipopersona(String cmbValuesubtipopersona) {
		this.cmbValuesubtipopersona = cmbValuesubtipopersona;
	}
	
	
	public List<MpTipoPersona> getlMpTipoPersona() {
		return lMpTipoPersona;
	}
	
	
	public void setlMpTipoPersona(List<MpTipoPersona> lMpTipoPersona) {
		this.lMpTipoPersona = lMpTipoPersona;
	}
	
	
	public List<MpSubtipoPersona> getlMpsubtipoPersona() {
		return lMpsubtipoPersona;
	}
	
	
	public void setlMpsubtipoPersona(List<MpSubtipoPersona> lMpsubtipoPersona) {
		this.lMpsubtipoPersona = lMpsubtipoPersona;
	}
	
	
	public Integer getSubtipoPersonaId() {
		return subtipoPersonaId;
	}
	
	
	public void setSubtipoPersonaId(Integer subtipoPersonaId) {
		this.subtipoPersonaId = subtipoPersonaId;
	}
	
	
	public Integer getTipoPersonaId() {
		return tipoPersonaId;
	}
	
	
	public void setTipoPersonaId(Integer tipoPersonaId) {
		this.tipoPersonaId = tipoPersonaId;
	}
	
	
	public Integer getTipodocumentoId() {
		return tipodocumentoId;
	}
	
	
	public void setTipodocumentoId(Integer tipodocumentoId) {
		this.tipodocumentoId = tipodocumentoId;
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
	
	
	public void setMapRpTipodocumentoidentidad(HashMap<String, Integer> mapRpTipodocumentoidentidad) {
		this.mapRpTipodocumentoidentidad = mapRpTipodocumentoidentidad;
	}
	
	
	public String getCmbValuetipodocumentoidentidad() {
		return cmbValuetipodocumentoidentidad;
	}
	
	
	public void setCmbValuetipodocumentoidentidad(String cmbValuetipodocumentoidentidad) {
		this.cmbValuetipodocumentoidentidad = cmbValuetipodocumentoidentidad;
	}
	
	
	public List<MpTipoDocuIdentidad> getlMptipodocumentoidentidad() {
		return lMptipodocumentoidentidad;
	}
	
	
	public void setlMptipodocumentoidentidad(List<MpTipoDocuIdentidad> lMptipodocumentoidentidad) {
		this.lMptipodocumentoidentidad = lMptipodocumentoidentidad;
	}
	
	
	public Boolean getIstipodocumentoadicional() {
		return istipodocumentoadicional;
	}
	
	
	public void setIstipodocumentoadicional(Boolean istipodocumentoadicional) {
		this.istipodocumentoadicional = istipodocumentoadicional;
	}
	
	
	public Boolean getIstipopersonaNatural() {
		return istipopersonaNatural;
	}
	
	
	public void setIstipopersonaNatural(Boolean istipopersonaNatural) {
		this.istipopersonaNatural = istipopersonaNatural;
	}
	
	
	public Boolean getIsrazonsocial() {
		return israzonsocial;
	}
	
	
	public void setIsrazonsocial(Boolean israzonsocial) {
		this.israzonsocial = israzonsocial;
	}
	
	
	public HtmlComboBox getCmbtipocondicionespecial() {
		return cmbtipocondicionespecial;
	}
	
	
	public void setCmbtipocondicionespecial(HtmlComboBox cmbtipocondicionespecial) {
		this.cmbtipocondicionespecial = cmbtipocondicionespecial;
	}
	
	
	public List<SelectItem> getLsttipocondicionespecial() {
		return lsttipocondicionespecial;
	}
	
	
	public void setLsttipocondicionespecial(List<SelectItem> lsttipocondicionespecial) {
		this.lsttipocondicionespecial = lsttipocondicionespecial;
	}
	
	
	public HashMap<String, Integer> getMapMpTipocondicionespecial() {
		return mapMpTipocondicionespecial;
	}
	
	
	public void setMapMpTipocondicionespecial(HashMap<String, Integer> mapMpTipocondicionespecial) {
		this.mapMpTipocondicionespecial = mapMpTipocondicionespecial;
	}
	
	
	
	
	public List<MpTipoCondicionEspecial> getlMptipocondicionespecial() {
		return lMptipocondicionespecial;
	}
	
	
	public void setlMptipocondicionespecial(List<MpTipoCondicionEspecial> lMptipocondicionespecial) {
		this.lMptipocondicionespecial = lMptipocondicionespecial;
	}
	

	
	
	public Integer getTipoCondicionEspecialId() {
		return tipoCondicionEspecialId;
	}
	
	
	public void setTipoCondicionEspecialId(Integer tipoCondicionEspecialId) {
		this.tipoCondicionEspecialId = tipoCondicionEspecialId;
	}
	
	
	public String getCmbValuetipocondicionespecial() {
		return cmbValuetipocondicionespecial;
	}
	
	
	public void setCmbValuetipocondicionespecial(
			String cmbValuetipocondicionespecial) {
		this.cmbValuetipocondicionespecial = cmbValuetipocondicionespecial;
	}
	
	
	public Integer getTipodocumentoIdentidadId() {
		return tipodocumentoIdentidadId;
	}
	
	
	public void setTipodocumentoIdentidadId(Integer tipodocumentoIdentidadId) {
		this.tipodocumentoIdentidadId = tipodocumentoIdentidadId;
	}
	
	
	public Integer getSituacionEmpresarialId() {
		return situacionEmpresarialId;
	}
	
	
	public void setSituacionEmpresarialId(Integer situacionEmpresarialId) {
		this.situacionEmpresarialId = situacionEmpresarialId;
	}
	
	
	public HtmlComboBox getCmbMpSituacionEmpresarial() {
		return cmbMpSituacionEmpresarial;
	}
	
	
	public void setCmbMpSituacionEmpresarial(HtmlComboBox cmbMpSituacionEmpresarial) {
		this.cmbMpSituacionEmpresarial = cmbMpSituacionEmpresarial;
	}
	
	
	public List<SelectItem> getLstMpSituacionEmpresarial() {
		return lstMpSituacionEmpresarial;
	}
	
	
	public void setLstMpSituacionEmpresarial(List<SelectItem> lstMpSituacionEmpresarial) {
		this.lstMpSituacionEmpresarial = lstMpSituacionEmpresarial;
	}
	
	
	public HashMap<String, Integer> getMapMpSituacionEmpresarial() {
		return mapMpSituacionEmpresarial;
	}
	
	
	public void setMapMpSituacionEmpresarial(HashMap<String, Integer> mapMpSituacionEmpresarial) {
		this.mapMpSituacionEmpresarial = mapMpSituacionEmpresarial;
	}
	
	
	public String getCmbValueMpSituacionEmpresarial() {
		return cmbValueMpSituacionEmpresarial;
	}
	
	
	public void setCmbValueMpSituacionEmpresarial(
			String cmbValueMpSituacionEmpresarial) {
		this.cmbValueMpSituacionEmpresarial = cmbValueMpSituacionEmpresarial;
	}
	
	
	public List<MpSituacionEmpresarial> getlMpSituacionEmpresarial() {
		return lMpSituacionEmpresarial;
	}
	
	
	public void setlMpSituacionEmpresarial(List<MpSituacionEmpresarial> lMpSituacionEmpresarial) {
		this.lMpSituacionEmpresarial = lMpSituacionEmpresarial;
	}
	
	
	public Boolean getIssucesionindivisa() {
		return issucesionindivisa;
	}
	
	
	public void setIssucesionindivisa(Boolean issucesionindivisa) {
		this.issucesionindivisa = issucesionindivisa;
	}
	
	
	public Boolean getIssituacionempresarial() {
		return issituacionempresarial;
	}
	
	
	public void setIssituacionempresarial(Boolean issituacionempresarial) {
		this.issituacionempresarial = issituacionempresarial;
	}
	
	
	public ArrayList<FindMpRelacionado> getRecordsRelacionado() {
		return recordsRelacionado;
	}
	
	
	public void setRecordsRelacionado(ArrayList<FindMpRelacionado> recordsRelacionado) {
		this.recordsRelacionado = recordsRelacionado;
	}
	
	
	public ArrayList<FindMpDireccion> getRecordsDireccion() {
		return recordsDireccion;
	}
	
	
	public void setRecordsDireccion(ArrayList<FindMpDireccion> recordsDireccion) {
		this.recordsDireccion = recordsDireccion;
	}
	
	
	public Integer getRegistroDomicilio() {
		return registroDomicilio;
	}
	
	
	public void setRegistroDomicilio(Integer registroDomicilio) {
		this.registroDomicilio = registroDomicilio;
	}
	
	
	public String getTipoDomicilio() {
		return tipoDomicilio;
	}
	
	
	public void setTipoDomicilio(String tipoDomicilio) {
		this.tipoDomicilio = tipoDomicilio;
	}
	
	
	public GnCondicionEspecial getGnCondicionEspecial() {
		return gnCondicionEspecial;
	}
	
	
	public void setGnCondicionEspecial(GnCondicionEspecial gnCondicionEspecial) {
		this.gnCondicionEspecial = gnCondicionEspecial;
	}
	
	
	
	
	public java.util.Date getFechaInscripcion() {
		return fechaInscripcion;
	}
	
	
	
	
	public void setFechaInscripcion(java.util.Date fechaInscripcion) {
		this.fechaInscripcion = fechaInscripcion;
	}
	
	
	
	
	public java.util.Date getFechaNacoConst() {
		return fechaNacoConst;
	}
	
	
	
	
	public void setFechaNacoConst(java.util.Date fechaNacoConst) {
		this.fechaNacoConst = fechaNacoConst;
	}
	
	
	
	
	public java.util.Date getFechaInicioCond() {
		return fechaInicioCond;
	}
	
	
	
	
	public void setFechaInicioCond(java.util.Date fechaInicioCond) {
		this.fechaInicioCond = fechaInicioCond;
	}
	
	
	
	
	public java.util.Date getFechaDocumento() {
		return fechaDocumento;
	}
	
	
	
	
	public void setFechaDocumento(java.util.Date fechaDocumento) {
		this.fechaDocumento = fechaDocumento;
	}
	
	
	
	
	public java.util.Date getFechaFinCond() {
		return fechaFinCond;
	}
	
	
	
	
	public void setFechaFinCond(java.util.Date fechaFinCond) {
		this.fechaFinCond = fechaFinCond;
	}
	
	
	
	
	public java.util.Date getFechaIniSituacionE() {
		return fechaIniSituacionE;
	}
	
	
	
	
	public void setFechaIniSituacionE(java.util.Date fechaIniSituacionE) {
		this.fechaIniSituacionE = fechaIniSituacionE;
	}
	
	
	
	
	public java.util.Date getFechaFinSituacionE() {
		return fechaFinSituacionE;
	}
	
	
	
	
	public void setFechaFinSituacionE(java.util.Date fechaFinSituacionE) {
		this.fechaFinSituacionE = fechaFinSituacionE;
	}
	
	
	
	
	public java.util.Date getFechaEmisionDoc() {
		return fechaEmisionDoc;
	}
	
	
	
	
	public void setFechaEmisionDoc(java.util.Date fechaEmisionDoc) {
		this.fechaEmisionDoc = fechaEmisionDoc;
	}
	
	
	
	
	public Boolean getIstipocondicionespecial() {
		return istipocondicionespecial;
	}
	
	
	
	
	public void setIstipocondicionespecial(Boolean istipocondicionespecial) {
		this.istipocondicionespecial = istipocondicionespecial;
	}
	
	
	
	
	public Boolean getIsPersonaexiste() {
		return isPersonaexiste;
	}
	
	
	
	
	public void setIsPersonaexiste(Boolean isPersonaexiste) {
		this.isPersonaexiste = isPersonaexiste;
	}

	public Integer getTipoAccion() {
		return tipoAccion;
	}

	public void setTipoAccion(Integer tipoAccion) {
		this.tipoAccion = tipoAccion;
	}

	public Boolean getIsDomiciliosIngresados() {
		return isDomiciliosIngresados;
	}

	public void setIsDomiciliosIngresados(Boolean isDomiciliosIngresados) {
		this.isDomiciliosIngresados = isDomiciliosIngresados;
	}

	public FindMpRelacionado getFindMpRelacionadoItem() {
		return findMpRelacionadoItem;
	}

	public void setFindMpRelacionadoItem(FindMpRelacionado findMpRelacionadoItem) {
		this.findMpRelacionadoItem = findMpRelacionadoItem;
	}

	public Boolean getIsSubtipoPersona() {
		return isSubtipoPersona;
	}

	public void setIsSubtipoPersona(Boolean isSubtipoPersona) {
		this.isSubtipoPersona = isSubtipoPersona;
	}

	public Boolean getIsTipoDocumentoIdentidad() {
		return isTipoDocumentoIdentidad;
	}

	public void setIsTipoDocumentoIdentidad(Boolean isTipoDocumentoIdentidad) {
		this.isTipoDocumentoIdentidad = isTipoDocumentoIdentidad;
	}

	public Boolean getIsRelacionados() {
		return isRelacionados;
	}

	public void setIsRelacionados(Boolean isRelacionados) {
		this.isRelacionados = isRelacionados;
	}

	public FindMpPersona getFinMpPersonaItem() {
		return finMpPersonaItem;
	}

	public void setFinMpPersonaItem1(FindMpPersona finMpPersonaItem) {
		this.finMpPersonaItem = finMpPersonaItem;
	}

	public Boolean getIsAccionRealizada() {
		return isAccionRealizada;
	}

	public void setIsAccionRealizada(Boolean isAccionRealizada) {
		this.isAccionRealizada = isAccionRealizada;
	}

	public List<MpTipoPersona> getlMpTipoPersonatemp() {
		return lMpTipoPersonatemp;
	}

	public void setlMpTipoPersonatemp(List<MpTipoPersona> lMpTipoPersonatemp) {
		this.lMpTipoPersonatemp = lMpTipoPersonatemp;
	}

	public java.util.Date getFechaDefuncion() {
		return fechaDefuncion;
	}

	public void setFechaDefuncion(java.util.Date fechaDefuncion) {
		this.fechaDefuncion = fechaDefuncion;
	}

	public String getCmbValuetipodocumentoCondicionEspecial() {
		return cmbValuetipodocumentoCondicionEspecial;
	}

	public void setCmbValuetipodocumentoCondicionEspecial(
			String cmbValuetipodocumentoCondicionEspecial) {
		this.cmbValuetipodocumentoCondicionEspecial = cmbValuetipodocumentoCondicionEspecial;
	}

	public HtmlComboBox getCmbtipodocumentoCondicionEspecial() {
		return cmbtipodocumentoCondicionEspecial;
	}

	public void setCmbtipodocumentoCondicionEspecial(
			HtmlComboBox cmbtipodocumentoCondicionEspecial) {
		this.cmbtipodocumentoCondicionEspecial = cmbtipodocumentoCondicionEspecial;
	}

	public List<SelectItem> getLsttipodocumentoCondicionEspecial() {
		return lsttipodocumentoCondicionEspecial;
	}

	public void setLsttipodocumentoCondicionEspecial(
			List<SelectItem> lsttipodocumentoCondicionEspecial) {
		this.lsttipodocumentoCondicionEspecial = lsttipodocumentoCondicionEspecial;
	}

	public Boolean getGeneradoDJ() {
		return generadoDJ;
	}

	public void setGeneradoDJ(Boolean generadoDJ) {
		this.generadoDJ = generadoDJ;
	}

	public MpDireccion getMpDireccion() {
		return mpDireccion;
	}

	public void setMpDireccion(MpDireccion mpDireccion) {
		this.mpDireccion = mpDireccion;
	}

	public MpPersonaDomicilio getMpPersonaDomicilio() {
		return mpPersonaDomicilio;
	}

	public void setMpPersonaDomicilio(MpPersonaDomicilio mpPersonaDomicilio) {
		this.mpPersonaDomicilio = mpPersonaDomicilio;
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

	public Boolean getTipoAccionVistaGuardarActualizar() {
		return tipoAccionVistaGuardarActualizar;
	}

	public void setTipoAccionVistaGuardarActualizar(
			Boolean tipoAccionVistaGuardarActualizar) {
		this.tipoAccionVistaGuardarActualizar = tipoAccionVistaGuardarActualizar;
	}

	public FindMpPersona getFindMpPersona() {
		return findMpPersona;
	}

	public void setFindMpPersona(FindMpPersona findMpPersona) {
		this.findMpPersona = findMpPersona;
	}
	
	
	
	public void agregarTransferente() {

		
		String primerNombre = mpPersona.getPrimerNombre();
		String segundoNombre = mpPersona.getSegundoNombre();
		String apePaterno = mpPersona.getApePaterno();
		String apeMaterno = mpPersona.getApeMaterno();
		String nroDocIdentidad = mpPersona.getNroDocuIdentidad();
		String telefono = mpPersona.getTelefono();
		
		String razonSocial = mpPersona.getRazonSocial();
		String nombresCompletos=null;
		
		if(primerNombre!=null && apePaterno !=null && apeMaterno!=null){
			nombresCompletos=primerNombre + " " + apePaterno + " "+ apeMaterno;
				
		}
		
				
		BuscarPersonaDTO buscarPersonaDTO= new BuscarPersonaDTO();
		buscarPersonaDTO.setApellidosNombres(nombresCompletos);
		buscarPersonaDTO.setTipoDocIdentidad(cmbValuetipodocumentoidentidad);
		buscarPersonaDTO.setNroDocuIdentidad(nroDocIdentidad);
		buscarPersonaDTO.setDireccionCompleta(direccionTransferente);
		buscarPersonaDTO.setRazonSocial(razonSocial);
		buscarPersonaDTO.setTelefono(telefono);
		buscarPersonaDTO.setTipoPersonaId(tipoPersonaId);
		buscarPersonaDTO.setSubtipoPersonaId(subtipoPersonaId);
		
		buscarPersonaDTO.setPersonaId(null);
		
		buscarPersonaDTO.setTipodocumentoIdentidadId(tipodocumentoIdentidadId);
		buscarPersonaDTO.setApellidoPaterno(apePaterno);
		buscarPersonaDTO.setApellidoMaterno(apeMaterno);
		buscarPersonaDTO.setPrimerNombre(primerNombre);
		buscarPersonaDTO.setSegundoNombre(segundoNombre);
		buscarPersonaDTO.setNombresCompletos(nombresCompletos);
		buscarPersonaDTO.setReferencia(referenciaTransferente);
		
		buscarPersonaDTO.setPartidaDefuncion(mpPersona.getNroPartidaDefuncion());
		buscarPersonaDTO.setFechaDefuncion(fechaDefuncion);
		
		buscarPersonaDTO.setFechaConsNac(fechaNacoConst);
		
		
		lstTransferentes.add(buscarPersonaDTO);
		
		setCmbtipodocumentoidentidad(new HtmlComboBox());

		setMpPersona(new MpPersona());
		setCmbValuetipodocumentoidentidad(null);
		setCmbValuetipopersona(null);
		setCmbValuesubtipopersona(null);
		
		
		setDireccionTransferente("");
		setFechaDefuncion(null);
		setFechaNacoConst(null);
		getMpPersona().setRazonSocial("");
		
		
	}
	
	public void setProperty(List<BuscarPersonaDTO> lstTransferentes){
		setLstTransferentes(lstTransferentes);
	}
	
	
	public void eliminarTransferenteDeLista(ActionEvent ev) {
		try {
			UIComponent comp = ev.getComponent().getParent().getParent();
			if (comp != null) {
				UIData uiData = (UIData) comp;
				BuscarPersonaDTO bpd=(BuscarPersonaDTO)uiData.getRowData();
				lstTransferentes.remove(bpd);

			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	private List<BuscarPersonaDTO> lstTransferentes = new ArrayList<BuscarPersonaDTO>();

	public List<BuscarPersonaDTO> getLstTransferentes() {
		return lstTransferentes;
	}

	public void setLstTransferentes(List<BuscarPersonaDTO> lstTransferentes) {
		this.lstTransferentes = lstTransferentes;
	}
	private String direccionTransferente;

	public String getDireccionTransferente() {
		return direccionTransferente;
	}

	public void setDireccionTransferente(String direccionTransferente) {
		this.direccionTransferente = direccionTransferente;
	}
	private String referenciaTransferente;

	public String getReferenciaTransferente() {
		return referenciaTransferente;
	}

	public void setReferenciaTransferente(String referenciaTransferente) {
		this.referenciaTransferente = referenciaTransferente;
	}
}
