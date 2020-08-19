package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The persistent class for the cj_agencia_operacion database table.
 * 
 */
@Entity
@Table(name="cj_agencia_operacion")
public class CjAgenciaOperacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CjAgenciaOperacionPK id;

	private String estado;

	@Column(name="fecha_apertura")
	private Timestamp fechaApertura;

	@Column(name="fecha_cierre")
	private Timestamp fechaCierre;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	private String terminal;

	//bi-directional many-to-one association to SgUsuario
    @ManyToOne
	@JoinColumn(name="usuario_id")
	private SgUsuario sgUsuario;

    public CjAgenciaOperacion() {
    }

	public CjAgenciaOperacionPK getId() {
		return this.id;
	}

	public void setId(CjAgenciaOperacionPK id) {
		this.id = id;
	}
	
	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Timestamp getFechaApertura() {
		return this.fechaApertura;
	}

	public void setFechaApertura(Timestamp fechaApertura) {
		this.fechaApertura = fechaApertura;
	}

	public Timestamp getFechaCierre() {
		return this.fechaCierre;
	}

	public void setFechaCierre(Timestamp fechaCierre) {
		this.fechaCierre = fechaCierre;
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

	public SgUsuario getSgUsuario() {
		return this.sgUsuario;
	}

	public void setSgUsuario(SgUsuario sgUsuario) {
		this.sgUsuario = sgUsuario;
	}
}