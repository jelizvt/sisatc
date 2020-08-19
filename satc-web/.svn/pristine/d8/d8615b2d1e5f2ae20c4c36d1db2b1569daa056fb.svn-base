package com.sat.sisat.controlycobranza.managed;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import com.sat.sisat.alfresco.RepositoryManager;
import com.sat.sisat.common.business.GeneralBoRemote;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.Util;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.controlycobranza.business.ControlyCobranzaBoRemote;
import com.sat.sisat.controlycobranza.dto.FindCcLote;
import com.sat.sisat.controlycobranza.dto.FindCcLoteConcepto;
import com.sat.sisat.controlycobranza.dto.FindCcLoteDetalleActo;
import com.sat.sisat.controlycobranza.dto.FindCcLoteDetalleActoDeuda;
import com.sat.sisat.controlycobranza.dto.FindCcLoteOrdenImpresion;
import com.sat.sisat.controlycobranza.dto.FindCcLoteSector;
import com.sat.sisat.controlycobranza.dto.FindCcLoteTipoPersona;
import com.sat.sisat.persistence.entity.CcFirmante;
import com.sat.sisat.persistence.entity.CcLote;
import com.sat.sisat.persistence.entity.CcLoteActo;
import com.sat.sisat.persistence.entity.CcLoteCuota;
import com.sat.sisat.persistence.entity.CcLoteSchedule;
import com.sat.sisat.persistence.entity.CcLoteSchedulePK;
import com.sat.sisat.persistence.entity.PaDocuAnexo;
import com.sat.sisat.persona.business.PersonaBoRemote;
@ManagedBean
@ViewScoped
public class ProgramacionLoteOrdinarioManaged extends BaseManaged {
	@EJB
	PersonaBoRemote personaBo;
	
	@EJB
	GeneralBoRemote generalBo;
	
	@EJB
	ControlyCobranzaBoRemote controlycobranzaBo;
	
	private FindCcLote findCcLoteItem= new FindCcLote();
	private CcLoteActo ccLoteActo = new CcLoteActo();
	private CcLote ccLote = new CcLote();
	private Integer actoId;
	private String nroActo;
	private Integer personaId;
	private String  nombreCompleto;
	private String tipoActo;
	private List<CcFirmante> lstCcFirmantes = new ArrayList<CcFirmante>();
	
	private List<FindCcLoteConcepto> lstCcLoteConcepto = new ArrayList<FindCcLoteConcepto>();
	private List<CcLoteCuota> lstCcLoteCuota = new ArrayList<CcLoteCuota>();
	private List<FindCcLoteSector> lstFindCcLoteSector = new ArrayList<FindCcLoteSector>();
	private List<FindCcLoteTipoPersona> lstFindCcLoteTipoPersona = new ArrayList<FindCcLoteTipoPersona>();
	private List<FindCcLoteOrdenImpresion> listFindCcLoteOrdenImpresion = new ArrayList<FindCcLoteOrdenImpresion>();
	private String agrupacionBien;
	private String agrupacionCuota;
	private String concepto;
	private Integer Accion=0;// 0=informaci�n,1= programacionLote Preliminar//2= resultado Preliminar
	private Integer estadoProcesoLote=0;// 0=programacion del lote, 1= lote preliminar programado,2=lote preliminar finalizado,3=lote final programado,4=lote  final finalizado   
	private java.util.Date fechaProgramacion;
	private CcLoteSchedule ccLoteSchedule;
	
	private List<FindCcLoteDetalleActo> lstlotePreliminar;
	private List<FindCcLoteDetalleActo> lstloteFinal;
	
	private List<FindCcLoteDetalleActoDeuda> lstDetallelotePreliminar;
	private String[] lstPeriodoCuotaSeleccionados;
	private String[] lstFirmantesSeleccionados;
	private String[] lstLoteCuotaSeleccionados;
	private String[] lstGnSubConceptoSeleccionados;
	private String[]  lstGnSectorSeleccionados ;
	private String[]  lstMpTipoPersonaSeleccionados ;
	private String archivos[];
	private String archivoItem;
	
	@PostConstruct
	public void init() {
		try{
			findCcLoteItem= new FindCcLote();
			Object obj = getSessionMap().get("findCcLoteItem");
			if (obj != null) {
				findCcLoteItem = (FindCcLote)obj;
				getSessionMap().remove("findCcLoteItem");
				Object pk=findCcLoteItem.getLoteId();
				setCcLote(controlycobranzaBo.findCcLote(pk));
				lstCcFirmantes=controlycobranzaBo.getAllCcFirmantes(findCcLoteItem.getLoteId());
				lstCcLoteConcepto=controlycobranzaBo.getAllFindCcLoteConcepto(findCcLoteItem.getLoteId(), findCcLoteItem.getAnnoLote());
				if(lstCcLoteConcepto!=null || lstCcLoteConcepto.size()>0)
					concepto=lstCcLoteConcepto.get(0).getConcepto();
		     	if(findCcLoteItem.getAgrupacionBien().compareTo(Constante.TIPO_AGRUPACION_AGRUPADO_VALOR)==0)
		        	setAgrupacionBien(Constante.TIPO_AGRUPACION_AGRUPADO);
		        if(findCcLoteItem.getAgrupacionBien().compareTo(Constante.TIPO_AGRUPACION_INDIVIDUAL_VALOR)==0)
		        	setAgrupacionBien(Constante.TIPO_AGRUPACION_INDIVIDUAL);
		        if(findCcLoteItem.getAgrupacionCuota().compareTo(Constante.TIPO_AGRUPACION_AGRUPADO_VALOR)==0)
		        	setAgrupacionCuota(Constante.TIPO_AGRUPACION_AGRUPADO);
		        if(findCcLoteItem.getAgrupacionCuota().compareTo(Constante.TIPO_AGRUPACION_INDIVIDUAL_VALOR)==0)
		        	setAgrupacionCuota(Constante.TIPO_AGRUPACION_INDIVIDUAL);
		        tipoActo=findCcLoteItem.getTipoActo();
			}
			
			lstCcLoteCuota=controlycobranzaBo.getAllFindCcLoteCuota(findCcLoteItem.getLoteId());
			lstFindCcLoteSector=controlycobranzaBo.getAllFindCcLoteSector(findCcLoteItem.getLoteId());
			lstFindCcLoteTipoPersona=controlycobranzaBo.getAllFindCcLoteTipoPersona(findCcLoteItem.getLoteId());
			listFindCcLoteOrdenImpresion=controlycobranzaBo.getAllCcLoteOrdenImpresion(findCcLoteItem.getLoteId());
			setLstlotePreliminar(new ArrayList<FindCcLoteDetalleActo>());
			lstloteFinal= new ArrayList<FindCcLoteDetalleActo>();
			lstDetallelotePreliminar = new ArrayList<FindCcLoteDetalleActoDeuda>();
			estadoProcesoLote();
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	public void generarLoteFinal(){
		//Corregido
		try{
			Accion=4;
			//controlycobranzaBo.registrarActo(findCcLoteItem.getLoteId(), 1);
			//lstloteFinal=controlycobranzaBo.getAllFindCcLoteFinal(findCcLoteItem.getLoteId(),findCcLoteItem.getTipoActoId());
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	public void motrarLotePreliminar(){
		//Corregido
		try{
			Accion=4;
			//controlycobranzaBo.registrarActo(findCcLoteItem.getLoteId(), 1);
			//lstloteFinal=controlycobranzaBo.getAllFindCcLoteFinal(findCcLoteItem.getLoteId(),findCcLoteItem.getTipoActoId());
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * eliminar no sirve
	 */
	public void estadoProcesoLote(){
		       //      preliminar=1  final=2 					      programada=1  finalizada=1                        
		try{
			if(findCcLoteItem.getTipo_lote_generacion()==null && findCcLoteItem.getFlag_generacion()==null ){
				estadoProcesoLote=0;
			}
			else{
				if(findCcLoteItem.getTipo_lote_generacion().compareTo(Constante.TIPO_LOTE_GENERACION_PRELIMINAR)==0 && findCcLoteItem.getFlag_generacion().compareTo(Constante.FLAG_GENERACION_PROGRAMADA)==0)
					estadoProcesoLote=1;
				if(findCcLoteItem.getTipo_lote_generacion().compareTo(Constante.TIPO_LOTE_GENERACION_PRELIMINAR)==0 && findCcLoteItem.getFlag_generacion().compareTo(Constante.FLAG_GENERACION_FINALIZADA)==0)
					estadoProcesoLote=2;
				if(findCcLoteItem.getTipo_lote_generacion().compareTo(Constante.TIPO_LOTE_GENERACION_FINAL)==0 && findCcLoteItem.getFlag_generacion().compareTo(Constante.FLAG_GENERACION_PROGRAMADA)==0)
					estadoProcesoLote=3;
				if(findCcLoteItem.getTipo_lote_generacion().compareTo(Constante.TIPO_LOTE_GENERACION_FINAL)==0&& findCcLoteItem.getFlag_generacion().compareTo(Constante.FLAG_GENERACION_FINALIZADA)==0)
					estadoProcesoLote=4;
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	public void programacionInformacionLote(){
		Accion=0;
	}

	/**
	 *  1,2,21 PROGRAMACION PRELIMINAR	
	 */
	public void programacionLotePreliminar(){
		Accion=1;
	}
	
	public void resultadoLotePreliminar(){
		try{
			Accion=2;
			setLstlotePreliminar(controlycobranzaBo.getAllFindCcLotePreliminar(findCcLoteItem.getLoteId(),findCcLoteItem.getTipoActoId()));
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	public void programacionLotePreliminarDetalle(){
		try{
		Accion=21;
		lstDetallelotePreliminar=controlycobranzaBo.getAllFindCcLoteDetalleActoDeuda(findCcLoteItem.getLoteId(), actoId,Constante.TIPO_LOTE_GENERACION_PRELIMINAR);
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	/**
	 *  3,4,41 PROGRAMACION FINAL	
	 */
	public void programacionLoteFinal(){
		Accion=3;
	
	}
	
	public void programacionLoteFinalDetalle(){
		try{
			Accion=41;
			lstDetallelotePreliminar=controlycobranzaBo.getAllFindCcLoteDetalleActoDeuda(findCcLoteItem.getLoteId(), actoId,Constante.TIPO_LOTE_GENERACION_FINAL);
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	
		
	/**
	 *  5 GENERACION DE ARCHIVO	
	 */

	public void generacionArchivo(){
		try{
			Accion=5;
			String ruta=Util.getRutaReportesGenerales();
					//controlycobranzaBo.rutaimpresion(findCcLoteItem.getTipoActoId());
			File folder = new File(ruta+findCcLoteItem.getLoteId()+"-"+findCcLoteItem.getAnnoLote()+"");
       		archivos=folder.list();
       		
       			/*if(archivos!=null){
		       		for(int i=0; i<archivos.length;i++)
					   System.out.println(archivos[i]);
       		}*/
			}catch(Exception e){
					e.printStackTrace();
					WebMessages.messageFatal(e);			
			}
	}
	
	public void guardarProgramacionFinal(){
		try{//---
			ccLoteSchedule=new CcLoteSchedule();
			CcLoteSchedulePK id=new CcLoteSchedulePK();
			controlycobranzaBo.darBajaSchedule(Constante.ESTADO_INACTIVO, findCcLoteItem.getLoteId());
			Integer loteScheduleId=generalBo.ObtenerCorrelativoTabla("cc_lote_schedule", 1);
			id.setScheduleId(loteScheduleId);
			id.setLoteId(findCcLoteItem.getLoteId());
			ccLoteSchedule.setId(id);
			ccLoteSchedule.setFechaProgramacion(fechaProgramacion);
			ccLoteSchedule.setFechaSchedule(DateUtil.dateToSqlTimestamp(fechaProgramacion));
			ccLoteSchedule.setHoraSchedule(DateUtil.dateToSqlTime(fechaProgramacion).toString());
			ccLoteSchedule.setTipoSchedule(Constante.TIPO_PROGRAMACION_FINAL_VALOR);
			ccLoteSchedule.setEstado(Constante.ESTADO_ACTIVO);
			ccLoteSchedule=controlycobranzaBo.create(ccLoteSchedule);
			controlycobranzaBo.actualizarEstadoLote(findCcLoteItem.getLoteId(),Constante.TIPO_LOTE_GENERACION_FINAL,Constante.FLAG_GENERACION_PROGRAMADA);
			estadoProcesoLote=3;
			
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	public void imprimirLote(){
		try{
			controlycobranzaBo.imprimirLote(findCcLoteItem.getLoteId(), findCcLoteItem.getAnnoLote());
			estadoProcesoLote=4;
			if(findCcLoteItem.getTipoActoId()==Constante.TIPO_ACTO_RESOLUCION_SANCION_ID){
				 List<PaDocuAnexo> list=controlycobranzaBo.findCcDocuAnexoLote(findCcLoteItem.getLoteId());	
	    	     for(PaDocuAnexo a:list){
	    	    	 a.setRuta(RepositoryManager.buscarContenido(a.getContentId().toString()).getPathFTP());
	    	    	 controlycobranzaBo.editPaDocuAnexo(a); 
	    	     }
	    	     
			}
    	 
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	public void descargar(){
			// Prepare.
	        FacesContext facesContext = FacesContext.getCurrentInstance();
	        ExternalContext externalContext = facesContext.getExternalContext();
	        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

	        BufferedInputStream input = null;
	        BufferedOutputStream output = null;
			try{
				int DEFAULT_BUFFER_SIZE = 10240; // 10KB.
		        
				String nombreArchivo=Util.getRutaReportesGenerales()+findCcLoteItem.getLoteId()+"-"+findCcLoteItem.getAnnoLote()+"\\"+archivoItem;
		        File pdf=new File(nombreArchivo);
		        
	            
		        //Init servlet response.
	            response.reset();
	            response.setHeader("Content-Type", "application/pdf");
	            response.setHeader("Content-Length", String.valueOf(pdf.length()));
	            response.setHeader("Content-Disposition", "attachment; filename=\"" + archivoItem + "\"");
	            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);
	
	            // Open file.
	            input = new BufferedInputStream(new FileInputStream(pdf), DEFAULT_BUFFER_SIZE);
	            // Write file contents to response.
	            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
	            int length;
	            while ((length = input.read(buffer)) > 0) {
	                output.write(buffer, 0, length);
	            }
	
	            // Finalize task.
	            output.flush();
				
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
	
	public FindCcLote getFindCcLoteItem() {
		return findCcLoteItem;
	}

	public void setFindCcLoteItem(FindCcLote findCcLoteItem) {
		this.findCcLoteItem = findCcLoteItem;
	}

	public CcLoteActo getCcLoteActo() {
		return ccLoteActo;
	}

	public void setCcLoteActo(CcLoteActo ccLoteActo) {
		this.ccLoteActo = ccLoteActo;
	}

	public CcLote getCcLote() {
		return ccLote;
	}

	public void setCcLote(CcLote ccLote) {
		this.ccLote = ccLote;
	}

	public List<FindCcLoteConcepto> getLstCcLoteConcepto() {
		return lstCcLoteConcepto;
	}

	public void setLstCcLoteConcepto(List<FindCcLoteConcepto> lstCcLoteConcepto) {
		this.lstCcLoteConcepto = lstCcLoteConcepto;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public List<CcLoteCuota> getLstCcLoteCuota() {
		return lstCcLoteCuota;
	}

	public void setLstCcLoteCuota(List<CcLoteCuota> lstCcLoteCuota) {
		this.lstCcLoteCuota = lstCcLoteCuota;
	}

	public List<FindCcLoteSector> getLstFindCcLoteSector() {
		return lstFindCcLoteSector;
	}

	public void setLstFindCcLoteSector(List<FindCcLoteSector> lstFindCcLoteSector) {
		this.lstFindCcLoteSector = lstFindCcLoteSector;
	}

	public List<FindCcLoteTipoPersona> getLstFindCcLoteTipoPersona() {
		return lstFindCcLoteTipoPersona;
	}

	public void setLstFindCcLoteTipoPersona(List<FindCcLoteTipoPersona> lstFindCcLoteTipoPersona) {
		this.lstFindCcLoteTipoPersona = lstFindCcLoteTipoPersona;
	}

	public String getAgrupacionBien() {
		return agrupacionBien;
	}

	public void setAgrupacionBien(String agrupacionBien) {
		this.agrupacionBien = agrupacionBien;
	}

	public String getAgrupacionCuota() {
		return agrupacionCuota;
	}

	public void setAgrupacionCuota(String agrupacionCuota) {
		this.agrupacionCuota = agrupacionCuota;
	}

	public List<FindCcLoteOrdenImpresion> getListFindCcLoteOrdenImpresion() {
		return listFindCcLoteOrdenImpresion;
	}

	public void setListFindCcLoteOrdenImpresion(
			List<FindCcLoteOrdenImpresion> listFindCcLoteOrdenImpresion) {
		this.listFindCcLoteOrdenImpresion = listFindCcLoteOrdenImpresion;
	}

	public Integer getAccion() {
		return Accion;
	}

	public void setAccion(Integer accion) {
		Accion = accion;
	}
	public java.util.Date getFechaProgramacion() {
		return fechaProgramacion;
	}
	public void setFechaProgramacion(java.util.Date fechaProgramacion) {
		this.fechaProgramacion = fechaProgramacion;
	}
	public CcLoteSchedule getCcLoteSchedule() {
		return ccLoteSchedule;
	}
	public void setCcLoteSchedule(CcLoteSchedule ccLoteSchedule) {
		this.ccLoteSchedule = ccLoteSchedule;
	}
	public List<FindCcLoteDetalleActo> getLstlotePreliminar() {
		return lstlotePreliminar;
	}
	public void setLstlotePreliminar(List<FindCcLoteDetalleActo> lstlotePreliminar) {
		this.lstlotePreliminar = lstlotePreliminar;
	}
	public List<FindCcLoteDetalleActo> getLstloteFinal() {
		return lstloteFinal;
	}
	public void setLstloteFinal(List<FindCcLoteDetalleActo> lstloteFinal) {
		this.lstloteFinal = lstloteFinal;
	}
	public List<FindCcLoteDetalleActoDeuda> getLstDetallelotePreliminar() {
		return lstDetallelotePreliminar;
	}
	public void setLstDetallelotePreliminar(List<FindCcLoteDetalleActoDeuda> lstDetallelotePreliminar) {
		this.lstDetallelotePreliminar = lstDetallelotePreliminar;
	}
	public Integer getActoId() {
		return actoId;
	}
	public void setActoId(Integer actoId) {
		this.actoId = actoId;
	}
	public String getNroActo() {
		return nroActo;
	}
	public void setNroActo(String nroActo) {
		this.nroActo = nroActo;
	}
	public Integer getPersonaId() {
		return personaId;
	}
	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	public String getTipoActo() {
		return tipoActo;
	}
	public void setTipoActo(String tipoActo) {
		this.tipoActo = tipoActo;
	}
	public String[] getArchivos() {
		return archivos;
	}
	public void setArchivos(String archivos[]) {
		this.archivos = archivos;
	}
	public String getArchivoItem() {
		return archivoItem;
	}
	public void setArchivoItem(String archivoItem) {
		this.archivoItem = archivoItem;
	}
	public Integer getEstadoProcesoLote() {
		return estadoProcesoLote;
	}
	public void setEstadoProcesoLote(Integer estadoProcesoLote) {
		this.estadoProcesoLote = estadoProcesoLote;
	}

	public List<CcFirmante> getLstCcFirmantes() {
		return lstCcFirmantes;
	}

	public void setLstCcFirmantes(List<CcFirmante> lstCcFirmantes) {
		this.lstCcFirmantes = lstCcFirmantes;
	}

	public String[] getLstPeriodoCuotaSeleccionados() {
		return lstPeriodoCuotaSeleccionados;
	}

	public void setLstPeriodoCuotaSeleccionados(
			String[] lstPeriodoCuotaSeleccionados) {
		this.lstPeriodoCuotaSeleccionados = lstPeriodoCuotaSeleccionados;
	}

	public String[] getLstFirmantesSeleccionados() {
		return lstFirmantesSeleccionados;
	}

	public void setLstFirmantesSeleccionados(String[] lstFirmantesSeleccionados) {
		this.lstFirmantesSeleccionados = lstFirmantesSeleccionados;
	}

	public String[] getLstLoteCuotaSeleccionados() {
		return lstLoteCuotaSeleccionados;
	}

	public void setLstLoteCuotaSeleccionados(String[] lstLoteCuotaSeleccionados) {
		this.lstLoteCuotaSeleccionados = lstLoteCuotaSeleccionados;
	}

	public String[] getLstGnSubConceptoSeleccionados() {
		return lstGnSubConceptoSeleccionados;
	}

	public void setLstGnSubConceptoSeleccionados(
			String[] lstGnSubConceptoSeleccionados) {
		this.lstGnSubConceptoSeleccionados = lstGnSubConceptoSeleccionados;
	}

	public String[] getLstGnSectorSeleccionados() {
		return lstGnSectorSeleccionados;
	}

	public void setLstGnSectorSeleccionados(String[] lstGnSectorSeleccionados) {
		this.lstGnSectorSeleccionados = lstGnSectorSeleccionados;
	}

	public String[] getLstMpTipoPersonaSeleccionados() {
		return lstMpTipoPersonaSeleccionados;
	}

	public void setLstMpTipoPersonaSeleccionados(
			String[] lstMpTipoPersonaSeleccionados) {
		this.lstMpTipoPersonaSeleccionados = lstMpTipoPersonaSeleccionados;
	}


}
