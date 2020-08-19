package com.sat.sisat.caja.managed;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
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

import com.sat.sisat.caja.business.CajaBoRemote;
import com.sat.sisat.caja.dto.CjPersona;
import com.sat.sisat.caja.dto.DeudaDTO;
import com.sat.sisat.caja.dto.ResumenConceptosDTO;
import com.sat.sisat.common.business.GeneralBoRemote;
import com.sat.sisat.common.dto.GenericDTO;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.persistence.entity.CjReciboDetalle;
import com.sat.sisat.persistence.entity.GnTipoCambio;
import com.sat.sisat.persistence.entity.GnTipoMoneda;
import com.sat.sisat.vehicular.business.VehicularBoRemote;

@ManagedBean
@ViewScoped
public class CajaCobranzaManaged extends BaseManaged implements Serializable {

	private static final long serialVersionUID = -1877564585156896865L;

	@EJB
	CajaBoRemote cajaBo;
	@EJB
	VehicularBoRemote vehicularBo;
	@EJB
	GeneralBoRemote generalBo;
	
	private String selectedFormasPago;
	
	private List<SelectItem> lstFormaPago = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapCjFormaPago = new HashMap<String, Integer>();
	private HashMap<Integer, String> mapICjFormaPago = new HashMap<Integer, String>();
	private String selectedFormaPago;
	private int selectedFormaPagoId;
	
	private List<SelectItem> lstTipoMoneda = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapGnTipoMoneda = new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIGnTipoMoneda = new HashMap<Integer, String>();
	private String selectedTipoMoneda;
	
	private GnTipoCambio tipoCambio = new GnTipoCambio();
	
	private String labelTarjetaCheque;
	
	private CjPersona contrib = new CjPersona();
	
	private BigDecimal montoSinDescuento;
	private BigDecimal montoACobrar;
	private BigDecimal monto;
	private BigDecimal montoSoles;
	private BigDecimal montoIngresado;
	private BigDecimal montoResta;
	private BigDecimal montoVuelto;
	private BigDecimal montoDescuento;
	private String observacion;
	private String referencia;
	
	// Banco
	private List<SelectItem> lstBanco = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapCjBanco = new HashMap<String, Integer>();
	private String selectedBanco;

	// Tipo tarjeta
	private List<SelectItem> lstTipoTarjeta = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapCjTipoTarjeta = new HashMap<String, Integer>();
	private String selectedTipoTarjeta;
	
	private String numTarCheq;
	private List<CjReciboDetalle> lstPagodetalle;
	
	private boolean correct;
	
	private String idDeudasACobrar;
	
	private String msgDescAplicacionBono;
	private Integer numeroCuotas;
	private Integer numeroCuyes;
	private Integer contadorUsos;
	private ResumenConceptosDTO resumenConcepto;
	
	public CajaCobranzaManaged(){
	}
	
	@PostConstruct
	public void inicialize(){
		try{
			// Fill payment form
			List<GenericDTO> lstTmfp = new ArrayList<GenericDTO>();
			lstTmfp = cajaBo.obtenerFormaPago();
			Iterator<GenericDTO> itfp = lstTmfp.iterator();
			while(itfp.hasNext()){
				GenericDTO ge = itfp.next();
				lstFormaPago.add(new SelectItem(ge.getDescripcion()));
				mapCjFormaPago.put(ge.getDescripcion(), ge.getId());
				mapICjFormaPago.put(ge.getId(), ge.getDescripcion());
			}
			// Fill type money
			List<GnTipoMoneda> lstTm = new ArrayList<GnTipoMoneda>();
			lstTm = vehicularBo.getAllGnTipoMoneda();
			Iterator<GnTipoMoneda> itTm = lstTm.iterator();
			while (itTm.hasNext()) {
				GnTipoMoneda objTm = itTm.next();
				lstTipoMoneda.add(new SelectItem(objTm.getDescripcion()));
				mapGnTipoMoneda.put(objTm.getDescripcion(),objTm.getTipoMonedaId());
				mapIGnTipoMoneda.put(objTm.getTipoMonedaId(),objTm.getDescripcion());
			}
			// Fill bank
			List<GenericDTO> lstTB = new ArrayList<GenericDTO>();
			lstTB = cajaBo.obtenerTipoBanco();
			Iterator<GenericDTO> itCj3 = lstTB.iterator();
			while (itCj3.hasNext()) {
				GenericDTO objAd = itCj3.next();
				lstBanco.add(new SelectItem(objAd.getDescripcion()));
				mapCjBanco.put(objAd.getDescripcion(), objAd.getId());
			}
			// Fill card type
			List<GenericDTO> lstTT = new ArrayList<GenericDTO>();
			lstTT = cajaBo.obtenerTipoTarjeta();
			Iterator<GenericDTO> itCj4 = lstTT.iterator();
			while (itCj4.hasNext()) {
				GenericDTO objAd = itCj4.next();
				lstTipoTarjeta.add(new SelectItem(objAd.getDescripcion()));
				mapCjTipoTarjeta.put(objAd.getDescripcion(),objAd.getId());
			}
			iniciarDatosDefault(new BigDecimal(0),new BigDecimal(0), new BigDecimal(0));
		}catch(Exception ex){
			String msg = "No se ha podido cargar los datos iniciales";
			System.out.println(msg + ex);
		}
	}
	
	private CuentaCorrienteManaged getCuentaCorrienteManaged(){
		return (CuentaCorrienteManaged) getManaged("cuentaCorrienteManaged");
	}
	
	/**
	 * 
	 * @param montoSinDescuento
	 * @param montoCobrar
	 * @param montoDescuento
	 */
	public void iniciarDatosDefault(BigDecimal montoSinDescuento, BigDecimal montoCobrar, BigDecimal montoDescuento){
		try {
			selectedFormasPago = Constante.FORMAS_PAGO_EFECTIVO;

			contrib = (CjPersona) getSessionMap().get("cjPersona");
			if (contrib != null) {
				int cajeroId = getSessionManaged().getUsuarioLogIn().getUsuarioId();
				contrib = cajaBo.ObtenerDatosPersona(contrib.getPersonaId(), cajeroId);
				contrib.setMontoCobrar(montoCobrar);
				this.setMontoACobrar(montoCobrar);
				this.setMontoSinDescuento(montoSinDescuento);
				this.setMontoDescuento(montoDescuento);
				if (contrib != null) {
					contrib.setCajeroId(cajeroId);
				} else {
					contrib = new CjPersona();
				}
			} else {
				contrib = new CjPersona();
			}

			iniciarFormasPago();
		} catch (SisatException e) {
			WebMessages.messageFatal(e);
		}
	}
	
	public void changeFormasPago(ValueChangeEvent ev){
		iniciarFormasPago();
	}
	
	public void changeFormaPago(ValueChangeEvent ev) {
		iniciarFormaPago();
		selectedFormaPagoId = mapCjFormaPago.get(ev.getNewValue());
		List<DeudaDTO> listUso = getCuentaCorrienteManaged()
				.getListDeudaCobrar();
		setNumeroCuyes(null);
		setNumeroCuotas(null);
		if (selectedFormaPagoId == 5) {
			int cuentaOtrosUsos = 0;
			contadorUsos = 0;
			for (DeudaDTO dt : listUso) {
				if (!String.valueOf(dt.getUso()).equals("Vivienda")) {
					cuentaOtrosUsos++;
					break;
				} else {
					contadorUsos++;
				}
			}
			if (cuentaOtrosUsos == 0) {
				// TODO : Validamos que los conceptos de pago tengan cumplan las
				// siguientes condiciones:
				// this.montoSoles = contrib.getMontoCobrar().multiply(new
				// BigDecimal("0.3"));
				validaAplicacionBono();
			} else {
				addErrorMessage("Seleccione solo uso Casa Habitación");
			}
		}
	}
	
	public void validaAplicacionBono(){
		// para periodos desde el 2009 al 2012  :: 1 periodo AND 1 Predio AND 1 SubConcepto LIMPIEZA => descuenta 15% por cuota 
		// para el periodo 2013 				:: 1 periodo AND 1 Predio AND 1 SubConcepto RECOJO => descuenta 30% por cuota
		if(cajaBo.esBenefBonoCajam(contrib.getPersonaId(),DateUtil.getAnioActual())>0){
			List<ResumenConceptosDTO> lConcepto=cajaBo.getResumenConcepto(idDeudasACobrar,contrib.getPersonaId(),DateUtil.getAnioActual());
			setResumenConcepto(new ResumenConceptosDTO());
			if(lConcepto.size()==1){
				//evaluar si cumple con la condicion				
				/** 
				 * 2009 LP
				 * 2010 LP
				 * 2011 LP
				 * 2012 LP ó Re
				 * 2013 Re
				 * */				
				ResumenConceptosDTO concepto=lConcepto.get(0);
				if((concepto.getSubConceptoId()==Constante.SUB_CONCEPTO_ARBITRIOS_LIMPIEZA_PUBLICA && 2009 <= concepto.getAnnoDeuda() && 2012 >= concepto.getAnnoDeuda() ) ||
				   (concepto.getSubConceptoId() == Constante.SUB_CONCEPTO_ARBITRIOS_RECOJO && 2012 <= concepto.getAnnoDeuda()) )
				{
					setMsgDescAplicacionBono(concepto.getCuotas()+" cuotas "+(concepto.getPredioId()>0? "del predio "+concepto.getPredioId() +" ":"")+" - periodo "+concepto.getAnnoDeuda());
					setResumenConcepto(concepto);
				}else{
					setMsgDescAplicacionBono(null);
					addErrorMessage("No es aplicable el Bono Cajamarquino");
					addErrorMessage(concepto.getCuotas()+" cuotas "+(concepto.getPredioId()>0?" del predio "+concepto.getPredioId():"")+" - periodo "+concepto.getAnnoDeuda()+" - Sub Concepto "+concepto.getSubConceptoId());
					addErrorMessage("Por favor verifique que la deuda a cobrar sea del concepto Recojo o Limpieza de un solo periodo");
					iniciarFormasPago();
				}
			}else{
				setMsgDescAplicacionBono(null);
				addErrorMessage("No es aplicable el Bono Cajamarquino");
				Iterator<ResumenConceptosDTO> it=lConcepto.iterator();
				while(it.hasNext()){
					ResumenConceptosDTO concepto=it.next();
					addErrorMessage(concepto.getCuotas()+" cuotas "+(concepto.getPredioId()>0?" del predio "+concepto.getPredioId():"")+" - periodo "+concepto.getAnnoDeuda()+" - Sub Concepto "+concepto.getSubConceptoId());
				}
				addErrorMessage("Porfavor verifique que la deuda a cobrar sea del concepto Recojo o Limpieza de un solo periodo");
				iniciarFormasPago();
			}	
		}else{
			addErrorMessage("El Contribuyente no es beneficiario del Bono Cajamarquino");
			iniciarFormasPago();
		}
	}
	
	public void changeTipoMoneda(ValueChangeEvent ev){
		selectedTipoMoneda = String.valueOf(ev.getNewValue());
		iniciarTipoMoneda();
		if(!selectedTipoMoneda.equals(Constante.TIPO_MONEDA_SOLES)){
			int tipoMonedaId = mapGnTipoMoneda.get(selectedTipoMoneda);
			tipoCambio = generalBo.obtenerTipoCambioDia(tipoMonedaId);
			if (tipoCambio == null) {
				tipoCambio = new GnTipoCambio();
				selectedTipoMoneda = Constante.TIPO_MONEDA_SOLES;
				addErrorMessage(getMsg("gn.tipocambionodef"));
			}
		}
	}
	
	public String agregarPago(){
		try{
			if(validaFormaPago()){
				if(validarNulos() && validarDatos()){
					CjReciboDetalle rd = new CjReciboDetalle();
					Integer banId = mapCjBanco.get(selectedBanco);
					Integer tipTarId = mapCjTipoTarjeta.get(selectedTipoTarjeta);
					int tipoMon = mapGnTipoMoneda.get(selectedTipoMoneda);
					rd.setBancoId(banId);
					rd.setFormaPagoId(selectedFormaPagoId);
					rd.setTipoCambio(tipoCambio==null ? null : tipoCambio.getValorVenta());
					rd.setMonto(monto == null || monto.doubleValue()==0 ? null : monto);
					rd.setMontoTotalSoles(montoSoles.setScale(2,RoundingMode.HALF_UP));
					rd.setNroCheque(numTarCheq == null || numTarCheq.isEmpty() ? null : numTarCheq);
					rd.setTarjetaId(tipTarId);
					rd.setUsuarioId(getSessionManaged().getUsuarioLogIn().getUsuarioId());
					rd.setObservacion(observacion);
					rd.setEstado("1");
					rd.setTerminal(getSessionManaged().getTerminalLogIn());
					rd.setTipoMonedaId(tipoMon);
					rd.setBanco(selectedBanco);
					rd.setFormaPago(selectedFormaPago);
					rd.setTipoTarjeta(selectedTipoTarjeta);
					rd.setTipoMoneda(selectedTipoMoneda);
					lstPagodetalle.add(rd);
					this.montoIngresado = this.montoIngresado.add(montoSoles).setScale(2,RoundingMode.HALF_UP);
					BigDecimal dif = montoIngresado.subtract(contrib.getMontoCobrar());
					if(dif.doubleValue() >= 0){
						montoVuelto = dif;
						montoResta = null;
					}else{
						montoResta = dif.multiply(new BigDecimal("-1"));
						montoVuelto = null;
					}
					correct = true;
					
					selectedFormaPagoId = Constante.FORMA_PAGO_EFECTIVO;
					selectedFormaPago = mapICjFormaPago.get(selectedFormaPagoId);
					numeroCuotas=0;
					montoSoles = new BigDecimal("0");
				}else{
					correct = false;
				}
			}else{
				correct = false;
			}
		}catch(Exception ex){
			String msg = "Ha ocurrido un error interno";
			addErrorMessage(msg + ex);
			correct = false;	
		}
		return null;
	}
	
	public boolean validaFormaPago(){
		if(selectedFormaPagoId==Constante.FORMA_PAGO_BONO_CAJAM&&
				existeFormaPagoBonoCajam()){
			addErrorMessage("Solo puede seleccionar una forma de pago con Bono Cajamarquino");
			return false;
		}
		return true;
	}
	public boolean existeFormaPagoBonoCajam(){
		Iterator<CjReciboDetalle> it=lstPagodetalle.iterator();
		while(it.hasNext()){
			CjReciboDetalle detalle=it.next();
			if(detalle.getFormaPagoId()==Constante.FORMA_PAGO_BONO_CAJAM){
				return true;
			}
		}
		return false;
	}
	
	public void removeRowDetallePago(ActionEvent ev){
		try {
			UIComponent comp = ev.getComponent().getParent().getParent();
			if (comp != null) {
				UIData uiData = (UIData) comp;
				CjReciboDetalle rd = (CjReciboDetalle) uiData.getRowData();
				lstPagodetalle.remove(rd);
				this.montoIngresado = this.montoIngresado.subtract(rd.getMontoTotalSoles());
				BigDecimal dif = montoIngresado.subtract(contrib.getMontoCobrar());
				if(dif.doubleValue() >= 0){
					montoVuelto = dif;
					montoResta = null;
				}else{
					montoResta = dif.multiply(new BigDecimal("-1"));
					montoVuelto = null;
				}
			}
		} catch (Exception ex) {
			String msg = "No se ha podido eliminar el registro";
			System.out.println(msg + ex);
		}
	}
	
	private boolean validarNulos(){
		boolean valido = true;
		if(selectedFormaPagoId == Constante.FORMA_PAGO_TARCRED || selectedFormaPagoId == Constante.FORMA_PAGO_TARDEB){
			if(selectedTipoTarjeta == null || selectedTipoTarjeta.isEmpty()){
				valido = false;
				addErrorMessage("Por favor, seleccione el tipo de tarjeta");
			}
			if(selectedBanco == null || selectedBanco.isEmpty()){
				valido = false;
				addErrorMessage("Por favor, seleccione el banco");
			}
			if(numTarCheq == null || numTarCheq.isEmpty()){
				valido = false;
				addErrorMessage("Por favor, ingrese el número de " + labelTarjetaCheque);
			}
		}else if(selectedFormaPagoId == Constante.FORMA_PAGO_CHEQUE){
			if(selectedBanco == null || selectedBanco.isEmpty()){
				valido = false;
				addErrorMessage("Por favor, seleccione el banco");
			}
			if(numTarCheq == null || numTarCheq.isEmpty()){
				valido = false;
				addErrorMessage("Por favor, ingrese el número de " + labelTarjetaCheque);
			}
		}
		return valido;
	}
	
	private boolean validarDatos(){
		boolean valido = true;
		if(montoSoles == null || montoSoles.doubleValue() == 0){
			valido = false;
			addErrorMessage("Por favor, ingrese el monto");
		}
		return valido;
	}
	
	private void iniciarFormasPago(){
		selectedFormaPagoId = Constante.FORMA_PAGO_EFECTIVO;
		selectedFormaPago = mapICjFormaPago.get(selectedFormaPagoId);
		iniciarFormaPago();
		montoIngresado = new BigDecimal("0");
		montoResta = null;		
		montoVuelto = null;
		referencia = null;
		lstPagodetalle = new ArrayList<CjReciboDetalle>();
	}
	
	private void iniciarFormaPago(){
		selectedTipoMoneda = Constante.TIPO_MONEDA_SOLES;
		iniciarTipoMoneda();
	}
	
	private void iniciarTipoMoneda(){
		monto = new BigDecimal("0");
		tipoCambio = new GnTipoCambio();
		montoSoles = new BigDecimal("0");
		selectedTipoTarjeta = null;
		selectedBanco = null;
		numTarCheq = null;
		observacion = null;
		
		if(selectedFormasPago.equals(Constante.FORMAS_PAGO_EFECTIVO)){
			montoIngresado = new BigDecimal("0");
			montoResta = null;
			montoVuelto = null;
		}
	}
	
	public String cobrarDeuda(){
		correct = false;
		try{
			//No puede realizar el cobro si es SOLO Bono cajamarquino
			int i = 0;
			
			if (selectedFormasPago.equals(Constante.FORMAS_PAGO_EFECTIVO)) {
				i = i + 1;
			} else {

				for (CjReciboDetalle formapago : this.lstPagodetalle) {
					if (formapago.getFormaPagoId() != Constante.FORMA_PAGO_BONO_CAJAM) {
						i = i + 1;
					}
				}
			}
			
			if(i > 0){
				//puede cobrar porque no solo es Bono Cajamarquino
				if(this.montoIngresado != null && !this.montoIngresado.equals("0.00") && this.montoIngresado.doubleValue() > 0){
					
					/** Verificacion del pago que no contenga descuentos y el monto ingresado sea menor del solicitado */
					if(this.montoDescuento.compareTo(BigDecimal.ZERO) > 0 && this.montoIngresado.compareTo(this.montoACobrar.setScale(2,RoundingMode.HALF_UP)) < 0){
						addErrorMessage("No se puede realizar el cobro con dinero menor del solicitado debido a que se esta realizando un pago con descuento.");
						return "";
					}
					
					if(selectedFormasPago.equals(Constante.FORMAS_PAGO_EFECTIVO)){
						
						/** Cuando solo paga en efectivo y en soles*/
						CjReciboDetalle rd = new CjReciboDetalle();
						int tipoMon = mapGnTipoMoneda.get(selectedTipoMoneda);
						rd.setFormaPagoId(selectedFormaPagoId);
						rd.setTipoCambio(tipoCambio==null ? null : tipoCambio.getValorVenta());
						
						/** Verificando cuando el contribuyente le cobran 20 y el paga con 50 entonces el monto es 20, ahora 
						 * si el contribuyente le cobran 20 y da 10 entonces el monto es 10 */
						BigDecimal montoCalculado = ((montoIngresado.compareTo(contrib.getMontoCobrar()) >= 0 )?contrib.getMontoCobrar():montoIngresado);
						rd.setMonto(montoCalculado);
						rd.setMontoTotalSoles(montoCalculado);					
						rd.setUsuarioId(getSessionManaged().getUsuarioLogIn().getUsuarioId());
						rd.setObservacion(referencia);
						rd.setEstado(Constante.ESTADO_ACTIVO);
						rd.setTerminal(getSessionManaged().getTerminalLogIn());
						rd.setTipoMonedaId(tipoMon);
						lstPagodetalle.add(rd);
					}
					
					
					/** Realizando la verificacion */
					int reciboId = cajaBo.cobrarDeuda(idDeudasACobrar,
							lstPagodetalle,
							getSessionManaged().getUsuarioLogIn().getUsuarioId(),
							contrib.getPersonaId(),
							contrib.getMontoCobrar(),
							montoIngresado,
							montoVuelto,
							referencia,
							selectedFormasPago);
					
					/** Iniciando la verificacion del recibo*/
					cajaBo.verificarRecibo(reciboId);
					
					correct=true;
					getSessionManaged().getSessionMap().put("caja.imprimirecibo.reciboId", reciboId);
					lstPagodetalle = new ArrayList<CjReciboDetalle>();
					getCuentaCorrienteManaged().initialize();
					//addInfoMessage("El pago se ha realizado con éxito");
				}else{
					addErrorMessage("Por favor, ingrese el monto");
				}
				
			}
			else{
				//No puede realizar el cobro si es SOLO Bono cajamarquino
				addErrorMessage("No es posible realizar el pago con solo el Bono Cajamarquino. Por favor, ingrese ingrese adicionalmente otra forma de pago");
			}
				
			
			
		}catch(SisatException ex){
//			addErrorMessage(ex.getMessage());
			WebMessages.messageError(ex.getMessage());
		}catch(Exception ex){
			String msg = "No se ha podido registrar el pago";
			addErrorMessage(msg);
			System.out.println(msg + ex);
		}
		return null;
	}
	
	public String getSelectedFormasPago() {
		return selectedFormasPago;
	}

	public void setSelectedFormasPago(String selectedFormasPago) {
		this.selectedFormasPago = selectedFormasPago;
	}

	public String getSelectedTipoMoneda() {
		return selectedTipoMoneda;
	}

	public void setSelectedTipoMoneda(String selectedTipoMoneda) {
		this.selectedTipoMoneda = selectedTipoMoneda;
	}

	public List<SelectItem> getLstTipoMoneda() {
		return lstTipoMoneda;
	}

	public List<SelectItem> getLstFormaPago() {
		return lstFormaPago;
	}

	public GnTipoCambio getTipoCambio() {
		return tipoCambio;
	}

	public String getSelectedFormaPago() {
		return selectedFormaPago;
	}

	public void setSelectedFormaPago(String selectedFormaPago) {
		this.selectedFormaPagoId = mapCjFormaPago.get(selectedFormaPago);
		if(selectedFormaPagoId == Constante.FORMA_PAGO_CHEQUE){
			labelTarjetaCheque = "cheque";
		}else{
			labelTarjetaCheque = "tarjeta";
		}
		this.selectedFormaPago = selectedFormaPago;
	}

	public int getSelectedFormaPagoId() {
		return selectedFormaPagoId;
	}

	public void setSelectedFormaPagoId(int selectedFormaPagoId) {
		this.selectedFormaPagoId = selectedFormaPagoId;
	}

	public String getLabelTarjetaCheque() {
		return labelTarjetaCheque;
	}

	public void setLabelTarjetaCheque(String labelTarjetaCheque) {
		this.labelTarjetaCheque = labelTarjetaCheque;
	}

	public CjPersona getContrib() {
		return contrib;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		if(monto != null){
			this.montoSoles = monto.multiply(tipoCambio.getValorVenta());
			this.montoSoles = montoSoles.setScale(2, RoundingMode.HALF_UP);
			if(selectedFormasPago.equals(Constante.FORMAS_PAGO_EFECTIVO)){
				this.montoIngresado = montoSoles.setScale(2,RoundingMode.HALF_UP);
				BigDecimal dif = montoIngresado.subtract(contrib.getMontoCobrar());
				if(dif.doubleValue() >= 0){
					montoVuelto = dif;
					montoResta = null;
				}else{
					montoResta = dif.multiply(new BigDecimal("-1"));
					montoVuelto = null;
				}
			}
		}
		this.monto = monto;
	}

	public BigDecimal getMontoSoles() {
		return montoSoles;
	}

	public void setMontoSoles(BigDecimal montoSoles) {
		if(montoSoles != null){
			if(selectedFormasPago.equals(Constante.FORMAS_PAGO_EFECTIVO)){
				this.montoIngresado = montoSoles;//.setScale(2,RoundingMode.HALF_UP);
				BigDecimal dif = montoIngresado.subtract(contrib.getMontoCobrar());
				if(dif.doubleValue() >= 0){
					montoVuelto = dif;
					montoResta = null;
				}else{
					montoResta = dif.multiply(new BigDecimal("-1"));
					montoVuelto = null;
				}
			}
		}
		this.montoSoles = montoSoles;
	}

	public Integer getNumeroCuotas() {
		return numeroCuotas;
	}

	public void setNumeroCuotas(Integer numeroCuotas) {
		if(numeroCuotas != null){
			if(selectedFormasPago.equals(Constante.FORMAS_PAGO_VARIAS)&&
					selectedFormaPagoId==Constante.FORMA_PAGO_BONO_CAJAM&&
					getResumenConcepto()!=null&&getResumenConcepto().getSubConceptoId()>0){
				
				if(numeroCuotas!=null&&numeroCuotas<=getResumenConcepto().getCuotas()){
					BigDecimal porcentajeDescuento= new BigDecimal("0.0");
					if(getResumenConcepto().getAnnoDeuda()==2016){
						porcentajeDescuento= new BigDecimal("0.15"); // Aplicación del bono cajamarquino del 15% para el 2016
					}else if(getResumenConcepto().getAnnoDeuda()==2015){
						porcentajeDescuento= new BigDecimal("0.15"); // Aplicación del bono cajamarquino del 15% para el 2014
					}else if(getResumenConcepto().getAnnoDeuda()==2014){
						porcentajeDescuento= new BigDecimal("0.15"); // Aplicación del bono cajamarquino del 15% para el 2014						
					}else if(getResumenConcepto().getAnnoDeuda()==2013){
						porcentajeDescuento= new BigDecimal("0.3");
					}else if(getResumenConcepto().getAnnoDeuda()<=2013 &&
							getResumenConcepto().getAnnoDeuda()>=2009){
						porcentajeDescuento= new BigDecimal("0.15");
					}
					//	BigDecimal es=getResumenConcepto().getMontoCuota();			
//					BigDecimal temp = (getResumenConcepto().getMontoCuota()).multiply(porcentajeDescuento);					
//					BigDecimal temp = ((montoDescuento.equals(new BigDecimal("0.00"))?montoACobrar:montoDescuento).multiply(porcentajeDescuento)).divide(BigDecimal.valueOf(resumenConcepto.getCuotas()));
					BigDecimal temp = (montoACobrar.multiply(porcentajeDescuento)).divide(BigDecimal.valueOf(resumenConcepto.getCuotas()));
//					BigDecimal montoDescuento = (temp.setScale(2, RoundingMode.)).multiply(new BigDecimal(numeroCuotas));
					BigDecimal montoDescuento = (temp).multiply(new BigDecimal(numeroCuotas));
					this.montoSoles = montoDescuento;
				}else{
					addErrorMessage("El numero de cuotas ingresado es mayor a la cantidad de cuotas seleccionado");
				}
			}
		}
		this.numeroCuotas = numeroCuotas;
	}
	
	public BigDecimal getMontoIngresado() {
		return montoIngresado;
	}

	public void setMontoIngresado(BigDecimal montoIngresado) {
		this.montoIngresado = montoIngresado;
	}

	public BigDecimal getMontoResta() {
		return montoResta;
	}

	public void setMontoResta(BigDecimal montoResta) {
		this.montoResta = montoResta;
	}

	public BigDecimal getMontoVuelto() {
		return montoVuelto;
	}

	public void setMontoVuelto(BigDecimal montoVuelto) {
		this.montoVuelto = montoVuelto;
	}

	public String getSelectedBanco() {
		return selectedBanco;
	}

	public void setSelectedBanco(String selectedBanco) {
		this.selectedBanco = selectedBanco;
	}

	public String getSelectedTipoTarjeta() {
		return selectedTipoTarjeta;
	}

	public void setSelectedTipoTarjeta(String selectedTipoTarjeta) {
		this.selectedTipoTarjeta = selectedTipoTarjeta;
	}

	public List<SelectItem> getLstBanco() {
		return lstBanco;
	}

	public List<SelectItem> getLstTipoTarjeta() {
		return lstTipoTarjeta;
	}

	public List<CjReciboDetalle> getLstPagodetalle() {
		return lstPagodetalle;
	}

	public String getNumTarCheq() {
		return numTarCheq;
	}

	public void setNumTarCheq(String numTarCheq) {
		this.numTarCheq = numTarCheq;
	}

	public boolean isCorrect() {
		return correct;
	}

	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getIdDeudasACobrar() {
		return idDeudasACobrar;
	}

	public void setIdDeudasACobrar(String idDeudasACobrar) {
		this.idDeudasACobrar = idDeudasACobrar;
	}

	public BigDecimal getMontoDescuento() {
		return montoDescuento;
	}

	public void setMontoDescuento(BigDecimal montoDescuento) {
		this.montoDescuento = montoDescuento;
	}

	public BigDecimal getMontoSinDescuento() {
		return montoSinDescuento;
	}

	public void setMontoSinDescuento(BigDecimal montoSinDescuento) {
		this.montoSinDescuento = montoSinDescuento;
	}

	public BigDecimal getMontoACobrar() {
		return montoACobrar;
	}

	public void setMontoACobrar(BigDecimal montoACobrar) {
		this.montoACobrar = montoACobrar;
	}
	
	public String getMsgDescAplicacionBono() {
		return msgDescAplicacionBono;
	}

	public void setMsgDescAplicacionBono(String msgDescAplicacionBono) {
		this.msgDescAplicacionBono = msgDescAplicacionBono;
	}
	
	public ResumenConceptosDTO getResumenConcepto() {
		return resumenConcepto;
	}

	public void setResumenConcepto(ResumenConceptosDTO resumenConcepto) {
		this.resumenConcepto = resumenConcepto;
	}

	public Integer getNumeroCuyes() {
		return numeroCuyes;
	}

	public void setNumeroCuyes(Integer numeroCuyes) {
		if (numeroCuyes != null) {
			if (selectedFormasPago.equals(Constante.FORMAS_PAGO_VARIAS)
					&& selectedFormaPagoId == Constante.FORMA_PAGO_BONO_CAJAM
					&& getResumenConcepto() != null
					&& getResumenConcepto().getSubConceptoId() > 0) {
				int nroCuyesCalculados = 0;
				for (int i = 1; i <= contadorUsos; i++) {
					if (numeroCuyes == 12 * i) {
						setNumeroCuotas(i);
						/** Numero de Cuotas beneficiadas con el bono */
						nroCuyesCalculados = 12 * i;
						break;
					} else {
						nroCuyesCalculados = 0;
					}
				}
				if (nroCuyesCalculados == 0) {
					addErrorMessage("El numero de Cuyes ingresado es incorrecto para "
							+ getContadorUsos() + " cuota(s) Casa Habitación.");
				}

			}
		}
		this.numeroCuyes = numeroCuyes;
	}

	public Integer getContadorUsos() {
		return contadorUsos;
	}

	public void setContadorUsos(Integer contadorUsos) {
		this.contadorUsos = contadorUsos;
	}
}
