package com.sat.sisat.fiscalizacion.managed;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
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

import com.sat.sisat.common.business.GeneralBoRemote;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.SATWEBParameterFactory;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.controlycobranza.business.ControlyCobranzaBoRemote;
import com.sat.sisat.controlycobranza.dto.FindCcLote;
import com.sat.sisat.controlycobranza.dto.FindCcLoteDetalleActo;
import com.sat.sisat.determinacion.vehicular.business.DeterminacionVehicularBo;
import com.sat.sisat.determinacion.vehicular.business.DeterminacionVehicularBoRemote;
import com.sat.sisat.fiscalizacion.business.FiscalizacionBoRemote;
import com.sat.sisat.persistence.CrudServiceBean;
import com.sat.sisat.persistence.entity.CcLote;
import com.sat.sisat.persistence.entity.CcLoteActo;
import com.sat.sisat.persistence.entity.CcLoteActoPK;
import com.sat.sisat.persistence.entity.CcLoteConcepto;
import com.sat.sisat.persistence.entity.CcLoteConceptoPK;
import com.sat.sisat.persistence.entity.RpDjpredial;
import com.sat.sisat.persistence.entity.RvDjvehicular;
import com.sat.sisat.persistence.entity.RvFiscalizacionInspeccion;
import com.sat.sisat.persistence.entity.RvOmisosVehicular;

@ManagedBean
@ViewScoped
public class RegistroLoteRDVehicularManaged extends BaseManaged{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	FiscalizacionBoRemote ficalizacionBo;
	
	@EJB
	DeterminacionVehicularBoRemote determinacionVehicular;
	
	@EJB
	ControlyCobranzaBoRemote controlycobranzaBo;
	
	@EJB
	GeneralBoRemote generalBo;
	
	/** BÚSQUEDA DE LOTE VEHICULAR (CARTERA DE OMISOS): */
	private List<FindCcLote> cartera = new ArrayList<FindCcLote>();
	private Integer nroLoteasociado;

	/** ACTUALIZACIÓN DE LA DJ : */
//	private List<RvDjvehicular> djomisa = new ArrayList<RvDjvehicular>();
	private List<FindCcLoteDetalleActo> lstlotePreliminar=null;
	
	/** GENERACIÓN DE RDS */
	private CcLote ccLote=new CcLote();
	private CcLoteActo ccLoteActo = new CcLoteActo();
	private FindCcLote findCcLoteItem= new FindCcLote();
	private Integer lote_id;
	private Boolean isAccionRealizada=Boolean.FALSE;
	private Boolean esImpresionMasiva=Boolean.FALSE;
	
	public RegistroLoteRDVehicularManaged() throws Exception {}
	
	@PostConstruct
	public void init() throws Exception {
			try{
				
				cartera = ficalizacionBo.getAllLoteOmiso();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public void generaDeterminacionVehicular(){
		try{
			if (this.getNroLoteasociado()!=null && this.getNroLoteasociado()>0){
				ficalizacionBo.actualizarDjOmisaVehicular(getNroLoteasociado());
				//1.-listar solo con flag es_masiva y por lote
				List<RvDjvehicular> djomisa=ficalizacionBo.getAllDjOmiso(getNroLoteasociado());
				//2.-generar la determinacion x id dj
				for(int i=0;i<djomisa.size();i++){
					RvDjvehicular rpDjVehicular=djomisa.get(i);
					determinacionVehicular.generarDeterminacion(rpDjVehicular.getDjvehicularId(), getUser().getUsuarioId(), getUser().getTerminal());
				}
			}
			else{
				addErrorMessage("Seleccione un Lote Vehicular");;
			}
//			if(this.getMontoMinimo()!=null && this.getPeriodo()!=null 
//					&& this.getMontoMinimo()>=0 && this.getPeriodo()>0 ){
//				lstlotePreliminar=controlycobranzaBo.getAllFindCcLotePreliminarRDArbitrios(periodo, 0.0,estadoDireccionId);
//			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void mostrarLotePreliminar(){
		try{
			if (this.getNroLoteasociado()!=null && this.getNroLoteasociado()>0){
				lstlotePreliminar=controlycobranzaBo.getAllFindCcLotePreliminarRDVehicular(getNroLoteasociado());
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	

	public void guardar(){
	  try{
			Integer lote_id=generalBo.ObtenerCorrelativoTabla("cc_lote", 1);
			
		    ccLote.setLoteId(lote_id);
		    ccLote.setTipoLoteId(Constante.TIPO_LOTE_COBRANZA_ID);//--CONTROL DE LA DEUDA
		    ccLote.setAnnoLote(DateUtil.getAnioActual());
		   // ccLote.setMontoInicio(BigDecimal.valueOf(montoMinimo));
		    //ccLote.setMontoFin(1000000000000);
		    
		    ccLote.setFechaLote(DateUtil.getCurrentDate());
		    ccLote.setEstado(Constante.ESTADO_ACTIVO);
		    ccLote.setFlagGeneracion(Constante.FLAG_GENERACION_PROGRAMADA);
		    ccLote.setTipoLoteGeneracion(Constante.TIPO_LOTE_GENERACION_PRELIMINAR);
		    ccLote.setImpresion(getNroLoteasociado());//Indica el número de Lote Vehicular asociado.
		    ccLote= controlycobranzaBo.create(ccLote);	
			 	
			Integer lote_acto_id=generalBo.ObtenerCorrelativoTabla("cc_lote_acto", 1);
			CcLoteActoPK id= new CcLoteActoPK();
			id.setLoteActoId(lote_acto_id);
			id.setLoteId(ccLote.getLoteId());
			ccLoteActo.setId(id);
			ccLoteActo.setTipoActoId(Constante.TIPO_ACTO_RESOLUCION_DETERMINACION_ID);
			ccLoteActo.setEstado(Constante.ESTADO_ACTIVO);
			ccLoteActo = controlycobranzaBo.create(ccLoteActo);
			
			Integer lote_concepto_id=generalBo.ObtenerCorrelativoTabla("cc_lote_concepto", 1);
			CcLoteConcepto ccLoteConcepto= new CcLoteConcepto();
			ccLoteConcepto.setConceptoId(Constante.CONCEPTO_VEHICULAR);
			CcLoteConceptoPK id1 = new CcLoteConceptoPK();
			id1.setLoteActoId(ccLoteActo.getId().getLoteActoId());
			id1.setLoteId(ccLote.getLoteId());
			id1.setLoteConceptoId(lote_concepto_id);
			ccLoteConcepto.setId(id1);
			ccLoteConcepto.setSubconceptoId(20);
			ccLoteConcepto.setEstado(Constante.ESTADO_ACTIVO);
			controlycobranzaBo.create(ccLoteConcepto);
			
			findCcLoteItem=controlycobranzaBo.getFindCcLote(lote_id);
			getSessionMap().put("findCcLoteItem", findCcLoteItem);	
			
		 }catch(Exception e){
				e.printStackTrace();
				WebMessages.messageFatal(e);			
		 }
	}
	
	public void generacionValores(){
		try{
			//Correccion : validacion de registrar el lote 
			if(findCcLoteItem!=null && findCcLoteItem.getLoteId()!=null &&findCcLoteItem.getLoteId()>0){
				controlycobranzaBo.registrarActoRDVehicular(findCcLoteItem.getLoteId());
				
				ccLote.setLoteId(findCcLoteItem.getLoteId());
			    ccLote.setTipoLoteId(Constante.TIPO_LOTE_COBRANZA_ID);//--CONTROL DE LA DEUDA
			    ccLote.setAnnoLote(DateUtil.getAnioActual());//periodo?
			    //ccLote.setMontoInicio(BigDecimal.valueOf(montoMinimo));montoMinimo?
			    //ccLote.setMontoFin(1000000000000);
			    
			    ccLote.setFechaLote(DateUtil.getCurrentDate());
			    ccLote.setFlagGeneracion(Constante.FLAG_GENERACION_FINALIZADA);
			    ccLote.setTipoLoteGeneracion(Constante.TIPO_LOTE_GENERACION_FINAL);
			    ccLote= controlycobranzaBo.udpate(ccLote);	
			    
				ccLote=controlycobranzaBo.findCcLote(getFindCcLoteItem().getLoteId());
		 		lote_id=ccLote.getLoteId();
		 		
				lstlotePreliminar=controlycobranzaBo.getAllFindCcLoteFinalRDVehicular(findCcLoteItem.getLoteId());
				findCcLoteItem=controlycobranzaBo.getFindCcLote(findCcLoteItem.getLoteId());
				
				setIsAccionRealizada(Boolean.TRUE);
				setEsImpresionMasiva(Boolean.TRUE);
			}else{
				WebMessages.messageInfo("Registre el lote de RD de Vehicular a generar");	
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public String salir(){
  		getSessionMap().remove("findCcLoteItem");
		return sendRedirectPrincipal();
  	}
	
	public void generacionPreliminarVehicular() {
		if (!lstlotePreliminar.equals("null")) {
			Workbook workbook = new HSSFWorkbook();
			Sheet sheet = workbook.createSheet("Rep.RD");
			int rowIndex = 1;
			Row row1 = sheet.createRow(0);
			row1.createCell(0).setCellValue("PersonaId");
			row1.createCell(1).setCellValue("Apellidos y Nombres");
			row1.createCell(2).setCellValue("Doc. Identidad");
			row1.createCell(3).setCellValue("Dirección");
			row1.createCell(4).setCellValue("Sector");
			row1.createCell(5).setCellValue("Tipo Via");
			row1.createCell(6).setCellValue("Via");
			row1.createCell(7).setCellValue("Numero");
			row1.createCell(8).setCellValue("Insoluto");
			row1.createCell(9).setCellValue("Emisión");
			row1.createCell(10).setCellValue("Reajuste");
			row1.createCell(11).setCellValue("Interés Capitalizado");
			row1.createCell(12).setCellValue("Total");
			row1.createCell(13).setCellValue("Nro. Valor");

			for (FindCcLoteDetalleActo data : lstlotePreliminar) {
				Row row = sheet.createRow(rowIndex++);
				int columnIndex = 0;

				row.createCell(columnIndex++).setCellValue(data.getPersonaId());
				row.createCell(columnIndex++).setCellValue(
						data.getDescripcionPersona());
				row.createCell(columnIndex++).setCellValue(
						data.getNroDocumento() == null ? "-" : data
								.getNroDocumento());
				row.createCell(columnIndex++).setCellValue(data.getDireccion());
				row.createCell(columnIndex++).setCellValue(data.getSector());
				row.createCell(columnIndex++).setCellValue(data.getTipoVia());
				row.createCell(columnIndex++).setCellValue(data.getVia());
				row.createCell(columnIndex++).setCellValue(data.getNumero());
				row.createCell(columnIndex++).setCellValue(
						data.getInsoluto().doubleValue());
				row.createCell(columnIndex++).setCellValue(
						data.getDerechoEmision().doubleValue());
				row.createCell(columnIndex++).setCellValue(
						data.getReajuste().doubleValue());
				row.createCell(columnIndex++).setCellValue(
						data.getInteresCapitalizado().doubleValue());
				row.createCell(columnIndex++).setCellValue(
						data.getDeudaTotal().doubleValue());
				row.createCell(columnIndex++).setCellValue(data.getNroActo());
			}
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext externalContext = context.getExternalContext();
			externalContext.responseReset();
			externalContext.setResponseContentType("application/vnd.ms-excel");
			externalContext.setResponseHeader("Content-Disposition",
					"attachment;filename=RD_Vehicular.xls");
			try {
				workbook.write(externalContext.getResponseOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			context.responseComplete(); // Prevent JSF from performing navigation.
		} else {
			addErrorMessage(getMsg("Muestre el Preliminar!!!"));
		}
	}
	
	public void generacionArchivos(){
		try{
			if(findCcLoteItem!=null){
				java.sql.Connection connection = null;
				try {
					CrudServiceBean connj = CrudServiceBean.getInstance();
					connection = connj.connectJasper();

					String path_context = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
					String path_report = path_context + "/sisat/reportes/";
					String path_imagen = path_context + "/sisat/reportes/imagen/";

					HashMap<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("p_lote_id", findCcLoteItem.getLoteId());
					parameters.put("ruta_imagen", path_imagen);
					//parameters.put("p_periodo", findCcLoteItem.getAnnoLote());
					parameters.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
		
					JasperPrint jasperPrint = JasperFillManager.fillReport(
							(SATWEBParameterFactory.getPathReporte() + "resolucion_determinacion_vehicular.jasper"),
							parameters, connection);

					ByteArrayOutputStream output = new ByteArrayOutputStream();
					JasperExportManager.exportReportToPdfStream(jasperPrint, output);
					
					HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
					response.setContentType("application/pdf");
					response.addHeader("Content-Disposition","attachment;filename=resolucion_determinacion_vehicular.pdf");
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
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	public void imprimirDjsPendientesXLS() {
		java.sql.Connection connection=null;
		try {
			CrudServiceBean connj=CrudServiceBean.getInstance();
			connection=connj.connectJasper();
			    
			HashMap<String, Object> parameters = new HashMap<String, Object>();

			parameters.put("lote_id", getNroLoteasociado());
			
			JasperPrint jasperPrint = JasperFillManager.fillReport((SATWEBParameterFactory.getPathReporte()+"reporte_inspeccion_rv_requerimiento_djs.jasper"),parameters,connection);
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			
			JRXlsExporter exporterXls = new JRXlsExporter();
			    exporterXls.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);  
			    exporterXls.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, true);  
			    exporterXls.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, true);  
			    exporterXls.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, false);  
			    exporterXls.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, true);  
			    exporterXls.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, true);  
			    exporterXls.setParameter(JRXlsExporterParameter.MAXIMUM_ROWS_PER_SHEET, 20000); 
			    exporterXls.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);  
			    exporterXls.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, System.currentTimeMillis() + "DJ_Pendientes_Omisas" + ".xls");  
			    exporterXls.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, output);  
			    exporterXls.exportReport();

			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			response.setContentType("application/vnd.ms-excel");  
		    response.setHeader("Content-Disposition", "attachment;filename=" + System.currentTimeMillis() + "DJ_Pendientes_Omisas.xls");  
			response.setContentLength(output.size());
			ServletOutputStream servletOutputStream = response.getOutputStream();
			servletOutputStream.write(output.toByteArray(), 0, output.size());
			servletOutputStream.flush();
			servletOutputStream.close();
			FacesContext.getCurrentInstance().responseComplete();
			
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
	
	
	public List<FindCcLote> getCartera() {
		return cartera;
	}

	public void setCartera(List<FindCcLote> cartera) {
		this.cartera = cartera;
	}

	public Integer getNroLoteasociado() {
		return nroLoteasociado;
	}

	public void setNroLoteasociado(Integer nroLoteasociado) {
		this.nroLoteasociado = nroLoteasociado;
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
	
	public FindCcLote getFindCcLoteItem() {
		return findCcLoteItem;
	}

	public void setFindCcLoteItem(FindCcLote findCcLoteItem) {
		this.findCcLoteItem = findCcLoteItem;
	}

	public Integer getLote_id() {
		return lote_id;
	}

	public void setLote_id(Integer lote_id) {
		this.lote_id = lote_id;
	}

	public Boolean getIsAccionRealizada() {
		return isAccionRealizada;
	}

	public void setIsAccionRealizada(Boolean isAccionRealizada) {
		this.isAccionRealizada = isAccionRealizada;
	}

	public Boolean getEsImpresionMasiva() {
		return esImpresionMasiva;
	}

	public void setEsImpresionMasiva(Boolean esImpresionMasiva) {
		this.esImpresionMasiva = esImpresionMasiva;
	}
}
