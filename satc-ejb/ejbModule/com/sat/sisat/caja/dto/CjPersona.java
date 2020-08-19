package com.sat.sisat.caja.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class CjPersona implements Serializable {

	private static final long serialVersionUID = 6819557802300424891L;
	
	private int personaId;
	private String nombreCompleto;
	private String apellido_paterno;
	private String apellido_materno;
	private String primer_nombre;
	private String segundo_nombre;
	private String razon_social;
	private String tipoDocuIdenDes;
	private String nroDocuIden;
	private int tipoDocuIdenId;
	private int usuarioId;
	private int cajeroId;
	private BigDecimal montoCobrar;
	private BigDecimal montoDescuento;
	private String NroPapeleta;
	private String placa;
	private String domicilio;
	private String appellidosNombres;
	private Integer deudaId;
	
	private Boolean seleccionado = Boolean.FALSE;
	
    public CjPersona() {
    }

	public int getPersonaId() {
		return personaId;
	}

	public void setPersona_id(int persona_id) {
		this.personaId = persona_id;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}
  
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getTipoDocuIdenDes() {
		return tipoDocuIdenDes;
	}

	public void setTipoDocuIdenDes(String tipoDocuIdenDes) {
		this.tipoDocuIdenDes = tipoDocuIdenDes;
	}

	public String getNroDocuIden() {
		return nroDocuIden;
	}

	public void setNroDocuIden(String nroDocuIden) {
		this.nroDocuIden = nroDocuIden;
	}

	public int getTipoDocuIdenId() {
		return tipoDocuIdenId;
	}

	public void setTipoDocuIdenId(int tipoDocuIdenId) {
		this.tipoDocuIdenId = tipoDocuIdenId;
	}

	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public int getCajeroId() {
		return cajeroId;
	}

	public void setCajeroId(int cajeroId) {
		this.cajeroId = cajeroId;
	}

	public BigDecimal getMontoCobrar() {
		return montoCobrar;
	}

	public void setMontoCobrar(BigDecimal montoCobrar) {
		this.montoCobrar = montoCobrar;
	}

	public String getApellido_paterno() {
		return apellido_paterno;
	}

	public void setApellido_paterno(String apellido_paterno) {
		this.apellido_paterno = apellido_paterno;
	}

	public String getApellido_materno() {
		return apellido_materno;
	}

	public void setApellido_materno(String apellido_materno) {
		this.apellido_materno = apellido_materno;
	}

	public String getPrimer_nombre() {
		return primer_nombre;
	}

	public void setPrimer_nombre(String primer_nombre) {
		this.primer_nombre = primer_nombre;
	}

	public String getSegundo_nombre() {
		return segundo_nombre;
	}

	public void setSegundo_nombre(String segundo_nombre) {
		this.segundo_nombre = segundo_nombre;
	}

	public String getRazon_social() {
		return razon_social;
	}

	public void setRazon_social(String razon_social) {
		this.razon_social = razon_social;
	}

	public String getNroPapeleta() {
		return NroPapeleta;
	}

	public void setNroPapeleta(String nroPapeleta) {
		NroPapeleta = nroPapeleta;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public String getAppellidosNombres() {
		return appellidosNombres;
	}

	public void setAppellidosNombres(String appellidosNombres) {
		this.appellidosNombres = appellidosNombres;
	}

	public BigDecimal getMontoDescuento() {
		return montoDescuento;
	}

	public void setMontoDescuento(BigDecimal montoDescuento) {
		this.montoDescuento = montoDescuento;
	}

	public Integer getDeudaId() {
		return deudaId;
	}

	public void setDeudaId(Integer deudaId) {
		this.deudaId = deudaId;
	}

	public Boolean getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(Boolean seleccionado) {
		this.seleccionado = seleccionado;
	}
}
