package com.sat.sisat.alcabala.managed;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import com.sat.sisat.alcabala.BuscarAlcabalaDTO;
import com.sat.sisat.alcabala.ImprimirAlcabalaDTO;
import com.sat.sisat.alcabala.business.AlcabalaRemote;
import com.sat.sisat.common.business.GeneralBoRemote;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.determinacion.vehicular.dto.DatosInafecDTO;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.menus.business.MenuBoRemote;
import com.sat.sisat.menus.dto.SimpleMenuDTO;
import com.sat.sisat.persistence.entity.GnTipoDocumento;
import com.sat.sisat.persistence.entity.RpDjpredial;
import com.sat.sisat.vehicular.dto.AnexosDeclaVehicDTO;
import com.sat.sisat.vehicular.dto.BuscarPersonaDTO;

@ManagedBean
@ViewScoped
public class BusquedaAlcabalaManaged extends BaseManaged{
	
	@EJB
	AlcabalaRemote servicioAlcabala;
	
	@EJB
	GeneralBoRemote generalBo;
	
	@EJB
	MenuBoRemote menuBo;

	public BusquedaAlcabalaManaged(){
		getSessionManaged().setLinkRegresar("/sisat/persona/detallepersona.xhtml");

	}
	
	private ArrayList<BuscarAlcabalaDTO> records;
	private static final long serialVersionUID = 1L;
	private String djAlcabala;
	private Date fechaInicio;
	private Date fechaFin;
	
	private Boolean inafectoImpuesto;
	private DatosInafecDTO datosInafec= new DatosInafecDTO();
	
	private RpDjpredial rpDjPredial = new RpDjpredial();

	public int personaId=getSessionManaged().getContribuyente().getPersonaId();
	
	private BuscarAlcabalaDTO currentItem=new BuscarAlcabalaDTO();
	
	private String tipoDocumento;
	private List<SelectItem> lstTipoDocumento;
	private HashMap<String, Integer> mapGnTipodocumento = new HashMap<String, Integer>();
	private String nroDocumento;
	private Date fechaDocumento;
	private String observacion;
	
	// INICIO PERMISOS PARA EL MODULO -=CRAMIREZ=-
		private List<SimpleMenuDTO> listPermisosSubmenu = new ArrayList<SimpleMenuDTO>();
		private boolean permisoBuscar;
		private boolean permisoAgregarRegistrar;
		private boolean permisoVerDetalle;
		private boolean permisoModificarActualizar;
		private boolean permisoImprimir;

	// FIN PERMISOS PARA EL MODULO
	
	@PostConstruct
	public void init(){
		permisosMenu();
		try{		
			setDjAlcabala("");
			buscarDjAlcabala();
			
			lstTipoDocumento = new ArrayList<SelectItem>();		
			
			List<GnTipoDocumento> list = servicioAlcabala.obtenerTipoDocumentos();
			lstTipoDocumento = new ArrayList<SelectItem>();		
	
			for (GnTipoDocumento it : list) {
				lstTipoDocumento.add(new SelectItem(it.getDescripcion(), String.valueOf(it.getTipoDocumentoId())));
				mapGnTipodocumento.put(it.getDescripcion(), it.getTipoDocumentoId());
			}
			fechaDocumento = DateUtil.getCurrentDate();
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		
	}
	public void permisosMenu() {	
		try {
			int submenuId = Constante.DECLARACION_JURADA_ACABALA;
			
			int permisoBuscarId = Constante.BUSCAR;
			int permisoAgregarRegistrarId = Constante.AGREGAR_REGISTRAR;
			int permisoVerDetalleId = Constante.VER_DETALLE;
			int permisoModificarActualizarId = Constante.MODIFICAR_ACTUALIZAR;
			int permisoImprimirId = Constante.IMPRIMIR;
			
			permisoBuscar = false;
			permisoAgregarRegistrar = false;
			permisoVerDetalle = false;
			permisoModificarActualizar = false;
			permisoImprimir = false;
			
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
				if(lsm.getItemId() == permisoVerDetalleId) {
					permisoVerDetalle = true;
				}
				if(lsm.getItemId() == permisoModificarActualizarId) {
					permisoModificarActualizar = true;
				}
				if(lsm.getItemId() == permisoImprimirId) {
					permisoImprimir = true;
				}
			}
			
		} catch (SisatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public BuscarAlcabalaDTO getCurrentItem() {
		return currentItem;
	}


	public void setCurrentItem(BuscarAlcabalaDTO currentItem) {
		this.currentItem = currentItem;
	}


	public void seleccionaDatos(){
		try{
			if(currentItem!=null){
			
				CalculoAlcabalaManaged calculo=(CalculoAlcabalaManaged) getManaged("calculoAlcabalaManaged");
				calculo.setBotonGuarda(Boolean.FALSE);
				calculo.setProperty(currentItem);
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public void seleccionaLectura(){
		try{
			if(currentItem!=null){
			
				Date fechaConsulta= currentItem.getFechaDeclaracion();
				CalculoAlcabalaManaged calculo=(CalculoAlcabalaManaged) getManaged("calculoAlcabalaManaged");
				calculo.setBotonGuarda(Boolean.FALSE);
				calculo.setProperty(currentItem);
				
				int tipoTransferencia=currentItem.getTipoTransferenciaId();
				//DatosInafecDTO datosInafec= new DatosInafecDTO();
				
				datosInafec=servicioAlcabala.getInafecAlcabala(getSessionManaged().getContribuyente().getPersonaId());

				
				if(tipoTransferencia>15){
					calculo.obtieneValorTransferencia();
				}else if(datosInafec!=null){
					calculo.obtieneValorTransferencia();
				}else{
					calculo.realizarCalculo();
					calculo.calcularInteres(fechaConsulta);
					
				}

			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public void buscarDjAlcabala(){
				
		try {
			
			if(!djAlcabala.trim().equals("")){
				records= servicioAlcabala.getAllRaDjalcabala(personaId,Integer.parseInt(djAlcabala),fechaInicio,fechaFin);
			}else{
				records= servicioAlcabala.getAllRaDjalcabala(personaId, null,fechaInicio,fechaFin);
			}
			
				
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public void imprimirDjAlcabala()throws SisatException{
		try{   
		
			if(currentItem != null){
				
				CalculoAlcabalaManaged calculo=(CalculoAlcabalaManaged) getManaged("calculoAlcabalaManaged");
				calculo.setBotonGuarda(Boolean.FALSE);
				calculo.setProperty(currentItem);
				calculo.obtieneValorTransferencia();
				calculo.realizarCalculo();
				
				rpDjPredial = servicioAlcabala.getRpDjPredial(currentItem.getDjId());
				
				ImprimirAlcabalaDTO impAlcaDTO= new ImprimirAlcabalaDTO();
				
				
				impAlcaDTO.setDjAlcabalaId(String.valueOf(currentItem.getDjAlcabalaId()));
				
				impAlcaDTO.setUbicacionPropiedad(calculo.getTipoUbicacionPropiedad1());
				
				if (calculo.getSelectedTipoPred() == 1) {
					impAlcaDTO.setTipoPredio(Constante.TIPO_PREDIO_URBANO);
				} else if (calculo.getSelectedTipoPred() == 2) {
					impAlcaDTO.setTipoPredio(Constante.TIPO_PREDIO_RUSTICO);
				}
				
				impAlcaDTO.setDireccionPredio(calculo.getDireccionPredio());
				impAlcaDTO.setDjPredial(calculo.getDjPredio());		
				impAlcaDTO.setFechaTransferencia(calculo.getFechaTransferencia());
				impAlcaDTO.setsFechaTransferencia(DateUtil.convertDateToString(calculo.getFechaTransferencia()));
				impAlcaDTO.setFechaDeclaracion(calculo.getFechaDeclaracion());
				impAlcaDTO.setsFechaDeclaracion(DateUtil.convertDateToString(calculo.getFechaDeclaracion()));
				impAlcaDTO.setPorPropiedad(calculo.getPorcPropiedad());
				impAlcaDTO.setNotaria(calculo.getCmbTipoNotariaValor());
				impAlcaDTO.setTipoTransferencia(calculo.getCmbTipoTransferenciaValor());
				impAlcaDTO.setCondicionPropiedad(calculo.getCmbCondiPropiedadValor());
				impAlcaDTO.setTipoMoneda(calculo.getCmbTipoMonedaValor());
				impAlcaDTO.setValorAutovaluo(calculo.getAutovaluo());
				impAlcaDTO.setValorAutovaluoAjustado(calculo.getAutovaluoAjuste());
				impAlcaDTO.setValorTransferencia(calculo.getValorTransferencia());
				impAlcaDTO.setValorUIT(calculo.getValorUIT());
				impAlcaDTO.setFactorAjuste(calculo.getFactorAjuste());
				
				impAlcaDTO.setTipoCambio(calculo.getTipoCambio());
				
				
				impAlcaDTO.setAjuste(calculo.getAjuste());
				impAlcaDTO.setValorTransferenciaSoles(calculo.getValorTransferenciaSoles());
				impAlcaDTO.setNroUitDeduc(calculo.getNroUitDeduc());
				impAlcaDTO.setMayorValorComparado(calculo.getMayorValorComparado());
				impAlcaDTO.setValorDeduccion(calculo.getValorDeduccion());
				impAlcaDTO.setBaseImpo(calculo.getBaseImponible());
				impAlcaDTO.setBaseExonerada(calculo.getBaseExonerada());
				impAlcaDTO.setBaseAfecta(calculo.getBaseAfecta());
				impAlcaDTO.setTasa(calculo.getTasa());
				impAlcaDTO.setImpuestoPagar(calculo.getImpuestoPagar());
				impAlcaDTO.setInteresMora(calculo.getInteresMora());
				impAlcaDTO.setTotalPagar(calculo.getTotalPagar());
				impAlcaDTO.setInafectoImpuesto(inafectoImpuesto);
				
				impAlcaDTO.setTipoDocContribu(getSessionManaged().getContribuyente().getTipoDocumentoIdentidad());
				impAlcaDTO.setNroDocContri(getSessionManaged().getContribuyente().getNroDocuIdentidad());
				impAlcaDTO.setDirecContri(getSessionManaged().getContribuyente().getDomicilioPersona());
				impAlcaDTO.setPersonaId(getSessionManaged().getContribuyente().getPersonaId());
				
				currentItem.setPredioId(rpDjPredial.getPredioId());
				impAlcaDTO.setPredioId(currentItem.getPredioId());
				
				impAlcaDTO.setUsuario(calculo.getUsuario());
				

				if(inafectoImpuesto==Boolean.TRUE){
					impAlcaDTO.setEtiquetaAfectoImpuesto("Contribuyente inafecto a Impuesto de alcabala");
				}else{
					impAlcaDTO.setEtiquetaAfectoImpuesto("Contribuyente afecto a Impuesto de alcabala");
				}
				
				impAlcaDTO.setApellidosNombres(getSessionManaged().getContribuyente().getApellidosNombres());
				getSessionMap().put("datosRaDjAlcabalaSesion", impAlcaDTO);
				
				List<BuscarPersonaDTO> lstTransferentes = new ArrayList<BuscarPersonaDTO>();
				AgregarTransferenteAlcabalaManaged lstTransferente= (AgregarTransferenteAlcabalaManaged)getManaged("agregarTransferenteAlcabalaManaged");
				lstTransferentes=lstTransferente.getLstTransferentes();
				getSessionMap().put("lstTransferentesSesion", lstTransferentes);
				
				List<AnexosDeclaVehicDTO> lstDocAnexMuestra = new ArrayList<AnexosDeclaVehicDTO>();
				SustentoAlcabalaManaged lstDocAnexos = (SustentoAlcabalaManaged)getManaged("sustentoAlcabalaManaged");
				lstDocAnexMuestra=lstDocAnexos.getLstAnexosMuestra();
				getSessionMap().put("lstDocAnexoSesion", lstDocAnexMuestra);
				
				
			}
		
		
			}catch (SisatException e) {
				WebMessages.messageError(e.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				WebMessages.messageFatal(e);
			}
		}

	public void eliminarDJ(){

		//System.out.println("eliminar DJ ");		
		try {		
			//Eliminar DJ
			StringBuffer sbGlosa = new StringBuffer();
					
			sbGlosa.append("Obsv: ").append(observacion) ;
			sbGlosa.append(". Realizado: ").append(currentItem.getUsuario());
			sbGlosa.append(". Eliminado: ").append(getSessionManaged().getUsuarioLogIn().getNombreUsuario());
			sbGlosa.append(". Doc: ").append(tipoDocumento);
			sbGlosa.append(". No: ").append(nroDocumento);
			sbGlosa.append(". F: ").append( DateUtil.convertDateToString(fechaDocumento));		
			
			currentItem.setGlosa(sbGlosa.toString());
			currentItem.setUsuario(getSessionManaged().getUsuarioLogIn().getNombreUsuario());
			currentItem.setUsuarioId(getSessionManaged().getUsuarioLogIn().getUsuarioId());
			currentItem.setFechaDeclaracion(DateUtil.getCurrentDate());
			
			servicioAlcabala.eliminarDJAlcabala(currentItem);
			
			//Refrescar data
			buscarDjAlcabala();
						
			
		} catch (SisatException e) {			
			WebMessages.messageError(e.getMessage());
		}
	}
	
	public ArrayList<BuscarAlcabalaDTO> getRecords() {
		return records;
	}


	public void setRecords(ArrayList<BuscarAlcabalaDTO> records) {
		this.records = records;
	}


	public String getDjAlcabala() {
		return djAlcabala;
	}


	public void setDjAlcabala(String djAlcabala) {
		this.djAlcabala = djAlcabala;
	}


	public Date getFechaInicio() {
		return fechaInicio;
	}


	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}


	public Date getFechaFin() {
		return fechaFin;
	}


	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}


	public Boolean getInafectoImpuesto() {
		return inafectoImpuesto;
	}


	public void setInafectoImpuesto(Boolean inafectoImpuesto) {
		this.inafectoImpuesto = inafectoImpuesto;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public List<SelectItem> getLstTipoDocumento() {
		return lstTipoDocumento;
	}

	public void setLstTipoDocumento(List<SelectItem> lstTipoDocumento) {
		this.lstTipoDocumento = lstTipoDocumento;
	}

	public HashMap<String, Integer> getMapGnTipodocumento() {
		return mapGnTipodocumento;
	}

	public void setMapGnTipodocumento(HashMap<String, Integer> mapGnTipodocumento) {
		this.mapGnTipodocumento = mapGnTipodocumento;
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
	public boolean isPermisoImprimir() {
		return permisoImprimir;
	}
	public void setPermisoImprimir(boolean permisoImprimir) {
		this.permisoImprimir = permisoImprimir;
	}
}
