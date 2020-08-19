package com.sat.sisat.determinacion.vehicular.business;

import javax.ejb.Remote;

import com.sat.sisat.determinacion.vehicular.calculo.ImpuestoVehicular;
import com.sat.sisat.determinacion.vehicular.dto.DatosNecesariosDeterDTO;
import com.sat.sisat.exception.SisatException;


@Remote
public interface DeterminacionVehicularBoRemote {

//	public abstract boolean generarDeterminacion(int djvehicularId,
//			int usuarioId, String terminal) throws SisatException;
	public abstract int generarDeterminacion(int djvehicularId,
			int usuarioId, String terminal) throws SisatException;

	public abstract ImpuestoVehicular calcular(DatosNecesariosDeterDTO datos)throws SisatException;
	
	public void duplicarDj(int anho, int user, String terminal, Integer inicio, Integer fin )throws SisatException;
	
	public void generarDeterminacionPorAnho(int anho, int user, String terminal) throws SisatException;
	
	public Integer actualizarDeterminacionPrevia(Integer determinacion,Integer usuario,String estado)throws Exception;
	
	public Integer actualizarDeterminacionVehicularPrevia(Integer determinacion,Integer usuario,String estado)throws Exception;
	
}