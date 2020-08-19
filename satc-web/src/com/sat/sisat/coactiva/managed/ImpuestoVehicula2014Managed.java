package com.sat.sisat.coactiva.managed;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import com.sat.sisat.coactiva.entity.CoactivaImpuestoVehicula2013;
import com.sat.sisat.coactiva.entity.CoactivaImpuestoVehicula2014;
import com.sat.sisat.coactiva.entity.CobranzaCoactiva;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.controlycobranza.business.ControlyCobranzaBoRemote;

@ManagedBean
@ViewScoped
public class ImpuestoVehicula2014Managed extends BaseManaged {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	ControlyCobranzaBoRemote controlycobranzaBo;
	
	private List<CobranzaCoactiva> lista;
	private Integer selectedRow;
	
	private Integer personaId;
	private String nroValor;
	
	@PostConstruct
	public void init() {
		
	}
	
	public void buscar(){
		try{
			lista=new LinkedList<CobranzaCoactiva>();
			if((personaId!=null&&personaId>0)||(nroValor!=null&&nroValor.trim().length()>0)){
				lista=controlycobranzaBo.getAllImpuestoVehicula2014(personaId);
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	public void limpiar()throws Exception{
		try{
			setNroValor("");
			setPersonaId(null);
			lista=new LinkedList<CobranzaCoactiva>();
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	public void exportHtmlTableToExcel() throws IOException{       
		try{
			List<CobranzaCoactiva> listaAll=controlycobranzaBo.getAllImpuestoVehicula2014(); 
				  StringBuffer htmlBuffer= new StringBuffer();
				  htmlBuffer.append("<table border='0' >");
				  
				  htmlBuffer.append("<tr>");
					htmlBuffer.append("<td>ID</td>");
					htmlBuffer.append("<td>EXP.</td>");
					htmlBuffer.append("<td>PLACA</td>");
					htmlBuffer.append("<td>AÑO</td>");
					htmlBuffer.append("<td>COD.CONTRIBUYENTE</td>");
					htmlBuffer.append("<td>NOMBRE_RAZON_SOCIAL</td>");
					htmlBuffer.append("<td>DOMICILIO FISCAL</td>");
					htmlBuffer.append("<td>DEUDA ACTUAL</td>");
					htmlBuffer.append("<td>ESTADO_ACTUAL</td>");
//					htmlBuffer.append("<td></td>");
					htmlBuffer.append("<td>FECHA GENER. REC1</td>");
					htmlBuffer.append("<td>FECHA EMISI. REC1</td>");
					htmlBuffer.append("<td>FECHA NOTIF. REC1</td>");
					htmlBuffer.append("<td>FECHA GENER. REC2</td>");
					htmlBuffer.append("<td>FECHA NOTIF. REC2</td>");
					htmlBuffer.append("<td>COSTAS</td>");
					htmlBuffer.append("<td>SUSPENSIÓN</td>");
					htmlBuffer.append("<td>OBSERVACIONES</td>");
					
				
					htmlBuffer.append("<td>REGISTRADO POR</td>");
					htmlBuffer.append("<td>FECHA ULT ACTUALIZACION</td>");
					
					htmlBuffer.append("<td>ESTADO_COLOR</td>");
					htmlBuffer.append("</tr>");	
				  
					Iterator<CobranzaCoactiva> it2 = listaAll.iterator();
					while (it2.hasNext()) {
						CoactivaImpuestoVehicula2014 obj = (CoactivaImpuestoVehicula2014)it2.next();
						htmlBuffer.append("<tr>");
							htmlBuffer.append("<td>").append(obj.getId()==null?"":obj.getId()).append("</td>");
							htmlBuffer.append("<td>").append(obj.getCampo1()==null?"":obj.getCampo1()).append("</td>");
							htmlBuffer.append("<td>").append(obj.getCampo2()==null?"":obj.getCampo2()).append("</td>");
							htmlBuffer.append("<td>").append(obj.getCampo3()==null?"":obj.getCampo3()).append("</td>");
							htmlBuffer.append("<td>").append(obj.getCampo4()==null?"":obj.getCampo4()).append("</td>");
							htmlBuffer.append("<td>").append(obj.getCampo5()==null?"":obj.getCampo5()).append("</td>");
							htmlBuffer.append("<td>").append(obj.getCampo6()==null?"":obj.getCampo6()).append("</td>");
							
							htmlBuffer.append("<td>").append(obj.getDeuda()==null?"":obj.getDeuda()).append("</td>");
							htmlBuffer.append("<td>").append(obj.getEstado_real()==null?"":obj.getEstado_real()).append("</td>");
						
//							htmlBuffer.append("<td></td>");
							htmlBuffer.append("<td>").append(obj.getFecha_genera_rec1()==null?"":obj.getFecha_genera_rec1()).append("</td>");
							htmlBuffer.append("<td>").append(obj.getFecha_emision_rec1()==null?"":obj.getFecha_emision_rec1()).append("</td>");
							htmlBuffer.append("<td>").append(obj.getFecha_notificacion_rec1()==null?"":obj.getFecha_notificacion_rec1()).append("</td>");
							
							htmlBuffer.append("<td>").append(obj.getFecha_genera_rec2()==null?"":obj.getFecha_genera_rec2()).append("</td>");
							htmlBuffer.append("<td>").append(obj.getFecha_notificacion_rec2()==null?"":obj.getFecha_notificacion_rec2()).append("</td>");
							
							htmlBuffer.append("<td>").append(obj.getCostas()==null?"":obj.getCostas()).append("</td>");
							htmlBuffer.append("<td>").append(obj.getSuspension()==null?"":obj.getSuspension()).append("</td>");
							htmlBuffer.append("<td>").append(obj.getObservaciones()==null?"":obj.getObservaciones()).append("</td>");
							
							htmlBuffer.append("<td>").append(obj.getUsuarioIdUpd()==null?"":obj.getUsuarioIdUpd()).append("</td>");
							htmlBuffer.append("<td>").append(obj.getFechaRegistroUpd()==null?"":DateUtil.getTimestampToString(obj.getFechaRegistroUpd())).append("</td>");
							
							htmlBuffer.append("<td>").append(obj.getEstado()==null?"":obj.getEstado()).append("</td>");
						htmlBuffer.append("</tr>");	
							
					
					}
				  htmlBuffer.append("</table>");
					//Set the filename
					//DateTime dt = new DateTime();
					//DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd_HHmmss");
			      String filename = "impuesto_vehicular_unificado"+System.currentTimeMillis() + ".xls";
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
	
	public Integer getPersonaId() {
		return personaId;
	}

	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}

	public String getNroValor() {
		return nroValor;
	}

	public void setNroValor(String nroValor) {
		this.nroValor = nroValor;
	}
}