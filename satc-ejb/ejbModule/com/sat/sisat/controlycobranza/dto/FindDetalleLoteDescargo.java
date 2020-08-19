package com.sat.sisat.controlycobranza.dto;
/**
 * @author Cristobal Ramires -=CRAMIREZ=-
 * @version 0.1
 * @since 28/02/2019 FindDetalleLoteDescargo
 */

import java.io.Serializable;
import java.math.BigDecimal;

public class FindDetalleLoteDescargo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer descargo_id;
	private Integer tipo_descargo;
	private Integer tipo_documento_id;
	private String nro_documento;
	private java.sql.Timestamp fecha_documento;
	private String observacion;
	private BigDecimal total_descargado;
	private Integer deuda_id;
	private BigDecimal interes;
	private BigDecimal reajuste;
	private BigDecimal total_deuda;
	private java.sql.Timestamp fecha_registro_deuda;
	private String estado;
	private Integer usuario_id;
	private java.sql.Timestamp fecha_registro;
	private String terminal;
	private Integer persona_id;
	private Integer persona__transfire_id;
	private String recibo_id;
	private Integer lote_descargo_id;
	private String concepto;
	private String sub_concepto;
	private Integer nro_cuota; 
	private Integer anno_deuda;
	private BigDecimal insoluto;
	private String descripcion_tipo_documento;
	private String descripcion_tipo_descargo;
	private String contribuyente;
	private String usuario;
	private Boolean isSelect;
	private Integer nro_lote;
	private String  estado_descargo;
	public Integer getDescargo_id() {
		return descargo_id;
	}
	public void setDescargo_id(Integer descargo_id) {
		this.descargo_id = descargo_id;
	}
	public Integer getTipo_descargo() {
		return tipo_descargo;
	}
	public void setTipo_descargo(Integer tipo_descargo) {
		this.tipo_descargo = tipo_descargo;
	}
	public Integer getTipo_documento_id() {
		return tipo_documento_id;
	}
	public void setTipo_documento_id(Integer tipo_documento_id) {
		this.tipo_documento_id = tipo_documento_id;
	}
	public String getNro_documento() {
		return nro_documento;
	}
	public void setNro_documento(String nro_documento) {
		this.nro_documento = nro_documento;
	}
	public java.sql.Timestamp getFecha_documento() {
		return fecha_documento;
	}
	public void setFecha_documento(java.sql.Timestamp fecha_documento) {
		this.fecha_documento = fecha_documento;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public BigDecimal getTotal_descargado() {
		return total_descargado;
	}
	public void setTotal_descargado(BigDecimal total_descargado) {
		this.total_descargado = total_descargado;
	}
	public Integer getDeuda_id() {
		return deuda_id;
	}
	public void setDeuda_id(Integer deuda_id) {
		this.deuda_id = deuda_id;
	}
	public BigDecimal getInteres() {
		return interes;
	}
	public void setInteres(BigDecimal interes) {
		this.interes = interes;
	}
	public BigDecimal getReajuste() {
		return reajuste;
	}
	public void setReajuste(BigDecimal reajuste) {
		this.reajuste = reajuste;
	}
	public BigDecimal getTotal_deuda() {
		return total_deuda;
	}
	public void setTotal_deuda(BigDecimal total_deuda) {
		this.total_deuda = total_deuda;
	}
	public java.sql.Timestamp getFecha_registro_deuda() {
		return fecha_registro_deuda;
	}
	public void setFecha_registro_deuda(java.sql.Timestamp fecha_registro_deuda) {
		this.fecha_registro_deuda = fecha_registro_deuda;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Integer getUsuario_id() {
		return usuario_id;
	}
	public void setUsuario_id(Integer usuario_id) {
		this.usuario_id = usuario_id;
	}
	public java.sql.Timestamp getFecha_registro() {
		return fecha_registro;
	}
	public void setFecha_registro(java.sql.Timestamp fecha_registro) {
		this.fecha_registro = fecha_registro;
	}
	public String getTerminal() {
		return terminal;
	}
	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
	public Integer getPersona_id() {
		return persona_id;
	}
	public void setPersona_id(Integer persona_id) {
		this.persona_id = persona_id;
	}
	public Integer getPersona__transfire_id() {
		return persona__transfire_id;
	}
	public void setPersona__transfire_id(Integer persona__transfire_id) {
		this.persona__transfire_id = persona__transfire_id;
	}
	public String getRecibo_id() {
		return recibo_id;
	}
	public void setRecibo_id(String recibo_id) {
		this.recibo_id = recibo_id;
	}
	public Integer getLote_descargo_id() {
		return lote_descargo_id;
	}
	public void setLote_descargo_id(Integer lote_descargo_id) {
		this.lote_descargo_id = lote_descargo_id;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public String getSub_concepto() {
		return sub_concepto;
	}
	public void setSub_concepto(String sub_concepto) {
		this.sub_concepto = sub_concepto;
	}
	public Integer getNro_cuota() {
		return nro_cuota;
	}
	public void setNro_cuota(Integer nro_cuota) {
		this.nro_cuota = nro_cuota;
	}
	public Integer getAnno_deuda() {
		return anno_deuda;
	}
	public void setAnno_deuda(Integer anno_deuda) {
		this.anno_deuda = anno_deuda;
	}
	public BigDecimal getInsoluto() {
		return insoluto;
	}
	public void setInsoluto(BigDecimal insoluto) {
		this.insoluto = insoluto;
	}
	public String getDescripcion_tipo_documento() {
		return descripcion_tipo_documento;
	}
	public void setDescripcion_tipo_documento(String descripcion_tipo_documento) {
		this.descripcion_tipo_documento = descripcion_tipo_documento;
	}
	public String getDescripcion_tipo_descargo() {
		return descripcion_tipo_descargo;
	}
	public void setDescripcion_tipo_descargo(String descripcion_tipo_descargo) {
		this.descripcion_tipo_descargo = descripcion_tipo_descargo;
	}
	public String getContribuyente() {
		return contribuyente;
	}
	public void setContribuyente(String contribuyente) {
		this.contribuyente = contribuyente;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public Boolean getIsSelect() {
		return isSelect;
	}
	public void setIsSelect(Boolean isSelect) {
		this.isSelect = isSelect;
	}
	public Integer getNro_lote() {
		return nro_lote;
	}
	public void setNro_lote(Integer nro_lote) {
		this.nro_lote = nro_lote;
	}
	public String getEstado_descargo() {
		return estado_descargo;
	}
	public void setEstado_descargo(String estado_descargo) {
		this.estado_descargo = estado_descargo;
	}
	
	
}
