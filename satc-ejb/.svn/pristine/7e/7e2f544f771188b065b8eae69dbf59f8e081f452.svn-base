package com.sat.sisat.obligacion.business;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import com.sat.sisat.cobranzacoactiva.dto.DetencionDeudaDTO;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.obligacion.dto.MultaDTO;
import com.sat.sisat.obligacion.dto.ObligacionDTO;
import com.sat.sisat.obligacion.dto.SubConceptoDTO;
import com.sat.sisat.persistence.entity.CcRec;
import com.sat.sisat.persistence.entity.DtFechaVencimiento;


@Remote
public interface ObligacionBoRemote {

	/**
	 * Método que obtiene los sub conceptos de Gastos asociado a un año
	 * 
	 * @param anho
	 * @return
	 * @throws SisatException
	 */
	public List<SubConceptoDTO> getSubConceptoGastos(int anho) throws SisatException;
	
	/**
	 * Método que obtiene los sub conceptos de Costas asociado a un año
	 * 
	 * @param anho
	 * @return
	 * @throws SisatException
	 */
	public List<SubConceptoDTO> getSubConceptoCostas(int anho,Integer subconceptoId) throws SisatException;
	
	public List<SubConceptoDTO> getSubConceptoCostas(int anho) throws SisatException;
	
	public List<SubConceptoDTO> getSubConceptoCostasEmbargo(int anho) throws SisatException ;
	/**
	 * Método que obtiene los sub conceptos de Multas asociado a un año
	 * 
	 * @param anho
	 * @return
	 * @throws SisatException
	 */
	public List<SubConceptoDTO> getSubConceptoMultas(int anho) throws SisatException;	
	
	/**
	 * Método que obtiene los sub conceptos de EPND asociado a un año
	 * 
	 * @param anho
	 * @return
	 * @throws SisatException
	 */
	public List<SubConceptoDTO> getSubConceptoEPND(int anho) throws SisatException;
	
	/**
	 * Método que obtiene los sub conceptos de MULTAS DRTPE asociado a un año
	 * 
	 * @param anho
	 * @return
	 * @throws SisatException
	 */
	public List<SubConceptoDTO> getSubConceptoMULTASDRTPE(int anho) throws SisatException;
	
	/**
	 * Método que registra las obligaciones para que luego sean cobrabas en caja
	 * 
	 * @param listObligacionDTOs Contiene la lista de obligaciones 
	 * @param personaId		Identificador de la persona que tiene las obligaciones
	 * @throws SisatException
	 */
	public void saveObligaciones(List<ObligacionDTO> listObligacionDTOs, int personaId) throws SisatException;
		
	public boolean checkUnidadOrganica(String unidadOrganica) throws SisatException;
	
	public boolean checkTipoDocumentoMulta(int tipoDocumentoId) throws SisatException;
	
	public CcRec obtenerRecByNroRec(String nroRec, int persona_id)throws SisatException;
	
	public void generarMulta(int personaId, ObligacionDTO obligacionDTO) throws SisatException;
	
	public void generarResolucionMulta(Integer loteId,Integer personaId, Integer unidad,Integer usuarioId,String terminal,Integer subConceptoId) throws Exception;
//	public void generarResolucionMulta(Integer loteId,Integer personaId, Integer unidad,Integer usuarioId,String terminal,Integer subConceptoId,Integer loteVehId) throws Exception;
	
	public List<MultaDTO> buscarMultas(ObligacionDTO obligacionDTO) throws SisatException;	
	
	public List<DetencionDeudaDTO> buscarDetenciones(Integer personaId,Integer anioDeuda,Integer papeletaId) throws SisatException;
	
	public List<DetencionDeudaDTO> buscarDetencionesPorPapeletaId(Integer papeletaId) throws SisatException; 
	
	public void desactivarDetencion(Integer personaId, Integer conceptoId, Integer subConceptoId, Integer anho, Integer determinacionId,String nroExped)throws SisatException;
	
	public void activarDetencion(Integer personaId, Integer conceptoId, Integer subConceptoId, Integer anho, Integer determinacionId,String nroExped) throws SisatException;
	
	public void activarDetencionPapeleta(String nroPapeleta) throws SisatException;
	
	public void notificarResolucionMulta(String nroPapeleta) throws SisatException;
	
	public void desactivarDetencionPapeleta(String nroActo) throws SisatException;

	public Integer obtenerDiasHabiles(Date fechaAdquision, Date time) throws SisatException;
	
	public Date sumarDiasHabiles(Date a, int b) throws SisatException ;
	
	public List<DtFechaVencimiento> getFechaVencimiento(Integer conceptoId,Integer anioAfectacion)throws Exception;
	
	public List<SubConceptoDTO> getSubConceptoMULTA(int tipo) throws SisatException;
}
