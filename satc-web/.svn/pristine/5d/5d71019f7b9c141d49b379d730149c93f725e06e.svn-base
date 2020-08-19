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

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.SATWEBParameterFactory;
import com.sat.sisat.common.util.Util;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.persistence.CrudServiceBean;
import com.sat.sisat.persistence.entity.DtDeterminacion;
import com.sat.sisat.predial.business.CalculoPredialBoRemote;
import com.sat.sisat.predial.dto.DtDeterminacionPeriodoDTO;

@ManagedBean
@ViewScoped
public class ComparaHistoriaDeterminacionManaged extends BaseManaged {
	
	@EJB
	CalculoPredialBoRemote calculoPredialBo;
	
	private ArrayList<DtDeterminacion> records;
	private DtDeterminacion currentItem = new DtDeterminacion();
	private Integer periodo;
	
	public ComparaHistoriaDeterminacionManaged(){
		
	}
	
	@PostConstruct
	public void init(){
		try{
			records=new ArrayList<DtDeterminacion>();
			
			Integer personaId=getSessionManaged().getContribuyente().getPersonaId();
			if(personaId!=null&&personaId!=Constante.RESULT_PENDING){
				
				DtDeterminacionPeriodoDTO dtDeterminacion=(DtDeterminacionPeriodoDTO)getSessionMap().get("DtDeterminacionPeriodoDTO");
				setPeriodo(Util.toInteger(dtDeterminacion.getAnnoDj()));
				records=calculoPredialBo.getAllDtDeterminacionByPeriodo(personaId,getPeriodo(),Constante.CONCEPTO_PREDIAL);
				
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
				getSessionMap().put("pageReturnDetalleDeter", "/sisat/predial/comparahistoriadeterminacion.xhtml");
				getSessionMap().put("DtDeterminacion", currentItem);
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		return sendRedirectPrincipal();
	}
	
	public void createHRporId() {
		java.sql.Connection connection=null;
		try {
			CrudServiceBean connj=CrudServiceBean.getInstance();
			 connection=connj.connectJasper();
			 
	             HashMap<String,Object> parameters = new HashMap<String,Object>();
	             parameters.put("anio", currentItem.getAnnoDeterminacion());
	             parameters.put("persona_id", currentItem.getPersonaId());
//	             parameters.put("determinacion_id", currentItem.getDeterminacionId());
	             parameters.put("ruta_image", SATWEBParameterFactory.getPathReporteImagenes());
	             parameters.put("responsable", getSessionManaged().getUsuarioLogIn().getNombreUsuario());
	             parameters.put("SUBREPORT_DIR",SATWEBParameterFactory.getPathReporte()) ;
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
	     }catch (Exception jre) {
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
	 
	 public void createPUporId() {
		 java.sql.Connection connection=null;   
		 try {
			 CrudServiceBean connj=CrudServiceBean.getInstance();
			 connection=connj.connectJasper();
			 
	        	if(currentItem.getCantUrbanos()>0){
	        	 
		            HashMap<String,Object> parameters = new HashMap<String,Object>();
		             parameters.put("anio", currentItem.getAnnoDeterminacion());
		             parameters.put("persona_id", currentItem.getPersonaId());
		             parameters.put("determinacion_id", currentItem.getDeterminacionId());
		             parameters.put("ruta_image", SATWEBParameterFactory.getPathReporteImagenes());
		             parameters.put("responsable", getSessionManaged().getUsuarioLogIn().getNombreUsuario());
		             parameters.put("SUBREPORT_DIR", SATWEBParameterFactory.getPathReporte()) ;
		            JasperPrint jasperPrint = JasperFillManager.fillReport((SATWEBParameterFactory.getPathReporte()+"PUporId.jasper"), parameters, connection);
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
	          }
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
	 
	 
	 public void createPRporId() {
		 java.sql.Connection connection=null;
	        try {
	 
	        	CrudServiceBean connj=CrudServiceBean.getInstance();
				 connection=connj.connectJasper();
				 
	        	if(currentItem.getCantRusticos()>0){
			        	 HashMap<String,Object> parameters = new HashMap<String,Object>();
			             parameters.put("anio", currentItem.getAnnoDeterminacion());
			             parameters.put("persona_id", currentItem.getPersonaId());
			             parameters.put("determinacion_id", currentItem.getDeterminacionId());
			             parameters.put("ruta_image", SATWEBParameterFactory.getPathReporteImagenes());
			             parameters.put("responsable", getSessionManaged().getUsuarioLogIn().getNombreUsuario());
			             parameters.put("SUBREPORT_DIR", SATWEBParameterFactory.getPathReporte()) ;
			             JasperPrint jasperPrint = JasperFillManager.fillReport((SATWEBParameterFactory.getPathReporte()+"PRporId.jasper"), parameters, connection);
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
			         
			   }
	        	
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
	
	 public void createHLporId() {
		 java.sql.Connection connection=null;
	        try {
	        	CrudServiceBean connj=CrudServiceBean.getInstance();
				 connection=connj.connectJasper();
				 
	             HashMap<String,Object> parameters = new HashMap<String,Object>();
	             parameters.put("anio", currentItem.getAnnoDeterminacion());
	             parameters.put("persona_id", currentItem.getPersonaId());
//	             parameters.put("determinacion_id", currentItem.getDeterminacionId());
	             parameters.put("ruta_image", SATWEBParameterFactory.getPathReporteImagenes());
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

	
	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
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
