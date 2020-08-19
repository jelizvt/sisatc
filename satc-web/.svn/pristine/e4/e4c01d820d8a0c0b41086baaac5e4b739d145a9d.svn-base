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

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.sat.sisat.cobranzacoactiva.business.CobranzaCoactivaBoRemote;
import com.sat.sisat.cobranzacoactiva.dto.SituacionExigibilidad;
import com.sat.sisat.common.util.BaseManaged;

@ManagedBean
@ViewScoped
public class ReporteSituacionExigibilidadManaged extends BaseManaged {
	@EJB
	CobranzaCoactivaBoRemote cobranzaCoactivaBo;
	
	private Integer nroLote;
	private java.util.Date fechaDesde;
	private java.util.Date fechaHasta;
	private Integer materiaId;
	
	private List<SituacionExigibilidad> records = new ArrayList<SituacionExigibilidad>();
	
	@PostConstruct
	public void init() throws Exception {
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void buscar(){
		try {
			records=cobranzaCoactivaBo.reporteSituacionExigibilidad(nroLote, fechaDesde, fechaHasta, materiaId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}  
	
	public void exportXls() {
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("Reporte");
		int rowIndex = 1;
		Row row1 = sheet.createRow(0);
		
		row1.createCell(0).setCellValue("item");
		row1.createCell(1).setCellValue("Nro Lote");
		row1.createCell(2).setCellValue("Tipo valor");
		row1.createCell(3).setCellValue("Concepto");
		row1.createCell(4).setCellValue("Periodo");
		row1.createCell(5).setCellValue("Cantidad valores");
		row1.createCell(6).setCellValue("Total deuda recibida");
		row1.createCell(7).setCellValue("Total deuda pagada");
		row1.createCell(8).setCellValue("Cantidad valores pendientes");

		for (SituacionExigibilidad data : records) {
			Row row = sheet.createRow(rowIndex++);
			int columnIndex = 0;
			row.createCell(columnIndex++).setCellValue(rowIndex-1);
			row.createCell(columnIndex++).setCellValue(data.getLoteId());
			row.createCell(columnIndex++).setCellValue(data.getTipoValor());
			row.createCell(columnIndex++).setCellValue(data.getConcepto());
			row.createCell(columnIndex++).setCellValue(data.getPeriodoDeuda());
			row.createCell(columnIndex++).setCellValue(data.getCantidadValores());
			row.createCell(columnIndex++).setCellValue(data.getTotalDeudaRecibida());
			row.createCell(columnIndex++).setCellValue(data.getTotalDeudaPagada());
			row.createCell(columnIndex++).setCellValue(data.getCantidadValoresPendiente());
		}
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		externalContext.responseReset();
		externalContext.setResponseContentType("application/vnd.ms-excel");
		externalContext.setResponseHeader("Content-Disposition","attachment;filename=reporte_situacion_exigibilidad.xls");
		try {
			workbook.write(externalContext.getResponseOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		context.responseComplete(); // Prevent JSF from performing navigation.
	}
	
	public CobranzaCoactivaBoRemote getCobranzaCoactivaBo() {
		return cobranzaCoactivaBo;
	}

	public void setCobranzaCoactivaBo(CobranzaCoactivaBoRemote cobranzaCoactivaBo) {
		this.cobranzaCoactivaBo = cobranzaCoactivaBo;
	}

	public Integer getNroLote() {
		return nroLote;
	}

	public void setNroLote(Integer nroLote) {
		this.nroLote = nroLote;
	}

	public java.util.Date getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(java.util.Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public java.util.Date getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(java.util.Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public Integer getMateriaId() {
		return materiaId;
	}

	public void setMateriaId(Integer materiaId) {
		this.materiaId = materiaId;
	}

	public List<SituacionExigibilidad> getRecords() {
		return records;
	}

	public void setRecords(List<SituacionExigibilidad> records) {
		this.records = records;
	}
	
}