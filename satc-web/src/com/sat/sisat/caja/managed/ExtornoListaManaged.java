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
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.richfaces.component.html.HtmlComboBox;

import com.sat.sisat.caja.business.CajaBoRemote;
import com.sat.sisat.caja.dto.CjMotivos;
import com.sat.sisat.caja.dto.CjReciboEntity;
import com.sat.sisat.caja.dto.CjTipoDocumento;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.FacesUtil;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.persistence.entity.CjReciboDetalle;

@ManagedBean
@ViewScoped
public class ExtornoListaManaged extends BaseManaged implements Serializable{

	@EJB
	CajaBoRemote cajaBo;
	
	private String codigoContribuyente;
	private String nombreContribuyente;
	private BigDecimal montoExtorno;
	private int recibo_id;
	
	//para lo de validar los campos
	private String nro_recibo; 
    private String tipoDoc;
    private String nroDocum;
    private Timestamp fechaInicio;
    private Timestamp fechaFin;
    private List<SelectItem> lstTipoDoc = new ArrayList<SelectItem>();
    private String cmbValTipoDocumento;
    private HashMap<String, Integer>  mapMpTipoDocumento = new HashMap<String, Integer>();
    private HtmlComboBox cmbtipoDoc;

	private int selectedOptBusc = 1;

	//cargar combo
	private HtmlComboBox cmbMotivoExtorno;
	private List<SelectItem> lstExtorno = new ArrayList<SelectItem>();
	private HashMap<String, Integer>  mapCjMotivos = new HashMap<String, Integer>();
	
	private ArrayList<CjReciboEntity> records;
	private List<CjReciboDetalle> lstPagodetalle = new ArrayList<CjReciboDetalle>();
	
	private CjReciboEntity currentItem;
	
	public ExtornoListaManaged(){
//		System.out.println("SE CREO EL BEAN");
		getSessionManaged().setLinkRegresar("/sisat/caja/extornoLista.xhtml");	
	}
				
	@PostConstruct
	public void init(){
		try {
			List<CjTipoDocumento> lstTD = new ArrayList<CjTipoDocumento>();
			lstTD = cajaBo.obtenerTipoDocumento();
			Iterator<CjTipoDocumento> itAd = lstTD.iterator();
			while (itAd.hasNext()) {
				CjTipoDocumento objAd = itAd.next();
				lstTipoDoc.add(new SelectItem(objAd.getDescripcioncorta(),objAd.getTipoDoc() + ""));
				mapMpTipoDocumento.put( objAd.getDescripcioncorta(), objAd.getTipoDoc());    
			}
		} catch (Exception ex) {
			ex.printStackTrace();

		}
		Tipo();
	}
	
	public String seleccionaExtorno() {//paso el SET***********
		try {
			if (currentItem != null) {
				//FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(),"cjPersona", currentItem);
				FacesUtil.setSessionMapValue(FacesContext.getCurrentInstance(),"CjReciboEntity", currentItem);
				
																
				
			} else {
				// selecciones
			}			
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
			
		}
		return null;
	}
	
	public void buscar(ActionEvent ev){
		try {
			String nroRecibo = String.valueOf(getNro_recibo());
			int personaId = 0;  
			if (!(getCodigoContribuyente().isEmpty())){				
				personaId = Integer.valueOf(getCodigoContribuyente()); 
			}
			int tipoDocumentoId = 0;
			String nroDocumento = getNroDocum();	
			records= cajaBo.ObtenerListaOperacion(nroRecibo, personaId,  tipoDocumentoId,  nroDocumento, fechaInicio,  fechaFin);
			setRecords(records);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();  
			addFatalMessage(e.getMessage());
		}
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
		}
	}

	public void limpiar() {



	}
	public int getRecibo_id() {
		return recibo_id;
	}


	public void setRecibo_id(int recibo_id) {
		this.recibo_id = recibo_id;
	}

	public List<CjReciboDetalle> getLstPagodetalle() {
		return lstPagodetalle;
	}

	public void setLstPagodetalle(List<CjReciboDetalle> lstPagodetalle) {
		this.lstPagodetalle = lstPagodetalle;
	}

	public ArrayList<CjReciboEntity> getRecords() {
		return records;
	}

	public void setRecords(ArrayList<CjReciboEntity> records) {
		this.records = records;
	}

	public String getNombreContribuyente() {
		return nombreContribuyente;
	}

	public void setNombreContribuyente(String nombreContribuyente) {
		this.nombreContribuyente = nombreContribuyente;
	}

	public BigDecimal getMontoExtorno() {
		return montoExtorno;
	}

	public void setMontoExtorno(BigDecimal montoExtorno) {
		this.montoExtorno = montoExtorno;
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

	public CjReciboEntity getCurrentItem() {
		return currentItem;
	}

	public void setCurrentItem(CjReciboEntity currentItem) {
		this.currentItem = currentItem;
	}

	public List<SelectItem> getLstExtorno() {
		return lstExtorno;
	}

	public void setLstExtorno(List<SelectItem> lstExtorno) {
		this.lstExtorno = lstExtorno;
	}
	

	public String getTipoDoc() {
		return tipoDoc;
	}

	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}

	public String getNroDocum() {
		return nroDocum;
	}

	public void setNroDocum(String nroDocum) {
		this.nroDocum = nroDocum;
	}

	public Timestamp getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Timestamp fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Timestamp getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Timestamp fechaFin) {
		this.fechaFin = fechaFin;
	}
	public int getSelectedOptBusc() {
		return selectedOptBusc;
	}

	public void setSelectedOptBusc(int selectedOptBusc) {
		this.selectedOptBusc = selectedOptBusc;
	}

	public String getNro_recibo() {
		return nro_recibo;
	}

	public void setNro_recibo(String nro_recibo) {
		this.nro_recibo = nro_recibo;
	}

	public String getCodigoContribuyente() {
		return codigoContribuyente;
	}

	public void setCodigoContribuyente(String codigoContribuyente) {
		this.codigoContribuyente = codigoContribuyente;
	}

	public List<SelectItem> getLstTipoDoc() {
		return lstTipoDoc;
	}

	public void setLstTipoDoc(List<SelectItem> lstTipoDoc) {
		this.lstTipoDoc = lstTipoDoc;
	}

	public String getCmbValTipoDocumento() {
		return cmbValTipoDocumento;
	}

	public void setCmbValTipoDocumento(String cmbValTipoDocumento) {
		this.cmbValTipoDocumento = cmbValTipoDocumento;
	}

	public HtmlComboBox getCmbtipoDoc() {
		return cmbtipoDoc;
	}

	public void setCmbtipoDoc(HtmlComboBox cmbtipoDoc) {
		this.cmbtipoDoc = cmbtipoDoc;
	}
}
