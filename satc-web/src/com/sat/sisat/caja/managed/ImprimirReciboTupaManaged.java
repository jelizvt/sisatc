package com.sat.sisat.caja.managed;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sat.sisat.caja.business.CajaBoRemote;
import com.sat.sisat.caja.dto.ReciboPagoDTO;
import com.sat.sisat.caja.dto.ReciboPagoDetalleDTO;
import com.sat.sisat.caja.dto.ReciboPagoFormaPagoDTO;
import com.sat.sisat.common.util.BaseManaged;

@ManagedBean
@ViewScoped
public class ImprimirReciboTupaManaged extends BaseManaged{

	private static final long serialVersionUID = -4778584220621576873L;

	@EJB
	CajaBoRemote cajaBo;
	
	private ReciboPagoDTO reciboPago;
	
	private List<ReciboPagoDetalleDTO> listReciboPagoDetalle = new ArrayList<ReciboPagoDetalleDTO>();
	private List<ReciboPagoFormaPagoDTO> listReciboPagoFormaPago = new ArrayList<ReciboPagoFormaPagoDTO>();
	
	public ImprimirReciboTupaManaged() {
	}

	@PostConstruct
	public void init() {
		cargarDatosReciboImprimir();
	}

	private void cargarDatosReciboImprimir(){
		try{
			Integer reciboId  = (Integer)getSessionMap().get("caja.imprimirecibo.reciboId");
			if(reciboId == null){
//				System.out.println("No se ha podido obtener el \"id\" del recibo");
				return;
			}
			reciboPago = cajaBo.obtenerDatosReciboPagoTupa(reciboId);
			if(reciboPago==null){
				return;
			}
			listReciboPagoFormaPago = cajaBo.getFormasPagoRecibo(reciboId);
			listReciboPagoDetalle = cajaBo.searchReciboDetalleTupa(reciboId);
		}catch(Exception ex){
			System.out.println("No se ha podido cargar todos los datos del recibo " + ex);
		}
	}

	public ReciboPagoDTO getReciboPago() {
		return reciboPago;
	}

	public void setReciboPago(ReciboPagoDTO reciboPago) {
		this.reciboPago = reciboPago;
	}

	public List<ReciboPagoFormaPagoDTO> getListReciboPagoFormaPago() {
		return listReciboPagoFormaPago;
	}

	public void setListReciboPagoFormaPago(List<ReciboPagoFormaPagoDTO> listReciboPagoFormaPago) {
		this.listReciboPagoFormaPago = listReciboPagoFormaPago;
	}

	public List<ReciboPagoDetalleDTO> getListReciboPagoDetalle() {
		return listReciboPagoDetalle;
	}

	public void setListReciboPagoDetalle(List<ReciboPagoDetalleDTO> listReciboPagoDetalle) {
		this.listReciboPagoDetalle = listReciboPagoDetalle;
	}	
}

	


