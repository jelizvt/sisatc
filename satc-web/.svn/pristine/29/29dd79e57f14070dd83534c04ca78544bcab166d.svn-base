package com.sat.sisat.caja.managed;

import java.io.Serializable;

import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.sat.sisat.caja.business.CajaBoRemote;
import com.sat.sisat.common.util.BaseManaged;

@ManagedBean
@SessionScoped
public class PreDestroyCajeroManaged extends BaseManaged implements Serializable{

	private static final long serialVersionUID = -8552316999298479811L;
	
	private int cajeroId;
	
	@EJB
	CajaBoRemote cajaBo;

	public PreDestroyCajeroManaged(){
	}
	
	@PreDestroy
	private void preDestroy(){
		// Eliminar el temporal de deudas, para no bloquear a los otros usuarios.
		cajaBo.eliminarTmpDeuda(cajeroId);
	}

	public int getCajeroId() {
		return cajeroId;
	}

	public void setCajeroId(int cajeroId) {
		this.cajeroId = cajeroId;
	}
}
