package com.sat.sisat.alcabala;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class BuscarAlcabalaDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int djAlcabalaId;
	private int djId;
	
	private int personaId;	
	
	private int direccionAlcabalaId;
	private String ubicPredio;	
	private String direcCompleta;
	private String distriDesc;
	private int distriId;
	
	private String tipoPredio;	
	private BigDecimal porcPropiedad;
	private int notariaId;	
	private int tipoTransferenciaId;
	private int condicionPropiedadId;	
	
	private String fechaTransferencia;
	private Timestamp fechaDeclaracion;	
	private int transferenteAlcabalaId;
	
	private String estado;
		
	private int alcabalaSustentoId;	
	
	/**Datos de Calculo*/
	private String flagPrimeraVenta;
	private boolean primeraVenta;
	
	private int tipoMonedaId;
	private BigDecimal tipoCambio;
	
	private BigDecimal autovaluoTotal;
	private String autovaluoAjuste;
	private BigDecimal autovaluoAjusteB;
	private BigDecimal autovaluoAfectaB;
	
	private BigDecimal valorTransferencia;
	
	private String valorUIT;
	private BigDecimal valorUITB;
	
	private String factorAjuste;	
	private String ajuste;
	
	private BigDecimal valorTransferenciaSolesB = new BigDecimal(0);
	
	private String textoIPM1;
	private String textoIPM2;
	
	private String iPMmesAnte;
	private String iPMdicAnte;
	
	private String nroUitDeduc;	
	
	private String mayorValorComparado;
	private String valorDeduccion;
	private String baseImponible;
	private String baseExonerada;
	private String baseAfecta;
	private String tasa;
	private String impuestoPagar;
	private String interesMora;
	private String totalPagar;
	
	private Date fechaVenceD;
	private int predioId;
	
	private String glosa;

	private String usuario;
	private Integer usuarioId;
	
	private String terminal;
			
	public BigDecimal getValorTransferencia() {
		return valorTransferencia;
	}
	public void setValorTransferencia(BigDecimal valorTransferencia) {
		this.valorTransferencia = valorTransferencia;
	}
	public int getDjAlcabalaId() {
		return djAlcabalaId;
	}
	public void setDjAlcabalaId(int djAlcabalaId) {
		this.djAlcabalaId = djAlcabalaId;
	}
	public int getDjId() {
		return djId;
	}
	public void setDjId(int djId) {
		this.djId = djId;
	}
	public int getPersonaId() {
		return personaId;
	}
	public void setPersonaId(int personaId) {
		this.personaId = personaId;
	}
	public Timestamp getFechaDeclaracion() {
		return fechaDeclaracion;
	}
	public void setFechaDeclaracion(Timestamp fechaDeclaracion) {
		this.fechaDeclaracion = fechaDeclaracion;
	}
	public String getFechaTransferencia() {
		return fechaTransferencia;
	}
	public void setFechaTransferencia(String fechaTransferencia) {
		this.fechaTransferencia = fechaTransferencia;
	}
	public BigDecimal getPorcPropiedad() {
		return porcPropiedad;
	}
	public void setPorcPropiedad(BigDecimal porcPropiedad) {
		this.porcPropiedad = porcPropiedad;
	}
	public String getTipoPredio() {
		return tipoPredio;
	}
	public void setTipoPredio(String tipoPredio) {
		this.tipoPredio = tipoPredio;
	}
	public String getUbicPredio() {
		return ubicPredio;
	}
	public void setUbicPredio(String ubicPredio) {
		this.ubicPredio = ubicPredio;
	}
	public String getDirecCompleta() {
		return direcCompleta;
	}
	public void setDirecCompleta(String direcCompleta) {
		this.direcCompleta = direcCompleta;
	}
	public String getDistriDesc() {
		return distriDesc;
	}
	public void setDistriDesc(String distriDesc) {
		this.distriDesc = distriDesc;
	}
	public int getDistriId() {
		return distriId;
	}
	public void setDistriId(int distriId) {
		this.distriId = distriId;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public int getNotariaId() {
		return notariaId;
	}
	public void setNotariaId(int notariaId) {
		this.notariaId = notariaId;
	}
	public int getTipoTransferenciaId() {
		return tipoTransferenciaId;
	}
	public void setTipoTransferenciaId(int tipoTransferenciaId) {
		this.tipoTransferenciaId = tipoTransferenciaId;
	}
	public int getCondicionPropiedadId() {
		return condicionPropiedadId;
	}
	public void setCondicionPropiedadId(int condicionPropiedadId) {
		this.condicionPropiedadId = condicionPropiedadId;
	}
	public String getFlagPrimeraVenta() {
		return flagPrimeraVenta;
	}
	public void setFlagPrimeraVenta(String flagPrimeraVenta) {
		this.flagPrimeraVenta = flagPrimeraVenta;
	}
	public BigDecimal getAutovaluoTotal() {
		return autovaluoTotal;
	}
	public void setAutovaluoTotal(BigDecimal autovaluoTotal) {
		this.autovaluoTotal = autovaluoTotal;
	}
	public int getTipoMonedaId() {
		return tipoMonedaId;
	}
	public void setTipoMonedaId(int tipoMonedaId) {
		this.tipoMonedaId = tipoMonedaId;
	}
	public int getDireccionAlcabalaId() {
		return direccionAlcabalaId;
	}
	public void setDireccionAlcabalaId(int direccionAlcabalaId) {
		this.direccionAlcabalaId = direccionAlcabalaId;
	}
	public int getTransferenteAlcabalaId() {
		return transferenteAlcabalaId;
	}
	public void setTransferenteAlcabalaId(int transferenteAlcabalaId) {
		this.transferenteAlcabalaId = transferenteAlcabalaId;
	}
	public int getAlcabalaSustentoId() {
		return alcabalaSustentoId;
	}
	public void setAlcabalaSustentoId(int alcabalaSustentoId) {
		this.alcabalaSustentoId = alcabalaSustentoId;
	}
	public String getTextoIPM2() {
		return textoIPM2;
	}
	public void setTextoIPM2(String textoIPM2) {
		this.textoIPM2 = textoIPM2;
	}
	public String getTextoIPM1() {
		return textoIPM1;
	}
	public void setTextoIPM1(String textoIPM1) {
		this.textoIPM1 = textoIPM1;
	}
	public String getiPMmesAnte() {
		return iPMmesAnte;
	}
	public void setiPMmesAnte(String iPMmesAnte) {
		this.iPMmesAnte = iPMmesAnte;
	}
	public String getiPMdicAnte() {
		return iPMdicAnte;
	}
	public void setiPMdicAnte(String iPMdicAnte) {
		this.iPMdicAnte = iPMdicAnte;
	}
	public String getFactorAjuste() {
		return factorAjuste;
	}
	public void setFactorAjuste(String factorAjuste) {
		this.factorAjuste = factorAjuste;
	}
	public String getAutovaluoAjuste() {
		return autovaluoAjuste;
	}
	public void setAutovaluoAjuste(String autovaluoAjuste) {
		this.autovaluoAjuste = autovaluoAjuste;
	}
	public String getAjuste() {
		return ajuste;
	}
	public void setAjuste(String ajuste) {
		this.ajuste = ajuste;
	}
	public String getValorUIT() {
		return valorUIT;
	}
	public void setValorUIT(String valorUIT) {
		this.valorUIT = valorUIT;
	}
	public String getNroUitDeduc() {
		return nroUitDeduc;
	}
	public void setNroUitDeduc(String nroUitDeduc) {
		this.nroUitDeduc = nroUitDeduc;
	}
	public BigDecimal getValorTransferenciaSolesB() {
		return valorTransferenciaSolesB;
	}
	public void setValorTransferenciaSolesB(BigDecimal valorTransferenciaSolesB) {
		this.valorTransferenciaSolesB = valorTransferenciaSolesB;
	}
	public String getMayorValorComparado() {
		return mayorValorComparado;
	}
	public void setMayorValorComparado(String mayorValorComparado) {
		this.mayorValorComparado = mayorValorComparado;
	}
	public String getValorDeduccion() {
		return valorDeduccion;
	}
	public void setValorDeduccion(String valorDeduccion) {
		this.valorDeduccion = valorDeduccion;
	}
	public String getBaseImponible() {
		return baseImponible;
	}
	public void setBaseImponible(String baseImponible) {
		this.baseImponible = baseImponible;
	}
	public String getBaseExonerada() {
		return baseExonerada;
	}
	public void setBaseExonerada(String baseExonerada) {
		this.baseExonerada = baseExonerada;
	}
	public String getBaseAfecta() {
		return baseAfecta;
	}
	public void setBaseAfecta(String baseAfecta) {
		this.baseAfecta = baseAfecta;
	}
	public String getTasa() {
		return tasa;
	}
	public void setTasa(String tasa) {
		this.tasa = tasa;
	}
	public String getImpuestoPagar() {
		return impuestoPagar;
	}
	public void setImpuestoPagar(String impuestoPagar) {
		this.impuestoPagar = impuestoPagar;
	}
	public String getInteresMora() {
		return interesMora;
	}
	public void setInteresMora(String interesMora) {
		this.interesMora = interesMora;
	}
	public String getTotalPagar() {
		return totalPagar;
	}
	public void setTotalPagar(String totalPagar) {
		this.totalPagar = totalPagar;
	}
	public BigDecimal getValorUITB() {
		return valorUITB;
	}
	public void setValorUITB(BigDecimal valorUITB) {
		this.valorUITB = valorUITB;
	}
	public BigDecimal getTipoCambio() {
		return tipoCambio;
	}
	public void setTipoCambio(BigDecimal tipoCambio) {
		this.tipoCambio = tipoCambio;
	}
	public BigDecimal getAutovaluoAfectaB() {
		return autovaluoAfectaB;
	}
	public void setAutovaluoAfectaB(BigDecimal autovaluoAfectaB) {
		this.autovaluoAfectaB = autovaluoAfectaB;
	}
	public BigDecimal getAutovaluoAjusteB() {
		return autovaluoAjusteB;
	}
	public void setAutovaluoAjusteB(BigDecimal autovaluoAjusteB) {
		this.autovaluoAjusteB = autovaluoAjusteB;
	}
	public boolean isPrimeraVenta() {
		return primeraVenta;
	}
	public void setPrimeraVenta(boolean primeraVenta) {
		this.primeraVenta = primeraVenta;
	}
	public Date getFechaVenceD() {
		return fechaVenceD;
	}
	public void setFechaVenceD(Date fechaVenceD) {
		this.fechaVenceD = fechaVenceD;
	}
	public int getPredioId() {
		return predioId;
	}
	public void setPredioId(int predioId) {
		this.predioId = predioId;
	}

	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getGlosa() {
		return glosa;
	}
	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}
	public Integer getUsuarioId() {
		return usuarioId;
	}
	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}
	public String getTerminal() {
		return terminal;
	}
	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

}
