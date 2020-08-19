package com.sat.sisat.papeletas.managed;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.papeleta.business.PapeletaBoRemote;
import com.sat.sisat.papeleta.dto.CargaLoteDTO;
import com.sat.sisat.papeleta.dto.GridDetalleLote;
import com.sat.sisat.persistence.entity.PaInfraccion;

@ManagedBean
@ViewScoped
public class VerificarLotesPapeletasManaged extends BaseManaged {
	
	@EJB
	PapeletaBoRemote papeletaBo;
	private List<GridDetalleLote> gridDetalleLotes = new ArrayList<GridDetalleLote>();
	private GridDetalleLote currentItem;
	private Integer cargaLotesId; 
	private CargaLoteDTO cargaLote;
	
	private Map<String,Integer> mapInfraccion = new HashMap<String,Integer>();
	private Map<Integer,String> mapIInfraccion = new HashMap<Integer,String>();
	
	public VerificarLotesPapeletasManaged(){
		
	}
	
	@PostConstruct
	public void init(){
		try{
			Integer leyId = 1;//por defecto Ley N 27181 Ley General de Transporte y Tr�nsito Terrestre
	        PaInfraccion infraccion = new PaInfraccion();
	        infraccion.setLeyId(leyId);
	        Calendar fechaInfraccion = Calendar.getInstance();
	        List<PaInfraccion> lstPaInfrc = papeletaBo.getAllInfracciones(infraccion,DateUtil.convertDateToString(fechaInfraccion.getTime()));
	        Iterator<PaInfraccion> ite2 = lstPaInfrc.iterator();
	        while(ite2.hasNext()){
	        	PaInfraccion obj = ite2.next();
	        	mapInfraccion.put(obj.getDescripcionCorta().trim(), obj.getInfraccionId());
	        	mapIInfraccion.put(obj.getInfraccionId(),obj.getDescripcionCorta().trim());
	        }
	        CargaLoteDTO carga=(CargaLoteDTO)getSessionMap().get("cargaLote");
			if(carga!=null&&carga.getCargaLotesId()>0){
				setCargaLote(carga);
				setCargaLotesId(carga.getCargaLotesId());
				cargar();
			}else{
				setCargaLotesId(Constante.RESULT_PENDING);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void cargar() {
		try{
			if(getCargaLotesId()!=null&&getCargaLotesId()>0){
				gridDetalleLotes = papeletaBo.buscarPapeletas(getCargaLotesId(),Constante.OPERACION_LOTE_PAPELETA_VERFICAR);	
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public String actualizacion(){
		if(currentItem!=null){
			getSessionMap().put("papeletaId", currentItem.getPapeletaId());
			getSessionMap().put("pageReturn", "/sisat/papeletas/verificarLotePapeleta.xhtml");
			getSessionMap().put("estadoPapeleta", Constante.ESTADO_PAPELETA_DEFINITIVO);
		}
		closeSession("registroPapeletasManaged");
		
		return sendRedirectPrincipal();
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
