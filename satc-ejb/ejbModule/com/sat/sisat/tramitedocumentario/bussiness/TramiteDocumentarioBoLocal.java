package com.sat.sisat.tramitedocumentario.bussiness;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Local;

import com.sat.sisat.exception.SisatException;
import com.sat.sisat.persistence.entity.GnTipoDocumento;
import com.sat.sisat.persistence.entity.GnUnidad;
import com.sat.sisat.persistence.entity.TdDJ;
import com.sat.sisat.persistence.entity.TdDataValor;
import com.sat.sisat.persistence.entity.TdDocumentoAnexo;
import com.sat.sisat.persistence.entity.TdDocumentoTramite;
import com.sat.sisat.persistence.entity.TdEstadoExpediente;
import com.sat.sisat.persistence.entity.TdExpediente;
import com.sat.sisat.persistence.entity.TdRepresentante;
import com.sat.sisat.persistence.entity.TdRequisitoExpediente;
import com.sat.sisat.persistence.entity.TdResolucion;
import com.sat.sisat.persistence.entity.TdResultado;
import com.sat.sisat.persistence.entity.TdTipoTramite;
import com.sat.sisat.tramitedocumentario.dto.BusquedaExpedienteDTO;
import com.sat.sisat.tramitedocumentario.dto.DeclaracionJuradaAdultDTO;
import com.sat.sisat.tramitedocumentario.dto.ItemBandejaEntradaDTO;
import com.sat.sisat.tramitedocumentario.dto.RequisitoExpededienteDTO;
import com.sat.sisat.tramitedocumentario.dto.ResolutorDTO;
import com.sat.sisat.tramitedocumentario.dto.ResumenReporteExpedientesDTO;

@Local
public interface TramiteDocumentarioBoLocal {

	public List<TdExpediente> getAllTdExpediente();

	public List<ItemBandejaEntradaDTO> obtenerExpedientes(BusquedaExpedienteDTO busquedaExpedienteDTO)
			throws SisatException;

	public List<TdTipoTramite> getAllTipoTramites() throws SisatException;
	
	public List<TdTipoTramite> obtenerTipoTramitesPorProcedimientoId(Integer procedimientoId) throws SisatException;

	public List<TdDocumentoTramite> getAllDocumentoTramites() throws SisatException;

	public List<GnUnidad> getAllGnUnidad() throws SisatException;

	public TdExpediente guardarExpediente(TdExpediente expediente,
			TdRepresentante tdRepresentante,
			List<TdDataValor> listDataValors,
			List<TdRequisitoExpediente> listRequisitoExpedientes,
			Boolean guardarRepresentante,
			List<TdDocumentoAnexo> listTdDocumentoAnexos) throws SisatException ;

	public void actualizarExpediente(TdExpediente expediente,
			TdRepresentante tdRepresentante,
			List<TdDataValor> listDataValors,
			List<TdRequisitoExpediente> listRequisitoExpedientes,
			Boolean guardarRepresentante,
			List<TdDocumentoAnexo> listTdDocumentoAnexos) throws SisatException;
	
	public void nuevaDjAdulto(Date fechaRecepcion,
			String correlativoDJ,
			int tramiteId,
			int documentoTramiteId,
			String nroExpedienteGenerico,
			String nroExpediente,
			int nroFolios,
			int contribuyenteId,
			String nroDocIdentContr,
			String apellidosNombresContr,
			String direccionFiscalContr,
			int relacionadoId,
			String nroDocIdentConyuge,
			String apellidosNombresConyuge,
			BigDecimal porcentajePartConyuge,
			int fallecidoConyuge,
			Date fechaPartidaDefuncion,
			Date fechaSucesionIntestada,
			BigDecimal cuotaIdeal,
			int vivienda,
			int comercio,
			int licenciaFuncionamiento,
			String observacion,
			String nroResolucion,
			int usuarioId,
			int estadoDJ,
			String terminal,
			int situacionDJ,
			int procedimientoId,
			int unidadId,
			int estadoExpediente,
			int djId,
			int requisitoPartId,
			int requisitoSucId,
			int requisitoLicenciaId
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
	
	public DeclaracionJuradaAdultDTO nuevaPropUnicaDjAdult(
			int djId,
			Boolean vivienda,
			Boolean negocio, 
			Boolean licenciaFuncionamiento,
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

	public  Integer obtenerResolutorDjId(int usuarioLogueadoId) throws Exception;
	
	public TdExpediente guardarCorrelativoDj(int anno) throws SisatException;
	
//	public TdExpediente guardarDJAdult(TdDJ dj,
//			TdRepresentante tdRepresentante,
//			List<TdDataValor> listDataValors,
//			List<TdRequisitoExpediente> listRequisitoExpedientes,
//			Boolean guardarRepresentante,
//			List<TdDocumentoAnexo> listTdDocumentoAnexos) throws SisatException ;
//
//	public void actualizarDJAdult(TdDJ dj,
//			TdRepresentante tdRepresentante,
//			List<TdDataValor> listDataValors,
//			List<TdRequisitoExpediente> listRequisitoExpedientes,
//			Boolean guardarRepresentante,
//			List<TdDocumentoAnexo> listTdDocumentoAnexos) throws SisatException;

	public TdExpediente obtenerExpediente(Integer expedienteId) throws SisatException;

	public TdRepresentante obtenerRepresentante(Integer representanteId) throws SisatException;

	public HashMap<String, Integer> getAllProcedimientos() throws SisatException;

	public List<TdDataValor> obtenerDataValorExpediente(Integer expedienteId) throws SisatException;

	public List<TdDocumentoTramite> getAllDocumentoTramitesByTipoTramite(Integer tipoTramiteId) throws SisatException;

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

	public void asignarExpedienteAResolutor(TdExpediente tdExpediente, Integer resolutorId) throws SisatException;

	public HashMap<Integer, String> obtenerEstadosExpediente() throws SisatException;
	
	public List<TdEstadoExpediente> obtenerEstadoExpedientes()throws SisatException;
	
	public List<ResumenReporteExpedientesDTO> obtenerResumenExpedientes(Date fechaInicio, Date fechaFin)
			throws SisatException;
}
