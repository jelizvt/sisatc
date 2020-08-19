package com.sat.sisat.alcabala.business;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import com.sat.sisat.alcabala.BuscarAlcabalaDTO;
import com.sat.sisat.determinacion.vehicular.dto.DatosInafecDTO;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.persistence.entity.CdDeuda;
import com.sat.sisat.persistence.entity.CdDeudaHistorica;
import com.sat.sisat.persistence.entity.DtDeterminacion;
import com.sat.sisat.persistence.entity.GnNotaria;
import com.sat.sisat.persistence.entity.GnTipoDocumento;
import com.sat.sisat.persistence.entity.GnTipoMoneda;
import com.sat.sisat.persistence.entity.RaAlcabalaSustento;
import com.sat.sisat.persistence.entity.RaAlcabalaTasa;
import com.sat.sisat.persistence.entity.RaDireccionAlcabala;
import com.sat.sisat.persistence.entity.RaDireccionAlcabalaHistorico;
import com.sat.sisat.persistence.entity.RaDjalcabala;
import com.sat.sisat.persistence.entity.RaDjalcabalaHistorico;
import com.sat.sisat.persistence.entity.RaTipoTransferencia;
import com.sat.sisat.persistence.entity.RaTransferenteAlcabala;
import com.sat.sisat.persistence.entity.RpCondicionPropiedad;
import com.sat.sisat.persistence.entity.RpDjpredial;
import com.sat.sisat.predial.dto.FindRpDjPredial;
import com.sat.sisat.vehicular.dto.AnexosDeclaVehicDTO;
import com.sat.sisat.vehicular.dto.BuscarPersonaDTO;

@Remote
public interface AlcabalaRemote {

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
	 * Actualiza trasnferente
	 * @param buscaPersonaDTO
	 * @return
	 * @throws Exception
	 */
	public int actualizaRaTransferente(int djAlcabalaId)throws Exception;
	
	
	
	
	
	/**
	 * Actualiza Direccion Alcabala
	 * @param raDirecAlca
	 * @return
	 * @throws Exception
	 */
	public int actualizaRaDireccionAlcabala(RaDireccionAlcabala raDirecAlca)throws Exception;

	
	
	/**
	 * Guarda los documentos anexos de DJAlcabala
	 */
	// public void guardarDocAnexos(List<RaAlcabalaSustento> lstAnexos)
	public void guardarDocAnexos(RaAlcabalaSustento raAlcabalaSustento)	throws Exception;
	
	/**
	 * Guarda la direccion de alcabala
	 */
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
	public ArrayList<BuscarPersonaDTO> getRaTransferenteAlcabalaByDJAlcabajaId(int djAlcabalaId)throws Exception;
	
	/**
	 * 
	 * @param djAlcabalaId
	 * @return
	 * @throws Exception
	 */
	public ArrayList<AnexosDeclaVehicDTO> getRaAlcabalaSustentoByDjAlcabalaId(int djAlcabalaId)throws Exception;
	
	/**
	 * 
	 * @param djAlcabalaId
	 * @return
	 * @throws Exception
	 */
	public RaDireccionAlcabala getRaDireccionAlcabalaByDjAlcabalaId(int djAlcabalaId, int direccionAlcaId)throws Exception;
	
	/**
	 * Obtiene la Tasa segun Fecha
	 * @param fecha
	 * @return
	 * @throws Exception
	 */
	public RaAlcabalaTasa getTasa(int alTasaId)throws Exception;
	
	/**
	 * Actualiza Documentos Anexos de Alcabala
	 * @param raAlcaSus
	 * @return
	 * @throws Exception
	 */
	public int actualizaAlcabalaSustento(int djAlcabalaId)throws Exception;
	
	/**
	 * Trae los datos de persona Inafecta
	 * @param idPersona
	 * @return
	 * @throws Exception
	 */
	public DatosInafecDTO getInafecAlcabala(int idPersona)throws Exception;
	
	/**
	 * 
	 * @param predioId
	 * @param annoTransfer
	 * @return
	 * @throws Exception
	 */
	public BigDecimal obtenerAutovaluoPredio(int predioId, int annoTransfer)throws Exception;
	
	/**
	 * Guarda la determinacion de una DJ de Alcabala
	 * @param dtDeterminacion
	 * @throws Exception
	 */
	public void  guardarDeterminacionALcabala(DtDeterminacion dtDeterminacion)throws Exception;
	
	/**
	 * Guarda la deuda de una DJ de Alcabala
	 * @param deuda
	 * @throws Exception
	 */
	public void guardarDeudaAlcabala(CdDeuda deuda)throws Exception;
	
	/**
	 * Guardar la Deuda Historica de una DJ de ALcabala
	 * @param dh
	 * @throws Exception
	 */
	public void guardarDeudaHistoricaAlcabala(CdDeudaHistorica dh)throws Exception;
	
	/**
	 * 
	 * @param personaId
	 * @param periodo
	 * @param concepto
	 * @param estado
	 * @return
	 * @throws Exception
	 */
	public ArrayList<DtDeterminacion> getAllDtDeterminacion(Integer personaId,Integer periodo,Integer concepto,String estado) throws Exception;
	

	/**
	 * 
	 * @param dtDeter
	 * @return
	 * @throws Exception
	 */
	public int actualizaDtDeterminacionALcabala(DtDeterminacion dtDeter)throws Exception;
	
	public int actualizaDeudaALcabala(int deterId)throws Exception;
	
	public List<RpCondicionPropiedad> getAllRpCondicionPropiedad()
			throws Exception;
	
	public RpDjpredial getRpDjPredial(Integer djId) throws SisatException;
	
	public List<GnTipoDocumento> obtenerTipoDocumentos() throws SisatException;

	public abstract void eliminarDJAlcabala(BuscarAlcabalaDTO currentItem) throws SisatException;
	
}
