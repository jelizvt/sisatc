/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sat.sisat.predial.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remote;

import com.sat.sisat.administracion.dto.CampoDTO;
import com.sat.sisat.common.dto.TipoUsoDTO;
import com.sat.sisat.determinacion.vehicular.dto.DeudaPagosDTO;
import com.sat.sisat.determinacion.vehicular.dto.DeudaValoresDTO;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.persistence.entity.CdDeuda;
import com.sat.sisat.persistence.entity.CdDeudaHistorica;
import com.sat.sisat.persistence.entity.DtCercaniaParque;
import com.sat.sisat.persistence.entity.DtCondiEspecialContri;
import com.sat.sisat.persistence.entity.DtCuotaConcepto;
import com.sat.sisat.persistence.entity.DtDeterminacion;
import com.sat.sisat.persistence.entity.DtDeterminacionConstruccion;
import com.sat.sisat.persistence.entity.DtDeterminacionInstalacion;
import com.sat.sisat.persistence.entity.DtDeterminacionPredio;
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
import com.sat.sisat.predial.dto.DatosDeterminacionPredialValoresDTO;
import com.sat.sisat.predial.dto.DeterminacionArbitriosDTO;
import com.sat.sisat.predial.dto.DeudaPagosPredialDTO;
import com.sat.sisat.predial.dto.DtDeterminacionConstruccionDTO;
import com.sat.sisat.predial.dto.DtDeterminacionDTO;
import com.sat.sisat.predial.dto.DtDeterminacionInstalacionDTO;
import com.sat.sisat.predial.dto.DtDeterminacionPeriodoDTO;
import com.sat.sisat.predial.dto.DtDeterminacionPredioDTO;
import com.sat.sisat.predial.dto.DtDeterminacionResArbDTO;


/**
 *
 * @author cchaucca
 */
@Remote
public interface CalculoPredialBoRemote {
	public MpPersona getFindPersona(Integer ContribuyenteId)throws Exception;
	public Integer guardarDeterminacion(DtDeterminacion determinacion)throws Exception;
	public ArrayList<RpDjpredial> getAllRpDJpredial(Integer personaId,Integer periodo)throws Exception;
	
	public RpDjpredial getRpDJpredial(Integer djId)throws Exception;
	
	public ArrayList<RpDjpredial> getAllRpDJpredialArb(Integer personaId,Integer periodo)throws Exception;
	public Integer guardarDeterminacionPredial(DtDeterminacionPredio deterPredial)throws Exception;
	public ArrayList<DtDeterminacionPredio> getAllDtDeterminacionPredio(Integer determinacionId)throws Exception;
	public ArrayList<DtTasaImpuestoPredial> getAllDeTasaImpuestoPredial(Integer periodo)throws Exception;
	public List<RpInstalacionDj> getOtrasInstalaciones(RpDjpredial rpDjPredial,Integer periodo)throws Exception;
	public List<RpInstalacionDj> getOtrasInstalacionesConAnioFiscal(RpDjpredial rpDjPredial,Integer periodo)throws Exception;
	public Double getValorArancelRustico(Integer periodo,Integer tipoTierraId,Integer categoriaRusticoId,Integer altitudId)throws Exception;
	public RpDjdireccion getRpDjDireccion(Integer DjId)throws Exception;
	public Double getValorArancelUrbano(Integer periodo,RpDjdireccion direccion)throws Exception;
	public List<RpDjconstruccion> getConstruccion(RpDjpredial rpDjPredial,Integer periodo)throws Exception;
	public List<RpDjconstruccion> getConstruccionConAnioFiscal(RpDjpredial rpDjPredial,Integer periodo)throws Exception;
	public Integer guardarDeterminacionConstruccion(DtDeterminacionConstruccion determinacionNivel)throws Exception;
	public double obtenerValorUnitarioComponente(int periodoId,int tipoComponenteId,int categoriaComponenteId)throws Exception;
	public double obtenerPorcentajeDepreciacion(int tipo_depreciacion_id,int anno_determinacion,int tipo_material_id,int conservacion_id,int antiguedad)throws Exception;
	public List<DtFechaVencimiento> getFechaVencimiento(Integer subConcepto,Integer anioAfectacion)throws Exception;
	public DtCuotaConcepto getCuotasConcepto(Integer subConcepto, Integer anioAfectacion)throws Exception;
	public Integer guardarDeuda(CdDeuda deuda)throws Exception;
	public Integer guardarDeudaHistorica(CdDeudaHistorica deudaHistorica)throws Exception;
	public DtFactorOfic getFactorOficializacion(Integer periodo)throws Exception;
	public GnCondicionEspecial getGnCondicionEspecialContribuyente(Integer personaId,Integer periodo)throws Exception;
	public List<RpDjuso> getRpDjusoByDjId(Integer djId) throws Exception;
	public DtCondiEspecialContri getDtCondiEspecialContriByPeriodo(Integer periodo,Integer tipoCondEspecialId,Integer usoPredioId)throws Exception;
	
	public DtDeterminacion getDtDeterminacion(Integer personaId,Integer periodo,Integer concepto,String estado)throws Exception;
	
	public DtDeterminacion getDtDeterminacionAnt(Integer personaId,Integer periodo,Integer concepto,String estado,Integer determinacionId)throws Exception;////Obtiene las determinaciones activas anteriores a una determinacion
	public DtDeterminacion getDtDeterminacion(Integer papeletaId,Integer concepto)throws Exception;
	public int actualizaEstadoCdDeuda(Integer determinacionId,String estado)throws Exception;
	public List<CdDeuda> recuperarFlagCdDeuda(Integer determinacionId,String estado)throws Exception;
	public int actualizaEstadoDtDeterminacionPredio(Integer determinacionId,String estado)throws Exception;
	public int actualizaEstadoDtDeterminacionConstruccion(Integer determinacionId,String estado)throws Exception;
	public ArrayList<DtDeterminacion> getAllDtDeterminacionByPersonaId(Integer personaId,Integer conceptoId)throws Exception;
	public ArrayList<DtDeterminacionPredioDTO> getAllDtDeterminacionPredioDj(Integer determinacionId)throws Exception;
	public Integer guardarDeterminacionInstalacion(DtDeterminacionInstalacion deterIntalacion)throws Exception;
	public ArrayList<DtDeterminacionConstruccionDTO> getAllDtDeterminacionConstruccion(Integer deterPredioId,Integer periodo)throws Exception;
	public ArrayList<DtDeterminacionInstalacionDTO> getAllDtDeterminacionInstalacion(Integer deterPredioId,Integer periodo)throws Exception;

	public DtFrecuenciaLimpieza getDtFrecuenciaLimpieza(Integer ubicacionId,Integer periodo)throws Exception;
	public DtFrecuenciaRecojo getDtFrecuenciaRecojo(Integer ubicacionId,Integer periodo)throws Exception;
	
	public List<DtMatrizRecojo> getAllDtMatrizRecojo(Integer frecuencia,Integer periodo)throws Exception;
	public List<DtMatrizRecojo> getAllDtMatrizRecojo(Integer periodo)throws Exception;
	
	public RpDjarbitrio getRpDjarbitrio(Integer djId)throws Exception;
	public List<RpDjuso> getAllRpDjuso(Integer djArbitrioId)throws Exception;
	public DtCercaniaParque getDtCercaniaParque(Integer ubicacionId,Integer periodo)throws Exception;
	public DtGrupoCercania getDtGrupoCercania(Integer grupoCercaniaId)throws Exception;
	public DtZonaSeguridadUbicacion getDtZonaSeguridadUbicacion(Integer ubicacionId,Integer periodo)throws Exception;
	public List<DtZonaSeguridadUso> getAllDtZonaSeguridadUso(Integer ubicacionId,Integer periodo)throws Exception;
	
	public List<DtZonaSeguridadUso2013> getAllDtZonaSeguridadUso2013(Integer ubicacionId,Integer periodo)throws Exception;
	
	public List<RpCategoriaUso> getAllRpCategoriaUso(Integer periodo)throws Exception;
	public List<RpCategoriaUsoSeguridad> getAllRpCategoriaUsoSeguridad(Integer periodo)throws Exception;
	
	public List<DtZonaSeguridad> getAllDtZonaSeguridad(Integer periodo)throws Exception;
	public ArrayList<DtDeterminacion> getAllDtDeterminacion(Integer personaId,Integer periodo,Integer concepto,String estado)throws Exception;
	public ArrayList<DtDeterminacion> getAllDtDeterminacionByPeriodo(Integer personaId,Integer periodo,Integer concepto)throws Exception;
	public List<RpTipoUso> getAllRpTipoUso(Integer periodo)throws Exception;
	public List<Integer> getAniosDeterminacion(Integer personaId)throws Exception;
	public ArrayList<DtDeterminacionPeriodoDTO> getAllDtDeterminacionPeriodo(Integer personaId,Boolean esFiscalizado)throws Exception;
	public ArrayList<DtDeterminacionResArbDTO> getAllDtDeterminacionArbitrios(Integer personaId,Integer periodo)throws Exception;
	//public ArrayList<DtDeterminacion> getAllDtDeterminacion(Integer djId,Integer concepto)throws Exception;
	public ArrayList<DtDeterminacion> getAllDtDeterminacion(Integer personaId,Integer periodo,Integer djId,Integer concepto)throws Exception;
	public Boolean guardarCalculoArbitrios(DeterminacionArbitriosDTO calculo)throws Exception;
	public DeterminacionArbitriosDTO getDeterminacionArbitrios(Integer djId)throws Exception;
	
	public DtDeterminacion getDtDeterminacionById(Integer determinacionId)throws Exception;
	
	public DeudaPagosDTO getDeudaPagos(int determinacionId)throws Exception;
//	public List<DeudaValoresDTO> getDeudaValores(int determinacionId)throws Exception;
	public List<DeudaValoresDTO> getDeudaValores(int persona,int concepto, int anio)throws Exception;
	
	public void inputarDeudaSinValores(int deterPreviaId, int deterId) throws SisatException;
	
	public void inputarDeudaFiscalizada(int deterId) throws SisatException;
	
	public int getCantTipoPredioPorDeterminacion(int determinaId, String tipoPredio) throws Exception;
	
	public List<DtDeterminacion> checkDtDeterminacion(List<DtDeterminacion> list)throws Exception;
	public Boolean validaPoseeValores(Integer predioId,Integer personaId,Integer anioDeuda)throws Exception;
	
	//public ArrayList<RpDjpredial> getAllRpDJpredial(Integer periodo)throws Exception;
	public ArrayList<RpDjpredial> getAllRpDJpredial(Integer periodo,Integer inicio,Integer fin)throws Exception;
	
	public Integer getCategoriaUso2013(Integer tipoUsoId)throws Exception;
	public Integer getCategoriaSeguridadTipoUso2013(Integer tipoUsoId)throws Exception;
	
	public ArrayList<Integer> getAllRpDJpredialId(Integer periodo,Integer personaInicioDjId,Integer personaFinDjId)throws Exception;
	
	public ArrayList<Integer> getAllPersonaId(Integer periodo,Integer inicioDjId,Integer finDjId)throws Exception;
	
	public ArrayList<CampoDTO> getAllParchePersonaId(Integer periodo,Integer inicioDjId,Integer finDjId)throws Exception;
	
	public DtDeterminacion getAllDtDeterminacionArbitrio(Integer personaId,Integer DjId,Integer periodo,Integer concepto,String estado)throws Exception;
	
	public int actualizaEstadoDtDeterminacionArbitrios(Integer djId,String estado)throws Exception;
	
	public void inactivaDeterminacionAnterior(Integer personaId,Integer periodo)throws Exception;
	
	public List<DtDeterminacion> getAllDtDeterminacionArbitriosPredio(Integer predioId,Integer personaId,Integer periodo,Integer conceptoId)throws Exception;

	public DtDeterminacion getDtDeterminacionPapeleta(Integer papeletaId,Integer concepto)throws Exception;
	
	public Integer guardarDeterminacionPredioAlcabala( DtDeterminacionPredio deterPredial) throws Exception;
	
	public ArrayList<Integer> getAllPersonaIds(Integer personaId1, Integer personaId2) throws Exception;
	
	public List<RpOtrosFrente> obtenerOtrosFrentes(Integer djId) throws SisatException;
	
	public Double obtenerArancelOtrosFrentes(Integer ubicacionId, Integer periodo) throws Exception;
	
	public Boolean generarDeudaAribtrios(Integer personaId, Integer djId, int anio, BigDecimal limpieza, BigDecimal parques,	BigDecimal serenazgo, Integer usuarioId, String terminal)  throws SisatException;
	
	public ArrayList<DtDeterminacionDTO> getArbitriosGenerados(Integer personaId, Integer djId) throws SisatException;
	
	public Integer getDjByPredioId(Integer personaId, Integer predioId, Integer anio) throws SisatException;
	
	/**
	 * Determinacion masiva del 2014
	 */
	public Integer getCategoriaUso2014(Integer tipoUsoId)throws Exception;

	public List<TipoUsoDTO> getCategoriaUso2014All()throws Exception;
	
	public Integer getCategoriaSeguridadTipoUso2014(Integer tipoUsoId)throws Exception;
	
	public List<DtZonaSeguridadUso2014> getAllDtZonaSeguridadUso2014(Integer zonaSeguridadId,Integer periodo)throws Exception;
	
	public List<TipoUsoDTO> getCategoriaSeguridadTipoUso2014All()throws Exception;
	
	public List<DtZonaSeguridadUso2014> getAllDtZonaSeguridadUso2014All()throws Exception;
	
	public abstract List<DeudaValoresDTO> getValorCoactiva(Integer codigo,String anio,Integer tipo,Integer determinacion) throws Exception;
	public abstract List<DeudaValoresDTO> getValorArbitrios(Integer codigo,Integer anio,Integer determinacion) throws Exception;
	public List<DeudaValoresDTO> getDeudaValoresArbitrios(int determinacionId)throws Exception;
	public Integer actualizarEstadoDeterminacion(Integer determinacion,String estado)throws Exception;
	public Integer guardarDeterminacionVariacion(DtDeterminacionValor variacion)throws Exception;
	public List<DtDeterminacion> getAllDtDeterminacionArbitriosSubconcepto(Integer predioId,Integer personaId,Integer periodo,Integer conceptoId,Integer subconceptoId)throws Exception;
	public DatosDeterminacionPredialValoresDTO getMontoDeterminacionValor(int persona,int concepto,int anio)throws Exception;
	public Integer actualizarDiffDeterminacion(Integer determinacion,BigDecimal diferencia)throws Exception;
	public DeudaPagosPredialDTO getDeudaValoresPagos(int persona,int anio,int determinacionId)throws Exception;
	public List<DatosDeterminacionPredialValoresDTO> getDeterminacionValores(int persona,int concepto,int anio,int determinacion) throws Exception;
	public void inputarDeudaSinValor(int deterPreviaId, int deterId) throws SisatException;
	public DtDeterminacion getMinDeterminacion(Integer personaId,Integer periodo,Integer concepto,String estado)throws Exception;
	public List<DeudaValoresDTO> getDeudaValoresRd(int persona,int concepto, int anio)throws Exception;
	public Integer actualizarFlagVariacionDeterminacion(Integer determinacion,String estado)throws Exception;
	
	/**
	 * Determinacion masiva del 2015
	 */
	public Integer getCategoriaUso2015(Integer tipoUsoId)throws Exception;

	public List<TipoUsoDTO> getCategoriaUso2015All()throws Exception;
	
	public Integer getCategoriaSeguridadTipoUso2015(Integer tipoUsoId)throws Exception;
	
	public List<DtZonaSeguridadUso2015> getAllDtZonaSeguridadUso2015(Integer zonaSeguridadId,Integer periodo)throws Exception;
	
	public List<TipoUsoDTO> getCategoriaSeguridadTipoUso2015All()throws Exception;
	
	public List<DtZonaSeguridadUso2015> getAllDtZonaSeguridadUso2015All()throws Exception;
	
	public int getUsoPensionista(Integer djId) throws Exception;
	public int getCantidadUsoPensionista(Integer djId) throws Exception;
	
	/**
	 * Determinacion masiva del 2016
	 */
	public Integer getCategoriaUso2016(Integer tipoUsoId,Integer periodo)throws Exception;

	public List<TipoUsoDTO> getCategoriaUso2016All(Integer periodo)throws Exception;
	
	public Integer getCategoriaSeguridadTipoUso2016(Integer tipoUsoId,Integer periodo)throws Exception;
	
	public List<DtMatrizSeguridad> getAllDtZonaSeguridadUso2016(Integer zonaSeguridadId,Integer periodo)throws Exception;
	
	public List<TipoUsoDTO> getCategoriaSeguridadTipoUso2016All(Integer periodo)throws Exception;
	
	public List<DtMatrizSeguridad> getAllDtZonaSeguridadUso2016All(Integer periodo)throws Exception;
	
	
}