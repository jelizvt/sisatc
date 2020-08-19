package com.sat.sisat.controlycobranza.managed;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.richfaces.component.html.HtmlComboBox;

import com.sat.sisat.common.business.GeneralBoRemote;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.SATWEBParameterFactory;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.controlycobranza.business.ControlyCobranzaBoRemote;
import com.sat.sisat.controlycobranza.dto.FindCcLote;
import com.sat.sisat.controlycobranza.dto.FindCcLoteConcepto;
import com.sat.sisat.controlycobranza.dto.FindCcLoteDetalleActo;
import com.sat.sisat.controlycobranza.dto.FindCcLoteSector;
import com.sat.sisat.controlycobranza.dto.FindCcLoteTipoPersona;
import com.sat.sisat.controlycobranza.dto.FindPeriodoCuota;
import com.sat.sisat.persistence.CrudServiceBean;
import com.sat.sisat.persistence.entity.CcFirmante;
import com.sat.sisat.persistence.entity.CcLote;
import com.sat.sisat.persistence.entity.CcLoteActo;
import com.sat.sisat.persistence.entity.CcLoteActoPK;
import com.sat.sisat.persistence.entity.CcLoteConcepto;
import com.sat.sisat.persistence.entity.CcLoteConceptoPK;
import com.sat.sisat.persistence.entity.CcLoteCuota;
import com.sat.sisat.persistence.entity.CcLoteCuotaPK;
import com.sat.sisat.persistence.entity.CcLoteFirma;
import com.sat.sisat.persistence.entity.CcLoteFirmaPK;
import com.sat.sisat.persistence.entity.CcLoteSector;
import com.sat.sisat.persistence.entity.CcLoteSectorPK;
import com.sat.sisat.persistence.entity.CcLoteTipoOrdenImpresion;
import com.sat.sisat.persistence.entity.CcLoteTipoOrdenImpresionPK;
import com.sat.sisat.persistence.entity.CcLoteTipoPersona;
import com.sat.sisat.persistence.entity.CcLoteTipoPersonaPK;
import com.sat.sisat.persistence.entity.CcTipoActo;
import com.sat.sisat.persistence.entity.CcTipoAgrupamiento;
import com.sat.sisat.persistence.entity.CcTipoLote;
import com.sat.sisat.persistence.entity.CcTipoOrdenImpresion;
import com.sat.sisat.persistence.entity.DtFechaVencimiento;
import com.sat.sisat.persistence.entity.GnConcepto;
import com.sat.sisat.persistence.entity.GnSector;
import com.sat.sisat.persistence.entity.GnSubconcepto;
import com.sat.sisat.persistence.entity.MpTipoPersona;
import com.sat.sisat.persistence.entity.SgUsuario;
import com.sat.sisat.persona.business.PersonaBoRemote;

@ManagedBean
@ViewScoped
public class RegistroLoteOrdinarioManaged  extends BaseManaged {
	@EJB
	PersonaBoRemote personaBo;
	
	@EJB
	GeneralBoRemote generalBo;
	
	@EJB
	ControlyCobranzaBoRemote controlycobranzaBo;
	
	private Boolean isAccionRealizada=Boolean.FALSE;
	private Boolean isRecordatorioDeudas=Boolean.FALSE;
	private Boolean isResolucionSancion=Boolean.FALSE;
	
	private Integer tipoLoteId;
	private Integer tipoActoId;
	private Integer conceptoId;
	private Integer periodo;
	private Integer agrupadoPorBien;
	private Integer agrupadoPorCuotas;
	private Integer UsuarioFirmanteId;
	
	private HtmlComboBox cmbtipodlote;
	private List<SelectItem> lsttipolote=new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapCcTipolote=new HashMap<String, Integer>();
	private String cmbValuetipolote;
	
	private HtmlComboBox cmbtipoacto;
	private List<SelectItem> lsttipoacto=new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapCcTipoacto=new HashMap<String, Integer>();
	private String cmbValuetipoacto;
	private List<CcTipoActo> lCcTipoActo = null;
	
	private HtmlComboBox cmbgnConcepto;
	private List<SelectItem> lstgnConcepto=new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapgnConcepto=new HashMap<String, Integer>();
	private String cmbValueConcepto;
	
	private HtmlComboBox cmbCcTipoAgrupamiento;
	private List<SelectItem> lstCcTipoAgrupamiento=new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapCcTipoAgrupamiento=new HashMap<String, Integer>();
	private String cmbValueCcTipoAgrupamientoBien;
	
	private HtmlComboBox cmbCcTipoAgrupamientoCuota;
	private List<SelectItem> lstCcTipoAgrupamientoCuota=new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapCcTipoAgrupamientoCuota=new HashMap<String, Integer>();
	private String cmbValueCcTipoAgrupamientoCuotas;
	
	private List<GnSubconcepto> lstGnSubConcepto = null;
	private String[] lstGnSubConceptoSeleccionados;
	private HashMap<String, Integer> mapSubConceptoSeleccionados=new HashMap<String, Integer>();
	
	private HtmlComboBox cmbGnUsuario;
	private List<SelectItem> lstGnUsuario=new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapGnUsuario=new HashMap<String, Integer>();
	private String cmbValueGnUsuario;
	private List<SgUsuario> lstUsuarios = null;
	
	private List<FindPeriodoCuota> lstPeriodoCuota = null;
	private String[] lstPeriodoCuotaSeleccionados;
	
	private List<DtFechaVencimiento> lstDtFechaVencimiento = null;
	private String[] lstDtFechaVencimientoSeleccionados;
	
	private List<GnSector> lstGnSector = null;

	private String[]  lstGnSectorSeleccionados = null;
	private HashMap<String, Integer> mapGnSectorSeleccionados=new HashMap<String, Integer>();
	
	private List<CcTipoAgrupamiento> lstTipoAgrupamiento=null;
	private List<MpTipoPersona> lstTipoPersona=null;
	private String[]  lstMpTipoPersonaSeleccionados = null;
	private HashMap<String, Integer> mapMpTipoPersonaSeleccionados=new HashMap<String, Integer>();

	private List<CcTipoOrdenImpresion> lstCcTipoOrdenImpresion = null;
	private String[] lstCcTipoOrdenImpresionValue;
	private String[] lstCcTipoOrdenImpresionSeleccionado;
	private HashMap<String, Integer> mapTipoOrdenImpresionValue=new HashMap<String, Integer>();
	
	private List<CcFirmante> lstCcFirmantes = null;
	private String[] lstCcFirmantesValue;
	private String[] lstCcFirmantesSeleccionado;
	private HashMap<String, Integer> mapFirmantesValue=new HashMap<String, Integer>();
	
	private CcLote ccLote=new CcLote();
	private CcLoteActo ccLoteActo = new CcLoteActo();
	private FindCcLote findCcLote= new FindCcLote();
	private FindCcLote findCcLoteItem= new FindCcLote();
	private Integer lote_id;
	
	private List<FindCcLoteDetalleActo> lstlotePreliminar;
	
	@PostConstruct
	public void init() {
		try{
		    lstGnSubConceptoSeleccionados = new String[]{};
		    lstGnSectorSeleccionados = new String[]{};
		    lstMpTipoPersonaSeleccionados = new String[]{};
		    lstCcTipoOrdenImpresionSeleccionado = new String[]{};
			//CcTipoLote
	        List<CcTipoLote> lstCcTipoLote=controlycobranzaBo.getAllCcTipoLote();
			Iterator<CcTipoLote> it = lstCcTipoLote.iterator();  
			lsttipolote=new ArrayList<SelectItem>();
			 
	        while (it.hasNext()){
	        	CcTipoLote obj = it.next();
	        	lsttipolote.add(new SelectItem(obj.getDescripcion(),String.valueOf(obj.getTipoLoteId())));
	        	mapCcTipolote.put(obj.getDescripcion().trim(), obj.getTipoLoteId());
	        }
	        
	        lstPeriodoCuotaSeleccionados=new String[]{};
	        lstPeriodoCuota=new ArrayList<FindPeriodoCuota>();
	        //lstGnSector
	        lstGnSector=controlycobranzaBo.getAllGnSector();
	        Iterator<GnSector> it1 = lstGnSector.iterator();
	        String temp1="";
	        while (it1.hasNext()){
	        	GnSector obj = it1.next();  
	        	mapGnSectorSeleccionados.put(obj.getDescripcion().trim(), obj.getSectorId());
	        	//--temp1=temp1+obj.getDescripcion()+",";
	        	
	        }
	        //--lstGnSectorSeleccionados=temp1.split(",");
	        //lstMpTipoPersona
	        lstTipoPersona=personaBo.getAllMpTipoPersona();
	        Iterator<MpTipoPersona> it2 = lstTipoPersona.iterator();
	        String temp2="";
	        while (it2.hasNext()){
	        	MpTipoPersona obj = it2.next();  
	        	mapMpTipoPersonaSeleccionados.put(obj.getDescripcion().trim(), obj.getTipoPersonaId());
	        	//--temp2=temp2+obj.getDescripcion()+",";
	        }
	        //--lstMpTipoPersonaSeleccionados=temp2.split(",");
	        
	        //lstMpTipoAgrupamiento
	        lstTipoAgrupamiento=controlycobranzaBo.getAllCcTipoAgrupamiento();
	        Iterator<CcTipoAgrupamiento> it3 = lstTipoAgrupamiento.iterator(); 
	        while (it3.hasNext()){
	        	CcTipoAgrupamiento obj = it3.next();  
	        	lstCcTipoAgrupamiento.add(new SelectItem(obj.getDescripcion().trim(),String.valueOf(obj.getTipoAgrupacionId())));
	        	mapCcTipoAgrupamiento.put(obj.getDescripcion().trim(), obj.getTipoAgrupacionId());
	        	lstCcTipoAgrupamientoCuota.add(new SelectItem(obj.getDescripcion().trim(),String.valueOf(obj.getTipoAgrupacionId())));
	        	mapCcTipoAgrupamientoCuota.put(obj.getDescripcion().trim(), obj.getTipoAgrupacionId());
	        }
	        //gnUsuario
	        lstUsuarios=controlycobranzaBo.getAllSgUsuario();
	        Iterator<SgUsuario> it4 = lstUsuarios.iterator(); 
	        while (it4.hasNext()){
	        	SgUsuario obj = it4.next();  
	        	lstGnUsuario.add(new SelectItem(obj.getNombreUsuario().trim(),String.valueOf(obj.getUsuarioId())));
	        	mapGnUsuario.put(obj.getNombreUsuario().trim(), obj.getUsuarioId());
	          }
	        //CcTipoOrdenImpresion
	        lstCcTipoOrdenImpresion=controlycobranzaBo.getAllCcTipoOrdenImpresion();
	        lstCcTipoOrdenImpresionValue= new String[lstCcTipoOrdenImpresion.size()];
	        for(int i =0; i<lstCcTipoOrdenImpresion.size();i++){
	        	lstCcTipoOrdenImpresionValue[i]=lstCcTipoOrdenImpresion.get(i).getDescripcion();
	        	mapTipoOrdenImpresionValue.put(lstCcTipoOrdenImpresion.get(i).getDescripcion().trim(), lstCcTipoOrdenImpresion.get(i).getTipoOrdenImpresionId());
	        }
	        															
	        FindCcLote findCcLoteItem= (FindCcLote)getSessionMap().get("findCcLoteItem");
	        if(findCcLoteItem!=null){
	        	setFindCcLoteItem(findCcLoteItem);
	        	selecccionaOpciones(findCcLoteItem);
	        }
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	
	public void selecccionaOpciones(FindCcLote findCcLoteItem){
		try{
			Integer loteId=findCcLoteItem.getLoteId();
			setLote_id(loteId);
			//--
			setCmbValuetipolote(findCcLoteItem.getTipoLote());
			setTipoLoteId(findCcLoteItem.getTipoLoteId());
			viewcmbtipoacto();
			//--
			setCmbValuetipoacto(findCcLoteItem.getTipoActo());
			setTipoActoId(findCcLoteItem.getTipoActoId());
			viewcmbconcepto();
			//--
			setCmbValueConcepto(findCcLoteItem.getConcepto());
			setConceptoId(findCcLoteItem.getConceptoId());
			viewsubconcepto();
			lstPeriodoCuota=controlycobranzaBo.findDtPeriodoCuota(getConceptoId(), null,getTipoActoId());
			//
			List<FindCcLoteSector> lCcLoteSector=controlycobranzaBo.getAllFindCcLoteSector(loteId);
			Iterator<FindCcLoteSector> it0 = lCcLoteSector.iterator();
	        String temp="";
	        while (it0.hasNext()){
	        	FindCcLoteSector obj = it0.next();  
	        	temp=temp+obj.getSector()+",";
	        }
	        lstGnSectorSeleccionados=temp.split(",");		
			
			//--
	        List<FindCcLoteTipoPersona> lCcLoteTipoPersona=controlycobranzaBo.getAllFindCcLoteTipoPersona(loteId);
	        Iterator<FindCcLoteTipoPersona> it1 = lCcLoteTipoPersona.iterator();
	        temp="";
	        while (it1.hasNext()){
	        	FindCcLoteTipoPersona obj = it1.next();  
	        	temp=temp+obj.getTipoPersona()+",";
	        }
	        lstMpTipoPersonaSeleccionados = temp.split(",");
	        
		    //--
			List<FindCcLoteConcepto> lCcLoteConcepto=controlycobranzaBo.getAllFindCcLoteConcepto(loteId);
			Iterator<FindCcLoteConcepto> it2 = lCcLoteConcepto.iterator();
	        temp="";
	        while (it2.hasNext()){
	        	FindCcLoteConcepto obj = it2.next();  
	        	temp=temp+obj.getSubconcepto()+",";
	        }
	        lstGnSubConceptoSeleccionados = temp.split(",");
	        
		    //--
			List<CcLoteCuota> lCcLoteCuota=controlycobranzaBo.getAllFindCcLoteCuota(loteId);
			Iterator<CcLoteCuota> it3 = lCcLoteCuota.iterator();
	        temp="";
	        while (it3.hasNext()){
	        	CcLoteCuota obj = it3.next();  
	        	temp=temp+obj.getAnnoCuota()+"-"+obj.getCuota()+",";
	        }
		    lstPeriodoCuotaSeleccionados = temp.split(",");
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public String salir() {
		getSessionMap().remove("findCcLoteItem");
		return sendRedirectPrincipal();
	}
	
	public boolean valida(){
		if(tipoLoteId==2){
			if(tipoActoId==4){
				if(conceptoId==1){
					return true;	
				}else{
					WebMessages.messageError("El Concepto seleccionado esta en construccion");
				}
			}else{
				WebMessages.messageError("El Tipo de Acto seleccionado esta en construccion");
			}
		}else{
			WebMessages.messageError("El Tipo de Lote seleccionado esta en construccion");
		}
		return false;
	}
	
	public void guardar(){
		if(valida()){
			 try{ 
				 FindCcLote pfindCcLoteItem= (FindCcLote)getSessionMap().get("findCcLoteItem");
			     if(pfindCcLoteItem!=null){
			        setFindCcLoteItem(pfindCcLoteItem);
			     }else{
			    	setFindCcLoteItem(null);
			     }
			     
				 if(validarIngresoLote()){
					 	if(getFindCcLoteItem()!=null&&getFindCcLoteItem().getLoteId()!=null&&getFindCcLoteItem().getLoteId()>0){
					 		ccLote=controlycobranzaBo.findCcLote(getFindCcLoteItem().getLoteId());
					 		lote_id=ccLote.getLoteId();
					 		controlycobranzaBo.actualizaEstadoLote(lote_id,Constante.ESTADO_INACTIVO);
					 	}else{
					 		lote_id=generalBo.ObtenerCorrelativoTabla("cc_lote", 1);
							
						    ccLote.setLoteId(lote_id);
						    ccLote.setTipoLote(Constante.TIPO_LOTE_ORDINARIO);
						    ccLote.setTipoLoteId(tipoLoteId);
						    ccLote.setAnnoLote(DateUtil.getAnioActual());
						    isAccionRealizada=Boolean.TRUE;
						    ccLote.setFechaLote(DateUtil.getCurrentDate());
						    ccLote.setEstado(Constante.ESTADO_ACTIVO);
						    ccLote.setFlagGeneracion(Constante.FLAG_GENERACION_PROGRAMADA);
						    ccLote.setTipoLoteGeneracion(Constante.TIPO_LOTE_GENERACION_PRELIMINAR);
						    ccLote= controlycobranzaBo.create(ccLote);	
					 	}
					    
					    Integer lote_acto_id=generalBo.ObtenerCorrelativoTabla("cc_lote_acto", 1);
					    CcLoteActoPK id= new CcLoteActoPK();
					    id.setLoteActoId(lote_acto_id);
					    id.setLoteId(ccLote.getLoteId());
					    ccLoteActo.setId(id);
					    ccLoteActo.setTipoActoId(tipoActoId);
					    ccLoteActo.setEstado(Constante.ESTADO_ACTIVO);
					    
				        //if(cmbValueCcTipoAgrupamientoBien.compareTo(Constante.TIPO_AGRUPACION_AGRUPADO)==0)
				        	//ccLoteActo.setAgrupadoBien(Constante.TIPO_AGRUPACION_AGRUPADO_VALOR);
				        //if(cmbValueCcTipoAgrupamientoBien.compareTo(Constante.TIPO_AGRUPACION_INDIVIDUAL)==0)
				        	//ccLoteActo.setAgrupadoBien(Constante.TIPO_AGRUPACION_INDIVIDUAL_VALOR);
				        //if(cmbValueCcTipoAgrupamientoCuotas.compareTo(Constante.TIPO_AGRUPACION_AGRUPADO)==0)
				        	//ccLoteActo.setAgrupadoCuota(Constante.TIPO_AGRUPACION_AGRUPADO_VALOR);
				        //if(cmbValueCcTipoAgrupamientoCuotas.compareTo(Constante.TIPO_AGRUPACION_INDIVIDUAL)==0)
				        	//ccLoteActo.setAgrupadoCuota(Constante.TIPO_AGRUPACION_INDIVIDUAL_VALOR);
					    //agrupado por bien o por cuota
				        ccLoteActo = controlycobranzaBo.create(ccLoteActo);
					    CcLoteConcepto ccLoteConcepto;
					    Integer lote_concepto_id;
					    if(lstGnSubConceptoSeleccionados==null || lstGnSubConceptoSeleccionados.length==0){
					    	String agrupadoCuota="0";
					    	String agrupadoBien="0";
					    	 //if(cmbValueCcTipoAgrupamientoBien.compareTo(Constante.TIPO_AGRUPACION_AGRUPADO)==0)
					    		// agrupadoBien=Constante.TIPO_AGRUPACION_AGRUPADO_VALOR;
					        //if(cmbValueCcTipoAgrupamientoBien.compareTo(Constante.TIPO_AGRUPACION_INDIVIDUAL)==0)
					        	//agrupadoBien=Constante.TIPO_AGRUPACION_INDIVIDUAL_VALOR;
					        //if(cmbValueCcTipoAgrupamientoCuotas.compareTo(Constante.TIPO_AGRUPACION_AGRUPADO)==0)
					        	//agrupadoCuota=Constante.TIPO_AGRUPACION_AGRUPADO_VALOR;
					        //if(cmbValueCcTipoAgrupamientoCuotas.compareTo(Constante.TIPO_AGRUPACION_INDIVIDUAL)==0)
					        	//agrupadoCuota=Constante.TIPO_AGRUPACION_INDIVIDUAL_VALOR;
					    	controlycobranzaBo.registrarLoteConceptoTodos(ccLote.getLoteId(), agrupadoCuota, agrupadoBien);
					    }
					    if(lstGnSubConceptoSeleccionados!=null && lstGnSubConceptoSeleccionados.length>0){
					    	for (String p : lstGnSubConceptoSeleccionados){
				    			lote_concepto_id=generalBo.ObtenerCorrelativoTabla("cc_lote_concepto", 1);
						        ccLoteConcepto= new CcLoteConcepto();
						        ccLoteConcepto.setConceptoId(conceptoId);
						        CcLoteConceptoPK id1 = new CcLoteConceptoPK();
						        id1.setLoteActoId(ccLoteActo.getId().getLoteActoId());
						        id1.setLoteId(ccLote.getLoteId());
						        id1.setLoteConceptoId(lote_concepto_id);
						        ccLoteConcepto.setId(id1);
						        ccLoteConcepto.setSubconceptoId((Integer)mapSubConceptoSeleccionados.get(p));
							        ccLoteConcepto.setEstado(Constante.ESTADO_ACTIVO);
						        //if(cmbValueCcTipoAgrupamientoBien.compareTo(Constante.TIPO_AGRUPACION_AGRUPADO)==0)
						        	//ccLoteConcepto.setAgrupadoBien(Constante.TIPO_AGRUPACION_AGRUPADO_VALOR);
						        //if(cmbValueCcTipoAgrupamientoBien.compareTo(Constante.TIPO_AGRUPACION_INDIVIDUAL)==0)
						        	//ccLoteConcepto.setAgrupadoBien(Constante.TIPO_AGRUPACION_INDIVIDUAL_VALOR);
						        //if(cmbValueCcTipoAgrupamientoCuotas.compareTo(Constante.TIPO_AGRUPACION_AGRUPADO)==0)
						          // ccLoteConcepto.setAgrupadoCuota(Constante.TIPO_AGRUPACION_AGRUPADO_VALOR);
						        //if(cmbValueCcTipoAgrupamientoCuotas.compareTo(Constante.TIPO_AGRUPACION_INDIVIDUAL)==0)
						          // ccLoteConcepto.setAgrupadoCuota(Constante.TIPO_AGRUPACION_INDIVIDUAL_VALOR);
						        controlycobranzaBo.create(ccLoteConcepto);
							 }
					    }
					    if(lstPeriodoCuotaSeleccionados==null || lstPeriodoCuotaSeleccionados.length==0){
					    	controlycobranzaBo.registrarLoteCuotaTodos(ccLote.getLoteId());
					    }
					    if(lstPeriodoCuotaSeleccionados!=null && lstPeriodoCuotaSeleccionados.length>0){
								for (String p1 : lstPeriodoCuotaSeleccionados){
						    			Integer lote_cuota_id=generalBo.ObtenerCorrelativoTabla("cc_lote_cuota", 1);
								        CcLoteCuota ccLoteCuota= new CcLoteCuota();
								        CcLoteCuotaPK id2 = new CcLoteCuotaPK();
								        id2.setLoteActoId(ccLoteActo.getId().getLoteActoId());
								        id2.setLoteId(ccLote.getLoteId());
								        id2.setLoteCuotaId(lote_cuota_id);
								        ccLoteCuota.setId(id2);
								        ccLoteCuota.setAnnoCuota(Integer.parseInt(p1.substring(0,4)));
								        ccLoteCuota.setCuota(Integer.parseInt(p1.substring(5)));
								        ccLoteCuota.setEstado(Constante.ESTADO_ACTIVO);
								        controlycobranzaBo.create(ccLoteCuota);
								}
					    }
					    if(lstGnSectorSeleccionados!=null && lstGnSectorSeleccionados.length>0){
							for (String p1 : lstGnSectorSeleccionados){
					    			Integer lote_sector_id=generalBo.ObtenerCorrelativoTabla("cc_lote_sector", 1);
							        CcLoteSector ccLoteSector= new CcLoteSector();
							        CcLoteSectorPK id3 = new CcLoteSectorPK();
							        id3.setSectorId((Integer)mapGnSectorSeleccionados.get(p1));
							        id3.setLoteId(ccLote.getLoteId());
							        id3.setLoteSectorId(lote_sector_id);
							        ccLoteSector.setId(id3);
							        ccLoteSector.setEstado(Constante.ESTADO_ACTIVO);
							        controlycobranzaBo.create(ccLoteSector);
							 }
					    }
						  if(lstMpTipoPersonaSeleccionados!=null && lstMpTipoPersonaSeleccionados.length>0){
								for (String p1 : lstMpTipoPersonaSeleccionados){
						    			Integer lote_tipo_persona_id=generalBo.ObtenerCorrelativoTabla("cc_lote_tipo_persona", 1);
								        CcLoteTipoPersona ccLoteTipoPersona= new CcLoteTipoPersona();
								        CcLoteTipoPersonaPK id4=new CcLoteTipoPersonaPK();
								        id4.setLoteId(ccLote.getLoteId());
								        id4.setLoteTipoPersonaId(lote_tipo_persona_id);
								        id4.setTipoPersonaId(mapMpTipoPersonaSeleccionados.get(p1));
								        ccLoteTipoPersona.setId(id4);
								        ccLoteTipoPersona.setEstado(Constante.ESTADO_ACTIVO);
								        controlycobranzaBo.create(ccLoteTipoPersona);
								    	}
								}
						  if(lstCcFirmantesSeleccionado!=null && lstCcFirmantesSeleccionado.length>0){
								for (String p1 : lstCcFirmantesSeleccionado){
						 	  Integer lote_firma_id=generalBo.ObtenerCorrelativoTabla("cc_lote_firma", 1);
							  CcLoteFirma ccLoteFirma= new CcLoteFirma();
							  CcLoteFirmaPK id5= new CcLoteFirmaPK();
							  id5.setLoteId(ccLote.getLoteId());
							  id5.setLoteFirmaId(lote_firma_id);
							  ccLoteFirma.setFirmanteId(mapFirmantesValue.get(p1));
							  ccLoteFirma.setId(id5);
							  ccLoteFirma.setEstado(Constante.ESTADO_ACTIVO);
							  controlycobranzaBo.create(ccLoteFirma);
						     }
								
						  }
						  if(lstCcTipoOrdenImpresionSeleccionado!=null && lstCcTipoOrdenImpresionSeleccionado.length>0){
							  String temp="";
							  Integer cant=lstCcTipoOrdenImpresionSeleccionado.length;
							  for (int i=0;i<cant;i++){
								  Integer lote_tipo_orden_impresion_id=generalBo.ObtenerCorrelativoTabla("cc_lote_tipo_orden_impresion", 1);
								  CcLoteTipoOrdenImpresion ccLoteTipoOrdenImpresion=new CcLoteTipoOrdenImpresion();
								  CcLoteTipoOrdenImpresionPK id6= new CcLoteTipoOrdenImpresionPK();
								  id6.setLoteTipoOrdenImpresionId(lote_tipo_orden_impresion_id);
								  id6.setLoteId(ccLote.getLoteId());
								  ccLoteTipoOrdenImpresion.setTipoOrdenImpresionId(mapTipoOrdenImpresionValue.get(lstCcTipoOrdenImpresionSeleccionado[i]));
								  ccLoteTipoOrdenImpresion.setId(id6);
								  ccLoteTipoOrdenImpresion.setOrden(i+1);
								  ccLoteTipoOrdenImpresion.setEstado(Constante.ESTADO_ACTIVO);
								  controlycobranzaBo.create(ccLoteTipoOrdenImpresion);
								  if(i>0)
									  temp=temp+","; 
								  if(ccLoteTipoOrdenImpresion.getTipoOrdenImpresionId()==1)
									  temp=temp+"s.sector_id ";
								  if(ccLoteTipoOrdenImpresion.getTipoOrdenImpresionId()==2)
									  temp=temp+"s.lugar_id ";
								  if(ccLoteTipoOrdenImpresion.getTipoOrdenImpresionId()==3)
									  temp=temp+"u.via_id ";
							  }
							  if(temp.compareTo("")==0)
								  temp="s.sector_id,s.lugar_id,u.via_id";
							  ccLote.setDescripcionOrdenamiento(temp);
							  controlycobranzaBo.edit(ccLote);
						  }
			
						  findCcLoteItem=controlycobranzaBo.getFindCcLote(lote_id);
						  getSessionMap().put("findCcLoteItem", findCcLoteItem);
		             }
			 }catch(Exception e){
					e.printStackTrace();
					WebMessages.messageFatal(e);			
			 }
		} 
	}
	
	public boolean validarIngresoLote(){
		  try{	
				//	FindMpPersona findMpPersonaTemp=new FindMpPersona();
				BigDecimal temp=BigDecimal.ZERO;
				/*
				if(ccLote.getMontoInicio()==null || ccLote.getMontoInicio().compareTo(temp)<=0){
					 addErrorMessage(getMsg("cc.errormontoinicio"));
					return false;
				 }
				if(ccLote.getMontoFin()==null || ccLote.getMontoFin().compareTo(temp)<=0){
					 addErrorMessage(getMsg("cc.errormontofin"));
					return false;
				 }
				*/
				if(tipoActoId!=Constante.TIPO_ACTO_RECOR_DEUDAS_ID){
					if(lstGnSubConceptoSeleccionados.length==0){
						addErrorMessage(getMsg("cc.errorsubconcepto"));
						return false;
					}
					if(lstPeriodoCuotaSeleccionados.length==0){
						addErrorMessage(getMsg("cc.errorperiodo"));
						return false;
					}
				}
				if(tipoActoId!=Constante.TIPO_ACTO_RESOLUCION_SANCION_ID){
					if(lstMpTipoPersonaSeleccionados.length==0){
						addErrorMessage(getMsg("cc.errortipopersona"));
						return false;
					}
					if(lstGnSectorSeleccionados.length==0){
						addErrorMessage(getMsg("cc.errorsector"));
						return false;
					}
				}
				/*
				if(lstCcTipoOrdenImpresionSeleccionado.length==0){
					addErrorMessage(getMsg("cc.errorimpresion"));
					return false;
				}*/
			}catch(Exception e){
				e.printStackTrace();
				WebMessages.messageFatal(e);
			}
			return true;
	}
	
	public void motrarLotePreliminar(){
		try{
			if(findCcLoteItem!=null){
				lstlotePreliminar=controlycobranzaBo.getAllFindCcLotePreliminar(findCcLoteItem.getLoteId(),findCcLoteItem.getTipoActoId());
//				System.out.println("resultado preliminar "+lstlotePreliminar.size());
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	public void generacionValores(){
		try{
			if(findCcLoteItem!=null){
				controlycobranzaBo.registrarActo(findCcLoteItem.getLoteId());
				ccLote=controlycobranzaBo.findCcLote(getFindCcLoteItem().getLoteId());
		 		lote_id=ccLote.getLoteId();
				lstlotePreliminar=controlycobranzaBo.getAllFindCcLoteFinal(findCcLoteItem.getLoteId(),findCcLoteItem.getTipoActoId());
//				System.out.println("resultado preliminar "+lstlotePreliminar.size());
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public void mostrarValores(){
		try{
			if(findCcLoteItem!=null){
				lstlotePreliminar=controlycobranzaBo.getAllFindCcLoteFinal(findCcLoteItem.getLoteId(),findCcLoteItem.getTipoActoId());
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
					
					JasperPrint jasperPrint = JasperFillManager.fillReport(
							(SATWEBParameterFactory.getPathReporte() + "resolucion_determinacion_predial.jasper"),
							parameters, connection);

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
	
	public void loadtipoLoteById(ValueChangeEvent event) {
		try{
		    HtmlComboBox combo=(HtmlComboBox) event.getComponent(); 
		    String value=combo.getValue().toString();
			if(value!=null){
				tipoLoteId=(Integer)mapCcTipolote.get(value);
				setCmbValuetipolote(value);
				if(tipoLoteId!=null){
					lCcTipoActo=controlycobranzaBo.getAllCcTipoActo(tipoLoteId);	
				}
			}
			viewcmbtipoacto();
			cmbValueConcepto="";
			cmbgnConcepto=null;
			lstGnSubConceptoSeleccionados=new String[0];
			lstGnSubConcepto=new ArrayList<GnSubconcepto>();
			lstPeriodoCuota=new ArrayList<FindPeriodoCuota>();
			lstPeriodoCuotaSeleccionados=new String[0];
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
  }

  public void loadtipoActoById(ValueChangeEvent event) {
		try{
			   HtmlComboBox combo=(HtmlComboBox) event.getComponent(); 
			    String value=combo.getValue().toString();
				if(value!=null){
					tipoActoId=(Integer)mapCcTipoacto.get(value);
					setCmbValuetipoacto(value);
				}
				if(tipoActoId==Constante.TIPO_ACTO_RECOR_DEUDAS_ID){
					isRecordatorioDeudas=Boolean.TRUE;
					cmbValueConcepto="";
					lstGnSubConceptoSeleccionados=null;
					lstGnSubConcepto=null;
					lstPeriodoCuotaSeleccionados=null;
					lstPeriodoCuota=null;
				}
				if(tipoActoId!=Constante.TIPO_ACTO_RECOR_DEUDAS_ID){
					    viewcmbconcepto();
						isRecordatorioDeudas=Boolean.FALSE;
				    }
				if(tipoActoId==Constante.TIPO_ACTO_RESOLUCION_SANCION_ID){
					isResolucionSancion=Boolean.TRUE;
					lstGnSectorSeleccionados=null;
					lstMpTipoPersonaSeleccionados=null;
					}
				if(tipoActoId!=Constante.TIPO_ACTO_RESOLUCION_SANCION_ID){
					isResolucionSancion=Boolean.FALSE;
				}
				if(tipoActoId!=Constante.TIPO_ACTO_RESOLUCION_DETERMINACION_ID){
					lstCcFirmantes=controlycobranzaBo.findCcFirmantes(tipoActoId,null);
			        lstCcFirmantesValue= new String[lstCcFirmantes.size()-1];
			        lstCcFirmantesSeleccionado= new String[1];
			        int k=0;
			        for(int i =0; i<lstCcFirmantes.size();i++){
			        	if(i==0)
			        		lstCcFirmantesSeleccionado[i]=lstCcFirmantes.get(i).getApellidosNombres()+"-"+lstCcFirmantes.get(i).getCargo();
				        else{
			        		lstCcFirmantesValue[k]=lstCcFirmantes.get(i).getApellidosNombres()+"-"+lstCcFirmantes.get(i).getCargo();
				        	k++;
			        	}
			        	mapFirmantesValue.put(lstCcFirmantes.get(i).getApellidosNombres()+"-"+lstCcFirmantes.get(i).getCargo(), lstCcFirmantes.get(i).getFirmanteId());
			        }
				}
				cmbValueConcepto="";
				cmbgnConcepto=null;
				lstGnSubConceptoSeleccionados=new String[0];
				lstGnSubConcepto=new ArrayList<GnSubconcepto>();
				lstPeriodoCuota=new ArrayList<FindPeriodoCuota>();
				lstPeriodoCuotaSeleccionados=new String[0];
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
  public void loadGnConceptoById(ValueChangeEvent event) {
		try{
			   HtmlComboBox combo=(HtmlComboBox) event.getComponent(); 
			    String value=combo.getValue().toString();
				if(value!=null){
					conceptoId=(Integer)mapgnConcepto.get(value);
					setCmbValueConcepto(value);
				}
				viewsubconcepto();
				lstPeriodoCuota=controlycobranzaBo.findDtPeriodoCuota(conceptoId, null,tipoActoId);

				if(tipoActoId==Constante.TIPO_ACTO_RESOLUCION_DETERMINACION_ID){
					lstCcFirmantes=controlycobranzaBo.findCcFirmantes(tipoActoId,conceptoId);
			        lstCcFirmantesValue= new String[lstCcFirmantes.size()-1];
			        lstCcFirmantesSeleccionado= new String[1];
			        int k=0;
			        for(int i =0; i<lstCcFirmantes.size();i++){
			        	if(i==0)
			        		lstCcFirmantesSeleccionado[i]=lstCcFirmantes.get(i).getApellidosNombres()+"-"+lstCcFirmantes.get(i).getCargo();
				        else{
			        		lstCcFirmantesValue[k]=lstCcFirmantes.get(i).getApellidosNombres()+"-"+lstCcFirmantes.get(i).getCargo();
				        	k++;
			        	}
			        	mapFirmantesValue.put(lstCcFirmantes.get(i).getApellidosNombres()+"-"+lstCcFirmantes.get(i).getCargo(), lstCcFirmantes.get(i).getFirmanteId());
			        }
				} 
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
	
	 
	/***
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
	 * @param event
	 */
  public void loadAgrupadoPorBienById(ValueChangeEvent event) {
		try{
			   HtmlComboBox combo=(HtmlComboBox) event.getComponent(); 
			   String value=combo.getValue().toString();
				if(value!=null){
					agrupadoPorBien=(Integer)mapCcTipoAgrupamiento.get(value);
					setCmbValueCcTipoAgrupamientoBien(value);
				}
					 
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
  
  public void loadAgrupadoPorCuotaById(ValueChangeEvent event) {
		try{
			   HtmlComboBox combo=(HtmlComboBox) event.getComponent(); 
			   String value=combo.getValue().toString();
				if(value!=null){
					agrupadoPorCuotas=(Integer)mapCcTipoAgrupamientoCuota.get(value);
					setCmbValueCcTipoAgrupamientoCuotas(value);
				}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
  }

  public void loadGnUsuarioById(ValueChangeEvent event) {
		try{
			   HtmlComboBox combo=(HtmlComboBox) event.getComponent(); 
			   String value=combo.getValue().toString();
				if(value!=null){
					UsuarioFirmanteId=(Integer)mapGnUsuario.get(value);
					setCmbValueGnUsuario(value);
				}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}

  public void viewsubconcepto(){
	  try{  
		  lstGnSubConcepto=controlycobranzaBo.getAllGnSubConcepto(conceptoId);
		  Iterator<GnSubconcepto> it2 = lstGnSubConcepto.iterator(); 
		  while (it2.hasNext()){
			  GnSubconcepto obj = it2.next();  
			  mapSubConceptoSeleccionados.put(obj.getDescripcion().trim(), obj.getId().getSubconceptoId());
		  		}
		  }catch(Exception e){
		  			e.printStackTrace();
		  			WebMessages.messageFatal(e);			
	}
  }
  
  public void viewcmbconcepto(){
		try{
			//CcTipoActo
	        List<GnConcepto> lstCcTipoActo=controlycobranzaBo.getAllGnConcepto(tipoActoId);
			Iterator<GnConcepto> it1 = lstCcTipoActo.iterator();  
			lstgnConcepto=new ArrayList<SelectItem>();
			 
	        while (it1.hasNext()){
	        	GnConcepto obj = it1.next();  
	        	lstgnConcepto.add(new SelectItem(obj.getDescripcion(),String.valueOf(obj.getConceptoId())));  
	        	mapgnConcepto.put(obj.getDescripcion().trim(), obj.getConceptoId());
	        }
	        cmbValueConcepto="";
	        cmbgnConcepto=null;
			
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	  
  }	

  public void viewcmbtipoacto(){
		try{
			//CcTipoActo
	        List<CcTipoActo> lstCcTipoActo=controlycobranzaBo.getAllCcTipoActo(tipoLoteId);
			Iterator<CcTipoActo> it1 = lstCcTipoActo.iterator();  
			lsttipoacto=new ArrayList<SelectItem>();
			 
	        while (it1.hasNext()){
	        	CcTipoActo obj = it1.next();  
	        	lsttipoacto.add(new SelectItem(obj.getDescripcion(),String.valueOf(obj.getTipoActoId())));  
	        	mapCcTipoacto.put(obj.getDescripcion().trim(), obj.getTipoActoId());
	        }
	        cmbValuetipoacto="";
			cmbtipoacto=null;
			
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	  
  }
	
 
  
  public String preliminar(){
	   try{
		   Object pk=lote_id;
		   ccLote=controlycobranzaBo.findCcLote(pk);
	   }catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	   return sendRedirectPrincipal();
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
	
	public HtmlComboBox getCmbtipodlote() {
		return cmbtipodlote;
	}


	public void setCmbtipodlote(HtmlComboBox cmbtipodlote) {
		this.cmbtipodlote = cmbtipodlote;
	}


	public List<SelectItem> getLsttipolote() {
		return lsttipolote;
	}


	public void setLsttipolote(List<SelectItem> lsttipolote) {
		this.lsttipolote = lsttipolote;
	}


	public HashMap<String, Integer> getMapCcTipolote() {
		return mapCcTipolote;
	}


	public void setMapRpTipolote(HashMap<String, Integer> mapCcTipolote) {
		this.mapCcTipolote = mapCcTipolote;
	}


	public String getCmbValuetipolote() {
		return cmbValuetipolote;
	}


	public void setCmbValuetipolote(String cmbValuetipolote) {
		this.cmbValuetipolote = cmbValuetipolote;
	}


	public HtmlComboBox getCmbtipoacto() {
		return cmbtipoacto;
	}


	public void setCmbtipoacto(HtmlComboBox cmbtipoacto) {
		this.cmbtipoacto = cmbtipoacto;
	}



	public HashMap<String, Integer> getMapCcTipoacto() {
		return mapCcTipoacto;
	}


	public void setMapCcTipoacto(HashMap<String, Integer> mapCcTipoacto) {
		this.mapCcTipoacto = mapCcTipoacto;
	}


	public String getCmbValuetipoacto() {
		return cmbValuetipoacto;
	}


	public void setCmbValuetipoacto(String cmbValuetipoacto) {
		this.cmbValuetipoacto = cmbValuetipoacto;
	}


	public Integer getTipoLoteId() {
		return tipoLoteId;
	}


	public void setTipoLoteId(Integer tipoLoteId) {
		this.tipoLoteId = tipoLoteId;
	}


	public List<CcTipoActo> getlCcTipoActo() {
		return lCcTipoActo;
	}


	public void setlCcTipoActo(List<CcTipoActo> lCcTipoActo) {
		this.lCcTipoActo = lCcTipoActo;
	}


	public List<SelectItem> getLsttipoacto() {
		return lsttipoacto;
	}


	public void setLsttipoacto(List<SelectItem> lsttipoacto) {
		this.lsttipoacto = lsttipoacto;
	}


	public HtmlComboBox getCmbgnConcepto() {
		return cmbgnConcepto;
	}


	public void setCmbgnConcepto(HtmlComboBox cmbgnConcepto) {
		this.cmbgnConcepto = cmbgnConcepto;
	}


	public List<SelectItem> getLstgnConcepto() {
		return lstgnConcepto;
	}


	public void setLstgnConcepto(List<SelectItem> lstgnConcepto) {
		this.lstgnConcepto = lstgnConcepto;
	}


	public HashMap<String, Integer> getMapgnConcepto() {
		return mapgnConcepto;
	}


	public void setMapgnConcepto(HashMap<String, Integer> mapgnConcepto) {
		this.mapgnConcepto = mapgnConcepto;
	}


	public String getCmbValueConcepto() {
		return cmbValueConcepto;
	}


	public void setCmbValueConcepto(String cmbValueConcepto) {
		this.cmbValueConcepto = cmbValueConcepto;
	}


	public Integer getTipoActoId() {
		return tipoActoId;
	}


	public void setTipoActoId(Integer tipoActoId) {
		this.tipoActoId = tipoActoId;
	}


	public Integer getConceptoId() {
		return conceptoId;
	}


	public void setConceptoId(Integer conceptoId) {
		this.conceptoId = conceptoId;
	}


	public List<GnSubconcepto> getLstGnSubConcepto() {
		return lstGnSubConcepto;
	}


	public void setLstGnSubConcepto(List<GnSubconcepto> lstGnSubConcepto) {
		this.lstGnSubConcepto = lstGnSubConcepto;
	}

	public List<GnSector> getLstGnSector() {
		return lstGnSector;
	}


	public void setLstGnSector(List<GnSector> lstGnSector) {
		this.lstGnSector = lstGnSector;
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


	public HashMap<String, Integer> getMapGnSectorSeleccionados() {
		return mapGnSectorSeleccionados;
	}


	public void setMapGnSectorSeleccionados(HashMap<String, Integer> mapGnSectorSeleccionados) {
		this.mapGnSectorSeleccionados = mapGnSectorSeleccionados;
	}


	public HashMap<String, Integer> getMapMpTipoPersonaSeleccionados() {
		return mapMpTipoPersonaSeleccionados;
	}


	public void setMapMpTipoPersonaSeleccionados(
			HashMap<String, Integer> mapMpTipoPersonaSeleccionados) {
		this.mapMpTipoPersonaSeleccionados = mapMpTipoPersonaSeleccionados;
	}


	public List<MpTipoPersona> getLstTipoPersona() {
		return lstTipoPersona;
	}


	public void setLstTipoPersona(List<MpTipoPersona> lstTipoPersona) {
		this.lstTipoPersona = lstTipoPersona;
	}


	public HashMap<String, Integer> getMapSubConceptoSeleccionados() {
		return mapSubConceptoSeleccionados;
	}


	public void setMapSubConceptoSeleccionados(
			HashMap<String, Integer> mapSubConceptoSeleccionados) {
		this.mapSubConceptoSeleccionados = mapSubConceptoSeleccionados;
	}


	public List<DtFechaVencimiento> getLstDtFechaVencimiento() {
		return lstDtFechaVencimiento;
	}


	public void setLstDtFechaVencimiento(List<DtFechaVencimiento> lstDtFechaVencimiento) {
		this.lstDtFechaVencimiento = lstDtFechaVencimiento;
	}


	public String[] getLstDtFechaVencimientoSeleccionados() {
		return lstDtFechaVencimientoSeleccionados;
	}


	public void setLstDtFechaVencimientoSeleccionados(
			String[] lstDtFechaVencimientoSeleccionados) {
		this.lstDtFechaVencimientoSeleccionados = lstDtFechaVencimientoSeleccionados;
	}


	public List<FindPeriodoCuota> getLstPeriodoCuota() {
		return lstPeriodoCuota;
	}


	public void setLstPeriodoCuota(List<FindPeriodoCuota> lstPeriodoCuota) {
		this.lstPeriodoCuota = lstPeriodoCuota;
	}


	public String[] getLstPeriodoCuotaSeleccionados() {
		return lstPeriodoCuotaSeleccionados;
	}


	public void setLstPeriodoCuotaSeleccionados(
			String[] lstPeriodoCuotaSeleccionados) {
		this.lstPeriodoCuotaSeleccionados = lstPeriodoCuotaSeleccionados;
	}





	public List<CcTipoOrdenImpresion> getLstCcTipoOrdenImpresion() {
		return lstCcTipoOrdenImpresion;
	}


	public void setLstCcTipoOrdenImpresion(List<CcTipoOrdenImpresion> lstCcTipoOrdenImpresion) {
		this.lstCcTipoOrdenImpresion = lstCcTipoOrdenImpresion;
	}


	public String[] getLstCcTipoOrdenImpresionSeleccionado() {
		return lstCcTipoOrdenImpresionSeleccionado;
	}


	public void setLstCcTipoOrdenImpresionSeleccionado(
			String[] lstCcTipoOrdenImpresionSeleccionado) {
		this.lstCcTipoOrdenImpresionSeleccionado = lstCcTipoOrdenImpresionSeleccionado;
	}


	public String[] getLstCcTipoOrdenImpresionValue() {
		return lstCcTipoOrdenImpresionValue;
	}


	public void setLstCcTipoOrdenImpresionValue(
			String[] lstCcTipoOrdenImpresionValue) {
		this.lstCcTipoOrdenImpresionValue = lstCcTipoOrdenImpresionValue;
	}


	public FindCcLote getFindCcLote() {
		return findCcLote;
	}


	public void setFindCcLote(FindCcLote findCcLote) {
		this.findCcLote = findCcLote;
	}


	public FindCcLote getFindCcLoteItem() {
		return findCcLoteItem;
	}


	public void setFindCcLoteItem(FindCcLote findCcLoteItem) {
		this.findCcLoteItem = findCcLoteItem;
	}


	public Integer getLote_id() {
		return lote_id;
	}


	public void setLote_id(Integer lote_id) {
		this.lote_id = lote_id;
	}


	public HtmlComboBox getCmbCcTipoAgrupamiento() {
		return cmbCcTipoAgrupamiento;
	}


	public void setCmbCcTipoAgrupamiento(HtmlComboBox cmbCcTipoAgrupamiento) {
		this.cmbCcTipoAgrupamiento = cmbCcTipoAgrupamiento;
	}


	public List<SelectItem> getLstCcTipoAgrupamiento() {
		return lstCcTipoAgrupamiento;
	}


	public void setLstCcTipoAgrupamiento(List<SelectItem> lstCcTipoAgrupamiento) {
		this.lstCcTipoAgrupamiento = lstCcTipoAgrupamiento;
	}


	public HashMap<String, Integer> getMapCcTipoAgrupamiento() {
		return mapCcTipoAgrupamiento;
	}


	public void setMapCcTipoAgrupamiento(HashMap<String, Integer> mapCcTipoAgrupamiento) {
		this.mapCcTipoAgrupamiento = mapCcTipoAgrupamiento;
	}


	public String getCmbValueCcTipoAgrupamientoBien() {
		return cmbValueCcTipoAgrupamientoBien;
	}


	public void setCmbValueCcTipoAgrupamientoBien(
			String cmbValueCcTipoAgrupamientoBien) {
		this.cmbValueCcTipoAgrupamientoBien = cmbValueCcTipoAgrupamientoBien;
	}


	public String getCmbValueCcTipoAgrupamientoCuotas() {
		return cmbValueCcTipoAgrupamientoCuotas;
	}


	public void setCmbValueCcTipoAgrupamientoCuotas(
			String cmbValueCcTipoAgrupamientoCuotas) {
		this.cmbValueCcTipoAgrupamientoCuotas = cmbValueCcTipoAgrupamientoCuotas;
	}


	public List<CcTipoAgrupamiento> getLstTipoAgrupamiento() {
		return lstTipoAgrupamiento;
	}


	public void setLstTipoAgrupamiento(List<CcTipoAgrupamiento> lstTipoAgrupamiento) {
		this.lstTipoAgrupamiento = lstTipoAgrupamiento;
	}


	public Integer getAgrupadoPorBien() {
		return agrupadoPorBien;
	}


	public void setAgrupadoPorBien(Integer agrupadoPorBien) {
		this.agrupadoPorBien = agrupadoPorBien;
	}


	public Integer getAgrupadoPorCuotas() {
		return agrupadoPorCuotas;
	}


	public void setAgrupadoPorCuotas(Integer agrupadoPorCuotas) {
		this.agrupadoPorCuotas = agrupadoPorCuotas;
	}


	public HtmlComboBox getCmbCcTipoAgrupamientoCuota() {
		return cmbCcTipoAgrupamientoCuota;
	}


	public void setCmbCcTipoAgrupamientoCuota(HtmlComboBox cmbCcTipoAgrupamientoCuota) {
		this.cmbCcTipoAgrupamientoCuota = cmbCcTipoAgrupamientoCuota;
	}


	public List<SelectItem> getLstCcTipoAgrupamientoCuota() {
		return lstCcTipoAgrupamientoCuota;
	}


	public void setLstCcTipoAgrupamientoCuota(
			List<SelectItem> lstCcTipoAgrupamientoCuota) {
		this.lstCcTipoAgrupamientoCuota = lstCcTipoAgrupamientoCuota;
	}


	public HashMap<String, Integer> getMapCcTipoAgrupamientoCuota() {
		return mapCcTipoAgrupamientoCuota;
	}


	public void setMapCcTipoAgrupamientoCuota(
			HashMap<String, Integer> mapCcTipoAgrupamientoCuota) {
		this.mapCcTipoAgrupamientoCuota = mapCcTipoAgrupamientoCuota;
	}


	public HtmlComboBox getCmbGnUsuario() {
		return cmbGnUsuario;
	}


	public void setCmbGnUsuario(HtmlComboBox cmbGnUsuario) {
		this.cmbGnUsuario = cmbGnUsuario;
	}


	public List<SelectItem> getLstGnUsuario() {
		return lstGnUsuario;
	}


	public void setLstGnUsuario(List<SelectItem> lstGnUsuario) {
		this.lstGnUsuario = lstGnUsuario;
	}


	public HashMap<String, Integer> getMapGnUsuario() {
		return mapGnUsuario;
	}


	public void setMapGnUsuario(HashMap<String, Integer> mapGnUsuario) {
		this.mapGnUsuario = mapGnUsuario;
	}


	public String getCmbValueGnUsuario() {
		return cmbValueGnUsuario;
	}


	public void setCmbValueGnUsuario(String cmbValueGnUsuario) {
		this.cmbValueGnUsuario = cmbValueGnUsuario;
	}


	public List<SgUsuario> getLstUsuarios() {
		return lstUsuarios;
	}


	public void setLstUsuarios(List<SgUsuario> lstUsuarios) {
		this.lstUsuarios = lstUsuarios;
	}


	public Integer getUsuarioFirmanteId() {
		return UsuarioFirmanteId;
	}


	public void setUsuarioFirmanteId(Integer usuarioFirmanteId) {
		UsuarioFirmanteId = usuarioFirmanteId;
	}


	public HashMap<String, Integer> getMapTipoOrdenImpresionValue() {
		return mapTipoOrdenImpresionValue;
	}


	public void setMapTipoOrdenImpresionValue(
			HashMap<String, Integer> mapTipoOrdenImpresionValue) {
		this.mapTipoOrdenImpresionValue = mapTipoOrdenImpresionValue;
	}


	public Boolean getIsRecordatorioDeudas() {
		return isRecordatorioDeudas;
	}


	public void setIsRecordatorioDeudas(Boolean isRecordatorioDeudas) {
		this.isRecordatorioDeudas = isRecordatorioDeudas;
	}


	public Boolean getIsResolucionSancion() {
		return isResolucionSancion;
	}


	public void setIsResolucionSancion(Boolean isResolucionSancion) {
		this.isResolucionSancion = isResolucionSancion;
	}


	public List<CcFirmante> getLstCcFirmantes() {
		return lstCcFirmantes;
	}


	public void setLstCcFirmantes(List<CcFirmante> lstCcFirmantes) {
		this.lstCcFirmantes = lstCcFirmantes;
	}


	public String[] getLstCcFirmantesValue() {
		return lstCcFirmantesValue;
	}


	public void setLstCcFirmantesValue(String[] lstCcFirmantesValue) {
		this.lstCcFirmantesValue = lstCcFirmantesValue;
	}


	public String[] getLstCcFirmantesSeleccionado() {
		return lstCcFirmantesSeleccionado;
	}


	public void setLstCcFirmantesSeleccionado(
			String[] lstCcFirmantesSeleccionado) {
		this.lstCcFirmantesSeleccionado = lstCcFirmantesSeleccionado;
	}


	public HashMap<String, Integer> getMapFirmantesValue() {
		return mapFirmantesValue;
	}


	public void setMapFirmantesValue(HashMap<String, Integer> mapFirmantesValue) {
		this.mapFirmantesValue = mapFirmantesValue;
	}


	public List<FindCcLoteDetalleActo> getLstlotePreliminar() {
		return lstlotePreliminar;
	}

	public void setLstlotePreliminar(List<FindCcLoteDetalleActo> lstlotePreliminar) {
		this.lstlotePreliminar = lstlotePreliminar;
	}

}
