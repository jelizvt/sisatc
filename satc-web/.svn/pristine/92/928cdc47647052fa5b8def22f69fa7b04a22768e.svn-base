package com.sat.sisat.caja.managed;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import com.sat.sisat.caja.business.CajaBoRemote;
import com.sat.sisat.caja.dto.TramoSaldoDTO;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.SATWEBParameterFactory;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.persistence.CrudServiceBean;
import com.sat.sisat.predial.business.CalculoPredialBoRemote;

@ManagedBean
@ViewScoped
public class ReporteSaldosMensualManaged extends BaseManaged {

	private static final long serialVersionUID = 6860634771670968735L;

	@EJB
	CajaBoRemote cajaBoRemote;
	
	
    private Integer tramo_id;
	private String tramo_fechas;
	
	private List<SelectItem> listaTramos = new ArrayList<SelectItem>(); 

	//private List<ReporteDJVehicularDTO> listaVehicularDTOs;
	
	// Variables reusabilidad
	private String pantallaUso;
	private String destinoRefresh;
	

	@PostConstruct
	public void init()  {
		
		List<TramoSaldoDTO> listTramoSaldoDTOs =  cajaBoRemote.obtenerTramos();
		for(TramoSaldoDTO tramoSaldoDTO:listTramoSaldoDTOs){
			listaTramos.add(new SelectItem(tramoSaldoDTO.getTramoId(), tramoSaldoDTO.getTramoDescripcion()));
		}
		 
	}

	public void previewDJ(){
		
	}
	
	public void imprimirXLSInsoluto()
	{
		java.sql.Connection connection=null;
		try {
			CrudServiceBean connj=CrudServiceBean.getInstance();
			connection=connj.connectJasper();
//			String path_context = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
//			String path_report = path_context + "/sisat/reportes/";
//			String path_imagen = path_context + "/sisat/reportes/imagen/";
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("tramo_id", getTramo_id());
			parameters.put("tipo_saldo", 1);
			parameters.put("ruta_image", SATWEBParameterFactory.getPathReporteImagenes());
			
//			parameters.put("ruta_image",path_imagen);
			JasperPrint jasperPrint = JasperFillManager
					.fillReport((SATWEBParameterFactory.getPathReporte() + "saldos.jasper"),parameters,connection);

			ByteArrayOutputStream output = new ByteArrayOutputStream();			
			
			 JRXlsExporter exporterXls = new JRXlsExporter ();  
			    exporterXls.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);  
			    exporterXls.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, true);  
			    exporterXls.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, true);  
			    exporterXls.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, false);  
			    exporterXls.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, true);  
			    exporterXls.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, true);  
			    exporterXls.setParameter(JRXlsExporterParameter.MAXIMUM_ROWS_PER_SHEET, 20000); 
			    exporterXls.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);  
			    exporterXls.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "Ops_inubicables_predial" + ".xls");  
			    exporterXls.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, output);  
			    exporterXls.exportReport();   


			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			response.setContentType("application/vnd.ms-excel");  
		    response.setHeader("Content-Disposition", "attachment;filename=" + "reporte_saldos_insoluto" + ".xls");  
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
	
		
	public void imprimirPdfInsoluto() {

		java.sql.Connection connection = null;
		try {
			CrudServiceBean connj = CrudServiceBean.getInstance();
			connection = connj.connectJasper();

			HashMap<String, Object> parameters = new HashMap<String, Object>();
			
			parameters.put("tramo_id", getTramo_id());
			parameters.put("tipo_saldo", 1);
			parameters.put("ruta_image", SATWEBParameterFactory.getPathReporteImagenes());
						
			parameters.put("SUBREPORT_DIR", SATWEBParameterFactory.getPathReporte());
			parameters.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
			
			
			JasperPrint jasperPrint = JasperFillManager
					.fillReport((SATWEBParameterFactory.getPathReporte() + "saldos.jasper"),
							parameters,
							connection);

			ByteArrayOutputStream output = new ByteArrayOutputStream();

			JasperExportManager.exportReportToPdfStream(jasperPrint, output);

			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment;filename=reporte_saldo_insoluto.pdf");

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
	
	public void imprimirXLSDerecho()
	{
		java.sql.Connection connection=null;
		try {
			CrudServiceBean connj=CrudServiceBean.getInstance();
			connection=connj.connectJasper();
//			String path_context = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
//			String path_report = path_context + "/sisat/reportes/";
//			String path_imagen = path_context + "/sisat/reportes/imagen/";
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("tramo_id", getTramo_id());
			parameters.put("tipo_saldo", 2);
			parameters.put("ruta_image", SATWEBParameterFactory.getPathReporteImagenes());
			
//			parameters.put("ruta_image",path_imagen);
			JasperPrint jasperPrint = JasperFillManager
					.fillReport((SATWEBParameterFactory.getPathReporte() + "saldos.jasper"),parameters,connection);

			ByteArrayOutputStream output = new ByteArrayOutputStream();			
			
			 JRXlsExporter exporterXls = new JRXlsExporter ();  
			    exporterXls.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);  
			    exporterXls.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, true);  
			    exporterXls.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, true);  
			    exporterXls.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, false);  
			    exporterXls.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, true);  
			    exporterXls.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, true);  
			    exporterXls.setParameter(JRXlsExporterParameter.MAXIMUM_ROWS_PER_SHEET, 20000); 
			    exporterXls.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);  
			    exporterXls.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "Ops_inubicables_predial" + ".xls");  
			    exporterXls.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, output);  
			    exporterXls.exportReport();   


			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			response.setContentType("application/vnd.ms-excel");  
		    response.setHeader("Content-Disposition", "attachment;filename=" + "reporte_saldos_insoluto" + ".xls");  
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
	
	
	public void imprimirPdfDerecho() {

		java.sql.Connection connection = null;
		try {
			CrudServiceBean connj = CrudServiceBean.getInstance();
			connection = connj.connectJasper();

			HashMap<String, Object> parameters = new HashMap<String, Object>();
			
			parameters.put("tramo_id", getTramo_id());
			parameters.put("tipo_saldo", 2);			
			parameters.put("ruta_image", SATWEBParameterFactory.getPathReporteImagenes());
			
			parameters.put("SUBREPORT_DIR", SATWEBParameterFactory.getPathReporte());
			parameters.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
			
			
			JasperPrint jasperPrint = JasperFillManager
					.fillReport((SATWEBParameterFactory.getPathReporte() + "saldos.jasper"),
							parameters,
							connection);

			ByteArrayOutputStream output = new ByteArrayOutputStream();

			JasperExportManager.exportReportToPdfStream(jasperPrint, output);

			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment;filename=reporte_saldo_derecho.pdf");

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
	
	public void imprimirPdfReajuste() {

		java.sql.Connection connection = null;
		try {
			CrudServiceBean connj = CrudServiceBean.getInstance();
			connection = connj.connectJasper();

			HashMap<String, Object> parameters = new HashMap<String, Object>();
			
			parameters.put("tramo_id", getTramo_id());
			parameters.put("tipo_saldo", 3);			
			parameters.put("ruta_image", SATWEBParameterFactory.getPathReporteImagenes());			
			
			parameters.put("SUBREPORT_DIR", SATWEBParameterFactory.getPathReporte());
			parameters.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
			
			
			JasperPrint jasperPrint = JasperFillManager
					.fillReport((SATWEBParameterFactory.getPathReporte() + "saldos.jasper"),
							parameters,
							connection);

			ByteArrayOutputStream output = new ByteArrayOutputStream();

			JasperExportManager.exportReportToPdfStream(jasperPrint, output);

			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment;filename=reporte_saldo_reajuste.pdf");

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
	
	public void imprimirXLSReajuste()
	{
		java.sql.Connection connection=null;
		try {
			CrudServiceBean connj=CrudServiceBean.getInstance();
			connection=connj.connectJasper();
//			String path_context = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
//			String path_report = path_context + "/sisat/reportes/";
//			String path_imagen = path_context + "/sisat/reportes/imagen/";
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("tramo_id", getTramo_id());
			parameters.put("tipo_saldo", 3);
			parameters.put("ruta_image", SATWEBParameterFactory.getPathReporteImagenes());
			
//			parameters.put("ruta_image",path_imagen);
			JasperPrint jasperPrint = JasperFillManager
					.fillReport((SATWEBParameterFactory.getPathReporte() + "saldos.jasper"),parameters,connection);

			ByteArrayOutputStream output = new ByteArrayOutputStream();			
			
			 JRXlsExporter exporterXls = new JRXlsExporter ();  
			    exporterXls.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);  
			    exporterXls.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, true);  
			    exporterXls.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, true);  
			    exporterXls.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, false);  
			    exporterXls.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, true);  
			    exporterXls.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, true);  
			    exporterXls.setParameter(JRXlsExporterParameter.MAXIMUM_ROWS_PER_SHEET, 20000); 
			    exporterXls.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);  
			    exporterXls.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "Ops_inubicables_predial" + ".xls");  
			    exporterXls.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, output);  
			    exporterXls.exportReport();   


			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			response.setContentType("application/vnd.ms-excel");  
		    response.setHeader("Content-Disposition", "attachment;filename=" + "reporte_saldos_insoluto" + ".xls");  
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
	
	public void imprimirXLSInteres()
	{
		java.sql.Connection connection=null;
		try {
			CrudServiceBean connj=CrudServiceBean.getInstance();
			connection=connj.connectJasper();
//			String path_context = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
//			String path_report = path_context + "/sisat/reportes/";
//			String path_imagen = path_context + "/sisat/reportes/imagen/";
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("tramo_id", getTramo_id());
			parameters.put("tipo_saldo", 4);
			parameters.put("ruta_image", SATWEBParameterFactory.getPathReporteImagenes());
			
//			parameters.put("ruta_image",path_imagen);
			JasperPrint jasperPrint = JasperFillManager
					.fillReport((SATWEBParameterFactory.getPathReporte() + "saldos.jasper"),parameters,connection);

			ByteArrayOutputStream output = new ByteArrayOutputStream();			
			
			 JRXlsExporter exporterXls = new JRXlsExporter ();  
			    exporterXls.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);  
			    exporterXls.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, true);  
			    exporterXls.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, true);  
			    exporterXls.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, false);  
			    exporterXls.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, true);  
			    exporterXls.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, true);  
			    exporterXls.setParameter(JRXlsExporterParameter.MAXIMUM_ROWS_PER_SHEET, 20000); 
			    exporterXls.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);  
			    exporterXls.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "Ops_inubicables_predial" + ".xls");  
			    exporterXls.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, output);  
			    exporterXls.exportReport();   


			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			response.setContentType("application/vnd.ms-excel");  
		    response.setHeader("Content-Disposition", "attachment;filename=" + "reporte_saldos_insoluto" + ".xls");  
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
	
	
	public void imprimirPdfInteres() {

		java.sql.Connection connection = null;
		try {
			CrudServiceBean connj = CrudServiceBean.getInstance();
			connection = connj.connectJasper();

			HashMap<String, Object> parameters = new HashMap<String, Object>();
			
			System.out.println("RUTA: "+SATWEBParameterFactory.getPathReporteImagenes());
			
			parameters.put("tramo_id", getTramo_id());
			parameters.put("tipo_saldo", 4);			
			parameters.put("ruta_image", SATWEBParameterFactory.getPathReporteImagenes());
			
			parameters.put("SUBREPORT_DIR", SATWEBParameterFactory.getPathReporte());
			parameters.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
			
			
			JasperPrint jasperPrint = JasperFillManager
					.fillReport((SATWEBParameterFactory.getPathReporte() + "saldos.jasper"),
							parameters,
							connection);

			ByteArrayOutputStream output = new ByteArrayOutputStream();

			JasperExportManager.exportReportToPdfStream(jasperPrint, output);

			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment;filename=reporte_saldo_interes.pdf");

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
	
	public void imprimirXLSOtros()
	{
		java.sql.Connection connection=null;
		try {
			CrudServiceBean connj=CrudServiceBean.getInstance();
			connection=connj.connectJasper();
//			String path_context = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
//			String path_report = path_context + "/sisat/reportes/";
//			String path_imagen = path_context + "/sisat/reportes/imagen/";
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("tramo_id", getTramo_id());		
			parameters.put("ruta_image", SATWEBParameterFactory.getPathReporteImagenes());
			
//			parameters.put("ruta_image",path_imagen);
			JasperPrint jasperPrint = JasperFillManager
					.fillReport((SATWEBParameterFactory.getPathReporte() + "otros_saldos.jasper"),parameters,connection);

			ByteArrayOutputStream output = new ByteArrayOutputStream();			
			
			 JRXlsExporter exporterXls = new JRXlsExporter ();  
			    exporterXls.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);  
			    exporterXls.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, true);  
			    exporterXls.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, true);  
			    exporterXls.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, false);  
			    exporterXls.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, true);  
			    exporterXls.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, true);  
			    exporterXls.setParameter(JRXlsExporterParameter.MAXIMUM_ROWS_PER_SHEET, 20000); 
			    exporterXls.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);  
			    exporterXls.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "Ops_inubicables_predial" + ".xls");  
			    exporterXls.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, output);  
			    exporterXls.exportReport();   


			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			response.setContentType("application/vnd.ms-excel");  
		    response.setHeader("Content-Disposition", "attachment;filename=" + "reporte_saldos_otros" + ".xls");  
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
	
	public void imprimirPdfOtros() {

		java.sql.Connection connection = null;
		try {
			CrudServiceBean connj = CrudServiceBean.getInstance();
			connection = connj.connectJasper();

			HashMap<String, Object> parameters = new HashMap<String, Object>();
			
			System.out.println("RUTA: "+SATWEBParameterFactory.getPathReporteImagenes());
			
			parameters.put("tramo_id", getTramo_id());					
			parameters.put("ruta_image", SATWEBParameterFactory.getPathReporteImagenes());
			
			parameters.put("SUBREPORT_DIR", SATWEBParameterFactory.getPathReporte());
			parameters.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
			
			
			JasperPrint jasperPrint = JasperFillManager
					.fillReport((SATWEBParameterFactory.getPathReporte() + "otros_saldos.jasper"),
							parameters,
							connection);

			ByteArrayOutputStream output = new ByteArrayOutputStream();

			JasperExportManager.exportReportToPdfStream(jasperPrint, output);

			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment;filename=reporte_saldo_otros.pdf");

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

	public Integer getTramo_id() {
		return tramo_id;
	}

	public void setTramo_id(Integer tramo_id) {
		this.tramo_id = tramo_id;
	}

	public String getTramo_fechas() {
		return tramo_fechas;
	}

	public void setTramo_fechas(String tramo_fechas) {
		this.tramo_fechas = tramo_fechas;
	}

	public List<SelectItem> getListaTramos() {
		return listaTramos;
	}

	public void setListaTramos(List<SelectItem> listaTramos) {
		this.listaTramos = listaTramos;
	}



}
