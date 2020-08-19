package com.sat.sisat.cobranzacoactiva.business;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import com.sat.sisat.cobranzacoactiva.dto.TipoDocumentoDescargo;
import com.sat.sisat.cobranzacoactiva.dao.CobranzaCoactivaBusinessDao;
import com.sat.sisat.cobranzacoactiva.dto.CarteraExigibilidad;
import com.sat.sisat.cobranzacoactiva.dto.CoCartera;
import com.sat.sisat.cobranzacoactiva.dto.ControlExpediente;
import com.sat.sisat.cobranzacoactiva.dto.GeneracionMasivaRecDTO;
import com.sat.sisat.cobranzacoactiva.dto.DetalleCostas;
import com.sat.sisat.cobranzacoactiva.dto.EjecutorCoactivo;
import com.sat.sisat.cobranzacoactiva.dto.ExpedienteCoactivo;
import com.sat.sisat.cobranzacoactiva.dto.FindCcLoteDetalleActoExp;
import com.sat.sisat.cobranzacoactiva.dto.FindCcLoteExigible;
import com.sat.sisat.cobranzacoactiva.dto.FindCcRecHistorico;
import com.sat.sisat.cobranzacoactiva.dto.FindCcRecTipo;
import com.sat.sisat.cobranzacoactiva.dto.GestionCostas;
import com.sat.sisat.cobranzacoactiva.dto.GestionEventos;
import com.sat.sisat.cobranzacoactiva.dto.GestionValores;
import com.sat.sisat.cobranzacoactiva.dto.InformeTransferido;
import com.sat.sisat.cobranzacoactiva.dto.InformeTransferidoDetalle;
import com.sat.sisat.cobranzacoactiva.dto.ListadoArea;
import com.sat.sisat.cobranzacoactiva.dto.ListadoEstadoTransferencia;
import com.sat.sisat.cobranzacoactiva.dto.SituacionCartera;
import com.sat.sisat.cobranzacoactiva.dto.SituacionDeuda;
import com.sat.sisat.cobranzacoactiva.dto.SituacionExigibilidad;
import com.sat.sisat.common.dto.GenericDTO;
import com.sat.sisat.controlycobranza.dto.FindCcLote;
import com.sat.sisat.controlycobranza.dto.FindCcRec;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.obligacion.dto.ObligacionDTO;
import com.sat.sisat.persistence.entity.GnRemate;
import com.sat.sisat.predial.business.BaseBusiness;

@Stateless
public class CobranzaCoactivaBo extends BaseBusiness implements
		CobranzaCoactivaBoRemote {
	
	
	
	private static final long serialVersionUID = 6148980956071264795L;

	private CobranzaCoactivaBusinessDao service;

	public CobranzaCoactivaBusinessDao getService() {
		return this.service;
	}

	@PostConstruct
	public void initialize() {
		this.service = new CobranzaCoactivaBusinessDao();
		setDataManager(this.service);
	}

	@Override
	public Boolean contribEnCobranzaCoactiva(Integer personaId)
			throws SisatException {

		Boolean respuesta = Boolean.FALSE;

		respuesta = service.contribEnCobranzaCoactiva(personaId);

		return respuesta;
	}
	
	public void setEstadoBloqueoDeuda(Integer recId, String valor,Integer usuarioId) throws Exception
	{
		getService().setEstadoBloqueoDeuda(recId, valor,usuarioId);
	}
	
	
	//
	public Integer descargaCosta (GestionCostas gestioncostas,Integer tipoDocumentoId,String	nroDocumento,Date fechaDocumento,String observacion,String terminal,Integer usuarioId) throws Exception 
	{
		
		return getService().descargaCosta(gestioncostas, tipoDocumentoId, nroDocumento, fechaDocumento, observacion, terminal, usuarioId);
	}
	
	//Agregado : Omar
	public Integer actualizarNotificacion(Integer recId,Integer motivoNotificacionId, Integer notificadorId,Date fechaNotificacion, int usuario_id, String terminal)throws Exception {		
		return getService().actualizarNotificacion(recId, motivoNotificacionId, notificadorId, fechaNotificacion, usuario_id, terminal);
	}
	
	public List<GeneracionMasivaRecDTO> getGeneracionMasivaRec (Integer usuario_id) throws Exception{		
		return getService().getGeneracionMasivaRec(usuario_id);
	}
	
	public List<FindCcRec> listaRecMasiva(Integer generacionmasivaid)	throws Exception
	{
		return getService().listaRecMasiva(generacionmasivaid);
	}

	//Fin:

	public Boolean resultadoDjsSinDeterminar(Integer personaId)
			throws SisatException, SQLException {

		Boolean respuesta = Boolean.FALSE;

		respuesta = service.resultadoDjsSinDeterminar(personaId);

		return respuesta;
	}

	public List<FindCcLoteDetalleActoExp> getAllFindCcLoteExp(Integer lote_id)
			throws Exception {
		return getService().getAllFindCcLoteExp(lote_id);
	}

	public List<FindCcLoteExigible> getAllFindLote() throws Exception {
		return getService().getAllFindLote();
	}

	public List<FindCcLoteDetalleActoExp> getAllDeudaExigibleOpPredialVehicular(
			Integer tipoActo, Integer conceptoId, Integer anio,
			BigDecimal montoMinimo, Integer flagUbicacion) throws Exception {
		return getService().getAllDeudaExigibleOpPredialVehicular(tipoActo,
				conceptoId, anio, montoMinimo, flagUbicacion);
	}

	public List<FindCcLoteDetalleActoExp> getAllDeudaExigibleRdArbitriosyRm(
			Integer tipoActo, Integer conceptoId, Integer anio,
			BigDecimal montoMinimo, Integer flagUbicacion) throws Exception {
		return getService().getAllDeudaExigibleRdArbitriosyRm(tipoActo,
				conceptoId, anio, montoMinimo, flagUbicacion);
	}

	public List<FindCcLoteDetalleActoExp> getAllDeudaExigibleRdPredial(
			Integer tipoActo, Integer conceptoId, Integer anio,
			BigDecimal montoMinimo, Integer flagUbicacion) throws Exception {
		return getService().getAllDeudaExigibleRdPredial(tipoActo, conceptoId,
				anio, montoMinimo, flagUbicacion);
	}

	public List<FindCcLoteDetalleActoExp> getAllDeudaExigibleRs(
			Integer tipoActo, Integer conceptoId, Integer anio,
			BigDecimal montoMinimo, Integer flagUbicacion) throws Exception {
		return getService().getAllDeudaExigibleRs(tipoActo, conceptoId, anio,
				montoMinimo, flagUbicacion);
	}

	public List<FindCcLote> getAllFindCcLoteDeudaExigible(Integer loteId,Integer annoLote) throws Exception {
		return getService().getAllFindCcLoteDeudaExigible(loteId,annoLote); 
	}
	
	public int registrarActoDeudaExigibleRs(Integer loteId, Integer periodo,
			Integer conceptoId, Integer tipoActoId, BigDecimal montoMinimo,
			Integer flagUbicable) throws Exception {
		return getService().registrarActoDeudaExigibleRs(loteId, periodo,
				conceptoId, tipoActoId, montoMinimo, flagUbicable,
				getUser().getUsuarioId(), getUser().getTerminal());
	}

	public int registrarActoDeudaExigibleRdPredial(Integer loteId,
			Integer periodo, Integer conceptoId, Integer tipoActoId,
			BigDecimal montoMinimo, Integer flagUbicable) throws Exception {
		return getService().registrarActoDeudaExigibleRdPredial(loteId,
				periodo, conceptoId, tipoActoId, montoMinimo, flagUbicable,
				getUser().getUsuarioId(), getUser().getTerminal());
	}

	public int registrarActoDeudaExigibleRdArbitriosRm(Integer loteId,
			Integer periodo, Integer conceptoId, Integer tipoActoId,
			BigDecimal montoMinimo, Integer flagUbicable) throws Exception {
		return getService().registrarActoDeudaExigibleRdArbitriosRm(loteId,
				periodo, conceptoId, tipoActoId, montoMinimo, flagUbicable,
				getUser().getUsuarioId(), getUser().getTerminal());
	}

	public int registrarActoDeudaExigibleOPPredialVehicular(Integer loteId,
			Integer periodo, Integer conceptoId, Integer tipoActoId,
			BigDecimal montoMinimo, Integer flagUbicable) throws Exception {
		return getService()
				.registrarActoDeudaExigibleOPPredialVehicular(loteId, periodo,
						conceptoId, tipoActoId, montoMinimo, flagUbicable,
						getUser().getUsuarioId(), getUser().getTerminal());
	}

	public List<FindCcLoteDetalleActoExp> getAllFindDetalleFinalDeudaExigible(
			Integer loteId) throws Exception {
		return getService().getAllFindDetalleFinalDeudaExigible(loteId);
	}

	public boolean eliminarDeudaExigible(Integer deudaExigibleId,
			Integer loteId, Integer actoId, String administrado,
			String nroActo, String motivoDescargo) throws Exception {
		return getService().eliminarDeudaExigible(deudaExigibleId, loteId,
				actoId, administrado, nroActo, motivoDescargo,
				getUser().getUsuarioId(), getUser().getTerminal());
	}

	public List<FindCcRecHistorico> getAllFindRecHistorico(Integer loteId,
			Integer deudaExId, Integer personaId) throws Exception {
		return getService()
				.getAllFindRecHistorico(loteId, deudaExId, personaId);
	}

	public int generarRecInicio(Integer loteId, Integer loteExigibleId,
			Integer nroRegistros) throws Exception {
		return getService()
				.generarRecInicio(loteId, loteExigibleId, nroRegistros,
						getUser().getUsuarioId(), getUser().getTerminal());
	}

	public List<FindCcRecTipo> getAllTipoRec(Boolean esPdf) throws Exception {
		return getService().getAllTipoRec(esPdf);
	}

	public List<TipoDocumentoDescargo> getAllTipoDocumento() throws Exception
	{
		return getService().getAllTipoDocumento();
	}
	
	public int actualizarNotificacionRec(Integer recId,
			Integer noNotificacionId, Integer notificadorId, Date fechaNotifica)
			throws Exception {
		return getService().actualizarNotificacionRec(recId, noNotificacionId,
				notificadorId, fechaNotifica, getUser().getUsuarioId());
	}

	public int actualizarEmisionRec(Integer recId, Date fechaEmision)
			throws Exception {
		return getService().actualizarEmisionRecMigradas(recId, fechaEmision);
	}

	public int generarRec(Integer loteRecId, Integer anioRec,
			Integer personaRecId, String nroExpedienteRec, Integer anioDeuda,
			Integer tipoRec, Integer tipoDocRec, Integer loteExId,
			Integer actoRecId) throws Exception {
		return getService().generarRec(loteRecId, anioRec, personaRecId,
				nroExpedienteRec, anioDeuda, tipoRec, tipoDocRec, loteExId,
				actoRecId, getUser().getUsuarioId(), getUser().getTerminal());
		// , getUser().getUsuarioId(), getUser().getTerminal());
	}

	public List<FindCcRecTipo> getAllRecsMasivasPorLote(Integer loteId)
			throws Exception {
		return getService().getAllRecsMasivasPorLote(loteId);
	}

	public List<FindCcLoteDetalleActoExp> getAllExpedientesAcumulados(
			Integer personaId) throws Exception {
		return getService().getAllExpedientesAcumulados(personaId);
	}

	public List<FindCcLoteDetalleActoExp> getAllExpedientesXPlaca(String placa)
			throws Exception {
		return getService().getAllExpedientesXPlaca(placa);
	}

	public List<ObligacionDTO> getAllCostasXTipoRec(Integer personaId,
			Integer recId) throws Exception {
		return getService().getAllCostasXTipoRec(personaId, recId);
	}

	public void insertarJustificacion(Integer personaId, Integer conceptoId,
			Integer subConceptoId, Integer anho, Integer determinacionId,
			String nroExped, String justificacion) throws SisatException {

		service.insertarJustificacion(personaId, conceptoId, subConceptoId,
				anho, determinacionId, nroExped, justificacion, this.getUser());
	}

	public int actualizarRecXCancelacion(String motivoCancelacion,
			String resolucionCancelacion, String estado, String nroExpediente,
			Integer codPersona) throws Exception {
		return getService().actualizarRecXCancelacion(motivoCancelacion,
				resolucionCancelacion, estado, nroExpediente, codPersona,
				getUser().getUsuario(), getUser().getUsuarioId(),
				getUser().getTerminal());
	}

	public List<FindCcLoteDetalleActoExp> getAllReporteExpedientes(
			Integer tipoDeuda, Integer tipoRecId) throws Exception {
		return service.getAllReporteExpedientes(tipoDeuda, tipoRecId);
	}

	public List<FindCcLoteDetalleActoExp> getAllExpedientesAnteriorAdministrado(
			String codigoAnterior) throws Exception {
		return service.getAllExpedientesXCodigoAnterior(codigoAnterior);
	}

	@Override
	public List<GnRemate> getAllRemates() throws Exception {
		TypedQuery<GnRemate> query = em.createNamedQuery("FindAllRemate",
				GnRemate.class);
		return query.getResultList();
	}

	@Override
	public GnRemate guardarRemate(GnRemate remate) throws Exception {
		remate.setUsuarioId(getUser().getUsuarioId());
		remate.setTerminal(getUser().getTerminal());
		return service.insertarDetalleRemate(remate);
	}
	
	public List<FindCcLoteDetalleActoExp> getAllDeudaExigibleRdVehicular(
			Integer tipoActo, Integer conceptoId, Integer anio,
			BigDecimal montoMinimo, Integer flagUbicacion) throws Exception {
		return getService().getAllDeudaExigibleRdVehicular(tipoActo, conceptoId,
				anio, montoMinimo, flagUbicacion);
	}
	
	public int registrarActoDeudaExigibleRdVehicular(Integer loteId,
			Integer periodo, Integer conceptoId, Integer tipoActoId,
			BigDecimal montoMinimo, Integer flagUbicable) throws Exception {
		return getService().registrarActoDeudaExigibleRdVehicular(loteId,
				periodo, conceptoId, tipoActoId, montoMinimo, flagUbicable,
				getUser().getUsuarioId(), getUser().getTerminal());
	}

	/**
	 * Modulo Coactiva V2 ::Inicio
	 */
	public Integer registraCarteraExigible(String listActoId,Integer loteId,Integer usuarioId,String terminal)throws Exception{
		return getService().registraCarteraExigible(listActoId, loteId,  usuarioId, terminal);
	}

	public Integer retiraCarteraExigible(Integer actoId,Integer motivoId,String resenaRetiro,Integer loteId,Integer usuarioId,String terminal)throws Exception{
		return getService().retiraCarteraExigible(actoId, motivoId, resenaRetiro, loteId, usuarioId, terminal);
	}
	
	public List<InformeTransferido> buscarInformeTransferido(Integer loteId, Integer periodoDeuda,
			Integer estadoTransferenciaId) throws Exception {
		return getService().buscarInformeTransferido(loteId,periodoDeuda,estadoTransferenciaId);
	}
	
	public List<InformeTransferidoDetalle> buscarDetalleInformeTransferido(Integer loteTransferenciaId)throws Exception {
		return getService().buscarDetalleInformeTransferido(loteTransferenciaId);
	}
	
	public List<ListadoArea> listarArea()throws Exception {
		return getService().listarArea();
	}
	
	public List<ListadoEstadoTransferencia> listarEstadoTransferencia()throws Exception {
		return getService().listarEstadoTransferencia();
	}

	public List<InformeTransferidoDetalle> listarValoresRecibidos(Integer loteTransferenciaId)throws Exception{
		return getService().listarValoresRecibidos(loteTransferenciaId);	
	}
	
	public List<InformeTransferidoDetalle> listarValoresDevueltos(Integer loteTransferenciaId)throws Exception{
		return getService().listarValoresDevueltos(loteTransferenciaId);
	}
	
	public Integer registraRecepcionInforme(Integer loteTransferenciaId,Integer usuarioId,String terminal)throws Exception{
		return getService().registraRecepcionInforme(loteTransferenciaId, usuarioId, terminal);
	}
	
	public Integer registraDevolucionValor(Integer loteTransferenciaDetalleId,Integer motivoDevolucionId,String observacionDevolucion, Integer usuarioId,String terminal)throws Exception {
		return getService().registraDevolucionValor(loteTransferenciaDetalleId, motivoDevolucionId, observacionDevolucion, usuarioId, terminal);
	}

	public List<GenericDTO> listarMotivoDevolucion()throws Exception{
		return getService().listarMotivoDevolucion();
	}

	
	/**
	 * Pantalla de busqueda de cartera exigibilidad 
	 */
	public List<GenericDTO> listarSituacionCartera()throws Exception{
		return getService().listarSituacionCartera();
	}
	
	public List<GenericDTO> listarEjecutorCoactivo()throws Exception{
		return getService().listarEjecutorCoactivo();
	}
	
	
	public List<GenericDTO> listarAuxiliarCoactivo()throws Exception{
		return getService().listarAuxiliarCoactivo();
	}
	
	
	

	public List<CarteraExigibilidad> buscarCarteraExigibilidad(String nroCartera,Integer usuarioCoactivoId,Integer situacionCarteraId,Integer materiaId)throws Exception{
		return getService().buscarCarteraExigibilidad(nroCartera, usuarioCoactivoId, situacionCarteraId,materiaId);
	}
	
	public Integer registrarCarteraExigibilidad(Integer carteraId,Integer coactivoId,Integer tipoCarteraId,Integer materiaId,String terminal,Integer usuarioId)throws Exception{
		return getService().registrarCarteraExigibilidad(carteraId, coactivoId, tipoCarteraId,materiaId, terminal, usuarioId);
	}
	
	public Integer registrarCarteraMedidaCautelar(Integer carteraId,Integer coactivoId,Integer tipoCarteraId,Integer materiaId,String terminal,Integer usuarioId)throws Exception{
		return getService().registrarCarteraMedidaCautelar(carteraId, coactivoId, tipoCarteraId,materiaId,terminal, usuarioId);
	}
	/**
	 *Pantalla de creacion de cartera 
	 */
	public List<GenericDTO> listarTipoPersona()throws Exception{
		return getService().listarTipoPersona();
	}
	
	public List<GenericDTO> listarTipoActo()throws Exception{
		return getService().listarTipoActo();
	}
	
	public List<GenericDTO> listarConcepto()throws Exception{
		return getService().listarConcepto();
	}
	
	public List<GenericDTO> listarSubConcepto(Integer conceptoId)throws Exception{
		return getService().listarSubConcepto(conceptoId);
	}
	
	/**
	 * acumula desacumula valores
	 * @param carteraId
	 * @param personaId
	 * @return
	 * @throws Exception
	 */
	public List<InformeTransferidoDetalle> consultarValoresCartera(Integer carteraId,Integer personaId)throws Exception{
		return getService().consultarValoresCartera(carteraId,personaId);
	}
	public List<InformeTransferidoDetalle> consultarValoresCartera(Integer carteraId)throws Exception{
		return getService().consultarValoresCartera(carteraId);
	}
	
	public Integer registraExpedientes(Integer carteraId,Integer usuarioId,String terminal)throws Exception{
		return getService().registraExpedientes(carteraId, usuarioId, terminal);
	}

	public Integer registraDesAcumulacionTodo(Integer carteraId,Integer usuarioId,String terminal)throws Exception{
		return getService().registraDesAcumulacionTodo(carteraId, usuarioId, terminal);
	}

	public Integer registraDesAcumulacionExpedienteTodo(Integer carteraId,Integer usuarioId,String terminal)throws Exception{
		return getService().registraDesAcumulacionExpedienteTodo(carteraId, usuarioId, terminal);
	}
	
//Al hacer click en el boton "Acumular todo"		
	public Integer registraAcumulacionTodo(Integer carteraId,Integer usuarioId,String terminal)throws Exception{
		return getService().registraAcumulacionTodo(carteraId, usuarioId, terminal);
	}
	public Integer registraAcumulacionExpedienteTodo(Integer carteraId,Integer usuarioId,String terminal)throws Exception{
		return getService().registraAcumulacionExpedienteTodo(carteraId, usuarioId, terminal);
	}

//Al hacer click en el sub menu "Desacumula"	
	public Integer registraDesAcumulacion(Integer carteraId,Integer personaId,String terminal,Integer usuarioId)throws Exception{
		return getService().registraDesAcumulacion(carteraId, personaId, terminal, usuarioId);
	}

//Al hacer click en el boton 	"Aceptar" de la pantalla popup "Valores de controbuyente"
	public Integer registraAcumulacion(String listaProspectoId,Integer carteraId,Integer personaId,String terminal,Integer usuarioId)throws Exception{
		return getService().registraAcumulacion(listaProspectoId, carteraId, personaId, terminal, usuarioId);
	}
	
	public Integer registraAcumulacionExpedienteEnCartera(String listaProspectoId,Integer carteraId,Integer personaId,String terminal,Integer usuarioId)throws Exception{
		return getService().registraAcumulacionExpedienteEnCartera(listaProspectoId, carteraId, personaId, terminal, usuarioId);
	}

//Al hacer click en el boton "Excel" esto debe exportar a excel
	/*
	public List<InformeTransferidoDetalle> consultarValoresCartera(Integer carteraId)throws Exception {
		return getService().consultarValoresCartera(carteraId);
	}
	*/

	public List<InformeTransferidoDetalle> seleccionaCarteraExigibilidad(Integer periodo,Integer tipoPersonaId,Integer tipoDeuda,Integer conceptoId,Integer subconceptoId,Integer tipoActoId,String nroActo,Integer personaId,Double montoMin,Double montoMax,Integer carteraId)throws Exception{
		return getService().seleccionaCarteraExigibilidad(periodo, tipoPersonaId, tipoDeuda, conceptoId, subconceptoId, tipoActoId, nroActo, personaId, montoMin, montoMax,carteraId);
	}
	
	public Integer registraProspectoExigibilidad(String listaDetalleId,Integer carteraId,Integer usuarioId,String terminal)throws Exception{
		return getService().registraProspectoExigibilidad(listaDetalleId, carteraId, usuarioId, terminal);
	}
	
	public List<InformeTransferidoDetalle> seleccionaProspectoExigibilidad(Integer carteraId)throws Exception{
		return getService().seleccionaProspectoExigibilidad(carteraId);
	}
	
	public List<ExpedienteCoactivo> gestionConsultaCartera(String placa,String nroCartera,String nroExpediente,String nroPapeleta,Integer personaId,Integer materiaId,Integer coactivoId)throws Exception{
		return getService().gestionConsultaCartera(placa,nroCartera,nroExpediente,nroPapeleta,personaId,materiaId,coactivoId);
	}
	
	public List<ExpedienteCoactivo> seleccionaCarteraMedidaCautelar(Integer periodo,Integer tipoPersonaId,Integer tipoDeuda,
			Double montoMin,Double montoMax,String nroExpediente,Integer personaId,Integer carteraId,Integer coactivoId)throws Exception{
		return getService().seleccionaCarteraMedidaCautelar(periodo, tipoPersonaId, tipoDeuda, 
				montoMin, montoMax, nroExpediente, personaId,carteraId,coactivoId);
	}
	
	public List<ExpedienteCoactivo> seleccionaProspectoMedidaCautelar(Integer carteraId)throws Exception{
		return getService().seleccionaProspectoMedidaCautelar(carteraId);
	}
	
	public Integer registraProspectoMedidaCautelar(String listaDetalleId,Integer carteraId,Integer usuarioId,String terminal)throws Exception{
		return getService().registraProspectoMedidaCautelar(listaDetalleId, carteraId, usuarioId, terminal);
	}
	
	public List<CarteraExigibilidad> buscarCarteraMedidaCautelar(String nroCartera,Integer usuarioCoactivoId,Integer situacionCarteraId)throws Exception{
		return getService().buscarCarteraMedidaCautelar(nroCartera, usuarioCoactivoId, situacionCarteraId);
	}
	
	public List<ExpedienteCoactivo> consultarExpedientesCartera(Integer carteraId)throws Exception{
		return getService().consultarExpedientesCartera(carteraId);
	}
	
	public List<ExpedienteCoactivo> consultarExpedientesCartera(Integer carteraId,Integer personaId)throws Exception{
		return getService().consultarExpedientesCartera(carteraId, personaId);
	}
	
	public List<GenericDTO> listarUsuario()throws Exception{
		return getService().listarUsuario();
	}
	
	public List<EjecutorCoactivo> consultarEjecutorCoactivo()throws Exception{
		return getService().consultarEjecutorCoactivo();
	}

	public Integer registraUsuarioCargo(Integer usuarioCargoId,Integer usuarioId,Integer cargoId,Integer usuarioAuxId,Integer estado,
			String nroRegistro,String nroregistroAux,
			Integer materiaId,
			Integer usuarioRegistroId,String terminal)throws Exception{
		return getService().registraUsuarioCargo(usuarioCargoId, usuarioId,cargoId,usuarioAuxId, estado,
				nroRegistro,nroregistroAux,
				materiaId,
				usuarioRegistroId, terminal);
	}
	
	public List<GenericDTO> consultarPeriodoCostas()throws Exception{
		return getService().consultarPeriodoCostas();
	}
	
	public List<DetalleCostas> consultarDetalleCostas(Integer periodo)throws Exception{
		return getService().consultarDetalleCostas(periodo);
	}
	
	public Integer registraCosta(Integer conceptoTasaId,Integer conceptoId,Integer subConceptoId,Integer periodo,Double valor,Integer estado)throws Exception{
		return getService().registraCosta(conceptoTasaId, conceptoId, subConceptoId, periodo, valor,estado);
	}
	
	public Integer duplicaCostaPeriodo(Integer periodoNuevo,Integer periodo,Integer usuarioId,String terminal)throws Exception{
		return getService().duplicaCostaPeriodo(periodoNuevo,periodo, usuarioId, terminal);
	}
	
	public List<GenericDTO> consultarMotivoDevolucion()throws Exception{
		return getService().consultarMotivoDevolucion();
	}
	public List<GenericDTO> consultarMotivoDesacumula()throws Exception{
		return getService().consultarMotivoDesacumula();
	} 
	public List<GenericDTO> consultarTipoGestion()throws Exception{
		return getService().consultarTipoGestion();
	}
	public Integer registraTipoGestion(Integer id,String descripcion,Integer estado,Integer usuarioId,String terminal)throws Exception{
		return getService().registraTipoGestion(id, descripcion, estado, usuarioId, terminal);
	}
	public Integer registraMotivoDevolucion(Integer id,String descripcion,Integer estado,Integer usuarioId,String terminal)throws Exception{
		return getService().registraMotivoDevolucion(id, descripcion, estado, usuarioId, terminal);
	}
	public Integer registraMotivoDesacumula(Integer id,String descripcion,Integer estado,Integer usuarioId,String terminal)throws Exception{
		return getService().registraMotivoDesacumula(id, descripcion, estado, usuarioId, terminal);
	}

	public Integer retiraValorCartera(Integer carteraId,Integer actoId,Integer usuarioId,String terminal)throws Exception{
		return getService().retiraValorCartera(carteraId, actoId, usuarioId, terminal);
	}
	
	public Integer reasignarCartera(Integer carteraId,Integer coactivoId,Integer coactivoAsignarId,Integer usuarioId,String terminal)throws Exception{
		return getService().reasignarCartera(carteraId, coactivoId, coactivoAsignarId, usuarioId, terminal);
	}
	
	public Integer retiraExpedienteCartera(Integer carteraId,Integer prospectoId,Integer usuarioId,String terminal)throws Exception{
		return getService().retiraExpedienteCartera(carteraId,prospectoId,usuarioId,terminal);
	}
	
	public List<GestionValores> gestionConsultaValores(Integer expedienteId)throws Exception{
		return getService().gestionConsultaValores(expedienteId);
	}
	
	public List<GestionEventos> gestionConsultaEventosExpediente(Integer expedienteId)throws Exception{
		return getService().gestionConsultaEventosExpediente(expedienteId);
	}
	
	public List<GenericDTO> listarTipoGestion()throws Exception{
		return getService().listarTipoGestion();
	}
	
	public Integer registraGestionExpediente(Integer expedienteId,Integer tipoGestionId,String resena,Date fechaGestion,Integer usuarioId,String terminal)throws Exception{
		return getService().registraGestionExpediente(expedienteId, tipoGestionId,resena, fechaGestion,usuarioId, terminal);
	}
	public Integer registraRecExpediente(String listExpedienteId,Integer conceptoID,Integer tipoRecId,Integer usuarioId,Integer usuario_ejecutor_id,Integer usuario_id_auxiliar,String terminal)throws Exception{
		return getService().registraRecExpediente( listExpedienteId, conceptoID, tipoRecId, usuarioId, usuario_ejecutor_id, usuario_id_auxiliar, terminal);
	}
	public Integer registraGestionCartera(Integer carteraId,Integer tipoGestionId,String resena,Integer usuarioId,String terminal)throws Exception{
		return getService().registraGestionCartera(carteraId, tipoGestionId, resena, usuarioId, terminal);
	}
	public Integer registraRecCartera(Integer carteraId,Integer tipoRecId,Integer usuarioId,String terminal)throws Exception{
		return getService().registraRecCartera(carteraId, tipoRecId, usuarioId, terminal);
	}

	public List<ExpedienteCoactivo> consultarExpedientesPersonaAcum(Integer personaId,Integer expedienteId)throws Exception{
		return getService().consultarExpedientesPersonaAcum(personaId,expedienteId);
	}
	
	public List<ExpedienteCoactivo> consultarExpedientesPersonaDesAcum(Integer personaId,Integer expedienteId)throws Exception{
		return getService().consultarExpedientesPersonaDesAcum(personaId,expedienteId);
	}
	
	public Integer registraAcumulacionExpediente(String listaProspectoId,Integer expedienteId,Integer personaId,String terminal,Integer usuarioId)throws Exception{
		return getService().registraAcumulacionExpediente(listaProspectoId, expedienteId, personaId, terminal, usuarioId);
	}
	
	public Integer registraDesAcumulacionExpediente(String listaProspectoId,Integer expedienteId,Integer personaId,String terminal,Integer usuarioId)throws Exception{
		return getService().registraDesAcumulacionExpediente(listaProspectoId, expedienteId, personaId, terminal, usuarioId);
	}
	
	public Integer registraCostasExpediente(Integer expedienteId,Integer recId,String terminal,Integer usuarioId)throws Exception{
		return getService().registraCostasExpediente(expedienteId,recId,terminal, usuarioId);
	}
	
	public List<GestionCostas> gestionConsultaCostasExpediente(Integer expedienteId)throws Exception{
		return getService().gestionConsultaCostasExpediente(expedienteId);
	}
	
	public Integer registraGastosExpediente(Integer expedienteId,Double montoGasto,Date fechaGasto,String resenaGasto,String comprobanteGasto,Integer recId,String terminal,Integer usuarioId)throws Exception{
		return getService().registraGastosExpediente(expedienteId, montoGasto, fechaGasto, resenaGasto,comprobanteGasto, recId, terminal, usuarioId);
	}
	
	public List<ExpedienteCoactivo> notificacionConsultaExpediente(String nroCartera,String nroExpediente,String nroPapeleta,Integer personaId,Integer ultimaRec)throws Exception{
		return getService().notificacionConsultaExpediente(nroCartera, nroExpediente, nroPapeleta, personaId,ultimaRec);
	}
	
	public Integer registraNotificacionExpediente(Integer motivoNotificacionId,Integer notificadorId,Date fechaNotificacion,Integer recId,Integer usuarioId,String terminal)throws Exception{
		return getService().registraNotificacionExpediente(motivoNotificacionId, notificadorId, fechaNotificacion, recId, usuarioId, terminal);
	}
	
	public List<SituacionExigibilidad> reporteSituacionExigibilidad(Integer loteId,java.util.Date fechaDesde,java.util.Date fechaHasta,Integer materiaId)throws Exception{
		return getService().reporteSituacionExigibilidad(loteId, fechaDesde, fechaHasta, materiaId);
	}
	
	public List<SituacionCartera> reporteSituacionCartera(Integer carteraId,java.util.Date fechaDesde,java.util.Date fechaHasta,Integer materiaId)throws Exception{
		return getService().reporteSituacionCartera(carteraId, fechaDesde, fechaHasta, materiaId);
	}
	
	public List<ControlExpediente> reporteControlExpediente(String nroExpediente,Integer coactivoId,java.util.Date fechaDesde,java.util.Date fechaHasta,Integer materiaId)throws Exception{
		return getService().reporteControlExpediente(nroExpediente, coactivoId, fechaDesde, fechaHasta, materiaId);
	}
	
	public List<SituacionDeuda> reporteSituacionDeuda(Integer carteraId,Integer personaId)throws Exception{
		return getService().reporteSituacionDeuda(carteraId, personaId);
	}
	
	public CoCartera consultaCartera(String nroCartera)throws Exception{
		return getService().consultaCartera(nroCartera);
	}
	
	public Integer registraObsGestion(Integer gestionExpedienteId,String observacion,Integer usuarioId,String terminal)throws Exception{
		return getService().registraObsGestion(gestionExpedienteId, observacion, usuarioId, terminal);
	}
	
	public String validaGeneraRec(Integer expedienteId,Integer tipoRecId)throws Exception{
		return getService().validaGeneraRec(expedienteId, tipoRecId);
	}

	
	
	
	public List<GenericDTO> getTributos() throws Exception{
		// TODO Auto-generated method stub
		return getService().getTributos();
	}

	
	/*
	public Integer actualizarNotificacion(Integer recId,Integer motivoNotificacionId,Integer notificadorId,Date fechaNotificacion,int usuario_id,String terminal) throws Exception{
		return getService().actualizarNotificacion(recId, motivoNotificacionId, notificadorId, fechaNotificacion, usuario_id, terminal);
	}
	*/
	
	
	
	/**
	 * cchaucca:inicio 14/07/2016 obtiene el listado de tipos de rec generados por cartera
	 */
	/*
	public List<CoCartera> obtenerCarteraGenMasiva(Integer carteraId)throws Exception{
		return getService().obtenerCarteraGenMasiva(carteraId);
	}
	*/
	
		
	
	/**
	 * Modulo Coactiva V2 ::Fin
	 */
}
