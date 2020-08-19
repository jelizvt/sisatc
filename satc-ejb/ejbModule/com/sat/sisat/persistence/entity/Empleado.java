/**
 * @author Claudio Chaucca
 * @version 1.0 
 * @since 11/12/2011
 * Entidad Empleado que continene los registros de la tabla EMPLEADO en la base de datos 
 */
package com.sat.sisat.persistence.entity;

import java.io.Serializable;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="empleado")
public class Empleado implements Serializable {
	@Id
	@Column (name="id")
	private Integer id;
	
	@Column (name="edad")
	private Integer edad;
	
	@Column (name="nombre")
	private String nombre;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
}
