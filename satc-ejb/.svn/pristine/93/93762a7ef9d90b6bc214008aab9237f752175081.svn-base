
package com.sat.sisat.cobranzacoactiva.business;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.ejb.Remote;



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
import com.sat.sisat.cobranzacoactiva.dto.TipoDocumentoDescargo;
import com.sat.sisat.common.dto.GenericDTO;
import com.sat.sisat.controlycobranza.dto.FindCcLote;
import com.sat.sisat.controlycobranza.dto.FindCcRec;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.obligacion.dto.ObligacionDTO;
import com.sat.sisat.persistence.entity.GnRemate;


@Remote
public interface CobranzaCoactivaBoRemote {
	
	//Agregado : omar
	public Integer descargaCosta (GestionCostas gestioncostas,Integer tipoDocumentoId,String	nroDocumento,Date fechaDocumento,String observacion,String terminal,Integer usuarioId) throws Exception;	
	public Integer actualizarNotificacion(Integer recId,Integer motivoNotificacionId,Integer notificadorId,Date fechaNotificacion,int usuario_id,String terminal)throws Exception;	
	public List<GeneracionMasivaRecDTO> getGeneracionMasivaRec (Integer usuario_id) throws Exception;
	public List<FindCcRec> listaRecMasiva(Integer generacionmasivaid)	throws Exception;
	public List<TipoDocumentoDescargo> getAllTipoDocumento() throws Exception;
	public void setEstadoBloqueoDeuda(Integer recId, String valor,Integer usuarioId) throws Exception;
	
	
	

	public List<GenericDTO> getTributos () throws Exception;
	
	
	
	public Boolean contribEnCobranzaCoactiva(Integer personaId)
			throws SisatException;

	public Boolean resultadoDjsSinDeterminar(Integer personaId)
			throws SisatException, SQLException;

	public abstract List<FindCcLoteDetalleActoExp> getAllFindCcLoteExp(
			Integer lote_id) throws Exception;

	public List<FindCcLoteExigible> getAllFindLote() throws Exception;

	public List<FindCcLoteDetalleActoExp> getAllDeudaExigibleOpPredialVehicular(
			Integer tipoActo, Integer conceptoId, Integer anio,
			BigDecimal montoMinimo, Integer flagUbicacion) throws Exception;

	public List<FindCcLoteDetalleActoExp> getAllDeudaExigibleRdArbitriosyRm(
			Integer tipoActo, Integer conceptoId, Integer anio,
			BigDecimal montoMinimo, Integer flagUbicacion) throws Exception;

	public List<FindCcLoteDetalleActoExp> getAllDeudaExigibleRdPredial(
			Integer tipoActo, Integer conceptoId, Integer anio,
			BigDecimal montoMinimo, Integer flagUbicacion) throws Exception;

	public List<FindCcLoteDetalleActoExp> getAllDeudaExigibleRs(
			Integer tipoActo, Integer conceptoId, Integer anio,
			BigDecimal montoMinimo, Integer flagUbicacion) throws Exception;

	public abstract List<FindCcLote> getAllFindCcLoteDeudaExigible(Integer loteId,Integer annoLote)
			throws Exception; 
	
	public abstract int registrarActoDeudaExigibleRs(Integer loteId,
			Integer periodo, Integer conceptoId, Integer tipoActoId,
			BigDecimal montoMinimo, Integer flagUbicable) throws Exception;

	public abstract int registrarActoDeudaExigibleRdPredial(Integer loteId,
			Integer periodo, Integer conceptoId, Integer tipoActoId,
			BigDecimal montoMinimo, Integer flagUbicable) throws Exception;

	public abstract int registrarActoDeudaExigibleRdArbitriosRm(Integer loteId,
			Integer periodo, Integer conceptoId, Integer tipoActoId,
			BigDecimal montoMinimo, Integer flagUbicable) throws Exception;

	// getUser().getUsuarioId()
	public abstract int registrarActoDeudaExigibleOPPredialVehicular(
			Integer loteId, Integer periodo, Integer conceptoId,
			Integer tipoActoId, BigDecimal montoMinimo, Integer flagUbicable)
			throws Exception;

	public List<FindCcLoteDetalleActoExp> getAllFindDetalleFinalDeudaExigible(
			Integer loteId) throws Exception;

	public abstract boolean eliminarDeudaExigible(Integer deudaExigibleId,
			Integer loteId, Integer actoId, String administrado,
			String nroActo, String motivoDescargo) throws Exception;

	public List<FindCcRecHistorico> getAllFindRecHistorico(Integer loteId,
			Integer deudaExId, Integer personaId) throws Exception;

	public abstract int generarRecInicio(Integer loteId,
			Integer loteExigibleId, Integer nroRegistros) throws Exception;

	public abstract List<FindCcRecTipo> getAllTipoRec(Boolean esPdf) throws Exception;
		
	
	public int actualizarNotificacionRec(Integer recId,
			Integer noNotificacionId, Integer notificadorId, Date fechaNotifica)
			throws Exception;

	public int actualizarEmisionRec(Integer recId, Date fechaEmision)
			throws Exception;

	public abstract int generarRec(Integer loteRecId, Integer anioRec,
			Integer personaRecId, String nroExpedienteRec, Integer anioDeuda,
			Integer tipoRec, Integer tipoDocRec, Integer loteExId,
			Integer actoRecId) throws Exception;

	public List<FindCcRecTipo> getAllRecsMasivasPorLote(Integer loteId)
			throws Exception;

	public List<FindCcLoteDetalleActoExp> getAllExpedientesAcumulados(
			Integer personaId) throws Exception;

	public List<FindCcLoteDetalleActoExp> getAllExpedientesXPlaca(String placa)
			throws Exception;

	public List<ObligacionDTO> getAllCostasXTipoRec(Integer personaId,
			Integer recId) throws Exception;

	public void insertarJustificacion(Integer personaId, Integer conceptoId,
			Integer subConceptoId, Integer anho, Integer determinacionId,
			String nroExped, String justificacion) throws SisatException;

	public int actualizarRecXCancelacion(String motivoCancelacion,
			String resolucionCancelacion, String estado, String nroExpediente,
			Integer codPersona) throws Exception;

	public List<FindCcLoteDetalleActoExp> getAllReporteExpedientes(
			Integer tipoDeuda, Integer tipoRecId) throws Exception;

	public List<FindCcLoteDetalleActoExp> getAllExpedientesAnteriorAdministrado(
			String codigoAnterior) throws Exception;

	public List<GnRemate> getAllRemates() throws Exception;
	
	public GnRemate guardarRemate(GnRemate remate) throws Exception;
	
	public List<FindCcLoteDetalleActoExp> getAllDeudaExigibleRdVehicular(
			Integer tipoActo, Integer conceptoId, Integer anio,
			BigDecimal montoMinimo, Integer flagUbicacion) throws Exception;
	
	public abstract int registrarActoDeudaExigibleRdVehicular(Integer loteId,
			Integer periodo, Integer conceptoId, Integer tipoActoId,
			BigDecimal montoMinimo, Integer flagUbicable) throws Exception;
	
	/**
	 * Modulo Coactiva V2 ::Inicio
	 */
	public Integer registraCarteraExigible(String listActoId,Integer loteId,Integer usuarioId,String terminal)throws Exception;

	public Integer retiraCarteraExigible(Integer actoId,Integer motivoId,String resenaRetiro,Integer loteId,Integer usuarioId,String terminal)throws Exception;

	public List<InformeTransferido> buscarInformeTransferido(Integer loteId, Integer periodoDeuda,
			Integer estadoTransferenciaId) throws Exception;
	
	public List<InformeTransferidoDetalle> buscarDetalleInformeTransferido(Integer loteTransferenciaId) throws Exception;
	
	public List<ListadoArea> listarArea() throws Exception;
	
	public List<ListadoEstadoTransferencia> listarEstadoTransferencia() throws Exception;
	
	public List<InformeTransferidoDetalle> listarValoresRecibidos(Integer loteTransferenciaId)throws Exception;
	
	public List<InformeTransferidoDetalle> listarValoresDevueltos(Integer loteTransferenciaId)throws Exception;
	
	public Integer registraRecepcionInforme(Integer loteTransferenciaId,Integer usuarioId,String terminal)throws Exception;
	
	public Integer registraDevolucionValor(Integer loteTransferenciaDetalleId,Integer motivoDevolucionId,String observacionDevolucion, Integer usuarioId,String terminal)throws Exception ;
	
	public List<GenericDTO> listarMotivoDevolucion()throws Exception;

	/**
	 * Pantalla de busqueda de cartera exigibilidad 
	 */
	public List<GenericDTO> listarSituacionCartera()throws Exception;
	
	public List<GenericDTO> listarEjecutorCoactivo()throws Exception;
	
	public List<GenericDTO> listarAuxiliarCoactivo()throws Exception;

	public List<CarteraExigibilidad> buscarCarteraExigibilidad(String nroCartera,Integer usuarioCoactivoId,Integer situacionCarteraId,Integer materiaId)throws Exception;
	
	public Integer registrarCarteraExigibilidad(Integer carteraId,Integer coactivoId,Integer tipoCarteraId,Integer materiaId,String terminal,Integer usuarioId)throws Exception;
	
	public Integer registrarCarteraMedidaCautelar(Integer carteraId,Integer coactivoId,Integer tipoCarteraId,Integer materiaId,String terminal,Integer usuarioId)throws Exception;
	
	/**
	 *Pantalla de creacion de cartera 
	 */
	public List<GenericDTO> listarTipoPersona()throws Exception;
	
	public List<GenericDTO> listarTipoActo()throws Exception;
	
	public List<GenericDTO> listarConcepto()throws Exception;
	
	public List<GenericDTO> listarSubConcepto(Integer conceptoId)throws Exception;
	
	/**
	 * acumula desacumula valores
	 * @param carteraId
	 * @param personaId
	 * @return
	 * @throws Exception
	 */
	public List<InformeTransferidoDetalle> consultarValoresCartera(Integer carteraId,Integer personaId)throws Exception;
	public List<InformeTransferidoDetalle> consultarValoresCartera(Integer carteraId)throws Exception;
	
	public Integer registraExpedientes(Integer carteraId,Integer usuarioId,String terminal)throws Exception;
	public Integer registraDesAcumulacionTodo(Integer carteraId,Integer usuarioId,String terminal)throws Exception;
	
	public Integer registraDesAcumulacionExpedienteTodo(Integer carteraId,Integer usuarioId,String terminal)throws Exception;
	
	public Integer registraAcumulacionTodo(Integer carteraId,Integer usuarioId,String terminal)throws Exception;
	public Integer registraAcumulacionExpedienteTodo(Integer carteraId,Integer usuarioId,String terminal)throws Exception;
	public Integer registraDesAcumulacion(Integer carteraId,Integer personaId,String terminal,Integer usuarioId)throws Exception;
	public Integer registraAcumulacion(String listaProspectoId,Integer carteraId,Integer personaId,String terminal,Integer usuarioId)throws Exception;
	
	public Integer registraAcumulacionExpedienteEnCartera(String listaProspectoId,Integer carteraId,Integer personaId,String terminal,Integer usuarioId)throws Exception;
	
	public List<InformeTransferidoDetalle> seleccionaCarteraExigibilidad(Integer periodo,Integer tipoPersonaId,Integer tipoDeuda,Integer conceptoId,Integer subconceptoId,Integer tipoActoId,String nroActo,Integer personaId,Double montoMin,Double montoMax,Integer carteraId)throws Exception;
	
	public Integer registraProspectoExigibilidad(String listaDetalleId,Integer carteraId,Integer usuarioId,String terminal)throws Exception;
	
	public List<InformeTransferidoDetalle> seleccionaProspectoExigibilidad(Integer carteraId)throws Exception;
	
	public List<ExpedienteCoactivo> gestionConsultaCartera(String placa,String nroCartera,String nroExpediente,String nroPapeleta,Integer personaId,Integer materiaId,Integer coactivoId)throws Exception;

	public List<ExpedienteCoactivo> seleccionaCarteraMedidaCautelar(Integer periodo,Integer tipoPersonaId,Integer tipoDeuda,
			Double montoMin,Double montoMax,String nroExpediente,Integer personaId,Integer carteraId,Integer coactivoId)throws Exception;
	
	public List<ExpedienteCoactivo> seleccionaProspectoMedidaCautelar(Integer carteraId)throws Exception;
	
	public Integer registraProspectoMedidaCautelar(String listaDetalleId,Integer carteraId,Integer usuarioId,String terminal)throws Exception;
	
	public List<CarteraExigibilidad> buscarCarteraMedidaCautelar(String nroCartera,Integer usuarioCoactivoId,Integer situacionCarteraId)throws Exception;
	
	public List<ExpedienteCoactivo> consultarExpedientesCartera(Integer carteraId)throws Exception;

	public List<ExpedienteCoactivo> consultarExpedientesCartera(Integer carteraId,Integer personaId)throws Exception;
	
	public List<GenericDTO> listarUsuario()throws Exception;
	
	public List<EjecutorCoactivo> consultarEjecutorCoactivo()throws Exception;
	
	public Integer registraUsuarioCargo(Integer usuarioCargoId,Integer usuarioId,Integer cargoId,Integer usuarioAuxId,Integer estado,
			String nroRegistro,String nroregistroAux,
			Integer materiaId,
			Integer usuarioRegistroId,String terminal)throws Exception;
	
	public List<GenericDTO> consultarPeriodoCostas()throws Exception;
	
	public List<DetalleCostas> consultarDetalleCostas(Integer periodo)throws Exception;
	
	public Integer registraCosta(Integer conceptoTasaId,Integer conceptoId,Integer subConceptoId,Integer periodo,Double valor,Integer estado)throws Exception;
	
	public Integer duplicaCostaPeriodo(Integer periodoNuevo,Integer periodo,Integer usuarioId,String terminal)throws Exception;
	
	public List<GenericDTO> consultarMotivoDevolucion()throws Exception;
	public List<GenericDTO> consultarMotivoDesacumula()throws Exception; 
	public List<GenericDTO> consultarTipoGestion()throws Exception;
	public Integer registraTipoGestion(Integer id,String descripcion,Integer estado,Integer usuarioId,String terminal)throws Exception;
	public Integer registraMotivoDevolucion(Integer id,String descripcion,Integer estado,Integer usuarioId,String terminal)throws Exception;
	public Integer registraMotivoDesacumula(Integer id,String descripcion,Integer estado,Integer usuarioId,String terminal)throws Exception;

	public Integer retiraValorCartera(Integer carteraId,Integer actoId,Integer usuarioId,String terminal)throws Exception;
	
	public Integer reasignarCartera(Integer carteraId,Integer coactivoId,Integer coactivoAsignarId,Integer usuarioId,String terminal)throws Exception;
	
	public Integer retiraExpedienteCartera(Integer carteraId,Integer prospectoId,Integer usuarioId,String terminal)throws Exception;
	
	public List<GestionValores> gestionConsultaValores(Integer expedienteId)throws Exception;
	
	public List<GestionEventos> gestionConsultaEventosExpediente(Integer expedienteId)throws Exception;
	
	public List<GenericDTO> listarTipoGestion()throws Exception;
	
	public Integer registraGestionExpediente(Integer expedienteId,Integer tipoGestionId,String resena,Date fechaGestion,Integer usuarioId,String terminal)throws Exception;
	
	
	public Integer registraRecExpediente(String listExpedienteId,Integer conceptoID,Integer tipoRecId,Integer usuarioId,Integer usuario_ejecutor_id,Integer usuario_id_auxiliar,String terminal)throws Exception;
	
	
	public Integer registraGestionCartera(Integer carteraId,Integer tipoGestionId,String resena,Integer usuarioId,String terminal)throws Exception;
	public Integer registraRecCartera(Integer carteraId,Integer tipoRecId,Integer usuarioId,String terminal)throws Exception;

	public List<ExpedienteCoactivo> consultarExpedientesPersonaAcum(Integer personaId,Integer expedienteId)throws Exception;
	
	public List<ExpedienteCoactivo> consultarExpedientesPersonaDesAcum(Integer personaId,Integer expedienteId)throws Exception;
	
	public Integer registraAcumulacionExpediente(String listaProspectoId,Integer expedienteId,Integer personaId,String terminal,Integer usuarioId)throws Exception;
	
	public Integer registraDesAcumulacionExpediente(String listaProspectoId,Integer expedienteId,Integer personaId,String terminal,Integer usuarioId)throws Exception;
	
	public Integer registraCostasExpediente(Integer expedienteId,Integer recId,String terminal,Integer usuarioId)throws Exception;
	
	public List<GestionCostas> gestionConsultaCostasExpediente(Integer expedienteId)throws Exception;
	
	public Integer registraGastosExpediente(Integer expedienteId,Double montoGasto,Date fechaGasto,String resenaGasto,String comrpobanteGasto,Integer recId,String terminal,Integer usuarioId)throws Exception;
	
	public List<ExpedienteCoactivo> notificacionConsultaExpediente(String nroCartera,String nroExpediente,String nroPapeleta,Integer personaId,Integer ultimaRec)throws Exception;
	
	public Integer registraNotificacionExpediente(Integer motivoNotificacionId,Integer notificadorId,Date fechaNotificacion,Integer recId,Integer usuarioId,String terminal)throws Exception;

	
	
	public List<SituacionExigibilidad> reporteSituacionExigibilidad(Integer loteId,java.util.Date fechaDesde,java.util.Date fechaHasta,Integer materiaId)throws Exception;
	
	public List<SituacionCartera> reporteSituacionCartera(Integer carteraId,java.util.Date fechaDesde,java.util.Date fechaHasta,Integer materiaId)throws Exception;
	
	public List<ControlExpediente> reporteControlExpediente(String nroExpediente,Integer coactivoId,java.util.Date fechaDesde,java.util.Date fechaHasta,Integer materiaId)throws Exception;
	
	public List<SituacionDeuda> reporteSituacionDeuda(Integer carteraId,Integer personaId)throws Exception;
	
	public CoCartera consultaCartera(String nroCartera)throws Exception;
	
	public Integer registraObsGestion(Integer gestionExpedienteId,String observacion,Integer usuarioId,String terminal)throws Exception;
	
	public String validaGeneraRec(Integer expedienteId,Integer tipoRecId)throws Exception;
	/**
	 * Modulo Coactiva V2 ::Fin
	 */
}
