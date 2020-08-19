
package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="mp_tipo_documento_condicion_especial")
public class MpTipoDocumentoCondicionEspecial implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="tipo_documento_condicion_especial_id")
	private int tipoDocumentoCondicionEspecialId;

	@Column(name="codigo_documento")
	private String codigoDocumento;

	private String descripcion;

	@Column(name="descripcion_corta")
	private String descripcionCorta;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	private String terminal;

	@Column(name="usuario_id")
	private int usuarioId;

    public MpTipoDocumentoCondicionEspecial() {
    }



	public String getCodigoDocumento() {
		return this.codigoDocumento;
	}

	public void setCodigoDocumento(String codigoDocumento) {
		this.codigoDocumento = codigoDocumento;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcionCorta() {
		return this.descripcionCorta;
	}

	public void setDescripcionCorta(String descripcionCorta) {
		this.descripcionCorta = descripcionCorta;
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

	public int getTipoDocumentoCondicionEspecialId() {
		return tipoDocumentoCondicionEspecialId;
	}

	public void setTipoDocumentoCondicionEspecialId(
			int tipoDocumentoCondicionEspecialId) {
		this.tipoDocumentoCondicionEspecialId = tipoDocumentoCondicionEspecialId;
	}

}