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

import org.codehaus.groovy.runtime.callsite.PerInstancePojoMetaClassSite;

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
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.FacesUtil;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.controlycobranza.business.ControlyCobranzaBoRemote;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.menus.business.MenuBoRemote;
import com.sat.sisat.menus.dto.SimpleMenuDTO;
import com.sat.sisat.persistence.entity.MpTipoDocuIdentidad;

@ManagedBean
@ViewScoped
public class ManteCostasManaged extends BaseManaged {
	private static final long serialVersionUID = 1673161260001450282L;
	@EJB
	CobranzaCoactivaBoRemote cobranzaCoactivaBo;
	
	@EJB
	MenuBoRemote menuBo;
	
	private List<GenericDTO> records = new ArrayList<GenericDTO>();
	private GenericDTO selected = new GenericDTO();
	private Integer nuevoPeriodo;
	
	// INICIO PERMISOS PARA EL MODULO -=CRAMIREZ=-
	 	private List<SimpleMenuDTO> listPermisosSubmenu = new ArrayList<SimpleMenuDTO>();
	 	private boolean permisoDuplicar;	 
	 	private boolean permisoModificarActualizar;
	 // FIN PERMISOS PARA EL MODULO
	
	@PostConstruct
	public void init() {
		permisosMenu();
		try {
			records=cobranzaCoactivaBo.consultarPeriodoCostas();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void permisosMenu() {	
		try {
			int submenuId = Constante.MANTE_COSTAS;
		 	
			int permisoDuplicarId = Constante.DUPLICAR;
			int permisoModificarActualizarId = Constante.MODIFICAR_ACTUALIZAR;		
			
			permisoDuplicar = false;
			permisoModificarActualizar = false;
			
			listPermisosSubmenu  = menuBo.getAccesosSubmenuUsuario( getSessionManaged().getUsuarioLogIn().getUsuarioId() , submenuId);
			
			Iterator<SimpleMenuDTO> menuIterar = listPermisosSubmenu.iterator();
			while (menuIterar.hasNext()) {
				SimpleMenuDTO lsm = menuIterar.next();
				if(lsm.getItemId() == permisoDuplicarId) {
					permisoDuplicar = true;
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
	
	public String editar(){
		try{
			if(selected!=null){
				getSessionMap().put("PeriodoCostas", selected);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return sendRedirectPrincipal();
	}
	
	public void duplicar(){
		try{
			Integer periodoActual=DateUtil.getAnioActual();
			if (selected!=null&&selected.getId()>0){
				if(!existePeriodo(nuevoPeriodo)){
					cobranzaCoactivaBo.duplicaCostaPeriodo(nuevoPeriodo,selected.getId(),getSessionManaged().getUsuarioLogIn().getUsuarioId(),getSessionManaged().getTerminalLogIn());
					records=cobranzaCoactivaBo.consultarPeriodoCostas();
				}else{
					WebMessages.messageError("Periodo existe o no es valido");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean existePeriodo(Integer periodo){
		for(GenericDTO r : records){
			if(r.getId()==periodo.intValue()){
				return true;
			}
		}
		return false;
	}
	
	public List<GenericDTO> getRecords() {
		return records;
	}

	public void setRecords(List<GenericDTO> records) {
		this.records = records;
	}

	public GenericDTO getSelected() {
		return selected;
	}

	public void setSelected(GenericDTO selected) {
		this.selected = selected;
	}

	public Integer getNuevoPeriodo() {
		return nuevoPeriodo;
	}

	public void setNuevoPeriodo(Integer nuevoPeriodo) {
		this.nuevoPeriodo = nuevoPeriodo;
	}

	public List<SimpleMenuDTO> getListPermisosSubmenu() {
		return listPermisosSubmenu;
	}

	public void setListPermisosSubmenu(List<SimpleMenuDTO> listPermisosSubmenu) {
		this.listPermisosSubmenu = listPermisosSubmenu;
	}

	public boolean isPermisoDuplicar() {
		return permisoDuplicar;
	}

	public void setPermisoDuplicar(boolean permisoDuplicar) {
		this.permisoDuplicar = permisoDuplicar;
	}

	public boolean isPermisoModificarActualizar() {
		return permisoModificarActualizar;
	}

	public void setPermisoModificarActualizar(boolean permisoModificarActualizar) {
		this.permisoModificarActualizar = permisoModificarActualizar;
	}
	
}
