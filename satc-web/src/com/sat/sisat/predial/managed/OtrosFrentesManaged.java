package com.sat.sisat.predial.managed;

import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.FacesUtil;
import com.sat.sisat.common.util.Util;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.common.vo.Mensaje;
import com.sat.sisat.persistence.entity.RpOtrosFrente;
import com.sat.sisat.predial.business.RegistroPrediosBoRemote;
import com.sat.sisat.predial.dto.UbicacionDTO;

@ManagedBean
@ViewScoped
public class OtrosFrentesManaged extends BaseManaged {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	RegistroPrediosBoRemote registroPrediosBo;
	
	Mensaje mensaje=new Mensaje(); 
	
	private RpOtrosFrente rpOtrosFrente;
	
	private String tipoVia;
	private String nombreVia;
	private String codigoSectorCatastral;
	
	private Integer viaId;
	private Integer ubicacionId;
	private String numero1;
	private Integer numeroCuadra;
	
	private BigDecimal frente;
	
	public OtrosFrentesManaged(){
		
	}
	@PostConstruct
	public void init(){
		try{
			rpOtrosFrente=new RpOtrosFrente();
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	public void salir(){
		
	}
	public void guardar(){
		try{
			Integer djId=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId");
			if(djId==null||djId==Constante.RESULT_PENDING){
				RegistroPredioManaged registroPredio = (RegistroPredioManaged) getManaged("registroPredioManaged");
				registroPredio.inscripcionPredio();
			}
			djId=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId");
			if(djId!=null){
				if(validar()){
					getRpOtrosFrente().setDjId(djId);
					getRpOtrosFrente().setUbicacionId(getUbicacionId());
					getRpOtrosFrente().setNumero1(getNumero1().trim());
					getRpOtrosFrente().setFrente(getFrente());
					getRpOtrosFrente().setEstado(Constante.ESTADO_ACTIVO);
					
					//cc: getRpOtrosFrente().setUsuarioId(Constante.USUARIO_ID);
					//cc: getRpOtrosFrente().setFechaRegistro(DateUtil.getCurrentDate());
					//cc: getRpOtrosFrente().setTerminal(Constante.TERMINAL);
					
					int result=registroPrediosBo.guardarRpOtrosFrente(getRpOtrosFrente());
					if(result>0){
						RegistroPredioManaged registroPredio = (RegistroPredioManaged) getManaged("registroPredioManaged");
						if(registroPredio!=null){
							registroPredio.loadOtrosFrentes();
							limpiar();
						}
					}else{
						WebMessages.messageError("No existe DJ seleccionado");
					}	
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	public boolean validar(){
		boolean validation=false;
		if(getUbicacionId()!=null&&getUbicacionId()>0){
			if(getNumero1()!=null&&getNumero1().trim().length()>0){
				if(validaCuadra(getNumero1().trim())){
					validation=true;
				}
			}else{
				WebMessages.messageError("Debe indicar el campo Numero 1");
			}
		}else{
			WebMessages.messageError("Debe seleccionar la via o la denominacion urbana del predio");
		}
		return validation;
	}
	
	public boolean validaCuadra(String valorCampo){
		if(valorCampo!=null){
			if(valorCampo.trim().toUpperCase().equals("S/N")){
				return true;
			}else if(getNumeroCuadra()!=null&&getNumeroCuadra()>0){
				if(Util.toInteger(valorCampo)>0){
					String cuadra=valorCampo.substring(0, String.valueOf(getNumeroCuadra()).length());
					if(Util.toInteger(cuadra)==getNumeroCuadra()){
						return true;
					}else{
						WebMessages.messageError("El campo Numero 1 indicado no corresponde con el Numero de Cuadra "+getNumeroCuadra());
					}
				}else{
					WebMessages.messageError("El campo Numero 1 indicado debe ser un numero correspondiente con el Numero de Cuadra "+getNumeroCuadra()+" o S/N");
				}
			}else{
				//No definido el numero de cuadra de la ubicacion
			}
		}
		return false;
 	}
	
	public void delete(){
		try{
			if(rpOtrosFrente!=null){
				int result=registroPrediosBo.deleteRpOtrosFrente(rpOtrosFrente.getDjId(),rpOtrosFrente.getOtroFrenteId());
				if(result>0){
					RegistroPredioManaged registro = (RegistroPredioManaged) getManaged("registroPredioManaged");
					registro.loadOtrosFrentes();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private void limpiar(){
		setTipoVia("");
		setNombreVia("");
		setUbicacionId(null);
		setNumero1("");
		setFrente(null);
	}
	
	public void setSelectedVia(UbicacionDTO currentItem){
		setTipoVia(currentItem.getTipoVia());
		setNombreVia(currentItem.getVia());
		setUbicacionId(currentItem.getUbicacionId());
		setViaId(currentItem.getViaid());
		setCodigoSectorCatastral(currentItem.getCodigoCatastral());
		setNumeroCuadra(currentItem.getNumeroCuadra());
	}
	
	public void setParameter(){
		try{
			RegistroPredioManaged registroPredioManaged = (RegistroPredioManaged) getManaged("registroPredioManaged");
			
			Integer ubicacionId = registroPredioManaged.getRpDjdireccion().getUbicacionId();
						
			if( ubicacionId != null && ubicacionId > 0 && registroPredioManaged.isOtroFrente()){
				Integer numeroManzana = registroPrediosBo.getNumeroManzanaByUbicacionId(ubicacionId);				
				
				BuscarViaManaged buscarViaManaged = (BuscarViaManaged) getManaged("buscarViaManaged");
				buscarViaManaged.setNumeroManzana(numeroManzana);
				if(numeroManzana != null && numeroManzana.intValue() > 0){
					buscarViaManaged.setOtroFrente(registroPredioManaged.isOtroFrente());
				}else{
					buscarViaManaged.setOtroFrente(Boolean.FALSE);
				}
				
				buscarViaManaged.limpiar();
				buscarViaManaged.buscarV2();			
				
			}
			
			String paramParent=getRequestParameter("paramParent");
			getSessionMap().put("paramParent", paramParent);
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public void setProperty(RpOtrosFrente otroFrente){
		setRpOtrosFrente(otroFrente);
	}
	
	public RpOtrosFrente getRpOtrosFrente() {
		return rpOtrosFrente;
	}

	public void setRpOtrosFrente(RpOtrosFrente rpOtrosFrente) {
		this.rpOtrosFrente = rpOtrosFrente;
	}
	
	public String getTipoVia() {
		return tipoVia;
	}

	public void setTipoVia(String tipoVia) {
		this.tipoVia = tipoVia;
	}

	public String getNombreVia() {
		return nombreVia;
	}

	public void setNombreVia(String nombreVia) {
		this.nombreVia = nombreVia;
	}

	public Integer getUbicacionId() {
		return ubicacionId;
	}
	public void setUbicacionId(Integer ubicacionId) {
		this.ubicacionId = ubicacionId;
	}

	public String getNumero1() {
		return numero1;
	}

	public void setNumero1(String numero1) {
		this.numero1 = numero1;
	}
	public String getCodigoSectorCatastral() {
		return codigoSectorCatastral;
	}
	public void setCodigoSectorCatastral(String codigoSectorCatastral) {
		this.codigoSectorCatastral = codigoSectorCatastral;
	}
	public Integer getNumeroCuadra() {
		return numeroCuadra;
	}
	public void setNumeroCuadra(Integer numeroCuadra) {
		this.numeroCuadra = numeroCuadra;
	}
	public Integer getViaId() {
		return viaId;
	}
	public void setViaId(Integer viaId) {
		this.viaId = viaId;
	}
	public BigDecimal getFrente() {
		return frente;
	}
	public void setFrente(BigDecimal frente) {
		this.frente = frente;
	}
}
