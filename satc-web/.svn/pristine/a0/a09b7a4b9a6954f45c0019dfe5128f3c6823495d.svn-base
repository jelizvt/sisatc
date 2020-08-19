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
import javax.faces.model.SelectItem;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.sat.sisat.caja.dto.CjPersona;
import com.sat.sisat.caja.dto.DeudaDTO;
import com.sat.sisat.cobranzacoactiva.business.CobranzaCoactivaBoRemote;
import com.sat.sisat.cobranzacoactiva.dto.FindCcLoteDetalleActoExp;
import com.sat.sisat.cobranzacoactiva.dto.InformeTransferido;
import com.sat.sisat.cobranzacoactiva.dto.InformeTransferidoDetalle;
import com.sat.sisat.common.business.GeneralBoRemote;
import com.sat.sisat.common.dto.GenericDTO;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.FacesUtil;
import com.sat.sisat.controlycobranza.business.ControlyCobranzaBoRemote;
import com.sat.sisat.controlycobranza.dto.FindCcLote;

@ManagedBean
@ViewScoped
public class RecepcionValoresManaged extends BaseManaged {
	/**
	 * fox
	 */
	private static final long serialVersionUID = 1673161260001450282L;
	@EJB
	CobranzaCoactivaBoRemote cobranzaCoactivaBo;
	
	private InformeTransferido informeTransferido = new InformeTransferido();
	private List<InformeTransferidoDetalle> valoresRecibir = new ArrayList<InformeTransferidoDetalle>();
	private List<InformeTransferidoDetalle> valoresDevolver = new ArrayList<InformeTransferidoDetalle>();
	
	private Integer motivoDevolucionId;
	private String observacionDevolucion;
	private InformeTransferidoDetalle detalleDevolver=new InformeTransferidoDetalle(); 
	
	private List<GenericDTO> listaMotivoDevolucion = new ArrayList<GenericDTO>();
	
	@PostConstruct
	public void init() throws Exception {
		try {
			
			InformeTransferido informeTransferido = (InformeTransferido) getSessionMap().get("LoteTransferencia");
			if(informeTransferido != null){
				setInformeTransferido(informeTransferido);
				listarValoresRecibidos(informeTransferido.getLoteTransferenciaId());
				listarValoresDevueltos(informeTransferido.getLoteTransferenciaId());
			}
			
			listaMotivoDevolucion=cobranzaCoactivaBo.listarMotivoDevolucion();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void registraDevolucion(){
		try {
			if(detalleDevolver!=null &&detalleDevolver.getPersonaId()!=null){
				cobranzaCoactivaBo.registraDevolucionValor(detalleDevolver.getLoteTransferenciaDetalleId(), motivoDevolucionId, observacionDevolucion, getSessionManaged().getUsuarioLogIn().getUsuarioId(), getSessionManaged().getTerminalLogIn());
				listarValoresRecibidos(informeTransferido.getLoteTransferenciaId());
				listarValoresDevueltos(informeTransferido.getLoteTransferenciaId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public void recibirInforme(){
		try {
			cobranzaCoactivaBo.registraRecepcionInforme(informeTransferido.getLoteTransferenciaId(),getSessionManaged().getUsuarioLogIn().getUsuarioId(), getSessionManaged().getTerminalLogIn());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	private void listarValoresRecibidos(Integer loteTransferenciaId){
		try{
			valoresRecibir=cobranzaCoactivaBo.listarValoresRecibidos(loteTransferenciaId);
		}catch(Exception e){
			
		}
	}
	private void listarValoresDevueltos(Integer loteTransferenciaId){
		try{
			valoresDevolver=cobranzaCoactivaBo.listarValoresDevueltos(loteTransferenciaId);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void exportRecibirXls(){
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("Reporte");
		int rowIndex = 1;
		Row row1 = sheet.createRow(0);
		
		row1.createCell(0).setCellValue("item");
		row1.createCell(1).setCellValue("Tipo de valor");
		row1.createCell(2).setCellValue("Concepto");
		row1.createCell(3).setCellValue("Periodo");
		row1.createCell(4).setCellValue("Nro de Valor");
		row1.createCell(5).setCellValue("Código");
		row1.createCell(6).setCellValue("Contribuyente");
		row1.createCell(7).setCellValue("Dirección Fiscal");
		row1.createCell(8).setCellValue("Deuda");
		row1.createCell(9).setCellValue("Exigibilidad");
		row1.createCell(10).setCellValue("Estado");

		for (InformeTransferidoDetalle data : valoresRecibir) {
			Row row = sheet.createRow(rowIndex++);
			int columnIndex = 0;
			row.createCell(columnIndex++).setCellValue(rowIndex-1);
			row.createCell(columnIndex++).setCellValue(data.getTipoValor());
			row.createCell(columnIndex++).setCellValue(data.getConcepto());
			row.createCell(columnIndex++).setCellValue(data.getPeriodo());
			row.createCell(columnIndex++).setCellValue(data.getNroValor());
			row.createCell(columnIndex++).setCellValue(data.getPersonaId());
			row.createCell(columnIndex++).setCellValue(data.getApellidosNombresPersona());
			row.createCell(columnIndex++).setCellValue(data.getDireccionFiscal());
			row.createCell(columnIndex++).setCellValue(data.getMontoDeuda());
			row.createCell(columnIndex++).setCellValue(data.getExigibilidad());
			row.createCell(columnIndex++).setCellValue(data.getEstadoRecepcion());
		}
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		externalContext.responseReset();
		externalContext.setResponseContentType("application/vnd.ms-excel");
		externalContext.setResponseHeader("Content-Disposition",
				"attachment;filename=consulta_valores_recibir.xls");
		try {
			workbook.write(externalContext.getResponseOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		context.responseComplete(); // Prevent JSF from performing navigation.
	}
	
	public void exportDevolverXls() {
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("Reporte");
		int rowIndex = 1;
		Row row1 = sheet.createRow(0);
		
		row1.createCell(0).setCellValue("item");
		row1.createCell(1).setCellValue("Tipo de valor");
		row1.createCell(2).setCellValue("Nro de valor");
		row1.createCell(3).setCellValue("Código");
		row1.createCell(4).setCellValue("Contribuyente");
		row1.createCell(5).setCellValue("Deuda");
		row1.createCell(6).setCellValue("Exigibilidad");
		row1.createCell(7).setCellValue("Motivo");
		row1.createCell(8).setCellValue("Observación");

		for (InformeTransferidoDetalle data : valoresDevolver) {
			Row row = sheet.createRow(rowIndex++);
			int columnIndex = 0;
			row.createCell(columnIndex++).setCellValue(rowIndex-1);
			row.createCell(columnIndex++).setCellValue(data.getTipoValor());
			row.createCell(columnIndex++).setCellValue(data.getNroValor());
			row.createCell(columnIndex++).setCellValue(data.getPersonaId());
			row.createCell(columnIndex++).setCellValue(data.getApellidosNombresPersona());
			row.createCell(columnIndex++).setCellValue(String.valueOf(data.getMontoDeuda().doubleValue()));
			row.createCell(columnIndex++).setCellValue(data.getExigibilidad());
			row.createCell(columnIndex++).setCellValue(data.getMotivoDevolucion());
			row.createCell(columnIndex++).setCellValue(data.getObservacion());
		}
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		externalContext.responseReset();
		externalContext.setResponseContentType("application/vnd.ms-excel");
		externalContext.setResponseHeader("Content-Disposition",
				"attachment;filename=consulta_valores_devolver.xls");
		try {
			workbook.write(externalContext.getResponseOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		context.responseComplete(); // Prevent JSF from performing navigation.
	}
	

	public InformeTransferido getInformeTransferido() {
		return informeTransferido;
	}

	public void setInformeTransferido(InformeTransferido informeTransferido) {
		this.informeTransferido = informeTransferido;
	}

	public List<InformeTransferidoDetalle> getValoresRecibir() {
		return valoresRecibir;
	}

	public void setValoresRecibir(List<InformeTransferidoDetalle> valoresRecibir) {
		this.valoresRecibir = valoresRecibir;
	}

	public List<InformeTransferidoDetalle> getValoresDevolver() {
		return valoresDevolver;
	}

	public void setValoresDevolver(List<InformeTransferidoDetalle> valoresDevolver) {
		this.valoresDevolver = valoresDevolver;
	}

	public Integer getMotivoDevolucionId() {
		return motivoDevolucionId;
	}

	public void setMotivoDevolucionId(Integer motivoDevolucionId) {
		this.motivoDevolucionId = motivoDevolucionId;
	}

	public String getObservacionDevolucion() {
		return observacionDevolucion;
	}

	public void setObservacionDevolucion(String observacionDevolucion) {
		this.observacionDevolucion = observacionDevolucion;
	}

	public List<GenericDTO> getListaMotivoDevolucion() {
		return listaMotivoDevolucion;
	}

	public void setListaMotivoDevolucion(List<GenericDTO> listaMotivoDevolucion) {
		this.listaMotivoDevolucion = listaMotivoDevolucion;
	}

	public InformeTransferidoDetalle getDetalleDevolver() {
		return detalleDevolver;
	}

	public void setDetalleDevolver(InformeTransferidoDetalle detalleDevolver) {
		this.detalleDevolver = detalleDevolver;
	}
}
