package com.sat.sisat.tramitedocumentario.bussiness;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Remote;

import com.sat.sisat.exception.SisatException;
import com.sat.sisat.persistence.entity.GnTipoDocumento;
import com.sat.sisat.persistence.entity.GnUnidad;
import com.sat.sisat.persistence.entity.MpRelacionado;
import com.sat.sisat.persistence.entity.TdDJ;
import com.sat.sisat.persistence.entity.TdDataValor;
import com.sat.sisat.persistence.entity.TdDjConyuge;
import com.sat.sisat.persistence.entity.TdDjUnicaPropiedad;
import com.sat.sisat.persistence.entity.TdDocumentoAnexo;
import com.sat.sisat.persistence.entity.TdDocumentoTramite;
import com.sat.sisat.persistence.entity.TdEstadoExpediente;
import com.sat.sisat.persistence.entity.TdExpediente;
import com.sat.sisat.persistence.entity.TdRepresentante;
import com.sat.sisat.persistence.entity.TdRequisitoExpediente;
import com.sat.sisat.persistence.entity.TdResolucion;
import com.sat.sisat.persistence.entity.TdResultado;
import com.sat.sisat.persistence.entity.TdTipoTramite;
import com.sat.sisat.persistence.entity.TdSituacionExpediente;
import com.sat.sisat.tramitedocumentario.dto.BusquedaExpedienteDTO;
import com.sat.sisat.tramitedocumentario.dto.DeclaracionJuradaAdultDTO;
import com.sat.sisat.tramitedocumentario.dto.ItemBandejaEntradaDTO;
import com.sat.sisat.tramitedocumentario.dto.ItemHistoricoEntradaDTO;
import com.sat.sisat.tramitedocumentario.dto.ItemSeguimientoEntradaDTO;
import com.sat.sisat.tramitedocumentario.dto.ItemSeguimientoExpedienteDTO;
import com.sat.sisat.tramitedocumentario.dto.RequisitoExpededienteDTO;
import com.sat.sisat.tramitedocumentario.dto.ResolutorDTO;
import com.sat.sisat.tramitedocumentario.dto.ResumenReporteExpedientesDTO;

@Remote
public interface TramiteDocumentarioBoRemote {

	public List<TdExpediente> getAllTdExpediente();

	public List<ItemBandejaEntradaDTO> obtenerExpedientes(BusquedaExpedienteDTO busquedaExpedienteDTO)
			throws SisatException;
	
	//Obtener dj adulto
	public List<DeclaracionJuradaAdultDTO> obtenerDjAdult(DeclaracionJuradaAdultDTO busquedaDjAdultDTO)
			throws SisatException;
	
	//servicios
	public List<ItemBandejaEntradaDTO> obtenerExpedientesServAdult(BusquedaExpedienteDTO busquedaExpedienteDTO)
			throws SisatException;
	
	public DeclaracionJuradaAdultDTO nuevaDjAdulto(Date fechaRecepcion, String correlativoDJ, int tramiteId,
			int documentoTramiteId, String nroExpedienteGenerico,
			String nroExpediente, int nroFolios, int contribuyenteId, String nroDocIdentContr,
			String apellidosNombresContr, String direccionFiscalContr, String observacion, String nroResolucion,int usuarioId, int estadoDJ,
			String terminal, int situacionDJ, int procedimientoId, int unidadId, int estadoExpediente, int resolutorId, String tipoBien,
			BigDecimal porcBenif,int iniAnnoBenif
			) throws SQLException;
	
	
	public DeclaracionJuradaAdultDTO nuevoConyugeDjAdult(int djId,
			int relacionadoId,
			String nroDocIdentConyuge,
			String apellidosNombresConyuge,
			BigDecimal porcentajePartConyuge,
			Boolean fallecidoConyuge,
			int requisitoPartId,
			Date fechaPartidaDefuncion,
			int requisitoSucId,
			Date fechaSucesionIntestada,
			BigDecimal cuotaIdeal,
			int contribuyenteId,
			int usuarioId,
			String terminal) throws SQLException;
	
	public DeclaracionJuradaAdultDTO nuevaResolucionDj(
			   String nroResolucion 
		      ,int djId
		      ,int usuarioId
		      ,String terminal) throws SQLException;
	
	public DeclaracionJuradaAdultDTO nuevaPropUnicaDjAdult(
			int djId,
			Boolean vivienda,
			Boolean negocio, 
			int contribuyenteId, 
			int usuarioId, 
			String terminal) throws SQLException;
	
	public RequisitoExpededienteDTO nuevoRequisitoExpedienteDjAdult(
			int requisitoId,
			Boolean flagPresentado,
			String glosa,
			int usuarioId,
			String terminal,
			int djId) throws SQLException;
	
//	public List<DeclaracionJuradaAdultDTO> obtenerDjId(int contribuyenteId ) throws Exception; 
	
	public  Integer obtenerDjId(int contribuyenteId) throws Exception; 
	
	public  Integer obtenerDjIdDocTramite(int contribuyenteId) throws Exception; 

	
	public  Integer obtenerResolutorDjId(int usuarioLogueadoId) throws Exception; 

	
	public List<MpRelacionado> obtenerRelacionados(MpRelacionado relacionados)
			throws SisatException;
	
	public List<ItemHistoricoEntradaDTO> obtenerExpedientesh(Integer idExpe) throws SisatException; //para historico de expediente
	
	public List<ItemSeguimientoEntradaDTO> seguimientoExpediente(Integer idExpediente) throws SisatException; //para seguimiento expediente detalle

	public List<ItemSeguimientoExpedienteDTO> seguimientoExpedientec(Integer idExpediente) throws SisatException; //para seguimiento expediente cabecera

	public List<TdTipoTramite> getAllTipoTramites() throws SisatException;
	
	//servicios pensionista y adulto
	public List<TdTipoTramite> getTipoTramitesAdult() throws SisatException;

	
	public List<TdTipoTramite> obtenerTipoTramitesPorProcedimientoId(Integer procedimientoId) throws SisatException;

	public List<TdDocumentoTramite> getAllDocumentoTramites() throws SisatException;
	
	//Servicios pensionista y adulto
	
	public List<TdDocumentoTramite> getDocumentoTramitesAdultPens() throws SisatException;

	public List<GnUnidad> getAllGnUnidad() throws SisatException;
	
	public List<TdEstadoExpediente> getAllEstadoExpedientes() throws SisatException;

	public List<TdSituacionExpediente> getMpSituacionExpedientes() throws SisatException; //Situacion para Mesa de parte
	
	public List<TdSituacionExpediente> getOdSituacionExpedientes() throws SisatException; //Situacion para Oficina derivada
	
	public TdExpediente guardarExpediente(int anno,TdExpediente expediente,
			TdRepresentante tdRepresentante,
			List<TdDataValor> listDataValors,
			List<TdRequisitoExpediente> listRequisitoExpedientes,
			Boolean guardarRepresentante,
			List<TdDocumentoAnexo> listTdDocumentoAnexos) throws SisatException;
	
	public TdDJ guardarCorrelativoDj(int anno) throws SisatException;
	
	public TdDJ guardarCorrelativoResolucionDj(int anno) throws SisatException;

//	
//	public TdDJ guardarDJAdult(int anno,TdDJ dj,
//			TdRepresentante tdRepresentante,
//			TdDjConyuge tdDjConyuge,
//			TdDjUnicaPropiedad tdPropiedad,
//			List<TdDataValor> listDataValors,
//			List<TdRequisitoExpediente> listRequisitoExpedientes,
//			Boolean guardarRepresentante,
//			List<TdDocumentoAnexo> listTdDocumentoAnexos) throws SisatException;

	
	public void NuevoTdRemitente(String primerNombre,
			String segundoNombre,
			String apellidoPaterno,
			String apellidoMaterno,
			String razonSocial,
			String nroDoc,
			String direcCompleta,
			int tipoDoc,
			int usuarioId,
			String terminal) throws SisatException;
	
//	public void registrarDJAdultPens(
//			Integer procedimientoId,
//			Integer tipoTramiteId,
//			Integer docuTramiteId,
//			String nroExpedienteGenerico,
//			String nroExpediente,
//			Integer contribuyenteId,
//			String nroDocContribuyente,
//			String nombreApellidosContribuyente,
//			String domicilioFiscalContribuyente,
//	     	Integer unidadId,
//	     	String observacion,
//	     	String nroResolucion,
//     		Date fechaPresentacion,
//     		Date fechaActualizacion, 
//     		Integer nroFolios,
//     		String referencia,
//     		Integer estadoExpediente,
//     		Integer usuarioId,
//     		Integer estado,
//     		Date fechaRegistro,
//     		String terminal,
//     		Integer situacionExpediente,    
//     		Integer relacionadoId, 
//     		String nroDocIdentConyuge,
//     		String nombreApellidosConyuge,
//     		BigDecimal porcentajePart,
//     		Integer fallecido,
//     		Integer requisitoPartId,
//     		Date fechaPartidaDefuncion,
//     		Integer requisitoSucId,
//     		Date fechaSucesionIntestada,
//     		BigDecimal cuotaIdeal,
//     		Integer estadoConyuge,
//     		Integer vivienda,
//     		Integer negocio,
//     		Integer licenciaFuncionamiento,
//     		Integer requisitoLicenciaId) throws SisatException;
	
	
	public void actualizarExpediente(TdExpediente expediente,
			TdRepresentante tdRepresentante,
			List<TdDataValor> listDataValors,
			List<TdRequisitoExpediente> listRequisitoExpedientes,
			Boolean guardarRepresentante,
			List<TdDocumentoAnexo> listTdDocumentoAnexos) throws SisatException;

	public TdExpediente obtenerExpediente(Integer expedienteId) throws SisatException;

	public TdRepresentante obtenerRepresentante(Integer representanteId) throws SisatException;

	public HashMap<String, Integer> getAllProcedimientos() throws SisatException;

	public List<TdDataValor> obtenerDataValorExpediente(Integer expedienteId) throws SisatException;

	public List<TdDocumentoTramite> getAllDocumentoTramitesByTipoTramite(Integer tipoTramiteId) throws SisatException;
	
	//servicios adulto mayor
	public List<TdDocumentoTramite> getDocumentoTramitesByTipoTramiteAdult(Integer tipoTramiteId) throws SisatException;


	public List<TdRequisitoExpediente> getRequisitosByTipoTramiteDocumentoTramite(Integer tipoTramiteId,
			Integer docuTramiteId) throws SisatException;

	public List<TdRequisitoExpediente> obtenerRequisitosExpediente(Integer expedienteId) throws SisatException;

	public void reasignarExpediente(TdExpediente expediente, Integer unidadIdADesignar) throws SisatException;

	public void guardarResolucion(TdExpediente expediente, TdResolucion resolucion) throws SisatException;
	
	public void guardarResultado(TdExpediente expediente, TdResultado tdResultado) throws SisatException;

	public void guardarProyectoResolucion(TdExpediente expediente, TdResolucion resolucion) throws SisatException;

	public TdResolucion obtenerResolucion(Integer expedienteId) throws SisatException;
	
	public TdResultado obtenerResultado(Integer expedienteId) throws SisatException;

	public void guardarDocumentosAnexos(TdExpediente tdExpediente, List<TdDocumentoAnexo> listTdDocumentoAnexos)
			throws SisatException;

	public List<GnTipoDocumento> obtenerTiposDocumentos() throws SisatException;

	public List<TdDocumentoAnexo> obtenerDocumentosAnexos(Integer expedienteId) throws SisatException;
	
	public List<ResolutorDTO> obtenerResolutores(Integer unidadId) throws SisatException;
	
	public void asignarExpedienteAResolutor(TdExpediente tdExpediente, Integer resolutorId)throws SisatException;
	
	public HashMap<Integer, String> obtenerEstadosExpediente() throws SisatException;
	
	public List<TdEstadoExpediente> obtenerEstadoExpedientes()throws SisatException;

	public List<TdSituacionExpediente> obtenerSituacionExpedientes()throws SisatException;
	
	public List<ResumenReporteExpedientesDTO> obtenerResumenExpedientes(Date fechaInicio, Date fechaFin)
			throws SisatException;
	
	public List<ResumenReporteExpedientesDTO> obtenerResumenExpedienteshr(Date fechaInicio, Date fechaFin, Integer ofiOrigen, Integer ofiDestino, Integer procedimientoExpediente, Integer tipoTramite, Integer impreso, Integer situacion)
			throws SisatException;
	
	public String obtenerCorrelativoTablaRegistroNuevoExpediente(String tabla, Integer anno);
	
	public String obtenerCorrelativoTablaRegistroNuevaDj(String tabla, Integer anno);
	
	public String obtenerCorrelativoTablaResolucionNuevaDj(String tabla, Integer anno);


	
	public String obtenerCorrelativoTablaRegistroDJAdult(String tabla, Integer anno);
	
	public void cambioSegReporte(Date fechaIn, Date fechaFi) throws SisatException; //para actualizar flag de exped. impresos todas las areas
	
	public void cambioSegReportea(Date fechaIn, Date fechaFi, Integer undId) throws SisatException; //para actualizar flag de exped. impresos por area
	
	public void estadoDigitaliadoExp(Integer idExpediente) throws SisatException; //actualiza estado de expediente digitalizado
	
	public void estadoDerivadoExp(Integer idExpediente) throws SisatException; //actualiza estado de expediente derivado
	
	public static String msjRemitente=""; //Para mensaje de guardar remitente


	
}