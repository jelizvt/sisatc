package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the no_detalle_masiva_digi_notif database table.
 * 
 */
@Entity
@Table(name="no_detalle_masiva_digi_notif")
public class NoDetalleMasivaDigiNotif implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="detalle_masiva_digi_notif_id")
	private int detalleMasivaDigiNotifId;


	@Column(name="error_message")
	private String errorMessage;

	private String estado;

	@Column(name="fecha_notificacion")
	private String fechaNotificacion;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="masiva_digi_notif_id")
	private int masivaDigiNotifId;

	@Column(name="notificacion_id")
	private int notificacionId;

	@Column(name="nro_documento")
	private String nroDocumento;

	private String terminal;

	@Column(name="usuario_id")
	private int usuarioId;
	
	@Column(name="conten_id")
	private Long contenId;
	
	@Transient
	private Boolean cargarImagen;
	
    public NoDetalleMasivaDigiNotif() {
    }

	public int getDetalleMasivaDigiNotifId() {
		return this.detalleMasivaDigiNotifId;
	}

	public void setDetalleMasivaDigiNotifId(int detalleMasivaDigiNotifId) {
		this.detalleMasivaDigiNotifId = detalleMasivaDigiNotifId;
	}

	public String getErrorMessage() {
		return this.errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getFechaNotificacion() {
		return this.fechaNotificacion;
	}

	public void setFechaNotificacion(String fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}

	public Timestamp getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public int getMasivaDigiNotifId() {
		return this.masivaDigiNotifId;
	}

	public void setMasivaDigiNotifId(int masivaDigiNotifId) {
		this.masivaDigiNotifId = masivaDigiNotifId;
	}

	public int getNotificacionId() {
		return this.notificacionId;
	}

	public void setNotificacionId(int notificacionId) {
		this.notificacionId = notificacionId;
	}

	public String getNroDocumento() {
		return this.nroDocumento;
	}

	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
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

	public Long getContenId() {
		return contenId;
	}

	public void setContenId(Long contenId) {
		this.contenId = contenId;
	}

	public Boolean getCargarImagen() {
		return cargarImagen;
	}

	public void setCargarImagen(Boolean cargarImagen) {
		this.cargarImagen = cargarImagen;
	}

}