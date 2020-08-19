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
import com.sat.sisat.cobranzacoactiva.dto.SituacionDeuda;
import com.sat.sisat.common.util.BaseManaged;

@ManagedBean
@ViewScoped
public class ReporteSituacionDeudaManaged extends BaseManaged {
	@EJB
	CobranzaCoactivaBoRemote cobranzaCoactivaBo;
	
	private Integer carteraId;
	private Integer personaId;
	
	private List<SituacionDeuda> records = new ArrayList<SituacionDeuda>();
	
	@PostConstruct
	public void init() throws Exception {
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void buscar(){
		try {
			records=cobranzaCoactivaBo.reporteSituacionDeuda(carteraId, personaId);
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
		row1.createCell(1).setCellValue("Codigo");
		row1.createCell(2).setCellValue("Apellidos y Nombres");
		row1.createCell(3).setCellValue("Nro valor");
		row1.createCell(4).setCellValue("Tipo valor");
		row1.createCell(5).setCellValue("Concepto");
		row1.createCell(6).setCellValue("Periodo");
		row1.createCell(7).setCellValue("Insoluto");
		row1.createCell(8).setCellValue("Resjuste");
		row1.createCell(9).setCellValue("Intereses");
		row1.createCell(10).setCellValue("Der. Emision");
		row1.createCell(11).setCellValue("Total Deuda");
		row1.createCell(12).setCellValue("Nro Expediente");
		row1.createCell(13).setCellValue("Fecha Expediente");
		row1.createCell(14).setCellValue("Nro Rec");
		row1.createCell(15).setCellValue("Tipo Rec");
		row1.createCell(16).setCellValue("Fecha Notificacion");
		row1.createCell(17).setCellValue("Coactivo");
		row1.createCell(18).setCellValue("Total Cance.");
		row1.createCell(19).setCellValue("Estado Deuda");

		for (SituacionDeuda data : records) {
			Row row = sheet.createRow(rowIndex++);
			int columnIndex = 0;
			row.createCell(columnIndex++).setCellValue(rowIndex-1);
			row.createCell(columnIndex++).setCellValue(data.getCoactivo());
			row.createCell(columnIndex++).setCellValue(data.getApellidosNombres());
			row.createCell(columnIndex++).setCellValue(data.getNroActo());
			row.createCell(columnIndex++).setCellValue(data.getTipoActo());
			row.createCell(columnIndex++).setCellValue(data.getConcepto());
			row.createCell(columnIndex++).setCellValue(data.getAnnoActo());
			row.createCell(columnIndex++).setCellValue(data.getInsoluto());
			row.createCell(columnIndex++).setCellValue(data.getReajuste());
			row.createCell(columnIndex++).setCellValue(data.getIntereses());
			row.createCell(columnIndex++).setCellValue(data.getDerechoEmision());
			row.createCell(columnIndex++).setCellValue(data.getTotalDeuda());
			row.createCell(columnIndex++).setCellValue(data.getNroExpediente());
			row.createCell(columnIndex++).setCellValue(data.getFechaRegistro());
			row.createCell(columnIndex++).setCellValue(data.getNroREC());
			row.createCell(columnIndex++).setCellValue(data.getTipoRec());
			row.createCell(columnIndex++).setCellValue(data.getFechaNotificacion());
			row.createCell(columnIndex++).setCellValue(data.getCoactivo());
			row.createCell(columnIndex++).setCellValue(data.getTotalCancelado());
			row.createCell(columnIndex++).setCellValue(data.getEstadoDeuda());
		}
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		externalContext.responseReset();
		externalContext.setResponseContentType("application/vnd.ms-excel");
		externalContext.setResponseHeader("Content-Disposition","attachment;filename=reporte_situacion_deuda.xls");
		try {
			workbook.write(externalContext.getResponseOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		context.responseComplete();
	}
	
	public Integer getCarteraId() {
		return carteraId;
	}

	public void setCarteraId(Integer carteraId) {
		this.carteraId = carteraId;
	}
	
	public Integer getPersonaId() {
		return personaId;
	}

	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}

	public List<SituacionDeuda> getRecords() {
		return records;
	}

	public void setRecords(List<SituacionDeuda> records) {
		this.records = records;
	}
}