package com.sat.sisat.fiscalizacion.managed;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.controlycobranza.dto.FindCcLote;
import com.sat.sisat.fiscalizacion.business.FiscalizacionBoRemote;
import com.sat.sisat.persistence.entity.RpFiscalizacionPrograma;
import com.sat.sisat.persistence.entity.RvOmisosVehicular;

@ManagedBean
@ViewScoped
public class busquedaDetalleOmisoManaged extends BaseManaged {
	
	private static final long serialVersionUID = 1;
	
	@EJB
	FiscalizacionBoRemote ficalizacionBo;
	
	private Integer omiso_id;	
	private List<RvOmisosVehicular> lstRvOmisosVehicular=null;
	private RvOmisosVehicular omisoDetItem=new RvOmisosVehicular();
	
	public busquedaDetalleOmisoManaged() throws Exception{}
	
	@PostConstruct
	public void init() throws Exception {
		try {
			RvOmisosVehicular omisoDetItem = (RvOmisosVehicular) getSessionMap().get("omisoDetItm");
			if (omisoDetItem != null) {
				setOmisoDetItem(omisoDetItem);
				setOmiso_id(omisoDetItem.getVehicularOmisosId());
				//setFindCcLoteItem(findCcLoteItem);
				//setLote_id(findCcLoteItem.getLoteId());
				//mostrarRegistros();
			}			
			
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}		
		
	}

	public Integer getOmiso_id() {
		return omiso_id;
	}

	public void setOmiso_id(Integer omiso_id) {
		this.omiso_id = omiso_id;
	}

	public List<RvOmisosVehicular> getLstRvOmisosVehicular() {
		return lstRvOmisosVehicular;
	}

	public void setLstRvOmisosVehicular(List<RvOmisosVehicular> lstRvOmisosVehicular) {
		this.lstRvOmisosVehicular = lstRvOmisosVehicular;
	}

	public RvOmisosVehicular getOmisoDetItem() {
		return omisoDetItem;
	}

	public void setOmisoDetItem(RvOmisosVehicular omisoDetItem) {
		this.omisoDetItem = omisoDetItem;
	}
}
