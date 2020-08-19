package com.sat.sisat.predial.managed;
        
import java.util.ArrayList;
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
import org.richfaces.component.html.HtmlExtendedDataTable;
import org.richfaces.model.selection.SimpleSelection;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.persistence.entity.GnSector;
import com.sat.sisat.persistence.entity.GnTipoVia;
import com.sat.sisat.persistence.entity.GnVia;
import com.sat.sisat.predial.business.RegistroPrediosBoRemote;
import com.sat.sisat.predial.dto.UbicacionDTO;

@ManagedBean
@ViewScoped
public class BuscarViaManaged extends BaseManaged{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	RegistroPrediosBoRemote registroPrediosBo;
	
	private HtmlComboBox cmbsector;
	private HtmlComboBox cmbtipovia;
	
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
	
	
	private List<SelectItem> listSelectItemGnVia = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapGnVia = new HashMap<String, Integer>();
	
	private Integer tipoViaId;
	private Integer viaId;	
	
	private String comboBoxVia;
	private Integer numeroManzana;
	private Integer ubicacionId;
	private boolean otroFrente;
	
	public BuscarViaManaged(){
		
	}
	@PostConstruct
	public void init() {
		try {
			// GnTipoVia
			List<GnTipoVia> lstGnTipoVia = registroPrediosBo.getAllGnTipoVia(null);			
			
			Iterator<GnTipoVia> it = lstGnTipoVia.iterator();
			lsttipovia = new ArrayList<SelectItem>();
			mapGnTipoVia.put("Todos", 0);
			lsttipovia.add(new SelectItem("Todos", null));
			while (it.hasNext()) {
				GnTipoVia obj = it.next();
				lsttipovia.add(new SelectItem(obj.getDescripcion(), String.valueOf(obj.getTipoViaId())));
				mapGnTipoVia.put(obj.getDescripcion(), obj.getTipoViaId());
			}

			// GnSector
			List<GnSector> lstGnSector = registroPrediosBo.getAllGnSector();

			Iterator<GnSector> it2 = lstGnSector.iterator();
			lstsector = new ArrayList<SelectItem>();

			while (it2.hasNext()) {
				GnSector obj = it2.next();
				lstsector.add(new SelectItem(obj.getDescripcion(), String.valueOf(obj.getSectorId())));
				mapGnSector.put(obj.getDescripcion(), obj.getSectorId());
			}

			currentItem = new UbicacionDTO();		
			setOtroFrente(Boolean.FALSE);
			
		} catch (SisatException e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	//select * from satc.dbo.gn_via
	public void buscar(){
		try{
			String sector=(String)getCmbsector().getValue();
			String tipovia=(String)getCmbtipovia().getValue();
			
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
	
	public void buscarV2(){
		try{
 			if(numeroManzana != null && numeroManzana.intValue() > 0 && isOtroFrente()){
				records=registroPrediosBo.findGnViaV3(numeroManzana, descripcion);
			}else{				
				records=registroPrediosBo.findGnViaV2(tipoViaId, viaId, descripcion);
			}
				
			
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
	
	public void salir(){
		limpiar();
	}
	
	public void limpiar(){
		//cchaucca 28/06/2012 se ha corregido 
		//records=new ArrayList<UbicacionDTO>();
		//cmbsector.setValue("");
		//cmbtipovia.setValue("");
		tipoViaId = null;
		viaId = null;
	}
	
	public void changeListenerComboBoxTipoVia(ValueChangeEvent event) {

		String key = (String) event.getNewValue();
		Integer id = mapGnTipoVia.get(key);
		if (id == null) {

			WebMessages.messageError("El tipo de via ingresado es incorrecto");
			this.tipoViaId = -1;
			return;
		}
		try {
			this.tipoViaId = id;
			List<GnVia> list = registroPrediosBo.getAllGnVia(id);
			
			listSelectItemGnVia = null;
			listSelectItemGnVia = new ArrayList<SelectItem>();
			mapGnVia=null;
			mapGnVia= new HashMap<String, Integer>();
			mapGnVia.put("Todos", 0);
			listSelectItemGnVia.add(new SelectItem("Todos", null));
			for (GnVia gnVia : list) {
				listSelectItemGnVia.add(new SelectItem(gnVia.getDescripcion(), String.valueOf(gnVia.getViaId())));
				mapGnVia.put(gnVia.getDescripcion(), gnVia.getViaId());
			}
			this.comboBoxVia = null;
			
			
		} catch (SisatException e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}

	}
	
	public void changeListenerComboBoxVia(ValueChangeEvent event) {
		String key = (String) event.getNewValue();
		Integer id = mapGnVia.get(key);		
		
		if(id == null){			
			
			WebMessages.messageError("La via ingresada es incorrecta");
			this.viaId = -1;
			return;
		}
		this.viaId = id;
		
		
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
	
	public HtmlComboBox getCmbsector() {
		return cmbsector;
	}
	public void setCmbsector(HtmlComboBox cmbsector) {
		this.cmbsector = cmbsector;
	}
	public HtmlComboBox getCmbtipovia() {
		return cmbtipovia;
	}
	public void setCmbtipovia(HtmlComboBox cmbtipovia) {
		this.cmbtipovia = cmbtipovia;
	}
	public List<SelectItem> getListSelectItemGnVia() {
		return listSelectItemGnVia;
	}
	public void setListSelectItemGnVia(List<SelectItem> listSelectItemGnVia) {
		this.listSelectItemGnVia = listSelectItemGnVia;
	}
	public Integer getViaId() {
		return viaId;
	}
	public void setViaId(Integer viaId) {
		this.viaId = viaId;
	}
	public Integer getTipoViaId() {
		return tipoViaId;
	}
	public void setTipoViaId(Integer tipoViaId) {
		this.tipoViaId = tipoViaId;
	}
	public String getComboBoxVia() {
		return comboBoxVia;
	}
	public void setComboBoxVia(String comboBoxVia) {
		this.comboBoxVia = comboBoxVia;
	}
	public HashMap<String, Integer> getMapGnTipoVia() {
		return mapGnTipoVia;
	}
	public void setMapGnTipoVia(HashMap<String, Integer> mapGnTipoVia) {
		this.mapGnTipoVia = mapGnTipoVia;
	}
	public Integer getNumeroManzana() {
		return numeroManzana;
	}
	public void setNumeroManzana(Integer numeroManzana) {
		this.numeroManzana = numeroManzana;
	}
	public Integer getUbicacionId() {
		return ubicacionId;
	}
	public void setUbicacionId(Integer ubicacionId) {
		this.ubicacionId = ubicacionId;
	}
	public boolean isOtroFrente() {
		return otroFrente;
	}
	public void setOtroFrente(boolean otroFrente) {
		this.otroFrente = otroFrente;
	}
}
