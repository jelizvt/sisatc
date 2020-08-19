package com.sat.sisat.caja.vo;

import java.io.Serializable;
import java.util.List;

import com.sat.sisat.caja.dto.CjPersona;

public class CjAdministradoVo implements Serializable {

	private static final long serialVersionUID = 5930265155254380146L;

	private String codigoAdministrado;
	private String nombreAdministrado;
	private String tipoDocIden;
	private String numDocIden;
	private List<CjPersona> papeletas;

	public CjAdministradoVo() {
	}

	public CjAdministradoVo(String codigoAdministrado, String nombreAdministrado,
			String tipoDocIden, String numDocIden) {
		this.codigoAdministrado = codigoAdministrado;
		this.nombreAdministrado = nombreAdministrado;
		this.tipoDocIden = tipoDocIden;
		this.numDocIden = numDocIden;
	}

	public String getCodigoAdministrado() {
		return codigoAdministrado;
	}

	public void setCodigoAdministrado(String codigoAdministrado) {
		this.codigoAdministrado = codigoAdministrado;
	}

	public String getNombreAdministrado() {
		return nombreAdministrado;
	}

	public void setNombreAdministrado(String nombreAdministrado) {
		this.nombreAdministrado = nombreAdministrado;
	}

	public String getTipoDocIden() {
		return tipoDocIden;
	}

	public void setTipoDocIden(String tipoDocIden) {
		this.tipoDocIden = tipoDocIden;
	}

	public String getNumDocIden() {
		return numDocIden;
	}

	public void setNumDocIden(String numDocIden) {
		this.numDocIden = numDocIden;
	}

	public List<CjPersona> getPapeletas() {
		return papeletas;
	}

	public void setPapeletas(List<CjPersona> papeletas) {
		this.papeletas = papeletas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((codigoAdministrado == null) ? 0 : codigoAdministrado
						.hashCode());
		result = prime
				* result
				+ ((nombreAdministrado == null) ? 0 : nombreAdministrado
						.hashCode());
		result = prime * result
				+ ((numDocIden == null) ? 0 : numDocIden.hashCode());
		result = prime * result
				+ ((papeletas == null) ? 0 : papeletas.hashCode());
		result = prime * result
				+ ((tipoDocIden == null) ? 0 : tipoDocIden.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CjAdministradoVo other = (CjAdministradoVo) obj;
		if (codigoAdministrado == null) {
			if (other.codigoAdministrado != null)
				return false;
		} else if (!codigoAdministrado.equals(other.codigoAdministrado))
			return false;
		if (nombreAdministrado == null) {
			if (other.nombreAdministrado != null)
				return false;
		} else if (!nombreAdministrado.equals(other.nombreAdministrado))
			return false;
		if (numDocIden == null) {
			if (other.numDocIden != null)
				return false;
		} else if (!numDocIden.equals(other.numDocIden))
			return false;
		if (papeletas == null) {
			if (other.papeletas != null)
				return false;
		} else if (!papeletas.equals(other.papeletas))
			return false;
		if (tipoDocIden == null) {
			if (other.tipoDocIden != null)
				return false;
		} else if (!tipoDocIden.equals(other.tipoDocIden))
			return false;
		return true;
	}
}
