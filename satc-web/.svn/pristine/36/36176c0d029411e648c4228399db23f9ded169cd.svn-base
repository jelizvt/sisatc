package com.sat.sisat.predial.managed;
        
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
import org.richfaces.component.html.HtmlExtendedDataTable;
import org.richfaces.model.selection.SimpleSelection;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.persistence.entity.GnSector;
import com.sat.sisat.persistence.entity.GnTipoVia;
import com.sat.sisat.persona.managed.RegistroDireccionPersonaManaged;
import com.sat.sisat.predial.business.RegistroPrediosBoRemote;
import com.sat.sisat.predial.dto.UbicacionDTO;

@ManagedBean
@ViewScoped
public class BuscarViaManaged_Ant extends BaseManaged{
	@EJB
	RegistroPrediosBoRemote registroPrediosBo;
	
	private HtmlComboBox cmbsectorpred;
	private HtmlComboBox cmbtipoviapred;
	
	private List<SelectItem> lstsector=new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapGnTipoVia=new HashMap<String, Integer>();
	
	private List<SelectItem> lsttipovia=new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapGnSector=new HashMap<String, Integer>();
	
	private String descripcion;
	private String selectedValue;
	
	private ArrayList<UbicacionDTO> records;
	private SimpleSelection selection = new SimpleSelection();
	private HtmlExtendedDataTable table;
	private UbicacionDTO currentItem = new UbicacionDTO();
	
	public BuscarViaManaged_Ant(){
		
	}
	@PostConstruct
	public void init(){
		try{
	        //GnTipoVia
	        List<GnTipoVia> lstGnTipoVia=registroPrediosBo.getAllGnTipoVia();
			Iterator<GnTipoVia> it = lstGnTipoVia.iterator();  
			lsttipovia = new ArrayList<SelectItem>();
			 
	        while (it.hasNext()){
	        	GnTipoVia obj = it.next();  
	        	lsttipovia.add(new SelectItem(obj.getDescripcion(),String.valueOf(obj.getTipoViaId())));
	        	mapGnTipoVia.put(obj.getDescripcion(), obj.getTipoViaId());
	        }
	        
	        //GnSector
	        List<GnSector> lstGnSector=registroPrediosBo.getAllGnSector();
			
			Iterator<GnSector> it2 = lstGnSector.iterator();  
			lstsector = new ArrayList<SelectItem>();
			 
	        while (it2.hasNext()){
	        	GnSector obj = it2.next();  
	        	lstsector.add(new SelectItem(obj.getDescripcion(),String.valueOf(obj.getSectorId())));
	        	mapGnSector.put(obj.getDescripcion(), obj.getSectorId());
	        }
	        
	        currentItem = new UbicacionDTO();
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	//select * from satc.dbo.gn_via
	public void buscar(){
		try{
			String sector=(String)getCmbsectorpred().getValue();
			String tipovia=(String)getCmbtipoviapred().getValue();
			
			Integer tipoViaId=null;
			if(tipovia!=null&&tipovia.trim().length()>0)
				tipoViaId=mapGnTipoVia.get(tipovia);
			
			Integer sectorId=null;
			if(sector!=null&&sector.trim().length()>0)
				sectorId=mapGnSector.get(sector);
			
			records=registroPrediosBo.findGnVia(tipoViaId,sectorId,descripcion);
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}

	public void seleccionar(){
		try{
			String paramParent=(String)getSessionMap().get("paramParent");
			
			if(paramParent!=null&&paramParent.equals("OtrosFrentes")){
				OtrosFrentesManaged frente = (OtrosFrentesManaged) getManaged("otrosFrentesManaged");
				frente.setSelectedVia(currentItem);
				salir();
			}else if(paramParent!=null&&paramParent.equals("UbicacionPredio")){
				UbicacionPredioUrbanoManaged ubicacion = (UbicacionPredioUrbanoManaged)getManaged("ubicacionPredioUrbanoManaged");
				ubicacion.setSelectedVia(currentItem);
				salir();
			}
			
			getSessionMap().remove("paramParent");
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public void seleccionarEnMpDireccion(){
		try{
			RegistroDireccionPersonaManaged ubicacion1 = (RegistroDireccionPersonaManaged) getManaged("registroDireccionPersonaManaged");
			ubicacion1.setSelectedVia(currentItem);
			
			//getSessionMap().put("ubicacion", currentItem);
			salir();	
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public void salir(){
		limpiar();
	}
	
	public void limpiar(){
		//cchaucca 28/06/2012 se ha corregido 
		//records=new ArrayList<UbicacionDTO>();
		//cmbsector.setValue("");
		//cmbtipovia.setValue("");
	}
	
	public List<SelectItem> getLstsector() {
		return lstsector;
	}

	public void setLstsector(List<SelectItem> lstsector) {
		this.lstsector = lstsector;
	}

	public List<SelectItem> getLsttipovia() {
		return lsttipovia;
	}

	public void setLsttipovia(List<SelectItem> lsttipovia) {
		this.lsttipovia = lsttipovia;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public ArrayList<UbicacionDTO> getRecords() {
		return records;
	}

	public void setRecords(ArrayList<UbicacionDTO> records) {
		this.records = records;
	}
	public String getSelectedValue() {
		return selectedValue;
	}

	public void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}
	
	public SimpleSelection getSelection() {
		return selection;
	}

	public void setSelection(SimpleSelection selection) {
		this.selection = selection;
	}

	public HtmlExtendedDataTable getTable() {
		return table;
	}

	public void setTable(HtmlExtendedDataTable table) {
		this.table = table;
	}

	public UbicacionDTO getCurrentItem() {
		return currentItem;
	}

	public void setCurrentItem(UbicacionDTO currentItem) {
		this.currentItem = currentItem;
	}
	
	public HtmlComboBox getCmbsectorpred() {
		return cmbsectorpred;
	}
	public void setCmbsectorpred(HtmlComboBox cmbsectorpred) {
		this.cmbsectorpred = cmbsectorpred;
	}
	public HtmlComboBox getCmbtipoviapred() {
		return cmbtipoviapred;
	}
	public void setCmbtipoviapred(HtmlComboBox cmbtipoviapred) {
		this.cmbtipoviapred = cmbtipoviapred;
	}
}
