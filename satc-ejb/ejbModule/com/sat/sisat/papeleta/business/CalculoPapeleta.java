package com.sat.sisat.papeleta.business;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.obligacion.dto.ObligacionDTO;
import com.sat.sisat.persistence.entity.CdDeuda;
import com.sat.sisat.persistence.entity.CdDeudaHistorica;
import com.sat.sisat.persistence.entity.CdDeudaHistoricaPK;
import com.sat.sisat.persistence.entity.DtDeterminacion;
import com.sat.sisat.persistence.entity.PaIncidencia;
import com.sat.sisat.persistence.entity.PaInfraccion;
import com.sat.sisat.persistence.entity.PaPapeleta;
import com.sat.sisat.predial.business.CalculoPredialBoRemote;

public class CalculoPapeleta {
	
	public static Integer PERIODO_REINCIDENCIA_MESES = 12;
	public static Integer PERIODO_PUNTOS_ACUM_MESES = 24;
	
	PapeletaBoRemote papeletaBo;
	CalculoPredialBoRemote calculoPredialBo;
	private List<CdDeuda> listFlagsDeudas = new ArrayList<CdDeuda>();
	public CalculoPapeleta(CalculoPredialBoRemote calculoPredialBo,PapeletaBoRemote papeletaBo){
		setCalculoPredialBo(calculoPredialBo);
		setPapeletaBo(papeletaBo);
	}
	
	
	public Integer papeletaDeterminada (Integer papeleiaId) throws Exception
	{
	
	//Nos devuelve las detemrinaciones activas generadas para la papeleta.   
	Integer cantidad;
	
	cantidad=papeletaBo.papeletaDeterminada(papeleiaId);
	
	return cantidad ;
	}
	
	
	public BigDecimal generarDeterminacion(PaPapeleta papeleta, PaIncidencia paIncidencia) throws Exception{
		
		
		BigDecimal  montoTotal=new BigDecimal(0);
		//BigDecimal sancionUit=new BigDecimal(0);
		
		try{
			
			Calendar calFecInfraccion=Calendar.getInstance();
			calFecInfraccion.setTime(papeleta.getFechaInfraccion());
			
			Calendar calFecActual=Calendar.getInstance();
			calFecActual.setTime(DateUtil.getCurrentDate());
			
			//Fecha 08-01-2018: Las papeletas ingresas tienen que ser calculadas con la UIT del periodo vigente.
			Calendar UITanioActual = Calendar.getInstance();
			
			//PaIncidencia
			BigDecimal uitVigente=papeletaBo.obtenerUIT(calFecInfraccion.get(Calendar.YEAR));
			PaInfraccion infraccion=papeletaBo.getPaInfraccion(papeleta.getInfraccionId());
			//Se considera la Reincidencia, al hecho de cometer nuevamente la misma infraccin dentro del lapso de doce (12) meses y debe ser sancionada con el doble de la multa establecida
			
			Integer reincidencia = papeletaBo.getReincidencia(paIncidencia,papeleta.getPersonaInfractorId(), papeleta.getInfraccionId(),papeleta.getFechaInfraccion(), PERIODO_REINCIDENCIA_MESES);
			
			BigDecimal multaUit=new BigDecimal(0);
			if(infraccion.getMultaUit()!=null){
				multaUit=infraccion.getMultaUit();
			}
			
			/*Sept-2016 - De acuerdo al D.S. Nº 007-2016-MTC, Tasa de sanción para la infracción M40:*/
//			if(reincidencia>0){
//				sancionUit=papeletaBo.obtenerSancionInfraccion(papeleta.getInfraccionId(),reincidencia);
//			}
			
			double multa=0;
			if(reincidencia>0){
//				if(sancionUit.compareTo(BigDecimal.ZERO) > 0){
//					multa=sancionUit.doubleValue()*uitVigente.doubleValue()/100;
//				}else{
				multa=2*multaUit.doubleValue()*uitVigente.doubleValue()/100;
//				}
			}else{
				multa=multaUit.doubleValue()*uitVigente.doubleValue()/100;
			}
			
			montoTotal=new BigDecimal(multa);
			//Los puntos generados en cada infraccin tendr�n una vigencia de 24 meses, vencido dicho plazo los puntos se borraran autom�ticamente
			//La acumulacion de puntos es en los 24 meses
			
			PaIncidencia incidenciaNueva=new PaIncidencia(); 
//			incidenciaNueva.setIncidenciaId(paIncidencia.getIncidenciaId());
			incidenciaNueva.setPersonaId(papeleta.getPersonaInfractorId());
			incidenciaNueva.setInfraccionId(papeleta.getInfraccionId());
			incidenciaNueva.setFechaInfraccion(papeleta.getFechaInfraccion());
			incidenciaNueva.setPapeletaId(papeleta.getPapeletaId());
			incidenciaNueva.setMontoMulta(multa);			
			incidenciaNueva.setReincidente((reincidencia+1));
			
			/** La actualizacion sobre la firmeza de una papeleta se realiza por un job de base de datos
			 * todos los dias, a las primeras horas del dia, por lo tanto, si los puntos expiran en una incidencia
			 * estos automaticamente pasan a la unificacion debido a que ya se verifico al inicio del dia la 
			 * expiracion de los puntos firmes */
			incidenciaNueva.setPuntos(paIncidencia.getPuntos());
			incidenciaNueva.setPuntosFirmes(paIncidencia.getPuntosFirmes());
			incidenciaNueva.setFlagFirme(paIncidencia.getFlagFirme());
			incidenciaNueva.setFechaFirme(paIncidencia.getFechaFirme());
			
			//--
			double montoInfraccion=multaUit.doubleValue()*uitVigente.doubleValue()/100;
			incidenciaNueva.setMontoInfraccion(montoInfraccion);
			
			/** Inicio - Quitando el calculo de expiracion de puntos por el nuevo que es realizado por un proceso batch en DB*/
//			//calculo de puntos-Correccion de anio de afectacion
//			Calendar calFecInfraccionAdd24Month=Calendar.getInstance();
//			calFecInfraccionAdd24Month.setTime(papeleta.getFechaInfraccion());
//			
//			calFecInfraccionAdd24Month.add(Calendar.MONTH, PERIODO_PUNTOS_ACUM_MESES);
//			if(DateUtil.diferenciaFechas(calFecInfraccionAdd24Month,calFecActual,1)<=0){
//				incidencia.setPuntos(infraccion.getPuntos());
//			}else{
//				incidencia.setPuntos(0);
//			}
//			//calculo de puntos
			/** Fin */
			
			
			
			//--
			incidenciaNueva.setEstado(Constante.ESTADO_ACTIVO);
			papeletaBo.guardarPaIncidencia(incidenciaNueva, paIncidencia);
			
			if(papeleta.getEstado().trim().equals(Constante.ESTADO_PAPELETA_DEFINITIVO)||
					papeleta.getEstado().trim().equals(Constante.ESTADO_PAPELETA_NO_COINCIDE)||
					papeleta.getEstado().trim().equals(Constante.ESTADO_PAPELETA_REGISTRADO)||
					papeleta.getEstado().trim().equals(Constante.ESTADO_PAPELETA_PENDIENTE)){
				//Inactivar la ultima determinacion de la papaleta
				DtDeterminacion determinacionAnt=getCalculoPredialBo().getDtDeterminacionPapeleta(papeleta.getPapeletaId(),Constante.CONCEPTO_PAPELETA);
				if(determinacionAnt!=null){
					determinacionAnt.setFechaActualizacion(DateUtil.getCurrentDate());
		        	determinacionAnt.setEstado(Constante.ESTADO_INACTIVO);
		        	//recuperar y resguardar los flag de la deuda anterior //recuperarFlagCdDeuda
		        	listFlagsDeudas=calculoPredialBo.recuperarFlagCdDeuda(determinacionAnt.getDeterminacionId(), Constante.ESTADO_ACTIVO);
		        	getCalculoPredialBo().guardarDeterminacion(determinacionAnt);		        	
					inactivarDatosDeterminacion(determinacionAnt);
				}
				//Guardar determinacion
				//DtDeterminacion
				DtDeterminacion determinacion=new DtDeterminacion();
				determinacion.setDeterminacionId(Constante.RESULT_PENDING);
				determinacion.setPersonaId(papeleta.getPersonaInfractorId());
				//determinacion.setAnnoDeterminacion(DateUtil.getAnioActual());//Correccion de anio de afectacion
				determinacion.setAnnoDeterminacion(calFecInfraccion.get(Calendar.YEAR));
		        determinacion.setBaseAfecta(BigDecimal.valueOf(multa).setScale(2, RoundingMode.HALF_EVEN));
		        determinacion.setBaseExonerada(BigDecimal.ZERO);
		        determinacion.setBaseImponible(BigDecimal.valueOf(multa).setScale(2, RoundingMode.HALF_EVEN));
		        determinacion.setConceptoId(infraccion.getConceptoId());
		        determinacion.setSubconceptoId(infraccion.getSubConceptoId());
		        determinacion.setDjreferenciaId(papeleta.getPapeletaId());
		        determinacion.setImpuesto(BigDecimal.ZERO);
		        determinacion.setImpuestoDiferencia(BigDecimal.ZERO);
		        determinacion.setNroCuotas(1);
		        determinacion.setNroDocumento(papeleta.getNroPapeleta());
		        determinacion.setPorcPropiedad(BigDecimal.valueOf(0));
		        
		        determinacion.setBaseAfectaAnterior(BigDecimal.valueOf(0));
		        determinacion.setBaseExoneradaAnterior(BigDecimal.valueOf(0));
		        determinacion.setBaseImponibleAnterior(BigDecimal.valueOf(0));
		        determinacion.setImpuestoAnterior(BigDecimal.valueOf(0));
		        determinacion.setPersonaId(papeleta.getPersonaInfractorId());
		        
		        determinacion.setEstado(Constante.ESTADO_ACTIVO);
		        Integer Id=getCalculoPredialBo().guardarDeterminacion(determinacion);
		        determinacion.setDeterminacionId(Id);
		        //con la papleta_id retornar la deuda anterior activa
		        String flag_cc;
		        String flag_detencion;
		        Timestamp fechaDetencion;
		    	if(determinacion.getDeterminacionId()==Constante.RESULT_PENDING){
		    		//nueva determinacion
		    		flag_cc=null;
		    		 flag_detencion=null;
		    		 fechaDetencion=null;
				} else {
					/**anterior determinacion
					 obtener la deuda anterior con la determinacion anterior y
					 coger los flag q se necitan*/
					if (determinacionAnt != null) {
						flag_cc = listFlagsDeudas.get(0).getFlagCc();
						flag_detencion = listFlagsDeudas.get(0).getFlagDetencion();
						fechaDetencion = listFlagsDeudas.get(0).getFechaDtencion();
					} else {
						flag_cc = null;
						flag_detencion = null;
						fechaDetencion = null;
					}
				}
		    	
		        if(Id>Constante.RESULT_PENDING){
			    	generarDeuda(determinacion,papeleta.getFechaInfraccion(),flag_cc,flag_detencion,fechaDetencion);
		        }
			}
		        
		}catch(Exception e){
			throw e;
		}
		return montoTotal;
	}
	
	public BigDecimal generarDeterminacionDE(PaPapeleta papeleta,Integer incidenciaId) throws Exception{
		BigDecimal montoTotal=new BigDecimal(0);
		try{
			Calendar calFecInfraccion=Calendar.getInstance();
			calFecInfraccion.setTime(papeleta.getFechaInfraccion());
			
			//PaIncidencia
			BigDecimal uitVigente=papeletaBo.obtenerUIT(calFecInfraccion.get(Calendar.YEAR));
			PaInfraccion infraccion=papeletaBo.getPaInfraccion(papeleta.getInfraccionId());
			//Se considera la Reincidencia, al hecho de cometer nuevamente la misma infracciï¿½n dentro del lapso de doce (12) meses y debe ser sancionada con el doble de la multa establecida
			
			BigDecimal multaUit=new BigDecimal(0);
			if(infraccion.getMultaUit()!=null){
				multaUit=infraccion.getMultaUit();
			}
			
			double multa=multaUit.doubleValue()*uitVigente.doubleValue()/100;
			
			montoTotal=new BigDecimal(multa);
			//Los puntos generados en cada infracciï¿½n tendrï¿½n una vigencia de 24 meses, vencido dicho plazo los puntos se borraran automï¿½ticamente
			//La acumulacion de puntos es en los 24 meses
			
			PaIncidencia incidencia=new PaIncidencia();			
			incidencia.setPersonaId(papeleta.getPersonaInfractorId());
			incidencia.setInfraccionId(papeleta.getInfraccionId());
			incidencia.setFechaInfraccion(papeleta.getFechaInfraccion());
			incidencia.setPapeletaId(papeleta.getPapeletaId());
			incidencia.setMontoMulta(multa);
			incidencia.setReincidente(0);
			//--
			double montoInfraccion=multaUit.doubleValue()*uitVigente.doubleValue()/100;
			incidencia.setMontoInfraccion(montoInfraccion);
			incidencia.setPuntos(0);
			//--
			incidencia.setEstado(Constante.ESTADO_ACTIVO);
			papeletaBo.guardarPaIncidenciaDE(incidencia);
		}catch(Exception e){
			throw e;
		}
		return montoTotal;
	}
	
	public void inactivarDatosDeterminacion(DtDeterminacion determinacion)throws Exception{
		//Determinacion Deuda
		getCalculoPredialBo().actualizaEstadoCdDeuda(determinacion.getDeterminacionId(), Constante.ESTADO_INACTIVO);
	}
	
	public Boolean generarDeuda(DtDeterminacion determinacion,Timestamp fehaEmisionDeuda,String flag_cc,String flag_detencion,Timestamp fechaDetencion)throws Exception{
			CdDeuda deuda = new CdDeuda();
			deuda.setTipoDeudaId(Constante.TIPO_DEUDA_AUTOGENERADO);
			deuda.setDeudaId(Constante.RESULT_PENDING);
			deuda.setPersonaId(determinacion.getPersonaId());
			
			deuda.setConceptoId(determinacion.getConceptoId());
			deuda.setSubconceptoId(determinacion.getSubconceptoId());
			
			deuda.setDeterminacionId(determinacion.getDeterminacionId());
			deuda.setAnnoDeuda(determinacion.getAnnoDeterminacion());
			deuda.setFechaEmision(fehaEmisionDeuda);
			deuda.setFechaVencimiento(fehaEmisionDeuda);
			deuda.setNroCuota(1);
			deuda.setMontoOriginal(determinacion.getImpuesto());
			
			deuda.setReajuste(BigDecimal.ZERO);
			deuda.setInteresMensual(BigDecimal.ZERO);
			deuda.setInteresAnual(BigDecimal.ZERO);
			deuda.setInteresCapitalizado(BigDecimal.ZERO);
			deuda.setInsolutoCancelado(BigDecimal.ZERO);
			deuda.setReajusteCancelado(BigDecimal.ZERO);
			deuda.setDerechoEmision(BigDecimal.ZERO);
			deuda.setDerechoEmisionCancelado(BigDecimal.ZERO);
			deuda.setInteresMensualCancelado(BigDecimal.ZERO);
			deuda.setInteresCapiCancelado(BigDecimal.ZERO);
			deuda.setTotalCancelado(BigDecimal.ZERO);
			deuda.setFlagCc(flag_cc);
			deuda.setFlagDetencion(flag_detencion);
			deuda.setFechaDtencion(fechaDetencion);
			deuda.setEstadoDeudaId(Constante.ESTADO_DEUDA_DETERMINADO);
			deuda.setDerechoEmision(BigDecimal.ZERO);
			deuda.setTotalDeuda(determinacion.getBaseImponible());
			
			deuda.setInsoluto(determinacion.getBaseImponible());
			deuda.setTotalCancelado(new BigDecimal(0));
			deuda.setNroReferencia(determinacion.getDjreferenciaId());
			deuda.setNroCuentaBanco(null);
			
			deuda.setPapeletaId(determinacion.getDjreferenciaId());//Solo para papeletas
			deuda.setNroPapeleta(determinacion.getNroDocumento());//Solo para papeletas
			
			deuda.setEstado(Constante.ESTADO_ACTIVO);
			int deudaId = getCalculoPredialBo().guardarDeuda(deuda);
			
			//Guardar deuda historica
			CdDeudaHistorica dh = new CdDeudaHistorica();
			CdDeudaHistoricaPK id = new CdDeudaHistoricaPK();
			id.setDeudaId(deudaId);
			dh.setId(id);
			// dh.setTipoMovimientoId();
			dh.setDeterminacionId(deuda.getDeterminacionId());
			dh.setPersonaId(deuda.getPersonaId());
			// dh.setFechaMovimiento();
			dh.setTipoDeuda(deuda.getTipoDeudaId());
			dh.setFechaVencimiento(deuda.getFechaVencimiento());
			dh.setInsoluto(deuda.getInsoluto());
			dh.setTotal(deuda.getTotalDeuda());
			
			//cc: dh.setUsuarioId(Constante.USUARIO_ID);
			dh.setEstado(Constante.ESTADO_ACTIVO);
			//cc: dh.setFechaRegistro(DateUtil.getCurrentDate());
			//cc: dh.setTerminal(Constante.TERMINAL);
			getCalculoPredialBo().guardarDeudaHistorica(dh);
		
		
		return Boolean.TRUE;
	}

	public CalculoPredialBoRemote getCalculoPredialBo() {
		return calculoPredialBo;
	}
	
	public PapeletaBoRemote getPapeletaBo() {
		return papeletaBo;
	}

	public void setPapeletaBo(PapeletaBoRemote papeletaBo) {
		this.papeletaBo = papeletaBo;
	}

	public void setCalculoPredialBo(CalculoPredialBoRemote calculoPredialBo) {
		this.calculoPredialBo = calculoPredialBo;
	}

	public List<CdDeuda> getListFlagsDeudas() {
		return listFlagsDeudas;
	}

	public void setListFlagsDeudas(List<CdDeuda> listFlagsDeudas) {
		this.listFlagsDeudas = listFlagsDeudas;
	}
}