package com.sat.sisat.consultasreportes.managed;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
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

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.controlycobranza.business.ControlyCobranzaBoRemote;
import com.sat.sisat.controlycobranza.dto.FindCcActo;
import com.sat.sisat.reportes.dto.ReporteNotificacionDTO;

@ManagedBean
@ViewScoped
public class ReporteNotificacionesManaged extends BaseManaged{
	
	@EJB
	ControlyCobranzaBoRemote controlycobranzaBo;
	
	private Date fechaInicio = Calendar.getInstance().getTime();
	private Date fechaFin = Calendar.getInstance().getTime();
	
	private List<ReporteNotificacionDTO> lstFindNotificaciones;
	
	@PostConstruct
	public void init()  {
		
	}
	
	
	public void buscar()throws Exception{
		try{
				
			lstFindNotificaciones=controlycobranzaBo.getAllNotificaciones(fechaInicio, fechaFin);
			
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	public void exportarExcel() {

		HSSFWorkbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("Exportando a Excel");
		int rowIndex = 1;
		Row row1 = sheet.createRow(0);
		row1.createCell(0).setCellValue("Notificador");
		row1.createCell(1).setCellValue("Tipo Notificación");
		row1.createCell(2).setCellValue("Fecha Notif.");
		row1.createCell(3).setCellValue("Numero valor");
		row1.createCell(4).setCellValue("Tipo valor");
		row1.createCell(5).setCellValue("Fecha Registro");
		row1.createCell(6).setCellValue("Usuario Registro");

		for (ReporteNotificacionDTO data : lstFindNotificaciones) {
			Row row = sheet.createRow(rowIndex++);
			int columnIndex = 0;

			row.createCell(columnIndex++).setCellValue(data.getNotificador());
			row.createCell(columnIndex++).setCellValue(data.getTipoNotificacion());
			
			if (data.getFechaNotificacion() != null) {
				row.createCell(columnIndex++).setCellValue(DateUtil.convertDateToString(data.getFechaNotificacion()));
			}
			
			row.createCell(columnIndex++).setCellValue(data.getNroValor());
			row.createCell(columnIndex++).setCellValue(data.getTipoValor());
			
			if (data.getFechaRegistro() != null) {
				row.createCell(columnIndex++).setCellValue(DateUtil.convertDateToString(data.getFechaRegistro()));
			}
			row.createCell(columnIndex++).setCellValue(data.getUsuarioRegistro());

		}
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		externalContext.responseReset();
		externalContext.setResponseContentType("application/vnd.ms-excel");
		externalContext.setResponseHeader("Content-Disposition",
				"attachment;filename=exportando_a_excel.xls");
		try {
			workbook.write(externalContext.getResponseOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		context.responseComplete(); // Prevent JSF from performing navigation.
	}
	
	
	

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}


	public List<ReporteNotificacionDTO> getLstFindNotificaciones() {
		return lstFindNotificaciones;
	}


	public void setLstFindNotificaciones(List<ReporteNotificacionDTO> lstFindNotificaciones) {
		this.lstFindNotificaciones = lstFindNotificaciones;
	}
	
	

}
