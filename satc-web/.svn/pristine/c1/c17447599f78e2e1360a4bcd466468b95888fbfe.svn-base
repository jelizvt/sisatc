package com.sat.sisat.coactivav2.managed;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.sat.sisat.caja.dto.CjPersona;
import com.sat.sisat.cobranzacoactiva.business.CobranzaCoactivaBoRemote;
import com.sat.sisat.cobranzacoactiva.dto.CarteraExigibilidad;
import com.sat.sisat.cobranzacoactiva.dto.ExpedienteCoactivo;
import com.sat.sisat.cobranzacoactiva.dto.InformeTransferidoDetalle;
import com.sat.sisat.cobranzacoactiva.dto.ValorCartera;
import com.sat.sisat.common.dto.GenericDTO;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.persistence.entity.PaIncidencia;
import com.sat.sisat.persistence.entity.PaPapeleta;

@ManagedBean
@ViewScoped
public class CreaCarteraMedidaCautelarManaged extends BaseManaged {
	@EJB
	CobranzaCoactivaBoRemote cobranzaCoactivaBo;
	
	private Integer periodo;
	private String nroExpediente;
	private Integer contribuyenteId;
	private Double montoMinimo;
	private Double montoMaximo;

	private Integer tipoPersonaId;
	private Integer tipoDeuda;
	
	private Integer carteraId;
	private Integer coactivoId;
	
	private List<GenericDTO> listaTipoPersona = new ArrayList<GenericDTO>();
	
	private List<ExpedienteCoactivo> records = new ArrayList<ExpedienteCoactivo>();
	private List<ExpedienteCoactivo> recordsCartera = new ArrayList<ExpedienteCoactivo>();
	
	private Boolean selTodos = Boolean.FALSE;
	
	private Integer materiaId;
	
	@PostConstruct
	public void init() throws Exception {
		try {
			if(getSessionManaged().getCoPerfil().getMateriaId()>0){
				materiaId=getSessionManaged().getCoPerfil().getMateriaId();
			}
			
			listaTipoPersona = cobranzaCoactivaBo.listarTipoPersona();

			CarteraExigibilidad selected = (CarteraExigibilidad) getSessionMap().get("carteraMedidaCautelar");
			if (selected != null && selected.getCarteraId()!= Constante.RESULT_PENDING) {
				//buscar cartera existente
				carteraId=selected.getCarteraId();
				coactivoId=selected.getUsuarioCoactivoId();
				listarExpedienteCartera(carteraId);
			} else {
				carteraId=cobranzaCoactivaBo.registrarCarteraMedidaCautelar(Constante.RESULT_PENDING,getSessionManaged().getUsuarioLogIn().getUsuarioId(),Constante.TIPO_CARTERA_MEDIDA_CAUTELAR,materiaId,getSessionManaged().getTerminalLogIn(),getSessionManaged().getUsuarioLogIn().getUsuarioId());
				coactivoId=getSessionManaged().getUsuarioLogIn().getUsuarioId();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void buscar(){
		try{
			records=cobranzaCoactivaBo.seleccionaCarteraMedidaCautelar(periodo, tipoPersonaId, tipoDeuda, montoMinimo, montoMaximo, nroExpediente, contribuyenteId,carteraId,coactivoId);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void agregarCartera(){
		try {
			StringBuffer listExpedienteId=new StringBuffer();
			for(ExpedienteCoactivo detalle : records){
				if(detalle.isSelected()){
					listExpedienteId.append(detalle.getExpedienteId()).append(",");
				}
			}
			if(listExpedienteId.toString().length()>0){
				cobranzaCoactivaBo.registraProspectoMedidaCautelar(listExpedienteId.toString(),carteraId,getSessionManaged().getUsuarioLogIn().getUsuarioId(), getSessionManaged().getTerminalLogIn());
				buscar();
				listarExpedienteCartera(carteraId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void registraAsignacion(){
		try{
			carteraId=cobranzaCoactivaBo.registrarCarteraMedidaCautelar(carteraId,coactivoId,Constante.TIPO_CARTERA_MEDIDA_CAUTELAR,materiaId,getSessionManaged().getTerminalLogIn(),getSessionManaged().getUsuarioLogIn().getUsuarioId());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void listarExpedienteCartera(Integer carteraId){
		try{
			recordsCartera=cobranzaCoactivaBo.seleccionaProspectoMedidaCautelar(carteraId);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void valueChangeListenerSelTodos(ValueChangeEvent ev){
		String nv = ev.getNewValue().toString();
		if(nv.equals("true")){
			for(ExpedienteCoactivo detalle:records){
				detalle.setSelected(Boolean.TRUE);				
			}
		}else{
			for(ExpedienteCoactivo detalle:records){
				detalle.setSelected(Boolean.FALSE);				
			}
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
		row1.createCell(4).setCellValue("Direccion");
		row1.createCell(5).setCellValue("Deuda");
		
		for (ExpedienteCoactivo data : recordsCartera) {
			Row row = sheet.createRow(rowIndex++);
			int columnIndex = 0;
			row.createCell(columnIndex++).setCellValue(rowIndex-1);
			row.createCell(columnIndex++).setCellValue(data.getNroExpediente());
			row.createCell(columnIndex++).setCellValue(data.getPersonaId());
			row.createCell(columnIndex++).setCellValue(data.getApellidosNombresPersona());
			row.createCell(columnIndex++).setCellValue(data.getDireccion());
			row.createCell(columnIndex++).setCellValue(data.getTotalDeuda());
		}
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		externalContext.responseReset();
		externalContext.setResponseContentType("application/vnd.ms-excel");
		externalContext.setResponseHeader("Content-Disposition",
				"attachment;filename=cartera_expedientes_medida_cautelar.xls");
		try {
			workbook.write(externalContext.getResponseOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		context.responseComplete(); // Prevent JSF from performing navigation.
	}
	
	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}

	public Integer getContribuyenteId() {
		return contribuyenteId;
	}

	public void setContribuyenteId(Integer contribuyenteId) {
		this.contribuyenteId = contribuyenteId;
	}

	public List<GenericDTO> getListaTipoPersona() {
		return listaTipoPersona;
	}

	public void setListaTipoPersona(List<GenericDTO> listaTipoPersona) {
		this.listaTipoPersona = listaTipoPersona;
	}

	public Double getMontoMinimo() {
		return montoMinimo;
	}

	public void setMontoMinimo(Double montoMinimo) {
		this.montoMinimo = montoMinimo;
	}

	public Double getMontoMaximo() {
		return montoMaximo;
	}

	public void setMontoMaximo(Double montoMaximo) {
		this.montoMaximo = montoMaximo;
	}

	public Integer getTipoPersonaId() {
		return tipoPersonaId;
	}

	public void setTipoPersonaId(Integer tipoPersonaId) {
		this.tipoPersonaId = tipoPersonaId;
	}

	public Integer getTipoDeuda() {
		return tipoDeuda;
	}

	public void setTipoDeuda(Integer tipoDeuda) {
		this.tipoDeuda = tipoDeuda;
	}

	public Integer getCarteraId() {
		return carteraId;
	}

	public void setCarteraId(Integer carteraId) {
		this.carteraId = carteraId;
	}
	
	public Boolean getSelTodos() {
		return selTodos;
	}

	public void setSelTodos(Boolean selTodos) {
		this.selTodos = selTodos;
	}
	
	public String getNroExpediente() {
		return nroExpediente;
	}

	public void setNroExpediente(String nroExpediente) {
		this.nroExpediente = nroExpediente;
	}

	public List<ExpedienteCoactivo> getRecords() {
		return records;
	}

	public void setRecords(List<ExpedienteCoactivo> records) {
		this.records = records;
	}

	public List<ExpedienteCoactivo> getRecordsCartera() {
		return recordsCartera;
	}

	public void setRecordsCartera(List<ExpedienteCoactivo> recordsCartera) {
		this.recordsCartera = recordsCartera;
	}
	
	public Integer getCoactivoId() {
		return coactivoId;
	}

	public void setCoactivoId(Integer coactivoId) {
		this.coactivoId = coactivoId;
	}
	
	public Integer getMateriaId() {
		return materiaId;
	}

	public void setMateriaId(Integer materiaId) {
		this.materiaId = materiaId;
	}
}
