package com.sat.sisat.calculo.predial;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.sat.sisat.common.business.GeneralBoRemote;
import com.sat.sisat.common.dto.TipoUsoDTO;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.persistence.entity.CdDeuda;
import com.sat.sisat.persistence.entity.CdDeudaHistorica;
import com.sat.sisat.persistence.entity.CdDeudaHistoricaPK;
import com.sat.sisat.persistence.entity.DtCercaniaParque;
import com.sat.sisat.persistence.entity.DtCuotaConcepto;
import com.sat.sisat.persistence.entity.DtDeterminacion;
import com.sat.sisat.persistence.entity.DtFechaVencimiento;
import com.sat.sisat.persistence.entity.DtFrecuenciaLimpieza;
import com.sat.sisat.persistence.entity.DtFrecuenciaRecojo;
import com.sat.sisat.persistence.entity.DtGrupoCercania;
import com.sat.sisat.persistence.entity.DtMatrizRecojo;
import com.sat.sisat.persistence.entity.DtMatrizSeguridad;
import com.sat.sisat.persistence.entity.DtZonaSeguridad;
import com.sat.sisat.persistence.entity.DtZonaSeguridadUbicacion;
import com.sat.sisat.persistence.entity.DtZonaSeguridadUso2015;
import com.sat.sisat.persistence.entity.MpPersona;
import com.sat.sisat.persistence.entity.RpCategoriaUso;
import com.sat.sisat.persistence.entity.RpCategoriaUsoSeguridad;
import com.sat.sisat.persistence.entity.RpDjarbitrio;
import com.sat.sisat.persistence.entity.RpDjdireccion;
import com.sat.sisat.persistence.entity.RpDjpredial;
import com.sat.sisat.persistence.entity.RpDjuso;
import com.sat.sisat.persistence.entity.RpOtrosFrente;
import com.sat.sisat.persistence.entity.RpTipoUso;
import com.sat.sisat.predial.business.CalculoPredialBoRemote;
import com.sat.sisat.predial.dto.DeterminacionArbitriosDTO;
import com.sat.sisat.predial.dto.RecojoArbitriosDTO;
import com.sat.sisat.predial.dto.SeguridadArbitriosDTO;

public class DeterminacionArbitriosMasivaOrigen {

	CalculoPredialBoRemote calculoPredialBo;
	GeneralBoRemote generalBo;
	
	HashMap<Integer,RpCategoriaUso> mapRpCategoriaUso=new HashMap<Integer,RpCategoriaUso>();
	HashMap<Integer,RpCategoriaUsoSeguridad> mapRpCategoriaUso2016=new HashMap<Integer,RpCategoriaUsoSeguridad>();
	HashMap<Integer,RpTipoUso> mapRpTipoUso=new HashMap<Integer,RpTipoUso>();
	HashMap<Integer,DtZonaSeguridad> mapDtZonaSeguridad=new HashMap<Integer,DtZonaSeguridad>();
	
	HashMap<Integer,DtMatrizRecojo> mapDtMatrizRecojo=new HashMap<Integer,DtMatrizRecojo>();
	
	HashMap<Integer,Integer> mapCategoriaUsoId2016=new HashMap<Integer,Integer>();
	HashMap<Integer,Integer> mapCategoriaSeguridadUsoId2016=new HashMap<Integer,Integer>();
	
	HashMap<String,DtMatrizSeguridad> mapDtZonaSeguridadUso2016=new HashMap<String,DtMatrizSeguridad>();
	
	/**
	 * Obtiene los valores definidos en la ordenanza 2017
	 * @param calculoPredialBo
	 * @param generalBo
	 * @param anio
	 */
	public DeterminacionArbitriosMasivaOrigen(CalculoPredialBoRemote calculoPredialBo,GeneralBoRemote generalBo,Integer anio){
		try{
			setCalculoPredialBo(calculoPredialBo,generalBo);
			setGeneralBo(generalBo);
			
			//Estas son Categorias de uso que se usaran SOLO en el recojo (NO EN SEGURIDAD)
			
			List<RpCategoriaUso> lstRpCategoriaUso=calculoPredialBo.getAllRpCategoriaUso(anio);//Domesticos,Recreacional,etc.
			Iterator<RpCategoriaUso> it0 = lstRpCategoriaUso.iterator();
	        while (it0.hasNext()){
	        	RpCategoriaUso obj = it0.next();  
	        	mapRpCategoriaUso.put(obj.getCategoriaUsoId(), obj);
	        }
	        
	        //Estas son categorias de uso de seguridad que se usan en el 2017
	        
			List<RpCategoriaUsoSeguridad> lstRpCategoriaUsoSeguridad=calculoPredialBo.getAllRpCategoriaUsoSeguridad(anio);
			Iterator<RpCategoriaUsoSeguridad> its0 = lstRpCategoriaUsoSeguridad.iterator();
	        while (its0.hasNext()){
	        	RpCategoriaUsoSeguridad obj = its0.next();  
	        	mapRpCategoriaUso2016.put(obj.getCategoriaUsoSeguridadId(), obj);
	        }
	        
	        //Para el 2012 y el 2017 son los mismos tipos de usos
	        
			List<RpTipoUso> lstRpTipoUso=calculoPredialBo.getAllRpTipoUso(anio);
			Iterator<RpTipoUso> it5 = lstRpTipoUso.iterator();
	        while (it5.hasNext()){
	        	RpTipoUso obj = it5.next();  
	        	mapRpTipoUso.put(obj.getTipoUsoId(), obj);
	        }
	        
	        //Zonas de seguridad del 2017
			List<DtZonaSeguridad> lstDtZonaSeguridad=calculoPredialBo.getAllDtZonaSeguridad(anio);
			Iterator<DtZonaSeguridad> it6 = lstDtZonaSeguridad.iterator();
	        while (it6.hasNext()){
	        	DtZonaSeguridad obj = it6.next();  
	        	mapDtZonaSeguridad.put(obj.getZonaSeguridadId(), obj);
	        }
	        
	        //Tasa para Recojo
	        List<DtMatrizRecojo> lrecojoUso=calculoPredialBo.getAllDtMatrizRecojo(anio);
			Iterator<DtMatrizRecojo> it3 = lrecojoUso.iterator();
	        while (it3.hasNext()){
	        	DtMatrizRecojo obj = it3.next();  
	        	mapDtMatrizRecojo.put(obj.getCategoriaUsoId(), obj);
	        }
	        
			//RECOJO: CATEGORIAS Y USOS
	        List<TipoUsoDTO> lCategoriaUsoId2016=calculoPredialBo.getCategoriaUso2016All(anio);
	        Iterator<TipoUsoDTO> it4 = lCategoriaUsoId2016.iterator();
	        while (it4.hasNext()){
	        	TipoUsoDTO obj = it4.next();  
	        	mapCategoriaUsoId2016.put(obj.getTipoUsoId(), obj.getCategoriaUsoId());
	        }
	        
	       //SEGURIDAD: CATEGORIAS Y USOS
	        List<TipoUsoDTO> lcategoriaSeguridadTipoUso2016=calculoPredialBo.getCategoriaSeguridadTipoUso2016All(anio);
	        Iterator<TipoUsoDTO> it7 = lcategoriaSeguridadTipoUso2016.iterator();
	        while (it7.hasNext()){
	        	TipoUsoDTO obj = it7.next();  
	        	mapCategoriaSeguridadUsoId2016.put(obj.getTipoUsoId(), obj.getCategoriaUsoId());
	        }
	        
	        //Tasa para Seguridad
	        List<DtMatrizSeguridad> lZonaSeguridadUso2016=calculoPredialBo.getAllDtZonaSeguridadUso2016All(anio);
	        Iterator<DtMatrizSeguridad> itm = lZonaSeguridadUso2016.iterator();
	        while (itm.hasNext()){
	        	DtMatrizSeguridad obj = itm.next();  
	        	mapDtZonaSeguridadUso2016.put(String.valueOf(obj.getCategoriaUsoSeguridadId())+"-"+String.valueOf(obj.getZonaSeguridadId()), obj);
	        }
	        
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * @param calculoPredialBo
	 * @param generalBo
	 * @param anio
	 */
//	public void DeterminacionArbitriosMasivaOrigen_2014(CalculoPredialBoRemote calculoPredialBo,GeneralBoRemote generalBo,Integer anio){
//		try{
//			setCalculoPredialBo(calculoPredialBo,generalBo);
//			setGeneralBo(generalBo);
//			
//			//Estas son Categorias de uso que se usaran SOLO en el recojo (NO EN SEGURIDAD)
//			
//			List<RpCategoriaUso> lstRpCategoriaUso=calculoPredialBo.getAllRpCategoriaUso(anio);
//			Iterator<RpCategoriaUso> it0 = lstRpCategoriaUso.iterator();
//	        while (it0.hasNext()){
//	        	RpCategoriaUso obj = it0.next();  
//	        	mapRpCategoriaUso.put(obj.getCategoriaUsoId(), obj);
//	        }
//	        
//	        //Estas son categorias de uso de seguridad que se usan en el 2014
//	        
//			List<RpCategoriaUsoSeguridad> lstRpCategoriaUsoSeguridad=calculoPredialBo.getAllRpCategoriaUsoSeguridad(anio);
//			Iterator<RpCategoriaUsoSeguridad> its0 = lstRpCategoriaUsoSeguridad.iterator();
//	        while (its0.hasNext()){
//	        	RpCategoriaUsoSeguridad obj = its0.next();  
//	        	mapRpCategoriaUso2014.put(obj.getCategoriaUsoSeguridadId(), obj);
//	        }
//	        
//	        //Para el 2012 y el 2014 son los mismos tipos de usos
//	        
//			List<RpTipoUso> lstRpTipoUso=calculoPredialBo.getAllRpTipoUso(anio);
//			Iterator<RpTipoUso> it5 = lstRpTipoUso.iterator();
//	        while (it5.hasNext()){
//	        	RpTipoUso obj = it5.next();  
//	        	mapRpTipoUso.put(obj.getTipoUsoId(), obj);
//	        }
//	        //Zonas de seguridad del 2014
//	        
//			List<DtZonaSeguridad> lstDtZonaSeguridad=calculoPredialBo.getAllDtZonaSeguridad(anio);
//			Iterator<DtZonaSeguridad> it6 = lstDtZonaSeguridad.iterator();
//	        while (it6.hasNext()){
//	        	DtZonaSeguridad obj = it6.next();  
//	        	mapDtZonaSeguridad.put(obj.getZonaSeguridadId(), obj);
//	        }
//	        
//	        
//	        List<DtMatrizRecojo> lrecojoUso=calculoPredialBo.getAllDtMatrizRecojo(anio);
//			Iterator<DtMatrizRecojo> it3 = lrecojoUso.iterator();
//	        while (it3.hasNext()){
//	        	DtMatrizRecojo obj = it3.next();  
//	        	mapDtMatrizRecojo.put(obj.getCategoriaUsoId(), obj);
//	        }
//			
//	        List<TipoUsoDTO> lCategoriaUsoId2014=calculoPredialBo.getCategoriaUso2014All();
//	        Iterator<TipoUsoDTO> it4 = lCategoriaUsoId2014.iterator();
//	        while (it4.hasNext()){
//	        	TipoUsoDTO obj = it4.next();  
//	        	mapCategoriaUsoId2014.put(obj.getTipoUsoId(), obj.getCategoriaUsoId());
//	        }
//	        
//	        List<TipoUsoDTO> lcategoriaSeguridadTipoUso2014=calculoPredialBo.getCategoriaSeguridadTipoUso2014All();
//	        Iterator<TipoUsoDTO> it7 = lcategoriaSeguridadTipoUso2014.iterator();
//	        while (it7.hasNext()){
//	        	TipoUsoDTO obj = it7.next();  
//	        	mapCategoriaSeguridadUsoId2014.put(obj.getTipoUsoId(), obj.getCategoriaUsoId());
//	        }
//	        
//	        
//	        List<DtZonaSeguridadUso2014> lZonaSeguridadUso2014=calculoPredialBo.getAllDtZonaSeguridadUso2014All();
//	        Iterator<DtZonaSeguridadUso2014> itm = lZonaSeguridadUso2014.iterator();
//	        while (itm.hasNext()){
//	        	DtZonaSeguridadUso2014 obj = itm.next();  
//	        	mapDtZonaSeguridadUso2014.put(String.valueOf(obj.getCategoriaUsoSeguridadId())+"-"+String.valueOf(obj.getZonaSeguridadId()), obj);
//	        }
//	        
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//	}
	
	/**
	 * Funcion para el calculo masivo de arbitrios
	 */
	public void generarDeterminacionArbitrios2016(Integer personaId,Integer anio)throws Exception{
		//SELECT a FROM RpDjpredial a WHERE a.estado='1' AND  a.personaId=:p_persona_id AND a.flagDjAnno=:p_flag_dj_anno AND a.annoDj=:p_periodo and a.tipoPredio='U' ORDER BY a.djId DESC
		ArrayList<RpDjpredial> lDjPredial=getCalculoPredialBo().getAllRpDJpredialArb(personaId,anio);
		if(lDjPredial.size()>0){
			for(int i=0;i<lDjPredial.size();i++){
				RpDjpredial rpDjPredial=lDjPredial.get(i);
				if(rpDjPredial.getTipoPredio().equals(Constante.TIPO_PREDIO_URBANO)){
					//(2)SELECT m FROM DtDeterminacion m WHERE m.estado=:p_estado AND m.personaId=:p_persona_id AND m.djreferenciaId=:p_djid AND m.annoDeterminacion=:p_periodo AND m.conceptoId=:p_concepto_id
					DtDeterminacion determinacionAnt=getCalculoPredialBo().getAllDtDeterminacionArbitrio(personaId,rpDjPredial.getDjId(),anio,Constante.CONCEPTO_ARBITRIO,Constante.ESTADO_ACTIVO);
					if(determinacionAnt!=null){
						//System.out.println("Existe determinacion arbitrios "+anio+" para persona_id "+personaId +" dj "+rpDjPredial.getDjId());
						
					}else{
						//System.out.println(" *** determinacion arbitrios persona "+personaId+ " predio "+rpDjPredial.getDjId());
//						calculoArbitrios2016(rpDjPredial,anio,null);	
						calculoArbitrios2017(rpDjPredial,anio,null);	
					}
				}else{
					//System.out.println(" *** No se realiza la determinacion arbitrios por ser predio Rustico persona "+personaId+ " predio "+rpDjPredial.getDjId());
				}
			}
		}
	}
	
	private DeterminacionArbitriosDTO calculoArbitrios2017(RpDjpredial djpredial,Integer anio,List<DtDeterminacion> lDeterminacionAnt)throws Exception{
		DeterminacionArbitriosDTO calculo=null;
		try{
			Double TotalArbitrio=Double.valueOf(0);				
			
			RpDjdireccion direccion=calculoPredialBo.getRpDjDireccion(djpredial.getDjId());
			if(direccion!=null&&direccion.getUbicacionId()>Constante.RESULT_PENDING){
				
				Boolean flagEsCochera = Boolean.FALSE;
				Boolean flagEsServicioEstacionamiento = Boolean.FALSE; 
				Boolean flagEsTerrenoSinConstruir = Boolean.FALSE;
				Boolean flagEsAlmacenDeposito = Boolean.FALSE; /**Ordenanza Nro xx CMPC del 2017*/
				Boolean flagEsVivienda = Boolean.FALSE; /**Ordenanza Nro xxx CMPC del 2017*/
				Boolean flagEsPh = Boolean.FALSE;
				
				int cantUsos = 0;
				String msg ="";
				
				RpDjarbitrio arbitrio=calculoPredialBo.getRpDjarbitrio(djpredial.getDjId());
				List<RpDjuso> luso=calculoPredialBo.getAllRpDjuso(arbitrio.getDjarbitrioId());
			
				/**De acuerdo a la ordenanza Nro xx CMPC del 2017, en las Situaciones Especiales - DECIMO TERCERA, indica que las cocheras,tendales,depósitos y otros predios, con partidas registrales independientes 
				 * pero comprendidos bajo el régimen de propiedad horizontal de un predio CASA/HABITACION  no estan afectos a los arbitrios municipales.
				 * */
		        Iterator<RpDjuso> itUsos = luso.iterator();  
			       
		        while (itUsos.hasNext()){
		        	RpDjuso uso=itUsos.next();
		        	
		        	//La cochera es igual un predio en edificio, se debe registrar como tal, No genera arbitrio para la Cochera		        	
		        	if(uso.getTipoUsoId()==Constante.TIPO_USO_COCHERA_DOMICILIO_NUEVO){
		        		flagEsCochera = Boolean.TRUE;
		        		cantUsos++;
		        	}
//		        	else if(uso.getTipoUsoId()==Constante.TIPO_USO_SERVICIO_DE_ESTACIONAMIENTO_NUEVO){
//		        		flagEsServicioEstacionamiento = Boolean.TRUE;
//		        		cantUsos++;
//		        	}
		        	else if(uso.getTipoUsoId()==Constante.TIPO_USO_TERRENO_SIN_CONSTRUIR_NUEVO){
		        		flagEsTerrenoSinConstruir = Boolean.TRUE;
		        		cantUsos++;
		        	}else if(uso.getTipoUsoId()==Constante.TIPO_USO_ALMACEN_DEPOSITO_NUEVO){
		        		flagEsAlmacenDeposito = Boolean.TRUE;
			        		cantUsos++;
		        	}else if(uso.getTipoUsoId()==Constante.TIPO_USO_VIVIENDA){
		        		flagEsVivienda = Boolean.TRUE;
		        		cantUsos++;
		        	}else{
		        		cantUsos++;
		        	}
		        }
		        
		        if((djpredial.getUbicacionPredioId()==Constante.UBICACION_PREDIO_EDIFICIO) //Edificio
				           || (djpredial.getUbicacionPredioId()==Constante.UBICACION_PREDIO_PREDIO_EN_QUINTA)//Predio con Áreas Comunes (Predio Independiente, En Quinta, En Solar, En Callejón, otros)
				           || (djpredial.getUbicacionPredioId()==Constante.UBICACION_PREDIO_PREDIO_EN_EDIFICIO)//Predio en Edificio
				           || (djpredial.getUbicacionPredioId()==Constante.UBICACION_PREDIO_PARTE_DEL_PREDIO) )//Parte del predio (PREDIOS SEPARADOS POR EL USO)
				        {
				        	if(flagEsCochera==Boolean.TRUE && cantUsos==1 ){
				        		//System.out.println("Hoa");
					        		flagEsPh = Boolean.TRUE;
				        	}else if(flagEsAlmacenDeposito==Boolean.TRUE && cantUsos==1 ){
				        		//System.out.println("Hoa2");
				        		 	flagEsPh = Boolean.TRUE;
				        	}
			     }
		 
		        //(1)Calculo arbitrio de Limpieza 2017
	        	DtFrecuenciaLimpieza limpieza = new DtFrecuenciaLimpieza();
	        	limpieza = calculoPredialBo.getDtFrecuenciaLimpieza(direccion.getUbicacionId(),anio);
		        Double arbitrioLimpiezaPreSubven = 0.0;
		        Double arbitrioLimpieza = 0.0;	
		        int limpiezaOtrosFrentesFrecuencia = 0;
		        
//		        if(! (flagEsCochera && cantUsos == 1) ){
//					if( !(flagEsServicioEstacionamiento && cantUsos == 1) ){
//						if(!(flagEsAlmacenDeposito && cantUsos==1)){
		        //System.out.println("limpieza :"+djpredial.getDjId()+" => " + flagEsPh);			
		        if(!(flagEsPh)) {
						    if( limpieza != null ){
					        	arbitrioLimpiezaPreSubven = limpieza.getTasaMlAnual().doubleValue()*djpredial.getFrente().doubleValue();				
								
					        	/** Se aplica la subvencion de acuerdo a la Ordenanza Nro xx-2016 :: paga el 75% del costo para Barrido de Calles*/
								//arbitrioLimpieza = arbitrioLimpiezaPreSubven*(0.75);
								arbitrioLimpieza = arbitrioLimpiezaPreSubven*(0.80);
					        }else{
					        	msg = msg.concat("Sin valor de Limpieza para ubicacionId: ").concat(direccion.getUbicacionId().toString()).concat(", anio: ").concat(anio.toString()).concat(". ");
					        }
					        
					        //BARRIDO para otros frentes si los tuviera		
					        DtFrecuenciaLimpieza limpiezaOtrosFrentes = new DtFrecuenciaLimpieza();
							Double arbitrioLimpiezaPreSubvenOtrosFrentes = 0.0;
							Double arbitrioLimpiezaOtrosFrentes = 0.0;
							
							Double totalArbitrioLimpiezaPreSubvenOtrosFrentes = Double.valueOf(0);
							Double totalArbitrioLimpiezaOtrosFrentes = Double.valueOf(0);	
							
							List<RpOtrosFrente> lstOtrosFrentes = getCalculoPredialBo().obtenerOtrosFrentes(djpredial.getDjId());
							
							for(RpOtrosFrente frente : lstOtrosFrentes){
							
								limpiezaOtrosFrentes = calculoPredialBo.getDtFrecuenciaLimpieza(frente.getUbicacionId(),anio);
								if(limpiezaOtrosFrentes != null){
									arbitrioLimpiezaPreSubvenOtrosFrentes = limpiezaOtrosFrentes.getTasaMlAnual().doubleValue()*frente.getFrente().doubleValue();
									totalArbitrioLimpiezaPreSubvenOtrosFrentes = totalArbitrioLimpiezaPreSubvenOtrosFrentes + arbitrioLimpiezaPreSubvenOtrosFrentes;
									limpiezaOtrosFrentesFrecuencia = limpiezaOtrosFrentesFrecuencia +  limpiezaOtrosFrentes.getFrecuencia();
								}
								//arbitrioLimpiezaOtrosFrentes = arbitrioLimpiezaPreSubvenOtrosFrentes*(0.75);    //1.Arbitrio de Barrido de Calles: 75% del total del costo real determinado en el Informe Técnico mediante la presente Ordenanza.
								arbitrioLimpiezaOtrosFrentes = arbitrioLimpiezaPreSubvenOtrosFrentes*(0.80);    //1.Arbitrio de Barrido de Calles: 75% del total del costo real determinado en el Informe Técnico mediante la presente Ordenanza.
								
								totalArbitrioLimpiezaOtrosFrentes = totalArbitrioLimpiezaOtrosFrentes + arbitrioLimpiezaOtrosFrentes;
							}
							
							//ARBITRIOS PARA BARRIDO INCLUYENDO OTROS FRENTES SI LOS TUVIERA
							arbitrioLimpieza = arbitrioLimpieza  + totalArbitrioLimpiezaOtrosFrentes;
							arbitrioLimpiezaPreSubven = arbitrioLimpiezaPreSubven + totalArbitrioLimpiezaPreSubvenOtrosFrentes;
							
							TotalArbitrio = TotalArbitrio + arbitrioLimpieza;
							
							//System.out.println("arbitrioLimpieza :" + arbitrioLimpieza);
							
							//guardarDeterminacion(djpredial,arbitrioLimpiezaPreSubven,arbitrioLimpieza,Constante.SUB_CONCEPTO_ARBITRIOS_BARRIDO,anio,null,lDeterminacionAnt);
							//AGREGANDO DJ USOS //
							guardarDeterminacion(djpredial,arbitrioLimpiezaPreSubven,arbitrioLimpieza,Constante.SUB_CONCEPTO_ARBITRIOS_BARRIDO,anio,null,lDeterminacionAnt,null);
//						}
//					}	
//		        }
		        }
		      
				
				//(2)Calculo arbitrio de Recojo (en el 2017 no se usa la frecuencia de recojo)
		    		/**De acuerdo a la ordenanza Nro xx CMPC del 2017, en las Situaciones Especiales - DECIMO TERCERA, indica que las cocheras,tendales,depósitos y otros predios, con partidas registrales independientes 
		    		 * pero comprendidos bajo el régimen de propiedad horizontal de un predio CASA/HABITACION  no estan afectos a los arbitrios municipales.
		    		 * */
		        
					/** De acuerdo a la ordenanza Nro xx CMPC del 2017, en las Situaciones Especiales - DECIMO TERCERA, indica que, los terrenos sin construir 
					 * cancelaran unicamente el arbitrio de Barrido de Calles y Seguridad Ciudadana.
					 */
					Double arbitrioRecojo=Double.valueOf(0);
					Double arbitrioRecojoPreSubven=Double.valueOf(0);
					List<RecojoArbitriosDTO> lRecojoArbitrio=new LinkedList<RecojoArbitriosDTO>();
					
//					 if(! (flagEsCochera && cantUsos == 1) ){
//							if( !(flagEsServicioEstacionamiento && cantUsos == 1) ){
//								if(!(flagEsAlmacenDeposito && cantUsos==1)){
								
					//La frecuencia de recojo para el anio 2017 depende del uso no de la frecuencia
					DtFrecuenciaRecojo frecuenciaRecojo=calculoPredialBo.getDtFrecuenciaRecojo(direccion.getUbicacionId(),anio);
					if(frecuenciaRecojo!=null&&frecuenciaRecojo.getFrecuencia()>0){
						
						if(djpredial.getUbicacionPredioId().compareTo(Constante.UBICACION_PREDIO_TERRENO_SIN_CONSTRUIR)!=0 && !flagEsTerrenoSinConstruir){
						        Iterator<RpDjuso> it = luso.iterator();  
						       
						        while (it.hasNext()){
						        	RpDjuso uso=it.next();
						        	//La cochera es igual un predio en edificio, se debe registrar como tal, No genera arbitrio para la Cochera
						        	//Los usos de RpCategoriaUso se usan en los Usos de Recojo (NO EN SEGURIDAD)
						        	if(uso.getTipoUsoId()!=Constante.TIPO_USO_COCHERA_DOMICILIO_NUEVO){
						        		if(uso.getTipoUsoId()!=Constante.TIPO_USO_ALMACEN_DEPOSITO_NUEVO){
						        			
						        		//System.out.println("djpredial.getDjId() :" + djpredial.getDjId());
						        		
							        	RpTipoUso tipoUso=mapRpTipoUso.get(uso.getTipoUsoId());
							        	//Debemos obtener las Categorias correspondientes al 2016 de la tabla::
							        	Integer categoriaUsoId2016=mapCategoriaUsoId2016.get(tipoUso.getTipoUsoId());
							        	
							        	DtMatrizRecojo matrizRecojo = mapDtMatrizRecojo.get(categoriaUsoId2016);
							        	
							        	//PARA LA ORDENANZA 407-CMPC ARTICULO NRO 16
//							        	MpPersona persona = calculoPredialBo.getFindPersona(djpredial.getPersonaId());
//							        	if(persona.getFlagEstatal() != null && persona.getFlagEstatal().compareTo(Constante.ESTADO_ACTIVO)==0){
//							        		// matrizRecojoCH  = traemos hardcode el valor de la recojo en casahabitacion
//							        		DtMatrizRecojo matrizRecojoCasaHabitacion = mapDtMatrizRecojo.get(Constante.CATEGORIA_USO_DOMESTICO);
//							        		
//							        		// comparacion entre matrizRecojoCH <= matrizRecojo ? matrizRecojo1: matrizRecojo
//							        		matrizRecojo  = matrizRecojo.getCostoM2Anual().compareTo(matrizRecojoCasaHabitacion.getCostoM2Anual()) > 0 ? 
//							        							matrizRecojoCasaHabitacion: matrizRecojo ;    
//							        	}					        	
//							        	
							        	if(matrizRecojo!=null){
							        		
							        		Double arbitrioRecojoUsoPreSubven=uso.getAreaUso().doubleValue()*matrizRecojo.getCostoM2Anual().doubleValue();
							        		arbitrioRecojoPreSubven=arbitrioRecojoPreSubven+arbitrioRecojoUsoPreSubven;
							        		/** Se aplica la subvencion de acuerdo a la ordenanza Nro xx CMPC del 2016 :: subvenciona el 75% del costo para Recolección de Residuos Sólidos*/
							        		
							        		//Double arbitrioRecojoUso=arbitrioRecojoUsoPreSubven*(0.75); 			 //2.	Arbitrio de Recolección de Residuos Sólidos: 75% del total del costo real determinado en el Informe Técnico mediante la presente Ordenanza.
							        		Double arbitrioRecojoUso=arbitrioRecojoUsoPreSubven*(0.80); 			 //2.	Arbitrio de Recolección de Residuos Sólidos: 75% del total del costo real determinado en el Informe Técnico mediante la presente Ordenanza.
							        		
							        		/** Se aplica la subvencion de acuerdo a la ordenanza Nro xx CMPC del 2016 :: subvenciona el 75% del costo para Recolección de Residuos Sólidos*/
							        		arbitrioRecojo=arbitrioRecojo+arbitrioRecojoUso;
							        	
								        	RecojoArbitriosDTO recojo=new RecojoArbitriosDTO();
								        	recojo.setUso(((RpCategoriaUso)mapRpCategoriaUso.get(categoriaUsoId2016)).getDescripcion());
								        	recojo.setCategoriaUsoId(categoriaUsoId2016);
								        	recojo.setAreaM2(BigDecimal.valueOf(uso.getAreaUso().doubleValue()));
								        	recojo.setCostoM2Anual(BigDecimal.valueOf(matrizRecojo.getCostoM2Anual().doubleValue()));
								        	recojo.setMontoRecojoUsoPreSubven(BigDecimal.valueOf(arbitrioRecojoUsoPreSubven));
								        	recojo.setMontoRecojoUso(BigDecimal.valueOf(arbitrioRecojoUso));
								        	lRecojoArbitrio.add(recojo);
								        	
								        	
								        	//System.out.println("arbitrioRecojoUso :" + arbitrioRecojoUso);
								        	//guardarDeterminacion(djpredial,arbitrioRecojoUsoPreSubven,arbitrioRecojoUso,Constante.SUB_CONCEPTO_ARBITRIOS_RECOJO,anio,tipoUso.getTipoUsoId(),lDeterminacionAnt);
								        	//AGREGANDO DJ USOS //
								        	guardarDeterminacion(djpredial,arbitrioRecojoUsoPreSubven,arbitrioRecojoUso,Constante.SUB_CONCEPTO_ARBITRIOS_RECOJO,anio,tipoUso.getTipoUsoId(),lDeterminacionAnt,uso.getDjusoId());
							        	}
						           	 }
						          }
						        }
					        TotalArbitrio=TotalArbitrio+arbitrioRecojo;
						}
					}
//				 } } }
		        //(3)Calculo arbitrio Parques [2017 UBICACION]
					/** De acuerdo a la ordenanza Nro xx CMPC del 2017, en las Situaciones Especiales - DECIMO TERCERA, indica que las cocheras,tendales,depósitos y otros predios, con partidas registrales independientes 
					  * pero comprendidos bajo el régimen de propiedad horizontal de un predio CASA/HABITACION  no estan afectos a los arbitrios municipales.
					  * */

					/** De acuerdo a la ordenanza Nro xx CMPC del 2017, en las Situaciones Especiales - DECIMO TERCERA, indica que, los terrenos sin construir 
					 * cancelaran unicamente el arbitrio de Barrido de Calles y Seguridad Ciudadana.
					 */
					
					/**
			         	28	Frente a Parques											32	Cerca a Parques
						29	Frente a Plazuelas											33	Cerca a Plazuelas
						30	Frente a Jardineras										    34	Cerca a Jardineras
						31	Frente a Jardineras de Acceso								35	Cerca a Jardineras de Acceso
	
						36	Lejos a Parques
						37	Lejos a Plazuelas
						38	Lejos a Jardineras
						39	Lejos a Jardineras de Acceso
						
						40	Predios ubicados a mas de seis manzanas de Areas Verdes - inafectos
			         */
					
					Double arbitrioParquesPreSubven=Double.valueOf(0);
			        Double arbitrioParquesAntesBeneficio=Double.valueOf(0);
			        Double arbitrioParques=Double.valueOf(0);
			        DtGrupoCercania grupoCercania = null;
			        
				if (djpredial.getUbicacionPredioId().compareTo(Constante.UBICACION_PREDIO_TERRENO_SIN_CONSTRUIR) != 0 && !flagEsTerrenoSinConstruir ) {
					
//					if( !(flagEsCochera && cantUsos == 1) ){
						
//						if( !(flagEsServicioEstacionamiento && cantUsos == 1) ){
							
//							if(!(flagEsAlmacenDeposito && cantUsos==1)){
					if(!(flagEsPh)) {
								DtCercaniaParque cercania = calculoPredialBo.getDtCercaniaParque(direccion.getUbicacionId(), anio);
								
								if(cercania != null){
									// Esto pasa cuando el predio no colinda con parques
									if (cercania.getGrupoCercaniaId() != null) { 
										grupoCercania = calculoPredialBo.getDtGrupoCercania(cercania.getGrupoCercaniaId());
										/**
										 * Se aplica la subvencion de acuerdo a la ordenanza Nro xx CMPC del 2016 :: Parques y Jardines
										 */
										arbitrioParquesPreSubven = grupoCercania.getCostoAnual().doubleValue();
										//arbitrioParquesAntesBeneficio = arbitrioParquesPreSubven * (0.6);//ii.	Arbitrio de Mantenimiento de Parques y Jardines: 40% del total del costo real determinado mediante la presente Ordenanza.
										arbitrioParquesAntesBeneficio = arbitrioParquesPreSubven * grupoCercania.getPorcentajeSubvencion().doubleValue();
										/**
										 * Se aplica la subvencion de acuerdo a la ordenanza Nro xx CMPC del 2016 :: Parques y Jardines
										 */

										arbitrioParques = arbitrioParquesAntesBeneficio;
										/**
										 * De acuerdo a los beneficios de arbitrios municipales del 2015:Fin
										 */
										
										//guardarDeterminacion(djpredial,arbitrioParquesPreSubven, arbitrioParques,Constante.SUB_CONCEPTO_ARBITRIOS_PARQUES,anio, null, lDeterminacionAnt);
										//AGREGANDO DJ USOS //
										guardarDeterminacion(djpredial,arbitrioParquesPreSubven, arbitrioParques,Constante.SUB_CONCEPTO_ARBITRIOS_PARQUES,anio, null, lDeterminacionAnt,null);
										
										TotalArbitrio = TotalArbitrio + arbitrioParques;		
									}
								}else{
									msg = msg.concat("Sin valor de Parques en DtCercaniaParque para ubicacionId: ").concat(direccion.getUbicacionId().toString()).concat(", anio: ").concat(anio.toString()).concat(". ");
								}
//						}
//					 }
//				   }
					}
				 }
		
		        //(4)Calculo arbitrio de Seguridad [2017 UBICACION-USO]
	    		/**De acuerdo a la ordenanza Nro xx CMPC del 2017, en las Situaciones Especiales - DECIMO TERCERA, indica que las cocheras,tendales,depósitos y otros predios, con partidas registrales independientes 
	    		 * pero comprendidos bajo el régimen de propiedad horizontal de un predio CASA/HABITACION  no estan afectos a los arbitrios municipales.
	    		 * */
	        
				/** De acuerdo a la ordenanza Nro xx CMPC del 2017, en las Situaciones Especiales - DECIMO TERCERA, indica que, los terrenos sin construir 
				 * cancelaran unicamente el arbitrio de Barrido de Calles y Seguridad Ciudadana.
				 */
			        Double arbitrioSeguridad=Double.valueOf(0);
			        Double arbitrioSeguridadPreSubven=Double.valueOf(0);
			        Double arbitrioSeguridadAntesBeneficios=Double.valueOf(0);
			        List<SeguridadArbitriosDTO> lSeguridadArbitrio=new LinkedList<SeguridadArbitriosDTO>();
									
					DtZonaSeguridadUbicacion zonaSeguridad=calculoPredialBo.getDtZonaSeguridadUbicacion(direccion.getUbicacionId(),anio);
					
//					if (djpredial.getUbicacionPredioId().compareTo(Constante.UBICACION_PREDIO_TERRENO_SIN_CONSTRUIR) != 0 && !flagEsTerrenoSinConstruir ) {
						
//						if( !(flagEsCochera && cantUsos == 1) ){
							
//							if( !(flagEsServicioEstacionamiento && cantUsos == 1) ){
								
//								if(!(flagEsAlmacenDeposito && cantUsos==1)){
					
						        Iterator<RpDjuso> it = luso.iterator();
						        while (it.hasNext()){
						        	//System.out.println("SEGURIDAD");
						        	RpDjuso uso=it.next();
						        	//La cochera es igual un predio en edificio, se debe registrar como tal, No genera arbitrio para la Cochera
						        	if(uso.getTipoUsoId()!=Constante.TIPO_USO_COCHERA_DOMICILIO_NUEVO){
						        		if(uso.getTipoUsoId()!=Constante.TIPO_USO_ALMACEN_DEPOSITO_NUEVO){
							        	RpTipoUso tipoUso=mapRpTipoUso.get(uso.getTipoUsoId());
							        	//System.out.println("DJID" + djpredial.getDjId());
							        	if(tipoUso != null){
							        		//System.out.println("TIPO USO");
							        		//Debemos obtener las Categorias correspondientes a SEGURIDAD DEL 2016 de la tabla rp_categoria_seguridad::INICIO
							        		Integer categoriaSeguridadTipoUso2016Id=mapCategoriaSeguridadUsoId2016.get(tipoUso.getTipoUsoId());
	
							        		DtMatrizSeguridad seguridadUso=mapDtZonaSeguridadUso2016.get(String.valueOf(categoriaSeguridadTipoUso2016Id)+"-"+String.valueOf(zonaSeguridad.getZonaSeguridadId()));
								        	/** Se aplica la subvencion de acuerdo a la ordenanza Nro xx CMPC del 2017 :: Seguridad Ciudadana */
								        	Double arbitrioSeguridadUsoPrevSubven=seguridadUso.getTasaAnual().doubleValue();
								        	arbitrioSeguridadPreSubven=arbitrioSeguridadPreSubven+arbitrioSeguridadUsoPrevSubven;
								        	
								        	Double arbitrioSeguridadUsoAntesBeneficio=arbitrioSeguridadUsoPrevSubven; //Sin Subvencion
								        	/** Se aplica la subvencion de acuerdo a la ordenanza Nro xx CMPC del 2017 :: Seguridad Ciudadana*/
								        	arbitrioSeguridadAntesBeneficios=arbitrioSeguridadAntesBeneficios.doubleValue()+arbitrioSeguridadUsoAntesBeneficio;
								        	
								        	Double arbitrioSeguridadUso=Double.valueOf(0);
								        	/** De acuerdo a los beneficios de arbitrios municipales del 2017:Inicio */
								        	arbitrioSeguridadUso=arbitrioSeguridadUsoAntesBeneficio;
								        	/** De acuerdo a los beneficios de arbitrios municipales del 2017:Fin */
								        	arbitrioSeguridad=arbitrioSeguridad.doubleValue()+arbitrioSeguridadUso;
								        	
								        	SeguridadArbitriosDTO seguridad=new SeguridadArbitriosDTO();
								        	seguridad.setUso(((RpCategoriaUsoSeguridad)mapRpCategoriaUso2016.get(categoriaSeguridadTipoUso2016Id)).getDescripcion());
								        	seguridad.setTasaAnualPreSubven(BigDecimal.valueOf(arbitrioSeguridadUsoPrevSubven));
								        	seguridad.setTasaAnualAntesBeneficios(BigDecimal.valueOf(arbitrioSeguridadUsoAntesBeneficio));
								        	seguridad.setTasaAnual(BigDecimal.valueOf(arbitrioSeguridadUso));
								        	seguridad.setCategoriaUsoId(categoriaSeguridadTipoUso2016Id);
								        	lSeguridadArbitrio.add(seguridad);
								        	
								        	//System.out.println("arbitrioSeguridadUso : "+arbitrioSeguridadUso);
								        	
								        	//guardarDeterminacion(djpredial,arbitrioSeguridadUsoPrevSubven,arbitrioSeguridadUso,Constante.SUB_CONCEPTO_ARBITRIOS_SEGURIDAD,anio,tipoUso.getTipoUsoId(),lDeterminacionAnt);
								        	//AGREGANDO DJ USOS //
								        	guardarDeterminacion(djpredial,arbitrioSeguridadUsoPrevSubven,arbitrioSeguridadUso,Constante.SUB_CONCEPTO_ARBITRIOS_SEGURIDAD,anio,tipoUso.getTipoUsoId(),lDeterminacionAnt,uso.getDjusoId());
								        	
							        	}else{
							        		msg = msg.concat("Sin valor de Uso: ").concat(anio.toString()).concat(". ");
							        	}
						        	 }
						        	}
						        }
						        TotalArbitrio=TotalArbitrio+arbitrioSeguridad;
//						}}}
//				}
		    	
			    //(6) Determinacion de arbitrios generales
		    	calculo=new DeterminacionArbitriosDTO();
		    	//--Datos del calculo para su validacion
		    	calculo.setArbitrioLimpieza(BigDecimal.valueOf(arbitrioLimpieza));
		        calculo.setArbitrioParques(BigDecimal.valueOf(arbitrioParques));
		        calculo.setArbitrioRecojo(BigDecimal.valueOf(arbitrioRecojo));
		        calculo.setArbitrioSeguridad(BigDecimal.valueOf(arbitrioSeguridad));
		        calculo.setFrecuenciaLimpieza(limpieza.getFrecuencia());
		        calculo.setTasaMlAnualLimpieza(BigDecimal.valueOf(limpieza.getTasaMlAnual().doubleValue()));
		        calculo.setFrenteMlLimpieza(BigDecimal.valueOf(djpredial.getFrente().doubleValue()));
		        
		        /** Se aplica la subvencion de acuerdo a la ordenanza 2017*/
		        calculo.setArbitrioLimpiezaAntesSubvencion(BigDecimal.valueOf(arbitrioLimpiezaPreSubven));
		        calculo.setArbitrioRecojoAntesSubvencion(BigDecimal.valueOf(arbitrioRecojoPreSubven));
		        calculo.setArbitrioSeguridadAntesBeneficio(BigDecimal.valueOf(arbitrioSeguridadPreSubven));
		        calculo.setArbitrioParquesAntesSubvencion(BigDecimal.valueOf(arbitrioParquesPreSubven));
		        
		        /** De acuerdo a los beneficios de arbitrios municipales del 2017 */
		        calculo.setArbitrioSeguridadAntesBeneficio(BigDecimal.valueOf(arbitrioSeguridadAntesBeneficios.doubleValue()));
	        	calculo.setArbitrioParquesAntesBeneficio(BigDecimal.valueOf(arbitrioParquesAntesBeneficio.doubleValue()));
	        	
		    	//Calculo arbitrio de Recojo
	        	calculo.setlRecojoArbitrio(lRecojoArbitrio);
		        
		    	//Calculo arbitrio Parques
		        if(grupoCercania != null){
		        	calculo.setGrupoCercaniaParques(grupoCercania.getDescipcionCorta());
		        	calculo.setCostoAnualParques(BigDecimal.valueOf(grupoCercania.getCostoAnual().doubleValue()));
		        }else{
		        	calculo.setGrupoCercaniaParques(null);
		        	calculo.setCostoAnualParques(BigDecimal.valueOf(0));
		        }
		    	
		        //Calculo arbitrio de Seguridad
		    	DtZonaSeguridad dtZonaSeguridad=mapDtZonaSeguridad.get(zonaSeguridad.getZonaSeguridadId());
		    	calculo.setZonaSeguridad(dtZonaSeguridad.getDescripcionCorta());
		    	calculo.setlSeguridadArbitrio(lSeguridadArbitrio);
		    	//General
		    	calculo.setDjId(djpredial.getDjId());
		    	calculo.setAnnoDeterminacion(anio);
		    	calculo.setDireccionCompleta(direccion.getDireccionCompleta());
		    	//--
		    	if(grupoCercania != null){
		    		calculo.setGrupoCercaniaParquesId(grupoCercania.getGrupoCercaniaId());
		    	}else{
		    		calculo.setGrupoCercaniaParquesId(null);
		    	}
		    	
		    	calculo.setFrecuenciaLimpiezaId(limpieza.getFrecuenciaLimpiezaId());
		    	calculo.setZonaSeguridadId(dtZonaSeguridad.getZonaSeguridadId());
		    	
		    	//getCalculoPredialBo().guardarCalculoArbitrios(calculo);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
        return calculo;
	}
	
	
	private DeterminacionArbitriosDTO calculoArbitrios2016(RpDjpredial djpredial,Integer anio,List<DtDeterminacion> lDeterminacionAnt)throws Exception{
		DeterminacionArbitriosDTO calculo=null;
		try{
			Double TotalArbitrio=Double.valueOf(0);				
			
			RpDjdireccion direccion=calculoPredialBo.getRpDjDireccion(djpredial.getDjId());
			if(direccion!=null&&direccion.getUbicacionId()>Constante.RESULT_PENDING){
				
				Boolean flagEsCochera = Boolean.FALSE;
				Boolean flagEsServicioEstacionamiento = Boolean.FALSE; 
				Boolean flagEsTerrenoSinConstruir = Boolean.FALSE;
				Boolean flagEsAlmacenDeposito = Boolean.FALSE; /**Ordenanza Nro xx CMPC del 2016*/
				
				int cantUsos = 0;
				String msg ="";
				
				RpDjarbitrio arbitrio=calculoPredialBo.getRpDjarbitrio(djpredial.getDjId());
				List<RpDjuso> luso=calculoPredialBo.getAllRpDjuso(arbitrio.getDjarbitrioId());
			
				/**De acuerdo a la ordenanza Nro xx CMPC del 2016, en las Situaciones Especiales - DECIMO TERCERA, indica que las cocheras,tendales,depósitos y otros predios, con partidas registrales independientes 
				 * pero comprendidos bajo el régimen de propiedad horizontal de un predio CASA/HABITACION  no estan afectos a los arbitrios municipales.
				 * */
		        Iterator<RpDjuso> itUsos = luso.iterator();  
			       
		        while (itUsos.hasNext()){
		        	RpDjuso uso=itUsos.next();
		        	
		        	//La cochera es igual un predio en edificio, se debe registrar como tal, No genera arbitrio para la Cochera		        	
		        	if(uso.getTipoUsoId()==Constante.TIPO_USO_COCHERA_DOMICILIO_NUEVO){
		        		flagEsCochera = Boolean.TRUE;
		        		cantUsos++;
		        	}else if(uso.getTipoUsoId()==Constante.TIPO_USO_SERVICIO_DE_ESTACIONAMIENTO_NUEVO){
		        		flagEsServicioEstacionamiento = Boolean.TRUE;
		        		cantUsos++;
		        	}else if(uso.getTipoUsoId()==Constante.TIPO_USO_TERRENO_SIN_CONSTRUIR_NUEVO){
		        		flagEsTerrenoSinConstruir = Boolean.TRUE;
		        		cantUsos++;
		        	}else if(uso.getTipoUsoId()==Constante.TIPO_USO_ALMACEN_DEPOSITO_NUEVO){
		        		flagEsAlmacenDeposito = Boolean.TRUE;
			        		cantUsos++;
		        	}else{
		        		cantUsos++;
		        	}
		        }
		 
		        //(1)Calculo arbitrio de Limpieza 2016
	        	DtFrecuenciaLimpieza limpieza = new DtFrecuenciaLimpieza();
	        	limpieza = calculoPredialBo.getDtFrecuenciaLimpieza(direccion.getUbicacionId(),anio);
		        Double arbitrioLimpiezaPreSubven = 0.0;
		        Double arbitrioLimpieza = 0.0;	
		        int limpiezaOtrosFrentesFrecuencia = 0;
		        
		        if(! (flagEsCochera && cantUsos == 1) ){
					if( !(flagEsServicioEstacionamiento && cantUsos == 1) ){
						if(!(flagEsAlmacenDeposito && cantUsos==1)){
							
						    if( limpieza != null ){
					        	arbitrioLimpiezaPreSubven = limpieza.getTasaMlAnual().doubleValue()*djpredial.getFrente().doubleValue();				
								
					        	/** Se aplica la subvencion de acuerdo a la Ordenanza Nro xx-2016 :: paga el 75% del costo para Barrido de Calles*/
								//arbitrioLimpieza = arbitrioLimpiezaPreSubven*(0.75);
								arbitrioLimpieza = arbitrioLimpiezaPreSubven*(0.80);
					        }else{
					        	msg = msg.concat("Sin valor de Limpieza para ubicacionId: ").concat(direccion.getUbicacionId().toString()).concat(", anio: ").concat(anio.toString()).concat(". ");
					        }
					        
					        //BARRIDO para otros frentes si los tuviera		
					        DtFrecuenciaLimpieza limpiezaOtrosFrentes = new DtFrecuenciaLimpieza();
							Double arbitrioLimpiezaPreSubvenOtrosFrentes = 0.0;
							Double arbitrioLimpiezaOtrosFrentes = 0.0;
							
							Double totalArbitrioLimpiezaPreSubvenOtrosFrentes = Double.valueOf(0);
							Double totalArbitrioLimpiezaOtrosFrentes = Double.valueOf(0);	
							
							List<RpOtrosFrente> lstOtrosFrentes = getCalculoPredialBo().obtenerOtrosFrentes(djpredial.getDjId());
							
							for(RpOtrosFrente frente : lstOtrosFrentes){
							
								limpiezaOtrosFrentes = calculoPredialBo.getDtFrecuenciaLimpieza(frente.getUbicacionId(),anio);
								if(limpiezaOtrosFrentes != null){
									arbitrioLimpiezaPreSubvenOtrosFrentes = limpiezaOtrosFrentes.getTasaMlAnual().doubleValue()*frente.getFrente().doubleValue();
									totalArbitrioLimpiezaPreSubvenOtrosFrentes = totalArbitrioLimpiezaPreSubvenOtrosFrentes + arbitrioLimpiezaPreSubvenOtrosFrentes;
									limpiezaOtrosFrentesFrecuencia = limpiezaOtrosFrentesFrecuencia +  limpiezaOtrosFrentes.getFrecuencia();
								}
								//arbitrioLimpiezaOtrosFrentes = arbitrioLimpiezaPreSubvenOtrosFrentes*(0.75);    //1.Arbitrio de Barrido de Calles: 75% del total del costo real determinado en el Informe Técnico mediante la presente Ordenanza.
								arbitrioLimpiezaOtrosFrentes = arbitrioLimpiezaPreSubvenOtrosFrentes*(0.80);    //1.Arbitrio de Barrido de Calles: 75% del total del costo real determinado en el Informe Técnico mediante la presente Ordenanza.
								
								totalArbitrioLimpiezaOtrosFrentes = totalArbitrioLimpiezaOtrosFrentes + arbitrioLimpiezaOtrosFrentes;
							}
							
							//ARBITRIOS PARA BARRIDO INCLUYENDO OTROS FRENTES SI LOS TUVIERA
							arbitrioLimpieza = arbitrioLimpieza  + totalArbitrioLimpiezaOtrosFrentes;
							arbitrioLimpiezaPreSubven = arbitrioLimpiezaPreSubven + totalArbitrioLimpiezaPreSubvenOtrosFrentes;
							
							TotalArbitrio = TotalArbitrio + arbitrioLimpieza;
							
							//guardarDeterminacion(djpredial,arbitrioLimpiezaPreSubven,arbitrioLimpieza,Constante.SUB_CONCEPTO_ARBITRIOS_BARRIDO,anio,null,lDeterminacionAnt);
							//AGREGANDO DJ USOS //
							guardarDeterminacion(djpredial,arbitrioLimpiezaPreSubven,arbitrioLimpieza,Constante.SUB_CONCEPTO_ARBITRIOS_BARRIDO,anio,null,lDeterminacionAnt,null);
						}
					}	
		        }
		      
				
				//(2)Calculo arbitrio de Recojo (en el 2016 no se usa la frecuencia de recojo)
		    		/**De acuerdo a la ordenanza Nro xx CMPC del 2016, en las Situaciones Especiales - DECIMO TERCERA, indica que las cocheras,tendales,depósitos y otros predios, con partidas registrales independientes 
		    		 * pero comprendidos bajo el régimen de propiedad horizontal de un predio CASA/HABITACION  no estan afectos a los arbitrios municipales.
		    		 * */
		        
					/** De acuerdo a la ordenanza Nro xx CMPC del 2016, en las Situaciones Especiales - DECIMO TERCERA, indica que, los terrenos sin construir 
					 * cancelaran unicamente el arbitrio de Barrido de Calles.
					 */
					Double arbitrioRecojo=Double.valueOf(0);
					Double arbitrioRecojoPreSubven=Double.valueOf(0);
					List<RecojoArbitriosDTO> lRecojoArbitrio=new LinkedList<RecojoArbitriosDTO>();
					
					 if(! (flagEsCochera && cantUsos == 1) ){
							if( !(flagEsServicioEstacionamiento && cantUsos == 1) ){
								if(!(flagEsAlmacenDeposito && cantUsos==1)){
								
					//La frecuencia de recojo para el anio 2016 depende del uso no de la frecuencia
					DtFrecuenciaRecojo frecuenciaRecojo=calculoPredialBo.getDtFrecuenciaRecojo(direccion.getUbicacionId(),anio);
					if(frecuenciaRecojo!=null&&frecuenciaRecojo.getFrecuencia()>0){
						
						if(djpredial.getUbicacionPredioId().compareTo(Constante.UBICACION_PREDIO_TERRENO_SIN_CONSTRUIR)!=0 && !flagEsTerrenoSinConstruir){
						        Iterator<RpDjuso> it = luso.iterator();  
						       
						        while (it.hasNext()){
						        	RpDjuso uso=it.next();
						        	//La cochera es igual un predio en edificio, se debe registrar como tal, No genera arbitrio para la Cochera
						        	//Los usos de RpCategoriaUso se usan en los Usos de Recojo (NO EN SEGURIDAD)
						        	if(uso.getTipoUsoId()!=Constante.TIPO_USO_COCHERA_DOMICILIO_NUEVO){
							        	RpTipoUso tipoUso=mapRpTipoUso.get(uso.getTipoUsoId());
							        	//Debemos obtener las Categorias correspondientes al 2016 de la tabla::
							        	Integer categoriaUsoId2016=mapCategoriaUsoId2016.get(tipoUso.getTipoUsoId());
							        	
							        	DtMatrizRecojo matrizRecojo = mapDtMatrizRecojo.get(categoriaUsoId2016);
							        	
							        	//PARA LA ORDENANZA 407-CMPC ARTICULO NRO 16
							        	MpPersona persona = calculoPredialBo.getFindPersona(djpredial.getPersonaId());
							        	if(persona.getFlagEstatal() != null && persona.getFlagEstatal().compareTo(Constante.ESTADO_ACTIVO)==0){
							        		// matrizRecojoCH  = traemos hardcode el valor de la recojo en casahabitacion
							        		DtMatrizRecojo matrizRecojoCasaHabitacion = mapDtMatrizRecojo.get(Constante.CATEGORIA_USO_DOMESTICO);
							        		
							        		// comparacion entre matrizRecojoCH <= matrizRecojo ? matrizRecojo1: matrizRecojo
							        		matrizRecojo  = matrizRecojo.getCostoM2Anual().compareTo(matrizRecojoCasaHabitacion.getCostoM2Anual()) > 0 ? 
							        							matrizRecojoCasaHabitacion: matrizRecojo ;    
							        	}					        	
							        	
							        	if(matrizRecojo!=null){
							        		Double arbitrioRecojoUsoPreSubven=uso.getAreaUso().doubleValue()*matrizRecojo.getCostoM2Anual().doubleValue();
							        		arbitrioRecojoPreSubven=arbitrioRecojoPreSubven+arbitrioRecojoUsoPreSubven;
							        		/** Se aplica la subvencion de acuerdo a la ordenanza Nro xx CMPC del 2016 :: subvenciona el 75% del costo para Recolección de Residuos Sólidos*/
							        		//Double arbitrioRecojoUso=arbitrioRecojoUsoPreSubven*(0.75); 			 //2.	Arbitrio de Recolección de Residuos Sólidos: 75% del total del costo real determinado en el Informe Técnico mediante la presente Ordenanza.
							        		Double arbitrioRecojoUso=arbitrioRecojoUsoPreSubven*(0.80); 			 //2.	Arbitrio de Recolección de Residuos Sólidos: 75% del total del costo real determinado en el Informe Técnico mediante la presente Ordenanza.
							        		
							        		/** Se aplica la subvencion de acuerdo a la ordenanza Nro xx CMPC del 2016 :: subvenciona el 75% del costo para Recolección de Residuos Sólidos*/
							        		arbitrioRecojo=arbitrioRecojo+arbitrioRecojoUso;
							        	
								        	RecojoArbitriosDTO recojo=new RecojoArbitriosDTO();
								        	recojo.setUso(((RpCategoriaUso)mapRpCategoriaUso.get(categoriaUsoId2016)).getDescripcion());
								        	recojo.setCategoriaUsoId(categoriaUsoId2016);
								        	recojo.setAreaM2(BigDecimal.valueOf(uso.getAreaUso().doubleValue()));
								        	recojo.setCostoM2Anual(BigDecimal.valueOf(matrizRecojo.getCostoM2Anual().doubleValue()));
								        	recojo.setMontoRecojoUsoPreSubven(BigDecimal.valueOf(arbitrioRecojoUsoPreSubven));
								        	recojo.setMontoRecojoUso(BigDecimal.valueOf(arbitrioRecojoUso));
								        	lRecojoArbitrio.add(recojo);
								        	
								        	//guardarDeterminacion(djpredial,arbitrioRecojoUsoPreSubven,arbitrioRecojoUso,Constante.SUB_CONCEPTO_ARBITRIOS_RECOJO,anio,tipoUso.getTipoUsoId(),lDeterminacionAnt);
								        	//AGREGANDO DJ USOS //
								        	guardarDeterminacion(djpredial,arbitrioRecojoUsoPreSubven,arbitrioRecojoUso,Constante.SUB_CONCEPTO_ARBITRIOS_RECOJO,anio,tipoUso.getTipoUsoId(),lDeterminacionAnt,uso.getDjusoId());
							        	}
						        	}
						        }
					        TotalArbitrio=TotalArbitrio+arbitrioRecojo;
						}
					}
				 } } }
		        //(3)Calculo arbitrio Parques [2016 UBICACION]
					/** De acuerdo a la ordenanza Nro xx CMPC del 2016, en las Situaciones Especiales - DECIMO TERCERA, indica que las cocheras,tendales,depósitos y otros predios, con partidas registrales independientes 
					  * pero comprendidos bajo el régimen de propiedad horizontal de un predio CASA/HABITACION  no estan afectos a los arbitrios municipales.
					  * */

					/** De acuerdo a la ordenanza Nro xx CMPC del 2016, en las Situaciones Especiales - DECIMO TERCERA, indica que, los terrenos sin construir 
					 * cancelaran unicamente el arbitrio de Barrido de Calles.
					 */
					
					/**
			         	28	Frente a Parques											32	Cerca a Parques
						29	Frente a Plazuelas											33	Cerca a Plazuelas
						30	Frente a Jardineras										    34	Cerca a Jardineras
						31	Frente a Jardineras de Acceso								35	Cerca a Jardineras de Acceso
	
						36	Lejos a Parques
						37	Lejos a Plazuelas
						38	Lejos a Jardineras
						39	Lejos a Jardineras de Acceso
						
						40	Predios ubicados a mas de seis manzanas de Areas Verdes - inafectos
			         */
					
					Double arbitrioParquesPreSubven=Double.valueOf(0);
			        Double arbitrioParquesAntesBeneficio=Double.valueOf(0);
			        Double arbitrioParques=Double.valueOf(0);
			        DtGrupoCercania grupoCercania = null;
			        
				if (djpredial.getUbicacionPredioId().compareTo(Constante.UBICACION_PREDIO_TERRENO_SIN_CONSTRUIR) != 0 && !flagEsTerrenoSinConstruir ) {
					
					if( !(flagEsCochera && cantUsos == 1) ){
						
						if( !(flagEsServicioEstacionamiento && cantUsos == 1) ){
							
							if(!(flagEsAlmacenDeposito && cantUsos==1)){
							
								DtCercaniaParque cercania = calculoPredialBo.getDtCercaniaParque(direccion.getUbicacionId(), anio);
								
								if(cercania != null){
									// Esto pasa cuando el predio no colinda con parques
									if (cercania.getGrupoCercaniaId() != null) { 
										grupoCercania = calculoPredialBo.getDtGrupoCercania(cercania.getGrupoCercaniaId());
										/**
										 * Se aplica la subvencion de acuerdo a la ordenanza Nro xx CMPC del 2016 :: Parques y Jardines
										 */
										arbitrioParquesPreSubven = grupoCercania.getCostoAnual().doubleValue();
										//arbitrioParquesAntesBeneficio = arbitrioParquesPreSubven * (0.6);//ii.	Arbitrio de Mantenimiento de Parques y Jardines: 40% del total del costo real determinado mediante la presente Ordenanza.
										arbitrioParquesAntesBeneficio = arbitrioParquesPreSubven * grupoCercania.getPorcentajeSubvencion().doubleValue();
										/**
										 * Se aplica la subvencion de acuerdo a la ordenanza Nro xx CMPC del 2016 :: Parques y Jardines
										 */

										arbitrioParques = arbitrioParquesAntesBeneficio;
										/**
										 * De acuerdo a los beneficios de arbitrios municipales del 2015:Fin
										 */
										
										//guardarDeterminacion(djpredial,arbitrioParquesPreSubven, arbitrioParques,Constante.SUB_CONCEPTO_ARBITRIOS_PARQUES,anio, null, lDeterminacionAnt);
										//AGREGANDO DJ USOS //
										guardarDeterminacion(djpredial,arbitrioParquesPreSubven, arbitrioParques,Constante.SUB_CONCEPTO_ARBITRIOS_PARQUES,anio, null, lDeterminacionAnt,null);
										
										TotalArbitrio = TotalArbitrio + arbitrioParques;		
									}
								}else{
									msg = msg.concat("Sin valor de Parques en DtCercaniaParque para ubicacionId: ").concat(direccion.getUbicacionId().toString()).concat(", anio: ").concat(anio.toString()).concat(". ");
								}
						}
					 }
				   }
				 }
		
		        //(4)Calculo arbitrio de Seguridad [2016 UBICACION-USO]
	    		/**De acuerdo a la ordenanza Nro xx CMPC del 2016, en las Situaciones Especiales - DECIMO TERCERA, indica que las cocheras,tendales,depósitos y otros predios, con partidas registrales independientes 
	    		 * pero comprendidos bajo el régimen de propiedad horizontal de un predio CASA/HABITACION  no estan afectos a los arbitrios municipales.
	    		 * */
	        
				/** De acuerdo a la ordenanza Nro xx CMPC del 2016, en las Situaciones Especiales - DECIMO TERCERA, indica que, los terrenos sin construir 
				 * cancelaran unicamente el arbitrio de Barrido de Calles.
				 */
			        Double arbitrioSeguridad=Double.valueOf(0);
			        Double arbitrioSeguridadPreSubven=Double.valueOf(0);
			        Double arbitrioSeguridadAntesBeneficios=Double.valueOf(0);
			        List<SeguridadArbitriosDTO> lSeguridadArbitrio=new LinkedList<SeguridadArbitriosDTO>();
									
					DtZonaSeguridadUbicacion zonaSeguridad=calculoPredialBo.getDtZonaSeguridadUbicacion(direccion.getUbicacionId(),anio);
					
					if (djpredial.getUbicacionPredioId().compareTo(Constante.UBICACION_PREDIO_TERRENO_SIN_CONSTRUIR) != 0 && !flagEsTerrenoSinConstruir ) {
						
						if( !(flagEsCochera && cantUsos == 1) ){
							
							if( !(flagEsServicioEstacionamiento && cantUsos == 1) ){
								
								if(!(flagEsAlmacenDeposito && cantUsos==1)){
					
						        Iterator<RpDjuso> it = luso.iterator();
						        while (it.hasNext()){
						        	RpDjuso uso=it.next();
						        	//La cochera es igual un predio en edificio, se debe registrar como tal, No genera arbitrio para la Cochera
						        	if(uso.getTipoUsoId()!=Constante.TIPO_USO_COCHERA_DOMICILIO_NUEVO){
							        	RpTipoUso tipoUso=mapRpTipoUso.get(uso.getTipoUsoId());
							        	
							        	if(tipoUso != null){
							        		//Debemos obtener las Categorias correspondientes a SEGURIDAD DEL 2016 de la tabla rp_categoria_seguridad::INICIO
							        		Integer categoriaSeguridadTipoUso2016Id=mapCategoriaSeguridadUsoId2016.get(tipoUso.getTipoUsoId());
	
							        		DtMatrizSeguridad seguridadUso=mapDtZonaSeguridadUso2016.get(String.valueOf(categoriaSeguridadTipoUso2016Id)+"-"+String.valueOf(zonaSeguridad.getZonaSeguridadId()));
								        	/** Se aplica la subvencion de acuerdo a la ordenanza Nro xx CMPC del 2016 :: Seguridad Ciudadana */
								        	Double arbitrioSeguridadUsoPrevSubven=seguridadUso.getTasaAnual().doubleValue();
								        	arbitrioSeguridadPreSubven=arbitrioSeguridadPreSubven+arbitrioSeguridadUsoPrevSubven;
								        	
								        	Double arbitrioSeguridadUsoAntesBeneficio=arbitrioSeguridadUsoPrevSubven; //Sin Subvencion
								        	/** Se aplica la subvencion de acuerdo a la ordenanza Nro xx CMPC del 2016 :: Seguridad Ciudadana*/
								        	arbitrioSeguridadAntesBeneficios=arbitrioSeguridadAntesBeneficios.doubleValue()+arbitrioSeguridadUsoAntesBeneficio;
								        	
								        	Double arbitrioSeguridadUso=Double.valueOf(0);
								        	/** De acuerdo a los beneficios de arbitrios municipales del 2016:Inicio */
								        	arbitrioSeguridadUso=arbitrioSeguridadUsoAntesBeneficio;
								        	/** De acuerdo a los beneficios de arbitrios municipales del 2016:Fin */
								        	arbitrioSeguridad=arbitrioSeguridad.doubleValue()+arbitrioSeguridadUso;
								        	
								        	SeguridadArbitriosDTO seguridad=new SeguridadArbitriosDTO();
								        	seguridad.setUso(((RpCategoriaUsoSeguridad)mapRpCategoriaUso2016.get(categoriaSeguridadTipoUso2016Id)).getDescripcion());
								        	seguridad.setTasaAnualPreSubven(BigDecimal.valueOf(arbitrioSeguridadUsoPrevSubven));
								        	seguridad.setTasaAnualAntesBeneficios(BigDecimal.valueOf(arbitrioSeguridadUsoAntesBeneficio));
								        	seguridad.setTasaAnual(BigDecimal.valueOf(arbitrioSeguridadUso));
								        	seguridad.setCategoriaUsoId(categoriaSeguridadTipoUso2016Id);
								        	lSeguridadArbitrio.add(seguridad);
								        	
								        	//guardarDeterminacion(djpredial,arbitrioSeguridadUsoPrevSubven,arbitrioSeguridadUso,Constante.SUB_CONCEPTO_ARBITRIOS_SEGURIDAD,anio,tipoUso.getTipoUsoId(),lDeterminacionAnt);
								        	//AGREGANDO DJ USOS //
								        	guardarDeterminacion(djpredial,arbitrioSeguridadUsoPrevSubven,arbitrioSeguridadUso,Constante.SUB_CONCEPTO_ARBITRIOS_SEGURIDAD,anio,tipoUso.getTipoUsoId(),lDeterminacionAnt,uso.getDjusoId());
								        	
							        	}else{
							        		msg = msg.concat("Sin valor de Uso: ").concat(anio.toString()).concat(". ");
							        	}
							        	
						        	}
						        }
						        TotalArbitrio=TotalArbitrio+arbitrioSeguridad;
						}}}
				}
		    	
			    //(6) Determinacion de arbitrios generales
		    	calculo=new DeterminacionArbitriosDTO();
		    	//--Datos del calculo para su validacion
		    	calculo.setArbitrioLimpieza(BigDecimal.valueOf(arbitrioLimpieza));
		        calculo.setArbitrioParques(BigDecimal.valueOf(arbitrioParques));
		        calculo.setArbitrioRecojo(BigDecimal.valueOf(arbitrioRecojo));
		        calculo.setArbitrioSeguridad(BigDecimal.valueOf(arbitrioSeguridad));
		        calculo.setFrecuenciaLimpieza(limpieza.getFrecuencia());
		        calculo.setTasaMlAnualLimpieza(BigDecimal.valueOf(limpieza.getTasaMlAnual().doubleValue()));
		        calculo.setFrenteMlLimpieza(BigDecimal.valueOf(djpredial.getFrente().doubleValue()));
		        
		        /** Se aplica la subvencion de acuerdo a la ordenanza 2015*/
		        calculo.setArbitrioLimpiezaAntesSubvencion(BigDecimal.valueOf(arbitrioLimpiezaPreSubven));
		        calculo.setArbitrioRecojoAntesSubvencion(BigDecimal.valueOf(arbitrioRecojoPreSubven));
		        calculo.setArbitrioSeguridadAntesBeneficio(BigDecimal.valueOf(arbitrioSeguridadPreSubven));
		        calculo.setArbitrioParquesAntesSubvencion(BigDecimal.valueOf(arbitrioParquesPreSubven));
		        
		        /** De acuerdo a los beneficios de arbitrios municipales del 2015 */
		        calculo.setArbitrioSeguridadAntesBeneficio(BigDecimal.valueOf(arbitrioSeguridadAntesBeneficios.doubleValue()));
	        	calculo.setArbitrioParquesAntesBeneficio(BigDecimal.valueOf(arbitrioParquesAntesBeneficio.doubleValue()));
	        	
		    	//Calculo arbitrio de Recojo
	        	calculo.setlRecojoArbitrio(lRecojoArbitrio);
		        
		    	//Calculo arbitrio Parques
		        if(grupoCercania != null){
		        	calculo.setGrupoCercaniaParques(grupoCercania.getDescipcionCorta());
		        	calculo.setCostoAnualParques(BigDecimal.valueOf(grupoCercania.getCostoAnual().doubleValue()));
		        }else{
		        	calculo.setGrupoCercaniaParques(null);
		        	calculo.setCostoAnualParques(BigDecimal.valueOf(0));
		        }
		    	
		        //Calculo arbitrio de Seguridad
		    	DtZonaSeguridad dtZonaSeguridad=mapDtZonaSeguridad.get(zonaSeguridad.getZonaSeguridadId());
		    	calculo.setZonaSeguridad(dtZonaSeguridad.getDescripcionCorta());
		    	calculo.setlSeguridadArbitrio(lSeguridadArbitrio);
		    	//General
		    	calculo.setDjId(djpredial.getDjId());
		    	calculo.setAnnoDeterminacion(anio);
		    	calculo.setDireccionCompleta(direccion.getDireccionCompleta());
		    	//--
		    	if(grupoCercania != null){
		    		calculo.setGrupoCercaniaParquesId(grupoCercania.getGrupoCercaniaId());
		    	}else{
		    		calculo.setGrupoCercaniaParquesId(null);
		    	}
		    	
		    	calculo.setFrecuenciaLimpiezaId(limpieza.getFrecuenciaLimpiezaId());
		    	calculo.setZonaSeguridadId(dtZonaSeguridad.getZonaSeguridadId());
		    	
		    	getCalculoPredialBo().guardarCalculoArbitrios(calculo);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
        return calculo;
	}
	
//	private DeterminacionArbitriosDTO calculoArbitrios2014_(RpDjpredial djpredial,Integer anio,List<DtDeterminacion> lDeterminacionAnt)throws Exception{
//		DeterminacionArbitriosDTO calculo=null;
//		try{
//			Double TotalArbitrio=Double.valueOf(0);				
//			
//			RpDjdireccion direccion=calculoPredialBo.getRpDjDireccion(djpredial.getDjId());
//			if(direccion!=null&&direccion.getUbicacionId()>Constante.RESULT_PENDING){
//				
//				Boolean flagEsCochera = Boolean.FALSE;
//				Boolean flagEsServicioEstacionamiento = Boolean.FALSE; 
//				Boolean flagEsTerrenoSinConstruir = Boolean.FALSE;
//				int cantUsos = 0;
//				String msg ="";
//				
//				RpDjarbitrio arbitrio=calculoPredialBo.getRpDjarbitrio(djpredial.getDjId());
//				List<RpDjuso> luso=calculoPredialBo.getAllRpDjuso(arbitrio.getDjarbitrioId());
//				
//		        /**De acuerdo a la ordenanza Nro 407 CMPC del 2012, en sus disoluciones complementarias CUARTA indica que las cocheras y tendales son afectas
//	        	 * a Barrido de Calles y Seguridad Ciudadana al ser unidades inmobilirias independientes y que no son afectas si pertenecen a una propiedad horizontal
//	        	 * */
//		        Iterator<RpDjuso> itUsos = luso.iterator();  
//			       
//		        while (itUsos.hasNext()){
//		        	RpDjuso uso=itUsos.next();
//		        	
//		        	//La cochera es igual un predio en edificio, se debe registrar como tal, No genera arbitrio para la Cochera		        	
//		        	if(uso.getTipoUsoId()==Constante.TIPO_USO_COCHERA_DOMICILIO){
//		        		flagEsCochera = Boolean.TRUE;
//		        		cantUsos++;
//		        	}else if(uso.getTipoUsoId()==Constante.TIPO_USO_SERVICIO_DE_ESTACIONAMIENTO){
//		        		flagEsServicioEstacionamiento = Boolean.TRUE;
//		        		cantUsos++;
//		        	}else if(uso.getTipoUsoId()==Constante.TIPO_USO_TERRENO_SIN_CONSTRUIR){
//		        		flagEsTerrenoSinConstruir = Boolean.TRUE;
//		        		cantUsos++;
//		        	}else{
//		        		cantUsos++;
//		        	}
//		        }
//		 
//		        //(1)Calculo arbitrio de Limpieza (2012 UBICACION) [2014 UBICACION] BARRIDO
//	        	DtFrecuenciaLimpieza limpieza = new DtFrecuenciaLimpieza();
//	        	limpieza = calculoPredialBo.getDtFrecuenciaLimpieza(direccion.getUbicacionId(),anio);
//		        Double arbitrioLimpiezaPreSubven = 0.0;
//		        Double arbitrioLimpieza = 0.0;	
//		        int limpiezaOtrosFrentesFrecuencia = 0;
//		        
//		        if(! (flagEsCochera && cantUsos == 1)){
//		        
//			        if( limpieza != null ){
//			        	arbitrioLimpiezaPreSubven = limpieza.getTasaMlAnual().doubleValue()*djpredial.getFrente().doubleValue();				
//						
//			        	/** Se aplica la subvencion de acuerdo a la ordenanza 407 - 408 :: paga el 70% del costo para LIMPIEZA*/
//						arbitrioLimpieza = arbitrioLimpiezaPreSubven*(0.7);
//			        }else{
//			        	msg = msg.concat("Sin valor de Limpieza para ubicacionId: ").concat(direccion.getUbicacionId().toString()).concat(", anio: ").concat(anio.toString()).concat(". ");
//			        }
//			        
//			        //BARRIDO para otros frentes si los tuviera		
//			        DtFrecuenciaLimpieza limpiezaOtrosFrentes = new DtFrecuenciaLimpieza();
//					Double arbitrioLimpiezaPreSubvenOtrosFrentes = 0.0;
//					Double arbitrioLimpiezaOtrosFrentes = 0.0;
//					
//					Double totalArbitrioLimpiezaPreSubvenOtrosFrentes = Double.valueOf(0);
//					Double totalArbitrioLimpiezaOtrosFrentes = Double.valueOf(0);	
//					
//					List<RpOtrosFrente> lstOtrosFrentes = getCalculoPredialBo().obtenerOtrosFrentes(djpredial.getDjId());
//					
//					for(RpOtrosFrente frente : lstOtrosFrentes){
//					
//						limpiezaOtrosFrentes = calculoPredialBo.getDtFrecuenciaLimpieza(frente.getUbicacionId(),anio);
//						if(limpiezaOtrosFrentes != null){
//							arbitrioLimpiezaPreSubvenOtrosFrentes = limpiezaOtrosFrentes.getTasaMlAnual().doubleValue()*frente.getFrente().doubleValue();
//							totalArbitrioLimpiezaPreSubvenOtrosFrentes = totalArbitrioLimpiezaPreSubvenOtrosFrentes + arbitrioLimpiezaPreSubvenOtrosFrentes;
//							limpiezaOtrosFrentesFrecuencia = limpiezaOtrosFrentesFrecuencia +  limpiezaOtrosFrentes.getFrecuencia();
//						}
//						arbitrioLimpiezaOtrosFrentes = arbitrioLimpiezaPreSubvenOtrosFrentes*(0.7);
//						totalArbitrioLimpiezaOtrosFrentes = totalArbitrioLimpiezaOtrosFrentes + arbitrioLimpiezaOtrosFrentes;
//					}
//					
//					//ARBITRIOS PARA BARRIDO INCLUYENDO OTROS FRENTES SI LOS TUVIERA
//					arbitrioLimpieza = arbitrioLimpieza  + totalArbitrioLimpiezaOtrosFrentes;
//					arbitrioLimpiezaPreSubven = arbitrioLimpiezaPreSubven + totalArbitrioLimpiezaPreSubvenOtrosFrentes;
//					
//					TotalArbitrio = TotalArbitrio + arbitrioLimpieza;
//					
//					guardarDeterminacion(djpredial,arbitrioLimpiezaPreSubven,arbitrioLimpieza,Constante.SUB_CONCEPTO_ARBITRIOS_BARRIDO,anio,null,lDeterminacionAnt);
//				 
//		        }
//		      
//				
//				//(2)Calculo arbitrio de Recojo (en el 2014 no se usa la frecuencia de recojo)
//					/** De acuerdo a la ordenanza Nro 407 CMPC del 2012, en sus disoluciones complementarias TERCERA indica que los terrenos sin construir 
//					 * cancelaran unicamente el arbitrio de Barrido de Calles y Seguridad Ciudadana
//					 */
//					Double arbitrioRecojo=Double.valueOf(0);
//					Double arbitrioRecojoPreSubven=Double.valueOf(0);
//					List<RecojoArbitriosDTO> lRecojoArbitrio=new LinkedList<RecojoArbitriosDTO>();
//					
//					//La frecuencia de recojo para el anio 2014 depende del uso no de la frecuencia
//					DtFrecuenciaRecojo frecuenciaRecojo=calculoPredialBo.getDtFrecuenciaRecojo(direccion.getUbicacionId(),anio);
//					if(frecuenciaRecojo!=null&&frecuenciaRecojo.getFrecuencia()>0){
//						
//						if(djpredial.getUbicacionPredioId().compareTo(Constante.UBICACION_PREDIO_TERRENO_SIN_CONSTRUIR)!=0 && !flagEsTerrenoSinConstruir){
//							//La frecuencia de recojo para el anio 2014 depende del uso no de la frecuencia
//							
////							List<DtMatrizRecojo> lrecojoUso=calculoPredialBo.getAllDtMatrizRecojo(anio);
//							
////							if(lrecojoUso != null){
////								HashMap<Integer,DtMatrizRecojo> mapDtMatrizRecojo=new HashMap<Integer,DtMatrizRecojo>();
////								Iterator<DtMatrizRecojo> it3 = lrecojoUso.iterator();
////						        while (it3.hasNext()){
////						        	DtMatrizRecojo obj = it3.next();  
////						        	mapDtMatrizRecojo.put(obj.getCategoriaUsoId(), obj);
////						        }
//						        Iterator<RpDjuso> it = luso.iterator();  
//						       
//						        while (it.hasNext()){
//						        	RpDjuso uso=it.next();
//						        	/**De acuerdo a la ordenanza Nro 407 CMPC del 2012, en sus disoluciones complementarias CUARTA indica que las cocheras y tendales son afectas
//						        	 * a Barrido de Calles y Seguridad Ciudadana al ser unidades inmobilirias independientes
//						        	 * */
//						        	//La cochera es igual un predio en edificio, se debe registrar como tal, No genera arbitrio para la Cochera
//						        	//Los usos de RpCategoriaUso se usan en los Usos de Recojo (NO EN SEGURIDAD)
//						        	if(uso.getTipoUsoId()!=Constante.TIPO_USO_COCHERA_DOMICILIO && uso.getTipoUsoId()!=Constante.TIPO_USO_SERVICIO_DE_ESTACIONAMIENTO){
//							        	RpTipoUso tipoUso=mapRpTipoUso.get(uso.getTipoUsoId());
//							        	//Debemos obtener las Categorias correspondientes al 2014 de la tabla RP_CATEGORIA_TIPO_USO2014::INICIO
//
//							        	//Integer categoriaUsoId2014=calculoPredialBo.getCategoriaUso2014(tipoUso.getTipoUsoId());
//							        	Integer categoriaUsoId2014=mapCategoriaUsoId2014.get(tipoUso.getTipoUsoId());
//							        	
//							        	//Debemos obtener las Categorias correspondientes al 2014 de la tabla RP_CATEGORIA_TIPO_USO2014::FIN
//							        	DtMatrizRecojo matrizRecojo = mapDtMatrizRecojo.get(categoriaUsoId2014);
//							        	
//							        	//PARA LA ORDENANZA 407-CMPC ARTICULO NRO 16
//							        	MpPersona persona = calculoPredialBo.getFindPersona(djpredial.getPersonaId());
//							        	if(persona.getFlagEstatal() != null && persona.getFlagEstatal().compareTo(Constante.ESTADO_ACTIVO)==0){
//							        		// matrizRecojoCH  = traemos hardcode el valor de la recojo en casahabitacion
//							        		DtMatrizRecojo matrizRecojoCasaHabitacion = mapDtMatrizRecojo.get(Constante.CATEGORIA_USO_DOMESTICO);
//							        		
//							        		// comparacion entre matrizRecojoCH <= matrizRecojo ? matrizRecojo1: matrizRecojo
//							        		matrizRecojo  = matrizRecojo.getCostoM2Anual().compareTo(matrizRecojoCasaHabitacion.getCostoM2Anual()) > 0 ? 
//							        							matrizRecojoCasaHabitacion: matrizRecojo ;    
//							        	}					        	
//							        	
//							        	if(matrizRecojo!=null){
//							        		Double arbitrioRecojoUsoPreSubven=uso.getAreaUso().doubleValue()*matrizRecojo.getCostoM2Anual().doubleValue();
//							        		arbitrioRecojoPreSubven=arbitrioRecojoPreSubven+arbitrioRecojoUsoPreSubven;
//							        		/** Se aplica la subvencion de acuerdo a la ordenanza 407 - 408 :: paga el 70% del costo para LIMPIEZA*/
//							        		Double arbitrioRecojoUso=arbitrioRecojoUsoPreSubven*(0.7);
//							        		/** Se aplica la subvencion de acuerdo a la ordenanza 407 - 408 :: subvenciona el 70% del costo para LIMPIEZA*/
//							        		arbitrioRecojo=arbitrioRecojo+arbitrioRecojoUso;
//							        	
//								        	RecojoArbitriosDTO recojo=new RecojoArbitriosDTO();
//								        	recojo.setUso(((RpCategoriaUso)mapRpCategoriaUso.get(categoriaUsoId2014)).getDescripcion());
//								        	recojo.setCategoriaUsoId(categoriaUsoId2014);
//								        	recojo.setAreaM2(BigDecimal.valueOf(uso.getAreaUso().doubleValue()));
//								        	recojo.setCostoM2Anual(BigDecimal.valueOf(matrizRecojo.getCostoM2Anual().doubleValue()));
//								        	recojo.setMontoRecojoUsoPreSubven(BigDecimal.valueOf(arbitrioRecojoUsoPreSubven));
//								        	recojo.setMontoRecojoUso(BigDecimal.valueOf(arbitrioRecojoUso));
//								        	lRecojoArbitrio.add(recojo);
//								        	
//								        	guardarDeterminacion(djpredial,arbitrioRecojoUsoPreSubven,arbitrioRecojoUso,Constante.SUB_CONCEPTO_ARBITRIOS_RECOJO,anio,tipoUso.getTipoUsoId(),lDeterminacionAnt);
//							        	}
//						        	}
//						        }
//							//}else{
//								//msg = msg.concat("Sin valor de Recojo en MatrizRecojo para anio: ").concat(anio.toString()).concat(". ");
//							//}
//							
//							
//					        
//					        TotalArbitrio=TotalArbitrio+arbitrioRecojo;
//						}
//					}
//		        
//		        //(3)Calculo arbitrio Parques (2012 UBICACION) [2014 UBICACION]
//					/** De acuerdo a la ordenanza Nro 407 CMPC del 2012, en sus disoluciones complementarias TERCERA indica que los terrenos sin construir 
//					 * cancelaran unicamente el arbitrio de Barrido de Calles y Seguridad Ciudadana
//					 */
//					
//					/**
//			         * Frente 16
//			         * Cerca 17
//			         * Lejos 18
//			         * Sin Radio 19
//			         */
//					
//					Double arbitrioParquesPreSubven=Double.valueOf(0);
//			        Double arbitrioParquesAntesBeneficio=Double.valueOf(0);
//			        Double arbitrioParques=Double.valueOf(0);
//			        DtGrupoCercania grupoCercania = null;
//			        
//				if (djpredial.getUbicacionPredioId().compareTo(Constante.UBICACION_PREDIO_TERRENO_SIN_CONSTRUIR) != 0 && !flagEsTerrenoSinConstruir ) {
//					
//					if( !(flagEsCochera && cantUsos == 1) ){
//						
//						if( !(flagEsServicioEstacionamiento && cantUsos == 1) ){
//							
//							/** De acuerdo a la ordenanza Nro 407 CMPC del 2012, en sus disoluciones complementarias CUARTA indica que
//							 * las cocheras y tendales son afectas a Barrido de Calles y Seguridad Ciudadana al ser unidades
//							 * inmobilirias independientes (no parques - no recojo)
//							 * */
//						
//								DtCercaniaParque cercania = calculoPredialBo.getDtCercaniaParque(direccion.getUbicacionId(), anio);
//								
//								if(cercania != null){
//									// Esto pasa cuando el predio no colinda con parques
//									if (cercania.getGrupoCercaniaId() != null) { 
//										grupoCercania = calculoPredialBo.getDtGrupoCercania(cercania.getGrupoCercaniaId());
//										/**
//										 * Se aplica la subvencion de acuerdo a la ordenanza 407 - 408 :: paga el 65% del costo para PARQUES
//										 */
//										arbitrioParquesPreSubven = grupoCercania.getCostoAnual().doubleValue();
//										arbitrioParquesAntesBeneficio = arbitrioParquesPreSubven * (0.65);
//										/**
//										 * Se aplica la subvencion de acuerdo a la ordenanza 407 - 408 :: subvenciona el 65% del costo para PARQUES
//										 */
//
//										/**
//										 * Beneficio del arbitrio de parques y jardines :Inicio establece un beneficio ascendente al 20% del monto determinado, despues de la
//										 * subvencion, a aquellos contribuyentes cuyos predios se encuentran nubicados en el rango 6 (R6) respecto al area verde ORDENANZA
//										 * 407-CMPC 20/12/2012
//										 */
////										if (grupoCercania.getDescipcionCorta().equals("R6")) {
////											arbitrioParquesAntesBeneficio = arbitrioParquesAntesBeneficio.doubleValue() * (1 - 0.20); 
////											// beneficio del 20%
////										}
//										/**
//										 * Beneficio del arbitrio de parques y jardines :Fin
//										 */
//
//										/**
//										 * De acuerdo a los beneficios de arbitrios municipales del 2014:Inicio
//										 */
////										if (grupoCercania.getDescipcionCorta().equals("R1")) {
////											arbitrioParques = arbitrioParquesAntesBeneficio.doubleValue() * (1 - 0.16); // descuento 16%
////										} else if (grupoCercania.getDescipcionCorta().equals("R2")) {
////											arbitrioParques = arbitrioParquesAntesBeneficio.doubleValue() * (1 - 0.11); // descuento 11%
////										} else if (grupoCercania.getDescipcionCorta().equals("R3")) {
////											arbitrioParques = arbitrioParquesAntesBeneficio.doubleValue() * (1 - 0.10); // descuento 10%
////										} else if (grupoCercania.getDescipcionCorta().equals("R4")) {
////											arbitrioParques = arbitrioParquesAntesBeneficio.doubleValue() * (1 - 0.04); // descuento 4%
////										} else if (grupoCercania.getDescipcionCorta().equals("R5")) {
////											arbitrioParques = arbitrioParquesAntesBeneficio.doubleValue() * (1 - 0.09); // descuento 9%
////										} else if (grupoCercania.getDescipcionCorta().equals("R6")) {
////											arbitrioParques = arbitrioParquesAntesBeneficio.doubleValue() * (1 - 0.36); // descuento 36%
////										} else {
////											arbitrioParques = arbitrioParquesAntesBeneficio;
////										}
//										arbitrioParques = arbitrioParquesAntesBeneficio;
//										/**
//										 * De acuerdo a los beneficios de arbitrios municipales del 2014:Fin
//										 */
//										
//										guardarDeterminacion(djpredial,arbitrioParquesPreSubven, arbitrioParques,Constante.SUB_CONCEPTO_ARBITRIOS_PARQUES,anio, null, lDeterminacionAnt);
//										TotalArbitrio = TotalArbitrio + arbitrioParques;		
//									}
//								}else{
//									msg = msg.concat("Sin valor de Parques en DtCercaniaParque para ubicacionId: ").concat(direccion.getUbicacionId().toString()).concat(", anio: ").concat(anio.toString()).concat(". ");
//								}
//						}
//					}
//
//				}
//					
//		        //(4)Calculo arbitrio de Seguridad (2012 UBICACION-USO) [2014 UBICACION-USO]
//			        Double arbitrioSeguridad=Double.valueOf(0);
//			        Double arbitrioSeguridadPreSubven=Double.valueOf(0);
//			        Double arbitrioSeguridadAntesBeneficios=Double.valueOf(0);
//			        List<SeguridadArbitriosDTO> lSeguridadArbitrio=new LinkedList<SeguridadArbitriosDTO>();
//			        
//					DtZonaSeguridadUbicacion zonaSeguridad=calculoPredialBo.getDtZonaSeguridadUbicacion(direccion.getUbicacionId(),anio);
//					
////					List<DtZonaSeguridadUso2014> lZonaSeguridadUso2014=calculoPredialBo.getAllDtZonaSeguridadUso2014(zonaSeguridad.getZonaSeguridadId(),anio);
////					HashMap<Integer,DtZonaSeguridadUso2014> mapDtZonaSeguridadUso2014=new HashMap<Integer,DtZonaSeguridadUso2014>();
////					Iterator<DtZonaSeguridadUso2014> it4 = lZonaSeguridadUso2014.iterator();
////					while (it4.hasNext()){
////						DtZonaSeguridadUso2014 obj = it4.next();  
////						mapDtZonaSeguridadUso2014.put(obj.getCategoriaUsoSeguridadId(), obj);
////					}
//
//			        Iterator<RpDjuso> it = luso.iterator();
//			        while (it.hasNext()){
//			        	RpDjuso uso=it.next();
//			        	//La cochera es igual un predio en edificio, se debe registrar como tal, No genera arbitrio para la Cochera
//			        	/**De acuerdo a la ordenanza Nro 407 CMPC del 2012, en sus disoluciones complementarias CUARTA indica que las cocheras y tendales son afectas
//			        	 * a Barrido de Calles y Seguridad Ciudadana al ser unidades inmobilirias independientes
//			        	 * */
//			        	if(uso.getTipoUsoId()!=Constante.TIPO_USO_COCHERA_DOMICILIO){
//				        	RpTipoUso tipoUso=mapRpTipoUso.get(uso.getTipoUsoId());
//				        	
//				        	if(tipoUso != null){
//				        		//Debemos obtener las Categorias correspondientes a SEGURIDAD DEL 2014 de la tabla rp_categoria_seguridad_tipo_uso2014::INICIO
//				        		
//				        		//--->Integer categoriaSeguridadTipoUso2014Id=calculoPredialBo.getCategoriaSeguridadTipoUso2014(tipoUso.getTipoUsoId());
//				        		Integer categoriaSeguridadTipoUso2014Id=mapCategoriaSeguridadUsoId2014.get(tipoUso.getTipoUsoId());
//					        	//Debemos obtener las Categorias correspondientes a SEGURIDAD DEL 2014 de la tabla rp_categoria_seguridad_tipo_uso2014::FIN
//					        	
//					        	DtZonaSeguridadUso2014 seguridadUso=mapDtZonaSeguridadUso2014.get(String.valueOf(categoriaSeguridadTipoUso2014Id)+"-"+String.valueOf(zonaSeguridad.getZonaSeguridadId()));
//					        	/** Se aplica la subvencion de acuerdo a la ordenanza 407 - 408 :: subvenciona el 65% del costo para SEGURIDAD*/
//					        	Double arbitrioSeguridadUsoPrevSubven=seguridadUso.getTasaAnual().doubleValue();
//					        	arbitrioSeguridadPreSubven=arbitrioSeguridadPreSubven+arbitrioSeguridadUsoPrevSubven;
//					        	
//					        	Double arbitrioSeguridadUsoAntesBeneficio=arbitrioSeguridadUsoPrevSubven*(0.65);
//					        	/** Se aplica la subvencion de acuerdo a la ordenanza 407 - 408 :: subvenciona el 65% del costo para SEGURIDAD*/
//					        	arbitrioSeguridadAntesBeneficios=arbitrioSeguridadAntesBeneficios.doubleValue()+arbitrioSeguridadUsoAntesBeneficio;
//					        	
//					        	Double arbitrioSeguridadUso=Double.valueOf(0);
//					        	/** De acuerdo a los beneficios de arbitrios municipales del 2014:Inicio */
////					        	if(categoriaSeguridadTipoUso2014Id.intValue()==6){//Restaurantes de rp_categoria_uso_seguridad / rp_categoria_seguridad_tipo_uso2014
////					        		if(arbitrioSeguridadUsoAntesBeneficio.doubleValue()>250){
////					        			arbitrioSeguridadUso=Double.valueOf(250.00);
////					        		}else{
////					        			arbitrioSeguridadUso=arbitrioSeguridadUsoAntesBeneficio;
////					        		}
////					        	}else if(categoriaSeguridadTipoUso2014Id.intValue()==7){//Bares, video pub, nigthclubs rp_categoria_uso_seguridad / rp_categoria_seguridad_tipo_uso2014
////					        		if(arbitrioSeguridadUsoAntesBeneficio.doubleValue()>500){
////					        			arbitrioSeguridadUso=Double.valueOf(500.00);
////					        		}else{
////					        			arbitrioSeguridadUso=arbitrioSeguridadUsoAntesBeneficio;
////					        		}
////					        	}else{
////					        		arbitrioSeguridadUso=arbitrioSeguridadUsoAntesBeneficio;
////					        	}
//					        	arbitrioSeguridadUso=arbitrioSeguridadUsoAntesBeneficio;
//					        	/** De acuerdo a los beneficios de arbitrios municipales del 2014:Fin */
//					        	arbitrioSeguridad=arbitrioSeguridad.doubleValue()+arbitrioSeguridadUso;
//					        	
//					        	SeguridadArbitriosDTO seguridad=new SeguridadArbitriosDTO();
//					        	seguridad.setUso(((RpCategoriaUsoSeguridad)mapRpCategoriaUso2014.get(categoriaSeguridadTipoUso2014Id)).getDescripcion());
//					        	seguridad.setTasaAnualPreSubven(BigDecimal.valueOf(arbitrioSeguridadUsoPrevSubven));
//					        	seguridad.setTasaAnualAntesBeneficios(BigDecimal.valueOf(arbitrioSeguridadUsoAntesBeneficio));
//					        	seguridad.setTasaAnual(BigDecimal.valueOf(arbitrioSeguridadUso));
//					        	seguridad.setCategoriaUsoId(categoriaSeguridadTipoUso2014Id);
//					        	lSeguridadArbitrio.add(seguridad);
//					        	
//					        	guardarDeterminacion(djpredial,arbitrioSeguridadUsoPrevSubven,arbitrioSeguridadUso,Constante.SUB_CONCEPTO_ARBITRIOS_SEGURIDAD,anio,tipoUso.getTipoUsoId(),lDeterminacionAnt);
//					        	
//				        	}else{
//				        		msg = msg.concat("Sin valor de Recojo en MatrizRecojo para anio: ").concat(anio.toString()).concat(". ");
//				        	}
//				        	
//			        	}
//			        }
//			        
//			        TotalArbitrio=TotalArbitrio+arbitrioSeguridad;
//		    	
//			    //(6) Determinacion de arbitrios generales
//		    	calculo=new DeterminacionArbitriosDTO();
//		    	//--Datos del calculo para su validacion
//		    	calculo.setArbitrioLimpieza(BigDecimal.valueOf(arbitrioLimpieza));
//		        calculo.setArbitrioParques(BigDecimal.valueOf(arbitrioParques));
//		        calculo.setArbitrioRecojo(BigDecimal.valueOf(arbitrioRecojo));
//		        calculo.setArbitrioSeguridad(BigDecimal.valueOf(arbitrioSeguridad));
//		        calculo.setFrecuenciaLimpieza(limpieza.getFrecuencia());
//		        calculo.setTasaMlAnualLimpieza(BigDecimal.valueOf(limpieza.getTasaMlAnual().doubleValue()));
//		        calculo.setFrenteMlLimpieza(BigDecimal.valueOf(djpredial.getFrente().doubleValue()));
//		        
//		        /** Se aplica la subvencion de acuerdo a la ordenanza 407 - 408 */
//		        calculo.setArbitrioLimpiezaAntesSubvencion(BigDecimal.valueOf(arbitrioLimpiezaPreSubven));
//		        calculo.setArbitrioRecojoAntesSubvencion(BigDecimal.valueOf(arbitrioRecojoPreSubven));
//		        calculo.setArbitrioSeguridadAntesBeneficio(BigDecimal.valueOf(arbitrioSeguridadPreSubven));
//		        calculo.setArbitrioParquesAntesSubvencion(BigDecimal.valueOf(arbitrioParquesPreSubven));
//		        
//		        /** De acuerdo a los beneficios de arbitrios municipales del 2014 */
//		        calculo.setArbitrioSeguridadAntesBeneficio(BigDecimal.valueOf(arbitrioSeguridadAntesBeneficios.doubleValue()));
//	        	calculo.setArbitrioParquesAntesBeneficio(BigDecimal.valueOf(arbitrioParquesAntesBeneficio.doubleValue()));
//	        	
//		    	//Calculo arbitrio de Recojo
//	        	calculo.setlRecojoArbitrio(lRecojoArbitrio);
//		        
//		    	//Calculo arbitrio Parques
//		        if(grupoCercania != null){
//		        	calculo.setGrupoCercaniaParques(grupoCercania.getDescipcionCorta());
//		        	calculo.setCostoAnualParques(BigDecimal.valueOf(grupoCercania.getCostoAnual().doubleValue()));
//		        }else{
//		        	calculo.setGrupoCercaniaParques(null);
//		        	calculo.setCostoAnualParques(BigDecimal.valueOf(0));
//		        }
//		    	
//		        //Calculo arbitrio de Seguridad
//		    	DtZonaSeguridad dtZonaSeguridad=mapDtZonaSeguridad.get(zonaSeguridad.getZonaSeguridadId());
//		    	calculo.setZonaSeguridad(dtZonaSeguridad.getDescripcionCorta());
//		    	calculo.setlSeguridadArbitrio(lSeguridadArbitrio);
//		    	//General
//		    	calculo.setDjId(djpredial.getDjId());
//		    	calculo.setAnnoDeterminacion(anio);
//		    	calculo.setDireccionCompleta(direccion.getDireccionCompleta());
//		    	//--
//		    	if(grupoCercania != null){
//		    		calculo.setGrupoCercaniaParquesId(grupoCercania.getGrupoCercaniaId());
//		    	}else{
//		    		calculo.setGrupoCercaniaParquesId(null);
//		    	}
//		    	
//		    	calculo.setFrecuenciaLimpiezaId(limpieza.getFrecuenciaLimpiezaId());
//		    	calculo.setZonaSeguridadId(dtZonaSeguridad.getZonaSeguridadId());
//		    	
//		    	getCalculoPredialBo().guardarCalculoArbitrios(calculo);
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//        return calculo;
//	}
	
	//AGREGANDO DJ USO//
	public Boolean guardarDeterminacion(RpDjpredial djpredial,Double TotalArbitrioPreSub,Double TotalArbitrio,Integer subConcepto,Integer anio,Integer tipoUsoId,List<DtDeterminacion> lDeterminacionAnt,Integer djUsoId)throws Exception{
	//public Boolean guardarDeterminacion(RpDjpredial djpredial,Double TotalArbitrioPreSub,Double TotalArbitrio,Integer subConcepto,Integer anio,Integer tipoUsoId,List<DtDeterminacion> lDeterminacionAnt)throws Exception{
		//Para controlar que no genere registros con cero durante la detreminaicon de arbitrios de un monto cero 
		if(TotalArbitrio>0){
			//Registro en la tabla Determinacion
	    	DtDeterminacion determinacion=new DtDeterminacion();
			determinacion.setDeterminacionId(Constante.RESULT_PENDING);
			determinacion.setPersonaId(djpredial.getPersonaId());
			//cc: determinacion.setUsuarioId(Constante.USUARIO_ID);
			//cc: determinacion.setFechaRegistro(DateUtil.getCurrentDate());
			//cc: determinacion.setTerminal(Constante.TERMINAL);
			
			determinacion.setAnnoDeterminacion(anio);
			determinacion.setConceptoId(Constante.CONCEPTO_ARBITRIO);
			determinacion.setSubconceptoId(subConcepto);
			//AGREGANDO DJ USO//
			determinacion.setDjusoId(djUsoId);//Agregado para identificar las determinaciones por usos
			determinacion.setNroCuotas(4);
			determinacion.setNroDocumento(null);
			
			//--
			Double dblPorcPropiedad = djpredial.getPorcPropiedad().doubleValue();
			
			if(dblPorcPropiedad == null){
				dblPorcPropiedad = new Double(100);						
			}
			
			Double dblTotalArbitrio = TotalArbitrio * dblPorcPropiedad / 100;
			
			/**
			 * Previo a la subvencion aplicado por ordenza de arbitrios::inicio
			 */
			Double dblTotalArbitrioPreSub = TotalArbitrioPreSub * dblPorcPropiedad / 100;
			determinacion.setBaseImponiblePreSub(BigDecimal.valueOf(dblTotalArbitrioPreSub).setScale(2, RoundingMode.HALF_UP));
			/**
			 * Previo a la subvencion aplicado por ordenza de arbitrios::fin
			 */
			/*
			 *Se realiza el valor de los arbitrios por porcentaje 
			 * determinacion.setBaseImponible(BigDecimal.valueOf(TotalArbitrio).setScale(2, RoundingMode.HALF_UP));
			 * determinacion.setBaseAfecta(BigDecimal.valueOf(TotalArbitrio).setScale(2, RoundingMode.HALF_UP));
			 * determinacion.setImpuesto(BigDecimal.valueOf(TotalArbitrio).setScale(2, RoundingMode.HALF_UP));
			 * */
			determinacion.setBaseImponible(BigDecimal.valueOf(dblTotalArbitrio).setScale(2, RoundingMode.HALF_UP));
			//determinacion.setBaseImponibleFisca(BigDecimal.valueOf(0));
			determinacion.setBaseAfecta(BigDecimal.valueOf(dblTotalArbitrio).setScale(2, RoundingMode.HALF_UP));
			//determinacion.setBaseAfectaFisca(BigDecimal.valueOf(0));		
			determinacion.setBaseExonerada(BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_UP));
			//determinacion.setBaseExoneradaFisca(BigDecimal.valueOf(0));
			determinacion.setImpuesto(BigDecimal.valueOf(dblTotalArbitrio).setScale(2, RoundingMode.HALF_UP));
			
			
			determinacion.setImpuestoDiferencia(BigDecimal.valueOf(0));
			//determinacion.setImpuestoFisca(BigDecimal.valueOf(0));
			determinacion.setPorcPropiedad(BigDecimal.valueOf(0));
			
			/*En estas variables se almacena el valor por el 100% de los ARBITRIOS
			 * */
	//		determinacion.setBaseAfectaAnterior(BigDecimal.valueOf(0));
	//		determinacion.setBaseImponibleAnterior(BigDecimal.valueOf(0));
	//		determinacion.setImpuestoAnterior(BigDecimal.valueOf(0));
			
			determinacion.setBaseImponibleAnterior(BigDecimal.valueOf(TotalArbitrio).setScale(2, RoundingMode.HALF_UP));
			determinacion.setBaseAfectaAnterior(BigDecimal.valueOf(TotalArbitrio).setScale(2, RoundingMode.HALF_UP));
			determinacion.setBaseExoneradaAnterior(BigDecimal.valueOf(0));
			determinacion.setImpuestoAnterior(BigDecimal.valueOf(TotalArbitrio).setScale(2, RoundingMode.HALF_UP));
			
			
			//--
			determinacion.setFiscalizado(Constante.FISCALIZADO_NO);
			determinacion.setFiscaAceptada(Constante.FISCA_ACEPTADA_NO);
			determinacion.setFiscaCerrada(Constante.FISCA_CERRADA_NO);
			//--
			determinacion.setEstado(Constante.ESTADO_ACTIVO);
			determinacion.setDjreferenciaId(djpredial.getDjId());
			determinacion.setTipoUsoId(tipoUsoId);
			
			Integer Id=getCalculoPredialBo().guardarDeterminacion(determinacion);
			determinacion.setDeterminacionId(Id);
			//EN CD_DEUDA
			generarDeuda(djpredial,determinacion,subConcepto);
			
//			if(lDeterminacionAnt!=null&&lDeterminacionAnt.size()>0){
//				Iterator<DtDeterminacion> it0 = lDeterminacionAnt.iterator();
//		        while (it0.hasNext()){
//		        	DtDeterminacion determinacionAnt = it0.next();
//	        		if(determinacionAnt!=null&&determinacionAnt.getSubconceptoId()!=null&&determinacionAnt.getSubconceptoId().compareTo(subConcepto)==0){
//	//        			System.out.println(determinacionAnt.getSubconceptoId()+" "+subConcepto);
//	        			DeudaPagosDTO deuPagPrevio = getCalculoPredialBo().getDeudaPagos(determinacionAnt.getDeterminacionId());
//	    	        	if(deuPagPrevio.getTotalCancelado()!=null&&deuPagPrevio.getTotalCancelado().doubleValue() > 0){
//	    	        		calculoPredialBo.inputarDeudaSinValores(determinacionAnt.getDeterminacionId(), determinacion.getDeterminacionId());	
//	    	        	}
//		        	}
//		        	
//		        	if(determinacionAnt!=null&&determinacionAnt.getSubconceptoId()!=null&&determinacionAnt.getSubconceptoId().compareTo(Constante.SUB_CONCEPTO_ARBITRIOS_LIMPIEZA_PUBLICA)==0){
//		        		DeudaPagosDTO deuPagPrevio = getCalculoPredialBo().getDeudaPagos(determinacionAnt.getDeterminacionId());
//	    	        	if(deuPagPrevio.getTotalCancelado()!=null&&deuPagPrevio.getTotalCancelado().doubleValue() > 0){
//	    	        		//lmsg.add("No se ha podido imputar el pago realizado "+deuPagPrevio.getTotalCancelado()+" del concepto de Limpieza a la nueva deuda generada porfavor realize la descarga de deuda de manera directa.");
//	    	        	}
//		        	}
//	        	}
//		    }
		}
		return Boolean.TRUE;
	}
	
//	public Boolean correspondeCuota(RpDjpredial rpDjPredial,DtFechaVencimiento fechaVenc){
//		if(rpDjPredial.getMotivoDeclaracionId().compareTo(Constante.MOTIVO_DECLARACION_DESCARGO)==0){
//			Calendar calFecDes=Calendar.getInstance();
//			calFecDes.setTime(rpDjPredial.getFechaDescargo());
//			
//			Calendar calFecVen=Calendar.getInstance();
//			calFecVen.setTime(fechaVenc.getFechaVencimiento());
//							
//			if(DateUtil.diferenciaFechas(calFecDes,calFecVen,1)<=0){
//				return Boolean.TRUE; 
//			}else{
//				return Boolean.FALSE;
//			}
//		}else{
//			Calendar calFecAdq=Calendar.getInstance();
//			calFecAdq.setTime(rpDjPredial.getFechaAdquisicion());
//			
//			Calendar calFecVen=Calendar.getInstance();
//			calFecVen.setTime(fechaVenc.getFechaVencimiento());
//							
//			if(DateUtil.diferenciaFechas(calFecAdq,calFecVen, 1)>0){
//				return Boolean.TRUE; 
//			}else{
//				return Boolean.FALSE;
//			}
//		}
//	}
	
	public Boolean generarDeuda(RpDjpredial djpredial,DtDeterminacion determinacion,Integer subConcepto)throws Exception{
		List<DtFechaVencimiento> listaFV = getCalculoPredialBo().getFechaVencimiento(subConcepto,determinacion.getAnnoDeterminacion());
		DtCuotaConcepto cuotas = getCalculoPredialBo().getCuotasConcepto(subConcepto, determinacion.getAnnoDeterminacion());
		Integer numeroCuotas=cuotas.getNroCuotas();
		
		BigDecimal montoCuota = determinacion.getImpuesto().divide(new BigDecimal(String.valueOf(numeroCuotas)), 2,RoundingMode.HALF_UP);
		
		for(int i=0;i<numeroCuotas;i++){
			
//			if(correspondeCuota(djpredial,listaFV.get(i))){
				CdDeuda deuda = new CdDeuda();
				deuda.setTipoDeudaId(Constante.TIPO_DEUDA_AUTOGENERADO);
				deuda.setDeudaId(Constante.RESULT_PENDING);
				deuda.setPersonaId(determinacion.getPersonaId());
				deuda.setConceptoId(Constante.CONCEPTO_ARBITRIO);
				deuda.setSubconceptoId(subConcepto);
				deuda.setDeterminacionId(determinacion.getDeterminacionId());
				deuda.setAnnoDeuda(determinacion.getAnnoDeterminacion());
				deuda.setFechaEmision(DateUtil.getCurrentDate());
				deuda.setFechaVencimiento(listaFV.get(i).getFechaVencimiento());
				deuda.setNroCuota(listaFV.get(i).getCuota());
				deuda.setMontoOriginal(determinacion.getImpuesto());
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
				
				if (deuda.getNroCuota() == cuotas.getCuotaDerechoEmision()) {
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
			
//		}
		
		return Boolean.TRUE;
	}
	
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