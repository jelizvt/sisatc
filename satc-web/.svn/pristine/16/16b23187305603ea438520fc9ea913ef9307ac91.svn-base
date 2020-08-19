package com.sat.sisat.valoresyresoluciones.managed;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.valoresyresoluciones.business.ValoresyResolucionesBoRemote;
import com.sat.sisat.valoresyresoluciones.dto.BuscarActoOrdinarioDTO;
import com.sat.sisat.valoresyresoluciones.dto.DatosActoDTO;
import com.sat.sisat.valoresyresoluciones.dto.PagosActoDTO;

@ManagedBean
@ViewScoped
public class DetalleActoManaged  extends BaseManaged {

	private List<DatosActoDTO> listaDatosActo;
	
@EJB
ValoresyResolucionesBoRemote valResBo;

private String nroActo;
private String fechaEmision;
private String tipoActo;
private String fechaNotificacion;
private String situaExpediente;
private String nroResolucion;
private String fechaResolucion;


public DetalleActoManaged(){
	getSessionManaged().setLinkRegresar("/sisat/valoresyresoluciones/consultavaloresyresoluciones.xhtml");

}

	
	public String getFechaEmision() {
	return fechaEmision;
}

public void setFechaEmision(String fechaEmision) {
	this.fechaEmision = fechaEmision;
}

public String getTipoActo() {
	return tipoActo;
}

public void setTipoActo(String tipoActo) {
	this.tipoActo = tipoActo;
}

public String getFechaNotificacion() {
	return fechaNotificacion;
}

public void setFechaNotificacion(String fechaNotificacion) {
	this.fechaNotificacion = fechaNotificacion;
}

public String getSituaExpediente() {
	return situaExpediente;
}

public void setSituaExpediente(String situaExpediente) {
	this.situaExpediente = situaExpediente;
}

public String getNroResolucion() {
	return nroResolucion;
}

public void setNroResolucion(String nroResolucion) {
	this.nroResolucion = nroResolucion;
}

public String getFechaResolucion() {
	return fechaResolucion;
}

public void setFechaResolucion(String fechaResolucion) {
	this.fechaResolucion = fechaResolucion;
}


private BuscarActoOrdinarioDTO buscarActo;

private List<PagosActoDTO> listaPagos= new ArrayList<PagosActoDTO>();

public void verPagosActo(){
	try {
		if(currentItem!=null){
			listaPagos=valResBo.getPagosActo(currentItem.getDeudaId());
		}	
	} catch (Exception e) {
		e.printStackTrace();
	}
}

private DatosActoDTO currentItem = new DatosActoDTO();

	public void setProperty(BuscarActoOrdinarioDTO actoCurrentItem){
		
		int idActo=actoCurrentItem.getActoId();
		
		try {
			
			
			listaDatosActo=valResBo.getAllDatosActo(idActo);
			String nroActo=  String.valueOf(listaDatosActo.get(0).getNroActo());
			buscarActo=valResBo.getAllActosOrdinarios(getSessionManaged().getContribuyente().getPersonaId(),nroActo).get(0);
			
			setNroActo(buscarActo.getNroActo());
			
			Date fechaEmisionDate= new Date(buscarActo.getFechaEmision().getTime());
			setFechaEmision(DateUtil.convertDateToString(fechaEmisionDate));
			
			setTipoActo(String.valueOf(buscarActo.getDescTipoActo()));
			if(buscarActo.getFechaNotificacion()!=null){
			
			Date fechaNotificaDate= new Date(buscarActo.getFechaNotificacion().getTime());
				
			setFechaNotificacion(DateUtil.convertDateToString(fechaNotificaDate));	
			}else{
				setFechaNotificacion("");	
			}
			
			setSituaExpediente("");
			if(buscarActo.getNroResolucion()!=null){
			setNroResolucion(String.valueOf(buscarActo.getNroResolucion()));
			}else {
			setNroResolucion("");	
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public List<DatosActoDTO> getListaDatosActo() {
		return listaDatosActo;
	}

	public void setListaDatosActo(List<DatosActoDTO> listaDatosActo) {
		this.listaDatosActo = listaDatosActo;
	}

	public String getNroActo() {
		return nroActo;
	}

	public void setNroActo(String nroActo) {
		this.nroActo = nroActo;
	}

	public BuscarActoOrdinarioDTO getBuscarActo() {
		return buscarActo;
	}

	public void setBuscarActo(BuscarActoOrdinarioDTO buscarActo) {
		this.buscarActo = buscarActo;
	}


	public DatosActoDTO getCurrentItem() {
		return currentItem;
	}


	public void setCurrentItem(DatosActoDTO currentItem) {
		this.currentItem = currentItem;
	}


	public List<PagosActoDTO> getListaPagos() {
		return listaPagos;
	}


	public void setListaPagos(List<PagosActoDTO> listaPagos) {
		this.listaPagos = listaPagos;
	}


}
