package com.sat.sisat.coactiva.managed;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.fill.JRAbstractLRUVirtualizer;
import net.sf.jasperreports.engine.fill.JRSwapFileVirtualizer;
import net.sf.jasperreports.engine.util.JRSwapFile;

import com.sat.sisat.coactiva.entity.CoactivaMultasTransito2014;
import com.sat.sisat.coactiva.entity.CobranzaCoactiva;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.SATWEBParameterFactory;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.controlycobranza.business.ControlyCobranzaBoRemote;
import com.sat.sisat.controlycobranza.dto.FindCcActo;
import com.sat.sisat.persistence.CrudServiceBean;
import com.sat.sisat.persistence.entity.MpClaseLicencia;

@ManagedBean
@ViewScoped
public class MultasTransito2014Managed extends BaseManaged {
	@EJB
	ControlyCobranzaBoRemote controlycobranzaBo;
	
	private List<CobranzaCoactiva> lista;
	private Integer selectedRow;
	
	private String papeleta;
	private String nroValor;
	private String placa;
	
	@PostConstruct
	public void init() {
		
	}
	
	public void buscar(){
		try{
			lista=new LinkedList<CobranzaCoactiva>();
			if((papeleta!=null&&papeleta.trim().length()>0)||(nroValor!=null&&nroValor.trim().length()>0)||(placa!=null&&placa.trim().length()>0)){
				lista=controlycobranzaBo.getAllMultasTransito2014(papeleta,nroValor,placa);
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	public void limpiar()throws Exception{
		try{
			setNroValor("");
			setPapeleta("");
			setPlaca("");
			lista=new LinkedList<CobranzaCoactiva>();
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	public void exportHtmlTableToExcel() throws IOException{       
		try{
			List<CobranzaCoactiva> listaAll=controlycobranzaBo.getAllMultasTransito2014();
		
			StringBuffer htmlBuffer= new StringBuffer();
			htmlBuffer.append("<table border='0' >");
	  	
			htmlBuffer.append("<tr>");
			htmlBuffer.append("<td>ID</td>");
			htmlBuffer.append("<td>NRO_EXPEDIENTE</td>");
			htmlBuffer.append("<td>NRO_RS</td>");
			htmlBuffer.append("<td>PLACA</td>");
			htmlBuffer.append("<td>NRO_PAPELETA</td>");
			htmlBuffer.append("<td>FECHA_PAPELETA</td>");
			htmlBuffer.append("<td>INFRACCION</td>");
			htmlBuffer.append("<td>MONTO</td>");
			htmlBuffer.append("<td>MONTO_SOLES</td>");
			htmlBuffer.append("<td>fecha_notificacion_RS</td>");
			htmlBuffer.append("<td>condicion</td>");
			htmlBuffer.append("<td>Fecha_emision_RS</td>");
			htmlBuffer.append("<td>conductor</td>");
			htmlBuffer.append("<td>Direccion_Conductor</td>");
			htmlBuffer.append("<td>Propietario</td>");
			htmlBuffer.append("<td>Direccion_Propietario</td>");
			htmlBuffer.append("<td>REC1_EMITIDA</td>");
			htmlBuffer.append("<td>REC_UNO_FECHA_NOTIFICADA</td>");
			htmlBuffer.append("<td>FORMA_NOTIFICACION_PERSONA</td>");
			htmlBuffer.append("<td>CONDICION</td>");
			htmlBuffer.append("<td>OBSERVACIONES</td>");
			htmlBuffer.append("<td>NOTIFICADOR</td>");
			htmlBuffer.append("<td>REC_DOS_EMISION_DESPUES_7MO_DIA_NOPAGO</td>");
			htmlBuffer.append("<td>REC_DOS_FECHA_NOTIFICADA</td>");
			htmlBuffer.append("<td>FORMA_NOTIFICACION</td>");
			htmlBuffer.append("<td>CONDICION</td>");
			htmlBuffer.append("<td>OBSERVACIONES</td>");
			htmlBuffer.append("<td>NOTIFICADOR</td>");
			htmlBuffer.append("<td>MEDIDA_CAUTELAR_TIPO</td>");
			htmlBuffer.append("<td>MOTIVO_SUSTENTO_MC</td>");
			htmlBuffer.append("<td>OBSERVACIONES</td>");
			htmlBuffer.append("<td>SUSPENSION_REC_FECHA</td>");
			htmlBuffer.append("<td>FECHA_INGRESO</td>");
			htmlBuffer.append("<td>APTO</td>");
			htmlBuffer.append("<td>CARGO_NOTIFICACION2</td>");
			htmlBuffer.append("<td>REGISTRAD_POR</td>");
			htmlBuffer.append("<td>FECHA ULT ACTUALIZACION</td>");
			htmlBuffer.append("<td>USUARIO ULT ACTUALIZACION</td>");
			htmlBuffer.append("<td>ESTADO_COLOR</td>");
			
			htmlBuffer.append("<td>MONTO INFRACCION</td>");
			htmlBuffer.append("<td>ESTADO ACTUAL</td>");
			
			htmlBuffer.append("</tr>");	
		  
			Iterator<CobranzaCoactiva> it2 = listaAll.iterator();
			while (it2.hasNext()) {
				CoactivaMultasTransito2014 obj = (CoactivaMultasTransito2014)it2.next();
				htmlBuffer.append("<tr>");
					htmlBuffer.append("<td>").append(obj.getId()==null?"":obj.getId()).append("</td>");
					htmlBuffer.append("<td>").append(obj.getCampo1()==null?"":obj.getCampo1()).append("</td>");
					htmlBuffer.append("<td>").append(obj.getCampo2()==null?"":obj.getCampo2()).append("</td>");
					htmlBuffer.append("<td>").append(obj.getCampo3()==null?"":obj.getCampo3()).append("</td>");
					htmlBuffer.append("<td>").append(obj.getCampo4()==null?"":obj.getCampo4()).append("</td>");
					htmlBuffer.append("<td>").append(obj.getCampo5()==null?"":obj.getCampo5()).append("</td>");
					htmlBuffer.append("<td>").append(obj.getCampo6()==null?"":obj.getCampo6()).append("</td>");
					htmlBuffer.append("<td>").append(obj.getCampo7()==null?"":obj.getCampo7()).append("</td>");
					htmlBuffer.append("<td>").append(obj.getCampo8()==null?"":obj.getCampo8()).append("</td>");
					htmlBuffer.append("<td>").append(obj.getCampo9()==null?"":obj.getCampo9()).append("</td>");
					htmlBuffer.append("<td>").append(obj.getCampo10()==null?"":obj.getCampo10()).append("</td>");
					htmlBuffer.append("<td>").append(obj.getCampo11()==null?"":obj.getCampo11()).append("</td>");
					htmlBuffer.append("<td>").append(obj.getCampo12()==null?"":obj.getCampo12()).append("</td>");
					htmlBuffer.append("<td>").append(obj.getCampo13()==null?"":obj.getCampo13()).append("</td>");
					htmlBuffer.append("<td>").append(obj.getCampo14()==null?"":obj.getCampo14()).append("</td>");
					htmlBuffer.append("<td>").append(obj.getCampo15()==null?"":obj.getCampo15()).append("</td>");
					htmlBuffer.append("<td>").append(obj.getCampo16()==null?"":obj.getCampo16()).append("</td>");
					htmlBuffer.append("<td>").append(obj.getCampo17()==null?"":obj.getCampo17()).append("</td>");
					htmlBuffer.append("<td>").append(obj.getCampo18()==null?"":obj.getCampo18()).append("</td>");
					htmlBuffer.append("<td>").append(obj.getCampo19()==null?"":obj.getCampo19()).append("</td>");
					htmlBuffer.append("<td>").append(obj.getCampo20()==null?"":obj.getCampo20()).append("</td>");
					htmlBuffer.append("<td>").append(obj.getCampo21()==null?"":obj.getCampo21()).append("</td>");
					htmlBuffer.append("<td>").append(obj.getCampo22()==null?"":obj.getCampo22()).append("</td>");
					htmlBuffer.append("<td>").append(obj.getCampo23()==null?"":obj.getCampo23()).append("</td>");
					htmlBuffer.append("<td>").append(obj.getCampo24()==null?"":obj.getCampo24()).append("</td>");
					htmlBuffer.append("<td>").append(obj.getCampo25()==null?"":obj.getCampo25()).append("</td>");
					htmlBuffer.append("<td>").append(obj.getCampo26()==null?"":obj.getCampo26()).append("</td>");
					htmlBuffer.append("<td>").append(obj.getCampo27()==null?"":obj.getCampo27()).append("</td>");
					htmlBuffer.append("<td>").append(obj.getCampo28()==null?"":obj.getCampo28()).append("</td>");
					htmlBuffer.append("<td>").append(obj.getCampo29()==null?"":obj.getCampo29()).append("</td>");
					htmlBuffer.append("<td>").append(obj.getCampo30()==null?"":obj.getCampo30()).append("</td>");
					htmlBuffer.append("<td>").append(obj.getCampo31()==null?"":obj.getCampo31()).append("</td>");
					htmlBuffer.append("<td>").append(obj.getCampo32()==null?"":obj.getCampo32()).append("</td>");
					htmlBuffer.append("<td>").append(obj.getCampo33()==null?"":obj.getCampo33()).append("</td>");
					htmlBuffer.append("<td>").append(obj.getCampo34()==null?"":obj.getCampo34()).append("</td>");
					htmlBuffer.append("<td>").append(obj.getCampo35()==null?"":obj.getCampo35()).append("</td>");
					
					htmlBuffer.append("<td>").append(obj.getFechaRegistroUpd()==null?"":DateUtil.getTimestampToString(obj.getFechaRegistroUpd())).append("</td>");
					htmlBuffer.append("<td>").append(obj.getUsuarioIdUpd()==null?"":obj.getUsuarioIdUpd()).append("</td>");
					htmlBuffer.append("<td>").append(obj.getEstadocolor()==null?"":obj.getEstadocolor()).append("</td>");
					
					htmlBuffer.append("<td>").append(obj.getDeuda_actual()==null?"":obj.getDeuda_actual()).append("</td>");
					htmlBuffer.append("<td>").append(obj.getEstado_actual()==null?"":obj.getEstado_actual()).append("</td>");
					
				htmlBuffer.append("</tr>");	
			}
			htmlBuffer.append("</table>");
			//Set the filename
			//DateTime dt = new DateTime();
			//DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd_HHmmss");
			  String filename = "multas_transito_2014_"+System.currentTimeMillis() + ".xls";
			  //Setup the output
			  String contentType = "application/vnd.ms-excel";
			  FacesContext fc = FacesContext.getCurrentInstance();
			   
			  HttpServletResponse response = (HttpServletResponse)fc.getExternalContext().getResponse();
			  response.setHeader("Content-disposition", "attachment; filename=" + filename);
			  response.setContentType(contentType);
			   
			  //Write the table back out
			  PrintWriter out = response.getWriter();
			  out.print(htmlBuffer);
			  out.close();
			  fc.responseComplete();
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		} 
	}
	
	public void cusSaveAction(CobranzaCoactiva c){
		try{
			cancelEdition();
			
		    c.setCusEditFlag(false);
		    
		    c.setFechaRegistroUpd(DateUtil.getCurrentDate());
			c.setUsuarioIdUpd(getUser().getUsuario());
			c.setTerminalUpd(getUser().getTerminal());
			
		    controlycobranzaBo.update(c);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void cusCancelAction(CobranzaCoactiva c){
		cancelEdition();
		
		c.setCusEditFlag(false);
	}
	
	public String cusEditAction(CobranzaCoactiva c){
		cancelEdition();
		
	    c.setCusEditFlag(true);
	    return null;
	}

	private void cancelEdition(){
		Iterator<CobranzaCoactiva> it = lista.iterator();
		while(it.hasNext()){
			CobranzaCoactiva o= it.next();
			o.setCusEditFlag(false);
		}
	}
	
	public List<CobranzaCoactiva> getLista() {
		return lista;
	}

	public void setLista(List<CobranzaCoactiva> lista) {
		this.lista = lista;
	}
	
	public Integer getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(Integer selectedRow) {
		this.selectedRow = selectedRow;
	}
	
	public String getNroValor() {
		return nroValor;
	}

	public void setNroValor(String nroValor) {
		this.nroValor = nroValor;
	}
	
	public String getPapeleta() {
		return papeleta;
	}

	public void setPapeleta(String papeleta) {
		this.papeleta = papeleta;
	}
	
	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}
}

