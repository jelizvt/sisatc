package com.sat.sisat.reportes.business;

import java.util.ArrayList;
import java.util.Date;

import javax.ejb.Local;

import com.sat.sisat.reportes.dto.AnalisisCuentaDTO;
import com.sat.sisat.reportes.dto.ConSubConDTO;
import com.sat.sisat.reportes.dto.ReporDeudaDTO;
import com.sat.sisat.reportes.dto.ReporRecaudacionDTO;

@Local
public interface ReportesBoLocal {

	public ArrayList<ReporRecaudacionDTO> getRecaudaciones(int anioInicio, int anioFin, int diaInicio, int diaFin, int mesInicio, int mesFin);

	public ArrayList<ReporDeudaDTO> getDeudas(int unidades, int anioFin,  int diaInicio, int diaFin, int mesInicio, int mesFin);
	
	public ArrayList<ConSubConDTO> getConceptoSubconcepto();
	
	public ArrayList<AnalisisCuentaDTO> getAnalisisCuenta(int tipoAna, int tipoConSub, Date fechaIni, Date fechaFin);

}
