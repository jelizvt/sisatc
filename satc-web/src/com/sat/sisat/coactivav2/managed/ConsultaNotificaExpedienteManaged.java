package com.sat.sisat.coactivav2.managed;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.sat.sisat.cobranzacoactiva.business.CobranzaCoactivaBoRemote;
import com.sat.sisat.cobranzacoactiva.dto.ExpedienteCoactivo;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.controlycobranza.business.ControlyCobranzaBoRemote;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.menus.business.MenuBoRemote;
import com.sat.sisat.menus.dto.SimpleMenuDTO;
import com.sat.sisat.persistence.entity.NoMotivoNotificacion;
import com.sat.sisat.persistence.entity.NoNotificador;

@ManagedBean
@ViewScoped
public class ConsultaNotificaExpedienteManaged extends BaseManaged {
	/**
	 * 
	 */
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -995424274070457104L;
	/**
	 * 
	 */
	
	@EJB
	CobranzaCoactivaBoRemote cobranzaCoactivaBo;
	@EJB
	ControlyCobranzaBoRemote controlycobranzaBo;
	
	@EJB
	MenuBoRemote menuBo;
	
	//Identifica si TRUE:se desea recuperar solo la última REC generada --FALSE: Todas las REC.
	private Boolean ultimaRec=Boolean.TRUE; 
	
	private String nroCartera;
	private String nroExpediente;
	private String nroPapeleta;
	private Integer contribuyenteId;
	
	private List<ExpedienteCoactivo> records = new ArrayList<ExpedienteCoactivo>();
	private List<NoMotivoNotificacion> listaFormaNotificacion = new ArrayList<NoMotivoNotificacion>();
	private List<NoNotificador> listaNotificador = new ArrayList<NoNotificador>();
	
	private ExpedienteCoactivo selExpediente=new ExpedienteCoactivo();
	
	private Integer formaNotificacionId;
	private java.util.Date fechaNotificacion;
	private Integer notificadorId;
	
	// INICIO PERMISOS PARA EL MODULO -=CRAMIREZ=-
	 	private List<SimpleMenuDTO> listPermisosSubmenu = new ArrayList<SimpleMenuDTO>();
	 	private boolean permisoBuscar;	 	
	 	private boolean permisoAgregarRegistrar;
	 	private boolean permisoModificarActualizar;
	 // FIN PERMISOS PARA EL MODULO
	
	@PostConstruct
	public void init() throws Exception {
		permisosMenu();
		try {
			listaNotificador=controlycobranzaBo.getAllNotificador();
			listaFormaNotificacion=controlycobranzaBo.getAlNoMotivoNotificacion(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void permisosMenu() {	
		try {
			int submenuId = Constante.NOTIFICACION_DE_EXPEDIENTES;
		 	
			int permisoBuscarId = Constante.BUSCAR;
			int permisoAgregarRegistrarId = Constante.AGREGAR_REGISTRAR;
			int permisoModificarActualizarId = Constante.MODIFICAR_ACTUALIZAR;		
			
			permisoBuscar = false;
			permisoAgregarRegistrar = false;
			permisoModificarActualizar = false;
			
			listPermisosSubmenu  = menuBo.getAccesosSubmenuUsuario( getSessionManaged().getUsuarioLogIn().getUsuarioId() , submenuId);
			
			Iterator<SimpleMenuDTO> menuIterar = listPermisosSubmenu.iterator();
			while (menuIterar.hasNext()) {
				SimpleMenuDTO lsm = menuIterar.next();
				if(lsm.getItemId() == permisoBuscarId) {
					permisoBuscar = true;
				}
				if(lsm.getItemId() == permisoAgregarRegistrarId) {
					permisoAgregarRegistrar = true;
				}
				if(lsm.getItemId() == permisoModificarActualizarId) {
					permisoModificarActualizar = true;
				}
			}
		} catch (SisatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	public void buscar(){
		try{
			
			//Verificamos si se desea consultar solo la última REC Generada o otodas.
			Integer ultimaRec=1;			
			if  (this.getUltimaRec()==Boolean.FALSE)
				ultimaRec=0;		

			records=cobranzaCoactivaBo.notificacionConsultaExpediente(nroCartera,nroExpediente,nroPapeleta,contribuyenteId,ultimaRec);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	//Agregado: Omar:
	//Actualiza las notificaciones.
	
		public void actualizarNotificacion(){
			try {
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
				String fechaConFormato = sdf.format(fechaNotificacion);
				
				//Verficamos que la fecha de notificación sea posterior a la fecha de emisión de la REC.
				Date fechaemision;
				fechaemision=selExpediente.getFechaemision();
				
				
				if (fechaNotificacion== null)
				{
					WebMessages.messageError("La fecha de Notificación "+fechaConFormato+" no es válida." );
					return;
				}
				else 
					if (fechaemision== null)
					{
						WebMessages.messageError("La fecha de Emisión de la REC no es válida." );
						return;
					}
				
				//
				if (fechaNotificacion.compareTo(fechaemision)<=0 )
				{				
					WebMessages.messageError("La fecha de Notificación "+fechaConFormato+" debe ser posterior a la fecha de Emisión. " );
				}
					
				else
					{
						//Actualizamos los datos de Notificación.
						Integer result=cobranzaCoactivaBo.actualizarNotificacion(selExpediente.getRecId(), formaNotificacionId, notificadorId, fechaNotificacion, getSessionManaged().getUsuarioLogIn().getUsuarioId(),getSessionManaged().getTerminalLogIn());
						
						if(result==Constante.RESULT_SUCCESS)
							WebMessages.messageInfo("Los cambios de Notificación han sido registrados con éxito.");
						else				
							WebMessages.messageError("Ocurrió un problema durante la Actualización de datos.");						
						
						buscar();
					}		

				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public void registraNotificacion(){
			try {
				//Cambiado por Omar:
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
				String fechaConFormato = sdf.format(fechaNotificacion);
				
				//Verficamos que la fecha de notificación sea posterior a la fecha de emisión de la REC.
				Date fechaemision;
				fechaemision=selExpediente.getFechaemision();			
						
				
				if (fechaNotificacion.compareTo(fechaemision)<=0 )
				{				
					WebMessages.messageError("La fecha de Notificación "+fechaConFormato+" debe ser posterior a la fechad e Emisión. " );
				}
					
				else
				{				
					Integer result=cobranzaCoactivaBo.registraNotificacionExpediente(formaNotificacionId, notificadorId, fechaNotificacion, selExpediente.getRecId(),getSessionManaged().getUsuarioLogIn().getUsuarioId(),getSessionManaged().getTerminalLogIn());
					if(result==Constante.RESULT_SUCCESS){
						cobranzaCoactivaBo.registraCostasExpediente(selExpediente.getExpedienteId(),selExpediente.getRecId(),getSessionManaged().getTerminalLogIn(),getSessionManaged().getUsuarioLogIn().getUsuarioId());					
						
						if(result==Constante.RESULT_SUCCESS)
							WebMessages.messageInfo("Se ha registrado la NOtificación y Generado Gastos Correctamente.");
						else				
							WebMessages.messageError("Ocurrió un problema durante la Registro de datos.");
						
					}
					buscar();
					
				}
				
				/*
				if(DateUtil.esFechaValida(fechaNotificacion)){
					Integer result=cobranzaCoactivaBo.registraNotificacionExpediente(formaNotificacionId, notificadorId, fechaNotificacion, selExpediente.getRecId(),getSessionManaged().getUsuarioLogIn().getUsuarioId(),getSessionManaged().getTerminalLogIn());
					if(result==Constante.RESULT_SUCCESS){
						cobranzaCoactivaBo.registraCostasExpediente(selExpediente.getExpedienteId(),selExpediente.getRecId(),getSessionManaged().getTerminalLogIn(),getSessionManaged().getUsuarioLogIn().getUsuarioId());
					}
					buscar();
				}else{
					WebMessages.messageError("La fecha de notificacion ingresada no es valida");
				}
				*/
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	
	public void exportXls() {
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("Reporte");
		int rowIndex = 1;
		Row row1 = sheet.createRow(0);
		
		row1.createCell(0).setCellValue("item");
		row1.createCell(1).setCellValue("Expediente");
		row1.createCell(2).setCellValue("Nro REC");
		row1.createCell(3).setCellValue("Código");
		row1.createCell(4).setCellValue("Apellidos y Nombres");
		row1.createCell(5).setCellValue("Fecha Expediente");
		row1.createCell(6).setCellValue("Fecha Notificacion");
		row1.createCell(7).setCellValue("Notificador");
		row1.createCell(8).setCellValue("Forma Notificacion");
		row1.createCell(9).setCellValue("Registrado");

		for (ExpedienteCoactivo data : records) {
			Row row = sheet.createRow(rowIndex++);
			int columnIndex = 0;
			row.createCell(columnIndex++).setCellValue(rowIndex-1);
			row.createCell(columnIndex++).setCellValue(data.getNroExpediente());
			row.createCell(columnIndex++).setCellValue(data.getNroRec());
			row.createCell(columnIndex++).setCellValue(data.getPersonaId());
			row.createCell(columnIndex++).setCellValue(data.getApellidosNombresPersona());
			row.createCell(columnIndex++).setCellValue(data.getFechaExpediente());
			row.createCell(columnIndex++).setCellValue(data.getFechaNotificacion());
			row.createCell(columnIndex++).setCellValue(data.getApellidosNombresNotificador());
			row.createCell(columnIndex++).setCellValue(data.getFormaNotificacion());
			row.createCell(columnIndex++).setCellValue(data.getUsuario());
		}
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		externalContext.responseReset();
		externalContext.setResponseContentType("application/vnd.ms-excel");
		externalContext.setResponseHeader("Content-Disposition",
				"attachment;filename=reporte_notificacion_expediente.xls");
		try {
			workbook.write(externalContext.getResponseOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		context.responseComplete(); // Prevent JSF from performing navigation.
	}
	
	public CobranzaCoactivaBoRemote getCobranzaCoactivaBo() {
		return cobranzaCoactivaBo;
	}

	public void setCobranzaCoactivaBo(CobranzaCoactivaBoRemote cobranzaCoactivaBo) {
		this.cobranzaCoactivaBo = cobranzaCoactivaBo;
	}

	public String getNroCartera() {
		return nroCartera;
	}

	public void setNroCartera(String nroCartera) {
		this.nroCartera = nroCartera;
	}

	public String getNroExpediente() {
		return nroExpediente;
	}

	public void setNroExpediente(String nroExpediente) {
		this.nroExpediente = nroExpediente;
	}

	public String getNroPapeleta() {
		return nroPapeleta;
	}

	public void setNroPapeleta(String nroPapeleta) {
		this.nroPapeleta = nroPapeleta;
	}

	public Integer getContribuyenteId() {
		return contribuyenteId;
	}

	public void setContribuyenteId(Integer contribuyenteId) {
		this.contribuyenteId = contribuyenteId;
	}

	public List<ExpedienteCoactivo> getRecords() {
		return records;
	}

	public void setRecords(List<ExpedienteCoactivo> records) {
		this.records = records;
	}
	
	public ExpedienteCoactivo getSelExpediente() {
		return selExpediente;
	}

	public void setSelExpediente(ExpedienteCoactivo selExpediente) {
		this.selExpediente = selExpediente;
	}
	public Integer getFormaNotificacionId() {
		return formaNotificacionId;
	}

	public void setFormaNotificacionId(Integer formaNotificacionId) {
		this.formaNotificacionId = formaNotificacionId;
	}

	public java.util.Date getFechaNotificacion() {
		return fechaNotificacion;
	}

	public void setFechaNotificacion(java.util.Date fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}

	public Integer getNotificadorId() {
		return notificadorId;
	}

	public void setNotificadorId(Integer notificadorId) {
		this.notificadorId = notificadorId;
	}

	public List<NoMotivoNotificacion> getListaFormaNotificacion() {
		return listaFormaNotificacion;
	}

	public void setListaFormaNotificacion(
			List<NoMotivoNotificacion> listaFormaNotificacion) {
		this.listaFormaNotificacion = listaFormaNotificacion;
	}

	public List<NoNotificador> getListaNotificador() {
		return listaNotificador;
	}

	public void setListaNotificador(List<NoNotificador> listaNotificador) {
		this.listaNotificador = listaNotificador;
	}

	public Boolean getUltimaRec() {
		return ultimaRec;
	}

	public void setUltimaRec(Boolean ultimaRec) {
		this.ultimaRec = ultimaRec;
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

	public boolean isPermisoAgregarRegistrar() {
		return permisoAgregarRegistrar;
	}

	public void setPermisoAgregarRegistrar(boolean permisoAgregarRegistrar) {
		this.permisoAgregarRegistrar = permisoAgregarRegistrar;
	}

	public boolean isPermisoModificarActualizar() {
		return permisoModificarActualizar;
	}

	public void setPermisoModificarActualizar(boolean permisoModificarActualizar) {
		this.permisoModificarActualizar = permisoModificarActualizar;
	}
	
}