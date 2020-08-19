package com.sat.sisat.estadocuenta.business;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import com.sat.sisat.caja.dto.CjGenerico;
import com.sat.sisat.caja.dto.DeudaDTO;
import com.sat.sisat.estadocuenta.dto.CrConstanciaNoAdeudo;
import com.sat.sisat.estadocuenta.dto.CrConstanciaNoAdeudoDTO;
import com.sat.sisat.estadocuenta.dto.CrGeneralDTO;
import com.sat.sisat.estadocuenta.dto.CrSubConceptoDTO;
import com.sat.sisat.estadocuenta.dto.ReciboPago;
import com.sat.sisat.persistence.entity.CjReciboPago;
import com.sat.sisat.persistence.entity.GnConcepto;
import com.sat.sisat.persistence.entity.GnSubconcepto;
import com.sat.sisat.persistence.entity.RpFiscalizacionPrograma;

@Remote
public interface EstadoCuentaBoRemote {

	public abstract ArrayList<CjGenerico> obtenerDeuda( int personaId,
			String ListaConcepto, String ListaSubConcepto,String ListaPredio, String ListaVehiculo,
			String ListaPapeletas, String ListaCuota, String ListaYear,
			Timestamp FechaInteres,boolean mostrarDeuda,boolean detallado) ;
	
    public abstract ArrayList<CjGenerico> obtenerSubConceptoTodos(int personaId,int mostrarDeuda);
    
    public abstract ArrayList<CjGenerico> obtenerNroCuotasTodos(int personaId,int mostrarDeuda);
    
    public abstract ArrayList<CjGenerico> obtenerAnyoTodos(int personaId,int mostrarDeuda);
    
    public abstract ArrayList<CjGenerico> obtenerVehiculoTodos(int personaId,int mostrarDeuda);
    
    public abstract ArrayList<CjGenerico> obtenerPredioTodos(int personaId,int mostrarDeuda) ;
    
    public abstract ArrayList<CjGenerico> obtenerPapeletaResumen(int personaId,
			String nroPapeleta,int mostrarDeuda) ;
    
    public abstract ArrayList<CjGenerico> obtenerTipoUsos(int personaId,int mostrarDeuda) ;
    
    
    /**
     * ITANTAMANGO
     * @param personaId
     * @param nroRecibo
     * @param fechaCancIni
     * @param fechaCancFin
     * @return
     */
    public abstract ArrayList<CjReciboPago> obtenerRecibosPago(int personaId, String nroRecibo ,Date fechaCancIni, Date fechaCancFin)throws Exception;
 
    public int obtenerDeudas(int personaId)throws Exception;  
    
    public abstract ArrayList<ReciboPago> obtenerRecibosPagoNew(int personaId, String nroRecibo ,Date fechaCancIni, Date fechaCancFin)throws Exception;
    
    public List<CrConstanciaNoAdeudoDTO> obtenerReciboConstancia(Integer usuarioId) throws Exception ;
    
	public List<CrSubConceptoDTO> obtenerSubConcepto(Integer personaId) throws Exception ;
	
	public List<CrGeneralDTO> obtenerAnio(Integer personaId)throws Exception;
	
    public List<CrGeneralDTO> obtenerCuota(Integer personaId)throws Exception;
	
	public List<CrGeneralDTO> obtenerPropiedad(Integer personaId)throws Exception;
	
	public List<DeudaDTO> obtenerDeuda(Integer personaId,String subconceptoId,String anio,String cuota,String predio)throws Exception;
	
    public Integer actualizarConstancia(Integer personaId,String anio,String subconceptoId,String referencia,Integer usuarioId,String terminal)throws Exception;
    
    public List<CrConstanciaNoAdeudoDTO> obtenerConstancia(Integer reciboId,Integer personaId)throws Exception;
	
}
