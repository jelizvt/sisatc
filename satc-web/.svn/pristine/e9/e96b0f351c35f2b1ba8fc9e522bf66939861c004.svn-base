package com.sat.sisat.persona.managed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import com.sat.sisat.common.security.UserSession;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.persistence.entity.GnCondicionEspecial;
import com.sat.sisat.persistence.entity.MpDireccion;
import com.sat.sisat.persistence.entity.MpPersona;
import com.sat.sisat.persistence.entity.MpRelacionado;
import com.sat.sisat.persistence.entity.MpTipoRelacion;
import com.sat.sisat.persona.business.PersonaBoRemote;
import com.sat.sisat.predial.dto.FindMpDireccion;
import com.sat.sisat.predial.dto.FindMpPersona;
@ManagedBean
@ViewScoped
public class VistaPreviaManaged extends BaseManaged {
	@EJB
	PersonaBoRemote personaBo;
	
	@Inject
	UserSession user;
	
	private MpPersona mpPersona= new MpPersona();
	private GnCondicionEspecial gnCondicionEspecial=new GnCondicionEspecial();
	
	private ArrayList<MpRelacionado> recordsRelacionado;
	private ArrayList<FindMpDireccion> recordsDireccion;
	
	private FindMpPersona findMpPersona;
	
	private String direccionFiscal;
	private String direccionProcesal;
	private String direccionReal;
	private String usuario;
	
	private boolean relacionados=false;
	private boolean personanaturaloConyugal=false;
	
	private HashMap<Integer,String> mapMpTipoRelacion=new HashMap<Integer,String>();
	
	public VistaPreviaManaged(){
		getSessionManaged().setLinkRegresar("/sisat/persona/buscarpersona.xhtml");
	}
	
	@PostConstruct
	public void init(){
		try{
			
			findMpPersona= (FindMpPersona)getSessionMap().get("findMpPersona1");
			if(findMpPersona==null){
				findMpPersona= (FindMpPersona)getSessionMap().get("nuevoPersona");
				if (findMpPersona != null) {
					getSessionMap().remove("nuevoPersona");
				}
			}else{
				if(findMpPersona.getTipoPersona().compareTo(Constante.TIPO_PERSONA_NATURAL)==0)
					personanaturaloConyugal=true;
				if(findMpPersona.getTipoPersona().compareTo(Constante.TIPO_PERSONA_SOC_CONYUGAL)==0)
					personanaturaloConyugal=true;
			}
			gnCondicionEspecial= new GnCondicionEspecial();
			Object obj1 = getSessionMap().get("condicionEspecialnuevoPersona");
			if (obj1 != null) {
				gnCondicionEspecial = (GnCondicionEspecial)obj1;
				getSessionMap().remove("condicionEspecialnuevoPersona");
			}
			
			List<MpTipoRelacion> lstMpTipoRelacion=personaBo.getAllMpTipoRelacion();
			Iterator<MpTipoRelacion> it3 = lstMpTipoRelacion.iterator();  
		    while (it3.hasNext()){
		    	MpTipoRelacion obj = it3.next();  
		    	mapMpTipoRelacion.put(obj.getTipoRelacionId(),obj.getDescripcion().trim());
		    }
		    
		    obtenerDatos();
		    
		    setUsuario(user.getUsuario());
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	@SuppressWarnings("unchecked")
	public void obtenerDatos(){
		try{
			findMpPersona= (FindMpPersona)getSessionMap().get("findMpPersona1");
			recordsRelacionado=(ArrayList<MpRelacionado>)getSessionMap().get("recordsRelacionado");
			if(recordsRelacionado==null)
			    recordsRelacionado=personaBo.findMpRelacionado(findMpPersona.getPersonaId());
			if(recordsRelacionado!=null&&recordsRelacionado.size()>0)
		    	relacionados=true;
		    recordsDireccion=(ArrayList<FindMpDireccion>)getSessionMap().get("recordsDireccion");
		    if(recordsDireccion==null)
		    	recordsDireccion=personaBo.finMpDireccionPersona(findMpPersona.getPersonaId());
			if(recordsDireccion!=null){
				for(int i=0;i<recordsDireccion.size();i++){
					if(recordsDireccion.get(i).getFlagFiscal()!=null){
					if(recordsDireccion.get(i).getFlagFiscal().compareTo(Constante.ESTADO_ACTIVO)==0)
						direccionFiscal=recordsDireccion.get(i).getDomicilioCompleto();
					}
					else
						if(recordsDireccion.get(i).getTipoDomicilio().compareTo(Constante.DIRECCION_FISCAL)==0){
							direccionFiscal=recordsDireccion.get(i).getDomicilioCompleto();
						}
				}
			}
			if((gnCondicionEspecial!=null && gnCondicionEspecial.getTipoCondEspecialId()==Constante.CONDICION_ESPECIAL_DEFECTO_ID) || gnCondicionEspecial==null){	
				findMpPersona.setTipoCondicionEspecial(Constante.CONDICION_ESPECIAL_DEFECTO);
			}
			
			if(findMpPersona.getSituacionEmpresarialId()!=null&&(findMpPersona.getSituacionEmpresarialId().compareTo(Constante.SITUACION_EMPRESARIAL_ID_DEFECTO)==0 || findMpPersona.getSituacionEmpresarialId().compareTo(Constante.SITUACION_EMPRESARIAL_ID_DEFECTO_PERJURID_PATRIAUTO)==0))
				findMpPersona.setSituacionEmpresarial(Constante.SITUACION_EMPRESARIAL_DEF_PERJURID_PATRIAUTO);
		  
			if(direccionFiscal ==null){
				MpDireccion direccion= (MpDireccion)getSessionMap().get("nuevoDireccion");
				if (direccion != null) {
					direccionFiscal=direccion.getDireccionCompleta();
					getSessionMap().remove("nuevoDireccion");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}

	public MpPersona getMpPersona() {
		return mpPersona;
	}

	public void setMpPersona(MpPersona mpPersona) {
		this.mpPersona = mpPersona;
	}

	public GnCondicionEspecial getGnCondicionEspecial() {
		return gnCondicionEspecial;
	}

	public void setGnCondicionEspecial(GnCondicionEspecial gnCondicionEspecial) {
		this.gnCondicionEspecial = gnCondicionEspecial;
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

	public String getDireccionFiscal() {
		return direccionFiscal;
	}

	public void setDireccionFiscal(String direccionFiscal) {
		this.direccionFiscal = direccionFiscal;
	}

	public String getDireccionProcesal() {
		return direccionProcesal;
	}

	public void setDireccionProcesal(String direccionProcesal) {
		this.direccionProcesal = direccionProcesal;
	}

	public String getDireccionReal() {
		return direccionReal;
	}

	public void setDireccionReal(String direccionReal) {
		this.direccionReal = direccionReal;
	}

	public boolean isRelacionados() {
		return relacionados;
	}

	public void setRelacionados(boolean relacionados) {
		this.relacionados = relacionados;
	}

	public boolean isPersonanaturaloConyugal() {
		return personanaturaloConyugal;
	}

	public void setPersonanaturaloConyugal(boolean personanaturaloConyugal) {
		this.personanaturaloConyugal = personanaturaloConyugal;
	}

	public FindMpPersona getFindMpPersona() {
		return findMpPersona;
	}

	public void setFindMpPersona(FindMpPersona findMpPersona) {
		this.findMpPersona = findMpPersona;
	}
	
	public HashMap<Integer, String> getMapMpTipoRelacion() {
		return mapMpTipoRelacion;
	}

	public void setMapMpTipoRelacion(HashMap<Integer, String> mapMpTipoRelacion) {
		this.mapMpTipoRelacion = mapMpTipoRelacion;
	}
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
}
