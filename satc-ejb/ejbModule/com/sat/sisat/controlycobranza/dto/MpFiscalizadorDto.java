package com.sat.sisat.controlycobranza.dto;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class MpFiscalizadorDto implements Serializable{
	
	@Id
	@GeneratedValue( strategy=GenerationType.IDENTITY)
	@Column(name="inspector_id")	
	private int idfiscalizador;
	
	@Column(name="nombre_inspector")	
	private String nombresApellidos;
	
	@Column(name="nro_dni")	
	private String dni;
	
	@Column(name="nro_celular")	
	private String celular;
	
	@Column(name="nombre_direccion")	
	private String direccion;
	
	@Column(name="fecha_inicio")
	private Date fini;
	
	@Column(name="fecha_fin")
	private Date ffin;
	
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
	
	@Column(name = "descripcion")
	private String descripcion;

	@Column(name="unidad_id")	
	private int unidad_id;

	public int getUnidad_id() {
		return unidad_id;
	}

	public void setUnidad_id(int unidad_id) {
		this.unidad_id = unidad_id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getIdfiscalizador() {
		return idfiscalizador;
	}

	public String getNombresApellidos() {
		return nombresApellidos;
	}

	public String getDni() {
		return dni;
	}

	public String getDireccion() {
		return direccion;
	}

	public Date getFini() {
		return fini;
	}

	public Date getFfin() {
		return ffin;
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

	public void setIdfiscalizador(int idfiscalizador) {
		this.idfiscalizador = idfiscalizador;
	}

	public void setNombresApellidos(String nombresApellidos) {
		this.nombresApellidos = nombresApellidos;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public void setFini(Date fini) {
		this.fini = fini;
	}

	public void setFfin(Date ffin) {
		this.ffin = ffin;
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

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

}
