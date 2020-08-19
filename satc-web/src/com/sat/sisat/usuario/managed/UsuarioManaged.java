package com.sat.sisat.usuario.managed;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.event.ActionEvent;

import org.richfaces.model.selection.SimpleSelection;

import com.sat.sisat.caja.business.CajaBoRemote;
import com.sat.sisat.caja.dto.AgenciaUsuarioDTO;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.menus.business.MenuBoRemote;
import com.sat.sisat.menus.dto.PerfilesDTO;
import com.sat.sisat.persistence.entity.CjAgencia;
import com.sat.sisat.persistence.entity.CjAgenciaUsuario;
import com.sat.sisat.persistence.entity.SgUsuario;
import com.sat.sisat.persistence.entity.SgUsuarioAcceso;
import com.sat.sisat.usuario.business.UsuarioBoRemote;
import com.sat.sisat.usuario.dto.RolesUsuarioDTO;

@ManagedBean
@ViewScoped
public class UsuarioManaged extends BaseManaged {

	private static final long serialVersionUID = -3544830243114448278L;

	@EJB
	private UsuarioBoRemote userBo;
	
	@EJB
	private CajaBoRemote cajaBo;
	
	@EJB
	private MenuBoRemote menuBo;

	// Usuario
	private List<SgUsuario> usuarios = new ArrayList<SgUsuario>();
	private SimpleSelection selectedUsuario;
	private int rowIndexSelected = -1;

	private int usuarioId;
	private String apellidos;
	private String nombres;
	private String nombreUsuario;
	private String clave;
	private boolean usuarioActivo;

	private boolean saveCorrecto;
	private int estadoSelectPerfil;
	
	// Roles
	private List<RolesUsuarioDTO> roles = new ArrayList<RolesUsuarioDTO>();
	private SimpleSelection simpleSelectedRol;
	private RolesUsuarioDTO selectedRol;
	
	//UsuarioAcceso
	private List<SgUsuarioAcceso> usuariosAcceso = new ArrayList<SgUsuarioAcceso>();
	private Date fechaIni = new Date();
	private Date fechaFin = new Date();
	
	// Para control de usuarios caja
	private boolean supervisor = false;
	private boolean cajero = false;
	List<AgenciaUsuarioDTO> agencias = new ArrayList<AgenciaUsuarioDTO>();
	List<AgenciaUsuarioDTO> agenciasCajero = new ArrayList<AgenciaUsuarioDTO>();
	private Integer agenCajeroId;
	private boolean correcto = false;
	
	// Inicio Para Perfiles
	private List<PerfilesDTO> listPerfiles = new ArrayList<PerfilesDTO>();
	private List<PerfilesDTO> listTempPerfiles = new ArrayList<PerfilesDTO>();
	private PerfilesDTO selectPerfil;
	private PerfilesDTO selectTempPerfil;
	// Fin Para Perfiles 

	@PostConstruct
	public void inicialize() {
		usuarios = userBo.getAllSgUsuario();
		agencias = cajaBo.getAllAgenciasActivas(); 
	}

	public void selectUsuarioAction() {
		if (selectedUsuario != null) {
			Iterator<Object> it = selectedUsuario.getKeys();
			while (it.hasNext()) {
				Object obj = it.next();
				try {
					rowIndexSelected = Integer.parseInt(String.valueOf(obj));
					SgUsuario sgu = usuarios.get(rowIndexSelected);
					usuarioId = sgu.getUsuarioId();
					roles = userBo.getRolesHabilitados(sgu.getUsuarioId());
					selectedRol = null;
					usuariosAcceso = new ArrayList<SgUsuarioAcceso>();
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
		}
	}
	
	// Inicio Para Perfiles
	public String getPerfiles(int usuarioId) {
		try {		
			listPerfiles = menuBo.getPerfilesUsuario(usuarioId, Constante.SUPERVISOR_CAJA, Constante.CAJERO); 
			listTempPerfiles = menuBo.getPerfilesUsuario(usuarioId, Constante.SUPERVISOR_CAJA, Constante.CAJERO); 
			System.out.println("getPerfiles");
		} catch (SisatException ex) {
			String msg = "No se ha recuperado los permisos";
			System.out.println(msg + ex);
		}
		return null;
	}
	
	public void selectUsuarioPerfilAction() {
		if (selectedUsuario != null) {
			Iterator<Object> it = selectedUsuario.getKeys();
			while (it.hasNext()) {
				Object obj = it.next();
				try {
					rowIndexSelected = Integer.parseInt(String.valueOf(obj));
					SgUsuario sgu = usuarios.get(rowIndexSelected);
					usuarioId = sgu.getUsuarioId();
					//roles = userBo.getRolesHabilitados(sgu.getUsuarioId());
					getPerfiles(sgu.getUsuarioId());
					selectedRol = null;
					usuariosAcceso = new ArrayList<SgUsuarioAcceso>();
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
		}
	}
	public void selectPerfilTable() {
		if (simpleSelectedRol != null) {
			Iterator<Object> it = simpleSelectedRol.getKeys();
			while (it.hasNext()) {
				Object obj = it.next();
				try {
					int rowIndex = Integer.parseInt(String.valueOf(obj));
					selectPerfil = listPerfiles.get(rowIndex);
					selectTempPerfil = listTempPerfiles.get(rowIndex);
					
					/*int estadoSelectPerfil = 1;
					if(selectPerfil.getEstado()) {
						estadoSelectPerfil = 1;
					}else {
						estadoSelectPerfil = 0;
					}*/					
					supervisor = false;
					cajero = false;
					
					for(PerfilesDTO age : listPerfiles){
						if(selectPerfil.getPerfilId() != age.getPerfilId()) {
							age.setEstado(false);
						}
					}
					
					/*if(selectPerfil.getEstado() != selectTempPerfil.getEstado()) {
						System.out.println("Actualizar");
						int resultado = menuBo.cambioEstadoPerfilUsuario(selectPerfil.getPerfilId(), usuarioId ,estadoSelectPerfil , getSessionManaged().getUsuarioLogIn().getUsuarioId() ,getSessionManaged().getTerminalLogIn());
						if(resultado == 1) {
							getPerfiles(usuarioId);
						}
					}*/
					
					
				} catch (Exception ex) {
				}
			}
		}
	}
	public String guardarNewRolesUsuario(){
		if(selectPerfil.getSuperfisorCaja()) {
			System.out.println("SUpervisor");
			supervisor = true;
			//Richfaces.showModalPanel(mpAgenciaUsuario);
		}else if(selectPerfil.getCajero()){
			System.out.println("cajero");
			cajero = true;
		}
		try{
			if(supervisor || cajero){
				// show window agencia_asuario
				List<AgenciaUsuarioDTO> lista = new ArrayList<AgenciaUsuarioDTO>(cajaBo.getAgenciaUsuario(usuarioId));
				if(supervisor && cajero){
					agencias = cajaBo.getAllAgenciasActivas();
					agenciasCajero = cajaBo.getAllAgenciasActivas();
					for(AgenciaUsuarioDTO ageU : lista){
						for(AgenciaUsuarioDTO age : agencias){
							if(ageU.getTipoRol().equals(Constante.TIPO_ROL_SUPERVISOR) && age.getAgenciaId() == ageU.getAgenciaId()){
								age.setFechaInicio(ageU.getFechaInicio());
								age.setFechaFin(ageU.getFechaFin());
								age.setIpAsignada(ageU.getIpAsignada());
								age.setSelected(true);
							}
						}
						for(AgenciaUsuarioDTO age : agenciasCajero){
							if(ageU.getTipoRol().equals(Constante.TIPO_ROL_CAJERO) && age.getAgenciaId() == ageU.getAgenciaId()){
								age.setFechaInicio(ageU.getFechaInicio());
								age.setFechaFin(ageU.getFechaFin());
								age.setIpAsignada(ageU.getIpAsignada());
								age.setSelected(true);
							}
						}
					}
				}else if(supervisor){
					agencias = cajaBo.getAllAgenciasActivas();
					agenciasCajero = new ArrayList<AgenciaUsuarioDTO>();
					for(AgenciaUsuarioDTO ageU : lista){
						for(AgenciaUsuarioDTO age : agencias){
							if(ageU.getTipoRol().equals(Constante.TIPO_ROL_SUPERVISOR) && age.getAgenciaId() == ageU.getAgenciaId()){
								age.setFechaInicio(ageU.getFechaInicio());
								age.setFechaFin(ageU.getFechaFin());
								age.setIpAsignada(ageU.getIpAsignada());
								age.setSelected(true);
							}
						}
					}
				}else{
					agencias = new ArrayList<AgenciaUsuarioDTO>();
					agenciasCajero = cajaBo.getAllAgenciasActivas();
					for(AgenciaUsuarioDTO ageU : lista){
						for(AgenciaUsuarioDTO age : agenciasCajero){
							if(ageU.getTipoRol().equals(Constante.TIPO_ROL_CAJERO) && age.getAgenciaId() == ageU.getAgenciaId()){
								age.setFechaInicio(ageU.getFechaInicio());
								age.setFechaFin(ageU.getFechaFin());
								age.setIpAsignada(ageU.getIpAsignada());
								age.setSelected(true);
							}
						}
					}
				}
			}else{
				if(selectPerfil.getEstado()) {
					estadoSelectPerfil = 1;
				}else {
					estadoSelectPerfil = 0;
				}
				int resultado = menuBo.cambioEstadoPerfilUsuario(selectPerfil.getPerfilId(), usuarioId ,estadoSelectPerfil , getSessionManaged().getUsuarioLogIn().getUsuarioId() ,getSessionManaged().getTerminalLogIn());
				if(resultado == 1) {
					getPerfiles(usuarioId);
					addInfoMessage("Roles actualizados con éxito");
				}else {
					addErrorMessage("No se ha podido guardar los datos");
				}
			}
		}catch(Exception ex){
			addErrorMessage("No se ha podido guardar los datos");
		}
		return null;
	}
	// Fin Para Perfiles 

	public void nuevoUsuario() {
		inicializeDataUser();
	}

	public String guardarUsuario() {
		saveCorrecto = false;
		if (datosNulos()) {
			return null;
		}

		try{
			SgUsuario us = new SgUsuario();
			us.setNombreUsuario(nombreUsuario);
			us.setClave(clave);
			us.setApellidos(apellidos);
		us.setNombres(nombres);
			if (isUsuarioActivo()) {
				us.setEstado("1");
			} else {
				us.setEstado("0");
			}
			us.setUsuarioActualizacion(getSessionManaged().getUsuarioLogIn().getUsuarioId());
			us.setFechaActualizacion(DateUtil.getCurrentDate());
			us.setTerminal(getSessionManaged().getTerminalLogIn());
			Integer usrId = userBo.crearUsuario(us);
			if (usrId != null) {
				us.setUsuarioId(usrId);
				usuarios.add(0, us);
				roles = new ArrayList<RolesUsuarioDTO>();
				usuariosAcceso = new ArrayList<SgUsuarioAcceso>();
				saveCorrecto = true;
				addInfoMessage("Usuario creado con éxito");
			} else {
				addErrorMessage("No ha sido posible crear el usuario");
			}
		}catch(SisatException ex){
			addErrorMessage(ex.getMessage());
		}
		return null;
	}

	private void inicializeDataUser() {
		usuarioId = 0;
		setApellidos("");
		setNombres("");
		setNombreUsuario("");
		setClave("");
		setUsuarioActivo(true);
	}

	private boolean datosNulos() {
		boolean rs = false;
		if (apellidos == null || apellidos.isEmpty()) {
			rs = true;
			addErrorMessage("Apellidos: Esta vacio, porfavor verifique");
		}
		if (nombres == null || nombres.isEmpty()) {
			rs = true;
			addErrorMessage("Nombres: Esta vacio, porfavor verifique");
		}
		if (nombreUsuario == null || nombreUsuario.isEmpty()) {
			rs = true;
			addErrorMessage("Nombre de usuario: Esta vacio, porfavor verifique");
		}
		if (clave == null || clave.isEmpty()) {
			rs = true;
			addErrorMessage("Clave: Esta vacio, porfavor verifique");
		}
		return rs;
	}

	public String openActualizacion() {
		try{
		inicializeDataUser();

		String rowIndex = getRequestParameter("rowIndexUser");
		if (rowIndex != null) {
			int rowI = Integer.parseInt(rowIndex);
			SgUsuario sgu = usuarios.get(rowI);
			usuarioId = sgu.getUsuarioId();
			setApellidos(sgu.getApellidos());
			setNombres(sgu.getNombres());
			if (sgu.getEstado().equals("1")) {
				setUsuarioActivo(true);
			} else {
				setUsuarioActivo(false);
			}
		}
		} catch (SisatException e) {			
			WebMessages.messageFatal(e);
		}
		return null;
	}

	public String actualizarUsuario() {
		try {
			saveCorrecto = false;

			if (usuarioId == 0) {
				addErrorMessage("Se ha perdido el usuario seleccionado");
				return null;
			}

			SgUsuario us = new SgUsuario();
			us.setUsuarioId(usuarioId);
			us.setApellidos(apellidos);
			us.setNombres(nombres);
			if (isUsuarioActivo()) {
				us.setEstado(Constante.ESTADO_ACTIVO);
			} else {
				us.setEstado(Constante.ESTADO_INACTIVO);
			}
			us.setUsuarioActualizacion(getSessionManaged().getUsuarioLogIn().getUsuarioId());
			us.setFechaActualizacion(DateUtil.getCurrentDate());
			us.setTerminal(getSessionManaged().getTerminalLogIn());

			boolean rs = userBo.actualizaUsuario(us);

			if (rs) {
				saveCorrecto = true;
				addInfoMessage("Datos actualizados con éxito");
				if (rowIndexSelected != -1) {
					usuarios.get(rowIndexSelected).setApellidos(us.getApellidos());
					usuarios.get(rowIndexSelected).setNombres(us.getNombres());
					usuarios.get(rowIndexSelected).setEstado(us.getEstado());
				}
			} else {
				addErrorMessage("No ha sido posible actualizar los datos del usuario");
			}
		} catch (SisatException e) {
			WebMessages.messageFatal(e);
		}
		return null;
	}
	
	public String guardarRolesUsuario(){
		supervisor = false;
		cajero = false;
		try{
			// 2. Si el rol es supervidor o cajero, debe mostrar una pantalla para asignarle a una agencia como supervisor o cajero, segun el caso.
			if (usuarioId == 0) {
				addErrorMessage("Se ha perdido el usuario seleccionado");
				return null;
			}
			
			for(RolesUsuarioDTO ru : roles){
				if(ru.isHabilitado()){
					if(ru.getRolId() == Constante.ROL_SUPERVISOR_ID){
						supervisor = true;
					}
					if(ru.getRolId() == Constante.ROL_CAJERO_ID){
						cajero = true;
					}
				}
			}

			if(supervisor || cajero){
				// show window agencia_asuario
				List<AgenciaUsuarioDTO> lista = new ArrayList<AgenciaUsuarioDTO>(cajaBo.getAgenciaUsuario(usuarioId));
				if(supervisor && cajero){
					agencias = cajaBo.getAllAgenciasActivas();
					agenciasCajero = cajaBo.getAllAgenciasActivas();
					for(AgenciaUsuarioDTO ageU : lista){
						for(AgenciaUsuarioDTO age : agencias){
							if(ageU.getTipoRol().equals(Constante.TIPO_ROL_SUPERVISOR) && age.getAgenciaId() == ageU.getAgenciaId()){
								age.setFechaInicio(ageU.getFechaInicio());
								age.setFechaFin(ageU.getFechaFin());
								age.setIpAsignada(ageU.getIpAsignada());
								age.setSelected(true);
							}
						}
						for(AgenciaUsuarioDTO age : agenciasCajero){
							if(ageU.getTipoRol().equals(Constante.TIPO_ROL_CAJERO) && age.getAgenciaId() == ageU.getAgenciaId()){
								age.setFechaInicio(ageU.getFechaInicio());
								age.setFechaFin(ageU.getFechaFin());
								age.setIpAsignada(ageU.getIpAsignada());
								age.setSelected(true);
							}
						}
					}
				}else if(supervisor){
					agencias = cajaBo.getAllAgenciasActivas();
					agenciasCajero = new ArrayList<AgenciaUsuarioDTO>();
					for(AgenciaUsuarioDTO ageU : lista){
						for(AgenciaUsuarioDTO age : agencias){
							if(ageU.getTipoRol().equals(Constante.TIPO_ROL_SUPERVISOR) && age.getAgenciaId() == ageU.getAgenciaId()){
								age.setFechaInicio(ageU.getFechaInicio());
								age.setFechaFin(ageU.getFechaFin());
								age.setIpAsignada(ageU.getIpAsignada());
								age.setSelected(true);
							}
						}
					}
				}else{
					agencias = new ArrayList<AgenciaUsuarioDTO>();
					agenciasCajero = cajaBo.getAllAgenciasActivas();
					for(AgenciaUsuarioDTO ageU : lista){
						for(AgenciaUsuarioDTO age : agenciasCajero){
							if(ageU.getTipoRol().equals(Constante.TIPO_ROL_CAJERO) && age.getAgenciaId() == ageU.getAgenciaId()){
								age.setFechaInicio(ageU.getFechaInicio());
								age.setFechaFin(ageU.getFechaFin());
								age.setIpAsignada(ageU.getIpAsignada());
								age.setSelected(true);
							}
						}
					}
				}
			}else{
				cajaBo.cambiarEstadoAgenciaUsuario(usuarioId, Constante.ESTADO_INACTIVO);
				Set<RolesUsuarioDTO> collec = new HashSet<RolesUsuarioDTO>(); 
				for(RolesUsuarioDTO ru : roles){
					collec.add(ru);// send all is necessary for inactive
				}
				boolean save = userBo.guardarRolUsuario(usuarioId, collec, getSessionManaged().getTerminalLogIn());
				if(save){
					roles = userBo.getRolesHabilitados(usuarioId);
					addInfoMessage("Roles actualizados con éxito");
				}else{
					addErrorMessage("No se ha podido actualizar los roles");
				}
			}
		}catch(Exception ex){
			addErrorMessage("No se ha podido guardar los datos");
		}
		return null;
	}
	
	public String guardarConfiguracionAgencia(ActionEvent ev){
		correcto = false;
		try{
			List<CjAgenciaUsuario> lista = new ArrayList<CjAgenciaUsuario>();
			if(supervisor){
				for(AgenciaUsuarioDTO a : agencias){
					if(a.isSelected()){
						SgUsuario usu = new SgUsuario();
						usu.setUsuarioId(usuarioId);
						CjAgencia age = new CjAgencia();
						age.setAgenciaId(a.getAgenciaId());
						CjAgenciaUsuario ageUsu = new CjAgenciaUsuario();
						ageUsu.setSgUsuario(usu);
						ageUsu.setCjAgencia(age);
						ageUsu.setFechaInicio(DateUtil.dateToSqlTimestamp(a.getFechaInicio()));
						if(a.getFechaFin() != null){
							ageUsu.setFechaFin(DateUtil.dateToSqlTimestamp(a.getFechaFin()));
						}
						ageUsu.setIpAsignada(a.getIpAsignada());
						ageUsu.setEstado(Constante.ESTADO_ACTIVO);
						ageUsu.setFechaRegistro(DateUtil.getCurrentDate());
						ageUsu.setTerminal(getSessionManaged().getTerminalLogIn());
						ageUsu.setTipoRol(Constante.TIPO_ROL_SUPERVISOR);
						lista.add(ageUsu);
					}
				}
			}
			if(cajero){
				for(AgenciaUsuarioDTO a : agenciasCajero){
					if(a.getAgenciaId() == agenCajeroId){
						SgUsuario usu = new SgUsuario();
						usu.setUsuarioId(usuarioId);
						CjAgencia age = new CjAgencia();
						age.setAgenciaId(a.getAgenciaId());
						CjAgenciaUsuario ageUsu = new CjAgenciaUsuario();
						ageUsu.setSgUsuario(usu);
						ageUsu.setCjAgencia(age);
						ageUsu.setFechaInicio(DateUtil.dateToSqlTimestamp(a.getFechaInicio()));
						if(a.getFechaFin() != null){
							ageUsu.setFechaFin(DateUtil.dateToSqlTimestamp(a.getFechaFin()));
						}
						ageUsu.setIpAsignada(a.getIpAsignada());
						ageUsu.setEstado(Constante.ESTADO_ACTIVO);
						ageUsu.setFechaRegistro(DateUtil.getCurrentDate());
						ageUsu.setTerminal(getSessionManaged().getTerminalLogIn());
						ageUsu.setTipoRol(Constante.TIPO_ROL_CAJERO);
						lista.add(ageUsu);
						break;
					}
				}
			}
			
			Set<RolesUsuarioDTO> collec = new HashSet<RolesUsuarioDTO>(); 
			for(RolesUsuarioDTO ru : roles){
				collec.add(ru);// send all is necessary for inactive
			}
			// INICIO ANTES DE PERFILES -=CRAMIREZ=-
				//userBo.guardarRolUsuario(usuarioId, collec, getSessionManaged().getTerminalLogIn());
				//cajaBo.registrarAgenciaUsuario(lista);
				//roles = userBo.getRolesHabilitados(usuarioId);
				//correcto = true;
				//addInfoMessage("Datos guardados con éxito");
			// FIN ANTES DE PERFILES 
				
			// INICIO NUEVO DE PERFILES -=CRAMIREZ=-
				if(selectPerfil.getEstado()) {
					estadoSelectPerfil = 1;
				}else {
					estadoSelectPerfil = 0;
				}
				int resultado = menuBo.cambioEstadoPerfilUsuario(selectPerfil.getPerfilId(), usuarioId ,estadoSelectPerfil , getSessionManaged().getUsuarioLogIn().getUsuarioId() ,getSessionManaged().getTerminalLogIn());
				if(resultado == 1) {
					cajaBo.registrarAgenciaUsuario(lista);
					getPerfiles(usuarioId);
					addInfoMessage("Datos guardados con éxito");
				}else {
					addErrorMessage("No se ha podido guardar los datos");
				}
				correcto = true;
			// FIN NUEVO DE PERFILES
			
		}catch(Exception ex){
			String msg = "Ha ocurrido un error guardando la configuración de agencias";
			addErrorMessage(msg);
			System.out.println(msg + ex);
		}
		return null;
	}
	
	public String cancelarRegAgenUsuario(){
		roles = userBo.getRolesHabilitados(usuarioId);
		return null;
	}
	
	public void selectRolAction() {
		selectedRol = null;
		if (simpleSelectedRol != null) {
			Iterator<Object> it = simpleSelectedRol.getKeys();
			while (it.hasNext()) {
				Object obj = it.next();
				try {
					int rowIndex = Integer.parseInt(String.valueOf(obj));
					selectedRol = roles.get(rowIndex);
					if(selectedRol.isHabilitado()){
						usuariosAcceso = userBo.getUsuarioAcceso(usuarioId, selectedRol.getRolId());
					}else{
						selectedRol = null;
						usuariosAcceso = new ArrayList<SgUsuarioAcceso>();
					}
				} catch (Exception ex) {
				}
			}
		}
	}
	
	
	
	private void inicializaUsuarioAcceso(){
		fechaIni = new Date();
		fechaFin = new Date();
	}
	
	public String nuevoUsuarioAcceso(){
		inicializaUsuarioAcceso();
		return null;
	}
	
	public String guardaUsuarioAcceso(){
		try {
			if (fechaIni == null || fechaFin == null) {
				addErrorMessage("Las fechas no pueden quedar vacias. Por favor verifique");
				return null;
			}

			SgUsuarioAcceso ua = new SgUsuarioAcceso();
			// ua.setRolUsuarioId();
			ua.setFechaInicio(DateUtil.dateToSqlTimestamp(fechaIni));
			ua.setFechaFin(DateUtil.dateToSqlTimestamp(fechaFin));
			ua.setEstado(Constante.ESTADO_ACTIVO);
			ua.setFechaRegistro(DateUtil.getCurrentDate());
			ua.setTerminal(getSessionManaged().getTerminalLogIn());
			ua.setUsuarioId(getSessionManaged().getUsuarioLogIn().getUsuarioId());

			Integer uaId = userBo.crearUsuarioAcceso(ua, selectedRol.getRolId(), usuarioId);
			if (uaId != null) {
				saveCorrecto = true;
				usuariosAcceso = userBo.getUsuarioAcceso(usuarioId, selectedRol.getRolId());
				addInfoMessage("Datos guardados con éxito");
			} else {
				addErrorMessage("No ha sido posible guardar los datos");
			}
		} catch (SisatException e) {
			WebMessages.messageFatal(e);
		}
		return null;
	}
	
	public void selectUsuarioAcceso(ActionEvent ev){
		UIComponent comp = ev.getComponent().getParent().getParent();
		if (comp != null) {
			UIData uiData = (UIData) comp;
			SgUsuarioAcceso ua = (SgUsuarioAcceso) uiData.getRowData();
			setFechaIni(ua.getFechaInicio());
			setFechaFin(ua.getFechaFin());
		}
	}

	public List<SgUsuario> getUsuarios() {
		return usuarios;
	}

	public List<RolesUsuarioDTO> getRoles() {
		return roles;
	}

	public SimpleSelection getSelectedUsuario() {
		return selectedUsuario;
	}

	public void setSelectedUsuario(SimpleSelection selectedUsuario) {
		this.selectedUsuario = selectedUsuario;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public boolean isUsuarioActivo() {
		return usuarioActivo;
	}

	public void setUsuarioActivo(boolean usuarioActivo) {
		this.usuarioActivo = usuarioActivo;
	}

	public boolean isSaveCorrecto() {
		return saveCorrecto;
	}

	public List<SgUsuarioAcceso> getUsuariosAcceso() {
		return usuariosAcceso;
	}

	public Date getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(Date fechaIni) {
		this.fechaIni = fechaIni;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public int getUsuarioId() {
		return usuarioId;
	}

	public SimpleSelection getSimpleSelectedRol() {
		return simpleSelectedRol;
	}

	public void setSimpleSelectedRol(SimpleSelection simpleSelectedRol) {
		this.simpleSelectedRol = simpleSelectedRol;
	}

	public RolesUsuarioDTO getSelectedRol() {
		return selectedRol;
	}

	public void setSelectedRol(RolesUsuarioDTO selectedRol) {
		this.selectedRol = selectedRol;
	}

	public boolean isSupervisor() {
		return supervisor;
	}

	public void setSupervisor(boolean supervisor) {
		this.supervisor = supervisor;
	}

	public boolean isCajero() {
		return cajero;
	}

	public void setCajero(boolean cajero) {
		this.cajero = cajero;
	}

	public List<AgenciaUsuarioDTO> getAgencias() {
		return agencias;
	}

	public List<AgenciaUsuarioDTO> getAgenciasCajero() {
		return agenciasCajero;
	}

	public Integer getAgenCajeroId() {
		return agenCajeroId;
	}

	public void setAgenCajeroId(Integer agenCajeroId) {
		this.agenCajeroId = agenCajeroId;
	}

	public boolean isCorrecto() {
		return correcto;
	}

	public void setCorrecto(boolean correcto) {
		this.correcto = correcto;
	}

	public List<PerfilesDTO> getListPerfiles() {
		return listPerfiles;
	}

	public void setListPerfiles(List<PerfilesDTO> listPerfiles) {
		this.listPerfiles = listPerfiles;
	}

	public List<PerfilesDTO> getListTempPerfiles() {
		return listTempPerfiles;
	}

	public void setListTempPerfiles(List<PerfilesDTO> listTempPerfiles) {
		this.listTempPerfiles = listTempPerfiles;
	}

	public PerfilesDTO getSelectPerfil() {
		return selectPerfil;
	}

	public void setSelectPerfil(PerfilesDTO selectPerfil) {
		this.selectPerfil = selectPerfil;
	}

	public PerfilesDTO getSelectTempPerfil() {
		return selectTempPerfil;
	}

	public void setSelectTempPerfil(PerfilesDTO selectTempPerfil) {
		this.selectTempPerfil = selectTempPerfil;
	}
	
}
