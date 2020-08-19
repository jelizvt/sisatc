package com.sat.sisat.predial.managed;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.richfaces.component.html.HtmlExtendedDataTable;
import org.richfaces.model.selection.SimpleSelection;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.predial.business.CalculoPredialBoRemote;
import com.sat.sisat.predial.business.RegistroPrediosBoRemote;
import com.sat.sisat.predial.dto.DeterminacionArbitriosDTO;


@ManagedBean
@ViewScoped
public class HistoriaArbitriosManaged extends BaseManaged {
	@EJB
	RegistroPrediosBoRemote registroPrediosBo;
	
	@EJB
	CalculoPredialBoRemote calculoPredialBo;
	
	private ArrayList<DeterminacionArbitriosDTO> records;

	private SimpleSelection selection = new SimpleSelection();
	private HtmlExtendedDataTable table;
	private int currentRow;
	private DeterminacionArbitriosDTO currentItem = new DeterminacionArbitriosDTO();
	private String codigoPredio;
	
	public HistoriaArbitriosManaged(){
		
	}
	
	@PostConstruct
	public void init(){
		try{
			records=new ArrayList<DeterminacionArbitriosDTO>(); 
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public String salir(){
		return sendRedirectPrincipal();
	}
	
	public RegistroPrediosBoRemote getRegistroPrediosBo() {
		return registroPrediosBo;
	}

	public void setRegistroPrediosBo(RegistroPrediosBoRemote registroPrediosBo) {
		this.registroPrediosBo = registroPrediosBo;
	}

	public CalculoPredialBoRemote getCalculoPredialBo() {
		return calculoPredialBo;
	}

	public void setCalculoPredialBo(CalculoPredialBoRemote calculoPredialBo) {
		this.calculoPredialBo = calculoPredialBo;
	}

	public ArrayList<DeterminacionArbitriosDTO> getRecords() {
		return records;
	}

	public void setRecords(ArrayList<DeterminacionArbitriosDTO> records) {
		this.records = records;
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

	public int getCurrentRow() {
		return currentRow;
	}

	public void setCurrentRow(int currentRow) {
		this.currentRow = currentRow;
	}

	public DeterminacionArbitriosDTO getCurrentItem() {
		return currentItem;
	}

	public void setCurrentItem(DeterminacionArbitriosDTO currentItem) {
		this.currentItem = currentItem;
	}

	public String getCodigoPredio() {
		return codigoPredio;
	}

	public void setCodigoPredio(String codigoPredio) {
		this.codigoPredio = codigoPredio;
	}
}
