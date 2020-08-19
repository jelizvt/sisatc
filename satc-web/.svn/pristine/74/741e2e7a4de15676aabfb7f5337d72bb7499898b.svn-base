package com.sat.sisat.controlycobranza.managed;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
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
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

import com.sat.sisat.common.business.GeneralBoRemote;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.SATWEBParameterFactory;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.controlycobranza.business.ControlyCobranzaBoRemote;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.menus.business.MenuBoRemote;
import com.sat.sisat.menus.dto.SimpleMenuDTO;
import com.sat.sisat.persistence.CrudServiceBean;
import com.sat.sisat.persistence.entity.CcCarteraDeuda;
import com.sat.sisat.persona.business.PersonaBoRemote;

@ManagedBean
@ViewScoped
public class CarteraManaged extends BaseManaged {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2855096379356064580L;

	@EJB
	PersonaBoRemote personaBo;

	@EJB
	GeneralBoRemote generalBo;

	@EJB
	ControlyCobranzaBoRemote controlycobranzaBo;
	
	@EJB
	MenuBoRemote menuBo;
	
	private List<CcCarteraDeuda> listCarteraDeudas;
	
	private Integer carteraDeudaId;
	
	// INICIO PERMISOS PARA EL MODULO -=CRAMIREZ=-
	 	private List<SimpleMenuDTO> listPermisosSubmenu = new ArrayList<SimpleMenuDTO>();
	 	private boolean permisoDescargarExcelTerminal;
	 	private boolean permisoDescargarExcelResumido;
	 // FIN PERMISOS PARA EL MODULO
	
	@PostConstruct
	public void init(){
		permisosMenu();
		try {
			listCarteraDeudas = controlycobranzaBo.obtenerCarteraDeudas();
		} catch (SisatException e) {
		
			WebMessages.messageError(e.getMessage());
		}				
	}
	
	public void permisosMenu() {	
		try {
			int submenuId = Constante.CARTERA_DE_TRABAJO;
			
			int permisoDescargarExcelTerminalId = Constante.DESCARGAR_EXCEL_TERMINAL;
			int permisoDescargarExcelResumidoId = Constante.DESCARGAR_EXCEL_RESUMIDO;
			
			permisoDescargarExcelTerminal = false;
			permisoDescargarExcelResumido = false;
			
			listPermisosSubmenu  = menuBo.getAccesosSubmenuUsuario( getSessionManaged().getUsuarioLogIn().getUsuarioId() , submenuId);
			
			Iterator<SimpleMenuDTO> menuIterar = listPermisosSubmenu.iterator();
			while (menuIterar.hasNext()) {
				SimpleMenuDTO lsm = menuIterar.next();
				if(lsm.getItemId() == permisoDescargarExcelTerminalId) {
					permisoDescargarExcelTerminal = true;
				}
				if(lsm.getItemId() == permisoDescargarExcelResumidoId) {
					permisoDescargarExcelResumido = true;
				}
			}
		} catch (SisatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	public String ver(Integer carteraDeudaId){
		this.getSessionManaged().getSessionMap().put("carteraDeudaId", carteraDeudaId);
		
		this.getSessionManaged().setPage("/sisat/controlycobranza/carteradetalle.xhtml");
		
		return sendRedirectPrincipal();
	}
	
	public void verExcelCarteraDeuda(Integer carteraDeudaId){
		
		this.carteraDeudaId = carteraDeudaId;
		
	}
	
	public void generarReportesXLSTrimestral() {
		java.sql.Connection connection=null;
		try {
			CrudServiceBean connj=CrudServiceBean.getInstance();
			connection=connj.connectJasper();
			 
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			
			parameters.put("SUBREPORT_DIR", SATWEBParameterFactory.getPathReporte());
			parameters.put("carteraDeudaId", this.carteraDeudaId);
			JasperPrint jasperPrint = JasperFillManager
					.fillReport((SATWEBParameterFactory.getPathReporte() + "cc_cartera_reporte_trimestre.jasper"),
							parameters,
							connection);

			ByteArrayOutputStream output = new ByteArrayOutputStream();			
			 JRXlsxExporter exporterXls = new JRXlsxExporter();  
			    exporterXls.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);  
			    exporterXls.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, true);  
			    exporterXls.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, true);  
			    exporterXls.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, false);  
			    exporterXls.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, true);  
			    exporterXls.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, true);  
			    exporterXls.setParameter(JRXlsExporterParameter.MAXIMUM_ROWS_PER_SHEET, 0); 
			    exporterXls.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);  
			    exporterXls.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, System.currentTimeMillis() + "prueba" + ".xlsx");  
			    exporterXls.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, output);  
			    exporterXls.exportReport();   


			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			response.setContentType("application/vnd.ms-excel");  
		    response.setHeader("Content-Disposition", "attachment;filename=" + System.currentTimeMillis() + ".xlsx");  
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
	
	
	public void generarReportesXLSResume() {
		java.sql.Connection connection=null;
		try {
			CrudServiceBean connj=CrudServiceBean.getInstance();
			connection=connj.connectJasper();
			 
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			
			parameters.put("SUBREPORT_DIR", SATWEBParameterFactory.getPathReporte());
			parameters.put("carteraDeudaId", this.carteraDeudaId);
			JasperPrint jasperPrint = JasperFillManager
					.fillReport((SATWEBParameterFactory.getPathReporte() + "cc_cartera_reporte_resumen.jasper"),
							parameters,
							connection);

			ByteArrayOutputStream output = new ByteArrayOutputStream();			
			 JRXlsxExporter exporterXls = new JRXlsxExporter();  
			    exporterXls.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);  
			    exporterXls.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, true);  
			    exporterXls.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, true);  
			    exporterXls.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, false);  
			    exporterXls.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, true);  
			    exporterXls.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, true);  
			    exporterXls.setParameter(JRXlsExporterParameter.MAXIMUM_ROWS_PER_SHEET, 0); 
			    exporterXls.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);  
			    exporterXls.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, System.currentTimeMillis() + "prueba" + ".xlsx");  
			    exporterXls.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, output);  
			    exporterXls.exportReport();   


			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			response.setContentType("application/vnd.ms-excel");  
		    response.setHeader("Content-Disposition", "attachment;filename=" + System.currentTimeMillis() + ".xlsx");  
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
	
	public List<CcCarteraDeuda> getListCarteraDeudas() {
		return listCarteraDeudas;
	}

	public void setListCarteraDeudas(List<CcCarteraDeuda> listCarteraDeudas) {
		this.listCarteraDeudas = listCarteraDeudas;
	}

	public Integer getCarteraDeudaId() {
		return carteraDeudaId;
	}

	public void setCarteraDeudaId(Integer carteraDeudaId) {
		this.carteraDeudaId = carteraDeudaId;
	}

	public List<SimpleMenuDTO> getListPermisosSubmenu() {
		return listPermisosSubmenu;
	}

	public void setListPermisosSubmenu(List<SimpleMenuDTO> listPermisosSubmenu) {
		this.listPermisosSubmenu = listPermisosSubmenu;
	}

	public boolean isPermisoDescargarExcelTerminal() {
		return permisoDescargarExcelTerminal;
	}

	public void setPermisoDescargarExcelTerminal(boolean permisoDescargarExcelTerminal) {
		this.permisoDescargarExcelTerminal = permisoDescargarExcelTerminal;
	}

	public boolean isPermisoDescargarExcelResumido() {
		return permisoDescargarExcelResumido;
	}

	public void setPermisoDescargarExcelResumido(boolean permisoDescargarExcelResumido) {
		this.permisoDescargarExcelResumido = permisoDescargarExcelResumido;
	}

	
	
}
