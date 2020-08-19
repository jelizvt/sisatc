package com.sat.sisat.controlycobranza.managed;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.richfaces.component.html.HtmlComboBox;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import com.sat.sisat.common.business.GeneralBoRemote;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.SATWEBParameterFactory;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.controlycobranza.business.ControlyCobranzaBoRemote;
import com.sat.sisat.controlycobranza.dto.FindCcLote;
import com.sat.sisat.controlycobranza.dto.FindCcLoteDetalleActo;
import com.sat.sisat.persistence.CrudServiceBean;
import com.sat.sisat.persistence.entity.CcLote;
import com.sat.sisat.persistence.entity.CcLoteActo;
import com.sat.sisat.persistence.entity.CcLoteActoPK;
import com.sat.sisat.persistence.entity.CcLoteConcepto;
import com.sat.sisat.persistence.entity.CcLoteConceptoPK;
import com.sat.sisat.persistence.entity.CcLoteTipoPersona;
import com.sat.sisat.persistence.entity.CcLoteTipoPersonaPK;
import com.sat.sisat.persona.business.PersonaBoRemote;
import com.sat.sisat.predial.dto.FindPersona;

@ManagedBean
@ViewScoped
public class RegistroLoteRsNoPecuniariaManaged extends BaseManaged {
	@EJB
	PersonaBoRemote personaBo;

	@EJB
	GeneralBoRemote generalBo;

	@EJB
	ControlyCobranzaBoRemote controlycobranzaBo;

	private Boolean isAccionRealizada = Boolean.FALSE;
	private FindCcLote findCcLoteItem = new FindCcLote();
	private CcLote ccLote = new CcLote();
	private CcLoteActo ccLoteActo = new CcLoteActo();
	private FindCcLote findCcLote = new FindCcLote();
	private Integer lote_id;
	private FindPersona findPersona;
	private List<FindPersona> lstPersonasInfractoresRS;
	private List<FindCcLoteDetalleActo> lstlotePreliminar=null;
	private String seleccionRs;
	private HtmlComboBox cmbgnRsUbicable;
	private Boolean esListar = Boolean.FALSE;


	@PostConstruct
	public void init() {
		try {
			FindCcLote findCcLoteItem = (FindCcLote) getSessionMap().get("findCcLoteItemRsNopec");
			if (findCcLoteItem != null) {
				setFindCcLoteItem(findCcLoteItem);
				setLote_id(findCcLoteItem.getLoteId());
				isAccionRealizada = Boolean.TRUE;
				mostrarValores();
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void guardar() {
		try {
			Integer lote_id = generalBo.ObtenerCorrelativoTabla("cc_lote", 1);
			Calendar c = Calendar.getInstance();
			// c.setTime(date);
			int periodo = c.get(Calendar.YEAR);
			ccLote.setLoteId(lote_id);
			ccLote.setTipoLoteId(Constante.TIPO_LOTE_COBRANZA_ID);
			ccLote.setAnnoLote(periodo);
			ccLote.setFechaLote(DateUtil.getCurrentDate());
			ccLote.setEstado(Constante.ESTADO_ACTIVO);
			ccLote.setFlagGeneracion(Constante.FLAG_GENERACION_PROGRAMADA);
			ccLote.setTipoLoteGeneracion(Constante.TIPO_LOTE_GENERACION_PRELIMINAR);
			ccLote = controlycobranzaBo.create(ccLote);
			isAccionRealizada = Boolean.TRUE;
			Integer lote_acto_id = generalBo.ObtenerCorrelativoTabla("cc_lote_acto", 1);
			CcLoteActoPK id = new CcLoteActoPK();
			id.setLoteActoId(lote_acto_id);
			id.setLoteId(ccLote.getLoteId());
			ccLoteActo.setId(id);
			ccLoteActo.setTipoActoId(Constante.TIPO_ACTO_RESOLUCION_SANCION_NP_ID);
			ccLoteActo.setEstado(Constante.ESTADO_ACTIVO);
			ccLoteActo = controlycobranzaBo.create(ccLoteActo);

			Integer lote_concepto_id = generalBo.ObtenerCorrelativoTabla("cc_lote_concepto", 1);
			CcLoteConcepto ccLoteConcepto = new CcLoteConcepto();
			ccLoteConcepto.setConceptoId(Constante.CONCEPTO_PAPELETA);
			CcLoteConceptoPK id1 = new CcLoteConceptoPK();
			id1.setLoteActoId(ccLoteActo.getId().getLoteActoId());
			id1.setLoteId(ccLote.getLoteId());
			id1.setLoteConceptoId(lote_concepto_id);
			ccLoteConcepto.setId(id1);
			ccLoteConcepto.setSubconceptoId(Constante.SUB_CONCEPTO_PAPELETA);
			ccLoteConcepto.setEstado(Constante.ESTADO_ACTIVO);
			controlycobranzaBo.create(ccLoteConcepto);

			Integer lote_tipo_persona_id = generalBo.ObtenerCorrelativoTabla("cc_lote_tipo_persona", 1);
			CcLoteTipoPersona ccLoteTipoPersona = new CcLoteTipoPersona();
			CcLoteTipoPersonaPK id4 = new CcLoteTipoPersonaPK();
			id4.setLoteId(ccLote.getLoteId());
			id4.setLoteTipoPersonaId(lote_tipo_persona_id);
			id4.setTipoPersonaId(Constante.TIPO_PERSONA_NATURAL_ID);
			ccLoteTipoPersona.setId(id4);
			ccLoteTipoPersona.setEstado(Constante.ESTADO_ACTIVO);
			controlycobranzaBo.create(ccLoteTipoPersona);
			findCcLoteItem.setLoteId(lote_id);
			getSessionMap().put("findCcLoteItem", findCcLoteItem);
			// }
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void motrarLotePreliminar() {
		try {
			if (findCcLoteItem != null) {
				if (getSeleccionRs().equals("RS Ubicables")) {
					lstlotePreliminar = controlycobranzaBo.getAllFindPreliminarRsNoPecuniariaUbic();
					ccLote.setFlagUbicables("2");
				}
				if (getSeleccionRs().equals("RS Inubicables S/N")) {
					lstlotePreliminar = controlycobranzaBo.getAllPreliminarRsNoPecSn();
					ccLote.setFlagUbicables("0");
				}
				if (getSeleccionRs().equals("RS Inubicables OTROS")) {
					lstlotePreliminar = controlycobranzaBo.getAllPreliminarRsNoPecOtros();
					ccLote.setFlagUbicables("1");
				}
				if (lstlotePreliminar == null || lstlotePreliminar.size() == 0) {
					addErrorMessage(getMsg("No hay RS por mostrar, verifique!!! ya fueron generadas en otro Lote."));
				}
				esListar=Boolean.TRUE;
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void loadSeleccion(ValueChangeEvent event) {
		try {
			HtmlComboBox combo = (HtmlComboBox) event.getComponent();
			String value = combo.getValue().toString();
			this.setSeleccionRs(value);
				
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void generacionValores() {
		try {
			if (findCcLoteItem != null && getSeleccionRs() != null) {
				if (lstlotePreliminar != null && lstlotePreliminar.size() != 0) {
					Calendar c = Calendar.getInstance();
					Integer periodo = c.get(Calendar.YEAR);
					Integer usuarioId = getSessionManaged().getUsuarioLogIn()
							.getUsuarioId();
					String terminal = getSessionManaged().getTerminalLogIn();
					if (getSeleccionRs().equals("RS Ubicables")) {
						controlycobranzaBo.registrarActoRsNoPecuniariasUbicables(findCcLoteItem.getLoteId(), periodo, usuarioId,terminal);
					}
					if (getSeleccionRs().equals("RS Inubicables S/N")) {
						controlycobranzaBo.registrarActoRsNoPecuniariasSinNumero(findCcLoteItem.getLoteId(), periodo, usuarioId,terminal);
					}
					if (getSeleccionRs().equals("RS Inubicables OTROS")) {
						controlycobranzaBo.registrarActoRsNoPecuniariasOtros(findCcLoteItem.getLoteId(), periodo, usuarioId,terminal);
					}
					// ccLote =
					// controlycobranzaBo.findCcLote(getFindCcLoteItem()
					// .getLoteId());
					// lote_id = ccLote.getLoteId();
					lstlotePreliminar = controlycobranzaBo.getAllFindCcLoteFinalRS(findCcLoteItem.getLoteId());
//					findCcLoteItem = controlycobranzaBo.getFindCcLote(findCcLoteItem.getLoteId());
					System.out.println("resultado de generar valores rs " + lstlotePreliminar.size());
				} else {
					addErrorMessage(getMsg("No hay RS por generar, verifique!!! ya fueron generadas en otro Lote."));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void mostrarValores() throws Exception {
		// try{
		if (findCcLoteItem != null) {
			lstlotePreliminar = controlycobranzaBo.getAllFindCcLoteFinalRS(findCcLoteItem.getLoteId());
			esListar=Boolean.TRUE;
			if (lstlotePreliminar != null && lstlotePreliminar.size() != 0) {
				findCcLoteItem.setFlag_generacion("2");
			} else {
				addErrorMessage(getMsg("No hay RS por mostrar, verifique!!! ya fueron generadas en otro Lote."));
			}
		}
	}

	public void previewRs() {
//		validarRangos() ;
	}
	
	public void generacionPreliminarRsNoPecuniarias(){
		java.sql.Connection connection=null;
		try {
			if(getSeleccionRs()!=null){
				String reporteName=null;
				if(getSeleccionRs().equals("RS Ubicables")){
					reporteName="preliminar_rs_nopecuniarias_ubicables.jasper";					
				}
				if(getSeleccionRs().equals("RS Inubicables S/N")){
					reporteName="preliminar_rs_nopecuniarias_sinnumero.jasper";
				}
				if(getSeleccionRs().equals("RS Inubicables OTROS")){
					reporteName="preliminar_rs_nopecuniarias_otros.jasper";
				}
				CrudServiceBean connj=CrudServiceBean.getInstance();
				connection=connj.connectJasper();
				HashMap<String, Object> parameters = new HashMap<String, Object>();
				JasperPrint jasperPrint = JasperFillManager.fillReport((SATWEBParameterFactory.getPathReporte()+reporteName),parameters,connection);
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				
				JRXlsExporter exporterXls = new JRXlsExporter();
				    exporterXls.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);  
				    exporterXls.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, true);  
				    exporterXls.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, true);  
				    exporterXls.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, false);  
				    exporterXls.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, true);  
				    exporterXls.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, true);  
				    exporterXls.setParameter(JRXlsExporterParameter.MAXIMUM_ROWS_PER_SHEET, 200000); 
				    exporterXls.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);  
				    exporterXls.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, System.currentTimeMillis() + "preliminar_rs_nopecuniarias"+getSeleccionRs()+".xls");  
				    exporterXls.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, output);  
				    exporterXls.exportReport();
	
				HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
				response.setContentType("application/vnd.ms-excel");  
			    response.setHeader("Content-Disposition", "attachment;filename=" + "preliminar_rs_nopecuniarias"+getSeleccionRs()+".xls");  
				response.setContentLength(output.size());
				ServletOutputStream servletOutputStream = response.getOutputStream();
				servletOutputStream.write(output.toByteArray(), 0, output.size());
				servletOutputStream.flush();
				servletOutputStream.close();
				FacesContext.getCurrentInstance().responseComplete();
			}else{
				WebMessages.messageInfo("Seleccione un Lote Adecuado");
			}
		} catch (Exception jre) {
			jre.printStackTrace();
		}finally{
	    	 try{
	    		 if(connection!=null){
	    			 connection.close();
	    			 connection=null;
	    		 }
	    	 }catch(Exception e){}
	     }
	}
	
	public void generacionFormatoRsNoPecuniarias() {
		try {
			if (findCcLoteItem != null) {
				java.sql.Connection connection = null;
				try {
					CrudServiceBean connj = CrudServiceBean.getInstance();
					connection = connj.connectJasper();
					String path_context = FacesContext.getCurrentInstance()
							.getExternalContext().getRealPath("/");
					String path_imagen = path_context
							+ "/sisat/reportes/imagen/";
					HashMap<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("p_lote_id", findCcLoteItem.getLoteId());
					parameters.put("ruta_imagen", path_imagen);
//					parameters
//							.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
					JasperPrint jasperPrint = JasperFillManager
							.fillReport(
									(SATWEBParameterFactory.getPathReporte() + "report_rs_nopecuniaria.jasper"),
									parameters, connection);
					ByteArrayOutputStream output = new ByteArrayOutputStream();
					JasperExportManager.exportReportToPdfStream(jasperPrint,
							output);
					HttpServletResponse response = (HttpServletResponse) FacesContext
							.getCurrentInstance().getExternalContext()
							.getResponse();
					response.setContentType("application/pdf");
					response.addHeader("Content-Disposition",
							"attachment;filename=rs_no_pecuniarias.pdf");
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
					}
				}
			} else {
				addErrorMessage(getMsg("No hay Resoluciones para descargar. Verifique!!!"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public String salir() {
		getSessionMap().remove("findCcLoteItemRsNopec");
		return sendRedirectPrincipal();
	}
	
	public void generacionResumenRs() {
		try {
			if (findCcLoteItem != null) {
				java.sql.Connection connection = null;
				try {
					CrudServiceBean connj = CrudServiceBean.getInstance();
					connection = connj.connectJasper();
					String path_context = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
//					String path_report = path_context + "/sisat/reportes/";
					String path_imagen = path_context + "/sisat/reportes/imagen/";
					HashMap<String, Object> parameters = new HashMap<String, Object>();
//					parameters.put("SUBREPORT_CONNECTION", connec);
					parameters.put("p_lote_id", findCcLoteItem.getLoteId());
					parameters.put("ruta_imagen", path_imagen);
					parameters.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
					JasperPrint jasperPrint = JasperFillManager
							.fillReport((SATWEBParameterFactory.getPathReporte() + "listado_valores_rs.jasper"),
									parameters,
									connection);
					ByteArrayOutputStream output = new ByteArrayOutputStream();
					JasperExportManager.exportReportToPdfStream(jasperPrint, output);
					HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance()
							.getExternalContext().getResponse();
					response.setContentType("application/pdf");
					response.addHeader("Content-Disposition", "attachment;filename=listado_resumen_rs.pdf");
					response.setContentLength(output.size());
					ServletOutputStream servletOutputStream = response.getOutputStream();
					servletOutputStream.write(output.toByteArray(), 0, output.size());
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
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public String sendRedirectPrincipal() {
		return "/sisat/principal.xhtml?faces-redirect=true";
	}

	public void regresar() {

	}

	public void sendRedirectPrincipalListener() {
		try {
			getExternalContext().redirect("principal.jsf");
		} catch (IOException ex) {
			// TODO : Controller exception
			System.out.println("Pagina no encontrada");
		}
	}
	
	public void exportarTablaExcel() {

		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("RS NO Pecuniaria");
		int rowIndex = 1;
		Row row1 = sheet.createRow(0);
		row1.createCell(0).setCellValue("Código Infractor");
		row1.createCell(1).setCellValue("DNI Infractor");
		row1.createCell(2).setCellValue("Datos Infractor");
		row1.createCell(3).setCellValue("Dirección Infractor");
		row1.createCell(4).setCellValue("Placa");
		row1.createCell(5).setCellValue("Nro. Papeleta");
		row1.createCell(6).setCellValue("Monto Infraccion");
		row1.createCell(7).setCellValue("Nro. RS");
//		row1.createCell(8).setCellValue("Tipo Sanción");
//		row1.createCell(9).setCellValue("Sanción No Pecuniaria");

		for (FindCcLoteDetalleActo data : lstlotePreliminar) {
			Row row = sheet.createRow(rowIndex++);
			int columnIndex = 0;

			row.createCell(columnIndex++).setCellValue(data.getPersonaId());
			row.createCell(columnIndex++).setCellValue(data.getNroDocumento());
			row.createCell(columnIndex++).setCellValue(data.getDescripcionPersona());
			row.createCell(columnIndex++).setCellValue(data.getDireccion());
			row.createCell(columnIndex++).setCellValue(data.getPlaca());
			row.createCell(columnIndex++).setCellValue(data.getNroPapeleta());
			row.createCell(columnIndex++).setCellValue(data.getMontoMulta().toString());
			row.createCell(columnIndex++).setCellValue(data.getNroActo()==null?"--":data.getNroActo());
		}
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		externalContext.responseReset();
		externalContext.setResponseContentType("application/vnd.ms-excel");
		externalContext.setResponseHeader("Content-Disposition",
				"attachment;filename=rs_no_pecuniaria.xls");
		try {
			workbook.write(externalContext.getResponseOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		context.responseComplete(); // Prevent JSF from performing navigation.
	}

	public Boolean getIsAccionRealizada() {
		return isAccionRealizada;
	}

	public void setIsAccionRealizada(Boolean isAccionRealizada) {
		this.isAccionRealizada = isAccionRealizada;
	}

	public FindCcLote getFindCcLoteItem() {
		return findCcLoteItem;
	}

	public void setFindCcLoteItem(FindCcLote findCcLoteItem) {
		this.findCcLoteItem = findCcLoteItem;
	}

	public List<FindCcLoteDetalleActo> getLstlotePreliminar() {
		return lstlotePreliminar;
	}

	public void setLstlotePreliminar(List<FindCcLoteDetalleActo> lstlotePreliminar) {
		this.lstlotePreliminar = lstlotePreliminar;
	}

	public CcLote getCcLote() {
		return ccLote;
	}

	public void setCcLote(CcLote ccLote) {
		this.ccLote = ccLote;
	}

	public CcLoteActo getCcLoteActo() {
		return ccLoteActo;
	}

	public void setCcLoteActo(CcLoteActo ccLoteActo) {
		this.ccLoteActo = ccLoteActo;
	}

	public FindCcLote getFindCcLote() {
		return findCcLote;
	}

	public void setFindCcLote(FindCcLote findCcLote) {
		this.findCcLote = findCcLote;
	}

	public Integer getLote_id() {
		return lote_id;
	}

	public void setLote_id(Integer lote_id) {
		this.lote_id = lote_id;
	}

	public FindPersona getFindPersona() {
		return findPersona;
	}

	public void setFindPersona(FindPersona findPersona) {
		this.findPersona = findPersona;
	}

	public List<FindPersona> getLstPersonasInfractoresRS() {
		return lstPersonasInfractoresRS;
	}

	public void setLstPersonasInfractoresRS(List<FindPersona> lstPersonasInfractoresRS) {
		this.lstPersonasInfractoresRS = lstPersonasInfractoresRS;
	}

	public HtmlComboBox getCmbgnRsUbicable() {
		return cmbgnRsUbicable;
	}

	public void setCmbgnRsUbicable(HtmlComboBox cmbgnRsUbicable) {
		this.cmbgnRsUbicable = cmbgnRsUbicable;
	}

	public String getSeleccionRs() {
		return seleccionRs;
	}

	public void setSeleccionRs(String seleccionRs) {
		this.seleccionRs = seleccionRs;
	}

	public Boolean getEsListar() {
		return esListar;
	}

	public void setEsListar(Boolean esListar) {
		this.esListar = esListar;
	}
}
