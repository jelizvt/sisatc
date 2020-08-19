package com.sat.sisat.cobranzacoactiva.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Transient;

import com.sat.sisat.common.dto.BaseDTO;

public class FindCcLoteDetalleActoExp extends BaseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer deudaExigibleId;
	private Integer loteId;
	private Integer tipoActoId;
	private Integer personaId;
	private Integer actoId;
	private String tipoActo;
	private String apellidosNombres;
	private String tipoActoDescripcionCorta;
	private String nroActo;
	private Integer annoLote;
	private BigDecimal montoDeuda;
	private Integer annoDeuda;
	private BigDecimal deuda;
	private BigDecimal pago;
	private Integer conceptoId;
	private String concepto;
	private String nroDocumento;
	private String direccion;
	private String sector;
	private Timestamp fechaNotificacion;
	private Integer loteIdAnterior;
	private Integer tipoActoIdAnterior;
	private Integer flagUbicables;
	private String ubicables;
	private String nroExpediente;
	private Integer loteRecId;
	private Integer tipoDocumentoId;
	private Timestamp fechaPago;
	private String ultimaRecEmitida;
	private Integer ultimaTipoRecId;
	private Integer papeletaId;
	private String criterioBusqueda;

	// corregido
	// private String sector;
	private String lugar;
	private String nroDireccion;

	private BigDecimal insoluto;
	private BigDecimal reajuste;
	private BigDecimal derechoEmision;
	private BigDecimal interesCapitalizado;
	private BigDecimal interesSimple;

	// solo para las RDs
	private BigDecimal baseImponibleFisca;
	private BigDecimal baseAfectaFisca;
	private BigDecimal impuestoFisca;
	private BigDecimal baseImponible;
	private BigDecimal baseAfecta;
	private BigDecimal impuesto;

	// solo para RS
	private String nroPapeleta;
	private String placa;
	private BigDecimal montoMulta;
	// solo para OP
	private String descripcionConcepto;
	private Integer nroCuota;
	private Integer anioCuota;

	private Integer ubicacionId;

	// Solo para las esquelas
	private BigDecimal totalDeudaPredial;
	private BigDecimal totalDeudaVehicular;
	private BigDecimal totalDeudaArbitrios;
	private BigDecimal totalDeudaPredialDcto;
	private BigDecimal totalDeudaVehicularDcto;
	private BigDecimal totalDeudaArbitriosDcto;
	private BigDecimal deudaTotalDcto;

	// estado de pago
	private String estado_deuda;
	// estado de las deudas exigible
	private String estadoDeudaExigible;
	/** Estado de Expedientes de REC */
	private String estadoExpediente;
	private String motivoCancelacion;
	private String resolucionCancelacion;
	private Timestamp fechaCancelacion;
	private String usuarioCancelacion;
	private String observacion;
	private Timestamp fechaEmision;

	/**Estado de registro*/
	private Integer estado;//1:vigente 9:retirado
	private String motivoRetiro;
	private String resenaMotivoRetiro;
	
	@Transient
	private Boolean cusEditFlag;

	private Long contenId;

	public Boolean getCusEditFlag() {
		return cusEditFlag;
	}

	public Boolean esPendiente() {
		return estado_deuda.equals("PENDIENTE") ? Boolean.TRUE : Boolean.FALSE;
	}

	public Boolean esCancelado() {
		return estado_deuda.equals("CANCELADA") ? Boolean.TRUE : Boolean.FALSE;
	}

	public String getStyleEstado() {
		if(getEstado()!=null&&getEstado()==9){
			return "#FF8F8F";
		}
		return "#white";
	}
	public String getStyleSelected() {

		if (estado_deuda == null || estado_deuda.equals("")) {
			return "#FFE7F3";
		} else if (esPendiente()) {
			return "#white";
		} else if (esCancelado()) {
			return "#B4E779";
		}// "#D8D8D8";}
		else if (estado_deuda.equals("PRESCRITA")) {
			return "#EBC5FF";
		}else if (estado_deuda.equals("DESCARGADA")) {
			return "#E6E6E6"; 
		}else if (estado_deuda.equals("REDETERMINADA")) {
			return "#FFE7F3"; 
		}else if (nroExpediente.equals("null")) {
			return "#6CC56C";
		} else {
			return "#blue";
		}

	}

	public String getStyleSelected2() {
		if (estadoExpediente.equals("SUSP. DEFINITIVAMENTE")) {
			return "#FF8F8F";
			/** Suspendido Definitivamente */
		} else if (estadoExpediente.equals("SUSP. TEMPORALMENTE")) {
			return "#FDCFA0";
			/** Suspendido Temporalmente */
		} else if (estado_deuda == null || estado_deuda.equals("")) {
			return "#FFE7F3";
		} else if (estado_deuda.equals("PENDIENTE")
				&& !estadoExpediente.equals("SUSP. TEMPORALMENTE")) {
			return "#FFFFFF";
		} else if (esCancelado()) {
			return "#FF8F8F";
		} else if (estado_deuda.equals("PRESCRITA")) {
			return "#EBC5FF";
		} else if (estado_deuda.equals("REDETERMINADA")) {
			return "#FFE7F3";
		} else if (nroExpediente.equals("null")) {
			return "#6CC56C";
		} else {
			return "#blue";
		}
	}

	public void setCusEditFlag(Boolean cusEditFlag) {
		this.cusEditFlag = cusEditFlag;
	}

	public BigDecimal getDeudaTotalDcto() {
		return deudaTotalDcto;
	}

	public void setDeudaTotalDcto(BigDecimal deudaTotalDcto) {
		this.deudaTotalDcto = deudaTotalDcto;
	}

	public BigDecimal getTotalDeudaPredial() {
		return totalDeudaPredial;
	}

	public void setTotalDeudaPredial(BigDecimal totalDeudaPredial) {
		this.totalDeudaPredial = totalDeudaPredial;
	}

	public BigDecimal getTotalDeudaVehicular() {
		return totalDeudaVehicular;
	}

	public void setTotalDeudaVehicular(BigDecimal totalDeudaVehicular) {
		this.totalDeudaVehicular = totalDeudaVehicular;
	}

	public BigDecimal getTotalDeudaArbitrios() {
		return totalDeudaArbitrios;
	}

	public void setTotalDeudaArbitrios(BigDecimal totalDeudaArbitrios) {
		this.totalDeudaArbitrios = totalDeudaArbitrios;
	}

	public BigDecimal getTotalDeudaPredialDcto() {
		return totalDeudaPredialDcto;
	}

	public void setTotalDeudaPredialDcto(BigDecimal totalDeudaPredialDcto) {
		this.totalDeudaPredialDcto = totalDeudaPredialDcto;
	}

	public BigDecimal getTotalDeudaVehicularDcto() {
		return totalDeudaVehicularDcto;
	}

	public void setTotalDeudaVehicularDcto(BigDecimal totalDeudaVehicularDcto) {
		this.totalDeudaVehicularDcto = totalDeudaVehicularDcto;
	}

	public BigDecimal getTotalDeudaArbitriosDcto() {
		return totalDeudaArbitriosDcto;
	}

	public void setTotalDeudaArbitriosDcto(BigDecimal totalDeudaArbitriosDcto) {
		this.totalDeudaArbitriosDcto = totalDeudaArbitriosDcto;
	}

	public Integer getUbicacionId() {
		return ubicacionId;
	}

	public void setUbicacionId(Integer ubicacionId) {
		this.ubicacionId = ubicacionId;
	}

	public Integer getNroCuota() {
		return nroCuota;
	}

	public void setNroCuota(Integer nroCuota) {
		this.nroCuota = nroCuota;
	}

	public BigDecimal getBaseImponibleFisca() {
		return baseImponibleFisca;
	}

	public void setBaseImponibleFisca(BigDecimal baseImponibleFisca) {
		this.baseImponibleFisca = baseImponibleFisca;
	}

	public BigDecimal getBaseAfectaFisca() {
		return baseAfectaFisca;
	}

	public void setBaseAfectaFisca(BigDecimal baseAfectaFisca) {
		this.baseAfectaFisca = baseAfectaFisca;
	}

	public BigDecimal getImpuestoFisca() {
		return impuestoFisca;
	}

	public void setImpuestoFisca(BigDecimal impuestoFisca) {
		this.impuestoFisca = impuestoFisca;
	}

	public BigDecimal getBaseImponible() {
		return baseImponible;
	}

	public void setBaseImponible(BigDecimal baseImponible) {
		this.baseImponible = baseImponible;
	}

	public BigDecimal getBaseAfecta() {
		return baseAfecta;
	}

	public void setBaseAfecta(BigDecimal baseAfecta) {
		this.baseAfecta = baseAfecta;
	}

	public BigDecimal getImpuesto() {
		return impuesto;
	}

	public void setImpuesto(BigDecimal impuesto) {
		this.impuesto = impuesto;
	}

	public BigDecimal getInsoluto() {
		return insoluto;
	}

	public void setInsoluto(BigDecimal insoluto) {
		this.insoluto = insoluto;
	}

	public BigDecimal getReajuste() {
		return reajuste;
	}

	public void setReajuste(BigDecimal reajuste) {
		this.reajuste = reajuste;
	}

	public BigDecimal getDerechoEmision() {
		return derechoEmision;
	}

	public void setDerechoEmision(BigDecimal derechoEmision) {
		this.derechoEmision = derechoEmision;
	}

	public BigDecimal getInteresCapitalizado() {
		return interesCapitalizado;
	}

	public void setInteresCapitalizado(BigDecimal interesCapitalizado) {
		this.interesCapitalizado = interesCapitalizado;
	}

	public BigDecimal getInteresSimple() {
		return interesSimple;
	}

	public void setInteresSimple(BigDecimal interesSimple) {
		this.interesSimple = interesSimple;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public FindCcLoteDetalleActoExp() {

	}

	public String getNroDocumento() {
		return nroDocumento;
	}

	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getNroPapeleta() {
		return nroPapeleta;
	}

	public void setNroPapeleta(String nroPapeleta) {
		this.nroPapeleta = nroPapeleta;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public BigDecimal getMontoMulta() {
		return montoMulta;
	}

	public void setMontoMulta(BigDecimal montoMulta) {
		this.montoMulta = montoMulta;
	}

	public String getDescripcionConcepto() {
		return descripcionConcepto;
	}

	public void setDescripcionConcepto(String descripcionConcepto) {
		this.descripcionConcepto = descripcionConcepto;
	}

	public Integer getAnioCuota() {
		return anioCuota;
	}

	public void setAnioCuota(Integer anioCuota) {
		this.anioCuota = anioCuota;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public String getNroDireccion() {
		return nroDireccion;
	}

	public void setNroDireccion(String nroDireccion) {
		this.nroDireccion = nroDireccion;
	}

	public String getEstado_deuda() {
		return estado_deuda;
	}

	public void setEstado_deuda(String estado_deuda) {
		this.estado_deuda = estado_deuda;
	}

	public BigDecimal getDeuda() {
		return deuda;
	}

	public BigDecimal getPago() {
		return pago;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setDeuda(BigDecimal deuda) {
		this.deuda = deuda;
	}

	public void setPago(BigDecimal pago) {
		this.pago = pago;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public Timestamp getFechaNotificacion() {
		return fechaNotificacion;
	}

	public void setFechaNotificacion(Timestamp fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}

	public Integer getTipoActoIdAnterior() {
		return tipoActoIdAnterior;
	}

	public void setTipoActoIdAnterior(Integer tipoActoIdAnterior) {
		this.tipoActoIdAnterior = tipoActoIdAnterior;
	}

	public Integer getLoteIdAnterior() {
		return loteIdAnterior;
	}

	public void setLoteIdAnterior(Integer loteIdAnterior) {
		this.loteIdAnterior = loteIdAnterior;
	}

	public Integer getDeudaExigibleId() {
		return deudaExigibleId;
	}

	public void setDeudaExigibleId(Integer deudaExigibleId) {
		this.deudaExigibleId = deudaExigibleId;
	}

	public Integer getLoteId() {
		return loteId;
	}

	public void setLoteId(Integer loteId) {
		this.loteId = loteId;
	}

	public Integer getTipoActoId() {
		return tipoActoId;
	}

	public void setTipoActoId(Integer tipoActoId) {
		this.tipoActoId = tipoActoId;
	}

	public Integer getPersonaId() {
		return personaId;
	}

	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}

	public Integer getActoId() {
		return actoId;
	}

	public void setActoId(Integer actoId) {
		this.actoId = actoId;
	}

	public String getApellidosNombres() {
		return apellidosNombres;
	}

	public void setApellidosNombres(String apellidosNombres) {
		this.apellidosNombres = apellidosNombres;
	}

	public String getNroActo() {
		return nroActo;
	}

	public void setNroActo(String nroActo) {
		this.nroActo = nroActo;
	}

	public Integer getAnnoLote() {
		return annoLote;
	}

	public void setAnnoLote(Integer annoLote) {
		this.annoLote = annoLote;
	}

	public BigDecimal getMontoDeuda() {
		return montoDeuda;
	}

	public void setMontoDeuda(BigDecimal montoDeuda) {
		this.montoDeuda = montoDeuda;
	}

	public Integer getAnnoDeuda() {
		return annoDeuda;
	}

	public void setAnnoDeuda(Integer annoDeuda) {
		this.annoDeuda = annoDeuda;
	}

	public String getTipoActo() {
		return tipoActo;
	}

	public void setTipoActo(String tipoActo) {
		this.tipoActo = tipoActo;
	}

	public String getTipoActoDescripcionCorta() {
		return tipoActoDescripcionCorta;
	}

	public void setTipoActoDescripcionCorta(String tipoActoDescripcionCorta) {
		this.tipoActoDescripcionCorta = tipoActoDescripcionCorta;
	}

	public Integer getFlagUbicables() {
		return flagUbicables;
	}

	public void setFlagUbicables(Integer flagUbicables) {
		this.flagUbicables = flagUbicables;
	}

	public String getUbicables() {
		return ubicables;
	}

	public void setUbicables(String ubicables) {
		this.ubicables = ubicables;
	}

	public Long getContenId() {
		return contenId;
	}

	public void setContenId(Long contenId) {
		this.contenId = contenId;
	}

	public String getNroExpediente() {
		return nroExpediente;
	}

	public void setNroExpediente(String nroExpediente) {
		this.nroExpediente = nroExpediente;
	}

	public Integer getLoteRecId() {
		return loteRecId;
	}

	public void setLoteRecId(Integer loteRecId) {
		this.loteRecId = loteRecId;
	}

	public Integer getTipoDocumentoId() {
		return tipoDocumentoId;
	}

	public void setTipoDocumentoId(Integer tipoDocumentoId) {
		this.tipoDocumentoId = tipoDocumentoId;
	}

	public String getEstadoDeudaExigible() {
		return estadoDeudaExigible;
	}

	public void setEstadoDeudaExigible(String estadoDeudaExigible) {
		this.estadoDeudaExigible = estadoDeudaExigible;
	}

	public Integer getConceptoId() {
		return conceptoId;
	}

	public void setConceptoId(Integer conceptoId) {
		this.conceptoId = conceptoId;
	}

	public Timestamp getFechaPago() {

		return fechaPago;
	}

	public void setFechaPago(Timestamp fechaPago) {
		this.fechaPago = fechaPago;
	}

	public String getUltimaRecEmitida() {
		return ultimaRecEmitida;
	}

	public void setUltimaRecEmitida(String ultimaRecEmitida) {
		this.ultimaRecEmitida = ultimaRecEmitida;
	}

	public Integer getPapeletaId() {
		return papeletaId;
	}

	public void setPapeletaId(Integer papeletaId) {
		this.papeletaId = papeletaId;
	}

	public String getCriterioBusqueda() {
		return criterioBusqueda;
	}

	public void setCriterioBusqueda(String criterioBusqueda) {
		this.criterioBusqueda = criterioBusqueda;
	}

	public Integer getUltimaTipoRecId() {
		return ultimaTipoRecId;
	}

	public void setUltimaTipoRecId(Integer ultimaTipoRecId) {
		this.ultimaTipoRecId = ultimaTipoRecId;
	}

	public String getEstadoExpediente() {
		return estadoExpediente;
	}

	public void setEstadoExpediente(String estadoExpediente) {
		this.estadoExpediente = estadoExpediente;
	}

	public String getMotivoCancelacion() {
		return motivoCancelacion;
	}

	public void setMotivoCancelacion(String motivoCancelacion) {
		this.motivoCancelacion = motivoCancelacion;
	}

	public String getResolucionCancelacion() {
		return resolucionCancelacion;
	}

	public void setResolucionCancelacion(String resolucionCancelacion) {
		this.resolucionCancelacion = resolucionCancelacion;
	}

	public Timestamp getFechaCancelacion() {
		return fechaCancelacion;
	}

	public void setFechaCancelacion(Timestamp fechaCancelacion) {
		this.fechaCancelacion = fechaCancelacion;
	}

	public String getUsuarioCancelacion() {
		return usuarioCancelacion;
	}

	public void setUsuarioCancelacion(String usuarioCancelacion) {
		this.usuarioCancelacion = usuarioCancelacion;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Timestamp getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(Timestamp fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	
	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	
	public String getMotivoRetiro() {
		return motivoRetiro;
	}

	public void setMotivoRetiro(String motivoRetiro) {
		this.motivoRetiro = motivoRetiro;
	}

	public String getResenaMotivoRetiro() {
		return resenaMotivoRetiro;
	}

	public void setResenaMotivoRetiro(String resenaMotivoRetiro) {
		this.resenaMotivoRetiro = resenaMotivoRetiro;
	}
}
