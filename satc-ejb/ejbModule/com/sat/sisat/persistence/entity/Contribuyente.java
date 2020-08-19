/**
 * @author Claudio Chaucca
 * @version 1.0 
 * @since 11/12/2011
 * Entidad Contribuyente que continene los registros de la tabla CONTRIBUYENTE en la base de datos 
 */

package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sat.sisat.common.vo.ContribuyenteVo;

@Entity
@Table(name="contribuyente")
public class Contribuyente implements Serializable {
	@Id
	@Column (name="id")
	@GeneratedValue
	private Integer id;
	
	@Column (name="fechaInscripcion")
	private String fechaInscripcion;
	
	@Column (name="estado")
	private String estado;
	
	@Column (name="tipoDocIdent")
	private String tipoDocIdent;
	
	@Column (name="nroDocIdent")
	private String nroDocIdent;
	
	@Column (name="fechaConstitucionNacimiento")
	private Date fechaConstitucionNacimiento;
	
	public Date getFechaConstitucionNacimiento() {
		return fechaConstitucionNacimiento;
	}
	public void setFechaConstitucionNacimiento(Date fechaConstitucionNacimiento) {
		this.fechaConstitucionNacimiento = fechaConstitucionNacimiento;
	}
	public Boolean getNotificarEmail() {
		return notificarEmail;
	}
	public void setNotificarEmail(Boolean notificarEmail) {
		this.notificarEmail = notificarEmail;
	}
	public Date getFechaDefuncion() {
		return fechaDefuncion;
	}
	public void setFechaDefuncion(Date fechaDefuncion) {
		this.fechaDefuncion = fechaDefuncion;
	}

	@Column (name="apePaterno")
	private String apePaterno;
	
	@Column (name="apeMaterno")
	private String apeMaterno;
	
	@Column (name="nombres")
	private String nombres;
	
	@Column (name="telefono1")
	private String telefono1;
	
	@Column (name="telefono2")
	private String telefono2;
	
	@Column (name="fax")
	private String fax;
	
	@Column (name="email")
	private String email;
	
	@Column (name="notificarEmail")
	private Boolean notificarEmail;
	
	@Column (name="partidaDefuncion")
	private String partidaDefuncion;
	
	@Column (name="fechaDefuncion")
	private Date fechaDefuncion;
	
	public String getFechaInscripcion() {
		return fechaInscripcion;
	}
	public void setFechaInscripcion(String fechaInscripcion) {
		this.fechaInscripcion = fechaInscripcion;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTipoDocIdent() {
		return tipoDocIdent;
	}
	public void setTipoDocIdent(String tipoDocIdent) {
		this.tipoDocIdent = tipoDocIdent;
	}
	public String getNroDocIdent() {
		return nroDocIdent;
	}
	public void setNroDocIdent(String nroDocIdent) {
		this.nroDocIdent = nroDocIdent;
	}
	public String getApePaterno() {
		return apePaterno;
	}
	public void setApePaterno(String apePaterno) {
		this.apePaterno = apePaterno;
	}
	public String getApeMaterno() {
		return apeMaterno;
	}
	public void setApeMaterno(String apeMaterno) {
		this.apeMaterno = apeMaterno;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getTelefono1() {
		return telefono1;
	}
	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}
	public String getTelefono2() {
		return telefono2;
	}
	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPartidaDefuncion() {
		return partidaDefuncion;
	}
	public void setPartidaDefuncion(String partidaDefuncion) {
		this.partidaDefuncion = partidaDefuncion;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void getAttribute(ContribuyenteVo contribuyente){
		setFechaInscripcion(contribuyente.getFechaInscripcion());
		setEstado(contribuyente.getEstado());
		setTipoDocIdent(contribuyente.getTipoDocIdent());
		setNroDocIdent(contribuyente.getNroDocIdent());
		setFechaConstitucionNacimiento(contribuyente.getFechaConstitucionNacimiento());
		setApePaterno(contribuyente.getApePaterno());
		setApeMaterno(contribuyente.getApeMaterno());
		setNombres(contribuyente.getNombres());
		setTelefono1(contribuyente.getTelefono1());
		setTelefono2(contribuyente.getTelefono2());
		setFax(contribuyente.getFax());
		setEmail(contribuyente.getEmail());
		setNotificarEmail(contribuyente.getNotificarEmail());
		setPartidaDefuncion(contribuyente.getPartidaDefuncion());
		setFechaDefuncion(contribuyente.getFechaDefuncion());
	}
	
}
