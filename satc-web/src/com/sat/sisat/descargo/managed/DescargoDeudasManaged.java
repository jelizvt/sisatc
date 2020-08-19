package com.sat.sisat.descargo.managed;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.swing.event.ListSelectionEvent;

import com.sat.sisat.caja.business.CajaBoRemote;
import com.sat.sisat.caja.dto.DeudaDTO;
import com.sat.sisat.caja.dto.ReciboPagoDetalleDTO;
import com.sat.sisat.caja.dto.ReciboPagoDetallePieDTO;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.FacesUtil;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.controlycobranza.dto.FindDetalleLoteDescargo;
import com.sat.sisat.descargo.business.DescargoBoRemote;
import com.sat.sisat.descargo.dto.ReciboPagoDescargo;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.menus.business.MenuBoRemote;
import com.sat.sisat.menus.dto.SimpleMenuDTO;
import com.sat.sisat.persistence.entity.CdDescargo;
import com.sat.sisat.persistence.entity.GnTipoDocumento;
import com.sat.sisat.persistence.entity.PaPapeleta;
import com.sat.sisat.tramitedocumentario.dto.ItemBandejaEntradaDTO;
import com.sat.sisat.vehicular.dto.BuscarPersonaDTO;

@ManagedBean
@ViewScoped
public class DescargoDeudasManaged extends BaseManaged implements Serializable {

	private static final long serialVersionUID = -1953592327390761744L;

	@EJB	
	private DescargoBoRemote descargoBo;
	
	@EJB
	MenuBoRemote menuBo;
	
	
	private CdDescargo cdDescargo = new CdDescargo();
	
	private List<DeudaDTO> listCtaCte = new ArrayList<DeudaDTO>();
	private List<DeudaDTO> listTempCtaCte = new ArrayList<DeudaDTO>();
	
	private Set<DeudaDTO> idDeudasEliminar;
	private Set<DeudaDTO> tempDeudasSelect;
	
	private DeudaDTO deudaDTOSeleccionada = new DeudaDTO();

	private int personaId;

	private BigDecimal insolutoCancelado = new BigDecimal(0);
	private BigDecimal derechoEmisionCancelado = new BigDecimal(0);
	private BigDecimal totalDeudaCancelada = new BigDecimal(0);
	
	private int deudaId;
	
	private List<SelectItem> lstTipoDocumento;
	
	private HashMap<String, Integer> mapGnTipodocumento = new HashMap<String, Integer>();
	
	private int tipoDescargo;
	private Integer tipoDocumentoId;
	private String nroDocumento;
	private Date fechaDocumento;
	private String observacion;
	
	private BigDecimal totalPrescribir;
	private BigDecimal totalCompensar;
	private BigDecimal totalDescargo;
	private BigDecimal totalRectificar;
	
	private BigDecimal totalInsolutoRectificar;

	
	private String tipoDocumento;
	
	private BigDecimal montoACompensar;
	private BigDecimal montoRectificado;
	
	private String usuario;
	
	private Date fechaCompensacion;
	
	private boolean selectedAllDeu = false;
	
	private List<SelectItem> listAnioDeuda = new ArrayList<SelectItem>();
	private Integer anioDeuda;
	private String cmbAnioDeuda;
	private LinkedHashSet <Integer> listHashSetAnioDeuda = new LinkedHashSet<Integer>();
		
	private List<SelectItem> listDescripcion = new ArrayList<SelectItem>();
	private String descripcion;
	private LinkedHashSet <String> listHashSetDescripcion = new LinkedHashSet<String>();
	
	private List<SelectItem> listSubConcepto  = new ArrayList<SelectItem>();
	private String subconcepto;	
	private LinkedHashSet <String> listHashSetSubConcepto = new LinkedHashSet<String>();
	
	private List<SelectItem> listNroCuota  = new ArrayList<SelectItem>();
	private Integer nroCuota;
	private String cmbNroCuota;
	private LinkedHashSet <Integer> listHashSetNroCuota = new LinkedHashSet<Integer>();
	
	

	// INICIO PERMISOS PARA EL MODULO -=CRAMIREZ=-
		private List<SimpleMenuDTO> listPermisosSubmenu = new ArrayList<SimpleMenuDTO>();
		private boolean permisoCalcularDeuda;
		private boolean permisoFiltrar;
		private boolean permisoDescargo;
		private boolean permisoTransferencia;
		private boolean permisoPrescripciones;
		private boolean permisoCompensaciones;

	// FIN PERMISOS PARA EL MODULO
	
	private int canSolicitudesPrescripcion;
	private List<ItemBandejaEntradaDTO> listItemsExpedientes = new ArrayList<ItemBandejaEntradaDTO>();
	private ItemBandejaEntradaDTO slectExpPrescripcion = new ItemBandejaEntradaDTO();
	private Boolean showListaExpPrescipcion;
	private String expedientesIds;
	
	private Boolean showListaExpCompenzacion;
	private Boolean showListaPagosCompenzacion;
	private int canPagosCompenzacion;
	private String recibosIds;
	private Boolean showSelectRecibo;
	private Boolean showSelectReciboParcial;
	private ReciboPagoDescargo ReciboSelect;
	private BigDecimal montoTotalRecibos;
	
	private List<ReciboPagoDescargo> listpagos = new ArrayList<ReciboPagoDescargo>();
	private List<ReciboPagoDetallePieDTO> listReciboPagoDetalle = new ArrayList<ReciboPagoDetallePieDTO>();
	private List<ReciboPagoDetalleDTO> showlistReciboPagoDetalle = new ArrayList<ReciboPagoDetalleDTO>();
	
	private Integer seleccionarDescargo = 1;
	private Boolean showOptionTipoDescargo;
	
	private Boolean showListaExpTransferencia;
	private Boolean showListaPagosTransferencia;
	private int canPagosTransferencia;
	private BuscarPersonaDTO contribuyente;
	List<DeudaDTO> listCtaCteAEliminar = new ArrayList<DeudaDTO>();
	
	private Boolean showListDeuda ;
	private Boolean showListDeudaSelecionada ;
	private int estadoValidaDocumento = 1;
	
	@PostConstruct
	public void initialize(){
		showListDeuda = false;
		showListDeudaSelecionada =false;
		showSelectRecibo = false;
		canSolicitudesPrescripcion = 0;
		canPagosCompenzacion = 0;
		canPagosTransferencia = 0;
		showSelectReciboParcial = false;
		ReciboSelect = new ReciboPagoDescargo();
		montoTotalRecibos = BigDecimal.ZERO; 
		nroDocumento = "000-000-00000000-2019";
		tempDeudasSelect = new HashSet<DeudaDTO>();
		permisosMenu();
		try {
			personaId = getSessionManaged().getContribuyente().getPersonaId();
			if (personaId > 0) {
				//listCtaCte = descargoBo.obtenerTodaDeuda(personaId);
			}
			
			this.getFiltros();
			
			idDeudasEliminar = new HashSet<DeudaDTO>();			
			
			List<GnTipoDocumento> list = descargoBo.obtenerTipoDocumentosDescargo( getSessionManaged().getUsuarioLogIn().getUsuarioId() );
			lstTipoDocumento = new ArrayList<SelectItem>();			

			for (GnTipoDocumento it : list) {
				lstTipoDocumento.add(new SelectItem(it.getDescripcion(), String.valueOf(it.getTipoDocumentoId())));
				mapGnTipodocumento.put(it.getDescripcion(), it.getTipoDocumentoId());
			}
			usuario = getSessionManaged().getUsuarioLogIn().getNombreUsuario();
			fechaCompensacion = DateUtil.getCurrentDate();
			fechaDocumento  = DateUtil.getCurrentDate();
			
		} catch (SisatException ex) {
			addErrorMessage(ex.getMessage());
		} catch (Exception ex) {
			String msg = "No se ha podido cargar la cuenta corriente";
			System.out.println(msg + ex);
			addErrorMessage(msg);
		}
	}
	
	public void getFiltros(){
		if(listCtaCte != null && listCtaCte.size()>0){				
			for(DeudaDTO deuda: listCtaCte){
				listHashSetAnioDeuda.add(deuda.getAnioDeuda());	
				listHashSetDescripcion.add(deuda.getDescripcion());
				listHashSetSubConcepto.add(deuda.getSubconcepto());
				listHashSetNroCuota.add(deuda.getNumCuota());
				
				// ===========================
				if(tempDeudasSelect != null && tempDeudasSelect.size()>0) {
					for(DeudaDTO temp: tempDeudasSelect){
						if(temp.getDeudaId() == deuda.getDeudaId()) {
							deuda.setSelected(true);
						}
					}
				}
				// ===========================
				
			}
			listAnioDeuda = new ArrayList<SelectItem>();
			listDescripcion = new ArrayList<SelectItem>();
			listSubConcepto = new ArrayList<SelectItem>();
			listNroCuota = new ArrayList<SelectItem>();
					
			listAnioDeuda.add(new SelectItem("Todo"));
			listDescripcion.add(new SelectItem("Todo"));
			listSubConcepto.add(new SelectItem("Todo"));
			listNroCuota.add(new SelectItem("Todo"));
			
			for(Integer anio: listHashSetAnioDeuda){					
				listAnioDeuda.add(new SelectItem(String.valueOf(anio)));
			}			
			for(String descripcion : listHashSetDescripcion){
				listDescripcion.add(new SelectItem(descripcion));
			}
			for(String subconcepto : listHashSetSubConcepto){
				listSubConcepto.add(new SelectItem(subconcepto));
			}
			for(Integer nroCuota : listHashSetNroCuota){
				listNroCuota.add(new SelectItem(String.valueOf(nroCuota)));
			}
			
			
		}
	}
	
	public void permisosMenu() {	
		try {
			int submenuId = Constante.DESCARGO_DE_DEUDAS;
			
			int permisoCalcularDeudaId = Constante.CARCULAR_DEUDA;
			int permisoFiltrarId = Constante.FILTRAR;
			int permisoDescargoId = Constante.DESCARGO;
			int permisoTransferenciaId = Constante.TRASFERENCIA;
			int permisoPrescripcionesId = Constante.PRESCRIPCIONES;
			int permisoCompensacionesId = Constante.COMPENSACIONES;;
			
			permisoCalcularDeuda = false;
			permisoFiltrar = false;
			permisoDescargo = false;
			permisoTransferencia = false;
			permisoPrescripciones = false;
			permisoCompensaciones = false;
			
			listPermisosSubmenu  = menuBo.getAccesosSubmenuUsuario( getSessionManaged().getUsuarioLogIn().getUsuarioId() , submenuId);
			
			Iterator<SimpleMenuDTO> menuIterar = listPermisosSubmenu.iterator();
			while (menuIterar.hasNext()) {
				SimpleMenuDTO lsm = menuIterar.next();
				if(lsm.getItemId() == permisoCalcularDeudaId) {
					permisoCalcularDeuda = true;
				}
				if(lsm.getItemId() == permisoFiltrarId) {
					permisoFiltrar = true;
				}
				if(lsm.getItemId() == permisoDescargoId) {
					permisoDescargo = true;
				}
				if(lsm.getItemId() == permisoTransferenciaId) {
					permisoTransferencia = true;
				}
				if(lsm.getItemId() == permisoPrescripcionesId) {
					permisoPrescripciones = true;
				}
				if(lsm.getItemId() == permisoCompensacionesId) {
					permisoCompensaciones = true;
				}
			}
			
		} catch (SisatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void calcularDeuda(){
		try {
			// ============================
			showListDeuda = true;
			showListDeudaSelecionada = false;
			
			cmbAnioDeuda = null;
			descripcion = null;
			cmbNroCuota = null;
			subconcepto = null;
			// tempDeudasSelect = new HashSet<DeudaDTO>();
			for (DeudaDTO de : listCtaCte) {
				if (de.isSelected()) {
					tempDeudasSelect.add(de);
				}else {
					for (DeudaDTO select : tempDeudasSelect) {
						if(de.getDeudaId() == select.getDeudaId() ) {
							tempDeudasSelect.remove(select);
						}
					}
				}
			}			
			// ============================
			
			personaId = getSessionManaged().getContribuyente().getPersonaId();
			if (personaId > 0) {				
				listCtaCte = descargoBo.obtenerTodaDeudaConFecha(personaId, fechaCompensacion);	
				listTempCtaCte = new ArrayList<DeudaDTO>();
				listTempCtaCte.addAll(listCtaCte);
			}
			
			this.getFiltros();
//			idDeudasEliminar = new HashSet<DeudaDTO>();			
//			
//			List<GnTipoDocumento> list = descargoBo.obtenerTipoDocumentosDescargo();
//			lstTipoDocumento = new ArrayList<SelectItem>();			
//
//			for (GnTipoDocumento it : list) {
//				lstTipoDocumento.add(new SelectItem(it.getDescripcion(), String.valueOf(it.getTipoDocumentoId())));
//				mapGnTipodocumento.put(it.getDescripcion(), it.getTipoDocumentoId());
//			}
//			usuario = getSessionManaged().getUsuarioLogIn().getNombreUsuario();
			
		} catch (SisatException ex) {
			addErrorMessage(ex.getMessage());
		} catch (Exception ex) {
			String msg = "No se ha podido cargar la cuenta corriente con fecha de compensacion";
			System.out.println(msg + ex);
			addErrorMessage(msg);
		}
	}
	
	public void filtrar(){
		showListDeudaSelecionada = false;
		try {
			for (DeudaDTO de : listCtaCte) {
				if (de.isSelected()) {
					tempDeudasSelect.add(de);
				}else {
					for (DeudaDTO select : tempDeudasSelect) {
						if(de.getDeudaId() == select.getDeudaId() ) {
							tempDeudasSelect.remove(select);
						}
					}
				}
			}	
			
			if (personaId > 0) {
				//listTempCtaCte
				//listCtaCte = new ArrayList<DeudaDTO>();				
				//listCtaCte = descargoBo.obtenerTodaDeudaConFiltro(personaId, fechaCompensacion, anioDeuda, null, nroCuota );				
			}	
			
			if(listTempCtaCte != null && listTempCtaCte.size()>0){			
				List<DeudaDTO> listCtaCteFiltro = new ArrayList<DeudaDTO>();
				
				if((descripcion != null && !descripcion.isEmpty() && descripcion.compareToIgnoreCase("Todo")!=0)
					|| (subconcepto != null && !subconcepto.isEmpty() && subconcepto.compareToIgnoreCase("Todo")!=0)
					|| (anioDeuda != null && anioDeuda != 0 )
					|| (nroCuota != null && nroCuota != 0 )	) {
					
					if(anioDeuda != null && anioDeuda != 0 ) {
						if(subconcepto != null && !subconcepto.isEmpty() && subconcepto.compareToIgnoreCase("Todo")!=0) {
							if(descripcion != null && !descripcion.isEmpty() && descripcion.compareToIgnoreCase("Todo")!=0) {
								if(nroCuota != null && nroCuota != 0 ) {
									for(DeudaDTO deuda : listTempCtaCte){										
										if(deuda.getDescripcion().compareToIgnoreCase(descripcion) == 0 
												&& deuda.getSubconcepto().compareToIgnoreCase(subconcepto) == 0
												&& deuda.getAnioDeuda() == anioDeuda
												&& deuda.getNumCuota() == nroCuota){
											listCtaCteFiltro.add(deuda);
										}
									}
								}else {
									for(DeudaDTO deuda : listTempCtaCte){										
										if(deuda.getDescripcion().compareToIgnoreCase(descripcion) == 0 
												&& deuda.getSubconcepto().compareToIgnoreCase(subconcepto) == 0
												&& deuda.getAnioDeuda() == anioDeuda){
											listCtaCteFiltro.add(deuda);
										}
									}
								}
							}
							else {
								if(nroCuota != null && nroCuota != 0 ) {
									for(DeudaDTO deuda : listTempCtaCte){										
										if( deuda.getSubconcepto().compareToIgnoreCase(subconcepto) == 0
												&& deuda.getAnioDeuda() == anioDeuda
												&& deuda.getNumCuota() == nroCuota){
											listCtaCteFiltro.add(deuda);
										}
									}
								}else {
									for(DeudaDTO deuda : listTempCtaCte){										
										if( deuda.getSubconcepto().compareToIgnoreCase(subconcepto) == 0
												&& deuda.getAnioDeuda() == anioDeuda){
											listCtaCteFiltro.add(deuda);
										}
									}
								}
							}
						}else {
							if(descripcion != null && !descripcion.isEmpty() && descripcion.compareToIgnoreCase("Todo")!=0) {
								if(nroCuota != null && nroCuota != 0 ) {
									for(DeudaDTO deuda : listTempCtaCte){										
										if( deuda.getDescripcion().compareToIgnoreCase(descripcion) == 0 
												&& deuda.getAnioDeuda() == anioDeuda
												&& deuda.getNumCuota() == nroCuota){
											listCtaCteFiltro.add(deuda);
										}
									}
								}else {
									for(DeudaDTO deuda : listTempCtaCte){										
										if( deuda.getDescripcion().compareToIgnoreCase(descripcion) == 0 
												&& deuda.getAnioDeuda() == anioDeuda){
											listCtaCteFiltro.add(deuda);
										}
									}
								}
							}else {
								if(nroCuota != null && nroCuota != 0 ) {
									for(DeudaDTO deuda : listTempCtaCte){										
										if(  deuda.getAnioDeuda() == anioDeuda
												&& deuda.getNumCuota() == nroCuota){
											listCtaCteFiltro.add(deuda);
										}
									}
								}else {
									for(DeudaDTO deuda : listTempCtaCte){										
										if( deuda.getAnioDeuda() == anioDeuda){
											listCtaCteFiltro.add(deuda);
										}
									}
								}
							}
						}
					}else {
						if(subconcepto != null && !subconcepto.isEmpty() && subconcepto.compareToIgnoreCase("Todo")!=0) {
							if(descripcion != null && !descripcion.isEmpty() && descripcion.compareToIgnoreCase("Todo")!=0) {
								if(nroCuota != null && nroCuota != 0 ) {
									for(DeudaDTO deuda : listTempCtaCte){										
										if(deuda.getDescripcion().compareToIgnoreCase(descripcion) == 0 
												&& deuda.getSubconcepto().compareToIgnoreCase(subconcepto) == 0
												&& deuda.getNumCuota() == nroCuota){
											listCtaCteFiltro.add(deuda);
										}
									}
								}else {
									for(DeudaDTO deuda : listTempCtaCte){										
										if(deuda.getDescripcion().compareToIgnoreCase(descripcion) == 0 
												&& deuda.getSubconcepto().compareToIgnoreCase(subconcepto) == 0){
											listCtaCteFiltro.add(deuda);
										}
									}
								}
							}
							else {
								if(nroCuota != null && nroCuota != 0 ) {
									for(DeudaDTO deuda : listTempCtaCte){										
										if( deuda.getSubconcepto().compareToIgnoreCase(subconcepto) == 0
												&& deuda.getNumCuota() == nroCuota){
											listCtaCteFiltro.add(deuda);
										}
									}
								}else {
									for(DeudaDTO deuda : listTempCtaCte){										
										if( deuda.getSubconcepto().compareToIgnoreCase(subconcepto) == 0){
											listCtaCteFiltro.add(deuda);
										}
									}
								}
							}
						}else {
							if(descripcion != null && !descripcion.isEmpty() && descripcion.compareToIgnoreCase("Todo")!=0) {
								if(nroCuota != null && nroCuota != 0 ) {
									for(DeudaDTO deuda : listTempCtaCte){										
										if( deuda.getDescripcion().compareToIgnoreCase(descripcion) == 0 
												&& deuda.getNumCuota() == nroCuota){
											listCtaCteFiltro.add(deuda);
										}
									}
								}else {
									for(DeudaDTO deuda : listTempCtaCte){										
										if( deuda.getDescripcion().compareToIgnoreCase(descripcion) == 0){
											listCtaCteFiltro.add(deuda);
										}
									}
								}
							}else {
								if(nroCuota != null && nroCuota != 0 ) {
									for(DeudaDTO deuda : listTempCtaCte){										
										if( deuda.getNumCuota() == nroCuota){
											listCtaCteFiltro.add(deuda);
										}
									}
								}
							}
						}
					}
					
				}else {
					listCtaCteFiltro.addAll(listTempCtaCte);
				}
				
				
				if(listCtaCteFiltro != null && listCtaCteFiltro.size()>0){
					listCtaCte = new ArrayList<DeudaDTO>();
					listCtaCte.addAll(listCtaCteFiltro);
				}				
			}			
			
			
		} catch (Exception ex) {
			String msg = "No se ha podido cargar la cuenta corriente con filtros";
			System.out.println(msg + ex);
			addErrorMessage(msg);
		}
		
		
		
		try {
			idDeudasEliminar = new HashSet<DeudaDTO>();	
			List<GnTipoDocumento> list;
			list = descargoBo.obtenerTipoDocumentosDescargo(getSessionManaged().getUsuarioLogIn().getUsuarioId());
			lstTipoDocumento = new ArrayList<SelectItem>();			

			for (GnTipoDocumento it : list) {
				lstTipoDocumento.add(new SelectItem(it.getDescripcion(), String.valueOf(it.getTipoDocumentoId())));
				mapGnTipodocumento.put(it.getDescripcion(), it.getTipoDocumentoId());
			}
			usuario = getSessionManaged().getUsuarioLogIn().getNombreUsuario();	
		} catch (SisatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
	}
	
	public void limpiarFiltros() {
		try {
			cmbAnioDeuda = null;
			descripcion = null;
			cmbNroCuota = null;
			subconcepto = null;
			if (personaId > 0) {
				//listCtaCte = descargoBo.obtenerTodaDeudaConFecha(personaId,	fechaCompensacion);
				
				if(listTempCtaCte != null && listTempCtaCte.size()>0){				
					for(DeudaDTO de: listTempCtaCte){
						de.setSelected(false);
					}
					
					listCtaCte = new ArrayList<DeudaDTO>();
					listCtaCte.addAll(listTempCtaCte);
				}
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void limpiarSelecionados(){
		List<DeudaDTO> eliminar = new ArrayList<DeudaDTO>();
		List<DeudaDTO> temporal = new ArrayList<DeudaDTO>();		
		
		if(listCtaCte != null && listCtaCte.size()>0){
			for (DeudaDTO de : listCtaCte) {
				if(de.isSelected()) {		
					tempDeudasSelect.add(de);
				}else {
					for (DeudaDTO select : tempDeudasSelect) {
						if(de.getDeudaId() == select.getDeudaId() ) {	
							eliminar.add(de);
						}
					}
				}
			}
		}		
		
		Boolean bandera = false;
		if(tempDeudasSelect != null && tempDeudasSelect.size()>0) {
			for (DeudaDTO select : tempDeudasSelect) {
				bandera = false;
				for(DeudaDTO d : eliminar) {
					if(select.getDeudaId() == d.getDeudaId()) {
						bandera = true;
					}
				}
				if(!bandera) {
					temporal.add(select);
				}	
			}
		}
		tempDeudasSelect = new HashSet<DeudaDTO>();
		tempDeudasSelect.addAll(temporal);
	}
	
	public void verDeudasSelecionadas(){
		limpiarSelecionados();
		
		if(tempDeudasSelect != null && tempDeudasSelect.size()>0) {
		}else {
			WebMessages.messageError("No existen deudas seleccionadas");
			return;
		}
		// ========================================
		if(listTempCtaCte != null && listTempCtaCte.size()>0){	
			List<DeudaDTO> listC = new ArrayList<DeudaDTO>();
			
			for(DeudaDTO deuda: listTempCtaCte){
				if(tempDeudasSelect != null && tempDeudasSelect.size()>0) {
					for(DeudaDTO temp: tempDeudasSelect){
						if(temp.getDeudaId() == deuda.getDeudaId()) {
							deuda.setSelected(true);
							listC.add(deuda);
						}
					}
				}
			}
			
			if(listC != null && listC.size()>0){
				showListDeudaSelecionada = true;
				
				listCtaCte = new ArrayList<DeudaDTO>();
				listCtaCte.addAll(listC);
			}
		}
		
	}
	
	public void cancelDeudasSelecionadas(){
		System.out.println("cancelDeudasSelecionadas");
		limpiarSelecionados();
		showListDeudaSelecionada = false;
		
		cmbAnioDeuda = null;
		descripcion = null;
		cmbNroCuota = null;
		subconcepto = null;
		
		listCtaCte = new ArrayList<DeudaDTO>();
		listCtaCte.addAll(listTempCtaCte);
	}
	public void inicioDescargo(){
		this.recibosIds = "";
		limpiar();
		totalDescargo= new BigDecimal(0);
		for (DeudaDTO de : listTempCtaCte) {
			if (de.isSelected()) {
				totalDescargo=de.getTotalDeuda().add(totalDescargo);
			}
		}
		if(totalDescargo.compareTo(new BigDecimal(0)) == 0){
			WebMessages.messageError("Seleccione las deudas a descargar");
		}
	}

	
	public void inicioPrescricion(){
		this.recibosIds = "";
		canSolicitudesPrescripcion = 0;
		showListaExpPrescipcion = true;
		
		
		limpiar();
		totalPrescribir = new BigDecimal(0);
		
		Boolean flagAnioIncorrecto = false;
		for (DeudaDTO de : listTempCtaCte) {
			if (de.isSelected()) {
				totalPrescribir=de.getTotalDeuda().add(totalPrescribir);
				
				Calendar cal= Calendar.getInstance();
				int year= cal.get(Calendar.YEAR);
				
				if( (year - de.getAnioDeuda()) <= Constante.ANTIGUEDAD_DEUDA_PRESCRIBIR) {
					flagAnioIncorrecto = true;
				}
			}			
		}		
		
		if(totalPrescribir.compareTo(new BigDecimal(0)) == 0){
			WebMessages.messageError("Seleccione las deudas a prescribir");
			return;
		}
		
		if (flagAnioIncorrecto) {
			addErrorMessage("Por favor, seleccione deudas con más de "+Constante.ANTIGUEDAD_DEUDA_PRESCRIBIR+ " años de antigüedad.");
			return;
		}
		
		//================= VALIDAR SI EXISTE EXPEDIENTE DE PRESCRIPCION ====================
			try {
				listItemsExpedientes = descargoBo.obtenerExpedientesContibuyente(this.personaId,Constante.TIPOS_TRAMITES_PRESCRIBIR);
				
				canSolicitudesPrescripcion = listItemsExpedientes.size() ;
				
				Iterator<ItemBandejaEntradaDTO> iterar = listItemsExpedientes.iterator();
				if(canSolicitudesPrescripcion > 1) {
					while (iterar.hasNext()) {					
						ItemBandejaEntradaDTO lsm = iterar.next();
						lsm.setSelect(false);					
					}
				}else {
					while (iterar.hasNext()) {					
						ItemBandejaEntradaDTO lsm = iterar.next();
						lsm.setSelect(true);
						if(lsm.getFlagDescargo() == 1) {
							WebMessages.messageError("El expediente ya se encuentra en uso.");
						}
					}
				}
				
				if(canSolicitudesPrescripcion == 1) {
					showListaExpPrescipcion = false;
					//changeExpPrescripcion(listItemsExpedientes.get(0));
				}
				
				if(canSolicitudesPrescripcion <=0 ){
					WebMessages.messageError("El contribuyente no tiene registrado ninguna solicitud de prescripción.");
				}
			} catch (SisatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		// ==================================================================================
	}
	
	public void changeExpPrescripcion() {
		this.expedientesIds = "";
		this.showListaExpPrescipcion =true;
		Boolean bandera = false;
		
		for (ItemBandejaEntradaDTO lsm : listItemsExpedientes) {
			if (lsm.getSelect()) {
				this.expedientesIds = this.expedientesIds + lsm.getExpedienteId() + ",";
				bandera = true;
			}
		}
		
		if(!bandera) {
			addErrorMessage("Seleccione almenos un expediente.");
			return;
		}else {
			this.showListaExpPrescipcion =false;
		}
		
	}
	
	public void cancelExpediente() {
		this.showListaExpPrescipcion =true;
	}
	
	public void prescribir() {
		//System.out.println("Eliminacion de deudas por PRESCRIPCION");
		if(estadoValidaDocumento == 1) {
			WebMessages.messageError("El número de documento ya está registrado");
			return;
		}
		
		List<DeudaDTO> listCtaCteAEliminar = new ArrayList<DeudaDTO>();
		
		totalDescargo = new BigDecimal(0);
		Boolean flagAnioIncorrecto = false;
		
		for (DeudaDTO de : listTempCtaCte) {
			if (de.isSelected()) {
				totalDescargo=de.getTotalDeuda().add(totalDescargo);
				idDeudasEliminar.add(de);
				listCtaCteAEliminar.add(de);
				
				Calendar cal= Calendar.getInstance();
				int year= cal.get(Calendar.YEAR);
				if( (year - de.getAnioDeuda()) <= Constante.ANTIGUEDAD_DEUDA_PRESCRIBIR) {
					flagAnioIncorrecto = true;
				}
				
			}
		}
		
		if (flagAnioIncorrecto) {
			addErrorMessage("Por favor, seleccione deudas con más de "+Constante.ANTIGUEDAD_DEUDA_PRESCRIBIR+ " años de antigüedad.");
			return;
		}
		
		if (showListaExpPrescipcion) {
			addErrorMessage("Por favor, seleccione un expediente.");
			return;
		}
		
		if (!idDeudasEliminar.isEmpty()) {
			
			//funcion a eliminar
			//System.out.println("POR PRESCRIPCION se eliminará de la bd de deudas ");
			
			try {
				//Eliminar Deudas y agregar registro
				cdDescargo.setTipoDocumentoId(tipoDocumentoId);
				cdDescargo.setNroDocumento(nroDocumento);
				cdDescargo.setFechaDocumento(fechaDocumento);	
				//cdDescargo.setObservacion("Servidor :"+usuario+". Total Prescrito: "+totalDescargo+". Obsv: "+observacion);
				cdDescargo.setObservacion(("Obsv: ").concat(observacion).concat(". Servidor :").concat(usuario).concat(". Total Prescrito: ").concat(totalDescargo.toString()));
				cdDescargo.setTipoDescargo(Constante.TIPO_DESCARGO_PRESCRIPCION);
				cdDescargo.setEstado(Constante.ESTADO_PRESCRITO);
				cdDescargo.setFechaRegistro(DateUtil.getCurrentDate());
				cdDescargo.setTotalDescargado(totalDescargo);
				
				// INICIO MODIFICACIONES nuevo descargo -=CRAMIREZ=-
				cdDescargo.setPersonaId(getSessionManaged().getContribuyente().getPersonaId());
				cdDescargo.setUsuarioId(getSessionManaged().getUsuarioLogIn().getUsuarioId());
				cdDescargo.setTerminal(getSessionManaged().getTerminalLogIn());
				
				descargoBo.descargarDeudas(cdDescargo, listCtaCteAEliminar,fechaCompensacion,0, this.expedientesIds, this.recibosIds, this.listpagos,1);
				// FIN MODIFICACIONES nuevo descargo -=CRAMIREZ=-
				//descargoBo.descargarDeudas(cdDescargo, listCtaCteAEliminar);
				
				//listCtaCte = descargoBo.obtenerTodaDeuda(personaId);
				listCtaCte = descargoBo.obtenerTodaDeudaConFecha(personaId, fechaCompensacion);	
				listTempCtaCte = new ArrayList<DeudaDTO>();
				listTempCtaCte.addAll(listCtaCte);
				
				idDeudasEliminar.clear();
				limpiarFiltros();
				
				////addInfoMessage("La operación ha sido realizada con éxito");
			} catch (SisatException e) {
				addErrorMessage(e.getMessage());				
			}
		}
		
	}
	
	public void inicioCompensacion(){
		this.recibosIds = "";
		showListaExpCompenzacion = true;
		showListaExpTransferencia = false;
		showListaPagosCompenzacion = true;
		canPagosCompenzacion = 0 ;
		montoTotalRecibos = BigDecimal.ZERO;
		showSelectReciboParcial = false;
		showSelectRecibo = false;
		seleccionarDescargo = 0 ;
		listpagos = new ArrayList<ReciboPagoDescargo>();
		
		limpiar();
		totalCompensar = new BigDecimal(0);
		for (DeudaDTO de : listTempCtaCte) {
			if (de.isSelected()) {
				totalCompensar=de.getTotalDeuda().add(totalCompensar);				
			}
		}
		if(totalCompensar.compareTo(new BigDecimal(0)) == 0){
			WebMessages.messageError("Seleccione las deudas a compensar");
		}
		
		//================= VALIDAR SI EXISTE EXPEDIENTE COMPENSACION ====================
		this.buscarRecibosContribuyente(this.personaId);
		
		try {
			listItemsExpedientes = descargoBo.obtenerExpedientesContibuyente(this.personaId,Constante.TIPOS_TRAMITES_COMPENZACION);

			Iterator<ItemBandejaEntradaDTO> iterar = listItemsExpedientes.iterator();
			
			showOptionTipoDescargo =false; 
			while (iterar.hasNext()) {					
				ItemBandejaEntradaDTO lsm = iterar.next();
				lsm.setSelect(false);	
				if(lsm.getFlagDescargo() == 0){
					showOptionTipoDescargo = true;
				}
			}
			if(!showOptionTipoDescargo){
				setSeleccionarDescargo(2);
			}
			
			
		} catch (SisatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	// ==================================================================================
	}
	
	
	public void changePagosCompensacion() {
		this.recibosIds = "";
		this.showListaPagosCompenzacion =true;
		showListaPagosTransferencia = true;
		Boolean bandera = false;
		for (ReciboPagoDescargo lp : listpagos) {			
			if (lp.getSelect()) {				
				this.recibosIds = this.recibosIds + lp.getReciboId() + ",";
				bandera = true;
			}
		}
		if(this.recibosIds != "") {
			this.recibosIds= this.recibosIds.substring(0,(this.recibosIds.length() - 1) );
		}
		
		if(!bandera) {
			addErrorMessage("Seleccione un Recibo.");
			return;
		}else {
			this.showListaPagosCompenzacion =false;
			this.showListaPagosTransferencia =false;
		}
	}
	
	public void selectReciboPagosCompensacion(ReciboPagoDescargo recibo){
		if(this.showSelectRecibo) {
			if(this.ReciboSelect.getReciboId() != recibo.getReciboId()) {
				recibo.setSelect(false);
			}else {
				recibo.setSelect(true);
			}
			addErrorMessage("Actualmente hay un recibo seleccionado");
			
			return;
		}else {
			recibo.setSelectAll(false);
			recibo.setIdDeudasSelect("");
			
			if(recibo.getSelect()) {	
				this.ReciboSelect = recibo;
				this.showSelectRecibo = true;
				
				if(this.ReciboSelect.getEstado().equals("3") || this.ReciboSelect.getEstado().equals("10")) {
					this.selectReciboPagosNo();
				}
			}else {
				recibo.setSelectAll(false);
				recibo.setIdDeudasSelect("");				
				
				this.showSelectReciboParcial = false;		
				this.showSelectRecibo = false;
				
				montoTotalRecibos = montoTotalRecibos.subtract(recibo.getSelectTotal());
				recibo.setSelectTotal(BigDecimal.ZERO);
			}
			
		}
	}
	
	public void selectReciboPagosSi(){
		long f1 = fechaCompensacion.getTime();
	    long f2 = this.ReciboSelect.getFechaRecibo().getTime();
	    
		if(f2 > f1) {
			WebMessages.messageError("Seleccione recibos menores a la fecha de compensación");
			selectReciboPagosCancel();
			return;
		}
		
		this.ReciboSelect.setSelectAll(true);
		this.ReciboSelect.setIdDeudasSelect("");
		this.ReciboSelect.setSelectTotal(this.ReciboSelect.getMontoACobrar());
		
		montoTotalRecibos = montoTotalRecibos.add(this.ReciboSelect.getMontoACobrar());
		
		showSelectReciboParcial = false;		
		this.showSelectRecibo = false;

	}
	
	public void selectReciboPagosNo(){	
		long f1 = fechaCompensacion.getTime();
	    long f2 = this.ReciboSelect.getFechaRecibo().getTime();
	    
	    System.out.println(f1);
	    System.out.println(f2);
		if(f2 > f1) {
			WebMessages.messageError("Seleccione recibos menores a la fecha de compensación");
			selectReciboPagosCancel();
			return;
		}
		
		this.showlistReciboPagoDetalle = new ArrayList<ReciboPagoDetalleDTO>();
		//this.showSelectRecibo = false;	
		showSelectReciboParcial = true;
		this.ReciboSelect.setSelectTotal(BigDecimal.ZERO);
		
		try {
			showlistReciboPagoDetalle = descargoBo.obtenerDeudasRecibo(ReciboSelect.getReciboId());
			
			for (ReciboPagoDetalleDTO lsm : showlistReciboPagoDetalle) {
				lsm.setSelect(false);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void selectReciboPagosCancel() {
		this.ReciboSelect.setSelect(false);
		this.ReciboSelect.setSelectAll(false);
		this.ReciboSelect.setIdDeudasSelect("");
		this.ReciboSelect.setSelectTotal(BigDecimal.ZERO);
		
		showSelectReciboParcial = false;		
		this.showSelectRecibo = false;
	}
	
	
	
	public void seletItemRecibo(ReciboPagoDetalleDTO item) {
		if(item.getSelect()) {
			BigDecimal suma = this.ReciboSelect.getSelectTotal();
			BigDecimal valor = item.getTotal();			
			suma = suma.add(valor);
			
			this.ReciboSelect.setSelectTotal(suma);
		}else {
			BigDecimal suma = this.ReciboSelect.getSelectTotal();
			BigDecimal valor = item.getTotal();
			suma = suma.subtract(valor);
			
			this.ReciboSelect.setSelectTotal(suma);
		}
	}
	
	public void confirmarReciboParcial(){		
		if (this.ReciboSelect.getSelectTotal().compareTo(BigDecimal.ZERO) ==  0) {
			addWarnMessage("Por favor, seleccione una deuda al menos");
			return;
		}
		
		this.ReciboSelect.setSelectAll(false);
		
		String labelRecibosId = "";
		
		for (ReciboPagoDetalleDTO lsm : showlistReciboPagoDetalle) {
			if (lsm.getSelect()) {
				labelRecibosId = labelRecibosId + lsm.getDeudaId() + ",";
			}
		}
		
		this.ReciboSelect.setIdDeudasSelect(labelRecibosId);
		
		montoTotalRecibos = montoTotalRecibos.add(this.ReciboSelect.getSelectTotal());
		
		this.showSelectReciboParcial = false;		
		this.showSelectRecibo = false;
	}
	
	public void cancelarReciboParcial(){
		this.ReciboSelect.setSelectAll(false);
		this.ReciboSelect.setIdDeudasSelect("");
		this.ReciboSelect.setSelect(false);
		
		this.showSelectReciboParcial = false;		
		this.showSelectRecibo = false;
	}
	
	public void cancelpagos() {		
		this.showListaPagosCompenzacion = true;
		this.showListaPagosTransferencia = true;
	}
	
	public void validarMontoCompenzar(ValueChangeEvent event){
		BigDecimal cmbValueSelect = (BigDecimal) event.getNewValue();
		
		
	}
	
	public void validaCompensacionTransferencia(){
		//INICIO SELECCION DE EXPEDIENTES
			this.expedientesIds = "";
			Boolean banderaExp = false;
			for (ItemBandejaEntradaDTO lsm : listItemsExpedientes) {
				if (lsm.getSelect()) {
					banderaExp = true;
					this.expedientesIds = this.expedientesIds + lsm.getExpedienteId() + ",";
				}
			} 
			
			if(!banderaExp &&  seleccionarDescargo == 1) {
				addErrorMessage("Por favor, seleccione un expediente..");
				return;
			}
		//FIN SELECCION DE EXPEDIENTES
		
		//	System.out.println("Modificacion de deudas por COMPENSACION");
			
			listCtaCteAEliminar = new ArrayList<DeudaDTO>();
			
			totalDescargo = new BigDecimal(0);
			BigDecimal interes = BigDecimal.ZERO;
			
			if (this.montoACompensar == null || this.montoACompensar.equals("0.00")
					|| this.montoACompensar.doubleValue() <= 0) {
				addErrorMessage("Por favor, ingrese el monto");
				return;
			}
			
			int res;
			res = montoACompensar.compareTo(montoTotalRecibos); // compare bg1 with bg2

			if (res == 1 ) {
				montoACompensar = montoTotalRecibos;
				addErrorMessage("Por favor, un mayor menor o igual al monto seleccionado en los recibos.");
				return;
			}
			
			for (DeudaDTO de : listTempCtaCte) {
				if (de.isSelected()) {
					totalDescargo=de.getTotalDeuda().add(totalDescargo);
					idDeudasEliminar.add(de);
					listCtaCteAEliminar.add(de);
					interes = interes.add(de.getInteres());
				}
			}	
			
			if (interes.compareTo(montoACompensar) == 1) {
				addWarnMessage("El monto a compensar debe cubrir por lo menos los intereses");
				return;
			}
	}
	
	public void compensar(){
		if(estadoValidaDocumento == 1) {
			WebMessages.messageError("El número de documento ya está registrado");
			return;
		}
		
		validaCompensacionTransferencia();
		if (!idDeudasEliminar.isEmpty()) {
			try {
				//Eliminar Deudas y agregar registro
				cdDescargo.setTipoDocumentoId(tipoDocumentoId);
				cdDescargo.setNroDocumento(nroDocumento);
				cdDescargo.setFechaDocumento(fechaDocumento);	
				cdDescargo.setObservacion((". Obsv: ").concat(observacion).concat(". Servidor :").concat(usuario).concat(". Monto a Compensar: ").concat(montoACompensar.toString()).concat(" Total Compensado: ").concat(totalDescargo.toString()).concat(" F. Compensacion: ").concat(DateUtil.convertDateToString(fechaCompensacion)));
				cdDescargo.setTipoDescargo(Constante.TIPO_DESCARGO_COMPENSACION);
				cdDescargo.setEstado(Constante.ESTADO_COMPESADO);
				cdDescargo.setFechaRegistro(DateUtil.getCurrentDate());
				// INICIO MODIFICACIONES nuevo descargo -=CRAMIREZ=-
				cdDescargo.setTotalDescargado(montoACompensar);
				cdDescargo.setPersonaId(getSessionManaged().getContribuyente().getPersonaId());
				cdDescargo.setUsuarioId(getSessionManaged().getUsuarioLogIn().getUsuarioId());
				cdDescargo.setTerminal(getSessionManaged().getTerminalLogIn());
				descargoBo.descargarDeudas(cdDescargo, listCtaCteAEliminar,fechaCompensacion,personaId, this.expedientesIds, this.recibosIds, this.listpagos,1);
				// FIN MODIFICACIONES nuevo descargo -=CRAMIREZ=-					
				//listCtaCte = descargoBo.obtenerTodaDeuda(personaId);
				listCtaCte = descargoBo.obtenerTodaDeudaConFecha(personaId, fechaCompensacion);	
				listTempCtaCte = new ArrayList<DeudaDTO>();
				listTempCtaCte.addAll(listCtaCte);
						
				idDeudasEliminar.clear();
				limpiarFiltros();
			} catch (SisatException e) {
				addErrorMessage(e.getMessage());				
			} 
		}
	}
	
	public void inicioTransferencia(){
		this.recibosIds = "";
		showListaExpTransferencia = true;
		showListaPagosTransferencia = true;
		canPagosCompenzacion = 0 ;
		montoTotalRecibos = BigDecimal.ZERO;
		showSelectReciboParcial = false;
		showSelectRecibo = false;
		seleccionarDescargo = 0 ;
		listpagos = new ArrayList<ReciboPagoDescargo>();
		changePersonaRecibo();
		
		limpiar();
		totalCompensar = new BigDecimal(0);
		for (DeudaDTO de : listTempCtaCte) {
			if (de.isSelected()) {
				totalCompensar=de.getTotalDeuda().add(totalCompensar);				
			}
		}
		if(totalCompensar.compareTo(new BigDecimal(0)) == 0){
			WebMessages.messageError("Seleccione las deudas a transferir");
		}
		
		try {
			listItemsExpedientes = descargoBo.obtenerExpedientesContibuyente(this.personaId,Constante.TIPOS_TRAMITES_TRANSFERENCIA);
			Iterator<ItemBandejaEntradaDTO> iterar = listItemsExpedientes.iterator();
			showOptionTipoDescargo =false; 
			while (iterar.hasNext()) {					
				ItemBandejaEntradaDTO lsm = iterar.next();
				lsm.setSelect(false);	
				if(lsm.getFlagDescargo() == 0){
					showOptionTipoDescargo = true;
				}
			}
			if(!showOptionTipoDescargo){
				setSeleccionarDescargo(2);
			}
		} catch (SisatException e) {
			e.printStackTrace();
		}
	}
	
	public void buscarRecibosContribuyente(int persona_id) {
		try {
			montoTotalRecibos = BigDecimal.ZERO;
			
			listpagos = descargoBo.obtenerRecibosPagoDescargo(persona_id);
			canPagosCompenzacion = listpagos.size() ;
			
			Iterator<ReciboPagoDescargo> iterarPago = listpagos.iterator();
			
			while (iterarPago.hasNext()) {	
				ReciboPagoDescargo lp = iterarPago.next();
				lp.setSelect(false);					
			}
			
			if(canPagosCompenzacion <=0 ){
				contribuyente = null;
				listpagos = new ArrayList<ReciboPagoDescargo>();
				WebMessages.messageError("El contribuyente no tiene registrado ninguna recibo de pagos.");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void changePersonaRecibo(){
		contribuyente = null;
		listpagos = new ArrayList<ReciboPagoDescargo>();
	}
	
	public void tranferencia(){
		if(estadoValidaDocumento == 1) {
			WebMessages.messageError("El número de documento ya está registrado");
			return;
		}
		
		validaCompensacionTransferencia();
		if (!idDeudasEliminar.isEmpty()) {
			try {
				//Eliminar Deudas y agregar registro
				cdDescargo.setTipoDocumentoId(tipoDocumentoId);
				cdDescargo.setNroDocumento(nroDocumento);
				cdDescargo.setFechaDocumento(fechaDocumento);	
				cdDescargo.setObservacion((". Obsv: ").concat(observacion).concat(". Servidor :").concat(usuario).concat(". Monto a Compensar: ").concat(montoACompensar.toString()).concat(" Total Compensado: ").concat(totalDescargo.toString()).concat(" F. Compensacion: ").concat(DateUtil.convertDateToString(fechaCompensacion)));
				cdDescargo.setTipoDescargo(Constante.TIPO_DESCARGO_TRANSFERENCIA);
				cdDescargo.setEstado(Constante.ESTADO_TRANSFERENCIA);
				cdDescargo.setFechaRegistro(DateUtil.getCurrentDate());
				// INICIO MODIFICACIONES nuevo descargo -=CRAMIREZ=-
				cdDescargo.setTotalDescargado(montoACompensar);
				cdDescargo.setPersonaId(getSessionManaged().getContribuyente().getPersonaId());
				cdDescargo.setUsuarioId(getSessionManaged().getUsuarioLogIn().getUsuarioId());
				cdDescargo.setTerminal(getSessionManaged().getTerminalLogIn());
				descargoBo.descargarDeudas(cdDescargo, listCtaCteAEliminar,fechaCompensacion, contribuyente.getPersonaId() , this.expedientesIds, this.recibosIds, this.listpagos, 1);
				// FIN MODIFICACIONES nuevo descargo -=CRAMIREZ=-					
				//listCtaCte = descargoBo.obtenerTodaDeuda(personaId);
				listCtaCte = descargoBo.obtenerTodaDeudaConFecha(personaId, fechaCompensacion);	
				listTempCtaCte = new ArrayList<DeudaDTO>();
				listTempCtaCte.addAll(listCtaCte);
				
				idDeudasEliminar.clear();
				limpiarFiltros();
			} catch (SisatException e) {
				addErrorMessage(e.getMessage());				
			} 
		}
	}
	public void inicioRectificacion(){
		limpiar();
		totalRectificar= new BigDecimal(0);
		totalInsolutoRectificar= new BigDecimal(0);
		List<String> lstSubConceptos = new ArrayList<String>();		

		for (DeudaDTO de : listTempCtaCte) {
			if (de.isSelected()) {
				lstSubConceptos.add(de.getSubconcepto());
				totalRectificar=de.getTotalDeuda().add(totalRectificar);
				totalInsolutoRectificar=de.getInsoluto().add(totalInsolutoRectificar);
			}			
		}
		if(!validarTributo(lstSubConceptos)){
			WebMessages.messageError("Solo debe seleccionar un tipo de tributo");
		}else if(totalRectificar.compareTo(new BigDecimal(0)) == 0){
			WebMessages.messageError("Seleccione las deudas a descargar");
		}
	}
	
	public Boolean validarTributo(List<String> lstSubConceptos){
		Boolean valido=true;
		if(lstSubConceptos.size() >1){
			String pivote = lstSubConceptos.get(0);
			for(String subconcepto : lstSubConceptos){
				if(pivote.compareToIgnoreCase(subconcepto) != 0){
					valido = false;
					break;
				}				
			}
		}
		
		return valido;
	}
	
	public void limpiar(){
//		this.tipoDocumentoId=1;
//		this.nroDocumento="";
//		this.fechaDocumento=null;
//		this.observacion="";
		this.totalPrescribir = new BigDecimal(0);
		this.totalCompensar = new BigDecimal(0);
		this.totalDescargo = new BigDecimal(0);
		this.totalRectificar = new BigDecimal(0);
//		this.tipoDocumento="Acta";
	}

	public void descargo() {
		if(estadoValidaDocumento == 1) {
			WebMessages.messageError("El número de documento ya está registrado");
			return;
		}
		

		List<DeudaDTO> listCtaCteAEliminar = new ArrayList<DeudaDTO>();
		
		totalDescargo = new BigDecimal(0);
		for (DeudaDTO de : listTempCtaCte) {
			if (de.isSelected()) {
				totalDescargo=de.getTotalDeuda().add(totalDescargo);
				idDeudasEliminar.add(de);
				listCtaCteAEliminar.add(de);
			}
		}		
		
		if (!idDeudasEliminar.isEmpty()) {
			
			try {
				//Eliminar Deudas y agregar registro
				cdDescargo.setTipoDocumentoId(tipoDocumentoId);
				cdDescargo.setNroDocumento(nroDocumento);
				cdDescargo.setFechaDocumento(fechaDocumento);				
				cdDescargo.setObservacion(("Obsv: ").concat(observacion).concat(". Servidor :").concat(usuario)
								.concat(". Total Descargado: ").concat(totalDescargo.toString()));
				cdDescargo.setTipoDescargo(Constante.TIPO_DESCARGO_DESCARGO);
				cdDescargo.setEstado(Constante.ESTADO_DESCARGADO);
				cdDescargo.setFechaRegistro(DateUtil.getCurrentDate());				
				cdDescargo.setTotalDescargado(totalDescargo);
				
				// INICIO MODIFICACIONES nuevo descargo -=CRAMIREZ=-
				
				cdDescargo.setPersonaId(getSessionManaged().getContribuyente().getPersonaId());
				cdDescargo.setUsuarioId(getSessionManaged().getUsuarioLogIn().getUsuarioId());
				cdDescargo.setTerminal(getSessionManaged().getTerminalLogIn());
				
				descargoBo.descargarDeudas(cdDescargo, listCtaCteAEliminar,fechaCompensacion,0, this.expedientesIds, this.recibosIds, this.listpagos,1);
				
				// FIN MODIFICACIONES nuevo descargo -=CRAMIREZ=-
				//descargoBo.descargarDeudas(cdDescargo, listCtaCteAEliminar);
				
				//listCtaCte = descargoBo.obtenerTodaDeuda(personaId);
				listCtaCte = descargoBo.obtenerTodaDeudaConFecha(personaId, fechaCompensacion);	
				listTempCtaCte = new ArrayList<DeudaDTO>();
				listTempCtaCte.addAll(listCtaCte);
				
				idDeudasEliminar.clear();
				limpiarFiltros();
				
				////addInfoMessage("La operación ha sido realizada con éxito");
			} catch (SisatException e) {
				addErrorMessage(e.getMessage());				
			}
		}
		
	}
	
	
	

	public void rectificacion(){
//			System.out.println("Modificacion de deudas por RECTIFICACION");
			
			List<DeudaDTO> listCtaCteARectificar = new ArrayList<DeudaDTO>();
			String deudasIds = "";
	
			
			totalRectificar = new BigDecimal(0);
			for (DeudaDTO de : listTempCtaCte) {
				if (de.isSelected()) {
					totalRectificar=de.getTotalDeuda().add(totalRectificar);
					idDeudasEliminar.add(de); //Se rectificara la deuda
					deudasIds = deudasIds + de.getDeudaId() + "; ";

					listCtaCteARectificar.add(de);
				}
			}		
			
			if (!idDeudasEliminar.isEmpty()) {
				try {
					//Rectificar Deuda y agregar registro
					//DESCARGO
					cdDescargo.setTipoDescargo(Constante.TIPO_DESCARGO_RECTIFICACION);
					cdDescargo.setTipoDocumentoId(tipoDocumentoId);
					cdDescargo.setNroDocumento(nroDocumento);
					cdDescargo.setFechaDocumento(fechaDocumento);	
					cdDescargo.setObservacion(("Obsv: ").concat(observacion).concat(".Servidor: ").concat(usuario).concat(". Nuevo Monto: ").concat(montoRectificado.toString()).concat(" Monto Anterior: ").concat(totalInsolutoRectificar.toString()).concat(" Monto Total: ").concat(totalRectificar.toString()).concat(". Deudas: ").concat(deudasIds).concat(". Determinacion:").concat(Integer.toString(listCtaCteARectificar.get(0).getDeterminacionId())));
					cdDescargo.setTotalDescargado(totalRectificar);
					
					cdDescargo.setDeudaId(listCtaCteARectificar.get(0).getDeudaId());
	    			cdDescargo.setInteres(listCtaCteARectificar.get(0).getInteres());
	    			cdDescargo.setReajuste(listCtaCteARectificar.get(0).getReajuste());
	    			cdDescargo.setTotalDeuda(listCtaCteARectificar.get(0).getTotalDeuda());
	    			cdDescargo.setFechaRegistroDeuda(listCtaCteARectificar.get(0).getFechaRegistro());
	    			
					cdDescargo.setEstado(Constante.ESTADO_RECTIFICADO);
					cdDescargo.setFechaRegistro(DateUtil.getCurrentDate());
					
					cdDescargo.setUsuarioId(getSessionManaged().getUsuarioLogIn().getUsuarioId());
					cdDescargo.setTerminal(getSessionManaged().getTerminalLogIn());
					
					//DEUDA NUEVA
					DeudaDTO deudaARectificar = new DeudaDTO();					
					deudaARectificar.setDeterminacionId(listCtaCteARectificar.get(0).getDeterminacionId());
					deudaARectificar.setPersonaId(listCtaCteARectificar.get(0).getPersonaId());
					deudaARectificar.setAnioDeuda(listCtaCteARectificar.get(0).getAnioDeuda());					
					deudaARectificar.setInsoluto(montoRectificado);//MONTO A CAMBIAR EN EL INSOLUTO
					deudaARectificar.setNumCuota(listCtaCteARectificar.get(0).getNroCuotas());
					deudaARectificar.setUsuarioId(cdDescargo.getUsuarioId());
					deudaARectificar.setTerminal(getSessionManaged().getTerminalLogIn());
					
					
					//descargoBo.rectificarDeuda(cdDescargo,deudaARectificar);
					
					
					//listCtaCte = descargoBo.obtenerTodaDeuda(personaId);
					
					idDeudasEliminar.clear();
					limpiarFiltros();
				} catch (SisatException e) {
					addErrorMessage(e.getMessage());				
				} 
			}
//			System.out.println("FIN Modificacion de deudas por RECTIFICACION");
		}
	
	public void valueChangeListenerItem(DeudaDTO deudaDTO){
		if(deudaDTO.isSelected()){
			if(deudaDTOSeleccionada.equals(deudaDTO)){
				deudaDTOSeleccionada = new DeudaDTO();
			}
		}		
		else{
			deudaDTOSeleccionada = deudaDTO;
		}
	}
	
	public void changeListenerCmbTipoDocumento(ValueChangeEvent event){
		
		String cmbValueSelect = (String) event.getNewValue();
		Integer id = mapGnTipodocumento.get(cmbValueSelect);
		
		if(id != null){
			
			this.tipoDocumentoId = id;
		}		
	}
	
	public void changeSelectAllDeu(ValueChangeEvent ev){
		String nv = ev.getNewValue().toString();
		if(nv.equals("true")){
			for(DeudaDTO de : listCtaCte){
				de.setSelected(true);
			}
		}else{
			for(DeudaDTO de : listCtaCte){
				de.setSelected(false);
			}
		}
	}
	
	public void changeListenerComboBoxAnioDeuda(ValueChangeEvent event) {
		
		String value = event.getNewValue().toString();
		System.out.println("anio : " + value);
		if(value != null && !value.isEmpty() && value.compareToIgnoreCase("Todo")!=0){
			anioDeuda = Integer.parseInt(value);
		}else{
			anioDeuda = null;
		}
	}
	
	public void changeListenerComboBoxDescripcion(ValueChangeEvent event) {
		String value = event.getNewValue().toString();
		
		if( value != null && !value.isEmpty() && value.compareToIgnoreCase("Todo")!=0 ){
			setDescripcion(value);
		}else{
			descripcion = null;
		}
	}
	
	public void changeListenerComboBoxSubConcepto(ValueChangeEvent event) {
		String value = event.getNewValue().toString();
		
		if(value != null && !value.isEmpty() && value.compareToIgnoreCase("Todo")!=0){
			setSubconcepto(value);
		}else{
			subconcepto = null;
		}
	}
	
	public void changeListenerComboBoxNroCuota(ValueChangeEvent event) {
		String value = event.getNewValue().toString();
		
		if(value != null && !value.isEmpty() && value.compareToIgnoreCase("Todo")!=0){
			nroCuota = Integer.parseInt(value);
		}else{
			nroCuota = null;
		}
	}
	
	
	
	
	
	
	public void validarUltimosDigitos() throws Exception {
		//obtener anio de la papeleta para proceder hacer la visualizacion
		
		validaNumeroDocumento();
		
		String anio_papeleta, verifica_anio;
		verifica_anio = nroDocumento.substring(nroDocumento.length() - 5, nroDocumento.length() - 4);
		
		if (verifica_anio.equals("-")) {
			anio_papeleta = nroDocumento.substring(nroDocumento.length() - 4);
		}
		else {anio_papeleta = "SN";}
		if (nroDocumento.equals("___-___-________-___") || nroDocumento.equals(null) || nroDocumento.substring(17).equals("____")
				|| nroDocumento.substring(20, 21).equals("_") || nroDocumento.substring(21).equals("_")) {
			setNroDocumento(null);
		} else {
			int valor = Integer.parseInt(String.valueOf(nroDocumento.substring(17)));
			Calendar c = new GregorianCalendar();
			int annio = Integer.parseInt(String.valueOf(c.get(Calendar.YEAR)));
			if (valor > annio) {
				WebMessages.messageError("Verifique los datos del año");
			} 

		}
	}
	
	public void validaNumeroDocumento(){
		System.out.println("validaNumeroDocumento");
		try {
			estadoValidaDocumento = descargoBo.validaNumeroDocumento(this.nroDocumento);
		
			if(estadoValidaDocumento == 1) {
				WebMessages.messageError("El número de documento ya está registrado");
				return;
			}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public String salir(){
		FacesUtil.closeSession("DescargoDeudasManaged");
		return sendRedirectPrincipal();
	}
	
	
	public List<DeudaDTO> getListCtaCte() {
		return listCtaCte;
	}
	
	
	public DescargoBoRemote getDescargoBo() {
		return descargoBo;
	}

	public void setDescargoBo(DescargoBoRemote descargoBo) {
		this.descargoBo = descargoBo;
	}

	public int getPersonaId() {
		return personaId;
	}

	public void setPersonaId(int personaId) {
		this.personaId = personaId;
	}	

	public DeudaDTO getDeudaDTOSeleccionada() {
		return deudaDTOSeleccionada;
	}

	public void setDeudaDTOSeleccionada(DeudaDTO deudaDTOSeleccionada) {
		this.deudaDTOSeleccionada = deudaDTOSeleccionada;
	}

	public BigDecimal getInsolutoCancelado() {
		return insolutoCancelado;
	}

	public void setInsolutoCancelado(BigDecimal insolutoCancelado) {
		this.insolutoCancelado = insolutoCancelado;
	}

	public BigDecimal getDerechoEmisionCancelado() {
		return derechoEmisionCancelado;
	}

	public void setDerechoEmisionCancelado(BigDecimal derechoEmisionCancelado) {
		this.derechoEmisionCancelado = derechoEmisionCancelado;
	}

	public BigDecimal getTotalDeudaCancelada() {
		return totalDeudaCancelada;
	}

	public void setTotalDeudaCancelada(BigDecimal totalDeudaCancelada) {
		this.totalDeudaCancelada = totalDeudaCancelada;
	}

	public int getDeudaId() {
		return deudaId;
	}

	public void setDeudaId(int deudaId) {
		this.deudaId = deudaId;
	}

	public CdDescargo getCdDescargo() {
		return cdDescargo;
	}

	public void setCdDescargo(CdDescargo cdDescargo) {
		this.cdDescargo = cdDescargo;
	}

	public List<SelectItem> getLstTipoDocumento() {
		return lstTipoDocumento;
	}

	public void setLstTipoDocumento(List<SelectItem> lstTipoDocumento) {
		this.lstTipoDocumento = lstTipoDocumento;
	}
	
	public int getTipoDescargo() {
		return tipoDescargo;
	}

	public void setTipoDescargo(int tipoDescargo) {
		this.tipoDescargo = tipoDescargo;
	}

	public Integer getTipoDocumentoId() {
		return tipoDocumentoId;
	}

	public void setTipoDocumentoId(Integer tipoDocumentoId) {
		this.tipoDocumentoId = tipoDocumentoId;
	}

	public String getNroDocumento() {
		return nroDocumento;
	}

	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}

	public Date getFechaDocumento() {
		return fechaDocumento;
	}

	public void setFechaDocumento(Date fechaDocumento) {
		this.fechaDocumento = fechaDocumento;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public DescargoDeudasManaged(){
		getSessionManaged().setLinkRegresar("/sisat/descargo/descargodeudas.xhtml");
	}

	public BigDecimal getTotalPrescribir() {
		return totalPrescribir;
	}

	public void setTotalPrescribir(BigDecimal totalPrescribir) {
		this.totalPrescribir = totalPrescribir;
	}
	
	public BigDecimal getTotalCompensar() {
		return totalCompensar;
	}


	public void setTotalCompensar(BigDecimal totalCompensar) {
		this.totalCompensar = totalCompensar;
	}	

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public BigDecimal getTotalDescargo() {
		return totalDescargo;
	}

	public void setTotalDescargo(BigDecimal totalDescargo) {
		this.totalDescargo = totalDescargo;
	}

	public BigDecimal getMontoACompensar() {
		return montoACompensar;
	}

	public void setMontoACompensar(BigDecimal montoACompensar) {
		this.montoACompensar = montoACompensar;
	}

	public Date getFechaCompensacion() {
		return fechaCompensacion;
	}

	public void setFechaCompensacion(Date fechaCompensacion) {
		this.fechaCompensacion = fechaCompensacion;
	}
	public BigDecimal getTotalRectificar() {
		return totalRectificar;
	}
	public void setTotalRectificar(BigDecimal totalRectificar) {
		this.totalRectificar = totalRectificar;
	}
	public BigDecimal getMontoRectificado() {
		return montoRectificado;
	}
	public void setMontoRectificado(BigDecimal montoRectificado) {
		this.montoRectificado = montoRectificado;
	}
	public BigDecimal getTotalInsolutoRectificar() {
		return totalInsolutoRectificar;
	}
	public void setTotalInsolutoRectificar(BigDecimal totalInsolutoRectificar) {
		this.totalInsolutoRectificar = totalInsolutoRectificar;
	}
	public boolean isSelectedAllDeu() {
		return selectedAllDeu;
	}
	public void setSelectedAllDeu(boolean selectedAllDeu) {
		this.selectedAllDeu = selectedAllDeu;
	}
	public List<SelectItem> getListAnioDeuda() {
		return listAnioDeuda;
	}
	public void setListAnioDeuda(List<SelectItem> listAnioDeuda) {
		this.listAnioDeuda = listAnioDeuda;
	}
	public List<SelectItem> getListDescripcion() {
		return listDescripcion;
	}
	public void setListDescripcion(List<SelectItem> listDescripcion) {
		this.listDescripcion = listDescripcion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public List<SelectItem> getListSubConcepto() {
		return listSubConcepto;
	}
	public void setListSubConcepto(List<SelectItem> listSubConcepto) {
		this.listSubConcepto = listSubConcepto;
	}
	public String getSubconcepto() {
		return subconcepto;
	}
	public void setSubconcepto(String subconcepto) {
		this.subconcepto = subconcepto;
	}
	public List<SelectItem> getListNroCuota() {
		return listNroCuota;
	}
	public void setListNroCuota(List<SelectItem> listNroCuota) {
		this.listNroCuota = listNroCuota;
	}
	public Integer getNroCuota() {
		return nroCuota;
	}
	public void setNroCuota(Integer nroCuota) {
		this.nroCuota = nroCuota;
	}
	public LinkedHashSet <Integer> getListHashSetAnioDeuda() {
		return listHashSetAnioDeuda;
	}
	public void setListHashSetAnioDeuda(LinkedHashSet <Integer> listHashSetAnioDeuda) {
		this.listHashSetAnioDeuda = listHashSetAnioDeuda;
	}
	public Integer getAnioDeuda() {
		return anioDeuda;
	}
	public void setAnioDeuda(Integer anioDeuda) {
		this.anioDeuda = anioDeuda;
	}
	public LinkedHashSet <String> getListHashSetDescripcion() {
		return listHashSetDescripcion;
	}
	public void setListHashSetDescripcion(
			LinkedHashSet <String> listHashSetAnioDescripcion) {
		this.listHashSetDescripcion = listHashSetAnioDescripcion;
	}	
	public LinkedHashSet <String> getListHashSetSubConcepto() {
		return listHashSetSubConcepto;
	}
	public void setListHashSetSubConcepto(LinkedHashSet <String> listHashSetSubConcepto) {
		this.listHashSetSubConcepto = listHashSetSubConcepto;
	}
	public LinkedHashSet <Integer> getListHashSetNroCuota() {
		return listHashSetNroCuota;
	}
	public void setListHashSetNroCuota(LinkedHashSet <Integer> listHashSetNroCuota) {
		this.listHashSetNroCuota = listHashSetNroCuota;
	}
	public String getCmbNroCuota() {
		return cmbNroCuota;
	}
	public void setCmbNroCuota(String cmbNroCuota) {
		this.cmbNroCuota = cmbNroCuota;
	}
	public String getCmbAnioDeuda() {
		return cmbAnioDeuda;
	}
	public void setCmbAnioDeuda(String cmbAnioDeuda) {
		this.cmbAnioDeuda = cmbAnioDeuda;
	}

	public List<SimpleMenuDTO> getListPermisosSubmenu() {
		return listPermisosSubmenu;
	}

	public void setListPermisosSubmenu(List<SimpleMenuDTO> listPermisosSubmenu) {
		this.listPermisosSubmenu = listPermisosSubmenu;
	}

	public boolean isPermisoCalcularDeuda() {
		return permisoCalcularDeuda;
	}

	public void setPermisoCalcularDeuda(boolean permisoCalcularDeuda) {
		this.permisoCalcularDeuda = permisoCalcularDeuda;
	}

	public boolean isPermisoFiltrar() {
		return permisoFiltrar;
	}

	public void setPermisoFiltrar(boolean permisoFiltrar) {
		this.permisoFiltrar = permisoFiltrar;
	}

	public boolean isPermisoDescargo() {
		return permisoDescargo;
	}

	public void setPermisoDescargo(boolean permisoDescargo) {
		this.permisoDescargo = permisoDescargo;
	}

	public boolean isPermisoTransferencia() {
		return permisoTransferencia;
	}

	public void setPermisoTransferencia(boolean permisoTransferencia) {
		this.permisoTransferencia = permisoTransferencia;
	}

	public boolean isPermisoPrescripciones() {
		return permisoPrescripciones;
	}

	public void setPermisoPrescripciones(boolean permisoPrescripciones) {
		this.permisoPrescripciones = permisoPrescripciones;
	}

	public boolean isPermisoCompensaciones() {
		return permisoCompensaciones;
	}

	public void setPermisoCompensaciones(boolean permisoCompensaciones) {
		this.permisoCompensaciones = permisoCompensaciones;
	}

	public List<ItemBandejaEntradaDTO> getListItemsExpedientes() {
		return listItemsExpedientes;
	}

	public void setListItemsExpedientes(List<ItemBandejaEntradaDTO> listItemsExpedientes) {
		this.listItemsExpedientes = listItemsExpedientes;
	}

	public int getCanSolicitudesPrescripcion() {
		return canSolicitudesPrescripcion;
	}

	public void setCanSolicitudesPrescripcion(int canSolicitudesPrescripcion) {
		this.canSolicitudesPrescripcion = canSolicitudesPrescripcion;
	}

	public ItemBandejaEntradaDTO getSlectExpPrescripcion() {
		return slectExpPrescripcion;
	}

	public void setSlectExpPrescripcion(ItemBandejaEntradaDTO slectExpPrescripcion) {
		this.slectExpPrescripcion = slectExpPrescripcion;
	}

	public Boolean getShowListaExpPrescipcion() {
		return showListaExpPrescipcion;
	}

	public void setShowListaExpPrescipcion(Boolean showListaExpPrescipcion) {
		this.showListaExpPrescipcion = showListaExpPrescipcion;
	}

	public Boolean getShowListaExpCompenzacion() {
		return showListaExpCompenzacion;
	}

	public void setShowListaExpCompenzacion(Boolean showListaExpCompenzacion) {
		this.showListaExpCompenzacion = showListaExpCompenzacion;
	}

	public int getCanPagosCompenzacion() {
		return canPagosCompenzacion;
	}

	public void setCanPagosCompenzacion(int canPagosCompenzacion) {
		this.canPagosCompenzacion = canPagosCompenzacion;
	}

	public List<ReciboPagoDescargo> getListpagos() {
		return listpagos;
	}

	public void setListpagos(List<ReciboPagoDescargo> listpagos) {
		this.listpagos = listpagos;
	}

	public Boolean getShowListaPagosCompenzacion() {
		return showListaPagosCompenzacion;
	}

	public void setShowListaPagosCompenzacion(Boolean showListaPagosCompenzacion) {
		this.showListaPagosCompenzacion = showListaPagosCompenzacion;
	}

	public String getRecibosIds() {
		return recibosIds;
	}

	public void setRecibosIds(String recibosIds) {
		this.recibosIds = recibosIds;
	}

	public Boolean getShowSelectRecibo() {
		return showSelectRecibo;
	}

	public void setShowSelectRecibo(Boolean showSelectRecibo) {
		this.showSelectRecibo = showSelectRecibo;
	}

	public List<ReciboPagoDetallePieDTO> getListReciboPagoDetalle() {
		return listReciboPagoDetalle;
	}

	public void setListReciboPagoDetalle(List<ReciboPagoDetallePieDTO> listReciboPagoDetalle) {
		this.listReciboPagoDetalle = listReciboPagoDetalle;
	}

	public List<ReciboPagoDetalleDTO> getShowlistReciboPagoDetalle() {
		return showlistReciboPagoDetalle;
	}

	public void setShowlistReciboPagoDetalle(List<ReciboPagoDetalleDTO> showlistReciboPagoDetalle) {
		this.showlistReciboPagoDetalle = showlistReciboPagoDetalle;
	}

	public Boolean getShowSelectReciboParcial() {
		return showSelectReciboParcial;
	}

	public void setShowSelectReciboParcial(Boolean showSelectReciboParcial) {
		this.showSelectReciboParcial = showSelectReciboParcial;
	}

	public ReciboPagoDescargo getReciboSelect() {
		return ReciboSelect;
	}

	public void setReciboSelect(ReciboPagoDescargo reciboSelect) {
		ReciboSelect = reciboSelect;
	}

	public BigDecimal getMontoTotalRecibos() {
		return montoTotalRecibos;
	}

	public void setMontoTotalRecibos(BigDecimal montoTotalRecibos) {
		this.montoTotalRecibos = montoTotalRecibos;
	}

	public Integer getSeleccionarDescargo() {
		return seleccionarDescargo;
	}

	public void setSeleccionarDescargo(Integer seleccionarDescargo) {
		this.seleccionarDescargo = seleccionarDescargo;
	}

	public Boolean getShowOptionTipoDescargo() {
		return showOptionTipoDescargo;
	}

	public void setShowOptionTipoDescargo(Boolean showOptionTipoDescargo) {
		this.showOptionTipoDescargo = showOptionTipoDescargo;
	}

	public BuscarPersonaDTO getContribuyente() {
		return contribuyente;
	}

	public void setContribuyente(BuscarPersonaDTO contribuyente) {
		this.contribuyente = contribuyente;
	}

	public Boolean getShowListaExpTransferencia() {
		return showListaExpTransferencia;
	}

	public void setShowListaExpTransferencia(Boolean showListaExpTransferencia) {
		this.showListaExpTransferencia = showListaExpTransferencia;
	}

	public Boolean getShowListaPagosTransferencia() {
		return showListaPagosTransferencia;
	}

	public void setShowListaPagosTransferencia(Boolean showListaPagosTransferencia) {
		this.showListaPagosTransferencia = showListaPagosTransferencia;
	}

	public Boolean getShowListDeuda() {
		return showListDeuda;
	}

	public void setShowListDeuda(Boolean showListDeuda) {
		this.showListDeuda = showListDeuda;
	}

	public Boolean getShowListDeudaSelecionada() {
		return showListDeudaSelecionada;
	}

	public void setShowListDeudaSelecionada(Boolean showListDeudaSelecionada) {
		this.showListDeudaSelecionada = showListDeudaSelecionada;
	}
	
	
}
