package com.sat.sisat.predial.managed.calculo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.jms.Session;

import com.sat.sisat.common.business.GeneralBoRemote;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.Util;
import com.sat.sisat.determinacion.vehicular.dto.DeudaPagosDTO;
import com.sat.sisat.determinacion.vehicular.dto.DeudaValoresDTO;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.persistence.entity.CdDeuda;
import com.sat.sisat.persistence.entity.CdDeudaHistorica;
import com.sat.sisat.persistence.entity.CdDeudaHistoricaPK;
import com.sat.sisat.persistence.entity.DtCondiEspecialContri;
import com.sat.sisat.persistence.entity.DtCuotaConcepto;
import com.sat.sisat.persistence.entity.DtDeterminacion;
import com.sat.sisat.persistence.entity.DtDeterminacionConstruccion;
import com.sat.sisat.persistence.entity.DtDeterminacionInstalacion;
import com.sat.sisat.persistence.entity.DtDeterminacionPredio;
import com.sat.sisat.persistence.entity.DtDeterminacionValor;
import com.sat.sisat.persistence.entity.DtFactorOfic;
import com.sat.sisat.persistence.entity.DtFechaVencimiento;
import com.sat.sisat.persistence.entity.DtTasaImpuestoPredial;
import com.sat.sisat.persistence.entity.GnCondicionEspecial;
import com.sat.sisat.persistence.entity.MpPersona;
import com.sat.sisat.persistence.entity.RpDjconstruccion;
import com.sat.sisat.persistence.entity.RpDjdireccion;
import com.sat.sisat.persistence.entity.RpDjpredial;
import com.sat.sisat.persistence.entity.RpDjuso;
import com.sat.sisat.persistence.entity.RpInstalacionDj;
import com.sat.sisat.persistence.entity.RpOtrosFrente;
import com.sat.sisat.predial.business.CalculoPredialBoRemote;
import com.sat.sisat.predial.dto.DatosDeterminacionPredialValoresDTO;
import com.sat.sisat.predial.dto.DeudaPagosPredialDTO;


public class DeterminacionPredial {

	public static Double cdblPorcentajeIncremento=0.05;//Si la cantida de niveles es >5 (incremento el 5%)
	
	private Double dblValorConstruccionAnioFiscal;
	private Double dblValorOtrasInstalacionesAnioFiscal;
	
	CalculoPredialBoRemote calculoPredialBo;
	GeneralBoRemote generalBo;
	
	private DtDeterminacion determinacionBase;
	private DeudaPagosPredialDTO deuSinValorPagPrevio;
	
	public static int FLAG_CALCULO_VALOR_TERRENO_FRENTE=1;
	
	public DeterminacionPredial(CalculoPredialBoRemote calculoPredialBo,GeneralBoRemote generalBo){
		setCalculoPredialBo(calculoPredialBo,generalBo);
		setGeneralBo(generalBo);
	}
	
	public MpPersona getContribuyente(Integer contribuyenteId) throws Exception{
		return getCalculoPredialBo().getFindPersona(contribuyenteId);
	}
	
	public String generarDeterminacionPredialComun(Integer personaId,Integer anio)throws Exception{
		String msg="";
		ArrayList<RpDjpredial> lDjPredial=getCalculoPredialBo().getAllRpDJpredial(personaId,anio);
		
		EstadoDeterminacionPredial estadoDeterminacion=new EstadoDeterminacionPredial();
    	Integer estadoFisca=estadoDeterminacion.estadoDtDeterminacion(lDjPredial);

		getCalculoPredialBo().inactivaDeterminacionAnterior(personaId,anio);//Corrige las determinacion y las deudas generadas de mas

		List<DeudaValoresDTO> lstDeuVal = getCalculoPredialBo().getDeudaValores(personaId,Constante.CONCEPTO_PREDIAL,anio);
		boolean tieneVal = false;
		for (DeudaValoresDTO dv : lstDeuVal) {
			if (dv.getFlagOp()!= null&&Util.toInteger(dv.getFlagOp())>0) {
				tieneVal = true;
				break;
			}
		}
		if(tieneVal){
			determinacionBase=getCalculoPredialBo().getMinDeterminacion(personaId,anio,Constante.CONCEPTO_PREDIAL,Constante.ESTADO_ACTIVO);
		}else{
			//Cod. anterior:://DtDeterminacion determinacionBase=getCalculoPredialBo().getDtDeterminacion(personaId,anio,Constante.CONCEPTO_PREDIAL,Constante.ESTADO_ACTIVO);//Siempre debe coger a la ultima determinacion activa base SI SI SI / NO NO NO esta sera la base
			determinacionBase=getCalculoPredialBo().getDtDeterminacion(personaId,anio,Constante.CONCEPTO_PREDIAL,Constante.ESTADO_ACTIVO);//Siempre debe coger a la ultima determinacion activa base SI SI SI / NO NO NO esta sera la base
		}
		

		/** Obteniendo la condicion especial del contribuyente */
		GnCondicionEspecial condicionEspContribuyente=getCalculoPredialBo().getGnCondicionEspecialContribuyente(personaId,anio);
		
		if(lDjPredial.size()==0){
			if(determinacionBase!=null){
				determinacionBase.setFechaActualizacion(DateUtil.getCurrentDate());
	        	determinacionBase.setEstado(Constante.ESTADO_INACTIVO);
	        	getCalculoPredialBo().guardarDeterminacion(determinacionBase);
	        	//Determinacion Predio
	    		getCalculoPredialBo().actualizaEstadoDtDeterminacionPredio(determinacionBase.getDeterminacionId(), Constante.ESTADO_INACTIVO);
	    		//Determinacion Construccion
	    		getCalculoPredialBo().actualizaEstadoDtDeterminacionConstruccion(determinacionBase.getDeterminacionId(), Constante.ESTADO_INACTIVO);
	    		getCalculoPredialBo().actualizaEstadoCdDeuda(determinacionBase.getDeterminacionId(), Constante.ESTADO_INACTIVO);
	    		
	    		msg="No posee predios vigentes para este periodo, se realizo el descargo de la deuda anterior";	
			}else{
				msg="No posee predios vigentes para este periodo, No se realiza el calculo predial";	
			}
		}
		
		if(lDjPredial.size()>0){
			DtDeterminacion determinacion=new DtDeterminacion();
			determinacion.setDeterminacionId(Constante.RESULT_PENDING);
			determinacion.setPersonaId(personaId);
			determinacion.setEstado(Constante.ESTADO_INACTIVO);
			determinacion.setConceptoId(Constante.CONCEPTO_PREDIAL);
			//--
			determinacion.setFiscalizado(Constante.FISCALIZADO_NO);
    		determinacion.setFiscaAceptada(Constante.FISCA_ACEPTADA_NO);
    		determinacion.setFiscaCerrada(Constante.FISCA_CERRADA_NO);
			Integer Id=getCalculoPredialBo().guardarDeterminacion(determinacion);
			determinacion.setDeterminacionId(Id);
			//Obtenemos la condicion especial del contribuyente independientemente de los Dj y los Periodos
			
			//Art�culo 19.- Los pensionistas propietarios de un solo predio, a nombre propio o de la sociedad conyugal, que est� destinado a vivienda de los mismos
			Boolean esPensionistaInafecto=Boolean.FALSE;
			if (condicionEspContribuyente != null
					&& condicionEspContribuyente.getTipoCondEspecialId() == Constante.CONDICION_ESPECIAL_CONTRIBUYENTE_PENSIONISTA) {
				if (lDjPredial.size() > 1) {
					//Se agregó las líneas de código en caso sea Cochera Independizada(Solo Casa Habitación y Cochera de Habitación)::               // 27.03.2015-(Caso Cod.25930 TEJADA FERNANDEZ)
						if (lDjPredial.size()==2){
							Integer flagpredio=0,flagpredios=0,djId,tipoUso,cantidadUso,contador=0,contadorC=0;
							for(int i=0;i<lDjPredial.size();i++){
							   djId=lDjPredial.get(i).getDjId();
							   tipoUso=calculoPredialBo.getUsoPensionista(djId);
							   cantidadUso=calculoPredialBo.getCantidadUsoPensionista(djId);
							 if(tipoUso==Constante.TIPO_USO_COCHERA_DOMICILIO || tipoUso==Constante.USO_PREDIO_CASA_HABITACION && cantidadUso==1){
								 			flagpredio=2;
								 			flagpredios=flagpredios+flagpredio;
											   if (tipoUso==Constante.USO_PREDIO_CASA_HABITACION){
												   contador++;
											   }
											   else if(tipoUso==Constante.TIPO_USO_COCHERA_DOMICILIO){
												   contadorC++;
											   }
							}else{
											flagpredio=0;
											flagpredios=flagpredios+flagpredio;
								}
							}
							if(flagpredios>2 && contador==1 && contadorC==1){
								esPensionistaInafecto = Boolean.TRUE;
							}
							else{
								esPensionistaInafecto = Boolean.FALSE;
							}
						}else{
							esPensionistaInafecto = Boolean.FALSE;
						}
					//--FIN--Se agregó las líneas de código en caso sea Cochera Independizada(Solo Casa Habitación y Cochera de Habitación)::               // 27.03.2015-(Caso Cod.25930 TEJADA FERNANDEZ)
				} else {
					// El uso parcial del inmueble con fines productivos, comerciales y/o
					// profesionales, con aprobaci�n de la Municipalidad respectiva, no afecta la
					// deducci�n que establece este art�culo
					/**
					 * Se corrige la inafectacion de pensionistas ya no se discrimina por el tipo de
					 * uso que posee la inafectacion
					 */
					// List<RpDjuso> lUsos=
					// getCalculoPredialBo().getRpDjusoByDjId(lDjPredial.get(0).getDjId());
					// Iterator<RpDjuso> it = lUsos.iterator();
					// while(it.hasNext()){
					// //Se corrige la inafectacion de pensionistas
					// /*RpDjuso obj = (RpDjuso)it.next();
					// if(obj.getTipoUsoId()==Constante.USO_PREDIO_CASA_HABITACION||
					// obj.getTipoUsoId()==Constante.USO_PREDIO_CASA_HABITACION_DIPLOMATICA||
					// obj.getTipoUsoId()==Constante.USO_PREDIO_COMERCIO||
					// obj.getTipoUsoId()==Constante.USO_PREDIO_TIENDA_BODEGA){
					// esPensionistaInafecto=Boolean.TRUE;
					// }else{
					// esPensionistaInafecto=Boolean.FALSE;
					// }*/
					//
					// }
					esPensionistaInafecto = Boolean.TRUE;
				}
			}
//*** AQUI empieza RECORRIDO DE LA LISTA DE PREDIOS	***					
			for(int i=0;i<lDjPredial.size();i++){
				RpDjpredial rpDjPredial=lDjPredial.get(i);
				//Calculo : Valor del terreno
				Double dblValorTerreno=obtenerValorTerreno(rpDjPredial,anio);
				if(dblValorTerreno==0){
					if(rpDjPredial.getTipoPredio().equals(Constante.TIPO_PREDIO_URBANO)){
						msg="El valor del Terreno del Predio Id = "+rpDjPredial.getPredioId()+" es cero, Verificar el Arancel correspondiente al predio urbano para el Año "+anio+".";	
					}else{
						msg="el valor del Terreno del Predio Id = "+rpDjPredial.getPredioId()+"  es cero, Verificar el Arancel correspondiente al predio rustico para el Año "+anio+".";
					}
				}

				//Guardamos de forma temporal la determinacion del predio
				DtDeterminacionPredio deterPredialt= new DtDeterminacionPredio();
				deterPredialt.setDeterPredioId(Constante.RESULT_PENDING);
				deterPredialt.setDeterminacionId(determinacion.getDeterminacionId());
				
				deterPredialt.setEstado(Constante.ESTADO_INACTIVO);
				//cc: deterPredialt.setFechaRegistro(DateUtil.getCurrentDate());
				//cc: deterPredialt.setTerminal(Constante.TERMINAL);
				//cc: deterPredialt.setUsuarioId(Constante.USUARIO_ID);
				Integer deterPredioId=getCalculoPredialBo().guardarDeterminacionPredial(deterPredialt);
				
				//Calculo : Valor de Construccion
				Double dblValorConstruccion=obtenerValorConstruccion(rpDjPredial,determinacion,deterPredioId,anio);
				dblValorConstruccionAnioFiscal = obtenerValorConstruccionConAnioFiscal(rpDjPredial,determinacion,deterPredioId,anio); 
				
				//Calculo : Valor de Otras instalaciones
				Double dblValorOtrasInstalaciones=obtenerValorOtrasInstalaciones(rpDjPredial,determinacion.getDeterminacionId(),deterPredioId,anio);
				dblValorOtrasInstalacionesAnioFiscal = obtenerValorOtrasInstalacionesConAnioFiscal(rpDjPredial, determinacion.getDeterminacionId(), deterPredioId, anio);
				
				dblValorTerreno=(new BigDecimal(dblValorTerreno)).setScale(2, RoundingMode.HALF_UP).doubleValue();
				dblValorConstruccion=(new BigDecimal(dblValorConstruccion)).setScale(2, RoundingMode.HALF_UP).doubleValue();
				dblValorOtrasInstalaciones=(new BigDecimal(dblValorOtrasInstalaciones)).setScale(2, RoundingMode.HALF_UP).doubleValue();
				
				//Calculo Valor Predio
				//Valor Predio =Valor Terreno + Valor de Construcci�n + Valor otras instalaciones.
				Double dblValorPredio = dblValorTerreno + dblValorConstruccion + dblValorOtrasInstalaciones;
				dblValorPredio=(new BigDecimal(dblValorPredio)).setScale(2, RoundingMode.HALF_UP).doubleValue();
				
				/** CONSIDERANDO ANIO FISCAL PARA ALCALABA */
				Double dblValorPredioConAnioFiscal = dblValorTerreno + dblValorConstruccionAnioFiscal + dblValorOtrasInstalacionesAnioFiscal;
				dblValorPredioConAnioFiscal=(new BigDecimal(dblValorPredioConAnioFiscal)).setScale(2, RoundingMode.HALF_UP).doubleValue();
				//--
				
				//Calculo de la Base Imponible segun el % de propiedad
				Double dblPorcPropiedad=rpDjPredial.getPorcPropiedad().doubleValue();
				Double dblBaseImponible = dblValorPredio * dblPorcPropiedad / 100;
				dblBaseImponible=(new BigDecimal(dblBaseImponible)).setScale(2, RoundingMode.HALF_UP).doubleValue();
				
				/** CONSIDERANDO ANIO FISCAL PARA ALCALABA */
				Double dblValorAutovaluoConAnioFiscal = dblValorPredioConAnioFiscal * dblPorcPropiedad / 100;
				dblValorAutovaluoConAnioFiscal=(new BigDecimal(dblValorAutovaluoConAnioFiscal)).setScale(2, RoundingMode.HALF_UP).doubleValue();
				//--
				
				
				if (rpDjPredial.getTipoPredio().equals(Constante.TIPO_PREDIO_RUSTICO)) {
					if(rpDjPredial.getTipoUsoRusticoId().intValue()==Constante.TIPO_USO_PREDIO_RUSTICO_AGRICOLA){
						//para el caso de los predios "rusticos" que tengan uso "agricola", tendr�n una deducci�n de la base imponible del  del 50%  (articulo 18 iniciso a, del TUO de la Ley de Tributaci�n Municipal).
						dblBaseImponible=dblBaseImponible*(0.5);
					}
					//PARA SECANO articulo III C.25 del reglamento nacional de tasaciones (se deja sin efecto por acuerdo de Gerencia de Operaciones)
					if( rpDjPredial.getSecano() != null &&  rpDjPredial.getSecano().equals(Constante.ESTADO_ACTIVO) &&   (rpDjPredial.getTipoTierraId().intValue()!=Constante.TIERRAS_APTAS_PARA_CULTIVO_EN_LIMPIO_CON_RIEGO || 
							rpDjPredial.getTipoTierraId().intValue()==Constante.TIERRAS_APTAS_PARA_CULTIVO_PARA_PASTOREO_CON_RIEGO ||
							rpDjPredial.getTipoTierraId().intValue()==Constante.TIERRAS_APTAS_PARA_CULTIVO_PERMANENTE_CON_RIEGO)){
						//articulo III C.25 del reglamento nacional de tasaciones
						dblBaseImponible=dblBaseImponible*(0.5);
						
					}
				}
				
				Double dblBaseExonerada=Double.valueOf(0);
				Double dblBaseAfecta=Double.valueOf(0);
				Double dblValorAfectacion=Double.valueOf(0);
				String strTipoAfectacion=Constante.TIPO_INFECTACION_PORCENTAJE;
				Boolean esInafecto=Boolean.FALSE;
				
				//-------------------------------------
				//Solo si posee condicion especial
				if(condicionEspContribuyente!=null){
					if(condicionEspContribuyente.getTipoCondEspecialId()==Constante.CONDICION_ESPECIAL_CONTRIBUYENTE_PENSIONISTA){
						if(esPensionistaInafecto){
							DtCondiEspecialContri inafectacion=getCalculoPredialBo().getDtCondiEspecialContriByPeriodo(anio, condicionEspContribuyente.getTipoCondEspecialId(),99);
							
							if(inafectacion!=null){
								dblValorAfectacion=inafectacion.getValor().doubleValue();
								strTipoAfectacion=inafectacion.getTipo();
								esInafecto=Boolean.TRUE;
							}else{
								//ERROR debiera existir valor y tipo de inafectacion en el periodo
								throw new SisatException("No existe un valor y tipo de inafectación en el periodo "+ anio + "en la tabla dt_condi_especial_contri");
							}
						}else{
							esInafecto=Boolean.FALSE;
						}
					}
					
					if(condicionEspContribuyente.getTipoCondEspecialId()!=Constante.CONDICION_ESPECIAL_CONTRIBUYENTE_PENSIONISTA){
						DtCondiEspecialContri inafectacion=getCalculoPredialBo().getDtCondiEspecialContriByPeriodo(anio, condicionEspContribuyente.getTipoCondEspecialId(),99);
						if(inafectacion!=null){
							//Inafectacion total independientemente de los usos que pueda tener
							dblValorAfectacion=inafectacion.getValor().doubleValue();
							strTipoAfectacion=inafectacion.getTipo();
							//esInafecto=Boolean.TRUE;
							
							//**CONDICION ESPECIAL PREDIO **********************
							if(rpDjPredial.getCondEspePredioId() != null &&  rpDjPredial.getCondEspePredioId() == Constante.PREDIO_AFECTO_IMPUESTO_PREDIAL){
								esInafecto=Boolean.FALSE;
							}else{
								esInafecto=Boolean.TRUE;
							}							
							//************************
						}else{
							//inafectacion solo si el uso especificado se encuentra en la tabla dt_condi_especial_contri
							List<RpDjuso> lUsos= getCalculoPredialBo().getRpDjusoByDjId(rpDjPredial.getDjId());
							Iterator<RpDjuso> it = lUsos.iterator();
							while(it.hasNext()){
								RpDjuso uso = (RpDjuso)it.next();
								inafectacion=getCalculoPredialBo().getDtCondiEspecialContriByPeriodo(anio, condicionEspContribuyente.getTipoCondEspecialId(),uso.getTipoUsoId());
								if(inafectacion!=null){
									dblValorAfectacion=inafectacion.getValor().doubleValue();
									strTipoAfectacion=inafectacion.getTipo();
									esInafecto=Boolean.TRUE;
								}else{
									//Pierde toda su condicion de inafectacion, posee un uso no contemplado en la tabla 
									esInafecto=Boolean.FALSE;
								}
							}
						}
					}
				}
				//-------------------------------------
				dblValorAfectacion=(new BigDecimal(dblValorAfectacion)).setScale(2, RoundingMode.HALF_UP).doubleValue();
				
				/**
				 * calculo de un porcentaje de la base imponible::inicio
				 */
					Double dblBaseImponibleInafectar=new Double(0);
					Double dblBaseImponibleAfectar=new Double(0);
										
					dblBaseImponibleAfectar=dblBaseImponible;					
										
					if(esInafecto){
						
						Double porcentaje = condicionEspContribuyente.getPorcentaje().doubleValue();
						
						if(porcentaje == null){
							porcentaje = new Double(100);						
						}
						
						dblBaseImponibleInafectar=dblBaseImponible*(porcentaje/100);
						dblBaseImponibleAfectar=dblBaseImponible*(1 - porcentaje/100);
						
						if(strTipoAfectacion.equals(Constante.TIPO_INFECTACION_PORCENTAJE)){
							//Se calcula en base al % de la Base Imponible
							//dblBaseExonerada = dblBaseImponible * (dblValorAfectacion / 100);
							dblBaseExonerada = dblBaseImponibleInafectar * (dblValorAfectacion / 100);
						}else{
							//Se calcula en base al monto maximo//Jubilado
							dblBaseExonerada = dblValorAfectacion;
						}
					}
					dblBaseExonerada=(new BigDecimal(dblBaseExonerada)).setScale(2, RoundingMode.HALF_UP).doubleValue();
					dblBaseImponibleInafectar=(new BigDecimal(dblBaseImponibleInafectar)).setScale(2, RoundingMode.HALF_UP).doubleValue();
					
					
					/*
					 if(dblBaseExonerada > dblBaseImponible){
						dblBaseAfecta = Double.valueOf(0);
					    dblBaseExonerada = dblBaseImponible;
					}else{
						dblBaseAfecta = dblBaseImponible - dblBaseExonerada;
					}
					 */
					
					Double dblBaseAfecta1=new Double(0);
					Double dblBaseAfecta2=new Double(0);
					
					//Base Imponible Final = Base Imponible - Base Exonerada
					if(dblBaseExonerada > dblBaseImponibleInafectar){
						dblBaseAfecta1 = Double.valueOf(0);
					    dblBaseExonerada = dblBaseImponibleInafectar;
					}else{
						dblBaseAfecta1 = dblBaseImponibleInafectar - dblBaseExonerada;
					}
										
					dblBaseAfecta2 = dblBaseImponibleAfectar;
					
					dblBaseAfecta = dblBaseAfecta1 + dblBaseAfecta2;
				
				/**
				 * calculo de un porcentaje de la base imponible::fin
				 */
				
				dblBaseAfecta=(new BigDecimal(dblBaseAfecta)).setScale(2, RoundingMode.HALF_UP).doubleValue();
	        	
	        	Calendar calFechaAdqui=Calendar.getInstance();
	        	calFechaAdqui.setTime(rpDjPredial.getFechaAdquisicion());
	        	Integer intAnnoAdquisicion=calFechaAdqui.get(Calendar.YEAR);
	        	
	        	Integer intAnnoAfectoPredio = intAnnoAdquisicion + 1;
	        	if(intAnnoAfectoPredio>anio){
	        		//Afecto al anio siguiente
	        		dblBaseExonerada = dblBaseImponible;
	        	    dblBaseAfecta = Double.valueOf(0);
	        	}
				
				Double valorArancel=obtenerValorArancel(rpDjPredial,anio);
				
				DtDeterminacionPredio deterPredial= new DtDeterminacionPredio();
				deterPredial.setDeterPredioId(deterPredioId);
				deterPredial.setDeterminacionId(determinacion.getDeterminacionId());
				deterPredial.setArancel((new BigDecimal(valorArancel)).setScale(2, RoundingMode.HALF_UP));
				if (rpDjPredial.getTipoPredio().equals(Constante.TIPO_PREDIO_URBANO)) {
					if (rpDjPredial.getAreaTerreno() != null)
						deterPredial.setAreaTerreno(rpDjPredial.getAreaTerreno().setScale(2, RoundingMode.HALF_UP));
				} else {
					if (rpDjPredial.getAreaTerrenoHas() != null)
						deterPredial.setAreaTerreno(rpDjPredial.getAreaTerrenoHas().setScale(2, RoundingMode.HALF_UP));
				}
				
				deterPredial.setBaseAfecta((new BigDecimal(dblBaseAfecta)).setScale(2, RoundingMode.HALF_UP));
				deterPredial.setBaseExonerada((new BigDecimal(dblBaseExonerada)).setScale(2, RoundingMode.HALF_UP));
				deterPredial.setBaseImponible((new BigDecimal(dblBaseImponible)).setScale(2, RoundingMode.HALF_UP));
				deterPredial.setDjId(rpDjPredial.getDjId());
				deterPredial.setFlagInafectacion(esInafecto?"S":"N");
				
				deterPredial.setPorcPropiedad(rpDjPredial.getPorcPropiedad());
				deterPredial.setPredioId(rpDjPredial.getPredioId());
				deterPredial.setTipoInafectacion(strTipoAfectacion);
				deterPredial.setValorConstruccion((new BigDecimal(dblValorConstruccion)).setScale(2, RoundingMode.HALF_UP));
				deterPredial.setValorImpuesto(new BigDecimal(0));
				deterPredial.setValorInafectacion((new BigDecimal(dblValorAfectacion)).setScale(2, RoundingMode.HALF_UP));
				deterPredial.setValorInstalacion((new BigDecimal(dblValorOtrasInstalaciones)).setScale(2, RoundingMode.HALF_UP));
				deterPredial.setValorPredio((new BigDecimal(dblValorPredio)).setScale(2, RoundingMode.HALF_UP));
				deterPredial.setValorTerreno((new BigDecimal(dblValorTerreno)).setScale(2, RoundingMode.HALF_UP));
				Date datFechaAdquiscion=rpDjPredial.getFechaAdquisicion();
				deterPredial.setFechaAdquisicion(DateUtil.dateToSqlTimestamp(datFechaAdquiscion));
				
				//PARA ALCABALA dblValorAutovaluoConAnioFiscal
				deterPredial.setValorAutovaluoAlcabala((new BigDecimal(dblValorAutovaluoConAnioFiscal)).setScale(2, RoundingMode.HALF_UP));
				
				deterPredial.setEstado(Constante.ESTADO_ACTIVO);
				//cc: deterPredial.setFechaRegistro(DateUtil.getCurrentDate());
				//cc: deterPredial.setTerminal(Constante.TERMINAL);
				//cc: deterPredial.setUsuarioId(Constante.USUARIO_ID);
				getCalculoPredialBo().guardarDeterminacionPredial(deterPredial);
			}//for(int i=0;i<lDjPredial.size();i++){
//*** AQUI TERMINA RECORRIDO DE LA LISTA DE PREDIOS	***	
			
//*** ........ AQUI empieza RECORRIDO DE LA LISTA DE PREDIOS en DT_DERMINACION_PREDIO	.........***				
			ArrayList<DtDeterminacionPredio> lDetPredio = getCalculoPredialBo().getAllDtDeterminacionPredio(determinacion.getDeterminacionId());
			Iterator<DtDeterminacionPredio> it = lDetPredio.iterator();  
			 
			Double dblBaseImponibleTotal= Double.valueOf(0);
		    Double dblBaseExoneradaTotal= Double.valueOf(0);
		    Double dblBaseAfectaTotal= Double.valueOf(0);
		            
		    //Buscamos la Base Imponible de los predios
	        while (it.hasNext()){
	        	DtDeterminacionPredio deterPredio= it.next();
	        	Date datFechaAdqui=new Date(deterPredio.getFechaAdquisicion().getTime());
	        	Calendar calFechaAdqui=Calendar.getInstance();
	        	calFechaAdqui.setTime(datFechaAdqui);
	        	Integer intAnnoAfectoPredio=calFechaAdqui.get(Calendar.YEAR)+1;
	        	if(intAnnoAfectoPredio<=anio){
	        		//SOLO SE CONSIDERA LOS AFECTOS HASTA EL Anio ACTUAL
	        		Double dblBaseAfecta=deterPredio.getBaseAfecta().doubleValue();
	        		Double dblBaseExonerada=deterPredio.getBaseExonerada().doubleValue();
	        		Double dblBaseImponible=deterPredio.getBaseImponible().doubleValue();
	        		
	        		dblBaseAfectaTotal += dblBaseAfecta;
	                dblBaseExoneradaTotal += dblBaseExonerada;
	                dblBaseImponibleTotal += dblBaseImponible;
	        	}
	        }
	        
	        //Calculamos el Valor del Impuesto.
	        Double cdblImpuestoTotal=Double.valueOf(0);
	        if(dblBaseAfectaTotal==0){
	        	cdblImpuestoTotal = Double.valueOf(0);
	        }else{
	        	cdblImpuestoTotal=calcularImpuesto(dblBaseAfectaTotal,anio);
	        	//Comparamos con el monto minimo
	        	Double cdblMontoMinimoImpuesto=getMontoMinimoImpuesto(anio);
	        	if(cdblImpuestoTotal < cdblMontoMinimoImpuesto){
	        		cdblImpuestoTotal = cdblMontoMinimoImpuesto;	
	        	}
	        }
			
	        //guardar determinacion
	        determinacion.setAnnoDeterminacion(anio);
	        determinacion.setBaseAfecta(BigDecimal.valueOf(dblBaseAfectaTotal).setScale(2, RoundingMode.HALF_UP));
	        	//determinacion.setBaseAfectaFisca(BigDecimal.valueOf(0));
	        determinacion.setBaseExonerada(BigDecimal.valueOf(dblBaseExoneradaTotal).setScale(2, RoundingMode.HALF_UP));
	        	//determinacion.setBaseExoneradaFisca(BigDecimal.valueOf(0));
	        determinacion.setBaseImponible(BigDecimal.valueOf(dblBaseImponibleTotal).setScale(2, RoundingMode.HALF_UP));
	        	//determinacion.setBaseImponibleFisca(BigDecimal.valueOf(0));
	        determinacion.setConceptoId(Constante.CONCEPTO_PREDIAL);
	        determinacion.setSubconceptoId(Constante.SUBCONCEPTO_PREDIAL);
	        determinacion.setImpuesto(BigDecimal.valueOf(cdblImpuestoTotal).setScale(2, RoundingMode.HALF_UP));
	        	//determinacion.setImpuestoFisca(BigDecimal.valueOf(0));
	        //--
	        //Calculamos la cantidad de cuotas correspondientes
	        DtCuotaConcepto cuotas = getCalculoPredialBo().getCuotasConcepto(Constante.SUB_CONCEPTO_PREDIAL, determinacion.getAnnoDeterminacion());
	        if(cuotas == null){
	        	throw new SisatException("No existen cuotas cargadas para el año ".concat(determinacion.getAnnoDeterminacion().toString()).concat(" en la tabla dt_cuota_concepto"));
	        }
	        	
	        Integer numeroCuotas=1;
	        if(dblBaseAfectaTotal>0&&cuotas!=null){
	        	numeroCuotas=cuotas.getNroCuotas();
	        }
	        //--
	        determinacion.setNroCuotas(numeroCuotas);
	        determinacion.setNroDocumento(null);
	        determinacion.setPersonaId(personaId);
	        determinacion.setPorcPropiedad(BigDecimal.valueOf(0));
	        if(determinacionBase!=null){
	        	determinacion.setIdAnterior(determinacionBase.getDeterminacionId());
	        	determinacion.setBaseAfectaAnterior(determinacionBase.getBaseAfecta());
	        	determinacion.setBaseExoneradaAnterior(determinacionBase.getBaseExonerada());
	        	determinacion.setBaseImponibleAnterior(determinacionBase.getBaseImponible());
	        	determinacion.setImpuestoAnterior(determinacionBase.getImpuesto());
	        }else{
	        	determinacion.setBaseAfectaAnterior(BigDecimal.valueOf(0));
	        	determinacion.setBaseExoneradaAnterior(BigDecimal.valueOf(0));
	        	determinacion.setBaseImponibleAnterior(BigDecimal.valueOf(0));
	        	determinacion.setImpuestoAnterior(BigDecimal.valueOf(0));
	        }
	        
	        BigDecimal impDif = determinacion.getImpuesto().subtract(determinacion.getImpuestoAnterior());
			impDif = impDif.setScale(2, RoundingMode.HALF_UP);
			determinacion.setImpuestoDiferencia(impDif);
	        
	        String nro_dj=generalBo.ObtenerCorrelativoDocumento("rp_djpredial", "nro_dj");
        	determinacion.setDjreferenciaId(Util.toInteger(nro_dj));
        	//Calculo del estado de la Determinacion : INICIO
        	//EstadoDeterminacionPredial estadoDeterminacion=new EstadoDeterminacionPredial();
        	//int estadoFisca=estadoDeterminacion.estadoDtDeterminacion(lDjPredial);//Se obtiene el estadoFisca de la determinacion al inicio de la funcion
        	if(estadoFisca==EstadoDeterminacionPredial.ACEPTADO_CERRADO){
        		determinacion.setFiscalizado(Constante.FISCALIZADO_SI);
        		determinacion.setFiscaAceptada(Constante.FISCA_ACEPTADA_SI);
        		determinacion.setFiscaCerrada(Constante.FISCA_CERRADA_SI);
        		determinacion.setEstado(Constante.ESTADO_ACTIVO);
        	}else if(estadoFisca==EstadoDeterminacionPredial.NO_ACEPTADO_NO_CERRADO){
        		determinacion.setFiscalizado(Constante.FISCALIZADO_SI);
        		determinacion.setFiscaAceptada(Constante.FISCA_ACEPTADA_NO);
        		determinacion.setFiscaCerrada(Constante.FISCA_CERRADA_NO);
        		determinacion.setEstado(Constante.ESTADO_PENDIENTE);
        	}else if(estadoFisca==EstadoDeterminacionPredial.NO_ACEPTADO_CERRADO){
        		determinacion.setFiscalizado(Constante.FISCALIZADO_SI);
        		determinacion.setFiscaAceptada(Constante.FISCA_ACEPTADA_NO);
        		determinacion.setFiscaCerrada(Constante.FISCA_CERRADA_SI);
        		determinacion.setEstado(Constante.ESTADO_ACTIVO);
        	}else{
        		determinacion.setFiscalizado(Constante.FISCALIZADO_NO);
        		determinacion.setFiscaAceptada(Constante.FISCA_ACEPTADA_NO);
        		determinacion.setFiscaCerrada(Constante.FISCA_CERRADA_NO);
        		determinacion.setEstado(Constante.ESTADO_ACTIVO);
        	}
        	//
        	
        	//Calculo del estado de la Determinacion : FIN
	        Id=getCalculoPredialBo().guardarDeterminacion(determinacion);
	        
	        //Para verificar nuevamente si esta asociado a un valor y no desactive la determinacion base con valor.
	        List<DeudaValoresDTO> lstDeuValor = getCalculoPredialBo().getDeudaValores(personaId,Constante.CONCEPTO_PREDIAL,anio);

		        //Siempre solo va ha ver una determinacion activa
		        //La nueva determinacion inactiva la anterior
		        if(determinacion.getEstado().equals(Constante.ESTADO_ACTIVO)&&determinacionBase!=null){
		        	boolean tieneValor = false;
					for (DeudaValoresDTO dv : lstDeuValor) {
						if (dv.getFlagOp()!= null&&Util.toInteger(dv.getFlagOp())>0) {
							tieneValor = true;
							break;
						}
					}
					if(tieneValor==false){
			        	determinacionBase.setFechaActualizacion(DateUtil.getCurrentDate());
			        	determinacionBase.setEstado(Constante.ESTADO_INACTIVO);
			        	Id=getCalculoPredialBo().guardarDeterminacion(determinacionBase);
			        	//Determinacion Predio
			    		getCalculoPredialBo().actualizaEstadoDtDeterminacionPredio(determinacionBase.getDeterminacionId(), Constante.ESTADO_INACTIVO);
			    		//Determinacion Construccion
			    		getCalculoPredialBo().actualizaEstadoDtDeterminacionConstruccion(determinacionBase.getDeterminacionId(), Constante.ESTADO_INACTIVO);
			    		DtDeterminacion deterPendienteAnt=getCalculoPredialBo().getDtDeterminacionAnt(personaId,anio,Constante.CONCEPTO_PREDIAL,Constante.ESTADO_PENDIENTE,determinacion.getDeterminacionId());
			    		
			    		if(deterPendienteAnt!=null){
			        		deterPendienteAnt.setFechaActualizacion(DateUtil.getCurrentDate());
				        	deterPendienteAnt.setEstado(Constante.ESTADO_INACTIVO);
				        	Id=getCalculoPredialBo().guardarDeterminacion(deterPendienteAnt);
				        	//Determinacion Predio
				    		getCalculoPredialBo().actualizaEstadoDtDeterminacionPredio(deterPendienteAnt.getDeterminacionId(), Constante.ESTADO_INACTIVO);
				    		//Determinacion Construccion
				    		getCalculoPredialBo().actualizaEstadoDtDeterminacionConstruccion(deterPendienteAnt.getDeterminacionId(), Constante.ESTADO_INACTIVO);	
			        	}
						
					}//tieneValor==false::fin
		        }
	        
	        if(determinacion.getEstado().equals(Constante.ESTADO_PENDIENTE)){
	        	DtDeterminacion deterPendienteAnt=getCalculoPredialBo().getDtDeterminacionAnt(personaId,anio,Constante.CONCEPTO_PREDIAL,Constante.ESTADO_PENDIENTE,determinacion.getDeterminacionId());
	        	if(deterPendienteAnt!=null){
	        		deterPendienteAnt.setFechaActualizacion(DateUtil.getCurrentDate());
		        	deterPendienteAnt.setEstado(Constante.ESTADO_INACTIVO);
		        	Id=getCalculoPredialBo().guardarDeterminacion(deterPendienteAnt);
		        	//Determinacion Predio
		    		getCalculoPredialBo().actualizaEstadoDtDeterminacionPredio(deterPendienteAnt.getDeterminacionId(), Constante.ESTADO_INACTIVO);
		    		//Determinacion Construccion
		    		getCalculoPredialBo().actualizaEstadoDtDeterminacionConstruccion(deterPendienteAnt.getDeterminacionId(), Constante.ESTADO_INACTIVO);	
	        	}
	        }
	        
	        if(determinacion.getFiscalizado()!=null&&determinacion.getFiscalizado().equals(Constante.FISCALIZADO_SI)&&determinacion.getFiscaCerrada().equals(Constante.FISCA_CERRADA_NO)){
	        	msg="[NO ACEPTADO - NO CERRADO] No genera deuda";	
	        }else{
	        	if(condicionEspContribuyente != null ){
	        		msg=generarDeuda(determinacion, determinacionBase, cuotas, condicionEspContribuyente.getTipoCondEspecialId());
	        	}else{
	        		msg=generarDeuda(determinacion, determinacionBase, cuotas, 0);
	        	}
	        }
		}
		return msg;
	}
	
	/**
	 * 	ACEPTADO CERRADO			NO TIENE VALOR	NO TIENE PAGO	REEMPLAZA	
		RECTIFICACION VOLUNTARIA					SI TIENE PAGO	REEMPLAZA	IMPUTA
					
									SI TIENE VALOR	*				GENERA CTA X LA DIFERENCIA	
					
		NO ACEPTADO Y CERRADO		*				*				GENERA CTA X LA DIFERENCIA	
	 * @param determinacion
	 * @param determinacionAnt
	 * @return
	 * @throws Exception
	 */
	public String generarDeuda(DtDeterminacion determinacion,DtDeterminacion determinacionBase,DtCuotaConcepto cuotas,int  tipoCondicionEspecial)throws Exception{
		String msg="";
		
		//Cod. anterior:
		if(determinacion.getFiscalizado().equals(Constante.FISCALIZADO_SI)&&
		   determinacion.getFiscaAceptada().equals(Constante.FISCA_ACEPTADA_NO)&&
		   determinacion.getFiscaCerrada().equals(Constante.FISCA_CERRADA_SI)){
							//	if(determinacion.getFiscalizado().equals(Constante.FISCALIZADO_SI) && determinacion.getFiscaCerrada().equals(Constante.FISCA_CERRADA_SI)){

				//Verificando la(s) deuda(s) con 'flag_op':
				List<DeudaValoresDTO> lstDeuVal = getCalculoPredialBo().getDeudaValores(determinacion.getPersonaId(),Constante.CONCEPTO_PREDIAL,determinacion.getAnnoDeterminacion());
					
				/**
				 * Verificando si alguna de la deudas en la "Determinacion Base" tiene valores.::11-12-2014::
				 * Dependiendo si mantiene el monto del impuesto o si existe una variación::
				 */
				
				boolean tieneVal = false;
				
				for (DeudaValoresDTO dv : lstDeuVal) {
					if (dv.getFlagOp()!= null&&Util.toInteger(dv.getFlagOp())>0) {
						tieneVal = true;
						break;
					}
				}
				
			if(tieneVal){//Si tiene valor.
					 //Obtenemos el monto de las Determinaciones con Valor.
						DatosDeterminacionPredialValoresDTO deterValor=getCalculoPredialBo().getMontoDeterminacionValor(determinacionBase.getPersonaId(),Constante.CONCEPTO_PREDIAL,determinacionBase.getAnnoDeterminacion());
						
					 //Generamos la diferencia entre el impuesto nuevo generado y el monto total de las determinaciones con valor.
						BigDecimal impDiff = determinacion.getImpuesto().subtract(deterValor.getImpuesto());
						impDiff = impDiff.setScale(2, RoundingMode.HALF_UP);
					    
					 //Actualizando la diferencia de la nueva determinacion.
						getCalculoPredialBo().actualizarDiffDeterminacion(determinacion.getDeterminacionId(), impDiff);

					 //Verificando si la Determinacion Previa sin valor tiene pagos://?for
						List<DatosDeterminacionPredialValoresDTO> dtsinValor=getCalculoPredialBo().getDeterminacionValores(determinacionBase.getPersonaId(),Constante.CONCEPTO_PREDIAL,determinacionBase.getAnnoDeterminacion(),determinacion.getDeterminacionId());
						  if(dtsinValor.equals(null)|| dtsinValor.isEmpty()){
							 //***
						  }else if(dtsinValor.get(0).getDeterminacionId()!=0 || dtsinValor.size()!=0 ){
							   deuSinValorPagPrevio=getCalculoPredialBo().getDeudaValoresPagos(determinacionBase.getPersonaId(),determinacionBase.getAnnoDeterminacion(),dtsinValor.get(0).getDeterminacionId());
						  }
					//Si tiene valor,y la B.I. nueva es mayor que el total de la B.I. con valores,genera la deuda por la diferencia.
						if(impDiff.doubleValue() > 0){//if (determinacion.getImpuestoDiferencia().doubleValue() > 0) {
						 if (deuSinValorPagPrevio==null||deuSinValorPagPrevio.equals(null)||deuSinValorPagPrevio.equals("")||deuSinValorPagPrevio.getTotalCancelado().doubleValue() == 0 || deuSinValorPagPrevio.getTotalCancelado()== null) {// No existen Pagos
							 
							 getCalculoPredialBo().actualizarFlagVariacionDeterminacion(determinacion.getDeterminacionId(), Constante.ESTADO_ACTIVO);
							
							 //Cod.anterior: //guardarDeuda(determinacion,determinacion.getImpuestoDiferencia(),cuotas,Boolean.FALSE);
							 guardarDeuda(determinacion,impDiff,cuotas,Boolean.FALSE);
										 
							 //Si existe una Determinacion sin valor en estado=1,se inactivará,al igual que la deuda y las demás tablas asociadas.
							 	List<DatosDeterminacionPredialValoresDTO> lDtsinValor=getCalculoPredialBo().getDeterminacionValores(determinacionBase.getPersonaId(),Constante.CONCEPTO_PREDIAL,determinacionBase.getAnnoDeterminacion(),determinacion.getDeterminacionId());
							    //DatosDeterminacionPredialValoresDTO dt=getCalculoPredialBo().getDeterminacionValores(determinacionBase.getPersonaId(),Constante.CONCEPTO_PREDIAL,determinacionBase.getAnnoDeterminacion());
								
							 		if(lDtsinValor.equals(null)|| dtsinValor.isEmpty()){
									 //***
							 		}else if (lDtsinValor.get(0).getDeterminacionId()!=0){
										 for(int i=0;i<lDtsinValor.size();i++){
												getCalculoPredialBo().actualizarEstadoDeterminacion(lDtsinValor.get(0).getDeterminacionId(), Constante.ESTADO_INACTIVO);
												getCalculoPredialBo().actualizaEstadoCdDeuda(lDtsinValor.get(0).getDeterminacionId(), Constante.ESTADO_INACTIVO);
									        	getCalculoPredialBo().actualizaEstadoDtDeterminacionPredio(lDtsinValor.get(0).getDeterminacionId(), Constante.ESTADO_INACTIVO);
									    		getCalculoPredialBo().actualizaEstadoDtDeterminacionConstruccion(lDtsinValor.get(0).getDeterminacionId(), Constante.ESTADO_INACTIVO);
										 }
									}
								
							//mayor:
								//Insertamos en la tabla dt_determinacion_valor,para registrar la variación en ambas determinaciones.
		    					DtDeterminacionValor variacion=new DtDeterminacionValor();
		        					variacion.setDeterminacionId(determinacionBase.getDeterminacionId());
		        					variacion.setAnnoDeterminacion(determinacionBase.getAnnoDeterminacion());
		        					variacion.setPersonaId(determinacionBase.getPersonaId());
		        					variacion.setConceptoId(determinacionBase.getConceptoId());
		        					variacion.setSubconceptoId(determinacionBase.getSubconceptoId());
		        					variacion.setBaseImponible(determinacionBase.getBaseImponible());
		        					variacion.setImpuesto(determinacionBase.getImpuesto());
		        					variacion.setFechaRegistroDt(determinacionBase.getFechaRegistro());
		        					
		        					variacion.setDeterminacionIdVariacion(determinacion.getDeterminacionId());
		        					variacion.setAnnoDeterminacionVariacion(determinacion.getAnnoDeterminacion());
		        					variacion.setPersonaIdVariacion(determinacion.getPersonaId());
		        					variacion.setConceptoIdVariacion(determinacion.getConceptoId());
		        					variacion.setSubconceptoIdVariacion(determinacion.getSubconceptoId());
		        					variacion.setBaseImponibleVariacion(determinacion.getBaseImponible());
		        					variacion.setImpuestoVariacion(determinacion.getImpuesto());
		        					variacion.setFechaRegistroDtVariacion(determinacion.getFechaRegistro());
		        					variacion.setFlagAumento(Constante.ESTADO_ACTIVO);
		        					variacion.setDiffImpuesto(impDiff);
		        					getCalculoPredialBo().guardarDeterminacionVariacion(variacion);
		        					
		        					msg="Tiene valores se genera la deuda por la diferencia "+impDiff+", mantiene la deuda anterior activa";
							}else{
									getCalculoPredialBo().actualizarFlagVariacionDeterminacion(determinacion.getDeterminacionId(), Constante.ESTADO_ACTIVO);
	     							guardarDeuda(determinacion,impDiff,cuotas,Boolean.FALSE);

								//Si existe una Determinacion sin valor en estado=1,se inactivará,al igual que la deuda y las demás tablas asociadas.
								 	List<DatosDeterminacionPredialValoresDTO> lDtsinValor=getCalculoPredialBo().getDeterminacionValores(determinacionBase.getPersonaId(),Constante.CONCEPTO_PREDIAL,determinacionBase.getAnnoDeterminacion(),determinacion.getDeterminacionId());
								 	//? for?
								 if(deuSinValorPagPrevio.getTotalCancelado().doubleValue() > 0){
										calculoPredialBo.inputarDeudaSinValor(lDtsinValor.get(0).getDeterminacionId(), determinacion.getDeterminacionId());
									}

								//mayor:
									//Insertamos en la tabla dt_determinacion_valor,para registrar la variación en ambas determinaciones.
			    					DtDeterminacionValor variacion=new DtDeterminacionValor();
			        					variacion.setDeterminacionId(determinacionBase.getDeterminacionId());
			        					variacion.setAnnoDeterminacion(determinacionBase.getAnnoDeterminacion());
			        					variacion.setPersonaId(determinacionBase.getPersonaId());
			        					variacion.setConceptoId(determinacionBase.getConceptoId());
			        					variacion.setSubconceptoId(determinacionBase.getSubconceptoId());
			        					variacion.setBaseImponible(determinacionBase.getBaseImponible());
			        					variacion.setImpuesto(determinacionBase.getImpuesto());
			        					variacion.setFechaRegistroDt(determinacionBase.getFechaRegistro());
			        					
			        					variacion.setDeterminacionIdVariacion(determinacion.getDeterminacionId());
			        					variacion.setAnnoDeterminacionVariacion(determinacion.getAnnoDeterminacion());
			        					variacion.setPersonaIdVariacion(determinacion.getPersonaId());
			        					variacion.setConceptoIdVariacion(determinacion.getConceptoId());
			        					variacion.setSubconceptoIdVariacion(determinacion.getSubconceptoId());
			        					variacion.setBaseImponibleVariacion(determinacion.getBaseImponible());
			        					variacion.setImpuestoVariacion(determinacion.getImpuesto());
			        					variacion.setFechaRegistroDtVariacion(determinacion.getFechaRegistro());
			        					variacion.setDiffImpuesto(impDiff);
			        					variacion.setFlagAumento(Constante.ESTADO_ACTIVO);
			        					getCalculoPredialBo().guardarDeterminacionVariacion(variacion);
			        					
			        					msg="Tiene valores se genera la deuda por la diferencia "+impDiff+", mantiene la deuda anterior activa y la determinacion previa (sin Valor) posee pagos";
							}
						}else if(impDiff.doubleValue() == 0){//(determinacion.getImpuestoDiferencia().doubleValue() == 0 && determinacionBase.getBaseImponible().compareTo(determinacion.getBaseImponible())==0){
								
							//Se mantiene los estados originales
								getCalculoPredialBo().actualizarEstadoDeterminacion(determinacion.getDeterminacionId(), Constante.ESTADO_INACTIVO);
					        	getCalculoPredialBo().actualizaEstadoDtDeterminacionPredio(determinacion.getDeterminacionId(), Constante.ESTADO_INACTIVO);
					    		getCalculoPredialBo().actualizaEstadoDtDeterminacionConstruccion(determinacion.getDeterminacionId(), Constante.ESTADO_INACTIVO);
			    		
					    		determinacionBase.setFechaActualizacion(DateUtil.getCurrentDate());
					        	determinacionBase.setEstado(Constante.ESTADO_ACTIVO);
					        	getCalculoPredialBo().guardarDeterminacion(determinacionBase);
					        	getCalculoPredialBo().actualizaEstadoDtDeterminacionPredio(determinacionBase.getDeterminacionId(), Constante.ESTADO_ACTIVO);
					    		getCalculoPredialBo().actualizaEstadoDtDeterminacionConstruccion(determinacionBase.getDeterminacionId(), Constante.ESTADO_ACTIVO);
								
					    		msg="Tiene valores,mantiene el monto de la B.I. por lo tanto no genera deuda, mantiene la deuda anterior activa ";
							
						}else if(impDiff.doubleValue() < 0){//(determinacion.getImpuestoDiferencia().doubleValue() < 0 && determinacionBase.getBaseImponible().compareTo(determinacion.getBaseImponible())>0){

								determinacion.setFechaActualizacion(DateUtil.getCurrentDate());
								determinacion.setEstado(Constante.ESTADO_INACTIVO);
					        	getCalculoPredialBo().guardarDeterminacion(determinacion);
					        	getCalculoPredialBo().actualizaEstadoDtDeterminacionPredio(determinacion.getDeterminacionId(), Constante.ESTADO_INACTIVO);
					    		getCalculoPredialBo().actualizaEstadoDtDeterminacionConstruccion(determinacion.getDeterminacionId(), Constante.ESTADO_INACTIVO);
					    		
					    		determinacionBase.setFechaActualizacion(DateUtil.getCurrentDate());
					        	determinacionBase.setEstado(Constante.ESTADO_ACTIVO);
					        	getCalculoPredialBo().guardarDeterminacion(determinacionBase);
					        	getCalculoPredialBo().actualizaEstadoDtDeterminacionPredio(determinacionBase.getDeterminacionId(), Constante.ESTADO_ACTIVO);
					    		getCalculoPredialBo().actualizaEstadoDtDeterminacionConstruccion(determinacionBase.getDeterminacionId(), Constante.ESTADO_ACTIVO);
		
					    		//Insertamos en la tabla dt_determinacion_valor,para registrar la variación en ambas determinaciones.
		    					DtDeterminacionValor variacion=new DtDeterminacionValor();
		        					variacion.setDeterminacionId(determinacionBase.getDeterminacionId());
		        					variacion.setAnnoDeterminacion(determinacionBase.getAnnoDeterminacion());
		        					variacion.setPersonaId(determinacionBase.getPersonaId());
		        					variacion.setConceptoId(determinacionBase.getConceptoId());
		        					variacion.setSubconceptoId(determinacionBase.getSubconceptoId());
		        					variacion.setBaseImponible(determinacionBase.getBaseImponible());
		        					variacion.setImpuesto(determinacionBase.getImpuesto());
		        					
		        					variacion.setDeterminacionIdVariacion(determinacion.getDeterminacionId());
		        					variacion.setAnnoDeterminacionVariacion(determinacion.getAnnoDeterminacion());
		        					variacion.setPersonaIdVariacion(determinacion.getPersonaId());
		        					variacion.setConceptoIdVariacion(determinacion.getConceptoId());
		        					variacion.setSubconceptoIdVariacion(determinacion.getSubconceptoId());
		        					variacion.setBaseImponibleVariacion(determinacion.getBaseImponible());
		        					variacion.setImpuestoVariacion(determinacion.getImpuesto());
		        					variacion.setDiffImpuesto(impDiff);
		        					variacion.setFlagAumento(Constante.ESTADO_INACTIVO);
		        					
		        					getCalculoPredialBo().guardarDeterminacionVariacion(variacion);
		        					
		        					msg="Tiene valores,la diferencia es menor por lo tanto no genera deuda, mantiene la deuda anterior activa ";
						}
			 //}
			}else{//No tiene valor.x
			//SE REALIZA DETERMINACI�N DE LA CUENTA CORRIENTE POR DIFERENCIA, respecto de la determinaci�n original
			//independiente de si tiene valores o no se genera cuenta corriente por la diferencia.
			if (determinacion.getImpuestoDiferencia().doubleValue() > 0) {
				msg="[NO ACEPTADO-CERRADO] Se genera la deuda por la diferencia "+determinacion.getImpuestoDiferencia()+", mantiene la deuda anterior activa";
				guardarDeuda(determinacion,determinacion.getImpuestoDiferencia(),cuotas,Boolean.FALSE);
				
				//---imputar todas los pagos de las determinaciones fiscalizadas
					calculoPredialBo.inputarDeudaFiscalizada(determinacion.getDeterminacionId());	//-- SOLO INGRESAN LAS DETERMINACIONES CUYA FISCALIZACION ES SI - NO - SI
	    		
				//Mantiene la determinacion anterior Activo debido a que su deuda sigue activa (solo se genero una nueva deuda por la direferencia) 
					if (determinacionBase!=null){
							determinacionBase.setFechaActualizacion(DateUtil.getCurrentDate());
				        	determinacionBase.setEstado(Constante.ESTADO_ACTIVO);
				        	getCalculoPredialBo().guardarDeterminacion(determinacionBase);
				        	getCalculoPredialBo().actualizaEstadoDtDeterminacionPredio(determinacionBase.getDeterminacionId(), Constante.ESTADO_ACTIVO);
				    		getCalculoPredialBo().actualizaEstadoDtDeterminacionConstruccion(determinacionBase.getDeterminacionId(), Constante.ESTADO_ACTIVO);
					}
	    		
	    		/**
				 * Verificando si alguna de la deudas en la "Determinacion Base" tiene valores.::26-11-2014::
				 * Sólo se registrará la variación,dependiendo si genera un incremento o disminución::
				 */
			}else{
				msg="[NO ACEPTADO-CERRADO] La diferencia es cero o menor por lo tanto no genera deuda, mantiene la deuda anterior activa";
				//Se mantiene los estados originales
						determinacion.setFechaActualizacion(DateUtil.getCurrentDate());
						determinacion.setEstado(Constante.ESTADO_INACTIVO);
			        	getCalculoPredialBo().guardarDeterminacion(determinacion);
			        	getCalculoPredialBo().actualizaEstadoDtDeterminacionPredio(determinacion.getDeterminacionId(), Constante.ESTADO_INACTIVO);
			    		getCalculoPredialBo().actualizaEstadoDtDeterminacionConstruccion(determinacion.getDeterminacionId(), Constante.ESTADO_INACTIVO);
			    		
			    		determinacionBase.setFechaActualizacion(DateUtil.getCurrentDate());
			        	determinacionBase.setEstado(Constante.ESTADO_ACTIVO);
			        	getCalculoPredialBo().guardarDeterminacion(determinacionBase);
			        	getCalculoPredialBo().actualizaEstadoDtDeterminacionPredio(determinacionBase.getDeterminacionId(), Constante.ESTADO_ACTIVO);
			    		getCalculoPredialBo().actualizaEstadoDtDeterminacionConstruccion(determinacionBase.getDeterminacionId(), Constante.ESTADO_ACTIVO);
   		//*** la diff es cero
				}//fin else
			}//fin else: NO TIENE VALOR
		}else{			//Si existe determinación y deuda para su anterior declaración, se debe evaluar los caso de pagos y valores.
			if(determinacionBase!=null){
						//Cod.anterior::// List<DeudaValoresDTO> lstDeuVal = getCalculoPredialBo().getDeudaValores(determinacionBase.getDeterminacionId());
				List<DeudaValoresDTO> lstDeuVal = getCalculoPredialBo().getDeudaValores(determinacionBase.getPersonaId(),Constante.CONCEPTO_PREDIAL,determinacionBase.getAnnoDeterminacion());
				DeudaPagosDTO deuPagPrevio = getCalculoPredialBo().getDeudaPagos(determinacionBase.getDeterminacionId());
				
				/**
				 * Verificando si alguna de la deudas en la "Determinacion Base" tiene valores.::26-11-2014::
				 * Dependiendo si mantiene el monto del impuesto o si existe una variación::
				 */
				
				boolean tieneVal = false;
				for (DeudaValoresDTO dv : lstDeuVal) {
														// Cod. anterior
														// if (dv.getFlagCoactiva() != null&&Util.toInteger(dv.getFlagCoactiva())>2) {
														// tieneVal = true;
														// break;
														// }
					if (dv.getFlagOp()!= null&&Util.toInteger(dv.getFlagOp())>0) {
						tieneVal = true;
						break;
					}
				}
				
				if(tieneVal){ //Si tiene valor.
					
				 //Obtenemos el monto de las Determinaciones con Valor.
					DatosDeterminacionPredialValoresDTO deterValor=getCalculoPredialBo().getMontoDeterminacionValor(determinacionBase.getPersonaId(),Constante.CONCEPTO_PREDIAL,determinacionBase.getAnnoDeterminacion());
					
				 //Generamos la diferencia entre el impuesto nuevo generado y el monto total de las determinaciones con valor.
					BigDecimal impDiff = determinacion.getImpuesto().subtract(deterValor.getImpuesto());
					impDiff = impDiff.setScale(2, RoundingMode.HALF_UP);
				    
				 //Actualizando la diferencia de la nueva determinacion.
					getCalculoPredialBo().actualizarDiffDeterminacion(determinacion.getDeterminacionId(), impDiff);

				 //Verificando si la Determinacion Previa sin valor tiene pagos://?for
					List<DatosDeterminacionPredialValoresDTO> dtsinValor=getCalculoPredialBo().getDeterminacionValores(determinacionBase.getPersonaId(),Constante.CONCEPTO_PREDIAL,determinacionBase.getAnnoDeterminacion(),determinacion.getDeterminacionId());
					  if(dtsinValor.equals(null)|| dtsinValor.isEmpty()){
						 //***
					  }else if(dtsinValor.get(0).getDeterminacionId()!=0 || dtsinValor.size()!=0 ){
						   deuSinValorPagPrevio=getCalculoPredialBo().getDeudaValoresPagos(determinacionBase.getPersonaId(),determinacionBase.getAnnoDeterminacion(),dtsinValor.get(0).getDeterminacionId());
					  }
				//Si tiene valor,y la B.I. nueva es mayor que el total de la B.I. con valores,genera la deuda por la diferencia.
					if(impDiff.doubleValue() > 0){//if (determinacion.getImpuestoDiferencia().doubleValue() > 0) {
					 if (deuSinValorPagPrevio==null||deuSinValorPagPrevio.equals(null)||deuSinValorPagPrevio.equals("")||deuSinValorPagPrevio.getTotalCancelado().doubleValue() == 0 || deuSinValorPagPrevio.getTotalCancelado()== null) {// No existen Pagos
						 
						 getCalculoPredialBo().actualizarFlagVariacionDeterminacion(determinacion.getDeterminacionId(), Constante.ESTADO_ACTIVO);
						 //Cod.anterior: //guardarDeuda(determinacion,determinacion.getImpuestoDiferencia(),cuotas,Boolean.FALSE);
						 guardarDeuda(determinacion,impDiff,cuotas,Boolean.FALSE);
									 
						 //Si existe una Determinacion sin valor en estado=1,se inactivará,al igual que la deuda y las demás tablas asociadas.
						 	List<DatosDeterminacionPredialValoresDTO> lDtsinValor=getCalculoPredialBo().getDeterminacionValores(determinacionBase.getPersonaId(),Constante.CONCEPTO_PREDIAL,determinacionBase.getAnnoDeterminacion(),determinacion.getDeterminacionId());
						    //DatosDeterminacionPredialValoresDTO dt=getCalculoPredialBo().getDeterminacionValores(determinacionBase.getPersonaId(),Constante.CONCEPTO_PREDIAL,determinacionBase.getAnnoDeterminacion());
							
						 		if(lDtsinValor.equals(null)|| dtsinValor.isEmpty()){
								 //***
						 		}else if (lDtsinValor.get(0).getDeterminacionId()!=0){
									 for(int i=0;i<lDtsinValor.size();i++){
											getCalculoPredialBo().actualizarEstadoDeterminacion(lDtsinValor.get(0).getDeterminacionId(), Constante.ESTADO_INACTIVO);
											getCalculoPredialBo().actualizaEstadoCdDeuda(lDtsinValor.get(0).getDeterminacionId(), Constante.ESTADO_INACTIVO);
								        	getCalculoPredialBo().actualizaEstadoDtDeterminacionPredio(lDtsinValor.get(0).getDeterminacionId(), Constante.ESTADO_INACTIVO);
								    		getCalculoPredialBo().actualizaEstadoDtDeterminacionConstruccion(lDtsinValor.get(0).getDeterminacionId(), Constante.ESTADO_INACTIVO);
									 }
								}
							
						//mayor:
							//Insertamos en la tabla dt_determinacion_valor,para registrar la variación en ambas determinaciones.
	    					DtDeterminacionValor variacion=new DtDeterminacionValor();
	        					variacion.setDeterminacionId(determinacionBase.getDeterminacionId());
	        					variacion.setAnnoDeterminacion(determinacionBase.getAnnoDeterminacion());
	        					variacion.setPersonaId(determinacionBase.getPersonaId());
	        					variacion.setConceptoId(determinacionBase.getConceptoId());
	        					variacion.setSubconceptoId(determinacionBase.getSubconceptoId());
	        					variacion.setBaseImponible(determinacionBase.getBaseImponible());
	        					variacion.setImpuesto(determinacionBase.getImpuesto());
	        					variacion.setFechaRegistroDt(determinacionBase.getFechaRegistro());
	        					
	        					variacion.setDeterminacionIdVariacion(determinacion.getDeterminacionId());
	        					variacion.setAnnoDeterminacionVariacion(determinacion.getAnnoDeterminacion());
	        					variacion.setPersonaIdVariacion(determinacion.getPersonaId());
	        					variacion.setConceptoIdVariacion(determinacion.getConceptoId());
	        					variacion.setSubconceptoIdVariacion(determinacion.getSubconceptoId());
	        					variacion.setBaseImponibleVariacion(determinacion.getBaseImponible());
	        					variacion.setImpuestoVariacion(determinacion.getImpuesto());
	        					variacion.setFechaRegistroDtVariacion(determinacion.getFechaRegistro());
	        					variacion.setFlagAumento(Constante.ESTADO_ACTIVO);
	        					variacion.setDiffImpuesto(impDiff);
	        					getCalculoPredialBo().guardarDeterminacionVariacion(variacion);
	        					
	        					msg="Tiene valores se genera la deuda por la diferencia "+impDiff+", mantiene la deuda anterior activa";
						}else{
							 getCalculoPredialBo().actualizarFlagVariacionDeterminacion(determinacion.getDeterminacionId(), Constante.ESTADO_ACTIVO);
							 guardarDeuda(determinacion,impDiff,cuotas,Boolean.FALSE);

							//Si existe una Determinacion sin valor en estado=1,se inactivará,al igual que la deuda y las demás tablas asociadas.
							 	List<DatosDeterminacionPredialValoresDTO> lDtsinValor=getCalculoPredialBo().getDeterminacionValores(determinacionBase.getPersonaId(),Constante.CONCEPTO_PREDIAL,determinacionBase.getAnnoDeterminacion(),determinacion.getDeterminacionId());
							 	//? for?
							 if(deuSinValorPagPrevio.getTotalCancelado().doubleValue() > 0){
									calculoPredialBo.inputarDeudaSinValor(lDtsinValor.get(0).getDeterminacionId(), determinacion.getDeterminacionId());
								}

							//mayor:
								//Insertamos en la tabla dt_determinacion_valor,para registrar la variación en ambas determinaciones.
		    					DtDeterminacionValor variacion=new DtDeterminacionValor();
		        					variacion.setDeterminacionId(determinacionBase.getDeterminacionId());
		        					variacion.setAnnoDeterminacion(determinacionBase.getAnnoDeterminacion());
		        					variacion.setPersonaId(determinacionBase.getPersonaId());
		        					variacion.setConceptoId(determinacionBase.getConceptoId());
		        					variacion.setSubconceptoId(determinacionBase.getSubconceptoId());
		        					variacion.setBaseImponible(determinacionBase.getBaseImponible());
		        					variacion.setImpuesto(determinacionBase.getImpuesto());
		        					variacion.setFechaRegistroDt(determinacionBase.getFechaRegistro());
		        					
		        					variacion.setDeterminacionIdVariacion(determinacion.getDeterminacionId());
		        					variacion.setAnnoDeterminacionVariacion(determinacion.getAnnoDeterminacion());
		        					variacion.setPersonaIdVariacion(determinacion.getPersonaId());
		        					variacion.setConceptoIdVariacion(determinacion.getConceptoId());
		        					variacion.setSubconceptoIdVariacion(determinacion.getSubconceptoId());
		        					variacion.setBaseImponibleVariacion(determinacion.getBaseImponible());
		        					variacion.setImpuestoVariacion(determinacion.getImpuesto());
		        					variacion.setFechaRegistroDtVariacion(determinacion.getFechaRegistro());
		        					variacion.setDiffImpuesto(impDiff);
		        					variacion.setFlagAumento(Constante.ESTADO_ACTIVO);
		        					getCalculoPredialBo().guardarDeterminacionVariacion(variacion);
		        					
		        					msg="Tiene valores se genera la deuda por la diferencia "+impDiff+", mantiene la deuda anterior activa y la determinacion previa (sin Valor) posee pagos";
						}
					}else if(impDiff.doubleValue() == 0){//(determinacion.getImpuestoDiferencia().doubleValue() == 0 && determinacionBase.getBaseImponible().compareTo(determinacion.getBaseImponible())==0){
							
						//Se mantiene los estados originales
							getCalculoPredialBo().actualizarEstadoDeterminacion(determinacion.getDeterminacionId(), Constante.ESTADO_INACTIVO);
				        	getCalculoPredialBo().actualizaEstadoDtDeterminacionPredio(determinacion.getDeterminacionId(), Constante.ESTADO_INACTIVO);
				    		getCalculoPredialBo().actualizaEstadoDtDeterminacionConstruccion(determinacion.getDeterminacionId(), Constante.ESTADO_INACTIVO);
		    		
				    		determinacionBase.setFechaActualizacion(DateUtil.getCurrentDate());
				        	determinacionBase.setEstado(Constante.ESTADO_ACTIVO);
				        	getCalculoPredialBo().guardarDeterminacion(determinacionBase);
				        	getCalculoPredialBo().actualizaEstadoDtDeterminacionPredio(determinacionBase.getDeterminacionId(), Constante.ESTADO_ACTIVO);
				    		getCalculoPredialBo().actualizaEstadoDtDeterminacionConstruccion(determinacionBase.getDeterminacionId(), Constante.ESTADO_ACTIVO);
							
				    		msg="Tiene valores,mantiene el monto de la B.I. por lo tanto no genera deuda, mantiene la deuda anterior activa ";
						
					}else if(impDiff.doubleValue() < 0){//(determinacion.getImpuestoDiferencia().doubleValue() < 0 && determinacionBase.getBaseImponible().compareTo(determinacion.getBaseImponible())>0){

							determinacion.setFechaActualizacion(DateUtil.getCurrentDate());
							determinacion.setEstado(Constante.ESTADO_INACTIVO);
				        	getCalculoPredialBo().guardarDeterminacion(determinacion);
				        	getCalculoPredialBo().actualizaEstadoDtDeterminacionPredio(determinacion.getDeterminacionId(), Constante.ESTADO_INACTIVO);
				    		getCalculoPredialBo().actualizaEstadoDtDeterminacionConstruccion(determinacion.getDeterminacionId(), Constante.ESTADO_INACTIVO);
				    		
				    		determinacionBase.setFechaActualizacion(DateUtil.getCurrentDate());
				        	determinacionBase.setEstado(Constante.ESTADO_ACTIVO);
				        	getCalculoPredialBo().guardarDeterminacion(determinacionBase);
				        	getCalculoPredialBo().actualizaEstadoDtDeterminacionPredio(determinacionBase.getDeterminacionId(), Constante.ESTADO_ACTIVO);
				    		getCalculoPredialBo().actualizaEstadoDtDeterminacionConstruccion(determinacionBase.getDeterminacionId(), Constante.ESTADO_ACTIVO);
	
				    		//Insertamos en la tabla dt_determinacion_valor,para registrar la variación en ambas determinaciones.
	    					DtDeterminacionValor variacion=new DtDeterminacionValor();
	        					variacion.setDeterminacionId(determinacionBase.getDeterminacionId());
	        					variacion.setAnnoDeterminacion(determinacionBase.getAnnoDeterminacion());
	        					variacion.setPersonaId(determinacionBase.getPersonaId());
	        					variacion.setConceptoId(determinacionBase.getConceptoId());
	        					variacion.setSubconceptoId(determinacionBase.getSubconceptoId());
	        					variacion.setBaseImponible(determinacionBase.getBaseImponible());
	        					variacion.setImpuesto(determinacionBase.getImpuesto());
	        					
	        					variacion.setDeterminacionIdVariacion(determinacion.getDeterminacionId());
	        					variacion.setAnnoDeterminacionVariacion(determinacion.getAnnoDeterminacion());
	        					variacion.setPersonaIdVariacion(determinacion.getPersonaId());
	        					variacion.setConceptoIdVariacion(determinacion.getConceptoId());
	        					variacion.setSubconceptoIdVariacion(determinacion.getSubconceptoId());
	        					variacion.setBaseImponibleVariacion(determinacion.getBaseImponible());
	        					variacion.setImpuestoVariacion(determinacion.getImpuesto());
	        					variacion.setDiffImpuesto(impDiff);
	        					variacion.setFlagAumento(Constante.ESTADO_INACTIVO);
	        					
	        					getCalculoPredialBo().guardarDeterminacionVariacion(variacion);
	        					
	        					msg="Tiene valores,la diferencia es menor por lo tanto no genera deuda, mantiene la deuda anterior activa ";
					}
				}else{
					//No tiene valores entonces evaluamos los pagos anteriores:
					if(deuPagPrevio != null && deuPagPrevio.getTotalCancelado()!=null && deuPagPrevio.getTotalCancelado().doubleValue() > 0){
						//Si tiene pagos
						msg="[ACEPTADO] No tiene valores y Si posee pagos por "+deuPagPrevio.getTotalCancelado().doubleValue()+", se genera la deuda por el total "+determinacion.getImpuesto() +" y realiza el proceso de imputacion, inactiva la deuda anterior ";
						//Inactivar la deuda antes de generar la nueva deuda
						getCalculoPredialBo().actualizaEstadoCdDeuda(determinacionBase.getDeterminacionId(), Constante.ESTADO_INACTIVO);
						Boolean conDerechoEmision=generaDerechoEmision(determinacion.getImpuesto().doubleValue(), tipoCondicionEspecial);
						guardarDeuda(determinacion,determinacion.getImpuesto(),cuotas,conDerechoEmision);
						calculoPredialBo.inputarDeudaSinValores(determinacionBase.getDeterminacionId(), determinacion.getDeterminacionId());
					}else{
						//No tiene pagos
						// Inahabilita anteriores, no funciona desde aca, ya está en el store de inputación
						msg="[ACEPTADO] No tiene valores y No posee pagos, se genera la deuda por el total "+determinacion.getImpuesto() +", inactiva la anterior deuda ";
						//Inactivar la deuda antes de generar la nueva deuda
						getCalculoPredialBo().actualizaEstadoCdDeuda(determinacionBase.getDeterminacionId(), Constante.ESTADO_INACTIVO);
						Boolean conDerechoEmision=generaDerechoEmision(determinacion.getImpuesto().doubleValue(), tipoCondicionEspecial);
						guardarDeuda(determinacion,determinacion.getImpuesto(),cuotas,conDerechoEmision);
					}
				}
			}else{
				//Genera la deuda por el total de la deuda
				msg="[NORMAL] Determinacion inicial, se genera la deuda por el total "+determinacion.getImpuesto();
				Boolean conDerechoEmision=generaDerechoEmision(determinacion.getImpuesto().doubleValue(),tipoCondicionEspecial);
				guardarDeuda(determinacion,determinacion.getImpuesto(),cuotas,conDerechoEmision);
			}
		}
		return msg;
	}

	private Boolean generaDerechoEmision(Double impuesto, int tipoCondicionEspecial){
		
		Boolean resp = Boolean.TRUE; 
		 
		
		if(tipoCondicionEspecial == 12 && impuesto.doubleValue() == 0){    		
			/** tipoCondicionEspecial Pensionistas */
			resp = Boolean.TRUE;			
    	}else if(tipoCondicionEspecial > 1 && impuesto.doubleValue() == 0 ){
    		/** Condicion especial Entidades religiosas */
    		resp = Boolean.TRUE;
    	}else if(impuesto.doubleValue() == 0){
    		//No se genera el derecho de emision ni se registra la deuda
    		//Es predio no le corresponde calculo en el periodo actual o es un predio descargado
    		resp = Boolean.FALSE;
    	}
		
		return resp;
	}
	
	public void guardarDeuda(DtDeterminacion determinacion,BigDecimal impuesto,DtCuotaConcepto cuotas,Boolean conDerechoEmision)throws Exception{
		//Genera la deuda por el total de la deuda
		List<DtFechaVencimiento> listaFV = getCalculoPredialBo().getFechaVencimiento(Constante.SUB_CONCEPTO_PREDIAL,determinacion.getAnnoDeterminacion());
		Integer numeroCuotas=4;//Por defecto
		if(cuotas!=null)
			numeroCuotas=cuotas.getNroCuotas();
		
		BigDecimal montoCuota = impuesto.divide(new BigDecimal(String.valueOf(numeroCuotas)), 2,RoundingMode.HALF_UP);
		for(int i=0;i<numeroCuotas;i++){
			CdDeuda deuda = new CdDeuda();
			deuda.setTipoDeudaId(Constante.TIPO_DEUDA_AUTOGENERADO);
			deuda.setDeudaId(Constante.RESULT_PENDING);
			deuda.setPersonaId(determinacion.getPersonaId());
			deuda.setConceptoId(Constante.CONCEPTO_PREDIAL);
			deuda.setSubconceptoId(Constante.SUB_CONCEPTO_PREDIAL);
			deuda.setDeterminacionId(determinacion.getDeterminacionId());
			deuda.setAnnoDeuda(determinacion.getAnnoDeterminacion());
			deuda.setFechaEmision(DateUtil.getCurrentDate());
			deuda.setFechaVencimiento(listaFV.get(i).getFechaVencimiento());
			deuda.setNroCuota(listaFV.get(i).getCuota());
			deuda.setMontoOriginal(impuesto);
			deuda.setEstadoDeudaId(Constante.ESTADO_DEUDA_DETERMINADO);
			
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
			//No le genera a la determinacion por difetencia el derecho de emision
			if (conDerechoEmision.booleanValue()&&impuesto.doubleValue()>=0&&deuda.getNroCuota() == cuotas.getCuotaDerechoEmision()) {				
				deuda.setDerechoEmision(cuotas.getMontoDerechoEmision());
				deuda.setTotalDeuda(montoCuota.add(deuda.getDerechoEmision()));
			} else {
				deuda.setDerechoEmision(BigDecimal.ZERO);
				deuda.setTotalDeuda(montoCuota);
			}
			deuda.setInsoluto(montoCuota);
			deuda.setTotalCancelado(new BigDecimal(0));
			deuda.setNroReferencia(determinacion.getDjreferenciaId());
			deuda.setNroCuentaBanco(null);
			
			//cc: deuda.setUsuarioId(Constante.USUARIO_ID);
			//cc: deuda.setFechaRegistro(DateUtil.getCurrentDate());
			//cc: deuda.setTerminal(Constante.TERMINAL);
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
		}
	}
	
	public Double getMontoMinimoImpuesto(Integer anio){
		Double cdblMontoMinimoImpuesto=Double.valueOf(0);
		try{
			ArrayList<DtTasaImpuestoPredial> aTasaImpuesto=getCalculoPredialBo().getAllDeTasaImpuestoPredial(anio);
			Iterator<DtTasaImpuestoPredial> it = aTasaImpuesto.iterator();
		    while (it.hasNext()){
		    	DtTasaImpuestoPredial tasa= it.next();
		    	if(tasa.getTipoTasa().equals(Constante.TIPO_TASA_IMPUESTO_PREDIAL_BASE)){
		    		cdblMontoMinimoImpuesto=tasa.getMontoInicio().doubleValue();
		    	}
		    }
		}catch(Exception e){
			
		}
	    return cdblMontoMinimoImpuesto;
	}
	//dtDatos = dsTasas.Tables("TasaImpuesto").Copy
	public Double calcularImpuesto(Double vdblBaseImponible,Integer anio){
		Double dblValorImpuesto= Double.valueOf(0);
		try{
			Double cdblMontoMinimoImpuesto=Double.valueOf(0);
			ArrayList<DtTasaImpuestoPredial> aTasaImpuesto=getCalculoPredialBo().getAllDeTasaImpuestoPredial(anio);
			Iterator<DtTasaImpuestoPredial> it = aTasaImpuesto.iterator();
			Integer tramoIdCalculo=0;
			
		    while (it.hasNext()){
		    	DtTasaImpuestoPredial tasa= it.next();
		    	if(tasa.getTipoTasa().trim().equals(Constante.TIPO_TASA_IMPUESTO_PREDIAL_BASE)){
		    		cdblMontoMinimoImpuesto=tasa.getMontoInicio().doubleValue();
		    	}else{
		    		
		    		String strTipoTasa = tasa.getTipoTasa();
		            Double dblMontoInicio = tasa.getMontoInicio().doubleValue();
		            Double dblMontoFin = tasa.getMontoFin().doubleValue();
		            Integer intOrdenTramoId = tasa.getId().getTramoId();
		            
		            if(strTipoTasa.trim().equals(Constante.TIPO_TASA_IMPUESTO_PREDIAL_RANGO_INICIO_FIN)){
		            	//Evaluamos si la base imponible esta entre este rango
		            	if(vdblBaseImponible > dblMontoInicio && vdblBaseImponible <= dblMontoFin){
		            		tramoIdCalculo=intOrdenTramoId;
		            	}else{
		            		if(vdblBaseImponible > dblMontoFin){
		            			tramoIdCalculo=intOrdenTramoId;
		            		}
		            	}
		            }else if(strTipoTasa.trim().equals(Constante.TIPO_TASA_IMPUESTO_PREDIAL_SIN_RANGO_FIN)){
		            	if(vdblBaseImponible > dblMontoInicio){
		            		tramoIdCalculo=intOrdenTramoId;
		            	}
		            }
		    	}
		    }
		    
		    //el tramo ultimo es en donde cayo la base imponible
		    Iterator<DtTasaImpuestoPredial> it2 = aTasaImpuesto.iterator();
		    while (it2.hasNext()){
		    	DtTasaImpuestoPredial tasa= it2.next();
		    	if(tasa.getId().getTramoId()==tramoIdCalculo){
		    		String strTipoTasa = tasa.getTipoTasa();
		            Double dblTasa = tasa.getTasa().doubleValue();
		            if(strTipoTasa.trim().equals(Constante.TIPO_TASA_IMPUESTO_PREDIAL_SIN_RANGO_FIN)){//2
		            	DtTasaImpuestoPredial tramoAnterior= aTasaImpuesto.get((tramoIdCalculo-1)-1);
		            	Double dblValorTramoAnterior = tramoAnterior.getMontoFin().doubleValue();
		            	Double dblMontoAcumulativo = tramoAnterior.getAcumulativo().doubleValue();
		            	dblValorImpuesto = dblMontoAcumulativo + dblTasa * (vdblBaseImponible - dblValorTramoAnterior) / 100;
		            }else{
		            	if(tramoIdCalculo==1){
		            		dblValorImpuesto = dblTasa * vdblBaseImponible / 100;
		            	}else{
		            		//' sacamos acumlativo
		            		DtTasaImpuestoPredial tramoAnterior= aTasaImpuesto.get(tramoIdCalculo-1);
		            		Double dblValorTramoAnterior = tramoAnterior.getMontoFin().doubleValue();
			            	Double dblMontoAcumulativo = tramoAnterior.getAcumulativo().doubleValue();
	                        dblValorImpuesto = dblMontoAcumulativo + dblTasa * (vdblBaseImponible - dblValorTramoAnterior) / 100;
		            	}
		            }
		    	}
		    }
		}catch(Exception e){
			e.printStackTrace();
		}
		return dblValorImpuesto;
	}
	
	public Double obtenerValorOtrasInstalaciones(RpDjpredial rpDjPredial,Integer determinacionId,Integer deterPredioId,Integer anio)throws Exception{
		double dblValorObraTotal=0;
		List<RpInstalacionDj> lInstalaciones=getCalculoPredialBo().getOtrasInstalaciones(rpDjPredial,anio);
		for(int i=0;i<lInstalaciones.size();i++){
			RpInstalacionDj instalacion=lInstalaciones.get(i);
			double dblValorObra=instalacion.getValorInstalacion().doubleValue();
			dblValorObraTotal=dblValorObraTotal+dblValorObra;
			
			DtDeterminacionInstalacion deterInsta=new DtDeterminacionInstalacion();
			deterInsta.setDeterminacionInstalacionId(Constante.RESULT_PENDING);
			deterInsta.setDeterminacionId(determinacionId);
			deterInsta.setDeterPredioId(deterPredioId);
			deterInsta.setDjId(instalacion.getDjId());
			deterInsta.setInstalacionId(instalacion.getInstalacionId());
			deterInsta.setValorInstalacion(instalacion.getValorInstalacion());
			
			//cc: deterInsta.setTerminal(Constante.TERMINAL);
			//cc: deterInsta.setUsuarioId(Constante.USUARIO_ID);
			deterInsta.setEstado(Constante.ESTADO_ACTIVO);
			//cc: deterInsta.setFechaRegistro(DateUtil.getCurrentDate());
			
			getCalculoPredialBo().guardarDeterminacionInstalacion(deterInsta);
		}
		
		//De acuerdo a la norma legar del 30/10/2011 se debe applicar el factor de oficializacion del 31 de octubre del anio anterior al resultante del valor de otras instalaciones
		DtFactorOfic factorOficializacion=getCalculoPredialBo().getFactorOficializacion(anio-1);
		if(factorOficializacion!=null&&factorOficializacion.getFactor().doubleValue()>0)
			dblValorObraTotal=dblValorObraTotal*factorOficializacion.getFactor().doubleValue();
		
		return dblValorObraTotal;
	}
	
	public Double obtenerValorOtrasInstalacionesConAnioFiscal(RpDjpredial rpDjPredial,Integer determinacionId,Integer deterPredioId,Integer anio)throws Exception{
		/**PARA OTRAS INSTALACIONES QUE INCLUYAN EL ANIO FISCAL - INICIO
		 * */
		double dblValorObraTotalConAnioFiscal=0;
		
		List<RpInstalacionDj> lInstalacionesConAnioFiscal=getCalculoPredialBo().getOtrasInstalaciones(rpDjPredial,anio);
		for(int i=0;i<lInstalacionesConAnioFiscal.size();i++){
			RpInstalacionDj instalacion = lInstalacionesConAnioFiscal.get(i);
			double dblValorObraConAnioFiscal = instalacion.getValorInstalacion().doubleValue();
			dblValorObraTotalConAnioFiscal = dblValorObraTotalConAnioFiscal+dblValorObraConAnioFiscal;
		}
		
		//De acuerdo a la norma legar del 30/10/2011 se debe applicar el factor de oficializacion del 31 de octubre del anio anterior al resultante del valor de otras instalaciones
		DtFactorOfic factorOficializacion=getCalculoPredialBo().getFactorOficializacion(anio-1);		
		if(factorOficializacion!=null&&factorOficializacion.getFactor().doubleValue()>0)
			dblValorObraTotalConAnioFiscal=dblValorObraTotalConAnioFiscal*factorOficializacion.getFactor().doubleValue();
		
		dblValorObraTotalConAnioFiscal = (new BigDecimal(dblValorObraTotalConAnioFiscal)).setScale(2, RoundingMode.HALF_UP).doubleValue();
		
		return dblValorObraTotalConAnioFiscal;
	}
	
	public Double obtenerValorTerreno(RpDjpredial rpDjpredial,Integer anio)throws Exception{
		Double valorTerreno=Double.valueOf(0);
		Double dblAreaTerreno=Double.valueOf(0);
		Double valorArancel=obtenerValorArancel(rpDjpredial,anio);
		
		if(rpDjpredial.getTipoPredio().equals(Constante.TIPO_PREDIO_URBANO)){
			if(rpDjpredial.getAreaTerreno()!=null)
				 dblAreaTerreno = rpDjpredial.getAreaTerreno().doubleValue();	
		}else{
			if(rpDjpredial.getAreaTerrenoHas()!=null)
				 dblAreaTerreno = rpDjpredial.getAreaTerrenoHas().doubleValue();
		}
		
		Double dblAreaTerrenoComun=new Double(0);
		Double dblFrente=new Double(0);
		
		if(rpDjpredial.getAreaTerrenoComun()!=null)
			dblAreaTerrenoComun = rpDjpredial.getAreaTerrenoComun().doubleValue();
		
		if(rpDjpredial.getFrente()!=null)
			dblFrente=rpDjpredial.getFrente().doubleValue();
		
		//Lista de Frentes si los tuviera
		List<RpOtrosFrente> lstOtrosFrentes = getCalculoPredialBo().obtenerOtrosFrentes(rpDjpredial.getDjId());
		
		//Aplicaci�n de la formula de reglamentaci�n  nacional de tasaciones del peru
		//Resolucion Ministerial 469-99 - del 10.12.1999
		Double dblAreaFrente = 3*dblFrente*dblFrente;
		Double dblAreaTotal = dblAreaTerreno+dblAreaTerrenoComun;
		
		//if(FLAG_CALCULO_VALOR_TERRENO_FRENTE==1){
		if(rpDjpredial.getTipoPredio().equals(Constante.TIPO_PREDIO_URBANO)){
			//frentera esta registrado correctamente
			if(rpDjpredial.getFrenteOk() != null  && rpDjpredial.getFrenteOk().equals(Constante.ESTADO_ACTIVO)  && dblFrente <= Constante.FRONTIS_MAXIMO  && dblFrente >= Constante.FRONTIS_MINIMO){
				
				/**Aplicacion de la Formula de Reglamentacion Nacional de Tasaciones del Peru Articulo II C 24
				**/
				if(rpDjpredial.getUbicacionPredioId()==4){
					/**La valuación de un lote de terreno urbano que tenga frente a un pasadizo común o vía de dominio privado en condominio
					**/
					if(rpDjpredial.getFrenteVia() != null && rpDjpredial.getFrenteVia().equals(Constante.ESTADO_INACTIVO)){
						Double valorAreaExclusivo = Double.valueOf(0);
						Double valorAreaProporcional = Double.valueOf(0);
						//Supuesto Valor Urbano 
						Double svu = getSupuestoValorUrbano(valorArancel,rpDjpredial.getFrenteAreaComun().doubleValue(),rpDjpredial.getDistanciaAPredio().doubleValue());
						
						//Si el supuesto valor urbano (SVU) resultará menor de 0.5 VT (valor arancelario) se desechará éste, considerándose como mínimo 0.5 VT. 
						if( svu < 0.5*valorArancel){
							svu = 0.5*valorArancel;
						}
						// A. valor del área del dominio exclusivo
						valorAreaExclusivo = valorTerreno(dblFrente, dblAreaFrente, dblAreaTerreno, svu);
						
						// B. valor de la vía de dominio privado o pasadizo común, el resultado obtenido se reparte proporcionalmente al área
						//C.22 para el AreaComun
						Double dblAreaFrenteComun = 3*rpDjpredial.getFrenteAreaComun().doubleValue()*rpDjpredial.getFrenteAreaComun().doubleValue();
						valorAreaProporcional = valorTerreno(rpDjpredial.getFrenteAreaComun().doubleValue(), dblAreaFrenteComun,dblAreaTerrenoComun,valorArancel);					
						valorAreaProporcional = valorAreaProporcional*rpDjpredial.getPorcentajeParticipacion().doubleValue()/100;
						
						//A+B. ValorTerreno se obtiene sumando al valor del área del dominio exclusivo el valor que le corresponde del pasadizo común
						valorTerreno = valorAreaExclusivo+valorAreaProporcional;
						valorTerreno=(new BigDecimal(valorTerreno)).setScale(2, RoundingMode.HALF_UP).doubleValue();
					}else{
						/** La valuación de un lote de terreno urbano que NO tenga frente a un pasadizo común o vía de dominio privado en condominio pero si pertenece a la Quinta
						 * Aplicacion de la Formula de Reglamentacion Nacional de Tasaciones del Peru Articulo II C 22
						 */
						Double dblAreaProporcional = Double.valueOf(0);
						dblAreaProporcional = dblAreaTerrenoComun*rpDjpredial.getPorcentajeParticipacion().doubleValue()/100;						
						
						if(lstOtrosFrentes.size() == 0){
							valorTerreno = valorTerreno(dblFrente,  dblAreaFrente, dblAreaTerreno+dblAreaProporcional, valorArancel);						
						}else if(lstOtrosFrentes.size() > 0){
							valorTerreno = valorTerrenoVariosFrentes( dblFrente, dblAreaTerreno+dblAreaProporcional, valorArancel, lstOtrosFrentes, anio );
						}
					}					
					
				}else if(rpDjpredial.getUbicacionPredioId()==8){
					/**Aplicacion de la Formula de Reglamentacion Nacional de Tasaciones del Peru Articulo II C 25
					 * La valuación de un terreno que no tenga acceso por vía pública ni por vía privada
					 * en condominio sino a través de un predio de propiedad de terceros mediante una servidumbre de paso
					**/
					Double valorAreaInterior = Double.valueOf(0);
					
					//Supuesto Valor Urbano, multiplicándose el supuesto valor urbano (SVU) por el coeficiente 0.8 
					Double svu = getSupuestoValorUrbano(valorArancel,rpDjpredial.getFrenteAreaComun().doubleValue(),rpDjpredial.getDistanciaAPredio().doubleValue());
					svu = svu * 0.8;
					//El resultado final no podrá ser menor de 0.4 VT 
					if( svu < 0.4*valorArancel){
						svu = 0.4*valorArancel;
					}					
					valorAreaInterior = valorTerreno(dblFrente, dblAreaFrente,dblAreaTerreno,svu);
					
					//ValorTerreno 
					valorTerreno = valorAreaInterior;
					valorTerreno=(new BigDecimal(valorTerreno)).setScale(2, RoundingMode.HALF_UP).doubleValue();
					
				}else if(lstOtrosFrentes.size() == 0){
					/**Aplicacion de la Formula de Reglamentacion Nacional de Tasaciones del Peru Articulo II C 22
					 * El lote de terreno urbano que tenga un solo frente a vía pública
					 */
					valorTerreno = valorTerreno(dblFrente, dblAreaFrente, dblAreaTotal, valorArancel);
					
				}else if(lstOtrosFrentes.size() > 0){
					/**Aplicacion de la Formula de Reglamentacion Nacional de Tasaciones del Peru Articulo II C 23
					 * El lote de terreno urbano que tenga más de un frente a vía pública
					 */
					
					valorTerreno = valorTerrenoVariosFrentes( dblFrente, dblAreaTotal, valorArancel, lstOtrosFrentes, anio );
					
//					List<Double> listFrentesTotal = new ArrayList<Double>();
//					List<Double> listAreasProporcionales = new ArrayList<Double>();
//					List<Double> listArancelesFrentes = new ArrayList<Double>();
//					
//					//Primer Frente
//					Double dblfrenteTotal = dblFrente;					
//					listFrentesTotal.add(dblFrente);
//					
//					//Arancel primer frontis
//					listArancelesFrentes.add(valorArancel);				
//					
//					for( RpOtrosFrente rpFrente : lstOtrosFrentes){
//						//Suma de todos los frentes
//						dblfrenteTotal = dblfrenteTotal + rpFrente.getFrente().doubleValue();
//						//lista de todos los frentes
//						listFrentesTotal.add(rpFrente.getFrente().doubleValue());
//						//Lista de Aranceles de los otros frentes
//						Double arancelfrente = getCalculoPredialBo().obtenerArancelOtrosFrentes(rpFrente.getUbicacionId(), anio);
//						listArancelesFrentes.add(arancelfrente);
//					}
//					
//					//Areas proporcionales				
//					for(Double frente : listFrentesTotal){
//						Double areaProporcional =  (dblAreaTotal*frente)/dblfrenteTotal;
//						listAreasProporcionales.add(areaProporcional);
//					}
//								
//					//PARTE A: CALCULO PARA CADA AREA PROPORCIONAL 			
//					Double valorTerrenoTotal_A = 0.0;
//					
//					for( int i=0; i<listAreasProporcionales.size(); i++){
//						Double frente = listFrentesTotal.get(i);
//						Double areaFrente = 3*frente*frente;
//											
//						Double valorTerreno_A = valorTerreno(frente, areaFrente,listAreasProporcionales.get(i),listArancelesFrentes.get(i));
//						//SUMA DE VALORES PROPORCIONALES
//						valorTerrenoTotal_A = valorTerrenoTotal_A + valorTerreno_A;
//					}
//					
//					//PARTE B: CONSIDERAR A CADA FRENTE COMO UNICO Y PROCEDER COMO ARTICULO II C 22
//					List<Double> listValorTerreno_B = new ArrayList<Double>();
//					
//					for( int i=0; i<listFrentesTotal.size(); i++){
//						Double frente = listFrentesTotal.get(i);
//						Double areaFrente = 3*frente*frente;
//											
//						Double valorTerreno_B = valorTerreno(frente, areaFrente,dblAreaTotal,listArancelesFrentes.get(i));
//						listValorTerreno_B.add(valorTerreno_B);
//					}
//					
//					//PARTE C: DETERMINAR EL MAYOR DE LOS VALORES DE A Y B
//					valorTerreno = mayorValor(valorTerrenoTotal_A,listValorTerreno_B);
//					valorTerreno=(new BigDecimal(valorTerreno)).setScale(2, RoundingMode.HALF_UP).doubleValue();
				}
				
			}else{
				/**CALCULO DE VALOR DE TERRENO PARA FRENTES FUERA DEL RANGO DE FRONTIS
				 * DEL PLAN DE ORDENAMIENTO URBANO DE CAJAMARCA 
				 */
				valorTerreno = valorArancel.doubleValue()*(dblAreaTotal);
				valorTerreno = (new BigDecimal(valorTerreno)).setScale(2, RoundingMode.HALF_UP).doubleValue();
			}		
		}else{//TIPO_PREDIO_RUSTICO
			valorTerreno=valorArancel.doubleValue()*(dblAreaTerreno+dblAreaTerrenoComun);
			valorTerreno=(new BigDecimal(valorTerreno)).setScale(2, RoundingMode.HALF_UP).doubleValue();				
		}
		//}
		return valorTerreno;	
	}
	
	/**Halla el valor de terreno con el triple cuadrado del frente
	 * */
	public Double valorTerreno(Double frente, Double dblAreaFrente, Double dblAreaTotal, Double valorArancel){
		Double valorTerreno;
		//Calculo del valor del terreno
		//Valor Terreno = AreaFrente * arancel + Exceso x arancel x 50%  ó AreaTotal x arancel
		//Donde : Area Frente= 3 [(Frente)x(Frente)] 				
		
		//if( dblAreaFrente > dblAreaTotal && frente <= Constante.FRONTIS_MAXIMO && frente >= Constante.FRONTIS_MINIMO){
		if( dblAreaFrente < dblAreaTotal){
			//Inciso a) hasta el AreaFrente * Arancel
			Double dblvalorFrente = dblAreaFrente*valorArancel.doubleValue();
			dblvalorFrente=(new BigDecimal(dblvalorFrente)).setScale(2, RoundingMode.HALF_UP).doubleValue();
			
			//donde : Exceso = AreaTotal - AreaFrente  
			Double dblExceso = dblAreaTotal - dblAreaFrente;
			
			//Inciso b) Exceso x arancel x 50%
			Double dblValorExceso = dblExceso*valorArancel.doubleValue()*0.5;
			dblValorExceso=(new BigDecimal(dblValorExceso)).setScale(2, RoundingMode.HALF_UP).doubleValue();
			
			valorTerreno = dblvalorFrente + dblValorExceso;
			valorTerreno=(new BigDecimal(valorTerreno)).setScale(2, RoundingMode.HALF_UP).doubleValue();
		}else{
			//Inciso a) o AreaTotal * Arancel si dblAreaTotal < dblAreaFrente  
			//VT=AT*Ar
			//AT:Area del terreno
			//Ar:Arancel de la via donde se ubica el predio
			valorTerreno = dblAreaTotal * valorArancel.doubleValue();
			valorTerreno=(new BigDecimal(valorTerreno)).setScale(2, RoundingMode.HALF_UP).doubleValue();
		}
		return valorTerreno;
	}
	/**Halla el valor de terreno basado en varios frentes
	 * */
	public Double valorTerrenoVariosFrentes( Double dblFrente, Double dblAreaTotal, Double valorArancel, List<RpOtrosFrente> lstOtrosFrentes, Integer anio ){
		Double valorTerreno = Double.valueOf(0);
		try {
			//Aplicacion de la Formula de Reglamentacion Nacional de Tasaciones del Peru Articulo II C 23
			List<Double> listFrentesTotal = new ArrayList<Double>();
			List<Double> listAreasProporcionales = new ArrayList<Double>();
			List<Double> listArancelesFrentes = new ArrayList<Double>();
			
			//Primer Frente
			Double dblfrenteTotal = dblFrente;					
			listFrentesTotal.add(dblFrente);
			
			//Arancel primer frontis
			listArancelesFrentes.add(valorArancel);				
			
			for( RpOtrosFrente rpFrente : lstOtrosFrentes){
				//Suma de todos los frentes
				dblfrenteTotal = dblfrenteTotal + rpFrente.getFrente().doubleValue();
				//lista de todos los frentes
				listFrentesTotal.add(rpFrente.getFrente().doubleValue());
				//Lista de Aranceles de los otros frentes
				Double arancelfrente;
				
					arancelfrente = getCalculoPredialBo().obtenerArancelOtrosFrentes(rpFrente.getUbicacionId(), anio);
				
				listArancelesFrentes.add(arancelfrente);
			}
			
			//Areas proporcionales				
			for(Double frente : listFrentesTotal){
				Double areaProporcional =  (dblAreaTotal*frente)/dblfrenteTotal;
				listAreasProporcionales.add(areaProporcional);
			}
						
			//PARTE A: CALCULO PARA CADA AREA PROPORCIONAL 			
			Double valorTerrenoTotal_A = 0.0;
			
			for( int i=0; i<listAreasProporcionales.size(); i++){
				Double frente = listFrentesTotal.get(i);
				Double areaFrente = 3*frente*frente;
									
				Double valorTerreno_A = valorTerreno(frente, areaFrente,listAreasProporcionales.get(i),listArancelesFrentes.get(i));
				//SUMA DE VALORES PROPORCIONALES
				valorTerrenoTotal_A = valorTerrenoTotal_A + valorTerreno_A;
			}
			
			//PARTE B: CONSIDERAR A CADA FRENTE COMO UNICO Y PROCEDER COMO ARTICULO II C 22
			List<Double> listValorTerreno_B = new ArrayList<Double>();
			
			for( int i=0; i<listFrentesTotal.size(); i++){
				Double frente = listFrentesTotal.get(i);
				Double areaFrente = 3*frente*frente;
									
				Double valorTerreno_B = valorTerreno(frente, areaFrente,dblAreaTotal,listArancelesFrentes.get(i));
				listValorTerreno_B.add(valorTerreno_B);
			}
			
			//PARTE C: DETERMINAR EL MAYOR DE LOS VALORES DE A Y B
			valorTerreno = mayorValor(valorTerrenoTotal_A,listValorTerreno_B);
			valorTerreno=(new BigDecimal(valorTerreno)).setScale(2, RoundingMode.HALF_UP).doubleValue();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return valorTerreno;
	}
	/**Halla el mayor valor de dos valores
	 * */
	public Double mayorValor(Double valorTerrenoTotal_A,List<Double> listValorTerreno_B){
		Double mayor=valorTerrenoTotal_A;
		for(Double valor : listValorTerreno_B){
			if(mayor < valor){
				mayor = valor;
			}
		}
		return mayor;
	}
	/**Halla el Supuesto Valor Urbano (valor alancelario)
	 * */
	public Double getSupuestoValorUrbano(Double valorArancel, Double frenteAreaComun, Double distanciaAPredio){
		Double svu = Double.valueOf(0);
		
		Double relacion = frenteAreaComun/3;
		if( relacion > 1){
			relacion = Double.valueOf(1) ;
		}
		
		svu = valorArancel*relacion*(Double.valueOf(1)-Double.valueOf(0.01)*distanciaAPredio);
		svu=(new BigDecimal(svu)).setScale(2, RoundingMode.HALF_UP).doubleValue();
		
		return svu;
	}
	
	public Double obtenerValorArancel(RpDjpredial rpDjpredial,Integer anio)throws Exception{
		Double dblArancel=Double.valueOf(0);
		if(rpDjpredial.getTipoPredio().equals(Constante.TIPO_PREDIO_RUSTICO)){
			dblArancel=getCalculoPredialBo().getValorArancelRustico(anio,rpDjpredial.getTipoTierraId(),rpDjpredial.getCategoriaRusticoId(),rpDjpredial.getAltitudId());
		}else{
			RpDjdireccion direccion=getCalculoPredialBo().getRpDjDireccion(rpDjpredial.getDjId());
			if(direccion!=null&&direccion.getDireccionId()>0)
				dblArancel=getCalculoPredialBo().getValorArancelUrbano(anio,direccion);
		}
		return dblArancel;
	}
	public Double obtenerValorConstruccion(RpDjpredial rpDjPredial,DtDeterminacion determinacion,Integer deterPredioId,Integer anio)throws Exception{
		double dblValorConstruccion=0;
		
		List<RpDjconstruccion> lConstruccion=getCalculoPredialBo().getConstruccion(rpDjPredial,anio);
		
		for(int i=0;i<lConstruccion.size();i++){
			RpDjconstruccion construccion=lConstruccion.get(i);
			
			int intTipoNivelId = construccion.getTipoNivelId();
			int intTipoDepreciacionId = construccion.getClasiDepreciacionId();
			int intNivel = construccion.getNroNivel();

			int intTipoMaterialId = construccion.getMatPredominanteId();
			int intConservacionId = construccion.getConservacionId();
			
			double dblAreaConstruccion=0;
			if(construccion.getAreaConstruccion()!=null)
				dblAreaConstruccion = construccion.getAreaConstruccion().doubleValue();
			
			double dblAreaComunConstruccion=0;
			if(construccion.getAreaComunConstruccion()!=null)//Depende de la Ubicacion de predio que tenga Area comun de construccion
				dblAreaComunConstruccion = construccion.getAreaComunConstruccion().doubleValue();
			
			int intAnnoConstruccion = construccion.getAnnoConstruccion();
			//int flagAreaComun = construccion.getTipoNivelId();
			
			if(intAnnoConstruccion==anio.intValue()){
				//Si el a�o  de proceso es igual al a�o construcion el valor de construccion debe ser cero.
				DtDeterminacionConstruccion determinacionNivel=new DtDeterminacionConstruccion();
				determinacionNivel.setDeterminacionId(determinacion.getDeterminacionId());
				determinacionNivel.setConstruccionId(construccion.getConstruccionId());
				determinacionNivel.setDeterPredioId(deterPredioId);
				//cc: 23/10/2012 cambio de estado  
				//ccdeterminacionNivel.setEstado(Constante.ESTADO_PENDIENTE_REGISTRO);
				determinacionNivel.setEstado(Constante.ESTADO_PENDIENTE);
				//cc: 23/10/2012
				determinacionNivel.setAreaComunConstruida(BigDecimal.valueOf(dblAreaComunConstruccion));
				determinacionNivel.setAreaConstruida(BigDecimal.valueOf(dblAreaConstruccion));
				determinacionNivel.setPorcDepreciacion(BigDecimal.valueOf(0));
				determinacionNivel.setValorAreaComun(BigDecimal.valueOf(0));
				determinacionNivel.setValorAreaConstruida(BigDecimal.valueOf(0));
				determinacionNivel.setValorCategoriaNivel(BigDecimal.valueOf(0));
				determinacionNivel.setValorConstruccion(BigDecimal.valueOf(0));
				determinacionNivel.setValorDepreciacion(BigDecimal.valueOf(0));
				determinacionNivel.setValorIncremento(BigDecimal.valueOf(0));
				determinacionNivel.setValorUnitario(BigDecimal.valueOf(0));
				determinacionNivel.setValorUnitarioDepre(BigDecimal.valueOf(0));
				
				getCalculoPredialBo().guardarDeterminacionConstruccion(determinacionNivel);
			}else{
				//Unidad de medida / Depende de la Ubicacion de predio que tenga Tipo de Unidad de Medida de la Area comun construida
				//1	: m2
				//2	: %
				
				//cchaucca:INI 18/06/2012 no se usara el campo de area de construccion:cambio solicitado
				/*
				if(construccion.getTipoUnidadMedida()!=null&&construccion.getTipoUnidadMedida().equals("2")){
					dblAreaComunConstruccion=dblAreaConstruccion*dblAreaComunConstruccion/100;
				}*/
				//cchaucca:INI 18/06/2012 no se usara el campo de area de construccion:cambio solicitado
				
				double dblValorUnitarioNivel = 0;
				double dblValorUnitarioTotal = 0;
				double dblValorIncremento = 0;
                
				double dblValorUnitarioComponente=0;
                
				//El Valor Unitario Para Muros
                dblValorUnitarioComponente=getCalculoPredialBo().obtenerValorUnitarioComponente(anio,Constante.CATEGORIA_COMPONENTE_MUROS,construccion.getMuros());
                dblValorUnitarioComponente=(new BigDecimal(dblValorUnitarioComponente)).setScale(2, RoundingMode.HALF_UP).doubleValue();
                dblValorUnitarioTotal=dblValorUnitarioTotal+dblValorUnitarioComponente;
                
                //El Valor Unitario Para techo
                dblValorUnitarioComponente=getCalculoPredialBo().obtenerValorUnitarioComponente(anio, Constante.CATEGORIA_COMPONENTE_TECHO,construccion.getTecho());
                dblValorUnitarioComponente=(new BigDecimal(dblValorUnitarioComponente)).setScale(2, RoundingMode.HALF_UP).doubleValue();
                dblValorUnitarioTotal=dblValorUnitarioTotal+dblValorUnitarioComponente;

                //El Valor Unitario Para Pisos
                dblValorUnitarioComponente=getCalculoPredialBo().obtenerValorUnitarioComponente(anio, Constante.CATEGORIA_COMPONENTE_PISOS,construccion.getPisos());
                dblValorUnitarioComponente=(new BigDecimal(dblValorUnitarioComponente)).setScale(2, RoundingMode.HALF_UP).doubleValue();
                dblValorUnitarioTotal=dblValorUnitarioTotal+dblValorUnitarioComponente;
                
                //El Valor Unitario Para Puerta
                dblValorUnitarioComponente=getCalculoPredialBo().obtenerValorUnitarioComponente(anio, Constante.CATEGORIA_COMPONENTE_PUERTAS,construccion.getPuertas());
                dblValorUnitarioComponente=(new BigDecimal(dblValorUnitarioComponente)).setScale(2, RoundingMode.HALF_UP).doubleValue();
                dblValorUnitarioTotal=dblValorUnitarioTotal+dblValorUnitarioComponente;
                
                //El Valor Unitario Para Revestimiento
                dblValorUnitarioComponente=getCalculoPredialBo().obtenerValorUnitarioComponente(anio, Constante.CATEGORIA_COMPONENTE_REVESTIMIENTO,construccion.getRevestimientos());
                dblValorUnitarioComponente=(new BigDecimal(dblValorUnitarioComponente)).setScale(2, RoundingMode.HALF_UP).doubleValue();
                dblValorUnitarioTotal=dblValorUnitarioTotal+dblValorUnitarioComponente;
                
                //El Valor Unitario Para Ba�os
                dblValorUnitarioComponente=getCalculoPredialBo().obtenerValorUnitarioComponente(anio, Constante.CATEGORIA_COMPONENTE_BANNOS,construccion.getBannos());
                dblValorUnitarioComponente=(new BigDecimal(dblValorUnitarioComponente)).setScale(2, RoundingMode.HALF_UP).doubleValue();
                dblValorUnitarioTotal=dblValorUnitarioTotal+dblValorUnitarioComponente;
                
                //El Valor Unitario Para Instalaciones electircas
                dblValorUnitarioComponente=getCalculoPredialBo().obtenerValorUnitarioComponente(anio, Constante.CATEGORIA_COMPONENTE_ELECTRICO,construccion.getElectricos());
                dblValorUnitarioComponente=(new BigDecimal(dblValorUnitarioComponente)).setScale(2, RoundingMode.HALF_UP).doubleValue();
                dblValorUnitarioTotal=dblValorUnitarioTotal+dblValorUnitarioComponente;
                
                //Valor Unitario por metro cuadrado de construcci�n (A)
                dblValorUnitarioNivel=dblValorUnitarioTotal;
                dblValorUnitarioNivel=(new BigDecimal(dblValorUnitarioNivel)).setScale(2, RoundingMode.HALF_UP).doubleValue();
                
                if(intTipoNivelId==Constante.TIPO_NIVEL_PISO){
                	if(intNivel>=Constante.NIVEL_INCREMENTO){
                		dblValorIncremento = dblValorUnitarioNivel * cdblPorcentajeIncremento;
                		dblValorIncremento=(new BigDecimal(dblValorIncremento)).setScale(2, RoundingMode.HALF_UP).doubleValue();
                	}
                }
                //Calculo del Porcentaje y monto de depreciaci�n por metro cuadrado
                
                //Antiguedad de la Construcci�n
                //int intAntiguedad = (anio-1) - intAnnoConstruccion;
                //Corección 10-01-2018: Error al momento de seleccionar la depreciación.
                int intAntiguedad = (anio) - intAnnoConstruccion;
                
                //Porcentaje de Depreciacion (ver tablas de depreciacion)
                //intTipoMaterialId 1	Concreto / 2	Ladrillo/ 3	Adobe, madera, quincha y otros	
                //intConservacionId	descripcion 1	Muy bueno / 2	Bueno/ 3	Regular / 4	Malo
                //intTipoDepreciacionId 1	Casa Habitaci�n / 2	Tiendas Dep�sitos/ 3	Edificios/ 4	Cl�nicas,Hospitales,Cines,Industrias,Talleres,etc

                double dblPorcentajeDepreciacion=getCalculoPredialBo().obtenerPorcentajeDepreciacion(intTipoDepreciacionId, anio, intTipoMaterialId, intConservacionId,intAntiguedad);
                
                //El Monto de depreciaci�n por metro cuadrado (C)
                //C =  (A + B) * P/100
                double dblMontoDepreciado=(dblValorUnitarioNivel + dblValorIncremento)*dblPorcentajeDepreciacion/100;
                dblMontoDepreciado=(new BigDecimal(dblMontoDepreciado)).setScale(2, RoundingMode.HALF_UP).doubleValue();
                
                //Calculo del Valor Unitario Depreciado (D)
                //D =  A + B � C
                double dblValorUnitarioDepreciado = dblValorUnitarioNivel + dblValorIncremento - dblMontoDepreciado;
                dblValorUnitarioDepreciado=(new BigDecimal(dblValorUnitarioDepreciado)).setScale(2, RoundingMode.HALF_UP).doubleValue();
                
                //Valor de construcci�n del nivel (E)
                //E = D * (�rea construida + �rea com�n construida)
                double dblAreaConstruidaValor=0;
                
                //No se usa el campo area comun, se ha creado un nivel Area Comun cuyo comportamiento es igual a los demas niveles
                /*if(flagAreaComun==Constante.TIPO_NIVEL_AREA_COMUN){
                	dblAreaConstruidaValor = 0;
                }else{
                	dblAreaConstruidaValor = dblValorUnitarioDepreciado * dblAreaConstruccion;
                	dblAreaConstruidaValor=(new BigDecimal(dblAreaConstruidaValor)).setScale(2, RoundingMode.HALF_UP).doubleValue();
                }*/
                
                dblAreaConstruidaValor = dblValorUnitarioDepreciado * dblAreaConstruccion;
            	dblAreaConstruidaValor=(new BigDecimal(dblAreaConstruidaValor)).setScale(2, RoundingMode.HALF_UP).doubleValue();
            	
            	//No se usa el campo area comun, se ha creado un nivel Area Comun cuyo comportamiento es igual a los demas niveles
            	/*
                double dblAreaComunValor = dblValorUnitarioDepreciado * dblAreaComunConstruccion;
                dblAreaComunValor=(new BigDecimal(dblAreaComunValor)).setScale(2, RoundingMode.HALF_UP).doubleValue();
                */
            	
            	//No se usa el campo area comun, se ha creado un nivel Area Comun cuyo comportamiento es igual a los demas niveles
            	//double dblValorConstruccionNivel = dblAreaConstruidaValor + dblAreaComunValor;
            	double dblValorConstruccionNivel = dblAreaConstruidaValor;
                dblValorConstruccionNivel=(new BigDecimal(dblValorConstruccionNivel)).setScale(2, RoundingMode.HALF_UP).doubleValue();
                
                dblValorConstruccion = dblValorConstruccion+dblValorConstruccionNivel;
                dblValorConstruccion=(new BigDecimal(dblValorConstruccion)).setScale(2, RoundingMode.HALF_UP).doubleValue();
                
                DtDeterminacionConstruccion determinacionNivel=new DtDeterminacionConstruccion();
                determinacionNivel.setDeterminacionId(determinacion.getDeterminacionId());
                determinacionNivel.setDeterPredioId(deterPredioId);
                determinacionNivel.setConstruccionId(construccion.getConstruccionId());
				//cc:23/10/2012
                //determinacionNivel.setEstado(Constante.ESTADO_PENDIENTE_REGISTRO);
                determinacionNivel.setEstado(Constante.ESTADO_PENDIENTE);
                
				determinacionNivel.setAreaComunConstruida(BigDecimal.valueOf(dblAreaComunConstruccion));
				determinacionNivel.setAreaConstruida(BigDecimal.valueOf(dblAreaConstruccion));
				
				determinacionNivel.setPorcDepreciacion(BigDecimal.valueOf(dblPorcentajeDepreciacion));
				//No se usa el campo area comun, se ha creado un nivel Area Comun cuyo comportamiento es igual a los demas niveles
				//determinacionNivel.setValorAreaComun(BigDecimal.valueOf(dblAreaComunValor));
				determinacionNivel.setValorAreaConstruida(BigDecimal.valueOf(dblAreaConstruidaValor));
				determinacionNivel.setValorCategoriaNivel(BigDecimal.valueOf(intTipoNivelId));
				determinacionNivel.setValorConstruccion(BigDecimal.valueOf(dblValorConstruccionNivel));
				determinacionNivel.setValorDepreciacion(BigDecimal.valueOf(dblMontoDepreciado));
				determinacionNivel.setValorIncremento(BigDecimal.valueOf(dblValorIncremento));
				determinacionNivel.setValorUnitario(BigDecimal.valueOf(dblValorUnitarioTotal));
				determinacionNivel.setValorUnitarioDepre(BigDecimal.valueOf(dblValorUnitarioDepreciado));
				
				determinacionNivel.setEstado(Constante.ESTADO_ACTIVO);
				//cc: determinacionNivel.setFechaRegistro(DateUtil.getCurrentDate());
				//cc: determinacionNivel.setTerminal(Constante.TERMINAL);
				//cc: determinacionNivel.setUsuarioId(Constante.USUARIO_ID);
				
				getCalculoPredialBo().guardarDeterminacionConstruccion(determinacionNivel);
			}			
		}
		return dblValorConstruccion;
	}

	public Double obtenerValorConstruccionConAnioFiscal( RpDjpredial rpDjPredial, DtDeterminacion determinacion,
			Integer deterPredioId, Integer anio) throws Exception {
		double dblValorConstruccionConAnioFiscal = 0;

		/** PARA ALCABALA SE NECESITA EL VALOR DE LA CONSTRUCCION AL ANIO FISCAL */
		List<RpDjconstruccion> lConstruccionConAnioFiscal = getCalculoPredialBo()
				.getConstruccionConAnioFiscal(rpDjPredial, anio);

		for (int i = 0; i < lConstruccionConAnioFiscal.size(); i++) {
			RpDjconstruccion construccion = lConstruccionConAnioFiscal.get(i);

			int intTipoNivelId = construccion.getTipoNivelId();
			int intTipoDepreciacionId = construccion.getClasiDepreciacionId();
			int intNivel = construccion.getNroNivel();

			int intTipoMaterialId = construccion.getMatPredominanteId();
			int intConservacionId = construccion.getConservacionId();

			double dblAreaConstruccion = 0;
			if (construccion.getAreaConstruccion() != null)
				dblAreaConstruccion = construccion.getAreaConstruccion()
						.doubleValue();

			double dblAreaComunConstruccion = 0;
			if (construccion.getAreaComunConstruccion() != null)// Depende de la
																// Ubicacion de
																// predio que
																// tenga Area
																// comun de
																// construccion
				dblAreaComunConstruccion = construccion
						.getAreaComunConstruccion().doubleValue();

			int intAnnoConstruccion = construccion.getAnnoConstruccion();
			// int flagAreaComun = construccion.getTipoNivelId();

			// Unidad de medida / Depende de la Ubicacion de predio que tenga
			// Tipo de Unidad de Medida de la Area comun construida
			// 1 : m2
			// 2 : %

			// cchaucca:INI 18/06/2012 no se usara el campo de area de
			// construccion:cambio solicitado
			/*
			 * if(construccion.getTipoUnidadMedida()!=null&&construccion.
			 * getTipoUnidadMedida().equals("2")){
			 * dblAreaComunConstruccion=dblAreaConstruccion
			 * *dblAreaComunConstruccion/100; }
			 */
			// cchaucca:INI 18/06/2012 no se usara el campo de area de
			// construccion:cambio solicitado

			double dblValorUnitarioNivel = 0;
			double dblValorUnitarioTotal = 0;
			double dblValorIncremento = 0;

			double dblValorUnitarioComponente = 0;

			// El Valor Unitario Para Muros
			dblValorUnitarioComponente = getCalculoPredialBo().obtenerValorUnitarioComponente(anio,	Constante.CATEGORIA_COMPONENTE_MUROS, construccion.getMuros());
			dblValorUnitarioComponente = (new BigDecimal(
					dblValorUnitarioComponente)).setScale(2,
					RoundingMode.HALF_UP).doubleValue();
			dblValorUnitarioTotal = dblValorUnitarioTotal
					+ dblValorUnitarioComponente;

			// El Valor Unitario Para techo
			dblValorUnitarioComponente = getCalculoPredialBo()
					.obtenerValorUnitarioComponente(anio,
							Constante.CATEGORIA_COMPONENTE_TECHO,
							construccion.getTecho());
			dblValorUnitarioComponente = (new BigDecimal(
					dblValorUnitarioComponente)).setScale(2,
					RoundingMode.HALF_UP).doubleValue();
			dblValorUnitarioTotal = dblValorUnitarioTotal
					+ dblValorUnitarioComponente;

			// El Valor Unitario Para Pisos
			dblValorUnitarioComponente = getCalculoPredialBo()
					.obtenerValorUnitarioComponente(anio,
							Constante.CATEGORIA_COMPONENTE_PISOS,
							construccion.getPisos());
			dblValorUnitarioComponente = (new BigDecimal(
					dblValorUnitarioComponente)).setScale(2,
					RoundingMode.HALF_UP).doubleValue();
			dblValorUnitarioTotal = dblValorUnitarioTotal
					+ dblValorUnitarioComponente;

			// El Valor Unitario Para Puerta
			dblValorUnitarioComponente = getCalculoPredialBo()
					.obtenerValorUnitarioComponente(anio,
							Constante.CATEGORIA_COMPONENTE_PUERTAS,
							construccion.getPuertas());
			dblValorUnitarioComponente = (new BigDecimal(
					dblValorUnitarioComponente)).setScale(2,
					RoundingMode.HALF_UP).doubleValue();
			dblValorUnitarioTotal = dblValorUnitarioTotal
					+ dblValorUnitarioComponente;

			// El Valor Unitario Para Revestimiento
			dblValorUnitarioComponente = getCalculoPredialBo()
					.obtenerValorUnitarioComponente(anio,
							Constante.CATEGORIA_COMPONENTE_REVESTIMIENTO,
							construccion.getRevestimientos());
			dblValorUnitarioComponente = (new BigDecimal(
					dblValorUnitarioComponente)).setScale(2,
					RoundingMode.HALF_UP).doubleValue();
			dblValorUnitarioTotal = dblValorUnitarioTotal
					+ dblValorUnitarioComponente;

			// El Valor Unitario Para Ba�os
			dblValorUnitarioComponente = getCalculoPredialBo()
					.obtenerValorUnitarioComponente(anio,
							Constante.CATEGORIA_COMPONENTE_BANNOS,
							construccion.getBannos());
			dblValorUnitarioComponente = (new BigDecimal(
					dblValorUnitarioComponente)).setScale(2,
					RoundingMode.HALF_UP).doubleValue();
			dblValorUnitarioTotal = dblValorUnitarioTotal
					+ dblValorUnitarioComponente;

			// El Valor Unitario Para Instalaciones electircas
			dblValorUnitarioComponente = getCalculoPredialBo()
					.obtenerValorUnitarioComponente(anio,
							Constante.CATEGORIA_COMPONENTE_ELECTRICO,
							construccion.getElectricos());
			dblValorUnitarioComponente = (new BigDecimal(
					dblValorUnitarioComponente)).setScale(2,
					RoundingMode.HALF_UP).doubleValue();
			dblValorUnitarioTotal = dblValorUnitarioTotal
					+ dblValorUnitarioComponente;

			// Valor Unitario por metro cuadrado de construcci�n (A)
			dblValorUnitarioNivel = dblValorUnitarioTotal;
			dblValorUnitarioNivel = (new BigDecimal(dblValorUnitarioNivel))
					.setScale(2, RoundingMode.HALF_UP).doubleValue();

			if (intTipoNivelId == Constante.TIPO_NIVEL_PISO) {
				if (intNivel >= Constante.NIVEL_INCREMENTO) {
					dblValorIncremento = dblValorUnitarioNivel
							* cdblPorcentajeIncremento;
					dblValorIncremento = (new BigDecimal(dblValorIncremento))
							.setScale(2, RoundingMode.HALF_UP).doubleValue();
				}
			}
			// Calculo del Porcentaje y monto de depreciaci�n por metro cuadrado

			// Antiguedad de la Construcci�n
			int intAntiguedad = (anio - 1) - intAnnoConstruccion;

			// Porcentaje de Depreciacion (ver tablas de depreciacion)
			// intTipoMaterialId 1 Concreto / 2 Ladrillo/ 3 Adobe, madera,
			// quincha y otros
			// intConservacionId descripcion 1 Muy bueno / 2 Bueno/ 3 Regular /
			// 4 Malo
			// intTipoDepreciacionId 1 Casa Habitaci�n / 2 Tiendas Dep�sitos/ 3
			// Edificios/ 4 Cl�nicas,Hospitales,Cines,Industrias,Talleres,etc

			double dblPorcentajeDepreciacion = getCalculoPredialBo()
					.obtenerPorcentajeDepreciacion(intTipoDepreciacionId, anio,
							intTipoMaterialId, intConservacionId, intAntiguedad);

			// El Monto de depreciaci�n por metro cuadrado (C)
			// C = (A + B) * P/100
			double dblMontoDepreciado = (dblValorUnitarioNivel + dblValorIncremento)
					* dblPorcentajeDepreciacion / 100;
			dblMontoDepreciado = (new BigDecimal(dblMontoDepreciado)).setScale(
					2, RoundingMode.HALF_UP).doubleValue();

			// Calculo del Valor Unitario Depreciado (D)
			// D = A + B � C
			double dblValorUnitarioDepreciado = dblValorUnitarioNivel
					+ dblValorIncremento - dblMontoDepreciado;
			dblValorUnitarioDepreciado = (new BigDecimal(
					dblValorUnitarioDepreciado)).setScale(2,
					RoundingMode.HALF_UP).doubleValue();

			// Valor de construcci�n del nivel (E)
			// E = D * (�rea construida + �rea com�n construida)
			double dblAreaConstruidaValor = 0;

			// No se usa el campo area comun, se ha creado un nivel Area Comun
			// cuyo comportamiento es igual a los demas niveles
			/*
			 * if(flagAreaComun==Constante.TIPO_NIVEL_AREA_COMUN){
			 * dblAreaConstruidaValor = 0; }else{ dblAreaConstruidaValor =
			 * dblValorUnitarioDepreciado * dblAreaConstruccion;
			 * dblAreaConstruidaValor=(new
			 * BigDecimal(dblAreaConstruidaValor)).setScale(2,
			 * RoundingMode.HALF_UP).doubleValue(); }
			 */

			dblAreaConstruidaValor = dblValorUnitarioDepreciado
					* dblAreaConstruccion;
			dblAreaConstruidaValor = (new BigDecimal(dblAreaConstruidaValor))
					.setScale(2, RoundingMode.HALF_UP).doubleValue();

			// No se usa el campo area comun, se ha creado un nivel Area Comun
			// cuyo comportamiento es igual a los demas niveles
			/*
			 * double dblAreaComunValor = dblValorUnitarioDepreciado *
			 * dblAreaComunConstruccion; dblAreaComunValor=(new
			 * BigDecimal(dblAreaComunValor)).setScale(2,
			 * RoundingMode.HALF_UP).doubleValue();
			 */

			// No se usa el campo area comun, se ha creado un nivel Area Comun
			// cuyo comportamiento es igual a los demas niveles
			// double dblValorConstruccionNivel = dblAreaConstruidaValor +
			// dblAreaComunValor;
			double dblValorConstruccionNivel = dblAreaConstruidaValor;
			dblValorConstruccionNivel = (new BigDecimal(
					dblValorConstruccionNivel)).setScale(2,
					RoundingMode.HALF_UP).doubleValue();

			dblValorConstruccionConAnioFiscal = dblValorConstruccionConAnioFiscal
					+ dblValorConstruccionNivel;
			dblValorConstruccionConAnioFiscal = (new BigDecimal(
					dblValorConstruccionConAnioFiscal)).setScale(2,
					RoundingMode.HALF_UP).doubleValue();

		}

		return dblValorConstruccionConAnioFiscal;
	}
	
	
	/**PARA BACHERO DETERMINACION MASIVA SOLO DETERMINACION PREDIO
	 * ******/
	public void gererarDetPredioMasivoParaAlcabala(Integer personaId1, Integer personaId2, Integer anio){
		ArrayList<Integer> ltsPersonas = new ArrayList<Integer>();
		try {
//			System.out.println("Generacion masiva para alcabala \n");
			int inf=personaId1;
			int sup=500;
			
			while(inf<=sup && sup <= personaId2){
				ltsPersonas = getCalculoPredialBo().getAllPersonaIds(inf, sup);
				
				for(Integer personaId: ltsPersonas){
					generarDeterminacionPredioMasivoParaAlcabala(personaId,anio);
				}
				inf=sup+1;
				sup=sup+500;				
			}
	
			ltsPersonas = null;
			ltsPersonas = getCalculoPredialBo().getAllPersonaIds(inf, personaId2);
			
			for(Integer personaId: ltsPersonas){
				generarDeterminacionPredioMasivoParaAlcabala(personaId,anio);
			}	
						
//			System.out.println("Generacion masiva para alcabala FIN");	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void generarDeterminacionPredioMasivoParaAlcabala(Integer personaId,
			Integer anio) throws Exception {

		ArrayList<RpDjpredial> lDjPredial = getCalculoPredialBo().getAllRpDJpredial(personaId, anio);
		DtDeterminacion determinacion = getCalculoPredialBo().getDtDeterminacion(personaId, anio, Constante.CONCEPTO_PREDIAL, Constante.ESTADO_ACTIVO);
		DtDeterminacionPredio deterPredialt  =null;
		if (determinacion != null && determinacion.getDeterminacionId() > 0) {
			ArrayList<DtDeterminacionPredio> ltsdeterPredio = getCalculoPredialBo().getAllDtDeterminacionPredio(determinacion.getDeterminacionId());

			if (lDjPredial.size() > 0) {
				for (int i = 0; i < lDjPredial.size(); i++) {
//					System.out.println("Para Alcabala INICIO "+personaId );
					try{
						RpDjpredial rpDjPredial = lDjPredial.get(i);
						
						// Guardamos de forma temporal la determinacion del predio
						deterPredialt = new DtDeterminacionPredio();
						deterPredialt = ltsdeterPredio.get(i);
						
						// Calculo : Valor del terreno
						Double dblValorTerreno = obtenerValorTerreno(rpDjPredial, anio);					
	
						// Calculo : Valor de Construccion
						dblValorConstruccionAnioFiscal = obtenerValorConstruccionConAnioFiscal(rpDjPredial, determinacion, deterPredialt.getDeterPredioId(), anio);
	
						// Calculo : Valor de Otras instalaciones
						dblValorOtrasInstalacionesAnioFiscal = obtenerValorOtrasInstalacionesConAnioFiscal( rpDjPredial, determinacion.getDeterminacionId(),
								deterPredialt.getDeterPredioId(), anio);
	
						dblValorTerreno = (new BigDecimal(dblValorTerreno)).setScale(2, RoundingMode.HALF_UP).doubleValue();
						dblValorConstruccionAnioFiscal = (new BigDecimal( dblValorConstruccionAnioFiscal)).setScale(2, RoundingMode.HALF_UP).doubleValue();
						dblValorOtrasInstalacionesAnioFiscal = (new BigDecimal( dblValorOtrasInstalacionesAnioFiscal)).setScale(2, RoundingMode.HALF_UP).doubleValue();
	
						/**
						 * CONSIDERANDO ANIO FISCAL PARA ALCALABA Calculo Valor
						 * Predio
						 */
						// Valor Predio =Valor Terreno + Valor de Construcci�n +
						// Valor otras instalaciones.
						Double dblValorPredioConAnioFiscal = dblValorTerreno + dblValorConstruccionAnioFiscal + dblValorOtrasInstalacionesAnioFiscal;
						dblValorPredioConAnioFiscal = (new BigDecimal( dblValorPredioConAnioFiscal)).setScale(2, RoundingMode.HALF_UP).doubleValue();
	
						/**
						 * CONSIDERANDO ANIO FISCAL PARA ALCALABA segun el % de
						 * propiedad
						 */
						Double dblPorcPropiedad = rpDjPredial.getPorcPropiedad().doubleValue();
						Double dblValorAutovaluoConAnioFiscal = dblValorPredioConAnioFiscal	* dblPorcPropiedad / 100;
						dblValorAutovaluoConAnioFiscal = (new BigDecimal( dblValorAutovaluoConAnioFiscal)).setScale(2, RoundingMode.HALF_UP).doubleValue();
	
						// PARA ALCABALA dblValorAutovaluoConAnioFiscal
						deterPredialt.setValorAutovaluoAlcabala((new BigDecimal( dblValorAutovaluoConAnioFiscal)).setScale(2, RoundingMode.HALF_UP));
	
						getCalculoPredialBo().guardarDeterminacionPredioAlcabala(deterPredialt);
						
//						System.out.println("Para Alcabala FIN "+personaId + " predio: "+ rpDjPredial.getPredioId());
					}
					catch (Exception e) {
					System.out.println("error en la derterminacion id: "+deterPredialt.getDeterPredioId());
						e.printStackTrace();
					} 
				}
			}
		}

	}
	/**FIN
	 * */
	
	public CalculoPredialBoRemote getCalculoPredialBo() {
		return calculoPredialBo;
	}

	public GeneralBoRemote getGeneralBo() {
		return generalBo;
	}
	
	public void setCalculoPredialBo(CalculoPredialBoRemote calculoPredialBo,GeneralBoRemote generalBo) {
		this.calculoPredialBo = calculoPredialBo;
	}
	public void setGeneralBo(GeneralBoRemote generalBo) {
		this.generalBo = generalBo;
	}
	public Double CalcularImpuesto(Integer intPeriodo,Double dblBaseImponible){
		return null;
	}

}