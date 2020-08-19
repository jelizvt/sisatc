package com.sat.sisat.estadocuenta.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="cr_constancia")
public class CrConstanciaNoAdeudo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="constancia_id")
	private int constanciaId;
	
	@Column(name="nro_constancia")
	private String numero;
	
	@Column(name="persona_id")
	private Integer personaId;
	
	@Column(name="nro_docu_identidad")
	private String docidentidad;
	
	@Column(name="anno")
	private String anio;
	
	@Column(name="concepto_id")
	private Integer  conceptoId;
	
	@Column(name="subconcepto_id")
	private String  subconceptoId;
	
	@Column(name="referencia")
	private String   referencia;
	
	@Column(name="recibo_id")
	private Integer  reciboId;
	
	@Column(name="tipo_constancia")
	private int   tipoConstancia;
	
	@Column(name="cuotas")
	private String   cuotas;
	
	@Column(name="usuario_id")
	private Integer usuarioId;
	
	@Column(name="estado")
	private String estado;
	
	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;
	
	@Column(name="terminal")
	private String terminal;
	
	  public CrConstanciaNoAdeudo() {
	    }

	public int getConstanciaId() {
		return constanciaId;
	}

	public void setConstanciaId(int constanciaId) {
		this.constanciaId = constanciaId;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Integer getPersonaId() {
		return personaId;
	}

	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}

	public String getDocidentidad() {
		return docidentidad;
	}

	public void setDocidentidad(String docidentidad) {
		this.docidentidad = docidentidad;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public Integer getConceptoId() {
		return conceptoId;
	}

	public void setConceptoId(Integer conceptoId) {
		this.conceptoId = conceptoId;
	}

	public String getSubconceptoId() {
		return subconceptoId;
	}

	public void setSubconceptoId(String subconceptoId) {
		this.subconceptoId = subconceptoId;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public Integer getReciboId() {
		return reciboId;
	}

	public void setReciboId(Integer reciboId) {
		this.reciboId = reciboId;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
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

	public int getTipoConstancia() {
		return tipoConstancia;
	}

	public void setTipoConstancia(int tipoConstancia) {
		this.tipoConstancia = tipoConstancia;
	}

	public String getCuotas() {
		return cuotas;
	}

	public void setCuotas(String cuotas) {
		this.cuotas = cuotas;
	}
	
	

}
