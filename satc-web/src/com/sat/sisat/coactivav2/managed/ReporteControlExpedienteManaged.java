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
import com.sat.sisat.cobranzacoactiva.dto.ControlExpediente;
import com.sat.sisat.common.dto.GenericDTO;
import com.sat.sisat.common.util.BaseManaged;

@ManagedBean
@ViewScoped
public class ReporteControlExpedienteManaged extends BaseManaged {
	@EJB
	CobranzaCoactivaBoRemote cobranzaCoactivaBo;
	
	private Integer coactivoId;
	private java.util.Date fechaDesde;
	private java.util.Date fechaHasta;
	private Integer materiaId;
	private String nroExpediente;
	private List<GenericDTO> listaEjecutor = new ArrayList<GenericDTO>();
	private List<ControlExpediente> records = new ArrayList<ControlExpediente>();
	
	@PostConstruct
	public void init() throws Exception {
		try {
			listaEjecutor=cobranzaCoactivaBo.listarEjecutorCoactivo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void buscar(){
		try {
			records=cobranzaCoactivaBo.reporteControlExpediente(nroExpediente, coactivoId, fechaDesde, fechaHasta, materiaId);
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
		row1.createCell(1).setCellValue("Nro Expediente");
		row1.createCell(2).setCellValue("Fecha Inicio");
		row1.createCell(3).setCellValue("Nro Valor");
		row1.createCell(4).setCellValue("Tipo valor");
		row1.createCell(5).setCellValue("Concepto");
		row1.createCell(6).setCellValue("Periodo");
		row1.createCell(7).setCellValue("Deuda Valor");
		row1.createCell(8).setCellValue("Codigo");
		row1.createCell(9).setCellValue("Apellidos y Nombres");
		row1.createCell(10).setCellValue("Tipo REC/RC");
		row1.createCell(11).setCellValue("Nro RC");
		row1.createCell(12).setCellValue("Fecha Notif. RC");

		for (ControlExpediente data : records) {
			Row row = sheet.createRow(rowIndex++);
			int columnIndex = 0;
			row.createCell(columnIndex++).setCellValue(rowIndex-1);
			row.createCell(columnIndex++).setCellValue(data.getNroExpediente());
			row.createCell(columnIndex++).setCellValue(data.getFechaRegistro());
			row.createCell(columnIndex++).setCellValue(data.getNroValor());
			row.createCell(columnIndex++).setCellValue(data.getTipoValor());
			row.createCell(columnIndex++).setCellValue(data.getConcepto());
			row.createCell(columnIndex++).setCellValue(data.getPeriodoValor());
			row.createCell(columnIndex++).setCellValue(data.getDeudaValor());
			row.createCell(columnIndex++).setCellValue(data.getPersonaId());
			row.createCell(columnIndex++).setCellValue(data.getApellidosNombres());
			row.createCell(columnIndex++).setCellValue(data.getTipoUltimaRC());
			row.createCell(columnIndex++).setCellValue(data.getNroRC());
			row.createCell(columnIndex++).setCellValue(data.getFechaNotificaUltimaRC());
		}
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		externalContext.responseReset();
		externalContext.setResponseContentType("application/vnd.ms-excel");
		externalContext.setResponseHeader("Content-Disposition","attachment;filename=reporte_control_expediente.xls");
		try {
			workbook.write(externalContext.getResponseOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		context.responseComplete(); // Prevent JSF from performing navigation.
	}
	public Integer getCoactivoId() {
		return coactivoId;
	}

	public void setCoactivoId(Integer coactivoId) {
		this.coactivoId = coactivoId;
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

	public String getNroExpediente() {
		return nroExpediente;
	}

	public void setNroExpediente(String nroExpediente) {
		this.nroExpediente = nroExpediente;
	}

	public List<ControlExpediente> getRecords() {
		return records;
	}

	public void setRecords(List<ControlExpediente> records) {
		this.records = records;
	}
	
	public List<GenericDTO> getListaEjecutor() {
		return listaEjecutor;
	}

	public void setListaEjecutor(List<GenericDTO> listaEjecutor) {
		this.listaEjecutor = listaEjecutor;
	}
}