package com.sat.sisat.fiscalizacion.managed;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

import org.richfaces.component.html.HtmlComboBox;
import org.richfaces.component.html.HtmlInputText;
import org.richfaces.model.filter.Filter;
import org.richfaces.model.selection.SimpleSelection;

import com.sat.sisat.cobranzacoactiva.business.CobranzaCoactivaBoRemote;
import com.sat.sisat.cobranzacoactiva.dto.FindCcLoteDetalleActoExp;
import com.sat.sisat.cobranzacoactiva.dto.FindCcRecTipo;
import com.sat.sisat.common.business.GeneralBoRemote;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.FacesUtil;
import com.sat.sisat.common.util.SATWEBParameterFactory;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.controlycobranza.dto.FindCcCuotasAgrupadasLote;
import com.sat.sisat.controlycobranza.dto.MpFiscalizador;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.fiscalizacion.business.FiscalizacionBo;
import com.sat.sisat.fiscalizacion.business.FiscalizacionBoRemote;
import com.sat.sisat.fiscalizacion.dto.FindInpscDocTipo;
import com.sat.sisat.fiscalizacion.dto.FindInspeccionByDetalle;
import com.sat.sisat.fiscalizacion.dto.FindInspeccionByHorario;
import com.sat.sisat.fiscalizacion.dto.FindInspeccionById;
import com.sat.sisat.fiscalizacion.dto.FindInspeccionByResultado;
import com.sat.sisat.fiscalizacion.dto.FindInspeccionDj;
import com.sat.sisat.fiscalizacion.dto.FindInspeccionDocCargoTipo;
import com.sat.sisat.fiscalizacion.dto.FindInspeccionHistorial;
import com.sat.sisat.fiscalizacion.dto.FindInspeccionHistorialDetalle;
import com.sat.sisat.menus.business.MenuBoRemote;
import com.sat.sisat.menus.dto.SimpleMenuDTO;
import com.sat.sisat.obligacion.business.ObligacionBoRemote;
import com.sat.sisat.obligacion.dto.MultaDTO;
import com.sat.sisat.obligacion.dto.ObligacionDTO;
import com.sat.sisat.obligacion.dto.SubConceptoDTO;
import com.sat.sisat.obligacion.managed.FechaVencimientoValidator;
import com.sat.sisat.papeleta.dto.FindPapeletas;
import com.sat.sisat.papeletas.managed.BuscarPersonaPapeletasManaged;
import com.sat.sisat.persistence.CrudServiceBean;
import com.sat.sisat.persistence.entity.DtFechaVencimiento;
import com.sat.sisat.persistence.entity.GnTipoDocumento;
import com.sat.sisat.persistence.entity.MpTipoDocuIdentidad;
import com.sat.sisat.persistence.entity.MpTipoPersona;
import com.sat.sisat.persistence.entity.PaInfraccion;
import com.sat.sisat.persistence.entity.PaLey;
import com.sat.sisat.persistence.entity.PaPapeleta;
import com.sat.sisat.persistence.entity.RpDjconstruccion;
import com.sat.sisat.persistence.entity.RpDjpredial;
import com.sat.sisat.persistence.entity.RpDjuso;
import com.sat.sisat.persistence.entity.RpFiscalizacionHorario;
import com.sat.sisat.persistence.entity.RpFiscalizacionHorarioDetalle;
import com.sat.sisat.persistence.entity.RpFiscalizacionInspeccion;
import com.sat.sisat.persistence.entity.RpFiscalizacionPrograma;
import com.sat.sisat.predial.business.RegistroPrediosBoRemote;
import com.sat.sisat.predial.dto.FindRpDjPredial;
import com.sat.sisat.predial.managed.BusquedaPredioManaged;
import com.sat.sisat.predial.managed.UsosPredioxNivelManaged;
import com.sat.sisat.vehicular.cns.ReusoFormCns;
import com.sat.sisat.vehicular.dto.BuscarPersonaDTO;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;


@ManagedBean
@ViewScoped
public class ActualizarRequerimientoManaged extends BaseManaged {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	FiscalizacionBoRemote ficalizacionBo;
	
	@EJB
	ObligacionBoRemote obligacionBo;
	
	@EJB
	RegistroPrediosBoRemote registroPrediosBo;
	
	@EJB
	GeneralBoRemote generalBo;
	
	@EJB
	MenuBoRemote menuBo;
	
	//public boolean rememberMe = false;
	// REGISTRAMOS DATOS DEL REQUERIMIENTO
		// OBTENEMOS EL TIPO DE DOC.: CARTA, REQUERIMIENTO
			private String cmbTipoDocRequerimiento;			
			private Integer tipoDocRequerimientoId;
			
			private HtmlComboBox cmbxTipoDocRequerimiento;
			private HashMap<String, Integer> mapTipoDocRequerimiento = new HashMap<String, Integer>();
			private HashMap<Integer,String> mapTipoDocRequerimientoR=new HashMap<Integer,String>();//Formato al recuperar desde la BD.
			private List<SelectItem> listaTipoDocRequerimiento = new ArrayList<SelectItem>();
		
		// OBTENEMOS EL TIPO PROGRAMA DE FISCALIZACION
			private String cmbTipoProgramaRequerimiento;
			private HtmlComboBox cmbxTipoProgramaRequerimiento;
			private Integer tipoProgramaRequerimientoId;
			private HashMap<String, Integer> mapTipoProgramaRequerimiento = new HashMap<String, Integer>();
			private HashMap<Integer,String> mapTipoProgramaRequerimientoR=new HashMap<Integer,String>();
			private List<SelectItem> listaTipoProgramaRequerimiento = new ArrayList<SelectItem>();
			
			//OBTENEMOS EL NUMERO DE VALOR
			
			private FindInpscDocTipo correlativoDoc;
			private String descripcion;
			
			// OBTENEMOS EL INSPECTOR:
			private String cmbInspector;
			private HtmlComboBox cmbxInspector;
			private Integer inspectorId;
			private HashMap<String, Integer> mapInspector = new HashMap<String, Integer>();
			private HashMap<Integer,String> mapInspectorR=new HashMap<Integer,String>();
			private List<SelectItem> listaInspectores = new ArrayList<SelectItem>();
			
	
			// OBTENEMOS EL HORARIO DE FISCALIZACION
			private String cmbHorario;
			private HtmlComboBox cmbxHorario;
			private Integer HorarioId;
			private HashMap<String, Integer> mapHorario = new HashMap<String, Integer>();
			private List<SelectItem> listaHorarios = new ArrayList<SelectItem>();
			
			//private Map<String, Long> availableItems; 
			
			private String[] lstHorarios = null;
			private List<RpFiscalizacionHorario> lstHorariosInspc = null;
			private List<RpFiscalizacionHorario> listaHora;
			private List<RpFiscalizacionHorario> lstHoras = null;
			
			private HashMap<String, Integer> mapMpTipoHoraSeleccionados = new HashMap<String, Integer>();
			
			/**validamos hora por inspector y fecha:*/
			private List<FindInspeccionByHorario> listaHorario = new ArrayList<FindInspeccionByHorario>();
			private Boolean isHorarioProgramado;
			/**validamos si el inspector ha cambiado:*/
			private Boolean isinspectorProgramado;
			/**validamos si la fecha ha cambiado:*/
			private Boolean isFechaProgramada;
			
			//OBTENEMOS EL CORRELATIVO
			private String correlativoCarta;
			private String correlativoReque;
			private Boolean istipoCarta;
			private Boolean istipoReque;
			
			private Boolean isCorrelativoRegistrado;
			
			//OBTENEMOS AL CONTRIBUYENTE
			private Integer codPersona;
			private String dirPersona;
							
			
			//GENERAMOS EL REQUERIMIENTO DE INSPECCION:
			private Integer tipoDocumento;
			private Integer tipoPrograma;
			private Integer nombreInspector;
			private Integer tipoHorario;
			private Date fechaNotificacion;
			private Date fechaInspeccion;
			private String observaciones;
			private Integer estadoRecuperado;
			private Integer nombreInspectorAr;
			private Date fechaInspeccionAr;
			private String peridoInspeccion;
			private String nroDocAsociado;
			
			private BuscarPersonaDTO datosContribuyente;

			//OBTENEMOS LOS PREDIOS CON DJS FISCALIZADAS
			
			private List<FindInspeccionDj> listaDjInspeccion = new ArrayList<FindInspeccionDj>();
			private List<FindInspeccionDj> listaPredioInspeccion = new ArrayList<FindInspeccionDj>();//Listamos los predios.
			private List<FindInspeccionDj> listaDjsInspeccion = new ArrayList<FindInspeccionDj>();//Listamos las djs asociadas.
			private FindRpDjPredial predioSeleccionado = new FindRpDjPredial();//Check Predios
			private Boolean tienePredioRegistrado;
			private Boolean habilitado;
			
			private FindInspeccionDj findDjInsp = new FindInspeccionDj();
			private FindInspeccionDj djSeleccionada = new FindInspeccionDj();
			
			private List<FindInspeccionDj> lstDjInspeccionDTOs;
			private Boolean isDjOmisa;

			//OBTENEMOS LOS REQUERIMIENTOS DE INSPECCION
			private RpFiscalizacionInspeccion rpInspeccion;
			
			//---*--CARGAR FECHA DE INSPECCION
			private FindInspeccionById findInspeccion;
			private java.util.Date fechaInspeccionRegistrada;
			private Integer inspeccionId;
			private Integer tipoDcId;
			private String  documento;
			private Map<Integer, String> mapIDoc = new HashMap<Integer, String>();
			private Map<String, Integer> mapDoc = new HashMap<String, Integer>();
			
			private Map<Integer, String> mapITipoDoc= new HashMap<Integer, String>();
			private Map<String, FindInpscDocTipo> mapFindInpscDoc = new HashMap<String, FindInpscDocTipo>();
			
			private FindInspeccionHistorial currentItemValue;
			private FindInspeccionHistorial inspeccionHistorial = new FindInspeccionHistorial();
						
			private List<FindInspeccionByHorario> lstHorarioInsp = null;
			private List<FindInspeccionByDetalle> lstDetalleInsp = null;
			private List<BuscarPersonaDTO> lstPersonaInsp = null;
			
			//COMPARA:
			private List<FindInspeccionByHorario> lstHorarioBD = null;
			private String[] lstHorariosBD = null;
			private HashMap<String, Integer> mapMpTipoHoraSeleccionadosBD = new HashMap<String, Integer>();
			private static Boolean esHorarioIgual;
			
			// OBTENEMOS EL CARGO DE FISCALIZACION
			private String cmbCargo;
			private HtmlComboBox cmbxCargo;
			private Integer cargoId;
			private HashMap<String, Integer> mapCargo = new HashMap<String, Integer>();
			private HashMap<Integer,String> mapCargoR=new HashMap<Integer,String>();
			private List<SelectItem> listaCargos = new ArrayList<SelectItem>();

			
			//OBTENEMOS EL CORRELATIVO SEGUN TIPO DE CARGO
			
			private Boolean istipoFip;
			private Boolean istipoAinr;
			private Boolean istipoInforme;
			private Boolean istipoAr;
			private FindInspeccionDocCargoTipo findCargoInsp = new FindInspeccionDocCargoTipo();
			private List<FindInspeccionDocCargoTipo> correlativoCargo = new ArrayList<FindInspeccionDocCargoTipo>();
			private String correlativo;
			private String correlativoEsquela;
			private String correlativoAR;
			private String descripcionFip;
			private String descripcionAinr;
			private String correlativoFipar;
			private HtmlInputText txtCorrelativo;
			
			private boolean actaReprograma;
			private Date fechaNotificacionEsquela;
			private Date fechaNotificacionAr;
			private Date fechaNotificacionFipar;
			
			  //validamos correlativo:
			private List<FindInspeccionHistorial> listacorrelativo = new ArrayList<FindInspeccionHistorial>();
			private boolean esCorrelativoFipAr;
			private boolean esCorrelativoAr;
			private boolean esCorrelativoFip;
			private boolean esCorrelativoAinr;
			
			  //validamos días hábiles desde la notificación del AINR a la fecha de reprogramación(A.R.)
			private boolean esMayor;
			
			
			//OBTENEMOS EL INSPECTOR PARA LA REPROGRAMACIÓN
			private String cmbInspectorAr;
			private HtmlComboBox cmbxInspectorAr;
			private Integer inspectorArId;
			private HashMap<String, Integer> mapInspectorAr = new HashMap<String, Integer>();
			private HashMap<Integer,String> mapInspectorArR=new HashMap<Integer,String>();
			private List<SelectItem> listaInspectoresAr = new ArrayList<SelectItem>();
			
			//OBTENEMOS EL HORARIO PARA LA REPROGRAMACION
			
			private String[] lstHorariosAr = null;
			private List<RpFiscalizacionHorario> lstHorariosArInspc = null;
			private HashMap<String, Integer> mapMpTipoHoraArSeleccionados = new HashMap<String, Integer>();
			
			/**Validacion de la hora y fecha de reprogramacion*/
			private Boolean isHorarioReprogramado;
			
			private Date fechaResultado=null;
			
			private Boolean ocultarBoton =false;
			
			//OBTENEMOS LOS DATOS DEL RESULTADO
			private List<FindInspeccionByResultado> inspeccionResultado = new ArrayList<FindInspeccionByResultado>();
			private FindInspeccionByResultado inspeccionResultadoTabla;
			private Boolean isNotificado;
			
			//OBTENEMOS EL ESTADO
			private Boolean esEmitido;
			private Boolean esNotif;
			private Boolean esFIP;
			private Boolean esAINR;
			private Boolean conResultado;
			private Boolean esAr;
			private Boolean esArFIP;
			private Boolean esINF;
			
			//FECHA DE NOTIFICACION DE RESULTADO:
			private Date fechaNotificacionfip;
			private Date fechaNotificacionainr;
			
			
			//GENERAR MULTA
			private List<FindInspeccionHistorialDetalle> historico = new ArrayList<FindInspeccionHistorialDetalle>();
			private String cmbUnidad = "Dpto. Fiscalización y Censo Predial";
			private String cmbSubConcepto;
			private String subConceptoDescripcion;
			private String cmbTributoAsociado = "Predial";
			
			private Integer contexto = 1;
			private Date fechaAdquision;
			private int TIPOPREDIO = 1;
			private int TIPOVEHICULO = 2;
			private int tipoReferenciaOblg = TIPOPREDIO;
			private String codigoPlacaReferencia;
			private Boolean presentoDocumentos;
			private String glosa;
			private int MULTAS = 12;
			@SuppressWarnings("unused")
			private int COSTAS = 5;
			@SuppressWarnings("unused")
			private int GASTOS = 6;
			private int annoAfectacion;
			
			
			private List<FindInspeccionDj> listaPrediosInspeccion = new ArrayList<FindInspeccionDj>();
			private List<FindRpDjPredial> listDjPredials = new ArrayList<FindRpDjPredial>();
			
			
			private ObligacionDTO obligacionDTO;
			private SubConceptoDTO subConceptoDTO;
			private HashMap<String, SubConceptoDTO> mapSupConcepto = new HashMap<String, SubConceptoDTO>();
			private List<SelectItem> listSubConceptoDTOItems = new ArrayList<SelectItem>();
			
			private List<MultaDTO> lstMultaDTOs;
			private MultaDTO multaDTOSeleccionada = new MultaDTO();
			BigDecimal totalAPagar = new BigDecimal(0);
			BigDecimal totalDscto = new BigDecimal(0);
			BigDecimal totalSubTotal = new BigDecimal(0);
			BigDecimal totalInteres = new BigDecimal(0);
			BigDecimal totalMonto = new BigDecimal(0);
			
			
			// INICIO PERMISOS PARA EL MODULO -=CRAMIREZ=-
			 	private List<SimpleMenuDTO> listPermisosSubmenu = new ArrayList<SimpleMenuDTO>();	 
			 	private boolean permisoModificarActualizar;
			// FIN PERMISOS PARA EL MODULO
			
			public ActualizarRequerimientoManaged() throws Exception {}
			
			
			public void permisosMenu() {	
				try {
					int submenuId = Constante.CONTROL_DE_REQUERIMIENTOS;
					
					int permisoModificarActualizarId = Constante.MODIFICAR_ACTUALIZAR;
					
				 	permisoModificarActualizar = false;
					
					listPermisosSubmenu  = menuBo.getAccesosSubmenuUsuario( getSessionManaged().getUsuarioLogIn().getUsuarioId() , submenuId);
					
					Iterator<SimpleMenuDTO> menuIterar = listPermisosSubmenu.iterator();
					while (menuIterar.hasNext()) {
						SimpleMenuDTO lsm = menuIterar.next();
						
						if(lsm.getItemId() == permisoModificarActualizarId) {
							permisoModificarActualizar = true;
						}
					}
				} catch (SisatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
			@PostConstruct
			public void init() throws Exception {
				permisosMenu();
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
						mapTipoDocRequerimientoR.put(obj.getTipoDocumentoId(), obj.getDescripcionTipoDocumento().trim());
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
						mapTipoProgramaRequerimientoR.put(obj.getProgramaId(), obj.getNombrePrograma().trim());
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
						mapInspectorR.put(obj.getIdfiscalizador(), obj.getNombresApellidos().trim());

					}

					loadHorarios();
					
					/* COMBOBOX:: TIPO DE DOCUMENTO DE CARGO */
					List<FindInspeccionDocCargoTipo> lstCcTipoCargo = ficalizacionBo.getAllTipoDocCargo();
					Iterator<FindInspeccionDocCargoTipo> ite = lstCcTipoCargo.iterator();
					listaCargos = new ArrayList<SelectItem>();

					while (ite.hasNext()) {
						FindInspeccionDocCargoTipo obj = ite.next();
						listaCargos.add(new SelectItem(obj.getDescripcionCortaTipoDocumento(), String.valueOf(obj.getTipoDocumentoId())));
						mapCargo.put(obj.getDescripcionCortaTipoDocumento().trim(),obj.getTipoDocumentoId());
						mapCargoR.put(obj.getTipoDocumentoId(), obj.getDescripcionCortaTipoDocumento().trim());
						
					}
					/*- COMBOBOX:: TIPO DE DOCUMENTO DE CARGO -*/
					
					/* COMBOBOX:: INSPECTOR PARA AR*/
					List<MpFiscalizador> lstMpInspectorAr = ficalizacionBo.getAllInspector();
					Iterator<MpFiscalizador> iter = lstMpInspectorAr.iterator();
					listaInspectoresAr = new ArrayList<SelectItem>();

					while (iter.hasNext()) {
						MpFiscalizador obj = iter.next();
						listaInspectoresAr.add(new SelectItem(obj.getNombresApellidos(), String.valueOf(obj.getIdfiscalizador())));
						mapInspectorAr.put(obj.getNombresApellidos().trim(),obj.getIdfiscalizador());
						mapInspectorArR.put(obj.getIdfiscalizador(), obj.getNombresApellidos().trim());
					}
					/* COMBOBOX:: INSPECTOR PARA AR*/
					
					/* LISTBOX:: HORARIOR PARA AR*/
							lstHorariosAr = new String[] {};
							// lstMpTipoPersona
							lstHorariosArInspc = ficalizacionBo.getAllHorario();
							Iterator<RpFiscalizacionHorario> itera = lstHorariosArInspc.iterator();
							String tempr = "";
							while (itera.hasNext()) {
								RpFiscalizacionHorario obj = itera.next();
								mapMpTipoHoraArSeleccionados.put(obj.getIntervaloHorario().trim(),obj.getHorarioId());
								tempr = tempr + obj.getIntervaloHorario()+ ",";
							}
							lstHorariosAr = tempr.split(",");
					
					/* LISTBOX:: HORARIOR PARA AR*/
					
					
					//RECIBIENDO EL SESSION MAP DESDE buscarrequerimiento.xhtml
					currentItemValue=(FindInspeccionHistorial) getSessionMap().get("currentItem");
					cargarDatos();

					
					//currentItemValue=(FindInspeccionHistorial) getSessionMap().get("currentItem");
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
			}

			
			public void cargarDatos() throws Exception{
				try{
					
					if (currentItemValue.getInspeccionId() != null) {
						currentItemValue.getTipoDocumentoId();
						
						if (currentItemValue.getTipoDocumentoId()==Constante.TIPO_DOC_CARTA_ID){
								setCorrelativoCarta(currentItemValue.getNroRequerimiento());
						}else
							if (currentItemValue.getTipoDocumentoId()==Constante.TIPO_DOC_REQ_ID){
								setCorrelativoCarta(currentItemValue.getNroRequerimiento());
							}
							else if (currentItemValue.getTipoDocumentoId()==Constante.TIPO_DOC_CARTA_MULT_ID){
								setCorrelativoCarta(currentItemValue.getNroRequerimiento());
							}
						
						setObservaciones(currentItemValue.getObservaciones());
						setFechaNotificacion(currentItemValue.getFechaNotificacion());
						setFechaInspeccion(currentItemValue.getFechaInspeccion());
						setPeridoInspeccion(currentItemValue.getAnioInspeccion());
						setNroDocAsociado(currentItemValue.getDocAsocNumero());
						
						/*** Para las fechas del resultado:*/
						
//						fechaNotificacionfip
//						fechaNotificacionainr
//						fechaNotificacionEsquela
						
						setFechaNotificacionfip(currentItemValue.getFechaInspeccion());
						setFechaNotificacionainr(currentItemValue.getFechaInspeccion());
						setFechaNotificacionEsquela(currentItemValue.getFechaInspeccion());
						
						if (currentItemValue.getEstado()==Constante.ESTADO_EMITIDO){
								esEmitido=Boolean.TRUE;
								esNotif=Boolean.FALSE;
								esFIP=Boolean.FALSE;
								esAINR=Boolean.FALSE;
								conResultado=Boolean.FALSE;
							
							//setEsEmitidoNotif(true);
						}else if (currentItemValue.getEstado()==Constante.ESTADO_NOTIFICADO &&currentItemValue.getArId()==0){
								esEmitido=Boolean.FALSE;
								esNotif=Boolean.TRUE;
								esFIP=Boolean.FALSE;
								esAINR=Boolean.FALSE;
								conResultado=Boolean.FALSE;
						
						}else if (currentItemValue.getEstado()==Constante.ESTADO_NOTIFICADO &&currentItemValue.getArId()==Constante.TIPO_DOC_AR_ID){
							
								 esEmitido=Boolean.FALSE;
								 esNotif=Boolean.FALSE;
								 esFIP=Boolean.FALSE;
								 esAINR=Boolean.FALSE;
								 conResultado=Boolean.TRUE;
								 esAr=Boolean.TRUE;
								 esArFIP=Boolean.FALSE;
							 
						}else if (currentItemValue.getEstado()==Constante.ESTADO_CON_INSPECCION &&currentItemValue.getArId()==0){
							
								 esEmitido=Boolean.FALSE;
								 esNotif=Boolean.FALSE;
								 esFIP=Boolean.TRUE;
								 esAINR=Boolean.FALSE;
								 conResultado=Boolean.TRUE;
								 
						}else if (currentItemValue.getEstado()==Constante.ESTADO_CON_INSPECCION && currentItemValue.getArId()==Constante.TIPO_DOC_AR_ID && currentItemValue.getArFipId()==Constante.TIPO_DOC_FIP_ID){
									 
								 esEmitido=Boolean.FALSE;
								 esNotif=Boolean.FALSE;
								 esFIP=Boolean.FALSE;
								 esAINR=Boolean.FALSE;
								 conResultado=Boolean.TRUE;
								 esAr=Boolean.TRUE;
								 esArFIP=Boolean.TRUE;
							
						}else if (currentItemValue.getEstado()==Constante.ESTADO_AINR && currentItemValue.getArId()==0 && currentItemValue.getArFipId()==0){
							
								 esEmitido=Boolean.FALSE;
								 esNotif=Boolean.FALSE;
								 esFIP=Boolean.FALSE;
								 esAINR=Boolean.TRUE;
								 conResultado=Boolean.TRUE;
								 esAr=Boolean.FALSE;
								 esArFIP=Boolean.FALSE;
								 
						}else if (currentItemValue.getEstado()==Constante.ESTADO_AINR && currentItemValue.getArId()==Constante.TIPO_DOC_AR_ID && currentItemValue.getArFipId()==0){
							
								 esEmitido=Boolean.FALSE;
								 esNotif=Boolean.FALSE;
								 esFIP=Boolean.FALSE;
								 esAINR=Boolean.FALSE;
								 conResultado=Boolean.TRUE;
								 esAr=Boolean.TRUE;
								 esArFIP=Boolean.FALSE;
								 
						}else if (currentItemValue.getEstado()==Constante.ESTADO_AINR && currentItemValue.getArId()==Constante.TIPO_DOC_AR_ID && currentItemValue.getArFipId()==Constante.TIPO_DOC_FIP_ID){
							
								 esEmitido=Boolean.FALSE;
								 esNotif=Boolean.FALSE;
								 esFIP=Boolean.FALSE;
								 esAINR=Boolean.FALSE;
								 conResultado=Boolean.TRUE;
								 esAr=Boolean.TRUE;
								 esArFIP=Boolean.TRUE;
						}
						
						
						//--- recuperando y listando Horarios:
						/*Comentado en Mayo 2017, solicitado por Correo Instituc. Jimmy Díaz (14.03.2016) e Informe 053-009-00001733 - Inicio*/
//						lstHorarioInsp =ficalizacionBo.getInspeccionesHorario(currentItemValue.getInspeccionId());
//						String cuotasRecuperadas = lstHorarioInsp.get(0).getIntervaloHorario();
//						 Iterator<FindInspeccionByHorario> it = lstHorarioInsp.iterator();
//						          lstHorarios = cuotasRecuperadas.split(",");
//						          String temp2 = "";
//						          while (it.hasNext()) {
//						        	  FindInspeccionByHorario obj = it.next();
//										mapMpTipoHoraSeleccionados.put(obj.getIntervaloHorario().trim(),obj.getHorarioId());
//										temp2 = temp2 + obj.getIntervaloHorario()+ ",";
//									}
//						
//						          lstHorarios = temp2.split(",");
						/*Comentado en Mayo 2017, solicitado por Correo Instituc. Jimmy Díaz (14.03.2016) e Informe 053-009-00001733 - Fin*/
						 						 
						 //--- recuperando y listando Inspector,Programa y Tipo de Documento:
						          //setProperty(currentItemValue);
		      	    	          	// lstDetalleInsp=ficalizacionBo.getInspeccionesDetalle(currentItemValue.getInspeccionId());
						      	String valueTipoInspector=(mapInspectorR.get(currentItemValue.getInspectorId())!=null)?mapInspectorR.get(currentItemValue.getInspectorId()):"";
								setCmbInspector(valueTipoInspector);
										              								
				                String valueTipoPrograma=(mapTipoProgramaRequerimientoR.get(currentItemValue.getProgramaId())!=null)?mapTipoProgramaRequerimientoR.get(currentItemValue.getProgramaId()):"";
				                setCmbTipoProgramaRequerimiento(valueTipoPrograma);
				                
				                String valueTipoDocumento=(mapTipoDocRequerimientoR.get(currentItemValue.getTipoDocumentoId())!=null)?mapTipoDocRequerimientoR.get(currentItemValue.getTipoDocumentoId()):"";
				                setCmbTipoDocRequerimiento(valueTipoDocumento);

						 //--- recuperando y listando Contribuyente y Declaraciones Juradas:						   
//						          lstPersonaInsp=ficalizacionBo.findPersona(lstDetalleInsp.get(0).getPersonaId(),lstDetalleInsp.get(0).getApellidosNombres());
//						          datosContribuyente=lstPersonaInsp.get(0);
				                
				                  lstPersonaInsp=ficalizacionBo.findPersona(currentItemValue.getPersonaId(),currentItemValue.getApellidosNombres());
				                  if (lstPersonaInsp!=null){
				                	  datosContribuyente=lstPersonaInsp.get(0);
				                  }else{
				                	  
				                  }
						          
						          cargarPredioInsp();
 
						          
//						 //cargarDatos();
//						 //--- recuperando resultado:
						          inspeccionResultado=ficalizacionBo.getAllInspeccionesResultado(currentItemValue.getInspeccionId());

						          
						          if (inspeccionResultado!=null){
						        	  cargarDatosResultado();
						          }else{
						        	  
						          }

						         
					}
					
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
			public void cargarDatosResultado() throws Exception{
				try{
					if (currentItemValue.getInspeccionId() != null) {
						
							String valueTipoResult=(mapCargoR.get(currentItemValue.getResultadoId())!=null)?mapCargoR.get(currentItemValue.getResultadoId()):"";
							setCmbCargo(valueTipoResult);
							
							if (cargoId==null&&cmbCargo!=null){
								currentItemValue.setResultadoId(mapCargo.get(cmbCargo));
							}

							if (currentItemValue.getResultadoId()==Constante.TIPO_DOC_FIP_ID){
								
								istipoFip=Boolean.TRUE;
								setCorrelativo(currentItemValue.getResultadoNumero());
								setFechaNotificacionfip(currentItemValue.getFechaResultado());
								setCorrelativoEsquela(currentItemValue.getEsquelaNumero());
								setFechaNotificacionEsquela(currentItemValue.getFechaEsquela());


							}else if(currentItemValue.getResultadoId()==Constante.TIPO_DOC_AINR_ID&&currentItemValue.getArId()==Constante.TIPO_DOC_AR_ID){
								istipoAinr=Boolean.TRUE;
								setCorrelativo(currentItemValue.getResultadoNumero());
								setFechaNotificacionainr(currentItemValue.getFechaResultado());
								setCorrelativoEsquela(currentItemValue.getEsquelaNumero());
								setFechaNotificacionEsquela(currentItemValue.getFechaEsquela());
								
								actaReprograma=Boolean.TRUE;
								setCorrelativoAR(currentItemValue.getArNumero());
								setFechaNotificacionAr(currentItemValue.getFechaAr());
								
								String valueInspAr=(mapInspectorArR.get(currentItemValue.getInspectorIdAr())!=null)?mapInspectorArR.get(currentItemValue.getInspectorIdAr()):"";
								setCmbInspectorAr(valueInspAr);
								
								if(currentItemValue.getArFipId()==Constante.TIPO_DOC_FIP_ID){
									setCorrelativoFipar(currentItemValue.getArFipNumero());
									setFechaNotificacionFipar(currentItemValue.getFechaArFip());
								}

								//horarios lstHorariosAr  cambiar el metodo:
								
								lstHorarioInsp =ficalizacionBo.getInspeccionesHorarioAr(currentItemValue.getInspeccionId());
								String cuotasRecuperadas = lstHorarioInsp.get(0).getIntervaloHorario();
								 Iterator<FindInspeccionByHorario> it = lstHorarioInsp.iterator();
								        lstHorariosAr = cuotasRecuperadas.split(",");
								          String temp2 = "";
								          while (it.hasNext()) {
								        	  FindInspeccionByHorario obj = it.next();
								        	  mapMpTipoHoraArSeleccionados.put(obj.getIntervaloHorario().trim(),obj.getHorarioId());
												temp2 = temp2 + obj.getIntervaloHorario()+ ",";
											}
								
								          lstHorariosAr = temp2.split(",");

							}else if(currentItemValue.getResultadoId()==Constante.TIPO_DOC_AINR_ID&&currentItemValue.getArId()==0){
								istipoAinr=Boolean.TRUE;
								istipoAr=Boolean.TRUE;
								setCorrelativo(currentItemValue.getResultadoNumero());
								setFechaNotificacionainr(currentItemValue.getFechaResultado());
								setCorrelativoEsquela(currentItemValue.getEsquelaNumero());
								setFechaNotificacionEsquela(currentItemValue.getFechaEsquela());
								
							}
								
							/**(1) Se agregó dj_id, para que el registro de la dj en rp_fiscalizacion_inspeccion sea en el mismo Form:"registropredio.xhtml"*/
						//listaDjInspeccion = ficalizacionBo.getDeclaracionesInspById(currentItemValue.getInspeccionId(),currentItemValue.getPersonaId()); //(1)
						listaDjsInspeccion=ficalizacionBo.getDeclaracionesInspeccionById(currentItemValue.getInspeccionId());
					}

				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
			public void setProperty(FindInspeccionHistorial currentItemValue) throws Exception{
				setCurrentItemValue(currentItemValue);

				String valueTipoInspector=(mapInspectorR.get(currentItemValue.getInspectorId())!=null)?mapInspectorR.get(currentItemValue.getInspectorId()):"";
				setCmbInspector(valueTipoInspector);
 				
                String valueTipoPrograma=(mapTipoProgramaRequerimientoR.get(currentItemValue.getProgramaId())!=null)?mapTipoProgramaRequerimientoR.get(currentItemValue.getProgramaId()):"";
                setCmbTipoProgramaRequerimiento(valueTipoPrograma);
                
                String valueTipoDocumento=(mapTipoDocRequerimientoR.get(currentItemValue.getTipoDocumentoId())!=null)?mapTipoDocRequerimientoR.get(currentItemValue.getTipoDocumentoId()):"";
                setCmbTipoDocRequerimiento(valueTipoDocumento);
		
			}

			/**
			 * Generando multas desde Control de Requerimientos
			 */

//			public void generarMulta() throws Exception {
//
//				try {
//					Calendar calendar = Calendar.getInstance();
//					Integer diasHabiles = obligacionBo.obtenerDiasHabiles(
//							obligacionDTO.getFechaAdquision(), calendar.getTime());
//					if (diasHabiles > 5) {
//						/** Generando las multas */
//						obligacionBo.generarMulta(currentItemValue.getPersonaId(), obligacionDTO);
//						Integer loteId = generalBo.ObtenerCorrelativoTabla("cc_lote", 1);
//						obligacionBo.generarResolucionMulta(loteId - 1,
//								currentItemValue.getPersonaId(),
//								obligacionDTO.getUnidadId(), getUser().getUsuarioId(),
//								getUser().getTerminal(),
//								obligacionDTO.getSubConceptoId());
//
//						/**
//						 * Buscando las multas recientemente generadas tambien trae
//						 * multas pendientes que contienen el mismo tipo de multa
//						 */
//						lstMultaDTOs = obligacionBo.buscarMultas(obligacionDTO);
//
//						sumarMultas(lstMultaDTOs);
//
//						Integer unidadId = obligacionDTO.getUnidadId();
//
//						/** Reiniciando el DTO de obligaciones */
//						this.obligacionDTO = new ObligacionDTO();
//						this.obligacionDTO.setPersonaId(currentItemValue.getPersonaId());
//						obligacionDTO.setConceptoId(this.MULTAS);
//						obligacionDTO.setConceptoDescripcion("MULTAS");
//						obligacionDTO.setUnidadId(unidadId);
//
//						/**
//						 * Reiniciando los combobox de subconceptos y tributos asociados
//						 */
//						cmbSubConcepto = null;
//						cmbTributoAsociado = null;
//					} else {
//						addErrorMessage(getMsg("Aun no le corresponde generarle multa."));
//					}
//
//				} catch (SisatException e) {
//					WebMessages.messageError(e.getMessage());
//				}
//
//			}
			
//			public void sumarMultas(List<MultaDTO> lstMultaDTOs) {
//
//				totalAPagar = new BigDecimal(0);
//				totalDscto = new BigDecimal(0);
//				totalSubTotal = new BigDecimal(0);
//				totalInteres = new BigDecimal(0);
//				totalMonto = new BigDecimal(0);
//
//				for (MultaDTO m : lstMultaDTOs) {
//					totalAPagar = totalAPagar.add(m.getMontoConDscto());
//					totalDscto = totalDscto.add(m.getMontoDescuento());
//					totalSubTotal = totalSubTotal.add(m.getMontoSinDscto());
//					totalInteres = totalInteres.add(m.getInteres());
//					totalMonto = totalMonto.add(m.getMonto());
//				}
//			}
			
//			public void changeListenerComboBoxUnidad(ValueChangeEvent event) {
//
//				String cmbValueSelect = (String) event.getNewValue();
//
//				if (cmbValueSelect != null
//						&& cmbValueSelect.equals("Dpto. Servicios al Contribuyente")) {
//					obligacionDTO.setUnidadId(61);
//				} else if (cmbValueSelect != null
//						&& cmbValueSelect
//								.equals("Dpto. Control y Cobranza de la Deuda")) {
//					obligacionDTO.setUnidadId(62);
//
//				} else if (cmbValueSelect != null
//						&& cmbValueSelect.equals("Dpto. Fiscalización y Censo Predial")) {
//					obligacionDTO.setUnidadId(64);
//				}
//			}
			
//			public void changeListenerSubConcepto(ValueChangeEvent event) {
//
//				debug("listenerSubConcepto - inicio");
//
//				String key = (String) event.getNewValue();
//
//				subConceptoDTO = mapSupConcepto.get(key);
//				cmbSubConcepto = key;
//
//				if (subConceptoDTO != null) {
//
//					getObligacionDTO().setMonto(subConceptoDTO.getValor());
//					getObligacionDTO().setSubConceptoId(
//							subConceptoDTO.getSubconceptoId());
//					getObligacionDTO().setSubConceptoDescripcion(
//							subConceptoDTO.getDescripcion());
//
//				}
//
//				debug("listenerSubConcepto - fin");
//			}
			
//			public void changeListenerComboBoxTributoAsociado(ValueChangeEvent event) {
//
//				String cmbValueSelect = (String) event.getNewValue();
//
//				if (cmbValueSelect != null && cmbValueSelect.equals("Predial")) {
//					obligacionDTO.setConceptoIdTributoReferencia(1);
//				} else if (cmbValueSelect != null && cmbValueSelect.equals("Vehicular")) {
//					obligacionDTO.setConceptoIdTributoReferencia(2);
//				}
//			}
			
			/**
			 * Generando multas desde Control de Requerimientos
			 */
			
			public void quitarPredioVehiculo() {
				debug("inicio - quitarPredioVehiculo");

				getObligacionDTO().setCodigoPlacaReferencia("");

				debug("fin - quitarPredioVehiculo");

			}
			
			
			public void validarCorrelativo() throws Exception {
				
				if (cargoId==null&&cmbCargo!=null){
					
					currentItemValue.setResultadoId(mapCargo.get(cmbCargo));
					cargoId=currentItemValue.getResultadoId();
				}
				
				if (cargoId==Constante.TIPO_DOC_FIP_ID){
					if (correlativo !=null){
						listacorrelativo=ficalizacionBo.getInspeccionByCorrelativo(correlativo, Constante.TIPO_DOC_FIP_ID);
						
						if (listacorrelativo!=null){
							WebMessages.messageError("El número de FIP: " + listacorrelativo.get(0).getResultadoNumero()+" ya se encuentra registrado" );
							setEsCorrelativoFip(true);
						}else{
							setEsCorrelativoFip(false);
						}
					}
					
				}else
					
				if (cargoId==Constante.TIPO_DOC_AINR_ID&&isActaReprograma() == Boolean.FALSE){
						listacorrelativo=ficalizacionBo.getInspeccionByCorrelativo(correlativo, Constante.TIPO_DOC_AINR_ID);
						
						if (listacorrelativo!=null){
							WebMessages.messageError("El número de AINR: " + listacorrelativo.get(0).getResultadoNumero()+" ya se encuentra registrado" );
							setEsCorrelativoAinr(true);
						}else{
							setEsCorrelativoAinr(false);
						}
						
						
				}else
				if (cargoId==Constante.TIPO_DOC_AINR_ID&&isActaReprograma() == Boolean.TRUE){
						listacorrelativo=ficalizacionBo.getInspeccionByCorrelativo(correlativoAR, Constante.TIPO_DOC_AR_ID);
						
						if (listacorrelativo!=null){
							WebMessages.messageError("El número de AR: " + listacorrelativo.get(0).getArNumero()+" ya se encuentra registrado" );
							setEsCorrelativoAr(true);
						}else{
							setEsCorrelativoAr(false);
						}

				}else
					if(cargoId==Constante.TIPO_DOC_INF_ID){
						if (correlativo !=null){
							listacorrelativo=ficalizacionBo.getInspeccionByCorrelativo(correlativo, Constante.TIPO_DOC_INF_ID);
							
							if (listacorrelativo!=null){
								WebMessages.messageError("El número de Informe: " + listacorrelativo.get(0).getResultadoNumero()+" ya se encuentra registrado" );
								setEsCorrelativoFip(true);
							}else{
								setEsCorrelativoFip(false);
							}
						}
					}
				}
			


			public void validarCorrelativoEsquela() throws Exception {
				if (correlativoEsquela !=null){
				listacorrelativo=ficalizacionBo.getInspeccionByCorrelativo(correlativoEsquela, Constante.TIPO_DOC_EA_ID);
				
					if (listacorrelativo!=null){
						WebMessages.messageError("El número de E.A.: " + listacorrelativo.get(0).getEsquelaNumero()+" ya se encuentra registrado" );
					}else{
						
					}
				}
			}
			
			public void validarCorrelativoFipAr() throws Exception {
				if (correlativoFipar !=null){
					listacorrelativo=ficalizacionBo.getInspeccionByCorrelativo(correlativoFipar, Constante.TIPO_DOC_FIP_ID);
				
					if (listacorrelativo!=null){
						WebMessages.messageError("El número de FIP: " + listacorrelativo.get(0).getResultadoNumero()+" ya se encuentra registrado" );
						setEsCorrelativoFipAr(true);
					}else{
						setEsCorrelativoFipAr(false);
					}
					
				}
			}
			
			public void checkReq(ValueChangeEvent event) {
				Boolean check = (Boolean) event.getNewValue();
				if (check == Boolean.TRUE) {			
					setActaReprograma(Boolean.TRUE);
				} else {			
					setActaReprograma(Boolean.FALSE);
				}
			}
			
			public void buscarDjFiscalizada() throws Exception{
				try{
					
					/**(1) Se agregó dj_id, para que el registro de la dj en rp_fiscalizacion_inspeccion sea en el Form:"registropredio.xhtml"*/
					//listaDjInspeccion = ficalizacionBo.getDeclaracionesInspById(currentItemValue.getInspeccionId(),currentItemValue.getPersonaId());//(1)
					   //listaDjInspeccion = ficalizacionBo.getDeclaracionesInsp(datosContribuyente.getPersonaId());
					if (listaDjInspeccion.isEmpty()&&listaDjInspeccion==null){
						setIsDjOmisa(true);
						WebMessages.messageError("¿Es una Dj omisa?");
					}else{
						
						setIsDjOmisa(false);
						
					}
					
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			public void buscarPredioFiscalizado() throws Exception{
				try{
					
					listDjPredials = registroPrediosBo.getRpDjpredial(null, null, null, null, null, null, null,
					         null, null, null, null, datosContribuyente.getPersonaId(), true);
					
					
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			public void cargarPredioInsp() throws Exception{
				try{
					
					
					listaPredioInspeccion=ficalizacionBo.getPrediosInspById(currentItemValue.getInspeccionId());
							if (listaPredioInspeccion.isEmpty()&&listaPredioInspeccion == null) {
								// tienePredioRegistrado=Boolean.FALSE;
								setTienePredioRegistrado(false);
							} else {
				
									// listDjPredials = registroPrediosBo.getRpDjpredial(null, null, null, null, null, null, null, null, null, null, null, currentItemValue.getPersonaId(),true);
									// //tienePredioRegistrado=Boolean.TRUE;
								listDjPredials = ficalizacionBo.getPrediosInspeccionById(currentItemValue.getInspeccionId());
								setTienePredioRegistrado(true);

							
						}
					
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
//			public void buscarCorrelativo() throws Exception{
//				try{
//					
//					//findCargoInsp = new FindInspeccionDocCargoTipo();
//					correlativoCargo=ficalizacionBo.getCorrelativoCargo(cargoId);
//					//listaDjInspeccion = ficalizacionBo.getDeclaracionesInsp(datosContribuyente.getPersonaId());
//					
//				}catch(Exception e){
//					e.printStackTrace();
//				}
//			}
			
			
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
							//correlativoCarta=ficalizacionBo.correlativo(tipoDocRequerimientoId); /* Dany A. solicito digitar correlativo - 18/07/14 */

						}
						else if(value.toString().compareTo(Constante.TIPO_DOC_REQ)==0){
							
							istipoCarta=Boolean.FALSE;
							istipoReque=Boolean.TRUE;
							//correlativoReque=ficalizacionBo.correlativo(tipoDocRequerimientoId); /* Dany A. solicito digitar correlativo - 18/07/14 */

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
			
			public void loadCargoxRequerimiento(ValueChangeEvent event) {
				
				try {
					HtmlComboBox combo = (HtmlComboBox) event.getComponent();
					String value = combo.getValue().toString();
					if (value != null) {
						cargoId = (Integer) mapCargo.get(value);
						setCmbCargo(value);
					}
					if(value!=null){
						if(value.compareTo(Constante.TIPO_DOC_FIP)==0)
						{
							istipoFip=Boolean.TRUE;
							istipoAinr=Boolean.FALSE;
							istipoInforme=Boolean.FALSE;
							correlativo=ficalizacionBo.correlativo(cargoId);
							//correlativoEsquela=ficalizacionBo.correlativo(67);
							

						}
						else if(value.toString().compareTo(Constante.TIPO_DOC_AINR)==0){
							
							istipoAinr=Boolean.TRUE;
							istipoFip=Boolean.FALSE;
							istipoInforme=Boolean.FALSE;
							correlativo=ficalizacionBo.correlativo(cargoId);
							//correlativoEsquela=ficalizacionBo.correlativo(67);
							//correlativoAR=ficalizacionBo.correlativo(64);
							
						}
						else if(value.toString().compareTo(Constante.TIPO_DOC_INFORME)==0){
							
							istipoInforme=Boolean.TRUE;
							istipoAinr=Boolean.FALSE;
							istipoFip=Boolean.FALSE;
							correlativo=ficalizacionBo.correlativo(cargoId);
							//correlativoEsquela=ficalizacionBo.correlativo(67);
							//correlativoAR=ficalizacionBo.correlativo(64);
							
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					WebMessages.messageFatal(e);
				}
			}
			
			public void loadInspectorArxRequerimiento(ValueChangeEvent event) {
				try {
					HtmlComboBox combo = (HtmlComboBox) event.getComponent();
					String value = combo.getValue().toString();
					if (value != null) {
						inspectorArId = (Integer) mapInspectorAr.get(value);
						setCmbInspectorAr(value);
					}
				} catch (Exception e) {
					e.printStackTrace();
					WebMessages.messageFatal(e);
				}
			}
			public String salir(){
				return sendRedirectPrincipal();
			}
			
			
			public void actualizarResultado() throws Exception {

				try {
					
					
					if (esFIP==Boolean.TRUE){
						nombreInspector=currentItemValue.getInspectorId();
						fechaInspeccion=currentItemValue.getFechaInspeccion();
						nombreInspectorAr=0;
						fechaInspeccionAr=null;
					}else if(esAINR==Boolean.TRUE){
						nombreInspector=currentItemValue.getInspectorId();
						fechaInspeccion=currentItemValue.getFechaInspeccion();
						nombreInspectorAr=currentItemValue.getInspectorIdAr();
						fechaInspeccionAr=currentItemValue.getFechaAr();
					}
					
						if(esAINR==Boolean.TRUE)
						{
							int cuentaSeleccion = 0;
							
							for (FindInspeccionDj i : listaDjInspeccion) {
							if (i.isSelected()) {
								ficalizacionBo.guardarDetalleFip(nombreInspector,i.getAnnoDj().toString(), i.getDjId(), i.getPredioId(),
										i.getUbicacionId(),i.getTipoViaId(),i.getViaId(),i.getSectorId(),i.getManzana(),i.getCuadra(),i.getLadoCuadra(),
										fechaInspeccion,currentItemValue.getInspeccionId(),i.getDireccionPredio(),i.getSector(),i.getVia(),i.getTipoVia(),
										i.getLugar(),nombreInspectorAr,fechaInspeccionAr,getUser().getUsuarioId(), getUser().getTerminal());

								cuentaSeleccion = cuentaSeleccion + 1;

							}
						}
								
						}
						else{
						
					
					
						int cuentaSeleccion = 0;
											
						for (FindInspeccionDj i : listaDjInspeccion) {
						if (i.isSelected()) {
							ficalizacionBo.guardarDetalleFip(nombreInspector,i.getAnnoDj().toString(), i.getDjId(), i.getPredioId(),
									i.getUbicacionId(),i.getTipoViaId(),i.getViaId(),i.getSectorId(),i.getManzana(),i.getCuadra(),i.getLadoCuadra(),
									fechaInspeccion,currentItemValue.getInspeccionId(),i.getDireccionPredio(),i.getSector(),i.getVia(),i.getTipoVia(),
									i.getLugar(),nombreInspectorAr,fechaInspeccionAr,getUser().getUsuarioId(), getUser().getTerminal());
							//(de.getDjId())

							cuentaSeleccion = cuentaSeleccion + 1;
							
							//,currentItemValue.getInspeccionId(),getUser().getUsuarioId(), getUser().getTerminal()
							}
						}
					
					}
					
					
				} catch (Exception e) {
					e.printStackTrace();
					WebMessages.messageFatal(e);
				}

			}
			
			public String actualizacion(){
				if(currentItemValue!=null){
					//getSessionMap().put("currentItem", datosContribuyente);//Enviando todo el objeto.
//					getSessionMap().put("currentItem", currentItemValue.getPersonaId());//Enviando solo la persona_id.
					getSessionMap().put("currentItemRequerimiento", currentItemValue);
				}
				System.out.println("ENTREEEEEEEEE :====== > "+ permisoModificarActualizar);
				if(permisoModificarActualizar) {
					getSessionManaged().setModuloFisca(true);
				}else {
					getSessionManaged().setModuloFisca(false);
				}
				
//				BusquedaPredioManaged managed = (BusquedaPredioManaged) getManaged("busquedaPredioManaged");
//				managed.setCodigoContribuyente(currentItemValue.getPersonaId());


				return sendRedirectPrincipal();
			}
			
			public void guardarResultado() throws Exception {

				try {
								//¿ar is checked y es un AINR?	
//					if (isActaReprograma() == Boolean.TRUE&&cargoId==Constante.TIPO_DOC_AINR_ID) {
//					
//						        ficalizacionBo.actualizarRequerimiento(cargoId, correlativo,fechaNotificacionainr,
//								Constante.TIPO_DOC_EA_ID, correlativoEsquela, fechaNotificacionEsquela, Constante.TIPO_DOC_AR_ID, correlativoAR, currentItemValue.getInspeccionId(),Constante.ESTADO_AINR,fechaNotificacionAr);
//
//				        //Actualizando el detalle de la inspeccion(inspector y fecha de inspeccion):
//						        ficalizacionBo.actualizarRequerimientoDetalle(fechaNotificacionAr, inspectorArId, DateUtil.getCurrentDate(), currentItemValue.getInspeccionId());
//								
//						//Registro el horario de reprogramación:
//								validarHorarioReprogramado();
//						
//						if (getIsHorarioReprogramado()==Boolean.FALSE){
//							
//						
//							if (lstHorariosAr != null && lstHorariosAr.length > 0) {
//								for (String p1 : lstHorariosAr) {
//									
//									Integer inspcAr_id = currentItemValue.getInspeccionId();
//									RpFiscalizacionHorarioDetalle rpFiscalizacionHora = new  RpFiscalizacionHorarioDetalle();
//									
//									rpFiscalizacionHora.setInspeccionId(inspcAr_id);
//									rpFiscalizacionHora.setHorarioId(mapMpTipoHoraArSeleccionados.get(p1));
//									rpFiscalizacionHora.setEstado("3");
//									
//									ficalizacionBo.create(rpFiscalizacionHora);
//									
//								}
//							}
//						}else {
//							//addErrorMessage(getMsg("Verifique la fecha de reprogramación."));
//							}
//						
//					
//							   
//					} 
					 //¿ar is not checked y es un AINR?	
					//else 
					if (isActaReprograma() == Boolean.FALSE&&cargoId==Constante.TIPO_DOC_AINR_ID){
						if (correlativo!=null&&correlativo.trim().length()>0){
							validarCorrelativo();
							if(esCorrelativoAinr==Boolean.FALSE){
								if(fechaNotificacionainr!=null){

								ficalizacionBo.actualizarRequerimiento(cargoId, correlativo,fechaNotificacionainr, Constante.TIPO_DOC_EA_ID,
								correlativoEsquela, fechaNotificacionEsquela, 0, null, currentItemValue.getInspeccionId(),Constante.ESTADO_AINR,null);
								
								}else{
									addErrorMessage(getMsg("Ingrese la Fecha de Notificación de la Inspección"));
								}
							}else{
							    addErrorMessage(getMsg("Verifique el correlativo del A.I.N.R."));
							}
						}else{
							addErrorMessage(getMsg("Ingrese el Nro. de A.I.N.R."));
						}
								//¿ar is not checked y es una FIP?	
					} else if (isActaReprograma() == Boolean.FALSE&&cargoId==Constante.TIPO_DOC_FIP_ID || cargoId==Constante.TIPO_DOC_INF_ID){
						if (correlativo!=null&&correlativo.trim().length()>0){
							validarCorrelativo();
							if(esCorrelativoFip==Boolean.FALSE){
								if(fechaNotificacionfip!=null){
									
								ficalizacionBo.actualizarRequerimiento(cargoId, correlativo, fechaNotificacionfip, Constante.TIPO_DOC_EA_ID,
								correlativoEsquela, fechaNotificacionEsquela,0, null, currentItemValue.getInspeccionId(),Constante.ESTADO_CON_INSPECCION,null);
								
								}else{
									addErrorMessage(getMsg("Ingrese la Fecha de Notificación de la Inspección"));
								}
							}else{
								addErrorMessage(getMsg("Verifique el correlativo del F.I.P."));
							}
								
								
						}else{
							addErrorMessage(getMsg("Ingrese el Nro. de F.I.P."));
						}
							
					}
					
					setOcultarBoton(true);
					inspeccionResultado=ficalizacionBo.getAllInspeccionesResultado(currentItemValue.getInspeccionId());
					cargarDatosResultado();
					
				} catch (Exception e) {
					e.printStackTrace();
					WebMessages.messageFatal(e);
				}

			}
			
			public void guardarResultadoAr() throws Exception {
				try{
					//¿ar is checked y es un AINR?
					if (isActaReprograma() == Boolean.TRUE&&cargoId==Constante.TIPO_DOC_AINR_ID) {
						
						if(correlativoAR!=null&&correlativoAR.trim().length()>0){
							validarCorrelativo();
							if(esCorrelativoAr==Boolean.FALSE){
								if(fechaNotificacionAr!=null){
									//aqui valida
									Integer diasHabiles = obligacionBo.obtenerDiasHabiles(fechaNotificacionainr, fechaNotificacionAr);
									if (diasHabiles > 5) {
										setEsMayor(true);
									}else{
										setEsMayor(false);
									}
									//aqui valida fin
							        ficalizacionBo.actualizarRequerimiento(cargoId, correlativo,fechaNotificacionainr,
									Constante.TIPO_DOC_EA_ID, correlativoEsquela, fechaNotificacionEsquela, Constante.TIPO_DOC_AR_ID, correlativoAR, currentItemValue.getInspeccionId(),Constante.ESTADO_NOTIFICADO,fechaNotificacionAr);

							        /**
							         * Actualizando el detalle de la inspeccion(inspector y fecha de inspeccion):
							         */
					        
							        ficalizacionBo.actualizarRequerimientoDetalle(fechaNotificacionAr, inspectorArId, DateUtil.getCurrentDate(), currentItemValue.getInspeccionId());
									
							        /**
							         * Registro el horario de reprogramación:
							         */
							
									validarHorarioReprogramado();
							
							if (getIsHorarioReprogramado()==Boolean.FALSE){
								
							
								if (lstHorariosAr != null && lstHorariosAr.length > 0) {
									for (String p1 : lstHorariosAr) {
										
										Integer inspcAr_id = currentItemValue.getInspeccionId();
										RpFiscalizacionHorarioDetalle rpFiscalizacionHora = new  RpFiscalizacionHorarioDetalle();
										
										rpFiscalizacionHora.setInspeccionId(inspcAr_id);
										rpFiscalizacionHora.setHorarioId(mapMpTipoHoraArSeleccionados.get(p1));
										rpFiscalizacionHora.setEstado("3");
										
										ficalizacionBo.create(rpFiscalizacionHora);
										
									}
								}
								setOcultarBoton(true);
					}else {
						addErrorMessage(getMsg("Verifique el horario."));
						}
					}else{
						addErrorMessage(getMsg("Ingrese la Fecha de Reprogramación de la Inspección"));
					}
				}else{
				    addErrorMessage(getMsg("Verifique el correlativo del A.R."));
					}		   			
				}else{
					addErrorMessage(getMsg("Ingrese el Nro. de A.R."));
				 }
			} 
					
				} catch (Exception e) {
					e.printStackTrace();
					WebMessages.messageFatal(e);
				}
				
			}
			
			public void guardarResultadoFipAr() throws Exception {
				try{			
					if (isActaReprograma() == Boolean.TRUE&&currentItemValue.getArId()==Constante.TIPO_DOC_AR_ID) {
						
						if(correlativoFipar !=null && correlativoFipar.trim().length()>0){
								validarCorrelativoFipAr();
							   if(esCorrelativoFipAr==Boolean.FALSE){
								   if(fechaNotificacionFipar!=null){
									     ficalizacionBo.actualizarRequerimientoArFIP( Constante.TIPO_DOC_FIP_ID,correlativoFipar,DateUtil.getCurrentDate(),fechaNotificacionFipar,getUser().getUsuarioId(),getUser().getTerminal(),currentItemValue.getInspeccionId());
									     setOcultarBoton(true);
									}else{
										addErrorMessage(getMsg("Ingrese la Fecha de notificación de la F.I.P."));
									}
							   }else{
								   addErrorMessage(getMsg("Verifique el correlativo de la F.I.P."));
							   }
								   
						}else{
								addErrorMessage(getMsg("Ingrese la F.I.P."));
							 }
					}
					
				   } catch (Exception e) {
					e.printStackTrace();
					WebMessages.messageFatal(e);
				   }
				
			}
			
			 public void validarHorario() throws Exception {
				 try{
					 
					 if (inspectorId==null){
							currentItemValue.setInspectorId(mapInspector.get(cmbInspector));
							inspectorId=currentItemValue.getInspectorId();
							
						}
						
					 
					 if (inspectorId!=null&&fechaInspeccion !=null&&lstHorarios != null&&lstHorarios.length > 0){
							 for (String p1 : lstHorarios) {

									listaHorario=ficalizacionBo.getHorarioByInspector(inspectorId, fechaInspeccion, mapMpTipoHoraSeleccionados.get(p1));

									if (listaHorario!=null)
								 	
									 {
									     WebMessages.messageError("El horario: " + listaHorario.get(0).getIntervaloHorario()+" ya se encuentra asignado a "+listaHorario.get(0).getNombreUsuario()+".");
										
									     isHorarioProgramado=Boolean.TRUE;
									     
									 }else //if (listaHorario.isEmpty())
									 {
										 
										// System.out.println("horario sin problema");
										 isHorarioProgramado=Boolean.FALSE;

									 }
									 
     						}
						 
					
				 }
			
			 }catch (Exception e) {
				e.printStackTrace();
				WebMessages.messageFatal(e);
			}
		}
			
			 public void comparaHorarioRecuperado() throws Exception {
				 try {

					 lstHorarioBD =ficalizacionBo.getInspeccionesHorario(currentItemValue.getInspeccionId());
						String cuotasRecuperadas = lstHorarioBD.get(0).getIntervaloHorario();
						 Iterator<FindInspeccionByHorario> it = lstHorarioBD.iterator();
						 lstHorariosBD = cuotasRecuperadas.split(",");
						          String temp2 = "";
						          while (it.hasNext()) {
						        	  FindInspeccionByHorario obj = it.next();
						        	  mapMpTipoHoraSeleccionadosBD.put(obj.getIntervaloHorario().trim(),obj.getHorarioId());
										temp2 = temp2 + obj.getIntervaloHorario()+ ",";
									}
						
						          lstHorariosBD = temp2.split(",");
						         
						          compareArrays(lstHorariosBD,lstHorarios);
						          
//						if(lstHorariosBD.length==lstHorarios.length){
//							esHorarioIgual=Boolean.TRUE;
//						}else
//						{
//							esHorarioIgual=Boolean.FALSE;
//						}
					 
				 } catch (Exception e) {
						e.printStackTrace();
						WebMessages.messageFatal(e);
					}
				 
			 }
			 
			 public static <E> boolean compareArrays(E[] array1, E[] array2) {
			      boolean b = true;
			      for (int i = 0; i < array2.length; i++) {
			        if (array2[i].equals(array1[i]) ) {// For String Compare
			           System.out.println("true");
			           esHorarioIgual=Boolean.TRUE;
			        } else {
			           b = false;
			           System.out.println("False");
			           esHorarioIgual=Boolean.FALSE;
			        }
			      } 
			      return b;
			    }
			 
			 public void validarHorarioReprogramado() throws Exception {
				 try{
					 
					 if (inspectorArId==null&&cmbInspectorAr!=null){
							currentItemValue.setInspectorIdAr(mapInspectorAr.get(cmbInspectorAr));
							inspectorArId=currentItemValue.getInspectorIdAr();
							
						}


					 if (inspectorArId!=null&&fechaNotificacionAr !=null&&lstHorariosAr != null&&lstHorariosAr.length > 0){
							 for (String p1 : lstHorariosAr) {

									listaHorario=ficalizacionBo.getHorarioByInspector(inspectorArId, fechaNotificacionAr, mapMpTipoHoraArSeleccionados.get(p1));

									if (listaHorario!=null)
								 	
									 {
									     WebMessages.messageError("El horario: " + listaHorario.get(0).getIntervaloHorario()+" ya se encuentra asignado a "+listaHorario.get(0).getNombreUsuario()+".");
										
									     isHorarioReprogramado=Boolean.TRUE;
									     
									 }else //if (listaHorario.isEmpty())
									 {
										 
										// System.out.println("horario sin problema");
										 isHorarioReprogramado=Boolean.FALSE;

									 }
									 
     						}
						 
					
				 }
			
			 }catch (Exception e) {
				e.printStackTrace();
				WebMessages.messageFatal(e);
			}
		}
			 
			 
			public void guardarInspeccionActual() throws Exception {

				try {
					//tipoDocumento=tipoDocRequerimientoId;
					//tipoPrograma = tipoProgramaRequerimientoId;
					
					
					listaPredioInspeccion=ficalizacionBo.getPrediosInspById(currentItemValue.getInspeccionId());
			          if (listaPredioInspeccion==null){
			        	  tienePredioRegistrado=Boolean.FALSE;
			        	     setTienePredioRegistrado(false);
						}else{
							 tienePredioRegistrado=Boolean.TRUE;
							 setTienePredioRegistrado(true);
						}
					
					if (inspectorId==null&&cmbInspector!=null){
						
						currentItemValue.setInspectorId(mapInspector.get(cmbInspector));
						nombreInspector=currentItemValue.getInspectorId();
						isinspectorProgramado=true;
						
					}else{
						nombreInspector=inspectorId;
						isinspectorProgramado=false;
					}
					
					if (tipoDocRequerimientoId==null){
						currentItemValue.setTipoDocumentoId(mapTipoDocRequerimiento.get(cmbTipoDocRequerimiento));
						tipoDocumento=currentItemValue.getTipoDocumentoId();
						
					}else{
						tipoDocumento=tipoDocRequerimientoId;
					}
					
						
					//si el inspector, fecha y lista de horarios son iguales no valida else que si lo haga:
					
					//comparaHorarioRecuperado();/*Comentado en Mayo 2017, solicitado por Correo Instituc. Jimmy Díaz (14.03.2016) e Informe 053-009-00001733*/
					
					/**
					 * fechas
					 */
					/**Cambié el formato de las fechas para que puedan compararse*/
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
					String fechita=formatter.format(fechaInspeccion);
					String fechitaObtenida=formatter.format(currentItemValue.getFechaInspeccion());
					
					/**Obtengo las fechas enviadas en el formato a comparar*/
					   SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy"); 
					   Date fecha1 = formateador.parse(fechita);
					   Date fecha2 = formateador.parse(fechitaObtenida);
					   
					/**Comparación de fechas, ¿cambiaron la Fecha de Inspeccion?*/

					if ( fecha1.before(fecha2) ){
					   // resultado= "La Fecha 1 es menor ";
						isFechaProgramada=Boolean.FALSE;
					    }else{
					     if ( fecha2.before(fecha1) ){
//					      resultado= "La Fecha 1 es Mayor ";
					    	 isFechaProgramada=Boolean.FALSE;
					     }else{
//					      resultado= "Las Fechas Son iguales ";
					    	 isFechaProgramada=Boolean.TRUE;
					     } 
					    }
					
					/**
					 * fechas
					 */
					//si el inspector, fecha y lista de horarios son iguales no valida else que si lo haga:
					
					//if (esHorarioIgual==Boolean.FALSE||isinspectorProgramado==Boolean.FALSE||isFechaProgramada==Boolean.FALSE){ /*Comentado en Mayo 2017, solicitado por Correo Instituc. Jimmy Díaz (14.03.2016) e Informe 053-009-00001733*/
					if (isinspectorProgramado==Boolean.FALSE||isFechaProgramada==Boolean.FALSE){
						validarHorario();
					}else{

						setIsHorarioProgramado(Boolean.FALSE);
					}


					//Guardando:
			if (getIsHorarioProgramado() == Boolean.FALSE) 
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
 
							if (tipoDocumento==Constante.TIPO_DOC_CARTA_ID||tipoDocumento==Constante.TIPO_DOC_REQ_ID||tipoDocumento==Constante.TIPO_DOC_CARTA_MULT_ID)
							{
								if (codPersona!=null&&dirPersona!=null&&fechaNotificacion!=null){/*Cambiado el 23-07-14 Ceci A. solicitó no registrar "Contribuy." y "Fecha de Notif." en una primera instancia*/
									    ficalizacionBo.actualizarRequerimientoNotificacion(nombreInspector,fechaInspeccion,codPersona,observaciones,dirPersona,fechaNotificacion,Constante.ESTADO_NOTIFICADO,currentItemValue.getInspeccionId(),getUser().getUsuarioId(), getUser().getTerminal());
								}else if (codPersona==null&&dirPersona==null&&fechaNotificacion==null){
									ficalizacionBo.actualizarRequerimientoNotificacion(nombreInspector,fechaInspeccion,0,observaciones,null,null,Constante.ESTADO_EMITIDO,currentItemValue.getInspeccionId(),getUser().getUsuarioId(), getUser().getTerminal());
								}else if (codPersona!=null&&dirPersona!=null&&fechaNotificacion==null){
									ficalizacionBo.actualizarRequerimientoNotificacion(nombreInspector,fechaInspeccion,codPersona,observaciones,dirPersona,null,Constante.ESTADO_EMITIDO,currentItemValue.getInspeccionId(),getUser().getUsuarioId(), getUser().getTerminal());
								}else if (codPersona==null&&dirPersona==null&&fechaNotificacion!=null){
									ficalizacionBo.actualizarRequerimientoNotificacion(nombreInspector,fechaInspeccion,0,observaciones,null,fechaNotificacion,Constante.ESTADO_NOTIFICADO,currentItemValue.getInspeccionId(),getUser().getUsuarioId(), getUser().getTerminal());
								 }

							//Aqui registra el/los horarios:
							   //if (currentItemValue.getFechaInspeccion()!=fechaInspeccion){
								/*Comentado en Mayo 2017, solicitado por Correo Instituc. Jimmy Díaz (14.03.2016) e Informe 053-009-00001733 - Inicio*/
//								if (lstHorarios != null && lstHorarios.length > 0) {
//									for (String p1 : lstHorarios) {
//										
//										//Integer inspc_id = ficalizacionBo.getUltimaInspeccion(codPersona);/*Cambiado el 23-07-14 Ceci A. solicito no registrar "Contribuy." en una primera instancia*/
//										Integer inspc_id = currentItemValue.getInspeccionId();
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
//							    //}	
									/*Comentado en Mayo 2017, solicitado por Correo Instituc. Jimmy Díaz (14.03.2016) e Informe 053-009-00001733 - Fin*/
									
							//Aqui registra el detalle:
							
								/*Cambiado el 23-07-14 Ceci Alvarado solicito no registrar el "detalle de Djs" en una primera instancia - CAMBIO POR BUSQUEDA DE PREDIOS*/
								int cuentaSeleccion = 0;
								//cargarPredioInsp();
								
									if (tienePredioRegistrado==Boolean.FALSE){
		
										int inspcc_id =currentItemValue.getInspeccionId();
										
										for (FindRpDjPredial i : listDjPredials) {
										if (i.isSelected()) {
											ficalizacionBo.guardarRequerimientoDetalle(nombreInspector, i.getAnioDj(), i.getDjId(), i.getPredioId(),inspcc_id,fechaInspeccion
													      ,i.getUbicacionId(),i.getSectorId(),i.getTipoViaId(),i.getViaId(),i.getManzana(),i.getCuadra(),i.getLado(),i.getDireccionCompleta());
											//(de.getDjId())
											cuentaSeleccion = cuentaSeleccion + 1;
												}
											}
									}
							}
						} else {addErrorMessage(getMsg("Verifique la fecha de notificación."));}
						// aqui fin valida persona:
					} else {addErrorMessage(getMsg("Seleccione un contribuyente y predio."));} 
				}
				else{ //LA FECHA DE NOTIFICACION ES NULA
					
					if (tipoDocumento==Constante.TIPO_DOC_CARTA_ID||tipoDocumento==Constante.TIPO_DOC_REQ_ID||tipoDocumento==Constante.TIPO_DOC_CARTA_MULT_ID)
					{
						if (codPersona!=null&&dirPersona!=null&&fechaNotificacion!=null){/*Cambiado el 23-07-14 Ceci A. solicitó no registrar "Contribuy." y "Fecha de Notif." en una primera instancia*/
						    ficalizacionBo.actualizarRequerimientoNotificacion(nombreInspector,fechaInspeccion,codPersona,observaciones,dirPersona,fechaNotificacion,Constante.ESTADO_NOTIFICADO,currentItemValue.getInspeccionId(),getUser().getUsuarioId(), getUser().getTerminal());
						}else if (codPersona==null&&dirPersona==null&&fechaNotificacion==null){
							ficalizacionBo.actualizarRequerimientoNotificacion(nombreInspector,fechaInspeccion,0,observaciones,null,null,Constante.ESTADO_EMITIDO,currentItemValue.getInspeccionId(),getUser().getUsuarioId(), getUser().getTerminal());
						}else if (codPersona!=null&&dirPersona!=null&&fechaNotificacion==null){
							ficalizacionBo.actualizarRequerimientoNotificacion(nombreInspector,fechaInspeccion,codPersona,observaciones,dirPersona,null,Constante.ESTADO_EMITIDO,currentItemValue.getInspeccionId(),getUser().getUsuarioId(), getUser().getTerminal());
						}else if (codPersona==null&&dirPersona==null&&fechaNotificacion!=null){
							ficalizacionBo.actualizarRequerimientoNotificacion(nombreInspector,fechaInspeccion,0,observaciones,null,fechaNotificacion,Constante.ESTADO_NOTIFICADO,currentItemValue.getInspeccionId(),getUser().getUsuarioId(), getUser().getTerminal());
						}

					//Aqui registra el/los horarios:
					   //if (currentItemValue.getFechaInspeccion()!=fechaInspeccion){
						/*Comentado en Mayo 2017, solicitado por Correo Instituc. Jimmy Díaz (14.03.2016) e Informe 053-009-00001733 - Inicio*/
//						if (lstHorarios != null && lstHorarios.length > 0) {
//							for (String p1 : lstHorarios) {
//								
//								//Integer inspc_id = ficalizacionBo.getUltimaInspeccion(codPersona);/*Cambiado el 23-07-14 Ceci A. solicito no registrar "Contribuy." en una primera instancia*/
//								Integer inspc_id = currentItemValue.getInspeccionId();
//								RpFiscalizacionHorarioDetalle rpFiscalizacionHora = new  RpFiscalizacionHorarioDetalle();
//								
//								rpFiscalizacionHora.setInspeccionId(inspc_id);
//								rpFiscalizacionHora.setHorarioId(mapMpTipoHoraSeleccionados.get(p1));
//								rpFiscalizacionHora.setEstado("1");
//								
//								ficalizacionBo.create(rpFiscalizacionHora);
//								
//	
//							}
//						}
//					    //}	
						/*Comentado en Mayo 2017, solicitado por Correo Instituc. Jimmy Díaz (14.03.2016) e Informe 053-009-00001733 - Fin*/
					
					//Aqui registra el detalle:
					
						/*Cambiado el 23-07-14 Ceci Alvarado solicito no registrar el "detalle de Djs" en una primera instancia - CAMBIO POR BUSQUEDA DE PREDIOS*/
						int cuentaSeleccion = 0;
						//cargarPredioInsp();
						
					if (tienePredioRegistrado==Boolean.FALSE){

						int inspcc_id =currentItemValue.getInspeccionId();
						
						for (FindRpDjPredial i : listDjPredials) {
						if (i.isSelected()) {
							ficalizacionBo.guardarRequerimientoDetalle(nombreInspector, i.getAnioDj(), i.getDjId(), i.getPredioId(),inspcc_id,fechaInspeccion
									      ,i.getUbicacionId(),i.getSectorId(),i.getTipoViaId(),i.getViaId(),i.getManzana(),i.getCuadra(),i.getLado(),i.getDireccionCompleta());
							//(de.getDjId())
							cuentaSeleccion = cuentaSeleccion + 1;
								}
							}
						}
					}
					
				}
			}//aqui fin de todo
					//}
					/**
					 * 
					 */
					else
					{
						addErrorMessage(getMsg("No se registro el requerimiento,verifique la información."));
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					WebMessages.messageFatal(e);
				}

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
			
			public Boolean validaNotificacion(){
				
				boolean fechaCorrecta = true;
								
//				if(fechaNotificacion!=null){

					if(fechaNotificacion.equals(fechaInspeccion)||fechaNotificacion.before(fechaInspeccion)){
						fechaCorrecta=true;
					}else{
						fechaCorrecta=false;
						
					}
				
				return 	fechaCorrecta;
			}
			
			public void imprimirDocxTipo(){
				try {
					if (inspeccionResultado != null && inspeccionResultado.size() > 0) {
						java.sql.Connection connection = null;
						try {
							Integer inspeccion_id= inspeccionResultado.get(0).getInspeccionId();
							Integer tipodoc_id=inspeccionResultado.get(0).getDocumentoId();
							String cadena = null;
							String cadena2 = null;
							
							if (tipodoc_id==Constante.TIPO_DOC_AINR_ID) {
								cadena = "reporte_inspeccion_ainr.jasper";
								cadena2 = "ActaInspeccionNoRealizada";
							}else
							if (tipodoc_id==Constante.TIPO_DOC_AR_ID) {
								cadena = "reporte_inspeccion_ar.jasper";
								cadena2 = "ActaReprogramacion";
							} else
								if (tipodoc_id==Constante.TIPO_DOC_EA_ID) {
									cadena = "reporte_inspeccion_ainr.jasper";
									cadena2 = "EsquelaApersonamiento";
								} 	

							
							
							CrudServiceBean connj = CrudServiceBean.getInstance();
							connection = connj.connectJasper();
							String path_context = FacesContext.getCurrentInstance()
									.getExternalContext().getRealPath("/");
							String path_report = path_context + "/sisat/reportes/";
							String path_imagen = path_context
									+ "/sisat/reportes/imagen/";
							HashMap<String, Object> parameters = new HashMap<String, Object>();
							// parameters.put("SUBREPORT_CONNECTION", connec);
							Integer val = inspeccion_id;
							
							//falta el tipo de rec como parametro
							parameters.put("p_inspeccion_id", val);
							parameters.put("ruta_imagen", path_imagen);
							parameters
									.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
							JasperPrint jasperPrint = JasperFillManager.fillReport(
									(SATWEBParameterFactory.getPathReporte() + cadena),
									parameters, connection);
							ByteArrayOutputStream output = new ByteArrayOutputStream();
							JasperExportManager.exportReportToPdfStream(jasperPrint,
									output);
							HttpServletResponse response = (HttpServletResponse) FacesContext
									.getCurrentInstance().getExternalContext()
									.getResponse();
							response.setContentType("application/pdf");
							response.addHeader("Content-Disposition",
									"attachment;filename=" + cadena2 + ".pdf");
							response.setContentLength(output.size());
							ServletOutputStream servletOutputStream = response
									.getOutputStream();
							servletOutputStream.write(output.toByteArray(), 0,
									output.size());
							servletOutputStream.flush();
							servletOutputStream.close();
							FacesContext.getCurrentInstance().responseComplete();
						} catch (Exception e) {
							e.printStackTrace();
							WebMessages.messageError(e.getMessage());
						} finally {
							try {
								if (connection != null) {
									connection.close();
									connection = null;
								}
							} catch (Exception e) {
								e.getStackTrace();
							}
						}
					} else {
						addErrorMessage(getMsg("Seleccione un Resultado. Verifique!!!"));
					}
				} catch (Exception e) {
					e.printStackTrace();
					WebMessages.messageFatal(e);
				}	
			}


			public void setPersonaInspeccion(){
				String destinoRefresh = FacesUtil.getRequestParameter("destinoRefresh");
				
				BuscarRequerimientoPersonaManaged buscarPersonaManaged=(BuscarRequerimientoPersonaManaged)getManaged("buscarRequerimientoPersonaManaged");
				buscarPersonaManaged.setPantallaUso(ReusoFormCns.BUSQU_PER_INSPECCION_ACT);
				buscarPersonaManaged.setDestinoRefresh(destinoRefresh);
			}
			
			public void copiaPersona(BuscarPersonaDTO persona){
				setDatosContribuyente(persona);
			}
	
//			public String sendRedirectBusqueda() {
//				return "/sisat/predial/buscarrequerimiento.xhtml";
//			}
			
			
			public void valueChangeListenerItem(FindInspeccionDj multaDTO) {
				if (multaDTO.isSelected()) {
					if (djSeleccionada.equals(multaDTO)) {
						djSeleccionada = new FindInspeccionDj();
					}
				} else {
					djSeleccionada = multaDTO;
				}
			}
			
			public void valueChangeListenerPredio(FindRpDjPredial predioSeleccion) {
				if (predioSeleccion.isSelected()) {
					if (predioSeleccionado.equals(predioSeleccion)) {
						predioSeleccionado = new FindRpDjPredial();
					}
				} else {
					predioSeleccionado = predioSeleccion;
				}
			}
			//private List<MultaDTO> lstMultaDTOs;
			
			/**
			 * Generando multas desde Control de Requerimientos
			 */
//			public void mostrar() {
//				try {
//					
//					//Integer id =inspeccionResultadoTabla.getInspeccionId();
//					//historico=ficalizacionBo.getAllInspeccionesDetalle(currentItemValue.getInspeccionId());
//					
//					this.obligacionDTO = new ObligacionDTO();
//					this.obligacionDTO.setPersonaId(currentItemValue.getPersonaId());
//					this.obligacionDTO.setUnidadId(64);
////					this.obligacionDTO.setFechaAdquision(currentItemValue.getFechaAr());
//										
//						//OBTIENE PREDIOS POR CONTRIBUYENTE:
//						listDjPredials = registroPrediosBo.getRpDjpredial(null, null, null, null, null, null, null,
//										 null, null, null, null, currentItemValue.getPersonaId(), true);
//						
//						//OBTIENE PREDIOS POR CONTRIBUYENTE Y REQUERIMIENTO DE INSPECCION:
//					
//							//listaPrediosInspeccion=ficalizacionBo.getInspeccionesByPredio(currentItemValue.getPersonaId(), currentItemValue.getInspeccionId());
//
//						List<SubConceptoDTO> listSubConceptoDTOs = new ArrayList<SubConceptoDTO>();
//
//						getObligacionDTO().setConceptoId(this.MULTAS);
//						getObligacionDTO().setConceptoDescripcion("MULTAS");
//						listSubConceptoDTOs = obligacionBo.getSubConceptoMultas(obligacionDTO.getAnnoAfectacion());
//
//						listSubConceptoDTOItems = null;
//						listSubConceptoDTOItems = new ArrayList<SelectItem>();
//
//						for (SubConceptoDTO subConceptoDTO : listSubConceptoDTOs) {
//
//							listSubConceptoDTOItems.add(new SelectItem(subConceptoDTO
//									.getDescripcionCorta(), subConceptoDTO
//									.getDescripcionCorta()));
//							mapSupConcepto.put(subConceptoDTO.getDescripcionCorta(),
//									subConceptoDTO);
//						}
//
//						//lstMultaDTOs = obligacionBo.buscarMultas(obligacionDTO);
//
//						//sumarMultas(lstMultaDTOs);
//
//				} catch (Exception e) {
//					e.printStackTrace();
//					WebMessages.messageFatal(e);
//				}
//			}

			/**
			 * Generando multas desde Control de Requerimientos
			 */
			
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

	public FindInspeccionDj getFindDjInsp() {
		return findDjInsp;
	}

	public void setFindDjInsp(FindInspeccionDj findDjInsp) {
		this.findDjInsp = findDjInsp;
	}

	public FindInspeccionDj getDjSeleccionada() {
		return djSeleccionada;
	}

	public void setDjSeleccionada(FindInspeccionDj djSeleccionada) {
		this.djSeleccionada = djSeleccionada;
	}

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

	public java.util.Date getFechaInspeccionRegistrada() {
		return fechaInspeccionRegistrada;
	}

	public void setFechaInspeccionRegistrada(
			java.util.Date fechaInspeccionRegistrada) {
		this.fechaInspeccionRegistrada = fechaInspeccionRegistrada;
	}

	public Integer getInspeccionId() {
		return inspeccionId;
	}

	public void setInspeccionId(Integer inspeccionId) {
		this.inspeccionId = inspeccionId;
	}

	public Integer getTipoDcId() {
		return tipoDcId;
	}

	public void setTipoDcId(Integer tipoDcId) {
		this.tipoDcId = tipoDcId;
	}

	public FindInspeccionById getFindInspeccion() {
		return findInspeccion;
	}

	public void setFindInspeccion(FindInspeccionById findInspeccion) {
		this.findInspeccion = findInspeccion;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

//	public Map<Integer, String> getMapIDoc() {
//		return mapIDoc;
//	}
//
//	public void setMapIDoc(Map<Integer, String> mapIDoc) {
//		this.mapIDoc = mapIDoc;
//	}

	public Map<String, Integer> getMapDoc() {
		return mapDoc;
	}

	public void setMapDoc(Map<String, Integer> mapDoc) {
		this.mapDoc = mapDoc;
	}

	public Map<Integer, String> getMapITipoDoc() {
		return mapITipoDoc;
	}

	public void setMapITipoDoc(Map<Integer, String> mapITipoDoc) {
		this.mapITipoDoc = mapITipoDoc;
	}

	public Map<String, FindInpscDocTipo> getMapFindInpscDoc() {
		return mapFindInpscDoc;
	}

	public void setMapFindInpscDoc(Map<String, FindInpscDocTipo> mapFindInpscDoc) {
		this.mapFindInpscDoc = mapFindInpscDoc;
	}

	public FindInspeccionHistorial getCurrentItemValue() {
		return currentItemValue;
	}

	public void setCurrentItemValue(FindInspeccionHistorial currentItemValue) {
		this.currentItemValue = currentItemValue;
	}

	public String getCmbCargo() {
		return cmbCargo;
	}

	public void setCmbCargo(String cmbCargo) {
		this.cmbCargo = cmbCargo;
	}

	public HtmlComboBox getCmbxCargo() {
		return cmbxCargo;
	}

	public void setCmbxCargo(HtmlComboBox cmbxCargo) {
		this.cmbxCargo = cmbxCargo;
	}

	public Integer getCargoId() {
		return cargoId;
	}

	public void setCargoId(Integer cargoId) {
		this.cargoId = cargoId;
	}

	public HashMap<String, Integer> getMapCargo() {
		return mapCargo;
	}

	public void setMapCargo(HashMap<String, Integer> mapCargo) {
		this.mapCargo = mapCargo;
	}

	public List<SelectItem> getListaCargos() {
		return listaCargos;
	}

	public void setListaCargos(List<SelectItem> listaCargos) {
		this.listaCargos = listaCargos;
	}

	public Boolean getIstipoFip() {
		return istipoFip;
	}

	public void setIstipoFip(Boolean istipoFip) {
		this.istipoFip = istipoFip;
	}

	public Boolean getIstipoAinr() {
		return istipoAinr;
	}

	public void setIstipoAinr(Boolean istipoAinr) {
		this.istipoAinr = istipoAinr;
	}

	public FindInspeccionDocCargoTipo getFindCargoInsp() {
		return findCargoInsp;
	}

	public void setFindCargoInsp(FindInspeccionDocCargoTipo findCargoInsp) {
		this.findCargoInsp = findCargoInsp;
	}

	
	public void setCorrelativoCargo(
			List<FindInspeccionDocCargoTipo> correlativoCargo) {
		this.correlativoCargo = correlativoCargo;
	}

	public List<FindInspeccionDocCargoTipo> getCorrelativoCargo() {
		return correlativoCargo;
	}

	public String getCorrelativo() {
		return correlativo;
	}

	public void setCorrelativo(String correlativo) {
		this.correlativo = correlativo;
	}

	public boolean isActaReprograma() {
		return actaReprograma;
	}

	public void setActaReprograma(boolean actaReprograma) {
		this.actaReprograma = actaReprograma;
	}

	public String getCorrelativoEsquela() {
		return correlativoEsquela;
	}

	public void setCorrelativoEsquela(String correlativoEsquela) {
		this.correlativoEsquela = correlativoEsquela;
	}

	public String getCorrelativoAR() {
		return correlativoAR;
	}

	public void setCorrelativoAR(String correlativoAR) {
		this.correlativoAR = correlativoAR;
	}

	public Date getFechaNotificacionEsquela() {
		return fechaNotificacionEsquela;
	}

	public void setFechaNotificacionEsquela(Date fechaNotificacionEsquela) {
		this.fechaNotificacionEsquela = fechaNotificacionEsquela;
	}

	public String getCmbInspectorAr() {
		return cmbInspectorAr;
	}

	public void setCmbInspectorAr(String cmbInspectorAr) {
		this.cmbInspectorAr = cmbInspectorAr;
	}

	public HtmlComboBox getCmbxInspectorAr() {
		return cmbxInspectorAr;
	}

	public void setCmbxInspectorAr(HtmlComboBox cmbxInspectorAr) {
		this.cmbxInspectorAr = cmbxInspectorAr;
	}

	public Integer getInspectorArId() {
		return inspectorArId;
	}

	public void setInspectorArId(Integer inspectorArId) {
		this.inspectorArId = inspectorArId;
	}

	public HashMap<String, Integer> getMapInspectorAr() {
		return mapInspectorAr;
	}

	public void setMapInspectorAr(HashMap<String, Integer> mapInspectorAr) {
		this.mapInspectorAr = mapInspectorAr;
	}

	public List<SelectItem> getListaInspectoresAr() {
		return listaInspectoresAr;
	}

	public void setListaInspectoresAr(List<SelectItem> listaInspectoresAr) {
		this.listaInspectoresAr = listaInspectoresAr;
	}

	public String[] getLstHorariosAr() {
		return lstHorariosAr;
	}

	public void setLstHorariosAr(String[] lstHorariosAr) {
		this.lstHorariosAr = lstHorariosAr;
	}

	public List<RpFiscalizacionHorario> getLstHorariosArInspc() {
		return lstHorariosArInspc;
	}

	public void setLstHorariosArInspc(
			List<RpFiscalizacionHorario> lstHorariosArInspc) {
		this.lstHorariosArInspc = lstHorariosArInspc;
	}

	public HashMap<String, Integer> getMapMpTipoHoraArSeleccionados() {
		return mapMpTipoHoraArSeleccionados;
	}

	public void setMapMpTipoHoraArSeleccionados(
			HashMap<String, Integer> mapMpTipoHoraArSeleccionados) {
		this.mapMpTipoHoraArSeleccionados = mapMpTipoHoraArSeleccionados;
	}

	public Date getFechaNotificacionAr() {
		return fechaNotificacionAr;
	}

	public void setFechaNotificacionAr(Date fechaNotificacionAr) {
		this.fechaNotificacionAr = fechaNotificacionAr;
	}

	public Date getFechaResultado() {
		return fechaResultado;
	}

	public void setFechaResultado(Date fechaResultado) {
		this.fechaResultado = fechaResultado;
	}

	public Boolean getOcultarBoton() {
		return ocultarBoton;
	}

	public void setOcultarBoton(Boolean ocultarBoton) {
		this.ocultarBoton = ocultarBoton;
	}

	public List<FindInspeccionByResultado> getInspeccionResultado() {
		return inspeccionResultado;
	}

	public void setInspeccionResultado(
			List<FindInspeccionByResultado> inspeccionResultado) {
		this.inspeccionResultado = inspeccionResultado;
	}

	public FindInspeccionByResultado getInspeccionResultadoTabla() {
		return inspeccionResultadoTabla;
	}

	public void setInspeccionResultadoTabla(
			FindInspeccionByResultado inspeccionResultadoTabla) {
		this.inspeccionResultadoTabla = inspeccionResultadoTabla;
	}

	

	public String getDirPersona() {
		return dirPersona;
	}

	public void setDirPersona(String dirPersona) {
		this.dirPersona = dirPersona;
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

	public Boolean getIsNotificado() {
		return isNotificado;
	}

	public void setIsNotificado(Boolean isNotificado) {
		this.isNotificado = isNotificado;
	}

	public String getDescripcionFip() {
		return descripcionFip;
	}

	public void setDescripcionFip(String descripcionFip) {
		this.descripcionFip = descripcionFip;
	}

	public String getDescripcionAinr() {
		return descripcionAinr;
	}

	public void setDescripcionAinr(String descripcionAinr) {
		this.descripcionAinr = descripcionAinr;
	}

	public List<FindInspeccionHistorial> getListacorrelativo() {
		return listacorrelativo;
	}

	public void setListacorrelativo(List<FindInspeccionHistorial> listacorrelativo) {
		this.listacorrelativo = listacorrelativo;
	}

	public Date getFechaNotificacionfip() {
		return fechaNotificacionfip;
	}

	public void setFechaNotificacionfip(Date fechaNotificacionfip) {
		this.fechaNotificacionfip = fechaNotificacionfip;
	}

	public Date getFechaNotificacionainr() {
		return fechaNotificacionainr;
	}

	public void setFechaNotificacionainr(Date fechaNotificacionainr) {
		this.fechaNotificacionainr = fechaNotificacionainr;
	}

	public FindInspeccionHistorial getInspeccionHistorial() {
		return inspeccionHistorial;
	}

	public List<FindInspeccionHistorialDetalle> getHistorico() {
		return historico;
	}

	public void setHistorico(List<FindInspeccionHistorialDetalle> historico) {
		this.historico = historico;
	}

	public String getCmbUnidad() {
		return cmbUnidad;
	}

	public void setCmbUnidad(String cmbUnidad) {
		this.cmbUnidad = cmbUnidad;
	}

	public ObligacionDTO getObligacionDTO() {
		return obligacionDTO;
	}

	public void setObligacionDTO(ObligacionDTO obligacionDTO) {
		this.obligacionDTO = obligacionDTO;
	}

	public String getCmbSubConcepto() {
		return cmbSubConcepto;
	}

	public void setCmbSubConcepto(String cmbSubConcepto) {
		this.cmbSubConcepto = cmbSubConcepto;
	}

	public SubConceptoDTO getSubConceptoDTO() {
		return subConceptoDTO;
	}

	public void setSubConceptoDTO(SubConceptoDTO subConceptoDTO) {
		this.subConceptoDTO = subConceptoDTO;
	}

	public HashMap<String, SubConceptoDTO> getMapSupConcepto() {
		return mapSupConcepto;
	}

	public void setMapSupConcepto(HashMap<String, SubConceptoDTO> mapSupConcepto) {
		this.mapSupConcepto = mapSupConcepto;
	}

	public List<SelectItem> getListSubConceptoDTOItems() {
		return listSubConceptoDTOItems;
	}

	public void setListSubConceptoDTOItems(List<SelectItem> listSubConceptoDTOItems) {
		this.listSubConceptoDTOItems = listSubConceptoDTOItems;
	}

	public String getSubConceptoDescripcion() {
		return subConceptoDescripcion;
	}

	public void setSubConceptoDescripcion(String subConceptoDescripcion) {
		this.subConceptoDescripcion = subConceptoDescripcion;
	}

	public String getCmbTributoAsociado() {
		return cmbTributoAsociado;
	}

	public void setCmbTributoAsociado(String cmbTributoAsociado) {
		this.cmbTributoAsociado = cmbTributoAsociado;
	}

	public Integer getContexto() {
		return contexto;
	}

	public void setContexto(Integer contexto) {
		this.contexto = contexto;
	}

	public Date getFechaAdquision() {
		return fechaAdquision;
	}

	public void setFechaAdquision(Date fechaAdquision) {
		this.fechaAdquision = fechaAdquision;
	}

	public int getTIPOPREDIO() {
		return TIPOPREDIO;
	}

	public void setTIPOPREDIO(int tIPOPREDIO) {
		TIPOPREDIO = tIPOPREDIO;
	}

	public int getTIPOVEHICULO() {
		return TIPOVEHICULO;
	}

	public void setTIPOVEHICULO(int tIPOVEHICULO) {
		TIPOVEHICULO = tIPOVEHICULO;
	}

	public int getTipoReferenciaOblg() {
		return tipoReferenciaOblg;
	}

	public void setTipoReferenciaOblg(int tipoReferenciaOblg) {
		this.tipoReferenciaOblg = tipoReferenciaOblg;
	}

	public String getCodigoPlacaReferencia() {
		return codigoPlacaReferencia;
	}

	public void setCodigoPlacaReferencia(String codigoPlacaReferencia) {
		this.codigoPlacaReferencia = codigoPlacaReferencia;
	}

	public Boolean getPresentoDocumentos() {
		return presentoDocumentos;
	}

	public void setPresentoDocumentos(Boolean presentoDocumentos) {
		this.presentoDocumentos = presentoDocumentos;
	}

	public String getGlosa() {
		return glosa;
	}

	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}

	public int getAnnoAfectacion() {
		return annoAfectacion;
	}

	public void setAnnoAfectacion(int annoAfectacion) {
		this.annoAfectacion = annoAfectacion;
	}

	public List<FindInspeccionDj> getListaPrediosInspeccion() {
		return listaPrediosInspeccion;
	}

	public void setListaPrediosInspeccion(
			List<FindInspeccionDj> listaPrediosInspeccion) {
		this.listaPrediosInspeccion = listaPrediosInspeccion;
	}

	public List<FindRpDjPredial> getListDjPredials() {
		return listDjPredials;
	}

	public void setListDjPredials(List<FindRpDjPredial> listDjPredials) {
		this.listDjPredials = listDjPredials;
	}

	public List<MultaDTO> getLstMultaDTOs() {
		return lstMultaDTOs;
	}

	public void setLstMultaDTOs(List<MultaDTO> lstMultaDTOs) {
		this.lstMultaDTOs = lstMultaDTOs;
	}

	public BigDecimal getTotalAPagar() {
		return totalAPagar;
	}

	public void setTotalAPagar(BigDecimal totalAPagar) {
		this.totalAPagar = totalAPagar;
	}

	public BigDecimal getTotalDscto() {
		return totalDscto;
	}

	public void setTotalDscto(BigDecimal totalDscto) {
		this.totalDscto = totalDscto;
	}

	public BigDecimal getTotalSubTotal() {
		return totalSubTotal;
	}

	public void setTotalSubTotal(BigDecimal totalSubTotal) {
		this.totalSubTotal = totalSubTotal;
	}

	public BigDecimal getTotalInteres() {
		return totalInteres;
	}

	public void setTotalInteres(BigDecimal totalInteres) {
		this.totalInteres = totalInteres;
	}

	public BigDecimal getTotalMonto() {
		return totalMonto;
	}

	public void setTotalMonto(BigDecimal totalMonto) {
		this.totalMonto = totalMonto;
	}

	public Boolean getIsDjOmisa() {
		return isDjOmisa;
	}

	public void setIsDjOmisa(Boolean isDjOmisa) {
		this.isDjOmisa = isDjOmisa;
	}

	public Integer getEstadoRecuperado() {
		return estadoRecuperado;
	}

	public void setEstadoRecuperado(Integer estadoRecuperado) {
		this.estadoRecuperado = estadoRecuperado;
	}

	
	public HashMap<Integer, String> getMapInspectorR() {
		return mapInspectorR;
	}

	public void setMapInspectorR(HashMap<Integer, String> mapInspectorR) {
		this.mapInspectorR = mapInspectorR;
	}

	public List<FindInspeccionDj> getListaPredioInspeccion() {
		return listaPredioInspeccion;
	}

	public void setListaPredioInspeccion(
			List<FindInspeccionDj> listaPredioInspeccion) {
		this.listaPredioInspeccion = listaPredioInspeccion;
	}

	public FindRpDjPredial getPredioSeleccionado() {
		return predioSeleccionado;
	}

	public void setPredioSeleccionado(FindRpDjPredial predioSeleccionado) {
		this.predioSeleccionado = predioSeleccionado;
	}

	public Boolean getTienePredioRegistrado() {
		return tienePredioRegistrado;
	}

	public void setTienePredioRegistrado(Boolean tienePredioRegistrado) {
		this.tienePredioRegistrado = tienePredioRegistrado;
	}

	public Boolean getHabilitado() {
		return habilitado;
	}

	public void setHabilitado(Boolean habilitado) {
		this.habilitado = habilitado;
	}

	public Boolean getIsHorarioProgramado() {
		return isHorarioProgramado;
	}

	public void setIsHorarioProgramado(Boolean isHorarioProgramado) {
		this.isHorarioProgramado = isHorarioProgramado;
	}

	public Boolean getIsCorrelativoRegistrado() {
		return isCorrelativoRegistrado;
	}

	public void setIsCorrelativoRegistrado(Boolean isCorrelativoRegistrado) {
		this.isCorrelativoRegistrado = isCorrelativoRegistrado;
	}

	public Boolean getEsHorarioIgual() {
		return esHorarioIgual;
	}

	public void setEsHorarioIgual(Boolean esHorarioIgual) {
		this.esHorarioIgual = esHorarioIgual;
	}

	public Boolean getEsFIP() {
		return esFIP;
	}

	public void setEsFIP(Boolean esFIP) {
		this.esFIP = esFIP;
	}

	public Boolean getEsAINR() {
		return esAINR;
	}

	public void setEsAINR(Boolean esAINR) {
		this.esAINR = esAINR;
	}

	
	public Boolean getIsinspectorProgramado() {
		return isinspectorProgramado;
	}

	public void setIsinspectorProgramado(Boolean isinspectorProgramado) {
		this.isinspectorProgramado = isinspectorProgramado;
	}

	public Boolean getIsFechaProgramada() {
		return isFechaProgramada;
	}

	public void setIsFechaProgramada(Boolean isFechaProgramada) {
		this.isFechaProgramada = isFechaProgramada;
	}

	public Boolean getEsEmitido() {
		return esEmitido;
	}

	public void setEsEmitido(Boolean esEmitido) {
		this.esEmitido = esEmitido;
	}

	public Boolean getEsNotif() {
		return esNotif;
	}

	public void setEsNotif(Boolean esNotif) {
		this.esNotif = esNotif;
	}

	public Boolean getIsHorarioReprogramado() {
		return isHorarioReprogramado;
	}

	public void setIsHorarioReprogramado(Boolean isHorarioReprogramado) {
		this.isHorarioReprogramado = isHorarioReprogramado;
	}

	public Integer getNombreInspectorAr() {
		return nombreInspectorAr;
	}

	public void setNombreInspectorAr(Integer nombreInspectorAr) {
		this.nombreInspectorAr = nombreInspectorAr;
	}

	public Date getFechaInspeccionAr() {
		return fechaInspeccionAr;
	}

	public void setFechaInspeccionAr(Date fechaInspeccionAr) {
		this.fechaInspeccionAr = fechaInspeccionAr;
	}

	public List<FindInspeccionDj> getListaDjsInspeccion() {
		return listaDjsInspeccion;
	}

	public void setListaDjsInspeccion(List<FindInspeccionDj> listaDjsInspeccion) {
		this.listaDjsInspeccion = listaDjsInspeccion;
	}

	public String getPeridoInspeccion() {
		return peridoInspeccion;
	}

	public void setPeridoInspeccion(String peridoInspeccion) {
		this.peridoInspeccion = peridoInspeccion;
	}

	public String getNroDocAsociado() {
		return nroDocAsociado;
	}

	public void setNroDocAsociado(String nroDocAsociado) {
		this.nroDocAsociado = nroDocAsociado;
	}

	public String getCorrelativoFipar() {
		return correlativoFipar;
	}

	public void setCorrelativoFipar(String correlativoFipar) {
		this.correlativoFipar = correlativoFipar;
	}

	public Date getFechaNotificacionFipar() {
		return fechaNotificacionFipar;
	}

	public void setFechaNotificacionFipar(Date fechaNotificacionFipar) {
		this.fechaNotificacionFipar = fechaNotificacionFipar;
	}

	public Boolean getConResultado() {
		return conResultado;
	}

	public void setConResultado(Boolean conResultado) {
		this.conResultado = conResultado;
	}

	public Boolean getEsAr() {
		return esAr;
	}

	public void setEsAr(Boolean esAr) {
		this.esAr = esAr;
	}

	public Boolean getEsArFIP() {
		return esArFIP;
	}

	public void setEsArFIP(Boolean esArFIP) {
		this.esArFIP = esArFIP;
	}

	public Boolean getIstipoAr() {
		return istipoAr;
	}

	public void setIstipoAr(Boolean istipoAr) {
		this.istipoAr = istipoAr;
	}

	public boolean isEsCorrelativoFipAr() {
		return esCorrelativoFipAr;
	}

	public void setEsCorrelativoFipAr(boolean esCorrelativoFipAr) {
		this.esCorrelativoFipAr = esCorrelativoFipAr;
	}

	public boolean isEsCorrelativoAr() {
		return esCorrelativoAr;
	}

	public void setEsCorrelativoAr(boolean esCorrelativoAr) {
		this.esCorrelativoAr = esCorrelativoAr;
	}

	public boolean isEsMayor() {
		return esMayor;
	}

	public void setEsMayor(boolean esMayor) {
		this.esMayor = esMayor;
	}

	public boolean isEsCorrelativoFip() {
		return esCorrelativoFip;
	}

	public void setEsCorrelativoFip(boolean esCorrelativoFip) {
		this.esCorrelativoFip = esCorrelativoFip;
	}

	public boolean isEsCorrelativoAinr() {
		return esCorrelativoAinr;
	}

	public void setEsCorrelativoAinr(boolean esCorrelativoAinr) {
		this.esCorrelativoAinr = esCorrelativoAinr;
	}

	public Boolean getIstipoInforme() {
		return istipoInforme;
	}

	public void setIstipoInforme(Boolean istipoInforme) {
		this.istipoInforme = istipoInforme;
	}

	public Boolean getEsINF() {
		return esINF;
	}

	public void setEsINF(Boolean esINF) {
		this.esINF = esINF;
	}

	public HtmlInputText getTxtCorrelativo() {
		return txtCorrelativo;
	}

	public void setTxtCorrelativo(HtmlInputText txtCorrelativo) {
		this.txtCorrelativo = txtCorrelativo;
	}


	public boolean isPermisoModificarActualizar() {
		return permisoModificarActualizar;
	}


	public void setPermisoModificarActualizar(boolean permisoModificarActualizar) {
		this.permisoModificarActualizar = permisoModificarActualizar;
	}


	
	


//	public List<FindInspeccionByResultado> getInspeccionResultado() {
//		return inspeccionResultado;
//	}
//
//	public void setInspeccionResultado(
//			List<FindInspeccionByResultado> inspeccionResultado) {
//		this.inspeccionResultado = inspeccionResultado;
//	}
	
}
