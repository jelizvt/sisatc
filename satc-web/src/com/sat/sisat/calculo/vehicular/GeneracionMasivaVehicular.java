package com.sat.sisat.calculo.vehicular;

import java.net.InetAddress;
import java.util.ArrayList;

import com.sat.sisat.determinacion.vehicular.business.DeterminacionVehicularBoRemote;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.persistence.entity.RpDjconstruccion;

public class GeneracionMasivaVehicular {

	private DeterminacionVehicularBoRemote determinacionVehicularBo;

	private Integer usuarioId;
	private String terminal;
	private Integer annioDj;

	public void init() {
		try {
			InetAddress owner = InetAddress.getLocalHost();
			setTerminal(owner.getHostAddress());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public GeneracionMasivaVehicular(DeterminacionVehicularBoRemote determinacionVehicularBo) {
		super();
		this.determinacionVehicularBo = determinacionVehicularBo;
		init();
	}

	public void run() {

		//duplicarDjVehicular();//Comentado en FEB-2017.

	}

	public void duplicarDjVehicular(Integer inicio, Integer fin) {

		try {
//			System.out.println("Iniciando el proceso de Generacion masiva Vehicular");

			getDeterminacionVehicularBo().duplicarDj(annioDj, usuarioId, terminal,inicio,fin);
		} catch (SisatException e) {
			e.printStackTrace();
		}

	}

	public Integer getNewConstruccionId(Integer construccionId, ArrayList<RpDjconstruccion> listaConstruccion) {
		Integer NewConstruccionId = 0;
		for (int i = 0; i < listaConstruccion.size(); i++) {
			if (construccionId.equals(listaConstruccion.get(i).getConstruccionId())) {
				NewConstruccionId = listaConstruccion.get(i).getNewConstruccionId();
			}
		}
		return NewConstruccionId;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public Integer getAnnioDj() {
		return annioDj;
	}

	public void setAnnioDj(Integer annioDj) {
		this.annioDj = annioDj;
	}

	public DeterminacionVehicularBoRemote getDeterminacionVehicularBo() {
		return determinacionVehicularBo;
	}

	public void setDeterminacionVehicularBo(DeterminacionVehicularBoRemote determinacionVehicularBo) {
		this.determinacionVehicularBo = determinacionVehicularBo;
	}
}
