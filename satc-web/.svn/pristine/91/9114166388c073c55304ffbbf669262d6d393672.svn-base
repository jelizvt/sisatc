package com.sat.sisat.caja.managed;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sat.sisat.caja.business.CajaBoRemote;
import com.sat.sisat.caja.dto.AgenciaOperacionDTO;
import com.sat.sisat.caja.dto.CajaAperturaDTO;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.exception.SisatException;

@ManagedBean
@ViewScoped
public class CajaAperturaManaged extends BaseManaged {

	private static final long serialVersionUID = 5728750393456468701L;

	@EJB
	private CajaBoRemote cajaBo;

	private Date fechaOperacion;
	private String nombreAgencia;
	private String usuario;
	private String estadoCaja;
	
	private boolean cajaAperturada;
	
	public CajaAperturaManaged() {
		getSessionManaged().setLinkRegresar("/sisat/caja/cajapertura.xhtml");
	}

	@PostConstruct
	public void init() {
		cargarEstadoActualCaja();
	}
	
	private void cargarEstadoActualCaja(){
		try {
			int usId = getSessionManaged().getUsuarioLogIn().getUsuarioId();
			String term = getSessionManaged().getTerminalLogIn();

			// Verificar si agencia esta aperturada
			AgenciaOperacionDTO ao = cajaBo.obtenerAgenciaOperacion(usId, term);
			System.out.println("pase");
			System.out.println(ao);
			System.out.println("---");
			//System.out.println(ao.getAgenciaId());
			
			if (ao == null) {
				addWarnMessage("Primero debe aperturar la agencia");
			}else {
				System.out.println(".-.");
				int agenId = ao.getAgenciaId();
				int agenOperId = ao.getAgenciaOperacionId();

				CajaAperturaDTO cap = cajaBo.obtenerEstadoActualCaja(agenId, agenOperId, usId, term);
				System.out.println("pase 2");
				if (cap == null) {
					fechaOperacion = Calendar.getInstance().getTime();
					nombreAgencia = ao.getNombreAgencia();
					usuario = ao.getUsuarioDes();
					cajaAperturada = false;
				} else {
					fechaOperacion = cap.getFechaApertura();
					nombreAgencia = cap.getAgenciaDes();
					usuario = cap.getUsuarioDes();
					estadoCaja = cap.getEstadoDes();
					if (cap.getEstado().equals(Constante.ESTADO_ACTIVO)) {
						cajaAperturada = true;
					} else {
						cajaAperturada = false;
					}
				}
				
			}

			
		} catch (SisatException e) {
			WebMessages.messageFatal(e);
		}
	}
	
	public String aperturarCaja(){
		try {
			int usId = getSessionManaged().getUsuarioLogIn().getUsuarioId();
			String term = getSessionManaged().getTerminalLogIn();
			try {
				AgenciaOperacionDTO ao = cajaBo.obtenerAgenciaOperacion(usId, term);
				if (ao == null) {
					addErrorMessage("La agencia no está aperturada");
				}

				CajaAperturaDTO ca = new CajaAperturaDTO();
				ca.setEstado(Constante.ESTADO_ACTIVO);
				ca.setUsuarioId(usId);
				ca.setTerminal(term);
				ca.setFlagOper(Constante.OPERACION_APERTURA);
				ca.setAgenciaId(ao.getAgenciaId());
				ca.setAgenciaOperacionId(ao.getAgenciaOperacionId());
				Integer result = cajaBo.aperturarCerrarCaja(ca);

				if (result == null || result.intValue() == 0) {
					addErrorMessage("No ha sido posible aperturar la caja");
					return null;
				}

				addInfoMessage("Caja aperturada con éxito");

				this.cargarEstadoActualCaja();
				getSessionManaged().fillPermisosModuloUsuario();

			} catch (Exception ex) {
				String msg = "No ha sido posible aperturar la caja";
				System.out.println(msg + ex);
			}
			
		} catch (SisatException e) {
			WebMessages.messageFatal(e);
		}
		return null;
	}
	
	public String cerrarCaja(){
		try{
			int usId = getSessionManaged().getUsuarioLogIn().getUsuarioId();
			String term = getSessionManaged().getTerminalLogIn();

			AgenciaOperacionDTO ao = cajaBo.obtenerAgenciaOperacion(usId, term);
			if (ao == null) {
				addErrorMessage("La agencia no está aperturada");
			}

			CajaAperturaDTO ca = new CajaAperturaDTO();
			ca.setEstado("2");
			ca.setUsuarioId(usId);
			ca.setTerminal(term);
			ca.setFlagOper(Constante.OPERACION_CIERRE);
			ca.setAgenciaId(ao.getAgenciaId());
			ca.setAgenciaOperacionId(ao.getAgenciaOperacionId());

			Integer result = cajaBo.aperturarCerrarCaja(ca);

			if (result == null) {
				addErrorMessage("No ha sido posible cerrar la caja");
				return null;
			}

			switch (result) {
			case -1:
				// Mostrar Mensaje "Primero debe cuadrar la caja"
				addErrorMessage("Primero debe cuadrar la caja");
				break;
			case -2:
				// Mostrar Mensaje "La caja esta cerrada"
				addErrorMessage("La caja esta cerrada");
				break;
			default:
				// Mostrar Mensaje "Caja cerrada satisfactoriamente"
				addInfoMessage("Caja cerrada satisfactoriamente");
				break;
			}

			cargarEstadoActualCaja();
			getSessionManaged().fillPermisosModuloUsuario();
		} catch (Exception ex) {
			String msg = "No ha sido posible cerrar la caja";
			System.out.println(msg + ex);
			addErrorMessage(msg);
		}
		return null;
	}

	public Date getFechaOperacion() {
		return fechaOperacion;
	}

	public void setFechaOperacion(Date fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
	}

	public String getNombreAgencia() {
		return nombreAgencia;
	}

	public void setNombreAgencia(String nombreAgencia) {
		this.nombreAgencia = nombreAgencia;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getEstadoCaja() {
		return estadoCaja;
	}

	public void setEstadoCaja(String estadoCaja) {
		this.estadoCaja = estadoCaja;
	}

	public boolean isCajaAperturada() {
		return cajaAperturada;
	}

	public void setCajaAperturada(boolean cajaAperturada) {
		this.cajaAperturada = cajaAperturada;
	}
}
