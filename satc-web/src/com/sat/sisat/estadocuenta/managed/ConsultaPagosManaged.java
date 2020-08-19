package com.sat.sisat.estadocuenta.managed;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
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

import com.sat.sisat.caja.managed.CajaCobranzaManaged;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.SATWEBParameterFactory;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.estadocuenta.business.EstadoCuentaBoRemote;
import com.sat.sisat.estadocuenta.dto.ReciboPago;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.menus.business.MenuBoRemote;
import com.sat.sisat.menus.dto.SimpleMenuDTO;
import com.sat.sisat.persistence.CrudServiceBean;
import com.sat.sisat.persistence.entity.CjReciboPago;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@ManagedBean
@ViewScoped
public class ConsultaPagosManaged extends BaseManaged{ 
	

	private static final long serialVersionUID = 1L;

	@EJB
	EstadoCuentaBoRemote estadocuentaBo;
	
	@EJB
	MenuBoRemote menuBo;
	
	//private ArrayList<CjReciboPago> listaRecibos= new ArrayList<CjReciboPago>();
	private ArrayList<ReciboPago> listaRecibos= new ArrayList<ReciboPago>();
	
	//private CjReciboPago currentItem= new CjReciboPago();
	private ReciboPago currentItem= new ReciboPago();
	
	private Date fechaInicioRecibo;
	
	private Date fechaFinRecibo;
	
	private String nroRecibo;
	
	// INICIO PERMISOS PARA EL MODULO -=CRAMIREZ=-
		private List<SimpleMenuDTO> listPermisosSubmenu = new ArrayList<SimpleMenuDTO>();
		private boolean permisoBuscar;
		private boolean permisoVerDetalle;
		private boolean permisoImprimir;
	// FIN PERMISOS PARA EL MODULO

	public String getNroRecibo() {
		return nroRecibo;
	}

	public void setNroRecibo(String nroRecibo) {
		this.nroRecibo = nroRecibo;
	}

	public Date getFechaInicioRecibo() {
		return fechaInicioRecibo;
	}

	public void setFechaInicioRecibo(Date fechaInicioRecibo) {
		this.fechaInicioRecibo = fechaInicioRecibo;
	}

	public Date getFechaFinRecibo() {
		return fechaFinRecibo;
	}

	public void setFechaFinRecibo(Date fechaFinRecibo) {
		this.fechaFinRecibo = fechaFinRecibo;
	}

	public ReciboPago getCurrentItem() {
		return currentItem;
	}

	public void setCurrentItem(ReciboPago currentItem) {
		this.currentItem = currentItem;
	}

	public ArrayList<ReciboPago> getListaRecibos() {
		return listaRecibos;
	}

	private CajaCobranzaManaged getCajaCobranzaManaged(){
		return (CajaCobranzaManaged) getManaged("cajaCobranzaManaged");
	}

	public void setListaRecibos(ArrayList<ReciboPago> listaRecibos) {
		this.listaRecibos = listaRecibos;
	}



	@PostConstruct
	public void init(){
		permisosMenu();
		try {
			listaRecibos= estadocuentaBo.obtenerRecibosPagoNew(getSessionManaged().getContribuyente().getPersonaId(),null, null, null);
				
		} catch (Exception e) {
			System.out.println("ERROR: "+e.getStackTrace());
		}
		
	}
	
	public void permisosMenu() {	
		try {
			int submenuId = Constante.CONSULTA_DE_PAGOS;
			
			int permisoBuscarId = Constante.BUSCAR;
			int permisoVerDetalleId = Constante.VER_DETALLE;
			int permisoImprimirId = Constante.IMPRIMIR;
			
			permisoBuscar = false;
			permisoVerDetalle = false;
			permisoImprimir = false;			
			
			listPermisosSubmenu  = menuBo.getAccesosSubmenuUsuario( getSessionManaged().getUsuarioLogIn().getUsuarioId() , submenuId);
			
			Iterator<SimpleMenuDTO> menuIterar = listPermisosSubmenu.iterator();
			while (menuIterar.hasNext()) {
				SimpleMenuDTO lsm = menuIterar.next();
				if(lsm.getItemId() == permisoBuscarId) {
					permisoBuscar = true;
				}
				if(lsm.getItemId() == permisoVerDetalleId) {
					permisoVerDetalle = true;
				}
				if(lsm.getItemId() == permisoImprimirId) {
					permisoImprimir = true;
				}
			}
			
		} catch (SisatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void mostrarRecibo(){
		if(currentItem.getReciboId()>0){
			int reciboId= currentItem.getReciboId();
			getSessionManaged().getSessionMap().put("caja.imprimirecibo.reciboId", reciboId);
		}
	}
	
	public void buscarRecibos(){
		try {
			if(nroRecibo!=null){
				nroRecibo=nroRecibo.trim();
			}
			
//			if(nroRecibo!=null){
			listaRecibos= estadocuentaBo.obtenerRecibosPagoNew(getSessionManaged().getContribuyente().getPersonaId(),nroRecibo, fechaInicioRecibo, fechaFinRecibo);
//			}else if(nroRecibo==null&&fechaInicioRecibo!=null){
//				
//			}
		} catch (Exception e) {
			System.out.println("ERROR: "+e.getStackTrace());
		}
	}
	
	
	
	 public void impresionConstancia(int reciboId) {
			try {

				String cadena = null;
				java.sql.Connection connection = null;
				cadena = "reporte_constacia_no_adeudo.jasper";

				CrudServiceBean connj = CrudServiceBean.getInstance();
				connection = connj.connectJasper();
				String path_context = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
				String path_report = path_context + "/sisat/reportes/";
				String path_imagen = path_context + "/sisat/reportes/imagen/";
				HashMap<String, Object> parameters = new HashMap<String, Object>();
				
				Integer persona = getSessionManaged().getContribuyente().getPersonaId();
				
				Integer recibo = reciboId;
				
				parameters.put("persona_id", persona);
				parameters.put("recibo_id", recibo);
				parameters.put("ruta_imagen", path_imagen);
				parameters.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						(SATWEBParameterFactory.getPathReporte() + cadena),
						parameters, connection);
				
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				HttpServletResponse response = (HttpServletResponse) FacesContext
						.getCurrentInstance().getExternalContext()
						.getResponse();
					JasperExportManager.exportReportToPdfStream(jasperPrint,output);
				
					response.setContentType("application/pdf");
					response.addHeader("Content-Disposition","attachment;filename=" + cadena.replaceAll(".jasper", ".pdf"));

				response.setContentLength(output.size());
				ServletOutputStream servletOutputStream = response.getOutputStream();
				
				servletOutputStream.write(output.toByteArray(), 0,output.size());
				
				servletOutputStream.flush();
				servletOutputStream.close();
				FacesContext.getCurrentInstance().responseComplete();
			
			
			} catch (Exception e) {
				e.printStackTrace();
				WebMessages.messageFatal(e);
			}
		}
	 
	 
	 
	 public void createHR(Integer recibo_id) {
			
		 java.sql.Connection connection=null;
	        try {
	        	CrudServiceBean connj=CrudServiceBean.getInstance();
				 connection=connj.connectJasper();
	        	String path_context=FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
			     String path_report=path_context+"/sisat/reportes/";
			     String path_imagen=path_context+"/sisat/reportes/imagen/";
			     
	        	
	             HashMap<String,Object> parameters = new HashMap<String,Object>();
	             parameters.put("recibo_id", recibo_id);
	             parameters.put("ruta_image", SATWEBParameterFactory.getPathReporteImagenes()) ;
	             parameters.put("responsable", getSessionManaged().getUsuarioLogIn().getNombreUsuario());
	 			 parameters.put("SUBREPORT_DIR", SATWEBParameterFactory.getPathReporte()) ;
	 			
	                JasperPrint jasperPrint = JasperFillManager.fillReport((SATWEBParameterFactory.getPathReporte()+"HR_record.jasper"), parameters, connection);
	            
	             ByteArrayOutputStream output = new ByteArrayOutputStream();
	                JasperExportManager.exportReportToPdfStream(jasperPrint, output);
	               
	          HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	                 response.setContentType("application/pdf");
	          response.addHeader("Content-Disposition","attachment;filename=HRReport.pdf");
	          response.setContentLength(output.size());
	          ServletOutputStream servletOutputStream = response.getOutputStream();
	          servletOutputStream.write(output.toByteArray(), 0, output.size());
	          servletOutputStream.flush();
	          servletOutputStream.close();
	          FacesContext.getCurrentInstance().responseComplete();
	          
	          
	     } catch (Exception jre) {
	          jre.printStackTrace();
	          WebMessages.messageFatal(jre);
	     }finally{
	    	 try{
	    		 if(connection!=null){
	    			 connection.close();
	    			 connection=null;
	    		 }
	    	 }catch(Exception e){}
	     }
	        
	 }
	
	 public void generaPUPR(Integer recibo_id, Integer tipo){
		 if(tipo == 2) {
			 createPU(recibo_id); 
		 }else if (tipo == 3){
			 createPR(recibo_id);  
		 }
	 }
	
	 public void createPU(Integer recibo_id) {
		 	java.sql.Connection connection=null;
	        try {
				 CrudServiceBean connj=CrudServiceBean.getInstance();
				 connection=connj.connectJasper();
				 
	        	String path_context=FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
			     String path_report=path_context+"/sisat/reportes/";
			     String path_imagen=path_context+"/sisat/reportes/imagen/";
			     
	            HashMap<String,Object> parameters = new HashMap<String,Object>();
	            parameters.put("recibo_id", recibo_id);
	            parameters.put("ruta_image", SATWEBParameterFactory.getPathReporteImagenes()) ;
	            parameters.put("responsable", getSessionManaged().getUsuarioLogIn().getNombreUsuario());
	 			parameters.put("SUBREPORT_DIR", SATWEBParameterFactory.getPathReporte()) ;
	            JasperPrint jasperPrint = JasperFillManager.fillReport((SATWEBParameterFactory.getPathReporte()+"PU_record.jasper"), parameters, connection);
	            ByteArrayOutputStream output = new ByteArrayOutputStream();
	            JasperExportManager.exportReportToPdfStream(jasperPrint, output);
	               
	          HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	                 response.setContentType("application/pdf");
	          response.addHeader("Content-Disposition","attachment;filename=PUReports.pdf");
	          response.setContentLength(output.size());
	          ServletOutputStream servletOutputStream = response.getOutputStream();
	          servletOutputStream.write(output.toByteArray(), 0, output.size());
	          servletOutputStream.flush();
	          servletOutputStream.close();
	          FacesContext.getCurrentInstance().responseComplete();
	          
	     } catch (Exception jre) {
	          jre.printStackTrace();
	          WebMessages.messageFatal(jre);
	     }finally{
	    	 try{
	    		 if(connection!=null){
	    			 connection.close();
	    			 connection=null;
	    		 }
	    	 }catch(Exception e){}
	     }
	 }
	 
	 
	 public void createPR(Integer recibo_id) {
		 	java.sql.Connection connection=null;
	        try {
				 CrudServiceBean connj=CrudServiceBean.getInstance();
				 connection=connj.connectJasper();
				 
	        	String path_context=FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
			     String path_report=path_context+"/sisat/reportes/";
			     String path_imagen=path_context+"/sisat/reportes/imagen/";
			     
	            HashMap<String,Object> parameters = new HashMap<String,Object>();
	            parameters.put("recibo_id", recibo_id);
	            parameters.put("ruta_image", SATWEBParameterFactory.getPathReporteImagenes()) ;
	            parameters.put("responsable", getSessionManaged().getUsuarioLogIn().getNombreUsuario());
	 			parameters.put("SUBREPORT_DIR", SATWEBParameterFactory.getPathReporte()) ;
	            JasperPrint jasperPrint = JasperFillManager.fillReport((SATWEBParameterFactory.getPathReporte()+"PR_record.jasper"), parameters, connection);
	            ByteArrayOutputStream output = new ByteArrayOutputStream();
	            JasperExportManager.exportReportToPdfStream(jasperPrint, output);
	               
	          HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	                 response.setContentType("application/pdf");
	          response.addHeader("Content-Disposition","attachment;filename=PRReports.pdf");
	          response.setContentLength(output.size());
	          ServletOutputStream servletOutputStream = response.getOutputStream();
	          servletOutputStream.write(output.toByteArray(), 0, output.size());
	          servletOutputStream.flush();
	          servletOutputStream.close();
	          FacesContext.getCurrentInstance().responseComplete();
	          
	     } catch (Exception jre) {
	          jre.printStackTrace();
	          WebMessages.messageFatal(jre);
	     }finally{
	    	 try{
	    		 if(connection!=null){
	    			 connection.close();
	    			 connection=null;
	    		 }
	    	 }catch(Exception e){}
	     }
	 }

	public List<SimpleMenuDTO> getListPermisosSubmenu() {
		return listPermisosSubmenu;
	}

	public void setListPermisosSubmenu(List<SimpleMenuDTO> listPermisosSubmenu) {
		this.listPermisosSubmenu = listPermisosSubmenu;
	}

	public boolean isPermisoBuscar() {
		return permisoBuscar;
	}

	public void setPermisoBuscar(boolean permisoBuscar) {
		this.permisoBuscar = permisoBuscar;
	}

	public boolean isPermisoVerDetalle() {
		return permisoVerDetalle;
	}

	public void setPermisoVerDetalle(boolean permisoVerDetalle) {
		this.permisoVerDetalle = permisoVerDetalle;
	}

	public boolean isPermisoImprimir() {
		return permisoImprimir;
	}

	public void setPermisoImprimir(boolean permisoImprimir) {
		this.permisoImprimir = permisoImprimir;
	}
	
	

}
