package com.sat.sisat.menus.business;

import java.util.List;

import javax.ejb.Remote;

import com.sat.sisat.exception.SisatException;
import com.sat.sisat.menus.dto.MenusDTO;
import com.sat.sisat.menus.dto.PerfilesDTO;
import com.sat.sisat.menus.dto.SimpleMenuDTO;
import com.sat.sisat.menus.dto.SubmenuNivelAccesoDTO;
import com.sat.sisat.menus.dto.TipoMenuDTO;

@Remote
public interface MenuBoRemote {

	List<MenusDTO> getMenus(int tipo) throws SisatException;
	public abstract List<SubmenuNivelAccesoDTO> getSubmenuNivelAcceso(int submenuId) throws SisatException;
	public int cambioSubmenuAcceso(int submenuId ,  int nivelAccesoId , int estado,int usuarioId ,String terminal) throws SisatException;
	public abstract List<PerfilesDTO> getPerfiles() throws SisatException;
	public abstract List<MenusDTO> getPermisosPerfil(int perfilId, int tipo) throws SisatException;
	public int eliminarPermisoPerfil( int perfilSubmenuId ,int usuarioIdUpd ,String terminalUpd) throws SisatException;
	public abstract List<TipoMenuDTO> getTipoMenu() throws SisatException;
	public abstract List<SimpleMenuDTO> getMenuTipo(int tipo) throws SisatException;
	public abstract List<SimpleMenuDTO> getSubmenu(int menuId) throws SisatException;
	public abstract List<SimpleMenuDTO> getAccesosSubmenu(int submenuId) throws SisatException;
	public int registrarPermisoPerfil(  int submenuId ,int perfilId ,int usuarioId,String terminal,String listNivelAcceso ) throws SisatException;
	public int registrarPerfil(String descripcion ,int usuarioId,String terminal ) throws SisatException;
	public int cambioEstadoPerfil(int perfilId, int estado ,int usuarioId,String terminal ) throws SisatException;
	public abstract List<PerfilesDTO> getPerfilesUsuario(int usuarioId,int supervisor_caja_id,int caja_id) throws SisatException;
	public int cambioEstadoPerfilUsuario(int perfilId, int usuarioPerfilId, int estado ,int usuarioId,String terminal ) throws SisatException;
	public abstract List<SimpleMenuDTO> getAccesosSubmenuUsuario(int usuarioId, int submenuId) throws SisatException;
}
