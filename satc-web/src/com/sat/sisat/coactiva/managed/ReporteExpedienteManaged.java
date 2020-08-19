package com.sat.sisat.coactiva.managed;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import org.richfaces.component.html.HtmlComboBox;

import com.sat.sisat.cobranzacoactiva.business.CobranzaCoactivaBoRemote;
import com.sat.sisat.cobranzacoactiva.dto.FindCcLoteDetalleActoExp;
import com.sat.sisat.cobranzacoactiva.dto.FindCcRecTipo;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.predial.dto.FindRpDjPredial;

@ManagedBean
@ViewScoped
public class ReporteExpedienteManaged extends BaseManaged {

	/**
	 * 
	 */
	@EJB
	CobranzaCoactivaBoRemote cobranzaCoactivaBo;
	private static final long serialVersionUID = -7984906985082754713L;
	private HtmlComboBox cmbHtmlTipoDeuda;
	private String selecionTipoDeuda;
	private String selecionTipoRec;
	private HtmlComboBox cmbNoTipoRec;
	private List<SelectItem> listTipoRec = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapTipoRec = new HashMap<String, Integer>();
	private List<FindCcLoteDetalleActoExp> listReporteExpedientes = new ArrayList<FindCcLoteDetalleActoExp>();
	private Integer tipoRecId;
	private Integer tipoDeudaId;

	@PostConstruct
	public void init() {
		/* COMBOBOX:: TIPO DE REC */
		List<FindCcRecTipo> lstCcTipoRec;
		try {
			lstCcTipoRec = cobranzaCoactivaBo.getAllTipoRec(Boolean.FALSE);

			Iterator<FindCcRecTipo> it1 = lstCcTipoRec.iterator();
			listTipoRec = new ArrayList<SelectItem>();
			while (it1.hasNext()) {
				FindCcRecTipo obj = it1.next();
				listTipoRec.add(new SelectItem(obj.getDescripcionTipoRec(),
						String.valueOf(obj.getTipoRecId())));
				mapTipoRec.put(obj.getDescripcionTipoRec().trim(),
						obj.getTipoRecId());
			}
			/*- COMBOBOX:: TIPO DE REC -*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadSeleccion(ValueChangeEvent event) {
		try {
			HtmlComboBox combo = (HtmlComboBox) event.getComponent();
			String value = combo.getValue().toString();
			if (value != null) {
				if (value.equals("Tributaria")) {
					setTipoDeudaId(19);
				} else {
					setTipoDeudaId(20);
				}
				this.setSelecionTipoDeuda(value);
			}

		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void loadTipoRecById(ValueChangeEvent event) {
		try {
			HtmlComboBox combo = (HtmlComboBox) event.getComponent();
			String value = combo.getValue().toString();
			if (value != null) {
				if (value
						.equals("All Expedientes igual a : (Total RECs Inicio)")) {
					setTipoRecId(1);
				} else {
					tipoRecId = (Integer) mapTipoRec.get(value);
				}
				setSelecionTipoRec(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void consultarReporte() throws Exception {
		if (this.selecionTipoDeuda != null && !selecionTipoDeuda.equals("")
				&& this.selecionTipoRec != null && !selecionTipoRec.equals("")) {

			listReporteExpedientes = cobranzaCoactivaBo
					.getAllReporteExpedientes(tipoDeudaId, tipoRecId);
			listReporteExpedientes.size();

		} else {
			addErrorMessage(getMsg("Debe Seleccionar Correctamente los parametros. Verifique!!!"));
		}
	}

	public void exportarTablaExcel() {

		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("Exportando a Excel");
		int rowIndex = 1;
		Row row1 = sheet.createRow(0);
		row1.createCell(0).setCellValue("PersonaId");
		row1.createCell(1).setCellValue("Administrado");
		row1.createCell(2).setCellValue("Domicilio Fiscal");
		row1.createCell(3).setCellValue("Tipo Valor");
		row1.createCell(4).setCellValue("Concepto");
		row1.createCell(5).setCellValue("Placa");
		row1.createCell(6).setCellValue("Nro. Valor");
		row1.createCell(7).setCellValue("Nro. Expediente");
		row1.createCell(8).setCellValue("Estado Expediente");
		row1.createCell(9).setCellValue("Fecha Emisión");
		row1.createCell(10).setCellValue("Fecha Notificacion");
		row1.createCell(11).setCellValue("Deuda REC");
		row1.createCell(12).setCellValue("Deuda Saldos");
		row1.createCell(13).setCellValue("Estado Deuda");
		row1.createCell(14).setCellValue("Año Deuda");
		row1.createCell(15).setCellValue("Monto Pago");
		row1.createCell(16).setCellValue("Fecha Pago");
		row1.createCell(17).setCellValue("Fecha Cancelación");
		row1.createCell(18).setCellValue("Ultima REC. Emitida.");

		for (FindCcLoteDetalleActoExp data : listReporteExpedientes) {
			Row row = sheet.createRow(rowIndex++);
			int columnIndex = 0;

			row.createCell(columnIndex++).setCellValue(data.getPersonaId());
			row.createCell(columnIndex++).setCellValue(
					data.getApellidosNombres());
			row.createCell(columnIndex++).setCellValue(data.getDireccion());
			row.createCell(columnIndex++).setCellValue(data.getTipoActo());
			row.createCell(columnIndex++).setCellValue(data.getConcepto());
			row.createCell(columnIndex++).setCellValue(data.getPlaca());
			row.createCell(columnIndex++).setCellValue(
					data.getNroActo() == null ? "-" : data.getNroActo());
			row.createCell(columnIndex++).setCellValue(
					data.getNroExpediente() == null ? "-" : data
							.getNroExpediente());
			row.createCell(columnIndex++).setCellValue(
					data.getEstadoExpediente());
			if (data.getFechaEmision() != null) {
				row.createCell(columnIndex++).setCellValue(
						DateUtil.convertDateToString(data.getFechaEmision()));
			} else {
				row.createCell(columnIndex++).setCellValue("-");
			}
			if (data.getFechaNotificacion() != null) {
				row.createCell(columnIndex++).setCellValue(
						DateUtil.convertDateToString(data
								.getFechaNotificacion()));
			} else {
				row.createCell(columnIndex++).setCellValue("-");
			}
			row.createCell(columnIndex++).setCellValue(
					data.getDeuda().doubleValue());
			row.createCell(columnIndex++).setCellValue(
					data.getMontoDeuda().doubleValue());
			row.createCell(columnIndex++).setCellValue(data.getEstado_deuda());
			row.createCell(columnIndex++).setCellValue(data.getAnnoDeuda());
			row.createCell(columnIndex++).setCellValue(
					data.getDeudaTotalDcto().doubleValue());
			if (data.getFechaPago() != null) {
				row.createCell(columnIndex++).setCellValue(
						DateUtil.convertDateToString(data.getFechaPago()));
			} else {
				row.createCell(columnIndex++).setCellValue("-");
			}
			if (data.getFechaCancelacion() != null) {
				row.createCell(columnIndex++)
						.setCellValue(
								DateUtil.convertDateToString(data
										.getFechaCancelacion()));
			} else {
				row.createCell(columnIndex++).setCellValue("-");
			}
			row.createCell(columnIndex++).setCellValue(
					data.getUltimaRecEmitida() == null ? "-" : data
							.getUltimaRecEmitida());
		}
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		externalContext.responseReset();
		externalContext.setResponseContentType("application/vnd.ms-excel");
		externalContext.setResponseHeader("Content-Disposition",
				"attachment;filename=exportando_a_excel.xls");
		try {
			workbook.write(externalContext.getResponseOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		context.responseComplete(); // Prevent JSF from performing navigation.
	}

	public HtmlComboBox getCmbHtmlTipoDeuda() {
		return cmbHtmlTipoDeuda;
	}

	public void setCmbHtmlTipoDeuda(HtmlComboBox cmbHtmlTipoDeuda) {
		this.cmbHtmlTipoDeuda = cmbHtmlTipoDeuda;
	}

	public String getSelecionTipoDeuda() {
		return selecionTipoDeuda;
	}

	public void setSelecionTipoDeuda(String selecionTipoDeuda) {
		this.selecionTipoDeuda = selecionTipoDeuda;
	}

	public String getSelecionTipoRec() {
		return selecionTipoRec;
	}

	public void setSelecionTipoRec(String selecionTipoRec) {
		this.selecionTipoRec = selecionTipoRec;
	}

	public HtmlComboBox getCmbNoTipoRec() {
		return cmbNoTipoRec;
	}

	public void setCmbNoTipoRec(HtmlComboBox cmbNoTipoRec) {
		this.cmbNoTipoRec = cmbNoTipoRec;
	}

	public List<SelectItem> getListTipoRec() {
		return listTipoRec;
	}

	public void setListTipoRec(List<SelectItem> listTipoRec) {
		this.listTipoRec = listTipoRec;
	}

	public List<FindCcLoteDetalleActoExp> getListReporteExpedientes() {
		return listReporteExpedientes;
	}

	public void setListReporteExpedientes(
			List<FindCcLoteDetalleActoExp> listReporteExpedientes) {
		this.listReporteExpedientes = listReporteExpedientes;
	}

	public Integer getTipoDeudaId() {
		return tipoDeudaId;
	}

	public void setTipoDeudaId(Integer tipoDeudaId) {
		this.tipoDeudaId = tipoDeudaId;
	}

	public Integer getTipoRecId() {
		return tipoRecId;
	}

	public void setTipoRecId(Integer tipoRecId) {
		this.tipoRecId = tipoRecId;
	}

}
