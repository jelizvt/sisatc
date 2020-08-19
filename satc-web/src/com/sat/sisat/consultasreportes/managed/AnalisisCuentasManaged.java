package com.sat.sisat.consultasreportes.managed;

import java.util.ArrayList;
import java.util.Date;
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

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.persistence.entity.GnNotaria;
import com.sat.sisat.persistence.entity.RaTipoTransferencia;
import com.sat.sisat.reportes.business.ReportesBoRemote;
import com.sat.sisat.reportes.dto.AnalisisCuentaDTO;
import com.sat.sisat.reportes.dto.ConSubConDTO;

@ManagedBean
@ViewScoped
public class AnalisisCuentasManaged extends BaseManaged{

	private static final long serialVersionUID = 1L;
	@EJB
	ReportesBoRemote reportesBo;
	
	ArrayList<ConSubConDTO> listaConSub= new ArrayList<ConSubConDTO>();
	private List<SelectItem> lstSelectConSub = new ArrayList<SelectItem>();
	private List<SelectItem> lstSelectTipoAna = new ArrayList<SelectItem>();
	public List<SelectItem> getLstSelectTipoAna() {
		return lstSelectTipoAna;
	}

	public void setLstSelectTipoAna(List<SelectItem> lstSelectTipoAna) {
		this.lstSelectTipoAna = lstSelectTipoAna;
	}

	public List<SelectItem> getLstSelectConSub() {
		return lstSelectConSub;
	}

	public void setLstSelectConSub(List<SelectItem> lstSelectConSub) {
		this.lstSelectConSub = lstSelectConSub;
	}

	private HtmlComboBox cmbtipoSubcon;
	private HtmlComboBox cmbtipoAna;
	
	public HtmlComboBox getCmbtipoSubcon() {
		return cmbtipoSubcon;
	}

	public void setCmbtipoSubcon(HtmlComboBox cmbtipoSubcon) {
		this.cmbtipoSubcon = cmbtipoSubcon;
	}

	@PostConstruct
	public void init(){
	try {

		mapTipoAnalisis.put("Insoluto", 1);
		lstSelectTipoAna.add(new SelectItem("Insoluto", "1"));
		mapTipoAnalisis.put("Interés", 2);
		lstSelectTipoAna.add(new SelectItem("Interés", "2"));
		mapTipoAnalisis.put("Reajuste", 3);
		lstSelectTipoAna.add(new SelectItem("Reajuste", "3"));
		mapTipoAnalisis.put("Derecho de Emisión", 4);
		lstSelectTipoAna.add(new SelectItem("Derecho de Emisión", "4"));
		mapTipoAnalisis.put("Costas", 5);
		lstSelectTipoAna.add(new SelectItem("Costas", "5"));
		mapTipoAnalisis.put("Gastos", 6);
		lstSelectTipoAna.add(new SelectItem("Gastos", "6"));
		
		listaConSub=reportesBo.getConceptoSubconcepto();
		Iterator<ConSubConDTO> it1= listaConSub.iterator();
		while(it1.hasNext()){
			ConSubConDTO obj= it1.next();
			lstSelectConSub.add(new SelectItem(obj.getDescSubcon(),String.valueOf(obj.getSubConceptoId())));
			mapTipoSubconcepto.put(obj.getDescSubcon(), obj.getSubConceptoId());
		}
			
	} catch (Exception e) {
		e.printStackTrace();
		System.out.println("ERROR: "+e);
	} }
	
	private HashMap<String, Integer> mapTipoAnalisis = new HashMap<String, Integer>();

	private HashMap<String, Integer> mapTipoSubconcepto = new HashMap<String, Integer>();

	Integer tipoSubcon;
	public void loadTipoSubCon(ValueChangeEvent event) {
		try {
			String value = (String) event.getNewValue();
			if (value.length()>0&&value!="--") {
				tipoSubcon = (Integer) mapTipoSubconcepto.get(value);
				
			}

		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	Integer tipoAnalisis;
	public void loadTipoAnalisis(ValueChangeEvent event) {
		try {
			String value = (String) event.getNewValue();
			if (value.length()>0&&value!="--") {
				tipoAnalisis = (Integer) mapTipoAnalisis.get(value);
				
			}

		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	private Date fechaIni;
	private Date fechaFin;
	
	private ArrayList<AnalisisCuentaDTO> lista = new ArrayList<AnalisisCuentaDTO>();
	
	public void analizar(){
		try {
			if(tipoSubcon!=null && tipoSubcon>0 && tipoAnalisis!=null && tipoAnalisis>0){
				lista=reportesBo.getAnalisisCuenta(tipoAnalisis, tipoSubcon, fechaIni, fechaFin);
			}	
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
	}
	
	
	public ArrayList<ConSubConDTO> getListaConSub() {
		return listaConSub;
	}
	public void setListaConSub(ArrayList<ConSubConDTO> listaConSub) {
		this.listaConSub = listaConSub;
	}

	public HashMap<String, Integer> getMapTipoAnalisis() {
		return mapTipoAnalisis;
	}

	public void setMapTipoAnalisis(HashMap<String, Integer> mapTipoAnalisis) {
		this.mapTipoAnalisis = mapTipoAnalisis;
	}

	public HtmlComboBox getCmbtipoAna() {
		return cmbtipoAna;
	}

	public void setCmbtipoAna(HtmlComboBox cmbtipoAna) {
		this.cmbtipoAna = cmbtipoAna;
	}

	public ArrayList<AnalisisCuentaDTO> getLista() {
		return lista;
	}

	public void setLista(ArrayList<AnalisisCuentaDTO> lista) {
		this.lista = lista;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Date getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(Date fechaIni) {
		this.fechaIni = fechaIni;
	}
	

}
