package com.sat.sisat.caja.managed;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.sat.sisat.caja.business.CajaBoRemote;
import com.sat.sisat.caja.dto.CjTupaDTO;
import com.sat.sisat.caja.dto.PagoTupaReferenciaDTO;
import com.sat.sisat.common.business.GeneralBoRemote;
import com.sat.sisat.common.dto.GenericDTO;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.SATWEBParameterFactory;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.estadocuenta.dto.CrConstanciaNoAdeudo;
import com.sat.sisat.estadocuenta.dto.CrGeneralDTO;
import com.sat.sisat.estadocuenta.managed.RegistrarConstanciaNoAdeudo;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.persistence.CrudServiceBean;
import com.sat.sisat.persistence.entity.CjPagoTupa;
import com.sat.sisat.persistence.entity.CjReciboDetalle;
import com.sat.sisat.persistence.entity.CjReciboPago;
import com.sat.sisat.persistence.entity.GnTipoCambio;
import com.sat.sisat.persistence.entity.GnTipoMoneda;
import com.sat.sisat.persona.business.PersonaBo;
import com.sat.sisat.persona.business.PersonaBoRemote;
import com.sat.sisat.persona.dto.PersonaEstadoCuentaResumido;
import com.sat.sisat.vehicular.business.VehicularBoRemote;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@ManagedBean
@ViewScoped
public class CajaTupaManaged extends BaseManaged implements Serializable {

	private static final long serialVersionUID = 562759051678515261L;

	@EJB
	CajaBoRemote cajaBo;
	@EJB
	VehicularBoRemote vehicularBo;
	@EJB
	GeneralBoRemote generalBo;
	@EJB
	PersonaBoRemote personaBo;

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

	private BigDecimal monto;
	private BigDecimal montoSoles;
		
	private BigDecimal total = new BigDecimal("0.00");
	private BigDecimal totalDescuento = new BigDecimal("0.00");
	private BigDecimal descuento = new BigDecimal("0.00");
	private BigDecimal montoIngresado;
	private BigDecimal montoResta;
	private BigDecimal montoVuelto;
	private String observacion;
	private PagoTupaReferenciaDTO referencia;
	private CrConstanciaNoAdeudo constancia;
	private Boolean tipo ;
	private Integer recibo ;
	private Integer reciboTemp;

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

	// TUPA
	private List<SelectItem> lstTupa = new ArrayList<SelectItem>();
//	private HashMap<String, Integer> mapCjTupa = new HashMap<String, Integer>();
//	private HashMap<Integer, CjTupaDTO> mapICjTupa = new HashMap<Integer, CjTupaDTO>();
//	private String selectedTupa;

	private List<CjTupaDTO> lstTupaDetalle = new ArrayList<CjTupaDTO>();

	/** Lista de tasas tupa */
	List<CjTupaDTO> listCjTupaDTOs;

	/*** Tupa actual seleccionado para agregar al carrito de compra */
	private CjTupaDTO tupaSeleccionado = new CjTupaDTO();
	
	private CjTupaDTO tupaSeleccionadoListaTupa = new CjTupaDTO();

	private BigDecimal montoTupa = new BigDecimal("0");

	private Boolean permanecerTupa = Boolean.FALSE;
	
	private Boolean permanecerReferencia = Boolean.FALSE;
	
	
	//--cramirez--
	private Boolean isConstancia = Boolean.FALSE;
	
	List<PersonaEstadoCuentaResumido> lstECRes = new ArrayList<PersonaEstadoCuentaResumido>();
	
	private Boolean isRecord = Boolean.FALSE;
	private int tipoRecord;
	private List<CrGeneralDTO> listAnioHR = null;
	private List<CrGeneralDTO> listPrediosHR = null;
	
	private int selectAnnioRecord;
	private int cantRecord = 0;
	
	private String prediosSelect = "";
	private int selectPersonaTemp;
	private int selectReciboTemp;
	
	private int recordId;
	
	//------------

	
	private String descripcionTupaSeleccionado;	
	
	public CajaTupaManaged() {
		getSessionManaged().setLinkRegresar("/sisat/caja/CajaTupa.xhtml");
	}

	List<PagoTupaReferenciaDTO> lst = new ArrayList<PagoTupaReferenciaDTO>();
	
	
	

	@PostConstruct
	public void inicialize() {
		recordId = Constante.TUPA_RECORD_HR_PU_PR;
		
		tipoRecord = 1 ;
		try {
			// Fill payment form
			List<GenericDTO> lstTmfp = new ArrayList<GenericDTO>();
			lstTmfp = cajaBo.obtenerFormaPagoSinBonoCajam();
			Iterator<GenericDTO> itfp = lstTmfp.iterator();
			while (itfp.hasNext()) {
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
				mapGnTipoMoneda.put(objTm.getDescripcion(), objTm.getTipoMonedaId());
				mapIGnTipoMoneda.put(objTm.getTipoMonedaId(), objTm.getDescripcion());
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
				mapCjTipoTarjeta.put(objAd.getDescripcion(), objAd.getId());
			}
			// Fill TUPA
//			List<CjTupaDTO> lstTP = new ArrayList<CjTupaDTO>();
//			lstTP = cajaBo.ObtenerTasaTupa();
//			if (lstTP != null && lstTP.size() > 0) {// Controlando null
//				Iterator<CjTupaDTO> itCj5 = lstTP.iterator();
//				while (itCj5.hasNext()) {
//					CjTupaDTO objAd = itCj5.next();
//					lstTupa.add(new SelectItem(objAd.getDescripcion()));
//					mapCjTupa.put(objAd.getDescripcion(), objAd.getTupaId());
//					mapICjTupa.put(objAd.getTupaId(), objAd);
//				}
//			}

			// comboxBox Tupa

			listCjTupaDTOs = cajaBo.ObtenerTasaTupa();

			iniciarDatosDefault();
		} catch (Exception ex) {
			String msg = "No se ha podido cargar los datos iniciales";
			System.out.println(msg + ex);
		}
	}

	public void initContent() {
		this.descripcionTupaSeleccionado = null;
		if (!permanecerTupa) {
			/** reinicializando las cantidades de los tupas */
			for (CjTupaDTO cjTupaDTO : lstTupaDetalle) {
				cjTupaDTO.setCant(1);
				cjTupaDTO.setTotalDescuento(BigDecimal.ZERO);
				cjTupaDTO.setSubTotal(BigDecimal.ZERO);
				
			}

			/** limpiando la lista que contiene los tupas seleccionados */
			lstTupaDetalle = new ArrayList<CjTupaDTO>();
			this.montoTupa = BigDecimal.ZERO;
			iniciarDatosDefault();
		}
		if (!permanecerReferencia) {
			referencia = new PagoTupaReferenciaDTO();
		}
	}

	public void iniciarDatosDefault() {
		selectedFormasPago = Constante.FORMAS_PAGO_EFECTIVO;

		iniciarFormasPago();
	}

	public void changeFormasPago(ValueChangeEvent ev) {
		iniciarFormasPago();
	}

	public void changeFormaPago(ValueChangeEvent ev) {
		iniciarFormaPago();
		selectedFormaPagoId = mapCjFormaPago.get(ev.getNewValue());
	}

	public void changeTipoMoneda(ValueChangeEvent ev) {
		selectedTipoMoneda = String.valueOf(ev.getNewValue());
		iniciarTipoMoneda();
		if (!selectedTipoMoneda.equals(Constante.TIPO_MONEDA_SOLES)) {
			int tipoMonedaId = mapGnTipoMoneda.get(selectedTipoMoneda);
			tipoCambio = generalBo.obtenerTipoCambioDia(tipoMonedaId);
			if (tipoCambio == null) {
				tipoCambio = new GnTipoCambio();
				selectedTipoMoneda = Constante.TIPO_MONEDA_SOLES;
				addErrorMessage(getMsg("gn.tipocambionodef"));
			}
		}
	}

	public String agregarPago() {
		try {
			if (validarNulos() && validarDatos()) {
				CjReciboDetalle rd = new CjReciboDetalle();
				Integer banId = mapCjBanco.get(selectedBanco);
				Integer tipTarId = mapCjTipoTarjeta.get(selectedTipoTarjeta);
				int tipoMon = mapGnTipoMoneda.get(selectedTipoMoneda);
				rd.setBancoId(banId);
				rd.setFormaPagoId(selectedFormaPagoId);
				rd.setTipoCambio(tipoCambio == null ? null : tipoCambio.getValorVenta());
				rd.setMonto(monto == null || monto.doubleValue() == 0 ? null : monto);
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
				this.montoIngresado = this.montoIngresado.add(montoSoles).setScale(2, RoundingMode.HALF_UP);
				BigDecimal dif = montoIngresado.subtract(montoTupa);
				if (dif.doubleValue() >= 0) {
					montoVuelto = dif;
					montoResta = null;
				} else {
					montoResta = dif.multiply(new BigDecimal("-1"));
					montoVuelto = null;
				}
				correct = true;
			} else {
				correct = false;
			}
		} catch (Exception ex) {
			String msg = "Ha ocurrido un error interno";
			addErrorMessage(msg + ex);
			correct = false;
		}
		return null;
	}

	public void removeRowDetallePago(ActionEvent ev) {
		try {
			UIComponent comp = ev.getComponent().getParent().getParent();
			if (comp != null) {
				UIData uiData = (UIData) comp;
				CjReciboDetalle rd = (CjReciboDetalle) uiData.getRowData();
				lstPagodetalle.remove(rd);
				this.montoIngresado = this.montoIngresado.subtract(rd.getMontoTotalSoles());
				BigDecimal dif = montoIngresado.subtract(montoTupa);
				if (dif.doubleValue() >= 0) {
					montoVuelto = dif;
					montoResta = null;
				} else {
					montoResta = dif.multiply(new BigDecimal("-1"));
					montoVuelto = null;
				}
			}
		} catch (Exception ex) {
			String msg = "No se ha podido eliminar el registro";
			System.out.println(msg + ex);
		}
	}

	public Integer getRecibo() {
		return recibo;
	}

	public void setRecibo(Integer recibo) {
		this.recibo = recibo;
	}

	private boolean validarNulos() {
		boolean valido = true;
		if (selectedFormaPagoId == Constante.FORMA_PAGO_TARCRED || selectedFormaPagoId == Constante.FORMA_PAGO_TARDEB) {
			if (selectedTipoTarjeta == null || selectedTipoTarjeta.isEmpty()) {
				valido = false;
				addErrorMessage("Por favor, seleccione el tipo de tarjeta");
			}
			if (selectedBanco == null || selectedBanco.isEmpty()) {
				valido = false;
				addErrorMessage("Por favor, seleccione el banco");
			}
			if (numTarCheq == null || numTarCheq.isEmpty()) {
				valido = false;
				addErrorMessage("Por favor, ingrese el número de " + labelTarjetaCheque);
			}
		} else if (selectedFormaPagoId == Constante.FORMA_PAGO_CHEQUE) {
			if (selectedBanco == null || selectedBanco.isEmpty()) {
				valido = false;
				addErrorMessage("Por favor, seleccione el banco");
			}
			if (numTarCheq == null || numTarCheq.isEmpty()) {
				valido = false;
				addErrorMessage("Por favor, ingrese el número de " + labelTarjetaCheque);
			}
		}
		return valido;
	}

	private boolean validarDatos() {
		boolean valido = true;
		if (montoSoles == null || montoSoles.doubleValue() == 0) {
			valido = false;
			addErrorMessage("Por favor, ingrese el monto ssss");
		}
		if (lstTupaDetalle == null || lstTupaDetalle.size() <= 0) {
			valido = false;
			addErrorMessage("Por favor, seleccione al menos una tasa de TUPA");
		}
		return valido;
	}

	private void iniciarFormasPago() {
		selectedFormaPagoId = Constante.FORMA_PAGO_EFECTIVO;
		selectedFormaPago = mapICjFormaPago.get(selectedFormaPagoId);
		iniciarFormaPago();
		montoIngresado = new BigDecimal("0");		
		montoResta = null;
		montoVuelto = null;
		totalDescuento=null;
		total=null;


		lstPagodetalle = new ArrayList<CjReciboDetalle>();
	}

	private void iniciarFormaPago() {
		selectedTipoMoneda = Constante.TIPO_MONEDA_SOLES;
		iniciarTipoMoneda();
	}

	private void iniciarTipoMoneda() {
		monto = new BigDecimal("0");
		tipoCambio = new GnTipoCambio();
		montoSoles = new BigDecimal("0");
		selectedTipoTarjeta = null;
		selectedBanco = null;
		numTarCheq = null;
		observacion = null;

		if (selectedFormasPago.equals(Constante.FORMAS_PAGO_EFECTIVO)) {
			montoIngresado = new BigDecimal("0");
			montoResta = null;
			montoVuelto = null;
		}
	}
	
	public String cobrarDeudaTupaPrev() {
		
		if(this.isConstancia) {			
			if(referencia.getPersonaId() == null){
				addErrorMessage("Por favor, seleccione un contribuyente");
			}else {
				cobrarDeudaTupa();
			}
		}else {
			cobrarDeudaTupa();
		}
		
		return null; 
	}
	
	public String cobrarDeudaTupaPrevRecord() {
		prediosSelect = "";
		
		if(listPrediosHR != null) {
			Iterator<CrGeneralDTO> it1 = listPrediosHR.iterator();
			
			while (it1.hasNext()) {
				CrGeneralDTO obj = it1.next();
				if(obj.isSeleted()) {
					prediosSelect=prediosSelect+obj.getGlosa()+",";
				}
			}
		}
		
		
		
		if(this.isRecord) {
			if(referencia.getPersonaId() == null){
				addErrorMessage("Por favor, seleccione un contribuyente");
			}else if(prediosSelect == null || prediosSelect == "") {
				addErrorMessage("Por favor, seleccione almenos un predio");
			}else {
				cobrarDeudaTupa();
			}
			
		}else {
			cobrarDeudaTupa();
		}
		
		return null; 
	}
	
	public void imprimirRecord() {
		if(tipoRecord == 2) {
			createPU();
		}else {
			createPR();
		}
	}
	
	public void limpiarRecord() {
		getAniosHR();
		isRecord =false;
		selectReciboTemp = 0;
	}
	
	public void createHR() {
		
		 java.sql.Connection connection=null;
	        try {
	        	CrudServiceBean connj=CrudServiceBean.getInstance();
				 connection=connj.connectJasper();
	        	String path_context=FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
			     String path_report=path_context+"/sisat/reportes/";
			     String path_imagen=path_context+"/sisat/reportes/imagen/";
			     
	        	
	             HashMap<String,Object> parameters = new HashMap<String,Object>();
	             parameters.put("recibo_id", selectReciboTemp);
	             parameters.put("ruta_image", SATWEBParameterFactory.getPathReporteImagenes()) ;
	             parameters.put("responsable", getSessionManaged().getUsuarioLogIn().getNombreUsuario());
	 			 parameters.put("SUBREPORT_DIR", SATWEBParameterFactory.getPathReporte()) ;
	 			
	                JasperPrint jasperPrint = JasperFillManager.fillReport((SATWEBParameterFactory.getPathReporte()+"HR_record.jasper"), parameters, connection);
	            
	             ByteArrayOutputStream output = new ByteArrayOutputStream();
	                JasperExportManager.exportReportToPdfStream(jasperPrint, output);
	               
	          HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	                 response.setContentType("application/pdf");
	          response.addHeader("Content-Disposition","attachment;filename=HRReport.pdf");
	          response.setContentLength(output.size());
	          ServletOutputStream servletOutputStream = response.getOutputStream();
	          servletOutputStream.write(output.toByteArray(), 0, output.size());
	          servletOutputStream.flush();
	          servletOutputStream.close();
	          FacesContext.getCurrentInstance().responseComplete();
	          
	          
	     } catch (Exception jre) {
	          jre.printStackTrace();
	          WebMessages.messageFatal(jre);
	     }finally{
	    	 try{
	    		 if(connection!=null){
	    			 connection.close();
	    			 connection=null;
	    		 }
	    	 }catch(Exception e){}
	     }
	        
	 }
	
	
	 public void createPU() {
		 	java.sql.Connection connection=null;
	        try {
				 CrudServiceBean connj=CrudServiceBean.getInstance();
				 connection=connj.connectJasper();
				 
	        	String path_context=FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
			     String path_report=path_context+"/sisat/reportes/";
			     String path_imagen=path_context+"/sisat/reportes/imagen/";
			     
	            HashMap<String,Object> parameters = new HashMap<String,Object>();
	            parameters.put("recibo_id", selectReciboTemp);
	            parameters.put("ruta_image", SATWEBParameterFactory.getPathReporteImagenes()) ;
	            parameters.put("responsable", getSessionManaged().getUsuarioLogIn().getNombreUsuario());
	 			parameters.put("SUBREPORT_DIR", SATWEBParameterFactory.getPathReporte()) ;
	            JasperPrint jasperPrint = JasperFillManager.fillReport((SATWEBParameterFactory.getPathReporte()+"PU_record.jasper"), parameters, connection);
	            ByteArrayOutputStream output = new ByteArrayOutputStream();
	            JasperExportManager.exportReportToPdfStream(jasperPrint, output);
	               
	          HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	                 response.setContentType("application/pdf");
	          response.addHeader("Content-Disposition","attachment;filename=PUReports.pdf");
	          response.setContentLength(output.size());
	          ServletOutputStream servletOutputStream = response.getOutputStream();
	          servletOutputStream.write(output.toByteArray(), 0, output.size());
	          servletOutputStream.flush();
	          servletOutputStream.close();
	          FacesContext.getCurrentInstance().responseComplete();
	          
	     } catch (Exception jre) {
	          jre.printStackTrace();
	          WebMessages.messageFatal(jre);
	     }finally{
	    	 try{
	    		 if(connection!=null){
	    			 connection.close();
	    			 connection=null;
	    		 }
	    	 }catch(Exception e){}
	     }
	 }
	 
	 
	 public void createPR() {
		 	java.sql.Connection connection=null;
	        try {
				 CrudServiceBean connj=CrudServiceBean.getInstance();
				 connection=connj.connectJasper();
				 
	        	String path_context=FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
			     String path_report=path_context+"/sisat/reportes/";
			     String path_imagen=path_context+"/sisat/reportes/imagen/";
			     
	            HashMap<String,Object> parameters = new HashMap<String,Object>();
	            parameters.put("recibo_id", selectReciboTemp);
	            parameters.put("ruta_image", SATWEBParameterFactory.getPathReporteImagenes()) ;
	            parameters.put("responsable", getSessionManaged().getUsuarioLogIn().getNombreUsuario());
	 			parameters.put("SUBREPORT_DIR", SATWEBParameterFactory.getPathReporte()) ;
	            JasperPrint jasperPrint = JasperFillManager.fillReport((SATWEBParameterFactory.getPathReporte()+"PR_record.jasper"), parameters, connection);
	            ByteArrayOutputStream output = new ByteArrayOutputStream();
	            JasperExportManager.exportReportToPdfStream(jasperPrint, output);
	               
	          HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	                 response.setContentType("application/pdf");
	          response.addHeader("Content-Disposition","attachment;filename=PRReports.pdf");
	          response.setContentLength(output.size());
	          ServletOutputStream servletOutputStream = response.getOutputStream();
	          servletOutputStream.write(output.toByteArray(), 0, output.size());
	          servletOutputStream.flush();
	          servletOutputStream.close();
	          FacesContext.getCurrentInstance().responseComplete();
	          
	     } catch (Exception jre) {
	          jre.printStackTrace();
	          WebMessages.messageFatal(jre);
	     }finally{
	    	 try{
	    		 if(connection!=null){
	    			 connection.close();
	    			 connection=null;
	    		 }
	    	 }catch(Exception e){}
	     }
	 }
	 
	
	public void limpiarContribuyente(){
		referencia = new PagoTupaReferenciaDTO();
	}

	public String cobrarDeudaTupa() {
		correct = false;
		try {
			RegistrarConstanciaNoAdeudo registrarConstanciaNoAdeudo = (RegistrarConstanciaNoAdeudo) this.getManaged("registrarConstanciaNoAdeudo");
			
			//cramirez
			if(registrarConstanciaNoAdeudo.getTipoConstancia() == 1) {
				registrarConstanciaNoAdeudo.setCuotaSelect("1,2,3,4,5,6,7,8,9,10,11,12,");
			}
			
			if(this.isConstancia) {
				int respon = registrarConstanciaNoAdeudo.buscar();
				
				if(respon == 1) {
					addErrorMessage("El Contribuyente mantiene deuda en los conceptos seleccionados.");
					return null;
				}
				if(respon == 3) {
					addErrorMessage("Por favor seleccione Concepto, Año de deuda, cuotas y Predio(s)/Placa(s). Gracias.");
					return null;
				}
				if(respon == 4) {
					addErrorMessage("Por favor seleccione Concepto, Año de deuda y Predio(s)/Placa(s). Gracias.");
					return null;
				}
			}
			//--------
			
			if (this.montoIngresado != null && this.montoIngresado.doubleValue() > 0
					&& this.montoIngresado.compareTo(montoTupa) >= 0 && this.lstTupaDetalle != null
					&& this.lstTupaDetalle.size() > 0) {
				CjReciboPago recibo = new CjReciboPago();
				// recibo.setPersonaId(contrib.getPersonaId());
				recibo.setFechaRecibo(DateUtil.getCurrentDate());
				recibo.setMontoACobrar(montoTupa);
				recibo.setMontoDescuento(totalDescuento);
				/**
				 * Por convencion no hay pagos a cuenta en el pago tupa entonces ingresa sin
				 * comparacion, caso contrario del pago de impuestos y arbitrios
				 */
				recibo.setMontoCobrado(this.montoIngresado);
				recibo.setMontoVuelto(this.montoVuelto);
				// recibo.setNroReferencia();
				recibo.setUsuarioId(getSessionManaged().getUsuarioLogIn().getUsuarioId());
				recibo.setEstado(Constante.ESTADO_ACTIVO);
				recibo.setFechaRegistro(DateUtil.getCurrentDate());
				recibo.setTerminal(getSessionManaged().getTerminalLogIn());
				recibo.setFlagFuente(Constante.ESTADO_ACTIVO);
				recibo.setReferencia(referencia.toString());
				recibo.setTipoRecibo(Constante.TIPO_RECIBO_TUPA);
				/** insertando a la persona para poder ser enlazada en el pago de tupas */
				if (referencia.getPersonaId() != null && referencia.getPersonaId() > 0) {
					recibo.setPersonaId(referencia.getPersonaId());
				}

				if (selectedFormasPago.equals(Constante.FORMAS_PAGO_EFECTIVO)) {
					CjReciboDetalle rd = new CjReciboDetalle();
					int tipoMon = mapGnTipoMoneda.get(selectedTipoMoneda);
					rd.setFormaPagoId(selectedFormaPagoId);
					rd.setTipoCambio(tipoCambio == null ? null : tipoCambio.getValorVenta());
					rd.setMonto(this.montoTupa);
					rd.setMontoTotalSoles(montoTupa);
					rd.setUsuarioId(getSessionManaged().getUsuarioLogIn().getUsuarioId());
					rd.setEstado(Constante.ESTADO_ACTIVO);
					rd.setTerminal(getSessionManaged().getTerminalLogIn());
					rd.setTipoMonedaId(tipoMon);
					lstPagodetalle.add(rd);
				}

				List<CjPagoTupa> list = new ArrayList<CjPagoTupa>();
				for (CjTupaDTO tu : lstTupaDetalle) {
					CjPagoTupa pa = new CjPagoTupa();
					pa.setTupaId(tu.getTupaId());
						if (tu.getTupaId()==8){ setTipo(true);}
					pa.setFechaPago(DateUtil.getCurrentDate());
					pa.setMontoPago(tu.getSubTotal());
					pa.setCantidad(tu.getCant());
					pa.setFlagExtorno(Constante.ESTADO_INACTIVO);
					pa.setUsuarioId(getSessionManaged().getUsuarioLogIn().getUsuarioId());
					pa.setEstado(Constante.ESTADO_ACTIVO);
					pa.setFechaRegistro(DateUtil.getCurrentDate());
					pa.setTerminal(getSessionManaged().getTerminalLogIn());
					pa.setDescuento(tu.getTotalDescuento());
					pa.setSubTotal(tu.getSubTotal().add(tu.getDescuento()));
					pa.setOrdenanzaId(16);
					list.add(pa);
				}
				
				/** Realizando el pago*/
				int reciboId = cajaBo.cobrarDeudaTupa(recibo, lstPagodetalle, list, getSessionManaged()
						.getUsuarioLogIn().getUsuarioId());
				
				/** Registro de Constancia */
				setRecibo(reciboId);
				if (tipo==Boolean.TRUE){
					constancia = new CrConstanciaNoAdeudo();
					if (referencia.getPersonaId() != null && referencia.getPersonaId() > 0) {
						constancia.setPersonaId(referencia.getPersonaId());}
					constancia.setReciboId(reciboId);
					constancia.setDocidentidad(referencia.getDniRuc());
					constancia.setUsuarioId(getSessionManaged().getUsuarioLogIn().getUsuarioId());
					constancia.setTerminal(getSessionManaged().getTerminalLogIn());
					
					//--cramirez
					
					registrarConstanciaNoAdeudo.getConstanciaItem().setPersonaId(referencia.getPersonaId());
					registrarConstanciaNoAdeudo.getConstanciaItem().setReciboId(reciboId);
					
					constancia.setAnio(registrarConstanciaNoAdeudo.getAnnioSelect());
					constancia.setSubconceptoId(registrarConstanciaNoAdeudo.getSubconceptoSelect());
					constancia.setReferencia(registrarConstanciaNoAdeudo.getPredioSelect());
					constancia.setTipoConstancia(registrarConstanciaNoAdeudo.getTipoConstancia());
					constancia.setCuotas(registrarConstanciaNoAdeudo.getCuotaSelect());
					//----------
					cajaBo.registrarConstancia(constancia);
					
					registrarConstanciaNoAdeudo.setAnnioSelect(null);
					registrarConstanciaNoAdeudo.setSubconceptoSelect(null);
					registrarConstanciaNoAdeudo.setPredioSelect(null);
					registrarConstanciaNoAdeudo.setCuotaSelect(null);
					
					registrarConstanciaNoAdeudo.setListAnio(null);
					registrarConstanciaNoAdeudo.setListConcepto(null);
					registrarConstanciaNoAdeudo.setListCuota(null);
					registrarConstanciaNoAdeudo.setListPropiedad(null);
					
					this.isConstancia = false;
					reciboTemp = reciboId;
					//--cramirez
					//registrarConstanciaNoAdeudo.impresion();
					//generacionRmPendientesPagoServicios(referencia.getPersonaId(), reciboId);
					//----------
				}
				
				if(isRecord && tipoRecord > 1 ) {			
					
					cajaBo.registrarRecord(referencia.getPersonaId(), referencia.getDniRuc(), (this.selectAnnioRecord+""), this.prediosSelect, tipoRecord, reciboId,
							getSessionManaged().getUsuarioLogIn().getUsuarioId(), getSessionManaged().getTerminalLogIn());
					///--------
					selectReciboTemp = reciboId;
					selectAnnioRecord = 0;
			        selectPersonaTemp = 0;
			        prediosSelect = "";
			        
					//selectPersonaTemp = referencia.getPersonaId();
				}
			
				/** Verificando el pago */
				cajaBo.verificarRecibo(reciboId);
						
				correct = true;
				getSessionManaged().getSessionMap().put("caja.imprimirecibo.reciboId", reciboId);
				lstPagodetalle = new ArrayList<CjReciboDetalle>();
				// addInfoMessage("El pago se ha realizado con éxito");

				// reinicializando los datos de los formularios
				initContent();

			} else if (this.montoIngresado != null && this.montoIngresado.doubleValue() >= 0
					&& this.montoIngresado.compareTo(montoTupa) < 0) {
				addErrorMessage("Por favor, ingrese el monto completo");
			} else if (this.lstTupaDetalle != null || this.lstTupaDetalle.size() > 0) {
				addErrorMessage("Por favor, seleccione al menos una tasa de TUPA");
			} else {
				addErrorMessage("Por favor, ingrese el monto");
			}
		} catch (SisatException ex) {
			addErrorMessage(ex.getMessage());
		} catch (Exception ex) {
			String msg = "No se ha podido registrar el pago";
			addErrorMessage(msg);
			System.out.println(msg + ex);
		}
		return null;
	}
	
	
	
	
	 public void impresion(int personaId,int reciboId_) {
			try {

				String cadena = null;
				java.sql.Connection connection = null;
				cadena = "reporte_constacia_no_adeudo.jasper";

				CrudServiceBean connj = CrudServiceBean.getInstance();
				connection = connj.connectJasper();
				String path_context = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
				String path_report = path_context + "/sisat/reportes/";
				String path_imagen = path_context + "/sisat/reportes/imagen/";
				HashMap<String, Object> parameters = new HashMap<String, Object>();
				
				Integer persona = personaId;
				
				Integer recibo = reciboId_;
				
				parameters.put("persona_id", persona);
				parameters.put("recibo_id", recibo);
				parameters.put("ruta_imagen", path_imagen);
				parameters.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						(SATWEBParameterFactory.getPathReporte() + cadena),
						parameters, connection);
				
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				HttpServletResponse response = (HttpServletResponse) FacesContext
						.getCurrentInstance().getExternalContext()
						.getResponse();
					JasperExportManager.exportReportToPdfStream(jasperPrint,output);
				
					response.setContentType("application/pdf");
					response.addHeader("Content-Disposition","attachment;filename=" + cadena.replaceAll(".jasper", ".pdf"));

				response.setContentLength(output.size());
				ServletOutputStream servletOutputStream = response.getOutputStream();
				
				servletOutputStream.write(output.toByteArray(), 0,output.size());
				
				servletOutputStream.flush();
				servletOutputStream.close();
				FacesContext.getCurrentInstance().responseComplete();
			
			
			} catch (Exception e) {
				e.printStackTrace();
				WebMessages.messageFatal(e);
			}
		}

	// TUPA
//	public String agregarFilaTupa() {
//		try {
//			Integer id = mapCjTupa.get(selectedTupa);
//			if (id != null) {
//				CjTupaDTO oDetalle = mapICjTupa.get(id);
//				getLstTupaDetalle().add(oDetalle); // not validate exist
//				selectedTupa = null;
//				calcularTotalTupa();
//			}
//		} catch (Exception ex) {
//			String msg = "Ha ocurrido un error agregando la fila";
//			System.out.println(msg + ex);
//		}
//		return null;
//	}

	public String agregarFilaTupa2() {
		try {

			if (tupaSeleccionado != null && tupaSeleccionado.getTasa() != null && buscarRegistroTupa()==false) {
				tupaSeleccionado.setDescuento(this.descuento);
				tupaSeleccionado.setSubTotal(tupaSeleccionado.getTasa());
				tupaSeleccionado.setTotalDescuento(this.descuento.multiply(tupaSeleccionado.getSubTotal()));
				tupaSeleccionado.setSubTotal(tupaSeleccionado.getSubTotal().subtract(tupaSeleccionado.getTotalDescuento()));
				//--cramirez-- 
				if(tupaSeleccionado.getTupaId() == Constante.TUPA_CONSTANCIA_NO_ADEUDO) {
					this.isConstancia = true;
					if(referencia.getPersonaId() != null) {
						this.lstECRes = personaBo.getEstadoCuentaResumido(referencia.getPersonaId());
						
						RegistrarConstanciaNoAdeudo registrarConstanciaNoAdeudo = (RegistrarConstanciaNoAdeudo) this.getManaged("registrarConstanciaNoAdeudo");
						registrarConstanciaNoAdeudo.setPersonaId(referencia.getPersonaId());
						registrarConstanciaNoAdeudo.viewdetalle();
						
					}
				}
				if(tupaSeleccionado.getTupaId() == Constante.TUPA_RECORD_HR_PU_PR) {
					
					isRecord = true;
					
					getAniosHR();
					
				}
				//----------- 
				getLstTupaDetalle().add(tupaSeleccionado); // not validate exist
				tupaSeleccionado = new CjTupaDTO();
				descripcionTupaSeleccionado = null;
				calcularTotalTupa();
			}else{
				WebMessages.messageError("Debe seleccionar un tasa TUPA valida o ya elegiste esa tasa.");
			}
		} catch (Exception ex) {
			WebMessages.messageError(ex.getMessage());
		}
		return null;
	}
	
	
	
	private boolean buscarRegistroTupa(){		
		boolean resultado=false;		
			for( int k = 0 ; k  < this.lstTupaDetalle.size(); k++){
				if(lstTupaDetalle.get(k).getTupaId()==tupaSeleccionado.getTupaId()){					
					resultado=true;
					break;
				}
			}						
		return resultado;
		
	}

	public void eliminarRegistroTupa() {
		try {
			
			if (tupaSeleccionadoListaTupa != null) {
				tupaSeleccionadoListaTupa.setSubTotal(tupaSeleccionadoListaTupa.getTasa());
				tupaSeleccionadoListaTupa.setCant(1);
				//--cramirez-- 
				if(tupaSeleccionadoListaTupa.getTupaId() == Constante.TUPA_CONSTANCIA_NO_ADEUDO) {
					this.isConstancia = false;
				}
				if(tupaSeleccionado.getTupaId() == Constante.TUPA_RECORD_HR_PU_PR) {
					isRecord = false;
				}
				//----------- 
				lstTupaDetalle.remove(tupaSeleccionadoListaTupa);
				calcularTotalTupa();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void calcularTotalTupa() {
		montoTupa = new BigDecimal("0");
		total = new BigDecimal("0");
		totalDescuento = new BigDecimal("0");		
		for (CjTupaDTO tu : getLstTupaDetalle()) {
			montoTupa = montoTupa.add(tu.getSubTotal());
			total=total.add(tu.getSubTotal().add(tu.getTotalDescuento()));
			totalDescuento=totalDescuento.add(tu.getTotalDescuento());
		}
	}
	public void agregarDescuento() {		
		montoTupa = new BigDecimal("0");
		totalDescuento= new BigDecimal("0");				
		for (CjTupaDTO tu : getLstTupaDetalle()) {
			tu.setDescuento(this.descuento);	
			tu.setSubTotal(tu.getTasa().multiply(new BigDecimal(tu.getCant())));
			tu.setTotalDescuento(this.descuento.multiply(tu.getSubTotal()));
			tu.setSubTotal(tu.getSubTotal().subtract(tu.getTotalDescuento()));
			montoTupa = montoTupa.add(tu.getSubTotal());
			totalDescuento=totalDescuento.add(tu.getTotalDescuento());
		}
		//this.agregarCantidades();
	}	
	

	public void agregarCantidades() {

		montoTupa = new BigDecimal("0");
		totalDescuento= new BigDecimal("0");
		total= new BigDecimal("0");
		
		for (CjTupaDTO tu : getLstTupaDetalle()) {
			montoTupa = montoTupa.add(tu.getSubTotal());
			totalDescuento=totalDescuento.add(tu.getTotalDescuento());
			total=total.add(tu.getSubTotal().add(tu.getTotalDescuento()));
			//montoTupa=new BigDecimal("500.00");
		}
		
		BigDecimal dif = montoIngresado.subtract(montoTupa);
		if (dif.doubleValue() >= 0) {
			montoVuelto = dif;
			montoResta = null;
		} else {
			montoResta = dif.multiply(new BigDecimal("-1"));
			montoVuelto = null;
		}
	}
	
	public void cambiarTasa(int tupaId, BigDecimal newTasa) {
		
		for (CjTupaDTO tu : getLstTupaDetalle()) {
			if(tu.getTupaId() == tupaId) {
				tu.setTasa(newTasa);
				//calcularTotalTupa();
				tu.setCant(1);
				agregarCantidades();
			}
		}
	}

	public void getAniosHR(){
		
		String tipoPredio = "U";
		if(tipoRecord == 3 ) {
			tipoPredio = "R";
		}
		
		if(isRecord && this.referencia.getPersonaId() != null ) {
			try {
				listAnioHR = cajaBo.obtenerAnio(this.referencia.getPersonaId(),tipoPredio);
				
				Iterator<CrGeneralDTO> it3 = listAnioHR.iterator();
				while (it3.hasNext()) {
					CrGeneralDTO obj = it3.next();
					obj.setSeleted(false);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			listAnioHR = null;
		}
		
		listPrediosHR = null;
		cantRecord = 0;
		cargarCantRecord();
		prediosSelect = "";
		
	}
	
	public void changeSelectAnio(int item){
		//String nv = ev.getNewValue().toString();		
		List<CrGeneralDTO> listAnioHRTemp =  new ArrayList<CrGeneralDTO>();
		
		Iterator<CrGeneralDTO> it3 = listAnioHR.iterator();
		
		while (it3.hasNext()) {
			CrGeneralDTO obj = it3.next();
			if(obj.getId() == item) {
				obj.setSeleted(true);
				selectAnnioRecord = Integer.parseInt(obj.getDescripcion());
				
			}else {
				obj.setSeleted(false);
			}
			
			listAnioHRTemp.add(obj);
		}
		
		listAnioHR = new ArrayList<CrGeneralDTO>();
		listAnioHR = listAnioHRTemp;
		
		//================ Cargar Lista Predios ==============
			try {
				listPrediosHR = cajaBo.obtenerPredios(this.referencia.getPersonaId(),selectAnnioRecord);
				
				Iterator<CrGeneralDTO> it4 = listPrediosHR.iterator();
				while (it3.hasNext()) {
					CrGeneralDTO obj = it4.next();
					obj.setSeleted(false);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//====================================================
		
  	}
	
	public void selectpredio() {
		cantRecord = 0;
		
		Iterator<CrGeneralDTO> it1 = listPrediosHR.iterator();
				
		while (it1.hasNext()) {
			CrGeneralDTO obj = it1.next();
			if(obj.isSeleted()) {
				cantRecord++;
			}
		}
			
		
		cargarCantRecord();
	}
	
	public void cargarCantRecord() {
		//==================================
		List<CjTupaDTO> tempLstTupaDetalle = new ArrayList<CjTupaDTO>();
		
			Iterator<CjTupaDTO> it2 = lstTupaDetalle.iterator();		
			while (it2.hasNext()) {
				CjTupaDTO obj = it2.next();
				
				if(obj.getTupaId() == Constante.TUPA_RECORD_HR_PU_PR) {
					if(cantRecord == 0 ) {
						obj.setCant(1);
					}else {
						obj.setCant(cantRecord);
					}
				}
				
				tempLstTupaDetalle.add(obj);
			}
			
			lstTupaDetalle = new ArrayList<CjTupaDTO>();
			lstTupaDetalle = tempLstTupaDetalle;
			agregarCantidades();
			
		//==================================
	}

	public List<PagoTupaReferenciaDTO> buscarPersonaContribuyente(Object p) {

		String prefix = (String) p;

		lst.clear();

		try {
			lst = cajaBo.buscarPersonaContribuyente(prefix);
		} catch (SisatException e) {

			WebMessages.messageError(e.getMessage());
		}

		return lst;

	}

	public List<CjTupaDTO> buscarTupa(Object p) {
		String prefix = (String) p;

		List<CjTupaDTO> result = new ArrayList<CjTupaDTO>();
		String valor;
		String consulta;

		for (CjTupaDTO elem : listCjTupaDTOs) {

			valor = elem.getDescripcion().toUpperCase();
			consulta = prefix.toUpperCase();
			if ((valor.indexOf(consulta) >= 0) || "".equals(prefix)) {
				result.add(elem);
			}
		}
		return result;
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
		if (selectedFormaPagoId == Constante.FORMA_PAGO_CHEQUE) {
			labelTarjetaCheque = "cheque";
		} else {
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

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		if (monto != null) {
			this.montoSoles = monto.multiply(tipoCambio.getValorVenta());
			this.montoSoles = montoSoles.setScale(2, RoundingMode.HALF_UP);
			if (selectedFormasPago.equals(Constante.FORMAS_PAGO_EFECTIVO)) {
				this.montoIngresado = montoSoles.setScale(2, RoundingMode.HALF_UP);
				BigDecimal dif = montoIngresado.subtract(montoTupa);
				if (dif.doubleValue() >= 0) {
					montoVuelto = dif;
					montoResta = null;
				} else {
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
		if (montoSoles != null) {
			if (selectedFormasPago.equals(Constante.FORMAS_PAGO_EFECTIVO)) {
				this.montoIngresado = montoSoles.setScale(2, RoundingMode.HALF_UP);
				BigDecimal dif = montoIngresado.subtract(montoTupa);
				if (dif.doubleValue() >= 0) {
					montoVuelto = dif;
					montoResta = null;
				} else {
					montoResta = dif.multiply(new BigDecimal("-1"));
					montoVuelto = null;
				}
			}
		}
		this.montoSoles = montoSoles;
	}	

	public void edit() {
		try {
		
			getSessionManaged().getSessionMap().put("recibo", getRecibo());
		
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		

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

//	public String getSelectedTupa() {
//		return selectedTupa;
//	}
//
//	public void setSelectedTupa(String selectedTupa) {
//		this.selectedTupa = selectedTupa;
//	}

	public List<SelectItem> getLstTupa() {
		return lstTupa;
	}

	public List<CjTupaDTO> getLstTupaDetalle() {
		return lstTupaDetalle;
	}

	public BigDecimal getMontoTupa() {
		return montoTupa;
	}

	public void setMontoTupa(BigDecimal montoTupa) {
		this.montoTupa = montoTupa;
	}

	public PagoTupaReferenciaDTO getReferencia() {
		if (referencia == null) {
			referencia = new PagoTupaReferenciaDTO();
		}
		return referencia;
	}

	public void setReferencia(PagoTupaReferenciaDTO referencia) {
		this.referencia = referencia;
	}	

	public CjTupaDTO getTupaSeleccionado() {
		return tupaSeleccionado;
	}

	public void setTupaSeleccionado(CjTupaDTO tupaSeleccionado) {
		this.tupaSeleccionado = tupaSeleccionado;
	}

	public Boolean getPermanecerReferencia() {
		return permanecerReferencia;
	}

	public void setPermanecerReferencia(Boolean permanecerReferencia) {
		this.permanecerReferencia = permanecerReferencia;
	}

	public Boolean getPermanecerTupa() {
		return permanecerTupa;
	}

	public void setPermanecerTupa(Boolean permanecerTupa) {
		this.permanecerTupa = permanecerTupa;
	}

	public String getDescripcionTupaSeleccionado() {
		return descripcionTupaSeleccionado;
	}

	public void setDescripcionTupaSeleccionado(String descripcionTupaSeleccionado) {
		this.descripcionTupaSeleccionado = descripcionTupaSeleccionado;
	}

	public CjTupaDTO getTupaSeleccionadoListaTupa() {
		return tupaSeleccionadoListaTupa;
	}

	public void setTupaSeleccionadoListaTupa(CjTupaDTO tupaSeleccionadoListaTupa) {
		this.tupaSeleccionadoListaTupa = tupaSeleccionadoListaTupa;
	}

	public BigDecimal getDescuento() {
		return descuento;
	}

	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getTotalDescuento() {
		return totalDescuento;
	}

	public void setTotalDescuento(BigDecimal totalDescuento) {
		this.totalDescuento = totalDescuento;
	}

	public CrConstanciaNoAdeudo getConstancia() {
//		if (constancia == null) {
//			constancia = new ConstanciaNoAdeudo();
//		}
		return constancia;
	}

	public void setConstancia(CrConstanciaNoAdeudo constancia) {
		this.constancia = constancia;
	}

	public Boolean getTipo() {
		return tipo;
	}

	public void setTipo(Boolean tipo) {
		this.tipo = tipo;
	}

	public Boolean getIsConstancia() {
		return isConstancia;
	}

	public void setIsConstancia(Boolean isConstancia) {
		this.isConstancia = isConstancia;
	}

	public List<PersonaEstadoCuentaResumido> getLstECRes() {
		return lstECRes;
	}

	public void setLstECRes(List<PersonaEstadoCuentaResumido> lstECRes) {
		this.lstECRes = lstECRes;
	}

	public Boolean getIsRecord() {
		return isRecord;
	}

	public void setIsRecord(Boolean isRecord) {
		this.isRecord = isRecord;
	}

	public int getTipoRecord() {
		return tipoRecord;
	}

	public void setTipoRecord(int tipoRecord) {
		this.tipoRecord = tipoRecord;
	}

	public List<CrGeneralDTO> getListAnioHR() {
		return listAnioHR;
	}

	public void setListAnioHR(List<CrGeneralDTO> listAnioHR) {
		this.listAnioHR = listAnioHR;
	}

	public List<CrGeneralDTO> getListPrediosHR() {
		return listPrediosHR;
	}

	public void setListPrediosHR(List<CrGeneralDTO> listPrediosHR) {
		this.listPrediosHR = listPrediosHR;
	}

	public int getRecordId() {
		return recordId;
	}

	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}
	
	
}
