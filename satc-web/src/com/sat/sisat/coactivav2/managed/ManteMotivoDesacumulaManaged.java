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
public class ManteMotivoDesacumulaManaged extends BaseManaged {
	private static final long serialVersionUID = 1673161260001450282L;
	@EJB
	CobranzaCoactivaBoRemote cobranzaCoactivaBo;
	
	private List<GenericDTO> records = new ArrayList<GenericDTO>();
	private GenericDTO selected = new GenericDTO();
	
	@PostConstruct
	public void init() {
		try {
			records=cobranzaCoactivaBo.consultarMotivoDesacumula();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void nuevo(){
		try{			
			selected = new GenericDTO();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
		
	public void guardar(){
		try{
			if(selected!=null){
				cobranzaCoactivaBo.registraMotivoDesacumula(selected.getId(), selected.getDescripcion(), selected.getEstado()?1:9, getSessionManaged().getUsuarioLogIn().getUsuarioId(),getSessionManaged().getTerminalLogIn());
				records=cobranzaCoactivaBo.consultarMotivoDesacumula();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public List<GenericDTO> getRecords() {
		return records;
	}

	public void setRecords(List<GenericDTO> records) {
		this.records = records;
	}

	public GenericDTO getSelected() {
		return selected;
	}

	public void setSelected(GenericDTO selected) {
		this.selected = selected;
	}
}
