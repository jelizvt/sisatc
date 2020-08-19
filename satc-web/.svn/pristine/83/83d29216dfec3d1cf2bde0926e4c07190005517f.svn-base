package com.sat.sisat.vehicular.managed;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.sat.sisat.alfresco.RepositoryManager;
import com.sat.sisat.alfresco.content.AnexoDjVehicular;
import com.sat.sisat.alfresco.util.Constantes;
import com.sat.sisat.alfresco.util.Util;
import com.sat.sisat.common.business.GeneralBoRemote;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.FacesUtil;
import com.sat.sisat.common.util.FileUpload;
import com.sat.sisat.common.util.SATWEBParameterFactory;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.controlycobranza.business.ControlyCobranzaBoRemote;
import com.sat.sisat.controlycobranza.dto.FindCcLote;
import com.sat.sisat.determinacion.vehicular.business.DeterminacionVehicularBo;
import com.sat.sisat.determinacion.vehicular.business.DeterminacionVehicularBoRemote;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.fiscalizacion.dto.DatosNecesariosDeclaracionDTO;
import com.sat.sisat.fiscalizacion.dto.FindInspeccionHistorial;
import com.sat.sisat.obligacion.business.ObligacionBoRemote;
import com.sat.sisat.obligacion.dto.MultaDTO;
import com.sat.sisat.obligacion.dto.ObligacionDTO;
import com.sat.sisat.persistence.CrudServiceBean;
import com.sat.sisat.persistence.entity.CcLote;
import com.sat.sisat.persistence.entity.CcLoteActo;
import com.sat.sisat.persistence.entity.CcLoteActoPK;
import com.sat.sisat.persistence.entity.CcLoteConcepto;
import com.sat.sisat.persistence.entity.CcLoteConceptoPK;
import com.sat.sisat.persistence.entity.DtDeterminacion;
import com.sat.sisat.persistence.entity.GnNotaria;
import com.sat.sisat.persistence.entity.GnTipoCambio;
import com.sat.sisat.persistence.entity.GnTipoMoneda;
import com.sat.sisat.persistence.entity.MpTipoDocumentoCondicionEspecial;
import com.sat.sisat.persistence.entity.RvAdquisicion;
import com.sat.sisat.persistence.entity.RvCategoriaVehiculo;
import com.sat.sisat.persistence.entity.RvClaseVehiculo;
import com.sat.sisat.persistence.entity.RvCondicionPropiedad;
import com.sat.sisat.persistence.entity.RvCondicionVehiculo;
import com.sat.sisat.persistence.entity.RvDetCondVehiculo;
import com.sat.sisat.persistence.entity.RvDjvehicular;
import com.sat.sisat.persistence.entity.RvMarca;
import com.sat.sisat.persistence.entity.RvModeloVehiculo;
import com.sat.sisat.persistence.entity.RvModeloVehiculoPK;
import com.sat.sisat.persistence.entity.RvMotivoDeclaracion;
import com.sat.sisat.persistence.entity.RvSustentoVehicular;
import com.sat.sisat.persistence.entity.RvSustentoVehicularPK;
import com.sat.sisat.persistence.entity.RvTipoCarroceria;
import com.sat.sisat.persistence.entity.RvTipoMotor;
import com.sat.sisat.persistence.entity.RvTipoTraccion;
import com.sat.sisat.persistence.entity.RvTipoTransmision;
import com.sat.sisat.persistence.entity.RvTransferenciaPropiedad;
import com.sat.sisat.persistence.entity.RvVehiculo;
import com.sat.sisat.persona.business.PersonaBoRemote;
import com.sat.sisat.predial.business.RegistroPrediosBoRemote;
import com.sat.sisat.predial.dto.FindRpDjPredial;
import com.sat.sisat.vehicular.business.VehicularBoRemote;
import com.sat.sisat.vehicular.cns.ReusoFormCns;
import com.sat.sisat.vehicular.cns.VehicularCns;
import com.sat.sisat.vehicular.dto.AnexosDeclaVehicDTO;
import com.sat.sisat.vehicular.dto.BuscarPersonaDTO;
import com.sat.sisat.vehicular.dto.BuscarVehicularDTO;
import com.sat.sisat.vehicular.dto.DocumentoSustentatorioDTO;
import com.sat.sisat.vehicular.dto.PreliminarDTO;
import com.sat.sisat.vehicular.dto.VehiculoExistenteDTO;

@ManagedBean
@ViewScoped
public class NuevadjRegistroManaged extends BaseManaged {

	private static final long serialVersionUID = 6572691406652672558L;

	@EJB
	VehicularBoRemote vehicularBo;

	@EJB
	PersonaBoRemote personaBo;

	@EJB
	GeneralBoRemote generalBo;

	@EJB
	DeterminacionVehicularBoRemote determinacion;

	@EJB
	ObligacionBoRemote obligacionBo;

	@EJB
	RegistroPrediosBoRemote registroPrediosBo;
	
	@EJB
	ControlyCobranzaBoRemote controlycobranzaBo;

	// Datos declaración
	private String numeroDecla;

	private List<SelectItem> lstMotivoDecla = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRvMotivoDecla = new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIRvMotivoDecla = new HashMap<Integer, String>();
	private String selectedMotivoDecla;
	private int selectedMotivoDeclaId = 0;

	private Date fechaDecla = new Date();

	private String anioDecla = DateUtil.obtenerAnioActual();

	private String estadoDecla;

	// Datos vehículo
	private boolean existeVehiculo = true;
	private VehiculoExistenteDTO vehiculoExistente = new VehiculoExistenteDTO();

	private String nroMotorBuscar;
	private String placaBuscar;

	private String nroMotorVehic;
	private String placaVehic;
	private Date fechaPrimeraInsReg;
	private String anioFabricVehic;

	private List<SelectItem> lstClaseVehiculo = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRvClaseVehiculo = new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIRvClaseVehiculo = new HashMap<Integer, String>();
	private String selectedClaseVehic;

	private List<SelectItem> lstCategoriaVehiculo = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRvCategoriaVehiculo = new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIRvCategoriaVehiculo = new HashMap<Integer, String>();
	private String selectedCategoriaVehic;

	private List<SelectItem> lstMarcaVehiculo = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRvMarca = new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIRvMarca = new HashMap<Integer, String>();
	private String selectedMarcaVehic;

	private List<SelectItem> lstModelo = new ArrayList<SelectItem>();
	private HashMap<String, RvModeloVehiculoPK> mapRvModelo = new HashMap<String, RvModeloVehiculoPK>();
	private HashMap<RvModeloVehiculoPK, String> mapIRvModelo = new HashMap<RvModeloVehiculoPK, String>();
	private String selectedModeloVehic;

	private List<SelectItem> lstCondicionVehic = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRvCondicionVehiculo = new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIRvCondicionVehiculo = new HashMap<Integer, String>();
	private String selectedCondicionVehic;

	private List<SelectItem> lstTipoMotor = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRvTipoMotor = new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIRvTipoMotor = new HashMap<Integer, String>();
	private String selectedTipoMotorVehic;

	private List<SelectItem> lstCarroceria = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRvCarroceria = new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIRvCarroceria = new HashMap<Integer, String>();
	private String selectedCarroceriaVehic;

	private List<SelectItem> lstTransmision = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRvTransmision = new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIRvTransmision = new HashMap<Integer, String>();
	private String selectedTransmisionVehic;

	private List<SelectItem> lstTraccion = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRvTraccion = new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIRvTraccion = new HashMap<Integer, String>();
	private String selectedTraccionVehic;

	private String pesoBrutoVehic;
	private String cilindrosVehic;

	// Vehiculo - condición especial
	private int detCondVehicId = -1;
	private Date fechaInicial = new Date();
	private Date fechaFinal = new Date();

	private List<SelectItem> lstTipoDoc = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapGnTipoDoc = new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIGnTipoDoc = new HashMap<Integer, String>();
	private String selectedTipoDoc;

	private String nroDoc;
	private String selectedTipoInafec = getMsg("rv.tipoinafec.porcentaje");
	private String valorInafec = "100";
	private String comentario;

	// Datos adquisición
	private List<SelectItem> lstAdquisicion = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRvAdquisicion = new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIRvAdquisicion = new HashMap<Integer, String>();
	private String selectedAdquisicion;

	private Date fechaAdquisicion; // = new Date();

	private String tarjetaPropiedad;

	private List<SelectItem> lstCondicionPropiedad = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRvCondicionPropiedad = new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIRvCondicionPropiedad = new HashMap<Integer, String>();
	private String selectedCondicionPropiedad;

	private String porcentajePropiedad = "100";

	private List<SelectItem> lstTipoMoneda = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapGnTipoMoneda = new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIGnTipoMoneda = new HashMap<Integer, String>();
	private String selectedTipoMoneda;
	private int selectedTipoMonedaId;

	private String valorAdquisicion; // Soles
	private String valorAdquisicionDolares;

	private GnTipoCambio tipoCambio = new GnTipoCambio();

	// Datos de transferentes
	private List<SelectItem> lstNotarias = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapGnNotaria = new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIGnNotaria = new HashMap<Integer, String>();
	private String selectedNotaria;

	private List<BuscarPersonaDTO> lstTransferentes = new ArrayList<BuscarPersonaDTO>();

	// Datos de anexos
	private List<DocumentoSustentatorioDTO> lstDocSusten = new ArrayList<DocumentoSustentatorioDTO>();

	private List<AnexosDeclaVehicDTO> lstAnexos = new ArrayList<AnexosDeclaVehicDTO>();

	private PreliminarDTO pre;

	// Controller
	private boolean disabledDatosVehic = true;
	private boolean datosCorrectos = false;
	private boolean djGeneradoCorrect = false;
	private Integer vehiculoId = null;
	private int djvehicularId = -1;
	private String djvehicularIdNro = null;
	private int oldDjvehicularId = -1;
	private boolean guardarCorrecto = false;

	private String ACCION = null;

	// Cambio de Placa
	private String placaVehicNueva;
	private String placaVehicAnterior;

	private String glosa;

	// Fiscalizacion:
	private String fiscaAceptado = "NO ACEPTADO POR CONTRIBUYENTE";
	private Boolean esMasivaFiscalizacion = Boolean.FALSE;
	private Boolean esMasivaFiscalizacionImpresion = Boolean.FALSE;
	private ObligacionDTO obligacionDTO;
	private Integer persona;
	private Date fechaCompra;
	private String placa;
	private String nroReq;
	private List<MultaDTO> lstMultaDTOs;
	private Boolean noGenerarMulta = Boolean.FALSE;
	private CcLote ccLote=new CcLote();
	private CcLoteActo ccLoteActo = new CcLoteActo();
	private FindCcLote findCcLoteItem= new FindCcLote();
	private Integer lote_id;
	private Integer requerimiento_id;
	private Integer lote_vehicular_id;
	private Integer determinacion_id;

	BigDecimal totalAPagar = new BigDecimal(0);
	BigDecimal totalDscto = new BigDecimal(0);
	BigDecimal totalSubTotal = new BigDecimal(0);
	BigDecimal totalInteres = new BigDecimal(0);
	BigDecimal totalMonto = new BigDecimal(0);

	// private GnAuditoriaOperacion auditoria = new GnAuditoriaOperacion();
	private Boolean asociarImpresion;

	public NuevadjRegistroManaged() {
		getSessionManaged().setLinkRegresar(
				"/sisat/vehicular/buscarvehicular.xhtml");
	}

	@PostConstruct
	public void init() {
		selectedCondicionPropiedad="1";
		
		inicioBasicos();
		Object accion = getSessionMap().get("accion");
		if (accion != null) {
			ACCION = String.valueOf(accion);
			if (ACCION.equals(VehicularCns.PRIMERA_INSCRIPCION_CERO)) {
				primeraInscripcionCero();
			} else if (ACCION.equals(VehicularCns.PRIMERA_INSCRIPCION_VEHIC)) {
				primeraInscripcionVehic();
			} else if (ACCION.equals(VehicularCns.INSCRIPCION)) {
				inscripcion();
			} else if (ACCION
					.equals(VehicularCns.PENDIENTE_PRIMERA_INSCRIPCION)) {
				cargarPendiente();
			} else if (ACCION.equals(VehicularCns.PENDIENTE_ACTUALIZACION)) {
				cargarPendiente();
				getSessionManaged().setLinkRegresar(
						"/sisat/vehicular/historicovehiculodj.xhtml");
			} else if (ACCION.equals(VehicularCns.VIEW_DJ)) {
				mostrarDeclaJuradaVehic();
				getSessionManaged().setLinkRegresar(
						"/sisat/vehicular/historicovehiculodj.xhtml");
			}
		}
	}

	private void primeraInscripcionCero() {
		Object objp = getSessionMap().get("nuevadjregistro.placa");
		Object objm = getSessionMap().get("nuevadjregistro.motor");
		if (objp != null) {
			placaVehic = objp.toString();
		}
		if (objm != null) {
			nroMotorVehic = objm.toString();
		}
		selectedMotivoDeclaId = Integer
				.parseInt(getMsg("param.rv.motivodecla.primerains"));
		String valueMd = mapIRvMotivoDecla.get(selectedMotivoDeclaId);
		selectedMotivoDecla = valueMd;
		disabledDatosVehic = false;
	}

	private void primeraInscripcionVehic() {
		Object veId = getSessionMap().get("nuevadjregistro.vehicId");
		vehiculoId = Integer.parseInt(veId.toString());
		cargarDatosVehiculo(vehiculoId);
		selectedMotivoDeclaId = Integer
				.parseInt(getMsg("param.rv.motivodecla.primerains"));
		String valueMd = mapIRvMotivoDecla.get(selectedMotivoDeclaId);
		selectedMotivoDecla = valueMd;
		disabledDatosVehic = false;
	}

	private void inscripcion() {
		Object obj = getSessionMap().get("nuevadjregistro.djvId");
		try {
			if (obj != null) {
				int djvId = Integer.parseInt(String.valueOf(obj));
				RvDjvehicular djv = vehicularBo.findDjVehicularById(djvId);
				if (djv.getEstado().equals(
						getMsg("param.rv.estadopendienteaprobacion"))) {
					cargarPendiente();
					selectedMotivoDeclaId = Integer
							.parseInt(getMsg("param.rv.motivodecla.ins"));
				} else {
					cargarDatosVehiculo(djv.getVehiculoId());
					selectedMotivoDeclaId = Integer
							.parseInt(getMsg("param.rv.motivodecla.ins"));
					String valueMd = mapIRvMotivoDecla
							.get(selectedMotivoDeclaId);
					selectedMotivoDecla = valueMd;
					disabledDatosVehic = true;
				}
			}
		} catch (SisatException e) {
			WebMessages.messageError(e.getMessage());
		}
	}

	private void cargarPendiente() {
		try {
			Object obj = getSessionMap().get("nuevadjregistro.djvId");
			if (obj != null) {
				djvehicularId = Integer.parseInt(String.valueOf(obj));
				RvDjvehicular djv = vehicularBo
						.findDjVehicularById(djvehicularId);
				numeroDecla = djv.getDjvehicularNro();
				fechaDecla = djv.getFechaDeclaracion();
				if (ACCION.equals(VehicularCns.PENDIENTE_ACTUALIZACION)) {
					selectedMotivoDeclaId = Integer
							.parseInt(getMsg("param.rv.motivodecla.actualiza"));
					selectedMotivoDecla = mapIRvMotivoDecla
							.get(selectedMotivoDeclaId);
				} else {
					selectedMotivoDecla = mapIRvMotivoDecla.get(djv
							.getRvMotivoDeclaracionId());
				}
				anioDecla = djv.getAnnoDeclaracion();
				selectedAdquisicion = mapIRvAdquisicion.get(djv
						.getTipoAdquisicionId());
				fechaAdquisicion = djv.getFechaAdquisicion();
				tarjetaPropiedad = djv.getNumTarjetaPropiedad();
				selectedCondicionPropiedad = mapIRvCondicionPropiedad.get(djv
						.getTipoPropiedadId());
				porcentajePropiedad = String.valueOf(djv.getPorcPropiedad());
				selectedTipoMoneda = mapIGnTipoMoneda
						.get(djv.getTipoMonedaId());
				selectedTipoMonedaId = djv.getTipoMonedaId();
				valorAdquisicion = String.valueOf(djv.getValAdqSoles());
				if (djv.getValAdqOtraMoneda() == null) {
					valorAdquisicionDolares = null;
				} else {
					valorAdquisicionDolares = String.valueOf(djv
							.getValAdqOtraMoneda());
					tipoCambio = generalBo.obtenerTipoCambio(djv
							.getTipoCambioId());
				}
				selectedNotaria = mapIGnNotaria.get(djv.getNotariaId());
				glosa = djv.getGlosa();

				cargarDatosVehiculo(djv.getVehiculoId());
				setVehiculoId(djv.getVehiculoId());
				oldDjvehicularId = djv.getDjvehicularPrevioId();

				// Si tiene condición especial
				if (!selectedCondicionVehic
						.equals(getMsg("param.rv.condicionvehic.ningunodes"))) {
					cargarDatosCondicionEspecialVehic(djvehicularId);
				}
				// cargarDatosTransferenciaVehic(djvehicularId);
				cargarDocAnexosSustentoVehic(djvehicularId);

				setAsociarImpresion(false);
				cargaDatosPreliminar();
				disabledDatosVehic = false;
				datosCorrectos = true;
				estadoDecla = djv.getEstado();
			}
		} catch (Exception ex) {
			// TODO : Controller exception
			System.out.println("ERROR CARGANDO PENDIENTE: " + ex);
		}
	}

	private void mostrarDeclaJuradaVehic() {
		try {
			Object declaJuradaVehicId = getSessionMap().get(
					"nuevadjregistro.djvId");
			if (declaJuradaVehicId != null) {
				djvehicularId = Integer.parseInt(String
						.valueOf(declaJuradaVehicId));
				RvDjvehicular djv = vehicularBo
						.findDjVehicularById(djvehicularId);
				numeroDecla = djv.getDjvehicularNro();
				fechaDecla = djv.getFechaDeclaracion();
				selectedMotivoDecla = mapIRvMotivoDecla.get(djv
						.getRvMotivoDeclaracionId());
				anioDecla = djv.getAnnoDeclaracion();
				selectedAdquisicion = mapIRvAdquisicion.get(djv
						.getTipoAdquisicionId());
				fechaAdquisicion = djv.getFechaAdquisicion();
				tarjetaPropiedad = djv.getNumTarjetaPropiedad();
				selectedCondicionPropiedad = mapIRvCondicionPropiedad.get(djv
						.getTipoPropiedadId());
				porcentajePropiedad = String.valueOf(djv.getPorcPropiedad());
				selectedTipoMoneda = mapIGnTipoMoneda
						.get(djv.getTipoMonedaId());
				selectedTipoMonedaId = djv.getTipoMonedaId();
				valorAdquisicion = String.valueOf(djv.getValAdqSoles());
				if (djv.getValAdqOtraMoneda() == null) {
					valorAdquisicionDolares = null;
				} else {
					valorAdquisicionDolares = String.valueOf(djv
							.getValAdqOtraMoneda());
					tipoCambio = generalBo.obtenerTipoCambio(djv
							.getTipoCambioId());
				}
				selectedNotaria = mapIGnNotaria.get(djv.getNotariaId());
				if (djv.getGlosa() != null || !getGlosa().isEmpty()) {
					glosa = djv.getGlosa();
				}

				// Carga los datos del vehículo de la misma declaración con
				// excepción de la placa
				RvVehiculo ve = vehicularBo.findVehiculoById(djv
						.getVehiculoId());
				placaVehic = ve.getPlaca();
				if (ve.getPlacaAnterior() != null
						&& !ve.getPlacaAnterior().isEmpty()) {
					placaVehicAnterior = ve.getPlacaAnterior();
				}
				nroMotorVehic = djv.getNumMotor();
				fechaPrimeraInsReg = djv.getFechaInsRegistros();
				anioFabricVehic = String.valueOf(djv.getAnnoFabricacion());
				selectedClaseVehic = mapIRvClaseVehiculo.get(djv
						.getClaseVehiculoId());
				selectedCategoriaVehic = mapIRvCategoriaVehiculo.get(djv
						.getCategoriaVehiculoId());
				fillMarcaVehic();
				selectedMarcaVehic = mapIRvMarca.get(djv.getMarcaVehiculoId());
				lstModelo = new ArrayList<SelectItem>();
				selectedModeloVehic = null;
				fillModelo();
				RvModeloVehiculoPK id = new RvModeloVehiculoPK();
				id.setCategoriaVehiculoId(djv.getCategoriaVehiculoId());
				id.setMarcaVehiculoId(djv.getMarcaVehiculoId());
				id.setModeloVehiculoId(djv.getModeloVehiculoId());
				selectedModeloVehic = mapIRvModelo.get(id);
				selectedTipoMotorVehic = mapIRvTipoMotor.get(djv
						.getTipoMotorId());
				selectedCarroceriaVehic = mapIRvCarroceria.get(djv
						.getTipoCarroceriaId());
				selectedTransmisionVehic = mapIRvTransmision.get(djv
						.getTipoTransmisionId());
				selectedTraccionVehic = mapIRvTraccion.get(djv
						.getTipoTraccionId());
				pesoBrutoVehic = String.valueOf(djv.getPesoBruto());
				cilindrosVehic = String.valueOf(djv.getNumCilindros());
				selectedCondicionVehic = mapIRvCondicionVehiculo.get(djv
						.getCondicionVehiculoId());

				// Si tiene condición especial
				if (!selectedCondicionVehic
						.equals(getMsg("param.rv.condicionvehic.ningunodes"))) {
					cargarDatosCondicionEspecialVehic(djvehicularId);
				}
				cargarDatosTransferenciaVehic(djvehicularId);
				cargarDocAnexosSustentoVehic(djvehicularId);
			}
		} catch (Exception ex) {
			System.out.println("No se puede mostrar la declaración: " + ex);
		}
	}

	private void cargarDatosVehiculo(int vehiculoId) {
		RvVehiculo ve = vehicularBo.findVehiculoById(vehiculoId);
		placaVehic = ve.getPlaca();
		setVehiculoId(Integer.valueOf(ve.getVehiculoId()));
		nroMotorVehic = ve.getNumMotor();
		fechaPrimeraInsReg = ve.getFechaInsRegistros();
		anioFabricVehic = String.valueOf(ve.getAnnoFabricacion());
		selectedClaseVehic = mapIRvClaseVehiculo.get(ve.getClaseVehiculoId());
		selectedCategoriaVehic = mapIRvCategoriaVehiculo.get(ve
				.getCategoriaVehiculoId());
		fillMarcaVehic();
		selectedMarcaVehic = mapIRvMarca.get(ve.getMarcaVehiculoId());
		lstModelo = new ArrayList<SelectItem>();
		selectedModeloVehic = null;
		fillModelo();
		RvModeloVehiculoPK id = new RvModeloVehiculoPK();
		id.setCategoriaVehiculoId(ve.getCategoriaVehiculoId());
		id.setMarcaVehiculoId(ve.getMarcaVehiculoId());
		id.setModeloVehiculoId(ve.getModeloVehiculoId());
		selectedModeloVehic = mapIRvModelo.get(id);
		selectedTipoMotorVehic = mapIRvTipoMotor.get(ve.getTipoMotorId());
		selectedCarroceriaVehic = mapIRvCarroceria
				.get(ve.getTipoCarroceriaId());
		selectedTransmisionVehic = mapIRvTransmision.get(ve
				.getTipoTransmisionId());
		selectedTraccionVehic = mapIRvTraccion.get(ve.getTipoTraccionId());
		pesoBrutoVehic = ve.getPesoBruto() == 0 ? null : String.valueOf(ve
				.getPesoBruto());
		cilindrosVehic = ve.getNumCilindros() == 0 ? null : String.valueOf(ve
				.getNumCilindros());
		selectedCondicionVehic = mapIRvCondicionVehiculo.get(ve
				.getCondicionVehiculoId());
	}

	private void cargarDatosCondicionEspecialVehic(int djVehicId) {
		RvDetCondVehiculo detcv = vehicularBo.obtenerDetCondVehiculo(djVehicId);
		if (detcv != null) {
			detCondVehicId = detcv.getDetalleCondicionId();
			fechaInicial = detcv.getFechaInicial();
			fechaFinal = detcv.getFechaFinal();
			selectedTipoDoc = mapIGnTipoDoc.get(detcv.getTipoDocumentoId());
			nroDoc = detcv.getNroDocumento();
			if (detcv.getTipoInafectacion().equals(
					getMsg("param.tipoinafec.porcentaje"))) {
				selectedTipoInafec = getMsg("rv.tipoinafec.porcentaje");
			} else {
				selectedTipoInafec = getMsg("rv.tipoinafec.valormaximo");
			}
			valorInafec = detcv.getValorInafectacion().toString();
			comentario = detcv.getGlosa();
		}
	}

	private void cargarDatosTransferenciaVehic(int djVehicId) {
		lstTransferentes = vehicularBo.findTransferentes(djVehicId);
	}

	private void cargarDocAnexosSustentoVehic(int djVehicId) {
		lstAnexos = vehicularBo.findAnexos(djVehicId);
	}

	public void inicioBasicos() {
		try {
			// Datos declaración
			List<RvAdquisicion> lstAd = new ArrayList<RvAdquisicion>();
			lstAd = vehicularBo.getAllRvAdquisicion();
			Iterator<RvAdquisicion> itAd = lstAd.iterator();
			while (itAd.hasNext()) {
				RvAdquisicion objAd = itAd.next();
				lstAdquisicion.add(new SelectItem(objAd.getDescripcion()));
				mapRvAdquisicion.put(objAd.getDescripcion(),
						objAd.getTipoAdquisicionId());
				mapIRvAdquisicion.put(objAd.getTipoAdquisicionId(),
						objAd.getDescripcion());
			}

			List<RvCondicionPropiedad> lstCp = new ArrayList<RvCondicionPropiedad>();
			lstCp = vehicularBo.getAllRvCondicionPropiedad();
			Iterator<RvCondicionPropiedad> itCp = lstCp.iterator();
			while (itCp.hasNext()) {
				RvCondicionPropiedad objCp = itCp.next();
				lstCondicionPropiedad
						.add(new SelectItem(objCp.getDescripcion()));
				mapRvCondicionPropiedad.put(objCp.getDescripcion(),
						objCp.getTipoPropiedadId());
				mapIRvCondicionPropiedad.put(objCp.getTipoPropiedadId(),
						objCp.getDescripcion());
				selectedCondicionPropiedad = getMsg("rp.condicion.propiedad.unico");
			}

			List<GnTipoMoneda> lstTm = new ArrayList<GnTipoMoneda>();
			lstTm = vehicularBo.getAllGnTipoMoneda();
			Iterator<GnTipoMoneda> itTm = lstTm.iterator();
			while (itTm.hasNext()) {
				GnTipoMoneda objTm = itTm.next();
				lstTipoMoneda.add(new SelectItem(objTm.getDescripcion()));
				mapGnTipoMoneda.put(objTm.getDescripcion(),
						objTm.getTipoMonedaId());
				mapIGnTipoMoneda.put(objTm.getTipoMonedaId(),
						objTm.getDescripcion());
			}

			// Motivo decla
			List<RvMotivoDeclaracion> lstMd = new ArrayList<RvMotivoDeclaracion>();
			lstMd = vehicularBo.getAllRvMotivoDeclaracion();
			Iterator<RvMotivoDeclaracion> itMd = lstMd.iterator();
			while (itMd.hasNext()) {
				RvMotivoDeclaracion objMd = itMd.next();
				lstMotivoDecla.add(new SelectItem(objMd.getDescripcion()));
				mapRvMotivoDecla.put(objMd.getDescripcion(),
						objMd.getMotivoDeclaracionId());
				mapIRvMotivoDecla.put(objMd.getMotivoDeclaracionId(),
						objMd.getDescripcion());
			}

			// Clase vehículo
			List<RvClaseVehiculo> lst = new ArrayList<RvClaseVehiculo>();
			lst = vehicularBo.getAllRvClaseVehiculo();
			Iterator<RvClaseVehiculo> it = lst.iterator();
			while (it.hasNext()) {
				RvClaseVehiculo obj = it.next();
				lstClaseVehiculo.add(new SelectItem(obj.getDescripcion()));
				mapRvClaseVehiculo.put(obj.getDescripcion(),
						obj.getClaseVehiculoId());
				mapIRvClaseVehiculo.put(obj.getClaseVehiculoId(),
						obj.getDescripcion());
			}

			// Categoría vehículo
			List<RvCategoriaVehiculo> lst2 = new ArrayList<RvCategoriaVehiculo>();
			lst2 = vehicularBo.getAllRvCategoriaVehiculo();
			Iterator<RvCategoriaVehiculo> it2 = lst2.iterator();
			while (it2.hasNext()) {
				RvCategoriaVehiculo obj2 = it2.next();
				lstCategoriaVehiculo.add(new SelectItem(obj2.getDescripcion()));
				mapRvCategoriaVehiculo.put(obj2.getDescripcion(),
						obj2.getCategoriaVehiculoId());
				mapIRvCategoriaVehiculo.put(obj2.getCategoriaVehiculoId(),
						obj2.getDescripcion());

			}

			// Condicion vehículo
			List<RvCondicionVehiculo> lstCV = new ArrayList<RvCondicionVehiculo>();
			lstCV = vehicularBo.getAllRvCondicionVehiculo();
			Iterator<RvCondicionVehiculo> itCV = lstCV.iterator();
			while (itCV.hasNext()) {
				RvCondicionVehiculo objCV = itCV.next();
				lstCondicionVehic.add(new SelectItem(objCV.getDescripcion()));
				mapRvCondicionVehiculo.put(objCV.getDescripcion(),
						objCV.getCondicionVehiculoId());
				mapIRvCondicionVehiculo.put(objCV.getCondicionVehiculoId(),
						objCV.getDescripcion());
			}
			selectedCondicionVehic = mapIRvCondicionVehiculo.get(Integer
					.parseInt(getMsg("param.rv.condicionvehic.ninguno")));

			// Carrocería
			List<RvTipoCarroceria> lst4 = new ArrayList<RvTipoCarroceria>();
			lst4 = vehicularBo.getAllRvTipoCarroceria();
			Iterator<RvTipoCarroceria> it4 = lst4.iterator();
			while (it4.hasNext()) {
				RvTipoCarroceria obj4 = it4.next();
				lstCarroceria.add(new SelectItem(obj4.getDescripcion()));
				mapRvCarroceria.put(obj4.getDescripcion(),
						obj4.getTipoCarroceriaId());
				mapIRvCarroceria.put(obj4.getTipoCarroceriaId(),
						obj4.getDescripcion());
			}

			// Transmisión
			List<RvTipoTransmision> lst5 = new ArrayList<RvTipoTransmision>();
			lst5 = vehicularBo.getAllRvTipoTransmision();
			Iterator<RvTipoTransmision> it5 = lst5.iterator();
			while (it5.hasNext()) {
				RvTipoTransmision obj5 = it5.next();
				lstTransmision.add(new SelectItem(obj5.getDescripcion()));
				mapRvTransmision.put(obj5.getDescripcion(),
						obj5.getTipoTransmisionId());
				mapIRvTransmision.put(obj5.getTipoTransmisionId(),
						obj5.getDescripcion());
			}

			// Tracción
			List<RvTipoTraccion> lst6 = new ArrayList<RvTipoTraccion>();
			lst6 = vehicularBo.getAllRvTipoTraccion();
			Iterator<RvTipoTraccion> it6 = lst6.iterator();
			while (it6.hasNext()) {
				RvTipoTraccion obj6 = it6.next();
				lstTraccion.add(new SelectItem(obj6.getDescripcion()));
				mapRvTraccion.put(obj6.getDescripcion(),
						obj6.getTipoTraccionId());
				mapIRvTraccion.put(obj6.getTipoTraccionId(),
						obj6.getDescripcion());
			}

			// Tipo motor
			List<RvTipoMotor> lst7 = new ArrayList<RvTipoMotor>();
			lst7 = vehicularBo.getAllRvTipoMotor();
			Iterator<RvTipoMotor> it7 = lst7.iterator();
			while (it7.hasNext()) {
				RvTipoMotor obj7 = it7.next();
				lstTipoMotor.add(new SelectItem(obj7.getDescripcion()));
				mapRvTipoMotor
						.put(obj7.getDescripcion(), obj7.getTipoMotorId());
				mapIRvTipoMotor.put(obj7.getTipoMotorId(),
						obj7.getDescripcion());
			}
			// Adquirientes

			Object obj = getSessionMap().get("nuevadjregistro.djvId");
			if (obj != null) {
				int djtId = Integer.parseInt(String.valueOf(obj));
				lstTransferentes = vehicularBo.findDjTransferentes(djtId);
			}

			List<MpTipoDocumentoCondicionEspecial> lst8 = new ArrayList<MpTipoDocumentoCondicionEspecial>();
			lst8 = personaBo.getAllMpTipoDocumentoCondicionEspecial();
			Iterator<MpTipoDocumentoCondicionEspecial> it8 = lst8.iterator();
			while (it8.hasNext()) {
				MpTipoDocumentoCondicionEspecial obj8 = it8.next();
				lstTipoDoc.add(new SelectItem(obj8.getDescripcion()));
				mapGnTipoDoc.put(obj8.getDescripcion(),
						obj8.getTipoDocumentoCondicionEspecialId());
				mapIGnTipoDoc.put(obj8.getTipoDocumentoCondicionEspecialId(),
						obj8.getDescripcion());
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
			placaVehicNueva = "";
			placaVehicAnterior = "";
			glosa = "";
		} catch (Exception ex) {
			// TODO : controller exception
			System.out.println("ERROR: " + ex);
		}
	}

	public BuscarPersonaManaged getBuscarPersonaManaged() {
		return (BuscarPersonaManaged) getManaged("buscarPersonaManaged");
	}

	public void changeCategoria(ActionEvent ev) {
		fillMarcaVehic();
		lstModelo = new ArrayList<SelectItem>();
		selectedModeloVehic = null;
	}

	private void fillMarcaVehic() {
		lstMarcaVehiculo = new ArrayList<SelectItem>();
		try {
			List<RvMarca> lst3 = new ArrayList<RvMarca>();
			int categId = -1;

			if (selectedCategoriaVehic != null) {
				categId = mapRvCategoriaVehiculo.get(selectedCategoriaVehic);
			}

			lst3 = vehicularBo.findRvMarca(categId);

			Iterator<RvMarca> it3 = lst3.iterator();
			while (it3.hasNext()) {
				RvMarca obj3 = it3.next();
				lstMarcaVehiculo.add(new SelectItem(obj3.getDescripcion()));
				mapRvMarca
						.put(obj3.getDescripcion(), obj3.getMarcaVehiculoId());
				mapIRvMarca.put(obj3.getMarcaVehiculoId(),
						obj3.getDescripcion());
			}
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
		}
		selectedMarcaVehic = null;
	}

	public void changeMarca(ActionEvent ev) {
		fillModelo();
	}

	private void fillModelo() {
		lstModelo = new ArrayList<SelectItem>();

		Integer categId = mapRvCategoriaVehiculo.get(selectedCategoriaVehic);
		Integer marcaId = mapRvMarca.get(selectedMarcaVehic);

		try {
			if (categId != null && marcaId != null) {
				List<RvModeloVehiculo> lst = new ArrayList<RvModeloVehiculo>();
				lst = vehicularBo.getAllRvModeloVehiculo(marcaId, categId);
				Iterator<RvModeloVehiculo> it = lst.iterator();
				while (it.hasNext()) {
					RvModeloVehiculo obj = it.next();
					lstModelo.add(new SelectItem(obj.getDescripcion()));
					mapRvModelo.put(obj.getDescripcion(), obj.getId());
					mapIRvModelo.put(obj.getId(), obj.getDescripcion());
				}
			}
		} catch (Exception ex) {
			// TODO : Controller exception
			System.out.println("EXCEPCION: " + ex);
		}
		selectedModeloVehic = null;
	}

	public void changeCondicionPropiedad(ActionEvent ev) {
		String propUnico = getMsg("param.rv.condicionprop.unico");
		Integer selCP = mapRvCondicionPropiedad.get(selectedCondicionPropiedad);
		if (selCP.intValue() == Integer.parseInt(propUnico)) {
			porcentajePropiedad = "100";
		} else {
			porcentajePropiedad = null;
		}
	}

	public void changeTipoMoneda(ActionEvent ev) {
		valorAdquisicion = null;
		valorAdquisicionDolares = null;
		selectedTipoMonedaId = mapGnTipoMoneda.get(selectedTipoMoneda);
		if (selectedTipoMonedaId != Integer
				.parseInt(getMsg("param.rv.tipomoneda.sol"))) {
			tipoCambio = generalBo.obtenerTipoCambioDia(selectedTipoMonedaId);
			if (tipoCambio == null) {
				addErrorMessage(getMsg("gn.tipocambionodef"));
			}
		} else {
			tipoCambio = null;
		}
	}

	public void changeValorOtraMoneda(ActionEvent ev) {
		if (tipoCambio == null) {
			valorAdquisicionDolares = null;
			addErrorMessage(getMsg("gn.tipocambionodef"));
		} else {
			if (!valorAdquisicionDolares.isEmpty()) {
				BigDecimal valorSoles = new BigDecimal(valorAdquisicionDolares);
				valorSoles = valorSoles.multiply(tipoCambio.getValorVenta());
				valorAdquisicion = valorSoles.toString();
			}
		}

	}

	public void agregarTransferente(ActionEvent ev) {
		getBuscarPersonaManaged().setPantallaUso(
				ReusoFormCns.REGISTRO_VEHICULAR);
		getBuscarPersonaManaged().setDestinoRefresh("tblTransferentes");
	}

	/**
	 * Verifica que no se agregue un transferente que ya ha sido agregado y
	 * siempre sea diferente al contribuyente declarante
	 * 
	 * @param personaId
	 *            Identificador de persona a agregar
	 * @return
	 */
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
			System.out.println("ERROR: " + ex);
		}
	}

	public void mostrarPanelDocSustent(ActionEvent ev) {
		// lstAnexos = new ArrayList<AnexosDeclaVehicDTO>();
		for (DocumentoSustentatorioDTO dsd : lstDocSusten) {
			dsd.setNomDocAdjunto(null);
			dsd.setGlosa(null);
			dsd.setSelected(false);
		}
	}

	// INICIO ITANTAMANGO
	private AnexosDeclaVehicDTO rvSusVehicul;

	public AnexosDeclaVehicDTO getRvSusVehicul() {
		return rvSusVehicul;
	}

	public void setRvSusVehicul(AnexosDeclaVehicDTO rvSusVehicul) {
		this.rvSusVehicul = rvSusVehicul;
	}

	public void download() {
		try {

			ByteArrayOutputStream output = new ByteArrayOutputStream();
			RepositoryManager.buscarContenido(
					String.valueOf(rvSusVehicul.getContentId()), output);

			HttpServletResponse response = (HttpServletResponse) FacesContext
					.getCurrentInstance().getExternalContext().getResponse();
			response.setContentType(Constantes.CONTENT_MIMETYPE_JPEG);
			response.addHeader("Content-Disposition", "attachment;filename="
					+ rvSusVehicul.getNomDocAdjunto());
			response.setContentLength(output.size());
			ServletOutputStream servletOutputStream = response
					.getOutputStream();
			servletOutputStream.write(output.toByteArray(), 0, output.size());
			servletOutputStream.flush();
			servletOutputStream.close();
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void agregarAnexos(ActionEvent ev) {
		try {
			for (DocumentoSustentatorioDTO dsd : lstDocSusten) {
				if (dsd.isSelected()) {

					lstAnexos.add(new AnexosDeclaVehicDTO(dsd
							.getDocSustentatorioId(), dsd.getDescripcion(), dsd
							.getGlosa(), dsd.getNomDocAdjunto(), dsd
							.getContentId()));

					if (getLstArchivo() != null) {

						for (int i = 0; i < lstArchivo.size(); i++) {
							if (lstArchivo.get(i).getContentId() == dsd
									.getContentId()) {
								AnexoDjVehicular djV = new AnexoDjVehicular(
										lstArchivo.get(i).getContentId()
												+ lstArchivo.get(i)
														.getFileName(),
										Util.getBytesFromFile(lstArchivo.get(i)
												.getFile()),
										String.valueOf(lstArchivo.get(i)
												.getContentId()));
								RepositoryManager.guardarContenido(djV);
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
		}
	}

	// FIN ITANTAMANGO

	public void eliminarAnexoDeLista(ActionEvent ev) {
		try {
			UIComponent comp = ev.getComponent().getParent().getParent();
			if (comp != null) {
				UIData uiData = (UIData) comp;
				AnexosDeclaVehicDTO adv = (AnexosDeclaVehicDTO) uiData
						.getRowData();
				lstAnexos.remove(adv);
			}
		} catch (Exception ex) {
			// TODO : Controller exception
			System.out.println("ERROR: " + ex);
		}
	}

	public void guardar(ActionEvent ev) {
		if (validaDatosNulos()) {
			if (validaLogicaNegocio()) {
				try {
					// Create objects
					RvVehiculo vehiculo = new RvVehiculo();
					RvDjvehicular djvehicular = new RvDjvehicular();

					// vehiculo y los similares con djvehicular
					// condicion_vehiculo_id
					int condVehicId = mapRvCondicionVehiculo
							.get(selectedCondicionVehic);
					vehiculo.setCondicionVehiculoId(condVehicId);

					// tipo_transmision_id
					/*
					if (!selectedTransmisionVehic.isEmpty()) {
						int tipoTransmId = mapRvTransmision
								.get(selectedTransmisionVehic);
						vehiculo.setTipoTransmisionId(tipoTransmId);
						djvehicular.setTipoTransmisionId(tipoTransmId);
					}
					*/
					vehiculo.setTipoTransmisionId(0);
					djvehicular.setTipoTransmisionId(0);
					// tipo_traccion_id
					/*
					if (!selectedTraccionVehic.isEmpty()) {
						int tipoTracId = mapRvTraccion
								.get(selectedTraccionVehic);
						vehiculo.setTipoTraccionId(tipoTracId);
						djvehicular.setTipoTraccionId(tipoTracId);
					}					
					*/
					vehiculo.setTipoTraccionId(0);
					djvehicular.setTipoTraccionId(0);
					
					
					// marca_vehiculo_id
					int marcId = mapRvMarca.get(selectedMarcaVehic);
					vehiculo.setMarcaVehiculoId(marcId);
					djvehicular.setMarcaVehiculoId(marcId);

					// categoria_vehiculo_id
					int categId = mapRvCategoriaVehiculo
							.get(selectedCategoriaVehic);
					vehiculo.setCategoriaVehiculoId(categId);
					djvehicular.setCategoriaVehiculoId(categId);

					// modelo_vehiculo_id
					RvModeloVehiculoPK modId = mapRvModelo
							.get(selectedModeloVehic);
					vehiculo.setModeloVehiculoId(modId.getModeloVehiculoId());
					djvehicular
							.setModeloVehiculoId(modId.getModeloVehiculoId());

					// tipo_motor_id
					/*
					if (!selectedTipoMotorVehic.isEmpty()) {
						int tipoMotId = mapRvTipoMotor
								.get(selectedTipoMotorVehic);
						vehiculo.setTipoMotorId(tipoMotId);
						djvehicular.setTipoMotorId(tipoMotId);
					}
					*/
					vehiculo.setTipoMotorId(0);
					djvehicular.setTipoMotorId(0);					
					// placa
					// vehiculo.setPlaca(placaVehic);
					// CAMBIO DE PLACA
					if (!placaVehicNueva.isEmpty()) {
						vehiculo.setPlaca(placaVehicNueva);
						vehiculo.setPlacaAnterior(placaVehic);
					} else {
						vehiculo.setPlaca(placaVehic);
					}

					// num_motor
					vehiculo.setNumMotor(nroMotorVehic);
					djvehicular.setNumMotor(nroMotorVehic);

					// anno_fabricacion
					vehiculo.setAnnoFabricacion(Integer
							.parseInt(anioFabricVehic));
					djvehicular.setAnnoFabricacion(Integer
							.parseInt(anioFabricVehic));

					// fecha_ins_registros
					vehiculo.setFechaInsRegistros(DateUtil
							.dateToSqlTimestamp(fechaPrimeraInsReg));
					djvehicular.setFechaInsRegistros(DateUtil
							.dateToSqlTimestamp(fechaPrimeraInsReg));

					// num_cilindros
					/*
					if (cilindrosVehic != null && !cilindrosVehic.isEmpty()) {
						vehiculo.setNumCilindros(Integer
								.parseInt(cilindrosVehic));
						djvehicular.setNumCilindros(Integer
								.parseInt(cilindrosVehic));

					}
					*/
					vehiculo.setNumCilindros(0);
					djvehicular.setNumCilindros(0);					
					// peso_bruto
					/*
					if (pesoBrutoVehic != null && !pesoBrutoVehic.isEmpty()) {
						vehiculo.setPesoBruto(Integer.parseInt(pesoBrutoVehic));
						djvehicular.setPesoBruto(Integer
								.parseInt(pesoBrutoVehic));
					}
					*/
					vehiculo.setPesoBruto(0);
					djvehicular.setPesoBruto(0);					

					// usuario_id
					vehiculo.setUsuarioId(getSessionManaged().getUsuarioLogIn()
							.getUsuarioId());
					djvehicular.setUsuarioId(getSessionManaged()
							.getUsuarioLogIn().getUsuarioId());

					// fecha_actualizacion
					vehiculo.setFechaActualizacion(DateUtil.getCurrentDate());
					djvehicular
							.setFechaActualizacion(DateUtil.getCurrentDate());

					// fecha_registro
					vehiculo.setFechaRegistro(DateUtil.getCurrentDate());
					djvehicular.setFechaRegistro(DateUtil.getCurrentDate());

					// terminal
					vehiculo.setTerminal(getSessionManaged().getTerminalLogIn());
					djvehicular.setTerminal(getSessionManaged()
							.getTerminalLogIn());

					// tipo_carroceria_id
					/*
					if (!selectedCarroceriaVehic.isEmpty()) {
						int tipoCarrId = mapRvCarroceria
								.get(selectedCarroceriaVehic);
						vehiculo.setTipoCarroceriaId(tipoCarrId);
						djvehicular.setTipoCarroceriaId(tipoCarrId);
					}
					*/
					vehiculo.setTipoCarroceriaId(0);
					djvehicular.setTipoCarroceriaId(0);
					// clase_vehiculo_id
					int claseVehiId = mapRvClaseVehiculo
							.get(selectedClaseVehic);
					vehiculo.setClaseVehiculoId(claseVehiId);
					djvehicular.setClaseVehiculoId(claseVehiId);

					// RESTANTE djvehicular

					// tipo_moneda_id
					int tipoMonId = mapGnTipoMoneda.get(selectedTipoMoneda);
					djvehicular.setTipoMonedaId(tipoMonId);

					// tipo_propiedad_id
					int tipoPropId = mapRvCondicionPropiedad
							.get(selectedCondicionPropiedad);
					djvehicular.setTipoPropiedadId(tipoPropId);

					// persona_id
					djvehicular.setPersonaId(getSessionManaged()
							.getContribuyente().getPersonaId());

					// tipo_adquisicion_id
					int tpAdquiId = mapRvAdquisicion.get(selectedAdquisicion);
					djvehicular.setTipoAdquisicionId(tpAdquiId);

					// rv_motivo_declaracion_id
					int motivoDeclaId = mapRvMotivoDecla
							.get(selectedMotivoDecla);
					djvehicular.setRvMotivoDeclaracionId(motivoDeclaId);

					// condicion_vehiculo_id
					djvehicular.setCondicionVehiculoId(condVehicId);

					// notaria_id
					int notarId = mapGnNotaria.get(selectedNotaria);
					djvehicular.setNotariaId(notarId);

					// fecha_declaracion
					djvehicular.setFechaDeclaracion(DateUtil.getCurrentDate());

					// Cálculo de años de afectación
					Calendar cal = Calendar.getInstance();
					cal.setTime(fechaPrimeraInsReg);
					int anioInsReg = cal.get(Calendar.YEAR);
					int anioIniAfec = anioInsReg + 1;
					int anioFinAfec = anioInsReg + 3;

					// anno_ini_afectacion
					djvehicular.setAnnoIniAfectacion(anioIniAfec);
					// anno_fin_afectacion
					djvehicular.setAnnoFinAfectacion(anioFinAfec);

					if (ACCION.equals(VehicularCns.PENDIENTE_ACTUALIZACION)) {
						/** Cuando realizamos una actualizacion */
						djvehicular.setAnnoAfectacion(Integer
								.parseInt(this.anioDecla));
					} else {
						/**
						 * Cuando realizamos una inscripcion, pendientes de
						 * inscripcion, pendientes descargo
						 */

						Integer anhoAdquision = DateUtil
								.getAnhoFecha(fechaAdquisicion);
						if (anhoAdquision.compareTo(DateUtil.getAnioActual()) < 0) {
							// caso menor que el anho en curso entonces
							djvehicular.setAnnoAfectacion(anhoAdquision
									.intValue() + 1);
							this.anioDecla = String.valueOf(anhoAdquision
									.intValue() + 1);
						} else {
							// primera inscripcion del vehiculo en el anho en
							// curso por lo tanto la DJ solo es informativa
							// para este anho no genera impuesto
							djvehicular.setAnnoAfectacion(anhoAdquision);
							this.anioDecla = String.valueOf(anhoAdquision
									.intValue());
						}
					}

					// // El año declarado puede o no generar determinación
					// int anioD = Integer.parseInt(anioDecla);
					// // if (anioD >= anioIniAfec && anioD <= anioFinAfec) {
					// // //anno_afectacion
					// djvehicular.setAnnoAfectacion(anioD);
					// // }
					//

					// int anhoInscripRegisPub =
					// DateUtil.getAnhoFecha(fechaPrimeraInsReg);
					// int anhoAdquision =
					// DateUtil.getAnhoFecha(fechaAdquisicion);
					//
					// if(anhoInscripRegisPub <= anhoAdquision){
					// /**
					// * Caso que el anho en registros publicos sea menor o
					// igual al anho de adquision entonces
					// * tomamos como anho de afectacion el siguiente anho de la
					// fecha de inscripcion de registros publicos*/
					//
					// djvehicular.setAnnoAfectacion((anhoInscripRegisPub)>=DateUtil.getAnioActual().intValue()?DateUtil.getAnioActual():(anhoInscripRegisPub
					// + 1));
					// }else{
					// /**
					// * Caso cuando el anho de adquision es mayor a la de
					// registros, indicativo que el vehiculo
					// * se esta realizando un traspaso o venta*/
					// djvehicular.setAnnoAfectacion((anhoAdquision)>=DateUtil.getAnioActual().intValue()?DateUtil.getAnioActual():(anhoAdquision
					// + 1));
					// }

					// num_tarjeta_propiedad
					djvehicular.setNumTarjetaPropiedad(tarjetaPropiedad);

					// fecha_adquisicion
					djvehicular.setFechaAdquisicion(DateUtil
							.dateToSqlTimestamp(fechaAdquisicion));

					// val_adq_soles
					djvehicular
							.setValAdqSoles(new BigDecimal(valorAdquisicion));
					if (valorAdquisicionDolares != null) {
						if (!valorAdquisicionDolares.trim().isEmpty()) {
							// val_adq_otra_moneda
							djvehicular.setValAdqOtraMoneda(new BigDecimal(
									valorAdquisicionDolares));
							// tipo_cambio_id
							djvehicular.setTipoCambioId(tipoCambio
									.getTipoCambioId());
						}
					}

					// porc_propiedad
					djvehicular.setPorcPropiedad(new BigDecimal(
							porcentajePropiedad));

					// anno_declaracion
					djvehicular.setAnnoDeclaracion(anioDecla);

					// estado
					if (ACCION.equals(VehicularCns.PENDIENTE_ACTUALIZACION)) {
						djvehicular
								.setEstado(Constante.ESTADO_PENDIENTE_ACTUALIZACION);
					} else {
						djvehicular.setEstado(Constante.ESTADO_PENDIENTE);
					}

					// glosa
					djvehicular.setGlosa(glosa);

					// cuando existe el vehiculo entonces estamos hablando de un
					// descargo
					if (getVehiculoId() == null) {
						/** Primera incripcion de un vehiculo */
						setVehiculoId(vehicularBo.guardarVehiculo(vehiculo));
					} else {
						/** Cambiando de propietario al vehiculo */
						vehiculo.setVehiculoId(getVehiculoId());
						vehicularBo.actualizarDatosVehiculo(vehiculo);
					}

					// Si existe condición especial del vehículo
					RvDetCondVehiculo dcv = new RvDetCondVehiculo();

					if (!selectedCondicionVehic
							.equals(getMsg("param.rv.condicionvehic.ningunodes"))) {
						dcv.setFechaDocumento(DateUtil.getCurrentDate());
						dcv.setFechaInicial(DateUtil
								.dateToSqlTimestamp(fechaInicial));
						dcv.setFechaFinal(DateUtil
								.dateToSqlTimestamp(fechaFinal));
						dcv.setUsuarioId(getSessionManaged().getUsuarioLogIn()
								.getUsuarioId());
						dcv.setFechaActualizacion(DateUtil.getCurrentDate());
						dcv.setTipoDocumentoId(mapGnTipoDoc
								.get(selectedTipoDoc));
						dcv.setNroDocumento(nroDoc);
						if (selectedTipoInafec
								.equals(getMsg("rv.tipoinafec.porcentaje"))) {
							dcv.setTipoInafectacion(getMsg("param.tipoinafec.porcentaje"));
						} else {
							dcv.setTipoInafectacion(getMsg("param.tipoinafec.valormaximo"));
						}
						if (valorInafec == null || valorInafec.isEmpty()) {
							dcv.setValorInafectacion(new BigDecimal(0));
						} else {
							dcv.setValorInafectacion(new BigDecimal(valorInafec));
						}
						dcv.setGlosa(comentario);
					}

					/**
					 * Verificando que el vehiculo fue registrado correctamente
					 * para poder realizar el registro de la DJ
					 */
					if (vehiculoId != null) {
						/**
						 * La variable djvehicularId es cargada cuando la dj ha
						 * pasado por una vista preliminar
						 */
						if (this.djvehicularId == -1) {
							/** Vista preliminar de la DJ */
							// vehiculo_id
							djvehicular.setVehiculoId(vehiculoId);
							djvehicular.setFlagDjAnno(Constante.FLAG_ACTIVO);
							// GUARDAR DJ VEHICULAR y saca la djvehicularId
							RvDjvehicular dj = vehicularBo
									.guardarDJVehicular(djvehicular);
							this.djvehicularId = dj.getDjvehicularId();
							this.djvehicularIdNro = dj.getDjvehicularNro();
							// Guarda detalle condicion especial
							if (!selectedCondicionVehic
									.equals(getMsg("param.rv.condicionvehic.ningunodes"))) {
								dcv.setDjvehicularId(djvehicularId);
								detCondVehicId = vehicularBo
										.guardaDetCondVehiculo(dcv);
							}
						} else {

							/** Guardado definitivo de la DJ */
							djvehicular.setDjvehicularId(djvehicularId);
							djvehicular.setVehiculoId(vehiculoId);

							djvehicular.setDjvehicularNro(djvehicularIdNro);

							// ESTADOS DE FISCALIZACION::
							if (getSessionManaged().isModuloFisca()) {
								if (fiscaAceptado
										.equals(Constante.FISCA_ACEPTADA_CONTRIB_NO)) {
									djvehicular
											.setFiscalizado(Constante.FISCALIZADO_SI);
									djvehicular
											.setFiscaAceptada(Constante.FISCA_ACEPTADA_NO);
									djvehicular
											.setFiscaCerrada(Constante.FISCA_CERRADA_NO);
								} else if (fiscaAceptado
										.equals(Constante.FISCA_ACEPTADA_CONTRIB_SI)) {
									djvehicular
											.setFiscalizado(Constante.FISCALIZADO_SI);
									djvehicular
											.setFiscaAceptada(Constante.FISCA_ACEPTADA_SI);
									djvehicular
											.setFiscaCerrada(Constante.FISCA_CERRADA_SI);
								} else if (fiscaAceptado
										.equals(Constante.FISCA_NOACEPTADA_CERRADA)) {
									djvehicular
											.setFiscalizado(Constante.FISCALIZADO_SI);
									djvehicular
											.setFiscaAceptada(Constante.FISCA_ACEPTADA_NO);
									djvehicular
											.setFiscaCerrada(Constante.FISCA_CERRADA_SI);
								} else {
								}

							} else {
								djvehicular
										.setFiscalizado(Constante.FISCALIZADO_NO);
								djvehicular
										.setFiscaAceptada(Constante.FISCA_ACEPTADA_NO);
								djvehicular
										.setFiscaCerrada(Constante.FISCA_CERRADA_NO);
							}

							// ACTUALIZAR DJ VEHICULAR
							vehicularBo.actualizarDJVehicular(djvehicular);

							// elimina condicion especial
							if (selectedCondicionVehic
									.equals(getMsg("param.rv.condicionvehic.ningunodes"))) {
								vehicularBo
										.eliminarDetCondVehiculo(djvehicularId);
							} else {
								// Si existe actualizar, sino guardar
								if (detCondVehicId == -1) {
									dcv.setDjvehicularId(djvehicularId);
									detCondVehicId = vehicularBo
											.guardaDetCondVehiculo(dcv);
								} else {
									dcv.setDetalleCondicionId(detCondVehicId);
									vehicularBo.actualizaDetCondVehiculo(dcv);
								}
							}
						}
					}

					if (djvehicularId != -1) {

						estadoDecla = djvehicular.getEstado();

						// Transferentes
						RvTransferenciaPropiedad tp = new RvTransferenciaPropiedad();
						tp.setDjvehicularId(djvehicularId);
						tp.setTipo("T");
						tp.setFechaActualizacion(DateUtil
								.dateToSqlTimestamp(new Date()));
						tp.setUsuarioId(getSessionManaged().getUsuarioLogIn()
								.getUsuarioId());
						tp.setFechaRegistro(DateUtil
								.dateToSqlTimestamp(new Date()));
						tp.setEstado(Constante.ESTADO_ACTIVO);
						tp.setTerminal(getSessionManaged().getTerminalLogIn());

						/**
						 * Eliminando transferentes anteriores de la DJ, caso en
						 * que se actualize informacion de transferentes
						 */
						vehicularBo.eliminarTransferentesDJ(djvehicularId);
						vehicularBo.guardarTransferenteAdquiriente(
								lstTransferentes, tp);
						// Anexos
						RvSustentoVehicular sv = new RvSustentoVehicular();
						RvSustentoVehicularPK svp = new RvSustentoVehicularPK();
						svp.setDjvehicularId(djvehicularId);
						sv.setId(svp);
						sv.setFechaRegistro(DateUtil
								.dateToSqlTimestamp(new Date()));
						sv.setUsuarioId(getSessionManaged().getUsuarioLogIn()
								.getUsuarioId());
						sv.setTerminal(getSessionManaged().getTerminalLogIn());

						// INICIO ITANTAMANGO
						// if(archivo!=null&&archivo.getFile()!=null){
						// sv.setReferencia(archivo.getFileName());
						// sv.setContent_id(archivo.getContentId());
						// AnexoDjVehicular djV=new
						// AnexoDjVehicular(archivo.getFileName(),Util.getBytesFromFile(archivo.getFile()),String.valueOf(archivo.getContentId()));
						//
						// RepositoryManager.guardarContenido(djV);
						//
						//
						// }else{
						// sv.setReferencia(null);
						// sv.setContent_id(null);
						// }
						// FIN ITANTAMANGO

						vehicularBo.eliminarAnexosDJ(djvehicularId);
						vehicularBo.guardarDocAnexosDjVehicular(lstAnexos, sv);
						// Mensaje de éxito
						if (ev != null) {
							addInfoMessage(getMsg("rv.registrodjsatisfactorio"));
						}
						setAsociarImpresion(true);
						// Pasar datos a preliminar
						cargaDatosPreliminar();
					}
					guardarCorrecto = true;

				} catch (Exception ex) {
					addErrorMessage(getMsg("rv.djnoguardada"));
					System.out.println("ERROR: " + ex);
					// TODO : Controller exception
				}
			}
		}
	}

	private void cargaDatosPreliminar() {
		pre = new PreliminarDTO();

		pre.setAnhoAfectacion(this.anioDecla);
		pre.setNumeroDecla(numeroDecla);
		pre.setMotivoDecla(selectedMotivoDecla);
		pre.setFechaDecla(fechaDecla);
		pre.setPlacaVehic(placaVehic);
		pre.setNroMotorVehic(nroMotorVehic);
		pre.setFechaPrimeraInsReg(fechaPrimeraInsReg);
		pre.setAnioFabric(anioFabricVehic);
		pre.setCategoriaVehic(selectedCategoriaVehic);
		pre.setMarcaVehic(selectedMarcaVehic);
		pre.setModeloVehic(selectedModeloVehic);
		pre.setClaseVehic(selectedClaseVehic);
		pre.setFechaAdqui(fechaAdquisicion);
		pre.setTipoAdqui(selectedAdquisicion);
		pre.setTarjetaProp(tarjetaPropiedad);
		pre.setCondicionProp(selectedCondicionPropiedad);
		pre.setPorcentaje(porcentajePropiedad);
		pre.setTipoMoneda(selectedTipoMoneda);
		pre.setValorAdqui(valorAdquisicion);
		pre.setUsuario(getSessionManaged().getUsuarioLogIn().getNombreUsuario());

		getSessionMap().put("preliminarDTO", pre);
		getSessionMap().put("lstTransferentes", lstTransferentes);
		getSessionMap().put("lstAnexos", lstAnexos);

		// if (getAsociarImpresion() == true) {
		// guardarAuditoria(pre);
		// }
	}

	public void changeDatosCorrectos(ActionEvent ev) {
		datosCorrectos = true;
	}

	public void generarDJ(ActionEvent ev) {
		try {

			/***/
			guardar(null);

			if (guardarCorrecto) {
				/**
				 * Activando la DJ Vehicular recientemente creada
				 */
				djGeneradoCorrect = vehicularBo
						.activarDJVehicular(djvehicularId);

				if (djGeneradoCorrect) {
					if (ACCION.equals(VehicularCns.PENDIENTE_ACTUALIZACION)) {
						vehicularBo.cambiarEstadoDjv(oldDjvehicularId,
								Constante.ESTADO_INACTIVO);
						vehicularBo.cambiarFlagDjAnio(oldDjvehicularId,
								Constante.ESTADO_INACTIVO);
						getSessionMap().put("hitoricovehiculodj.djvId",
								djvehicularId);
					} else if (ACCION.equals(VehicularCns.INSCRIPCION)) {
						/**
						 * Obteniendo la DJ del vendedor de la cual se ha
						 * obtenido los datos para la inscripcion de DJ del
						 * comprador
						 */
						Object obj = getSessionMap().get(
								"nuevadjregistro.djvId");
						if (obj != null) {
							int djvId = Integer.parseInt(String.valueOf(obj));
							RvDjvehicular djv = vehicularBo
									.findDjVehicularById(djvId);

							if (djv.getEstado().equals("1")
									&& djv.getRvMotivoDeclaracionId() == Constante.MOTIVO_DECLARACION_DESCARGO
											.intValue()) {
								// Vehiculo descargado y no hay problema
							} else {
								/**
								 * Aqui se debe de crear la dj de descargo y
								 * para que cuando el comprador venga a
								 * registrar su descargo genere las dj de
								 * descargo para los demas años afectados
								 */
								// vehicularBo.cambiarEstadoDjv(djv.getDjvehicularId(),
								// getMsg("param.rv.estadopendientedescargo"));
								/** modificando la dj vehicular del vendedor */
								djv.setFechaDescargo(new Timestamp(this
										.getFechaAdquisicion().getTime()));
								djv.setAnnoAfectacion(DateUtil
										.obtenerAnioSegunFecha(this
												.getFechaAdquisicion()));
								djv.setEstado(Constante.ESTADO_PENDIENTE_DESCARGO);

								vehicularBo.guardarDJVehicular(djv);
							}

						}
					}
					// addInfoMessage(getMsg("rv.djgeneradasatisfactoriamente"));
					/**
					 * Aquí valida si la DJ proviene de un proceso de Fiscalizacion(Cartera SUNARP)
					 */
					DatosNecesariosDeclaracionDTO detdj = vehicularBo.obtenerReqDj(djvehicularId);

					if (detdj!=null && detdj.getReqId() > 0 ) {
						setEsMasivaFiscalizacion(true);
						setPersona(detdj.getPersonaId());
						setFechaCompra(detdj.getFechaAdquisicion());
						setPlaca(detdj.getPlaca());
						setNroReq(detdj.getNroReq());
						setRequerimiento_id(detdj.getReqId());
						setLote_vehicular_id(detdj.getLoteId());
						
						addInfoMessage(getMsg("Se ha generado la declaración jurada satisfactoriamente. El vehículo identificado con placa de rodaje Nº "
								+ detdj.getPlaca()
								+ ", se encuentra en proceso de fiscalización dentro del Lote Nº "
								+ detdj.getLoteId()));
					} else {
						
						addInfoMessage(getMsg("rv.djgeneradasatisfactoriamente"));
					}

				} else {
					addErrorMessage(getMsg("rv.djnogenerada"));
				}
			}
			guardarCorrecto = false;
		} catch (Exception ex) {
			WebMessages.messageError(ex.getMessage());
		}
	}

	public void redirecGenerado(ActionEvent ev) {
		try {
			if (ACCION.equals(VehicularCns.PENDIENTE_ACTUALIZACION)) {
				Object obj = getSessionMap().get("paramVehicDjHist");
				if (obj != null) {
					BuscarVehicularDTO vehiculoDJ = (BuscarVehicularDTO) obj;
					vehiculoDJ.setDjVehicularId(djvehicularId);
					vehiculoDJ.setNroMotor(nroMotorVehic);
					vehiculoDJ.setFechaInsReg(fechaPrimeraInsReg);
					vehiculoDJ.setCategoria(selectedCategoriaVehic);
					vehiculoDJ.setMarca(selectedMarcaVehic);
					vehiculoDJ.setModelo(selectedModeloVehic);
					vehiculoDJ.setMotivoDecla(selectedMotivoDecla);
					vehiculoDJ.setFecha(fechaDecla);
					vehiculoDJ.setFechaInsReg(fechaPrimeraInsReg);
					vehiculoDJ.setAnioFabric(anioFabricVehic);
					vehiculoDJ.setMotivoDeclaId(selectedMotivoDeclaId);
					// vehiculoDJ.setDjvehicularNro(djvehicularNro);
				}
				if (getEsMasivaFiscalizacion() == Boolean.TRUE) {

					setEsMasivaFiscalizacionImpresion(true);
					/**
					 * GENERACIÓN DE DETERMINACION Y DEUDA IMP. VEHICULAR::
					 */
						determinacion.generarDeterminacion(djvehicularId,getSessionManaged().getUsuarioLogIn().getUsuarioId(), getSessionManaged().getTerminalLogIn());

					/**
					 * GENERACIÓN DE RESOL.DE DETERMINACIÓN IMP. VEHICULAR::
					 */
						DtDeterminacion det =vehicularBo.obtenerDeterminacionByDj(djvehicularId,getPersona());
						setDeterminacion_id(det.getDeterminacionId());
					if (det.getFiscalizado().equals("1")&& det.getFiscaAceptada().equals("0")&& det.getFiscaCerrada().equals("1")){
						guardarRd();
					}
				}else{
				getSessionManaged().setPage(
						"/sisat/vehicular/historicovehiculodj.xhtml");
				sendRedirectPrincipalListener();
				}
			} else {
				// getSessionManaged().setPage("/sisat/vehicular/buscarvehicular.xhtml");
				// sendRedirectPrincipalListener();
				if (getEsMasivaFiscalizacion() == Boolean.TRUE) {
					setEsMasivaFiscalizacionImpresion(true);
					/**
					 * GENERACIÓN DE DETERMINACION Y DEUDA IMP. VEHICULAR::
					 */
						determinacion.generarDeterminacion(djvehicularId,getSessionManaged().getUsuarioLogIn().getUsuarioId(), getSessionManaged().getTerminalLogIn());
					/**
					 * GENERACIÓN DE RESOL.DE MULTA IMP. VEHICULAR::
					 */
						this.obligacionDTO = new ObligacionDTO();
						
						obligacionDTO.setConceptoDescripcion("MULTAS");
						obligacionDTO.setPersonaId(persona);
						obligacionDTO.setConceptoDescripcion("No presentar las Declaraciones que contengan la determinación de la deuda tributaria dentro de los plazos establecidos");
						obligacionDTO.setConceptoId(12);
						obligacionDTO.setSubConceptoId(126);
						obligacionDTO.setConceptoIdTributoReferencia(2);
						obligacionDTO.setCodigoPlacaReferencia(getPlaca());// **
						obligacionDTO.setFechaAdquision(getFechaCompra());// **
						obligacionDTO.setGlosa("");
						obligacionDTO.setNroRequerimiento(getNroReq());// **
						obligacionDTO.setNroActa("");// **
						obligacionDTO.setContexto(1);
						obligacionDTO.setPresentoDocumentos(true);
						obligacionDTO.setUnidadId(64);

						generarMulta();

					/**
					 * GENERACIÓN DE RESOL.DE DETERMINACIÓN IMP. VEHICULAR::
					 */
						DtDeterminacion det =vehicularBo.obtenerDeterminacionByDj(djvehicularId,getPersona());
						setDeterminacion_id(det.getDeterminacionId());
					if (det.getFiscalizado().equals("1")&& det.getFiscaAceptada().equals("0")&& det.getFiscaCerrada().equals("1")){
						guardarRd();
					}

				} else {
					getSessionManaged().setPage(
							"/sisat/vehicular/buscarvehicular.xhtml");
					sendRedirectPrincipalListener();
				}
			}
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
			// TODO : Controller exception
		}
	}

	private boolean validaDatosNulos() {
		boolean valido = true;
		try {
			if (placaVehic == null || placaVehic.trim().isEmpty()) {
				valido = false;
				addErrorMessage(getMsg("rv.placa") + ": "
						+ getMsg("rv.campovacioverifique"));
			}
			if (nroMotorVehic == null || nroMotorVehic.trim().isEmpty()) {
				valido = false;
				addErrorMessage(getMsg("rv.nromotor") + ": "
						+ getMsg("rv.campovacioverifique"));
			}
			if (fechaPrimeraInsReg == null) {
				valido = false;
				addErrorMessage(getMsg("rv.fechaprimerainsreg") + ": "
						+ getMsg("rv.campovacioverifique"));
			}
			if (anioFabricVehic == null || anioFabricVehic.trim().isEmpty()) {
				valido = false;
				addErrorMessage(getMsg("rv.aniofabricacion") + ": "
						+ getMsg("rv.campovacioverifique"));
			}
			if (selectedClaseVehic.isEmpty()) {
				valido = false;
				addErrorMessage(getMsg("rv.clase") + ": "
						+ getMsg("rv.campovacioverifique"));
			}
			if (selectedCategoriaVehic.isEmpty()) {
				valido = false;
				addErrorMessage(getMsg("rv.categoria") + ": "
						+ getMsg("rv.campovacioverifique"));
			}
			if (selectedMarcaVehic.isEmpty()) {
				valido = false;
				addErrorMessage(getMsg("rv.marca") + ": "
						+ getMsg("rv.campovacioverifique"));
			}
			if (selectedModeloVehic.isEmpty()) {
				valido = false;
				addErrorMessage(getMsg("rv.modelo") + ": "
						+ getMsg("rv.campovacioverifique"));
			}
			if (selectedCondicionVehic.isEmpty()) {
				valido = false;
				addErrorMessage(getMsg("rv.condicionespecialvehic") + ": "
						+ getMsg("rv.campovacioverifique"));
			}
			if (selectedAdquisicion.isEmpty()) {
				valido = false;
				addErrorMessage(getMsg("rv.tipoadquisicion") + ": "
						+ getMsg("rv.campovacioverifique"));
			}
			if (fechaAdquisicion == null) {
				valido = false;
				addErrorMessage(getMsg("rv.fechaadquisicion") + ": "
						+ getMsg("rv.campovacioverifique"));
			}
			if (selectedCondicionPropiedad.isEmpty()) {
				valido = false;
				addErrorMessage(getMsg("rv.condicionpropiedad") + ": "
						+ getMsg("rv.campovacioverifique"));
			}
			if (porcentajePropiedad == null
					|| porcentajePropiedad.trim().isEmpty()) {
				valido = false;
				addErrorMessage(getMsg("rv.porcentajepropiedad") + ": "
						+ getMsg("rv.campovacioverifique"));
			}
			if (selectedTipoMoneda.isEmpty()) {
				valido = false;
				addErrorMessage(getMsg("gn.tipomoneda") + ": "
						+ getMsg("rv.campovacioverifique"));
			}
			if (valorAdquisicion == null || valorAdquisicion.trim().isEmpty()) {
				valido = false;
				addErrorMessage(getMsg("rv.valoradquisicion") + ": "
						+ getMsg("rv.campovacioverifique"));
			}

			// if(mapRvMotivoDecla.get(selectedMotivoDecla) == 1)
			// {
			if (selectedNotaria.isEmpty()) {
				valido = false;
				addErrorMessage(getMsg("rv.notaria") + ": "
						+ getMsg("rv.campovacioverifique"));
			}
			// }
			if (lstAnexos.isEmpty()) {
				valido = false;
				addErrorMessage(getMsg("rv.debeingresardocanexo"));
			}
			// vehículo condición especial
			if (!selectedCondicionVehic
					.equals(getMsg("param.rv.condicionvehic.ningunodes"))) {
				if (selectedTipoDoc.isEmpty()) {
					valido = false;
					addErrorMessage(getMsg("gn.tipodoc") + ": "
							+ getMsg("rv.campovacioverifique"));
				}
				if (nroDoc == null || nroDoc.isEmpty()) {
					valido = false;
					addErrorMessage(getMsg("gn.nrodoc") + ": "
							+ getMsg("rv.campovacioverifique"));
				}
				if (selectedTipoInafec.isEmpty()) {
					valido = false;
					addErrorMessage(getMsg("rv.tipoinafectacion") + ": "
							+ getMsg("rv.campovacioverifique"));
				}
				if (valorInafec == null || valorInafec.isEmpty()) {
					valido = false;
					addErrorMessage(getMsg("rv.valorinafect") + ": "
							+ getMsg("rv.campovacioverifique"));
				}
			}
		} catch (Exception ex) {
			valido = false;
			addErrorMessage(getMsg("rv.errorvalidadatos"));
			System.out.println("ERROR: " + ex);
		}
		return valido;
	}

	private boolean validaLogicaNegocio() {
		boolean valido = true;
		try {
			// BigDecimal vad = new BigDecimal(valorAdquisicion);
			// if(vad.intValue() == 0 ){
			// addErrorMessage("El valor de adquisición debe ser mayor a cero");
			// valido = false;
			// }
			// Fecha adquisición <= Fecha declaración <= Fecha actual
			// (sistema)
			Calendar fechaAdqui = new GregorianCalendar();
			fechaAdqui.setTime(fechaAdquisicion);
			Long fechaAdquiL = fechaAdqui.getTimeInMillis();

			Calendar fechaDec = new GregorianCalendar();
			fechaDec.set(fechaDec.get(Calendar.YEAR),
					fechaDec.get(Calendar.MONTH),
					fechaDec.get(Calendar.DAY_OF_MONTH));
			Long fechaDecL = fechaDec.getTimeInMillis();

			Calendar fechaSis = new GregorianCalendar();
			fechaSis.setTime(new Date());
			Long fechaSisL = fechaSis.getTimeInMillis();

			if (fechaDecL < fechaAdquiL) {
				addErrorMessage(getMsg("rv.fechaadquimayorfechadecla"));
				valido = false;
			}

			if (fechaDecL > fechaSisL) {
				addErrorMessage(getMsg("rv.fechadeclamayorfechactual"));
				valido = false;
			}

			// Año de primera inscripcion en registros
			// Debe ser mayor que el año de fabricación
			Calendar cal = Calendar.getInstance();
			cal.setTime(fechaPrimeraInsReg);
			if (cal.get(Calendar.YEAR) < Integer.parseInt(anioFabricVehic)) {
				addErrorMessage(getMsg("rv.aniofabricmenoranioregistrospub"));
				valido = false;
			}

			if (Integer.parseInt(anioFabricVehic) > Integer.parseInt(DateUtil
					.obtenerAnioActual())) {
				addErrorMessage(getMsg("rv.aniofabricacion") + ": "
						+ getMsg("gn.noescorrecto"));
				valido = false;
			}

			// Fecha inscripción en registros <= Fecha actual
			Long fechaInsReg = cal.getTimeInMillis();
			if (fechaInsReg > fechaSisL) {
				addErrorMessage(getMsg("rv.fechainsregmayorfechactual"));
				valido = false;
			}

			// vehículo condición especial
			if (!selectedCondicionVehic
					.equals(getMsg("param.rv.condicionvehic.ningunodes"))) {
				if (fechaInicial.getTime() >= fechaFinal.getTime()) {
					addErrorMessage(getMsg("rv.fechainimayorfechafin"));
					valido = false;
				}
			}
		} catch (Exception ex) {
			valido = false;
			addErrorMessage(getMsg("rv.errorvalidadatos"));
			System.out.println("ERROR: " + ex);
		}
		return valido;
	}

	// INICIO IVO

	private ArrayList<FileUpload> lstArchivo = new ArrayList<FileUpload>();

	public ArrayList<FileUpload> getLstArchivo() {
		return lstArchivo;
	}

	public void setLstArchivo(ArrayList<FileUpload> lstArchivo) {
		this.lstArchivo = lstArchivo;
	}

	private FileUpload archivo;

	public FileUpload getArchivo() {
		return archivo;
	}

	public void setArchivo(FileUpload archivo) {
		this.archivo = archivo;
	}

	public void setProperty(FileUpload archivo) {
		lstArchivo.add(archivo);
		// setArchivo(archivo);
		// setLstArchivo(lstArchivo)
	}

	public void setParameters() {
		String paramParentFileUpload = FacesUtil
				.getRequestParameter("paramParentFileUpload");
		FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(),
				"paramParentFileUpload", paramParentFileUpload);
	}

	private DocumentoSustentatorioDTO currentItem = new DocumentoSustentatorioDTO();

	public DocumentoSustentatorioDTO getCurrentItem() {
		return currentItem;
	}

	public void setCurrentItem(DocumentoSustentatorioDTO currentItem) {
		this.currentItem = currentItem;
	}

	// FIN IVO

	/*
	 * Se agregaron estas líneas de código,para registrar operaciones en la
	 * tabla gn_auditoria_operacion,para el Reporte del Dpto. de
	 * Servicios.-29.01.2015
	 */
	// public void guardarAuditoria(PreliminarDTO pre){
	//
	// auditoria = new GnAuditoriaOperacion();
	// try {
	// int anio_dj = Integer.parseInt(pre.getAnhoAfectacion());
	//
	// auditoria.setTablaNombre("rv_djvehicular");
	// auditoria.setPersonaId(getSessionManaged().getContribuyente().getPersonaId());
	// auditoria.setContribuyente(getSessionManaged().getContribuyente().getApellidosNombres());
	// auditoria.setAnio(anio_dj);
	// auditoria.setPlaca(pre.getPlacaVehic());
	// auditoria.setCodigoOperacion(pre.getNumeroDecla());
	// auditoria.setTipoOperacion(Constante.OPERACION_IMPRESION);
	// auditoria.setEstado(Constante.ESTADO_PENDIENTE_ACTUALIZACION);
	// auditoria.setMotivoDeclaracionId(Constante.MOTIVO_DECLARACION_ACTUALIZA);
	// auditoria.setUsuarioId(getSessionManaged().getUsuarioLogIn().getUsuarioId());
	// auditoria.setTerminalRegistro(getSessionManaged().getTerminalLogIn());
	//
	// vehicularBo.guardarOperacionAuditoria(auditoria);
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// }

	public void imprimirEstadoCuenta() throws SQLException {
		java.sql.Connection connection = null;
		try {
			Integer persona = getSessionManaged().getContribuyente()
					.getPersonaId();
			CrudServiceBean connj = CrudServiceBean.getInstance();
			connection = connj.connectJasper();

			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("persona_id", persona);
			parameters.put("ruta_image",
					SATWEBParameterFactory.getPathReporteImagenes());
			parameters.put("SUBREPORT_DIR",
					SATWEBParameterFactory.getPathReporte());
			parameters.put("responsable", getSessionManaged().getUsuarioLogIn()
					.getNombreUsuario());
			parameters.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
			JasperPrint jasperPrint = JasperFillManager
					.fillReport(
							(SATWEBParameterFactory.getPathReporte() + "ec_estado_cuenta.jasper"),
							parameters, connection);
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, output);
			HttpServletResponse response = (HttpServletResponse) FacesContext
					.getCurrentInstance().getExternalContext().getResponse();
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition",
					"attachment;filename=ec_estado_cuenta" + persona + ".pdf");
			response.setContentLength(output.size());
			ServletOutputStream servletOutputStream = response
					.getOutputStream();
			servletOutputStream.write(output.toByteArray(), 0, output.size());
			servletOutputStream.flush();
			servletOutputStream.close();
			FacesContext.getCurrentInstance().responseComplete();

		} catch (Exception jre) {
			jre.printStackTrace();
			WebMessages.messageFatal(jre);
		} finally {
			try {
				if (connection != null) {
					connection.close();
					connection = null;
				}
			} catch (Exception e) {
			}
		}
	}

	public void generarMulta() throws Exception {

		try {
			Calendar calendar = Calendar.getInstance();
			Integer diasHabiles = obligacionBo.obtenerDiasHabiles(
					obligacionDTO.getFechaAdquision(), calendar.getTime());
			if (diasHabiles > 5) {
				/** Generando las multas */
				obligacionBo.generarMulta(getSessionManaged()
						.getContribuyente().getPersonaId(), obligacionDTO);

				Integer loteId = generalBo
						.ObtenerCorrelativoTabla("cc_lote", 1);

				obligacionBo.generarResolucionMulta(loteId - 1,
						getSessionManaged().getContribuyente().getPersonaId(),
						obligacionDTO.getUnidadId(), getUser().getUsuarioId(),
						getUser().getTerminal(),
						obligacionDTO.getSubConceptoId());

				/**
				 * Buscando las multas recientemente generadas tambien trae
				 * multas pendientes que contienen el mismo tipo de multa
				 */
				lstMultaDTOs = obligacionBo.buscarMultas(obligacionDTO);

				sumarMultas(lstMultaDTOs);

				Integer unidadId = obligacionDTO.getUnidadId();

				/** Reiniciando el DTO de obligaciones */
				this.obligacionDTO = new ObligacionDTO();
				this.obligacionDTO.setPersonaId(getSessionManaged()
						.getContribuyente().getPersonaId());
				obligacionDTO.setConceptoId(12);
				obligacionDTO.setConceptoDescripcion("MULTAS");
				obligacionDTO.setUnidadId(unidadId);

			} else {
				// addErrorMessage(getMsg("Aun no le corresponde generarle multa."));
				// boolean mensaje
				setNoGenerarMulta(true);
			}

		} catch (SisatException e) {
			WebMessages.messageError(e.getMessage());
		}

	}

	public void sumarMultas(List<MultaDTO> lstMultaDTOs) {

		totalAPagar = new BigDecimal(0);
		totalDscto = new BigDecimal(0);
		totalSubTotal = new BigDecimal(0);
		totalInteres = new BigDecimal(0);
		totalMonto = new BigDecimal(0);

		for (MultaDTO m : lstMultaDTOs) {
			totalAPagar = totalAPagar.add(m.getMontoConDscto());
			totalDscto = totalDscto.add(m.getMontoDescuento());
			totalSubTotal = totalSubTotal.add(m.getMontoSinDscto());
			totalInteres = totalInteres.add(m.getInteres());
			totalMonto = totalMonto.add(m.getMonto());
		}
	}

	public void generacionRmPendientesPagoFiscalizacion() {
				try {
//					if (listVehicularDTOs != null || listDjPredials != null) {
						java.sql.Connection connection = null;
						// java.sql.Connection connec = null;
						try {
							CrudServiceBean connj = CrudServiceBean.getInstance();
							connection = connj.connectJasper();
							// CrudServiceBean conxi = CrudServiceBean.getInstance();
							// connec = conxi.connectImage();
							String path_context = FacesContext.getCurrentInstance()
									.getExternalContext().getRealPath("/");
							String path_report = path_context + "/sisat/reportes/";
							String path_imagen = path_context
									+ "/sisat/reportes/imagen/";
							HashMap<String, Object> parameters = new HashMap<String, Object>();
							// parameters.put("SUBREPORT_CONNECTION", connec);
							parameters.put("p_persona_id",getPersona());
							parameters.put("p_responsable", getSessionManaged()
									.getUsuarioLogIn().getNombreUsuario());
							parameters.put("ruta_imagen", path_imagen);
							// parameters.put("p_periodo",
							// findCcLoteItem.getAnnoLote());
							// parameters.put("REPORT_LOCALE", new Locale("en",
							// "ENGLISH"));
							JasperPrint jasperPrint = JasperFillManager
									.fillReport(
											(SATWEBParameterFactory.getPathReporte() + "rm_resolucion_multa_fiscalizacion.jasper"),
											parameters, connection);
							ByteArrayOutputStream output = new ByteArrayOutputStream();
							JasperExportManager.exportReportToPdfStream(jasperPrint,
									output);
							HttpServletResponse response = (HttpServletResponse) FacesContext
									.getCurrentInstance().getExternalContext()
									.getResponse();
							response.setContentType("application/pdf");
							response.addHeader("Content-Disposition",
									"attachment;filename=rm_sinpagar_fiscalizacion.pdf");
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
							}
						}
//					}
				} catch (Exception e) {
					e.printStackTrace();
					WebMessages.messageFatal(e);
				}
			}

		public void guardarRd(){
		  try{
				Integer lote_id=generalBo.ObtenerCorrelativoTabla("cc_lote", 1);
				
			    ccLote.setLoteId(lote_id);
			    ccLote.setTipoLoteId(Constante.TIPO_LOTE_COBRANZA_ID);//--CONTROL DE LA DEUDA
			    ccLote.setAnnoLote(DateUtil.getAnioActual());
			   // ccLote.setMontoInicio(BigDecimal.valueOf(montoMinimo));
			    //ccLote.setMontoFin(1000000000000);
			    
			    ccLote.setFechaLote(DateUtil.getCurrentDate());
			    ccLote.setEstado(Constante.ESTADO_ACTIVO);
			    ccLote.setFlagGeneracion(Constante.FLAG_GENERACION_PROGRAMADA);
			    ccLote.setTipoLoteGeneracion(Constante.TIPO_LOTE_GENERACION_PRELIMINAR);
			    ccLote.setImpresion(getLote_vehicular_id());//Indica el número de Lote Vehicular asociado.
			    ccLote= controlycobranzaBo.create(ccLote);	
				 	
				Integer lote_acto_id=generalBo.ObtenerCorrelativoTabla("cc_lote_acto", 1);
				CcLoteActoPK id= new CcLoteActoPK();
				id.setLoteActoId(lote_acto_id);
				id.setLoteId(ccLote.getLoteId());
				ccLoteActo.setId(id);
				ccLoteActo.setTipoActoId(Constante.TIPO_ACTO_RESOLUCION_DETERMINACION_ID);
				ccLoteActo.setEstado(Constante.ESTADO_ACTIVO);
				ccLoteActo = controlycobranzaBo.create(ccLoteActo);
				
				Integer lote_concepto_id=generalBo.ObtenerCorrelativoTabla("cc_lote_concepto", 1);
				CcLoteConcepto ccLoteConcepto= new CcLoteConcepto();
				ccLoteConcepto.setConceptoId(Constante.CONCEPTO_VEHICULAR);
				CcLoteConceptoPK id1 = new CcLoteConceptoPK();
				id1.setLoteActoId(ccLoteActo.getId().getLoteActoId());
				id1.setLoteId(ccLote.getLoteId());
				id1.setLoteConceptoId(lote_concepto_id);
				ccLoteConcepto.setId(id1);
				ccLoteConcepto.setSubconceptoId(20);
				ccLoteConcepto.setEstado(Constante.ESTADO_ACTIVO);
				controlycobranzaBo.create(ccLoteConcepto);
				
				findCcLoteItem=controlycobranzaBo.getFindCcLote(lote_id);
				generacionValores();
				
			 }catch(Exception e){
					e.printStackTrace();
					WebMessages.messageFatal(e);			
			 }
		}
	
		public void generacionValores(){
			try{
				//Correccion : validacion de registrar el lote 
				if(findCcLoteItem!=null && findCcLoteItem.getLoteId()!=null &&findCcLoteItem.getLoteId()>0){
					controlycobranzaBo.registrarActoRDVehicularIndividual(findCcLoteItem.getLoteId(),getPersona(),getRequerimiento_id(),getDeterminacion_id());
					
					ccLote.setLoteId(findCcLoteItem.getLoteId());
				    ccLote.setTipoLoteId(Constante.TIPO_LOTE_COBRANZA_ID);//--CONTROL DE LA DEUDA
				    ccLote.setAnnoLote(DateUtil.getAnioActual());//periodo?
				    //ccLote.setMontoInicio(BigDecimal.valueOf(montoMinimo));montoMinimo?
				    //ccLote.setMontoFin(1000000000000);
				    
				    ccLote.setFechaLote(DateUtil.getCurrentDate());
				    ccLote.setFlagGeneracion(Constante.FLAG_GENERACION_FINALIZADA);
				    ccLote.setTipoLoteGeneracion(Constante.TIPO_LOTE_GENERACION_FINAL);
				    ccLote= controlycobranzaBo.udpate(ccLote);	
				    
					ccLote=controlycobranzaBo.findCcLote(getFindCcLoteItem().getLoteId());
			 		lote_id=ccLote.getLoteId();
			 		
					findCcLoteItem=controlycobranzaBo.getFindCcLote(findCcLoteItem.getLoteId());
					
				}else{
					//WebMessages.messageInfo("Registre el lote de RD de Vehicular a generar");	
				}
			}catch(Exception e){
				e.printStackTrace();
				WebMessages.messageFatal(e);
			}
		}
		
		public void generacionRd(){
			try{
				if(findCcLoteItem!=null){
					java.sql.Connection connection = null;
					try {
						CrudServiceBean connj = CrudServiceBean.getInstance();
						connection = connj.connectJasper();

						String path_context = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
						String path_report = path_context + "/sisat/reportes/";
						String path_imagen = path_context + "/sisat/reportes/imagen/";

						HashMap<String, Object> parameters = new HashMap<String, Object>();
						parameters.put("p_lote_id", findCcLoteItem.getLoteId());
						parameters.put("ruta_imagen", path_imagen);
						//parameters.put("p_periodo", findCcLoteItem.getAnnoLote());
						parameters.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
			
						JasperPrint jasperPrint = JasperFillManager.fillReport(
								(SATWEBParameterFactory.getPathReporte() + "resolucion_determinacion_vehicular.jasper"),
								parameters, connection);

						ByteArrayOutputStream output = new ByteArrayOutputStream();
						JasperExportManager.exportReportToPdfStream(jasperPrint, output);
						
						HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
						response.setContentType("application/pdf");
						response.addHeader("Content-Disposition","attachment;filename=resolucion_determinacion_vehicular.pdf");
						response.setContentLength(output.size());
						ServletOutputStream servletOutputStream = response.getOutputStream();
						servletOutputStream.write(output.toByteArray(), 0, output.size());
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
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
				WebMessages.messageFatal(e);			
			}
		}
	
		public void salirPanel(ActionEvent ev) {
			 getSessionManaged().setPage("/sisat/vehicular/buscarvehicular.xhtml");
			 sendRedirectPrincipalListener();
		}
		
	public List<SelectItem> getLstClaseVehiculo() {
		return lstClaseVehiculo;
	}

	public List<SelectItem> getLstCategoriaVehiculo() {
		return lstCategoriaVehiculo;
	}

	public List<SelectItem> getLstMarcaVehiculo() {
		return lstMarcaVehiculo;
	}

	public List<SelectItem> getLstCarroceria() {
		return lstCarroceria;
	}

	public List<SelectItem> getLstTransmision() {
		return lstTransmision;
	}

	public List<SelectItem> getLstTraccion() {
		return lstTraccion;
	}

	public List<SelectItem> getLstTipoMotor() {
		return lstTipoMotor;
	}

	public List<SelectItem> getLstModelo() {
		return lstModelo;
	}

	public String getSelectedCondicionVehic() {
		return selectedCondicionVehic;
	}

	public void setSelectedCondicionVehic(String selectedCondicionVehic) {
		this.selectedCondicionVehic = selectedCondicionVehic;
	}

	public List<SelectItem> getLstCondicionVehic() {
		return lstCondicionVehic;
	}

	public boolean isExisteVehiculo() {
		return existeVehiculo;
	}

	public Date getFechaPrimeraInsReg() {
		return fechaPrimeraInsReg;
	}

	public void setFechaPrimeraInsReg(Date fechaPrimeraInsReg) {
		this.fechaPrimeraInsReg = fechaPrimeraInsReg;
	}

	public Date getFechaAdquisicion() {
		return fechaAdquisicion;
	}

	public void setFechaAdquisicion(Date fechaAdquisicion) {
		this.fechaAdquisicion = fechaAdquisicion;
	}

	public Date getFechaDecla() {
		return fechaDecla;
	}

	public void setFechaDecla(Date fechaDecla) {
		this.fechaDecla = fechaDecla;
	}

	public List<SelectItem> getLstMotivoDecla() {
		return lstMotivoDecla;
	}

	public String getSelectedMotivoDecla() {
		return selectedMotivoDecla;
	}

	public void setSelectedMotivoDecla(String selectedMotivoDecla) {
		this.selectedMotivoDecla = selectedMotivoDecla;
	}

	public String getNumeroDecla() {
		return numeroDecla;
	}

	public void setNumeroDecla(String numeroDecla) {
		this.numeroDecla = numeroDecla;
	}

	public String getAnioDecla() {
		return anioDecla;
	}

	public void setAnioDecla(String anioDecla) {
		this.anioDecla = anioDecla;
	}

	public String getSelectedAdquisicion() {
		return selectedAdquisicion;
	}

	public void setSelectedAdquisicion(String selectedAdquisicion) {
		this.selectedAdquisicion = selectedAdquisicion;
	}

	public String getTarjetaPropiedad() {
		return tarjetaPropiedad;
	}

	public void setTarjetaPropiedad(String tarjetaPropiedad) {
		this.tarjetaPropiedad = tarjetaPropiedad;
	}

	public String getSelectedCondicionPropiedad() {
		return selectedCondicionPropiedad;
	}

	public void setSelectedCondicionPropiedad(String selectedCondicionPropiedad) {
		this.selectedCondicionPropiedad = selectedCondicionPropiedad;
	}

	public String getPorcentajePropiedad() {
		return porcentajePropiedad;
	}

	public void setPorcentajePropiedad(String porcentajePropiedad) {
		this.porcentajePropiedad = porcentajePropiedad;
	}

	public String getSelectedTipoMoneda() {
		return selectedTipoMoneda;
	}

	public void setSelectedTipoMoneda(String selectedTipoMoneda) {
		this.selectedTipoMoneda = selectedTipoMoneda;
	}

	public String getValorAdquisicion() {
		return valorAdquisicion;
	}

	public void setValorAdquisicion(String valorAdquisicion) {
		this.valorAdquisicion = valorAdquisicion;
	}

	public List<SelectItem> getLstAdquisicion() {
		return lstAdquisicion;
	}

	public List<SelectItem> getLstCondicionPropiedad() {
		return lstCondicionPropiedad;
	}

	public List<SelectItem> getLstTipoMoneda() {
		return lstTipoMoneda;
	}

	public String getNroMotorBuscar() {
		return nroMotorBuscar;
	}

	public void setNroMotorBuscar(String nroMotorBuscar) {
		this.nroMotorBuscar = nroMotorBuscar;
	}

	public String getPlacaBuscar() {
		return placaBuscar;
	}

	public void setPlacaBuscar(String placaBuscar) {
		this.placaBuscar = placaBuscar;
	}

	public String getNroMotorVehic() {
		return nroMotorVehic;
	}

	public void setNroMotorVehic(String nroMotorVehic) {
		this.nroMotorVehic = nroMotorVehic;
	}

	public String getPlacaVehic() {
		return placaVehic;
	}

	public void setPlacaVehic(String placaVehic) {
		this.placaVehic = placaVehic;
	}

	public String getAnioFabricVehic() {
		return anioFabricVehic;
	}

	public void setAnioFabricVehic(String anioFabricVehic) {
		this.anioFabricVehic = anioFabricVehic;
	}

	public String getSelectedClaseVehic() {
		return selectedClaseVehic;
	}

	public void setSelectedClaseVehic(String selectedClaseVehic) {
		this.selectedClaseVehic = selectedClaseVehic;
	}

	public String getSelectedCategoriaVehic() {
		return selectedCategoriaVehic;
	}

	public void setSelectedCategoriaVehic(String selectedCategoriaVehic) {
		this.selectedCategoriaVehic = selectedCategoriaVehic;
	}

	public String getSelectedMarcaVehic() {
		return selectedMarcaVehic;
	}

	public void setSelectedMarcaVehic(String selectedMarcaVehic) {
		this.selectedMarcaVehic = selectedMarcaVehic;
	}

	public String getSelectedModeloVehic() {
		return selectedModeloVehic;
	}

	public void setSelectedModeloVehic(String selectedModeloVehic) {
		this.selectedModeloVehic = selectedModeloVehic;
	}

	public String getSelectedTipoMotorVehic() {
		return selectedTipoMotorVehic;
	}

	public void setSelectedTipoMotorVehic(String selectedTipoMotorVehic) {
		this.selectedTipoMotorVehic = selectedTipoMotorVehic;
	}

	public String getSelectedCarroceriaVehic() {
		return selectedCarroceriaVehic;
	}

	public void setSelectedCarroceriaVehic(String selectedCarroceriaVehic) {
		this.selectedCarroceriaVehic = selectedCarroceriaVehic;
	}

	public String getSelectedTransmisionVehic() {
		return selectedTransmisionVehic;
	}

	public void setSelectedTransmisionVehic(String selectedTransmisionVehic) {
		this.selectedTransmisionVehic = selectedTransmisionVehic;
	}

	public String getSelectedTraccionVehic() {
		return selectedTraccionVehic;
	}

	public void setSelectedTraccionVehic(String selectedTraccionVehic) {
		this.selectedTraccionVehic = selectedTraccionVehic;
	}

	public String getPesoBrutoVehic() {
		return pesoBrutoVehic;
	}

	public void setPesoBrutoVehic(String pesoBrutoVehic) {
		this.pesoBrutoVehic = pesoBrutoVehic;
	}

	public String getCilindrosVehic() {
		return cilindrosVehic;
	}

	public void setCilindrosVehic(String cilindrosVehic) {
		this.cilindrosVehic = cilindrosVehic;
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

	public List<BuscarPersonaDTO> getLstTransferentes() {
		return lstTransferentes;
	}

	public List<AnexosDeclaVehicDTO> getLstAnexos() {
		return lstAnexos;
	}

	public String getEstadoDecla() {
		return estadoDecla;
	}

	public int getSelectedTipoMonedaId() {
		return selectedTipoMonedaId;
	}

	public void setSelectedTipoMonedaId(int selectedTipoMonedaId) {
		this.selectedTipoMonedaId = selectedTipoMonedaId;
	}

	public boolean isDatosCorrectos() {
		return datosCorrectos;
	}

	public void setDatosCorrectos(boolean datosCorrectos) {
		this.datosCorrectos = datosCorrectos;
	}

	public boolean isDjGeneradoCorrect() {
		return djGeneradoCorrect;
	}

	public boolean isDisabledDatosVehic() {
		return disabledDatosVehic;
	}

	public VehiculoExistenteDTO getVehiculoExistente() {
		return vehiculoExistente;
	}

	public String getValorAdquisicionDolares() {
		return valorAdquisicionDolares;
	}

	public void setValorAdquisicionDolares(String valorAdquisicionDolares) {
		this.valorAdquisicionDolares = valorAdquisicionDolares;
	}

	public List<DocumentoSustentatorioDTO> getLstDocSusten() {
		return lstDocSusten;
	}

	public int getSelectedMotivoDeclaId() {
		return selectedMotivoDeclaId;
	}

	public void setSelectedMotivoDeclaId(int selectedMotivoDeclaId) {
		this.selectedMotivoDeclaId = selectedMotivoDeclaId;
	}

	public Date getFechaInicial() {
		return fechaInicial;
	}

	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public Date getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public String getSelectedTipoDoc() {
		return selectedTipoDoc;
	}

	public void setSelectedTipoDoc(String selectedTipoDoc) {
		this.selectedTipoDoc = selectedTipoDoc;
	}

	public String getNroDoc() {
		return nroDoc;
	}

	public void setNroDoc(String nroDoc) {
		this.nroDoc = nroDoc;
	}

	public String getSelectedTipoInafec() {
		return selectedTipoInafec;
	}

	public void setSelectedTipoInafec(String selectedTipoInafec) {
		this.selectedTipoInafec = selectedTipoInafec;
	}

	public String getValorInafec() {
		return valorInafec;
	}

	public void setValorInafec(String valorInafec) {
		this.valorInafec = valorInafec;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public List<SelectItem> getLstTipoDoc() {
		return lstTipoDoc;
	}

	public GnTipoCambio getTipoCambio() {
		return tipoCambio;
	}

	public Integer getVehiculoId() {
		return vehiculoId;
	}

	public void setVehiculoId(Integer vehiculoId) {
		this.vehiculoId = vehiculoId;
	}

	public void setLstTransferentes(List<BuscarPersonaDTO> lstTransferentes) {
		this.lstTransferentes = lstTransferentes;
	}

	public String getPlacaVehicNueva() {
		return placaVehicNueva;
	}

	public void setPlacaVehicNueva(String placaVehicNueva) {
		this.placaVehicNueva = placaVehicNueva;
	}

	public String getPlacaVehicAnterior() {
		return placaVehicAnterior;
	}

	public void setPlacaVehicAnterior(String placaVehicAnterior) {
		this.placaVehicAnterior = placaVehicAnterior;
	}

	public String getGlosa() {
		return glosa;
	}

	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}

	public String getFiscaAceptado() {
		return fiscaAceptado;
	}

	public void setFiscaAceptado(String fiscaAceptado) {
		this.fiscaAceptado = fiscaAceptado;
	}

	public String getDjvehicularIdNro() {
		return djvehicularIdNro;
	}

	public void setDjvehicularIdNro(String djvehicularIdNro) {
		this.djvehicularIdNro = djvehicularIdNro;
	}

	public Boolean getAsociarImpresion() {
		return asociarImpresion;
	}

	public void setAsociarImpresion(Boolean asociarImpresion) {
		this.asociarImpresion = asociarImpresion;
	}

	public Boolean getEsMasivaFiscalizacion() {
		return esMasivaFiscalizacion;
	}

	public void setEsMasivaFiscalizacion(Boolean esMasivaFiscalizacion) {
		this.esMasivaFiscalizacion = esMasivaFiscalizacion;
	}

	public Boolean getEsMasivaFiscalizacionImpresion() {
		return esMasivaFiscalizacionImpresion;
	}

	public void setEsMasivaFiscalizacionImpresion(
			Boolean esMasivaFiscalizacionImpresion) {
		this.esMasivaFiscalizacionImpresion = esMasivaFiscalizacionImpresion;
	}

	public Integer getPersona() {
		return persona;
	}

	public void setPersona(Integer persona) {
		this.persona = persona;
	}

	public Date getFechaCompra() {
		return fechaCompra;
	}

	public void setFechaCompra(Date fechaCompra) {
		this.fechaCompra = fechaCompra;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getNroReq() {
		return nroReq;
	}

	public void setNroReq(String nroReq) {
		this.nroReq = nroReq;
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

	public Boolean getNoGenerarMulta() {
		return noGenerarMulta;
	}

	public void setNoGenerarMulta(Boolean noGenerarMulta) {
		this.noGenerarMulta = noGenerarMulta;
	}

	public ObligacionDTO getObligacionDTO() {
		if (obligacionDTO == null) {
			obligacionDTO = new ObligacionDTO();
		}
		return obligacionDTO;
	}

	public void setObligacionDTO(ObligacionDTO obligacionDTO) {
		this.obligacionDTO = obligacionDTO;
	}

	public CcLote getCcLote() {
		return ccLote;
	}

	public void setCcLote(CcLote ccLote) {
		this.ccLote = ccLote;
	}

	public CcLoteActo getCcLoteActo() {
		return ccLoteActo;
	}

	public void setCcLoteActo(CcLoteActo ccLoteActo) {
		this.ccLoteActo = ccLoteActo;
	}

	public FindCcLote getFindCcLoteItem() {
		return findCcLoteItem;
	}

	public void setFindCcLoteItem(FindCcLote findCcLoteItem) {
		this.findCcLoteItem = findCcLoteItem;
	}

	public Integer getLote_id() {
		return lote_id;
	}

	public void setLote_id(Integer lote_id) {
		this.lote_id = lote_id;
	}

	public Integer getRequerimiento_id() {
		return requerimiento_id;
	}

	public void setRequerimiento_id(Integer requerimiento_id) {
		this.requerimiento_id = requerimiento_id;
	}

	public Integer getLote_vehicular_id() {
		return lote_vehicular_id;
	}

	public void setLote_vehicular_id(Integer lote_vehicular_id) {
		this.lote_vehicular_id = lote_vehicular_id;
	}

	public Integer getDeterminacion_id() {
		return determinacion_id;
	}

	public void setDeterminacion_id(Integer determinacion_id) {
		this.determinacion_id = determinacion_id;
	}

}
