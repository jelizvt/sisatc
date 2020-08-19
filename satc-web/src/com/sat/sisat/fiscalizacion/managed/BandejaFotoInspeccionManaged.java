package com.sat.sisat.fiscalizacion.managed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.richfaces.component.html.HtmlComboBox;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.controlycobranza.dto.MpFiscalizadorDto;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.fiscalizacion.business.FiscalizacionBoRemote;
import com.sat.sisat.menus.dto.SimpleMenuDTO;
import com.sat.sisat.persistence.entity.GnVia;
import com.sat.sisat.persona.dto.ValidaUbicacionDTO;
import com.sat.sisat.predial.business.RegistroPrediosBoRemote;
import com.sat.sisat.predial.dto.FotoPredioConstruccionesDTO;
import com.sat.sisat.predial.dto.FotoPredioDTO;
import com.sat.sisat.predial.dto.FotoPredioInspeccionDTO;
import com.sat.sisat.predial.dto.FotoPredioInstalacionesDTO;

@ManagedBean
@ViewScoped
public class BandejaFotoInspeccionManaged extends BaseManaged{
	
	@EJB
	RegistroPrediosBoRemote servicio;
	
	@EJB
	FiscalizacionBoRemote ficalizacionBo;
	
	private List<FotoPredioInspeccionDTO> listFotoInspeccion;
	
	private Integer personaId;
	private Integer predioId;
	
	// FOTO PREDIO
		private String imgpredio;
		private int posicionImg;
		private int fotoPredioId;
		private String fotoContribuyente;
		private String fotoDireccion;
		private List<FotoPredioDTO> listFotos;
		private List<FotoPredioConstruccionesDTO> listConstruccionFotos;
		private List<FotoPredioInstalacionesDTO> listInstalacionesFotos;
	// ==============
		
		
		private List<MpFiscalizadorDto> listarFiscalizadores = new ArrayList<MpFiscalizadorDto>();
		
		private List<SelectItem> listSelectItemFisca = new ArrayList<SelectItem>();
		private HashMap<String, Integer> mapFisca = new HashMap<String, Integer>();
		
		private int inspectorId;
		private int fotoInspeccionId;
	
		private String observacionCierre;
		private Boolean selecteFiscalizado;

	@PostConstruct
	public void init(){
		this.selecteFiscalizado = true;
		listFotoInspeccion  = new ArrayList<FotoPredioInspeccionDTO>();
		
		this.buscar();
	}
	
	
	public void buscar() {
		try {
			System.out.println(personaId);
			
			if(personaId == null) {
				personaId = 0;
			}
			
			if(predioId == null) {
				predioId = 0;
			}
			
			listFotoInspeccion = servicio.getFotoInspeccion(4,personaId,predioId,0,0,0,"",0,"");
			
			if(personaId == 0) {
				personaId = null;
			}
			if(predioId == 0) {
				predioId = null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void limpiar() {
		personaId = null;
		predioId = null;
		this.buscar();
	}
	
	public void getFotos(int predioId, String contribuyente, String direccion) {
		fotoPredioId = predioId;
		fotoContribuyente = contribuyente;
		fotoDireccion = direccion;
		
		try {
			listFotos=servicio.getFotoPredio(predioId);
			
			int catidad = listFotos.size();
			
			if (catidad <= 0) {
				addErrorMessage("El predio no cuenta con fotos registradas.");
				return;
			}else {
				listConstruccionFotos = servicio.getFotoPredioConstrucciones(predioId);
				listInstalacionesFotos = servicio.getFotoPredioInstalaciones(predioId);
				
				posicionImg = 0;
				this.imgpredio  = listFotos.get(posicionImg).getUrl();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void regresarImg() {
		int catidad = listFotos.size() - 1;
		
		if(this.posicionImg > 0) {
			this.posicionImg--;
			this.imgpredio  = listFotos.get(posicionImg).getUrl();
		}else if(this.posicionImg == 0){
			this.posicionImg= catidad;
			this.imgpredio  = listFotos.get(posicionImg).getUrl();
		}
		
	}

	public void adelantarImg() {
		int catidad = listFotos.size() - 1;
	
		if(this.posicionImg < catidad) {
			this.posicionImg++;
			this.imgpredio  = listFotos.get(posicionImg).getUrl();
		}else if(this.posicionImg == catidad){
			this.posicionImg= 0;
			this.imgpredio  = listFotos.get(posicionImg).getUrl();
		}
		
	}
	
	public void cargarInspectores(int fInspeccionId) {
		this.fotoInspeccionId = fInspeccionId;
		
		try {
			listarFiscalizadores=ficalizacionBo.getAllInspectores();
			
			listSelectItemFisca = null;
			listSelectItemFisca = new ArrayList<SelectItem>();
			
			
			mapFisca=null;
			mapFisca= new HashMap<String, Integer>();
			mapFisca.put("Todos", 0);
			
			for (MpFiscalizadorDto fisca : listarFiscalizadores) {
				listSelectItemFisca.add(new SelectItem(fisca.getNombresApellidos(), String.valueOf(fisca.getIdfiscalizador())));
				mapFisca.put(fisca.getNombresApellidos(), fisca.getIdfiscalizador());
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadInspectorById(ValueChangeEvent event) {
		String key = (String) event.getNewValue();
		Integer id = mapFisca.get(key);
		
		if (id == null) {
			WebMessages.messageError("El inspector ingresado es incorrecto");
			this.inspectorId = -1 ;
			return;
		}
		
		this.inspectorId = id ;
		System.out.println(id);
	}
	
	public void asignarInspector() {
		try {
				
			// opcion,personaId,predioId,fotoInspecionId,inspectorId,flagValida,glosa,usuarioId,terminal
			
			servicio.registrarFotoInspeccion(2,0,0,fotoInspeccionId,this.inspectorId,0,"",getSessionManaged().getUsuarioLogIn().getUsuarioId(),getSessionManaged().getTerminalLogIn());
			
			this.buscar();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void openModalCerrarInspeccion(int fInspeccionId) {
		this.fotoInspeccionId = fInspeccionId;
	}

	public void cerrarInspeccion() {
		System.out.println(this.observacionCierre);
		System.out.println(this.selecteFiscalizado);
		System.out.println(this.fotoInspeccionId);
		
		try {
			
			int flag = 1;
			if(!selecteFiscalizado) {
				flag = 0;
			}
			// opcion,personaId,predioId,fotoInspecionId,inspectorId,flagValida,glosa,usuarioId,terminal
			servicio.registrarFotoInspeccion(3,0,0,fotoInspeccionId,0,flag,observacionCierre,getSessionManaged().getUsuarioLogIn().getUsuarioId(),getSessionManaged().getTerminalLogIn());
			this.buscar();
			
		} catch (SisatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public Integer getPersonaId() {
		return personaId;
	}
	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}
	public Integer getPredioId() {
		return predioId;
	}
	public void setPredioId(Integer predioId) {
		this.predioId = predioId;
	}
	public List<FotoPredioInspeccionDTO> getListFotoInspeccion() {
		return listFotoInspeccion;
	}
	public void setListFotoInspeccion(List<FotoPredioInspeccionDTO> listFotoInspeccion) {
		this.listFotoInspeccion = listFotoInspeccion;
	}
	public String getImgpredio() {
		return imgpredio;
	}
	public void setImgpredio(String imgpredio) {
		this.imgpredio = imgpredio;
	}
	public int getPosicionImg() {
		return posicionImg;
	}
	public void setPosicionImg(int posicionImg) {
		this.posicionImg = posicionImg;
	}
	public int getFotoPredioId() {
		return fotoPredioId;
	}
	public void setFotoPredioId(int fotoPredioId) {
		this.fotoPredioId = fotoPredioId;
	}
	public String getFotoContribuyente() {
		return fotoContribuyente;
	}
	public void setFotoContribuyente(String fotoContribuyente) {
		this.fotoContribuyente = fotoContribuyente;
	}
	public String getFotoDireccion() {
		return fotoDireccion;
	}
	public void setFotoDireccion(String fotoDireccion) {
		this.fotoDireccion = fotoDireccion;
	}
	public List<FotoPredioDTO> getListFotos() {
		return listFotos;
	}
	public void setListFotos(List<FotoPredioDTO> listFotos) {
		this.listFotos = listFotos;
	}
	public List<FotoPredioConstruccionesDTO> getListConstruccionFotos() {
		return listConstruccionFotos;
	}
	public void setListConstruccionFotos(List<FotoPredioConstruccionesDTO> listConstruccionFotos) {
		this.listConstruccionFotos = listConstruccionFotos;
	}
	public List<FotoPredioInstalacionesDTO> getListInstalacionesFotos() {
		return listInstalacionesFotos;
	}
	public void setListInstalacionesFotos(List<FotoPredioInstalacionesDTO> listInstalacionesFotos) {
		this.listInstalacionesFotos = listInstalacionesFotos;
	}
	public List<MpFiscalizadorDto> getListarFiscalizadores() {
		return listarFiscalizadores;
	}
	public void setListarFiscalizadores(List<MpFiscalizadorDto> listarFiscalizadores) {
		this.listarFiscalizadores = listarFiscalizadores;
	}
	public List<SelectItem> getListSelectItemFisca() {
		return listSelectItemFisca;
	}
	public void setListSelectItemFisca(List<SelectItem> listSelectItemFisca) {
		this.listSelectItemFisca = listSelectItemFisca;
	}
	public HashMap<String, Integer> getMapFisca() {
		return mapFisca;
	}
	public void setMapFisca(HashMap<String, Integer> mapFisca) {
		this.mapFisca = mapFisca;
	}
	public int getInspectorId() {
		return inspectorId;
	}
	public void setInspectorId(int inspectorId) {
		this.inspectorId = inspectorId;
	}
	public int getFotoInspeccionId() {
		return fotoInspeccionId;
	}
	public void setFotoInspeccionId(int fotoInspeccionId) {
		this.fotoInspeccionId = fotoInspeccionId;
	}
	public String getObservacionCierre() {
		return observacionCierre;
	}
	public void setObservacionCierre(String observacionCierre) {
		this.observacionCierre = observacionCierre;
	}
	public Boolean getSelecteFiscalizado() {
		return selecteFiscalizado;
	}
	public void setSelecteFiscalizado(Boolean selecteFiscalizado) {
		this.selecteFiscalizado = selecteFiscalizado;
	}
	
}
