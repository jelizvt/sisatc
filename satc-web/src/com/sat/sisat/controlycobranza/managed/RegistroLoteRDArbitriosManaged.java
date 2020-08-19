package com.sat.sisat.controlycobranza.managed;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
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

import com.sat.sisat.cobranzacoactiva.dto.FindCcLoteDetalleActoExp;
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
import com.sat.sisat.persistence.entity.GnSector;

@ManagedBean
@ViewScoped
public class RegistroLoteRDArbitriosManaged  extends BaseManaged {
	@EJB
	ControlyCobranzaBoRemote controlycobranzaBo;
	
	@EJB
	GeneralBoRemote generalBo;
	
	private Integer periodo;
	private Integer lote_id;
	private Double montoMinimo=new Double(0);
	private List<FindCcLoteDetalleActo> lstlotePreliminar=null;
	
	private String cmbValueSector;
	private List<SelectItem> lstSector;
	
	private CcLote ccLote=new CcLote();
	private CcLoteActo ccLoteActo = new CcLoteActo();
	private FindCcLote findCcLote= new FindCcLote();
	
	private FindCcLote findCcLoteItem= new FindCcLote();
	
	private HashMap<String, Integer> mapIGnSector=new HashMap<String, Integer>();
	
	private Boolean isAccionRealizada=Boolean.FALSE;
	
	private HtmlComboBox cmbEstadoDireccion;
	private Integer estadoDireccionId=new Integer(0);
	
	@PostConstruct
	public void init() {
		try{
			List<GnSector> lstGnSector=controlycobranzaBo.getAllGnSector();
			Iterator<GnSector> it2 = lstGnSector.iterator();
			lstSector = new ArrayList<SelectItem>();
			 
	        while (it2.hasNext()){
	        	GnSector obj = it2.next();
	        	lstSector.add(new SelectItem(obj.getDescripcion(),String.valueOf(obj.getSectorId())));
	        	mapIGnSector.put(obj.getDescripcion(),obj.getSectorId());
	        }
	        
	        FindCcLote findCcLoteItem= (FindCcLote)getSessionMap().get("findCcLoteItem");
	        if(findCcLoteItem!=null){
	        	setFindCcLoteItem(findCcLoteItem);
	        	setLote_id(findCcLoteItem.getLoteId());
	        	mostrarValores();
	        }
	        
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	public void motrarLotePreliminar(){
		try{
			if(this.getMontoMinimo()!=null && this.getPeriodo()!=null 
					&& this.getMontoMinimo()>=0 && this.getPeriodo()>0 ){
				lstlotePreliminar=controlycobranzaBo.getAllFindCcLotePreliminarRDArbitrios(periodo, 0.0,estadoDireccionId);
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
		    ccLote.setAnnoLote(periodo);
		    ccLote.setMontoInicio(BigDecimal.valueOf(montoMinimo));
		    //ccLote.setMontoFin(1000000000000);
		    
		    ccLote.setFechaLote(DateUtil.getCurrentDate());
		    ccLote.setEstado(Constante.ESTADO_ACTIVO);
		    ccLote.setFlagGeneracion(Constante.FLAG_GENERACION_PROGRAMADA);
		    ccLote.setTipoLoteGeneracion(Constante.TIPO_LOTE_GENERACION_PRELIMINAR);
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
			ccLoteConcepto.setConceptoId(Constante.CONCEPTO_ARBITRIO);
			CcLoteConceptoPK id1 = new CcLoteConceptoPK();
			id1.setLoteActoId(ccLoteActo.getId().getLoteActoId());
			id1.setLoteId(ccLote.getLoteId());
			id1.setLoteConceptoId(lote_concepto_id);
			ccLoteConcepto.setId(id1);
			ccLoteConcepto.setSubconceptoId(140);//--se genera la RD de todos los Arbitrios. Se utiliza el subconcepto referencial Limpieza P�blica 140 INACTIVO 
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
				controlycobranzaBo.registrarActoRDArbitrios(findCcLoteItem.getLoteId(),periodo,montoMinimo,estadoDireccionId);
				
				ccLote.setLoteId(findCcLoteItem.getLoteId());
			    ccLote.setTipoLoteId(Constante.TIPO_LOTE_COBRANZA_ID);//--CONTROL DE LA DEUDA
			    ccLote.setAnnoLote(periodo);
			    ccLote.setMontoInicio(BigDecimal.valueOf(montoMinimo));
			    //ccLote.setMontoFin(1000000000000);
			    
			    ccLote.setFechaLote(DateUtil.getCurrentDate());
			    ccLote.setFlagGeneracion(Constante.FLAG_GENERACION_FINALIZADA);
			    ccLote.setTipoLoteGeneracion(Constante.TIPO_LOTE_GENERACION_FINAL);
			    ccLote= controlycobranzaBo.udpate(ccLote);	
			    
				ccLote=controlycobranzaBo.findCcLote(getFindCcLoteItem().getLoteId());
		 		lote_id=ccLote.getLoteId();
		 		
				lstlotePreliminar=controlycobranzaBo.getAllFindCcLoteFinalRDArbitrios(findCcLoteItem.getLoteId());
				findCcLoteItem=controlycobranzaBo.getFindCcLote(findCcLoteItem.getLoteId());
				
				setIsAccionRealizada(Boolean.TRUE);
			}else{
				WebMessages.messageInfo("Registre el lote de RD de Arbitrios a generar");	
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public void mostrarValores(){
		try{
			if(findCcLoteItem!=null){
				lstlotePreliminar=controlycobranzaBo.getAllFindCcLoteFinalRDArbitrios(findCcLoteItem.getLoteId());
				if(lstlotePreliminar!=null&&lstlotePreliminar.size()>0){
					findCcLoteItem.setFlag_generacion("2");
					setPeriodo(findCcLoteItem.getAnnoLote());
					ccLote=controlycobranzaBo.findCcLote(getFindCcLoteItem().getLoteId());
					Integer ubicacionId=lstlotePreliminar.get(0).getUbicacionId();
					if(ubicacionId>0){
						setEstadoDireccionId(1);
					}else{
						setEstadoDireccionId(0);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public void generacionArchivos(){
		try{
			if(findCcLoteItem!=null){
				java.sql.Connection connection = null;
				try {
					CrudServiceBean connj = CrudServiceBean.getInstance();
					connection = connj.connectJasper();

					Integer sectorId=new Integer(0);
					if(getEstadoDireccionId()==1){
						if(cmbValueSector!=null && cmbValueSector.trim().length()>0){
							sectorId=mapIGnSector.get(cmbValueSector);	
						}
					}
					
					String path_context = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
					String path_report = path_context + "/sisat/reportes/";
					String path_imagen = path_context + "/sisat/reportes/imagen/";

					HashMap<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("p_lote_id", findCcLoteItem.getLoteId());
					parameters.put("ruta_imagen", path_imagen);
					parameters.put("p_periodo", findCcLoteItem.getAnnoLote());
					parameters.put("p_sector_id", sectorId);
					
					parameters.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
					
					JasperPrint jasperPrint = JasperFillManager.fillReport((SATWEBParameterFactory.getPathReporte() + "resolucion_determinacion_arbitrios2.jasper"),parameters, connection);

					ByteArrayOutputStream output = new ByteArrayOutputStream();
					JasperExportManager.exportReportToPdfStream(jasperPrint, output);
					
					HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
					response.setContentType("application/pdf");
					response.addHeader("Content-Disposition","attachment;filename=resolucion_determinacion_arbitrios.pdf");
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
					}catch (Exception e) {
							
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}

	public void generacionPreliminarArbitrios() {
		if (!lstlotePreliminar.equals("null")) {
			Workbook workbook = new HSSFWorkbook();
			Sheet sheet = workbook.createSheet("Exportando a Excel");
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
			row1.createCell(9).setCellValue("Interés Simple");
			row1.createCell(10).setCellValue("Interés Capitalizado");
			row1.createCell(11).setCellValue("Total");
			row1.createCell(12).setCellValue("Nro. Valor");

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
						data.getInteresSimple().doubleValue());
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
					"attachment;filename=exportando_a_excel.xls");
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
	
	public void generacionArchivosXml() {
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("Exportando a Excel");
		int rowIndex = 1;
		Row row1 = sheet.createRow(0);
		row1.createCell(0).setCellValue("PersonaId");
		row1.createCell(1).setCellValue("Apellidos y Nombres");
		row1.createCell(2).setCellValue("Doc. Identidad");
		row1.createCell(3).setCellValue("Dirección");
		row1.createCell(4).setCellValue("Sector");
		row1.createCell(5).setCellValue("Insoluto");
		row1.createCell(6).setCellValue("Interés");
		row1.createCell(7).setCellValue("Total");
		row1.createCell(8).setCellValue("Nro. Valor");

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
			row.createCell(columnIndex++).setCellValue(
					data.getInsoluto().doubleValue());
			row.createCell(columnIndex++).setCellValue(
					data.getInteresSimple().doubleValue()
							+ data.getInteresCapitalizado().doubleValue());
			row.createCell(columnIndex++).setCellValue(
					data.getDeudaTotal().doubleValue());
			row.createCell(columnIndex++).setCellValue(data.getNroActo());
			// if (data.getFechaNotificacion() != null) {
			// row.createCell(columnIndex++).setCellValue(
			// DateUtil.convertDateToString(data
			// .getFechaNotificacion()));
			// } else {
			// row.createCell(columnIndex++).setCellValue("-");
			// }

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
	
	
	public void loadSeleccion(ValueChangeEvent event) {
		try {
			HtmlComboBox combo = (HtmlComboBox) event.getComponent();
			String value = combo.getValue().toString();
			if(value.equals("RD Ubicables")){
				setEstadoDireccionId(1);
			}else if(value.equals("RD InUbicables")){
				setEstadoDireccionId(0);
			}else if(value.equals("RD InUbicables S/N")){
				setEstadoDireccionId(2);
			}
		}catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public String preliminar(){
	   try{
		   Object pk=lote_id;
		   ccLote=controlycobranzaBo.findCcLote(pk);
	   }catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	   return sendRedirectPrincipal();
   }
 
  	public String sendRedirectPrincipal() {
		return "/sisat/principal.xhtml?faces-redirect=true";
	}

  	public String salir(){
  		getSessionMap().remove("findCcLoteItem");
		return sendRedirectPrincipal();
  	}
  	public void sendRedirectPrincipalListener() {
		try {
			getExternalContext().redirect("principal.jsf");
		} catch (IOException ex) {
			// TODO : Controller exception
			System.out.println("Pagina no encontrada");
		}
	}
	
	public String getCmbValueSector() {
		return cmbValueSector;
	}

	public void setCmbValueSector(String cmbValueSector) {
		this.cmbValueSector = cmbValueSector;
	}

	public List<SelectItem> getLstSector() {
		return lstSector;
	}

	public void setLstSector(List<SelectItem> lstSector) {
		this.lstSector = lstSector;
	}
	
	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}

	public Integer getLote_id() {
		return lote_id;
	}

	public void setLote_id(Integer lote_id) {
		this.lote_id = lote_id;
	}

	public Double getMontoMinimo() {
		return montoMinimo;
	}

	public void setMontoMinimo(Double montoMinimo) {
		this.montoMinimo = montoMinimo;
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
	
	public FindCcLote getFindCcLoteItem() {
		return findCcLoteItem;
	}

	public void setFindCcLoteItem(FindCcLote findCcLoteItem) {
		this.findCcLoteItem = findCcLoteItem;
	}
	
	public Boolean getIsAccionRealizada() {
		return isAccionRealizada;
	}

	public void setIsAccionRealizada(Boolean isAccionRealizada) {
		this.isAccionRealizada = isAccionRealizada;
	}

	public HtmlComboBox getCmbEstadoDireccion() {
		return cmbEstadoDireccion;
	}

	public void setCmbEstadoDireccion(HtmlComboBox cmbEstadoDireccion) {
		this.cmbEstadoDireccion = cmbEstadoDireccion;
	}

	public Integer getEstadoDireccionId() {
		return estadoDireccionId;
	}

	public void setEstadoDireccionId(Integer estadoDireccionId) {
		this.estadoDireccionId = estadoDireccionId;
	}
}