package com.sat.sisat.papeletas.managed;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.menus.business.MenuBoRemote;
import com.sat.sisat.menus.dto.SimpleMenuDTO;
import com.sat.sisat.papeleta.business.PapeletaBoRemote;
import com.sat.sisat.papeleta.dto.CargaLoteDTO;
import com.sat.sisat.papeleta.dto.GridDetalleLote;
import com.sat.sisat.persistence.entity.PaCargaDetalleLote;
import com.sat.sisat.persistence.entity.PaCargaLote;

@ManagedBean
@ViewScoped
public class BuscarOficioLotesManaged extends BaseManaged {
	
	@EJB
	PapeletaBoRemote papeletaBo;
	
	@EJB
	private MenuBoRemote menuBo;
	
	private List<GridDetalleLote> gridDetalleLotes = new ArrayList<GridDetalleLote>();
	private Date fechaOficio;
	private Date fechaRecepcion;
	private String numeroOficio;
	
	private PaCargaLote lote = new PaCargaLote();
	private List<CargaLoteDTO> listaLotes = new ArrayList<CargaLoteDTO>();
	private CargaLoteDTO currentItem;
	
	// INICIO PERMISOS PARA EL MODULO -=CRAMIREZ=-
		private List<SimpleMenuDTO> listPermisosSubmenu = new ArrayList<SimpleMenuDTO>();
		private boolean permisoBuscar;
		private boolean permisoAgregarRegistrar;
		private boolean permisoModificarActualizar;
		private boolean permisoVerificar;
	// FIN PERMISOS PARA EL MODULO
			
	public BuscarOficioLotesManaged(){
		
	}
	
	@PostConstruct
	public void init(){
		permisosMenu();
		buscarLotes();
	}
	
	public void permisosMenu() {	
		try {
			int submenuId = Constante.REGISTRO_MASIVO;
			
			int permisoBuscarId = Constante.BUSCAR;
			int permisoAgregarRegistrarId = Constante.AGREGAR_REGISTRAR;
			int permisoModificarActualizarId = Constante.MODIFICAR_ACTUALIZAR;
			int permisoVerificarId = Constante.VERIFICAR;
			
			permisoBuscar = false;
			permisoAgregarRegistrar = false;
			permisoModificarActualizar = false;
			permisoVerificar = false;
			
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
				if(lsm.getItemId() == permisoVerificarId) {
					permisoVerificar = true;
				}
			}
			
		} catch (SisatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String inscripcion(){
		closeSession("registroOficioLotesManaged");
		return sendRedirectPrincipal();
	}
	
	public void buscarLotes() {
		try{
			lote = new PaCargaLote();
			if(getFechaOficio()!=null){
				lote.setFecOficio(DateUtil.dateToSqlTimestamp(getFechaOficio()));
			}else if(getFechaRecepcion()!=null){
				lote.setFecRecepcion(DateUtil.dateToSqlTimestamp(getFechaRecepcion()));	
			}else if(getNumeroOficio()!=null&&getNumeroOficio().trim().length()>0){
				lote.setNumOficio(getNumeroOficio());	
			}
			listaLotes = papeletaBo.buscarLotes(lote);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void actualizacion(){
		
	}
	
	public String verDetalle() {
		if(currentItem!=null){
			getSessionMap().put("cargaLote", currentItem);
		}
		closeSession("registroDetalleLotePapeletasManaged");
		return sendRedirectPrincipal();
	}

	public String digitar() {
		if(currentItem!=null){
			getSessionMap().put("cargaLote", currentItem);
		}
		closeSession("digitarLotesPapeletasManaged");
		return sendRedirectPrincipal();
	}
	
	public String verificar() {
		if(currentItem!=null){
			getSessionMap().put("cargaLote", currentItem);
		}
		closeSession("verificarLotesPapeletasManaged");
		return sendRedirectPrincipal();
	}
	
	public Date getFechaOficio() {
		return fechaOficio;
	}

	public void setFechaOficio(Date fechaOficio) {
		this.fechaOficio = fechaOficio;
	}

	public Date getFechaRecepcion() {
		return fechaRecepcion;
	}

	public void setFechaRecepcion(Date fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}

	public String getNumeroOficio() {
		return numeroOficio;
	}

	public void setNumeroOficio(String numeroOficio) {
		this.numeroOficio = numeroOficio;
	}

	public List<CargaLoteDTO> getListaLotes() {
		return listaLotes;
	}

	public void setListaLotes(List<CargaLoteDTO> listaLotes) {
		this.listaLotes = listaLotes;
	}
	public CargaLoteDTO getCurrentItem() {
		return currentItem;
	}

	public void setCurrentItem(CargaLoteDTO currentItem) {
		this.currentItem = currentItem;
	}
	
	public List<GridDetalleLote> getGridDetalleLotes() {
		return gridDetalleLotes;
	}

	public void setGridDetalleLotes(List<GridDetalleLote> gridDetalleLotes) {
		this.gridDetalleLotes = gridDetalleLotes;
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

	public boolean isPermisoVerificar() {
		return permisoVerificar;
	}

	public void setPermisoVerificar(boolean permisoVerificar) {
		this.permisoVerificar = permisoVerificar;
	}
	
}
