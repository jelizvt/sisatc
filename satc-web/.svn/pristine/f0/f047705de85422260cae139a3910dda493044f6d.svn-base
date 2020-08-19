package com.sat.sisat.persona.managed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.richfaces.component.html.HtmlComboBox;

import com.sat.sisat.cobranzacoactiva.business.CobranzaCoactivaBoRemote;
import com.sat.sisat.common.business.GeneralBoRemote;
import com.sat.sisat.common.managed.MensajeSisatDTO;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.FacesUtil;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.fiscalizacion.dto.FindInspeccionHistorial;
import com.sat.sisat.menus.business.MenuBoRemote;
import com.sat.sisat.menus.dto.SimpleMenuDTO;
import com.sat.sisat.persistence.entity.GnCondicionEspecial;
import com.sat.sisat.persistence.entity.MpPersona;
import com.sat.sisat.persistence.entity.MpTipoDocuIdentidad;
import com.sat.sisat.persistence.entity.MpTipoPersona;
import com.sat.sisat.persona.business.PersonaBoRemote;
import com.sat.sisat.predial.dto.FindMpDireccion;
import com.sat.sisat.predial.dto.FindMpPersona;
import com.sat.sisat.predial.dto.FindMpRelacionado;
import com.sat.sisat.vehicular.dto.BuscarPersonaDTO;

@ManagedBean
@ViewScoped
public class BusquedaPersonaManaged extends BaseManaged {

	private static final long serialVersionUID = 6319707928414372255L;

	@EJB
	MenuBoRemote menuBo;
	
	@EJB
	PersonaBoRemote personaBo;

	@EJB
	GeneralBoRemote generalBo;
	private MpPersona mpPersona;
	
	@EJB
	CobranzaCoactivaBoRemote cobranzaCoactivaBo;

	private HtmlComboBox cmbtipodocumento;
	private List<SelectItem> lsttipodocumento = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRpTipodocumento = new HashMap<String, Integer>();
	private String cmbValuetipodocumento;

	private HtmlComboBox cmbtipopersona;
	private List<SelectItem> lsttipopersona = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRpTipopersona = new HashMap<String, Integer>();
	private String cmbValuetipopersona;
	private String apellidosNombres;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private Integer personaId = null;
	private String primerNombre;
	private String segundoNombre;
	private String nroDocumentoIdentidad;
	private Integer tipoDocumento = 0;
	private Integer nroDj = null;
	private String razonSocial;
	private Integer tipodocumentoIdent;
	List<MpTipoPersona> lMpTipoPersona = null;
	List<MpTipoDocuIdentidad> lMpTipoDocuIdentidad = null;
	private MpTipoDocuIdentidad mptipoDocuIdentidad;

	private Boolean isDNI = Boolean.FALSE;
	private Boolean isRUC = Boolean.FALSE;

	private ArrayList<FindMpPersona> personas;
	private ArrayList<FindMpRelacionado> relacionadoPersona;
	private ArrayList<FindMpDireccion> direcciones;
	private FindMpPersona currentItem = new FindMpPersona();
	private FindMpPersona finMpPersonaItem = new FindMpPersona();
	private MpPersona persona;

	private String codigoAnterior;
	
	private String placa;
	
	private BuscarPersonaDTO buscarPersonaDTO;
//	private BuscarPersonaDTO buscarPersonaInspeccionDTO;
	private FindInspeccionHistorial buscarPersonaInspeccionDTO;

	// INICIO PERMISOS PARA EL MODULO -=CRAMIREZ=-
	private List<SimpleMenuDTO> listPermisosSubmenu = new ArrayList<SimpleMenuDTO>();
	private boolean permisoAgregarRegistrar;
	private boolean permisoBuscar;
	private boolean permisoModificarActualizar;
	private boolean permisoEstadoCuenta;
	// FIN PERMISOS PARA EL MODULO
	public BusquedaPersonaManaged() throws Exception {
	}

	@PostConstruct
	public void init() { 
		
		permisosMenu();
		
		try {
			buscarPersonaDTO = (BuscarPersonaDTO)getSessionMap().get("buscarPersonaDTO"); 
						
			if(buscarPersonaDTO != null){
				personaId = buscarPersonaDTO.getPersonaId(); 
				apellidoPaterno = buscarPersonaDTO.getApellidoPaterno();
				apellidoMaterno = buscarPersonaDTO.getApellidoMaterno(); 
				primerNombre = buscarPersonaDTO.getPrimerNombre(); 
				apellidosNombres = buscarPersonaDTO.getApellidosNombres();
				nroDocumentoIdentidad = buscarPersonaDTO.getNroDocumentoIdentidad(); 
				nroDj = buscarPersonaDTO.getNroDj(); 
				tipodocumentoIdent = buscarPersonaDTO.getTipodocumentoIdent();
				razonSocial = buscarPersonaDTO.getRazonSocial(); 
				codigoAnterior = buscarPersonaDTO.getCodigoAnterior(); 
				placa = buscarPersonaDTO.getPlaca();
			}
			
			relacionadoPersona = new ArrayList<FindMpRelacionado>();
			direcciones = new ArrayList<FindMpDireccion>();
			personas = new ArrayList<FindMpPersona>();

			// MpTipoPersona
			List<MpTipoPersona> lstMpTipoPersona = personaBo
					.getAllMpTipoPersona();
			Iterator<MpTipoPersona> it = lstMpTipoPersona.iterator();
			lsttipopersona = new ArrayList<SelectItem>();

			while (it.hasNext()) {
				MpTipoPersona obj = it.next();
				lsttipopersona.add(new SelectItem(obj.getDescripcionCorta(),
						String.valueOf(obj.getTipoPersonaId())));
				mapRpTipopersona.put(obj.getDescripcionCorta().trim(),
						obj.getTipoPersonaId());
			}
			// MpTipoDocumento
			List<MpTipoDocuIdentidad> lstMpTipoDocuIdentidad = personaBo
					.getAllMpTipoDocumento();
			Iterator<MpTipoDocuIdentidad> it1 = lstMpTipoDocuIdentidad
					.iterator();
			lsttipodocumento = new ArrayList<SelectItem>();

			while (it1.hasNext()) {
				MpTipoDocuIdentidad obj = it1.next();
				lsttipodocumento.add(new SelectItem(obj.getDescrpcionCorta(),
						String.valueOf(obj.getTipoDocIdentidadId())));
				mapRpTipodocumento.put(obj.getDescrpcionCorta().trim(),
						obj.getTipoDocIdentidadId());
			}
				buscar();

				buscarPersonaInspeccion(); //14-07-14
			
			//limpiar();

		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		
		
		
	}
	
	public void noFisca() {
		//return "buscar";
		getSessionManaged().setModuloFisca(false);
	}
	
	public void permisosMenu() {	
		try {
			int submenuId = Constante.TRIBUTOS_TRIBUTOS;
			
			int permisoAgregarRegistrarId = Constante.AGREGAR_REGISTRAR;
			int permisoBuscarId = Constante.BUSCAR;
			int permisoModificarActualizarId = Constante.MODIFICAR_ACTUALIZAR;
			int permisoEstadoCuentaId = Constante.ESTADO_CUENTA;
	
			permisoAgregarRegistrar = false;
			
			listPermisosSubmenu  = menuBo.getAccesosSubmenuUsuario( getSessionManaged().getUsuarioLogIn().getUsuarioId() , submenuId);
			
			Iterator<SimpleMenuDTO> menuIterar = listPermisosSubmenu.iterator();
			while (menuIterar.hasNext()) {
				SimpleMenuDTO lsm = menuIterar.next();
				if(lsm.getItemId() == permisoAgregarRegistrarId) {
					permisoAgregarRegistrar = true;
				}
				if(lsm.getItemId() == permisoBuscarId) {
					permisoBuscar = true;
				}
				if(lsm.getItemId() == permisoModificarActualizarId) {
					permisoModificarActualizar = true;
				}
				if(lsm.getItemId() == permisoEstadoCuentaId) {
					permisoEstadoCuenta = true;
				}
									
			}
			
		} catch (SisatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String buscarPersona() {
		return "buscar";
	}

	public RegistroPersonaManaged getRegistroPersonaManaged() {
		return (RegistroPersonaManaged) getManaged("registroPersonaManaged");
	}

	public void verPredios(ActionEvent ev) {
		try {
			UIComponent comp = ev.getComponent().getParent().getParent();
			if (comp != null) {
				UIData uiData = (UIData) comp;
				finMpPersonaItem = (FindMpPersona) uiData.getRowData();
				getSessionManaged().setContribuyente(finMpPersonaItem);
				getSessionManaged().setTributoPage(true);
				getSessionManaged().setDatosContribuyente(false);
			}
		} catch (Exception ex) {
			String msg = "verPredios ";

		}
	}

	public void imprimir() {
		try {
			getSessionMap().put("findMpPersona1", finMpPersonaItem);
			GnCondicionEspecial gnCondicionEspecialTemp = new GnCondicionEspecial();
			gnCondicionEspecialTemp = personaBo
					.findCondicionEspecial(finMpPersonaItem.getMpPersona());
			if (gnCondicionEspecialTemp != null) {
				getSessionMap().put("condicionEspecialnuevoPersona",
						gnCondicionEspecialTemp);
			}
			getSessionMap().remove("finMpPersonaItem");
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}

	}

	public boolean validarTipoBusqueda() {
		if ((personaId == null || personaId == 0)
				&& (apellidoPaterno == null || apellidoPaterno.compareTo("") == 0)
				&& (apellidoMaterno == null || apellidoMaterno.compareTo("") == 0)
				&& (primerNombre == null || primerNombre.compareTo("") == 0)				
				&& (apellidosNombres == null || apellidosNombres.compareTo("") == 0)
				&& (nroDocumentoIdentidad == null || nroDocumentoIdentidad
						.compareTo("") == 0)
				&& (nroDj == null || nroDj == 0)
				&& (tipodocumentoIdent == null || tipodocumentoIdent == 0)
				&& (razonSocial == null || razonSocial.compareTo("") == 0)
				&& (codigoAnterior == null || codigoAnterior.compareTo("") == 0)
				&& (placa == null || placa.compareTo("") == 0))
			return false;

		if (tipodocumentoIdent != null && tipodocumentoIdent != 0) {

			if (nroDocumentoIdentidad == null
					|| nroDocumentoIdentidad.compareTo("") == 0) {
				addErrorMessage(getMsg("mp.errornrodocumento"));
				return false;
			}
			if (isDNI) {
				if (nroDocumentoIdentidad.trim().length() != Constante.CANTIDAD_DIGITOS_DNI) {
					addErrorMessage(getMsg("Ingrese 8 Digitos"));
					return false;
				}
			}
			if (isRUC) {
				if (nroDocumentoIdentidad.trim().length() != Constante.CANTIDAD_DIGITOS_RUC) {
					addErrorMessage(getMsg("Ingrese 11 Digitos"));

					return false;
				}
			}
		}

		if (nroDocumentoIdentidad != null) {
			if (nroDocumentoIdentidad.compareTo("") != 0) {
				if (tipodocumentoIdent == null || tipodocumentoIdent == 0) {
					addErrorMessage(getMsg("mp.errortipodocumento"));
					return false;
				}
			}
		}
		return true;

	}

	public void buscar() {
		try {
			personas = new ArrayList<FindMpPersona>();
			Object obj = getSessionMap().get("nuevoPersona");
			if (obj != null) {
				MpPersona mpPersona = (MpPersona) obj;
				if (mpPersona.getNroDj() != null && mpPersona.getNroDj() > 0) {
					personaId = mpPersona.getPersonaId();
				}
				getSessionMap().remove("nuevoPersona");
			}

			if (validarTipoBusqueda()) {

				buscarPersonaDTO = new BuscarPersonaDTO();

				if (personaId != null && personaId > 0) {
					buscarPersonaDTO.setPersonaId(personaId);
				}
				if (apellidoPaterno != null && !apellidoPaterno.isEmpty()) {
					buscarPersonaDTO.setApellidoPaterno(apellidoPaterno);
				}
				if (apellidoMaterno != null && !apellidoMaterno.isEmpty()) {
					buscarPersonaDTO.setApellidoMaterno(apellidoMaterno);
				}
				if (primerNombre != null && !primerNombre.isEmpty()) {
					buscarPersonaDTO.setPrimerNombre(primerNombre);
				}
				if (apellidosNombres != null && !apellidosNombres.isEmpty()) {
					buscarPersonaDTO.setApellidosNombres(apellidosNombres);
				}
				if (nroDocumentoIdentidad != null
						&& !nroDocumentoIdentidad.isEmpty()) {
					buscarPersonaDTO
							.setNroDocumentoIdentidad(nroDocumentoIdentidad);
				}
				if (nroDj != null && nroDj.intValue() > 0) {
					buscarPersonaDTO.setNroDj(nroDj);
				}
				if (tipodocumentoIdent != null
						&& tipodocumentoIdent.intValue() > 0) {
					buscarPersonaDTO.setTipodocumentoIdent(tipodocumentoIdent);
				}
				if (razonSocial != null && !razonSocial.isEmpty()) {
					buscarPersonaDTO.setRazonSocial(razonSocial);
				}
				if (codigoAnterior != null && !codigoAnterior.isEmpty()) {
					buscarPersonaDTO.setCodigoAnterior(codigoAnterior);
				}
				if (placa != null && !placa.isEmpty()) {
					buscarPersonaDTO.setPlaca(placa);
				}
				//Colocando en Sesion los atributos de busqueda usados
				getSessionMap().put("buscarPersonaDTO", buscarPersonaDTO);

				personas = personaBo.getmpPersona(personaId, apellidoPaterno,
						apellidoMaterno, primerNombre, apellidosNombres,
						nroDocumentoIdentidad, nroDj, tipodocumentoIdent,
						razonSocial, codigoAnterior, placa);

			}
			
		}catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void limpiar() {
		apellidoPaterno = "";
		apellidoMaterno = "";
		personaId = null;
		nroDj = null;
		primerNombre = "";
		segundoNombre = "";
		nroDocumentoIdentidad = "";
		cmbValuetipodocumento = "";
		razonSocial = "";
		tipodocumentoIdent = null;
		apellidosNombres = "";
		codigoAnterior = "";
		placa = "";
	}

	/**
	 * funciones invocadas desde los botones Nuevo, Editar y Ver
	 */
	public String nuevo() throws Exception {
		try {
			getSessionMap().remove("finMpPersonaItem");
			FacesUtil.closeSession("registroPersonaManaged");
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		return sendRedirectPrincipal();
	}

	public String edit() {
		try {
			getSessionMap().put("finMpPersonaItem", finMpPersonaItem);
			FacesUtil.closeSession("registroPersonaManaged");
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		return sendRedirectPrincipal();

	}

	public String ver() {
		try {

			getSessionManaged().setContribuyente(finMpPersonaItem);
			getSessionManaged().setTributoPage(true);
			getSessionManaged().setDatosContribuyente(false);
			Boolean resp = cobranzaCoactivaBo
					.contribEnCobranzaCoactiva(finMpPersonaItem.getPersonaId());
			if (resp.booleanValue()) {
				MensajeSisatDTO mensajeSisatDTO = new MensajeSisatDTO();
				mensajeSisatDTO.setMensaje("El contribuyente "
						+ finMpPersonaItem.getPersonaId()
						+ " esta en un proceso de Cobranza Coactiva");
				mensajeSisatDTO.setTipoColor(1);
				getSessionManaged().anhadirMensaje(mensajeSisatDTO);
			}
			Boolean resp2 = cobranzaCoactivaBo
					.resultadoDjsSinDeterminar(finMpPersonaItem.getPersonaId());
			if (resp2.booleanValue()) {
				MensajeSisatDTO mensajeSisatDTO1 = new MensajeSisatDTO();
				mensajeSisatDTO1.setMensaje("EL CONTRIBUYENTE "
						+ finMpPersonaItem.getPersonaId()
						+ " TIENE DJS SIN DETERMINAR VERIFIQUE!!!");
				mensajeSisatDTO1.setTipoColor(1);
				getSessionManaged().anhadirMensaje(mensajeSisatDTO1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		return sendRedirectPrincipal();
	}
	
	//para ver aviso en fiscalizacion
	public String verf() {
		try {

			getSessionManaged().setContribuyente(finMpPersonaItem);
			getSessionManaged().setDatosContribuyente(true);
			getSessionManaged().setTributoPage(false);
			Boolean resp = cobranzaCoactivaBo
					.contribEnCobranzaCoactiva(finMpPersonaItem.getPersonaId());
			if (resp.booleanValue()) {
				MensajeSisatDTO mensajeSisatDTO = new MensajeSisatDTO();
				mensajeSisatDTO.setMensaje("El contribuyente "
						+ finMpPersonaItem.getPersonaId()
						+ " esta en un proceso de Cobranza Coactiva");
				mensajeSisatDTO.setTipoColor(1);
				getSessionManaged().anhadirMensaje(mensajeSisatDTO);
			}
			Boolean resp2 = cobranzaCoactivaBo
					.resultadoDjsSinDeterminar(finMpPersonaItem.getPersonaId());
			if (resp2.booleanValue()) {
				MensajeSisatDTO mensajeSisatDTO1 = new MensajeSisatDTO();
				mensajeSisatDTO1.setMensaje("EL CONTRIBUYENTE "
						+ finMpPersonaItem.getPersonaId()
						+ " TIENE DJS SIN DETERMINAR VERIFIQUE!!!");
				mensajeSisatDTO1.setTipoColor(1);
				getSessionManaged().anhadirMensaje(mensajeSisatDTO1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		return sendRedirectPrincipal();
	}

	public String verDatos() {
		try {

			getSessionMap().put("finMpPersonaItemVerDatos", finMpPersonaItem);
			FacesUtil.closeSession("registroPersonaManaged");
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		return sendRedirectPrincipal();

	}

	public void getPersonas(Integer personaId) {
		try {
			personas = personaBo.getmpPersona(0, "", "", "", "", "", 0, 0, "",
					"","");
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void loadTipoPersonaById(ValueChangeEvent event) {
		try {
			String value = (String) event.getNewValue();
			if (value != null) {
				Integer tipoPersonaId = (Integer) getMapRpTipopersona().get(
						value);
				if (tipoPersonaId != null) {
					lMpTipoPersona = personaBo.findMpTipoPersona(tipoPersonaId);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void loadTipoTipoDocumentoById(ValueChangeEvent event) {
		try {
			setIsDNI(Boolean.FALSE);
			setIsRUC(Boolean.FALSE);
			String value = (String) event.getNewValue();
			if (value != null) {
				tipodocumentoIdent = (Integer) mapRpTipodocumento.get(value);

				if (tipodocumentoIdent != null) {
					lMpTipoDocuIdentidad = personaBo
							.findMpTipoDocuIdentidad(tipodocumentoIdent);
				}
				if (value.compareTo(Constante.TIPO_DOCUMENTO_DNI) == 0) {
					setIsDNI(Boolean.TRUE);
					setIsRUC(Boolean.FALSE);
				}
				if (value.compareTo(Constante.TIPO_DOCUMENTO_RUC) == 0) {
					setIsDNI(Boolean.FALSE);
					setIsRUC(Boolean.TRUE);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public void buscarPersonaInspeccion() {
		try {
			//Obtiene el sessionMap, recibiendo sólo la personaId:
//			Integer buscarPersonaIdInspeccionDTO = (Integer) getSessionMap().get("currentItem");
//			if (buscarPersonaIdInspeccionDTO != null) {
//				personaId=buscarPersonaIdInspeccionDTO;
//     		}
			//Obtiene el sessionMap, recibiendo todo el objeto:
			buscarPersonaInspeccionDTO = (FindInspeccionHistorial) getSessionMap().get("currentItemRequerimiento");
			if (buscarPersonaInspeccionDTO!=null){

					if (buscarPersonaInspeccionDTO.getPersonaId() != null) {
							personaId = buscarPersonaInspeccionDTO.getPersonaId();
					}
			}
							
		}catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	public ArrayList<FindMpPersona> getPersonas() {
		return personas;
	}

	public void setPersonas(ArrayList<FindMpPersona> personas) {
		this.personas = personas;
	}

	public HtmlComboBox getCmbtipopersona() {
		return cmbtipopersona;
	}

	public void setCmbtipopersona(HtmlComboBox cmbtipopersona) {
		this.cmbtipopersona = cmbtipopersona;
	}

	public List<SelectItem> getLsttipopersona() {
		return lsttipopersona;
	}

	public void setLsttipopersona(List<SelectItem> lsttipopersona) {
		this.lsttipopersona = lsttipopersona;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno.trim();
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno.trim();
	}

	public Integer getPersonaId() {
		return personaId;
	}

	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}

	public String getPrimerNombre() {
		return primerNombre;
	}

	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre.trim();
	}

	public String getSegundoNombre() {
		return segundoNombre;
	}

	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre.trim();
	}

	public HashMap<String, Integer> getMapRpTipopersona() {
		return mapRpTipopersona;
	}

	public void setMapRpTipopersona(HashMap<String, Integer> mapRpTipopersona) {
		this.mapRpTipopersona = mapRpTipopersona;
	}

	public List<SelectItem> getLsttipodocumento() {
		return lsttipodocumento;
	}

	public void setLsttipodocumento(List<SelectItem> lsttipodocumento) {
		this.lsttipodocumento = lsttipodocumento;
	}

	public HashMap<String, Integer> getMapRpTipodocumento() {
		return mapRpTipodocumento;
	}

	public void setMapRpTipodocumento(
			HashMap<String, Integer> mapRpTipodocumento) {
		this.mapRpTipodocumento = mapRpTipodocumento;
	}

	public HtmlComboBox getCmbtipodocumento() {
		return cmbtipodocumento;
	}

	public void setCmbtipodocumento(HtmlComboBox cmbtipodocumento) {
		this.cmbtipodocumento = cmbtipodocumento;
	}

	public String getNroDocumentoIdentidad() {
		return nroDocumentoIdentidad;
	}

	public void setNroDocumentoIdentidad(String nroDocumentoIdentidad) {
		this.nroDocumentoIdentidad = nroDocumentoIdentidad;
	}

	public Integer getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(Integer tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public MpTipoDocuIdentidad getMptipoDocuIdentidad() {
		return mptipoDocuIdentidad;
	}

	public void setMptipoDocuIdentidad(MpTipoDocuIdentidad mptipoDocuIdentidad) {
		this.mptipoDocuIdentidad = mptipoDocuIdentidad;
	}

	public String getCmbValuetipodocumento() {
		return cmbValuetipodocumento;
	}

	public void setCmbValuetipodocumento(String cmbValuetipodocumento) {
		this.cmbValuetipodocumento = cmbValuetipodocumento;
	}

	public String getCmbValuetipopersona() {
		return cmbValuetipopersona;
	}

	public void setCmbValuetipopersona(String cmbValuetipopersona) {
		this.cmbValuetipopersona = cmbValuetipopersona;
	}

	public FindMpPersona getCurrentItem() {
		return currentItem;
	}

	public void setCurrentItem(FindMpPersona currentItem) {
		this.currentItem = currentItem;
	}

	public FindMpPersona getFinMpPersonaItem() {
		return finMpPersonaItem;
	}

	public void setFinMpPersonaItem(FindMpPersona finMpPersonaItem) {
		this.finMpPersonaItem = finMpPersonaItem;
	}

	public ArrayList<FindMpRelacionado> getRelacionadoPersona() {
		return relacionadoPersona;
	}

	public void setRelacionadoPersona(
			ArrayList<FindMpRelacionado> relacionadoPersona) {
		this.relacionadoPersona = relacionadoPersona;
	}

	public ArrayList<FindMpDireccion> getDirecciones() {
		return direcciones;
	}

	public void setDirecciones(ArrayList<FindMpDireccion> direcciones) {
		this.direcciones = direcciones;
	}

	public Integer getNroDj() {
		return nroDj;
	}

	public void setNroDj(Integer nroDj) {
		this.nroDj = nroDj;
	}

	public Boolean getIsDNI() {
		return isDNI;
	}

	public void setIsDNI(Boolean isDNI) {
		this.isDNI = isDNI;
	}

	public Boolean getIsRUC() {
		return isRUC;
	}

	public void setIsRUC(Boolean isRUC) {
		this.isRUC = isRUC;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial.trim();
	}

	public Integer getTipodocumentoIdent() {
		return tipodocumentoIdent;
	}

	public void setTipodocumentoIdent(Integer tipodocumentoIdent) {
		this.tipodocumentoIdent = tipodocumentoIdent;
	}

	public String getApellidosNombres() {
		return apellidosNombres;
	}

	public void setApellidosNombres(String apellidosNombres) {
		this.apellidosNombres = apellidosNombres;
	}

	public String getCodigoAnterior() {
		return codigoAnterior;
	}

	public void setCodigoAnterior(String codigoAnterior) {
		this.codigoAnterior = codigoAnterior;
	}

	public MpPersona getPersona() {
		return persona;
	}

	public void setPersona(MpPersona persona) {
		this.persona = persona;
	}

	public MpPersona getMpPersona() {
		return mpPersona;
	}

	public void setMpPersona(MpPersona mpPersona) {
		this.mpPersona = mpPersona;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public BuscarPersonaDTO getBuscarPersonaDTO() {
		return buscarPersonaDTO;
	}

	public void setBuscarPersonaDTO(BuscarPersonaDTO buscarPersonaDTO) {
		this.buscarPersonaDTO = buscarPersonaDTO;
	}

	public boolean isPermisoAgregarRegistrar() {
		return permisoAgregarRegistrar;
	}

	public void setPermisoAgregarRegistrar(boolean permisoAgregarRegistrar) {
		this.permisoAgregarRegistrar = permisoAgregarRegistrar;
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

	public boolean isPermisoEstadoCuenta() {
		return permisoEstadoCuenta;
	}

	public void setPermisoEstadoCuenta(boolean permisoEstadoCuenta) {
		this.permisoEstadoCuenta = permisoEstadoCuenta;
	}
	
//	public BuscarPersonaDTO getBuscarPersonaInspeccionDTO() {
//		return buscarPersonaInspeccionDTO;
//	}
//
//	public void setBuscarPersonaInspeccionDTO(
//			BuscarPersonaDTO buscarPersonaInspeccionDTO) {
//		this.buscarPersonaInspeccionDTO = buscarPersonaInspeccionDTO;
//	}

}
