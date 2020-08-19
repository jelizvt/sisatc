package com.sat.sisat.predial.managed;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.FacesUtil;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.persistence.entity.GnNotaria;
import com.sat.sisat.persistence.entity.MpPredio;
import com.sat.sisat.persistence.entity.RjDocuAnexo;
import com.sat.sisat.persistence.entity.RpDjarbitrio;
import com.sat.sisat.persistence.entity.RpDjconstruccion;
import com.sat.sisat.persistence.entity.RpDjdireccion;
import com.sat.sisat.persistence.entity.RpDjpredial;
import com.sat.sisat.persistence.entity.RpDjuso;
import com.sat.sisat.persistence.entity.RpDjusoDetalle;
import com.sat.sisat.persistence.entity.RpInstalacionDj;
import com.sat.sisat.persistence.entity.RpOtrosFrente;
import com.sat.sisat.persistence.entity.RpTipoObra;
import com.sat.sisat.persistence.entity.RpTipoObraPeriodo;
import com.sat.sisat.persistence.entity.RvMotivoDescargo;
import com.sat.sisat.persona.managed.RegistroPersonaManaged;
import com.sat.sisat.predial.business.CalculoPredialBoRemote;
import com.sat.sisat.predial.business.RegistroPrediosBoRemote;
import com.sat.sisat.predial.dto.FindMpPersona;
import com.sat.sisat.predial.dto.FindRpDjPredial;
import com.sat.sisat.predial.dto.PreliminarDescargoPredDTO;
import com.sat.sisat.vehicular.business.VehicularBoRemote;
import com.sat.sisat.vehicular.cns.ReusoFormCns;
import com.sat.sisat.vehicular.dto.AnexosDeclaVehicDTO;
import com.sat.sisat.vehicular.dto.BuscarPersonaDTO;
import com.sat.sisat.vehicular.dto.DocumentoSustentatorioDTO;
import com.sat.sisat.vehicular.managed.BuscarPersonaManaged;

@ManagedBean
@ViewScoped
public class DescargoPredialManaged extends BaseManaged {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7673535581117544078L;

	@EJB
	RegistroPrediosBoRemote registroPrediosBo;
	
	@EJB
	CalculoPredialBoRemote calculoPredialBo;
	
	@EJB
	VehicularBoRemote vehicularBo;
	
	private String fechaDeclaracion;
	private String motivoDescargo;//
	private Double porcentajePropiedad;//
	private Double area;//
	private String glosa;
	private String tipoPredio;
	private String codigoPredio;
	private String direccion;
	private String condicionPropiedad;
	private String selectedNotaria;//
	private String tipoDescargo;//
	
	private BigDecimal areaMatriz;
	private BigDecimal areaTransferida;
	private BigDecimal areaRestante;
	private BigDecimal porcentajeMatriz;
	private BigDecimal porcentajeTransferido;
	private BigDecimal porcentajeRestante;
	
	private List<SelectItem> lstMotivoDescargo=new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRvMotivoDescargo = new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIRvMotivoDescargo = new HashMap<Integer, String>();
	
	private List<BuscarPersonaDTO> lstTransferentes = new ArrayList<BuscarPersonaDTO>();//
	private BuscarPersonaDTO transferente = new BuscarPersonaDTO();//
	
	private List<DocumentoSustentatorioDTO> lstDocSusten = new ArrayList<DocumentoSustentatorioDTO>();
	private List<AnexosDeclaVehicDTO> lstAnexos = new ArrayList<AnexosDeclaVehicDTO>();//
	
	private List<SelectItem> lstNotarias = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapGnNotaria = new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIGnNotaria = new HashMap<Integer, String>();
	
	//private HashMap<String, Integer> mapRpTipDocIdentidad = new HashMap<String, Integer>();
	
	private boolean datosCorrectos = false;
	private String estadoDecla;
	
	private boolean descargado = false;
	
	private Integer djId;
	
	private java.util.Date  fechaDescargo;
	
	private String formaAdquisicion;
	
	public DescargoPredialManaged(){
		
	}
	
	@PostConstruct
	public void init(){
		try{
			List<RvMotivoDescargo> lstAd = new ArrayList<RvMotivoDescargo>();
			lstAd = vehicularBo.getAllRvMotivoDescargo();
			Iterator<RvMotivoDescargo> itAd = lstAd.iterator();
			while (itAd.hasNext()) {
				RvMotivoDescargo objAd = itAd.next();
				lstMotivoDescargo.add(new SelectItem(objAd.getDescripcion()));
				mapRvMotivoDescargo.put(objAd.getDescripcion(),objAd.getMotivoDescargoId());
				mapIRvMotivoDescargo.put(objAd.getMotivoDescargoId(),objAd.getDescripcion());
			}
			
			List<GnNotaria> lstN = new ArrayList<GnNotaria>();
			lstN = vehicularBo.getAllGnNotaria();
			Iterator<GnNotaria> itN = lstN.iterator();
			while (itN.hasNext()) {
				GnNotaria objN = itN.next();
				lstNotarias.add(new SelectItem(objN.getNombreNotaria()));
				mapGnNotaria.put(objN.getNombreNotaria(), objN.getNotariaId());
				mapIGnNotaria.put(objN.getNotariaId(), objN.getNombreNotaria());
			}
			
			lstDocSusten = vehicularBo.getAllRvDocumentoSustentatorio();
			
			setFechaDeclaracion(DateUtil.convertDateToString(DateUtil.getCurrentDate()));
			
			FindRpDjPredial findRpDjPredial=(FindRpDjPredial)getSessionMap().get("findRpDjPredial");
			
			setTipoPredio(findRpDjPredial.getTipoPredio());
			setCodigoPredio(String.valueOf(findRpDjPredial.getPredioId()));
			setDireccion(findRpDjPredial.getDireccionCompleta());
			setCondicionPropiedad(findRpDjPredial.getCondicionPropiedad());
			setPorcentajePropiedad(findRpDjPredial.getPorcPropiedad().doubleValue());
			if(findRpDjPredial.getTipoPredio().equals(Constante.TIPO_PREDIO_URBANO)){
				setArea(findRpDjPredial.getAreaTerreno());
				setAreaMatriz(BigDecimal.valueOf(findRpDjPredial.getAreaTerreno()));
			}else{//Rustico
				setArea(findRpDjPredial.getAreaTerrenoHas());
				setAreaMatriz(BigDecimal.valueOf(findRpDjPredial.getAreaTerrenoHas()));
			}
			
			setFormaAdquisicion("P");
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	public void loadDescargo(){
		try{
			Integer djId=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId");
			if(djId!=null&&djId>0){
				RpDjpredial djpredio=registroPrediosBo.getRpDjpredial(djId);
				setMotivoDescargo(mapIRvMotivoDescargo.get(djpredio.getMotivoDescargoId()));
				setPorcentajePropiedad(djpredio.getPorcPropiedad().doubleValue());
				setGlosa(djpredio.getGlosa());
				setSelectedNotaria(mapIGnNotaria.get(djpredio.getNotariaId()));
				
				lstTransferentes=registroPrediosBo.getTransferentePropiedad(djId,Constante.TIPO_TRANSFERENCIA_ADQUIRIENTE);
				lstAnexos=registroPrediosBo.getDocumentosAnexos(djId);
				
				Integer descargoId=djpredio.getMotivoDeclaracionId();
				if(descargoId==Constante.MOTIVO_DECLARACION_DESCARGO){
					setDescargado(Boolean.TRUE);
				}else{
					setDescargado(Boolean.FALSE);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void agregarAdquiriente(ActionEvent ev) {
		getBuscarPersonaManaged().setPantallaUso(ReusoFormCns.DESCARGO_PREDIAL);
		getBuscarPersonaManaged().setDestinoRefresh("tblTransferentes");
	}
	
	public void nuevoAdquiriente(){
		String destinoRefresh = FacesUtil.getRequestParameter("destinoRefresh");
		RegistroPersonaManaged registroPersonaManaged=(RegistroPersonaManaged)getManaged("registroPersonaManaged");
		registroPersonaManaged.setPantallaUso(ReusoFormCns.DESCARGO_PREDIAL);
		registroPersonaManaged.setDestinoRefresh(destinoRefresh);
		registroPersonaManaged.salirPersonaBasico();
	}
	
	public void loadLstTransferentes(BuscarPersonaDTO adquiriente){
		lstTransferentes.add(adquiriente);
	}
	
	public void eliminarTransfDeLista(ActionEvent ev) {
		try {
			UIComponent comp = ev.getComponent().getParent().getParent();
			if (comp != null) {
				UIData uiData = (UIData) comp;
				BuscarPersonaDTO bpd = (BuscarPersonaDTO) uiData.getRowData();
				lstTransferentes.remove(bpd);
			}
		} catch (Exception ex) {
			// TODO : Controller exception
		}
	}
	public void mostrarPanelDocSustent(ActionEvent ev) {
		lstAnexos = new ArrayList<AnexosDeclaVehicDTO>();
		for (DocumentoSustentatorioDTO dsd : lstDocSusten) {
			dsd.setGlosa(null);
			dsd.setSelected(false);
		}
	}
	
	public void eliminarAnexoDeLista(ActionEvent ev) {
		try {
			UIComponent comp = ev.getComponent().getParent().getParent();
			if (comp != null) {
				UIData uiData = (UIData) comp;
				AnexosDeclaVehicDTO adv = (AnexosDeclaVehicDTO) uiData.getRowData();
				lstAnexos.remove(adv);
			}
		} catch (Exception ex) {
			// TODO : Controller exception
		}
	}
	
	public void verPreview(ActionEvent ev) {
		if (validaDatosNulos()) {
			
			Double cantidadPropiedad=new Double(0);
			if(formaAdquisicion.equals("M")){
				cantidadPropiedad=area;
			}else{
				cantidadPropiedad=porcentajePropiedad;
			}
				
			if (cantidadDescargo()<=cantidadPropiedad) {
				cargaDatosPreliminar();
				datosCorrectos = true;
				estadoDecla = Constante.ESTADO_PENDIENTE;
			}else{
				datosCorrectos = false;
				if(formaAdquisicion.equals("M")){
					addErrorMessage("El area descargada es mayor al Area de terreno de propiedad original");
				}else{
					addErrorMessage("El porcentaje descargada es mayor al Porcentaje de propiedad original");
				}
			}
		}
	}
	
	private void primerCalculoAreas(){
		if(formaAdquisicion.equals("M")){			
			
			areaMatriz = BigDecimal.valueOf(area);
			areaTransferida = BigDecimal.valueOf(cantidadDescargo());
			areaRestante = areaMatriz.subtract(areaTransferida);		
			
			porcentajeMatriz = BigDecimal.valueOf(porcentajePropiedad);
			porcentajeTransferido = new BigDecimal(0);
			porcentajeRestante = BigDecimal.valueOf(porcentajePropiedad);
			
		}else{//por defecto en Porcentaje
			areaMatriz = BigDecimal.valueOf(area);
			areaTransferida = new BigDecimal(0);
			areaRestante = BigDecimal.valueOf(area);
			
			porcentajeMatriz = BigDecimal.valueOf(porcentajePropiedad);
			porcentajeTransferido = BigDecimal.valueOf(cantidadDescargo());
			porcentajeRestante = porcentajeMatriz.subtract(porcentajeTransferido);
		}
	}
	
	private void cargaDatosPreliminar() {
		PreliminarDescargoPredDTO pre = new PreliminarDescargoPredDTO();
		FindMpPersona contribuyente=getSessionManaged().getContribuyente();
		//Datos del Contribuyente
		pre.setPersonaId(contribuyente.getPersonaId());
		pre.setNombreRazonSocial(contribuyente.getApellidosNombres()==null?contribuyente.getRazonSocial():contribuyente.getApellidosNombres());
		pre.setTipoDocumento(contribuyente.getTipoDocumentoIdentidad());
		pre.setNumeroDocumento(contribuyente.getNroDocuIdentidad());
			
		//Datos de la declaración
		pre.setFechaDeclaracion(getFechaDeclaracion());
		pre.setFechaDescargo(DateUtil.convertDateToString(fechaDescargo));
		pre.setMotivoDeclaracion(getMsg("rv.descargo"));
		pre.setMotivoDescargo(getMotivoDescargo());
		pre.setGlosa(getGlosa());
		pre.setFormaAdquisicion(formaAdquisicion);
		
		primerCalculoAreas();
		pre.setArea(areaRestante.doubleValue());		
		pre.setAreaMatriz(areaMatriz);
		pre.setAreaTransferida(areaTransferida);
		pre.setPorcentajePropiedad(porcentajeRestante.doubleValue());
		pre.setPorcentajeMatriz(porcentajeMatriz);
		pre.setPorcentajeTransferido(porcentajeTransferido);
		
		
		//Datos del Predio
		pre.setTipoPredio(getTipoPredio());
		pre.setNumeroPredio(getCodigoPredio());
		pre.setDireccion(getDireccion());
		pre.setCondicionPropiedad(getCondicionPropiedad());
		
		//DATOS DE ADQUIRIENTE
		pre.setSelectedNotaria(getSelectedNotaria());
		
		getSessionMap().put("preliminarDescargoPredDTO", pre);
		getSessionMap().put("lstAdquirientesPred", lstTransferentes);
		getSessionMap().put("lstAnexosDescargoPred", lstAnexos);
		
		//usuario
		pre.setUsuario(getSessionManaged().getUsuarioLogIn().getNombreUsuario());
	}
	
	private boolean validaFechaTransferencia(FindRpDjPredial fRpDjPredial,java.util.Date fechaDescargo){
		Calendar now=Calendar.getInstance();
		now.setTime(fechaDescargo);
		
		int annioDescargo=now.get(Calendar.YEAR);
		
		if(DateUtil.getAnioActual()>=annioDescargo){
			return true;
		}
		return false;
	}
	
	private boolean validaDatosNulos() {
		boolean valido = true;
		try {
			Integer motivoDescargoId = mapRvMotivoDescargo.get(motivoDescargo.trim());
			
			if (!(motivoDescargoId!= null &&motivoDescargoId>Constante.RESULT_PENDING)) {
				valido = false;
				addErrorMessage(getMsg("rp.motivodescargo.required"));
			}
			if (motivoDescargoId.equals(Constante.MOTIVO_DESCARGO_VENTA)){
				if(selectedNotaria == null || selectedNotaria.trim().isEmpty()){
					valido = false;
					addErrorMessage(getMsg("rp.notaria.required"));
				}
			}
			if (porcentajePropiedad == null || porcentajePropiedad==0) {
				valido = false;
				addErrorMessage(getMsg("rp.porcentajepropiedad.required"));
			}
			if (lstAnexos.isEmpty()) {
				valido = false;
				addErrorMessage(getMsg("rp.noexistenanexos.required"));
			}
			if (motivoDescargoId.equals(Constante.MOTIVO_DESCARGO_VENTA)){
				if(lstTransferentes.isEmpty()) {
					valido = false;
					addErrorMessage(getMsg("rp.noexisteadquiriente.required"));
				}
			}
		} catch (Exception ex) {
			valido = false;
			addErrorMessage(getMsg("rv.errorvalidadatos"));
		}
		return valido;
	}
	
	//----DJ ANTERIOR
	//(1)Inactiva la Dj
	//(2)Genera la Dj de Descargo por el % especificado
	//----DJ NUEVA
	//(4)Genera las Djs con los porcentajes correspondientes (Pendiente de inscripcion)
	public void generarDJ() {
		try{
			//cargaDatosPreliminar();
			FindRpDjPredial findRpDjPredial=(FindRpDjPredial)getSessionMap().get("findRpDjPredial");
			if(validaFechaTransferencia(findRpDjPredial,getFechaDescargo())){
				if (validaDatosNulos()) {
					if(validaPoseeValores()){
						try {
							RpDjpredial rpDjDescargo=registroPrediosBo.getRpDjpredial(findRpDjPredial.getDjId());
							if(rpDjDescargo!=null){
								//Glosa
								rpDjDescargo.setGlosa(getGlosa());
								//Notaria
								Integer notariaId = mapGnNotaria.get(getSelectedNotaria());
								rpDjDescargo.setNotariaId(notariaId);
								
								//Area de terreno
								Double areaTerreno=new Double(0);
								if(rpDjDescargo.getTipoPredio().equals(Constante.TIPO_PREDIO_URBANO)){
									areaTerreno=rpDjDescargo.getAreaTerreno().doubleValue();	
								}else{
									areaTerreno=rpDjDescargo.getAreaTerrenoHas().doubleValue();
								}
								
								//Porcentaje de propiedad
								Double porcentajePropiedad=rpDjDescargo.getPorcPropiedad().doubleValue();
								
								Double cantidadPropiedad=new Double(0);
								Boolean xArea;
								if(formaAdquisicion.equals("M")){
									cantidadPropiedad=areaTerreno;
									setAreaMatriz(BigDecimal.valueOf(areaTerreno));
									xArea = Boolean.TRUE;									
								}else{//por defecto en Porcentaje
									cantidadPropiedad=porcentajePropiedad;
									setPorcentajeMatriz(BigDecimal.valueOf(porcentajePropiedad));
									xArea = Boolean.FALSE;
								}
								
								Double cantidadDescargo=new Double(0.0); 
								if(lstTransferentes.size()>0){
									//Si existe un listado de transferentes entonces se le calcula la cantidad a descargar
									cantidadDescargo=cantidadDescargo();	
								}else{
									//si no existe un listado de transferentes se descarga el total en porcentaje
									cantidadDescargo=porcentajePropiedad;
									cantidadPropiedad=porcentajePropiedad;
									setFormaAdquisicion("P");
								}
								
								if(cantidadDescargo<=cantidadPropiedad){
									Calendar calFechaDescargo=Calendar.getInstance();
									calFechaDescargo.setTime(getFechaDescargo());
									
									Integer periodoIncial=calFechaDescargo.get(Calendar.YEAR);
									
									Integer personaId=rpDjDescargo.getPersonaId();
									Integer predioId=rpDjDescargo.getPredioId();
									Integer periodoActual=registroPrediosBo.obtenerAnnioMax(personaId, predioId);
									
									BuscarPersonaDTO propietario=new BuscarPersonaDTO(); 
									
									//El periodo inicial de descargo es el anio siguiente al periodo de descargo, debido a que el impuesto predial se aplica al contribuyente desde el primero de enero de propiedad del predio 
									//periodoIncial=periodoIncial+1;
									//periodoIncial=periodoIncial;//Cambiado
									Double pendienteDescargo=0.0;
									for(int anno=periodoIncial;anno<=periodoActual;anno++){
										RpDjpredial djPredio=registroPrediosBo.getAllRpDjpredialByAnnoDj(personaId, predioId, anno);
										if(djPredio!=null){
											//Double pendienteDescargo=cantidadPropiedad-cantidadDescargo.doubleValue();
											pendienteDescargo=cantidadPropiedad-cantidadDescargo.doubleValue();											
											
											propietario.setPersonaId(personaId);
											propietario.setPorcentaje(BigDecimal.valueOf(pendienteDescargo));//Calcula el porcentaje que le corresponde
											propietario.setFormaAdquisicion(formaAdquisicion);
											
											//areas y porcentajes correspondientes al Vendedor
											if(xArea){												
												setArea(pendienteDescargo); 												
												setAreaTransferida(BigDecimal.valueOf(cantidadDescargo));
												setAreaRestante(BigDecimal.valueOf(pendienteDescargo));
												
												propietario.setArea(BigDecimal.valueOf(area));												
												propietario.setAreaMatriz(areaMatriz);
												propietario.setAreaTransferida(areaTransferida);
												propietario.setAreaRestante(areaRestante);												
												
												//propietario.setPorcentaje(BigDecimal.valueOf(porcentajePropiedad));
												propietario.setPorcentajeMatriz(porcentajeMatriz);
												propietario.setPorcentajeTransferido(porcentajeTransferido);
												propietario.setPorcentajeRestante(porcentajeRestante);
											}else{
												setPorcentajePropiedad(pendienteDescargo);
												setPorcentajeTransferido(BigDecimal.valueOf(cantidadDescargo));
												setPorcentajeRestante(BigDecimal.valueOf(pendienteDescargo));
												
												//propietario.setPorcentaje(BigDecimal.valueOf(pendienteDescargo));
												propietario.setPorcentajeMatriz(porcentajeMatriz);
												propietario.setPorcentajeTransferido(porcentajeTransferido);
												propietario.setPorcentajeRestante(porcentajeRestante);
												
												propietario.setArea(BigDecimal.valueOf(areaTerreno));
												propietario.setAreaMatriz(areaMatriz);
												propietario.setAreaTransferida(areaTransferida);
												propietario.setAreaRestante(areaRestante);
											}
											
											//
											String estadoDjDescargo=Constante.ESTADO_ACTIVO;//1
											if(getTipoDescargo().equals("Descargo pendiente")){
												estadoDjDescargo=Constante.ESTADO_PENDIENTE;//2
											}
											
											Integer copiaDjId=0;
											
											if(pendienteDescargo!=0){
												//Actualizacion de DJ
												//Indica que la condicion de propiedad es condomino
												//Siempre genera la Dj para el Anio Vigente
												if( formaAdquisicion.equals("M") ){
													copiaDjId=duplicaDj(djPredio,anno,djPredio.getFechaAdquisicion(),propietario,Constante.MOTIVO_DECLARACION_ACTUALIZA,Constante.CONDICION_PROPIEDAD_PROPIETARIO_UNICO, -1.0, propietario.getPersonaId());
												}else{ //Solo es condomino si la venta es por porncentaje
													copiaDjId=duplicaDj(djPredio,anno,djPredio.getFechaAdquisicion(),propietario,Constante.MOTIVO_DECLARACION_ACTUALIZA,Constante.CONDICION_PROPIEDAD_CONDOMINO_SIMPLE, -1.0 , propietario.getPersonaId());
												}												
												
												if(copiaDjId>Constante.RESULT_PENDING){
													creardj(copiaDjId,estadoDjDescargo);	
												}
											}else if(pendienteDescargo==0){
												//Descargo de DJ
												//Mantiene su condicion de propiedad original
												copiaDjId=duplicaDj(djPredio,anno,djPredio.getFechaAdquisicion(),propietario,Constante.MOTIVO_DECLARACION_DESCARGO,Constante.RESULT_PENDING, -1.0 , propietario.getPersonaId());
												if(copiaDjId>Constante.RESULT_PENDING){
													creardj(copiaDjId,estadoDjDescargo);	
												}
											}
											
											//Mantiene el numero de DJ de descargo (o actualizacion) 
											if(copiaDjId>Constante.RESULT_PENDING){
												//--inserta rp_djpredial de propietario nuevo inscripcion PENDIENTE
												
												//Registrando el area y el porcentaje que corresponde al (los) Comprador (es)
												for(BuscarPersonaDTO adquiriente:lstTransferentes){
													if(xArea){
														adquiriente.setArea(adquiriente.getPorcentaje());
														adquiriente.setAreaMatriz(areaMatriz);
														adquiriente.setAreaTransferida(areaTransferida);
														adquiriente.setAreaRestante(areaRestante);														
														
														adquiriente.setPorcentajeMatriz(BigDecimal.valueOf(porcentajePropiedad));
														adquiriente.setPorcentajeTransferido(porcentajeTransferido);
														adquiriente.setPorcentajeRestante(porcentajeRestante);
													}else{
														adquiriente.setArea(BigDecimal.valueOf(areaTerreno));
														adquiriente.setAreaMatriz(areaMatriz);
														adquiriente.setAreaTransferida(areaTransferida);
														adquiriente.setAreaRestante(areaRestante);
																												
														adquiriente.setPorcentajeMatriz(porcentajeMatriz);
														adquiriente.setPorcentajeTransferido(porcentajeTransferido);
														adquiriente.setPorcentajeRestante(porcentajeRestante);
													}
													adquiriente.setFormaAdquisicion(formaAdquisicion);
												}	
												
												registroPrediosBo.registrarAdquirientes(lstTransferentes,copiaDjId,Constante.TIPO_TRANSFERENCIA_ADQUIRIENTE);
												registroPrediosBo.registrarDocAnexos(lstAnexos, copiaDjId);//INSERT INTO dbo.rv_sustento_vehicular
											}
											setDjId(copiaDjId);
										}else{
											addErrorMessage("El contribuyente no posee una declaracion jurada para el periodo "+anno);
										}
									}								
									
									
									//Se realiza el descargo si existen los transferentes independientemente del motivo de descargo  
									//if(motivoDescargoId.equals(Constante.MOTIVO_DESCARGO_VENTA)){
										if(getDjId()!=null&&getDjId()>Constante.RESULT_PENDING){
											//--inserta rp_djpredial de propietario nuevo inscripcion PENDIENTE
											//No se sabe que tipo de propiedad poseera el comprador
											
											for(int t=0;t<lstTransferentes.size();t++){
												//El periodo incial es la fecha de descargo, pero el calculo del impuesto predial no lo considerara hasta el siguiente anio
												duplicaDj(rpDjDescargo,periodoIncial,DateUtil.dateToSqlDate(getFechaDescargo()),lstTransferentes.get(t),Constante.MOTIVO_DECLARACION_INSCRIPCION,Constante.RESULT_PENDING, pendienteDescargo, propietario.getPersonaId());
											}
											setDatosCorrectos(Boolean.TRUE);
										}
									//}		

								}else{
									if(formaAdquisicion.equals("M")){
										addErrorMessage(getMsg("rp.areaterrenomayor100"));	
									}else{
										addErrorMessage(getMsg("rp.porcentajemayor100"));
									}
								}
							}
						}catch (Exception ex) {
							addErrorMessage(getMsg("rv.djnogenerada"));
						}
					}else{
						addErrorMessage("Contribuyente posee valores");
					}
				}
			}else{
				addErrorMessage("La fecha de transferencia es mayor al anio actual");
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	/**
	 * Verifica si el predio a descargar posee Valores para los periodos desde la fecha de descargo a la fecha actual
	 * @return
	 */
	public Boolean validaPoseeValores()throws Exception{
		FindRpDjPredial findRpDjPredial=(FindRpDjPredial)getSessionMap().get("findRpDjPredial");
		
		Calendar calFechaDescargo=Calendar.getInstance();
		calFechaDescargo.setTime(getFechaDescargo());
		
		Integer periodoIncial=calFechaDescargo.get(Calendar.YEAR)+1;//Valida que contenga valores a partir del siguiente periodo al descargo
		Integer periodoActual=DateUtil.getAnioActual();
		
		for(int anio=periodoIncial.intValue();anio<=periodoActual.intValue();anio++){
			Boolean poseeValores=calculoPredialBo.validaPoseeValores(findRpDjPredial.getPredioId(), findRpDjPredial.getPersonaId(), anio);
			if(poseeValores){
				WebMessages.messageError("El predio posee valores asociados al periodo "+anio);
				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;
	}
	
	public void creardj(Integer DjId,String estadoDjDescargo){
		try{
			if(DjId!=null&&DjId>0){
				RpDjpredial rpDjpredial=registroPrediosBo.getRpDjpredial(DjId);
				rpDjpredial.setEstado(estadoDjDescargo);
				rpDjpredial.setFlagDjAnno(estadoDjDescargo.equals(Constante.ESTADO_ACTIVO)?Constante.FLAG_DJ_ANIO_ACTIVO:Constante.FLAG_DJ_ANIO_INACTIVO);
				rpDjpredial.setFechaDescargo(DateUtil.dateToSqlTimestamp(getFechaDescargo()));
				rpDjpredial.setEsDescargo(Constante.ESTADO_ACTIVO);
				registroPrediosBo.actualizaRpDjpredial(rpDjpredial); //Hasta aca hay 2 flags en estado Constante.ESTADO_ACTIVO
				if(estadoDjDescargo.equals(Constante.ESTADO_ACTIVO)){//Es descargo definitivo
					registroPrediosBo.actualizaEstadoRpDjpredial(rpDjpredial,Constante.FLAG_DJ_ANIO_INACTIVO);//aca actualiza los estados anteriores a Constante.ESTADO_INACTIVO
				}
				//Si el estado es Constante.ESTADO_PENDIENTE no inactiva los registros anteriores
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public Integer duplicaDj(RpDjpredial rpDjPredial,Integer annoDj,Date fechaInscripcion,BuscarPersonaDTO persona,Integer motivoDeclaracion,Integer condicionPropiedad, Double pendienteDescargo, Integer propietarioId){
		Integer djId=Constante.RESULT_PENDING;
		try{
			RpDjpredial copiaRpDjpredial=duplicaDjPredial(rpDjPredial,annoDj,fechaInscripcion,Constante.RESULT_PENDING,getSessionManaged().getUsuarioLogIn().getUsuarioId(),getSessionManaged().getTerminalLogIn(),persona,motivoDeclaracion,condicionPropiedad, pendienteDescargo, propietarioId);
			if(copiaRpDjpredial!=null&&copiaRpDjpredial.getDjId()>0){
				djId=copiaRpDjpredial.getDjId();	
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return djId;
	}
	
	public RpDjpredial duplicaDjPredial(RpDjpredial djpredio,Integer annoDj,Date fechaInscripcion,Integer DjId ,int userId, String terminal,BuscarPersonaDTO persona,Integer motivoDeclaracion,Integer condicionPropiedad, Double pendienteDescargo, Integer transferenteId){
		Integer DjActualizaId=0;
		try{
			if(djpredio!=null&&djpredio.getDjId()>0){
				
				//satc.dbo.rp_djpredial
				//djpredio=getService().getRpDjpredial(DjIdAnt);
				//djpredio=registroPrediosBo.getRpDjpredial(rpDjPredialAnt.getDjId());
				//satc.dbo.rp_djdireccion
				//RpDjdireccion direccion=getService().getRpDjDireccion(djpredio.getDjId());
				RpDjdireccion direccion=registroPrediosBo.getRpDjDireccion(djpredio.getDjId());
				//satc.dbo.rp_djconstruccion
				/**
				 * Obtenemos todos los listados del DJ anterior
				 */
				ArrayList<RpDjconstruccion> listaConstruccion=registroPrediosBo.getAllRpDjconstruccion(djpredio.getDjId(),djpredio.getAnnoDj());
				ArrayList<RpInstalacionDj> listaOtrasInsta=registroPrediosBo.getAllRpInstalacionDj(djpredio.getDjId());
				ArrayList<RpOtrosFrente> listaOtrosFrentes=registroPrediosBo.getAllRpOtrosFrente(djpredio.getDjId());
				List<BuscarPersonaDTO> listaTransferente=registroPrediosBo.getTransferentePropiedad(djpredio.getDjId(), Constante.TIPO_TRANSFERENCIA_TRANSFERENTE);
				//Obtenemos el djArbitrioId Anterior a copiar
				Integer djArbitrioIdAnterior=registroPrediosBo.getDjArbitrioId(djpredio.getDjId());
				ArrayList<RpDjuso> listaUsosDelPredio=new ArrayList<RpDjuso>();
				if(djArbitrioIdAnterior!=null&&djArbitrioIdAnterior>0){
//					listaUsosDelPredio=registroPrediosBo.getAllRpDjuso(djArbitrioIdAnterior);
				Integer tramoUso=registroPrediosBo.getAllTramo(djpredio.getAnnoDj());
				 if (tramoUso==Constante.PRIMER_TRAMO && annoDj<2016){
						listaUsosDelPredio=registroPrediosBo.getAllRpDjuso(djArbitrioIdAnterior);
				 }else if (tramoUso==Constante.PRIMER_TRAMO && annoDj>=2016){
					    listaUsosDelPredio=registroPrediosBo.getAllRpDjusoTramos(djArbitrioIdAnterior);
				 }else if(tramoUso==Constante.SEGUNDO_TRAMO && annoDj<2016){
					    listaUsosDelPredio=registroPrediosBo.getAllRpDjusoTramos(djArbitrioIdAnterior);
				 }else if(tramoUso==Constante.SEGUNDO_TRAMO && annoDj>=2016){
					    listaUsosDelPredio=registroPrediosBo.getAllRpDjuso(djArbitrioIdAnterior);
				 }
				}
				ArrayList<RjDocuAnexo> listaDocAnexos=registroPrediosBo.getAllRjDocuAnexo(djpredio.getDjId());
				//--
				Integer djIdAnt=djpredio.getDjId();
				//Integer personaIdAnt=djpredio.getPersonaId();
				//--
				//satc.dbo.rp_djpredial
				djpredio.setDjId(DjId);
				djpredio.setIdAnterior(String.valueOf(djIdAnt));
				djpredio.setPersonaId(persona.getPersonaId());
				
				if(formaAdquisicion.equals("M")){
					if(djpredio.getTipoPredio().equals(Constante.TIPO_PREDIO_RUSTICO)){
						djpredio.setAreaTerrenoHas(persona.getPorcentaje());
					}else{
						djpredio.setAreaTerreno(persona.getPorcentaje());	
					}
				}else{
					djpredio.setPorcPropiedad(persona.getPorcentaje());	
				}
				
				djpredio.setUsuarioId(userId);
				djpredio.setTerminal(terminal);
				djpredio.setFechaActualizacion(DateUtil.getCurrentDate());
				djpredio.setTerminalRegistro(terminal);
				if(!condicionPropiedad.equals(Constante.RESULT_PENDING))
					djpredio.setCondicionPropiedadId(condicionPropiedad);
				
				djpredio.setFlagDjAnno(Constante.FLAG_DJ_ANIO_INACTIVO);
				djpredio.setEstado(Constante.ESTADO_PENDIENTE);//PENDIENTE AUN NO ES DJ
				djpredio.setMotivoDeclaracionId(motivoDeclaracion);
				djpredio.setAnnoDj(annoDj);
				djpredio.setFechaDeclaracion(DateUtil.getCurrentDate());
				djpredio.setFechaRegistro(DateUtil.getCurrentDate());
				//--
				djpredio.setFechaAdquisicion(fechaInscripcion);
				//--
				djpredio.setFiscalizado(Constante.FISCALIZADO_NO);
				djpredio.setFiscaAceptada(Constante.FISCA_ACEPTADA_NO);
				djpredio.setFiscaCerrada(Constante.FISCA_CERRADA_NO);
				//-- Todo es descargo sea actualizacion o total 
				//if(motivoDeclaracion==Constante.MOTIVO_DECLARACION_DESCARGO){
					if (!motivoDescargo.isEmpty()) {
						int motivoDescargoId = mapRvMotivoDescargo.get(motivoDescargo);
						//en caso de escoger el de FusionMigracion se pasa a tipo Fusion para evitar errores
						if(motivoDescargoId == Constante.MOTIVO_DESCARGO_FUSION_MIGRACION) motivoDescargoId = 5;
						djpredio.setMotivoDescargoId(motivoDescargoId);
					}
					if (!selectedNotaria.isEmpty()) {
						int selectedNotariaId = mapGnNotaria.get(selectedNotaria);
						djpredio.setNotariaId(selectedNotariaId);
					}
					djpredio.setFechaDescargo(DateUtil.getCurrentDate());
					
				//}
				//djpredio.setEsDescargo(Constante.ESTADO_INACTIVO);
				djpredio.setEsDescargo(Constante.ESTADO_ACTIVO);
				djpredio.setGlosa(getGlosa());
				/**
				 * Se debe insertar un predioID por cada comprador para el caso de descargo por partes del áera
				 * */
				if( formaAdquisicion.equals("M") && pendienteDescargo >= 0  && motivoDeclaracion.compareTo(Constante.MOTIVO_DECLARACION_INSCRIPCION) == 0){
					MpPredio predio=new MpPredio();
					predio.setPredioId(Constante.RESULT_PENDING);
					predio.setTipoPredio(djpredio.getTipoPredio());
					predio.setEstado(Constante.ESTADO_ACTIVO);					
					
					if(djpredio.getTipoPredio().equals("U")){
						predio.setAreaTerreno(djpredio.getAreaTerreno());
						predio.setAreaTerrenoComun(djpredio.getAreaTerrenoComun());
					}else {
						predio.setAreaTerreno(djpredio.getAreaTerrenoHas());
						predio.setAreaTerrenoComunHas(djpredio.getAreaTerrenoComunHas());
					}
					predio.setFechaAdquisicion(djpredio.getFechaAdquisicion());
					predio.setFrente(djpredio.getFrente());	
					predio.setCodigoAnterior(getCodigoPredio());
					
					Integer NextPredioId=registroPrediosBo.guardarMpPredio(predio);
					djpredio.setCodigoAnterior(getCodigoPredio());
					//setendo el nuevo mp_predio a djpredio
					djpredio.setPredioId(NextPredioId);										
				}
				
				/****************************************/
				
				DjActualizaId=registroPrediosBo.guardarRpDjpredial(djpredio);
				
				//satc.dbo.rp_djdireccion
				if(direccion!=null){
					direccion.setDjId(DjActualizaId);
					direccion.setEstado(Constante.ESTADO_ACTIVO);
					direccion.setFechaRegistro(DateUtil.getCurrentDate());
					direccion.setUsuarioId(userId);
					direccion.setTerminal(terminal);
					registroPrediosBo.guardarRpDjdireccion(direccion);
				}
				
				//satc.dbo.rp_djconstruccion
				for(int i=0;i<listaConstruccion.size();i++){
					RpDjconstruccion construccion=listaConstruccion.get(i);
					construccion.setDjId(DjActualizaId);
					construccion.setUsuarioId(userId);
					construccion.setFechaRegistro(DateUtil.getCurrentDate());
					construccion.setTerminal(terminal);
					int rez=registroPrediosBo.guardarRpDjconstruccion(construccion);
					if(rez>0){
						Integer newConstruccionId=registroPrediosBo.getUltimoConstruccionId(DjActualizaId);
						listaConstruccion.get(i).setNewConstruccionId(newConstruccionId);
					}
				}
				
				//satc.dbo.rp_instalacion_dj
				for(int i=0;i<listaOtrasInsta.size();i++){
					RpInstalacionDj instalacion=listaOtrasInsta.get(i);
					instalacion.setDjId(DjActualizaId);
					instalacion.setUsuarioId(userId);
					instalacion.setFechaRegistro(DateUtil.getCurrentDate());
					instalacion.setTerminal(terminal);
					instalacion.setEstado(Constante.ESTADO_ACTIVO);
					//--Correccion del calculo del valor de las otras instalaciones -22/12/2013
					RpTipoObraPeriodo tipoObraPeriodo = new RpTipoObraPeriodo();				
					tipoObraPeriodo = registroPrediosBo.getRpTipoObraPeriodo(instalacion.getTipoObraId(), djpredio.getAnnoDj());
					if(tipoObraPeriodo!=null&&tipoObraPeriodo.getValorUnitario()!=null&&tipoObraPeriodo.getValorUnitario().doubleValue()>0){
						BigDecimal valorInstalacion=instalacion.getAreaTerreno().multiply(tipoObraPeriodo.getValorUnitario());
						if(valorInstalacion!=null){
							instalacion.setValorInstalacion(valorInstalacion);	
						}
					}else {
						RpTipoObra tipoObra=registroPrediosBo.getRpTipoObra(instalacion.getTipoObraId());
						if(tipoObra!=null&&tipoObra.getValorUnitario()!=null&&tipoObra.getValorUnitario().doubleValue()>0){
							BigDecimal valorInstalacion=instalacion.getAreaTerreno().multiply(tipoObra.getValorUnitario());
							if(valorInstalacion!=null){
								instalacion.setValorInstalacion(valorInstalacion);	
							}	
						}
					}
					//--Correccion del calculo del valor de las otras instalaciones -22/12/2013
					registroPrediosBo.guardarRpInstalacionDj(instalacion);
				}
				
				//satc.dbo.rp_otros_frentes
				for(int i=0;i<listaOtrosFrentes.size();i++){
					RpOtrosFrente frente=listaOtrosFrentes.get(i);
					frente.setDjId(DjActualizaId);
					frente.setUsuarioId(userId);
					frente.setFechaRegistro(DateUtil.getCurrentDate());
					frente.setTerminal(terminal);
					frente.setEstado(Constante.ESTADO_ACTIVO);
					//int rez=registroPrediosBo.guardarRpOtrosFrente(frente);
				}
				
				//satc.dbo.rp_djuso
				//creamos el nuevo Djarbitrio
				RpDjarbitrio rpDjarbitrio=new RpDjarbitrio();  
				rpDjarbitrio.setDjId(DjActualizaId);
				rpDjarbitrio.setEstado(Constante.ESTADO_ACTIVO);
				rpDjarbitrio.setFechaRegistro(DateUtil.getCurrentDate());
				rpDjarbitrio.setTerminal(terminal);
				rpDjarbitrio.setUsuarioId(userId);
				int result=registroPrediosBo.guardarDjArbitrioId(rpDjarbitrio);
				if(result>0){
					//Obtenemos el djArbitrioId del nuevo Dj
					Integer djArbitrioId=registroPrediosBo.getDjArbitrioId(DjActualizaId);
					for(int i=0;i<listaUsosDelPredio.size();i++){
						RpDjuso uso=listaUsosDelPredio.get(i);
						uso.setDjarbitrioId(djArbitrioId);
						uso.setUsuarioId(userId);
						uso.setFechaRegistro(DateUtil.getCurrentDate());
						uso.setTerminal(terminal);
						uso.setEstado(Constante.ESTADO_ACTIVO);
						//Se corrige el anio de afectacion de acuerdo al anio que se duplica la Dj
						uso.setAnnoAfectacion(annoDj);
						int rez=registroPrediosBo.guardarRpDjuso(uso);
						if(rez>0){
						   Integer newdjUsoId=registroPrediosBo.getUltimoDjUsoId(djArbitrioId);
						   ArrayList<RpDjusoDetalle> lista=registroPrediosBo.getAllRpDjusoDetalle(uso.getDjusoId());
						   for(int j=0;j<lista.size();j++){
							   RpDjusoDetalle detalle=lista.get(j);
							   detalle.setDjusoId(newdjUsoId);
							   Integer newConstruccionId=getNewConstruccionId(detalle.getConstruccionId(),listaConstruccion);
							   if(newConstruccionId>0){
								   detalle.setConstruccionId(newConstruccionId);
								   detalle.setDjusoDetalleId(Constante.RESULT_PENDING);
								   registroPrediosBo.guardarRpDjusoDetalle(detalle);
							   }
						   }
						}
					}
				}				
				
				//satc.dbo.rp_transferencia_propiedad
				if(motivoDeclaracion==Constante.MOTIVO_DECLARACION_INSCRIPCION){
					//En una inscripción del predio hacia el Adquiriente
					//Propietario actual del predio es el transferente
					BuscarPersonaDTO transferente=new BuscarPersonaDTO();
					transferente.setPersonaId(transferenteId);
					//transferente.setPorcentaje(persona.getPorcentaje());
					//La inscripcion del predio para el adquiriente no generara un descargo
					transferente.setDescargoAutomatico("N"); 
					transferente.setFormaAdquisicion(formaAdquisicion);
					
					//Registrando el area matriz y el area restante o el porcentaje matriz y el porcentaje restante PARA EL TRANSFERENTE
					transferente.setArea(BigDecimal.valueOf(area));
					transferente.setAreaMatriz(areaMatriz);
					transferente.setAreaTransferida(areaTransferida);
					transferente.setAreaRestante(areaRestante);
					
					transferente.setPorcentaje(BigDecimal.valueOf(porcentajePropiedad));
					transferente.setPorcentajeMatriz(porcentajeMatriz);
					transferente.setPorcentajeTransferido(porcentajeTransferido);
					transferente.setPorcentajeRestante(porcentajeRestante);
					
					List<BuscarPersonaDTO> lTransferente=new LinkedList<BuscarPersonaDTO>();
					lTransferente.add(transferente);
					registroPrediosBo.registrarAdquirientes(lTransferente,DjActualizaId,Constante.TIPO_TRANSFERENCIA_TRANSFERENTE);
					
				}else{//Constante.MOTIVO_DECLARACION_ACTUALIZA //Constante.MOTIVO_DECLARACION_DESCARGO
					//En una actualizacion del predio del Transferente
					//Mantiene la lista de transferentes originales
					for(int i=0;i<listaTransferente.size();i++){
						registroPrediosBo.registrarAdquirientes(listaTransferente,DjActualizaId,Constante.TIPO_TRANSFERENCIA_TRANSFERENTE);
					}
				}
				
				//satc.dbo.rj_docu_anexo
				for(int i=0;i<listaDocAnexos.size();i++){
					RjDocuAnexo anexo=listaDocAnexos.get(i);
					anexo.setDjId(DjActualizaId);
					anexo.setUsuarioId(userId);
					anexo.setFechaRegistro(DateUtil.getCurrentDate());
					anexo.setTerminal(terminal);
					anexo.setEstado(Constante.ESTADO_ACTIVO);
					registroPrediosBo.guardarRjDocuAnexo(anexo);
				}
				
				
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		if(djpredio!=null){
			djpredio.setDjId(DjActualizaId);
		}
		return djpredio;
	}
	
	public Double cantidadDescargo(){
		
		
//		Double sumaPorcentaje=new Double(0);
//		Iterator<BuscarPersonaDTO> it = lstTransferentes.iterator();  
//        while (it.hasNext()){
//        	BuscarPersonaDTO persona=it.next();
//        	System.out.println(persona.getPorcentaje().doubleValue());
//        	sumaPorcentaje=sumaPorcentaje+persona.getPorcentaje().setScale(6).doubleValue();
//        	System.out.println(sumaPorcentaje);
//        }
//        return sumaPorcentaje;
		
		
		/** Anterior codigo no realizaba correctamente la suma de las areas: 
		/* 0.645
		/* 0.4995
		/* 0.4560
		/* 0.4851
		/* 0.4840
		/* Se paso a sumar uniformente con tipo de datos bigdecimal y aplicando un redondeo a 
		/* seis cifra en caso de unidades hectareas
		*/
		
		BigDecimal sumaPorcentaje=new BigDecimal(0);
        for(BuscarPersonaDTO buscarPersonaDTO:lstTransferentes){
        	
        	sumaPorcentaje = sumaPorcentaje.add(buscarPersonaDTO.getPorcentaje());        	
        }
        
        return sumaPorcentaje.setScale(6, RoundingMode.HALF_UP).doubleValue();        
	}
	
	public Integer getNewConstruccionId(Integer construccionId,ArrayList<RpDjconstruccion> listaConstruccion){
		Integer NewConstruccionId=0;
		for(int i=0;i<listaConstruccion.size();i++){
			if(construccionId.equals(listaConstruccion.get(i).getConstruccionId())){
				NewConstruccionId=listaConstruccion.get(i).getNewConstruccionId();
			}
		}
		return NewConstruccionId;
	}
	
	
	public void changeDatosCorrectos(ActionEvent ev) {
		datosCorrectos = true;
	}
	
	//INICIO ITANTAMANGO
	public void agregarAnexos(ActionEvent ev) {
		try {
			for (DocumentoSustentatorioDTO dsd : lstDocSusten) {
				if (dsd.isSelected()) {
					lstAnexos.add(new AnexosDeclaVehicDTO(dsd
							.getDocSustentatorioId(), dsd.getDescripcion(), dsd
							.getGlosa(), null,null));
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	//FIN ITANTAMANGO
	
	public boolean existeTransfEnLista(int personaId) {
		boolean existe = false;
		int max = lstTransferentes.size();
		for (int i = 0; i < max; i++) {
			if (lstTransferentes.get(i).getPersonaId() == personaId) {
				existe = true;
				break;
			}
		}
		if (getSessionManaged().getContribuyente().getPersonaId().intValue() == personaId) {
			existe = true;
		}
		return existe;
	}
	public boolean isDescargado() {
		return descargado;
	}
	
	public void delete(){
		try{
			if(transferente!=null){
				lstTransferentes.remove(transferente);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public BuscarPersonaManaged getBuscarPersonaManaged() {
		return (BuscarPersonaManaged) getManaged("buscarPersonaManaged");
	}
	
	public RegistroPrediosBoRemote getRegistroPrediosBo() {
		return registroPrediosBo;
	}

	public void setRegistroPrediosBo(RegistroPrediosBoRemote registroPrediosBo) {
		this.registroPrediosBo = registroPrediosBo;
	}

	public String getFechaDeclaracion() {
		return fechaDeclaracion;
	}

	public void setFechaDeclaracion(String fechaDeclaracion) {
		this.fechaDeclaracion = fechaDeclaracion;
	}

	public String getMotivoDescargo() {
		return motivoDescargo;
	}

	public void setMotivoDescargo(String motivoDescargo) {
		this.motivoDescargo = motivoDescargo;
	}

	public Double getPorcentajePropiedad() {
		return porcentajePropiedad;
	}

	public void setPorcentajePropiedad(Double porcentajePropiedad) {
		this.porcentajePropiedad = porcentajePropiedad;
	}

	public String getGlosa() {
		return glosa;
	}

	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}

	public String getTipoPredio() {
		return tipoPredio;
	}

	public void setTipoPredio(String tipoPredio) {
		this.tipoPredio = tipoPredio;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCondicionPropiedad() {
		return condicionPropiedad;
	}

	public void setCondicionPropiedad(String condicionPropiedad) {
		this.condicionPropiedad = condicionPropiedad;
	}
	public List<SelectItem> getLstMotivoDescargo() {
		return lstMotivoDescargo;
	}

	public void setLstMotivoDescargo(List<SelectItem> lstMotivoDescargo) {
		this.lstMotivoDescargo = lstMotivoDescargo;
	}

	public List<BuscarPersonaDTO> getLstTransferentes() {
		return lstTransferentes;
	}

	public void setLstTransferentes(List<BuscarPersonaDTO> lstTransferentes) {
		this.lstTransferentes = lstTransferentes;
	}

	public List<DocumentoSustentatorioDTO> getLstDocSusten() {
		return lstDocSusten;
	}

	public void setLstDocSusten(List<DocumentoSustentatorioDTO> lstDocSusten) {
		this.lstDocSusten = lstDocSusten;
	}

	public List<AnexosDeclaVehicDTO> getLstAnexos() {
		return lstAnexos;
	}

	public void setLstAnexos(List<AnexosDeclaVehicDTO> lstAnexos) {
		this.lstAnexos = lstAnexos;
	}
	public List<SelectItem> getLstNotarias() {
		return lstNotarias;
	}

	public void setLstNotarias(List<SelectItem> lstNotarias) {
		this.lstNotarias = lstNotarias;
	}

	public String getSelectedNotaria() {
		return selectedNotaria;
	}

	public void setSelectedNotaria(String selectedNotaria) {
		this.selectedNotaria = selectedNotaria;
	}

	public boolean isDatosCorrectos() {
		return datosCorrectos;
	}

	public void setDatosCorrectos(boolean datosCorrectos) {
		this.datosCorrectos = datosCorrectos;
	}
	public String getEstadoDecla() {
		return estadoDecla;
	}

	public void setEstadoDecla(String estadoDecla) {
		this.estadoDecla = estadoDecla;
	}
	public void setDescargado(boolean descargado) {
		this.descargado = descargado;
	}
	
	public String getCodigoPredio() {
		return codigoPredio;
	}

	public void setCodigoPredio(String codigoPredio) {
		this.codigoPredio = codigoPredio;
	}
	
	public Integer getDjId() {
		return djId;
	}

	public void setDjId(Integer djId) {
		this.djId = djId;
	}
	
	public BuscarPersonaDTO getTransferente() {
		return transferente;
	}

	public void setTransferente(BuscarPersonaDTO transferente) {
		this.transferente = transferente;
	}
	
	public java.util.Date getFechaDescargo() {
		return fechaDescargo;
	}

	public void setFechaDescargo(java.util.Date fechaDescargo) {
		this.fechaDescargo = fechaDescargo;
	}
	
	public String getTipoDescargo() {
		return tipoDescargo;
	}

	public void setTipoDescargo(String tipoDescargo) {
		this.tipoDescargo = tipoDescargo;
	}
	
	public String getFormaAdquisicion() {
		return formaAdquisicion;
	}

	public void setFormaAdquisicion(String formaAdquisicion) {
		this.formaAdquisicion = formaAdquisicion;
	}
	
	public Double getArea() {
		return area;
	}

	public void setArea(Double area) {
		this.area = area;
	}

	public BigDecimal getAreaMatriz() {
		return areaMatriz;
	}

	public void setAreaMatriz(BigDecimal areaMatriz) {
		this.areaMatriz = areaMatriz;
	}

	public BigDecimal getPorcentajeMatriz() {
		return porcentajeMatriz;
	}

	public void setPorcentajeMatriz(BigDecimal porcentajeMatriz) {
		this.porcentajeMatriz = porcentajeMatriz;
	}

	public BigDecimal getAreaTransferida() {
		return areaTransferida;
	}

	public void setAreaTransferida(BigDecimal areaTransferida) {
		this.areaTransferida = areaTransferida;
	}

	public BigDecimal getPorcentajeTransferido() {
		return porcentajeTransferido;
	}

	public void setPorcentajeTransferido(BigDecimal porcentajeTransferido) {
		this.porcentajeTransferido = porcentajeTransferido;
	}

	public BigDecimal getPorcentajeRestante() {
		return porcentajeRestante;
	}

	public void setPorcentajeRestante(BigDecimal porcentajeRestante) {
		this.porcentajeRestante = porcentajeRestante;
	}

	public BigDecimal getAreaRestante() {
		return areaRestante;
	}

	public void setAreaRestante(BigDecimal areaRestante) {
		this.areaRestante = areaRestante;
	}
}
