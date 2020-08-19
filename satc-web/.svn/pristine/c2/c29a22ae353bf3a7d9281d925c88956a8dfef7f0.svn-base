package com.sat.sisat.controlycobranza.managed;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

import com.sat.sisat.common.business.GeneralBoRemote;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.FacesUtil;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.controlycobranza.business.ControlyCobranzaBoRemote;
import com.sat.sisat.controlycobranza.dto.FindCcLote;
import com.sat.sisat.persona.business.PersonaBoRemote;

@ManagedBean
@ViewScoped
public class BusquedaLoteOrdinarioManaged extends BaseManaged {
	@EJB
	PersonaBoRemote personaBo;
	
	@EJB
	GeneralBoRemote generalBo;
	
	@EJB
	ControlyCobranzaBoRemote controlycobranzaBo;
	
	private List<FindCcLote> lstFindLote;
	private FindCcLote findCcLoteItem= new FindCcLote();
    private String nroLote=null;
	private java.util.Date fechaRegistro;
	private String estadoLote;
    @PostConstruct
	public void init() {
		try{
			lstFindLote=controlycobranzaBo.getAllFindCcLote(nroLote,null,estadoLote,Constante.TIPO_COBRANZA_ORDINARIO);
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}
    
    public String registroNuevoLote(){
		FacesUtil.closeSession("registroLoteOrdinarioManaged");
		getSessionMap().remove("findCcLoteItem");
		return sendRedirectPrincipal();
	}
    
    public String registroNuevoLoteContrib(){
    	FacesUtil.closeSession("registroLoteOrdinarioContribManaged");
		getSessionMap().remove("findCcLoteItem");
		return sendRedirectPrincipal();
    }
    
    public void loadEstadoById(ValueChangeEvent event) {
		try{
			estadoLote=(String)event.getNewValue();
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
    }
    
    public void limpiar(){
    	estadoLote="";
    	nroLote="";
    	fechaRegistro=null;
    }
    
	public String edit(){
		try{
			FacesUtil.closeSession("registroLoteOrdinarioManaged");
			getSessionMap().put("findCcLoteItem", findCcLoteItem);
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		return sendRedirectPrincipal();
	}

	public void buscar(){
		try{
			String fecha="";
			String[] lote = nroLote.split("-");
			nroLote="";
			for (int i = 0; i < lote.length; i++) {
				nroLote =  nroLote + lote[i];
			}
			if(nroLote!="" && nroLote!=null){
				if(lote.length!=2)
					nroLote="";
			}
			if(estadoLote!=null && estadoLote!=""){
				if(estadoLote.compareTo(Constante.ESTADO_LOTE_PRELIMINAR)==0)
					estadoLote=Constante.ESTADO_LOTE_PRELIMINAR_VALOR;
				if(estadoLote.compareTo(Constante.ESTADO_LOTE_FINAL)==0)
					estadoLote=Constante.ESTADO_LOTE_FINAL_VALOR;
			}
			if(fechaRegistro!=null){
			   fecha=	DateUtil.convertDateToString(fechaRegistro);
			}
			lstFindLote=controlycobranzaBo.getAllFindCcLote(nroLote,fecha,estadoLote,Constante.TIPO_COBRANZA_ORDINARIO);
			
			}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public List<FindCcLote> getLstFindLote() {
		return lstFindLote;
	}

	public void setLstFindLote(List<FindCcLote> lstFindLote) {
		this.lstFindLote = lstFindLote;
	}

	public FindCcLote getFindCcLoteItem() {
		return findCcLoteItem;
	}
	public void setFindCcLoteItem(FindCcLote findCcLoteItem) {
		this.findCcLoteItem = findCcLoteItem;
	}
	public String getNroLote() {
		return nroLote;
	}
	public void setNroLote(String nroLote) {
		this.nroLote = nroLote;
	}
	public java.util.Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(java.util.Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public String getEstadoLote() {
		return estadoLote;
	}
	public void setEstadoLote(String estadoLote) {
		this.estadoLote = estadoLote;
	}
}
