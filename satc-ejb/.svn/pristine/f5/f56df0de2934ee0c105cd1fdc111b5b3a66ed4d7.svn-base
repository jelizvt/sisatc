package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import java.sql.Timestamp;

/**
 * The persistent class for the rp_tipo_obra database table.
 * 
 */
@Entity
@Table(name = "rp_tipo_obra")
@NamedQueries({
		@NamedQuery(name = "getAllTipoObra", query = "SELECT a FROM RpTipoObra a WHERE a.categoriaObraId=:p_categoriaObraId AND a.estado='1' and a.tipoObraId not in(341,342)"),
		@NamedQuery(name = "getAllTipoObraIdMinVivienda", query = "SELECT a FROM RpTipoObra a WHERE a.categoriaObraId=:p_categoriaObraId AND a.estado='1' and a.idMinVivienda=:p_idMinVivienda and a.tipoObraId not in(341,342)") })
public class RpTipoObra implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "tipo_obra_id")
	private Integer tipoObraId;

	private String descripcion;

	@Column(name = "usuario_id")
	private Integer usuarioId;

	@Column(name = "fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name = "estado")
	private String estado;

	@Column(name = "terminal")
	private String terminal;

	@Column(name = "categoria_obra_id")
	private int categoriaObraId;

	@Column(name = "valor_unitario")
	private BigDecimal valorUnitario;

	@Column(name = "id_min_vivienda")
	private Integer idMinVivienda;

	public RpTipoObra() {

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

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public Integer getIdMinVivienda() {
		return idMinVivienda;
	}

	public void setIdMinVivienda(Integer idMinVivienda) {
		this.idMinVivienda = idMinVivienda;
	}

}