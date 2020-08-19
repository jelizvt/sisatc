package com.sat.sisat.coactivav2.managed;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sat.sisat.cobranzacoactiva.business.CobranzaCoactivaBoRemote;
import com.sat.sisat.cobranzacoactiva.dto.CarteraExigibilidad;
import com.sat.sisat.cobranzacoactiva.dto.FindParameterDto;
import com.sat.sisat.cobranzacoactiva.dto.InformeTransferido;
import com.sat.sisat.common.dto.GenericDTO;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.FacesUtil;
import com.sat.sisat.common.util.Util;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.menus.business.MenuBoRemote;
import com.sat.sisat.menus.dto.SimpleMenuDTO;

@ManagedBean
@ViewScoped
public class ConsultaCarteraExigibilidadManaged extends BaseManaged {
	@EJB
	CobranzaCoactivaBoRemote cobranzaCoactivaBo;
	
	@EJB
	MenuBoRemote menuBo;
	
	private String nroCartera;
	private Integer coactivoId;
	private Integer situacionCarteraId;

	private List<GenericDTO> listaEjecutor = new ArrayList<GenericDTO>();
	private List<GenericDTO> listaSituacion = new ArrayList<GenericDTO>();
	
	private List<CarteraExigibilidad> records = new ArrayList<CarteraExigibilidad>();
	
	private CarteraExigibilidad selCarteraExigibilidad = new CarteraExigibilidad();

	private Integer coactivoAsignarId;
	
	// INICIO PERMISOS PARA EL MODULO -=CRAMIREZ=-
	 	private List<SimpleMenuDTO> listPermisosSubmenu = new ArrayList<SimpleMenuDTO>();
	 	private boolean permisoBuscar;	 	
	 	private boolean permisoAgregarRegistrar;
	 	private boolean permisoModificarActualizar;
	 	private boolean permisoReasignar;
	 	private boolean permisoVerEjecutor;

	 // FIN PERMISOS PARA EL MODULO
	
	@PostConstruct
	public void init() throws Exception {
		permisosMenu();
		try {
			listaSituacion=cobranzaCoactivaBo.listarSituacionCartera();
			listaEjecutor=cobranzaCoactivaBo.listarEjecutorCoactivo();
			if(getParameterSession()){
				buscar();
			}
			
			if(getSessionManaged().getCoPerfil().getCargoId()==1){
				coactivoId=getSessionManaged().getUsuarioLogIn().getUsuarioId();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void permisosMenu() {	
		try {
			int submenuId=0;
			if(getSessionManaged().getMateriaId() == 1) {
				submenuId = Constante.CARTERA_VALORES_TRIB;
			}else if (getSessionManaged().getMateriaId() == 2) {
				submenuId = Constante.CARTERA_VALORES_NO_TRIB;
			}
		 	
			int permisoBuscarId = Constante.BUSCAR;
			int permisoAgregarRegistrarId = Constante.AGREGAR_REGISTRAR;
			int permisoModificarActualizarId = Constante.MODIFICAR_ACTUALIZAR;			
			int permisoReasignarId = Constante.REASIGNAR;
			int permisoVerEjecutorId = Constante.VER_EJECUTOR;
			
			permisoBuscar = false;
			permisoAgregarRegistrar = false;
			permisoModificarActualizar = false;
			permisoReasignar = false;
			permisoVerEjecutor = false;
			
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
				if(lsm.getItemId() == permisoReasignarId) {
					permisoReasignar = true;
				}
				if(lsm.getItemId() == permisoVerEjecutorId) {
					permisoVerEjecutor = true;
				}
			}
		} catch (SisatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	public void buscar(){
		try{
			setParameterSession();
			records=cobranzaCoactivaBo.buscarCarteraExigibilidad(nroCartera, coactivoId, situacionCarteraId,getSessionManaged().getMateriaId());	
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String gestionCartera()throws Exception{
		try{
			FacesUtil.closeSession("acumulaCarteraExigibilidadManaged");
			getSessionMap().put("carteraExigibilidad", selCarteraExigibilidad);
		}catch(Exception e){
			e.printStackTrace();
		}
		return sendRedirectPrincipal();
	}
	
	public String nuevaCartera(){
		try{
			selCarteraExigibilidad=new CarteraExigibilidad();
			selCarteraExigibilidad.setCarteraId(Constante.RESULT_PENDING);
			selCarteraExigibilidad.setMateriaId(getSessionManaged().getMateriaId());
			getSessionMap().put("carteraExigibilidad", selCarteraExigibilidad);
		}catch(Exception e){
			e.printStackTrace();
		}
		return sendRedirectPrincipal();
	}
	
	public void registraAsignacion(){
		try{
			if(coactivoAsignarId!=null&&coactivoAsignarId>0){
				cobranzaCoactivaBo.reasignarCartera(selCarteraExigibilidad.getCarteraId(), selCarteraExigibilidad.getUsuarioCoactivoId(), coactivoAsignarId, getSessionManaged().getUsuarioLogIn().getUsuarioId(), getSessionManaged().getTerminalLogIn());
			}				
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void setParameterSession(){
		if(nroCartera!=null&&nroCartera.trim().length()>0){
			FindParameterDto findParameter=new FindParameterDto("nroCartera",nroCartera); 
			getSessionMap().put("findParameter", findParameter);
		}else if(coactivoId!=null&&coactivoId>0){
			FindParameterDto findParameter=new FindParameterDto("coactivoId",coactivoId); 
			getSessionMap().put("findParameter", findParameter);
		}else if(situacionCarteraId!=null&&situacionCarteraId>0){
			FindParameterDto findParameter=new FindParameterDto("situacionCarteraId",situacionCarteraId); 
			getSessionMap().put("findParameter", findParameter);
		}
	}
	
	public boolean getParameterSession(){
		FindParameterDto findParameter=(FindParameterDto)getSessionMap().get("findParameter");
		if(findParameter!=null){
			if(findParameter.getParameterName().equals("nroCartera")){
				nroCartera=String.valueOf(findParameter.getParameterValue());
				return true;	
			}else if(findParameter.getParameterName().equals("coactivoId")){
				coactivoId=Integer.valueOf(findParameter.getParameterValue());
				return true;
			}else if(findParameter.getParameterName().equals("situacionCarteraId")){
				situacionCarteraId=Integer.valueOf(findParameter.getParameterValue());
				return true;
			}
		}
		return false;
	}
	
	public String getNroCartera() {
		return nroCartera;
	}

	public void setNroCartera(String nroCartera) {
		this.nroCartera = nroCartera;
	}

	public List<GenericDTO> getListaEjecutor() {
		return listaEjecutor;
	}

	public void setListaEjecutor(List<GenericDTO> listaEjecutor) {
		this.listaEjecutor = listaEjecutor;
	}

	public List<GenericDTO> getListaSituacion() {
		return listaSituacion;
	}

	public void setListaSituacion(List<GenericDTO> listaSituacion) {
		this.listaSituacion = listaSituacion;
	}

	public Integer getCoactivoId() {
		return coactivoId;
	}

	public void setCoactivoId(Integer coactivoId) {
		this.coactivoId = coactivoId;
	}

	public List<CarteraExigibilidad> getRecords() {
		return records;
	}

	public void setRecords(List<CarteraExigibilidad> records) {
		this.records = records;
	}

	public CarteraExigibilidad getSelCarteraExigibilidad() {
		return selCarteraExigibilidad;
	}

	public void setSelCarteraExigibilidad(CarteraExigibilidad selCarteraExigibilidad) {
		this.selCarteraExigibilidad = selCarteraExigibilidad;
	}

	public Integer getSituacionCarteraId() {
		return situacionCarteraId;
	}

	public void setSituacionCarteraId(Integer situacionCarteraId) {
		this.situacionCarteraId = situacionCarteraId;
	}
	
	public Integer getCoactivoAsignarId() {
		return coactivoAsignarId;
	}

	public void setCoactivoAsignarId(Integer coactivoAsignarId) {
		this.coactivoAsignarId = coactivoAsignarId;
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


	public boolean isPermisoModificarActualizar() {
		return permisoModificarActualizar;
	}


	public void setPermisoModificarActualizar(boolean permisoModificarActualizar) {
		this.permisoModificarActualizar = permisoModificarActualizar;
	}


	public boolean isPermisoAgregarRegistrar() {
		return permisoAgregarRegistrar;
	}


	public void setPermisoAgregarRegistrar(boolean permisoAgregarRegistrar) {
		this.permisoAgregarRegistrar = permisoAgregarRegistrar;
	}


	public boolean isPermisoReasignar() {
		return permisoReasignar;
	}


	public void setPermisoReasignar(boolean permisoReasignar) {
		this.permisoReasignar = permisoReasignar;
	}


	public boolean isPermisoVerEjecutor() {
		return permisoVerEjecutor;
	}


	public void setPermisoVerEjecutor(boolean permisoVerEjecutor) {
		this.permisoVerEjecutor = permisoVerEjecutor;
	}
	
}
