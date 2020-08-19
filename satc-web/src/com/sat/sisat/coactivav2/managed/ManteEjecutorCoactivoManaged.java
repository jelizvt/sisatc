package com.sat.sisat.coactivav2.managed;

import java.util.ArrayList;
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

import com.sat.sisat.cobranzacoactiva.business.CobranzaCoactivaBoRemote;
import com.sat.sisat.cobranzacoactiva.dto.EjecutorCoactivo;
import com.sat.sisat.cobranzacoactiva.dto.InformeTransferido;
import com.sat.sisat.cobranzacoactiva.dto.InformeTransferidoDetalle;
import com.sat.sisat.cobranzacoactiva.dto.ListadoArea;
import com.sat.sisat.cobranzacoactiva.dto.ListadoEstadoTransferencia;
import com.sat.sisat.common.business.GeneralBoRemote;
import com.sat.sisat.common.dto.GenericDTO;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.FacesUtil;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.controlycobranza.business.ControlyCobranzaBoRemote;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.menus.business.MenuBoRemote;
import com.sat.sisat.menus.dto.SimpleMenuDTO;
import com.sat.sisat.persistence.entity.MpTipoDocuIdentidad;

@ManagedBean
@ViewScoped
public class ManteEjecutorCoactivoManaged extends BaseManaged {
	private static final long serialVersionUID = 1673161260001450282L;
	@EJB
	CobranzaCoactivaBoRemote cobranzaCoactivaBo;
	
	@EJB
	MenuBoRemote menuBo;
	
	private List<EjecutorCoactivo> records = new ArrayList<EjecutorCoactivo>();
	private EjecutorCoactivo selected = new EjecutorCoactivo();
	
	private List<GenericDTO> lUsuarios = new ArrayList<GenericDTO>();
	// INICIO PERMISOS PARA EL MODULO -=CRAMIREZ=-
	 	private List<SimpleMenuDTO> listPermisosSubmenu = new ArrayList<SimpleMenuDTO>();	
	 	private boolean permisoAgregarRegistrar;
	 	private boolean permisoModificarActualizar;
	// FIN PERMISOS PARA EL MODULO
	@PostConstruct
	public void init() {
		permisosMenu();
		try {
			records=cobranzaCoactivaBo.consultarEjecutorCoactivo();
			lUsuarios=cobranzaCoactivaBo.listarUsuario();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void permisosMenu() {	
		try {
			int submenuId = Constante.MANTE_EJECUTOR_COACTIVO;
		 	
			int permisoAgregarRegistrarId = Constante.AGREGAR_REGISTRAR;
			int permisoModificarActualizarId = Constante.MODIFICAR_ACTUALIZAR;	
			
			permisoAgregarRegistrar = false;
			permisoModificarActualizar = false;
			
			listPermisosSubmenu  = menuBo.getAccesosSubmenuUsuario( getSessionManaged().getUsuarioLogIn().getUsuarioId() , submenuId);
			
			Iterator<SimpleMenuDTO> menuIterar = listPermisosSubmenu.iterator();
			while (menuIterar.hasNext()) {
				SimpleMenuDTO lsm = menuIterar.next();
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
	
	public void nuevo(){
		try{			
			selected = new EjecutorCoactivo();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
		
	public void editar(){
		try{
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void guardar(){
		try{
			cobranzaCoactivaBo.registraUsuarioCargo(selected.getUsuarioCargoId(), selected.getUsuarioId(), selected.getCargoId(),selected.getUsuarioAuxId(),selected.getEstado()?1:9,
					selected.getNroRegistro(),selected.getNroRegistroAux(),
					selected.getMateriaId(),
					getSessionManaged().getUsuarioLogIn().getUsuarioId(),getSessionManaged().getTerminalLogIn());
			records=cobranzaCoactivaBo.consultarEjecutorCoactivo();
			lUsuarios=cobranzaCoactivaBo.listarUsuario();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public List<EjecutorCoactivo> getRecords() {
		return records;
	}

	public void setRecords(List<EjecutorCoactivo> records) {
		this.records = records;
	}

	public EjecutorCoactivo getSelected() {
		return selected;
	}

	public void setSelected(EjecutorCoactivo selected) {
		this.selected = selected;
	}

	public List<GenericDTO> getlUsuarios() {
		return lUsuarios;
	}

	public void setlUsuarios(List<GenericDTO> lUsuarios) {
		this.lUsuarios = lUsuarios;
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

	public boolean isPermisoModificarActualizar() {
		return permisoModificarActualizar;
	}

	public void setPermisoModificarActualizar(boolean permisoModificarActualizar) {
		this.permisoModificarActualizar = permisoModificarActualizar;
	}
}
