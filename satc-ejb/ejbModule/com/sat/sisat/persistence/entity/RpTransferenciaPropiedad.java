package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the rp_transferencia_propiedad database table.
 * 
 */
@Entity
@Table(name="rp_transferencia_propiedad")
@NamedQuery(name="getAllTransferente", query="SELECT a FROM RpTransferenciaPropiedad a WHERE a.djId=:p_dj_id AND a.tipo='T' AND a.estado='1'")
public class RpTransferenciaPropiedad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="transferencia_id")
	private int transferenciaId;

	@Column(name="dj_id")
	private int djId;

	@Column(name="estado")
	private String estado;

	@Column(name="fecha_actualizacion")
	private Timestamp fechaActualizacion;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="persona_id")
	private int personaId;

	@Column(name="terminal")
	private String terminal;

	@Column(name="tipo")
	private String tipo;

	@Column(name="usuario_id")
	private int usuarioId;
	
	@Column(name="porcentaje")
	private BigDecimal porcentaje;
	
	@Column(name="descargo_auto")
	private String descargoAuto;
	
	@Column(name="area")
	private BigDecimal area;
	
	@Column(name="area_matriz")
	private BigDecimal areaMatriz;
	
	@Column(name="area_transferida")
	private BigDecimal areaTransferida;	

	@Column(name="area_restante")
	private BigDecimal areaRestante;
	
	@Column(name="porcentaje_matriz")
	private BigDecimal porcentajeMatriz;
	
	@Column(name="porcentaje_transferido")
	private BigDecimal porcentajeTransferido;
	
	@Column(name="porcentaje_restante")
	private BigDecimal porcentajeRestante;

	@Column(name="forma_adquisicion")
	private String formaAdquisicion;
	
	public BigDecimal getArea() {
		return area;
	}

	public void setArea(BigDecimal area) {
		this.area = area;
	}

	public BigDecimal getAreaMatriz() {
		return areaMatriz;
	}

	public void setAreaMatriz(BigDecimal areaMatriz) {
		this.areaMatriz = areaMatriz;
	}

	public BigDecimal getPorcentajeMatriz() {
		return porcentajeMatriz;
	}

	public void setPorcentajeMatriz(BigDecimal porcentajeMatriz) {
		this.porcentajeMatriz = porcentajeMatriz;
	}

	public RpTransferenciaPropiedad() {
		
    }

	public int getTransferenciaId() {
		return this.transferenciaId;
	}

	public void setTransferenciaId(int transferenciaId) {
		this.transferenciaId = transferenciaId;
	}

	public int getDjId() {
		return this.djId;
	}

	public void setDjId(int djId) {
		this.djId = djId;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Timestamp getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(Timestamp fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Timestamp getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public int getPersonaId() {
		return this.personaId;
	}

	public void setPersonaId(int personaId) {
		this.personaId = personaId;
	}

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getDescargoAuto() {
		return descargoAuto;
	}

	public void setDescargoAuto(String descargoAuto) {
		this.descargoAuto = descargoAuto;
	}

	public BigDecimal getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(BigDecimal porcentaje) {
		this.porcentaje = porcentaje;
	}

	public BigDecimal getAreaTransferida() {
		return areaTransferida;
	}

	public void setAreaTransferida(BigDecimal areaTransferida) {
		this.areaTransferida = areaTransferida;
	}

	public BigDecimal getPorcentajeTransferido() {
		return porcentajeTransferido;
	}

	public void setPorcentajeTransferido(BigDecimal porcentajeTransferido) {
		this.porcentajeTransferido = porcentajeTransferido;
	}

	public BigDecimal getAreaRestante() {
		return areaRestante;
	}

	public void setAreaRestante(BigDecimal areaRestante) {
		this.areaRestante = areaRestante;
	}

	public BigDecimal getPorcentajeRestante() {
		return porcentajeRestante;
	}

	public void setPorcentajeRestante(BigDecimal porcentajeRestante) {
		this.porcentajeRestante = porcentajeRestante;
	}

	public String getFormaAdquisicion() {
		return formaAdquisicion;
	}

	public void setFormaAdquisicion(String formaAdquisicion) {
		this.formaAdquisicion = formaAdquisicion;
	}
}