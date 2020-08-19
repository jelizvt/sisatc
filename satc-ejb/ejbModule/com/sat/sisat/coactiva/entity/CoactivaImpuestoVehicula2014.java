package com.sat.sisat.coactiva.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the COACTIVA_MULTAS_TRANSITO_2013 database table.
 * 
 */
@Entity
@Table(name="COACTIVA_IMPUESTO_VEHICULAR_2014")



@NamedQueries({
	@NamedQuery(name="getAllCoactivaImpuestoVehicula2014", query="SELECT a FROM CoactivaImpuestoVehicula2014 a ORDER BY a.id"),
	@NamedQuery(name="getCoactivaImpuestoVehicula2014By", query="SELECT a FROM CoactivaImpuestoVehicula2014 a WHERE a.campo4=:p_persona_id ORDER BY a.id")
})

public class CoactivaImpuestoVehicula2014 extends CobranzaCoactiva {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;
	
	private String campo1;
	private String campo2;
	private String campo3;
	private Integer campo4;
	private String campo5;
	private String campo6;
	
	private String estado;
	private BigDecimal deuda;
	
	private String fecha_genera_rec1;
	private String fecha_emision_rec1;
	private String fecha_notificacion_rec1;
	private String fecha_genera_rec2;
	private String fecha_emision_rec2;
	private String fecha_notificacion_rec2;
	private String costas;
	private String suspension;
	private String observaciones;
	
	@Column(name="estado_real")
	private String estado_real;
	
	@Column(name="usuario_id_upd")
	private String usuarioIdUpd;
	
	@Column(name="terminal_upd")
	private String terminalUpd;
	
	@Column(name="fecha_registro_upd")
	private Timestamp fechaRegistroUpd;
	
	
	
	
	
	public String getUsuarioIdUpd() {
		return usuarioIdUpd;
	}

	public void setUsuarioIdUpd(String usuarioIdUpd) {
		this.usuarioIdUpd = usuarioIdUpd;
	}

	public String getTerminalUpd() {
		return terminalUpd;
	}

	public void setTerminalUpd(String terminalUpd) {
		this.terminalUpd = terminalUpd;
	}

	public Timestamp getFechaRegistroUpd() {
		return fechaRegistroUpd;
	}

	public void setFechaRegistroUpd(Timestamp fechaRegistroUpd) {
		this.fechaRegistroUpd = fechaRegistroUpd;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getCampo2() {
		return campo2;
	}
	public void setCampo2(String campo2) {
		this.campo2 = campo2;
	}

	public String getCampo1() {
		return campo1;
	}

	public void setCampo1(String campo1) {
		this.campo1 = campo1;
	}

	public String getCampo3() {
		return campo3;
	}

	public void setCampo3(String campo3) {
		this.campo3 = campo3;
	}

	public Integer getCampo4() {
		return campo4;
	}

	public void setCampo4(Integer campo4) {
		this.campo4 = campo4;
	}

	public String getCampo5() {
		return campo5;
	}

	public void setCampo5(String campo5) {
		this.campo5 = campo5;
	}

	public String getCampo6() {
		return campo6;
	}

	public void setCampo6(String campo6) {
		this.campo6 = campo6;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public BigDecimal getDeuda() {
		return deuda;
	}

	public void setDeuda(BigDecimal deuda) {
		this.deuda = deuda;
	}

	public String getFecha_genera_rec1() {
		return fecha_genera_rec1;
	}

	public void setFecha_genera_rec1(String fecha_genera_rec1) {
		this.fecha_genera_rec1 = fecha_genera_rec1;
	}

	public String getFecha_emision_rec1() {
		return fecha_emision_rec1;
	}

	public void setFecha_emision_rec1(String fecha_emision_rec1) {
		this.fecha_emision_rec1 = fecha_emision_rec1;
	}

	public String getFecha_notificacion_rec1() {
		return fecha_notificacion_rec1;
	}

	public void setFecha_notificacion_rec1(String fecha_notificacion_rec1) {
		this.fecha_notificacion_rec1 = fecha_notificacion_rec1;
	}

	public String getFecha_genera_rec2() {
		return fecha_genera_rec2;
	}

	public void setFecha_genera_rec2(String fecha_genera_rec2) {
		this.fecha_genera_rec2 = fecha_genera_rec2;
	}

	public String getFecha_emision_rec2() {
		return fecha_emision_rec2;
	}

	public void setFecha_emision_rec2(String fecha_emision_rec2) {
		this.fecha_emision_rec2 = fecha_emision_rec2;
	}

	public String getFecha_notificacion_rec2() {
		return fecha_notificacion_rec2;
	}

	public void setFecha_notificacion_rec2(String fecha_notificacion_rec2) {
		this.fecha_notificacion_rec2 = fecha_notificacion_rec2;
	}

	public String getCostas() {
		return costas;
	}

	public void setCostas(String costas) {
		this.costas = costas;
	}

	public String getSuspension() {
		return suspension;
	}

	public void setSuspension(String suspension) {
		this.suspension = suspension;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getEstado_real() {
		return estado_real;
	}

	public void setEstado_real(String estado_real) {
		this.estado_real = estado_real;
	}
	
}