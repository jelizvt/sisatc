package com.sat.sisat.fiscalizacion.managed;

import java.io.ByteArrayOutputStream;
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

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.SATWEBParameterFactory;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.controlycobranza.business.ControlyCobranzaBoRemote;
import com.sat.sisat.controlycobranza.dto.FindCcLote;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.fiscalizacion.dto.FindInspeccionHistorial;
import com.sat.sisat.menus.business.MenuBoRemote;
import com.sat.sisat.menus.dto.SimpleMenuDTO;
import com.sat.sisat.persistence.CrudServiceBean;

@ManagedBean
@ViewScoped
public class BusquedaLoteRDVehicularManaged extends BaseManaged {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	ControlyCobranzaBoRemote controlycobranzaBo;
	
	@EJB
	MenuBoRemote menuBo;
	
	private List<FindCcLote> lstFindLote;
	
	private FindCcLote currentItem;
	
	public BusquedaLoteRDVehicularManaged() throws Exception {}
	
	
	// INICIO PERMISOS PARA EL MODULO -=CRAMIREZ=-
	 	private List<SimpleMenuDTO> listPermisosSubmenu = new ArrayList<SimpleMenuDTO>();
	 	private boolean permisoAgregarRegistrar;
	 	private boolean permisoDescargaPDF;
	// FIN PERMISOS PARA EL MODULO
	
	@PostConstruct
	public void init() {		
		permisosMenu();
		try
		{
			lstFindLote=controlycobranzaBo.getAllFindCcLoteRDVehicular();
			
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}	
	}
	
	public void permisosMenu() {	
		try {
			int submenuId = Constante.RESOL_DETERMINACION_VEHIC;
			
			int permisoAgregarRegistrarId = Constante.AGREGAR_REGISTRAR;
			int permisoDescargaPDFId = Constante.DESCARGA_PDF;
			
		 	permisoAgregarRegistrar = false;
		 	permisoDescargaPDF = false;
			
			listPermisosSubmenu  = menuBo.getAccesosSubmenuUsuario( getSessionManaged().getUsuarioLogIn().getUsuarioId() , submenuId);
			
			Iterator<SimpleMenuDTO> menuIterar = listPermisosSubmenu.iterator();
			while (menuIterar.hasNext()) {
				SimpleMenuDTO lsm = menuIterar.next();
				if(lsm.getItemId() == permisoAgregarRegistrarId) {
					permisoAgregarRegistrar = true;
				}
				if(lsm.getItemId() == permisoDescargaPDFId) {
					permisoDescargaPDF = true;
				}
			}
		} catch (SisatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	public String registroNuevoLote(){
//    	FacesUtil.closeSession("registroLoteRDArbitriosManaged");
		getSessionMap().remove("findCcLoteItem");
		return sendRedirectPrincipal();
	}
	
	public void generacionRD(){
		try{
			if(currentItem!=null){
				java.sql.Connection connection = null;
				try {
					CrudServiceBean connj = CrudServiceBean.getInstance();
					connection = connj.connectJasper();

					String path_context = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
					String path_report = path_context + "/sisat/reportes/";
					String path_imagen = path_context + "/sisat/reportes/imagen/";

					HashMap<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("p_lote_id", currentItem.getLoteId());
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

	public List<FindCcLote> getLstFindLote() {
		return lstFindLote;
	}

	public void setLstFindLote(List<FindCcLote> lstFindLote) {
		this.lstFindLote = lstFindLote;
	}

	public FindCcLote getCurrentItem() {
		return currentItem;
	}

	public void setCurrentItem(FindCcLote currentItem) {
		this.currentItem = currentItem;
	}

	public List<SimpleMenuDTO> getListPermisosSubmenu() {
		return listPermisosSubmenu;
	}

	public void setListPermisosSubmenu(List<SimpleMenuDTO> listPermisosSubmenu) {
		this.listPermisosSubmenu = listPermisosSubmenu;
	}

	public boolean isPermisoAgregarRegistrar() {
		return permisoAgregarRegistrar;
	}

	public void setPermisoAgregarRegistrar(boolean permisoAgregarRegistrar) {
		this.permisoAgregarRegistrar = permisoAgregarRegistrar;
	}

	public boolean isPermisoDescargaPDF() {
		return permisoDescargaPDF;
	}

	public void setPermisoDescargaPDF(boolean permisoDescargaPDF) {
		this.permisoDescargaPDF = permisoDescargaPDF;
	}
    
	
}
