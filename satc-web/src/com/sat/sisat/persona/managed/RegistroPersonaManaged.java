package com.sat.sisat.persona.managed;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
import org.richfaces.component.html.HtmlInputText;

import com.sat.sisat.administracion.business.AdministracionBoRemote;
import com.sat.sisat.common.business.GeneralBoRemote;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.Util;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.consumoWS.ConsultaReniecManaged;
import com.sat.sisat.persistence.entity.GnCondicionEspecial;
import com.sat.sisat.persistence.entity.GnCondicionEspecialPK;
import com.sat.sisat.persistence.entity.GnPersona;
import com.sat.sisat.persistence.entity.MpDireccion;
import com.sat.sisat.persistence.entity.MpPersona;
import com.sat.sisat.persistence.entity.MpPersonaDomicilio;
import com.sat.sisat.persistence.entity.MpRelacionado;
import com.sat.sisat.persistence.entity.MpSituacionEmpresarial;
import com.sat.sisat.persistence.entity.MpSubtipoPersona;
import com.sat.sisat.persistence.entity.MpTipoCondicionEspecial;
import com.sat.sisat.persistence.entity.MpTipoDocuIdentidad;
import com.sat.sisat.persistence.entity.MpTipoDocumentoCondicionEspecial;
import com.sat.sisat.persistence.entity.MpTipoPersona;
import com.sat.sisat.persistence.entity.MpTipoRelacion;

import com.sat.sisat.persistence.entity.TgPersona;
import com.sat.sisat.persona.business.PersonaBoRemote;
import com.sat.sisat.predial.dto.FindMpDireccion;
import com.sat.sisat.predial.dto.FindMpPersona;
import com.sat.sisat.predial.dto.FindMpRelacionado;
import com.sat.sisat.predial.dto.UbicacionDTO;
import com.sat.sisat.predial.managed.DescargoPredialManaged;
import com.sat.sisat.predial.managed.RegistroPredioManaged;

import com.sat.sisat.vehicular.cns.ReusoFormCns;
import com.sat.sisat.vehicular.dto.BuscarPersonaDTO;
import com.sat.sisat.vehicular.managed.DescargoVehicularManaged;
import com.sat.sisat.vehicular.managed.NuevadjRegistroManaged;
import com.sat.sisatc.seguridad.dto.ConsultaReniecDTO;

import pe.gob.reniec.ws.ResultadoConsulta;

@ManagedBean
@ViewScoped
public class RegistroPersonaManaged extends BaseManaged {
	
	private static final long serialVersionUID = 1089541023986679063L;
	@EJB
	PersonaBoRemote personaBo;
	@EJB
	GeneralBoRemote generalBo;
	
	@EJB	
	AdministracionBoRemote administracionBo;

	
	private MpPersona mpPersona;
	//= new MpPersona();
	private GnCondicionEspecial gnCondicionEspecial=null;
	
	private String rutaFotoReniec;
	private ResultadoConsulta resultadoConsultaReniec = new ResultadoConsulta();
	
	

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
	private HashMap<Integer,String> mapIRpTipodocumentoidentidad = new HashMap<Integer,String>();
	private String cmbValuetipodocumentoidentidad;
	private List<MpTipoDocuIdentidad> lMptipodocumentoidentidad = null;
	
	private String seleccionTipoEmpresa = null;
	
	

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

	private HashMap<Integer,String> mapMpTipoRelacion=new HashMap<Integer,String>();
	//--
	
	//MpTipoRelacion
	private HtmlComboBox cmbHtmlTipoEmpresa;
	
	private Integer tipoPersonaId;
	private Integer subtipoPersonaId;
	private Integer tipodocumentoId;
	private Integer tipoCondicionEspecialId;
	private Integer tipodocumentoIdentidadId;
	private Integer situacionEmpresarialId;
	private Integer registroDomicilio=0;//0= fiscal,1=Procesal, 2=Real  
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
	
	private Integer personaId;

	private java.util.Date fechaInscripcion;
	private java.util.Date fechaNacoConst;
	private java.util.Date fechaInicioCond;
	private java.util.Date fechaDocumento;
	private java.util.Date fechaFinCond;
	private java.util.Date fechaIniSituacionE;
	private java.util.Date fechaFinSituacionE;
	private java.util.Date fechaEmisionDoc;
	private java.util.Date fechaDefuncion;
	//private java.util.Date fechaActualizacion;
	
	private ArrayList<MpRelacionado> recordsRelacionado;
	//relacionados adulto
	private ArrayList<MpRelacionado> recordsRelacionadoAdult;
	
	private ArrayList<FindMpDireccion> recordsDireccion;
	private FindMpPersona finMpPersonaItem;
	
	private FindMpRelacionado findMpRelacionadoItem;
	
	private MpRelacionado relacionado;
	
	private MpDireccion mpDireccion;
	private MpPersonaDomicilio mpPersonaDomicilio;
	private UbicacionDTO ubicacionDTO; 
	
	//Buscar persona desde RENIEC
	private HtmlInputText txtNumDocumento = new HtmlInputText();
	private HtmlInputText txtApePaterno = new HtmlInputText();
	private HtmlInputText txtApeMaterno = new HtmlInputText();
	private HtmlInputText txtPrimerNombre = new HtmlInputText();
	private HtmlInputText txtSegundoNombre = new HtmlInputText();
	
	// Variables reusabilidad
	private String pantallaUso;
	private String destinoRefresh;
	
	private boolean flagEstatal;
	
	private List<TgPersona> listaPersonas = new ArrayList<TgPersona>();
	
	@EJB
	private PersonaBoRemote personaBoFinal;
	
	public RegistroPersonaManaged() {
		getSessionManaged().setLinkRegresar("/sisat/persona/buscarpersona.xhtml");
	}
	
	@PostConstruct
	public void init(){
		
		
		try{ 
			mpPersona= new MpPersona(personaId);
			mpPersona.setEstado(Constante.ESTADO_ACTIVO);
			mpPersona.setPersonaId(Constante.RESULT_PENDING);
			
			mpDireccion=new MpDireccion();
			mpDireccion.setEstado(Constante.ESTADO_ACTIVO);
			
			mpPersonaDomicilio=new MpPersonaDomicilio();
			mpPersonaDomicilio.setEstado(Constante.ESTADO_ACTIVO);
			
			ubicacionDTO=new UbicacionDTO();
			
			gnCondicionEspecial=new GnCondicionEspecial();
			GnCondicionEspecialPK id=new GnCondicionEspecialPK();
			id.setCondicionEspecialId(Constante.RESULT_PENDING);
			id.setPersonaId(Constante.RESULT_PENDING);
			gnCondicionEspecial.setId(id);
			
			recordsDireccion = new ArrayList<FindMpDireccion>();
			recordsRelacionado = new ArrayList<MpRelacionado>();
			//relacionados adulto
			recordsRelacionadoAdult = new ArrayList<MpRelacionado>();
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
			tipoAccion=Constante.TIPO_ACCION_NUEVO;
			
						
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
			
			List<MpTipoRelacion> lstMpTipoRelacion=personaBo.getAllMpTipoRelacion();
			Iterator<MpTipoRelacion> it3 = lstMpTipoRelacion.iterator();  
		    while (it3.hasNext()){
		    	MpTipoRelacion obj = it3.next();  
		    	mapMpTipoRelacion.put(obj.getTipoRelacionId(),obj.getDescripcion().trim());
		    }	    
			/**Ingreso Para editar*/

			finMpPersonaItem= new FindMpPersona();
			Object objtemp = getSessionMap().get("finMpPersonaItem");
			if (objtemp != null) {
				finMpPersonaItem = (FindMpPersona)objtemp;
				getSessionMap().remove("finMpPersonaItem");
				mpPersona = new MpPersona();
				//mpPersona = finMpPersonaItem.getMpPersona();//cc:ini 25/01/2013
				mpPersona = personaBo.getMpPersona(finMpPersonaItem.getPersonaId());
				
				System.out.println("***********************************");
				System.out.println(mpPersona.isUbicableControl());
				
				fechaNacoConst=mpPersona.getFechaDeclaracion();
				tipoAccion=Constante.TIPO_ACCION_EDITAR;
				if(mpPersona.getFlagEstatal() != null){
					flagEstatal = mpPersona.getFlagEstatal().equals(Constante.ESTADO_ACTIVO)? Boolean.TRUE : Boolean.FALSE;
					int flagEst = flagEstatal? 1:0;
					
					if(flagEst == 0)
					{
						this.setSeleccionTipoEmpresa("No Estatal");
						flagEstatal = Boolean.FALSE;
					}
					else
					{
						this.setSeleccionTipoEmpresa("Estatal");
						flagEstatal = Boolean.TRUE;
					}					 
					
				}else{					
					flagEstatal = Boolean.FALSE;
				}
				obtenerDatos();
			}
			
			/**Ingreso Para ver*/
			Object objtemp1 = getSessionMap().get("finMpPersonaItemVerDatos");
			if (objtemp1 != null) {
				finMpPersonaItem= new FindMpPersona();
				finMpPersonaItem = (FindMpPersona)objtemp1;
				getSessionMap().remove("finMpPersonaItemVerDatos");
				mpPersona = new MpPersona();
				//mpPersona = finMpPersonaItem.getMpPersona();//cc:ini 25/01/2013
				mpPersona = personaBo.getMpPersona(finMpPersonaItem.getPersonaId());
				fechaNacoConst=mpPersona.getFechaDeclaracion();
				tipoAccion=Constante.TIPO_ACCION_VER;
				obtenerDatos();
				generadoDJ=Constante.GENERADO_DJ;
			}
			
			//Seteando la fecha actual para la fecha de inscripcion
			this.setFechaInscripcion(DateUtil.getCurrentDate());
			
			findMpRelacionadoItem= new FindMpRelacionado();
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
		
	}
	
	
	
	public void consultaReniec() 
	{		
		
		if (mpPersona.getTipoDocIdentidadId() !=1  && mpPersona.getTipoDocIdentidadId() !=12) //DNi y LE			
			{
			WebMessages.messageError("Válido solo para búsuqedas por documento de tipo DNI.");
			return;
			}
				
		ConsultaReniecManaged consultaReniec=new ConsultaReniecManaged();
		resultadoConsultaReniec=new ResultadoConsulta();		
		
		consultaReniec.setDniConsulta(mpPersona.getNroDocuIdentidad());
		
		
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
			
			
			rutaFotoReniec=consultaReniec.getRutaImagen();
			
			String primerNombre,segundoNombre="";
			Integer indice;
			
			mpPersona.setApePaterno(resultadoConsultaReniec.getDatosPersona().getApPrimer());
			mpPersona.setApeMaterno(resultadoConsultaReniec.getDatosPersona().getApSegundo());			
			mpPersona.setPrimerNombre(resultadoConsultaReniec.getDatosPersona().getPrenombres());
			
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
			
			
					
			mpPersona.setPrimerNombre(primerNombre);
			mpPersona.setSegundoNombre(segundoNombre);		
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// TODO: handle finally clause
		}
		
	}
	
	public void recuperarDatos()
	{
		WebMessages.messageError("Código: "+mpPersona.getPersonaId().toString() );
		
	}

	
	//cchaucca 02/07/2012 obtiene datos de la base de reniec
	public void buscarPersonaDNI(){
		try{
			if(verificarPersona()){
					if(validaNroDocumento()){
							if(getCmbtipodocumentoidentidad().getValue()!=null&&getCmbtipodocumentoidentidad().getValue().equals(Constante.TIPO_DOCUMENTO_DNI)){
								if(getTxtNumDocumento().getSubmittedValue()!=null&&getTxtNumDocumento().getSubmittedValue().toString().length()==8){
									TgPersona persona=personaBo.buscarPersonaReniec(getTxtNumDocumento().getSubmittedValue().toString());	
									if(persona!=null){
										
										//INFORMACION ANTERIOR AL POSIBLE CAMBIO
										String nombre = "";
										
										if( getTxtNumDocumento().getValue() != null )
											nombre = nombre.concat(" DNI: " + getTxtNumDocumento().getValue().toString());
										if( getTxtPrimerNombre().getValue() != null )
											nombre = nombre.concat(" PERSONA: " + getTxtPrimerNombre().getValue().toString());
										if( getTxtSegundoNombre().getValue() != null )
											nombre = nombre.concat(" "+getTxtSegundoNombre().getValue().toString());
										if( getTxtApePaterno().getValue() != null )
											nombre = nombre.concat(", "+getTxtApePaterno().getValue().toString());
										if( getTxtApeMaterno().getValue() != null )
											nombre =nombre.concat(" "+getTxtApeMaterno().getValue().toString());
										//--									
										
										String[] temp=persona.getNombre().split("\\s+");
										getTxtPrimerNombre().setValue(temp.length>0?temp[0]:"");
										getTxtSegundoNombre().setValue(temp.length>1?temp[1]:"");
										
										getTxtApePaterno().setValue(persona.getApe_pat());
										getTxtApeMaterno().setValue(persona.getApe_mat());
										
										getMpPersona().setPrimerNombre(temp.length>0?temp[0]:"");
										getMpPersona().setSegundoNombre(temp.length>1?temp[1]:"");
										
										getMpPersona().setApePaterno(persona.getApe_pat());
										getMpPersona().setApeMaterno(persona.getApe_mat());
										fechaNacoConst=persona.getFechaNacimiento();
										
										addErrorMessage("RENIEC DNI: "+persona.getLe() + " PERSONA: "+persona.getNombre()+", "+persona.getApe_pat()+" "+persona.getApe_mat());
										addErrorMessage("ANTES " +nombre);

									}else{
										//INFORMACION del contribuyente que no tiene registrado el DNI en la RENIEC
										String nombreFinal = "";
										
										if( getTxtNumDocumento().getSubmittedValue() != null )
											nombreFinal = nombreFinal.concat(" DNI: " + getTxtNumDocumento().getSubmittedValue().toString());
										if( getTxtPrimerNombre().getValue() != null )
											nombreFinal = nombreFinal.concat(" PERSONA: " + getTxtPrimerNombre().getValue().toString());
										if( getTxtSegundoNombre().getValue() != null )
											nombreFinal = nombreFinal.concat(" "+getTxtSegundoNombre().getValue().toString());
										if( getTxtApePaterno().getValue() != null )
											nombreFinal = nombreFinal.concat(", "+getTxtApePaterno().getValue().toString());
										if( getTxtApeMaterno().getValue() != null )
											nombreFinal = nombreFinal.concat(" "+getTxtApeMaterno().getValue().toString());
										//--
										addInfoMessage(nombreFinal);
									}
								}
							}
					}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void actualizarDatosPersonaBasico()throws Exception{
		try{
			String ApellidosNombresORazonSocial="";
			   
			ApellidosNombresORazonSocial="";
			mpPersona.setApellidosNombres("");
			if(tipoPersonaId==Constante.TIPO_PERSONA_NATURAL_ID || tipoPersonaId==Constante.TIPO_PERSONA_SOCIEDADCONYUGAL_ID ||tipoPersonaId==Constante.SUB_TIPO_PERSONA_COPROPIETARIO_ID)
			{
			   mpPersona.setRazonSocial(null);
			   ApellidosNombresORazonSocial = mpPersona.getApePaterno()+' '+mpPersona.getApeMaterno()+' '+mpPersona.getPrimerNombre();
			   if(mpPersona.getSegundoNombre()!=null)
				   ApellidosNombresORazonSocial = ApellidosNombresORazonSocial + ' ' + mpPersona.getSegundoNombre();
			   mpPersona.setSituacionEmpresarialId(Constante.SITUACION_EMPRESARIAL_ID_DEFECTO);
			}
			if(tipoPersonaId==Constante.TIPO_PERSONA_JURIDICA_ID || tipoPersonaId==Constante.TIPO_PERSONA_PATRIMONIAUTONOMO_ID)
			{
			   mpPersona.setApeMaterno(null);
			   mpPersona.setApePaterno(null);
			   mpPersona.setPrimerNombre(null);
			   mpPersona.setSegundoNombre(null);
			   ApellidosNombresORazonSocial= mpPersona.getRazonSocial();
			   mpPersona.setSituacionEmpresarialId(Constante.SITUACION_EMPRESARIAL_ID_DEFECTO_PERJURID_PATRIAUTO);
			}
		    mpPersona.setApellidosNombres(ApellidosNombresORazonSocial);
			//mpPersona.setFechaInscripcion(DateUtil.getCurrentDate());
		    mpPersona.setFechaActualizacion(DateUtil.getCurrentDate());
			//mpPersona.setFechaRegistro(DateUtil.getCurrentDate());
			mpPersona.setTipoPersonaId(tipoPersonaId);
		    mpPersona.setSubtipoPersonaId(subtipoPersonaId);
		    mpPersona.setTipoDocIdentidadId(tipodocumentoIdentidadId);
		    mpPersona.setFlagNotificaEmail(Constante.FLAG_INACTIVO);
		    
		   	
		    if(fechaInscripcion != null)
		    	mpPersona.setFechaInscripcion(DateUtil.dateToSqlTimestamp(fechaInscripcion));
		    if(fechaNacoConst != null)
		    	mpPersona.setFechaDeclaracion(DateUtil.dateToSqlTimestamp(fechaNacoConst));
			if(fechaEmisionDoc!=null)
				mpPersona.setFechaEmisionSituacionEmpresarial(DateUtil.dateToSqlTimestamp(fechaEmisionDoc));
			if(fechaDefuncion!=null)
				mpPersona.setFechaDefuncion(DateUtil.dateToSqlTimestamp(fechaDefuncion));
		
			guardarPersona(mpPersona,Constante.ESTADO_PENDIENTE);
			
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	/**
	 * cchaucca 02/07/2012 Se agrega el registro de Persona en la tabla generica GN_PERSONA
	 */
	public MpPersona guardarPersona(MpPersona mpPersona,String estado){
		try{
			//GnPersona
			GnPersona persona=new GnPersona();
			persona.setPersonaId(Constante.RESULT_PENDING);
			persona.setTipoDocumentoId(mpPersona.getTipoDocIdentidadId());
			persona.setNroDocIdentidad(mpPersona.getNroDocuIdentidad());
			persona.setPrimerNombre(mpPersona.getPrimerNombre());
			persona.setSegundoNombre(mpPersona.getSegundoNombre());
			persona.setApePaterno(mpPersona.getApePaterno());
			persona.setApeMaterno(mpPersona.getApeMaterno());
			persona.setDomicilioCompleto(getMpDireccion().getDireccionCompleta());
			if(mpPersona.getTipoPersonaId().compareTo(Constante.TIPO_PERSONA_NATURAL_ID)==0 || mpPersona.getTipoPersonaId().compareTo(Constante.TIPO_PERSONA_SOCIEDADCONYUGAL_ID)==0)
				persona.setApellidosNombres(persona.getApePaterno()+" "+persona.getApeMaterno()+" "+persona.getPrimerNombre()+" "+persona.getSegundoNombre());
			if(mpPersona.getTipoPersonaId().compareTo(Constante.TIPO_PERSONA_JURIDICA_ID)==0 || mpPersona.getTipoPersonaId().compareTo(Constante.TIPO_PERSONA_PATRIMONIAUTONOMO_ID)==0){   
				persona.setRazonSocial(mpPersona.getRazonSocial());
			}
			persona.setEstado(estado);
			persona.setApellidosNombres(mpPersona.getApellidosNombres());
			persona.setPersonaId(mpPersona.getPersonaId());
			persona.setTerminalRegistro(mpPersona.getTerminal());
			/*
			String seleccionado =  this.getSeleccionTipoEmpresa().toString();
			
			if (seleccionado.equals("No Estatal"))
				mpPersona.setFlagEstatal("0");
			else
				mpPersona.setFlagEstatal("1");
			*/
			Integer personaId=personaBo.registraPersona(persona);
			persona.setPersonaId(personaId);
			
			//MpPersona
			if(mpPersona.getNroDj()==null||mpPersona.getNroDj()==0){
				String nro_dj=generalBo.ObtenerCorrelativoDocumento("MP_PERSONA", "NRO_DJ");
				mpPersona.setNroDjFormato(nro_dj);
				mpPersona.setNroDj(Integer.parseInt(nro_dj));
			}
			mpPersona.setEstado(estado);
			if(mpPersona.getPersonaId()>Constante.RESULT_PENDING ){
				personaBo.actualizarPersona(mpPersona);
			}else{
				mpPersona.setPersonaId(personaId);
				mpPersona=personaBo.guardarPersona(mpPersona);	
			}
			
			getMpDireccion().setPersonaId(personaId);
			getMpDireccion().setEstado(estado);
			Integer direccionId=personaBo.guardarMpdireccion(getMpDireccion());
			getMpDireccion().setDireccionId(direccionId);
			
			getMpPersonaDomicilio().setDireccionId(direccionId);
			getMpPersonaDomicilio().setPersonaId(personaId);
			getMpPersonaDomicilio().setEstado(estado);
			Integer personaDomicilioId=personaBo.guardarMpPersonadireccion(getMpPersonaDomicilio());
			getMpPersonaDomicilio().setDomicilio_persona_id(personaDomicilioId);
			personaBo.grabarTablaMpPersonaHistorico(mpPersona);

			return mpPersona;
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		return mpPersona;
	}
	
	/**
	 * Registro de Nuevo MpDireccion : fin
	 * @throws Exception
	 */
	public void obtenerDatos(){
		try{
			if(mpPersona.getFlagNotificaEmail()!= null && mpPersona.getFlagNotificaEmail().compareTo(Constante.FLAG_ACTIVO)==0)
				 mpPersona.setNotificaEmail(true);
			else if(mpPersona.getFlagNotificaEmail()!= null && mpPersona.getFlagNotificaEmail().compareTo(Constante.FLAG_INACTIVO)==0)
			     mpPersona.setNotificaEmail(false);
		    tipoPersonaId =mpPersona.getTipoPersonaId();
			setCmbValuetipopersona(finMpPersonaItem.getTipoPersona());
			subtipoPersonaId = finMpPersonaItem.getSubtipoPersonaId();
			setCmbValuesubtipopersona(finMpPersonaItem.getSubtipopersona());
			
			/** Seteando la fecha de inscripcion del contribuyente*/
			setFechaInscripcion(mpPersona.getFechaInscripcion());
			
			if(getCmbValuetipopersona().compareTo(Constante.TIPO_PERSONA_JURIDICA)==0 || getCmbValuetipopersona().toString().compareTo(Constante.TIPO_PERSONA_PATRIMONIO)==0){
				istipodocumentoadicional=Boolean.TRUE;
				israzonsocial=Boolean.TRUE;
			    istipopersonaNatural=Boolean.FALSE;
			}
			else if(getCmbValuetipopersona().toString().compareTo(Constante.TIPO_PERSONA_NATURAL)==0 || getCmbValuetipopersona().toString().compareTo(Constante.TIPO_PERSONA_SOC_CONYUGAL)==0 || getCmbValuetipopersona().toString().compareTo(Constante.TIPO_PERSONA_COPROPIEDAD)==0){
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
				else if(getCmbValuetipopersona().toString().compareTo(Constante.TIPO_PERSONA_NATURAL)==0 || getCmbValuetipopersona().toString().compareTo(Constante.TIPO_PERSONA_SOC_CONYUGAL)==0||getCmbValuetipopersona().toString().compareTo(Constante.TIPO_PERSONA_COPROPIEDAD)==0){
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
				situacionEmpresarialId=Constante.SITUACION_EMPRESARIAL_ID_DEFECTO;
			}
			gnCondicionEspecial=personaBo.findCondicionEspecial(mpPersona);
			if(gnCondicionEspecial==null){
				setCmbValuetipocondicionespecial(Constante.CONDICION_ESPECIAL_DEFECTO);
				istipocondicionespecial=Boolean.FALSE;
				gnCondicionEspecial=new GnCondicionEspecial();
				GnCondicionEspecialPK id=new GnCondicionEspecialPK();
				id.setCondicionEspecialId(Constante.RESULT_PENDING);
				id.setPersonaId(Constante.RESULT_PENDING);
				gnCondicionEspecial.setId(id);
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
			
			recordsRelacionado=personaBo.getAllMpRelacionadoPersona(finMpPersonaItem.getPersonaId());
			if(mpPersona.getFlagEstatal()!=null){
				flagEstatal = mpPersona.getFlagEstatal().compareTo(Constante.ESTADO_ACTIVO) == 0? Boolean.TRUE:Boolean.FALSE;
				int flagEst = flagEstatal? 1:0;
				
				if(flagEst == 0)
				{
					this.setSeleccionTipoEmpresa("No Estatal");
					flagEstatal = Boolean.FALSE;
				}
				else
				{
					this.setSeleccionTipoEmpresa("Estatal");
					flagEstatal = Boolean.TRUE;
				}	
			}else {
				flagEstatal = Boolean.FALSE;
			}
			
			//relacionados adulto
			recordsRelacionadoAdult=personaBo.getAllMpRelacionadoPersona(finMpPersonaItem.getPersonaId());
			if(mpPersona.getFlagEstatal()!=null){
				flagEstatal = mpPersona.getFlagEstatal().compareTo(Constante.ESTADO_ACTIVO) == 0? Boolean.TRUE:Boolean.FALSE;
				int flagEst = flagEstatal? 1:0;
				
				if(flagEst == 0)
				{
					this.setSeleccionTipoEmpresa("No Estatal");
					flagEstatal = Boolean.FALSE;
				}
				else
				{
					this.setSeleccionTipoEmpresa("Estatal");
					flagEstatal = Boolean.TRUE;
				}	
			}else {
				flagEstatal = Boolean.FALSE;
			}
			
		    recordsDireccion=personaBo.finMpDireccionPersona(finMpPersonaItem.getPersonaId());
		    if(mpPersona.getNroDj()!=null&&mpPersona.getNroDj()>0&&recordsDireccion.size()==0)
		    {
	    	    if(recordsDireccion.size()>0){
	    	    	personaBo.actualizarEstadoMpDireccion(recordsDireccion.get(0).getDireccionId(), Constante.ESTADO_ACTIVO);
					personaBo.actualizarEstadoMpPersonaDomicilio(recordsDireccion.get(0).getPersonaDomicilio(), Constante.ESTADO_ACTIVO);
					recordsDireccion = new ArrayList<FindMpDireccion>();
					recordsDireccion=personaBo.finMpDireccionPersona(finMpPersonaItem.getPersonaId());
				}
		    }
		    
		    //Obtiene los objetos necesarios para el mantenimiento de la direccion de la persona.
	        if(recordsDireccion.size()>0){
	        	FindMpDireccion direccion=recordsDireccion.get(0);
	        	
	        	isDomiciliosIngresados=Boolean.TRUE;
	        	
	        	mpDireccion=personaBo.finMpDireccion(recordsDireccion.get(0).getDireccionId());
	        	mpPersonaDomicilio=personaBo.finMpPersonaDomicilio(recordsDireccion.get(0).getPersonaDomicilio());
	        	
	        	ubicacionDTO.setNumeroCuadra(direccion.getNroCuadra());
	        	ubicacionDTO.setLado(direccion.getLadoCuadra());
	        	
	        	ubicacionDTO.setTipoVia(direccion.getTipoVia());
				ubicacionDTO.setViaid(direccion.getViaId());
				ubicacionDTO.setVia(direccion.getVia());
				ubicacionDTO.setSector(direccion.getSector());
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
	
	public void obtenerDatosRelacionado(){
		try {
			recordsRelacionado=personaBo.getAllMpRelacionadoPersona(finMpPersonaItem.getPersonaId());
		} catch (Exception e) {
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
		mapRpTipodocumentoidentidad=new HashMap<String, Integer>();
    	mapIRpTipodocumentoidentidad=new HashMap<Integer, String>();
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

	/*load de listas/*/
	public void loadRelacionados(MpRelacionado mpRelacionado){
		try{
			int index=recordsRelacionado.indexOf(mpRelacionado);
			if(index>=0){
				recordsRelacionado.remove(mpRelacionado);
				recordsRelacionado.add(index,mpRelacionado);
			}else{
				recordsRelacionado.add(mpRelacionado);
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public void loadRelacionadosAdulto(MpRelacionado mpRelacionado){
		try{
			int index=recordsRelacionadoAdult.indexOf(mpRelacionado);
			if(index>=0){
				recordsRelacionadoAdult.remove(mpRelacionado);
				recordsRelacionadoAdult.add(index,mpRelacionado);
			}else{
				recordsRelacionadoAdult.add(mpRelacionado);
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void loadDirecciones(){
		try{
			recordsDireccion=new ArrayList<FindMpDireccion>();
			//recordsDireccion=personaBo.finMpDireccionPersona(personaId);
			FindMpDireccion direccion=new FindMpDireccion();
			direccion.setTipoDomicilio(getMpPersonaDomicilio().getTipoDomicilio());
			direccion.setDomicilioCompleto(getMpDireccion().getDireccionCompleta());
			//direccion.setDireccionId(getMpDireccion().getDireccionId());
			//direccion.setPersonaDomicilio(getMpPersonaDomicilio().getDomicilio_persona_id());
			
			direccion.setDepartamento(getMpDireccion().getUbigeo().getDepartamento());
			direccion.setProvincia(getMpDireccion().getUbigeo().getProvincia());
			direccion.setDistrito(getMpDireccion().getUbigeo().getDistrito());
			
			direccion.setDepartamentoId(getMpDireccion().getDptoId());
			direccion.setProvinciaId(getMpDireccion().getProvinciaId());
			direccion.setDistritoId(getMpDireccion().getDistritoId());
			//numeroCuadra=findMpDireccionItem.getNroCuadra();
			//sector=findMpDireccionItem.getSector();
			
			direccion.setTipoVia(getMpDireccion().getTipoVia());
			direccion.setViaId(getMpDireccion().getViaId());
			direccion.setVia(getMpDireccion().getNombreVia());
			//ladoCuadra=findMpDireccionItem.getLadoCuadra();
	    	
			direccion.setTipoEdificacionId(getMpDireccion().getTipoEdificacionId());
			direccion.setTipoInteriorId(getMpDireccion().getTipoInteriorId());
			direccion.setTipoIngresoId(getMpDireccion().getTipoIngresoId());
			direccion.setFlagFiscal("1");
		    
			recordsDireccion.add(direccion);
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	/*Load de los Combos  */
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
			
			if (tipoAccion.compareTo(Constante.TIPO_ACCION_NUEVO) == 0
					|| tipoAccion.compareTo(Constante.TIPO_ACCION_EDITAR) == 0) {
				viewcmbtipodocumento();
			}
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
				else if(value.toString().compareTo(Constante.TIPO_PERSONA_NATURAL)==0 || value.toString().compareTo(Constante.TIPO_PERSONA_COPROPIEDAD)==0){
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
				
			if (tipoAccion.compareTo(Constante.TIPO_ACCION_NUEVO) == 0
					|| tipoAccion.compareTo(Constante.TIPO_ACCION_EDITAR) == 0) {
				cmbValuetipodocumentoidentidad = "";
				tipodocumentoIdentidadId = null;
				viewcmbtipodocumento();

				mpPersona.setNroDocuIdentidad(null);
			}
			viewcmbMpCondicionContribuyente();
	    	setCmbValuetipocondicionespecial(Constante.CONDICION_ESPECIAL_DEFECTO);
	    	tipoCondicionEspecialId=Constante.CONDICION_ESPECIAL_DEFECTO_ID;
	    	istipocondicionespecial=Boolean.FALSE;
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
			
			/**
			 * Actualizando Lista de Tipos de Documentos para los Relacionados*/
			
			RegistroRelacionadoPersonaManaged registroRelacionadoPersonaManaged = (RegistroRelacionadoPersonaManaged) this.getManaged("registroRelacionadoPersonaManaged");
		
			registroRelacionadoPersonaManaged.actualizarLstTipoDoc(this.tipoPersonaId, this.subtipoPersonaId);
			
			
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
						issituacionempresarial=Boolean.FALSE;
					}
					else{ 
						issituacionempresarial=Boolean.TRUE; 
					}
		    }
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	/*Fin de load Combos*/
	
	public void guardar()throws Exception{
		
		
		
		try{ 
			tipoAccion=Constante.TIPO_ACCION_NUEVO;
			if(!verificarPersonaPorDocumento(mpPersona.getNroDocuIdentidad()))
			{
				if(validarDatosMpPersona()){
					actualizarDatosPersona(Constante.ESTADO_ACTIVO);
					actualizarRelacionados();
					actualizarCondicionEspecial();
					isAccionRealizada=Boolean.TRUE;
					generadoDJ=Constante.GENERADO_DJ;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	/*
	public void actualizar(){
		try{
			tipoAccion=Constante.TIPO_ACCION_EDITAR;
			MpTipoPersonaTipoDocIden temp= new MpTipoPersonaTipoDocIden();
			temp.setSubtipoPersonaId(subtipoPersonaId);
			temp.setTipoPersonaId(tipoPersonaId);
			temp.setTipoDocIdentidadId(tipodocumentoIdentidadId);
			if(personaBo.verificarTipoDocumentoTipoPersona(temp)!=0){
				if(validarDatosMpPersona()){
					actualizarDatosPersona(Constante.ESTADO_ACTIVO);
					actualizarRelacionados();
					actualizarCondicionEspecial();
					generadoDJ=Constante.GENERADO_DJ;
					isAccionRealizada=Boolean.TRUE;//habilitar los botones
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}*/
	
	public void vistaPrevia()throws Exception{
		try{    
			mpPersona.setFechaInscripcion(new Timestamp(this.getFechaInscripcion().getTime()));
			if(!verificarPersonaPorDocumento(mpPersona.getNroDocuIdentidad()))
			{
				if(validarDatosMpPersona()){
					tipoAccion=Constante.TIPO_ACCION_VISTA_PREVIA;
					actualizarDatosPersona(Constante.ESTADO_PENDIENTE);
					actualizarRelacionados();
					actualizarCondicionEspecial();
					isAccionRealizada=Boolean.FALSE;
					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}

	/**
	 * Al hacer click en el boton Grabar Fiscal  de la pantalla registropersonabasico.xhtml
	 * @throws Exception
	 */
	public void guardarBasico()throws Exception{
		try{   
			if(mpPersona.getPersonaId()!=null){
				if(!verificarPersonaPorDocumento(mpPersona.getNroDocuIdentidad()))
				{    		
	  				if(validarDatosMpPersonaBasico()){
						actualizarDatosPersonaBasico();
						
						if (pantallaUso.equals(ReusoFormCns.REGISTRO_PREDIAL)) {
							BuscarPersonaDTO transferente=new BuscarPersonaDTO(); 
							RegistroPredioManaged registroPredioManaged=(RegistroPredioManaged)getManaged("registroPredioManaged");
							transferente.setPersonaId(mpPersona.getPersonaId());
							transferente.setTipodocumentoIdentidadId(mpPersona.getTipoDocIdentidadId());
							transferente.setTipoDocIdentidad(mapIRpTipodocumentoidentidad.get(mpPersona.getTipoDocIdentidadId()));
							transferente.setNroDocuIdentidad(mpPersona.getNroDocuIdentidad());
							transferente.setApellidosNombres(mpPersona.getApellidosNombres());
							
							registroPredioManaged.setTransferente(transferente);
							
						}else if(pantallaUso.equals(ReusoFormCns.DESCARGO_PREDIAL)){
							BuscarPersonaDTO adquiriente=new BuscarPersonaDTO(); 
							DescargoPredialManaged descargoPredialManaged=(DescargoPredialManaged)getManaged("descargoPredialManaged");
							adquiriente.setPersonaId(mpPersona.getPersonaId());
							adquiriente.setTipodocumentoIdentidadId(mpPersona.getTipoDocIdentidadId());
							adquiriente.setTipoDocIdentidad(mapIRpTipodocumentoidentidad.get(mpPersona.getTipoDocIdentidadId()));
							adquiriente.setNroDocuIdentidad(mpPersona.getNroDocuIdentidad());
							adquiriente.setApellidosNombres(mpPersona.getApellidosNombres());
							
							descargoPredialManaged.loadLstTransferentes(adquiriente);	
							
						}else if(pantallaUso.equals(ReusoFormCns.REGISTRO_VEHICULAR)){
							BuscarPersonaDTO transferente=new BuscarPersonaDTO(); 
							
							transferente.setPersonaId(mpPersona.getPersonaId());
							transferente.setTipodocumentoIdentidadId(mpPersona.getTipoDocIdentidadId());
							transferente.setTipoDocIdentidad(mapIRpTipodocumentoidentidad.get(mpPersona.getTipoDocIdentidadId()));
							transferente.setNroDocuIdentidad(mpPersona.getNroDocuIdentidad());
							transferente.setApellidosNombres(mpPersona.getApellidosNombres());
							
							if (!getNuevadjRegistroManaged().existeTransfEnLista(
									transferente.getPersonaId())) {
								getNuevadjRegistroManaged().getLstTransferentes().add(
										transferente);
							}	
							setDestinoRefresh("tblTransferentes");

						}else if(pantallaUso.equals(ReusoFormCns.DESCARGO_VEHICULAR)){
							BuscarPersonaDTO adquiriente=new BuscarPersonaDTO(); 
							DescargoVehicularManaged descargoVehicularManaged=(DescargoVehicularManaged)getManaged("descargoVehicularManaged");
							adquiriente.setPersonaId(mpPersona.getPersonaId());
							adquiriente.setTipodocumentoIdentidadId(mpPersona.getTipoDocIdentidadId());
							adquiriente.setTipoDocIdentidad(mapIRpTipodocumentoidentidad.get(mpPersona.getTipoDocIdentidadId()));
							adquiriente.setNroDocuIdentidad(mpPersona.getNroDocuIdentidad());
							adquiriente.setApellidosNombres(mpPersona.getApellidosNombres());
							
							descargoVehicularManaged.getLstTransferentes().add(adquiriente);							
						}
						
						salirPersonaBasico();
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	//Inicio ITantamango
	public NuevadjRegistroManaged getNuevadjRegistroManaged() {
		return (NuevadjRegistroManaged) getManaged("nuevadjRegistroManaged");
	}
	//Fin ITantamango
	
	public void actualizarCondicionEspecial()throws Exception{
		if(cmbValuetipocondicionespecial.compareTo(Constante.CONDICION_ESPECIAL_DEFECTO)!=0){
			guardarGnCondicionEspecial();	
		}else if(cmbValuetipocondicionespecial.compareTo(Constante.CONDICION_ESPECIAL_DEFECTO)==0){
			darBajaGnCondicionEspecial(gnCondicionEspecial);
		}
	}
	
	public void actualizarRelacionados(){
		try{
			personaBo.guardarMpRelacionado(getMpPersona().getPersonaId(),recordsRelacionado);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void salirPersonaBasico(){
		mpPersona= new MpPersona(personaId);
		mpPersona.setEstado(Constante.ESTADO_ACTIVO);
		mpPersona.setPersonaId(Constante.RESULT_PENDING);
		
		mpDireccion=new MpDireccion();
		mpDireccion.setEstado(Constante.ESTADO_ACTIVO);
		
		mpPersonaDomicilio=new MpPersonaDomicilio();
		mpPersonaDomicilio.setEstado(Constante.ESTADO_ACTIVO);
		
		ubicacionDTO=new UbicacionDTO();
		
		recordsDireccion= new ArrayList<FindMpDireccion>();
		isDomiciliosIngresados=false;
		cmbValuetipopersona="";
		cmbValuesubtipopersona="";
		cmbValuetipodocumentoidentidad="";
		fechaNacoConst=null;
	}
	
	public boolean validarDatosMpPersonaBasico()throws Exception{
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
		
			if(!isDomiciliosIngresados){
				addErrorMessage(getMsg("mp.domiciliosiningresar"));
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
			
				
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
		return true;
	}
	
	public void actualizarDatosPersona(String estado)throws Exception{
		try{
			String ApellidosNombresORazonSocial="";
			   
			if(tipoAccion!=Constante.TIPO_ACCION_VISTA_PREVIA){
				ApellidosNombresORazonSocial="";
				mpPersona.setApellidosNombres("");
				
				if(tipoPersonaId==Constante.TIPO_PERSONA_NATURAL_ID || tipoPersonaId==Constante.TIPO_PERSONA_SOCIEDADCONYUGAL_ID || tipoPersonaId==Constante.TIPO_PERSONA_COPROPIEDAD_ID)
				{
		    	   mpPersona.setRazonSocial(null);
		    	   ApellidosNombresORazonSocial = mpPersona.getApePaterno()+' '+mpPersona.getApeMaterno()+' '+mpPersona.getPrimerNombre();
		    	   if(mpPersona.getSegundoNombre()!=null)
		        		ApellidosNombresORazonSocial = ApellidosNombresORazonSocial + ' ' + mpPersona.getSegundoNombre();
				}
				if(tipoPersonaId==Constante.TIPO_PERSONA_JURIDICA_ID || tipoPersonaId==Constante.TIPO_PERSONA_PATRIMONIAUTONOMO_ID)
				{
		    	   mpPersona.setApeMaterno(null);
		    	   mpPersona.setApePaterno(null);
		    	   mpPersona.setPrimerNombre(null);
		    	   mpPersona.setSegundoNombre(null);
		    	   ApellidosNombresORazonSocial= mpPersona.getRazonSocial();
		    	   //mpPersona.setFlagEstatal(flagEstatal?Constante.ESTADO_ACTIVO:Constante.ESTADO_INACTIVO);
		    	   if (tipoPersonaId==Constante.TIPO_PERSONA_JURIDICA_ID)
		    	   {
			    	   String seleccionado =  this.getSeleccionTipoEmpresa().toString();
						
						if (seleccionado.equals("No Estatal"))
						{
							mpPersona.setFlagEstatal("0");
							flagEstatal = Boolean.FALSE;
						}
						else
						{
							mpPersona.setFlagEstatal("1");
							flagEstatal = Boolean.TRUE;
						}		
		    	   }

				}				
				
			
		        mpPersona.setApellidosNombres(ApellidosNombresORazonSocial);
				mpPersona.setFechaInscripcion(DateUtil.dateToSqlTimestamp(fechaInscripcion));
				mpPersona.setFechaActualizacion(DateUtil.getCurrentDate());
				mpPersona.setTipoPersonaId(tipoPersonaId);
		        mpPersona.setSubtipoPersonaId(subtipoPersonaId);
		        mpPersona.setTipoDocIdentidadId(tipodocumentoIdentidadId);
		        if(mpPersona.getNotificaEmail()==true)
		        	mpPersona.setFlagNotificaEmail(Constante.FLAG_ACTIVO);
		        else if(mpPersona.getNotificaEmail()==false)
			        mpPersona.setFlagNotificaEmail(Constante.FLAG_INACTIVO);
		       
		        if(situacionEmpresarialId != null)
		        	mpPersona.setSituacionEmpresarialId(situacionEmpresarialId);
		        else{
		        	if(cmbValueMpSituacionEmpresarial!=null&&cmbValueMpSituacionEmpresarial.trim().length()>0){
		        		situacionEmpresarialId=mapMpSituacionEmpresarial.get(cmbValueMpSituacionEmpresarial);
			        	mpPersona.setSituacionEmpresarialId(situacionEmpresarialId);
		        	}
		        }
		   
		        if(fechaInscripcion != null)
		        	mpPersona.setFechaInscripcion(DateUtil.dateToSqlTimestamp(fechaInscripcion));
		        if(fechaNacoConst != null)
		        	mpPersona.setFechaDeclaracion(DateUtil.dateToSqlTimestamp(fechaNacoConst));
				if(fechaIniSituacionE != null)
					mpPersona.setFechaSituacionEmpre(DateUtil.dateToSqlTimestamp(fechaIniSituacionE));
				if(fechaFinSituacionE!=null)
					mpPersona.setFechaFinSituacionEmpresarial(DateUtil.dateToSqlTimestamp(fechaFinSituacionE));
				if(fechaEmisionDoc!=null)
					mpPersona.setFechaEmisionSituacionEmpresarial(DateUtil.dateToSqlTimestamp(fechaEmisionDoc));
				if(fechaDefuncion!=null)
					mpPersona.setFechaDefuncion(DateUtil.dateToSqlTimestamp(fechaDefuncion));
			
				if(mpPersona.getSituacionEmpresarialId().compareTo(Constante.SITUACION_EMPRESARIAL_ID_DEFECTO)==0 || mpPersona.getSituacionEmpresarialId().compareTo(Constante.SITUACION_EMPRESARIAL_ID_DEFECTO_PERJURID_PATRIAUTO)==0){
	        		fechaFinSituacionE=null;
					fechaIniSituacionE=null;
					fechaEmisionDoc=null;
					mpPersona.setTipoDocSustSituacionEmpresarial(null);
					mpPersona.setNroDocSustSituacionEmpresarial(null);
					mpPersona.setFechaFinSituacionEmpresarial(null);
					mpPersona.setFechaSituacionEmpre(null);
					mpPersona.setFechaEmisionSituacionEmpresarial(null);
					
	        	}
				
				if(mpPersona.getNroDj()==null || mpPersona.getNroDj()==0){
					mpPersona.setEstado(Constante.ESTADO_PENDIENTE);
				}else if(mpPersona.getNroDj()>0){
					mpPersona.setEstado(Constante.ESTADO_ACTIVO);
				}
				mpPersona.setTerminal(generalBo.obtenerTerminalCliente());
				
				guardarPersona(mpPersona,estado);
				getSessionMap().put("nuevoPersona", mpPersona);  	
			}if(tipoAccion==Constante.TIPO_ACCION_VISTA_PREVIA){
				mpPersona.setApellidosNombres("");
			    if(tipoPersonaId==Constante.TIPO_PERSONA_NATURAL_ID || tipoPersonaId==Constante.TIPO_PERSONA_SOCIEDADCONYUGAL_ID || tipoPersonaId==Constante.TIPO_PERSONA_COPROPIEDAD_ID){
		    	   ApellidosNombresORazonSocial = mpPersona.getApePaterno()+' '+mpPersona.getApeMaterno()+' '+mpPersona.getPrimerNombre();
		    	   if(mpPersona.getSegundoNombre()!=null)
		        		ApellidosNombresORazonSocial = ApellidosNombresORazonSocial + ' ' + mpPersona.getSegundoNombre();
			    }
			    
			    if(tipoPersonaId==Constante.TIPO_PERSONA_JURIDICA_ID || tipoPersonaId==Constante.TIPO_PERSONA_PATRIMONIAUTONOMO_ID){
			    	ApellidosNombresORazonSocial= mpPersona.getRazonSocial();
			    						
			    	if (tipoPersonaId==Constante.TIPO_PERSONA_JURIDICA_ID)
			    	   {
					    	String seleccionado =  this.getSeleccionTipoEmpresa().toString();
							if (seleccionado.equals("No Estatal"))
							{
								mpPersona.setFlagEstatal("0");
								flagEstatal = Boolean.FALSE;
							}
							else
							{
								mpPersona.setFlagEstatal("1");
								flagEstatal = Boolean.TRUE;
							}
			    	   }
				}
			    
		        mpPersona.setApellidosNombres(ApellidosNombresORazonSocial);
				
		        mpPersona.setFechaRegistro(DateUtil.dateToSqlTimestamp(new Date()));
				if(mpPersona.getNroDj()==null || mpPersona.getNroDj()==0){
					mpPersona.setEstado(Constante.ESTADO_PENDIENTE);
				}else if(mpPersona.getNroDj()>0){
					mpPersona.setEstado(Constante.ESTADO_ACTIVO);
				}
				mpPersona.setTerminal(generalBo.obtenerTerminalCliente());
				mpPersona.setFechaInscripcion(DateUtil.getCurrentDate());
				mpPersona.setTipoPersonaId(tipoPersonaId);
		        mpPersona.setSubtipoPersonaId(subtipoPersonaId);
		        mpPersona.setTipoDocIdentidadId(tipodocumentoIdentidadId);
			
		        if(mpPersona.getNotificaEmail()==true)
		        	mpPersona.setFlagNotificaEmail(Constante.FLAG_ACTIVO);
		        else if(mpPersona.getNotificaEmail()==false)
			            mpPersona.setFlagNotificaEmail(Constante.FLAG_INACTIVO);
		       
		        if(situacionEmpresarialId != null)
		        	mpPersona.setSituacionEmpresarialId(situacionEmpresarialId);
		        else{
		        	if(cmbValueMpSituacionEmpresarial!=null&&cmbValueMpSituacionEmpresarial.trim().length()>0){
		        		situacionEmpresarialId=mapMpSituacionEmpresarial.get(cmbValueMpSituacionEmpresarial);
			        	mpPersona.setSituacionEmpresarialId(situacionEmpresarialId);
			        
		        	}
		        }
		   
		        if(fechaInscripcion != null)
		        	mpPersona.setFechaInscripcion(DateUtil.dateToSqlTimestamp(fechaInscripcion));
		        if(fechaNacoConst != null)
		        	mpPersona.setFechaDeclaracion(DateUtil.dateToSqlTimestamp(fechaNacoConst));
				if(fechaIniSituacionE != null)
					mpPersona.setFechaSituacionEmpre(DateUtil.dateToSqlTimestamp(fechaIniSituacionE));
				if(fechaFinSituacionE!=null)
					mpPersona.setFechaFinSituacionEmpresarial(DateUtil.dateToSqlTimestamp(fechaFinSituacionE));
				if(fechaEmisionDoc!=null)
					mpPersona.setFechaEmisionSituacionEmpresarial(DateUtil.dateToSqlTimestamp(fechaEmisionDoc));
				if(fechaDefuncion!=null)
					mpPersona.setFechaDefuncion(DateUtil.dateToSqlTimestamp(fechaDefuncion));
					
				FindMpPersona findMpPersona=new FindMpPersona(); 
				if(mpPersona.getNroPartidaDefuncion()!=null)
					findMpPersona.setNroPartidaDefuncion(mpPersona.getNroPartidaDefuncion());
				if(fechaDefuncion!=null)
					findMpPersona.setFechaDefuncion(DateUtil.dateToSqlTimestamp(fechaDefuncion));
			
				findMpPersona.setApellidosNombres(ApellidosNombresORazonSocial);
				if(fechaNacoConst != null)
					findMpPersona.setFechaDeclaracion(DateUtil.dateToSqlTimestamp(fechaNacoConst));
			    findMpPersona.setApePaterno(mpPersona.getApePaterno());
			    findMpPersona.setApeMaterno(mpPersona.getApeMaterno());
			    findMpPersona.setPrimerNombre(mpPersona.getPrimerNombre());
			    findMpPersona.setSegundoNombre(mpPersona.getSegundoNombre());
		        findMpPersona.setTelefono(mpPersona.getTelefono());
		        findMpPersona.setFax(mpPersona.getFax());
		        findMpPersona.setRazonSocial(mpPersona.getRazonSocial());
		        findMpPersona.setTwitter(mpPersona.getTwitter());
				findMpPersona.setEmail(mpPersona.getEmail());
				findMpPersona.setNroDocuIdentidad(mpPersona.getNroDocuIdentidad());
				findMpPersona.setPartidaDefuncion(mpPersona.getNroPartidaDefuncion());
				
				if(situacionEmpresarialId.compareTo(Constante.SITUACION_EMPRESARIAL_ID_DEFECTO)!=0 && situacionEmpresarialId.compareTo(Constante.SITUACION_EMPRESARIAL_ID_DEFECTO_PERJURID_PATRIAUTO)!=0){
					if(fechaIniSituacionE != null)
						findMpPersona.setFechaSituacionEmpresarial(DateUtil.dateToSqlTimestamp(fechaIniSituacionE));
					if(fechaFinSituacionE!=null)
						findMpPersona.setFechaFinSituacionEmpresarial(DateUtil.dateToSqlTimestamp(fechaFinSituacionE));
					if(fechaEmisionDoc!=null)
						findMpPersona.setFechaEmisionSituacionEmpresarial(DateUtil.dateToSqlTimestamp(fechaEmisionDoc));
					
					findMpPersona.setSituacionEmpresarial(cmbValueMpSituacionEmpresarial);
					findMpPersona.setTipoDocSustSituacionEmpresarial(mpPersona.getTipoDocSustSituacionEmpresarial());
					findMpPersona.setNroDocSustSituacionEmpresarial(mpPersona.getNroDocSustSituacionEmpresarial());
				 }
				if(situacionEmpresarialId.compareTo(Constante.SITUACION_EMPRESARIAL_ID_DEFECTO)==0 || situacionEmpresarialId.compareTo(Constante.SITUACION_EMPRESARIAL_ID_DEFECTO_PERJURID_PATRIAUTO)==0){
					findMpPersona.setFechaSituacionEmpresarial(null);
					findMpPersona.setFechaEmisionSituacionEmpresarial(null);
					findMpPersona.setFechaFinSituacionEmpresarial(null);
					if(tipoPersonaId.compareTo(Constante.TIPO_PERSONA_NATURAL_ID)==0 || tipoPersonaId.compareTo(Constante.TIPO_PERSONA_SOCIEDADCONYUGAL_ID)==0)
					findMpPersona.setSituacionEmpresarial(Constante.SITUACION_EMPRESARIAL_DEFECTO);
					if(tipoPersonaId.compareTo(Constante.TIPO_PERSONA_JURIDICA_ID)==0 || tipoPersonaId.compareTo(Constante.TIPO_PERSONA_PATRIMONIAUTONOMO_ID)==0)
						findMpPersona.setSituacionEmpresarial(Constante.SITUACION_EMPRESARIAL_DEF_PERJURID_PATRIAUTO);
					findMpPersona.setTipoDocSustSituacionEmpresarial(null);
					findMpPersona.setNroDocSustSituacionEmpresarial(null);
				 }
				if(tipoCondicionEspecialId!=Constante.CONDICION_ESPECIAL_DEFECTO_ID){
					if(fechaInicioCond!=null)
						findMpPersona.setFechaInicioCondContri(DateUtil.dateToSqlTimestamp(fechaInicioCond));
					if(fechaFinCond!=null)
						findMpPersona.setFechafinCondContri(DateUtil.dateToSqlTimestamp(fechaFinCond));
					if(fechaDocumento!=null)
						findMpPersona.setFechaDocumentoCondContri(DateUtil.dateToSqlTimestamp(fechaDocumento));
					findMpPersona.setNroDocumentoCondContri(gnCondicionEspecial.getNroDocumento());
					findMpPersona.setTipoCondicionEspecial(cmbValuetipocondicionespecial);
					findMpPersona.setTipodocumento(cmbValuetipodocumentoCondicionEspecial);
					//se agrega el porcentaje de la condiciones especial
					findMpPersona.setPorcentaje(gnCondicionEspecial.getPorcentaje());
				 }
				if(tipoCondicionEspecialId==Constante.CONDICION_ESPECIAL_DEFECTO_ID){
					findMpPersona.setFechaInicioCondContri(null);
					findMpPersona.setFechafinCondContri(null);
					findMpPersona.setFechaDocumentoCondContri(null);
					findMpPersona.setNroDocumentoCondContri(null);
					findMpPersona.setTipoCondicionEspecial(Constante.CONDICION_ESPECIAL_DEFECTO);
					findMpPersona.setTipodocumento(null);
				 }
				
				findMpPersona.setTipoPersona(cmbValuetipopersona);
				findMpPersona.setSubtipopersona(cmbValuesubtipopersona);
				findMpPersona.setTipoDocumentoIdentidad(cmbValuetipodocumentoidentidad);
				if(mpPersona.getNotificaEmail()==true)
					findMpPersona.setNotificaEmail("SI");
		        else if(mpPersona.getNotificaEmail()==false)
		        	findMpPersona.setNotificaEmail("NO");
				
				if(mpPersona.getNroDj()!=null)
					if(mpPersona.getNroDj()>0)
					estado=Constante.ESTADO_ACTIVO;
				
				mpPersona=guardarPersona(mpPersona,estado);
				findMpPersona.setPersonaId(mpPersona.getPersonaId());
				findMpPersona.setNroDj(mpPersona.getNroDj());
				
				//Se agrega la fecha de inscripcion
				findMpPersona.setFechaInscripcion(mpPersona.getFechaInscripcion());
				findMpPersona.setFlagEstatal(flagEstatal?Constante.ESTADO_ACTIVO:Constante.ESTADO_INACTIVO);
				/*
					if (getSeleccionTipoEmpresa().equals("No Estatal"))
			    	{
			    		  findMpPersona.setFlagEstatal(Constante.ESTADO_INACTIVO);
			    	}
			    	else
			    	{
			    		findMpPersona.setFlagEstatal(Constante.ESTADO_ACTIVO);
			    	}
				*/
				findMpPersona.setGlosa(mpPersona.getGlosa());
				
				getSessionMap().put("findMpPersona1", findMpPersona);
				getSessionMap().put("recordsRelacionado", recordsRelacionado);
				getSessionMap().put("recordsDireccion", recordsDireccion);
				getSessionMap().put("nuevoDireccion", getMpDireccion());
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}

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
			
			if(mpPersona.getTelefono() ==null || mpPersona.getTelefono().trim().length()==0 ){
				addErrorMessage(getMsg("Debe ingresar un Nümero Telefónico."));
			     return false;
			}
			
			
			
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
							//FindMpRelacionado temp = recordsRelacionado.get(i);
							 MpRelacionado temp = recordsRelacionado.get(i);
		 					 //if(temp.getRelacionadoDescripcion().compareTo(Constante.TIPO_RELACIONADO_PADREOTUTOR)!=0){
							 if(temp.getTipoRelacionId()!=Constante.TIPO_RELACIONADO_PADREOTUTOR_ID){
								 // addErrorMessage(getMsg("mp.errormenoredadconyuge"));
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
							//FindMpRelacionado temp = recordsRelacionado.get(i);
							 MpRelacionado temp = recordsRelacionado.get(i);
		 					 if(temp.getTipoRelacionId()==Constante.TIPO_RELACIONADO_CONYUGE_ID){
								  addErrorMessage(getMsg("mp.errormenoredadconyuge"));
								  return false;
							}
						}
						
					}
				}
				if(getCmbValuesubtipopersona().toString().compareTo(Constante.SUB_TIPO_PERSONA_SUC_INDIVISA)==0){
					if(recordsRelacionado.size()>0){
						for(int i=0;i<recordsRelacionado.size();i++){
							//FindMpRelacionado temp = recordsRelacionado.get(i);
							 MpRelacionado temp = recordsRelacionado.get(i);
		 					 if(temp.getMpTipoDocIdentidadId() == tipodocumentoIdentidadId && temp.getNroDocuIdentidad().compareTo(mpPersona.getNroDocuIdentidad())==0){
								  addErrorMessage("Un Relacionado no puede tener el mismo Tipo y Nro de Doc. que el Contribuyente en Suc. Indivisa ");
								  return false;
							}
						}
						
					}
				}
			   }
			
			if(getCmbValuetipopersona().toString().compareTo(Constante.TIPO_PERSONA_COPROPIEDAD)==0){
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
				if(getCmbValuesubtipopersona().toString().compareTo(Constante.SUB_TIPO_PERSONA_COPROPIETARIO)!=0)
					if(recordsRelacionado.size()==0){
						 addErrorMessage(getMsg("mp.relacionado"));
						 return false;
					}				
				if(getCmbValuesubtipopersona().toString().compareTo(Constante.SUB_TIPO_PERSONA_SUC_INDIVISA)==0){
					if(recordsRelacionado.size()>0){
						for(int i=0;i<recordsRelacionado.size();i++){
							//FindMpRelacionado temp = recordsRelacionado.get(i);
							 MpRelacionado temp = recordsRelacionado.get(i);
		 					 if(temp.getMpTipoDocIdentidadId() == tipodocumentoIdentidadId && temp.getNroDocuIdentidad().compareTo(mpPersona.getNroDocuIdentidad())==0){
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
						//FindMpRelacionado temp = recordsRelacionado.get(i);
						MpRelacionado temp = recordsRelacionado.get(i);
	 					if(temp.getTipoRelacionId()==Constante.TIPO_RELACIONADO_CONYUGE_ID){
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
		if(situacionEmpresarialId!=null){
			if(getCmbValuetipopersona().compareTo(tipoPersona)==0 &&  situacionEmpresarialId!=valorPorDefecto){
				
				/*Desactrivado no se encuentra donde ingresar los datos
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
				*/
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
			//Se comenta la fecha del documento, pues no siempre sera antes de la fecha de inicio
//			if(tipoCondicionEspecialId != Constante.TIPO_CONDICION_ESPECIAL_PENSIONISTA_ID){
//				if(fechaDocumento.compareTo(fechaInicioCond)>0){
//					addErrorMessage(getMsg("mp.errorMayorfechadocumentofechaInicioCondicion"));
//					return false ;
//				}
//			}
			//La fecha de documento puede ser mayor al periodo de regulacion del beneficio
//			if(tipoCondicionEspecialId != Constante.TIPO_CONDICION_ESPECIAL_PENSIONISTA_ID){
//				if(fechaDocumento.compareTo(fechaFinCond)>0){
//					addErrorMessage(getMsg("mp.errorMayorfechadocumentofechaFinCondicion"));
//					return false ;
//				}
//			}
			if(fechaInicioCond!=null && fechaFinCond!=null){
				   if (fechaInicioCond.compareTo(fechaFinCond) > 0){
					   addErrorMessage(getMsg("mp.errorMayorfechaInicioCondicion"));
						return false ;
					}
			    }
		}
		return temp;
	}
	
	
	public void nuevaDireccion(){
		
	}
	
	public void deleteRelacionado(){
		try{
			if(relacionado!=null){
				recordsRelacionado.remove(relacionado);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void guardarGnCondicionEspecial()throws Exception{
		try{
		    if(fechaInicioCond!=null)
		    	gnCondicionEspecial.setFechaInicio(DateUtil.dateToSqlTimestamp(fechaInicioCond));
			if(fechaFinCond!=null)
				gnCondicionEspecial.setFechaFin(DateUtil.dateToSqlTimestamp(fechaFinCond));
			if(fechaDocumento!=null)
				gnCondicionEspecial.setFechaDocumento(DateUtil.dateToSqlTimestamp(fechaDocumento));
			
			//cc: gnCondicionEspecial.setFechaRegistro(DateUtil.getCurrentDate());
			if(tipoCondicionEspecialId != null)
				gnCondicionEspecial.setTipoCondEspecialId(tipoCondicionEspecialId);
		    else{
		       	tipoCondicionEspecialId=mapMpTipocondicionespecial.get(Constante.CONDICION_ESPECIAL_DEFECTO);
		       	gnCondicionEspecial.setTipoCondEspecialId(tipoCondicionEspecialId);
		    } 
			
			gnCondicionEspecial.setTipoDocumento(tipodocumentoId);

			gnCondicionEspecial.setEstado(Constante.ESTADO_ACTIVO);
			personaBo.guardarGnCondicionEspecial(mpPersona.getPersonaId(),gnCondicionEspecial);
			personaBo.grabarTablaGnCondicionEspecialHistorico(gnCondicionEspecial);
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	public void darBajaGnCondicionEspecial(GnCondicionEspecial gnCondicionEspecialTemp)throws Exception{
		try{
		   personaBo.darBajaCondicionEspecial(gnCondicionEspecialTemp);
		   personaBo.grabarTablaGnCondicionEspecialHistorico(gnCondicionEspecialTemp);
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	/*Navegacion*/	
	public String salir(){
		if(mpPersona.getNroDj()==null||mpPersona.getNroDj()==0){
			 addErrorMessage(getMsg("mp.errornumerodj"));
		}
		return "salir";
	}
	
	public void salirRP(){
		try{
		if(mpPersona.getNroDj()==null||mpPersona.getNroDj()==0){
			 addErrorMessage(getMsg("mp.errornumerodj"));
		}
			
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
	    }
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
			addErrorMessage(getMsg("mp.errortipodocumentoidentidad"));
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
		
			if(verificarPersonaPorDocumento(getTxtNumDocumento().getSubmittedValue().toString()))
				return false;
		
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
						addErrorMessage(getMsg("Persona Con Dni: "+dni+" Existe!!"));
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
			Integer tipoContribuyenteId=0;
			if(cmbValuetipopersona!=null)
				tipoContribuyenteId=mapRpTipopersona.get(cmbValuetipopersona);
			
			Integer subTipoContribuyenteId=0;
			if(cmbValuesubtipopersona!=null)
				subTipoContribuyenteId=mapRpsubTipopersona.get(cmbValuesubtipopersona);
			
			Integer tipoDocumentoId=0;
			if(cmbValuetipodocumentoidentidad!=null)
				tipoDocumentoId=mapRpTipodocumentoidentidad.get(cmbValuetipodocumentoidentidad);
			
			FindMpPersona findMpPersonaTemp=personaBo.existeContribuyente(tipoContribuyenteId,subTipoContribuyenteId,tipoDocumentoId,nroDoc);
			if(findMpPersonaTemp !=null){
				Integer a=findMpPersonaTemp.getPersonaId();
				Integer b=mpPersona.getPersonaId();
				if(a.compareTo(b)!=0){
					addErrorMessage(getMsg("Persona Con "+findMpPersonaTemp.getTipodocumento()+" Nro: "+nroDoc+"  Existe"));
					addErrorMessage(getMsg("Código: "+findMpPersonaTemp.getPersonaId()));
					if(findMpPersonaTemp.getTipodocumentoId().equals(Constante.TIPO_PERSONA_JURIDICA_ID)){
						addErrorMessage(getMsg("Razon Social: "+findMpPersonaTemp.getApellidosNombres()));
					}else{
						addErrorMessage(getMsg("Nombres: "+findMpPersonaTemp.getApellidosNombres()));	
					}
					addErrorMessage(getMsg(findMpPersonaTemp.getTipoPersona()+" / "+findMpPersonaTemp.getSubtipopersona()));
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
	
	public String imprimir(){
		return "imprimir";
	}
	
	public void loadSeleccion(ValueChangeEvent event) {
		try {
			HtmlComboBox combo = (HtmlComboBox) event.getComponent();
			String value = combo.getValue().toString();
			this.setSeleccionTipoEmpresa(value);

		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public void buscarDNIPorDatosdeIndentificacion(){
		try {
			if(getMpPersona() != null){
				if(getMpPersona().getApePaterno().length() > 1 && getMpPersona().getApeMaterno().length() > 1){
					setListaPersonas(personaBo.buscarDNIPorDatosIdentificacion(getMpPersona().getApePaterno(), getMpPersona().getApeMaterno(),
							getMpPersona().getPrimerNombre(), getMpPersona().getSegundoNombre()));
				}else{
					
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}			
	}
	
	public void checkFlagEstatal(ValueChangeEvent event) {
		Boolean check = (Boolean) event.getNewValue();
		if (check == Boolean.TRUE) {
			setFlagEstatal(Boolean.TRUE);
		} else {
			setFlagEstatal(Boolean.FALSE);
		}
	}
	
	
	public void cancelUbicacion() {
		mpPersona.setUbicableControl(!mpPersona.isUbicableControl());
		System.out.print("*********************");
		System.out.print(mpPersona.isUbicableControl());
	}
	
	public void aceptarUbicacion() {
		try {
			System.out.println("*********************");
			System.out.println(mpPersona.isUbicableControl());
			
			int flag = 0;
			
			if(mpPersona.isUbicableControl()) {
				flag = 1;
			}
			
			personaBo.changeFlagUbicableControl(mpPersona.getPersonaId(), flag);
		
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void limpiarMpPersona(){
		mpPersona = new MpPersona();
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
	
	
	public ArrayList<MpRelacionado> getRecordsRelacionado() {
		return recordsRelacionado;
	}
	
	public void setRecordsRelacionado(ArrayList<MpRelacionado> recordsRelacionado) {
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

	public Integer getPersonaId() {
		return personaId;
	}

	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}
	
	public UbicacionDTO getUbicacionDTO() {
		return ubicacionDTO;
	}

	public void setUbicacionDTO(UbicacionDTO ubicacionDTO) {
		this.ubicacionDTO = ubicacionDTO;
	}
	
	public HashMap<Integer, String> getMapMpTipoRelacion() {
		return mapMpTipoRelacion;
	}

	public void setMapMpTipoRelacion(HashMap<Integer, String> mapMpTipoRelacion) {
		this.mapMpTipoRelacion = mapMpTipoRelacion;
	}
	
	public MpRelacionado getRelacionado() {
		return relacionado;
	}

	public void setRelacionado(MpRelacionado relacionado) {
		this.relacionado = relacionado;
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
	
	public FindMpPersona getFinMpPersonaItem() {
		return finMpPersonaItem;
	}

	public void setFinMpPersonaItem(FindMpPersona finMpPersonaItem) {
		this.finMpPersonaItem = finMpPersonaItem;
	}

	public List<TgPersona> getListaPersonas() {
		return listaPersonas;
	}

	public void setListaPersonas(List<TgPersona> listaPersonas) {
		this.listaPersonas = listaPersonas;
	}

	public boolean isFlagEstatal() {
		return flagEstatal;
	}

	public void setFlagEstatal(boolean flagEstatal) {
		this.flagEstatal = flagEstatal;
	}

	public String getSeleccionTipoEmpresa() {
		return seleccionTipoEmpresa;
	}

	public void setSeleccionTipoEmpresa(String seleccionTipoEmpresa) {
		this.seleccionTipoEmpresa = seleccionTipoEmpresa;
	}
	
	public HtmlComboBox getCmbHtmlTipoEmpresa() {
		return cmbHtmlTipoEmpresa;
	}

	public void setCmbHtmlTipoEmpresa(HtmlComboBox cmbHtmlTipoEmpresa) {
		this.cmbHtmlTipoEmpresa = cmbHtmlTipoEmpresa;
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

	public ArrayList<MpRelacionado> getRecordsRelacionadoAdult() {
		return recordsRelacionadoAdult;
	}

	public void setRecordsRelacionadoAdult(ArrayList<MpRelacionado> recordsRelacionadoAdult) {
		this.recordsRelacionadoAdult = recordsRelacionadoAdult;
	}
	

}
