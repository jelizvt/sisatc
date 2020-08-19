package com.sat.sisat.consultasreportes.managed;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.SATWEBParameterFactory;
import com.sat.sisat.persistence.CrudServiceBean;

@ManagedBean
@ViewScoped
public class ReporteDescargoManaged extends BaseManaged {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int selectedTipoDeuda;
	private Boolean disabledFecha;
	private Boolean disabledAnio;
	
	private Integer anioInicio;
	private Integer anioFin;
	private Date fechaInicio = Calendar.getInstance().getTime();
	private Date fechaFin = Calendar.getInstance().getTime();
	private Integer tipo;
	
	@PostConstruct
	public void init()  {
	}
	
	public void generarReporteDescargo() {
		java.sql.Connection connection=null;
		try {
			CrudServiceBean connj=CrudServiceBean.getInstance();
			connection=connj.connectJasper();
			
		if(disabledFecha.equals(Boolean.TRUE)){
			//validar fechas..si está vacio, mayor, etc.
//			setFechaInicio(fechaInicio);
//	    	setFechaFin(fechaFin);
			setTipo(1);
		}else if(disabledAnio.equals(Boolean.TRUE)){
			//validar fechas..si está vacio, mayor, etc.
		//	pst.setInt(1, loteId==null?0:loteId);
			setAnioInicio(0);
	    	setAnioFin(0);
	    	setTipo(2);
		}
		//cs.setNull(2, java.sql.Types.NULL);
  				
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("tipo_deuda", new Integer(selectedTipoDeuda));
			parameters.put("fecha_inicio", new Timestamp(fechaInicio.getTime()));
			parameters.put("fecha_fin", new Timestamp(fechaFin.getTime()));
			parameters.put("anio_inicio", new Integer(anioInicio));
			parameters.put("anio_fin", new Integer(anioFin));
			parameters.put("tipo", new Integer(tipo));
//			if(fechaInicio != null){pst.setTimestamp(2, new Timestamp(fechaInicio.getTime()));}else{pst.setNull(2, java.sql.Types.NULL);}
//			parameters.put("fecIni", new Date((fechaInicio.equals(null)?0:fechaInicio.getTime())));
//			parameters.put("fecFin", new Date((fechaFin.equals(null)?null:fechaFin.getTime())));
			
			
			
			JasperPrint jasperPrint = JasperFillManager.fillReport((SATWEBParameterFactory.getPathReporte()+"reporte_descargo.jasper"),parameters,connection);
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
			    exporterXls.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, System.currentTimeMillis() + "reporte_Descargos" + ".xls");  
			    exporterXls.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, output);  
			    exporterXls.exportReport();

			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
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

	public int getSelectedTipoDeuda() {
		return selectedTipoDeuda;
	}

	public void setSelectedTipoDeuda(int selectedTipoDeuda) {
		this.selectedTipoDeuda = selectedTipoDeuda;
	}

	public Boolean getDisabledFecha() {
		return disabledFecha;
	}

	public void setDisabledFecha(Boolean disabledFecha) {
		this.disabledFecha = disabledFecha;
	}

	public Boolean getDisabledAnio() {
		return disabledAnio;
	}

	public void setDisabledAnio(Boolean disabledAnio) {
		this.disabledAnio = disabledAnio;
	}

	public Integer getAnioInicio() {
		return anioInicio;
	}

	public void setAnioInicio(Integer anioInicio) {
		this.anioInicio = anioInicio;
	}

	public Integer getAnioFin() {
		return anioFin;
	}

	public void setAnioFin(Integer anioFin) {
		this.anioFin = anioFin;
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

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

}
