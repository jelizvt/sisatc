package com.sat.sisat.controlycobranza.managed;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sat.sisat.common.business.GeneralBoRemote;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.controlycobranza.business.ControlyCobranzaBoRemote;
import com.sat.sisat.controlycobranza.dto.CarteraItemDTO;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.persistence.entity.CcCarteraDeuda;
import com.sat.sisat.persona.business.PersonaBoRemote;

@ManagedBean
@ViewScoped
public class CarteraDetalleManaged extends BaseManaged {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2855096379356064580L;

	@EJB
	PersonaBoRemote personaBo;

	@EJB
	GeneralBoRemote generalBo;

	@EJB
	ControlyCobranzaBoRemote controlycobranzaBo;	
	
	private CcCarteraDeuda ccCarteraDeuda;	
	
	private List<CarteraItemDTO> listCarteraItemDTOs;
	
	@PostConstruct
	public void init(){
		
		try {
			
			Integer carteraDeudaId = (Integer) this.getSessionManaged().getSessionMap().get("carteraDeudaId");
			this.getSessionManaged().getSessionMap().remove("carteraDeudaId");
			
			ccCarteraDeuda = controlycobranzaBo.obtenerCcCarteraDeuda(carteraDeudaId);
			
			listCarteraItemDTOs = controlycobranzaBo.obtenerCarteraItems(carteraDeudaId);
			
		} catch (SisatException e) {
		
			WebMessages.messageError(e.getMessage());
		}				
	}

	public CcCarteraDeuda getCcCarteraDeuda() {
		return ccCarteraDeuda;
	}

	public void setCcCarteraDeuda(CcCarteraDeuda ccCarteraDeuda) {
		this.ccCarteraDeuda = ccCarteraDeuda;
	}

	public List<CarteraItemDTO> getListCarteraItemDTOs() {
		return listCarteraItemDTOs;
	}

	public void setListCarteraItemDTOs(List<CarteraItemDTO> listCarteraItemDTOs) {
		this.listCarteraItemDTOs = listCarteraItemDTOs;
	}	
}
