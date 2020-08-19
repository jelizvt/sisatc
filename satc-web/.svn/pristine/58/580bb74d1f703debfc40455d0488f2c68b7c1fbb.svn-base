package com.sat.sisat.controlycobranza.managed;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sat.sisat.common.business.GeneralBoRemote;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.FacesUtil;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.controlycobranza.business.ControlyCobranzaBoRemote;
import com.sat.sisat.controlycobranza.dto.FindCcLote;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.menus.business.MenuBoRemote;
import com.sat.sisat.menus.dto.SimpleMenuDTO;
import com.sat.sisat.persona.business.PersonaBoRemote;

@ManagedBean
@ViewScoped
public class BusquedaLoteOrdinarioContribManaged extends BaseManaged {
	@EJB
	PersonaBoRemote personaBo;
	
	@EJB
	GeneralBoRemote generalBo;
	
	@EJB
	ControlyCobranzaBoRemote controlycobranzaBo;
	
	@EJB
	MenuBoRemote menuBo; 
	
	private List<FindCcLote> lstFindLote;
	private FindCcLote findCcLoteItem= new FindCcLote();
	
    private Integer loteId=null;
    private Integer personaId=null;
    
    
 // INICIO PERMISOS PARA EL MODULO -=CRAMIREZ=-
 		private List<SimpleMenuDTO> listPermisosSubmenu = new ArrayList<SimpleMenuDTO>();
 		private boolean permisoAgregarRegistrar;
 		private boolean permisoBuscar;
 		private boolean permisoVerDetalle;
 	// FIN PERMISOS PARA EL MODULO
    
	@PostConstruct
	public void init() {
			permisosMenu();
			try {
				Integer personaIdmantenerLista = (Integer) getSessionMap().get("personaIdmantenerLista");
				if (personaIdmantenerLista != null) {
					lstFindLote = controlycobranzaBo.getAllFindCcLoteContrib(0, personaIdmantenerLista,Constante.TIPO_COBRANZA_ORDINARIO);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
	public void permisosMenu() {	
		try {
			int submenuId = Constante.RESOL_DETERMINACION_PREDIAL;
			
			int permisoAgregarRegistrarId = Constante.AGREGAR_REGISTRAR;
			int permisoBuscarId = Constante.BUSCAR;
			int permisoVerDetalleId = Constante.VER_DETALLE;
			
			permisoAgregarRegistrar = false;
			permisoBuscar = false;
			permisoVerDetalle = false;
			
			listPermisosSubmenu  = menuBo.getAccesosSubmenuUsuario( getSessionManaged().getUsuarioLogIn().getUsuarioId() , submenuId);
			
			Iterator<SimpleMenuDTO> menuIterar = listPermisosSubmenu.iterator();
			while (menuIterar.hasNext()) {
				SimpleMenuDTO lsm = menuIterar.next();
				if(lsm.getItemId() == permisoAgregarRegistrarId) {
					permisoAgregarRegistrar = true;
				}
				if(lsm.getItemId() == permisoBuscarId) {
					permisoBuscar = true;
				}
				if(lsm.getItemId() == permisoVerDetalleId) {
					permisoVerDetalle = true;
				}
			}
		} catch (SisatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}
	}
    public String registroNuevoLote(){
		FacesUtil.closeSession("registroLoteOrdinarioManaged");
		getSessionMap().remove("findCcLoteItem");
		return sendRedirectPrincipal();
	}
    
    public String registroNuevoLoteContrib(){
    	FacesUtil.closeSession("registroLoteOrdinarioContribManaged");
		getSessionMap().remove("findCcLoteItem");
		return sendRedirectPrincipal();
    }
    
    public void limpiar(){
    	loteId=Constante.RESULT_PENDING;
    	personaId=Constante.RESULT_PENDING;
    	lstFindLote.clear();
    	getSessionMap().remove("personaIdmantenerLista");
    }
    
	public String edit(){
		try{
			FacesUtil.closeSession("registroLoteOrdinarioManaged");
			getSessionMap().put("findCcLoteItem", findCcLoteItem);
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		return sendRedirectPrincipal();
	}

	public void buscar(){
		try{
			if(loteId != null||personaId!= null){
				lstFindLote=controlycobranzaBo.getAllFindCcLoteContrib(loteId,personaId,Constante.TIPO_COBRANZA_ORDINARIO);
				getSessionMap().put("personaIdmantenerLista", personaId);
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public List<FindCcLote> getLstFindLote() {
		return lstFindLote;
	}

	public void setLstFindLote(List<FindCcLote> lstFindLote) {
		this.lstFindLote = lstFindLote;
	}

	public FindCcLote getFindCcLoteItem() {
		return findCcLoteItem;
	}
	public void setFindCcLoteItem(FindCcLote findCcLoteItem) {
		this.findCcLoteItem = findCcLoteItem;
	}
	
	public Integer getPersonaId() {
		return personaId;
	}

	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}
	
	public Integer getLoteId() {
		return loteId;
	}

	public void setLoteId(Integer loteId) {
		this.loteId = loteId;
	}


	public List<SimpleMenuDTO> getListPermisosSubmenu() {
		return listPermisosSubmenu;
	}


	public void setListPermisosSubmenu(List<SimpleMenuDTO> listPermisosSubmenu) {
		this.listPermisosSubmenu = listPermisosSubmenu;
	}


	public boolean isPermisoAgregarRegistrar() {
		return permisoAgregarRegistrar;
	}


	public void setPermisoAgregarRegistrar(boolean permisoAgregarRegistrar) {
		this.permisoAgregarRegistrar = permisoAgregarRegistrar;
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
	
	
}
