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

import com.sat.sisat.common.business.GeneralBoRemote;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.Util;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.persistence.entity.GnDepartamento;
import com.sat.sisat.persistence.entity.GnDistrito;
import com.sat.sisat.persistence.entity.GnProvincia;
import com.sat.sisat.persistence.entity.GnTipoDenoUrbana;
import com.sat.sisat.persistence.entity.GnTipoEdificacion;
import com.sat.sisat.persistence.entity.GnTipoIngreso;
import com.sat.sisat.persistence.entity.GnTipoInterior;
import com.sat.sisat.persistence.entity.GnTipoVia;
import com.sat.sisat.persistence.entity.GnVia;
import com.sat.sisat.persistence.entity.MpDireccion;
import com.sat.sisat.persistence.entity.MpPersona;
import com.sat.sisat.persistence.entity.MpPersonaDomicilio;
import com.sat.sisat.persistence.entity.Ubigeo;
import com.sat.sisat.persona.business.PersonaBoRemote;
import com.sat.sisat.predial.dto.FindMpDireccion;
import com.sat.sisat.predial.dto.UbicacionDTO;

@ManagedBean
@ViewScoped
public class RegistroDireccionPersonaManaged extends BaseManaged {
	private String direccionPersona=""; 
	
	@EJB
	PersonaBoRemote personaBo;
	
	@EJB
	GeneralBoRemote generalBo;
	
	private Ubigeo ubigeo;
	
	private HtmlComboBox cmbtipoedificio;
	private List<SelectItem> lsttipoedificio;
	private HashMap<String, Integer> mapGnTipoEdificacion=new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIGnTipoEdificacion=new HashMap<Integer,String>();
	private String cmbvaluetipoedificio;
	
	private HtmlComboBox cmbtipointerior;
	private List<SelectItem> lsttipointerior;
	private HashMap<String, Integer> mapGnTipoInterior=new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIGnTipoInterior=new HashMap<Integer, String>();
	private String cmbvaluetipointerior;
	
	private HtmlComboBox cmbtipoingreso;
	private List<SelectItem> lsttipoingreso;
	private HashMap<String, Integer> mapGnTipoIngreso=new HashMap<String, Integer>();
	private HashMap<Integer,String> mapIGnTipoIngreso=new HashMap<Integer,String>();
	private String cmbvaluetipoingreso;
	
	private HtmlComboBox cmbDepartamento;
	private List<SelectItem> lstdepartamento = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapGnDepartamento=new HashMap<String, Integer>();
	private HashMap<Integer,String> mapIGnDepartamento=new HashMap<Integer,String>();
	private String cmbvaluedepartamento;
	
	private HtmlComboBox cmbProvincia;
	private List<SelectItem> lstprovincia = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapGnProvincia=new HashMap<String, Integer>();
	private HashMap<Integer,String> mapIGnProvincia=new HashMap<Integer,String>();
	private String cmbvalueprovincia;
	
	private HtmlComboBox cmbDistrito;
	private List<SelectItem> lstdistrito = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapGnDistrito=new HashMap<String, Integer>();
	private HashMap<Integer,String> mapIGnDistrito=new HashMap<Integer,String>();
	private String cmbvaluedistrito;
	
	private HtmlComboBox cmbtipovia;
	private List<SelectItem> lsttipovia=new ArrayList<SelectItem>();
	private HashMap<Integer,String> mapIGnTipoVia=new HashMap<Integer,String>();
	private HashMap<String,Integer> mapGnTipoVia=new HashMap<String,Integer>();
	
	private Integer departamentoId=Constante.DEPARTAMENTO_ID_DEFECTO;
	private Integer provinciaId=Constante.PROVINCIA_ID_DEFECTO;
	private Integer distritoId=Constante.DISTRITO_ID_DEFECTO;
	private Integer departamentoProvinciaDistrito=Constante.DEPART_PROVIN_DISTR_DEFECTO;//por defecto
	
	private String cmbvalueregistroTipoDomicilio;
	private Integer registroTipoDomicilio=Constante.REGISTRAR_DIRECCION_FISCAL;//se activa cuando se registra los 3 domicilios 
	
	private MpDireccion mpDireccion;
	private MpPersonaDomicilio mpPersonaDomicilio;
	private UbicacionDTO ubicacionDTO;
	
	private Boolean selEdificio=Boolean.FALSE;
	private Boolean selInterior=Boolean.FALSE;
	private Boolean selIngreso=Boolean.FALSE;
	private Boolean selAgrupamiento=Boolean.FALSE;
	
	private Boolean isActualizar=Boolean.FALSE;
	
	private Boolean registrodomicilios=Boolean.FALSE;
	private FindMpDireccion findMpDireccionItem;
		

	
	public RegistroDireccionPersonaManaged(){
		
	}
	
	@PostConstruct
	public void init(){
		try{
			mpDireccion=new MpDireccion();
			setCmbvaluedepartamento(Constante.DEPARTAMENTO);
			setCmbvalueprovincia(Constante.PROVINCIA);
			setCmbvaluedistrito(Constante.DISTRITO);
			
			ubigeo=new Ubigeo();
			ubigeo.setDepartamento(Constante.DEPARTAMENTO);
			ubigeo.setDistrito(Constante.PROVINCIA);
			ubigeo.setProvincia(Constante.DISTRITO);
			
			mpPersonaDomicilio= new MpPersonaDomicilio();
			
			ubicacionDTO=new UbicacionDTO();
			//GnDepartamento
	        List<GnDepartamento> lstGnDepartamento=personaBo.getAllGnDepartamento();
			
			Iterator<GnDepartamento> itd = lstGnDepartamento.iterator();  
			lstdepartamento = new ArrayList<SelectItem>();
			 
	        while (itd.hasNext()){  
	        	GnDepartamento obj = itd.next();  
	        	lstdepartamento.add(new SelectItem(obj.getDescripcion(),String.valueOf(obj.getDptoId())));
	        	mapGnDepartamento.put(obj.getDescripcion(), obj.getDptoId());
	        	mapIGnDepartamento.put(obj.getDptoId(),obj.getDescripcion());
	        }
			
			//GnTipoEdificacion
	        List<GnTipoEdificacion> lstGnTipoEdificacion=personaBo.getAllGnTipoEdificacion();
			
			Iterator<GnTipoEdificacion> it = lstGnTipoEdificacion.iterator();  
			lsttipoedificio = new ArrayList<SelectItem>();
			 
	        while (it.hasNext()){  
	        	GnTipoEdificacion obj = it.next();  
	        	lsttipoedificio.add(new SelectItem(obj.getDescripcion(),String.valueOf(obj.getTipoEdificacionId())));
	        	mapGnTipoEdificacion.put(obj.getDescripcion(), obj.getTipoEdificacionId());
	        	mapIGnTipoEdificacion.put(obj.getTipoEdificacionId(),obj.getDescripcion());
	        }
	        
	        //GnTipoIngreso
	        List<GnTipoIngreso> lstGnTipoIngreso=personaBo.getAllGnTipoIngreso();
			
	        Iterator<GnTipoIngreso> it2 = lstGnTipoIngreso.iterator();  
			lsttipoingreso = new ArrayList<SelectItem>();
			 
	        while (it2.hasNext()){  
	            GnTipoIngreso obj = it2.next();  
	            lsttipoingreso.add(new SelectItem(obj.getDescripcion(),String.valueOf(obj.getTipoIngresoId())));
	            mapGnTipoIngreso.put(obj.getDescripcion(), obj.getTipoIngresoId());
	            mapIGnTipoIngreso.put(obj.getTipoIngresoId(),obj.getDescripcion());
	        }
	        //GnTipoInterior
	        List<GnTipoInterior> lstGnTipoInterior=personaBo.getAllGnTipoInterior();
			
			Iterator<GnTipoInterior> it3 = lstGnTipoInterior.iterator();  
			lsttipointerior = new ArrayList<SelectItem>();
			 
	        while (it3.hasNext()){  
	            GnTipoInterior obj = it3.next();  
	            lsttipointerior.add(new SelectItem(obj.getDescripcion(),String.valueOf(obj.getTipoInteriorId())));
	            mapGnTipoInterior.put(obj.getDescripcion(), obj.getTipoInteriorId());
	            mapIGnTipoInterior.put(obj.getTipoInteriorId(),obj.getDescripcion());
	        }
	        
	        //GnTipoVia
			List<GnTipoVia> lstGnTipoVia=personaBo.getAllGnTipoVia();
			Iterator<GnTipoVia> ittv = lstGnTipoVia.iterator();  
			while (ittv.hasNext()){
	        	GnTipoVia obj = ittv.next();  
	        	getLsttipovia().add(new SelectItem(obj.getDescripcion(),String.valueOf(obj.getTipoViaId())));
	            mapIGnTipoVia.put(obj.getTipoViaId(),obj.getDescripcion());
	            mapGnTipoVia.put(obj.getDescripcion(), obj.getTipoViaId());
	        }
	        
	        //getCmbtipoedificio().setValue("--");
	        setCmbvaluetipoedificio("--");
	        setSelEdificio(Boolean.FALSE);
	      	
			//getCmbtipoingreso().setValue("--");
			setCmbvaluetipoingreso("--");
			setSelIngreso(Boolean.FALSE);
			
			//getCmbtipointerior().setValue("--");
			setCmbvaluetipointerior("--");
			setSelInterior(Boolean.FALSE);
			
			/*EDITAR*/
			RegistroPersonaManaged registroPersonaManaged = (RegistroPersonaManaged) getManaged("registroPersonaManaged");

			if(registroPersonaManaged!=null){
				registrodomicilios=registroPersonaManaged.getIsDomiciliosIngresados();
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}

	public void viewcmbGnProvincia(){
		try{
			//GnProvincia
	        List<GnProvincia> lstGnProvincia=personaBo.getAllGnProvincia(departamentoId);
			Iterator<GnProvincia> it = lstGnProvincia.iterator();  
			lstprovincia=new ArrayList<SelectItem>();
			 
	        while (it.hasNext()){
	        	GnProvincia obj = it.next();  
	        	lstprovincia.add(new SelectItem(obj.getDescripcion(),String.valueOf(obj.getId().getProvinciaId())));  
	        	mapGnProvincia.put(obj.getDescripcion().trim(), obj.getId().getProvinciaId());
	        	mapIGnProvincia.put(obj.getId().getProvinciaId(),obj.getDescripcion());
	        }
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	public void viewcmbGnDistrito(){
		try{
			//GnDistrito
		    List<GnDistrito> lstGnDistrito=personaBo.getAllGnDistrito(departamentoId, provinciaId);
			Iterator<GnDistrito> it = lstGnDistrito.iterator();  
			lstdistrito=new ArrayList<SelectItem>();
			 
	        while (it.hasNext()){
	        	GnDistrito obj = it.next();  
	        	lstdistrito.add(new SelectItem(obj.getDescripcion(),String.valueOf(obj.getId().getDistritoId())));  
	        	mapGnDistrito.put(obj.getDescripcion().trim(), obj.getId().getDistritoId());
	        	mapIGnDistrito.put(obj.getId().getProvinciaId(),obj.getDescripcion().trim());
	        }
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	public void loadTipoDireccionById(ValueChangeEvent event) {
		try {
			HtmlComboBox combo = (HtmlComboBox) event.getComponent();
			String value = combo.getValue().toString();
			if (value != null) {
				RegistroPersonaManaged registroPersonaManaged = (RegistroPersonaManaged) getManaged("registroPersonaManaged");
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
		
	public void loadDepartamentoById(ValueChangeEvent event) {
		try{
			   HtmlComboBox combo=(HtmlComboBox) event.getComponent(); 
			    String value=combo.getValue().toString();
				if(value!=null){
				departamentoId=(Integer)mapGnDepartamento.get(value);
				cmbvalueprovincia="";
				cmbvaluedistrito="";
				if(departamentoId!=null){
					//lMpsubtipoPersona=personaBo.finMpSubtipoPersona(tipoPersonaId);	
				}
		
			}
			viewcmbGnProvincia();
		
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	public void loadProvinciaById(ValueChangeEvent event) {
		try{
			   HtmlComboBox combo=(HtmlComboBox) event.getComponent(); 
			    String value=combo.getValue().toString();
				if(value!=null){
				provinciaId=(Integer)mapGnProvincia.get(value);
				cmbvaluedistrito="";
				if(provinciaId!=null){
					//lMpsubtipoPersona=personaBo.finMpSubtipoPersona(tipoPersonaId);	
				}
		
			}
			viewcmbGnDistrito();
		
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	public void loadDistritoById(ValueChangeEvent event) {
		 HtmlComboBox combo=(HtmlComboBox) event.getComponent(); 
		    String value=combo.getValue().toString();
		if(cmbvaluedepartamento.compareTo(Constante.DEPARTAMENTO)==0 && cmbvalueprovincia.compareTo(Constante.PROVINCIA)==0 && value.compareTo(Constante.DISTRITO)==0){
			departamentoProvinciaDistrito=Constante.DEPART_PROVIN_DISTR_DEFECTO;
		}
		else{
			departamentoProvinciaDistrito=Constante.DEPART_PROVIN_DISTR_DISTINTO;
		}
	}
		
	public void verMpDireccionPersona()throws Exception{
		try{
			mpDireccion = new MpDireccion();
			mpDireccion = personaBo.finMpDireccion(findMpDireccionItem.getDireccionId());
			ubicacionDTO.setTipoVia(findMpDireccionItem.getTipoVia());
			ubicacionDTO.setViaid(findMpDireccionItem.getViaId());
			ubicacionDTO.setVia(findMpDireccionItem.getVia());
			
		    setCmbvaluetipoedificio(findMpDireccionItem.getTipoEdificacion());
		    setCmbvaluetipointerior(findMpDireccionItem.getTipoInterior());
		    setCmbvaluetipoingreso(findMpDireccionItem.getTipoIngreso());
		    
		    setIsActualizar(null);
		    RegistroPersonaManaged registroPersonaManaged = (RegistroPersonaManaged) getManaged("registroPersonaManaged");
			if(findMpDireccionItem.getTipoDomicilio().compareTo(Constante.DIRECCION_FISCAL)==0){
		        registroPersonaManaged.setTipoDomicilio(Constante.DIRECCION_FISCAL);
			}
		
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		
		
	}
	
	
	
	public void guardarPersonaDomicilio(String TipoDomicilio)throws Exception{
		try{
		//	mpPersonaDomicilio.setDomicilio_persona_id(generalBo.ObtenerCorrelativoTabla("mp_persona_domicilio", 1));	
			mpPersonaDomicilio.setDireccionId(getMpDireccion().getDireccionId());
			mpPersonaDomicilio.setDomicilioCompleto(getMpDireccion().getDireccionCompleta());
			mpPersonaDomicilio.setPersonaId(getMpDireccion().getPersonaId());
			mpPersonaDomicilio.setEstado(Constante.ESTADO_PENDIENTE);
			if(TipoDomicilio.compareTo(Constante.DIRECCION_FISCAL)==0){
				mpPersonaDomicilio.setFlagFiscal(Constante.FLAG_ACTIVO);
				mpPersonaDomicilio.setFlagProcesal(Constante.FLAG_INACTIVO);
				mpPersonaDomicilio.setFlagReal(Constante.FLAG_INACTIVO);
				mpPersonaDomicilio.setTipoDomicilio(Constante.DIRECCION_FISCAL);
			}
		
			//cc: personaBo.guardarMpPersonadireccion(mpPersonaDomicilio);
			
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	public boolean validarDatosMpPersona()throws Exception{
		try{ 
			if(departamentoProvinciaDistrito==1){
				if(ubicacionDTO.getTipoVia()==null || ubicacionDTO.getTipoVia().compareTo("")==0){
					addErrorMessage(getMsg("mp.errortipovia"));
				}
				else if(ubicacionDTO.getVia()==null || ubicacionDTO.getVia().compareTo("")==0){
					addErrorMessage(getMsg("mp.errornombrevia"));
				}
			}
			if(departamentoProvinciaDistrito==0){
				if(mpDireccion.getTipoVia()==null || mpDireccion.getTipoVia().compareTo("")==0){
					addErrorMessage(getMsg("mp.errortipovia"));
				}
				else if(mpDireccion.getNombreVia()==null || mpDireccion.getNombreVia().compareTo("")==0){
					addErrorMessage(getMsg("mp.errornombrevia"));
				}
			}
			else if(mpDireccion.getNumero()==null){
				addErrorMessage(getMsg("mp.errornumero1"));
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
		return true;
	}
	
	/**
	 * Registro de Nuevo MpDireccion : inicio
	 * @throws Exception
	 */
	/*
	 * Al hacer click en el boton Nuevo de la pantalla registropersonabasico.xhtml
	 */
	
	public void nuevo()throws Exception{
		try{
			limpiar();
		    setIsActualizar(false);
		    
		    RegistroPersonaManaged registroPersonaManaged = (RegistroPersonaManaged) getManaged("registroPersonaManaged");
			if(registroPersonaManaged!=null){
				registrodomicilios=registroPersonaManaged.getIsDomiciliosIngresados();
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	public void editMpDireccionPersona()throws Exception{
		try{
			mpDireccion = new MpDireccion();
			mpPersonaDomicilio=new MpPersonaDomicilio();
			ubicacionDTO=new UbicacionDTO();
			
			ubigeo= new Ubigeo();
			ubigeo.setDepartamento(findMpDireccionItem.getDepartamento());
			ubigeo.setProvincia(findMpDireccionItem.getProvincia());
			ubigeo.setDistrito(findMpDireccionItem.getDistrito());
			departamentoId= findMpDireccionItem.getDepartamentoId();
			provinciaId= findMpDireccionItem.getProvinciaId();
			distritoId= findMpDireccionItem.getDistritoId();
			
			RegistroPersonaManaged registroPersonaManaged = (RegistroPersonaManaged) getManaged("registroPersonaManaged");
			mpDireccion=registroPersonaManaged.getMpDireccion();
			mpDireccion.setUbigeo(ubigeo);
			mpPersonaDomicilio=registroPersonaManaged.getMpPersonaDomicilio();
			
			ubicacionDTO=registroPersonaManaged.getUbicacionDTO();
			
		    if(findMpDireccionItem.getTipoEdificacionId()!=null&&findMpDireccionItem.getTipoEdificacionId()>0){
		    	setCmbvaluetipoedificio(mapIGnTipoEdificacion.get(findMpDireccionItem.getTipoEdificacionId()));
		    }
		    if(findMpDireccionItem.getTipoInteriorId()!=null&&findMpDireccionItem.getTipoInteriorId()>0)
		    	setCmbvaluetipointerior(mapIGnTipoInterior.get(findMpDireccionItem.getTipoInteriorId()));
		    if(findMpDireccionItem.getTipoIngresoId()!=null&&findMpDireccionItem.getTipoIngresoId()>0)
		    	setCmbvaluetipoingreso(mapIGnTipoIngreso.get(findMpDireccionItem.getTipoIngresoId()));
		    setIsActualizar(Boolean.TRUE);
		    
			if(findMpDireccionItem.getTipoDomicilio().compareTo(Constante.DIRECCION_FISCAL)==0){
		        registroPersonaManaged.setTipoDomicilio(Constante.DIRECCION_FISCAL);
		        registroTipoDomicilio=Constante.REGISTRAR_DIRECCION_FISCAL;
		        registroPersonaManaged.setRegistroDomicilio(registroTipoDomicilio);
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	/*
	 * Al hacer click en el boton Grabar Fiscal en la pantalla registrodireccion.xhtml
	 */
	public void guardarPersonaOtrosDomicilio()throws Exception{
		try{
			if(validar(ubicacionDTO.getNumeroCuadra(),ubicacionDTO.getLado())){	
				guardar();
				if(registrodomicilios==false){
					RegistroPersonaManaged registroPersonaManaged = (RegistroPersonaManaged) getManaged("registroPersonaManaged");
					String tipoDomicilioTemp= registroPersonaManaged.getTipoDomicilio(); 
				 	if(tipoDomicilioTemp.compareTo(Constante.DIRECCION_FISCAL)==0 && registroPersonaManaged.getRegistroDomicilio()==0){
				 		registrodomicilios=true;
				 		registroPersonaManaged.setIsDomiciliosIngresados(registrodomicilios);
				 	}
				}
				limpiar();
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public void guardar()throws Exception{
		try{
			ubigeo=new Ubigeo();
			ubigeo.setDepartamento(cmbvaluedepartamento);
			ubigeo.setDistrito(cmbvaluedistrito);
			ubigeo.setProvincia(cmbvalueprovincia);
			RegistroPersonaManaged registroPersonaManaged = (RegistroPersonaManaged) getManaged("registroPersonaManaged");
			getMpDireccion().setPersonaId(registroPersonaManaged.getMpPersona().getPersonaId());
			
			if(mpDireccion.getPersonaId()==null){
				Object obj = getSessionMap().get("nuevoPersona");
				if (obj != null) {
					MpPersona mpPersonaTemp = (MpPersona)obj;
					mpDireccion.setPersonaId(mpPersonaTemp.getPersonaId());
				}
			}
			
			getMpDireccion().setDptoId(departamentoId);
			getMpDireccion().setProvinciaId(provinciaId);
			getMpDireccion().setDistritoId(distritoId);
			
			//cc: el correlativo se setea en el Bo
			//Integer NextMpDireccionId=generalBo.ObtenerCorrelativoTabla("MP_DIRECCION", 1);
			//if(NextMpDireccionId!=null){
			//getMpDireccion().setDireccionId(NextMpDireccionId);
			getMpDireccion().setTipoDireccion(Constante.TIPO_PREDIO_URBANO);
			
			String tipoedificio=(String)getCmbtipoedificio().getValue();
			String tipointerior=(String)getCmbtipointerior().getValue();
			String tipoingreso=(String)getCmbtipoingreso().getValue();
			
			Integer tipoEdificacionId=mapGnTipoEdificacion.get(tipoedificio);
			Integer tipoInteriorId=mapGnTipoInterior.get(tipointerior);
			Integer tipoIngresoId=mapGnTipoIngreso.get(tipoingreso);
			
			if(tipoEdificacionId!=null){
				getMpDireccion().setTipoEdificacionId(tipoEdificacionId);
			}
			
			if(tipoInteriorId!=null)
				getMpDireccion().setTipoInteriorId(tipoInteriorId);
			if(tipoIngresoId!=null)
				getMpDireccion().setTipoIngresoId(tipoIngresoId);
				
			if(ubicacionDTO.getViaid()!=null){
				GnVia via=personaBo.getGnVia(ubicacionDTO.getViaid());
				getMpDireccion().setViaId(ubicacionDTO.getViaid());
				getMpDireccion().setVia(via);
			}
			
			if(ubicacionDTO.getUbicacionId()!=null){
				getMpDireccion().setUbicacionId(ubicacionDTO.getUbicacionId());
				getMpDireccion().setSector(ubicacionDTO.getSector());
				getMpDireccion().setLugar(ubicacionDTO.getLugar());
					
				//getMpDireccion().set
//				ubicacionDTO.getLado();
//				ubicacionDTO.getCodigoCatastral();
//				ubicacionDTO.getNumeroCuadra();
//				ubicacionDTO.getNumeroManzana();
				//ubicacionDTO.getSector();				
			}
			
			getMpDireccion().setUbigeo(ubigeo);
			getMpDireccion().setEstado(Constante.ESTADO_PENDIENTE);
			
			String direccionCompleta=personaBo.ConcatenarMpDireccion(getMpDireccion(), mapIGnTipoEdificacion, mapIGnTipoInterior, mapIGnTipoIngreso, mapIGnTipoVia,ubicacionDTO.getNumeroCuadra(),ubicacionDTO.getSector());
			getMpDireccion().setDireccionCompleta(direccionCompleta);
			
			//cc: int resul=personaBo.guardarMpdireccion(getMpDireccion());
			registroPersonaManaged.setMpDireccion(getMpDireccion());
			registroPersonaManaged.setUbicacionDTO(getUbicacionDTO());
			
			guardarPersonaDomicilio(registroPersonaManaged.getTipoDomicilio());
			registroPersonaManaged.setMpPersonaDomicilio(getMpPersonaDomicilio());
			
			registroPersonaManaged.loadDirecciones();
			//}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	/**
	 * Registro de Nuevo MpDireccion : fin
	 * @throws Exception
	 */
	
	public boolean validaCuadra(String valorCampo,Integer numeroCuadra,Integer ladoCuadra){
		try{
			if(valorCampo!=null){
				if(valorCampo.trim().toUpperCase().equals("S/N")){
					return true;
				}else if(numeroCuadra!=null&&numeroCuadra>0){
					if(Util.toInteger(valorCampo)>0){
						if(valorCampo.trim().length()>String.valueOf(numeroCuadra).length()){
							String cuadra=valorCampo.substring(0, String.valueOf(numeroCuadra).length());
							String numero=valorCampo.substring(String.valueOf(numeroCuadra).length(),valorCampo.length());
							
							if(Util.toInteger(cuadra)==numeroCuadra){
								if(ladoCuadra==2&&esImpar(Util.toInteger(numero))||//Lado Izquierdo, numeros impares
										ladoCuadra==1&&!esImpar(Util.toInteger(numero))){//Lado Derecho, numeros pares
									return true;
								}else{
									WebMessages.messageError("El campo Numero indicado no corresponde con el Lado de la cuadra "+(ladoCuadra==2?" Izquierda":" Derecha"));
								}
							}else{
								WebMessages.messageError("El campo Numero indicado no corresponde con el Numero de Cuadra "+numeroCuadra);
							}
						}else{
							WebMessages.messageError("El campo Numero indicado no corresponde con el Numero de Cuadra "+numeroCuadra);
						}
					}else{
						WebMessages.messageError("El campo Numero 1 indicado debe ser un numero correspondiente con el Numero de Cuadra "+numeroCuadra+" o S/N");
					}
				}else{
					WebMessages.messageError("No se ha definido el Numero de Cuadra de la Ubicación indicada");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
 	}
	
	public boolean esImpar(int iNumero) {
		  if (iNumero%2!=0)
		    return true;
		  else
		    return false;
	}
	
	public boolean validar(Integer numeroCuadra,Integer ladoCuadra){
		boolean validation=false;
//		if(getCmbvaluetipoedificio().compareTo("--")!=0 ){
//			if(mpDireccion.getPiso()!=null &&  mpDireccion.getPiso().trim().length()==0){
//				addErrorMessage(getMsg("mp.errorpiso"));
//				return false;
//			}
//			if(mpDireccion.getNombreEdificiacion()!=null &&  mpDireccion.getNombreEdificiacion().trim().length()==0){
//				addErrorMessage(getMsg("mp.errornombreedificio"));
//				return false;
//			}
//		}
//		if(getCmbvaluetipointerior().compareTo("--")!=0 ){
//			if(mpDireccion.getNombreInterior()!=null &&  mpDireccion.getNombreInterior().trim().length()==0){
//				addErrorMessage(getMsg("mp.errornumerointerior"));
//				return false;
//			}
			//No es requerido 
//			if(mpDireccion.getLetraInterior()!=null &&  mpDireccion.getLetraInterior().trim().length()==0){
//				addErrorMessage(getMsg("mp.errorletrainterior"));
//				return false;
//			}
//		}
//		if(getCmbvaluetipoingreso().compareTo("--")!=0 ){
//			if(mpDireccion.getNombreIngreso()!=null &&  mpDireccion.getNombreIngreso().trim().length()==0){
//				addErrorMessage(getMsg("mp.errornumeroingreso"));
//				return false;
//			}
//		}
		if(!isActualizar){
			if(registrodomicilios){
				if(cmbvalueregistroTipoDomicilio==null || cmbvalueregistroTipoDomicilio.compareTo("")==0){
					addErrorMessage(getMsg("mp.errortipodomicilio"));
				return false;
				}
			}
		}
		
		departamentoProvinciaDistrito = Constante.DEPART_PROVIN_DISTR_DEFECTO;
		   
		if(departamentoProvinciaDistrito == Constante.DEPART_PROVIN_DISTR_DEFECTO){
			if(ubicacionDTO.getViaid()!=null){  // Ojo Se ha quitado la validacion con getViaId = 0 caso Otros -> Otros
				if(getMpDireccion().getNumero()!=null&&getMpDireccion().getNumero().trim().length()>0){
					validation=validaCuadra(getMpDireccion().getNumero(),numeroCuadra,ladoCuadra);
					if(!validation)
						return false;
				}else{
					addErrorMessage(getMsg("mp.errornumero1"));
					return false;
				}
			}else{
				addErrorMessage(getMsg("mp.errornombrevia"));
			}
		}
		else if(departamentoProvinciaDistrito == Constante.DEPART_PROVIN_DISTR_DISTINTO){
			if(getMpDireccion().getTipoVia()!=null && getMpDireccion().getNombreVia()!=null){
				if(getMpDireccion().getNumero()!=null&&getMpDireccion().getNumero().trim().length()>0){
					validation=true;	
				}
			}else{
				addErrorMessage(getMsg("mp.errornumero1"));
			}
		}
		
		return validation;
	}
	
	public void setSelectedVia(UbicacionDTO currentItem){
		setUbicacionDTO(currentItem);
	}
	
	public void salir(){
		limpiar();
	}
	
	public void limpiar(){
		mpDireccion=new MpDireccion();
		mpDireccion.setDireccionId(Constante.RESULT_PENDING);
		
		mpPersonaDomicilio=new MpPersonaDomicilio();
		mpPersonaDomicilio.setDomicilio_persona_id(Constante.RESULT_PENDING);
		mpPersonaDomicilio.setDomiProcesal(false);
		mpPersonaDomicilio.setDomiReal(false);
		
		ubicacionDTO=new UbicacionDTO();
		
		isActualizar=Boolean.FALSE;
		setCmbvalueregistroTipoDomicilio("");
		setCmbvaluetipoedificio("--");
		setCmbtipoedificio(null);
		setSelEdificio(Boolean.FALSE);
		   
		setCmbvaluetipoingreso("--");
		setCmbtipoingreso(null);
		setSelIngreso(Boolean.FALSE);
		
		setCmbvaluetipointerior("--");
		setCmbtipointerior(null);
		setSelInterior(Boolean.FALSE);
	}
	
	public void selectTipoEdificacion(ValueChangeEvent event)//ok
	{
		 HtmlComboBox combo=(HtmlComboBox) event.getComponent(); 
		 
		 Integer tipoEdificacionId=mapGnTipoEdificacion.get(combo.getValue());
		 
		 if(!combo.getValue().toString().equalsIgnoreCase("--") && tipoEdificacionId != null && tipoEdificacionId>0){
			 setSelEdificio(Boolean.TRUE);			
		 }
		 else{
			 setSelEdificio(Boolean.FALSE);
			 setCmbvaluetipoedificio("--");
			 getCmbtipoedificio().setValue(null);
			 mpDireccion.setTipoEdificacionId(null);
			 mpDireccion.setNombreEdificiacion(null);
			 mpDireccion.setPiso(null);			 
		 }
	}
		
	public void selectTipoIngreso(ValueChangeEvent event)//ok
	{
		 HtmlComboBox combo=(HtmlComboBox) event.getComponent(); 
		 if(!combo.getValue().toString().equalsIgnoreCase("--")){
			 setSelIngreso(Boolean.TRUE);			 
		 }
		 else{
			 setSelIngreso(Boolean.FALSE);			 
			 setCmbvaluetipoingreso("--");			 
			 getCmbtipoingreso().setValue(null);
			 mpDireccion.setTipoIngresoId(null);
			 mpDireccion.setNombreIngreso(null);
		 }
	}
	
	public void selectTipoInterior(ValueChangeEvent event)//ok
	{
		 HtmlComboBox combo=(HtmlComboBox) event.getComponent(); 
		 
		 if(!combo.getValue().toString().equalsIgnoreCase("--")){
			 setSelInterior(Boolean.TRUE);			 
		 }
		 else{
			 setSelInterior(Boolean.FALSE);
			 setCmbvaluetipointerior("--");
			 getCmbtipoingreso().setValue(null);
			 mpDireccion.setTipoInteriorId(null);
			 mpDireccion.setNumeroInterior(null);
			 mpDireccion.setLetraInterior(null);
		 }
	}
	
	public Ubigeo getUbigeo() {
		return ubigeo;
	}

	public void setUbigeo(Ubigeo ubigeo) {
		this.ubigeo = ubigeo;
	}
	
	public List<SelectItem> getLsttipoedificio() {
		return lsttipoedificio;
	}

	public void setLsttipoedificio(List<SelectItem> lsttipoedificio) {
		this.lsttipoedificio = lsttipoedificio;
	}

	public List<SelectItem> getLsttipointerior() {
		return lsttipointerior;
	}

	public void setLsttipointerior(List<SelectItem> lsttipointerior) {
		this.lsttipointerior = lsttipointerior;
	}

	public List<SelectItem> getLsttipoingreso() {
		return lsttipoingreso;
	}

	public void setLsttipoingreso(List<SelectItem> lsttipoingreso) {
		this.lsttipoingreso = lsttipoingreso;
	}
	
	public HtmlComboBox getCmbtipointerior() {
		return cmbtipointerior;
	}

	public void setCmbtipointerior(HtmlComboBox cmbtipointerior) {
		this.cmbtipointerior = cmbtipointerior;
	}
	
	public HtmlComboBox getCmbtipoedificio() {
		return cmbtipoedificio;
	}

	public void setCmbtipoedificio(HtmlComboBox cmbtipoedificio) {
		this.cmbtipoedificio = cmbtipoedificio;
	}

	public HtmlComboBox getCmbtipoingreso() {
		return cmbtipoingreso;
	}

	public void setCmbtipoingreso(HtmlComboBox cmbtipoingreso) {
		this.cmbtipoingreso = cmbtipoingreso;
	}

	public String getCmbvaluetipoedificio() {
		return cmbvaluetipoedificio;
	}

	public void setCmbvaluetipoedificio(String cmbvaluetipoedificio) {
		this.cmbvaluetipoedificio = cmbvaluetipoedificio;
	}

	public String getCmbvaluetipointerior() {
		return cmbvaluetipointerior;
	}

	public void setCmbvaluetipointerior(String cmbvaluetipointerior) {
		this.cmbvaluetipointerior = cmbvaluetipointerior;
	}

	public String getCmbvaluetipoingreso() {
		return cmbvaluetipoingreso;
	}

	public void setCmbvaluetipoingreso(String cmbvaluetipoingreso) {
		this.cmbvaluetipoingreso = cmbvaluetipoingreso;
	}

	public Boolean getSelEdificio() {
		return selEdificio;
	}
//solo el condomino simple tiene  porcenjate
	public void setSelEdificio(Boolean selEdificio) {
		this.selEdificio = selEdificio;
	}

	public Boolean getSelInterior() {
		return selInterior;
	}

	public void setSelInterior(Boolean selInterior) {
		this.selInterior = selInterior;
	}

	public Boolean getSelIngreso() {
		return selIngreso;
	}

	public void setSelIngreso(Boolean selIngreso) {
		this.selIngreso = selIngreso;
	}

	public Boolean getSelAgrupamiento() {
		return selAgrupamiento;
	}

	public void setSelAgrupamiento(Boolean selAgrupamiento) {
		this.selAgrupamiento = selAgrupamiento;
	}
	public String getDireccionPersona() {
		return direccionPersona;
	}
	public void setDireccionPersona(String direccionPersona) {
		this.direccionPersona = direccionPersona;
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
	
	public HtmlComboBox getCmbDepartamento() {
		return cmbDepartamento;
	}
	public void setCmbDepartamento(HtmlComboBox cmbDepartamento) {
		this.cmbDepartamento = cmbDepartamento;
	}
	public List<SelectItem> getLstdepartamento() {
		return lstdepartamento;
	}
	public void setLstdepartamento(List<SelectItem> lstdepartamento) {
		this.lstdepartamento = lstdepartamento;
	}
	public HashMap<String, Integer> getMapGnDepartamento() {
		return mapGnDepartamento;
	}
	public void setMapGnDepartamento(HashMap<String, Integer> mapGnDepartamento) {
		this.mapGnDepartamento = mapGnDepartamento;
	}
	public HashMap<Integer,String> getMapIGnDepartamento() {
		return mapIGnDepartamento;
	}
	public void setMapIGnDepartamento(HashMap<Integer,String> mapIGnDepartamento) {
		this.mapIGnDepartamento = mapIGnDepartamento;
	}
	public String getCmbvaluedepartamento() {
		return cmbvaluedepartamento;
	}
	public void setCmbvaluedepartamento(String cmbvaluedepartamento) {
		this.cmbvaluedepartamento = cmbvaluedepartamento;
	}
	public HtmlComboBox getCmbProvincia() {
		return cmbProvincia;
	}
	public void setCmbProvincia(HtmlComboBox cmbProvincia) {
		this.cmbProvincia = cmbProvincia;
	}
	public List<SelectItem> getLstprovincia() {
		return lstprovincia;
	}
	public void setLstprovincia(List<SelectItem> lstprovincia) {
		this.lstprovincia = lstprovincia;
	}
	public HashMap<String, Integer> getMapGnProvincia() {
		return mapGnProvincia;
	}
	public void setMapGnProvincia(HashMap<String, Integer> mapGnProvincia) {
		this.mapGnProvincia = mapGnProvincia;
	}
	public HashMap<Integer,String> getMapIGnProvincia() {
		return mapIGnProvincia;
	}
	public void setMapIGnProvincia(HashMap<Integer,String> mapIGnProvincia) {
		this.mapIGnProvincia = mapIGnProvincia;
	}
	public String getCmbvalueprovincia() {
		return cmbvalueprovincia;
	}
	public void setCmbvalueprovincia(String cmbvalueprovincia) {
		this.cmbvalueprovincia = cmbvalueprovincia;
	}
	public HtmlComboBox getCmbDistrito() {
		return cmbDistrito;
	}
	public void setCmbDistrito(HtmlComboBox cmbDistrito) {
		this.cmbDistrito = cmbDistrito;
	}
	public List<SelectItem> getLstdistrito() {
		return lstdistrito;
	}
	public void setLstdistrito(List<SelectItem> lstdistrito) {
		this.lstdistrito = lstdistrito;
	}
	public HashMap<String, Integer> getMapGnDistrito() {
		return mapGnDistrito;
	}
	public void setMapGnDistrito(HashMap<String, Integer> mapGnDistrito) {
		this.mapGnDistrito = mapGnDistrito;
	}
	public HashMap<Integer,String> getMapIGnDistrito() {
		return mapIGnDistrito;
	}
	public void setMapIGnDistrito(HashMap<Integer,String> mapIGnDistrito) {
		this.mapIGnDistrito = mapIGnDistrito;
	}
	public String getCmbvaluedistrito() {
		return cmbvaluedistrito;
	}
	public void setCmbvaluedistrito(String cmbvaluedistrito) {
		this.cmbvaluedistrito = cmbvaluedistrito;
	}
	public Integer getDepartamentoId() {
		return departamentoId;
	}
	public void setDepartamentoId(Integer departamentoId) {
		this.departamentoId = departamentoId;
	}
	public Integer getProvinciaId() {
		return provinciaId;
	}
	public void setProvinciaId(Integer provinciaId) {
		this.provinciaId = provinciaId;
	}
	public Integer getDistritoId() {
		return distritoId;
	}
	public void setDistritoId(Integer distritoId) {
		this.distritoId = distritoId;
	}
	
	public Integer getDepartamentoProvinciaDistrito() {
		return departamentoProvinciaDistrito;
	}
	public void setDepartamentoProvinciaDistrito(
			Integer departamentoProvinciaDistrito) {
		this.departamentoProvinciaDistrito = departamentoProvinciaDistrito;
	}

	public Integer getRegistroTipoDomicilio() {
		return registroTipoDomicilio;
	}
	public void setRegistroTipoDomicilio(Integer registroTipoDomicilio) {
		this.registroTipoDomicilio = registroTipoDomicilio;
	}
	public String getCmbvalueregistroTipoDomicilio() {
		return cmbvalueregistroTipoDomicilio;
	}
	public void setCmbvalueregistroTipoDomicilio(
			String cmbvalueregistroTipoDomicilio) {
		this.cmbvalueregistroTipoDomicilio = cmbvalueregistroTipoDomicilio;
	}
	public Boolean getRegistrodomicilios() {
		return registrodomicilios;
	}
	public void setRegistrodomicilios(Boolean registrodomicilios) {
		this.registrodomicilios = registrodomicilios;
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
	
	public FindMpDireccion getFindMpDireccionItem() {
		return findMpDireccionItem;
	}

	public void setFindMpDireccionItem(FindMpDireccion findMpDireccionItem) {
		this.findMpDireccionItem = findMpDireccionItem;
	}

	public HashMap<String,Integer> getMapGnTipoVia() {
		return mapGnTipoVia;
	}

	public void setMapGnTipoVia(HashMap<String,Integer> mapGnTipoVia) {
		this.mapGnTipoVia = mapGnTipoVia;
	}

	public Boolean getIsActualizar() {
		return isActualizar;
	}

	public void setIsActualizar(Boolean isActualizar) {
		this.isActualizar = isActualizar;
	}

	public UbicacionDTO getUbicacionDTO() {
		return ubicacionDTO;
	}

	public void setUbicacionDTO(UbicacionDTO ubicacionDTO) {
		this.ubicacionDTO = ubicacionDTO;
	}	
}
