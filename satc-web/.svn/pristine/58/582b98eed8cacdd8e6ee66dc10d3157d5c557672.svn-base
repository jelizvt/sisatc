package com.sat.sisat.tramitedocumentario.managed;

import java.io.ByteArrayOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.print.*;
import javax.print.DocFlavor.URL;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.SATWEBParameterFactory;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.persistence.CrudServiceBean;
import com.sat.sisat.persona.business.PersonaBoRemote;
import com.sat.sisat.tramitedocumentario.bussiness.TramiteDocumentarioBoRemote;
import com.sat.sisat.tramitedocumentario.dto.ResumenReporteExpedientesDTO;

@ManagedBean
@ViewScoped
public class ReporteTramiteDocManaged extends BaseManaged {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6514234838662396057L;

	private Date fechaInicio = Calendar.getInstance().getTime();

	private Date fechaFin = Calendar.getInstance().getTime();

	private List<ResumenReporteExpedientesDTO> listResumenReporteExpedientesDTOs;
	
	private Integer totalFinal;
	
	private Integer idArea;
	
	private Integer situacionExpediente = 11;
	
	private Integer reportExpediente = 1;
	
	private Integer unidadOrigen;
	
	private Integer unidadDestino;
	
	private Integer procedimientoExpediente;
	
	private Integer tipoTramite;

	@EJB
	TramiteDocumentarioBoRemote tramiteDocumentarioBo;

	@EJB
	PersonaBoRemote personaBo;

	public void buscar() {

		try {
			
			listResumenReporteExpedientesDTOs = tramiteDocumentarioBo.obtenerResumenExpedientes(DateUtil
					.moverHoraAlInicioDelDia(fechaInicio), DateUtil.moverHoraAlFinalDelDia(fechaFin));
			if (listResumenReporteExpedientesDTOs.size()>0){ //Se agrego para evitar error cuando no existe datos en la busqueda
				setTotalFinal(listResumenReporteExpedientesDTOs.get(listResumenReporteExpedientesDTOs.size()-1).getTotal());
			}
			else setTotalFinal(0);
			
		} catch (SisatException e) {
			WebMessages.messageError(e.getMessage());
		}

	}
	
	public void buscarhd() {
		
		try {
			
			listResumenReporteExpedientesDTOs = tramiteDocumentarioBo.obtenerResumenExpedienteshr(DateUtil
					.moverHoraAlInicioDelDia(fechaInicio), DateUtil.moverHoraAlFinalDelDia(fechaFin), 
					unidadOrigen, unidadDestino, procedimientoExpediente, tipoTramite, reportExpediente, situacionExpediente);
			
			if (listResumenReporteExpedientesDTOs.size()>0){ //Se agrego para evitar error cuando no existe datos en la busqueda
				setTotalFinal(listResumenReporteExpedientesDTOs.get(listResumenReporteExpedientesDTOs.size()-1).getTotal());
			}
			else setTotalFinal(0);			
			
		} catch (SisatException e) {
			e.printStackTrace();
			WebMessages.messageError(e.getMessage());
		}

	}	
	
	public void limpiarhd() {
		setFechaInicio(Calendar.getInstance().getTime());
		setFechaFin(Calendar.getInstance().getTime());
		setUnidadOrigen(0);
		setUnidadDestino(0);
		setProcedimientoExpediente(0);
		setTipoTramite(0);
		setReportExpediente(1);
		setSituacionExpediente(11);		
	}
	
	public void descargar(Integer unidadId) {

		java.sql.Connection connection = null;
		try {
			CrudServiceBean connj = CrudServiceBean.getInstance();
			connection = connj.connectJasper();

			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("fecha_inicio", new Timestamp(fechaInicio.getTime()));
			parameters.put("fecha_fin", new Timestamp((DateUtil.moverHoraAlFinalDelDia(fechaFin)).getTime()));
			parameters.put("unidad_id", unidadId);
			parameters.put("usuario_nombre", this.getSessionManaged().getUsuarioLogIn().getNombreUsuario());

			parameters.put("ruta_image", SATWEBParameterFactory.getPathReporteImagenes());
			parameters.put("SUBREPORT_DIR", SATWEBParameterFactory.getPathReporte());
			parameters.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
			
			JasperPrint jasperPrint = null;
			
			/** Comparacion: si unidadId es nulo, imprime el reporte total, de no ser asi imprime el nuevo reporte por  area */
			
			if (unidadId == null)
			{
				jasperPrint = JasperFillManager
						.fillReport((SATWEBParameterFactory.getPathReporte() + "td_reporte_detallado_expediente.jasper"),
								parameters,
								connection);
			}
			else
			{
				jasperPrint = JasperFillManager
						.fillReport((SATWEBParameterFactory.getPathReporte() + "td_reporte_detallado_expediente_area.jasper"),
								parameters,
								connection);
			}


			ByteArrayOutputStream output = new ByteArrayOutputStream();

			JasperExportManager.exportReportToPdfStream(jasperPrint, output);

			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment;filename=reporte_expediente.pdf");

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
	
	public void descargarhr(Integer unidadId) {

		java.sql.Connection connection = null;
		try {
			CrudServiceBean connj = CrudServiceBean.getInstance();
			connection = connj.connectJasper();

			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("fecha_inicio", new Timestamp(fechaInicio.getTime()));
			parameters.put("fecha_fin", new Timestamp((DateUtil.moverHoraAlFinalDelDia(fechaFin)).getTime()));
			parameters.put("unidad_id", unidadId);
			parameters.put("usuario_nombre", this.getSessionManaged().getUsuarioLogIn().getNombreUsuario());

			parameters.put("ruta_image", SATWEBParameterFactory.getPathReporteImagenes());
			parameters.put("SUBREPORT_DIR", SATWEBParameterFactory.getPathReporte());
			parameters.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
			
			JasperPrint jasperPrint = null;
			
			/** Comparacion: si unidadId es nulo, imprime el reporte total, de no ser asi imprime el nuevo reporte por  area y cambia flag de report */
			
			if (unidadId == null)
			{
				jasperPrint = JasperFillManager
						.fillReport((SATWEBParameterFactory.getPathReporte() + "td_reporte_detallado_expedientehr.jasper"),
								parameters,
								connection);				
				tramiteDocumentarioBo.cambioSegReporte(new Timestamp((DateUtil.moverHoraAlInicioDelDia(fechaInicio)).getTime()), new Timestamp((DateUtil.moverHoraAlFinalDelDia(fechaFin)).getTime()));
								
			}
			else
			{
				jasperPrint = JasperFillManager
						.fillReport((SATWEBParameterFactory.getPathReporte() + "td_reporte_detallado_expediente_areahr.jasper"),
								parameters,
								connection);
				tramiteDocumentarioBo.cambioSegReportea(new Timestamp((DateUtil.moverHoraAlInicioDelDia(fechaInicio)).getTime()), new Timestamp((DateUtil.moverHoraAlFinalDelDia(fechaFin)).getTime()), unidadId);
								
			}			

			ByteArrayOutputStream output = new ByteArrayOutputStream();

			JasperExportManager.exportReportToPdfStream(jasperPrint, output);

			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment;filename=reporte_expediente.pdf");

			response.setContentLength(output.size());
			ServletOutputStream servletOutputStream = response.getOutputStream();
			servletOutputStream.write(output.toByteArray(), 0, output.size());
			servletOutputStream.flush();
			servletOutputStream.close();
			FacesContext.getCurrentInstance().responseComplete();
			//Para actualizar expedientes a mostrar
			buscarhd();
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
	
	//para guardar id de area de expediente a imprimir
	public void areaexp(Integer undId){
		setIdArea(undId);
	}
		
	public void imprimirSeguimiento(Integer expedienteId) {
		java.sql.Connection connection = null;
		try {
			CrudServiceBean connj = CrudServiceBean.getInstance();
			connection = connj.connectJasper();

			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("expediente_id", expedienteId);
			parameters.put("usuario_nombre", this.getSessionManaged().getUsuarioLogIn().getNombreUsuario());
			parameters.put("ruta_image", SATWEBParameterFactory.getPathReporteImagenes());

			parameters.put("SUBREPORT_DIR", SATWEBParameterFactory.getPathReporte());
			parameters.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
			
			JasperPrint jasperPrint = null;
			
			jasperPrint = JasperFillManager
						.fillReport((SATWEBParameterFactory.getPathReporte() + "td_seguimiento_expediente.jasper"),
								parameters,
								connection);				
						
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			
			//JasperPrintManager.printReport(jasperPrint, false);

			JasperExportManager.exportReportToPdfStream(jasperPrint, output);

			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment;filename=seguimiento_expediente_"+ Integer.toString(expedienteId)+".pdf");

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
	//Hasta aqui

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

	public List<ResumenReporteExpedientesDTO> getListResumenReporteExpedientesDTOs() {
		return listResumenReporteExpedientesDTOs;
	}

	public void setListResumenReporteExpedientesDTOs(List<ResumenReporteExpedientesDTO> listResumenReporteExpedientesDTOs) {
		this.listResumenReporteExpedientesDTOs = listResumenReporteExpedientesDTOs;
	}
	
	public Integer getTotalFinal() {
		return totalFinal;
	}

	public void setTotalFinal(Integer totalFinal) {
		this.totalFinal = totalFinal;
	}
	
	//para guardar id de area de expediente a immprimir
	public Integer getIdArea(){
		return idArea;
	}

	public void setIdArea(Integer idArea){
		this.idArea = idArea;
	}
	
	//Nuevos criterios de busqueda
	public Integer getSituacionExpediente(){
		return situacionExpediente;
	}

	public void setSituacionExpediente(Integer situacionExpediente){
		this.situacionExpediente = situacionExpediente;
	}
	
	public Integer getReportExpediente(){
		return reportExpediente;
	}

	public void setReportExpediente(Integer reportExpediente){
		this.reportExpediente = reportExpediente;
	}
	
	public Integer getUnidadOrigen(){
		return unidadOrigen;
	}

	public void setUnidadOrigen(Integer unidadOrigen){
		this.unidadOrigen = unidadOrigen;	
	}
	
	public Integer getUnidadDestino(){
		return unidadDestino;
	}

	public void setUnidadDestino(Integer unidadDestino){
		this.unidadDestino = unidadDestino;	
	}
	
	public Integer getProcedimientoExpediente(){
		return procedimientoExpediente;
	}

	public void setProcedimientoExpediente(Integer procedimientoExpediente){
		this.procedimientoExpediente = procedimientoExpediente;	
	}
	
	public Integer getTipoTramite(){
		return tipoTramite;
	}

	public void setTipoTramite(Integer tipoTramite){
		this.tipoTramite = tipoTramite;	
	}
	
}
