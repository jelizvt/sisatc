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
import com.sat.sisat.cobranzacoactiva.dto.SituacionCartera;
import com.sat.sisat.common.util.BaseManaged;

@ManagedBean
@ViewScoped
public class ReporteSituacionCarteraManaged extends BaseManaged {
	@EJB
	CobranzaCoactivaBoRemote cobranzaCoactivaBo;
	
	private Integer nroCartera;
	private java.util.Date fechaDesde;
	private java.util.Date fechaHasta;
	private Integer materiaId;
	
	private List<SituacionCartera> records = new ArrayList<SituacionCartera>();
	
	@PostConstruct
	public void init() throws Exception {
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void buscar(){
		try {
			records=cobranzaCoactivaBo.reporteSituacionCartera(nroCartera, fechaDesde, fechaHasta, materiaId);
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
		row1.createCell(2).setCellValue("Nro cartera");
		row1.createCell(3).setCellValue("Tipo valor");
		row1.createCell(4).setCellValue("Periodo Valor");
		row1.createCell(5).setCellValue("Concepto");
		row1.createCell(6).setCellValue("Nro valor");
		row1.createCell(7).setCellValue("Coactivo");
		row1.createCell(8).setCellValue("Deuda recibida");
		row1.createCell(9).setCellValue("Pagos Previos");
		row1.createCell(10).setCellValue("Pagos Coactiva");
		row1.createCell(11).setCellValue("Cos/Gas. emitidos");
		row1.createCell(12).setCellValue("Cos/Gas. pagados");
		row1.createCell(13).setCellValue("Estado deuda");
		
		row1.createCell(14).setCellValue("Nro Papeleta");
		row1.createCell(15).setCellValue("Nro Placa");
		row1.createCell(16).setCellValue("Codigo");
		row1.createCell(17).setCellValue("Apellidos y Nombres");

		for (SituacionCartera data : records) {
			Row row = sheet.createRow(rowIndex++);
			int columnIndex = 0;
			row.createCell(columnIndex++).setCellValue(rowIndex-1);
			row.createCell(columnIndex++).setCellValue(data.getLoteId());
			row.createCell(columnIndex++).setCellValue(data.getCarteraId());
			row.createCell(columnIndex++).setCellValue(data.getTipoValor());
			row.createCell(columnIndex++).setCellValue(data.getPeriodo());
			row.createCell(columnIndex++).setCellValue(data.getConcepto());
			row.createCell(columnIndex++).setCellValue(data.getNroValor());
			row.createCell(columnIndex++).setCellValue(data.getCoactivo());
			row.createCell(columnIndex++).setCellValue(data.getDeudaRecibida());
			row.createCell(columnIndex++).setCellValue(data.getDeudaPagadaPreCoactiva());
			row.createCell(columnIndex++).setCellValue(data.getDeudaPagadaCoactiva());
			row.createCell(columnIndex++).setCellValue(data.getCostas());
			row.createCell(columnIndex++).setCellValue(data.getCostasPagada());
			row.createCell(columnIndex++).setCellValue(data.getEstadoDeuda());
			
			row.createCell(columnIndex++).setCellValue(data.getNroPapeleta());
			row.createCell(columnIndex++).setCellValue(data.getNroPlaca());
			row.createCell(columnIndex++).setCellValue(data.getCodigoInfractor());
			row.createCell(columnIndex++).setCellValue(data.getApellidosNombresInfractor());
		}
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		externalContext.responseReset();
		externalContext.setResponseContentType("application/vnd.ms-excel");
		externalContext.setResponseHeader("Content-Disposition","attachment;filename=reporte_situacion_cartera.xls");
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

	public List<SituacionCartera> getRecords() {
		return records;
	}

	public void setRecords(List<SituacionCartera> records) {
		this.records = records;
	}
	
	public Integer getNroCartera() {
		return nroCartera;
	}

	public void setNroCartera(Integer nroCartera) {
		this.nroCartera = nroCartera;
	}
	
}