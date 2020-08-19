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
import com.sat.sisat.coactiva.entity.CoactivaImpuestoPredial2014;
import com.sat.sisat.coactiva.entity.CobranzaCoactiva;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.controlycobranza.business.ControlyCobranzaBoRemote;

@ManagedBean
@ViewScoped
public class ImpuestoPredial2014Managed extends BaseManaged {
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
				lista=controlycobranzaBo.getAllImpuestoPredial2014(personaId,nroValor);
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
			List<CobranzaCoactiva> listaAll=controlycobranzaBo.getAllImpuestoPredial2014();

			  StringBuffer htmlBuffer= new StringBuffer();
			  htmlBuffer.append("<table border='0' >");
			  htmlBuffer.append("<tr>");
				htmlBuffer.append("<td>ID</td>");
				htmlBuffer.append("<td>TIPO</td>");
				htmlBuffer.append("<td>N°</td>");
				htmlBuffer.append("<td>NRO_EXPEDIENTE</td>");
				htmlBuffer.append("<td>NRO_EXPEDIENTE_II</td>");
				htmlBuffer.append("<td>CARGO</td>");
				htmlBuffer.append("<td>CODIGO_NUEVO</td>");
				htmlBuffer.append("<td>CONTRIBUYENTE</td>");
				htmlBuffer.append("<td>PLACA</td>");
				htmlBuffer.append("<td>VALOR</td>");
				htmlBuffer.append("<td>NRO_VALOR</td>");
				htmlBuffer.append("<td>ANIO</td>");
				htmlBuffer.append("<td>TRIMESTRE</td>");
				htmlBuffer.append("<td>FECHA_EMISION_VALOR</td>");
				htmlBuffer.append("<td>DIRECCION</td>");
				
				htmlBuffer.append("<td>INSOLUTO</td>");
				htmlBuffer.append("<td>EMISION</td>");
				htmlBuffer.append("<td>REAJUSTE</td>");
				htmlBuffer.append("<td>INTERES</td>");
				htmlBuffer.append("<td>TOTAL</td>");

				htmlBuffer.append("<td>INSOLUTO_PARQ.</td>");
				htmlBuffer.append("<td>EMISION_PARQ.</td>");
				htmlBuffer.append("<td>REAJUSTE_PARQ.</td>");
				htmlBuffer.append("<td>INTERES_PARQ.</td>");
				htmlBuffer.append("<td>TOTAL_PARQ.</td>");
				
				htmlBuffer.append("<td>INSOLUTO_LIMP.</td>");
				htmlBuffer.append("<td>EMISION_LIMP.</td>");
				htmlBuffer.append("<td>REAJUSTE_LIMP.</td>");
				htmlBuffer.append("<td>INTERES_LIMP.</td>");
				htmlBuffer.append("<td>TOTAL_LIMP.</td>");
				
				htmlBuffer.append("<td>FECHA_PAGO</td>");
				htmlBuffer.append("<td>ESTADO_COLOR</td>");
				htmlBuffer.append("<td>FECHA_GENERACION_REC1</td>");
				htmlBuffer.append("<td>FECHA_EMISION_REC1</td>");
				htmlBuffer.append("<td>FECHA_NOTIFIC_REC1</td>");
				htmlBuffer.append("<td>MEDIDA_CAUTELAR_GENERACION</td>");
				htmlBuffer.append("<td>MEDIDA_CAUTELAR_NOTIFICACION</td>");
				htmlBuffer.append("<td>MEDIDA_CAUTELAR</td>");
				htmlBuffer.append("<td>COSTAS</td>");
				htmlBuffer.append("<td>SUSPENSION</td>");
				htmlBuffer.append("<td>OBSERVACIONES</td>");
				
				htmlBuffer.append("<td>FECHA_EMISION_REC2</td>");
				htmlBuffer.append("<td>FECHA_NOTIFIC_REC2</td>");
				htmlBuffer.append("<td>MEDIDA_CAUTELAR</td>");
				
				htmlBuffer.append("<td>VALORES_SEG.</td>");
				htmlBuffer.append("<td>VALORES_PARQ.</td>");
				htmlBuffer.append("<td>VALORES_LIMP.</td>");
				
//				htmlBuffer.append("<td>MONTO_CANCELADO</td>");
//				htmlBuffer.append("<td>MONTO PENDIENTE</td>");
								
				htmlBuffer.append("<td>FECHA ULT ACTUALIZACION</td>");
				htmlBuffer.append("<td>USUARIO ULT ACTUALIZACION</td>");
				
				htmlBuffer.append("<td>DEUDA ACTUAL</td>");
				htmlBuffer.append("<td>ESTADO ACTUAL</td>");
				
				
				htmlBuffer.append("<td>insoluto</td>");
				htmlBuffer.append("<td>emision</td>");
				htmlBuffer.append("<td>reajuste</td>");
				htmlBuffer.append("<td>interes</td>");
				htmlBuffer.append("<td>total</td>");
				
				htmlBuffer.append("<td>parq.insoluto</td>");
				htmlBuffer.append("<td>parq.emision</td>");
				htmlBuffer.append("<td>parq.reajuste</td>");
				htmlBuffer.append("<td>parq.interes</td>");
				htmlBuffer.append("<td>parq.total</td>");
				

				htmlBuffer.append("<td>limp.insoluto</td>");
				htmlBuffer.append("<td>limp.emision</td>");
				htmlBuffer.append("<td>limp.reajuste</td>");
				htmlBuffer.append("<td>limp.interes</td>");
				htmlBuffer.append("<td>limp.total</td>");
				

				htmlBuffer.append("</tr>");	
			  
				Iterator<CobranzaCoactiva> it2 = listaAll.iterator();
				while (it2.hasNext()) {
					CoactivaImpuestoPredial2014 obj = (CoactivaImpuestoPredial2014)it2.next();
					htmlBuffer.append("<tr>");
						
						htmlBuffer.append("<td>").append(obj.getId()==null?"":obj.getId()).append("</td>");
						htmlBuffer.append("<td>").append(obj.getTipo()==null?"":obj.getTipo()).append("</td>");
						htmlBuffer.append("<td>").append(obj.getCampo1()==null?"":obj.getCampo1()).append("</td>");
						htmlBuffer.append("<td>").append(obj.getCampo2()==null?"":obj.getCampo2()).append("</td>");
						htmlBuffer.append("<td>").append(obj.getCampo4()==null?"":obj.getCampo4()).append("</td>");
						htmlBuffer.append("<td>").append(obj.getCampo5()==null?"":obj.getCampo5()).append("</td>");
						htmlBuffer.append("<td>").append(obj.getCampo8()==null?"":obj.getCampo8()).append("</td>");
						htmlBuffer.append("<td>").append(obj.getCampo6()==null?"":obj.getCampo6()).append("</td>");
						htmlBuffer.append("<td>").append(obj.getCampo29()==null?"":obj.getCampo29()).append("</td>");//placa
						htmlBuffer.append("<td>").append(obj.getCampo30()==null?"":obj.getCampo30()).append("</td>");//valor
						htmlBuffer.append("<td>").append(obj.getCampo9()==null?"":obj.getCampo9()).append("</td>");//nrovalor
						htmlBuffer.append("<td>").append(obj.getCampo11()==null?"":obj.getCampo11()).append("</td>");//anio
						htmlBuffer.append("<td>").append(obj.getCampo18()==null?"":obj.getCampo18()).append("</td>");//trim
						htmlBuffer.append("<td>").append(obj.getCampo10()==null?"":obj.getCampo10()).append("</td>");
						htmlBuffer.append("<td>").append(obj.getCampo7()==null?"":obj.getCampo7()).append("</td>");//direccion
						
						//TOTAL PREDIAL-VEHICULAR-ARBITRIOS SEGURIDAD::
						
						htmlBuffer.append("<td>").append(obj.getCampo12()==null?"":obj.getCampo12()).append("</td>");//insoluto
						htmlBuffer.append("<td>").append(obj.getCampo13()==null?"":obj.getCampo13()).append("</td>");
						htmlBuffer.append("<td>").append(obj.getCampo14()==null?"":obj.getCampo14()).append("</td>");
						htmlBuffer.append("<td>").append(obj.getCampo15()==null?"":obj.getCampo15()).append("</td>");
						htmlBuffer.append("<td>").append(obj.getCampo16()==null?"":obj.getCampo16()).append("</td>");//total
						
						//ARBITRIOS PARQUES::
						
						htmlBuffer.append("<td>").append(obj.getInsoluto_parques()==null?"":obj.getInsoluto_parques()).append("</td>");
						htmlBuffer.append("<td>").append(obj.getEmision_parques()==null?"":obj.getEmision_parques()).append("</td>");
						htmlBuffer.append("<td>").append(obj.getReajuste_parques()==null?"":obj.getReajuste_parques()).append("</td>");
						htmlBuffer.append("<td>").append(obj.getInteres_parques()==null?"":obj.getInteres_parques()).append("</td>");
						htmlBuffer.append("<td>").append(obj.getTotal_parques()==null?"":obj.getTotal_parques()).append("</td>");
						
						//ARBITRIOS LIMPIEZA::
						
						htmlBuffer.append("<td>").append(obj.getInsoluto_limpieza()==null?"":obj.getInsoluto_limpieza()).append("</td>");
						htmlBuffer.append("<td>").append(obj.getEmision_limpieza()==null?"":obj.getEmision_limpieza()).append("</td>");
						htmlBuffer.append("<td>").append(obj.getReajuste_limpieza()==null?"":obj.getReajuste_limpieza()).append("</td>");
						htmlBuffer.append("<td>").append(obj.getInteres_limpieza()==null?"":obj.getInteres_limpieza()).append("</td>");
						htmlBuffer.append("<td>").append(obj.getTotal_limpieza()==null?"":obj.getTotal_limpieza()).append("</td>");
						
						
						htmlBuffer.append("<td>").append(obj.getCampo23()==null?"":obj.getCampo23()).append("</td>");//fecha_pago
						htmlBuffer.append("<td>").append(obj.getCampo17()==null?"":obj.getCampo17()).append("</td>");//estado
						htmlBuffer.append("<td>").append(obj.getCampo22()==null?"":obj.getCampo22()).append("</td>");//generacion rec1
						htmlBuffer.append("<td>").append(obj.getCampo19()==null?"":obj.getCampo19()).append("</td>");//emision
						htmlBuffer.append("<td>").append(obj.getCampo20()==null?"":obj.getCampo20()).append("</td>");//notif
						htmlBuffer.append("<td>").append(obj.getCampo24()==null?"":obj.getCampo24()).append("</td>");//medida caut. generac.
						htmlBuffer.append("<td>").append(obj.getCampo25()==null?"":obj.getCampo25()).append("</td>");//medida caut. notf.
						htmlBuffer.append("<td>").append(obj.getCampo34()==null?"":obj.getCampo34()).append("</td>");//medida cautelar(tipo)
						htmlBuffer.append("<td>").append(obj.getCampo26()==null?"":obj.getCampo26()).append("</td>");//costas
						htmlBuffer.append("<td>").append(obj.getCampo27()==null?"":obj.getCampo27()).append("</td>");//suspension
						htmlBuffer.append("<td>").append(obj.getCampo28()==null?"":obj.getCampo28()).append("</td>");//observ.
						
						htmlBuffer.append("<td>").append(obj.getCampo31()==null?"":obj.getCampo31()).append("</td>");
						htmlBuffer.append("<td>").append(obj.getCampo32()==null?"":obj.getCampo32()).append("</td>");
						htmlBuffer.append("<td>").append(obj.getCampo33()==null?"":obj.getCampo33()).append("</td>");
						
									
						//ARBITRIOS VALORES::
						htmlBuffer.append("<td>").append(obj.getValor_seguridad()==null?"":obj.getValor_seguridad()).append("</td>");
						htmlBuffer.append("<td>").append(obj.getValor_parques()==null?"":obj.getValor_parques()).append("</td>");
						htmlBuffer.append("<td>").append(obj.getValor_limpieza()==null?"":obj.getValor_limpieza()).append("</td>");
												
						
						
						
//						htmlBuffer.append("<td>").append(obj.getMonto_cancelado()==null?"":obj.getMonto_cancelado()).append("</td>");
//						htmlBuffer.append("<td>").append(obj.getMonto_pendiente()==null?"":obj.getMonto_pendiente()).append("</td>");
					
						htmlBuffer.append("<td>").append(obj.getFechaRegistroUpd()==null?"":DateUtil.getTimestampToString(obj.getFechaRegistroUpd())).append("</td>");
						htmlBuffer.append("<td>").append(obj.getUsuarioIdUpd()==null?"":obj.getUsuarioIdUpd()).append("</td>");
						
						htmlBuffer.append("<td>").append(obj.getDeuda_actual()==null?"":obj.getDeuda_actual()).append("</td>");
						htmlBuffer.append("<td>").append(obj.getEstado_actual()==null?"":obj.getEstado_actual()).append("</td>");
						
						
						htmlBuffer.append("<td>").append(obj.getInsoluto()==null?"":obj.getInsoluto()).append("</td>");
						htmlBuffer.append("<td>").append(obj.getEmision()==null?"":obj.getEmision()).append("</td>");
						htmlBuffer.append("<td>").append(obj.getReajuste()==null?"":obj.getReajuste()).append("</td>");
						htmlBuffer.append("<td>").append(obj.getInteres()==null?"":obj.getInteres()).append("</td>");
						htmlBuffer.append("<td>").append(obj.getTotalSEG()==null?"":obj.getTotalSEG()).append("</td>");
						
						
						htmlBuffer.append("<td>").append(obj.getInsolutoPJ()==null?"":obj.getInsolutoPJ()).append("</td>");
						htmlBuffer.append("<td>").append(obj.getEmisionPJ()==null?"":obj.getEmisionPJ()).append("</td>");
						htmlBuffer.append("<td>").append(obj.getReajustePJ()==null?"":obj.getReajustePJ()).append("</td>");
						htmlBuffer.append("<td>").append(obj.getInteresPJ()==null?"":obj.getInteresPJ()).append("</td>");
						htmlBuffer.append("<td>").append(obj.getTotalPJ()==null?"":obj.getTotalPJ()).append("</td>");
						
						
						htmlBuffer.append("<td>").append(obj.getInsolutoLP()==null?"":obj.getInsolutoLP()).append("</td>");
						htmlBuffer.append("<td>").append(obj.getEmisionLP()==null?"":obj.getEmisionLP()).append("</td>");
						htmlBuffer.append("<td>").append(obj.getReajusteLP()==null?"":obj.getReajusteLP()).append("</td>");
						htmlBuffer.append("<td>").append(obj.getInteresLP()==null?"":obj.getInteresLP()).append("</td>");
						htmlBuffer.append("<td>").append(obj.getTotalLP()==null?"":obj.getTotalLP()).append("</td>");
						
						
						
						
					
					htmlBuffer.append("</tr>");	
				}
			  htmlBuffer.append("</table>");
				//Set the filename
				//DateTime dt = new DateTime();
				//DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd_HHmmss");
		      String filename = "impuesto_predial_2014_"+System.currentTimeMillis() + ".xls";
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
