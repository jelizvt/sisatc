package com.sat.sisat.predial.managed;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.richfaces.component.html.HtmlComboBox;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.FacesUtil;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.common.vo.Mensaje;
import com.sat.sisat.persistence.entity.RjComponenteConstruccion;
import com.sat.sisat.persistence.entity.RjEstadoConservacion;
import com.sat.sisat.persistence.entity.RjMes;
import com.sat.sisat.persistence.entity.RjTipoDepreciacion;
import com.sat.sisat.persistence.entity.RpDjconstruccion;
import com.sat.sisat.persistence.entity.RpDjpredial;
import com.sat.sisat.persistence.entity.RpMaterialPredominante;
import com.sat.sisat.persistence.entity.RpTipoNivel;
import com.sat.sisat.persistence.entity.TgTabla;
import com.sat.sisat.predial.business.RegistroPrediosBoRemote;

@ManagedBean
@ViewScoped
public class ConstruccionManaged extends BaseManaged {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	RegistroPrediosBoRemote registroPrediosBo;
	
	Mensaje mensaje=new Mensaje(); 
	
	private RpDjconstruccion rpDjconstruccion;
	
	private String referencia;
	
	private HtmlComboBox cmbtiponivel;
	private List<SelectItem> lsttiponivel=new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRpTipoNivel=new HashMap<String, Integer>();
	private HashMap<Integer,String> mapIRpTipoNivel=new HashMap<Integer,String>();
	
	private HtmlComboBox cmbmes;
	private List<SelectItem> lstmes=new ArrayList<SelectItem>();
	private HashMap<String, String> mapRjMes=new HashMap<String, String>();

	private HashMap<String, String> mapIRjMes=new HashMap<String, String>();
	
	private HtmlComboBox cmbMaterialPredominante;
	private List<SelectItem> lstMaterialPredominante=new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRpMaterialPredominante=new HashMap<String, Integer>();
	private HashMap<Integer,String> mapIRpMaterialPredominante=new HashMap<Integer,String>();
	
	private HtmlComboBox cmbEstadoConservacion;
	private List<SelectItem> lstEstadoConservacion=new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRjEstadoConservacion=new HashMap<String, Integer>();
	private HashMap<Integer,String> mapIRjEstadoConservacion=new HashMap<Integer,String>();
	
	private HtmlComboBox cmbTipoDepreciacion;
	private List<SelectItem> lstTipoDepreciacion=new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRjTipoDepreciacion=new HashMap<String, Integer>();
	private HashMap<Integer,String> mapIRjTipoDepreciacion=new HashMap<Integer,String>();
	
	//cchaucca:INI 18/06/2012 cambio solicitado, no se usa el campo area comun
	//private HtmlComboBox cmbUnidadMedidaAcc;
	//private String cmbValueUnidadMedidaAcc;
	//cchaucca:FIN 18/06/2012 cambio solicitado, no se usa el campo area comun

	private List<SelectItem> lstUnidadMedidaAcc=new ArrayList<SelectItem>();
	private HashMap<String, String> mapRjUnidadMedidaAcc=new HashMap<String, String>();
	private HashMap<String, String> mapIRjUnidadMedidaAcc=new HashMap<String, String>();
	
	//--RjComponenteConstruccion :ini
	private HtmlComboBox cmbComponenteConstruccion1;
	private List<SelectItem> lstComponenteConstruccion1=new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRjComponenteConstruccion1=new HashMap<String, Integer>();
	private HashMap<String, Integer> mapMRjComponenteConstruccion1=new HashMap<String, Integer>();
	private HashMap<Integer,String> mapIRjComponenteConstruccion1=new HashMap<Integer,String>();
	private HashMap<Integer,String> mapARjComponenteConstruccion1=new HashMap<Integer,String>();
	
	private HtmlComboBox cmbComponenteConstruccion2;
	private List<SelectItem> lstComponenteConstruccion2=new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRjComponenteConstruccion2=new HashMap<String, Integer>();
	private HashMap<String, Integer> mapMRjComponenteConstruccion2=new HashMap<String, Integer>();
	private HashMap<Integer,String> mapIRjComponenteConstruccion2=new HashMap<Integer,String>();
	private HashMap<Integer,String> mapARjComponenteConstruccion2=new HashMap<Integer,String>();
	
	private HtmlComboBox cmbComponenteConstruccion3;
	private List<SelectItem> lstComponenteConstruccion3=new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRjComponenteConstruccion3=new HashMap<String, Integer>();
	private HashMap<String, Integer> mapMRjComponenteConstruccion3=new HashMap<String, Integer>();
	private HashMap<Integer,String> mapIRjComponenteConstruccion3=new HashMap<Integer,String>();
	private HashMap<Integer,String> mapARjComponenteConstruccion3=new HashMap<Integer,String>();
	
	private HtmlComboBox cmbComponenteConstruccion4;
	private List<SelectItem> lstComponenteConstruccion4=new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRjComponenteConstruccion4=new HashMap<String, Integer>();
	private HashMap<String, Integer> mapMRjComponenteConstruccion4=new HashMap<String, Integer>();
	private HashMap<Integer,String> mapIRjComponenteConstruccion4=new HashMap<Integer,String>();
	private HashMap<Integer,String> mapARjComponenteConstruccion4=new HashMap<Integer,String>();
	
	private HtmlComboBox cmbComponenteConstruccion5;
	private List<SelectItem> lstComponenteConstruccion5=new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRjComponenteConstruccion5=new HashMap<String, Integer>();
	private HashMap<String, Integer> mapMRjComponenteConstruccion5=new HashMap<String, Integer>();
	private HashMap<Integer,String> mapIRjComponenteConstruccion5=new HashMap<Integer,String>();
	private HashMap<Integer,String> mapARjComponenteConstruccion5=new HashMap<Integer,String>();
	
	private HtmlComboBox cmbComponenteConstruccion6;
	private List<SelectItem> lstComponenteConstruccion6=new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRjComponenteConstruccion6=new HashMap<String, Integer>();
	private HashMap<String, Integer> mapMRjComponenteConstruccion6=new HashMap<String, Integer>();
	private HashMap<Integer,String> mapIRjComponenteConstruccion6=new HashMap<Integer,String>();
	private HashMap<Integer,String> mapARjComponenteConstruccion6=new HashMap<Integer,String>();
	
	private HtmlComboBox cmbComponenteConstruccion7;
	private List<SelectItem> lstComponenteConstruccion7=new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRjComponenteConstruccion7=new HashMap<String, Integer>();
	private HashMap<String, Integer> mapMRjComponenteConstruccion7=new HashMap<String, Integer>();
	private HashMap<Integer,String> mapIRjComponenteConstruccion7=new HashMap<Integer,String>();
	private HashMap<Integer,String> mapARjComponenteConstruccion7=new HashMap<Integer,String>();
	
	//--RjComponenteConstruccion :fin
	private Integer annoDj;
	private Integer annoActualizacion;
	private Integer annoConstruccion;
	
	//Obteniendo el año actual del procesamiento de la actual DJ
	private Integer annoActualProcesamiento = DateUtil.getAnioActual();
	
	private Boolean disabled;
	
	public ConstruccionManaged(){
		
	}
	
	@PostConstruct
	public void init(){
		try{
			rpDjconstruccion=new RpDjconstruccion();
			setSelUnidadMed(Boolean.FALSE);
			
			//cchaucca:INI 18/06/2012 cambio solicitado, no se usa el campo area comun
			//setCmbValueUnidadMedidaAcc("--");
			//cchaucca:FIN 18/06/2012 cambio solicitado, no se usa el campo area comun
			
		        //RpTipoNivel
				//INICIO ITANTAMANGO
		        List<RpTipoNivel> lstRpTipoNivel=registroPrediosBo.getAllRpTipoNivel();
				Iterator<RpTipoNivel> it = lstRpTipoNivel.iterator();  
				lsttiponivel = new ArrayList<SelectItem>();
				
		        while (it.hasNext()){
		        	RpTipoNivel obj = it.next();
	        		lsttiponivel.add(new SelectItem(obj.getDescripcion().trim(),String.valueOf(obj.getTipoNivelId())));
		        	mapRpTipoNivel.put(obj.getDescripcion().trim(), obj.getTipoNivelId());
		        	mapIRpTipoNivel.put(obj.getTipoNivelId(),obj.getDescripcion().trim());
		        }
		        //FIN ITANTAMANGO
		        
		        //RjMes
		        List<RjMes> lstRjMes=registroPrediosBo.getAllRjMes();
				Iterator<RjMes> it2 = lstRjMes.iterator();  
				lstmes = new ArrayList<SelectItem>();
				 
		        while (it2.hasNext()){
		        	RjMes obj = it2.next();  
		        	lstmes.add(new SelectItem(obj.getDescripcion().trim(),String.valueOf(obj.getMesId())));
		        	mapRjMes.put(obj.getDescripcion().trim(), obj.getMesId().trim());
		        	mapIRjMes.put(obj.getMesId().trim(),obj.getDescripcion().trim());
		        }
		        
		        //RpMaterialPredominante
		        List<RpMaterialPredominante> lstRpMaterialPredominante=registroPrediosBo.getAllRpMaterialPredominante();
				Iterator<RpMaterialPredominante> it3 = lstRpMaterialPredominante.iterator();  
				lstMaterialPredominante = new ArrayList<SelectItem>();
				 
		        while (it3.hasNext()){
		        	RpMaterialPredominante obj = it3.next();  
		        	lstMaterialPredominante.add(new SelectItem(obj.getMatPredominanteId()+"-"+obj.getDescripcion().trim(),String.valueOf(obj.getMatPredominanteId())));
		        	mapRpMaterialPredominante.put(obj.getMatPredominanteId()+"-"+obj.getDescripcion().trim(), obj.getMatPredominanteId());
		        	mapIRpMaterialPredominante.put(obj.getMatPredominanteId(),obj.getMatPredominanteId()+"-"+obj.getDescripcion().trim());
		        }
		        
		        //RjEstadoConservacion
		        List<RjEstadoConservacion> lstRjEstadoConservacion=registroPrediosBo.getAllRjEstadoConservacion();
				Iterator<RjEstadoConservacion> it4 = lstRjEstadoConservacion.iterator();  
				lstEstadoConservacion = new ArrayList<SelectItem>();
				 
		        while (it4.hasNext()){	
		        	RjEstadoConservacion obj = it4.next();  
		        	lstEstadoConservacion.add(new SelectItem(obj.getConservacionId()+"-"+obj.getDescripcion().trim(),String.valueOf(obj.getConservacionId())));
		        	mapRjEstadoConservacion.put(obj.getConservacionId()+"-"+obj.getDescripcion().trim(), obj.getConservacionId());
		        	mapIRjEstadoConservacion.put(obj.getConservacionId(),obj.getConservacionId()+"-"+obj.getDescripcion().trim());
		        	
		        	
		        }
		        
		    	//RjTipoDepreciacion
		        List<RjTipoDepreciacion> lstRjTipoDepreciacion=registroPrediosBo.getAllRjTipoDepreciacion();
				Iterator<RjTipoDepreciacion> it5 = lstRjTipoDepreciacion.iterator();  
				lstTipoDepreciacion = new ArrayList<SelectItem>();
				 
		        while (it5.hasNext()){
		        	RjTipoDepreciacion obj = it5.next();  
		        	lstTipoDepreciacion.add(new SelectItem(obj.getTipoDepreciacionId()+"-"+obj.getDescripcion().trim(),String.valueOf(obj.getTipoDepreciacionId())));
		        	mapRjTipoDepreciacion.put(obj.getTipoDepreciacionId()+"-"+obj.getDescripcion().trim(), obj.getTipoDepreciacionId());
		        	mapIRjTipoDepreciacion.put(obj.getTipoDepreciacionId(),obj.getTipoDepreciacionId()+"-"+obj.getDescripcion().trim());
		        }
		        
		        //UnidadMedidaAcc
		        List<TgTabla> lstRjUnidadMedidaAcc=registroPrediosBo.getAllTgTabla("rj_unidad_medida_acc");
				Iterator<TgTabla> it6 = lstRjUnidadMedidaAcc.iterator();  
				lstUnidadMedidaAcc = new ArrayList<SelectItem>();
				 
		        while (it6.hasNext()){
		        	TgTabla obj = it6.next();  
		        	lstUnidadMedidaAcc.add(new SelectItem(obj.getCampo2().trim(),obj.getCampo1().trim()));
		        	mapRjUnidadMedidaAcc.put(obj.getCampo2().trim(), obj.getCampo1().trim());
		        	mapIRjUnidadMedidaAcc.put(obj.getCampo1().trim(),obj.getCampo2().trim());
		        }
		        
		        Integer djId=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId");
				if(djId!=null&&djId>0){
					RpDjpredial djpredio=registroPrediosBo.getRpDjpredial(djId);
					if(djpredio!=null){
						setAnnoDj(djpredio.getAnnoDj());	
					}else{
						setAnnoDj(DateUtil.getAnioActual());
					}
				}else{
					setAnnoDj(DateUtil.getAnioActual());
				}
					
				//--RjComponenteConstruccion :inicio
		        //K_TABLA_MUROS
		        //List<RjComponenteConstruccion> lstRjComponenteConstruccion=registroPrediosBo.getAllRjComponenteConstruccion(getAnnoDj(), 1);
				List<RjComponenteConstruccion> lstRjComponenteConstruccion=registroPrediosBo.getAllRjComponenteConstruccion(annoActualProcesamiento, 1);
				Iterator<RjComponenteConstruccion> ite = lstRjComponenteConstruccion.iterator();  
				lstComponenteConstruccion1 = new ArrayList<SelectItem>();
				 
		        while (ite.hasNext()){
		        	RjComponenteConstruccion obj = ite.next();  
		        	lstComponenteConstruccion1.add(new SelectItem(obj.getCategoria().trim()+"-"+obj.getDescripcion().trim(),String.valueOf(obj.getCateConstruccionId())));
		        	mapRjComponenteConstruccion1.put(obj.getCategoria().trim()+"-"+obj.getDescripcion().trim(),obj.getCateConstruccionId());
		        	mapMRjComponenteConstruccion1.put(obj.getCategoria().trim(),obj.getCateConstruccionId());
		        	mapIRjComponenteConstruccion1.put(obj.getCateConstruccionId(),obj.getCategoria().trim()+"-"+obj.getDescripcion().trim());
		        	mapARjComponenteConstruccion1.put(obj.getCateConstruccionId(),obj.getCategoria().trim());
		        }
		        
		        //K_TABLA_TECHOS
		        //lstRjComponenteConstruccion=registroPrediosBo.getAllRjComponenteConstruccion(getAnnoDj(), 2);
		        lstRjComponenteConstruccion=registroPrediosBo.getAllRjComponenteConstruccion(annoActualProcesamiento, 2);
				ite = lstRjComponenteConstruccion.iterator();  
				lstComponenteConstruccion2 = new ArrayList<SelectItem>();
				 
		        while (ite.hasNext()){
		        	RjComponenteConstruccion obj = ite.next();  
		        	lstComponenteConstruccion2.add(new SelectItem(obj.getCategoria().trim()+"-"+obj.getDescripcion().trim(),String.valueOf(obj.getCateConstruccionId())));
		        	mapRjComponenteConstruccion2.put(obj.getCategoria().trim()+"-"+obj.getDescripcion().trim(),obj.getCateConstruccionId());
		        	mapMRjComponenteConstruccion2.put(obj.getCategoria().trim(),obj.getCateConstruccionId());
		        	mapIRjComponenteConstruccion2.put(obj.getCateConstruccionId(),obj.getCategoria().trim()+"-"+obj.getDescripcion().trim());
		        	mapARjComponenteConstruccion2.put(obj.getCateConstruccionId(),obj.getCategoria().trim());
		        }
		        
		        //K_TABLA_PISOS
		        //lstRjComponenteConstruccion=registroPrediosBo.getAllRjComponenteConstruccion(getAnnoDj(), 3);
		        lstRjComponenteConstruccion=registroPrediosBo.getAllRjComponenteConstruccion(annoActualProcesamiento, 3);
				ite = lstRjComponenteConstruccion.iterator();  
				lstComponenteConstruccion3 = new ArrayList<SelectItem>();
				 
		        while (ite.hasNext()){
		        	RjComponenteConstruccion obj = ite.next();  
		        	lstComponenteConstruccion3.add(new SelectItem(obj.getCategoria().trim()+"-"+obj.getDescripcion().trim(),String.valueOf(obj.getCateConstruccionId())));
		        	mapRjComponenteConstruccion3.put(obj.getCategoria().trim()+"-"+obj.getDescripcion().trim(),obj.getCateConstruccionId());
		        	mapMRjComponenteConstruccion3.put(obj.getCategoria().trim(),obj.getCateConstruccionId());
		        	mapIRjComponenteConstruccion3.put(obj.getCateConstruccionId(),obj.getCategoria().trim()+"-"+obj.getDescripcion().trim());
		        	mapARjComponenteConstruccion3.put(obj.getCateConstruccionId(),obj.getCategoria().trim());
		        }
		        
		        //K_TABLA_PUERTAS
		        //lstRjComponenteConstruccion=registroPrediosBo.getAllRjComponenteConstruccion(getAnnoDj(), 4);
		        lstRjComponenteConstruccion=registroPrediosBo.getAllRjComponenteConstruccion(annoActualProcesamiento, 4);
				ite = lstRjComponenteConstruccion.iterator();  
				lstComponenteConstruccion4 = new ArrayList<SelectItem>();
				 
		        while (ite.hasNext()){
		        	RjComponenteConstruccion obj = ite.next();  
		        	lstComponenteConstruccion4.add(new SelectItem(obj.getCategoria().trim()+"-"+obj.getDescripcion().trim(),String.valueOf(obj.getCateConstruccionId())));
		        	mapRjComponenteConstruccion4.put(obj.getCategoria().trim()+"-"+obj.getDescripcion().trim(),obj.getCateConstruccionId());
		        	mapMRjComponenteConstruccion4.put(obj.getCategoria().trim(),obj.getCateConstruccionId());
		        	mapIRjComponenteConstruccion4.put(obj.getCateConstruccionId(),obj.getCategoria().trim()+"-"+obj.getDescripcion().trim());
		        	mapARjComponenteConstruccion4.put(obj.getCateConstruccionId(),obj.getCategoria().trim());
		        }
		        
		        //K_TABLA_REVESTIMIENTO
		        //lstRjComponenteConstruccion=registroPrediosBo.getAllRjComponenteConstruccion(getAnnoDj(), 5);
		        lstRjComponenteConstruccion=registroPrediosBo.getAllRjComponenteConstruccion(annoActualProcesamiento, 5);
				ite = lstRjComponenteConstruccion.iterator();  
				lstComponenteConstruccion5 = new ArrayList<SelectItem>();
				 
		        while (ite.hasNext()){
		        	RjComponenteConstruccion obj = ite.next();  
		        	lstComponenteConstruccion5.add(new SelectItem(obj.getCategoria().trim()+"-"+obj.getDescripcion().trim(),String.valueOf(obj.getCateConstruccionId())));
		        	mapRjComponenteConstruccion5.put(obj.getCategoria().trim()+"-"+obj.getDescripcion().trim(),obj.getCateConstruccionId());
		        	mapMRjComponenteConstruccion5.put(obj.getCategoria().trim(),obj.getCateConstruccionId());
		        	mapIRjComponenteConstruccion5.put(obj.getCateConstruccionId(),obj.getCategoria().trim()+"-"+obj.getDescripcion().trim());
		        	mapARjComponenteConstruccion5.put(obj.getCateConstruccionId(),obj.getCategoria().trim());
		        }
		        
		        //K_TABLA_BANNOS
		        //lstRjComponenteConstruccion=registroPrediosBo.getAllRjComponenteConstruccion(getAnnoDj(), 6);
		        lstRjComponenteConstruccion=registroPrediosBo.getAllRjComponenteConstruccion(annoActualProcesamiento, 6);
				ite = lstRjComponenteConstruccion.iterator();  
				lstComponenteConstruccion6 = new ArrayList<SelectItem>();
				 
		        while (ite.hasNext()){
		        	RjComponenteConstruccion obj = ite.next();  
		        	lstComponenteConstruccion6.add(new SelectItem(obj.getCategoria().trim()+"-"+obj.getDescripcion().trim(),String.valueOf(obj.getCateConstruccionId())));
		        	mapRjComponenteConstruccion6.put(obj.getCategoria().trim()+"-"+obj.getDescripcion().trim(),obj.getCateConstruccionId());
		        	mapMRjComponenteConstruccion6.put(obj.getCategoria().trim(),obj.getCateConstruccionId());
		        	mapIRjComponenteConstruccion6.put(obj.getCateConstruccionId(),obj.getCategoria().trim()+"-"+obj.getDescripcion().trim());
		        	mapARjComponenteConstruccion6.put(obj.getCateConstruccionId(),obj.getCategoria().trim());
		        }
		        
		        //K_TABLA_INSTALACIONES_ELECTRICAS
		        //lstRjComponenteConstruccion=registroPrediosBo.getAllRjComponenteConstruccion(getAnnoDj(), 7);
		        lstRjComponenteConstruccion=registroPrediosBo.getAllRjComponenteConstruccion(annoActualProcesamiento, 7);
				ite = lstRjComponenteConstruccion.iterator();  
				lstComponenteConstruccion7 = new ArrayList<SelectItem>();
				 
		        while (ite.hasNext()){
		        	RjComponenteConstruccion obj = ite.next();  
		        	lstComponenteConstruccion7.add(new SelectItem(obj.getCategoria().trim()+"-"+obj.getDescripcion().trim(),String.valueOf(obj.getCateConstruccionId())));
		        	mapRjComponenteConstruccion7.put(obj.getCategoria().trim()+"-"+obj.getDescripcion().trim(),obj.getCateConstruccionId());
		        	mapMRjComponenteConstruccion7.put(obj.getCategoria().trim(),obj.getCateConstruccionId());
		        	mapIRjComponenteConstruccion7.put(obj.getCateConstruccionId(),obj.getCategoria().trim()+"-"+obj.getDescripcion().trim());
		        	mapARjComponenteConstruccion7.put(obj.getCateConstruccionId(),obj.getCategoria().trim());
		        }
		        //--RjComponenteConstruccion :fin
		        
		}catch(Exception e){
			e.printStackTrace();
	        WebMessages.messageFatal(e);
		}
	}
	public HashMap<String, String> getMapRjMes() {
		return mapRjMes;
	}

	public void setMapRjMes(HashMap<String, String> mapRjMes) {
		this.mapRjMes = mapRjMes;
	}

	public HashMap<String, String> getMapIRjMes() {
		return mapIRjMes;
	}

	public void setMapIRjMes(HashMap<String, String> mapIRjMes) {
		this.mapIRjMes = mapIRjMes;
	}

	public void reloadTipoNivel(){
		try{
			Boolean poseeAreaComunConstruida=(Boolean)getSessionMap().get("poseeAreaComunConstruida");
			if(poseeAreaComunConstruida==null){
				poseeAreaComunConstruida=Boolean.TRUE;
			}
			
			List<RpTipoNivel> lstRpTipoNivel=registroPrediosBo.getAllRpTipoNivel();
			Iterator<RpTipoNivel> it = lstRpTipoNivel.iterator();
			
			lsttiponivel = new ArrayList<SelectItem>();
			mapRpTipoNivel=new HashMap<String, Integer>();
			mapIRpTipoNivel=new HashMap<Integer,String>();
			
	        while (it.hasNext()){
	        	RpTipoNivel obj = it.next();
	        	if(obj.getTipoNivelId()==6){
	        		if(poseeAreaComunConstruida){
		        		lsttiponivel.add(new SelectItem(obj.getDescripcion().trim(),String.valueOf(obj.getTipoNivelId())));
		        		mapRpTipoNivel.put(obj.getDescripcion().trim(), obj.getTipoNivelId());
		        		mapIRpTipoNivel.put(obj.getTipoNivelId(),obj.getDescripcion().trim());
	        		}
	        	}else{
	        		lsttiponivel.add(new SelectItem(obj.getDescripcion().trim(),String.valueOf(obj.getTipoNivelId())));
	        		mapRpTipoNivel.put(obj.getDescripcion().trim(), obj.getTipoNivelId());
	        		mapIRpTipoNivel.put(obj.getTipoNivelId(),obj.getDescripcion().trim());
	        	}
	        }
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void delete(){
		try{
			if(rpDjconstruccion!=null){
				int result=registroPrediosBo.deleteRpDjconstruccion(rpDjconstruccion.getDjId(),rpDjconstruccion.getConstruccionId());
				if(result>0){
					RegistroPredioManaged registro = (RegistroPredioManaged) getManaged("registroPredioManaged");
					
					registro.loadDatosConstruccion();
					registro.recalculoDatosUsos();
					registro.loadDatosUsos();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public boolean validaAreaConstruccion()
	{	/*
		Corregido a solicitud de Cesar Minchan
	    */
		/*
		if(rpDjconstruccion.getAreaConstruccion().longValue()<=rpDjconstruccion.getAreaTerreno().longValue()){
			return true;
		}else{
			WebMessages.messageError("El Area de Construccion es mayor al Area de Terreno");
			return false;
		}*/
		return true;
	}
	
	/**
	 * Valida que los niveles y secciones sean unicas para un determinada declaraci�n jurada 
	 */
	public boolean validaSeccionUnica(Integer DjId){
		try{
			if(rpDjconstruccion!=null&&rpDjconstruccion.getConstruccionId()!=null&&rpDjconstruccion.getConstruccionId()>0){
				Integer cantidadDuplicados=registroPrediosBo.getExisteMismoNivel(DjId, rpDjconstruccion.getConstruccionId(), rpDjconstruccion.getNroNivel(), rpDjconstruccion.getSeccion());
				if(cantidadDuplicados==0){
					return true;
				}
			}else{
				Integer cantidadDuplicados=registroPrediosBo.getExisteMismoNivel(DjId,rpDjconstruccion.getNroNivel(), rpDjconstruccion.getSeccion());
				if(cantidadDuplicados==0){
					return true;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
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
				if(validaFechas()){
					if(validaSeccionUnica(djId)){//Valida que los niveles y secciones sean unicas para un determinada declaraci�n jurada
						if(validaAreaConstruccion()){
							rpDjconstruccion.setDjId(djId);
							
							rpDjconstruccion.setTipoNivelId(Integer.valueOf(mapRpTipoNivel.get(cmbtiponivel.getValue())));
							//nroNivel
							//annoConstruccion
							rpDjconstruccion.setMesConstruccion(String.valueOf(mapRjMes.get(cmbmes.getValue())));
							//areaConstruccion
							
							//INICIO ITANTAMANGO
							if(rpDjconstruccion.getTipoNivelId()==Constante.TIPO_NIVEL_AREA_COMUN){
								rpDjconstruccion.setAreaComunConstruccion(rpDjconstruccion.getAreaConstruccion());
							}
							//FIN ITANTAMANGO
							//areaTerreno
							//
							
							//cchaucca:INI 18/06/2012 no se usara el campo de area de construccion:cambio solicitado
							/*
							String CodigoUnidadMedida=mapRjUnidadMedidaAcc.get(cmbUnidadMedidaAcc.getValue());
							if(CodigoUnidadMedida!=null)
								rpDjconstruccion.setTipoUnidadMedida(String.valueOf(CodigoUnidadMedida));
							else
								rpDjconstruccion.setTipoUnidadMedida(null);
							*/
							//cchaucca:INI 18/06/2012 no se usara el campo de area de construccion:cambio solicitado
							
							//areaComunConstruccion
							rpDjconstruccion.setMatPredominanteId(mapRpMaterialPredominante.get(cmbMaterialPredominante.getValue()));
							//estado de concervacion
							rpDjconstruccion.setConservacionId(mapRjEstadoConservacion.get(cmbEstadoConservacion.getValue()));
							
							rpDjconstruccion.setClasiDepreciacionId(mapRjTipoDepreciacion.get(cmbTipoDepreciacion.getValue()));
							/*
									 	mapRjEstadoConservacion.put(obj.getDescripcion().trim(), obj.getConservacionId());
					        			mapIRjEstadoConservacion.put(obj.getConservacionId(),obj.getDescripcion().trim());
							 * **/
							rpDjconstruccion.setMuros(mapMRjComponenteConstruccion1.get(getCategoria(cmbComponenteConstruccion1.getValue())));
							rpDjconstruccion.setTecho(mapMRjComponenteConstruccion2.get(getCategoria(cmbComponenteConstruccion2.getValue())));
							rpDjconstruccion.setPisos(mapMRjComponenteConstruccion3.get(getCategoria(cmbComponenteConstruccion3.getValue())));
							rpDjconstruccion.setPuertas(mapMRjComponenteConstruccion4.get(getCategoria(cmbComponenteConstruccion4.getValue())));
							rpDjconstruccion.setRevestimientos(mapMRjComponenteConstruccion5.get(getCategoria(cmbComponenteConstruccion5.getValue())));
							rpDjconstruccion.setBannos(mapMRjComponenteConstruccion6.get(getCategoria(cmbComponenteConstruccion6.getValue())));
							rpDjconstruccion.setElectricos(mapMRjComponenteConstruccion7.get(getCategoria(cmbComponenteConstruccion7.getValue())));
							//referencia
							//cc: rpDjconstruccion.setUsuarioId(Constante.USUARIO_ID);
							//cc: rpDjconstruccion.setFechaRegistro(DateUtil.getCurrentDate());
							//cc: rpDjconstruccion.setTerminal(Constante.TERMINAL);
							
							if(rpDjconstruccion.getAnnoActualizacion() == null || rpDjconstruccion.getAnnoActualizacion() == 0 ){
								rpDjconstruccion.setAnnoActualizacion( rpDjconstruccion.getAnnoConstruccion() );
							}else if( rpDjconstruccion.getAnnoActualizacion().intValue() < rpDjconstruccion.getAnnoConstruccion()){
								rpDjconstruccion.setAnnoActualizacion( rpDjconstruccion.getAnnoConstruccion() );
							}else if( rpDjconstruccion.getAnnoActualizacion().intValue() > annoDj){
								rpDjconstruccion.setAnnoActualizacion( annoDj );
							}
							
							if(rpDjconstruccion.getConstruccionId()==null){
								int res=registroPrediosBo.guardarRpDjconstruccion(rpDjconstruccion);
								if(res>0){
									RegistroPredioManaged registroPredio = (RegistroPredioManaged) getManaged("registroPredioManaged");
									if(registroPredio!=null){
										registroPredio.loadDatosConstruccion();
										limpiar();
									}
								}else{
									WebMessages.messageError("No existe DJ seleccionado");
								}
							}else{
								if(annoConstruccion.intValue() == rpDjconstruccion.getAnnoActualizacion().intValue() && annoConstruccion.intValue() != annoActualizacion.intValue() ){
									int res = registroPrediosBo.guardarRpDjconstruccion(rpDjconstruccion);
									if (res > 0) {
										RegistroPredioManaged registroPredio = (RegistroPredioManaged) getManaged("registroPredioManaged");
										if (registroPredio != null) {
											registroPredio.loadDatosConstruccion();
											registroPredio.recalculoDatosUsos();
											registroPredio.loadDatosUsos();
											limpiar();
										}
									} else {
										WebMessages
												.messageError("No existe DJ seleccionado");
									}
								}else if(rpDjconstruccion.getAnnoActualizacion().intValue() == rpDjconstruccion.getAnnoConstruccion().intValue()
											 || rpDjconstruccion.getAnnoActualizacion().intValue() == annoActualizacion.intValue() ){
									
									int res=registroPrediosBo.actualizaRpDjconstruccion(rpDjconstruccion);
									if(res>0){
										RegistroPredioManaged registroPredio = (RegistroPredioManaged) getManaged("registroPredioManaged");
											
										if(registroPredio!=null){
											registroPredio.loadDatosConstruccion();
										}
									}else{
											WebMessages.messageError("No existe DJ seleccionado");
									}
								}else{
									int res = registroPrediosBo.guardarRpDjconstruccion(rpDjconstruccion);
									if (res > 0) {
										RegistroPredioManaged registroPredio = (RegistroPredioManaged) getManaged("registroPredioManaged");
										if (registroPredio != null) {
											registroPredio.loadDatosConstruccion();
											registroPredio.recalculoDatosUsos();
											registroPredio.loadDatosUsos();
											limpiar();
										}
									} else {
										WebMessages
												.messageError("No existe DJ seleccionado");
									}
								}
									
								
							}
						}
					}else{
						WebMessages.messageError("El nivel y seccion "+rpDjconstruccion.getNroNivel()+" - "+rpDjconstruccion.getSeccion()+" ya existe");
					}	
				}
			}else{
				WebMessages.messageError("No existe DJ seleccionado");
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	private boolean validaFechas(){
		boolean resul=false;
		try{
			Integer mes=Integer.valueOf(mapRjMes.get(cmbmes.getValue()));
			Integer anio=Integer.valueOf(getRpDjconstruccion().getAnnoConstruccion());
     					
			if(anio.intValue()<DateUtil.getAnioActual().intValue()){
				resul=true;
			}else if(anio.intValue()==DateUtil.getAnioActual().intValue()&&mes.intValue()<=DateUtil.getMesActual().intValue()){
				resul=true;
			}else{
				WebMessages.messageError("La fecha de construccion indicada es superior a la fecha actual.");
				resul=false;	
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		return resul;
	}
	
	private void limpiar(){
		mensaje=new Mensaje();
		BigDecimal temp=rpDjconstruccion.getAreaTerreno();
		rpDjconstruccion=new RpDjconstruccion();
		rpDjconstruccion.setAreaTerreno(temp);
		setReferencia("");
		getCmbtiponivel().setValue("");
		getCmbmes().setValue("");
		getCmbMaterialPredominante().setValue("");
		getCmbEstadoConservacion().setValue("");
		getCmbTipoDepreciacion().setValue("");
		//cchaucca:INI 18/06/2012 cambio solicitado, no se usa el campo area comun
		//getCmbUnidadMedidaAcc().setValue("");
		//setCmbValueUnidadMedidaAcc("");
		//cchaucca:INI 18/06/2012 cambio solicitado, no se usa el campo area comun
		getCmbComponenteConstruccion1().setValue("");
		getCmbComponenteConstruccion2().setValue("");
		getCmbComponenteConstruccion3().setValue("");
		getCmbComponenteConstruccion4().setValue("");
		getCmbComponenteConstruccion5().setValue("");
		getCmbComponenteConstruccion6().setValue("");
		getCmbComponenteConstruccion7().setValue("");
	}
	private String getCategoria(Object componente){
		try{
			return String.valueOf(componente.toString().charAt(0));	
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	public void setDenominacion(RpDjconstruccion rpDjconstruccion)
	{
		rpDjconstruccion.setDentiponivel(mapIRpTipoNivel.get(rpDjconstruccion.getTipoNivelId()));
		rpDjconstruccion.setDenmatpredominante(mapIRpMaterialPredominante.get(rpDjconstruccion.getMatPredominanteId()));
		rpDjconstruccion.setDenestadoconservacion(mapIRjEstadoConservacion.get(rpDjconstruccion.getConservacionId()));
		rpDjconstruccion.setDentipodepreciacion(mapIRpTipoNivel.get(rpDjconstruccion.getClasiDepreciacionId()));
		
		rpDjconstruccion.setDenmuros(mapARjComponenteConstruccion1.get(rpDjconstruccion.getMuros()));
		rpDjconstruccion.setDentecho(mapARjComponenteConstruccion2.get(rpDjconstruccion.getTecho()));
		rpDjconstruccion.setDenpisos(mapARjComponenteConstruccion3.get(rpDjconstruccion.getPisos()));
		rpDjconstruccion.setDenpuertas(mapARjComponenteConstruccion4.get(rpDjconstruccion.getPuertas()));
		rpDjconstruccion.setDenrevestimiento(mapARjComponenteConstruccion5.get(rpDjconstruccion.getRevestimientos()));
		rpDjconstruccion.setDenbannos(mapARjComponenteConstruccion6.get(rpDjconstruccion.getBannos()));
		rpDjconstruccion.setDenelectrico(mapARjComponenteConstruccion7.get(rpDjconstruccion.getElectricos()));
	}
	public void setProperty(RpDjconstruccion construccion){
		setRpDjconstruccion(construccion);
		cmbtiponivel.setValue(mapIRpTipoNivel.get(rpDjconstruccion.getTipoNivelId()));
		//nroNivel
		//annoConstruccion
		if(rpDjconstruccion.getMesConstruccion()!=null)
			cmbmes.setValue(mapIRjMes.get(rpDjconstruccion.getMesConstruccion().trim()));
		//areaConstruccion
		//areaTerreno--FALTA CAMPO EN TABLA
		
		//cchaucca:INI 18/06/2012 no se usara el campo de area de construccion:cambio solicitado
		//cmbUnidadMedidaAcc.setValue(mapIRjUnidadMedidaAcc.get(rpDjconstruccion.getTipoUnidadMedida()));
		//cchaucca:FIN 18/06/2012 
		
		//areaComunConstruccion
		cmbMaterialPredominante.setValue(mapIRpMaterialPredominante.get(rpDjconstruccion.getMatPredominanteId()));
		//estado de conservacion
		cmbEstadoConservacion.setValue(mapIRjEstadoConservacion.get(rpDjconstruccion.getConservacionId()));
			
		cmbTipoDepreciacion.setValue(mapIRjTipoDepreciacion.get(rpDjconstruccion.getClasiDepreciacionId()));
		cmbComponenteConstruccion1.setValue(mapIRjComponenteConstruccion1.get(rpDjconstruccion.getMuros()));
		cmbComponenteConstruccion2.setValue(mapIRjComponenteConstruccion2.get(rpDjconstruccion.getTecho()));
		cmbComponenteConstruccion3.setValue(mapIRjComponenteConstruccion3.get(rpDjconstruccion.getPisos()));
		
		cmbComponenteConstruccion4.setValue(mapIRjComponenteConstruccion4.get(rpDjconstruccion.getPuertas()));
		
		cmbComponenteConstruccion5.setValue(mapIRjComponenteConstruccion5.get(rpDjconstruccion.getRevestimientos()));
		cmbComponenteConstruccion6.setValue(mapIRjComponenteConstruccion6.get(rpDjconstruccion.getBannos()));
		cmbComponenteConstruccion7.setValue(mapIRjComponenteConstruccion7.get(rpDjconstruccion.getElectricos()));
		//referencia
		rpDjconstruccion.setDjId(construccion.getDjId());
		rpDjconstruccion.setConstruccionId(construccion.getConstruccionId());
		
		annoActualizacion = construccion.getAnnoActualizacion();
		annoConstruccion = construccion.getAnnoConstruccion();
	}
	
	private Boolean selUnidadMed;
	
	public Boolean getSelUnidadMed() {
		return selUnidadMed;
	}

	public void setSelUnidadMed(Boolean selUnidadMed) {
		this.selUnidadMed = selUnidadMed;
	}

	public void selectUnidadMedidaAcc(ValueChangeEvent event)
	{
		 HtmlComboBox combo=(HtmlComboBox) event.getComponent(); 
		 if(combo.getValue().toString().equalsIgnoreCase("--")){
			 selUnidadMed=Boolean.FALSE;
		 }
		 else{
			 selUnidadMed=Boolean.TRUE;
		 }
	}
	
	
	public void salir(){
		cerrar();
	}
	public void cerrar(){
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		Object session = externalContext.getSession(false);
		HttpSession httpSession = (HttpSession) session;
		httpSession.removeAttribute("construccionManaged");
	}

	public BigDecimal getBigDecimal(String value){
		BigDecimal result=new BigDecimal(0);
		try{
			Double temp=Double.valueOf(value);
		}catch(Exception e){//janeca3
			e.printStackTrace();
		}
		return result;
	} 
	
	public HtmlComboBox getCmbtiponivel() {
		return cmbtiponivel;
	}

	public void setCmbtiponivel(HtmlComboBox cmbtiponivel) {
		this.cmbtiponivel = cmbtiponivel;
	}

	public List<SelectItem> getLsttiponivel() {
		return lsttiponivel;
	}

	public void setLsttiponivel(List<SelectItem> lsttiponivel) {
		this.lsttiponivel = lsttiponivel;
	}
	
	public HtmlComboBox getCmbmes() {
		return cmbmes;
	}

	public void setCmbmes(HtmlComboBox cmbmes) {
		this.cmbmes = cmbmes;
	}

	public List<SelectItem> getLstmes() {
		return lstmes;
	}

	public void setLstmes(List<SelectItem> lstmes) {
		this.lstmes = lstmes;
	}
	
	public HtmlComboBox getCmbMaterialPredominante() {
		return cmbMaterialPredominante;
	}

	public void setCmbMaterialPredominante(HtmlComboBox cmbMaterialPredominante) {
		this.cmbMaterialPredominante = cmbMaterialPredominante;
	}

	public List<SelectItem> getLstMaterialPredominante() {
		return lstMaterialPredominante;
	}

	public void setLstMaterialPredominante(List<SelectItem> lstMaterialPredominante) {
		this.lstMaterialPredominante = lstMaterialPredominante;
	}

	public HtmlComboBox getCmbEstadoConservacion() {
		return cmbEstadoConservacion;
	}

	public void setCmbEstadoConservacion(HtmlComboBox cmbEstadoConservacion) {
		this.cmbEstadoConservacion = cmbEstadoConservacion;
	}

	public List<SelectItem> getLstEstadoConservacion() {
		return lstEstadoConservacion;
	}

	public void setLstEstadoConservacion(List<SelectItem> lstEstadoConservacion) {
		this.lstEstadoConservacion = lstEstadoConservacion;
	}

	public HtmlComboBox getCmbTipoDepreciacion() {
		return cmbTipoDepreciacion;
	}

	public void setCmbTipoDepreciacion(HtmlComboBox cmbTipoDepreciacion) {
		this.cmbTipoDepreciacion = cmbTipoDepreciacion;
	}

	public List<SelectItem> getLstTipoDepreciacion() {
		return lstTipoDepreciacion;
	}

	public void setLstTipoDepreciacion(List<SelectItem> lstTipoDepreciacion) {
		this.lstTipoDepreciacion = lstTipoDepreciacion;
	}

	public List<SelectItem> getLstUnidadMedidaAcc() {
		return lstUnidadMedidaAcc;
	}

	public void setLstUnidadMedidaAcc(List<SelectItem> lstUnidadMedidaAcc) {
		this.lstUnidadMedidaAcc = lstUnidadMedidaAcc;
	}
	
	public HtmlComboBox getCmbComponenteConstruccion1() {
		return cmbComponenteConstruccion1;
	}

	public void setCmbComponenteConstruccion1(
			HtmlComboBox cmbComponenteConstruccion1) {
		this.cmbComponenteConstruccion1 = cmbComponenteConstruccion1;
	}



	public HtmlComboBox getCmbComponenteConstruccion2() {
		return cmbComponenteConstruccion2;
	}

	public void setCmbComponenteConstruccion2(
			HtmlComboBox cmbComponenteConstruccion2) {
		this.cmbComponenteConstruccion2 = cmbComponenteConstruccion2;
	}



	public HtmlComboBox getCmbComponenteConstruccion3() {
		return cmbComponenteConstruccion3;
	}

	public void setCmbComponenteConstruccion3(
			HtmlComboBox cmbComponenteConstruccion3) {
		this.cmbComponenteConstruccion3 = cmbComponenteConstruccion3;
	}



	public HtmlComboBox getCmbComponenteConstruccion4() {
		return cmbComponenteConstruccion4;
	}

	public void setCmbComponenteConstruccion4(
			HtmlComboBox cmbComponenteConstruccion4) {
		this.cmbComponenteConstruccion4 = cmbComponenteConstruccion4;
	}



	public HtmlComboBox getCmbComponenteConstruccion5() {
		return cmbComponenteConstruccion5;
	}

	public void setCmbComponenteConstruccion5(
			HtmlComboBox cmbComponenteConstruccion5) {
		this.cmbComponenteConstruccion5 = cmbComponenteConstruccion5;
	}

	

	public HtmlComboBox getCmbComponenteConstruccion6() {
		return cmbComponenteConstruccion6;
	}

	public void setCmbComponenteConstruccion6(
			HtmlComboBox cmbComponenteConstruccion6) {
		this.cmbComponenteConstruccion6 = cmbComponenteConstruccion6;
	}



	public HtmlComboBox getCmbComponenteConstruccion7() {
		return cmbComponenteConstruccion7;
	}

	public void setCmbComponenteConstruccion7(
			HtmlComboBox cmbComponenteConstruccion7) {
		this.cmbComponenteConstruccion7 = cmbComponenteConstruccion7;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public List<SelectItem> getLstComponenteConstruccion1() {
		return lstComponenteConstruccion1;
	}

	public void setLstComponenteConstruccion1(
			List<SelectItem> lstComponenteConstruccion1) {
		this.lstComponenteConstruccion1 = lstComponenteConstruccion1;
	}

	public List<SelectItem> getLstComponenteConstruccion2() {
		return lstComponenteConstruccion2;
	}

	public void setLstComponenteConstruccion2(
			List<SelectItem> lstComponenteConstruccion2) {
		this.lstComponenteConstruccion2 = lstComponenteConstruccion2;
	}

	public List<SelectItem> getLstComponenteConstruccion3() {
		return lstComponenteConstruccion3;
	}

	public void setLstComponenteConstruccion3(
			List<SelectItem> lstComponenteConstruccion3) {
		this.lstComponenteConstruccion3 = lstComponenteConstruccion3;
	}

	public List<SelectItem> getLstComponenteConstruccion4() {
		return lstComponenteConstruccion4;
	}

	public void setLstComponenteConstruccion4(
			List<SelectItem> lstComponenteConstruccion4) {
		this.lstComponenteConstruccion4 = lstComponenteConstruccion4;
	}

	public List<SelectItem> getLstComponenteConstruccion5() {
		return lstComponenteConstruccion5;
	}

	public void setLstComponenteConstruccion5(
			List<SelectItem> lstComponenteConstruccion5) {
		this.lstComponenteConstruccion5 = lstComponenteConstruccion5;
	}

	public List<SelectItem> getLstComponenteConstruccion6() {
		return lstComponenteConstruccion6;
	}

	public void setLstComponenteConstruccion6(
			List<SelectItem> lstComponenteConstruccion6) {
		this.lstComponenteConstruccion6 = lstComponenteConstruccion6;
	}

	public List<SelectItem> getLstComponenteConstruccion7() {
		return lstComponenteConstruccion7;
	}

	public void setLstComponenteConstruccion7(
			List<SelectItem> lstComponenteConstruccion7) {
		this.lstComponenteConstruccion7 = lstComponenteConstruccion7;
	}
	public RpDjconstruccion getRpDjconstruccion() {
		return rpDjconstruccion;
	}

	public void setRpDjconstruccion(RpDjconstruccion rpDjconstruccion) {
		this.rpDjconstruccion = rpDjconstruccion;
	}
	public Mensaje getMensaje() {
		return mensaje;
	}

	public void setMensaje(Mensaje mensaje) {
		this.mensaje = mensaje;
	}
	
	public HashMap<Integer, String> getMapIRjTipoDepreciacion() {
		return mapIRjTipoDepreciacion;
	}

	public void setMapIRjTipoDepreciacion(
			HashMap<Integer, String> mapIRjTipoDepreciacion) {
		this.mapIRjTipoDepreciacion = mapIRjTipoDepreciacion;
	}
	public Boolean getIsEditable() {
		return (getRpDjconstruccion().getConstruccionId()!=null&&getRpDjconstruccion().getConstruccionId()>0)?Boolean.TRUE:Boolean.FALSE;
	}
	public HashMap<Integer, String> getMapIRpTipoNivel() {
		return mapIRpTipoNivel;
	}

	public void setMapIRpTipoNivel(HashMap<Integer, String> mapIRpTipoNivel) {
		this.mapIRpTipoNivel = mapIRpTipoNivel;
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

	public Integer getAnnoActualProcesamiento() {
		return annoActualProcesamiento;
	}

	public void setAnnoActualProcesamiento(Integer annoActualProcesamiento) {
		this.annoActualProcesamiento = annoActualProcesamiento;
	}

	public Integer getAnnoActualizacion() {
		return annoActualizacion;
	}

	public void setAnnoActualizacion(Integer annoActualizacion) {
		this.annoActualizacion = annoActualizacion;
	}

	public Integer getAnnoConstruccion() {
		return annoConstruccion;
	}

	public void setAnnoConstruccion(Integer annoConstruccion) {
		this.annoConstruccion = annoConstruccion;
	}
}
