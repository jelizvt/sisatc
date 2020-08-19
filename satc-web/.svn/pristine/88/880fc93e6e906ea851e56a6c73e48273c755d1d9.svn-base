package com.sat.sisat.controlycobranza.managed;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.richfaces.component.html.HtmlComboBox;

import com.sat.sisat.cobranzacoactiva.business.CobranzaCoactivaBoRemote;
import com.sat.sisat.cobranzacoactiva.dto.FindCcLoteDetalleActoExp;
import com.sat.sisat.cobranzacoactiva.dto.InformeTransferidoDetalle;
import com.sat.sisat.common.business.GeneralBoRemote;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.SATWEBParameterFactory;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.controlycobranza.business.ControlyCobranzaBoRemote;
import com.sat.sisat.controlycobranza.dto.FindCcLote;
import com.sat.sisat.persistence.CrudServiceBean;
import com.sat.sisat.persistence.entity.CcLote;
import com.sat.sisat.persistence.entity.CcLoteActo;
import com.sat.sisat.persistence.entity.CcLoteActoPK;
import com.sat.sisat.persistence.entity.GnConcepto;

@ManagedBean
@ViewScoped
public class RegistroDeudaExigible extends BaseManaged {

	private static final long serialVersionUID = 1L;

	private static final Object SEPARATOR = null;

	@EJB
	CobranzaCoactivaBoRemote cobranzaCoactivaBo;

	@EJB
	ControlyCobranzaBoRemote controlycobranzaBo;

	@EJB
	GeneralBoRemote generalBo;

	private FindCcLote findCcLoteItem = new FindCcLote();
	private HtmlComboBox cmbgnConcepto;
	private HtmlComboBox cmbTipoActo;
	private HtmlComboBox cmbHtmlDexUbicable;
	private List<SelectItem> lstgnConcepto = new ArrayList<SelectItem>();
	private List<FindCcLoteDetalleActoExp> lstlotePreliminar = new ArrayList<FindCcLoteDetalleActoExp>();
	private FindCcLoteDetalleActoExp findDetalleDeudaExigItem = new FindCcLoteDetalleActoExp();
	private String valueConcepto;
	private String tipoActo;
	private Integer periodo;
	private BigDecimal montoMinimo;
	private Integer lote_id=Constante.RESULT_PENDING;
	private CcLote ccLote = new CcLote();
	private CcLoteActo ccLoteActo = new CcLoteActo();
	private HashMap<String, Integer> mapgnConcepto = new HashMap<String, Integer>();
	private Integer flagUbicable;
	private String motivoDescargo = "";

	private Boolean selTodos = Boolean.FALSE;
	private Integer motivoRetiroId;
	private String resenaMotivoRetiro;
	
	@PostConstruct
	public void init() {
		try {
			FindCcLote findCcLoteItem = (FindCcLote) getSessionMap().get("findCcLoteDeItem");
			if (findCcLoteItem != null) {
				setFindCcLoteItem(findCcLoteItem);
				setLote_id(findCcLoteItem.getLoteId());
				mostrarValores();
			}else{
				getFindCcLoteItem().setLoteId(Constante.RESULT_PENDING);
				getFindCcLoteItem().setFlag_generacion("1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		// this.viewcmbconcepto();
	}

	public void mostrarValores() throws Exception {
		// try{
		if (findCcLoteItem != null) {
			lstlotePreliminar = cobranzaCoactivaBo
					.getAllFindDetalleFinalDeudaExigible(findCcLoteItem.getLoteId());
		}
	}

	public void verPnlEliminarExigibilidad() {
		setMotivoDescargo("");
		setMotivoRetiroId(Constante.RESULT_PENDING);
		setResenaMotivoRetiro("");
	}

	public void eliminar_click() {
		try {
			if ((!motivoDescargo.equals("") || motivoDescargo != "")
					&& motivoDescargo.length() > 10) {
				if (findDetalleDeudaExigItem.getEstadoDeudaExigible().equals(
						"1")) {
					cobranzaCoactivaBo.eliminarDeudaExigible(
							findDetalleDeudaExigItem.getDeudaExigibleId(),
							findDetalleDeudaExigItem.getLoteId(),
							findDetalleDeudaExigItem.getActoId(),
							findDetalleDeudaExigItem.getApellidosNombres(),
							findDetalleDeudaExigItem.getNroActo(),
							getMotivoDescargo());
					lstlotePreliminar = cobranzaCoactivaBo
							.getAllFindDetalleFinalDeudaExigible(findCcLoteItem
									.getLoteId());
				} else {
					addErrorMessage(getMsg("No se Puede Eliminar. El valor esta en Cobranza Coactiva. Verifique!!!."));
				}
			} else {
				addErrorMessage(getMsg("Ingrese Correctamente el Motivo de Descargo!!!."));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String salir() {
		getSessionMap().remove("findCcLoteDeItem");
		return sendRedirectPrincipal();
	}

	public void viewcmbconcepto() {
		try {
			if (periodo == null) {
				Calendar c = Calendar.getInstance();
				setPeriodo(c.get(Calendar.YEAR));
			}
			Integer tipoActoId = null;
			if (cmbTipoActo.getValue().equals(null) == false) {
				setTipoActo(String.valueOf(this.cmbTipoActo.getValue()));

				if (getTipoActo().equals("RD")) {
					tipoActoId = 4;
				} else if (getTipoActo().equals("OP")) {
					tipoActoId = 3;
				} else if (getTipoActo().equals("RM")) {
					tipoActoId = 5;
				} else if (getTipoActo().equals("RS")) {
					tipoActoId = 8;
				}

				List<GnConcepto> lstCcTipoActo = controlycobranzaBo
						.getAllGnConcepto(tipoActoId);
				Iterator<GnConcepto> it1 = lstCcTipoActo.iterator();
				lstgnConcepto = new ArrayList<SelectItem>();

				while (it1.hasNext()) {
					GnConcepto obj = it1.next();
					lstgnConcepto.add(new SelectItem(obj.getDescripcion(),
							String.valueOf(obj.getConceptoId())));
					mapgnConcepto.put(obj.getDescripcion().trim(),
							obj.getConceptoId());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void generarValores() {
		try {
			if (validaDatos()) {
				if (findCcLoteItem != null) {
					if (lstlotePreliminar != null
							&& lstlotePreliminar.size() != 0) {
						Integer tipoActoId = null;
						setValueConcepto(String.valueOf(cmbgnConcepto
								.getValue()));
						if (getTipoActo().equals("RD")) {
							/** Rd Predial y Arbitrios */
							tipoActoId = 4;
							if (getValueConcepto().equals("Impuesto Predial")) {
								cobranzaCoactivaBo
										.registrarActoDeudaExigibleRdPredial(
												findCcLoteItem.getLoteId(),
												periodo, 1, tipoActoId,
												montoMinimo, flagUbicable);
							}else if (getValueConcepto().equals("Arbitrios Municipal"))  {
								cobranzaCoactivaBo
										.registrarActoDeudaExigibleRdArbitriosRm(
												findCcLoteItem.getLoteId(),
												periodo, 3, tipoActoId,
												montoMinimo, flagUbicable);
							}else if (getValueConcepto().equals("Impuesto Vehicular")) {
								cobranzaCoactivaBo
										.registrarActoDeudaExigibleRdVehicular(
												findCcLoteItem.getLoteId(),
												periodo, 2, tipoActoId,
												montoMinimo, flagUbicable);
							}
						} else if (getTipoActo().equals("OP")) {
							/** OP Predial y Vehicular */
							tipoActoId = 3;
							if (getValueConcepto().equals("Impuesto Predial")) {
								cobranzaCoactivaBo
										.registrarActoDeudaExigibleOPPredialVehicular(
												findCcLoteItem.getLoteId(),
												periodo, 1, tipoActoId,
												montoMinimo, flagUbicable);
							} else {
								cobranzaCoactivaBo
										.registrarActoDeudaExigibleOPPredialVehicular(
												findCcLoteItem.getLoteId(),
												periodo, 2, tipoActoId,
												montoMinimo, flagUbicable);
							}
						} else if (getTipoActo().equals("RM")) {
							tipoActoId = 5;
							if (getValueConcepto().equals("Multas")) {
								cobranzaCoactivaBo
										.registrarActoDeudaExigibleRdArbitriosRm(
												findCcLoteItem.getLoteId(),
												periodo, 12, tipoActoId,
												montoMinimo, flagUbicable);
							}
						} else if (getTipoActo().equals("RS")) {
							tipoActoId = 8;
							if (getValueConcepto().equals("Papeletas")) {
								cobranzaCoactivaBo
										.registrarActoDeudaExigibleRs(
												findCcLoteItem.getLoteId(),
												periodo, 4, tipoActoId,
												montoMinimo, flagUbicable);
							}
						}
						lstlotePreliminar = cobranzaCoactivaBo
								.getAllFindDetalleFinalDeudaExigible(findCcLoteItem
										.getLoteId());
						// findCcLoteItem =
						// controlycobranzaBo.getFindCcLote(findCcLoteItem.getLoteId());
						findCcLoteItem.setFlag_generacion("2");//Se genero el lote de exigibilidad
					} else {
						addErrorMessage(getMsg("No hay Deuda Exigible por generar, verifique!!! ya fue generada en otro Lote."));
					}
				} else {
					addErrorMessage(getMsg("Porfavor guarde primero el Lote!!!."));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}

	}

	public void guardarLote() {
		try {
			if (findCcLoteItem != null) {
				if (lstlotePreliminar.size() != 0 && lstlotePreliminar != null) {
					lote_id = generalBo.ObtenerCorrelativoTabla("cc_lote", 1);
					ccLote.setLoteId(lote_id);
					ccLote.setTipoLoteId(Constante.TIPO_LOTE_COBRANZA_ID);
					ccLote.setAnnoLote(getPeriodo());
					ccLote.setFechaLote(DateUtil.getCurrentDate());
					ccLote.setEstado(Constante.ESTADO_ACTIVO);
					ccLote.setFlagGeneracion(Constante.FLAG_GENERACION_PROGRAMADA);
					ccLote.setTipoLoteGeneracion(Constante.TIPO_LOTE_GENERACION_PRELIMINAR);
					ccLote = controlycobranzaBo.create(ccLote);
					// INSERT CC_LOTE_ACTO
					Integer lote_acto_id = generalBo.ObtenerCorrelativoTabla(
							"cc_lote_acto", 1);
					CcLoteActoPK id = new CcLoteActoPK();
					id.setLoteActoId(lote_acto_id);
					id.setLoteId(ccLote.getLoteId());
					ccLoteActo.setId(id);
					ccLoteActo
							.setTipoActoId(Constante.TIPO_ACTO_CONSTANCIA_EXIGIBILIDAD);
					ccLoteActo.setEstado(Constante.ESTADO_ACTIVO);
					ccLoteActo = controlycobranzaBo.create(ccLoteActo);
					findCcLoteItem.setLoteId(lote_id);
					// findCcLoteItem =
					// controlycobranzaBo.getFindCcLote(lote_id);
					
					agregarCartera(lote_id);
					
					getSessionMap().put("findCcLoteItem", findCcLoteItem);
				} else {
					addErrorMessage(getMsg("La Deuda Exigible, ya fue generadas en otro Lote. Verifique!!!"));
				}
			} else {
				addErrorMessage(getMsg("Porfavor Verifique Si hay Preliminar!!!."));
			}

		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public void valueChangeListenerSelTodos(ValueChangeEvent ev){
		String nv = ev.getNewValue().toString();
		if(nv.equals("true")){
			for(FindCcLoteDetalleActoExp detalle:lstlotePreliminar){
				detalle.setSelected(Boolean.TRUE);				
			}
		}else{
			for(FindCcLoteDetalleActoExp detalle:lstlotePreliminar){
				detalle.setSelected(Boolean.FALSE);				
			}
		}
	}
	
	public void agregarCartera(Integer loteId){
		try {
			StringBuffer listId=new StringBuffer();
			for(FindCcLoteDetalleActoExp detalle : lstlotePreliminar){
				listId.append(detalle.getActoId()).append(",");
			}
			if(listId.toString().length()>0){
				cobranzaCoactivaBo.registraCarteraExigible(listId.toString(), loteId, getSessionManaged().getUsuarioLogIn().getUsuarioId(),getSessionManaged().getTerminalLogIn());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void retirarCartera(){
		try {
			if(findDetalleDeudaExigItem!=null&&
					findDetalleDeudaExigItem.getActoId()!=null&&
					findDetalleDeudaExigItem.getActoId()>0){
			  if ((!resenaMotivoRetiro.equals("") || resenaMotivoRetiro != "")&& resenaMotivoRetiro.length() > 10) {
				cobranzaCoactivaBo.retiraCarteraExigible(findDetalleDeudaExigItem.getActoId(),motivoRetiroId,resenaMotivoRetiro,lote_id, getSessionManaged().getUsuarioLogIn().getUsuarioId(),getSessionManaged().getTerminalLogIn());
				findDetalleDeudaExigItem.setEstado(9);
				findDetalleDeudaExigItem.setMotivoRetiro(getMotivoRetiro(motivoRetiroId));
				findDetalleDeudaExigItem.setResenaMotivoRetiro(resenaMotivoRetiro);
			  }else{
					addErrorMessage(getMsg("Ingrese Correctamente el Motivo de Descargo!!!."));
			  }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String getMotivoRetiro(Integer motivoRetiroId){
		if(motivoRetiroId==1){
			return "Lote Diferente";
		}else if(motivoRetiroId==2){
			return "No existe Fisico";
		}else if(motivoRetiroId==3){
			return "Sin Notificar";
		}else if(motivoRetiroId==4){
			return "Otros";
		}
		return "";	
	}

	public void motrarLotePreliminar() {
		try {
			// lstlotePreliminar.clear();
			if (validaDatos()) {

				Integer tipoActoId = null;
				setValueConcepto(String.valueOf(cmbgnConcepto.getValue()));
				if (getTipoActo().equals("RD")) {
					/** Rd Predial y Arbitrios */
					tipoActoId = 4;
					if (getValueConcepto().equals("Impuesto Predial")) {
						lstlotePreliminar = cobranzaCoactivaBo
								.getAllDeudaExigibleRdPredial(tipoActoId, 1,
										periodo, montoMinimo, flagUbicable);
					} else if (getValueConcepto().equals("Arbitrios Municipal")) {
						lstlotePreliminar = cobranzaCoactivaBo
								.getAllDeudaExigibleRdArbitriosyRm(tipoActoId,
										3, periodo, montoMinimo, flagUbicable);
					}else if (getValueConcepto().equals("Impuesto Vehicular")) {
						lstlotePreliminar = cobranzaCoactivaBo
								.getAllDeudaExigibleRdVehicular(tipoActoId,
										2, periodo, montoMinimo, flagUbicable);
					}
				} else if (getTipoActo().equals("OP")) {
					/** OP Predial y Vehicular */
					tipoActoId = 3;
					if (getValueConcepto().equals("Impuesto Predial")) {
						lstlotePreliminar = cobranzaCoactivaBo
								.getAllDeudaExigibleOpPredialVehicular(
										tipoActoId, 1, periodo, montoMinimo,
										flagUbicable);
					} else {
						lstlotePreliminar = cobranzaCoactivaBo
								.getAllDeudaExigibleOpPredialVehicular(
										tipoActoId, 2, periodo, montoMinimo,
										flagUbicable);
					}
				} else if (getTipoActo().equals("RM")) {
					tipoActoId = 5;
					if (getValueConcepto().equals("Multas")) {
						lstlotePreliminar = cobranzaCoactivaBo
								.getAllDeudaExigibleRdArbitriosyRm(tipoActoId,
										12, periodo, montoMinimo, flagUbicable);
					}
				} else if (getTipoActo().equals("RS")) {
					tipoActoId = 8;
					if (getValueConcepto().equals("Papeletas")) {
						lstlotePreliminar = cobranzaCoactivaBo
								.getAllDeudaExigibleRs(tipoActoId, 4, periodo,
										montoMinimo, flagUbicable);
					}
				}
				if (lstlotePreliminar == null || lstlotePreliminar.size() == 0) {
					addErrorMessage(getMsg("No hay Deuda Exigible Preliminar, verifique!!! ya fue generada en otro Lote."));
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean validaDatos() throws Exception {
		try {

			if (tipoActo.equals("")) {
				addErrorMessage(getMsg("Seleccione un Valor Tributario"));
			} else if (valueConcepto.equals("")) {
				addErrorMessage("Seleccione un Concepto");

			} else if (periodo == null) {
				addErrorMessage(getMsg("Ingrese Periodo a Consultar"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		return true;
	}

	public void loadGnConceptoById(ValueChangeEvent event) {
		try {
			lstgnConcepto.clear();
			viewcmbconcepto();
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void loadSeleccion(ValueChangeEvent event) {
		try {
			HtmlComboBox combo = (HtmlComboBox) event.getComponent();
			String value = combo.getValue().toString();
			if (value.endsWith("Ubicables")) {
				setFlagUbicable(1);
			}
			if (value.endsWith("Inubicables")) {
				setFlagUbicable(2);
			}

		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public final void doExportSelectedDataToCSV() {
		final StringBuffer sb = new StringBuffer();
		for (FindCcLoteDetalleActoExp data : lstlotePreliminar) {
			sb.append(data.getPersonaId());
			sb.append(File.separator);
			sb.append(data.getApellidosNombres());
			sb.append(File.separator);
			sb.append(data.getDireccion());
			sb.append(File.separator);
			sb.append(data.getNroActo());
			sb.append(File.separator);
			// All your fields ...
			sb.append("\n");
		}
		byte[] csvData = null;
		// in case you need some specific charset :
		// here is an exemple with some standard utf-8
		try {
			csvData = sb.toString().getBytes("utf-8");
		} catch (final UnsupportedEncodingException e1) {
			// manage your encoding exception error exception here
		}
		final FacesContext context = FacesContext.getCurrentInstance();
		final HttpServletResponse response = (HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse();

		response.setHeader("Content-disposition",
				"attached; filename=\"consultaMasiva.csv\"");
		response.setContentType("application/force.download");

		try {
			response.getOutputStream().write(csvData);
			response.getOutputStream().flush();
			response.getOutputStream().close();
			context.responseComplete();
			// Telling the framework that the response has been completed.
			FacesContext.getCurrentInstance().responseComplete();
		} catch (final IOException e) {
			// mange another exception
		}
	}

	public void exportarTablaExcel() {

		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("Exportando a Excel");
		int rowIndex = 1;
		Row row1 = sheet.createRow(0);
		row1.createCell(0).setCellValue("PersonaId");
		row1.createCell(1).setCellValue("Apellidos y Nombres");
		row1.createCell(2).setCellValue("Direccion");
		row1.createCell(3).setCellValue("Nro. Acto");
		row1.createCell(4).setCellValue("Año");
		row1.createCell(5).setCellValue("Monto");
		row1.createCell(6).setCellValue("Fecha Notificacion");
		row1.createCell(7).setCellValue("Estado Deuda");
		row1.createCell(8).setCellValue("Observacion");
		row1.createCell(9).setCellValue("Estado registro");
		
		for (FindCcLoteDetalleActoExp data : lstlotePreliminar) {
			Row row = sheet.createRow(rowIndex++);
			int columnIndex = 0;

			row.createCell(columnIndex++).setCellValue(data.getPersonaId());
			row.createCell(columnIndex++).setCellValue(
					data.getApellidosNombres());
			row.createCell(columnIndex++).setCellValue(data.getDireccion());
			row.createCell(columnIndex++).setCellValue(data.getNroActo());
			row.createCell(columnIndex++).setCellValue(data.getAnnoDeuda());
			row.createCell(columnIndex++).setCellValue(
					data.getMontoDeuda().doubleValue());
			if (data.getFechaNotificacion() != null) {
				row.createCell(columnIndex++).setCellValue(
						DateUtil.convertDateToString(data
								.getFechaNotificacion()));
			} else {
				row.createCell(columnIndex++).setCellValue("--");
			}
			row.createCell(columnIndex++).setCellValue(data.getEstado_deuda());
			row.createCell(columnIndex++).setCellValue(data.getObservacion());
			if(data.getEstado()!=null){
				row.createCell(columnIndex++).setCellValue(data.getEstado()==9?"Retirado : "+data.getMotivoRetiro()+" "+data.getResenaMotivoRetiro():"");
			}
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

	public void imprimirConstanciasExibilidad() {
		try {
			if (findCcLoteItem != null) {
				java.sql.Connection connection = null;
				try {
					CrudServiceBean connj = CrudServiceBean.getInstance();
					connection = connj.connectJasper();
					String path_context = FacesContext.getCurrentInstance()
							.getExternalContext().getRealPath("/");
					// String path_report = path_context + "/sisat/reportes/";
					String path_imagen = path_context
							+ "/sisat/reportes/imagen/";
					HashMap<String, Object> parameters = new HashMap<String, Object>();
					Integer val = findCcLoteItem.getLoteId();
					parameters.put("p_lote_id", val);
					parameters.put("ruta_imagen", path_imagen);
					// parameters
					// .put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
					JasperPrint jasperPrint = JasperFillManager
							.fillReport(
									(SATWEBParameterFactory.getPathReporte() + "cc_constancias_exigibilidad.jasper"),
									parameters, connection);
					ByteArrayOutputStream output = new ByteArrayOutputStream();
					JasperExportManager.exportReportToPdfStream(jasperPrint,
							output);
					HttpServletResponse response = (HttpServletResponse) FacesContext
							.getCurrentInstance().getExternalContext()
							.getResponse();
					response.setContentType("application/pdf");
					response.addHeader("Content-Disposition",
							"attachment;filename=ConstanciasExigibilidadLote_"
									+ val + ".pdf");
					response.setContentLength(output.size());
					ServletOutputStream servletOutputStream = response
							.getOutputStream();
					servletOutputStream.write(output.toByteArray(), 0,
							output.size());
					servletOutputStream.flush();
					servletOutputStream.close();
					FacesContext.getCurrentInstance().responseComplete();
				} catch (Exception e) {
					e.printStackTrace();
					WebMessages.messageError(e.getMessage());
				} finally {
					try {
						if (connection != null) {
							connection.close();
							connection = null;
						}
					} catch (Exception e) {
						e.getStackTrace();
					}
				}
			} else {
				addErrorMessage(getMsg("No Hay Valores para Imprimir. Verifique!!!"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void imprimirDeudasExigiblesEliminadas() {
		try {
			if (findCcLoteItem != null) {
				java.sql.Connection connection = null;
				try {
					CrudServiceBean connj = CrudServiceBean.getInstance();
					connection = connj.connectJasper();
					String path_context = FacesContext.getCurrentInstance()
							.getExternalContext().getRealPath("/");
					// String path_report = path_context + "/sisat/reportes/";
					String path_imagen = path_context
							+ "/sisat/reportes/imagen/";
					HashMap<String, Object> parameters = new HashMap<String, Object>();
					Integer val = findCcLoteItem.getLoteId();
					parameters.put("p_lote_id", val);
					parameters.put("ruta_imagen", path_imagen);
					parameters
							.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
					JasperPrint jasperPrint = JasperFillManager
							.fillReport(
									(SATWEBParameterFactory.getPathReporte() + "cc_constancias_exi_eliminadas.jasper"),
									parameters, connection);
					ByteArrayOutputStream output = new ByteArrayOutputStream();
					JasperExportManager.exportReportToPdfStream(jasperPrint,
							output);
					HttpServletResponse response = (HttpServletResponse) FacesContext
							.getCurrentInstance().getExternalContext()
							.getResponse();
					response.setContentType("application/pdf");
					response.addHeader("Content-Disposition",
							"attachment;filename=DeudasExigibleEliminadasLote_"
									+ val + ".pdf");
					response.setContentLength(output.size());
					ServletOutputStream servletOutputStream = response
							.getOutputStream();
					servletOutputStream.write(output.toByteArray(), 0,
							output.size());
					servletOutputStream.flush();
					servletOutputStream.close();
					FacesContext.getCurrentInstance().responseComplete();
				} catch (Exception e) {
					e.printStackTrace();
					WebMessages.messageError(e.getMessage());
				} finally {
					try {
						if (connection != null) {
							connection.close();
							connection = null;
						}
					} catch (Exception e) {
						e.getStackTrace();
					}
				}
			} else {
				addErrorMessage(getMsg("No Hay Valores para Imprimir. Verifique!!!"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public HtmlComboBox getCmbgnConcepto() {
		return cmbgnConcepto;
	}

	public void setCmbgnConcepto(HtmlComboBox cmbgnConcepto) {
		this.cmbgnConcepto = cmbgnConcepto;
	}

	public List<SelectItem> getLstgnConcepto() {
		return lstgnConcepto;
	}

	public void setLstgnConcepto(List<SelectItem> lstgnConcepto) {
		this.lstgnConcepto = lstgnConcepto;
	}

	public String getValueConcepto() {
		return valueConcepto;
	}

	public void setValueConcepto(String valueConcepto) {
		this.valueConcepto = valueConcepto;
	}

	public String getTipoActo() {
		return tipoActo;
	}

	public void setTipoActo(String tipoActo) {
		this.tipoActo = tipoActo;
	}

	public HtmlComboBox getCmbTipoActo() {
		return cmbTipoActo;
	}

	public void setCmbTipoActo(HtmlComboBox cmbTipoActo) {
		this.cmbTipoActo = cmbTipoActo;
	}

	public Integer getPeriodo() {
		return periodo;
	}

	public void setMontoMinimo(BigDecimal montoMinimo) {
		this.montoMinimo = montoMinimo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}

	public List<FindCcLoteDetalleActoExp> getLstlotePreliminar() {
		return lstlotePreliminar;
	}

	public void setLstlotePreliminar(
			List<FindCcLoteDetalleActoExp> lstlotePreliminar) {
		this.lstlotePreliminar = lstlotePreliminar;
	}

	public Integer getLote_id() {
		return lote_id;
	}

	public void setLote_id(Integer lote_id) {
		this.lote_id = lote_id;
	}

	public HashMap<String, Integer> getMapgnConcepto() {
		return mapgnConcepto;
	}

	public void setMapgnConcepto(HashMap<String, Integer> mapgnConcepto) {
		this.mapgnConcepto = mapgnConcepto;
	}

	public FindCcLote getFindCcLoteItem() {
		return findCcLoteItem;
	}

	public void setFindCcLoteItem(FindCcLote findCcLoteItem) {
		this.findCcLoteItem = findCcLoteItem;
	}

	public FindCcLoteDetalleActoExp getFindDetalleDeudaExigItem() {
		return findDetalleDeudaExigItem;
	}

	public void setFindDetalleDeudaExigItem(
			FindCcLoteDetalleActoExp findDetalleDeudaExigItem) {
		this.findDetalleDeudaExigItem = findDetalleDeudaExigItem;
	}

	public BigDecimal getMontoMinimo() {
		return montoMinimo;
	}

	public HtmlComboBox getCmbHtmlDexUbicable() {
		return cmbHtmlDexUbicable;
	}

	public void setCmbHtmlDexUbicable(HtmlComboBox cmbHtmlDexUbicable) {
		this.cmbHtmlDexUbicable = cmbHtmlDexUbicable;
	}

	public Integer getFlagUbicable() {
		return flagUbicable;
	}

	public void setFlagUbicable(Integer flagUbicable) {
		this.flagUbicable = flagUbicable;
	}

	public String getMotivoDescargo() {
		return motivoDescargo;
	}

	public void setMotivoDescargo(String motivoDescargo) {
		this.motivoDescargo = motivoDescargo;
	}
	
	public Boolean getSelTodos() {
		return selTodos;
	}

	public void setSelTodos(Boolean selTodos) {
		this.selTodos = selTodos;
	}
	
	public Integer getMotivoRetiroId() {
		return motivoRetiroId;
	}

	public void setMotivoRetiroId(Integer motivoRetiroId) {
		this.motivoRetiroId = motivoRetiroId;
	}

	public String getResenaMotivoRetiro() {
		return resenaMotivoRetiro;
	}

	public void setResenaMotivoRetiro(String resenaMotivoRetiro) {
		this.resenaMotivoRetiro = resenaMotivoRetiro;
	}
}
