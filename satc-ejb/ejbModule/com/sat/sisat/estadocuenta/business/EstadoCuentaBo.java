
package com.sat.sisat.estadocuenta.business;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

import com.sat.sisat.caja.dto.CjGenerico;
import com.sat.sisat.caja.dto.DeudaDTO;
import com.sat.sisat.controlycobranza.dto.MpFiscalizador;
import com.sat.sisat.estadocuenta.dao.EstadoCuentaDao;
import com.sat.sisat.estadocuenta.dto.CrConstanciaNoAdeudo;
import com.sat.sisat.estadocuenta.dto.CrConstanciaNoAdeudoDTO;
import com.sat.sisat.estadocuenta.dto.CrGeneralDTO;
import com.sat.sisat.estadocuenta.dto.CrSubConceptoDTO;
import com.sat.sisat.estadocuenta.dto.ReciboPago;
import com.sat.sisat.persistence.entity.CjReciboPago;
import com.sat.sisat.persistence.entity.GnConcepto;
import com.sat.sisat.persistence.entity.GnSubconcepto;
import com.sat.sisat.predial.business.BaseBusiness;

@Stateless
public class EstadoCuentaBo extends BaseBusiness implements EstadoCuentaBoRemote {

	private EstadoCuentaDao service;
	
	public EstadoCuentaDao getService() {
    	return this.service;
	}
    @PostConstruct
    public void initialize(){
    	this.service=new EstadoCuentaDao();
    	setDataManager(this.service);
    }
   
      
    public ArrayList<CjGenerico> obtenerDeuda(int personaId,
			String ListaConcepto,String ListaSubConcepto, String ListaPredio, String ListaVehiculo,
			String ListaPapeletas, String ListaCuota, String ListaYear,
			Timestamp FechaInteres,boolean mostrarDeuda,boolean detallado) {
    	return service.obtenerDeuda(personaId, ListaConcepto,ListaSubConcepto, ListaPredio, ListaVehiculo, ListaPapeletas, ListaCuota, ListaYear,FechaInteres,mostrarDeuda,detallado);
    }
    
    public ArrayList<CjGenerico> obtenerSubConceptoTodos(int personaId,int mostrarDeuda) {
    	return service.obtenerSubConceptoTodos(personaId, mostrarDeuda);
    }
    public ArrayList<CjGenerico> obtenerNroCuotasTodos(int personaId,int mostrarDeuda) {
    	return service.obtenerNroCuotasTodos(personaId, mostrarDeuda);
    }
    public ArrayList<CjGenerico> obtenerAnyoTodos(int personaId,int mostrarDeuda) {
    	return service.obtenerAnyoTodos(personaId, mostrarDeuda);
    }
    public ArrayList<CjGenerico> obtenerVehiculoTodos(int personaId,int mostrarDeuda) {
    	return service.obtenerVehiculoTodos(personaId, mostrarDeuda);
    }
    public ArrayList<CjGenerico> obtenerPredioTodos(int personaId,int mostrarDeuda) {
    	return service.obtenerPredioTodos(personaId, mostrarDeuda);
    }
    public ArrayList<CjGenerico> obtenerPapeletaResumen(int personaId,
			String nroPapeleta,int mostrarDeuda) {
    	return service.obtenerPapeletaResumen(personaId, nroPapeleta, mostrarDeuda);
    }
    public ArrayList<CjGenerico> obtenerTipoUsos(int personaId,int mostrarDeuda) {
    	return service.obtenerTipoUsos(personaId, mostrarDeuda);
    }
	@Override
	public ArrayList<CjReciboPago> obtenerRecibosPago(int personaId,
			String nroRecibo, Date fechaCancIni, Date fechaCancFin)throws Exception{
		return service.obtenerRecibosPago(personaId, nroRecibo, fechaCancIni, fechaCancFin);
	}
	public int obtenerDeudas(int personaId)throws Exception{
    	return service.obtenerDeudas(personaId);
    }
	@Override
	public ArrayList<ReciboPago> obtenerRecibosPagoNew(int personaId, String nroRecibo, Date fechaCancIni,
			Date fechaCancFin) throws Exception {
		return service.obtenerRecibosPagoNew(personaId, nroRecibo, fechaCancIni, fechaCancFin);
	}
	
	public List<CrConstanciaNoAdeudoDTO> obtenerReciboConstancia(Integer usuarioId) throws Exception {
		return getService().obtenerReciboConstancia(usuarioId);
	}
	
	public List<CrSubConceptoDTO> obtenerSubConcepto(Integer personaId) throws Exception {
		return getService().obtenerSubConcepto(personaId);
	}
	public List<CrGeneralDTO> obtenerAnio(Integer personaId)throws Exception{
		return getService().obtenerAnio(personaId);
	}
	
	public List<CrGeneralDTO> obtenerCuota(Integer personaId)throws Exception{
		return getService().obtenerCuota(personaId);
	}
	
	public List<CrGeneralDTO> obtenerPropiedad(Integer personaId)throws Exception{
		return getService().obtenerPropiedad(personaId);
	}
	
	public List<DeudaDTO> obtenerDeuda(Integer personaId,String subconceptoId,String anio,String cuota,String predio)throws Exception{
		return getService().obtenerDeuda(personaId,subconceptoId,anio,cuota,predio);
	}
	
	public Integer actualizarConstancia(Integer personaId,String anio,String subconceptoId,String referencia,Integer usuarioId,String terminal)throws Exception{
		return getService().actualizarConstancia(personaId,anio,subconceptoId,referencia,usuarioId,terminal);
	}
	
	public List<CrConstanciaNoAdeudoDTO> obtenerConstancia(Integer reciboId,Integer personaId)throws Exception{
		return getService().obtenerConstancia(reciboId,personaId);
	}
}
