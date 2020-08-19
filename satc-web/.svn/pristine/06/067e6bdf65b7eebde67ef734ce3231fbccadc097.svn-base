package com.sat.sisat.predial.managed;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.FacesUtil;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.persistence.entity.RpDjdireccion;
import com.sat.sisat.persistence.entity.Ubigeo;
import com.sat.sisat.predial.business.RegistroPrediosBoRemote;

@ManagedBean
@ViewScoped
public class UbicacionPredioRusticoManaged extends BaseManaged {
	private String direccionPredio=""; 
	
	@EJB
	RegistroPrediosBoRemote registroPrediosBo;
	
	private Ubigeo ubigeo;
	
	private RpDjdireccion rpDjdireccion;
	private Integer annoDj;
	
	private Boolean disabled;
	
	public UbicacionPredioRusticoManaged(){
		
	}
	@PostConstruct
	public void init(){
		try{
			ubigeo=new Ubigeo();
			ubigeo.setDepartamento("Cajamarca");
			ubigeo.setDistrito("Cajamarca");
			ubigeo.setProvincia("Cajamarca");
			
			rpDjdireccion=new RpDjdireccion();
			String RECORD_STATUS=(String)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "RECORD_STATUS");
			if(RECORD_STATUS!=null&&RECORD_STATUS.equals(Constante.RECORD_STATUS_UPDATE)){
				Integer DjId=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId");
				if(DjId!=null){
					rpDjdireccion=registroPrediosBo.getRpDjDireccion(DjId);
				}
			}else{
				
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	public void guardar(){
		try{
			Integer NextDjId=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId");
			if(NextDjId==null||NextDjId==Constante.RESULT_PENDING){
				RegistroPredioManaged registroPredio = (RegistroPredioManaged) getManaged("registroPredioManaged");
				registroPredio.inscripcionPredio();
			}
			NextDjId=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId");
			if(NextDjId!=null){
				getRpDjdireccion().setDjId(NextDjId);
				getRpDjdireccion().setTipoDireccion(Constante.TIPO_PREDIO_RUSTICO);
				getRpDjdireccion().setEstado(Constante.ESTADO_ACTIVO);
				//cc: getRpDjdireccion().setFechaRegistro(DateUtil.getCurrentDate());
				//cc: getRpDjdireccion().setUsuarioId(Constante.USUARIO_ID);
				//cc: getRpDjdireccion().setTerminal(Constante.TERMINAL);
				getRpDjdireccion().setDireccionCompleta(getRpDjdireccion().getLugar());
				
				int resul=registroPrediosBo.guardarRpDjdireccion(getRpDjdireccion());
				if(resul>0){
					int direccionId=registroPrediosBo.getDjDireccionActive(NextDjId);
					getRpDjdireccion().setDireccionId(direccionId);
					getRpDjdireccion().setUbigeo(ubigeo);
					if(direccionId>0){
						registroPrediosBo.desactiveDirecciones(NextDjId,direccionId);
						RegistroPredioManaged registroPredio = (RegistroPredioManaged) getManaged("registroPredioManaged");
						registroPredio.setDireccionPredio(getRpDjdireccion().getLugar());
						registroPredio.getRpDjpredial().setDescDomicilio(getRpDjdireccion().getDireccionCompleta());
						registroPredio.loadUbicacion();
					}
				}else{
					//no se registro
				}
			}else{
				//no registrado DjID
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	public void salir(){
		limpiar();
	}
	
	public void limpiar(){
		rpDjdireccion=new RpDjdireccion();
	}
	
	public Ubigeo getUbigeo() {
		return ubigeo;
	}

	public void setUbigeo(Ubigeo ubigeo) {
		this.ubigeo = ubigeo;
	}
	
	
	public RpDjdireccion getRpDjdireccion() {
		return rpDjdireccion;
	}

	public void setRpDjdireccion(RpDjdireccion rpDjdireccion) {
		this.rpDjdireccion = rpDjdireccion;
	}
	
	public String getDireccionPredio() {
		return direccionPredio;
	}

	public void setDireccionPredio(String direccionPredio) {
		this.direccionPredio = direccionPredio;
	}

	public Integer getAnnoDj() {
		return annoDj;
	}
	public void setAnnoDj(Integer annoDj) {
		this.annoDj = annoDj;
	}
	
	public Boolean getDisabled() {
		return disabled;
	}
	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
}
