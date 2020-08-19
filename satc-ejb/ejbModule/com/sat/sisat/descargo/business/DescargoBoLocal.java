package com.sat.sisat.descargo.business;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import com.sat.sisat.caja.dto.AgenciaOperacionDTO;
import com.sat.sisat.caja.dto.AgenciaUsuarioDTO;
import com.sat.sisat.caja.dto.CajaAperturaDTO;
import com.sat.sisat.caja.dto.CajeroDTO;
import com.sat.sisat.caja.dto.CjCajaCuadreEntity;
import com.sat.sisat.caja.dto.CjCobranza;
import com.sat.sisat.caja.dto.CjGenerico;
import com.sat.sisat.caja.dto.CjMotivos;
import com.sat.sisat.caja.dto.CjPapeletaDTO;
import com.sat.sisat.caja.dto.CjPartidaEntity;
import com.sat.sisat.caja.dto.CjPersona;
import com.sat.sisat.caja.dto.CjReciboDetalleEntity;
import com.sat.sisat.caja.dto.CjReciboEntity;
import com.sat.sisat.caja.dto.CjReciboPagoEntity;
import com.sat.sisat.caja.dto.CjTipoDocumento;
import com.sat.sisat.caja.dto.CjTupaDTO;
import com.sat.sisat.caja.dto.DeudaDTO;
import com.sat.sisat.caja.dto.ReciboPagoDTO;
import com.sat.sisat.caja.dto.ReciboPagoDetalleDTO;
import com.sat.sisat.caja.dto.ReciboPagoDetallePieDTO;
import com.sat.sisat.caja.dto.ReciboPagoFormaPagoDTO;
import com.sat.sisat.common.dto.GenericDTO;
import com.sat.sisat.descargo.dto.ReciboPagoDescargo;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.persistence.entity.CdDescargo;
import com.sat.sisat.persistence.entity.CjAgencia;
import com.sat.sisat.persistence.entity.CjAgenciaOperacion;
import com.sat.sisat.persistence.entity.CjAgenciaUsuario;
import com.sat.sisat.persistence.entity.CjCajaApertura;
import com.sat.sisat.persistence.entity.CjPagoTupa;
import com.sat.sisat.persistence.entity.CjReciboDetalle;
import com.sat.sisat.persistence.entity.CjReciboPago;
import com.sat.sisat.persistence.entity.GnTipoCambio;
import com.sat.sisat.persistence.entity.GnTipoDocumento;
import com.sat.sisat.tramitedocumentario.dto.ItemBandejaEntradaDTO;

@Remote
public interface DescargoBoLocal {
	
	public abstract List<DeudaDTO> obtenerTodaDeuda(int personaId ) throws SisatException;
	
	public abstract void descargarDeudas(CdDescargo cdDescargo, List<DeudaDTO> deudasAEliminar) throws SisatException;
	
	public abstract void descargarDeudas(CdDescargo cdDescargo, List<DeudaDTO> deudasAEliminar,Date fechaCompensacion, Integer persona_transferencia_id , String expedientesIds, String descargarDeudas, List<ReciboPagoDescargo> recibos ,int modulo) throws SisatException;
	
	public abstract void rectificarDeuda(CdDescargo cdDescargo, DeudaDTO deudaARectificar) throws SisatException;
	
	public abstract void modificarDeudas(int deudaId,BigDecimal insolutoCancelado, BigDecimal derechoEmisionCancelado, BigDecimal totalDeudaCancelada,  int usuarioId) throws SisatException;
	
	public abstract List<GnTipoDocumento> obtenerTipoDocumentos() throws SisatException;
	public abstract List<GnTipoDocumento> obtenerTipoDocumentosDescargo(int usuarioID) throws SisatException;
	
	public abstract List<DeudaDTO> obtenerTodaDeudaConFiltro(Integer personaId, Date fechaCompensacion,
			Integer anioDeuda, Integer subConceptoId, Integer nroCuota) throws SisatException;
	
	// -=CRAMIREZ=-
		public List<ItemBandejaEntradaDTO> obtenerExpedientesContibuyente(int persona_id, String tipos_tramite)throws SisatException;
		public abstract ArrayList<ReciboPagoDescargo> obtenerRecibosPagoDescargo(int personaId)throws Exception;
		public abstract ArrayList<ReciboPagoDetalleDTO> obtenerDeudasRecibo(int reciboId)throws Exception;
		public abstract int validaNumeroDocumento(String nroDocumento)throws Exception;
	// -=CRAMIREZ=-
	
}

