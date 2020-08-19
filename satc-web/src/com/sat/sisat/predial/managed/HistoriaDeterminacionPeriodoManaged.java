package com.sat.sisat.predial.managed;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.richfaces.component.html.HtmlExtendedDataTable;
import org.richfaces.model.selection.SimpleSelection;

import com.sat.sisat.common.business.GeneralBoRemote;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.SATWEBParameterFactory;
import com.sat.sisat.common.util.Util;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.persistence.CrudServiceBean;
import com.sat.sisat.predial.business.CalculoPredialBoRemote;
import com.sat.sisat.predial.dto.DtDeterminacionPeriodoDTO;
import com.sat.sisat.predial.managed.calculo.DeterminacionPredial;
import com.sat.sisat.determinacion.vehicular.dto.DeudaValoresDTO;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.menus.business.MenuBoRemote;
import com.sat.sisat.menus.dto.SimpleMenuDTO;

@ManagedBean
@ViewScoped
public class HistoriaDeterminacionPeriodoManaged extends BaseManaged {

	@EJB
	CalculoPredialBoRemote calculoPredialBo;

	@EJB
	GeneralBoRemote generalBo;
	
	@EJB
	MenuBoRemote menuBo;

	private ArrayList<DtDeterminacionPeriodoDTO> records;

	private SimpleSelection selection = new SimpleSelection();
	private HtmlExtendedDataTable table;
	private int currentRow;
	private DtDeterminacionPeriodoDTO currentItem = new DtDeterminacionPeriodoDTO();
	private String codigoPredio;
	
	private List<DeudaValoresDTO> listarValorCoactiva = new ArrayList<DeudaValoresDTO>();
	private Boolean detFiscalizada;
	private Boolean conValor;
	
	// INICIO PERMISOS PARA EL MODULO -=CRAMIREZ=-
		private List<SimpleMenuDTO> listPermisosSubmenu = new ArrayList<SimpleMenuDTO>();		
		private boolean permisoDeterminar;
		private boolean permisoPDF_HR;
		private boolean permisoPDF_PU;
		private boolean permisoPDF_PR;
		private boolean permisoPDF_HL;
		private boolean permisoPDF_DJ;
		private boolean permisoVerCalculo;
		private boolean permisoVerComparacionDeterminacion;
		private boolean permisoVerArbitrios;
	// FIN PERMISOS PARA EL MODULO	

	public HistoriaDeterminacionPeriodoManaged() {

	}

	@PostConstruct
	public void init() {
		permisosMenu();
		try {
			records = new ArrayList<DtDeterminacionPeriodoDTO>();

			Integer personaId = getSessionManaged().getContribuyente().getPersonaId();
			if (personaId != null && personaId != Constante.RESULT_PENDING) {
				records = calculoPredialBo.getAllDtDeterminacionPeriodo( personaId, Boolean.FALSE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public void permisosMenu() {	
		try {
			int submenuId = Constante.DECLARACION_JURADA;
			
			int permisoDeterminarId = Constante.DETERMINAR;
			int permisoPDF_HR_ID = Constante.PDF_HR;
			int permisoPDF_PU_ID = Constante.PDF_PU;
			int permisoPDF_PR_ID = Constante.PDF_PR;
			int permisoPDF_HL_ID = Constante.PDF_HL;
			int permisoPDF_DJ_ID = Constante.PDF_DJ;
			int permisoVerCalculoId = Constante.VER_CALCULO;
			int permisoVerComparacionDeterminacionId = Constante.VER_COMPARACION_DETER_IMACION;
			int permisoVerArbitriosId = Constante.VER_ARBITRIOS;
			
			permisoDeterminar = false;
			permisoPDF_HR = false;
			permisoPDF_PU = false;
			permisoPDF_PR = false;
			permisoPDF_HL = false;
			permisoPDF_DJ = false;
			permisoVerCalculo = false;
			permisoVerComparacionDeterminacion = false;
			permisoVerArbitrios = false;
			
			listPermisosSubmenu  = menuBo.getAccesosSubmenuUsuario( getSessionManaged().getUsuarioLogIn().getUsuarioId() , submenuId);
			
			Iterator<SimpleMenuDTO> menuIterar = listPermisosSubmenu.iterator();
			while (menuIterar.hasNext()) {
				SimpleMenuDTO lsm = menuIterar.next();
				if(lsm.getItemId() == permisoDeterminarId) {
					permisoDeterminar = true;
				}
				if(lsm.getItemId() == permisoPDF_HR_ID) {
					permisoPDF_HR = true;
				}
				if(lsm.getItemId() == permisoPDF_PU_ID) {
					permisoPDF_PU = true;
				}
				if(lsm.getItemId() == permisoPDF_PR_ID) {
					permisoPDF_PR = true;
				}				
				if(lsm.getItemId() == permisoPDF_HL_ID) {
					permisoPDF_HL = true;
				}
				if(lsm.getItemId() == permisoPDF_DJ_ID) {
					permisoPDF_DJ = true;
				}
				if(lsm.getItemId() == permisoVerCalculoId) {
					permisoVerCalculo = true;
				}
				if(lsm.getItemId() == permisoVerComparacionDeterminacionId) {
					permisoVerComparacionDeterminacion = true;
				}
				if(lsm.getItemId() == permisoVerArbitriosId) {
					permisoVerArbitrios = true;
				}
			}
			
		} catch (SisatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String salir(){
		return sendRedirectPrincipal();
	}

	public String detalleDeterminacion() {
		try {
			if (currentItem != null) {
				getSessionMap().put("pageReturnDetalleDeter",
						"/sisat/predial/historiadeterminacionperiodo.xhtml");
				getSessionMap().put("DtDeterminacion",
						currentItem.getDeterminacion());
				getSessionMap().put("DtDeterminacionPeriodoDTO", currentItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		return sendRedirectPrincipal();
	}

	public void determinarCalculoImpuesto() {
		try {
			if (currentItem != null) {
//				if (validaValorDeuda()==Boolean.FALSE){
				Integer personaId = getSessionManaged().getContribuyente().getPersonaId();

				DeterminacionPredial predial = new DeterminacionPredial(calculoPredialBo, generalBo);
				String result = predial.generarDeterminacionPredialComun(personaId, Util.toInteger(currentItem.getAnnoDj()));

				if (result == null) {
					WebMessages.messageError("No se puede realizar la determinacion");
				} else {
					WebMessages.messageInfo(result);
				}
				records = new ArrayList<DtDeterminacionPeriodoDTO>();
				records = calculoPredialBo.getAllDtDeterminacionPeriodo(personaId, Boolean.FALSE);
//			  }else
//				  {WebMessages.messageError("No se puede realizar el cálculo del Impuesto Predial para deudas asociadas a Valores, porfavor informe a las Áreas correspondientes.");}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			WebMessages.messageError(e.getMessage());
		}
	}

	// hparedes:ini 28/06/2012
	public void createHR(boolean notario) {
		java.sql.Connection connection = null;
		try {
			CrudServiceBean connj = CrudServiceBean.getInstance();
			connection = connj.connectJasper();

			String path_context = FacesContext.getCurrentInstance()
					.getExternalContext().getRealPath("/");
			String path_report = path_context + "/sisat/reportes/";
			String path_imagen = path_context + "/sisat/reportes/imagen/"; 

			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("anio", currentItem.getDeterminacion().getAnnoDeterminacion());
			parameters.put("persona_id", currentItem.getDeterminacion().getPersonaId());
			parameters.put("ruta_image",SATWEBParameterFactory.getPathReporteImagenes());
			parameters.put("responsable", getSessionManaged().getUsuarioLogIn().getNombreUsuario());
			parameters.put("SUBREPORT_DIR",SATWEBParameterFactory.getPathReporte());
			
			JasperPrint jasperPrint;
			
			if(notario) {
				jasperPrint = JasperFillManager.fillReport(
						(SATWEBParameterFactory.getPathReporte() + "HR_notarios.jasper"),
						parameters, connection);
			}else {
				jasperPrint = JasperFillManager.fillReport(
						(SATWEBParameterFactory.getPathReporte() + "HR.jasper"),
						parameters, connection);
			}
			

			ByteArrayOutputStream output = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, output);

			HttpServletResponse response = (HttpServletResponse) FacesContext
					.getCurrentInstance().getExternalContext().getResponse();
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition",
					"attachment;filename=HRReport.pdf");
			response.setContentLength(output.size());
			ServletOutputStream servletOutputStream = response
					.getOutputStream();
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

	public void createPU(boolean notario) {
		java.sql.Connection connection = null;
		try {
			CrudServiceBean connj = CrudServiceBean.getInstance();
			connection = connj.connectJasper();
			String path_context = FacesContext.getCurrentInstance()
					.getExternalContext().getRealPath("/");
			String path_report = path_context + "/sisat/reportes/";
			String path_imagen = path_context + "/sisat/reportes/imagen/";

			// INICIO ITANTAMANGO
			if (currentItem.getDeterminacion().getCantUrbanos() > 0) {

				HashMap<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("anio", currentItem.getDeterminacion()
						.getAnnoDeterminacion());
				parameters.put("persona_id", currentItem.getDeterminacion()
						.getPersonaId());
				parameters.put("ruta_image",
						SATWEBParameterFactory.getPathReporteImagenes());
				parameters.put("responsable", getSessionManaged()
						.getUsuarioLogIn().getNombreUsuario());
				parameters.put("SUBREPORT_DIR",
						SATWEBParameterFactory.getPathReporte());
				
				JasperPrint jasperPrint;
				
				if(notario) {
					jasperPrint = JasperFillManager
							.fillReport(
									(SATWEBParameterFactory.getPathReporte() + "PU_notarios.jasper"),
									parameters, connection);
				}else {
					jasperPrint = JasperFillManager
							.fillReport(
									(SATWEBParameterFactory.getPathReporte() + "PU.jasper"),
									parameters, connection);
				}
				
				
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				JasperExportManager
						.exportReportToPdfStream(jasperPrint, output);

				HttpServletResponse response = (HttpServletResponse) FacesContext
						.getCurrentInstance().getExternalContext()
						.getResponse();
				response.setContentType("application/pdf");
				response.addHeader("Content-Disposition",
						"attachment;filename=PUReports.pdf");
				response.setContentLength(output.size());
				ServletOutputStream servletOutputStream = response
						.getOutputStream();
				servletOutputStream.write(output.toByteArray(), 0,
						output.size());
				servletOutputStream.flush();
				servletOutputStream.close();
				FacesContext.getCurrentInstance().responseComplete();
			}
			// FIN ITANTAMANGO
		} catch (Exception jre) {
			jre.printStackTrace();
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

	public void createPR(boolean notario) {
		java.sql.Connection connection = null;
		try {
			CrudServiceBean connj = CrudServiceBean.getInstance();
			connection = connj.connectJasper();

			String path_context = FacesContext.getCurrentInstance()
					.getExternalContext().getRealPath("/");
			String path_report = path_context + "/sisat/reportes/";
			String path_imagen = path_context + "/sisat/reportes/imagen/";

			// INICIO ITANTAMANGO
			if (currentItem.getDeterminacion().getCantRusticos() > 0) {

				HashMap<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("anio", currentItem.getDeterminacion()
						.getAnnoDeterminacion());
				parameters.put("persona_id", currentItem.getDeterminacion()
						.getPersonaId());
				parameters.put("ruta_image",
						SATWEBParameterFactory.getPathReporteImagenes());
				parameters.put("responsable", getSessionManaged()
						.getUsuarioLogIn().getNombreUsuario());
				parameters.put("SUBREPORT_DIR",
						SATWEBParameterFactory.getPathReporte());
				JasperPrint jasperPrint;
				if(notario) {
					jasperPrint = JasperFillManager
							.fillReport(
									(SATWEBParameterFactory.getPathReporte() + "PR_notarios.jasper"),
									parameters, connection);
				}else {
					jasperPrint = JasperFillManager
							.fillReport(
									(SATWEBParameterFactory.getPathReporte() + "PR.jasper"),
									parameters, connection);
				}
				
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				JasperExportManager
						.exportReportToPdfStream(jasperPrint, output);

				HttpServletResponse response = (HttpServletResponse) FacesContext
						.getCurrentInstance().getExternalContext()
						.getResponse();
				response.setContentType("application/pdf");
				response.addHeader("Content-Disposition",
						"attachment;filename=PRReport.pdf");
				response.setContentLength(output.size());
				ServletOutputStream servletOutputStream = response
						.getOutputStream();
				servletOutputStream.write(output.toByteArray(), 0,
						output.size());
				servletOutputStream.flush();
				servletOutputStream.close();
				FacesContext.getCurrentInstance().responseComplete();
			}
		} catch (Exception jre) {
			jre.printStackTrace();
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

	public void createHL() {
		java.sql.Connection connection = null;
		try {
			CrudServiceBean connj = CrudServiceBean.getInstance();
			connection = connj.connectJasper();

			String path_context = FacesContext.getCurrentInstance()
					.getExternalContext().getRealPath("/");
			String path_report = path_context + "/sisat/reportes/";
			String path_imagen = path_context + "/sisat/reportes/imagen/";

			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("anio", currentItem.getDeterminacion()
					.getAnnoDeterminacion());
			parameters.put("persona_id", currentItem.getDeterminacion()
					.getPersonaId());
			parameters.put("ruta_image",
					SATWEBParameterFactory.getPathReporteImagenes());
			parameters.put("responsable", getSessionManaged().getUsuarioLogIn()
					.getNombreUsuario());
			parameters.put("SUBREPORT_DIR",
					SATWEBParameterFactory.getPathReporte());
			JasperPrint jasperPrint = JasperFillManager
					.fillReport(
							(SATWEBParameterFactory.getPathReporte() + "HojaLiquidacionIP.jasper"),
							parameters, connection);
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, output);

			HttpServletResponse response = (HttpServletResponse) FacesContext
					.getCurrentInstance().getExternalContext().getResponse();
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition",
					"attachment;filename=HojaLiquidacionIPReport.pdf");
			response.setContentLength(output.size());
			ServletOutputStream servletOutputStream = response
					.getOutputStream();
			servletOutputStream.write(output.toByteArray(), 0, output.size());
			servletOutputStream.flush();
			servletOutputStream.close();
			FacesContext.getCurrentInstance().responseComplete();

		} catch (Exception jre) {
			jre.printStackTrace();
			WebMessages.messageFatal(jre);
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

	public void createDJ() {
		java.sql.Connection connection = null;
		try {
			CrudServiceBean connj = CrudServiceBean.getInstance();
			connection = connj.connectJasper();

			String path_context = FacesContext.getCurrentInstance()
					.getExternalContext().getRealPath("/");
			String path_report = path_context + "/sisat/reportes/";
			String path_imagen = path_context + "/sisat/reportes/imagen/";

			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("determinacion_id", currentItem.getDeterminacion().getDeterminacionId());
			parameters.put("ruta_imagen",SATWEBParameterFactory.getPathReporteImagenes());
			parameters.put("responsable", getSessionManaged().getUsuarioLogIn().getNombreUsuario());
			parameters.put("SUBREPORT_DIR",SATWEBParameterFactory.getPathReporte());
			JasperPrint jasperPrint = JasperFillManager
					.fillReport(
							(SATWEBParameterFactory.getPathReporte() + "reporte_sin_formato.jasper"),
							parameters, connection);
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, output);

			HttpServletResponse response = (HttpServletResponse) FacesContext
					.getCurrentInstance().getExternalContext().getResponse();
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition",
					"attachment;filename=DeclaracionJurada-IP-Report.pdf");
			response.setContentLength(output.size());
			ServletOutputStream servletOutputStream = response
					.getOutputStream();
			servletOutputStream.write(output.toByteArray(), 0, output.size());
			servletOutputStream.flush();
			servletOutputStream.close();
			FacesContext.getCurrentInstance().responseComplete();

		} catch (Exception jre) {
			jre.printStackTrace();
			WebMessages.messageFatal(jre);
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
	// hparedes:fin 28/06/2012

	private Integer personaId1;
	private Integer personaId2;
	private Integer anio;

	public void generarDeterminacionParaAlcabala() {
		try {
//			System.out.println("Esto sale en pantalla\n");
			
			DeterminacionPredial determinacion = new DeterminacionPredial(
					calculoPredialBo, generalBo);
			determinacion.gererarDetPredioMasivoParaAlcabala(personaId1,
					personaId2, anio);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public Boolean validaValorDeuda(){
		try{
			if(currentItem!=null){	
				
				String fiscalizado=currentItem.getDeterminacion().getFiscalizado();
				Integer codPersona = getSessionManaged().getContribuyente().getPersonaId();
				String anio=currentItem.getAnnoDj();
				Integer determinacion=currentItem.getDeterminacion().getDeterminacionId();
								
				if (fiscalizado.equals(Constante.FISCALIZADO_SI)){
					setDetFiscalizada(true);
				}else{
					setDetFiscalizada(false);
				}
				
				if (detFiscalizada==Boolean.TRUE){

					listarValorCoactiva=calculoPredialBo.getValorCoactiva(codPersona,anio,Constante.TIPO_ACTO_RESOLUCION_DETERMINACION_ID,determinacion);
					if (listarValorCoactiva!=null){
						setConValor(true);
						//WebMessages.messageError("El contribuyente tiene Valores, porfavor informe a las áreas correspondientes el efecto de la determinación.");
					}else{
						setConValor(false);
					}

				}else{
					listarValorCoactiva=calculoPredialBo.getValorCoactiva(codPersona,anio,Constante.TIPO_ACTO_ORDEN_PAGO_ID,determinacion);
					if (listarValorCoactiva!=null){
						setConValor(true);
					//	WebMessages.messageError("El contribuyente tiene Valores, porfavor informe a las áreas correspondientes el efecto de la determinación.");
					}else{
						setConValor(false);
					}
				}
				
			}else{
				
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		return conValor;
	}
	
	
	public SimpleSelection getSelection() {
		return selection;
	}

	public void setSelection(SimpleSelection selection) {
		this.selection = selection;
	}

	public HtmlExtendedDataTable getTable() {
		return table;
	}

	public void setTable(HtmlExtendedDataTable table) {
		this.table = table;
	}

	public int getCurrentRow() {
		return currentRow;
	}

	public void setCurrentRow(int currentRow) {
		this.currentRow = currentRow;
	}

	public String getCodigoPredio() {
		return codigoPredio;
	}

	public void setCodigoPredio(String codigoPredio) {
		this.codigoPredio = codigoPredio;
	}

	public ArrayList<DtDeterminacionPeriodoDTO> getRecords() {
		return records;
	}

	public void setRecords(ArrayList<DtDeterminacionPeriodoDTO> records) {
		this.records = records;
	}

	public DtDeterminacionPeriodoDTO getCurrentItem() {
		return currentItem;
	}

	public void setCurrentItem(DtDeterminacionPeriodoDTO currentItem) {
		this.currentItem = currentItem;
	}

	public Integer getPersonaId1() {
		return personaId1;
	}

	public void setPersonaId1(Integer personaId1) {
		this.personaId1 = personaId1;
	}

	public Integer getPersonaId2() {
		return personaId2;
	}

	public void setPersonaId2(Integer personaId2) {
		this.personaId2 = personaId2;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public Boolean getDetFiscalizada() {
		return detFiscalizada;
	}

	public void setDetFiscalizada(Boolean detFiscalizada) {
		this.detFiscalizada = detFiscalizada;
	}

	public Boolean getConValor() {
		return conValor;
	}

	public void setConValor(Boolean conValor) {
		this.conValor = conValor;
	}

	public List<SimpleMenuDTO> getListPermisosSubmenu() {
		return listPermisosSubmenu;
	}

	public void setListPermisosSubmenu(List<SimpleMenuDTO> listPermisosSubmenu) {
		this.listPermisosSubmenu = listPermisosSubmenu;
	}

	public boolean isPermisoDeterminar() {
		return permisoDeterminar;
	}

	public void setPermisoDeterminar(boolean permisoDeterminar) {
		this.permisoDeterminar = permisoDeterminar;
	}

	public boolean isPermisoPDF_HR() {
		return permisoPDF_HR;
	}

	public void setPermisoPDF_HR(boolean permisoPDF_HR) {
		this.permisoPDF_HR = permisoPDF_HR;
	}

	public boolean isPermisoPDF_PU() {
		return permisoPDF_PU;
	}

	public void setPermisoPDF_PU(boolean permisoPDF_PU) {
		this.permisoPDF_PU = permisoPDF_PU;
	}

	public boolean isPermisoPDF_PR() {
		return permisoPDF_PR;
	}

	public void setPermisoPDF_PR(boolean permisoPDF_PR) {
		this.permisoPDF_PR = permisoPDF_PR;
	}

	public boolean isPermisoPDF_HL() {
		return permisoPDF_HL;
	}

	public void setPermisoPDF_HL(boolean permisoPDF_HL) {
		this.permisoPDF_HL = permisoPDF_HL;
	}

	public boolean isPermisoVerCalculo() {
		return permisoVerCalculo;
	}

	public void setPermisoVerCalculo(boolean permisoVerCalculo) {
		this.permisoVerCalculo = permisoVerCalculo;
	}

	public boolean isPermisoVerComparacionDeterminacion() {
		return permisoVerComparacionDeterminacion;
	}

	public void setPermisoVerComparacionDeterminacion(boolean permisoVerComparacionDeterminacion) {
		this.permisoVerComparacionDeterminacion = permisoVerComparacionDeterminacion;
	}

	public boolean isPermisoVerArbitrios() {
		return permisoVerArbitrios;
	}

	public void setPermisoVerArbitrios(boolean permisoVerArbitrios) {
		this.permisoVerArbitrios = permisoVerArbitrios;
	}

	public boolean isPermisoPDF_DJ() {
		return permisoPDF_DJ;
	}

	public void setPermisoPDF_DJ(boolean permisoPDF_DJ) {
		this.permisoPDF_DJ = permisoPDF_DJ;
	}
	
}
