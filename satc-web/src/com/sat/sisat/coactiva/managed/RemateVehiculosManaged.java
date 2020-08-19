package com.sat.sisat.coactiva.managed;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import com.sat.sisat.cobranzacoactiva.business.CobranzaCoactivaBoRemote;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.FacesUtil;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.menus.business.MenuBoRemote;
import com.sat.sisat.menus.dto.SimpleMenuDTO;
import com.sat.sisat.papeletas.managed.BuscarPersonaPapeletasManaged;
import com.sat.sisat.persistence.entity.GnRemate;
import com.sat.sisat.vehicular.cns.ReusoFormCns;
import com.sat.sisat.vehicular.dto.BuscarPersonaDTO;

@ManagedBean
@ViewScoped
public class RemateVehiculosManaged extends BaseManaged {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB
	CobranzaCoactivaBoRemote cobranzaCoactivaBo;
	
	@EJB
	MenuBoRemote menuBo;
	
	private List<GnRemate> listRemate = new ArrayList<GnRemate>();
	private GnRemate gnRemate = new GnRemate();
	private Integer propietarioId;
	private String placa;
	private BigDecimal montoAdjudicado = null;
	private java.util.Date fechaRemate;
	private String sustento;
	private BuscarPersonaDTO datosPropietario;
	// INICIO PERMISOS PARA EL MODULO -=CRAMIREZ=-
	 	private List<SimpleMenuDTO> listPermisosSubmenu = new ArrayList<SimpleMenuDTO>();	 	
	 	private boolean permisoAgregarRegistrar;
	// FIN PERMISOS PARA EL MODULO
	@PostConstruct
	public void init() {
		permisosMenu();
		try {
			listRemate = cobranzaCoactivaBo.getAllRemates();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void permisosMenu() {	
		try {
			int submenuId = Constante.REMATE_DE_VEHICULOS;
		 	
			int permisoAgregarRegistrarId = Constante.AGREGAR_REGISTRAR;	
			permisoAgregarRegistrar = false;
			
			listPermisosSubmenu  = menuBo.getAccesosSubmenuUsuario( getSessionManaged().getUsuarioLogIn().getUsuarioId() , submenuId);
			
			Iterator<SimpleMenuDTO> menuIterar = listPermisosSubmenu.iterator();
			while (menuIterar.hasNext()) {
				SimpleMenuDTO lsm = menuIterar.next();
				if(lsm.getItemId() == permisoAgregarRegistrarId) {
					permisoAgregarRegistrar = true;
				}
			}
		} catch (SisatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	public void nuevoRemate() {
		setFechaRemate(null);
		setMontoAdjudicado(null);
		setPlaca(null);
		setPropietarioId(null);
		setSustento(null);
	}

	public boolean validarCampos() {
		if (datosPropietario.getPersonaId() == 0) {
			addErrorMessage(getMsg("Seleccione el Propietario. Click en Buscar."));
			return false;
		} else if (placa == null || placa.equals("")) {
			addErrorMessage(getMsg("Ingrese Placa."));
			return false;
		} else if (montoAdjudicado == null) {
			addErrorMessage(getMsg("Ingrese Monto Adjudicado."));
			return false;
		} else if (sustento == null) {
			addErrorMessage(getMsg("Ingrese Sustento."));
			return false;
		} else if (fechaRemate == null) {
			addErrorMessage(getMsg("Ingrese Fecha Remate."));
			return false;
		}
		return true;
	}

	public void guardarRemates() {
		try {
			if (validarCampos()) {
				gnRemate.setPropietarioId(datosPropietario.getPersonaId());
				gnRemate.setPlaca(placa.toUpperCase());
				gnRemate.setMontoAdjudicado(montoAdjudicado);
				gnRemate.setFechaRemate(DateUtil
						.dateToSqlTimestamp(getFechaRemate()));
				gnRemate.setSustento(sustento);
				gnRemate.setFechaRegistro(DateUtil.dateToSqlTimestamp(Calendar
						.getInstance().getTime()));
				cobranzaCoactivaBo.guardarRemate(gnRemate);
				listRemate = cobranzaCoactivaBo.getAllRemates();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setPersonaAsociadaConPapeleta() {
		String destinoRefresh = FacesUtil.getRequestParameter("destinoRefresh");
		BuscarPersonaPapeletasManaged buscarPersonaManaged = (BuscarPersonaPapeletasManaged) getManaged("buscarPersonaPapeletasManaged");
		buscarPersonaManaged
				.setPantallaUso(ReusoFormCns.BUSQU_PER_REMATE_VEHICULO);
		buscarPersonaManaged.setDestinoRefresh(destinoRefresh);
	}

	public void copiaPersona(BuscarPersonaDTO persona) {
		setDatosPropietario(persona);
	}

	public GnRemate getGnRemate() {
		return gnRemate;
	}

	public void setGnRemate(GnRemate gnRemate) {
		this.gnRemate = gnRemate;
	}

	public List<GnRemate> getListRemate() {
		return listRemate;
	}

	public void setListRemate(List<GnRemate> listRemate) {
		this.listRemate = listRemate;
	}

	public Integer getPropietarioId() {
		return propietarioId;
	}

	public void setPropietarioId(Integer propietarioId) {
		this.propietarioId = propietarioId;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getSustento() {
		return sustento;
	}

	public void setSustento(String sustento) {
		this.sustento = sustento;
	}

	public BigDecimal getMontoAdjudicado() {
		return montoAdjudicado;
	}

	public void setMontoAdjudicado(BigDecimal montoAdjudicado) {
		this.montoAdjudicado = montoAdjudicado;
	}

	public java.util.Date getFechaRemate() {
		return fechaRemate;
	}

	public void setFechaRemate(java.util.Date fechaRemate) {
		this.fechaRemate = fechaRemate;
	}

	public BuscarPersonaDTO getDatosPropietario() {
		return datosPropietario;
	}

	public void setDatosPropietario(BuscarPersonaDTO datosPropietario) {
		this.datosPropietario = datosPropietario;
	}

	public List<SimpleMenuDTO> getListPermisosSubmenu() {
		return listPermisosSubmenu;
	}

	public void setListPermisosSubmenu(List<SimpleMenuDTO> listPermisosSubmenu) {
		this.listPermisosSubmenu = listPermisosSubmenu;
	}

	public boolean isPermisoAgregarRegistrar() {
		return permisoAgregarRegistrar;
	}

	public void setPermisoAgregarRegistrar(boolean permisoAgregarRegistrar) {
		this.permisoAgregarRegistrar = permisoAgregarRegistrar;
	}
	
}
