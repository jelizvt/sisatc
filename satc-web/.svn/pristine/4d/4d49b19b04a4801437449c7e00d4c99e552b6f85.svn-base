package com.sat.sisat.predial.managed;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.predial.business.CalculoPredialBoRemote;
import com.sat.sisat.predial.dto.DeterminacionArbitriosDTO;
import com.sat.sisat.predial.dto.DtDeterminacionResArbDTO;

@ManagedBean
@ViewScoped
public class DetalleArbitriosManaged extends BaseManaged {

	private DeterminacionArbitriosDTO records;
	private String codigoPredio;

	@EJB
	CalculoPredialBoRemote calculoPredialBo;
	
	public DetalleArbitriosManaged() {

	}

	@PostConstruct
	public void init(){
		try{
			DtDeterminacionResArbDTO determinacion=(DtDeterminacionResArbDTO)getSessionMap().get("dtDeterminacionResArbDTO");
			if(determinacion!=null){
				records=calculoPredialBo.getDeterminacionArbitrios(determinacion.getDjId());
			}
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public String salir() {
		return sendRedirectPrincipal();
	}

	public DeterminacionArbitriosDTO getRecords() {
		return records;
	}

	public void setRecords(DeterminacionArbitriosDTO records) {
		this.records = records;
	}

	public String getCodigoPredio() {
		return codigoPredio;
	}

	public void setCodigoPredio(String codigoPredio) {
		this.codigoPredio = codigoPredio;
	}
}
