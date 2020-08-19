package com.sat.sisat.coactiva.managed;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import com.sat.sisat.cobranzacoactiva.business.CobranzaCoactivaBoRemote;
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
public class ListarDeudaExigibleManaged extends BaseManaged {

	@EJB
	PersonaBoRemote personaBo;

	@EJB
	GeneralBoRemote generalBo;

	@EJB
	ControlyCobranzaBoRemote controlycobranzaBo;

	@EJB
	CobranzaCoactivaBoRemote ccobranzaCoactivaBo;
	
	@EJB
	MenuBoRemote menuBo;
	
	private List<FindCcLote> lstFindLote;
	private FindCcLote findCcLoteItem = new FindCcLote();
	private Integer nroLote = null;
	private java.util.Date fechaRegistro;
	private String estadoLote;

	private Integer periodoDeuda;
	
	// INICIO PERMISOS PARA EL MODULO -=CRAMIREZ=-
	 	private List<SimpleMenuDTO> listPermisosSubmenu = new ArrayList<SimpleMenuDTO>();
	 	private boolean permisoBuscar;
	 	private boolean permisoAgregarRegistrar;
	 	private boolean permisoVerDetalle;
	 	private boolean permisoEliminar;
	 // FIN PERMISOS PARA EL MODULO
	 		
	
	@PostConstruct
	public void init() {
		permisosMenu();
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}

	}
	
	public void permisosMenu() {	
		try {
			int submenuId = Constante.GENERAR_DEUDA_EXIGIBLE;
			
			int permisoBuscarId = Constante.BUSCAR;
			int permisopermisoAgregarRegistrarId = Constante.AGREGAR_REGISTRAR;
			int permisoVerDetalleId = Constante.VER_DETALLE;
			int permisoEliminarId = Constante.ELIMINAR;
			
			permisoBuscar = false;
			permisoAgregarRegistrar = false;
			permisoVerDetalle = false;
			permisoEliminar = false;
			
			listPermisosSubmenu  = menuBo.getAccesosSubmenuUsuario( getSessionManaged().getUsuarioLogIn().getUsuarioId() , submenuId);
			
			Iterator<SimpleMenuDTO> menuIterar = listPermisosSubmenu.iterator();
			while (menuIterar.hasNext()) {
				SimpleMenuDTO lsm = menuIterar.next();
				if(lsm.getItemId() == permisoBuscarId) {
					permisoBuscar = true;
				}
				if(lsm.getItemId() == permisopermisoAgregarRegistrarId) {
					permisoAgregarRegistrar = true;
				}
				if(lsm.getItemId() == permisoVerDetalleId) {
					permisoVerDetalle = true;
				}
				if(lsm.getItemId() == permisoEliminarId) {
					permisoEliminar = true;
				}
			}
		} catch (SisatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	public void buscar(){
		try{
		    lstFindLote = ccobranzaCoactivaBo.getAllFindCcLoteDeudaExigible(nroLote,periodoDeuda);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String nuevo(){
		try{
			getSessionMap().put("findCcLoteDeItem", null);
			getSessionMap().remove("findCcLoteDeItem");
		}catch(Exception e){
			e.printStackTrace();
		}
		return sendRedirectPrincipal();
	}
	
	public void loadEstadoById(ValueChangeEvent event) {
		try {
			estadoLote = (String) event.getNewValue();
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void limpiar() {
		estadoLote = "";
		nroLote = 0;
		fechaRegistro = null;
	}

	public String edit() {
		try {
			getSessionMap().put("findCcLoteDeItem", findCcLoteItem);
		} catch (Exception e) {
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

	public Integer getNroLote() {
		return nroLote;
	}

	public void setNroLote(Integer nroLote) {
		this.nroLote = nroLote;
	}

	public java.util.Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(java.util.Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getEstadoLote() {
		return estadoLote;
	}

	public void setEstadoLote(String estadoLote) {
		this.estadoLote = estadoLote;
	}
	
	public Integer getPeriodoDeuda() {
		return periodoDeuda;
	}

	public void setPeriodoDeuda(Integer periodoDeuda) {
		this.periodoDeuda = periodoDeuda;
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

	public boolean isPermisoEliminar() {
		return permisoEliminar;
	}

	public void setPermisoEliminar(boolean permisoEliminar) {
		this.permisoEliminar = permisoEliminar;
	}
	
}
