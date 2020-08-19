package com.sat.sisat.papeletas.managed;

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
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.SATWEBParameterFactory;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.papeleta.business.PapeletaBoRemote;
import com.sat.sisat.papeleta.dto.RecordPapeletaDTO;
import com.sat.sisat.persistence.CrudServiceBean;
import com.sat.sisat.persona.business.PersonaBoRemote;
import com.sat.sisat.vehicular.dto.BuscarPersonaDTO;

@ManagedBean
@ViewScoped
public class RecordInfraccionVehiculoManaged extends BaseManaged {
	
	@EJB
	PapeletaBoRemote papeletaBo;
	
	@EJB
	PersonaBoRemote personaBo;

	private String numeroPlaca;

	private ArrayList<RecordPapeletaDTO> records;
	private int currentRow;
	private RecordPapeletaDTO currentItem = new RecordPapeletaDTO();

	private Integer realizaConsulta;
	
	public RecordInfraccionVehiculoManaged() throws Exception {
		
	}
	
	@PostConstruct
	public void init(){
		try{
			records=new ArrayList<RecordPapeletaDTO>();
			realizaConsulta=Constante.RESULT_PENDING;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void buscar() throws Exception{
		try{
			if(numeroPlaca!=null&&numeroPlaca.trim().length()>0){
				records=papeletaBo.getRecordVehiculo(numeroPlaca);
				realizaConsulta=Constante.RESULT_SUCCESS;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void limpiar()throws Exception{
		records=new ArrayList<RecordPapeletaDTO>();
		currentItem = new RecordPapeletaDTO();
		numeroPlaca="";
		realizaConsulta=Constante.RESULT_PENDING;
	}
	
	public void exportarPdf() {
		java.sql.Connection connection=null;
		try {
			if(realizaConsulta>Constante.RESULT_PENDING){
				if(numeroPlaca!=null&&numeroPlaca.trim().length()>0&&records!=null&&records.size()==0){
	        		CrudServiceBean connj=CrudServiceBean.getInstance();
	   			    connection=connj.connectJasper(); 
	        		
	        		  HashMap<String,Object> parameters = new HashMap<String,Object>();
	        		  parameters.put("placa", numeroPlaca);
		   			  parameters.put("nombre_propietario", "");
		   			  parameters.put("direccion_propietario", "");
//		   			  parameters.put("fecha_expedicion", DateUtil.convertDateToString(DateUtil.getCurrentDate()));
		   			  parameters.put("p_responsable", getSessionManaged().getUser().getUsuario());
		   			  parameters.put("ruta_image", SATWEBParameterFactory.getPathReporteImagenes()) ;
		   			  //--
		   			  JasperPrint jasperPrint = JasperFillManager.fillReport((SATWEBParameterFactory.getPathReporte()+"pa_record_vehiculonoinf.jasper"), parameters, connection);
		   		      ByteArrayOutputStream output = new ByteArrayOutputStream();
		   		      
		   		      JasperExportManager.exportReportToPdfStream(jasperPrint, output);
		   			  HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		   			  response.setContentType("application/pdf");
		   			  response.addHeader("Content-Disposition","attachment;filename=record_vehiculo_noinfraccion.pdf");
		   			  response.setContentLength(output.size());
		   			  ServletOutputStream servletOutputStream = response.getOutputStream();
		   			  servletOutputStream.write(output.toByteArray(), 0, output.size());
		   			  servletOutputStream.flush();
		   			  servletOutputStream.close();
		   			  FacesContext.getCurrentInstance().responseComplete();
		   			  
	        	}if(records.size()>0){
	        		 CrudServiceBean connj=CrudServiceBean.getInstance();
	    			 connection=connj.connectJasper();
	    			
	        		  RecordPapeletaDTO papeletaDto=records.get(0);
	        		
	        		  HashMap<String,Object> parameters = new HashMap<String,Object>();
		   			  parameters.put("placa", papeletaDto.getPlaca());
		   			  parameters.put("nombre_propietario", "");
		   			  parameters.put("direccion_propietario", "");
		   			  parameters.put("fecha_expedicion", DateUtil.convertDateToString(DateUtil.getCurrentDate()));
		   			  parameters.put("hora_expedicion", DateUtil.getHoraActual());
		   			  parameters.put("ruta_image", SATWEBParameterFactory.getPathReporteImagenes()) ;
		   			  parameters.put("p_responsable", getSessionManaged().getUser().getUsuario());
		   			  JasperPrint jasperPrint = JasperFillManager.fillReport((SATWEBParameterFactory.getPathReporte()+"pa_record_vehiculo.jasper"), parameters, connection);
		   		      ByteArrayOutputStream output = new ByteArrayOutputStream();
		   		      JasperExportManager.exportReportToPdfStream(jasperPrint, output);
		   			  HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		   			  response.setContentType("application/pdf");
		   			  response.addHeader("Content-Disposition","attachment;filename=record_vehicular.pdf");
		   			  response.setContentLength(output.size());
		   			  ServletOutputStream servletOutputStream = response.getOutputStream();
		   			  servletOutputStream.write(output.toByteArray(), 0, output.size());
		   			  servletOutputStream.flush();
		   			  servletOutputStream.close();
		   			  FacesContext.getCurrentInstance().responseComplete();
	        	}
			}
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

	public String getNumeroPlaca() {
		return numeroPlaca;
	}

	public void setNumeroPlaca(String numeroPlaca) {
		this.numeroPlaca = numeroPlaca;
	}

	public ArrayList<RecordPapeletaDTO> getRecords() {
		return records;
	}

	public void setRecords(ArrayList<RecordPapeletaDTO> records) {
		this.records = records;
	}

	public int getCurrentRow() {
		return currentRow;
	}

	public void setCurrentRow(int currentRow) {
		this.currentRow = currentRow;
	}

	public RecordPapeletaDTO getCurrentItem() {
		return currentItem;
	}

	public void setCurrentItem(RecordPapeletaDTO currentItem) {
		this.currentItem = currentItem;
	}
	
	public Integer getRealizaConsulta() {
		return realizaConsulta;
	}

	public void setRealizaConsulta(Integer realizaConsulta) {
		this.realizaConsulta = realizaConsulta;
	}
}
