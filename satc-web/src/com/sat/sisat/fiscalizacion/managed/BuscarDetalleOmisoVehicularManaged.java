package com.sat.sisat.fiscalizacion.managed;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.fiscalizacion.business.FiscalizacionBoRemote;
import com.sat.sisat.fiscalizacion.dto.FindInspeccionHistorial;
import com.sat.sisat.persistence.entity.RvOmisosDetalleVehicular;

@ManagedBean
@ViewScoped
public class BuscarDetalleOmisoVehicularManaged extends BaseManaged {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2445862386275865019L;	
	@EJB
	FiscalizacionBoRemote ficalizacionBo;
	
	private FindInspeccionHistorial detalleInspeccion = new FindInspeccionHistorial();
	
	private List<RvOmisosDetalleVehicular> lstDetalleOmisoVehicular = new ArrayList<RvOmisosDetalleVehicular>();
	
	public BuscarDetalleOmisoVehicularManaged() throws Exception {		   
	}
	
	
	
	@PostConstruct
	public void init() throws Exception {		
		try {
			FindInspeccionHistorial detalleInspeccion = (FindInspeccionHistorial) getSessionMap().get(
					"FindInspecHistorial");
			if (detalleInspeccion != null) {							
				setDetalleInspeccion(detalleInspeccion);				
				lstDetalleOmisoVehicular = ficalizacionBo.getAllDetalleOmisosVehicular(detalleInspeccion.getInspeccionId());
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	

	public List<RvOmisosDetalleVehicular> getLstDetalleOmisoVehicular() {
		return lstDetalleOmisoVehicular;
	}



	public void setLstDetalleOmisoVehicular(
			List<RvOmisosDetalleVehicular> lstDetalleOmisoVehicular) {
		this.lstDetalleOmisoVehicular = lstDetalleOmisoVehicular;
	}



	public FindInspeccionHistorial getDetalleInspeccion() {
		return detalleInspeccion;
	}



	public void setDetalleInspeccion(FindInspeccionHistorial detalleInspeccion) {
		this.detalleInspeccion = detalleInspeccion;
	}	


}
