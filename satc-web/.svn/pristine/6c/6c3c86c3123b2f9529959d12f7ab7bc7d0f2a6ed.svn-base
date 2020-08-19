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
import org.richfaces.component.html.HtmlExtendedDataTable;
import org.richfaces.model.selection.SimpleSelection;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.FacesUtil;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.fiscalizacion.business.FiscalizacionBoRemote;
import com.sat.sisat.fiscalizacion.dto.FindInspeccionById;
import com.sat.sisat.fiscalizacion.dto.FindInspeccionByIdAsociada;
import com.sat.sisat.fiscalizacion.dto.FindInspeccionDj;
import com.sat.sisat.fiscalizacion.dto.FindInspeccionHistorial;
import com.sat.sisat.menus.business.MenuBoRemote;
import com.sat.sisat.menus.dto.SimpleMenuDTO;
import com.sat.sisat.persistence.entity.DtFactorOfic;
import com.sat.sisat.persistence.entity.RjDocuAnexo;
import com.sat.sisat.persistence.entity.RpDjarbitrio;
import com.sat.sisat.persistence.entity.RpDjconstruccion;
import com.sat.sisat.persistence.entity.RpDjdireccion;
import com.sat.sisat.persistence.entity.RpDjpredial;
import com.sat.sisat.persistence.entity.RpDjuso;
import com.sat.sisat.persistence.entity.RpDjusoDetalle;
import com.sat.sisat.persistence.entity.RpFiscalizacionHorarioDetalle;
import com.sat.sisat.persistence.entity.RpInstalacionDj;
import com.sat.sisat.persistence.entity.RpOtrosFrente;
import com.sat.sisat.persistence.entity.RpTipoObra;
import com.sat.sisat.persistence.entity.RpTipoObraPeriodo;
import com.sat.sisat.persistence.entity.RpTransferenciaPropiedad;
import com.sat.sisat.predial.business.RegistroPrediosBoRemote;
import com.sat.sisat.predial.dto.FindMpPersona;
import com.sat.sisat.predial.dto.FindRpDjPredial;
import com.sat.sisat.predial.dto.ListRpDjPredial;
import com.sat.sisat.predial.dto.PreliminarDescargoPredDTO;
import com.sat.sisat.vehicular.dto.AnexosDeclaVehicDTO;
import com.sat.sisat.vehicular.dto.BuscarPersonaDTO;

@ManagedBean
@ViewScoped
public class ListaDJPredioManaged extends BaseManaged {

	private static final long serialVersionUID = 1215730818441379392L;
	
	@EJB
	RegistroPrediosBoRemote registroPrediosBo;
	
	@EJB
	FiscalizacionBoRemote ficalizacionBo;
	
	@EJB
	MenuBoRemote menuBo;
	
	private ArrayList<ListRpDjPredial> records;
	private SimpleSelection selection = new SimpleSelection();
	private HtmlExtendedDataTable table;
	private int currentRow;
	private ListRpDjPredial currentItem = new ListRpDjPredial();
	
	private String codigoPredio;
	
	private FindRpDjPredial findRpDjPredial;
	private int anioCrear;
	private String rsCopiarDj= "E"; // Error
	
	private List<BuscarPersonaDTO> lstTransferentes = new ArrayList<BuscarPersonaDTO>();//
	private List<AnexosDeclaVehicDTO> lstAnexos = new ArrayList<AnexosDeclaVehicDTO>();//	
	
	private Boolean tieneInspeccion;
	private Boolean isInspeccion;
	private Boolean isEditable;
	private FindInspeccionHistorial currentItemValorInsp;
	
	//Asociar requerimiento a traves de un combo
	private String cmbReq;
	private HtmlComboBox cmbxReq;
	private Integer reqId;
	private HashMap<String, Integer> mapReq = new HashMap<String, Integer>();
	private List<SelectItem> listaReqs = new ArrayList<SelectItem>();
	
	//Asociar requerimiento a traves de un panel
	private List<FindInspeccionDj> listarequer = new ArrayList<FindInspeccionDj>();
	private Boolean esregistrado;
	
	private Integer reqIdElegida;
	private String  reqNroElegida;
	private List<FindInspeccionByIdAsociada> inspeccionelegida = new ArrayList<FindInspeccionByIdAsociada>();
	
	
	private List<FindInspeccionDj> listaDjInspeccion = new ArrayList<FindInspeccionDj>();
	private Integer nombreInspector;
	private Integer nombreInspectorAr;
	private Date fechaInspeccion;
	private Date fechaInspeccionAr;
	private Boolean esFIP;
	private Boolean esAINR;
	private Integer inspeccionesId;
	
	// INICIO PERMISOS PARA EL MODULO -=CRAMIREZ=-
		private List<SimpleMenuDTO> listPermisosSubmenu = new ArrayList<SimpleMenuDTO>();
		private boolean permisoImprimiDJ;
		private boolean permisoDescargarDJ;
		private boolean permisoImprimirDescargoDJ;
		private boolean permisoVerDJ;
		private boolean permisoCopiarDJ;
		private boolean permisoActualizarDJ;
	// FIN PERMISOS PARA EL MODULO		

	public ListaDJPredioManaged(){
		
	}
	@PostConstruct
	public void init(){
		permisosMenu();
		try{
			records=new ArrayList<ListRpDjPredial>(); 
			findRpDjPredial=(FindRpDjPredial)getSessionMap().get("findRpDjPredial");
			
			Integer PredioId=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextPredioId");
			if(PredioId!=null){
				records=registroPrediosBo.getListRpDjpredial(PredioId,findRpDjPredial.getPersonaId());
				if(records.size()>0){
					findRpDjPredial.setMotivoDeclaracionId(String.valueOf(records.get(0).getMotivoDeclaracionId()));
					setCodigoPredio(String.valueOf(records.get(0).getPredioId()));
				}
				setCodigoPredio(String.valueOf(PredioId));
				
				/*** Control de Requerimientos */
				
				if (currentItemValorInsp!=null){
					currentItemValorInsp=(FindInspeccionHistorial) getSessionMap().get("currentItemRequerimiento");
				}
				
				Integer anioDJ = Integer.parseInt(findRpDjPredial.getAnioDj());
				listarequer=ficalizacionBo.getDeclaracionesInspByPersona(findRpDjPredial.getPersonaId(),findRpDjPredial.getPredioId(),anioDJ);
				
				/*** Control de Requerimientos */
				
			}else{
				WebMessages.messageError("Debe seleccionar el predio");
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public void permisosMenu() {	
		try {
			int submenuId = Constante.DECLARACION_JURADA;
			
			int permisoImprimiDJId = Constante.IMPRIMIR_DJ;
			int permisoDescargarDJId = Constante.DESCARGAR_DJ;
			int permisoImprimirDescargoDJId = Constante.IMPRIMIR_DESCARGO_DJ;
			int permisoVerDJId = Constante.VER_DJ;
			int permisoCopiarDJId = Constante.COPIAR_DJ;
			int permisoActualizarDJId = Constante.ACTUALIZAR_DJ;
			
			permisoImprimiDJ = false;
			permisoDescargarDJ = false;
			permisoImprimirDescargoDJ = false;
			permisoVerDJ = false;
			permisoCopiarDJ = false ;
			permisoActualizarDJ = false;
			
			listPermisosSubmenu  = menuBo.getAccesosSubmenuUsuario( getSessionManaged().getUsuarioLogIn().getUsuarioId() , submenuId);
			
			Iterator<SimpleMenuDTO> menuIterar = listPermisosSubmenu.iterator();
			while (menuIterar.hasNext()) {
				SimpleMenuDTO lsm = menuIterar.next();
				if(lsm.getItemId() == permisoImprimiDJId) {
					permisoImprimiDJ = true;
				}
				if(lsm.getItemId() == permisoDescargarDJId) {
					permisoDescargarDJ = true;
				}
				if(lsm.getItemId() == permisoImprimirDescargoDJId) {
					permisoImprimirDescargoDJ = true;
				}
				if(lsm.getItemId() == permisoVerDJId) {
					permisoVerDJ = true;
				}				
				if(lsm.getItemId() == permisoCopiarDJId) {
					permisoCopiarDJ = true;
				}
				if(lsm.getItemId() == permisoActualizarDJId) {
					permisoActualizarDJ = true;
				}
			}
			
		} catch (SisatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String salir(){		
		getSessionMap().remove("listaDJPredioManaged");
		return sendRedirectPrincipal();
	}
	
	public String visualizadj(){
		try{
			if(currentItem!=null){
				FacesUtil.closeSession("registroPredioManaged");
				FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "RECORD_STATUS",Constante.RECORD_STATUS_VIEW);
				FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "NextPredioId",currentItem.getPredioId());
				FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId",currentItem.getDjId());
				//Todas las Dj que se registran por este Managed es MOTIVO: DECLARACION POR ACTUALIZACION
				FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "MOTIVO_DECLARACION",Constante.MOTIVO_DECLARACION_ACTUALIZA);
			}else{
				//selecciones
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		return sendRedirectPrincipal();
	}
	

	public String clonadj(){
		try{
			if(currentItem!=null){
				FacesUtil.closeSession("registroPredioManaged");
				FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "RECORD_STATUS",Constante.RECORD_STATUS_CLONE);
				FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "NextPredioId",currentItem.getPredioId());
				FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId",currentItem.getDjId());
				//Todas las Dj que se registran por este Managed es MOTIVO: DECLARACION POR ACTUALIZACION
				FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "MOTIVO_DECLARACION",Constante.MOTIVO_DECLARACION_ACTUALIZA);
				getSessionManaged().setPage("/sisat/predial/registropredio.xhtml");
			}else{
				//selecciones
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		return sendRedirectPrincipal();
	}
	

//ITantamango:ini
	public void imprimeDj(){
		try{
			if(currentItem!=null){
				FacesUtil.closeSession("registroPredioManaged");
				FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "RECORD_STATUS",Constante.RECORD_STATUS_VIEW);
				FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "NextPredioId",currentItem.getPredioId());
				FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId",currentItem.getDjId());
				FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "MOTIVO_DECLARACION",Constante.MOTIVO_DECLARACION_ACTUALIZA);
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}

	}
//ITantamango:fin
	
	
	//caltamirano:ini
	public String fiscalizarDjPendiente(){
		try{
			if(currentItem!=null){
				FacesUtil.closeSession("registroPredioManaged");
				FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "RECORD_STATUS",Constante.RECORD_STATUS_UPDATE_FISCA);
				FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "NextPredioId",currentItem.getPredioId());
				FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId",currentItem.getDjId());
				//Todas las Dj que se registran por este Managed es MOTIVO: DECLARACION POR ACTUALIZACION
				//ccFacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "MOTIVO_DECLARACION",Constante.MOTIVO_DECLARACION_ACTUALIZA);
				FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "MOTIVO_DECLARACION",currentItem.getMotivoDeclaracionId());
				getSessionManaged().setPage("/sisat/predial/registropredio.xhtml");
			}else{
				//selecciones
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		return sendRedirectPrincipal();
	}
	
	public String fiscalizarDj(){
		try{
			if(currentItem!=null){
				if(currentItem.getMotivoDeclaracionId()!=Constante.MOTIVO_DECLARACION_MIGRACION){
					FacesUtil.closeSession("registroPredioManaged");
					FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "RECORD_STATUS",Constante.RECORD_STATUS_CLONE_FISCA);
					FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "NextPredioId",currentItem.getPredioId());
					FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId",currentItem.getDjId());
					//Todas las Dj que se registran por este Managed es MOTIVO: DECLARACION POR ACTUALIZACION
					FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "MOTIVO_DECLARACION",Constante.MOTIVO_DECLARACION_ACTUALIZA);
					getSessionManaged().setPage("/sisat/predial/registropredio.xhtml");
				}else{
					WebMessages.messageError("No es posible fiscalizar la DJ cuyo motivo de declaración es Migración");		
				}
			}else{
				//selecciones
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		return sendRedirectPrincipal();
	}
	
	public String actualizarImpulsadaFisca(){
		try{
			if(currentItem!=null){
				FacesUtil.closeSession("registroPredioManaged");
				FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "RECORD_STATUS",Constante.RECORD_STATUS_CLONE_FISCA_ACEPTADA);
				FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "NextPredioId",currentItem.getPredioId());
				FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId",currentItem.getDjId());
				//Todas las Dj que se registran por este Managed es MOTIVO: DECLARACION POR ACTUALIZACION
				FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "MOTIVO_DECLARACION",Constante.MOTIVO_DECLARACION_ACTUALIZA);
				if(currentItem.getFiscalizado()==null || currentItem.getFiscalizado().isEmpty()){
					FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "flagFiscaPrevio","0");
				}else{
					FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "flagFiscaPrevio",currentItem.getFiscalizado());
				}
				getSessionManaged().setPage("/sisat/predial/registropredio.xhtml");
			}else{
				//selecciones
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		return sendRedirectPrincipal();
	}
	//caltamirano:fin
	
	public String actualizadj(){
		try{
			if(currentItem!=null){
				FacesUtil.closeSession("registroPredioManaged");
				FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "RECORD_STATUS",Constante.RECORD_STATUS_UPDATE);
				FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "NextPredioId",currentItem.getPredioId());
				FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "NextDjId",currentItem.getDjId());
				//Todas las Dj que se registran por este Managed es MOTIVO: DECLARACION POR ACTUALIZACION
				FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(), "MOTIVO_DECLARACION",currentItem.getMotivoDeclaracionId());
				getSessionManaged().setPage("/sisat/predial/registropredio.xhtml");
			}else{
				//selecciones
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		return sendRedirectPrincipal();
	}
	
	public String descargo(){
		FacesUtil.closeSession("descargoPredialManaged");
		return sendRedirectPrincipal();
	}
	
	public String copiarDjp() {
		rsCopiarDj = "E";
		try {
			int anioRango = DateUtil.getAnioActual();
			anioRango = anioRango - 15;
			
			if(anioRango > anioCrear){
				addErrorMessage("El año para el que se desea realizar la copia no esta en el rango aceptado. Debe ser mayor a: " + anioRango);
				return null;
			}
			
			//if(anioCrear>=DateUtil.getAnioActual().intValue()){
			if(anioCrear>DateUtil.getAnioActual().intValue()){
			//if(anioCrear>(DateUtil.getAnioActual().intValue()+1)){ //mjpajares se cambio de manera temporal para soportar el copiado de DJ hacia el 2014
				addErrorMessage("Solamente se permite hacer copias para años anteriores al actual");
				return null;
			}
			
			boolean isAtYear = registroPrediosBo.isDjpAtYear(anioCrear, getSessionManaged().getContribuyente().getPersonaId(), currentItem.getPredioId());
			if(isAtYear){
				rsCopiarDj = "W"; // Warn, DJ exists on year
				addWarnMessage("Ya existe una declaración jurada para el año que desea hacer la copia. ¿Desea actualizar la DJ. existente?");
				return null;
			}
			
			if(currentItem!=null){
				RpDjpredial djpredio = copiarDjpOtroAnio(currentItem.getDjId(), anioCrear, getSessionManaged().getUsuarioLogIn().getUsuarioId(), getSessionManaged().getTerminalLogIn());
				if(djpredio!=null){
					if(!djpredio.getMotivoDeclaracionId().equals(Constante.MOTIVO_DECLARACION_MIGRACION)){
						rsCopiarDj = "S"; // succesfull
						addInfoMessage("Copia realizada con éxito");
						Integer PredioId=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextPredioId");
						if(PredioId!=null){
							records=registroPrediosBo.getListRpDjpredial(PredioId,findRpDjPredial.getPersonaId());	
						}
					}else{
						rsCopiarDj = "F"; // Fatal Error
						addErrorMessage("No es posible realizar la copia de la DJ cuyo motivo de declaración es Migración");
					}
				}else{
					rsCopiarDj = "F"; // Fatal Error
					addErrorMessage("No ha sido posible realizar la copia");
				}
			}
		}catch(SisatException ex){
			addErrorMessage(ex.getMessage());
		} catch (Exception ex) {
			rsCopiarDj = "F"; // Fatal Error
			addErrorMessage("No ha sido posible realizar la copia");
		}
		return null;
	}
	
	public String copiarDjpAnioExist(){
		try{
			if(rsCopiarDj.equals("W")){
				if(currentItem!=null){
					RpDjpredial djpredio = copiarDjpOtroAnioExist(currentItem.getDjId(), anioCrear, getSessionManaged().getUsuarioLogIn().getUsuarioId(), getSessionManaged().getTerminalLogIn());
					if(djpredio!=null){
						rsCopiarDj = "S"; // succesfull
						addInfoMessage("Copia realizada con éxito");
						Integer PredioId=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextPredioId");
						if(PredioId!=null){
							records = registroPrediosBo.getListRpDjpredial(PredioId,findRpDjPredial.getPersonaId());	
						}
					}else{
						rsCopiarDj = "F"; // Fatal Error
						addErrorMessage("No ha sido posible realizar la copia");
					}
				}
			}
		}catch(SisatException ex){
			rsCopiarDj = "F"; // Fatal Error
			addErrorMessage(ex.getMessage());
		}catch(Exception ex){
			rsCopiarDj = "F"; // Fatal Error
			addErrorMessage("No ha sido posible realizar la copia");
		}
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	//caltamirano:ini
	public RpDjpredial copiarDjpOtroAnio(Integer DjIdAnt, int anioCrear, int userId, String terminal) throws SisatException{
		RpDjpredial djpredio = null;
		Integer DjActualizaId=0;
		try{
			//if(anioCrear>=DateUtil.getAnioActual().intValue()){
			//	throw new SisatException("Solamente se permite duplicación para años pasados");
			//}
			if(DjIdAnt!=null&&DjIdAnt>0){
				//satc.dbo.rp_djpredial
				//djpredio=getService().getRpDjpredial(DjIdAnt);
				djpredio=registroPrediosBo.getRpDjpredial(DjIdAnt);
				// Valida que el contribuyente esté afecto.
				if(!registroPrediosBo.estaEnRangoAfecContrib(DjIdAnt, anioCrear)){
					throw new SisatException("El contribuyente no esta afecto para el año que desea hacer la copia");
				}

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
				 if (tramoUso==Constante.PRIMER_TRAMO && anioCrear<2016){
						listaUsosDelPredio=registroPrediosBo.getAllRpDjuso(djArbitrioIdAnterior);
				 }else if (tramoUso==Constante.PRIMER_TRAMO && anioCrear>=2016){
					    listaUsosDelPredio=registroPrediosBo.getAllRpDjusoTramos(djArbitrioIdAnterior);
				 }else if(tramoUso==Constante.SEGUNDO_TRAMO && anioCrear<2016){
					    listaUsosDelPredio=registroPrediosBo.getAllRpDjusoTramos(djArbitrioIdAnterior);
				 }else if(tramoUso==Constante.SEGUNDO_TRAMO && anioCrear>=2016){
					    listaUsosDelPredio=registroPrediosBo.getAllRpDjuso(djArbitrioIdAnterior);
				 }
				}
				ArrayList<RjDocuAnexo> listaDocAnexos=registroPrediosBo.getAllRjDocuAnexo(djpredio.getDjId());
				
				//satc.dbo.rp_djpredial
				djpredio.setDjId(Constante.RESULT_PENDING);
				djpredio.setUsuarioId(userId);
				djpredio.setTerminal(terminal);
				djpredio.setFechaActualizacion(DateUtil.getCurrentDate());
				djpredio.setTerminalRegistro(terminal);
				
				djpredio.setFlagDjAnno(Constante.FLAG_DJ_ANIO_ACTIVO);
				djpredio.setEstado(Constante.ESTADO_ACTIVO);
				//djpredio.setMotivoDeclaracionId(Constante.MOTIVO_DECLARACION_INSCRIPCION);
				if(!djpredio.getMotivoDeclaracionId().equals(Constante.MOTIVO_DECLARACION_MIGRACION)){
					djpredio.setMotivoDeclaracionId(Constante.MOTIVO_DECLARACION_ACTUALIZA);
				}
				djpredio.setAnnoDj(anioCrear);
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
					//actualizacion de Valor de Instalacion
					instalacion.setValorInstalacion(getValorInstalacion(instalacion, anioCrear));					
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
					int rez=registroPrediosBo.guardarRpOtrosFrente(frente);
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
						uso.setAnnoAfectacion(anioCrear);
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
				for(int i=0;i<listaTransferente.size();i++){
					registroPrediosBo.registrarAdquirientes(listaTransferente,DjActualizaId,Constante.TIPO_TRANSFERENCIA_TRANSFERENTE);
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
		}catch(SisatException ex){
			throw ex;
		}catch(Exception ex){
			// TODO: Handle exception
			System.out.println("No se ha podido copiar a otro año: "+ex);
		}
		
		if(djpredio!=null){
			djpredio.setDjId(DjActualizaId);
		}
		
		return djpredio;
	}
	
	public RpDjpredial copiarDjpOtroAnioExist(Integer DjIdAnt, int anioCrear, int userId, String terminal) throws SisatException{
		RpDjpredial djpredio = null;
		Integer DjActualizaId=0;
		try{
			//if(anioCrear>=DateUtil.getAnioActual().intValue()){
			//	throw new SisatException("Solamente se permite duplicaci�n para a�os pasados");
			//}
			if(DjIdAnt!=null&&DjIdAnt>0){
				
				//satc.dbo.rp_djpredial
				djpredio=registroPrediosBo.getRpDjpredial(DjIdAnt);
				
				// get the current record of the year to create
				RpDjpredial djpActivoAnio = registroPrediosBo.getDjpActivoAnio(djpredio.getPersonaId(), djpredio.getPredioId(), anioCrear);
				
				if(djpActivoAnio==null){
					throw new SisatException("No se ha podido obtener el registro activo del año: " + anioCrear + ", necesario para guardar la referencia");
				}
				
				if(djpActivoAnio.getMotivoDeclaracionId()==Constante.MOTIVO_DECLARACION_DESCARGO.intValue()){
					throw new SisatException("El registro activo del año: " + anioCrear + ", está descargado y no puede modificarse desde esta ventana.");
				}
				
				//satc.dbo.rp_djdireccion
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
				 if (tramoUso==Constante.PRIMER_TRAMO && anioCrear<2016){
						listaUsosDelPredio=registroPrediosBo.getAllRpDjuso(djArbitrioIdAnterior);
				 }else if (tramoUso==Constante.PRIMER_TRAMO && anioCrear>=2016){
					    listaUsosDelPredio=registroPrediosBo.getAllRpDjusoTramos(djArbitrioIdAnterior);
				 }else if(tramoUso==Constante.SEGUNDO_TRAMO && anioCrear<2016){
					    listaUsosDelPredio=registroPrediosBo.getAllRpDjusoTramos(djArbitrioIdAnterior);
				 }else if(tramoUso==Constante.SEGUNDO_TRAMO && anioCrear>=2016){
					    listaUsosDelPredio=registroPrediosBo.getAllRpDjuso(djArbitrioIdAnterior);
				 }
				}
				ArrayList<RjDocuAnexo> listaDocAnexos=registroPrediosBo.getAllRjDocuAnexo(djpredio.getDjId());
				
				//satc.dbo.rp_djpredial
				djpredio.setDjId(Constante.RESULT_PENDING);
				djpredio.setUsuarioId(userId);
				djpredio.setTerminal(terminal);
				djpredio.setFechaActualizacion(DateUtil.getCurrentDate());
				djpredio.setTerminalRegistro(terminal);
				
				djpredio.setFlagDjAnno(Constante.FLAG_DJ_ANIO_ACTIVO);
				djpredio.setEstado(Constante.ESTADO_ACTIVO);
				djpredio.setMotivoDeclaracionId(Constante.MOTIVO_DECLARACION_ACTUALIZA);
				djpredio.setAnnoDj(anioCrear);
				djpredio.setIdAnterior(String.valueOf(djpActivoAnio.getDjId()));
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
					//actualizacion de Valor de Instalacion
					instalacion.setValorInstalacion(getValorInstalacion(instalacion, anioCrear));					
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
								
							}     /** De acuerdo al Informe 053-009-00001961, de fecha 04 Abril del 2017, se realizará el cálculo incluyendo depreciación:  (Fin)*/
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
					int rez=registroPrediosBo.guardarRpOtrosFrente(frente);
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
						uso.setAnnoAfectacion(anioCrear);
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
				for(int i=0;i<listaTransferente.size();i++){
					registroPrediosBo.registrarAdquirientes(listaTransferente,DjActualizaId,Constante.TIPO_TRANSFERENCIA_TRANSFERENTE);
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
				
				// Cambiamos el estado
				djpredio.setDjId(DjActualizaId);
				registroPrediosBo.actualizaEstadoRpDjpredial(djpredio, Constante.FLAG_DJ_ANIO_INACTIVO);
			}
		}catch(SisatException ex){
			throw ex;
		}catch(Exception ex){
			String msg = "No se ha podido copiar a otro año:";
			System.out.println(msg+ex);
			throw new SisatException(msg);
		}
		
		if(djpredio!=null){
			djpredio.setDjId(DjActualizaId);
		}
		
		return djpredio;
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
	//caltamirano:fin
	
	public void imprimirDescargo(){
		try{
			if(currentItem!=null){
				PreliminarDescargoPredDTO pre = new PreliminarDescargoPredDTO();
				FindMpPersona contribuyente=getSessionManaged().getContribuyente();
				
				//Datos del Contribuyente
				pre.setPersonaId(contribuyente.getPersonaId());
				pre.setNombreRazonSocial(contribuyente.getApellidosNombres()==null?contribuyente.getRazonSocial():contribuyente.getApellidosNombres());
				pre.setTipoDocumento(contribuyente.getTipoDocumentoIdentidad());
				pre.setNumeroDocumento(contribuyente.getNroDocuIdentidad());
					
				//Datos de la declaración
				pre.setDjId(currentItem.getDjId());
				pre.setFechaDeclaracion(DateUtil.convertDateToString(currentItem.getFechaDeclaracion()));
				pre.setFechaDescargo(DateUtil.convertDateToString(currentItem.getFechaDescargo()));
				pre.setMotivoDeclaracion(currentItem.getMotivoDeclaracion());
				pre.setMotivoDescargo(currentItem.getMotivoDescargo());
				//pre.setPorcentajePropiedad(currentItem.getPorcPropiedad().doubleValue());
				pre.setGlosa(currentItem.getGlosa());
				pre.setSelectedNotaria(currentItem.getNombreNotaria());
				
				//Datos de Area matriz, area transferida porcentaje matriz transferido 
				RpTransferenciaPropiedad rpTransferenciaPropiedad = new RpTransferenciaPropiedad();
				rpTransferenciaPropiedad = registroPrediosBo.getRpTransferenciaPropiedad(currentItem.getDjId());				
				
				
				if(rpTransferenciaPropiedad.getAreaMatriz() != null){
					pre.setArea(rpTransferenciaPropiedad.getAreaRestante().doubleValue());
					pre.setAreaMatriz(rpTransferenciaPropiedad.getAreaMatriz());
					pre.setAreaTransferida(rpTransferenciaPropiedad.getAreaTransferida());
					
					pre.setPorcentajePropiedad(rpTransferenciaPropiedad.getPorcentajeRestante().doubleValue());
					pre.setPorcentajeMatriz(rpTransferenciaPropiedad.getPorcentajeMatriz());
					pre.setPorcentajeTransferido(rpTransferenciaPropiedad.getPorcentajeTransferido());
					pre.setFormaAdquisicion(rpTransferenciaPropiedad.getFormaAdquisicion());
					
				}else{
					if( currentItem.getTipoPredio().equalsIgnoreCase("U")){
						pre.setArea(currentItem.getAreaTerreno().doubleValue());
					}else if( currentItem.getTipoPredio().equalsIgnoreCase("R")){
						pre.setArea(currentItem.getAreaTerrenoHas().doubleValue());
					}
					pre.setFormaAdquisicion("PM");//para los anteriores descargos que no tiene forma de aquisicion se imprimira los dos
					pre.setPorcentajePropiedad(currentItem.getPorcPropiedad().doubleValue());
				}
								
				
				
				//Datos del Predio
				pre.setTipoPredio(currentItem.getTipoPredio());
				pre.setNumeroPredio(String.valueOf(currentItem.getPredioId()));
				pre.setDireccion(currentItem.getDireccionCompleta());
				pre.setCondicionPropiedad(currentItem.getCondicionPropiedad());
				
				//DATOS DE ADQUIRIENTE			
				
				lstTransferentes=registroPrediosBo.getTransferentePropiedadReImpresion(currentItem.getDjId(),Constante.TIPO_TRANSFERENCIA_ADQUIRIENTE);
				
				//DATOS DOCUMENTOS ANEXOS
				lstAnexos=registroPrediosBo.getDocumentosAnexos(currentItem.getDjId());
				
				//usuario
				//pre.setUsuario(getSessionManaged().getUsuarioLogIn().getNombreUsuario());
				pre.setUsuario(currentItem.getUsuario());
				pre.setFechaDeclaracion(currentItem.getFechaDeclaracion().toString());
				
				getSessionMap().put("preliminarDescargoPredDTO", pre);
				getSessionMap().put("lstAdquirientesPred", lstTransferentes);
				getSessionMap().put("lstAnexosDescargoPred", lstAnexos);
				
			}
	
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public BigDecimal getValorInstalacion(RpInstalacionDj instalacion, Integer periodo){
		BigDecimal valorInstalacion=BigDecimal.ZERO;
		try{
			RpTipoObra tipoObra=registroPrediosBo.getRpTipoObra(instalacion.getTipoObraId());
//			if(tipoObra!=null){
//				valorInstalacion=instalacion.getAreaTerreno().multiply(tipoObra.getValorUnitario());
//	 		}
			//SE SACARA EL VALOR DEL TIPO DE OBRA DE LA TABLA TIPO_OBRA_PERIODO 
			if(tipoObra!=null){
				RpTipoObraPeriodo tipoObraPeriodo = new RpTipoObraPeriodo();				
				tipoObraPeriodo = registroPrediosBo.getRpTipoObraPeriodo(instalacion.getTipoObraId(), periodo);
				valorInstalacion=instalacion.getAreaTerreno().multiply(tipoObraPeriodo.getValorUnitario());
	 		}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return valorInstalacion;
	}
	
	public Boolean verificarDjInspeccion(){
		try{
			Integer resultado;
			
			if(currentItem!=null){
				resultado=registroPrediosBo.getReqInspeccionByDj(currentItem.getDjId());
				if (resultado ==0){
					tieneInspeccion=Boolean.FALSE;
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
	
	public void verificarDjInspeccionMsj(){
		try{
			if(currentItem!=null){				
				verificarDjInspeccion();				
				if (tieneInspeccion==false){

					WebMessages.messageError("No es posible editar la DJ porque no tiene un Requerimiento asociado.");
					
						isInspeccion=Boolean.TRUE;
						isEditable=Boolean.FALSE;
				}else{
					    isInspeccion=Boolean.FALSE;
						isEditable=Boolean.TRUE;
						
						fiscalizarDjPendiente();
				}
				
			}else{
				
			}
		}catch(Exception e){
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
	
	public String redireccionaBusqueda(){
		if(findRpDjPredial!=null){
			getSessionMap().put("currentItemPersona2", findRpDjPredial.getPersonaId());
		}
		return sendRedirectPrincipal();
	}
	
	public void guardarDjInspeccionElegida() throws Exception {
		try {
			Integer djId=currentItem.getDjId(); //findRpDjPredial.getDjId();
						//Integer anioDJ = Integer.parseInt(findRpDjPredial.getAnioDj());
						// List<FindInspeccionDj> lstMpInspector = ficalizacionBo.getDeclaracionesInspByPersona(findRpDjPredial.getPersonaId(),findRpDjPredial.getPredioId(),anioDJ);

			 	if (reqNroElegida!=null){
						
						Integer inspeccionIds=reqIdElegida;//lstMpInspector.get(0).getReqInspeccion();
						Integer personaIds=listarequer.get(0).getPersonaId();//lstMpInspector.get(0).getPersonaId();
						
						listaDjInspeccion = ficalizacionBo.getDeclaracionesInspById(inspeccionIds,personaIds,djId);
						inspeccionelegida=ficalizacionBo.getInspeccionByIdAsociada(inspeccionIds);
						
						if (inspeccionelegida.get(0).getTipoDocumentoIdResultado()!=null&&inspeccionelegida.get(0).getTipoDocumentoIdResultado()!=0){

							if (inspeccionelegida.get(0).getTipoDocumentoIdResultado()==Constante.TIPO_DOC_FIP_ID || inspeccionelegida.get(0).getTipoDocumentoIdResultado()==Constante.TIPO_DOC_INF_ID){//lstMpInspector.get(0).getFlagDjAnno()==Constante.TIPO_DOC_FIP_ID
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
								//setTieneInspeccion(true);	
								setEsregistrado(true);
							}
							else{
									  for (FindInspeccionDj i : listaDjInspeccion) {
											ficalizacionBo.guardarDetalleFip(nombreInspector,i.getAnnoDj().toString(), i.getDjId(), i.getPredioId(),
													i.getUbicacionId(),i.getTipoViaId(),i.getViaId(),i.getSectorId(),i.getManzana(),i.getCuadra(),i.getLadoCuadra(),
													fechaInspeccion,inspeccionIds,i.getDireccionPredio(),i.getSector(),i.getVia(),i.getTipoVia(),
													i.getLugar(),nombreInspectorAr,fechaInspeccionAr,getUser().getUsuarioId(), getUser().getTerminal());
										}
										//setTieneInspeccion(true);
										setEsregistrado(true);
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
	
	public void loadReq(ValueChangeEvent event) {
		try {
			HtmlComboBox combo = (HtmlComboBox) event.getComponent();
			String value = combo.getValue().toString();
			if (value != null) {
				reqId = (Integer) mapReq.get(value);
				setCmbReq(value);
				
				setReqId(mapReq.get(value));
				reqIdElegida=getReqId();
//				listarFiscalizador.get(0).setUnidad_id(mapUnidad.get(cmbTipoUnidad));
//				unidadId=listarFiscalizador.get(0).getUnidad_id();
				
//				setReqId(mapReq.get(cmbReq));
//				setInspeccionesId(reqId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public String primeraInscripcion(){
		return "registro";
	}

	public ArrayList<ListRpDjPredial> getRecords() {
		return records;
	}

	public void setRecords(ArrayList<ListRpDjPredial> records) {
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

	public ListRpDjPredial getCurrentItem() {
		return currentItem;
	}

	public void setCurrentItem(ListRpDjPredial currentItem) {
		this.currentItem = currentItem;
	}
	public String getCodigoPredio() {
		return codigoPredio;
	}

	public void setCodigoPredio(String codigoPredio) {
		this.codigoPredio = codigoPredio;
	}
	public FindRpDjPredial getFindRpDjPredial() {
		return findRpDjPredial;
	}
	public void setFindRpDjPredial(FindRpDjPredial findRpDjPredial) {
		this.findRpDjPredial = findRpDjPredial;
	}
	public int getAnioCrear() {
		return anioCrear;
	}
	public void setAnioCrear(int anioCrear) {
		this.anioCrear = anioCrear;
	}
	public String getRsCopiarDj() {
		return rsCopiarDj;
	}
	public List<AnexosDeclaVehicDTO> getLstAnexos() {
		return lstAnexos;
	}
	public void setLstAnexos(List<AnexosDeclaVehicDTO> lstAnexos) {
		this.lstAnexos = lstAnexos;
	}
	public List<BuscarPersonaDTO> getLstTransferentes() {
		return lstTransferentes;
	}
	public void setLstTransferentes(List<BuscarPersonaDTO> lstTransferentes) {
		this.lstTransferentes = lstTransferentes;
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
	public Date getFechaInspeccion() {
		return fechaInspeccion;
	}
	public void setFechaInspeccion(Date fechaInspeccion) {
		this.fechaInspeccion = fechaInspeccion;
	}
	public Integer getInspeccionesId() {
		return inspeccionesId;
	}
	public void setInspeccionesId(Integer inspeccionesId) {
		this.inspeccionesId = inspeccionesId;
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
	public List<FindInspeccionDj> getListarequer() {
		return listarequer;
	}
	public void setListarequer(List<FindInspeccionDj> listarequer) {
		this.listarequer = listarequer;
	}
	public Boolean getEsregistrado() {
		return esregistrado;
	}
	public void setEsregistrado(Boolean esregistrado) {
		this.esregistrado = esregistrado;
	}
	public List<FindInspeccionByIdAsociada> getInspeccionelegida() {
		return inspeccionelegida;
	}
	public void setInspeccionelegida(
			List<FindInspeccionByIdAsociada> inspeccionelegida) {
		this.inspeccionelegida = inspeccionelegida;
	}
	public List<SimpleMenuDTO> getListPermisosSubmenu() {
		return listPermisosSubmenu;
	}
	public void setListPermisosSubmenu(List<SimpleMenuDTO> listPermisosSubmenu) {
		this.listPermisosSubmenu = listPermisosSubmenu;
	}
	public boolean isPermisoImprimiDJ() {
		return permisoImprimiDJ;
	}
	public void setPermisoImprimiDJ(boolean permisoImprimiDJ) {
		this.permisoImprimiDJ = permisoImprimiDJ;
	}
	public boolean isPermisoDescargarDJ() {
		return permisoDescargarDJ;
	}
	public void setPermisoDescargarDJ(boolean permisoDescargarDJ) {
		this.permisoDescargarDJ = permisoDescargarDJ;
	}
	public boolean isPermisoImprimirDescargoDJ() {
		return permisoImprimirDescargoDJ;
	}
	public void setPermisoImprimirDescargoDJ(boolean permisoImprimirDescargoDJ) {
		this.permisoImprimirDescargoDJ = permisoImprimirDescargoDJ;
	}
	public boolean isPermisoVerDJ() {
		return permisoVerDJ;
	}
	public void setPermisoVerDJ(boolean permisoVerDJ) {
		this.permisoVerDJ = permisoVerDJ;
	}
	public boolean isPermisoCopiarDJ() {
		return permisoCopiarDJ;
	}
	public void setPermisoCopiarDJ(boolean permisoCopiarDJ) {
		this.permisoCopiarDJ = permisoCopiarDJ;
	}
	public boolean isPermisoActualizarDJ() {
		return permisoActualizarDJ;
	}
	public void setPermisoActualizarDJ(boolean permisoActualizarDJ) {
		this.permisoActualizarDJ = permisoActualizarDJ;
	}
	
	
}
