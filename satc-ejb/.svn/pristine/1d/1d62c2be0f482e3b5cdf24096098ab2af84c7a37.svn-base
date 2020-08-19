package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the rp_tipo_uso database table.
 * 
 */
@Entity
@Table(name="rp_tipo_uso")
@NamedQuery(name="getAllRpTipoUsoByPeriodo", query="SELECT m FROM RpTipoUso m WHERE m.estado='1' ")
public class RpTipoUso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="tipo_uso_id")
	private Integer tipoUsoId;

	private String descripcion;

	@Column(name="descripcion_corta")
	private String descripcionCorta;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="tabla_depre_id")
	private String tablaDepreId;

	@Column(name="tabla_tipo_uso_id")
	private String tablaTipoUsoId;

	private String terminal;

	@Column(name="usuario_id")
	private Integer usuarioId;

	@Column(name="categoria_uso_id")
	private Integer categoriaUsoId;
	
    public Integer getTipoUsoId() {
		return tipoUsoId;
	}

	public void setTipoUsoId(Integer tipoUsoId) {
		this.tipoUsoId = tipoUsoId;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcionCorta() {
		return descripcionCorta;
	}

	public void setDescripcionCorta(String descripcionCorta) {
		this.descripcionCorta = descripcionCorta;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Timestamp getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getTablaDepreId() {
		return tablaDepreId;
	}

	public void setTablaDepreId(String tablaDepreId) {
		this.tablaDepreId = tablaDepreId;
	}

	public String getTablaTipoUsoId() {
		return tablaTipoUsoId;
	}

	public void setTablaTipoUsoId(String tablaTipoUsoId) {
		this.tablaTipoUsoId = tablaTipoUsoId;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Integer getCategoriaUsoId() {
		return categoriaUsoId;
	}

	public void setCategoriaUsoId(Integer categoriaUsoId) {
		this.categoriaUsoId = categoriaUsoId;
	}

	public RpTipoUso() {
    }

	

}