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
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.controlycobranza.business.ControlyCobranzaBoRemote;
import com.sat.sisat.controlycobranza.dto.FindCcLote;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.menus.business.MenuBoRemote;
import com.sat.sisat.menus.dto.SimpleMenuDTO;
import com.sat.sisat.persona.business.PersonaBoRemote;

@ManagedBean
@ViewScoped
public class BusquedaRsNoPecuniariaManaged  extends BaseManaged {

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
    private String nroLote=null;
    
    // INICIO PERMISOS PARA EL MODULO -=CRAMIREZ=-
  		private List<SimpleMenuDTO> listPermisosSubmenu = new ArrayList<SimpleMenuDTO>();
  		private boolean permisoAgregarRegistrar;
  		private boolean permisoBuscar;
  		private boolean permisoModificarActualizar;
  	// FIN PERMISOS PARA EL MODULO
    @PostConstruct
	public void init() {
    	permisosMenu();
		try{
			lstFindLote=controlycobranzaBo.getAllFindCcLoteRsNoPecuniaria(nroLote);
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
		
	}
    
    public void permisosMenu() {	
		try {
			int submenuId = Constante.RS_NO_PECUNIARIAS;
			
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
    
    
    public void limpiar(){
    	nroLote="";
    }
    
	public String edit() {
		try {
			getSessionMap().put("findCcLoteItemRsNopec", findCcLoteItem);
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		return sendRedirectPrincipal();
	}

	public void buscar() {
		try {
			String[] lote = nroLote.split("-");
			nroLote = "";
			for (int i = 0; i < lote.length; i++) {
				nroLote = nroLote + lote[i];
			}
			lstFindLote = controlycobranzaBo
					.getAllFindCcLoteRsNoPecuniaria(nroLote);
		} catch (Exception e) {
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
	public String getNroLote() {
		return nroLote;
	}
	public void setNroLote(String nroLote) {
		this.nroLote = nroLote;
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

