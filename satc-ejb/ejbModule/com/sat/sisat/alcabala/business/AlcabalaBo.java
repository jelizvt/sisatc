package com.sat.sisat.alcabala.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.sat.sisat.alcabala.BuscarAlcabalaDTO;
import com.sat.sisat.alcabala.dao.AlcabalaBusinessDao;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.determinacion.vehicular.dto.DatosInafecDTO;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.persistence.entity.CdDeuda;
import com.sat.sisat.persistence.entity.CdDeudaHistorica;
import com.sat.sisat.persistence.entity.DtDeterminacion;
import com.sat.sisat.persistence.entity.GnNotaria;
import com.sat.sisat.persistence.entity.GnTipoDocumento;
import com.sat.sisat.persistence.entity.GnTipoMoneda;
import com.sat.sisat.persistence.entity.RaAlcabalaSustento;
import com.sat.sisat.persistence.entity.RaAlcabalaTasa;
import com.sat.sisat.persistence.entity.RaDireccionAlcabala;
import com.sat.sisat.persistence.entity.RaDireccionAlcabalaHistorico;
import com.sat.sisat.persistence.entity.RaDjalcabala;
import com.sat.sisat.persistence.entity.RaDjalcabalaHistorico;
import com.sat.sisat.persistence.entity.RaTipoTransferencia;
import com.sat.sisat.persistence.entity.RaTransferenteAlcabala;
import com.sat.sisat.persistence.entity.RpCondicionPropiedad;
import com.sat.sisat.persistence.entity.RpDjpredial;
import com.sat.sisat.predial.business.BaseBusiness;
import com.sat.sisat.predial.dto.UbicacionDTO;
import com.sat.sisat.vehicular.dto.AnexosDeclaVehicDTO;
import com.sat.sisat.vehicular.dto.BuscarPersonaDTO;

@Stateless
public class AlcabalaBo  extends BaseBusiness implements AlcabalaRemote {

	private static final long serialVersionUID = 1L;

	public AlcabalaBo() {
		super();
	}


	@Override
	public List<GnNotaria> getAllGnNotaria() throws Exception {
		return getService().getAllGnNotaria();
	}

	@Override
	public List<RaTipoTransferencia> getAllRaTipoTransferencia()
			throws Exception {
		return getService().getAllRaTipoTransferencia();
	}

	@Override
	public List<GnTipoMoneda> getAllGnTipoMoneda() throws Exception {
		return getService().getAllGnTipoMoneda();
	}

	@Override
	public BigDecimal getIPM(int anio, int mes) throws Exception {
		return getService().getIPM(anio, mes);
	}

	@Override
	public ArrayList<BuscarAlcabalaDTO> getAllRaDjalcabala(Integer personaId, Integer djAlcabala, Date fechaInicio, Date fechaFin)
			throws Exception {
		return getService().getAllRaDjalcabala(personaId, djAlcabala, fechaInicio, fechaFin);
	}

	@Override
	public void guardaDjAlcabala(RaDjalcabala raDjalcabala) throws Exception {
		try {
			raDjalcabala.setFechaRegistro(DateUtil.getCurrentDate());
			raDjalcabala.setTerminal(getUser().getTerminal());
			raDjalcabala.setUsuarioId(getUser().getUsuarioId());
			getService().create(raDjalcabala);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	

	@Override
	public void guardaDjAlcabalaHistorico(
			RaDjalcabalaHistorico raDjalcabalaHistorico) throws Exception {
		try {
			raDjalcabalaHistorico.setFechaRegistro(DateUtil.getCurrentDate());
			raDjalcabalaHistorico.setTerminal(getUser().getTerminal());
			raDjalcabalaHistorico.setUsuarioId(getUser().getUsuarioId());
			getService().create(raDjalcabalaHistorico);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int actualizaRaTransferente(int djaAlcabalaId)throws Exception{
		return getService().actualizaRaTransferente(djaAlcabalaId);
	}
	
	@Override
	public void guardarDocAnexos(RaAlcabalaSustento raAlcabalaSustento)
			throws Exception {
		try {
			raAlcabalaSustento.setFechaRegistro(DateUtil.getCurrentDate());
			raAlcabalaSustento.setTerminal(getUser().getTerminal());
			raAlcabalaSustento.setUsuarioId(getUser().getUsuarioId());
			getService().create(raAlcabalaSustento);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void guardarDireccionAlcabala(RaDireccionAlcabala raDireccionAlcabala)
			throws Exception {
		try {
			raDireccionAlcabala.setFechaRegistro(DateUtil.getCurrentDate());
			raDireccionAlcabala.setTerminal(getUser().getTerminal());
			raDireccionAlcabala.setUsuarioId(getUser().getUsuarioId());
			getService().create(raDireccionAlcabala);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void guardarTransferenteAlcabala(
			RaTransferenteAlcabala raTransferenteAlcabala) throws Exception {
		try {
			raTransferenteAlcabala.setFechaRegistro(DateUtil.getCurrentDate());
			raTransferenteAlcabala.setTerminal(getUser().getTerminal());
			raTransferenteAlcabala.setUsuarioId(getUser().getUsuarioId());
			getService().create(raTransferenteAlcabala);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void guardarDireccionAlcabalaHstorico(RaDireccionAlcabalaHistorico raDireccionAlcabalaHistorico) throws Exception {
		try {
			raDireccionAlcabalaHistorico.setFechaRegistro(DateUtil.getCurrentDate());
			raDireccionAlcabalaHistorico.setTerminal(getUser().getTerminal());
			raDireccionAlcabalaHistorico.setUsuarioId(getUser().getUsuarioId());
			getService().create(raDireccionAlcabalaHistorico);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public int actualizaDjAlcabala(RaDjalcabala raDjalcabala) throws Exception {
		raDjalcabala.setFechaRegistro(DateUtil.getCurrentDate());
		raDjalcabala.setTerminal(getUser().getTerminal());
		raDjalcabala.setUsuarioId(getUser().getUsuarioId());
		return getService().actualizaDjAlcabala(raDjalcabala);
		
	}

	@Override
	public ArrayList<BuscarPersonaDTO> getRaTransferenteAlcabalaByDJAlcabajaId(
			int djAlcabalaId) throws Exception {
		return getService().getRaTransferenteAlcabalaByDJAlcabajaId(djAlcabalaId);
	}

	@Override
	public ArrayList<AnexosDeclaVehicDTO> getRaAlcabalaSustentoByDjAlcabalaId(
			int djAlcabalaId) throws Exception {
		return getService().getRaAlcabalaSustentoByDjAlcabalaId(djAlcabalaId);
	}
	
	private AlcabalaBusinessDao service;
   
    public AlcabalaBusinessDao getService() {

    	return this.service;
	}
    
    @PostConstruct
    public void initialize(){
    	this.service=new AlcabalaBusinessDao();
    	setDataManager(this.service);
    }


	public RaDireccionAlcabala getRaDireccionAlcabalaByDjAlcabalaId(int djAlcabalaId, int direccionAlcaId)throws Exception{
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("p_djalcabala", djAlcabalaId);
		parameters.put("p_direccion", direccionAlcaId);
		List<Object> lRaDirec=getService().findByNamedQuery("findRaDireccionById", parameters);
		if(lRaDirec!=null&&lRaDirec.size()>0){
			return (RaDireccionAlcabala)lRaDirec.get(0);
		}
		return null;
	}


	@Override
	public int actualizaRaDireccionAlcabala(RaDireccionAlcabala raDirecAlca)throws Exception {
		return getService().actualizaRaDireccionAlcabala(raDirecAlca);
	}

	@Override
	public RaAlcabalaTasa getTasa(int alTasaId) throws Exception {
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("p_alTasaId",  alTasaId);
		List<Object> lRaTasa=getService().findByNamedQuery("findRaAlcabalaTasaById", parameters);
		if(lRaTasa!=null&&lRaTasa.size()>0){
			return (RaAlcabalaTasa)lRaTasa.get(0);
		}
		return null;
	}


	@Override
	public int actualizaAlcabalaSustento(int djAlcabalaId) throws Exception {
		return getService().actualizaAlcabalaSustento(djAlcabalaId);
	}


	@Override
	public DatosInafecDTO getInafecAlcabala(int idPersona) throws Exception {
		return getService().getInafecAlcabala(idPersona);
	}


	@Override
	public BigDecimal obtenerAutovaluoPredio(int predioId, int annoTransfer)throws Exception {
		return getService().obtenerAutovaluoPredio(predioId, annoTransfer);
	}


	@Override
	public void guardarDeterminacionALcabala(DtDeterminacion dtDeterminacion)throws Exception {
		try {
			dtDeterminacion.setFechaRegistro(DateUtil.getCurrentDate());
			dtDeterminacion.setTerminal(getUser().getTerminal());
			dtDeterminacion.setUsuarioId(getUser().getUsuarioId());
			getService().create(dtDeterminacion);	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public void guardarDeudaAlcabala(CdDeuda deuda) throws Exception {
		try {
			//cc: 
			deuda.setUsuarioId(getUser().getUsuarioId());
			deuda.setFechaRegistro(DateUtil.getCurrentDate());
			deuda.setTerminal(getUser().getTerminal());
			//cc: 
			getService().create(deuda);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public void guardarDeudaHistoricaAlcabala(CdDeudaHistorica dh) throws Exception {
		try {
			//cc: 
			dh.setUsuarioId(getUser().getUsuarioId());
			dh.setFechaRegistro(DateUtil.getCurrentDate());
			dh.setTerminal(getUser().getTerminal());
			//cc: 
			getService().create(dh);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<DtDeterminacion> getAllDtDeterminacion(Integer personaId,Integer periodo,Integer concepto,String estado)throws Exception{
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("p_persona_id", personaId);
		parameters.put("p_periodo", periodo);
		parameters.put("p_concepto_id", concepto);
		parameters.put("p_estado", estado);
		return (ArrayList<DtDeterminacion>)(List)getService().findByNamedQuery("getDtDeterminacionByPersonaId", parameters);
	}


	@Override
	public int actualizaDtDeterminacionALcabala(DtDeterminacion dtDeter)throws Exception {
		return getService().actualizaDtDeterminacionALcabala(dtDeter);
	}

	
	@Override
	public int actualizaDeudaALcabala(int deterId) throws Exception {
		return getService().actualizaDeudaAlcabala(deterId);
	}
	
	@Override
	public List<RpCondicionPropiedad> getAllRpCondicionPropiedad()
			throws Exception{
		return getService().getAllRpCondicionPropiedad();
	}	

	@Override	
	public RpDjpredial getRpDjPredial(Integer djId) throws SisatException{
		RpDjpredial rpDjPredial = new RpDjpredial();		
		try {

			Query q = em
					.createQuery("from RpDjpredial r where r.djId = :djId ");
			q.setParameter("djId", djId);			

			rpDjPredial = (RpDjpredial) q.getSingleResult();

		}catch (NoResultException e) {			
		} 		
		catch (Exception e) {
			throw new SisatException("Error en la tabla rp_djpredial. ".concat(e.getMessage()));
		}
		return rpDjPredial;		
	}
	
	@SuppressWarnings("unchecked")
	@Override	
	public List<GnTipoDocumento> obtenerTipoDocumentos() throws SisatException {
		
		List<GnTipoDocumento> result  = null;
		try{
			Query q = em.createQuery("from GnTipoDocumento"); 
			
			result = q.getResultList();
				
		}catch (Exception e) {
			throw new SisatException(e.getMessage(),e);
		}

		return result;
	} 
	
	public void eliminarDJAlcabala(BuscarAlcabalaDTO currentItem) throws SisatException{
		currentItem.setTerminal(getUser().getTerminal());		
		getService().eliminarDJAlcabala(currentItem);
	}

}
