package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="mp_requisito_condicion_especial")
public class MpCondicionEspecialRequisito implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Id
	@Column(name="condicion_especial_requisito_id")
	private Integer CondEspecialRequisitoId;

	@Column(name="tipo_cond_especial_id")
	private Integer tipoCondEspecId;
	
	@Column(name="requisito_id")
	private Integer requisitoId;
	
	@Column(name="plazo_presentacion")
	private Integer plazoPresentacion;
	
	@Column(name="flag_obligatorio")
	private Integer flag;
	
	@Column(name="usuario_id")
	private Integer usuarioId;
	
	@Column(name="estado")
	private Integer estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="terminal")
	private String terminal;
	
	private String descripcion;
	
	private boolean selected;
	
	private String glosa;
	
	public Integer getCondEspecialRequisitoId() {
		return CondEspecialRequisitoId;
	}

	public void setCondEspecialRequisitoId(Integer condEspecialRequesitoId) {
		CondEspecialRequisitoId = condEspecialRequesitoId;
	}

	public Integer getTipoCondEspecId() {
		return tipoCondEspecId;
	}

	public void setTipoCondEspecId(Integer tipoCondEspecId) {
		this.tipoCondEspecId = tipoCondEspecId;
	}

	public Integer getRequisitoId() {
		return requisitoId;
	}

	public void setRequisitoId(Integer requisitoId) {
		this.requisitoId = requisitoId;
	}

	public Integer getPlazoPresentacion() {
		return plazoPresentacion;
	}

	public void setPlazoPresentacion(Integer plazoPresentacion) {
		this.plazoPresentacion = plazoPresentacion;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Timestamp getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getGlosa() {
		return glosa;
	}

	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}

}
