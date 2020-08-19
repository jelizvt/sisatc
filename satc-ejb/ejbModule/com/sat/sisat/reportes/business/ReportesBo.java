package com.sat.sisat.reportes.business;

import java.util.ArrayList;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

import com.sat.sisat.predial.business.BaseBusiness;
//import com.sat.sisat.predial.dao.PredialBusinessDao;
import com.sat.sisat.reportes.dao.RecaudacionesBusinessDao;
import com.sat.sisat.reportes.dto.AnalisisCuentaDTO;
import com.sat.sisat.reportes.dto.ConSubConDTO;
import com.sat.sisat.reportes.dto.ReporDeudaDTO;
import com.sat.sisat.reportes.dto.ReporRecaudacionDTO;

@Stateless
public class ReportesBo extends BaseBusiness implements ReportesBoRemote {

	private static final long serialVersionUID = 1L;
	private RecaudacionesBusinessDao service;
	
	public RecaudacionesBusinessDao getService() {
		return service;
	}


	@PostConstruct
    public void initialize(){
    	this.service=new RecaudacionesBusinessDao();
    	setDataManager(this.service);
    }
    
	
	@Override
	public ArrayList<ReporRecaudacionDTO> getRecaudaciones(int anioInicio, int anioFin, int diaInicio, int diaFin, int mesInicio, int mesFin) {
		return getService().getRecaudaciones(anioInicio,anioFin, diaInicio, diaFin, mesInicio, mesFin);
	}


	@Override
	public ArrayList<ReporDeudaDTO> getDeudas(int unidades, int anioFin,
			int diaInicio, int diaFin, int mesInicio, int mesFin) {
		return getService().getDeudas(unidades, anioFin, diaInicio, diaFin, mesInicio, mesFin);
	}


	@Override
	public ArrayList<ConSubConDTO> getConceptoSubconcepto() {
		return getService().getConceptoSubconcepto();
	}


	@Override
	public ArrayList<AnalisisCuentaDTO> getAnalisisCuenta(int tipoAna, int tipoConSub, Date fechaIni, Date fechaFin) {
		
		return getService().getAnalisisCuenta(tipoAna, tipoConSub, fechaIni, fechaFin);
	}

}
