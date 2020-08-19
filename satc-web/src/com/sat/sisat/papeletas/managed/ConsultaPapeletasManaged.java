package com.sat.sisat.papeletas.managed;

import java.io.IOException;
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
import org.apache.poi.ss.usermodel.Workbook;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.papeleta.business.PapeletaBoRemote;
import com.sat.sisat.papeleta.dto.ConsultaPapeletaDTO;

@ManagedBean
@ViewScoped
public class ConsultaPapeletasManaged extends BaseManaged {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7714471403770936932L;
	@EJB
	PapeletaBoRemote papeletaBo;	
	
	private String nroPapeleta;
	
	private Date fechaInicio;
	
	private Date fechaFin;
	
	private List<ConsultaPapeletaDTO> consultaPapeletaDTOs;  
	
	private ConsultaPapeletaDTO selectItem;
	
	private List<ConsultaPapeletaDTO> consultaPapeletaDescargo;
	
	public ConsultaPapeletasManaged() throws Exception {
		
	}
	
	@PostConstruct
	public void init(){
		selectItem = new ConsultaPapeletaDTO();
	}
	
	
	public void buscar() {

		try {
			
			if(nroPapeleta!= null){
				nroPapeleta.trim();
			}
			
			if(nroPapeleta.isEmpty()){
				nroPapeleta = null;
			}
			
			consultaPapeletaDTOs = papeletaBo.consultarPapeletas(nroPapeleta, fechaInicio, DateUtil.moverHoraAlFinalDelDia(fechaFin));
			
			if (fechaInicio!=null && fechaFin!=null){
				consulta(nroPapeleta,fechaInicio,fechaFin);
			}
	
		} catch (SisatException e) {

			WebMessages.messageError(e.getMessage());
		}
	}
	
	public void limpiar(){
		nroPapeleta = null;
		fechaInicio = null;
		fechaFin = null;
	}
	
	public void consulta(String numero,Date fIni, Date fFin){
		try {
			consultaPapeletaDescargo=papeletaBo.consultarPapeletaDescargo(numero,fIni,fFin);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void seleccionar(){
//		System.out.println("sdfsdf");
		
		
	}
	
	public void exportarTablaExcel() {

		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("Detalle_Descargo_Prescripcion");
		int rowIndex = 1;
		Row fila = sheet.createRow(0);
		fila.createCell(0).setCellValue("Tipo Doc.");
		fila.createCell(1).setCellValue("Nro. Doc.");
		fila.createCell(2).setCellValue("Fecha Doc.");
		fila.createCell(3).setCellValue("Motivo");
		fila.createCell(4).setCellValue("Monto");
		fila.createCell(5).setCellValue("Fecha Registro");
		fila.createCell(6).setCellValue("Registra");
		fila.createCell(7).setCellValue("Estado");
		fila.createCell(8).setCellValue("Codigo");
		fila.createCell(9).setCellValue("Nro. Papeleta");

		for (ConsultaPapeletaDTO data : consultaPapeletaDescargo) {
			Row row = sheet.createRow(rowIndex++);
			int columnIndex = 0;
	
			row.createCell(columnIndex++).setCellValue(data.getTipoDoc());
			row.createCell(columnIndex++).setCellValue(data.getNroDoc());
			if (data.getFechaDocumentoDescargo() != null) {
				row.createCell(columnIndex++).setCellValue(DateUtil.convertDateToString(data.getFechaDocumentoDescargo()));
			} else {
				row.createCell(columnIndex++).setCellValue("-");
			}
			row.createCell(columnIndex++).setCellValue(data.getDescripcionDescargo());
			row.createCell(columnIndex++).setCellValue(
					String.valueOf(data.getMontoMulta().doubleValue()));
			if (data.getFechaActualizacion() != null) {
				row.createCell(columnIndex++).setCellValue(DateUtil.convertDateToString(data.getFechaActualizacion()));
			} else {
				row.createCell(columnIndex++).setCellValue("-");
			}
			row.createCell(columnIndex++).setCellValue(data.getResponsable());
			row.createCell(columnIndex++).setCellValue(data.getTipoDescargo());
			row.createCell(columnIndex++).setCellValue(data.getCodigo());
			row.createCell(columnIndex++).setCellValue(data.getNroPapeleta());

		}
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		externalContext.responseReset();
		externalContext.setResponseContentType("application/vnd.ms-excel");
		externalContext.setResponseHeader("Content-Disposition",
				"attachment;filename=descargo_papeleta.xls");
		try {
			workbook.write(externalContext.getResponseOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		context.responseComplete(); // Prevent JSF from performing navigation.
	}
	
	public String getNroPapeleta() {
		return nroPapeleta;
	}

	public void setNroPapeleta(String nroPapeleta) {
		this.nroPapeleta = nroPapeleta;
	}

	public List<ConsultaPapeletaDTO> getConsultaPapeletaDTOs() {
		return consultaPapeletaDTOs;
	}

	public void setConsultaPapeletaDTOs(List<ConsultaPapeletaDTO> consultaPapeletaDTOs) {
		this.consultaPapeletaDTOs = consultaPapeletaDTOs;
	}

	public ConsultaPapeletaDTO getSelectItem() {
		return selectItem;
	}

	public void setSelectItem(ConsultaPapeletaDTO selectItem) {
		this.selectItem = selectItem;
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

	public List<ConsultaPapeletaDTO> getConsultaPapeletaDescargo() {
		return consultaPapeletaDescargo;
	}

	public void setConsultaPapeletaDescargo(
			List<ConsultaPapeletaDTO> consultaPapeletaDescargo) {
		this.consultaPapeletaDescargo = consultaPapeletaDescargo;
	}
	
	
}
