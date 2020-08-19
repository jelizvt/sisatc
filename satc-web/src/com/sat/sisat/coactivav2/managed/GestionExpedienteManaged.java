package com.sat.sisat.coactivav2.managed;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.taglibs.standard.tag.common.xml.ForEachTag;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import com.sat.sisat.cobranzacoactiva.dto.TipoDocumentoDescargo;
import com.sat.sisat.cobranzacoactiva.business.CobranzaCoactivaBoRemote;
import com.sat.sisat.cobranzacoactiva.dto.CoCartera;
import com.sat.sisat.cobranzacoactiva.dto.GeneracionMasivaRecDTO;
import com.sat.sisat.cobranzacoactiva.dto.ExpedienteCoactivo;
import com.sat.sisat.cobranzacoactiva.dto.FindCcRecTipo;
import com.sat.sisat.cobranzacoactiva.dto.GestionCostas;
import com.sat.sisat.cobranzacoactiva.dto.GestionEventos;
import com.sat.sisat.cobranzacoactiva.dto.GestionValores;
import com.sat.sisat.common.dto.GenericDTO;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.FacesUtil;
import com.sat.sisat.common.util.SATWEBParameterFactory;
import com.sat.sisat.common.util.Util;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.controlycobranza.dto.FindCcRec;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.menus.business.MenuBoRemote;
import com.sat.sisat.menus.dto.SimpleMenuDTO;
import com.sat.sisat.persistence.CrudServiceBean;
import com.sat.sisat.persistence.entity.RjMes;

@ManagedBean
@ViewScoped

public class GestionExpedienteManaged extends BaseManaged {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	CobranzaCoactivaBoRemote cobranzaCoactivaBo;
	
	@EJB
	MenuBoRemote menuBo;

	private String nroCartera;
	private String nroExpediente;
	private String placa;
	private String nroPapeleta;
	private Integer contribuyenteId;
	private Integer generacionMasivaId;
	private Integer tipoDocumentoId;
	private String nroDocumento;
	private Date fechaDocumento;
	private String observacion;

	private Integer ejecutorID;
	private Integer auxiliarID;
	private Integer conceptoID;
	
		

	private List<GeneracionMasivaRecDTO> listaGeneracionMasiva = new ArrayList<GeneracionMasivaRecDTO>();
	private List<FindCcRec> listaRec = new ArrayList<FindCcRec>();
	private FindCcRec rec = new FindCcRec();

	public List<TipoDocumentoDescargo> listaDocumentoDescargo = new ArrayList<TipoDocumentoDescargo>();

	public List<TipoDocumentoDescargo> getListaDocumentoDescargo() {
		return listaDocumentoDescargo;
	}

	public void setListaDocumentoDescargo(List<TipoDocumentoDescargo> listaDocumentoDescargo) {
		this.listaDocumentoDescargo = listaDocumentoDescargo;
	}

	private List<ExpedienteCoactivo> records = new ArrayList<ExpedienteCoactivo>();
	private List<ExpedienteCoactivo> recordsgenerarREC = new ArrayList<ExpedienteCoactivo>();

	private List<GenericDTO> ejecutores = new ArrayList<GenericDTO>();
	private List<GenericDTO> auxiliares = new ArrayList<GenericDTO>();
	public List<GenericDTO> tributos =new ArrayList<GenericDTO>();



	private List<GestionValores> recordsValores = new ArrayList<GestionValores>();
	private List<GestionEventos> recordsEventos = new ArrayList<GestionEventos>();
	private List<GestionCostas> recordsCostas = new ArrayList<GestionCostas>();
	
		

	private ExpedienteCoactivo selExpediente = new ExpedienteCoactivo();
	private ExpedienteCoactivo selGestion = new ExpedienteCoactivo();

	private ExpedienteCoactivo expediente = new ExpedienteCoactivo();

	private GestionCostas selCostas = new GestionCostas();

	private GestionEventos selEvento = new GestionEventos();

	private List<FindCcRecTipo> listaTipoRec = new ArrayList<FindCcRecTipo>();
	private List<FindCcRecTipo> listaTipoRecMasivo = new ArrayList<FindCcRecTipo>();

	private Integer tipoRecId = Constante.RESULT_PENDING;
	private Integer tipoRecIdMasivo = Constante.RESULT_PENDING;

	private Integer carteraId = Constante.RESULT_PENDING;

	private List<ExpedienteCoactivo> expedienteContrib = new ArrayList<ExpedienteCoactivo>();

	private Boolean selTodos = Boolean.FALSE;

	private Double montoGasto;
	private java.util.Date fechaGasto;
	private String resenaGasto;
	private String comprobanteGasto;

	// cc:inicio 14/07/2016 Lista de Documentos generados por tipo de rec para una
	// determinada cartera
	private List<CoCartera> listaDocDescarga = new ArrayList<CoCartera>();
	private CoCartera selDocDescarga = new CoCartera();
	// cc:fin
	
	// INICIO PERMISOS PARA EL MODULO -=CRAMIREZ=-
	 	private List<SimpleMenuDTO> listPermisosSubmenu = new ArrayList<SimpleMenuDTO>();
	 	
	 	private boolean permisoBuscar;	
	 	private boolean permisoVerDetalle;
	 	private boolean permisoModificarActualizar;
	 	private boolean permisoDescargaPDF;
	 	private boolean permisoAdjuntar;
	 	private boolean permisoBloquerDesbloquearDeuda;
	 	private boolean permisoEliminaREC;
	 // FIN PERMISOS PARA EL MODULO

	@PostConstruct
	public void init() throws Exception {
		permisosMenu();
		try {
			listaGeneracionMasiva = cobranzaCoactivaBo
					.getGeneracionMasivaRec(getSessionManaged().getUsuarioLogIn().getUsuarioId());

			// listaGeneracionMasiva=cobranzaCoactivaBo.getGeneracionMasivaRec(33);

			listaDocumentoDescargo = cobranzaCoactivaBo.getAllTipoDocumento();
			listaTipoRec = cobranzaCoactivaBo.getAllTipoRec(Boolean.FALSE);
			listaTipoRecMasivo = cobranzaCoactivaBo.getAllTipoRec(Boolean.TRUE);
			selExpediente = new ExpedienteCoactivo();
			selGestion = new ExpedienteCoactivo();
			expediente = new ExpedienteCoactivo();

			ejecutores = cobranzaCoactivaBo.listarEjecutorCoactivo();
			auxiliares = cobranzaCoactivaBo.listarAuxiliarCoactivo();
			tributos=cobranzaCoactivaBo.getTributos();
			
			ejecutorID=getSessionManaged().getUsuarioLogIn().getUsuarioId();
			auxiliarID=getSessionManaged().getUsuarioLogIn().getUsuarioId();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void permisosMenu() {	
		try {
			int submenuId = Constante.GESTION_DE_EXPEDIENTES;
		 	
			int permisoBuscarId = Constante.BUSCAR;
			int permisoVerDetalleId = Constante.VER_DETALLE;
			int permisoModificarActualizarId = Constante.MODIFICAR_ACTUALIZAR;	
			int permisoDescargaPDFId = Constante.DESCARGA_PDF;
			int permisoAdjuntarId = Constante.ADJUNTAR;
			int permisoBloquerDesbloquearDeudaId = Constante.BLOQUEAR_DESBLOQUEAR_DEUDA;
			int permisoEliminaRECId = Constante.ELIMINAR_REC;	
			
			permisoBuscar = false;	
		 	permisoVerDetalle = false;
		 	permisoModificarActualizar = false;
		 	permisoDescargaPDF = false;
		 	permisoAdjuntar = false;
		 	permisoBloquerDesbloquearDeuda = false;
		 	permisoEliminaREC = false;
			
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
				if(lsm.getItemId() == permisoModificarActualizarId) {
					permisoModificarActualizar = true;
				}
				if(lsm.getItemId() == permisoDescargaPDFId) {
					permisoDescargaPDF = true;
				}
				if(lsm.getItemId() == permisoAdjuntarId) {
					permisoAdjuntar = true;
				}
				if(lsm.getItemId() == permisoBloquerDesbloquearDeudaId) {
					permisoBloquerDesbloquearDeuda = true;
				}
				if(lsm.getItemId() == permisoEliminaRECId) {
					permisoEliminaREC = true;
				}
			}
		} catch (SisatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	public void gestionTodos() {
		selGestion = new ExpedienteCoactivo();
	}

	public void gestionExpediente() {
		selGestion = selExpediente;
	}

	public void registraAsignacionGasto() {
		try {
			if (selGestion != null && selGestion.getExpedienteId() != null && selGestion.getExpedienteId() > 0
					&& selGestion.getRecId() != null && selGestion.getRecId() > 0) {
				cobranzaCoactivaBo.registraGastosExpediente(selGestion.getExpedienteId(), montoGasto, fechaGasto,
						resenaGasto, comprobanteGasto, selGestion.getRecId(), getSessionManaged().getTerminalLogIn(),
						getSessionManaged().getUsuarioLogIn().getUsuarioId());
				recordsEventos = cobranzaCoactivaBo.gestionConsultaEventosExpediente(selExpediente.getExpedienteId());
				recordsCostas = cobranzaCoactivaBo.gestionConsultaCostasExpediente(selExpediente.getExpedienteId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void descargoCostas() {
		// Descargamos las costas o gastos.
		// cobranzaCoactivaBo.descargaCosta(selCostas,getTipoDocumentoId(),nroDocumento,
		// fechaDocumento, observacion);
		try {
			// Verificamos que la información este completa.
			if (this.selCostas == null || tipoDocumentoId == null || tipoDocumentoId == 0 || fechaDocumento == null) {
				addErrorMessage(
						getMsg("Debe completar la información, antes de proceder al descargo de costas/gastos."));
				return;
			}

			//
			if (cobranzaCoactivaBo.descargaCosta(this.selCostas, tipoDocumentoId, nroDocumento, fechaDocumento,
					observacion, getSessionManaged().getTerminalLogIn(),
					getSessionManaged().getUsuarioLogIn().getUsuarioId()) == 1)
				addErrorMessage(getMsg("El descargo se realizó correctamente."));
			else
				WebMessages.messageInfo("No se pudo realizar el descargo de la deuda.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void buscarMasiva() {
		try {
			if (getGeneracionMasivaId() == null || getGeneracionMasivaId() == 0)
			{
				WebMessages.messageInfo("DDebe seleccionar una Generación..");
				return;
			}
			// Extraemos las REC, generadas masivamente.
			setListaRec(cobranzaCoactivaBo.listaRecMasiva(generacionMasivaId));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void buscar() {
		try {
			if (nroCartera != null && nroCartera.trim().length() > 0) {
				CoCartera cartera = cobranzaCoactivaBo.consultaCartera(nroCartera);
				carteraId = cartera.getCarteraId();
				// cchaucca:inicio 14/07/2016 obtiene el listado de tipos de rec generados por
				// cartera
				if (Util.nvl(carteraId, 0) > 0) {
					// ------listaDocDescarga=cobranzaCoactivaBo.obtenerCarteraGenMasiva(carteraId);
				}
				// cchaucca:fin
			}

			// Limpiamos la lista
			records.clear();

			records = cobranzaCoactivaBo.gestionConsultaCartera(placa, nroCartera, nroExpediente, nroPapeleta,
					contribuyenteId, getSessionManaged().getCoPerfil().getMateriaId(),
					getSessionManaged().getUsuarioLogIn().getUsuarioId());

			recordsValores = new ArrayList<GestionValores>();
			recordsEventos = new ArrayList<GestionEventos>();
			recordsCostas = new ArrayList<GestionCostas>();

			selExpediente = new ExpedienteCoactivo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void registraAsignacionRec() {
		try {
			if (selGestion.getExpedienteId() > 0) {
				String mensaje = cobranzaCoactivaBo.validaGeneraRec(selGestion.getExpedienteId(), tipoRecId);
				if (Util.nvl(mensaje, "").length() > 0) {
					WebMessages.messageError(mensaje);
				} else {
					cobranzaCoactivaBo.registraRecExpediente(String.valueOf(selGestion.getExpedienteId()), conceptoID,tipoRecId,
							getSessionManaged().getUsuarioLogIn().getUsuarioId(),
							ejecutorID,auxiliarID,
							getSessionManaged().getTerminalLogIn());
					recordsEventos = cobranzaCoactivaBo.gestionConsultaEventosExpediente(selGestion.getExpedienteId());
					recordsCostas = cobranzaCoactivaBo.gestionConsultaCostasExpediente(selGestion.getExpedienteId());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void registraAsignacionRecMasivo() {
		//Verificamos q los expedients esten corectos.
		if (recordsgenerarREC.size()==0 || recordsgenerarREC==null)
		{
			WebMessages.messageInfo("La lista de expedientes está vacia.");
			return;
		}

		
		try {
			StringBuffer listId = new StringBuffer();

			for (ExpedienteCoactivo obj : recordsgenerarREC) 
				listId.append(obj.getExpedienteId()).append(",");
			

			if (listId.toString().length() > 0) {
				cobranzaCoactivaBo.registraRecExpediente(listId.toString(), conceptoID,tipoRecId,
						getSessionManaged().getUsuarioLogIn().getUsuarioId(), 
						ejecutorID,auxiliarID,
						getSessionManaged().getTerminalLogIn()
						);
				buscar();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void downloadEventos() {
		try {
			if (selExpediente != null && selExpediente.getExpedienteId() != null
					&& selExpediente.getExpedienteId() > 0) {
				java.sql.Connection connection = null;
				try {
					CrudServiceBean connj = CrudServiceBean.getInstance();
					connection = connj.connectJasper();
					String path_context = SATWEBParameterFactory.getPathReporte();
					String path_report = path_context + "coactiva/";
					// String path_imagen = path_context+ "imagen/";
					HashMap<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("p_expediente_id", selExpediente.getExpedienteId());
					parameters.put("p_nro_expediente", selExpediente.getNroExpediente());
					parameters.put("p_nombre_contrib", selExpediente.getApellidosNombresPersona());

					parameters.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
					JasperPrint jasperPrint = JasperFillManager
							.fillReport((path_report + "co_reporte_consulta_gestion.jasper"), parameters, connection);
					ByteArrayOutputStream output = new ByteArrayOutputStream();
					JasperExportManager.exportReportToPdfStream(jasperPrint, output);

					HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance()
							.getExternalContext().getResponse();

					response.setContentType("application/pdf");
					response.addHeader("Content-Disposition",
							"attachment;filename=" + "reporte_consulta_gestion" + ".pdf");
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
						e.getStackTrace();
					}
				}
			} else {
				addErrorMessage(getMsg("Seleccione expediente"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void registraResenaGestion() {
		try {
			if (selEvento != null && selEvento.getGestionExpedienteId() != null
					&& selEvento.getGestionExpedienteId() > 0) {
				cobranzaCoactivaBo.registraObsGestion(selEvento.getGestionExpedienteId(), selEvento.getObservacion(),
						getSessionManaged().getUsuarioLogIn().getUsuarioId(), getSessionManaged().getTerminalLogIn());
				recordsEventos = cobranzaCoactivaBo.gestionConsultaEventosExpediente(selExpediente.getExpedienteId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void verEventos() {
		try {
			if (selExpediente != null) {
				recordsEventos = cobranzaCoactivaBo.gestionConsultaEventosExpediente(selExpediente.getExpedienteId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void verDetalle() {

		try {
			if (selExpediente != null) {
				recordsValores = cobranzaCoactivaBo.gestionConsultaValores(selExpediente.getExpedienteId());
				recordsEventos = cobranzaCoactivaBo.gestionConsultaEventosExpediente(selExpediente.getExpedienteId());
				recordsCostas = cobranzaCoactivaBo.gestionConsultaCostasExpediente(selExpediente.getExpedienteId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void valueChangeListenerSelTodos(ValueChangeEvent ev) {
		String nv = ev.getNewValue().toString();
		if (nv.equals("true")) {
			for (ExpedienteCoactivo detalle : expedienteContrib) {
				detalle.setSelected(Boolean.TRUE);
			}
		} else {
			for (ExpedienteCoactivo detalle : expedienteContrib) {
				detalle.setSelected(Boolean.FALSE);
			}
		}
	}

	public void acumularExpedientes() {
		try {
			StringBuffer listDetalleId = new StringBuffer();
			for (ExpedienteCoactivo detalle : expedienteContrib) {
				if (detalle.isSelected()) {
					listDetalleId.append(detalle.getExpedienteId()).append(",");
				}
			}
			if (listDetalleId.toString().length() > 0) {
				cobranzaCoactivaBo.registraAcumulacionExpediente(listDetalleId.toString(), selGestion.getExpedienteId(),
						selGestion.getPersonaId(), getSessionManaged().getTerminalLogIn(),
						getSessionManaged().getUsuarioLogIn().getUsuarioId());

				Integer tipoRecAcumId = 109;// Resolucion de acumulacion para el expediente acumulador
				cobranzaCoactivaBo.registraRecExpediente(String.valueOf(selGestion.getExpedienteId()),conceptoID, tipoRecAcumId,						
						getSessionManaged().getUsuarioLogIn().getUsuarioId(),
						ejecutorID,auxiliarID,
						getSessionManaged().getTerminalLogIn());
			}
			verDetalle();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void desAcumularExpedientes() {
		try {
			StringBuffer listDetalleId = new StringBuffer();
			for (ExpedienteCoactivo detalle : expedienteContrib) {
				if (detalle.isSelected()) {
					listDetalleId.append(detalle.getExpedienteId()).append(",");
				}
			}
			if (listDetalleId.toString().length() > 0) {
				cobranzaCoactivaBo.registraDesAcumulacionExpediente(listDetalleId.toString(),
						selGestion.getExpedienteId(), selGestion.getPersonaId(), getSessionManaged().getTerminalLogIn(),
						getSessionManaged().getUsuarioLogIn().getUsuarioId());
			}
			verDetalle();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void expedientesContribuyenteAcum() {
		try {
			gestionExpediente();
			expedienteContrib = cobranzaCoactivaBo.consultarExpedientesPersonaAcum(selGestion.getPersonaId(),
					selGestion.getExpedienteId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void expedientesContribuyenteDesAcum() {
		try {
			gestionExpediente();
			expedienteContrib = cobranzaCoactivaBo.consultarExpedientesPersonaDesAcum(selGestion.getPersonaId(),
					selGestion.getExpedienteId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getFormatoJasper(Integer tipoRecId, Boolean tributario) {
		String formatoJasper = null;

		switch (tipoRecId) {
		case 101: // Emisión de Resolución de Ejecución Coactiva de inicio de procedimiento
			if (tributario)
				formatoJasper = "co_rec_inicio_tributaria.jasper";
			else
				formatoJasper = "co_rec_inicio_notributaria.jasper";
			break;

		case 102: // Embargo en forma de Intervencion en Recaudación
			formatoJasper = "co_rc_embargo_recaudacion.jasper";
			break;
		case 103: // Embargo en forma de intervención en Información
			formatoJasper = "co_rc_embargo_informacion.jasper";
			break;
		case 104: // Embargo en forma de Deposito o Secuestro Conservativo
			formatoJasper = "co_rc_embargo_secuestro_conservativo.jasper";
			break;
		case 105: // Embargo en forma de Depósito con extracción de bienes
			formatoJasper = "co_rc_embargo_deposito_conextraccion.jasper";
			break;
		case 106: // Embargo en forma de Inscripción
			formatoJasper = "co_rc_embargo_inscripcion_inmuebles.jasper";
			break;
		case 107:
			formatoJasper = "co_rc_embargo_retencion_bancos.jasper";
			break;
		case 108: // Emisión de Resolución de Suspensión
			formatoJasper = "co_rc_suspencion.jasper";
			break;
		case 109: // Emisión de Resolución de Acumulacion
			formatoJasper = "co_rc_acumulacion_expedientes.jasper";
			break;
		case 110: // Levantamiento de embargo
			formatoJasper = "co_rc_levantamiento.jasper";
			break;
		case 111: // Requerimiento de pago
			formatoJasper = "co_rc_requerimiento_pago.jasper";
			break;
		default: // 112: //Resolucion de formato Libre
			formatoJasper = "co_rc_formato_libre.jasper";
			break;
		}

		return formatoJasper;

		/*
		 * Descontinuado //Emisión de Resolución de Ejecución Coactiva de inicio de
		 * procedimiento if (tipoRecId == 101) { if (tributario) { formatoJasper =
		 * "co_rec_inicio_tributaria.jasper"; } if (!tributario) { formatoJasper =
		 * "co_rec_inicio_notributaria.jasper"; } }
		 * 
		 * 
		 * //Embargo en forma de Intervencion en Recaudación if (tipoRecId == 102) {
		 * formatoJasper = "co_rc_embargo_recaudacion.jasper"; }
		 * 
		 * //Embargo en forma de intervención en Información if (tipoRecId == 103) {
		 * formatoJasper = "co_rc_embargo_informacion.jasper"; }
		 * 
		 * //Embargo en forma de Deposito o Secuestro Conservativo if (tipoRecId==104) {
		 * formatoJasper = "co_rc_embargo_secuestro_conservativo.jasper"; }
		 * 
		 * //Embargo en forma de Depósito con extracción de bienes if (tipoRecId ==
		 * 105){ formatoJasper = "co_rc_embargo_deposito_conextraccion.jasper"; }
		 * 
		 * //Embargo en forma de Inscripción if (tipoRecId == 106) { formatoJasper =
		 * "co_rc_embargo_inscripcion_inmuebles.jasper"; }
		 * 
		 * //Embargo en forma de Retención if (tipoRecId == 107) { formatoJasper =
		 * "co_rc_embargo_retencion_bancos.jasper"; }
		 * 
		 * //Emisión de Resolución de Suspensión if (tipoRecId == 108) { formatoJasper =
		 * "co_rc_suspencion.jasper"; }
		 * 
		 * //Emisión de Resolución de Acumulacion if (tipoRecId == 109) { formatoJasper
		 * = "co_rc_acumulacion_expedientes.jasper"; } //Levantamiento de embargo if
		 * (tipoRecId == 110) { formatoJasper = "co_rc_levantamiento.jasper"; }
		 * //Requerimiento de pago if (tipoRecId == 111) { formatoJasper =
		 * "co_rc_requerimiento_pago.jasper"; } //Resolucion de formato Libre if
		 * (tipoRecId == 112) { formatoJasper = "co_rc_formato_libre.jasper"; }
		 * 
		 * return formatoJasper;
		 */
	}

	private String getFormatoJasperMasivo(Integer tipoRecId, Boolean tributario) {
		String formatoJasper = null;

		// Emisión de Resolución de Ejecución Coactiva de inicio de procedimiento
		if (tipoRecId == 101) {
			if (tributario) {
				formatoJasper = "co_rec_inicio_tributaria_masivo.jasper";// ok

			}
			if (!tributario) {
				formatoJasper = "co_rec_inicio_notributaria_masivo.jasper";// ok
			}
		}

		// Embargo en forma de Intervencion en Recaudación
		if (tipoRecId == 102) {
			formatoJasper = "co_rc_embargo_recaudacion_masivo.jasper";// ok
		}

		// Embargo en forma de intervención en Información
		if (tipoRecId == 103) {
			formatoJasper = "co_rc_embargo_informacion_masivo.jasper";// ok
		}

		// Embargo en forma de Deposito o Secuestro Conservativo
		if (tipoRecId == 104) {
			formatoJasper = "co_rc_embargo_secuestro_conservativo_masivo.jasper";// ok
		}

		// Embargo en forma de Depósito con extracción de bienes
		if (tipoRecId == 105) {
			formatoJasper = "co_rc_embargo_deposito_conextraccion_masivo.jasper";// ok
		}

		// Embargo en forma de Inscripción
		if (tipoRecId == 106) {
			formatoJasper = "co_rc_embargo_inscripcion_inmuebles_masivo.jasper";// ok
		}

		// Embargo en forma de Retención
		if (tipoRecId == 107) {
			formatoJasper = "co_rc_embargo_retencion_bancos_masivo.jasper";// ok
		}

		// Emisión de Resolución de Suspensión
		if (tipoRecId == 108) {
			formatoJasper = "co_rc_suspencion_masivo.jasper";// ok
		}

		// No existe Emisión de Resolución de Acumulacion masivo
		// Levantamiento de embargo
		if (tipoRecId == 110) {
			formatoJasper = "co_rc_levantamiento_masivo.jasper";// ok
		}
		// Requerimiento de pago
		if (tipoRecId == 111) {
			formatoJasper = "co_rc_requerimiento_pago_masivo.jasper";// ok
		}
		return formatoJasper;
	}

	public void downloadListado() {
		java.sql.Connection connection = null;
		try {

			addErrorMessage(getMsg("Exportar excel."));

			// if(selDocDescarga!=null &&Util.nvl(selDocDescarga.getUltTipoRecId(),0)>0){
			if (generacionMasivaId != null) {

				// Buscamos en la lista el id <generacionmasivaid>
				GeneracionMasivaRecDTO resultado = new GeneracionMasivaRecDTO();
				for (GeneracionMasivaRecDTO busqueda : listaGeneracionMasiva) {
					if (busqueda.getGeneracionMasivaId() == this.getGeneracionMasivaId()) {
						resultado = busqueda;
						break;
					}
				}
				// Fin de búsqueda.

				// String
				// formatoJasper=getFormatoJasperMasivo(selDocDescarga.getUltTipoRecId(),selDocDescarga.getMateriaId()==1?true:false);
				// String
				// formatoJasper=getFormatoJasperMasivo(resultado.getTipoRecId(),resultado.getMateriaId()==1?true:false);
				String formatoJasper = "co_rec_tributaria_listado.jasper";

				CrudServiceBean connj = CrudServiceBean.getInstance();
				connection = connj.connectJasper();
				String path_context = SATWEBParameterFactory.getPathReporte();
				String path_report = path_context + "coactiva/";

				HashMap<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("generacion_masiva_id", resultado.getGeneracionMasivaId());
				// parameters.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));

				JasperPrint jasperPrint = JasperFillManager.fillReport((path_report + formatoJasper), parameters,
						connection);

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
				exporterXls.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "Ops_inubicables_predial" + ".xls");
				exporterXls.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, output);
				exporterXls.exportReport();

				HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance()
						.getExternalContext().getResponse();
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition",
						"attachment;filename=" + "reporte_generacion_listado" + ".xls");
				response.setContentLength(output.size());
				ServletOutputStream servletOutputStream = response.getOutputStream();
				servletOutputStream.write(output.toByteArray(), 0, output.size());
				servletOutputStream.flush();
				servletOutputStream.close();
				FacesContext.getCurrentInstance().responseComplete();

				/*
				 * 
				 * HttpServletResponse response = (HttpServletResponse)
				 * FacesContext.getCurrentInstance().getExternalContext().getResponse();
				 * JasperExportManager.exportReportToPdfStream(jasperPrint,output);
				 * 
				 * response.setContentType("application/vnd.ms-excel");
				 * response.addHeader("Content-Disposition","attachment;filename=" +
				 * formatoJasper.replaceAll(".jasper", ".xls"));
				 * response.setContentLength(output.size()); ServletOutputStream
				 * servletOutputStream = response.getOutputStream();
				 * servletOutputStream.write(output.toByteArray(), 0,output.size());
				 * servletOutputStream.flush(); servletOutputStream.close();
				 * FacesContext.getCurrentInstance().responseComplete();
				 */

			}
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

	}

	public void downloadMasivo() {	
		
		
		java.sql.Connection connection = null;
		try {

			// if(selDocDescarga!=null &&Util.nvl(selDocDescarga.getUltTipoRecId(),0)>0){
			if (generacionMasivaId != null) {

				// Buscamos en la lista el id <generacionmasivaid>
				GeneracionMasivaRecDTO resultado = new GeneracionMasivaRecDTO();
				
				for (GeneracionMasivaRecDTO busqueda : listaGeneracionMasiva) {
					if (busqueda.getGeneracionMasivaId() == this.getGeneracionMasivaId()) {
						resultado = busqueda;
						break;
					}
				}
				// Fin de búsqueda.

				// String
				// formatoJasper=getFormatoJasperMasivo(selDocDescarga.getUltTipoRecId(),selDocDescarga.getMateriaId()==1?true:false);
				//String formatoJasper = getFormatoJasperMasivo(resultado.getTipoRecId(),resultado.getMateriaId() == 1 ? true : false);
				String formatoJasper = resultado.getArchivoJasper();
				

				CrudServiceBean connj = CrudServiceBean.getInstance();
				connection = connj.connectJasper();
				String path_context = SATWEBParameterFactory.getPathReporte();
				String path_report = path_context + "coactiva/";
				String path_imagen = path_context + "imagen/";
				HashMap<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("p_generacion_masiva_id", resultado.getGeneracionMasivaId());
				//parameters.put("p_usuario", getSessionManaged().getUsuarioLogIn().getUsuarioId());
				parameters.put("ruta_imagen", path_imagen);
				parameters.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
				JasperPrint jasperPrint = JasperFillManager.fillReport((path_report + formatoJasper), parameters,
						connection);
				ByteArrayOutputStream output = new ByteArrayOutputStream();

				HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance()
						.getExternalContext().getResponse();
				JasperExportManager.exportReportToPdfStream(jasperPrint, output);

				response.setContentType("application/pdf");
				response.addHeader("Content-Disposition",
						"attachment;filename=" + formatoJasper.replaceAll(".jasper", ".pdf"));
				response.setContentLength(output.size());
				ServletOutputStream servletOutputStream = response.getOutputStream();
				servletOutputStream.write(output.toByteArray(), 0, output.size());
				servletOutputStream.flush();
				servletOutputStream.close();
				FacesContext.getCurrentInstance().responseComplete();
			}
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
	}

	public void downloadGeneradas() {

		if (rec == null || rec.getExpedienteId() == null) {
			addErrorMessage(getMsg("Seleccione  un registro válido"));
			return;
		}

		try {
			java.sql.Connection connection = null;

			try {
				Boolean tributario = false;
				if (rec.getTipoDocumentoId() == 19) {
					tributario = true;

				}

				String formatoJasper = getFormatoJasper(rec.getTipoRecId(), tributario);

				CrudServiceBean connj = CrudServiceBean.getInstance();
				connection = connj.connectJasper();
				String path_context = SATWEBParameterFactory.getPathReporte();
				String path_report = path_context + "coactiva/";
				String path_imagen = path_context + "imagen/";
				HashMap<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("p_rec_id", rec.getRecId());
				parameters.put("p_expediente_id", rec.getExpedienteId());
				parameters.put("p_usuario", getSessionManaged().getUsuarioLogIn().getUsuarioId());
				parameters.put("ruta_imagen", path_imagen);
				parameters.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
				JasperPrint jasperPrint = JasperFillManager.fillReport((path_report + formatoJasper), parameters,
						connection);
				ByteArrayOutputStream output = new ByteArrayOutputStream();

				HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance()
						.getExternalContext().getResponse();

				JasperExportManager.exportReportToPdfStream(jasperPrint, output);
				response.setContentType("application/pdf");
				response.addHeader("Content-Disposition",
						"attachment;filename=" + formatoJasper.replaceAll(".jasper", ".pdf"));

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
					e.getStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}

	}

	public void download() {
		try {
			if (selEvento != null && selEvento.getExpedienteId() != null && selEvento.getExpedienteId() > 0
					&& selEvento.getRecId() != null && selEvento.getRecId() > 0) {
				java.sql.Connection connection = null;

				try {
					Boolean tributario = false;
					if (selEvento.getTipoDocumentoId() == 19) {
						tributario = true;
					}

					String formatoJasper = getFormatoJasper(selEvento.getTipoRecId(), tributario);

					CrudServiceBean connj = CrudServiceBean.getInstance();
					connection = connj.connectJasper();
					String path_context = SATWEBParameterFactory.getPathReporte();
					String path_report = path_context + "coactiva/";
					String path_imagen = path_context + "imagen/";
					HashMap<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("p_rec_id", selEvento.getRecId());
					parameters.put("p_expediente_id", selExpediente.getExpedienteId());
					parameters.put("p_usuario", getSessionManaged().getUsuarioLogIn().getUsuarioId());
					parameters.put("ruta_imagen", path_imagen);
					parameters.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
					JasperPrint jasperPrint = JasperFillManager.fillReport((path_report + formatoJasper), parameters,
							connection);
					ByteArrayOutputStream output = new ByteArrayOutputStream();

					HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance()
							.getExternalContext().getResponse();

					if (selEvento.getDocumentoPdf() == 1) {
						JasperExportManager.exportReportToPdfStream(jasperPrint, output);

						response.setContentType("application/pdf");
						response.addHeader("Content-Disposition",
								"attachment;filename=" + formatoJasper.replaceAll(".jasper", ".pdf"));
					} else {
						JRRtfExporter rtfExporter = new JRRtfExporter();
						rtfExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
						rtfExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, output);
						rtfExporter.exportReport();

						response.setContentType("application/rtf");
						response.addHeader("Content-Disposition",
								"attachment;filename=" + formatoJasper.replaceAll(".jasper", ".rtf"));
					}

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
						e.getStackTrace();
					}
				}
			} else {
				addErrorMessage(getMsg("Seleccione registro valido"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void downloadAdj() {
		try {
			CrudServiceBean connj = CrudServiceBean.getInstance();

			ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			String path = ctx.getRealPath("/");

			String dtFile = path + "tmp" + File.separator + getSelEvento().getRecId() + ".pdf";
			getImageData(connj.connectJasper(), getSelEvento().getRecId(), dtFile);

			File f = new File(dtFile);
			if (f.exists() && !f.isDirectory()) {
				ByteArrayOutputStream output = new ByteArrayOutputStream();

				HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance()
						.getExternalContext().getResponse();

				response.setContentType("application/pdf");
				response.addHeader("Content-Disposition", "attachment;filename=" + getSelEvento().getRecId() + ".pdf");
				response.setContentLength(output.size());
				ServletOutputStream servletOutputStream = response.getOutputStream();

				FileInputStream fis = new FileInputStream(dtFile);
				try {
					int buffSize = 1024;
					byte[] buffer = new byte[buffSize];
					int len;
					while ((len = fis.read(buffer)) != -1) {
						servletOutputStream.write(buffer, 0, len);
						servletOutputStream.flush();
						response.flushBuffer();
					}
				} finally {
					servletOutputStream.close();
				}
				FacesContext.getCurrentInstance().responseComplete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getImageData(Connection conn, Integer recId, String fileName) {
		byte[] fileBytes;
		String query;
		try {
			query = "select documento from dbo.co_rec_documento where rec_id=? and estado=1";
			PreparedStatement state = conn.prepareStatement(query);
			state.setInt(1, recId);

			ResultSet rs = state.executeQuery();
			if (rs.next()) {
				fileBytes = rs.getBytes(1);
				OutputStream targetFile = new FileOutputStream(fileName);

				targetFile.write(fileBytes);
				targetFile.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void mantenerEvento() {
		try {
			/// using in FileUpload
			FacesUtil.closeSession("fileUploadRecManaged");
			getSessionMap().put("selectedRecId", selEvento.getRecId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void limpiarseleccion() {

		recordsgenerarREC = new ArrayList<ExpedienteCoactivo>();
	}

	private Integer existeExpedienteGenerarREC(Integer expedienteID) {
		for (int i = 0; i < recordsgenerarREC.size(); i++) {
			if (recordsgenerarREC.get(i).getExpedienteId() == expedienteID) {
				return i;
			}
		}

		// no existe.
		return -1;
	}

	public void quitarSeleccion() {
		Integer indice;
		indice = existeExpedienteGenerarREC(expediente.getExpedienteId());

		if (indice >= 0) {
			// Eliminamos el expediente.
			recordsgenerarREC.remove(expediente);
		}

	}

	public void agregarSelected() {
		// recordsgenerarREC = new ArrayList<ExpedienteCoactivo>();
		ExpedienteCoactivo objExp;

		for (int i = 0; i < records.size(); i++) {
			if (records.get(i).isSeleccion()) {
				objExp = new ExpedienteCoactivo();
				objExp = records.get(i);

				// Verificamos si el expediente no existe.
				if (existeExpedienteGenerarREC(objExp.getExpedienteId()) >= 0)
					continue;

				// Agergamos a la lista
				recordsgenerarREC.add(objExp);
			}

		}

	}

	public void seleccionarAll() {
		for (int i = 0; i < records.size(); i++) {
			records.get(i).setSeleccion(Boolean.TRUE);
		}
	}

	public void unseleccionarAll() {

		for (int i = 0; i < records.size(); i++) {
			records.get(i).setSeleccion(Boolean.FALSE);
		}
	}
	
	
	
	public void bloquedarDeuda() throws Exception
	{
		
		
		try {
			if (selExpediente == null)
			{
				addErrorMessage(getMsg("No ha seleccionado un expediente."));
				return;
			}
			
			if (selExpediente.isDeudaBloqueada())
				//Desbloquear
				cobranzaCoactivaBo.setEstadoBloqueoDeuda(selExpediente.getRecId(),"0",getSessionManaged().getUsuarioLogIn().getUsuarioId());
			else
				//Bloquear
				cobranzaCoactivaBo.setEstadoBloqueoDeuda(selExpediente.getRecId(),"1",getSessionManaged().getUsuarioLogIn().getUsuarioId());
			
			//
			
			selExpediente.setDeudaBloqueada(!selExpediente.isDeudaBloqueada());
			
			//verDetalle();
				
			
			
		} catch (Exception e) {
			addErrorMessage(getMsg("No ha seleccionado un expediente."));
			return;
		}
		
		
	}
	
	

	public String getNroCartera() {
		return nroCartera;
	}

	public void setNroCartera(String nroCartera) {
		this.nroCartera = nroCartera;
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

	public List<FindCcRecTipo> getListaTipoRec() {
		return listaTipoRec;
	}

	public void setListaTipoRec(List<FindCcRecTipo> listaTipoRec) {
		this.listaTipoRec = listaTipoRec;
	}

	public Integer getTipoRecId() {
		return tipoRecId;
	}

	public void setTipoRecId(Integer tipoRecId) {
		this.tipoRecId = tipoRecId;
	}

	public List<GestionValores> getRecordsValores() {
		return recordsValores;
	}

	public void setRecordsValores(List<GestionValores> recordsValores) {
		this.recordsValores = recordsValores;
	}

	public List<GestionEventos> getRecordsEventos() {
		return recordsEventos;
	}

	public void setRecordsEventos(List<GestionEventos> recordsEventos) {
		this.recordsEventos = recordsEventos;
	}

	public ExpedienteCoactivo getSelGestion() {
		return selGestion;
	}

	public void setSelGestion(ExpedienteCoactivo selGestion) {
		this.selGestion = selGestion;
	}

	public List<ExpedienteCoactivo> getExpedienteContrib() {
		return expedienteContrib;
	}

	public void setExpedienteContrib(List<ExpedienteCoactivo> expedienteContrib) {
		this.expedienteContrib = expedienteContrib;
	}

	public Integer getCarteraId() {
		return carteraId;
	}

	public void setCarteraId(Integer carteraId) {
		this.carteraId = carteraId;
	}

	public Boolean getSelTodos() {
		return selTodos;
	}

	public void setSelTodos(Boolean selTodos) {
		this.selTodos = selTodos;
	}

	public List<GestionCostas> getRecordsCostas() {
		return recordsCostas;
	}

	public void setRecordsCostas(List<GestionCostas> recordsCostas) {
		this.recordsCostas = recordsCostas;
	}

	public GestionEventos getSelEvento() {
		return selEvento;
	}

	public void setSelEvento(GestionEventos selEvento) {
		this.selEvento = selEvento;
	}

	public String getNroExpediente() {
		return nroExpediente;
	}

	public void setNroExpediente(String nroExpediente) {
		this.nroExpediente = nroExpediente;
	}

	public Integer getContribuyenteId() {
		return contribuyenteId;
	}

	public void setContribuyenteId(Integer contribuyenteId) {
		this.contribuyenteId = contribuyenteId;
	}

	public String getNroPapeleta() {
		return nroPapeleta;
	}

	public void setNroPapeleta(String nroPapeleta) {
		this.nroPapeleta = nroPapeleta;
	}

	public Double getMontoGasto() {
		return montoGasto;
	}

	public void setMontoGasto(Double montoGasto) {
		this.montoGasto = montoGasto;
	}

	public java.util.Date getFechaGasto() {
		return fechaGasto;
	}

	public void setFechaGasto(java.util.Date fechaGasto) {
		this.fechaGasto = fechaGasto;
	}

	public String getResenaGasto() {
		return resenaGasto;
	}

	public void setResenaGasto(String resenaGasto) {
		this.resenaGasto = resenaGasto;
	}

	public String getComprobanteGasto() {
		return comprobanteGasto;
	}

	public void setComprobanteGasto(String comprobanteGasto) {
		this.comprobanteGasto = comprobanteGasto;
	}

	public List<FindCcRecTipo> getListaTipoRecMasivo() {
		return listaTipoRecMasivo;
	}

	public void setListaTipoRecMasivo(List<FindCcRecTipo> listaTipoRecMasivo) {
		this.listaTipoRecMasivo = listaTipoRecMasivo;
	}

	public Integer getTipoRecIdMasivo() {
		return tipoRecIdMasivo;
	}

	public void setTipoRecIdMasivo(Integer tipoRecIdMasivo) {
		this.tipoRecIdMasivo = tipoRecIdMasivo;
	}

	// cchaucca:inicio 14/07/2016
	public List<CoCartera> getListaDocDescarga() {
		return listaDocDescarga;
	}

	public void setListaDocDescarga(List<CoCartera> listaDocDescarga) {
		this.listaDocDescarga = listaDocDescarga;
	}

	public CoCartera getSelDocDescarga() {
		return selDocDescarga;
	}

	public void setSelDocDescarga(CoCartera selDocDescarga) {
		this.selDocDescarga = selDocDescarga;
	}
	// cchaucca:fin.

	public List<GeneracionMasivaRecDTO> getListaGeneracionMasiva() {
		return listaGeneracionMasiva;
	}

	public void setListaGeneracionMasiva(List<GeneracionMasivaRecDTO> listaGeneracionMasiva) {
		this.listaGeneracionMasiva = listaGeneracionMasiva;
	}

	public List<FindCcRec> getListaRec() {
		return listaRec;
	}

	public void setListaRec(List<FindCcRec> listaRec) {
		this.listaRec = listaRec;
	}

	public FindCcRec getRec() {
		return rec;
	}

	public void setRec(FindCcRec rec) {
		this.rec = rec;
	}

	public Integer getGeneracionMasivaId() {
		return generacionMasivaId;
	}

	public void setGeneracionMasivaId(Integer generacionMasivaId) {
		this.generacionMasivaId = generacionMasivaId;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public Integer getTipoDocumentoId() {
		return tipoDocumentoId;
	}

	public void setTipoDocumentoId(Integer tipoDocumentoId) {
		this.tipoDocumentoId = tipoDocumentoId;
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

	public GestionCostas getSelCostas() {
		return selCostas;
	}

	public void setSelCostas(GestionCostas selCostas) {
		this.selCostas = selCostas;
	}

	public List<ExpedienteCoactivo> getRecordsgenerarREC() {
		return recordsgenerarREC;
	}

	public void setRecordsgenerarREC(List<ExpedienteCoactivo> recordsgenerarREC) {
		this.recordsgenerarREC = recordsgenerarREC;
	}

	public ExpedienteCoactivo getExpediente() {
		return expediente;
	}

	public void setExpediente(ExpedienteCoactivo expediente) {
		this.expediente = expediente;
	}

	public List<GenericDTO> getEjecutores() {
		return ejecutores;
	}

	public void setEjecutores(List<GenericDTO> ejecutores) {
		this.ejecutores = ejecutores;
	}

	public List<GenericDTO> getAuxiliares() {
		return auxiliares;
	}

	public void setAuxiliares(List<GenericDTO> auxiliares) {
		this.auxiliares = auxiliares;
	}

	public Integer getEjecutorID() {
		return ejecutorID;
	}

	public void setEjecutorID(Integer ejecutorID) {
		this.ejecutorID = ejecutorID;
	}

	public Integer getAuxiliarID() {
		return auxiliarID;
	}

	public void setAuxiliarID(Integer auxiliarID) {
		this.auxiliarID = auxiliarID;
	}
	
	public Integer getConceptoID() {
		return conceptoID;
	}

	public void setConceptoID(Integer conceptoID) {
		this.conceptoID = conceptoID;
	}
	
	public List<GenericDTO> getTributos() {
		return tributos;
	}

	public void setTributos(List<GenericDTO> tributos) {
		this.tributos = tributos;
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

	public boolean isPermisoModificarActualizar() {
		return permisoModificarActualizar;
	}

	public void setPermisoModificarActualizar(boolean permisoModificarActualizar) {
		this.permisoModificarActualizar = permisoModificarActualizar;
	}

	public boolean isPermisoDescargaPDF() {
		return permisoDescargaPDF;
	}

	public void setPermisoDescargaPDF(boolean permisoDescargaPDF) {
		this.permisoDescargaPDF = permisoDescargaPDF;
	}

	public boolean isPermisoAdjuntar() {
		return permisoAdjuntar;
	}

	public void setPermisoAdjuntar(boolean permisoAdjuntar) {
		this.permisoAdjuntar = permisoAdjuntar;
	}

	public boolean isPermisoBloquerDesbloquearDeuda() {
		return permisoBloquerDesbloquearDeuda;
	}

	public void setPermisoBloquerDesbloquearDeuda(boolean permisoBloquerDesbloquearDeuda) {
		this.permisoBloquerDesbloquearDeuda = permisoBloquerDesbloquearDeuda;
	}

	public boolean isPermisoEliminaREC() {
		return permisoEliminaREC;
	}

	public void setPermisoEliminaREC(boolean permisoEliminaREC) {
		this.permisoEliminaREC = permisoEliminaREC;
	}


}
