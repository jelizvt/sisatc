package com.sat.sisat.coactivav2.managed;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.sat.sisat.cobranzacoactiva.business.CobranzaCoactivaBoRemote;
import com.sat.sisat.cobranzacoactiva.dto.CarteraExigibilidad;
import com.sat.sisat.cobranzacoactiva.dto.ExpedienteCoactivo;
import com.sat.sisat.cobranzacoactiva.dto.InformeTransferido;
import com.sat.sisat.cobranzacoactiva.dto.InformeTransferidoDetalle;
import com.sat.sisat.cobranzacoactiva.dto.ValorCartera;
import com.sat.sisat.common.dto.GenericDTO;
import com.sat.sisat.common.util.BaseManaged;

@ManagedBean
@ViewScoped
public class AcumulaCarteraMedidaCautelarManaged extends BaseManaged {
	/**
	 * fox
	 */
	@EJB
	CobranzaCoactivaBoRemote cobranzaCoactivaBo;
	
	private List<ExpedienteCoactivo> expedienteCartera = new ArrayList<ExpedienteCoactivo>();
	private List<ExpedienteCoactivo> expedienteContrib = new ArrayList<ExpedienteCoactivo>();
	
	private CarteraExigibilidad carteraExigibilidad = new CarteraExigibilidad();
	private ExpedienteCoactivo selExpedienteAcumula=new ExpedienteCoactivo(); 

	private Boolean selTodos = Boolean.FALSE;
	
	@PostConstruct
	public void init() {
		try {
			CarteraExigibilidad selCartera = (CarteraExigibilidad) getSessionMap().get("carteraMedidaCautelar");
			if(selCartera != null){
				setCarteraExigibilidad(selCartera);
				consultarExpedientesCartera(selCartera.getCarteraId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void expedientesContribuyente() {
		try{
			expedienteContrib=cobranzaCoactivaBo.consultarExpedientesCartera(carteraExigibilidad.getCarteraId(), selExpedienteAcumula.getPersonaId());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void desAcumulaContribuyente(){
		try{
			cobranzaCoactivaBo.registraDesAcumulacion(carteraExigibilidad.getCarteraId(),selExpedienteAcumula.getPersonaId(),getSessionManaged().getTerminalLogIn(),getSessionManaged().getUsuarioLogIn().getUsuarioId());
			consultarExpedientesCartera(carteraExigibilidad.getCarteraId());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void retirarExpediente(){
		try{
			cobranzaCoactivaBo.retiraExpedienteCartera(carteraExigibilidad.getCarteraId(),selExpedienteAcumula.getExpedienteId(),getSessionManaged().getUsuarioLogIn().getUsuarioId(),getSessionManaged().getTerminalLogIn());
			consultarExpedientesCartera(carteraExigibilidad.getCarteraId());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void acumularTodo() {
		try {
			cobranzaCoactivaBo.registraAcumulacionExpedienteTodo(carteraExigibilidad.getCarteraId(),getSessionManaged().getUsuarioLogIn().getUsuarioId(),getSessionManaged().getTerminalLogIn());
			consultarExpedientesCartera(carteraExigibilidad.getCarteraId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void desAcumularTodo(){
		try {
			cobranzaCoactivaBo.registraDesAcumulacionExpedienteTodo(carteraExigibilidad.getCarteraId(),getSessionManaged().getUsuarioLogIn().getUsuarioId(),getSessionManaged().getTerminalLogIn());
			consultarExpedientesCartera(carteraExigibilidad.getCarteraId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void consultarExpedientesCartera(Integer carteraId) {
		try {
			expedienteCartera=cobranzaCoactivaBo.consultarExpedientesCartera(carteraId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void valueChangeListenerSelTodos(ValueChangeEvent ev){
		String nv = ev.getNewValue().toString();
		if(nv.equals("true")){
			for(ExpedienteCoactivo detalle:expedienteContrib){
				detalle.setSelected(Boolean.TRUE);				
			}
		}else{
			for(ExpedienteCoactivo detalle:expedienteContrib){
				detalle.setSelected(Boolean.FALSE);				
			}
		}
	}
	
	public void acumularValores(){
		try {
			StringBuffer listDetalleId=new StringBuffer();
			for(ExpedienteCoactivo detalle : expedienteContrib){
				if(detalle.isSelected()){
					listDetalleId.append(detalle.getExpedienteId()).append(",");
				}
			}
			if(listDetalleId.toString().length()>0){
				cobranzaCoactivaBo.registraAcumulacionExpedienteEnCartera(listDetalleId.toString(), carteraExigibilidad.getCarteraId(), selExpedienteAcumula.getPersonaId(), getSessionManaged().getTerminalLogIn(),getSessionManaged().getUsuarioLogIn().getUsuarioId());
				consultarExpedientesCartera(carteraExigibilidad.getCarteraId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void registraExpediente(){
		try{
			cobranzaCoactivaBo.registraExpedientes(carteraExigibilidad.getCarteraId(), getSessionManaged().getUsuarioLogIn().getUsuarioId(), getSessionManaged().getTerminalLogIn());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void descargarExcel(){
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("Exportando a Excel");
		int rowIndex = 1;
		Row row1 = sheet.createRow(0);
		
		row1.createCell(0).setCellValue("item");
		row1.createCell(1).setCellValue("Nro expediente");
		row1.createCell(2).setCellValue("Código");
		row1.createCell(3).setCellValue("Contribuyente");
		row1.createCell(4).setCellValue("Deuda");
		row1.createCell(5).setCellValue("Nro REC");
		row1.createCell(6).setCellValue("Periodo REC");
		row1.createCell(7).setCellValue("Situacion");
		
		for (ExpedienteCoactivo data : expedienteCartera) {
			Row row = sheet.createRow(rowIndex++);
			int columnIndex = 0;
			row.createCell(columnIndex++).setCellValue(rowIndex-1);
			row.createCell(columnIndex++).setCellValue(data.getNroExpediente());
			row.createCell(columnIndex++).setCellValue(data.getPersonaId());
			row.createCell(columnIndex++).setCellValue(data.getApellidosNombresPersona());
			row.createCell(columnIndex++).setCellValue(data.getTotalDeuda());
			row.createCell(columnIndex++).setCellValue(data.getNroRec());
			row.createCell(columnIndex++).setCellValue(data.getAnnoRec());
			row.createCell(columnIndex++).setCellValue(data.getSituacion());
		}
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		externalContext.responseReset();
		externalContext.setResponseContentType("application/vnd.ms-excel");
		externalContext.setResponseHeader("Content-Disposition",
				"attachment;filename=acumula_expedientes_medida_cautelar.xls");
		try {
			workbook.write(externalContext.getResponseOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		context.responseComplete(); // Prevent JSF from performing navigation.
	}
	
	public CarteraExigibilidad getCarteraExigibilidad() {
		return carteraExigibilidad;
	}

	public void setCarteraExigibilidad(CarteraExigibilidad carteraExigibilidad) {
		this.carteraExigibilidad = carteraExigibilidad;
	}
	
	public Boolean getSelTodos() {
		return selTodos;
	}

	public void setSelTodos(Boolean selTodos) {
		this.selTodos = selTodos;
	}

	public List<ExpedienteCoactivo> getExpedienteCartera() {
		return expedienteCartera;
	}

	public void setExpedienteCartera(List<ExpedienteCoactivo> expedienteCartera) {
		this.expedienteCartera = expedienteCartera;
	}

	public List<ExpedienteCoactivo> getExpedienteContrib() {
		return expedienteContrib;
	}

	public void setExpedienteContrib(List<ExpedienteCoactivo> expedienteContrib) {
		this.expedienteContrib = expedienteContrib;
	}

	public ExpedienteCoactivo getSelExpedienteAcumula() {
		return selExpedienteAcumula;
	}

	public void setSelExpedienteAcumula(ExpedienteCoactivo selExpedienteAcumula) {
		this.selExpedienteAcumula = selExpedienteAcumula;
	}
}
