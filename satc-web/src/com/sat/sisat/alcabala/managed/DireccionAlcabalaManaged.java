package com.sat.sisat.alcabala.managed;

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
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.persistence.entity.GnDepartamento;
import com.sat.sisat.persistence.entity.GnDistrito;
import com.sat.sisat.persistence.entity.GnTipoEdificacion;
import com.sat.sisat.persistence.entity.GnTipoIngreso;
import com.sat.sisat.persistence.entity.GnTipoInterior;
import com.sat.sisat.persistence.entity.GnTipoVia;
import com.sat.sisat.persistence.entity.MpDireccion;
import com.sat.sisat.persistence.entity.RaDireccionAlcabala;
import com.sat.sisat.persistence.entity.Ubigeo;
import com.sat.sisat.persona.business.PersonaBoRemote;

@ManagedBean
@ViewScoped
public class DireccionAlcabalaManaged extends BaseManaged {

	private static final long serialVersionUID = 1L;

	@EJB
	GeneralBoRemote generalBo;

	@EJB
	PersonaBoRemote personaBo;

	private List<SelectItem> lsttipovia = new ArrayList<SelectItem>();

	private HashMap<Integer, String> mapIGnTipoVia = new HashMap<Integer, String>();

	public HashMap<Integer, String> getMapIGnTipoVia() {
		return mapIGnTipoVia;
	}

	public void setMapIGnTipoVia(HashMap<Integer, String> mapIGnTipoVia) {
		this.mapIGnTipoVia = mapIGnTipoVia;
	}

	public HashMap<String, Integer> getMapGnTipoVia() {
		return mapGnTipoVia;
	}

	public void setMapGnTipoVia(HashMap<String, Integer> mapGnTipoVia) {
		this.mapGnTipoVia = mapGnTipoVia;
	}

	private HashMap<String, Integer> mapGnTipoVia = new HashMap<String, Integer>();
	private Integer departamentoId = Constante.DEPARTAMENTO_ID_DEFECTO;
	private String direccionCompleta;
	private String referencia;
	private String nombreVia;
	private String numero;
	private String letra;
	private String numero2;
	private String letra2;
	private String nombreEdificacion;
	private String piso;
	private String numeroInterior;
	private String letraInterior;
	private String numeroIngreso;
	private String manzana;
	private String lote;

	private Integer tipoVia;

	private Boolean selVia = Boolean.FALSE;

	
	private String valorDistrito;
	private String valorTipoVia;
	private String valorTipoEdificacion;
	
	public void loadTIpoVia(ValueChangeEvent event) {
		try {
			String value = (String) event.getNewValue();

			tipoVia = (Integer) mapGnTipoVia.get(value);

			if (tipoVia != null) {
				setSelVia(Boolean.TRUE);
			} else {
				setSelVia(Boolean.FALSE);
			}

		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	private HtmlComboBox cmbTipoVia;

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getLetra() {
		return letra;
	}

	public void setLetra(String letra) {
		this.letra = letra;
	}

	public String getNumero2() {
		return numero2;
	}

	public void setNumero2(String numero2) {
		this.numero2 = numero2;
	}

	public String getLetra2() {
		return letra2;
	}

	public void setLetra2(String letra2) {
		this.letra2 = letra2;
	}

	public void grabarDireccion() {

	}

	public String getDireccionCompleta() {
		return direccionCompleta;
	}

	public void setDireccionCompleta(String direccionCompleta) {
		this.direccionCompleta = direccionCompleta;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getNombreVia() {
		return nombreVia;
	}

	public void setNombreVia(String nombreVia) {
		this.nombreVia = nombreVia;
	}


	public String getNombreEdificacion() {
		return nombreEdificacion;
	}

	public void setNombreEdificacion(String nombreEdificacion) {
		this.nombreEdificacion = nombreEdificacion;
	}

	public String getPiso() {
		return piso;
	}

	public void setPiso(String piso) {
		this.piso = piso;
	}

	public String getNumeroInterior() {
		return numeroInterior;
	}

	public void setNumeroInterior(String numeroInterior) {
		this.numeroInterior = numeroInterior;
	}

	public String getLetraInterior() {
		return letraInterior;
	}

	public void setLetraInterior(String letraInterior) {
		this.letraInterior = letraInterior;
	}

	public String getNumeroIngreso() {
		return numeroIngreso;
	}

	public void setNumeroIngreso(String numeroIngreso) {
		this.numeroIngreso = numeroIngreso;
	}

	public String getManzana() {
		return manzana;
	}

	public void setManzana(String manzana) {
		this.manzana = manzana;
	}

	public String getLote() {
		return lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	public int traeIdDireccionAlcabala() {

		try {
			int id = generalBo.ObtenerCorrelativoTabla("ra_direccion_alcabala",
					1);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	private MpDireccion mpDireccion;

	public MpDireccion getMpDireccion() {
		return mpDireccion;
	}

	public void setMpDireccion(MpDireccion mpDireccion) {
		this.mpDireccion = mpDireccion;
	}

	private String cmbvaluedepartamento;
	private String cmbvalueprovincia;
	private HtmlComboBox cmbDistrito;
	private String cmbvaluedistrito;
	private Ubigeo ubigeo;

	private Integer idDIstrito;

	public void loadIdDistrito(ValueChangeEvent event) {
		try {
			String value = (String) event.getNewValue();

			if (value != null) {
				// Integer
				idDIstrito = (Integer) mapGnDistrito.get(value);

			}

		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public Ubigeo getUbigeo() {
		return ubigeo;
	}

	public void setUbigeo(Ubigeo ubigeo) {
		this.ubigeo = ubigeo;
	}

	private List<SelectItem> lstdepartamento = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapGnDepartamento = new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIGnDepartamento = new HashMap<Integer, String>();
	private List<SelectItem> lsttipoedificio;

	public List<SelectItem> getLsttipoedificio() {
		return lsttipoedificio;
	}

	public void setLsttipoedificio(List<SelectItem> lsttipoedificio) {
		this.lsttipoedificio = lsttipoedificio;
	}

	public List<SelectItem> getLsttipoingreso() {
		return lsttipoingreso;
	}

	public void setLsttipoingreso(List<SelectItem> lsttipoingreso) {
		this.lsttipoingreso = lsttipoingreso;
	}

	public List<SelectItem> getLsttipointerior() {
		return lsttipointerior;
	}

	public void setLsttipointerior(List<SelectItem> lsttipointerior) {
		this.lsttipointerior = lsttipointerior;
	}

	private HashMap<String, Integer> mapGnTipoEdificacion = new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIGnTipoEdificacion = new HashMap<Integer, String>();
	private List<SelectItem> lsttipoingreso;
	private HashMap<String, Integer> mapGnTipoIngreso = new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIGnTipoIngreso = new HashMap<Integer, String>();
	private HashMap<String, Integer> mapGnTipoInterior = new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIGnTipoInterior = new HashMap<Integer, String>();
	private List<SelectItem> lsttipointerior;

	private List<SelectItem> lstdistrito = new ArrayList<SelectItem>();

	public List<SelectItem> getLstdistrito() {
		return lstdistrito;
	}

	public void setLstdistrito(List<SelectItem> lstdistrito) {
		this.lstdistrito = lstdistrito;
	}

	private HashMap<String, Integer> mapGnDistrito = new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIGnDistrito = new HashMap<Integer, String>();
	private Integer provinciaId = Constante.PROVINCIA_ID_DEFECTO;

	private HtmlComboBox cmbtipoedificio;
	private String cmbvaluetipoedificio;
	private HtmlComboBox cmbtipointerior;
	private String cmbvaluetipointerior;

	private HtmlComboBox cmbtipoingreso;
	private String cmbvaluetipoingreso;

	@PostConstruct
	public void init() {
		try {
			
			setNombreVia("");
			setNumero("");
			setLetra("");
			setNumero2("");
			setLetra2("");

			setNombreEdificacion("");
			setPiso("");
			setNumeroInterior("");
			setLetraInterior("");
			setNumeroIngreso("");
			setManzana("");
			setLote("");
			setReferencia("");
			
			mpDireccion = new MpDireccion();

			mpDireccion.setTipoEdificacionId(0);
			mpDireccion.setTipoIngresoId(0);
			mpDireccion.setTipoInteriorId(0);
			setCmbvaluedepartamento(Constante.DEPARTAMENTO);
			setCmbvalueprovincia(Constante.PROVINCIA);
			setCmbvaluedistrito(Constante.DISTRITO);
			ubigeo = new Ubigeo();
			ubigeo.setDepartamento(Constante.DEPARTAMENTO);
			ubigeo.setDistrito(Constante.PROVINCIA);
			ubigeo.setProvincia(Constante.DISTRITO);

			List<GnDepartamento> lstGnDepartamento = personaBo
					.getAllGnDepartamento();

			Iterator<GnDepartamento> itd = lstGnDepartamento.iterator();
			lstdepartamento = new ArrayList<SelectItem>();

			while (itd.hasNext()) {
				GnDepartamento obj = itd.next();
				lstdepartamento.add(new SelectItem(obj.getDescripcion(), String
						.valueOf(obj.getDptoId())));
				mapGnDepartamento.put(obj.getDescripcion(), obj.getDptoId());
				mapIGnDepartamento.put(obj.getDptoId(), obj.getDescripcion());
			}

			viewcmbGnDistrito();

			List<GnTipoEdificacion> lstGnTipoEdificacion = personaBo.getAllGnTipoEdificacion();

			Iterator<GnTipoEdificacion> it = lstGnTipoEdificacion.iterator();
			lsttipoedificio = new ArrayList<SelectItem>();

			while (it.hasNext()) {
				GnTipoEdificacion obj = it.next();
				lsttipoedificio.add(new SelectItem(obj.getDescripcion(), String.valueOf(obj.getTipoEdificacionId())));
				mapGnTipoEdificacion.put(obj.getDescripcion(),obj.getTipoEdificacionId());
				mapIGnTipoEdificacion.put(obj.getTipoEdificacionId(),obj.getDescripcion());
			}

			// GnTipoIngreso
			List<GnTipoIngreso> lstGnTipoIngreso = personaBo.getAllGnTipoIngreso();

			Iterator<GnTipoIngreso> it2 = lstGnTipoIngreso.iterator();
			lsttipoingreso = new ArrayList<SelectItem>();

			while (it2.hasNext()) {
				GnTipoIngreso obj = it2.next();
				lsttipoingreso.add(new SelectItem(obj.getDescripcion(), String.valueOf(obj.getTipoIngresoId())));
				mapGnTipoIngreso.put(obj.getDescripcion(),obj.getTipoIngresoId());
				mapIGnTipoIngreso.put(obj.getTipoIngresoId(),obj.getDescripcion());
			}
			// GnTipoInterior
			List<GnTipoInterior> lstGnTipoInterior = personaBo.getAllGnTipoInterior();

			Iterator<GnTipoInterior> it3 = lstGnTipoInterior.iterator();
			lsttipointerior = new ArrayList<SelectItem>();

			while (it3.hasNext()) {
				GnTipoInterior obj = it3.next();
				lsttipointerior.add(new SelectItem(obj.getDescripcion(), String.valueOf(obj.getTipoInteriorId())));
				mapGnTipoInterior.put(obj.getDescripcion(),obj.getTipoInteriorId());
				mapIGnTipoInterior.put(obj.getTipoInteriorId(),obj.getDescripcion());
			}

			// GnTipoVia
			List<GnTipoVia> lstGnTipoVia = personaBo.getAllGnTipoVia();
			Iterator<GnTipoVia> ittv = lstGnTipoVia.iterator();
			while (ittv.hasNext()) {
				GnTipoVia obj = ittv.next();
				getLsttipovia().add(new SelectItem(obj.getDescripcion(), String.valueOf(obj.getTipoViaId())));
				mapIGnTipoVia.put(obj.getTipoViaId(), obj.getDescripcion());
				mapGnTipoVia.put(obj.getDescripcion(), obj.getTipoViaId());
			}


		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void viewcmbGnDistrito() {

		try {
			// GnDistrito
			List<GnDistrito> lstGnDistrito = personaBo.getAllGnDistrito(
					departamentoId, provinciaId);
			Iterator<GnDistrito> it = lstGnDistrito.iterator();
			lstdistrito = new ArrayList<SelectItem>();

			while (it.hasNext()) {
				GnDistrito obj = it.next();
				lstdistrito.add(new SelectItem(obj.getDescripcion(), String.valueOf(obj.getId().getDistritoId())));
				mapGnDistrito.put(obj.getDescripcion().trim(), obj.getId().getDistritoId());
				mapIGnDistrito.put(obj.getId().getProvinciaId(), obj.getDescripcion().trim());
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	private RaDireccionAlcabala raDireccionAlcabala = new RaDireccionAlcabala();

	public RaDireccionAlcabala getRaDireccionAlcabala() {
		return raDireccionAlcabala;
	}

	public void setRaDireccionAlcabala(RaDireccionAlcabala raDireccionAlcabala) {
		this.raDireccionAlcabala = raDireccionAlcabala;
	}


	Integer tipoEdificacion;

	public void loadTipoEdificacion(ValueChangeEvent event) {
		try {
			String value = (String) event.getNewValue();

			if ((value != null) && (!value.equalsIgnoreCase("--"))) {
				// Integer
				tipoEdificacion = (Integer) mapGnTipoEdificacion.get(value);
				setSelEdificio(Boolean.TRUE);
			} else {
				setSelEdificio(Boolean.FALSE);
			}

		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	private Boolean selEdificio = Boolean.FALSE;

	Integer tipoInterior;

	private boolean selTipoInte = Boolean.FALSE;

	public void loadtipoInterior(ValueChangeEvent event) {
		try {
			String value = (String) event.getNewValue();

			if (value != null) {
				// Integer
				tipoInterior = (Integer) mapGnTipoInterior.get(value);
				setSelTipoInte(true);

			} else {
				setSelTipoInte(false);
			}

		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	Integer tipoIngreso;

	private boolean selTipoIngre = Boolean.FALSE;

	public boolean isSelTipoIngre() {
		return selTipoIngre;
	}

	public void setSelTipoIngre(boolean selTipoIngre) {
		this.selTipoIngre = selTipoIngre;
	}

	public void loadTipoIngreso(ValueChangeEvent event) {
		try {
			String value = (String) event.getNewValue();

			if (value != null) {
				tipoIngreso = (Integer) mapGnTipoIngreso.get(value);
				setSelTipoIngre(true);
			} else {
				setSelTipoIngre(false);
			}

		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}

	}


	public void guardarDireccion() {
		
			
		CalculoAlcabalaManaged calculo = (CalculoAlcabalaManaged) getManaged("calculoAlcabalaManaged");
		try {
			int djAlcabalaId = Integer.parseInt(calculo.getDjAlcabala());
			raDireccionAlcabala.setDjalcabalaId(djAlcabalaId);
			raDireccionAlcabala.setDptoId(Constante.DEPARTAMENTO_ID_DEFECTO);
			raDireccionAlcabala.setProvinciaId(Constante.PROVINCIA_ID_DEFECTO);
			raDireccionAlcabala.setDistritoId(idDIstrito);
			if (tipoVia != null) {
				raDireccionAlcabala.setTipoViaId(tipoVia);
			}
			raDireccionAlcabala.setDescVia(nombreVia);
			raDireccionAlcabala.setNumero(numero);
			raDireccionAlcabala.setLetra(letra);
			raDireccionAlcabala.setNumero2(numero2);
			raDireccionAlcabala.setLetra2(letra2);

			if (tipoEdificacion != null) {
				raDireccionAlcabala.setTipoEdificacionId(tipoEdificacion);
			}

			raDireccionAlcabala.setNombreEdificacion(nombreEdificacion);
			raDireccionAlcabala.setPiso(piso);

			if (tipoInterior != null) {
				raDireccionAlcabala.setTipoInteriorId(tipoInterior);

			}

			raDireccionAlcabala.setNumeroInterior(numeroInterior);
			raDireccionAlcabala.setLetraInterior(letraInterior);

			if (tipoIngreso != null) {
				raDireccionAlcabala.setTipoIngresoId(tipoIngreso);
			}

			raDireccionAlcabala.setNombreIngreso(numeroIngreso);
			raDireccionAlcabala.setManzana(manzana);
			raDireccionAlcabala.setLote(lote);

			// direccion completa
			StringBuffer cadena = new StringBuffer();
			cadena.append(((valorTipoVia != null && valorTipoVia.length() > 0) ? " " + valorTipoVia : " ") + " "
					+ ((nombreVia != null && nombreVia.length() > 0) ? " " + nombreVia : " ") + " "
					+ ((numero != null && numero.length() > 0) ? "numero: " + numero : " ") + " "
					+ ((letra != null && letra.length() > 0) ? "letra: " + letra : "") + " "
					+ ((numero2 != null && numero2.length() > 0) ? "- " + numero2 : "") + " "
					+ ((letra2 != null && letra2.length() > 0) ? "- " + letra2 : ""));
			cadena.append(((valorTipoEdificacion != null && valorTipoEdificacion.length() > 0) ? ", "
					+ valorTipoEdificacion : "")
					+ " "
					+ ((nombreEdificacion != null && nombreEdificacion.length() > 0) ? ", " + nombreEdificacion : "")
					+ ((piso != null && piso.length() > 0) ? "piso: " + piso : ""));
			cadena.append(((cmbvaluetipointerior != null && cmbvaluetipointerior.length() > 0) ? ", "
					+ cmbvaluetipointerior : "")
					+ " "
					+ ((numeroInterior != null && numeroInterior.length() > 0) ? ", " + numeroInterior : "")
					+ ((letraInterior != null && letraInterior.length() > 0) ? ", " + letraInterior : ""));
			cadena.append(((cmbvaluetipoingreso != null && cmbvaluetipoingreso.length() > 0) ? ", "
					+ cmbvaluetipoingreso : "")
					+ " "
					+ ((numeroIngreso != null && numeroIngreso.length() > 0) ? ", " + numeroIngreso : "")
					+ " "
					+ ((manzana != null && manzana.length() > 0) ? ",Manzana: " + manzana : "")
					+ " "
					+ ((lote != null && lote.length() > 0) ? ", Lote: " + lote : "")
					+ ". "
					+ ((referencia != null && referencia.length() > 0) ? ". " + referencia : "")
					+ " - "
					+ valorDistrito);
			setDireccionCompleta(cadena.toString());
			raDireccionAlcabala.setDireccionCompleta(direccionCompleta);
			raDireccionAlcabala.setReferencia(referencia);
			raDireccionAlcabala.setEstadoId(Constante.ESTADO_ACTIVO);

			raDireccionAlcabala.setUsuarioId(getSessionManaged().getUsuarioLogIn().getUsuarioId());

			raDireccionAlcabala.setFechaRegistro(DateUtil.getCurrentDate());
			raDireccionAlcabala.setTerminal(getSessionManaged().getTerminalLogIn());

			calculo.setDireccionPredio(direccionCompleta);
		} catch (SisatException e) {			
			WebMessages.messageFatal(e);
		}

	}

	public List<SelectItem> getLsttipovia() {
		return lsttipovia;
	}

	public void setLsttipovia(List<SelectItem> lsttipovia) {
		this.lsttipovia = lsttipovia;
	}

	public String getCmbvaluedepartamento() {
		return cmbvaluedepartamento;
	}

	public void setCmbvaluedepartamento(String cmbvaluedepartamento) {
		this.cmbvaluedepartamento = cmbvaluedepartamento;
	}

	public String getCmbvalueprovincia() {
		return cmbvalueprovincia;
	}

	public void setCmbvalueprovincia(String cmbvalueprovincia) {
		this.cmbvalueprovincia = cmbvalueprovincia;
	}

	public HtmlComboBox getCmbDistrito() {
		return cmbDistrito;
	}

	public void setCmbDistrito(HtmlComboBox cmbDistrito) {
		this.cmbDistrito = cmbDistrito;
	}

	public String getCmbvaluedistrito() {
		return cmbvaluedistrito;
	}

	public void setCmbvaluedistrito(String cmbvaluedistrito) {
		this.cmbvaluedistrito = cmbvaluedistrito;
	}

	public HtmlComboBox getCmbtipoedificio() {
		return cmbtipoedificio;
	}

	public void setCmbtipoedificio(HtmlComboBox cmbtipoedificio) {
		this.cmbtipoedificio = cmbtipoedificio;
	}

	public String getCmbvaluetipoedificio() {
		return cmbvaluetipoedificio;
	}

	public void setCmbvaluetipoedificio(String cmbvaluetipoedificio) {
		this.cmbvaluetipoedificio = cmbvaluetipoedificio;
	}

	public HtmlComboBox getCmbtipointerior() {
		return cmbtipointerior;
	}

	public void setCmbtipointerior(HtmlComboBox cmbtipointerior) {
		this.cmbtipointerior = cmbtipointerior;
	}

	public String getCmbvaluetipointerior() {
		return cmbvaluetipointerior;
	}

	public void setCmbvaluetipointerior(String cmbvaluetipointerior) {
		this.cmbvaluetipointerior = cmbvaluetipointerior;
	}

	public HtmlComboBox getCmbtipoingreso() {
		return cmbtipoingreso;
	}

	public void setCmbtipoingreso(HtmlComboBox cmbtipoingreso) {
		this.cmbtipoingreso = cmbtipoingreso;
	}

	public String getCmbvaluetipoingreso() {
		return cmbvaluetipoingreso;
	}

	public void setCmbvaluetipoingreso(String cmbvaluetipoingreso) {
		this.cmbvaluetipoingreso = cmbvaluetipoingreso;
	}

	public HtmlComboBox getCmbTipoVia() {
		return cmbTipoVia;
	}

	public void setCmbTipoVia(HtmlComboBox cmbTipoVia) {
		this.cmbTipoVia = cmbTipoVia;
	}

	public Boolean getSelEdificio() {
		return selEdificio;
	}

	public void setSelEdificio(Boolean selEdificio) {
		this.selEdificio = selEdificio;
	}

	public Boolean getSelVia() {
		return selVia;
	}

	public void setSelVia(Boolean selVia) {
		this.selVia = selVia;
	}

	public boolean isSelTipoInte() {
		return selTipoInte;
	}

	public void setSelTipoInte(boolean selTipoInte) {
		this.selTipoInte = selTipoInte;
	}

	public String getValorDistrito() {
		return valorDistrito;
	}

	public void setValorDistrito(String valorDistrito) {
		this.valorDistrito = valorDistrito;
	}

	public String getValorTipoVia() {
		return valorTipoVia;
	}

	public void setValorTipoVia(String valorTipoVia) {
		this.valorTipoVia = valorTipoVia;
	}


	public String getValorTipoEdificacion() {
		return valorTipoEdificacion;
	}

	public void setValorTipoEdificacion(String valorTipoEdificacion) {
		this.valorTipoEdificacion = valorTipoEdificacion;
	}

}
