package com.sat.sisat.predial.managed;

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
import javax.faces.model.SelectItem;

import org.richfaces.component.html.HtmlExtendedDataTable;
import org.richfaces.model.selection.SimpleSelection;

import com.sat.sisat.alcabala.managed.CalculoAlcabalaManaged;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.FacesUtil;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.menus.business.MenuBoRemote;
import com.sat.sisat.menus.dto.SimpleMenuDTO;
import com.sat.sisat.persistence.entity.GnTipoDocumento;
import com.sat.sisat.persistence.entity.RpDjconstruccion;
import com.sat.sisat.predial.business.RegistroPrediosBoRemote;
import com.sat.sisat.predial.dto.FindRpDjPredial;
import com.sat.sisat.predial.dto.ListRpDjPredial;




@ManagedBean
@ViewScoped
public class EliminacionListaDJPredioManaged extends BaseManaged {

	private static final long serialVersionUID = 1215730818441379392L;

	@EJB
	RegistroPrediosBoRemote registroPrediosBo;
	
	@EJB
	MenuBoRemote menuBo;	
	
	private ArrayList<ListRpDjPredial> records;
	private SimpleSelection selection = new SimpleSelection();
	private HtmlExtendedDataTable table;
	private int currentRow;
	private ListRpDjPredial currentItem = new ListRpDjPredial();
	
	private String codigoPredio;
	
	private FindRpDjPredial findRpDjPredial;
	private ListRpDjPredial  djPredial = new ListRpDjPredial();
	
	private int anioCrear;
	private String rsCopiarDj= "E"; // Error
	
	private int djId = 0;	
	private int predioId = 0;
	
	private String tipoDocumento;
	private List<SelectItem> lstTipoDocumento;
	private HashMap<String, Integer> mapGnTipodocumento = new HashMap<String, Integer>();
	private String nroDocumento;
	private Date fechaDocumento;
	private String observacion;
	
	// INICIO PERMISOS PARA EL MODULO -=CRAMIREZ=-
		private List<SimpleMenuDTO> listPermisosSubmenu = new ArrayList<SimpleMenuDTO>();
		private boolean permisoEliminar;
		private boolean permisoInactivar;
	// FIN PERMISOS PARA EL MODULO	
	
	public EliminacionListaDJPredioManaged(){
		
	}
	@PostConstruct
	public void init(){
		permisosMenu();
		try{
			records=new ArrayList<ListRpDjPredial>(); 
			findRpDjPredial=(FindRpDjPredial)getSessionMap().get("findRpDjPredial");
			
			Integer PredioId=(Integer)FacesUtil.getSessionMapValue(FacesContext.getCurrentInstance(), "NextPredioId");
			if(PredioId!=null){				
				mostrarData(PredioId);
			}else{
				WebMessages.messageError("Debe seleccionar el predio");
			}
			
			lstTipoDocumento = new ArrayList<SelectItem>();		
			
			List<GnTipoDocumento> list = registroPrediosBo.obtenerTipoDocumentos();
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
			int submenuId = Constante.ELIMINACION_DDJJ;
			
			int permisoEliminarId = Constante.ELIMINAR;
			int permisoVerInactivarId = Constante.INACTIVAR;
			
			permisoEliminar = false;
			permisoInactivar = false;
			
			listPermisosSubmenu  = menuBo.getAccesosSubmenuUsuario( getSessionManaged().getUsuarioLogIn().getUsuarioId() , submenuId);
			
			Iterator<SimpleMenuDTO> menuIterar = listPermisosSubmenu.iterator();
			while (menuIterar.hasNext()) {
				SimpleMenuDTO lsm = menuIterar.next();
				if(lsm.getItemId() == permisoEliminarId) {
					permisoEliminar = true;
				}
				if(lsm.getItemId() == permisoVerInactivarId) {
					permisoInactivar = true;
				}
			}
			
		} catch (SisatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String salir(){
		FacesUtil.closeSession("EliminacionListaDJPredioManaged");
		return sendRedirectPrincipal();
	}
	
	public void seleccionaDatos(){
		try{
			if(currentItem!=null){
			//	FacesUtil.closeSession("registroPredioManaged");
				
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
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
	
	public Integer getNewConstruccionId(Integer construccionId,ArrayList<RpDjconstruccion> listaConstruccion){
		Integer NewConstruccionId=0;
		for(int i=0;i<listaConstruccion.size();i++){
			if(construccionId.equals(listaConstruccion.get(i).getConstruccionId())){
				NewConstruccionId=listaConstruccion.get(i).getNewConstruccionId();
			}
		}
		return NewConstruccionId;
	}
	
	public void vacio(){
		
		System.out.println("vacio");
	}
	
	public void mostrarData(Integer predioId){	
		
		try {
			records=registroPrediosBo.getListRpDjpredialTodos(predioId,findRpDjPredial.getPersonaId());
			if(records.size()>0){
				findRpDjPredial.setMotivoDeclaracionId(String.valueOf(records.get(0).getMotivoDeclaracionId()));
				setCodigoPredio(String.valueOf(records.get(0).getPredioId()));
			}
			setCodigoPredio(String.valueOf(predioId));
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		
		
	}
	
	public void eliminarDJ(){

		//System.out.println("se cambiara de estado a eliminado: dj:"+ dj_id +" anio: "+ dj_anio +" predio: "+ predio_id +" estado: "+ dj_estado + " motivoDj: "+ motivo_declaracion_id);		
		try {		
			//Eliminar DJ
			//registroPrediosBo.eliminarDJPredio(dj_id, dj_anio, predio_id, motivo_declaracion_id, dj_estado);
						
			String glosa ="Doc: "+ tipoDocumento + ", No: "+nroDocumento+", F: "+DateUtil.convertDateToString(fechaDocumento)+", Obsv: "+observacion+". Usuario: "+djPredial.getUsuario();
			djPredial.setGlosa(glosa);
			
			registroPrediosBo.eliminarDJPredio(djPredial,findRpDjPredial.getPersonaId());
			
			//Refrescar data
			mostrarData(new Integer(predioId));			
			
		} catch (SisatException e) {			
			WebMessages.messageError(e.getMessage());
		}
	}
	
	public void inactivarDJ(){		
		try {		
			//Inactivar DJ
			String glosa = "Obsv: "+observacion+", Doc: "+ tipoDocumento + ", No: "+nroDocumento+", F: "+DateUtil.convertDateToString(fechaDocumento)+". Usuario: "+djPredial.getUsuario();
			djPredial.setGlosa(glosa);
			
			registroPrediosBo.inactivarDJPredio(djPredial,findRpDjPredial.getPersonaId());
			
			//Refrescar data
			mostrarData(new Integer(predioId));			
			
		} catch (SisatException e) {			
			WebMessages.messageError(e.getMessage());
		}
	}
	
	//caltamirano:fin
	
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
	public ListRpDjPredial getDjPredial() {
		return djPredial;
	}
	public void setDjPredial(ListRpDjPredial djPredial) {
		this.djPredial = djPredial;
	}
	public int getDjId() {
		return djId;
	}
	public void setDjId(int djId) {
		this.djId = djId;
	}
	public int getPredioId() {
		return predioId;
	}
	public void setPredioId(int predioId) {
		this.predioId = predioId;
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
	public boolean isPermisoEliminar() {
		return permisoEliminar;
	}
	public void setPermisoEliminar(boolean permisoEliminar) {
		this.permisoEliminar = permisoEliminar;
	}
	public boolean isPermisoInactivar() {
		return permisoInactivar;
	}
	public void setPermisoInactivar(boolean permisoInactivar) {
		this.permisoInactivar = permisoInactivar;
	}	

	
}
