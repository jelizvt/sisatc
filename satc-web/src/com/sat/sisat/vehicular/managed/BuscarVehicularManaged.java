package com.sat.sisat.vehicular.managed;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.event.ActionEvent;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.menus.business.MenuBoRemote;
import com.sat.sisat.menus.dto.SimpleMenuDTO;
import com.sat.sisat.vehicular.business.VehicularBoRemote;
import com.sat.sisat.vehicular.cns.VehicularCns;
import com.sat.sisat.vehicular.dto.BuscarVehicularDTO;

@ManagedBean
@ViewScoped
public class BuscarVehicularManaged extends BaseManaged {

	private static final long serialVersionUID = 8838158610430298358L;

	@EJB
	VehicularBoRemote vehicularBo;
	
	@EJB
	MenuBoRemote menuBo;

	private boolean contribuyenteNull = false;

	private String placaBusc;
	private List<BuscarVehicularDTO> lstDjVehicular = new ArrayList<BuscarVehicularDTO>();
	
	// INICIO PERMISOS PARA EL MODULO -=CRAMIREZ=-
	//DECLARACION
		private List<SimpleMenuDTO> listPermisosSubmenu = new ArrayList<SimpleMenuDTO>();
		private List<SimpleMenuDTO> listPermisosEliminarSubmenu = new ArrayList<SimpleMenuDTO>();
		private boolean permisoAgregarRegistrar;
		private boolean permisoBuscar;
		private boolean permisoVerDetalle;
	// ELIMINAR
		private boolean permisoBuscarEliminar;
		private boolean permisoEliminar;
	// FIN PERMISOS PARA EL MODULO

	public void setLstDjVehicular(List<BuscarVehicularDTO> lstDjVehicular) {
		this.lstDjVehicular = lstDjVehicular;
	}

	public BuscarVehicularManaged() {
		getSessionManaged().setLinkRegresar("/sisat/persona/detallepersona.xhtml");
	}

	//INICIO IVO
	private int desactiva;
	
	public int getDesactiva() {
		return desactiva;
	}

	public void setDesactiva(int desactiva) {
		this.desactiva = desactiva;
	}
	
	private String codigoContri;
	
	private String placa;
	
	private String nomApe;
	
	private String razon;
	
	private String tipoDocumento;
	
	private String nroDocumento;
	
	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNroDocumento() {
		return nroDocumento;
	}

	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}

	public String getRazon() {
		return razon;
	}

	public void setRazon(String razon) {
		this.razon = razon.trim();
	}

	public String getNomApe() {
		return nomApe;
	}

	public void setNomApe(String nomApe) {
		this.nomApe = nomApe.trim();
	}

	public String getCodigoContri() {
		return codigoContri;
	}

	public void setCodigoContri(String codigoContri) {
		this.codigoContri = codigoContri;
	}
	
	
	//FIN IVO

	@PostConstruct
	public void init() {
		permisosMenu();
		try {
			setLstDjVehicular(null);
		
			if (getSessionManaged() != null) {
				if (getSessionManaged().getFinMpPersonaItem() == null) {
					contribuyenteNull = true;
				} else {
					//LISTA LOS VEHICULOS
					mostrarTodasDeclaraciones(null);

				}
			}

		} catch (Exception ex) {
			// TODO : Controller exception
		}
	}
	
	public void permisosMenu() {	
		try {
			int submenuId = Constante.DECLARACION_JURADA_VEHICULAR;
			
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
			
			int submenuEliminarId = Constante.ELIMINACION_DDJJ_VEHICULAR;
			
			int permisoEliminarId = Constante.ELIMINAR;
			
			permisoBuscarEliminar = false;
			permisoEliminar = false;
			
			listPermisosEliminarSubmenu  = menuBo.getAccesosSubmenuUsuario( getSessionManaged().getUsuarioLogIn().getUsuarioId() , submenuEliminarId);
			
			Iterator<SimpleMenuDTO> menuEliminarIterar = listPermisosEliminarSubmenu.iterator();
			while (menuEliminarIterar.hasNext()) {
				SimpleMenuDTO lsmE = menuEliminarIterar.next();
				if(lsmE.getItemId() == permisoBuscarId) {
					permisoBuscarEliminar = true;
				}
				if(lsmE.getItemId() == permisoEliminarId) {
					permisoEliminar = true;
				}
			}
			
		} catch (SisatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void buscarDeclaraciones(ActionEvent ev) {
		try {
			lstDjVehicular = vehicularBo.findDjVehicular(getSessionManaged().getContribuyente().getPersonaId(), placaBusc);
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
			// FIXME : Controller exception
		}
	}

	/**
	 * ITANTAMANGO
	 */
	public void buscarDeclaracionesGeneral(){
		try {

			lstDjVehicular=vehicularBo.findDjVehicular(codigoContri, placa, nomApe, razon);
			
		} catch (Exception e) {
			System.out.println("ERROR: " + e);
		}
	}
	
	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public void mostrarTodasDeclaraciones(ActionEvent ev) {
		try {
			lstDjVehicular = vehicularBo.findDjVehicular(getSessionManaged().getContribuyente().getPersonaId());
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
			// FIXME : Controller exception
		}
	}

	public void mostrarHistorico(ActionEvent ev) {
		try {
			UIComponent comp = ev.getComponent().getParent().getParent();
			if (comp != null) {
				UIData uiData = (UIData) comp;
				BuscarVehicularDTO bv = (BuscarVehicularDTO) uiData.getRowData();
				getSessionMap().put("hitoricovehiculodj.djvId", bv.getDjVehicularId());
				sendRedirectPrincipalListener();
			}
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
			// TODO : controlar excepción
		}
	}

	public void terminarPendiente(ActionEvent ev) {
		try {
			UIComponent comp = ev.getComponent().getParent().getParent();
			if (comp != null) {
				UIData uiData = (UIData) comp;
				BuscarVehicularDTO bv = (BuscarVehicularDTO) uiData.getRowData();
					getSessionMap().put("nuevadjregistro.djvId", bv.getDjVehicularId());
					getSessionMap().put("accion",VehicularCns.PENDIENTE_PRIMERA_INSCRIPCION);
					sendRedirectPrincipalListener();
			}
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
			// TODO : controlar excepción
		}
	}

	public void realizarDescargo(ActionEvent ev) {
		try {
			UIComponent comp = ev.getComponent().getParent().getParent();
			if (comp != null) {
				UIData uiData = (UIData) comp;
				BuscarVehicularDTO bv = (BuscarVehicularDTO) uiData.getRowData();
				getSessionMap().put("descargovehicular.djvId", bv.getDjVehicularId());
				getSessionMap().put("descargovehicular.accion", VehicularCns.ACCION_DESCARGOV_PEND);
				sendRedirectPrincipalListener();
			}
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
			// TODO : controlar excepción
		}
	}

	public String getPlacaBusc() {
		return placaBusc;
	}

	public void setPlacaBusc(String placaBusc) {
		this.placaBusc = placaBusc;
	}

	public List<BuscarVehicularDTO> getLstDjVehicular() {
		return lstDjVehicular;
	}

	public boolean isContribuyenteNull() {
		return contribuyenteNull;
	}

	public void setContribuyenteNull(boolean contribuyenteNull) {
		this.contribuyenteNull = contribuyenteNull;
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

	public boolean isPermisoBuscarEliminar() {
		return permisoBuscarEliminar;
	}

	public void setPermisoBuscarEliminar(boolean permisoBuscarEliminar) {
		this.permisoBuscarEliminar = permisoBuscarEliminar;
	}

	public boolean isPermisoEliminar() {
		return permisoEliminar;
	}

	public void setPermisoEliminar(boolean permisoEliminar) {
		this.permisoEliminar = permisoEliminar;
	}
	

}
