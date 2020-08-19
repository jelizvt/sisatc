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

import com.sat.sisat.coactiva.entity.CoactivaImpuestoVehicula2012;
import com.sat.sisat.coactiva.entity.CobranzaCoactiva;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.controlycobranza.business.ControlyCobranzaBoRemote;

@ManagedBean
@ViewScoped
public class ImpuestoVehicula2012Managed extends BaseManaged {
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
				lista=controlycobranzaBo.getAllImpuestoVehicula2012(personaId,nroValor);
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
			List<CobranzaCoactiva> listaAll=controlycobranzaBo.getAllImpuestoVehicula2012();
				  StringBuffer htmlBuffer= new StringBuffer();
				  htmlBuffer.append("<table border='0' >");
				  htmlBuffer.append("<tr>");
					htmlBuffer.append("<td>ID</td>");
					htmlBuffer.append("<td>NRO_REFER</td>");
					htmlBuffer.append("<td>NRO_EXP</td>");
					htmlBuffer.append("<td>NUMERO_RESOLUCION_EMITIDO</td>");
					htmlBuffer.append("<td>ETAPA</td>");
					htmlBuffer.append("<td>PLACA</td>");
					htmlBuffer.append("<td>CODIGO_ANTIGUO</td>");
					htmlBuffer.append("<td>CODIGO NUEVO</td>");
					htmlBuffer.append("<td>CONTRIBUYENTE</td>");
					htmlBuffer.append("<td>DIRECCION</td>");
					htmlBuffer.append("<td>BARRIO</td>");
					htmlBuffer.append("<td>BASE_IMPONIBLE</td>");
					htmlBuffer.append("<td>INSOLUTO</td>");
					htmlBuffer.append("<td>INTERES</td>");
					htmlBuffer.append("<td>DEREC_EMISION</td>");
					htmlBuffer.append("<td>ACTUALIZACION</td>");
					htmlBuffer.append("<td>CUOTAS_PAGADAS</td>");
					htmlBuffer.append("<td>TOTAL_DEUDA</td>");
					htmlBuffer.append("<td>MONTO_LETRAS</td>");
					htmlBuffer.append("<td>CONDICION_DEUDA</td>");
					htmlBuffer.append("<td>NRO_ORD_NOT</td>");
					htmlBuffer.append("<td>ANYO</td>");
					htmlBuffer.append("<td>EMISION_REC_UNO</td>");
					htmlBuffer.append("<td>NRO_EXP2</td>");
					htmlBuffer.append("<td>REC_01_FECHA_NOTIFICADA</td>");
					htmlBuffer.append("<td>CONDICION</td>");
					htmlBuffer.append("<td>OBSERVACIONES</td>");
					htmlBuffer.append("<td>NOTIFICADOR</td>");
					htmlBuffer.append("<td>REC2_CAPTURA_EMISION</td>");
					htmlBuffer.append("<td>MEDIDA_CAUTELAR</td>");
					htmlBuffer.append("<td>SUSTENTO</td>");
					htmlBuffer.append("<td>FECHA_NOTIF_DE_REC_DOS</td>");
					htmlBuffer.append("<td>CONDICION</td>");
					htmlBuffer.append("<td>OBSERVACION</td>");
					htmlBuffer.append("<td>NOTIFICADOR</td>");
					htmlBuffer.append("<td>OBSERVACIONES</td>");
					htmlBuffer.append("<td>FECHA_PAGO_SUSPENSION</td>");
					
//					htmlBuffer.append("<td>DEUDA_ACTUAL</td>");
//					htmlBuffer.append("<td>MONTO_CANCELADO</td>");
//					htmlBuffer.append("<td>MONTO_PENDIENTE</td>");
					
					
					htmlBuffer.append("<td>FECHA ULT ACTUALIZACION</td>");
					htmlBuffer.append("<td>USUARIO ULT ACTUALIZACION</td>");
					htmlBuffer.append("<td>ESTADO_COLOR</td>");
					
					//htmlBuffer.append("<td>FECHA_PAGO</td>");
					
//					htmlBuffer.append("<td>FECHA_PAGO (MAY-NOV 2013)</td>");
//					htmlBuffer.append("<td>MONTO_CANCELADO (MAY-NOV 2013)</td>");
					
					htmlBuffer.append("<td>DEUDA ACTUAL</td>");
					htmlBuffer.append("<td>ESTADO ACTUAL</td>");
					
					htmlBuffer.append("</tr>");	
				  
					Iterator<CobranzaCoactiva> it2 = listaAll.iterator();
					while (it2.hasNext()) {
						CoactivaImpuestoVehicula2012 obj = (CoactivaImpuestoVehicula2012)it2.next();
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
							htmlBuffer.append("<td>").append(obj.getCampo36()==null?"":obj.getCampo36()).append("</td>");
							
//							htmlBuffer.append("<td>").append(obj.getDeudaTotal_actual()==null?"":obj.getDeudaTotal_actual()).append("</td>");
//							htmlBuffer.append("<td>").append(obj.getDeudaCancelada_actual() ==null?"":obj.getDeudaCancelada_actual()).append("</td>");
//							htmlBuffer.append("<td>").append(obj.getDeudaPendiente_actual()==null?"":obj.getDeudaPendiente_actual()).append("</td>");
							
							htmlBuffer.append("<td>").append(obj.getFechaRegistroUpd()==null?"":DateUtil.getTimestampToString(obj.getFechaRegistroUpd())).append("</td>");
							htmlBuffer.append("<td>").append(obj.getUsuarioIdUpd()==null?"":obj.getUsuarioIdUpd()).append("</td>");
							htmlBuffer.append("<td>").append(obj.getEstadocolor()==null?"":obj.getEstadocolor()).append("</td>");
							
//							htmlBuffer.append("<td>").append(obj.getFecha_pago()==null?"":obj.getFecha_pago()).append("</td>");
//							htmlBuffer.append("<td>").append(obj.getMonto_pago()==null?"":obj.getMonto_pago()).append("</td>");
							
							htmlBuffer.append("<td>").append(obj.getDeuda_actual()==null?"":obj.getDeuda_actual()).append("</td>");
							htmlBuffer.append("<td>").append(obj.getEstado_actual()==null?"":obj.getEstado_actual()).append("</td>");
							
						htmlBuffer.append("</tr>");	
				  }
					
				  htmlBuffer.append("</table>");
					//Set the filename
					//DateTime dt = new DateTime();
					//DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd_HHmmss");
			      String filename = "impuesto_vehicular_2012_"+System.currentTimeMillis() + ".xls";
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
