package com.sat.sisat.caja.managed;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

import com.sat.sisat.caja.business.CajaBoRemote;
import com.sat.sisat.caja.dto.CajeroDTO;
import com.sat.sisat.caja.dto.CajeroRecaudacionDTO;
import com.sat.sisat.caja.dto.ReporteCuentaDTO;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.SATWEBParameterFactory;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.persistence.CrudServiceBean;

@ManagedBean
@ViewScoped
public class GenerarPartidasManaged extends BaseManaged {

	private static final long serialVersionUID = -3882058657072954285L;
	@EJB
	CajaBoRemote cajaBo;

	private Date fechaInicio = Calendar.getInstance().getTime();	
	
	private Date fechaFin = Calendar.getInstance().getTime();
	
	private Date fechaInicioCJ = Calendar.getInstance().getTime();
	
	private Date fechaFinCJ = Calendar.getInstance().getTime();
	
	private List<CajeroDTO> listaCajeros;
	
	private List<CajeroRecaudacionDTO> listaCajeroRecaudacion;
	
	private List<ReporteCuentaDTO> listaCuentaDTOs;
	
	private HashMap<Integer, String> partidasDescripcion;		
	
	private Integer partidaId = 124;
	@PostConstruct
	public void init() {
		partidasDescripcion = new HashMap<Integer, String>();
		
		Calendar cal = Calendar.getInstance();
		
		cal.set(Calendar.DAY_OF_MONTH, 1);
		
		fechaInicioCJ = cal.getTime(); 
		
		partidasDescripcion.put(124, "DERECHOS Y OBLIGACIONES - FONDO");
		partidasDescripcion.put(125, "GUARDIANÍA - DEPÓSITO DE VEHÍCULOS");
		partidasDescripcion.put(126, "DERECHO DE PERMISO DE OPERACIONES");
		partidasDescripcion.put(127, "DERECHO DE INSCRIPCIÓN - TRANSPORTES");
		partidasDescripcion.put(128, "OTROS PERMISOS PROVICIONALES TRANS.GENER.");
		partidasDescripcion.put(129, "FORMULARIOS");		
		
	}	
	
	public void buscar() {

		try {
			listaCajeros = cajaBo.obtenerCajeros(fechaInicio, DateUtil.moverHoraAlFinalDelDia(fechaFin));
			
			listaCajeroRecaudacion = cajaBo.obtenerCajerosRecaudacion(DateUtil.moverHoraAlInicioDelDia(fechaInicio), DateUtil.moverHoraAlFinalDelDia(fechaFin));
		} catch (SisatException e) {

			addErrorMessage(e.getMessage());
		}

	}

	public void previewPartidas(){
		
	}
	
	public void generarReportesPartidasPDF()throws Exception {
		java.sql.Connection connection=null;
		try {
			CrudServiceBean connj=CrudServiceBean.getInstance();
			connection=connj.connectJasper();
			 
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("fecha_inicio", new Timestamp(fechaInicio.getTime()));
			parameters.put("fecha_fin", new Timestamp((DateUtil.moverHoraAlFinalDelDia(fechaFin)).getTime()));		

			parameters.put("ruta_image", SATWEBParameterFactory.getPathReporteImagenes());
			parameters.put("SUBREPORT_DIR", SATWEBParameterFactory.getPathReporte());
			parameters.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
			JasperPrint jasperPrint = JasperFillManager
					.fillReport((SATWEBParameterFactory.getPathReporte() + "cj_reporte_diario_partidas.jasper"),
							parameters,
							connection);

			ByteArrayOutputStream output = new ByteArrayOutputStream();			
			
			JasperExportManager.exportReportToPdfStream(jasperPrint, output);

			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment;filename=DetallePartidas.pdf");
			 
			response.setContentLength(output.size());
			ServletOutputStream servletOutputStream = response.getOutputStream();
			servletOutputStream.write(output.toByteArray(), 0, output.size());
			servletOutputStream.flush();
			servletOutputStream.close();
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception jre) {
			jre.printStackTrace();
			WebMessages.messageFatal(jre);
		}finally{
	    	 try{
	    		 if(connection!=null){
	    			 connection.close();
	    			 connection=null;
	    		 }
	    	 }catch(Exception e){}
	     }
	}
	
	public void generarReportesPartidasXLS() {
		java.sql.Connection connection=null;
		try {
			CrudServiceBean connj=CrudServiceBean.getInstance();
			connection=connj.connectJasper();

			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("fecha_inicio", new Timestamp(fechaInicio.getTime()));
			parameters.put("fecha_fin", new Timestamp((DateUtil.moverHoraAlFinalDelDia(fechaFin)).getTime()));
		
			parameters.put("ruta_image", SATWEBParameterFactory.getPathReporteImagenes());
			parameters.put("SUBREPORT_DIR", SATWEBParameterFactory.getPathReporte());
			JasperPrint jasperPrint = JasperFillManager
					.fillReport((SATWEBParameterFactory.getPathReporte() + "cj_reporte_diario_partidas.jasper"),
							parameters,
							connection);

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
			    exporterXls.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, System.currentTimeMillis() + "prueba" + ".xls");  
			    exporterXls.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, output);  
			    exporterXls.exportReport();   


			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			response.setContentType("application/vnd.ms-excel");  
		    response.setHeader("Content-Disposition", "attachment;filename=" + System.currentTimeMillis() + ".xls");  
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

	public void generarReportesPartidasSOATPDF()throws Exception {
		java.sql.Connection connection=null;
		try {
			CrudServiceBean connj=CrudServiceBean.getInstance();
			connection=connj.connectJasper();
			 
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("fecha_inicio", new Timestamp(fechaInicio.getTime()));
			parameters.put("fecha_fin", new Timestamp((DateUtil.moverHoraAlFinalDelDia(fechaFin)).getTime()));

			parameters.put("ruta_image", SATWEBParameterFactory.getPathReporteImagenes());
			parameters.put("SUBREPORT_DIR", SATWEBParameterFactory.getPathReporte());
			parameters.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
			JasperPrint jasperPrint = JasperFillManager
					.fillReport((SATWEBParameterFactory.getPathReporte() + "cj_reporte_diario_partidas_SOAT.jasper"),
							parameters,
							connection);

			ByteArrayOutputStream output = new ByteArrayOutputStream();			
			
			JasperExportManager.exportReportToPdfStream(jasperPrint, output);

			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment;filename=DetallePartidas.pdf");
			 
			response.setContentLength(output.size());
			ServletOutputStream servletOutputStream = response.getOutputStream();
			servletOutputStream.write(output.toByteArray(), 0, output.size());
			servletOutputStream.flush();
			servletOutputStream.close();
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception jre) {
			jre.printStackTrace();
			WebMessages.messageFatal(jre);
		}finally{
	    	 try{
	    		 if(connection!=null){
	    			 connection.close();
	    			 connection=null;
	    		 }
	    	 }catch(Exception e){}
	     }
	}
	
	public void generarReportesPartidasSOATXLS() {
		java.sql.Connection connection=null;
		try {
			CrudServiceBean connj=CrudServiceBean.getInstance();
			connection=connj.connectJasper();
			 
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("fecha_inicio", new Timestamp(fechaInicio.getTime()));
			parameters.put("fecha_fin", new Timestamp((DateUtil.moverHoraAlFinalDelDia(fechaFin)).getTime()));
			
			parameters.put("ruta_image", SATWEBParameterFactory.getPathReporteImagenes());
			parameters.put("SUBREPORT_DIR", SATWEBParameterFactory.getPathReporte());
			JasperPrint jasperPrint = JasperFillManager
					.fillReport((SATWEBParameterFactory.getPathReporte() + "cj_reporte_diario_partidas_SOAT.jasper"),
							parameters,
							connection);

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
			    exporterXls.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, System.currentTimeMillis() + "prueba" + ".xls");  
			    exporterXls.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, output);  
			    exporterXls.exportReport();   


			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			response.setContentType("application/vnd.ms-excel");  
		    response.setHeader("Content-Disposition", "attachment;filename=" + System.currentTimeMillis() + ".xls");  
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
	
	public void generarReportesPartidasMTPDF()throws Exception {
		java.sql.Connection connection=null;
		try {
			int PARTIDA_MULTA = 133;
			
			CrudServiceBean connj=CrudServiceBean.getInstance();
			connection=connj.connectJasper();
			 
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("fecha_inicio", new Timestamp(fechaInicio.getTime()));
			parameters.put("fecha_fin", new Timestamp((DateUtil.moverHoraAlFinalDelDia(fechaFin)).getTime()));
			parameters.put("concepto_id", PARTIDA_MULTA);

			parameters.put("ruta_image", SATWEBParameterFactory.getPathReporteImagenes());
			parameters.put("SUBREPORT_DIR", SATWEBParameterFactory.getPathReporte());
			parameters.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
			JasperPrint jasperPrint = JasperFillManager
					.fillReport((SATWEBParameterFactory.getPathReporte() + "cj_reporte_diario_partidas_MT.jasper"),
							parameters,
							connection);

			ByteArrayOutputStream output = new ByteArrayOutputStream();			
			
			JasperExportManager.exportReportToPdfStream(jasperPrint, output);

			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment;filename=DetallePartidasMT.pdf");
			 
			response.setContentLength(output.size());
			ServletOutputStream servletOutputStream = response.getOutputStream();
			servletOutputStream.write(output.toByteArray(), 0, output.size());
			servletOutputStream.flush();
			servletOutputStream.close();
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception jre) {
			jre.printStackTrace();
			WebMessages.messageFatal(jre);
		}finally{
	    	 try{
	    		 if(connection!=null){
	    			 connection.close();
	    			 connection=null;
	    		 }
	    	 }catch(Exception e){}
	     }
	}
	
	public void generarReportesPartidasMTXLS() {
		java.sql.Connection connection=null;
		try {
			int PARTIDA_MULTA = 133;
			
			CrudServiceBean connj=CrudServiceBean.getInstance();
			connection=connj.connectJasper();
			 
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("fecha_inicio", new Timestamp(fechaInicio.getTime()));
			parameters.put("fecha_fin", new Timestamp((DateUtil.moverHoraAlFinalDelDia(fechaFin)).getTime()));
			parameters.put("concepto_id", PARTIDA_MULTA);
			
			parameters.put("ruta_image", SATWEBParameterFactory.getPathReporteImagenes());
			parameters.put("SUBREPORT_DIR", SATWEBParameterFactory.getPathReporte());
			JasperPrint jasperPrint = JasperFillManager
					.fillReport((SATWEBParameterFactory.getPathReporte() + "cj_reporte_diario_partidas_MT.jasper"),
							parameters,
							connection);

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
			    exporterXls.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, System.currentTimeMillis() + "prueba" + ".xls");  
			    exporterXls.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, output);  
			    exporterXls.exportReport();   

			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			response.setContentType("application/vnd.ms-excel");  
		    response.setHeader("Content-Disposition", "attachment;filename=" + System.currentTimeMillis() + ".xls");  
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
	
	public void generarReportesPartidasSinReparticion() {
		java.sql.Connection connection=null;
		try {
			CrudServiceBean connj=CrudServiceBean.getInstance();
			connection=connj.connectJasper();
			 		
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("fecha_inicio", new Timestamp(fechaInicio.getTime()));
			parameters.put("fecha_fin", new Timestamp((DateUtil.moverHoraAlFinalDelDia(fechaFin)).getTime()));

			parameters.put("ruta_image", SATWEBParameterFactory.getPathReporteImagenes());
			parameters.put("SUBREPORT_DIR", SATWEBParameterFactory.getPathReporte());
			parameters.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
			JasperPrint jasperPrint = JasperFillManager
					.fillReport((SATWEBParameterFactory.getPathReporte() + "cj_reporte_diario_partidas_sin_reparticion.jasper"),
							parameters,
							connection);
			
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, output);

			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment;filename=DetallePartidas.pdf");
			response.setContentLength(output.size());
			ServletOutputStream servletOutputStream = response.getOutputStream();
			servletOutputStream.write(output.toByteArray(), 0, output.size());
			servletOutputStream.flush();
			servletOutputStream.close();
			FacesContext.getCurrentInstance().responseComplete();
			
		} catch (Exception jre) {
			jre.printStackTrace();
			WebMessages.messageFatal(jre);
		}finally{
	    	 try{
	    		 if(connection!=null){
	    			 connection.close();
	    			 connection=null;
	    		 }
	    	 }catch(Exception e){}
	     }

	}
	
	public void generarReportesPartidaDetalle() {
		java.sql.Connection connection=null;
		try {
			CrudServiceBean connj=CrudServiceBean.getInstance();
			connection=connj.connectJasper();			
			
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("fecha_inicio", new java.sql.Timestamp(DateUtil.moverHoraAlInicioDelDia(fechaInicio).getTime()));
			parameters.put("fecha_fin", new java.sql.Timestamp(DateUtil.moverHoraAlFinalDelDia(fechaFin).getTime()));			

			parameters.put("partidaId", partidaId);
			parameters.put("partidaDescripcion", partidasDescripcion.get(partidaId));
			parameters.put("ruta_image", SATWEBParameterFactory.getPathReporteImagenes());
			parameters.put("SUBREPORT_DIR", SATWEBParameterFactory.getPathReporte());
			parameters.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
			
			JasperPrint jasperPrint = JasperFillManager
					.fillReport((SATWEBParameterFactory.getPathReporte() + "cj_reporte_partida_detalle.jasper"),
							parameters,
							connection);

			ByteArrayOutputStream output = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, output);

			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment;filename=".concat(partidasDescripcion.get(partidaId).replace(" ", "")).concat(".pdf"));
			response.setContentLength(output.size());
			ServletOutputStream servletOutputStream = response.getOutputStream();
			servletOutputStream.write(output.toByteArray(), 0, output.size());
			servletOutputStream.flush();
			servletOutputStream.close();
			FacesContext.getCurrentInstance().responseComplete();
			
		} catch (Exception jre) {
			jre.printStackTrace();
			WebMessages.messageFatal(jre);
		}finally{
	    	 try{
	    		 if(connection!=null){
	    			 connection.close();
	    			 connection=null;
	    		 }
	    	 }catch(Exception e){}
	     }

	}
	
	
	public void generarReportesConsolidadoCaja() {
		java.sql.Connection connection=null;
		try {
			CrudServiceBean connj=CrudServiceBean.getInstance();
			connection=connj.connectJasper();			
			
			HashMap<String, Object> parameters = new HashMap<String, Object>();
				
			
			Calendar cal = Calendar.getInstance();				
			
			parameters.put("fechaInicio",new Timestamp(fechaInicioCJ.getTime()) );
			parameters.put("fechaFin",new Timestamp(fechaFinCJ.getTime()) );
			parameters.put("usuarioIdAsString",this.getUser().getUsuario());
			parameters.put("SUBREPORT_DIR", SATWEBParameterFactory.getPathReporte());
			parameters.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
			
			SimpleDateFormat s = new SimpleDateFormat("dd.MM.yyyy.HH.mm.ss");
			String nombre = "CJ_".concat(s.format(cal.getTime()));
			
			JasperPrint jasperPrint = JasperFillManager
					.fillReport((SATWEBParameterFactory.getPathReporte() + "cj_reporte_consolidado.jasper"),
							parameters,
							connection);

			ByteArrayOutputStream output = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, output);

			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment;filename=".concat(nombre).concat(".pdf"));
			response.setContentLength(output.size());
			ServletOutputStream servletOutputStream = response.getOutputStream();
			servletOutputStream.write(output.toByteArray(), 0, output.size());
			servletOutputStream.flush();
			servletOutputStream.close();
			FacesContext.getCurrentInstance().responseComplete();
			
		} catch (Exception jre) {
			jre.printStackTrace();
			WebMessages.messageFatal(jre);
		}finally{
	    	 try{
	    		 if(connection!=null){
	    			 connection.close();
	    			 connection=null;
	    		 }
	    	 }catch(Exception e){}
	     }

	}
	public void generarReportesConsolidadoCajaXLS() {
		java.sql.Connection connection=null;
		try {
			CrudServiceBean connj=CrudServiceBean.getInstance();
			connection=connj.connectJasper();			
			
			HashMap<String, Object> parameters = new HashMap<String, Object>();
				
			
			Calendar cal = Calendar.getInstance();				
			
			parameters.put("fechaInicio",new Timestamp(fechaInicioCJ.getTime()) );
			parameters.put("fechaFin",new Timestamp(fechaFinCJ.getTime()) );
			parameters.put("usuarioIdAsString",this.getUser().getUsuario());
			parameters.put("SUBREPORT_DIR", SATWEBParameterFactory.getPathReporte());
			parameters.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
			
			SimpleDateFormat s = new SimpleDateFormat("dd.MM.yyyy.HH.mm.ss");
			String nombre = "CJ_".concat(s.format(cal.getTime()));
			
			JasperPrint jasperPrint = JasperFillManager
					.fillReport((SATWEBParameterFactory.getPathReporte() + "cj_reporte_consolidado.jasper"),
							parameters,
							connection);

						
			//----------------------------------
			
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
		    response.setHeader("Content-Disposition", "attachment;filename=" + nombre + ".xls");  
			response.setContentLength(output.size());
			ServletOutputStream servletOutputStream = response.getOutputStream();
			servletOutputStream.write(output.toByteArray(), 0, output.size());
			servletOutputStream.flush();
			servletOutputStream.close();
			FacesContext.getCurrentInstance().responseComplete();
			
		} catch (Exception jre) {
			jre.printStackTrace();
			WebMessages.messageFatal(jre);
		}finally{
	    	 try{
	    		 if(connection!=null){
	    			 connection.close();
	    			 connection=null;
	    		 }
	    	 }catch(Exception e){}
	     }

	}	
	public void verCuentas(){
		
		getSessionMap().put("fechaInicio", fechaInicio);
		getSessionMap().put("fechaFin", fechaFin);
	}
	
	/**  Nuevos Reportes Noviembre 2016
	 * */
	

	public void generarReportesPartidasMPCXLS() {
		java.sql.Connection connection=null;
		try {
			int PARTIDA_MULTAS_MPC = 135;
			
			CrudServiceBean connj=CrudServiceBean.getInstance();
			connection=connj.connectJasper();
			 
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("fecha_inicio", new Timestamp(fechaInicio.getTime()));
			parameters.put("fecha_fin", new Timestamp((DateUtil.moverHoraAlFinalDelDia(fechaFin)).getTime()));
			parameters.put("concepto_id", PARTIDA_MULTAS_MPC);
			parameters.put("ruta_image", SATWEBParameterFactory.getPathReporteImagenes());
			parameters.put("SUBREPORT_DIR", SATWEBParameterFactory.getPathReporte());
			JasperPrint jasperPrint = JasperFillManager.fillReport((SATWEBParameterFactory.getPathReporte() + "cj_reporte_diario_partidas_MTmpc.jasper"),parameters,connection);

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
			    exporterXls.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, System.currentTimeMillis() + "prueba" + ".xls");  
			    exporterXls.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, output);  
			    exporterXls.exportReport();   

			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			response.setContentType("application/vnd.ms-excel");  
		    response.setHeader("Content-Disposition", "attachment;filename=Multas_MPC_" + System.currentTimeMillis() + ".xls");  
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
	
	public void generarReportesPartidasMPCPDF()throws Exception {
		java.sql.Connection connection=null;
		try {
			int PARTIDA_MULTAS_MPC = 135;
			
			CrudServiceBean connj=CrudServiceBean.getInstance();
			connection=connj.connectJasper();
			 
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("fecha_inicio", new Timestamp(fechaInicio.getTime()));
			parameters.put("fecha_fin", new Timestamp((DateUtil.moverHoraAlFinalDelDia(fechaFin)).getTime()));
			parameters.put("concepto_id", PARTIDA_MULTAS_MPC);
			parameters.put("ruta_image", SATWEBParameterFactory.getPathReporteImagenes());
			parameters.put("SUBREPORT_DIR", SATWEBParameterFactory.getPathReporte());
			parameters.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
			JasperPrint jasperPrint = JasperFillManager.fillReport((SATWEBParameterFactory.getPathReporte() + "cj_reporte_diario_partidas_MTmpc.jasper"),parameters,connection);

			ByteArrayOutputStream output = new ByteArrayOutputStream();			
			
			JasperExportManager.exportReportToPdfStream(jasperPrint, output);

			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment;filename=DetallePartidasMPC.pdf");
			 
			response.setContentLength(output.size());
			ServletOutputStream servletOutputStream = response.getOutputStream();
			servletOutputStream.write(output.toByteArray(), 0, output.size());
			servletOutputStream.flush();
			servletOutputStream.close();
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception jre) {
			jre.printStackTrace();
			WebMessages.messageFatal(jre);
		}finally{
	    	 try{
	    		 if(connection!=null){
	    			 connection.close();
	    			 connection=null;
	    		 }
	    	 }catch(Exception e){}
	     }
	}
	
	public void generarReportesPartidasDRTCXLS() {
		java.sql.Connection connection=null;
		try {
			int PARTIDA_MULTAS_DRTC = 136;
			
			CrudServiceBean connj=CrudServiceBean.getInstance();
			connection=connj.connectJasper();
			 
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("fecha_inicio", new Timestamp(fechaInicio.getTime()));
			parameters.put("fecha_fin", new Timestamp((DateUtil.moverHoraAlFinalDelDia(fechaFin)).getTime()));
			parameters.put("concepto_id", PARTIDA_MULTAS_DRTC);
			parameters.put("ruta_image", SATWEBParameterFactory.getPathReporteImagenes());
			parameters.put("SUBREPORT_DIR", SATWEBParameterFactory.getPathReporte());
			JasperPrint jasperPrint = JasperFillManager.fillReport((SATWEBParameterFactory.getPathReporte() + "cj_reporte_diario_partidas_MTmpc.jasper"),parameters,connection);

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
			    exporterXls.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, System.currentTimeMillis() + "prueba" + ".xls");  
			    exporterXls.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, output);  
			    exporterXls.exportReport();   

			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			response.setContentType("application/vnd.ms-excel");  
		    response.setHeader("Content-Disposition", "attachment;filename=" + System.currentTimeMillis() + ".xls");  
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
	
	public void generarReportesPartidasDRTCPDF()throws Exception {
		java.sql.Connection connection=null;
		try {
			int PARTIDA_MULTAS_DRTC = 136;
			
			CrudServiceBean connj=CrudServiceBean.getInstance();
			connection=connj.connectJasper();
			 
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("fecha_inicio", new Timestamp(fechaInicio.getTime()));
			parameters.put("fecha_fin", new Timestamp((DateUtil.moverHoraAlFinalDelDia(fechaFin)).getTime()));
			parameters.put("concepto_id", PARTIDA_MULTAS_DRTC);
			parameters.put("ruta_image", SATWEBParameterFactory.getPathReporteImagenes());
			parameters.put("SUBREPORT_DIR", SATWEBParameterFactory.getPathReporte());
			parameters.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
			JasperPrint jasperPrint = JasperFillManager.fillReport((SATWEBParameterFactory.getPathReporte() + "cj_reporte_diario_partidas_MTmpc.jasper"),parameters,connection);

			ByteArrayOutputStream output = new ByteArrayOutputStream();			
			
			JasperExportManager.exportReportToPdfStream(jasperPrint, output);

			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment;filename=DetallePartidasDRTC.pdf");
			 
			response.setContentLength(output.size());
			ServletOutputStream servletOutputStream = response.getOutputStream();
			servletOutputStream.write(output.toByteArray(), 0, output.size());
			servletOutputStream.flush();
			servletOutputStream.close();
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception jre) {
			jre.printStackTrace();
			WebMessages.messageFatal(jre);
		}finally{
	    	 try{
	    		 if(connection!=null){
	    			 connection.close();
	    			 connection=null;
	    		 }
	    	 }catch(Exception e){}
	     }
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

	public List<CajeroDTO> getListaCajeros() {
		return listaCajeros;
	}

	public void setListaCajeros(List<CajeroDTO> listaCajeros) {
		this.listaCajeros = listaCajeros;
	}

	public List<ReporteCuentaDTO> getListaCuentaDTOs() {
		return listaCuentaDTOs;
	}

	public void setListaCuentaDTOs(List<ReporteCuentaDTO> listaCuentaDTOs) {
		this.listaCuentaDTOs = listaCuentaDTOs;
	}

	public List<CajeroRecaudacionDTO> getListaCajeroRecaudacion() {
		return listaCajeroRecaudacion;
	}

	public void setListaCajeroRecaudacion(List<CajeroRecaudacionDTO> listaCajeroRecaudacion) {
		this.listaCajeroRecaudacion = listaCajeroRecaudacion;
	}

	public Integer getPartidaId() {
		return partidaId;
	}

	public void setPartidaId(Integer partidaId) {
		this.partidaId = partidaId;
	}	

	public Date getFechaInicioCJ() {
		return fechaInicioCJ;
	}

	public void setFechaInicioCJ(Date fechaInicioCJ) {
		this.fechaInicioCJ = fechaInicioCJ;
	}

	public Date getFechaFinCJ() {
		return fechaFinCJ;
	}

	public void setFechaFinCJ(Date fechaFinCJ) {
		this.fechaFinCJ = fechaFinCJ;
	}

}
