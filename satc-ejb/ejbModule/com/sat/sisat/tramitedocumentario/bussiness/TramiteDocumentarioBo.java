package com.sat.sisat.tramitedocumentario.bussiness;


import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import com.sat.sisat.administracion.parameter.ParameterLoader;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.persistence.entity.GnTipoDocumento;
import com.sat.sisat.persistence.entity.GnUnidad;
import com.sat.sisat.persistence.entity.MpRelacionado;
import com.sat.sisat.persistence.entity.TdAsignacionExpediente;
import com.sat.sisat.persistence.entity.TdDJ;
import com.sat.sisat.persistence.entity.TdDataValor;
import com.sat.sisat.persistence.entity.TdDjConyuge;
import com.sat.sisat.persistence.entity.TdDjUnicaPropiedad;
import com.sat.sisat.persistence.entity.TdDocumentoAnexo;
import com.sat.sisat.persistence.entity.TdDocumentoTramite;
import com.sat.sisat.persistence.entity.TdEstadoExpediente;
import com.sat.sisat.persistence.entity.TdExpediente;
import com.sat.sisat.persistence.entity.TdExpedienteDocumentoAnexo;
import com.sat.sisat.persistence.entity.TdProcedimiento;
import com.sat.sisat.persistence.entity.TdRepresentante;
import com.sat.sisat.persistence.entity.TdRequisito;
import com.sat.sisat.persistence.entity.TdRequisitoExpediente;
import com.sat.sisat.persistence.entity.TdResolucion;
import com.sat.sisat.persistence.entity.TdResolutor;
import com.sat.sisat.persistence.entity.TdResultado;
import com.sat.sisat.persistence.entity.TdTipoTramite;
import com.sat.sisat.persistence.entity.TdSituacionExpediente;
import com.sat.sisat.predial.business.BaseBusiness;
import com.sat.sisat.tramitedocumentario.dao.TramiteDocumentarioDao;
import com.sat.sisat.tramitedocumentario.dto.BusquedaExpedienteDTO;
import com.sat.sisat.tramitedocumentario.dto.DeclaracionJuradaAdultDTO;
import com.sat.sisat.tramitedocumentario.dto.ItemBandejaEntradaDTO;
import com.sat.sisat.tramitedocumentario.dto.ItemHistoricoEntradaDTO;
import com.sat.sisat.tramitedocumentario.dto.ItemSeguimientoEntradaDTO;
import com.sat.sisat.tramitedocumentario.dto.ItemSeguimientoExpedienteDTO;
import com.sat.sisat.tramitedocumentario.dto.RequisitoExpededienteDTO;
import com.sat.sisat.tramitedocumentario.dto.ResolutorDTO;
import com.sat.sisat.tramitedocumentario.dto.ResumenReporteExpedientesDTO;

@Stateless
public class TramiteDocumentarioBo extends BaseBusiness implements TramiteDocumentarioBoRemote {
	private static final long serialVersionUID = 2340865930287738169L;

	private TramiteDocumentarioDao service;

	public final int TIPO_DOCU_EXPEDIENTE = 9;
	
	//Para mensaje de guardar remitente
	public static String msjRemitente;
	
	@Resource
	private SessionContext context;

	@PostConstruct
	public void initialize() {
		this.service = new TramiteDocumentarioDao();
		setDataManager(this.service);
	}

	@Override
	public List<TdExpediente> getAllTdExpediente() {

		StringBuilder sb = new StringBuilder();
		sb.append("from TdExpediente exp");

		Query q = this.em.createQuery(sb.toString());

		@SuppressWarnings("unchecked")
		List<TdExpediente> list = q.getResultList();

		return list;
	}

	public List<ItemBandejaEntradaDTO> obtenerExpedientes(BusquedaExpedienteDTO busquedaExpedienteDTO)
			throws SisatException {

		List<ItemBandejaEntradaDTO> list = new ArrayList<ItemBandejaEntradaDTO>();

		list = service.obtenerExpedientes(busquedaExpedienteDTO);

		return list;
	}
	
	public List<DeclaracionJuradaAdultDTO> obtenerDjAdult(DeclaracionJuradaAdultDTO busquedaDjAdultDTO)
			throws SisatException{

		List<DeclaracionJuradaAdultDTO> listDj = new ArrayList<DeclaracionJuradaAdultDTO>();
		listDj = service.obtenerDjAdult(busquedaDjAdultDTO);

		
		return listDj;

	}
	
	//servicios
	public List<ItemBandejaEntradaDTO> obtenerExpedientesServAdult(BusquedaExpedienteDTO busquedaExpedienteDTO)
			throws SisatException {

		List<ItemBandejaEntradaDTO> list = new ArrayList<ItemBandejaEntradaDTO>();

		list = service.obtenerExpedientesServAdult(busquedaExpedienteDTO);

		return list;
	}
	
	//historico de expedientes
	public List<ItemHistoricoEntradaDTO> obtenerExpedientesh(Integer expedienteId) throws SisatException {
		List<ItemHistoricoEntradaDTO> list = new ArrayList<ItemHistoricoEntradaDTO>();
		list = service.obtenerExpedientesh(expedienteId);
		return list;
	}
	
	//seguimiento expediente detalle
	public List<ItemSeguimientoEntradaDTO> seguimientoExpediente(Integer expedienteId) throws SisatException {
		List<ItemSeguimientoEntradaDTO> list = new ArrayList<ItemSeguimientoEntradaDTO>();
		list = service.seguimientoExpediente(expedienteId);
		return list;
	}
		
	//seguimiento expediente cabecera
	public List<ItemSeguimientoExpedienteDTO> seguimientoExpedientec(Integer expedienteId) throws SisatException {
		List<ItemSeguimientoExpedienteDTO> list = new ArrayList<ItemSeguimientoExpedienteDTO>();
		list = service.seguimientoExpedientec(expedienteId);
		return list;
	}
	
	@Override
	public List<TdTipoTramite> getAllTipoTramites() throws SisatException {

		Query q = em.createQuery("from TdTipoTramite");
		@SuppressWarnings("unchecked")
		List<TdTipoTramite> list = q.getResultList();

		return list;
	}
	
	//servicios adulto mayor
	
	@Override
	public List<TdTipoTramite> getTipoTramitesAdult() throws SisatException {

		Query q = em.createQuery("from TdTipoTramite where tipoTramiteId=29");
		@SuppressWarnings("unchecked")
		List<TdTipoTramite> list = q.getResultList();

		return list;
	}

	@Override
	public List<TdDocumentoTramite> getAllDocumentoTramites() throws SisatException {
		Query q = em.createQuery("from TdDocumentoTramite");
		@SuppressWarnings("unchecked")
		List<TdDocumentoTramite> list = q.getResultList();

		return list;
	}
	
	//servicios adulto y pensionista
	
	@Override
	public List<TdDocumentoTramite> getDocumentoTramitesAdultPens() throws SisatException {
		Query q = em.createQuery("from TdDocumentoTramite WHERE docu_tramite_id IN (20,21)");
		@SuppressWarnings("unchecked")
		List<TdDocumentoTramite> list = q.getResultList();

		return list;
	}
	

	public List<GnUnidad> getAllGnUnidad() throws SisatException {
		Query q = em.createQuery("from GnUnidad WHERE estado = 1");
		@SuppressWarnings("unchecked")
		List<GnUnidad> list = q.getResultList();

		return list;
	}
	
	public List<TdEstadoExpediente> getAllEstadoExpedientes() throws SisatException {
		Query q = em.createQuery("from TdEstadoExpediente where estadoExpedienteId IN (2,10,11,12)");
		@SuppressWarnings("unchecked")
		List<TdEstadoExpediente> list = q.getResultList();

		return list;
	}
	//Para situacion de expediente en mesa de partes
	public List<TdSituacionExpediente> getMpSituacionExpedientes() throws SisatException {
		Query q = em.createQuery("from TdSituacionExpediente where situacionExpedienteId IN (5,9,10,11)");
		@SuppressWarnings("unchecked")
		List<TdSituacionExpediente> list = q.getResultList();

		return list;
	}
	//Para situacion en expediente en Oficina derivada
	public List<TdSituacionExpediente> getOdSituacionExpedientes() throws SisatException {
		Query q = em.createQuery("from TdSituacionExpediente where situacionExpedienteId IN (6,7,8,11,12,13)");
		@SuppressWarnings("unchecked")
		List<TdSituacionExpediente> list = q.getResultList();

		return list;
	}
	
	// @Override
	// public List<TdTipoTramiteRequisito> getAllTipoTramiteDocumento(int idDocumentoTramite, int
	// idTipoTramite)
	// throws SisatException{
	//
	// TdTipoTramiteDocumento tdTipoTramiteDocumento = new TdTipoTramiteDocumento();
	//
	// TdTipoTramiteDocumentoId tdTipoTramiteDocumentoId = new TdTipoTramiteDocumentoId();
	//
	// tdTipoTramiteDocumentoId.setDocuTramiteId(idDocumentoTramite);
	// tdTipoTramiteDocumentoId.setTipoTramiteId(idTipoTramite);
	//
	// tdTipoTramiteDocumento.setId(tdTipoTramiteDocumentoId);
	//
	//
	// Query q =
	// em.createQuery("from TdTipoTramiteRequisito tr where tr.tdTipoTramiteDocumento = :var1");
	// q.setParameter("var1", tdTipoTramiteDocumento);
	//
	// @SuppressWarnings("unchecked")
	// List<TdTipoTramiteRequisito> list = q.getResultList();
	//
	// // List<TdTipoTramiteRequisito> list = new ArrayList<TdTipoTramiteRequisito>();
	// //
	// // list.add(new TdTipoTramiteRequisito());
	// // list.add(new TdTipoTramiteRequisito());
	// // list.add(new TdTipoTramiteRequisito());
	// // list.add(new TdTipoTramiteRequisito());
	// // list.add(new TdTipoTramiteRequisito());
	// return list;
	// }

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public TdExpediente guardarExpediente(int anno,TdExpediente expediente,
			TdRepresentante tdRepresentante,
			List<TdDataValor> listDataValors,
			List<TdRequisitoExpediente> listRequisitoExpedientes,
			Boolean guardarRepresentante,
			List<TdDocumentoAnexo> listTdDocumentoAnexos) throws SisatException {

		try {
			// System.out.println(this.getUser());
			if (guardarRepresentante) {
				tdRepresentante.setEstado("1");
				tdRepresentante.setUsuarioId(getUser().getUsuarioId());
				tdRepresentante.setTerminal(getUser().getTerminal());
				tdRepresentante.setFechaRegistro(DateUtil.getCurrentDateOnly());

				this.em.persist(tdRepresentante);
			}
			/*Se quita por que actualiza aun cuando el td_expediente no se ha guardado*/
			//
			//Integer expedienteId = service.obtenerCorrelativoDoc("td_expediente", anno);
			Integer expedienteId = service.obtenerCorrelativoDocTdExpediente(anno);			
			
			Integer expedienteIdFinal = service.obtenerIdMaximo("td_expediente", anno);
			
			expediente.setExpedienteId(expedienteIdFinal);
	

			if (guardarRepresentante) {
				expediente.setRepresentanteId(tdRepresentante.getRepresentanteId());
			}

			/**
			 * Variables de auditoria
			 */
			expediente.setUsuarioId(getUser().getUsuarioId());
			expediente.setTerminal(getUser().getTerminal());
			expediente.setFechaRegistro(DateUtil.getCurrentDateOnly());
			expediente.setFechaActualizacion(DateUtil.getCurrentDateOnly());

			/** ------------------------------------------s */
			int _nroExpediente = this.service.ObtenerCorrelativoDocumentoPorUnidadYTipo(expediente.getUnidadId(), TIPO_DOCU_EXPEDIENTE, 1);
			
			int nroUnidad= this.service.ObtenerCodigoUnidadPorTramo(expediente.getUnidadId(), expediente.getFechaPresentacion());
			
			int nroDocumento= this.service.ObtenerCodigoDocumentoPorTramo(TIPO_DOCU_EXPEDIENTE, expediente.getFechaPresentacion());
			

			DecimalFormat decimalFormatUndTipoDoc = new DecimalFormat(ParameterLoader
					.getParameter("patternUnidad_TipoDoc").concat("-"));
			DecimalFormat decimalFormatSecuence = new DecimalFormat(ParameterLoader.getParameter("patternSequenceDoc"));

			DecimalFormat formatoNroExpedienteGenerico = new DecimalFormat(ParameterLoader.getParameter("patternSequenceDoc"));
			
			String nroExpedienteGenerico = formatoNroExpedienteGenerico.format(expedienteId);
			
			/** Cod. anterior - Comentado en Abril - 2016**/
//			String nroExpediente = decimalFormatUndTipoDoc.format(expediente.getUnidadId()).concat(decimalFormatUndTipoDoc.format(TIPO_DOCU_EXPEDIENTE))
//					.concat(decimalFormatSecuence.format(_nroExpediente));
			
			String nroExpediente = decimalFormatUndTipoDoc.format(nroUnidad).concat(decimalFormatUndTipoDoc.format(nroDocumento))
					.concat(decimalFormatSecuence.format(_nroExpediente));

			expediente.setNroExpedienteGenerico(nroExpedienteGenerico);
			expediente.setNroExpediente(nroExpediente);
			
			//Guardar datos de nombre y ubicacion de archivo digitalizado
			DecimalFormat formatoNombreExpediente = new DecimalFormat(ParameterLoader.getParameter("patternSequenceDoc"));
			DecimalFormat formatoNombreEmisor = new DecimalFormat(ParameterLoader.getParameter("patternSequenceDoc"));
			String expId, expNom, expCarpeta;
					
			expId = formatoNombreExpediente.format(expedienteIdFinal);
			if (expediente.getContribuyenteId()>0){
				expNom = formatoNombreEmisor.format(expediente.getContribuyenteId());
				expCarpeta = Integer.toString(expediente.getContribuyenteId()).concat("/");
			}
			else {
				expNom = formatoNombreEmisor.format(expediente.getRepresentanteId());
				expCarpeta = Integer.toString(expediente.getRepresentanteId()).concat("/");
			}
			
			String nombreArchivo = expId.concat(expNom); //nombre de expediente digitalizado
			
			expediente.setArchivoNombre(nombreArchivo);			
			expediente.setArchivoUbicacion("//172.26.128.130/expedientes/"+expCarpeta); //Ubicacion de expediente digitalizado
			
			this.em.persist(expediente);
			
			//Guardar valor de correlativo expediente generico en tabla gn_correlativo_doc	de td_expediente
			service.actualizaValorNroExpedienteGenerico(anno);
			
			for (TdDataValor tdDataValor : listDataValors) {
				tdDataValor.setExpedienteId(expedienteId);

				// variables de control
				tdDataValor.setUsuarioId(getUser().getUsuarioId());
				tdDataValor.setTerminal(getUser().getTerminal());
				tdDataValor.setEstado("1");
				tdDataValor.setFechaRegistro(DateUtil.getCurrentDateOnly());

				this.em.persist(tdDataValor);
			}
			for (TdRequisitoExpediente tdRequisitoExpediente : listRequisitoExpedientes) {
				tdRequisitoExpediente.setExpedienteId(expedienteIdFinal);
				tdRequisitoExpediente.setUsuarioId(getUser().getUsuarioId());
				tdRequisitoExpediente.setTerminal(getUser().getTerminal());
				tdRequisitoExpediente.setEstado("1");
				tdRequisitoExpediente.setFechaRegistro(DateUtil.getCurrentDateOnly());
				this.em.persist(tdRequisitoExpediente);
			}

			Date fechaProcesamiento = DateUtil.getCurrentDateOnly();

			for (TdDocumentoAnexo tdDocumentoAnexo : listTdDocumentoAnexos) {

				if (tdDocumentoAnexo.isPendienteGuardado()) {
					tdDocumentoAnexo.setEstado("1");
					tdDocumentoAnexo.setUsuarioId(getUser().getUsuarioId());
					tdDocumentoAnexo.setFechaRegistro(fechaProcesamiento);
					tdDocumentoAnexo.setTerminal(getUser().getTerminal());
					
					em.persist(tdDocumentoAnexo);

					TdExpedienteDocumentoAnexo tdExpedienteDocumentoAnexo = new TdExpedienteDocumentoAnexo();

					tdExpedienteDocumentoAnexo.setFechaAsignacion(fechaProcesamiento);
					tdExpedienteDocumentoAnexo.setEstado("1");
					tdExpedienteDocumentoAnexo.setUsuarioId(getUser().getUsuarioId());
					tdExpedienteDocumentoAnexo.setFechaRegistro(fechaProcesamiento);
					tdExpedienteDocumentoAnexo.setTerminal(getUser().getTerminal());

					tdExpedienteDocumentoAnexo.setExpedienteId(expediente.getExpedienteId());
					tdExpedienteDocumentoAnexo.setDocumentoAnexoId(tdDocumentoAnexo.getDocumentoAnexoId());

					em.persist(tdExpedienteDocumentoAnexo);
				}
			}			
			
			try{
			
			// Expediente asignado en mesa de partes desde el registro.
			//Seleccionar Tramitador. Rol 0 (Rol Cero permite varios tramitadores, no existen oficina menos responsable de area)
				int unidad = 66; //unidad de mesa de partes. No se debe cambiar valor en Base de Datos, valor por defecto 66...!!!
			Query q = this.em
					.createQuery("from TdResolutor resol where resol.estado = 1 and resol.fechaInicio < :fechaConsulta and resol.fechaFin > :fechaConsulta and resol.rol = 0 and resol.unidadId = :unidadId and resol.usuarioResolutorId = :usuarioRe");
			q.setParameter("fechaConsulta", DateUtil.getCurrentDateOnly());
			q.setParameter("unidadId", unidad);
			q.setParameter("usuarioRe",getUser().getUsuarioId());	
			
			
			TdResolutor tdResolutor = (TdResolutor) q.getSingleResult();
			
			if (tdResolutor != null) {

				TdAsignacionExpediente tdAsignacionExpediente = new TdAsignacionExpediente();

				tdAsignacionExpediente.setExpedienteId(expediente.getExpedienteId());
				tdAsignacionExpediente.setResolutorId(tdResolutor.getResolutorId());
				tdAsignacionExpediente.setUnidadId(tdResolutor.getUnidadId());
				tdAsignacionExpediente.setEstado(Constante.ESTADO_ACTIVO);
				tdAsignacionExpediente.setUsuarioId(getUser().getUsuarioId());
				tdAsignacionExpediente.setFechaRegistro(DateUtil.getCurrentDateOnly());
				tdAsignacionExpediente.setTerminal(getUser().getTerminal());				
				tdAsignacionExpediente.setEstadoExpediente(expediente.getEstadoExpediente());
				tdAsignacionExpediente.setSituacionExpediente(expediente.getSituacionExpediente());

				this.em.persist(tdAsignacionExpediente);
			}				
			}catch (NoResultException e) {
				context.setRollbackOnly();
				throw new SisatException("Aun no se tiene asignado responsable en Mesa de Partes. Para proseguir asignar Responsable en Mesa de Partes ".concat(e.getMessage()));
			}			
			
		}catch (Exception e) {
			context.setRollbackOnly();
			throw new SisatException("Error en la persistencia del expediente. ".concat(e.getMessage()));
		}
		
		return expediente;
	}

	
//	@Override
//	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
//	public TdDJ guardarDJAdult(int anno,TdDJ dj,
//			TdRepresentante tdRepresentante,
//			TdDjConyuge tdDjConyuge,
//			TdDjUnicaPropiedad tdDjUnicaPropiedad,
//			List<TdDataValor> listDataValors,
//			List<TdRequisitoExpediente> listRequisitoExpedientes,
//			Boolean guardarRepresentante,
//			List<TdDocumentoAnexo> listTdDocumentoAnexos) throws SisatException {
//
//		try {
//			// System.out.println(this.getUser());
//			if (guardarRepresentante) {
//				tdRepresentante.setEstado("1");
//				tdRepresentante.setUsuarioId(getUser().getUsuarioId());
//				tdRepresentante.setTerminal(getUser().getTerminal());
//				tdRepresentante.setFechaRegistro(DateUtil.getCurrentDateOnly());
//
//				this.em.persist(tdRepresentante);
//			}
//			/*Se quita por que actualiza aun cuando el td_expediente no se ha guardado*/
//			//
//			//Integer expedienteId = service.obtenerCorrelativoDoc("td_expediente", anno);
//			Integer djId = service.obtenerCorrelativoDocTdExpediente(anno);			
//			
//			Integer expedienteIdFinal = service.obtenerIdMaximo("td_expediente", anno);
//			
//			dj.setDjId(expedienteIdFinal);
//
//			if (guardarRepresentante) {
//				dj.setRepresentanteId(tdRepresentante.getRepresentanteId());
//			}
//
//			/**
//			 * Variables de auditoria
//			 */
//			dj.setUsuarioId(getUser().getUsuarioId());
//			dj.setTerminal(getUser().getTerminal());
//			dj.setFechaRegistro(DateUtil.getCurrentDateOnly());
//			dj.setFechaActualizacion(DateUtil.getCurrentDateOnly());
//
//			/** ------------------------------------------s */
//			int _nroExpediente = this.service.ObtenerCorrelativoDocumentoPorUnidadYTipo(dj.getUnidadId(), TIPO_DOCU_EXPEDIENTE, 1);
//			
//			int nroUnidad= this.service.ObtenerCodigoUnidadPorTramo(dj.getUnidadId(), dj.getFechaPresentacion());
//			
//			int nroDocumento= this.service.ObtenerCodigoDocumentoPorTramo(TIPO_DOCU_EXPEDIENTE, dj.getFechaPresentacion());
//			
//
//			DecimalFormat decimalFormatUndTipoDoc = new DecimalFormat(ParameterLoader
//					.getParameter("patternUnidad_TipoDoc").concat("-"));
//			DecimalFormat decimalFormatSecuence = new DecimalFormat(ParameterLoader.getParameter("patternSequenceDoc"));
//
//			DecimalFormat formatoNroExpedienteGenerico = new DecimalFormat(ParameterLoader.getParameter("patternSequenceDoc"));
//			
//			String nroExpedienteGenerico = formatoNroExpedienteGenerico.format(djId);
//		
//			String nroExpediente = decimalFormatUndTipoDoc.format(nroUnidad).concat(decimalFormatUndTipoDoc.format(nroDocumento))
//					.concat(decimalFormatSecuence.format(_nroExpediente));
//
//			dj.setNroExpedienteGenerico(nroExpedienteGenerico);
//			dj.setNroExpediente(nroExpediente);
//			
//			//Guardar datos de nombre y ubicacion de archivo digitalizado
//			DecimalFormat formatoNombreExpediente = new DecimalFormat(ParameterLoader.getParameter("patternSequenceDoc"));
//			DecimalFormat formatoNombreEmisor = new DecimalFormat(ParameterLoader.getParameter("patternSequenceDoc"));
//			String expId, expNom, expCarpeta;
//					
//			expId = formatoNombreExpediente.format(expedienteIdFinal);
//			if (dj.getContribuyenteId()>0){
//				expNom = formatoNombreEmisor.format(dj.getContribuyenteId());
//				expCarpeta = Integer.toString(dj.getContribuyenteId()).concat("/");
//			}
//			else {
//				expNom = formatoNombreEmisor.format(dj.getRepresentanteId());
//				expCarpeta = Integer.toString(dj.getRepresentanteId()).concat("/");
//			}
//			
//			String nombreArchivo = expId.concat(expNom); //nombre de expediente digitalizado
//			
//			dj.setArchivoNombre(nombreArchivo);			
//			dj.setArchivoUbicacion("//172.26.128.130/expedientes/"+expCarpeta); //Ubicacion de expediente digitalizado
//			
//			this.em.persist(dj);
//			
//			//Guardar valor de correlativo expediente generico en tabla gn_correlativo_doc	de td_expediente
//			service.actualizaValorNroExpedienteGenerico(anno);
//			
//			for (TdDataValor tdDataValor : listDataValors) {
//				tdDataValor.setExpedienteId(djId);
//
//				// variables de control
//				tdDataValor.setUsuarioId(getUser().getUsuarioId());
//				tdDataValor.setTerminal(getUser().getTerminal());
//				tdDataValor.setEstado("1");
//				tdDataValor.setFechaRegistro(DateUtil.getCurrentDateOnly());
//
//				this.em.persist(tdDataValor);
//			}
//			for (TdRequisitoExpediente tdRequisitoExpediente : listRequisitoExpedientes) {
//				tdRequisitoExpediente.setExpedienteId(expedienteIdFinal);
//				tdRequisitoExpediente.setUsuarioId(getUser().getUsuarioId());
//				tdRequisitoExpediente.setTerminal(getUser().getTerminal());
//				tdRequisitoExpediente.setEstado("1");
//				tdRequisitoExpediente.setFechaRegistro(DateUtil.getCurrentDateOnly());
//				this.em.persist(tdRequisitoExpediente);
//			}
//
//			Date fechaProcesamiento = DateUtil.getCurrentDateOnly();
//
//			for (TdDocumentoAnexo tdDocumentoAnexo : listTdDocumentoAnexos) {
//
//				if (tdDocumentoAnexo.isPendienteGuardado()) {
//					tdDocumentoAnexo.setEstado("1");
//					tdDocumentoAnexo.setUsuarioId(getUser().getUsuarioId());
//					tdDocumentoAnexo.setFechaRegistro(fechaProcesamiento);
//					tdDocumentoAnexo.setTerminal(getUser().getTerminal());
//					
//					em.persist(tdDocumentoAnexo);
//
//					TdExpedienteDocumentoAnexo tdExpedienteDocumentoAnexo = new TdExpedienteDocumentoAnexo();
//
//					tdExpedienteDocumentoAnexo.setFechaAsignacion(fechaProcesamiento);
//					tdExpedienteDocumentoAnexo.setEstado("1");
//					tdExpedienteDocumentoAnexo.setUsuarioId(getUser().getUsuarioId());
//					tdExpedienteDocumentoAnexo.setFechaRegistro(fechaProcesamiento);
//					tdExpedienteDocumentoAnexo.setTerminal(getUser().getTerminal());
//
//					tdExpedienteDocumentoAnexo.setExpedienteId(dj.getDjId());
//					tdExpedienteDocumentoAnexo.setDocumentoAnexoId(tdDocumentoAnexo.getDocumentoAnexoId());
//
//					em.persist(tdExpedienteDocumentoAnexo);
//				}
//			}			
//			
//			try{
//			
//			// Expediente asignado en mesa de partes desde el registro.
//			//Seleccionar Tramitador. Rol 0 (Rol Cero permite varios tramitadores, no existen oficina menos responsable de area)
//				int unidad = 66; //unidad de mesa de partes. No se debe cambiar valor en Base de Datos, valor por defecto 66...!!!
//			Query q = this.em
//					.createQuery("from TdResolutor resol where resol.estado = 1 and resol.fechaInicio < :fechaConsulta and resol.fechaFin > :fechaConsulta and resol.rol = 0 and resol.unidadId = :unidadId and resol.usuarioResolutorId = :usuarioRe");
//			q.setParameter("fechaConsulta", DateUtil.getCurrentDateOnly());
//			q.setParameter("unidadId", unidad);
//			q.setParameter("usuarioRe",getUser().getUsuarioId());	
//			
//			
//			TdResolutor tdResolutor = (TdResolutor) q.getSingleResult();
//			
//			if (tdResolutor != null) {
//
//				TdAsignacionExpediente tdAsignacionExpediente = new TdAsignacionExpediente();
//
//				tdAsignacionExpediente.setExpedienteId(dj.getDjId());
//				tdAsignacionExpediente.setResolutorId(tdResolutor.getResolutorId());
//				tdAsignacionExpediente.setUnidadId(tdResolutor.getUnidadId());
//				tdAsignacionExpediente.setEstado(Constante.ESTADO_ACTIVO);
//				tdAsignacionExpediente.setUsuarioId(getUser().getUsuarioId());
//				tdAsignacionExpediente.setFechaRegistro(DateUtil.getCurrentDateOnly());
//				tdAsignacionExpediente.setTerminal(getUser().getTerminal());				
//				tdAsignacionExpediente.setEstadoExpediente(dj.getEstadoExpediente());
//				tdAsignacionExpediente.setSituacionExpediente(dj.getSituacionExpediente());
//
//				this.em.persist(tdAsignacionExpediente);
//			}				
//			}catch (NoResultException e) {
//				context.setRollbackOnly();
//				throw new SisatException("Aun no se tiene asignado responsable en Mesa de Partes. Para proseguir asignar Responsable en Mesa de Partes ".concat(e.getMessage()));
//			}			
//			
//		}catch (Exception e) {
//			context.setRollbackOnly();
//			throw new SisatException("Error en la persistencia del expediente. ".concat(e.getMessage()));
//		}
//		
//		return dj;
//	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void actualizarExpediente(TdExpediente expediente,
			TdRepresentante tdRepresentante,
			List<TdDataValor> listDataValors,
			List<TdRequisitoExpediente> listRequisitoExpedientes,
			Boolean guardarRepresentante,
			List<TdDocumentoAnexo> listTdDocumentoAnexos) throws SisatException {

		try {
			// System.out.println(this.getUser());
			//buscando representante
			
			//caso que no tenia representante y ahora si lo tiene
			if(expediente.getRepresentanteId() == null && guardarRepresentante){
				
				tdRepresentante.setEstado("1");
				tdRepresentante.setUsuarioId(getUser().getUsuarioId());
				tdRepresentante.setTerminal(getUser().getTerminal());
				tdRepresentante.setFechaRegistro(DateUtil.getCurrentDateOnly());

				this.em.persist(tdRepresentante);

				//caso que si tenia representante y ahora no lo tiene
			}else if(expediente.getRepresentanteId() != null && !guardarRepresentante){
				//borrar representante de la tabla de representantes
				tdRepresentante = em.find(TdRepresentante.class, tdRepresentante.getRepresentanteId());
				this.em.remove(tdRepresentante);				
				expediente.setRepresentanteId(null);
				//caso tenia representante y ahora tambien guardara representante
			}else if(expediente.getRepresentanteId() != null && guardarRepresentante){
				
				//caso que se trata de otro representante registramos el otro representante
				if(!expediente.getRepresentanteId().equals(tdRepresentante.getRepresentanteId())){
					
					// Removiendo el representante anterior
					TdRepresentante tempRep = new TdRepresentante();
					tempRep.setRepresentanteId(expediente.getRepresentanteId());
					
					this.em.remove(tempRep);
					
					
					//Registrando el nuevo representante
					tdRepresentante.setEstado("1");
					tdRepresentante.setUsuarioId(getUser().getUsuarioId());
					tdRepresentante.setTerminal(getUser().getTerminal());
					tdRepresentante.setFechaRegistro(DateUtil.getCurrentDateOnly());

					this.em.persist(tdRepresentante);
				}
				
			}
			
			//no tenia representante y tampoco lo quiere no se realiza ningun cambio
			
			
			// seteando el nuevo representante si es que lo hubiese
			if (guardarRepresentante) {
				expediente.setRepresentanteId(tdRepresentante.getRepresentanteId());
			}

			/**
			 * Variables de auditoria
			 */
			expediente.setUsuarioId(getUser().getUsuarioId());
			expediente.setTerminal(getUser().getTerminal());			
			expediente.setFechaActualizacion(DateUtil.getCurrentDateOnly());
			
			this.em.merge(expediente);
			
			Query q = this.em.createQuery("delete from TdDataValor dataValor where dataValor.expedienteId = :expedienteId");
			q.setParameter("expedienteId", expediente.getExpedienteId());
			
			q.executeUpdate();


			for (TdDataValor tdDataValor : listDataValors) {
				
				tdDataValor.setExpedienteId(expediente.getExpedienteId());
				// variables de control
				tdDataValor.setUsuarioId(getUser().getUsuarioId());
				tdDataValor.setTerminal(getUser().getTerminal());
				tdDataValor.setEstado("1");
				tdDataValor.setFechaRegistro(DateUtil.getCurrentDateOnly());
				this.em.merge(tdDataValor);
			}

			q = this.em.createQuery("delete from TdRequisitoExpediente reqExpediente where reqExpediente.expedienteId = :expedienteId");
			q.setParameter("expedienteId", expediente.getExpedienteId());
			
			q.executeUpdate();
			
			for (TdRequisitoExpediente tdRequisitoExpediente : listRequisitoExpedientes) {
				tdRequisitoExpediente.setExpedienteId(expediente.getExpedienteId());
				tdRequisitoExpediente.setUsuarioId(getUser().getUsuarioId());
				tdRequisitoExpediente.setTerminal(getUser().getTerminal());
				tdRequisitoExpediente.setEstado("1");
				tdRequisitoExpediente.setFechaRegistro(DateUtil.getCurrentDateOnly());
				this.em.merge(tdRequisitoExpediente);
			}

			Date fechaProcesamiento = DateUtil.getCurrentDateOnly();
			
			
			//desvinculando el anexo del expediente			
			q = this.em.createQuery("update TdExpedienteDocumentoAnexo expDocAnexo set expDocAnexo.estado = 9 where expDocAnexo.expedienteId = :expedienteId");
			q.setParameter("expedienteId", expediente.getExpedienteId());
			
			q.executeUpdate();
			
			/* Buscando al responsable de area segun area del expediente */
			Query qr = this.em
					.createQuery("from TdResolutor resol where resol.estado = 1 and resol.fechaInicio < :fechaConsulta and resol.fechaFin > :fechaConsulta and resol.rol = 1 and resol.unidadId = :unidadId");
			qr.setParameter("fechaConsulta", DateUtil.getCurrentDateOnly());
			qr.setParameter("unidadId", expediente.getUnidadId());

			TdResolutor tdResolutor = (TdResolutor) qr.getSingleResult();
			
			/* --Actualizar asignación expediente-- */
			//Actualiza mismo registro si aun no se deriva
			if (expediente.getSituacionExpediente()<Constante.SITUACION_EXPEDIENTE_DERIVADO){
				q = this.em.createQuery("update TdAsignacionExpediente set usuarioId = :usuId, fechaRegistro = :fecReg, terminal = :ter, estadoExpediente = :esExp, situacionExpediente = :siExp where expedienteId = :expId");
				
				q.setParameter("usuId", getUser().getUsuarioId());
				q.setParameter("fecReg", DateUtil.getCurrentDateOnly());
				q.setParameter("ter", getUser().getTerminal());				
				q.setParameter("esExp", expediente.getEstadoExpediente());
				q.setParameter("siExp", expediente.getSituacionExpediente());
				q.setParameter("expId", expediente.getExpedienteId());
				
				q.executeUpdate();
			}
			//Crea nuevo registro luego de la derivación
			else {
				TdAsignacionExpediente tdAsignacionExpediente = new TdAsignacionExpediente();

				tdAsignacionExpediente.setExpedienteId(expediente.getExpedienteId());
				tdAsignacionExpediente.setResolutorId(tdResolutor.getResolutorId());
				tdAsignacionExpediente.setUnidadId(tdResolutor.getUnidadId());
				tdAsignacionExpediente.setEstado(Constante.ESTADO_ACTIVO);
				tdAsignacionExpediente.setUsuarioId(getUser().getUsuarioId());
				tdAsignacionExpediente.setFechaRegistro(DateUtil.getCurrentDateOnly());
				tdAsignacionExpediente.setTerminal(getUser().getTerminal());
				tdAsignacionExpediente.setEstadoExpediente(expediente.getEstadoExpediente());
				tdAsignacionExpediente.setSituacionExpediente(expediente.getSituacionExpediente());

				this.em.persist(tdAsignacionExpediente);
			}
			
			for (TdDocumentoAnexo tdDocumentoAnexo : listTdDocumentoAnexos) {

					tdDocumentoAnexo.setEstado("1");
					tdDocumentoAnexo.setUsuarioId(getUser().getUsuarioId());
					tdDocumentoAnexo.setFechaRegistro(fechaProcesamiento);
					tdDocumentoAnexo.setTerminal(getUser().getTerminal());

						
					if(tdDocumentoAnexo.getDocumentoAnexoId() != null){
						// para actualizacion
						em.merge(tdDocumentoAnexo);
					}else{
						// para registrar nuevos
						em.persist(tdDocumentoAnexo);	
					}
					
					TdExpedienteDocumentoAnexo tdExpedienteDocumentoAnexo = new TdExpedienteDocumentoAnexo();

					tdExpedienteDocumentoAnexo.setFechaAsignacion(fechaProcesamiento);
					tdExpedienteDocumentoAnexo.setEstado("1");
					tdExpedienteDocumentoAnexo.setUsuarioId(getUser().getUsuarioId());
					tdExpedienteDocumentoAnexo.setFechaRegistro(fechaProcesamiento);
					tdExpedienteDocumentoAnexo.setTerminal(getUser().getTerminal());

					tdExpedienteDocumentoAnexo.setExpedienteId(expediente.getExpedienteId());
					tdExpedienteDocumentoAnexo.setDocumentoAnexoId(tdDocumentoAnexo.getDocumentoAnexoId());

					em.merge(tdExpedienteDocumentoAnexo);
				
			}

		}catch (RuntimeException e) {
			context.setRollbackOnly();
			throw new SisatException("Error en la persistencia del expediente. ".concat(e.getMessage()));
		}

	}

	
	public TdExpediente obtenerExpediente(Integer expedienteId) throws SisatException {

		TdExpediente e = null;
		try {
			Query q = this.em.createQuery("from TdExpediente e where e.expedienteId = :expedienteId");

			q.setParameter("expedienteId", expedienteId);

			e = (TdExpediente) q.getSingleResult();

		} catch (Exception ex) {
			throw new SisatException(ex.getMessage());
		}

		return e;
	}
	
	public TdRepresentante obtenerRepresentante(Integer representanteId) throws SisatException {

		TdRepresentante tdRepresentante = new TdRepresentante();

		try {
			Query q = this.em
					.createQuery("from TdRepresentante r where r.estado = 1 and r.representanteId = :representanteId");

			q.setParameter("representanteId", representanteId);

			tdRepresentante = (TdRepresentante) q.getSingleResult();

		} catch (NoResultException e) {

		} catch (RuntimeException ex) {
			throw new SisatException(ex.getMessage());
		}

		return tdRepresentante;

	}

	@SuppressWarnings("unchecked")
	public List<TdDataValor> obtenerDataValorExpediente(Integer expedienteId) throws SisatException {

		List<TdDataValor> listTdDataValors = new ArrayList<TdDataValor>();
		try {
			Query q = this.em
					.createQuery("from TdDataValor datae where datae.expedienteId = :expedienteId and datae.estado = 1");

			q.setParameter("expedienteId", expedienteId);

			listTdDataValors = q.getResultList();
			
			for(TdDataValor data:listTdDataValors){
				data.setEnEdicion(false);
			}
			

		} catch (Exception ex) {
			throw new SisatException(ex.getMessage());
		}

		return listTdDataValors;
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Integer> getAllProcedimientos() throws SisatException {
		HashMap<String, Integer> procedimientos = new HashMap<String, Integer>();
		List<TdProcedimiento> lista = new ArrayList<TdProcedimiento>();

		Query q = this.em.createQuery("from TdProcedimiento pro where pro.estado = 1");

		lista = q.getResultList();

		for (TdProcedimiento tdProcedimiento : lista) {
			procedimientos.put(tdProcedimiento.getDescripcion(), tdProcedimiento.getProcedimientoId());
		}
		return procedimientos;
	}

	public List<TdDocumentoTramite> getAllDocumentoTramitesByTipoTramite(Integer tipoTramiteId) throws SisatException {

		return service.getAllDocumentoTramitesByTipoTramite(tipoTramiteId);
	}
	
	//servicios adulto mayor
	public List<TdDocumentoTramite> getDocumentoTramitesByTipoTramiteAdult(Integer tipoTramiteId) throws SisatException {

		return service.getDocumentoTramitesByTipoTramiteAdult(tipoTramiteId);
	}


	public List<TdRequisitoExpediente> getRequisitosByTipoTramiteDocumentoTramite(Integer tipoTramiteId,
			Integer docuTramiteId) throws SisatException {

		List<TdRequisitoExpediente> listTdRequisitoExpedientes = new ArrayList<TdRequisitoExpediente>();
		List<TdRequisito> listTdRequisitos = new ArrayList<TdRequisito>();

		listTdRequisitos = service.getRequisitosByTipoTramiteDocumentoTramite(tipoTramiteId, docuTramiteId);

		for (TdRequisito requisito : listTdRequisitos) {
			TdRequisitoExpediente tdRequisitoExpediente = new TdRequisitoExpediente();

			tdRequisitoExpediente.setDescripcionRequisito(requisito.getDescripcion());
			tdRequisitoExpediente.setRequisitoId(requisito.getRequisitoId());

			listTdRequisitoExpedientes.add(tdRequisitoExpediente);

		}

		return listTdRequisitoExpedientes;
	}
	

	public List<TdRequisitoExpediente> obtenerRequisitosExpediente(Integer expedienteId) throws SisatException {

		return service.obtenerRequisitosExpediente(expedienteId);
	}

	public void reasignarExpediente(TdExpediente tdExpediente, Integer unidadIdADesignar) throws SisatException {
				
		tdExpediente.setUnidadId(unidadIdADesignar);
		tdExpediente.setUsuarioId(this.getUser().getUsuarioId());
		tdExpediente.setFechaActualizacion(DateUtil.getCurrentDateOnly());
		tdExpediente.setTerminal(this.getUser().getTerminal());

		this.em.merge(tdExpediente);
		
		/* Buscando al responsable de area segun area del expediente */
		Query qr = this.em
				.createQuery("from TdResolutor resol where resol.estado = 1 and resol.fechaInicio < :fechaConsulta and resol.fechaFin > :fechaConsulta and resol.rol = 1 and resol.unidadId = :unidadId");
		qr.setParameter("fechaConsulta", DateUtil.getCurrentDateOnly());
		qr.setParameter("unidadId", unidadIdADesignar);

		TdResolutor tdResolutor = (TdResolutor) qr.getSingleResult();
		
		TdAsignacionExpediente tdAsignacionExpediente = new TdAsignacionExpediente();
		
		tdAsignacionExpediente.setExpedienteId(tdExpediente.getExpedienteId());
		tdAsignacionExpediente.setResolutorId(tdResolutor.getResolutorId());
		tdAsignacionExpediente.setSituacionExpediente(Constante.SITUACION_EXPEDIENTE_DERIVADO);
		tdAsignacionExpediente.setEstadoExpediente(tdExpediente.getEstadoExpediente());
		tdAsignacionExpediente.setUnidadId(unidadIdADesignar);

		tdAsignacionExpediente.setEstado(Constante.ESTADO_ACTIVO);
		tdAsignacionExpediente.setUsuarioId(getUser().getUsuarioId());
		tdAsignacionExpediente.setFechaRegistro(DateUtil.getCurrentDateOnly());
		tdAsignacionExpediente.setTerminal(getUser().getTerminal());

		this.em.persist(tdAsignacionExpediente);
	}

	public void guardarResolucion(TdExpediente expediente, TdResolucion resolucion) throws SisatException {

		try {
			expediente.setUsuarioId(getUser().getUsuarioId());
			expediente.setFechaActualizacion(DateUtil.getCurrentDateOnly());
			expediente.setTerminal(getUser().getTerminal());
			expediente.setEstadoExpediente(Constante.ESTADO_EXPEDIENTE_RESUELTO);

			resolucion.setExpedienteId(expediente.getExpedienteId());

			resolucion.setEstado("1");
			resolucion.setUsuarioId(getUser().getUsuarioId());
			resolucion.setFechaRegistro(DateUtil.getCurrentDateOnly());
			resolucion.setTerminal(getUser().getTerminal());

			this.em.merge(expediente);
			this.em.merge(resolucion);
		} catch (RuntimeException e) {
			throw new SisatException(e.getMessage());
		}

	}

	public void guardarResultado(TdExpediente expediente, TdResultado tdResultado) throws SisatException {
		try {
			expediente.setUsuarioId(getUser().getUsuarioId());
			expediente.setFechaActualizacion(DateUtil.getCurrentDateOnly());
			expediente.setTerminal(getUser().getTerminal());
			expediente.setEstadoExpediente(Constante.ESTADO_EXPEDIENTE_ATENDIDO);

			tdResultado.setExpedienteId(expediente.getExpedienteId());

			tdResultado.setEstado("1");
			tdResultado.setUsuarioId(getUser().getUsuarioId());
			tdResultado.setFechaRegistro(DateUtil.getCurrentDateOnly());
			tdResultado.setTerminal(getUser().getTerminal());

			this.em.merge(expediente);
			this.em.merge(tdResultado);
		} catch (RuntimeException e) {
			throw new SisatException(e.getMessage());
		}

	}

	public void guardarProyectoResolucion(TdExpediente expediente, TdResolucion resolucion) throws SisatException {

		expediente.setUsuarioId(getUser().getUsuarioId());
		expediente.setFechaActualizacion(DateUtil.getCurrentDateOnly());
		expediente.setTerminal(getUser().getTerminal());
		expediente.setEstadoExpediente(Constante.ESTADO_EXPEDIENTE_PEND_APROBACION);

		resolucion.setExpedienteId(expediente.getExpedienteId());

		resolucion.setEstado("1");
		resolucion.setUsuarioId(getUser().getUsuarioId());
		resolucion.setFechaRegistro(DateUtil.getCurrentDateOnly());
		resolucion.setTerminal(getUser().getTerminal());

		this.em.merge(expediente);
		this.em.persist(resolucion);

	}

	@Override
	public TdResolucion obtenerResolucion(Integer expedienteId) throws SisatException {

		TdResolucion tdResolucion = new TdResolucion();
		try {
			Query q = em
					.createQuery("from TdResolucion resol where resol.estado = 1 and resol.expedienteId = :expedienteId");
			q.setParameter("expedienteId", expedienteId);

			tdResolucion = (TdResolucion) q.getSingleResult();
		} catch (NoResultException e) {
			throw new SisatException("El expediente no tiene una resolución.");

		} catch (NonUniqueResultException e) {
			throw new SisatException("El expediente contiene mas de una resolución.");
		}

		return tdResolucion;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarDocumentosAnexos(TdExpediente tdExpediente, List<TdDocumentoAnexo> listTdDocumentoAnexos)
			throws SisatException {

		try {
			Date fechaProcesamiento = DateUtil.getCurrentDateOnly();

			for (TdDocumentoAnexo tdDocumentoAnexo : listTdDocumentoAnexos) {

				if (tdDocumentoAnexo.isPendienteGuardado()) {
					tdDocumentoAnexo.setEstado("1");
					tdDocumentoAnexo.setUsuarioId(getUser().getUsuarioId());
					tdDocumentoAnexo.setFechaRegistro(fechaProcesamiento);
					tdDocumentoAnexo.setTerminal(getUser().getTerminal());

					em.persist(tdDocumentoAnexo);

					TdExpedienteDocumentoAnexo tdExpedienteDocumentoAnexo = new TdExpedienteDocumentoAnexo();

					tdExpedienteDocumentoAnexo.setFechaAsignacion(fechaProcesamiento);
					tdExpedienteDocumentoAnexo.setEstado("1");
					tdExpedienteDocumentoAnexo.setUsuarioId(getUser().getUsuarioId());
					tdExpedienteDocumentoAnexo.setFechaRegistro(fechaProcesamiento);
					tdExpedienteDocumentoAnexo.setTerminal(getUser().getTerminal());

					tdExpedienteDocumentoAnexo.setExpedienteId(tdExpediente.getExpedienteId());
					tdExpedienteDocumentoAnexo.setDocumentoAnexoId(tdDocumentoAnexo.getDocumentoAnexoId());

					em.persist(tdExpedienteDocumentoAnexo);
				}

			}
		} catch (RuntimeException e) {
			throw new SisatException(e.getMessage());
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GnTipoDocumento> obtenerTiposDocumentos() throws SisatException {

		List<GnTipoDocumento> listGnTipoDocumentos = new ArrayList<GnTipoDocumento>();
		Query q = this.em.createQuery("from GnTipoDocumento doc where doc.estado = 1");

		listGnTipoDocumentos = q.getResultList();

		return listGnTipoDocumentos;

	}

	public List<TdDocumentoAnexo> obtenerDocumentosAnexos(Integer expedienteId) throws SisatException {

		return service.obtenerDocumentosAnexos(expedienteId);
	}

	public List<ResolutorDTO> obtenerResolutores(Integer unidadId) throws SisatException {

		return service.obtenerResolutores(unidadId);
	}

	public void asignarExpedienteAResolutor(TdExpediente tdExpediente, Integer resolutorId) throws SisatException {

		try {

			tdExpediente.setSituacionExpediente(Constante.SITUACION_EXPEDIENTE_ASIGNADO);
			tdExpediente.setFechaActualizacion(DateUtil.getCurrentDateOnly());
			tdExpediente.setUsuarioId(getUser().getUsuarioId());
			tdExpediente.setTerminal(getUser().getTerminal());

			this.em.merge(tdExpediente);

			TdAsignacionExpediente tdAsignacionExpediente = new TdAsignacionExpediente();

			tdAsignacionExpediente.setExpedienteId(tdExpediente.getExpedienteId());
			tdAsignacionExpediente.setResolutorId(resolutorId);
			tdAsignacionExpediente.setSituacionExpediente(Constante.SITUACION_EXPEDIENTE_ASIGNADO);
			tdAsignacionExpediente.setEstadoExpediente(tdExpediente.getEstadoExpediente());
			tdAsignacionExpediente.setUnidadId(tdExpediente.getUnidadId());

			tdAsignacionExpediente.setEstado(Constante.ESTADO_ACTIVO);
			tdAsignacionExpediente.setUsuarioId(getUser().getUsuarioId());
			tdAsignacionExpediente.setFechaRegistro(DateUtil.getCurrentDateOnly());
			tdAsignacionExpediente.setTerminal(getUser().getTerminal());

			this.em.persist(tdAsignacionExpediente);
		} catch (RuntimeException ex) {
			throw new SisatException(ex.getMessage());
		}

	}
	//por verificar
	@Override
	public HashMap<Integer, String> obtenerEstadosExpediente() throws SisatException {
		HashMap<Integer, String> hashMapEstadosExpediente = new HashMap<Integer, String>();

		hashMapEstadosExpediente.put(Constante.ESTADO_EXPEDIENTE_ASIGNADO, "Asignado");
		// hashMapEstadosExpediente.put(Constante.ESTADO_EXPEDIENTE_EN_TRAMITE, "En tramite");
		hashMapEstadosExpediente.put(Constante.ESTADO_EXPEDIENTE_PEND_APROBACION, "Pend. de Aprobación");
		hashMapEstadosExpediente.put(Constante.ESTADO_EXPEDIENTE_PEND_REQUISITOS, "Pend. de Requisitos");
		hashMapEstadosExpediente.put(Constante.ESTADO_EXPEDIENTE_RECEPCIONADO, "Recepcionado");
		hashMapEstadosExpediente.put(Constante.ESTADO_EXPEDIENTE_RESUELTO, "Resuelto");
		return hashMapEstadosExpediente;
	}

	public TdResultado obtenerResultado(Integer expedienteId) throws SisatException {

		TdResultado tdResultado = new TdResultado();

		try {
			Query q = this.em
					.createQuery("from TdResultado resul where resul.expedienteId = :expedienteId and resul.estado = 1");
	
			q.setParameter("expedienteId", expedienteId);
			
			tdResultado = (TdResultado) q.getSingleResult();
		} catch (RuntimeException e) {
			throw new SisatException(e.getMessage());
		}

		return tdResultado;

	}
	
	@SuppressWarnings("unchecked")
	public List<TdTipoTramite> obtenerTipoTramitesPorProcedimientoId(Integer procedimientoId) throws SisatException {

		List<TdTipoTramite> lista = new ArrayList<TdTipoTramite>();

		try {
			StringBuffer sb = new StringBuffer();

			sb.append(" select tp_tra.tipo_tramite_id, tp_tra.desc_corta, tp_tra.descripcion, tp_tra.usuario_id, tp_tra.estado, tp_tra.fecha_registro, tp_tra.terminal  ");
			sb.append("from td_tipo_tramite tp_tra ");
			sb.append(" inner join td_procedimiento_tipo_tramite proc_tra on proc_tra.tipo_tramite_id = tp_tra.tipo_tramite_id ");
			sb.append(" where tp_tra.estado = 1 and proc_tra.procedimiento_id = :procedimientoId");

			Query q = this.em.createNativeQuery(sb.toString(), TdTipoTramite.class);
			q.setParameter("procedimientoId", procedimientoId.intValue());
			lista = q.getResultList();
		} catch (RuntimeException e) {
			throw new SisatException(e.getMessage());
		}

		return lista;
	}
	
	@SuppressWarnings("unchecked")
	public List<TdEstadoExpediente> obtenerEstadoExpedientes() throws SisatException {

		List<TdEstadoExpediente> listEstadoExpedientes = new ArrayList<TdEstadoExpediente>();
		try {

			Query q = this.em.createQuery("from TdEstadoExpediente e where e.estado = 1");

			listEstadoExpedientes = q.getResultList();
			
		} catch (RuntimeException e) {
			throw new SisatException(e.getMessage());
		}

		return listEstadoExpedientes;
	}

	@SuppressWarnings("unchecked")
	public List<TdSituacionExpediente> obtenerSituacionExpedientes() throws SisatException {

		List<TdSituacionExpediente> listSituacionExpedientes = new ArrayList<TdSituacionExpediente>();
		try {

			Query q = this.em.createQuery("from TdSituacionExpediente e where e.estado = 1");

			listSituacionExpedientes = q.getResultList();
			
		} catch (RuntimeException e) {
			throw new SisatException(e.getMessage());
		}

		return listSituacionExpedientes;
	}
	
	public List<ResumenReporteExpedientesDTO> obtenerResumenExpedientes(Date fechaInicio, Date fechaFin)
			throws SisatException {

		return this.service.obtenerResumenExpedientes(fechaInicio, fechaFin);
	}
	
	//para resumen de expedientes por entregar (con flag)
	public List<ResumenReporteExpedientesDTO> obtenerResumenExpedienteshr(Date fechaInicio, Date fechaFin, Integer ofiOrigen, Integer ofiDestino, Integer procedimientoExpediente, Integer tipoTramite, Integer impreso, Integer situacionExpediente)
			throws SisatException {
		
		return this.service.obtenerResumenExpedienteshr(fechaInicio, fechaFin, ofiOrigen, ofiDestino, procedimientoExpediente, tipoTramite, impreso, situacionExpediente);
	}
	
	//para actualizar flag de resumen de expedientes por entregar, todas las areas
	public void cambioSegReporte(Date fechaIn, Date fechaFi) throws SisatException {
	
		Timestamp fini;
		Timestamp ffin;
		
		fini = new Timestamp(fechaIn.getTime());
		ffin = new Timestamp(fechaFi.getTime());
		
		try {
			Query q = this.em.createQuery("UPDATE TdExpediente SET report = 0 WHERE fechaPresentacion >= :fini and fechaPresentacion < :ffin");
			q.setParameter("fini", fini);
			q.setParameter("ffin", ffin);
			
			q.executeUpdate();				
		} catch (NoResultException e) {
			throw new SisatException("No existe Expediente para Mostrar");		
		}				
	}
	
	//para actualizar flag de resumen de expedientes por entregar, por area
	public void cambioSegReportea(Date fechaIn, Date fechaFi, Integer undId) throws SisatException {
	
		Timestamp fini;
		Timestamp ffin;
		
		fini = new Timestamp(fechaIn.getTime());
		ffin = new Timestamp(fechaFi.getTime());
		
		try {
			Query q = this.em.createQuery("UPDATE TdExpediente SET report = 0 WHERE unidadId = :undid and fechaPresentacion >= :fini and fechaPresentacion <= :ffin");
			q.setParameter("undid", undId);
			q.setParameter("fini", fini);
			q.setParameter("ffin", ffin);
			
			q.executeUpdate();				
		} catch (NoResultException e) {
			throw new SisatException("No existe Expediente");		
		}				
	}
	
	//Modificaciones al cambiar situacion de expediente a digitalizado
	@SuppressWarnings("unchecked")
	public void estadoDigitaliadoExp (Integer expedienteId) throws SisatException{
		int digitalizadoExp = Constante.SITUACION_EXPEDIENTE_DIGITALIZADO;		
		List<TdExpediente> tdExpe = new ArrayList<TdExpediente>();
		
		try{
			//Se actualiza situacion de td_expediente a digitalizado
			Query qe = this.em.createQuery("UPDATE TdExpediente SET situacionExpediente = :digitalizadoexp WHERE expedienteId = :expedienteid");
			qe.setParameter("digitalizadoexp", digitalizadoExp);
			qe.setParameter("expedienteid", expedienteId);
				
			qe.executeUpdate();
			
			//Obtener valores de expediente		
			Query qev = this.em.createQuery("from TdExpediente ex where ex.expedienteId = :expedienteid");
			qev.setParameter("expedienteid", expedienteId);
			tdExpe = qev.getResultList();
			
			//Actualizar td_asignacion_expediente (modifica registro actual)
			Query qa = this.em.createQuery("update TdAsignacionExpediente set usuarioId = :usuId, fechaRegistro = :fecReg, terminal = :ter, estadoExpediente = :esExp, situacionExpediente = :siExp where expedienteId = :expId");
			qa.setParameter("usuId", getUser().getUsuarioId());
			qa.setParameter("fecReg", DateUtil.getCurrentDateOnly());
			qa.setParameter("ter", getUser().getTerminal());				
			qa.setParameter("esExp", tdExpe.get(0).getEstadoExpediente());
			qa.setParameter("siExp", tdExpe.get(0).getSituacionExpediente());
			qa.setParameter("expId", expedienteId);
			
			qa.executeUpdate();
		}catch (RuntimeException e) {
			context.setRollbackOnly();
			throw new SisatException("Hubo un error al Cambiar estado, verifique. ".concat(e.getMessage()));
		}
	}
		
	//Modificaciones al cambiar situacion de expediente a derivado
	@SuppressWarnings("unchecked")
	public void estadoDerivadoExp (Integer expedienteId) throws SisatException {
		int derivadoExp = Constante.SITUACION_EXPEDIENTE_DERIVADO;
		List<TdExpediente> tdExpe = new ArrayList<TdExpediente>();
		List<TdResolutor> tdResol = new ArrayList<TdResolutor>();
		
		try{
			//Se actualiza situacion de td_expediente a Derivado
			Query q = this.em.createQuery("UPDATE TdExpediente SET situacionExpediente = :derivadoexp WHERE expedienteId = :expedienteid");
			q.setParameter("derivadoexp", derivadoExp);
			q.setParameter("expedienteid", expedienteId);
				
			q.executeUpdate();	
			
			//Obtener valores de expediente	
			Query qve = this.em.createQuery("from TdExpediente ex where ex.expedienteId = :expedienteid");
			qve.setParameter("expedienteid", expedienteId);
			tdExpe = qve.getResultList();			
				
			//Buscando al responsable de area segun area del expediente
			Query qr = this.em.createQuery("from TdResolutor resol where resol.estado = 1 and resol.fechaInicio < :fechaConsulta and resol.fechaFin > :fechaConsulta and resol.rol = 1 and resol.unidadId = :unidadId");
			qr.setParameter("fechaConsulta", DateUtil.getCurrentDateOnly());
			qr.setParameter("unidadId", tdExpe.get(0).getUnidadId());
	
			tdResol = qr.getResultList();

			//Actualizar td_asignacion_expediente (inserta nuevo registro)			
			TdAsignacionExpediente tdAsignacionExpediente = new TdAsignacionExpediente();

			tdAsignacionExpediente.setExpedienteId(expedienteId);
			tdAsignacionExpediente.setResolutorId(tdResol.get(0).getResolutorId());
			tdAsignacionExpediente.setUnidadId(tdExpe.get(0).getUnidadId());
			tdAsignacionExpediente.setEstado(Constante.ESTADO_ACTIVO);
			tdAsignacionExpediente.setUsuarioId(getUser().getUsuarioId());
			tdAsignacionExpediente.setFechaRegistro(DateUtil.getCurrentDateOnly());
			tdAsignacionExpediente.setTerminal(getUser().getTerminal());
			tdAsignacionExpediente.setEstadoExpediente(tdExpe.get(0).getEstadoExpediente());
			tdAsignacionExpediente.setSituacionExpediente(tdExpe.get(0).getSituacionExpediente());

			this.em.persist(tdAsignacionExpediente);
		
		}catch (RuntimeException e) {
			context.setRollbackOnly();
			throw new SisatException("Hubo un error al derivar, verifique. ".concat(e.getMessage()));
		}
	}
	
	public String obtenerCorrelativoTablaRegistroNuevoExpediente(String tabla, Integer anno)
	{
		DecimalFormat formatoNroExpedienteGenerico = new DecimalFormat(ParameterLoader.getParameter("patternSequenceDoc"));		
		String nroExpedienteGenerico = formatoNroExpedienteGenerico.format(service.obtenerCorrelativoTablaRegistroNuevoExpediente(tabla,anno));
		
		return nroExpedienteGenerico;
	}
	
	public String obtenerCorrelativoTablaRegistroNuevaDj (String tabla, Integer anno)
	{
		DecimalFormat formatoNroExpedienteGenerico = new DecimalFormat(ParameterLoader.getParameter("patternSequenceDoc"));		
		String nroExpedienteGenerico = formatoNroExpedienteGenerico.format(service.obtenerCorrelativoTablaRegistroDJAdult(tabla,anno));
		
		return nroExpedienteGenerico;
	}
	
	public String obtenerCorrelativoTablaResolucionNuevaDj (String tabla, Integer anno)
	{
		DecimalFormat formatoNroExpedienteGenerico = new DecimalFormat(ParameterLoader.getParameter("patternSequenceDoc"));		
		String nroExpedienteGenerico = formatoNroExpedienteGenerico.format(service.obtenerCorrelativoTablaRegistroDJAdult(tabla,anno));
		
		return nroExpedienteGenerico;
	}
	
	//Correlativo DJ adulto
	
	public String obtenerCorrelativoTablaRegistroDJAdult(String tabla, Integer anno)
	{
		DecimalFormat formatoNroDJAdult = new DecimalFormat(ParameterLoader.getParameter("patternSequenceDoc"));		
		String nroDJAdult = formatoNroDJAdult.format(service.obtenerCorrelativoTablaRegistroDJAdult(tabla,anno));
		
		return nroDJAdult;
	}
	
//	public void nuevaDjAdulto(Date fechaRecepcion,
//			String correlativoDJ,
//			int tramiteId,
//			int documentoTramiteId,
//			String nroExpedienteGenerico,
//			String nroExpediente,
//			int nroFolios,
//			int contribuyenteId,
//			String nroDocIdentContr,
//			String apellidosNombresContr,
//			String direccionFiscalContr,
//			int relacionadoId,
//			String nroDocIdentConyuge,
//			String apellidosNombresConyuge,
//			BigDecimal porcentajePartConyuge,
//			Boolean fallecidoConyuge,
//			Date fechaPartidaDefuncion,
//			Date fechaSucesionIntestada,
//			BigDecimal cuotaIdeal,
//			Boolean vivienda,
//			Boolean comercio,
//			Boolean licenciaFuncionamiento,
//			String observacion,
//			String nroResolucion,
//			int usuarioId,
//			int estadoDJ,
//			String terminal,
//			int situacionDJ,
//			int procedimientoId,
//			int unidadId,
//			int estadoExpediente,
//			int djId,
//			int requisitoPartId,
//			int requisitoSucId,
//			int requisitoLicenciaId
//			) throws SQLException{
//		
//		service.nuevaDjAdulto(fechaRecepcion,
//				correlativoDJ,
//				tramiteId,
//				documentoTramiteId,
//				nroExpedienteGenerico,
//				nroExpediente,
//				nroFolios,
//				contribuyenteId,
//				nroDocIdentContr,
//				apellidosNombresContr,
//				direccionFiscalContr,
//				relacionadoId,
//				nroDocIdentConyuge,
//				apellidosNombresConyuge,
//				porcentajePartConyuge,
//				fallecidoConyuge,
//				fechaPartidaDefuncion,
//				fechaSucesionIntestada,
//				cuotaIdeal,
//				vivienda,
//				comercio,
//				licenciaFuncionamiento,
//				observacion,
//				nroResolucion,
//				usuarioId,
//				estadoDJ,
//				terminal,
//				situacionDJ,
//				procedimientoId,
//				unidadId,
//				estadoExpediente,
//				djId,
//				requisitoPartId,
//				requisitoSucId,
//				requisitoLicenciaId);
//		
//		
//	}
	
	
	//guardar Remitente en GN_persona
	@Override
	public void NuevoTdRemitente(String primerNombre, String segundoNombre, String apellidoPaterno,
			String apellidoMaterno, String razonSocial, String nroDoc, String direcCompleta, int tipoDoc, int usuarioId,
			String terminal) throws SisatException {
		// TODO Auto-generated method stub
		Integer valor = 0;
		String ape_nom;
		try {
			//Verificar que no existe remitente			
			if (tipoDoc == 1) {
				ape_nom = apellidoPaterno +' '+ apellidoMaterno +' '+ primerNombre +' '+ segundoNombre;
			}else {ape_nom = razonSocial;}
			
			valor = service.CantidadRemitente(ape_nom,nroDoc);
			if (valor == 0) {
				//Sino existe inserta remitente
				service.NuevoRemitente(primerNombre, segundoNombre, apellidoPaterno, apellidoMaterno, razonSocial, nroDoc, direcCompleta, tipoDoc, usuarioId, terminal);
				msjRemitente = "Remitente Guardado Satisfactoriamente.";
			}
			else {
				msjRemitente = "El Remitente ya Existe. Verifique.";				
			}

		} catch (Exception ex) {
			throw new SisatException("No se guardo Remitente. ".concat(ex.getMessage()));
		}
		
	}

	@Override
	public List<MpRelacionado> obtenerRelacionados(MpRelacionado relacionados) throws SisatException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DeclaracionJuradaAdultDTO nuevaDjAdulto(Date fechaRecepcion, String correlativoDJ, int tramiteId,
			int documentoTramiteId, String nroExpedienteGenerico,
			String nroExpediente, int nroFolios, int contribuyenteId, String nroDocIdentContr,
			String apellidosNombresContr, String direccionFiscalContr, String observacion, String nroResolucion,int usuarioId, int estadoDJ,
			String terminal, int situacionDJ, int procedimientoId, int unidadId, int estadoExpediente, int resolutorId, String tipoBien,
			BigDecimal porcBenif,int iniAnnoBenif) throws SQLException {
		
		return this.service.nuevaDjAdulto(fechaRecepcion,
				correlativoDJ,
				tramiteId,
				documentoTramiteId,
				nroExpedienteGenerico,
				nroExpediente,
				nroFolios,
				contribuyenteId,
				nroDocIdentContr,
				apellidosNombresContr,
				direccionFiscalContr,
				observacion,
				nroResolucion,
				usuarioId,
				estadoDJ,
				terminal, 
				situacionDJ,
				procedimientoId,
				unidadId,
				estadoExpediente,
				resolutorId,
				tipoBien,
				porcBenif,
				iniAnnoBenif
				);
		
		
		
	}
	
	@Override
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
			String terminal) throws SQLException {
		
		return this.service.nuevoConyugeDjAdult( 
				djId, 
				relacionadoId, 
				nroDocIdentConyuge, 
				apellidosNombresConyuge, 
				porcentajePartConyuge, 
				fallecidoConyuge, 
				requisitoPartId, 
				fechaPartidaDefuncion, 
				requisitoSucId, 
				fechaSucesionIntestada, 
				cuotaIdeal, 
				contribuyenteId, 
				usuarioId, 
				terminal);
	}

	@Override
	public DeclaracionJuradaAdultDTO nuevaPropUnicaDjAdult(int djId, Boolean vivienda, Boolean negocio
			, int contribuyenteId, int usuarioId, String terminal) throws SQLException {
		
		return this.service.nuevaPropUnicaDjAdult(djId, vivienda, negocio, contribuyenteId, usuarioId, terminal);
	}

	@Override
	public RequisitoExpededienteDTO nuevoRequisitoExpedienteDjAdult(int requisitoId, Boolean flagPresentado, String glosa,
			int usuarioId, String terminal, int djId) throws SQLException {
		
		return this.service.nuevoRequisitoExpedienteDjAdult(requisitoId, flagPresentado, glosa, usuarioId, terminal, djId);
	}

	@Override
	public Integer obtenerDjId(int contribuyenteId) throws Exception {
		return this.service.obtenerDjId(contribuyenteId);
	}
	
	@Override
	public Integer obtenerDjIdDocTramite(int contribuyenteId) throws Exception {
		return this.service.obtenerDjTipoDocTramiteId(contribuyenteId);
	}
	
	@Override
	public Integer obtenerResolutorDjId(int usuarioLogueadoId) throws Exception {
		return this.service.obtenerResolutorDjId(usuarioLogueadoId);
	}

	@Override
	public TdDJ guardarCorrelativoDj(int anno) throws SisatException {
		
		//Guardar valor de correlativo expediente generico en tabla gn_correlativo_doc	de td_DJ
		
	//	Integer djId = service.obtenerCorrelativoDocTdDj(anno);			
		
	//	Integer djIdFinal = service.obtenerIdMaximo("td_DJ", anno);
		
	//	dj.setDjId(djIdFinal);
				
		service.actualizaValorNroExpedienteGenericoDj(anno);

	//	DecimalFormat formatoNroExpedienteGenerico = new DecimalFormat(ParameterLoader.getParameter("patternSequenceDoc"));
		
	//String nroExpedienteGenerico = formatoNroExpedienteGenerico.format(djId);
		
	//	dj.setNroExpedienteGenerico(nroExpedienteGenerico);
		
		
		
		return null;
	}
	
	@Override
	public TdDJ guardarCorrelativoResolucionDj(int anno) throws SisatException {

				
		service.actualizaValorResolucionDj(anno);
		
		
		return null;
	}

	@Override
	public DeclaracionJuradaAdultDTO nuevaResolucionDj(String nroResolucion, int djId, int usuarioId,
			String terminal) throws SQLException {

		return this.service.nuevaResolucionDj(nroResolucion, djId, usuarioId, terminal);
	}

	

		
}



