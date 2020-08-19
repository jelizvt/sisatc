package com.sat.sisat.coactivav2.managed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import com.sat.sisat.cobranzacoactiva.business.CobranzaCoactivaBoRemote;
import com.sat.sisat.cobranzacoactiva.dto.DetalleCostas;
import com.sat.sisat.cobranzacoactiva.dto.EjecutorCoactivo;
import com.sat.sisat.cobranzacoactiva.dto.InformeTransferido;
import com.sat.sisat.cobranzacoactiva.dto.InformeTransferidoDetalle;
import com.sat.sisat.cobranzacoactiva.dto.ListadoArea;
import com.sat.sisat.cobranzacoactiva.dto.ListadoEstadoTransferencia;
import com.sat.sisat.common.business.GeneralBoRemote;
import com.sat.sisat.common.dto.GenericDTO;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.FacesUtil;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.controlycobranza.business.ControlyCobranzaBoRemote;
import com.sat.sisat.persistence.entity.MpTipoDocuIdentidad;

@ManagedBean
@ViewScoped
public class ManteDetalleCostasManaged extends BaseManaged {
	private static final long serialVersionUID = 1673161260001450282L;
	@EJB
	CobranzaCoactivaBoRemote cobranzaCoactivaBo;
	
	private List<DetalleCostas> records = new ArrayList<DetalleCostas>();
	private DetalleCostas selected = new DetalleCostas();
	private Integer periodo;
	
	@PostConstruct
	public void init() {
		try {
			GenericDTO selPeriodo=(GenericDTO)getSessionMap().get("PeriodoCostas");
			
			if(selPeriodo!=null&&selPeriodo.getId()>0){
				periodo=selPeriodo.getId();
				records=cobranzaCoactivaBo.consultarDetalleCostas(periodo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void editar(){
		try{
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void guardar(){
		try{
			if(selected!=null&&selected.getSubconceptoId()>0){
				cobranzaCoactivaBo.registraCosta(selected.getConceptoTasaId(), selected.getConceptoId(), 
						selected.getSubconceptoId(), selected.getPeriodo(), selected.getValor(),selected.getEstado()?1:9);
				records=cobranzaCoactivaBo.consultarDetalleCostas(periodo);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	

	public List<DetalleCostas> getRecords() {
		return records;
	}

	public void setRecords(List<DetalleCostas> records) {
		this.records = records;
	}

	public DetalleCostas getSelected() {
		return selected;
	}

	public void setSelected(DetalleCostas selected) {
		this.selected = selected;
	}

	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}
}
