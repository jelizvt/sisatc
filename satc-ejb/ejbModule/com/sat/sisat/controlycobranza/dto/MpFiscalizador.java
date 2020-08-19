package com.sat.sisat.controlycobranza.dto;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity 
@Table(name="mp_fiscalizador")
public class MpFiscalizador implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue( strategy=GenerationType.IDENTITY)
	@Column(name="inspector_id")	
	private int idfiscalizador;
	
	@Column(name="nombre_inspector")	
	private String nombresApellidos;
	
	@Column(name="nro_dni")	
	private String dni;
	
	@Column(name="nombre_direccion")	
	private String direccion;
	
	@Column(name="fecha_inicio")
	private Date fini;
	
	@Column(name="fecha_fin")
	private Date ffin;
	
	@Column(name="unidad_id")	
	private int unidad_id;
	

	
	public int getUnidad_id() {
		return unidad_id;
	}

	public void setUnidad_id(int unidad_id) {
		this.unidad_id = unidad_id;
	}

	@Column(name="codigo")
	private String codigo;
	
	@Column(name="estado")
	private String  estado;
	
	@Column(name="usuario_id")
	private int usuarioId;
	
	@Column(name="fecha_registro")
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date fechaRegistro;
	
	@Column(name="terminal")
	private String termninal;	
	

	public int getIdfiscalizador() {
		return idfiscalizador;
	}

	public void setIdfiscalizador(int idfiscalizador) {
		this.idfiscalizador = idfiscalizador;
	}

	public String getNombresApellidos() {
		return nombresApellidos;
	}

	public void setNombresApellidos(String nombresApellidos) {
		this.nombresApellidos = nombresApellidos;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Date getFini() {
		return fini;
	}

	public void setFini(Date fini) {
		this.fini = fini;
	}

	public Date getFfin() {
		return ffin;
	}

	public void setFfin(Date ffin) {
		this.ffin = ffin;
	}	
	
	
	
	
	public String getEstado() {
		return estado;
	}

	public int getUsuarioId() {
		return usuarioId;
	}

	public java.util.Date getFechaRegistro() {
		return fechaRegistro;
	}

	public String getTermninal() {
		return termninal;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public void setFechaRegistro(java.util.Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public void setTermninal(String termninal) {
		this.termninal = termninal;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
}
