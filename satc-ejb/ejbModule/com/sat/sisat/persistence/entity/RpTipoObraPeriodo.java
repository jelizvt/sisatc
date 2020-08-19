package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the rp_tipo_obra_periodo database table.
 * 
 */
@Entity
@Table(name="rp_tipo_obra_periodo")
public class RpTipoObraPeriodo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="tipo_obra_periodo_id")
	private Integer tipoObraPeriodoId;
	
	@Column(name="categoria_obra_id")
	private int categoriaObraId;
	
	@Column(name="tipo_obra_id")
	private Integer tipoObraId;
	
	@Column(name="periodo")
	private Integer periodo;
	
	@Column(name="valor_unitario")
	private BigDecimal valorUnitario;
	
	@Column(name="usuario_id")
	private Integer usuarioId;
	
	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;
	
	@Column(name="estado")
	private String estado;
	
	@Column(name="terminal")
	private String terminal;
	

	public RpTipoObraPeriodo() {
		
    }
	
    public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public int getCategoriaObraId() {
		return categoriaObraId;
	}

	public void setCategoriaObraId(int categoriaObraId) {
		this.categoriaObraId = categoriaObraId;
	}

	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public void setTipoObraId(Integer tipoObraId) {
		this.tipoObraId = tipoObraId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public int getTipoObraId() {
		return this.tipoObraId;
	}

	public void setTipoObraId(int tipoObraId) {
		this.tipoObraId = tipoObraId;
	}

	public Timestamp getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public int getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Integer getTipoObraPeriodoId() {
		return tipoObraPeriodoId;
	}

	public void setTipoObraPeriodoId(Integer tipoObraPeriodoId) {
		this.tipoObraPeriodoId = tipoObraPeriodoId;
	}

	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}

}