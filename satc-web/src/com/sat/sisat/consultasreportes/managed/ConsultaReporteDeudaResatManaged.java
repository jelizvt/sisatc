package com.sat.sisat.consultasreportes.managed;

import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.SATWEBParameterFactory;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.persistence.CrudServiceBean;

@ManagedBean
@ViewScoped
public class ConsultaReporteDeudaResatManaged extends BaseManaged{
	private static final long serialVersionUID = 1089541023986679063L;
	private Integer personaId;
	
	public void imprimirEstadoCuentaResat()throws SQLException {
		java.sql.Connection connection=null;	
		try {
			CrudServiceBean connj=CrudServiceBean.getInstance();
			 connection=connj.connectJasper();
			 
		     HashMap<String,Object> parameters = new HashMap<String,Object>();
		     parameters.put("persona_id", personaId);
		     parameters.put("ruta_image",SATWEBParameterFactory.getPathReporteImagenes()) ;
		     parameters.put("SUBREPORT_DIR",SATWEBParameterFactory.getPathReporte()) ;
		     parameters.put("responsable", getSessionManaged().getUsuarioLogIn().getNombreUsuario());
		     parameters.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
		     JasperPrint jasperPrint = JasperFillManager.fillReport((SATWEBParameterFactory.getPathReporte()+"ec_estado_cuenta_resat_individual.jasper"), parameters,connection);
		     ByteArrayOutputStream output = new ByteArrayOutputStream();
		     JasperExportManager.exportReportToPdfStream(jasperPrint, output);	                           
		     HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();		         		          
		     response.setContentType("application/pdf");	         
		     response.addHeader("Content-Disposition","attachment;filename=ec_estado_cuenta_resat"+personaId+".pdf");
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
	
	public Integer getPersonaId() {
			return personaId;
	}
	public void setPersonaId(Integer personaId) {
			this.personaId = personaId;
	}

}
