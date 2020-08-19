package com.sat.sisat.caja.managed;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.sat.sisat.caja.dto.CjPapeletaDTO;
import com.sat.sisat.caja.dto.CjPersona;
import com.sat.sisat.caja.dto.DeudaDTO;
import com.sat.sisat.caja.vo.CjAdministradoVo;
import com.sat.sisat.cobranzacoactiva.business.CobranzaCoactivaBoRemote;
import com.sat.sisat.cobranzacoactiva.dto.ResumenDeudasCobranzaCoactivaDTO;
import com.sat.sisat.common.business.GeneralBoRemote;
import com.sat.sisat.common.dto.GenericDTO;
import com.sat.sisat.common.managed.MensajeSisatDTO;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.obligacion.dto.ResumenObligacionDTO;
import com.sat.sisat.persistence.entity.CjReciboDetalle;
import com.sat.sisat.persistence.entity.GnTipoCambio;
import com.sat.sisat.persistence.entity.GnTipoMoneda;
import com.sat.sisat.vehicular.business.VehicularBoRemote;

@ManagedBean
@ViewScoped
public class CajaCobranzaPapeletaManaged extends BaseManaged implements Serializable {

	private static final long serialVersionUID = -6132040880175757395L;

	@EJB
	CajaBoRemote cajaBo;
	@EJB
	VehicularBoRemote vehicularBo;
	@EJB
	GeneralBoRemote generalBo;
	
	@EJB
	CobranzaCoactivaBoRemote cobranzaCoactivaBo;
	
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
	
	private BigDecimal montoSinDescuento = BigDecimal.ZERO;
	private BigDecimal montoACobrar = BigDecimal.ZERO;
	private BigDecimal monto = BigDecimal.ZERO;
	private BigDecimal montoSoles = BigDecimal.ZERO;
	private BigDecimal montoIngresado = BigDecimal.ZERO;
	private BigDecimal montoResta = BigDecimal.ZERO;
	private BigDecimal montoVuelto = BigDecimal.ZERO;
	private BigDecimal montoDescuento = BigDecimal.ZERO;
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
	
	private List<CjPapeletaDTO> listPapeletas = new ArrayList<CjPapeletaDTO>();
	
	private String idDeudasACobrar;
	
	
	private List<Integer> deudasPapeletasSeleccionadas;
	
	private List<ResumenDeudasCobranzaCoactivaDTO> lstResumenDeudasCobranzaCoactivaDTOs;
	
	private List<ResumenObligacionDTO> listResumenObligacionDTOs;
	
	private List<String> listUsuariosAtencion;
	
	private MensajeSisatDTO mensajeSisatDTO = null;
	
	public CajaCobranzaPapeletaManaged() {
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
			
			montoSinDescuento = BigDecimal.ZERO;
			montoACobrar = BigDecimal.ZERO;
			monto = BigDecimal.ZERO;
			montoSoles = BigDecimal.ZERO;
			montoIngresado = BigDecimal.ZERO;
			montoResta = BigDecimal.ZERO;
			montoVuelto = BigDecimal.ZERO;
			montoDescuento = BigDecimal.ZERO;
			
			deudasPapeletasSeleccionadas = new ArrayList<Integer>();
			iniciarDatosDefault();
			
		}catch(Exception ex){
			String msg = "No se ha podido cargar los datos iniciales";
			System.out.println(msg + ex);
		}
	}
	
	private CajaPersonaManaged getCajaPersonaManaged(){
		return (CajaPersonaManaged) getManaged("cajaPersonaManaged");
	}
	
	@SuppressWarnings("unchecked")
	public void iniciarDatosDefault() {
		try {
			
			
			/** Reiniciando los montos */
			this.montoDescuento = BigDecimal.ZERO;
			this.montoSinDescuento = BigDecimal.ZERO;
			this.montoACobrar = BigDecimal.ZERO;
			
			
			// String deudasId;

			selectedFormasPago = Constante.FORMAS_PAGO_EFECTIVO;

			contrib = (CjPersona) getSessionMap().get("cjPersona");

			// obtension de las papeletas seleccionadas
			deudasPapeletasSeleccionadas = (List<Integer>) getSessionMap().get("deudasPapeletasSeleccionadas");
						
			if (deudasPapeletasSeleccionadas != null) {
				
				/** Removiendo el objeto de session*/
				getSessionMap().remove("deudasPapeletasSeleccionadas");

				idDeudasACobrar = deudasPapeletasSeleccionadas.toString();
				idDeudasACobrar = idDeudasACobrar.replace("[", "");
				idDeudasACobrar = idDeudasACobrar.replace("]", "");

				int cajeroId = getSessionManaged().getUsuarioLogIn().getUsuarioId();
				// String nroPapeleta = contrib.getNroPapeleta();
				// if (nroPapeleta != null && !nroPapeleta.equals("")) {
				
				listUsuariosAtencion = cajaBo.busquedaDeudaEnAtencion(idDeudasACobrar);
				lstResumenDeudasCobranzaCoactivaDTOs = cajaBo.verificarDeudasEnCobranzaCoactiva(idDeudasACobrar);
				this.listPapeletas = cajaBo.obtenerPapeletaResumenPorDeudasId(idDeudasACobrar, 2);
				this.listResumenObligacionDTOs = cajaBo.obtenerResumenObligacionesPorDeudasId(idDeudasACobrar, 2);
				
				if(listResumenObligacionDTOs.size() > 0){
					idDeudasACobrar = idDeudasACobrar.concat(procesarResumenObligacionesDTO(listResumenObligacionDTOs));
				}

				if (listPapeletas != null && listPapeletas.size() > 0) {

					// int papeletaId = listPapeletas.get(0).getPapeletaId();

					// cajaBo.obtenerDeudaPapeleta(cajeroId,
					// contrib.getPersonaId(),
					// papeletaId,
					// DateUtil.getCurrentDate());

					/**
					 * Carga las deudas en la tabla cj_tmp_deuda para despues poder filtrar la deuda
					 */
					// cajaBo.obtenerDeuda(getSessionManaged().getUsuarioLogIn().getUsuarioId(),
					// contrib.getPersonaId(), "", "", "", "", DateUtil.getCurrentDate());

					/**
					 * cuando se tiene mas de una papeleta entonces obtenemos el dueño del vehiculo
					 * para que figure en la cabecera del recibo
					 */
					if (listPapeletas.size() > 1) {
						contrib = cajaBo.cargarContribuyentePorPlaca((listPapeletas.get(0)).getPlaca());
					}

					/** Llenando la tabla cj_tmp con las deudas a ser cobradas*/
					cajaBo.obtenerDeudasPorDeudaId(cajeroId, idDeudasACobrar, DateUtil.getCurrentDate());
					// (getSessionManaged().getUsuarioLogIn().getUsuarioId(),
					// contrib.getPersonaId(), "", "", "", "", DateUtil.getCurrentDate());

					/** Solo se puede realizar el pago de una papeleta a la vez */
					// idDeudasACobrar = listPapeletas.get(0).getDeudaId().toString();
					List<DeudaDTO> listDeudaCobrar = cajaBo.obetenerDeudaConDsctos(idDeudasACobrar, Calendar
							.getInstance().getTime(), getSessionManaged().getUsuarioLogIn().getUsuarioId(),2);
					
					

					/** Sumando el total a pagar */
					for (DeudaDTO deudaDTO : listDeudaCobrar) {
						this.montoDescuento = montoDescuento.add(deudaDTO.getDescuento());
						this.montoSinDescuento = montoSinDescuento.add(deudaDTO.getSubtotal());
						this.montoACobrar = montoACobrar.add(deudaDTO.getTotalDeuda());

						for (CjPapeletaDTO papeletaDTO : listPapeletas) {
							if (papeletaDTO.getDeudaId().intValue() == (deudaDTO.getDeudaId())) {
								papeletaDTO.setMontoSinDescuento(deudaDTO.getSubtotal());
								papeletaDTO.setMontoConDescuento(deudaDTO.getSubtotal().subtract(deudaDTO
										.getDescuento()));
							}
						}

					}
				}
				// }
				contrib = cajaBo.ObtenerDatosPersona(contrib.getPersonaId(), cajeroId);
				if (contrib != null) {
					// contrib.setNroPapeleta(nroPapeleta);
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
	
	public void changeFormaPago(ValueChangeEvent ev){
		iniciarFormaPago();
		selectedFormaPagoId = mapCjFormaPago.get(ev.getNewValue());
		if(selectedFormaPagoId == 5){
			// TODO : Corregir
			this.montoSoles = contrib.getMontoCobrar().multiply(new BigDecimal("0.3"));
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
			if(validarNulos() && validarDatos()){
				CjReciboDetalle rd = new CjReciboDetalle();
				Integer banId = mapCjBanco.get(selectedBanco);
				Integer tipTarId = mapCjTipoTarjeta.get(selectedTipoTarjeta);
				int tipoMon = mapGnTipoMoneda.get(selectedTipoMoneda);
				rd.setBancoId(banId);
				rd.setFormaPagoId(selectedFormaPagoId);
				rd.setTipoCambio(tipoCambio==null ? null : tipoCambio.getValorVenta());
				rd.setMonto(monto == null || monto.doubleValue()==0 ? null : monto);
				rd.setMontoTotalSoles(montoSoles);
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
		montoIngresado = BigDecimal.ZERO;
		montoResta = BigDecimal.ZERO;
		montoVuelto = BigDecimal.ZERO;
		referencia = null;
		lstPagodetalle = new ArrayList<CjReciboDetalle>();
	}
	
	private void iniciarFormaPago(){
		selectedTipoMoneda = Constante.TIPO_MONEDA_SOLES;
		iniciarTipoMoneda();
	}
	
	private void iniciarTipoMoneda(){
		monto = BigDecimal.ZERO;
		tipoCambio = new GnTipoCambio();
		montoSoles = BigDecimal.ZERO;
		selectedTipoTarjeta = null;
		selectedBanco = null;
		numTarCheq = null;
		observacion = null;
		
		if(selectedFormasPago.equals(Constante.FORMAS_PAGO_EFECTIVO)){
			montoIngresado = BigDecimal.ZERO;
			montoResta = BigDecimal.ZERO;
			montoVuelto = BigDecimal.ZERO;
		}
	}
	
	public String cobrarDeuda(){
		correct = false;
		try{
			if(this.montoIngresado != null && !this.montoIngresado.equals("0.00") && this.montoIngresado.doubleValue() > 0){
				
				/** Verificacion del pago que no contenga descuentos y el monto ingresado sea menor del solicitado */
				if(this.montoDescuento.compareTo(BigDecimal.ZERO) > 0 && this.montoIngresado.compareTo(this.montoACobrar) < 0){
					addErrorMessage("No se puede realizar el cobro con dinero menor del solicitado debido a que se esta realizando un pago con descuento.");
					return "";
				}
				
//				CjReciboPago recibo = new CjReciboPago();
//				recibo.setPersonaId(contrib.getPersonaId());
//				recibo.setFechaRecibo(DateUtil.getCurrentDate());
//				recibo.setMontoACobrar(contrib.getMontoCobrar());
//				recibo.setMontoDescuento(BigDecimal.ZERO); //TODO : POR REVISAR TODO SOBRE DESCUENTOS
//				recibo.setMontoCobrado(this.montoIngresado.doubleValue() > contrib.getMontoCobrar().doubleValue() ?  contrib.getMontoCobrar() : this.montoIngresado);
//				recibo.setMontoVuelto(this.montoVuelto == null || this.montoVuelto.doubleValue() == 0 ? BigDecimal.ZERO : this.montoVuelto);
//				//recibo.setNroReferencia();
//				recibo.setUsuarioId(getSessionManaged().getUsuarioLogIn().getUsuarioId());
//				recibo.setEstado(Constante.ESTADO_ACTIVO);
//				recibo.setFechaRegistro(DateUtil.getCurrentDate());
//				recibo.setTerminal(getSessionManaged().getTerminalLogIn());
//				recibo.setFlagFuente(Constante.ESTADO_ACTIVO);
//				recibo.setReferencia(referencia);
				if(selectedFormasPago.equals(Constante.FORMAS_PAGO_EFECTIVO)){
					CjReciboDetalle rd = new CjReciboDetalle();
					int tipoMon = mapGnTipoMoneda.get(selectedTipoMoneda);
					rd.setFormaPagoId(selectedFormaPagoId);
					rd.setTipoCambio(tipoCambio==null ? null : tipoCambio.getValorVenta());
					rd.setMonto(monto == null || monto.doubleValue()==0 ? null : monto);
					/** Verificando cuando el contribuyente le cobran 20 y el paga con 50 entonces el monto es 20, ahora 
					 * si el contribuyente le cobran 20 y da 10 entonces el monto es 10 */
					BigDecimal montoCalculado = ((montoIngresado.compareTo(montoACobrar) >= 0 )? montoACobrar : montoIngresado);
					rd.setMonto(montoCalculado);					
					rd.setMontoTotalSoles(montoCalculado);
					rd.setUsuarioId(getSessionManaged().getUsuarioLogIn().getUsuarioId());
					rd.setObservacion(referencia);
					rd.setEstado(Constante.ESTADO_ACTIVO);
					rd.setTerminal(getSessionManaged().getTerminalLogIn());
					rd.setTipoMonedaId(tipoMon);
					lstPagodetalle.add(rd);
				}
				//TODO Modificar para la cobranza de papeletas 
				
//				int reciboId = cajaBo.cobrarDeuda("",
//							lstPagodetalle, 
//							getSessionManaged().getUsuarioLogIn().getUsuarioId(),
//							contrib.getPersonaId(),
//							monto,
//							montoIngresado,
//							montoVuelto,
//							referencia,
//							selectedFormasPago
//							);
				
				/** Realizando el pago*/
				int reciboId = cajaBo.cobrarDeuda(idDeudasACobrar,
						lstPagodetalle,
						getSessionManaged().getUsuarioLogIn().getUsuarioId(),
						contrib.getPersonaId(),
						this.montoACobrar,//contrib.getMontoCobrar(),
						montoIngresado,
						montoVuelto,
						referencia,
						selectedFormasPago);
				
				/** Realizando la verificacion del pago*/
				cajaBo.verificarRecibo(reciboId);
				
				correct=true;
				getSessionManaged().getSessionMap().put("caja.imprimirecibo.reciboId", reciboId);
				lstPagodetalle = new ArrayList<CjReciboDetalle>();
				getCajaPersonaManaged().setContribuyentes(new ArrayList<CjAdministradoVo>());
				
				
				/**Vuelve a realizar la busqueda para poder pagar otra papeleta o para mostrar que el contribuyente ya no
				 * posee deudas
				 * */
				
				CajaPersonaManaged cajaPersonaManaged = (CajaPersonaManaged) this.getManaged("cajaPersonaManaged");
				
				cajaPersonaManaged.buscar();				
				
				listPapeletas.clear();
			}else{
				addErrorMessage("Por favor, ingrese el monto");
			}
		}catch(SisatException ex){
			addErrorMessage(ex.getMessage());
		}catch(Exception ex){
			String msg = "No se ha podido registrar el pago";
			addErrorMessage(msg);
			System.out.println(msg + ex);
		}
		return null;
	}
	
	public void liberarContribuyente(){
		
		try {
			cajaBo.limpiarCjTmpDeudaCajero(getSessionManaged().getUsuarioLogIn().getUsuarioId());
			listPapeletas.clear();
		} catch (SisatException e) {
			addErrorMessage(e.getMessage());
		}
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
				this.montoIngresado = montoSoles.setScale(2,RoundingMode.HALF_UP);
				BigDecimal dif = montoIngresado.subtract(this.montoACobrar);
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

	private String procesarResumenObligacionesDTO(List<ResumenObligacionDTO> list){
		
		StringBuffer sb = new StringBuffer();
		for(ResumenObligacionDTO resumenObligacionDTO:list){
			sb.append(",");
			sb.append(resumenObligacionDTO.getDeudaId());
		}		
		return sb.toString();
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

	public List<CjPapeletaDTO> getListPapeletas() {
		return listPapeletas;
	}

	public BigDecimal getMontoACobrar() {
		return montoACobrar;
	}

	public void setMontoACobrar(BigDecimal montoACobrar) {
		this.montoACobrar = montoACobrar;
	}

	public BigDecimal getMontoSinDescuento() {
		return montoSinDescuento;
	}

	public void setMontoSinDescuento(BigDecimal montoSinDescuento) {
		this.montoSinDescuento = montoSinDescuento;
	}

	public BigDecimal getMontoDescuento() {
		return montoDescuento;
	}

	public void setMontoDescuento(BigDecimal montoDescuento) {
		this.montoDescuento = montoDescuento;
	}

	public List<ResumenDeudasCobranzaCoactivaDTO> getLstResumenDeudasCobranzaCoactivaDTOs() {
		return lstResumenDeudasCobranzaCoactivaDTOs;
	}

	public void setLstResumenDeudasCobranzaCoactivaDTOs(List<ResumenDeudasCobranzaCoactivaDTO> lstResumenDeudasCobranzaCoactivaDTOs) {
		this.lstResumenDeudasCobranzaCoactivaDTOs = lstResumenDeudasCobranzaCoactivaDTOs;
	}

	public MensajeSisatDTO getMensajeSisatDTO() {
		return mensajeSisatDTO;
	}

	public void setMensajeSisatDTO(MensajeSisatDTO mensajeSisatDTO) {
		this.mensajeSisatDTO = mensajeSisatDTO;
	}

	public List<String> getListUsuariosAtencion() {
		return listUsuariosAtencion;
	}

	public void setListUsuariosAtencion(List<String> listUsuariosAtencion) {
		this.listUsuariosAtencion = listUsuariosAtencion;
	}

	public List<ResumenObligacionDTO> getListResumenObligacionDTOs() {
		return listResumenObligacionDTOs;
	}

	public void setListResumenObligacionDTOs(List<ResumenObligacionDTO> listResumenObligacionDTOs) {
		this.listResumenObligacionDTOs = listResumenObligacionDTOs;
	}
}
