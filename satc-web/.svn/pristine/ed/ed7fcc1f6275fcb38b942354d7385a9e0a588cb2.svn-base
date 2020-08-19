package com.sat.sisat.consultasreportes.managed;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

//import org.eclipse.birt.report.engine.api.EngineConfig;
//import org.eclipse.birt.report.engine.api.IReportEngine;
//import org.eclipse.birt.report.engine.api.IReportRunnable;
//import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
//import org.eclipse.birt.report.engine.api.ReportEngine;
import org.richfaces.component.html.HtmlComboBox;

import com.itextpdf.text.pdf.codec.Base64.OutputStream;
import com.sat.sisat.alfresco.util.Util;
import com.sat.sisat.common.security.UserSession;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.SATWEBParameterFactory;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.persistence.CrudServiceBean;
import com.sat.sisat.predial.business.BaseBusiness;
import com.sat.sisat.reportes.business.ReportesBoRemote;
import com.sat.sisat.reportes.dto.ReporDeudaDTO;
import com.sat.sisat.reportes.dto.ReporRecaudacionDTO;

@ManagedBean
@ViewScoped
public class ReportesManaged extends BaseManaged{

	private static final long serialVersionUID = 1L;
	
	@EJB
	ReportesBoRemote reportesBo;
	
	ArrayList<ReporRecaudacionDTO> listaReca;
	
	ArrayList<ReporDeudaDTO> listaDeuda;
	
	public ArrayList<ReporDeudaDTO> getListaDeuda() {
		return listaDeuda;
	}

	public void setListaDeuda(ArrayList<ReporDeudaDTO> listaDeuda) {
		this.listaDeuda = listaDeuda;
	}

	public ArrayList<ReporRecaudacionDTO> getListaReca() {
		return listaReca;
	}

	public void setListaReca(ArrayList<ReporRecaudacionDTO> listaReca) {
		this.listaReca = listaReca;
	}

	@PostConstruct
	public void init(){
		mapTipoGrafico.put("Cuadro", 1);
		lstTipoGrafico.add(new SelectItem("Cuadro","1"));
		mapTipoGrafico.put("Gráfico de Lineas por Año", 2);
		lstTipoGrafico.add(new SelectItem("Gráfico de Lineas por Año","2"));
		mapTipoGrafico.put("Gráfico de Barras", 3);
		lstTipoGrafico.add(new SelectItem("Gráfico de Barras","3"));
		
		
		mapUnidades.put("Nuevos Soles", 1);
		lstUnidades.add(new SelectItem("Nuevos Soles", "1"));
		mapUnidades.put("Miles Nuevos Soles", 2);
		lstUnidades.add(new SelectItem("Miles Nuevos Soles", "2"));
		mapUnidades.put("Millones de Nuevos Soles", 3);
		lstUnidades.add(new SelectItem("Millones de Nuevos Soles", "3"));
		mapUnidades.put("Porcentaje", 4);
		lstUnidades.add(new SelectItem("Porcentaje", "4"));
	}

	private HashMap<String, Integer> mapTipoGrafico = new HashMap<String, Integer>();
	private HashMap<String, Integer> mapUnidades = new HashMap<String, Integer>();
	private List<SelectItem> lstTipoGrafico = new ArrayList<SelectItem>();
	private List<SelectItem> lstUnidades = new ArrayList<SelectItem>();

	private HtmlComboBox cmbtipoGrafico;
	
	private HtmlComboBox cmbUnidades;
	

	

	public void llamarReporte() throws IOException{
		try {
			

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}
	
	Integer tipoSalida;
	public void loadTipoSalida(ValueChangeEvent event) {
		try {
			String value = (String) event.getNewValue();
			if (value.length()>0&&value!="") {
				tipoSalida = (Integer) mapTipoGrafico.get(value);
			}

		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	
	Integer unidades;
	public void loadUnidades(ValueChangeEvent event) {
		try {
			String value = (String) event.getNewValue();
			if (value.length()>0&&value!="") {
				unidades = (Integer) mapUnidades.get(value);
			}

		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	
	private String diaInicioReca;
	private String diaFinReca;
	private String mesInicioReca;
	private String mesFinReca;
	private String anioInicioReca;
	private String diaInicioDeuda;
	private String diaFinDeuda;
	private String mesInicioDeuda;
	private String mesFinDeuda;
	private String anioInicioDeuda;
	private String anioFinDeuda;
	
	private Date fechaInicio;
	private Date fechaFin;
	
	
	public String getDiaInicioDeuda() {
		return diaInicioDeuda;
	}

	public void setDiaInicioDeuda(String diaInicioDeuda) {
		this.diaInicioDeuda = diaInicioDeuda;
	}

	public String getDiaFinDeuda() {
		return diaFinDeuda;
	}

	public void setDiaFinDeuda(String diaFinDeuda) {
		this.diaFinDeuda = diaFinDeuda;
	}

	public String getMesInicioDeuda() {
		return mesInicioDeuda;
	}

	public void setMesInicioDeuda(String mesInicioDeuda) {
		this.mesInicioDeuda = mesInicioDeuda;
	}

	public String getMesFinDeuda() {
		return mesFinDeuda;
	}

	public void setMesFinDeuda(String mesFinDeuda) {
		this.mesFinDeuda = mesFinDeuda;
	}

	public String getAnioInicioDeuda() {
		return anioInicioDeuda;
	}

	public void setAnioInicioDeuda(String anioInicioDeuda) {
		this.anioInicioDeuda = anioInicioDeuda;
	}

	public String getDiaInicioReca() {
		return diaInicioReca;
	}

	public String getDiaFinReca() {
		return diaFinReca;
	}

	public void setDiaFinReca(String diaFinReca) {
		this.diaFinReca = diaFinReca;
	}

	public String getMesInicioReca() {
		return mesInicioReca;
	}

	public void setMesInicioReca(String mesInicioReca) {
		this.mesInicioReca = mesInicioReca;
	}

	public String getMesFinReca() {
		return mesFinReca;
	}

	public void setMesFinReca(String mesFinReca) {
		this.mesFinReca = mesFinReca;
	}

	public String getAnioInicioReca() {
		return anioInicioReca;
	}

	public void setAnioInicioReca(String anioInicioReca) {
		this.anioInicioReca = anioInicioReca;
	}

	public String getAnioFInReca() {
		return anioFInReca;
	}

	public void setAnioFInReca(String anioFInReca) {
		this.anioFInReca = anioFInReca;
	}

	public void setDiaInicioReca(String diaInicioReca) {
		this.diaInicioReca = diaInicioReca;
	}

	private String anioFInReca;
	
	int anioIReca;
	int anioFReca;
	int diaIReca;
	int diaFReca;
	int mesIReca;
	int mesFReca;
	
	private Map<String, Object> pReportes = new HashMap();
	
	public Map getpReportes() {
		return pReportes;
	}

	public void setpReportes(Map pReportes) {
		this.pReportes = pReportes;
	}

	private String oculto;
	
	public void validaCampos(){
		if(fechaInicio==null){
			WebMessages.messageError("Ingresar Fecha Inicio");
			return;	
		}
		if(fechaFin==null){
			WebMessages.messageError("Ingresar Fecha Hasta");
			return;	
		}
		
		if(unidades==null){
			WebMessages.messageError("Seleccionar Unidades");
			return;	
		}
		
		if(tipoSalida==null){
			WebMessages.messageError("Seleccionar tipo de Salida");
			return;	
		}
		setOculto("Validado");
	}
	
	public void mostrarRecaudacion(){
	
		
		Calendar calenI = Calendar.getInstance();
		calenI.setTime(fechaInicio);
		anioIReca=calenI.get(Calendar.YEAR);
		diaIReca=calenI.get(Calendar.DAY_OF_MONTH);
		mesIReca=calenI.get(Calendar.MONTH)+1;
		
		
		Calendar calenF = Calendar.getInstance();
		calenF.setTime(fechaFin);
		anioFReca=calenF.get(Calendar.YEAR);
		diaFReca=calenF.get(Calendar.DAY_OF_MONTH);
		mesFReca=calenF.get(Calendar.MONTH)+1;
		
		
		
		if(anioInicioReca!=null && anioInicioReca.trim().length()>0){
			anioIReca=Integer.parseInt(anioInicioReca.trim());
		}
		if(anioFInReca!=null && anioFInReca.trim().length()>0){
			anioFReca=Integer.parseInt(anioFInReca.trim());
		}
		if(diaInicioReca!=null && diaInicioReca.trim().length()>0){
			diaIReca=Integer.parseInt(diaInicioReca.trim());
		}
		if(diaFinReca!=null&&diaFinReca.trim().length()>0){
			diaFReca=Integer.parseInt(diaFinReca.trim());
		}
		if(mesInicioReca!=null&&mesInicioReca.trim().length()>0){
			mesIReca=Integer.parseInt(mesInicioReca.trim());
		}
		if(mesFinReca!=null&&mesFinReca.trim().length()>0){
			mesFReca=Integer.parseInt(mesFinReca.trim());
		}
		
		try {
				listaReca=reportesBo.getRecaudaciones(anioIReca,anioFReca,diaIReca,diaFReca,mesIReca,mesFReca);
				emitirReporteReca();
		} catch (Exception e) {
			System.out.println("ERROR: "+e);
			WebMessages.messageError("Se produjo un error");
			return;
		}
	}
	
	private int selectedTipoAgrupaContri = 1;
	
	public int getSelectedTipoAgrupaContri() {
		return selectedTipoAgrupaContri;
	}

	public void setSelectedTipoAgrupaContri(int selectedTipoAgrupaContri) {
		this.selectedTipoAgrupaContri = selectedTipoAgrupaContri;
	}

	private String punidades;
	private String nombreJasper;
	
	public void emitirReporteReca(){
		try {
			
		if(listaReca.size()>0){
			pReportes.put("P_usuario", getSessionManaged().getUsuarioLogIn().getNombreUsuario());
			pReportes.put("P_anio", "AÑO "+DateUtil.getAnioActual());
			pReportes.put("P_ruta_image",  SATWEBParameterFactory.getPathReporteImagenes());

			if(tipoSalida>0&&tipoSalida==1){//tipo de salida cuadro
				if(selectedTipoAgrupaContri==1){
					nombreJasper="Reca.jasper";			
				}else{
					nombreJasper="RecaConcepto_Cuadro.jasper";		
				}
				
				if(unidades==1){
						punidades="En Nuevos Soles";
				}else if(unidades==2){
					for(int i=0;i<listaReca.size();i++){
						listaReca.get(i).setMontoPago(listaReca.get(i).getMilesSoles());
						punidades="En Miles Nuevos Soles";
					}
				}else if(unidades==3){
					for(int i=0;i<listaReca.size();i++){
						listaReca.get(i).setMontoPago(listaReca.get(i).getMillonesSoles());
						punidades="En Millones Nuevos Soles";
					}
				}else if(unidades==4){
					for(int i=0;i<listaReca.size();i++){
						//listaReca.get(i).setMontoPago(listaReca.get(i).getPorcentajeDeuda());
						punidades="En Porcentaje de Pago";
					}
					if(selectedTipoAgrupaContri==1){
						nombreJasper="RecaCuadroPorcen.jasper";	
					}else{
						nombreJasper="RecaConceptoCuadroPorcen.jasper";	
					}
					
				}
				
			}else if(tipoSalida>0&&tipoSalida==2){//tipo de salida lineas
				nombreJasper="RecaLineal.jasper";	
				
				if(unidades==1){
						punidades="Nuevos Soles";
				}else if(unidades==2){
					for(int i=0;i<listaReca.size();i++){
						//listaReca.get(i).setMontoPago(listaReca.get(i).getMilesSoles());
						listaReca.get(i).setRecaudacionAnioDeuda(listaReca.get(i).getRecaudacionAnioDeuda().divide(new BigDecimal(1000)).setScale(4,BigDecimal.ROUND_UP));
						punidades="Miles Nuevos Soles";
					}
				}else if(unidades==3){
					for(int i=0;i<listaReca.size();i++){
						//listaReca.get(i).setMontoPago(listaReca.get(i).getMillonesSoles());
						listaReca.get(i).setRecaudacionAnioDeuda(listaReca.get(i).getRecaudacionAnioDeuda().divide(new BigDecimal(1000000)).setScale(6,BigDecimal.ROUND_UP));
						punidades="Millones Nuevos Soles";
					}
				}else if(unidades==4){
					for(int i=0;i<listaReca.size();i++){
						listaReca.get(i).setMontoPago(listaReca.get(i).getPorcentajeDeuda());
						punidades="Porcentaje de Pago";
						nombreJasper="RecaLinealPorcen.jasper";
					}
				}
			}else if(tipoSalida>0&&tipoSalida==3){//tipo de salida barras
				nombreJasper="RecaBarras.jasper";
				
				if(unidades==1){
					punidades="Nuevos Soles";
				}else if(unidades==2){
					for(int i=0;i<listaReca.size();i++){
						listaReca.get(i).setRecaudacionAnioDeuda(listaReca.get(i).getRecaudacionAnioDeuda().divide(new BigDecimal(1000)).setScale(4,BigDecimal.ROUND_UP));
						punidades="Miles Nuevos Soles";
					}
				}else if(unidades==3){
					for(int i=0;i<listaReca.size();i++){
						listaReca.get(i).setRecaudacionAnioDeuda(listaReca.get(i).getRecaudacionAnioDeuda().divide(new BigDecimal(1000000)).setScale(6,BigDecimal.ROUND_UP));
						punidades="Millones Nuevos Soles";
					}
				}else if(unidades==4){
					punidades="Porcentaje";
					nombreJasper="RecaBarrasPorcen.jasper";
				}
			}
			//List<ReporRecaudacionDTO> lisObj= new ArrayList<ReporRecaudacionDTO>();
			
			
			pReportes.put("P_unidades", punidades);
			
			JasperPrint jasperPrint=JasperFillManager.fillReport((SATWEBParameterFactory.getPathReporte()+nombreJasper), pReportes,new JRBeanCollectionDataSource(listaReca));

			ByteArrayOutputStream output = new ByteArrayOutputStream();
			JExcelApiExporter exporterXLS = new JExcelApiExporter();
		    exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
		    exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, output);
		    exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
		    exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
		    exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
		    exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		    exporterXLS.exportReport();

		    HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.setContentType("application/vnd.ms-excel");
            response.addHeader("Content-Disposition","attachment;filename=JasperReportBarras.xls");
            response.setContentLength(output.size());
	        ServletOutputStream servletOutputStream = response.getOutputStream();
	        servletOutputStream.write(output.toByteArray(), 0, output.size());
	        servletOutputStream.flush();
	        servletOutputStream.close();
	        FacesContext.getCurrentInstance().responseComplete();

		}
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}	
	}
	
	int anioIDeuda;
	int anioFDeuda;
	int diaIDeuda;
	int diaFDeuda;
	int mesIDeuda;
	int mesFDeuda;
	

	
	
	
	
	
	public void limpiarCamposReca(){
		setAnioInicioReca("");
		setAnioFInReca("");
		setMesInicioReca("");
		setMesFinReca("");
		setDiaInicioReca("");
		setDiaFinReca("");
	}
	
	


	public String getAnioFinDeuda() {
		return anioFinDeuda;
	}

	public void setAnioFinDeuda(String anioFinDeuda) {
		this.anioFinDeuda = anioFinDeuda;
	}

	public HashMap<String, Integer> getMapTipoGrafico() {
		return mapTipoGrafico;
	}

	public void setMapTipoGrafico(HashMap<String, Integer> mapTipoGrafico) {
		this.mapTipoGrafico = mapTipoGrafico;
	}

	public List<SelectItem> getLstTipoGrafico() {
		return lstTipoGrafico;
	}

	public void setLstTipoGrafico(List<SelectItem> lstTipoGrafico) {
		this.lstTipoGrafico = lstTipoGrafico;
	}

	public HtmlComboBox getCmbtipoGrafico() {
		return cmbtipoGrafico;
	}

	public void setCmbtipoGrafico(HtmlComboBox cmbtipoGrafico) {
		this.cmbtipoGrafico = cmbtipoGrafico;
	}

	public HtmlComboBox getCmbUnidades() {
		return cmbUnidades;
	}

	public void setCmbUnidades(HtmlComboBox cmbUnidades) {
		this.cmbUnidades = cmbUnidades;
	}

	public List<SelectItem> getLstUnidades() {
		return lstUnidades;
	}

	public void setLstUnidades(List<SelectItem> lstUnidades) {
		this.lstUnidades = lstUnidades;
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

	public String getOculto() {
		return oculto;
	}

	public void setOculto(String oculto) {
		this.oculto = oculto;
	}


}
