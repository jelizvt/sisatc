package com.sat.sisat.controlycobranza.managed;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.richfaces.component.html.HtmlComboBox;

import com.sat.sisat.common.business.GeneralBoRemote;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.SATWEBParameterFactory;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.controlycobranza.business.ControlyCobranzaBoRemote;
import com.sat.sisat.controlycobranza.dto.FindCcActo;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.menus.business.MenuBoRemote;
import com.sat.sisat.menus.dto.SimpleMenuDTO;
import com.sat.sisat.persistence.CrudServiceBean;
import com.sat.sisat.persistence.entity.CcReclamo;
import com.sat.sisat.persistence.entity.NoNotificacion;
import com.sat.sisat.persona.business.PersonaBoRemote;

@ManagedBean
@ViewScoped
public class ReclamarActoLoteOrdinarioManaged extends BaseManaged {
	@EJB
	PersonaBoRemote personaBo;
	@EJB
	GeneralBoRemote generalBo;
	@EJB
	ControlyCobranzaBoRemote controlycobranzaBo;
	@EJB
	MenuBoRemote menuBo;
	
	private String nroValor;
	private Integer personaId;
	private Integer estadoReclamoId;
	
	private FindCcActo findCcActoItem= new FindCcActo();
	private List<FindCcActo> lstFindLote;
	
	//Campos de registro de documento de expediente de reclamacion 
	private java.util.Date fechaReclamacion;
	private String cmbValueEstadoReclamacion;
	private String nroExpediente;
	
	// INICIO PERMISOS PARA EL MODULO -=CRAMIREZ=-
 		private List<SimpleMenuDTO> listPermisosSubmenu = new ArrayList<SimpleMenuDTO>();
 		private boolean permisoBuscar;
 		private boolean permisoReclamar;
 	// FIN PERMISOS PARA EL MODULO
	
	@PostConstruct
	public void init() {
		permisosMenu();
		try{
			limpiarCamposReclamo();
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	public void permisosMenu() {	
		try {
			int submenuId = Constante.RECLAMOS;
			
			int permisoBuscarId = Constante.BUSCAR;
			int permisoReclamarId = Constante.RECLAMAR;
			
			permisoBuscar = false;
			permisoReclamar = false;
			
			listPermisosSubmenu  = menuBo.getAccesosSubmenuUsuario( getSessionManaged().getUsuarioLogIn().getUsuarioId() , submenuId);
			
			Iterator<SimpleMenuDTO> menuIterar = listPermisosSubmenu.iterator();
			while (menuIterar.hasNext()) {
				SimpleMenuDTO lsm = menuIterar.next();
				if(lsm.getItemId() == permisoBuscarId) {
					permisoBuscar = true;
				}
				if(lsm.getItemId() == permisoReclamarId) {
					permisoReclamar = true;
				}
			}
		} catch (SisatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	public void buscar()throws Exception{
		try{
			lstFindLote=controlycobranzaBo.getAllFindCcActoReclamos(nroValor, personaId);
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	public void limpiar()throws Exception{
		try{
			setNroValor("");
			setPersonaId(null);
			lstFindLote=new LinkedList<FindCcActo>();
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	public void loadSeleccion(ValueChangeEvent event) {
		try {
			HtmlComboBox combo = (HtmlComboBox) event.getComponent();
			String value = combo.getValue().toString();
			if(value.equals("Reclamado")){
				setEstadoReclamoId(Constante.ESTADO_RECLAMO_RECLAMADO);
			}else if(value.equals("Resuelto")){
				setEstadoReclamoId(Constante.ESTADO_RECLAMO_RESUELTO);
			}
		}catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void reclamarActo()throws Exception{
		try{
			if(validar()){
				CcReclamo reclamo=new CcReclamo();
				reclamo.setReclamoId(findCcActoItem.getReclamoId());
				reclamo.setLoteId(findCcActoItem.getLoteId());
				reclamo.setActoId(findCcActoItem.getActoId());
				reclamo.setEstadoReclamoId(estadoReclamoId);
				reclamo.setNroDocumento(getNroExpediente());
				reclamo.setFechaReclamo(DateUtil.dateToSqlTimestamp(fechaReclamacion));
				reclamo.setEstado(Constante.ESTADO_ACTIVO);
				reclamo.setPersonaId(findCcActoItem.getPersonaId());
				controlycobranzaBo.create(reclamo);
				
				if(estadoReclamoId==Constante.ESTADO_RECLAMO_RECLAMADO){
					controlycobranzaBo.actualizarActo(Constante.ESTADO_ACTIVO,reclamo);
					controlycobranzaBo.actualizarDeuda(Constante.ESTADO_ACTIVO,reclamo);	
				}else{
					controlycobranzaBo.actualizarActo(Constante.ESTADO_INACTIVO,reclamo);
					controlycobranzaBo.actualizarDeuda(Constante.ESTADO_INACTIVO,reclamo);
				}
				
				limpiarCamposReclamo();
				lstFindLote=controlycobranzaBo.getAllFindCcActoReclamos(nroValor, personaId);
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	public void mostrarPanelReclamarActo(){
		limpiarCamposReclamo();
		if(findCcActoItem!=null&&findCcActoItem.getReclamoId()>0){
			setFechaReclamacion(findCcActoItem.getFechaReclamo());
			setCmbValueEstadoReclamacion(findCcActoItem.getEstadoReclamoId()==1?"Reclamado":"Resuelto");
			setEstadoReclamoId(findCcActoItem.getEstadoReclamoId());
			setNroExpediente(findCcActoItem.getNroDocumentoReclamo());
		}
	}
	
	public void generacionArchivosXml(){
		java.sql.Connection connection=null;
		try {
			
			CrudServiceBean connj=CrudServiceBean.getInstance();
			connection=connj.connectJasper();
			    
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			
			JasperPrint jasperPrint = JasperFillManager.fillReport((SATWEBParameterFactory.getPathReporte()+"reporte_estado_reclamos.jasper"),parameters,connection);
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
			    exporterXls.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "reporte_valores_reclamados_" +System.currentTimeMillis()+ ".xls");  
			    exporterXls.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, output);  
			    exporterXls.exportReport();

			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			response.setContentType("application/vnd.ms-excel");  
		    response.setHeader("Content-Disposition", "attachment;filename=" + "reporte_valores_reclamados_" +System.currentTimeMillis()+ ".xls");  
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
	
	public void limpiarCamposReclamo(){
		setFechaReclamacion(null);
		setCmbValueEstadoReclamacion("");
		setNroExpediente("");
	}
	
	public void salir(){
		limpiarCamposReclamo();
	}
	
	public boolean validar()throws Exception{
	    try{ 
	    	if(findCcActoItem!=null&&findCcActoItem.getActoId()>0&&
	    			fechaReclamacion!=null){
				if(findCcActoItem.getFechaEmision().compareTo(fechaReclamacion)>=0){
					addErrorMessage(getMsg("no.errorfechanotificacion"));
					return false;
				}
	    	}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
		return true;
	}
	
	public List<FindCcActo> getLstFindLote() {
		return lstFindLote;
	}

	public void setLstFindLote(List<FindCcActo> lstFindLote) {
		this.lstFindLote = lstFindLote;
	}

	public FindCcActo getFindCcActoItem() {
		return findCcActoItem;
	}

	public void setFindCcActoItem(FindCcActo findCcActoItem) {
		this.findCcActoItem = findCcActoItem;
	}

	public String getNroValor() {
		return nroValor;
	}
	public void setNroValor(String nroValor) {
		this.nroValor = nroValor;
	}
	public Integer getPersonaId() {
		return personaId;
	}
	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}
	
	public String getCmbValueEstadoReclamacion() {
		return cmbValueEstadoReclamacion;
	}
	public void setCmbValueEstadoReclamacion(String cmbValueEstadoReclamacion) {
		this.cmbValueEstadoReclamacion = cmbValueEstadoReclamacion;
	}

	public String getNroExpediente() {
		return nroExpediente;
	}
	public void setNroExpediente(String nroExpediente) {
		this.nroExpediente = nroExpediente;
	}
	
	public java.util.Date getFechaReclamacion() {
		return fechaReclamacion;
	}

	public void setFechaReclamacion(java.util.Date fechaReclamacion) {
		this.fechaReclamacion = fechaReclamacion;
	}
	
	public Integer getEstadoReclamoId() {
		return estadoReclamoId;
	}

	public void setEstadoReclamoId(Integer estadoReclamoId) {
		this.estadoReclamoId = estadoReclamoId;
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

	public boolean isPermisoReclamar() {
		return permisoReclamar;
	}

	public void setPermisoReclamar(boolean permisoReclamar) {
		this.permisoReclamar = permisoReclamar;
	}
	
}
