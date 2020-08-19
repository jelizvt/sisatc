package com.sat.sisat.coactivav2.managed;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sat.sisat.cobranzacoactiva.business.CobranzaCoactivaBoRemote;
import com.sat.sisat.cobranzacoactiva.dto.CarteraExigibilidad;
import com.sat.sisat.cobranzacoactiva.dto.FindParameterDto;
import com.sat.sisat.common.dto.GenericDTO;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.FacesUtil;

@ManagedBean
@ViewScoped
public class ConsultaCarteraMedidaCautelarManaged extends BaseManaged {
	@EJB
	CobranzaCoactivaBoRemote cobranzaCoactivaBo;
	
	private String nroCartera;
	private Integer coactivoId;
	private Integer situacionCarteraId;

	private List<GenericDTO> listaEjecutor = new ArrayList<GenericDTO>();
	private List<GenericDTO> listaSituacion = new ArrayList<GenericDTO>();
	
	private List<CarteraExigibilidad> records = new ArrayList<CarteraExigibilidad>();
	
	private CarteraExigibilidad selCarteraExigibilidad = new CarteraExigibilidad();

	private Integer coactivoAsignarId;
	
	private Integer materiaId;
	
	@PostConstruct
	public void init() throws Exception {
		try {
			if(getSessionManaged().getCoPerfil().getMateriaId()>0){
				materiaId=getSessionManaged().getCoPerfil().getMateriaId();
			}
			
			listaSituacion=cobranzaCoactivaBo.listarSituacionCartera();
			listaEjecutor=cobranzaCoactivaBo.listarEjecutorCoactivo();
			if(getParameterSession()){
				buscar();
			}
			
			if(getSessionManaged().getCoPerfil().getCargoId()==1){
				coactivoId=getSessionManaged().getUsuarioLogIn().getUsuarioId();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void buscar(){
		try{
			setParameterSession();
			records=cobranzaCoactivaBo.buscarCarteraMedidaCautelar(nroCartera, coactivoId, situacionCarteraId);	
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void setParameterSession(){
		if(nroCartera!=null&&nroCartera.trim().length()>0){
			FindParameterDto findParameter=new FindParameterDto("nroCartera",nroCartera); 
			getSessionMap().put("findParameter", findParameter);
		}else if(coactivoId!=null&&coactivoId>0){
			FindParameterDto findParameter=new FindParameterDto("coactivoId",coactivoId); 
			getSessionMap().put("findParameter", findParameter);
		}else if(situacionCarteraId!=null&&situacionCarteraId>0){
			FindParameterDto findParameter=new FindParameterDto("situacionCarteraId",situacionCarteraId); 
			getSessionMap().put("findParameter", findParameter);
		}
	}
	
	public boolean getParameterSession(){
		FindParameterDto findParameter=(FindParameterDto)getSessionMap().get("findParameter");
		if(findParameter!=null){
			if(findParameter.getParameterName().equals("nroCartera")){
				nroCartera=String.valueOf(findParameter.getParameterValue());
				return true;	
			}else if(findParameter.getParameterName().equals("coactivoId")){
				coactivoId=Integer.valueOf(findParameter.getParameterValue());
				return true;
			}else if(findParameter.getParameterName().equals("situacionCarteraId")){
				situacionCarteraId=Integer.valueOf(findParameter.getParameterValue());
				return true;
			}
		}
		return false;
	}
	
	public String gestionCartera()throws Exception{
		try{
			FacesUtil.closeSession("acumulaCarteraMedidaCautelarManaged");
			getSessionMap().put("carteraMedidaCautelar", selCarteraExigibilidad);
		}catch(Exception e){
			e.printStackTrace();
		}
		return sendRedirectPrincipal();
	}
	public String nuevaCartera(){
		try{
			selCarteraExigibilidad=new CarteraExigibilidad();
			selCarteraExigibilidad.setCarteraId(Constante.RESULT_PENDING);
			getSessionMap().put("carteraMedidaCautelar", selCarteraExigibilidad);
		}catch(Exception e){
			e.printStackTrace();
		}
		return sendRedirectPrincipal();
	}
	
	public void registraAsignacion(){
		try{
			if(coactivoAsignarId!=null&&coactivoAsignarId>0){
				cobranzaCoactivaBo.reasignarCartera(selCarteraExigibilidad.getCarteraId(), selCarteraExigibilidad.getUsuarioCoactivoId(), coactivoAsignarId, getSessionManaged().getUsuarioLogIn().getUsuarioId(), getSessionManaged().getTerminalLogIn());
			}				
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String getNroCartera() {
		return nroCartera;
	}

	public void setNroCartera(String nroCartera) {
		this.nroCartera = nroCartera;
	}

	public List<GenericDTO> getListaEjecutor() {
		return listaEjecutor;
	}

	public void setListaEjecutor(List<GenericDTO> listaEjecutor) {
		this.listaEjecutor = listaEjecutor;
	}

	public List<GenericDTO> getListaSituacion() {
		return listaSituacion;
	}

	public void setListaSituacion(List<GenericDTO> listaSituacion) {
		this.listaSituacion = listaSituacion;
	}

	public Integer getCoactivoId() {
		return coactivoId;
	}

	public void setCoactivoId(Integer coactivoId) {
		this.coactivoId = coactivoId;
	}

	public List<CarteraExigibilidad> getRecords() {
		return records;
	}

	public void setRecords(List<CarteraExigibilidad> records) {
		this.records = records;
	}

	public CarteraExigibilidad getSelCarteraExigibilidad() {
		return selCarteraExigibilidad;
	}

	public void setSelCarteraExigibilidad(CarteraExigibilidad selCarteraExigibilidad) {
		this.selCarteraExigibilidad = selCarteraExigibilidad;
	}

	public Integer getSituacionCarteraId() {
		return situacionCarteraId;
	}

	public void setSituacionCarteraId(Integer situacionCarteraId) {
		this.situacionCarteraId = situacionCarteraId;
	}
	
	public Integer getCoactivoAsignarId() {
		return coactivoAsignarId;
	}

	public void setCoactivoAsignarId(Integer coactivoAsignarId) {
		this.coactivoAsignarId = coactivoAsignarId;
	}

	public Integer getMateriaId() {
		return materiaId;
	}

	public void setMateriaId(Integer materiaId) {
		this.materiaId = materiaId;
	}
	
}
