package com.sat.sisat.alcabala.managed;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sat.sisat.alcabala.ImprimirAlcabalaDTO;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.persistence.entity.MpRelacionado;
import com.sat.sisat.persistence.entity.MpTipoRelacion;
import com.sat.sisat.persistence.entity.RaDjalcabala;
import com.sat.sisat.persona.business.PersonaBoRemote;
import com.sat.sisat.predial.business.RegistroPrediosBoRemote;
import com.sat.sisat.predial.dto.FindMpPersona;
import com.sat.sisat.predial.dto.RelacionadosDTO;
import com.sat.sisat.vehicular.dto.AnexosDeclaVehicDTO;
import com.sat.sisat.vehicular.dto.BuscarPersonaDTO;

@ManagedBean
@ViewScoped
public class ImprimirAlcabalaManaged extends BaseManaged{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 219330499147173043L;
	
	@EJB
	RegistroPrediosBoRemote registroPrediosBo;

	private String djAlcabala;
	
	private List<AnexosDeclaVehicDTO> lstAnexosMuestra = new ArrayList<AnexosDeclaVehicDTO>();

	private ImprimirAlcabalaDTO impAlcaDto= new ImprimirAlcabalaDTO();
	
	private List<BuscarPersonaDTO> lstTransferentes = new ArrayList<BuscarPersonaDTO>();
	
	private Date fechaActual;
	private String usuarioActual;
	private ArrayList<RelacionadosDTO> listRelacionados;
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init(){
		try {
						
			setImpAlcaDto((ImprimirAlcabalaDTO)getSessionMap().get("datosRaDjAlcabalaSesion"));
			setLstTransferentes((List<BuscarPersonaDTO>)getSessionMap().get("lstTransferentesSesion"));
			setLstAnexosMuestra((List<AnexosDeclaVehicDTO>)getSessionMap().get("lstDocAnexoSesion"));
			
			//FECHA Y USUARIO  ACTUAL
			setFechaActual(DateUtil.getCurrentDate());
			setUsuarioActual(getSessionManaged().getUsuarioLogIn().getNombreUsuario());
			
			//Lista de Relacionados
			loadRelacionados();


		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);		
		}
	}
	
	public void loadRelacionados(){
		try{			
			Integer personaID = Integer.parseInt(String.valueOf(getSessionManaged().getContribuyente().getPersonaId()));
			listRelacionados = registroPrediosBo.getAllRelacionadoDTO(personaID);			
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);		
		}
	}

	public String getDjAlcabala() {
		return djAlcabala;
	}

	public void setDjAlcabala(String djAlcabala) {
		this.djAlcabala = djAlcabala;
	}

	public ImprimirAlcabalaDTO getImpAlcaDto() {
		return impAlcaDto;
	}

	public void setImpAlcaDto(ImprimirAlcabalaDTO impAlcaDto) {
		this.impAlcaDto = impAlcaDto;
	}

	public List<BuscarPersonaDTO> getLstTransferentes() {
		return lstTransferentes;
	}

	public void setLstTransferentes(List<BuscarPersonaDTO> lstTransferentes) {
		this.lstTransferentes = lstTransferentes;
	}

	public List<AnexosDeclaVehicDTO> getLstAnexosMuestra() {
		return lstAnexosMuestra;
	}

	public void setLstAnexosMuestra(List<AnexosDeclaVehicDTO> lstAnexosMuestra) {
		this.lstAnexosMuestra = lstAnexosMuestra;
	}
	public Date getFechaActual() {
		return fechaActual;
	}
	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
	}

	public ArrayList<RelacionadosDTO> getListRelacionados() {
		return listRelacionados;
	}

	public void setListRelacionados(ArrayList<RelacionadosDTO> listRelacionados) {
		this.listRelacionados = listRelacionados;
	}

	public String getUsuarioActual() {
		return usuarioActual;
	}

	public void setUsuarioActual(String usuarioActual) {
		this.usuarioActual = usuarioActual;
	}


}
