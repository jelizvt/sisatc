package com.sat.sisat.caja.managed;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import com.sat.sisat.caja.business.CajaBoRemote;
import com.sat.sisat.caja.dto.CjPersona;
import com.sat.sisat.caja.dto.DeudaDTO;
import com.sat.sisat.cobranzacoactiva.dto.ResumenDeudasCobranzaCoactivaDTO;
import com.sat.sisat.common.dto.GenericDTO;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.exception.SisatException;

@ManagedBean
@ViewScoped
public class CuentaCorrienteManaged extends BaseManaged implements Serializable {

	private static final long serialVersionUID = -1953592327390761744L;

	@EJB
	private CajaBoRemote cajaBo;

	private CjPersona cjPersona;
	private int cajeroId;
	private int personaId;
	
	private List<DeudaDTO> listCtaCte = new ArrayList<DeudaDTO>();
	private List<GenericDTO> listAnios = new ArrayList<GenericDTO>();
	private List<GenericDTO> listConceptos = new ArrayList<GenericDTO>();
	private List<GenericDTO> listCuotas = new ArrayList<GenericDTO>();
	private List<GenericDTO> listPlacas = new ArrayList<GenericDTO>();
	private List<GenericDTO> listPredios = new ArrayList<GenericDTO>();
	private List<GenericDTO> listUso = new ArrayList<GenericDTO>();

	private boolean selectedAllYears;
	private boolean selectedAllConc;
	private boolean selectedAllCuotas;
	private boolean selectedAllPlacas;
	private boolean selectedAllPredios;
	private boolean selectedAllUso;
	
	
	private BigDecimal insoluto;
	private BigDecimal derechoEmi;
	private BigDecimal reajuste;
	private BigDecimal interes;
	private BigDecimal subtotal;
	private BigDecimal descuento;
	private BigDecimal total;
	
	private boolean renderedDirec;
	private boolean renderedUso;
	private boolean renderedPlaca;
	private boolean rendereNumPap;
	private int colspan;

	private List<DeudaDTO> listDeudaCobrar = new ArrayList<DeudaDTO>();
	private BigDecimal insolutoCobrar;
	private BigDecimal derechoEmiCobrar;
	private BigDecimal reajusteCobrar;
	private BigDecimal interesCobrar;
	private BigDecimal subtotalCobrar;
	private BigDecimal descuentoCobrar;
	private BigDecimal totalCobrar;
	private BigDecimal totalPosibleCobrar;
	
	private boolean selectedAllDeu = false;
	
	private Set<Integer> idDeudasCarrito;
	
	private List<ResumenDeudasCobranzaCoactivaDTO> lstResumenDeudasCobranzaCoactiva = new ArrayList<ResumenDeudasCobranzaCoactivaDTO>(); 
	
	public CuentaCorrienteManaged(){
		getSessionManaged().setLinkRegresar("/sisat/caja/cajaUbicaPersona.xhtml");
	}
	
//	@PostConstruct
//	private void postConstruct() {
//		this.initialize();
//	}
	
	@PostConstruct
	public void initialize(){
		try {
			cjPersona = (CjPersona) getSessionMap().get("cjPersona");
			if (cjPersona != null) {
				personaId = cjPersona.getPersonaId();
				cajeroId = getSessionManaged().getUsuarioLogIn().getUsuarioId();
				listCtaCte = cajaBo.obtenerDeuda(cajeroId, personaId,"","","","",DateUtil.getCurrentDate());
				calcularTotales();

				// fill select one menu
				listAnios = cajaBo.obtenerAniosDeuda(personaId);
				listConceptos = cajaBo.obtenerSubconceptoDeuda(personaId);
				listCuotas = cajaBo.obtenerCuotas(personaId);
				setListPlacas(cajaBo.obtenerPlacas(personaId));
				listPredios = cajaBo.obtenerPredios(personaId);
				listUso = cajaBo.obtenerUso(personaId);
			}
			
			listDeudaCobrar = new ArrayList<DeudaDTO>();
			insolutoCobrar = new BigDecimal("0");
			derechoEmiCobrar = new BigDecimal("0");
			reajusteCobrar = new BigDecimal("0");
			interesCobrar = new BigDecimal("0");
			subtotalCobrar = new BigDecimal("0");
			descuentoCobrar = new BigDecimal("0");
			totalCobrar = new BigDecimal("0");
			totalPosibleCobrar = new BigDecimal("0");
			idDeudasCarrito = new HashSet<Integer>();
			
		} catch (SisatException ex) {
			addErrorMessage(ex.getMessage());
		} catch (Exception ex) {
			String msg = "No se ha podido cargar la cuenta corriente";
			System.out.println(msg + ex);
			addErrorMessage(msg);
		}
	}
	
	private CajaCobranzaManaged getCajaCobranzaManaged(){
		return (CajaCobranzaManaged) getManaged("cajaCobranzaManaged");
	}

	public void changeSelectAnios(ValueChangeEvent ev) {
		try{
			String newValue = ev.getNewValue().toString();
			if (newValue.equals("true")) {
				for (GenericDTO ani : listAnios) {
					ani.setSelected(true);
				}
			} else {
				for (GenericDTO ani : listAnios) {
					ani.setSelected(false);
				}
			}
			listCtaCte = cajaBo.obtenerDeudaFiltrada(cajeroId, personaId, "", getStrCoSubco()[0], getStrCoSubco()[1], getStrCuotas(),this.getStrPlacas(),this.getStrPredios(), this.getStrUso(),  DateUtil.getCurrentDate());
			calcularTotales();
		}catch(SisatException ex){
			addErrorMessage(ex.getMessage());
		}catch(Exception ex){
			String msg = "No se ha podido cargar la cuenta corriente";
			System.out.println(msg + ex);
			addErrorMessage(msg);
		}
	}

	public void changeSelectConc(ValueChangeEvent ev) {
		try{
			String newValue = ev.getNewValue().toString();
			if (newValue.equals("true")) {
				for (GenericDTO sc : listConceptos) {
					sc.setSelected(true);
				}
			} else {
				for (GenericDTO sc : listConceptos) {
					sc.setSelected(false);
				}
			}
			listCtaCte = cajaBo.obtenerDeudaFiltrada(cajeroId, personaId, getStrAnios(), "", "", getStrCuotas(),this.getStrPlacas(), this.getStrPredios(), this.getStrUso(), DateUtil.getCurrentDate());
			calcularTotales();
		}catch(SisatException ex){
			addErrorMessage(ex.getMessage());
		}catch(Exception ex){
			String msg = "No se ha podido cargar la cuenta corriente";
			System.out.println(msg + ex);
			addErrorMessage(msg);
		}
	}
	
	public void changeSelectCuotas(ValueChangeEvent ev) {
		try{
			String newValue = ev.getNewValue().toString();
			if (newValue.equals("true")) {
				for (GenericDTO sc : listCuotas) {
					sc.setSelected(true);
				}
			} else {
				for (GenericDTO sc : listCuotas) {
					sc.setSelected(false);
				}
			}
			listCtaCte = cajaBo.obtenerDeudaFiltrada(cajeroId, personaId, getStrAnios(), getStrCoSubco()[0], getStrCoSubco()[1], "",this.getStrPlacas(), this.getStrPredios(), this.getStrUso(), DateUtil.getCurrentDate());
			calcularTotales();
		}catch(SisatException ex){
			addErrorMessage(ex.getMessage());
		}catch(Exception ex){
			String msg = "No se ha podido cargar la cuenta corriente";
			System.out.println(msg + ex);
			addErrorMessage(msg);
		}
	}
	
	
	public void changeSelectPlacas(ValueChangeEvent ev) {
		try{
			String newValue = ev.getNewValue().toString();
			if (newValue.equals("true")) {
				for (GenericDTO sc : listPlacas) {
					sc.setSelected(true);
				}
			} else {
				for (GenericDTO sc : listPlacas) {
					sc.setSelected(false);
				}
			}
			listCtaCte = cajaBo.obtenerDeudaFiltrada(cajeroId, personaId, getStrAnios(), getStrCoSubco()[0], getStrCoSubco()[1], "",this.getStrPlacas(), this.getStrPredios(), this.getStrUso(), DateUtil.getCurrentDate());
			calcularTotales();
		}catch(SisatException ex){
			addErrorMessage(ex.getMessage());
		}catch(Exception ex){
			String msg = "No se ha podido cargar las Placas";
			System.out.println(msg + ex);
			addErrorMessage(msg);
		}
	}
	
	public void changeSelectUso(ValueChangeEvent ev) {
		try{
			String newValue = ev.getNewValue().toString();
			if (newValue.equals("true")) {
				for (GenericDTO sc : listUso) {
					sc.setSelected(true);
				}
			} else {
				for (GenericDTO sc : listUso) {
					sc.setSelected(false);
				}
			}
			listCtaCte = cajaBo.obtenerDeudaFiltrada(cajeroId, personaId, getStrAnios(), getStrCoSubco()[0], getStrCoSubco()[1], "",this.getStrPlacas(), this.getStrPredios(), this.getStrUso(), DateUtil.getCurrentDate());
			calcularTotales();
		}catch(SisatException ex){
			addErrorMessage(ex.getMessage());
		}catch(Exception ex){
			String msg = "No se ha podido cargar las Placas";
			System.out.println(msg + ex);
			addErrorMessage(msg);
		}
	}
	
	public void changeSelectPredios(ValueChangeEvent ev) {
		try{
			String newValue = ev.getNewValue().toString();
			if (newValue.equals("true")) {
				for (GenericDTO sc : listPredios) {
					sc.setSelected(true);
				}
			} else {
				for (GenericDTO sc : listPredios) {
					sc.setSelected(false);
				}
			}
			//Agregar el parametro para los predios Id
			listCtaCte = cajaBo.obtenerDeudaFiltrada(cajeroId, personaId, getStrAnios(), getStrCoSubco()[0], getStrCoSubco()[1], "",this.getStrPlacas(), this.getStrPredios(), this.getStrUso(), DateUtil.getCurrentDate());
			calcularTotales();
		}catch(SisatException ex){
			addErrorMessage(ex.getMessage());
		}catch(Exception ex){
			String msg = "No se ha podido cargar las Placas";
			System.out.println(msg + ex);
			addErrorMessage(msg);
		}
	}
	
	public void changeSelectItemAnio(ValueChangeEvent ev){
		try{
			HtmlSelectBooleanCheckbox obj = (HtmlSelectBooleanCheckbox)ev.getComponent();
			int label = Integer.parseInt(obj.getLabel());
			boolean value = Boolean.valueOf(obj.getValue().toString());
			selectedAllYears = true;
			for(GenericDTO an : listAnios){
				if(an.getId() == label){
					an.setSelected(value);
				}
				if(!an.isSelected()){
					selectedAllYears = false;
				}
			}
			listCtaCte = cajaBo.obtenerDeudaFiltrada(cajeroId, personaId, getStrAnios(), getStrCoSubco()[0], getStrCoSubco()[1], getStrCuotas(), this.getStrPlacas(), this.getStrPredios(), this.getStrUso(), DateUtil.getCurrentDate());
			calcularTotales();
		}catch(SisatException ex){
			addErrorMessage(ex.getMessage());
		}catch(Exception ex){
			String msg = "No se ha podido cargar la cuenta corriente";
			System.out.println(msg + ex);
			addErrorMessage(msg);
		}
	}
	
	public void changeSelectItemConc(ValueChangeEvent ev){
		try{
			HtmlSelectBooleanCheckbox obj = (HtmlSelectBooleanCheckbox)ev.getComponent();
			int label = Integer.parseInt(obj.getLabel());
			boolean value = Boolean.valueOf(obj.getValue().toString());
			selectedAllConc = true;
			for(GenericDTO cd : listConceptos){
				if(cd.getId() == label){
					cd.setSelected(value);
				}
				if(!cd.isSelected()){
					selectedAllConc = false;
				}
			}
			String[] itemsConceptoSubConcepto = getStrCoSubco();
			listCtaCte = cajaBo.obtenerDeudaFiltrada(cajeroId, personaId, getStrAnios(), itemsConceptoSubConcepto[0], itemsConceptoSubConcepto[1], getStrCuotas(),this.getStrPlacas(), this.getStrPredios(), this.getStrUso(), DateUtil.getCurrentDate());
			calcularTotales();
		}catch(SisatException ex){
			addErrorMessage(ex.getMessage());
		}catch(Exception ex){
			String msg = "No se ha podido cargar la cuenta corriente";
			System.out.println(msg + ex);
			addErrorMessage(msg);
		}
	}
	
	//inicio placa
	public void changeSelectItemPlaca(ValueChangeEvent ev){
		try{
			HtmlSelectBooleanCheckbox obj = (HtmlSelectBooleanCheckbox)ev.getComponent();
			String label = obj.getLabel().toString();
			boolean value = Boolean.valueOf(obj.getValue().toString());
			selectedAllPlacas = true;
			for(GenericDTO cd : listPlacas){
				if(cd.getDescripcion().equals(label)){
					cd.setSelected(value);
				}
				if(!cd.isSelected()){
					selectedAllPlacas = false;
				}
			}
			String[] itemsConceptoSubConcepto = getStrCoSubco();
			listCtaCte = cajaBo.obtenerDeudaFiltrada(cajeroId, personaId, getStrAnios(), itemsConceptoSubConcepto[0], itemsConceptoSubConcepto[1], getStrCuotas(),this.getStrPlacas(), this.getStrPredios(), this.getStrUso(), DateUtil.getCurrentDate());
			calcularTotales();
		}catch(SisatException ex){
			addErrorMessage(ex.getMessage());
		}catch(Exception ex){
			String msg = "No se ha podido cargar las Placas";
			System.out.println(msg + ex);
			addErrorMessage(msg);
		}
	}
	//fin placa
	
	public void changeSelectItemPredio(ValueChangeEvent ev){
		try{
			HtmlSelectBooleanCheckbox obj = (HtmlSelectBooleanCheckbox)ev.getComponent();
			String label = obj.getLabel().toString();
			boolean value = Boolean.valueOf(obj.getValue().toString());
			selectedAllPlacas = true;
			for(GenericDTO cd : listPredios){
				if(cd.getDescripcion().equals(label)){
					cd.setSelected(value);
				}
				if(!cd.isSelected()){
					selectedAllPlacas = false;
				}
			}
			String[] itemsConceptoSubConcepto = getStrCoSubco();
			// Agregar el parametro para predios
			listCtaCte = cajaBo.obtenerDeudaFiltrada(cajeroId, personaId, getStrAnios(), itemsConceptoSubConcepto[0], itemsConceptoSubConcepto[1], getStrCuotas(),this.getStrPlacas(), this.getStrPredios(), this.getStrUso(), DateUtil.getCurrentDate());
			calcularTotales();
		}catch(SisatException ex){
			addErrorMessage(ex.getMessage());
		}catch(Exception ex){
			String msg = "No se ha podido cargar las Placas";
			System.out.println(msg + ex);
			addErrorMessage(msg);
		}
	}
	
	//inicio placa
		public void changeSelectItemUso(ValueChangeEvent ev){
			try{
				HtmlSelectBooleanCheckbox obj = (HtmlSelectBooleanCheckbox)ev.getComponent();
				String label = obj.getLabel().toString();
				boolean value = Boolean.valueOf(obj.getValue().toString());
				selectedAllUso = true;
				for(GenericDTO cd : listUso){
					if(cd.getDescripcion().equals(label)){
						cd.setSelected(value);
					}
					if(!cd.isSelected()){
						selectedAllUso = false;
					}
				}
				String[] itemsConceptoSubConcepto = getStrCoSubco();
				listCtaCte = cajaBo.obtenerDeudaFiltrada(cajeroId, personaId, getStrAnios(), itemsConceptoSubConcepto[0], itemsConceptoSubConcepto[1], getStrCuotas(),this.getStrPlacas(), this.getStrPredios(), this.getStrUso(), DateUtil.getCurrentDate());
				calcularTotales();
			}catch(SisatException ex){
				addErrorMessage(ex.getMessage());
			}catch(Exception ex){
				String msg = "No se ha podido cargar las Placas";
				System.out.println(msg + ex);
				addErrorMessage(msg);
			}
		}
		//fin placa
	
	public void changeSelectItemCuota(ValueChangeEvent ev){
		try{
			HtmlSelectBooleanCheckbox obj = (HtmlSelectBooleanCheckbox)ev.getComponent();
			int label = Integer.parseInt(obj.getLabel());
			boolean value = Boolean.valueOf(obj.getValue().toString());
			selectedAllCuotas = true;
			for(GenericDTO cd : listCuotas){
				if(cd.getId() == label){
					cd.setSelected(value);
				}
				if(!cd.isSelected()){
					selectedAllCuotas = false;
				}
			}
			listCtaCte = cajaBo.obtenerDeudaFiltrada(cajeroId, personaId, getStrAnios(), getStrCoSubco()[0], getStrCoSubco()[1], getStrCuotas(),this.getStrPlacas(), this.getStrPredios(), this.getStrUso(), DateUtil.getCurrentDate());
			calcularTotales();
		}catch(SisatException ex){
			addErrorMessage(ex.getMessage());
		}catch(Exception ex){
			String msg = "No se ha podido cargar la cuenta corriente";
			System.out.println(msg + ex);
			addErrorMessage(msg);
		}
	}
	
	private String getStrAnios(){
		try{
			String strAnios = "";
			int noSel = 0;
			if(listAnios != null && listAnios.size() > 0){
				for (GenericDTO an : listAnios) {
					if(!an.isSelected()){
						noSel ++;
						if(strAnios == ""){
							strAnios = String.valueOf(an.getId());
						}else{
							strAnios = strAnios + "," + String.valueOf(an.getId());
						}
					}
				}
			}
			if(listAnios != null && listAnios.size() == noSel){
				strAnios = "";
			}
			return strAnios;
		}catch(Exception ex){
			String msg = "No se ha podido obtener los años filtrados";
			System.out.println(msg + ex);
		}
		return "";
	}
	
	private String[] getStrCoSubco(){
		String descripcion[] = {"",""};  
		try{
			int numSel = 0;
			
			boolean anyCo = false;
			String strAllCo = "";
			String strAnyCo = "";
			boolean anySubco = false;
			String strAllSubco = "";
			String strAnySubco = "";
			
			if(listConceptos != null && listConceptos.size() > 0){
				for (GenericDTO co : listConceptos) {
					if(co.isSelected()){
						numSel ++;
					}
					if(co.getId() < 30 || co.getId() >= 40){	// Concepts
						if(strAllCo.equals("")){
							strAllCo = String.valueOf(co.getId());
						}else{
							strAllCo = strAllCo + "," + String.valueOf(co.getId());
						}
						if(co.isSelected()){
							anyCo = true;
						}else{
							if(strAnyCo.equals("")){
								strAnyCo = String.valueOf(co.getId());
							}else{
								strAnyCo = strAnyCo + "," + String.valueOf(co.getId());
							}
						}
					}else{	// Sub concepts
						if(strAllSubco.equals("")){
							strAllSubco = String.valueOf(co.getId());
						}else{
							strAllSubco = strAllSubco + "," + String.valueOf(co.getId());
						}
						if(co.isSelected()){
							anySubco = true;
						}else{
							if(strAnySubco.equals("")){
								strAnySubco = String.valueOf(co.getId());
							}else{
								strAnySubco = strAnySubco + "," + String.valueOf(co.getId());
							}
						}
					}
				}
			}
			
			String strCo = "";
			String strSubco = "";
			if(listConceptos != null && listConceptos.size() == numSel){
				strCo = "";
				strSubco = "";
			}else{
				if(!anyCo && !anySubco){
					strCo = "";
					strSubco = "";
				}else if(anyCo && !anySubco){
					strCo = strAnyCo;
					strSubco = strAllSubco;
				}else if(!anyCo && anySubco){
					strCo = strAllCo;
					strSubco = strAnySubco;
				}else{
					strCo = strAnyCo;
					strSubco = strAnySubco;
				}
			}
			
			descripcion[0] = strCo;
			descripcion[1] = strSubco;
			
			return descripcion;
		}catch(Exception ex){
			String msg = "No se ha podido obtener los conceptos y subconceptos filtrados";
			System.out.println(msg + ex);
		}
		return descripcion;
	}
	
	private String getStrCuotas(){
		try{
			String strCuotas = "";
			int noSel = 0;
			if(listCuotas != null && listCuotas.size() > 0){
				for (GenericDTO cu : listCuotas) {
					if(!cu.isSelected()){
						noSel ++;
						if(strCuotas == ""){
							strCuotas = String.valueOf(cu.getId());
						}else{
							strCuotas = strCuotas + "," + String.valueOf(cu.getId());
						}
					}
				}
			}
			if(listCuotas != null && listCuotas.size() == noSel){
				strCuotas = "";
			}
			return strCuotas;
		}catch(Exception ex){
			String msg = "No se ha podido obtener las cuotas filtrados";
			System.out.println(msg + ex);
		}
		return "";
	}
	
	//muestra del filtrado de placas
	
	private String getStrPlacas(){
		try{
			String strCuotas = "";
			int noSel = 0;
			if(listPlacas != null && listPlacas.size() > 0){
				for (GenericDTO cu : listPlacas) {
					if(!cu.isSelected()){
						noSel ++;
						if(strCuotas == ""){
							strCuotas = String.valueOf(cu.getDescripcion());
						}else{
							strCuotas = strCuotas + "," + String.valueOf(cu.getDescripcion());
						}
					}
				}
			}
			if(listPlacas != null && listPlacas.size() == noSel){
				strCuotas = "";
			}
			return strCuotas;
		}catch(Exception ex){
			String msg = "No se ha podido obtener las placas filtradas";
			System.out.println(msg + ex);
		}
		return "";
	}
	
	private String getStrPredios(){
		try{
			String strPredios = "";
			int noSel = 0;
			if(listPredios != null && listPredios.size() > 0){
				for (GenericDTO cu : listPredios) {
					if(!cu.isSelected()){
						noSel ++;
						if(strPredios == ""){
							strPredios = String.valueOf(cu.getDescripcion());
						}else{
							strPredios = strPredios + "," + String.valueOf(cu.getDescripcion());
						}
					}
				}
			}
			if(listPredios != null && listPredios.size() == noSel){
				strPredios = "";
			}
			return strPredios;
		}catch(Exception ex){
			String msg = "No se ha podido obtener las placas filtradas";
			System.out.println(msg + ex);
		}
		return "";
	}
	
	private String getStrUso(){
		try{
			String strUso = "";
			int noSel = 0;
			if(listUso != null && listUso.size() > 0){
				for (GenericDTO cu : listUso) {
					if(!cu.isSelected()){
						noSel ++;
						if(strUso == ""){
							strUso = String.valueOf(cu.getId());
						}else{
							strUso = strUso + "," + String.valueOf(cu.getId());
						}
					}
				}
			}
			if(listUso != null && listUso.size() == noSel){
				strUso = "";
			}
			return strUso;
		}catch(Exception ex){
			String msg = "No se ha podido obtener las placas filtradas";
			System.out.println(msg + ex);
		}
		return "";
	}
	
	//fin filtrado placas
	private void calcularTotales(){
		renderedDirec = false;
		renderedUso = false;
		renderedPlaca = false;
		rendereNumPap = false;
		colspan = 4;
		insoluto = new BigDecimal("0");
		derechoEmi = new BigDecimal("0");
		reajuste = new BigDecimal("0");
		interes = new BigDecimal("0");
		subtotal = new BigDecimal("0");
		descuento = new BigDecimal("0");
		total = new BigDecimal("0");
		for(DeudaDTO de : listCtaCte){
			insoluto = insoluto.add(de.getInsoluto());
			derechoEmi = derechoEmi.add(de.getDerechoEmi());
			reajuste = reajuste.add(de.getReajuste());
			interes = interes.add(de.getInteres());
			subtotal = subtotal.add(de.getSubtotal());
			descuento = descuento.add(de.getDescuento());
			total = total.add(de.getTotalDeuda());
			
			if(de.getDireccion() != null && !de.getDireccion().isEmpty() && !renderedDirec){
				renderedDirec = true;
				colspan ++;
			}
			if(de.getUso() != null && !de.getUso().isEmpty() && !renderedUso){
				renderedUso = true;
				colspan ++;
			}
			if(((de.getPlacaPa() != null && !de.getPlacaPa().isEmpty()) || (de.getPlacaVe() != null && !de.getPlacaVe().isEmpty())) && !renderedPlaca){
				renderedPlaca = true;
				colspan ++;
			}
			if(de.getNumPapeleta() != null && !de.getNumPapeleta().isEmpty() && !rendereNumPap){
				rendereNumPap = true;
				colspan ++;
			}
		}
	}
	
	public void changeSelectAllDeu(ValueChangeEvent ev){
		String nv = ev.getNewValue().toString();
		if(nv.equals("true")){
			for(DeudaDTO de : listCtaCte){
				de.setSelected(true);
			}
		}else{
			for(DeudaDTO de : listCtaCte){
				de.setSelected(false);
			}
		}
	}
	
	private void calcularTotalesCabrar(){
		insolutoCobrar = new BigDecimal("0");
		derechoEmiCobrar = new BigDecimal("0");
		reajusteCobrar = new BigDecimal("0");
		interesCobrar = new BigDecimal("0");
		subtotalCobrar = new BigDecimal("0");
		descuentoCobrar = new BigDecimal("0");
		totalCobrar = new BigDecimal("0");
		totalPosibleCobrar = new BigDecimal("0");
		for(DeudaDTO de: listDeudaCobrar){
			insolutoCobrar = insolutoCobrar.add(de.getInsoluto());
			derechoEmiCobrar = derechoEmiCobrar.add(de.getDerechoEmi());
			reajusteCobrar = reajusteCobrar.add(de.getReajuste());
			interesCobrar = interesCobrar.add(de.getInteres());
			subtotalCobrar = subtotalCobrar.add(de.getSubtotal());
			descuentoCobrar = descuentoCobrar.add(de.getDescuento());
			totalCobrar = totalCobrar.add(de.getTotalDeuda());
			if(de.getTotalPosible()!=null){
				totalPosibleCobrar = totalPosibleCobrar.add(de.getTotalPosible());
			}
		}
	}
		
	public String agregar() {

		try {
			// long tiempoInicioT = System.currentTimeMillis();
			// long tiempoInicio = System.currentTimeMillis();

			String deudas = "";
			for (DeudaDTO de : listCtaCte) {
	//			for (DeudaDTO de : listDeudaCobrar){
				if (de.isSelected()) {
					// deudas = deudas + "," + de.getDeudaId();
					idDeudasCarrito.add(de.getDeudaId());
				}
			}

			// System.out.println("El tiempo despues del for 1 es: " + (System.currentTimeMillis() -
			// tiempoInicio));
			// tiempoInicio = System.currentTimeMillis();

			// no entiendo la logica de estas lineas !!!!!!
			// for(DeudaDTO de : listDeudaCobrar){
			// if(de.getConceptoId() == Constante.CONCEPTO_ARBITRIO){
			// deudas = deudas + "," + de.getDeudaId();
			// }
			// }

			// for(DeudaDTO de : listDeudaCobrar){
			// if(de.getDeudaId() != )
			// }

			// deudas = deudas.replaceFirst(",", "");
			deudas = idDeudasCarrito.toString().replace("[", "".trim()).replace("]", "".trim()).replace(" ", "".trim()).trim();
			// System.out.println("El tiempo despues del for 2 es: " + (System.currentTimeMillis() -
			// tiempoInicio));
			// tiempoInicio = System.currentTimeMillis();

			if (!deudas.isEmpty()) {
				
				/** Verificando que las deudas no esten en cobranza coactiva
				 * 
				 * 
				 * 
				 * */
				//pedniente de: INhabilitamos esta opción en coordinación con el ärea de Cobranza. Para evitar bloquear deuda Coactiva al momento de cobrarla.
				//Omar 19-10-2018
				lstResumenDeudasCobranzaCoactiva.clear();
				lstResumenDeudasCobranzaCoactiva = cajaBo.verificarDeudasEnCobranzaCoactiva(deudas);
				
				
				
				
				listDeudaCobrar = new ArrayList<DeudaDTO>();
				listDeudaCobrar = cajaBo.obetenerDeudaConDsctos(deudas,
						Calendar.getInstance().getTime(),
						getSessionManaged().getUsuarioLogIn().getUsuarioId(),2);
				
				for (DeudaDTO de2 : listDeudaCobrar) {
					//			for (DeudaDTO de : listDeudaCobrar){
							//if (de.isSelected()) {
									// deudas = deudas + "," + de.getDeudaId();
									idDeudasCarrito.add(de2.getDeudaId());
							//	}
							}
				
				// System.out.println("El tiempo despues del store es: " +
				// (System.currentTimeMillis() -
				// tiempoInicio));
				// tiempoInicio = System.currentTimeMillis();

				calcularTotalesCabrar();

				// System.out.println("El tiempo despues del metodo: " + (System.currentTimeMillis()
				// -
				// tiempoInicio));
				// tiempoInicio = System.currentTimeMillis();

				selectedAllDeu = false;
				removeFilter();

				// System.out.println("El tiempo despues del remove filter: " +
				// (System.currentTimeMillis() - tiempoInicio));
			}

			// long tiempoFinT = System.currentTimeMillis();
			// System.out.println("tiempo total del metodo "+ ((tiempoFinT-tiempoInicioT)/1000.0));
		} catch (SisatException e) {
			WebMessages.messageFatal(e);
		}
		return null;
	}
	
	private void removeFilter(){
//		for(GenericDTO g : listAnios){
//			g.setSelected(false);
//		}
//		for(GenericDTO g : listConceptos){
//			g.setSelected(false);
//		}
//		for(GenericDTO g : listCuotas){
//			g.setSelected(false);
//		}
		try{
			//listCtaCte = cajaBo.obtenerDeudaFiltrada(cajeroId, personaId, listAnios, getStrCoSubco()[0], getStrCoSubco()[1], getStrCuotas(),DateUtil.getCurrentDate());
			listCtaCte = cajaBo.obtenerDeudaFiltrada(cajeroId, personaId, getStrAnios(), getStrCoSubco()[0], getStrCoSubco()[1], getStrCuotas(), getStrPlacas(), this.getStrPredios(), this.getStrUso(), DateUtil.getCurrentDate());
			this.selectedAllDeu = false;
			calcularTotales();
		}catch(SisatException ex){
			System.out.println(ex);
		}
	}
	
	public void removeRowDeudaCobrar(ActionEvent ev){
		try {
			UIComponent comp = ev.getComponent().getParent().getParent();
			if (comp != null) {
				UIData uiData = (UIData) comp;
				DeudaDTO deSel = (DeudaDTO) uiData.getRowData();
				//String deudas = "";
				
				/** Coleccion donde contendra todos los items para ser borrados, sino se usa de esta forma, lanza una excepcion de ConcurrentModificationException  */
				Collection<Integer> removeCandidatesId = new LinkedList<Integer>();
				Collection<DeudaDTO> removeCandidatesDeudaDTO = new LinkedList<DeudaDTO>();
				
				for(DeudaDTO de : listDeudaCobrar){
//					if(deSel.getDeudaId() != de.getDeudaId()){
//						if(de.getConceptoId() == Constante.CONCEPTO_ARBITRIO){
//							deudas = deudas + "," + de.getDeudaId();
//						}
//					}
					if(de.getDeudaId() == deSel.getDeudaId()){
						removeCandidatesDeudaDTO.add(de);
						removeCandidatesId.add(Integer.valueOf(de.getDeudaId()));
					}
				
				}
				
				idDeudasCarrito.removeAll(removeCandidatesId);
				listDeudaCobrar.removeAll(removeCandidatesDeudaDTO);
				
				String deudas = idDeudasCarrito.toString().replace("[", "".trim()).replace("]", "".trim()).replace(" ", "".trim()).trim();
				//listDeudaCobrar = new ArrayList<DeudaDTO>();
				listDeudaCobrar = cajaBo.obetenerDeudaConDsctos(deudas, Calendar.getInstance().getTime(), getSessionManaged().getUsuarioLogIn().getUsuarioId(),2);
				calcularTotalesCabrar();
			}
		} catch (Exception ex) {
			String msg = "No se ha podido eliminar el registro";
			System.out.println(msg + ex);
		}
	}
	
	public String cancelar(){
		listDeudaCobrar = new ArrayList<DeudaDTO>();
		insolutoCobrar = new BigDecimal("0");
		derechoEmiCobrar = new BigDecimal("0");
		reajusteCobrar = new BigDecimal("0");
		interesCobrar = new BigDecimal("0");
		descuentoCobrar = new BigDecimal("0");
		subtotalCobrar = new BigDecimal("0");
		totalCobrar = new BigDecimal("0");
		idDeudasCarrito = new HashSet<Integer>();
		return null;
	}
	
	public String cobrar(){
		if(listDeudaCobrar != null && listDeudaCobrar.size() == 0){
			addErrorMessage("Debe ingresar las deudas a cobrar");
			return null;
		}
		/*
		boolean save = cajaBo.guardarTmpDeuda(listDeudaCobrar);
		if(save == false){
			addErrorMessage("No se ha podido mantener el temporal de deudas");
			return null;
		}*/
		getCajaCobranzaManaged().iniciarDatosDefault(getSubtotalCobrar(), getTotalCobrar(), getDescuentoCobrar());
		getCajaCobranzaManaged().setIdDeudasACobrar(idDeudasCarrito.toString().replace("[", "".trim()).replace("]", "".trim()).replace(" ", "".trim()).trim());
		return null;
	}

	public List<DeudaDTO> getListCtaCte() {
		return listCtaCte;
	}
	
	public boolean isSelectedAllYears() {
		return selectedAllYears;
	}

	public void setSelectedAllYears(boolean selectedAllYears) {
		this.selectedAllYears = selectedAllYears;
	}

	public boolean isSelectedAllConc() {
		return selectedAllConc;
	}

	public void setSelectedAllConc(boolean selectedAllConc) {
		this.selectedAllConc = selectedAllConc;
	}

	public BigDecimal getInsoluto() {
		return insoluto;
	}

	public BigDecimal getDerechoEmi() {
		return derechoEmi;
	}

	public BigDecimal getReajuste() {
		return reajuste;
	}

	public BigDecimal getInteres() {
		return interes;
	}

	public BigDecimal getDescuento() {
		return descuento;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public boolean isRenderedDirec() {
		return renderedDirec;
	}

	public boolean isRenderedUso() {
		return renderedUso;
	}

	public boolean isRenderedPlaca() {
		return renderedPlaca;
	}

	public boolean isRendereNumPap() {
		return rendereNumPap;
	}

	public int getColspan() {
		return colspan;
	}

	public List<DeudaDTO> getListDeudaCobrar() {
		return listDeudaCobrar;
	}

	public BigDecimal getInsolutoCobrar() {
		return insolutoCobrar;
	}

	public BigDecimal getDerechoEmiCobrar() {
		return derechoEmiCobrar;
	}

	public BigDecimal getReajusteCobrar() {
		return reajusteCobrar;
	}

	public BigDecimal getInteresCobrar() {
		return interesCobrar;
	}

	public BigDecimal getDescuentoCobrar() {
		return descuentoCobrar;
	}

	public BigDecimal getTotalCobrar() {
		return totalCobrar;
	}

	
	public boolean isSelectedAllDeu() {
		return selectedAllDeu;
	}


	public void setSelectedAllDeu(boolean selectedAllDeu) {
		this.selectedAllDeu = selectedAllDeu;
	}

	public boolean isSelectedAllCuotas() {
		return selectedAllCuotas;
	}

	public void setSelectedAllCuotas(boolean selectedAllCuotas) {
		this.selectedAllCuotas = selectedAllCuotas;
	}

	public List<GenericDTO> getListAnios() {
		return listAnios;
	}

	public void setListAnios(List<GenericDTO> listAnios) {
		this.listAnios = listAnios;
	}

	public List<GenericDTO> getListConceptos() {
		return listConceptos;
	}

	public void setListConceptos(List<GenericDTO> listConceptos) {
		this.listConceptos = listConceptos;
	}

	public List<GenericDTO> getListCuotas() {
		return listCuotas;
	}

	public void setListCuotas(List<GenericDTO> listCuotas) {
		this.listCuotas = listCuotas;
	}

	public void setListDeudaCobrar(List<DeudaDTO> listDeudaCobrar) {
		this.listDeudaCobrar = listDeudaCobrar;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public BigDecimal getSubtotalCobrar() {
		return subtotalCobrar;
	}

	public void setSubtotalCobrar(BigDecimal subtotalCobrar) {
		this.subtotalCobrar = subtotalCobrar;
	}

	public BigDecimal getTotalPosibleCobrar() {
		return totalPosibleCobrar;
	}

	public void setTotalPosibleCobrar(BigDecimal totalPosibleCobrar) {
		this.totalPosibleCobrar = totalPosibleCobrar;
	}

	public Set<Integer> getIdDeudasCarrito() {
		return idDeudasCarrito;
	}

	public void setIdDeudasCarrito(Set<Integer> idDeudasCarrito) {
		this.idDeudasCarrito = idDeudasCarrito;
	}

	public boolean isSelectedAllPlacas() {
		return selectedAllPlacas;
	}

	public void setSelectedAllPlacas(boolean selectedAllPlacas) {
		this.selectedAllPlacas = selectedAllPlacas;
	}

	public List<GenericDTO> getListPlacas() {
		return listPlacas;
	}

	public void setListPlacas(List<GenericDTO> listPlacas) {
		this.listPlacas = listPlacas;
	}

	public boolean isSelectedAllPredios() {
		return selectedAllPredios;
	}

	public void setSelectedAllPredios(boolean selectedAllPredios) {
		this.selectedAllPredios = selectedAllPredios;
	}

	public List<GenericDTO> getListPredios() {
		return listPredios;
	}

	public void setListPredios(List<GenericDTO> listPredios) {
		this.listPredios = listPredios;
	}

	public boolean isSelectedAllUso() {
		return selectedAllUso;
	}

	public void setSelectedAllUso(boolean selectedAllUso) {
		this.selectedAllUso = selectedAllUso;
	}

	public List<GenericDTO> getListUso() {
		return listUso;
	}

	public void setListUso(List<GenericDTO> listUso) {
		this.listUso = listUso;
	}

	public List<ResumenDeudasCobranzaCoactivaDTO> getLstResumenDeudasCobranzaCoactiva() {
		return lstResumenDeudasCobranzaCoactiva;
	}

	public void setLstResumenDeudasCobranzaCoactiva(List<ResumenDeudasCobranzaCoactivaDTO> lstResumenDeudasCobranzaCoactiva) {
		this.lstResumenDeudasCobranzaCoactiva = lstResumenDeudasCobranzaCoactiva;
	}

	
}
