package com.sat.sisat.controlycobranza.managed;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

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
public class BusquedaLoteRDArbitriosManaged  extends BaseManaged {

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
    private Integer loteId;
	private Integer periodo;
	
	// INICIO PERMISOS PARA EL MODULO -=CRAMIREZ=-
 		private List<SimpleMenuDTO> listPermisosSubmenu = new ArrayList<SimpleMenuDTO>();
 		private boolean permisoAgregarRegistrar;
 		private boolean permisoBuscar;
 		private boolean permisoModificarActualizar;
 	// FIN PERMISOS PARA EL MODULO
	
	@PostConstruct
	public void init() {
		permisosMenu();
	}
	
	public void permisosMenu() {	
		try {
			int submenuId = Constante.RESOL_DETERMINACION_ARBITRIOS;
			
			int permisoAgregarRegistrarId = Constante.AGREGAR_REGISTRAR;
			int permisoBuscarId = Constante.BUSCAR;
			int permisoModificarActualizarId = Constante.MODIFICAR_ACTUALIZAR;
			
			permisoAgregarRegistrar = false;
			permisoBuscar = false;
			permisoModificarActualizar = false;
			
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
				if(lsm.getItemId() == permisoModificarActualizarId) {
					permisoModificarActualizar = true;
				}
			}
		} catch (SisatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}
	}
    
    public void loadEstadoById(ValueChangeEvent event) {
		
    }
    
    public void limpiar(){
    	
    }
    
	public void buscar(){
		try{
			
			lstFindLote=controlycobranzaBo.getAllFindCcLoteRDArbitrios(loteId,periodo,Constante.TIPO_COBRANZA_ORDINARIO);
			
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public String registroNuevoLote(){
    	FacesUtil.closeSession("registroLoteRDArbitriosManaged");
		getSessionMap().remove("findCcLoteItem");
		return sendRedirectPrincipal();
	}
	
	public String edit(){
		try{
			FacesUtil.closeSession("registroLoteRDArbitriosManaged");
			getSessionMap().put("findCcLoteItem", findCcLoteItem);
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		return sendRedirectPrincipal();
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
	
	public Integer getPeriodo() {
		return periodo;
	}
	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
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

	public boolean isPermisoModificarActualizar() {
		return permisoModificarActualizar;
	}

	public void setPermisoModificarActualizar(boolean permisoModificarActualizar) {
		this.permisoModificarActualizar = permisoModificarActualizar;
	}
	
}

