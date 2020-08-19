package com.sat.sisat.predial.managed.calculo;

import java.util.Iterator;
import java.util.List;

import com.sat.sisat.common.util.Constante;
import com.sat.sisat.persistence.entity.DtDeterminacion;
import com.sat.sisat.persistence.entity.RpDjpredial;

public class EstadoDeterminacionPredial {

	public static Integer ACEPTADO_CERRADO=0;
	public static Integer NO_ACEPTADO_NO_CERRADO=1;
	public static Integer NO_ACEPTADO_CERRADO=2;
	public static Integer NO_FISCALIZADO=-1;
	
	/**
	 * DJ ==> DETERMINACION NUEVA
	 * [NO ACEPTADO - NO CERRADO] No genera deuda
	 * 
 	Estado Deter	Estado Fiscalizaci�n		Descripci�n	Acci�n
	Fiscalizado		Aceptado					Indica que la fiscalizaci�n de todos los predios revisados ha sido ACEPTADO por el contribuyente y la fiscalizaci�n se cierra.	
											La nueva determinaci�n reemplza a la existe (si nohay valores)
	
	Fiscalizado		No Aceptado					Indica que el estado de la fiscalizaci�n de al menos un predio es NO ACEPTADO por el contribuyente, la fiscalizaci�n a�n esta abierta, por ello es un estado temporal.	
											La nueva determinaci�n no reemplaza la existente, se mantiene dos determinaciones.
	
	
	
	******
	Fiscalizado		No Aceptado y Cerrada 		Indica que el estado de la fiscalizaci�n de los predios sean ACEPTADO y NO ACEPTADO Y CERRADA LA FISACLIZACI�N, donde al menos un predio tenga el estado NO ACEPTADO Y CERRADO POR FISCALIZACION	
											La nueva determinaci�n no reemplaza la existente, se mantiene dos determinaciones, 
											SE REALIZA DETERMINACI�N DE LA CUENTA CORRIENTE POR DIFERENCIA, respecto de la determinaci�n original
	 
	 * @param lRpDjpredial
	 * @return
	 */
	
	//0 : ACEPTADO | CERRADO
	//1 : NO ACEPTADO | NO CERRADO
	//2 : NO ACEPTADO | CERRADO
	//-1 : ERROR
	public static Integer estadoDtDeterminacion(List<RpDjpredial> lRpDjpredial)throws Exception{
		if(existeNoAceptadoNoCerrado(lRpDjpredial)){
			return NO_ACEPTADO_NO_CERRADO;
		}else if(existeNoAceptadoCerrado(lRpDjpredial)){
			return NO_ACEPTADO_CERRADO;
		}else if(existeAceptado(lRpDjpredial)){
			return ACEPTADO_CERRADO;
		}
		return NO_FISCALIZADO;
	}
	
	//--Inicio
	private static Boolean existeNoAceptadoNoCerrado(List<RpDjpredial> lRpDjpredial){
		Iterator<RpDjpredial> it=lRpDjpredial.iterator();
		while(it.hasNext()){
			RpDjpredial rpDjpredial=it.next();
			if(rpDjpredial.getFiscalizado()!=null&&rpDjpredial.getFiscalizado().equals(Constante.FISCALIZADO_SI)){
				if(rpDjpredial.getFiscaAceptada().equals(Constante.FISCA_ACEPTADA_NO)&&
						rpDjpredial.getFiscaCerrada().equals(Constante.FISCA_CERRADA_NO)){
					return Boolean.TRUE;
				}
			}
		}
		return Boolean.FALSE;
	}
	
	private static Boolean existeNoAceptadoCerrado(List<RpDjpredial> lRpDjpredial){
		Iterator<RpDjpredial> it=lRpDjpredial.iterator();
		while(it.hasNext()){
			RpDjpredial rpDjpredial=it.next();
			if(rpDjpredial.getFiscalizado()!=null&&rpDjpredial.getFiscalizado().equals(Constante.FISCALIZADO_SI)){
				if(rpDjpredial.getFiscaAceptada().equals(Constante.FISCA_ACEPTADA_NO)&&
						rpDjpredial.getFiscaCerrada().equals(Constante.FISCA_CERRADA_SI)){
					return Boolean.TRUE;
				}
			}
		}
		return Boolean.FALSE;
	}
	
	private static Boolean existeAceptado(List<RpDjpredial> lRpDjpredial){
		Iterator<RpDjpredial> it=lRpDjpredial.iterator();
		while(it.hasNext()){
			RpDjpredial rpDjpredial=it.next();
			if(rpDjpredial.getFiscalizado()!=null&&rpDjpredial.getFiscalizado().equals(Constante.FISCALIZADO_SI)){
				if(rpDjpredial.getFiscaAceptada().equals(Constante.FISCA_ACEPTADA_SI)){
					return Boolean.TRUE;
				}
			}
		}
		return Boolean.FALSE;
	}
	//--Fin
	
	
}
