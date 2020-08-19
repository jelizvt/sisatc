package com.sat.sisat.coactiva.entity;

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
@Table(name="COACTIVA_IMPUESTO_PREDIAL_2013")
@NamedQueries({
	@NamedQuery(name="getAllCoactivaImpuestoPredial2013", query="SELECT a FROM CoactivaImpuestoPredial2013 a ORDER BY a.id"),
	@NamedQuery(name="getCoactivaImpuestoPredial2013By", query="SELECT a FROM CoactivaImpuestoPredial2013 a WHERE a.campo3=:p_persona_id OR a.campo11=:p_nro_valor ORDER BY a.id")
})

public class CoactivaImpuestoPredial2013 extends CobranzaCoactiva {
	@Id
	private Integer id;
	
	private Integer campo1;
	private String campo2;
	private Integer campo3;//Persona_id
	private String campo4;
	private String campo5;
	private String campo6;
	private String campo7;
	private String campo8;
	private String campo9;
	private String campo10;
	private String campo11;//Nro_valor
	private String campo12;
	private String campo13;
	private String campo14;
	private String campo15;
	private String campo16;
	private String campo17;
	private String campo18;
	private String campo19;
	private String campo20;
	private String campo21;
	private String campo22;
	private String campo23;
	private String campo24;
	private String campo25;
	private String campo26;
	private String campo27;
	private String campo28;
	private String campo29;
	private String campo30;
	private String campo31;
	private String campo32;
	private String campo33;
	private String campo34;
	private String campo35;
	private String campo36;
	private String campo37;
	private String deudaTotal_actual;
	private String deudaCancelada_actual;
	private String deudaPendiente_actual;
	
	private Date fecha_pago;
	private String monto_pago;
	
	private String deuda_actual;
	private String estado_actual;
	
	@Column(name="usuario_id_upd")
	private String usuarioIdUpd;
	
	@Column(name="terminal_upd")
	private String terminalUpd;
	
	@Column(name="fecha_registro_upd")
	private Timestamp fechaRegistroUpd;
	
	@Column(name="estado_color")
	private String estadocolor;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCampo1() {
		return campo1;
	}

	public void setCampo1(Integer campo1) {
		this.campo1 = campo1;
	}

	public String getCampo2() {
		return campo2;
	}

	public void setCampo2(String campo2) {
		this.campo2 = campo2;
	}

	public Integer getCampo3() {
		return campo3;
	}

	public void setCampo3(Integer campo3) {
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

	public String getCampo8() {
		return campo8;
	}

	public void setCampo8(String campo8) {
		this.campo8 = campo8;
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

	public String getCampo17() {
		return campo17;
	}

	public void setCampo17(String campo17) {
		this.campo17 = campo17;
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

	public String getCampo34() {
		return campo34;
	}

	public void setCampo34(String campo34) {
		this.campo34 = campo34;
	}

	public String getCampo35() {
		return campo35;
	}

	public void setCampo35(String campo35) {
		this.campo35 = campo35;
	}

	public String getCampo36() {
		return campo36;
	}

	public void setCampo36(String campo36) {
		this.campo36 = campo36;
	}

	public String getCampo37() {
		return campo37;
	}

	public void setCampo37(String campo37) {
		this.campo37 = campo37;
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

	public String getEstadocolor() {
		return estadocolor;
	}

	public void setEstadocolor(String estadocolor) {
		this.estadocolor = estadocolor;
	}

	public String getDeudaTotal_actual() {
		return deudaTotal_actual;
	}

	public String getDeudaCancelada_actual() {
		return deudaCancelada_actual;
	}

	public String getDeudaPendiente_actual() {
		return deudaPendiente_actual;
	}

	public void setDeudaTotal_actual(String deudaTotal_actual) {
		this.deudaTotal_actual = deudaTotal_actual;
	}

	public void setDeudaCancelada_actual(String deudaCancelada_actual) {
		this.deudaCancelada_actual = deudaCancelada_actual;
	}

	public void setDeudaPendiente_actual(String deudaPendiente_actual) {
		this.deudaPendiente_actual = deudaPendiente_actual;
	}

	public Date getFecha_pago() {
		return fecha_pago;
	}

	public void setFecha_pago(Date fecha_pago) {
		this.fecha_pago = fecha_pago;
	}
	
	public String getMonto_pago() {
		return monto_pago;
	}

	public void setMonto_pago(String monto_pago) {
		this.monto_pago = monto_pago;
	}

	public String getDeuda_actual() {
		return deuda_actual;
	}

	public void setDeuda_actual(String deuda_actual) {
		this.deuda_actual = deuda_actual;
	}

	public String getEstado_actual() {
		return estado_actual;
	}

	public void setEstado_actual(String estado_actual) {
		this.estado_actual = estado_actual;
	}
	
}