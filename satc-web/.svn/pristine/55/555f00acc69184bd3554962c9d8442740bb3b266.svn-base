package com.sat.sisat.predial.managed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.richfaces.component.html.HtmlComboBox;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.FacesUtil;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.common.vo.Mensaje;
import com.sat.sisat.persistence.entity.RjMes;
import com.sat.sisat.persistence.entity.RpDjarbitrio;
import com.sat.sisat.persistence.entity.RpDjpredial;
import com.sat.sisat.persistence.entity.RpDjuso;
import com.sat.sisat.persistence.entity.RpTipoUso;
import com.sat.sisat.predial.business.RegistroPrediosBoRemote;

@ManagedBean
@ViewScoped
public class UsosPredioManaged extends BaseManaged {
	
	@EJB
	RegistroPrediosBoRemote registroPrediosBo;
	
	Mensaje mensaje=new Mensaje(); 
	
	private RpDjuso rpDjuso;
	
	private HtmlComboBox cmbRpTipoUso;
	private List<SelectItem> lstTipoUso=new ArrayList<SelectItem>();

	private HashMap<String, Integer> mapRpTipoUso=new HashMap<String, Integer>();
	private HashMap<Integer,String> mapIRpTipoUso=new HashMap<Integer,String>();
	private String cmbValueRpTipoUso;
	
	private HtmlComboBox cmbmesinicio;
	private String cmbValueMesInicio;
	
	private HtmlComboBox cmbmesfin;
	private String cmbValueMesFin;
	
	private List<SelectItem> lstmes=new ArrayList<SelectItem>();
	private HashMap<String, String> mapRjMes=new HashMap<String, String>();
	private HashMap<String, String> mapIRjMes=new HashMap<String, String>();
	private Boolean disabled;
	
	public UsosPredioManaged(){
		
	}
	@PostConstruct
	public void init(){
		try{
			
//			List<RpTipoUso> lstRpTipoUso=registroPrediosBo.getAllRpTipoUso();
//			Iterator<RpTipoUso> it1 = lstRpTipoUso.iterator();  
//			lstTipoUso= new ArrayList<SelectItem>();
//			 
//	        while (it1.hasNext()){
//	        	RpTipoUso obj = it1.next();
//	        	lstTipoUso.add(new SelectItem(obj.getDescripcion().trim(),String.valueOf(obj.getTipoUsoId())));
//	        	mapRpTipoUso.put(obj.getDescripcion().trim(), obj.getTipoUsoId());
//	        	mapIRpTipoUso.put(obj.getTipoUsoId(),obj.getDescripcion().trim());
//	        }
	       
			List<RjMes> lstRjMes=registroPrediosBo.getAllRjMes();
			Iterator<RjMes> it2 = lstRjMes.iterator();  
			lstmes= new ArrayList<SelectItem>();
			 
	        while (it2.hasNext()){
	        	RjMes obj = it2.next();  
	        	lstmes.add(new SelectItem(obj.getDescripcion().trim(),String.valueOf(obj.getMesId())));
	        	mapRjMes.put(obj.getDescripcion().trim(), obj.getMesId());
	        	mapIRjMes.put(obj.getMesId(),obj.getDescripcion().trim());
	        }
	        
	        
	        rpDjuso=new RpDjuso();
	        
	        //Se corrige el a�o de afectacion para los USOS
	        Integer djId=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId");
			if(djId==null||djId==Constante.RESULT_PENDING){
				rpDjuso.setAnnoAfectacion(DateUtil.getAnioActual());
			}else{
				RpDjpredial rpdjpredial = registroPrediosBo.getRpDjpredial(djId);
				rpDjuso.setAnnoAfectacion(rpdjpredial.getAnnoDj());
			}
			
			/**
			 * Usos 2016 - Diferenciado por tramos.
			 * 
			 * */
			
			List<RpTipoUso> lstRpTipoUsos=registroPrediosBo.getAllRpTipoUsos(rpDjuso.getAnnoAfectacion());
			Iterator<RpTipoUso> it3 = lstRpTipoUsos.iterator();  
			lstTipoUso= new ArrayList<SelectItem>();
			 
	        while (it3.hasNext()){
	        	RpTipoUso obj = it3.next();
	        	lstTipoUso.add(new SelectItem(obj.getDescripcion().trim(),String.valueOf(obj.getTipoUsoId())));
	        	mapRpTipoUso.put(obj.getDescripcion().trim(), obj.getTipoUsoId());
	        	mapIRpTipoUso.put(obj.getTipoUsoId(),obj.getDescripcion().trim());
	        }
	        
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	public void salir(){
		
	}
	
	public boolean valida(){
		boolean result=true;
		try{
			if(!validaRangoFechas(cmbValueMesInicio, cmbValueMesFin)){
				result=false;
				WebMessages.messageError("Rango de fechas no es valido");	
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean validaRangoFechas(String fechaIni,String fechaFin){
		boolean result=false;
		try{
			Integer fechaIniId=Integer.valueOf(mapRjMes.get(fechaIni));
			Integer fechaFinId=Integer.valueOf(mapRjMes.get(fechaFin));
			if(fechaFinId>=fechaIniId)
				result=true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
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
				if(valida()){
					Integer djArbitrioId=registroPrediosBo.getDjArbitrioId(djId);
					if(djArbitrioId==null){
						RpDjarbitrio rpDjarbitrio=new RpDjarbitrio();  
						rpDjarbitrio.setDjId(djId);
						
						rpDjarbitrio.setEstado(Constante.ESTADO_ACTIVO);
						//cc: rpDjarbitrio.setFechaRegistro(DateUtil.getCurrentDate());
						//cc: rpDjarbitrio.setTerminal(Constante.TERMINAL);
						//cc: rpDjarbitrio.setUsuarioId(Constante.USUARIO_ID);
						
						int result=registroPrediosBo.guardarDjArbitrioId(rpDjarbitrio);
					}
					
					djArbitrioId=registroPrediosBo.getDjArbitrioId(djId);
					if(djArbitrioId!=null){
						getRpDjuso().setDjarbitrioId(djArbitrioId);
						//rpDjuso.annoAfectacion
						//rpDjuso.areaUso
						
						//cmbValueRpTipoUso
						getRpDjuso().setTipoUsoId(mapRpTipoUso.get(cmbValueRpTipoUso));
						//cmbValueMesInicio
						getRpDjuso().setMesInicioId(mapRjMes.get(cmbValueMesInicio));
						//cmbValueMesFin
						getRpDjuso().setMesFinId(mapRjMes.get(cmbValueMesFin));
						getRpDjuso().setEstado(Constante.ESTADO_ACTIVO);
						
						//cc: getRpDjuso().setUsuarioId(Constante.USUARIO_ID);
						//cc: getRpDjuso().setFechaRegistro(DateUtil.getCurrentDate());
						//cc: getRpDjuso().setTerminal(Constante.TERMINAL);
						
						int result=0;
						if(getRpDjuso().getDjusoId()>0)
							result=registroPrediosBo.actualizaRpDjuso(getRpDjuso());
						else
							result=registroPrediosBo.guardarRpDjuso(getRpDjuso());
						
						if(result>0){
							RegistroPredioManaged registroPredio = (RegistroPredioManaged) getManaged("registroPredioManaged");
							if(registroPredio!=null){
								registroPredio.loadUsosPredio();
								if(getRpDjuso().getDjusoId()==0)
									limpiar();
							}
						}else{
							WebMessages.messageError("No existe DJ seleccionado");
						}
					}
				}
			}else{
				
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	public void delete(){
		try{
			if(rpDjuso!=null){
				int result=registroPrediosBo.deleteRpDjuso(rpDjuso.getDjarbitrioId(),rpDjuso.getDjusoId());
				
				if(result>0){
					RegistroPredioManaged registro = (RegistroPredioManaged) getManaged("registroPredioManaged");
					registro.loadUsosPredio();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void limpiar(){
		try
		{
			rpDjuso=new RpDjuso();
			  //Se corrige el a�o de afectacion para los USOS
	        Integer djId=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId");
			if(djId==null||djId==Constante.RESULT_PENDING){
				rpDjuso.setAnnoAfectacion(DateUtil.getAnioActual());
			}else{
				RpDjpredial rpdjpredial = registroPrediosBo.getRpDjpredial(djId);
				rpDjuso.setAnnoAfectacion(rpdjpredial.getAnnoDj());
			} 
	     
	     getCmbRpTipoUso().setValue("");
	 	 getCmbmesinicio().setValue("");
	 	 getCmbmesfin().setValue("");
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	public void setProperty(RpDjuso uso){
		setRpDjuso(uso);
		
		String valueTipoUso=(mapIRpTipoUso.get(uso.getTipoUsoId())!=null)?mapIRpTipoUso.get(uso.getTipoUsoId()):"";
		getCmbRpTipoUso().setValue(valueTipoUso);
		
		String valueMesInicio=(mapIRjMes.get(uso.getMesInicio()!=null?uso.getMesInicio().trim():null)!=null)?mapIRjMes.get(uso.getMesInicio().trim()):"";
	 	getCmbmesinicio().setValue(valueMesInicio);
	 	
	 	String valueMesFin=(mapIRjMes.get(uso.getMesFin()!=null?uso.getMesFin().trim():null)!=null)?mapIRjMes.get(uso.getMesFin().trim()):"";
	 	getCmbmesfin().setValue(valueMesFin);
	}
	
	public RpDjuso getRpDjuso() {
		return rpDjuso;
	}

	public void setRpDjuso(RpDjuso rpDjuso) {
		this.rpDjuso = rpDjuso;
	}
	public HtmlComboBox getCmbmesinicio() {
		return cmbmesinicio;
	}

	public void setCmbmesinicio(HtmlComboBox cmbmesinicio) {
		this.cmbmesinicio = cmbmesinicio;
	}

	public String getCmbValueMesInicio() {
		return cmbValueMesInicio;
	}

	public void setCmbValueMesInicio(String cmbValueMesInicio) {
		this.cmbValueMesInicio = cmbValueMesInicio;
	}

	public HtmlComboBox getCmbmesfin() {
		return cmbmesfin;
	}

	public void setCmbmesfin(HtmlComboBox cmbmesfin) {
		this.cmbmesfin = cmbmesfin;
	}

	public String getCmbValueMesFin() {
		return cmbValueMesFin;
	}

	public void setCmbValueMesFin(String cmbValueMesFin) {
		this.cmbValueMesFin = cmbValueMesFin;
	}

	public List<SelectItem> getLstmes() {
		return lstmes;
	}

	public void setLstmes(List<SelectItem> lstmes) {
		this.lstmes = lstmes;
	}
	public HtmlComboBox getCmbRpTipoUso() {
		return cmbRpTipoUso;
	}

	public void setCmbRpTipoUso(HtmlComboBox cmbRpTipoUso) {
		this.cmbRpTipoUso = cmbRpTipoUso;
	}

	public String getCmbValueRpTipoUso() {
		return cmbValueRpTipoUso;
	}

	public void setCmbValueRpTipoUso(String cmbValueRpTipoUso) {
		this.cmbValueRpTipoUso = cmbValueRpTipoUso;
	}
	public List<SelectItem> getLstTipoUso() {
		return lstTipoUso;
	}

	public void setLstTipoUso(List<SelectItem> lstTipoUso) {
		this.lstTipoUso = lstTipoUso;
	}
	
	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public Boolean getIsEditable() {
		return getRpDjuso().getDjusoId()>0?Boolean.TRUE:Boolean.FALSE;
	}
}
