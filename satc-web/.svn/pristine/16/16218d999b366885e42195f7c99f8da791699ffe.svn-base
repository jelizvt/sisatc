package com.sat.sisat.consultasreportes.managed;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.SATWEBParameterFactory;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.persistence.CrudServiceBean;


@ManagedBean
@ViewScoped
public class ReporteDJPredialManaged extends BaseManaged {

	private static final long serialVersionUID = 6860634771670968735L;

    private Date fechaInicio = Calendar.getInstance().getTime();
	private Date fechaFin = Calendar.getInstance().getTime();
	
	
		// Variables reusabilidad
	private String pantallaUso;
	private String destinoRefresh;

	public ReporteDJPredialManaged() {
		 
	}

	@PostConstruct
	public void init()  {
		
	}

	public void previewDJ(){
		
	}
	
		
	public void imprimirPdfPrediales() {

		java.sql.Connection connection = null;
		try {
			CrudServiceBean connj = CrudServiceBean.getInstance();
			connection = connj.connectJasper();

			HashMap<String, Object> parameters = new HashMap<String, Object>();
			
			parameters.put("fecI", new Timestamp(fechaInicio.getTime()));
			parameters.put("fecF", new Timestamp((DateUtil.moverHoraAlFinalDelDia(fechaFin)).getTime()));
			
						
			parameters.put("SUBREPORT_DIR", SATWEBParameterFactory.getPathReporte());
			parameters.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
			
			
			JasperPrint jasperPrint = JasperFillManager
					.fillReport((SATWEBParameterFactory.getPathReporte() + "reporte_dj_predial_pendientes.jasper"),
							parameters,
							connection);

			ByteArrayOutputStream output = new ByteArrayOutputStream();

			JasperExportManager.exportReportToPdfStream(jasperPrint, output);

			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment;filename=reporte_dj_predial_pendientes.pdf");

			response.setContentLength(output.size());
			ServletOutputStream servletOutputStream = response.getOutputStream();
			servletOutputStream.write(output.toByteArray(), 0, output.size());
			servletOutputStream.flush();
			servletOutputStream.close();
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception jre) {
			jre.printStackTrace();
			WebMessages.messageFatal(jre);
		} finally {
			try {
				if (connection != null) {
					connection.close();
					connection = null;
				}
			} catch (Exception e) {
			}
		}
	}
	
	
	public void imprimirXlsPrediales() {
		java.sql.Connection connection=null;
		try {
			CrudServiceBean connj=CrudServiceBean.getInstance();
			connection=connj.connectJasper();
			    
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("fecI", new Timestamp(fechaInicio.getTime()));
			parameters.put("fecF", new Timestamp((DateUtil.moverHoraAlFinalDelDia(fechaFin)).getTime()));
			
			JasperPrint jasperPrint = JasperFillManager.fillReport((SATWEBParameterFactory.getPathReporte()+"reporte_dj_predial_pendientes.jasper"),parameters,connection);
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			
			JRXlsExporter exporterXls = new JRXlsExporter();
			    exporterXls.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);  
			    exporterXls.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, true);  
			    exporterXls.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, true);  
			    exporterXls.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, false);  
			    exporterXls.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, true);  
			    exporterXls.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, true);  
			    exporterXls.setParameter(JRXlsExporterParameter.MAXIMUM_ROWS_PER_SHEET, 20000); 
			    exporterXls.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);  
			    exporterXls.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, System.currentTimeMillis() + "Dj_Predial_Pendientes" + ".xls");  
			    exporterXls.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, output);  
			    exporterXls.exportReport();

			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			response.setContentType("application/vnd.ms-excel");  
		    response.setHeader("Content-Disposition", "attachment;filename=" + System.currentTimeMillis() + "Dj_Predial_Pendientes.xls");  
			response.setContentLength(output.size());
			ServletOutputStream servletOutputStream = response.getOutputStream();
			servletOutputStream.write(output.toByteArray(), 0, output.size());
			servletOutputStream.flush();
			servletOutputStream.close();
			FacesContext.getCurrentInstance().responseComplete();
			
		} catch (Exception jre) {
			jre.printStackTrace();
		}finally{
	    	 try{
	    		 if(connection!=null){
	    			 connection.close();
	    			 connection=null;
	    		 }
	    	 }catch(Exception e){}
	     }
	}
	
	
	public void imprimirConsolidadoPdf() {

		java.sql.Connection connection = null;
		try {
			CrudServiceBean connj = CrudServiceBean.getInstance();
			connection = connj.connectJasper();

			HashMap<String, Object> parameters = new HashMap<String, Object>();
			
			parameters.put("fecha_inicio", new Timestamp(fechaInicio.getTime()));
			parameters.put("fecha_fin", new Timestamp((DateUtil.moverHoraAlFinalDelDia(fechaFin)).getTime()));
			
						
			parameters.put("SUBREPORT_DIR", SATWEBParameterFactory.getPathReporte());
			parameters.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
			
			
			JasperPrint jasperPrint = JasperFillManager
					.fillReport((SATWEBParameterFactory.getPathReporte() + "reportedjpredialconsolidado.jasper"),
							parameters,
							connection);

			ByteArrayOutputStream output = new ByteArrayOutputStream();

			JasperExportManager.exportReportToPdfStream(jasperPrint, output);

			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment;filename=Dj_Predial_Consolidado.pdf");

			response.setContentLength(output.size());
			ServletOutputStream servletOutputStream = response.getOutputStream();
			servletOutputStream.write(output.toByteArray(), 0, output.size());
			servletOutputStream.flush();
			servletOutputStream.close();
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception jre) {
			jre.printStackTrace();
			WebMessages.messageFatal(jre);
		} finally {
			try {
				if (connection != null) {
					connection.close();
					connection = null;
				}
			} catch (Exception e) {
			}
		}
	}
	
	public void imprimirConsolidadoXLS() {
		java.sql.Connection connection=null;
		try {
			CrudServiceBean connj=CrudServiceBean.getInstance();
			connection=connj.connectJasper();
			    
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("fecha_inicio", new Timestamp(fechaInicio.getTime()));
			parameters.put("fecha_fin", new Timestamp((DateUtil.moverHoraAlFinalDelDia(fechaFin)).getTime()));
			
			JasperPrint jasperPrint = JasperFillManager.fillReport((SATWEBParameterFactory.getPathReporte()+"reportedjpredialconsolidado.jasper"),parameters,connection);
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			
			JRXlsExporter exporterXls = new JRXlsExporter();
			    exporterXls.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);  
			    exporterXls.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, true);  
			    exporterXls.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, true);  
			    exporterXls.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, false);  
			    exporterXls.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, true);  
			    exporterXls.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, true);  
			    exporterXls.setParameter(JRXlsExporterParameter.MAXIMUM_ROWS_PER_SHEET, 20000); 
			    exporterXls.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);  
			    exporterXls.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, System.currentTimeMillis() + "Dj_Predial_Consolidado" + ".xls");  
			    exporterXls.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, output);  
			    exporterXls.exportReport();

			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			response.setContentType("application/vnd.ms-excel");  
		    response.setHeader("Content-Disposition", "attachment;filename=" + System.currentTimeMillis() + "Dj_Predial_Consolidado.xls");  
			response.setContentLength(output.size());
			ServletOutputStream servletOutputStream = response.getOutputStream();
			servletOutputStream.write(output.toByteArray(), 0, output.size());
			servletOutputStream.flush();
			servletOutputStream.close();
			FacesContext.getCurrentInstance().responseComplete();
			
		} catch (Exception jre) {
			jre.printStackTrace();
		}finally{
	    	 try{
	    		 if(connection!=null){
	    			 connection.close();
	    			 connection=null;
	    		 }
	    	 }catch(Exception e){}
	     }
	}

	public String getPantallaUso() {
		return pantallaUso;
	}

	public void setPantallaUso(String pantallaUso) {
		this.pantallaUso = pantallaUso;
	}

	public String getDestinoRefresh() {
		return destinoRefresh;
	}

	public void setDestinoRefresh(String destinoRefresh) {
		this.destinoRefresh = destinoRefresh;
	}

	

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}



}
