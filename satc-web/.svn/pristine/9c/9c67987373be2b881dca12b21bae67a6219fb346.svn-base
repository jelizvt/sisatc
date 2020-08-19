package com.sat.sisat.caja.managed;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sat.sisat.caja.business.CajaBoRemote;
import com.sat.sisat.caja.dto.ReciboPagoDTO;
import com.sat.sisat.caja.dto.ReciboPagoDescuentoDetalleDTO;
import com.sat.sisat.caja.dto.ReciboPagoDetallePieDTO;
import com.sat.sisat.caja.dto.ReciboPagoFormaPagoDTO;
import com.sat.sisat.common.util.BaseManaged;

@ManagedBean
@ViewScoped
public class ImprimirReciboManaged extends BaseManaged{

	private static final long serialVersionUID = 1414965372988724514L;

	@EJB
	CajaBoRemote cajaBo;
	
	private ReciboPagoDTO reciboPago;
	private List<ReciboPagoDetallePieDTO> listReciboPagoDetalle = new ArrayList<ReciboPagoDetallePieDTO>();
	private List<ReciboPagoFormaPagoDTO> listReciboPagoFormaPago = new ArrayList<ReciboPagoFormaPagoDTO>();
	private List<ReciboPagoDescuentoDetalleDTO> listReciboPagoDescuentoDetalle = new ArrayList<ReciboPagoDescuentoDetalleDTO>();
	
	public ImprimirReciboManaged() {
	}

	@PostConstruct
	public void init() {
		CargarDatosReciboImprimir();
	}

	private void CargarDatosReciboImprimir(){
		try{
			Integer reciboId  = (Integer)getSessionMap().get("caja.imprimirecibo.reciboId");
			
			//reciboId  =cajaBo.temporal();

			if(reciboId == null){
//				System.out.println("No se ha podido obtener el \"id\" del recibo");
				return;
			}
		
			reciboPago = cajaBo.obtenerDatosReciboPago(reciboId);
			
			if(reciboPago==null){
				return;
			}
			
			listReciboPagoDetalle = cajaBo.searchReciboDetalle(reciboId);
			listReciboPagoFormaPago = cajaBo.getFormasPagoRecibo(reciboId);
			listReciboPagoDescuentoDetalle = cajaBo.getDetalleDescuentoRecibo(reciboId);			
			
		}catch(Exception ex){
			System.out.println("No se ha podido cargar todos los datos del recibo " + ex);
		}
	}

	public List<ReciboPagoDetallePieDTO> getListReciboPagoDetalle() {
		return listReciboPagoDetalle;
	}

	public ReciboPagoDTO getReciboPago() {
		if(this.reciboPago==null){
			this.reciboPago = new ReciboPagoDTO();
		}
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

	public List<ReciboPagoDescuentoDetalleDTO> getListReciboPagoDescuentoDetalle() {
		return listReciboPagoDescuentoDetalle;
	}

	public void setListReciboPagoDescuentoDetalle(List<ReciboPagoDescuentoDetalleDTO> listReciboPagoDescuentoDetalle) {
		this.listReciboPagoDescuentoDetalle = listReciboPagoDescuentoDetalle;
	}
}
