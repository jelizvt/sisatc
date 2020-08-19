/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sat.sisat.predial.business;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

import com.sat.sisat.administracion.dto.CampoDTO;
import com.sat.sisat.common.dto.TipoUsoDTO;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.Util;
import com.sat.sisat.determinacion.common.dao.DeterminacionDao;
import com.sat.sisat.determinacion.vehicular.dto.DeudaPagosDTO;
import com.sat.sisat.determinacion.vehicular.dto.DeudaValoresDTO;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.imputacion.dao.ImputacionDao;
import com.sat.sisat.persistence.entity.CdDeuda;
import com.sat.sisat.persistence.entity.CdDeudaHistorica;
import com.sat.sisat.persistence.entity.CdDeudaHistoricaPK;
import com.sat.sisat.persistence.entity.DtCercaniaParque;
import com.sat.sisat.persistence.entity.DtCondiEspecialContri;
import com.sat.sisat.persistence.entity.DtCuotaConcepto;
import com.sat.sisat.persistence.entity.DtDeterminacion;
import com.sat.sisat.persistence.entity.DtDeterminacionArbitrio;
import com.sat.sisat.persistence.entity.DtDeterminacionConstruccion;
import com.sat.sisat.persistence.entity.DtDeterminacionInstalacion;
import com.sat.sisat.persistence.entity.DtDeterminacionPredio;
import com.sat.sisat.persistence.entity.DtDeterminacionRecojo;
import com.sat.sisat.persistence.entity.DtDeterminacionSeguridad;
import com.sat.sisat.persistence.entity.DtDeterminacionValor;
import com.sat.sisat.persistence.entity.DtFactorOfic;
import com.sat.sisat.persistence.entity.DtFechaVencimiento;
import com.sat.sisat.persistence.entity.DtFrecuenciaLimpieza;
import com.sat.sisat.persistence.entity.DtFrecuenciaRecojo;
import com.sat.sisat.persistence.entity.DtGrupoCercania;
import com.sat.sisat.persistence.entity.DtMatrizRecojo;
import com.sat.sisat.persistence.entity.DtMatrizSeguridad;
import com.sat.sisat.persistence.entity.DtTasaImpuestoPredial;
import com.sat.sisat.persistence.entity.DtZonaSeguridad;
import com.sat.sisat.persistence.entity.DtZonaSeguridadUbicacion;
import com.sat.sisat.persistence.entity.DtZonaSeguridadUso;
import com.sat.sisat.persistence.entity.DtZonaSeguridadUso2013;
import com.sat.sisat.persistence.entity.DtZonaSeguridadUso2014;
import com.sat.sisat.persistence.entity.DtZonaSeguridadUso2015;
import com.sat.sisat.persistence.entity.GnCondicionEspecial;
import com.sat.sisat.persistence.entity.MpPersona;
import com.sat.sisat.persistence.entity.RpCategoriaUso;
import com.sat.sisat.persistence.entity.RpCategoriaUsoSeguridad;
import com.sat.sisat.persistence.entity.RpDjarbitrio;
import com.sat.sisat.persistence.entity.RpDjconstruccion;
import com.sat.sisat.persistence.entity.RpDjdireccion;
import com.sat.sisat.persistence.entity.RpDjpredial;
import com.sat.sisat.persistence.entity.RpDjuso;
import com.sat.sisat.persistence.entity.RpInstalacionDj;
import com.sat.sisat.persistence.entity.RpOtrosFrente;
import com.sat.sisat.persistence.entity.RpTipoUso;
import com.sat.sisat.predial.dao.CalculoBusinessDao;
import com.sat.sisat.predial.dto.DatosDeterminacionPredialValoresDTO;
import com.sat.sisat.predial.dto.DeterminacionArbitriosDTO;
import com.sat.sisat.predial.dto.DeudaPagosPredialDTO;
import com.sat.sisat.predial.dto.DtDeterminacionConstruccionDTO;
import com.sat.sisat.predial.dto.DtDeterminacionDTO;
import com.sat.sisat.predial.dto.DtDeterminacionInstalacionDTO;
import com.sat.sisat.predial.dto.DtDeterminacionPeriodoDTO;
import com.sat.sisat.predial.dto.DtDeterminacionPredioDTO;
import com.sat.sisat.predial.dto.DtDeterminacionResArbDTO;
import com.sat.sisat.predial.dto.RecojoArbitriosDTO;
import com.sat.sisat.predial.dto.SeguridadArbitriosDTO;

/**
 *
 * @author cchaucca
 */
@Stateless
public class CalculoPredialBo extends BaseBusiness implements CalculoPredialBoRemote{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9140827291248834973L;
	private CalculoBusinessDao service;
	private DeterminacionDao determinacionDao;
	private ImputacionDao imputacionDao;
	
	public CalculoPredialBo(){
    	
    }
    
    public CalculoBusinessDao getService() {
    	return this.service;
	}

    public DeterminacionDao getDeterminacionDao() {
    	return this.determinacionDao;
	}
    
    public ImputacionDao getInputacionDao() {
    	return this.imputacionDao;
	}
    
    @PostConstruct
    public void initialize(){
    	this.service=new CalculoBusinessDao();
    	this.determinacionDao=new DeterminacionDao();
    	this.imputacionDao=new ImputacionDao();
    	
    	setDataManager(this.service);
    	setDataManager(this.determinacionDao);
    	setDataManager(this.imputacionDao);
    }
    
    public MpPersona getFindPersona(Integer ContribuyenteId)throws Exception{
    	return (MpPersona)getService().find(ContribuyenteId, MpPersona.class);
    }
    
	public Integer guardarDeterminacion(DtDeterminacion determinacion)throws Exception{
		Integer result=Constante.RESULT_PENDING;
		if(determinacion.getDeterminacionId()==Constante.RESULT_PENDING){
			Integer Id=getService().ObtenerCorrelativoTabla("dt_determinacion", 1);
			determinacion.setDeterminacionId(Id);
			//cc :
			determinacion.setUsuarioId(getUser().getUsuarioId());
			determinacion.setFechaRegistro(DateUtil.getCurrentDate());
			determinacion.setTerminal(getUser().getTerminal());
			//cc :
			getService().create(determinacion);
			result=Id;
		
		}else{
			determinacion.setUsuarioId(getUser().getUsuarioId());
			determinacion.setFechaRegistro(DateUtil.getCurrentDate());
			determinacion.setTerminal(getUser().getTerminal());
			determinacion.setFechaActualizacion(DateUtil.getCurrentDate());
			getService().update(determinacion);
			result=determinacion.getDeterminacionId();
		}
		return result;
	}
	
	public ArrayList<RpDjpredial> getAllRpDJpredial(Integer personaId,Integer periodo)throws Exception{
		/*Reemplazado por Procedure
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("p_persona_id", personaId);
		parameters.put("p_flag_dj_anno", "1");
		parameters.put("p_periodo", periodo);
		return (ArrayList<RpDjpredial>)(List)getService().findByNamedQuery("getAllRpDJpredialByPersonaId", parameters);
		*/		
		return getService().determinacionPredialGet_dj(personaId, periodo,"1");		
	}
	
	
	public ArrayList<Integer> getAllRpDJpredialId(Integer periodo,Integer personaInicioDjId,Integer personaFinDjId)throws Exception{
		return getService().getAllRpDJpredialId(periodo,personaInicioDjId,personaFinDjId);
	}
	
	public RpDjpredial getRpDJpredial(Integer djId)throws Exception{
		return getService().find(djId, RpDjpredial.class);
	}
	
	public ArrayList<RpDjpredial> getAllRpDJpredialArb(Integer personaId,Integer periodo)throws Exception{
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("p_persona_id", personaId);
		parameters.put("p_flag_dj_anno", "1");
		parameters.put("p_periodo", periodo);
		return (ArrayList<RpDjpredial>)(List)getService().findByNamedQuery("getAllRpDJpredialArbByPersonaId", parameters);
	}
	
	
	
	public Integer guardarDeterminacionPredial(DtDeterminacionPredio deterPredial)throws Exception{
		Integer result=Constante.RESULT_PENDING;
		if(deterPredial.getDeterPredioId()==Constante.RESULT_PENDING){
			Integer Id=getService().ObtenerCorrelativoTabla("dt_determinacion_predio", 1);
			
			deterPredial.setDeterPredioId(Id);
			deterPredial.setFechaRegistro(DateUtil.getCurrentDate());
			deterPredial.setTerminal(getUser().getTerminal());
			deterPredial.setUsuarioId(getUser().getUsuarioId());
			getService().create(deterPredial);
			result=Id;
		}else{
			deterPredial.setFechaRegistro(DateUtil.getCurrentDate());
			deterPredial.setTerminal(getUser().getTerminal());
			deterPredial.setUsuarioId(getUser().getUsuarioId());
			getService().update(deterPredial);
			result=deterPredial.getDeterPredioId();
		}
		return result;
	}
	public ArrayList<DtDeterminacionPredio> getAllDtDeterminacionPredio(Integer determinacionId)throws Exception{
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("p_determinacion_id", determinacionId);
		return (ArrayList<DtDeterminacionPredio>)(List)getService().findByNamedQuery("getAllDtDeterminacionPredioByDeterminacionId", parameters);
	}
	
	public ArrayList<DtTasaImpuestoPredial> getAllDeTasaImpuestoPredial(Integer periodo)throws Exception{
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("p_periodo", periodo);
		return (ArrayList<DtTasaImpuestoPredial>)(List)getService().findByNamedQuery("getAllDtTasaImpuestoPredialByPeriodo", parameters);
	}
	
	public List<RpInstalacionDj> getOtrasInstalaciones(RpDjpredial rpDjPredial,Integer periodo)throws Exception{
		return getService().getOtrasInstalaciones(rpDjPredial, periodo);
	}
	
	public List<RpInstalacionDj> getOtrasInstalacionesConAnioFiscal(RpDjpredial rpDjPredial,Integer periodo)throws Exception{
		return getService().getOtrasInstalacionesConAnioFiscal(rpDjPredial, periodo);
	}
	
	public Double getValorArancelRustico(Integer periodo,Integer tipoTierraId,Integer categoriaRusticoId,Integer altitudId)throws Exception{
		return getService().getValorArancelRustico(periodo, tipoTierraId, categoriaRusticoId, altitudId);
	}
	public RpDjdireccion getRpDjDireccion(Integer DjId)throws Exception{
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("p_dj_id", DjId);
		List<Object> lDireccion=getService().findByNamedQuery("getAllRpDjdireccionByDjId", parameters);
		if(lDireccion!=null&&lDireccion.size()>0){
			return (RpDjdireccion)lDireccion.get(0);
		}
		return null;
	}
	
	public Double getValorArancelUrbano(Integer periodo,RpDjdireccion direccion)throws Exception{
		return getService().getValorArancelUrbano(periodo, direccion);
	}
	
	public List<RpDjconstruccion> getConstruccion(RpDjpredial rpDjPredial,Integer periodo)throws Exception{
		return getService().getConstruccion(rpDjPredial,periodo);
	}
	public List<RpDjconstruccion> getConstruccionConAnioFiscal(RpDjpredial rpDjPredial,Integer periodo)throws Exception{
		return getService().getConstruccionConAnioFiscal(rpDjPredial,periodo);
	}
	public Integer guardarDeterminacionConstruccion(DtDeterminacionConstruccion determinacionNivel)throws Exception{
		Integer result=Constante.RESULT_PENDING;
		if(determinacionNivel.getDeterminacionConstruccionId()==Constante.RESULT_PENDING){
			Integer Id=getService().ObtenerCorrelativoTabla("dt_determinacion_construccion", 1);
			
			determinacionNivel.setDeterminacionConstruccionId(Id);
			determinacionNivel.setFechaRegistro(DateUtil.getCurrentDate());
			determinacionNivel.setTerminal(getUser().getTerminal());
			determinacionNivel.setUsuarioId(getUser().getUsuarioId());
			getService().create(determinacionNivel);
			result=Constante.RESULT_SUCCESS;
		}else{
			getService().update(determinacionNivel);
			result=Constante.RESULT_SUCCESS;
		}
		return result;
	}
	public double obtenerValorUnitarioComponente(int periodoId,int tipoComponenteId,int categoriaComponenteId)throws Exception{
		return getService().obtenerValorUnitarioComponente(periodoId, tipoComponenteId, categoriaComponenteId);
	}
	public double obtenerPorcentajeDepreciacion(int tipo_depreciacion_id,int anno_determinacion,int tipo_material_id,int conservacion_id,int antiguedad)throws Exception{
		return getService().obtenerPorcentajeDepreciacion(tipo_depreciacion_id, anno_determinacion, tipo_material_id, conservacion_id,antiguedad);
	}

	public List<DtFechaVencimiento> getFechaVencimiento(Integer subConceptoId,Integer periodo)throws Exception{
		return getDeterminacionDao().getFechaVencimiento(subConceptoId, periodo);
	}
	public DtCuotaConcepto getCuotasConcepto(Integer subConcepto, Integer periodo)throws Exception{
		return getDeterminacionDao().getCuotasConcepto(subConcepto, periodo);
	}
	public Integer guardarDeuda(CdDeuda deuda)throws Exception{
		Integer result=deuda.getDeudaId();
		
		if(deuda.getDeudaId()==Constante.RESULT_PENDING){
			Integer Id=getService().ObtenerCorrelativoTabla("cd_deuda", 1);
			deuda.setDeudaId(Id);
			deuda.setUsuarioId(getUser().getUsuarioId());
			deuda.setFechaRegistro(DateUtil.getCurrentDate());
			deuda.setTerminal(getUser().getTerminal());
			getService().create(deuda);
			result=Id;
		}else{
			getService().update(deuda);
		}
		
		return result;
	}
	public Integer guardarDeudaHistorica(CdDeudaHistorica deudaHistorica)throws Exception{
		Integer result=Constante.RESULT_PENDING;
		Integer Id=getService().ObtenerCorrelativoTabla("cd_deuda_historica", 1);
		deudaHistorica.getId().setHistoricaId(Id);
		deudaHistorica.setUsuarioId(getUser().getUsuarioId());
		deudaHistorica.setFechaRegistro(DateUtil.getCurrentDate());
		deudaHistorica.setTerminal(getUser().getTerminal());
		getService().create(deudaHistorica);
		return result;
	}
	
	public DtFactorOfic getFactorOficializacion(Integer periodo)throws Exception{
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("p_periodo", periodo);
		List<Object> lista=getService().findByNamedQuery("getDtFactorOficByPeriodo", parameters);
		if(lista!=null&&lista.size()>0){
			return (DtFactorOfic)lista.get(0);
		}
		return null; 
	}

	public GnCondicionEspecial getGnCondicionEspecialContribuyente(Integer personaId,Integer periodo)throws Exception{
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("p_persona_id", personaId);
		parameters.put("p_periodo", periodo);
		List<Object> lista=getService().findByNamedQuery("getGnCondicionEspecialByPersonaId", parameters);
		if(lista!=null&&lista.size()>0){
			return (GnCondicionEspecial)lista.get(0);
		}
		return null; 
	}
	
	public List<RpDjuso> getRpDjusoByDjId(Integer djId) throws Exception{
		return getService().getRpDjusoByDjId(djId);
	}
	
	public DtCondiEspecialContri getDtCondiEspecialContriByPeriodo(Integer periodo,Integer tipoCondEspecialId,Integer usoPredioId)throws Exception{
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("p_periodo", periodo);
		parameters.put("p_concepto_id", Constante.CONCEPTO_PREDIAL);
		parameters.put("p_subconcepto_id", Constante.SUB_CONCEPTO_PREDIAL);
		parameters.put("p_tipo_cond_especial_id", tipoCondEspecialId);
		parameters.put("p_uso_predio_id", usoPredioId);
		List<Object> lista=getService().findByNamedQuery("getDtCondiEspecialContriByPeriodo", parameters);
		if(lista!=null&&lista.size()>0){
			return (DtCondiEspecialContri)lista.get(0);
		}
		return null; 
	}
	
	//--ini
	/*
	public DtDeterminacion getDtDeterminacion(Integer personaId,Integer periodo,Integer concepto,Integer djId,Boolean esFiscalizado)throws Exception{
		Integer determinacionId=getService().getDtDeterminacionByDj(personaId,periodo,concepto,djId,esFiscalizado);
		if(determinacionId!=null&&determinacionId>Constante.RESULT_PENDING){
			return getService().find(determinacionId, DtDeterminacion.class);
		}
		return null;
	}
	*/
	//getDtDeterminacionAnteriorByPersonaId
	
	public DtDeterminacion getDtDeterminacion(Integer personaId,Integer periodo,Integer concepto,String estado)throws Exception{
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("p_persona_id", personaId);
		parameters.put("p_periodo", periodo);
		parameters.put("p_concepto_id", concepto);
		parameters.put("p_estado", estado);
		
		List<Object> lista=getService().findByNamedQuery("getDtDeterminacionByPersonaId", parameters);
		if(lista!=null&&lista.size()>0){
			return (DtDeterminacion)lista.get(0);
		}
		return null; 
	}
	
	public DtDeterminacion getDtDeterminacionAnt(Integer personaId,Integer periodo,Integer concepto,String estado,Integer determinacionId)throws Exception{
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("p_persona_id", personaId);
		parameters.put("p_periodo", periodo);
		parameters.put("p_concepto_id", concepto);
		parameters.put("p_estado", estado);
		parameters.put("p_determinacion_id", determinacionId);
		List<Object> lista=getService().findByNamedQuery("getDtDeterminacionAntByPersonaId", parameters);
		if(lista!=null&&lista.size()>0){
			return (DtDeterminacion)lista.get(0);
		}
		return null; 
	}
	
	public void inactivaDeterminacionAnterior(Integer personaId,Integer periodo)throws Exception{
		getService().inactivaDeterminacionAnterior(personaId,periodo);
	}
	
	public DtDeterminacion getAllDtDeterminacionArbitrio(Integer personaId,Integer DjId,Integer periodo,Integer concepto,String estado)throws Exception{
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("p_persona_id", personaId);
		parameters.put("p_periodo", periodo);
		parameters.put("p_concepto_id", concepto);
		parameters.put("p_estado", estado);
		parameters.put("p_djid", DjId);//p_djid
		List<Object> lista=getService().findByNamedQuery("getDtDeterminacionArbitrioByPersonaId", parameters);
		if(lista!=null&&lista.size()>0){
			return (DtDeterminacion)lista.get(0);
		}
		return null; 
	}
	
	public DtDeterminacion getDtDeterminacionById(Integer determinacionId)throws Exception{
		return getService().find(determinacionId, DtDeterminacion.class); 
	}
	
	public DtDeterminacion getDtDeterminacion(Integer papeletaId,Integer concepto)throws Exception{
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("p_djreferencia_id", papeletaId);
		parameters.put("p_concepto_id", concepto);
		List<Object> lista=getService().findByNamedQuery("getDtDeterminacionByDjReferenciaId", parameters);
		if(lista!=null&&lista.size()>0){
			return (DtDeterminacion)lista.get(0);
		}
		return null; 
	}
	
	public DtDeterminacion getDtDeterminacionPapeleta(Integer papeletaId,Integer concepto)throws Exception{
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("p_djreferencia_id", papeletaId);
		parameters.put("p_concepto_id", concepto);
		List<Object> lista=getService().findByNamedQuery("getDtDeterminacionByPapeletaId", parameters);
		if(lista!=null&&lista.size()>0){
			return (DtDeterminacion)lista.get(0);
		}
		return null; 
	}
	
	//--fin
	
	public ArrayList<DtDeterminacion> getAllDtDeterminacion(Integer personaId,Integer periodo,Integer concepto,String estado)throws Exception{
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("p_persona_id", personaId);
		parameters.put("p_periodo", periodo);
		parameters.put("p_concepto_id", concepto);
		parameters.put("p_estado", estado);
		return (ArrayList<DtDeterminacion>)(List)getService().findByNamedQuery("getDtDeterminacionByPersonaId", parameters);
	}
	
	public ArrayList<DtDeterminacion> getAllDtDeterminacionByPeriodo(Integer personaId,Integer periodo,Integer concepto)throws Exception{
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("p_persona_id", personaId);
		parameters.put("p_periodo", periodo);
		parameters.put("p_concepto_id", concepto);
		//getService().getCantTipoPredioPorDeterminacion(determinaId, tipoPredio)
		List<DtDeterminacion> list =(List)getService().findByNamedQuery("getAllDtDeterminacionByPeriodo", parameters);
		return (ArrayList<DtDeterminacion>) getService().checkDtDeterminacion(list);
	}
	
	public ArrayList<DtDeterminacion> getAllDtDeterminacion(Integer personaId,Integer periodo,Integer djId,Integer concepto)throws Exception{
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("p_djreferencia_id", djId);
		parameters.put("p_concepto_id", concepto);
		parameters.put("p_periodo", periodo);
		parameters.put("p_persona_id", personaId);
		return (ArrayList<DtDeterminacion>)(List)getService().findByNamedQuery("getDtDeterminacionByDjReferenciaId", parameters);
	}
	
	public ArrayList<DtDeterminacion> getAllDtDeterminacionByPersonaId(Integer personaId,Integer conceptoId)throws Exception{
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("p_persona_id", personaId);
		parameters.put("p_concepto_id", conceptoId);
		return (ArrayList<DtDeterminacion>)(List)getService().findByNamedQuery("getAllDtDeterminacionByPersonaId", parameters);
	}
	
	public List<Integer> getAniosDeterminacion(Integer personaId)throws Exception{
		return getService().getAniosDeterminacion(personaId);
	}
			
	public int actualizaEstadoCdDeuda(Integer determinacionId,String estado)throws Exception{
		return getService().actualizaEstadoCdDeuda(determinacionId, estado);
	}
	
	public List<CdDeuda> recuperarFlagCdDeuda(Integer determinacionId,String estado)throws Exception{
		return getService().recuperarFlagCdDeuda(determinacionId, estado);
	}
	
	public int actualizaEstadoDtDeterminacionPredio(Integer determinacionId,String estado)throws Exception{
		return getService().actualizaEstadoDtDeterminacionPredio(determinacionId, estado);
	}
	
	public int actualizaEstadoDtDeterminacionConstruccion(Integer determinacionId,String estado)throws Exception{
		return getService().actualizaEstadoDtDeterminacionConstruccion(determinacionId, estado);
	}
	public int actualizaEstadoDtDeterminacionArbitrios(Integer djId,String estado)throws Exception{
		return getService().actualizaEstadoDtDeterminacionArbitrios(djId, estado);
	}
	
	public ArrayList<DtDeterminacionPredioDTO> getAllDtDeterminacionPredioDj(Integer determinacionId)throws Exception{
		return getService().getAllDtDeterminacionPredioDj(determinacionId);
	}
	
	public Integer guardarDeterminacionInstalacion(DtDeterminacionInstalacion deterIntalacion)throws Exception{
		Integer result=Constante.RESULT_PENDING;
		if(deterIntalacion.getDeterminacionInstalacionId()==Constante.RESULT_PENDING){
			Integer Id=getService().ObtenerCorrelativoTabla("dt_determinacion_instalacion", 1);
			
			deterIntalacion.setDeterminacionInstalacionId(Id);
			
			deterIntalacion.setTerminal(getUser().getTerminal());
			deterIntalacion.setUsuarioId(getUser().getUsuarioId());
			deterIntalacion.setFechaRegistro(DateUtil.getCurrentDate());
			
			getService().create(deterIntalacion);
			result=Constante.RESULT_SUCCESS;
		}else{
			getService().update(deterIntalacion);
			result=Constante.RESULT_SUCCESS;
		}
		return result;
	}
	public ArrayList<DtDeterminacionConstruccionDTO> getAllDtDeterminacionConstruccion(Integer deterPredioId,Integer periodo)throws Exception{
		return getService().getAllDtDeterminacionConstruccion(deterPredioId,periodo);
	}
	
	public ArrayList<DtDeterminacionInstalacionDTO> getAllDtDeterminacionInstalacion(Integer deterPredioId,Integer periodo)throws Exception{
		return getService().getAllDtDeterminacionInstalacion(deterPredioId,periodo);
	}
	public DtFrecuenciaLimpieza getDtFrecuenciaLimpieza(Integer ubicacionId,Integer periodo)throws Exception{
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("p_ubicacion_id", ubicacionId);
		parameters.put("p_periodo", periodo);
		List<Object> lista=getService().findByNamedQuery("getDtFrecuenciaLimpiezaByUbicacionId", parameters);
		if(lista!=null&&lista.size()>0){
			return (DtFrecuenciaLimpieza)lista.get(0);
		}
		return null; 		
	}
	
	public DtFrecuenciaRecojo getDtFrecuenciaRecojo(Integer ubicacionId,Integer periodo)throws Exception{
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("p_ubicacion_id", ubicacionId);
		parameters.put("p_periodo", periodo);
		List<Object> lista=getService().findByNamedQuery("getDtFrecuenciaRecojoByUbicacionId", parameters);
		if(lista!=null&&lista.size()>0){
			return (DtFrecuenciaRecojo)lista.get(0);
		}
		return null;
	}
	public List<DtMatrizRecojo> getAllDtMatrizRecojo(Integer frecuencia,Integer periodo)throws Exception{
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("p_frecuencia", frecuencia);
		parameters.put("p_periodo", periodo);
		return (ArrayList<DtMatrizRecojo>)(List)getService().findByNamedQuery("getDtMatrizRecojoByFrecuencia", parameters);
	}
	
	public List<DtMatrizRecojo> getAllDtMatrizRecojo(Integer periodo)throws Exception{
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("p_periodo", periodo);
		return (ArrayList<DtMatrizRecojo>)(List)getService().findByNamedQuery("getDtMatrizRecojoByPeriodo", parameters);
	}
	
	public RpDjarbitrio getRpDjarbitrio(Integer djId)throws Exception{
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("p_dj_id", djId);
		List<Object> lista=getService().findByNamedQuery("getRpDjarbitrioByDjId", parameters);
		if(lista!=null&&lista.size()>0){
			return (RpDjarbitrio)lista.get(0);
		}
		return null; 		
	}
	
	public List<RpDjuso> getAllRpDjuso(Integer djArbitrioId)throws Exception{
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("p_arbitrio_id", djArbitrioId);
		return (ArrayList<RpDjuso>)(List)getService().findByNamedQuery("getAllRpDjusoByArbitrioId", parameters);
	}
	
	public DtCercaniaParque getDtCercaniaParque(Integer ubicacionId,Integer periodo)throws Exception{
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("p_ubicacion_id", ubicacionId);
		parameters.put("p_periodo", periodo);
		List<Object> lista=getService().findByNamedQuery("getDtCercaniaParqueByUbicacionId", parameters);
		if(lista!=null&&lista.size()>0){
			return (DtCercaniaParque)lista.get(0);
		}
		return null;
	}
	
	public DtGrupoCercania getDtGrupoCercania(Integer grupoCercaniaId)throws Exception{
		return getService().find(grupoCercaniaId, DtGrupoCercania.class);
	}
	
	public DtZonaSeguridadUbicacion getDtZonaSeguridadUbicacion(Integer ubicacionId,Integer periodo)throws Exception{
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("p_ubicacion_id", ubicacionId);
		parameters.put("p_periodo", periodo);
		List<Object> lista=getService().findByNamedQuery("getDtZonaSeguridadByUbicacionId", parameters);
		if(lista!=null&&lista.size()>0){
			return (DtZonaSeguridadUbicacion)lista.get(0);
		}
		return null;
	}
	
	public List<DtZonaSeguridadUso> getAllDtZonaSeguridadUso(Integer ubicacionId,Integer periodo)throws Exception{
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("p_zona_seguridad_id", ubicacionId);
		parameters.put("p_periodo", periodo);
		return (ArrayList<DtZonaSeguridadUso>)(List)getService().findByNamedQuery("getAllDtZonaSeguridadUsoByPeriodo", parameters);
	}

	public List<DtZonaSeguridadUso2013> getAllDtZonaSeguridadUso2013(Integer ubicacionId,Integer periodo)throws Exception{
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("p_zona_seguridad_id", ubicacionId);
		parameters.put("p_periodo", periodo);
		return (ArrayList<DtZonaSeguridadUso2013>)(List)getService().findByNamedQuery("getAllDtZonaSeguridadUso2013ByPeriodo", parameters);
	}
	
	public List<RpCategoriaUso> getAllRpCategoriaUso(Integer periodo)throws Exception{
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("p_periodo", periodo);
		return (ArrayList<RpCategoriaUso>)(List)getService().findByNamedQuery("getAllRpCategoriaUsoByPeriodo", parameters);
	}
	
	public List<RpCategoriaUsoSeguridad> getAllRpCategoriaUsoSeguridad(Integer periodo)throws Exception{
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("p_periodo", periodo);
		return (ArrayList<RpCategoriaUsoSeguridad>)(List)getService().findByNamedQuery("getAllRpCategoriaUsoSeguridadByPeriodo", parameters);
	}
	
	public List<RpTipoUso> getAllRpTipoUso(Integer periodo)throws Exception{
		Map<String, Object> parameters=new HashMap<String, Object>();
		//parameters.put("p_periodo", periodo);
		return (ArrayList<RpTipoUso>)(List)getService().findByNamedQuery("getAllRpTipoUsoByPeriodo", parameters);
	}

	public List<DtZonaSeguridad> getAllDtZonaSeguridad(Integer periodo)throws Exception{
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("p_periodo", periodo);
		return (ArrayList<DtZonaSeguridad>)(List)getService().findByNamedQuery("getAllDtZonaSeguridadByPeriodo", parameters);
	}
	
	public ArrayList<DtDeterminacionPeriodoDTO> getAllDtDeterminacionPeriodo(Integer personaId,Boolean esFiscalizado)throws Exception{
		return getService().getAllDtDeterminacionPeriodo(personaId,esFiscalizado);
	}
	
	public ArrayList<DtDeterminacionResArbDTO> getAllDtDeterminacionArbitrios(Integer personaId,Integer periodo)throws Exception{
		return getService().getAllDtDeterminacionArbitrios(personaId, periodo);
	}

	public Boolean guardarCalculoArbitrios(DeterminacionArbitriosDTO calculo)throws Exception{
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("p_dj_id", calculo.getDjId());
		List<Object> lista =getService().findByNamedQuery("getAllDtDeterminacionArbitrioByDjId", parameters);
		Iterator itDet=lista.iterator();
		while(itDet.hasNext()){
			DtDeterminacionArbitrio arb=(DtDeterminacionArbitrio)itDet.next();
			Integer idAnt=arb.getDeterminacionArbitriosId();
			arb.setEstado(Constante.ESTADO_INACTIVO);
			getService().update(arb);
			getService().actualizaEstadoDtDeterminacionArbitrio(idAnt, Constante.ESTADO_INACTIVO);
		}
		
		DtDeterminacionArbitrio arbitrio=new DtDeterminacionArbitrio();
		Integer Id=getService().ObtenerCorrelativoTabla("dt_determinacion_arbitrios", 1);
		arbitrio.setDeterminacionArbitriosId(Id);
		//--
		arbitrio.setArbitrioLimpieza(calculo.getArbitrioLimpieza());
		arbitrio.setArbitrioParques(calculo.getArbitrioParques());
		arbitrio.setArbitrioRecojo(calculo.getArbitrioRecojo());
		arbitrio.setArbitrioSeguridad(calculo.getArbitrioSeguridad());
		//--
		arbitrio.setGrupoCercaniaId(calculo.getGrupoCercaniaParquesId());
		arbitrio.setDjId(calculo.getDjId());
		arbitrio.setEstado(Constante.ESTADO_ACTIVO);
		arbitrio.setFrecuenciaLimpiezaId(calculo.getFrecuenciaLimpiezaId());
		arbitrio.setFrecuenciaRecojoId(calculo.getFrecuenciaRecojoId());
		arbitrio.setFrenteMlLimpieza(calculo.getFrenteMlLimpieza());
		arbitrio.setTasaMlAnualLimpieza(calculo.getTasaMlAnualLimpieza());
		arbitrio.setZonaSeguridadId(calculo.getZonaSeguridadId());
		
		//--
		arbitrio.setArbitrioLimpiezaPreSubven(calculo.getArbitrioLimpiezaAntesSubvencion());
		arbitrio.setArbitrioLimpiezaPreBenef(calculo.getArbitrioLimpiezaAntesBeneficio());
		
		arbitrio.setArbitrioParquesPreSubven(calculo.getArbitrioParquesAntesSubvencion());
		arbitrio.setArbitrioParquesPreBenef(calculo.getArbitrioParquesAntesBeneficio());
		
		arbitrio.setArbitrioSeguridadPreSubven(calculo.getArbitrioSeguridadAntesSubvencion());
		arbitrio.setArbitrioSeguridadPreBenef(calculo.getArbitrioSeguridadAntesBeneficio());
		
		arbitrio.setArbitrioRecojoPreSubven(calculo.getArbitrioRecojoAntesSubvencion());
		arbitrio.setArbitrioRecojoPreBenef(calculo.getArbitrioRecojoAntesBeneficio());
		//--
		
		arbitrio.setTerminal(getUser().getTerminal());
		arbitrio.setUsuarioId(getUser().getUsuarioId());
		arbitrio.setFechaRegistro(DateUtil.getCurrentDate());
		
		getService().create(arbitrio);
		
		Iterator<RecojoArbitriosDTO> it=calculo.getlRecojoArbitrio().iterator();
		while(it.hasNext()){
			RecojoArbitriosDTO obj=(RecojoArbitriosDTO)it.next();
			Integer IdRec=getService().ObtenerCorrelativoTabla("dt_determinacion_recojo", 1);
			DtDeterminacionRecojo recojo=new DtDeterminacionRecojo();
			recojo.setDeterminacionRecojoId(IdRec);
			recojo.setAreaM2(obj.getAreaM2());
			recojo.setCategoriaUsoId(obj.getCategoriaUsoId());
			recojo.setCostoM2Anual(obj.getCostoM2Anual());
			recojo.setDeterminacionArbitriosId(Id);
			recojo.setEstado(Constante.ESTADO_ACTIVO);
			
			recojo.setFechaRegistro(DateUtil.getCurrentDate());
			recojo.setTerminal(getUser().getTerminal());
			recojo.setUsuarioId(getUser().getUsuarioId());
			getService().create(recojo);
		}
		
		Iterator<SeguridadArbitriosDTO> itSeg=calculo.getlSeguridadArbitrio().iterator();
		while(itSeg.hasNext()){
			SeguridadArbitriosDTO obj=(SeguridadArbitriosDTO)itSeg.next();
			Integer idSeg=getService().ObtenerCorrelativoTabla("dt_determinacion_seguridad", 1);
			DtDeterminacionSeguridad seguridad=new DtDeterminacionSeguridad();
			seguridad.setDeterminacionSeguridadId(idSeg);
			seguridad.setCategoriaUsoId(obj.getCategoriaUsoId());
			seguridad.setDeterminacionArbitriosId(Id);
			seguridad.setTasaAnual(obj.getTasaAnual());
			seguridad.setEstado(Constante.ESTADO_ACTIVO);
			
			seguridad.setFechaRegistro(DateUtil.getCurrentDate());
			seguridad.setTerminal(getUser().getTerminal());
			seguridad.setUsuarioId(getUser().getUsuarioId());
			getService().create(seguridad);
		}
		
		return Boolean.TRUE;
	}
	
	public DeterminacionArbitriosDTO getDeterminacionArbitrios(Integer djId)throws Exception{
		DeterminacionArbitriosDTO arbitrios=getService().getDeterminacionArbitriosDTO(djId);
		if(arbitrios!=null){
			List<SeguridadArbitriosDTO> lSeguridadArbitrio=getService().getAllSeguridadArbitrios(arbitrios.getDeterminacionArbitriosId());
			List<RecojoArbitriosDTO> lRecojoArbitrio=getService().getAllRecojoArbitrios(arbitrios.getDeterminacionArbitriosId());
			arbitrios.setlSeguridadArbitrio(lSeguridadArbitrio);
			arbitrios.setlRecojoArbitrio(lRecojoArbitrio);	
		}
		return arbitrios;
	}
	
	public DeudaPagosDTO getDeudaPagos(int determinacionId)throws Exception{
		return getService().getDeudaPagos(determinacionId);
	}
	
	public List<DeudaValoresDTO> getDeudaValores(int persona,int concepto, int anio)throws Exception{
		return getService().getDeudaValores(persona,concepto,anio);
	}
	//Cod. anterior::inicio
//	public List<DeudaValoresDTO> getDeudaValores(int determinacionId)throws Exception{
//		return getService().getDeudaValores(determinacionId);
//	}
	//Cod. anterior::fin

	public void inputarDeudaSinValores(int deterPreviaId, int deterId) throws SisatException{
		getInputacionDao().inputarDeudaSinValores(deterPreviaId, deterId, getUser().getUsuarioId(), getUser().getTerminal());
	}
	
	public void inputarDeudaFiscalizada(int deterId) throws SisatException{
		getInputacionDao().inputarDeudaFiscalizada(deterId, getUser().getUsuarioId(), getUser().getTerminal());
	}
	
	public List<DtDeterminacion> checkDtDeterminacion(List<DtDeterminacion> list)
			throws Exception {
		return getService().checkDtDeterminacion(list);
	}

	public int getCantTipoPredioPorDeterminacion(int determinaId,
			String tipoPredio) throws Exception {
		return getService().getCantTipoPredioPorDeterminacion(determinaId, tipoPredio);
	}
	
	/**
	 * Obtiene si la determinacion asociada a la Declaracion jurada posee Valores generados.
	 * @param djId
	 * @return
	 * @throws Exception
	 */
	public Boolean validaPoseeValores(Integer predioId,Integer personaId,Integer anioDeuda)throws Exception{
		List<CdDeuda> lDeuda=getService().getAllCdDeuda(predioId,personaId,anioDeuda);
		Iterator<CdDeuda> it=lDeuda.iterator();
		while(it.hasNext()){
			CdDeuda deuda=it.next();
			if(deuda.getFlagCc()!=null&&Util.toInteger(deuda.getFlagCc())>=Constante.TIPO_ACTO_ORDEN_PAGO){
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}
	
	public ArrayList<Integer> getAllPersonaId(Integer periodo,Integer inicioDjId,Integer finDjId)throws Exception{
		//return (ArrayList<Integer>)(List)getService().findByNamedQuery("getAllPersonaId");
		return getService().getAllPersonaId(periodo, inicioDjId, finDjId);
	}
	
	public ArrayList<CampoDTO> getAllParchePersonaId(Integer periodo,Integer inicioDjId,Integer finDjId)throws Exception{
		//return (ArrayList<Integer>)(List)getService().findByNamedQuery("getAllPersonaId");
		return getService().getAllParchePersonaId(periodo, inicioDjId, finDjId); 
	} 
	
	
	public ArrayList<RpDjpredial> getAllRpDJpredial(Integer periodo,Integer inicio,Integer fin)throws Exception{
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("p_flag_dj_anno", "1");
		parameters.put("p_periodo", periodo);
		parameters.put("p_djid_inicio", inicio);//p_djid_inicio AND a.djId<:p_djid_fin
		parameters.put("p_djid_fin", fin);
		return (ArrayList<RpDjpredial>)(List)getService().findByNamedQuery("getAllRpDJpredialByAnnoDj", parameters);
	}
	
	public Integer getCategoriaUso2013(Integer tipoUsoId)throws Exception{
		return getService().getCategoriaUso2013(tipoUsoId);
	}
	
	public Integer getCategoriaSeguridadTipoUso2013(Integer tipoUsoId)throws Exception{
		return getService().getCategoriaSeguridadTipoUso2013(tipoUsoId);
	}
	
	public List<DtDeterminacion> getAllDtDeterminacionArbitriosPredio(Integer predioId,Integer personaId,Integer periodo,Integer conceptoId)throws Exception{
		return getService().getAllDtDeterminacionArbitriosPredio(predioId, personaId, periodo, conceptoId);
	}

	public Integer guardarDeterminacionPredioAlcabala( DtDeterminacionPredio deterPredial) throws Exception {
		Integer result = Constante.RESULT_PENDING;

		deterPredial.setTerminal("10.10.10.190");

		getService().update(deterPredial);
		result = deterPredial.getDeterPredioId();

		return result;
	}
	
	public ArrayList<Integer> getAllPersonaIds(Integer personaId1, Integer personaId2) throws Exception{
		return getService().getAllPersonaIds(personaId1, personaId2);
	}
	
	public ArrayList<RpOtrosFrente> obtenerOtrosFrentes(Integer djId) throws SisatException{	
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("p_dj_id", djId);
		
		return (ArrayList<RpOtrosFrente>)(List)getService().findByNamedQuery("getAllRpOtrosFrenteByDjId", parameters);				
	}
	public Double obtenerArancelOtrosFrentes(Integer ubicacionId, Integer periodo) throws Exception{
		return getService().obtenerArancelOtrosFrentes(ubicacionId, periodo);
	}
	public Boolean generarDeudaAribtrios(Integer personaId, Integer djId, int anio, 
			BigDecimal limpieza, BigDecimal parques, BigDecimal serenazgo, Integer usuarioId, String terminal)  throws SisatException{
		
		Boolean rpst = Boolean.FALSE;
	try{	
		//crear determinaciones y deudas
		DtDeterminacion dtArbitrios = new DtDeterminacion();
		CdDeuda cdArbitrios = new CdDeuda();
		
		//SUBCONCEPTOS 1.Parques=32, 2.Serenazgo=33, 3.Limpieza=39		
		
		for(int i=1; i<=3; i++){
			//Determinacion
			dtArbitrios = new DtDeterminacion();			
			
			Integer determinacionId = service.obtenerCorrelativoTabla("dt_determinacion");
			
			dtArbitrios.setDeterminacionId(determinacionId);
			dtArbitrios.setPersonaId(personaId);
			dtArbitrios.setAnnoDeterminacion(anio);
			dtArbitrios.setConceptoId(Constante.CONCEPTO_ARBITRIO);
			dtArbitrios.setDjreferenciaId(djId);
			dtArbitrios.setEstado(Constante.ESTADO_ACTIVO);
			dtArbitrios.setUsuarioId(usuarioId);
			dtArbitrios.setFechaRegistro(DateUtil.getCurrentDate());
			dtArbitrios.setTerminal(terminal);
			dtArbitrios.setTipoDeudaId(Constante.TIPO_DEUDA_GENERADO_MANUALMENTE);			
			
			dtArbitrios.setBaseImponible(BigDecimal.ZERO);
			dtArbitrios.setBaseExonerada(BigDecimal.ZERO);
			dtArbitrios.setBaseAfecta(BigDecimal.ZERO);
			
			BigDecimal montoOriginal = new BigDecimal(0);
			
			if(i==1 && parques.compareTo(new BigDecimal(0)) > 0){
				//PARQUES
				dtArbitrios.setSubconceptoId(Constante.SUB_CONCEPTO_ARBITRIOS_PARQUES);				
				dtArbitrios.setImpuesto(parques);
				int nroCuotas = service.getNroCuotasByAnio(anio, dtArbitrios.getConceptoId(), dtArbitrios.getSubconceptoId());
				dtArbitrios.setNroCuotas(nroCuotas);
				
				montoOriginal = parques;
				montoOriginal = montoOriginal.setScale(2, RoundingMode.HALF_UP);
				
				//Deudas para PARQUES		
				for(int nroCuota=1; nroCuota<=nroCuotas; nroCuota++){					
					cdArbitrios = new CdDeuda();
					Integer deudaId = service.obtenerCorrelativoTabla("cd_deuda");					
					
					cdArbitrios.setDeudaId(deudaId);
					cdArbitrios.setTipoDeudaId(Constante.TIPO_DEUDA_GENERADO_MANUALMENTE);
					cdArbitrios.setPersonaId(personaId);
					cdArbitrios.setConceptoId(Constante.CONCEPTO_ARBITRIO);
					cdArbitrios.setSubconceptoId(Constante.SUB_CONCEPTO_ARBITRIOS_PARQUES);
					cdArbitrios.setDeterminacionId(determinacionId);
					cdArbitrios.setAnnoDeuda(anio);
					cdArbitrios.setFechaEmision(DateUtil.getCurrentDate());
					cdArbitrios.setUsuarioId(usuarioId);
					cdArbitrios.setEstado(Constante.ESTADO_ACTIVO);
					cdArbitrios.setFechaRegistro(DateUtil.getCurrentDate());
					cdArbitrios.setTerminal(terminal);
									
					BigDecimal insoluto = montoOriginal.divide(new BigDecimal(nroCuotas), 2, RoundingMode.HALF_UP);					
					insoluto = insoluto.setScale(2, RoundingMode.HALF_UP);					
					
					Timestamp fechaVencimiento = service.getFechaVenciminetoByCuotaAnio(anio, cdArbitrios.getConceptoId(), cdArbitrios.getSubconceptoId(), nroCuota);
					
					cdArbitrios.setFechaVencimiento(fechaVencimiento);
					cdArbitrios.setNroCuota(nroCuota);					
					cdArbitrios.setMontoOriginal(montoOriginal);
					cdArbitrios.setInsoluto(insoluto);
					cdArbitrios.setTotalDeuda(insoluto);
					
					cdArbitrios.setReajuste(BigDecimal.ZERO);
					cdArbitrios.setReajusteCancelado(BigDecimal.ZERO);
					cdArbitrios.setInteresMensual(BigDecimal.ZERO);
					cdArbitrios.setInteresMensualCancelado(BigDecimal.ZERO);
					cdArbitrios.setInteresAnual(BigDecimal.ZERO);
					cdArbitrios.setInteresCapitalizado(BigDecimal.ZERO);
					cdArbitrios.setInteresCapiCancelado(BigDecimal.ZERO);
					cdArbitrios.setInsolutoCancelado(BigDecimal.ZERO);
					cdArbitrios.setDerechoEmision(BigDecimal.ZERO);
					cdArbitrios.setDerechoEmisionCancelado(BigDecimal.ZERO);					
					cdArbitrios.setTotalCancelado(BigDecimal.ZERO);
					
					//GUARDA LA DEUDA POR CUOTA
					service.create(cdArbitrios);
					
					//Guardar deuda historica
					CdDeudaHistorica dh = new CdDeudaHistorica();
					CdDeudaHistoricaPK id = new CdDeudaHistoricaPK();
					id.setDeudaId(deudaId);
					dh.setId(id);
					// dh.setTipoMovimientoId();
					dh.setDeterminacionId(cdArbitrios.getDeterminacionId());
					dh.setPersonaId(cdArbitrios.getPersonaId());
					// dh.setFechaMovimiento();
					dh.setTipoDeuda(cdArbitrios.getTipoDeudaId());
					dh.setFechaVencimiento(cdArbitrios.getFechaVencimiento());
					dh.setInsoluto(cdArbitrios.getInsoluto());
					dh.setTotal(cdArbitrios.getTotalDeuda());
					
					//cc: dh.setUsuarioId(Constante.USUARIO_ID);
					dh.setEstado(Constante.ESTADO_ACTIVO);
					//cc: dh.setFechaRegistro(DateUtil.getCurrentDate());
					//cc: dh.setTerminal(Constante.TERMINAL);
					guardarDeudaHistorica(dh);
					
					//System.out.println("Genero Parques deuda: "+cdArbitrios.getDeudaId()+" cuota "+nroCuota);
				}
				
				//GUARDA LA DETERMINACION DEL ARBITRIO
				service.create(dtArbitrios);
				
				//System.out.println("Genero Parques determinacion: " + dtArbitrios.getDeterminacionId());
				
			}else if(i==2 && serenazgo.compareTo(new BigDecimal(0)) > 0){
				//Serenazgo
				dtArbitrios.setSubconceptoId(Constante.SUB_CONCEPTO_ARBITRIOS_SEGURIDAD);
				dtArbitrios.setImpuesto(serenazgo);
				int nroCuotas = service.getNroCuotasByAnio(anio, dtArbitrios.getConceptoId(), dtArbitrios.getSubconceptoId());
				dtArbitrios.setNroCuotas(nroCuotas);
									
				montoOriginal = serenazgo;				
				montoOriginal = montoOriginal.setScale(2, RoundingMode.HALF_UP);	
				
				for(int nroCuota=1; nroCuota<=nroCuotas; nroCuota++){
					//Deuda
					cdArbitrios = new CdDeuda();
					Integer deudaId = service.obtenerCorrelativoTabla("cd_deuda");
					
					cdArbitrios.setDeudaId(deudaId);		
					cdArbitrios.setTipoDeudaId(Constante.TIPO_DEUDA_GENERADO_MANUALMENTE);
					cdArbitrios.setPersonaId(personaId);
					cdArbitrios.setConceptoId(Constante.CONCEPTO_ARBITRIO);		
					cdArbitrios.setSubconceptoId(Constante.SUB_CONCEPTO_ARBITRIOS_SEGURIDAD);
					cdArbitrios.setDeterminacionId(determinacionId);
					cdArbitrios.setAnnoDeuda(anio);
					cdArbitrios.setFechaEmision(DateUtil.getCurrentDate());
					cdArbitrios.setUsuarioId(usuarioId);
					cdArbitrios.setEstado(Constante.ESTADO_ACTIVO);
					cdArbitrios.setFechaRegistro(DateUtil.getCurrentDate());
					cdArbitrios.setTerminal(terminal);				
					
					Timestamp fechaVencimiento = service.getFechaVenciminetoByCuotaAnio(anio, cdArbitrios.getConceptoId(), cdArbitrios.getSubconceptoId(), nroCuota);
					
					BigDecimal insoluto = montoOriginal.divide(new BigDecimal(nroCuotas), 2, RoundingMode.HALF_UP);	
					insoluto = insoluto.setScale(2, RoundingMode.HALF_UP);			
					
					cdArbitrios.setFechaVencimiento(fechaVencimiento);
					cdArbitrios.setNroCuota(nroCuota);					
					cdArbitrios.setMontoOriginal(montoOriginal);
					cdArbitrios.setInsoluto(insoluto);
					cdArbitrios.setTotalDeuda(insoluto);					
					
					cdArbitrios.setReajuste(BigDecimal.ZERO);
					cdArbitrios.setReajusteCancelado(BigDecimal.ZERO);
					cdArbitrios.setInteresMensual(BigDecimal.ZERO);
					cdArbitrios.setInteresMensualCancelado(BigDecimal.ZERO);
					cdArbitrios.setInteresAnual(BigDecimal.ZERO);
					cdArbitrios.setInteresCapitalizado(BigDecimal.ZERO);
					cdArbitrios.setInteresCapiCancelado(BigDecimal.ZERO);
					cdArbitrios.setInsolutoCancelado(BigDecimal.ZERO);
					cdArbitrios.setDerechoEmision(BigDecimal.ZERO);
					cdArbitrios.setDerechoEmisionCancelado(BigDecimal.ZERO);					
					cdArbitrios.setTotalCancelado(BigDecimal.ZERO);
					
					//GUARDA LA DEUDA POR CUOTA
					em.merge(cdArbitrios);		
					
					//Guardar deuda historica
					CdDeudaHistorica dh = new CdDeudaHistorica();
					CdDeudaHistoricaPK id = new CdDeudaHistoricaPK();
					id.setDeudaId(deudaId);
					dh.setId(id);
					// dh.setTipoMovimientoId();
					dh.setDeterminacionId(cdArbitrios.getDeterminacionId());
					dh.setPersonaId(cdArbitrios.getPersonaId());
					// dh.setFechaMovimiento();
					dh.setTipoDeuda(cdArbitrios.getTipoDeudaId());
					dh.setFechaVencimiento(cdArbitrios.getFechaVencimiento());
					dh.setInsoluto(cdArbitrios.getInsoluto());
					dh.setTotal(cdArbitrios.getTotalDeuda());
					
					//cc: dh.setUsuarioId(Constante.USUARIO_ID);
					dh.setEstado(Constante.ESTADO_ACTIVO);
					//cc: dh.setFechaRegistro(DateUtil.getCurrentDate());
					//cc: dh.setTerminal(Constante.TERMINAL);
					guardarDeudaHistorica(dh);
					
					//System.out.println("Genero Seguridad deuda: "+cdArbitrios.getDeudaId()+" cuota "+nroCuota);
				}
				
				//GUARDA LA DETERMINACION
				em.merge(dtArbitrios);
				
				//System.out.println("Genero Seguridad determinacion: " + dtArbitrios.getDeterminacionId());
				
			}else if(i==3 && limpieza.compareTo(new BigDecimal(0)) > 0){
				//Limpieza
				dtArbitrios.setSubconceptoId(Constante.SUB_CONCEPTO_ARBITRIOS_LIMPIEZA_PUBLICA);
				dtArbitrios.setImpuesto(limpieza);
				int nroCuotas = service.getNroCuotasByAnio(anio, dtArbitrios.getConceptoId(), dtArbitrios.getSubconceptoId());
				dtArbitrios.setNroCuotas(nroCuotas);
				
				montoOriginal = limpieza;			
				montoOriginal = montoOriginal.setScale(2, RoundingMode.HALF_UP);	
				
				for(int nroCuota=1; nroCuota<=nroCuotas; nroCuota++){
					//Deuda
					cdArbitrios = new CdDeuda();
					Integer deudaId = service.obtenerCorrelativoTabla("cd_deuda");
					
					cdArbitrios.setDeudaId(deudaId);		
					cdArbitrios.setTipoDeudaId(Constante.TIPO_DEUDA_GENERADO_MANUALMENTE);
					cdArbitrios.setPersonaId(personaId);
					cdArbitrios.setConceptoId(Constante.CONCEPTO_ARBITRIO);		
					cdArbitrios.setSubconceptoId(Constante.SUB_CONCEPTO_ARBITRIOS_LIMPIEZA_PUBLICA);
					cdArbitrios.setDeterminacionId(determinacionId);
					cdArbitrios.setAnnoDeuda(anio);
					cdArbitrios.setFechaEmision(DateUtil.getCurrentDate());
					cdArbitrios.setUsuarioId(usuarioId);
					cdArbitrios.setEstado(Constante.ESTADO_ACTIVO);
					cdArbitrios.setFechaRegistro(DateUtil.getCurrentDate());
					cdArbitrios.setTerminal(terminal);				
					
					Timestamp fechaVencimiento = service.getFechaVenciminetoByCuotaAnio(anio, cdArbitrios.getConceptoId(), cdArbitrios.getSubconceptoId(), nroCuota);
					
					BigDecimal insoluto = montoOriginal.divide(new BigDecimal(nroCuotas), 2, RoundingMode.HALF_UP);	
					insoluto = insoluto.setScale(2, RoundingMode.HALF_UP);	
					
					cdArbitrios.setFechaVencimiento(fechaVencimiento);
					cdArbitrios.setNroCuota(nroCuota);					
					cdArbitrios.setMontoOriginal(montoOriginal);
					cdArbitrios.setInsoluto(insoluto);
					cdArbitrios.setTotalDeuda(insoluto);					

					cdArbitrios.setReajuste(BigDecimal.ZERO);
					cdArbitrios.setReajusteCancelado(BigDecimal.ZERO);
					cdArbitrios.setInteresMensual(BigDecimal.ZERO);
					cdArbitrios.setInteresMensualCancelado(BigDecimal.ZERO);
					cdArbitrios.setInteresAnual(BigDecimal.ZERO);
					cdArbitrios.setInteresCapitalizado(BigDecimal.ZERO);
					cdArbitrios.setInteresCapiCancelado(BigDecimal.ZERO);
					cdArbitrios.setInsolutoCancelado(BigDecimal.ZERO);
					cdArbitrios.setDerechoEmision(BigDecimal.ZERO);
					cdArbitrios.setDerechoEmisionCancelado(BigDecimal.ZERO);					
					cdArbitrios.setTotalCancelado(BigDecimal.ZERO);
					
					//GUARDA LA DEUDA POR CUOTA
					em.merge(cdArbitrios);						
					
					//Guardar deuda historica
					CdDeudaHistorica dh = new CdDeudaHistorica();
					CdDeudaHistoricaPK id = new CdDeudaHistoricaPK();
					id.setDeudaId(deudaId);
					dh.setId(id);
					// dh.setTipoMovimientoId();
					dh.setDeterminacionId(cdArbitrios.getDeterminacionId());
					dh.setPersonaId(cdArbitrios.getPersonaId());
					// dh.setFechaMovimiento();
					dh.setTipoDeuda(cdArbitrios.getTipoDeudaId());
					dh.setFechaVencimiento(cdArbitrios.getFechaVencimiento());
					dh.setInsoluto(cdArbitrios.getInsoluto());
					dh.setTotal(cdArbitrios.getTotalDeuda());
					
					//cc: dh.setUsuarioId(Constante.USUARIO_ID);
					dh.setEstado(Constante.ESTADO_ACTIVO);
					//cc: dh.setFechaRegistro(DateUtil.getCurrentDate());
					//cc: dh.setTerminal(Constante.TERMINAL);
					guardarDeudaHistorica(dh);
					
					//System.out.println("Genero Limpieza deuda: "+cdArbitrios.getDeudaId()+" cuota "+nroCuota);
				}
				//GUARDA LA DETERMINACION
				em.merge(dtArbitrios);
				//System.out.println("Genero Limpieza determinacion: " + dtArbitrios.getDeterminacionId());
			}
			
		}
		rpst = Boolean.TRUE;
		
	}catch( Exception e){		
		throw new SisatException("ERROR en Generacion de Arbitrios. ".concat(e.getMessage()), e);
	}
		return rpst;
	}
	
	public ArrayList<DtDeterminacionDTO> getArbitriosGenerados(Integer personaId, Integer djId) throws SisatException{		
		return getService().getArbitriosGenerados(personaId, djId);		
	}
	
	public Integer getDjByPredioId(Integer personaId, Integer predioId, Integer anio) throws SisatException{
		return getService().getDjByPredioId( personaId, predioId, anio);
	}
	
	/**
	 * Determinacion masiva del 2014
	 */
	public Integer getCategoriaUso2014(Integer tipoUsoId)throws Exception{
		return getService().getCategoriaUso2014(tipoUsoId);
	}
	
	public List<TipoUsoDTO> getCategoriaUso2014All()throws Exception{
		return getService().getCategoriaUso2014All();
	}
	
	public Integer getCategoriaSeguridadTipoUso2014(Integer tipoUsoId)throws Exception{
		return getService().getCategoriaSeguridadTipoUso2014(tipoUsoId);
	}

	public List<TipoUsoDTO> getCategoriaSeguridadTipoUso2014All()throws Exception{
		return getService().getCategoriaSeguridadTipoUso2014All();
	}
	
	public List<DtZonaSeguridadUso2014> getAllDtZonaSeguridadUso2014(Integer zonaSeguridadId,Integer periodo)throws Exception{
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("p_zona_seguridad_id", zonaSeguridadId);
		parameters.put("p_periodo", periodo);
		return (ArrayList<DtZonaSeguridadUso2014>)(List)getService().findByNamedQuery("getAllDtZonaSeguridadUso2014ByPeriodo", parameters);
	}
	
	public List<DtZonaSeguridadUso2014> getAllDtZonaSeguridadUso2014All()throws Exception{
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("p_periodo", 2014);
		return (ArrayList<DtZonaSeguridadUso2014>)(List)getService().findByNamedQuery("getAllDtZonaSeguridadUso2014All", parameters);
	}
	
	public List<DeudaValoresDTO> getValorCoactiva(Integer codigo,String anio,Integer tipo,Integer determinacion)throws Exception {
		return getService().getValorCoactiva(codigo,anio,tipo,determinacion);
	}
	public List<DeudaValoresDTO> getValorArbitrios(Integer codigo,Integer anio,Integer determinacion)throws Exception {
		return getService().getValorArbitrios(codigo,anio,determinacion);
	}
	public List<DeudaValoresDTO> getDeudaValoresArbitrios(int determinacionId)throws Exception{
		return getService().getDeudaValoresArbitrios(determinacionId);
	}
	public Integer actualizarEstadoDeterminacion(Integer determinacion,String estado) throws Exception {
		return getService().actualizarEstadoDeterminacion(determinacion, estado);
	}
	public Integer guardarDeterminacionVariacion(DtDeterminacionValor variacion)throws Exception{
		Integer result=0;
		variacion.setEstado(Constante.ESTADO_ACTIVO);
		variacion.setUsuarioId(getUser().getUsuarioId());
		variacion.setFechaRegistro(DateUtil.getCurrentDate());
		variacion.setTerminal(getUser().getTerminal());
		getService().create(variacion);
	//	result=variacion.getDeterminacionValorId();
		
		return result;
	}
	
	public List<DtDeterminacion> getAllDtDeterminacionArbitriosSubconcepto(Integer predioId,Integer personaId,Integer periodo,Integer conceptoId,Integer subconceptoId)throws Exception{
		return getService().getAllDtDeterminacionArbitriosSubconcepto(predioId, personaId, periodo, conceptoId,subconceptoId);
	}
	
	public DatosDeterminacionPredialValoresDTO getMontoDeterminacionValor(int persona,int concepto,int anio)throws Exception{
		return getService().getMontoDeterminacionValor(persona,concepto,anio);
	}
	
	public Integer actualizarDiffDeterminacion(Integer determinacion,BigDecimal diferencia)throws Exception{
		return getService().actualizarDiffDeterminacion(determinacion, diferencia);
	}
	
	public DeudaPagosPredialDTO getDeudaValoresPagos(int persona,int anio,int determinacionId) {
		return getService().getDeudaValoresPagos(persona,anio,determinacionId);
	}
	

	public List<DatosDeterminacionPredialValoresDTO> getDeterminacionValores(int persona,int concepto,int anio,int determinacion) throws Exception{
		return getService().getDeterminacionValores(persona, concepto, anio,determinacion);
	}
	
	public void inputarDeudaSinValor(int deterPreviaId, int deterId) throws SisatException{
		getInputacionDao().inputarDeudaSinValor(deterPreviaId, deterId, getUser().getUsuarioId(), getUser().getTerminal());
	}

	public DtDeterminacion getMinDeterminacion(Integer personaId,Integer periodo,Integer concepto,String estado)throws Exception{
		return getService().getMinDeterminacion(personaId, periodo, concepto, estado);
	}
	
	public List<DeudaValoresDTO> getDeudaValoresRd(int persona,int concepto, int anio)throws Exception{
		return getService().getDeudaValores(persona,concepto,anio);
	}
	public Integer actualizarFlagVariacionDeterminacion(Integer determinacion,String estado) throws Exception {
		return getService().actualizarFlagVariacionDeterminacion(determinacion, estado);
	}
	
	/**
	 * Determinacion masiva del 2015
	 */
	public Integer getCategoriaUso2015(Integer tipoUsoId)throws Exception{
		return getService().getCategoriaUso2015(tipoUsoId);
	}
	
	public List<TipoUsoDTO> getCategoriaUso2015All()throws Exception{
		return getService().getCategoriaUso2015All();
	}
	
	public Integer getCategoriaSeguridadTipoUso2015(Integer tipoUsoId)throws Exception{
		return getService().getCategoriaSeguridadTipoUso2015(tipoUsoId);
	}

	public List<TipoUsoDTO> getCategoriaSeguridadTipoUso2015All()throws Exception{
		return getService().getCategoriaSeguridadTipoUso2015All();
	}
	
	public List<DtZonaSeguridadUso2015> getAllDtZonaSeguridadUso2015(Integer zonaSeguridadId,Integer periodo)throws Exception{
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("p_zona_seguridad_id", zonaSeguridadId);
		parameters.put("p_periodo", periodo);
		return (ArrayList<DtZonaSeguridadUso2015>)(List)getService().findByNamedQuery("getAllDtZonaSeguridadUso2015ByPeriodo", parameters);
	}
	
	public List<DtZonaSeguridadUso2015> getAllDtZonaSeguridadUso2015All()throws Exception{
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("p_periodo", 2015);
		return (ArrayList<DtZonaSeguridadUso2015>)(List)getService().findByNamedQuery("getAllDtZonaSeguridadUso2015All", parameters);
	}
	
	public int getUsoPensionista(Integer djId) throws Exception {
		return getService().getUsoPensionista(djId);
	}
	public int getCantidadUsoPensionista(Integer djId) throws Exception {
		return getService().getCantidadUsoPensionista(djId);
	}
	
	/**
	 * Determinacion masiva del 2016
	 */
	public Integer getCategoriaUso2016(Integer tipoUsoId,Integer periodo)throws Exception{
		return getService().getCategoriaUso2016(tipoUsoId,periodo);
	}
	
	public List<TipoUsoDTO> getCategoriaUso2016All(Integer periodo)throws Exception{
		return getService().getCategoriaUso2016All(periodo);
	}
	
	public Integer getCategoriaSeguridadTipoUso2016(Integer tipoUsoId,Integer periodo)throws Exception{
		return getService().getCategoriaSeguridadTipoUso2016(tipoUsoId,periodo);
	}

	public List<TipoUsoDTO> getCategoriaSeguridadTipoUso2016All(Integer periodo)throws Exception{
		return getService().getCategoriaSeguridadTipoUso2016All(periodo);
	}
	
	public List<DtMatrizSeguridad> getAllDtZonaSeguridadUso2016(Integer zonaSeguridadId,Integer periodo)throws Exception{
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("p_zona_seguridad_id", zonaSeguridadId);
		parameters.put("p_periodo", periodo);
		return (ArrayList<DtMatrizSeguridad>)(List)getService().findByNamedQuery("getAllDtZonaSeguridadUso2016ByPeriodo", parameters);
	}
	
	public List<DtMatrizSeguridad> getAllDtZonaSeguridadUso2016All(Integer periodo)throws Exception{
		System.out.println(periodo);
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("p_periodo", periodo);
		return (ArrayList<DtMatrizSeguridad>)(List)getService().findByNamedQuery("getAllDtZonaSeguridadUso2016All", parameters);
	}
}