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
import com.sat.sisat.persistence.CrudServiceBean;
import com.sat.sisat.persistence.entity.RjMes;

@ManagedBean
@ViewScoped

public class GeneracionREC extends BaseManaged {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	CobranzaCoactivaBoRemote cobranzaCoactivaBo;

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

	
	private List<FindCcRec> listaRec = new ArrayList<FindCcRec>();
	private FindCcRec rec = new FindCcRec();


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

	@PostConstruct
	public void init() throws Exception {
		try {						
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

	public void gestionTodos() {
		selGestion = new ExpedienteCoactivo();
	}

	public void gestionExpediente() {
		selGestion = selExpediente;
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


}
