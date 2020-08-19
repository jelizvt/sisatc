package com.sat.sisat.caja.managed;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.sat.sisat.caja.dto.CjReciboEntity;
import com.sat.sisat.caja.dto.CjReciboPagoEntity;
import com.sat.sisat.caja.dto.CjTipoDocumento;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.exception.SisatException;

@ManagedBean
@ViewScoped
public class ListaOperacionManaged extends BaseManaged {

	private static final long serialVersionUID = 7047465727772501944L;

	@EJB
	CajaBoRemote cajaBo;

	private HtmlComboBox cmbtipoDoc;
	private String cmbValTipoDocumento;
	private String nroDocumentoIdentidad;
	private int personaId;
	private String nroRecibo;
	private Date fechaInicio = Calendar.getInstance().getTime();
	private Date fechaFin = Calendar.getInstance().getTime();

	private int selectedOptBusc = 1;

	private ArrayList<CjReciboEntity> records;

	private List<SelectItem> lstTipoDoc = new ArrayList<SelectItem>();

	private HashMap<String, Integer> mapMpTipoDocumento = new HashMap<String, Integer>();

	private List<CjReciboPagoEntity> lstOperaciones = new ArrayList<CjReciboPagoEntity>();

	public ListaOperacionManaged() {
	}

	private Integer reciboId;
	
	private String observacion;
	private String usuarioSupervisor;
	private String passwordSupervisor;
	
	@PostConstruct
	public void init() {
		try {
			List<CjTipoDocumento> lstTD = new ArrayList<CjTipoDocumento>();
			lstTD = cajaBo.obtenerTipoDocumento();
			Iterator<CjTipoDocumento> itAd = lstTD.iterator();
			while (itAd.hasNext()) {
				CjTipoDocumento objAd = itAd.next();
				lstTipoDoc.add(new SelectItem(objAd.getDescripcioncorta(), objAd.getTipoDoc() + ""));
				mapMpTipoDocumento.put(objAd.getDescripcioncorta(),	objAd.getTipoDoc());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void buscar() {
		try {
			int cajero_id = getSessionManaged().getUsuarioLogIn().getUsuarioId();			
			
			this.lstOperaciones = cajaBo.obtenerListadoOperaciones(cajero_id, DateUtil.moverHoraAlInicioDelDia(fechaInicio), DateUtil.moverHoraAlFinalDelDia(fechaFin));
		} catch (Exception ex) {						
			WebMessages.messageError("No ha sido posible recuperar la lista de operaciones.".concat(ex.getMessage()));
		}
	}

	public void verRecibo(){
		/** Direccionando segun el tipo de recibo a ser impreso */ 	
		getSessionManaged().getSessionMap().put("caja.imprimirecibo.reciboId", reciboId);		
	}
	
	public void extornarRecibo(){
		 try 
		 {
			 //Verificar Usuario y password del Supervisor para efectuar el extorno
			 int isSupervisorValido = cajaBo.buscarSupervisor(usuarioSupervisor, passwordSupervisor);
			 if(isSupervisorValido == 1 ){
				//contatenar observacion con supervisor.
				 observacion = observacion + ". Supervisor: "+usuarioSupervisor;
				 //Extornar
				 cajaBo.extornarRecibo(reciboId, observacion, getSessionManaged().getUsuarioLogIn().getUsuarioId(), getSessionManaged().getTerminalLogIn());
				 addInfoMessage("Se extorno el recibo correctamente");
				 buscar();
			 }else{				 			
				 addWarnMessage("Supervisor no Valido. El recibo NO ha sido extornado");	
			 }			 
		 } catch (SisatException ex) {
				addErrorMessage(ex.getMessage());
		 } catch (Exception ex) {
				String msg = "No se ha podido extornar el recibo";
				System.out.println(msg + ex);
				addErrorMessage(msg);
		 }
	}
	public void inicioExtorno(){
		limpiar();
	}
	
	public void limpiar(){
		this.observacion="";
		this.usuarioSupervisor="";
		this.passwordSupervisor="";
	}
	
	public String salir() {
		return sendRedirectPrincipal();
	}

	public String getCmbValTipoDocumento() {
		return cmbValTipoDocumento;
	}

	public void setCmbValTipoDocumento(String cmbValTipoDocumento) {
		this.cmbValTipoDocumento = cmbValTipoDocumento;
	}

	public String getNroDocumentoIdentidad() {
		return nroDocumentoIdentidad;
	}

	public void setNroDocumentoIdentidad(String nroDocumentoIdentidad) {
		this.nroDocumentoIdentidad = nroDocumentoIdentidad;
	}

	public int getPersonaId() {
		return personaId;
	}

	public void setPersonaId(int personaId) {
		this.personaId = personaId;
	}

	public String getNroRecibo() {
		return nroRecibo;
	}

	public void setNroRecibo(String nroRecibo) {
		this.nroRecibo = nroRecibo;
	}

	public ArrayList<CjReciboEntity> getRecords() {
		return records;
	}

	public void setRecords(ArrayList<CjReciboEntity> records) {
		this.records = records;
	}

	public HtmlComboBox getCmbtipoDoc() {
		return cmbtipoDoc;
	}

	public void setCmbtipoDoc(HtmlComboBox cmbtipoDoc) {
		this.cmbtipoDoc = cmbtipoDoc;
	}

	public List<SelectItem> getLstTipoDoc() {
		return lstTipoDoc;
	}

	public void setLstTipoDoc(List<SelectItem> lstTipoDoc) {
		this.lstTipoDoc = lstTipoDoc;
	}

	public List<CjReciboPagoEntity> getLstOperaciones() {
		return lstOperaciones;
	}

	public void setLstOperaciones(List<CjReciboPagoEntity> lstOperaciones) {
		this.lstOperaciones = lstOperaciones;
	}

	public int getSelectedOptBusc() {
		return selectedOptBusc;
	}

	public void setSelectedOptBusc(int selectedOptBusc) {
		this.selectedOptBusc = selectedOptBusc;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Integer getReciboId() {
		return reciboId;
	}

	public void setReciboId(Integer reciboId) {
		this.reciboId = reciboId;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getUsuarioSupervisor() {
		return usuarioSupervisor;
	}

	public void setUsuarioSupervisor(String usuarioSupervisor) {
		this.usuarioSupervisor = usuarioSupervisor;
	}

	public String getPasswordSupervisor() {
		return passwordSupervisor;
	}

	public void setPasswordSupervisor(String passwordSupervisor) {
		this.passwordSupervisor = passwordSupervisor;
	}

}
