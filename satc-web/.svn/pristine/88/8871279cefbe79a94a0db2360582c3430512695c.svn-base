package com.sat.sisat.predial.managed;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.richfaces.component.html.HtmlComboBox;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.FacesUtil;
import com.sat.sisat.common.util.Util;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.persistence.entity.GnDenominacionUrbana;
import com.sat.sisat.persistence.entity.GnTipoAgrupamiento;
import com.sat.sisat.persistence.entity.GnTipoDenoUrbana;
import com.sat.sisat.persistence.entity.GnTipoEdificacion;
import com.sat.sisat.persistence.entity.GnTipoIngreso;
import com.sat.sisat.persistence.entity.GnTipoInterior;
import com.sat.sisat.persistence.entity.GnTipoVia;
import com.sat.sisat.persistence.entity.GnUbicacion;
import com.sat.sisat.persistence.entity.GnVia;
import com.sat.sisat.persistence.entity.RpDjdireccion;
import com.sat.sisat.persistence.entity.Ubigeo;
import com.sat.sisat.predial.business.RegistroPrediosBoRemote;
import com.sat.sisat.predial.dto.FindDireccion;
import com.sat.sisat.predial.dto.FindGnDenUrbana;
import com.sat.sisat.predial.dto.UbicacionDTO;

@ManagedBean
@ViewScoped
public class UbicacionPredioUrbanoManaged extends BaseManaged {
	private String direccionPredio=""; 
	
	@EJB
	RegistroPrediosBoRemote registroPrediosBo;
	
	private Ubigeo ubigeo;
	
	private HtmlComboBox cmbtipoedificio;
	private List<SelectItem> lsttipoedificio;
	private HashMap<String, Integer> mapGnTipoEdificacion=new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIGnTipoEdificacion=new HashMap<Integer,String>();
	private String cmbvaluetipoedificio;
	private Integer tipoedificioId;
	
	private HtmlComboBox cmbtipointerior;
	private List<SelectItem> lsttipointerior;
	private HashMap<String, Integer> mapGnTipoInterior=new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIGnTipoInterior=new HashMap<Integer, String>();
	private String cmbvaluetipointerior;
	private Integer tipointerioresId;
	
	private HtmlComboBox cmbtipoingreso;
	private List<SelectItem> lsttipoingreso;
	private HashMap<String, Integer> mapGnTipoIngreso=new HashMap<String, Integer>();
	private HashMap<Integer,String> mapIGnTipoIngreso=new HashMap<Integer,String>();
	private String cmbvaluetipoingreso;
	
	private HtmlComboBox cmbtipoagrupamiento;
	private List<SelectItem> lsttipoagrupamiento;
	private HashMap<String, Integer> mapGnTipoAgrupamiento=new HashMap<String, Integer>();
	private HashMap<Integer,String> mapIGnTipoAgrupamiento=new HashMap<Integer,String>();
	private String cmbvaluetipoagrupamiento;
	
	private HashMap<Integer,String> mapIGnTipoVia=new HashMap<Integer,String>();
	private HashMap<Integer,String> mapIGnTipoDenUrbana=new HashMap<Integer,String>();
	
	private String tipoVia;
	private String nombreVia;
	private Integer viaId;//ubicacion_Id
	private Integer ubicacionId;//ubicacion_Id
	
	private String tipoDenUrbana;
	private String nombreDenUrbana;
	private Integer denoUrbanaId;
	
	private String codigoSectorCatastral;
	
	private RpDjdireccion rpDjdireccion;
	
	private Boolean selEdificio=Boolean.FALSE;
	private Boolean selInterior=Boolean.FALSE;
	private Boolean selIngreso=Boolean.FALSE;
	private Boolean selAgrupamiento=Boolean.FALSE;
	
	private Integer numeroCuadra;
	private Integer ladoCuadra;
	
	private String denominacionSector;
	
	private Integer annoDj;
	
	private Boolean disabled;
	
	public UbicacionPredioUrbanoManaged(){
		
	}
	
	@PostConstruct
	public void inicio(){
		try{
			ubigeo=new Ubigeo();
			ubigeo.setDepartamento("Cajamarca");
			ubigeo.setDistrito("Cajamarca");
			ubigeo.setProvincia("Cajamarca");
			
			init();
			String RECORD_STATUS=(String)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "RECORD_STATUS");
			if(RECORD_STATUS!=null&&RECORD_STATUS.equals(Constante.RECORD_STATUS_UPDATE)){
				Integer DjId=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId");
				if(DjId!=null){
					
					rpDjdireccion=registroPrediosBo.getRpDjDireccion(DjId);
					if(rpDjdireccion!=null){
						setCmbvaluetipoedificio(mapIGnTipoEdificacion.get(rpDjdireccion.getTipoEdificacionId()));
						setCmbvaluetipointerior(mapIGnTipoInterior.get(rpDjdireccion.getTipoInteriorId()));
						setCmbvaluetipoingreso(mapIGnTipoIngreso.get(rpDjdireccion.getTipoIngresoId()));
						setCmbvaluetipoagrupamiento(mapIGnTipoAgrupamiento.get(rpDjdireccion.getTipoAgrupamientoId()));
						
						if(rpDjdireccion.getViaId()!=null&&rpDjdireccion.getViaId()>Constante.RESULT_PENDING){
							GnVia via=registroPrediosBo.getGnVia(rpDjdireccion.getViaId());
							setViaId(getRpDjdireccion().getViaId());
							setNombreVia(via.getDescripcion());
							setTipoVia(via.getDescripcionTipoVia());
						}
						
						if(rpDjdireccion.getDenoId()!=null&&rpDjdireccion.getDenoId()>Constante.RESULT_PENDING){
							GnDenominacionUrbana deno=registroPrediosBo.getGnDenominacionUrbana(rpDjdireccion.getDenoId());
							setDenoUrbanaId(deno.getDenoId());
							setNombreDenUrbana(deno.getDescripcion());
							setTipoDenUrbana(deno.getDescripcionTipoDeno());
						}
						
						setCodigoSectorCatastral(rpDjdireccion.getSectorCatastral());
					}else{
						rpDjdireccion=new RpDjdireccion();
						rpDjdireccion.setDireccionId(Constante.RESULT_PENDING);
					}
				}
			}else{
				
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	public void setProperty(){
		try{
			if(rpDjdireccion.getViaId()!=null){
				GnVia via=registroPrediosBo.getGnVia(rpDjdireccion.getViaId());
				setViaId(getRpDjdireccion().getViaId());
				setNombreVia(via.getDescripcion());
				setTipoVia(via.getDescripcionTipoVia());	
			}
			
			if(rpDjdireccion.getDenoId()!=null&&rpDjdireccion.getDenoId()>Constante.RESULT_PENDING){
				GnDenominacionUrbana deno=registroPrediosBo.getGnDenominacionUrbana(rpDjdireccion.getDenoId());
				setDenoUrbanaId(deno.getDenoId());
				setNombreDenUrbana(deno.getDescripcion());
				setTipoDenUrbana(deno.getDescripcionTipoDeno());	
			}
			
			if(rpDjdireccion.getTipoEdificacionId()!=null&&rpDjdireccion.getTipoEdificacionId()>Constante.RESULT_PENDING){
				setCmbvaluetipoedificio(mapIGnTipoEdificacion.get(rpDjdireccion.getTipoEdificacionId()));	
				setSelEdificio(Boolean.TRUE);
			}else{
				setCmbvaluetipoedificio("--");
		        setSelEdificio(Boolean.FALSE);
			}
			
			if(rpDjdireccion.getTipoInteriorId()!=null&&rpDjdireccion.getTipoInteriorId()>Constante.RESULT_PENDING){
				setCmbvaluetipointerior(mapIGnTipoInterior.get(rpDjdireccion.getTipoInteriorId()));
				setSelInterior(Boolean.TRUE);
			}else{
				setCmbvaluetipointerior("--");
				setSelInterior(Boolean.FALSE);
			}
			
			if(rpDjdireccion.getTipoIngresoId()!=null&&rpDjdireccion.getTipoIngresoId()>Constante.RESULT_PENDING){
				setCmbvaluetipoingreso(mapIGnTipoIngreso.get(rpDjdireccion.getTipoIngresoId()));
				setSelIngreso(Boolean.TRUE);
			}else{
				setCmbvaluetipoingreso("--");
				setSelIngreso(Boolean.FALSE);
			}
			
			if(rpDjdireccion.getTipoAgrupamientoId()!=null&&rpDjdireccion.getTipoAgrupamientoId()>Constante.RESULT_PENDING){
				setCmbvaluetipoagrupamiento(mapIGnTipoAgrupamiento.get(rpDjdireccion.getTipoAgrupamientoId()));
				setSelAgrupamiento(Boolean.TRUE);
			}else{
				setCmbvaluetipoagrupamiento("--");
				setSelAgrupamiento(Boolean.FALSE);
			}
			
			GnUbicacion ubicacion=registroPrediosBo.getGnUbicacion(rpDjdireccion.getUbicacionId());
			setNumeroCuadra(ubicacion.getNumeroCuadra());
			setLadoCuadra(ubicacion.getLadoCuadra());
			setUbicacionId(ubicacion.getUbicacionId());
			
			setCodigoSectorCatastral(getRpDjdireccion().getSectorCatastral());
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public void init(){
		try{
	        //GnTipoEdificacion
	        List<GnTipoEdificacion> lstGnTipoEdificacion=registroPrediosBo.getAllGnTipoEdificacion();
			
			Iterator<GnTipoEdificacion> it = lstGnTipoEdificacion.iterator();  
			lsttipoedificio = new ArrayList<SelectItem>();
			 
	        while (it.hasNext()){  
	        	GnTipoEdificacion obj = it.next();  
	        	lsttipoedificio.add(new SelectItem(obj.getDescripcion(),String.valueOf(obj.getTipoEdificacionId())));
	        	mapGnTipoEdificacion.put(obj.getDescripcion(), obj.getTipoEdificacionId());
	        	mapIGnTipoEdificacion.put(obj.getTipoEdificacionId(),obj.getDescripcion());
	        }
	        
	        //GnTipoIngreso
	        List<GnTipoIngreso> lstGnTipoIngreso=registroPrediosBo.getAllGnTipoIngreso();
			
	        Iterator<GnTipoIngreso> it2 = lstGnTipoIngreso.iterator();  
			lsttipoingreso = new ArrayList<SelectItem>();
			 
	        while (it2.hasNext()){  
	            GnTipoIngreso obj = it2.next();  
	            lsttipoingreso.add(new SelectItem(obj.getDescripcion(),String.valueOf(obj.getTipoIngresoId())));
	            mapGnTipoIngreso.put(obj.getDescripcion(), obj.getTipoIngresoId());
	            mapIGnTipoIngreso.put(obj.getTipoIngresoId(),obj.getDescripcion());
	        }
	        //GnTipoInterior
	        List<GnTipoInterior> lstGnTipoInterior=registroPrediosBo.getAllGnTipoInterior();
			
			Iterator<GnTipoInterior> it3 = lstGnTipoInterior.iterator();  
			lsttipointerior = new ArrayList<SelectItem>();
			 
	        while (it3.hasNext()){  
	            GnTipoInterior obj = it3.next();  
	            lsttipointerior.add(new SelectItem(obj.getDescripcion(),String.valueOf(obj.getTipoInteriorId())));
	            mapGnTipoInterior.put(obj.getDescripcion(), obj.getTipoInteriorId());
	            mapIGnTipoInterior.put(obj.getTipoInteriorId(),obj.getDescripcion());
	        }
	        
	    	//GnTipoAgrupamiento
			List<GnTipoAgrupamiento> lstGnTipoAgrupamiento=registroPrediosBo.getAllGnTipoAgrupamiento();
			
			Iterator<GnTipoAgrupamiento> it4 = lstGnTipoAgrupamiento.iterator();  
			lsttipoagrupamiento = new ArrayList<SelectItem>();
			 
	        while (it4.hasNext()){
	        	GnTipoAgrupamiento obj = it4.next();  
	            lsttipoagrupamiento.add(new SelectItem(obj.getDescripcion(),String.valueOf(obj.getTipoAgrupamientoId())));
	            mapGnTipoAgrupamiento.put(obj.getDescripcion(), obj.getTipoAgrupamientoId());
	            mapIGnTipoAgrupamiento.put(obj.getTipoAgrupamientoId(),obj.getDescripcion());
	        }
	        
	        
	      //GnTipoVia
			List<GnTipoVia> lstGnTipoVia=registroPrediosBo.getAllGnTipoVia();
			
			Iterator<GnTipoVia> ittv = lstGnTipoVia.iterator();  
			 
	        while (ittv.hasNext()){
	        	GnTipoVia obj = ittv.next();  
	            mapIGnTipoVia.put(obj.getTipoViaId(),obj.getDescripcion());
	        }
	        
	        //GnTipoDenUrbana
	        List<GnTipoDenoUrbana> lstGnTipoDen=registroPrediosBo.getAllGnTipoDenoUrbana();
			
			Iterator<GnTipoDenoUrbana> ittd = lstGnTipoDen.iterator();  
			 
	        while (ittv.hasNext()){
	        	GnTipoDenoUrbana obj = ittd.next();  
	            mapIGnTipoDenUrbana.put(obj.getTipoDenoId(),obj.getDescripcion());
	        }
	        
	        //getCmbtipoedificio().setValue("--");
	        setCmbvaluetipoedificio("--");
	        setSelEdificio(Boolean.FALSE);
	        
	        //getCmbtipoagrupamiento().setValue("--");
	        setCmbvaluetipoagrupamiento("--");
			setSelAgrupamiento(Boolean.FALSE);
			
			//getCmbtipoingreso().setValue("--");
			setCmbvaluetipoingreso("--");
			setSelIngreso(Boolean.FALSE);
			
			//getCmbtipointerior().setValue("--");
			setCmbvaluetipointerior("--");
			setSelInterior(Boolean.FALSE);
			
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
			Integer PredioId=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextPredioId");
			
			if(NextDjId!=null){
				if(validar()){
					 /** * 17-09-2014 : Se agregó (al método:existeDireccion) el tipo de edificación,descripción de la misma,tipo de interior y referencia, a solicitud de Dany Alfaro, caso 111160(Cod:Contribuyente),validación de dichos registros*/
					if(!existeDireccion(PredioId,getViaId(),getRpDjdireccion().getNumero(),getRpDjdireccion().getLetra(),getRpDjdireccion().getNumero2(),getRpDjdireccion().getLetra2(),tipoedificioId,getRpDjdireccion().getNombreEdificiacion(),tipointerioresId,getRpDjdireccion().getNumeroInterior(),getRpDjdireccion().getReferencia())){
						getRpDjdireccion().setDjId(NextDjId);
						getRpDjdireccion().setTipoDireccion(Constante.TIPO_PREDIO_URBANO);
						
						String tipoedificio=(String)getCmbtipoedificio().getValue();
						String tipointerior=(String)getCmbtipointerior().getValue();
						String tipoingreso=(String)getCmbtipoingreso().getValue();
						String tipoagrupamiento=(String)getCmbtipoagrupamiento().getValue();
						
						Integer tipoEdificacionId=mapGnTipoEdificacion.get(tipoedificio);
						Integer tipoInteriorId=mapGnTipoInterior.get(tipointerior);
						Integer tipoIngresoId=mapGnTipoIngreso.get(tipoingreso);
						Integer tipoAgrupamientoId=mapGnTipoAgrupamiento.get(tipoagrupamiento);
						
						getRpDjdireccion().setTipoEdificacionId(tipoEdificacionId);
						getRpDjdireccion().setTipoInteriorId(tipoInteriorId);
						getRpDjdireccion().setTipoIngresoId(tipoIngresoId);
						getRpDjdireccion().setTipoAgrupamientoId(tipoAgrupamientoId);
						
						if(getViaId()!=null){
							GnVia via=registroPrediosBo.getGnVia(getViaId());
							getRpDjdireccion().setViaId(getViaId());
							getRpDjdireccion().setVia(via);
							getRpDjdireccion().setUbicacionId(getUbicacionId());
							
							//cchaucca 06/06/2012 INI corregido el registro del valor arancel para el calculo
							Double valorArancel=registroPrediosBo.getValorArancel(getUbicacionId(),getAnnoDj());
							if(valorArancel!=null){
								getRpDjdireccion().setValorArancel(BigDecimal.valueOf(valorArancel));
							}else{
								getRpDjdireccion().setValorArancel(BigDecimal.ZERO);
							}
							//cchaucca 06/06/2012 FIN 
						}
						if(getDenoUrbanaId()!=null&&getDenoUrbanaId()!=Constante.RESULT_PENDING){
							GnDenominacionUrbana deno=registroPrediosBo.getGnDenominacionUrbana(getDenoUrbanaId());
							getRpDjdireccion().setDenominacionUrbana(deno);
							getRpDjdireccion().setDenoId(getDenoUrbanaId());
							getRpDjdireccion().setValorArancel(null);
						}
						
						getRpDjdireccion().setUbigeo(ubigeo);
						getRpDjdireccion().setSectorCatastral(getCodigoSectorCatastral());
						
						getRpDjdireccion().setEstado(Constante.ESTADO_ACTIVO);
						
						//cc: getRpDjdireccion().setFechaRegistro(DateUtil.getCurrentDate());
						//cc: getRpDjdireccion().setUsuarioId(Constante.USUARIO_ID);
						//cc: getRpDjdireccion().setTerminal(Constante.TERMINAL);
						
						String DireccionCompleta=registroPrediosBo.ConcatenarDomicilioUrbano(getRpDjdireccion(), mapIGnTipoEdificacion, mapIGnTipoInterior, mapIGnTipoIngreso, mapIGnTipoAgrupamiento,mapIGnTipoVia,mapIGnTipoDenUrbana,getDenominacionSector(),getNumeroCuadra());
						
						getRpDjdireccion().setDireccionCompleta(DireccionCompleta);
						
						int resul=registroPrediosBo.guardarRpDjdireccion(getRpDjdireccion());	
						if(resul>0){
							int direccionId=registroPrediosBo.getDjDireccionActive(NextDjId);
							getRpDjdireccion().setDireccionId(direccionId);
							getRpDjdireccion().setUbigeo(ubigeo);
							
							if(direccionId>0){
								registroPrediosBo.desactiveDirecciones(NextDjId,direccionId);
								RegistroPredioManaged registroPredio = (RegistroPredioManaged) getManaged("registroPredioManaged");
								if(registroPredio!=null){
									registroPredio.setDireccionPredio(DireccionCompleta);
									registroPredio.getRpDjpredial().setDescDomicilio(getRpDjdireccion().getDireccionCompleta());
									registroPredio.loadUbicacion();
								}else{
									//null
								}
							}
						}else{
							//no se registro
						}
					}
				}
			}else{
				//no tiene  Dj id
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public boolean existeDireccion(Integer PredioId,Integer idVia,String numero1, String letra1,String numero2, String letra2,Integer edificacionId,String edificacion,Integer interiorId,String interior,String referencia){       /** * 17-09-2014 : Se agregó el tipo de edificacion y la descripcion de la misma, a solicitud de Dany Alfaro, caso 111160(Cod:Contribuyente)*/ 
		boolean existe=false;
		try{
			//cchaucca 12/07/2012 permitir el registro de S/N sin validar su existencia
			//Cambio solicitado por el Ing Jaime Sobrados
			if(!numero1.trim().toUpperCase().equals("S/N")){
				FindDireccion direccion=registroPrediosBo.verificaDireccion(PredioId, idVia, numero1, letra1, numero2, letra2,edificacionId,edificacion,interiorId,interior,referencia);
				if(direccion!=null){
					Date fecha=new Date(direccion.getFechaDeclaracion().getTime());
					existe=true;
					WebMessages.messageError("La dirección indicada se encuentra registra para el predio "+ direccion.getPredioId()+" / "+ direccion.getCodigoAnterior()+" para la persona: "+direccion.getPersonaId()+" con fecha de inscripción "+DateUtil.convertDateToString(fecha)+" ["+direccion.getDescDomicilio()+"]");
				}
			}
		}catch(Exception e ){
			existe=true;
			WebMessages.messageFatal(e);
		}
		return existe;
	}
	
	public boolean validar(){
		boolean validation=false;
		if(getViaId()!=null&&getViaId()>0){
			if(getRpDjdireccion().getNumero()!=null&&getRpDjdireccion().getNumero().trim().length()>0){
				if(validaCuadra(getRpDjdireccion().getNumero(),getNumeroCuadra(),getLadoCuadra())){
					validation=true;
				}
			}else{
				WebMessages.messageError("Debe indicar el campo número 1");
			}
		}else if(getDenoUrbanaId()!=null&&getDenoUrbanaId()>0){
			validation=true;
		}else{
			WebMessages.messageError("Debe seleccionar la via o la denominación urbana del predio");
		}
		return validation;
	}
	
	public boolean validaCuadra(String valorCampo,Integer numeroCuadra,Integer ladoCuadra){
		try{
			if(valorCampo!=null){
				if(valorCampo.trim().toUpperCase().equals("S/N")){
					return true;
				}else if(numeroCuadra!=null&&numeroCuadra>0){
					if(Util.toLong(valorCampo)>0)
					{
						if(valorCampo.trim().length()>String.valueOf(numeroCuadra).length()){
							String cuadra=valorCampo.substring(0, String.valueOf(numeroCuadra).length());
							String numero=valorCampo.substring(String.valueOf(numeroCuadra).length(),valorCampo.length());
							
							if(Util.toInteger(cuadra)==numeroCuadra){
								if(ladoCuadra==2&&esImpar(Util.toInteger(numero))||//Lado Izquierdo, numeros impares
										ladoCuadra==1&&!esImpar(Util.toInteger(numero))){//Lado Derecho, numeros pares
									return true;
								}else{
									WebMessages.messageError("El campo número indicado no corresponde con el lado de la cuadra "+(ladoCuadra==2?" izquierda":" derecha"));
								}
							}else{
								WebMessages.messageError("El campo número indicado no corresponde con el número de cuadra "+numeroCuadra);
							}
						}else{
							WebMessages.messageError("El campo número indicado no corresponde con el número de cuadra "+numeroCuadra);
						}
					}else{
						WebMessages.messageError("El campo número 1 indicado debe ser un número correspondiente con el número de cuadra "+numeroCuadra+" o S/N");
					}
				}else{
					WebMessages.messageError("No se ha definido el número de cuadra de la ubicación indicada");
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
	
	public void setSelectedVia(UbicacionDTO currentItem){
		setTipoVia(currentItem.getTipoVia());
		setNombreVia(currentItem.getVia());
		setViaId(currentItem.getViaid());
		setUbicacionId(currentItem.getUbicacionId());
		setCodigoSectorCatastral(currentItem.getCodigoCatastral());
		setNumeroCuadra(currentItem.getNumeroCuadra());
		setLadoCuadra(currentItem.getLado());
		setDenominacionSector(currentItem.getSector());
	}
	
	public void setSelectedDenUrbana(FindGnDenUrbana currentItem){
		setTipoDenUrbana(currentItem.getGnTipoDenoUrbana().getDescripcion());
		setNombreDenUrbana(currentItem.getGnDenUrbana().getDescripcion());
		setDenoUrbanaId(currentItem.getGnDenUrbana().getDenoId());
		
	}
	
	
	public void salir(){
		limpiar();
	}
	
	public void limpiar(){
		rpDjdireccion=new RpDjdireccion();
		FacesUtil.closeSession("ubicacionPredioUrbanoManaged");
	}
	
	public void selectTipoEdificacion(ValueChangeEvent event)//ok
	{
		 HtmlComboBox combo=(HtmlComboBox) event.getComponent(); 
		 if(!combo.getValue().toString().equalsIgnoreCase("--")){
			 setSelEdificio(Boolean.TRUE);
			 tipoedificioId  = (Integer) mapGnTipoEdificacion.get(combo.getValue().toString());
		 }
		 else{
			 getRpDjdireccion().setNombreEdificiacion("");
			 getRpDjdireccion().setPiso("");
			 setSelEdificio(Boolean.FALSE);
		 }
	}
	
	public void selectTipoAgrupamiento(ValueChangeEvent event)
	{
		 HtmlComboBox combo=(HtmlComboBox) event.getComponent(); 
		 if(!combo.getValue().toString().equalsIgnoreCase("--")){
			 setSelAgrupamiento(Boolean.TRUE);
		 }
		 else{
			 getRpDjdireccion().setNombreAgrupamiento("");
			 setSelAgrupamiento(Boolean.FALSE);
		 }
	}
	
	public void selectTipoIngreso(ValueChangeEvent event)//ok
	{
		 HtmlComboBox combo=(HtmlComboBox) event.getComponent(); 
		 if(!combo.getValue().toString().equalsIgnoreCase("--")){
			 setSelIngreso(Boolean.TRUE);
		 }
		 else{
			 getRpDjdireccion().setNombreIngreso("");
			 setSelIngreso(Boolean.FALSE);
		 }
	}
	
	public void selectTipoInterior(ValueChangeEvent event)//ok
	{
		 HtmlComboBox combo=(HtmlComboBox) event.getComponent(); 
		 if(!combo.getValue().toString().equalsIgnoreCase("--")){
			 setSelInterior(Boolean.TRUE);
			 tipointerioresId  = (Integer) mapGnTipoInterior.get(combo.getValue().toString());
		 }
		 else{
			 getRpDjdireccion().setNumeroInterior("");
			 getRpDjdireccion().setLetraInterior("");
			 setSelInterior(Boolean.FALSE);
		 }
	}
	
	public void setParameter(){
		RegistroPredioManaged registroPredioManaged = (RegistroPredioManaged) getManaged("registroPredioManaged");	

		BuscarViaManaged buscarViaManaged = (BuscarViaManaged) getManaged("buscarViaManaged");
		buscarViaManaged.setOtroFrente(registroPredioManaged.isOtroFrente());
		buscarViaManaged.limpiar();
		
		String paramParent=getRequestParameter("paramParent");
		getSessionMap().put("paramParent", paramParent);
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

	public List<SelectItem> getLsttipoagrupamiento() {
		return lsttipoagrupamiento;
	}

	public void setLsttipoagrupamiento(List<SelectItem> lsttipoagrupamiento) {
		this.lsttipoagrupamiento = lsttipoagrupamiento;
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

	public HtmlComboBox getCmbtipoagrupamiento() {
		return cmbtipoagrupamiento;
	}

	public void setCmbtipoagrupamiento(HtmlComboBox cmbtipoagrupamiento) {
		this.cmbtipoagrupamiento = cmbtipoagrupamiento;
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
	public String getTipoDenUrbana() {
		return tipoDenUrbana;
	}

	public void setTipoDenUrbana(String tipoDenUrbana) {
		this.tipoDenUrbana = tipoDenUrbana;
	}

	public String getNombreDenUrbana() {
		return nombreDenUrbana;
	}

	public void setNombreDenUrbana(String nombreDenUrbana) {
		this.nombreDenUrbana = nombreDenUrbana;
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

	public String getCmbvaluetipoagrupamiento() {
		return cmbvaluetipoagrupamiento;
	}

	public void setCmbvaluetipoagrupamiento(String cmbvaluetipoagrupamiento) {
		this.cmbvaluetipoagrupamiento = cmbvaluetipoagrupamiento;
	}
	public Integer getViaId() {
		return viaId;
	}

	public void setViaId(Integer viaId) {
		this.viaId = viaId;
	}

	public Integer getDenoUrbanaId() {
		return denoUrbanaId;
	}

	public void setDenoUrbanaId(Integer denoUrbanaId) {
		this.denoUrbanaId = denoUrbanaId;
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
	public String getCodigoSectorCatastral() {
		return codigoSectorCatastral;
	}

	public void setCodigoSectorCatastral(String codigoSectorCatastral) {
		this.codigoSectorCatastral = codigoSectorCatastral;
	}
	public Integer getUbicacionId() {
		return ubicacionId;
	}

	public void setUbicacionId(Integer ubicacionId) {
		this.ubicacionId = ubicacionId;
	}
	
	public Integer getNumeroCuadra() {
		return numeroCuadra;
	}

	public void setNumeroCuadra(Integer numeroCuadra) {
		this.numeroCuadra = numeroCuadra;
	}
	public String getDenominacionSector() {
		return denominacionSector;
	}

	public void setDenominacionSector(String denominacionSector) {
		this.denominacionSector = denominacionSector;
	}
	
	public Integer getAnnoDj() {
		return annoDj;
	}

	public void setAnnoDj(Integer annoDj) {
		this.annoDj = annoDj;
	}
	
	public Integer getLadoCuadra() {
		return ladoCuadra;
	}

	public void setLadoCuadra(Integer ladoCuadra) {
		this.ladoCuadra = ladoCuadra;
	}
	
	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public Integer getTipoedificioId() {
		return tipoedificioId;
	}

	public void setTipoedificioId(Integer tipoedificioId) {
		this.tipoedificioId = tipoedificioId;
	}

	public Integer getTipointerioresId() {
		return tipointerioresId;
	}

	public void setTipointerioresId(Integer tipointerioresId) {
		this.tipointerioresId = tipointerioresId;
	}


}
