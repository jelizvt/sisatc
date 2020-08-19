package com.sat.sisat.menus.business;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.sat.sisat.exception.SisatException;
import com.sat.sisat.imputacion.dao.ImputacionDao;
import com.sat.sisat.menus.dao.MenuDao;
import com.sat.sisat.menus.dto.MenusDTO;
import com.sat.sisat.menus.dto.PerfilesDTO;
import com.sat.sisat.menus.dto.SimpleMenuDTO;
import com.sat.sisat.menus.dto.SubmenuNivelAccesoDTO;
import com.sat.sisat.menus.dto.TipoMenuDTO;
import com.sat.sisat.predial.business.BaseBusiness;

/**
 * Session Bean implementation class MenuBo
 * // -=CRAMIREZ=-
 */
@Stateless
public class MenuBo extends BaseBusiness implements MenuBoRemote {
	
	private static final long serialVersionUID = -8302299678574876556L;
	
	private MenuDao menuDao;
	
	public MenuBo() {
	}

	@PostConstruct
	public void initialize() {
		this.menuDao = new MenuDao();
    	setDataManager(this.menuDao);
	}
	
	@Override
	public List<MenusDTO> getMenus(int tipo) throws SisatException {
		return menuDao.getMenus(tipo);
	}
	
	@Override
	public List<SubmenuNivelAccesoDTO> getSubmenuNivelAcceso(int submenuId) throws SisatException {
		return menuDao.getSubmenuNivelAcceso(submenuId);
	}
	
	@Override
	public int cambioSubmenuAcceso(int submenuId ,  int nivelAccesoId , int estado,int usuarioId ,String terminal) throws SisatException {
		return menuDao.cambioSubmenuAcceso(submenuId , nivelAccesoId , estado, usuarioId , terminal);
	}
	
	@Override
	public List<PerfilesDTO> getPerfiles() throws SisatException {
		return menuDao.getPerfiles();
	}
	
	@Override
	public List<MenusDTO> getPermisosPerfil(int perfilId, int tipo) throws SisatException {
		return menuDao.getPermisosPerfil(perfilId, tipo);
	}
	
	@Override
	public int eliminarPermisoPerfil( int perfilSubmenuId ,int usuarioIdUpd ,String terminalUpd) throws SisatException {
		return menuDao.eliminarPermisoPerfil( perfilSubmenuId ,usuarioIdUpd ,terminalUpd);
	}
	
	@Override
	public List<TipoMenuDTO> getTipoMenu() throws SisatException {
		return menuDao.getTipoMenu();
	}
	
	
	@Override
	public List<SimpleMenuDTO> getMenuTipo(int tipo) throws SisatException {
		return menuDao.getMenuTipo(tipo);
	}
	
	@Override
	public List<SimpleMenuDTO> getSubmenu(int menuId) throws SisatException {
		return menuDao.getSubmenu(menuId);
	}
	
	@Override
	public List<SimpleMenuDTO> getAccesosSubmenu(int submenuId) throws SisatException {
		return menuDao.getAccesosSubmenu(submenuId);
	}
	@Override
	public int registrarPermisoPerfil(  int submenuId ,int perfilId ,int usuarioId,String terminal,String listNivelAcceso ) throws SisatException {
		return menuDao.registrarPermisoPerfil(submenuId ,perfilId , usuarioId, terminal, listNivelAcceso);
	}
	
	@Override
	public int registrarPerfil(  String descripcion ,int usuarioId,String terminal ) throws SisatException {
		return menuDao.registrarPerfil(descripcion , usuarioId, terminal);
	}
	
	@Override
	public int cambioEstadoPerfil( int perfilId ,int estado ,int usuarioId,String terminal) throws SisatException {
		return menuDao.cambioEstadoPerfil(perfilId, estado , usuarioId, terminal);
	}
	
	@Override
	public List<PerfilesDTO> getPerfilesUsuario(int usuarioId,int supervisor_caja_id,int caja_id) throws SisatException {
		return menuDao.getPerfilesUsuario(usuarioId, supervisor_caja_id, caja_id);
	}
	
	@Override
	public int cambioEstadoPerfilUsuario( int perfilId, int usuarioPerfilId ,int estado ,int usuarioId,String terminal) throws SisatException {
		return menuDao.cambioEstadoPerfilUsuario(perfilId, usuarioPerfilId , estado , usuarioId, terminal);
	}
	
	@Override
	public List<SimpleMenuDTO> getAccesosSubmenuUsuario(int usuarioId, int submenuId) throws SisatException {
		return menuDao.getAccesosSubmenuUsuario(usuarioId,submenuId);
	}

}
