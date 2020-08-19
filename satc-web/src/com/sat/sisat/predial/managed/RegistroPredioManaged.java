package com.sat.sisat.predial.managed;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlSelectOneRadio;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import org.richfaces.component.html.HtmlComboBox;
import org.richfaces.component.html.HtmlExtendedDataTable;
import org.richfaces.component.html.HtmlInputText;
import org.richfaces.model.selection.SimpleSelection;

import com.sat.sisat.common.security.UserSession;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.FacesUtil;
import com.sat.sisat.common.util.MesUtil;
import com.sat.sisat.common.util.Util;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.common.vo.EstadoArea;
import com.sat.sisat.common.vo.EstadoTipoTierra;
import com.sat.sisat.controlycobranza.dto.MpFiscalizador;
import com.sat.sisat.fiscalizacion.business.FiscalizacionBoRemote;
import com.sat.sisat.fiscalizacion.dto.FindInspeccionByIdAsociada;
import com.sat.sisat.fiscalizacion.dto.FindInspeccionDj;
import com.sat.sisat.fiscalizacion.dto.FindInspeccionHistorial;
import com.sat.sisat.fiscalizacion.managed.ActualizarRequerimientoManaged;
import com.sat.sisat.persistence.entity.DtFactorOfic;
import com.sat.sisat.persistence.entity.GnNotaria;
import com.sat.sisat.persistence.entity.MpCondiEspePredio;
import com.sat.sisat.persistence.entity.MpPersona;
import com.sat.sisat.persistence.entity.MpPredio;
import com.sat.sisat.persistence.entity.RjDocuAnexo;
import com.sat.sisat.persistence.entity.RjMes;
import com.sat.sisat.persistence.entity.RpAltitud;
import com.sat.sisat.persistence.entity.RpCategoriaRustico;
import com.sat.sisat.persistence.entity.RpCondicionPropiedad;
import com.sat.sisat.persistence.entity.RpDjarbitrio;
import com.sat.sisat.persistence.entity.RpDjconstruccion;
import com.sat.sisat.persistence.entity.RpDjdireccion;
import com.sat.sisat.persistence.entity.RpDjpredial;
import com.sat.sisat.persistence.entity.RpDjuso;
import com.sat.sisat.persistence.entity.RpDjusoDetalle;
import com.sat.sisat.persistence.entity.RpFiscaInspeccion;
import com.sat.sisat.persistence.entity.RpInstalacionDj;
import com.sat.sisat.persistence.entity.RpOtrosFrente;
import com.sat.sisat.persistence.entity.RpTipoAdquisicion;
import com.sat.sisat.persistence.entity.RpTipoObra;
import com.sat.sisat.persistence.entity.RpTipoObraPeriodo;
import com.sat.sisat.persistence.entity.RpTipoTierraRustico;
import com.sat.sisat.persistence.entity.RpTipoUsoPredioRustico;
import com.sat.sisat.persistence.entity.RpUbicacionPredio;
import com.sat.sisat.persistence.entity.RvMotivoDeclaracion;
import com.sat.sisat.persona.business.PersonaBoRemote;
import com.sat.sisat.persona.managed.RegistroPersonaManaged;
import com.sat.sisat.predial.business.RegistroPrediosBoRemote;
import com.sat.sisat.predial.dto.FindMpPersona;
import com.sat.sisat.predial.dto.FindPersona;
import com.sat.sisat.predial.dto.RelacionadosDTO;
import com.sat.sisat.vehicular.business.VehicularBoRemote;
import com.sat.sisat.vehicular.cns.ReusoFormCns;
import com.sat.sisat.vehicular.dto.BuscarPersonaDTO;
import com.sat.sisat.vehicular.managed.BuscarPersonaManaged;

@ManagedBean
@ViewScoped
public class RegistroPredioManaged extends BaseManaged {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7464867766546413476L;

	@EJB
	RegistroPrediosBoRemote registroPrediosBo;
	
	@EJB
	VehicularBoRemote vehicularBo;
	
	@EJB
	PersonaBoRemote personaBo;
	
	@EJB
	FiscalizacionBoRemote ficalizacionBo;
	
	@Inject
	UserSession user;
	
	private boolean esUrbano; 
	private HashMap<String, String> mapRjMes=new HashMap<String, String>();
	
	private String direccionPredio;
	
	private String tipoPredio;
	private String predioHabitable;
	
	private String resultado;
	
	private HtmlInputText txtPorcentaje;
	private HtmlInputText txtAreaTerreno;
	
	private HtmlComboBox cmbRpUbicacionPredio;
	private List<SelectItem> lstUbicacionPredio=new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRpUbicacionPredio=new HashMap<String, Integer>();
	private HashMap<Integer,String> mapIRpUbicacionPredio=new HashMap<Integer,String>();
	
	private String cmbValueUbicacionPredio;
	
	private HtmlComboBox cmbRpCondicionPropiedad;
	private List<SelectItem> lstCondicionPropiedad=new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRpCondicionPropiedad=new HashMap<String, Integer>();
	private HashMap<Integer,String> mapIRpCondicionPropiedad=new HashMap<Integer,String>();
	
	private String cmbValueCondicionPropiedad;
	
	private HtmlComboBox cmbRpTipoAdquisicion;
	private List<SelectItem> lstTipoAdquisicion=new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRpTipoAdquisicion=new HashMap<String, Integer>();
	private HashMap<Integer,String> mapIRpTipoAdquisicion=new HashMap<Integer,String>();
	
	private String cmbValueTipoAdquisicion;
	
	private HtmlComboBox cmbMpCondiEspePredio;
	private List<SelectItem> lstCondiEspePredio=new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapMpCondiEspePredio=new HashMap<String, Integer>();
	private HashMap<Integer,String> mapIMpCondiEspePredio=new HashMap<Integer,String>();
	private String cmbValueCondiEspePredio;
	
	//RpTipoTierraRustico
	private HtmlComboBox cmbTipoTierraRustico;
	private List<SelectItem> lstTipoTierraRustico=new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRpTipoTierraRustico=new HashMap<String, Integer>();
	private HashMap<Integer,String> mapIRpTipoTierraRustico=new HashMap<Integer,String>();
	private String cmbValueTipoTierraRustico;
	
	//RpAltitud
	private HtmlComboBox cmbAltitud;
	private List<SelectItem> lstAltitud=new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRpAltitud=new HashMap<String, Integer>();
	private HashMap<Integer,String> mapIRpAltitud=new HashMap<Integer,String>();
	
	private String cmbValueAltitud;
	
	//RpCategoriaRustico
	private HtmlComboBox cmbCategoriaRustico;
	private List<SelectItem> lstCategoriaRustico=new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRpCategoriaRustico=new HashMap<String, Integer>();
	private HashMap<Integer,String> mapIRpCategoriaRustico=new HashMap<Integer,String>();
	
	//RpTipoUsoPredioRustico
	private HtmlComboBox cmbTipoUsoRustico;
	private List<SelectItem> lstTipoUsoRustico=new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRpTipoUsoPredioRustico=new HashMap<String, Integer>();
	private HashMap<Integer,String> mapIRpTipoUsoPredioRustico=new HashMap<Integer,String>();
	
	//Motivo de declaracion
	private HashMap<Integer,String> mapIRvMotivoDecla=new HashMap<Integer,String>();
	
	//Datos de la transferencia
	private String selectedNotaria;//
	private List<SelectItem> lstNotarias = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapGnNotaria = new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIGnNotaria = new HashMap<Integer, String>();
	
	private List<SelectItem> lstAltitudCmb=new ArrayList<SelectItem>();
	private List<SelectItem> lstCategoriaRusticoCmb=new ArrayList<SelectItem>();
	
	private String cmbValueCategoriaRustico;
	private String cmbValueTipoUsoRustico;
	
	private RpDjpredial rpDjpredial;
	private MpPredio mpPredio;
	private FindPersona persona;
	
	private java.util.Date fechaAdquisicion;
	private String areaTerreno;
	private String areaTerrenoHas;
	private String areaTerrenoComun;
	private String areaTerrenoComunHas;
	private String frente;
	
	private BigDecimal frenteAreaComun;
	private BigDecimal distanciaAPredio;
	private BigDecimal porcentajeParticipacion;
	
	private ArrayList<RpDjconstruccion> records;
	private ArrayList<RpDjconstruccion> recordsAllConstruccion;
	private SimpleSelection selection = new SimpleSelection();
	private HtmlExtendedDataTable table;
	private int currentRow;
	private RpDjconstruccion currentItem = new RpDjconstruccion();
	
	private Boolean isCondiEspePredio;
	private Boolean isHabitable;
	private Boolean is100porciento;
	public Boolean esConstruccion;
	
	public Boolean poseeAreaComun;
	
	private String outputtextTituloCondicionEspecial;
	
	private ArrayList<RpInstalacionDj> recordsOtrasInsta;
	private RpInstalacionDj currentItemOtrasInsta = new RpInstalacionDj();
	
	private ArrayList<RpOtrosFrente> recordsOtrosFrentes;
	private RpOtrosFrente currentItemOtrosFrentes = new RpOtrosFrente();
	
	private ArrayList<RpDjuso> recordsUsosDelPredio;
	private RpDjuso currentItemUsosDelPredio=new RpDjuso(); 
	
	private ArrayList<RjDocuAnexo> recordsDocAnexos;
	private RjDocuAnexo currentItemDocAnexos=new RjDocuAnexo();
	
	private ArrayList<RelacionadosDTO> listRelacionados;
	
	HashMap<Integer, ArrayList<RpDjusoDetalle>> recordsUsosDelPredioxNivel=new HashMap<Integer, ArrayList<RpDjusoDetalle>>(); 
	private BigDecimal porcentaje;
	private Boolean datoscorrectos;
	private Integer ubicacionPredioId;
	
	//Controla la logica campos requeridos para las distintas areas de terrenos Urbanos
	private HashMap<Integer,EstadoArea> mapEstadoArea=new HashMap<Integer,EstadoArea>();
	private HashMap<Integer,EstadoTipoTierra> mapEstadoTipoTierra=new HashMap<Integer,EstadoTipoTierra>();
	
	private String fiscaAceptado = "NO ACEPTADO POR CONTRIBUYENTE";
	private boolean flagFiscaPrevio;

	//Indica el motivo de la declaracion
	private Integer motivoDeclaracion;
	
	//Transferente
	private BuscarPersonaDTO transferente;
	
	private String usuario;
	private java.util.Date fechaActual;
	
	private String glosa;
	
	private boolean secano;
	private boolean frenteOk;
	private boolean frenteVia;
	
	//PARA FISCALIZACION - INSPECCION
	private RpFiscaInspeccion inspeccionFisca;	
	private String valueComboBoxTipoInspeccion = "";	
	private List<SelectItem> listTipoInspeccion = new ArrayList<SelectItem>();	
	private boolean tipoAR;
	private boolean fisca;
	private boolean requerimiento;
	private java.util.Date fechaRequerimiento;
	private java.util.Date fechaInspeccion;
	
	private Boolean tieneInspeccion;
	private Boolean isInspeccion;
	private Boolean isEditable;
	private FindInspeccionHistorial currentItemValorInsp;
	private List<FindInspeccionDj> listaDjInspeccion = new ArrayList<FindInspeccionDj>();
	private FindInspeccionDj inspecciones;
	private Integer nombreInspector;
	private Integer nombreInspectorAr;
	private Date fechaInspeccionAr;
	private Boolean esFIP;
	private Boolean esAINR;
	
	//Asociar requerimiento a traves de un combo:
		private String cmbReq;
		private HtmlComboBox cmbxReq;
		private Integer reqId;
		private HashMap<String, Integer> mapReq = new HashMap<String, Integer>();
		private List<SelectItem> listaReqs = new ArrayList<SelectItem>();
	
	//Asociar requerimiento a traves de un panel:
		private List<FindInspeccionDj> listarequer = new ArrayList<FindInspeccionDj>();
		private Integer reqIdElegida;
		private String  reqNroElegida;
		private List<FindInspeccionByIdAsociada> inspeccionelegida = new ArrayList<FindInspeccionByIdAsociada>();
	
	private boolean otroFrente;
	
	public RegistroPredioManaged(){
		
	}
	
	@PostConstruct
	public void inicio(){
		try{
			init();
			String RECORD_STATUS=(String)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "RECORD_STATUS");
			if(RECORD_STATUS!=null&&RECORD_STATUS.equals(Constante.RECORD_STATUS_CLONE)){
				copiaDj();
			}else if(RECORD_STATUS!=null&&RECORD_STATUS.equals(Constante.RECORD_STATUS_CLONE_FISCA)){//--Fiscalizacion				
				copiaFiscaDj();
				//setFisca(Boolean.TRUE);
			}else if(RECORD_STATUS!=null&&RECORD_STATUS.equals(Constante.RECORD_STATUS_CLONE_FISCA_ACEPTADA)){//--Fiscalizacion
				copiaDjFiscaAceptada();
				//setFisca(Boolean.TRUE);
			}else if(RECORD_STATUS!=null&&RECORD_STATUS.equals(Constante.RECORD_STATUS_UPDATE)){
				cargaDj();
			}else if(RECORD_STATUS!=null&&RECORD_STATUS.equals(Constante.RECORD_STATUS_UPDATE_FISCA)){//--Fiscalizacion
				cargaFiscaDj();
				//setFisca(Boolean.TRUE);
			}else if(RECORD_STATUS!=null&&RECORD_STATUS.equals(Constante.RECORD_STATUS_VIEW)){
				muestraDj();
			}else {
				transferente=new BuscarPersonaDTO();
				transferente.setPersonaId(Constante.RESULT_PENDING);
				
				setTipoPredio("U");
				setOutputtextTituloCondicionEspecial("CONDICION ESPECIAL Y SITUACION DEL PREDIO");
				setEsUrbano(true);
				setPredioHabitable("A");
				setCmbValueCondiEspePredio("Ninguna");
				setCmbValueCondicionPropiedad(getMsg("rp.condicion.propiedad.unico"));
				
				setPorcentaje(new BigDecimal(100.00));
				setCmbValueTipoAdquisicion("Compra - venta");
				setCmbValueUbicacionPredio("Predio Independiente");
				setUbicacionPredioId(2);
				setPoseeAreaComun(Boolean.FALSE);
				selectAreaComun(getCmbValueUbicacionPredio());
				 
				isCondiEspePredio=Boolean.FALSE;
				isHabitable=Boolean.TRUE;
				is100porciento=Boolean.TRUE;
				setEsConstruccion(Boolean.TRUE);
				
				records=new ArrayList<RpDjconstruccion>();
				recordsAllConstruccion=new ArrayList<RpDjconstruccion>();
				recordsOtrasInsta=new ArrayList<RpInstalacionDj>();
				recordsOtrosFrentes=new ArrayList<RpOtrosFrente>();
				recordsUsosDelPredio=new ArrayList<RpDjuso>();
				recordsDocAnexos=new ArrayList<RjDocuAnexo>();
				listRelacionados=new ArrayList<RelacionadosDTO>();
				rpDjdireccion=new RpDjdireccion();
			}
			
			Integer motivoDecla=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "MOTIVO_DECLARACION");
			if(motivoDecla!=null&&motivoDecla>0){
				setMotivoDeclaracion(motivoDecla);	
			}
			
			setUsuario(user.getUsuario());
			setFechaActual(DateUtil.getCurrentDate());
			setOtroFrente(false);
			
			/*** Control de Requerimientos */
			
				 currentItemValorInsp=(FindInspeccionHistorial) getSessionMap().get("currentItemRequerimiento");	
				
				 listarequer=ficalizacionBo.getDeclaracionesInspByPersona(rpDjpredial.getPersonaId(),rpDjpredial.getPredioId(),rpDjpredial.getAnnoDj());
						
			/*** Control de Requerimientos */
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public void cargaDj(){
		try{
			//inhabilita la anterior y crea uno nuevo
			//carga los datos
			Integer djId=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId");
			if(djId!=null&&djId>0){
				RpDjpredial djpredio=registroPrediosBo.getRpDjpredial(djId);
				loadDatosPredio();
				loadDatosConstruccion();
				loadDatosInstalaciones();
				loadDatosFrentes();
				loadDatosUsos();
				loadDatosAnexos();
				loadPersona(djpredio);
				loadUbicacion();
				loadTransferente();
				loadRelacionados();

			}
			
			this.ubicacionPredioId = rpDjpredial.getUbicacionPredioId();
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	//caltamirano:ini
	public void copiaFiscaDj(){
		try{
			Integer djIdAnterior=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId");
			Integer djIdFisca = guardarDetalle(djIdAnterior);
			if(djIdFisca!=null){
				registroPrediosBo.updateDjPredial(djIdFisca, djIdAnterior, Constante.FISCALIZADO_SI, Constante.FISCA_ACEPTADA_NO, Constante.FISCA_CERRADA_NO);
				//cchaucca 13/08/2012 :ini 
				getRpDjpredial().setFiscalizado(Constante.FISCALIZADO_SI);
				getRpDjpredial().setIdAnterior(String.valueOf(djIdAnterior));
				getRpDjpredial().setFiscaAceptada(Constante.FISCA_ACEPTADA_NO);
				getRpDjpredial().setFiscaCerrada(Constante.FISCA_CERRADA_NO);
				//cchaucca 13/08/2012 :fin
			}
		}catch(Exception ex){
			// TODO : Handle exception
			System.out.println("copiaFiscaDj "+ex);
			addFatalMessage(ex.getMessage());
		}
	}
	
	public void copiaDjFiscaAceptada(){//DESDE DISCALIZACION
		try{
			String ffp = (String)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "flagFiscaPrevio");
			if(ffp!=null){
				if(ffp.equals("1")){
					flagFiscaPrevio = true;
				}
			}
			Integer djIdAnterior=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId");
			Integer djIdFisca = guardarDetalle(djIdAnterior);
			if(djIdFisca!=null){
				registroPrediosBo.updateDjPredial(djIdFisca, djIdAnterior, Constante.FISCALIZADO_SI, Constante.FISCA_ACEPTADA_SI, Constante.FISCA_CERRADA_SI);
				//cchaucca 13/08/2012 :ini 
				getRpDjpredial().setFiscalizado(Constante.FISCALIZADO_SI);
				getRpDjpredial().setIdAnterior(String.valueOf(djIdAnterior));
				getRpDjpredial().setFiscaAceptada(Constante.FISCA_ACEPTADA_SI);
				getRpDjpredial().setFiscaCerrada(Constante.FISCA_CERRADA_SI);
				//cchaucca 13/08/2012 :fin
			}
		}catch(Exception ex){
			// TODO : Handle exception
			System.out.println("copiaFiscaDj "+ex);
			addFatalMessage(ex.getMessage());
		}
	}
	
	public void cargaFiscaDj(){
		try{
			//inhabilita la anterior y crea uno nuevo
			//carga los datos
			Integer djId=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId");
			if(djId!=null&&djId>0){
				RpDjpredial djpredio=registroPrediosBo.getRpDjpredial(djId);
				loadDatosPredio();
				loadDatosConstruccion();
				loadDatosInstalaciones();
				loadDatosFrentes();
				loadDatosUsos();
				loadDatosAnexos();
				loadPersona(djpredio);
				loadUbicacion();
				loadTransferente();
				loadRelacionados();
				registroPrediosBo.updateDjPredial(djId, null, Constante.FISCALIZADO_SI, Constante.FISCA_ACEPTADA_NO, Constante.FISCA_CERRADA_NO);
				//cchaucca 13/08/2012 :ini 
				getRpDjpredial().setFiscalizado(Constante.FISCALIZADO_SI);
				getRpDjpredial().setFiscaAceptada(Constante.FISCA_ACEPTADA_NO);
				getRpDjpredial().setFiscaCerrada(Constante.FISCA_CERRADA_NO);
				//cchaucca 13/08/2012 :fin
				
				//PARA FISCALIZACION
				inspeccionFisca = registroPrediosBo.getRpFiscaInspeccion(djId, djpredio.getPredioId(), djpredio.getPersonaId());
				if(inspeccionFisca != null && inspeccionFisca.getInspeccionId() != null){
					setFechaRequerimiento(inspeccionFisca.getFechaRequerimiento());
					setFechaInspeccion(inspeccionFisca.getFechaInspeccion());
					setValueComboBoxTipoInspeccion(inspeccionFisca.getTipoInspeccion());
					if(inspeccionFisca.getTipoAr().equals(Constante.ESTADO_ACTIVO))
						setTipoAR(true);
				}
				
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	//caltamirano:fin
	
	public void muestraDj(){
		try{
			//inhabilita la anterior y crea uno nuevo
			//carga los datos
			Integer djId=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId");
			if(djId!=null&&djId>0){
				RpDjpredial djpredio=registroPrediosBo.getRpDjpredial(djId);
				loadDatosPredio();
				loadDatosConstruccion();
				loadDatosInstalaciones();
				loadDatosFrentes();
				loadDatosUsos();
				loadDatosAnexos();
				loadPersona(djpredio);
				loadUbicacion();
				loadTransferente();
				loadRelacionados();
				
				//PARA FISCALIZACION
				if(isFisca()){
					inspeccionFisca = registroPrediosBo.getRpFiscaInspeccion(djId, djpredio.getPredioId(), djpredio.getPersonaId() );
					if(inspeccionFisca != null){					
						setFechaRequerimiento(inspeccionFisca.getFechaRequerimiento());
						setFechaInspeccion(inspeccionFisca.getFechaInspeccion());
						setValueComboBoxTipoInspeccion(inspeccionFisca.getTipoInspeccion());
						if(inspeccionFisca.getTipoAr() != null && inspeccionFisca.getTipoAr().equals(Constante.ESTADO_ACTIVO))
							setTipoAR(true);
					}
					
					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public void copiaDj(){
		try{
			//inhabilita la anterior y crea uno nuevo
			//carga los datos
			Integer djIdAnterior=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId");
			guardarDetalle(djIdAnterior);
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public void init(){
		try{
			//RpUbicacionPredio
			List<RpUbicacionPredio> lstRpUbicacionPredio=registroPrediosBo.getAllRpUbicacionPredio();
			Iterator<RpUbicacionPredio> it = lstRpUbicacionPredio.iterator();  
			lstUbicacionPredio = new ArrayList<SelectItem>();
			 
	        while (it.hasNext()){
	        	RpUbicacionPredio obj = it.next();  
	        	lstUbicacionPredio.add(new SelectItem(obj.getDescripcion(),String.valueOf(obj.getUbicacionPredioId())));
	        	mapRpUbicacionPredio.put(obj.getDescripcion(), obj.getUbicacionPredioId());
	        	mapIRpUbicacionPredio.put(obj.getUbicacionPredioId(),obj.getDescripcion());
	        }
		    /** 
		     * Controla la regla de captura de datos obligatorios en base a la Ubicacion del Predio
		     	Econ. Julio Cesar De La Rosa Lujan  
	        	ubicacion_predio_id	descripcion	descripcion_corta
		        1	TSC Terreno sin Construir	
		        2	PI Predio Independiente	
		        3	PE Predio en Edificio	
		        5	ED Edificio	
		        4	PQSC Predio en Quinta, Solar, callejon	
		        6   QU Quinta 
		        8	PINT Predio Interior
	        */
	        mapEstadoArea.put(1, new EstadoArea(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE));
	        mapEstadoArea.put(2, new EstadoArea(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE));
	        mapEstadoArea.put(3, new EstadoArea(Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE));
	        mapEstadoArea.put(5, new EstadoArea(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE));
	        mapEstadoArea.put(4, new EstadoArea(Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE));
	        mapEstadoArea.put(6, new EstadoArea(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE));
	        mapEstadoArea.put(8, new EstadoArea(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE));
	        
	        
	        
	        /**
	         * Controla la regla de visualizacion de datos en base al Tipo de tierra rustico
	         *  1	Tierras aptas para cultivo en limpio con riego
			 *	2	Tierras aptas para cultivo permanente con riego
			 *	3	Tierras aptas para cultivo para pastoreo con riego
			 *	4	Tierras eriazas
			 * 		tipo_tierra_id	categoria_rustico_id	altitud_id
			 * 			1			 1, 2, 3, 4				1, 2, 3, 4
			 * 			2				5, 6				1, 2, 3, 4
			 * 			3				1					1, 2, 3, 4
			 * 			4				1	
	         */
	        mapEstadoTipoTierra.put(1, new EstadoTipoTierra(new Integer[]{1,2,3,4},new Integer[]{1,2,3,4}));
	        //mapEstadoTipoTierra.put(2, new EstadoTipoTierra(new Integer[]{5,6},new Integer[]{1,2,3,4}));
	        //SE AGREGA LA CATEGORIA 4 PARA EL TIPO 2 POR TABLA DE VALORES UNITARIOS OFICIALES DE TERRENOS RUSTICOS 2013
	        mapEstadoTipoTierra.put(2, new EstadoTipoTierra(new Integer[]{4,5,6},new Integer[]{1,2,3,4}));
	      //SE AGREGA LA CATEGORIA 2 y 3 PARA EL TIPO 3 POR TABLA DE VALORES UNITARIOS OFICIALES DE TERRENOS RUSTICOS 2013
	        mapEstadoTipoTierra.put(3, new EstadoTipoTierra(new Integer[]{1,2,3},new Integer[]{1,2,3,4}));
	        mapEstadoTipoTierra.put(4, new EstadoTipoTierra(new Integer[]{1},new Integer[]{}));
	        mapEstadoTipoTierra.put(6, new EstadoTipoTierra(new Integer[]{8},new Integer[]{5}));//Se agregó un Arancel Promedio para la determinación de Granja Porcón-17.03.2015
	        
			//RpCondicionPropiedad
	        List<RpCondicionPropiedad> lstRpCondicionPropiedad=registroPrediosBo.getAllRpCondicionPropiedad();
			Iterator<RpCondicionPropiedad> it2 = lstRpCondicionPropiedad.iterator();  
			lstCondicionPropiedad = new ArrayList<SelectItem>();
			 
	        while (it2.hasNext()){
	        	RpCondicionPropiedad obj = it2.next();  
	        	lstCondicionPropiedad.add(new SelectItem(obj.getDescripcion(),String.valueOf(obj.getCondicionPropiedadId())));
	        	mapRpCondicionPropiedad.put(obj.getDescripcion(), obj.getCondicionPropiedadId());
	        	mapIRpCondicionPropiedad.put(obj.getCondicionPropiedadId(),obj.getDescripcion());
	        }
	        
			//RpTipoAdquisicion
	        List<RpTipoAdquisicion> lstRpTipoAdquisicion=registroPrediosBo.getAllRpTipoAdquisicion();
			Iterator<RpTipoAdquisicion> it3 = lstRpTipoAdquisicion.iterator();  
			lstTipoAdquisicion = new ArrayList<SelectItem>();
			 
	        while (it3.hasNext()){
	        	RpTipoAdquisicion obj = it3.next();  
	        	lstTipoAdquisicion.add(new SelectItem(obj.getDescripcion(),String.valueOf(obj.getTipoAdquisicionId())));
	        	mapRpTipoAdquisicion.put(obj.getDescripcion(), obj.getTipoAdquisicionId());
	        	mapIRpTipoAdquisicion.put(obj.getTipoAdquisicionId(),obj.getDescripcion());
	        }
	        
			//MpCondiEspePredio
	        List<MpCondiEspePredio> lstMpCondiEspePredio=registroPrediosBo.getAllMpCondiEspePredio();
			Iterator<MpCondiEspePredio> it4 = lstMpCondiEspePredio.iterator();  
			lstCondiEspePredio = new ArrayList<SelectItem>();
			 
	        while (it4.hasNext()){
	        	MpCondiEspePredio obj = it4.next();  
	        	lstCondiEspePredio.add(new SelectItem(obj.getDescripcion(),String.valueOf(obj.getCondEspePredioId())));
	        	mapMpCondiEspePredio.put(obj.getDescripcion(), obj.getCondEspePredioId());
	        	mapIMpCondiEspePredio.put(obj.getCondEspePredioId(),obj.getDescripcion());
	        }
	        
	        /**
	         * Para el caso de predios rusticos se usa RpTipoTierraRustico,  RpAltitud y RpCategoriaRustico
	         */
	        //RpTipoTierraRustico
	        List<RpTipoTierraRustico> lstRpTipoTierraRustico=registroPrediosBo.getAllRpTipoTierraRustico();
			Iterator<RpTipoTierraRustico> it5 = lstRpTipoTierraRustico.iterator();  
			lstTipoTierraRustico = new ArrayList<SelectItem>();
			 
	        while (it5.hasNext()){
	        	RpTipoTierraRustico obj = it5.next();  
	        	lstTipoTierraRustico.add(new SelectItem(obj.getDescripcion(),String.valueOf(obj.getTipoTierraId())));
	        	mapRpTipoTierraRustico.put(obj.getDescripcion(), obj.getTipoTierraId());
	        	mapIRpTipoTierraRustico.put(obj.getTipoTierraId(),obj.getDescripcion());
	        }
	        
	        List<RpAltitud> lstRpAltitud=registroPrediosBo.getAllRpAltitud();
			Iterator<RpAltitud> it6 = lstRpAltitud.iterator();
			lstAltitud = new ArrayList<SelectItem>();
			 
	        while (it6.hasNext()){
	        	RpAltitud obj = it6.next();
	        	lstAltitud.add(new SelectItem(obj.getDescripcion(),String.valueOf(obj.getAltitudId())));
	        	mapRpAltitud.put(obj.getDescripcion(), obj.getAltitudId());
	        	mapIRpAltitud.put(obj.getAltitudId(),obj.getDescripcion());
	        }
	    	
	    	//RpCategoriaRustico
	        List<RpCategoriaRustico> lstRpCategoriaRustico=registroPrediosBo.getAllRpCategoriaRustico();
			Iterator<RpCategoriaRustico> it7 = lstRpCategoriaRustico.iterator();  
			lstCategoriaRustico = new ArrayList<SelectItem>();
			 
	        while (it7.hasNext()){
	        	RpCategoriaRustico obj = it7.next();  
	        	lstCategoriaRustico.add(new SelectItem(obj.getDescripcion(),String.valueOf(obj.getCategoriaRusticoId())));
	        	mapRpCategoriaRustico.put(obj.getDescripcion(), obj.getCategoriaRusticoId());
	        	mapIRpCategoriaRustico.put(obj.getCategoriaRusticoId(),obj.getDescripcion());
	        }
	    	
	        //RpTipoUsoPredioRustico
	        List<Object> lstRpTipoUsoPredioRustico=registroPrediosBo.getAllRpTipoUsoPredioRustico();
			Iterator<Object> it8 = lstRpTipoUsoPredioRustico.iterator();  
			lstTipoUsoRustico= new ArrayList<SelectItem>();
			 
	        while (it8.hasNext()){
	        	RpTipoUsoPredioRustico obj = (RpTipoUsoPredioRustico)it8.next();  
	        	lstTipoUsoRustico.add(new SelectItem(obj.getDescripcion(),String.valueOf(obj.getTipoUsoRusticoId())));
	        	mapRpTipoUsoPredioRustico.put(obj.getDescripcion(), obj.getTipoUsoRusticoId());
	        	mapIRpTipoUsoPredioRustico.put(obj.getTipoUsoRusticoId(),obj.getDescripcion());
	        }
	        
	        List<RjMes> lstRjMes=registroPrediosBo.getAllRjMes();
			 Iterator<RjMes> itm = lstRjMes.iterator();
			 while (itm.hasNext()){
		     	RjMes obj = itm.next();  
		     	mapRjMes.put(obj.getMesId(),obj.getDescripcion().trim());
			 }
			 
			//Motivo decla
			List<RvMotivoDeclaracion> lstMd = new ArrayList<RvMotivoDeclaracion>();
			lstMd = vehicularBo.getAllRvMotivoDeclaracion();
			Iterator<RvMotivoDeclaracion> itMd = lstMd.iterator();
			while (itMd.hasNext()) {
				RvMotivoDeclaracion objMd = itMd.next();
				mapIRvMotivoDecla.put(objMd.getMotivoDeclaracionId(),objMd.getDescripcion());
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
			
			//PARA FISCALIZACION - INSPECCION
			fisca = getSessionManaged().isModuloFisca();
			requerimiento = Boolean.FALSE;
			inspeccionFisca = new RpFiscaInspeccion();
			
			listTipoInspeccion.add(new SelectItem("FIP", "FIP"));
			listTipoInspeccion.add(new SelectItem("AINR", "AINR"));
			valueComboBoxTipoInspeccion = "FIP";
			
			
			 
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	public void loadDatosPredio(){
		try{
			Integer PredioId=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextPredioId");
			Integer DjId=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId");
			if(PredioId!=null&&PredioId>0){
				if(DjId!=null&&DjId>0){
					mpPredio=registroPrediosBo.getMpPredio(PredioId);	
					rpDjpredial=registroPrediosBo.getRpDjpredial(DjId);
					if(mpPredio!=null&&rpDjpredial!=null){
						//setTipoPredio(mpPredio.getTipoPredio());
						setTipoPredio(rpDjpredial.getTipoPredio());
						selectTipoPredio(getTipoPredio());
						
						//setEsUrbano(mpPredio.getTipoPredio().equals(Constante.TIPO_PREDIO_URBANO)?true:false);
						setEsUrbano(rpDjpredial.getTipoPredio().equals(Constante.TIPO_PREDIO_URBANO)?true:false);
						
						if(rpDjpredial.getEsHabitable()!=null)
							setPredioHabitable(rpDjpredial.getEsHabitable());
						else
							setPredioHabitable("A");
						
						if(getPredioHabitable()!=null&&getPredioHabitable().equals("A"))//N
							isHabitable=Boolean.TRUE;
						else
							isHabitable=Boolean.FALSE;
						
						if(rpDjpredial.getCondEspePredioId()!=null)
							setCmbValueCondiEspePredio(mapIMpCondiEspePredio.get(rpDjpredial.getCondEspePredioId()));
						else
							setCmbValueCondiEspePredio("Ninguna");
						
						if(rpDjpredial.getCondicionPropiedadId()!=null)
							setCmbValueCondicionPropiedad(mapIRpCondicionPropiedad.get(rpDjpredial.getCondicionPropiedadId()));
						else
							setCmbValueCondicionPropiedad(getMsg("rp.condicion.propiedad.unico"));
						
						if(rpDjpredial.getTipoAdquisicionId()!=null)
							setCmbValueTipoAdquisicion(mapIRpTipoAdquisicion.get(rpDjpredial.getTipoAdquisicionId()));
						else
							setCmbValueTipoAdquisicion("Compra - venta");
						
						setPorcentaje(rpDjpredial.getPorcPropiedad());
						if(getPorcentaje().doubleValue()>=100)
							is100porciento=Boolean.TRUE;
						else
							is100porciento=Boolean.FALSE;
						
						if(!getCmbValueCondicionPropiedad().equalsIgnoreCase("Ninguna"))
							isCondiEspePredio=Boolean.FALSE;
						else
							isCondiEspePredio=Boolean.TRUE;
					
						if(rpDjpredial.getFechaAdquisicion()!=null)
							setFechaAdquisicion(rpDjpredial.getFechaAdquisicion());
						
						if(rpDjpredial.getTipoPredio().equalsIgnoreCase(Constante.TIPO_PREDIO_URBANO)){
							//registroPredioManaged.cmbValueUbicacionPredio
							if(rpDjpredial.getUbicacionPredioId()!=null){
								setCmbValueUbicacionPredio(mapIRpUbicacionPredio.get(rpDjpredial.getUbicacionPredioId()));
								setUbicacionPredioId(rpDjpredial.getUbicacionPredioId());
							}
							else{
								setCmbValueUbicacionPredio("Predio Independiente");
								setUbicacionPredioId(2);
								setPoseeAreaComun(Boolean.FALSE);
							}
							selectAreaComun(getCmbValueUbicacionPredio());
							//registroPredioManaged.areaTerreno
							if(rpDjpredial.getAreaTerreno()!=null)
								setAreaTerreno(String.valueOf(rpDjpredial.getAreaTerreno()));
							//registroPredioManaged.areaTerrenoComun
							if(rpDjpredial.getAreaTerrenoComun()!=null)
								setAreaTerrenoComun(String.valueOf(rpDjpredial.getAreaTerrenoComun()));
							//registroPredioManaged.frente
							if(rpDjpredial.getFrente()!=null)
								setFrente(String.valueOf(rpDjpredial.getFrente()));
						}else{
							//registroPredioManaged.areaTerrenoHas
							if(rpDjpredial.getAreaTerrenoHas()!=null)
								setAreaTerrenoHas(String.valueOf(rpDjpredial.getAreaTerrenoHas()));
							//registroPredioManaged.cmbValueTipoTierraRustico
							
							/**Se corrige que al carga de lista de predios rusticos posea un tipo de tierra diferente de nulo**/ 
							if(rpDjpredial.getTipoTierraId()!=null){
								setCmbValueTipoTierraRustico(mapIRpTipoTierraRustico.get(rpDjpredial.getTipoTierraId()));
								cargarListasPredioRustico(rpDjpredial.getTipoTierraId());
							}
							
							//registroPredioManaged.cmbValueAltitud
							if(rpDjpredial.getAltitudId()!=null)
								setCmbValueAltitud(mapIRpAltitud.get(rpDjpredial.getAltitudId()));
							//registroPredioManaged.cmbValueCategoriaRustico
							if(rpDjpredial.getCategoriaRusticoId()!=null)
								setCmbValueCategoriaRustico(mapIRpCategoriaRustico.get(rpDjpredial.getCategoriaRusticoId()));
							//registroPredioManaged.cmbValueTipoUsoRustico
							if(rpDjpredial.getTipoUsoRusticoId()!=null)
								setCmbValueTipoUsoRustico(mapIRpTipoUsoPredioRustico.get(rpDjpredial.getTipoUsoRusticoId()));
							
							//registroPredioManaged.areaTerrenoComunHas
							if(rpDjpredial.getAreaTerrenoComunHas()!=null)
								setAreaTerrenoComunHas(String.valueOf(rpDjpredial.getAreaTerrenoComunHas()));
						}
						setDireccionPredio(rpDjpredial.getDescDomicilio());
						setEsConstruccion(necesitaConstruccion());
						
						if(rpDjpredial.getNotariaId()!=null&&rpDjpredial.getNotariaId()>Constante.RESULT_PENDING){
							setSelectedNotaria(mapIGnNotaria.get(rpDjpredial.getNotariaId()));	
						}
						if(rpDjpredial.getGlosa() != null){
							setGlosa(rpDjpredial.getGlosa());
						}
						//secano
						if(rpDjpredial.getSecano() != null && rpDjpredial.getSecano().equals(Constante.ESTADO_ACTIVO)){
							setSecano(Boolean.TRUE);
						}
						//frenteOk
						if(rpDjpredial.getFrenteOk() != null && rpDjpredial.getFrenteOk().equals(Constante.ESTADO_ACTIVO)){
							setFrenteOk(Boolean.TRUE);
						}
						//Frente del area en comun
						if(rpDjpredial.getFrenteAreaComun() != null){
							setFrenteAreaComun(rpDjpredial.getFrenteAreaComun());
						}else{
							setFrenteAreaComun(new BigDecimal(0));
						}
						//Distancia de la via publica al predio
						if(rpDjpredial.getDistanciaAPredio() != null){
							setDistanciaAPredio(rpDjpredial.getDistanciaAPredio());
						}else{
							setDistanciaAPredio(new BigDecimal(0));
						}
						//Porcentaje de participacion de un Predio en quinta
						if(rpDjpredial.getPorcentajeParticipacion() != null){
							setPorcentajeParticipacion(rpDjpredial.getPorcentajeParticipacion());
						}else{
							setPorcentajeParticipacion(new BigDecimal(0));
						}
						//frente a la via publica
						if(rpDjpredial.getFrenteVia() != null && rpDjpredial.getFrenteVia().equals(Constante.ESTADO_ACTIVO)){
							setFrenteVia(Boolean.TRUE);
						}
					}
				}else{
					//si esta en nulo regresar al buscador
				}
			}else{
				//si esta en nulo regresar al buscador
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	public String imprimirUrbano(){
		return "imprimirUrbano";
	}
	
	public String imprimirRustico(){
		return "imprimirRustico";
	}
	
	public boolean necesitaConstruccion(){
		boolean necesita=false;
		try{
			if(getTipoPredio().equals(Constante.TIPO_PREDIO_URBANO)&&getCmbValueUbicacionPredio().equalsIgnoreCase("Terreno sin Construir")){
				//no necesita datos de construccion
				necesita=false;
			}else if(getTipoPredio().equals(Constante.TIPO_PREDIO_RUSTICO)){
				//no necesita datos de construccion
				necesita=true;
			}else{
				necesita=true;
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		return necesita;
	}
	
	public void selectRadioValue(ValueChangeEvent event)
	{
		 HtmlSelectOneRadio radio=(HtmlSelectOneRadio) event.getComponent(); 
		 selectTipoPredio(radio.getValue().toString());
		 loadDatosUsos();
	}
	
	private void selectTipoPredio(String tipoPredio){
		 if(tipoPredio.toString().equalsIgnoreCase("U")){
			 setEsUrbano(true);
			 setTipoPredio("U");
			 setOutputtextTituloCondicionEspecial("CONDICION ESPECIAL Y SITUACION DEL PREDIO");
			 setCmbValueUbicacionPredio("Predio Independiente");setUbicacionPredioId(2);
			 selectAreaComun(getCmbValueUbicacionPredio());
			 setEsConstruccion(Boolean.TRUE);
		 }
		 else{
			 setEsUrbano(false);
			 setTipoPredio("R");
		 	 setEsConstruccion(Boolean.TRUE);
			 setOutputtextTituloCondicionEspecial("CONDICION ESPECIAL");
			 isHabitable=Boolean.TRUE;
		 }
	}
	
	public void selectCondiEspePredio(ValueChangeEvent event)
	{
		 //isCondiEspePredio
		 HtmlComboBox combo=(HtmlComboBox) event.getComponent(); 
		 if(combo.getValue().toString().equalsIgnoreCase("Ninguna")){
			 isCondiEspePredio=Boolean.FALSE;
		 }
		 else{
			 isCondiEspePredio=Boolean.TRUE;
		 }
	}
	public void selectCondiPropiedad(ValueChangeEvent event)
	{
		 HtmlComboBox combo=(HtmlComboBox) event.getComponent(); 
		 //if(combo.getValue().toString().equalsIgnoreCase("Cond�mino Simple")){
		 if(combo.getValue().toString().equalsIgnoreCase(getMsg("rp.condicion.propiedad.defecto"))){
			 is100porciento=Boolean.FALSE;
			 getTxtPorcentaje().setValue("");
			 setPorcentaje(new BigDecimal(0.00));
		 }else{
			 is100porciento=Boolean.TRUE;
			 getTxtPorcentaje().setValue("100");
			 setPorcentaje(new BigDecimal(100.00));
		 }
	}
	
	public void selectDatosConstruccion(ValueChangeEvent event)
	{
		 HtmlComboBox combo=(HtmlComboBox) event.getComponent(); 
		 if(combo.getValue().toString().equalsIgnoreCase("Terreno sin Construir")){
			 setEsConstruccion(Boolean.FALSE);
		 }
		 else{
			 setEsConstruccion(Boolean.TRUE);
		 }
		 
		 //Predio Independiente /Terreno sin Construir
		 if(combo.getValue().toString().equalsIgnoreCase("Terreno sin Construir")||combo.getValue().toString().equalsIgnoreCase("Edificio")||combo.getValue().toString().equalsIgnoreCase("Predio Independiente")){
			 setPoseeAreaComun(Boolean.FALSE);
		 }
		 else{
			 setPoseeAreaComun(Boolean.TRUE);
		 }
		 
		 setUbicacionPredioId(mapRpUbicacionPredio.get(combo.getValue().toString().trim()));
		 
		 //PARA PREDIO EN QUINTA O INTERIOR
//		 if(combo.getValue().toString().equalsIgnoreCase("Predio en Quinta, Solar, callejón")||combo.getValue().toString().equalsIgnoreCase("Predio Interior")){
//			 setPredioQuintaOInterior(Boolean.TRUE);
//			 if(combo.getValue().toString().equalsIgnoreCase("Predio en Quinta, Solar, callejón")){
//				 setPredioEnQuinta(Boolean.TRUE);
//			 }else{
//				 setPredioEnQuinta(Boolean.FALSE);
//			 }
//		 }
//		 else{
//			 setPredioQuintaOInterior(Boolean.FALSE);
//		 }
	}
	
	public void selectTipoTierraRustico(ValueChangeEvent event)
	{
		try{
			 HtmlComboBox combo=(HtmlComboBox) event.getComponent(); 
			 String tipoTierraSelected=(String)combo.getValue();
			 if(tipoTierraSelected!=null&&tipoTierraSelected.trim().length()>0){
			 	 Integer tipoTierraId=mapRpTipoTierraRustico.get(tipoTierraSelected);
			 	 cargarListasPredioRustico(tipoTierraId);
			 }
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void cargarListasPredioRustico(Integer tipoTierraId){
		lstAltitudCmb=new ArrayList<SelectItem>();
		for(int i=0;i<lstAltitud.size();i++){
			SelectItem item=lstAltitud.get(i);
			Integer id=Integer.valueOf(item.getLabel().toString());
			if(mapEstadoTipoTierra.get(tipoTierraId).getAltitud().get(id)!=null&&mapEstadoTipoTierra.get(tipoTierraId).getAltitud().get(id).equals(Boolean.TRUE)){
				lstAltitudCmb.add(item);	
			}
		}
		
		lstCategoriaRusticoCmb=new ArrayList<SelectItem>();
		for(int i=0;i<lstCategoriaRustico.size();i++){
			SelectItem item=lstCategoriaRustico.get(i);
			Integer id=Integer.valueOf(item.getLabel().toString());
			if(mapEstadoTipoTierra.get(tipoTierraId).getCategoria().get(id)!=null&&mapEstadoTipoTierra.get(tipoTierraId).getCategoria().get(id).equals(Boolean.TRUE)){
				lstCategoriaRusticoCmb.add(item);
			}
		}
		
		setCmbValueAltitud("");
		setCmbValueCategoriaRustico("");
	}
	
	
	private void selectAreaComun(String ubicacionPredio){
		 if(ubicacionPredio.trim().equalsIgnoreCase("Terreno sin Construir")||ubicacionPredio.trim().equalsIgnoreCase("Edificio")||ubicacionPredio.trim().equalsIgnoreCase("Predio Independiente")){
			 setPoseeAreaComun(Boolean.FALSE);
		 }
		 else{
			 setPoseeAreaComun(Boolean.TRUE);
		 }
		 
		 setUbicacionPredioId(mapRpUbicacionPredio.get(ubicacionPredio.trim()));
	}
	
	public void selectHabitableNo(ValueChangeEvent event)
	{
		 HtmlSelectOneRadio radio=(HtmlSelectOneRadio) event.getComponent(); 
		 if(radio.getValue().toString().equalsIgnoreCase("A")){
			 isHabitable=Boolean.TRUE;
		 }
		 else{
			 isHabitable=Boolean.FALSE;
		 }
	}
	
		
	public void cargarListaConstruccion(){
		/*
		int item=records.size()+1;
		rpDjconstruccion.setItem(item);
		records.add(rpDjconstruccion);
		*/
	}
	
	public void creardj(){
		try{
			if(guardarDj()){
				Integer PredioId=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextPredioId");
				Integer DjId=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId");
				if(PredioId!=null&&PredioId>0){
					if(DjId!=null&&DjId>0){
						rpDjpredial=registroPrediosBo.getRpDjpredial(DjId);
						rpDjpredial.setEstado(Constante.ESTADO_ACTIVO);
						rpDjpredial.setFlagDjAnno(Constante.FLAG_DJ_ANIO_ACTIVO);
						//rpDjpredial.setTerminal(Constante.TERMINAL);
						//rpDjpredial.setTerminalRegistro(Constante.TERMINAL);
						//rpDjpredial.setFechaActualizacion(DateUtil.getCurrentDate());
						//rpDjpredial.setFechaRegistro(DateUtil.getCurrentDate());
						//rpDjpredial.setUsuarioId(Constante.USUARIO_ID);
						int res=registroPrediosBo.actualizaRpDjpredial(rpDjpredial); //Hasta aca hay 2 flags en estado activo
						if(res>0){
							MpPredio mpPredio=registroPrediosBo.getMpPredio(PredioId);
							if(mpPredio!=null&&mpPredio.getEstado().equals(Constante.ESTADO_PENDIENTE)){
								mpPredio.setEstado(Constante.ESTADO_ACTIVO);
								registroPrediosBo.actualizaMpPredio(mpPredio);
							}
						}
						
						//caltamirano:ini
						if(getSessionManaged().isModuloFisca()){
							if(fiscaAceptado.equals(Constante.FISCA_ACEPTADA_CONTRIB_NO)){
								registroPrediosBo.updateDjPredial(DjId, null, Constante.FISCALIZADO_SI, Constante.FISCA_ACEPTADA_NO, Constante.FISCA_CERRADA_NO);
								//cchaucca 13/08/2012 :ini 
								getRpDjpredial().setFiscalizado(Constante.FISCALIZADO_SI);
								getRpDjpredial().setFiscaAceptada(Constante.FISCA_ACEPTADA_NO);
								getRpDjpredial().setFiscaCerrada(Constante.FISCA_CERRADA_NO);
								//cchaucca 13/08/2012 :fin
							}else if(fiscaAceptado.equals(Constante.FISCA_ACEPTADA_CONTRIB_SI)){
								registroPrediosBo.updateDjPredial(DjId, null, Constante.FISCALIZADO_SI, Constante.FISCA_ACEPTADA_SI, Constante.FISCA_CERRADA_SI);
								//cchaucca 13/08/2012 :ini 
								getRpDjpredial().setFiscalizado(Constante.FISCALIZADO_SI);
								getRpDjpredial().setFiscaAceptada(Constante.FISCA_ACEPTADA_SI);
								getRpDjpredial().setFiscaCerrada(Constante.FISCA_CERRADA_SI);
								//cchaucca 13/08/2012 :fin
							}else if(fiscaAceptado.equals(Constante.FISCA_NOACEPTADA_CERRADA)){
								registroPrediosBo.updateDjPredial(DjId, null, Constante.FISCALIZADO_SI, Constante.FISCA_ACEPTADA_NO, Constante.FISCA_CERRADA_SI);
								//cchaucca 13/08/2012 :ini 
								getRpDjpredial().setFiscalizado(Constante.FISCALIZADO_SI);
								getRpDjpredial().setFiscaAceptada(Constante.FISCA_ACEPTADA_NO);
								getRpDjpredial().setFiscaCerrada(Constante.FISCA_CERRADA_SI);
								//cchaucca 13/08/2012 :fin
							}else{
							}
							//PARA INSPECCION FISCALIZACION
							if(isRequerimiento()){
								
								if(inspeccionFisca.getNroRequerimiento() == null || inspeccionFisca.getNroRequerimiento()=="")
									inspeccionFisca.setNroRequerimiento("RegistradoSinNroRequerimiento");	
								if(fechaRequerimiento == null){
									inspeccionFisca.setFechaRequerimiento(DateUtil.getCurrentDate());
								}else{
									inspeccionFisca.setFechaRequerimiento(new Timestamp(fechaRequerimiento.getTime()));
								}
								if(inspeccionFisca.getInspector() == null || inspeccionFisca.getInspector() == "")	
									inspeccionFisca.setInspector("RegistradoSinInspector");		   
								if(inspeccionFisca.getNroInspeccion() == null || inspeccionFisca.getNroInspeccion() == "") 
									inspeccionFisca.setNroInspeccion("RegistradoSinNroDocumento");
								if(fechaInspeccion ==null){		
									inspeccionFisca.setFechaInspeccion(DateUtil.getCurrentDate());
								}else{
									inspeccionFisca.setFechaInspeccion(new Timestamp(fechaInspeccion.getTime()));
								}								
									inspeccionFisca.setDjId(getRpDjpredial().getDjId());
									inspeccionFisca.setPersonaId(getRpDjpredial().getPersonaId());
									inspeccionFisca.setPredioId(getRpDjpredial().getPredioId());
									if(isTipoAR()) inspeccionFisca.setTipoAr(Constante.ESTADO_ACTIVO);
									inspeccionFisca.setTipoInspeccion(valueComboBoxTipoInspeccion);
									inspeccionFisca.setEstado(Constante.ESTADO_ACTIVO);
									
									inspeccionFisca.setGlosa(inspeccionFisca.getGlosa().concat(". ").concat(getSessionManaged().getUsuarioLogIn().getNombreUsuario()));
									
									registroPrediosBo.registrarRpFiscaInspeccion(getInspeccionFisca());	
							}
							
						}
						//caltamirano:fin
						
						int result=registroPrediosBo.actualizaEstadoRpDjpredial(getRpDjpredial(),Constante.FLAG_DJ_ANIO_INACTIVO);//aca actualiza los estados anteriores a 0
						
						/**
						 * No se realiza el descargo automatico
						if(rpDjpredial.getMotivoDeclaracionId().equals(Constante.MOTIVO_DECLARACION_INSCRIPCION)){
							generaDescargoAutomatico();	
						}*/
						
					}
					salir();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public void guardar(){
		guardarDj();
	}

	public void inscripcionPredio(){
		try{
			MpPredio predio=new MpPredio();
			predio.setPredioId(Constante.RESULT_PENDING);
			predio.setTipoPredio(Constante.TIPO_PREDIO_URBANO);
			predio.setEstado(Constante.ESTADO_ACTIVO);
			Integer NextPredioId=registroPrediosBo.guardarMpPredio(predio);
			if(NextPredioId!=null&&NextPredioId>Constante.RESULT_PENDING){
				RpDjpredial dj=new RpDjpredial();
				dj.setDjId(Constante.RESULT_PENDING);
				dj.setPredioId(NextPredioId);
				dj.setPersonaId(getSessionManaged().getContribuyente().getPersonaId());
				dj.setAnnoDj(DateUtil.getAnioActual());
				dj.setTipoPredio(predio.getTipoPredio());
				dj.setFechaDeclaracion(DateUtil.getCurrentDate());
				dj.setFechaRegistro(DateUtil.getCurrentDate());
				dj.setFechaActualizacion(DateUtil.getCurrentDate());
				dj.setEstado(Constante.ESTADO_PENDIENTE);
				dj.setFlagDjAnno(Constante.FLAG_DJ_ANIO_INACTIVO);
				dj.setCodigoPredio(predio.getCodigoPredio());
				dj.setMotivoDeclaracionId(Constante.MOTIVO_DECLARACION_PRIMERA_INSCRIPCION);//--
				dj.setPorcPropiedad(new BigDecimal(100));
				dj.setCondicionPropiedadId(Constante.CONDICION_PROPIEDAD_PROPIETARIO_UNICO);
				//--Por defecto la inscripcion normal es sin fiscalizacion
				dj.setFiscalizado(Constante.FISCALIZADO_NO);
				dj.setFiscaAceptada(Constante.FISCA_ACEPTADA_NO);
				dj.setFiscaCerrada(Constante.FISCA_CERRADA_NO);
				//--
				dj.setEsDescargo(Constante.ESTADO_INACTIVO);
				Integer NextDjId=registroPrediosBo.guardarRpDjpredial(dj);
				if(NextDjId!=null&&NextDjId>Constante.RESULT_PENDING){
					if(getSessionManaged().isModuloFisca()){
						registroPrediosBo.updateDjPredial(NextDjId, null, Constante.FISCALIZADO_SI,Constante.FISCA_ACEPTADA_NO,Constante.FISCA_CERRADA_NO);
						dj.setFiscalizado(Constante.FISCALIZADO_SI);
						dj.setFiscaAceptada(Constante.FISCA_ACEPTADA_NO);
						dj.setFiscaCerrada(Constante.FISCA_CERRADA_NO);
					}
					String esFiscalizacion=(String)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "ES_FISCALIZACION");
					if(esFiscalizacion!=null&&esFiscalizacion.equals("S")){
						registroPrediosBo.updateDjPredial(NextDjId, null, Constante.FISCALIZADO_SI,Constante.FISCA_ACEPTADA_SI,Constante.FISCA_CERRADA_SI);
						dj.setFiscalizado(Constante.FISCALIZADO_SI);
						dj.setFiscaAceptada(Constante.FISCA_ACEPTADA_SI);
						dj.setFiscaCerrada(Constante.FISCA_CERRADA_SI);
					}
					
					FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "RECORD_STATUS",Constante.RECORD_STATUS_NEW);
					FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "NextPredioId",NextPredioId);
					FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId",NextDjId);
					FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "MOTIVO_DECLARACION",Constante.MOTIVO_DECLARACION_PRIMERA_INSCRIPCION);
				}
				setRpDjpredial(dj);
				setMpPredio(predio);
			}else{
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public boolean guardarDj(){
		Boolean result=Boolean.FALSE;
		try{
			Integer djId=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId");
			Integer predioId=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextPredioId");
			if(djId!=null){
				if(getSessionManaged().isModuloFisca()){
				 if(verificarDjInspeccion()){ /**¿Tiene un requerimiento de inspección?**/	
				  if(DateUtil.esFechaValida(getFechaAdquisicion())){
					if(registroPrediosBo.existDjDireccion(djId)>0){
						//La validacion se retiro debido a los diversos casos que se presentan
						//if(validaCondicion()){
							//La validacion se retiro debido a los diversos casos que se presentan
							//if(ValidaAreaComun(djId)){
								if(ValidaConstruccion(djId)){
									if(validaUsosPredio()){
										//Datos de construccion
										getRpDjpredial().setDjId(djId);
										getMpPredio().setPredioId(predioId);
										//Codigo de predio
										getRpDjpredial().setCodigoPredio(getMpPredio().getCodigoPredio());
										//registroPredioManaged.tipoPredio
										getMpPredio().setTipoPredio(getTipoPredio());
										getRpDjpredial().setTipoPredio(getTipoPredio());
										//descripcion de domicilio
										getRpDjpredial().setDescDomicilio(getDireccionPredio());
										//registroPredioManaged.cmbValueCondicionPropiedad
										getRpDjpredial().setCondicionPropiedadId(mapRpCondicionPropiedad.get(getCmbValueCondicionPropiedad()));
										//registroPredioManaged.porcentaje
										getRpDjpredial().setPorcPropiedad(BigDecimal.valueOf(getNumber(getTxtPorcentaje().getValue().toString())));
										//registroPredioManaged.cmbValueTipoAdquisicion
										getRpDjpredial().setTipoAdquisicionId(mapRpTipoAdquisicion.get(getCmbValueTipoAdquisicion()));
										//registroPredioManaged.fechaAdquisicion
										getRpDjpredial().setFechaAdquisicion(DateUtil.dateToSqlDate(getFechaAdquisicion()));
										getMpPredio().setFechaAdquisicion(DateUtil.dateToSqlDate(getFechaAdquisicion()));
										//registroPredioManaged.predioHabitable
										getRpDjpredial().setEsHabitable(predioHabitable);
										//registroPredioManaged.cmbValueCondiEspePredio
										getRpDjpredial().setCondEspePredioId(mapMpCondiEspePredio.get(getCmbValueCondiEspePredio()));
										getRpDjpredial().setFechaActualizacion(DateUtil.getCurrentDate());
										if(getMpPredio().getTipoPredio().equalsIgnoreCase(Constante.TIPO_PREDIO_URBANO)){
											//registroPredioManaged.cmbValueUbicacionPredio                   
											getRpDjpredial().setUbicacionPredioId(mapRpUbicacionPredio.get(getCmbValueUbicacionPredio()));
											selectAreaComun(getCmbValueUbicacionPredio());
											//registroPredioManaged.areaTerreno
											getMpPredio().setAreaTerreno(BigDecimal.valueOf(getNumber(getAreaTerreno())));
											getRpDjpredial().setAreaTerreno(BigDecimal.valueOf(getNumber(getAreaTerreno())));
											getMpPredio().setAreaTerrenoHas(null);
											getRpDjpredial().setAreaTerrenoHas(null);
											//registroPredioManaged.areaTerrenoComun
											if(getAreaTerrenoComun()!=null){
												getMpPredio().setAreaTerrenoComun(BigDecimal.valueOf(getNumber(getAreaTerrenoComun())));
												getRpDjpredial().setAreaTerrenoComun(BigDecimal.valueOf(getNumber(getAreaTerrenoComun())));
											}else{
												getMpPredio().setAreaTerrenoComun(BigDecimal.valueOf(0));
												getRpDjpredial().setAreaTerrenoComun(BigDecimal.valueOf(0));
											}
											//registroPredioManaged.frente
											getMpPredio().setFrente(BigDecimal.valueOf(getNumber(getFrente())));
											getRpDjpredial().setFrente(BigDecimal.valueOf(getNumber(getFrente())));
											
											//FRENTE OK
											if(isFrenteOk()){
												getRpDjpredial().setFrenteOk(Constante.ESTADO_ACTIVO);
											}else{
												getRpDjpredial().setFrenteOk(Constante.ESTADO_INACTIVO);
											}
											
											//FRENTE del area comun
											if(getFrenteAreaComun() != null && getFrenteAreaComun().compareTo(new BigDecimal(0))>0){
												getRpDjpredial().setFrenteAreaComun(frenteAreaComun);
											}else{
												getRpDjpredial().setFrenteAreaComun(BigDecimal.ZERO);
											}
											
											//Distancia de la via publica al primer vertice del predio a calcular valor de terreno
											if(getDistanciaAPredio() != null && getDistanciaAPredio().compareTo(new BigDecimal(0))>0){
												getRpDjpredial().setDistanciaAPredio(distanciaAPredio);
											}else{
												getRpDjpredial().setDistanciaAPredio(BigDecimal.ZERO);
											}
											
											//Porcentaje de participacion
											if(getPorcentajeParticipacion() != null && getPorcentajeParticipacion().compareTo(new BigDecimal(0))>0){
												getRpDjpredial().setPorcentajeParticipacion(porcentajeParticipacion);
											}else{
												getRpDjpredial().setPorcentajeParticipacion(BigDecimal.ZERO);
											}
											
											//FRENTE a la via pública
											if(isFrenteVia()){
												getRpDjpredial().setFrenteVia(Constante.ESTADO_ACTIVO);
											}else{
												getRpDjpredial().setFrenteVia(Constante.ESTADO_INACTIVO);
											}
											
										}else{//Predio Rustico
											//registroPredioManaged.areaTerrenoHas
											getMpPredio().setAreaTerrenoHas(BigDecimal.valueOf(getNumber(getAreaTerrenoHas())));
											getRpDjpredial().setAreaTerrenoHas(BigDecimal.valueOf(getNumber(getAreaTerrenoHas())));
											getMpPredio().setAreaTerreno(null);
											getRpDjpredial().setAreaTerreno(null);
											//registroPredioManaged.cmbValueTipoTierraRustico
											getRpDjpredial().setTipoTierraId(mapRpTipoTierraRustico.get(getCmbValueTipoTierraRustico()));
											if(getRpDjpredial().getTipoTierraId()!=Constante.TIPO_TIERRA_RUSTICO_ERIAZA_ID){
												//registroPredioManaged.cmbValueAltitud
												getRpDjpredial().setAltitudId(mapRpAltitud.get(getCmbValueAltitud()));
											}else{
												//Por defecto se considera que la altitud es 1, solo con el proposito de evitar el nulo
												//Si es tipo de tierra es ERIAZA no importa la altitud del predio siempre es el mismo arancel
												getRpDjpredial().setAltitudId(1);
											}
											//registroPredioManaged.cmbValueCategoriaRustico
											getRpDjpredial().setCategoriaRusticoId(mapRpCategoriaRustico.get(getCmbValueCategoriaRustico()));
											//registroPredioManaged.cmbValueTipoUsoPredioRustico
											getRpDjpredial().setTipoUsoRusticoId(mapRpTipoUsoPredioRustico.get(getCmbValueTipoUsoRustico()));
											
											//registroPredioManaged.areaTerrenoComun
											if(getAreaTerrenoComunHas()!=null){
												getMpPredio().setAreaTerrenoComunHas(BigDecimal.valueOf(getNumber(getAreaTerrenoComunHas())));
												getRpDjpredial().setAreaTerrenoComunHas(BigDecimal.valueOf(getNumber(getAreaTerrenoComunHas())));
											}else{
												getMpPredio().setAreaTerrenoComunHas(BigDecimal.valueOf(0));
												getRpDjpredial().setAreaTerrenoComunHas(BigDecimal.valueOf(0));
											}
											//SECANO
												if(isSecano()){
													getRpDjpredial().setSecano(Constante.ESTADO_ACTIVO);
												}else{
													getRpDjpredial().setSecano(Constante.ESTADO_INACTIVO);
												}
										}
										getMpPredio().setEstado(Constante.ESTADO_ACTIVO);
										
										int count=registroPrediosBo.actualizaMpPredio(getMpPredio());	
										
										if(count>0){
											getRpDjpredial().setPersonaId(getSessionManaged().getContribuyente().getPersonaId());
											getRpDjpredial().setFechaDeclaracion(DateUtil.getCurrentDate());
											if(!esDjAnio(getRpDjpredial())){
												getRpDjpredial().setFlagDjAnno(Constante.FLAG_DJ_ANIO_INACTIVO);
												getRpDjpredial().setEstado(Constante.ESTADO_PENDIENTE);	
											}else{
												getRpDjpredial().setFlagDjAnno(Constante.FLAG_DJ_ANIO_ACTIVO);
												getRpDjpredial().setEstado(Constante.ESTADO_ACTIVO);
											}
											
											Integer motivoDeclaracion=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "MOTIVO_DECLARACION");
											if(motivoDeclaracion!=null&&motivoDeclaracion>0){
												getRpDjpredial().setMotivoDeclaracionId(motivoDeclaracion);	
											}
											
											//Con esto creamos otro dj con los mismos datos que el anteior
											//solo cambia su Id
											if(getSelectedNotaria()!=null&&getSelectedNotaria().trim().length()>0){
												Integer notariaId=mapGnNotaria.get(getSelectedNotaria());
												getRpDjpredial().setNotariaId(notariaId);	
											}
											
											if(getTransferente()!=null&&getTransferente().getPersonaId()>0){
												List<BuscarPersonaDTO> lstTransferentes=new LinkedList<BuscarPersonaDTO>();
												getTransferente().setPorcentaje(new BigDecimal(100));
												lstTransferentes.add(getTransferente());
												registroPrediosBo.registrarAdquirientes(lstTransferentes,djId,Constante.TIPO_TRANSFERENCIA_TRANSFERENTE);
											}
//											else{
//												registroPrediosBo.eliminaAdquirientes(djId);
//											}
											if(getGlosa() != null){
												getRpDjpredial().setGlosa(getGlosa());
											}
											registroPrediosBo.actualizaRpDjpredial(getRpDjpredial());	
										}
										loadPersona(getRpDjpredial());
										loadDatosUsos();
										
										result=Boolean.TRUE;
									}
								}else{
									WebMessages.messageError("No existe datos de construcción");	
								}
							//}else{
							//	WebMessages.messageError("Area comun no registrado, requerido para Predio en Edificio y Predio en Quinta, Solar, callejon");
							//}
						//}else{
							//WebMessages.messageError("El porcentaje de la propiedad es mayor al porcentaje de la propiedad original");
						//}
					}else{
						WebMessages.messageError("no existe Datos de dirección");
					}
				}else{
					WebMessages.messageError("La fecha de adquisición es superior a la fecha actual");
				}
			}/**¿Tiene un requerimiento de inspección?**/	
		   }else
		      {
			       if(DateUtil.esFechaValida(getFechaAdquisicion())){
						if(registroPrediosBo.existDjDireccion(djId)>0){
							//La validacion se retiro debido a los diversos casos que se presentan
							//if(validaCondicion()){
								//La validacion se retiro debido a los diversos casos que se presentan
								//if(ValidaAreaComun(djId)){
									if(ValidaConstruccion(djId)){
										if(validaUsosPredio()){
											//Datos de construccion
											getRpDjpredial().setDjId(djId);
											getMpPredio().setPredioId(predioId);
											//Codigo de predio
											getRpDjpredial().setCodigoPredio(getMpPredio().getCodigoPredio());
											//registroPredioManaged.tipoPredio
											getMpPredio().setTipoPredio(getTipoPredio());
											getRpDjpredial().setTipoPredio(getTipoPredio());
											//descripcion de domicilio
											getRpDjpredial().setDescDomicilio(getDireccionPredio());
											//registroPredioManaged.cmbValueCondicionPropiedad
											getRpDjpredial().setCondicionPropiedadId(mapRpCondicionPropiedad.get(getCmbValueCondicionPropiedad()));
											//registroPredioManaged.porcentaje
											getRpDjpredial().setPorcPropiedad(BigDecimal.valueOf(getNumber(getTxtPorcentaje().getValue().toString())));
											//registroPredioManaged.cmbValueTipoAdquisicion
											getRpDjpredial().setTipoAdquisicionId(mapRpTipoAdquisicion.get(getCmbValueTipoAdquisicion()));
											//registroPredioManaged.fechaAdquisicion
											getRpDjpredial().setFechaAdquisicion(DateUtil.dateToSqlDate(getFechaAdquisicion()));
											getMpPredio().setFechaAdquisicion(DateUtil.dateToSqlDate(getFechaAdquisicion()));
											//registroPredioManaged.predioHabitable
											getRpDjpredial().setEsHabitable(predioHabitable);
											//registroPredioManaged.cmbValueCondiEspePredio
											getRpDjpredial().setCondEspePredioId(mapMpCondiEspePredio.get(getCmbValueCondiEspePredio()));
											getRpDjpredial().setFechaActualizacion(DateUtil.getCurrentDate());
											if(getMpPredio().getTipoPredio().equalsIgnoreCase(Constante.TIPO_PREDIO_URBANO)){
												//registroPredioManaged.cmbValueUbicacionPredio                   
												getRpDjpredial().setUbicacionPredioId(mapRpUbicacionPredio.get(getCmbValueUbicacionPredio()));
												selectAreaComun(getCmbValueUbicacionPredio());
												//registroPredioManaged.areaTerreno
												getMpPredio().setAreaTerreno(BigDecimal.valueOf(getNumber(getAreaTerreno())));
												getRpDjpredial().setAreaTerreno(BigDecimal.valueOf(getNumber(getAreaTerreno())));
												getMpPredio().setAreaTerrenoHas(null);
												getRpDjpredial().setAreaTerrenoHas(null);
												//registroPredioManaged.areaTerrenoComun
												if(getAreaTerrenoComun()!=null){
													getMpPredio().setAreaTerrenoComun(BigDecimal.valueOf(getNumber(getAreaTerrenoComun())));
													getRpDjpredial().setAreaTerrenoComun(BigDecimal.valueOf(getNumber(getAreaTerrenoComun())));
												}else{
													getMpPredio().setAreaTerrenoComun(BigDecimal.valueOf(0));
													getRpDjpredial().setAreaTerrenoComun(BigDecimal.valueOf(0));
												}
												//registroPredioManaged.frente
												getMpPredio().setFrente(BigDecimal.valueOf(getNumber(getFrente())));
												getRpDjpredial().setFrente(BigDecimal.valueOf(getNumber(getFrente())));
												
												//FRENTE OK
												if(isFrenteOk()){
													getRpDjpredial().setFrenteOk(Constante.ESTADO_ACTIVO);
												}else{
													getRpDjpredial().setFrenteOk(Constante.ESTADO_INACTIVO);
												}
												
												//FRENTE del area comun
												if(getFrenteAreaComun() != null && getFrenteAreaComun().compareTo(new BigDecimal(0))>0){
													getRpDjpredial().setFrenteAreaComun(frenteAreaComun);
												}else{
													getRpDjpredial().setFrenteAreaComun(BigDecimal.ZERO);
												}
												
												//Distancia de la via publica al primer vertice del predio a calcular valor de terreno
												if(getDistanciaAPredio() != null && getDistanciaAPredio().compareTo(new BigDecimal(0))>0){
													getRpDjpredial().setDistanciaAPredio(distanciaAPredio);
												}else{
													getRpDjpredial().setDistanciaAPredio(BigDecimal.ZERO);
												}
												
												//Porcentaje de participacion
												if(getPorcentajeParticipacion() != null && getPorcentajeParticipacion().compareTo(new BigDecimal(0))>0){
													getRpDjpredial().setPorcentajeParticipacion(porcentajeParticipacion);
												}else{
													getRpDjpredial().setPorcentajeParticipacion(BigDecimal.ZERO);
												}
												
												//FRENTE a la via pública
												if(isFrenteVia()){
													getRpDjpredial().setFrenteVia(Constante.ESTADO_ACTIVO);
												}else{
													getRpDjpredial().setFrenteVia(Constante.ESTADO_INACTIVO);
												}
												
											}else{//Predio Rustico
												//registroPredioManaged.areaTerrenoHas
												getMpPredio().setAreaTerrenoHas(BigDecimal.valueOf(getNumber(getAreaTerrenoHas())));
												getRpDjpredial().setAreaTerrenoHas(BigDecimal.valueOf(getNumber(getAreaTerrenoHas())));
												getMpPredio().setAreaTerreno(null);
												getRpDjpredial().setAreaTerreno(null);
												//registroPredioManaged.cmbValueTipoTierraRustico
												getRpDjpredial().setTipoTierraId(mapRpTipoTierraRustico.get(getCmbValueTipoTierraRustico()));
												if(getRpDjpredial().getTipoTierraId()!=Constante.TIPO_TIERRA_RUSTICO_ERIAZA_ID){
													//registroPredioManaged.cmbValueAltitud
													getRpDjpredial().setAltitudId(mapRpAltitud.get(getCmbValueAltitud()));
												}else{
													//Por defecto se considera que la altitud es 1, solo con el proposito de evitar el nulo
													//Si es tipo de tierra es ERIAZA no importa la altitud del predio siempre es el mismo arancel
													getRpDjpredial().setAltitudId(1);
												}
												//registroPredioManaged.cmbValueCategoriaRustico
												getRpDjpredial().setCategoriaRusticoId(mapRpCategoriaRustico.get(getCmbValueCategoriaRustico()));
												//registroPredioManaged.cmbValueTipoUsoPredioRustico
												getRpDjpredial().setTipoUsoRusticoId(mapRpTipoUsoPredioRustico.get(getCmbValueTipoUsoRustico()));
												
												//registroPredioManaged.areaTerrenoComun
												if(getAreaTerrenoComunHas()!=null){
													getMpPredio().setAreaTerrenoComunHas(BigDecimal.valueOf(getNumber(getAreaTerrenoComunHas())));
													getRpDjpredial().setAreaTerrenoComunHas(BigDecimal.valueOf(getNumber(getAreaTerrenoComunHas())));
												}else{
													getMpPredio().setAreaTerrenoComunHas(BigDecimal.valueOf(0));
													getRpDjpredial().setAreaTerrenoComunHas(BigDecimal.valueOf(0));
												}
												//SECANO
													if(isSecano()){
														getRpDjpredial().setSecano(Constante.ESTADO_ACTIVO);
													}else{
														getRpDjpredial().setSecano(Constante.ESTADO_INACTIVO);
													}
											}
											getMpPredio().setEstado(Constante.ESTADO_ACTIVO);
											
											int count=registroPrediosBo.actualizaMpPredio(getMpPredio());	
											
											if(count>0){
												getRpDjpredial().setPersonaId(getSessionManaged().getContribuyente().getPersonaId());
												getRpDjpredial().setFechaDeclaracion(DateUtil.getCurrentDate());
												if(!esDjAnio(getRpDjpredial())){
													getRpDjpredial().setFlagDjAnno(Constante.FLAG_DJ_ANIO_INACTIVO);
													getRpDjpredial().setEstado(Constante.ESTADO_PENDIENTE);	
												}else{
													getRpDjpredial().setFlagDjAnno(Constante.FLAG_DJ_ANIO_ACTIVO);
													getRpDjpredial().setEstado(Constante.ESTADO_ACTIVO);
												}
												
												Integer motivoDeclaracion=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "MOTIVO_DECLARACION");
												if(motivoDeclaracion!=null&&motivoDeclaracion>0){
													getRpDjpredial().setMotivoDeclaracionId(motivoDeclaracion);	
												}
												
												//Con esto creamos otro dj con los mismos datos que el anteior
												//solo cambia su Id
												if(getSelectedNotaria()!=null&&getSelectedNotaria().trim().length()>0){
													Integer notariaId=mapGnNotaria.get(getSelectedNotaria());
													getRpDjpredial().setNotariaId(notariaId);	
												}
												
												if(getTransferente()!=null&&getTransferente().getPersonaId()>0){
													List<BuscarPersonaDTO> lstTransferentes=new LinkedList<BuscarPersonaDTO>();
													getTransferente().setPorcentaje(new BigDecimal(100));
													lstTransferentes.add(getTransferente());
													registroPrediosBo.registrarAdquirientes(lstTransferentes,djId,Constante.TIPO_TRANSFERENCIA_TRANSFERENTE);
												}
//												else{
//													registroPrediosBo.eliminaAdquirientes(djId);
//												}
												if(getGlosa() != null){
													getRpDjpredial().setGlosa(getGlosa());
												}
												registroPrediosBo.actualizaRpDjpredial(getRpDjpredial());	
											}
											loadPersona(getRpDjpredial());
											loadDatosUsos();
											
											result=Boolean.TRUE;
										}
									}else{
										WebMessages.messageError("No existe datos de construcción");	
									}
								//}else{
								//	WebMessages.messageError("Area comun no registrado, requerido para Predio en Edificio y Predio en Quinta, Solar, callejon");
								//}
							//}else{
								//WebMessages.messageError("El porcentaje de la propiedad es mayor al porcentaje de la propiedad original");
							//}
						}else{
							WebMessages.messageError("no existe Datos de dirección");
						}
					}else{
						WebMessages.messageError("La fecha de adquisición es superior a la fecha actual");
					}
				
		   }
		  }		
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		return result;
	}
	
	/*
	 * La validacion se retiro debido a los diversos casos que se presentan en produccion
	public Boolean validaCondicion(){
		BigDecimal porcentajePropiedad=BigDecimal.valueOf(getNumber(getTxtPorcentaje().getValue().toString()));
		if(getRpDjpredial().getPorcPropiedad()!=null&&getRpDjpredial().getPorcPropiedad().compareTo(porcentajePropiedad)>=0){
			return Boolean.TRUE;
		}else{
			return Boolean.FALSE;
		}
	}
	*/
	
	public void limpiarTransferente(){
		transferente=new BuscarPersonaDTO();
		transferente.setPersonaId(Constante.RESULT_PENDING);
	}
	
	public boolean validaUsosPredio(){
		boolean result=false;
		if(getTipoPredio().equals(Constante.TIPO_PREDIO_URBANO)&&(!getCmbValueUbicacionPredio().equalsIgnoreCase("Terreno sin Construir"))){
			return validaNivelUsos();
		}else if(getTipoPredio().equals(Constante.TIPO_PREDIO_URBANO)&&getCmbValueUbicacionPredio().equalsIgnoreCase("Terreno sin Construir")){
			return validaTerrenoSinConstruir();
		}
		else if(getTipoPredio().equals(Constante.TIPO_PREDIO_RUSTICO)){
			//No es necesario el registro de los usos por nivel de construcción
			//return validaNivelUsos();
			return true;
		}
		return result;
	}
	
	public boolean validaTerrenoSinConstruir(){
		boolean valido=true;
		try{
			Integer NextDjId=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId");
			Integer djArbitrioId=registroPrediosBo.getDjArbitrioId(NextDjId);
			if(djArbitrioId!=null&&djArbitrioId>0){
				if(getAreaTerreno()!=null&&Util.toDouble(getAreaTerreno())>0){
					Double areaTerreno=Util.toDouble(getAreaTerreno());
					BigDecimal areaUsada=registroPrediosBo.getAreaUsada(djArbitrioId,Constante.RESULT_PENDING);
			       	areaUsada=(areaUsada==null)?new BigDecimal(0):areaUsada;
			       	if(areaTerreno.doubleValue()>areaUsada.doubleValue()){
			       		valido=false;
			       		double areasinuso=areaTerreno.doubleValue()-areaUsada.doubleValue();
			       		WebMessages.messageError("Area de "+areasinuso+" (m2) sin uso registrado");
			       	}else if(areaTerreno.doubleValue()<areaUsada.doubleValue()){
			       		valido=false;
			       		WebMessages.messageError("Area de terreno "+areaTerreno+" (m2) menor a las areas de uso registrado");
			       	}	
				}
		    }else{
				valido=false;
        		WebMessages.messageError("No se han registrado usos del predio");
			}
		}catch(Exception e){
			WebMessages.messageFatal(e);
			e.printStackTrace();
		}
		return valido;
	}
	public boolean validaTerrenoUsos(){
		boolean valido=true;
		try{
			Integer NextDjId=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId");
			Integer djArbitrioId=registroPrediosBo.getDjArbitrioId(NextDjId);
			if(djArbitrioId!=null&&djArbitrioId>0){
				BigDecimal areaTerreno=Util.toBigDecimal(getAreaTerreno());
		       	BigDecimal areaUsada=registroPrediosBo.getAreaUsada(djArbitrioId,Constante.RESULT_PENDING);
		       	areaUsada=(areaUsada==null)?new BigDecimal(0):areaUsada;
		       	if(areaTerreno.doubleValue()>areaUsada.doubleValue()){
		       		valido=false;
		       		double areasinuso=areaTerreno.doubleValue()-areaUsada.doubleValue();
		       		WebMessages.messageError("Area de "+areasinuso+" (Has) sin uso registrado");
		       	}
		    }else{
				valido=false;
        		WebMessages.messageError("No se han registrado usos del predio");
			}
		}catch(Exception e){
			WebMessages.messageFatal(e);
			e.printStackTrace();
		}
		return valido;
	}
	
	public boolean validaNivelUsos(){
		boolean valido=true;
		try{
			Iterator<RpDjconstruccion> it = records.iterator();  
			
			Integer NextDjId=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId");
			Integer djArbitrioId=registroPrediosBo.getDjArbitrioId(NextDjId);
			if(djArbitrioId!=null&&djArbitrioId>0){
		        while (it.hasNext()){
		        	RpDjconstruccion obj = it.next();
		        	BigDecimal areaConstruccion=obj.getAreaConstruccion();
		        	BigDecimal areaUsada=registroPrediosBo.getAreaUsada(djArbitrioId,obj.getConstruccionId());
		        	areaUsada=(areaUsada==null)?new BigDecimal(0):areaUsada;
		        	if(areaConstruccion.doubleValue()>areaUsada.doubleValue()){
		        		valido=false;
		        		double areasinuso=areaConstruccion.doubleValue()-areaUsada.doubleValue();
		        		WebMessages.messageError("Area de "+areasinuso+" (m2) de Nivel "+obj.getDentiponivel()+" Nro "+obj.getNroNivel()+" sin uso registrado");
		        	}
		        }
			}else{
				valido=false;
        		WebMessages.messageError("No se han registrado usos del predio");
			}
		}catch(Exception e){
			WebMessages.messageFatal(e);
			e.printStackTrace();
		}
		return valido;
	}
	public boolean esDjAnio(RpDjpredial rpDjPredial){
		try{
			String flagDjAnno=registroPrediosBo.getFlagDjAnno(rpDjPredial.getDjId(), rpDjPredial.getAnnoDj(),rpDjPredial.getPredioId());
			if(flagDjAnno!=null&&flagDjAnno.equals(Constante.FLAG_DJ_ANIO_ACTIVO))
				return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	public Integer guardarDetalle(Integer DjIdAnt){
		Integer DjActualizaId=0;
		try{
			if(DjIdAnt!=null&&DjIdAnt>0){
				Integer motivoDeclaracion=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "MOTIVO_DECLARACION");
				RpDjpredial djpredio = duplicarPredioTemp(DjIdAnt,Constante.RESULT_PENDING,
						getSessionManaged().getUsuarioLogIn().getUsuarioId(),
						getSessionManaged().getTerminalLogIn(),Boolean.TRUE,motivoDeclaracion);
				if(djpredio!=null){
					DjActualizaId = djpredio.getDjId();
				}
				//--
				FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId",DjActualizaId);
				//--
				loadDatosPredio();	
				loadDatosConstruccion();
				loadDatosInstalaciones();
				loadDatosFrentes();
				loadDatosUsos();
				loadDatosAnexos();
				loadPersona(djpredio);
				loadUbicacion();
				loadTransferente();
				loadRelacionados();
				
					
			}else{
				//selecciones
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		return DjActualizaId;
	}

	public RpDjpredial duplicarPredioTemp(Integer DjIdAnt,Integer DjId ,int userId, String terminal,Boolean esPropietario,Integer motivoDeclaracion){
		RpDjpredial djpredio = null;
		Integer DjActualizaId=0;
		try{
			if(DjIdAnt!=null&&DjIdAnt>0){
				//satc.dbo.rp_djpredial
				//djpredio=getService().getRpDjpredial(DjIdAnt);
				djpredio=registroPrediosBo.getRpDjpredial(DjIdAnt);
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
				MpPersona propietario=personaBo.getMpPersona(djpredio.getPersonaId());
				//Obtenemos el djArbitrioId Anterior a copiar
				Integer djArbitrioIdAnterior=registroPrediosBo.getDjArbitrioId(djpredio.getDjId());
				ArrayList<RpDjuso> listaUsosDelPredio=new ArrayList<RpDjuso>();
				if(djArbitrioIdAnterior!=null&&djArbitrioIdAnterior>0)
					listaUsosDelPredio=registroPrediosBo.getAllRpDjuso(djArbitrioIdAnterior);
				ArrayList<RjDocuAnexo> listaDocAnexos=registroPrediosBo.getAllRjDocuAnexo(djpredio.getDjId());
				
				//satc.dbo.rp_djpredial
				djpredio.setDjId(DjId);
				djpredio.setIdAnterior(String.valueOf(DjIdAnt));
				djpredio.setPersonaId(esPropietario.equals(Boolean.TRUE)?getSessionManaged().getContribuyente().getPersonaId():djpredio.getPersonaId());
				djpredio.setUsuarioId(userId);
				djpredio.setTerminal(terminal);
				djpredio.setFechaActualizacion(DateUtil.getCurrentDate());
				djpredio.setTerminalRegistro(terminal);
				djpredio.setFlagDjAnno(Constante.FLAG_DJ_ANIO_INACTIVO);
				//cc: 23/10/2012
				//djpredio.setEstado(Constante.ESTADO_PENDIENTE_REGISTRO);//TEMPORAL NO ES DJ AUN
				djpredio.setEstado(Constante.ESTADO_PENDIENTE);//TEMPORAL NO ES DJ AUN
				
				djpredio.setMotivoDeclaracionId(Constante.MOTIVO_DECLARACION_ACTUALIZA);
				//--
				djpredio.setFiscalizado(Constante.FISCALIZADO_NO);
				djpredio.setFiscaAceptada(Constante.FISCA_ACEPTADA_NO);
				djpredio.setFiscaCerrada(Constante.FISCA_CERRADA_NO);
				//--
				djpredio.setEsDescargo(Constante.ESTADO_INACTIVO);
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
							if (instalacion.getValorObra()==null&&instalacion.getValorUnitarioDepreciado()==null){
								BigDecimal valorInstalacion=instalacion.getAreaTerreno().multiply(tipoObraPeriodo.getValorUnitario());
								if(valorInstalacion!=null){
									instalacion.setValorInstalacion(valorInstalacion);	
								}
							}else{ /** De acuerdo al Informe 053-009-00001961, de fecha 04 Abril del 2017, se realizará el cálculo incluyendo depreciación:  (Ini.)*/
								
								DtFactorOfic factorOficializacion=registroPrediosBo.getFactorOficializacion(djpredio.getAnnoDj());
								BigDecimal valorUnitarioOficializado=tipoObraPeriodo.getValorUnitario().multiply(factorOficializacion.getFactor());
								instalacion.setValorUnitarioOficializado(valorUnitarioOficializado);
								 
								int antiguedad = djpredio.getAnnoDj() - instalacion.getAnnoInstalacion();
								BigDecimal porcentajeDep= BigDecimal.valueOf(100.00);
								BigDecimal valorDepreciacion=registroPrediosBo.obtenerPorcentajeDepreciacion(instalacion.getClasiDepreciacionId(), djpredio.getAnnoDj(), instalacion.getMatPredominanteId(), instalacion.getConservacionId(), antiguedad);
								BigDecimal valorUnitarioDepreciacion=valorUnitarioOficializado.subtract(valorUnitarioOficializado.multiply((valorDepreciacion.divide(porcentajeDep))));
								instalacion.setValorUnitarioDepreciado(valorUnitarioDepreciacion);
								instalacion.setValorObra(valorUnitarioDepreciacion.multiply(instalacion.getAreaTerreno()));
												
								BigDecimal valorUnitario=instalacion.getAreaTerreno().multiply(tipoObraPeriodo.getValorUnitario());
								BigDecimal valorInstalacion=valorUnitario.subtract(valorUnitario.multiply((valorDepreciacion.divide(porcentajeDep))));
								
								if(valorInstalacion!=null){
									instalacion.setValorInstalacion(valorInstalacion);	
								}
								
							}     /** De acuerdo al Informe 053-009-00001961, de fecha 04 Abril del 2017, se realizará el cálculo incluyendo depreciación:  (Fin)*/
					}else {
						RpTipoObra tipoObra=registroPrediosBo.getRpTipoObra(instalacion.getTipoObraId());
						if(tipoObra!=null&&tipoObra.getValorUnitario()!=null&&tipoObra.getValorUnitario().doubleValue()>0){
							if (instalacion.getValorObra()==null&&instalacion.getValorUnitarioDepreciado()==null){
								BigDecimal valorInstalacion=instalacion.getAreaTerreno().multiply(tipoObra.getValorUnitario());
								if(valorInstalacion!=null){
									instalacion.setValorInstalacion(valorInstalacion);	
								}
							}else{ /** De acuerdo al Informe 053-009-00001961, de fecha 04 Abril del 2017, se realizará el cálculo incluyendo depreciación:  (Ini.)*/
								
								DtFactorOfic factorOficializacion=registroPrediosBo.getFactorOficializacion(djpredio.getAnnoDj());
								BigDecimal valorUnitarioOficializado=tipoObraPeriodo.getValorUnitario().multiply(factorOficializacion.getFactor());
								instalacion.setValorUnitarioOficializado(valorUnitarioOficializado);
								 
								int antiguedad = djpredio.getAnnoDj() - instalacion.getAnnoInstalacion();
								BigDecimal porcentajeDep= BigDecimal.valueOf(100.00);
								BigDecimal valorDepreciacion=registroPrediosBo.obtenerPorcentajeDepreciacion(instalacion.getClasiDepreciacionId(), djpredio.getAnnoDj(), instalacion.getMatPredominanteId(), instalacion.getConservacionId(), antiguedad);
								BigDecimal valorUnitarioDepreciacion=valorUnitarioOficializado.subtract(valorUnitarioOficializado.multiply((valorDepreciacion.divide(porcentajeDep))));
								instalacion.setValorUnitarioDepreciado(valorUnitarioDepreciacion);
								instalacion.setValorObra(valorUnitarioDepreciacion.multiply(instalacion.getAreaTerreno()));
												
								BigDecimal valorUnitario=instalacion.getAreaTerreno().multiply(tipoObraPeriodo.getValorUnitario());
								BigDecimal valorInstalacion=valorUnitario.subtract(valorUnitario.multiply((valorDepreciacion.divide(porcentajeDep))));
								
								if(valorInstalacion!=null){
									instalacion.setValorInstalacion(valorInstalacion);	
								}
								
							}/** De acuerdo al Informe 053-009-00001961, de fecha 04 Abril del 2017, se realizará el cálculo incluyendo depreciación:  (Fin)*/
	
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
					int res=registroPrediosBo.guardarRpOtrosFrente(frente);
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
						//Siempre es del mismo periodo por lo tanto no cambia el anio de afectacion de los usos
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
				
				if(motivoDeclaracion.equals(Constante.MOTIVO_DECLARACION_INSCRIPCION)){
					//Los transferentes de este predio en Inscripcion es el propietario original
					if(propietario!=null){
						//satc.dbo.rp_transferencia_propiedad	
						BuscarPersonaDTO transf=new BuscarPersonaDTO();
						transf.setPersonaId(propietario.getPersonaId());
						transf.setPorcentaje(djpredio.getPorcPropiedad());
						transf.setDescargoAutomatico("S");
						
						List<BuscarPersonaDTO> lTransferente=new LinkedList<BuscarPersonaDTO>();
						lTransferente.add(transf);
						registroPrediosBo.registrarAdquirientes(lTransferente,DjActualizaId,Constante.TIPO_TRANSFERENCIA_TRANSFERENTE);
					}
				}else if(motivoDeclaracion.equals(Constante.MOTIVO_DECLARACION_ACTUALIZA)||
							motivoDeclaracion.equals(Constante.MOTIVO_DECLARACION_DESCARGO)){
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
			// TODO: Handle exception
			System.out.println("No se puede duplicar dj predial: "+ex);
		}
		
		if(djpredio!=null){
			djpredio.setDjId(DjActualizaId);
		}
		return djpredio;
	}
	private RpDjdireccion rpDjdireccion;
	
	public RpDjdireccion getRpDjdireccion() {
		return rpDjdireccion;
	}
	public void setRpDjdireccion(RpDjdireccion rpDjdireccion) {
		this.rpDjdireccion = rpDjdireccion;
	}

	public void loadUbicacion() {
		try {
			Integer djId = (Integer) FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId");
			if (djId != null && djId > 0) {
				rpDjdireccion = registroPrediosBo.getRpDjDireccion(djId);
				if (rpDjdireccion != null) {
					setDireccionPredio(getRpDjdireccion().getDireccionCompleta());
					getRpDjpredial().setDescDomicilio(getRpDjdireccion().getDireccionCompleta());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
		
	public void loadTransferente(){
		try{
			Integer djId=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId");
			if(djId!=null&&djId>0){
				List<BuscarPersonaDTO> lTransferente=registroPrediosBo.getTransferentePropiedad(djId,Constante.TIPO_TRANSFERENCIA_TRANSFERENTE);
				if(lTransferente.size()>0){
					transferente=lTransferente.get(0);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
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
	//--Construccion
	public void editarConstruccionLectura(){
		editarConstruccion(Boolean.TRUE);
	}
	public void editarConstruccion(){
		editarConstruccion(Boolean.FALSE);
	}
	
	public void editarConstruccion(Boolean disabled){
		try{
			FacesUtil.closeSession("construccionManaged");
			
			if(currentItem!=null){
				
				String myareaTerreno = FacesUtil.getRequestParameter("myareaTerreno");
				String myareaTerrenorustico = FacesUtil.getRequestParameter("myareaTerrenorustico");
				
				String poseeAreaComunConstruida= FacesUtil.getRequestParameter("poseeAreaComunConstruida");
				if(poseeAreaComunConstruida.equals("1")){
					getSessionMap().put("poseeAreaComunConstruida", Boolean.TRUE);	
				}else{
					getSessionMap().put("poseeAreaComunConstruida", Boolean.FALSE);
				}
				RpDjconstruccion rpDjconstruccion =registroPrediosBo.getRpDjconstruccion(currentItem.getDjId(),currentItem.getConstruccionId(),getRpDjpredial().getAnnoDj());
				
				if(getTipoPredio().trim().equals("U")){
					rpDjconstruccion.setAreaTerreno(convertir(myareaTerreno));
				}else{
					rpDjconstruccion.setAreaTerreno(convertir(myareaTerrenorustico));
				}
				//--
				ConstruccionManaged construccion = (ConstruccionManaged) getManaged("construccionManaged");
				construccion.setAnnoDj(rpDjpredial.getAnnoDj());
				construccion.reloadTipoNivel();
				construccion.setProperty(rpDjconstruccion);
				construccion.setDisabled(disabled);
			}else{
				WebMessages.messageError("Seleccione registro");
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);	
		}
	}
	
	public void eliminarConstruccion(){
		try{
			FacesUtil.closeSession("construccionManaged");
			if(currentItem!=null){
				String myareaTerreno = FacesUtil.getRequestParameter("myareaTerreno");
				String myareaTerrenorustico = FacesUtil.getRequestParameter("myareaTerrenorustico");
				
				RpDjconstruccion rpDjconstruccion =registroPrediosBo.getRpDjconstruccion(currentItem.getDjId(),currentItem.getConstruccionId(),getRpDjpredial().getAnnoDj());
				if(getTipoPredio().trim().equals("U")){
					rpDjconstruccion.setAreaTerreno(convertir(myareaTerreno));
				}else{
					rpDjconstruccion.setAreaTerreno(convertir(myareaTerrenorustico));
				}
				//--
				ConstruccionManaged construccion = (ConstruccionManaged) getManaged("construccionManaged");
				construccion.setProperty(rpDjconstruccion);	
			}else{
				WebMessages.messageError("Seleccione registro");
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);	
		}
	}

	public void nuevaConstruccion(){
		try{
			FacesUtil.closeSession("construccionManaged");
			String myareaTerreno = FacesUtil.getRequestParameter("myareaTerreno");
			String myareaTerrenorustico = FacesUtil.getRequestParameter("myareaTerrenorustico");
			String poseeAreaComunConstruida= FacesUtil.getRequestParameter("poseeAreaComunConstruida");
			if(poseeAreaComunConstruida.equals("1")){
				getSessionMap().put("poseeAreaComunConstruida", Boolean.TRUE);	
			}else{
				getSessionMap().put("poseeAreaComunConstruida", Boolean.FALSE);
			}
			RpDjconstruccion rpDjconstruccion =new RpDjconstruccion();
			if(getTipoPredio().trim().equals("U")){
				rpDjconstruccion.setAreaTerreno(convertir(myareaTerreno));
			}else{
				rpDjconstruccion.setAreaTerreno(convertir(myareaTerrenorustico));
			}
			ConstruccionManaged construccion = (ConstruccionManaged) getManaged("construccionManaged");
			construccion.setAnnoDj(rpDjpredial.getAnnoDj());
			construccion.reloadTipoNivel();
			construccion.setProperty(rpDjconstruccion);
			construccion.setDisabled(Boolean.FALSE);
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);	
		}
	}
	//--Construccion
	
	
	//--Otras instalaciones
	public void editarOtrasInstalacionesLectura(){
		editarOtrasInstalaciones(Boolean.TRUE);
	}
	public void editarOtrasInstalaciones(){
		editarOtrasInstalaciones(Boolean.FALSE);
	}
	
	public void editarOtrasInstalaciones(Boolean disabled){
		try{
			if(currentItemOtrasInsta!=null){
				RpInstalacionDj instalacion =registroPrediosBo.getRpInstalacionDj(currentItemOtrasInsta.getDjId(),currentItemOtrasInsta.getInstalacionId());
				if(instalacion!=null){
					OtrasInstalacionesManaged managed = (OtrasInstalacionesManaged) getManaged("otrasInstalacionesManaged");
					managed.setProperty(instalacion);
					managed.setDisabled(disabled);
					managed.setAnnoDj(getRpDjpredial().getAnnoDj());
				}else{
					//error es nulo
				}
		}else{
			WebMessages.messageError("Seleccione registro");
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);	
		}
	}

	public void nuevoOtrasInstalaciones(){
		try{
			RpInstalacionDj instalacion =new RpInstalacionDj();
			OtrasInstalacionesManaged managed = (OtrasInstalacionesManaged) getManaged("otrasInstalacionesManaged");
			managed.setAnnoDj(getRpDjpredial().getAnnoDj());
			managed.limpiar();
			managed.setProperty(instalacion);	
			managed.setDisabled(Boolean.FALSE);
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);	
		}
	}
	//--Otras instalaciones
	
	//--Otras Frentes
	public void editarOtrosFrentes(){
		try{
			if(currentItemOtrasInsta!=null){
				RpOtrosFrente instalacion =registroPrediosBo.getRpOtrosFrente(currentItemOtrasInsta.getDjId(),currentItemOtrasInsta.getInstalacionId());
				if(instalacion!=null){
					OtrosFrentesManaged managed = (OtrosFrentesManaged) getManaged("otrosFrentesManaged");
					managed.setProperty(instalacion);	
				}else{
					//error es nulo
				}
		}else{
			WebMessages.messageError("Seleccione registro");
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);	
		}
	}

	public void nuevoOtrosFrentes(){
		try{
			setOtroFrente(Boolean.TRUE);
			RpOtrosFrente frente =new RpOtrosFrente();
			OtrosFrentesManaged managed = (OtrosFrentesManaged) getManaged("otrosFrentesManaged");
			managed.setProperty(frente);
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);	
		}
	}
	//--Otras Frentes
	
	
	//--Usos del predio
	public void editarUsosDelPredioLectura(){
		editarUsosDelPredio(Boolean.TRUE);
	}
	
	public void editarUsosDelPredio(){
		editarUsosDelPredio(Boolean.FALSE);
	}
	public void editarUsosDelPredio(Boolean disabled){
		try{
			if(currentItemUsosDelPredio!=null){
				RpDjuso uso =registroPrediosBo.getRpDjuso(currentItemUsosDelPredio.getDjarbitrioId(),currentItemUsosDelPredio.getDjusoId());
				if(uso!=null){
					//if(isEsUrbano()){
					if(getEsConstruccion()){
						UsosPredioxNivelManaged managed = (UsosPredioxNivelManaged) getManaged("usosPredioxNivelManaged");
						managed.setProperty(uso);
						managed.loadEditNivelesConstruccion(records);
						managed.setDisabled(disabled);
					}else{
						UsosPredioManaged managed = (UsosPredioManaged) getManaged("usosPredioManaged");
						managed.setProperty(uso);
						managed.setDisabled(disabled);
					}
				}else{
					//error es nulo
				}
		}else{
			WebMessages.messageError("Seleccione registro");
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);	
		}
	}

	public void nuevoUsosDelPredio(){
		try{
			RpDjuso uso =new RpDjuso();
			//Se corrige el a�o de Usos de las construcciones
			if(rpDjpredial != null && rpDjpredial.getAnnoDj() != null){
				uso.setAnnoAfectacion(rpDjpredial.getAnnoDj());
			}else{
				uso.setAnnoAfectacion(DateUtil.getAnioActual());
			}
			
			//if(isEsUrbano())
			if(getEsConstruccion()){
				UsosPredioxNivelManaged managed = (UsosPredioxNivelManaged) getManaged("usosPredioxNivelManaged");
				managed.setProperty(uso);
				managed.loadNewNivelesConstruccion(records);
				managed.setDisabled(Boolean.FALSE);
			}else{
				UsosPredioManaged managed = (UsosPredioManaged) getManaged("usosPredioManaged");
				managed.setProperty(uso);
				managed.setDisabled(Boolean.FALSE);
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);	
		}
	}
	
	public void eliminarUsosDelPredio(){
		
	}
	//--Usos del predio

	public void vistaPrevia()throws Exception{
		try{    
			if(guardarDj()){
				cargaDj();
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	//--documentos anexos
	public void editarDocumentoAnexo(){
		try{
			if(currentItemDocAnexos!=null){
				RjDocuAnexo uso =registroPrediosBo.getRjDocuAnexo(currentItemDocAnexos.getDjId(),currentItemDocAnexos.getDocuAnexoId());
				if(uso!=null){
					DocumentoAnexoManaged managed = (DocumentoAnexoManaged) getManaged("documentoAnexoManaged");
					managed.setProperty(uso);	
				}else{
					//error es nulo
				}
		}else{
			WebMessages.messageError("Seleccione registro");
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);	
		}
	}

	public void nuevoDocumentoAnexo(){
		try{
			RjDocuAnexo docAnexo =new RjDocuAnexo();
			DocumentoAnexoManaged managed = (DocumentoAnexoManaged) getManaged("documentoAnexoManaged");
			managed.setProperty(docAnexo);
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);	
		}
	}
	public void eliminarDocumentoAnexo(){
		
	}
	//--documentos anexos
	
	/*
	 * La validacion se retiro debido a los diversos casos que se presentan
	public boolean ValidaAreaComun(Integer djId){
		boolean valido=false;
		try{
			if(mpPredio.getTipoPredio().equals(Constante.TIPO_PREDIO_URBANO)&&getPoseeAreaComun()){
				Iterator<RpDjconstruccion> it=records.iterator();
				while(it.hasNext()){
					RpDjconstruccion construccion=it.next();
					if(construccion.getTipoNivelId()==6){
						valido=true;
					}
				}
			}else{
				valido=true;
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		return valido;
	}
	*/
	
	public boolean ValidaConstruccion(Integer djId){
		boolean valido=false;
		try{
			if(getTipoPredio().equals(Constante.TIPO_PREDIO_URBANO)&&getCmbValueUbicacionPredio().equalsIgnoreCase("Terreno sin Construir")){
				//no necesita datos de construccion
				valido=true;
			}else if(getTipoPredio().equals(Constante.TIPO_PREDIO_RUSTICO)){
				//no necesita datos de construccion
				valido=true;
			}else{
				if(registroPrediosBo.existDjConstruccion(djId)>0){
					valido=true;
				}else{
					valido=false;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		return valido;
	}
	
	public void muestraUbicacionLectura(){
		muestraUbicacion(Boolean.TRUE);
	}
	
	public void muestraUbicacion() {
		setOtroFrente(Boolean.FALSE);
		muestraUbicacion(Boolean.FALSE);

	}
	
	public void muestraUbicacion(Boolean esLectura){
		try{
			Integer djId=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId");
			if(djId!=null&&djId>0){
				RpDjdireccion direccion=registroPrediosBo.getRpDjDireccion(djId);
				if(direccion!=null){
					if(!isEsUrbano()){
						UbicacionPredioRusticoManaged dire = (UbicacionPredioRusticoManaged) getManaged("ubicacionPredioRusticoManaged");
						dire.setRpDjdireccion(direccion);
						dire.setAnnoDj(getRpDjpredial().getAnnoDj());
						dire.setDisabled(esLectura);
						//no necesita setProperty()
					}else{
						UbicacionPredioUrbanoManaged dire = (UbicacionPredioUrbanoManaged) getManaged("ubicacionPredioUrbanoManaged");
						dire.setRpDjdireccion(direccion);
						dire.setAnnoDj(getRpDjpredial().getAnnoDj());
						dire.setProperty();
						dire.setDisabled(esLectura);
					}
				}else{
					if(!isEsUrbano()){
						UbicacionPredioRusticoManaged dire = (UbicacionPredioRusticoManaged) getManaged("ubicacionPredioRusticoManaged");
						dire.setRpDjdireccion(new RpDjdireccion());
						dire.setDisabled(esLectura);
					}else{
						UbicacionPredioUrbanoManaged dire = (UbicacionPredioUrbanoManaged) getManaged("ubicacionPredioUrbanoManaged");
						dire.setRpDjdireccion(new RpDjdireccion());
						dire.setDisabled(esLectura);
					}
				}
			}else{
				if(!isEsUrbano()){
					UbicacionPredioRusticoManaged dire = (UbicacionPredioRusticoManaged) getManaged("ubicacionPredioRusticoManaged");
					dire.setRpDjdireccion(new RpDjdireccion());
					dire.setDisabled(esLectura);
				}else{
					UbicacionPredioUrbanoManaged dire = (UbicacionPredioUrbanoManaged) getManaged("ubicacionPredioUrbanoManaged");
					dire.setRpDjdireccion(new RpDjdireccion());
					dire.setDisabled(esLectura);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);	
		}
	}
	
	public void muestraTransferente(){
		try{
			String destinoRefresh = FacesUtil.getRequestParameter("destinoRefresh");
			RegistroPersonaManaged registroPersonaManaged=(RegistroPersonaManaged)getManaged("registroPersonaManaged");
			registroPersonaManaged.setPantallaUso(ReusoFormCns.REGISTRO_PREDIAL);
			registroPersonaManaged.setDestinoRefresh(destinoRefresh);
			if(getTransferente()!=null&&getTransferente().getPersonaId()>Constante.RESULT_PENDING){
				MpPersona transferente=personaBo.getMpPersona(getTransferente().getPersonaId());
				FindMpPersona findMpPersona=personaBo.findmpPersona(getTransferente().getPersonaId());
				findMpPersona.setMpPersona(transferente);
				registroPersonaManaged.setMpPersona(transferente);
				registroPersonaManaged.setFinMpPersonaItem(findMpPersona);
				registroPersonaManaged.obtenerDatos();	
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public BigDecimal convertir(String value){
		BigDecimal decimal=new BigDecimal(0); 
		try{
			decimal=BigDecimal.valueOf(getNumber(value));
		}catch(Exception e){
			decimal=new BigDecimal(0);
		}
		return decimal;
	}
	
	public double getNumber(String value){
		double double_value=0; 
		try{
			double_value=Double.valueOf(value.replaceAll(",",""));
		}catch(Exception e){
			double_value=0;
		}
		return double_value;
	}
	
	public void loadDatosConstruccion(){
		try{
			Integer NextDjId=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId");
			//Se realizo la coreccion debido a que las construcciones no dependen del anio de la Dj los niveles de construccion deben ser las del periodo actual : 04/03/2013
			//records=registroPrediosBo.getAllRpDjconstruccion(NextDjId,getRpDjpredial().getAnnoDj());
			
			records=registroPrediosBo.getAllRpDjconstruccion2(NextDjId,DateUtil.getAnioActual());
			recordsAllConstruccion=registroPrediosBo.getAllRpDjconstruccion2(NextDjId,DateUtil.getAnioActual());
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	public void loadDatosInstalaciones(){
		try{
			Integer NextDjId=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId");
			recordsOtrasInsta=registroPrediosBo.getAllRpInstalacionDj(NextDjId);
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	public void loadDatosFrentes(){
		try{
			Integer NextDjId=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId");
			recordsOtrosFrentes=registroPrediosBo.getAllRpOtrosFrente(NextDjId);
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public HashMap<Integer, ArrayList<RpDjusoDetalle>> getRecordsUsosDelPredioxNivel() {
		return recordsUsosDelPredioxNivel;
	}
	public void setRecordsUsosDelPredioxNivel(
			HashMap<Integer, ArrayList<RpDjusoDetalle>> recordsUsosDelPredioxNivel) {
		this.recordsUsosDelPredioxNivel = recordsUsosDelPredioxNivel;
	}
	public void recalculoDatosUsos(){
		try{
			Integer NextDjId=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId");
			Integer djArbitrioId=registroPrediosBo.getDjArbitrioId(NextDjId);
			if(djArbitrioId!=null&&djArbitrioId>0){
				recordsUsosDelPredio=registroPrediosBo.getAllRpDjuso(djArbitrioId);
				//if(isEsUrbano()){ 
				if(getEsConstruccion()){
					for(int i=0;i<recordsUsosDelPredio.size();i++){
						RpDjuso uso=recordsUsosDelPredio.get(i);
						ArrayList<RpDjusoDetalle> lista=registroPrediosBo.getAllRpDjusoDetalle(uso.getDjusoId());
						BigDecimal areaTotalUsada=new BigDecimal(0); 
						for(int j=0;j<lista.size();j++){
							BigDecimal areaUsada=registroPrediosBo.getAreaUsadaRecalculo(uso.getDjusoId(),djArbitrioId,lista.get(j).getConstruccionId());
							areaUsada=(areaUsada==null)?new BigDecimal(0):areaUsada;
							areaTotalUsada=areaTotalUsada.add(areaUsada);
						}
						uso.setAreaUso(areaTotalUsada);
						registroPrediosBo.actualizaAreaUsoRpDjuso(uso);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	public void loadDatosUsos(){
		try{
			Integer NextDjId=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId");
			Integer djArbitrioId=registroPrediosBo.getDjArbitrioId(NextDjId);
			if(djArbitrioId!=null&&djArbitrioId>0){
				recordsUsosDelPredio=registroPrediosBo.getAllRpDjuso(djArbitrioId);
				//if(isEsUrbano()){ 
				if(getEsConstruccion()){
					for(int i=0;i<recordsUsosDelPredio.size();i++){
						RpDjuso uso=recordsUsosDelPredio.get(i);
						ArrayList<RpDjusoDetalle> lista=registroPrediosBo.getAllRpDjusoDetalle(uso.getDjusoId());
						for(int j=0;j<lista.size();j++){
							BigDecimal areaUsada=registroPrediosBo.getAreaUsada(djArbitrioId,lista.get(j).getConstruccionId());
							areaUsada=(areaUsada==null)?new BigDecimal(0):areaUsada;
							lista.get(j).setAreaUsada(areaUsada);	
						}
						recordsUsosDelPredioxNivel.put(uso.getDjusoId(), lista);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	public void loadDatosAnexos(){
		try{
			Integer NextDjId=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId");
			recordsDocAnexos=registroPrediosBo.getAllRjDocuAnexo(NextDjId);
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public void loadPersona(RpDjpredial djpredio){
		try{
			persona=registroPrediosBo.getFindPersona(djpredio.getPersonaId());
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	//--
	public void loadOtrasInstalaciones(){
		try{
			Integer NextDjId=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId");
			recordsOtrasInsta=registroPrediosBo.getAllRpInstalacionDj(NextDjId);
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	public void loadOtrosFrentes(){
		try{
			Integer NextDjId=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId");
			recordsOtrosFrentes=registroPrediosBo.getAllRpOtrosFrente(NextDjId);
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	public void loadUsosPredio(){
		try{
			Integer NextDjId=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId");
			Integer arbitrioId=registroPrediosBo.getDjArbitrioId(NextDjId);
			recordsUsosDelPredio=registroPrediosBo.getAllRpDjuso(arbitrioId);
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	public void loadDocumentosAnexos(){
		try{
			Integer NextDjId=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId");
			recordsDocAnexos=registroPrediosBo.getAllRjDocuAnexo(NextDjId);
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public void loadRelacionados(){
		try{
			//System.out.println("ingresando a loadrelacionados");
			Integer personaID=Integer.parseInt(String.valueOf(getSessionManaged().getContribuyente().getPersonaId()));
			listRelacionados=registroPrediosBo.getAllRelacionadoDTO(personaID);
			//System.out.println("cantidad de relacionados"+listRelacionados.size());
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);		
		}
	}
	
	//--
	
	public void registraTipoSustento(){
		String paramTipoSustento = FacesUtil.getRequestParameter("paramTipoSustento");
		DocumentoSustentoManaged sustento = (DocumentoSustentoManaged) getManaged("documentoSustentoManaged");
		sustento.setTipoDocumentoSustento(paramTipoSustento);
		sustento.init();
	}
	public String salir(){
		return sendRedirectPrincipal();
	}
	
	public void setTransferente(){
		String destinoRefresh = FacesUtil.getRequestParameter("destinoRefresh");
		BuscarPersonaManaged buscarPersonaManaged=(BuscarPersonaManaged)getManaged("buscarPersonaManaged");
		buscarPersonaManaged.setPantallaUso(ReusoFormCns.REGISTRO_PREDIAL);
		buscarPersonaManaged.setDestinoRefresh(destinoRefresh);
	}
	
	public void checkSecano(ValueChangeEvent event) {
		Boolean check = (Boolean) event.getNewValue();
		if (check == Boolean.TRUE) {
			setSecano(Boolean.TRUE);
		} else {
			setSecano(Boolean.FALSE);
		}
	}
	
	public void checkFrenteOk(ValueChangeEvent event) {
		Boolean check = (Boolean) event.getNewValue();
		if (check == Boolean.TRUE) {
			setFrenteOk(Boolean.TRUE);			
		} else {
			setFrenteOk(Boolean.FALSE);
		}
	}
	
	public void checkFrenteAViaPublica(ValueChangeEvent event) {
		Boolean check = (Boolean) event.getNewValue();
		if (check == Boolean.TRUE) {
			setFrenteVia(Boolean.TRUE);			
		} else {
			setFrenteVia(Boolean.FALSE);
		}
	}
	
	public void changeListenerComboBoxTipoInspeccion(ValueChangeEvent event) {
		String key = (String) event.getNewValue();

		if (key.contains("FIP")) {
			this.valueComboBoxTipoInspeccion = "FIP";
		} else if (key.contains("AINR")) {
			this.valueComboBoxTipoInspeccion = "AINR";
		} 
	}
	
	public void checkAR(ValueChangeEvent event) {
		Boolean check = (Boolean) event.getNewValue();
		if (check == Boolean.TRUE) {			
			setTipoAR(Boolean.TRUE);
		} else {			
			setTipoAR(Boolean.FALSE);
		}
	}
	
	public void checkReq(ValueChangeEvent event) {
		Boolean check = (Boolean) event.getNewValue();
		if (check == Boolean.TRUE) {			
			setRequerimiento(Boolean.TRUE);
		} else {			
			setRequerimiento(Boolean.FALSE);
		}
	}
	
	public void verificarDjInspeccionMsj(){
		try{
			if(currentItem!=null){				
				verificarDjInspeccion();				
				if (tieneInspeccion==false){
					WebMessages.messageError("No es posible editar la DJ porque no tiene un Requerimiento asociado");
						isInspeccion=Boolean.TRUE;
						isEditable=Boolean.FALSE;
				}else{
						isEditable=Boolean.TRUE;
						isInspeccion=Boolean.FALSE;
//						fiscalizarDjPendiente();
				}
				
			}else{
				
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public Boolean verificarDjInspeccion(){
		try{
			Integer resultado;
			Integer djId=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId");
			if(djId!=null){
				resultado=registroPrediosBo.getReqInspeccionByDj(djId);
				if (resultado ==0){
					tieneInspeccion=Boolean.FALSE;
					WebMessages.messageError("No es posible crear la DJ porque no tiene un Requerimiento asociado");
				}else{
					tieneInspeccion=Boolean.TRUE;
				}

			}else{
				
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		return tieneInspeccion;
	}

	public void guardarDjInspeccion() throws Exception {

		try {
			Integer djId=rpDjpredial.getDjId();//(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId");
			//rpDjpredial.getDjId();
			if(currentItemValorInsp != null){
				if (currentItemValorInsp.getResultadoId()!=null && currentItemValorInsp.getResultadoId()!=0){
					if (currentItemValorInsp.getResultadoId()==Constante.TIPO_DOC_FIP_ID){
						esFIP=Boolean.TRUE;
						esAINR=Boolean.FALSE;
					}else if (currentItemValorInsp.getResultadoId()==Constante.TIPO_DOC_AINR_ID){
						esFIP=Boolean.FALSE;
						esAINR=Boolean.TRUE;
					}

					listaDjInspeccion = ficalizacionBo.getDeclaracionesInspById(currentItemValorInsp.getInspeccionId(),currentItemValorInsp.getPersonaId(),djId);
			
						if (esFIP==Boolean.TRUE){
							nombreInspector=currentItemValorInsp.getInspectorId();
							fechaInspeccion=currentItemValorInsp.getFechaInspeccion();
							nombreInspectorAr=0;
							fechaInspeccionAr=null;
							
						}else if(esAINR==Boolean.TRUE){
							nombreInspector=currentItemValorInsp.getInspectorId();
							fechaInspeccion=currentItemValorInsp.getFechaInspeccion();
							nombreInspectorAr=currentItemValorInsp.getInspectorIdAr();
							fechaInspeccionAr=currentItemValorInsp.getFechaAr();
						}
						
							if(esAINR==Boolean.TRUE)
							{
								
								for (FindInspeccionDj i : listaDjInspeccion) {
								
									ficalizacionBo.guardarDetalleFip(nombreInspector,i.getAnnoDj().toString(), i.getDjId(), i.getPredioId(),
											i.getUbicacionId(),i.getTipoViaId(),i.getViaId(),i.getSectorId(),i.getManzana(),i.getCuadra(),i.getLadoCuadra(),
											fechaInspeccion,currentItemValorInsp.getInspeccionId(),i.getDireccionPredio(),i.getSector(),i.getVia(),i.getTipoVia(),
											i.getLugar(),nombreInspectorAr,fechaInspeccionAr,getUser().getUsuarioId(), getUser().getTerminal());
							
								}
								setTieneInspeccion(true);	
								
								//FacesUtil.closeSession("ActualizarRequerimientoManaged");
							}
							else{
										for (FindInspeccionDj i : listaDjInspeccion) {
										
											ficalizacionBo.guardarDetalleFip(nombreInspector,i.getAnnoDj().toString(), i.getDjId(), i.getPredioId(),
													i.getUbicacionId(),i.getTipoViaId(),i.getViaId(),i.getSectorId(),i.getManzana(),i.getCuadra(),i.getLadoCuadra(),
													fechaInspeccion,currentItemValorInsp.getInspeccionId(),i.getDireccionPredio(),i.getSector(),i.getVia(),i.getTipoVia(),
													i.getLugar(),nombreInspectorAr,fechaInspeccionAr,getUser().getUsuarioId(), getUser().getTerminal());
										
										}
										setTieneInspeccion(true);
//										FacesUtil.closeSession("ListaDJPredioManaged");
//										FacesUtil.closeSession("BusquedaPredioManaged");
//										FacesUtil.closeSession("ActualizarRequerimientoManaged");
										
						}
							
				}else{
					addErrorMessage(getMsg("Registre la F.I.P."));
					//currentItemValorInsp=new FindInspeccionHistorial();
				}
		 }else {
			 addErrorMessage(getMsg("Registre un requerimiento."));
			 
			 /**
			  * aqui estuvo lo que esta en guardarDjInspeccionElegida();
			  */
			 listarequer=ficalizacionBo.getDeclaracionesInspByPersona(rpDjpredial.getPersonaId(),rpDjpredial.getPredioId(),rpDjpredial.getAnnoDj());
			 
			 //Se comento la insercion del combo:porque se lo cambio por un panel:
						//			 List<FindInspeccionDj> lstMpInspector = ficalizacionBo.getDeclaracionesInspByPersona(rpDjpredial.getPersonaId(),rpDjpredial.getPredioId(),rpDjpredial.getAnnoDj());
						//			 if (lstMpInspector!=null){
						//			 		setTieneInspeccion(true);
						//					    Iterator<FindInspeccionDj> it3 = lstMpInspector.iterator();
						//						listaReqs = new ArrayList<SelectItem>();
						//		
						//						while (it3.hasNext()) {
						//							FindInspeccionDj obj = it3.next();
						//							listaReqs.add(new SelectItem(obj.getNroreqInspeccion(), String.valueOf(obj.getViaId())));
						//							mapReq.put(obj.getNroreqInspeccion().trim(),obj.getViaId());
						//						}
						//			 }
			 
		 }
			 
			
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}

	}
	
	public void guardarDjInspeccionElegida() throws Exception {
		try {
//			Integer djId=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId");
//			 List<FindInspeccionDj> lstMpInspector = ficalizacionBo.getDeclaracionesInspByPersona(rpDjpredial.getPersonaId(),rpDjpredial.getPredioId(),rpDjpredial.getAnnoDj());
				
			Integer djId=rpDjpredial.getDjId();
			
			 	if (reqNroElegida!=null){//lstMpInspector!=null
			 		setTieneInspeccion(true);

						
						Integer inspeccionIds=reqIdElegida;//lstMpInspector.get(0).getReqInspeccion();
						Integer personaIds=listarequer.get(0).getPersonaId();//lstMpInspector.get(0).getPersonaId();
			 	
						listaDjInspeccion = ficalizacionBo.getDeclaracionesInspById(inspeccionIds,personaIds,djId);
						inspeccionelegida=ficalizacionBo.getInspeccionByIdAsociada(inspeccionIds);
						
						if (inspeccionelegida.get(0).getTipoDocumentoIdResultado()!=null&&inspeccionelegida.get(0).getTipoDocumentoIdResultado()!=0){
		
							if (inspeccionelegida.get(0).getTipoDocumentoIdResultado()==Constante.TIPO_DOC_FIP_ID){//lstMpInspector.get(0).getFlagDjAnno()==Constante.TIPO_DOC_FIP_ID
								esFIP=Boolean.TRUE;
								esAINR=Boolean.FALSE;
							}else if (inspeccionelegida.get(0).getTipoDocumentoIdResultado()==Constante.TIPO_DOC_AINR_ID){
								esFIP=Boolean.FALSE;
								esAINR=Boolean.TRUE;
							}
							
							if (esFIP==Boolean.TRUE){
								nombreInspector=inspeccionelegida.get(0).getInspectorId();//lstMpInspector.get(0).getEstado();
								fechaInspeccion=inspeccionelegida.get(0).getFechaInspeccion();//lstMpInspector.get(0).getFechaRegistro();
								nombreInspectorAr=0;
								fechaInspeccionAr=null;
								
							}else if(esAINR==Boolean.TRUE){
								nombreInspector=inspeccionelegida.get(0).getInspectorId();//lstMpInspector.get(0).getEstado();
								fechaInspeccion=inspeccionelegida.get(0).getFechaInspeccion();//lstMpInspector.get(0).getFechaRegistro();
								nombreInspectorAr=inspeccionelegida.get(0).getInspectorIdAr();//lstMpInspector.get(0).getAnnoDj();
								fechaInspeccionAr=inspeccionelegida.get(0).getFechaInspeccionAr();//lstMpInspector.get(0).getFechaDeclaracion();
							}
							
							if(esAINR==Boolean.TRUE)
							{

								for (FindInspeccionDj i : listaDjInspeccion) {
									ficalizacionBo.guardarDetalleFip(nombreInspector,i.getAnnoDj().toString(), i.getDjId(), i.getPredioId(),
											i.getUbicacionId(),i.getTipoViaId(),i.getViaId(),i.getSectorId(),i.getManzana(),i.getCuadra(),i.getLadoCuadra(),
											fechaInspeccion,inspeccionIds,i.getDireccionPredio(),i.getSector(),i.getVia(),i.getTipoVia(),
											i.getLugar(),nombreInspectorAr,fechaInspeccionAr,getUser().getUsuarioId(), getUser().getTerminal());

								}
								setTieneInspeccion(true);	
							}
							else{
									  for (FindInspeccionDj i : listaDjInspeccion) {
											ficalizacionBo.guardarDetalleFip(nombreInspector,i.getAnnoDj().toString(), i.getDjId(), i.getPredioId(),
													i.getUbicacionId(),i.getTipoViaId(),i.getViaId(),i.getSectorId(),i.getManzana(),i.getCuadra(),i.getLadoCuadra(),
													fechaInspeccion,inspeccionIds,i.getDireccionPredio(),i.getSector(),i.getVia(),i.getTipoVia(),
													i.getLugar(),nombreInspectorAr,fechaInspeccionAr,getUser().getUsuarioId(), getUser().getTerminal());
										}
										setTieneInspeccion(true);
						   }
							
				}else{
					  addErrorMessage(getMsg("Registre la F.I.P."));
				}
							
			 }else{
				 setTieneInspeccion(false);	
				 addErrorMessage(getMsg("Verifique la elección."));
				  }
		}catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public String redireccionaRequerimiento(){	
		if(currentItemValorInsp!=null){
			getSessionMap().put("currentItem", currentItemValorInsp);
		}

		return sendRedirectPrincipal();
	}
	
	public String actualizacion(){
		if(rpDjpredial!=null){
			getSessionMap().put("currentItemPersona", rpDjpredial.getPersonaId());
		}
		return sendRedirectPrincipal();
	}
	
	public void loadReq(ValueChangeEvent event) {
		try {
			HtmlComboBox combo = (HtmlComboBox) event.getComponent();
			String value = combo.getValue().toString();
			if (value != null) {
				reqId = (Integer) mapReq.get(value);
				setCmbReq(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	
	public String getDireccionPredio() {
		return direccionPredio;
	}

	public void setDireccionPredio(String direccionPredio) {
		this.direccionPredio = direccionPredio;
	}
	
	public String getTipoPredio() {
		return tipoPredio;
	}

	public void setTipoPredio(String tipoPredio) {
		this.tipoPredio = tipoPredio;
	}
	public String getPredioHabitable() {
		return predioHabitable;
	}

	public void setPredioHabitable(String predioHabitable) {
		this.predioHabitable = predioHabitable;
	}
	
	public HtmlComboBox getCmbRpUbicacionPredio() {
		return cmbRpUbicacionPredio;
	}
	public void setCmbRpUbicacionPredio(HtmlComboBox cmbRpUbicacionPredio) {
		this.cmbRpUbicacionPredio = cmbRpUbicacionPredio;
	}
	public List<SelectItem> getLstUbicacionPredio() {
		return lstUbicacionPredio;
	}
	public void setLstUbicacionPredio(List<SelectItem> lstUbicacionPredio) {
		this.lstUbicacionPredio = lstUbicacionPredio;
	}
	public HtmlComboBox getCmbRpCondicionPropiedad() {
		return cmbRpCondicionPropiedad;
	}
	public void setCmbRpCondicionPropiedad(HtmlComboBox cmbRpCondicionPropiedad) {
		this.cmbRpCondicionPropiedad = cmbRpCondicionPropiedad;
	}
	public List<SelectItem> getLstCondicionPropiedad() {
		return lstCondicionPropiedad;
	}
	public void setLstCondicionPropiedad(List<SelectItem> lstCondicionPropiedad) {
		this.lstCondicionPropiedad = lstCondicionPropiedad;
	}
	public HtmlComboBox getCmbRpTipoAdquisicion() {
		return cmbRpTipoAdquisicion;
	}
	public void setCmbRpTipoAdquisicion(HtmlComboBox cmbRpTipoAdquisicion) {
		this.cmbRpTipoAdquisicion = cmbRpTipoAdquisicion;
	}
	public List<SelectItem> getLstTipoAdquisicion() {
		return lstTipoAdquisicion;
	}
	public void setLstTipoAdquisicion(List<SelectItem> lstTipoAdquisicion) {
		this.lstTipoAdquisicion = lstTipoAdquisicion;
	}
	public HtmlComboBox getCmbMpCondiEspePredio() {
		return cmbMpCondiEspePredio;
	}
	public void setCmbMpCondiEspePredio(HtmlComboBox cmbMpCondiEspePredio) {
		this.cmbMpCondiEspePredio = cmbMpCondiEspePredio;
	}
	public List<SelectItem> getLstCondiEspePredio() {
		return lstCondiEspePredio;
	}
	public void setLstCondiEspePredio(List<SelectItem> lstCondiEspePredio) {
		this.lstCondiEspePredio = lstCondiEspePredio;
	}
	public RpDjpredial getRpDjpredial() {
		return rpDjpredial;
	}
	public void setRpDjpredial(RpDjpredial rpDjpredial) {
		this.rpDjpredial = rpDjpredial;
	}
	public MpPredio getMpPredio() {
		return mpPredio;
	}
	public void setMpPredio(MpPredio mpPredio) {
		this.mpPredio = mpPredio;
	}
	
	public java.util.Date getFechaAdquisicion() {
		return fechaAdquisicion;
	}
	public void setFechaAdquisicion(java.util.Date fechaAdquisicion) {
		this.fechaAdquisicion = fechaAdquisicion;
	}
	public String getAreaTerreno() {
		return areaTerreno;
	}
	public void setAreaTerreno(String areaTerreno) {
		this.areaTerreno = areaTerreno;
	}
	public String getAreaTerrenoComun() {
		return areaTerrenoComun;
	}
	public void setAreaTerrenoComun(String areaTerrenoComun) {
		this.areaTerrenoComun = areaTerrenoComun;
	}
	public String getFrente() {
		return frente;
	}
	public void setFrente(String frente) {
		this.frente = frente;
	}
	public ArrayList<RpDjconstruccion> getRecords() {
		return records;
	}

	public void setRecords(ArrayList<RpDjconstruccion> records) {
		this.records = records;
	}

	public SimpleSelection getSelection() {
		return selection;
	}

	public void setSelection(SimpleSelection selection) {
		this.selection = selection;
	}

	public HtmlExtendedDataTable getTable() {
		return table;
	}

	public void setTable(HtmlExtendedDataTable table) {
		this.table = table;
	}

	public int getCurrentRow() {
		return currentRow;
	}

	public void setCurrentRow(int currentRow) {
		this.currentRow = currentRow;
	}

	public RpDjconstruccion getCurrentItem() {
		return currentItem;
	}

	public void setCurrentItem(RpDjconstruccion currentItem) {
		this.currentItem = currentItem;
	}
	
	public boolean isEsUrbano() {
		return esUrbano;
	}

	public void setEsUrbano(boolean esUrbano) {
		this.esUrbano = esUrbano;
	}

	public HtmlComboBox getCmbTipoTierraRustico() {
		return cmbTipoTierraRustico;
	}

	public void setCmbTipoTierraRustico(HtmlComboBox cmbTipoTierraRustico) {
		this.cmbTipoTierraRustico = cmbTipoTierraRustico;
	}

	public List<SelectItem> getLstTipoTierraRustico() {
		return lstTipoTierraRustico;
	}

	public void setLstTipoTierraRustico(List<SelectItem> lstTipoTierraRustico) {
		this.lstTipoTierraRustico = lstTipoTierraRustico;
	}

	public HtmlComboBox getCmbAltitud() {
		return cmbAltitud;
	}

	public void setCmbAltitud(HtmlComboBox cmbAltitud) {
		this.cmbAltitud = cmbAltitud;
	}

	public List<SelectItem> getLstAltitud() {
		return lstAltitud;
	}

	public void setLstAltitud(List<SelectItem> lstAltitud) {
		this.lstAltitud = lstAltitud;
	}

	public HtmlComboBox getCmbCategoriaRustico() {
		return cmbCategoriaRustico;
	}

	public void setCmbCategoriaRustico(HtmlComboBox cmbCategoriaRustico) {
		this.cmbCategoriaRustico = cmbCategoriaRustico;
	}

	public List<SelectItem> getLstCategoriaRustico() {
		return lstCategoriaRustico;
	}

	public void setLstCategoriaRustico(List<SelectItem> lstCategoriaRustico) {
		this.lstCategoriaRustico = lstCategoriaRustico;
	}	
	public String getCmbValueCondiEspePredio() {
		return cmbValueCondiEspePredio;
	}

	public void setCmbValueCondiEspePredio(String cmbValueCondiEspePredio) {
		this.cmbValueCondiEspePredio = cmbValueCondiEspePredio;
	}
	public String getCmbValueCondicionPropiedad() {
		return cmbValueCondicionPropiedad;
	}

	public void setCmbValueCondicionPropiedad(String cmbValueCondicionPropiedad) {
		this.cmbValueCondicionPropiedad = cmbValueCondicionPropiedad;
	}
	public String getCmbValueTipoAdquisicion() {
		return cmbValueTipoAdquisicion;
	}

	public void setCmbValueTipoAdquisicion(String cmbValueTipoAdquisicion) {
		this.cmbValueTipoAdquisicion = cmbValueTipoAdquisicion;
	}

	public String getCmbValueUbicacionPredio() {
		return cmbValueUbicacionPredio;
	}

	public void setCmbValueUbicacionPredio(String cmbValueUbicacionPredio) {
		this.cmbValueUbicacionPredio = cmbValueUbicacionPredio;
	}

	public Boolean getIs100porciento() {
		return is100porciento;
	}

	public void setIs100porciento(Boolean is100porciento) {
		this.is100porciento = is100porciento;
	}

	public Boolean getIsHabitable() {
		return isHabitable;
	}

	public void setIsHabitable(Boolean isHabitable) {
		this.isHabitable = isHabitable;
	}

	public Boolean getIsCondiEspePredio() {
		return isCondiEspePredio;
	}

	public void setIsCondiEspePredio(Boolean isCondiEspePredio) {
		this.isCondiEspePredio = isCondiEspePredio;
	}

	public String getCmbValueTipoTierraRustico() {
		return cmbValueTipoTierraRustico;
	}

	public void setCmbValueTipoTierraRustico(String cmbValueTipoTierraRustico) {
		this.cmbValueTipoTierraRustico = cmbValueTipoTierraRustico;
	}

	public String getCmbValueAltitud() {
		return cmbValueAltitud;
	}

	public void setCmbValueAltitud(String cmbValueAltitud) {
		this.cmbValueAltitud = cmbValueAltitud;
	}

	public String getCmbValueCategoriaRustico() {
		return cmbValueCategoriaRustico;
	}

	public void setCmbValueCategoriaRustico(String cmbValueCategoriaRustico) {
		this.cmbValueCategoriaRustico = cmbValueCategoriaRustico;
	}
	
	public Boolean getEsConstruccion() {
		return esConstruccion;
	}

	public void setEsConstruccion(Boolean esConstruccion) {
		this.esConstruccion = esConstruccion;
	}
	public Boolean getPoseeAreaComun() {
		return poseeAreaComun;
	}

	public void setPoseeAreaComun(Boolean poseeAreaComun) {
		this.poseeAreaComun = poseeAreaComun;
	}
	public String getOutputtextTituloCondicionEspecial() {
		return outputtextTituloCondicionEspecial;
	}

	public void setOutputtextTituloCondicionEspecial(
			String outputtextTituloCondicionEspecial) {
		this.outputtextTituloCondicionEspecial = outputtextTituloCondicionEspecial;
	}
	public HtmlInputText getTxtPorcentaje() {
		return txtPorcentaje;
	}

	public void setTxtPorcentaje(HtmlInputText txtPorcentaje) {
		this.txtPorcentaje = txtPorcentaje;
	}
	
	public HtmlInputText getTxtAreaTerreno() {
		return txtAreaTerreno;
	}

	public void setTxtAreaTerreno(HtmlInputText txtAreaTerreno) {
		this.txtAreaTerreno = txtAreaTerreno;
	}

	public BigDecimal getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(BigDecimal porcentaje) {
		this.porcentaje = porcentaje;
	}

	public ArrayList<RpInstalacionDj> getRecordsOtrasInsta() {
		return recordsOtrasInsta;
	}

	public void setRecordsOtrasInsta(ArrayList<RpInstalacionDj> recordsOtrasInsta) {
		this.recordsOtrasInsta = recordsOtrasInsta;
	}

	public RpInstalacionDj getCurrentItemOtrasInsta() {
		return currentItemOtrasInsta;
	}

	public void setCurrentItemOtrasInsta(RpInstalacionDj currentItemOtrasInsta) {
		this.currentItemOtrasInsta = currentItemOtrasInsta;
	}
	public ArrayList<RpOtrosFrente> getRecordsOtrosFrentes() {
		return recordsOtrosFrentes;
	}

	public void setRecordsOtrosFrentes(
			ArrayList<RpOtrosFrente> recordsOtrosFrentes) {
		this.recordsOtrosFrentes = recordsOtrosFrentes;
	}

	public RpOtrosFrente getCurrentItemOtrosFrentes() {
		return currentItemOtrosFrentes;
	}

	public void setCurrentItemOtrosFrentes(RpOtrosFrente currentItemOtrosFrentes) {
		this.currentItemOtrosFrentes = currentItemOtrosFrentes;
	}
	public ArrayList<RpDjuso> getRecordsUsosDelPredio() {
		return recordsUsosDelPredio;
	}

	public void setRecordsUsosDelPredio(ArrayList<RpDjuso> recordsUsosDelPredio) {
		this.recordsUsosDelPredio = recordsUsosDelPredio;
	}

	public RpDjuso getCurrentItemUsosDelPredio() {
		return currentItemUsosDelPredio;
	}

	public void setCurrentItemUsosDelPredio(RpDjuso currentItemUsosDelPredio) {
		this.currentItemUsosDelPredio = currentItemUsosDelPredio;
	}
	public ArrayList<RjDocuAnexo> getRecordsDocAnexos() {
		return recordsDocAnexos;
	}

	public void setRecordsDocAnexos(ArrayList<RjDocuAnexo> recordsDocAnexos) {
		this.recordsDocAnexos = recordsDocAnexos;
	}

	public RjDocuAnexo getCurrentItemDocAnexos() {
		return currentItemDocAnexos;
	}

	public void setCurrentItemDocAnexos(RjDocuAnexo currentItemDocAnexos) {
		this.currentItemDocAnexos = currentItemDocAnexos;
	}
	public FindPersona getPersona() {
		return persona;
	}
	public void setPersona(FindPersona persona) {
		this.persona = persona;
	}
	
	public Boolean getDatoscorrectos() {
		return datoscorrectos;
	}
	public void setDatoscorrectos(Boolean datoscorrectos) {
		this.datoscorrectos = datoscorrectos;
	}
	public void setdatoscorrectos(ValueChangeEvent event){
		 Boolean newValue = (Boolean) event.getNewValue();
		 if(newValue){
			 datoscorrectos=Boolean.TRUE;
		 }else{
			 datoscorrectos=Boolean.FALSE;
		 }
	}
	public String getFechaPalabras(){
		return MesUtil.getInstance().getCurrentDateWord();
	}
	
	public HashMap<Integer, String> getMapIRpCategoriaRustico() {
		return mapIRpCategoriaRustico;
	}
	public void setMapIRpCategoriaRustico(
			HashMap<Integer, String> mapIRpCategoriaRustico) {
		this.mapIRpCategoriaRustico = mapIRpCategoriaRustico;
	}
	public HashMap<Integer, String> getMapIRpAltitud() {
		return mapIRpAltitud;
	}
	public void setMapIRpAltitud(HashMap<Integer, String> mapIRpAltitud) {
		this.mapIRpAltitud = mapIRpAltitud;
	}
	public HashMap<Integer, String> getMapIRpTipoTierraRustico() {
		return mapIRpTipoTierraRustico;
	}
	public void setMapIRpTipoTierraRustico(
			HashMap<Integer, String> mapIRpTipoTierraRustico) {
		this.mapIRpTipoTierraRustico = mapIRpTipoTierraRustico;
	}
	public HashMap<Integer, String> getMapIMpCondiEspePredio() {
		return mapIMpCondiEspePredio;
	}
	public void setMapIMpCondiEspePredio(
			HashMap<Integer, String> mapIMpCondiEspePredio) {
		this.mapIMpCondiEspePredio = mapIMpCondiEspePredio;
	}

	public HashMap<Integer, String> getMapIRpTipoAdquisicion() {
		return mapIRpTipoAdquisicion;
	}
	public void setMapIRpTipoAdquisicion(
			HashMap<Integer, String> mapIRpTipoAdquisicion) {
		this.mapIRpTipoAdquisicion = mapIRpTipoAdquisicion;
	}

	public HashMap<Integer, String> getMapIRpCondicionPropiedad() {
		return mapIRpCondicionPropiedad;
	}
	public void setMapIRpCondicionPropiedad(
			HashMap<Integer, String> mapIRpCondicionPropiedad) {
		this.mapIRpCondicionPropiedad = mapIRpCondicionPropiedad;
	}

	public HashMap<Integer, String> getMapIRpUbicacionPredio() {
		return mapIRpUbicacionPredio;
	}
	public void setMapIRpUbicacionPredio(
			HashMap<Integer, String> mapIRpUbicacionPredio) {
		this.mapIRpUbicacionPredio = mapIRpUbicacionPredio;
	}

	public Integer getUbicacionPredioId() {
		return ubicacionPredioId;
	}
	public void setUbicacionPredioId(Integer ubicacionPredioId) {
		this.ubicacionPredioId = ubicacionPredioId;
	}

	public HashMap<Integer, EstadoArea> getMapEstadoArea() {
		return mapEstadoArea;
	}
	public void setMapEstadoArea(HashMap<Integer, EstadoArea> mapEstadoArea) {
		this.mapEstadoArea = mapEstadoArea;
	}
	public HashMap<String, String> getMapRjMes() {
		return mapRjMes;
	}
	public void setMapRjMes(HashMap<String, String> mapRjMes) {
		this.mapRjMes = mapRjMes;
	}
	public HashMap<Integer, EstadoTipoTierra> getMapEstadoTipoTierra() {
		return mapEstadoTipoTierra;
	}
	public void setMapEstadoTipoTierra(HashMap<Integer, EstadoTipoTierra> mapEstadoTipoTierra) {
		this.mapEstadoTipoTierra = mapEstadoTipoTierra;
	}
	public List<SelectItem> getLstAltitudCmb() {
		return lstAltitudCmb;
	}

	public void setLstAltitudCmb(List<SelectItem> lstAltitudCmb) {
		this.lstAltitudCmb = lstAltitudCmb;
	}

	public List<SelectItem> getLstCategoriaRusticoCmb() {
		return lstCategoriaRusticoCmb;
	}

	public void setLstCategoriaRusticoCmb(List<SelectItem> lstCategoriaRusticoCmb) {
		this.lstCategoriaRusticoCmb = lstCategoriaRusticoCmb;
	}

	public String getFiscaAceptado() {
		return fiscaAceptado;
	}

	public void setFiscaAceptado(String fiscaAceptado) {
		this.fiscaAceptado = fiscaAceptado;
	}

	public boolean isFlagFiscaPrevio() {
		return flagFiscaPrevio;
	}
	
	public Integer getMotivoDeclaracion() {
		return motivoDeclaracion;
	}

	public void setMotivoDeclaracion(Integer motivoDeclaracion) {
		this.motivoDeclaracion = motivoDeclaracion;
	}
	
	public HtmlComboBox getCmbTipoUsoRustico() {
		return cmbTipoUsoRustico;
	}

	public void setCmbTipoUsoRustico(HtmlComboBox cmbTipoUsoRustico) {
		this.cmbTipoUsoRustico = cmbTipoUsoRustico;
	}

	public List<SelectItem> getLstTipoUsoRustico() {
		return lstTipoUsoRustico;
	}

	public void setLstTipoUsoRustico(List<SelectItem> lstTipoUsoRustico) {
		this.lstTipoUsoRustico = lstTipoUsoRustico;
	}
	
	public String getCmbValueTipoUsoRustico() {
		return cmbValueTipoUsoRustico;
	}

	public void setCmbValueTipoUsoRustico(String cmbValueTipoUsoRustico) {
		this.cmbValueTipoUsoRustico = cmbValueTipoUsoRustico;
	}
	
	public HashMap<Integer, String> getMapIRvMotivoDecla() {
		return mapIRvMotivoDecla;
	}

	public void setMapIRvMotivoDecla(HashMap<Integer, String> mapIRvMotivoDecla) {
		this.mapIRvMotivoDecla = mapIRvMotivoDecla;
	}
	
	public String getSelectedNotaria() {
		return selectedNotaria;
	}

	public void setSelectedNotaria(String selectedNotaria) {
		this.selectedNotaria = selectedNotaria;
	}

	public List<SelectItem> getLstNotarias() {
		return lstNotarias;
	}

	public void setLstNotarias(List<SelectItem> lstNotarias) {
		this.lstNotarias = lstNotarias;
	}

	public HashMap<String, Integer> getMapGnNotaria() {
		return mapGnNotaria;
	}

	public void setMapGnNotaria(HashMap<String, Integer> mapGnNotaria) {
		this.mapGnNotaria = mapGnNotaria;
	}

	public HashMap<Integer, String> getMapIGnNotaria() {
		return mapIGnNotaria;
	}

	public void setMapIGnNotaria(HashMap<Integer, String> mapIGnNotaria) {
		this.mapIGnNotaria = mapIGnNotaria;
	}
	
	public BuscarPersonaDTO getTransferente() {
		return transferente;
	}

	public void setTransferente(BuscarPersonaDTO transferente) {
		this.transferente = transferente;
	}
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public String getAreaTerrenoHas() {
		return areaTerrenoHas;
	}

	public void setAreaTerrenoHas(String areaTerrenoHas) {
		this.areaTerrenoHas = areaTerrenoHas;
	}
	
	public String getAreaTerrenoComunHas() {
		return areaTerrenoComunHas;
	}

	public void setAreaTerrenoComunHas(String areaTerrenoComunHas) {
		this.areaTerrenoComunHas = areaTerrenoComunHas;
	}

	public ArrayList<RelacionadosDTO> getListRelacionados() {
		return listRelacionados;
	}

	public void setListRelacionados(ArrayList<RelacionadosDTO> listRelacionados) {
		this.listRelacionados = listRelacionados;
	}

	public java.util.Date getFechaActual() {
		return fechaActual;
	}

	public void setFechaActual(java.util.Date fechaActual) {
		this.fechaActual = fechaActual;
	}

	public String getGlosa() {
		return glosa;
	}

	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}

	public boolean isSecano() {
		return secano;
	}

	public void setSecano(boolean secano) {
		this.secano = secano;
	}

	public RpFiscaInspeccion getInspeccionFisca() {
		return inspeccionFisca;
	}

	public void setInspeccionFisca(RpFiscaInspeccion inspeccionFisca) {
		this.inspeccionFisca = inspeccionFisca;
	}

	public String getValueComboBoxTipoInspeccion() {
		return valueComboBoxTipoInspeccion;
	}

	public void setValueComboBoxTipoInspeccion(
			String valueComboBoxTipoInspeccion) {
		this.valueComboBoxTipoInspeccion = valueComboBoxTipoInspeccion;
	}

	public List<SelectItem> getListTipoInspeccion() {
		return listTipoInspeccion;
	}

	public void setListTipoInspeccion(List<SelectItem> listTipoInspeccion) {
		this.listTipoInspeccion = listTipoInspeccion;
	}

	public boolean isTipoAR() {
		return tipoAR;
	}

	public void setTipoAR(boolean tipoAR) {
		this.tipoAR = tipoAR;
	}

	public boolean isFisca() {
		return fisca;
	}

	public void setFisca(boolean fisca) {
		this.fisca = fisca;
	}

	public java.util.Date getFechaInspeccion() {
		return fechaInspeccion;
	}

	public void setFechaInspeccion(java.util.Date fechaInspeccion) {
		this.fechaInspeccion = fechaInspeccion;
	}

	public java.util.Date getFechaRequerimiento() {
		return fechaRequerimiento;
	}

	public void setFechaRequerimiento(java.util.Date fechaRequerimiento) {
		this.fechaRequerimiento = fechaRequerimiento;
	}

	public boolean isRequerimiento() {
		return requerimiento;
	}

	public void setRequerimiento(boolean requerimiento) {
		this.requerimiento = requerimiento;
	}

	public boolean isFrenteOk() {
		return frenteOk;
	}

	public void setFrenteOk(boolean frenteOk) {
		this.frenteOk = frenteOk;
	}

	public BigDecimal getFrenteAreaComun() {
		return frenteAreaComun;
	}

	public void setFrenteAreaComun(BigDecimal frenteAreaComun) {
		this.frenteAreaComun = frenteAreaComun;
	}

	public BigDecimal getDistanciaAPredio() {
		return distanciaAPredio;
	}

	public void setDistanciaAPredio(BigDecimal distanciaAPredio) {
		this.distanciaAPredio = distanciaAPredio;
	}

	public BigDecimal getPorcentajeParticipacion() {
		return porcentajeParticipacion;
	}

	public void setPorcentajeParticipacion(BigDecimal porcentajeParticipacion) {
		this.porcentajeParticipacion = porcentajeParticipacion;
	}

	public boolean isFrenteVia() {
		return frenteVia;
	}

	public void setFrenteVia(boolean frenteVia) {
		this.frenteVia = frenteVia;
	}

	public Integer getUbicacionPredio() {
		return ubicacionPredioId;
	}

	public void setUbicacionPredio(Integer ubicacionPredio) {
		this.ubicacionPredioId = ubicacionPredio;
	}

	public boolean isOtroFrente() {
		return otroFrente;
	}

	public void setOtroFrente(boolean otroFrente) {
		this.otroFrente = otroFrente;
	}

	public ArrayList<RpDjconstruccion> getRecordsAllConstruccion() {
		return recordsAllConstruccion;
	}

	public void setRecordsAllConstruccion(ArrayList<RpDjconstruccion> recordsAllConstruccion) {
		this.recordsAllConstruccion = recordsAllConstruccion;
	}

	public Boolean getTieneInspeccion() {
		return tieneInspeccion;
	}

	public void setTieneInspeccion(Boolean tieneInspeccion) {
		this.tieneInspeccion = tieneInspeccion;
	}

	public Boolean getIsInspeccion() {
		return isInspeccion;
	}

	public void setIsInspeccion(Boolean isInspeccion) {
		this.isInspeccion = isInspeccion;
	}

	public Boolean getIsEditable() {
		return isEditable;
	}

	public void setIsEditable(Boolean isEditable) {
		this.isEditable = isEditable;
	}

	public FindInspeccionHistorial getCurrentItemValorInsp() {
		return currentItemValorInsp;
	}

	public void setCurrentItemValorInsp(FindInspeccionHistorial currentItemValorInsp) {
		this.currentItemValorInsp = currentItemValorInsp;
	}

	public List<FindInspeccionDj> getListaDjInspeccion() {
		return listaDjInspeccion;
	}

	public void setListaDjInspeccion(List<FindInspeccionDj> listaDjInspeccion) {
		this.listaDjInspeccion = listaDjInspeccion;
	}

	public Integer getNombreInspector() {
		return nombreInspector;
	}

	public void setNombreInspector(Integer nombreInspector) {
		this.nombreInspector = nombreInspector;
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

	public String getCmbReq() {
		return cmbReq;
	}

	public void setCmbReq(String cmbReq) {
		this.cmbReq = cmbReq;
	}

	public HtmlComboBox getCmbxReq() {
		return cmbxReq;
	}

	public void setCmbxReq(HtmlComboBox cmbxReq) {
		this.cmbxReq = cmbxReq;
	}

	public Integer getReqId() {
		return reqId;
	}

	public void setReqId(Integer reqId) {
		this.reqId = reqId;
	}

	public HashMap<String, Integer> getMapReq() {
		return mapReq;
	}

	public void setMapReq(HashMap<String, Integer> mapReq) {
		this.mapReq = mapReq;
	}

	public List<SelectItem> getListaReqs() {
		return listaReqs;
	}

	public void setListaReqs(List<SelectItem> listaReqs) {
		this.listaReqs = listaReqs;
	}

	public FindInspeccionDj getInspecciones() {
		return inspecciones;
	}

	public void setInspecciones(FindInspeccionDj inspecciones) {
		this.inspecciones = inspecciones;
	}

	public List<FindInspeccionDj> getListarequer() {
		return listarequer;
	}

	public void setListarequer(List<FindInspeccionDj> listarequer) {
		this.listarequer = listarequer;
	}

	public Integer getReqIdElegida() {
		return reqIdElegida;
	}

	public void setReqIdElegida(Integer reqIdElegida) {
		this.reqIdElegida = reqIdElegida;
	}

	public String getReqNroElegida() {
		return reqNroElegida;
	}

	public void setReqNroElegida(String reqNroElegida) {
		this.reqNroElegida = reqNroElegida;
	}

	public List<FindInspeccionByIdAsociada> getInspeccionelegida() {
		return inspeccionelegida;
	}

	public void setInspeccionelegida(
			List<FindInspeccionByIdAsociada> inspeccionelegida) {
		this.inspeccionelegida = inspeccionelegida;
	}

		
}