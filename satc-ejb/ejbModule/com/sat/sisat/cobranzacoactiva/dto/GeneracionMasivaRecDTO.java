package com.sat.sisat.cobranzacoactiva.dto;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import com.sat.sisat.common.dto.BaseDTO;

//Almacena todas las generaciones emitidas masivamente, mediante el módulo de GestionExpediente.xhtml
public class GeneracionMasivaRecDTO extends BaseDTO  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer carteraId	;
	private Date 	fecha;
	private Integer generacionMasivaId;
	private Time  	hora;
	private Integer tipoRecId;
	private Integer usuarioId;	
	private Integer materiaId;
	private Integer cantidadRec;
	
	private String termial;
	private String descripcion;
	private String textoMostrar;
	
	private String archivoJasper;
	
	
	public String getArchivoJasper() {
		return archivoJasper;
	}

	public void setArchivoJasper(String archivo_jasper) {
		this.archivoJasper = archivo_jasper;
	}

	public String getTextoMostrar() {
		return textoMostrar;
	}

	public void setTextoMostrar(String textoMostrar) {
		this.textoMostrar = textoMostrar;
	}

		
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Time getHora() {
		return hora;
	}
	public void setHora(Time hora) {
		this.hora = hora;
	}

	public String getTermial() {
		return termial;
	}

	public void setTermial(String termial) {
		this.termial = termial;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getGeneracionMasivaId() {
		return generacionMasivaId;
	}
	
	//Temporal no se usa, puesto que el campo es un Identity.
	public void setGeneracionMasivaId(Integer generacionMasivaId) {
		this.generacionMasivaId = generacionMasivaId;
	}

	public Integer getCarteraId() {
		return carteraId;
	}

	public void setCarteraId(Integer carteraId) {
		this.carteraId = carteraId;
	}

	public Integer getTipoRecId() {
		return tipoRecId;
	}

	public void setTipoRecId(Integer tipoRecId) {
		this.tipoRecId = tipoRecId;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Integer getMateriaId() {
		return materiaId;
	}

	public void setMateriaId(Integer materiaId) {
		this.materiaId = materiaId;
	}


	public Integer getCantidadRec() {
		return cantidadRec;
	}

	public void setCantidadRec(Integer cantidadRec) {
		this.cantidadRec = cantidadRec;
	}

}
