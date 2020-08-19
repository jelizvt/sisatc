package com.sat.sisat.vehicular.managed;

import java.util.ArrayList;
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
public class PreliminarManaged extends BaseManaged {

	private static final long serialVersionUID = 8867157461663722611L;
	
	private PreliminarDTO prelim = new PreliminarDTO();
	private List<BuscarPersonaDTO> lstTransferentes = new ArrayList<BuscarPersonaDTO>();
	private List<AnexosDeclaVehicDTO> lstAnexos = new ArrayList<AnexosDeclaVehicDTO>();

	private String anioDecla = DateUtil.obtenerAnioActual();
	private Date fechaActual = DateUtil.getCurrentDateOnly();

	@SuppressWarnings("unchecked")
	public PreliminarManaged() {
		Object obj = getSessionMap().get("preliminarDTO");
		if (obj != null) {
			prelim = (PreliminarDTO) obj;
			anioDecla = prelim.getAnhoAfectacion();
		}
		Object obj2 = getSessionMap().get("lstTransferentes");
		if (obj2 != null) {
			lstTransferentes = (ArrayList<BuscarPersonaDTO>) obj2;
		}
		Object obj3 = getSessionMap().get("lstAnexos");
		if (obj3 != null) {
			lstAnexos = (ArrayList<AnexosDeclaVehicDTO>) obj3;
		}
		getSessionMap().remove("preliminarDTO");
		getSessionMap().remove("lstTransferentes");
		getSessionMap().remove("lstAnexos");
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

	
}
