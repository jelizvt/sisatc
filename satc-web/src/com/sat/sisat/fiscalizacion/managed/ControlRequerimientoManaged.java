package com.sat.sisat.fiscalizacion.managed;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.sql.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.richfaces.component.html.HtmlCalendar;
import org.richfaces.component.html.HtmlComboBox;
import org.richfaces.model.filter.Filter;
import org.richfaces.model.selection.SimpleSelection;

import com.sat.sisat.cobranzacoactiva.business.CobranzaCoactivaBoRemote;
import com.sat.sisat.cobranzacoactiva.dto.FindCcRecTipo;
import com.sat.sisat.common.business.GeneralBoRemote;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.FacesUtil;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.controlycobranza.dto.MpFiscalizador;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.fiscalizacion.business.FiscalizacionBo;
import com.sat.sisat.fiscalizacion.business.FiscalizacionBoRemote;
import com.sat.sisat.fiscalizacion.dto.FindInpscDocTipo;
import com.sat.sisat.fiscalizacion.dto.FindInspeccionByHorario;
import com.sat.sisat.fiscalizacion.dto.FindInspeccionDj;
import com.sat.sisat.fiscalizacion.dto.FindInspeccionHistorial;
import com.sat.sisat.obligacion.dto.MultaDTO;
import com.sat.sisat.obligacion.dto.SubConceptoDTO;
import com.sat.sisat.papeleta.dto.FindPapeletas;
import com.sat.sisat.papeletas.managed.BuscarPersonaPapeletasManaged;
import com.sat.sisat.persistence.entity.DtFechaVencimiento;
import com.sat.sisat.persistence.entity.MpPersona;
import com.sat.sisat.persistence.entity.MpTipoDocuIdentidad;
import com.sat.sisat.persistence.entity.MpTipoPersona;
import com.sat.sisat.persistence.entity.PaPapeleta;
import com.sat.sisat.persistence.entity.RpDjpredial;
import com.sat.sisat.persistence.entity.RpFiscalizacionHorario;
import com.sat.sisat.persistence.entity.RpFiscalizacionHorarioDetalle;
import com.sat.sisat.persistence.entity.RpFiscalizacionInspeccion;
import com.sat.sisat.persistence.entity.RpFiscalizacionPrograma;
import com.sat.sisat.persistence.entity.RpFiscalizacionProgramaDetalle;
import com.sat.sisat.predial.business.RegistroPrediosBoRemote;
import com.sat.sisat.predial.dto.FindRpDjPredial;
import com.sat.sisat.vehicular.cns.ReusoFormCns;
import com.sat.sisat.vehicular.dto.BuscarPersonaDTO;


@ManagedBean
@ViewScoped
public class ControlRequerimientoManaged extends BaseManaged {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	FiscalizacionBoRemote ficalizacionBo;
	
	@EJB
	RegistroPrediosBoRemote registroPrediosBo;
	
	//public boolean rememberMe = false;
	// REGISTRAMOS DATOS DEL REQUERIMIENTO
		// OBTENEMOS EL TIPO DE DOC.: CARTA, REQUERIMIENTO
			private String cmbTipoDocRequerimiento;
			private HtmlComboBox cmbxTipoDocRequerimiento;
			private Integer tipoDocRequerimientoId;
			private HashMap<String, Integer> mapTipoDocRequerimiento = new HashMap<String, Integer>();
			private List<SelectItem> listaTipoDocRequerimiento = new ArrayList<SelectItem>();
		
		// OBTENEMOS EL TIPO PROGRAMA DE FISCALIZACION
			private String cmbTipoProgramaRequerimiento;
			private HtmlComboBox cmbxTipoProgramaRequerimiento;
			private Integer tipoProgramaRequerimientoId;
			private HashMap<String, Integer> mapTipoProgramaRequerimiento = new HashMap<String, Integer>();
			private List<SelectItem> listaTipoProgramaRequerimiento = new ArrayList<SelectItem>();
			
			//OBTENEMOS EL NUMERO DE VALOR
			
			private FindInpscDocTipo correlativoDoc;
			private String descripcion;
			
			// OBTENEMOS EL INSPECTOR:
			private String cmbInspector;
			private HtmlComboBox cmbxInspector;
			private Integer inspectorId;
			private HashMap<String, Integer> mapInspector = new HashMap<String, Integer>();
			private List<SelectItem> listaInspectores = new ArrayList<SelectItem>();
			
			private Boolean isInspector;
			
			
			// OBTENEMOS EL HORARIO DE FISCALIZACION
					private String cmbHorario;
					private HtmlComboBox cmbxHorario;
					private Integer HorarioId;
					private HashMap<String, Integer> mapHorario = new HashMap<String, Integer>();
					private List<SelectItem> listaHorarios = new ArrayList<SelectItem>();
					private String listarHorarios;
					
					//private Map<String, Long> availableItems; 
					
					private String[] lstHorarios = null;
					private List<RpFiscalizacionHorario> lstHorariosInspc = null;
					private List<RpFiscalizacionHorario> listaHora;
					private List<RpFiscalizacionHorario> lstHoras = null;
					
					private HashMap<String, Integer> mapMpTipoHoraSeleccionados = new HashMap<String, Integer>();
					
					//validamos hora por inspector y fecha:
					private List<FindInspeccionByHorario> listaHorario = new ArrayList<FindInspeccionByHorario>();
					
			//OBTENEMOS EL EJERCICIO FISCAL POR PROGRAMA
					private String[] lstAnios = null;
					private List<RpFiscalizacionProgramaDetalle> lstAniosInspc = null;
					private HashMap<String, Integer> mapMpTipoAnioSeleccionados = new HashMap<String, Integer>();
					
					private String[] lstAniosReq = null;
					private List<RpFiscalizacionProgramaDetalle> lstAniosInspcReq = null;
					private HashMap<String, Integer> mapMpTipoAnioReqSeleccionados = new HashMap<String, Integer>();
					

			//OBTENEMOS EL CORRELATIVO
					private String correlativoCarta;
					private String correlativoReque;
					private String correlativoCartaMult;
					private Boolean istipoCarta;
					private Boolean istipoReque;
					private Boolean istipoCartaMult;
					//validamos correlativo
					private List<FindInspeccionHistorial> listacorrelativo = new ArrayList<FindInspeccionHistorial>();
					private Boolean isCorrelativoRegistrado;
			
			
			//OBTENEMOS AL CONTRIBUYENTE
					private Integer codPersona;
					private String dirPersona;
					private Boolean seleccionPersona=false;		
			
			//GENERAMOS EL REQUERIMIENTO DE INSPECCION:
					private Integer tipoDocumento;
					private Integer tipoPrograma;
					private Integer nombreInspector;
					private Integer tipoHorario;
					private Date fechaNotificacion;
					private Date fechaInspeccion=DateUtil.getCurrentDate();
					private String valor;
					private Boolean isHorarioProgramado;
					//private String fechaInspeccion;
					private String observaciones;
					
					private BuscarPersonaDTO datosContribuyente;
					
					private String anioInsp;
					private String anioReqInsp;
					
					private String tipoDocumentoAsoc;
					private String numeroDocumentoAsoc;
					

			//OBTENEMOS LOS PREDIOS CON DJS FISCALIZADAS
			
					private List<FindInspeccionDj> listaDjInspeccion = new ArrayList<FindInspeccionDj>();
					//private FindInspeccionDj djSeleccionada = new FindInspeccionDj();//En la grilla
					private FindRpDjPredial predioSeleccionado = new FindRpDjPredial();//En la grilla,23-07::Cambio djs por predios.
					
					private List<FindInspeccionDj> lstDjInspeccionDTOs;
					private Boolean isDjOmisa;
							
					private List<FindRpDjPredial> listDjPredials = new ArrayList<FindRpDjPredial>();//23-07::Obtenemos los predios, en una primera instancia primero registran Predios no Djs.	
					
			//OBTENEMOS LOS REQUERIMIENTOS DE INSPECCION
					private RpFiscalizacionInspeccion rpInspeccion;
			
			//OCULTAMOS O MOSTRAMOS EL BOTÓN BUSCAR
					private Boolean ocultarBoton =false;
			
			//ASOCIAMOS A UN DOCUMENTO ANTES REGISTRADO:CARTA MULTIPLE,CARTA O REQUERIMIENTO
					private Boolean asociarDocumento=false;
					private String nroDocumentoasociado;
					private List<FindInspeccionHistorial> inspeccion = new ArrayList<FindInspeccionHistorial>();
					private Boolean isPrograma;
					private Integer idDocumentoasociado;
					private String anioDocumentoasociado;
					
			//REGISTRO DE UN PREDIO OMISO:
					private boolean predioOmiso;
					private Integer omiso;
			
			public ControlRequerimientoManaged() throws Exception {}
						
			@PostConstruct
			public void init() throws Exception {
				
				try {

					/* CORRELATIVO */
					
					
					/* CORRELATIVO */
					
					/* COMBOBOX:: TIPO DE DOCUMENTO */
					List<FindInpscDocTipo> lstCcTipoRec = ficalizacionBo.getAllTipoDoc();
					Iterator<FindInpscDocTipo> it1 = lstCcTipoRec.iterator();
					listaTipoDocRequerimiento = new ArrayList<SelectItem>();

					while (it1.hasNext()) {
						FindInpscDocTipo obj = it1.next();
						listaTipoDocRequerimiento.add(new SelectItem(obj.getDescripcionTipoDocumento(), String.valueOf(obj.getTipoDocumentoId())));
						mapTipoDocRequerimiento.put(obj.getDescripcionTipoDocumento().trim(),obj.getTipoDocumentoId());
					}
					/*- COMBOBOX:: TIPO DE DOCUMENTO -*/
					
					/* COMBOBOX:: TIPO DE PROGRAMA */
					List<RpFiscalizacionPrograma> lstRpTipoPrograma = ficalizacionBo.getAllTipoPrograma();
					Iterator<RpFiscalizacionPrograma> it2 = lstRpTipoPrograma.iterator();
					listaTipoProgramaRequerimiento = new ArrayList<SelectItem>();

					while (it2.hasNext()) {
						RpFiscalizacionPrograma obj = it2.next();
						listaTipoProgramaRequerimiento.add(new SelectItem(obj.getNombrePrograma(), String.valueOf(obj.getProgramaId())));
						mapTipoProgramaRequerimiento.put(obj.getNombrePrograma().trim(),obj.getProgramaId());
					}
					/*- COMBOBOX:: TIPO DE PROGRAMA -*/
					
					
					/* COMBOBOX:: INSPECTOR */
					List<MpFiscalizador> lstMpInspector = ficalizacionBo.getAllInspector();
					Iterator<MpFiscalizador> it3 = lstMpInspector.iterator();
					listaInspectores = new ArrayList<SelectItem>();

					while (it3.hasNext()) {
						MpFiscalizador obj = it3.next();
						listaInspectores.add(new SelectItem(obj.getNombresApellidos(), String.valueOf(obj.getIdfiscalizador())));
						mapInspector.put(obj.getNombresApellidos().trim(),obj.getIdfiscalizador());
					}
					/*- COMBOBOX:: INSPECTOR -*/
					
					/* COMBOBOX:: HORARIO */
//					List<RpFiscalizacionHorario> lstHorarios = ficalizacionBo.getAllHorario();
//					Iterator<RpFiscalizacionHorario> it4 = lstHorarios.iterator();
//					listaHorarios = new ArrayList<SelectItem>();
//
//					while (it4.hasNext()) {
//						RpFiscalizacionHorario obj = it4.next();
//						listaHorarios.add(new SelectItem(obj.getNombreHorario(), String.valueOf(obj.getHorarioId())));
//						mapHorario.put(obj.getNombreHorario().trim(),obj.getHorarioId());
//					}
					/*- COMBOBOX:: HORARIO -*/
					
					//loadHorarios();/*Comentado en Mayo 2017, solicitado por Correo Instituc. Jimmy Díaz (14.03.2016) e Informe 053-009-00001733*/
					
					//loadAniosReq();
					loadAnios();
					loadReferencia();
					//loadAniosPrograma();
//					if (asociarDocumento == Boolean.TRUE && nroDocumentoasociado != null) {
//						loadAnios();
//					}
					
//					if (asociarDocumento == Boolean.TRUE && nroDocumentoasociado != null) {
//						lstAniosProgramaInspc = ficalizacionBo.getAllAniosById(idDocumentoasociado);
//					}
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
			}
		
			public void buscarDjFiscalizada() throws Exception{
				try{

						listDjPredials = registroPrediosBo.getRpDjpredial(null, null, null, null, null, null, null,
								         null, null, null, null, datosContribuyente.getPersonaId(), true);

//					listaDjInspeccion = ficalizacionBo.getDeclaracionesInsp(datosContribuyente.getPersonaId());
//					if (listaDjInspeccion.isEmpty()){
//						setIsDjOmisa(true);
//						WebMessages.messageError("¿Es una Dj omisa?");
//					}else{
//						
//						setIsDjOmisa(false);
//						
//					}
	
				}catch(Exception e){
					e.printStackTrace();
					WebMessages.messageFatal(e);
				}
				//return isDjOmisa;
			}
			
			
			
			
			public String actualizacion(){
				if(datosContribuyente!=null){
					//getSessionMap().put("currentItem", datosContribuyente);//Enviando todo el objeto.
					getSessionMap().put("currentItem", datosContribuyente.getPersonaId());
					

				}

				return sendRedirectPrincipal();
			}
			

			public void loadHorarios() throws Exception {

				lstHorarios = new String[] {};
				// lstMpTipoPersona
				lstHorariosInspc = ficalizacionBo.getAllHorario();
				Iterator<RpFiscalizacionHorario> it2 = lstHorariosInspc.iterator();
				String temp2 = "";
				while (it2.hasNext()) {
					RpFiscalizacionHorario obj = it2.next();
					mapMpTipoHoraSeleccionados.put(obj.getIntervaloHorario().trim(),obj.getHorarioId());
					temp2 = temp2 + obj.getIntervaloHorario()+ ",";
				}
				lstHorarios = temp2.split(",");
					
			}
			public void loadReferencia() throws Exception {
				if (tipoProgramaRequerimientoId!=null){
					inspeccion = ficalizacionBo.getAllInspeccionesByPrograma(tipoProgramaRequerimientoId);
				}else{
					inspeccion = ficalizacionBo.getAllInspecciones();
				}
				
			}
			
			
			public void loadAnios() throws Exception {

				lstAnios = new String[] {};
			
			    if (tipoProgramaRequerimientoId!=null&&tipoProgramaRequerimientoId!=Constante.TIPO_PROGRAMA_ID){
			    	lstAniosInspc = ficalizacionBo.getAllAnios(tipoProgramaRequerimientoId);
			    }else if (tipoProgramaRequerimientoId==null){
			    	lstAniosInspc = ficalizacionBo.getAllAniosReq();
			    }else if(tipoProgramaRequerimientoId!=null&&tipoProgramaRequerimientoId==Constante.TIPO_PROGRAMA_ID) {
			    	lstAniosInspc = ficalizacionBo.getAllAniosReq();
			    } 
		    
				Iterator<RpFiscalizacionProgramaDetalle> it2 = lstAniosInspc.iterator();
				String temp2 = "";
				while (it2.hasNext()) 
				{
					RpFiscalizacionProgramaDetalle obj = it2.next();
					mapMpTipoAnioSeleccionados.put(obj.getAnioFiscalizacion(),obj.getProgramaDetalleId());
					temp2 = temp2 + obj.getAnioFiscalizacion()+ ",";
				}
				lstAnios = temp2.split(",");
					
			}
			
			public void loadAniosReq() throws Exception {

				lstAnios = new String[] {};
			
				lstAniosInspcReq = ficalizacionBo.getAllAniosReq();
				Iterator<RpFiscalizacionProgramaDetalle> it2 = lstAniosInspcReq.iterator();
				String temp2 = "";
				while (it2.hasNext()) {
					RpFiscalizacionProgramaDetalle obj = it2.next();
					mapMpTipoAnioReqSeleccionados.put(obj.getAnioFiscalizacion().trim(),obj.getProgramaDetalleId());
					temp2 = temp2 + obj.getAnioFiscalizacion()+ ",";
				}
				lstAnios = temp2.split(",");
					
			}
	
			
			
			public void loadTipoDocRequerimiento(ValueChangeEvent event) {
				try {
					HtmlComboBox combo = (HtmlComboBox) event.getComponent();
					String value = combo.getValue().toString();
					if (value != null) {
						tipoDocRequerimientoId  = (Integer) mapTipoDocRequerimiento.get(value);
						setCmbTipoDocRequerimiento(value);
					}
					if(value!=null){
						if(value.compareTo(Constante.TIPO_DOC_CARTA)==0)
						{
							istipoCarta=Boolean.TRUE;
							istipoReque=Boolean.FALSE;
							istipoCartaMult=Boolean.FALSE;
							//correlativoCarta=ficalizacionBo.correlativo(tipoDocRequerimientoId); /* Dany A. solicito digitar correlativo - 18/07/14 */

						}
						else if(value.toString().compareTo(Constante.TIPO_DOC_REQ)==0){
							
							istipoCarta=Boolean.FALSE;
							istipoReque=Boolean.TRUE;
							istipoCartaMult=Boolean.FALSE;
							//correlativoReque=ficalizacionBo.correlativo(tipoDocRequerimientoId); /* Dany A. solicito digitar correlativo - 18/07/14 */
							//loadAniosReq();

						}else if (value.toString().compareTo(Constante.TIPO_DOC_CARTA_MULT)==0){
							
							istipoCarta=Boolean.FALSE;
							istipoReque=Boolean.FALSE;
							istipoCartaMult=Boolean.TRUE;
						}
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					WebMessages.messageFatal(e);
				}
			}

			
			public void loadTipoProgramaRequerimiento(ValueChangeEvent event) {
				try {
					HtmlComboBox combo = (HtmlComboBox) event.getComponent();
					String value = combo.getValue().toString();
					if (value != null) {
						tipoProgramaRequerimientoId= (Integer) mapTipoProgramaRequerimiento.get(value);
						setCmbTipoProgramaRequerimiento(value);
					}
					if(value!=null){
						isPrograma=Boolean.TRUE;
						loadAnios();
						loadReferencia();
					}
				} catch (Exception e) {
					e.printStackTrace();
					WebMessages.messageFatal(e);
				}
			}
			
			
			public void loadInspectorxRequerimiento(ValueChangeEvent event) {
				try {
					HtmlComboBox combo = (HtmlComboBox) event.getComponent();
					String value = combo.getValue().toString();
					if (value != null) {
						inspectorId = (Integer) mapInspector.get(value);
						setCmbInspector(value);
					}
					if(inspectorId!=null){
						
						isInspector=Boolean.TRUE;

                    }
				} catch (Exception e) {
					e.printStackTrace();
					WebMessages.messageFatal(e);
				}
			}
			
			public void loadHorarioRequerimiento(ValueChangeEvent event) {
				try {
					HtmlComboBox combo = (HtmlComboBox) event.getComponent();
					String value = combo.getValue().toString();
					if (value != null) {
						HorarioId= (Integer) mapHorario.get(value);
						setCmbHorario(value);
					}
				} catch (Exception e) {
					e.printStackTrace();
					WebMessages.messageFatal(e);
				}
			}
			
			
			
			public String salir(){
				return sendRedirectPrincipal();

			}
			
			 public static String arrayToString(String[] array, String delimiter) {
			        StringBuilder arTostr = new StringBuilder();
			        if (array.length > 0) {
			            arTostr.append(array[0]);
			            for (int i=1; i<array.length; i++) {
			                arTostr.append(delimiter);
			                arTostr.append(array[i]);
			            }
			        }
			        return arTostr.toString();
			    }

			public void guardar() throws Exception {

				try {

					tipoDocumento=tipoDocRequerimientoId;
					
					if (tipoProgramaRequerimientoId==null||tipoProgramaRequerimientoId==Constante.TIPO_PROGRAMA_ID){
						tipoPrograma=Constante.TIPO_PROGRAMA_ID;
					}else{
						tipoPrograma = tipoProgramaRequerimientoId;
					}
					nombreInspector=inspectorId;
					validarCorrelativo();
					//validarHorario();/*Comentado en Mayo 2017, solicitado por Correo Instituc. Jimmy Díaz (14.03.2016) e Informe 053-009-00001733*/
					
					//Lista de años a fiscalizar:
//					if (tipoDocumento==Constante.TIPO_DOC_CARTA_ID || tipoDocumento==Constante.TIPO_DOC_CARTA_MULT_ID){
//						anioInsp=arrayToString(lstAnios,",");
//					}else{
//						anioReqInsp=arrayToString(lstAniosReq,",");
//					}

						anioInsp=arrayToString(lstAnios,",");
					
					
					// Si tiene un documento asociado:
					if (asociarDocumento==Boolean.FALSE)
					{
						tipoDocumentoAsoc=null;
						nroDocumentoasociado=null;
					}
					else if(asociarDocumento==Boolean.TRUE && nroDocumentoasociado!=null)
					{
						tipoDocumentoAsoc=idDocumentoasociado.toString();//inspeccion.get(0).getTipoDocumentoId().toString();
						numeroDocumentoAsoc=nroDocumentoasociado;
						anioInsp=anioDocumentoasociado;
					}

					if (isPredioOmiso()==Boolean.TRUE){
						setOmiso(1);
					}else{
						setOmiso(0);
					}
					
					//Guardando:
				    	if (getIsCorrelativoRegistrado()==Boolean.FALSE)//&&getIsHorarioProgramado()==Boolean.FALSE /*Comentado en Mayo 2017, solicitado por Correo Instituc. Jimmy Díaz (14.03.2016) e Informe 053-009-00001733*/
				    	{
							if (datosContribuyente == null) {
								codPersona = null;
								dirPersona = null;
							} else {
								codPersona = datosContribuyente.getPersonaId();
								dirPersona = datosContribuyente.getDireccionCompleta();
							}
							if (fechaNotificacion != null) {
								if (validaPersona() == false) {
								 if (validaNotificacion() == true) {
									 if (validaPredio() == false) {
										if (tipoDocumento==Constante.TIPO_DOC_CARTA_ID)
											{
												if (codPersona!=null&&dirPersona!=null&&fechaNotificacion!=null){/*Cambiado el 23-07-14 Ceci A. solicitó no registrar "Contribuy." y "Fecha de Notif." en una primera instancia*/
													ficalizacionBo.guardarRequerimiento(tipoDocumento, correlativoCarta, tipoPrograma,nombreInspector, fechaInspeccion, codPersona,observaciones,dirPersona,fechaNotificacion,anioInsp,tipoDocumentoAsoc,numeroDocumentoAsoc,omiso,Constante.ESTADO_NOTIFICADO);	
												}else if (codPersona==null&&dirPersona==null&&fechaNotificacion==null){
													ficalizacionBo.guardarRequerimiento(tipoDocumento, correlativoCarta, tipoPrograma,nombreInspector, fechaInspeccion, 0,observaciones,null,null,anioInsp,tipoDocumentoAsoc,numeroDocumentoAsoc,omiso,Constante.ESTADO_EMITIDO);
												}else if (codPersona!=null&&dirPersona!=null&&fechaNotificacion==null){
												    ficalizacionBo.guardarRequerimiento(tipoDocumento, correlativoCarta, tipoPrograma,nombreInspector, fechaInspeccion, codPersona,observaciones,dirPersona,null,anioInsp,tipoDocumentoAsoc,numeroDocumentoAsoc,omiso,Constante.ESTADO_EMITIDO);
												}else if (codPersona==null&&dirPersona==null&&fechaNotificacion!=null){
													ficalizacionBo.guardarRequerimiento(tipoDocumento, correlativoCarta, tipoPrograma,nombreInspector, fechaInspeccion, 0,observaciones,null,fechaNotificacion,anioInsp,tipoDocumentoAsoc,numeroDocumentoAsoc,omiso,Constante.ESTADO_NOTIFICADO);
												}
												
												
											}else if(tipoDocumento==Constante.TIPO_DOC_REQ_ID){
												if (codPersona!=null&&dirPersona!=null&&fechaNotificacion!=null){/*Cambiado el 23-07-14 Ceci A. solicito no registrar "Contribuy." y "Fecha de Notif." en una primera instancia*/
													ficalizacionBo.guardarRequerimiento(tipoDocumento,correlativoReque, tipoPrograma,nombreInspector, fechaInspeccion, codPersona,observaciones,dirPersona,fechaNotificacion,anioInsp,tipoDocumentoAsoc,numeroDocumentoAsoc,omiso,Constante.ESTADO_NOTIFICADO);	
												}else if (codPersona==null&&dirPersona==null&&fechaNotificacion==null){
													ficalizacionBo.guardarRequerimiento(tipoDocumento,correlativoReque, tipoPrograma,nombreInspector, fechaInspeccion, 0,observaciones,null,null,anioInsp,tipoDocumentoAsoc,numeroDocumentoAsoc,omiso,Constante.ESTADO_EMITIDO);
												}else if (codPersona!=null&&dirPersona!=null&&fechaNotificacion==null){
													ficalizacionBo.guardarRequerimiento(tipoDocumento,correlativoReque, tipoPrograma,nombreInspector, fechaInspeccion, codPersona,observaciones,dirPersona,null,anioInsp,tipoDocumentoAsoc,numeroDocumentoAsoc,omiso,Constante.ESTADO_EMITIDO);
												}else if (codPersona==null&&dirPersona==null&&fechaNotificacion!=null){
													ficalizacionBo.guardarRequerimiento(tipoDocumento,correlativoReque, tipoPrograma,nombreInspector, fechaInspeccion, 0,observaciones,null,fechaNotificacion,anioInsp,tipoDocumentoAsoc,numeroDocumentoAsoc,omiso,Constante.ESTADO_NOTIFICADO);
													
												}
						
											}else 
												if (tipoDocumento==Constante.TIPO_DOC_CARTA_MULT_ID){
													if (codPersona!=null&&dirPersona!=null&&fechaNotificacion!=null){/*Cambiado el 23-07-14 Ceci A. solicitó no registrar "Contribuy." y "Fecha de Notif." en una primera instancia.Además agregar un tipo de doc. multiple*/
														ficalizacionBo.guardarRequerimiento(tipoDocumento, correlativoCartaMult, tipoPrograma,nombreInspector, fechaInspeccion, codPersona,observaciones,dirPersona,fechaNotificacion,anioInsp,tipoDocumentoAsoc,numeroDocumentoAsoc,omiso,Constante.ESTADO_NOTIFICADO);	
													}else if (codPersona==null&&dirPersona==null&&fechaNotificacion==null){
														ficalizacionBo.guardarRequerimiento(tipoDocumento, correlativoCartaMult, tipoPrograma,nombreInspector, fechaInspeccion, 0,observaciones,null,null,anioInsp,tipoDocumentoAsoc,numeroDocumentoAsoc,omiso,Constante.ESTADO_EMITIDO);
													}else if (codPersona!=null&&dirPersona!=null&&fechaNotificacion==null){
														ficalizacionBo.guardarRequerimiento(tipoDocumento, correlativoCartaMult, tipoPrograma,nombreInspector, fechaInspeccion, codPersona,observaciones,dirPersona,null,anioInsp,tipoDocumentoAsoc,numeroDocumentoAsoc,omiso,Constante.ESTADO_EMITIDO);
													}else if (codPersona==null&&dirPersona==null&&fechaNotificacion!=null){
														ficalizacionBo.guardarRequerimiento(tipoDocumento, correlativoCartaMult, tipoPrograma,nombreInspector, fechaInspeccion, 0,observaciones,null,fechaNotificacion,anioInsp,tipoDocumentoAsoc,numeroDocumentoAsoc,omiso,Constante.ESTADO_NOTIFICADO);
													}
												}
											
								//Aqui registra el/los horarios:
										/*Comentado en Mayo 2017, solicitado por Correo Instituc. Jimmy Díaz (14.03.2016) e Informe 053-009-00001733*/
//										if (lstHorarios != null
//												&& lstHorarios.length > 0) {
//											for (String p1 : lstHorarios) {
//												
//												//Integer inspc_id = ficalizacionBo.getUltimaInspeccion(codPersona);/*Cambiado el 23-07-14 Ceci A. solicito no registrar "Contribuy." en una primera instancia*/
//												Integer inspc_id = ficalizacionBo.getUltimaInspeccion();
//												RpFiscalizacionHorarioDetalle rpFiscalizacionHora = new  RpFiscalizacionHorarioDetalle();
//												
//												rpFiscalizacionHora.setInspeccionId(inspc_id);
//												rpFiscalizacionHora.setHorarioId(mapMpTipoHoraSeleccionados.get(p1));
//												rpFiscalizacionHora.setEstado("1");
//												
//												ficalizacionBo.create(rpFiscalizacionHora);
//												
//					
//											}
//										}
									
								//Aqui registra el detalle:
									
										/*Cambiado el 23-07-14 Ceci Alvarado solicito no registrar el "detalle de Djs" en una primera instancia - CAMBIO POR BUSQUEDA DE PREDIOS*/
										int cuentaSeleccion = 0;
									
										int inspcc_id = ficalizacionBo.getUltimaInspeccion();
										
										for (FindRpDjPredial i : listDjPredials) {
										if (i.isSelected()) {
											ficalizacionBo.guardarRequerimientoDetalle(nombreInspector, i.getAnioDj(), i.getDjId(), i.getPredioId(),inspcc_id,fechaInspeccion
													      ,i.getUbicacionId(),i.getSectorId(),i.getTipoViaId(),i.getViaId(),i.getManzana(),i.getCuadra(),i.getLado(),i.getDireccionCompleta());
											//(de.getDjId())
											cuentaSeleccion = cuentaSeleccion + 1;
										}
									  }
								 }else {addErrorMessage(getMsg("Seleccione un predio."));}
							 } else {addErrorMessage(getMsg("Verifique la fecha de notificación."));}
						   } else {addErrorMessage(getMsg("Seleccione un contribuyente y predio."));} 	
						}	//fin if
						else {
							
							if (tipoDocumento==Constante.TIPO_DOC_CARTA_ID)
							{
								if (codPersona!=null&&dirPersona!=null&&fechaNotificacion!=null){/*Cambiado el 23-07-14 Ceci A. solicitó no registrar "Contribuy." y "Fecha de Notif." en una primera instancia*/
									ficalizacionBo.guardarRequerimiento(tipoDocumento, correlativoCarta, tipoPrograma,nombreInspector, fechaInspeccion, codPersona,observaciones,dirPersona,fechaNotificacion,anioInsp,tipoDocumentoAsoc,numeroDocumentoAsoc,omiso,Constante.ESTADO_NOTIFICADO);	
								}else if (codPersona==null&&dirPersona==null&&fechaNotificacion==null){
									ficalizacionBo.guardarRequerimiento(tipoDocumento, correlativoCarta, tipoPrograma,nombreInspector, fechaInspeccion, 0,observaciones,null,null,anioInsp,tipoDocumentoAsoc,numeroDocumentoAsoc,omiso,Constante.ESTADO_EMITIDO);
								}else if (codPersona!=null&&dirPersona!=null&&fechaNotificacion==null){
								    ficalizacionBo.guardarRequerimiento(tipoDocumento, correlativoCarta, tipoPrograma,nombreInspector, fechaInspeccion, codPersona,observaciones,dirPersona,null,anioInsp,tipoDocumentoAsoc,numeroDocumentoAsoc,omiso,Constante.ESTADO_EMITIDO);
								}else if (codPersona==null&&dirPersona==null&&fechaNotificacion!=null){
									ficalizacionBo.guardarRequerimiento(tipoDocumento, correlativoCarta, tipoPrograma,nombreInspector, fechaInspeccion, 0,observaciones,null,fechaNotificacion,anioInsp,tipoDocumentoAsoc,numeroDocumentoAsoc,omiso,Constante.ESTADO_NOTIFICADO);
								}
								
								
							}else if(tipoDocumento==Constante.TIPO_DOC_REQ_ID){
								if (codPersona!=null&&dirPersona!=null&&fechaNotificacion!=null){/*Cambiado el 23-07-14 Ceci A. solicito no registrar "Contribuy." y "Fecha de Notif." en una primera instancia*/
									ficalizacionBo.guardarRequerimiento(tipoDocumento,correlativoReque, tipoPrograma,nombreInspector, fechaInspeccion, codPersona,observaciones,dirPersona,fechaNotificacion,anioInsp,tipoDocumentoAsoc,numeroDocumentoAsoc,omiso,Constante.ESTADO_NOTIFICADO);	
								}else if (codPersona==null&&dirPersona==null&&fechaNotificacion==null){
									ficalizacionBo.guardarRequerimiento(tipoDocumento,correlativoReque, tipoPrograma,nombreInspector, fechaInspeccion, 0,observaciones,null,null,anioInsp,tipoDocumentoAsoc,numeroDocumentoAsoc,omiso,Constante.ESTADO_EMITIDO);
								}else if (codPersona!=null&&dirPersona!=null&&fechaNotificacion==null){
									ficalizacionBo.guardarRequerimiento(tipoDocumento,correlativoReque, tipoPrograma,nombreInspector, fechaInspeccion, codPersona,observaciones,dirPersona,null,anioInsp,tipoDocumentoAsoc,numeroDocumentoAsoc,omiso,Constante.ESTADO_EMITIDO);
								}else if (codPersona==null&&dirPersona==null&&fechaNotificacion!=null){
									ficalizacionBo.guardarRequerimiento(tipoDocumento,correlativoReque, tipoPrograma,nombreInspector, fechaInspeccion, 0,observaciones,null,fechaNotificacion,anioInsp,tipoDocumentoAsoc,numeroDocumentoAsoc,omiso,Constante.ESTADO_NOTIFICADO);
									
								}
		
							}else 
								if (tipoDocumento==Constante.TIPO_DOC_CARTA_MULT_ID){
									if (codPersona!=null&&dirPersona!=null&&fechaNotificacion!=null){/*Cambiado el 23-07-14 Ceci A. solicitó no registrar "Contribuy." y "Fecha de Notif." en una primera instancia.Además agregar un tipo de doc. multiple*/
										ficalizacionBo.guardarRequerimiento(tipoDocumento, correlativoCartaMult, tipoPrograma,nombreInspector, fechaInspeccion, codPersona,observaciones,dirPersona,fechaNotificacion,anioInsp,tipoDocumentoAsoc,numeroDocumentoAsoc,omiso,Constante.ESTADO_NOTIFICADO);	
									}else if (codPersona==null&&dirPersona==null&&fechaNotificacion==null){
										ficalizacionBo.guardarRequerimiento(tipoDocumento, correlativoCartaMult, tipoPrograma,nombreInspector, fechaInspeccion, 0,observaciones,null,null,anioInsp,tipoDocumentoAsoc,numeroDocumentoAsoc,omiso,Constante.ESTADO_EMITIDO);
									}else if (codPersona!=null&&dirPersona!=null&&fechaNotificacion==null){
										ficalizacionBo.guardarRequerimiento(tipoDocumento, correlativoCartaMult, tipoPrograma,nombreInspector, fechaInspeccion, codPersona,observaciones,dirPersona,null,anioInsp,tipoDocumentoAsoc,numeroDocumentoAsoc,omiso,Constante.ESTADO_EMITIDO);
									}else if (codPersona==null&&dirPersona==null&&fechaNotificacion!=null){
										ficalizacionBo.guardarRequerimiento(tipoDocumento, correlativoCartaMult, tipoPrograma,nombreInspector, fechaInspeccion, 0,observaciones,null,fechaNotificacion,anioInsp,tipoDocumentoAsoc,numeroDocumentoAsoc,omiso,Constante.ESTADO_NOTIFICADO);
									}
								}
							
							
							//Aqui registra el/los horarios:
							/*Comentado en Mayo 2017, solicitado por Correo Instituc. Jimmy Díaz (14.03.2016) e Informe 053-009-00001733 - Inicio*/
//								if (lstHorarios != null
//										&& lstHorarios.length > 0) {
//									for (String p1 : lstHorarios) {
//										
//										//Integer inspc_id = ficalizacionBo.getUltimaInspeccion(codPersona);/*Cambiado el 23-07-14 Ceci A. solicito no registrar "Contribuy." en una primera instancia*/
//										Integer inspc_id = ficalizacionBo.getUltimaInspeccion();
//										RpFiscalizacionHorarioDetalle rpFiscalizacionHora = new  RpFiscalizacionHorarioDetalle();
//										
//										rpFiscalizacionHora.setInspeccionId(inspc_id);
//										rpFiscalizacionHora.setHorarioId(mapMpTipoHoraSeleccionados.get(p1));
//										rpFiscalizacionHora.setEstado("1");
//										
//										ficalizacionBo.create(rpFiscalizacionHora);
//										
//			
//									}
//								}
							/*Comentado en Mayo 2017, solicitado por Correo Instituc. Jimmy Díaz (14.03.2016) e Informe 053-009-00001733 - Fin*/
							
							//Aqui registra el detalle:
							
								/*Cambiado el 23-07-14 Ceci Alvarado solicito no registrar el "detalle de Djs" en una primera instancia - CAMBIO POR BUSQUEDA DE PREDIOS*/
								int cuentaSeleccion = 0;
							
								int inspcc_id = ficalizacionBo.getUltimaInspeccion();
								
								for (FindRpDjPredial i : listDjPredials) {
								if (i.isSelected()) {
									ficalizacionBo.guardarRequerimientoDetalle(nombreInspector, i.getAnioDj(), i.getDjId(), i.getPredioId(),inspcc_id,fechaInspeccion
											      ,i.getUbicacionId(),i.getSectorId(),i.getTipoViaId(),i.getViaId(),i.getManzana(),i.getCuadra(),i.getLado(),i.getDireccionCompleta());
									//(de.getDjId())
									cuentaSeleccion = cuentaSeleccion + 1;
								}
							  }
							
						}//fin else
					 }//fin general
					//}
					/**
					 * 
					 */
				else {
					if (getIsHorarioProgramado() == Boolean.TRUE) {
						addErrorMessage(getMsg("El horario: "
								+ listaHorario.get(0).getIntervaloHorario()
								+ " ya se encuentra asignado a "
								+ listaHorario.get(0).getNombreUsuario() + "."));
					} else {
						addErrorMessage(getMsg("No se registro el requerimiento,verifique la información."));
					}
	
				}

//						for (FindInspeccionDj i : listaDjInspeccion) {
//							if (i.isSelected()) {
//								ficalizacionBo.guardarRequerimientoDetalle(nombreInspector, i.getAnnoDj(), i.getDjId(), i.getPredioId(),inspcc_id,fechaInspeccion);
//								//(de.getDjId())
//								cuentaSeleccion = cuentaSeleccion + 1;
//							}
//						}
						

				} catch (Exception e) {
					e.printStackTrace();
					WebMessages.messageFatal(e);
				}

			}
			
				public Boolean validaNotificacion() {
					boolean fechaCorrecta = true;
					if (fechaNotificacion.equals(fechaInspeccion)|| fechaNotificacion.before(fechaInspeccion)) {
						fechaCorrecta = true;
					} else {
						fechaCorrecta = false;
					}
					return fechaCorrecta;
				}
			
				public Boolean validaPersona(){
					boolean sinContribuyente = true;
						if(fechaNotificacion!=null&&codPersona==null){
							sinContribuyente=true;
						}else{
							sinContribuyente=false;
						}
					    return 	sinContribuyente;
				}
				
				public Boolean validaPredio(){
					
					boolean sinPredio = true;
					Integer cuentaSeleccion=0;
					
					for (FindRpDjPredial i : listDjPredials) {
						if (i.isSelected()) {
							cuentaSeleccion = cuentaSeleccion + 1;
						}
					  }
						if(fechaNotificacion!=null&&codPersona!=null &&listDjPredials.size()==0&&cuentaSeleccion==0){
							sinPredio=true;
						}
						else if(fechaNotificacion!=null&&codPersona!=null &&listDjPredials.size()>0&&cuentaSeleccion==0){
							sinPredio=true;
						}
						else if(fechaNotificacion!=null&&codPersona!=null &&listDjPredials.size()>0&&cuentaSeleccion>0){
							sinPredio=false;
						}
					    return 	sinPredio;
				}
			
			public void setPersonaInspeccion(){
				String destinoRefresh = FacesUtil.getRequestParameter("destinoRefresh");
				
				BuscarRequerimientoPersonaManaged buscarPersonaManaged=(BuscarRequerimientoPersonaManaged)getManaged("buscarRequerimientoPersonaManaged");
				buscarPersonaManaged.setPantallaUso(ReusoFormCns.BUSQU_PER_INSPECCION);
				buscarPersonaManaged.setDestinoRefresh(destinoRefresh);
				
			}
			
			public void copiaPersona(BuscarPersonaDTO persona){
					setDatosContribuyente(persona);
			}
	
//			public String sendRedirectBusqueda() {
//				return "/sisat/predial/buscarrequerimiento.xhtml";
//			}
			
			/*Cambiado el 23-07-14 Ceci A. solicitó registrar "Predios" y no "Djs"*/
			public void valueChangeListenerItem(FindRpDjPredial predioSeleccion) {
				if (predioSeleccion.isSelected()) {
					if (predioSeleccionado.equals(predioSeleccion)) {
						predioSeleccionado = new FindRpDjPredial();
					}
				} else {
					predioSeleccionado = predioSeleccion;
				}
			}
			
			public void seleccionAsociarDoc() throws Exception {
				if (asociarDocumento==Boolean.TRUE) {
					setAsociarDocumento(true);
					//loadReferencia();
			} 
					else {
					setAsociarDocumento(false);
				}
			}
					
			public void validarCorrelativo() throws Exception {
				if (tipoDocRequerimientoId==Constante.TIPO_DOC_CARTA_ID){
					if (correlativoCarta !=null){
						listacorrelativo=ficalizacionBo.getInspeccionByCorrelativo(correlativoCarta, Constante.TIPO_DOC_CARTA_ID);
						
						if (listacorrelativo!=null){
							WebMessages.messageError("El número de carta: " + listacorrelativo.get(0).getNroRequerimiento()+" ya se encuentra registrado" );
							isCorrelativoRegistrado=Boolean.TRUE;
						}else{
							isCorrelativoRegistrado=Boolean.FALSE;
						}
							
						
						listacorrelativo=ficalizacionBo.getInspeccionByCorrelativo(correlativoCarta, Constante.TIPO_DOC_CARTA_MULT_ID);
						
						if (listacorrelativo!=null){
							WebMessages.messageError("El número de carta: " + listacorrelativo.get(0).getNroRequerimiento()+" ya se encuentra registrado" );
							isCorrelativoRegistrado=Boolean.TRUE;
						}else{
							isCorrelativoRegistrado=Boolean.FALSE;
						}
							
						
					}
				}else
					
				if (tipoDocRequerimientoId==Constante.TIPO_DOC_REQ_ID){
						listacorrelativo=ficalizacionBo.getInspeccionByCorrelativo(correlativoReque, Constante.TIPO_DOC_REQ_ID);
						
						if (listacorrelativo!=null){
							WebMessages.messageError("El número de requerimiento: " + listacorrelativo.get(0).getNroRequerimiento()+" ya se encuentra registrado" );
							isCorrelativoRegistrado=Boolean.TRUE;
						}else{
							isCorrelativoRegistrado=Boolean.FALSE;
						}
				 }
				else
					if (tipoDocRequerimientoId==Constante.TIPO_DOC_CARTA_MULT_ID){
						if (correlativoCartaMult !=null){
							listacorrelativo=ficalizacionBo.getInspeccionByCorrelativo(correlativoCartaMult, Constante.TIPO_DOC_CARTA_ID);
							
							if (listacorrelativo!=null){
								WebMessages.messageError("El número de carta: " + listacorrelativo.get(0).getNroRequerimiento()+" ya se encuentra registrado" );
								isCorrelativoRegistrado=Boolean.TRUE;
							}else{
								isCorrelativoRegistrado=Boolean.FALSE;
							}
						}
					}
				}
			
			 public void validarHorario() throws Exception {
				 try{

				 if (inspectorId!=null&&fechaInspeccion !=null&&lstHorarios != null&&lstHorarios.length > 0){
							 for (String p1 : lstHorarios) {

									listaHorario=ficalizacionBo.getHorarioByInspector(inspectorId, fechaInspeccion, mapMpTipoHoraSeleccionados.get(p1));

									if (listaHorario!=null)
								 	 {
									     //WebMessages.messageError("El horario: " + listaHorario.get(0).getIntervaloHorario()+" ya se encuentra asignado a "+listaHorario.get(0).getNombreUsuario()+".");
										 isHorarioProgramado=Boolean.TRUE;
									     
									 }else //if (listaHorario.isEmpty())
									 {
										 isHorarioProgramado=Boolean.FALSE;
									 }
     						}
				 }
			
			 }catch (Exception e) {
				e.printStackTrace();
				WebMessages.messageFatal(e);
			}
		}

	public void quitarDocAsociado() {
		debug("inicio - quitarDocAsociado");
		setNroDocumentoasociado("");
		setAnioDocumentoasociado("");
		debug("fin - quitarDocAsociado");

	}
	
	public void checkOmiso(ValueChangeEvent event) {
		Boolean check = (Boolean) event.getNewValue();
		if (check == Boolean.TRUE) {
			setPredioOmiso(true);
		} else {
			setPredioOmiso(false);
		}
	}
		 

	public String getCmbTipoDocRequerimiento() {
		return cmbTipoDocRequerimiento;
	}

	public void setCmbTipoDocRequerimiento(String cmbTipoDocRequerimiento) {
		this.cmbTipoDocRequerimiento = cmbTipoDocRequerimiento;
	}

	public HtmlComboBox getCmbxTipoDocRequerimiento() {
		return cmbxTipoDocRequerimiento;
	}

	public void setCmbxTipoDocRequerimiento(HtmlComboBox cmbxTipoDocRequerimiento) {
		this.cmbxTipoDocRequerimiento = cmbxTipoDocRequerimiento;
	}

	public Integer getTipoDocRequerimientoId() {
		return tipoDocRequerimientoId;
	}

	public void setTipoDocRequerimientoId(Integer tipoDocRequerimientoId) {
		this.tipoDocRequerimientoId = tipoDocRequerimientoId;
	}

	public HashMap<String, Integer> getMapTipoDocRequerimiento() {
		return mapTipoDocRequerimiento;
	}

	public void setMapTipoDocRequerimiento(
			HashMap<String, Integer> mapTipoDocRequerimiento) {
		this.mapTipoDocRequerimiento = mapTipoDocRequerimiento;
	}

	public List<SelectItem> getListaTipoDocRequerimiento() {
		return listaTipoDocRequerimiento;
	}

	public void setListaTipoDocRequerimiento(
			List<SelectItem> listaTipoDocRequerimiento) {
		this.listaTipoDocRequerimiento = listaTipoDocRequerimiento;
	}


	public String getCmbTipoProgramaRequerimiento() {
		return cmbTipoProgramaRequerimiento;
	}

	public void setCmbTipoProgramaRequerimiento(String cmbTipoProgramaRequerimiento) {
		this.cmbTipoProgramaRequerimiento = cmbTipoProgramaRequerimiento;
	}

	public HtmlComboBox getCmbxTipoProgramaRequerimiento() {
		return cmbxTipoProgramaRequerimiento;
	}

	public void setCmbxTipoProgramaRequerimiento(
			HtmlComboBox cmbxTipoProgramaRequerimiento) {
		this.cmbxTipoProgramaRequerimiento = cmbxTipoProgramaRequerimiento;
	}

	public Integer getTipoProgramaRequerimientoId() {
		return tipoProgramaRequerimientoId;
	}

	public void setTipoProgramaRequerimientoId(Integer tipoProgramaRequerimientoId) {
		this.tipoProgramaRequerimientoId = tipoProgramaRequerimientoId;
	}

	public HashMap<String, Integer> getMapTipoProgramaRequerimiento() {
		return mapTipoProgramaRequerimiento;
	}

	public void setMapTipoProgramaRequerimiento(
			HashMap<String, Integer> mapTipoProgramaRequerimiento) {
		this.mapTipoProgramaRequerimiento = mapTipoProgramaRequerimiento;
	}

	public List<SelectItem> getListaTipoProgramaRequerimiento() {
		return listaTipoProgramaRequerimiento;
	}

	public void setListaTipoProgramaRequerimiento(
			List<SelectItem> listaTipoProgramaRequerimiento) {
		this.listaTipoProgramaRequerimiento = listaTipoProgramaRequerimiento;
	}

	

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCmbInspector() {
		return cmbInspector;
	}

	public void setCmbInspector(String cmbInspector) {
		this.cmbInspector = cmbInspector;
	}

	public HtmlComboBox getCmbxInspector() {
		return cmbxInspector;
	}

	public void setCmbxInspector(HtmlComboBox cmbxInspector) {
		this.cmbxInspector = cmbxInspector;
	}

	public Integer getInspectorId() {
		return inspectorId;
	}

	public void setInspectorId(Integer inspectorId) {
		this.inspectorId = inspectorId;
	}

	public HashMap<String, Integer> getMapInspector() {
		return mapInspector;
	}

	public void setMapInspector(HashMap<String, Integer> mapInspector) {
		this.mapInspector = mapInspector;
	}

	public List<SelectItem> getListaInspectores() {
		return listaInspectores;
	}

	public void setListaInspectores(List<SelectItem> listaInspectores) {
		this.listaInspectores = listaInspectores;
	}

	public String getCmbHorario() {
		return cmbHorario;
	}

	public void setCmbHorario(String cmbHorario) {
		this.cmbHorario = cmbHorario;
	}

	public HtmlComboBox getCmbxHorario() {
		return cmbxHorario;
	}

	public void setCmbxHorario(HtmlComboBox cmbxHorario) {
		this.cmbxHorario = cmbxHorario;
	}

	public Integer getHorarioId() {
		return HorarioId;
	}

	public void setHorarioId(Integer horarioId) {
		HorarioId = horarioId;
	}

	public HashMap<String, Integer> getMapHorario() {
		return mapHorario;
	}

	public void setMapHorario(HashMap<String, Integer> mapHorario) {
		this.mapHorario = mapHorario;
	}

	public List<SelectItem> getListaHorarios() {
		return listaHorarios;
	}

	public void setListaHorarios(List<SelectItem> listaHorarios) {
		this.listaHorarios = listaHorarios;
	}

	public FindInpscDocTipo getCorrelativoDoc() {
		return correlativoDoc;
	}

	public void setCorrelativoDoc(FindInpscDocTipo correlativoDoc) {
		this.correlativoDoc = correlativoDoc;
	}

	public String getCorrelativoCarta() {
		return correlativoCarta;
	}

	public void setCorrelativoCarta(String correlativoCarta) {
		this.correlativoCarta = correlativoCarta;
	}

	public String getCorrelativoReque() {
		return correlativoReque;
	}

	public void setCorrelativoReque(String correlativoReque) {
		this.correlativoReque = correlativoReque;
	}

	public Boolean getIstipoCarta() {
		return istipoCarta;
	}

	public void setIstipoCarta(Boolean istipoCarta) {
		this.istipoCarta = istipoCarta;
	}

	public Boolean getIstipoReque() {
		return istipoReque;
	}

	public void setIstipoReque(Boolean istipoReque) {
		this.istipoReque = istipoReque;
	}

	

	public Integer getCodPersona() {
		return codPersona;
	}

	public void setCodPersona(Integer codPersona) {
		this.codPersona = codPersona;
	}

	public Integer getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(Integer tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Integer getTipoPrograma() {
		return tipoPrograma;
	}

	public void setTipoPrograma(Integer tipoPrograma) {
		this.tipoPrograma = tipoPrograma;
	}

	public Integer getNombreInspector() {
		return nombreInspector;
	}

	public void setNombreInspector(Integer nombreInspector) {
		this.nombreInspector = nombreInspector;
	}

	public Integer getTipoHorario() {
		return tipoHorario;
	}

	public void setTipoHorario(Integer tipoHorario) {
		this.tipoHorario = tipoHorario;
	}

	public Date getFechaNotificacion() {
		return fechaNotificacion;
	}

	public void setFechaNotificacion(Date fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}

	public Date getFechaInspeccion() {
		return fechaInspeccion;
	}

	public void setFechaInspeccion(Date fechaInspeccion) {
		this.fechaInspeccion = fechaInspeccion;
	}

	
//	public Date getFechaInspeccion() {
//		if (valor.equals("") || valor == null)
//			 return null;
//		 else
//		  this.valor=new SimpleDateFormat("dd/MM/yyyy HH:mm").format((Date)fechaInspeccion);
//		  return fechaInspeccion;
//		
//	}
//
//	public void setFechaInspeccion(Date fechaInspeccion) {
//		if (fechaInspeccion== null)
//			this.valor="";
//		else
//			this.valor=new SimpleDateFormat("dd/MM/yyyy HH:mm").format((Date)fechaInspeccion);
//		
//	}
	
	public BuscarPersonaDTO getDatosContribuyente() {
		return datosContribuyente;
	}

	public void setDatosContribuyente(BuscarPersonaDTO datosContribuyente) {
		this.datosContribuyente = datosContribuyente;
	}

	public List<FindInspeccionDj> getListaDjInspeccion() {
		return listaDjInspeccion;
	}

	public void setListaDjInspeccion(List<FindInspeccionDj> listaDjInspeccion) {
		this.listaDjInspeccion = listaDjInspeccion;
	}

//	public FindInspeccionDj getFindDjInsp() {
//		return findDjInsp;
//	}
//
//	public void setFindDjInsp(FindInspeccionDj findDjInsp) {
//		this.findDjInsp = findDjInsp;
//	}


	public List<FindInspeccionDj> getLstDjInspeccionDTOs() {
		return lstDjInspeccionDTOs;
	}

	public void setLstDjInspeccionDTOs(List<FindInspeccionDj> lstDjInspeccionDTOs) {
		this.lstDjInspeccionDTOs = lstDjInspeccionDTOs;
	}

	public String[] getLstHorarios() {
		return lstHorarios;
	}

	public void setLstHorarios(String[] lstHorarios) {
		this.lstHorarios = lstHorarios;
	}

	public List<RpFiscalizacionHorario> getLstHorariosInspc() {
		return lstHorariosInspc;
	}

	public void setLstHorariosInspc(List<RpFiscalizacionHorario> lstHorariosInspc) {
		this.lstHorariosInspc = lstHorariosInspc;
	}

	public List<RpFiscalizacionHorario> getListaHora() {
		return listaHora;
	}

	public void setListaHora(List<RpFiscalizacionHorario> listaHora) {
		this.listaHora = listaHora;
	}

	public List<RpFiscalizacionHorario> getLstHoras() {
		return lstHoras;
	}

	public void setLstHoras(List<RpFiscalizacionHorario> lstHoras) {
		this.lstHoras = lstHoras;
	}

	public HashMap<String, Integer> getMapMpTipoHoraSeleccionados() {
		return mapMpTipoHoraSeleccionados;
	}

	public void setMapMpTipoHoraSeleccionados(
			HashMap<String, Integer> mapMpTipoHoraSeleccionados) {
		this.mapMpTipoHoraSeleccionados = mapMpTipoHoraSeleccionados;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public RpFiscalizacionInspeccion getRpInspeccion() {
		return rpInspeccion;
	}

	public void setRpInspeccion(RpFiscalizacionInspeccion rpInspeccion) {
		this.rpInspeccion = rpInspeccion;
	}


	public Boolean getOcultarBoton() {
		return ocultarBoton;
	}


	public void setOcultarBoton(Boolean ocultarBoton) {
		this.ocultarBoton = ocultarBoton;
	}


	public String getDirPersona() {
		return dirPersona;
	}


	public void setDirPersona(String dirPersona) {
		this.dirPersona = dirPersona;
	}


	public Boolean getIsDjOmisa() {
		return isDjOmisa;
	}


	public void setIsDjOmisa(Boolean isDjOmisa) {
		this.isDjOmisa = isDjOmisa;
	}


	public List<FindInspeccionHistorial> getListacorrelativo() {
		return listacorrelativo;
	}


	public void setListacorrelativo(List<FindInspeccionHistorial> listacorrelativo) {
		this.listacorrelativo = listacorrelativo;
	}


	public Boolean getIsInspector() {
		return isInspector;
	}


	public void setIsInspector(Boolean isInspector) {
		this.isInspector = isInspector;
	}


	public List<FindInspeccionByHorario> getListaHorario() {
		return listaHorario;
	}


	public void setListaHorario(List<FindInspeccionByHorario> listaHorario) {
		this.listaHorario = listaHorario;
	}


	public String getListarHorarios() {
		return listarHorarios;
	}


	public void setListarHorarios(String listarHorarios) {
		this.listarHorarios = listarHorarios;
	}


	public String getValor() {
		return valor;
	}


	public void setValor(String valor) {
		this.valor = valor;
	}


	public Boolean getIsHorarioProgramado() {
		return isHorarioProgramado;
	}


	public void setIsHorarioProgramado(Boolean isHorarioProgramado) {
		this.isHorarioProgramado = isHorarioProgramado;
	}


	public List<FindRpDjPredial> getListDjPredials() {
		return listDjPredials;
	}


	public void setListDjPredials(List<FindRpDjPredial> listDjPredials) {
		this.listDjPredials = listDjPredials;
	}

	public Boolean getSeleccionPersona() {
		return seleccionPersona;
	}

	public void setSeleccionPersona(Boolean seleccionPersona) {
		this.seleccionPersona = seleccionPersona;
	}

	public FindRpDjPredial getPredioSeleccionado() {
		return predioSeleccionado;
	}

	public void setPredioSeleccionado(FindRpDjPredial predioSeleccionado) {
		this.predioSeleccionado = predioSeleccionado;
	}

	public Boolean getIstipoCartaMult() {
		return istipoCartaMult;
	}

	public void setIstipoCartaMult(Boolean istipoCartaMult) {
		this.istipoCartaMult = istipoCartaMult;
	}

	public String getCorrelativoCartaMult() {
		return correlativoCartaMult;
	}

	public void setCorrelativoCartaMult(String correlativoCartaMult) {
		this.correlativoCartaMult = correlativoCartaMult;
	}

	public Boolean getIsCorrelativoRegistrado() {
		return isCorrelativoRegistrado;
	}

	public void setIsCorrelativoRegistrado(Boolean isCorrelativoRegistrado) {
		this.isCorrelativoRegistrado = isCorrelativoRegistrado;
	}

	public Boolean getAsociarDocumento() {
		return asociarDocumento;
	}

	public void setAsociarDocumento(Boolean asociarDocumento) {
		this.asociarDocumento = asociarDocumento;
	}

	public String getNroDocumentoasociado() {
		return nroDocumentoasociado;
	}

	public void setNroDocumentoasociado(String nroDocumentoasociado) {
		this.nroDocumentoasociado = nroDocumentoasociado;
	}

	public List<FindInspeccionHistorial> getInspeccion() {
		return inspeccion;
	}

	public void setInspeccion(List<FindInspeccionHistorial> inspeccion) {
		this.inspeccion = inspeccion;
	}

	public String[] getLstAnios() {
		return lstAnios;
	}

	public void setLstAnios(String[] lstAnios) {
		this.lstAnios = lstAnios;
	}

	public List<RpFiscalizacionProgramaDetalle> getLstAniosInspc() {
		return lstAniosInspc;
	}

	public void setLstAniosInspc(List<RpFiscalizacionProgramaDetalle> lstAniosInspc) {
		this.lstAniosInspc = lstAniosInspc;
	}

	public HashMap<String, Integer> getMapMpTipoAnioSeleccionados() {
		return mapMpTipoAnioSeleccionados;
	}

	public void setMapMpTipoAnioSeleccionados(
			HashMap<String, Integer> mapMpTipoAnioSeleccionados) {
		this.mapMpTipoAnioSeleccionados = mapMpTipoAnioSeleccionados;
	}

	public Boolean getIsPrograma() {
		return isPrograma;
	}

	public void setIsPrograma(Boolean isPrograma) {
		this.isPrograma = isPrograma;
	}

	public String[] getLstAniosReq() {
		return lstAniosReq;
	}

	public void setLstAniosReq(String[] lstAniosReq) {
		this.lstAniosReq = lstAniosReq;
	}

	public List<RpFiscalizacionProgramaDetalle> getLstAniosInspcReq() {
		return lstAniosInspcReq;
	}

	public void setLstAniosInspcReq(
			List<RpFiscalizacionProgramaDetalle> lstAniosInspcReq) {
		this.lstAniosInspcReq = lstAniosInspcReq;
	}

	public HashMap<String, Integer> getMapMpTipoAnioReqSeleccionados() {
		return mapMpTipoAnioReqSeleccionados;
	}

	public void setMapMpTipoAnioReqSeleccionados(
			HashMap<String, Integer> mapMpTipoAnioReqSeleccionados) {
		this.mapMpTipoAnioReqSeleccionados = mapMpTipoAnioReqSeleccionados;
	}

	public String getAnioInsp() {
		return anioInsp;
	}

	public void setAnioInsp(String anioInsp) {
		this.anioInsp = anioInsp;
	}

	public String getAnioReqInsp() {
		return anioReqInsp;
	}

	public void setAnioReqInsp(String anioReqInsp) {
		this.anioReqInsp = anioReqInsp;
	}

	

	public String getNumeroDocumentoAsoc() {
		return numeroDocumentoAsoc;
	}

	public void setNumeroDocumentoAsoc(String numeroDocumentoAsoc) {
		this.numeroDocumentoAsoc = numeroDocumentoAsoc;
	}

	public String getTipoDocumentoAsoc() {
		return tipoDocumentoAsoc;
	}

	public void setTipoDocumentoAsoc(String tipoDocumentoAsoc) {
		this.tipoDocumentoAsoc = tipoDocumentoAsoc;
	}

	public Integer getIdDocumentoasociado() {
		return idDocumentoasociado;
	}

	public void setIdDocumentoasociado(Integer idDocumentoasociado) {
		this.idDocumentoasociado = idDocumentoasociado;
	}


	public String getAnioDocumentoasociado() {
		return anioDocumentoasociado;
	}

	public void setAnioDocumentoasociado(String anioDocumentoasociado) {
		this.anioDocumentoasociado = anioDocumentoasociado;
	}

	public boolean isPredioOmiso() {
		return predioOmiso;
	}

	public void setPredioOmiso(boolean predioOmiso) {
		this.predioOmiso = predioOmiso;
	}

	public Integer getOmiso() {
		return omiso;
	}

	public void setOmiso(Integer omiso) {
		this.omiso = omiso;
	}



	

//	public String getFechaInspeccion() {
//		return fechaInspeccion;
//	}
//
//
//	public void setFechaInspeccion(String fechaInspeccion) {
//		this.fechaInspeccion = fechaInspeccion;
//	}

	
}
