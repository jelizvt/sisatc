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

import com.sat.sisat.coactiva.entity.CoactivaImpuestoPredial2012;
import com.sat.sisat.coactiva.entity.CobranzaCoactiva;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.controlycobranza.business.ControlyCobranzaBoRemote;

@ManagedBean
@ViewScoped
public class ImpuestoPredial2012Managed extends BaseManaged {
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
				lista=controlycobranzaBo.getAllImpuestoPredial2012(personaId,nroValor);
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
	
	public void exportHtmlTableToExcel() throws IOException{       
		try{
			List<CobranzaCoactiva> listaAll=controlycobranzaBo.getAllImpuestoPredial2012();

			  StringBuffer htmlBuffer= new StringBuffer();
			  htmlBuffer.append("<table border='0' >");
			  htmlBuffer.append("<tr>");
				htmlBuffer.append("<td>ID</td>");
				htmlBuffer.append("<td>NRO_EXPEDIENTE</td>");
				htmlBuffer.append("<td>IMP_PREDIAL_ANYO</td>");
				htmlBuffer.append("<td>TIPO_VALOR</td>");
				htmlBuffer.append("<td>SERIE</td>");
				htmlBuffer.append("<td>NRO_VALOR</td>");
				htmlBuffer.append("<td>CODIGO_ANTIGUO</td>");
				htmlBuffer.append("<td>CODIGO_NUEVO</td>");
				htmlBuffer.append("<td>CONTRIBUYENTE</td>");
				htmlBuffer.append("<td>DIRECCION</td>");
				htmlBuffer.append("<td>NRO_VIA</td>");
				htmlBuffer.append("<td>BARRIO</td>");
				htmlBuffer.append("<td>TIPO_DOCUMENTO</td>");
				htmlBuffer.append("<td>NRO_DOCUMENTO</td>");
				htmlBuffer.append("<td>INSOLUTO</td>");
				htmlBuffer.append("<td>INTERES</td>");
				htmlBuffer.append("<td>REAJUSTE</td>");
				htmlBuffer.append("<td>EMISION</td>");
				htmlBuffer.append("<td>TOTAL</td>");
				htmlBuffer.append("<td>EMISION_REC_UNO_FECHA</td>");
				htmlBuffer.append("<td>REC_UNO_FECHA_NOTIFICADA</td>");
				htmlBuffer.append("<td>FECHA_EMISION_CARTA_HAYME</td>");
				htmlBuffer.append("<td>REC_DOS_EMISION_Individual_AcumulaciON</td>");
				htmlBuffer.append("<td>MEDIDA_CAUTELAR_TIPO</td>");
				htmlBuffer.append("<td>MOTIVO_SUSTENTO_MC</td>");
				htmlBuffer.append("<td>REC_DOS_FECHA_VARIACION</td>");
				htmlBuffer.append("<td>NUEVA_MEDIDA_CAUTELAR_VARIACION</td>");
				htmlBuffer.append("<td>INFORMACION_TERCEROS_FECHA_REC_EMITIDA</td>");
				htmlBuffer.append("<td>TOMA_DICHO</td>");
				htmlBuffer.append("<td>FECHA_REC_INFOR_TERCEROS</td>");
				htmlBuffer.append("<td>NRO_DOCUM_QUE_INGRESA</td>");
				htmlBuffer.append("<td>CHEQUE_PE_FICHA_TOMO_OTRO</td>");
				htmlBuffer.append("<td>BANCO_ENTIDAD_RETIENE</td>");
				htmlBuffer.append("<td>DIRECCION_PREDIO_EMBARGADO</td>");
				htmlBuffer.append("<td>FECHA_ORDENA_DESEMBOLSO</td>");
				
				htmlBuffer.append("<td>MONTO_RETENIDO_SOLICITA_DESEMBOLSO</td>");
				htmlBuffer.append("<td>QUIEN_REALIZA_DESEMBOLSO_RESOLUCION</td>");
				htmlBuffer.append("<td>FECHA_LLEGA_CHEQUE</td>");
				htmlBuffer.append("<td>MONTO_DESEMBOLSADO</td>");
				
				htmlBuffer.append("<td>FECHA_IMPUT_CHEQUE</td>");
				htmlBuffer.append("<td>PAGOS_2013_2012</td>");
				htmlBuffer.append("<td>FECHA_PAGO</td>");
				htmlBuffer.append("<td>PAGOS_CUENTA</td>");
				
				htmlBuffer.append("<td>FECHA_PAGO_CUENTA</td>");
				htmlBuffer.append("<td>OBSERVACIONES</td>");
				htmlBuffer.append("<td>SUSPENSION_REC_FECHA</td>");
				htmlBuffer.append("<td>CARGO_NOTIFICACION</td>");
				htmlBuffer.append("<td>MONTO_ACTUAL</td>");
				
//				htmlBuffer.append("<td>DEUDA_ACTUAL</td>");
//				htmlBuffer.append("<td>MONTO_CANCELADO</td>");
//				htmlBuffer.append("<td>MONTO_PENDIENTE</td>");
				
				htmlBuffer.append("<td>FECHA ULT ACTUALIZACION</td>");
				htmlBuffer.append("<td>USUARIO ULT ACTUALIZACION</td>");
				htmlBuffer.append("<td>ESTADO_COLOR</td>");
				//htmlBuffer.append("<td>FECHA_PAGO</td>");
				
//				htmlBuffer.append("<td>FECHA_PAGO (MAY-NOV 2013)</td>");
//				htmlBuffer.append("<td>MONTO_CANCELADO (MAY-NOV 2013)</td>");
				
				htmlBuffer.append("<td>DEUDA ACTUAL</td>");
				htmlBuffer.append("<td>ESTADO ACTUAL</td>");
				
				htmlBuffer.append("</tr>");	
			  
				Iterator<CobranzaCoactiva> it2 = listaAll.iterator();
				while (it2.hasNext()) {
					CoactivaImpuestoPredial2012 obj = (CoactivaImpuestoPredial2012)it2.next();
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
						htmlBuffer.append("<td>").append(obj.getCampo37()==null?"":obj.getCampo37()).append("</td>");
						htmlBuffer.append("<td>").append(obj.getCampo38()==null?"":obj.getCampo38()).append("</td>");
						htmlBuffer.append("<td>").append(obj.getCampo39()==null?"":obj.getCampo39()).append("</td>");
						htmlBuffer.append("<td>").append(obj.getCampo40()==null?"":obj.getCampo40()).append("</td>");
						htmlBuffer.append("<td>").append(obj.getCampo41()==null?"":obj.getCampo41()).append("</td>");
						htmlBuffer.append("<td>").append(obj.getCampo42()==null?"":obj.getCampo42()).append("</td>");
						htmlBuffer.append("<td>").append(obj.getCampo43()==null?"":obj.getCampo43()).append("</td>");
						htmlBuffer.append("<td>").append(obj.getCampo44()==null?"":obj.getCampo44()).append("</td>");
						htmlBuffer.append("<td>").append(obj.getCampo45()==null?"":obj.getCampo45()).append("</td>");
						htmlBuffer.append("<td>").append(obj.getCampo46()==null?"":obj.getCampo46()).append("</td>");
						htmlBuffer.append("<td>").append(obj.getCampo47()==null?"":obj.getCampo47()).append("</td>");
						
//						htmlBuffer.append("<td>").append(obj.getDeudaTotal_actual()==null?"":obj.getDeudaTotal_actual()).append("</td>");
//						htmlBuffer.append("<td>").append(obj.getDeudaCancelada_actual() ==null?"":obj.getDeudaCancelada_actual()).append("</td>");
//						htmlBuffer.append("<td>").append(obj.getDeudaPendiente_actual()==null?"":obj.getDeudaPendiente_actual()).append("</td>");
						
						htmlBuffer.append("<td>").append(obj.getFechaRegistroUpd()==null?"":DateUtil.getTimestampToString(obj.getFechaRegistroUpd())).append("</td>");
						htmlBuffer.append("<td>").append(obj.getUsuarioIdUpd()==null?"":obj.getUsuarioIdUpd()).append("</td>");
						
						htmlBuffer.append("<td>").append(obj.getEstadocolor()==null?"":obj.getEstadocolor()).append("</td>");
						
//						htmlBuffer.append("<td>").append(obj.getFecha_pago()==null?"":obj.getFecha_pago()).append("</td>");
//						htmlBuffer.append("<td>").append(obj.getMonto_pago()==null?"":obj.getMonto_pago()).append("</td>");
						
						htmlBuffer.append("<td>").append(obj.getDeuda_actual()==null?"":obj.getDeuda_actual()).append("</td>");
						htmlBuffer.append("<td>").append(obj.getEstado_actual()==null?"":obj.getEstado_actual()).append("</td>");
						
					htmlBuffer.append("</tr>");	
				}
			  htmlBuffer.append("</table>");
				//Set the filename
				//DateTime dt = new DateTime();
				//DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd_HHmmss");
		      String filename = "impuesto_predial_2012_"+System.currentTimeMillis() + ".xls";
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
