package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="rp_categoria_obra")
@NamedQuery(name="getAllRpCategoriaObra", query="SELECT a FROM RpCategoriaObra a WHERE a.periodo=:p_periodo AND a.estado='1'")
//@NamedQuery(name="getAllRpCategoriaObra", query="SELECT a FROM RpCategoriaObra a WHERE a.periodo<=:p_periodo AND a.estado='1'")
public class RpCategoriaObra implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="categoria_obra_id")
	private int categoriaObraId;

	@Column(name="descripcion")
	private String descripcion;

	@Column(name="usuario_id")
	private int usuarioId;
	
	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="estado")
	private String estado;
	
	@Column(name="terminal")
	private String terminal;
	
	@Column(name="unidad_medida")
	private String unidad_medida;

	@Column(name="periodo")
	private Integer periodo;
	
	public Integer getPeriodo() {
		return periodo;
	}
	
	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}

	public int getCategoriaObraId() {
		return categoriaObraId;
	}

	public void setCategoriaObraId(int categoriaObraId) {
		this.categoriaObraId = categoriaObraId;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Timestamp getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
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

	public String getUnidad_medida() {
		return unidad_medida;
	}

	public void setUnidad_medida(String unidad_medida) {
		this.unidad_medida = unidad_medida;
	}

}