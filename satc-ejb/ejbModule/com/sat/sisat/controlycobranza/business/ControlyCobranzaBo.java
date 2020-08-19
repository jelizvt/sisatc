package com.sat.sisat.controlycobranza.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.sql.DataSource;

import com.sat.sisat.coactiva.entity.CobranzaCoactiva;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.controlycobranza.dao.ControlyCobranzaBusinessDao;
import com.sat.sisat.controlycobranza.dao.FiscalizadorDao;
import com.sat.sisat.controlycobranza.dto.CarteraItemDTO;
import com.sat.sisat.controlycobranza.dto.CcRecAcumulada;
import com.sat.sisat.controlycobranza.dto.ConexionJasper;
import com.sat.sisat.controlycobranza.dto.FindCcActo;
import com.sat.sisat.controlycobranza.dto.FindCcCuotasAgrupadasLote;
import com.sat.sisat.controlycobranza.dto.FindCcLote;
import com.sat.sisat.controlycobranza.dto.FindCcLoteConcepto;
import com.sat.sisat.controlycobranza.dto.FindCcLoteDetalleActo;
import com.sat.sisat.controlycobranza.dto.FindCcLoteDetalleActoDeuda;
//import com.sat.sisat.controlycobranza.dto.FindCcLoteDetalleActoExp;
//import com.sat.sisat.controlycobranza.dto.FindCcLoteExigible;
import com.sat.sisat.controlycobranza.dto.FindCcLoteOrdenImpresion;
import com.sat.sisat.controlycobranza.dto.FindCcLoteSector;
import com.sat.sisat.controlycobranza.dto.FindCcLoteTipoPersona;
import com.sat.sisat.controlycobranza.dto.FindCcRec;
import com.sat.sisat.controlycobranza.dto.FindDetalleLoteDescargo;
import com.sat.sisat.controlycobranza.dto.FindLoteDescargo;
import com.sat.sisat.controlycobranza.dto.FindNotificacion;
import com.sat.sisat.controlycobranza.dto.FindPeriodoCuota;
import com.sat.sisat.controlycobranza.dto.FindPersonaDescargo;
import com.sat.sisat.controlycobranza.dto.MpFiscalizador;
import com.sat.sisat.controlycobranza.dto.MpFiscalizadorArea;
import com.sat.sisat.controlycobranza.dto.MpFiscalizadorDto;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.notificaciones.dto.NotificacionesDTO;
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
import com.sat.sisat.persistence.entity.CcReclamo;
import com.sat.sisat.persistence.entity.CcTipoActo;
import com.sat.sisat.persistence.entity.CcTipoAgrupamiento;
import com.sat.sisat.persistence.entity.CcTipoLote;
import com.sat.sisat.persistence.entity.CcTipoOrdenImpresion;
import com.sat.sisat.persistence.entity.DtFechaVencimiento;
import com.sat.sisat.persistence.entity.GnConcepto;
import com.sat.sisat.persistence.entity.GnSector;
import com.sat.sisat.persistence.entity.GnSubconcepto;
import com.sat.sisat.persistence.entity.NoDetalleMasivaDigiNotif;
import com.sat.sisat.persistence.entity.NoDetalleMasivaNotificacion;
import com.sat.sisat.persistence.entity.NoMasivaDigiNotif;
import com.sat.sisat.persistence.entity.NoMasivaNotificacion;
import com.sat.sisat.persistence.entity.NoMotivoNotificacion;
import com.sat.sisat.persistence.entity.NoNotificacion;
import com.sat.sisat.persistence.entity.NoNotificador;
import com.sat.sisat.persistence.entity.NoRelacionPersona;
import com.sat.sisat.persistence.entity.PaDocuAnexo;
import com.sat.sisat.persistence.entity.RvFiscalizacionInspeccion;
import com.sat.sisat.persistence.entity.RvFiscalizacionNotificacion;
import com.sat.sisat.persistence.entity.SgUsuario;
import com.sat.sisat.predial.business.BaseBusiness;
import com.sat.sisat.predial.dto.FindPersona;
import com.sat.sisat.reportes.dto.ReporteNotificacionDTO;

@Stateless
public class ControlyCobranzaBo extends BaseBusiness implements ControlyCobranzaBoRemote {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ControlyCobranzaBusinessDao service;

	public ControlyCobranzaBusinessDao getService() {
		return this.service;
	}

	@PostConstruct
	public void initialize() {
		this.service = new ControlyCobranzaBusinessDao();
		setDataManager(this.service);

		// sd = new FiscalizadorDao(em, ds);
	}

	public List<DtFechaVencimiento> getAllMpCuotasAnio(Integer conceptoId, Integer periodo) throws Exception {
		return getService().getAllMpCuotasAnio(conceptoId, periodo);
	}

	public List<DtFechaVencimiento> getAllMpCuotasxLoteIngresadas(Integer loteId) throws Exception {
		return getService().getAllMpCuotasxLoteIngresadas(loteId);
	}

	public List<FindCcLoteDetalleActo> getAllFindCcLotePreliminar(Integer lote_id, Integer TipoActoId)
			throws Exception {
		return getService().getAllFindCcLotePreliminar(lote_id, TipoActoId);
	}

	public List<FindCcLoteDetalleActo> getAllFindCcLotePreliminarRD(Integer personaId, Integer periodo)
			throws Exception {
		return getService().getAllFindCcLotePreliminarRD(personaId, periodo);
	}

	public List<FindCcLoteDetalleActo> getAllFindPreliminarRsNoPecuniariaUbic() throws Exception {
		return getService().getAllFindPreliminarRsNoPecuniariaUbic();
	}

	public List<FindCcLoteDetalleActo> getAllPreliminarRsNoPecSn() throws Exception {
		return getService().getAllPreliminarRsNoPecSn();
	}

	public List<FindCcLoteDetalleActo> getAllPreliminarRsNoPecOtros() throws Exception {
		return getService().getAllPreliminarRsNoPecOtros();
	}

	public List<FindCcLoteDetalleActo> getAllFindCcLotePreliminarRsSn() throws Exception {
		return getService().getAllFindCcLotePreliminarRsSn();
	}

	public List<FindCcLoteDetalleActo> getAllFindCcLotePreliminarRsOtros() throws Exception {
		return getService().getAllFindCcLotePreliminarRsOtros();
	}

	public List<FindCcLoteDetalleActo> getAllFindCcLotePreliminarRsUbicables() throws Exception {
		return getService().getAllFindCcLotePreliminarRsUbicables();
	}

	public List<FindCcLoteDetalleActo> getAllFindCcLotePreliminarOpVehicular(Integer conceptoId, Integer anio,
			String cuotas, BigDecimal montoMin, BigDecimal montoMax, Integer personaId) throws Exception {
		return getService().getAllFindCcLotePreliminarOpVehicular(conceptoId, anio, cuotas, montoMin, montoMax,
				personaId);
	}

	public List<FindCcLoteDetalleActo> getAllFindCcLotePreliminarOpVehicularSN(Integer conceptoId, Integer anio,
			String cuotas, BigDecimal montoMin, BigDecimal montoMax, Integer personaId) throws Exception {
		return getService().getAllFindCcLotePreliminarOpVehicularSN(conceptoId, anio, cuotas, montoMin, montoMax,
				personaId);
	}

	public List<FindCcLoteDetalleActo> getAllFindCcLotePreliminarOpVehicularOtros(Integer conceptoId, Integer anio,
			String cuotas, BigDecimal montoMin, BigDecimal montoMax, Integer personaId) throws Exception {
		return getService().getAllFindCcLotePreliminarOpVehicularOtros(conceptoId, anio, cuotas, montoMin, montoMax,
				personaId);
	}

	public List<FindCcLoteDetalleActo> getAllFindCcLotePreliminarOpPredial(Integer conceptoId, Integer anio,
			String cuotas, BigDecimal montoMin, BigDecimal montoMax, Integer personaId) throws Exception {
		return getService().getAllFindCcLotePreliminarOpPredial(conceptoId, anio, cuotas, montoMin, montoMax,
				personaId);
	}

	public List<FindCcLoteDetalleActo> getAllFindCcLotePreliminarOpPredialSn(Integer conceptoId, Integer anio,
			String cuotas, BigDecimal montoMin, BigDecimal montoMax, Integer personaId) throws Exception {
		return getService().getAllFindCcLotePreliminarOpPredialSn(conceptoId, anio, cuotas, montoMin, montoMax,
				personaId);
	}

	public List<FindCcLoteDetalleActo> getAllFindCcLotePreliminarOpPredialOtros(Integer conceptoId, Integer anio,
			String cuotas, BigDecimal montoMin, BigDecimal montoMax, Integer personaId) throws Exception {
		return getService().getAllFindCcLotePreliminarOpPredialOtros(conceptoId, anio, cuotas, montoMin, montoMax,
				personaId);
	}

	public List<FindCcLoteDetalleActo> getAllFindCcLoteFinal(Integer lote_id, Integer TipoActoId) throws Exception {
		return getService().getAllFindCcLoteFinal(lote_id, TipoActoId);
	}

	public List<FindCcLoteDetalleActo> getAllFindCcLoteFinalRD(Integer lote_id) throws Exception {
		return getService().getAllFindCcLoteFinalRD(lote_id);
	}

	public List<FindCcLoteDetalleActo> getAllFindCcLoteFinalRS(Integer lote_id) throws Exception {
		return getService().getAllFindCcLoteFinalRS(lote_id);
	}

	public List<FindCcLoteDetalleActo> getAllFindCcLoteFinalOP(Integer lote_id) throws Exception {
		return getService().getAllFindCcLoteFinalOP(lote_id);
	}

	public int actualizaEstadoLote(Integer loteId, String estado) throws Exception {
		return getService().actualizaEstadoLote(loteId, estado);
	}

	public List<CcLoteCuota> getAllFindCcLoteCuota(Integer lote_id) throws Exception {
		return getService().getAllFindCcLoteCuota(lote_id);
	}

	public List<FindCcLoteSector> getAllFindCcLoteSector(Integer lote_id) throws Exception {
		return getService().getAllFindCcLoteSector(lote_id);
	}

	public List<FindCcLoteTipoPersona> getAllFindCcLoteTipoPersona(Integer lote_id) throws Exception {
		return getService().getAllFindCcLoteTipoPersona(lote_id);
	}

	public List<FindCcLoteConcepto> getAllFindCcLoteConcepto(Integer lote_id) throws Exception {
		return getService().getAllFindCcLoteConcepto(lote_id);
	}

	public FindPersona getSectorIdByPersonaId(Integer personaId) throws Exception {
		return getService().getSectorIdByPersonaId(personaId);
	}

	public FindPersona getSectorIdByPersonaInfractorId() throws Exception {
		return getService().getSectorIdByPersonaInfractorId();
	}

	/****
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	public List<CcTipoLote> getAllCcTipoLote() throws Exception {
		return getService().getAllCcTipoLote();
	}

	public List<CcTipoActo> getAllCcTipoActo(Integer cctipolote_id) throws Exception {
		return getService().getAllCcTipoActo(cctipolote_id);
	}

	public List<GnConcepto> getAllGnConcepto(Integer cctipoacto_id) throws Exception {
		return getService().getAllGnConcepto(cctipoacto_id);
	}

	public List<GnSubconcepto> getAllGnSubConcepto(Integer gnconcepto_id) throws Exception {
		return getService().getAllGnSubConcepto(gnconcepto_id);
	}

	public List<GnSector> getAllGnSector() throws Exception {
		return getService().getAllGnSector();
	}

	public void edit(CcLote cclote) throws Exception {
		em.merge(cclote);
	}

	public void editPaDocuAnexo(PaDocuAnexo obj) throws Exception {
		em.merge(obj);
	}

	public CcLote create(CcLote cclote) throws Exception {
		cclote.setFechaRegistro(DateUtil.getCurrentDate());
		cclote.setUsuarioId(getUser().getUsuarioId());
		cclote.setTerminal(getUser().getTerminal());
		return (CcLote) getService().create(cclote);
	}

	public CcLoteActo create(CcLoteActo ccloteActo) throws Exception {
		ccloteActo.setFechaRegistro(DateUtil.getCurrentDate());
		ccloteActo.setUsuarioId(getUser().getUsuarioId());
		ccloteActo.setTerminal(getUser().getTerminal());
		return getService().create(ccloteActo);
	}

	public void create(CcLoteSector ccloteSector) throws Exception {
		ccloteSector.setFechaRegistro(DateUtil.getCurrentDate());
		ccloteSector.setUsuarioId(getUser().getUsuarioId());
		ccloteSector.setTerminal(getUser().getTerminal());
		getService().create(ccloteSector);
	}

	public void create(CcLoteConcepto ccloteConcepto) throws Exception {
		ccloteConcepto.setFechaRegistro(DateUtil.getCurrentDate());
		ccloteConcepto.setUsuarioId(getUser().getUsuarioId());
		ccloteConcepto.setTerminal(getUser().getTerminal());
		getService().create(ccloteConcepto);
	}

	public void create(CcLoteTipoPersona ccloteTipoPersona) throws Exception {
		ccloteTipoPersona.setFechaRegistro(DateUtil.getCurrentDate());
		ccloteTipoPersona.setUsuarioId(getUser().getUsuarioId());
		ccloteTipoPersona.setTerminal(getUser().getTerminal());
		getService().create(ccloteTipoPersona);
	}

	public void create(CcLoteCuota ccloteCuota) throws Exception {
		ccloteCuota.setFechaRegistro(DateUtil.getCurrentDate());
		ccloteCuota.setUsuarioId(getUser().getUsuarioId());
		ccloteCuota.setTerminal(getUser().getTerminal());
		getService().create(ccloteCuota);
	}

	public void create(CcLoteFirma ccloteFirma) throws Exception {
		ccloteFirma.setFechaRegistro(DateUtil.getCurrentDate());
		ccloteFirma.setUsuarioId(getUser().getUsuarioId());
		ccloteFirma.setTerminal(getUser().getTerminal());
		getService().create(ccloteFirma);
	}

	public void create(CcLoteTipoOrdenImpresion ccloteTipoOrdenImpresion) throws Exception {
		ccloteTipoOrdenImpresion.setFechaRegistro(DateUtil.getCurrentDate());
		ccloteTipoOrdenImpresion.setUsuarioId(getUser().getUsuarioId());
		ccloteTipoOrdenImpresion.setTerminal(getUser().getTerminal());
		getService().create(ccloteTipoOrdenImpresion);
	}

	public CcLoteSchedule create(CcLoteSchedule ccLoteSchedule) throws Exception {
		ccLoteSchedule.setFechaRegistro(DateUtil.getCurrentDate());
		ccLoteSchedule.setUsuarioId(getUser().getUsuarioId());
		ccLoteSchedule.setTerminal(getUser().getTerminal());
		return getService().create(ccLoteSchedule);
	}

	public void create(NoNotificacion notificacion) throws Exception {
		notificacion.setFechaRegistro(DateUtil.getCurrentDate());
		notificacion.setUsuarioId(getUser().getUsuarioId());
		notificacion.setTerminal(getUser().getTerminal());
		getService().create(notificacion);
	}

	public void create(RvFiscalizacionNotificacion notificacion) throws Exception {
		notificacion.setFechaRegistro(DateUtil.getCurrentDate());
		notificacion.setUsuarioId(getUser().getUsuarioId());
		notificacion.setTerminal(getUser().getTerminal());
		getService().create(notificacion);
	};

	public void create(RvFiscalizacionInspeccion notificacion) throws Exception {
		notificacion.setFechaRegistro(DateUtil.getCurrentDate());
		notificacion.setUsuarioId(getUser().getUsuarioId());
		notificacion.setTerminal(getUser().getTerminal());
		getService().create(notificacion);
	}

	public List<FindCcLote> getAllFindCcLote(String nroLote, String fechaRegistro, String estadolote,
			Integer tipoCobranza) throws Exception {
		return getService().getAllFindCcLote(nroLote, fechaRegistro, estadolote, tipoCobranza);
	}

	public List<FindCcLote> getAllFindCcLoteRS(String nroLote, String fechaRegistro, String estadolote,
			Integer tipoCobranza) throws Exception {
		return getService().getAllFindCcLoteRS(nroLote, fechaRegistro, estadolote, tipoCobranza);
	}

	public List<FindCcLote> getAllFindCcLoteRsNoPecuniaria(String nroLote) throws Exception {
		return getService().getAllFindCcLoteRsNoPecuniaria(nroLote);
	}

	public List<FindCcLote> getAllFindCcLoteOP(String nroLote, String fechaRegistro, String estadolote,
			Integer tipoCobranza) throws Exception {
		return getService().getAllFindCcLoteOP(nroLote, fechaRegistro, estadolote, tipoCobranza);
	}

	public List<FindCcCuotasAgrupadasLote> getAllFindCuotasAgrupadasxLote(Integer loteId) throws Exception {
		return getService().getAllFindCuotasAgrupadasxLote(loteId);
	}

	public List<FindCcLote> getAllFindCcLoteContrib(Integer loteId, Integer personaId, Integer tipoCobranza)
			throws Exception {
		return getService().getAllFindCcLoteContrib(loteId, personaId, tipoCobranza);
	}

	public GnSubconcepto findGnSubConcepto(Integer gnconcepto_id, String descripcion) throws Exception {
		return getService().findGnSubConcepto(gnconcepto_id, descripcion);
	}

	public List<FindPeriodoCuota> findDtPeriodoCuota(Integer conceptoId, Integer[] lstGnSubConceptoSeleccionados,
			Integer tipoActoId) throws Exception {
		return getService().findDtPeriodoCuota(conceptoId, lstGnSubConceptoSeleccionados, tipoActoId);
	}

	public List<CcTipoOrdenImpresion> getAllCcTipoOrdenImpresion() throws Exception {
		return getService().getAllCcTipoOrdenImpresion();
	}

	public FindCcLote getFindCcLote(Integer lote_id) throws Exception {
		return getService().findCcLote(lote_id);
	}

	public CcLote findCcLote(Object pk) throws Exception {
		return (CcLote) em.find(CcLote.class, pk);
	}

	public List<FindCcLoteConcepto> getAllFindCcLoteConcepto(Integer nro_lote, Integer anno) throws Exception {
		return getService().getAllFindCcLoteConcepto(nro_lote, anno);
	}

	public List<CcTipoAgrupamiento> getAllCcTipoAgrupamiento() throws Exception {
		return getService().getAllCcTipoAgrupamiento();
	}

	public List<SgUsuario> getAllSgUsuario() throws Exception {
		return getService().getAllSgUsuario();
	}

	public List<FindCcLoteOrdenImpresion> getAllCcLoteOrdenImpresion(Integer lote_id) throws Exception {
		return getService().getAllCcLoteOrdenImpresion(lote_id);
	}

	public List<FindCcLoteDetalleActoDeuda> getAllFindCcLoteDetalleActoDeuda(Integer lote_id, Integer acto_id,
			String tipoLoteGeneracion) throws Exception {
		return getService().getAllFindCcLoteDetalleActoDeuda(lote_id, acto_id, tipoLoteGeneracion);
	}

	public int actualizarEstadoLote(Integer loteId, String tipo_lote_generacion, String flag_generacion)
			throws Exception {
		return getService().actualizarEstadoLote(loteId, tipo_lote_generacion, flag_generacion);
	}

	public int darBajaSchedule(String estado, Integer loteId) throws Exception {
		return getService().darBajaSchedule(estado, loteId);
	}

	public List<FindCcLote> getAllFindCcLoteImprimir() throws Exception {
		return getService().getAllFindCcLoteImprimir();
	}

	public int terminarImpresionLote(Integer loteId, Integer anio) throws Exception {
		return getService().terminarImpresionLote(loteId, anio);
	}

	public int imprimirLote(Integer loteId, Integer anio) throws Exception {
		return getService().imprimirLote(loteId, anio);
	}

	public String rutaimpresion(Integer tipoActoId) throws Exception {
		return getService().rutaimpresion(tipoActoId);
	}

	public int registrarLoteConceptoTodos(Integer lote_id, String agrupadoCuota, String agrupadoBien) throws Exception {
		return getService().registrarLoteConceptoTodos(lote_id, agrupadoCuota, agrupadoBien, getUser().getUsuarioId());
	}

	public int registrarLoteCuotaTodos(Integer lote_id) throws Exception {
		return getService().registrarLoteCuotaTodos(lote_id, getUser().getUsuarioId());
	}

	public List<FindCcActo> getAllFindCcActo(String nroLote, String nroActoRec, Integer persona_id, Integer tipoLote)
			throws Exception {
		return getService().getAllFindCcActo(nroLote, nroActoRec, persona_id, tipoLote);
	}

	public List<NoMotivoNotificacion> getAlNoMotivoNotificacion(Integer flagUbicacion) throws Exception {
		return getService().getAlNoMotivoNotificacion(flagUbicacion);
	}

	public List<NoNotificador> getAllNotificador() throws Exception {
		return getService().getAllNotificador();
	}

	public List<NoRelacionPersona> getAlNoRelacionPersona() throws Exception {
		return getService().getAlNoRelacionPersona();
	}

	public int actualizarActo(NoNotificacion notificacion) throws Exception {
		return getService().actualizarActo(notificacion);
	}

	public int actualizarRequerimientoVehicular(RvFiscalizacionNotificacion notificacion) throws Exception {
		return getService().actualizarRequerimientoVehicular(notificacion);
	};

	public FindCcActo findCcActo(String flag_cc, Integer acto_id, Integer persona_id) throws Exception {
		return getService().findCcActo(flag_cc, acto_id, persona_id);
	}

	public List<CcFirmante> findCcFirmantes(Integer tipo_acto_id, Integer concepto_id) throws Exception {
		return getService().findCcFirmantes(tipo_acto_id, concepto_id);
	}

	public List<CcFirmante> findCcFirmantesRS(Integer tipo_acto_id) throws Exception {
		return getService().findCcFirmantesRS(tipo_acto_id);
	}

	public List<CcFirmante> findCcFirmantesREC(Integer tipo_acto_id) throws Exception {
		return getService().findCcFirmantesREC(tipo_acto_id);
	}

	public List<CcFirmante> getAllCcFirmantes(Integer lote_id) throws Exception {
		return getService().getAllCcFirmantes(lote_id);
	}

	public List<FindCcRec> getAllFindCcLoteRec(Integer lote_id, Integer TipoActoId, String tipoLoteGeneracion)
			throws Exception {
		return getService().getAllFindCcLoteRec(lote_id, TipoActoId, tipoLoteGeneracion);
	}

	public List<FindCcActo> getAllCcActoRec(Integer lote_id, Integer rec_id, String tipoLoteGeneracion)
			throws Exception {
		return getService().getAllCcActoRec(lote_id, rec_id, tipoLoteGeneracion);
	}

	public List<PaDocuAnexo> findCcDocuAnexoLote(Integer lote_id) throws Exception {
		return getService().findCcDocuAnexoLote(lote_id);
	}

	public Integer notificarMasivaId() throws Exception {
		return getService().ObtenerCorrelativoTabla("no_masiva_notificacion", 1);
	}

	public String notificarMasiva(String directorio, String nombreArchivo, Integer notificarMasivaId) throws Exception {
		Integer usuarioId = getUser().getUsuarioId();
		String terminal = getUser().getTerminal();
		return getService().notificarMasiva(directorio, nombreArchivo, notificarMasivaId, usuarioId, terminal);
	}

	public NoMasivaNotificacion findNoMasivaNotificacion(Object pk) throws Exception {
		return (NoMasivaNotificacion) em.find(NoMasivaNotificacion.class, pk);
	}

	public ArrayList<NoDetalleMasivaNotificacion> getAllNoDetalleMasivaNotificacion(Integer noMasivaNotificacionId)
			throws Exception {
		return getService().getAllNoDetalleMasivaNotificacion(noMasivaNotificacionId);
	}

	public List<FindNotificacion> getAllFindNoNotificacion(Integer noMasivaNotificacionId) throws Exception {
		return getService().getAllFindNoNotificacion(noMasivaNotificacionId);
	}

	public NotificacionesDTO findNoNotificacionDTO(String nroDocumento) throws Exception {
		return getService().findNoNotificacionDTO(nroDocumento);
	}

	public NoNotificador findNoNotificador(Object pk) throws Exception {
		return (NoNotificador) em.find(NoNotificador.class, pk);
	}

	public int actualizarNotificacionContenId(Integer notificacionId, Long contenId) throws Exception {
		return getService().actualizarNotificacionContenId(notificacionId, contenId);
	}

	public NoMasivaDigiNotif create(NoMasivaDigiNotif noMasivaDigiNotif) throws Exception {
		noMasivaDigiNotif.setFechaRegistro(DateUtil.getCurrentDate());
		noMasivaDigiNotif.setUsuarioId(getUser().getUsuarioId());
		noMasivaDigiNotif.setTerminal(getUser().getTerminal());
		return getService().create(noMasivaDigiNotif);
	}

	public NoDetalleMasivaDigiNotif create(NoDetalleMasivaDigiNotif noDetalleMasivaDigiNotif) throws Exception {
		noDetalleMasivaDigiNotif.setFechaRegistro(DateUtil.getCurrentDate());
		noDetalleMasivaDigiNotif.setUsuarioId(getUser().getUsuarioId());
		noDetalleMasivaDigiNotif.setTerminal(getUser().getTerminal());
		return getService().create(noDetalleMasivaDigiNotif);
	}

	public List<NoDetalleMasivaDigiNotif> getAllNoDetalleMasivaDigiNotif(Integer masivaDigiNotifId, String estado)
			throws Exception {
		return getService().getAllNoDetalleMasivaDigiNotif(masivaDigiNotifId, estado);
	}

	public void edit(NoDetalleMasivaDigiNotif noDetalleMasivaDigiNotif) throws Exception {
		em.merge(noDetalleMasivaDigiNotif);
	}

	public NoMasivaDigiNotif find(Object pk) throws Exception {
		return (NoMasivaDigiNotif) em.find(NoMasivaDigiNotif.class, pk);
	}

	public void edit(NoMasivaDigiNotif noMasivaDigiNotif) throws Exception {
		noMasivaDigiNotif.setFechaRegistro(DateUtil.getCurrentDate());
		noMasivaDigiNotif.setUsuarioId(getUser().getUsuarioId());
		noMasivaDigiNotif.setTerminal(getUser().getTerminal());
		em.merge(noMasivaDigiNotif);
	}

	public List<CcRecAcumulada> findRecAcumulables() throws Exception {
		return getService().findRecAcumulables();
	}

	public List<CcRecAcumulada> findRecAcumulables(Integer personaId) throws Exception {
		return getService().findRecAcumulables(personaId);
	}

	public String acumularRecs(Integer persona_id, String recIds) throws Exception {
		Integer usuarioId = getUser().getUsuarioId();
		String terminal = getUser().getTerminal();
		Integer annoRec = DateUtil.getAnioActual();
		return getService().acumularRecs(persona_id, recIds, annoRec, usuarioId, terminal);
	}

	/************************************************************************************************************************************************************************************************/
	/**
	 * 
	 * @param lote_id
	 * @param agrupadoCuota
	 * @param agrupadoBien
	 * @param usuarioId
	 * @return
	 */
	public int registrarActoRD(Integer lote_id, Integer personaId, Integer periodo) throws Exception {
		return getService().registrarActoRD(lote_id, personaId, periodo);
	}

	public int regeneraActoRD(Integer lote_id, Integer personaId, Integer periodo) throws Exception {
		return getService().regeneraActoRD(lote_id, personaId, periodo);
	}

	public int registrarActoRsSn(Integer lote_id, Integer periodo, Integer usuarioId, String terminal)
			throws Exception {
		return getService().registrarActoRsSn(lote_id, periodo, usuarioId, terminal);
	}

	public int registrarActoRsOtros(Integer lote_id, Integer periodo, Integer usuarioId, String terminal)
			throws Exception {
		return getService().registrarActoRsOtros(lote_id, periodo, usuarioId, terminal);
	}

	public int registrarActoRsUbicables(Integer lote_id, Integer periodo, Integer usuarioId, String terminal)
			throws Exception {
		return getService().registrarActoRsUbicables(lote_id, periodo, usuarioId, terminal);
	}

	public int registrarActoOP(Integer lote_id, Integer periodo, Integer conceptoId, String cuotas, Integer usuarioId,
			Integer personaId) throws Exception {
		return getService().registrarActoOP(lote_id, periodo, conceptoId, cuotas, usuarioId, personaId);
	}

	public int registrarActoOPInubicalbesSN(Integer lote_id, Integer periodo, Integer conceptoId, String cuotas,
			Integer usuarioId, Integer personaId) throws Exception {
		return getService().registrarActoOPInubicalbesSN(lote_id, periodo, conceptoId, cuotas, usuarioId, personaId);
	}

	public int registrarActoOPInubicablesOtros(Integer lote_id, Integer periodo, Integer conceptoId, String cuotas,
			Integer usuarioId, Integer personaId) throws Exception {
		return getService().registrarActoOPInubicablesOtros(lote_id, periodo, conceptoId, cuotas, usuarioId, personaId);
	}

	public int registrarActoOpUbicablesPredial(Integer lote_id, Integer periodo, Integer conceptoId, String cuotas,
			Integer usuarioId, Integer personaId) throws Exception {
		return getService().registrarActoOpUbicablesPredial(lote_id, periodo, conceptoId, cuotas, usuarioId, personaId);
	}

	public int registrarActoOpSnPredial(Integer lote_id, Integer periodo, Integer conceptoId, String cuotas,
			Integer usuarioId, Integer personaId) throws Exception {
		return getService().registrarActoOpSnPredial(lote_id, periodo, conceptoId, cuotas, usuarioId, personaId);
	}

	public int registrarActoOpOtrosPredial(Integer lote_id, Integer periodo, Integer conceptoId, String cuotas,
			Integer usuarioId, Integer personaId) throws Exception {
		return getService().registrarActoOpOtrosPredial(lote_id, periodo, conceptoId, cuotas, usuarioId, personaId);
	}

	public int registrarActo(Integer lote_id) throws Exception {
		return getService().registrarActo(lote_id);
	}

	/**
	 * RD de Arbitrios
	 */
	public List<FindCcLote> getAllFindCcLoteRDArbitrios(Integer loteId, Integer periodo, Integer tipoCobranza)
			throws Exception {
		return getService().getAllFindCcLoteRDArbitrios(loteId, periodo, tipoCobranza);
	}

	public List<FindCcLoteDetalleActo> getAllFindCcLotePreliminarRDArbitrios(Integer periodo, Double montoMinimo,
			Integer estadoDireccionId) throws Exception {
		return getService().getAllFindCcLotePreliminarRDArbitrios(periodo, montoMinimo, estadoDireccionId);
	}

	public int registrarActoRDArbitrios(Integer lote_id, Integer periodo, Double montoMinimo, Integer estadoDireccionId)
			throws Exception {
		return getService().registrarActoRDArbitrios(lote_id, periodo, montoMinimo, estadoDireccionId);
	}

	public List<FindCcLoteDetalleActo> getAllFindCcLoteFinalRDArbitrios(Integer lote_id) throws Exception {
		return getService().getAllFindCcLoteFinalRDArbitrios(lote_id);
	}

	public CcLote udpate(CcLote cclote) throws Exception {
		cclote.setFechaRegistro(DateUtil.getCurrentDate());
		cclote.setUsuarioId(getUser().getUsuarioId());
		cclote.setTerminal(getUser().getTerminal());
		return (CcLote) getService().update(cclote);
	}

	/**
	 * Esquelas
	 */
	public List<FindCcLoteDetalleActo> getAllFindCcLotePreliminarEsquelas(Integer periodoActual,
			Integer estadoDireccionId, Integer tipoRecordatorioId, Double montoMinimo) throws Exception {
		if (tipoRecordatorioId == 1) {
			return getService().getAllFindCcLotePreliminarEsquelasPorVencer(periodoActual, estadoDireccionId,
					montoMinimo);
		} else if (tipoRecordatorioId == 0) {
			return getService().getAllFindCcLotePreliminarEsquelasVencida(periodoActual, estadoDireccionId,
					montoMinimo);// Este posee descuento por resat
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<CcCarteraDeuda> obtenerCarteraDeudas() throws SisatException {

		List<CcCarteraDeuda> listCarteraDeudas = new ArrayList<CcCarteraDeuda>();

		try {
			Query q = this.em.createQuery("from CcCarteraDeuda c where c.estado = 1");

			listCarteraDeudas = q.getResultList();
		} catch (RuntimeException ex) {
			throw new SisatException(ex.getMessage());
		}

		return listCarteraDeudas;
	}

	public CcCarteraDeuda obtenerCcCarteraDeuda(Integer carteraDeudaId) throws SisatException {

		CcCarteraDeuda ccCarteraDeuda = null;

		try {
			ccCarteraDeuda = this.em.find(CcCarteraDeuda.class, carteraDeudaId);
		} catch (RuntimeException e) {
			throw new SisatException(e.getMessage());
		}

		return ccCarteraDeuda;
	}

	public List<CarteraItemDTO> obtenerCarteraItems(Integer carteraDeudaId) throws SisatException {

		return service.obtenerCarteraItems(carteraDeudaId);
	}

	/**
	 * Reclamos
	 */
	public List<FindCcActo> getAllFindCcActoReclamos(String nroActo, Integer personaId) throws Exception {
		return getService().getAllFindCcActoReclamos(nroActo, personaId);
	}

	public void create(CcReclamo reclamo) throws Exception {
		if (reclamo.getReclamoId() == Constante.RESULT_PENDING) {
			Integer reclamoId = getService().ObtenerCorrelativoTabla("cc_reclamo", 1);
			reclamo.setReclamoId(reclamoId);
			reclamo.setFechaRegistro(DateUtil.getCurrentDate());
			reclamo.setUsuarioId(getUser().getUsuarioId());
			reclamo.setTerminal(getUser().getTerminal());
			getService().create(reclamo);
		} else {
			reclamo.setFechaRegistro(DateUtil.getCurrentDate());
			reclamo.setUsuarioId(getUser().getUsuarioId());
			reclamo.setTerminal(getUser().getTerminal());
			getService().update(reclamo);
		}
	}

	public void actualizarActo(String esReclamado, CcReclamo reclamo) throws Exception {
		getService().actualizarActo(esReclamado, reclamo);
	}

	public void actualizarDeuda(String esReclamado, CcReclamo reclamo) throws Exception {
		getService().actualizarDeuda(esReclamado, reclamo);
	}

	/**
	 * Coactiva
	 */
	public List<CobranzaCoactiva> getAllImpuestoPredial2012(Integer personaId, String nroValor) throws Exception {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("p_persona_id", personaId);
		parameters.put("p_nro_valor", nroValor);
		return (ArrayList<CobranzaCoactiva>) (List) getService().findByNamedQuery("getCoactivaImpuestoPredial2012By",
				parameters);
	}

	public List<CobranzaCoactiva> getAllImpuestoPredial2013(Integer personaId, String nroValor) throws Exception {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("p_persona_id", personaId);
		parameters.put("p_nro_valor", nroValor);
		return (ArrayList<CobranzaCoactiva>) (List) getService().findByNamedQuery("getCoactivaImpuestoPredial2013By",
				parameters);
	}

	// ip2014
	public List<CobranzaCoactiva> getAllImpuestoPredial2014(Integer personaId, String nroValor) throws Exception {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("p_persona_id", personaId);
		parameters.put("p_nro_valor", nroValor);
		return (ArrayList<CobranzaCoactiva>) (List) getService().findByNamedQuery("getCoactivaImpuestoPredial2014By",
				parameters);
	}

	// ip2014-*-
	public List<CobranzaCoactiva> getAllImpuestoVehicula2012(Integer personaId, String nroValor) throws Exception {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("p_persona_id", personaId);
		parameters.put("p_nro_valor", nroValor);
		return (ArrayList<CobranzaCoactiva>) (List) getService().findByNamedQuery("getCoactivaImpuestoVehicula2012By",
				parameters);
	}

	public List<CobranzaCoactiva> getAllImpuestoVehicula2013(Integer personaId, String nroValor) throws Exception {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("p_persona_id", personaId);
		parameters.put("p_nro_valor", nroValor);
		return (ArrayList<CobranzaCoactiva>) (List) getService().findByNamedQuery("getCoactivaImpuestoVehicula2013By",
				parameters);
	}

	public List<CobranzaCoactiva> getAllImpuestoVehicula2014(Integer personaId) throws Exception {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("p_persona_id", personaId);
		// parameters.put("p_nro_valor", nroValor);
		return (ArrayList<CobranzaCoactiva>) (List) getService().findByNamedQuery("getCoactivaImpuestoVehicula2014By",
				parameters);
	}

	public List<CobranzaCoactiva> getAllMultasTransito2012(String papeleta, String nroValor, String placa)
			throws Exception {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("p_papeleta", papeleta);
		parameters.put("p_nro_valor", nroValor);
		parameters.put("p_placa", placa);
		return (ArrayList<CobranzaCoactiva>) (List) getService().findByNamedQuery("getCoactivaMultasTransito2012By",
				parameters);
	}

	public List<CobranzaCoactiva> getAllMultasTransito2013(String papeleta, String nroValor, String placa)
			throws Exception {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("p_papeleta", papeleta);
		parameters.put("p_nro_valor", nroValor);
		parameters.put("p_placa", placa);
		return (ArrayList<CobranzaCoactiva>) (List) getService().findByNamedQuery("getCoactivaMultasTransito2013By",
				parameters);
	}

	public List<CobranzaCoactiva> getAllMultasTransito2014(String papeleta, String nroValor, String placa)
			throws Exception {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("p_papeleta", papeleta);
		parameters.put("p_nro_valor", nroValor);
		parameters.put("p_placa", placa);
		return (ArrayList<CobranzaCoactiva>) (List) getService().findByNamedQuery("getCoactivaMultasTransito2014By",
				parameters);
	}

	public List<CobranzaCoactiva> getAllMultasTransito_2014(String papeleta, String placa, String nroValor)
			throws Exception {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("p_papeleta", papeleta);
		parameters.put("p_placa", placa);
		parameters.put("p_valor", nroValor);
		return (ArrayList<CobranzaCoactiva>) (List) getService().findByNamedQuery("getCoactivaMultaTransito2014By",
				parameters);
	}

	public List<CobranzaCoactiva> getAllImpuestoPredial2012() throws Exception {
		return (ArrayList<CobranzaCoactiva>) (List) getService().findByNamedQuery("getAllCoactivaImpuestoPredial2012");
	}

	public List<CobranzaCoactiva> getAllImpuestoPredial2013() throws Exception {
		return (ArrayList<CobranzaCoactiva>) (List) getService().findByNamedQuery("getAllCoactivaImpuestoPredial2013");
	}

	// ip2014
	public List<CobranzaCoactiva> getAllImpuestoPredial2014() throws Exception {
		return (ArrayList<CobranzaCoactiva>) (List) getService().findByNamedQuery("getAllCoactivaImpuestoPredial2014");
	}

	// ip2014-*-
	public List<CobranzaCoactiva> getAllImpuestoVehicula2012() throws Exception {
		return (ArrayList<CobranzaCoactiva>) (List) getService().findByNamedQuery("getAllCoactivaImpuestoVehicula2012");
	}

	public List<CobranzaCoactiva> getAllImpuestoVehicula2013() throws Exception {
		return (ArrayList<CobranzaCoactiva>) (List) getService().findByNamedQuery("getAllCoactivaImpuestoVehicula2013");
	}

	public List<CobranzaCoactiva> getAllImpuestoVehicula2014() throws Exception {
		return (ArrayList<CobranzaCoactiva>) (List) getService().findByNamedQuery("getAllCoactivaImpuestoVehicula2014");
	}

	public List<CobranzaCoactiva> getAllMultasTransito2012() throws Exception {
		return (ArrayList<CobranzaCoactiva>) (List) getService().findByNamedQuery("getAllCoactivaMultasTransito2012");
	}

	public List<CobranzaCoactiva> getAllMultasTransito2013() throws Exception {
		return (ArrayList<CobranzaCoactiva>) (List) getService().findByNamedQuery("getAllCoactivaMultasTransito2013");
	}

	public List<CobranzaCoactiva> getAllMultasTransito2014() throws Exception {
		return (ArrayList<CobranzaCoactiva>) (List) getService().findByNamedQuery("getAllCoactivaMultasTransito2014");
	}

	public List<CobranzaCoactiva> getAllMultasTransito_2014() throws Exception {
		return (ArrayList<CobranzaCoactiva>) (List) getService().findByNamedQuery("getAllCoactivaMultaTransito2014");
	}

	public <T> T update(T t) throws Exception {
		return getService().update(t);
	}

	public int registrarActoRsNoPecuniariasUbicables(Integer lote_id, Integer periodo, Integer responsable,
			String terminal) throws Exception {
		return getService().registrarActoRsNoPecuniariasUbicables(lote_id, periodo, responsable, terminal);
	}

	public int registrarActoRsNoPecuniariasSinNumero(Integer lote_id, Integer periodo, Integer responsable,
			String terminal) throws Exception {
		return getService().registrarActoRsNoPecuniariasSinNumero(lote_id, periodo, responsable, terminal);
	}

	public int registrarActoRsNoPecuniariasOtros(Integer lote_id, Integer periodo, Integer responsable, String terminal)
			throws Exception {
		return getService().registrarActoRsNoPecuniariasOtros(lote_id, periodo, responsable, terminal);
	}

	/**
	 * Generación de RD's Vehiculares
	 */
	public List<FindCcLoteDetalleActo> getAllFindCcLotePreliminarRDVehicular(Integer loteId) throws Exception {
		return getService().getAllFindCcLotePreliminarRDVehicular(loteId);
	}

	public int registrarActoRDVehicular(Integer lote_id) throws Exception {
		return getService().registrarActoRDVehicular(lote_id);
	}

	public List<FindCcLoteDetalleActo> getAllFindCcLoteFinalRDVehicular(Integer lote_id) throws Exception {
		return getService().getAllFindCcLoteFinalRDVehicular(lote_id);
	}

	public int registrarActoRDVehicularIndividual(Integer loteId, Integer personaId, Integer reqId,
			Integer determinacionId) throws Exception {
		return getService().registrarActoRDVehicularIndividual(loteId, personaId, reqId, determinacionId);
	}

	public List<FindCcLote> getAllFindCcLoteRDVehicular() throws Exception {
		return getService().getAllFindCcLoteRDVehicular();
	}

	
	public ArrayList<GnSector> getAllSector() throws Exception {
		return getService().getAllSector();
	}
	
	public List<FindCcLoteDetalleActo> getAllFindCcLotePreliminarOpPredialGeneral(Integer conceptoId, Integer anio,
			String cuotas, BigDecimal montoMin, BigDecimal montoMax, Integer personaId, Integer sectorID,
			Integer tipoUbicacion,Integer usuarioID) throws Exception {

		return getService().getAllFindCcLotePreliminarOpPredialGeneral(conceptoId, anio, cuotas, montoMin, montoMax,
				personaId, sectorID, tipoUbicacion,usuarioID);
	}
	
	
	public Boolean registrarActoOpPredialGeneral  (Integer loteID, Integer usuarioID,String terminal)throws Exception {		
		return getService().registrarActoOpPredialGeneral  (loteID, usuarioID,terminal);
	}
	
	/**
	 * -=CRAMIREZ=-
	 */
	public List<FindPersonaDescargo> getAllPersonaDescargo(Integer persona_id, Date fecha_Inicio, Date fecha_Fin, int tipo) throws Exception {
        return getService().getAllPersonaDescargo(persona_id,fecha_Inicio,fecha_Fin, tipo);
    }
	
	public List<FindLoteDescargo> getAllLoteDescargo(Integer codigo, Integer usuario_id, Date fecha_registro) throws Exception {
        return getService().getAllLoteDescargo(codigo,usuario_id,fecha_registro);
    }
	
	public List<FindDetalleLoteDescargo> getAllDetalleLoteDescargo(Integer lote_descargo_id) throws Exception {
        return getService().getAllDetalleLoteDescargo(lote_descargo_id);
    }
	
	public void confirmarDescargo(Integer lote_descargo_id, Integer usuario_id, String terminal ,String deudas_id, Integer tipo_operacion, Integer personaId, Date fecha, Integer usuarioLote, String observaciones) throws Exception {
        getService().confirmarDescargo(lote_descargo_id,usuario_id,terminal,deudas_id,tipo_operacion,personaId,fecha,usuarioLote,observaciones);
    }
	
	public List<ReporteNotificacionDTO> getAllNotificaciones(Date fechaInicio, Date fechaFin)throws Exception {
        return getService().getAllNotificaciones(fechaInicio,fechaFin);
    }
	
}