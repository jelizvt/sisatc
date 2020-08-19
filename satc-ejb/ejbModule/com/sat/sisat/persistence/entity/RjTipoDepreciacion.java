package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the rj_tipo_depreciacion database table.
 * 
 */
@Entity
@Table(name="rj_tipo_depreciacion")
public class RjTipoDepreciacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="tipo_depreciacion_id")
	private int tipoDepreciacionId;

	@Column(name="desc_corta")
	private String descCorta;

	private String descripcion;

	@Column(name="estado")
	private String estadoId;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	private String terminal;

	@Column(name="usuario_id")
	private int usuarioId;

    public RjTipoDepreciacion() {
    }

	public int getTipoDepreciacionId() {
		return this.tipoDepreciacionId;
	}

	public void setTipoDepreciacionId(int tipoDepreciacionId) {
		this.tipoDepreciacionId = tipoDepreciacionId;
	}

	public String getDescCorta() {
		return this.descCorta;
	}

	public void setDescCorta(String descCorta) {
		this.descCorta = descCorta;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstadoId() {
		return this.estadoId;
	}

	public void setEstadoId(String estadoId) {
		this.estadoId = estadoId;
	}

	public Timestamp getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public int getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

}