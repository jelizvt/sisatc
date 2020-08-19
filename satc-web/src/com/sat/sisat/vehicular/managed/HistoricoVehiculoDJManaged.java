package com.sat.sisat.vehicular.managed;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.SATWEBParameterFactory;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.determinacion.exception.DeterminacionVehicularException;
import com.sat.sisat.determinacion.vehicular.business.DeterminacionVehicularBoRemote;
import com.sat.sisat.determinacion.vehicular.dto.DeudaValoresDTO;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.menus.business.MenuBoRemote;
import com.sat.sisat.menus.dto.SimpleMenuDTO;
import com.sat.sisat.persistence.CrudServiceBean;
import com.sat.sisat.persistence.entity.GnTipoDocumento;
import com.sat.sisat.persistence.entity.RvDjvehicular;
import com.sat.sisat.vehicular.business.VehicularBoRemote;
import com.sat.sisat.vehicular.cns.VehicularCns;
import com.sat.sisat.vehicular.dto.HistoricoVehiculodjDTO;
import com.sat.sisat.vehicular.dto.RvVehiculoDTO;

@ManagedBean
@ViewScoped
public class HistoricoVehiculoDJManaged extends BaseManaged {

	private static final long serialVersionUID = -5314539332591895079L;

	@EJB
	VehicularBoRemote vehicularBo;
	
	@EJB
	DeterminacionVehicularBoRemote deterVehicBo;
	
	@EJB
	MenuBoRemote menuBo;
	
	RvDjvehicular djvehicular = new RvDjvehicular();
	private RvVehiculoDTO vehiculoDJ = new RvVehiculoDTO();
	private List<HistoricoVehiculodjDTO> lstDjvs = new ArrayList<HistoricoVehiculodjDTO>();
	private List<HistoricoVehiculodjDTO> lstDjvsTodo = new ArrayList<HistoricoVehiculodjDTO>();
	HistoricoVehiculodjDTO selectedHistorico;
	private HistoricoVehiculodjDTO currentItem;
	private int anioCrear;
	
	// hparedes:ini
	private Integer djvehicular_id;
	private String djvehicular_nro;
	private Integer anio_decla;
	private Integer anio_afec;
	private int motivoDeclaracionDescargo_id;
	// hparedes:fin
	
	private String tipoDocumento;
	private List<SelectItem> lstTipoDocumento;
	private HashMap<String, Integer> mapGnTipodocumento = new HashMap<String, Integer>();
	private String nroDocumento;
	private Date fechaDocumento;
	private String observacion;
	
	private Integer personaId;
	
	private List<DeudaValoresDTO> listarValorCoactiva = new ArrayList<DeudaValoresDTO>();
	private Boolean conValor;
	
	// INICIO PERMISOS PARA EL MODULO -=CRAMIREZ=-
		private List<SimpleMenuDTO> listPermisosSubmenu = new ArrayList<SimpleMenuDTO>();
		private boolean permisoDescargarDJ;
		private boolean permisoListarInformacion;
		private boolean permisoActualizarDJ;
		private boolean permisoDeterminar;
		private boolean permisoImprimirDeterminacion;
		private boolean permisoImprimirLiquidacion;
		private boolean permisoImprimirDJ;
		private boolean permisoCopiaDJ;
	// FIN PERMISOS PARA EL MODULO
	
	public HistoricoVehiculoDJManaged() {
		getSessionManaged().setLinkRegresar("/sisat/vehicular/buscarvehicular.xhtml");
	}

	@PostConstruct
	public void init() {
		permisosMenu();
		try {
			Object obj = getSessionMap().get("hitoricovehiculodj.djvId");
			personaId = getSessionManaged().getContribuyente().getPersonaId();
			if (obj != null) {
				int djvId = (Integer) obj;

				djvehicular = vehicularBo.findDjVehicularById(djvId);
				vehiculoDJ = vehicularBo.findVehiculoDTOById(djvehicular
						.getVehiculoId());

				System.out.println("+++++++++");
				System.out.println(getSessionManaged().getContribuyente().getPersonaId());
				System.out.println(vehiculoDJ.getPlaca());
				lstDjvs = vehicularBo.findHistoricoVehiculoDj(getSessionManaged().getContribuyente().getPersonaId(), vehiculoDJ.getPlaca());
				cargarTodasDJ();

			}

			lstTipoDocumento = new ArrayList<SelectItem>();

			List<GnTipoDocumento> list = vehicularBo.obtenerTipoDocumentos();
			lstTipoDocumento = new ArrayList<SelectItem>();

			for (GnTipoDocumento it : list) {
				lstTipoDocumento.add(new SelectItem(it.getDescripcion(), String
						.valueOf(it.getTipoDocumentoId())));
				mapGnTipodocumento.put(it.getDescripcion(),
						it.getTipoDocumentoId());
			}
			fechaDocumento = DateUtil.getCurrentDate();
			
			

		} catch (SisatException e) {
			WebMessages.messageError(e.getMessage());
		}
	}
	
	public void permisosMenu() {	
		try {
			int submenuId = Constante.DECLARACION_JURADA_VEHICULAR;
			
			int permisoDescargarDJId = Constante.DESCARGAR_DJ;
			int permisoListarInformacionId = Constante.LISTAR_INFORMACION;
			int permisoActualizarDJId = Constante.ACTUALIZAR_DJ;
			int permisoDeterminarId = Constante.DETERMINAR;
			int permisoImprimirDeterminacionId = Constante.IMPRIMIR_DETERMINACION;
			int permisoImprimirLiquidacionId = Constante.IMPRIMIR_LIQUIDACION;
			int permisoImprimirDJId = Constante.IMPRIMIR_DJ;
			int permisoCopiaDJId = Constante.COPIAR_DJ;
			
			permisoDescargarDJ = false;
			permisoListarInformacion = false;
			permisoActualizarDJ = false;
			permisoDeterminar = false;
			permisoImprimirDeterminacion = false;
			permisoImprimirLiquidacion = false;
			permisoImprimirDJ = false;
			permisoCopiaDJ = false;
			
			listPermisosSubmenu  = menuBo.getAccesosSubmenuUsuario( getSessionManaged().getUsuarioLogIn().getUsuarioId() , submenuId);
			
			Iterator<SimpleMenuDTO> menuIterar = listPermisosSubmenu.iterator();
			while (menuIterar.hasNext()) {
				SimpleMenuDTO lsm = menuIterar.next();
				if(lsm.getItemId() == permisoDescargarDJId) {
					permisoDescargarDJ = true;
				}
				if(lsm.getItemId() == permisoListarInformacionId) {
					permisoListarInformacion = true;
				}
				if(lsm.getItemId() == permisoActualizarDJId) {
					permisoActualizarDJ = true;
				}
				if(lsm.getItemId() == permisoDeterminarId) {
					permisoDeterminar = true;
				}
				if(lsm.getItemId() == permisoImprimirDeterminacionId) {
					permisoImprimirDeterminacion = true;
				}
				if(lsm.getItemId() == permisoImprimirLiquidacionId) {
					permisoImprimirLiquidacion = true;
				}
				if(lsm.getItemId() == permisoImprimirDJId) {
					permisoImprimirDJ = true;
				}
				if(lsm.getItemId() == permisoCopiaDJId) {
					permisoCopiaDJ = true;
				}
			}
			
		} catch (SisatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void descargo(ActionEvent ev) {
		getSessionMap().put("descargovehicular.djvId", djvehicular.getDjvehicularId());
		getSessionMap().put("descargovehicular.accion", VehicularCns.ACCION_DESCARGOV_NEW);
		sendRedirectPrincipalListener();
	}

	public void verDjvehicular(ActionEvent ev) {
		try {
			UIComponent comp = ev.getComponent().getParent().getParent().getParent();
			if (comp != null) {
				UIData uiData = (UIData) comp;
				HistoricoVehiculodjDTO hv = (HistoricoVehiculodjDTO) uiData.getRowData();

				if(hv.getMotivoDeclaId()==Constante.MOTIVO_DECLARACION_DESCARGO.intValue()){
					getSessionMap().put("descargovehicular.djvId", hv.getDjVehicularId());
					getSessionMap().put("descargovehicular.accion", VehicularCns.ACCION_DESCARGOV_VIEW);
					getSessionManaged().setPage("/sisat/vehicular/descargovehicularlectura.xhtml");
				}else{
					getSessionMap().put("nuevadjregistro.djvId", hv.getDjVehicularId());
					getSessionMap().put("accion", VehicularCns.VIEW_DJ);
					getSessionManaged().setPage("/sisat/vehicular/nuevadjregistrolectura.xhtml");
					
				}
				sendRedirectPrincipalListener();
			}
		} catch (Exception ex) {
			String msg = "Ha ocurrido un error, debe cerrar e ingresar nuevamente";
			System.out.println(msg + ex);
		}
	}

//	public void actualizarDjv() {
//		try {
//			currentItem=(HistoricoVehiculodjDTO) getSessionMap().get("historicoVehiculo");
//			
//				int newDjvId = vehicularBo.duplicarDjvActualizar(currentItem.getDjVehicularId(), getSessionManaged().getUsuarioLogIn().getUsuarioId());
//				if (newDjvId != 0) {
//					getSessionMap().put("nuevadjregistro.djvId", newDjvId);
//					getSessionMap().put("accion",VehicularCns.PENDIENTE_ACTUALIZACION);
//					sendRedirectPrincipalListener();
//				}
//		} catch (Exception ex) {
//			System.out.println("ERROR: " + ex);
//			// TODO : controlar excepción
//		}
//	}
/**
 * 
 *Comentar o eliminar actualizarDjv(ActionEvent ev) para las validaciones de las determinaciones con valores emitidos. y descomentar actualizarDjv().(Líneas arriba)
 */
	public void actualizarDjv(ActionEvent ev) {
		try {
			UIComponent comp = ev.getComponent().getParent().getParent().getParent();
			if (comp != null) {
				UIData uiData = (UIData) comp;
				HistoricoVehiculodjDTO hv = (HistoricoVehiculodjDTO) uiData.getRowData();
				int newDjvId = vehicularBo.duplicarDjvActualizar(hv.getDjVehicularId(), getSessionManaged().getUsuarioLogIn().getUsuarioId());
				if (newDjvId != 0) {
					getSessionMap().put("nuevadjregistro.djvId", newDjvId);
					getSessionMap().put("accion",VehicularCns.PENDIENTE_ACTUALIZACION);
					sendRedirectPrincipalListener();
				}
			}
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
			// TODO : controlar excepción
		}
	}
	public void determinar(ActionEvent ev) {
		try {
			UIComponent comp = ev.getComponent().getParent().getParent().getParent();
			if (comp != null) {
				UIData uiData = (UIData) comp;
				HistoricoVehiculodjDTO hv = (HistoricoVehiculodjDTO) uiData.getRowData();
//				Cod. anterior: //inicio
//				boolean deterSave = deterVehicBo.generarDeterminacion(hv.getDjVehicularId(), getSessionManaged().getUsuarioLogIn().getUsuarioId(), getSessionManaged().getTerminalLogIn());
//				if (deterSave) {
//					hv.setDeterminado(true);
//					addInfoMessage(getMsg("dt.determinadocorrecto"));
//				} else {
//					addErrorMessage(getMsg("dt.errorgenerardeter"));
//				}
//				Cod. anterior: //fin
				int deterSave = deterVehicBo.generarDeterminacion(hv.getDjVehicularId(), getSessionManaged().getUsuarioLogIn().getUsuarioId(), getSessionManaged().getTerminalLogIn());
				if (deterSave==1) {
					hv.setDeterminado(true);
					addInfoMessage(getMsg("dt.determinadocorrecto"));
				} else if (deterSave==0){
					addErrorMessage(getMsg("dt.errorgenerardeter"));
				}else if (deterSave==2){
					addInfoMessage("Tiene valores,se genera la deuda por la diferencia, mantiene la deuda (con Valor) activa");
				}else if (deterSave==3){
					addInfoMessage("Tiene valores y la determinacion previa (sin Valor) posee pagos,se genera la deuda por la diferencia, mantiene la deuda (con Valor) activa");
				}else if (deterSave==4){
					addInfoMessage("Tiene valores,no existe diferencia en el monto de la B.I. por lo tanto no genera deuda, mantiene la deuda (con Valor) activa");
				}else if (deterSave==5){
					addInfoMessage("Tiene valores,la diferencia es menor por lo tanto no genera deuda, mantiene la deuda (con Valor) activa.Reportado al Dpto. de Control y Cobranza de la Deuda");
				}
			}
		} catch (DeterminacionVehicularException ex) {
			addErrorMessage(ex.getMessage());
		} catch (SisatException ex) {
			addErrorMessage(ex.getMessage());
		} catch (Exception ex) {
			addErrorMessage(ex.getMessage());
		}
	}

	public void seleccionarRegistro(ActionEvent ev) {
		try {
			UIComponent comp = ev.getComponent().getParent().getParent().getParent();
			if (comp != null) {
				UIData uiData = (UIData) comp;
				selectedHistorico = (HistoricoVehiculodjDTO) uiData.getRowData();
			}
		} catch (Exception ex) {

		}
	}

	public void copiarDjv() {
		try {
			boolean save = vehicularBo.copiarDjvAOtroAnio(selectedHistorico.getDjVehicularId(), anioCrear, getSessionManaged().getContribuyente().getPersonaId(), getSessionManaged().getUsuarioLogIn().getUsuarioId(), getSessionManaged().getTerminalLogIn());
			if(save){
				lstDjvs = vehicularBo.findHistoricoVehiculoDj(getSessionManaged().getContribuyente().getPersonaId(), vehiculoDJ.getPlaca());
				addInfoMessage("Copia realizada con éxito");	
			}else{
				addErrorMessage("No ha sido posible realizar la copia");
			}
		}catch(SisatException ex){
			addErrorMessage(ex.getMessage());
		} catch (Exception ex) {
			addErrorMessage("No ha sido posible realizar la copia");
		}
	}
	
	//hparedes:ini 06/07/2012
	public void imprimirDeterminacion() {
		java.sql.Connection connection=null;
		try {
			CrudServiceBean connj=CrudServiceBean.getInstance();
			connection=connj.connectJasper();
			 
			String path_context=FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
		     String path_report=path_context+"/sisat/reportes/";
		     String path_imagen=path_context+"/sisat/reportes/imagen/";
		     
			HashMap<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("djvehicular_id", djvehicular_id);
			parameters.put("ruta_image", SATWEBParameterFactory.getPathReporteImagenes());    
			parameters.put("responsable", getSessionManaged().getUsuarioLogIn().getNombreUsuario());
			JasperPrint jasperPrint = JasperFillManager.fillReport((SATWEBParameterFactory.getPathReporte()+"DeterminacionIV.jasper"), parameters, connection);
	            
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, output);
	               
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition","attachment;filename=DeterminacionIVReport.pdf");
			response.setContentLength(output.size());
			ServletOutputStream servletOutputStream = response.getOutputStream();
			servletOutputStream.write(output.toByteArray(), 0, output.size());
			servletOutputStream.flush();
			servletOutputStream.close();
			FacesContext.getCurrentInstance().responseComplete();
				
		}catch (Exception jre) {
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
	
	public void imprimirHojaLiquidacion() {
		java.sql.Connection connection=null;
		try {
			CrudServiceBean connj=CrudServiceBean.getInstance();
			 connection=connj.connectJasper();
			 
			String path_context=FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
		     String path_report=path_context+"/sisat/reportes/";
		     String path_imagen=path_context+"/sisat/reportes/imagen/";
		     
			HashMap<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("djvehicular_id", djvehicular_id);
			parameters.put("ruta_image", SATWEBParameterFactory.getPathReporteImagenes()) ;
			parameters.put("responsable", getSessionManaged().getUsuarioLogIn().getNombreUsuario());
			parameters.put("SUBREPORT_DIR", SATWEBParameterFactory.getPathReporte()) ;
			JasperPrint jasperPrint = JasperFillManager.fillReport((SATWEBParameterFactory.getPathReporte()+"HojaLiquidacionIV.jasper"), parameters, connection);
            
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, output);
               
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition","attachment;filename=DeterminacionIVReport.pdf");
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
	
	public void imprimirDJ() {
		java.sql.Connection connection=null;
		try {
			CrudServiceBean connj=CrudServiceBean.getInstance();
			connection=connj.connectJasper();
			 
			String path_context=FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
		    String path_report=path_context+"/sisat/reportes/";
		    String path_imagen=path_context+"/sisat/reportes/imagen/";
		     
			HashMap<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("djvehicular_id", djvehicular_id);
			if(anio_afec==null || anio_afec==0 )
				parameters.put("anio", anio_decla);
			else
				parameters.put("anio", anio_afec);
			parameters.put("SUBREPORT_DIR", SATWEBParameterFactory.getPathReporte()) ;
			parameters.put("ruta_image", SATWEBParameterFactory.getPathReporteImagenes()) ;
			parameters.put("responsable", getSessionManaged().getUsuarioLogIn().getNombreUsuario());
			JasperPrint jasperPrint;
			if (motivoDeclaracionDescargo_id != 0
					&& motivoDeclaracionDescargo_id == Constante.MOTIVO_DECLARACION_DESCARGO)
				jasperPrint = JasperFillManager
						.fillReport((SATWEBParameterFactory.getPathReporte() + "DJVehicular_descargo.jasper"),
								parameters,
								connection);
			else
				jasperPrint = JasperFillManager
						.fillReport((SATWEBParameterFactory.getPathReporte() + "DJVehicular.jasper"),
								parameters,
								connection);
			
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, output);
              
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition","attachment;filename=DJVehicularReport.pdf");
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
	//fin: hparedes

	public void seleccionaDatos(){
		try{
			if(currentItem != null){
							
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public void eliminarDJ(){
		try {		
			
			//Eliminar DJ									
			String glosa ="Doc: "+ tipoDocumento + ", No: "+nroDocumento+", F: "+DateUtil.convertDateToString(fechaDocumento)+", Obsv: "+observacion+". Usuario: "+currentItem.getUsuario();
			currentItem.setGlosa(glosa);
			
			vehicularBo.eliminarDJVehicular(currentItem);
			
			//Refrescar data
			cargarTodasDJ();			
			
			
		} catch (SisatException e) {			
			WebMessages.messageError(e.getMessage());
		}
	}
	public void cargarTodasDJ(){	
		
		try {
			lstDjvsTodo = vehicularBo.findHistoricoVehiculoDjTodo(
					personaId,
					vehiculoDJ.getPlaca());
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		
		
	}
	
	public void validar(ActionEvent ev) {
		try {
			UIComponent comp = ev.getComponent().getParent().getParent().getParent();
			if (comp != null) {
				UIData uiData = (UIData) comp;
				HistoricoVehiculodjDTO hv = (HistoricoVehiculodjDTO) uiData.getRowData();
				
				Integer codPersona = getSessionManaged().getContribuyente().getPersonaId();
				String anio=hv.getAnioDecla();
				Integer dj=hv.getDjVehicularId();
				
				listarValorCoactiva=vehicularBo.getValorVehicular(codPersona,anio,dj);
				valida();
				 if (this.conValor==Boolean.FALSE){
					 getSessionMap().put("historicoVehiculo", hv);
//					 actualizarDjv(hv);
					 
				 }else  if (this.conValor==Boolean.TRUE){
					 WebMessages.messageError("No se puede realizar el cálculo del Impuesto Vehicular para deudas asociadas a Valores, porfavor informe a las Áreas correspondientes.");
				 }
				
				
			}
		} catch (Exception ex) {
			//System.out.println("ERROR: " + ex);
			// TODO : controlar excepción
			ex.printStackTrace();
			WebMessages.messageFatal(ex);
		}
	}
	
	public Boolean valida(){
		try{
			if (listarValorCoactiva!=null && listarValorCoactiva.size()>0){
				setConValor(true);
			}else{
				setConValor(false);
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		return conValor;
	}
	
	public List<HistoricoVehiculodjDTO> getLstDjvs() {
		return lstDjvs;
	}

	public RvVehiculoDTO getVehiculoDJ() {
		return vehiculoDJ;
	}

	public void setVehiculoDJ(RvVehiculoDTO vehiculoDJ) {
		this.vehiculoDJ = vehiculoDJ;
	}

	public HistoricoVehiculodjDTO getSelectedHistorico() {
		return selectedHistorico;
	}

	public int getAnioCrear() {
		return anioCrear;
	}

	public void setAnioCrear(int anioCrear) {
		this.anioCrear = anioCrear;
	}

	public Integer getDjvehicular_id() {
		return djvehicular_id;
	}

	public void setDjvehicular_id(Integer djvehicular_id) {
		this.djvehicular_id = djvehicular_id;
	}
	
	public Integer getAnio_decla() {
		return anio_decla;
	}

	public void setAnio_decla(Integer anio_decla) {
		this.anio_decla = anio_decla;
	}

	public RvDjvehicular getDjvehicular() {
		return djvehicular;
	}

	public void setDjvehicular(RvDjvehicular djvehicular) {
		this.djvehicular = djvehicular;
	}

	public Integer getAnio_afec() {
		return anio_afec;
	}

	public void setAnio_afec(Integer anio_afec) {
		this.anio_afec = anio_afec;
	}

	public int getMotivoDeclaracionDescargo_id() {
		return motivoDeclaracionDescargo_id;
	}

	public void setMotivoDeclaracionDescargo_id(int motivoDeclaracionDescargo_id) {
		this.motivoDeclaracionDescargo_id = motivoDeclaracionDescargo_id;
	}

	public String getDjvehicular_nro() {
		return djvehicular_nro;
	}

	public void setDjvehicular_nro(String djvehicular_nro) {
		this.djvehicular_nro = djvehicular_nro;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public List<SelectItem> getLstTipoDocumento() {
		return lstTipoDocumento;
	}

	public void setLstTipoDocumento(List<SelectItem> lstTipoDocumento) {
		this.lstTipoDocumento = lstTipoDocumento;
	}

	public HashMap<String, Integer> getMapGnTipodocumento() {
		return mapGnTipodocumento;
	}

	public void setMapGnTipodocumento(HashMap<String, Integer> mapGnTipodocumento) {
		this.mapGnTipodocumento = mapGnTipodocumento;
	}

	public String getNroDocumento() {
		return nroDocumento;
	}

	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}

	public Date getFechaDocumento() {
		return fechaDocumento;
	}

	public void setFechaDocumento(Date fechaDocumento) {
		this.fechaDocumento = fechaDocumento;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public HistoricoVehiculodjDTO getCurrentItem() {
		return currentItem;
	}

	public void setCurrentItem(HistoricoVehiculodjDTO currentItem) {
		this.currentItem = currentItem;
	}

	public List<HistoricoVehiculodjDTO> getLstDjvsTodo() {
		return lstDjvsTodo;
	}

	public void setLstDjvsTodo(List<HistoricoVehiculodjDTO> lstDjvsTodo) {
		this.lstDjvsTodo = lstDjvsTodo;
	}

	public Integer getPersonaId() {
		return personaId;
	}

	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
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

	public boolean isPermisoDescargarDJ() {
		return permisoDescargarDJ;
	}

	public void setPermisoDescargarDJ(boolean permisoDescargarDJ) {
		this.permisoDescargarDJ = permisoDescargarDJ;
	}

	public boolean isPermisoListarInformacion() {
		return permisoListarInformacion;
	}

	public void setPermisoListarInformacion(boolean permisoListarInformacion) {
		this.permisoListarInformacion = permisoListarInformacion;
	}

	public boolean isPermisoActualizarDJ() {
		return permisoActualizarDJ;
	}

	public void setPermisoActualizarDJ(boolean permisoActualizarDJ) {
		this.permisoActualizarDJ = permisoActualizarDJ;
	}

	public boolean isPermisoDeterminar() {
		return permisoDeterminar;
	}

	public void setPermisoDeterminar(boolean permisoDeterminar) {
		this.permisoDeterminar = permisoDeterminar;
	}

	public boolean isPermisoImprimirDeterminacion() {
		return permisoImprimirDeterminacion;
	}

	public void setPermisoImprimirDeterminacion(boolean permisoImprimirDeterminacion) {
		this.permisoImprimirDeterminacion = permisoImprimirDeterminacion;
	}

	public boolean isPermisoImprimirLiquidacion() {
		return permisoImprimirLiquidacion;
	}

	public void setPermisoImprimirLiquidacion(boolean permisoImprimirLiquidacion) {
		this.permisoImprimirLiquidacion = permisoImprimirLiquidacion;
	}

	public boolean isPermisoImprimirDJ() {
		return permisoImprimirDJ;
	}

	public void setPermisoImprimirDJ(boolean permisoImprimirDJ) {
		this.permisoImprimirDJ = permisoImprimirDJ;
	}

	public boolean isPermisoCopiaDJ() {
		return permisoCopiaDJ;
	}

	public void setPermisoCopiaDJ(boolean permisoCopiaDJ) {
		this.permisoCopiaDJ = permisoCopiaDJ;
	}
	
}