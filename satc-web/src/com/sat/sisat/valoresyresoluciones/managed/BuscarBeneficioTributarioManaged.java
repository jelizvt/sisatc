package com.sat.sisat.valoresyresoluciones.managed;

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
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRRtfExporter;

import org.richfaces.component.html.HtmlComboBox;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.SATWEBParameterFactory;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.menus.business.MenuBoRemote;
import com.sat.sisat.menus.dto.SimpleMenuDTO;
import com.sat.sisat.persistence.CrudServiceBean;
import com.sat.sisat.persistence.entity.GnCondicionEspecial;
import com.sat.sisat.persistence.entity.GnCondicionEspecialPK;
import com.sat.sisat.persistence.entity.MpCondicionEspecialRequisito;
import com.sat.sisat.persistence.entity.MpInspeccionCondicionEspecial;
import com.sat.sisat.persistence.entity.MpRelacionado;
import com.sat.sisat.persistence.entity.MpTipoCondicionEspecial;
import com.sat.sisat.persistence.entity.MpTipoDocumentoCondicionEspecial;
import com.sat.sisat.persistence.entity.NoMotivoNotificacion;
import com.sat.sisat.persistence.entity.NoNotificador;
import com.sat.sisat.persistence.entity.ReEstadoResolucion;
import com.sat.sisat.predial.dto.MpRequerimientoCondicionEspecialDTO;
import com.sat.sisat.predial.dto.SustentoCondicionEspecialDTO;
import com.sat.sisat.valoresyresoluciones.business.ValoresyResolucionesBoRemote;


@ManagedBean
@ViewScoped
public class BuscarBeneficioTributarioManaged   extends BaseManaged{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	ValoresyResolucionesBoRemote valResBo;
	
	@EJB
	MenuBoRemote menuBo;
		
		private List<MpCondicionEspecialRequisito> lstRequisito = new ArrayList<MpCondicionEspecialRequisito>();
		private List<MpCondicionEspecialRequisito> lstRequisitoAdult = new ArrayList<MpCondicionEspecialRequisito>();

		private List<SustentoCondicionEspecialDTO> lstSustento = new ArrayList<SustentoCondicionEspecialDTO>();
		private List<SustentoCondicionEspecialDTO> lstSustentoPersona = new ArrayList<SustentoCondicionEspecialDTO>();
		private List<MpRequerimientoCondicionEspecialDTO> lstRequerimientoCe = new ArrayList<MpRequerimientoCondicionEspecialDTO>();
		
		private MpRequerimientoCondicionEspecialDTO findRequerimientoItem= new MpRequerimientoCondicionEspecialDTO();
	
		private boolean flagIncompleto;
		
		private HtmlComboBox cmbtipocondicionespecial;
		private List<SelectItem> lsttipocondicionespecial = new ArrayList<SelectItem>();
		private HashMap<String, Integer> mapMpTipocondicionespecial = new HashMap<String, Integer>();
		private HashMap<Integer,String> mapIMpTipocondicionespecial = new HashMap<Integer,String>();
		private String cmbValuetipocondicionespecial;
		private String cmbValuetipocondicionespecialAdult;

		private List<MpTipoCondicionEspecial> lMptipocondicionespecial = null;
		private Integer tipocondicionespecialId;
		
		private Boolean isPensionista;
		
		private Boolean isPensionistaServ=true;

		private String mensaje;
	
	/** Notificacion 
	 * */
		private HtmlComboBox cmbCondicionAdministrado;
		private List<SelectItem> lstTipoNotificacion = new ArrayList<SelectItem>();
		private HashMap<String, Integer> mapTipoNotificacion = new HashMap<String, Integer>();
		private String codicionAdministrado;
		private String cmbValueNoNotificacion;
		private Integer condicionAdministradoId;
		private HtmlComboBox cmbNoNotificacion;
		private Integer noNotificacionId;
		private String cmbNotificador;
		private HtmlComboBox cmbHtmlNotificador;
		private Integer notificadorId;
		private HashMap<String, Integer> mapNotificador = new HashMap<String, Integer>();
		private List<SelectItem> lstSelectItemsNotificador = new ArrayList<SelectItem>();
		private Date fechaNotificacion;
		private String observacion;
			private Boolean isNotificado;
			private Boolean isNuevo=true;
			private Boolean isEdicion=false;
//			private Boolean isCierre=false;
			private Integer idR;
			private Integer flagD;
			private Integer flagR;
			private Integer flagI;
			private Integer estado;
		
	/** Resolución
	 * */
		
		private HtmlComboBox cmbtipodocumentoCondicionEspecial;
		private List<SelectItem> lsttipodocumentoCondicionEspecial= new ArrayList<SelectItem>();
		private HashMap<String, Integer> mapMpTipodocumentoCondicionEspecial = new HashMap<String, Integer>();
		private String cmbValuetipodocumentoCondicionEspecial;
		private Integer tipodocumentoId;
		
		private GnCondicionEspecial gnCondicionEspecial=null;
		private Date fechaDocumento;
		private Date fechaInicioCond;
		private Date fechaFinCond;
		private String nroDocumento;
		private Double porcentaje;
		
		private String cmbValueEstadoCondicionEspecial;
		private Integer estadoCondicionEspecialId;
		private HtmlComboBox cmbtipoEstadoCondicionEspecial;
		private List<SelectItem> lstEstadoCondicionEspecial= new ArrayList<SelectItem>();
		private HashMap<String, Integer> mapEstado = new HashMap<String, Integer>();
		
		private String sustento;
	
	/** Insp. Ocular
	 * */
		private String motivo;
		private MpInspeccionCondicionEspecial mpInspeccionCondicionEspecial=null;
		private Integer requerimientoId;
		private Integer inspOcular;
		private Boolean isInspNuevo=false;
		private Boolean isInspeccion=false;
		
		private Integer tipoAccion=Constante.TIPO_ACCION_NUEVO;
		private String condicion;
		private HtmlComboBox cmbCondicion;
		private List<SelectItem> listaTipoNotificacion = new ArrayList<SelectItem>();
		private String cmbMotivo;
		private HtmlComboBox cmbxMotivo;
		private Integer motivoId;
		private HashMap<String, Integer> mapMotivo=new HashMap<String, Integer>();
		private List<SelectItem> listaMotivoNotificacion=new ArrayList<SelectItem>();
		private String cmbNotifica;
		private HtmlComboBox cmbxNotificador;
		private Integer notificaId;
		private List<SelectItem> listaNotificador=new ArrayList<SelectItem>();
		private java.util.Date fechaNotifica;
		private HashMap<String, Integer> mapMotivoNotificacion=new HashMap<String, Integer>();
		
		// INICIO PERMISOS PARA EL MODULO -=CRAMIREZ=-
		private List<SimpleMenuDTO> listPermisosSubmenu = new ArrayList<SimpleMenuDTO>();
		private boolean permisoAgregarRegistrar;
		// FIN PERMISOS PARA EL MODULO
		

	@PostConstruct
	public void init(){
		try {
			permisosMenu();
			lstRequerimientoCe=valResBo.getAllBandejaCondicionEspecial(getSessionManaged().getContribuyente().getPersonaId());
			
			/** Requisitos de Inafectos **/
			
			gnCondicionEspecial=new GnCondicionEspecial();
			GnCondicionEspecialPK id=new GnCondicionEspecialPK();
			id.setCondicionEspecialId(Constante.RESULT_PENDING);
			id.setPersonaId(Constante.RESULT_PENDING);
			gnCondicionEspecial.setId(id);
			
			mpInspeccionCondicionEspecial=new MpInspeccionCondicionEspecial();
		
			obtenerCondicionEspecial();	
			//obtenerDocumento();
			
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public void permisosMenu() {	
		try {
			int submenuId = Constante.BENEFICIO_TRIBUTARIO;
			
			int permisoAgregarRegistrarId = Constante.AGREGAR_REGISTRAR;
			
			permisoAgregarRegistrar = false;
			
			listPermisosSubmenu  = menuBo.getAccesosSubmenuUsuario( getSessionManaged().getUsuarioLogIn().getUsuarioId() , submenuId);
			
			Iterator<SimpleMenuDTO> menuIterar = listPermisosSubmenu.iterator();
			while (menuIterar.hasNext()) {
				SimpleMenuDTO lsm = menuIterar.next();
				if(lsm.getItemId() == permisoAgregarRegistrarId) {
					permisoAgregarRegistrar = true;
				}									
			}
		} catch (SisatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void agregarSustento(ActionEvent ev) {
		try {
				int cont =0;int count =0;
				int flag;
				int tipo=0;
				Date fecha=DateUtil.getCurrentDateOnly();
				
				Integer codigo=getSessionManaged().getContribuyente().getPersonaId();
				String contribuyente=getSessionManaged().getContribuyente().getApellidosNombres();
				String direccion=getSessionManaged().getContribuyente().getDomicilioPersona();
				setFlagIncompleto(Boolean.FALSE);
							
			lstSustentoPersona=valResBo.getAllRequisitoByCondicionByPersona(getSessionManaged().getContribuyente().getPersonaId());//mpPersona.getPersonaId()
			if (lstSustentoPersona==null || lstSustentoPersona.size()==0){
				for (MpCondicionEspecialRequisito cer : lstRequisito) {
					if (cer.isSelected()==Boolean.FALSE && cer.getFlag()==1){
						cont=cont+1;
					}
				}
				if (cont>=1){tipo=1;}//faltan requisitos
				
				int resultado=valResBo.registrarRequerimiento(codigo, tipocondicionespecialId, contribuyente, direccion,tipo, null, null);
				for (MpCondicionEspecialRequisito cer : lstRequisito) {
					//if (cer.isSelected()) {
						lstSustento.add(new SustentoCondicionEspecialDTO(cer.getCondEspecialRequisitoId(),cer.getDescripcion(),cer.getGlosa(),cer.getFlag()));
						if(cer.isSelected()){ flag=1;}else{flag=0;}
						valResBo.registrarSustento(codigo,cer.getCondEspecialRequisitoId(),cer.getGlosa(),flag,resultado,null,null);
					//}else if (cer.isSelected()==Boolean.FALSE && cer.getFlag()==1){
					if (cer.isSelected()==Boolean.FALSE && cer.getFlag()==1){
						count=count+1;
						setFlagIncompleto(Boolean.TRUE);
					}
				}
				if (flagIncompleto==Boolean.TRUE){
					addErrorMessage("Falta(n) "+ count + " requisito(s) a presentar.");
				}else{
					addErrorMessage("¡Requisitos completos!");
				}
				
				//if (tipo==1){
					valResBo.registrarNotificacion(resultado, 1, 1, fecha, null,tipo, null, null);
				//}
				lstRequerimientoCe=valResBo.getAllBandejaCondicionEspecial(getSessionManaged().getContribuyente().getPersonaId());
			}
			else{
				addErrorMessage("Ya existe(n) documento(s) presentado(s)");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			WebMessages.messageFatal(ex);
			addErrorMessage(ex.getMessage());
		}
	}
	
	public void agregarSustentoAdult(ActionEvent ev) {
		try {
				int cont =0;int count =0;
				int flag;
				int tipo=0;
				Date fecha=DateUtil.getCurrentDateOnly();
				
				Integer codigo=getSessionManaged().getContribuyente().getPersonaId();
				String contribuyente=getSessionManaged().getContribuyente().getApellidosNombres();
				String direccion=getSessionManaged().getContribuyente().getDomicilioPersona();
				setFlagIncompleto(Boolean.FALSE);
							
			lstSustentoPersona=valResBo.getAllRequisitoByCondicionByPersona(getSessionManaged().getContribuyente().getPersonaId());//mpPersona.getPersonaId()
			if (lstSustentoPersona==null || lstSustentoPersona.size()==0){
				for (MpCondicionEspecialRequisito cer : lstRequisito) {
					if (cer.isSelected()==Boolean.FALSE && cer.getFlag()==1){
						cont=cont+1;
					}
				}
				if (cont>=1){tipo=1;}//faltan requisitos
				
				int resultado=valResBo.registrarRequerimiento(codigo, tipocondicionespecialId, contribuyente, direccion,tipo, null, null);
				for (MpCondicionEspecialRequisito cer : lstRequisito) {
					//if (cer.isSelected()) {
						lstSustento.add(new SustentoCondicionEspecialDTO(cer.getCondEspecialRequisitoId(),cer.getDescripcion(),cer.getGlosa(),cer.getFlag()));
						if(cer.isSelected()){ flag=1;}else{flag=0;}
						valResBo.registrarSustento(codigo,cer.getCondEspecialRequisitoId(),cer.getGlosa(),flag,resultado,null,null);
					//}else if (cer.isSelected()==Boolean.FALSE && cer.getFlag()==1){
					if (cer.isSelected()==Boolean.FALSE && cer.getFlag()==1){
						count=count+1;
						setFlagIncompleto(Boolean.TRUE);
					}
				}
				if (flagIncompleto==Boolean.TRUE){
					addErrorMessage("Falta(n) "+ count + " requisito(s) a presentar.");
				}else{
					addErrorMessage("¡Requisitos completos!");
				}
				
				//if (tipo==1){
					valResBo.registrarNotificacion(resultado, 1, 1, fecha, null,tipo, null, null);
				//}
				lstRequerimientoCe=valResBo.getAllBandejaCondicionEspecial(getSessionManaged().getContribuyente().getPersonaId());
			}
			else{
				addErrorMessage("Ya existe(n) documento(s) presentado(s)");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			WebMessages.messageFatal(ex);
			addErrorMessage(ex.getMessage());
		}
	}
	
	public void notificarRequerimiento() throws Exception {
		try {
			
			valResBo.registrarNotificacion(findRequerimientoItem.getCondicionRequerimientoId(), condicionAdministradoId, notificadorId, fechaNotificacion, observacion,0, null, null);

			lstRequerimientoCe=valResBo.getAllBandejaCondicionEspecial(getSessionManaged().getContribuyente().getPersonaId());
			
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public void agregarCondicionEspecial()throws Exception{
		try{
			
			String tabla="gn_condicion_especial";
			Integer codigo=getSessionManaged().getContribuyente().getPersonaId();

			if(fechaInicioCond!=null && fechaFinCond!=null){
				   if (fechaInicioCond.compareTo(fechaFinCond) > 0){
					   addErrorMessage(getMsg("mp.errorMayorfechaInicioCondicion"));
					}
			    }
			
		    if(fechaInicioCond!=null)
		    	gnCondicionEspecial.setFechaInicio(DateUtil.dateToSqlTimestamp(fechaInicioCond));
			if(fechaFinCond!=null)
				gnCondicionEspecial.setFechaFin(DateUtil.dateToSqlTimestamp(fechaFinCond));
			if(fechaDocumento!=null)
				gnCondicionEspecial.setFechaDocumento(DateUtil.dateToSqlTimestamp(fechaDocumento));

			gnCondicionEspecial.setTipoDocumento(tipodocumentoId);
			gnCondicionEspecial.setEstado(Constante.ESTADO_ACTIVO);
			gnCondicionEspecial.setRequerimientoId(findRequerimientoItem.getCondicionRequerimientoId());
			gnCondicionEspecial.setEstadoResolucionId(estadoCondicionEspecialId);
			gnCondicionEspecial.setGlosa(sustento);
			gnCondicionEspecial.setPorcentaje(porcentaje);
			gnCondicionEspecial.setNroDocumento(nroDocumento);
						
			valResBo.registrarResolucion(gnCondicionEspecial,tabla,codigo, null, null);
			
			lstRequerimientoCe=valResBo.getAllBandejaCondicionEspecial(getSessionManaged().getContribuyente().getPersonaId());

		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	public void editarExpediente(Integer expedienteId,Integer flagDoc,Integer flagInspeccion,Integer estado) {
		try{
			setIsPensionista(Boolean.TRUE);
			setIsEdicion(Boolean.TRUE);
			setIsNuevo(Boolean.FALSE);
			setIsPensionistaServ(Boolean.TRUE);
			
			setIdR(expedienteId);
			setFlagD(flagDoc);
			setFlagI(flagInspeccion);
			setEstado(estado);
			
			lstRequisito = valResBo.obtenerRequisitosExpediente(expedienteId,getSessionManaged().getContribuyente().getPersonaId());
			
		    List<MpTipoCondicionEspecial> lMptipocondicionespecial=valResBo.obtenerCondicionExpediente(expedienteId);
				Iterator<MpTipoCondicionEspecial> it1 = lMptipocondicionespecial.iterator();  
				lsttipocondicionespecial=new ArrayList<SelectItem>();
				 
		        while (it1.hasNext()){
		        	MpTipoCondicionEspecial obj1 = it1.next();  
		        	lsttipocondicionespecial.add(new SelectItem(obj1.getDescripcion(),String.valueOf(obj1.getTipoCondEspecialId())));  
		        	mapMpTipocondicionespecial.put(obj1.getDescripcion().trim(), obj1.getTipoCondEspecialId());
		        	mapIMpTipocondicionespecial.put(obj1.getTipoCondEspecialId(), obj1.getDescripcion().trim());

		        } 
	        	String valueTipoDocumento=(mapIMpTipocondicionespecial.get(lMptipocondicionespecial.get(0).getTipoCondEspecialId())!=null)?mapIMpTipocondicionespecial.get(lMptipocondicionespecial.get(0).getTipoCondEspecialId()):"";
	        	setCmbValuetipocondicionespecial(valueTipoDocumento);
	        	setCmbValuetipocondicionespecialAdult("1");
		        
	        
	        	System.out.println("setInspOcular "+ getInspOcular());
	        	
//	        	if (getIsInspeccion()==Boolean.TRUE){
//	        		setInspOcular(0);		
//	        	}

		} catch (Exception e) {

			WebMessages.messageError(e.getMessage());
		}
		
	}
	
	public void actualizarSustento(ActionEvent ev) {
		try {
				int cont =0;
				int flag;
				int tipo=0;
				
				Integer codigo=getSessionManaged().getContribuyente().getPersonaId();
				setFlagIncompleto(Boolean.FALSE);
//				if (isInspeccion==false){setIsCierre(Boolean.TRUE);}else {setIsCierre(Boolean.FALSE);}
				Integer requerimientoId = getIdR();
							
//				for (MpCondicionEspecialRequisito cer : lstRequisito) {
//					if (cer.isSelected()==Boolean.FALSE && cer.getFlag()==1){
//						cont=cont+1;
//					}
//				}
//				if (cont>=1){tipo=1;}//faltan requisitos
				
				for (MpCondicionEspecialRequisito cer : lstRequisito) {
					lstSustento.add(new SustentoCondicionEspecialDTO(cer.getCondEspecialRequisitoId(),cer.getDescripcion(),cer.getGlosa(),cer.getFlag()));
						if(cer.isSelected()){flag=1;}else{flag=0;}
						valResBo.actualizarSustento(codigo,cer.getCondEspecialRequisitoId(),cer.getGlosa(),flag,requerimientoId,null,null,0);
					if (cer.isSelected()==Boolean.FALSE && cer.getFlag()==1){
						cont=cont+1;
						setFlagIncompleto(Boolean.TRUE);
					}
				}
				if (flagIncompleto==Boolean.TRUE){
					//addErrorMessage("Falta(n) "+ cont + " requisito(s) a presentar.");
					setMensaje("Falta(n) "+ cont + " requisito(s) a presentar.");
					///CUANDO TIENE REQUISITOS COMPLETOS.
								if(getFlagD()==1){
									valResBo.actualizarSustento(codigo,0,null,0,requerimientoId,0,null,1);
									//System.out.println("***********aqui act*****************");
									setFlagD(0);
								}
					///CUANDO TIENE REQUISITOS COMPLETOS.
				}else{
					//valResBo.cierreRequerimiento(codigo,requerimientoId,null,null);
					//addErrorMessage("¡Requisitos completos!");
					setMensaje("¡Requisitos completos!");
				}

				lstRequerimientoCe=valResBo.getAllBandejaCondicionEspecial(getSessionManaged().getContribuyente().getPersonaId());

		} catch (Exception ex) {
			ex.printStackTrace();
			WebMessages.messageFatal(ex);
			addErrorMessage(ex.getMessage());
		}
	}
	
	public void actualizarRequerimiento(ActionEvent ev) {
		try {
				Integer requerimientoId = getIdR();
				
				valResBo.cierreRequerimiento(getSessionManaged().getContribuyente().getPersonaId(),requerimientoId,null,null);
					System.out.println("Cierro flag_requerimiento=1  :." + getFlagR());
					setFlagR(1);
					System.out.println("Cierro flag_requerimiento=1  :." + getFlagR());
				lstRequerimientoCe=valResBo.getAllBandejaCondicionEspecial(getSessionManaged().getContribuyente().getPersonaId());

		} catch (Exception ex) {
			ex.printStackTrace();
			WebMessages.messageFatal(ex);
			addErrorMessage(ex.getMessage());
		}
	}
	
	
	public void registraInspeccion(Integer expedienteId){
		try{
			
			setRequerimientoId(expedienteId);
		
		} catch (Exception ex) {
			ex.printStackTrace();
			WebMessages.messageFatal(ex);
			addErrorMessage(ex.getMessage());
		}
	}
	
	public void registrarInspeccion()throws Exception { 
		try{
			Integer tipo=1;
				mpInspeccionCondicionEspecial.setDescripcion(motivo);
				mpInspeccionCondicionEspecial.setFlagSituacion(1);
				mpInspeccionCondicionEspecial.setRequerimientoId(getRequerimientoId());
				mpInspeccionCondicionEspecial.setUsuarioId(getUser().getUsuarioId());
				mpInspeccionCondicionEspecial.setTerminal(getUser().getTerminal());
				
				valResBo.registrarInspecion(mpInspeccionCondicionEspecial,tipo);
				setIsInspNuevo(Boolean.TRUE);
				setIsInspeccion(Boolean.TRUE);
//				setIsInspCierre(Boolean.TRUE);
				
				lstRequerimientoCe=valResBo.getAllBandejaCondicionEspecial(getSessionManaged().getContribuyente().getPersonaId());

		} catch (Exception ex) {
			ex.printStackTrace();
			WebMessages.messageFatal(ex);
			addErrorMessage(ex.getMessage());
		}
	}
	
	public void actualizaInspeccion(Integer expedienteId,Integer flagDoc,Integer flagEstadoInsp,Integer flagReqInsp,Integer estado){
		try{
			setRequerimientoId(expedienteId);
			setFlagD(flagDoc);
			
			if (flagEstadoInsp==0){
				setInspOcular(flagEstadoInsp);	
			}else if (flagEstadoInsp==1){
				setInspOcular(null);
			}
			
			setFlagR(flagReqInsp);
			setEstado(estado);
			
			System.out.println(getRequerimientoId());
			System.out.println("flagDoc "+ flagDoc); System.out.println("FlagD "+getFlagD());
			System.out.println("flagEstadoInsp "+flagEstadoInsp); System.out.println("InspOcular"+getInspOcular());
			System.out.println("flagReqInsp:" + flagReqInsp);
			System.out.println("estado:" + getEstado());
//			---1:abiert
//			---0:cerrado
//			if (flagEstadoInsp==0){
//				setInspOcular(Boolean.TRUE);
//			}
//			else if (flagEstadoInsp==1){
//				setInspOcular(Boolean.FALSE);
//			}

		} catch (Exception ex) {
			ex.printStackTrace();
			WebMessages.messageFatal(ex);
			addErrorMessage(ex.getMessage());
		}
	}
	
	
	public void actualizarInspeccion() throws Exception {
		try{
			
			/***
			 * Cargando...
			 */
				lstRequisito = valResBo.obtenerRequisitosExpediente(getRequerimientoId(),getSessionManaged().getContribuyente().getPersonaId());
			
				List<MpTipoCondicionEspecial> lMptipocondicionespecial=valResBo.obtenerCondicionExpediente(getRequerimientoId());
				Iterator<MpTipoCondicionEspecial> it1 = lMptipocondicionespecial.iterator();  
				lsttipocondicionespecial=new ArrayList<SelectItem>();
				 
		        while (it1.hasNext()){
		        	MpTipoCondicionEspecial obj1 = it1.next();  
		        	lsttipocondicionespecial.add(new SelectItem(obj1.getDescripcion(),String.valueOf(obj1.getTipoCondEspecialId())));  
		        	mapMpTipocondicionespecial.put(obj1.getDescripcion().trim(), obj1.getTipoCondEspecialId());
		        	mapIMpTipocondicionespecial.put(obj1.getTipoCondEspecialId(), obj1.getDescripcion().trim());

		        } 
	        	String valueTipoDocumento=(mapIMpTipocondicionespecial.get(lMptipocondicionespecial.get(0).getTipoCondEspecialId())!=null)?mapIMpTipocondicionespecial.get(lMptipocondicionespecial.get(0).getTipoCondEspecialId()):"";
	        	setCmbValuetipocondicionespecial(valueTipoDocumento);
	        	setCmbValuetipocondicionespecialAdult(valueTipoDocumento);

			
			
			/***
			 * Registro
			 */
			Integer tipo=2;
				//mpInspeccionCondicionEspecial.setDescripcion(motivo);
				mpInspeccionCondicionEspecial.setFlagSituacion(0);
				mpInspeccionCondicionEspecial.setRequerimientoId(getRequerimientoId());
				mpInspeccionCondicionEspecial.setUsuarioId(getUser().getUsuarioId());
				mpInspeccionCondicionEspecial.setTerminal(getUser().getTerminal());
				
				
				valResBo.registrarInspecion(mpInspeccionCondicionEspecial,tipo);
//				setIsInspeccion(Boolean.TRUE);
				setInspOcular(0);
				editarExpediente(getRequerimientoId(),getFlagD(),getFlagI(),getEstado());

		} catch (Exception ex) {
			ex.printStackTrace();
			WebMessages.messageFatal(ex);
			addErrorMessage(ex.getMessage());
		}
	}
	
	public void notificar() {
		try {
			List<NoMotivoNotificacion> lsNoMotivoNotificacion = valResBo.getAlNoMotivoNotificacion(1);
			Iterator<NoMotivoNotificacion> it1 = lsNoMotivoNotificacion.iterator();
			lstTipoNotificacion = new ArrayList<SelectItem>();
			while (it1.hasNext()) {
				NoMotivoNotificacion obj01 = it1.next();
				lstTipoNotificacion.add(new SelectItem(obj01.getDescripcion(),
						String.valueOf(obj01.getMotivoNotificacionId())));
			}
			/** NOTIFICADORES
			 * */
			List<NoNotificador> lstNotificador = valResBo.getAllNotificador();
			for (NoNotificador noNotificador : lstNotificador) {
				lstSelectItemsNotificador.add(new SelectItem(noNotificador
						.getApellidosNombres(), String.valueOf(noNotificador
						.getNotificadorId())));
				mapNotificador.put(noNotificador.getApellidosNombres().trim(),
						noNotificador.getNotificadorId());
			}
			/** NOTIFICADORES
			 * */
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	

	
	public void obtenerCondicionEspecial(){
		try{
			//MpTipoCondicionEspecial
	        List<MpTipoCondicionEspecial> lMptipocondicionespecial=valResBo.getAllMpTipoCondicionEspecial(getSessionManaged().getContribuyente().getTipoPersonaId(),getSessionManaged().getContribuyente().getSubtipoPersonaId());
			Iterator<MpTipoCondicionEspecial> it1 = lMptipocondicionespecial.iterator();  
			lsttipocondicionespecial=new ArrayList<SelectItem>();
			 
	        while (it1.hasNext()){
	        	MpTipoCondicionEspecial obj1 = it1.next();  
	        	lsttipocondicionespecial.add(new SelectItem(obj1.getDescripcion(),String.valueOf(obj1.getTipoCondEspecialId())));  
	        	mapMpTipocondicionespecial.put(obj1.getDescripcion().trim(), obj1.getTipoCondEspecialId());
	        } 

		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	public void obtenerDocumento(Integer estadoReq,Integer req,Integer inspeccion,Integer estadoI) {
		try {
			 //MpTipoDocumento
	        List<MpTipoDocumentoCondicionEspecial> lGntipodocumento=valResBo.getAllMpTipoDocumentoCondicionEspecial();
			Iterator<MpTipoDocumentoCondicionEspecial> it2 = lGntipodocumento.iterator();  
			setLsttipodocumentoCondicionEspecial(new ArrayList<SelectItem>());
			 
	        while (it2.hasNext()){
	        	MpTipoDocumentoCondicionEspecial obj1 = it2.next();  
	        	getLsttipodocumentoCondicionEspecial().add(new SelectItem(obj1.getDescripcion(),String.valueOf(obj1.getTipoDocumentoCondicionEspecialId())));  
	        	mapMpTipodocumentoCondicionEspecial.put(obj1.getDescripcion().trim(), obj1.getTipoDocumentoCondicionEspecialId());
	        }
	        
		      //EstadoCondicionEspecial
	        List<ReEstadoResolucion> lReEstado=valResBo.getAllEstadoCondicionEspecial(findRequerimientoItem.getCondicionRequerimientoId());
			Iterator<ReEstadoResolucion> it3 = lReEstado.iterator();  
			lstEstadoCondicionEspecial=new ArrayList<SelectItem>();
			 
	        while (it3.hasNext()){
	        	ReEstadoResolucion estado = it3.next();  					
	        	lstEstadoCondicionEspecial.add(new SelectItem(estado.getDescripcion(),String.valueOf(estado.getEstadoResolucionId())));  
	        	mapEstado.put(estado.getDescripcion().trim(), estado.getEstadoResolucionId());
	        } 
	        
       
	        if (((estadoReq==Constante.ESTADO_REQ_CON_REQUERIMIENTO) && req ==Constante.ESTADO_INACTIVADO)){
	       	        	addErrorMessage("Debe cerrar el requerimiento de subsanación");
	        }else if ((estadoReq==Constante.ESTADO_REQ_CON_REQUERIMIENTO || (estadoReq==Constante.ESTADO_REQ_EN_TRAMITE)) && inspeccion==Constante.ESTADO_ACTIVOS && estadoI== Constante.ESTADO_ACTIVOS){
	        			addErrorMessage("Debe cerrar la Inspección Ocular");
	        }
	        
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public void cancelarCondicionEspecial()throws Exception{
		try{
			limpiar();
			}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
			
	public void limpiar(){
		setCmbValuetipodocumentoCondicionEspecial("");
		porcentaje = null;
		nroDocumento=null;
		fechaDocumento=null;
		fechaInicioCond=null;
		fechaFinCond=null;
		setCmbValueEstadoCondicionEspecial("");
		sustento=null;
	}
	
	//imprimirRequerimiento
	public void imprimirRequerimiento() {
		try {
			
					String cadena = null;
					String cadena2 = null;
					java.sql.Connection connection = null;
				
					/**Requerimiento de Subsanación: */

							cadena = "mp_reporte_condicion_especial_req.jasper";
							cadena2 = "Requerimiento Nº " + '-'
									+ findRequerimientoItem.getNroRequerimiento();
						
					CrudServiceBean connj = CrudServiceBean.getInstance();
					connection = connj.connectJasper();
					String path_context = FacesContext.getCurrentInstance()
							.getExternalContext().getRealPath("/");
					String path_report = path_context + "/sisat/reportes/";
					String path_imagen = path_context
							+ "/sisat/reportes/imagen/";
					HashMap<String, Object> parameters = new HashMap<String, Object>();
					Integer id = findRequerimientoItem.getCondicionRequerimientoId();
					parameters.put("p_id",findRequerimientoItem.getCondicionRequerimientoId());
					parameters.put("ruta_imagen", path_imagen);
					parameters.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
					JasperPrint jasperPrint = JasperFillManager.fillReport(
							(SATWEBParameterFactory.getPathReporte() + cadena),
							parameters, connection);
					
					ByteArrayOutputStream output = new ByteArrayOutputStream();
					JasperExportManager.exportReportToPdfStream(jasperPrint,
							output);
					HttpServletResponse response = (HttpServletResponse) FacesContext
							.getCurrentInstance().getExternalContext()
							.getResponse();
					response.setContentType("application/pdf");
					response.addHeader("Content-Disposition",
							"attachment;filename=" + cadena2 + ".pdf");
					response.setContentLength(output.size());
					ServletOutputStream servletOutputStream = response
							.getOutputStream();
					servletOutputStream.write(output.toByteArray(), 0,
							output.size());
					servletOutputStream.flush();
					servletOutputStream.close();
					FacesContext.getCurrentInstance().responseComplete();
				} catch (Exception e) {
					e.printStackTrace();
					WebMessages.messageError(e.getMessage());
				
		} 
		
	}

	public void imprimirResolucion() {
		try {
					String cadena = null;
					String cadena2 = null;
					java.sql.Connection connection = null;
				
					/**Fundada e Infundada: */
					if (findRequerimientoItem.getEstadoResolucion()== 1) {
							cadena = "mp_reporte_condicion_especial_resolucion.jasper";
							cadena2 = "Res Nº " + '-'
									+ findRequerimientoItem.getNroDocResol();
					}
					/** Improcedente:  */
					else if (findRequerimientoItem.getEstadoResolucion() == 5) {
						cadena = "mp_reporte_condicion_especial_resolucion_improcedente.jasper";
						cadena2 = "Res Nº " + '-'
								+ findRequerimientoItem.getNroDocResol();
					}
					/** Infundada:  */
					else if (findRequerimientoItem.getEstadoResolucion() == 4) {
						cadena = "mp_reporte_condicion_especial_resolucion_infundada.jasper";
						cadena2 = "Res Nº " + '-'
								+ findRequerimientoItem.getNroDocResol();
					}
					
					CrudServiceBean connj = CrudServiceBean.getInstance();
					connection = connj.connectJasper();
					String path_context = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
					String path_report = path_context + "/sisat/reportes/";
					String path_imagen = path_context
							+ "/sisat/reportes/imagen/";
					HashMap<String, Object> parameters = new HashMap<String, Object>();
					Integer id = findRequerimientoItem.getCondicionRequerimientoId();
					parameters.put("p_id", id);
					parameters.put("ruta_imagen", path_imagen);
					parameters.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
					JasperPrint jasperPrint = JasperFillManager.fillReport(
							(SATWEBParameterFactory.getPathReporte() + cadena),
							parameters, connection);
					
					ByteArrayOutputStream output = new ByteArrayOutputStream();
					HttpServletResponse response = (HttpServletResponse) FacesContext
							.getCurrentInstance().getExternalContext()
							.getResponse();
	
					if(findRequerimientoItem.getEstadoResolucion() != 4){
						JasperExportManager.exportReportToPdfStream(jasperPrint,output);
					
						response.setContentType("application/pdf");
						response.addHeader("Content-Disposition","attachment;filename=" + cadena.replaceAll(".jasper", ".pdf"));
					}else{
						JRRtfExporter rtfExporter = new JRRtfExporter();  
					    rtfExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);  
					    rtfExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, output);  
					    rtfExporter.exportReport();
							
						response.setContentType("application/doc");
						response.addHeader("Content-Disposition","attachment;filename=" + cadena.replaceAll(".jasper", ".doc"));
					}
					
					response.setContentLength(output.size());
					ServletOutputStream servletOutputStream = response.getOutputStream();
					
					servletOutputStream.write(output.toByteArray(), 0,output.size());
					
					servletOutputStream.flush();
					servletOutputStream.close();
					FacesContext.getCurrentInstance().responseComplete();
				} catch (Exception e) {
					e.printStackTrace();
					WebMessages.messageError(e.getMessage());
				
		} 
		
	}
	
	public void imprimirInspeccion() {
		try {
			
					String cadena = null;
					String cadena2 = null;
					java.sql.Connection connection = null;
				
					/**Requerimiento de Inspección: */

							cadena = "mp_reporte_condicion_especial_inspeccion.jasper";
							cadena2 = "Requerimiento Inspección";
						
					CrudServiceBean connj = CrudServiceBean.getInstance();
					connection = connj.connectJasper();
					String path_context = FacesContext.getCurrentInstance()
							.getExternalContext().getRealPath("/");
					String path_report = path_context + "/sisat/reportes/";
					String path_imagen = path_context
							+ "/sisat/reportes/imagen/";
					HashMap<String, Object> parameters = new HashMap<String, Object>();
					Integer id = findRequerimientoItem.getCondicionRequerimientoId();
					parameters.put("p_req_id",getRequerimientoId());
					parameters.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
					JasperPrint jasperPrint = JasperFillManager.fillReport(
							(SATWEBParameterFactory.getPathReporte() + cadena),
							parameters, connection);
					
					ByteArrayOutputStream output = new ByteArrayOutputStream();
					JasperExportManager.exportReportToPdfStream(jasperPrint,
							output);
					HttpServletResponse response = (HttpServletResponse) FacesContext
							.getCurrentInstance().getExternalContext()
							.getResponse();
					response.setContentType("application/pdf");
					response.addHeader("Content-Disposition",
							"attachment;filename=" + cadena2 + ".pdf");
					response.setContentLength(output.size());
					ServletOutputStream servletOutputStream = response
							.getOutputStream();
					servletOutputStream.write(output.toByteArray(), 0,
							output.size());
					servletOutputStream.flush();
					servletOutputStream.close();
					FacesContext.getCurrentInstance().responseComplete();
					
//					lstRequerimientoCe=valResBo.getAllBandejaCondicionEspecial(getSessionManaged().getContribuyente().getPersonaId());
					
				} catch (Exception e) {
					e.printStackTrace();
					WebMessages.messageError(e.getMessage());
				
		} 
		
	}
	
	public void loadTipoCondicion(ValueChangeEvent event) {
		try {
			HtmlComboBox combo = (HtmlComboBox) event.getComponent();
			String value = combo.getValue().toString();
			String value1 = "1";

			if (value != null) {
				tipocondicionespecialId  = (Integer) mapMpTipocondicionespecial.get(value);
				setCmbValuetipocondicionespecial(value);
			}
			if (isNuevo==Boolean.TRUE){
			if(value!=null){
				lstRequisito = valResBo.getAllRequisitoByCondicionEspecial(tipocondicionespecialId,getSessionManaged().getContribuyente().getPersonaId());
				isPensionista=Boolean.TRUE;
			}
			if(value1!=null){
				lstRequisito = valResBo.getAllRequisitoByCondicionEspecial(tipocondicionespecialId,getSessionManaged().getContribuyente().getPersonaId());
				isPensionistaServ=Boolean.TRUE;
			}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public void loadFormaNotificacionById(ValueChangeEvent event) {
		try {

			HtmlComboBox combo = (HtmlComboBox) event.getComponent();
			String value = combo.getValue().toString();
			Integer flagUbicacion;
			if (value.equals("Ubicables")) {
				flagUbicacion = 1;
			} else if (value.equals("Inubicables")) {
				flagUbicacion = 2;
			} else {
				flagUbicacion = 3;
			}
			condicionAdministradoId=flagUbicacion;
			
			List<NoMotivoNotificacion> lsNoMotivoNotificacion = valResBo.getAlNoMotivoNotificacion(flagUbicacion);
			Iterator<NoMotivoNotificacion> it1 = lsNoMotivoNotificacion.iterator();
			lstTipoNotificacion = new ArrayList<SelectItem>();
			while (it1.hasNext()) {
				NoMotivoNotificacion obj01 = it1.next();
				lstTipoNotificacion.add(new SelectItem(obj01.getDescripcion(),
						String.valueOf(obj01.getMotivoNotificacionId())));
			}
			// verFormasNotificacion()
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public void loadNotificacionById(ValueChangeEvent event) {
		try {
			HtmlComboBox combo = (HtmlComboBox) event.getComponent();
			String value = combo.getValue().toString();
			if (value != null) {
				noNotificacionId = (Integer) mapTipoNotificacion.get(value);
				setCmbValueNoNotificacion(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public void loadNotificador(ValueChangeEvent event) {
		try {
			HtmlComboBox combo = (HtmlComboBox) event.getComponent();
			String value = combo.getValue().toString();
			if (value != null) {
				notificadorId = (Integer) mapNotificador.get(value);
				setCmbNotificador(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void loadMpTipoDocumentoCondicionEspecialById(ValueChangeEvent event) {
		try{
				HtmlComboBox combo=(HtmlComboBox) event.getComponent(); 
			    String value=combo.getValue().toString();
			
			    if(value!=null&&value.trim().length()>0){
				tipodocumentoId = (Integer)mapMpTipodocumentoCondicionEspecial.get(value);
				setCmbValuetipodocumentoCondicionEspecial(value);
				
				if(tipodocumentoId!=null){
//					nroDocumento=valResBo.getAllCorrelativoCondicionEspecial(tipodocumentoId); /* Línea comentada,registro de correlativo será manual, de acuerdo a correo institucional,Nov.2016*/
				}
			}
	
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	public void loadEstadoCondicionEspecialById(ValueChangeEvent event) {
		try {
			HtmlComboBox combo = (HtmlComboBox) event.getComponent();
			String value = combo.getValue().toString();
			if (value != null) {
				estadoCondicionEspecialId = (Integer) mapEstado.get(value);
				setCmbValueEstadoCondicionEspecial(value);
			}
			
			if (value!=null){
				if(value.compareTo(Constante.ESTADO_FUNDADA)!=0){
					setPorcentaje(0.00);
				}else if (value.compareTo(Constante.ESTADO_FUNDADA)==0){
					setPorcentaje(null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}


	
	public void loadTipoCondicionEspecial(ValueChangeEvent event) {
		try {
			HtmlComboBox combo = (HtmlComboBox) event.getComponent();
			String value = combo.getValue().toString();
			if (value != null) {
				tipocondicionespecialId  = (Integer) mapMpTipocondicionespecial.get(value);
				setCmbValuetipocondicionespecial(value);
			}
//			if(value!=null){
//				lstRequisito = valResBo.getAllRequisitoByCondicionEspecial(tipocondicionespecialId);
//				isPensionista=Boolean.TRUE;
//			}
			
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public void notificacionById(ValueChangeEvent event) {
		try {

			HtmlComboBox combo=(HtmlComboBox) event.getComponent(); 
		    String value=combo.getValue().toString();
		    Integer flagUbicacion;
			if (value.equals("Ubicables")) {
				flagUbicacion=1;
			} else if (value.equals("Inubicables")) {
				flagUbicacion=2;
			} else {
				flagUbicacion=3;
			}
    		   List<NoMotivoNotificacion> listaMotivo = valResBo.getAlNoMotivoNotificacion(flagUbicacion);
		       Iterator<NoMotivoNotificacion> it1 = listaMotivo.iterator();  
		       listaTipoNotificacion=new ArrayList<SelectItem>();
		        while (it1.hasNext()){
		        	NoMotivoNotificacion obj01 = it1.next();  
		        	listaTipoNotificacion.add(new SelectItem(obj01.getDescripcion(),String.valueOf(obj01.getMotivoNotificacionId())));  
		        	mapMotivoNotificacion.put(obj01.getDescripcion().trim(), obj01.getMotivoNotificacionId());
		        }
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public void motivoNotificacionById(ValueChangeEvent event) {
		try{
			HtmlComboBox combo=(HtmlComboBox) event.getComponent(); 
		    String value=combo.getValue().toString();
		    if(value!=null){
		    	motivoId = (Integer)mapMotivo.get(value);
		    	setCmbMotivo(value);
		    }
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		  }
	}
	
	
	public void notificadorById(ValueChangeEvent event) {
		try{
			HtmlComboBox combo=(HtmlComboBox) event.getComponent(); 
		    String value=combo.getValue().toString();
		    if(value!=null){
		    	notificaId = (Integer)mapNotificador.get(value);
				setCmbNotifica(value);
		    }
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		  }
	}

	public void salir(){
//		notificacion=new NoNotificacion();
	}
	
	public void notificaResolucion()throws Exception{
		try {
//			if (validar()) {
//				Integer notificacionid = generalBo.ObtenerCorrelativoTabla(
//						"no_notificacion", 1);
//				notificacion.setNotificacionId(notificacionid);
//				notificacion.setNotificadorId(notificadorId);
//				notificacion.setMotivoNotificacionId(noMotivoNotificacionId);
//				if (findCcActoItem.getActoId() != null	&& findCcActoItem.getActoId() > 0) {
//					notificacion.setActoId(findCcActoItem.getActoId());
//				}
//				if (findCcActoItem.getRecId() != null && findCcActoItem.getRecId() > 0) {
//					notificacion.setRecId(findCcActoItem.getRecId());
//					notificacion.setLoteId(findCcActoItem.getLoteId());
//				}
//				// notificacion.setNroCargo(findCcActoItem.getNroCargoNotificacion());//el
//				// cargo se genera automaticamente
//				notificacion.setFechaNotificacion(DateUtil
//						.dateToSqlTimestamp(fechaNotificacion));
//				notificacion.setEstado(Constante.ESTADO_ACTIVO);
//				controlycobranzaBo.create(notificacion);
//				controlycobranzaBo.actualizarActo(notificacion);
//				notificacion = new NoNotificacion();
//				setLstFindLote(controlycobranzaBo.getAllFindCcActo(nroLote,
//						nroActoRec, persona_id, tipoLote));
//				tipoAccion = null;
//				fechaNotificacion = null;
//				cmbNoRelacionPersona = null;
//				noMotivoNotificacionId = null;
//				cmbValueNoMotivoNotificacion = null;
//
//			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	public void notificarResolucion(){
		tipoAccion=Constante.TIPO_ACCION_NUEVO;
	}

	public List<MpCondicionEspecialRequisito> getLstRequisito() {
		return lstRequisito;
	}
	public void setLstRequisito(List<MpCondicionEspecialRequisito> lstRequisito) {
		
		this.lstRequisito = lstRequisito;
	}
	public List<SustentoCondicionEspecialDTO> getLstSustento() {
		return lstSustento;
	}
	public void setLstSustento(List<SustentoCondicionEspecialDTO> lstSustento) {
		this.lstSustento = lstSustento;
	}
	public List<SustentoCondicionEspecialDTO> getLstSustentoPersona() {
		return lstSustentoPersona;
	}
	public void setLstSustentoPersona(
			List<SustentoCondicionEspecialDTO> lstSustentoPersona) {
		this.lstSustentoPersona = lstSustentoPersona;
	}
	public boolean isFlagIncompleto() {
		return flagIncompleto;
	}
	public void setFlagIncompleto(boolean flagIncompleto) {
		this.flagIncompleto = flagIncompleto;
	}

	public HtmlComboBox getCmbtipocondicionespecial() {
		return cmbtipocondicionespecial;
	}

	public void setCmbtipocondicionespecial(HtmlComboBox cmbtipocondicionespecial) {
		this.cmbtipocondicionespecial = cmbtipocondicionespecial;
	}

	public List<SelectItem> getLsttipocondicionespecial() {
		return lsttipocondicionespecial;
	}

	public void setLsttipocondicionespecial(
			List<SelectItem> lsttipocondicionespecial) {
		this.lsttipocondicionespecial = lsttipocondicionespecial;
	}

	public HashMap<String, Integer> getMapMpTipocondicionespecial() {
		return mapMpTipocondicionespecial;
	}

	public void setMapMpTipocondicionespecial(
			HashMap<String, Integer> mapMpTipocondicionespecial) {
		this.mapMpTipocondicionespecial = mapMpTipocondicionespecial;
	}

	public String getCmbValuetipocondicionespecial() {
		return cmbValuetipocondicionespecial;
	}

	public void setCmbValuetipocondicionespecial(
			String cmbValuetipocondicionespecial) {
		this.cmbValuetipocondicionespecial = cmbValuetipocondicionespecial;
	}

	public List<MpTipoCondicionEspecial> getlMptipocondicionespecial() {
		return lMptipocondicionespecial;
	}

	public void setlMptipocondicionespecial(
			List<MpTipoCondicionEspecial> lMptipocondicionespecial) {
		this.lMptipocondicionespecial = lMptipocondicionespecial;
	}

	public Integer getTipocondicionespecialId() {
		return tipocondicionespecialId;
	}

	public void setTipocondicionespecialId(Integer tipocondicionespecialId) {
		this.tipocondicionespecialId = tipocondicionespecialId;
	}

	public Boolean getIsPensionista() {
		return isPensionista;
	}

	public void setIsPensionista(Boolean isPensionista) {
		this.isPensionista = isPensionista;
	}

	public List<MpRequerimientoCondicionEspecialDTO> getLstRequerimientoCe() {
		return lstRequerimientoCe;
	}

	public void setLstRequerimientoCe(
			List<MpRequerimientoCondicionEspecialDTO> lstRequerimientoCe) {
		this.lstRequerimientoCe = lstRequerimientoCe;
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

	public List<SelectItem> getLstTipoNotificacion() {
		return lstTipoNotificacion;
	}

	public void setLstTipoNotificacion(List<SelectItem> lstTipoNotificacion) {
		this.lstTipoNotificacion = lstTipoNotificacion;
	}

	public String getCmbValueNoNotificacion() {
		return cmbValueNoNotificacion;
	}

	public void setCmbValueNoNotificacion(String cmbValueNoNotificacion) {
		this.cmbValueNoNotificacion = cmbValueNoNotificacion;
	}

	public HtmlComboBox getCmbNoNotificacion() {
		return cmbNoNotificacion;
	}

	public void setCmbNoNotificacion(HtmlComboBox cmbNoNotificacion) {
		this.cmbNoNotificacion = cmbNoNotificacion;
	}

	public Integer getNoNotificacionId() {
		return noNotificacionId;
	}

	public void setNoNotificacionId(Integer noNotificacionId) {
		this.noNotificacionId = noNotificacionId;
	}

	public HashMap<String, Integer> getMapTipoNotificacion() {
		return mapTipoNotificacion;
	}

	public void setMapTipoNotificacion(HashMap<String, Integer> mapTipoNotificacion) {
		this.mapTipoNotificacion = mapTipoNotificacion;
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

	public HashMap<String, Integer> getMapNotificador() {
		return mapNotificador;
	}

	public void setMapNotificador(HashMap<String, Integer> mapNotificador) {
		this.mapNotificador = mapNotificador;
	}

	public List<SelectItem> getLstSelectItemsNotificador() {
		return lstSelectItemsNotificador;
	}

	public void setLstSelectItemsNotificador(
			List<SelectItem> lstSelectItemsNotificador) {
		this.lstSelectItemsNotificador = lstSelectItemsNotificador;
	}

	public Date getFechaNotificacion() {
		return fechaNotificacion;
	}

	public void setFechaNotificacion(Date fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public MpRequerimientoCondicionEspecialDTO getFindRequerimientoItem() {
		return findRequerimientoItem;
	}

	public void setFindRequerimientoItem(
			MpRequerimientoCondicionEspecialDTO findRequerimientoItem) {
		this.findRequerimientoItem = findRequerimientoItem;
	}

	public Integer getCondicionAdministradoId() {
		return condicionAdministradoId;
	}

	public void setCondicionAdministradoId(Integer condicionAdministradoId) {
		this.condicionAdministradoId = condicionAdministradoId;
	}

	public Boolean getIsNotificado() {
		return isNotificado;
	}

	public void setIsNotificado(Boolean isNotificado) {
		this.isNotificado = isNotificado;
	}

	public HtmlComboBox getCmbtipodocumentoCondicionEspecial() {
		return cmbtipodocumentoCondicionEspecial;
	}

	public void setCmbtipodocumentoCondicionEspecial(
			HtmlComboBox cmbtipodocumentoCondicionEspecial) {
		this.cmbtipodocumentoCondicionEspecial = cmbtipodocumentoCondicionEspecial;
	}

	public List<SelectItem> getLsttipodocumentoCondicionEspecial() {
		return lsttipodocumentoCondicionEspecial;
	}

	public void setLsttipodocumentoCondicionEspecial(
			List<SelectItem> lsttipodocumentoCondicionEspecial) {
		this.lsttipodocumentoCondicionEspecial = lsttipodocumentoCondicionEspecial;
	}

	public HashMap<String, Integer> getMapMpTipodocumentoCondicionEspecial() {
		return mapMpTipodocumentoCondicionEspecial;
	}

	public void setMapMpTipodocumentoCondicionEspecial(
			HashMap<String, Integer> mapMpTipodocumentoCondicionEspecial) {
		this.mapMpTipodocumentoCondicionEspecial = mapMpTipodocumentoCondicionEspecial;
	}

	public String getCmbValuetipodocumentoCondicionEspecial() {
		return cmbValuetipodocumentoCondicionEspecial;
	}

	public void setCmbValuetipodocumentoCondicionEspecial(
			String cmbValuetipodocumentoCondicionEspecial) {
		this.cmbValuetipodocumentoCondicionEspecial = cmbValuetipodocumentoCondicionEspecial;
	}

	public Integer getTipodocumentoId() {
		return tipodocumentoId;
	}

	public void setTipodocumentoId(Integer tipodocumentoId) {
		this.tipodocumentoId = tipodocumentoId;
	}

	public GnCondicionEspecial getGnCondicionEspecial() {
		return gnCondicionEspecial;
	}

	public void setGnCondicionEspecial(GnCondicionEspecial gnCondicionEspecial) {
		this.gnCondicionEspecial = gnCondicionEspecial;
	}

	public Date getFechaDocumento() {
		return fechaDocumento;
	}

	public void setFechaDocumento(Date fechaDocumento) {
		this.fechaDocumento = fechaDocumento;
	}

	public Date getFechaInicioCond() {
		return fechaInicioCond;
	}

	public void setFechaInicioCond(Date fechaInicioCond) {
		this.fechaInicioCond = fechaInicioCond;
	}

	public Date getFechaFinCond() {
		return fechaFinCond;
	}

	public void setFechaFinCond(Date fechaFinCond) {
		this.fechaFinCond = fechaFinCond;
	}

	public String getNroDocumento() {
		return nroDocumento;
	}

	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}

	public Double getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(Double porcentaje) {
		this.porcentaje = porcentaje;
	}

	public String getCmbValueEstadoCondicionEspecial() {
		return cmbValueEstadoCondicionEspecial;
	}

	public void setCmbValueEstadoCondicionEspecial(
			String cmbValueEstadoCondicionEspecial) {
		this.cmbValueEstadoCondicionEspecial = cmbValueEstadoCondicionEspecial;
	}

	public HtmlComboBox getCmbtipoEstadoCondicionEspecial() {
		return cmbtipoEstadoCondicionEspecial;
	}

	public void setCmbtipoEstadoCondicionEspecial(
			HtmlComboBox cmbtipoEstadoCondicionEspecial) {
		this.cmbtipoEstadoCondicionEspecial = cmbtipoEstadoCondicionEspecial;
	}

	public List<SelectItem> getLstEstadoCondicionEspecial() {
		return lstEstadoCondicionEspecial;
	}

	public void setLstEstadoCondicionEspecial(
			List<SelectItem> lstEstadoCondicionEspecial) {
		this.lstEstadoCondicionEspecial = lstEstadoCondicionEspecial;
	}

	public Integer getEstadoCondicionEspecialId() {
		return estadoCondicionEspecialId;
	}

	public void setEstadoCondicionEspecialId(Integer estadoCondicionEspecialId) {
		this.estadoCondicionEspecialId = estadoCondicionEspecialId;
	}

	public HashMap<String, Integer> getMapEstado() {
		return mapEstado;
	}

	public void setMapEstado(HashMap<String, Integer> mapEstado) {
		this.mapEstado = mapEstado;
	}

	public String getSustento() {
		return sustento;
	}

	public void setSustento(String sustento) {
		this.sustento = sustento;
	}

	public Boolean getIsEdicion() {
		return isEdicion;
	}

	public void setIsEdicion(Boolean isEdicion) {
		this.isEdicion = isEdicion;
	}

	public Boolean getIsNuevo() {
		return isNuevo;
	}

	public void setIsNuevo(Boolean isNuevo) {
		this.isNuevo = isNuevo;
	}

	public Integer getIdR() {
		return idR;
	}

	public void setIdR(Integer idR) {
		this.idR = idR;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public MpInspeccionCondicionEspecial getMpInspeccionCondicionEspecial() {
		return mpInspeccionCondicionEspecial;
	}

	public void setMpInspeccionCondicionEspecial(
			MpInspeccionCondicionEspecial mpInspeccionCondicionEspecial) {
		this.mpInspeccionCondicionEspecial = mpInspeccionCondicionEspecial;
	}

	public Integer getRequerimientoId() {
		return requerimientoId;
	}

	public void setRequerimientoId(Integer requerimientoId) {
		this.requerimientoId = requerimientoId;
	}

	public Boolean getIsInspNuevo() {
		return isInspNuevo;
	}

	public void setIsInspNuevo(Boolean isInspNuevo) {
		this.isInspNuevo = isInspNuevo;
	}

	public HashMap<Integer, String> getMapIMpTipocondicionespecial() {
		return mapIMpTipocondicionespecial;
	}

	public void setMapIMpTipocondicionespecial(
			HashMap<Integer, String> mapIMpTipocondicionespecial) {
		this.mapIMpTipocondicionespecial = mapIMpTipocondicionespecial;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Integer getFlagD() {
		return flagD;
	}

	public void setFlagD(Integer flagD) {
		this.flagD = flagD;
	}

	public Integer getInspOcular() {
		return inspOcular;
	}

	public void setInspOcular(Integer inspOcular) {
		this.inspOcular = inspOcular;
	}

	public Integer getFlagR() {
		return flagR;
	}

	public void setFlagR(Integer flagR) {
		this.flagR = flagR;
	}

	public Boolean getIsInspeccion() {
		return isInspeccion;
	}

	public void setIsInspeccion(Boolean isInspeccion) {
		this.isInspeccion = isInspeccion;
	}

	public Integer getFlagI() {
		return flagI;
	}

	public void setFlagI(Integer flagI) {
		this.flagI = flagI;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Integer getTipoAccion() {
		return tipoAccion;
	}

	public void setTipoAccion(Integer tipoAccion) {
		this.tipoAccion = tipoAccion;
	}

	public String getCondicion() {
		return condicion;
	}

	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}

	public HtmlComboBox getCmbCondicion() {
		return cmbCondicion;
	}

	public void setCmbCondicion(HtmlComboBox cmbCondicion) {
		this.cmbCondicion = cmbCondicion;
	}

	public List<SelectItem> getListaTipoNotificacion() {
		return listaTipoNotificacion;
	}

	public void setListaTipoNotificacion(List<SelectItem> listaTipoNotificacion) {
		this.listaTipoNotificacion = listaTipoNotificacion;
	}

	public String getCmbMotivo() {
		return cmbMotivo;
	}

	public void setCmbMotivo(String cmbMotivo) {
		this.cmbMotivo = cmbMotivo;
	}

	public HtmlComboBox getCmbxMotivo() {
		return cmbxMotivo;
	}

	public void setCmbxMotivo(HtmlComboBox cmbxMotivo) {
		this.cmbxMotivo = cmbxMotivo;
	}

	public Integer getMotivoId() {
		return motivoId;
	}

	public void setMotivoId(Integer motivoId) {
		this.motivoId = motivoId;
	}

	public HashMap<String, Integer> getMapMotivo() {
		return mapMotivo;
	}

	public void setMapMotivo(HashMap<String, Integer> mapMotivo) {
		this.mapMotivo = mapMotivo;
	}

	public List<SelectItem> getListaMotivoNotificacion() {
		return listaMotivoNotificacion;
	}

	public void setListaMotivoNotificacion(List<SelectItem> listaMotivoNotificacion) {
		this.listaMotivoNotificacion = listaMotivoNotificacion;
	}

	public String getCmbNotifica() {
		return cmbNotifica;
	}

	public void setCmbNotifica(String cmbNotifica) {
		this.cmbNotifica = cmbNotifica;
	}

	public HtmlComboBox getCmbxNotificador() {
		return cmbxNotificador;
	}

	public void setCmbxNotificador(HtmlComboBox cmbxNotificador) {
		this.cmbxNotificador = cmbxNotificador;
	}

	public Integer getNotificaId() {
		return notificaId;
	}

	public void setNotificaId(Integer notificaId) {
		this.notificaId = notificaId;
	}

	public List<SelectItem> getListaNotificador() {
		return listaNotificador;
	}

	public void setListaNotificador(List<SelectItem> listaNotificador) {
		this.listaNotificador = listaNotificador;
	}

	public java.util.Date getFechaNotifica() {
		return fechaNotifica;
	}

	public void setFechaNotifica(java.util.Date fechaNotifica) {
		this.fechaNotifica = fechaNotifica;
	}

	public HashMap<String, Integer> getMapMotivoNotificacion() {
		return mapMotivoNotificacion;
	}

	public void setMapMotivoNotificacion(
			HashMap<String, Integer> mapMotivoNotificacion) {
		this.mapMotivoNotificacion = mapMotivoNotificacion;
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

	public Boolean getIsPensionistaServ() {
		return isPensionistaServ;
	}

	public void setIsPensionistaServ(Boolean isPensionistaServ) {
		this.isPensionistaServ = isPensionistaServ;
	}

	public String getCmbValuetipocondicionespecialAdult() {
		return cmbValuetipocondicionespecialAdult;
	}

	public void setCmbValuetipocondicionespecialAdult(String cmbValuetipocondicionespecialAdult) {
		this.cmbValuetipocondicionespecialAdult = cmbValuetipocondicionespecialAdult;
	}

	public List<MpCondicionEspecialRequisito> getLstRequisitoAdult() {
		return lstRequisitoAdult;
	}

	public void setLstRequisitoAdult(List<MpCondicionEspecialRequisito> lstRequisitoAdult) {
		this.lstRequisitoAdult = lstRequisitoAdult;
	}

	
}
