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

import com.sat.sisat.common.business.GeneralBoRemote;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.Util;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.controlycobranza.business.ControlyCobranzaBoRemote;
import com.sat.sisat.controlycobranza.dto.FindCcActo;
import com.sat.sisat.controlycobranza.dto.FindCcLote;
import com.sat.sisat.controlycobranza.dto.FindCcLoteConcepto;
import com.sat.sisat.controlycobranza.dto.FindCcLoteDetalleActo;
import com.sat.sisat.controlycobranza.dto.FindCcLoteDetalleActoDeuda;
import com.sat.sisat.controlycobranza.dto.FindCcLoteOrdenImpresion;
import com.sat.sisat.controlycobranza.dto.FindCcLoteTipoPersona;
import com.sat.sisat.controlycobranza.dto.FindCcRec;
import com.sat.sisat.persistence.entity.CcFirmante;
import com.sat.sisat.persistence.entity.CcLote;
import com.sat.sisat.persistence.entity.CcLoteActo;
import com.sat.sisat.persistence.entity.CcLoteCuota;
import com.sat.sisat.persistence.entity.CcLoteSchedule;
import com.sat.sisat.persistence.entity.CcLoteSchedulePK;
import com.sat.sisat.persona.business.PersonaBoRemote;

@ManagedBean
@ViewScoped
public class ProgramacionLoteCoactivoManaged extends BaseManaged {
	@EJB
	PersonaBoRemote personaBo;
	
	@EJB
	GeneralBoRemote generalBo;
	
	@EJB
	ControlyCobranzaBoRemote controlycobranzaBo;
	
	private FindCcLote findCcLoteItem= new FindCcLote();
	private CcLoteActo ccLoteActo = new CcLoteActo();
	private CcLote ccLote = new CcLote();
	private Integer recId;
	private Integer actoId;
	private String nroActo;
	private String nroREC;
	private Integer personaId;
	private String  nombreCompleto;
	private String tipoActo;
	private String tipoActoREC;
	private Integer loteIdActo;
	private List<CcFirmante> lstCcFirmantes = new ArrayList<CcFirmante>();
	private List<FindCcLoteConcepto> lstCcLoteConcepto = new ArrayList<FindCcLoteConcepto>();
	private List<CcLoteCuota> lstCcLoteCuota = new ArrayList<CcLoteCuota>();
	private List<FindCcLoteTipoPersona> lstFindCcLoteTipoPersona = new ArrayList<FindCcLoteTipoPersona>();
	private List<FindCcLoteOrdenImpresion> listFindCcLoteOrdenImpresion = new ArrayList<FindCcLoteOrdenImpresion>();
	private String agrupacionActo;
	private String concepto;
	private Integer Accion=0;// 0=informaci�n,1= programacionLote Preliminar//2= resultado Preliminar
	private Integer estadoProcesoLote=0;// 0=programacion del lote, 1= lote preliminar programado,2=lote preliminar finalizado,3=lote final programado,4=lote  final finalizado   
	private java.util.Date fechaProgramacion;
	private java.util.Date fechaVencimiento;
	private CcLoteSchedule ccLoteSchedule;
	
    private List<FindCcActo> lstloteActoRec;
	private List<FindCcLoteDetalleActo> lstloteFinal;
	
	private List<FindCcRec> lstloteRecPreliminar;
	
	
	private List<FindCcLoteDetalleActoDeuda> lstDetallelotePreliminar;
	
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
		     	if(findCcLoteItem.getAgrupacionActo().compareTo(Constante.TIPO_AGRUPACION_AGRUPADO_VALOR)==0)
		        	setAgrupacionActo(Constante.TIPO_AGRUPACION_AGRUPADO);
		        if(findCcLoteItem.getAgrupacionActo().compareTo(Constante.TIPO_AGRUPACION_INDIVIDUAL_VALOR)==0)
		        	setAgrupacionActo(Constante.TIPO_AGRUPACION_INDIVIDUAL);
		       
		    tipoActo=findCcLoteItem.getTipoActo();
			}
			lstCcLoteCuota=controlycobranzaBo.getAllFindCcLoteCuota(findCcLoteItem.getLoteId());
			lstFindCcLoteTipoPersona=controlycobranzaBo.getAllFindCcLoteTipoPersona(findCcLoteItem.getLoteId());
			listFindCcLoteOrdenImpresion=controlycobranzaBo.getAllCcLoteOrdenImpresion(findCcLoteItem.getLoteId());
			lstloteRecPreliminar=new ArrayList<FindCcRec>();
			setLstloteActoRec(new ArrayList<FindCcActo>());
			lstloteFinal= new ArrayList<FindCcLoteDetalleActo>();
			lstDetallelotePreliminar = new ArrayList<FindCcLoteDetalleActoDeuda>();
			estadoProcesoLote();
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
		
	}
	
	public void estadoProcesoLote(){
		       //      preliminar=1  final=2 					      programada=1  finalizada=1                        
	try{
		if(findCcLoteItem.getTipo_lote_generacion()==null && findCcLoteItem.getFlag_generacion()==null ){
			estadoProcesoLote=0;}
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
	
	public void programacionLotePreliminar(){
		Accion=1;
	}

	public void resultadoLotePreliminar(){
		try{
			Accion=2;
			lstloteRecPreliminar=controlycobranzaBo.getAllFindCcLoteRec(findCcLoteItem.getLoteId(),findCcLoteItem.getTipoActoId(),Constante.TIPO_LOTE_GENERACION_PRELIMINAR);
			if(lstloteRecPreliminar!=null && lstloteRecPreliminar.size()>0)
			tipoActoREC=lstloteRecPreliminar.get(0).getTipoActoRecDescrip();
					
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
		}
	
	public void programacionLotePreliminarActoDetalle(){
		try{
		Accion=201;
		setLstloteActoRec(new ArrayList<FindCcActo>());
		setLstloteActoRec(controlycobranzaBo.getAllCcActoRec(findCcLoteItem.getLoteId(), recId,Constante.TIPO_LOTE_GENERACION_PRELIMINAR));
		
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
		}
	
	public void programacionLotePreliminarDetalle(){
		try{
		Accion=21;
		lstDetallelotePreliminar=controlycobranzaBo.getAllFindCcLoteDetalleActoDeuda(loteIdActo, actoId,Constante.TIPO_LOTE_GENERACION_FINAL);//ya que son valores ya generados
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
		}
	
	public void programacionLoteFinal(){
		Accion=3;
	}
	
	public void resultadoLoteFinal(){
		try{
			Accion=4;
			lstloteRecPreliminar=controlycobranzaBo.getAllFindCcLoteRec(findCcLoteItem.getLoteId(),findCcLoteItem.getTipoActoId(),Constante.TIPO_LOTE_GENERACION_FINAL);
			if(lstloteRecPreliminar!=null && lstloteRecPreliminar.size()>0)
				tipoActoREC=lstloteRecPreliminar.get(0).getTipoActoRecDescrip();
			}catch(Exception e){
					e.printStackTrace();
					WebMessages.messageFatal(e);			
			}
	}
	
	public void programacionLoteFinalActoDetalle(){
		try{
		Accion=401;
		setLstloteActoRec(new ArrayList<FindCcActo>());
		setLstloteActoRec(controlycobranzaBo.getAllCcActoRec(findCcLoteItem.getLoteId(), recId,Constante.TIPO_LOTE_GENERACION_FINAL));
		
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
		}
	
	public void programacionLoteFinalDetalle(){
		try{
		Accion=41;
		lstDetallelotePreliminar=controlycobranzaBo.getAllFindCcLoteDetalleActoDeuda(loteIdActo, actoId,Constante.TIPO_LOTE_GENERACION_FINAL);
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
		}
	
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
		try{
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

	public List<FindCcLoteTipoPersona> getLstFindCcLoteTipoPersona() {
		return lstFindCcLoteTipoPersona;
	}

	public void setLstFindCcLoteTipoPersona(List<FindCcLoteTipoPersona> lstFindCcLoteTipoPersona) {
		this.lstFindCcLoteTipoPersona = lstFindCcLoteTipoPersona;
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

	public String getAgrupacionActo() {
		return agrupacionActo;
	}

	public void setAgrupacionActo(String agrupacionActo) {
		this.agrupacionActo = agrupacionActo;
	}

	public List<CcFirmante> getLstCcFirmantes() {
		return lstCcFirmantes;
	}

	public void setLstCcFirmantes(List<CcFirmante> lstCcFirmantes) {
		this.lstCcFirmantes = lstCcFirmantes;
	}

	public List<FindCcRec> getLstloteRecPreliminar() {
		return lstloteRecPreliminar;
	}

	public void setLstloteRecPreliminar(List<FindCcRec> lstloteRecPreliminar) {
		this.lstloteRecPreliminar = lstloteRecPreliminar;
	}

	public String getTipoActoREC() {
		return tipoActoREC;
	}

	public void setTipoActoREC(String tipoActoREC) {
		this.tipoActoREC = tipoActoREC;
	}

	public Integer getRecId() {
		return recId;
	}

	public void setRecId(Integer recId) {
		this.recId = recId;
	}

	public Integer getLoteIdActo() {
		return loteIdActo;
	}

	public void setLoteIdActo(Integer loteIdActo) {
		this.loteIdActo = loteIdActo;
	}

	public List<FindCcActo> getLstloteActoRec() {
		return lstloteActoRec;
	}

	public void setLstloteActoRec(List<FindCcActo> lstloteActoRec) {
		this.lstloteActoRec = lstloteActoRec;
	}

	public java.util.Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(java.util.Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public String getNroREC() {
		return nroREC;
	}

	public void setNroREC(String nroREC) {
		this.nroREC = nroREC;
	}
}
