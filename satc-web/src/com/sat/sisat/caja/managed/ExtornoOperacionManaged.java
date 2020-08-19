package com.sat.sisat.caja.managed;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.richfaces.component.html.HtmlComboBox;

import com.sat.sisat.caja.business.CajaBoRemote;
import com.sat.sisat.caja.dto.CjMotivos;
import com.sat.sisat.caja.dto.CjReciboDetalleEntity;
import com.sat.sisat.caja.dto.CjReciboEntity;
import com.sat.sisat.caja.dto.CjReciboPagoEntity;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.persistence.entity.CjReciboDetalle;

@ManagedBean
@ViewScoped
public class ExtornoOperacionManaged extends BaseManaged implements Serializable{ 
	
	@EJB
	CajaBoRemote cajaBo;
	
	private int codigoContribuyente;
	private String nombreContribuyente;
	private BigDecimal montoPago;
	private Timestamp fechaRecibo;
	private String txtObs_observacion;
	private boolean estadoBoton;
	
	private ArrayList<CjReciboDetalleEntity> lstReciboDetalle;
	private ArrayList<CjReciboPagoEntity> lstReciboPago;

	//cargar combo
	private HtmlComboBox cmbMotivoExtorno;
	private List<SelectItem> lstExtorno = new ArrayList<SelectItem>();
	private HashMap<String, Integer>  mapCjMotivos = new HashMap<String, Integer>();
	
	private ArrayList<CjReciboEntity> records;
	private List<CjReciboDetalle> lstPagodetalle = new ArrayList<CjReciboDetalle>();

	public ExtornoOperacionManaged(){
		
		getSessionManaged().setLinkRegresar("/sisat/caja/extornoLista.xhtml");
	}
				
	@PostConstruct
	public void init(){   
	
		Tipo();   
		
		//Muestra datos del recibo
		CjReciboEntity oRecibo  = (CjReciboEntity)getSessionMap().get("CjReciboEntity");
		int recibo_id =  oRecibo.getReciboId();
		CjReciboEntity oReciboPago = cajaBo.ObtenerDatosRecibo(recibo_id);
		if(oReciboPago!=null){
			setCodigoContribuyente(oReciboPago.getPersonaId());
			setNombreContribuyente(oReciboPago.getNombrePersona());
			setMontoPago(oReciboPago.getMontoCobrado());
			setFechaRecibo(oReciboPago.getFechaRecibo());
		}

		lstReciboDetalle = cajaBo.ObtenerDatosReciboDetalle(recibo_id);
		lstReciboPago = cajaBo.ObtenerDatosReciboPago(recibo_id);
		
		if (oReciboPago.getEstado().equals("2")) setEstadoBoton(true);
		else setEstadoBoton(false);
	}
	
	// Motivo de extorno
	public void Tipo(){
		try {
			List<CjMotivos> lstME = new ArrayList<CjMotivos>();
			lstME = cajaBo.obtenerMotivoExtorno();
			Iterator<CjMotivos> itAd = lstME.iterator();
			
			while (itAd.hasNext()) {
				CjMotivos objAd = itAd.next();
				lstExtorno.add(new SelectItem(objAd.getDescripcionExtorno(),objAd.getMotivo_extorno_id() +""));
				mapCjMotivos.put(objAd.getDescripcionExtorno(),objAd.getMotivo_extorno_id());
				
			
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

			
	
	public void ExtornarPago(){
		try {
			
			CjReciboEntity oRecibo  = (CjReciboEntity)getSessionMap().get("CjReciboEntity");
			 int recibo_id =  oRecibo.getReciboId();
			 int usuario_id = getSessionManaged().getUsuarioLogIn().getUsuarioId();
			 String terminal = getSessionManaged().getTerminalLogIn();
			 String obs_extorno = txtObs_observacion;
			 int result = cajaBo.ExtornarPago(recibo_id, usuario_id,  obs_extorno, terminal);
			 addInfoMessage("Proceso realizado satisfactoriamente"); 
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		
		
	}    
	
	
	public String salir() {
		return sendRedirectPrincipal();
	}

	public void limpiar() {



	}


	public String getNombreContribuyente() {
		return nombreContribuyente;
	}

	public void setNombreContribuyente(String nombreContribuyente) {
		this.nombreContribuyente = nombreContribuyente;
	}

	public BigDecimal getMontoPago() {
		return montoPago;
	}

	public void setMontoPago(BigDecimal montoPago) {
		this.montoPago = montoPago;
	}
	public int getCodigoContribuyente() {
		return codigoContribuyente;
	}

	public void setCodigoContribuyente(int codigoContribuyente) {
		this.codigoContribuyente = codigoContribuyente;
	}


	public HtmlComboBox getCmbMotivoExtorno() {
		return cmbMotivoExtorno;
	}


	public void setCmbMotivoExtorno(HtmlComboBox cmbMotivoExtorno) {
		this.cmbMotivoExtorno = cmbMotivoExtorno;
	}

	public HashMap<String, Integer> getMapCjMotivos() {
		return mapCjMotivos;    
	}

	public void setMapCjMotivos(HashMap<String, Integer> mapCjMotivos) {
		this.mapCjMotivos = mapCjMotivos;
	}

	public List<SelectItem> getLstExtorno() {
		return lstExtorno;
	}

	public void setLstExtorno(List<SelectItem> lstExtorno) {
		this.lstExtorno = lstExtorno;
	}

	public Timestamp getFechaRecibo() {
		return fechaRecibo;
	}

	public void setFechaRecibo(Timestamp fechaRecibo) {
		this.fechaRecibo = fechaRecibo;
	}

	public ArrayList<CjReciboDetalleEntity> getLstReciboDetalle() {
		return lstReciboDetalle;
	}

	public void setLstReciboDetalle(ArrayList<CjReciboDetalleEntity> lstReciboDetalle) {
		this.lstReciboDetalle = lstReciboDetalle;
	}

	public ArrayList<CjReciboPagoEntity> getLstReciboPago() {
		return lstReciboPago;
	}

	public void setLstReciboPago(ArrayList<CjReciboPagoEntity> lstReciboPago) {
		this.lstReciboPago = lstReciboPago;
	}

	public ArrayList<CjReciboEntity> getRecords() {
		return records;
	}

	public void setRecords(ArrayList<CjReciboEntity> records) {
		this.records = records;
	}
	public List<CjReciboDetalle> getLstPagodetalle() {
		return lstPagodetalle;
	}

	public void setLstPagodetalle(List<CjReciboDetalle> lstPagodetalle) {
		this.lstPagodetalle = lstPagodetalle;
	}

	public String getTxtObs_observacion() {
		return txtObs_observacion;
	}

	public void setTxtObs_observacion(String txtObs_observacion) {
		this.txtObs_observacion = txtObs_observacion;
	}

	public boolean getEstadoBoton() {
		return estadoBoton;
	}

	public void setEstadoBoton(boolean estadoBoton) {
		this.estadoBoton = estadoBoton;
	}

}



	
