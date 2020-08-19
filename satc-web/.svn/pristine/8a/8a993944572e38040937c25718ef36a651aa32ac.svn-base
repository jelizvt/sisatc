package com.sat.sisat.fiscalizacion.managed;

import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.richfaces.component.html.HtmlComboBox;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.SATWEBParameterFactory;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.controlycobranza.dto.FindCcLote;
import com.sat.sisat.controlycobranza.dto.FindCcLoteDetalleActo;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.fiscalizacion.business.FiscalizacionBo;
import com.sat.sisat.fiscalizacion.business.FiscalizacionBoRemote;
import com.sat.sisat.fiscalizacion.dto.FindInpscDocTipo;
import com.sat.sisat.fiscalizacion.dto.FindInspeccionHistorial;
import com.sat.sisat.menus.business.MenuBoRemote;
import com.sat.sisat.menus.dto.SimpleMenuDTO;
import com.sat.sisat.persistence.CrudServiceBean;
import com.sat.sisat.persistence.entity.CcLote;
import com.sat.sisat.persistence.entity.RpFiscalizacionPrograma;
import com.sat.sisat.persistence.entity.RvOmisosVehicular;


@ManagedBean
@ViewScoped
public class BusquedaLoteCarteraVehicularManaged extends BaseManaged {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	FiscalizacionBoRemote ficalizacionBo;
	
	@EJB
	MenuBoRemote menuBo;
	
	// BUSQUEDA:
	private List<FindCcLote> lstFindLoteVehicular;
	private FindCcLote findCcLoteItem;
	private FindCcLote findCcLoteItem1;
	List<Integer> listEstadisticas = new ArrayList<Integer>();
	private Integer nroLoteasociado;
	
	// INICIO PERMISOS PARA EL MODULO -=CRAMIREZ=-
	 	private List<SimpleMenuDTO> listPermisosSubmenu = new ArrayList<SimpleMenuDTO>();	 	
	 	private boolean permisoVerDetalle;	 	
	 	private boolean permisoModificarActualizar;
	 	private boolean permisoEliminar;
	 	
	 	private boolean permisoModificarActualizarCR;
	// FIN PERMISOS PARA EL MODULO
		 	
	public BusquedaLoteCarteraVehicularManaged() throws Exception {
	}

	@PostConstruct
	public void init() throws Exception {
		permisosMenu();
		try {			
			listEstadisticas=null;
			lstFindLoteVehicular = ficalizacionBo.getAllLotes();			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void permisosMenu() {	
		try {
			int submenuId = Constante.CARTERA_OMISOS;
		 	
			int permisoVerDetalleId = Constante.VER_DETALLE;
			int permisoModificarActualizarId = Constante.MODIFICAR_ACTUALIZAR;
			int permisoEliminarId = Constante.ELIMINAR;
			
			
			permisoVerDetalle = false;
		 	permisoModificarActualizar = false;
		 	permisoEliminar = false;
			
			listPermisosSubmenu  = menuBo.getAccesosSubmenuUsuario( getSessionManaged().getUsuarioLogIn().getUsuarioId() , submenuId);
			
			Iterator<SimpleMenuDTO> menuIterar = listPermisosSubmenu.iterator();
			while (menuIterar.hasNext()) {
				SimpleMenuDTO lsm = menuIterar.next();
				if(lsm.getItemId() == permisoVerDetalleId) {
					permisoVerDetalle = true;
				}
				if(lsm.getItemId() == permisoModificarActualizarId) {
					permisoModificarActualizar = true;
				}
				if(lsm.getItemId() == permisoEliminarId) {
					permisoEliminar = true;
				}
			}
			
			permisoModificarActualizarCR = false;
			
			int submenuCRId = Constante.CONTROL_DE_REQUERIMIENTOS_VEHICULAR;
			
			listPermisosSubmenu  = menuBo.getAccesosSubmenuUsuario( getSessionManaged().getUsuarioLogIn().getUsuarioId() , submenuCRId);
			
			Iterator<SimpleMenuDTO> menuIterarCR = listPermisosSubmenu.iterator();
			while (menuIterarCR.hasNext()) {
				SimpleMenuDTO lsm = menuIterarCR.next();
				if(lsm.getItemId() == permisoModificarActualizarId) {
					permisoModificarActualizarCR = true;
				}
			}
		} catch (SisatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	

	public void estadistica(){
		try{				
			listEstadisticas=ficalizacionBo.estadisticaLote(21716);//findCcLoteItem.getLoteId());
			//sendRedirectPrincipal();
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
			
	}
	public String edit(){
		try{
			getSessionMap().put("findCcLoteItm", findCcLoteItem);
			}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		return sendRedirectPrincipal();
	}
	public String listarFiscalizacion(){
		try{
			getSessionMap().put("findCcLoteItm1", findCcLoteItem);
			}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		return sendRedirectPrincipal();
	}	
	
	public void imprimirCarteraXLS() {
		java.sql.Connection connection=null;
		try {
			CrudServiceBean connj=CrudServiceBean.getInstance();
			connection=connj.connectJasper();
			    
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			
			Integer val = findCcLoteItem.getLoteId();

			// falta el tipo de rec como parametro
			parameters.put("lote_id", val);
			
			JasperPrint jasperPrint = JasperFillManager.fillReport((SATWEBParameterFactory.getPathReporte()+"reporte_inspeccion_rv_cartera.jasper"),parameters,connection);
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
			    exporterXls.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, System.currentTimeMillis() + "Cartera_Vehicular" + ".xls");  
			    exporterXls.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, output);  
			    exporterXls.exportReport();

			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			response.setContentType("application/vnd.ms-excel");  
		    response.setHeader("Content-Disposition", "attachment;filename=" + System.currentTimeMillis() + "Cartera_Vehicular.xls");  
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
	
	public void imprimirRequerimiento() throws SQLException {
		java.sql.Connection connection = null;
		try {
			//Integer persona=getSessionManaged().getContribuyente().getPersonaId();
			CrudServiceBean connj = CrudServiceBean.getInstance();
			connection = connj.connectJasper();

			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("lote_id", getNroLoteasociado());
			parameters.put("ruta_imagen",
					SATWEBParameterFactory.getPathReporteImagenes());
			parameters.put("SUBREPORT_DIR",
					SATWEBParameterFactory.getPathReporte());
			parameters.put("responsable", getSessionManaged().getUsuarioLogIn()
					.getNombreUsuario());
			parameters.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
			JasperPrint jasperPrint = JasperFillManager
					.fillReport(
							(SATWEBParameterFactory.getPathReporte() + "reporte_inspeccion_rv_requerimiento.jasper"),
							parameters, connection);
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, output);
			HttpServletResponse response = (HttpServletResponse) FacesContext
					.getCurrentInstance().getExternalContext().getResponse();
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition",
					"attachment;filename=Requerimiento_Vehicular.pdf");
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
	
	public List<FindCcLote> getLstFindLoteVehicular() {
		return lstFindLoteVehicular;
	}

	public void setLstFindLoteVehicular(List<FindCcLote> lstFindLoteVehicular) {
		this.lstFindLoteVehicular = lstFindLoteVehicular;
	}

	public FindCcLote getFindCcLoteItem() {
		return findCcLoteItem;
	}

	public void setFindCcLoteItem(FindCcLote findCcLoteItem) {
		this.findCcLoteItem = findCcLoteItem;
	}

	public List<Integer> getListEstadisticas() {
		return listEstadisticas;
	}

	public void setListEstadisticas(List<Integer> listEstadisticas) {
		this.listEstadisticas = listEstadisticas;
	}

	public FindCcLote getFindCcLoteItem1() {
		return findCcLoteItem1;
	}

	public void setFindCcLoteItem1(FindCcLote findCcLoteItem1) {
		this.findCcLoteItem1 = findCcLoteItem1;
	}

	public Integer getNroLoteasociado() {
		return nroLoteasociado;
	}

	public void setNroLoteasociado(Integer nroLoteasociado) {
		this.nroLoteasociado = nroLoteasociado;
	}

	public List<SimpleMenuDTO> getListPermisosSubmenu() {
		return listPermisosSubmenu;
	}

	public void setListPermisosSubmenu(List<SimpleMenuDTO> listPermisosSubmenu) {
		this.listPermisosSubmenu = listPermisosSubmenu;
	}

	public boolean isPermisoVerDetalle() {
		return permisoVerDetalle;
	}

	public void setPermisoVerDetalle(boolean permisoVerDetalle) {
		this.permisoVerDetalle = permisoVerDetalle;
	}

	public boolean isPermisoModificarActualizar() {
		return permisoModificarActualizar;
	}

	public void setPermisoModificarActualizar(boolean permisoModificarActualizar) {
		this.permisoModificarActualizar = permisoModificarActualizar;
	}

	public boolean isPermisoEliminar() {
		return permisoEliminar;
	}

	public void setPermisoEliminar(boolean permisoEliminar) {
		this.permisoEliminar = permisoEliminar;
	}

	public boolean isPermisoModificarActualizarCR() {
		return permisoModificarActualizarCR;
	}

	public void setPermisoModificarActualizarCR(boolean permisoModificarActualizarCR) {
		this.permisoModificarActualizarCR = permisoModificarActualizarCR;
	}
	
}
