package com.sat.sisat.caja.managed;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sat.sisat.caja.business.CajaBoRemote;
import com.sat.sisat.caja.dto.ReporteCuentaDTO;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.exception.SisatException;

@ManagedBean
@ViewScoped
public class ReporteCuentaManaged extends BaseManaged {

	private static final long serialVersionUID = -3882058657072954285L;
	
	
	private List<ReporteCuentaDTO> listaCuentaDTOs;
	
	@EJB
	CajaBoRemote cajaBo;
	
	private Date fechaInicio;
	
	private Date fechaFin; 
	
	@PostConstruct
	public void init() {
		
		try {
			
			fechaInicio = (Date) getSessionMap().get("fechaInicio");
			fechaFin = (Date) getSessionMap().get("fechaFin");
			
			
			listaCuentaDTOs = cajaBo.obtenerReporteCuentas(fechaInicio, DateUtil.moverHoraAlFinalDelDia(fechaFin));
		} catch (SisatException e) {			
			WebMessages.messageError(e.getMessage());
		}
	}

	
	public List<ReporteCuentaDTO> getListaCuentaDTOs() {
		return listaCuentaDTOs;
	}

	public void setListaCuentaDTOs(List<ReporteCuentaDTO> listaCuentaDTOs) {
		this.listaCuentaDTOs = listaCuentaDTOs;
	}


	public Date getFechaInicio() {
		return fechaInicio;
	}


	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}


	public Date getFechaFin() {
		return fechaFin;
	}


	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	public Date getFechaEmision(){
		
		return Calendar.getInstance().getTime();
	}
}
