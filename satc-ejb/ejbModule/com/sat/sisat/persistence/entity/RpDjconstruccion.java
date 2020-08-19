package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the rp_djconstruccion database table.
 * 
 */
@Entity
@Table(name="rp_djconstruccion")
@NamedQuery(name="getMaxRpDjConstruccionByDjId", query="SELECT MAX(a.construccionId) FROM RpDjconstruccion a WHERE a.djId=:p_dj_id AND a.estado='1'")
public class RpDjconstruccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="dj_id")
	private Integer djId;

	@Id
	@Column(name="construccion_id")
	private Integer construccionId;
	
	@Column(name="clasi_depreciacion_id")
	private Integer clasiDepreciacionId;
	
	@Column(name="conservacion_id")
	private Integer conservacionId;
	
	@Column(name="tipo_nivel_id")
	private Integer tipoNivelId;
	
	@Column(name="mat_predominante_id")
	private Integer matPredominanteId;
	
	@Column(name="nro_nivel")
	private Integer nroNivel;
	
	@Column(name="seccion")
	private String seccion;
	
	@Column(name="anno_construccion")
	private Integer annoConstruccion;
	
	@Column(name="anno_actualizacion")
	private Integer annoActualizacion;

	@Column(name="area_comun_construccion")
	private BigDecimal areaComunConstruccion;

	@Column(name="area_construccion")
	private BigDecimal areaConstruccion;
	
	@Column(name="area_terreno")
	private BigDecimal areaTerreno;

	private Integer bannos;

	private Integer electricos;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="mes_construccion")
	private String mesConstruccion;

	private Integer muros;

	private Integer pisos;

	private Integer puertas;

	private String referencia;

	private Integer revestimientos;

	private Integer techo;

	private String terminal;
	
	private String estado;

	@Column(name="tipo_unidad_medida")
	private String tipoUnidadMedida;

	@Column(name="usuario_id")
	private Integer usuarioId;
	
	@Transient
	private Integer newConstruccionId;
	
	@Transient
	private Integer item;
	@Transient
	private String dentiponivel;
	@Transient
	private String denmatpredominante;
	@Transient
	private String denestadoconservacion;
	@Transient
	private String dentipodepreciacion;
	@Transient
	private String denmuros;
	@Transient
	private String dentecho;
	@Transient
	private String denpisos;
	@Transient
	private String denpuertas;
	@Transient
	private String denrevestimiento;
	@Transient
	private String denbannos;
	@Transient
	private String denelectrico;
	@Transient
	private String denoTipoNivel;
	
	public RpDjconstruccion() {
		
    }
	
    public String getDentiponivel() {
		return dentiponivel;
	}

	public void setDentiponivel(String dentiponivel) {
		this.dentiponivel = dentiponivel;
	}

	public String getDenmatpredominante() {
		return denmatpredominante;
	}

	public void setDenmatpredominante(String denmatpredominante) {
		this.denmatpredominante = denmatpredominante;
	}

	public String getDenestadoconservacion() {
		return denestadoconservacion;
	}

	public void setDenestadoconservacion(String denestadoconservacion) {
		this.denestadoconservacion = denestadoconservacion;
	}

	public String getDentipodepreciacion() {
		return dentipodepreciacion;
	}

	public void setDentipodepreciacion(String dentipodepreciacion) {
		this.dentipodepreciacion = dentipodepreciacion;
	}

	public String getDenmuros() {
		return denmuros;
	}

	public void setDenmuros(String denmuros) {
		this.denmuros = denmuros;
	}

	public String getDentecho() {
		return dentecho;
	}

	public void setDentecho(String dentecho) {
		this.dentecho = dentecho;
	}

	public String getDenpisos() {
		return denpisos;
	}

	public void setDenpisos(String denpisos) {
		this.denpisos = denpisos;
	}

	public String getDenpuertas() {
		return denpuertas;
	}

	public void setDenpuertas(String denpuertas) {
		this.denpuertas = denpuertas;
	}

	public String getDenrevestimiento() {
		return denrevestimiento;
	}

	public void setDenrevestimiento(String denrevestimiento) {
		this.denrevestimiento = denrevestimiento;
	}

	public String getDenbannos() {
		return denbannos;
	}

	public void setDenbannos(String denbannos) {
		this.denbannos = denbannos;
	}

	public String getDenelectrico() {
		return denelectrico;
	}

	public void setDenelectrico(String denelectrico) {
		this.denelectrico = denelectrico;
	}

	public Integer getAnnoConstruccion() {
		return this.annoConstruccion;
	}

	public void setAnnoConstruccion(Integer annoConstruccion) {
		this.annoConstruccion = annoConstruccion;
	}

	public BigDecimal getAreaComunConstruccion() {
		return this.areaComunConstruccion;
	}

	public void setAreaComunConstruccion(BigDecimal areaComunConstruccion) {
		this.areaComunConstruccion = areaComunConstruccion;
	}

	public BigDecimal getAreaConstruccion() {
		return this.areaConstruccion;
	}

	public void setAreaConstruccion(BigDecimal areaConstruccion) {
		this.areaConstruccion = areaConstruccion;
	}

	public Integer getBannos() {
		return this.bannos;
	}

	public void setBannos(Integer bannos) {
		this.bannos = bannos;
	}

	public Integer getClasiDepreciacionId() {
		return this.clasiDepreciacionId;
	}

	public void setClasiDepreciacionId(Integer clasiDepreciacionId) {
		this.clasiDepreciacionId = clasiDepreciacionId;
	}

	public Integer getConservacionId() {
		return this.conservacionId;
	}

	public void setConservacionId(Integer conservacionId) {
		this.conservacionId = conservacionId;
	}

	public Integer getElectricos() {
		return this.electricos;
	}

	public void setElectricos(Integer electricos) {
		this.electricos = electricos;
	}

	public Timestamp getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Integer getMatPredominanteId() {
		return this.matPredominanteId;
	}

	public void setMatPredominanteId(Integer matPredominanteId) {
		this.matPredominanteId = matPredominanteId;
	}

	public String getMesConstruccion() {
		return this.mesConstruccion;
	}

	public void setMesConstruccion(String mesConstruccion) {
		this.mesConstruccion = mesConstruccion;
	}

	public Integer getMuros() {
		return this.muros;
	}

	public void setMuros(Integer muros) {
		this.muros = muros;
	}

	public Integer getNroNivel() {
		return this.nroNivel;
	}

	public void setNroNivel(Integer nroNivel) {
		this.nroNivel = nroNivel;
	}

	public Integer getPisos() {
		return this.pisos;
	}

	public void setPisos(Integer pisos) {
		this.pisos = pisos;
	}

	public Integer getPuertas() {
		return this.puertas;
	}

	public void setPuertas(Integer puertas) {
		this.puertas = puertas;
	}

	public String getReferencia() {
		return this.referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public Integer getRevestimientos() {
		return this.revestimientos;
	}

	public void setRevestimientos(Integer revestimientos) {
		this.revestimientos = revestimientos;
	}

	public Integer getTecho() {
		return this.techo;
	}

	public void setTecho(Integer techo) {
		this.techo = techo;
	}

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public Integer getTipoNivelId() {
		return this.tipoNivelId;
	}

	public void setTipoNivelId(Integer tipoNivelId) {
		this.tipoNivelId = tipoNivelId;
	}

	public String getTipoUnidadMedida() {
		return this.tipoUnidadMedida;
	}

	public void setTipoUnidadMedida(String tipoUnidadMedida) {
		this.tipoUnidadMedida = tipoUnidadMedida;
	}

	public Integer getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}
	public Integer getDjId() {
		return this.djId;
	}
	public void setDjId(Integer djId) {
		this.djId = djId;
	}
	public Integer getConstruccionId() {
		return this.construccionId;
	}
	public void setConstruccionId(Integer construccionId) {
		this.construccionId = construccionId;
	}
	public Integer getItem() {
		return item;
	}

	public void setItem(Integer item) {
		this.item = item;
	}
	public String getDenoTipoNivel() {
		return denoTipoNivel;
	}

	public void setDenoTipoNivel(String denoTipoNivel) {
		this.denoTipoNivel = denoTipoNivel;
	}
	public BigDecimal getAreaTerreno() {
		return areaTerreno;
	}

	public void setAreaTerreno(BigDecimal areaTerreno) {
		this.areaTerreno = areaTerreno;
	}
	
	public Integer getNewConstruccionId() {
		return newConstruccionId;
	}

	public void setNewConstruccionId(Integer newConstruccionId) {
		this.newConstruccionId = newConstruccionId;
	}
	
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String getSeccion() {
		return seccion;
	}

	public void setSeccion(String seccion) {
		this.seccion = seccion;
	}

	public Integer getAnnoActualizacion() {
		return annoActualizacion;
	}

	public void setAnnoActualizacion(Integer annoActualizacion) {
		this.annoActualizacion = annoActualizacion;
	}
}