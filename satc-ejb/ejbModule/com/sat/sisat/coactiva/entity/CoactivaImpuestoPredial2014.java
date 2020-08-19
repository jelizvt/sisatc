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
 * The persistent class for the COACTIVA_MULTAS_TRANSITO_2014 database table.
 * 
 */
@Entity
@Table(name="COACTIVA_IMPUESTO_PREDIAL_2014")

@NamedQueries({
	@NamedQuery(name="getAllCoactivaImpuestoPredial2014", query="SELECT a FROM CoactivaImpuestoPredial2014 a ORDER BY a.id"),
	@NamedQuery(name="getCoactivaImpuestoPredial2014By", query="SELECT a FROM CoactivaImpuestoPredial2014 a WHERE a.campo8=:p_persona_id OR a.campo9=:p_nro_valor ORDER BY a.id")
})
public class CoactivaImpuestoPredial2014 extends CobranzaCoactiva {
	@Id
	private Integer id;
	private String campo1;
	private String campo2;
	private String campo3;
	private String campo4;//Nro valor
	private String campo5;
	private String campo6;
	private String campo7;
	private Integer campo8;//persona_id
	private String campo9;
	private String campo10;
	private String campo11;
	private String campo12;//insoluto
	private String campo13;
	private String campo14;
	private String campo15;
	private String campo16;//total
	private String campo17;
	private String campo18;
	private String campo19;//FECHA EMISION REC INICIO
	private String campo20;//FECHA NOTIFIC REC INICIO
	private String campo21;//NRO_EXPEDIENTE
	private String campo22;//FECHA DE GENERACION REC 1 
	private String campo23;//FECHA DE PAGO
	private String campo24;//REC M.CAUTELAR GENERACION
	private String campo25;//REC M.CAUTELAR NOTIFICACION
	private String campo26;//COSTAS
	private String campo27;//SUSPENSIÓN
	private String campo28;//OBSERVACION
	private String campo29;//PLACA
	private String campo30;//TIPO DE VALOR:OP...
	
	private String campo31;
	private String campo32;
	private String campo33;//MEDIDA CAUTELAR2
	private String campo34;//MEDIDA CAUTELAR1
	//private String deuda_actual;
//	private String monto_cancelado;
//	private String monto_pendiente;
	
	private String tipo;
	private String insoluto_parques; 
	private String reajuste_parques; 
	private String interes_parques;
	private String emision_parques; 
	private String total_parques;
	private String insoluto_limpieza;
	private String reajuste_limpieza; 
	private String interes_limpieza;
	private String emision_limpieza; 
	private String total_limpieza;
	private String fecha_seguridad; 
	private String  fecha_parques;
	private String  fecha_limpieza; 
	private String  valor_seguridad; 
	private String valor_parques;
    private String  valor_limpieza;
    
	private BigDecimal insoluto_total;
	private BigDecimal emision_total;
	private BigDecimal reajuste_total;
	private BigDecimal interes_total;
	private BigDecimal total;
	
	private String deuda_actual;
	private String estado_actual;
	
	
	private String insoluto;
	private String emision	;
	private String reajuste	;
	private String interes	;
	private String totalSEG	;
	private String insolutoPJ;
	private String emisionPJ;
	private String reajustePJ;
	private String interesPJ;
	private String totalPJ;
	private String insolutoLP;
	private String emisionLP;
	private String reajusteLP;
	private String interesLP;
	private String totalLP;


	@Column(name="usuario_id_upd")
	private String usuarioIdUpd;
	
	@Column(name="terminal_upd")
	private String terminalUpd;
	
	@Column(name="fecha_registro_upd")
	private Timestamp fechaRegistroUpd;
	
//	@Column(name="estado_real")
//	private String estado_real;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCampo1() {
		return campo1;
	}

	public void setCampo1(String campo1) {
		this.campo1 = campo1;
	}

	public String getCampo2() {
		return campo2;
	}

	public void setCampo2(String campo2) {
		this.campo2 = campo2;
	}

	public String getCampo3() {
		return campo3;
	}

	public void setCampo3(String campo3) {
		this.campo3 = campo3;
	}

	public String getCampo4() {
		return campo4;
	}

	public void setCampo4(String campo4) {
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

	public String getCampo7() {
		return campo7;
	}

	public void setCampo7(String campo7) {
		this.campo7 = campo7;
	}

	public String getCampo9() {
		return campo9;
	}

	public void setCampo9(String campo9) {
		this.campo9 = campo9;
	}

	public String getCampo10() {
		return campo10;
	}

	public void setCampo10(String campo10) {
		this.campo10 = campo10;
	}

	public String getCampo11() {
		return campo11;
	}

	public void setCampo11(String campo11) {
		this.campo11 = campo11;
	}

	public String getCampo17() {
		return campo17;
	}

	public void setCampo17(String campo17) {
		this.campo17 = campo17;
	}

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

	
	public String getCampo18() {
		return campo18;
	}

	public void setCampo18(String campo18) {
		this.campo18 = campo18;
	}

	public String getCampo19() {
		return campo19;
	}

	public void setCampo19(String campo19) {
		this.campo19 = campo19;
	}

	public String getCampo20() {
		return campo20;
	}

	public void setCampo20(String campo20) {
		this.campo20 = campo20;
	}

	public Integer getCampo8() {
		return campo8;
	}

	public void setCampo8(Integer campo8) {
		this.campo8 = campo8;
	}

	public String getCampo21() {
		return campo21;
	}

	public void setCampo21(String campo21) {
		this.campo21 = campo21;
	}

	public String getCampo22() {
		return campo22;
	}

	public void setCampo22(String campo22) {
		this.campo22 = campo22;
	}

	public String getCampo23() {
		return campo23;
	}

	public void setCampo23(String campo23) {
		this.campo23 = campo23;
	}

	public String getCampo24() {
		return campo24;
	}

	public void setCampo24(String campo24) {
		this.campo24 = campo24;
	}

	public String getCampo25() {
		return campo25;
	}

	public void setCampo25(String campo25) {
		this.campo25 = campo25;
	}

	public String getCampo26() {
		return campo26;
	}

	public void setCampo26(String campo26) {
		this.campo26 = campo26;
	}

	public String getCampo27() {
		return campo27;
	}

	public void setCampo27(String campo27) {
		this.campo27 = campo27;
	}

	public String getCampo28() {
		return campo28;
	}

	public void setCampo28(String campo28) {
		this.campo28 = campo28;
	}

	public String getCampo29() {
		return campo29;
	}

	public void setCampo29(String campo29) {
		this.campo29 = campo29;
	}

	public String getCampo30() {
		return campo30;
	}

	public void setCampo30(String campo30) {
		this.campo30 = campo30;
	}

	public String getCampo31() {
		return campo31;
	}

	public void setCampo31(String campo31) {
		this.campo31 = campo31;
	}

	public String getCampo32() {
		return campo32;
	}

	public void setCampo32(String campo32) {
		this.campo32 = campo32;
	}

	public String getCampo33() {
		return campo33;
	}

	public void setCampo33(String campo33) {
		this.campo33 = campo33;
	}

	public String getDeuda_actual() {
		return deuda_actual;
	}

	public void setDeuda_actual(String deuda_actual) {
		this.deuda_actual = deuda_actual;
	}

	

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getInsoluto_parques() {
		return insoluto_parques;
	}

	public void setInsoluto_parques(String insoluto_parques) {
		this.insoluto_parques = insoluto_parques;
	}

	public String getReajuste_parques() {
		return reajuste_parques;
	}

	public void setReajuste_parques(String reajuste_parques) {
		this.reajuste_parques = reajuste_parques;
	}

	public String getInteres_parques() {
		return interes_parques;
	}

	public void setInteres_parques(String interes_parques) {
		this.interes_parques = interes_parques;
	}

	public String getEmision_parques() {
		return emision_parques;
	}

	public void setEmision_parques(String emision_parques) {
		this.emision_parques = emision_parques;
	}

	public String getTotal_parques() {
		return total_parques;
	}

	public void setTotal_parques(String total_parques) {
		this.total_parques = total_parques;
	}

	public String getInsoluto_limpieza() {
		return insoluto_limpieza;
	}

	public void setInsoluto_limpieza(String insoluto_limpieza) {
		this.insoluto_limpieza = insoluto_limpieza;
	}

	public String getReajuste_limpieza() {
		return reajuste_limpieza;
	}

	public void setReajuste_limpieza(String reajuste_limpieza) {
		this.reajuste_limpieza = reajuste_limpieza;
	}

	public String getInteres_limpieza() {
		return interes_limpieza;
	}

	public void setInteres_limpieza(String interes_limpieza) {
		this.interes_limpieza = interes_limpieza;
	}

	public String getEmision_limpieza() {
		return emision_limpieza;
	}

	public void setEmision_limpieza(String emision_limpieza) {
		this.emision_limpieza = emision_limpieza;
	}

	public String getTotal_limpieza() {
		return total_limpieza;
	}

	public void setTotal_limpieza(String total_limpieza) {
		this.total_limpieza = total_limpieza;
	}

	public String getFecha_seguridad() {
		return fecha_seguridad;
	}

	public void setFecha_seguridad(String fecha_seguridad) {
		this.fecha_seguridad = fecha_seguridad;
	}

	public String getFecha_parques() {
		return fecha_parques;
	}

	public void setFecha_parques(String fecha_parques) {
		this.fecha_parques = fecha_parques;
	}

	public String getFecha_limpieza() {
		return fecha_limpieza;
	}

	public void setFecha_limpieza(String fecha_limpieza) {
		this.fecha_limpieza = fecha_limpieza;
	}

	public String getValor_seguridad() {
		return valor_seguridad;
	}

	public void setValor_seguridad(String valor_seguridad) {
		this.valor_seguridad = valor_seguridad;
	}

	public String getValor_parques() {
		return valor_parques;
	}

	public void setValor_parques(String valor_parques) {
		this.valor_parques = valor_parques;
	}

	public String getValor_limpieza() {
		return valor_limpieza;
	}

	public void setValor_limpieza(String valor_limpieza) {
		this.valor_limpieza = valor_limpieza;
	}

	public String getCampo34() {
		return campo34;
	}

	public void setCampo34(String campo34) {
		this.campo34 = campo34;
	}

	public BigDecimal getInsoluto_total() {
		return insoluto_total;
	}

	public void setInsoluto_total(BigDecimal insoluto_total) {
		this.insoluto_total = insoluto_total;
	}

	public BigDecimal getEmision_total() {
		return emision_total;
	}

	public void setEmision_total(BigDecimal emision_total) {
		this.emision_total = emision_total;
	}

	public BigDecimal getReajuste_total() {
		return reajuste_total;
	}

	public void setReajuste_total(BigDecimal reajuste_total) {
		this.reajuste_total = reajuste_total;
	}

	public BigDecimal getInteres_total() {
		return interes_total;
	}

	public void setInteres_total(BigDecimal interes_total) {
		this.interes_total = interes_total;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getCampo12() {
		return campo12;
	}

	public void setCampo12(String campo12) {
		this.campo12 = campo12;
	}

	public String getCampo13() {
		return campo13;
	}

	public void setCampo13(String campo13) {
		this.campo13 = campo13;
	}

	public String getCampo14() {
		return campo14;
	}

	public void setCampo14(String campo14) {
		this.campo14 = campo14;
	}

	public String getCampo15() {
		return campo15;
	}

	public void setCampo15(String campo15) {
		this.campo15 = campo15;
	}

	public String getCampo16() {
		return campo16;
	}

	public void setCampo16(String campo16) {
		this.campo16 = campo16;
	}

	public String getEstado_actual() {
		return estado_actual;
	}

	public void setEstado_actual(String estado_actual) {
		this.estado_actual = estado_actual;
	}

	public String getInsoluto() {
		return insoluto;
	}

	public void setInsoluto(String insoluto) {
		this.insoluto = insoluto;
	}

	public String getEmision() {
		return emision;
	}

	public void setEmision(String emision) {
		this.emision = emision;
	}

	public String getReajuste() {
		return reajuste;
	}

	public void setReajuste(String reajuste) {
		this.reajuste = reajuste;
	}

	public String getInteres() {
		return interes;
	}

	public void setInteres(String interes) {
		this.interes = interes;
	}

	public String getTotalSEG() {
		return totalSEG;
	}

	public void setTotalSEG(String totalSEG) {
		this.totalSEG = totalSEG;
	}

	public String getInsolutoPJ() {
		return insolutoPJ;
	}

	public void setInsolutoPJ(String insolutoPJ) {
		this.insolutoPJ = insolutoPJ;
	}

	public String getEmisionPJ() {
		return emisionPJ;
	}

	public void setEmisionPJ(String emisionPJ) {
		this.emisionPJ = emisionPJ;
	}

	public String getReajustePJ() {
		return reajustePJ;
	}

	public void setReajustePJ(String reajustePJ) {
		this.reajustePJ = reajustePJ;
	}

	public String getInteresPJ() {
		return interesPJ;
	}

	public void setInteresPJ(String interesPJ) {
		this.interesPJ = interesPJ;
	}

	public String getTotalPJ() {
		return totalPJ;
	}

	public void setTotalPJ(String totalPJ) {
		this.totalPJ = totalPJ;
	}

	public String getInsolutoLP() {
		return insolutoLP;
	}

	public void setInsolutoLP(String insolutoLP) {
		this.insolutoLP = insolutoLP;
	}

	public String getEmisionLP() {
		return emisionLP;
	}

	public void setEmisionLP(String emisionLP) {
		this.emisionLP = emisionLP;
	}

	public String getReajusteLP() {
		return reajusteLP;
	}

	public void setReajusteLP(String reajusteLP) {
		this.reajusteLP = reajusteLP;
	}

	public String getInteresLP() {
		return interesLP;
	}

	public void setInteresLP(String interesLP) {
		this.interesLP = interesLP;
	}

	public String getTotalLP() {
		return totalLP;
	}

	public void setTotalLP(String totalLP) {
		this.totalLP = totalLP;
	}
	


}