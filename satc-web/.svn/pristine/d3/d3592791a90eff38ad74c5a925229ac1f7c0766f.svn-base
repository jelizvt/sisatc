package com.sat.sisat.perfilmenu.managed;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.richfaces.model.selection.SimpleSelection;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.menus.business.MenuBoRemote;
import com.sat.sisat.menus.dto.MenusDTO;
import com.sat.sisat.menus.dto.SubmenuNivelAccesoDTO;
import com.sat.sisat.persistence.entity.SgUsuarioAcceso;
/**
 * @author Cristobal Ramires -=CRAMIREZ=-
 * @version 0.1
 * @since 05/02/2019 La clase AdministrarMenusManaged.java se encarga de la administración de menús para los perfiles 
 */

//28-01-2019S
@ManagedBean
@ViewScoped
public class AdministrarMenusManaged  extends BaseManaged{
	private static final long serialVersionUID = -3544830243114448278L;
	private SimpleSelection selectedMenu;
	private SimpleSelection simpleSelectedAccion;
	
	private int rowIndexSelected = -1;
	private SubmenuNivelAccesoDTO selectAction;
	private int submenuId;
	
	private List<SubmenuNivelAccesoDTO> nivelAcceso = new ArrayList<SubmenuNivelAccesoDTO>();

	@EJB
	private MenuBoRemote menuBo;
	
	private List<MenusDTO> listMenus = new ArrayList<MenusDTO>();
	
	
	@PostConstruct
	public void inicialize() {
		System.out.println("Desde Menus");
		getMenus();
	}
	
	public String getMenus() {
		try {
			
			//List<MenusDTO> menus = new ArrayList<MenusDTO>(); 
			listMenus = menuBo.getMenus(0);
			System.out.println("Desde");
			/*Iterator<MenusDTO> menuIterar = menus.iterator();
			while (menuIterar.hasNext()) {
				MenusDTO m = menuIterar.next();
				this.listMenus.add(m);
				System.out.println(m.getMenu());
									
			}*/

		} catch (SisatException ex) {
			String msg = "No se ha recuperado los permisos";
			System.out.println(msg + ex);
		}
		return null;
	}
	
	
	public void selectMenuAction() {
		System.out.println("HOLA");
		
		//selectedMenu = null;
		if (selectedMenu != null) {
			Iterator<Object> it = selectedMenu.getKeys();
			System.out.println(selectedMenu.getKeys());
			
			while (it.hasNext()) {
				Object obj = it.next();
				try {
					rowIndexSelected = Integer.parseInt(String.valueOf(obj));
					System.out.println(rowIndexSelected);
					MenusDTO sgu = this.listMenus.get(rowIndexSelected);
					submenuId = sgu.getSubMenuId();
					nivelAcceso = menuBo.getSubmenuNivelAcceso(sgu.getSubMenuId());
					
					selectAction = null;
					//usuariosAcceso = new ArrayList<SgUsuarioAcceso>();
				} catch (Exception ex) {
					System.out.println(ex); 
				}
			}
		}
	}
	
	public void selectAction() {
		selectAction = null;
		if (simpleSelectedAccion != null) {
			Iterator<Object> it = simpleSelectedAccion.getKeys();
			while (it.hasNext()) {
				Object obj = it.next();
				try {
					int rowIndex = Integer.parseInt(String.valueOf(obj));
					selectAction = nivelAcceso.get(rowIndex);
					int estado =0;
					if(selectAction.isHabilitado()){
						estado = 1;
					}else{
						estado =0;
					}
					menuBo.cambioSubmenuAcceso(submenuId ,  selectAction.getNivelAccesoId() , estado,getSessionManaged().getUsuarioLogIn().getUsuarioId() ,getSessionManaged().getTerminalLogIn());
					/*if(selectedRol.isHabilitado()){
						usuariosAcceso = userBo.getUsuarioAcceso(usuarioId, selectedRol.getRolId());
					}else{
						selectedRol = null;
						usuariosAcceso = new ArrayList<SgUsuarioAcceso>();
					}*/
				} catch (Exception ex) {
				}
			}
		}
	}
	
	

	public List<MenusDTO> getListMenus() {
		return listMenus;
	}

	public void setListMenus(List<MenusDTO> listMenus) {
		this.listMenus = listMenus;
	}

	public SimpleSelection getSelectedMenu() {
		return selectedMenu;
	}

	public void setSelectedMenu(SimpleSelection selectedMenu) {
		this.selectedMenu = selectedMenu;
	}

	public List<SubmenuNivelAccesoDTO> getNivelAcceso() {
		return nivelAcceso;
	}

	public void setNivelAcceso(List<SubmenuNivelAccesoDTO> nivelAcceso) {
		this.nivelAcceso = nivelAcceso;
	}

	public SubmenuNivelAccesoDTO getSelectAction() {
		return selectAction;
	}

	public void setSelectAction(SubmenuNivelAccesoDTO selectAction) {
		this.selectAction = selectAction;
	}

	public int getSubmenuId() {
		return submenuId;
	}

	public void setSubmenuId(int submenuId) {
		this.submenuId = submenuId;
	}

	public SimpleSelection getSimpleSelectedAccion() {
		return simpleSelectedAccion;
	}

	public void setSimpleSelectedAccion(SimpleSelection simpleSelectedAccion) {
		this.simpleSelectedAccion = simpleSelectedAccion;
	}
	
	
	
}

