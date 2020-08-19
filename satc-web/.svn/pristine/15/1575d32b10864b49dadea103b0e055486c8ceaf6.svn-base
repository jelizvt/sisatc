package com.sat.sisat.controlycobranza.managed;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.richfaces.component.html.HtmlComboBox;

import com.sat.sisat.cobranzacoactiva.dto.FindCcLoteDetalleActoExp;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.SATWEBParameterFactory;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.controlycobranza.business.ControlyCobranzaBoRemote;
import com.sat.sisat.controlycobranza.dto.FindCcLoteDetalleActo;
import com.sat.sisat.persistence.CrudServiceBean;
import com.sat.sisat.persona.business.PersonaBoRemote;

@ManagedBean
@ViewScoped
public class GenerarEsquelasManaged extends BaseManaged {
	@EJB
	PersonaBoRemote personaBo;
	
	@EJB
	ControlyCobranzaBoRemote controlycobranzaBo;
	
	private Double montoMinimo=new Double(0);
	private HtmlComboBox cmbEstadoDireccion;
	private HtmlComboBox cmbTipoRecordatorio;
	//private HtmlComboBox cmbPagina;//--Paginacion
	
	private Integer estadoDireccionId=new Integer(0);
	private Integer tipoRecordatorioId=new Integer(0);
	//private Integer paginaId=new Integer(0);//--Paginacion
	
	private List<FindCcLoteDetalleActo> lstlotePreliminar;
	//private Page paginaActual;//--Paginacion 
	
	private String deudaPorVencer;
	private String deudaVencida;
	
	@PostConstruct
	public void init() {
		try{
			setDeudaPorVencer("Deuda por vencer (="+DateUtil.getAnioActual()+")");
			setDeudaVencida("Deuda vencida (<="+DateUtil.getAnioActual()+")");
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	public void motrarLotePreliminar(){
		try{
			if(this.getMontoMinimo()!=null && this.getEstadoDireccionId()!=null&&this.getTipoRecordatorioId()!=null){
				Integer periodoActual=DateUtil.getAnioActual();
				lstlotePreliminar=controlycobranzaBo.getAllFindCcLotePreliminarEsquelas(periodoActual,estadoDireccionId,tipoRecordatorioId,montoMinimo);
				//cargaPaginas();//--Paginacion
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	/** FIN COSTAS Y GASTOS */
	public void exportarTablaExcel(){
		
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("Exportando a Excel");
		int rowIndex = 1;
		 Row row1 = sheet.createRow(0);
		 	row1.createCell(0).setCellValue("PersonaId");
		    row1.createCell(1).setCellValue("Apellidos y Nombres");
		    row1.createCell(2).setCellValue("Nro. Doc.");
		    row1.createCell(3).setCellValue("Direccion");
		    row1.createCell(4).setCellValue("Sector");
		    row1.createCell(5).setCellValue("Predial");
		    row1.createCell(6).setCellValue("Predial con Dscto.");
		    row1.createCell(7).setCellValue("Vehicular");
		    row1.createCell(8).setCellValue("Vehicular con Dscto.");
		    row1.createCell(9).setCellValue("Arbitrios");
		    row1.createCell(10).setCellValue("Arbitrios con Dscto.");

		  for( FindCcLoteDetalleActo data : lstlotePreliminar){
		    Row row = sheet.createRow(rowIndex++);
		    int columnIndex = 0;		 
		    row.createCell(columnIndex++).setCellValue(data.getPersonaId());
		    row.createCell(columnIndex++).setCellValue(data.getDescripcionPersona());
		    row.createCell(columnIndex++).setCellValue(data.getNroDocumento());
		    row.createCell(columnIndex++).setCellValue(data.getDireccion());
		    row.createCell(columnIndex++).setCellValue(data.getSector());
		    row.createCell(columnIndex++).setCellValue(data.getTotalDeudaPredial().doubleValue());
		    row.createCell(columnIndex++).setCellValue(data.getTotalDeudaPredialDcto().doubleValue());
		    row.createCell(columnIndex++).setCellValue(data.getTotalDeudaVehicular().doubleValue());
		    row.createCell(columnIndex++).setCellValue(data.getTotalDeudaVehicularDcto().doubleValue());
		    row.createCell(columnIndex++).setCellValue(data.getTotalDeudaArbitrios().doubleValue());
		    row.createCell(columnIndex++).setCellValue(data.getTotalDeudaArbitriosDcto().doubleValue());
		}
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		externalContext.responseReset(); 
		externalContext.setResponseContentType("application/vnd.ms-excel");
		externalContext.setResponseHeader("Content-Disposition", "attachment;filename=exportando_a_excel.xls");
		try {
			workbook.write(externalContext.getResponseOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		context.responseComplete(); // Prevent JSF from performing navigation.
	}
	
	public void generacionArchivosXml(){
		java.sql.Connection connection=null;
		try {
			if(lstlotePreliminar!=null&&lstlotePreliminar.size()>0){
				CrudServiceBean connj = CrudServiceBean.getInstance();
				connection = connj.connectJasper();

				String path_context = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
				HashMap<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("p_periodo", DateUtil.getAnioActual());
				parameters.put("p_estado_direccion", estadoDireccionId);
				parameters.put("p_total_deuda_min", montoMinimo);
				parameters.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
				JasperPrint jasperPrint =null;
				if(tipoRecordatorioId==1){
					jasperPrint = JasperFillManager.fillReport((SATWEBParameterFactory.getPathReporte() + "reporte_esquelas_informativas_periodo_actual.jasper"),parameters, connection);	
				}else if(tipoRecordatorioId==0){
					jasperPrint = JasperFillManager.fillReport((SATWEBParameterFactory.getPathReporte() + "reporte_esquelas_informativas_periodo_anterior.jasper"),parameters, connection);
				}
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				
				JRXlsExporter exporterXls = new JRXlsExporter();
				    exporterXls.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);  
				    exporterXls.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, true);  
				    exporterXls.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, true);  
				    exporterXls.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, false);  
				    exporterXls.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, true);  
				    exporterXls.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, true);  
				    exporterXls.setParameter(JRXlsExporterParameter.MAXIMUM_ROWS_PER_SHEET, 200000); 
				    exporterXls.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);
				    if(tipoRecordatorioId==1){
				    	exporterXls.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "reporte_esquelas_informativas_periodo_actual.xls");
				    }else{
				    	exporterXls.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "reporte_esquelas_informativas_periodo_anterior.xls");
				    }
				    exporterXls.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, output);  
				    exporterXls.exportReport();
	
				HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
				response.setContentType("application/vnd.ms-excel");  
				if(tipoRecordatorioId==1){
					response.setHeader("Content-Disposition", "attachment;filename=" + "reporte_esquelas_informativas_periodo_actual.xls");
				}else{
					response.setHeader("Content-Disposition", "attachment;filename=" + "reporte_esquelas_informativas_periodo_anterior.xls");
				}
				response.setContentLength(output.size());
				ServletOutputStream servletOutputStream = response.getOutputStream();
				servletOutputStream.write(output.toByteArray(), 0, output.size());
				servletOutputStream.flush();
				servletOutputStream.close();
				FacesContext.getCurrentInstance().responseComplete();
			}else{
				WebMessages.messageInfo("Genere la data preliminar");
			}
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
	
	/***
	 * Paginacion
	public void generacionArchivos(){
		try{
			if(lstlotePreliminar!=null&&lstlotePreliminar.size()>0){
				if(paginaActual!=null){
					java.sql.Connection connection = null;
					try {
						CrudServiceBean connj = CrudServiceBean.getInstance();
						connection = connj.connectJasper();
	
						String path_context = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
						String path_report = path_context + "/sisat/reportes/";
						String path_imagen = path_context + "/sisat/reportes/imagen/";
	
						HashMap<String, Object> parameters = new HashMap<String, Object>();
						
						parameters.put("ruta_imagen", path_imagen);
						parameters.put("p_periodo", DateUtil.getAnioActual());
						parameters.put("p_pagina_inicio", paginaActual.getFrom());
						parameters.put("p_pagina_fin", paginaActual.getTo());
						parameters.put("p_estado_direccion", estadoDireccionId);
						parameters.put("p_total_deuda_min", montoMinimo);
						parameters.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
						JasperPrint jasperPrint =null;
						if(tipoRecordatorioId==1){
							jasperPrint = JasperFillManager.fillReport((SATWEBParameterFactory.getPathReporte() + "recordatorio_deudas_periodo_actual.jasper"),parameters, connection);	
						}else if(tipoRecordatorioId==0){
							jasperPrint = JasperFillManager.fillReport((SATWEBParameterFactory.getPathReporte() + "recordatorio_deudas_periodo_anterior.jasper"),parameters, connection);
						}
	
						ByteArrayOutputStream output = new ByteArrayOutputStream();
						JasperExportManager.exportReportToPdfStream(jasperPrint, output);
						
						HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
						response.setContentType("application/pdf");
						response.addHeader("Content-Disposition","attachment;filename=decordatorio_deudas_"+paginaActual.getFrom()+"_"+paginaActual.getTo()+".pdf");
						response.setContentLength(output.size());
						ServletOutputStream servletOutputStream = response.getOutputStream();
						servletOutputStream.write(output.toByteArray(), 0, output.size());
						servletOutputStream.flush();
						servletOutputStream.close();
						FacesContext.getCurrentInstance().responseComplete();
						
					} catch (Exception e) {
						e.printStackTrace();
						WebMessages.messageError(e.getMessage());
					} finally {
						try {
							if (connection != null) {
								connection.close();
								connection = null;
							}
						}catch (Exception e) {
								
						}
					}	
					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	*/
	
	public void loadSeleccionEstadoDireccion(ValueChangeEvent event) {
		try {
			HtmlComboBox combo = (HtmlComboBox) event.getComponent();
			String value = combo.getValue().toString();
			if(value.equals("Ubicables")){
				setEstadoDireccionId(1);
			}else if(value.equals("InUbicables")){
				setEstadoDireccionId(0);
			}
		}catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public void loadSeleccionTipoRecordatorio(ValueChangeEvent event) {
		try {
			HtmlComboBox combo = (HtmlComboBox) event.getComponent();
			String value = combo.getValue().toString();
			if(value.equals(getDeudaPorVencer())){
				setTipoRecordatorioId(1);
			}else if(value.equals(getDeudaVencida())){
				setTipoRecordatorioId(0);
			}
		}catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	/**
	 * Paginacion
	public void loadSeleccionPagina(ValueChangeEvent event) {
		try {
			HtmlComboBox combo = (HtmlComboBox) event.getComponent();
			String value = combo.getValue().toString();
			paginaActual=getPagina(value);
		}catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	*/
	
	/**
	 * Paginacion
	private Page getPagina(String value){
		try{
			String []valuepagina=value.split("-");
			if(valuepagina!=null&&valuepagina.length==2){
				Integer from=Util.toInteger(valuepagina[0].trim().replace("[", "").replace("]",""));
				Integer to=Util.toInteger(valuepagina[1].trim().replace("[", "").replace("]",""));
				if(from<=to){
					return new Page(0,from.intValue(),to.intValue());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	*/
	
	/**
	 * Paginacion
	public void cargaPaginas(){
		if(lstlotePreliminar!=null&&lstlotePreliminar.size()>0){
			Paginator paginator=new Paginator(lstlotePreliminar.size(), 1000);
			int length=paginator.maxPages();
			for(int i=0;i<length;i++){
				Page page=paginator.get(i);
				
				UISelectItem select = new UISelectItem();
				select.setItemValue(String.valueOf(page.getFrom()+" - "+page.getTo()));
	            cmbPagina.getChildren().add(select);  
			}
		}
	}
	*/
	
	public List<FindCcLoteDetalleActo> getLstlotePreliminar() {
		return lstlotePreliminar;
	}

	public void setLstlotePreliminar(List<FindCcLoteDetalleActo> lstlotePreliminar) {
		this.lstlotePreliminar = lstlotePreliminar;
	}
	
	public Double getMontoMinimo() {
		return montoMinimo;
	}

	public void setMontoMinimo(Double montoMinimo) {
		this.montoMinimo = montoMinimo;
	}

	public HtmlComboBox getCmbEstadoDireccion() {
		return cmbEstadoDireccion;
	}

	public void setCmbEstadoDireccion(HtmlComboBox cmbEstadoDireccion) {
		this.cmbEstadoDireccion = cmbEstadoDireccion;
	}

	public Integer getEstadoDireccionId() {
		return estadoDireccionId;
	}

	public void setEstadoDireccionId(Integer estadoDireccionId) {
		this.estadoDireccionId = estadoDireccionId;
	}
	
	public HtmlComboBox getCmbTipoRecordatorio() {
		return cmbTipoRecordatorio;
	}

	public void setCmbTipoRecordatorio(HtmlComboBox cmbTipoRecordatorio) {
		this.cmbTipoRecordatorio = cmbTipoRecordatorio;
	}
	
	public Integer getTipoRecordatorioId() {
		return tipoRecordatorioId;
	}

	public void setTipoRecordatorioId(Integer tipoRecordatorioId) {
		this.tipoRecordatorioId = tipoRecordatorioId;
	}
	
	/**
	 * Paginacion
	public HtmlComboBox getCmbPagina() {
		return cmbPagina;
	}

	public void setCmbPagina(HtmlComboBox cmbPagina) {
		this.cmbPagina = cmbPagina;
	}

	public Integer getPaginaId() {
		return paginaId;
	}

	public void setPaginaId(Integer paginaId) {
		this.paginaId = paginaId;
	}
	*/
	
	public String getDeudaPorVencer() {
		return deudaPorVencer;
	}

	public void setDeudaPorVencer(String deudaPorVencer) {
		this.deudaPorVencer = deudaPorVencer;
	}

	public String getDeudaVencida() {
		return deudaVencida;
	}

	public void setDeudaVencida(String deudaVencida) {
		this.deudaVencida = deudaVencida;
	}
}
