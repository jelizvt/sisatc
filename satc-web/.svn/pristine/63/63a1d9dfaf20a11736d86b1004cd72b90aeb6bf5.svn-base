package com.sat.sisat.fiscalizacion.managed;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.resource.cci.Record;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.richfaces.component.html.HtmlComboBox;
import org.richfaces.model.selection.SimpleSelection;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import com.sat.sisat.cobranzacoactiva.dto.FindCcLoteDetalleActoExp;
import com.sat.sisat.cobranzacoactiva.dto.FindCcLoteExigible;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.FacesUtil;
import com.sat.sisat.common.util.SATWEBParameterFactory;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.fiscalizacion.business.FiscalizacionBoRemote;
import com.sat.sisat.fiscalizacion.dto.FindInpscDocTipo;
import com.sat.sisat.fiscalizacion.dto.FindInspeccionById;
import com.sat.sisat.fiscalizacion.dto.FindInspeccionHistorial;
import com.sat.sisat.menus.business.MenuBoRemote;
import com.sat.sisat.menus.dto.SimpleMenuDTO;
import com.sat.sisat.papeleta.dto.FindPapeletas;
import com.sat.sisat.papeletas.managed.BuscarPersonaPapeletasManaged;
import com.sat.sisat.persistence.CrudServiceBean;
import com.sat.sisat.persistence.entity.MpTipoDocuIdentidad;
import com.sat.sisat.persistence.entity.RpFiscaInspeccion;
import com.sat.sisat.persona.business.PersonaBoRemote;
import com.sat.sisat.predial.dto.FindMpPersona;
import com.sat.sisat.predial.dto.FindRpDjPredial;
import com.sat.sisat.predial.managed.ConstruccionManaged;
import com.sat.sisat.predial.managed.ListaDJPredioManaged;
import com.sat.sisat.predial.managed.RegistroPredioManaged;
import com.sat.sisat.vehicular.cns.ReusoFormCns;
import com.sat.sisat.vehicular.dto.BuscarPersonaDTO;



@ManagedBean
@ViewScoped
public class BusquedaRequerimientoManaged extends BaseManaged  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	FiscalizacionBoRemote ficalizacionBo;
	
	@EJB
	PersonaBoRemote personaBo;
	
	@EJB
	MenuBoRemote menuBo;
	
	private List<FindInspeccionHistorial> inspeccion = new ArrayList<FindInspeccionHistorial>();
	private FindInspeccionById findInspenccion = new FindInspeccionById();
	//BUSQUEDA:
	private FindInspeccionHistorial findInspenccionHistorial = new FindInspeccionHistorial();
	private List<FindInspeccionHistorial> inspecciones = new ArrayList<FindInspeccionHistorial>();

	private FindInspeccionHistorial currentItem;//FindInspeccionById
	
	//EXPORTAR:
	private List<FindInspeccionHistorial> lista;
	
	// OBTENEMOS EL TIPO DE DOC.: CARTA, REQUERIMIENTO
	private String cmbTipoDocsRequerimiento;
	private HtmlComboBox cmbxTipoDocsRequerimiento;
	private Integer tipoDocsRequerimientoId;
	private HashMap<String, Integer> mapTipoDocsRequerimiento = new HashMap<String, Integer>();
	private List<SelectItem> listaTipoDocsRequerimiento = new ArrayList<SelectItem>();
	private Boolean istipoCarta;
	private Boolean istipoReque;
	private Boolean istipoCartaMult;
	private String correlativoCarta;
	private String correlativoReque;
	private String correlativoCartaMult;
	
	// OBTENEMOS EL TIPO DE DOC. DE IDENTIDAD: DNI,CARNTE EXTRAN,ETC.
	private String cmbTipodocumento;
	private HtmlComboBox cmbxTipodocumento;
	private List<SelectItem> listaTipoDocumento=new ArrayList<SelectItem>();
	private Boolean isDNI=Boolean.FALSE;
	private Boolean isRUC=Boolean.FALSE;
	private String nroDocumentoIdentidad;
	private Integer tipodocumentoIdent;
	private HashMap<String, Integer> mapRpTipodocumento=new HashMap<String, Integer>();
	List<MpTipoDocuIdentidad> lMpTipoDocuIdentidad = null;
	
	//OBTENEMOS AL APELLIDO/RAZ. SOCIAL DE LA PERSONA:
	private BuscarPersonaDTO datosContribuyente;
	private Integer contribuyenteId;
	
	
	private SimpleSelection selectedUsuario;
	private int rowIndexSelected = -1;
	
	private Integer personaId;
	private String  direccionNombre;
	private String  documentoNumero;
	private Integer dniId;
	private String  dniNumero;
	private String  apellidos;
	
	private List<FindInspeccionHistorial> inspeccionResultado = new ArrayList<FindInspeccionHistorial>();
	//variables para obtener la ubicacion
	private Integer inspeccionId;
	private Integer paquete;
	private Integer annioPaquete;
	
	// INICIO PERMISOS PARA EL MODULO -=CRAMIREZ=-
	 	private List<SimpleMenuDTO> listPermisosSubmenu = new ArrayList<SimpleMenuDTO>();	 	
	 	private boolean permisoBuscar;
	 	private boolean permisoAgregarRegistrar;
	 	private boolean permisoModificarActualizar;
	// FIN PERMISOS PARA EL MODULO

			public BusquedaRequerimientoManaged() throws Exception {
				
			}
			
			@PostConstruct
			public void init() throws Exception {
				permisosMenu();
				try {

					
					/* COMBOBOX:: TIPO DE DOCUMENTO */
					List<FindInpscDocTipo> lstCcTipoRec = ficalizacionBo.getAllTipoDoc();
					Iterator<FindInpscDocTipo> it1 = lstCcTipoRec.iterator();
					listaTipoDocsRequerimiento = new ArrayList<SelectItem>();

					while (it1.hasNext()) {
						FindInpscDocTipo obj = it1.next();
						listaTipoDocsRequerimiento.add(new SelectItem(obj.getDescripcionTipoDocumento(), String.valueOf(obj.getTipoDocumentoId())));
						mapTipoDocsRequerimiento.put(obj.getDescripcionTipoDocumento().trim(),obj.getTipoDocumentoId());
					}
					/*- COMBOBOX:: TIPO DE DOCUMENTO -*/
					
					/* COMBOBOX:: TIPO DE DOCUMENTO DE IDENTIDAD */
					List<MpTipoDocuIdentidad> lstMpTipoDocuIdentidad=personaBo.getAllMpTipoDocumento();
					Iterator<MpTipoDocuIdentidad> iter = lstMpTipoDocuIdentidad.iterator();  
					listaTipoDocumento=new ArrayList<SelectItem>();
					 
			        while (iter.hasNext()){
			        	MpTipoDocuIdentidad obj = iter.next();  
			        	listaTipoDocumento.add(new SelectItem(obj.getDescrpcionCorta(),String.valueOf(obj.getTipoDocIdentidadId())));  
			        	mapRpTipodocumento.put(obj.getDescrpcionCorta().trim(), obj.getTipoDocIdentidadId());
			        }
			        /* COMBOBOX:: TIPO DE DOCUMENTO DE IDENTIDAD */
			        
					inspeccion = ficalizacionBo.getAllInspecciones();
					
					//recibiendo desde registropredio
					Integer per = (Integer) getSessionMap().get("currentItemPersona");
					if(per !=null){
						personaId=per;
					}
					//FacesUtil.closeSession("RegistroPredioManaged");
					//recibiendo desde listadjpredio
					Integer per2 = (Integer) getSessionMap().get("currentItemPersona2");
					if(per2 !=null){
						personaId=per2;
					}
					//FacesUtil.closeSession("ListaDJPredioManaged");
										
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
			}
			
			
			public void permisosMenu() {	
				try {
					int submenuId = Constante.CONTROL_DE_REQUERIMIENTOS;
				 	
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

			public String edit() {
				try {
					getSessionMap().put("findInspenccion", findInspenccion);
					FacesUtil.closeSession("registroPersonaManaged");
				} catch (Exception e) {
					e.printStackTrace();
					WebMessages.messageFatal(e);
				}
				return sendRedirectPrincipal();

			}
			

			public String actualizacion(){
				
				if(currentItem!=null){
					getSessionMap().put("currentItem", currentItem);
					
				}
//				closeSession("registroPersonaManaged");
//				closeSession("controlRequerimientoManaged");
				return sendRedirectPrincipal();

			}

			
			public void limpiar()throws Exception{
				
				inspeccion=new ArrayList<FindInspeccionHistorial>();
				currentItem=new FindInspeccionHistorial();
				
				setCmbTipoDocsRequerimiento("");
				setCorrelativoReque("");
				setCorrelativoCarta("");
				setCorrelativoCartaMult("");
				setCmbTipodocumento("");
				setNroDocumentoIdentidad("");
				setPersonaId(0);
				setDireccionNombre("");
				setDocumentoNumero("");
				
				datosContribuyente=new BuscarPersonaDTO();
				inspeccion = ficalizacionBo.getAllInspecciones();

			}
			

			public void loadTipoDocsRequerimiento(ValueChangeEvent event) {
				try {
					HtmlComboBox combo = (HtmlComboBox) event.getComponent();
					String value = combo.getValue().toString();
					if (value != null) {
						tipoDocsRequerimientoId  = (Integer) mapTipoDocsRequerimiento.get(value);
						setCmbTipoDocsRequerimiento(value);
					}
					if(value!=null){
						if(value.compareTo(Constante.TIPO_DOC_CARTA)==0)
						{
							istipoCarta=Boolean.TRUE;
							istipoReque=Boolean.FALSE;
							istipoCartaMult=Boolean.FALSE;
//							correlativoCarta=ficalizacionBo.correlativo(tipoDocRequerimientoId);
							
						}
						else if(value.toString().compareTo(Constante.TIPO_DOC_REQ)==0){
							
							istipoCarta=Boolean.FALSE;
							istipoReque=Boolean.TRUE;
							istipoCartaMult=Boolean.FALSE;
//							correlativoReque=ficalizacionBo.correlativo(tipoDocRequerimientoId);
							

						}else if (value.toString().compareTo(Constante.TIPO_DOC_CARTA_MULT)==0){
							
							istipoCarta=Boolean.FALSE;
							istipoReque=Boolean.FALSE;
							istipoCartaMult=Boolean.TRUE;
							

						}
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					WebMessages.messageFatal(e);
				}
			}
			

			public void loadTipoTipoDocumentoById(ValueChangeEvent event) {
				try{
					setIsDNI(Boolean.FALSE);
					setIsRUC(Boolean.FALSE);
					String value=(String)event.getNewValue();
					if(value!=null){
						tipodocumentoIdent = (Integer)mapRpTipodocumento.get(value);
						if(tipodocumentoIdent!=null){
							lMpTipoDocuIdentidad=personaBo.findMpTipoDocuIdentidad(tipodocumentoIdent);	
						}
						if(value.compareTo(Constante.TIPO_DOCUMENTO_DNI)==0){
							setIsDNI(Boolean.TRUE);
							setIsRUC(Boolean.FALSE);
						}
						if(value.compareTo(Constante.TIPO_DOCUMENTO_RUC)==0){
							setIsDNI(Boolean.FALSE);
							setIsRUC(Boolean.TRUE);
						}
					}
				}catch(Exception e){
					e.printStackTrace();
					WebMessages.messageFatal(e);			
				}
		    }
			
		
			public void setPersonaInspeccion(){
				String destinoRefresh = FacesUtil.getRequestParameter("destinoRefresh");
				
				BuscarRequerimientoContribuyenteManaged buscarPersonaManaged=(BuscarRequerimientoContribuyenteManaged)getManaged("buscarRequerimientoContribuyenteManaged");
				buscarPersonaManaged.setPantallaUso(ReusoFormCns.BUSQU_PER_INSPECCION);
				buscarPersonaManaged.setDestinoRefresh(destinoRefresh);
			}
			
						
			public void copiaPersona(BuscarPersonaDTO persona){
				setDatosContribuyente(persona);
		    }
			

			
			public void buscar() {

				try{
					inspeccion=new ArrayList<FindInspeccionHistorial>();
					
					if(personaId!=null&&personaId>0){
						   currentItem.setPersonaId(personaId);
						   inspeccion=ficalizacionBo.findInspeccion(personaId,null,null,null,null,null);
					}else if(direccionNombre!=null&&direccionNombre.trim().length()>0){
						inspeccion=ficalizacionBo.findInspeccion(null,null,direccionNombre,null,null,null);
					}else if(documentoNumero!=null&&documentoNumero.trim().length()>0){
						inspeccion=ficalizacionBo.findInspeccion(null,documentoNumero,null,null,null,null);
					}else if (datosContribuyente.getApellidosNombres()!=null&&datosContribuyente.getApellidosNombres().trim().length()>0 ){
						apellidos=datosContribuyente.getApellidosNombres();
						inspeccion=ficalizacionBo.findInspeccion(null,null,null,apellidos,null,null);
					}else if(getCmbTipodocumento()!=null && !getCmbTipodocumento().isEmpty()){
						if(getNroDocumentoIdentidad()!=null && !getNroDocumentoIdentidad().isEmpty()){
							setDniId(mapRpTipodocumento.get(getCmbTipodocumento()));
							setDniNumero(getNroDocumentoIdentidad());
						inspeccion=ficalizacionBo.findInspeccion(null,null,null,null,getDniId(),getDniNumero());
						}
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
			public void selectUsuarioAction() {
				if (selectedUsuario != null) {
					Iterator<Object> it = selectedUsuario.getKeys();
					while (it.hasNext()) {
						Object obj = it.next();
						try {
							rowIndexSelected = Integer.parseInt(String.valueOf(obj));
							FindInspeccionHistorial lexig=inspeccion.get(rowIndexSelected);
							currentItem=lexig;
//							lista = cobranzaCoactivaBo.getAllFindCcLoteExp(lexig
//									.getLoteId());
//							setLoteExigibleId(lexig.getLoteId());
//							setAnioLote(lexig.getAnnoLote());
//							setTipoActoId(lexig.getTipoActoId());
//							selectedRol = null;

						} catch (Exception ex) {
							System.out.println(ex);
						}
					}
				}
			}
			/**
			 * 
			 */
			

			
			public void imprimirReqxTipo(){
				try {
					if (currentItem != null) {
						java.sql.Connection connection = null;
						try {
							Integer concepto= currentItem.getTipoDocumentoId();
							String nroDocumento=currentItem.getNroRequerimiento();
							String cadena = null;
							String cadena2 = null;
							
							
								if (concepto == Constante.TIPO_DOC_CARTA_MULT_ID){
									
									cadena = "reporte_inspeccion_carta_inductiva.jasper";
									cadena2 = "CartaInductiva "+ nroDocumento;
								}else
								
								if (concepto == Constante.TIPO_DOC_CARTA_ID){
									
									cadena = "reporte_inspeccion_carta_presentacion.jasper";
									cadena2 = "CartaPresentacion " + nroDocumento;
								}else
								
								if (concepto == Constante.TIPO_DOC_REQ_ID){
									
									cadena = "reporte_inspeccion_requerimiento.jasper";
									cadena2 = "Requerimiento " + nroDocumento;
								}

							CrudServiceBean connj = CrudServiceBean.getInstance();
							connection = connj.connectJasper();
							String path_context = FacesContext.getCurrentInstance()
									.getExternalContext().getRealPath("/");
							String path_report = path_context + "/sisat/reportes/";
							String path_imagen = path_context
									+ "/sisat/reportes/imagen/";
							HashMap<String, Object> parameters = new HashMap<String, Object>();
							// parameters.put("SUBREPORT_CONNECTION", connec);
							Integer val = currentItem.getInspeccionId();
							
							//falta el tipo de rec como parametro
							parameters.put("p_inspeccion_id", val);
							parameters.put("ruta_imagen", path_imagen);
							parameters
									.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
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
						} finally {
							try {
								if (connection != null) {
									connection.close();
									connection = null;
								}
							} catch (Exception e) {
								e.getStackTrace();
							}
						}
					} else {
						addErrorMessage(getMsg("Seleccione un Requerimiento. Verifique!!!"));
					}
				} catch (Exception e) {
					e.printStackTrace();
					WebMessages.messageFatal(e);
				}	
			}
			
			
			
			/**
			 * 
			 */
			
			public void imprimirTapa(){
				try {
					if (currentItem != null) {
						java.sql.Connection connection = null;
						try {
							Integer inspeccion= currentItem.getInspeccionId();
							String documento=currentItem.getTipoDocumentoNombre();
							String nroDocumento=currentItem.getNroRequerimiento();
							String cadena = null;
							String cadena2 = null;
							
	
									cadena = "reporte_inspeccion_tapa.jasper";
									cadena2 = "Tapa - "+ documento +" " + nroDocumento;
								
							CrudServiceBean connj = CrudServiceBean.getInstance();
							connection = connj.connectJasper();
							String path_context = FacesContext.getCurrentInstance()
									.getExternalContext().getRealPath("/");
							String path_report = path_context + "/sisat/reportes/";
							String path_imagen = path_context
									+ "/sisat/reportes/imagen/";
							HashMap<String, Object> parameters = new HashMap<String, Object>();
							// parameters.put("SUBREPORT_CONNECTION", connec);
							
							
							//falta el tipo de rec como parametro
							parameters.put("p_inspeccion_id", inspeccion);
							parameters.put("ruta_imagen", path_imagen);
							parameters
									.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
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
						} finally {
							try {
								if (connection != null) {
									connection.close();
									connection = null;
								}
							} catch (Exception e) {
								e.getStackTrace();
							}
						}
					} else {
						addErrorMessage(getMsg("Seleccione un Requerimiento. Verifique!!!"));
					}
				} catch (Exception e) {
					e.printStackTrace();
					WebMessages.messageFatal(e);
				}	
			}
			
		
			public void imprimirResultado(){
				try {
				
					if (currentItem != null) {
						java.sql.Connection connection = null;
						try {
							Integer inspeccion= currentItem.getInspeccionId();
							String documento=currentItem.getTipoDocumentoNombre();
							String nroDocumento=currentItem.getNroRequerimiento();
							String cadena = null;
							String cadena2 = null;

							inspeccionResultado = ficalizacionBo.getAllInspecciones();
							
							if (inspeccion!=null) 
							{
										cadena = "reporte_inspeccion_resultado_dj.jasper";
										cadena2 = "Resultado de Requerimiento con DJ - "+ documento +" " + nroDocumento;
										
										
							}		
								
							CrudServiceBean connj = CrudServiceBean.getInstance();
							connection = connj.connectJasper();
							String path_context = FacesContext.getCurrentInstance()
									.getExternalContext().getRealPath("/");
							String path_report = path_context + "/sisat/reportes/";
							String path_imagen = path_context
									+ "/sisat/reportes/imagen/";
							HashMap<String, Object> parameters = new HashMap<String, Object>();
							// parameters.put("SUBREPORT_CONNECTION", connec);
							
							
							//falta el tipo de rec como parametro
							parameters.put("p_inspeccion_id", inspeccion);
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
						} finally {
							try {
								if (connection != null) {
									connection.close();
									connection = null;
								}
							} catch (Exception e) {
								e.getStackTrace();
							}
						}
					} else {
						addErrorMessage(getMsg("Seleccione un Requerimiento. Verifique!!!"));
					}
					//imprimirResultado2(currentItem.getInspeccionId());
				} catch (Exception e) {
					e.printStackTrace();
					WebMessages.messageFatal(e);
				}	
			}
			

			public void exportarTablaExcel(){
				
				Workbook workbook = new HSSFWorkbook();
				Sheet sheet = workbook.createSheet("Exportando a Excel");
				int rowIndex = 1;
				 Row row1 = sheet.createRow(0);
				 	row1.createCell(0).setCellValue("Tipo");
				    row1.createCell(1).setCellValue("Nro. Req./Carta");
				    row1.createCell(2).setCellValue("Fecha de Emisión");
				    row1.createCell(3).setCellValue("Fecha de Inspeccion");
				    row1.createCell(4).setCellValue("Inspector");
				    row1.createCell(5).setCellValue("Doc. Ident. Inspector");
				    row1.createCell(6).setCellValue("Fecha de Notificación");
				    row1.createCell(7).setCellValue("Codigo");
				    row1.createCell(8).setCellValue("Nombre de Contribuyente");
				    row1.createCell(9).setCellValue("Domic. Fiscal");
				    row1.createCell(10).setCellValue("Tipo Resultado");
				    row1.createCell(11).setCellValue("F.I.P./A.I.N.R. Número");
				    row1.createCell(12).setCellValue("F.I.P./A.I.N.R Fecha");
				    row1.createCell(13).setCellValue("Estado");
				    row1.createCell(14).setCellValue("Registra");

				  for(FindInspeccionHistorial data : inspeccion){
				    Row row = sheet.createRow(rowIndex++);
				    int columnIndex = 0;
				    
				    row.createCell(columnIndex++).setCellValue(data.getTipoDocumentoNombre());
				    row.createCell(columnIndex++).setCellValue(data.getNroRequerimiento());
				    row.createCell(columnIndex++).setCellValue(data.getFechaEmision());
				    if (data.getFechaInspeccion() != null) {
				    	row.createCell(columnIndex++).setCellValue(data.getFechaInspeccion());
					} else {
						row.createCell(columnIndex++).setCellValue("-");
				    }
				    row.createCell(columnIndex++).setCellValue(data.getInspectorNombre());
				    row.createCell(columnIndex++).setCellValue(data.getInspectorDni());	 
				    if ((data.getFechaNotificacion()) != null) {
						row.createCell(columnIndex++).setCellValue((data.getFechaNotificacion()));
					} else {
						row.createCell(columnIndex++).setCellValue("-");
					}
				    row.createCell(columnIndex++).setCellValue(data.getPersonaId());
				    row.createCell(columnIndex++).setCellValue(data.getApellidosNombres());
				    row.createCell(columnIndex++).setCellValue(data.getPersonaDomicilioFiscal());
				    row.createCell(columnIndex++).setCellValue(data.getResultadoNombre());	
				    row.createCell(columnIndex++).setCellValue(data.getResultadoNumero());
				    if (data.getFechaResultado() != null) {
				    	row.createCell(columnIndex++).setCellValue(data.getFechaResultado());
					} else {
						row.createCell(columnIndex++).setCellValue("-");
				    }
				    row.createCell(columnIndex++).setCellValue(data.getEstadoDescripcion());
				    row.createCell(columnIndex++).setCellValue(data.getNombreUsuario());

				}
				FacesContext context = FacesContext.getCurrentInstance();
				ExternalContext externalContext = context.getExternalContext();
				externalContext.responseReset(); 
				externalContext.setResponseContentType("application/vnd.ms-excel");
				externalContext.setResponseHeader("Content-Disposition", "attachment;filename=Control_Requerimientos.xls");
				try {
					workbook.write(externalContext.getResponseOutputStream());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				context.responseComplete(); // Prevent JSF from performing navigation.
			}
			
			public void generarReporte() {
				java.sql.Connection connection=null;
				try {
					CrudServiceBean connj=CrudServiceBean.getInstance();
					connection=connj.connectJasper();
					    
					HashMap<String, Object> parameters = new HashMap<String, Object>();
			
					
					JasperPrint jasperPrint = JasperFillManager.fillReport((SATWEBParameterFactory.getPathReporte()+"reporte_inspeccion.jasper"),parameters,connection);
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
					    exporterXls.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, System.currentTimeMillis() + "reporte_estado_papeleta" + ".xls");  
					    exporterXls.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, output);  
					    exporterXls.exportReport();

					HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
					response.setContentType("application/vnd.ms-excel");  
				    response.setHeader("Content-Disposition", "attachment;filename=" + DateUtil.getCurrentDate() + ".xls");  
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
			
			public void actualizarUbicacion() throws Exception{
				if(currentItem!=null){
					ficalizacionBo.actualizarUbicacion(this.currentItem.getInspeccionId(),this.currentItem.getPaquete(), this.currentItem.getAnnioPaquete(),this.currentItem.getExpediente());					
				}			
			}
			
			public List<FindInspeccionHistorial> getInspeccion() {
				return inspeccion;
			}

			public void setInspeccion(List<FindInspeccionHistorial> inspeccion) {
				this.inspeccion = inspeccion;
			}			
			public void setCurrentItem(FindInspeccionHistorial currentItem) {
				this.currentItem = currentItem;
			}
			public FindInspeccionHistorial getCurrentItem() {
				return currentItem;
			}			

			public FindInspeccionById getFindInspenccion() {
				return findInspenccion;
			}

			public void setFindInspenccion(FindInspeccionById findInspenccion) {
				this.findInspenccion = findInspenccion;
			}

			public String getCmbTipoDocsRequerimiento() {
				return cmbTipoDocsRequerimiento;
			}

			public void setCmbTipoDocsRequerimiento(String cmbTipoDocsRequerimiento) {
				this.cmbTipoDocsRequerimiento = cmbTipoDocsRequerimiento;
			}

			public HtmlComboBox getCmbxTipoDocsRequerimiento() {
				return cmbxTipoDocsRequerimiento;
			}

			public void setCmbxTipoDocsRequerimiento(HtmlComboBox cmbxTipoDocsRequerimiento) {
				this.cmbxTipoDocsRequerimiento = cmbxTipoDocsRequerimiento;
			}

			public Integer getTipoDocsRequerimientoId() {
				return tipoDocsRequerimientoId;
			}

			public void setTipoDocsRequerimientoId(Integer tipoDocsRequerimientoId) {
				this.tipoDocsRequerimientoId = tipoDocsRequerimientoId;
			}

			public HashMap<String, Integer> getMapTipoDocsRequerimiento() {
				return mapTipoDocsRequerimiento;
			}

			public void setMapTipoDocsRequerimiento(
					HashMap<String, Integer> mapTipoDocsRequerimiento) {
				this.mapTipoDocsRequerimiento = mapTipoDocsRequerimiento;
			}

			public List<SelectItem> getListaTipoDocsRequerimiento() {
				return listaTipoDocsRequerimiento;
			}

			public void setListaTipoDocsRequerimiento(
					List<SelectItem> listaTipoDocsRequerimiento) {
				this.listaTipoDocsRequerimiento = listaTipoDocsRequerimiento;
			}

			public String getCmbTipodocumento() {
				return cmbTipodocumento;
			}

			public void setCmbTipodocumento(String cmbTipodocumento) {
				this.cmbTipodocumento = cmbTipodocumento;
			}

			public HtmlComboBox getCmbxTipodocumento() {
				return cmbxTipodocumento;
			}

			public void setCmbxTipodocumento(HtmlComboBox cmbxTipodocumento) {
				this.cmbxTipodocumento = cmbxTipodocumento;
			}

			public List<SelectItem> getListaTipoDocumento() {
				return listaTipoDocumento;
			}

			public void setListaTipoDocumento(List<SelectItem> listaTipoDocumento) {
				this.listaTipoDocumento = listaTipoDocumento;
			}

			public Boolean getIsDNI() {
				return isDNI;
			}

			public void setIsDNI(Boolean isDNI) {
				this.isDNI = isDNI;
			}

			public Boolean getIsRUC() {
				return isRUC;
			}

			public void setIsRUC(Boolean isRUC) {
				this.isRUC = isRUC;
			}

			public String getNroDocumentoIdentidad() {
				return nroDocumentoIdentidad;
			}

			public void setNroDocumentoIdentidad(String nroDocumentoIdentidad) {
				this.nroDocumentoIdentidad = nroDocumentoIdentidad;
			}

			public BuscarPersonaDTO getDatosContribuyente() {
				return datosContribuyente;
			}

			public void setDatosContribuyente(BuscarPersonaDTO datosContribuyente) {
				this.datosContribuyente = datosContribuyente;
			}

			public Integer getContribuyenteId() {
				return contribuyenteId;
			}

			public void setContribuyenteId(Integer contribuyenteId) {
				this.contribuyenteId = contribuyenteId;
			}

			public Boolean getIstipoCarta() {
				return istipoCarta;
			}

			public void setIstipoCarta(Boolean istipoCarta) {
				this.istipoCarta = istipoCarta;
			}

			public Boolean getIstipoReque() {
				return istipoReque;
			}

			public void setIstipoReque(Boolean istipoReque) {
				this.istipoReque = istipoReque;
			}

			public FindInspeccionHistorial getFindInspenccionHistorial() {
				return findInspenccionHistorial;
			}

			public void setFindInspenccionHistorial(
					FindInspeccionHistorial findInspenccionHistorial) {
				this.findInspenccionHistorial = findInspenccionHistorial;
			}

			public List<FindInspeccionHistorial> getInspecciones() {
				return inspecciones;
			}

			public void setInspecciones(List<FindInspeccionHistorial> inspecciones) {
				this.inspecciones = inspecciones;
			}

			public String getCorrelativoCarta() {
				return correlativoCarta;
			}

			public void setCorrelativoCarta(String correlativoCarta) {
				this.correlativoCarta = correlativoCarta;
			}

			public String getCorrelativoReque() {
				return correlativoReque;
			}

			public void setCorrelativoReque(String correlativoReque) {
				this.correlativoReque = correlativoReque;
			}

			public SimpleSelection getSelectedUsuario() {
				return selectedUsuario;
			}

			public void setSelectedUsuario(SimpleSelection selectedUsuario) {
				this.selectedUsuario = selectedUsuario;
			}

			public int getRowIndexSelected() {
				return rowIndexSelected;
			}

			public void setRowIndexSelected(int rowIndexSelected) {
				this.rowIndexSelected = rowIndexSelected;
			}

			public Boolean getIstipoCartaMult() {
				return istipoCartaMult;
			}

			public void setIstipoCartaMult(Boolean istipoCartaMult) {
				this.istipoCartaMult = istipoCartaMult;
			}

			public String getCorrelativoCartaMult() {
				return correlativoCartaMult;
			}

			public void setCorrelativoCartaMult(String correlativoCartaMult) {
				this.correlativoCartaMult = correlativoCartaMult;
			}

			public Integer getPersonaId() {
				return personaId;
			}

			public void setPersonaId(Integer personaId) {
				this.personaId = personaId;
			}

			public String getDireccionNombre() {
				return direccionNombre;
			}

			public void setDireccionNombre(String direccionNombre) {
				this.direccionNombre = direccionNombre;
			}

			public List<FindInspeccionHistorial> getLista() {
				return lista;
			}

			public void setLista(List<FindInspeccionHistorial> lista) {
				this.lista = lista;
			}

			
			public String getDocumentoNumero() {
				return documentoNumero;
			}

			public void setDocumentoNumero(String documentoNumero) {
				this.documentoNumero = documentoNumero;
			}

			public Integer getDniId() {
				return dniId;
			}

			public void setDniId(Integer dniId) {
				this.dniId = dniId;
			}

			public String getDniNumero() {
				return dniNumero;
			}

			public void setDniNumero(String dniNumero) {
				this.dniNumero = dniNumero;
			}

			public String getApellidos() {
				return apellidos;
			}

			public void setApellidos(String apellidos) {
				this.apellidos = apellidos;
			}

			public Integer getPaquete() {
				return paquete;
			}

			public void setPaquete(Integer paquete) {
				this.paquete = paquete;
			}

			public Integer getAnnioPaquete() {
				return annioPaquete;
			}

			public void setAnnioPaquete(Integer annioPaquete) {
				this.annioPaquete = annioPaquete;
			}
			public Integer getInspeccionId() {
				return inspeccionId;
			}

			public void setInspeccionId(Integer inspeccionId) {
				this.inspeccionId = inspeccionId;
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
