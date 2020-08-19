package com.sat.sisat.controlycobranza.managed;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.sat.sisat.common.business.GeneralBoRemote;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.SATWEBParameterFactory;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.controlycobranza.business.ControlyCobranzaBoRemote;
import com.sat.sisat.controlycobranza.dto.FindCcLote;
import com.sat.sisat.controlycobranza.dto.FindCcLoteDetalleActo;
import com.sat.sisat.persistence.CrudServiceBean;
import com.sat.sisat.persistence.entity.CcLote;
import com.sat.sisat.persistence.entity.CcLoteActo;
import com.sat.sisat.persistence.entity.CcLoteActoPK;
import com.sat.sisat.persistence.entity.CcLoteConcepto;
import com.sat.sisat.persistence.entity.CcLoteConceptoPK;
import com.sat.sisat.persistence.entity.CcLoteCuota;
import com.sat.sisat.persistence.entity.CcLoteCuotaPK;
import com.sat.sisat.persistence.entity.CcLoteSector;
import com.sat.sisat.persistence.entity.CcLoteSectorPK;
import com.sat.sisat.persistence.entity.CcLoteTipoPersona;
import com.sat.sisat.persistence.entity.CcLoteTipoPersonaPK;
import com.sat.sisat.persona.business.PersonaBoRemote;
import com.sat.sisat.predial.dto.FindPersona;

@ManagedBean
@ViewScoped
public class RegistroLoteOrdinarioContribManaged  extends BaseManaged {
	@EJB
	PersonaBoRemote personaBo;
	
	@EJB
	GeneralBoRemote generalBo;
	
	@EJB
	ControlyCobranzaBoRemote controlycobranzaBo;
	
	private Boolean isAccionRealizada=Boolean.FALSE;
	
	private Integer personaId;
	private Integer periodo;
	private FindCcLote findCcLoteItem= new FindCcLote();
	
	private CcLote ccLote=new CcLote();
	private CcLoteActo ccLoteActo = new CcLoteActo();
	private FindCcLote findCcLote= new FindCcLote();
	private Integer lote_id;
	
	private FindPersona findPersona;
	
	private List<FindCcLoteDetalleActo> lstlotePreliminar;
	private Integer loteInicio;
	private Integer loteFin;
	private BigDecimal montoMinimo;
	private Boolean selected=true;
	private Boolean selected1=true;
	private Boolean selected2=true;
	private Date fechaInicio = Calendar.getInstance().getTime();
	private Date fechaFin = Calendar.getInstance().getTime();
	@PostConstruct
	public void init() {
		try{
	        FindCcLote findCcLoteItem= (FindCcLote)getSessionMap().get("findCcLoteItem");
	        if(findCcLoteItem!=null){
	        	setFindCcLoteItem(findCcLoteItem);
	        	setLote_id(findCcLoteItem.getLoteId());
	        	mostrarValores();
	        }
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	public void guardar(){
		 try{ 
			// Inicio Busca Sector Persona
		 	FindPersona persona=controlycobranzaBo.getSectorIdByPersonaId(personaId);
		 	// Fin Busca Sector Persona
		 	
			if(persona!=null){
				setFindPersona(persona);
				// Inicio Registra Lote 'cc_lote'
					
					// Inicio Corrolativo Lote
					Integer lote_id=generalBo.ObtenerCorrelativoTabla("cc_lote", 1);
					// Fin Corrolativo Lote
					
				    ccLote.setLoteId(lote_id);
				    ccLote.setTipoLoteId(Constante.TIPO_LOTE_COBRANZA_ID);
				    ccLote.setAnnoLote(periodo);
				    
				    ccLote.setFechaLote(DateUtil.getCurrentDate());
				    ccLote.setEstado(Constante.ESTADO_ACTIVO);
				    ccLote.setFlagGeneracion(Constante.FLAG_GENERACION_PROGRAMADA);
				    ccLote.setTipoLoteGeneracion(Constante.TIPO_LOTE_GENERACION_PRELIMINAR);
				    ccLote= controlycobranzaBo.create(ccLote);	
			    // Fin Registra Lote 
				    
				// Inicio Registra Lote_Acto 'cc_lote_acto'    
				Integer lote_acto_id=generalBo.ObtenerCorrelativoTabla("cc_lote_acto", 1);
				CcLoteActoPK id= new CcLoteActoPK();
				id.setLoteActoId(lote_acto_id);
				id.setLoteId(ccLote.getLoteId());
				ccLoteActo.setId(id);
				ccLoteActo.setTipoActoId(Constante.TIPO_ACTO_RESOLUCION_DETERMINACION_ID);
				ccLoteActo.setEstado(Constante.ESTADO_ACTIVO);
				ccLoteActo = controlycobranzaBo.create(ccLoteActo);
				// Fin Registra Lote_Acto 
				
				Integer lote_concepto_id=generalBo.ObtenerCorrelativoTabla("cc_lote_concepto", 1);
				CcLoteConcepto ccLoteConcepto= new CcLoteConcepto();
				ccLoteConcepto.setConceptoId(Constante.CONCEPTO_PREDIAL);
				CcLoteConceptoPK id1 = new CcLoteConceptoPK();
				id1.setLoteActoId(ccLoteActo.getId().getLoteActoId());
				id1.setLoteId(ccLote.getLoteId());
				id1.setLoteConceptoId(lote_concepto_id);
				ccLoteConcepto.setId(id1);
				ccLoteConcepto.setSubconceptoId(Constante.SUB_CONCEPTO_PREDIAL);
				ccLoteConcepto.setEstado(Constante.ESTADO_ACTIVO);
				controlycobranzaBo.create(ccLoteConcepto);
				
				Integer lote_cuota_id=generalBo.ObtenerCorrelativoTabla("cc_lote_cuota", 1);
			    CcLoteCuota ccLoteCuota= new CcLoteCuota();
			    CcLoteCuotaPK id2 = new CcLoteCuotaPK();
			    id2.setLoteActoId(ccLoteActo.getId().getLoteActoId());
			    id2.setLoteId(ccLote.getLoteId());
			    id2.setLoteCuotaId(lote_cuota_id);
			    ccLoteCuota.setId(id2);
			    ccLoteCuota.setAnnoCuota(periodo);
			    ccLoteCuota.setCuota(0);
			    ccLoteCuota.setEstado(Constante.ESTADO_ACTIVO);
			    controlycobranzaBo.create(ccLoteCuota);
				
				Integer lote_sector_id=generalBo.ObtenerCorrelativoTabla("cc_lote_sector", 1);
				CcLoteSector ccLoteSector= new CcLoteSector();
				CcLoteSectorPK id3 = new CcLoteSectorPK();
				id3.setSectorId(persona.getSectorId());
				id3.setLoteId(ccLote.getLoteId());
				id3.setLoteSectorId(lote_sector_id);
				ccLoteSector.setId(id3);
				ccLoteSector.setEstado(Constante.ESTADO_ACTIVO);
				controlycobranzaBo.create(ccLoteSector);
				
				Integer lote_tipo_persona_id=generalBo.ObtenerCorrelativoTabla("cc_lote_tipo_persona", 1);
				CcLoteTipoPersona ccLoteTipoPersona= new CcLoteTipoPersona();
				CcLoteTipoPersonaPK id4=new CcLoteTipoPersonaPK();
				id4.setLoteId(ccLote.getLoteId());
				id4.setLoteTipoPersonaId(lote_tipo_persona_id);
				id4.setTipoPersonaId(persona.getTipoPersonaId());
				ccLoteTipoPersona.setId(id4);
				ccLoteTipoPersona.setEstado(Constante.ESTADO_ACTIVO);
				controlycobranzaBo.create(ccLoteTipoPersona);
		
				findCcLoteItem=controlycobranzaBo.getFindCcLote(lote_id);
				getSessionMap().put("findCcLoteItem", findCcLoteItem);	
			}
		 }catch(Exception e){
				e.printStackTrace();
				WebMessages.messageFatal(e);			
		 }
	}
	
	public void checkMontoMinimo(ValueChangeEvent event) {
		Boolean check = (Boolean) event.getNewValue();
		if (check == Boolean.TRUE) {
			setSelected(true);
		} else {
			setSelected(false);
		}
		limpiar();
	}
	
	public void checkFechasEmision(ValueChangeEvent event) {
		Boolean check = (Boolean) event.getNewValue();
		if (check == Boolean.TRUE) {
			setSelected1(true);
		} else {
			setSelected1(false);
		}
		limpiar();
	}
	
	public void checkLotes(ValueChangeEvent event) {
		Boolean check = (Boolean) event.getNewValue();
		if (check == Boolean.TRUE) {
			setSelected2(true);
		} else {
			setSelected2(false);
		}
		limpiar();
	}
	
	public void limpiar() {
		if (selected == true && selected1 == true && selected2 == true) {
			setMontoMinimo(null);
			setLoteInicio(null);
			setLoteFin(null);
			setFechaInicio(null);
			setFechaFin(null);
		}
		if (selected == true && selected1 == false && selected2 == false) {
			setMontoMinimo(null);
		}
		if (selected == true && selected1 == false && selected2 == true) {
			setMontoMinimo(null);
			setLoteInicio(null);
			setLoteFin(null);
		}
		if (selected == false && selected1 == true && selected2 == true) {
			setLoteInicio(null);
			setLoteFin(null);
			setFechaInicio(null);
			setFechaFin(null);
		}
		if (selected == false && selected1 == false && selected2 == true) {
			setLoteInicio(null);
			setLoteFin(null);
		}
		if (selected == false && selected1 == true && selected2 == false) {
			setFechaInicio(null);
			setFechaFin(null);
		}
		if (selected == true && selected1 == true && selected2 == false) {
			setMontoMinimo(null);
			setFechaInicio(null);
			setFechaFin(null);
		}
	}
	
	public String salir() {
		getSessionMap().remove("findCcLoteItem");
		return sendRedirectPrincipal();
	}
	
	public void motrarLotePreliminar(){
		try{
			if(findPersona!=null){
				lstlotePreliminar=controlycobranzaBo.getAllFindCcLotePreliminarRD(findPersona.getPersonaId(),periodo);
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	public void generacionValores(){
		try{
			if(findCcLoteItem!=null){
				controlycobranzaBo.registrarActoRD(findCcLoteItem.getLoteId(),findPersona.getPersonaId(),periodo);
				ccLote=controlycobranzaBo.findCcLote(getFindCcLoteItem().getLoteId());
		 		lote_id=ccLote.getLoteId();
				lstlotePreliminar=controlycobranzaBo.getAllFindCcLoteFinalRD(findCcLoteItem.getLoteId());
				findCcLoteItem=controlycobranzaBo.getFindCcLote(findCcLoteItem.getLoteId());
				System.out.println("resultado preliminar "+lstlotePreliminar.size());
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public void mostrarValores(){
		try{
			if(findCcLoteItem!=null){
				lstlotePreliminar=controlycobranzaBo.getAllFindCcLoteFinalRD(findCcLoteItem.getLoteId());
				if(lstlotePreliminar!=null&&lstlotePreliminar.size()>0){
					findCcLoteItem.setFlag_generacion("2");
					setPersonaId(lstlotePreliminar.get(0).getPersonaId());
					setPeriodo(lstlotePreliminar.get(0).getAnnoLote());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public void generacionArchivos(){
		try{
			if(findCcLoteItem!=null){
				java.sql.Connection connection = null;
				try {
					CrudServiceBean connj = CrudServiceBean.getInstance();
					connection = connj.connectJasper();

					String path_context = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
					String path_report = path_context + "/sisat/reportes/";
					String path_imagen = path_context + "/sisat/reportes/imagen/";

					HashMap<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("p_lote_id", findCcLoteItem.getLoteId());
					parameters.put("ruta_imagen", path_imagen);
					parameters.put("p_periodo", findCcLoteItem.getAnnoLote());
					parameters.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
					
					JasperPrint jasperPrint = JasperFillManager.fillReport((SATWEBParameterFactory.getPathReporte() + "resolucion_determinacion_predial.jasper"),parameters, connection);

					ByteArrayOutputStream output = new ByteArrayOutputStream();
					JasperExportManager.exportReportToPdfStream(jasperPrint, output);
					
					HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
					response.setContentType("application/pdf");
					response.addHeader("Content-Disposition","attachment;filename=resolucion_determinacion_predial.pdf");
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
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
  }
	
	public void previewRds() {
		validarRangos();
	}
	
	public void validarRangos(){
		// try{
		if (periodo!=null && periodo!=0 &&(selected==false||selected1==false ||selected2==false)) {
				
			} else {
				addErrorMessage(getMsg("Debe seleccionar algun criterio ademas de ingresar el Periodo."));
			}
	}
	
	public void impresionMasivaRDs() {
		try {
//			limpiar();
			if (periodo!=null && periodo!=0 &&(selected==false||selected1==false ||selected2==false)) {
				java.sql.Connection connection = null;
				// java.sql.Connection connec = null;
				try {
					CrudServiceBean connj = CrudServiceBean.getInstance();
					connection = connj.connectJasper();
					String path_context = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
					String path_imagen = path_context + "/sisat/reportes/imagen/";
					HashMap<String, Object> parameters = new HashMap<String, Object>();	
					String nombreReporte=null;
						if (montoMinimo != null && loteInicio != null && loteFin != null && fechaInicio!=null && fechaFin!=null) {
							parameters.put("p_lote_inicio", loteInicio);
							parameters.put("p_lote_fin", loteFin);
							parameters.put("p_monto_minimo",montoMinimo);
							parameters.put("p_fecha_inicio", fechaInicio);
							parameters.put("p_fecha_fin", fechaFin);
							nombreReporte="resolucion_determinacion_predial_masiva.jasper";
						}						
						if (montoMinimo != null && loteInicio != null && loteFin != null && fechaInicio==null && fechaFin==null) {
							parameters.put("p_monto_minimo",montoMinimo);
							parameters.put("p_lote_inicio", loteInicio);
							parameters.put("p_lote_fin", loteFin);
							nombreReporte="rd_predial_masiva_monto_lote.jasper";
						}
						if (montoMinimo != null && loteInicio == null && loteFin == null && fechaInicio!=null && fechaFin!=null) {							
							parameters.put("p_monto_minimo",montoMinimo);
							parameters.put("p_fecha_inicio", fechaInicio);
							parameters.put("p_fecha_fin", fechaFin);
							nombreReporte="rd_predial_masiva_monto_fecha.jasper";
						}
						if (montoMinimo == null && loteInicio != null && loteFin != null && fechaInicio!=null && fechaFin!=null) {
							parameters.put("p_lote_inicio", loteInicio);
							parameters.put("p_lote_fin", loteFin);
							parameters.put("p_fecha_inicio", fechaInicio);
							parameters.put("p_fecha_fin", fechaFin);
							nombreReporte="rd_predial_masiva_lote_fecha.jasper";
						}
						if (montoMinimo != null && loteInicio == null && loteFin == null && fechaInicio==null && fechaFin==null) {
							parameters.put("p_monto_minimo",montoMinimo);
							nombreReporte="rd_predial_masiva_monto_minimo.jasper";
						}
						if (montoMinimo == null && loteInicio == null && loteFin == null && fechaInicio!=null && fechaFin!=null) {
							parameters.put("p_fecha_inicio", fechaInicio);
							parameters.put("p_fecha_fin", fechaFin);
							nombreReporte="rd_masiva_fecha_emision.jasper";
						}
						if (montoMinimo == null && loteInicio != null && loteFin != null && fechaInicio==null && fechaFin==null) {
							parameters.put("p_lote_inicio", loteInicio);
							parameters.put("p_lote_fin", loteFin);						
							nombreReporte="rd_predial_masiva_lotes.jasper";
						}
					parameters.put("p_periodo", periodo);
					parameters.put("ruta_imagen", path_imagen);
					parameters.put("REPORT_LOCALE", new Locale("en", "ENGLISH"));
					JasperPrint jasperPrint = JasperFillManager.fillReport((SATWEBParameterFactory.getPathReporte() + nombreReporte),parameters, connection);
					ByteArrayOutputStream output = new ByteArrayOutputStream();
					JasperExportManager.exportReportToPdfStream(jasperPrint,
							output);
					HttpServletResponse response = (HttpServletResponse) FacesContext
							.getCurrentInstance().getExternalContext()
							.getResponse();
					response.setContentType("application/pdf");
					response.addHeader("Content-Disposition",
							"attachment;filename=resolucion_determinacion_predial_masiva.pdf");
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
					}
				}
			}
			else
			{
				addErrorMessage("Debe seleccionar algun criterio ademas de ingresar el Periodo");
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
  public String sendRedirectPrincipal() {
	 return "/sisat/principal.xhtml?faces-redirect=true";
  }
  
  public void regresar(){
	  
  }

    public void sendRedirectPrincipalListener() {
		try {
			getExternalContext().redirect("principal.jsf");
		} catch (IOException ex) {
			// TODO : Controller exception
			System.out.println("Pagina no encontrada");
		}
	}
	
	public Boolean getIsAccionRealizada() {
		return isAccionRealizada;
	}


	public void setIsAccionRealizada(Boolean isAccionRealizada) {
		this.isAccionRealizada = isAccionRealizada;
	}


	public Integer getPeriodo() {
		return periodo;
	}


	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}

	public Integer getPersonaId() {
		return personaId;
	}

	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}

	public FindCcLote getFindCcLoteItem() {
		return findCcLoteItem;
	}

	public void setFindCcLoteItem(FindCcLote findCcLoteItem) {
		this.findCcLoteItem = findCcLoteItem;
	}
	
	public List<FindCcLoteDetalleActo> getLstlotePreliminar() {
		return lstlotePreliminar;
	}

	public void setLstlotePreliminar(List<FindCcLoteDetalleActo> lstlotePreliminar) {
		this.lstlotePreliminar = lstlotePreliminar;
	}
	
	public CcLote getCcLote() {
		return ccLote;
	}

	public void setCcLote(CcLote ccLote) {
		this.ccLote = ccLote;
	}

	public CcLoteActo getCcLoteActo() {
		return ccLoteActo;
	}

	public void setCcLoteActo(CcLoteActo ccLoteActo) {
		this.ccLoteActo = ccLoteActo;
	}

	public FindCcLote getFindCcLote() {
		return findCcLote;
	}

	public void setFindCcLote(FindCcLote findCcLote) {
		this.findCcLote = findCcLote;
	}

	public Integer getLote_id() {
		return lote_id;
	}

	public void setLote_id(Integer lote_id) {
		this.lote_id = lote_id;
	}
	
	public FindPersona getFindPersona() {
		return findPersona;
	}

	public void setFindPersona(FindPersona findPersona) {
		this.findPersona = findPersona;
	}

	public Integer getLoteInicio() {
		return loteInicio;
	}

	public void setLoteInicio(Integer loteInicio) {
		this.loteInicio = loteInicio;
	}

	public Integer getLoteFin() {
		return loteFin;
	}

	public void setLoteFin(Integer loteFin) {
		this.loteFin = loteFin;
	}

	public BigDecimal getMontoMinimo() {
		return montoMinimo;
	}

	public void setMontoMinimo(BigDecimal montoMinimo) {
		this.montoMinimo = montoMinimo;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Boolean getSelected1() {
		return selected1;
	}

	public void setSelected1(Boolean selected1) {
		this.selected1 = selected1;
	}

	public Boolean getSelected2() {
		return selected2;
	}

	public void setSelected2(Boolean selected2) {
		this.selected2 = selected2;
	}
}
