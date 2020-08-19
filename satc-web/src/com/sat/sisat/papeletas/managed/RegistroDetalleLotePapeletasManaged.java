package com.sat.sisat.papeletas.managed;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.papeleta.business.PapeletaBoRemote;
import com.sat.sisat.papeleta.dto.CargaLoteDTO;
import com.sat.sisat.papeleta.dto.GridDetalleLote;

@ManagedBean
@ViewScoped
public class RegistroDetalleLotePapeletasManaged extends BaseManaged{
	
	@EJB
	PapeletaBoRemote papeletaBo;
	private List<GridDetalleLote> gridDetalleLotes = new ArrayList<GridDetalleLote>();
	private GridDetalleLote currentItem;
	private Integer cargaLotesId; 
	
	private CargaLoteDTO cargaLote;
	
	public RegistroDetalleLotePapeletasManaged(){
		
	}
	
	@PostConstruct
	public void init(){
		CargaLoteDTO carga=(CargaLoteDTO)getSessionMap().get("cargaLote");
		if(carga!=null&&carga.getCargaLotesId()>0){
			setCargaLote(carga);
			setCargaLotesId(carga.getCargaLotesId());
			cargar();
		}else{
			setCargaLotesId(Constante.RESULT_PENDING);
		}
	}
	
	public String actualizacion(){
		if(currentItem!=null){
			getSessionMap().put("papeletaId", currentItem.getPapeletaId());
			getSessionMap().put("pageReturn", "/sisat/papeletas/registroDetalleLotePapeleta.xhtml");
			getSessionMap().put("estadoPapeleta", Constante.ESTADO_PAPELETA_DEFINITIVO);
		}
		closeSession("registroPapeletasManaged");
		return sendRedirectPrincipal();
	}
	public String inscripcion(){
		getSessionMap().put("papeletaId", Constante.RESULT_PENDING);
		getSessionMap().put("cargaLotesId", cargaLotesId);
		getSessionMap().put("pageReturn", "/sisat/papeletas/registroDetalleLotePapeleta.xhtml");
		closeSession("registroPapeletasManaged");
		return sendRedirectPrincipal();
	}
	public void cargar() {
		try{
			if(getCargaLotesId()!=null&&getCargaLotesId()>0){
				gridDetalleLotes = papeletaBo.buscarPapeletas(getCargaLotesId(),Constante.OPERACION_LOTE_PAPELETA_AGREGAR);	
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public List<GridDetalleLote> getGridDetalleLotes() {
		return gridDetalleLotes;
	}

	public void setGridDetalleLotes(List<GridDetalleLote> gridDetalleLotes) {
		this.gridDetalleLotes = gridDetalleLotes;
	}
	
	public GridDetalleLote getCurrentItem() {
		return currentItem;
	}

	public void setCurrentItem(GridDetalleLote currentItem) {
		this.currentItem = currentItem;
	}

	public Integer getCargaLotesId() {
		return cargaLotesId;
	}

	public void setCargaLotesId(Integer cargaLotesId) {
		this.cargaLotesId = cargaLotesId;
	}
	public CargaLoteDTO getCargaLote() {
		return cargaLote;
	}

	public void setCargaLote(CargaLoteDTO cargaLote) {
		this.cargaLote = cargaLote;
	}
}
