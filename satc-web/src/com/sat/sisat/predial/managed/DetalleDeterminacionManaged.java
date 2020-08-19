package com.sat.sisat.predial.managed;

import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.richfaces.component.html.HtmlExtendedDataTable;
import org.richfaces.model.selection.SimpleSelection;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.persistence.entity.DtDeterminacion;
import com.sat.sisat.predial.business.CalculoPredialBoRemote;
import com.sat.sisat.predial.business.RegistroPrediosBoRemote;
import com.sat.sisat.predial.dto.DtDeterminacionConstruccionDTO;
import com.sat.sisat.predial.dto.DtDeterminacionInstalacionDTO;
import com.sat.sisat.predial.dto.DtDeterminacionPredioDTO;

@ManagedBean
@ViewScoped
public class DetalleDeterminacionManaged extends BaseManaged {
	@EJB
	RegistroPrediosBoRemote registroPrediosBo;
	
	@EJB
	CalculoPredialBoRemote calculoPredialBo;
	
	private ArrayList<DtDeterminacionPredioDTO> records;
	private DtDeterminacion determinacion;
	
	HashMap<Integer, ArrayList<DtDeterminacionConstruccionDTO>> recordsConstruccion=new HashMap<Integer, ArrayList<DtDeterminacionConstruccionDTO>>();
	HashMap<Integer, ArrayList<DtDeterminacionInstalacionDTO>> recordsInstalaciones=new HashMap<Integer, ArrayList<DtDeterminacionInstalacionDTO>>();
	
	private SimpleSelection selection = new SimpleSelection();
	private HtmlExtendedDataTable table;
	private int currentRow;
	
	private String codigoPredio;
	
	public DetalleDeterminacionManaged(){
		
	}
	
	@PostConstruct
	public void init(){
		try{
			records=new ArrayList<DtDeterminacionPredioDTO>();
			determinacion=new DtDeterminacion();
			
			determinacion=(DtDeterminacion)getSessionMap().get("DtDeterminacion");
			
			if(determinacion!=null&&determinacion.getDeterminacionId()>Constante.RESULT_PENDING){
				records=calculoPredialBo.getAllDtDeterminacionPredioDj(determinacion.getDeterminacionId());
				for(int i=0;i<records.size();i++){
					DtDeterminacionPredioDTO determinacionPredio=records.get(i);
					ArrayList<DtDeterminacionConstruccionDTO> lConstruccion=calculoPredialBo.getAllDtDeterminacionConstruccion(determinacionPredio.getDeterPredioId(),determinacion.getAnnoDeterminacion());
					recordsConstruccion.put(determinacionPredio.getDeterPredioId(), lConstruccion);
					
					ArrayList<DtDeterminacionInstalacionDTO> lInstalaciones=calculoPredialBo.getAllDtDeterminacionInstalacion(determinacionPredio.getDeterPredioId(),determinacion.getAnnoDeterminacion());
					recordsInstalaciones.put(determinacionPredio.getDeterPredioId(), lInstalaciones);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public String salir(){
		String pageReturn=(String)getSessionMap().get("pageReturnDetalleDeter");
        if(pageReturn!=null&&pageReturn.trim().length()>0){
        	getSessionManaged().setPage(pageReturn);
        	getSessionMap().remove("pageReturnDetalleDeter");
        }
		return sendRedirectPrincipal();
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

	public String getCodigoPredio() {
		return codigoPredio;
	}

	public void setCodigoPredio(String codigoPredio) {
		this.codigoPredio = codigoPredio;
	}

	public ArrayList<DtDeterminacionPredioDTO> getRecords() {
		return records;
	}

	public void setRecords(ArrayList<DtDeterminacionPredioDTO> records) {
		this.records = records;
	}

	public HashMap<Integer, ArrayList<DtDeterminacionConstruccionDTO>> getRecordsConstruccion() {
		return recordsConstruccion;
	}

	public void setRecordsConstruccion(
			HashMap<Integer, ArrayList<DtDeterminacionConstruccionDTO>> recordsConstruccion) {
		this.recordsConstruccion = recordsConstruccion;
	}

	public HashMap<Integer, ArrayList<DtDeterminacionInstalacionDTO>> getRecordsInstalaciones() {
		return recordsInstalaciones;
	}

	public void setRecordsInstalaciones(
			HashMap<Integer, ArrayList<DtDeterminacionInstalacionDTO>> recordsInstalaciones) {
		this.recordsInstalaciones = recordsInstalaciones;
	}

	public DtDeterminacion getDeterminacion() {
		return determinacion;
	}

	public void setDeterminacion(DtDeterminacion determinacion) {
		this.determinacion = determinacion;
	}
	
}
