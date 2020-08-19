package com.sat.sisat.tramitedocumentario.managed;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.FileUpload;
import com.sat.sisat.common.util.SATWEBParameterFactory;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.menus.business.MenuBoRemote;
import com.sat.sisat.menus.dto.SimpleMenuDTO;
import com.sat.sisat.persistence.CrudServiceBean;
import com.sat.sisat.persistence.entity.GnTipoDocumento;
import com.sat.sisat.persistence.entity.GnUnidad;
import com.sat.sisat.persistence.entity.TdDataValor;
import com.sat.sisat.persistence.entity.TdDjConyuge;
import com.sat.sisat.persistence.entity.TdDjUnicaPropiedad;
import com.sat.sisat.persistence.entity.TdDocumentoAnexo;
import com.sat.sisat.persistence.entity.TdDocumentoTramite;
import com.sat.sisat.persistence.entity.TdEstadoExpediente;
import com.sat.sisat.persistence.entity.TdExpediente;
import com.sat.sisat.persistence.entity.TdRepresentante;
import com.sat.sisat.persistence.entity.TdRequisito;
import com.sat.sisat.persistence.entity.TdRequisitoExpediente;
import com.sat.sisat.persistence.entity.TdTipoTramite;
import com.sat.sisat.persistence.entity.TdSituacionExpediente;
import com.sat.sisat.persistence.entity.MpRelacionado;
import com.sat.sisat.persistence.entity.TdDJ;
import com.sat.sisat.persona.business.PersonaBoRemote;
import com.sat.sisat.predial.dto.UbicacionDTO;
import com.sat.sisat.tramitedocumentario.bussiness.TramiteDocumentarioBo;
import com.sat.sisat.tramitedocumentario.bussiness.TramiteDocumentarioBoRemote;
import com.sat.sisat.tramitedocumentario.dto.BusquedaExpedienteDTO;
import com.sat.sisat.tramitedocumentario.dto.DeclaracionJuradaAdultDTO;
import com.sat.sisat.tramitedocumentario.dto.ItemBandejaEntradaDTO;
import com.sat.sisat.tramitedocumentario.dto.ItemHistoricoEntradaDTO;
import com.sat.sisat.tramitedocumentario.dto.ItemSeguimientoEntradaDTO;
import com.sat.sisat.tramitedocumentario.dto.ItemSeguimientoExpedienteDTO;
import com.sat.sisat.tramitedocumentario.dto.RelacionadosDTO;
import com.sat.sisat.tramitedocumentario.dto.RemitenteDTO;
import com.sat.sisat.tramitedocumentario.dto.RequisitoExpededienteDTO;
import com.sat.sisat.tramitedocumentario.managed.layout.ViaManaged;
import com.sat.sisat.vehicular.dto.BuscarPersonaDTO;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@ManagedBean
@ViewScoped
public class RegistroTramiteManaged extends BaseManaged implements ViaManaged {

private BusquedaExpedienteDTO busquedaExpedienteDTO = new BusquedaExpedienteDTO();

private DeclaracionJuradaAdultDTO busquedaDjAdultDTO = new DeclaracionJuradaAdultDTO();

private MpRelacionado relacionado = new MpRelacionado();

private TdDjUnicaPropiedad unicaPropiedad = new TdDjUnicaPropiedad();

	
	private static final long serialVersionUID = -4121173506281562868L;

	private List<AnexoDTO> listAnexoDTOs = new ArrayList<AnexoDTO>();

	private TdExpediente tdExpediente = new TdExpediente();
	
	private TdDJ tdDj = new TdDJ();
	
	private TdDjConyuge tdDjConyuge = new TdDjConyuge();
	
	private TdRequisitoExpediente tdRequisitoExpediente = new TdRequisitoExpediente();

	private TdDjUnicaPropiedad tdDjUnicaPropiedad = new TdDjUnicaPropiedad();
	
	private TdEstadoExpediente tdEstadoExpediente = new TdEstadoExpediente();
	
	private TdSituacionExpediente tdSituacionExpediente = new TdSituacionExpediente();

	private int indexTblAnexo = -1;

	private HashMap<String, Integer> hashMapTdTipoTramite = new HashMap<String, Integer>();

	private List<TdDocumentoTramite> listTdDocumentoTramites;
	
	//Servicios Adulto mayor y pensionista
	
	private List<TdDocumentoTramite> listTdDocumentoTramitesAdultPens;

	private HashMap<String, Integer> hashMapTdDocumentoTramite = new HashMap<String, Integer>();

	private List<TdTipoTramite> listTdTipoTramites;
	
	//servicios adulto mayor

	private List<TdTipoTramite> listTdTipoTramitesAdult;

	
	private List<SelectItem> listSelectItemTdTipoTramite = new ArrayList<SelectItem>();
	
	//servicios adulto mayor
	private List<SelectItem> listSelectItemTdTipoTramiteAdult = new ArrayList<SelectItem>();

	private String tramiteSeleccionado = null;

	private List<SelectItem> listSelectItemTdDocumentoTramite = new ArrayList<SelectItem>();

	private List<GnUnidad> listGnUnidad;
	
	private List<TdEstadoExpediente> listTdEstadoExpediente;
	
	private List<TdSituacionExpediente> listTdSituacionExpediente;

	private HashMap<String, Integer> listProcedimientos;

	private List<SelectItem> listSelectItemProcedimientos = new ArrayList<SelectItem>();

	private String procedimientoSeleccionado = null;

	private UbicacionDTO ubicacionDTO;

	private UbicacionDTO ubicacionDTOContribuyente;

	private UbicacionDTO ubicacionDTORepresentante;

	private BuscarPersonaDTO contribuyente;
	
	private RelacionadosDTO relacionadoCon;
	
	private BuscarPersonaDTO recurrenteCont;

	
	
	
	//private 

	// private BuscarPersonaDTO representante;
	private List<ItemBandejaEntradaDTO> listItemBandejaEntradaDTOs = new ArrayList<ItemBandejaEntradaDTO>();
	
	//Buscar Djs
	
	private List<DeclaracionJuradaAdultDTO> listDjadulto = new ArrayList<DeclaracionJuradaAdultDTO>();

	
	//relacionados 
	private List<MpRelacionado> listMpRelacionados = new ArrayList<MpRelacionado>();
	
	//SERVICIOS
	private List<ItemBandejaEntradaDTO> listItemBandejaEntradaDTOsServAdult = new ArrayList<ItemBandejaEntradaDTO>();
	
	//para historico de actualizaciones de expediente
	private List<ItemHistoricoEntradaDTO> listItemHistoricoEntradaDTOs = new ArrayList<ItemHistoricoEntradaDTO>();

	//para seguimiento de expediente
	private List<ItemSeguimientoEntradaDTO> listItemSeguimientoEntradaDTOs = new ArrayList<ItemSeguimientoEntradaDTO>(); //Detalle seguimiento expediente
	
	private List<ItemSeguimientoExpedienteDTO> litsItemSeguimientoExpedienteDTOs = new ArrayList<ItemSeguimientoExpedienteDTO>(); //Cabecera seguimiento expediente
	
	private Boolean verFormRegistro = Boolean.FALSE;

	private List<TdDataValor> listTdDataValors = new ArrayList<TdDataValor>();

	private List<TdRequisitoExpediente> listTdRequisitoExpedientes = new ArrayList<TdRequisitoExpediente>();

	private String descDocumentoTramite = null;

	private TdRepresentante tdRepresentante = new TdRepresentante();
	
	//adulto mayor
//	private TdDjConyuge tdDjConyuge = new TdDjConyuge();

//	private TdDjUnicaPropiedad tdDjUnicaPropiedad = new TdDjUnicaPropiedad();

	
	
	private RemitenteDTO tdRemitente = new RemitenteDTO(); //Para remitente en gn_persona
	
	//adultoMayor
	
//	private DeclaracionJuradaAdultDTO tdDJAdult = new DeclaracionJuradaAdultDTO(); 


	private Boolean copiarDesdeContribuyente = Boolean.FALSE;
	
	private Boolean copiarDesdeConyuge = Boolean.FALSE;
	
	private Boolean copiarDesdeUnicaPropiedad = Boolean.FALSE;


	
	private Boolean tieneContribuyente;
	private Boolean tieneConyuge;
	private Boolean tieneUnicaPropiedad;
	
	private Boolean tieneRepresentante;

	private Boolean verPanelRepresentante = Boolean.FALSE;
		
	private boolean vistaEntidad = false; //para seleccionar entre persona natural o juridica
	private boolean vistaRemitente = false; //tipo de remitente
	private int tipoRemitente = 1;
	
	private Boolean mantenerDataRepresentante = Boolean.FALSE;
	
	private Boolean mantenerDataContribuyente = Boolean.FALSE;
	
	private Boolean mantenerDataConyuge = Boolean.FALSE;
	
	private Boolean mantenerDataUnicaPropiedad = Boolean.FALSE;

	private boolean vistaExpedienteSimple = false;
	
	private String correlativoActual = null;
	
	private String correlativoActualDJ = null;
	
	private String correlativoActualDJ1 = null;
	
	private String correlativoActualDJ2 = null;

	
	private String correlativoResolucionDj = null;

	
	private String nroExpeGenerico; //para guardar nro expediente generico (Usar en seguimiento e historico de expediente)
	
	private Integer idExpeDig; //obtener id de expediente  
	
	private String nomArchivoExpeDig; // nombre de archivo digitalizado del expediente
	
	private String ubiArchivoExpeDig; // ubicacion de archivo digitalizado
	
	private String refDocumentoExpe; //Para nombre de expedient (referencia)
	
	private String asuntoExp; //para asunto de expediente
	
	private String remiteExp; //quien remite el expediente
	
	private String firmaExp; //quien firma expediente
	
	private String observacionExp; //observacion en expediente
	
	private String tramiteExp; //tipo de tramite para expediente
	
	private Date fechaPresentacionExp; //fecha de presentacion de expediente
	
	private String estadoExp; //estado de recepcion de expediente

	private boolean enEdicion = false;
	
	private List<TdEstadoExpediente> listEstadoExpedientes = new ArrayList<TdEstadoExpediente>();
	
	private List<TdSituacionExpediente> listSituacionExpedientes = new ArrayList<TdSituacionExpediente>(); //Para mostrar situacion de expediente en mesa de partes
	

	/** Campos para el manejo de anexos */

	private List<TdDocumentoAnexo> listDocumentoAnexos = new ArrayList<TdDocumentoAnexo>();

	/** Variables para el control de la tabla de anexos */
	private int indexTblDocumentoAnexo = -1;

	private int nroExpedientesPorGuardar = 0;

	private List<GnTipoDocumento> listGnTipoDocumentos = new ArrayList<GnTipoDocumento>();
	private boolean disableFechaRecepcion=true;
	@EJB
	private TramiteDocumentarioBoRemote tramiteDocumentarioBo;

	@EJB
	PersonaBoRemote personaBo;
	
	@EJB
	private MenuBoRemote menuBo;
	
	// INICIO PERMISOS PARA EL MODULO -=CRAMIREZ=-
		private List<SimpleMenuDTO> listPermisosSubmenu = new ArrayList<SimpleMenuDTO>();
		private boolean permisoBuscar;
		private boolean permisoAgregarRegistrar;
		private boolean permisoModificarActualizar;
		private boolean permisoDigitalizar;
		private boolean permisoSeguimiento;
		private boolean permisoHistoricoModificaciones;
	// FIN PERMISOS PARA EL MODULO

	@PostConstruct
	public void init() {
		permisosMenu();
try {
			
			tieneContribuyente = true;
			tieneRepresentante = false;
			
			tieneConyuge = true;
			tieneUnicaPropiedad = true;
			
			setCorrelativoActual("CORRELATIVO ACTUAL: "+tramiteDocumentarioBo.
			obtenerCorrelativoTablaRegistroNuevoExpediente("td_expediente",DateUtil.getAnioActual())+'-'+DateUtil.getAnioActual());
			
			//DJ adulto
			setCorrelativoActualDJ("CORRELATIVO ACTUAL: "+tramiteDocumentarioBo.
					obtenerCorrelativoTablaRegistroNuevaDj("td_DJ",DateUtil.getAnioActual())+'-'+DateUtil.getAnioActual());
			
			setCorrelativoActualDJ1(tramiteDocumentarioBo.
					obtenerCorrelativoTablaRegistroNuevaDj("td_DJ",DateUtil.getAnioActual())+'-'+DateUtil.getAnioActual());
			

			setCorrelativoActualDJ2(tramiteDocumentarioBo.
					obtenerCorrelativoTablaRegistroNuevaDj("td_DJ",DateUtil.getAnioActual()));
			
			
			setCorrelativoResolucionDj("051-080-"+
					tramiteDocumentarioBo.obtenerCorrelativoTablaResolucionNuevaDj("td_DJ_resolucion", DateUtil.getAnioActual())+'-'+DateUtil.getAnioActual());
			
			busquedaExpedienteDTO.setFechaRecepcion(DateUtil.getCurrentDateOnly());
			
			busquedaExpedienteDTO.setSituacionExpediente(4);//Mostrar expedientes en toda situacion habil (condicion mayor a 4)
								
			System.out.println("FECHA PRESENTACION: "+tdExpediente.getFechaPresentacion());
			
			listItemBandejaEntradaDTOs = tramiteDocumentarioBo.obtenerExpedientes(busquedaExpedienteDTO);
			

			listItemBandejaEntradaDTOsServAdult = tramiteDocumentarioBo.obtenerExpedientesServAdult(busquedaExpedienteDTO);
					
			listTdTipoTramites = tramiteDocumentarioBo.getAllTipoTramites();
			
			//servicios pensionista 
			
			listTdTipoTramitesAdult = tramiteDocumentarioBo.getTipoTramitesAdult();

			listProcedimientos = tramiteDocumentarioBo.getAllProcedimientos();
			
			listEstadoExpedientes = tramiteDocumentarioBo.obtenerEstadoExpedientes();

			listSituacionExpedientes = tramiteDocumentarioBo.obtenerSituacionExpedientes();
								
			for (TdTipoTramite tdTipoTramite : listTdTipoTramites) {
				SelectItem item = new SelectItem();
				hashMapTdTipoTramite.put(tdTipoTramite.getDescripcion(), tdTipoTramite.getTipoTramiteId());

				item.setLabel(tdTipoTramite.getDescripcion());
				item.setValue(tdTipoTramite.getDescripcion());
				listSelectItemTdTipoTramite.add(item);
				
				//servicios adulto
				listSelectItemTdTipoTramiteAdult.add(item);
			}

			listTdDocumentoTramites = tramiteDocumentarioBo.getAllDocumentoTramites();

			for (TdDocumentoTramite tdDocumentoTramite : listTdDocumentoTramites) {
				hashMapTdDocumentoTramite.put(tdDocumentoTramite.getDescCorta(), tdDocumentoTramite.getDocuTramiteId());
			}
			
			listTdDocumentoTramitesAdultPens = tramiteDocumentarioBo.getDocumentoTramitesAdultPens();

			for (TdDocumentoTramite tdDocumentoTramite : listTdDocumentoTramitesAdultPens) {
				hashMapTdDocumentoTramite.put(tdDocumentoTramite.getDescCorta(), tdDocumentoTramite.getDocuTramiteId());
			}
			

			listGnUnidad = tramiteDocumentarioBo.getAllGnUnidad();
			listTdEstadoExpediente = tramiteDocumentarioBo.getAllEstadoExpedientes();
			listTdSituacionExpediente = tramiteDocumentarioBo.getMpSituacionExpedientes();
			
			for (Map.Entry<String, Integer> obj : listProcedimientos.entrySet()) {
				SelectItem item = new SelectItem(obj.getKey(), obj.getKey());
				listSelectItemProcedimientos.add(item);
			}

			/**
			 * Por defecto todos los expedientes son seteandos con tipo de procedimiento tributario
			 */
			this.tdExpediente.setProcedimientoId(1);

			/** Para la administracion de documentos anexos */
			listGnTipoDocumentos = tramiteDocumentarioBo.obtenerTiposDocumentos();
			
		} catch (SisatException e) {
			e.printStackTrace();
		} 
	}
	
	public void permisosMenu() {	
		try {
			int submenuId = Constante.MESA_PARTES;
			
			int permisoBuscarId = Constante.BUSCAR;
			int permisoAgregarRegistrarId = Constante.AGREGAR_REGISTRAR;
			int permisoModificarActualizarId = Constante.MODIFICAR_ACTUALIZAR;
			int permisoDigitalizarId = Constante.DIGITALIZAR;
			int permisoSeguimientoId = Constante.SEGUIMIENTO;
			int permisoHistoricoModificacionesId = Constante.HISTORICO_MODIFICACIONES;
			
			permisoBuscar = false;
			permisoAgregarRegistrar = false;
			permisoModificarActualizar = false;
			permisoDigitalizar = false;
			permisoSeguimiento = false;
			permisoHistoricoModificaciones = false;
			
			listPermisosSubmenu  = menuBo.getAccesosSubmenuUsuario( getSessionManaged().getUsuarioLogIn().getUsuarioId() , submenuId);
			
			Iterator<SimpleMenuDTO> menuIterar = listPermisosSubmenu.iterator();
			while (menuIterar.hasNext()) {
				SimpleMenuDTO lsm = menuIterar.next();
				if(lsm.getItemId() == permisoBuscarId) {
					permisoBuscar = true;
				}
				if(lsm.getItemId() == permisoAgregarRegistrarId) {
					permisoAgregarRegistrar = true;
				}
				if(lsm.getItemId() == permisoModificarActualizarId) {
					permisoModificarActualizar = true;
				}
				if(lsm.getItemId() == permisoDigitalizarId) {
					permisoDigitalizar = true;
				}
				if(lsm.getItemId() == permisoSeguimientoId) {
					permisoSeguimiento = true;
				}
				if(lsm.getItemId() == permisoHistoricoModificacionesId) {
					permisoHistoricoModificaciones = true;
				}
			}
			
		} catch (SisatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void filtar() {

		try {
			listItemBandejaEntradaDTOs = tramiteDocumentarioBo.obtenerExpedientes(busquedaExpedienteDTO);
		} catch (SisatException e) {
			WebMessages.messageError(e.getMessage());
		}
	}
	
	public void filtarDj() {
		System.out.println("Entra a filtar");
		try {
			listDjadulto = tramiteDocumentarioBo.obtenerDjAdult(busquedaDjAdultDTO);
		} catch (SisatException e) {
			WebMessages.messageError(e.getMessage());
		}
	}
	
	private DeclaracionJuradaAdultDTO nuevaDj = new DeclaracionJuradaAdultDTO();
	
	public void nuevaDjAdulto() throws SQLException, SisatException
	{
		System.out.println("funciona porfa*************************************");
		System.out.println("usrid"+getSessionManaged().getUsuarioLogIn().getUsuarioId());
		System.out.println("termi"+getUser().getTerminal());
		System.out.println("fecha"+tdDj.getFechaPresentacion());
		System.out.println("corre"+getCorrelativoActualDJ()+ "Corre1" +getCorrelativoActualDJ1());
		System.out.println("folio"+tdDj.getNroFolios());
		System.out.println("personaid"+contribuyente.getPersonaId());
		System.out.println("dni"+contribuyente.getNroDocuIdentidad());
		System.out.println("ape"+contribuyente.getApellidosNombres());
		System.out.println("dir"+contribuyente.getDireccionCompleta()); 
		System.out.println("obs"+tdDj.getObservacion());
		System.out.println("reso"+tdDj.getNroResolucion());
		System.out.println("ter"+tdDj.getTerminal());
		System.out.println("djid"+tdDj.getDjId());
		System.out.println("docuProc"+tdExpediente.getDocuTramiteId().intValue());
		
		obtenerResolutorDj();
		ingresarTipoBienDj();
		
	//	int anno;
		
	//	String formato="yyyy";
	//	SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
	//	anno = Integer.parseInt(dateFormat.format(tdExpediente.getFechaPresentacion()));
		
		
		tdDj.setUsuarioId(getSessionManaged().getUsuarioLogIn().getUsuarioId());
		tdDj.setTerminal(getUser().getTerminal());
		
		nuevaDj=tramiteDocumentarioBo.nuevaDjAdulto(
				tdDj.getFechaPresentacion(),
				getCorrelativoActualDJ(),
				tdExpediente.getTipoTramiteId().intValue(),
				tdExpediente.getDocuTramiteId().intValue(),
				getCorrelativoActualDJ2(),
				getCorrelativoActualDJ1(),
				tdDj.getNroFolios(), 
				contribuyente.getPersonaId(),
				contribuyente.getNroDocuIdentidad(), 
				contribuyente.getApellidosNombres(),
				contribuyente.getDireccionCompleta(), 
				tdDj.getObservacion(), 
				tdDj.getNroResolucion(),
				tdDj.getUsuarioId(),
				1,
				tdDj.getTerminal(),
				7,
				1,
				77,
				1,
				tdDj.getResolutorId(),
				tdDj.getTipoBien(),
				tdDj.getPorcBeneficio(),
				tdDj.getIniAnnoBenef()
				);
		
		obtenerIdDjUltima();
		obtenerDjIdDocTramite();
		nuevoConyugeDjAdult();
		nuevaPropUnicaDjAdult();
		guardarRequisitosExpedienteDj();
		correlativoDj();
		correlativoResolucionDj();
		guardarResolucionDj();
		//imprimirResolucionDjAdult();
		this.reinicioComponentesDJ();
	
		//init();
		
	}
	
	
	
	public void correlativoDj()throws SQLException, SisatException{
		
		int anno;
		String formato="yyyy";
		SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
		anno = Integer.parseInt(dateFormat.format(tdDj.getFechaPresentacion()));
		
		try {
			tramiteDocumentarioBo.guardarCorrelativoDj(anno);
					

			WebMessages.messageInfo("Se actualizó correlativo" );

			this.reinicioComponentesDJ();
			
		} catch (SisatException e) {
			WebMessages.messageError(e.getMessage());
		}		
	}
	
public void correlativoResolucionDj()throws SQLException, SisatException{
		
		int anno;
		String formato="yyyy";
		SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
		anno = Integer.parseInt(dateFormat.format(tdDj.getFechaPresentacion()));
		
		try {
			tramiteDocumentarioBo.guardarCorrelativoResolucionDj(anno);
					

			WebMessages.messageInfo("Se actualizó correlativo resolución" );

			this.reinicioComponentesDJ();
			
		} catch (SisatException e) {
			WebMessages.messageError(e.getMessage());
		}		
	}
	
	
	Boolean estadoBienPropio;
	Boolean estadoBienSocial;
	Boolean estadoCopropiedad;
	Boolean estadoSucesionIntestada;
	
	public void ingresarTipoBienDj()throws SQLException{
		
		if (estadoBienPropio==true) {
			
			tdDj.setTipoBien("Bien propio");
			
		} else if (estadoBienSocial==true){
			
			tdDj.setTipoBien("Bien social");

		}else if (estadoCopropiedad==true){
			
			tdDj.setTipoBien("Copropiedad");
			
		}else if (estadoSucesionIntestada==true){
			
			tdDj.setTipoBien("Sucesión intestada");		
		}
		
	}
	
	

	public Boolean getEstadoBienPropio() {
		return estadoBienPropio;
	}

	public void setEstadoBienPropio(Boolean estadoBienPropio) {
		this.estadoBienPropio = estadoBienPropio;
	}

	public Boolean getEstadoBienSocial() {
		return estadoBienSocial;
	}

	public void setEstadoBienSocial(Boolean estadoBienSocial) {
		this.estadoBienSocial = estadoBienSocial;
	}

	public Boolean getEstadoCopropiedad() {
		return estadoCopropiedad;
	}

	public void setEstadoCopropiedad(Boolean estadoCopropiedad) {
		this.estadoCopropiedad = estadoCopropiedad;
	}

	public Boolean getEstadoSucesionIntestada() {
		return estadoSucesionIntestada;
	}

	public void setEstadoSucesionIntestada(Boolean estadoSucesionIntestada) {
		this.estadoSucesionIntestada = estadoSucesionIntestada;
	}

	Boolean estadoConyuge;
	
	public Boolean getEstadoConyuge() {
		return estadoConyuge;
	}

	public void setEstadoConyuge(Boolean estadoConyuge) {
		this.estadoConyuge = estadoConyuge;
	}

	public void nuevoConyugeDjAdult() throws SQLException, SisatException
	{
		
		if (estadoConyuge == true) {
					
		System.out.println("funciona porfa conyuge*************************************");
		System.out.println("ter"+getUser().getTerminal());
		System.out.println("LEGO TDDJ");
		tdDj.setUsuarioId(getSessionManaged().getUsuarioLogIn().getUsuarioId());
		tdDjConyuge.setTerminal(getUser().getTerminal());	
		tdDjConyuge.setCuotaIdeal(tdDjConyuge.getCuotaIdeal()); 
		tdDjConyuge.setFechaSucesionIntestada(tdDjConyuge.getFechaSucesionIntestada());
		tdDjConyuge.setFechaPartidaDefuncion(tdDjConyuge.getFechaPartidaDefuncion());
		tdDjConyuge.setFallecido(tdDjConyuge.getFallecido()); 
		System.out.println("LEGO if");

		
			
			System.out.println("relaci"+relacionadoCon.getRelacionadoId());
				
		nuevaDj=tramiteDocumentarioBo.nuevoConyugeDjAdult(
				tdDj.getDjId(),
				relacionadoCon.getRelacionadoId(), 
				relacionadoCon.getNroDocuIdentidad(), 
				relacionadoCon.getApellidosNombres(), 
				relacionadoCon.getPorcParticipacion(), 
				tdDjConyuge.getFallecido(), 
				2, 
				tdDjConyuge.getFechaPartidaDefuncion(), 
				1, 
				tdDjConyuge.getFechaSucesionIntestada(), 
				tdDjConyuge.getCuotaIdeal(), 
				contribuyente.getPersonaId(),
				tdDj.getUsuarioId(), 
				tdDjConyuge.getTerminal() 
				);
		
		//this.reinicioComponentesDJ();

		} 
	}
	
	public void nuevaPropUnicaDjAdult() throws SQLException, SisatException
	{
		tdDj.setUsuarioId(getSessionManaged().getUsuarioLogIn().getUsuarioId());
		tdDjConyuge.setTerminal(getUser().getTerminal());
		
		tdDjUnicaPropiedad.setVivienda(tdDjUnicaPropiedad.getVivienda()); 
		tdDjUnicaPropiedad.setNegocio(tdDjUnicaPropiedad.getNegocio()); 
		tdDjUnicaPropiedad.setLicenciaFuncionamiento(tdDjUnicaPropiedad.getLicenciaFuncionamiento()); 


		
		nuevaDj=tramiteDocumentarioBo.nuevaPropUnicaDjAdult(
				tdDj.getDjId(), 
				tdDjUnicaPropiedad.getVivienda(), 
				tdDjUnicaPropiedad.getNegocio(), 
			//	tdDjUnicaPropiedad.getLicenciaFuncionamiento(), 
				contribuyente.getPersonaId(), 
				tdDj.getUsuarioId(), 
				tdDjConyuge.getTerminal());
		
		//this.reinicioComponentesDJ();

	}
	
	
public void guardarResolucionDj()throws SQLException, SisatException{

	tdDj.setUsuarioId(getSessionManaged().getUsuarioLogIn().getUsuarioId());
	tdDjConyuge.setTerminal(getUser().getTerminal());
	
	
		
			nuevaDj=tramiteDocumentarioBo.nuevaResolucionDj(
					getCorrelativoResolucionDj(), 
					tdDj.getDjId(),   
					tdDj.getUsuarioId(), 
					tdDjConyuge.getTerminal());
		
		
	}
	
	private RequisitoExpededienteDTO requisitoExpedienteDj = new RequisitoExpededienteDTO();

	
	public void guardarRequisitosExpedienteDj() throws SQLException, SisatException
	{
		for (int i = 0; i < listTdRequisitoExpedientes.size(); i++) {
			TdRequisitoExpediente datos = listTdRequisitoExpedientes.get(i);
			
			tdRequisitoExpediente.setRequisitoId(datos.getRequisitoId());
			tdRequisitoExpediente.setFlagPresentado(datos.getFlagPresentado());
			tdRequisitoExpediente.setGlosa(datos.getGlosa());
			tdRequisitoExpediente.setUsuarioId(getSessionManaged().getUsuarioLogIn().getUsuarioId());
			tdRequisitoExpediente.setTerminal(getUser().getTerminal());
			//tdRequisitoExpediente.setDjId(datos.getDjId());
			
			requisitoExpedienteDj =tramiteDocumentarioBo.nuevoRequisitoExpedienteDjAdult(
					tdRequisitoExpediente.getRequisitoId(), 
					tdRequisitoExpediente.getFlagPresentado(),
					tdRequisitoExpediente.getGlosa(), 
					tdRequisitoExpediente.getUsuarioId(), 
					tdRequisitoExpediente.getTerminal(), 
					tdDj.getDjId());
		};
		
		//convertirImagenReq();
		

	}
	
	
	private byte[] adjRequisito;
	private String rutaImagen;

	
	
	public void convertirImagenReq() {
		
		System.out.println("adjreq"+ getAdjRequisito());

		if (getAdjRequisito() == null) {
			setRutaImagen("");
			return;
		}

		try {
			String nroDj= getCorrelativoActualDJ();
			
			// convert byte array back to BufferedImage
			ByteArrayInputStream in = new ByteArrayInputStream(getAdjRequisito());
			BufferedImage bImageFromConvert = ImageIO.read(in);

			String dtFile;
			// String path = ctx.getRealPath("/");
			// dtFile = path + "tmp" + File.separator + DniConsulta + ".jpg";
			
			for (int i = 0; i < listTdRequisitoExpedientes.size(); i++) {
				//TdRequisitoExpediente datos = listTdRequisitoExpedientes.get(i);

			dtFile = "//172.26.128.130/RequisitosDjAdulto/" + nroDj+"_" + ".jpg";
			ImageIO.write(bImageFromConvert, "jpg", new File(dtFile));
			}
		//	setRutaImagen("http://190.116.36.140/FotoDNI/" + DniConsulta + ".jpg");

			//

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}
	
	private int djadultId;
	
	public void obtenerIdDjUltima() {
		try {
			djadultId = tramiteDocumentarioBo.obtenerDjId(contribuyente.getPersonaId());
			System.out.println("dj del metodo nuevo"+djadultId);
			tdDj.setDjId(djadultId);
			
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	private int djtipoTramiteId;

	public void obtenerDjIdDocTramite() {
		try {
			djtipoTramiteId = tramiteDocumentarioBo.obtenerDjIdDocTramite(contribuyente.getPersonaId());
			System.out.println("dj del metodo nuevo docu"+djtipoTramiteId);
			tdDj.setDocuTramiteId(djtipoTramiteId);
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
private int resolutorDjId;
	
	public void obtenerResolutorDj() {
		try {
			resolutorDjId = tramiteDocumentarioBo.obtenerResolutorDjId(getSessionManaged().getUsuarioLogIn().getUsuarioId());
			System.out.println("dj del metodo nuevo"+djadultId);
			tdDj.setResolutorId(resolutorDjId);
			
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	private RelacionadosDTO relacionadoC;
	
	
	public void obtenerDatosRelacionadoC(){
		try {
			relacionadoC =(RelacionadosDTO) personaBo.GetRelacionadosNew(contribuyente.getPersonaId());
			
				
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	private List<RelacionadosDTO> recordsRelacionado;
	
	public void obtenerDatosRelacionado(){
		try {
			recordsRelacionado=personaBo.GetRelacionadosNew(contribuyente.getPersonaId());
			//recordsRelacionado=personaBo.GetRelacionadosNew(27406);
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	
	public void imprimirResolucionDjAdult()throws SQLException {
		
		 	System.out.println("djidImp" + tdDj.getDjId());
		 	System.out.println("tramiteId" + tdDj.getDocuTramiteId());
		 	
		 	if (tdDj.getDocuTramiteId()==null) {
		 		WebMessages.messageInfo("No ha registrado ninguna DJ." );
		 	}
		 	else if (tdDj.getDocuTramiteId()==21) {
				java.sql.Connection connection=null;	

				try {
					CrudServiceBean connj=CrudServiceBean.getInstance();
					 connection=connj.connectJasper();
					 
					 int djId= tdDj.getDjId();
					 System.out.println("djidImp" + tdDj.getDjId());
//					 String djN= tdDj.getNroExpedienteGenerico();
//					 System.out.println("djidImp" + tdDj.getNroExpedienteGenerico());
//					 
//					 String djN1= tdDj.getNroExpediente();
//					 System.out.println("djidImp" +djN1);
//					 
				//	 int CodCont = contribuyente.getPersonaId();
				//	 System.out.println("codigo cont" +CodCont);

					 
				     HashMap<String,Object> parameters = new HashMap<String,Object>();
				     parameters.put("dj_id", djId);
				     parameters.put("ruta_imagen",SATWEBParameterFactory.getPathReporteImagenes()) ;
				     parameters.put("SUBREPORT_DIR",SATWEBParameterFactory.getPathReporte()) ;
				     parameters.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
				     JasperPrint jasperPrint = JasperFillManager.fillReport((SATWEBParameterFactory.getPathReporte()+"rs_adulto_dj.jasper"), parameters,connection);
				     ByteArrayOutputStream output = new ByteArrayOutputStream();
				     JasperExportManager.exportReportToPdfStream(jasperPrint, output);	                           
				     HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();		         		          
				     response.setContentType("application/pdf");	         
				     response.addHeader("Content-Disposition","attachment;filename=Resolucion_adulto"+djId+".pdf");
				     response.setContentLength(output.size());
				     ServletOutputStream servletOutputStream = response.getOutputStream();
				     servletOutputStream.write(output.toByteArray(), 0, output.size());
				     servletOutputStream.flush();
				     servletOutputStream.close();
				     FacesContext.getCurrentInstance().responseComplete();
				     
				 } catch (Exception jre) {
				      jre.printStackTrace();
				      WebMessages.messageFatal(jre);
				 }finally{
			    	 try{
			    		 if(connection!=null){
			    			 connection.close();
			    			 connection=null;
			    		 }
			    	 }catch(Exception e){}
			     }
			} else if (tdDj.getDocuTramiteId()==20){
				java.sql.Connection connection=null;	

			try {
			 CrudServiceBean connj=CrudServiceBean.getInstance();
			 connection=connj.connectJasper();
			 
			 int djId= tdDj.getDjId();
			 System.out.println("djidImp" + djId);
			// String djN= tdDj.getNroExpediente();
			 
		//	 int CodCont = contribuyente.getPersonaId();
			 
		     HashMap<String,Object> parameters = new HashMap<String,Object>();
		     parameters.put("dj_id", djId);
		     parameters.put("ruta_imagen",SATWEBParameterFactory.getPathReporteImagenes()) ;
		     parameters.put("SUBREPORT_DIR",SATWEBParameterFactory.getPathReporte()) ;
		     parameters.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
		     JasperPrint jasperPrint = JasperFillManager.fillReport((SATWEBParameterFactory.getPathReporte()+"rs_adulto_pensionista_dj.jasper"), parameters,connection);
		     ByteArrayOutputStream output = new ByteArrayOutputStream();
		     JasperExportManager.exportReportToPdfStream(jasperPrint, output);	                           
		     HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();		         		          
		     response.setContentType("application/pdf");	         
		     response.addHeader("Content-Disposition","attachment;filename=Resolucion_pensionista"+djId+".pdf");
		     response.setContentLength(output.size());
		     ServletOutputStream servletOutputStream = response.getOutputStream();
		     servletOutputStream.write(output.toByteArray(), 0, output.size());
		     servletOutputStream.flush();
		     servletOutputStream.close();
		     FacesContext.getCurrentInstance().responseComplete();
		     
		 } catch (Exception jre) {
		      jre.printStackTrace();
		      WebMessages.messageFatal(jre);
		 }finally{
	    	 try{
	    		 if(connection!=null){
	    			 connection.close();
	    			 connection=null;
	    		 }
	    	 }catch(Exception e){}
	     }
			}
	}
	
//	public void filtarRelacionados() {
//
//		try {
//			listMpRelacionados = tramiteDocumentarioBo.obtenerRelacionados(MpRelacionado relacionados);
//		} catch (SisatException e) {
//			WebMessages.messageError(e.getMessage());
//		}
//	}
	
	
	
	public void filtarServAdult() {

		try {
			listItemBandejaEntradaDTOsServAdult = tramiteDocumentarioBo.obtenerExpedientesServAdult(busquedaExpedienteDTO);
		} catch (SisatException e) {
			WebMessages.messageError(e.getMessage());
		}
	}
	

	
	//para valores historico de expediente
	public void mostrar(Integer expedienteId) {			
		try {
			listItemHistoricoEntradaDTOs = tramiteDocumentarioBo.obtenerExpedientesh(expedienteId);	 //historico del expediente		
		} catch (SisatException e) {
			WebMessages.messageError(e.getMessage());
		}
		nroExpeGenerico = listItemHistoricoEntradaDTOs.get(0).getExpediente(); //mostrar nro expediente generico
	}
	
	//para valores de expediente en seguimiento y digitalizacion
	public void seguimiento(Integer expedienteId) {				
		try {				
			litsItemSeguimientoExpedienteDTOs = tramiteDocumentarioBo.seguimientoExpedientec(expedienteId); //seguimiento del expediente cabecera
			listItemSeguimientoEntradaDTOs = tramiteDocumentarioBo.seguimientoExpediente(expedienteId);	 //seguimiento del expediente detalle			
		} catch (SisatException e) {
			WebMessages.messageError(e.getMessage());
		}
		nroExpeGenerico = litsItemSeguimientoExpedienteDTOs.get(0).getExpediente(); //mostrar nro expediente generico
		//valores para digitalizacion (nombre de archivo y Metadatos)
		nomArchivoExpeDig = litsItemSeguimientoExpedienteDTOs.get(0).getNombreArchivo();
		ubiArchivoExpeDig = litsItemSeguimientoExpedienteDTOs.get(0).getUbicacionArchivo();
		idExpeDig = litsItemSeguimientoExpedienteDTOs.get(0).getExpedienteId();
		refDocumentoExpe = litsItemSeguimientoExpedienteDTOs.get(0).getDocumento();
		asuntoExp = litsItemSeguimientoExpedienteDTOs.get(0).getAsunto();
		remiteExp = litsItemSeguimientoExpedienteDTOs.get(0).getRemitente();
		firmaExp = litsItemSeguimientoExpedienteDTOs.get(0).getFirmante();
		observacionExp = listItemSeguimientoEntradaDTOs.get(0).getObsevacionExpe();
		tramiteExp = litsItemSeguimientoExpedienteDTOs.get(0).getTramite();
		fechaPresentacionExp = litsItemSeguimientoExpedienteDTOs.get(0).getFechaPresentacion();
		estadoExp = listItemSeguimientoEntradaDTOs.get(0).getEstadoExpediente();

		
	}	
	
	//
		
	
	/** Metodo que sirve para actualizar el correlativo mostrado en pantalla cuando el anio seleccionado
	 * sea menor o mayor al actual */
	
	public void correlativoAnterior()
	{
		String anhoSeleccionado, anhoActual;
		
		Date fechaActual = DateUtil.getCurrentDateOnly();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		
		anhoActual = dateFormat.format(fechaActual);
		anhoSeleccionado = dateFormat.format(tdExpediente.getFechaPresentacion());
		
		Integer.parseInt(anhoActual);
		Integer.parseInt(anhoSeleccionado);
		
		if (Integer.parseInt(anhoSeleccionado) < Integer.parseInt(anhoActual))
		{
			setCorrelativoActual("CORRELATIVO ACTUAL: "+tramiteDocumentarioBo.
					obtenerCorrelativoTablaRegistroNuevoExpediente("td_expediente",Integer.parseInt(anhoSeleccionado))+'-'+Integer.parseInt(anhoSeleccionado));
		}
		else
		{
			setCorrelativoActual("CORRELATIVO ACTUAL: "+tramiteDocumentarioBo.
					obtenerCorrelativoTablaRegistroNuevoExpediente("td_expediente",Integer.parseInt(anhoSeleccionado))+'-'+Integer.parseInt(anhoSeleccionado));
		}	
		
	}
	
	public void correlativoAnteriorDj()
	{
		String anhoSeleccionado, anhoActual;
		
		Date fechaActual = DateUtil.getCurrentDateOnly();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		
		anhoActual = dateFormat.format(fechaActual);
		anhoSeleccionado = dateFormat.format(tdDj.getFechaPresentacion());
		
		Integer.parseInt(anhoActual);
		Integer.parseInt(anhoSeleccionado);
		
		if (Integer.parseInt(anhoSeleccionado) < Integer.parseInt(anhoActual))
		{
			setCorrelativoActual("CORRELATIVO ACTUAL: "+tramiteDocumentarioBo.
					obtenerCorrelativoTablaRegistroNuevaDj("td_dj",Integer.parseInt(anhoSeleccionado))+'-'+Integer.parseInt(anhoSeleccionado));
		}
		else
		{
			setCorrelativoActual("CORRELATIVO ACTUAL: "+tramiteDocumentarioBo.
					obtenerCorrelativoTablaRegistroNuevaDj("td_dj",Integer.parseInt(anhoSeleccionado))+'-'+Integer.parseInt(anhoSeleccionado));
		}	
		
	}
	
	public void limpiar() {

		this.busquedaExpedienteDTO = new BusquedaExpedienteDTO();

	}
	
	public void limpiarDj() {

		System.out.println("Entro  al limpiar dj");
		this.busquedaDjAdultDTO = new DeclaracionJuradaAdultDTO();

	}
	
	public void pruebaDj() {

		System.out.println("Entro  a pueba dj");
		

	}

	public void addRowAnexo() {
		AnexoDTO anexoDTO = new AnexoDTO();
		listAnexoDTOs.add(anexoDTO);
	}

	public void addRowDataValor() {

		TdDataValor dataValor = new TdDataValor();

		listTdDataValors.add(dataValor);
	}

	public void addItem() {
		listAnexoDTOs.get(indexTblAnexo).setEstado(AnexoDTO.TipoEstadoAnexo.Finalizado);
	}

	public void addItemValor() {
		// listAnexoDTOs.get(indexTblAnexo).setEstado(AnexoDTO.TipoEstadoAnexo.Finalizado);

		listTdDataValors.get(indexTblAnexo).setEnEdicion(false);
	}

	public void listenerArchivoFileUpload(UploadEvent event) {

		UploadItem item = event.getUploadItem();
		FileUpload fileUpload = new FileUpload();
		fileUpload.setFile(item.getFile());
		fileUpload.setFileName(item.getFileName());
		fileUpload.setContentType(item.getContentType());
		listAnexoDTOs.get(indexTblAnexo).setFile(fileUpload);

	}
	//se agrega en 2018, nuevo remitente en gn_persona
	
	public void guardarTdRemitente() throws Exception {
		int tipDoc=0;
		String nroDocRe="";
		//Validar tipo de Remitente
		if (tdRemitente.getTipo().equals("Persona Juridica")) {
			tipDoc = 2;
			tdRemitente.setPriNombre(null); 
			tdRemitente.setSegNombre(null); 
			tdRemitente.setApePaterno(null); 
			tdRemitente.setApeMaterno(null); 
		}
		else {
			tipDoc = 1;
			tdRemitente.setRazonSocial(null); 
		}
		tdRemitente.setTipoDoc(tipDoc);	
		//seleccionar documento segun tipo de remitente
		if (tdRemitente.getTipoDoc() == 1) {
			nroDocRe = tdRemitente.getNroDNI();
		}
		else {
			nroDocRe = tdRemitente.getNroRUC();
		}
		
		tdRemitente.setNroDoc(nroDocRe);
		tdRemitente.setUsarioId(getSessionManaged().getUsuarioLogIn().getUsuarioId());
		tdRemitente.setTerminal(getUser().getTerminal());
		
		tramiteDocumentarioBo.NuevoTdRemitente(
				tdRemitente.getPriNombre(), 
				tdRemitente.getSegNombre(), 
				tdRemitente.getApePaterno(), 
				tdRemitente.getApeMaterno(), 
				tdRemitente.getRazonSocial(), 
				tdRemitente.getNroDoc(), 
				tdRemitente.getDirCompleta(), 
				tdRemitente.getTipoDoc(), 
				tdRemitente.getUsarioId(), 
				tdRemitente.getTerminal());
		WebMessages.messageInfo(TramiteDocumentarioBo.msjRemitente);
		limpiarTdRemitente();		
	}
	
	public void limpiarTdRemitente() throws Exception{
		tdRemitente.setNroDNI(null);
		tdRemitente.setNroRUC(null);
		tdRemitente.setPriNombre(null);
		tdRemitente.setSegNombre(null); 
		tdRemitente.setApePaterno(null);
		tdRemitente.setApeMaterno(null); 
		tdRemitente.setRazonSocial(null);
		tdRemitente.setNroDoc(null);
		tdRemitente.setDirCompleta(null);
		tdRemitente.setTipoDoc(null);
		tdRemitente.setUsarioId(null);
		tdRemitente.setTerminal(null);		
	}
	
	/**
	 * Inicio cambios 26/07/2013
	 * 
	 * @throws Exception
	 * */

	public void guardarExpediente() throws Exception {
		
		this.tdExpediente.setContribuyenteId(getContribuyente().getPersonaId()); 
		
		this.tdExpediente.setEstadoExpediente(getTdEstadoExpediente().getEstadoExpedienteId());
		this.tdExpediente.setSituacionExpediente(Constante.SITUACION_EXPEDIENTE_REGISTRADO); //para nuevo nuevo expediente
		
		// La fecha de presentacion sera cambiante
		//this.tdExpediente.setFechaPresentacion(DateUtil.getCurrentDateOnly());

		this.tdExpediente.setEstado("1");	
		this.tdExpediente.setReport(1);//para controlar reporte de expedientes derivados	
		
		int anno;
		
		String formato="yyyy";
		SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
		anno = Integer.parseInt(dateFormat.format(tdExpediente.getFechaPresentacion()));
		
		try {
			this.validaListDataValors();
			this.validaListDocumentosAnexos();
			tdExpediente = tramiteDocumentarioBo.guardarExpediente(anno,tdExpediente,
					tdRepresentante,
					listTdDataValors,
					listTdRequisitoExpedientes,
					this.verPanelRepresentante,
					this.listDocumentoAnexos);

			WebMessages.messageInfo("Expediente guardado satisfactoriamente NRO EXPEDIENTE: ".concat(tdExpediente.getNroExpedienteGenerico()) );

			this.reinicioComponentes();
			
		} catch (SisatException e) {
			WebMessages.messageError(e.getMessage());
		}
	}


	
	private void validaListDataValors() throws SisatException{
		for(TdDataValor data: this.listTdDataValors){
			if(data.isEnEdicion()){
				throw new SisatException("Existe un valor que esta pendiente de agregación: ".concat(data.getNroValor()));
			}
		}
	}
	
	private void validaListDocumentosAnexos() throws SisatException{
		for(TdDocumentoAnexo documento:this.listDocumentoAnexos){
			if(documento.isEnEdicion()){
				throw new SisatException("Existe un Anexo pendiente de agregación: ".concat(documento.getNroDocumento()));
			}
		}
	}

	public void actualizarExpediente() throws Exception {

		this.tdExpediente.setContribuyenteId(getContribuyente().getPersonaId());
	
		// La fecha de presentacion sera cambiante
		//this.tdExpediente.setFechaPresentacion(DateUtil.getCurrentDateOnly());
				
		this.tdExpediente.setEstado("1");
		this.tdExpediente.setReport(1);//para controlar reporte de expedientes derivados

		try {
			
			this.validaListDataValors();
			this.validaListDocumentosAnexos();
			
			tramiteDocumentarioBo.actualizarExpediente(tdExpediente,
					tdRepresentante,
					listTdDataValors,
					listTdRequisitoExpedientes,
					this.verPanelRepresentante,
					this.listDocumentoAnexos);

			WebMessages.messageInfo("Expediente Actualizado satisfactoriamente");

			this.reinicioComponentes();

		} catch (SisatException e) {
			WebMessages.messageError(e.getMessage());
		}
	}

	/**
	 * Inicio cambios 26/07/2013
	 * 
	 * @throws Exception
	 * */
	public void guardarExpedienteSimple() throws Exception {

		if (getContribuyente() != null && getContribuyente().getPersonaId() != null
				&& getContribuyente().getPersonaId().intValue() > 0) {
			this.tdExpediente.setContribuyenteId(getContribuyente().getPersonaId());
		} else {
			this.tdExpediente.setContribuyenteId(null);
		}

		//this.tdExpediente.setEstadoExpediente(Constante.ESTADO_EXPEDIENTE_RECEPCIONADO); //ya no, ahora se tiene que elegir el estado
		this.tdExpediente.setEstadoExpediente(getTdEstadoExpediente().getEstadoExpedienteId());		
		
		/** Parametros para expediente simple */
		this.tdExpediente.setTipoTramiteId(1);
		this.tdExpediente.setDocuTramiteId(3);

		// La fecha de presentacion sera cambiante
		// this.tdExpediente.setFechaPresentacion(DateUtil.getCurrentDateOnly());

		this.tdExpediente.setEstado("1");
		this.tdExpediente.setReport(1);//para controlar reporte de expedientes derivados
		this.tdExpediente.setSituacionExpediente(Constante.SITUACION_EXPEDIENTE_REGISTRADO); //para nuevo nuevo expediente

		try {
			this.validaListDataValors();
			this.validaListDocumentosAnexos();
			
			int anno;
			
			String formato="yyyy";
			SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
			anno = Integer.parseInt(dateFormat.format(tdExpediente.getFechaPresentacion()));
						
			tdExpediente = tramiteDocumentarioBo.guardarExpediente(anno,tdExpediente,
					tdRepresentante,
					listTdDataValors,
					listTdRequisitoExpedientes,
					this.verPanelRepresentante,
					this.listDocumentoAnexos);

			WebMessages.messageInfo("Expediente guardado satisfactoriamente NRO EXPEDIENTE: ".concat(tdExpediente.getNroExpedienteGenerico()) );

			this.reinicioComponentes();

		} catch (SisatException e) {
			WebMessages.messageError("Error al actualizar el expediente, por favor verifique. ".concat(e.getMessage()));
		}
	}

	/**
	 * Inicio cambios 26/07/2013
	 * 
	 * @throws Exception
	 * */
	public void actualizarExpedienteSimple() throws Exception {

		if (getContribuyente() != null && getContribuyente().getPersonaId() != null
				&& getContribuyente().getPersonaId().intValue() > 0) {
			this.tdExpediente.setContribuyenteId(getContribuyente().getPersonaId());
		} else {
			this.tdExpediente.setContribuyenteId(null);			
		}

		/** Parametros para expediente simple */
		this.tdExpediente.setTipoTramiteId(1);
		this.tdExpediente.setDocuTramiteId(3);

		// La fecha de presentacion sera cambiante
		//this.tdExpediente.setFechaPresentacion(DateUtil.getCurrentDateOnly());
		this.tdExpediente.setEstado("1");
		this.tdExpediente.setReport(1);//para controlar reporte de expedientes derivados

		try {
			this.validaListDataValors();
			this.validaListDocumentosAnexos();
			
			tramiteDocumentarioBo.actualizarExpediente(tdExpediente,
					tdRepresentante,
					listTdDataValors,
					listTdRequisitoExpedientes,
					this.verPanelRepresentante,
					this.listDocumentoAnexos);

			WebMessages.messageInfo("Expediente Actualizado satisfactoriamente");

			// reinicio de componentes
			this.reinicioComponentes();

		} catch (SisatException e) {
			WebMessages.messageError("No se ha podido actualizar cambios, verifique ".concat(e.getMessage()));
		}
	}

	public void cancelar() {
		this.reinicioComponentes();
	}
	
	public void cancelarDj() {
		this.reinicioComponentesDJ();
	}

	private void reinicioComponentes() {
		try {
			/** Reiniciando componentes */
			setCorrelativoActual("CORRELATIVO ACTUAL: "+tramiteDocumentarioBo.
			obtenerCorrelativoTablaRegistroNuevoExpediente("td_expediente",DateUtil.getAnioActual())+'-'+DateUtil.getAnioActual());
			
			if (this.mantenerDataContribuyente == Boolean.FALSE)
			{
				this.contribuyente = new BuscarPersonaDTO();
			}	
			
			if (this.mantenerDataRepresentante == Boolean.FALSE)
			{
				this.tdRepresentante = new TdRepresentante();
			}	
			
			this.tdEstadoExpediente = new TdEstadoExpediente();
			this.ubicacionDTORepresentante = new UbicacionDTO();
			this.listTdDataValors = new ArrayList<TdDataValor>();
			this.listTdRequisitoExpedientes = new ArrayList<TdRequisitoExpediente>();
			this.listAnexoDTOs = new ArrayList<AnexoDTO>();

			this.tdExpediente = new TdExpediente();
			tdExpediente.setFechaPresentacion(DateUtil.getCurrentDateOnly());
			/**
			 * Por defecto todos los expedientes son seteandos con tipo de procedimiento tributario
			 */
			this.tdExpediente.setProcedimientoId(1);
			this.procedimientoSeleccionado = null;
			this.listDocumentoAnexos.clear();

			listItemBandejaEntradaDTOs = tramiteDocumentarioBo.obtenerExpedientes(busquedaExpedienteDTO);
			
			//listItemBandejaEntradaDTOsServAdult = tramiteDocumentarioBo.obtenerExpedientesServAdult(busquedaExpedienteDTO);
			
			
			// reiniciando comboBox de tramite y documento tramite
			this.tramiteSeleccionado = null;
			this.descDocumentoTramite = null;

			// ocultando el formulario de registro
			this.verFormRegistro = Boolean.FALSE;
			// setenado la vista a registro de expediente
			this.vistaExpedienteSimple = false;

			this.enEdicion = false;
		} catch (SisatException e) {
			WebMessages.messageError(e.getMessage());
		}

	}
	
	
	private void reinicioComponentesDJ() {
		try {
			/** Reiniciando componentes */
			setCorrelativoActualDJ("CORRELATIVO ACTUAL: "+tramiteDocumentarioBo.
			obtenerCorrelativoTablaRegistroNuevaDj("td_dj",DateUtil.getAnioActual())+'-'+DateUtil.getAnioActual());
			
			
			setCorrelativoActualDJ1(tramiteDocumentarioBo.
					obtenerCorrelativoTablaRegistroNuevaDj("td_DJ",DateUtil.getAnioActual())+'-'+DateUtil.getAnioActual());
			

			setCorrelativoActualDJ2(tramiteDocumentarioBo.
					obtenerCorrelativoTablaRegistroNuevaDj("td_DJ",DateUtil.getAnioActual()));
			
					
			//setCorrelativoActual("CORRELATIVO ACTUAL: "+tramiteDocumentarioBo.
				//	obtenerCorrelativoTablaRegistroNuevoExpediente("td_dj",DateUtil.getAnioActual())+'-'+DateUtil.getAnioActual());
			
			if (this.mantenerDataContribuyente == Boolean.FALSE)
			{
				this.contribuyente = new BuscarPersonaDTO();
			}	
			
			if (this.mantenerDataRepresentante == Boolean.FALSE)
			{
				this.tdRepresentante = new TdRepresentante();
			}			
			if (this.mantenerDataConyuge ==Boolean.FALSE)
			{
				this.relacionadoCon = new RelacionadosDTO();
			}
			if (this.mantenerDataUnicaPropiedad == Boolean.FALSE)
			{
				this.tdDjUnicaPropiedad = new TdDjUnicaPropiedad();
			}
			
			this.tdEstadoExpediente = new TdEstadoExpediente();
			this.ubicacionDTORepresentante = new UbicacionDTO();
			this.listTdDataValors = new ArrayList<TdDataValor>();
			this.listTdRequisitoExpedientes = new ArrayList<TdRequisitoExpediente>();
			this.listAnexoDTOs = new ArrayList<AnexoDTO>();

			this.tdDj = new TdDJ();
			tdDj.setFechaPresentacion(DateUtil.getCurrentDateOnly());
		
			
			this.tdDj.setProcedimientoId(1);
			this.procedimientoSeleccionado = null;
			this.listDocumentoAnexos.clear();

			//listItemBandejaEntradaDTOs = tramiteDocumentarioBo.obtenerExpedientesServAdult(busquedaExpedienteDTO);
			
			listItemBandejaEntradaDTOsServAdult = tramiteDocumentarioBo.obtenerExpedientesServAdult(busquedaExpedienteDTO);

			
			// reiniciando comboBox de tramite y documento tramite
			this.tramiteSeleccionado = null;
			this.descDocumentoTramite = null;

			// ocultando el formulario de registro
			this.verFormRegistro = Boolean.FALSE;
			// setenado la vista a registro de expediente
			this.vistaExpedienteSimple = false;

			this.enEdicion = false;
		} catch (SisatException e) {
			WebMessages.messageError(e.getMessage());
		}

	}

	public void listenerValueChangeTipoTramite(ValueChangeEvent e) {

		String descripcionTipoTramiteId = (String) e.getNewValue();
		Integer tipoTramiteId = hashMapTdTipoTramite.get(descripcionTipoTramiteId);

		try {
			if (tipoTramiteId != null) {

				listTdDocumentoTramites.clear();
				listTdDocumentoTramites = tramiteDocumentarioBo.getAllDocumentoTramitesByTipoTramite(tipoTramiteId);

				listSelectItemTdDocumentoTramite.clear();
				hashMapTdDocumentoTramite.clear();

				for (TdDocumentoTramite tdDocumentoTramite : listTdDocumentoTramites) {
					SelectItem item = new SelectItem();
					item.setLabel(tdDocumentoTramite.getDescripcion());
					item.setValue(tdDocumentoTramite.getDescripcion());
					listSelectItemTdDocumentoTramite.add(item);
					hashMapTdDocumentoTramite.put(tdDocumentoTramite.getDescripcion(),
							tdDocumentoTramite.getDocuTramiteId());
				}

				this.tdExpediente.setTipoTramiteId(tipoTramiteId);
				descDocumentoTramite = null;

			}
		} catch (SisatException e1) {
			WebMessages.messageError(e1.getMessage());
		}
	}
	
	public void listenerValueChangeTipoTramiteAdult(ValueChangeEvent e) {

		String descripcionTipoTramiteId = (String) e.getNewValue();
		Integer tipoTramiteId = hashMapTdTipoTramite.get(descripcionTipoTramiteId);

		try {
			if (tipoTramiteId != null) {

				listTdDocumentoTramites.clear();
				listTdDocumentoTramites = tramiteDocumentarioBo.getDocumentoTramitesByTipoTramiteAdult(tipoTramiteId);

				listSelectItemTdDocumentoTramite.clear();
				hashMapTdDocumentoTramite.clear();

				for (TdDocumentoTramite tdDocumentoTramite : listTdDocumentoTramites) {
					SelectItem item = new SelectItem();
					item.setLabel(tdDocumentoTramite.getDescripcion());
					item.setValue(tdDocumentoTramite.getDescripcion());
					listSelectItemTdDocumentoTramite.add(item);
					hashMapTdDocumentoTramite.put(tdDocumentoTramite.getDescripcion(),
							tdDocumentoTramite.getDocuTramiteId());
				}

				this.tdExpediente.setTipoTramiteId(tipoTramiteId);
				descDocumentoTramite = null;

			}
		} catch (SisatException e1) {
			WebMessages.messageError(e1.getMessage());
		}
	}

	public void listenerValueChangeTipoProcedimiento(ValueChangeEvent e) {

		String descripcionTipoProcedimientoId = (String) e.getNewValue();
		Integer tipoProcedimientoId = listProcedimientos.get(descripcionTipoProcedimientoId);
		try {
			if (tipoProcedimientoId != null) {

				vistaExpedienteSimple = (tipoProcedimientoId >= 3 ? true : false); //para mostrar procedimiento 'otros' y 'derivacion directa'

				tdExpediente.setProcedimientoId(tipoProcedimientoId);
				this.listTdTipoTramites.clear();

				this.listTdTipoTramites = tramiteDocumentarioBo
						.obtenerTipoTramitesPorProcedimientoId(tipoProcedimientoId);
				
				hashMapTdTipoTramite.clear();
				listSelectItemTdTipoTramite.clear();
				
				//servicios adulto
				listSelectItemTdTipoTramiteAdult.clear();

				
				listSelectItemTdDocumentoTramite.clear();
				hashMapTdDocumentoTramite.clear();
				
				for (TdTipoTramite tdTipoTramite : listTdTipoTramites) {
					SelectItem item = new SelectItem();
					hashMapTdTipoTramite.put(tdTipoTramite.getDescripcion(), tdTipoTramite.getTipoTramiteId());

					item.setLabel(tdTipoTramite.getDescripcion());
					item.setValue(tdTipoTramite.getDescripcion());
					listSelectItemTdTipoTramite.add(item);
					
					//servicios adulto
					listSelectItemTdTipoTramiteAdult.add(item);
					
				}
				
				/** Reiniciando combobox de tipo tramite y documento tramite y requisitos*/
				this.tramiteSeleccionado = null;
				this.descDocumentoTramite = null;
				
				this.listTdRequisitoExpedientes.clear();
				
			}
		} catch (SisatException e1) {
			WebMessages.messageError(e1.getMessage());
		}

	}
	/*---Para nuevas funcionalidades de tramite---*/
	//Para ver entidad (natural, juridica) y representante (firmante)
	public void listenerValueChangeTipoEntidad(ValueChangeEvent e) {
		String tipoEntidad = (String) e.getNewValue();
		
		if (tipoEntidad != null) {	
			if (tipoEntidad.equals("Persona Juridica"))	{
				vistaEntidad = true; 
				verPanelRepresentante = Boolean.TRUE;
				mantenerDataRepresentante = Boolean.TRUE;}				
			else {vistaEntidad = false;
				verPanelRepresentante = Boolean.FALSE;
				mantenerDataRepresentante = Boolean.FALSE;}
		}
		//para guardar datos de representante (firmante)
		if (verPanelRepresentante == true)
		{	
			tieneContribuyente = false;
			tieneRepresentante = true;
		}
		if (verPanelRepresentante == false)
		{
			tieneContribuyente = true;	
			tieneRepresentante = false;
		}
	}	
	
	
	
	//para ingreso de remitente natural o juridica : gn_persona:::::verifcar por que no se ejecutar al cambiar combobox
	public void listenerValueChangeTipoRemitente(ValueChangeEvent e) {
		String remite = (String) e.getNewValue();
		
		if (remite != null) {	
			if (remite.equals("Persona Juridica"))	{
				vistaRemitente = true; 
				tipoRemitente = 2;					
				}				
			else {vistaRemitente = false;
				  tipoRemitente = 1;				  
				}
		}
		tdRemitente.setTipoDoc(tipoRemitente);	
	}
	
	//Cambiar estado de expediente a digitalizado
	public void digitalizarExpediente(Integer expedienteId) throws SisatException{
		tramiteDocumentarioBo.estadoDigitaliadoExp(expedienteId);
		listItemBandejaEntradaDTOs = tramiteDocumentarioBo.obtenerExpedientes(busquedaExpedienteDTO); //se actualiza listado de expedientes
	}	
	//Cambiar estado de expediente a derivado
	public void derivarExpediente(Integer expedienteId) throws SisatException{		
		tramiteDocumentarioBo.estadoDerivadoExp(expedienteId);
		listItemBandejaEntradaDTOs = tramiteDocumentarioBo.obtenerExpedientes(busquedaExpedienteDTO); //se actualiza listado de expedientes
	}
	/*---Termina aqui---*/
	
	public void listenerValueChangeDocumentoTramite(ValueChangeEvent e) {

		String descripcionDocumentoTramiteId = (String) e.getNewValue();
		Integer documentoTramiteId = hashMapTdDocumentoTramite.get(descripcionDocumentoTramiteId);

		try {
			if (documentoTramiteId != null && tdExpediente.getTipoTramiteId() != null) {

				listTdRequisitoExpedientes = tramiteDocumentarioBo
						.getRequisitosByTipoTramiteDocumentoTramite(tdExpediente.getTipoTramiteId(), documentoTramiteId);

				this.tdExpediente.setDocuTramiteId(documentoTramiteId);
			}
		} catch (SisatException e1) {
			WebMessages.messageError(e1.getMessage());
		}
	}

	public void copiaDataContribuyente() {

		if (this.copiarDesdeContribuyente) {

			if (this.contribuyente != null) {

				this.getTdRepresentante().setApellidosNombres(this.contribuyente.getApellidosNombres());
				this.getTdRepresentante().setDni(this.contribuyente.getNroDocuIdentidad());
				this.getTdRepresentante().setDireccion(this.contribuyente.getDireccionCompleta());
			} else {
				this.copiarDesdeContribuyente = Boolean.FALSE;
				WebMessages.messageError("Seleccione un contribuyente");
			}

		} else {
			this.tdRepresentante = new TdRepresentante();
		}

	}
	
	public void copiaDataConyuge() {

		if (this.copiarDesdeConyuge) {

			if (this.relacionadoCon != null) {

				this.getRelacionadoCon().setApellidosNombres(this.relacionadoCon.getApellidosNombres());
				this.getRelacionadoCon().setNroDocuIdentidad(this.relacionadoCon.getNroDocuIdentidad());
			//	this.getTdDjConyuge().setConyugeId(this.relacionado.getRelacionadoId());
				this.getRelacionadoCon().setPorcParticipacion(this.relacionadoCon.getPorcParticipacion());
				this.getRelacionadoCon().setRelacionadoId(this.relacionadoCon.getRelacionadoId());
			//	this.getTdDjConyuge().setDjId(this.tdDj.getDjId());
			//	this.getTdDjConyuge().setFallecido(this.);
			} else {
				this.copiarDesdeConyuge = Boolean.FALSE;
				WebMessages.messageError("Cargue un cónyuge");
			}

		} 
	}
	
//	public void copiaDataUnicaPropiedad() {
//
//		if (this.copiarDesdeUnicaPropiedad) {
//
//			if (this.unicaPropiedad != null) {
//
//				this.getTdRepresentante().setApellidosNombres(this.contribuyente.getApellidosNombres());
//				this.getTdRepresentante().setDni(this.contribuyente.getNroDocuIdentidad());
//				this.getTdRepresentante().setDireccion(this.contribuyente.getDireccionCompleta());
//			} else {
//				this.copiarDesdeContribuyente = Boolean.FALSE;
//				WebMessages.messageError("Seleccione un contribuyente");
//			}
//
//		} else {
//			this.tdRepresentante = new TdRepresentante();
//		}
//
//	}

	/** Inicio para la administracion para documentos anexos */
	public void changeRowItemDocumentoAnexo() {
		listDocumentoAnexos.get(this.indexTblDocumentoAnexo).setEnEdicion(true);
	}

	public void quitRowItemDocumentoAnexo() {
		listDocumentoAnexos.remove(this.indexTblDocumentoAnexo);

		this.nroExpedientesPorGuardar = this.nroExpedientesPorGuardar - 1;
	}

	public void addItemDocumentoAnexo() {

		TdDocumentoAnexo _tdDocumentoAnexo = listDocumentoAnexos.get(indexTblDocumentoAnexo);
		_tdDocumentoAnexo.setEnEdicion(false);
		_tdDocumentoAnexo.setFechaDocumento(DateUtil.getCurrentDateOnly());
//		_tdDocumentoAnexo.setUnidadId(getSessionManaged().getUsuarioLogIn().getUnidadId());

	}

	public void addRowDataDocumentoAnexo() {

		TdDocumentoAnexo tdDocumentoAnexo = new TdDocumentoAnexo();

		tdDocumentoAnexo.setPendienteGuardado(Boolean.TRUE);
		listDocumentoAnexos.add(tdDocumentoAnexo);
		this.nroExpedientesPorGuardar = this.nroExpedientesPorGuardar + 1;

	}

	/** Fin administracion de documentos anexos */

	/**
	 * Metodo usado para la edicion de expedientes con estado Recepcionado y pendiente de
	 * requisitos, estos expedientes aun no han sido procesados por parte de las unidad de la
	 * entidad.
	 * */
	public void editarExpediente(Integer expedienteId) {

		try {			
			vistaExpedienteSimple = false;

			/** Cambiando al modo edicion */
			enEdicion = true;

			tdExpediente = tramiteDocumentarioBo.obtenerExpediente(expedienteId);

			tdRepresentante = tramiteDocumentarioBo.obtenerRepresentante(tdExpediente.getRepresentanteId());

			listTdDataValors = tramiteDocumentarioBo.obtenerDataValorExpediente(expedienteId);

			listTdRequisitoExpedientes = tramiteDocumentarioBo.obtenerRequisitosExpediente(expedienteId);

			listDocumentoAnexos = tramiteDocumentarioBo.obtenerDocumentosAnexos(expedienteId);
			
			// trae los datos del contribuyente solo si se encuentra registrado
			if (tdExpediente.getContribuyenteId() != null) {
				List<BuscarPersonaDTO> lista = personaBo.findTraDocPersona(tdExpediente.getContribuyenteId(),
						null,
						null,
						null,
						null,
						null,
						null,
						null,
						null,
						null,
						null);

				if (lista.size() > 0) {
					contribuyente = lista.get(0);
				}
			}

			// mostrarExpediente = Boolean.TRUE;

			if (tdExpediente.getProcedimientoId() == 3 ) {
				vistaExpedienteSimple = true;
			}

			/** Carga de datos desde expediente a combos */
			for (Entry<String, Integer> entry : listProcedimientos.entrySet()) {
				if (entry.getValue().equals(tdExpediente.getProcedimientoId())) {
					procedimientoSeleccionado = entry.getKey();
					break;
				}
			}

			for (Entry<String, Integer> entry : hashMapTdTipoTramite.entrySet()) {
				if (entry.getValue().equals(tdExpediente.getTipoTramiteId())) {
					tramiteSeleccionado = entry.getKey();
					break;
				}
			}

			/** Para el tipoDocumento */
			listTdDocumentoTramites.clear();
			listTdDocumentoTramites = tramiteDocumentarioBo.getAllDocumentoTramitesByTipoTramite(tdExpediente
					.getTipoTramiteId());

			listSelectItemTdDocumentoTramite.clear();
			hashMapTdDocumentoTramite.clear();

			for (TdDocumentoTramite tdDocumentoTramite : listTdDocumentoTramites) {
				SelectItem item = new SelectItem();
				item.setLabel(tdDocumentoTramite.getDescripcion());
				item.setValue(tdDocumentoTramite.getDescripcion());
				listSelectItemTdDocumentoTramite.add(item);
				hashMapTdDocumentoTramite.put(tdDocumentoTramite.getDescripcion(),
						tdDocumentoTramite.getDocuTramiteId());
				if (tdDocumentoTramite.getDocuTramiteId() == tdExpediente.getDocuTramiteId().intValue()) {
					descDocumentoTramite = tdDocumentoTramite.getDescripcion();
				}
			}

			this.verFormRegistro = Boolean.TRUE;
		} catch (SisatException e) {

			WebMessages.messageError(e.getMessage());

		} catch (Exception e) {

			WebMessages.messageError(e.getMessage());
		}

	}
	

	public void prepararNuevoExpediente() {

		this.reinicioComponentes();
		this.verFormRegistro = Boolean.TRUE;

	}
	
	public void prepararNuevaDj() {

		this.reinicioComponentesDJ();
		this.verFormRegistro = Boolean.TRUE;

	} 
	
	public void quitarContribuyente(){
		this.tdExpediente.setContribuyenteId(null);
		this.contribuyente = new BuscarPersonaDTO();
	}
	
	public void quitarConyuge(){
		this.tdDj.setContribuyenteId(null);
		this.relacionadoCon = new RelacionadosDTO();
	}

	public void changeRowItem() {
		listAnexoDTOs.get(indexTblAnexo).setEstado(AnexoDTO.TipoEstadoAnexo.EnEdicion);
	}

	public void changeRowItemValores() {
		listTdDataValors.get(indexTblAnexo).setEnEdicion(true);
	}

	public void quitRowItem() {
		listAnexoDTOs.remove(indexTblAnexo);
	}

	public void quitRowItemValor() {
		listTdDataValors.remove(indexTblAnexo);
	}

	public List<AnexoDTO> getListAnexoDTOs() {
		return listAnexoDTOs;
	}

	public void setListAnexoDTOs(List<AnexoDTO> listAnexoDTOs) {
		this.listAnexoDTOs = listAnexoDTOs;
	}

	public int getIndexTblAnexo() {
		return indexTblAnexo;
	}

	public void setIndexTblAnexo(int indexTblAnexo) {
		this.indexTblAnexo = indexTblAnexo;
	}

	public TdExpediente getTdExpediente() {
		return tdExpediente;
	}

	public void setTdExpediente(TdExpediente tdExpediente) {
		this.tdExpediente = tdExpediente;
	}
	
	public TdEstadoExpediente getTdEstadoExpediente() {
		return tdEstadoExpediente;
	}

	public void setTdEstadoExpediente(TdEstadoExpediente tdEstadoExpediente) {
		this.tdEstadoExpediente = tdEstadoExpediente;
	}
	//para visualizar situacion de expediente
	public TdSituacionExpediente getTdSituacionExpediente() {
		return tdSituacionExpediente;
	}

	public void setTdSituacionExpediente(TdSituacionExpediente tdSituacionExpediente) {
		this.tdSituacionExpediente = tdSituacionExpediente;
	}
	
	public List<TdTipoTramite> getListTdTipoTramites() {
		return listTdTipoTramites;
	}

	public void setListTdTipoTramites(List<TdTipoTramite> listTdTipoTramites) {
		this.listTdTipoTramites = listTdTipoTramites;
	}

	public List<TdDocumentoTramite> getListTdDocumentoTramites() {
		return listTdDocumentoTramites;
	}

	public void setListTdDocumentoTramites(List<TdDocumentoTramite> listTdDocumentoTramites) {
		this.listTdDocumentoTramites = listTdDocumentoTramites;
	}

	@Override
	public void setSelectedVia(UbicacionDTO currentItem) {

		setUbicacionDTO(currentItem);

	}

	public UbicacionDTO getUbicacionDTO() {
		return ubicacionDTO;
	}

	public void setUbicacionDTO(UbicacionDTO ubicacionDTO) {
		this.ubicacionDTO = ubicacionDTO;
	}

	public UbicacionDTO getUbicacionDTOContribuyente() {
		return ubicacionDTOContribuyente;
	}

	public void setUbicacionDTOContribuyente(UbicacionDTO ubicacionDTOContribuyente) {
		this.ubicacionDTOContribuyente = ubicacionDTOContribuyente;
	}

	public UbicacionDTO getUbicacionDTORepresentante() {
		return ubicacionDTORepresentante;
	}

	public void setUbicacionDTORepresentante(UbicacionDTO ubicacionDTORepresentante) {
		this.ubicacionDTORepresentante = ubicacionDTORepresentante;
	}

	public BuscarPersonaDTO getContribuyente() {
		return contribuyente;
	}

	public void setContribuyente(BuscarPersonaDTO contribuyente) {
		this.contribuyente = contribuyente;
	}

	// public BuscarPersonaDTO getRepresentante() {
	// return representante;
	// }
	//
	// public void setRepresentante(BuscarPersonaDTO representante) {
	// this.representante = representante;
	// }
	
	
	public List<GnUnidad> getListGnUnidad() {
		return listGnUnidad;
	}

	public void setListGnUnidad(List<GnUnidad> listGnUnidad) {
		this.listGnUnidad = listGnUnidad;
	}
	
	public List<TdEstadoExpediente> getListTdEstadoExpediente() {
		return listTdEstadoExpediente;
	}

	public void setListTdEstadoExpediente(List<TdEstadoExpediente> listTdEstadoExpediente) {
		this.listTdEstadoExpediente = listTdEstadoExpediente;
	}
	
	public List<TdSituacionExpediente> getListTdSituacionExpediente() {
		return listTdSituacionExpediente;
	}

	public void setListTdSituacionExpediente(List<TdSituacionExpediente> listTdSituacionExpediente) {
		this.listTdSituacionExpediente = listTdSituacionExpediente;
	}

	public List<ItemBandejaEntradaDTO> getListItemBandejaEntradaDTOs() {
		return listItemBandejaEntradaDTOs;
	}
	
	public void setListItemBandejaEntradaDTOs(List<ItemBandejaEntradaDTO> listItemBandejaEntradaDTOs) {
		this.listItemBandejaEntradaDTOs = listItemBandejaEntradaDTOs;
	}

	//Para ver historico de actualizaciones
	public List<ItemHistoricoEntradaDTO> getListItemHistoricoEntradaDTOs() {
		return listItemHistoricoEntradaDTOs;
	}
	
	public void setListItemHistoricoEntradaDTOs(List<ItemHistoricoEntradaDTO> listItemHistoricoEntradaDTOs) {
		this.listItemHistoricoEntradaDTOs = listItemHistoricoEntradaDTOs;
	}
	
	//Para ver seguimiento de expediente
	public List<ItemSeguimientoEntradaDTO> getListItemSeguimientoEntradaDTOs() {
		return listItemSeguimientoEntradaDTOs;
	}
	
	public void setListItemSeguimientoEntradaDTOs(List<ItemSeguimientoEntradaDTO> listItemSeguimientoEntradaDTOs) {
		this.listItemSeguimientoEntradaDTOs = listItemSeguimientoEntradaDTOs;
	}
	
	public List<ItemSeguimientoExpedienteDTO> getListItemSeguimientoExpedienteDTOs() {
		return litsItemSeguimientoExpedienteDTOs;
	}
	
	public void setListItemSeguimientoExpedienteDTOs(List<ItemSeguimientoExpedienteDTO> litsItemSeguimientoExpedienteDTOs) {
		this.litsItemSeguimientoExpedienteDTOs = litsItemSeguimientoExpedienteDTOs;
	}	
	//Hasta aqui
	
	public Boolean getVerFormRegistro() {
		return verFormRegistro;
	}

	public void setVerFormRegistro(Boolean verFormRegistro) {
		this.verFormRegistro = verFormRegistro;
	}

	public List<TdDataValor> getListTdDataValors() {
		return listTdDataValors;
	}

	public void setListTdDataValors(List<TdDataValor> listTdDataValors) {
		this.listTdDataValors = listTdDataValors;
	}

	public List<SelectItem> getListSelectItemTdTipoTramite() {
		return listSelectItemTdTipoTramite;
	}

	public void setListSelectItemTdTipoTramite(List<SelectItem> listSelectItemTdTipoTramite) {
		this.listSelectItemTdTipoTramite = listSelectItemTdTipoTramite;
	}

	public List<SelectItem> getListSelectItemTdDocumentoTramite() {
		return listSelectItemTdDocumentoTramite;
	}

	public void setListSelectItemTdDocumentoTramite(List<SelectItem> listSelectItemTdDocumentoTramite) {
		this.listSelectItemTdDocumentoTramite = listSelectItemTdDocumentoTramite;
	}

	public List<TdRequisitoExpediente> getListTdRequisitoExpedientes() {
		return listTdRequisitoExpedientes;
	}

	public void setListTdRequisitoExpedientes(List<TdRequisitoExpediente> listTdRequisitoExpedientes) {
		this.listTdRequisitoExpedientes = listTdRequisitoExpedientes;
	}

	public String getDescDocumentoTramite() {
		return descDocumentoTramite;
	}

	public void setDescDocumentoTramite(String descDocumentoTramite) {
		this.descDocumentoTramite = descDocumentoTramite;
	}

	public BusquedaExpedienteDTO getBusquedaExpedienteDTO() {
		return busquedaExpedienteDTO;
	}

	public void setBusquedaExpedienteDTO(BusquedaExpedienteDTO busquedaExpedienteDTO) {
		this.busquedaExpedienteDTO = busquedaExpedienteDTO;
	}

	public TdRepresentante getTdRepresentante() {
		return tdRepresentante;
	}

	public void setTdRepresentante(TdRepresentante tdRepresentante) {
		this.tdRepresentante = tdRepresentante;
	}

	public RemitenteDTO getTdRemitente() {
		return tdRemitente;
	}

	public void setTdRemitente(RemitenteDTO tdRemitente) {
		this.tdRemitente = tdRemitente;
	}
	public Boolean getCopiarDesdeContribuyente() {
		return copiarDesdeContribuyente;
	}

	public void setCopiarDesdeContribuyente(Boolean copiarDesdeContribuyente) {
		this.copiarDesdeContribuyente = copiarDesdeContribuyente;
	}

	public Boolean getVerPanelRepresentante() {
		return verPanelRepresentante;
	}

	public void setVerPanelRepresentante(Boolean verPanelRepresentante) {
		this.verPanelRepresentante = verPanelRepresentante;
		
		if (verPanelRepresentante == true)
		{	
			tieneContribuyente = false;
			tieneRepresentante = true;
		}
		if (verPanelRepresentante == false)
		{
			tieneContribuyente = true;	
			tieneRepresentante = false;
		}
	}
	
	public HashMap<String, Integer> getListProcedimientos() {
		return listProcedimientos;
	}

	public void setListProcedimientos(HashMap<String, Integer> listProcedimientos) {
		this.listProcedimientos = listProcedimientos;
	}

	public List<SelectItem> getListSelectItemProcedimientos() {
		return listSelectItemProcedimientos;
	}

	public void setListSelectItemProcedimientos(List<SelectItem> listSelectItemProcedimientos) {
		this.listSelectItemProcedimientos = listSelectItemProcedimientos;
	}

	public boolean isVistaExpedienteSimple() {
		return vistaExpedienteSimple;
	}

	public void setVistaExpedienteSimple(boolean vistaExpedienteSimple) {
		this.vistaExpedienteSimple = vistaExpedienteSimple;
	}
	
	//para ver tipo de entidad (natural, juridica)
	public boolean isVistaEntidad() {
		return vistaEntidad;
	}

	public void setVistaEntidad(boolean vistaEntidad) {
		this.vistaEntidad = vistaEntidad;
	}
	//Hasta aqui
	
	//para tipo de remitente	
	public boolean isVistaRemitente() {
		return vistaRemitente;
	}

	public void setVistaRemitente(boolean vistaRemitente) {
		this.vistaRemitente = vistaRemitente;
	}

	public String getProcedimientoSeleccionado() {
		return procedimientoSeleccionado;
	}

	public void setProcedimientoSeleccionado(String procedimientoSeleccionado) {
		this.procedimientoSeleccionado = procedimientoSeleccionado;
	}
	
	public Boolean getMantenerDataContribuyente() {
		return mantenerDataContribuyente;
	}

	public void setMantenerDataContribuyente(Boolean mantenerDataContribuyente) {
		this.mantenerDataContribuyente = mantenerDataContribuyente;
	}
	
	public Boolean getMantenerDataRepresentante() {
		return mantenerDataRepresentante;
	}

	public void setMantenerDataRepresentante(Boolean mantenerDataRepresentante) {
		this.mantenerDataRepresentante = mantenerDataRepresentante;
	}
	
	/**
	 * @return the tramiteSeleccionado
	 */
	public String getTramiteSeleccionado() {
		return tramiteSeleccionado;
	}

	/**
	 * @param tramiteSeleccionado
	 *            the tramiteSeleccionado to set
	 */
	public void setTramiteSeleccionado(String tramiteSeleccionado) {
		this.tramiteSeleccionado = tramiteSeleccionado;
	}

	/**
	 * @return the listDocumentoAnexos
	 */
	public List<TdDocumentoAnexo> getListDocumentoAnexos() {
		return listDocumentoAnexos;
	}

	/**
	 * @param listDocumentoAnexos
	 *            the listDocumentoAnexos to set
	 */
	public void setListDocumentoAnexos(List<TdDocumentoAnexo> listDocumentoAnexos) {
		this.listDocumentoAnexos = listDocumentoAnexos;
	}

	/**
	 * @return the indexTblDocumentoAnexo
	 */
	public int getIndexTblDocumentoAnexo() {
		return indexTblDocumentoAnexo;
	}

	/**
	 * @param indexTblDocumentoAnexo
	 *            the indexTblDocumentoAnexo to set
	 */
	public void setIndexTblDocumentoAnexo(int indexTblDocumentoAnexo) {
		this.indexTblDocumentoAnexo = indexTblDocumentoAnexo;
	}

	/**
	 * @return the listGnTipoDocumentos
	 */
	public List<GnTipoDocumento> getListGnTipoDocumentos() {
		return listGnTipoDocumentos;
	}

	/**
	 * @param listGnTipoDocumentos
	 *            the listGnTipoDocumentos to set
	 */
	public void setListGnTipoDocumentos(List<GnTipoDocumento> listGnTipoDocumentos) {
		this.listGnTipoDocumentos = listGnTipoDocumentos;
	}

	/**
	 * @return the enEdicion
	 */
	public boolean isEnEdicion() {
		return enEdicion;
	}

	/**
	 * @param enEdicion
	 *            the enEdicion to set
	 */
	public void setEnEdicion(boolean enEdicion) {
		this.enEdicion = enEdicion;
	}

	public List<TdEstadoExpediente> getListEstadoExpedientes() {
		return listEstadoExpedientes;
	}

	public void setListEstadoExpedientes(List<TdEstadoExpediente> listEstadoExpedientes) {
		this.listEstadoExpedientes = listEstadoExpedientes;
	}
	
	public List<TdSituacionExpediente> getListSituacionExpedientes() {
		return listSituacionExpedientes;
	}

	public void setListSituacionExpedientes(List<TdSituacionExpediente> listSituacionExpedientes) {
		this.listSituacionExpedientes = listSituacionExpedientes;
	}
	
	public String getCorrelativoActual() {
		return correlativoActual;
	}

	public void setCorrelativoActual(String correlativoActual) {
		this.correlativoActual = correlativoActual;
	}

	public Boolean getTieneContribuyente() {
		return tieneContribuyente;
	}

	public void setTieneContribuyente(Boolean tieneContribuyente) {
		this.tieneContribuyente = tieneContribuyente;
	}

	public Boolean getTieneRepresentante() {
		return tieneRepresentante;
	}

	public void setTieneRepresentante(Boolean tieneRepresentante) {
		this.tieneRepresentante = tieneRepresentante;
	}

	public boolean isDisableFechaRecepcion() {
		return disableFechaRecepcion;
	}

	public void setDisableFechaRecepcion(boolean disableFechaRecepcion) {
		this.disableFechaRecepcion = disableFechaRecepcion;
	}	
	
	//para guardar numero generico de expediente: Mostrar en seguimmiento y actualizaciones (historico)
	public String getNroExpeGenerico(){
		return nroExpeGenerico;
	}
	
	public void SetNroExpeGenerico(String nroExpeGenerico){
		this.nroExpeGenerico = nroExpeGenerico;
	}
	
	//Desde aqui se obtiene valores para expediente digitalizado (nombre, ubicacion archivo y metadatos)
	public String getNomArchivoExpeDig(){
		return nomArchivoExpeDig;
	}
	
	public void SetNomArchivoExpeDig(String nomArchivoExpeDig){
		this.nomArchivoExpeDig = nomArchivoExpeDig;
	}
	
	public String getUbiArchivoExpeDig(){
		return ubiArchivoExpeDig;
	}
	
	public void SetUbiArchivoExpeDig(String ubiArchivoExpeDig){
		this.ubiArchivoExpeDig = ubiArchivoExpeDig;
	}
	
	public Integer getIdExpeDig(){
		return idExpeDig;
	}
	
	public void setIdExpeDig(Integer idExpeDig){
		this.idExpeDig = idExpeDig;
	}
	
	public String getRefDocumentoExpe(){
		return refDocumentoExpe;
	}
	
	public void setRefDocumentoExpe(String refDocumentoExpe){
		this.refDocumentoExpe = refDocumentoExpe;
	}
	
	public String getAsuntoExp(){
		return asuntoExp;
	}
	
	public void setAsuntoExp(String asuntoExp){
		this.asuntoExp = asuntoExp;
	}
	
	public String getRemiteExp(){
		return remiteExp;
	}
	
	public void setRemiteExp(String remiteExp){
		this.remiteExp = remiteExp;
	}
	
	public String getFirmaExp(){
		return firmaExp;
	}
	
	public void setFirmaExp(String firmaExp){
		this.firmaExp = firmaExp;
	}
	
	public String getObservacionExp(){
		return observacionExp;
	}
	
	public void setObservacionExp(String observacionExp){
		this.observacionExp = observacionExp;
	}
	
	public String getTramiteExp(){
		return tramiteExp;
	}
	
	public void setTramiteExp(String tramiteExp){
		this.tramiteExp = tramiteExp;
	}
	
	public Date getFechaPresentacionExp(){
		return fechaPresentacionExp;
	}
	
	public void setFechaPresentacionExp(Date fechaPresentacionExp){
		this.fechaPresentacionExp = fechaPresentacionExp;
	}
	
	public String getEstadoExp(){
		return estadoExp;
	}
	
	public void SetEstadoExp(String estadoExp){
		this.estadoExp = estadoExp;
	}

	public int getTipoRemitente() {
		return tipoRemitente;
	}

	public void setTipoRemitente(int tipoRemitente) {
		this.tipoRemitente = tipoRemitente;
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

	public boolean isPermisoAgregarRegistrar() {
		return permisoAgregarRegistrar;
	}

	public void setPermisoAgregarRegistrar(boolean permisoAgregarRegistrar) {
		this.permisoAgregarRegistrar = permisoAgregarRegistrar;
	}

	public boolean isPermisoModificarActualizar() {
		return permisoModificarActualizar;
	}

	public void setPermisoModificarActualizar(boolean permisoModificarActualizar) {
		this.permisoModificarActualizar = permisoModificarActualizar;
	}

	public boolean isPermisoDigitalizar() {
		return permisoDigitalizar;
	}

	public void setPermisoDigitalizar(boolean permisoDigitalizar) {
		this.permisoDigitalizar = permisoDigitalizar;
	}

	public boolean isPermisoSeguimiento() {
		return permisoSeguimiento;
	}

	public void setPermisoSeguimiento(boolean permisoSeguimiento) {
		this.permisoSeguimiento = permisoSeguimiento;
	}

	public boolean isPermisoHistoricoModificaciones() {
		return permisoHistoricoModificaciones;
	}

	public void setPermisoHistoricoModificaciones(boolean permisoHistoricoModificaciones) {
		this.permisoHistoricoModificaciones = permisoHistoricoModificaciones;
	}

	public List<ItemBandejaEntradaDTO> getListItemBandejaEntradaDTOsServAdult() {
		return listItemBandejaEntradaDTOsServAdult;
	}

	public void setListItemBandejaEntradaDTOsServAdult(List<ItemBandejaEntradaDTO> listItemBandejaEntradaDTOsServAdult) {
		this.listItemBandejaEntradaDTOsServAdult = listItemBandejaEntradaDTOsServAdult;
	}

	public List<TdDocumentoTramite> getListTdDocumentoTramitesAdultPens() {
		return listTdDocumentoTramitesAdultPens;
	}

	public void setListTdDocumentoTramitesAdultPens(List<TdDocumentoTramite> listTdDocumentoTramitesAdultPens) {
		this.listTdDocumentoTramitesAdultPens = listTdDocumentoTramitesAdultPens;
	}

	public List<SelectItem> getListSelectItemTdTipoTramiteAdult() {
		return listSelectItemTdTipoTramiteAdult;
	}

	public void setListSelectItemTdTipoTramiteAdult(List<SelectItem> listSelectItemTdTipoTramiteAdult) {
		this.listSelectItemTdTipoTramiteAdult = listSelectItemTdTipoTramiteAdult;
	}

	public List<TdTipoTramite> getListTdTipoTramitesAdult() {
		return listTdTipoTramitesAdult;
	}

	public void setListTdTipoTramitesAdult(List<TdTipoTramite> listTdTipoTramitesAdult) {
		this.listTdTipoTramitesAdult = listTdTipoTramitesAdult;
	}

	public String getCorrelativoActualDJ() {
		return correlativoActualDJ;
	}

	public void setCorrelativoActualDJ(String correlativoActualDJ) {
		this.correlativoActualDJ = correlativoActualDJ;
	}

	public List<MpRelacionado> getListMpRelacionados() {
		return listMpRelacionados;
	}

	public void setListMpRelacionados(List<MpRelacionado> listMpRelacionados) {
		this.listMpRelacionados = listMpRelacionados;
	}

	public List<RelacionadosDTO> getRecordsRelacionado() {
		return recordsRelacionado;
	}

	public void setRecordsRelacionado(List<RelacionadosDTO> recordsRelacionado) {
		this.recordsRelacionado = recordsRelacionado;
	}

	public TdDJ getTdDj() {
		return tdDj;
	}

	public void setTdDj(TdDJ tdDj) {
		this.tdDj = tdDj;
	}

	public Boolean getMantenerDataConyuge() {
		return mantenerDataConyuge;
	}

	public void setMantenerDataConyuge(Boolean mantenerDataConyuge) {
		this.mantenerDataConyuge = mantenerDataConyuge;
	}

	public Boolean getMantenerDataUnicaPropiedad() {
		return mantenerDataUnicaPropiedad;
	}

	public void setMantenerDataUnicaPropiedad(Boolean mantenerDataUnicaPropiedad) {
		this.mantenerDataUnicaPropiedad = mantenerDataUnicaPropiedad;
	}

	public Boolean getCopiarDesdeConyuge() {
		return copiarDesdeConyuge;
	}

	public void setCopiarDesdeConyuge(Boolean copiarDesdeConyuge) {
		this.copiarDesdeConyuge = copiarDesdeConyuge;
	}

	public Boolean getCopiarDesdeUnicaPropiedad() {
		return copiarDesdeUnicaPropiedad;
	}

	public void setCopiarDesdeUnicaPropiedad(Boolean copiarDesdeUnicaPropiedad) {
		this.copiarDesdeUnicaPropiedad = copiarDesdeUnicaPropiedad;
	}
	
	public TdDjConyuge getTdDjConyuge() {
		return tdDjConyuge;
	}

	public void setTdDjConyuge(TdDjConyuge tdDjConyuge) {
		this.tdDjConyuge = tdDjConyuge;
	}

	public TdDjUnicaPropiedad getTdDjUnicaPropiedad() {
		return tdDjUnicaPropiedad;
	}

	public void setTdDjUnicaPropiedad(TdDjUnicaPropiedad tdDjUnicaPropiedad) {
		this.tdDjUnicaPropiedad = tdDjUnicaPropiedad;
	}

	public TdDjUnicaPropiedad getUnicaPropiedad() {
		return unicaPropiedad;
	}

	public void setUnicaPropiedad(TdDjUnicaPropiedad unicaPropiedad) {
		this.unicaPropiedad = unicaPropiedad;
	}

	public DeclaracionJuradaAdultDTO getNuevaDj() {
		return nuevaDj;
	}

	public void setNuevaDj(DeclaracionJuradaAdultDTO nuevaDj) {
		this.nuevaDj = nuevaDj;
	}

	public RelacionadosDTO getRelacionadoCon() {
		return relacionadoCon;
	}

	public void setRelacionadoCon(RelacionadosDTO relacionadoCon) {
		this.relacionadoCon = relacionadoCon;
	}

	public Boolean getTieneConyuge() {
		return tieneConyuge;
	}

	public void setTieneConyuge(Boolean tieneConyuge) {
		this.tieneConyuge = tieneConyuge;
	}

	public Boolean getTieneUnicaPropiedad() {
		return tieneUnicaPropiedad;
	}

	public void setTieneUnicaPropiedad(Boolean tieneUnicaPropiedad) {
		this.tieneUnicaPropiedad = tieneUnicaPropiedad;
	}

	public MpRelacionado getRelacionado() {
		return relacionado;
	}

	public void setRelacionado(MpRelacionado relacionado) {
		this.relacionado = relacionado;
	}

	public RelacionadosDTO getRelacionadoC() {
		return relacionadoC;
	}

	public void setRelacionadoC(RelacionadosDTO relacionadoC) {
		this.relacionadoC = relacionadoC;
	}

	public TdRequisitoExpediente getTdRequisitoExpediente() {
		return tdRequisitoExpediente;
	}

	public void setTdRequisitoExpediente(TdRequisitoExpediente tdRequisitoExpediente) {
		this.tdRequisitoExpediente = tdRequisitoExpediente;
	}

	public RequisitoExpededienteDTO getRequisitoExpedienteDj() {
		return requisitoExpedienteDj;
	}

	public void setRequisitoExpediente(RequisitoExpededienteDTO requisitoExpedienteDj) {
		this.requisitoExpedienteDj = requisitoExpedienteDj;
	}

	public String getCorrelativoActualDJ1() {
		return correlativoActualDJ1;
	}

	public void setCorrelativoActualDJ1(String correlativoActualDJ1) {
		this.correlativoActualDJ1 = correlativoActualDJ1;
	}

	public List<DeclaracionJuradaAdultDTO> getListDjadulto() {
		return listDjadulto;
	}

	public void setListDjadulto(List<DeclaracionJuradaAdultDTO> listDjadulto) {
		this.listDjadulto = listDjadulto;
	}

	public DeclaracionJuradaAdultDTO getBusquedaDjAdultDTO() {
		return busquedaDjAdultDTO;
	}

	public void setBusquedaDjAdultDTO(DeclaracionJuradaAdultDTO busquedaDjAdultDTO) {
		this.busquedaDjAdultDTO = busquedaDjAdultDTO;
	}

	public String getCorrelativoResolucionDj() {
		return correlativoResolucionDj;
	}

	public void setCorrelativoResolucionDj(String correlativoResolucionDj) {
		this.correlativoResolucionDj = correlativoResolucionDj;
	}


	public String getRutaImagen() {
		return rutaImagen;
	}

	public void setRutaImagen(String rutaImagen) {
		this.rutaImagen = rutaImagen;
	}

	public byte[] getAdjRequisito() {
		return adjRequisito;
	}

	public void setAdjRequisito(byte[] adjRequisito) {
		this.adjRequisito = adjRequisito;
	}

	public int getDjtipoTramiteId() {
		return djtipoTramiteId;
	}

	public void setDjtipoTramiteId(int djtipoTramiteId) {
		this.djtipoTramiteId = djtipoTramiteId;
	}

	public BuscarPersonaDTO getRecurrenteCont() {
		return recurrenteCont;
	}

	public void setRecurrenteCont(BuscarPersonaDTO recurrenteCont) {
		this.recurrenteCont = recurrenteCont;
	}

	public String getCorrelativoActualDJ2() {
		return correlativoActualDJ2;
	}

	public void setCorrelativoActualDJ2(String correlativoActualDJ2) {
		this.correlativoActualDJ2 = correlativoActualDJ2;
	}


	


}
