package com.sat.sisat.tramitedocumentario.managed.popup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.richfaces.component.html.HtmlComboBox;

import com.sat.sisat.common.business.GeneralBoRemote;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.descargo.managed.DescargoDeudasManaged;
import com.sat.sisat.persistence.entity.MpPersona;
import com.sat.sisat.persistence.entity.MpTipoDocuIdentidad;
import com.sat.sisat.persistence.entity.MpTipoPersona;
import com.sat.sisat.persona.business.PersonaBoRemote;
import com.sat.sisat.predial.dto.FindMpDireccion;
import com.sat.sisat.predial.dto.FindMpPersona;
import com.sat.sisat.tramitedocumentario.dto.RelacionadosDTO;
import com.sat.sisat.tramitedocumentario.managed.RegistroTramiteManaged;
import com.sat.sisat.vehicular.dto.BuscarPersonaDTO;


@ManagedBean
@ViewScoped
public class BusquedaContribuyentePopupManaged extends BaseManaged {

	private static final long serialVersionUID = 6319707928414372255L;

	@EJB
	PersonaBoRemote personaBo;

	@EJB
	GeneralBoRemote generalBo;
	private MpPersona mpPersona;

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

	private List<BuscarPersonaDTO> personas;

	private ArrayList<FindMpDireccion> direcciones;
	private BuscarPersonaDTO currentItem = new BuscarPersonaDTO();
	private RelacionadosDTO currentItemC = new RelacionadosDTO();
	private BuscarPersonaDTO currentItemR = new BuscarPersonaDTO();


	private BuscarPersonaDTO finMpPersonaItem = new BuscarPersonaDTO();
	private MpPersona persona;

	private String codigoAnterior;

	private String placa;

	public BusquedaContribuyentePopupManaged() throws Exception {
	}

	@PostConstruct
	public void init() {
		try {

			direcciones = new ArrayList<FindMpDireccion>();
			personas = new ArrayList<BuscarPersonaDTO>();

			// MpTipoPersona
			List<MpTipoPersona> lstMpTipoPersona = personaBo.getAllMpTipoPersona();
			Iterator<MpTipoPersona> it = lstMpTipoPersona.iterator();
			lsttipopersona = new ArrayList<SelectItem>();

			while (it.hasNext()) {
				MpTipoPersona obj = it.next();
				lsttipopersona.add(new SelectItem(obj.getDescripcionCorta(), String.valueOf(obj.getTipoPersonaId())));
				mapRpTipopersona.put(obj.getDescripcionCorta().trim(), obj.getTipoPersonaId());
			}
			// MpTipoDocumento
			List<MpTipoDocuIdentidad> lstMpTipoDocuIdentidad = personaBo.getAllMpTipoDocumento();
			Iterator<MpTipoDocuIdentidad> it1 = lstMpTipoDocuIdentidad.iterator();
			lsttipodocumento = new ArrayList<SelectItem>();

			while (it1.hasNext()) {
				MpTipoDocuIdentidad obj = it1.next();
				lsttipodocumento.add(new SelectItem(obj.getDescrpcionCorta(), String.valueOf(obj
						.getTipoDocIdentidadId())));
				mapRpTipodocumento.put(obj.getDescrpcionCorta().trim(), obj.getTipoDocIdentidadId());
			}
			buscar();
			limpiar();

		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public boolean validarTipoBusqueda() {
		if ((personaId == null || personaId == 0) && (apellidoPaterno == null || apellidoPaterno.compareTo("") == 0)
				&& (apellidoMaterno == null || apellidoMaterno.compareTo("") == 0)
				&& (primerNombre == null || primerNombre.compareTo("") == 0)
				&& (apellidosNombres == null || apellidosNombres.compareTo("") == 0)
				&& (nroDocumentoIdentidad == null || nroDocumentoIdentidad.compareTo("") == 0)
				&& (nroDj == null || nroDj == 0) && (tipodocumentoIdent == null || tipodocumentoIdent == 0)
				&& (razonSocial == null || razonSocial.compareTo("") == 0)
				&& (codigoAnterior == null || codigoAnterior.compareTo("") == 0)
				&& (placa == null || placa.compareTo("") == 0))
			return false;

		if (tipodocumentoIdent != null && tipodocumentoIdent != 0) {

			if (nroDocumentoIdentidad == null || nroDocumentoIdentidad.compareTo("") == 0) {
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
			personas = new ArrayList<BuscarPersonaDTO>();
			Object obj = getSessionMap().get("nuevoPersona");
			if (obj != null) {
				MpPersona mpPersona = (MpPersona) obj;
				if (mpPersona.getNroDj() != null && mpPersona.getNroDj() > 0) {
					personaId = mpPersona.getPersonaId();
				}
				getSessionMap().remove("nuevoPersona");
			}

			if (validarTipoBusqueda())
				personas = personaBo.findTraDocPersona(personaId,
						apellidoPaterno,
						apellidoMaterno,
						primerNombre,
						apellidosNombres,
						nroDocumentoIdentidad,
						nroDj,
						tipodocumentoIdent,
						razonSocial,
						codigoAnterior,
						placa);

		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}

	}
	
	// adulto mayor
	
	private ArrayList<FindMpPersona> personas1;
	private BuscarPersonaDTO buscarPersonaDTO;
	
	private BuscarPersonaDTO currentItem1 = new BuscarPersonaDTO();
	
	public void buscarAdulto() {
		
		try {
			setPersonas1(new ArrayList<FindMpPersona>());
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

				personas1 = personaBo.getmpPersona(personaId, apellidoPaterno,
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

	public void ver() {

		RegistroTramiteManaged registroTramiteManaged = (RegistroTramiteManaged) getManaged("registroTramiteManaged");
		registroTramiteManaged.setContribuyente(currentItem);
		
		DescargoDeudasManaged descargoDeudasManaged = (DescargoDeudasManaged) getManaged("descargoDeudasManaged");
		descargoDeudasManaged.setContribuyente(currentItem);
		
		descargoDeudasManaged.buscarRecibosContribuyente(currentItem.getPersonaId());
	}
	
	public void verConyuge() {

		RegistroTramiteManaged registroTramiteManaged = (RegistroTramiteManaged) getManaged("registroTramiteManaged");
		registroTramiteManaged.setRelacionadoCon(currentItemC);
		
		//DescargoDeudasManaged descargoDeudasManaged = (DescargoDeudasManaged) getManaged("descargoDeudasManaged");
		//descargoDeudasManaged.setContribuyente(currentItem);
		
		//descargoDeudasManaged.buscarRecibosContribuyente(currentItem.getPersonaId());
	}
	
	public void verRecurrente() {

		RegistroTramiteManaged registroTramiteManaged = (RegistroTramiteManaged) getManaged("registroTramiteManaged");
		registroTramiteManaged.setRecurrenteCont(currentItemR);
		
		//DescargoDeudasManaged descargoDeudasManaged = (DescargoDeudasManaged) getManaged("descargoDeudasManaged");
		//descargoDeudasManaged.setContribuyente(currentItem);
		
		//descargoDeudasManaged.buscarRecibosContribuyente(currentItem.getPersonaId());
	}
	
	public void verAdult() {

		RegistroTramiteManaged registroTramiteManaged = (RegistroTramiteManaged) getManaged("registroTramiteManaged");
		registroTramiteManaged.setContribuyente(currentItem1);
		
		DescargoDeudasManaged descargoDeudasManaged = (DescargoDeudasManaged) getManaged("descargoDeudasManaged");
		descargoDeudasManaged.setContribuyente(currentItem1);
		
		descargoDeudasManaged.buscarRecibosContribuyente(currentItem1.getPersonaId());
	}
	
	public void loadTipoPersonaById(ValueChangeEvent event) {
		try {
			String value = (String) event.getNewValue();
			if (value != null) {
				Integer tipoPersonaId = (Integer) getMapRpTipopersona().get(value);
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
					lMpTipoDocuIdentidad = personaBo.findMpTipoDocuIdentidad(tipodocumentoIdent);
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

	public List<BuscarPersonaDTO> getPersonas() {
		return personas;
	}

	public void setPersonas(List<BuscarPersonaDTO> personas) {
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

	public void setMapRpTipodocumento(HashMap<String, Integer> mapRpTipodocumento) {
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

	public BuscarPersonaDTO getCurrentItem() {
		return currentItem;
	}

	public void setCurrentItem(BuscarPersonaDTO currentItem) {
		this.currentItem = currentItem;
	}

	public BuscarPersonaDTO getFinMpPersonaItem() {
		return finMpPersonaItem;
	}

	public void setFinMpPersonaItem(BuscarPersonaDTO finMpPersonaItem) {
		this.finMpPersonaItem = finMpPersonaItem;
	}

	// public ArrayList<FindMpRelacionado> getRelacionadoPersona() {
	// return relacionadoPersona;
	// }
	//
	// public void setRelacionadoPersona(
	// ArrayList<FindMpRelacionado> relacionadoPersona) {
	// this.relacionadoPersona = relacionadoPersona;
	// }

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

	public ArrayList<FindMpPersona> getPersonas1() {
		return personas1;
	}

	public void setPersonas1(ArrayList<FindMpPersona> personas1) {
		this.personas1 = personas1;
	}

	public BuscarPersonaDTO getCurrentItem1() {
		return currentItem1;
	}

	public void setCurrentItem1(BuscarPersonaDTO currentItem1) {
		this.currentItem1 = currentItem1;
	}

	public RelacionadosDTO getCurrentItemC() {
		return currentItemC;
	}

	public void setCurrentItemC(RelacionadosDTO currentItemC) {
		this.currentItemC = currentItemC;
	}

	public BuscarPersonaDTO getCurrentItemR() {
		return currentItemR;
	}

	public void setCurrentItemR(BuscarPersonaDTO currentItemR) {
		this.currentItemR = currentItemR;
	}

}