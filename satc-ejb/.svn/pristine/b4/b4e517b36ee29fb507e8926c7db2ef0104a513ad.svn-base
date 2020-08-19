package com.sat.sisat.menus.dao;
//// -=CRAMIREZ=-
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.sat.sisat.common.dao.GeneralDao;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.menus.dto.MenusDTO;
import com.sat.sisat.menus.dto.PerfilesDTO;
import com.sat.sisat.menus.dto.SimpleMenuDTO;
import com.sat.sisat.menus.dto.SubmenuNivelAccesoDTO;
import com.sat.sisat.menus.dto.TipoMenuDTO;
import com.sat.sisat.persistence.CrudServiceBean;

public class MenuDao extends GeneralDao{

	public List<MenusDTO> getMenus(int tipo) throws SisatException {
		List<MenusDTO> list = new ArrayList<MenusDTO>();
		
		try {
			String SQL = new String("dbo.sg_menu_submenu ?");
		
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, tipo);
				
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {	
				MenusDTO obj = new MenusDTO();
				
				obj.setMenuId(rs.getInt("menu_id"));
				obj.setSubMenuId(rs.getInt("submenu_id"));
				obj.setMenu(rs.getString("menu"));
				obj.setSubmenu(rs.getString("submenu"));
				obj.setUrl(rs.getString("url"));
				obj.setTipo(rs.getString("tipo"));
				obj.setEstado(rs.getInt("estado"));
				
				list.add(obj);
			}
			
		} catch (Exception ex) {
			String msg = "No se ha podido recuperar informacion: " ;
			System.out.println(msg + ": " + ex);
			throw new SisatException(msg);
		}
		
		return list;
		
	}
	
	
	public List<SubmenuNivelAccesoDTO> getSubmenuNivelAcceso(int submenuId) throws SisatException {
		List<SubmenuNivelAccesoDTO> list = new ArrayList<SubmenuNivelAccesoDTO>();
		
		try {
			String SQL = new String("dbo.sg_submenu_nivel_acceso ?");
		
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, submenuId);
				
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {	
				SubmenuNivelAccesoDTO obj = new SubmenuNivelAccesoDTO();
				
				obj.setNivelAccesoId(rs.getInt("nivel_acceso_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				obj.setHabilitado(rs.getBoolean("habilitado"));
				
				list.add(obj);
			}
			
		} catch (Exception ex) {
			String msg = "No se ha podido recuperar informacion: " ;
			System.out.println(msg + ": " + ex);
			throw new SisatException(msg);
		}
		
		return list;
		
	}
	
	public int cambioSubmenuAcceso(int submenuId ,  int nivelAccesoId , int estado,int usuarioId ,String terminal) throws SisatException {
		int result = 0;
		
		try {
			String SQL = new String("dbo.sg_cambio_nivel_acceso ?, ?, ?, ?, ?");
		
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, submenuId);
			pst.setInt(2, nivelAccesoId);
			pst.setInt(3, estado);
			pst.setInt(4, usuarioId);
			pst.setString(5, terminal);
				
			result = pst.executeUpdate();
			
			//result = 1;
			
		} catch (Exception ex) {
			String msg = "Ocurrio un error al actualizar : " ;
			System.out.println(msg + ": " + ex);
			throw new SisatException(msg);
		}
		
		return result;
		
	}
	
	public List<PerfilesDTO> getPerfiles() throws SisatException {
		List<PerfilesDTO> list = new ArrayList<PerfilesDTO>();
		
		try {
			String SQL = new String("dbo.sg_get_perfil");
		
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
				
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {	
				PerfilesDTO obj = new PerfilesDTO();
				
				obj.setPerfilId(rs.getInt("perfil_id"));
				obj.setNombre(rs.getString("descripcion"));
				obj.setEstado(rs.getBoolean("estado"));
				
				list.add(obj);
			}
			
		} catch (Exception ex) {
			String msg = "No se ha podido recuperar informacion: " ;
			System.out.println(msg + ": " + ex);
			throw new SisatException(msg);
		}
		
		return list;
		
	}
	
	public List<MenusDTO> getPermisosPerfil(int perfilId, int tipo) throws SisatException {
		List<MenusDTO> list = new ArrayList<MenusDTO>();
		
		try {
			String SQL = new String("dbo.sg_get_permisos_perfil ?, ?");
		
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, perfilId);
			pst.setInt(2, tipo);
				
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {	
				MenusDTO obj = new MenusDTO();
				
				obj.setMenuId(rs.getInt("menu_id"));
				obj.setSubMenuId(rs.getInt("submenu_id"));
				obj.setMenu(rs.getString("menu"));
				obj.setSubmenu(rs.getString("submenu"));
				obj.setUrl(rs.getString("url"));
				obj.setTipo(rs.getString("tipo"));
				obj.setNivelAcceso(rs.getString("acceso"));
				obj.setPerfilSubmenuId(rs.getInt("perfil_submenu_id"));
				
				list.add(obj);
			}
			
		} catch (Exception ex) {
			String msg = "No se ha podido recuperar informacion: " ;
			System.out.println(msg + ": " + ex);
			throw new SisatException(msg);
		}
		
		return list;
		
	}
	
	
	public int eliminarPermisoPerfil( int perfilSubmenuId ,int usuarioIdUpd ,String terminalUpd) throws SisatException {
		int result = 0;
		
		try {
			String SQL = new String("dbo.sg_eliminar_perfil_submenu ?, ?, ?");
		
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, perfilSubmenuId);
			pst.setInt(2, usuarioIdUpd);
			pst.setString(3, terminalUpd);
		
			result = pst.executeUpdate();
			
			
		} catch (Exception ex) {
			String msg = "Ocurrio un error al actualizar : " ;
			System.out.println(msg + ": " + ex);
			throw new SisatException(msg);
		}
		
		return result;
		
	}
	
	public List<TipoMenuDTO> getTipoMenu() throws SisatException {
		List<TipoMenuDTO> list = new ArrayList<TipoMenuDTO>();
		
		try {
			String SQL = new String("dbo.sg_get_tipo_menu");
		
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
				
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {	
				TipoMenuDTO obj = new TipoMenuDTO();
				
				obj.setTipoMenuId(rs.getInt("tipo_menu_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				obj.setEstado(rs.getInt("estado"));
				obj.setUsuarioId(rs.getInt("usuario_id"));
				
				list.add(obj);
			}
			
		} catch (Exception ex) {
			String msg = "No se ha podido recuperar informacion: " ;
			System.out.println(msg + ": " + ex);
			throw new SisatException(msg);
		}
		
		return list;
		
	}
	
	public List<SimpleMenuDTO> getMenuTipo(int tipo) throws SisatException {
		List<SimpleMenuDTO> list = new ArrayList<SimpleMenuDTO>();
		
		try {
			String SQL = new String("dbo.sg_get_menu_tipo ?");
		
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, tipo);
				
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {	
				SimpleMenuDTO obj = new SimpleMenuDTO();
				
				obj.setItemId(rs.getInt("menu_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				obj.setOrden(rs.getInt("orden"));
				obj.setEstado(rs.getInt("estado"));
				obj.setUsuarioId(rs.getInt("usuario_id"));
				
				list.add(obj);
			}
			
		} catch (Exception ex) {
			String msg = "No se ha podido recuperar informacion: " ;
			System.out.println(msg + ": " + ex);
			throw new SisatException(msg);
		}
		
		return list;
		
	}
	
	public List<SimpleMenuDTO> getSubmenu(int menuId) throws SisatException {
		List<SimpleMenuDTO> list = new ArrayList<SimpleMenuDTO>();
		
		try {
			String SQL = new String("dbo.sg_get_submenu ?");
		
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, menuId);
				
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {	
				SimpleMenuDTO obj = new SimpleMenuDTO();
				
				obj.setItemId(rs.getInt("submenu_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				obj.setOrden(rs.getInt("orden"));
				obj.setEstado(rs.getInt("estado"));
				obj.setUsuarioId(rs.getInt("usuario_id"));
				
				list.add(obj);
			}
			
		} catch (Exception ex) {
			String msg = "No se ha podido recuperar informacion: " ;
			System.out.println(msg + ": " + ex);
			throw new SisatException(msg);
		}
		
		return list;		
	}
	
	public List<SimpleMenuDTO> getAccesosSubmenu(int sunmenuId) throws SisatException {
		List<SimpleMenuDTO> list = new ArrayList<SimpleMenuDTO>();
		
		try {
			String SQL = new String("dbo.sg_get_accesos_submenu ?");
		
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, sunmenuId);
				
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {	
				SimpleMenuDTO obj = new SimpleMenuDTO();
				
				obj.setItemId(rs.getInt("nivel_acceso_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				obj.setUsuarioId(rs.getInt("usuario_id"));
				obj.setHabilitado(false);
				
				list.add(obj);
			}
			
		} catch (Exception ex) {
			String msg = "No se ha podido recuperar informacion: " ;
			System.out.println(msg + ": " + ex);
			throw new SisatException(msg);
		}
		
		return list;
		
	}
	
	public int registrarPermisoPerfil( int submenuId ,int perfilId ,int usuarioId,String terminal,String listNivelAcceso) throws SisatException {
		int result = 0;
		
		try {
			String SQL = new String("dbo.sg_registrar_submenu_acceso ?, ?, ?, ?, ?");
		
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, submenuId);
			pst.setInt(2, perfilId);
			pst.setInt(3, usuarioId);
			pst.setString(4, terminal);
			pst.setString(5, listNivelAcceso);

			result = pst.executeUpdate();
			
			
		} catch (Exception ex) {
			String msg = "Ocurrio un error al registrar : " ;
			System.out.println(msg + ": " + ex);
			throw new SisatException(msg);
		}
		
		return result;
		
	}
	
	public int registrarPerfil( String descripcion ,int usuarioId,String terminal) throws SisatException {
		int result = 0;
		
		try {
			String SQL = new String("dbo.sg_registrar_perfil ?, ?, ?");
		
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setString(1, descripcion);
			pst.setInt(2, usuarioId);
			pst.setString(3, terminal);

			result = pst.executeUpdate();
			
			
		} catch (Exception ex) {
			String msg = "Ocurrio un error al registrar : " ;
			System.out.println(msg + ": " + ex);
			throw new SisatException(msg);
		}
		
		return result;
		
	}
	
	public int cambioEstadoPerfil( int perfilId ,int estado ,int usuarioId,String terminal) throws SisatException {
		int result = 0;
		
		try {
			String SQL = new String("dbo.sg_cambio_estado_perfil ?, ?, ?, ?");
		
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, perfilId);
			pst.setInt(2, estado);
			pst.setInt(3, usuarioId);
			pst.setString(4, terminal);

			result = pst.executeUpdate();
			
		} catch (Exception ex) {
			String msg = "Ocurrio un error al registrar : " ;
			System.out.println(msg + ": " + ex);
			throw new SisatException(msg);
		}
		
		return result;
		
	}
	
	public List<PerfilesDTO> getPerfilesUsuario(int usuarioId,int supervisor_caja_id,int caja_id) throws SisatException {
		List<PerfilesDTO> list = new ArrayList<PerfilesDTO>();
		
		try {
			String SQL = new String("dbo.sg_get_perfil_usuario ?,?,?");
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, usuarioId);
			pst.setInt(2, supervisor_caja_id);
			pst.setInt(3, caja_id);
			
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {	
				PerfilesDTO obj = new PerfilesDTO();
				
				obj.setPerfilId(rs.getInt("perfil_id"));
				obj.setNombre(rs.getString("descripcion"));
				obj.setEstado(rs.getBoolean("estado"));
				obj.setSuperfisorCaja(rs.getBoolean("rol_supervisor_caja"));
				obj.setCajero(rs.getBoolean("rol_cajero"));
				
				list.add(obj);
			}
			
		} catch (Exception ex) {
			String msg = "No se ha podido recuperar informacion: " ;
			System.out.println(msg + ": " + ex);
			throw new SisatException(msg);
		}
		
		return list;
		
	}
	
	public int cambioEstadoPerfilUsuario( int perfilId, int usuarioPerfilId  ,int estado ,int usuarioId,String terminal) throws SisatException {
		int result = 0;
		
		try {
			String SQL = new String("dbo.sg_cambio_perfil_usuario ?, ?, ?, ?, ?");
		
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, usuarioPerfilId);
			pst.setInt(2, perfilId);
			pst.setInt(3, estado);
			pst.setInt(4, usuarioId);
			pst.setString(5, terminal);

			result = pst.executeUpdate();
			
		} catch (Exception ex) {
			String msg = "Ocurrio un error al registrar : " ;
			System.out.println(msg + ": " + ex);
			throw new SisatException(msg);
		}
		
		return result;
		
	}
	
	public List<SimpleMenuDTO> getAccesosSubmenuUsuario(int usuarioId, int sunmenuId) throws SisatException {
		List<SimpleMenuDTO> list = new ArrayList<SimpleMenuDTO>();
		
		try {
			String SQL = new String("dbo.sg_get_accesos_submenu_usuario ?, ? ");
		
			PreparedStatement pst = connect().prepareStatement(SQL.toString());
			pst.setInt(1, usuarioId);
			pst.setInt(2, sunmenuId);
			
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {	
				SimpleMenuDTO obj = new SimpleMenuDTO();
				
				obj.setItemId(rs.getInt("nivel_acceso_id"));
				obj.setDescripcion(rs.getString("descripcion"));
				obj.setUsuarioId(rs.getInt("usuario_id"));
				obj.setHabilitado(false);
				
				list.add(obj);
			}
			
		} catch (Exception ex) {
			String msg = "No se ha podido recuperar informacion: " ;
			System.out.println(msg + ": " + ex);
			throw new SisatException(msg);
		}
		
		return list;
		
	}
}
