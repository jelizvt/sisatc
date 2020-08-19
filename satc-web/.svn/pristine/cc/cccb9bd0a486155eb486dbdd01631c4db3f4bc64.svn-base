package com.sat.sisat.caja.managed;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import com.sat.sisat.caja.business.CajaBoRemote;
import com.sat.sisat.caja.dto.AgenciaOperacionDTO;
import com.sat.sisat.common.dto.GenericDTO;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.exception.SisatException;

@ManagedBean
@ViewScoped
public class InicioOperacionManaged extends BaseManaged implements Serializable{

	private static final long serialVersionUID = -1039317021007314599L;
	
	@EJB
	CajaBoRemote cajaBo;
	
	private List<SelectItem> listAgencias = new ArrayList<SelectItem>();
	private Map<String, Integer> mapCjAgencia = new HashMap<String, Integer>();
	private Map<Integer,String> mapICjAgencia = new HashMap<Integer, String>();
	private String selectedAgencia;
	
	private AgenciaOperacionDTO agenciaEstado;
	private boolean agenciaAperturada;
	
	public InicioOperacionManaged(){
		getSessionManaged().setLinkRegresar("/sisat/caja/iniciaroperacionesdia.xhtml");
	}
	
	@PostConstruct
	private void inicialize(){
		this.cargarAgenUsuarioSupervisor();
	}
	
	private void cargarAgenUsuarioSupervisor(){
		try {

			Integer usuarioId = getSessionManaged().getUsuarioLogIn().getUsuarioId();
			if (usuarioId == null) {
				return;
			}
			List<GenericDTO> lista = new ArrayList<GenericDTO>();
			lista = cajaBo.obtenerAgenciasUsuario(usuarioId, Constante.TIPO_ROL_SUPERVISOR);
			if (lista != null && !lista.isEmpty()) {
				for (GenericDTO g : lista) {
					System.out.println(g.getDescripcion());
					listAgencias.add(new SelectItem(g.getDescripcion()));
					mapCjAgencia.put(g.getDescripcion(), g.getId());
					mapICjAgencia.put(g.getId(), g.getDescripcion());
					if (selectedAgencia == null) {
						selectedAgencia = g.getDescripcion();
					}
				}
				cargarDatosAgenciaSelec();
			} else {
				addErrorMessage("No se ha podido recuperar las agencias del usuario");
			}
		} catch (SisatException e) {
			WebMessages.messageFatal(e);
		}
	}
	
	public void changeAgencia(ActionEvent ev){
		cargarDatosAgenciaSelec();
	}
	
	private void cargarDatosAgenciaSelec(){
		try {
			int agenciaId = mapCjAgencia.get(selectedAgencia);
			int usuarioId = getSessionManaged().getUsuarioLogIn().getUsuarioId();
			String terminal = getSessionManaged().getTerminalLogIn();
			AgenciaOperacionDTO ao = cajaBo.obtenerEstadoAgenSupervisor(agenciaId, usuarioId, terminal);
			if (ao == null) {
				this.agenciaEstado = new AgenciaOperacionDTO();
				this.agenciaAperturada = false;
				/*
				 * String msg = "No se ha podido recuperar los datos de la agencia y su estado";
				 * addErrorMessage(msg); System.out.println(msg);
				 */
				return;
			}
			if (ao.getEstado().equals(Constante.ESTADO_ACTIVO)) {
				this.agenciaAperturada = true;
			} else {
				this.agenciaAperturada = false;
			}
			this.agenciaEstado = ao;
		} catch (SisatException e) {
			WebMessages.messageFatal(e);
		}
	}
	
	public String aperturarAgencia() {
		try {
			if (selectedAgencia == null) {
				return null;
			}

			int agenciaId = mapCjAgencia.get(selectedAgencia);
			int usuarioId = getSessionManaged().getUsuarioLogIn().getUsuarioId();
			String terminal = getSessionManaged().getTerminalLogIn();

			AgenciaOperacionDTO ao = new AgenciaOperacionDTO();
			ao.setAgenciaId(agenciaId);
			ao.setUsuarioId(usuarioId);
			ao.setTerminal(terminal);
			ao.setFlagOperacion(Constante.OPERACION_APERTURA);

			Integer result = cajaBo.aperturarCerrarAgencia(ao);
			if (result == null) {
				addErrorMessage("No se ha podido realizar la operación");
				return null;
			}
			switch (result) {
			case -3:
				addInfoMessage("Agencia no permitida");
				break;
			case -4:
				addInfoMessage("Sus fechas de acceso para realizar esta operacion han expirado");
				break;	
			case -5:
				addInfoMessage("Agencia no permitida");
				break;
			default:
				this.cargarDatosAgenciaSelec();
				getSessionManaged().fillPermisosModuloUsuario();
				addInfoMessage("Agencia aperturada satisfactoriamente");
				break;
			}
		} catch (SisatException e) {
			WebMessages.messageError(e.getMessage());
		}
		return null;
	}
	
	public String cerrarAgencia() {
		try {
			int agenciaId = mapCjAgencia.get(selectedAgencia);
			int usuarioId = getSessionManaged().getUsuarioLogIn().getUsuarioId();
			String terminal = getSessionManaged().getTerminalLogIn();

			AgenciaOperacionDTO ao = new AgenciaOperacionDTO();
			ao.setAgenciaId(agenciaId);
			ao.setUsuarioId(usuarioId);
			ao.setTerminal(terminal);
			ao.setFlagOperacion(Constante.OPERACION_CIERRE);
			int result = cajaBo.aperturarCerrarAgencia(ao);

			switch (result) {
			case -1:
				addInfoMessage("Primero debe cerrar la(s) caja(s) abiertas");
				break;
			case -2:
				addInfoMessage("La agencia ya esta cerrada");
				break;
			case 0:
				break;
			case -4:
				addInfoMessage("Sus fechas de acceso para realizar esta operacion han expirado");
				break;	
			case -5:
				addInfoMessage("Agencia no permitida");
				break;
			default:
				this.cargarDatosAgenciaSelec();
				getSessionManaged().fillPermisosModuloUsuario();
				addInfoMessage("Agencia cerrada satisfactoriamente");
				break;
			}
		} catch (SisatException e) {
			WebMessages.messageFatal(e);
		}
		return null;
	}
	
	public List<SelectItem> getListAgencias() {
		return listAgencias;
	}

	public void setListAgencias(List<SelectItem> listAgencias) {
		this.listAgencias = listAgencias;
	}

	public String getSelectedAgencia() {
		return selectedAgencia;
	}

	public void setSelectedAgencia(String selectedAgencia) {
		this.selectedAgencia = selectedAgencia;
	}

	public AgenciaOperacionDTO getAgenciaEstado() {
		if(agenciaEstado == null){
			agenciaEstado = new AgenciaOperacionDTO();
		}
		return agenciaEstado;
	}

	public void setAgenciaEstado(AgenciaOperacionDTO agenciaEstado) {
		this.agenciaEstado = agenciaEstado;
	}

	public boolean isAgenciaAperturada() {
		return agenciaAperturada;
	}

	public void setAgenciaAperturada(boolean agenciaAperturada) {
		this.agenciaAperturada = agenciaAperturada;
	}
}
