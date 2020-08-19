package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * The persistent class for the rp_instalacion_dj database table.
 * 
 */
@Entity
@Table(name="rp_instalacion_dj")
public class RpInstalacionDj implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="instalacion_id")
	private Integer instalacionId;

	@Column(name="anno_instalacion")
	private Integer annoInstalacion;

	private String estado;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="mes_instalacion")
	private String mesInstalacion;

	@Column(name="numero_documento")
	private String numeroDocumento;
	
	private String referencia;

	private String terminal;

	@Column(name="tipo_obra_id")
	private Integer tipoObraId;

	@Column(name="usuario_id")
	private Integer usuarioId;

	@Column(name="valor_instalacion")
	private BigDecimal valorInstalacion;

	@Transient
	private String tipoInstalacion;
	
	@Transient
	private String mes;
	
	@Transient
	private Integer item;
	
	@Column(name="nro_nivel")
	private Integer nroNivel;
	
	@Column(name="area_terreno")
	private BigDecimal areaTerreno;
	
	@Column(name="mat_predominante_id")
	private Integer matPredominanteId;

	@Column(name="conservacion_id")
	private Integer conservacionId;

	@Column(name="clasi_depreciacion_id")
	private Integer clasiDepreciacionId;
	
	@Transient
	private Integer categoriaObraId;
	
	@Column(name="dj_id")
	private Integer djId;
	
	@Column(name="valor_unitario_ofic")
	private BigDecimal valorUnitarioOficializado;
	
	@Column(name="valor_unitario_deprec")
	private BigDecimal valorUnitarioDepreciado;
	
	@Column(name="valor_obra")
	private BigDecimal valorObra;
	
	public RpInstalacionDj() {
		
    }
	
	public String getTipoInstalacion() {
		return tipoInstalacion;
	}

	public void setTipoInstalacion(String tipoInstalacion) {
		this.tipoInstalacion = tipoInstalacion;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public Integer getItem() {
		return item;
	}

	public void setItem(Integer item) {
		this.item = item;
	}

	public Integer getAnnoInstalacion() {
		return this.annoInstalacion;
	}

	public void setAnnoInstalacion(Integer annoInstalacion) {
		this.annoInstalacion = annoInstalacion;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Timestamp getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getReferencia() {
		return this.referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public Integer getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public BigDecimal getValorInstalacion() {
		return this.valorInstalacion;
	}

	public void setValorInstalacion(BigDecimal valorInstalacion) {
		this.valorInstalacion = valorInstalacion;
	}
	public Integer getDjId() {
		return djId;
	}

	public void setDjId(Integer djId) {
		this.djId = djId;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getMesInstalacion() {
		return mesInstalacion;
	}

	public void setMesInstalacion(String mesInstalacion) {
		this.mesInstalacion = mesInstalacion;
	}
	
	public Integer getInstalacionId() {
		return instalacionId;
	}

	public void setInstalacionId(Integer instalacionId) {
		this.instalacionId = instalacionId;
	}

	public Integer getTipoObraId() {
		return tipoObraId;
	}

	public void setTipoObraId(Integer tipoObraId) {
		this.tipoObraId = tipoObraId;
	}
	
	public Integer getNroNivel() {
		return nroNivel;
	}

	public void setNroNivel(Integer nroNivel) {
		this.nroNivel = nroNivel;
	}
	
	public BigDecimal getAreaTerreno() {
		return areaTerreno;
	}

	public void setAreaTerreno(BigDecimal areaTerreno) {
		this.areaTerreno = areaTerreno;
	}
	
	public Integer getMatPredominanteId() {
		return matPredominanteId;
	}

	public void setMatPredominanteId(Integer matPredominanteId) {
		this.matPredominanteId = matPredominanteId;
	}

	public Integer getConservacionId() {
		return conservacionId;
	}

	public void setConservacionId(Integer conservacionId) {
		this.conservacionId = conservacionId;
	}

	public Integer getClasiDepreciacionId() {
		return clasiDepreciacionId;
	}

	public void setClasiDepreciacionId(Integer clasiDepreciacionId) {
		this.clasiDepreciacionId = clasiDepreciacionId;
	}
	
	public Integer getCategoriaObraId() {
		return categoriaObraId;
	}

	public void setCategoriaObraId(Integer categoriaObraId) {
		this.categoriaObraId = categoriaObraId;
	}

	public BigDecimal getValorUnitarioOficializado() {
		return valorUnitarioOficializado;
	}

	public void setValorUnitarioOficializado(BigDecimal valorUnitarioOficializado) {
		this.valorUnitarioOficializado = valorUnitarioOficializado;
	}

	public BigDecimal getValorUnitarioDepreciado() {
		return valorUnitarioDepreciado;
	}

	public void setValorUnitarioDepreciado(BigDecimal valorUnitarioDepreciado) {
		this.valorUnitarioDepreciado = valorUnitarioDepreciado;
	}

	public BigDecimal getValorObra() {
		return valorObra;
	}

	public void setValorObra(BigDecimal valorObra) {
		this.valorObra = valorObra;
	}
}