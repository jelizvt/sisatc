package com.sat.sisat.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the ra_djalcabala_historico database table.
 * 
 */
@Entity
@Table(name="ra_djalcabala_historico")
public class RaDjalcabalaHistorico implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	@Column(name="djalcabala_historico_id")
	private int djalcabalaHistoricoId;

	private BigDecimal ajuste;

	@Column(name="anno_declaracion")
	private int annoDeclaracion;

	@Column(name="autovaluo_afecta")
	private BigDecimal autovaluoAfecta;

	@Column(name="autovaluo_ajustado")
	private BigDecimal autovaluoAjustado;

	@Column(name="autovaluo_total")
	private BigDecimal autovaluoTotal;

	@Column(name="base_afecta")
	private BigDecimal baseAfecta;

	@Column(name="base_exonerada")
	private BigDecimal baseExonerada;

	@Column(name="base_imponible")
	private BigDecimal baseImponible;

	@Column(name="codigo_postal_distrito")
	private String codigoPostalDistrito;

	@Column(name="condicion_propiedad_id")
	private int condicionPropiedadId;

	@Column(name="departamento_id")
	private int departamentoId;

	@Column(name="distrito_id")
	private int distritoId;

	@Column(name="dj_id")
	private int djId;

	@Column(name="djalcabala_id")
	private int djalcabalaId;

	private String estado;

	@Column(name="fecha_declaracion")
	private Timestamp fechaDeclaracion;

	@Column(name="fecha_operacion")
	private Timestamp fechaOperacion;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;

	@Column(name="fecha_transferencia")
	private String fechaTransferencia;

	@Column(name="flag_primera_venta")
	private String flagPrimeraVenta;

	@Column(name="formato_formulario")
	private String formatoFormulario;

	private String glosa;

	@Column(name="glosa_sustento")
	private String glosaSustento;

	private BigDecimal interes;

	@Column(name="motivo_actualizacion_id")
	private int motivoActualizacionId;

	@Column(name="notaria_id")
	private int notariaId;

	@Column(name="nro_documento_sustento")
	private String nroDocumentoSustento;

	@Column(name="nro_formulario")
	private String nroFormulario;

	@Column(name="nro_orden_dj")
	private String nroOrdenDj;

	@Column(name="persona_id")
	private int personaId;

	@Column(name="porc_propiedad")
	private BigDecimal porcPropiedad;

	@Column(name="predio_alcabala_id")
	private int predioAlcabalaId;

	@Column(name="provincia_id")
	private int provinciaId;

	private BigDecimal tasa;

	@Column(name="tasa_ajuste")
	private BigDecimal tasaAjuste;

	private String terminal;

	@Column(name="tiene_deuda")
	private String tieneDeuda;

	@Column(name="tipo_documento_sustento_id")
	private int tipoDocumentoSustentoId;

	@Column(name="tipo_moneda_id")
	private int tipoMonedaId;

	@Column(name="tipo_operacion_id")
	private int tipoOperacionId;

	@Column(name="tipo_predio")
	private String tipoPredio;

	@Column(name="total_deuda")
	private BigDecimal totalDeuda;

	@Column(name="ubicacion_predio")
	private String ubicacionPredio;

	@Column(name="usuario_id")
	private int usuarioId;

	@Column(name="valor_impuesto")
	private BigDecimal valorImpuesto;

	@Column(name="valor_transferencia")
	private BigDecimal valorTransferencia;

	@Column(name="valor_transferencia_soles")
	private BigDecimal valorTransferenciaSoles;
	
	@Column(name="tipo_transferencia_id")
	private int tipoTransferenciaId;

	//bi-directional many-to-one association to RaTipoTransferencia
//    @ManyToOne
//	@JoinColumn(name="tipo_transferencia_id")
//	private RaTipoTransferencia raTipoTransferencia;

    public RaDjalcabalaHistorico() {
    }

	public int getDjalcabalaHistoricoId() {
		return this.djalcabalaHistoricoId;
	}

	public void setDjalcabalaHistoricoId(int djalcabalaHistoricoId) {
		this.djalcabalaHistoricoId = djalcabalaHistoricoId;
	}

	public BigDecimal getAjuste() {
		return this.ajuste;
	}

	public void setAjuste(BigDecimal ajuste) {
		this.ajuste = ajuste;
	}

	public int getAnnoDeclaracion() {
		return this.annoDeclaracion;
	}

	public void setAnnoDeclaracion(int annoDeclaracion) {
		this.annoDeclaracion = annoDeclaracion;
	}

	public BigDecimal getAutovaluoAfecta() {
		return this.autovaluoAfecta;
	}

	public void setAutovaluoAfecta(BigDecimal autovaluoAfecta) {
		this.autovaluoAfecta = autovaluoAfecta;
	}

	public BigDecimal getAutovaluoAjustado() {
		return this.autovaluoAjustado;
	}

	public void setAutovaluoAjustado(BigDecimal autovaluoAjustado) {
		this.autovaluoAjustado = autovaluoAjustado;
	}

	public BigDecimal getAutovaluoTotal() {
		return this.autovaluoTotal;
	}

	public void setAutovaluoTotal(BigDecimal autovaluoTotal) {
		this.autovaluoTotal = autovaluoTotal;
	}

	public BigDecimal getBaseAfecta() {
		return this.baseAfecta;
	}

	public void setBaseAfecta(BigDecimal baseAfecta) {
		this.baseAfecta = baseAfecta;
	}

	public BigDecimal getBaseExonerada() {
		return this.baseExonerada;
	}

	public void setBaseExonerada(BigDecimal baseExonerada) {
		this.baseExonerada = baseExonerada;
	}

	public BigDecimal getBaseImponible() {
		return this.baseImponible;
	}

	public void setBaseImponible(BigDecimal baseImponible) {
		this.baseImponible = baseImponible;
	}

	public String getCodigoPostalDistrito() {
		return this.codigoPostalDistrito;
	}

	public void setCodigoPostalDistrito(String codigoPostalDistrito) {
		this.codigoPostalDistrito = codigoPostalDistrito;
	}

	public int getCondicionPropiedadId() {
		return this.condicionPropiedadId;
	}

	public void setCondicionPropiedadId(int condicionPropiedadId) {
		this.condicionPropiedadId = condicionPropiedadId;
	}

	public int getDepartamentoId() {
		return this.departamentoId;
	}

	public void setDepartamentoId(int departamentoId) {
		this.departamentoId = departamentoId;
	}

	public int getDistritoId() {
		return this.distritoId;
	}

	public void setDistritoId(int distritoId) {
		this.distritoId = distritoId;
	}

	public int getDjId() {
		return this.djId;
	}

	public void setDjId(int djId) {
		this.djId = djId;
	}

	public int getDjalcabalaId() {
		return this.djalcabalaId;
	}

	public void setDjalcabalaId(int djalcabalaId) {
		this.djalcabalaId = djalcabalaId;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Timestamp getFechaDeclaracion() {
		return this.fechaDeclaracion;
	}

	public void setFechaDeclaracion(Timestamp fechaDeclaracion) {
		this.fechaDeclaracion = fechaDeclaracion;
	}

	public Timestamp getFechaOperacion() {
		return this.fechaOperacion;
	}

	public void setFechaOperacion(Timestamp fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
	}

	public Timestamp getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getFechaTransferencia() {
		return this.fechaTransferencia;
	}

	public void setFechaTransferencia(String fechaTransferencia) {
		this.fechaTransferencia = fechaTransferencia;
	}

	public String getFlagPrimeraVenta() {
		return this.flagPrimeraVenta;
	}

	public void setFlagPrimeraVenta(String flagPrimeraVenta) {
		this.flagPrimeraVenta = flagPrimeraVenta;
	}

	public String getFormatoFormulario() {
		return this.formatoFormulario;
	}

	public void setFormatoFormulario(String formatoFormulario) {
		this.formatoFormulario = formatoFormulario;
	}

	public String getGlosa() {
		return this.glosa;
	}

	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}

	public String getGlosaSustento() {
		return this.glosaSustento;
	}

	public void setGlosaSustento(String glosaSustento) {
		this.glosaSustento = glosaSustento;
	}

	public BigDecimal getInteres() {
		return this.interes;
	}

	public void setInteres(BigDecimal interes) {
		this.interes = interes;
	}

	public int getMotivoActualizacionId() {
		return this.motivoActualizacionId;
	}

	public void setMotivoActualizacionId(int motivoActualizacionId) {
		this.motivoActualizacionId = motivoActualizacionId;
	}

	public int getNotariaId() {
		return this.notariaId;
	}

	public void setNotariaId(int notariaId) {
		this.notariaId = notariaId;
	}

	public String getNroDocumentoSustento() {
		return this.nroDocumentoSustento;
	}

	public void setNroDocumentoSustento(String nroDocumentoSustento) {
		this.nroDocumentoSustento = nroDocumentoSustento;
	}

	public String getNroFormulario() {
		return this.nroFormulario;
	}

	public void setNroFormulario(String nroFormulario) {
		this.nroFormulario = nroFormulario;
	}

	public String getNroOrdenDj() {
		return this.nroOrdenDj;
	}

	public void setNroOrdenDj(String nroOrdenDj) {
		this.nroOrdenDj = nroOrdenDj;
	}

	public int getPersonaId() {
		return this.personaId;
	}

	public void setPersonaId(int personaId) {
		this.personaId = personaId;
	}

	public BigDecimal getPorcPropiedad() {
		return this.porcPropiedad;
	}

	public void setPorcPropiedad(BigDecimal porcPropiedad) {
		this.porcPropiedad = porcPropiedad;
	}

	public int getPredioAlcabalaId() {
		return this.predioAlcabalaId;
	}

	public void setPredioAlcabalaId(int predioAlcabalaId) {
		this.predioAlcabalaId = predioAlcabalaId;
	}

	public int getProvinciaId() {
		return this.provinciaId;
	}

	public void setProvinciaId(int provinciaId) {
		this.provinciaId = provinciaId;
	}

	public BigDecimal getTasa() {
		return this.tasa;
	}

	public void setTasa(BigDecimal tasa) {
		this.tasa = tasa;
	}

	public BigDecimal getTasaAjuste() {
		return this.tasaAjuste;
	}

	public void setTasaAjuste(BigDecimal tasaAjuste) {
		this.tasaAjuste = tasaAjuste;
	}

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getTieneDeuda() {
		return this.tieneDeuda;
	}

	public void setTieneDeuda(String tieneDeuda) {
		this.tieneDeuda = tieneDeuda;
	}

	public int getTipoDocumentoSustentoId() {
		return this.tipoDocumentoSustentoId;
	}

	public void setTipoDocumentoSustentoId(int tipoDocumentoSustentoId) {
		this.tipoDocumentoSustentoId = tipoDocumentoSustentoId;
	}

	public int getTipoMonedaId() {
		return this.tipoMonedaId;
	}

	public void setTipoMonedaId(int tipoMonedaId) {
		this.tipoMonedaId = tipoMonedaId;
	}

	public int getTipoOperacionId() {
		return this.tipoOperacionId;
	}

	public void setTipoOperacionId(int tipoOperacionId) {
		this.tipoOperacionId = tipoOperacionId;
	}

	public String getTipoPredio() {
		return this.tipoPredio;
	}

	public void setTipoPredio(String tipoPredio) {
		this.tipoPredio = tipoPredio;
	}

	public BigDecimal getTotalDeuda() {
		return this.totalDeuda;
	}

	public void setTotalDeuda(BigDecimal totalDeuda) {
		this.totalDeuda = totalDeuda;
	}

	public String getUbicacionPredio() {
		return this.ubicacionPredio;
	}

	public void setUbicacionPredio(String ubicacionPredio) {
		this.ubicacionPredio = ubicacionPredio;
	}

	public int getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public BigDecimal getValorImpuesto() {
		return this.valorImpuesto;
	}

	public void setValorImpuesto(BigDecimal valorImpuesto) {
		this.valorImpuesto = valorImpuesto;
	}

	public BigDecimal getValorTransferencia() {
		return this.valorTransferencia;
	}

	public void setValorTransferencia(BigDecimal valorTransferencia) {
		this.valorTransferencia = valorTransferencia;
	}

	public BigDecimal getValorTransferenciaSoles() {
		return this.valorTransferenciaSoles;
	}

	public void setValorTransferenciaSoles(BigDecimal valorTransferenciaSoles) {
		this.valorTransferenciaSoles = valorTransferenciaSoles;
	}

	public int getTipoTransferenciaId() {
		return tipoTransferenciaId;
	}

	public void setTipoTransferenciaId(int tipoTransferenciaId) {
		this.tipoTransferenciaId = tipoTransferenciaId;
	}

//	public RaTipoTransferencia getRaTipoTransferencia() {
//		return this.raTipoTransferencia;
//	}
//
//	public void setRaTipoTransferencia(RaTipoTransferencia raTipoTransferencia) {
//		this.raTipoTransferencia = raTipoTransferencia;
//	}
	
}