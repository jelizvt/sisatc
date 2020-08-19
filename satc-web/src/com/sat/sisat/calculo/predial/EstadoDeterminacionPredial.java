package com.sat.sisat.calculo.predial;

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
	 *	DJ => DJ nueva
	 *  Estado Predio	Estado Fiscalizaci�n	Nuevo Estado de fiscalizaci�n	Acci�n
		Fiscalizado	Aceptado	Aceptado	No es posible
		Fiscalizado	Aceptado	No Aceptado	No es posible
		Fiscalizado	Aceptado	No Aceptado y cerrada la fiscalizaci�n	No es posible
					
		Fiscalizado	No Aceptado	Aceptado	Registra una nueva DJ del predio, en reemplazo de la anterior y ser� la unica DJ activa para el cada a�o de obligaci�n
		Fiscalizado	No Aceptado	No Aceptado	Si es posible, cuando el fiscalizador ajuste sus datos, pero desactiva la anterior, solo debe haber una sola DJ con estado NO ACEPTADO.
		Fiscalizado	No Aceptado	No Aceptado y cerrada la fiscalizaci�n	Genera DJ nueva del predio.
					
		Fiscalizado	No Aceptado y cerrada la fiscalizaci�n	Aceptado	No es posible ( La aceptaci�n es con el pago)
		Fiscalizado	No Aceptado y cerrada la fiscalizaci�n	No Aceptado	No es posible
		Fiscalizado	No Aceptado y cerrada la fiscalizaci�n	No Aceptado y cerrada la fiscalizaci�n	No es posible

	 * @param rpDjpredial
	 * @param nuevoFiscaAceptada
	 * @return
	 */
	private static Boolean siguienteEstadoFisca(RpDjpredial rpDjpredial,String nuevoFiscaAceptada){
		if(rpDjpredial.getFiscalizado().equals(Constante.FISCALIZADO_SI)){
			if(rpDjpredial.getFiscaAceptada().equals(Constante.FISCA_ACEPTADA_NO)&&
					rpDjpredial.getFiscaCerrada().equals(Constante.FISCA_CERRADA_NO)&&
					nuevoFiscaAceptada.equals(Constante.FISCA_ACEPTADA_SI)){
					return Boolean.TRUE;
			}if(rpDjpredial.getFiscaAceptada().equals(Constante.FISCA_ACEPTADA_NO)&&
					rpDjpredial.getFiscaCerrada().equals(Constante.FISCA_CERRADA_NO)&&
					nuevoFiscaAceptada.equals(Constante.FISCA_ACEPTADA_NO)){
					return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}
	
	/**
	 *  DETERMINACION => DETERMINACION nueva
	 *  Estado Deter	Estado Fiscalizaci�n	Nuevo Estado de fiscalizaci�n	Acci�n
		Fiscalizado	Aceptado	Aceptado	No es posible
		Fiscalizado	Aceptado	No Aceptado	No es posible
		Fiscalizado	Aceptado	No Aceptado y cerrada la fiscalizaci�n	No es posible
					
		Fiscalizado	No Aceptado	Aceptado	Genera determinaci�n y cta cte. ( Casos )
		Fiscalizado	No Aceptado	No Aceptado	Si es posible, cuando el fiscalizador ajuste sus datos, pero desactiva la anterior, solo debe haber una sola determinaci�n con estado NO ACEPTADO.
		Fiscalizado	No Aceptado	No Aceptado y cerrada la fiscalizaci�n	Genera Determinacion ( Casos )
					
		Fiscalizado	No Aceptado y cerrada la fiscalizaci�n	Aceptado	No es posible
		Fiscalizado	No Aceptado y cerrada la fiscalizaci�n	No Aceptado	No es posible
		Fiscalizado	No Aceptado y cerrada la fiscalizaci�n	No Aceptado y cerrada la fiscalizaci�n	No es posible

	 * @param determinacion
	 * @param nuevoFiscaAceptada
	 * @return
	 */
	private static  Boolean siguienteEstadoFisca(DtDeterminacion determinacion,String nuevoFiscaAceptada){
		if(determinacion.getFiscalizado().equals(Constante.FISCALIZADO_SI)){
			if(determinacion.getFiscaAceptada().equals(Constante.FISCA_ACEPTADA_NO)&&
					determinacion.getFiscaCerrada().equals(Constante.FISCA_CERRADA_NO)&&
					nuevoFiscaAceptada.equals(Constante.FISCA_ACEPTADA_SI)){
					return Boolean.TRUE;
			}if(determinacion.getFiscaAceptada().equals(Constante.FISCA_ACEPTADA_NO)&&
					determinacion.getFiscaCerrada().equals(Constante.FISCA_CERRADA_NO)&&
					nuevoFiscaAceptada.equals(Constante.FISCA_ACEPTADA_NO)){
					return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}
	
	/**
	 * DJ ==> DETERMINACION NUEVA
 	Estado Deter	Estado Fiscalizaci�n	Descripci�n	Acci�n
	Fiscalizado	Aceptado	Indica que la fiscalizaci�n de todos los predios revisados ha sido ACEPTADO por el contribuyente y la fiscalizaci�n se cierra.	La nueva determinaci�n reemplza a la existe (si nohay valores)
	Fiscalizado	No Aceptado	Indica que el estado de la fiscalizaci�n de al menos un predio es NO ACEPTADO por el contribuyente, la fiscalizaci�n a�n esta abierta, por ello es un estado temporal.	La nueva determinaci�n no reemplaza la existente, se mantiene dos determinaciones.
	Fiscalizado	No Aceptado y cerrada la fiscalizaci�n	Indica que el estado de la fiscalizaci�n de los predios sean ACEPTADO y NO ACEPTADO Y CERRADA LA FISACLIZACI�N, donde al menos un predio tenga el estado NO ACEPTADO Y CERRADO POR FISCALIZACION	La nueva determinaci�n no reemplaza la existente, se mantiene dos determinaciones, SE REALIZA DETERMINACI�N DE LA CUANTA CORRIENTE POR DIFERENCIA, respecto de la determinaci�n original
	 * @param lRpDjpredial
	 * @return
	 */
	
	//0 : ACEPTADO | CERRADO
	//1 : NO ACEPTADO | NO CERRADO
	//2 : NO ACEPTADO | CERRADO
	//-1 : ERROR
	public static int estadoDtDeterminacion(List<RpDjpredial> lRpDjpredial)throws Exception{
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
