package com.sat.sisat.alcabala.business;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import com.sat.sisat.alcabala.BuscarAlcabalaDTO;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.persistence.entity.GnNotaria;
import com.sat.sisat.persistence.entity.GnTipoDocumento;
import com.sat.sisat.persistence.entity.GnTipoMoneda;
import com.sat.sisat.persistence.entity.RaAlcabalaSustento;
import com.sat.sisat.persistence.entity.RaDireccionAlcabala;
import com.sat.sisat.persistence.entity.RaDireccionAlcabalaHistorico;
import com.sat.sisat.persistence.entity.RaDjalcabala;
import com.sat.sisat.persistence.entity.RaDjalcabalaHistorico;
import com.sat.sisat.persistence.entity.RaTipoTransferencia;
import com.sat.sisat.persistence.entity.RaTransferenteAlcabala;
import com.sat.sisat.persistence.entity.RpCondicionPropiedad;
import com.sat.sisat.persistence.entity.RpDjpredial;
import com.sat.sisat.predial.dto.FindRpDjPredial;

@Local
public interface AlcabalaBoLocal {

	/**
	 * Obtiene la lista de notarias registradas
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract List<GnNotaria> getAllGnNotaria() throws Exception;

	/*
	 * 
	 */
	public abstract List<RaTipoTransferencia> getAllRaTipoTransferencia()
			throws Exception;

	public List<GnTipoMoneda> getAllGnTipoMoneda() throws Exception;

	/**
	 * trae IPM segun a�o y mes
	 */
	public BigDecimal getIPM(int anio, int mes) throws Exception;

	/**
	 * trae DJ de alcabalas por aportante
	 */
	public ArrayList<BuscarAlcabalaDTO> getAllRaDjalcabala(Integer personaId, Integer djAlcabala, Date fechaInicio, Date fechaFin)
			throws Exception;

	/**
	 * Guarda la DJ de alcabala
	 */
	public void guardaDjAlcabala(RaDjalcabala raDjalcabala) throws Exception;
	
	/**
	 * Confirma una DJAlcabala
	 * @param raDjalcabala
	 * @throws Exception
	 */
	public int actualizaDjAlcabala(RaDjalcabala raDjalcabala)throws Exception;
	
	/**
	 * GUARDA raDjalcabalaHistorico
	 * @param raDjalcabalaHistorico
	 * @throws Exception
	 */
	public void guardaDjAlcabalaHistorico(RaDjalcabalaHistorico raDjalcabalaHistorico) throws Exception;

	/**
	 * Guarda los documentos anexos de DJAlcabala
	 */
	// public void guardarDocAnexos(List<RaAlcabalaSustento> lstAnexos)
	public void guardarDocAnexos(RaAlcabalaSustento raAlcabalaSustento)
			throws Exception;
	
	public void guardarDireccionAlcabala(RaDireccionAlcabala raDireccionAlcabala)throws Exception;
	
	/**
	 * Guarda la direccion Historica de alcabala
	 */
	public void guardarDireccionAlcabalaHstorico(RaDireccionAlcabalaHistorico raDireccionAlcabalaHistorico)throws Exception;
	
	
	/**
	 * Guarda el transferente de alcabala
	 */
	public void guardarTransferenteAlcabala(RaTransferenteAlcabala raTransferenteAlcabala)throws Exception;
	
	/**
	 * 
	 * @param djAlcabalaId
	 * @return
	 * @throws Exception
	 */
	//public RaDireccionAlcabala getRaDireccionAlcabalaByDjAlcabalaId(int djAlcabalaId)throws Exception;
	
	public List<RpCondicionPropiedad> getAllRpCondicionPropiedad()
			throws Exception;

	public RpDjpredial getRpDjPredial(Integer djId) throws SisatException;
	
	public List<GnTipoDocumento> obtenerTipoDocumentos() throws SisatException;
	
	public abstract void eliminarDJAlcabala(BuscarAlcabalaDTO currentItem) throws SisatException;
}
