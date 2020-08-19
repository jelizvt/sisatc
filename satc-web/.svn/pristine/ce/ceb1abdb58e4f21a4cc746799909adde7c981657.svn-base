package com.sat.sisat.vehicular.managed;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.vehicular.dto.AnexosDeclaVehicDTO;
import com.sat.sisat.vehicular.dto.BuscarPersonaDTO;
import com.sat.sisat.vehicular.dto.PreliminarDTO;

@ManagedBean
@ViewScoped
public class PreliminarDescargoManaged extends BaseManaged {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PreliminarDTO prelim = new PreliminarDTO();
	private List<BuscarPersonaDTO> lstTransferentes = new ArrayList<BuscarPersonaDTO>();
	private List<AnexosDeclaVehicDTO> lstAnexos = new ArrayList<AnexosDeclaVehicDTO>();
	private String anioDecla = DateUtil.obtenerAnioActual();
	private Date fechaActual = Calendar.getInstance().getTime();
	private String usuarioActual;

	public PreliminarDescargoManaged() {
		usuarioActual = getSessionManaged().getUsuarioLogIn().getNombreUsuario();
		Object obj = getSessionMap().get("preliminarDescargoDTO");
		if (obj != null) {
			prelim = (PreliminarDTO) obj;
		}
		Object obj2 = getSessionMap().get("lstAdquirientes");
		if (obj2 != null) {
			lstTransferentes = (ArrayList<BuscarPersonaDTO>) obj2;
		}
		Object obj3 = getSessionMap().get("lstAnexosDescargo");
		if (obj3 != null) {
			lstAnexos = (ArrayList<AnexosDeclaVehicDTO>) obj3;
		}
		getSessionMap().remove("preliminarDescargoDTO");
		getSessionMap().remove("lstAdquirientes");
		getSessionMap().remove("lstAnexosDescargo");
	}

	public PreliminarDTO getPrelim() {
		return prelim;
	}

	public void setPrelim(PreliminarDTO prelim) {
		this.prelim = prelim;
	}

	public List<BuscarPersonaDTO> getLstTransferentes() {
		return lstTransferentes;
	}

	public List<AnexosDeclaVehicDTO> getLstAnexos() {
		return lstAnexos;
	}

	public String getAnioDecla() {
		return anioDecla;
	}

	public void setAnioDecla(String anioDecla) {
		this.anioDecla = anioDecla;
	}

	public Date getFechaActual() {
		return fechaActual;
	}

	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
	}

	public String getUsuarioActual() {
		return usuarioActual;
	}

	public void setUsuarioActual(String usuarioActual) {
		this.usuarioActual = usuarioActual;
	}
}
