package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the gn_notaria database table.
 * 
 */
@Entity
@Table(name="gn_notaria")
public class GnNotaria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="notaria_id")
	private Integer notariaId;

	@Column(name="direccion_notaria")
	private String direccionNotaria;

	private String estado;

	@Column(name="nombre_notaria")
	private String nombreNotaria;

	@Column(name="notario_publico")
	private String notarioPublico;

	@Column(name="nro_telefono")
	private String nroTelefono;

    public GnNotaria() {
    }

	public Integer getNotariaId() {
		return this.notariaId;
	}

	public void setNotariaId(Integer notariaId) {
		this.notariaId = notariaId;
	}

	public String getDireccionNotaria() {
		return this.direccionNotaria;
	}

	public void setDireccionNotaria(String direccionNotaria) {
		this.direccionNotaria = direccionNotaria;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNombreNotaria() {
		return this.nombreNotaria;
	}

	public void setNombreNotaria(String nombreNotaria) {
		this.nombreNotaria = nombreNotaria;
	}

	public String getNotarioPublico() {
		return this.notarioPublico;
	}

	public void setNotarioPublico(String notarioPublico) {
		this.notarioPublico = notarioPublico;
	}

	public String getNroTelefono() {
		return this.nroTelefono;
	}

	public void setNroTelefono(String nroTelefono) {
		this.nroTelefono = nroTelefono;
	}

}