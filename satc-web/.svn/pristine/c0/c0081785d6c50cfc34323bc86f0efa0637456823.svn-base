package com.sat.sisat.coactiva.managed;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.richfaces.component.html.HtmlComboBox;
import org.richfaces.model.filter.Filter;
import org.richfaces.model.selection.SimpleSelection;

import com.sat.sisat.cobranzacoactiva.business.CobranzaCoactivaBoRemote;
import com.sat.sisat.cobranzacoactiva.dto.DetencionDeudaDTO;
import com.sat.sisat.cobranzacoactiva.dto.FindCcLoteDetalleActoExp;
import com.sat.sisat.cobranzacoactiva.dto.FindCcLoteExigible;
import com.sat.sisat.cobranzacoactiva.dto.FindCcRecHistorico;
import com.sat.sisat.cobranzacoactiva.dto.FindCcRecTipo;
import com.sat.sisat.common.business.GeneralBoRemote;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.SATWEBParameterFactory;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.controlycobranza.business.ControlyCobranzaBoRemote;
import com.sat.sisat.controlycobranza.dto.FindCcLoteDetalleActo;
import com.sat.sisat.controlycobranza.dto.FindCcRec;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.menus.business.MenuBoRemote;
import com.sat.sisat.menus.dto.SimpleMenuDTO;
import com.sat.sisat.obligacion.business.ObligacionBoRemote;
import com.sat.sisat.obligacion.dto.ObligacionDTO;
import com.sat.sisat.obligacion.dto.SubConceptoDTO;
import com.sat.sisat.persistence.CrudServiceBean;
import com.sat.sisat.persistence.entity.CcLote;
import com.sat.sisat.persistence.entity.CcLoteActo;
import com.sat.sisat.persistence.entity.CcLoteActoPK;
import com.sat.sisat.persistence.entity.GnSector;
import com.sat.sisat.persistence.entity.NoMotivoNotificacion;
import com.sat.sisat.persistence.entity.NoNotificador;
import com.sat.sisat.predial.business.RegistroPrediosBoRemote;
import com.sat.sisat.predial.dto.FindRpDjPredial;
import com.sat.sisat.vehicular.business.VehicularBoRemote;
import com.sat.sisat.vehicular.dto.BuscarVehicularDTO;

@ManagedBean
@ViewScoped
public class AcumulacionExpedienteManaged extends BaseManaged {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1673161260001450282L;
	@EJB
	CobranzaCoactivaBoRemote cobranzaCoactivaBo;
	@EJB
	ControlyCobranzaBoRemote controlycobranzaBo;
	private List<FindCcLoteDetalleActoExp> lista;
	private List<FindCcLoteDetalleActoExp> listaExpedienteAgrupados;
	private FindCcLoteDetalleActo selectedRol;
	private SimpleSelection selectedUsuario;
	private SimpleSelection simpleSelectedRol;
	private List<FindCcLoteExigible> lotes = new ArrayList<FindCcLoteExigible>();
	private FindCcLoteExigible findCcLoteItem = new FindCcLoteExigible();
	private int rowIndexSelected = -1;
	private Integer lote_id;
	private String nroValor;
	@EJB
	GeneralBoRemote generalBo;
	@EJB
	RegistroPrediosBoRemote prediosBo;
	private List<SelectItem> selectItems = new ArrayList<SelectItem>();
	private List<SelectItem> selectSector = new ArrayList<SelectItem>();
	private CcLote ccLote = new CcLote();
	private CcLoteActo ccLoteActo = new CcLoteActo();
	private String vendorFilter;
	private String sectorFilter;
	private boolean desactivaRecsInicio;
	private boolean booleanRecsMasiva;
	// VALORES AGRUPADOS POR PERSONA
	private FindCcLoteDetalleActoExp findCcActoAgrupadoItem = new FindCcLoteDetalleActoExp();
	// OBTENEMOS EL HISTORICO DE RECs POR DEUDA EXIGIBLE
	private List<FindCcRecHistorico> historico = new ArrayList<FindCcRecHistorico>();
	private FindCcLoteDetalleActoExp findCcActoItem = new FindCcLoteDetalleActoExp();
	private List<SelectItem> listaTipoRec = new ArrayList<SelectItem>();
	private String cmbTipoRec;
	private Integer tipoRecId;
	private HashMap<String, Integer> mapTipoRec = new HashMap<String, Integer>();
	private HtmlComboBox cmbNoTipoRec;
	private HtmlComboBox cmbNoTipoRecMasivo;
	// **//
	private Integer deudaExId;
	private Integer personaId;
	private Integer loteExId;
	private String numExpediente;
	private Integer loteAnteriorId;
	private boolean flagJustificacion = false;
	private String justificacionDesbloqueo;
	// GENERAMOS NUEVA(s) REC(s) PARA LA DEUDA EXIGIBLE ELEGIDA
	private FindCcRecHistorico findCcRecs = new FindCcRecHistorico();
	private Integer loteRecId;
	private Integer anioRec;
	private Integer personaRecId;
	private String nroExpedienteRec;
	private Integer tipoRec;
	private Integer tipoDocRec;
	private Integer actoRecId;
	private Integer anioDeuda;

	// PARA EL LAS REC MASIVAS POR LOTE
	private List<FindCcRecTipo> listaRecMasiva = new ArrayList<FindCcRecTipo>();
	private FindCcRecTipo findCcTipoRecs = new FindCcRecTipo();
	private Integer conceptoId;
	// private FindCcRecTipo findCcRecTipoItem=new FindCcRecTipo();
	// REGISTRAMOS INFO. DE NOTIFICACION DE REC
	private java.util.Date fechaEmisionRecMigradas;
	private java.util.Date fechaNotificacion;
	private String cmbValueNoNotificacion;
	private String cmbNotificador;
	private HtmlComboBox cmbNoNotificacion;
	private HtmlComboBox cmbHtmlNotificador;
	private Integer noNotificacionId;
	private Integer notificadorId;
	private HashMap<String, Integer> mapTipoNotificacion = new HashMap<String, Integer>();
	private HashMap<String, Integer> mapNotificador = new HashMap<String, Integer>();
	private List<SelectItem> lstTipoNotificacion = new ArrayList<SelectItem>();
	private List<SelectItem> lstSelectItemsNotificador = new ArrayList<SelectItem>();
	private Integer anioLote;
	private Integer loteExigibleId;
	private Integer tipoActoId;
	private Integer recId;
	private Integer codigoPersonaBuscar;
	private String placaVehiculoBuscar;
	private String codigoAnteriorPersona;
	/** para costas */
	private List<DetencionDeudaDTO> lstDetencionDeudaDTOs = new ArrayList<DetencionDeudaDTO>();
	private DetencionDeudaDTO detencionesDeudasItem = new DetencionDeudaDTO();
	private List<ObligacionDTO> listObligacionDTOs = new ArrayList<ObligacionDTO>();
	private List<ObligacionDTO> listObligGeneradasDTOs = new ArrayList<ObligacionDTO>();
	private List<BuscarVehicularDTO> listVehicularDTOs = new ArrayList<BuscarVehicularDTO>();
	private List<FindRpDjPredial> listDjPredials = new ArrayList<FindRpDjPredial>();
	private SubConceptoDTO subConceptoDTO;
	private List<SelectItem> listSubConceptoDTOItems = new ArrayList<SelectItem>();
	private HashMap<String, SubConceptoDTO> mapSupConcepto = new HashMap<String, SubConceptoDTO>();
	private ObligacionDTO obligacionDTO;
	List<SelectItem> listSelectItems = new ArrayList<SelectItem>();
	private String codicionAdministrado;
	private HtmlComboBox cmbCondicionAdministrado;
	// private HtmlFileUpload fileUpload;
	private BigDecimal montoTotal = new BigDecimal(0);
	private SimpleSelection selectObligacionDTO;
	private String nroPapeleta;
	private Integer factor = 1;
	private boolean flagValido = false;
	private boolean flagViewDetalle = false;
	private String cmbConcepto;
	private String cmbSubConcepto;
	private Integer subconceptoId;
	private String cmbTasa;
	private int modoGastoMonto = 1;
	private SimpleSelection selectedRowLotes;
	private boolean disableBtnGeneraRec = false;
	private boolean disableBtnGeneraCostas = false;
	private Integer nroRegistrosGenerar;
	private Integer papeletaId;
	// CONSTANTES
	private int MULTAS = 12;
	private int COSTAS = 5;
	private int GASTOS = 6;
	private int EPND = 11;
	private int MULTAS_DRTPE = 13;
	// Cancelacion de Expedientes Coactivos
	private String nroResolucion;
	private String motivoCancelacion;
	private String estadoCancelacion;
	private HtmlComboBox cmbHtmlEstadoSusp;
	// INICIO PERMISOS PARA EL MODULO -=CRAMIREZ=-
	 	private List<SimpleMenuDTO> listPermisosSubmenu = new ArrayList<SimpleMenuDTO>();	 	
	 	private boolean permisoBuscar;
	 	private boolean permisoVerDetalle;
	 	private boolean permisoModificarActualizar;
	 	private boolean permisoGenerarRECInicio;
	 	private boolean permisoGenerarMasRECs;
	// FIN PERMISOS PARA EL MODULO
	/**
	 * 0: Suspension Definitiva; 1: Activo 2:Suspension Temporal 9: Eliminado
	 * solo por el sistema para las REC de suspension cuyo contribuyente se
	 * retiro sin pagar
	 */
	// Para impresion de recs
	private Integer tipoDocumentoAgrupados;
	private Integer tipoDocumento;

	@EJB
	VehicularBoRemote vehicularBo;

	@EJB
	RegistroPrediosBoRemote registroPrediosBo;

	@EJB
	ObligacionBoRemote obligacionBo;
	
	@EJB
	MenuBoRemote menuBo;
	
	@PostConstruct
	public void init() throws Exception {
		permisosMenu();
		lotes = cobranzaCoactivaBo.getAllFindLote();
		try {
			// para costas y gastos
			listVehicularDTOs = null;
			listDjPredials = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		/* FILTRO POR SECTOR */
	}
	
	public void permisosMenu() {	
		try {
			int submenuId = Constante.CONSULTAS_RECS;
		 	
			int permisoBuscarId = Constante.BUSCAR;
			int permisoVerDetalleId = Constante.VER_DETALLE;
			int permisoModificarActualizarId = Constante.MODIFICAR_ACTUALIZAR;
			int permisoGenerarRECInicioId = Constante.GENERAR_REC_INICIO;
			int permisoGenerarMasRECsId = Constante.GENERAR_MAS_RECS;
			
			permisoBuscar = false;
		 	permisoVerDetalle = false;
		 	permisoModificarActualizar = false;
		 	permisoGenerarRECInicio = false;
		 	permisoGenerarMasRECs = false;
			
			listPermisosSubmenu  = menuBo.getAccesosSubmenuUsuario( getSessionManaged().getUsuarioLogIn().getUsuarioId() , submenuId);
			
			Iterator<SimpleMenuDTO> menuIterar = listPermisosSubmenu.iterator();
			while (menuIterar.hasNext()) {
				SimpleMenuDTO lsm = menuIterar.next();
				if(lsm.getItemId() == permisoBuscarId) {
					permisoBuscar = true;
				}
				if(lsm.getItemId() == permisoVerDetalleId) {
					permisoVerDetalle = true;
				}
				if(lsm.getItemId() == permisoModificarActualizarId) {
					permisoModificarActualizar = true;
				}
				if(lsm.getItemId() == permisoGenerarRECInicioId) {
					permisoGenerarRECInicio = true;
				}
				if(lsm.getItemId() == permisoGenerarMasRECsId) {
					permisoGenerarMasRECs = true;
				}
			}
		} catch (SisatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	public Filter<?> getFilterVendor() {
		return new Filter<FindCcLoteDetalleActoExp>() {
			public boolean accept(FindCcLoteDetalleActoExp t) {
				String vendor = getVendorFilter();
				if (vendor == null || vendor.length() == 0
						|| vendor.equals(t.getUbicables())) {
					return true;
				}
				return false;
			}
		};
	}

	public Filter<?> getFilterSector() {
		return new Filter<FindCcLoteDetalleActoExp>() {
			public boolean accept(FindCcLoteDetalleActoExp t) {
				String vendor2 = getSectorFilter();
				if (vendor2 == null || vendor2.length() == 0
						|| vendor2.equals(t.getSector())) {
					return true;
				}
				return false;
			}
		};
	}

	public void selectUsuarioAction() {
		if (findCcLoteItem != null) {
			try {
				lista = cobranzaCoactivaBo.getAllFindCcLoteExp(findCcLoteItem
						.getLoteId());
				setLoteExigibleId(findCcLoteItem.getLoteId());
				setAnioLote(findCcLoteItem.getAnnoLote());
				setTipoActoId(findCcLoteItem.getTipoActoId());
				selectedRol = null;
				// listaRecMasiva=cobranzaCoactivaBo.getAllRecsMasivasPorLote(loteExigibleId);
				for (FindCcLoteDetalleActoExp findCcLoteDetalleItem : lista) {
					if (findCcLoteDetalleItem.getEstado_deuda().equals(
							"PENDIENTE")
							&& findCcLoteDetalleItem.getNroExpediente().equals(
									"--")) {
						setDesactivaRecsInicio(true);
						break;
					} else {
						setDesactivaRecsInicio(false);
					}
				}
				for (FindCcLoteDetalleActoExp findCcLoteDetalleItem : lista) {
					if (findCcLoteDetalleItem.getNroExpediente() != null) {
						setBooleanRecsMasiva(true);
						break;
					} else {
						setBooleanRecsMasiva(false);
					}
				}
			} catch (Exception ex) {
				System.out.println(ex);
			}
			// }
		}
	}

	public void selectRowSelection() {
		if (findCcActoAgrupadoItem != null) {
			try {
				lista = cobranzaCoactivaBo
						.getAllFindCcLoteExp(findCcActoAgrupadoItem
								.getLoteIdAnterior());
				setLoteExigibleId(findCcActoAgrupadoItem.getLoteIdAnterior());
				setAnioLote(findCcActoAgrupadoItem.getAnnoDeuda());
				setTipoActoId(findCcActoAgrupadoItem.getTipoActoId());
				selectedRol = null;
				List<FindCcLoteDetalleActoExp> listaDetalle = new ArrayList<FindCcLoteDetalleActoExp>(
						lista);
				Iterator<FindCcLoteDetalleActoExp> iterador = listaDetalle
						.iterator();
				while (iterador.hasNext()) {
					FindCcLoteDetalleActoExp findCcActoItem = iterador.next();
					if (findCcActoItem.getEstado_deuda().equals("PENDIENTE")
							&& findCcActoItem.getNroExpediente() != null) {
						setDesactivaRecsInicio(true);
						break;
					} else {
						setDesactivaRecsInicio(false);
					}
				}
			} catch (Exception ex) {
				System.out.println(ex);
			}
		}
	}

	public void generarRecInicio() throws Exception {
		try {
			if (lista.size() != 0 && lista != null) {
				if (lista.size() >= nroRegistrosGenerar) {

					lote_id = generalBo.ObtenerCorrelativoTabla("cc_lote", 1);
					ccLote.setLoteId(lote_id);
					ccLote.setTipoLoteId(Constante.TIPO_LOTE_COACTIVO_ID);
					ccLote.setAnnoLote(getAnioLote());
					ccLote.setFechaLote(DateUtil.getCurrentDate());
					ccLote.setEstado(Constante.ESTADO_ACTIVO);
					ccLote.setFlagGeneracion(Constante.FLAG_GENERACION_PROGRAMADA);
					ccLote.setTipoLoteGeneracion(Constante.TIPO_LOTE_GENERACION_PRELIMINAR);
					ccLote = controlycobranzaBo.create(ccLote);
					// INSERT CC_LOTE_ACTO
					Integer lote_acto_id = generalBo.ObtenerCorrelativoTabla(
							"cc_lote_acto", 1);
					CcLoteActoPK id = new CcLoteActoPK();
					id.setLoteActoId(lote_acto_id);
					id.setLoteId(ccLote.getLoteId());
					ccLoteActo.setId(id);
					if (tipoActoId == 3 || tipoActoId == 4) {
						ccLoteActo
								.setTipoActoId(Constante.TIPO_ACTO_REC_TRIBUTARIA);
					} else {
						ccLoteActo
								.setTipoActoId(Constante.TIPO_ACTO_REC_ADMINISTRATIVA);
					}
					ccLoteActo.setEstado(Constante.ESTADO_ACTIVO);
					ccLoteActo = controlycobranzaBo.create(ccLoteActo);
					cobranzaCoactivaBo.generarRecInicio(lote_id,
							loteExigibleId, nroRegistrosGenerar);
					lotes = cobranzaCoactivaBo.getAllFindLote();
					lista = cobranzaCoactivaBo
							.getAllFindCcLoteExp(loteExigibleId);
					setDesactivaRecsInicio(true);
				} else {
					addErrorMessage(getMsg("Ingrese la cantidad Correcta a generar. Verifique!!!"));
				}
			} else {
				addErrorMessage(getMsg("La Deuda Exigible, ya fue generadas en otro Lote. Verifique!!!"));
			}
		} catch (Exception e) {
			// e.printStackTrace();
			addErrorMessage(getMsg("Seleccione un Lote de la lista de Lotes con deuda exigible!!!"));
		}
	}

	public void limpiar() {
		this.motivoCancelacion = null;
		this.nroResolucion = null;
		this.estadoCancelacion = null;
	}

	public void verSuspensionXExpediente() {
		try {
			limpiar();
			// loteExId = findCcActoAgrupadoItem.getLoteIdAnterior();
			// deudaExId = findCcActoAgrupadoItem.getDeudaExigibleId();
			// personaId = findCcActoAgrupadoItem.getPersonaId();
			motivoCancelacion = findCcActoAgrupadoItem.getMotivoCancelacion();
			nroResolucion = findCcActoAgrupadoItem.getResolucionCancelacion();
			String estado = findCcActoAgrupadoItem.getEstadoExpediente();
			if (estado.equals("SUSP. DEFINITIVAMENTE")) {
				setEstadoCancelacion("Susp. Definitiva");
			} else if (estado.equals("ACTIVO")) {
				setEstadoCancelacion("Activo");
			} else {
				setEstadoCancelacion("Susp. Temporal");
			}

		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public Boolean validaDatosExpediente() {
		if (motivoCancelacion == null) {
			addErrorMessage(getMsg("Ingrese Motivo de Suspension del Expediente"));
			return false;
		} else if (nroResolucion == null
				|| nroResolucion.equals("___-___-________")) {
			addErrorMessage(getMsg("Ingrese Resolución que Suspende Expediente"));
			return false;
		}
		return true;
	}

	public void suspenderExpediente() {
		try {
			if (validaDatosExpediente()) {
				String estadoExpe = findCcActoAgrupadoItem
						.getEstadoExpediente();
				if (!estadoExpe.equals("SUSP. DEFINITIVAMENTE")) {
					String estadoDeuda = findCcActoAgrupadoItem
							.getEstado_deuda();
					if (estadoDeuda == null || estadoDeuda.equals("")
							|| !estadoDeuda.equals("CANCELADA")) {

						Integer codPersona = findCcActoAgrupadoItem
								.getPersonaId();
						String nroExp = findCcActoAgrupadoItem
								.getNroExpediente();
						String estado;
						if (estadoCancelacion.equals("Susp. Definitiva")) {
							estado = "0";
						} else if (estadoCancelacion.equals("Activo")) {
							estado = "1";
						} else {
							estado = "2";
						}
						cobranzaCoactivaBo.actualizarRecXCancelacion(
								motivoCancelacion, nroResolucion, estado,
								nroExp, codPersona);
						verRecExpedienteAgrupados();
					} else {
						addErrorMessage(getMsg("Las CANCELADAS ya estan Suspendidas automaticamente X el sistema."));
					}
				} else {
					addErrorMessage(getMsg("Expediente "
							+ findCcActoAgrupadoItem.getNroExpediente()
							+ " ya fue Suspendido Definitivamente."));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void limpiarVariablesHistoricoRECs() {
		setLoteExId(null);
		setDeudaExId(null);
		setPersonaId(null);
		setAnioDeuda(null);
		setPapeletaId(null);
		setNroPapeleta(null);
		setConceptoId(null);
	}

	public void mostrarHistoricoRECsXExpediente() {
		try {
			limpiarVariablesHistoricoRECs();
			findCcActoItem = null;
			loteExId = findCcActoAgrupadoItem.getLoteIdAnterior();
			deudaExId = findCcActoAgrupadoItem.getDeudaExigibleId();
			personaId = findCcActoAgrupadoItem.getPersonaId();
			setAnioDeuda(findCcActoAgrupadoItem.getAnnoDeuda());
			setPapeletaId(findCcActoAgrupadoItem.getPapeletaId());
			setNroPapeleta(findCcActoAgrupadoItem.getNroPapeleta());
			setConceptoId(findCcActoAgrupadoItem.getConceptoId());
			historico = cobranzaCoactivaBo.getAllFindRecHistorico(loteExId,
					deudaExId, personaId);
			for (FindCcRecHistorico findCcRecHistorico : historico) {
				if (findCcRecHistorico.getTipoRecId() == 16
						|| findCcRecHistorico.getTipoRecId() == 17) {
					setDisableBtnGeneraRec(true);
					break;
				} else {
					setDisableBtnGeneraRec(false);
				}
			}
			/* COMBOBOX:: TIPO DE REC */
			List<FindCcRecTipo> lstCcTipoRec = cobranzaCoactivaBo
					.getAllTipoRec(Boolean.FALSE);
			Iterator<FindCcRecTipo> it1 = lstCcTipoRec.iterator();
			listaTipoRec = new ArrayList<SelectItem>();
			while (it1.hasNext()) {
				FindCcRecTipo obj = it1.next();
				listaTipoRec.add(new SelectItem(obj.getDescripcionTipoRec(),
						String.valueOf(obj.getTipoRecId())));
				mapTipoRec.put(obj.getDescripcionTipoRec().trim(),
						obj.getTipoRecId());
			}
			/*- COMBOBOX:: TIPO DE REC -*/

		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void mostrar() {
		try {
			limpiarVariablesHistoricoRECs();
			findCcActoAgrupadoItem = null;
			loteExId = findCcActoItem.getLoteId();
			deudaExId = findCcActoItem.getDeudaExigibleId();
			personaId = findCcActoItem.getPersonaId();
			setAnioDeuda(findCcActoItem.getAnnoDeuda());
			setPapeletaId(findCcActoItem.getPapeletaId());
			setNroPapeleta(findCcActoItem.getNroPapeleta());
			setConceptoId(findCcActoItem.getConceptoId());
			historico = cobranzaCoactivaBo.getAllFindRecHistorico(loteExId,
					deudaExId, personaId);
			for (FindCcRecHistorico findCcRecHistorico : historico) {
				if (findCcRecHistorico.getTipoRecId() == 16
						|| findCcRecHistorico.getTipoRecId() == 17) {
					setDisableBtnGeneraRec(true);
					break;
				} else {
					setDisableBtnGeneraRec(false);
				}
			}
			/* COMBOBOX:: TIPO DE REC */
			List<FindCcRecTipo> lstCcTipoRec = cobranzaCoactivaBo
					.getAllTipoRec(Boolean.FALSE);
			Iterator<FindCcRecTipo> it1 = lstCcTipoRec.iterator();
			listaTipoRec = new ArrayList<SelectItem>();
			while (it1.hasNext()) {
				FindCcRecTipo obj = it1.next();
				listaTipoRec.add(new SelectItem(obj.getDescripcionTipoRec(),
						String.valueOf(obj.getTipoRecId())));
				mapTipoRec.put(obj.getDescripcionTipoRec().trim(),
						obj.getTipoRecId());
			}
			/*- COMBOBOX:: TIPO DE REC -*/

		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void generarRecMasiva() throws Exception {
		try {
			if (tipoRecId != null) {
				int maxItem = listaRecMasiva.size();
				if (listaRecMasiva.get(maxItem - 1).getFechaNotificacionRec() != null) {
					// boolean valComparar = false;
					Calendar calendar = Calendar.getInstance();
					int anio = calendar.get(Calendar.YEAR);
					Integer diasHabiles = null;
					for (FindCcRecTipo findCcRecTipoItem : listaRecMasiva) {
						if (findCcRecTipoItem.getTipoRecId() == 1
								&& findCcRecTipoItem.getFechaNotificacionRec() != null) {
							diasHabiles = obligacionBo
									.obtenerDiasHabiles(findCcRecTipoItem
											.getFechaNotificacionRec(),
											calendar.getTime());
							break;
						}
					}
					if (diasHabiles != null) {
						if (diasHabiles > 7) {

							if (tipoRecId == 15) {
								int columnIndex = 0;
								for (FindCcLoteDetalleActoExp findCcActoItem : lista) {
									if (lista.get(columnIndex)
											.getEstado_deuda()
											.equals("PENDIENTE")
											&& lista.get(columnIndex)
													.getUltimaTipoRecId() == 1
											&& !lista.get(columnIndex)
													.getNroDocumento()
													.equals("-")
											&& !lista.get(columnIndex)
													.getNroExpediente()
													.equals("--")) {
										cobranzaCoactivaBo.generarRec(
												findCcActoItem.getLoteRecId(),
												anio, findCcActoItem
														.getPersonaId(),
												findCcActoItem
														.getNroExpediente(),
												findCcActoItem.getAnnoDeuda(),
												tipoRecId, findCcActoItem
														.getTipoDocumentoId(),
												findCcActoItem.getLoteId(),
												findCcActoItem.getActoId());
									}
									columnIndex++;
								}
								listaRecMasiva = cobranzaCoactivaBo
										.getAllRecsMasivasPorLote(loteExigibleId);
								lista = cobranzaCoactivaBo
										.getAllFindCcLoteExp(loteExigibleId);
							} else {
								int columnIndex = 0;
								for (FindCcLoteDetalleActoExp findCcActoItem : lista) {
									if (lista.get(columnIndex)
											.getEstado_deuda()
											.equals("PENDIENTE")
											&& lista.get(columnIndex)
													.getUltimaTipoRecId() == 1
											&& !lista.get(columnIndex)
													.getNroExpediente()
													.equals("--")) {
										cobranzaCoactivaBo.generarRec(
												findCcActoItem.getLoteRecId(),
												anio, findCcActoItem
														.getPersonaId(),
												findCcActoItem
														.getNroExpediente(),
												findCcActoItem.getAnnoDeuda(),
												tipoRecId, findCcActoItem
														.getTipoDocumentoId(),
												findCcActoItem.getLoteId(),
												findCcActoItem.getActoId());
									}
									columnIndex++;
								}
								listaRecMasiva = cobranzaCoactivaBo
										.getAllRecsMasivasPorLote(loteExigibleId);
								lista = cobranzaCoactivaBo
										.getAllFindCcLoteExp(loteExigibleId);
							}
						} else if (tipoRecId == 16 || tipoRecId == 17) {
							int columnIndex = 0;
							for (FindCcLoteDetalleActoExp findCcActoItem : lista) {
								if (lista.get(columnIndex).getEstado_deuda()
										.equals("PENDIENTE")
										&& !lista.get(columnIndex)
												.getNroExpediente()
												.equals("--")
										&& lista.get(columnIndex)
												.getUltimaTipoRecId() != tipoRecId
										&& lista.get(columnIndex)
												.getUltimaTipoRecId() == 1) {
									cobranzaCoactivaBo
											.generarRec(
													findCcActoItem
															.getLoteRecId(),
													anio,
													findCcActoItem
															.getPersonaId(),
													findCcActoItem
															.getNroExpediente(),
													findCcActoItem
															.getAnnoDeuda(),
													tipoRecId,
													findCcActoItem
															.getTipoDocumentoId(),
													findCcActoItem.getLoteId(),
													findCcActoItem.getActoId());
								}
								columnIndex++;
							}
							listaRecMasiva = cobranzaCoactivaBo
									.getAllRecsMasivasPorLote(loteExigibleId);
							lista = cobranzaCoactivaBo
									.getAllFindCcLoteExp(loteExigibleId);
						} else {
							addErrorMessage(getMsg("No puede generar Nuevas RECs, No han pasado los 7 dias HÃ¡biles."));
						}
					} else {
						addErrorMessage(getMsg("Falta Ingresar Fechas de Notificacion a RECs de Inicio."));
					}
				} else {
					addErrorMessage(getMsg("Todas las RECs del último lote no han sido notificadas. GENERE RECs INDIVIDUALES."));
				}
			} else {
				addErrorMessage(getMsg("Seleccione un Tipo de REC."));
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void verRecMasiva() {
		/* COMBOBOX:: TIPO DE REC */

		List<FindCcRecTipo> lstCcTipoRec = null;
		try {
			lstCcTipoRec = cobranzaCoactivaBo.getAllTipoRec(Boolean.FALSE);
			Iterator<FindCcRecTipo> it1 = lstCcTipoRec.iterator();
			listaTipoRec = new ArrayList<SelectItem>();

			while (it1.hasNext()) {
				FindCcRecTipo obj = it1.next();
				listaTipoRec.add(new SelectItem(obj.getDescripcionTipoRec(),
						String.valueOf(obj.getTipoRecId())));
				mapTipoRec.put(obj.getDescripcionTipoRec().trim(),
						obj.getTipoRecId());
			}
			/*- COMBOBOX:: TIPO DE REC -*/
			// for (FindCcLoteDetalleActoExp findCcActoItem : lista) {
			// if (findCcActoItem.getEstado_deuda().equals("PENDIENTE")
			// && !findCcActoItem.getNroExpediente().equals("null")) {
			listaRecMasiva = cobranzaCoactivaBo
					.getAllRecsMasivasPorLote(loteExigibleId);
			// break;
			// }
			// }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void verRecExpedienteAgrupados() {

		try {
			listaExpedienteAgrupados = null;
			if (codigoPersonaBuscar != null && codigoPersonaBuscar != 0) {
				listaExpedienteAgrupados = cobranzaCoactivaBo
						.getAllExpedientesAcumulados(codigoPersonaBuscar);
			} else if (placaVehiculoBuscar != null
					&& !placaVehiculoBuscar.equals("")) {
				listaExpedienteAgrupados = cobranzaCoactivaBo
						.getAllExpedientesXPlaca(placaVehiculoBuscar.trim());
			} else if (this.codigoAnteriorPersona != null
					&& !codigoAnteriorPersona.equals("")) {
				listaExpedienteAgrupados = cobranzaCoactivaBo
						.getAllExpedientesAnteriorAdministrado(codigoAnteriorPersona
								.trim());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void verCostas() {
		try {
			java.util.Date fechaNot = null;
			java.util.Date fechaEmis = null;
			fechaNot = findCcRecs.getFechaNotificacion();
			fechaEmis = findCcRecs.getFechaEmision();
			Calendar c = Calendar.getInstance();
			c.setTime(fechaEmis);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.HOUR_OF_DAY, 0);
			Date fechEmision = c.getTime();
			Calendar c1 = Calendar.getInstance();
			if (fechaNot != null) {
				c1.setTime(fechaNot);
				c1.set(Calendar.SECOND, 0);
				c1.set(Calendar.MILLISECOND, 0);
				c1.set(Calendar.MINUTE, 0);
				c1.set(Calendar.HOUR_OF_DAY, 0);
			}
			Date fechNotificacion = c1.getTime();

			Calendar currDtCal = Calendar.getInstance();
			currDtCal.set(Calendar.SECOND, 0);
			currDtCal.set(Calendar.MILLISECOND, 0);
			currDtCal.set(Calendar.MINUTE, 0);
			currDtCal.set(Calendar.HOUR_OF_DAY, 0);
			Date fechActual = currDtCal.getTime();

			if (fechEmision.before(fechNotificacion)
					|| (fechEmision.compareTo(fechNotificacion) == 0 && DateUtil
							.diferenciasDeFechas(fechNotificacion, fechActual) > 0)
					|| findCcRecs.getTipoRecId() == 16
					|| findCcRecs.getTipoRecId() == 17) {
				Calendar calendar = Calendar.getInstance();
				java.util.Date fechaIni = null;
				fechaIni = findCcRecs.getFechaNotificacion();
				if ((DateUtil.diferenciasDeFechas(fechaIni, calendar.getTime()) > 0)
						|| findCcRecs.getTipoRecId() == 16
						|| findCcRecs.getTipoRecId() == 17) {
					listSelectItems.clear();
					getListItemConcepto();
					if (findCcActoItem == null) {
						setAnioDeuda(findCcActoAgrupadoItem.getAnnoDeuda());
					} else if (findCcActoAgrupadoItem == null) {
						setAnioDeuda(findCcActoItem.getAnnoDeuda());
					}
					setAnioRec(findCcRecs.getAnioRec());
					setPersonaId(findCcRecs.getPersonaId());
					setSubconceptoId(findCcRecs.getSubConceptoId());
					setRecId(findCcRecs.getRecId());
					listSubConceptoDTOItems = new ArrayList<SelectItem>();
					llenarSubconcepto(listSelectItems.get(0).getValue()
							.toString());
					subConceptoDTO = null;
					cmbSubConcepto = null;
					cmbTasa = null;
					setMontoTotal(BigDecimal.valueOf(0.0));
					obligacionDTO = new ObligacionDTO();
					listObligacionDTOs.clear();
					listObligGeneradasDTOs = cobranzaCoactivaBo
							.getAllCostasXTipoRec(personaId, recId);
				} else {
					addErrorMessage(getMsg("Solo puede generar Costas al dia siguiente de la Notificacion."));
				}
			} else {
				addErrorMessage(getMsg("No Corresponde Generarle Costas a la rec. "
						+ findCcRecs.getDescripcionCortaRec()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		// return sendRedirectPrincipal();
	}

	public void loadFormaNotificacionById(ValueChangeEvent event) {
		try {

			HtmlComboBox combo = (HtmlComboBox) event.getComponent();
			String value = combo.getValue().toString();
			Integer flagUbicacion;
			if (value.equals("Ubicables")) {
				flagUbicacion = 1;
			} else if (value.equals("Inubicables")) {
				flagUbicacion = 2;
			} else {
				flagUbicacion = 3;
			}
			List<NoMotivoNotificacion> lsNoMotivoNotificacion = controlycobranzaBo
					.getAlNoMotivoNotificacion(flagUbicacion);
			Iterator<NoMotivoNotificacion> it1 = lsNoMotivoNotificacion
					.iterator();
			lstTipoNotificacion = new ArrayList<SelectItem>();
			while (it1.hasNext()) {
				NoMotivoNotificacion obj01 = it1.next();
				lstTipoNotificacion.add(new SelectItem(obj01.getDescripcion(),
						String.valueOf(obj01.getMotivoNotificacionId())));
			}
			// verFormasNotificacion()
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void notificar() {
		try {
			List<NoMotivoNotificacion> lsNoMotivoNotificacion = controlycobranzaBo
					.getAlNoMotivoNotificacion(1);
			Iterator<NoMotivoNotificacion> it1 = lsNoMotivoNotificacion
					.iterator();
			lstTipoNotificacion = new ArrayList<SelectItem>();
			while (it1.hasNext()) {
				NoMotivoNotificacion obj01 = it1.next();
				lstTipoNotificacion.add(new SelectItem(obj01.getDescripcion(),
						String.valueOf(obj01.getMotivoNotificacionId())));
			}
			/* COMBOBOX:: NOTIFICADORES */
			List<NoNotificador> lstNotificador = controlycobranzaBo
					.getAllNotificador();
			for (NoNotificador noNotificador : lstNotificador) {
				lstSelectItemsNotificador.add(new SelectItem(noNotificador
						.getApellidosNombres(), String.valueOf(noNotificador
						.getNotificadorId())));
				mapNotificador.put(noNotificador.getApellidosNombres().trim(),
						noNotificador.getNotificadorId());
			}
			/* COMBOBOX:: NOTIFICADORES */
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void ingresarFechaEmisionMigrados() {
		try {
			recId = findCcRecs.getRecId();
			if (fechaEmisionRecMigradas != null) {
				cobranzaCoactivaBo.actualizarEmisionRec(recId,
						fechaEmisionRecMigradas);
				historico = cobranzaCoactivaBo.getAllFindRecHistorico(loteExId,
						deudaExId, personaId);
			} else {
				addErrorMessage(getMsg("Ingrese Correctamente Fecha de Emision.!!!"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void imprimirRecxTipo() {
		try {
			if ((lista != null && lista.size() > 0)
					|| listaExpedienteAgrupados != null
					&& listaExpedienteAgrupados.size() > 0) {
				java.sql.Connection connection = null;
				try {
					// Integer concepto=
					// lista.get(0).getConceptoId().intValue();
					if (lista != null && lista.size() > 0) {
						tipoDocumento = lista.get(0).getTipoDocumentoId();
						tipoDocumentoAgrupados = 0;
					}
					if (listaExpedienteAgrupados != null
							&& listaExpedienteAgrupados.size() > 0) {
						tipoDocumentoAgrupados = listaExpedienteAgrupados
								.get(0).getTipoDocumentoId();
						tipoDocumento = 0;
					}

					String cadena = null;
					String cadena2 = null;

					if (findCcRecs.getTipoRecId() == 1) {
						if (conceptoId != 4) {

							cadena = "rec_inicio_predial_tributaria.jasper";
							cadena2 = "RecInicio" + '-'
									+ findCcRecs.getNroExpediente();
						}

						// if (conceptoId == 2) {
						//
						// cadena = "rec_inicio_vehicular_tributaria.jasper";
						// cadena2 = "RecInicio" + '-'
						// + findCcRecs.getNroExpediente();
						// }

						if (conceptoId == 4) {

							cadena = "rec_inicio_notributaria.jasper";
							cadena2 = "RecInicio" + '-'
									+ findCcRecs.getNroExpediente();
						}

						// if (conceptoId == 12) {
						//
						// cadena = "rec_inicio_multas_notributaria.jasper";
						// cadena2 = "RecInicio" + '-'
						// + findCcRecs.getNroExpediente();
						// }

					}
					if (findCcRecs.getTipoRecId() == 13
							|| findCcRecs.getTipoRecId() == 14) {
						cadena = "reporte_rec_embargo_inscripcion_tributaria.jasper";
						cadena2 = "RecEmbargoInscripcionTributaria" + '-'
								+ findCcRecs.getNroExpediente();
					}

					/**
					 * * M.C Embargo en forma de Retencion, por cada entidad
					 * bancaria, financiera y/o terceros
					 */
					if (findCcRecs.getTipoRecId() == 15) {
						if (conceptoId == 1) {
							cadena = "reporte_rec_retencion_predial_tributaria.jasper";
							cadena2 = "RecEmbargoRetencion" + '-'
									+ findCcRecs.getNroExpediente();
						}
						else if (conceptoId == 2) {
							cadena = "reporte_rec_retencion_vehicular_tributaria.jasper";
							cadena2 = "RecEmbargoRetencion" + '-'
									+ findCcRecs.getNroExpediente();
						}
						else if (conceptoId == 4){
							cadena = "reporte_rec_sin_formato.jasper";// sin formato
							cadena2 = "RecEmbargoRetencion" + '-'
									+ findCcRecs.getNroExpediente();
						}
					}

					if (findCcRecs.getTipoRecId() == 5
							|| findCcRecs.getTipoRecId() == 6)
					// || findCcRecs.getTipoRecId() == 7
					// || findCcRecs.getTipoRecId() == 8)
					{
						cadena = "reporte_rec_embargo_extraccion_tributaria.jasper";
						cadena2 = "RecEmbargoExtraccionTributaria" + '-'
								+ findCcRecs.getNroExpediente();
					}

					if (findCcRecs.getTipoRecId() == 9
							|| findCcRecs.getTipoRecId() == 10
							|| findCcRecs.getTipoRecId() == 11
							|| findCcRecs.getTipoRecId() == 12) {
						if (conceptoId == 4) {
							cadena = "reporte_rec_captura_notributaria.jasper";
							cadena2 = "RecCapturaNoTributariaMasiva";
						} else if (conceptoId == 2) {
							cadena = "reporte_rec_captura_tributaria.jasper";
							cadena2 = "RecCapturaTributaria" + '-'
									+ findCcRecs.getNroExpediente();
						}

					}

					if (findCcRecs.getTipoRecId() == 16) {

						if (conceptoId == 4) {// (tipoDocumento==20||tipoDocumentoAgrupados==20){
							cadena = "reporte_rec_suspension_notributaria.jasper";
							cadena2 = "RecSuspencionNoTributaria" + '-'
									+ findCcRecs.getNroExpediente();
						} else if (conceptoId != 4) {// (tipoDocumento==19||tipoDocumentoAgrupados==19){
							cadena = "reporte_rec_suspension_tributaria.jasper";
							cadena2 = "RecSuspencionTributaria" + '-'
									+ findCcRecs.getNroExpediente();
						}

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
					Integer val = findCcRecs.getLoteRecId();
					Integer per = findCcRecs.getPersonaId();
					Integer tip = findCcRecs.getTipoRecId();
					Integer rec = findCcRecs.getRecId();
					Integer c = conceptoId;
					Integer usuario=findCcRecs.getUsuarioId();
					// falta el tipo de rec como parametro
					parameters.put("p_lote_id", val);
					parameters.put("p_persona_id", per);
					parameters.put("p_tipo_rec", tip);
					parameters.put("p_concepto_id", c);
					parameters.put("p_rec_id", rec);
					parameters.put("ruta_imagen", path_imagen);
					parameters.put("p_usuario", usuario);
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
				addErrorMessage(getMsg("Seleccione un Lote Exigible. Verifique!!!"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void loadSeleccion(ValueChangeEvent event) {
		try {
			HtmlComboBox combo = (HtmlComboBox) event.getComponent();
			String value = combo.getValue().toString();
			this.setEstadoCancelacion(value);

		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void imprimirRecMasivaxTipo() {
		try {
			if (listaRecMasiva != null && listaRecMasiva.size() > 0) {
				java.sql.Connection connection = null;
				try {
					Integer concepto = listaRecMasiva.get(0).getConceptoId().intValue();
					Integer usuario = listaRecMasiva.get(0).getUsuarioId().intValue();
					// Integer tipo = getTipoRecId();
					String cadena = null;
					String cadena2 = null;

					if (findCcTipoRecs.getTipoRecId() == 1) {
						if (concepto == 1) {

							cadena = "rec_masiva_inicio_predial_tributaria.jasper";
							cadena2 = "RecInicioMasiva";
						}

						if (concepto == 2) {

							cadena = "rec_masiva_inicio_vehicular_tributaria.jasper";
							cadena2 = "RecInicioMasiva";
						}

						if (concepto == 3) {

							cadena = "rec_masiva_inicio_arbitrios_tributaria.jasper";
							cadena2 = "RecInicioMasiva";
						}

						if (concepto == 4) {

							cadena = "rec_masiva_inicio_transito_notributaria.jasper";
							cadena2 = "RecInicioMasiva";
						}

						if (concepto == 12) {

							cadena = "rec_masiva_inicio_multas_notributaria.jasper";
							cadena2 = "RecInicioMasiva";
						}

					}
					if (findCcTipoRecs.getTipoRecId() == 13) {
						if (concepto == 1) {
							cadena = "reporte_rec_masiva_embargo_inscripcion_tributaria.jasper";
							cadena2 = "RecEmbargoInscripcionTributariaMasiva";
						} else {
							cadena = "";
							cadena2 = "RecEmbargoInscripcionMasiva";
						}

					}

					if (findCcTipoRecs.getTipoRecId() == 14) {

						cadena = "";
						cadena2 = "RecEmbargoInscripcionTributariaMasiva";

					}

					/**
					 * * M.C Embargo en forma de Retencion, por cada entidad
					 * bancaria, financiera y/o terceros
					 */
					if (findCcTipoRecs.getTipoRecId() == 15) {
						// cadena =
						// "reporte_rec_masiva_retencion_tributaria.jasper";
						if (concepto == 1) {
							cadena = "reporte_rec_masiva_retenciones_tributaria.jasper";
							cadena2 = "RecEmbargoRetencionMasiva";
						}
						else if (concepto == 2) {
							cadena = "reporte_rec_masiva_retenciones_vehicular_tributaria.jasper";
							cadena2 = "RecEmbargoRetencionMasivaVehicular";
						}
						else if (concepto == 4){
							cadena = "reporte_rec_sin_formato.jasper";// sin formato
							cadena2 = "RecEmbargoRetencionMasiva";
						}
					}

					/**
					 * * M.C Embargo en forma de Deposito con extraccion : Uso
					 * casa habitacion/uso comercial
					 */
					if (findCcTipoRecs.getTipoRecId() == 5
							|| findCcTipoRecs.getTipoRecId() == 6) {
						if (concepto == 1) {
							cadena = "reporte_rec_masiva_embargo_extraccion_tributaria.jasper";
							cadena2 = "RecEmbargoExtraccionTributariaMasiva";
						} else {
							cadena = "";// sn formato
							cadena2 = "RecEmbargoExtraccionMasiva";
						}

					}

					/**
					 * * M.C Embargo en forma de DepÃ³sito sin extracciÃ³n : Uso
					 * casa habitaciÃ³n/uso comercial
					 */

					if (findCcTipoRecs.getTipoRecId() == 7
							|| findCcTipoRecs.getTipoRecId() == 8) {
						cadena = "";// sn formato
						cadena2 = "RecEmbargoSinExtraccionMasiva";

					}

					/**
					 * * M.C Embargo en forma de Secuestro Conservativo
					 * VehÃ­culos:..
					 */
					if (findCcTipoRecs.getTipoRecId() == 9
							|| findCcTipoRecs.getTipoRecId() == 10
							|| findCcTipoRecs.getTipoRecId() == 11
							|| findCcTipoRecs.getTipoRecId() == 12) {
						if (concepto == 4) {
							cadena = "reporte_rec_masiva_captura_notributaria.jasper";
							cadena2 = "RecCapturaNoTributariaMasiva";
						} else if (concepto == 2) {
							cadena = "reporte_rec_masiva_captura_tributaria.jasper";
							cadena2 = "RecCapturaTributariaMasiva";
						}  else if (concepto == 1) {
							cadena = "reporte_rec_masiva_captura_tributaria_predial.jasper";
							cadena2 = "RecCapturaTributariaMasiva";
						}
					}

					/** * EmisiÃ³n de Resolucion de Suspension */
					if (findCcTipoRecs.getTipoRecId() == 16) {

						if (concepto == 4) {
							cadena = "reporte_rec_masiva_suspension_notributaria.jasper";
							cadena2 = "RecSuspencionNoTributariaMasiva"
									+ "- Lote" + findCcTipoRecs.getLoteId();
						} else if (concepto != 4) {
							cadena = "reporte_rec_masiva_suspension_tributaria.jasper";
							cadena2 = "RecSuspencionTributariaMasiva"
									+ "- Lote" + findCcTipoRecs.getLoteId();
						}

					}

					/**
					 * * EmisiÃ³n de ResoluciÃ³n de SuspenciÃ³n a solicitud de
					 * parte
					 */
					if (findCcTipoRecs.getTipoRecId() == 17) {

						cadena = "";// sin fomrato
						cadena2 = "RecSuspencionxSolicitudMasiva";
					}

					CrudServiceBean connj = CrudServiceBean.getInstance();
					connection = connj.connectJasper();
					String path_context = FacesContext.getCurrentInstance()
							.getExternalContext().getRealPath("/");
					String path_report = path_context + "/sisat/reportes/";
					String path_imagen = path_context
							+ "/sisat/reportes/imagen/";
					HashMap<String, Object> parameters = new HashMap<String, Object>();
					Integer val = findCcTipoRecs.getLoteId();
					Integer tip = findCcTipoRecs.getTipoRecId();
					Integer c = concepto;
					// falta el tipo de rec como parametro
					parameters.put("p_lote_id", val);
					parameters.put("p_tipo_rec", tip);
					parameters.put("p_concepto_id", c);
					parameters.put("p_usuario", usuario);
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
				addErrorMessage(getMsg("Seleccione un Lote Exigible. Verifique!!!"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void loadTipoRecById(ValueChangeEvent event) {
		try {
			HtmlComboBox combo = (HtmlComboBox) event.getComponent();
			String value = combo.getValue().toString();
			if (value != null) {
				tipoRecId = (Integer) mapTipoRec.get(value);
				setCmbTipoRec(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void loadNotificador(ValueChangeEvent event) {
		try {
			HtmlComboBox combo = (HtmlComboBox) event.getComponent();
			String value = combo.getValue().toString();
			if (value != null) {
				notificadorId = (Integer) mapNotificador.get(value);
				setCmbNotificador(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void generarRec() throws Exception {
		try {
			int maxItem = historico.size();
			if (historico.get(maxItem - 1).getFechaNotificacion() != null) {
				Integer recIdUltima = 1;
				boolean valComparar = false;
				for (FindCcRecHistorico findCcRecHistoricoItem : historico) {
					if (findCcRecHistoricoItem.getTipoRecId() == tipoRecId) {
						valComparar = true;
						break;
					}
					recIdUltima = findCcRecHistoricoItem.getRecId();
				}

				for (FindCcRecHistorico findCcRecHistorico : historico) {
					if (findCcRecHistorico.getTipoRecId() == 16
							|| findCcRecHistorico.getTipoRecId() == 17) {
						setDisableBtnGeneraRec(true);
						break;
					} else {
						setDisableBtnGeneraRec(false);
					}
				}

				if (valComparar != true) {
					Calendar currDtCal = Calendar.getInstance();
					currDtCal.set(Calendar.SECOND, 0);
					currDtCal.set(Calendar.MILLISECOND, 0);
					currDtCal.set(Calendar.MINUTE, 0);
					currDtCal.set(Calendar.HOUR_OF_DAY, 0);
					Date fechActual = currDtCal.getTime();
					Date fechaNotif = historico.get(maxItem - 1)
							.getFechaNotificacion();
					Calendar cal = Calendar.getInstance();
					cal.setTime(fechaNotif);
					cal.set(Calendar.SECOND, 0);
					cal.set(Calendar.MILLISECOND, 0);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.HOUR_OF_DAY, 0);
					Date fechaNotifica = cal.getTime();

					Date fechEmis = historico.get(maxItem - 1)
							.getFechaEmision();
					cal.setTime(fechEmis);
					cal.set(Calendar.SECOND, 0);
					cal.set(Calendar.MILLISECOND, 0);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.HOUR_OF_DAY, 0);
					Date fechEmision = cal.getTime();
					/** insertar la lista de costas generadas para la persona */
					listObligGeneradasDTOs = cobranzaCoactivaBo
							.getAllCostasXTipoRec(personaId, recIdUltima);
					if (listObligGeneradasDTOs.size() > 0
							|| fechaNotifica.compareTo(fechActual) == 0
							|| fechaNotifica.compareTo(fechEmision) == 0) {
						Calendar calendar = Calendar.getInstance();
						Integer diasHabiles = obligacionBo.obtenerDiasHabiles(
								historico.get(0).getFechaNotificacion(),
								calendar.getTime());
						int anioRecGenerar = calendar.get(Calendar.YEAR);
						/** Si findCcActoItem!=null */
						if (findCcActoItem != null) {
							if (findCcActoItem.getLoteRecId() != null) {
								if (diasHabiles > 7) {
									if (findCcActoItem.getEstado_deuda()
											.equals("PENDIENTE")) {
										loteRecId = findCcActoItem
												.getLoteRecId();

										anioRec = anioRecGenerar;
										personaRecId = findCcActoItem
												.getPersonaId();
										nroExpedienteRec = findCcActoItem
												.getNroExpediente();
										anioDeuda = findCcActoItem
												.getAnnoDeuda();
										tipoRec = tipoRecId;
										tipoDocRec = findCcActoItem
												.getTipoDocumentoId();
										loteExId = findCcActoItem.getLoteId();
										actoRecId = findCcActoItem.getActoId();

										cobranzaCoactivaBo.generarRec(
												loteRecId, anioRec,
												personaRecId, nroExpedienteRec,
												anioDeuda, tipoRec, tipoDocRec,
												loteExId, actoRecId);
										historico = cobranzaCoactivaBo
												.getAllFindRecHistorico(
														loteExId, deudaExId,
														personaId);
										lista = cobranzaCoactivaBo
												.getAllFindCcLoteExp(loteExId);
									} else if (tipoRecId == 16
											|| tipoRecId == 17) {
										loteRecId = findCcActoItem
												.getLoteRecId();
										anioRec = anioRecGenerar;
										personaRecId = findCcActoItem
												.getPersonaId();
										nroExpedienteRec = findCcActoItem
												.getNroExpediente();
										anioDeuda = findCcActoItem
												.getAnnoDeuda();
										tipoRec = tipoRecId;
										tipoDocRec = findCcActoItem
												.getTipoDocumentoId();
										loteExId = findCcActoItem.getLoteId();
										actoRecId = findCcActoItem.getActoId();
										cobranzaCoactivaBo.generarRec(
												loteRecId, anioRec,
												personaRecId, nroExpedienteRec,
												anioDeuda, tipoRec, tipoDocRec,
												loteExId, actoRecId);
										historico = cobranzaCoactivaBo
												.getAllFindRecHistorico(
														loteExId, deudaExId,
														personaId);
										lista = cobranzaCoactivaBo
												.getAllFindCcLoteExp(loteExId);
									} else {
										addErrorMessage(getMsg("Deuda de REC Cancelada. Solo puede Generar REC de Suspension."));
									}
								} else if (tipoRecId == 16 || tipoRecId == 17) {
									loteRecId = findCcActoItem.getLoteRecId();
									anioRec = anioRecGenerar;
									personaRecId = findCcActoItem
											.getPersonaId();
									nroExpedienteRec = findCcActoItem
											.getNroExpediente();
									anioDeuda = findCcActoItem.getAnnoDeuda();
									tipoRec = tipoRecId;
									tipoDocRec = findCcActoItem
											.getTipoDocumentoId();
									loteExId = findCcActoItem.getLoteId();
									actoRecId = findCcActoItem.getActoId();
									cobranzaCoactivaBo.generarRec(loteRecId,
											anioRec, personaRecId,
											nroExpedienteRec, anioDeuda,
											tipoRec, tipoDocRec, loteExId,
											actoRecId);
									historico = cobranzaCoactivaBo
											.getAllFindRecHistorico(loteExId,
													deudaExId, personaId);
									lista = cobranzaCoactivaBo
											.getAllFindCcLoteExp(loteExId);
								} else {
									addErrorMessage(getMsg("Solo Puede Generar RECs de suspension, Para Generar Otras RECs deben pasar 7 dias Habiles."));
								}
							}
						} else if (findCcActoAgrupadoItem != null) {

							if (findCcActoAgrupadoItem.getLoteRecId() != null) {
								if (diasHabiles > 7) {
									/**
									 * ir a la BD y verificar si esta pendiente
									 * de pago
									 */
									if (findCcActoAgrupadoItem
											.getEstado_deuda().equals(
													"PENDIENTE")) {
										loteRecId = findCcActoAgrupadoItem
												.getLoteRecId();
										anioRec = anioRecGenerar;
										personaRecId = findCcActoAgrupadoItem
												.getPersonaId();
										nroExpedienteRec = findCcActoAgrupadoItem
												.getNroExpediente();
										anioDeuda = findCcActoAgrupadoItem
												.getAnnoDeuda();
										tipoRec = tipoRecId;
										tipoDocRec = findCcActoAgrupadoItem
												.getTipoDocumentoId();
										loteExId = findCcActoAgrupadoItem
												.getLoteIdAnterior();
										actoRecId = findCcActoAgrupadoItem
												.getActoId();
										cobranzaCoactivaBo.generarRec(
												loteRecId, anioRec,
												personaRecId, nroExpedienteRec,
												anioDeuda, tipoRec, tipoDocRec,
												loteExId, actoRecId);
										historico = cobranzaCoactivaBo
												.getAllFindRecHistorico(
														loteExId, deudaExId,
														personaId);
										lista = cobranzaCoactivaBo
												.getAllFindCcLoteExp(loteExId);
									} else if (tipoRecId == 16
											|| tipoRecId == 17) {
										loteRecId = findCcActoAgrupadoItem
												.getLoteRecId();
										anioRec = anioRecGenerar;
										personaRecId = findCcActoAgrupadoItem
												.getPersonaId();
										nroExpedienteRec = findCcActoAgrupadoItem
												.getNroExpediente();
										anioDeuda = findCcActoAgrupadoItem
												.getAnnoDeuda();
										tipoRec = tipoRecId;
										tipoDocRec = findCcActoAgrupadoItem
												.getTipoDocumentoId();
										loteExId = findCcActoAgrupadoItem
												.getLoteIdAnterior();
										actoRecId = findCcActoAgrupadoItem
												.getActoId();
										cobranzaCoactivaBo.generarRec(
												loteRecId, anioRec,
												personaRecId, nroExpedienteRec,
												anioDeuda, tipoRec, tipoDocRec,
												loteExId, actoRecId);
										historico = cobranzaCoactivaBo
												.getAllFindRecHistorico(
														loteExId, deudaExId,
														personaId);
										lista = cobranzaCoactivaBo
												.getAllFindCcLoteExp(loteExId);
									} else {
										addErrorMessage(getMsg("Deuda de REC Cancelada. Solo puede Generar REC de Suspension."));
									}
								} else if (tipoRecId == 16 || tipoRecId == 17) {
									loteRecId = findCcActoAgrupadoItem
											.getLoteRecId();
									anioRec = anioRecGenerar;
									personaRecId = findCcActoAgrupadoItem
											.getPersonaId();
									nroExpedienteRec = findCcActoAgrupadoItem
											.getNroExpediente();
									anioDeuda = findCcActoAgrupadoItem
											.getAnnoDeuda();
									tipoRec = tipoRecId;
									tipoDocRec = findCcActoAgrupadoItem
											.getTipoDocumentoId();
									loteExId = findCcActoAgrupadoItem
											.getLoteIdAnterior();
									actoRecId = findCcActoAgrupadoItem
											.getActoId();
									cobranzaCoactivaBo.generarRec(loteRecId,
											anioRec, personaRecId,
											nroExpedienteRec, anioDeuda,
											tipoRec, tipoDocRec, loteExId,
											actoRecId);
									historico = cobranzaCoactivaBo
											.getAllFindRecHistorico(loteExId,
													deudaExId, personaId);
									lista = cobranzaCoactivaBo
											.getAllFindCcLoteExp(loteExId);
								} else {
									addErrorMessage(getMsg("Solo Puede Generar RECs de suspension, Para Generar Otras RECs deben pasar 7 dias Habiles."));
								}
							}
						}
						/** Fin del condicional de las listas combinadas */
					} else {
						addErrorMessage(getMsg("Genere las Costas de la REC anterior."));
					}

				} else {
					addErrorMessage(getMsg("Ya existe una REC de este Tipo."));
				}
			} else {
				addErrorMessage(getMsg("No puede generar Nueva REC, primero Notifique la REC Anterior."));
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}

	}

	public void loadNotificacionById(ValueChangeEvent event) {
		try {
			HtmlComboBox combo = (HtmlComboBox) event.getComponent();
			String value = combo.getValue().toString();
			if (value != null) {
				noNotificacionId = (Integer) mapTipoNotificacion.get(value);
				setCmbValueNoNotificacion(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void notificarRec() throws Exception {
		try {
			// notificacion.setFechaNotificacion(DateUtil.dateToSqlTimestamp(fechaNotificacion));
			recId = findCcRecs.getRecId();
			Integer tipoRecIdNotificada = findCcRecs.getTipoRecId();
			if (tipoRecIdNotificada == 16 || tipoRecIdNotificada == 17) {
				setDisableBtnGeneraRec(true);
			} else {
				setDisableBtnGeneraRec(false);
			}
			if (findCcRecs.getFechaEmision() != null) {
				// Integer diasHabiles = obligacionBo.obtenerDiasHabiles(
				// findCcRecs.getFechaEmision(),
				// DateUtil.dateToSqlTimestamp(fechaNotificacion));
				// if (findCcRecs.getFechaEmision().compareTo(
				// DateUtil.dateToSqlTimestamp(fechaNotificacion)) <= 0
				// && diasHabiles <= 5) {
				if (Calendar
						.getInstance()
						.getTime()
						.compareTo(
								DateUtil.dateToSqlTimestamp(fechaNotificacion)) >= 0) {
					cobranzaCoactivaBo.actualizarNotificacionRec(recId, 1,
							notificadorId, fechaNotificacion);
					historico = cobranzaCoactivaBo.getAllFindRecHistorico(
							loteExId, deudaExId, personaId);
				} else {
					addErrorMessage(getMsg("Ingrese Correctamente la Fecha de Notificacion. No Fechas Futuras!!!"));
				}
			} else {
				addErrorMessage(getMsg("Ingrese Primero FECHA DE EMISION no se encontro en los EXCEL de MIGRACION.!!!"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void generacionRecInicioPdf() {
		try {
			if (lista != null && lista.size() > 0) {
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
					Integer val = lista.get(0).getLoteRecId().intValue();
					parameters.put("p_lote_id", val);
					parameters.put("ruta_imagen", path_imagen);
					parameters
							.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
					JasperPrint jasperPrint = JasperFillManager
							.fillReport(
									(SATWEBParameterFactory.getPathReporte() + "rec_inicio.jasper"),
									parameters, connection);
					ByteArrayOutputStream output = new ByteArrayOutputStream();
					JasperExportManager.exportReportToPdfStream(jasperPrint,
							output);
					HttpServletResponse response = (HttpServletResponse) FacesContext
							.getCurrentInstance().getExternalContext()
							.getResponse();
					response.setContentType("application/pdf");
					response.addHeader("Content-Disposition",
							"attachment;filename=coactivarecs.pdf");
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
				addErrorMessage(getMsg("Seleccione un Lote Exigible. Verifique!!!"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void buscar() {
		try {
			lista = new LinkedList<FindCcLoteDetalleActoExp>();
			if ((lote_id != null && lote_id > 0)) {
				lista = cobranzaCoactivaBo.getAllFindCcLoteExp(lote_id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	/** METODOS DE COSTAS Y GASTOS */
	/**
	 * Quita el elemento i de la grilla de obligaciones
	 * 
	 * @param i
	 */
	public void quitar() {

		debug("quitar - inicio");

		Iterator<Object> iter = selectObligacionDTO.getKeys();

		while (iter.hasNext()) {
			int i = (Integer) iter.next();
			montoTotal = montoTotal.subtract(listObligacionDTOs.get(i)
					.getMonto());
			listObligacionDTOs.remove(i);
		}

		debug("quitar - fin");
	}

	public void checkCostas(ValueChangeEvent event) {
		Boolean check = (Boolean) event.getNewValue();
		if (check == Boolean.TRUE) {
			// setChekcHabilitaCostas(true);
		} else {
			// setChekcHabilitaCostas(false);
		}
	}

	public void verDeudasDetenidas() {
		try {
			listVehicularDTOs = vehicularBo.findDjVehicular(personaId);
			listDjPredials = registroPrediosBo.getRpDjpredial(null, null, null,
					null, null, null, null, null, null, null, null, personaId,
					true);
			// lstDetencionDeudaDTOs = obligacionBo
			// .buscarDetenciones(getPersonaId(),getAnioDeuda());
			lstDetencionDeudaDTOs = obligacionBo.buscarDetenciones(personaId,
					anioDeuda, papeletaId);
		} catch (Exception ex) {
			WebMessages.messageError(ex.getMessage());
		}
	}

	public void llenarSubconcepto(String concepto) {
		// cmbConcepto = (String) e.getNewValue();
		setCmbConcepto(concepto);
		try {
			listSubConceptoDTOItems = new ArrayList<SelectItem>();

			List<SubConceptoDTO> listSubConceptoDTOs = new ArrayList<SubConceptoDTO>();

			subConceptoDTO = null;
			cmbSubConcepto = null;
			cmbTasa = null;
			setFlagViewDetalle(false);
			modoGastoMonto = 1;
			// resguardando el anno de afectacion
			// int annoAfectacion = obligacionDTO.getAnnoAfectacion();

			// reinicializando la data de ObligacionDTO
			obligacionDTO = new ObligacionDTO();
			//
			// // restaurando anno de afectacion y recId
			obligacionDTO.setAnnoAfectacion(anioRec);
			obligacionDTO.setPersonaId(personaId);

			if (cmbConcepto.equals("COSTAS")) {
				getObligacionDTO().setConceptoId(this.COSTAS);
				getObligacionDTO().setConceptoDescripcion("COSTAS");
				/**
				 * colocar aqui el subconcepto_id que se esta cargando para la
				 * rec
				 */
				// listSubConceptoDTOs =
				// obligacionBo.getSubConceptoCostas(findCcRecs.getAnioDeuda(),findCcRecs.getSubConceptoId());

				if (findCcRecs.getTipoRecId() == 9
						|| findCcRecs.getTipoRecId() == 10
						|| findCcRecs.getTipoRecId() == 11
						|| findCcRecs.getTipoRecId() == 12) {
					listSubConceptoDTOs = obligacionBo
							.getSubConceptoCostasEmbargo(anioRec);
				} else {
					listSubConceptoDTOs = obligacionBo.getSubConceptoCostas(
							anioRec, subconceptoId);
				}
			}

			if (cmbConcepto.equals("GASTOS")) {
				getObligacionDTO().setConceptoId(this.GASTOS);
				getObligacionDTO().setConceptoDescripcion("GASTOS");
				listSubConceptoDTOs = obligacionBo
						.getSubConceptoGastos(anioRec);
				// listSubConceptoDTOs =
				// obligacionBo.getSubConceptoGastos(findCcRecs.getAnioDeuda());
			}

			// if (cmbConcepto.equals("MULTAS")) {
			// getObligacionDTO().setConceptoId(this.MULTAS);
			// getObligacionDTO().setConceptoDescripcion("MULTAS");
			// // listSubConceptoDTOs =
			// // obligacionBo.getSubConceptoMultas(findCcRecs.getAnioDeuda());
			// listSubConceptoDTOs = obligacionBo
			// .getSubConceptoMultas(anioDeuda);
			// }
			//
			// if (cmbConcepto.equals("EPND")) {
			// getObligacionDTO().setConceptoId(this.EPND);
			// getObligacionDTO().setConceptoDescripcion("EPND");
			// listSubConceptoDTOs = obligacionBo
			// .getSubConceptoEPND(anioDeuda);
			// // listSubConceptoDTOs =
			// // obligacionBo.getSubConceptoEPND(findCcRecs.getAnioDeuda());
			// }
			//
			// if (cmbConcepto.equals("MULTA DRTPE")) {
			// getObligacionDTO().setConceptoId(this.MULTAS_DRTPE);
			// getObligacionDTO().setConceptoDescripcion("MULTA DRTPE");
			// listSubConceptoDTOs = obligacionBo
			// .getSubConceptoMULTASDRTPE(anioDeuda);
			// // listSubConceptoDTOs =
			// //
			// obligacionBo.getSubConceptoMULTASDRTPE(findCcRecs.getAnioDeuda());
			// }
			// getSessionManaged().getSessionMap().remove("findCcRecHistoricoItem");

			listSubConceptoDTOItems = null;
			listSubConceptoDTOItems = new ArrayList<SelectItem>();

			for (SubConceptoDTO subConceptoDTO : listSubConceptoDTOs) {

				listSubConceptoDTOItems.add(new SelectItem(subConceptoDTO
						.getDescripcionCorta(), subConceptoDTO
						.getDescripcionCorta()));
				mapSupConcepto.put(subConceptoDTO.getDescripcionCorta(),
						subConceptoDTO);
			}

			if (listSubConceptoDTOs.size() == 0 && !cmbConcepto.isEmpty()) {
				WebMessages
						.messageInfo("No se encontraron subconceptos asociados al aÃ±o de afectaciÃ³n ingresado y al concepto elegido");
				cmbConcepto = null;

			}
		} catch (SisatException e1) {
			WebMessages.messageError(e1.getMessage());
		}
	}

	/**
	 * Listener para detectar el cambio en el combobox Concepto
	 * 
	 * @param e
	 */
	public void changeListenerValueCmbConcepto(ValueChangeEvent e) {

		cmbConcepto = (String) e.getNewValue();

		try {
			listSubConceptoDTOItems = new ArrayList<SelectItem>();

			List<SubConceptoDTO> listSubConceptoDTOs = new ArrayList<SubConceptoDTO>();

			subConceptoDTO = null;
			cmbSubConcepto = null;
			cmbTasa = null;
			setFlagViewDetalle(false);
			modoGastoMonto = 1;
			// resguardando el anno de afectacion
			// int annoAfectacion = obligacionDTO.getAnnoAfectacion();

			// reinicializando la data de ObligacionDTO
			obligacionDTO = new ObligacionDTO();
			//
			// // restahurando anno de afectacion y recId
			obligacionDTO.setAnnoAfectacion(anioRec);
			obligacionDTO.setPersonaId(personaId);

			if (cmbConcepto.equals("COSTAS")) {
				getObligacionDTO().setConceptoId(this.COSTAS);
				getObligacionDTO().setConceptoDescripcion("COSTAS");
				/**
				 * colocar aqui el subconcepto_id que se esta cargando para la
				 * rec
				 */
				// listSubConceptoDTOs =
				// obligacionBo.getSubConceptoCostas(findCcRecs.getAnioDeuda(),findCcRecs.getSubConceptoId());
				listSubConceptoDTOs = obligacionBo.getSubConceptoCostas(
						anioDeuda, subconceptoId);
			}

			if (cmbConcepto.equals("GASTOS")) {
				getObligacionDTO().setConceptoId(this.GASTOS);
				getObligacionDTO().setConceptoDescripcion("GASTOS");
				listSubConceptoDTOs = obligacionBo
						.getSubConceptoGastos(anioRec);
				// listSubConceptoDTOs =
				// obligacionBo.getSubConceptoGastos(findCcRecs.getAnioDeuda());
			}

			// if (cmbConcepto.equals("MULTAS")) {
			// getObligacionDTO().setConceptoId(this.MULTAS);
			// getObligacionDTO().setConceptoDescripcion("MULTAS");
			// // listSubConceptoDTOs =
			// // obligacionBo.getSubConceptoMultas(findCcRecs.getAnioDeuda());
			// listSubConceptoDTOs = obligacionBo
			// .getSubConceptoMultas(anioDeuda);
			// }
			//
			// if (cmbConcepto.equals("EPND")) {
			// getObligacionDTO().setConceptoId(this.EPND);
			// getObligacionDTO().setConceptoDescripcion("EPND");
			// listSubConceptoDTOs = obligacionBo
			// .getSubConceptoEPND(anioDeuda);
			// // listSubConceptoDTOs =
			// // obligacionBo.getSubConceptoEPND(findCcRecs.getAnioDeuda());
			// }
			//
			// if (cmbConcepto.equals("MULTA DRTPE")) {
			// getObligacionDTO().setConceptoId(this.MULTAS_DRTPE);
			// getObligacionDTO().setConceptoDescripcion("MULTA DRTPE");
			// listSubConceptoDTOs = obligacionBo
			// .getSubConceptoMULTASDRTPE(anioDeuda);
			// // listSubConceptoDTOs =
			// //
			// obligacionBo.getSubConceptoMULTASDRTPE(findCcRecs.getAnioDeuda());
			// }
			// getSessionManaged().getSessionMap().remove("findCcRecHistoricoItem");

			listSubConceptoDTOItems = null;
			listSubConceptoDTOItems = new ArrayList<SelectItem>();

			for (SubConceptoDTO subConceptoDTO : listSubConceptoDTOs) {

				listSubConceptoDTOItems.add(new SelectItem(subConceptoDTO
						.getDescripcionCorta(), subConceptoDTO
						.getDescripcionCorta()));
				mapSupConcepto.put(subConceptoDTO.getDescripcionCorta(),
						subConceptoDTO);
			}

			if (listSubConceptoDTOs.size() == 0 && !cmbConcepto.isEmpty()) {
				WebMessages
						.messageInfo("No se encontraron subconceptos asociados al aÃ±o de afectaciÃ³n ingresado y al concepto elegido");
				cmbConcepto = null;

			}
		} catch (SisatException e1) {

			WebMessages.messageError(e1.getMessage());
		}

	}

	/**
	 * MÃ©todo que limpia el campo <code>codidoPlacaReferenciaObg</code> para
	 * quitar la eleccion de un predio o un vehiculo
	 */
	public void quitarPredioVehiculo() {
		debug("inicio - quitarPredioVehiculo");

		getObligacionDTO().setCodigoPlacaReferencia("");

		debug("fin - quitarPredioVehiculo");

	}

	/**
	 * Listener que detectar el cambio en el combobox sub-concepto
	 * 
	 * @param event
	 */
	public void changeListenerSubConcepto(ValueChangeEvent event) {

		debug("listenerSubConcepto - inicio");

		String key = (String) event.getNewValue();

		subConceptoDTO = mapSupConcepto.get(key);
		cmbSubConcepto = key;

		if (subConceptoDTO != null) {
			obligacionDTO.setConceptoId(subConceptoDTO.getConceptoId());
			obligacionDTO.setAnnoAfectacion(anioRec);
			getObligacionDTO().setTasa(subConceptoDTO.getValor());
			getObligacionDTO().setSubConceptoId(
					subConceptoDTO.getSubconceptoId());
			getObligacionDTO().setSubConceptoDescripcion(
					subConceptoDTO.getDescripcionCorta());
			if (getObligacionDTO().getTasa() != null) {
				getObligacionDTO().setMonto(
						this.obligacionDTO.getTasa().multiply(
								new BigDecimal(factor)));
			}
			setFlagViewDetalle(true);
		}

		debug("listenerSubConcepto - fin");
	}

	public void getListItemConcepto() {
		debug("getListItemConcepto - inicio");
		listSelectItems.add(new SelectItem("COSTAS", String
				.valueOf(this.COSTAS)));
		// listSelectItems.add(new SelectItem("EPND",
		// String.valueOf(this.EPND)));
		listSelectItems.add(new SelectItem("GASTOS", String
				.valueOf(this.GASTOS)));
		// listSelectItems.add(new SelectItem("MULTA DRTPE", String
		// .valueOf(this.MULTAS_DRTPE)));
		// listSelectItems.add(new SelectItem("MULTAS",
		// String.valueOf(this.MULTAS)));

		debug("getListItemConcepto - fin");

		// return listSelectItems;
	}

	/**
	 * Listener que detecta el cambio en el combobox tasa en la obligacion EPND
	 * 
	 * @param event
	 */
	public void changeListenerCmbTasaEPND(ValueChangeEvent event) {
		debug("changeListenerCmbTasaEPND - inicio");

		String q = (String) event.getNewValue();

		q = q.replace("%", "");

		BigDecimal b = new BigDecimal(q);

		b = b.divide(new BigDecimal("100"));

		getObligacionDTO().setTasa(b);

		if (getObligacionDTO().getBaseImponible() != null) {

			b = b.multiply(getObligacionDTO().getBaseImponible());

			getObligacionDTO().setImpuesto(b);

			getObligacionDTO().setMonto(b);
		}

		debug("changeListenerCmbTasaEPND - fin");
	}

	public void changeListenerSelectOneRadioCosta(ValueChangeEvent event) {
		debug("changeListenerCmbTasaEPND - inicio");

		if (modoGastoMonto == 1) {
			getObligacionDTO().setTasa(subConceptoDTO.getValor());
			getObligacionDTO().setMonto(
					this.obligacionDTO.getTasa().multiply(
							new BigDecimal(factor)));
		} else {
			getObligacionDTO().setTasa(subConceptoDTO.getValor());
			getObligacionDTO().setMonto(
					this.obligacionDTO.getTasa().multiply(
							new BigDecimal(factor)));
		}

		debug("changeListenerCmbTasaEPND - fin");
	}

	/**
	 * MÃ©todo que detecta el cambio en el campo base imponible, para luego
	 * actualizar el valor que aparecera en el campo impuesto.
	 * 
	 * @param event
	 */
	public void changeListenerInputBaseImponibleEPND(ValueChangeEvent event) {
		debug("changeListenerInputBaseImponibleEPND - inicio");

		BigDecimal b = (BigDecimal) event.getNewValue();

		getObligacionDTO().setBaseImponible(b);

		if (getObligacionDTO().getTasa() != null) {

			BigDecimal tasa = getObligacionDTO().getTasa();

			BigDecimal temp = tasa.multiply(getObligacionDTO()
					.getBaseImponible());

			getObligacionDTO().setImpuesto(temp);
			getObligacionDTO().setMonto(temp);

		}

		debug("changeListenerInputBaseImponibleEPND - fin");
	}

	/**
	 * MÃ©todo que realiza el registro que todos los items de la grilla, asi
	 * como el registro de las deudas generadas.
	 * 
	 * @throws Exception
	 */
	public void guardarItemsObligacion() throws Exception {
		debug("guardarObligacion - inicio");

		try {
			// obligacionBo.saveObligaciones(listObligacionDTOs,
			// getSessionManaged().getContribuyente().getPersonaId());
			obligacionBo.saveObligaciones(listObligacionDTOs,
					obligacionDTO.getPersonaId());
			flagValido = true;
			flagViewDetalle = false;
			subConceptoDTO = null;
			cmbSubConcepto = null;
			listSubConceptoDTOItems = new ArrayList<SelectItem>();
			cmbConcepto = null;
			obligacionDTO = null;
			cmbTasa = null;
			listObligacionDTOs.clear();
			montoTotal = new BigDecimal(0);
			// verDeudasDetenidas();
			listObligGeneradasDTOs = cobranzaCoactivaBo.getAllCostasXTipoRec(
					personaId, recId);
			// WebMessages.messageInfo("Se han registrado las obligaciones satisfactoriamente");

		} catch (SisatException e) {

			WebMessages.messageError(e.getMessage());
		}

		debug("guardarObligacion - fin");
	}

	public void changeListenerFechaEmisionCostas() {

		debug("changeListenerFechaEmisionCostas - inicio");

		Date d = (Date) getObligacionDTO().getFechaEmision().clone();
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.DAY_OF_MONTH, 1);
		d = c.getTime();

		getObligacionDTO().setFechaVencimiento(d);

		debug("changeListenerFechaEmisionCostas - fin");
	}

	public void activarFlagJustificacion() throws SisatException {
		setFlagJustificacion(false);
		setJustificacionDesbloqueo(null);
		int valList = historico.size();
		if (historico.get(valList - 1).getFechaEmision() != null) {
			Date fechaIni = historico.get(valList - 1).getFechaEmision();

			Calendar currDtCal = Calendar.getInstance();
			currDtCal.set(Calendar.SECOND, 0);
			currDtCal.set(Calendar.MILLISECOND, 0);
			currDtCal.set(Calendar.MINUTE, 0);
			currDtCal.set(Calendar.HOUR_OF_DAY, 0);
			Date fechActual = currDtCal.getTime();

			Calendar c = Calendar.getInstance();
			c.setTime(fechaIni);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.HOUR_OF_DAY, 0);
			Date fechaInicial = c.getTime();

			Date fechaNotif = historico.get(valList - 1).getFechaNotificacion();
			Calendar c1 = Calendar.getInstance();
			if (fechaNotif != null) {
				c1.setTime(fechaNotif);
				c1.set(Calendar.SECOND, 0);
				c1.set(Calendar.MILLISECOND, 0);
				c1.set(Calendar.MINUTE, 0);
				c1.set(Calendar.HOUR_OF_DAY, 0);
			}
			Date fechaNotifica = c1.getTime();
			/**
			 * Cuando no existe fecha notificacion hasta los 5 dias habiles
			 * despues de emitida la rec
			 */
			Date fechaMaxNotificacion = obligacionBo.sumarDiasHabiles(
					fechaInicial, 5);
			if ((fechActual.before(fechaMaxNotificacion)
					&& historico.get(valList - 1).getFechaNotificacion() == null && historico
					.get(valList - 1).getTipoRecId() == 1)
					|| (fechaInicial.compareTo(fechaNotifica) == 0 && historico
							.get(valList - 1).getTipoRecId() == 1)
					|| (fechaNotifica.compareTo(fechActual) == 0 && historico
							.get(valList - 1).getTipoRecId() == 1)) {
				setFlagJustificacion(true);
			} else if (historico.get(valList - 1).getTipoRecId() == 16
					|| historico.get(valList - 1).getTipoRecId() == 17) {
				setFlagJustificacion(false);
			} else {
				addErrorMessage(getMsg("Genere REC de Suspension para Finalizar el proceso de cobranza Coactiva.!!!"));
			}
		} else {
			addErrorMessage(getMsg("Verifique por que no tiene Fecha Emision. No se encontrÃ³ para cargar en la MigraciÃ³n.!!!"));
		}
	}

	public void desactivarDetencion(Integer personaId, Integer conceptoId,
			Integer subConceptoId, Integer anho, Integer determinacionId)
			throws Exception {
		try {
			if ((flagJustificacion == false && justificacionDesbloqueo == null)
					|| (flagJustificacion == true && justificacionDesbloqueo != null)) {
				int valList = historico.size();
				if (historico.get(valList - 1).getFechaEmision() != null) {
					Date fechaIni = historico.get(valList - 1)
							.getFechaEmision();

					Calendar currDtCal = Calendar.getInstance();
					currDtCal.set(Calendar.SECOND, 0);
					currDtCal.set(Calendar.MILLISECOND, 0);
					currDtCal.set(Calendar.MINUTE, 0);
					currDtCal.set(Calendar.HOUR_OF_DAY, 0);
					Date fechActual = currDtCal.getTime();

					Calendar c = Calendar.getInstance();
					c.setTime(fechaIni);
					c.set(Calendar.SECOND, 0);
					c.set(Calendar.MILLISECOND, 0);
					c.set(Calendar.MINUTE, 0);
					c.set(Calendar.HOUR_OF_DAY, 0);
					Date fechaInicial = c.getTime();

					Date fechaNotif = historico.get(valList - 1)
							.getFechaNotificacion();
					Calendar c1 = Calendar.getInstance();
					if (fechaNotif != null) {
						c1.setTime(fechaNotif);
						c1.set(Calendar.SECOND, 0);
						c1.set(Calendar.MILLISECOND, 0);
						c1.set(Calendar.MINUTE, 0);
						c1.set(Calendar.HOUR_OF_DAY, 0);
					}
					Date fechaNotifica = c1.getTime();
					/**
					 * Cuando no existe fecha notificacion hasta los 5 dias
					 * habiles despues de emitida la rec
					 */
					Date fechaMaxNotificacion = obligacionBo.sumarDiasHabiles(
							fechaInicial, 5);
					// Integer diasHabiles =
					// obligacionBo.obtenerDiasHabiles(historico.get(valList -
					// 1).getFechaEmision(), fechaInicial);
					String nroExped = null;
					if (findCcActoItem == null) {
						nroExped = findCcActoAgrupadoItem.getNroExpediente();
					} else if (findCcActoAgrupadoItem == null) {
						nroExped = findCcActoItem.getNroExpediente();
					}
					// String nroExped = (findCcActoItem.getNroExpediente() ==
					// null ? findCcActoAgrupadoItem
					// .getNroExpediente() : findCcActoItem
					// .getNroExpediente());
					if ((fechActual.before(fechaMaxNotificacion)
							&& historico.get(valList - 1)
									.getFechaNotificacion() == null
							&& historico.get(valList - 1).getTipoRecId() != 16 && historico
							.get(valList - 1).getTipoRecId() != 17)
							|| (fechaInicial.compareTo(fechaNotifica) == 0
									&& historico.get(valList - 1)
											.getTipoRecId() != 16 && historico
									.get(valList - 1).getTipoRecId() != 17)) {
						obligacionBo.desactivarDetencion(personaId, conceptoId,
								subConceptoId, anho, determinacionId, nroExped);
						lstDetencionDeudaDTOs = obligacionBo.buscarDetenciones(
								this.personaId, anho, papeletaId);
						/** Insertar Justificacion */
						cobranzaCoactivaBo.insertarJustificacion(personaId,
								conceptoId, subConceptoId, anho,
								determinacionId, nroExped,
								justificacionDesbloqueo);
						addInfoMessage(getMsg("Su Desbloqueo es bajo Responsabilidad ya fue reportado Sr(a) "
								+ getSessionManaged().getUser().getUsuario()
								+ ".!!!"));

					} else if (fechaMaxNotificacion.before(fechActual)
							&& historico.get(valList - 1)
									.getFechaNotificacion() == null) {
						addInfoMessage(getMsg("Pasaron 5 dÃ­as habiles desde la emision. La fecha de NotificaciÃ³n no ha sido Ingresada del Doc. FÃ­sico, SolicÃ­telo al responsable."));
					}
					/**
					 * Cuando existe fecha de Notificacion y se desea pagar el
					 * mismo dia de la notificacion
					 */

					else if (fechaNotifica.compareTo(fechActual) == 0
							&& historico.get(valList - 1).getTipoRecId() != 16
							&& historico.get(valList - 1).getTipoRecId() != 17) {
						obligacionBo.desactivarDetencion(personaId, conceptoId,
								subConceptoId, anho, determinacionId, nroExped);
						lstDetencionDeudaDTOs = obligacionBo.buscarDetenciones(
								this.personaId, anho, papeletaId);
						/** Insertar Justificacion */
						cobranzaCoactivaBo.insertarJustificacion(personaId,
								conceptoId, subConceptoId, anho,
								determinacionId, nroExped,
								justificacionDesbloqueo);
						addErrorMessage(getMsg("Su Desbloqueo es bajo Responsabilidad ya fue reportado Sr(a) "
								+ getSessionManaged().getUser().getUsuario()
								+ ".!!! Sucede solo si va ha pagar HOY, ya que aÃºn la notificaciÃ³n no surte efecto.!!!"));
					} else if (historico.get(valList - 1).getTipoRecId() == 16
							|| historico.get(valList - 1).getTipoRecId() == 17) {
						// validar si tiene costas de suspension generadas
						listObligGeneradasDTOs = cobranzaCoactivaBo
								.getAllCostasXTipoRec(personaId,
										historico.get(historico.size() - 1)
												.getRecId());
						if (listObligGeneradasDTOs.size() > 0) {
							obligacionBo.desactivarDetencion(personaId,
									conceptoId, subConceptoId, anho,
									determinacionId, nroExped);
							lstDetencionDeudaDTOs = obligacionBo
									.buscarDetenciones(this.personaId, anho,
											papeletaId);
						} else {
							addErrorMessage(getMsg("Genere Costas de Suspension para desbloquear. Verifique!!!"));
						}
					} else {
						addErrorMessage(getMsg("Genere Rec de Suspension para desbloquear. Verifique!!!"));
					}
				} else {
					addErrorMessage(getMsg("Verifique por que no tiene Fecha Emision. No se encontrÃ³ para cargar en la MigraciÃ³n.!!!"));
				}
			} else {
				addErrorMessage(getMsg("Ingrese JustificaciÃ³n por la deuda a desbloquear.!!!"));
			}
		} catch (SisatException e) {
			WebMessages.messageError(e.getMessage());
		}
	}

	public void activarDetencion(Integer personaId, Integer conceptoId,
			Integer subConceptoId, Integer anho, Integer determinacionId) {

		try {
			String nroExped = null;
			// String nroExped = (findCcActoItem.getNroExpediente() == null ?
			// findCcActoAgrupadoItem
			// .getNroExpediente() : findCcActoItem.getNroExpediente());
			if (findCcActoItem == null) {
				nroExped = findCcActoAgrupadoItem.getNroExpediente();
			} else if (findCcActoAgrupadoItem == null) {
				nroExped = findCcActoItem.getNroExpediente();
			}
			obligacionBo.activarDetencion(personaId, conceptoId, subConceptoId,
					anho, determinacionId, nroExped);
			lstDetencionDeudaDTOs = obligacionBo.buscarDetenciones(
					this.personaId, anioDeuda, papeletaId);

		} catch (SisatException e) {

			WebMessages.messageError(e.getMessage());
		}

	}

	public void changeListenerInputIdFactor(ValueChangeEvent event) {

		Integer b = (Integer) event.getNewValue();

		if (b != null) {
			this.factor = b;
			this.obligacionDTO.setMonto(this.obligacionDTO.getTasa().multiply(
					new BigDecimal(factor)));
		} else {
			this.factor = 0;
			this.obligacionDTO.setMonto(this.obligacionDTO.getTasa().multiply(
					new BigDecimal(factor)));
		}

	}

	public void changeListenerInputIdTasa(ValueChangeEvent event) {

		BigDecimal b = (BigDecimal) event.getNewValue();

		if (b != null) {
			obligacionDTO.setTasa(b);
			this.obligacionDTO.setMonto(this.obligacionDTO.getTasa().multiply(
					new BigDecimal(factor)));
		} else {
			obligacionDTO.setTasa(BigDecimal.ZERO);
			this.obligacionDTO.setMonto(this.obligacionDTO.getTasa().multiply(
					new BigDecimal(factor)));
		}

	}

	/**
	 * MÃ©todo que agrega una obligaciÃ³n a la grilla para luego ser procesada
	 */
	public void agregar() {
		boolean costaDuplicada = false;
		if (listObligGeneradasDTOs.size() > 0) {

			for (ObligacionDTO obligacionDTOItem : listObligGeneradasDTOs) {
				if (obligacionDTOItem.getConceptoDescripcion().toUpperCase()
						.equals(cmbConcepto)) {
					costaDuplicada = true;
					break;
				} else {
					costaDuplicada = false;
				}
			}
		}
		if (listObligacionDTOs.size() > 0) {
			for (ObligacionDTO obligacionAgregadasDTOItem : listObligacionDTOs) {
				if (obligacionAgregadasDTOItem.getConceptoDescripcion()
						.toUpperCase().equals(cmbConcepto)) {
					costaDuplicada = true;
					break;
				} else {
					costaDuplicada = false;
				}
			}
		}
		if (costaDuplicada == false) {
			if (cmbConcepto.equals("COSTAS")) {
				getObligacionDTO().setConceptoId(this.COSTAS);
				getObligacionDTO().setConceptoDescripcion("COSTAS");
			}

			getObligacionDTO().setNroPapeleta(getNroPapeleta());
			getObligacionDTO().setPapeletaId(getPapeletaId());
			subConceptoDTO.getConceptoId();
			obligacionDTO.setIdReferenciaREC(findCcRecs.getRecId());
			/**
			 * se inserta en el nro referencia de la cd_deuda
			 */
			if (obligacionDTO.getConceptoId() != EPND) {
				/**
				 * Realizando el parche, a solicitud de Sonia para el cambio de
				 * fecha emision por fecha liquidacion y la eliminacion de fecha
				 * de vencimiento
				 */
				obligacionDTO.setFechaVencimiento(obligacionDTO
						.getFechaEmision());
			}

			listObligacionDTOs.add(this.obligacionDTO);
			montoTotal = montoTotal.add(obligacionDTO.getMonto());
			subConceptoDTO = null;
			cmbSubConcepto = null;

			// //resguardando el anno de afectacion y personaId
			// int annoAfectacion = obligacionDTO.gtAnnoAfectacion();
			// int personaId=obligacionDTO.getPersonaId();
			// reinicializando la data de ObligacionDTO
			obligacionDTO = new ObligacionDTO();
			// restaurando anno de afectacion y personaId
			obligacionDTO.setAnnoAfectacion(anioRec);
			obligacionDTO.setPersonaId(personaId);

			flagValido = true;
			setFlagViewDetalle(false);
			subConceptoDTO = null;
			cmbSubConcepto = null;
			listSubConceptoDTOItems = new ArrayList<SelectItem>();
			cmbConcepto = null;
			factor = 1;
		} else {
			addErrorMessage(getMsg("No Puede Generar Costas o Gastos Duplicados. Verifique!!!"));
		}

	}

	/** FIN COSTAS Y GASTOS */
	public void exportarTablaExcel() {

		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("Exportando a Excel");
		int rowIndex = 1;
		Row row1 = sheet.createRow(0);
		row1.createCell(0).setCellValue("PersonaId");
		row1.createCell(1).setCellValue("Doc. Identidad");
		row1.createCell(2).setCellValue("Apellidos y Nombres");
		row1.createCell(3).setCellValue("Tipo Valor");
		row1.createCell(4).setCellValue("Nro. Valor");
		row1.createCell(5).setCellValue("Total REC1");
		row1.createCell(6).setCellValue("DirecciÃ³n Fiscal");
		row1.createCell(7).setCellValue(
				!lista.get(0).getSector().equals("-") ? "Sector"
						: "Nro. Papeleta");
		row1.createCell(8).setCellValue("Nro. Expediente");
		row1.createCell(9).setCellValue("Fecha Notificacion");
		row1.createCell(10).setCellValue("Estado_Deuda");
		row1.createCell(11).setCellValue("Estado_Expediente");
		row1.createCell(12).setCellValue("Ultima REC. Emitida.");
		row1.createCell(13).setCellValue("Placa(s)");

		for (FindCcLoteDetalleActoExp data : lista) {
			Row row = sheet.createRow(rowIndex++);
			int columnIndex = 0;

			row.createCell(columnIndex++).setCellValue(data.getPersonaId());
			row.createCell(columnIndex++).setCellValue(data.getNroDocumento());
			row.createCell(columnIndex++).setCellValue(
					data.getApellidosNombres());
			row.createCell(columnIndex++).setCellValue(
					data.getTipoActoDescripcionCorta() + "-"
							+ data.getConcepto());
			row.createCell(columnIndex++).setCellValue(data.getNroActo());
			row.createCell(columnIndex++).setCellValue(
					String.valueOf(data.getMontoDeuda().doubleValue()));
			row.createCell(columnIndex++).setCellValue(data.getDireccion());
			if (!data.getSector().equals("-")) {
				row.createCell(columnIndex++).setCellValue(data.getSector());
			} else {
				row.createCell(columnIndex++).setCellValue(
						data.getNroPapeleta());
			}
			row.createCell(columnIndex++).setCellValue(
					data.getNroExpediente().toString());

			if (data.getFechaNotificacion() != null) {
				row.createCell(columnIndex++).setCellValue(
						DateUtil.convertDateToString(data
								.getFechaNotificacion()));
			} else {
				row.createCell(columnIndex++).setCellValue("-");
			}
			row.createCell(columnIndex++).setCellValue(data.getEstado_deuda());
			row.createCell(columnIndex++).setCellValue(
					data.getEstadoExpediente());
			row.createCell(columnIndex++).setCellValue(
					data.getUltimaRecEmitida());
			row.createCell(columnIndex++).setCellValue(data.getPlaca());
		}
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		externalContext.responseReset();
		externalContext.setResponseContentType("application/vnd.ms-excel");
		externalContext.setResponseHeader("Content-Disposition",
				"attachment;filename=exportando_a_excel.xls");
		try {
			workbook.write(externalContext.getResponseOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		context.responseComplete(); // Prevent JSF from performing navigation.
	}

	public void changeListenerTxtBuscarAdministrado(ValueChangeEvent e) {
		this.codigoPersonaBuscar = (Integer) e.getNewValue();
	}

	public void changeListenerTxtAnteriorAdministrado(ValueChangeEvent e) {
		this.codigoAnteriorPersona = (String) e.getNewValue();
	}

	public void changeListenerTxtBuscarXPlaca(ValueChangeEvent e) {
		this.placaVehiculoBuscar = (String) e.getNewValue();
	}

	public Integer getLote_id() {
		return lote_id;
	}

	public void setLote_id(Integer lote_id) {
		this.lote_id = lote_id;
	}

	public String getNroValor() {
		return nroValor;
	}

	public void setNroValor(String nroValor) {
		this.nroValor = nroValor;
	}

	public List<FindCcLoteDetalleActoExp> getLista() {
		return lista;
	}

	public void setLista(List<FindCcLoteDetalleActoExp> lista) {
		this.lista = lista;
	}

	public List<FindCcLoteExigible> getLotes() {
		return lotes;
	}

	public void setLotes(List<FindCcLoteExigible> lotes) {
		this.lotes = lotes;
	}

	public FindCcLoteDetalleActo getSelectedRol() {
		return selectedRol;
	}

	public void setSelectedRol(FindCcLoteDetalleActo selectedRol) {
		this.selectedRol = selectedRol;
	}

	public SimpleSelection getSelectedUsuario() {
		return selectedUsuario;
	}

	public void setSelectedUsuario(SimpleSelection selectedUsuario) {
		this.selectedUsuario = selectedUsuario;
	}

	public SimpleSelection getSimpleSelectedRol() {
		return simpleSelectedRol;
	}

	public void setSimpleSelectedRol(SimpleSelection simpleSelectedRol) {
		this.simpleSelectedRol = simpleSelectedRol;
	}

	public List<SelectItem> getSelectItems() {
		return selectItems;
	}

	public void setSelectItems(List<SelectItem> selectItems) {
		this.selectItems = selectItems;
	}

	public String getVendorFilter() {
		return vendorFilter;
	}

	public void setVendorFilter(String vendorFilter) {
		this.vendorFilter = vendorFilter;
	}

	public String getSectorFilter() {
		return sectorFilter;
	}

	public void setSectorFilter(String sectorFilter) {
		this.sectorFilter = sectorFilter;
	}

	public List<SelectItem> getSelectSector() {
		return selectSector;
	}

	public void setSelectSector(List<SelectItem> selectSector) {
		this.selectSector = selectSector;
	}

	public List<FindCcRecHistorico> getHistorico() {
		return historico;
	}

	public void setHistorico(List<FindCcRecHistorico> historico) {
		this.historico = historico;
	}

	public FindCcLoteDetalleActoExp getFindCcActoItem() {
		return findCcActoItem;
	}

	public void setFindCcActoItem(FindCcLoteDetalleActoExp findCcActoItem) {
		this.findCcActoItem = findCcActoItem;
	}

	public Integer getDeudaExId() {
		return deudaExId;
	}

	public void setDeudaExId(Integer deudaExId) {
		this.deudaExId = deudaExId;
	}

	public Integer getPersonaId() {
		return personaId;
	}

	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}

	public Integer getLoteExigibleId() {
		return loteExigibleId;
	}

	public void setLoteExigibleId(Integer loteExigibleId) {
		this.loteExigibleId = loteExigibleId;
	}

	public Integer getAnioLote() {
		return anioLote;
	}

	public void setAnioLote(Integer anioLote) {
		this.anioLote = anioLote;
	}

	public Integer getLoteExId() {
		return loteExId;
	}

	public void setLoteExId(Integer loteExId) {
		this.loteExId = loteExId;
	}

	public Integer getTipoActoId() {
		return tipoActoId;
	}

	public void setTipoActoId(Integer tipoActoId) {
		this.tipoActoId = tipoActoId;
	}

	public List<SelectItem> getListaTipoRec() {
		return listaTipoRec;
	}

	public void setListaTipoRec(List<SelectItem> listaTipoRec) {
		this.listaTipoRec = listaTipoRec;
	}

	public String getCmbTipoRec() {
		return cmbTipoRec;
	}

	public void setCmbTipoRec(String cmbTipoRec) {
		this.cmbTipoRec = cmbTipoRec;
	}

	public HashMap<String, Integer> getMapTipoRec() {
		return mapTipoRec;
	}

	public void setMapTipoRec(HashMap<String, Integer> mapTipoRec) {
		this.mapTipoRec = mapTipoRec;
	}

	public HtmlComboBox getCmbNoTipoRec() {
		return cmbNoTipoRec;
	}

	public void setCmbNoTipoRec(HtmlComboBox cmbNoTipoRec) {
		this.cmbNoTipoRec = cmbNoTipoRec;
	}

	public String getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}

	public Integer getLoteAnteriorId() {
		return loteAnteriorId;
	}

	public void setLoteAnteriorId(Integer loteAnteriorId) {
		this.loteAnteriorId = loteAnteriorId;
	}

	public FindCcRecHistorico getFindCcRecs() {
		return findCcRecs;
	}

	public void setFindCcRecs(FindCcRecHistorico findCcRecs) {
		this.findCcRecs = findCcRecs;
	}

	public String getCmbValueNoNotificacion() {
		return cmbValueNoNotificacion;
	}

	public void setCmbValueNoNotificacion(String cmbValueNoNotificacion) {
		this.cmbValueNoNotificacion = cmbValueNoNotificacion;
	}

	public HtmlComboBox getCmbNoNotificacion() {
		return cmbNoNotificacion;
	}

	public void setCmbNoNotificacion(HtmlComboBox cmbNoNotificacion) {
		this.cmbNoNotificacion = cmbNoNotificacion;
	}

	public Integer getNoNotificacionId() {
		return noNotificacionId;
	}

	public void setNoNotificacionId(Integer noNotificacionId) {
		this.noNotificacionId = noNotificacionId;
	}

	public HashMap<String, Integer> getMapTipoNotificacion() {
		return mapTipoNotificacion;
	}

	public void setMapTipoNotificacion(
			HashMap<String, Integer> mapTipoNotificacion) {
		this.mapTipoNotificacion = mapTipoNotificacion;
	}

	public List<SelectItem> getLstTipoNotificacion() {
		return lstTipoNotificacion;
	}

	public void setLstTipoNotificacion(List<SelectItem> lstTipoNotificacion) {
		this.lstTipoNotificacion = lstTipoNotificacion;
	}

	public String getCmbNotificador() {
		return cmbNotificador;
	}

	public void setCmbNotificador(String cmbNotificador) {
		this.cmbNotificador = cmbNotificador;
	}

	public HtmlComboBox getCmbHtmlNotificador() {
		return cmbHtmlNotificador;
	}

	public void setCmbHtmlNotificador(HtmlComboBox cmbHtmlNotificador) {
		this.cmbHtmlNotificador = cmbHtmlNotificador;
	}

	public Integer getNotificadorId() {
		return notificadorId;
	}

	public void setNotificadorId(Integer notificadorId) {
		this.notificadorId = notificadorId;
	}

	public HashMap<String, Integer> getMapNotificador() {
		return mapNotificador;
	}

	public void setMapNotificador(HashMap<String, Integer> mapNotificador) {
		this.mapNotificador = mapNotificador;
	}

	public List<SelectItem> getLstSelectItemsNotificador() {
		return lstSelectItemsNotificador;
	}

	public void setLstSelectItemsNotificador(
			List<SelectItem> lstSelectItemsNotificador) {
		this.lstSelectItemsNotificador = lstSelectItemsNotificador;
	}

	public Integer getLoteRecId() {
		return loteRecId;
	}

	public void setLoteRecId(Integer loteRecId) {
		this.loteRecId = loteRecId;
	}

	public Integer getAnioRec() {
		return anioRec;
	}

	public void setAnioRec(Integer anioRec) {
		this.anioRec = anioRec;
	}

	public Integer getPersonaRecId() {
		return personaRecId;
	}

	public void setPersonaRecId(Integer personaRecId) {
		this.personaRecId = personaRecId;
	}

	public String getNroExpedienteRec() {
		return nroExpedienteRec;
	}

	public void setNroExpedienteRec(String nroExpedienteRec) {
		this.nroExpedienteRec = nroExpedienteRec;
	}

	public Integer getTipoRec() {
		return tipoRec;
	}

	public void setTipoRec(Integer tipoRec) {
		this.tipoRec = tipoRec;
	}

	public Integer getTipoDocRec() {
		return tipoDocRec;
	}

	public void setTipoDocRec(Integer tipoDocRec) {
		this.tipoDocRec = tipoDocRec;
	}

	public Integer getActoRecId() {
		return actoRecId;
	}

	public void setActoRecId(Integer actoRecId) {
		this.actoRecId = actoRecId;
	}

	public Integer getAnioDeuda() {
		return anioDeuda;
	}

	public void setAnioDeuda(Integer anioDeuda) {
		this.anioDeuda = anioDeuda;
	}

	public Integer getTipoRecId() {
		return tipoRecId;
	}

	public void setTipoRecId(Integer tipoRecId) {
		this.tipoRecId = tipoRecId;
	}

	public HtmlComboBox getCmbNoTipoRecMasivo() {
		return cmbNoTipoRecMasivo;
	}

	public void setCmbNoTipoRecMasivo(HtmlComboBox cmbNoTipoRecMasivo) {
		this.cmbNoTipoRecMasivo = cmbNoTipoRecMasivo;
	}

	public Integer getRecId() {
		return recId;
	}

	public void setRecId(Integer recId) {
		this.recId = recId;
	}

	public List<ObligacionDTO> getListObligacionDTOs() {
		return listObligacionDTOs;
	}

	public void setListObligacionDTOs(List<ObligacionDTO> listObligacionDTOs) {
		this.listObligacionDTOs = listObligacionDTOs;
	}

	public List<BuscarVehicularDTO> getListVehicularDTOs() {
		return listVehicularDTOs;
	}

	public void setListVehicularDTOs(List<BuscarVehicularDTO> listVehicularDTOs) {
		this.listVehicularDTOs = listVehicularDTOs;
	}

	public List<FindRpDjPredial> getListDjPredials() {
		return listDjPredials;
	}

	public void setListDjPredials(List<FindRpDjPredial> listDjPredials) {
		this.listDjPredials = listDjPredials;
	}

	public SubConceptoDTO getSubConceptoDTO() {
		return subConceptoDTO;
	}

	public void setSubConceptoDTO(SubConceptoDTO subConceptoDTO) {
		this.subConceptoDTO = subConceptoDTO;
	}

	public List<SelectItem> getListSubConceptoDTOItems() {
		return listSubConceptoDTOItems;
	}

	public void setListSubConceptoDTOItems(
			List<SelectItem> listSubConceptoDTOItems) {
		this.listSubConceptoDTOItems = listSubConceptoDTOItems;
	}

	public ObligacionDTO getObligacionDTO() {
		return obligacionDTO;
	}

	public void setObligacionDTO(ObligacionDTO obligacionDTO) {
		this.obligacionDTO = obligacionDTO;
	}

	public BigDecimal getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(BigDecimal montoTotal) {
		this.montoTotal = montoTotal;
	}

	public SimpleSelection getSelectObligacionDTO() {
		return selectObligacionDTO;
	}

	public void setSelectObligacionDTO(SimpleSelection selectObligacionDTO) {
		this.selectObligacionDTO = selectObligacionDTO;
	}

	public String getNroPapeleta() {
		return nroPapeleta;
	}

	public void setNroPapeleta(String nroPapeleta) {
		this.nroPapeleta = nroPapeleta;
	}

	public List<DetencionDeudaDTO> getLstDetencionDeudaDTOs() {
		return lstDetencionDeudaDTOs;
	}

	public void setLstDetencionDeudaDTOs(
			List<DetencionDeudaDTO> lstDetencionDeudaDTOs) {
		this.lstDetencionDeudaDTOs = lstDetencionDeudaDTOs;
	}

	public Integer getFactor() {
		return factor;
	}

	public void setFactor(Integer factor) {
		this.factor = factor;
	}

	public boolean isFlagValido() {
		return flagValido;
	}

	public void setFlagValido(boolean flagValido) {
		this.flagValido = flagValido;
	}

	public boolean isFlagViewDetalle() {
		return flagViewDetalle;
	}

	public void setFlagViewDetalle(boolean flagViewDetalle) {
		this.flagViewDetalle = flagViewDetalle;
	}

	public String getCmbConcepto() {
		return cmbConcepto;
	}

	public void setCmbConcepto(String cmbConcepto) {
		this.cmbConcepto = cmbConcepto;
	}

	public String getCmbSubConcepto() {
		return cmbSubConcepto;
	}

	public void setCmbSubConcepto(String cmbSubConcepto) {
		this.cmbSubConcepto = cmbSubConcepto;
	}

	public String getCmbTasa() {
		return cmbTasa;
	}

	public void setCmbTasa(String cmbTasa) {
		this.cmbTasa = cmbTasa;
	}

	public int getModoGastoMonto() {
		return modoGastoMonto;
	}

	public void setModoGastoMonto(int modoGastoMonto) {
		this.modoGastoMonto = modoGastoMonto;
	}

	public Integer getSubconceptoId() {
		return subconceptoId;
	}

	public void setSubconceptoId(Integer subconceptoId) {
		this.subconceptoId = subconceptoId;
	}

	public List<FindCcRecTipo> getListaRecMasiva() {
		return listaRecMasiva;
	}

	public void setListaRecMasiva(List<FindCcRecTipo> listaRecMasiva) {
		this.listaRecMasiva = listaRecMasiva;
	}

	public List<SelectItem> getListSelectItems() {
		return listSelectItems;
	}

	public void setListSelectItems(List<SelectItem> listSelectItems) {
		this.listSelectItems = listSelectItems;
	}

	public FindCcRecTipo getFindCcTipoRecs() {
		return findCcTipoRecs;
	}

	public void setFindCcTipoRecs(FindCcRecTipo findCcTipoRecs) {
		this.findCcTipoRecs = findCcTipoRecs;
	}

	public boolean isDesactivaRecsInicio() {
		return desactivaRecsInicio;
	}

	public void setDesactivaRecsInicio(boolean desactivaRecsInicio) {
		this.desactivaRecsInicio = desactivaRecsInicio;
	}

	public java.util.Date getFechaNotificacion() {
		return fechaNotificacion;
	}

	public void setFechaNotificacion(java.util.Date fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}

	public Integer getCodigoPersonaBuscar() {
		return codigoPersonaBuscar;
	}

	public void setCodigoPersonaBuscar(Integer codigoPersonaBuscar) {
		this.codigoPersonaBuscar = codigoPersonaBuscar;
	}

	public List<FindCcLoteDetalleActoExp> getListaExpedienteAgrupados() {
		return listaExpedienteAgrupados;
	}

	public void setListaExpedienteAgrupados(
			List<FindCcLoteDetalleActoExp> listaExpedienteAgrupados) {
		this.listaExpedienteAgrupados = listaExpedienteAgrupados;
	}

	public SimpleSelection getSelectedRowLotes() {
		return selectedRowLotes;
	}

	public void setSelectedRowLotes(SimpleSelection selectedRowLotes) {
		this.selectedRowLotes = selectedRowLotes;
	}

	public boolean isDisableBtnGeneraRec() {
		return disableBtnGeneraRec;
	}

	public void setDisableBtnGeneraRec(boolean disableBtnGeneraRec) {
		this.disableBtnGeneraRec = disableBtnGeneraRec;
	}

	public List<ObligacionDTO> getListObligGeneradasDTOs() {
		return listObligGeneradasDTOs;
	}

	public void setListObligGeneradasDTOs(
			List<ObligacionDTO> listObligGeneradasDTOs) {
		this.listObligGeneradasDTOs = listObligGeneradasDTOs;
	}

	public Integer getNroRegistrosGenerar() {
		return nroRegistrosGenerar;
	}

	public void setNroRegistrosGenerar(Integer nroRegistrosGenerar) {
		this.nroRegistrosGenerar = nroRegistrosGenerar;
	}

	public String getCodicionAdministrado() {
		return codicionAdministrado;
	}

	public void setCodicionAdministrado(String codicionAdministrado) {
		this.codicionAdministrado = codicionAdministrado;
	}

	public HtmlComboBox getCmbCondicionAdministrado() {
		return cmbCondicionAdministrado;
	}

	public void setCmbCondicionAdministrado(
			HtmlComboBox cmbCondicionAdministrado) {
		this.cmbCondicionAdministrado = cmbCondicionAdministrado;
	}

	public boolean isDisableBtnGeneraCostas() {
		return disableBtnGeneraCostas;
	}

	public void setDisableBtnGeneraCostas(boolean disableBtnGeneraCostas) {
		this.disableBtnGeneraCostas = disableBtnGeneraCostas;
	}

	public Integer getPapeletaId() {
		return papeletaId;
	}

	public void setPapeletaId(Integer papeletaId) {
		this.papeletaId = papeletaId;
	}

	public java.util.Date getFechaEmisionRecMigradas() {
		return fechaEmisionRecMigradas;
	}

	public void setFechaEmisionRecMigradas(
			java.util.Date fechaEmisionRecMigradas) {
		this.fechaEmisionRecMigradas = fechaEmisionRecMigradas;
	}

	public FindCcLoteExigible getFindCcLoteItem() {
		return findCcLoteItem;
	}

	public void setFindCcLoteItem(FindCcLoteExigible findCcLoteItem) {
		this.findCcLoteItem = findCcLoteItem;
	}

	public String getPlacaVehiculoBuscar() {
		return placaVehiculoBuscar;
	}

	public void setPlacaVehiculoBuscar(String placaVehiculoBuscar) {
		this.placaVehiculoBuscar = placaVehiculoBuscar;
	}

	public FindCcLoteDetalleActoExp getFindCcActoAgrupadoItem() {
		return findCcActoAgrupadoItem;
	}

	public void setFindCcActoAgrupadoItem(
			FindCcLoteDetalleActoExp findCcActoAgrupadoItem) {
		this.findCcActoAgrupadoItem = findCcActoAgrupadoItem;
	}

	public Integer getConceptoId() {
		return conceptoId;
	}

	public void setConceptoId(Integer conceptoId) {
		this.conceptoId = conceptoId;
	}

	public boolean isBooleanRecsMasiva() {
		return booleanRecsMasiva;
	}

	public void setBooleanRecsMasiva(boolean booleanRecsMasiva) {
		this.booleanRecsMasiva = booleanRecsMasiva;
	}

	public Integer getTipoDocumentoAgrupados() {
		return tipoDocumentoAgrupados;
	}

	public void setTipoDocumentoAgrupados(Integer tipoDocumentoAgrupados) {
		this.tipoDocumentoAgrupados = tipoDocumentoAgrupados;
	}

	public Integer getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(Integer tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public DetencionDeudaDTO getDetencionesDeudasItem() {
		return detencionesDeudasItem;
	}

	public void setDetencionesDeudasItem(DetencionDeudaDTO detencionesDeudasItem) {
		this.detencionesDeudasItem = detencionesDeudasItem;
	}

	public boolean isFlagJustificacion() {
		return flagJustificacion;
	}

	public void setFlagJustificacion(boolean flagJustificacion) {
		this.flagJustificacion = flagJustificacion;
	}

	public String getJustificacionDesbloqueo() {
		return justificacionDesbloqueo;
	}

	public void setJustificacionDesbloqueo(String justificacionDesbloqueo) {
		this.justificacionDesbloqueo = justificacionDesbloqueo;
	}

	public String getNroResolucion() {
		return nroResolucion;
	}

	public void setNroResolucion(String nroResolucion) {
		this.nroResolucion = nroResolucion;
	}

	public String getMotivoCancelacion() {
		return motivoCancelacion;
	}

	public void setMotivoCancelacion(String motivoCancelacion) {
		this.motivoCancelacion = motivoCancelacion;
	}

	public String getEstadoCancelacion() {
		return estadoCancelacion;
	}

	public void setEstadoCancelacion(String estadoCancelacion) {
		this.estadoCancelacion = estadoCancelacion;
	}

	// public SelectItem[] getListEstadosCancelacion() {
	// return listEstadosCancelacion;
	// }
	//
	// public void setListEstadosCancelacion(SelectItem[]
	// listEstadosCancelacion) {
	// this.listEstadosCancelacion = listEstadosCancelacion;
	// }

	public HtmlComboBox getCmbHtmlEstadoSusp() {
		return cmbHtmlEstadoSusp;
	}

	public void setCmbHtmlEstadoSusp(HtmlComboBox cmbHtmlEstadoSusp) {
		this.cmbHtmlEstadoSusp = cmbHtmlEstadoSusp;
	}

	public String getCodigoAnteriorPersona() {
		return codigoAnteriorPersona;
	}

	public void setCodigoAnteriorPersona(String codigoAnteriorPersona) {
		this.codigoAnteriorPersona = codigoAnteriorPersona;
	}

	public List<SimpleMenuDTO> getListPermisosSubmenu() {
		return listPermisosSubmenu;
	}

	public void setListPermisosSubmenu(List<SimpleMenuDTO> listPermisosSubmenu) {
		this.listPermisosSubmenu = listPermisosSubmenu;
	}

	public boolean isPermisoBuscar() {
		return permisoBuscar;
	}

	public void setPermisoBuscar(boolean permisoBuscar) {
		this.permisoBuscar = permisoBuscar;
	}

	public boolean isPermisoVerDetalle() {
		return permisoVerDetalle;
	}

	public void setPermisoVerDetalle(boolean permisoVerDetalle) {
		this.permisoVerDetalle = permisoVerDetalle;
	}

	public boolean isPermisoModificarActualizar() {
		return permisoModificarActualizar;
	}

	public void setPermisoModificarActualizar(boolean permisoModificarActualizar) {
		this.permisoModificarActualizar = permisoModificarActualizar;
	}

	public boolean isPermisoGenerarRECInicio() {
		return permisoGenerarRECInicio;
	}

	public void setPermisoGenerarRECInicio(boolean permisoGenerarRECInicio) {
		this.permisoGenerarRECInicio = permisoGenerarRECInicio;
	}

	public boolean isPermisoGenerarMasRECs() {
		return permisoGenerarMasRECs;
	}

	public void setPermisoGenerarMasRECs(boolean permisoGenerarMasRECs) {
		this.permisoGenerarMasRECs = permisoGenerarMasRECs;
	}
	
}
