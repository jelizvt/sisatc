package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the cc_lote_tipo_orden_impresion database table.
 * 
 */
@Entity
@Table(name="cc_lote_tipo_orden_impresion")
public class CcLoteTipoOrdenImpresion implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CcLoteTipoOrdenImpresionPK id;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	private int orden;

	private String terminal;

	@Column(name="tipo_orden_impresion_id")
	private int tipoOrdenImpresionId;

	@Column(name="usuario_id")
	private int usuarioId;

    public CcLoteTipoOrdenImpresion() {
    }

	public CcLoteTipoOrdenImpresionPK getId() {
		return this.id;
	}

	public void setId(CcLoteTipoOrdenImpresionPK id) {
		this.id = id;
	}
	
	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Timestamp getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public int getOrden() {
		return this.orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public int getTipoOrdenImpresionId() {
		return this.tipoOrdenImpresionId;
	}

	public void setTipoOrdenImpresionId(int tipoOrdenImpresionId) {
		this.tipoOrdenImpresionId = tipoOrdenImpresionId;
	}

	public int getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

}