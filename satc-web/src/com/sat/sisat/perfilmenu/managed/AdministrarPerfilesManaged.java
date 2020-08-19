package com.sat.sisat.perfilmenu.managed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.richfaces.component.html.HtmlComboBox;
import org.richfaces.model.selection.SimpleSelection;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.menus.business.MenuBoRemote;
import com.sat.sisat.menus.dto.MenusDTO;
import com.sat.sisat.menus.dto.PerfilesDTO;
import com.sat.sisat.menus.dto.SimpleMenuDTO;
import com.sat.sisat.menus.dto.SubmenuNivelAccesoDTO;
import com.sat.sisat.menus.dto.TipoMenuDTO;
import com.sat.sisat.persistence.entity.MpTipoDocuIdentidad;
import com.sat.sisat.persistence.entity.SgUsuario;
import com.sat.sisat.persistence.entity.SgUsuarioAcceso;
import com.sat.sisat.usuario.dto.RolesUsuarioDTO;
/**
 * @author Cristobal Ramires -=CRAMIREZ=-
 * @version 0.1
 * @since 02/05/2019 La clase AdministrarPerfilesManaged.java se encarga de la creacion de perfiles para los accesos a modulos 
 */
@ManagedBean
@ViewScoped
public class AdministrarPerfilesManaged extends BaseManaged{
	
	@EJB
	private MenuBoRemote menuBo;

	
	private List<PerfilesDTO> listPerfiles = new ArrayList<PerfilesDTO>();
	private List<PerfilesDTO> listTempPerfiles = new ArrayList<PerfilesDTO>();
	private List<MenusDTO> listPermisosPerfiles = new ArrayList<MenusDTO>();
	private List<TipoMenuDTO> listTipoMenu = new ArrayList<TipoMenuDTO>();	
	private List<SimpleMenuDTO> listMenuPorTipo = new ArrayList<SimpleMenuDTO>();
	private List<SimpleMenuDTO> listSubMenu = new ArrayList<SimpleMenuDTO>();
	private List<SimpleMenuDTO> listPermisosPorSubmenu = new ArrayList<SimpleMenuDTO>();
	
	private HtmlComboBox cmbtipodocumento;
	private List<SelectItem> listcmbTipoMenu = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRpTipoMenu = new HashMap<String, Integer>();
	private String tipoMenuValue;
	private Integer tipoMenuId;
	
	private HtmlComboBox cmbMenu;
	private List<SelectItem> listcmbMenu = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRpMenu = new HashMap<String, Integer>();
	private String menuValue;
	private Integer menuId;
	
	
	private HtmlComboBox cmbSubmenu;
	private List<SelectItem> listcmbSubmenu = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRpSubmenu = new HashMap<String, Integer>();
	private String submenuValue;
	private Integer submenuId;
	
	private boolean saveCorrectoPermisoPerfil;
	
	private boolean saveCorrectoPerfil;
	
	private SimpleSelection simpleSelectedPerfil;
	private PerfilesDTO selectPerfil;
	private PerfilesDTO selectTempPerfil;
	
	private SimpleSelection simpleSelectedPermisoPerfil;
	private SimpleMenuDTO selectPermisoPerfil;
	
	private boolean flagRegistrarPermisoPerfil ;
	
	private String listNivelAcceso;
	
	private String nuevoNombrePerfil;
	
	
	@PostConstruct
	public void inicialize() {
		flagRegistrarPermisoPerfil = true;
		getPerfiles();
		getTipoMenu();
	}
	
	public String getPerfiles() {
		try {		
			listPerfiles = menuBo.getPerfiles();
			listTempPerfiles = menuBo.getPerfiles(); 
			System.out.println("getPerfiles");
		} catch (SisatException ex) {
			String msg = "No se ha recuperado los permisos";
			System.out.println(msg + ex);
		}
		return null;
	}
	
	public void selectPerfilTable() {
		if (simpleSelectedPerfil != null) {
			Iterator<Object> it = simpleSelectedPerfil.getKeys();
			while (it.hasNext()) {
				Object obj = it.next();
				try {
					int rowIndex = Integer.parseInt(String.valueOf(obj));
					selectPerfil = listPerfiles.get(rowIndex);
					selectTempPerfil = listTempPerfiles.get(rowIndex);
					
					getPermisosPerfiles(selectPerfil.getPerfilId(),0);		
					flagRegistrarPermisoPerfil = false;
					
					int estado = 1;
					if(selectPerfil.getEstado()) {
						estado = 1;
					}else {
						estado = 0;
					}
					
					if(selectPerfil.getEstado() != selectTempPerfil.getEstado()) {
						int resultado = menuBo.cambioEstadoPerfil(selectPerfil.getPerfilId(),estado , getSessionManaged().getUsuarioLogIn().getUsuarioId() ,getSessionManaged().getTerminalLogIn());
						if(resultado == 1) {
							getPerfiles();
						}
					}
					
					
				} catch (Exception ex) {
				}
			}
		}
	}
	
	public void elimiarPermisoPerfil(int perfilSubmenuId) {
		System.out.println("elimiarPermisoPerfil");
		System.out.println(perfilSubmenuId);
		
		try {
			menuBo.eliminarPermisoPerfil(perfilSubmenuId , getSessionManaged().getUsuarioLogIn().getUsuarioId() ,getSessionManaged().getTerminalLogIn());
			
			getPermisosPerfiles(selectPerfil.getPerfilId(),0);
		} catch (SisatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getPermisosPerfiles(int perfilId, int tipo) {
		try {		
			listPermisosPerfiles = menuBo.getPermisosPerfil(perfilId, tipo);
			System.out.println("getPermisosPerfiles");
		} catch (SisatException ex) {
			String msg = "No se ha recuperado los permisos";
			System.out.println(msg + ex);
		}
		return null;
	}

	
	public String getTipoMenu() {
		try {		
			listTipoMenu = menuBo.getTipoMenu();
			System.out.println("getPerfiles");
			Iterator<TipoMenuDTO> it1 = listTipoMenu.iterator();
			while (it1.hasNext()) {
				TipoMenuDTO obj = it1.next();
				listcmbTipoMenu.add(new SelectItem(obj.getDescripcion(),
						String.valueOf(obj.getTipoMenuId())));
				mapRpTipoMenu.put(obj.getDescripcion().trim(),
						obj.getTipoMenuId());
			}
		} catch (SisatException ex) {
			String msg = "No se ha recuperado los permisos";
			System.out.println(msg + ex);
		}
		return null;
	}
	
	public void cambiarTipoMenu(ValueChangeEvent event) {
		String value = (String) event.getNewValue();
		listPermisosPorSubmenu = new ArrayList<SimpleMenuDTO>();
		listcmbSubmenu = new ArrayList<SelectItem>();
		listcmbMenu = new ArrayList<SelectItem>();
		menuValue=null;
		submenuValue=null;
		menuId = null;
		submenuId = null ;
		
		if (value != null) {
			tipoMenuId = (Integer) mapRpTipoMenu.get(value);

			if (tipoMenuId != null) {
				try {
					listMenuPorTipo = menuBo.getMenuTipo(tipoMenuId);
					
					Iterator<SimpleMenuDTO> itmt = listMenuPorTipo.iterator();
					
					listcmbMenu = new ArrayList<SelectItem>();
							
					while (itmt.hasNext()) {
						SimpleMenuDTO obj = itmt.next();
						
						listcmbMenu.add(new SelectItem(obj.getDescripcion(),
								String.valueOf(obj.getItemId())));
						mapRpMenu.put(obj.getDescripcion().trim(),
								obj.getItemId());
					}
					
				} catch (SisatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("Error al cargar los menus:"+e);
				}
				
			}
		}
		//System.out.println(tipo);
	}
	
	public void cambiarMenu(ValueChangeEvent event) {
		String value = (String) event.getNewValue();
		
		listPermisosPorSubmenu = new ArrayList<SimpleMenuDTO>();
		listcmbSubmenu = new ArrayList<SelectItem>();
		submenuValue=null;
		submenuId = null;
		
		if (value != null) {
			menuId = (Integer) mapRpMenu.get(value);
			System.out.println(menuId);
			
			if (menuId != null) {
				try {
					listSubMenu = menuBo.getSubmenu(menuId);
					Iterator<SimpleMenuDTO> itsmt = listSubMenu.iterator();
					
					listcmbSubmenu = new ArrayList<SelectItem>();
					
					while (itsmt.hasNext()) {
						SimpleMenuDTO obj = itsmt.next();
						
						listcmbSubmenu.add(new SelectItem(obj.getDescripcion(),
								String.valueOf(obj.getItemId())));
						mapRpSubmenu.put(obj.getDescripcion().trim(),
								obj.getItemId());
					}
					
				} catch (SisatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("Error al cargar los menus:"+e);
				}
				
			}
		}
	}	
	
	public void cambiarSubmenu(ValueChangeEvent event) {
		String value = (String) event.getNewValue();
		
		System.out.println("cambiarSubmenu");
		System.out.println(value);
		
		if (value != null) {
			submenuId = (Integer) mapRpSubmenu.get(value);
			System.out.println(submenuId);
			
			if (submenuId != null) {
				try {
					listPermisosPorSubmenu = new ArrayList<SimpleMenuDTO>();
					
					listPermisosPorSubmenu = menuBo.getAccesosSubmenu(submenuId);
					
				} catch (SisatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("Error al cargar los menus:"+e);
				}
				
			}
		}
	}
	
	public String guardarPermisosPerfil() {
		saveCorrectoPermisoPerfil= false;
		if (datosNulosPermisoPerfil()) {
			return null;
		}
		
		try {
			
			Iterator<SimpleMenuDTO> iterar = listPermisosPorSubmenu.iterator();
			listNivelAcceso = "";
			while (iterar.hasNext()) {
				SimpleMenuDTO obj = iterar.next();			
				
				if(obj.isHabilitado()) {			
					listNivelAcceso = listNivelAcceso + obj.getItemId() + ",";
				}
			}
			listNivelAcceso = listNivelAcceso.substring(0,listNivelAcceso.length()-1) ;
			
			int resultado = 0;		
		
			resultado =  menuBo.registrarPermisoPerfil(submenuId, selectPerfil.getPerfilId(), getSessionManaged().getUsuarioLogIn().getUsuarioId() ,getSessionManaged().getTerminalLogIn(), listNivelAcceso);
		
			if(resultado == 0 ) {
				addErrorMessage("No ha sido posible crear los permisos");
			}else {
				getPermisosPerfiles(selectPerfil.getPerfilId(),0);
				saveCorrectoPermisoPerfil = true;
				addInfoMessage("Permisos creado con éxito");
			}
		} catch (SisatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	private boolean datosNulosPermisoPerfil() {
		boolean rs = false;
		if (menuId == null || menuValue.isEmpty()) {
			rs = true;
			addErrorMessage("Menú: No se encuentra seleccioné, por favor verifique");
		}
		if (submenuId == null || submenuValue.isEmpty()) {
			rs = true;
			addErrorMessage("Submenú: No se encuentra seleccioné, por favor verifique");
		}
		
		return rs;
	}
	
	public void nuevoPermisoUsuario() {
		System.out.println("nuevoPermisoUsuario");
		listPermisosPorSubmenu = new ArrayList<SimpleMenuDTO>();
		listcmbSubmenu = new ArrayList<SelectItem>();
		listcmbMenu = new ArrayList<SelectItem>();
		menuValue=null;
		tipoMenuId = null;
		tipoMenuValue = null;
		submenuValue=null;
		menuId = null;
		submenuId = null ;
	}
	
	public void nuevoPerfil() {
		nuevoNombrePerfil = null;
	}
	
	public String guardarPerfil() {
		//System.out.println("guardarPerfil");
		saveCorrectoPerfil= false;
		System.out.println(nuevoNombrePerfil);
		if (datosNulosPerfil()) {
			return null;
		}
		try {
			System.out.println("Entre !!");
			int resultado;
		
			resultado = menuBo.registrarPerfil(nuevoNombrePerfil, getSessionManaged().getUsuarioLogIn().getUsuarioId() ,getSessionManaged().getTerminalLogIn());
			System.out.println(resultado);
			
			if(resultado == 0 ) {
				addErrorMessage("No ha sido posible crear el perfil");
			}else {
				getPerfiles();
				saveCorrectoPerfil = true;
				addInfoMessage("Perfil creado con éxito");
			}
		
		} catch (SisatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return null;
	}
	
	private boolean datosNulosPerfil() {
		boolean rs = false;
		System.out.println(nuevoNombrePerfil);
		if (nuevoNombrePerfil == null || nuevoNombrePerfil.isEmpty()) {
			rs = true;
			addErrorMessage("Nombre del perfil: Esta vacio, porfavor verifique");
		}		
		return rs;
	}
	
	public void selecionarPermisoPerfil () {

		if (simpleSelectedPermisoPerfil != null) {
			Iterator<Object> it = simpleSelectedPermisoPerfil.getKeys();
			while (it.hasNext()) {
				Object obj = it.next();
				try {
					int rowIndex = Integer.parseInt(String.valueOf(obj));
					selectPermisoPerfil = listPermisosPorSubmenu.get(rowIndex);
					
				} catch (Exception ex) {
				}
			}
		}
	}
	
	
	
	public MenuBoRemote getMenuBo() {
		return menuBo;
	}


	public void setMenuBo(MenuBoRemote menuBo) {
		this.menuBo = menuBo;
	}


	public List<PerfilesDTO> getListPerfiles() {
		return listPerfiles;
	}


	public void setListPerfiles(List<PerfilesDTO> listPerfiles) {
		this.listPerfiles = listPerfiles;
	}

	public List<MenusDTO> getListPermisosPerfiles() {
		return listPermisosPerfiles;
	}

	public void setListPermisosPerfiles(List<MenusDTO> listPermisosPerfiles) {
		this.listPermisosPerfiles = listPermisosPerfiles;
	}

	public SimpleSelection getSimpleSelectedPerfil() {
		return simpleSelectedPerfil;
	}

	public void setSimpleSelectedPerfil(SimpleSelection simpleSelectedPerfil) {
		this.simpleSelectedPerfil = simpleSelectedPerfil;
	}

	public PerfilesDTO getSelectPerfil() {
		return selectPerfil;
	}

	public void setSelectPerfil(PerfilesDTO selectPerfil) {
		this.selectPerfil = selectPerfil;
	}

	public boolean isFlagRegistrarPermisoPerfil() {
		return flagRegistrarPermisoPerfil;
	}

	public void setFlagRegistrarPermisoPerfil(boolean flagRegistrarPermisoPerfil) {
		this.flagRegistrarPermisoPerfil = flagRegistrarPermisoPerfil;
	}

	public List<TipoMenuDTO> getListTipoMenu() {
		return listTipoMenu;
	}

	public void setListTipoMenu(List<TipoMenuDTO> listTipoMenu) {
		this.listTipoMenu = listTipoMenu;
	}

	public HtmlComboBox getCmbtipodocumento() {
		return cmbtipodocumento;
	}

	public void setCmbtipodocumento(HtmlComboBox cmbtipodocumento) {
		this.cmbtipodocumento = cmbtipodocumento;
	}

	public List<SelectItem> getListcmbTipoMenu() {
		return listcmbTipoMenu;
	}

	public void setListcmbTipoMenu(List<SelectItem> listcmbTipoMenu) {
		this.listcmbTipoMenu = listcmbTipoMenu;
	}

	public HashMap<String, Integer> getMapRpTipoMenu() {
		return mapRpTipoMenu;
	}

	public void setMapRpTipoMenu(HashMap<String, Integer> mapRpTipoMenu) {
		this.mapRpTipoMenu = mapRpTipoMenu;
	}

	public Integer getTipoMenuId() {
		return tipoMenuId;
	}

	public void setTipoMenuId(Integer tipoMenuId) {
		this.tipoMenuId = tipoMenuId;
	}

	public String getTipoMenuValue() {
		return tipoMenuValue;
	}

	public void setTipoMenuValue(String tipoMenuValue) {
		this.tipoMenuValue = tipoMenuValue;
	}

	public List<SimpleMenuDTO> getListMenuPorTipo() {
		return listMenuPorTipo;
	}

	public void setListMenuPorTipo(List<SimpleMenuDTO> listMenuPorTipo) {
		this.listMenuPorTipo = listMenuPorTipo;
	}

	public List<SelectItem> getListcmbMenu() {
		return listcmbMenu;
	}

	public void setListcmbMenu(List<SelectItem> listcmbMenu) {
		this.listcmbMenu = listcmbMenu;
	}

	public HashMap<String, Integer> getMapRpMenu() {
		return mapRpMenu;
	}

	public void setMapRpMenu(HashMap<String, Integer> mapRpMenu) {
		this.mapRpMenu = mapRpMenu;
	}

	public String getMenuValue() {
		return menuValue;
	}

	public void setMenuValue(String menuValue) {
		this.menuValue = menuValue;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public HtmlComboBox getCmbMenu() {
		return cmbMenu;
	}

	public void setCmbMenu(HtmlComboBox cmbMenu) {
		this.cmbMenu = cmbMenu;
	}

	public List<SimpleMenuDTO> getListSubMenu() {
		return listSubMenu;
	}

	public void setListSubMenu(List<SimpleMenuDTO> listSubMenu) {
		this.listSubMenu = listSubMenu;
	}

	public HtmlComboBox getCmbSubmenu() {
		return cmbSubmenu;
	}

	public void setCmbSubmenu(HtmlComboBox cmbSubmenu) {
		this.cmbSubmenu = cmbSubmenu;
	}

	public List<SelectItem> getListcmbSubmenu() {
		return listcmbSubmenu;
	}

	public void setListcmbSubmenu(List<SelectItem> listcmbSubmenu) {
		this.listcmbSubmenu = listcmbSubmenu;
	}

	public HashMap<String, Integer> getMapRpSubmenu() {
		return mapRpSubmenu;
	}

	public void setMapRpSubmenu(HashMap<String, Integer> mapRpSubmenu) {
		this.mapRpSubmenu = mapRpSubmenu;
	}

	public String getSubmenuValue() {
		return submenuValue;
	}

	public void setSubmenuValue(String submenuValue) {
		this.submenuValue = submenuValue;
	}

	public Integer getSubmenuId() {
		return submenuId;
	}

	public void setSubmenuId(Integer submenuId) {
		this.submenuId = submenuId;
	}

	public List<SimpleMenuDTO> getListPermisosPorSubmenu() {
		return listPermisosPorSubmenu;
	}

	public void setListPermisosPorSubmenu(List<SimpleMenuDTO> listPermisosPorSubmenu) {
		this.listPermisosPorSubmenu = listPermisosPorSubmenu;
	}

	public boolean isSaveCorrectoPermisoPerfil() {
		return saveCorrectoPermisoPerfil;
	}

	public void setSaveCorrectoPermisoPerfil(boolean saveCorrectoPermisoPerfil) {
		this.saveCorrectoPermisoPerfil = saveCorrectoPermisoPerfil;
	}

	public SimpleSelection getSimpleSelectedPermisoPerfil() {
		return simpleSelectedPermisoPerfil;
	}

	public void setSimpleSelectedPermisoPerfil(SimpleSelection simpleSelectedPermisoPerfil) {
		this.simpleSelectedPermisoPerfil = simpleSelectedPermisoPerfil;
	}

	public SimpleMenuDTO getSelectPermisoPerfil() {
		return selectPermisoPerfil;
	}

	public void setSelectPermisoPerfil(SimpleMenuDTO selectPermisoPerfil) {
		this.selectPermisoPerfil = selectPermisoPerfil;
	}

	public String getListNivelAcceso() {
		return listNivelAcceso;
	}

	public void setListNivelAcceso(String listNivelAcceso) {
		this.listNivelAcceso = listNivelAcceso;
	}

	public String getNuevoNombrePerfil() {
		return nuevoNombrePerfil;
	}

	public void setNuevoNombrePerfil(String nuevoNombrePerfil) {
		this.nuevoNombrePerfil = nuevoNombrePerfil;
	}

	public boolean isSaveCorrectoPerfil() {
		return saveCorrectoPerfil;
	}

	public void setSaveCorrectoPerfil(boolean saveCorrectoPerfil) {
		this.saveCorrectoPerfil = saveCorrectoPerfil;
	}

	public List<PerfilesDTO> getListTempPerfiles() {
		return listTempPerfiles;
	}

	public void setListTempPerfiles(List<PerfilesDTO> listTempPerfiles) {
		this.listTempPerfiles = listTempPerfiles;
	}

	public PerfilesDTO getSelectTempPerfil() {
		return selectTempPerfil;
	}

	public void setSelectTempPerfil(PerfilesDTO selectTempPerfil) {
		this.selectTempPerfil = selectTempPerfil;
	}
	
	
}
