package com.sat.sisat.coactivav2.managed;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.sat.sisat.cobranzacoactiva.business.CobranzaCoactivaBoRemote;
import com.sat.sisat.cobranzacoactiva.dto.CarteraExigibilidad;
import com.sat.sisat.cobranzacoactiva.dto.InformeTransferidoDetalle;
import com.sat.sisat.common.dto.GenericDTO;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;

@ManagedBean
@ViewScoped
public class CreaCarteraExigibilidadManaged extends BaseManaged {
	@EJB
	CobranzaCoactivaBoRemote cobranzaCoactivaBo;
	
	private Integer periodo;
	private String nroValor;
	private Integer contribuyenteId;
	private Double montoMinimo;
	private Double montoMaximo;

	private Integer tipoPersonaId;
	private Integer conceptoId;
	private Integer subconceptoId;
	private Integer tipoActoId; 
	
	private Integer coactivoId;
	private Integer carteraId;
	
	private List<GenericDTO> listaTipoPersona = new ArrayList<GenericDTO>();
	private List<GenericDTO> listaConcepto = new ArrayList<GenericDTO>();
	private List<GenericDTO> listaSubConcepto = new ArrayList<GenericDTO>();
	private List<GenericDTO> listaTipoValor = new ArrayList<GenericDTO>();
	private List<GenericDTO> listaEjecutor = new ArrayList<GenericDTO>();
	
	private List<InformeTransferidoDetalle> records = new ArrayList<InformeTransferidoDetalle>();
	private List<InformeTransferidoDetalle> recordsCartera = new ArrayList<InformeTransferidoDetalle>();
	
	private Boolean selTodos = Boolean.FALSE;
	
	private Integer materiaId;
	
	@PostConstruct
	public void init() throws Exception {
		try {
			listaTipoPersona = cobranzaCoactivaBo.listarTipoPersona();
			listaTipoValor = cobranzaCoactivaBo.listarTipoActo();
			listaConcepto = cobranzaCoactivaBo.listarConcepto();
			listaEjecutor=cobranzaCoactivaBo.listarEjecutorCoactivo();
			
			CarteraExigibilidad selected = (CarteraExigibilidad) getSessionMap().get("carteraExigibilidad");
			if (selected != null && selected.getCarteraId()!= Constante.RESULT_PENDING) {
				//buscar cartera existente
				carteraId=selected.getCarteraId();
				materiaId=selected.getMateriaId(); 
				listarValoresCartera(carteraId);
			} else {
				materiaId=selected.getMateriaId();//Esta opcion de creacion de carteras lo usa la Jefatura de Cobranza Coactiva
				carteraId=cobranzaCoactivaBo.registrarCarteraExigibilidad(Constante.RESULT_PENDING,getSessionManaged().getUsuarioLogIn().getUsuarioId(),Constante.TIPO_CARTERA_EXIGIBILIDAD,materiaId,getSessionManaged().getTerminalLogIn(),getSessionManaged().getUsuarioLogIn().getUsuarioId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void changeComboBoxConcepto(ValueChangeEvent event) {
		try{
			if(event.getNewValue()!=null){
				Integer conceptoId = Integer.parseInt(event.getNewValue().toString());
				if (conceptoId != null) {
					listaSubConcepto = cobranzaCoactivaBo.listarSubConcepto(conceptoId);
				}	
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void buscar(){
		try{
			records=cobranzaCoactivaBo.seleccionaCarteraExigibilidad(periodo, tipoPersonaId, materiaId, conceptoId, subconceptoId, tipoActoId, nroValor, contribuyenteId, montoMinimo, montoMaximo,carteraId);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void agregarCartera(){
		try {
			StringBuffer listDetalleId=new StringBuffer();
			for(InformeTransferidoDetalle detalle : records){
				if(detalle.isSelected()){
					listDetalleId.append(detalle.getLoteTransferenciaDetalleId()).append(",");
				}
			}
			if(listDetalleId.toString().length()>0){
				cobranzaCoactivaBo.registraProspectoExigibilidad(listDetalleId.toString(),carteraId,getSessionManaged().getUsuarioLogIn().getUsuarioId(), getSessionManaged().getTerminalLogIn());
				buscar();
				listarValoresCartera(carteraId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void registraAsignacion(){
		try{
			if(coactivoId!=null&&coactivoId>Constante.RESULT_PENDING){
				carteraId=cobranzaCoactivaBo.registrarCarteraExigibilidad(carteraId,coactivoId,Constante.TIPO_CARTERA_EXIGIBILIDAD,materiaId,getSessionManaged().getTerminalLogIn(),getSessionManaged().getUsuarioLogIn().getUsuarioId());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void listarValoresCartera(Integer carteraId){
		try{
			recordsCartera=cobranzaCoactivaBo.seleccionaProspectoExigibilidad(carteraId);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void valueChangeListenerSelTodos(ValueChangeEvent ev){
		String nv = ev.getNewValue().toString();
		if(nv.equals("true")){
			for(InformeTransferidoDetalle detalle:records){
				detalle.setSelected(Boolean.TRUE);				
			}
		}else{
			for(InformeTransferidoDetalle detalle:records){
				detalle.setSelected(Boolean.FALSE);				
			}
		}
	}
	
	public void descargarExcel(){
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("Reporte");
		int rowIndex = 1;
		Row row1 = sheet.createRow(0);
		
		row1.createCell(0).setCellValue("item");
		row1.createCell(1).setCellValue("Tipo de valor");
		row1.createCell(2).setCellValue("Nro de valor");
		row1.createCell(3).setCellValue("Código");
		row1.createCell(4).setCellValue("Contribuyente");
		row1.createCell(5).setCellValue("Periodo");
		row1.createCell(6).setCellValue("Concepto");
		row1.createCell(7).setCellValue("SubConcepto");
		row1.createCell(8).setCellValue("Deuda");
		
		for (InformeTransferidoDetalle data : recordsCartera) {
			Row row = sheet.createRow(rowIndex++);
			int columnIndex = 0;
			row.createCell(columnIndex++).setCellValue(rowIndex-1);
			row.createCell(columnIndex++).setCellValue(data.getTipoValor());
			row.createCell(columnIndex++).setCellValue(data.getNroValor());
			row.createCell(columnIndex++).setCellValue(data.getPersonaId());
			row.createCell(columnIndex++).setCellValue(data.getApellidosNombresPersona());
			row.createCell(columnIndex++).setCellValue(data.getPeriodo());
			row.createCell(columnIndex++).setCellValue(data.getConcepto());
			row.createCell(columnIndex++).setCellValue(data.getSubConcepto());
			row.createCell(columnIndex++).setCellValue(data.getMontoDeuda());
		}
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		externalContext.responseReset();
		externalContext.setResponseContentType("application/vnd.ms-excel");
		externalContext.setResponseHeader("Content-Disposition",
				"attachment;filename=cartera_valores_exigibilidad.xls");
		try {
			workbook.write(externalContext.getResponseOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		context.responseComplete(); // Prevent JSF from performing navigation.
	}
	
	public List<GenericDTO> getListaEjecutor() {
		return listaEjecutor;
	}

	public void setListaEjecutor(List<GenericDTO> listaEjecutor) {
		this.listaEjecutor = listaEjecutor;
	}

	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}

	public String getNroValor() {
		return nroValor;
	}

	public void setNroValor(String nroValor) {
		this.nroValor = nroValor;
	}

	public Integer getContribuyenteId() {
		return contribuyenteId;
	}

	public void setContribuyenteId(Integer contribuyenteId) {
		this.contribuyenteId = contribuyenteId;
	}

	public List<GenericDTO> getListaTipoPersona() {
		return listaTipoPersona;
	}

	public void setListaTipoPersona(List<GenericDTO> listaTipoPersona) {
		this.listaTipoPersona = listaTipoPersona;
	}

	public List<GenericDTO> getListaConcepto() {
		return listaConcepto;
	}

	public void setListaConcepto(List<GenericDTO> listaConcepto) {
		this.listaConcepto = listaConcepto;
	}

	public List<GenericDTO> getListaSubConcepto() {
		return listaSubConcepto;
	}

	public void setListaSubConcepto(List<GenericDTO> listaSubConcepto) {
		this.listaSubConcepto = listaSubConcepto;
	}

	public List<GenericDTO> getListaTipoValor() {
		return listaTipoValor;
	}

	public void setListaTipoValor(List<GenericDTO> listaTipoValor) {
		this.listaTipoValor = listaTipoValor;
	}

	public List<InformeTransferidoDetalle> getRecords() {
		return records;
	}

	public void setRecords(List<InformeTransferidoDetalle> records) {
		this.records = records;
	}
	
	public Double getMontoMinimo() {
		return montoMinimo;
	}

	public void setMontoMinimo(Double montoMinimo) {
		this.montoMinimo = montoMinimo;
	}

	public Double getMontoMaximo() {
		return montoMaximo;
	}

	public void setMontoMaximo(Double montoMaximo) {
		this.montoMaximo = montoMaximo;
	}

	public Integer getTipoPersonaId() {
		return tipoPersonaId;
	}

	public void setTipoPersonaId(Integer tipoPersonaId) {
		this.tipoPersonaId = tipoPersonaId;
	}

	public Integer getConceptoId() {
		return conceptoId;
	}

	public void setConceptoId(Integer conceptoId) {
		this.conceptoId = conceptoId;
	}

	public Integer getSubconceptoId() {
		return subconceptoId;
	}

	public void setSubconceptoId(Integer subconceptoId) {
		this.subconceptoId = subconceptoId;
	}

	public Integer getTipoActoId() {
		return tipoActoId;
	}

	public void setTipoActoId(Integer tipoActoId) {
		this.tipoActoId = tipoActoId;
	}

	public Integer getCoactivoId() {
		return coactivoId;
	}

	public void setCoactivoId(Integer coactivoId) {
		this.coactivoId = coactivoId;
	}
	
	public Integer getCarteraId() {
		return carteraId;
	}

	public void setCarteraId(Integer carteraId) {
		this.carteraId = carteraId;
	}
	
	public Boolean getSelTodos() {
		return selTodos;
	}

	public void setSelTodos(Boolean selTodos) {
		this.selTodos = selTodos;
	}
	public List<InformeTransferidoDetalle> getRecordsCartera() {
		return recordsCartera;
	}

	public void setRecordsCartera(List<InformeTransferidoDetalle> recordsCartera) {
		this.recordsCartera = recordsCartera;
	}
	
	public Integer getMateriaId() {
		return materiaId;
	}

	public void setMateriaId(Integer materiaId) {
		this.materiaId = materiaId;
	}
}
