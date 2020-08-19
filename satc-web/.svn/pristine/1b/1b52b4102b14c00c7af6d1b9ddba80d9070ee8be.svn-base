package com.sat.sisat.administracion.managed;

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
import org.richfaces.component.html.HtmlExtendedDataTable;
import org.richfaces.model.selection.SimpleSelection;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.persistence.entity.DtArancelUbicacion;
import com.sat.sisat.persistence.entity.DtCercaniaParque;
import com.sat.sisat.persistence.entity.DtFrecuenciaLimpieza;
import com.sat.sisat.persistence.entity.DtFrecuenciaRecojo;
import com.sat.sisat.persistence.entity.DtGrupoCercania;
import com.sat.sisat.persistence.entity.DtZonaSeguridad;
import com.sat.sisat.persistence.entity.DtZonaSeguridadUbicacion;
import com.sat.sisat.persistence.entity.GnLugar;
import com.sat.sisat.persistence.entity.GnSector;
import com.sat.sisat.persistence.entity.GnTipoVia;
import com.sat.sisat.persistence.entity.GnVia;
import com.sat.sisat.predial.business.RegistroPrediosBoRemote;
import com.sat.sisat.predial.dto.UbicacionDTO;

@ManagedBean
@ViewScoped
public class AdministrarDireccionManaged extends BaseManaged {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	RegistroPrediosBoRemote registroPrediosBo;

	private HtmlComboBox cmbsector;
	private HtmlComboBox cmbtipovia;

	private List<SelectItem> lstsector = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapGnSector = new HashMap<String, Integer>();

	private List<SelectItem> lsttipovia = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapGnTipoVia = new HashMap<String, Integer>();

	private String descripcion;
	private String selectedValue;

	private ArrayList<UbicacionDTO> records;
	private SimpleSelection selection = new SimpleSelection();
	private HtmlExtendedDataTable table;
	private UbicacionDTO currentItem = new UbicacionDTO();

	private List<SelectItem> listSelectItemGnVia = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapGnVia = new HashMap<String, Integer>();

	private List<SelectItem> listSelectItemGnLugar = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapGnLugar = new HashMap<String, Integer>();

	private Integer tipoViaId;
	private Integer viaId;

	private Integer sectorId;
	private Integer lugarId;

	private String comboBoxVia;
	private String comboBoxLugar;

	private List<SelectItem> listLadoCuadra = new ArrayList<SelectItem>();

	private String valueComboBoxLado = "";

	private DtArancelUbicacion arancelUbicacion = new DtArancelUbicacion();
	private List<DtArancelUbicacion> lstArancelUbicacion = new ArrayList<DtArancelUbicacion>();

	private DtFrecuenciaRecojo frecuenciaRecojo = new DtFrecuenciaRecojo();
	private List<DtFrecuenciaRecojo> lstFrecuenciaRecojo = new ArrayList<DtFrecuenciaRecojo>();

	private DtFrecuenciaLimpieza frecuenciaLimpieza = new DtFrecuenciaLimpieza();
	private List<DtFrecuenciaLimpieza> lstFrecuenciaLimpieza = new ArrayList<DtFrecuenciaLimpieza>();

	private DtCercaniaParque cercaniaParque = new DtCercaniaParque();
	private List<DtCercaniaParque> lstCercaniaParque = new ArrayList<DtCercaniaParque>();

	/** Valores de comboBox */
	private List<DtGrupoCercania> lstGrupoCercania = new ArrayList<DtGrupoCercania>();
	private List<SelectItem> lstSelectItemGrupoCercania = new ArrayList<SelectItem>();

	private DtZonaSeguridadUbicacion zonaSeguridadUbicacion = new DtZonaSeguridadUbicacion();
	private List<DtZonaSeguridadUbicacion> lstzonaSeguridadUbicacion = new ArrayList<DtZonaSeguridadUbicacion>();

	/** Valores para el comboBox */
	private List<DtZonaSeguridad> lstZonaSeguridad = new ArrayList<DtZonaSeguridad>();
	private List<SelectItem> lstSelectItemZonaSeguridad = new ArrayList<SelectItem>();

	private Integer periodo = DateUtil.getAnioActual();

	private String labelZonaSeguridad;
	private String labelGrupoCercania;

	private Boolean flagEdicion = Boolean.TRUE;

	private Integer cercaniaParqueId;
	
	private Boolean flagFrecuenciaLimpieza = Boolean.TRUE;
	private Boolean flagFrecuenciaRecojo = Boolean.TRUE;
	private Boolean flagCercaniaParque = Boolean.TRUE;
	private Boolean flagZonaSeguridadUbicacion = Boolean.TRUE;
	
	private Integer ubicacionId;


	public AdministrarDireccionManaged() {

	}

	@PostConstruct
	public void init() {
		try {
			// GnTipoVia
			List<GnTipoVia> lstGnTipoVia = registroPrediosBo.getAllGnTipoVia(null);
			Iterator<GnTipoVia> it = lstGnTipoVia.iterator();
			lsttipovia = new ArrayList<SelectItem>();
//			mapGnTipoVia.put("Todos", 0);
//			lsttipovia.add(new SelectItem("Todos", null));
			while (it.hasNext()) {
				GnTipoVia obj = it.next();
				lsttipovia.add(new SelectItem(obj.getDescripcion(), String.valueOf(obj.getTipoViaId())));
				mapGnTipoVia.put(obj.getDescripcion(), obj.getTipoViaId());
			}

			// GnSector
			List<GnSector> lstGnSector = registroPrediosBo.getAllGnSector();

			Iterator<GnSector> it2 = lstGnSector.iterator();
			lstsector = new ArrayList<SelectItem>();
//			mapGnSector.put("Todos", 0);
//			lstsector.add(new SelectItem("Todos", null));

			while (it2.hasNext()) {
				GnSector obj = it2.next();
				lstsector.add(new SelectItem(obj.getDescripcion(), String.valueOf(obj.getSectorId())));
				mapGnSector.put(obj.getDescripcion(), obj.getSectorId());
			}

			currentItem = new UbicacionDTO();

			listLadoCuadra.add(new SelectItem("1 - DER", "1"));
			listLadoCuadra.add(new SelectItem("2 - IZQ", "2"));
			
			flagFrecuenciaLimpieza = Boolean.TRUE;
			flagFrecuenciaRecojo = Boolean.TRUE;
			flagCercaniaParque = Boolean.TRUE;
			flagZonaSeguridadUbicacion = Boolean.TRUE;

		} catch (SisatException e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void buscar() {
		try {
			if ((tipoViaId != null && tipoViaId > 0) && (viaId != null && viaId > 0)
					&& (sectorId != null && sectorId > 0) && (lugarId != null && lugarId > 0)) {
				limpiar();
				records = registroPrediosBo.findGnUbicacion(tipoViaId, viaId, sectorId, lugarId);
			}else if(ubicacionId != null && ubicacionId > 0){
				limpiar();
				records = registroPrediosBo.findGnUbicacionById(ubicacionId);
			}else if (tipoViaId == null || tipoViaId <= 0) {
				addErrorMessage("Debe seleccionar un Tipo de Via valido");
			} else if (viaId == null || viaId <= 0) {
				addErrorMessage("Debe seleccionar una Via valida");
			} else if (sectorId == null || sectorId <= 0) {
				addErrorMessage("Debe seleccionar un Sector valido");
			} else if (lugarId == null || lugarId <= 0) {
				addErrorMessage("Debe seleccionar un Lugar valido");
			}		
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void agregarUbicacion() {
		try {
			if (validarDatos()) {
				registroPrediosBo.agregarUbicacion(currentItem);
				limpiar();
				WebMessages.messageInfo("La ubicación ha sido añadida correctamente");
				
			} else if (tipoViaId == null && tipoViaId <= 0) {
				addErrorMessage("Debe seleccionar un Tipo de Via valido");
			} else if (viaId == null && viaId <= 0) {
				addErrorMessage("Debe seleccionar una Via valida");
			} else if (sectorId == null && sectorId <= 0) {
				addErrorMessage("Debe seleccionar un Sector valido");
			} else if (lugarId == null && lugarId <= 0) {
				addErrorMessage("Debe seleccionar un Lugar valido");
			} else {
				addErrorMessage("Debe ingresar todos los datos requeridos");
			}

			buscar();
		} catch (Exception e) {
			WebMessages.messageError(e.getMessage());
		}
	}

	public Boolean validarDatos() {
		Boolean valido = new Boolean(false);

		if ((tipoViaId != null && tipoViaId > 0) && (viaId != null && viaId > 0) && (sectorId != null && sectorId > 0)
				&& (lugarId != null && lugarId > 0)
				&& (currentItem.getNumeroCuadra() != null && currentItem.getNumeroCuadra().compareTo(0) > 0)
				&& (currentItem.getNumeroManzana() != null && currentItem.getNumeroManzana().compareTo(0) > 0)
				&& (currentItem.getLado() != null && currentItem.getLado().compareTo(0) > 0)) {
			valido = true;
		} else {
			valido = false;
		}
		return valido;
	}

	public void seleccionItem() {
		try {
			flagFrecuenciaLimpieza = Boolean.TRUE;
			flagFrecuenciaRecojo = Boolean.TRUE;
			flagCercaniaParque = Boolean.TRUE;
			flagZonaSeguridadUbicacion = Boolean.TRUE;

			arancelUbicacion = registroPrediosBo.getDtArancelUbicacion(currentItem.getUbicacionId(), periodo);
			frecuenciaRecojo = registroPrediosBo.getDtFrecuenciaRecojo(currentItem.getUbicacionId(), periodo);
			frecuenciaLimpieza = registroPrediosBo.getDtFrecuenciaLimpieza(currentItem.getUbicacionId(), periodo);
			cercaniaParque = registroPrediosBo.getDtCercaniaParque(currentItem.getUbicacionId(), periodo);
			zonaSeguridadUbicacion = registroPrediosBo.getDtZonaSeguridadUbicacion(currentItem.getUbicacionId(),
					periodo);

			lstGrupoCercania = registroPrediosBo.getAllDtGrupoCercania(periodo);

			lstSelectItemGrupoCercania.clear();
			for (DtGrupoCercania item : lstGrupoCercania) {
				lstSelectItemGrupoCercania.add(new SelectItem(item.getGrupoCercaniaId(), item.getDescipcionCorta()));
			}

			lstZonaSeguridad = registroPrediosBo.getAllDtZonaSeguridad(periodo);
			lstSelectItemZonaSeguridad.clear();
			for (DtZonaSeguridad item : lstZonaSeguridad) {
				lstSelectItemZonaSeguridad.add(new SelectItem(item.getZonaSeguridadId(), item.getDescripcionCorta()));
			}

			if (arancelUbicacion.getArancelUbicacionId() == null && frecuenciaRecojo.getFrecuenciaRecojoId() == null
					&& frecuenciaLimpieza.getFrecuenciaLimpiezaId() == null
					&& cercaniaParque.getGrupoCercaniaId() == null
					&& zonaSeguridadUbicacion.getZonaSeguridadId() == null) {
				flagEdicion = false;
			} else if (arancelUbicacion == null) {
				arancelUbicacion.setValorArancel(null);
			} else if (frecuenciaRecojo == null) {
				frecuenciaRecojo.setFrecuencia(null);
			} else if (frecuenciaLimpieza == null) {
				frecuenciaLimpieza.setFrecuencia(null);
				frecuenciaLimpieza.setTasaMlAnual(null);
			} else if (cercaniaParque == null) {
				cercaniaParque.setGrupoCercaniaId(null);
			} else if (zonaSeguridadUbicacion == null) {
				zonaSeguridadUbicacion.setZonaSeguridadId(null);
			}

		} catch (SisatException e) {
			WebMessages.messageError(e.getMessage());
		}
	}

	public void agregarValoresArancelArbitrios() {

		debug("agregarValoresArancelArbitrios - inicio");

		try {
			if (validarCampos()) {
				// Para arancel
				arancelUbicacion.setUbicacionId(currentItem.getUbicacionId());
				arancelUbicacion.setPeriodo(periodo);
				registroPrediosBo.agregarArancelUbicacion(arancelUbicacion);
				
				// Para Frecuencia de Barrido - Limpieza
				if(flagFrecuenciaLimpieza){
					frecuenciaLimpieza.setUbicacionId(currentItem.getUbicacionId());
					frecuenciaLimpieza.setPeriodo(periodo);
					frecuenciaLimpieza.setSubconceptoId(Constante.SUB_CONCEPTO_ARBITRIOS_BARRIDO);
					frecuenciaLimpieza.setConceptoId(Constante.CONCEPTO_ARBITRIO);
					registroPrediosBo.agregarFrecuenciaLimpieza(frecuenciaLimpieza);
				}else{
					WebMessages.messageError("No se agregara Frecuencia de Barrido");
				}
				
				// Para Frecuencia de Recojo
				if(flagFrecuenciaRecojo){
					frecuenciaRecojo.setUbicacionId(currentItem.getUbicacionId());
					frecuenciaRecojo.setPeriodo(periodo);
					registroPrediosBo.agregarFrecuenciaRecojo(frecuenciaRecojo);
				}else{
					WebMessages.messageError("No se agregara Frecuencia de Recojo");
				}

				// Para Cercania de Parques
				if(flagCercaniaParque){
					cercaniaParque.setUbicacionId(currentItem.getUbicacionId());
					cercaniaParque.setPeriodo(periodo);
					registroPrediosBo.agregarCercaniaParque(cercaniaParque);
				}else{
					WebMessages.messageError("No se agregara Cercania de Parques");
				}
				
				// Para Zona de Seguridad con Ubicacion
				if(flagZonaSeguridadUbicacion){
					zonaSeguridadUbicacion.setUbicacionId(currentItem.getUbicacionId());
					zonaSeguridadUbicacion.setPeriodo(periodo);
					registroPrediosBo.agregarZonaSeguridadUbicacion(zonaSeguridadUbicacion);
				}else{
					WebMessages.messageError("No se agregara Zona de Seguridad");
				}

				String mensaje;
				if (flagEdicion) {
					mensaje = "<< Los valores han sido actualizados correctamente >>";
				} else {
					mensaje = "<< Los valores han sido agregados correctamente. >>";
				}
				cancelar();

				WebMessages.messageInfo(mensaje);
			}
		} catch (SisatException e) {

			WebMessages.messageError(e.getMessage());
		} catch (Exception e) {
			WebMessages.messageFatal(e);
		}

		debug("agregarValoresArancelArbitrios - fin");

	}

	public Boolean validarCampos() {
		Boolean resp = Boolean.TRUE;

		if (periodo == null || periodo <= 0) {
			WebMessages.messageError("Ingrese un periodo valido");
			resp = Boolean.FALSE;
		}else{
			if (arancelUbicacion.getValorArancel() == null || arancelUbicacion.getValorArancel() <=0) {
				WebMessages.messageError("Ingrese un valor de arancel mayor a cero");
				resp = Boolean.FALSE;
			}else{
				if (frecuenciaLimpieza.getFrecuencia() == null) {
					//WebMessages.messageError("Ingrese una frecuencia para el Barrido ");
					//resp = Boolean.FALSE;
					flagFrecuenciaLimpieza = Boolean.FALSE; 
				}
				if (frecuenciaLimpieza.getTasaMlAnual() == null) {
					//WebMessages.messageError("Ingrese una tasa anual");
					//resp = Boolean.FALSE;
					flagFrecuenciaLimpieza = Boolean.FALSE;
				}
				if (frecuenciaRecojo.getFrecuencia() == null) {
					//WebMessages.messageError("Ingrese un frecuencia para el Recojo");
					//resp = Boolean.FALSE;
					flagFrecuenciaRecojo = Boolean.FALSE;
				}
				if (cercaniaParque.getGrupoCercaniaId() == null || cercaniaParque.getGrupoCercaniaId() == -1) {
					//WebMessages.messageError("Seleccione un grupo de cercania para parques");
					//resp = Boolean.FALSE;
					flagCercaniaParque = Boolean.FALSE;
				}
				if (zonaSeguridadUbicacion.getZonaSeguridadId() == null || zonaSeguridadUbicacion.getZonaSeguridadId() == -1) {
					//WebMessages.messageError("Selecciones una zona de seguridad");
					//resp = Boolean.FALSE;
					flagZonaSeguridadUbicacion = Boolean.FALSE;
				}
			}
			
		}
		

		return resp;
	}

	public void cancelar() {
		arancelUbicacion = null;
		frecuenciaRecojo = null;
		frecuenciaLimpieza = null;
		cercaniaParque = null;
		zonaSeguridadUbicacion = null;
		flagEdicion = Boolean.TRUE;
		periodo = DateUtil.getAnioActual();		
		
		flagFrecuenciaLimpieza = Boolean.TRUE;
		flagFrecuenciaRecojo = Boolean.TRUE;
		flagCercaniaParque = Boolean.TRUE;
		flagZonaSeguridadUbicacion = Boolean.TRUE;
	}

	public void salir() {
		limpiar();
	}

	public void limpiar() {
		currentItem.setNumeroCuadra(null);
		currentItem.setLado(null);
		currentItem.setNumeroManzana(null);
		valueComboBoxLado = null;
	}

	public void changeListenerComboBoxTipoVia(ValueChangeEvent event) {

		String key = (String) event.getNewValue();
		Integer id = mapGnTipoVia.get(key);
		if (id == null) {

			WebMessages.messageError("El tipo de via ingresado es incorrecto o seleccione un tipo de via");
			this.tipoViaId = -1;
			return;
		}
		try {

			this.tipoViaId = id;
			List<GnVia> list = registroPrediosBo.getAllGnVia(id);
			listSelectItemGnVia = null;
			listSelectItemGnVia = new ArrayList<SelectItem>();
			mapGnVia = null;
			mapGnVia = new HashMap<String, Integer>();
//			mapGnVia.put("Todos", 0);
//			listSelectItemGnVia.add(new SelectItem("Todos", null));
			for (GnVia gnVia : list) {
				listSelectItemGnVia.add(new SelectItem(gnVia.getDescripcion(), String.valueOf(gnVia.getViaId())));
				mapGnVia.put(gnVia.getDescripcion(), gnVia.getViaId());
			}
			this.comboBoxVia = null;
			this.currentItem.setTipoViaId(tipoViaId);

		} catch (SisatException e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}

	}

	public void changeListenerComboBoxSector(ValueChangeEvent event) throws Exception {

		String key = (String) event.getNewValue();
		Integer id = mapGnSector.get(key);
		if (id == null) {

			WebMessages.messageError("El sector ingresado es incorrecto o seleccione un sector");
			this.sectorId = -1;
			return;
		}
		try {
			this.sectorId = id;
			List<GnLugar> list = registroPrediosBo.findGnLugar(sectorId);
			listSelectItemGnLugar = null;
			listSelectItemGnLugar = new ArrayList<SelectItem>();
			mapGnLugar = null;
			mapGnLugar = new HashMap<String, Integer>();
//			mapGnLugar.put("Todos", 0);
//			listSelectItemGnLugar.add(new SelectItem("Todos", null));
			for (GnLugar gnLugar : list) {
				listSelectItemGnLugar
						.add(new SelectItem(gnLugar.getDescripcion(), String.valueOf(gnLugar.getLugarId())));
				mapGnLugar.put(gnLugar.getDescripcion(), gnLugar.getLugarId());
			}
			this.comboBoxLugar = null;
			this.currentItem.setSectorId(sectorId);
		} catch (SisatException e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}

	}

	public void changeListenerComboBoxVia(ValueChangeEvent event) {
		String key = (String) event.getNewValue();
		Integer id = mapGnVia.get(key);

		if (id == null) {

			WebMessages.messageError("La via ingresada es incorrecta o seleccione una via");
			this.viaId = -1;
			return;
		}
		this.viaId = id;
		this.currentItem.setViaid(viaId);

	}

	public void changeListenerComboBoxLugar(ValueChangeEvent event) {
		String key = (String) event.getNewValue();
		Integer id = mapGnLugar.get(key);

		if (id == null) {

			WebMessages.messageError("Lugar ingresado es incorrecto o seleccione un lugar");
			this.lugarId = -1;
			return;
		}
		this.lugarId = id;
		this.currentItem.setLugarId(lugarId);

	}

	public void changeListenerComboBoxLado(ValueChangeEvent event) {
		String key = (String) event.getNewValue();

		if (key.contains("2 - IZQ")) {
			this.currentItem.setLado(2);
			this.valueComboBoxLado = "2 - IZQ";
		} else if (key.contains("1 - DER")) {
			this.currentItem.setLado(1);
			this.valueComboBoxLado = "1 - DER";
		} else {
			WebMessages.messageError("El lado es incorrecto");
			this.valueComboBoxLado = null;
		}

	}

	public void onBlurInputTextPeridoId() {

		try {
			
			flagFrecuenciaLimpieza = Boolean.TRUE;
			flagFrecuenciaRecojo = Boolean.TRUE;
			flagCercaniaParque = Boolean.TRUE;
			flagZonaSeguridadUbicacion = Boolean.TRUE;

			arancelUbicacion = registroPrediosBo.getDtArancelUbicacion(currentItem.getUbicacionId(), periodo);
			frecuenciaRecojo = registroPrediosBo.getDtFrecuenciaRecojo(currentItem.getUbicacionId(), periodo);
			frecuenciaLimpieza = registroPrediosBo.getDtFrecuenciaLimpieza(currentItem.getUbicacionId(), periodo);
			cercaniaParque = registroPrediosBo.getDtCercaniaParque(currentItem.getUbicacionId(), periodo);
			zonaSeguridadUbicacion = registroPrediosBo.getDtZonaSeguridadUbicacion(currentItem.getUbicacionId(),
					periodo);

			lstGrupoCercania = registroPrediosBo.getAllDtGrupoCercania(periodo);

			lstSelectItemGrupoCercania.clear();
			for (DtGrupoCercania item : lstGrupoCercania) {

				lstSelectItemGrupoCercania.add(new SelectItem(item.getGrupoCercaniaId(), item.getDescipcionCorta()));
			}

			lstZonaSeguridad = registroPrediosBo.getAllDtZonaSeguridad(periodo);

			lstSelectItemZonaSeguridad.clear();
			for (DtZonaSeguridad item : lstZonaSeguridad) {

				lstSelectItemZonaSeguridad.add(new SelectItem(item.getZonaSeguridadId(), item.getDescripcionCorta()));
			}

			if (arancelUbicacion.getArancelUbicacionId() == null && frecuenciaRecojo.getFrecuenciaRecojoId() == null
					&& frecuenciaLimpieza.getFrecuenciaLimpiezaId() == null
					&& cercaniaParque.getGrupoCercaniaId() == null
					&& zonaSeguridadUbicacion.getZonaSeguridadId() == null) {
				flagEdicion = false;
			} else if (arancelUbicacion == null) {
				arancelUbicacion.setValorArancel(null);
			} else if (frecuenciaRecojo == null) {
				frecuenciaRecojo.setFrecuencia(null);
			} else if (frecuenciaLimpieza == null) {
				frecuenciaLimpieza.setFrecuencia(null);
				frecuenciaLimpieza.setTasaMlAnual(null);
			} else if (cercaniaParque == null) {
				cercaniaParque.setGrupoCercaniaId(null);
			} else if (zonaSeguridadUbicacion == null) {
				zonaSeguridadUbicacion.setZonaSeguridadId(null);
			}

		} catch (Exception e) {
			WebMessages.messageError(e.getMessage());
		}
	}


	public List<SelectItem> getLstsector() {
		return lstsector;
	}

	public void setLstsector(List<SelectItem> lstsector) {
		this.lstsector = lstsector;
	}

	public List<SelectItem> getLsttipovia() {
		return lsttipovia;
	}

	public void setLsttipovia(List<SelectItem> lsttipovia) {
		this.lsttipovia = lsttipovia;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public ArrayList<UbicacionDTO> getRecords() {
		return records;
	}

	public void setRecords(ArrayList<UbicacionDTO> records) {
		this.records = records;
	}

	public String getSelectedValue() {
		return selectedValue;
	}

	public void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}

	public SimpleSelection getSelection() {
		return selection;
	}

	public void setSelection(SimpleSelection selection) {
		this.selection = selection;
	}

	public HtmlExtendedDataTable getTable() {
		return table;
	}

	public void setTable(HtmlExtendedDataTable table) {
		this.table = table;
	}

	public UbicacionDTO getCurrentItem() {
		return currentItem;
	}

	public void setCurrentItem(UbicacionDTO currentItem) {
		this.currentItem = currentItem;
	}

	public HtmlComboBox getCmbsector() {
		return cmbsector;
	}

	public void setCmbsector(HtmlComboBox cmbsector) {
		this.cmbsector = cmbsector;
	}

	public HtmlComboBox getCmbtipovia() {
		return cmbtipovia;
	}

	public void setCmbtipovia(HtmlComboBox cmbtipovia) {
		this.cmbtipovia = cmbtipovia;
	}

	public List<SelectItem> getListSelectItemGnVia() {
		return listSelectItemGnVia;
	}

	public void setListSelectItemGnVia(List<SelectItem> listSelectItemGnVia) {
		this.listSelectItemGnVia = listSelectItemGnVia;
	}

	public Integer getViaId() {
		return viaId;
	}

	public void setViaId(Integer viaId) {
		this.viaId = viaId;
	}

	public Integer getTipoViaId() {
		return tipoViaId;
	}

	public void setTipoViaId(Integer tipoViaId) {
		this.tipoViaId = tipoViaId;
	}

	public String getComboBoxVia() {
		return comboBoxVia;
	}

	public void setComboBoxVia(String comboBoxVia) {
		this.comboBoxVia = comboBoxVia;
	}

	public Integer getSectorId() {
		return sectorId;
	}

	public void setSectorId(Integer sectorId) {
		this.sectorId = sectorId;
	}

	public Integer getLugarId() {
		return lugarId;
	}

	public void setLugarId(Integer lugarId) {
		this.lugarId = lugarId;
	}

	public List<SelectItem> getListSelectItemGnLugar() {
		return listSelectItemGnLugar;
	}

	public void setListSelectItemGnLugar(List<SelectItem> listSelectItemGnLugar) {
		this.listSelectItemGnLugar = listSelectItemGnLugar;
	}

	public HashMap<String, Integer> getMapGnLugar() {
		return mapGnLugar;
	}

	public void setMapGnLugar(HashMap<String, Integer> mapGnLugar) {
		this.mapGnLugar = mapGnLugar;
	}

	public String getComboBoxLugar() {
		return comboBoxLugar;
	}

	public void setComboBoxLugar(String comboBoxLugar) {
		this.comboBoxLugar = comboBoxLugar;
	}

	public List<SelectItem> getListLadoCuadra() {
		return listLadoCuadra;
	}

	public void setListLadoCuadra(List<SelectItem> listLadoCuadra) {
		this.listLadoCuadra = listLadoCuadra;
	}

	public String getValueComboBoxLado() {
		return valueComboBoxLado;
	}

	public void setValueComboBoxLado(String valueComboBoxLado) {
		this.valueComboBoxLado = valueComboBoxLado;
	}

	public List<DtArancelUbicacion> getLstArancelUbicacion() {
		return lstArancelUbicacion;
	}

	public void setLstArancelUbicacion(List<DtArancelUbicacion> lstArancelUbicacion) {
		this.lstArancelUbicacion = lstArancelUbicacion;
	}

	public DtArancelUbicacion getArancelUbicacion() {
		return arancelUbicacion;
	}

	public void setArancelUbicacion(DtArancelUbicacion arancelUbicacion) {
		this.arancelUbicacion = arancelUbicacion;
	}

	public DtFrecuenciaRecojo getFrecuenciaRecojo() {
		return frecuenciaRecojo;
	}

	public void setFrecuenciaRecojo(DtFrecuenciaRecojo frecuenciaRecojo) {
		this.frecuenciaRecojo = frecuenciaRecojo;
	}

	public List<DtFrecuenciaRecojo> getLstFrecuenciaRecojo() {
		return lstFrecuenciaRecojo;
	}

	public void setLstFrecuenciaRecojo(List<DtFrecuenciaRecojo> lstFrecuenciaRecojo) {
		this.lstFrecuenciaRecojo = lstFrecuenciaRecojo;
	}

	public DtCercaniaParque getCercaniaParque() {
		return cercaniaParque;
	}

	public void setCercaniaParque(DtCercaniaParque cercaniaParque) {
		this.cercaniaParque = cercaniaParque;
	}

	public List<DtCercaniaParque> getLstCercaniaParque() {
		return lstCercaniaParque;
	}

	public void setLstCercaniaParque(List<DtCercaniaParque> lstCercaniaParque) {
		this.lstCercaniaParque = lstCercaniaParque;
	}

	public DtZonaSeguridadUbicacion getZonaSeguridadUbicacion() {
		return zonaSeguridadUbicacion;
	}

	public void setZonaSeguridadUbicacion(DtZonaSeguridadUbicacion zonaSeguridadUbicacion) {
		this.zonaSeguridadUbicacion = zonaSeguridadUbicacion;
	}

	public List<DtZonaSeguridadUbicacion> getLstzonaSeguridadUbicacion() {
		return lstzonaSeguridadUbicacion;
	}

	public void setLstzonaSeguridadUbicacion(List<DtZonaSeguridadUbicacion> lstzonaSeguridadUbicacion) {
		this.lstzonaSeguridadUbicacion = lstzonaSeguridadUbicacion;
	}

	public List<DtGrupoCercania> getLstGrupoCercania() {
		return lstGrupoCercania;
	}

	public void setLstGrupoCercania(List<DtGrupoCercania> lstGrupoCercania) {
		this.lstGrupoCercania = lstGrupoCercania;
	}

	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}

	public List<DtZonaSeguridad> getLstZonaSeguridad() {
		return lstZonaSeguridad;
	}

	public void setLstZonaSeguridad(List<DtZonaSeguridad> lstZonaSeguridad) {
		this.lstZonaSeguridad = lstZonaSeguridad;
	}

	public List<SelectItem> getLstSelectItemZonaSeguridad() {
		return lstSelectItemZonaSeguridad;
	}

	public void setLstSelectItemZonaSeguridad(List<SelectItem> lstSelectItemZonaSeguridad) {
		this.lstSelectItemZonaSeguridad = lstSelectItemZonaSeguridad;
	}

	public List<SelectItem> getLstSelectItemGrupoCercania() {
		return lstSelectItemGrupoCercania;
	}

	public void setLstSelectItemGrupoCercania(List<SelectItem> lstSelectItemGrupoCercania) {
		this.lstSelectItemGrupoCercania = lstSelectItemGrupoCercania;
	}

	public String getLabelZonaSeguridad() {
		return labelZonaSeguridad;
	}

	public void setLabelZonaSeguridad(String labelZonaSeguridad) {
		this.labelZonaSeguridad = labelZonaSeguridad;
	}

	public String getLabelGrupoCercania() {
		return labelGrupoCercania;
	}

	public void setLabelGrupoCercania(String labelGrupoCercania) {
		this.labelGrupoCercania = labelGrupoCercania;
	}

	public boolean isFlagEdicion() {
		return flagEdicion;
	}

	public void setFlagEdicion(boolean flagEdicion) {
		this.flagEdicion = flagEdicion;
	}

	public DtFrecuenciaLimpieza getFrecuenciaLimpieza() {
		return frecuenciaLimpieza;
	}

	public void setFrecuenciaLimpieza(DtFrecuenciaLimpieza frecuenciaLimpieza) {
		this.frecuenciaLimpieza = frecuenciaLimpieza;
	}

	public List<DtFrecuenciaLimpieza> getLstFrecuenciaLimpieza() {
		return lstFrecuenciaLimpieza;
	}

	public void setLstFrecuenciaLimpieza(List<DtFrecuenciaLimpieza> lstFrecuenciaLimpieza) {
		this.lstFrecuenciaLimpieza = lstFrecuenciaLimpieza;
	}

	public Integer getCercaniaParqueId() {
		return cercaniaParqueId;
	}

	public void setCercaniaParqueId(Integer cercaniaParqueId) {
		this.cercaniaParqueId = cercaniaParqueId;
	}

	public Boolean getFlagFrecuenciaLimpieza() {
		return flagFrecuenciaLimpieza;
	}

	public void setFlagFrecuenciaLimpieza(Boolean flagFrecuenciaLimpieza) {
		this.flagFrecuenciaLimpieza = flagFrecuenciaLimpieza;
	}

	public Boolean getFlagFrecuenciaRecojo() {
		return flagFrecuenciaRecojo;
	}

	public void setFlagFrecuenciaRecojo(Boolean flagFrecuenciaRecojo) {
		this.flagFrecuenciaRecojo = flagFrecuenciaRecojo;
	}

	public Boolean getFlagCercaniaParque() {
		return flagCercaniaParque;
	}

	public void setFlagCercaniaParque(Boolean flagCercaniaParque) {
		this.flagCercaniaParque = flagCercaniaParque;
	}

	public Boolean getFlagZonaSeguridadUbicacion() {
		return flagZonaSeguridadUbicacion;
	}

	public void setFlagZonaSeguridadUbicacion(Boolean flagZonaSeguridadUbicacion) {
		this.flagZonaSeguridadUbicacion = flagZonaSeguridadUbicacion;
	}

	public Integer getUbicacionId() {
		return ubicacionId;
	}

	public void setUbicacionId(Integer ubicacionId) {
		this.ubicacionId = ubicacionId;
	}
}
