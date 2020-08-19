package com.sat.sisat.caja.managed;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.richfaces.component.html.HtmlComboBox;

import com.sat.sisat.caja.business.CajaBoRemote;
import com.sat.sisat.caja.dto.CjCajaCuadreEntity;
import com.sat.sisat.caja.dto.CjMotivos;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.WebMessages;

@ManagedBean
@ViewScoped
public class CajaCuadreManaged extends BaseManaged implements Serializable{  

	private static final long serialVersionUID = -2300055633609016331L;

	@EJB
	CajaBoRemote cajaBo;
	
	private HtmlComboBox cmbMotivoCuadre;  
	private List<SelectItem> lstMotivoCuadre = new ArrayList<SelectItem>();
	private HashMap<String, Integer>  mapCjMotivos = new HashMap<String, Integer>();
	private int motivoCuadreId;
	private ArrayList<CjCajaCuadreEntity> records = new ArrayList<CjCajaCuadreEntity>();  //----
	private boolean estadoBoton;
	
	private BigDecimal monTotRec = new BigDecimal("0");
	private BigDecimal monTotIng = new BigDecimal("0");
	private BigDecimal monTotDif = new BigDecimal("0");
	
	public CajaCuadreManaged(){
		getSessionManaged().setLinkRegresar("/sisat/caja/CajaCuadre.xhtml");
	}
	
	@PostConstruct
	public void init(){
		setEstadoBoton(true);
		Tipo();  
		try {
			int usuario_id =  getSessionManaged().getUsuarioLogIn().getUsuarioId();
			records = cajaBo.ObtenerOperacionesCuadre(usuario_id);
			calcularMonTotRec();
		} catch (Exception ex) {
			WebMessages.messageError(ex.getMessage());
		}
	}
	
	private void calcularMonTotRec(){
		monTotRec = new BigDecimal("0");
		for(CjCajaCuadreEntity cc : records){
			monTotRec = monTotRec.add(cc.getMontoTotal());
		}
	}
	
	private void calcularMonTotIng(){
		monTotIng = new BigDecimal("0");
		for(CjCajaCuadreEntity cc : records){
			monTotIng = monTotIng.add(cc.getMontoCaja());
		}
	}
	
	private void calcularMonTotDif(){
		monTotDif = new BigDecimal("0");
		for(CjCajaCuadreEntity cc : records){
			monTotDif = monTotDif.add(cc.getMontoDiferencia());
		}
	}

	public void Tipo(){
		try {
			List<CjMotivos> lstME = new ArrayList<CjMotivos>();
			lstME = cajaBo.obtenerMotivoCuadre();
			Iterator<CjMotivos> itAd = lstME.iterator();
			  
			while (itAd.hasNext()) {
				CjMotivos objAd = itAd.next();
				lstMotivoCuadre.add(new SelectItem(objAd.getDescripcionCierreCaja(),objAd.getMotivo_cuadre_id() +""));
				mapCjMotivos.put(objAd.getDescripcionCierreCaja(),objAd.getMotivo_cuadre_id());  
			}
		} catch (Exception e) {
			e.printStackTrace();  
		}
	}
	
	public void grabarCuadre(){
		try {

			if (!validoMotivo()) {
				return;
			}

			int usuarioId = getSessionManaged().getUsuarioLogIn().getUsuarioId();
			String terminal = getSessionManaged().getTerminalLogIn();

			ArrayList<CjCajaCuadreEntity> listaCuadre = new ArrayList<CjCajaCuadreEntity>();
			for (CjCajaCuadreEntity item : records) {
				if (item.getMontoTotal().compareTo(BigDecimal.ZERO) == 1) {
					CjCajaCuadreEntity oCuadre = new CjCajaCuadreEntity();
					oCuadre.setMotivoCuadreId(motivoCuadreId);
					oCuadre.setTipoCuadreId(Constante.TIPO_CUADRE_SISTEMA);
					oCuadre.setFormaPagoId(item.getFormaPagoId());
					oCuadre.setMontoCuadre(item.getMontoTotal());
					oCuadre.setAperturaId(item.getAperturaId());
					oCuadre.setUsuarioId(usuarioId);
					oCuadre.setTerminal(terminal);
					oCuadre.setEstado(Constante.ESTADO_ACTIVO);
					listaCuadre.add(oCuadre);
				}

				if (item.getMontoCaja().compareTo(BigDecimal.ZERO) == 1) {
					CjCajaCuadreEntity oCuadre = new CjCajaCuadreEntity();
					oCuadre.setMotivoCuadreId(motivoCuadreId);
					oCuadre.setTipoCuadreId(Constante.TIPO_CUADRE_CAJA);
					oCuadre.setFormaPagoId(item.getFormaPagoId());
					oCuadre.setMontoCuadre(item.getMontoCaja());
					oCuadre.setAperturaId(item.getAperturaId());
					oCuadre.setUsuarioId(usuarioId);
					oCuadre.setTerminal(terminal);
					oCuadre.setEstado(Constante.ESTADO_ACTIVO);
					listaCuadre.add(oCuadre);
				}

				if (item.getMontoDiferencia().compareTo(BigDecimal.ZERO) != 0) {
					CjCajaCuadreEntity oCuadre = new CjCajaCuadreEntity();
					oCuadre.setMotivoCuadreId(motivoCuadreId);
					oCuadre.setTipoCuadreId(item.getTipoCuadreId()); // Tipo Cuadre 3 , 4 -->
																		// Diferencia
					oCuadre.setFormaPagoId(item.getFormaPagoId());
					oCuadre.setMontoCuadre(item.getMontoCaja());
					oCuadre.setAperturaId(item.getAperturaId());
					oCuadre.setUsuarioId(usuarioId);
					oCuadre.setTerminal(terminal);
					oCuadre.setEstado(Constante.ESTADO_ACTIVO);
					listaCuadre.add(oCuadre);
				}
			}
			boolean save = cajaBo.grabarCuadre(listaCuadre);
			if (save) {
				addInfoMessage("Proceso culminado satisfactotiamente");
			} else {
				addErrorMessage("No se ha podido guardar el cuadre de caja");
			}
		} catch (Exception ex) {
			String msg = "No se ha podido guardar el cuadre de caja";
			addErrorMessage(msg);
			System.out.println(msg + ex);
		}
	}

	public void ProbarCuadre(){
		boolean caja_cuadre_ok = true;
		ArrayList<CjCajaCuadreEntity> listaCuadre = getRecords();
		
		for (CjCajaCuadreEntity oCajaCuadre : listaCuadre) {
			BigDecimal monto_diferencia = oCajaCuadre.getMontoTotal().subtract(oCajaCuadre.getMontoCaja());
			int valor = oCajaCuadre.getMontoTotal().compareTo(oCajaCuadre.getMontoCaja());
			switch(valor){
				case 0:
					caja_cuadre_ok=true;
					oCajaCuadre.setFaltanteSobrante("");
					oCajaCuadre.setTipoCuadreId(2);
					break;
				case -1:
					caja_cuadre_ok=false;
					//oCajaCuadre.setFaltanteSobrante("SOBRANTE");
					oCajaCuadre.setFaltanteSobrante("");
					oCajaCuadre.setTipoCuadreId(3);
					monto_diferencia = monto_diferencia.multiply(new BigDecimal(-1));
					break;
				case 1:
					caja_cuadre_ok=false;
					oCajaCuadre.setFaltanteSobrante("FALTANTE");
					oCajaCuadre.setTipoCuadreId(4);
					break;
			}		
			oCajaCuadre.setMontoDiferencia(monto_diferencia);
		}
		if (caja_cuadre_ok==true){
			setEstadoBoton(false);// boton activado	
		}else{
			setEstadoBoton(true);// boton desactivado
		}
		
		//records.clear();
		records = listaCuadre;
		calcularMonTotIng();
		calcularMonTotDif();
	}
	
	private boolean validoMotivo() {
		Object cuadre = cmbMotivoCuadre.getValue();
		if (cuadre == null || cuadre.toString().isEmpty()) {
			addErrorMessage("Seleccionar motivo de cuadre");
			return false;
		} else {
			motivoCuadreId = mapCjMotivos.get(cuadre.toString());
			return true;
		}
	} 
	
	public void iniciaListaOperaciones(){
		ListaOperacionManaged listaOperaciones = (ListaOperacionManaged) getManaged("listaOperacionManaged");
		if(listaOperaciones!=null){
			listaOperaciones.buscar();	
		}
	}
	
	public void limpiar() {
		//	private ArrayList<CjCajaCuadreEntity> records;  
		records = new ArrayList<CjCajaCuadreEntity>();
		//lstPagodetalle.removeAll(lstPagodetalle);
	}
	
	public void GraboCorrectoCuadre(ActionEvent ev){
		getSessionManaged().sendRedirectPrincipalListener();
	}
	
	public ArrayList<CjCajaCuadreEntity> getRecords() {
		return records;
	}
	
	public void setRecords(ArrayList<CjCajaCuadreEntity> records) {
		this.records = records;
	}
	
	public HashMap<String, Integer> getMapCjMotivos() {
		return mapCjMotivos;
	}
	
	public void setMapCjMotivos(HashMap<String, Integer> mapCjMotivos) {
		this.mapCjMotivos = mapCjMotivos;
	}
	
	public HtmlComboBox getCmbMotivoCuadre() {
		return cmbMotivoCuadre;
	}
	
	public void setCmbMotivoCuadre(HtmlComboBox cmbMotivoCuadre) {
		this.cmbMotivoCuadre = cmbMotivoCuadre;
	}
	
	public List<SelectItem> getLstMotivoCuadre() {
		return lstMotivoCuadre;
	}
	
	public void setLstMotivoCuadre(List<SelectItem> lstMotivoCuadre) {
		this.lstMotivoCuadre = lstMotivoCuadre;
	}
	
	public int getMotivoCuadreId() {
		return motivoCuadreId;
	}
	
	public void setMotivoCuadreId(int motivoCuadreId) {
		this.motivoCuadreId = motivoCuadreId;
	}
	
	public boolean isEstadoBoton() {
		return estadoBoton;
	}
	
	public void setEstadoBoton(boolean estadoBoton) {
		this.estadoBoton = estadoBoton;
	}

	public BigDecimal getMonTotRec() {
		return monTotRec;
	}

	public void setMonTotRec(BigDecimal monTotRec) {
		this.monTotRec = monTotRec;
	}

	public BigDecimal getMonTotIng() {
		return monTotIng;
	}

	public void setMonTotIng(BigDecimal monTotIng) {
		this.monTotIng = monTotIng;
	}

	public BigDecimal getMonTotDif() {
		return monTotDif;
	}

	public void setMonTotDif(BigDecimal monTotDif) {
		this.monTotDif = monTotDif;
	}
}
