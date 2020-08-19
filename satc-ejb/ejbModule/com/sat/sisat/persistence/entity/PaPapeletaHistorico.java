package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the pa_papeleta database table.
 * 
 */
@Entity
@Table(name="pa_papeleta_historico")
public class PaPapeletaHistorico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional = false)
	@Column(name="papeleta_historico_id")
	private Integer papeletaHistoricoId;
	
	@Column(name="papeleta_id")
	private Integer papeletaId;
	
	@Column(name="nro_papeleta")
	private String nroPapeleta;
	
	@Column(name="persona_infractor_id")
	private Integer personaInfractorId;
	
	@Column(name="persona_propietario_id")
	private Integer personaPropietarioId;
	
	@Column(name="infraccion_id")
	private Integer infraccionId;
	
	@Column(name="ley_id")
	private Integer leyId;
	
	@Column(name="vehiculo_papeleta_id")
	private Integer vehiculoPapeletaId;
	
	@Column(name="fecha_infraccion")
	private Timestamp fechaInfraccion;
	
	@Column(name="hora_infraccion")
	private String horaInfraccion;
	
	@Column(name="monto_multa")
	private Double montoMulta;
	
	@Column(name="estado")
	private String estado;
	
	@Column(name="usuario_id")
	private Integer usuarioId;
	
	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;
	
	@Column(name="terminal")
	private String terminal;
	
	@Column(name="sin_licencia")
	private String sinLicencia;
	
	@Column(name="num_licencia")
	private String numLicencia;
	
	@Column(name="origen")
	private String origen;
	
	@Column(name="clase_licencia_id")
	private Integer claseLicenciaId;
	
	@Column(name="placa")
	private String placa;
	
	@Column(name="nro_tarjeta_propiedad")
	private String nroTarjetaPropiedad;
	
	@Column(name="glosa")
	private String glosa;
	
	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getNroTarjetaPropiedad() {
		return nroTarjetaPropiedad;
	}

	public void setNroTarjetaPropiedad(String nroTarjetaPropiedad) {
		this.nroTarjetaPropiedad = nroTarjetaPropiedad;
	}

	public Integer getClaseLicenciaId() {
		return claseLicenciaId;
	}

	public void setClaseLicenciaId(Integer claseLicenciaId) {
		this.claseLicenciaId = claseLicenciaId;
	}

	public Integer getVehiculoPapeletaId() {
		return vehiculoPapeletaId;
	}

	public void setVehiculoPapeletaId(Integer vehiculoPapeletaId) {
		this.vehiculoPapeletaId = vehiculoPapeletaId;
	}

	public String getNumLicencia() {
		return numLicencia;
	}

	public void setNumLicencia(String numLicencia) {
		this.numLicencia = numLicencia;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getSinLicencia() {
		return sinLicencia;
	}

	public void setSinLicencia(String sinLicencia) {
		this.sinLicencia = sinLicencia;
	}
	
	public Integer getPapeletaId() {
		return papeletaId;
	}

	public void setPapeletaId(Integer papeletaId) {
		this.papeletaId = papeletaId;
	}

	public String getNroPapeleta() {
		return nroPapeleta;
	}

	public void setNroPapeleta(String nroPapeleta) {
		this.nroPapeleta = nroPapeleta;
	}

	public Integer getPersonaInfractorId() {
		return personaInfractorId;
	}

	public void setPersonaInfractorId(Integer personaInfractorId) {
		this.personaInfractorId = personaInfractorId;
	}

	public Integer getPersonaPropietarioId() {
		return personaPropietarioId;
	}

	public void setPersonaPropietarioId(Integer personaPropietarioId) {
		this.personaPropietarioId = personaPropietarioId;
	}

	public Integer getInfraccionId() {
		return infraccionId;
	}

	public void setInfraccionId(Integer infraccionId) {
		this.infraccionId = infraccionId;
	}

	public Integer getLeyId() {
		return leyId;
	}

	public void setLeyId(Integer leyId) {
		this.leyId = leyId;
	}

	public Timestamp getFechaInfraccion() {
		return fechaInfraccion;
	}

	public void setFechaInfraccion(Timestamp fechaInfraccion) {
		this.fechaInfraccion = fechaInfraccion;
	}

	public Double getMontoMulta() {
		return montoMulta;
	}

	public void setMontoMulta(Double montoMulta) {
		this.montoMulta = montoMulta;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
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
		this.terminal= terminal;
	}

	public String getHoraInfraccion() {
		return horaInfraccion;
	}

	public void setHoraInfraccion(String horaInfraccion) {
		this.horaInfraccion = horaInfraccion;
	}	

	public Integer getPapeletaHistoricoId() {
		return papeletaHistoricoId;
	}

	public void setPapeletaHistoricoId(Integer papeletaHistoricoId) {
		this.papeletaHistoricoId = papeletaHistoricoId;
	}

	public String getGlosa() {
		return glosa;
	}

	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}
}