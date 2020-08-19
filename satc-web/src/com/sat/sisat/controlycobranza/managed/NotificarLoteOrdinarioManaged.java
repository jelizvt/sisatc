package com.sat.sisat.controlycobranza.managed;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.richfaces.component.html.HtmlComboBox;
import org.richfaces.component.html.HtmlInputText;

import com.sat.sisat.alfresco.RepositoryManager;
import com.sat.sisat.alfresco.util.Constantes;
import com.sat.sisat.common.business.GeneralBoRemote;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.controlycobranza.business.ControlyCobranzaBoRemote;
import com.sat.sisat.controlycobranza.dto.FindCcActo;
import com.sat.sisat.controlycobranza.dto.FindNotificacion;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.menus.business.MenuBoRemote;
import com.sat.sisat.menus.dto.SimpleMenuDTO;
import com.sat.sisat.notificaciones.LoadImageNotificacionesManager;
import com.sat.sisat.notificaciones.dto.NotificacionesDTO;
import com.sat.sisat.papeletas.ParameterLoader;
import com.sat.sisat.persistence.entity.NoDetalleMasivaDigiNotif;
import com.sat.sisat.persistence.entity.NoDetalleMasivaNotificacion;
import com.sat.sisat.persistence.entity.NoMasivaDigiNotif;
import com.sat.sisat.persistence.entity.NoMasivaNotificacion;
import com.sat.sisat.persistence.entity.NoMotivoNotificacion;
import com.sat.sisat.persistence.entity.NoNotificacion;
import com.sat.sisat.persistence.entity.NoNotificador;
import com.sat.sisat.persistence.entity.NoRelacionPersona;
import com.sat.sisat.persona.business.PersonaBoRemote;

@ManagedBean
@ViewScoped
public class NotificarLoteOrdinarioManaged extends BaseManaged {
	@EJB
	PersonaBoRemote personaBo;
	@EJB
	GeneralBoRemote generalBo;
	@EJB
	ControlyCobranzaBoRemote controlycobranzaBo;
	@EJB
	MenuBoRemote menuBo;
							
	private List<FindCcActo> lstFindLote;
	private FindCcActo findCcActoItem= new FindCcActo();
	private java.util.Date fechaNotificacion;
	private NoNotificacion notificacion;
	private String nroLote;
	private String nroActoRec;
	private Integer persona_id;
	private Integer tipoLote;
	private HtmlComboBox cmbNoRelacionPersona;
	private List<SelectItem> lstNoRelacionPersona=new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapNoRelacionPersona=new HashMap<String, Integer>();
	private String cmbValueNoRelacionPersona;
	private List<NoRelacionPersona> lNoRelacionPersona = null;
	private Integer noRelacionPersonaId;
	private HtmlComboBox cmbNoMotivoNotificacion;
	private List<SelectItem> lstNoMotivoNotificacion=new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapNoMotivoNotificacion=new HashMap<String, Integer>();
	private String cmbValueNoMotivoNotificacion;
	private List<NoRelacionPersona> lNoMotivoNotificacion = null;
	private Integer noMotivoNotificacionId;
	private Integer tipoAccion=Constante.TIPO_ACCION_NUEVO;
	private HtmlInputText txtarchivo;
	private List<NoMasivaNotificacion> records=null;
	private List<NoDetalleMasivaNotificacion> recordsItem=null;
	private List<FindNotificacion> recordsItemNotificacion=null;
	private NoMasivaNotificacion currentItem;
	private Boolean mostraDetalle=Boolean.FALSE;
	private Boolean mostraResultados=Boolean.FALSE;
	private Boolean mostraResultadosDigitalizacion=Boolean.FALSE;
	private Boolean mostraDetalleResulDigit=Boolean.FALSE;
	private ArrayList<NotificacionesDTO> listaNot;
	private NoMasivaDigiNotif noMasivaDigiNotif;
	private NoDetalleMasivaDigiNotif noDetalleMasivaDigiNot;
	private List<NoDetalleMasivaDigiNotif> listaDetalleMasivaDigiNot;
	private Boolean cargarImagenes=Boolean.FALSE;
	private String directorio=ParameterLoader.getParameter("directorio_notificaciones_digitalizados");
	private String cmbNotificador;
	private HtmlComboBox cmbHtmlNotificador;
	private List<SelectItem> lstSelectItemsNotificador=new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapNotificador=new HashMap<String, Integer>();
	private Integer notificadorId;
	private String codicionAdministrado;
	private HtmlComboBox cmbCondicionAdministrado;
	// INICIO PERMISOS PARA EL MODULO -=CRAMIREZ=-
 		private List<SimpleMenuDTO> listPermisosSubmenu = new ArrayList<SimpleMenuDTO>();
 		private boolean permisoBuscar;
 		private boolean permisoNotificar;
 	// FIN PERMISOS PARA EL MODULO
	 		
	public NotificarLoteOrdinarioManaged() {
		getSessionManaged().setLinkRegresar("/sisat/controlycobranza/notificarActoLoteOrdinario.xhtml");
	}
	@PostConstruct
	public void init() {
		permisosMenu();
		try{
			tipoAccion=Constante.TIPO_ACCION_NUEVO;
			notificacion = new NoNotificacion();
			
			//NoRelacionPersona
	        List<NoRelacionPersona> lsNoRelacionPersona=controlycobranzaBo.getAlNoRelacionPersona();
	       Iterator<NoRelacionPersona> it = lsNoRelacionPersona.iterator();  
	       lstNoRelacionPersona=new ArrayList<SelectItem>();
			 
	        while (it.hasNext()){
	        	NoRelacionPersona obj01 = it.next();  
	        	lstNoRelacionPersona.add(new SelectItem(obj01.getDescripcion(),String.valueOf(obj01.getRelacionNotificacionId())));  
	        	mapNoRelacionPersona.put(obj01.getDescripcion().trim(), obj01.getRelacionNotificacionId());
	        }
	        
	      //NoMotivoNotificacion
//	        List<NoMotivoNotificacion> lsNoMotivoNotificacion=controlycobranzaBo.getAlNoMotivoNotificacion();
//	       Iterator<NoMotivoNotificacion> it1 = lsNoMotivoNotificacion.iterator();  
//	       lstNoMotivoNotificacion=new ArrayList<SelectItem>();
//	        while (it1.hasNext()){
//	        	NoMotivoNotificacion obj01 = it1.next();  
//	        	lstNoMotivoNotificacion.add(new SelectItem(obj01.getDescripcion(),String.valueOf(obj01.getMotivoNotificacionId())));  
//	        	mapNoMotivoNotificacion.put(obj01.getDescripcion().trim(), obj01.getMotivoNotificacionId());
//	        }
	        
	        List<NoNotificador> lstNotificador=controlycobranzaBo.getAllNotificador();	        
	        for(NoNotificador noNotificador:lstNotificador){
	        	lstSelectItemsNotificador.add(new SelectItem(noNotificador.getApellidosNombres(),String.valueOf(noNotificador.getNotificadorId())));
	        	mapNotificador.put(noNotificador.getApellidosNombres().trim(), noNotificador.getNotificadorId());
	        }
	        
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	 public void permisosMenu() {	
		try {
			int submenuId = Constante.NOTIFICACIONES;
			
			int permisoBuscarId = Constante.BUSCAR;
			int permisoNotificarId = Constante.NOTIFICAR;
			permisoBuscar = false;
			permisoNotificar = false;
			
			listPermisosSubmenu  = menuBo.getAccesosSubmenuUsuario( getSessionManaged().getUsuarioLogIn().getUsuarioId() , submenuId);
			
			Iterator<SimpleMenuDTO> menuIterar = listPermisosSubmenu.iterator();
			while (menuIterar.hasNext()) {
				SimpleMenuDTO lsm = menuIterar.next();
				if(lsm.getItemId() == permisoBuscarId) {
					permisoBuscar = true;
				}
				if(lsm.getItemId() == permisoNotificarId) {
					permisoNotificar = true;
				}
			}
		} catch (SisatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	public void buscar()throws Exception{
	try{
			if(nroLote!=null && nroLote.compareTo("")!=0){
				String[] lote = nroLote.split("-");
				nroLote="";
				for (int i = 0; i < lote.length; i++) {
					nroLote =  nroLote + lote[i];
				}
			}
		lstFindLote=controlycobranzaBo.getAllFindCcActo(nroLote, nroActoRec, persona_id, tipoLote);
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		  }
	}
	
	public void limpiar()throws Exception{
		try{
			nroActoRec="";
			nroLote="";
			persona_id=0;
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		  }
	}
	
	public void notificar(){
		tipoAccion=Constante.TIPO_ACCION_NUEVO;
	}
	
	public void verActo(){
		tipoAccion=null;
		String nombreArchivo="";
			try{
				if(findCcActoItem.getNroRec()==null)
					nombreArchivo=findCcActoItem.getNroActo();
				else
					nombreArchivo=findCcActoItem.getNroRec();
				ByteArrayOutputStream output = new ByteArrayOutputStream();
		        RepositoryManager.buscarContenido(String.valueOf(findCcActoItem.getContenId()), output);
		        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			    response.setContentType(Constantes.CONTENT_MIMETYPE_JPEG);
			    response.addHeader("Content-Disposition","attachment;filename="+nombreArchivo);
			    response.setContentLength(output.size());
			    ServletOutputStream servletOutputStream = response.getOutputStream();
			    servletOutputStream.write(output.toByteArray(), 0, output.size());
			    servletOutputStream.flush();
			    servletOutputStream.close();
			    FacesContext.getCurrentInstance().responseComplete();
			}catch(Exception e){
				e.printStackTrace();
			}
	}
    
	public void notificarActo()throws Exception{
		try {
			if (validar()) {
				Integer notificacionid = generalBo.ObtenerCorrelativoTabla(
						"no_notificacion", 1);
				notificacion.setNotificacionId(notificacionid);
				notificacion.setNotificadorId(notificadorId);
				notificacion.setMotivoNotificacionId(noMotivoNotificacionId);
				if (findCcActoItem.getActoId() != null	&& findCcActoItem.getActoId() > 0) {
					notificacion.setActoId(findCcActoItem.getActoId());
				}
				if (findCcActoItem.getRecId() != null && findCcActoItem.getRecId() > 0) {
					notificacion.setRecId(findCcActoItem.getRecId());
					notificacion.setLoteId(findCcActoItem.getLoteId());
				}
				// notificacion.setNroCargo(findCcActoItem.getNroCargoNotificacion());//el
				// cargo se genera automaticamente
				notificacion.setFechaNotificacion(DateUtil
						.dateToSqlTimestamp(fechaNotificacion));
				notificacion.setEstado(Constante.ESTADO_ACTIVO);
				controlycobranzaBo.create(notificacion);
				controlycobranzaBo.actualizarActo(notificacion);
				notificacion = new NoNotificacion();
				setLstFindLote(controlycobranzaBo.getAllFindCcActo(nroLote,
						nroActoRec, persona_id, tipoLote));
				tipoAccion = null;
				fechaNotificacion = null;
				cmbNoRelacionPersona = null;
				noMotivoNotificacionId = null;
				cmbValueNoMotivoNotificacion = null;

			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	public boolean validar() throws Exception {
		try {
			NoNotificador not = controlycobranzaBo
					.findNoNotificador(notificadorId);
			if (not == null) {
				addErrorMessage(getMsg("no.errornotificador"));
				return false;
			}
			if (findCcActoItem.getFechaEmision().compareTo(
					DateUtil.dateToSqlTimestamp(fechaNotificacion)) >= 0) {
				addErrorMessage(getMsg("no.errorfechanotificacion"));
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		return true;
	}
	
	public void loadRelacionPersonaById(ValueChangeEvent event) {
		try{
			HtmlComboBox combo=(HtmlComboBox) event.getComponent(); 
		    String value=combo.getValue().toString();
		    if(value!=null){
				noRelacionPersonaId = (Integer)mapNoRelacionPersona.get(value);
				setCmbValueNoRelacionPersona(value);
		    }
		 }catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		  }
	}
	
	public void loadFormaNotificacionById(ValueChangeEvent event) {
		try {

			HtmlComboBox combo=(HtmlComboBox) event.getComponent(); 
		    String value=combo.getValue().toString();
		    Integer flagUbicacion;
			if (value.equals("Ubicables")) {
				flagUbicacion=1;
			} else if (value.equals("Inubicables")) {
				flagUbicacion=2;
			} else if (value.equals("No Hallados")){
				flagUbicacion=3;}
			else
				{flagUbicacion=4;
					
			}
			 List<NoMotivoNotificacion> lsNoMotivoNotificacion=controlycobranzaBo.getAlNoMotivoNotificacion(flagUbicacion);
		       Iterator<NoMotivoNotificacion> it1 = lsNoMotivoNotificacion.iterator();  
		       lstNoMotivoNotificacion=new ArrayList<SelectItem>();
		        while (it1.hasNext()){
		        	NoMotivoNotificacion obj01 = it1.next();  
		        	lstNoMotivoNotificacion.add(new SelectItem(obj01.getDescripcion(),String.valueOf(obj01.getMotivoNotificacionId())));  
		        	mapNoMotivoNotificacion.put(obj01.getDescripcion().trim(), obj01.getMotivoNotificacionId());
		        }
		//verFormasNotificacion()
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public void loadTMotivoNotificacionById(ValueChangeEvent event) {
		try{
			HtmlComboBox combo=(HtmlComboBox) event.getComponent(); 
		    String value=combo.getValue().toString();
		    if(value!=null){
				noMotivoNotificacionId = (Integer)mapNoMotivoNotificacion.get(value);
				setCmbValueNoMotivoNotificacion(value);
		    }
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		  }
	}
	
	public void loadNotificador(ValueChangeEvent event) {
		try{
			HtmlComboBox combo=(HtmlComboBox) event.getComponent(); 
		    String value=combo.getValue().toString();
		    if(value!=null){
				notificadorId = (Integer)mapNotificador.get(value);
				setCmbNotificador(value);
		    }
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		  }
	}
	
	public void salir(){
		notificacion=new NoNotificacion();
	}
	
	public void detalle()throws Exception {
		recordsItemNotificacion=controlycobranzaBo.getAllFindNoNotificacion(currentItem.getMasivaNotificacionId());
		if(recordsItemNotificacion.size()>0)
		  mostraDetalle=Boolean.TRUE;
	}
	
	public void procesarcarga(){
		try{
			 setRecords(new ArrayList<NoMasivaNotificacion>());
				//String 
				directorio=ParameterLoader.getParameter("directorio_carga_notificaciones");
				String nombreArchivo=(String)txtarchivo.getValue();
				if(nombreArchivo!=null&&nombreArchivo.trim().length()>0){
					File fichero = new File(directorio+nombreArchivo);
					if (fichero.exists()){
					Integer cargaLotesId=controlycobranzaBo.notificarMasivaId();
					String procesoCarga=controlycobranzaBo.notificarMasiva(directorio, nombreArchivo,cargaLotesId);//call sp_carga_lotes_bulk
					if(cargaLotesId>0){
						NoMasivaNotificacion item=controlycobranzaBo.findNoMasivaNotificacion(cargaLotesId);
						getRecords().add(item);
					  mostraResultados=Boolean.TRUE;
					}
				}else{
					WebMessages.messageError("Nombre del archivo de carga no Existe");
				}
			}else{
				WebMessages.messageError("Especifique Nombre del archivo de carga");
			}
				mostraDetalle=Boolean.FALSE;
				mostraResultadosDigitalizacion=Boolean.FALSE;
				mostraDetalleResulDigit=Boolean.FALSE;
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);	
		}
	}
	
	public void procesarnotificacionesDigitalizadas(){
		try{
			Integer correcto=0;
			Integer duplicado=0;
			Integer errores=0;
			noMasivaDigiNotif = new NoMasivaDigiNotif();
			listaNot=new ArrayList<NotificacionesDTO>();
			NotificacionesDTO obj;
			String archivos[];
			Integer id;
			Integer idMasivaDigi;
			noDetalleMasivaDigiNot= new NoDetalleMasivaDigiNotif();
			File folder = new File(ParameterLoader.getParameter("directorio_notificaciones_digitalizados"));
       		archivos=folder.list(new Filtro(Constante.TIPO_ARCHIVO_JPG));
       		if(archivos!=null){
       			idMasivaDigi=generalBo.ObtenerCorrelativoTabla("no_masiva_digi_notif", 1);
       			noMasivaDigiNotif.setMasivaDigiNotifId(idMasivaDigi);
    			noMasivaDigiNotif.setCantCorrectos(0);
    			noMasivaDigiNotif.setCantErrores(0);
    			noMasivaDigiNotif.setCantDuplicado(0);
    			noMasivaDigiNotif.setEstado(Constante.ESTADO_INACTIVO);
       			controlycobranzaBo.create(noMasivaDigiNotif);
       			for(int i=0; i<archivos.length;i++){
	       			obj=new NotificacionesDTO();
	       			noDetalleMasivaDigiNot= new NoDetalleMasivaDigiNotif();
					obj=controlycobranzaBo.findNoNotificacionDTO(archivos[i]);
					if(obj.getNotificacionesId()!=null && obj.getNotificacionesId()>0){
					  if(obj.getContenId()==0){ correcto++;
						    id=generalBo.ObtenerCorrelativoTabla("af_contentmanager", 1);
				       		obj.setContenId(Long.parseLong(id.toString()));
				       		listaNot.add(obj);
				       		controlycobranzaBo.actualizarNotificacionContenId(obj.getNotificacionesId(),Long.parseLong(id.toString()));
			       		    noDetalleMasivaDigiNot.setMasivaDigiNotifId(idMasivaDigi);
			       		    noDetalleMasivaDigiNot.setNroDocumento(obj.getNroDocumento());
			       		    noDetalleMasivaDigiNot.setNotificacionId(obj.getNotificacionesId());
			       		    noDetalleMasivaDigiNot.setContenId(Long.parseLong(id.toString()));
			       		    noDetalleMasivaDigiNot.setErrorMessage(null);
			       		    noDetalleMasivaDigiNot.setEstado(Constante.ESTADO_ACTIVO);
			       		    noDetalleMasivaDigiNot.setFechaNotificacion(DateUtil.convertDateToString(obj.getFechaNotificacion()));
					  }else{ duplicado++;
		       		    noDetalleMasivaDigiNot.setMasivaDigiNotifId(idMasivaDigi);
		       		    noDetalleMasivaDigiNot.setNroDocumento(obj.getNroDocumento());
		       		    noDetalleMasivaDigiNot.setNotificacionId(obj.getNotificacionesId());
		       		    noDetalleMasivaDigiNot.setErrorMessage("Notificaci�n Ya se Encuentra Digitalizada");
		       		    noDetalleMasivaDigiNot.setEstado(Constante.ESTADO_PENDIENTE);
		       		    noDetalleMasivaDigiNot.setFechaNotificacion(DateUtil.convertDateToString(obj.getFechaNotificacion()));
					  }
					}else{ errores++;
						noDetalleMasivaDigiNot.setMasivaDigiNotifId(idMasivaDigi);
		       		    noDetalleMasivaDigiNot.setNroDocumento(archivos[i]);
		       		    noDetalleMasivaDigiNot.setErrorMessage("Notificaci�n Con Error");
		       		    noDetalleMasivaDigiNot.setEstado(Constante.ESTADO_INACTIVO);
					} 
					controlycobranzaBo.create(noDetalleMasivaDigiNot);
       		     }
       		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			String pathDes = ctx.getRealPath("/");
			LoadImageNotificacionesManager task=new LoadImageNotificacionesManager(listaNot,pathDes);
			task.run();
			mostraResultadosDigitalizacion=Boolean.TRUE;
			mostraDetalleResulDigit=Boolean.FALSE;
			mostraResultados=Boolean.FALSE;
			mostraDetalle=Boolean.FALSE;
			noMasivaDigiNotif.setMasivaDigiNotifId(idMasivaDigi);
			noMasivaDigiNotif.setCantCorrectos(correcto);
			noMasivaDigiNotif.setCantErrores(errores);
			noMasivaDigiNotif.setCantDuplicado(duplicado);
			noMasivaDigiNotif.setEstado(Constante.ESTADO_ACTIVO);
			controlycobranzaBo.edit(noMasivaDigiNotif);
		    }  
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);	
		}
	}
	
	public void digitalizarPendientes(){
		try{ Integer id;
			listaNot=new ArrayList<NotificacionesDTO>();
			NotificacionesDTO obj;
			for(NoDetalleMasivaDigiNotif no:listaDetalleMasivaDigiNot){
				if(no.getCargarImagen().compareTo(Boolean.TRUE)==0){
       			 obj=new NotificacionesDTO();
       			 obj=controlycobranzaBo.findNoNotificacionDTO(no.getNroDocumento());
				 id=generalBo.ObtenerCorrelativoTabla("af_contentmanager", 1);
			     obj.setContenId(Long.parseLong(id.toString()));
			     listaNot.add(obj);
			     no.setContenId(Long.parseLong(id.toString()));
			     no.setErrorMessage(null);
			     no.setEstado(Constante.ESTADO_ACTIVO);
		       	 controlycobranzaBo.edit(no); 	    
				}
			}
	       	if(listaNot.size()>0){
			   ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
				String pathDes = ctx.getRealPath("/");
				LoadImageNotificacionesManager task=new LoadImageNotificacionesManager(listaNot,pathDes);
				task.run();
				mostraResultadosDigitalizacion=Boolean.TRUE;
				mostraResultados=Boolean.FALSE;
				mostraDetalle=Boolean.FALSE;
				mostraDetalleResulDigit=Boolean.FALSE;
				noMasivaDigiNotif = controlycobranzaBo.find(listaDetalleMasivaDigiNot.get(0).getMasivaDigiNotifId());
				noMasivaDigiNotif.setCantCorrectos(noMasivaDigiNotif.getCantCorrectos()+listaNot.size());
				noMasivaDigiNotif.setCantDuplicado(noMasivaDigiNotif.getCantDuplicado()-listaNot.size());
				controlycobranzaBo.edit(noMasivaDigiNotif);
	       	}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);	
		}
	}
	
	public void mostrarPendientes(){
		try{	
			listaDetalleMasivaDigiNot=controlycobranzaBo.getAllNoDetalleMasivaDigiNotif(noMasivaDigiNotif.getMasivaDigiNotifId(), Constante.ESTADO_PENDIENTE);
			cargarImagenes=Boolean.TRUE;
			mostraDetalleResulDigit=Boolean.TRUE;
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);	
		}
	}
	
	public void seleccionarTodosPendientes(){
		try{	
			for(NoDetalleMasivaDigiNotif no:listaDetalleMasivaDigiNot){
				no.setCargarImagen(Boolean.TRUE);
				mostraDetalleResulDigit=Boolean.TRUE;
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);	
		}
	}
	
	public void deSeleccionarTodosPendientes(){
		try{	
			for(NoDetalleMasivaDigiNotif no:listaDetalleMasivaDigiNot){
				no.setCargarImagen(Boolean.FALSE);
				mostraDetalleResulDigit=Boolean.TRUE;
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);	
		}
	}
	
	public void mostrarCorrecto(){
		try{	
			listaDetalleMasivaDigiNot=controlycobranzaBo.getAllNoDetalleMasivaDigiNotif(noMasivaDigiNotif.getMasivaDigiNotifId(), Constante.ESTADO_ACTIVO);
			cargarImagenes=Boolean.FALSE;
			mostraDetalleResulDigit=Boolean.TRUE;
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);	
		}
	}
	
	public void mostrarErrores(){
		try{	
			listaDetalleMasivaDigiNot=controlycobranzaBo.getAllNoDetalleMasivaDigiNotif(noMasivaDigiNotif.getMasivaDigiNotifId(), Constante.ESTADO_INACTIVO);
			cargarImagenes=Boolean.FALSE;
			mostraDetalleResulDigit=Boolean.TRUE;
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);	
		}
	}
	
	public void descargaError(){
		// Prepare.
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

        BufferedInputStream input = null;
        BufferedOutputStream output = null;
		try{
			if(getCurrentItem()!=null){
				int DEFAULT_BUFFER_SIZE = 10240; // 10KB.
		        ArrayList<NoDetalleMasivaNotificacion> listaError=controlycobranzaBo.getAllNoDetalleMasivaNotificacion(getCurrentItem().getMasivaNotificacionId());
		        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
				String pathDes = ctx.getRealPath("/");
				
		        
		        File excel=new File(pathDes+File.separator+"tmp"+File.separator+"detallelote.xls");
				WritableWorkbook xls = Workbook.createWorkbook(excel);
				xls.createSheet("Hoja", 0);
	       	 	WritableSheet sheet =xls.getSheet(0);
	    		writeDataSheet(sheet,listaError);
	    		xls.write();
	    		xls.close();
	    		
	    		 // Init servlet response.
	            response.reset();
	            /*response.setHeader("Content-Type", "application/vnd.ms-excel");
	            response.setHeader("Content-Length", String.valueOf(excel.length()));*/
	            response.setHeader("Content-Disposition", "attachment; filename=\"" + "result.xls" + "\"");
	            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

	            // Open file.
	            input = new BufferedInputStream(new FileInputStream(excel), DEFAULT_BUFFER_SIZE);
	            // Write file contents to response.
	            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
	            int length;
	            while ((length = input.read(buffer)) > 0) {
	                output.write(buffer, 0, length);
	            }

	            // Finalize task.
	            output.flush();
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
            // Gently close streams.
            close(output);
            close(input);
        }

        // Inform JSF that it doesn't need to handle response.
        // This is very important, otherwise you will get the following exception in the logs:
        // java.lang.IllegalStateException: Cannot forward after response has been committed.
        facesContext.responseComplete();
	}

	private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                // Do your thing with the exception. Print it, log it or mail it. It may be useful to 
                // know that this will generally only be thrown when the client aborted the download.
                e.printStackTrace();
            }
        }
    }
	
	private void writeDataSheet(WritableSheet s,ArrayList<NoDetalleMasivaNotificacion> listaError)throws WriteException{
	    WritableFont wf = new WritableFont(WritableFont.ARIAL,8);
	    WritableCellFormat cf = new WritableCellFormat(wf);
	    Iterator<NoDetalleMasivaNotificacion> it=listaError.iterator();
	    int i=0;
	    while(it.hasNext()){
	    	NoDetalleMasivaNotificacion err=it.next();
	    	Label l;
	    	if(err.getCorrelativo()!=null || err.getCorrelativo()>0){
	    	    l = new Label(1,i, String.valueOf(err.getCorrelativo()),cf);s.addCell(l);
		    }
	    	if(err.getCorrelativo()==null || err.getCorrelativo()<0){
	    	    l = new Label(1,i, null,cf);s.addCell(l);
		    }
	    	l = new Label(2,i,err.getNroActo()+err.getNroRec(),cf);s.addCell(l);
	    	l = new Label(3,i,err.getFechaNotificacion(),cf);s.addCell(l);
	    	if(err.getCodigoNotificador()!=null || err.getCodigoNotificador()>0){
	    	    l = new Label(4,i, String.valueOf(err.getCodigoNotificador()),cf);s.addCell(l);
		    }
	    	if(err.getCodigoNotificador()==null || err.getCodigoNotificador()<0){
	    	    l = new Label(4,i, null,cf);s.addCell(l);
		    }
	    	
	    	l = new Label(5,i,err.getMotivoNotificacion(),cf);s.addCell(l);
		    l = new Label(6,i,err.getErrorCode(),cf);s.addCell(l);
		    l = new Label(7,i,err.getErrorMessage(),cf);s.addCell(l);
		    i++;
	    }
    }
	
	public void exportarExcel() {

		HSSFWorkbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("Exportando a Excel");
		int rowIndex = 1;
		Row row1 = sheet.createRow(0);
		row1.createCell(0).setCellValue("Nro.Lote");
		row1.createCell(1).setCellValue("Nro.Acto");
		row1.createCell(2).setCellValue("Año Acto");
		row1.createCell(3).setCellValue("Año Deuda");
		row1.createCell(4).setCellValue("Cod.Contribuyente");
		row1.createCell(5).setCellValue("Concepto");
		row1.createCell(6).setCellValue("Monto Deuda");
		row1.createCell(7).setCellValue("Tipo Acto");
		row1.createCell(8).setCellValue("Fecha Emisión");
		row1.createCell(9).setCellValue("Tipo de Notificación");
		row1.createCell(10).setCellValue("Fecha Notificación");
		row1.createCell(11).setCellValue("Notificador");
		row1.createCell(12).setCellValue("Fecha Registro");
		row1.createCell(13).setCellValue("Registra");

		for (FindCcActo data : lstFindLote) {
			Row row = sheet.createRow(rowIndex++);
			int columnIndex = 0;

			row.createCell(columnIndex++).setCellValue(data.getLoteId());
			row.createCell(columnIndex++).setCellValue(data.getNroActo());
			row.createCell(columnIndex++).setCellValue(data.getAnnoActo());
			row.createCell(columnIndex++).setCellValue(data.getAnnoDeuda());
			row.createCell(columnIndex++).setCellValue(data.getPersonaId());
			row.createCell(columnIndex++).setCellValue(data.getConcepto());
			
			if (data.getMontoDeuda() != null) {
				row.createCell(columnIndex++).setCellValue(	String.valueOf(data.getMontoDeuda().doubleValue()));
			} else {
				row.createCell(columnIndex++).setCellValue("-");
			}
			row.createCell(columnIndex++).setCellValue(data.getTipoActo());
			row.createCell(columnIndex++).setCellValue(data.getFechaEmision());
			
			if (data.getTipoNotificacion() != null) {
				row.createCell(columnIndex++).setCellValue(data.getTipoNotificacion());
			} else {
				row.createCell(columnIndex++).setCellValue("-");
			}
			
			if (data.getFechaNotificacion() != null) {
				row.createCell(columnIndex++).setCellValue(DateUtil.convertDateToString(data.getFechaNotificacion()));
			} else {
				row.createCell(columnIndex++).setCellValue("-");
			}
			
			row.createCell(columnIndex++).setCellValue(data.getApellidosNombres());
			
			if (data.getFechaCancelacion() != null) {
				row.createCell(columnIndex++).setCellValue(data.getFechaCancelacion());
			} else {
				row.createCell(columnIndex++).setCellValue("-");
			}
			
			if (data.getRegistrador() != null) {
			row.createCell(columnIndex++).setCellValue(data.getRegistrador());
			} else {
				row.createCell(columnIndex++).setCellValue("-");
			}
			
			
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
	
	public List<NoDetalleMasivaNotificacion> getRecordsItem() {
		return recordsItem;
	}
	public void setRecordsItem(List<NoDetalleMasivaNotificacion> recordsItem) {
		this.recordsItem = recordsItem;
	}
	
	public HtmlComboBox getCmbNoMotivoNotificacion() {
		return cmbNoMotivoNotificacion;
	}

	public void setCmbNoMotivoNotificacion(HtmlComboBox cmbNoMotivoNotificacion) {
		this.cmbNoMotivoNotificacion = cmbNoMotivoNotificacion;
	}

	public List<SelectItem> getLstNoMotivoNotificacion() {
		return lstNoMotivoNotificacion;
	}

	public void setLstNoMotivoNotificacion(List<SelectItem> lstNoMotivoNotificacion) {
		this.lstNoMotivoNotificacion = lstNoMotivoNotificacion;
	}

	public HashMap<String, Integer> getMapNoMotivoNotificacion() {
		return mapNoMotivoNotificacion;
	}

	public void setMapNoMotivoNotificacion(
			HashMap<String, Integer> mapNoMotivoNotificacion) {
		this.mapNoMotivoNotificacion = mapNoMotivoNotificacion;
	}

	public String getCmbValueNoMotivoNotificacion() {
		return cmbValueNoMotivoNotificacion;
	}

	public void setCmbValueNoMotivoNotificacion(String cmbValueNoMotivoNotificacion) {
		this.cmbValueNoMotivoNotificacion = cmbValueNoMotivoNotificacion;
	}

	public List<NoRelacionPersona> getlNoMotivoNotificacion() {
		return lNoMotivoNotificacion;
	}

	public void setlNoMotivoNotificacion(
			List<NoRelacionPersona> lNoMotivoNotificacion) {
		this.lNoMotivoNotificacion = lNoMotivoNotificacion;
	}

	public HtmlComboBox getCmbNoRelacionPersona() {
		return cmbNoRelacionPersona;
	}

	public void setCmbNoRelacionPersona(HtmlComboBox cmbNoRelacionPersona) {
		this.cmbNoRelacionPersona = cmbNoRelacionPersona;
	}

	public List<SelectItem> getLstNoRelacionPersona() {
		return lstNoRelacionPersona;
	}

	public void setLstNoRelacionPersona(List<SelectItem> lstNoRelacionPersona) {
		this.lstNoRelacionPersona = lstNoRelacionPersona;
	}

	public HashMap<String, Integer> getMapNoRelacionPersona() {
		return mapNoRelacionPersona;
	}

	public void setMapNoRelacionPersona(
			HashMap<String, Integer> mapNoRelacionPersona) {
		this.mapNoRelacionPersona = mapNoRelacionPersona;
	}

	public String getCmbValueNoRelacionPersona() {
		return cmbValueNoRelacionPersona;
	}

	public void setCmbValueNoRelacionPersona(String cmbValueNoRelacionPersona) {
		this.cmbValueNoRelacionPersona = cmbValueNoRelacionPersona;
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

	public NoNotificacion getNotificacion() {
		return notificacion;
	}

	public void setNotificacion(NoNotificacion notificacion) {
		this.notificacion = notificacion;
	}

	public List<NoRelacionPersona> getlNoRelacionPersona() {
		return lNoRelacionPersona;
	}

	public void setlNoRelacionPersona(List<NoRelacionPersona> lNoRelacionPersona) {
		this.lNoRelacionPersona = lNoRelacionPersona;
	}


	public Integer getNoMotivoNotificacionId() {
		return noMotivoNotificacionId;
	}

	public void setNoMotivoNotificacionId(Integer noMotivoNotificacionId) {
		this.noMotivoNotificacionId = noMotivoNotificacionId;
	}

	public Integer getNoRelacionPersonaId() {
		return noRelacionPersonaId;
	}

	public void setNoRelacionPersonaId(Integer noRelacionPersonaId) {
		this.noRelacionPersonaId = noRelacionPersonaId;
	}
	public Integer getTipoAccion() {
		return tipoAccion;
	}
	public void setTipoAccion(Integer tipoAccion) {
		this.tipoAccion = tipoAccion;
	}
	public String getNroLote() {
		return nroLote;
	}
	public void setNroLote(String nroLote) {
		this.nroLote = nroLote;
	}
	public String getNroActoRec() {
		return nroActoRec;
	}
	public void setNroActoRec(String nroActoRec) {
		this.nroActoRec = nroActoRec;
	}
	public Integer getPersona_id() {
		return persona_id;
	}
	public void setPersona_id(Integer persona_id) {
		this.persona_id = persona_id;
	}
	public Integer getTipoLote() {
		return tipoLote;
	}
	public void setTipoLote(Integer tipoLote) {
		this.tipoLote = tipoLote;
	}
	public HtmlInputText getTxtarchivo() {
		return txtarchivo;
	}
	public void setTxtarchivo(HtmlInputText txtarchivo) {
		this.txtarchivo = txtarchivo;
	}
	public List<NoMasivaNotificacion> getRecords() {
		return records;
	}
	public void setRecords(List<NoMasivaNotificacion> records) {
		this.records = records;
	}
	public NoMasivaNotificacion getCurrentItem() {
		return currentItem;
	}
	public void setCurrentItem(NoMasivaNotificacion currentItem) {
		this.currentItem = currentItem;
	}
	public Boolean getMostraDetalle() {
		return mostraDetalle;
	}
	public void setMostraDetalle(Boolean mostraDetalle) {
		this.mostraDetalle = mostraDetalle;
	}

	public List<FindNotificacion> getRecordsItemNotificacion() {
		return recordsItemNotificacion;
	}

	public void setRecordsItemNotificacion(List<FindNotificacion> recordsItemNotificacion) {
		this.recordsItemNotificacion = recordsItemNotificacion;
	}
	
	public ArrayList<NotificacionesDTO> getListaNot() {
		return listaNot;
	}
	public void setListaNot(ArrayList<NotificacionesDTO> listaNot) {
		this.listaNot = listaNot;
	}

	public Boolean getMostraResultados() {
		return mostraResultados;
	}
	public void setMostraResultados(Boolean mostraResultados) {
		this.mostraResultados = mostraResultados;
	}

	public Boolean getMostraResultadosDigitalizacion() {
		return mostraResultadosDigitalizacion;
	}
	public void setMostraResultadosDigitalizacion(
			Boolean mostraResultadosDigitalizacion) {
		this.mostraResultadosDigitalizacion = mostraResultadosDigitalizacion;
	}

	public NoMasivaDigiNotif getNoMasivaDigiNotif() {
		return noMasivaDigiNotif;
	}
	public void setNoMasivaDigiNotif(NoMasivaDigiNotif noMasivaDigiNotif) {
		this.noMasivaDigiNotif = noMasivaDigiNotif;
	}

	public NoDetalleMasivaDigiNotif getNoDetalleMasivaDigiNot() {
		return noDetalleMasivaDigiNot;
	}
	public void setNoDetalleMasivaDigiNot(NoDetalleMasivaDigiNotif noDetalleMasivaDigiNot) {
		this.noDetalleMasivaDigiNot = noDetalleMasivaDigiNot;
	}

	public List<NoDetalleMasivaDigiNotif> getListaDetalleMasivaDigiNot() {
		return listaDetalleMasivaDigiNot;
	}
	public void setListaDetalleMasivaDigiNot(
			List<NoDetalleMasivaDigiNotif> listaDetalleMasivaDigiNot) {
		this.listaDetalleMasivaDigiNot = listaDetalleMasivaDigiNot;
	}

	public Boolean getCargarImagenes() {
		return cargarImagenes;
	}
	public void setCargarImagenes(Boolean cargarImagenes) {
		this.cargarImagenes = cargarImagenes;
	}

	public Boolean getMostraDetalleResulDigit() {
		return mostraDetalleResulDigit;
	}
	public void setMostraDetalleResulDigit(Boolean mostraDetalleResulDigit) {
		this.mostraDetalleResulDigit = mostraDetalleResulDigit;
	}

	public String getDirectorio() {
		return directorio;
	}
	public void setDirectorio(String directorio) {
		this.directorio = directorio;
	}

	public class Filtro implements FilenameFilter{
	    String extension;
	    Filtro(String extension){
	        this.extension=extension;
	    }
	    public boolean accept(File dir, String name){
	        return name.endsWith(extension);
	    }
	}

	public HashMap<String, Integer> getMapNotificador() {
		return mapNotificador;
	}
	public void setMapNotificador(HashMap<String, Integer> mapNotificador) {
		this.mapNotificador = mapNotificador;
	}
	public String getCmbNotificador() {
		return cmbNotificador;
	}
	public void setCmbNotificador(String cmbNotificador) {
		this.cmbNotificador = cmbNotificador;
	}
	public HtmlComboBox getCmbHtmlNotificador() {
		return cmbHtmlNotificador;
	}
	public void setCmbHtmlNotificador(HtmlComboBox cmbHtmlNotificador) {
		this.cmbHtmlNotificador = cmbHtmlNotificador;
	}
	public Integer getNotificadorId() {
		return notificadorId;
	}
	public void setNotificadorId(Integer notificadorId) {
		this.notificadorId = notificadorId;
	}
	public List<SelectItem> getLstSelectItemsNotificador() {
		return lstSelectItemsNotificador;
	}
	public void setLstSelectItemsNotificador(List<SelectItem> lstSelectItemsNotificador) {
		this.lstSelectItemsNotificador = lstSelectItemsNotificador;
	}
	public java.util.Date getFechaNotificacion() {
		return fechaNotificacion;
	}
	public void setFechaNotificacion(java.util.Date fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}
	public String getCodicionAdministrado() {
		return codicionAdministrado;
	}
	public void setCodicionAdministrado(String codicionAdministrado) {
		this.codicionAdministrado = codicionAdministrado;
	}
	public HtmlComboBox getCmbCondicionAdministrado() {
		return cmbCondicionAdministrado;
	}
	public void setCmbCondicionAdministrado(HtmlComboBox cmbCondicionAdministrado) {
		this.cmbCondicionAdministrado = cmbCondicionAdministrado;
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
	public boolean isPermisoNotificar() {
		return permisoNotificar;
	}
	public void setPermisoNotificar(boolean permisoNotificar) {
		this.permisoNotificar = permisoNotificar;
	}
	
	
		
}
