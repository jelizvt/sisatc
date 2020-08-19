package com.sat.sisat.predial.managed;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.richfaces.component.html.HtmlExtendedDataTable;
import org.richfaces.model.selection.SimpleSelection;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.SATWEBParameterFactory;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.persistence.CrudServiceBean;
import com.sat.sisat.persistence.entity.DtDeterminacion;
import com.sat.sisat.predial.business.CalculoPredialBoRemote;
import com.sat.sisat.predial.business.RegistroPrediosBoRemote;

@ManagedBean
@ViewScoped
public class HistoriaDeterminacionManaged extends BaseManaged {
	@EJB
	RegistroPrediosBoRemote registroPrediosBo;
	
	@EJB
	CalculoPredialBoRemote calculoPredialBo;
	
	private ArrayList<DtDeterminacion> records;

	private SimpleSelection selection = new SimpleSelection();
	private HtmlExtendedDataTable table;
	private int currentRow;
	private DtDeterminacion currentItem = new DtDeterminacion();
	private String codigoPredio;
	
	public HistoriaDeterminacionManaged(){
		
	}
	
	@PostConstruct
	public void init(){
		try{
			records=new ArrayList<DtDeterminacion>();
			Integer personaId=getSessionManaged().getContribuyente().getPersonaId();
			if(personaId!=null&&personaId!=Constante.RESULT_PENDING){
				records=calculoPredialBo.getAllDtDeterminacionByPersonaId(personaId,Constante.CONCEPTO_PREDIAL);
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public String salir(){
		return sendRedirectPrincipal();
	}
	
	public String detalleDeterminacion(){
		try{
			if(currentItem!=null){
				getSessionMap().put("determinacion_id", currentItem.getDeterminacionId());
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		return sendRedirectPrincipal();
	}
	     
	//hparedes:ini 28/06/2012
	 public void createHR() {
		 java.sql.Connection connection=null;
	        try {
	        	CrudServiceBean connj=CrudServiceBean.getInstance();
				 connection=connj.connectJasper();
	        	String path_context=FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
			     String path_report=path_context+"/sisat/reportes/";
			     String path_imagen=path_context+"/sisat/reportes/imagen/";
			     
	        	
	             HashMap<String,Object> parameters = new HashMap<String,Object>();
	             parameters.put("anio", currentItem.getAnnoDeterminacion());
	             parameters.put("persona_id", currentItem.getPersonaId());
	             parameters.put("ruta_image", SATWEBParameterFactory.getPathReporteImagenes()) ;
	             parameters.put("responsable", getSessionManaged().getUsuarioLogIn().getNombreUsuario());
	 			 parameters.put("SUBREPORT_DIR", SATWEBParameterFactory.getPathReporte()) ;
	 			
	                JasperPrint jasperPrint = JasperFillManager.fillReport((SATWEBParameterFactory.getPathReporte()+"HR.jasper"), parameters, connection);
	            
	             ByteArrayOutputStream output = new ByteArrayOutputStream();
	                JasperExportManager.exportReportToPdfStream(jasperPrint, output);
	               
	          HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	                 response.setContentType("application/pdf");
	          response.addHeader("Content-Disposition","attachment;filename=HRReport.pdf");
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
	 
	 public void createPU() {
		 	java.sql.Connection connection=null;
	        try {
				 CrudServiceBean connj=CrudServiceBean.getInstance();
				 connection=connj.connectJasper();
				 
	        	String path_context=FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
			     String path_report=path_context+"/sisat/reportes/";
			     String path_imagen=path_context+"/sisat/reportes/imagen/";
			     
	            HashMap<String,Object> parameters = new HashMap<String,Object>();
	            parameters.put("anio", currentItem.getAnnoDeterminacion());
	            parameters.put("persona_id", currentItem.getPersonaId());
	            parameters.put("ruta_image", SATWEBParameterFactory.getPathReporteImagenes()) ;
	            parameters.put("responsable", getSessionManaged().getUsuarioLogIn().getNombreUsuario());
	 			parameters.put("SUBREPORT_DIR", SATWEBParameterFactory.getPathReporte()) ;
	            JasperPrint jasperPrint = JasperFillManager.fillReport((SATWEBParameterFactory.getPathReporte()+"PU.jasper"), parameters, connection);
	            ByteArrayOutputStream output = new ByteArrayOutputStream();
	            JasperExportManager.exportReportToPdfStream(jasperPrint, output);
	               
	          HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	                 response.setContentType("application/pdf");
	          response.addHeader("Content-Disposition","attachment;filename=PUReports.pdf");
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
	 public void createPR() {
		 java.sql.Connection connection=null;   
		 try {
			 CrudServiceBean connj=CrudServiceBean.getInstance();
			 connection=connj.connectJasper();
			 
	        	String path_context=FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
			     String path_report=path_context+"/sisat/reportes/";
			     String path_imagen=path_context+"/sisat/reportes/imagen/";
			     
	             HashMap<String,Object> parameters = new HashMap<String,Object>();
	             parameters.put("anio", currentItem.getAnnoDeterminacion());
	             parameters.put("persona_id", currentItem.getPersonaId());
	             parameters.put("ruta_image", SATWEBParameterFactory.getPathReporteImagenes()) ;
	             parameters.put("responsable", getSessionManaged().getUsuarioLogIn().getNombreUsuario());
	 			 parameters.put("SUBREPORT_DIR",SATWEBParameterFactory.getPathReporte()) ;
	             JasperPrint jasperPrint = JasperFillManager.fillReport((SATWEBParameterFactory.getPathReporte()+"PR.jasper"), parameters, connection);
	             ByteArrayOutputStream output = new ByteArrayOutputStream();
	                JasperExportManager.exportReportToPdfStream(jasperPrint, output);
	               
	          HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	                 response.setContentType("application/pdf");
	          response.addHeader("Content-Disposition","attachment;filename=PRReport.pdf");
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
	 public void createHL() {
		 java.sql.Connection connection=null;   
		 try {
			 CrudServiceBean connj=CrudServiceBean.getInstance();
			 connection=connj.connectJasper();	
	        	
	        	String path_context=FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
			     String path_report=path_context+"/sisat/reportes/";
			     String path_imagen=path_context+"/sisat/reportes/imagen/";
			     
	        	 HashMap<String,Object> parameters = new HashMap<String,Object>();
	             parameters.put("anio", currentItem.getAnnoDeterminacion());
	             parameters.put("persona_id", currentItem.getPersonaId());
	             parameters.put("ruta_image", SATWEBParameterFactory.getPathReporteImagenes()) ;
	             parameters.put("responsable", getSessionManaged().getUsuarioLogIn().getNombreUsuario());
	 			 parameters.put("SUBREPORT_DIR", SATWEBParameterFactory.getPathReporte()) ;
	             JasperPrint jasperPrint = JasperFillManager.fillReport((SATWEBParameterFactory.getPathReporte()+"HojaLiquidacionIP.jasper"), parameters, connection);
	             ByteArrayOutputStream output = new ByteArrayOutputStream();
	                JasperExportManager.exportReportToPdfStream(jasperPrint, output);
	               
	          HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	                 response.setContentType("application/pdf");
	          response.addHeader("Content-Disposition","attachment;filename=HojaLiquidacionIPReport.pdf");
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
	//hparedes:fin 28/06/2012
	 
	 
	public SimpleSelection getSelection() {
		return selection;
	}

	public void setSelection(SimpleSelection selection) {
		this.selection = selection;
	}

	public HtmlExtendedDataTable getTable() {
		return table;
	}

	public void setTable(HtmlExtendedDataTable table) {
		this.table = table;
	}

	public int getCurrentRow() {
		return currentRow;
	}

	public void setCurrentRow(int currentRow) {
		this.currentRow = currentRow;
	}

	public String getCodigoPredio() {
		return codigoPredio;
	}

	public void setCodigoPredio(String codigoPredio) {
		this.codigoPredio = codigoPredio;
	}

	public ArrayList<DtDeterminacion> getRecords() {
		return records;
	}

	public void setRecords(ArrayList<DtDeterminacion> records) {
		this.records = records;
	}
	public DtDeterminacion getCurrentItem() {
		return currentItem;
	}

	public void setCurrentItem(DtDeterminacion currentItem) {
		this.currentItem = currentItem;
	}
}
