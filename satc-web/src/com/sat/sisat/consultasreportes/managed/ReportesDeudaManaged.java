package com.sat.sisat.consultasreportes.managed;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
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

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.richfaces.component.html.HtmlComboBox;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.SATWEBParameterFactory;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.reportes.business.ReportesBoRemote;
import com.sat.sisat.reportes.dto.ReporDeudaDTO;

@ManagedBean
@ViewScoped
public class ReportesDeudaManaged extends BaseManaged{
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	ReportesBoRemote reportesBo;
	
	ArrayList<ReporDeudaDTO> listaDeuda;
	
	public ArrayList<ReporDeudaDTO> getListaDeuda() {
		return listaDeuda;
	}

	public void setListaDeuda(ArrayList<ReporDeudaDTO> listaDeuda) {
		this.listaDeuda = listaDeuda;
	}
	
	private HtmlComboBox cmbtipoGrafico;
	
	private HtmlComboBox cmbUnidadesDeuda;
	
	private HtmlComboBox cmbTipoAgrupa;
	
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
		
		
		mapTipoAgrupa.put("Concepto", 1);
		lstTipoAgrupa.add(new SelectItem("Concepto","1"));
		mapTipoAgrupa.put("Estados de Deuda", 2);
		lstTipoAgrupa.add(new SelectItem("Estados de Deuda","2"));
		mapTipoAgrupa.put("Valores", 3);
		lstTipoAgrupa.add(new SelectItem("Valores","3"));
//		mapTipoAgrupa.put("Rango", 4);
//		lstTipoAgrupa.add(new SelectItem("Rango","4"));
	}
	
	Integer unidades;
	public void loadUnidadesDeuda(ValueChangeEvent event) {
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
	
	
	
	Integer tipoAgrupa;
	public void loadTipoAgrupa(ValueChangeEvent event) {
		try {
			String value = (String) event.getNewValue();
			if (value.length()>0&&value!="") {
				tipoAgrupa = (Integer) mapTipoAgrupa.get(value);
			}

		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	private HashMap<String, Integer> mapTipoAgrupa = new HashMap<String, Integer>();
	private HashMap<String, Integer> mapTipoGrafico = new HashMap<String, Integer>();
	private HashMap<String, Integer> mapUnidades = new HashMap<String, Integer>();
	private List<SelectItem> lstTipoGrafico = new ArrayList<SelectItem>();
	private List<SelectItem> lstTipoAgrupa = new ArrayList<SelectItem>();
	public List<SelectItem> getLstTipoGrafico() {
		return lstTipoGrafico;
	}

	public void setLstTipoGrafico(List<SelectItem> lstTipoGrafico) {
		this.lstTipoGrafico = lstTipoGrafico;
	}

	private List<SelectItem> lstUnidades = new ArrayList<SelectItem>();

	public List<SelectItem> getLstUnidades() {
		return lstUnidades;
	}

	public void setLstUnidades(List<SelectItem> lstUnidades) {
		this.lstUnidades = lstUnidades;
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
	
	
	public void validarCampos(){
		if(tipoSalida==null){
			WebMessages.messageError("Seleccionar tipo de Salida");
			return;	
		}
		
		if(unidades==null){
			WebMessages.messageError("Seleccionar Unidades");
			return;	
		}
		
		if(tipoAgrupa==null){
			WebMessages.messageError("Seleccionar Tipo de Agrupacion");
			return;	
		}
	}
	
	public void mostrarDeuda(){
		

		try {
			
			listaDeuda=reportesBo.getDeudas(unidades,0,0,0,0,0);
			emitirReporteDeuda();
	
		} catch (Exception e) {
			System.out.println("ERROR: "+e);
		}
	}

	private Map<String, Object> pReportes = new HashMap();
	private String punidades;
	private String nombreJasper;
	
	public void emitirReporteDeuda(){
		try {
		if(listaDeuda.size()>0){
			pReportes.put("P_usuario", getSessionManaged().getUsuarioLogIn().getNombreUsuario());
			pReportes.put("P_anio", "AÑO "+DateUtil.getAnioActual());
			pReportes.put("P_ruta_image",  SATWEBParameterFactory.getPathReporteImagenes());
			
			if(tipoSalida==1){
				
			
			if(tipoAgrupa==1){
				
				
			
			if(tipoSalida==1){
			nombreJasper="DeudaCuadro.jasper";				
			
			if(unidades==1){
				punidades="Nuevos Soles";
			}else if(unidades==2){
				for(int i=0;i<listaDeuda.size();i++){
					listaDeuda.get(i).setMontoDeuda(listaDeuda.get(i).getMontoDeuda().divide(new BigDecimal(1000)).setScale(4,BigDecimal.ROUND_UP));
					punidades="Miles Nuevos Soles";
				}
			}else if(unidades==3){
				for(int i=0;i<listaDeuda.size();i++){
					listaDeuda.get(i).setMontoDeuda(listaDeuda.get(i).getMontoDeuda().divide(new BigDecimal(1000000)).setScale(6,BigDecimal.ROUND_UP));
					punidades="Millones Nuevos Soles";
				}
			}else if(unidades==4){
				punidades="Porcentaje";
				nombreJasper="DeudaCuadroPorcen.jasper";
			}
			
			}
			
			}else if(tipoAgrupa==2){
				//listaDeuda=reportesBo.getDeudas(unidades,0,0,0,0,0);
				if(tipoSalida==1){
					nombreJasper="DeudaCuadroEstadoDeuda.jasper";				
					
					if(unidades==1){
						punidades="Nuevos Soles";
					}else if(unidades==2){
						for(int i=0;i<listaDeuda.size();i++){
							listaDeuda.get(i).setMontoDeuda(listaDeuda.get(i).getMontoDeuda().divide(new BigDecimal(1000)).setScale(4,BigDecimal.ROUND_UP));
							punidades="Miles Nuevos Soles";
						}
					}else if(unidades==3){
						for(int i=0;i<listaDeuda.size();i++){
							listaDeuda.get(i).setMontoDeuda(listaDeuda.get(i).getMontoDeuda().divide(new BigDecimal(1000000)).setScale(6,BigDecimal.ROUND_UP));
							punidades="Millones Nuevos Soles";
						}
					}else if(unidades==4){
						punidades="Porcentaje";
						nombreJasper="DeudaCuadroEstadoDeudaPorcen.jasper";
					}
					
					}
			}else if(tipoAgrupa==3){
				//listaDeuda=reportesBo.getDeudas(unidades,0,0,0,0,0);
				if(tipoSalida==1){
					nombreJasper="DeudaValorCuadro.jasper";				
					
					if(unidades==1){
						punidades="Nuevos Soles";
					}else if(unidades==2){
						for(int i=0;i<listaDeuda.size();i++){
							listaDeuda.get(i).setMontoDeuda(listaDeuda.get(i).getMontoDeuda().divide(new BigDecimal(1000)).setScale(4,BigDecimal.ROUND_UP));
							punidades="Miles Nuevos Soles";
						}
					}else if(unidades==3){
						for(int i=0;i<listaDeuda.size();i++){
							listaDeuda.get(i).setMontoDeuda(listaDeuda.get(i).getMontoDeuda().divide(new BigDecimal(1000000)).setScale(6,BigDecimal.ROUND_UP));
							punidades="Millones Nuevos Soles";
						}
					}else if(unidades==4){
						punidades="Porcentaje";
						nombreJasper="DeudaCuadroValorPorcen.jasper";
					}
					
					}
			}
			}
			else if(tipoSalida==2){
				nombreJasper="DeudaLineal.jasper";				
				
				if(unidades==1){
					punidades="Nuevos Soles";
				}else if(unidades==2){
					for(int i=0;i<listaDeuda.size();i++){
						listaDeuda.get(i).setMontoDeuda(listaDeuda.get(i).getMontoDeuda().divide(new BigDecimal(1000)).setScale(4,BigDecimal.ROUND_UP));
						punidades="Miles Nuevos Soles";
					}
				}else if(unidades==3){
					for(int i=0;i<listaDeuda.size();i++){
						listaDeuda.get(i).setMontoDeuda(listaDeuda.get(i).getMontoDeuda().divide(new BigDecimal(1000000)).setScale(6,BigDecimal.ROUND_UP));
						punidades="Millones Nuevos Soles";
					}
				}else if(unidades==4){
					punidades="Porcentaje";
					nombreJasper="DeudaLinealPorcen.jasper";
				}
				
			}else if(tipoSalida==3){

				nombreJasper="DeudaBarras.jasper";				
				
				if(unidades==1){
					punidades="Nuevos Soles";
				}else if(unidades==2){
					for(int i=0;i<listaDeuda.size();i++){
						listaDeuda.get(i).setMontoDeuda(listaDeuda.get(i).getMontoDeuda().divide(new BigDecimal(1000)).setScale(4,BigDecimal.ROUND_UP));
						punidades="Miles Nuevos Soles";
					}
				}else if(unidades==3){
					for(int i=0;i<listaDeuda.size();i++){
						listaDeuda.get(i).setMontoDeuda(listaDeuda.get(i).getMontoDeuda().divide(new BigDecimal(1000000)).setScale(6,BigDecimal.ROUND_UP));
						punidades="Millones Nuevos Soles";
					}
				}else if(unidades==4){
					punidades="Porcentaje";
					nombreJasper="DeudaBarrasPorcen.jasper";
				}
			}

		
		
		pReportes.put("P_unidades", punidades);
		
		JasperPrint jasperPrint=JasperFillManager.fillReport((SATWEBParameterFactory.getPathReporte()+nombreJasper), pReportes,new JRBeanCollectionDataSource(listaDeuda));

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
	
	public HtmlComboBox getCmbtipoGrafico() {
		return cmbtipoGrafico;
	}

	public void setCmbtipoGrafico(HtmlComboBox cmbtipoGrafico) {
		this.cmbtipoGrafico = cmbtipoGrafico;
	}
	
	public HtmlComboBox getCmbUnidadesDeuda() {
		return cmbUnidadesDeuda;
	}

	public void setCmbUnidadesDeuda(HtmlComboBox cmbUnidadesDeuda) {
		this.cmbUnidadesDeuda = cmbUnidadesDeuda;
	}

	public List<SelectItem> getLstTipoAgrupa() {
		return lstTipoAgrupa;
	}

	public void setLstTipoAgrupa(List<SelectItem> lstTipoAgrupa) {
		this.lstTipoAgrupa = lstTipoAgrupa;
	}

	public HtmlComboBox getCmbTipoAgrupa() {
		return cmbTipoAgrupa;
	}

	public void setCmbTipoAgrupa(HtmlComboBox cmbTipoAgrupa) {
		this.cmbTipoAgrupa = cmbTipoAgrupa;
	}

}
