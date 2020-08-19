package com.sat.sisat.papeletas.managed;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.richfaces.component.html.HtmlComboBox;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.FacesUtil;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.menus.business.MenuBoRemote;
import com.sat.sisat.menus.dto.SimpleMenuDTO;
import com.sat.sisat.papeleta.business.PapeletaBoRemote;
import com.sat.sisat.papeleta.dto.FindPapeletas;
import com.sat.sisat.persistence.entity.MpTipoDocuIdentidad;
import com.sat.sisat.persistence.entity.PaInfraccion;
import com.sat.sisat.persona.business.PersonaBoRemote;
import com.sat.sisat.vehicular.cns.ReusoFormCns;
import com.sat.sisat.vehicular.dto.BuscarPersonaDTO;

@ManagedBean
@ViewScoped
public class BuscarPapeletasManaged extends BaseManaged {
	
	@EJB
	PapeletaBoRemote papeletaBo;
	
	@EJB
	PersonaBoRemote personaBo;
	
	@EJB
	private MenuBoRemote menuBo;
	
	private String infractor;
	private String propietario;
	private HtmlComboBox cmbLey;
	private HtmlComboBox cmbInfraccion;
	private List<SelectItem> lstLey = new ArrayList<SelectItem>();
	private List<SelectItem> lstInfraccion = new ArrayList<SelectItem>();
	private String placa;
	private String nrolicencia;
	private List<FindPapeletas> records = new ArrayList<FindPapeletas>();
	
	private Map<String, Integer> mapLey = new HashMap<String, Integer>();
	private Map<String,Integer> mapInfraccion = new HashMap<String,Integer>();
	private Map<Integer,String> mapIInfraccion= new HashMap<Integer,String>();
	private FindPapeletas findPapeleta = new FindPapeletas();
	private String cmbInfraccionValue;
	private String ley;
	private PaInfraccion infraccion;
	private FindPapeletas currentItem;
	private String numeroPapeleta;

	//--
	private String cmbValuetipodocumento;
	private HtmlComboBox cmbtipodocumento;
	private List<SelectItem> lsttipodocumento=new ArrayList<SelectItem>();
	private Boolean isDNI=Boolean.FALSE;
	private Boolean isRUC=Boolean.FALSE;
	private String nroDocumentoIdentidad;
	private Integer tipodocumentoIdent;
	private HashMap<String, Integer> mapRpTipodocumento=new HashMap<String, Integer>();
	List<MpTipoDocuIdentidad> lMpTipoDocuIdentidad = null;
	//--
	private BuscarPersonaDTO datosInfractor;
	private Integer personaId;

	// INICIO PERMISOS PARA EL MODULO -=CRAMIREZ=-
		private List<SimpleMenuDTO> listPermisosSubmenu = new ArrayList<SimpleMenuDTO>();
		private boolean permisoBuscar;
		private boolean permisoAgregarRegistrar;
		private boolean permisoModificarActualizar;
	// FIN PERMISOS PARA EL MODULO
		
	public BuscarPapeletasManaged() throws Exception {
		
	}
	
	@PostConstruct
	public void init(){
		permisosMenu();
		try{
			cargaInfracciones();
			
			List<MpTipoDocuIdentidad> lstMpTipoDocuIdentidad=personaBo.getAllMpTipoDocumento();
			Iterator<MpTipoDocuIdentidad> it1 = lstMpTipoDocuIdentidad.iterator();  
			lsttipodocumento=new ArrayList<SelectItem>();
			 
	        while (it1.hasNext()){
	        	MpTipoDocuIdentidad obj = it1.next();  
	        	lsttipodocumento.add(new SelectItem(obj.getDescrpcionCorta(),String.valueOf(obj.getTipoDocIdentidadId())));  
	        	mapRpTipodocumento.put(obj.getDescrpcionCorta().trim(), obj.getTipoDocIdentidadId());
	        }
	        
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void permisosMenu() {	
		try {
			int submenuId = Constante.REGISTRO_PAPELETA;
			
			int permisoBuscarId = Constante.BUSCAR;
			int permisoAgregarRegistrarId = Constante.AGREGAR_REGISTRAR;
			int permisoModificarActualizarId = Constante.MODIFICAR_ACTUALIZAR;
			
			permisoBuscar = false;
			permisoAgregarRegistrar = false;
			permisoModificarActualizar = false;
			
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
			}
			
		} catch (SisatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public void buscar() throws Exception{
		try{
			findPapeleta = new FindPapeletas();
			if(datosInfractor!=null&&datosInfractor.getPersonaId()!=null&&datosInfractor.getPersonaId()>Constante.RESULT_PENDING){
				findPapeleta.setInfractorId(datosInfractor.getPersonaId());
				records = papeletaBo.buscarPapeletasCriteria(findPapeleta);
			}else if(getPersonaId()!=null&&getPersonaId()>Constante.RESULT_PENDING){
				findPapeleta.setInfractorId(getPersonaId());
				records = papeletaBo.buscarPapeletasCriteria(findPapeleta);
			}else if(getPlaca()!=null && !getPlaca().trim().equals("")){
				findPapeleta.setPlaca(getPlaca());
				records = papeletaBo.buscarPapeletasCriteria(findPapeleta);
			}else if(getNumeroPapeleta()!=null && !getNumeroPapeleta().isEmpty()){
				findPapeleta.setNumPapeleta(getNumeroPapeleta());
				records = papeletaBo.buscarPapeletasCriteria(findPapeleta);
			}else if(getCmbValuetipodocumento()!=null && !getCmbValuetipodocumento().isEmpty()){
				if(getNroDocumentoIdentidad()!=null && !getNroDocumentoIdentidad().isEmpty()){
					findPapeleta.setTipoDocumentoId(mapRpTipodocumento.get(getCmbValuetipodocumento()));	
					findPapeleta.setNumeroDocumento(getNroDocumentoIdentidad());
					records = papeletaBo.buscarPapeletasCriteria(findPapeleta);
				}
			}else if(getNrolicencia()!=null && !getNrolicencia().isEmpty()){
				findPapeleta.setNumeroLicencia(getNrolicencia());
				records = papeletaBo.buscarPapeletasCriteria(findPapeleta);
			}	
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String actualizacion(){
		if(currentItem!=null){
			getSessionMap().put("papeletaId", currentItem.getPapeletaId());
			getSessionMap().put("pageReturn", "/sisat/papeletas/buscarpapeletas.xhtml");
			getSessionMap().put("estadoPapeleta", currentItem.getEstado());
			getSessionMap().put("findPapeleta", findPapeleta);
		}
		closeSession("registroPapeletasManaged");
		return sendRedirectPrincipal();
	}
	
	public void limpiar()throws Exception{
		records=new ArrayList<FindPapeletas>();
		currentItem = new FindPapeletas();
		
		setNumeroPapeleta("");
		setPlaca("");
		setNrolicencia("");
		setCmbInfraccionValue("");
		setLey("");
		setCmbValuetipodocumento("");
		setNroDocumentoIdentidad("");

		datosInfractor=new BuscarPersonaDTO();
	}
	public void descargar(ActionEvent ev) {
		//records=new ArrayList<FindPapeletas>();
		
		try {
			UIComponent comp = ev.getComponent().getParent().getParent();
			if (comp != null) {
				UIData uiData = (UIData) comp;
				FindPapeletas adv = (FindPapeletas) uiData.getRowData();		
				String estado=adv.getEstado();
				if(estado.equals("3")){
					papeletaBo.cambiarEstadoPapeleta(adv.getPapeletaId());
					records=papeletaBo.buscarPapeletasCriteria(findPapeleta);
				}else{
					//prueba
				}
			}
		} catch (Exception ex) {
			// TODO : Controller exception
		}
		
		
		
		
		
	}
	
	public void loadTipoTipoDocumentoById(ValueChangeEvent event) {
		try{
			setIsDNI(Boolean.FALSE);
			setIsRUC(Boolean.FALSE);
			String value=(String)event.getNewValue();
			if(value!=null){
				tipodocumentoIdent = (Integer)mapRpTipodocumento.get(value);
				if(tipodocumentoIdent!=null){
					lMpTipoDocuIdentidad=personaBo.findMpTipoDocuIdentidad(tipodocumentoIdent);	
				}
				if(value.compareTo(Constante.TIPO_DOCUMENTO_DNI)==0){
					setIsDNI(Boolean.TRUE);
					setIsRUC(Boolean.FALSE);
				}
				if(value.compareTo(Constante.TIPO_DOCUMENTO_RUC)==0){
					setIsDNI(Boolean.FALSE);
					setIsRUC(Boolean.TRUE);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
    }
	
	public String inscripcion(){
		getSessionMap().put("papeletaId", Constante.RESULT_PENDING);
		getSessionMap().put("pageReturn", "/sisat/papeletas/buscarpapeletas.xhtml");
		getSessionMap().put("estadoPapeleta", Constante.ESTADO_PAPELETA_DEFINITIVO);
		closeSession("registroPapeletasManaged");
		return sendRedirectPrincipal();
	}
	
	
	
	public void cargaInfracciones(){
		try{
			infraccion = new PaInfraccion();
			Integer codLey = new Integer(1);//Ley N 27181 Ley General de Transporte y Tr�nsito Terrestre
			if(codLey!=null ){
				infraccion.setLeyId(codLey);
				Calendar cal=Calendar.getInstance();
				List<PaInfraccion> lstPaInfraccion  = papeletaBo.getAllInfracciones(infraccion,DateUtil.convertDateToString(cal.getTime()));
				Iterator<PaInfraccion> it8 = lstPaInfraccion.iterator();
				lstInfraccion = new ArrayList<SelectItem>();
				while(it8.hasNext())  {
					PaInfraccion obj = it8.next();
					lstInfraccion.add(new SelectItem(obj.getDescripcionCorta().trim(), String.valueOf(obj.getInfraccionId()) ));
					mapInfraccion.put(obj.getDescripcionCorta().trim(),obj.getInfraccionId());
					mapIInfraccion.put(obj.getInfraccionId(),obj.getDescripcionCorta().trim());
				}
			}
		}catch(Exception exc){
			exc.printStackTrace();
			WebMessages.messageFatal(exc);
		}
	}
	
	public void copiaPersona(BuscarPersonaDTO persona){
		setDatosInfractor(persona);
	}
	
	public void setPersonaPapeleta(){
		String destinoRefresh = FacesUtil.getRequestParameter("destinoRefresh");
		
		BuscarPersonaPapeletasManaged buscarPersonaManaged=(BuscarPersonaPapeletasManaged)getManaged("buscarPersonaPapeletasManaged");
		buscarPersonaManaged.setPantallaUso(ReusoFormCns.BUSQU_INFR_PAPELETAS);
		buscarPersonaManaged.setDestinoRefresh(destinoRefresh);
	}
	
	public PapeletaBoRemote getPapeletaBo() {
		return papeletaBo;
	}

	public void setPapeletaBo(PapeletaBoRemote papeletaBo) {
		this.papeletaBo = papeletaBo;
	}

	public String getInfractor() {
		return infractor;
	}

	public void setInfractor(String infractor) {
		this.infractor = infractor;
	}

	public String getPropietario() {
		return propietario;
	}

	public void setPropietario(String propietario) {
		this.propietario = propietario;
	}

	public HtmlComboBox getCmbLey() {
		return cmbLey;
	}

	public void setCmbLey(HtmlComboBox cmbLey) {
		this.cmbLey = cmbLey;
	}

	public HtmlComboBox getCmbInfraccion() {
		return cmbInfraccion;
	}

	public void setCmbInfraccion(HtmlComboBox cmbInfraccion) {
		this.cmbInfraccion = cmbInfraccion;
	}

	public List<SelectItem> getLstLey() {
		return lstLey;
	}

	public void setLstLey(List<SelectItem> lstLey) {
		this.lstLey = lstLey;
	}

	public List<SelectItem> getLstInfraccion() {
		return lstInfraccion;
	}

	public void setLstInfraccion(List<SelectItem> lstInfraccion) {
		this.lstInfraccion = lstInfraccion;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public List<FindPapeletas> getRecords() {
		return records;
	}

	public void setRecords(List<FindPapeletas> records) {
		this.records = records;
	}

	public Map<String, Integer> getMapLey() {
		return mapLey;
	}

	public void setMapLey(Map<String, Integer> mapLey) {
		this.mapLey = mapLey;
	}

	public Map<String, Integer> getMapInfraccion() {
		return mapInfraccion;
	}

	public void setMapInfraccion(Map<String, Integer> mapInfraccion) {
		this.mapInfraccion = mapInfraccion;
	}

	public FindPapeletas getFindPapeleta() {
		return findPapeleta;
	}

	public void setFindPapeleta(FindPapeletas findPapeleta) {
		this.findPapeleta = findPapeleta;
	}

	public String getCmbInfraccionValue() {
		return cmbInfraccionValue;
	}

	public void setCmbInfraccionValue(String cmbInfraccionValue) {
		this.cmbInfraccionValue = cmbInfraccionValue;
	}

	public String getLey() {
		return ley;
	}

	public void setLey(String ley) {
		this.ley = ley;
	}

	public PaInfraccion getInfraccion() {
		return infraccion;
	}

	public void setInfraccion(PaInfraccion infraccion) {
		this.infraccion = infraccion;
	}

	public FindPapeletas getCurrentItem() {
		return currentItem;
	}

	public void setCurrentItem(FindPapeletas currentItem) {
		this.currentItem = currentItem;
	}

	public String getNumeroPapeleta() {
		return numeroPapeleta;
	}

	public void setNumeroPapeleta(String numeroPapeleta) {
		this.numeroPapeleta = numeroPapeleta;
	}

	public Map<Integer, String> getMapIInfraccion() {
		return mapIInfraccion;
	}

	public void setMapIInfraccion(Map<Integer, String> mapIInfraccion) {
		this.mapIInfraccion = mapIInfraccion;
	}

	public String getCmbValuetipodocumento() {
		return cmbValuetipodocumento;
	}

	public void setCmbValuetipodocumento(String cmbValuetipodocumento) {
		this.cmbValuetipodocumento = cmbValuetipodocumento;
	}

	public HtmlComboBox getCmbtipodocumento() {
		return cmbtipodocumento;
	}

	public void setCmbtipodocumento(HtmlComboBox cmbtipodocumento) {
		this.cmbtipodocumento = cmbtipodocumento;
	}

	public List<SelectItem> getLsttipodocumento() {
		return lsttipodocumento;
	}

	public void setLsttipodocumento(List<SelectItem> lsttipodocumento) {
		this.lsttipodocumento = lsttipodocumento;
	}

	public Boolean getIsDNI() {
		return isDNI;
	}

	public void setIsDNI(Boolean isDNI) {
		this.isDNI = isDNI;
	}

	public Boolean getIsRUC() {
		return isRUC;
	}

	public void setIsRUC(Boolean isRUC) {
		this.isRUC = isRUC;
	}

	public String getNroDocumentoIdentidad() {
		return nroDocumentoIdentidad;
	}

	public void setNroDocumentoIdentidad(String nroDocumentoIdentidad) {
		this.nroDocumentoIdentidad = nroDocumentoIdentidad;
	}

	public Integer getTipodocumentoIdent() {
		return tipodocumentoIdent;
	}

	public void setTipodocumentoIdent(Integer tipodocumentoIdent) {
		this.tipodocumentoIdent = tipodocumentoIdent;
	}

	public HashMap<String, Integer> getMapRpTipodocumento() {
		return mapRpTipodocumento;
	}

	public void setMapRpTipodocumento(HashMap<String, Integer> mapRpTipodocumento) {
		this.mapRpTipodocumento = mapRpTipodocumento;
	}

	public List<MpTipoDocuIdentidad> getlMpTipoDocuIdentidad() {
		return lMpTipoDocuIdentidad;
	}

	public void setlMpTipoDocuIdentidad(List<MpTipoDocuIdentidad> lMpTipoDocuIdentidad) {
		this.lMpTipoDocuIdentidad = lMpTipoDocuIdentidad;
	}
	
	public String getNrolicencia() {
		return nrolicencia;
	}

	public void setNrolicencia(String nrolicencia) {
		this.nrolicencia = nrolicencia;
	}
	public BuscarPersonaDTO getDatosInfractor() {
		return datosInfractor;
	}
	public void setDatosInfractor(BuscarPersonaDTO datosInfractor) {
		this.datosInfractor = datosInfractor;
	}

	public Integer getPersonaId() {
		return personaId;
	}

	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
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

}
