package com.sat.sisat.determinacion.vehicular.business;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

import com.sat.sisat.common.dao.GeneralDao;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.determinacion.common.dao.DeterminacionDao;
import com.sat.sisat.determinacion.exception.DeterminacionVehicularException;
import com.sat.sisat.determinacion.vehicular.calculo.BaseExonerada;
import com.sat.sisat.determinacion.vehicular.calculo.ImpuestoVehicular;
import com.sat.sisat.determinacion.vehicular.calculo.TablaReferencialMEF;
import com.sat.sisat.determinacion.vehicular.dao.DeterminacionVehicularDao;
import com.sat.sisat.determinacion.vehicular.dto.DatosDeterminacionValoresDTO;
import com.sat.sisat.determinacion.vehicular.dto.DatosExisteDeterminacionDTO;
import com.sat.sisat.determinacion.vehicular.dto.DatosInafecDTO;
import com.sat.sisat.determinacion.vehicular.dto.DatosNecesariosDeterDTO;
import com.sat.sisat.determinacion.vehicular.dto.DeudaPagosDTO;
import com.sat.sisat.determinacion.vehicular.dto.DeudaValoresDTO;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.imputacion.dao.ImputacionDao;
import com.sat.sisat.persistence.entity.CdDeuda;
import com.sat.sisat.persistence.entity.CdDeudaHistorica;
import com.sat.sisat.persistence.entity.CdDeudaHistoricaPK;
import com.sat.sisat.persistence.entity.DtCuotaConcepto;
import com.sat.sisat.persistence.entity.DtDeterminacion;
import com.sat.sisat.persistence.entity.DtDeterminacionValor;
import com.sat.sisat.persistence.entity.DtDeterminacionVehicular;
import com.sat.sisat.persistence.entity.DtFechaVencimiento;
import com.sat.sisat.persistence.entity.DtTasaVehicular;
import com.sat.sisat.predial.business.BaseBusiness;

@Stateless
public class DeterminacionVehicularBo extends BaseBusiness implements DeterminacionVehicularBoRemote {

	private static final long serialVersionUID = -7892251902824721756L;
	
	private DeterminacionVehicularDao deterVehicDao;
	private DeterminacionDao determinacionDao;
	private ImputacionDao inputacionDao;
	private GeneralDao generalDao;

	public DeterminacionVehicularBo() {
	}

	@PostConstruct
	public void initialize() {
		this.deterVehicDao = new DeterminacionVehicularDao();
		this.generalDao = new GeneralDao();
		this.determinacionDao = new DeterminacionDao();
		this.inputacionDao = new ImputacionDao();
		
		setDataManager(this.deterVehicDao);
		setDataManager(this.generalDao);
		setDataManager(this.determinacionDao);
		setDataManager(this.inputacionDao);
	}

	public int generarDeterminacion(int djvehicularId, int usuarioId,String terminal) throws SisatException {

		try {
			DatosNecesariosDeterDTO datos = deterVehicDao.getDatosNecesariosDeterminar(djvehicularId);

			if (datos == null) {
				throw new DeterminacionVehicularException("No se ha obtenido los requisitos para poder determinar");
			}
			
			/**
			 * Iniciando la verificacion si es una DJ de incripcion o una DJ de descargo.
			 * */
			if(datos.getMotivoDeclaracionId().equals(new Integer(4))){
				
				deterVehicDao.descargoDeudaDJAnhoAfectacion(djvehicularId,
						datos.getPersonaId(),
						datos.getVehiculoId(),
						datos.getAnioAfec(),
						this.getUser());				
				//return true;
				return 1;
			}
			
			
			DtCuotaConcepto cuotas = determinacionDao.getCuotasConcepto(Constante.SUBCONCEPTO_VEHICULAR, datos.getAnioAfec());

			if (cuotas == null) {
				throw new DeterminacionVehicularException("No esta definido el número de cuotas");
			}

			List<DtFechaVencimiento> listaFV = determinacionDao.getFechaVencimiento(Constante.SUBCONCEPTO_VEHICULAR,datos.getAnioAfec());

			if (listaFV == null || listaFV.isEmpty()) {
				throw new DeterminacionVehicularException("Fechas de vencimiento de las cuotas no estan definidas");
			}

			if (cuotas.getNroCuotas() != listaFV.size()) {
				throw new DeterminacionVehicularException("Número de cuotas no concuerda con la cantidad de fechas de vencimiento programadas");
			}

			if (datos.getAnioAfec() < datos.getAnioIniAfec() || datos.getAnioAfec() > datos.getAnioFinAfec()) {
				throw new DeterminacionVehicularException("El año de afectación no está en el rango aceptado");
			}
			
			if (datos.getAnioAfec() < datos.getAnioIniAfecContrib() || datos.getAnioAfec() > datos.getAnioFinAfec()) {
				throw new DeterminacionVehicularException("Este contribuyente no está afecto al impuesto para este año");
			}
			
			if(datos.getAnhoAdquision().intValue() == datos.getAnioAfec()){
				throw new DeterminacionVehicularException("Este contribuyente no está afecto al impuesto para este año");
			}
			

			DtTasaVehicular tv = deterVehicDao.getTasaVehicular(datos.getAnioAfec());
			if (tv == null) {
				throw new DeterminacionVehicularException("No está definido la tasa del impuesto");
			}

			BigDecimal uit = generalDao.getUitAnio(datos.getAnioAfec());

			if (uit == null) {
				throw new DeterminacionVehicularException("No esta definido la UIT para el año: " + datos.getAnioAfec());
			}

			BigDecimal tasaAnual = tv.getTasaAnual().divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
			BigDecimal impuestoMin = tv.getPorcUitMin().divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
			impuestoMin = impuestoMin.multiply(uit);

			BigDecimal porcentajeExoner = null;
			// Valida inafecto - condición especial contribuyente
			DatosInafecDTO datosInafecC = deterVehicDao.getInafecContrib(datos.getPersonaId(), datos.getAnioAfec());

			if (datosInafecC == null) {
				// Valida inafecto - condicion especial vehículo
				DatosInafecDTO datosInafecV = deterVehicDao.getInafecVehic(djvehicularId);
				if (datosInafecV != null) {
					if (BaseExonerada.isAfecto(datos.getAnioAfec(),datosInafecV.getFechaInicio(),datosInafecV.getFechaFin())) {
						porcentajeExoner = datosInafecV.getValor();
						impuestoMin = null; // no considerar impuesto min
					}
				}
			} else {
				if (BaseExonerada.isAfecto(datos.getAnioAfec(),datosInafecC.getFechaInicio(),datosInafecC.getFechaFin())) {
					// Solo porcentaje (tipo = 1)
					porcentajeExoner = datosInafecC.getValor();
					impuestoMin = null; // no considerar impuesto min
				} else {
					// Valida inafecto - condicion especial vehículo
					DatosInafecDTO datosInafecV = deterVehicDao.getInafecVehic(djvehicularId);
					if (datosInafecV != null) {
						if (BaseExonerada.isAfecto(datos.getAnioAfec(),datosInafecV.getFechaInicio(),datosInafecV.getFechaFin())) {
							porcentajeExoner = datosInafecV.getValor();
							impuestoMin = null; // no considerar impuesto min
						}
					}
				}
			}
			// Si es null, utilizará el ajuste MEF
			BigDecimal valorMEF = deterVehicDao.getValorMEF(datos.getCategVehicId(),
					datos.getMarcaVehicId(),
					datos.getModeloVehicId(),
					datos.getAnioAfec(),
					datos.getAnioFabric());

			ImpuestoVehicular iv = null;
			if (valorMEF == null) {
				iv = new ImpuestoVehicular(null, datos.getAnioAfec() - 1,
						deterVehicDao.getMontoAnioMenorAntig(datos.getCategVehicId(),
								datos.getMarcaVehicId(),
								datos.getModeloVehicId(),
								datos.getAnioAfec()),
						datos.getAnioFabric(),
						datos.getValorAdquiSoles(),porcentajeExoner, tasaAnual, impuestoMin,datos.getPorcentajePropiedad());
				if(iv.getBaseImponible()==null){
					throw new DeterminacionVehicularException(
							(new StringBuffer()).append("Verificar valor referencial para categoría").append(datos.getCategVehicId())
							.append(", marca ").append(datos.getMarcaVehicId())
							.append("y modelo ").append(datos.getModeloVehicId())
							.append("para el vehículo en el año de afectacion ").append(datos.getAnioAfec())
							.append("y año fabricacion ").append(datos.getAnioFabric()).toString());
				}
			} else {
				iv = new ImpuestoVehicular(valorMEF, 0, null,datos.getAnioFabric(), datos.getValorAdquiSoles(),
						porcentajeExoner, tasaAnual, impuestoMin,datos.getPorcentajePropiedad());
				if(iv.getBaseImponible()==null){
					throw new DeterminacionVehicularException("No se ha podido calcular el impuesto");
				}
			}

			BigDecimal montoCuota = iv.getImpuestoParcial().divide(new BigDecimal(String.valueOf(cuotas.getNroCuotas())), 2,RoundingMode.HALF_UP);

			// Determinaciones con estado 1
			DatosExisteDeterminacionDTO datosDeter = deterVehicDao.getDatosExisteDeterminacion(Constante.CONCEPTO_VEHICULAR,djvehicularId);

			if (datosDeter == null) { // No existe determinación
				// Determinación
				DtDeterminacion dtr = new DtDeterminacion();
				dtr.setPersonaId(datos.getPersonaId());
				dtr.setAnnoDeterminacion(datos.getAnioAfec());
				dtr.setConceptoId(Constante.CONCEPTO_VEHICULAR);
				dtr.setSubconceptoId(Constante.SUB_CONCEPTO_VEHICULAR);
				dtr.setBaseImponible(iv.getBaseImponible());
				dtr.setBaseExonerada(iv.getBaseExonerada());
				dtr.setBaseAfecta(iv.getBaseAfecta());
				dtr.setImpuesto(iv.getImpuestoParcial());
				dtr.setNroCuotas(cuotas.getNroCuotas());
				dtr.setPorcPropiedad(datos.getPorcentajePropiedad());
				dtr.setDjreferenciaId(djvehicularId);
				dtr.setFechaActualizacion(DateUtil.getCurrentDate());
				dtr.setEstado(Constante.ESTADO_ACTIVO);
				dtr.setUsuarioId(usuarioId);
				dtr.setFechaRegistro(DateUtil.getCurrentDate());
				dtr.setTerminal(terminal);
				dtr.setFiscalizado(datos.getFiscalizado());//Fiscalización 2015
				dtr.setFiscaAceptada(datos.getFiscalizadoAceptado());//Fiscalización 2015
				dtr.setFiscaCerrada(datos.getFiscalizadoCerrado());//Fiscalización 2015
				// Determinacion vehicular
				DtDeterminacionVehicular dtv = new DtDeterminacionVehicular();
				dtv.setNroReferencia(djvehicularId);
				dtv.setTipoMonedaId(datos.getTipoMonedaId());
				dtv.setValorAdquisicionSoles(datos.getValorAdquiSoles());
				dtv.setValorAdquisicionMoneda(datos.getValorAdquiOtraMoneda());
				dtv.setValorReferencial(iv.getValoreRefencialMEF());
				dtv.setTasa(tasaAnual);
				dtv.setBaseImponible(iv.getBaseImponible());
				dtv.setBaseExonerada(iv.getBaseExonerada());
				dtv.setBaseAfecta(iv.getBaseAfecta());
				dtv.setValorImpuesto(iv.getImpuestoParcial());
				dtv.setEstado(Constante.ESTADO_ACTIVO);
				dtv.setCategoriaVehiculoId(datos.getCategVehicId());
				dtv.setMarcaVehiculoId(datos.getMarcaVehicId());
				dtv.setModeloVehiculoId(datos.getModeloVehicId());
				dtv.setUsuarioId(usuarioId);
				dtv.setFechaRegistro(DateUtil.getCurrentDate());
				dtv.setTerminal(terminal);

				if (datos.getDjPrevioId() == null) { // No DJ Previo

					int deterId = deterVehicDao.guardarDeterminacion(dtr);
					// Guardar Determinación vehicular
					dtv.setDeterminacionId(deterId);
					deterVehicDao.guardarDeterminacionVehicular(dtv);
					// Guardar las deudas
					for (int i = 0; i < cuotas.getNroCuotas(); i++) {
						// Guardar deuda
						CdDeuda deuda = new CdDeuda();
						deuda.setTipoDeudaId(Constante.TIPO_DEUDA_AUTOGENERADO);
						deuda.setPersonaId(datos.getPersonaId());
						deuda.setConceptoId(Constante.CONCEPTO_VEHICULAR);
						deuda.setSubconceptoId(Constante.SUBCONCEPTO_VEHICULAR);
						deuda.setDeterminacionId(deterId);
						deuda.setAnnoDeuda(datos.getAnioAfec());
						deuda.setFechaEmision(DateUtil.getCurrentDate());
						deuda.setFechaVencimiento(listaFV.get(i).getFechaVencimiento());
						deuda.setNroCuota(listaFV.get(i).getCuota());
						deuda.setMontoOriginal(iv.getImpuestoParcial());
						deuda.setInsoluto(montoCuota);
						if (deuda.getNroCuota() == cuotas.getCuotaDerechoEmision()) {
							deuda.setDerechoEmision(cuotas.getMontoDerechoEmision());
							deuda.setTotalDeuda(montoCuota.add(deuda.getDerechoEmision()));
						} else {
							deuda.setDerechoEmision(BigDecimal.ZERO);
							deuda.setTotalDeuda(montoCuota);
						}
						deuda.setNroReferencia(djvehicularId);
						deuda.setNroCuentaBanco(null);
						deuda.setUsuarioId(usuarioId);
						deuda.setFechaRegistro(DateUtil.getCurrentDate());
						deuda.setTerminal(terminal);
						deuda.setEstadoDeudaId(Constante.ESTADO_DEUDA_DETERMINADO);
						int deudaId = deterVehicDao.guardarDeuda(deuda);
						// Guardar deuda historica
						CdDeudaHistorica dh = new CdDeudaHistorica();
						CdDeudaHistoricaPK id = new CdDeudaHistoricaPK();
						id.setDeudaId(deudaId);
						dh.setId(id);
						dh.setTipoMovimientoId(Constante.TIPO_MOV_GENERADO);
						dh.setDeterminacionId(deuda.getDeterminacionId());
						dh.setPersonaId(deuda.getPersonaId());
						dh.setFechaMovimiento(deuda.getFechaEmision());
						dh.setTipoDeuda(deuda.getTipoDeudaId());
						dh.setFechaVencimiento(deuda.getFechaVencimiento());
						dh.setInsoluto(deuda.getInsoluto());
						dh.setTotal(deuda.getTotalDeuda());
						dh.setUsuarioId(usuarioId);
						dh.setEstado(Constante.ESTADO_ACTIVO);
						dh.setFechaRegistro(DateUtil.getCurrentDate());
						dh.setTerminal(terminal);
						deterVehicDao.guardarDeudaHistorica(dh);
					}
					//return true;
					return 1;
				} else { // Proviene de actualizacion
					DatosExisteDeterminacionDTO deterPrevio = deterVehicDao.getDatosExisteDeterminacionActiva(Constante.CONCEPTO_VEHICULAR,datos.getPersonaId(),datos.getAnioAfec(),datos.getVehiculoId());					
				
					
					if (deterPrevio == null) {// DJ anterior no determinada
						
						// Guardar Determinación
						int deterId = deterVehicDao.guardarDeterminacion(dtr);
						
						// Guardar Determinación_vehicular
						dtv.setDeterminacionId(deterId);
						deterVehicDao.guardarDeterminacionVehicular(dtv);
						
						// Guardar Deuda en Cuotas
						for (int i = 0; i < cuotas.getNroCuotas(); i++) {
							// Guardar deuda
							CdDeuda deuda = new CdDeuda();
							deuda.setTipoDeudaId(Constante.TIPO_DEUDA_AUTOGENERADO);
							deuda.setPersonaId(datos.getPersonaId());
							deuda.setConceptoId(Constante.CONCEPTO_VEHICULAR);
							deuda.setSubconceptoId(Constante.SUBCONCEPTO_VEHICULAR);
							deuda.setDeterminacionId(deterId);
							deuda.setAnnoDeuda(datos.getAnioAfec());
							deuda.setFechaEmision(DateUtil.getCurrentDate());
							deuda.setFechaVencimiento(listaFV.get(i).getFechaVencimiento());
							deuda.setNroCuota(listaFV.get(i).getCuota());
							deuda.setMontoOriginal(iv.getImpuestoParcial());
							deuda.setInsoluto(montoCuota);
							if (deuda.getNroCuota() == cuotas.getCuotaDerechoEmision()) {
								deuda.setDerechoEmision(cuotas.getMontoDerechoEmision());
								deuda.setTotalDeuda(montoCuota.add(deuda.getDerechoEmision()));
							} else {
								deuda.setDerechoEmision(BigDecimal.ZERO);
								deuda.setTotalDeuda(montoCuota);
							}
							deuda.setNroReferencia(djvehicularId);
							deuda.setNroCuentaBanco(null);
							deuda.setUsuarioId(usuarioId);
							deuda.setFechaRegistro(DateUtil.getCurrentDate());
							deuda.setTerminal(terminal);
							deuda.setEstadoDeudaId(Constante.ESTADO_DEUDA_DETERMINADO);
							int deudaId = deterVehicDao.guardarDeuda(deuda);
							// Guardar deuda historica
							CdDeudaHistorica dh = new CdDeudaHistorica();
							CdDeudaHistoricaPK id = new CdDeudaHistoricaPK();
							id.setDeudaId(deudaId);
							dh.setId(id);
							dh.setTipoMovimientoId(Constante.TIPO_MOV_GENERADO);
							dh.setDeterminacionId(deuda.getDeterminacionId());
							dh.setPersonaId(deuda.getPersonaId());
							dh.setFechaMovimiento(deuda.getFechaEmision());
							dh.setTipoDeuda(deuda.getTipoDeudaId());
							dh.setFechaVencimiento(deuda.getFechaVencimiento());
							dh.setInsoluto(deuda.getInsoluto());
							dh.setTotal(deuda.getTotalDeuda());
							dh.setUsuarioId(usuarioId);
							dh.setEstado(Constante.ESTADO_ACTIVO);
							dh.setFechaRegistro(DateUtil.getCurrentDate());
							dh.setTerminal(terminal);
							deterVehicDao.guardarDeudaHistorica(dh);
						}
						//return true;
						return 1;
					} else { // DJ Anterior si está Determinada
						dtr.setBaseImponibleAnterior(deterPrevio.getBaseImponible());
						dtr.setBaseExoneradaAnterior(deterPrevio.getBaseExonerada());
						dtr.setBaseAfectaAnterior(deterPrevio.getBaseAfecta());
						dtr.setImpuestoAnterior(deterPrevio.getImpuesto());
						
						BigDecimal impDif = dtr.getImpuesto().subtract(dtr.getImpuestoAnterior());
						impDif = impDif.setScale(2, RoundingMode.HALF_UP);
						// Guardar Determinación
						dtr.setImpuestoDiferencia(impDif);
						int deterId = deterVehicDao.guardarDeterminacion2(dtr);
						// Guardar Determinación vehicular
						dtv.setDeterminacionId(deterId);
						deterVehicDao.guardarDeterminacionVehicular(dtv);

						// Existe determinación y deuda para su anterior declaración, se deve evaluar los caso de pagos y valores.
							//Cod. anterior:: List<DeudaValoresDTO> lstDeuVal = deterVehicDao.getDeudaValores(deterPrevio.getDeterminacionId());
						List<DeudaValoresDTO> lstDeuVal = deterVehicDao.getDeudaValores(datos.getPersonaId(),Constante.CONCEPTO_VEHICULAR,datos.getAnioAfec(),datos.getVehiculoId());
						DeudaPagosDTO deuPagPrevio = deterVehicDao.getDeudaPagos(deterPrevio.getDeterminacionId());
						
						/**
						 * Verificando si alguna de la deudas tiene valores.(tieneVal==true)::27-11-2014::
						 * Dependiendo si mantiene el monto del impuesto o si existe una variación::
						 */
						
						boolean tieneVal = false;
						for (DeudaValoresDTO dv : lstDeuVal) {
							int coact = 0;
							try{
			  //Cod. anterior://coact = dv.getFlagCoactiva() == null ? 0 : Integer.parseInt(dv.getFlagCoactiva()); //26-11-14
								coact = dv.getFlagOp() == null ? 0 : Integer.parseInt(dv.getFlagOp());
							}catch(Exception ex){
								coact=0;
							}
			 // Cod. anterior:// if (coact > 2) { tieneVal = true; break;}
							if (coact > 0) {
								tieneVal = true;
								break;
							}
						}

						if (tieneVal == false) { // No tiene valores
							for (int i = 0; i < cuotas.getNroCuotas(); i++) {
								// Guardar deuda
								CdDeuda deuda = new CdDeuda();
								deuda.setTipoDeudaId(Constante.TIPO_DEUDA_AUTOGENERADO);
								deuda.setPersonaId(datos.getPersonaId());
								deuda.setConceptoId(Constante.CONCEPTO_VEHICULAR);
								deuda.setSubconceptoId(Constante.SUBCONCEPTO_VEHICULAR);
								deuda.setDeterminacionId(deterId);
								deuda.setAnnoDeuda(datos.getAnioAfec());
								deuda.setFechaEmision(DateUtil.getCurrentDate());
								deuda.setFechaVencimiento(listaFV.get(i).getFechaVencimiento());
								deuda.setNroCuota(listaFV.get(i).getCuota());
								deuda.setMontoOriginal(iv.getImpuestoParcial());
								deuda.setInsoluto(montoCuota);
								if (deuda.getNroCuota() == cuotas.getCuotaDerechoEmision()) {
									deuda.setDerechoEmision(cuotas.getMontoDerechoEmision());
									deuda.setTotalDeuda(montoCuota.add(deuda.getDerechoEmision()));
								} else {
									deuda.setDerechoEmision(BigDecimal.ZERO);
									deuda.setTotalDeuda(montoCuota);
								}
								deuda.setNroReferencia(djvehicularId);
								deuda.setNroCuentaBanco(null);
								deuda.setUsuarioId(usuarioId);
								deuda.setFechaRegistro(DateUtil.getCurrentDate());
								deuda.setTerminal(terminal);
								deuda.setEstadoDeudaId(Constante.ESTADO_DEUDA_DETERMINADO);
								int deudaId = deterVehicDao.guardarDeuda(deuda);
								// Guardar deuda historica
								CdDeudaHistorica dh = new CdDeudaHistorica();
								CdDeudaHistoricaPK id = new CdDeudaHistoricaPK();
								id.setDeudaId(deudaId);
								dh.setId(id);
								dh.setTipoMovimientoId(Constante.TIPO_MOV_GENERADO);
								dh.setDeterminacionId(deuda.getDeterminacionId());
								dh.setPersonaId(deuda.getPersonaId());
								dh.setFechaMovimiento(deuda.getFechaEmision());
								dh.setTipoDeuda(deuda.getTipoDeudaId());
								dh.setFechaVencimiento(deuda.getFechaVencimiento());
								dh.setInsoluto(deuda.getInsoluto());
								dh.setTotal(deuda.getTotalDeuda());
								dh.setUsuarioId(usuarioId);
								dh.setEstado(Constante.ESTADO_ACTIVO);
								dh.setFechaRegistro(DateUtil.getCurrentDate());
								dh.setTerminal(terminal);
								deterVehicDao.guardarDeudaHistorica(dh);
							}
							// Para el caso que no tiene valores y tiene pagos
							if(deuPagPrevio.getTotalCancelado().doubleValue() > 0){
								inputacionDao.inputarDeudaSinValores(deterPrevio.getDeterminacionId(), deterId, usuarioId, terminal);
							}else{
								// Inahabilita anteriores, no funciona desde aca, ya está en el store de inputación
								determinacionDao.cambiarEstadoDeterminacion(deterPrevio.getDeterminacionId(),Constante.ESTADO_ELIMINADO);
								deterVehicDao.cambiarEstadoDeterminacionVehic(deterPrevio.getDeterminacionId(),Constante.ESTADO_ELIMINADO);
								determinacionDao.cambiarEstadoDeudasDeter(deterPrevio.getDeterminacionId(),	Constante.ESTADO_ELIMINADO);
								determinacionDao.cambiarEstadoDeudaHistoricaDeter(deterPrevio.getDeterminacionId(),Constante.ESTADO_ELIMINADO);
							}
							//return true;
							return 1;
						} else { // Tiene valores
							//Obtenemos el monto de las Determinaciones con Valor.
							DatosDeterminacionValoresDTO deterValor =deterVehicDao.getMontoDeterminacionValor(datos.getPersonaId(),Constante.CONCEPTO_VEHICULAR,datos.getAnioAfec());
							
							//Generamos la diferencia entre el impuesto nuevo generado y el monto total de las determinaciones con valor.
							BigDecimal impDiff = dtr.getImpuesto().subtract(deterValor.getImpuesto());
							impDiff = impDiff.setScale(2, RoundingMode.HALF_UP);
						    
							//Actualizando la diferencia de la nueva determinacion
							deterVehicDao.actualizarDiffDeterminacion(deterId, impDiff);
							
							//Verificando si la Determinacion Previa sin valor tiene pagos:
								DeudaPagosDTO deuValorPagPrevio = deterVehicDao.getDeudaValoresPagos(deterPrevio.getDeterminacionId());

							if (impDiff.doubleValue() > 0) { //if (dtr.getImpuestoDiferencia().doubleValue() >= 0)//:Cod. anterior
								if (deuValorPagPrevio.getTotalCancelado().doubleValue() == 0 || deuValorPagPrevio.getTotalCancelado()== null) {// No existen Pagos
									montoCuota = impDiff.divide(new BigDecimal(String.valueOf(cuotas.getNroCuotas())),2, RoundingMode.HALF_UP);
									// Guardar las deudas solo con la diferencia
									for (int i = 0; i < cuotas.getNroCuotas(); i++) {
										// Guardar deuda
										CdDeuda deuda = new CdDeuda();
										deuda.setTipoDeudaId(Constante.TIPO_DEUDA_AUTOGENERADO);
										deuda.setPersonaId(datos.getPersonaId());
										deuda.setConceptoId(Constante.CONCEPTO_VEHICULAR);
										deuda.setSubconceptoId(Constante.SUBCONCEPTO_VEHICULAR);
										deuda.setDeterminacionId(deterId);
										deuda.setAnnoDeuda(datos.getAnioAfec());
										deuda.setFechaEmision(DateUtil.getCurrentDate());
										deuda.setFechaVencimiento(listaFV.get(i).getFechaVencimiento());
										deuda.setNroCuota(listaFV.get(i).getCuota());
										deuda.setMontoOriginal(impDiff);//deuda.setMontoOriginal(dtr.getImpuestoDiferencia());
										deuda.setInsoluto(montoCuota);
										if (deuda.getNroCuota() == cuotas.getCuotaDerechoEmision()) {
											deuda.setDerechoEmision(cuotas.getMontoDerechoEmision());
											deuda.setTotalDeuda(montoCuota.add(deuda.getDerechoEmision()));
										} else {
											deuda.setDerechoEmision(BigDecimal.ZERO);
											deuda.setTotalDeuda(montoCuota);
										}
										deuda.setNroReferencia(djvehicularId);
										deuda.setNroCuentaBanco(null);
										deuda.setUsuarioId(usuarioId);
										deuda.setFechaRegistro(DateUtil.getCurrentDate());
										deuda.setTerminal(terminal);
										deuda.setEstadoDeudaId(Constante.ESTADO_DEUDA_DETERMINADO);
										int deudaId = deterVehicDao.guardarDeuda(deuda);
										// Guardar deuda historica
										CdDeudaHistorica dh = new CdDeudaHistorica();
										CdDeudaHistoricaPK id = new CdDeudaHistoricaPK();
										id.setDeudaId(deudaId);
										dh.setId(id);
										dh.setTipoMovimientoId(Constante.TIPO_MOV_GENERADO);
										dh.setDeterminacionId(deuda.getDeterminacionId());
										dh.setPersonaId(deuda.getPersonaId());
										dh.setFechaMovimiento(deuda.getFechaEmision());
										dh.setTipoDeuda(deuda.getTipoDeudaId());
										dh.setFechaVencimiento(deuda.getFechaVencimiento());
										dh.setInsoluto(deuda.getInsoluto());
										dh.setTotal(deuda.getTotalDeuda());
										dh.setUsuarioId(usuarioId);
										dh.setEstado(Constante.ESTADO_ACTIVO);
										dh.setFechaRegistro(DateUtil.getCurrentDate());
										dh.setTerminal(terminal);
										deterVehicDao.guardarDeudaHistorica(dh);
									}
									
									//Si existe una Determinacion sin valor en estado=1,se inactivará,al igual que la deuda y las demás tablas asociadas.
									DatosDeterminacionValoresDTO dt=  deterVehicDao.getDeterminacionValores(datos.getPersonaId(),Constante.CONCEPTO_VEHICULAR,datos.getAnioAfec(),deterPrevio.getDeterminacionId());
									
										if (dt.getDeterminacionId()==0){
											determinacionDao.cambiarEstadoDeterminacion(deterPrevio.getDeterminacionId(),Constante.ESTADO_ELIMINADO);
											deterVehicDao.cambiarEstadoDeterminacionVehic(deterPrevio.getDeterminacionId(),Constante.ESTADO_ELIMINADO);
											determinacionDao.cambiarEstadoDeudasDeter(deterPrevio.getDeterminacionId(),	Constante.ESTADO_ELIMINADO);
											determinacionDao.cambiarEstadoDeudaHistoricaDeter(deterPrevio.getDeterminacionId(),Constante.ESTADO_ELIMINADO);
										}
									

									//Obtenemos los demás datos de la Determinacion Previa.
									  DtDeterminacion dt1 =deterVehicDao.getDatosPreviaDeterminacionActiva(deterPrevio.getDeterminacionId());
									
									//Insertamos en la tabla dt_determinacion_valor,para registrar la variación en ambas determinaciones.
		        					DtDeterminacionValor variacion=new DtDeterminacionValor();
			        					variacion.setDeterminacionId(dt1.getDeterminacionId());
			        					variacion.setAnnoDeterminacion(dt1.getAnnoDeterminacion());
			        					variacion.setPersonaId(dt1.getPersonaId());
			        					variacion.setConceptoId(dt1.getConceptoId());
			        					variacion.setSubconceptoId(dt1.getSubconceptoId());
			        					variacion.setBaseImponible(dt1.getBaseImponible());
			        					variacion.setImpuesto(dt1.getImpuesto());
			        					variacion.setFechaRegistroDt(dt1.getFechaRegistro());
			        					
			        					variacion.setDeterminacionIdVariacion(deterId);
			        					variacion.setAnnoDeterminacionVariacion(dtr.getAnnoDeterminacion());
			        					variacion.setPersonaIdVariacion(dtr.getPersonaId());
			        					variacion.setConceptoIdVariacion(dtr.getConceptoId());
			        					variacion.setSubconceptoIdVariacion(dtr.getSubconceptoId());
			        					variacion.setBaseImponibleVariacion(dtr.getBaseImponible());
			        					variacion.setImpuestoVariacion(dtr.getImpuesto());
			        					variacion.setFechaRegistroDtVariacion(dtr.getFechaRegistro());
			        					variacion.setFlagAumento(Constante.ESTADO_ACTIVO);
			        					
			        					variacion.setUsuarioId(usuarioId);
			        					variacion.setEstado(Constante.ESTADO_ACTIVO);
			        					variacion.setFechaRegistro(DateUtil.getCurrentDate());
			        					variacion.setTerminal(terminal);
			        								        					
			        					deterVehicDao.guardarDeterminacionVariacion(variacion);
			        					
									//return true;
			        					return 2;
								} 
								else { // Si Pagos
									
									montoCuota = impDiff.divide(new BigDecimal(String.valueOf(cuotas.getNroCuotas())),2, RoundingMode.HALF_UP);
									// Guardar las deudas solo con la diferencia
									for (int i = 0; i < cuotas.getNroCuotas(); i++) {
										// Guardar deuda
										CdDeuda deuda = new CdDeuda();
										deuda.setTipoDeudaId(Constante.TIPO_DEUDA_AUTOGENERADO);
										deuda.setPersonaId(datos.getPersonaId());
										deuda.setConceptoId(Constante.CONCEPTO_VEHICULAR);
										deuda.setSubconceptoId(Constante.SUBCONCEPTO_VEHICULAR);
										deuda.setDeterminacionId(deterId);
										deuda.setAnnoDeuda(datos.getAnioAfec());
										deuda.setFechaEmision(DateUtil.getCurrentDate());
										deuda.setFechaVencimiento(listaFV.get(i).getFechaVencimiento());
										deuda.setNroCuota(listaFV.get(i).getCuota());
										deuda.setMontoOriginal(dtr.getImpuestoDiferencia());
										deuda.setInsoluto(montoCuota);
										if (deuda.getNroCuota() == cuotas.getCuotaDerechoEmision()) {
											deuda.setDerechoEmision(cuotas.getMontoDerechoEmision());
											deuda.setTotalDeuda(montoCuota.add(deuda.getDerechoEmision()));
										} else {
											deuda.setDerechoEmision(BigDecimal.ZERO);
											deuda.setTotalDeuda(montoCuota);
										}
										deuda.setNroReferencia(djvehicularId);
										deuda.setNroCuentaBanco(null);
										deuda.setUsuarioId(usuarioId);
										deuda.setFechaRegistro(DateUtil.getCurrentDate());
										deuda.setTerminal(terminal);
										deuda.setEstadoDeudaId(Constante.ESTADO_DEUDA_DETERMINADO);
										int deudaId = deterVehicDao.guardarDeuda(deuda);
										// Guardar deuda historica
										CdDeudaHistorica dh = new CdDeudaHistorica();
										CdDeudaHistoricaPK id = new CdDeudaHistoricaPK();
										id.setDeudaId(deudaId);
										dh.setId(id);
										dh.setTipoMovimientoId(Constante.TIPO_MOV_GENERADO);
										dh.setDeterminacionId(deuda.getDeterminacionId());
										dh.setPersonaId(deuda.getPersonaId());
										dh.setFechaMovimiento(deuda.getFechaEmision());
										dh.setTipoDeuda(deuda.getTipoDeudaId());
										dh.setFechaVencimiento(deuda.getFechaVencimiento());
										dh.setInsoluto(deuda.getInsoluto());
										dh.setTotal(deuda.getTotalDeuda());
										dh.setUsuarioId(usuarioId);
										dh.setEstado(Constante.ESTADO_ACTIVO);
										dh.setFechaRegistro(DateUtil.getCurrentDate());
										dh.setTerminal(terminal);
										deterVehicDao.guardarDeudaHistorica(dh);
									}
									
									if(deuValorPagPrevio.getTotalCancelado().doubleValue() > 0){
										inputacionDao.inputarDeudaSinValor(deterPrevio.getDeterminacionId(), deterId, usuarioId, terminal);
									}else{
										// Inahabilita anteriores, no funciona desde aca, ya está en el store de inputación
										determinacionDao.cambiarEstadoDeterminacion(deterPrevio.getDeterminacionId(),Constante.ESTADO_ELIMINADO);
										deterVehicDao.cambiarEstadoDeterminacionVehic(deterPrevio.getDeterminacionId(),Constante.ESTADO_ELIMINADO);
										determinacionDao.cambiarEstadoDeudasDeter(deterPrevio.getDeterminacionId(),	Constante.ESTADO_ELIMINADO);
										determinacionDao.cambiarEstadoDeudaHistoricaDeter(deterPrevio.getDeterminacionId(),Constante.ESTADO_ELIMINADO);
									}
									
									//Obtenemos los demás datos de la Determinacion Previa.
									  DtDeterminacion dt1 =deterVehicDao.getDatosPreviaDeterminacionActiva(deterPrevio.getDeterminacionId());
									
									//Insertamos en la tabla dt_determinacion_valor,para registrar la variación en ambas determinaciones.
		        					DtDeterminacionValor variacion=new DtDeterminacionValor();
			        					variacion.setDeterminacionId(dt1.getDeterminacionId());
			        					variacion.setAnnoDeterminacion(dt1.getAnnoDeterminacion());
			        					variacion.setPersonaId(dt1.getPersonaId());
			        					variacion.setConceptoId(dt1.getConceptoId());
			        					variacion.setSubconceptoId(dt1.getSubconceptoId());
			        					variacion.setBaseImponible(dt1.getBaseImponible());
			        					variacion.setImpuesto(dt1.getImpuesto());
			        					variacion.setFechaRegistroDt(dt1.getFechaRegistro());
			        					
			        					variacion.setDeterminacionIdVariacion(deterId);
			        					variacion.setAnnoDeterminacionVariacion(dtr.getAnnoDeterminacion());
			        					variacion.setPersonaIdVariacion(dtr.getPersonaId());
			        					variacion.setConceptoIdVariacion(dtr.getConceptoId());
			        					variacion.setSubconceptoIdVariacion(dtr.getSubconceptoId());
			        					variacion.setBaseImponibleVariacion(dtr.getBaseImponible());
			        					variacion.setImpuestoVariacion(dtr.getImpuesto());
			        					variacion.setFechaRegistroDtVariacion(dtr.getFechaRegistro());
			        					variacion.setFlagAumento(Constante.ESTADO_ACTIVO);
			        					
			        					variacion.setUsuarioId(usuarioId);
			        					variacion.setEstado(Constante.ESTADO_ACTIVO);
			        					variacion.setFechaRegistro(DateUtil.getCurrentDate());
			        					variacion.setTerminal(terminal);
			        								        					
			        					deterVehicDao.guardarDeterminacionVariacion(variacion);
			        					
									//return true;
			        					return 3;
									
								}//fin si tiene pagos
							}//fin diff>0
					//si las bases son iguales   
							else if(impDiff.doubleValue() == 0)	// && deterPrevio.getBaseImponible().compareTo(dtr.getBaseImponible())==0
							{
								//Se inactiva la nueva Determinacion:
									deterVehicDao.actualizarDeterminacionPrevia(deterId,usuarioId,Constante.ESTADO_INACTIVO);
									deterVehicDao.actualizarDeterminacionVehicularPrevia(deterId,usuarioId,Constante.ESTADO_INACTIVO);
									//String msg="";
									//msg="Tiene valores,no existe diferencia en el monto de la B.I. por lo tanto no genera deuda, mantiene la deuda anterior activa";
									//return true;
									return 4;
							}
					//si las base actual es menor a la nueva
							else if(impDiff.doubleValue() < 0 )//&& deterPrevio.getBaseImponible().compareTo(dtr.getBaseImponible())>0
							{//inicia
								
								//Se inactiva la nueva Determinacion:
								deterVehicDao.actualizarDeterminacionPrevia(deterId,usuarioId,Constante.ESTADO_INACTIVO);
								deterVehicDao.actualizarDeterminacionVehicularPrevia(deterId,usuarioId,Constante.ESTADO_INACTIVO);
								
								
								//Obtenemos los demás datos de la Determinacion Previa.
								  DtDeterminacion dt1 =deterVehicDao.getDatosPreviaDeterminacionActiva(deterPrevio.getDeterminacionId());
								
								//Insertamos en la tabla dt_determinacion_valor,para registrar la variación en ambas determinaciones.
	        					DtDeterminacionValor variacion=new DtDeterminacionValor();
		        					variacion.setDeterminacionId(dt1.getDeterminacionId());
		        					variacion.setAnnoDeterminacion(dt1.getAnnoDeterminacion());
		        					variacion.setPersonaId(dt1.getPersonaId());
		        					variacion.setConceptoId(dt1.getConceptoId());
		        					variacion.setSubconceptoId(dt1.getSubconceptoId());
		        					variacion.setBaseImponible(dt1.getBaseImponible());
		        					variacion.setImpuesto(dt1.getImpuesto());
		        					variacion.setFechaRegistroDt(dt1.getFechaRegistro());
		        					
		        					variacion.setDeterminacionIdVariacion(deterId);
		        					variacion.setAnnoDeterminacionVariacion(dtr.getAnnoDeterminacion());
		        					variacion.setPersonaIdVariacion(dtr.getPersonaId());
		        					variacion.setConceptoIdVariacion(dtr.getConceptoId());
		        					variacion.setSubconceptoIdVariacion(dtr.getSubconceptoId());
		        					variacion.setBaseImponibleVariacion(dtr.getBaseImponible());
		        					variacion.setImpuestoVariacion(dtr.getImpuesto());
		        					variacion.setFechaRegistroDtVariacion(dtr.getFechaRegistro());
		        					variacion.setFlagAumento(Constante.ESTADO_INACTIVO);
		        					
		        					variacion.setUsuarioId(usuarioId);
		        					variacion.setEstado(Constante.ESTADO_ACTIVO);
		        					variacion.setFechaRegistro(DateUtil.getCurrentDate());
		        					variacion.setTerminal(terminal);
		        								        					
		        					deterVehicDao.guardarDeterminacionVariacion(variacion);
								//return true;
		        					return 5;
								
							}//termina
						}
					}
				}
			} else {
				// TODO: DECLARACION YA ESTA DETERMINADA
				/*
				 * Preguntar si se puede ahi mismo hacer una actualización de la
				 * misma determinación (Logicamente no deberia, debe actualizar
				 * la dj y luego generar la nueva determinación para la dj
				 * actualizada
				 */
				throw new DeterminacionVehicularException(
						"Declaración Jurada Vehicular, Ya ha sido determinada");
			}
		} catch (DeterminacionVehicularException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new SisatException(ex.getMessage());
		}
		//return false;
		return 0;
	}
	
	public ImpuestoVehicular calcular(DatosNecesariosDeterDTO datos)throws SisatException{
	
		try {
			
		
		//obtener tasa
		DtTasaVehicular tv = deterVehicDao.getTasaVehicular(datos.getAnioAfec());
			if (tv == null) {
				throw new DeterminacionVehicularException("No está definido la tasa del impuesto");
			}
		
			BigDecimal uit = generalDao.getUitAnio(datos.getAnioAfec());

			if (uit == null) {
				throw new DeterminacionVehicularException("No esta definido la UIT para el año: " + datos.getAnioAfec());
			}

			BigDecimal tasaAnual = tv.getTasaAnual().divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
			BigDecimal impuestoMin = tv.getPorcUitMin().divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
			impuestoMin = impuestoMin.multiply(uit);
			

			BigDecimal valorMEF = deterVehicDao.getValorMEF(datos.getCategVehicId(),
					datos.getMarcaVehicId(),
					datos.getModeloVehicId(),
					datos.getAnioAfec(),
					datos.getAnioFabric());
			
		BigDecimal factorAjuste= new BigDecimal(0);
		ImpuestoVehicular iv = null;
		if (valorMEF == null) {
			
				BigDecimal montoAnioMenorAntig = deterVehicDao.getMontoAnioMenorAntig(datos.getCategVehicId(),
						datos.getMarcaVehicId(),
						datos.getModeloVehicId(),
						datos.getAnioAfec());

			
			iv = new ImpuestoVehicular(null, datos.getAnioAfec() - 1,montoAnioMenorAntig,datos.getAnioFabric(), 
					datos.getValorAdquiSoles(),new BigDecimal(0),tasaAnual, impuestoMin,datos.getPorcentajePropiedad());
			if(iv.getBaseImponible()==null){
				throw new DeterminacionVehicularException((new StringBuffer()).append("Verificar valor referencial para categoría ").append(datos.getCategVehicId())
						.append(" , marca ").append(datos.getMarcaVehicId())
						.append(" y modelo ").append(datos.getModeloVehicId())
						.append(" para el vehículo en el año de afectacion ").append(datos.getAnioAfec())
						.append(" y año fabricacion ").append(datos.getAnioFabric()).toString());
			}
			
			//factor
			BigDecimal valorAjustado = (new TablaReferencialMEF()).getValorAjusteMEF(datos.getAnioAfec() - 1,montoAnioMenorAntig,datos.getAnioFabric());

			factorAjuste= (new TablaReferencialMEF()).getFactor(datos.getAnioAfec() - 1, datos.getAnioFabric());
			
			iv.setBaseImponible(iv.getBaseImponible().setScale(2,BigDecimal.ROUND_UP));
			iv.setValorAjuste(valorAjustado.setScale(2, BigDecimal.ROUND_UP));
			iv.setAjuste(factorAjuste.setScale(2, BigDecimal.ROUND_UP));
			iv.setValoreRefencialMEF(montoAnioMenorAntig.setScale(2, BigDecimal.ROUND_UP));

		} else {
			iv = new ImpuestoVehicular(valorMEF, 0, null,datos.getAnioFabric(), datos.getValorAdquiSoles(),
					new BigDecimal(0), tasaAnual, impuestoMin,datos.getPorcentajePropiedad());
			if(iv.getBaseImponible()==null){
				throw new DeterminacionVehicularException("No se ha podido calcular el impuesto");
			}
			
//			System.out.println("factor de ajuste: "+1);
//			System.out.println("valor ajustado: "+0);
			factorAjuste= new BigDecimal("1.00");

			iv.setAjuste(factorAjuste.setScale(2,BigDecimal.ROUND_UP));
			
			iv.setValorAjuste(iv.getValoreRefencialMEF().setScale(2,BigDecimal.ROUND_UP));
		}
		
		return iv;
		
		} catch (SisatException e) {
		throw e;
		
		}
	}

	@Override
	public void duplicarDj(int anho, int user, String terminal , Integer inicio, Integer fin) throws SisatException {

		System.out.println("***************** INICIO masiva vehicular EJB *****************");
		
		
//		BigDecimal nroDj = deterVehicDao.duplicarDjAnho(anho, user, terminal);
//
//		System.out.println("Nro de dj duplicadas: " + nroDj);

		int size = deterVehicDao.getCountDjVehicularesSinDeterminar(anho);

		System.out.println("Nro de dj a determinar: " + size); 
		
		double c = size / (double) 100;
		int max = (int) Math.ceil(c);
		int inf = 0;
		int sup = 100;
		int pivot = 100;
		List<Integer> listDjVehicularId = null;
		int count = 0;

		for (int i = inicio; i < fin; i++) {//Comentado en FEB-2017: for (int i = 0; i < max; i++)
			listDjVehicularId = deterVehicDao.getDjVehicularesSinDeterminar(anho, inicio, fin);//Comentado en FEB-2017: listDjVehicularId = deterVehicDao.getDjVehicularesSinDeterminar(anho, inf, sup);
			

			for (Integer id : listDjVehicularId) {
				try {
//					Cod. anterior //inicio
//					boolean b = generarDeterminacion(id, user, terminal);
//					if (b) {
//						//System.out.println("Determinacion correcta para la dj: " + id);
//						count++;
//					} else {
//						System.out.println("No se ha podido generar determinacion para la dj: " + id);
//					}
//					Cod. anterior //fin
					int b = generarDeterminacion(id, user, terminal);
					if (b==1) {
						//System.out.println("Determinacion correcta para la dj: " + id);
						count++;
					} else if(b==0) {
						System.out.println("No se ha podido generar determinacion para la dj: " + id);
					}
				} catch (SisatException e) {
					System.out.println("SisatException: Ocurrio un error para la dj: " + id);
					System.out.println(e.getMessage());
					//e.printStackTrace();
				}
			}
			listDjVehicularId = null;
			inf = inf + pivot;
			sup = sup + pivot;
			if (i == 0) {
				inf++;
			}
		}
		
		System.out.println("Nro de dj determinadas: " + count);
		
		System.out.println("***************** FIN masiva vehicular EJB *****************");
	}
	
	
	@Override
	public void generarDeterminacionPorAnho(int anho, int user, String terminal) throws SisatException {

		System.out.println("***************** INICIO masiva determinacion vehicular por anho EJB *****************");		

		int size = deterVehicDao.getCountDjVehicularesPorAnho(anho);

		System.out.println("Nro de dj a realizar determinacion: " + size); 
		
		double c = size / (double) 100;
		int max = (int) Math.ceil(c);
		int inf = 0;
		int sup = 100;
		int pivot = 100;
		List<Integer> listDjVehicularId = null;
		int count = 0;

		for (int i = 0; i < max; i++) {
			listDjVehicularId = deterVehicDao.getDjVehicularesPorAnho(anho, inf, sup);
			

			for (Integer id : listDjVehicularId) {
				try {
//					Cod. anterior //inicio
//					boolean b = generarDeterminacion(id, user, terminal);
//					if (b) {
//						//System.out.println("Determinacion correcta para la dj: " + id);
//						count++;
//					} else {
//						System.out.println("No se ha podido generar determinacion para la dj: " + id);
//					}
//					Cod. anterior //fin
					int b = generarDeterminacion(id, user, terminal);
					if (b==1) {
						//System.out.println("Determinacion correcta para la dj: " + id);
						count++;
					} else if(b==0){
						System.out.println("No se ha podido generar determinacion para la dj: " + id);
					}
				} catch (SisatException e) {
					System.out.println("SisatException: Ocurrio un error para la dj: " + id);
					System.out.println(e.getMessage());
					//e.printStackTrace();
				}
			}
			listDjVehicularId = null;
			inf = inf + pivot;
			sup = sup + pivot;
			if (i == 0) {
				inf++;
			}
		}
		
		System.out.println("Nro de dj determinadas: " + count);
		
		System.out.println("***************** FIN masiva determinacion vehicular por anho EJB*****************");
	}

	@Override
	public Integer actualizarDeterminacionPrevia(Integer determinacion,
			Integer usuario, String estado) throws Exception {
		// TODO Auto-generated method stub
		return null;
		
	}

	@Override
	public Integer actualizarDeterminacionVehicularPrevia(
			Integer determinacion, Integer usuario, String estado)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	

}
