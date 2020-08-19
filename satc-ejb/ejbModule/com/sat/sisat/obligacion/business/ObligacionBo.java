package com.sat.sisat.obligacion.business;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import com.sat.sisat.cobranzacoactiva.dto.DetencionDeudaDTO;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.obligacion.dao.ObligacionBusinessDao;
import com.sat.sisat.obligacion.dto.MultaDTO;
import com.sat.sisat.obligacion.dto.ObligacionDTO;
import com.sat.sisat.obligacion.dto.SubConceptoDTO;
//import com.sat.sisat.persistence.entity.CcActo;
//import com.sat.sisat.persistence.entity.CcActoDeuda;
//import com.sat.sisat.persistence.entity.CcActoDeudaPK;
//import com.sat.sisat.persistence.entity.CcActoPK;
import com.sat.sisat.persistence.entity.CcLote;
import com.sat.sisat.persistence.entity.CcLoteDeuda;
import com.sat.sisat.persistence.entity.CcRec;
import com.sat.sisat.persistence.entity.CdDeuda;
import com.sat.sisat.persistence.entity.CdDeudaHistorica;
import com.sat.sisat.persistence.entity.CdDeudaHistoricaPK;
import com.sat.sisat.persistence.entity.DtDeterminacion;
import com.sat.sisat.persistence.entity.DtFechaVencimiento;
import com.sat.sisat.persistence.entity.GnUit;
import com.sat.sisat.persistence.entity.RsMulta;
import com.sat.sisat.predial.business.BaseBusiness;


@Stateful
public class ObligacionBo extends BaseBusiness implements ObligacionBoRemote {
	private CcLote ccLote = new CcLote();
	/**
	 * 
	 */
	private static final long serialVersionUID = 4614092758779764776L;

	private ObligacionBusinessDao service;

	public ObligacionBo() {

	}

	@PostConstruct
	public void initialize() {
		this.service = new ObligacionBusinessDao();
		setDataManager(this.service);
	}

	public ObligacionBusinessDao getService() {
		return this.service;
	}

	@Override
	public List<SubConceptoDTO> getSubConceptoGastos(int anho)
			throws SisatException {

		return service.getSubConceptoGastos(anho);
	}

	@Override
	public List<SubConceptoDTO> getSubConceptoMultas(int anho)
			throws SisatException {

		return service.getSubConceptoMultas(anho);
	}

	@Override
	public List<SubConceptoDTO> getSubConceptoEPND(int anho)
			throws SisatException {

		return service.getSubConceptoEPND(anho);
	}

	@Override
	public List<SubConceptoDTO> getSubConceptoCostas(int anho,Integer subconceptoId)
			throws SisatException {

		return service.getSubConceptoCostas(anho,subconceptoId);
	}

	@Override
	public List<SubConceptoDTO> getSubConceptoCostas(int anho)
			throws SisatException {

		return service.getSubConceptoCostas(anho);
	}
	
	public List<SubConceptoDTO> getSubConceptoCostasEmbargo(int anho)
			throws SisatException {

		return service.getSubConceptoCostasEmbargo(anho);
	}

	
	@Override
	public List<SubConceptoDTO> getSubConceptoMULTASDRTPE(int anho)
			throws SisatException {

		return service.getSubConceptoMULTASDRTPE(anho);
	}

	@Override
	public void saveObligaciones(List<ObligacionDTO> listObligacionDTOs,
			int personaId) throws SisatException {

		service.saveObligaciones(listObligacionDTOs, personaId, this.getUser());

		return;
	}

	@Override
	public boolean checkUnidadOrganica(String unidadOrganica)
			throws SisatException {

		return service.checkUnidadOrganica(unidadOrganica);
	}

	@Override
	public boolean checkTipoDocumentoMulta(int tipoDocumentoId)
			throws SisatException {

		return tipoDocumentoId == this.service.TIPO_DOCU_RESOLUCION_MULTA ? true
				: false;
	}

	@Override
	public CcRec obtenerRecByNroRec(String nroRec, int persona_id)
			throws SisatException {

		CcRec ccRec = null;

		Query q = service.em
				.createQuery("from CcRec rec where rec.nroRec = :nrorec and rec.personaId = :personaId");

		try {
			q.setParameter("nrorec", nroRec);
			q.setParameter("personaId", persona_id);
			ccRec = (CcRec) q.getSingleResult();
		} catch (NoResultException e) {
			ccRec = null;
		} catch (NonUniqueResultException e) {
			ccRec = null;
		} catch (RuntimeException e) {
			throw new SisatException(e.getMessage(), e.getCause());
		}

		return ccRec;
	}

	@SuppressWarnings("static-access")
	@Override
	public void generarMulta(int personaId, ObligacionDTO obligacionDTO)
			throws SisatException {

		// int conceptoId = 0;

		Date fechaActual = Calendar.getInstance().getTime();
		/**
		 * Comentado en Mayo 2016, no distingue la fecha de vencimiento, con el formato en java: */
//		
//		// Parche para que se ajuste hasta el vencimiento de la primera cuota del 2015
//		Calendar cal1 = Calendar.getInstance();
//		cal1.set(Calendar.MONTH, 2);
//		cal1.set(Calendar.YEAR, 2017);
//		cal1.set(Calendar.DATE, 28);
//		cal1.set(Calendar.HOUR_OF_DAY,23);
//		cal1.set(Calendar.MINUTE,59);
//		cal1.set(Calendar.SECOND,59);
//
//		Date fechaVencimiento = cal1.getTime();
		//**********************
		/** Para obtener fecha de vencimiento de la primera cuota del año en curso:
		 **/
				Calendar fechas = Calendar.getInstance();
				int año = fechas.get(Calendar.YEAR);
		
				List<DtFechaVencimiento> listaFV = service.getFechaVencimiento(obligacionDTO.getConceptoId(),año);
				Date fechaVencimiento=listaFV.get(0).getFechaVencimiento();
		/** **/
		
		int conceptoMultaId = obligacionDTO.getConceptoId();
		int subConceptoMultaId = obligacionDTO.getSubConceptoId();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(obligacionDTO.getFechaAdquision());
		int anhoAdquision = calendar.get(Calendar.YEAR);
		
		// Parche para que se ajuste hasta el vencimiento de la primera cuota del 2015
		int anhoActual = Calendar.getInstance().get(Calendar.YEAR);
		if(fechaActual.before(fechaVencimiento)){
			anhoActual = anhoActual-1;
		}
		//******************************
		
		int pivot = anhoAdquision;

		Date fechaUltimaDeclDescargo = null;

		if (obligacionDTO.getContexto().intValue() == 1) { 
			if (anhoActual - anhoAdquision > 5) {
//				if (anhoActual - anhoAdquision > 4) {
				pivot = anhoActual - 5;
				//pivot = anhoActual - 4;
		    }
		} else if (obligacionDTO.getContexto().intValue() == 2) {
			/** Caso de contexto de generacion de multas por descargo de predios */
			/** Moviendo la fecha al siguiente mes */
			Calendar cal = Calendar.getInstance();
			cal.setTime(obligacionDTO.getFechaAdquision());

			/** Verificacion para años prescritos */
			int anhoMulta = cal.get(Calendar.YEAR);
				if (anhoMulta > (anhoActual - 5)) {
//					if (anhoMulta > (anhoActual - 4)) {
				cal.add(Calendar.MONTH, 1);
				/** seteando el ultimo dia */
				cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
				pivot = anhoActual - 1;
		} else if (anhoMulta == (anhoActual - 5)
//		} else if (anhoMulta == (anhoActual - 4)
					&& cal.get(Calendar.MONTH) >= Calendar.NOVEMBER) {
				cal.add(Calendar.MONTH, 1);
				/** seteando el ultimo dia */
				cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
				pivot = anhoActual - 1;
			} else {
				/**
				 * Seteando el anho actual al pivot para que no genere multa
				 * para años prescritos
				 */
				pivot = anhoActual;
			}

			cal.add(Calendar.DAY_OF_MONTH, 1);
			fechaUltimaDeclDescargo = cal.getTime();
		} else {
			/** Caso de contexto de generacion de multas por descargo de predios */
			/** Moviendo la fecha al siguiente mes */
			Calendar cal = Calendar.getInstance();
			cal.setTime(obligacionDTO.getFechaAdquision());

			/** Verificacion para años prescritos */
			int anhoMulta = cal.get(Calendar.YEAR);
				//if (anhoMulta > (anhoActual - 4)) {
				if (anhoMulta > (anhoActual - 5)) {
				cal.add(Calendar.MONTH, 1);
				/** seteando el ultimo dia */
				cal.set(Calendar.DAY_OF_MONTH,
						cal.getActualMaximum(Calendar.DAY_OF_MONTH));
				pivot = anhoActual - 1;
			}
			/** para omisos a declaracion */
		}

		/** Fecha de vencimiento para la declaracion del bien */
		DtFechaVencimiento dtFechaVencimiento;

		BigDecimal montoUit = BigDecimal.ZERO;
		/** Creacion del lote */
		Integer lote_id = service.ObtenerCorrelativoTabla("cc_lote", 1);
		ccLote.setLoteId(lote_id);
		ccLote.setTipoLoteId(Constante.TIPO_LOTE_COBRANZA_ID);
		ccLote.setAnnoLote(DateUtil.getAnioActual());
		ccLote.setFechaLote(DateUtil.getCurrentDate());
		ccLote.setEstado(Constante.ESTADO_ACTIVO);
		ccLote.setFechaRegistro(DateUtil.getCurrentDate());
		ccLote.setTerminal(getUser().getTerminal());
		ccLote.setUsuarioId(getUser().getUsuarioId());
		ccLote.setFlagGeneracion(Constante.FLAG_GENERACION_PROGRAMADA);
		ccLote.setTipoLoteGeneracion(Constante.TIPO_LOTE_GENERACION_PRELIMINAR);
		// em.merge(ccLote);
		getService().create(ccLote);
		
		//Temporal hasta el primer vencimiento.
		//pivot ++;
		//anhoActual=2017;


		try {
			while (pivot < anhoActual) {

				/** Creacion de la Deuda */

				// System.out.println("anho: " + pivot);

				/** Obteniendo la Uit del anho iterado */
				Query query = em
						.createQuery("from GnUit u where u.annoUit = :anhoUit");
				if (obligacionDTO.getContexto().intValue() == 1) {
					query.setParameter("anhoUit", pivot + 1);
				} else {
					/** Caso multa o por descargo */
					Calendar cal = Calendar.getInstance();
					/**
					 * Se toma la fecha de adquision que para nuestro proposito
					 * es la fecha de infraccion
					 */
					cal.setTime(obligacionDTO.getFechaAdquision());
					query.setParameter("anhoUit", cal.get(Calendar.YEAR));
				}

				GnUit gnUit = (GnUit) query.getSingleResult();

				// System.out.println(gnUit.getUit());

				montoUit = getTasaUit(subConceptoMultaId).multiply(
						gnUit.getUit());
				int rsMultaId = service.ObtenerCorrelativoTabla("rs_multa", 1);

				String nro_resolucion = "Liquidación";
				/** Registrando la resolucion de multa */
				RsMulta rsMulta = new RsMulta();

				rsMulta.setMultaId(rsMultaId);
				rsMulta.setNroRsMulta(nro_resolucion);
				rsMulta.setUnidadId(obligacionDTO.getUnidadId());
				rsMulta.setConceptoId(Constante.CONCEPTO_MULTAS);
				rsMulta.setSubConceptoId(obligacionDTO.getSubConceptoId());
				rsMulta.setConceptoIdReferencia(obligacionDTO
						.getConceptoIdTributoReferencia());
				rsMulta.setGlosa(obligacionDTO.getGlosa());
				rsMulta.setFlagPresentoDocumentacion(obligacionDTO
						.getPresentoDocumentos());
				if (obligacionDTO.getNroActa() != null
						&& !obligacionDTO.getNroActa().trim().equals("")) {
					rsMulta.setNroActa(obligacionDTO.getNroActa());
				} else {
					rsMulta.setNroActa(null);
				}

				if (obligacionDTO.getNroRequerimiento() != null
						&& !obligacionDTO.getNroRequerimiento().trim()
								.equals("")) {
					rsMulta.setNroRequerimiento(obligacionDTO
							.getNroRequerimiento());
				} else {
					rsMulta.setNroRequerimiento(null);
				}

				if (obligacionDTO.getDjReferencia() > 0) {
					rsMulta.setDjReferencia(obligacionDTO.getDjReferencia());
				} else {
					rsMulta.setDjReferencia(null);
				}

				rsMulta.setEstado(Constante.ESTADO_ACTIVO);
				rsMulta.setFechaRegistro(Calendar.getInstance().getTime());
				rsMulta.setTerminal(getUser().getTerminal());
				rsMulta.setUsuarioId(getUser().getUsuarioId());
				rsMulta.setMonto(montoUit);
				rsMulta.setPorcentajeSancion(getTasaUit(subConceptoMultaId));
				rsMulta.setValorUit(gnUit.getUit());
				rsMulta.setPersonaId(personaId);
				rsMulta.setFlagContexto(String.valueOf(obligacionDTO.getContexto()));
				rsMulta.setPeriodo(pivot + 1);
				if (obligacionDTO.getContexto().intValue() == 3) {
					rsMulta.setFechaActa(obligacionDTO.getFechaAdquision());
				} else {
					rsMulta.setFechaActa(null);
				}
				em.persist(rsMulta);

				// obteniendo la fecha de vencimiento
				query = em
						.createQuery("from DtFechaVencimiento  f where f.periodo = :periodo and f.conceptoId = :conceptoId and f.cuota = 1");
				query.setParameter("periodo", pivot + 1);
				query.setParameter("conceptoId", obligacionDTO
						.getConceptoIdTributoReferencia().intValue());

				dtFechaVencimiento = (DtFechaVencimiento) query .getSingleResult();
				Integer determinacionId = service.obtenerCorrelativoTabla("dt_determinacion");

				DtDeterminacion determinacion = new DtDeterminacion();

				determinacion.setDeterminacionId(determinacionId);
				determinacion.setPersonaId(personaId);
				/** Caso cuando es multa por incripcion */
				if (obligacionDTO.getContexto().intValue() == 1) {
					determinacion.setAnnoDeterminacion(pivot + 1);
				} else if (obligacionDTO.getContexto().intValue() == 2) {
					/** Caso cuando es multa por descargo */
					Calendar cal = Calendar.getInstance();
					cal.setTime(fechaUltimaDeclDescargo);
					determinacion.setAnnoDeterminacion(cal.get(Calendar.YEAR));
				} else {
					/** Caso multa por omision */
					Calendar cal = Calendar.getInstance();
					/**
					 * Se toma la fecha de adquision que para nuestro proposito
					 * es la fecha de infraccion
					 */	
					Date fechaInfraccion = null;
					//llamar a la funcion fnGN_sumaDiasHabiles
					/**retorna la fecha infraccion sin considerar feriados*/
					
					fechaInfraccion=sumarDiasHabiles(obligacionDTO.getFechaAdquision(), 6);
					cal.setTime(fechaInfraccion);
					determinacion.setAnnoDeterminacion(cal.get(Calendar.YEAR));
				}
				determinacion.setConceptoId(conceptoMultaId);
				determinacion.setSubconceptoId(subConceptoMultaId);

				determinacion.setDjreferenciaId(rsMultaId);

				determinacion.setBaseImponible(montoUit);
				determinacion.setBaseAfecta(montoUit);
				determinacion.setImpuesto(montoUit);
				determinacion.setNroCuotas(1);

				/** Campos de auditoria */
				determinacion.setEstado(Constante.ESTADO_ACTIVO);
				determinacion.setFechaRegistro(DateUtil.getCurrentDate());
				determinacion.setTerminal(this.getUser().getTerminal());
				determinacion.setUsuarioId(this.getUser().getUsuarioId());

				/** persistiendo la entidad */
				em.merge(determinacion);

				/*************************************************************/

				/** Generacion de la deuda */

				Integer deudaId = service.obtenerCorrelativoTabla("cd_deuda");

				CdDeuda cdDeuda = new CdDeuda();

				cdDeuda.setDeudaId(deudaId);
				cdDeuda.setPersonaId(personaId);
				cdDeuda.setConceptoId(conceptoMultaId);
				cdDeuda.setSubconceptoId(subConceptoMultaId);
				cdDeuda.setDeterminacionId(determinacionId);
				cdDeuda.setFechaEmision(DateUtil.getCurrentDate());
				if (obligacionDTO.getContexto().intValue() == 1) {
					/** Añadiendo el dia despues de la fecha de vencimiento */
					Date fecha = new Date();
					fecha.setTime(dtFechaVencimiento.getFechaVencimiento().getTime());
					Calendar cal = Calendar.getInstance();
					cal.setTime(fecha);
					cal.add(Calendar.DAY_OF_MONTH, 1);

					/** Seteando la fecha de vencimiento */
					cdDeuda.setFechaVencimiento(new Timestamp(cal.getTime().getTime()));
				} else if (obligacionDTO.getContexto().intValue() == 2) {
					cdDeuda.setFechaVencimiento(new java.sql.Timestamp(fechaUltimaDeclDescargo.getTime()));
				} else {
					/** Contexto cuando es omision caso fiscalizacion */
					Date fechaInfraccion = null;
					//llamar a la funcion fnGN_sumaDiasHabiles
					/**retorna la fecha infraccion sin considerar feriados*/
					fechaInfraccion=sumarDiasHabiles(obligacionDTO.getFechaAdquision(), 6);
					cdDeuda.setFechaVencimiento(new java.sql.Timestamp(fechaInfraccion.getTime()));//fecha infraccion sumar 5 dias habiles
				}
				cdDeuda.setNroCuota(1);
				cdDeuda.setMontoOriginal(determinacion.getBaseImponible());
				cdDeuda.setInsoluto(determinacion.getBaseImponible());
				cdDeuda.setReajuste(BigDecimal.ZERO);
				cdDeuda.setDerechoEmision(BigDecimal.ZERO);
				cdDeuda.setDerechoEmisionCancelado(BigDecimal.ZERO);
				cdDeuda.setInteresMensual(BigDecimal.ZERO);
				cdDeuda.setInteresAnual(BigDecimal.ZERO);
				cdDeuda.setInteresCapitalizado(BigDecimal.ZERO);
				cdDeuda.setInsolutoCancelado(BigDecimal.ZERO);
				cdDeuda.setReajusteCancelado(BigDecimal.ZERO);
				cdDeuda.setInteresMensualCancelado(BigDecimal.ZERO);
				cdDeuda.setInteresCapiCancelado(BigDecimal.ZERO);
				cdDeuda.setTotalCancelado(BigDecimal.ZERO);

				cdDeuda.setAnnoDeuda(determinacion.getAnnoDeterminacion());

				cdDeuda.setTotalDeuda(determinacion.getBaseImponible());
				cdDeuda.setFlagCc("5");
				/** Anho documento */
				Calendar cal = Calendar.getInstance();
				cdDeuda.setAnnoDocumento(cal.YEAR);
				// cdDeuda.setNroReferencia("061-016-0021253");
				/** insertando el acto_id generado */
				// cdDeuda.setNroDocumento(ccActo.getId().getActoId());
				/** Campos de auditoria */
				cdDeuda.setEstado(Constante.ESTADO_ACTIVO);
				cdDeuda.setFechaRegistro(DateUtil.getCurrentDate());
				cdDeuda.setTerminal(this.getUser().getTerminal());
				cdDeuda.setUsuarioId(this.getUser().getUsuarioId());

				em.merge(cdDeuda);

				// Añadiendo lote_deuda
				CcLoteDeuda ccLoteDeuda = new CcLoteDeuda();
				ccLoteDeuda.setDeudaId(deudaId);
				ccLoteDeuda.setLoteId(lote_id);
				em.merge(ccLoteDeuda);

				/** Historico de deuda */
				CdDeudaHistorica dh = new CdDeudaHistorica();
				CdDeudaHistoricaPK id = new CdDeudaHistoricaPK();
				id.setDeudaId(cdDeuda.getDeudaId());

				Integer historicoId = service.ObtenerCorrelativoTabla(
						"cd_deuda_historica", 1);
				id.setHistoricaId(historicoId);

				dh.setId(id);
				dh.setDeterminacionId(cdDeuda.getDeterminacionId());
				dh.setPersonaId(cdDeuda.getPersonaId());
				dh.setTipoDeuda(cdDeuda.getTipoDeudaId());
				dh.setFechaVencimiento(cdDeuda.getFechaVencimiento());
				dh.setInsoluto(cdDeuda.getInsoluto());
				dh.setTotal(cdDeuda.getTotalDeuda());

				/** Campos de auditoria */
				dh.setEstado(Constante.ESTADO_ACTIVO);
				dh.setFechaRegistro(DateUtil.getCurrentDate());
				dh.setTerminal(this.getUser().getTerminal());
				dh.setUsuarioId(this.getUser().getUsuarioId());

				em.merge(dh);
				pivot++;
			}

			this.em.flush();
		} catch (Exception e) {

			throw new SisatException(e.getMessage());
		}

	}

	private BigDecimal getTasaUit(int i) {

		BigDecimal monto = BigDecimal.ZERO;

		switch (i) {
		case 126:
			monto = new BigDecimal("0.25");//0.50
			break;
		case 127:
			monto = new BigDecimal("0.25");//0.15
			break;
		case 128:
			monto = new BigDecimal("0.15");//0.25
			break;
		case 129:
			monto = new BigDecimal("0.15");//0.15
			break;
		case 130:
			monto = new BigDecimal("0.00");//0.15
			break;
		case 131:
			monto = new BigDecimal("0.00");//0.10
			break;
		case 132:
			monto = new BigDecimal("0.00");//0.10
			break;
		case 133:
			monto = new BigDecimal("0.00");//0.10
			break;
		case 134:
			monto = new BigDecimal("0.25");//0.10
//			monto = new BigDecimal("0.25");
			break;
		case 135:
			monto = new BigDecimal("0.00");//0.20
//			monto = new BigDecimal("0.25");
			break;
		case 136:
			monto = new BigDecimal("0.25");//0.50
//			monto = new BigDecimal("0.10");
			break;
		case 139:
			monto = new BigDecimal("0.50");//0.50
			break;
		case 141:
			monto = new BigDecimal("0.50");//0.50
			break;
		default:
			break;
		}

		return monto;
	}

	public List<MultaDTO> buscarMultas(ObligacionDTO obligacionDTO)
			throws SisatException {

		return service.buscarMultas(obligacionDTO, getUser().getUsuarioId());

	}

	public List<DetencionDeudaDTO> buscarDetenciones(Integer personaId,Integer anioDeuda,Integer papeletaId)
			throws SisatException {
		return service.buscarDetenciones(personaId, anioDeuda,papeletaId);
	}

	public List<DetencionDeudaDTO> buscarDetencionesPorPapeletaId(
			Integer papeletaId) throws SisatException {

		return service.buscarDetencionesPorPapeletaId(papeletaId);
	}

	public void desactivarDetencion(Integer personaId, Integer conceptoId,
			Integer subConceptoId, Integer anho, Integer determinacionId,String nroExped)
			throws SisatException {

		service.desactivarDetencion(personaId, conceptoId, subConceptoId, anho,
				determinacionId,nroExped, this.getUser());
	}

	public void activarDetencion(Integer personaId, Integer conceptoId,
			Integer subConceptoId, Integer anho, Integer determinacionId,String nroExped)
			throws SisatException {

		service.activarDetencion(personaId, conceptoId, subConceptoId, anho,
				determinacionId,nroExped, this.getUser());
	}

	public void notificarResolucionMulta(String nroActo)
			throws SisatException {

		service.notificarResolucionMulta(nroActo, this.getUser());
	}
	
	public void desactivarDetencionPapeleta(String nroPapeleta)
			throws SisatException {

		service.desactivarDetencionPapeleta(nroPapeleta, this.getUser());
	}

	public void activarDetencionPapeleta(String nroPapeleta)
			throws SisatException {

		service.activarDetencionPapeleta(nroPapeleta, this.getUser());
	}

	public void generarResolucionMulta(Integer loteId, Integer personaId,
			Integer unidad, Integer usuarioId, String terminal,
			Integer subConceptoId) throws Exception {
//,Integer loteVehId
		getService().generarResolucionMulta(loteId, personaId, unidad,
				usuarioId, terminal, subConceptoId);
	}

	public Integer obtenerDiasHabiles(Date a, Date b) throws SisatException {
		return service.obtenerDiasHabiles(new java.sql.Timestamp(a.getTime()),
				new java.sql.Timestamp(b.getTime()));
	}
	
	public Date sumarDiasHabiles(Date a, int b) throws SisatException {
		Date i = null;
		try {
			Query q = service.em.createNativeQuery("select dbo.fnGN_sumaDiasHabiles(:dia,:cantidad) ");
			/** retorna la fecha infraccion si considerar feriados */

			q.setParameter("dia", new Timestamp(a.getTime()));
			q.setParameter("cantidad", b);

			java.sql.Date dt = (java.sql.Date) q.getSingleResult();

			i = new Date(dt.getTime());

		} catch (RuntimeException e) {
			throw new SisatException(e.getMessage());
		}
		return i;
	}
	
	public List<DtFechaVencimiento> getFechaVencimiento(Integer subConceptoId,Integer periodo)throws Exception{
		return service.getFechaVencimiento(subConceptoId, periodo);
	}
	
	public List<SubConceptoDTO> getSubConceptoMULTA(int tipo)
			throws SisatException {

		return service.getSubConceptoMULTA(tipo);
	}
}
