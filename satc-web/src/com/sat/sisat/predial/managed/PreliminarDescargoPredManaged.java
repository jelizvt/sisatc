package com.sat.sisat.predial.managed;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.predial.dto.PreliminarDescargoPredDTO;
import com.sat.sisat.vehicular.dto.AnexosDeclaVehicDTO;
import com.sat.sisat.vehicular.dto.BuscarPersonaDTO;

@ManagedBean
@ViewScoped
public class PreliminarDescargoPredManaged extends BaseManaged {

	/**
	 * 
	 */
	private static final long serialVersionUID = 410119742728888175L;
	private PreliminarDescargoPredDTO prelim = new PreliminarDescargoPredDTO();
	private List<BuscarPersonaDTO> lstTransferentes = new ArrayList<BuscarPersonaDTO>();
	private List<AnexosDeclaVehicDTO> lstAnexos = new ArrayList<AnexosDeclaVehicDTO>();
	private String anioDecla = DateUtil.obtenerAnioActual();

	private Date fechaActual;
	private String usuarioActual;
	
	public PreliminarDescargoPredManaged() {
		Object obj = getSessionMap().get("preliminarDescargoPredDTO");
		if (obj != null) {
			prelim = (PreliminarDescargoPredDTO) obj;
		}
		Object obj2 = getSessionMap().get("lstAdquirientesPred");
		if (obj2 != null) {
			lstTransferentes = (ArrayList<BuscarPersonaDTO>) obj2;
		}
		Object obj3 = getSessionMap().get("lstAnexosDescargoPred");
		if (obj3 != null) {
			lstAnexos = (ArrayList<AnexosDeclaVehicDTO>) obj3;
		}
		getSessionMap().remove("preliminarDescargoPredDTO");
		getSessionMap().remove("lstAdquirientesPred");
		getSessionMap().remove("lstAnexosDescargoPred");		

		setFechaActual(DateUtil.getCurrentDate());
		setUsuarioActual(getSessionManaged().getUsuarioLogIn().getNombreUsuario());
	}

	public PreliminarDescargoPredDTO getPrelim() {
		return prelim;
	}

	public void setPrelim(PreliminarDescargoPredDTO prelim) {
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
