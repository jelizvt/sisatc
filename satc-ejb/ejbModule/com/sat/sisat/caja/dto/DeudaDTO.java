package com.sat.sisat.caja.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.sat.sisat.common.dto.BaseDTO;

public class DeudaDTO extends BaseDTO implements Serializable{

	private static final long serialVersionUID = 4946032651139353379L;

	private int deudaId;
	private int anioDeuda;
	private int numCuota;
	private BigDecimal insoluto;
	private BigDecimal reajuste;
	private BigDecimal derechoEmi;
	private BigDecimal interesCapi;
	private BigDecimal interesSimp;
	private BigDecimal interes;
	private BigDecimal subtotal;
	private BigDecimal tasaDcto;
	private BigDecimal descuento;
	private BigDecimal totalDeuda;
	private int determinacionId;
	private Date fechaInteres;
	private Date fechaVencim;
	private int conceptoId;
	private String concepto;
	private int subconceptoId;
	private String subconcepto;
	private String placaVe;
	private String placaPa;
	private String numPapeleta;
	private String direccion;
	private String uso;
	private int personaId;
	private int cajeroId;
	private BigDecimal descuentoPosible;
	private BigDecimal totalPosible;
	private String estado;
	private int usuarioId;
	private Date fechaEmision;
	private int predioId;
	private int vehiculoId;
	private String descripcion;
	private Date fechaRegistro;
	private String terminal;
	private int nroCuotas;
	private String placaPar;//para mostrar la placa en ObtenerDeudaTotalPapeletas
	private String infractorPar;//para mostrar infractor en ObtenerDeudaTotalPapeletas
	
	public DeudaDTO(){
	}

	public int getDeudaId() {
		return deudaId;
	}

	public void setDeudaId(int deudaId) {
		this.deudaId = deudaId;
	}

	public int getAnioDeuda() {
		return anioDeuda;
	}

	public void setAnioDeuda(int anioDeuda) {
		this.anioDeuda = anioDeuda;
	}

	public int getNumCuota() {
		return numCuota;
	}

	public void setNumCuota(int numCuota) {
		this.numCuota = numCuota;
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

	public BigDecimal getDerechoEmi() {
		return derechoEmi;
	}

	public void setDerechoEmi(BigDecimal derechoEmi) {
		this.derechoEmi = derechoEmi;
	}

	public BigDecimal getInteresCapi() {
		return interesCapi;
	}

	public void setInteresCapi(BigDecimal interesCapi) {
		this.interesCapi = interesCapi;
	}

	public BigDecimal getInteresSimp() {
		return interesSimp;
	}

	public void setInteresSimp(BigDecimal interesSimp) {
		this.interesSimp = interesSimp;
	}

	public BigDecimal getInteres() {
		return interes;
	}

	public void setInteres(BigDecimal interes) {
		this.interes = interes;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public BigDecimal getTasaDcto() {
		return tasaDcto;
	}

	public void setTasaDcto(BigDecimal tasaDcto) {
		this.tasaDcto = tasaDcto;
	}

	public BigDecimal getDescuento() {
		return descuento;
	}

	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}

	public BigDecimal getTotalDeuda() {
		return totalDeuda;
	}

	public void setTotalDeuda(BigDecimal totalDeuda) {
		this.totalDeuda = totalDeuda;
	}

	public int getDeterminacionId() {
		return determinacionId;
	}

	public void setDeterminacionId(int determinacionId) {
		this.determinacionId = determinacionId;
	}

	public Date getFechaInteres() {
		return fechaInteres;
	}

	public void setFechaInteres(Date fechaInteres) {
		this.fechaInteres = fechaInteres;
	}

	public Date getFechaVencim() {
		return fechaVencim;
	}

	public void setFechaVencim(Date fechaVencim) {
		this.fechaVencim = fechaVencim;
	}

	public int getConceptoId() {
		return conceptoId;
	}

	public void setConceptoId(int conceptoId) {
		this.conceptoId = conceptoId;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public int getSubconceptoId() {
		return subconceptoId;
	}

	public void setSubconceptoId(int subconceptoId) {
		this.subconceptoId = subconceptoId;
	}

	public String getSubconcepto() {
		return subconcepto;
	}

	public void setSubconcepto(String subconcepto) {
		this.subconcepto = subconcepto;
	}

	public String getPlacaVe() {
		return placaVe;
	}

	public void setPlacaVe(String placaVe) {
		this.placaVe = placaVe;
	}

	public String getPlacaPa() {
		return placaPa;
	}

	public void setPlacaPa(String placaPa) {
		this.placaPa = placaPa;
	}

	public String getNumPapeleta() {
		return numPapeleta;
	}

	public void setNumPapeleta(String numPapeleta) {
		this.numPapeleta = numPapeleta;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getUso() {
		return uso;
	}

	public void setUso(String uso) {
		this.uso = uso;
	}

	public int getPersonaId() {
		return personaId;
	}

	public void setPersonaId(int personaId) {
		this.personaId = personaId;
	}

	public int getCajeroId() {
		return cajeroId;
	}

	public void setCajeroId(int cajeroId) {
		this.cajeroId = cajeroId;
	}

	public BigDecimal getDescuentoPosible() {
		return descuentoPosible;
	}

	public void setDescuentoPosible(BigDecimal descuentoPosible) {
		this.descuentoPosible = descuentoPosible;
	}

	public BigDecimal getTotalPosible() {
		return totalPosible;
	}

	public void setTotalPosible(BigDecimal totalPosible) {
		this.totalPosible = totalPosible;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + deudaId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DeudaDTO other = (DeudaDTO) obj;
		if (deudaId != other.deudaId)
			return false;
		return true;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Date getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public int getPredioId() {
		return predioId;
	}

	public void setPredioId(int predioId) {
		this.predioId = predioId;
	}

	public int getVehiculoId() {
		return vehiculoId;
	}

	public void setVehiculoId(int vehiculoId) {
		this.vehiculoId = vehiculoId;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public int getNroCuotas() {
		return nroCuotas;
	}

	public void setNroCuotas(int nroCuotas) {
		this.nroCuotas = nroCuotas;
	}
	//para mostrar placa e infractor
	public String getPlacaPar(){ 
		return placaPar;
	}

	public void setPlacaPar(String placaPar) {
		this.placaPar = placaPar;
	}
	
	public String getInfractorPar(){ 
		return infractorPar;
	}

	public void setInfractorPar(String infractorPar) {
		this.infractorPar = infractorPar;
	}
}
