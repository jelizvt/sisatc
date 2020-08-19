package com.sat.sisat.controlycobranza.business;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import com.sat.sisat.controlycobranza.dto.CarteraItemDTO;
import com.sat.sisat.controlycobranza.dto.FindCcActo;
import com.sat.sisat.controlycobranza.dto.FindCcCuotasAgrupadasLote;
import com.sat.sisat.controlycobranza.dto.FindCcLote;
import com.sat.sisat.controlycobranza.dto.FindCcLoteConcepto;
import com.sat.sisat.controlycobranza.dto.FindCcLoteDetalleActo;
import com.sat.sisat.controlycobranza.dto.FindCcLoteDetalleActoDeuda;
import com.sat.sisat.controlycobranza.dto.FindCcLoteOrdenImpresion;
import com.sat.sisat.controlycobranza.dto.FindCcLoteSector;
import com.sat.sisat.controlycobranza.dto.FindCcLoteTipoPersona;
import com.sat.sisat.controlycobranza.dto.FindDetalleLoteDescargo;
import com.sat.sisat.controlycobranza.dto.FindLoteDescargo;
import com.sat.sisat.controlycobranza.dto.FindPeriodoCuota;
import com.sat.sisat.controlycobranza.dto.FindPersonaDescargo;
import com.sat.sisat.controlycobranza.dto.MpFiscalizadorDto;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.persistence.entity.CcCarteraDeuda;
import com.sat.sisat.persistence.entity.CcFirmante;
import com.sat.sisat.persistence.entity.CcLote;
import com.sat.sisat.persistence.entity.CcLoteActo;
import com.sat.sisat.persistence.entity.CcLoteConcepto;
import com.sat.sisat.persistence.entity.CcLoteCuota;
import com.sat.sisat.persistence.entity.CcLoteFirma;
import com.sat.sisat.persistence.entity.CcLoteSchedule;
import com.sat.sisat.persistence.entity.CcLoteSector;
import com.sat.sisat.persistence.entity.CcLoteTipoOrdenImpresion;
import com.sat.sisat.persistence.entity.CcLoteTipoPersona;
import com.sat.sisat.persistence.entity.CcTipoActo;
import com.sat.sisat.persistence.entity.CcTipoAgrupamiento;
import com.sat.sisat.persistence.entity.CcTipoLote;
import com.sat.sisat.persistence.entity.CcTipoOrdenImpresion;
import com.sat.sisat.persistence.entity.DtFechaVencimiento;
import com.sat.sisat.persistence.entity.GnConcepto;
import com.sat.sisat.persistence.entity.GnSector;
import com.sat.sisat.persistence.entity.GnSubconcepto;
import com.sat.sisat.persistence.entity.NoMotivoNotificacion;
import com.sat.sisat.persistence.entity.NoNotificacion;
import com.sat.sisat.persistence.entity.NoNotificador;
import com.sat.sisat.persistence.entity.NoRelacionPersona;
import com.sat.sisat.persistence.entity.SgUsuario;
import com.sat.sisat.predial.dto.FindPersona;
import com.sat.sisat.reportes.dto.ReporteNotificacionDTO;

@Local
public interface ControlyCobranzaBoLocal {
	

	public abstract List<CcTipoLote> getAllCcTipoLote() throws Exception;

	public List<FindCcLoteDetalleActo> getAllFindPreliminarRsNoPecuniariaUbic() throws Exception;
	public List<FindCcLoteDetalleActo> getAllPreliminarRsNoPecSn() throws Exception;
	public List<FindCcLoteDetalleActo> getAllPreliminarRsNoPecOtros() throws Exception;
	
	public List<FindCcLoteDetalleActo> getAllFindCcLotePreliminarOpVehicular(Integer conceptoId,Integer anio,String cuotas,	BigDecimal montoMin,BigDecimal montoMax,Integer personaId) throws Exception;

	public abstract List<FindCcLote> getAllFindCcLoteOP(String nroLote,	String fechaRegistro,String estadolote,	Integer tipoCobranza) throws Exception;
	
	public List<NoNotificador> getAllNotificador() throws Exception;
	
	public List<FindCcLoteDetalleActo> getAllFindCcLotePreliminarOpPredial(Integer conceptoId,	Integer anio, String cuotas, BigDecimal montoMin, BigDecimal montoMax,Integer personaId) throws Exception;
	
	public List<FindCcLoteDetalleActo> getAllFindCcLotePreliminarOpPredialSn(Integer conceptoId,	Integer anio, String cuotas, BigDecimal montoMin, BigDecimal montoMax,Integer personaId) throws Exception;
	
	public List<FindCcLoteDetalleActo> getAllFindCcLotePreliminarOpPredialOtros(Integer conceptoId,	Integer anio, String cuotas, BigDecimal montoMin, BigDecimal montoMax,Integer personaId) throws Exception;
	
	public int registrarActoOPInubicalbesSN(Integer lote_id, Integer anio, Integer conceptoId, String cuotas, Integer usuarioId,Integer personaId)	throws Exception;
	
	public int registrarActoOPInubicablesOtros(Integer lote_id, Integer anio, Integer conceptoId, String cuotas, Integer usuarioId, Integer personaId)	throws Exception;
	
	public List<FindCcLoteDetalleActo> getAllFindCcLotePreliminarOpVehicularOtros(Integer conceptoId,	Integer anio, String cuotas, BigDecimal montoMin, BigDecimal montoMax,Integer personaId) throws Exception;
	
	public List<FindCcLoteDetalleActo> getAllFindCcLotePreliminarOpVehicularSN(Integer conceptoId,	Integer anio, String cuotas, BigDecimal montoMin, BigDecimal montoMax,Integer personaId) throws Exception;
	
	public int registrarActoRsOtros(Integer lote_id, Integer periodo, Integer responsable,String terminal) throws Exception;
	
	public List<FindCcLoteDetalleActo> getAllFindCcLotePreliminarRsOtros() throws Exception;
	
	public int registrarActoRsUbicables(Integer lote_id, Integer periodo, Integer responsable, String terminal) throws Exception;
	
	public List<FindCcLoteDetalleActo> getAllFindCcLotePreliminarRsUbicables() throws Exception;
	
	public abstract List<CcTipoActo> getAllCcTipoActo(Integer cctipolote_id) throws Exception;
	
	public abstract List<DtFechaVencimiento> getAllMpCuotasxLoteIngresadas(Integer loteId) throws Exception;

	public List<CcFirmante> findCcFirmantesRS(Integer tipo_acto_id) throws Exception;

	public abstract List<DtFechaVencimiento> getAllMpCuotasAnio(Integer conceptoId, Integer periodo) throws Exception;

	public abstract List<GnConcepto> getAllGnConcepto(Integer cctipoacto_id) throws Exception;

	public abstract List<GnSubconcepto> getAllGnSubConcepto(Integer gnconcepto_id) throws Exception;

	public FindPersona getSectorIdByPersonaInfractorId() throws Exception;

	public List<FindCcLoteDetalleActo> getAllFindCcLotePreliminarRsSn() throws Exception;

	public abstract List<GnSector> getAllGnSector() throws Exception;

	public abstract List<FindCcLoteDetalleActo> getAllFindCcLoteFinalRS(Integer lote_id) throws Exception;

	public abstract void create(CcLote cclote) throws Exception;

	public abstract void create(CcLoteConcepto ccloteConcepto) throws Exception;

	public abstract void create(CcLoteTipoPersona ccloteTipoPersona) throws Exception;

	public abstract void create(CcLoteSector ccloteSector) throws Exception;

	public abstract void create(CcLoteActo ccloteActo) throws Exception;

	public abstract List<FindCcLote> getAllFindCcLote(String nroLote, String fechaRegistro, String estadolote)
			throws Exception;

	public abstract GnSubconcepto findGnSubConcepto(Integer gnconcepto_id, String descripcion) throws Exception;

	public abstract List<FindCcLote> getAllFindCcLote(String nroLote,
			String fechaRegistro,
			String estadolote,
			Integer tipoCobranza) throws Exception;

	public abstract void create(CcLoteCuota ccloteCuota) throws Exception;

	public abstract List<FindPeriodoCuota> findDtPeriodoCuota(Integer conceptoId,
			Integer[] lstGnSubConceptoSeleccionados,
			Integer tipoActoId) throws Exception;

	public abstract List<CcTipoOrdenImpresion> getAllCcTipoOrdenImpresion() throws Exception;

	public abstract FindCcLote findCcLote(Integer lote_id) throws Exception;

	public abstract CcLote findCcLote(Object pk) throws Exception;

	public abstract List<FindCcLoteConcepto> getAllFindCcLoteConcepto(Integer nro_lote, Integer anno) throws Exception;

	public abstract List<CcLoteCuota> getAllFindCcLoteCuota(Integer lote_id) throws Exception;

	public abstract List<FindCcLoteSector> getAllFindCcLoteSector(Integer lote_id) throws Exception;

	public abstract List<FindCcLoteTipoPersona> getAllFindCcLoteTipoPersona(Integer lote_id) throws Exception;

	public abstract List<CcTipoAgrupamiento> getAllCcTipoAgrupamiento() throws Exception;

	public abstract List<SgUsuario> getAllSgUsuario() throws Exception;

	public abstract void create(CcLoteFirma ccloteFirma) throws Exception;

	public abstract void create(CcLoteTipoOrdenImpresion ccloteTipoOrdenImpresion) throws Exception;

	public abstract List<FindCcLoteOrdenImpresion> getAllCcLoteOrdenImpresion(Integer lote_id) throws Exception;

	public abstract void create(CcLoteSchedule ccLoteSchedule) throws Exception;

	public abstract void create(NoNotificacion notificacion) throws Exception;

	public abstract List<FindCcLoteDetalleActo> getAllFindCcLotePreliminar(Integer lote_id) throws Exception;

	public abstract List<FindCcLoteDetalleActoDeuda> getAllFindCcLoteDetalleActoDeuda(Integer lote_id,
			Integer acto_id,
			String tipoLoteGeneracion) throws Exception;

	public abstract int actualizarEstadoLote(Integer loteId, String tipo_lote_generacion, String flag_generacion)
			throws Exception;

	public abstract int darBajaSchedule(String estado, Integer loteId) throws Exception;

	public abstract List<FindCcLoteDetalleActo> getAllFindCcLoteFinal(Integer lote_id) throws Exception;

	public abstract void edit(CcLote cclote) throws Exception;

	public abstract List<FindCcLote> getAllFindCcLoteImprimir() throws Exception;

	public abstract int terminarImpresionLote(Integer loteId, Integer anio) throws Exception;

	public abstract int imprimirLote(Integer loteId, Integer anio) throws Exception;

	public String rutaimpresion(Integer tipoActoId) throws Exception;

	public int registrarLoteConceptoTodos(Integer lote_id, String agrupadoCuota, String agrupadoBien);

	public int registrarLoteCuotaTodos(Integer lote_id);

//	public List<FindCcActo> getAllFindCcActo() throws Exception;
	
	public List<FindCcActo> getAllFindCcActo(String nroLote, String nroActoRec, Integer persona_id, Integer tipoLote)
			throws Exception;

	public List<NoMotivoNotificacion> getAlNoMotivoNotificacion(Integer flagUbicacion) throws Exception;

	public List<NoRelacionPersona> getAlNoRelacionPersona() throws Exception;

	public int actualizarActo(NoNotificacion notificacion) throws Exception;

	public FindCcActo findCcActo(Integer acto_id) throws Exception;

	public int registrarActoRsSn(Integer lote_id, Integer periodo, Integer usuarioId, String terminal) throws Exception;

	public int registrarActoOP(Integer lote_id, Integer anio, Integer conceptoId, String cuotas, Integer usuarioId,Integer personaId)
			throws Exception;

	public abstract List<FindCcLoteDetalleActo> getAllFindCcLoteFinalOP(Integer lote_id) throws Exception;

	public List<FindCcCuotasAgrupadasLote> getAllFindCuotasAgrupadasxLote(Integer lote_id) throws Exception;
	
	/**
	 * RD de arbitrios
	 */
	public List<FindCcLote> getAllFindCcLoteRDArbitrios(Integer loteId,Integer periodo,Integer tipoCobranza)	throws Exception;
	
	public List<FindCcLoteDetalleActo> getAllFindCcLotePreliminarRDArbitrios(Integer periodo,Double montoMinimo,Integer estadoDireccionId)throws Exception;
	
	public int registrarActoRDArbitrios(Integer lote_id,Integer periodo,Double montoMinimo,Integer estadoDireccionId)  throws Exception;
	
	public List<FindCcLoteDetalleActo> getAllFindCcLoteFinalRDArbitrios(Integer lote_id)	throws Exception;
	
	public CcLote udpate(CcLote cclote)throws Exception;
	
	/**
	 * Esquelas de notificacion 
	 */
	public List<FindCcLoteDetalleActo> getAllFindCcLotePreliminarEsquelas(Integer periodoActual,Integer estadoDireccionId,Integer tipoRecordatorioId,Double montoMinimo) throws Exception;

	
	public int registrarActoOpUbicablesPredial(Integer lote_id, Integer anio, Integer conceptoId, String cuotas, Integer usuarioId,Integer personaId)	throws Exception;
	
	public int registrarActoOpSnPredial(Integer lote_id, Integer anio, Integer conceptoId, String cuotas, Integer usuarioId,Integer personaId)	throws Exception;
	
	public int registrarActoOpOtrosPredial(Integer lote_id, Integer anio, Integer conceptoId, String cuotas, Integer usuarioId,Integer personaId)	throws Exception;
		
	public List<CcCarteraDeuda> obtenerCarteraDeudas() throws SisatException;
	
	public List<CarteraItemDTO> obtenerCarteraItems(Integer carteraDeudaId) throws SisatException;
	
	public abstract List<FindCcLote> getAllFindCcLoteRsNoPecuniaria(String nroLote) throws Exception;

	public int registrarActoRsNoPecuniariasUbicables(Integer lote_id, Integer periodo, Integer responsable,String terminal) throws Exception;
	
	public int registrarActoRsNoPecuniariasOtros(Integer lote_id, Integer periodo, Integer responsable,String terminal) throws Exception;
	
	public int registrarActoRsNoPecuniariasSinNumero(Integer lote_id, Integer periodo, Integer responsable,String terminal) throws Exception;
	
	/**
	 * Generación de RD's Vehiculares
	 */
	public List<FindCcLoteDetalleActo> getAllFindCcLotePreliminarRDVehicular(Integer loteId)throws Exception;
	
	public int registrarActoRDVehicular(Integer lote_id)  throws Exception;
	
	public List<FindCcLoteDetalleActo> getAllFindCcLoteFinalRDVehicular(Integer lote_id)throws Exception;
	
	public int registrarActoRDVehicularIndividual(Integer loteId,Integer personaId,Integer reqId,Integer determinacionId)  throws Exception;
	
	public List<FindCcLote> getAllFindCcLoteRDVehicular()throws Exception;
	
	/**
	 * -=CRAMIREZ=-
	 */
	public List<FindPersonaDescargo> getAllPersonaDescargo(Integer persona_id, Date fecha_Inicio, Date fecha_Fin,int tipo) throws Exception;
	public List<FindLoteDescargo> getAllLoteDescargo(Integer codigo, Integer usuario,Date fecha_registro) throws Exception;
	public List<FindDetalleLoteDescargo> getAllDetalleLoteDescargo(Integer lote_descargo_id) throws Exception;
	public void confirmarDescargo(Integer lote_descargo_id, Integer usuario_id,String terminal, String deudas_id, Integer tipo_operacion, Integer personaId, Date fecha, Integer usuarioLote, String observaciones) throws Exception;
	public List<ReporteNotificacionDTO> getAllNotificaciones(Date fechaInicio, Date fechaFin)throws Exception;
}
