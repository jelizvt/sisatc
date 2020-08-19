package com.sat.sisat.caja.managed;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sat.sisat.caja.business.CajaBoRemote;
import com.sat.sisat.caja.dto.CjReciboPagoEntity;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.exception.SisatException;

@ManagedBean
@ViewScoped
public class DuplicadoRecibosManaged extends BaseManaged {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7745707655443917738L;

	@EJB
	CajaBoRemote cajaBo;
	
	private String referencia;
	
	private List<CjReciboPagoEntity> listCjReciboPago;
	
	
	
	public void buscar(){
		
		try {
			listCjReciboPago = cajaBo.busquedaRecibosPorReferencia(referencia);
		} catch (SisatException e) {
			WebMessages.messageError(e.getMessage());
		}
		
	}
	
	public void verRecibo(Integer reciboId){
		/** Direccionando segun el tipo de recibo a ser impreso */ 	
		getSessionManaged().getSessionMap().put("caja.imprimirecibo.reciboId", reciboId);		
	}
	
	
	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	
	public List<CjReciboPagoEntity> getListCjReciboPago() {
		return listCjReciboPago;
	}


	public void setListCjReciboPago(List<CjReciboPagoEntity> listCjReciboPago) {
		this.listCjReciboPago = listCjReciboPago;
	}
	
}
