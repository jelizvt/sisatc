package com.sat.sisat.valoresyresoluciones.managed;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.valoresyresoluciones.business.ValoresyResolucionesBoRemote;
import com.sat.sisat.valoresyresoluciones.dto.BuscarActoCoactivoDTO;
import com.sat.sisat.valoresyresoluciones.dto.BuscarActoOrdinarioDTO;
import com.sat.sisat.valoresyresoluciones.dto.BuscarDescargoDTO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class ConsultaValoresyResolucionesManaged  extends BaseManaged {

	@EJB
	ValoresyResolucionesBoRemote valResBo;
	
	private static final long serialVersionUID = 1L;

	private int selectedTipoActo;
	
	private Boolean ActoOrdinario;
	
	private String nroActo;
	
	private BuscarActoOrdinarioDTO actoCurrentItem;
	
	private Boolean CondicionDeuda; /*Para mostrar Descargos,Prescripciones,etc. - Ago-2016*/
	private Boolean ActoCoactiva;   /*Ago-2016*/
	
	public ConsultaValoresyResolucionesManaged(){

		getSessionManaged().setLinkRegresar("/sisat/persona/detallepersona.xhtml");

	}
	
	private List<BuscarActoOrdinarioDTO> listaActosOrdinarios;
	
	private List<BuscarActoCoactivoDTO> listaActosCoactivos;
	
	private List<BuscarDescargoDTO> listaDescargoDeuda;

	@PostConstruct
	public void init() {
		
		setSelectedTipoActo(1);
		setActoOrdinario(Boolean.TRUE);
		BuscarActos();
		
	}
	
	public void BuscarActos(){
		try {
			
			if(ActoOrdinario==Boolean.TRUE){
				
				listaActosOrdinarios=valResBo.getAllActosOrdinarios(getSessionManaged().getContribuyente().getPersonaId(),nroActo);
			}else if(ActoCoactiva==Boolean.TRUE){
				nroRec=nroActo;
				listaActosCoactivos=valResBo.getAllActosCoactivos(getSessionManaged().getContribuyente().getPersonaId(), nroRec);
			}
				listaDescargoDeuda=valResBo.obtenerCondicionDeuda(getSessionManaged().getContribuyente().getPersonaId()); /*Para mostrar Descargos,Prescripciones,etc. - Ago-2016*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private String nroRec;
	
	public void datosActo(){
		try {
			if(actoCurrentItem!=null){
				DetalleActoManaged detalleActo=(DetalleActoManaged)getManaged("detalleActoManaged");
				detalleActo.setProperty(actoCurrentItem);
				//sendRedirectPrincipalListener();
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadTipoActo(ValueChangeEvent event) {
		Integer value = (Integer) event.getNewValue();

		try {

			if(value==1){
				setActoOrdinario(Boolean.TRUE);
				setActoCoactiva(Boolean.FALSE);
				setCondicionDeuda(Boolean.FALSE);
			}else if(value==2) {
				setActoOrdinario(Boolean.FALSE);
				setActoCoactiva(Boolean.TRUE);
				setCondicionDeuda(Boolean.FALSE);
			}else if(value==3) {					 /*Para mostrar Descargos,Prescripciones,etc. - Ago-2016*/
				setCondicionDeuda(Boolean.TRUE);
				setActoOrdinario(Boolean.FALSE);
				setActoCoactiva(Boolean.FALSE);
			}
			BuscarActos();
		} catch (Exception e) {
			e.printStackTrace();
			}
	}

	
	public void limpiar() throws Exception   		 /*Ago-2016*/
	{ 		
		setNroActo("");
		listaActosOrdinarios.clear();
		listaActosCoactivos.clear();
		listaDescargoDeuda.clear();
		BuscarActos();
	}
	
	
	public String getNroActo() {
		return nroActo;
	}

	public void setNroActo(String nroActo) {
		this.nroActo = nroActo;
	}
	
	public List<BuscarActoOrdinarioDTO> getListaActosOrdinarios() {
		return listaActosOrdinarios;
	}

	public void setListaActosOrdinarios(
			List<BuscarActoOrdinarioDTO> listaActosOrdinarios) {
		this.listaActosOrdinarios = listaActosOrdinarios;
	}
	
	public Boolean getActoOrdinario() {
		return ActoOrdinario;
	}

	public void setActoOrdinario(Boolean actoOrdinario) {
		ActoOrdinario = actoOrdinario;
	}
	public int getSelectedTipoActo() {
		return selectedTipoActo;
	}
	public void setSelectedTipoActo(int selectedTipoActo) {
		this.selectedTipoActo = selectedTipoActo;
	}

	public List<BuscarActoCoactivoDTO> getListaActosCoactivos() {
		return listaActosCoactivos;
	}

	public void setListaActosCoactivos(List<BuscarActoCoactivoDTO> listaActosCoactivos) {
		this.listaActosCoactivos = listaActosCoactivos;
	}

	public BuscarActoOrdinarioDTO getActoCurrentItem() {
		return actoCurrentItem;
	}

	public void setActoCurrentItem(BuscarActoOrdinarioDTO actoCurrentItem) {
		this.actoCurrentItem = actoCurrentItem;
	}

	public Boolean getCondicionDeuda() {
		return CondicionDeuda;
	}

	public void setCondicionDeuda(Boolean condicionDeuda) {
		CondicionDeuda = condicionDeuda;
	}

	public List<BuscarDescargoDTO> getListaDescargoDeuda() {
		return listaDescargoDeuda;
	}

	public void setListaDescargoDeuda(List<BuscarDescargoDTO> listaDescargoDeuda) {
		this.listaDescargoDeuda = listaDescargoDeuda;
	}

	public Boolean getActoCoactiva() {
		return ActoCoactiva;
	}

	public void setActoCoactiva(Boolean actoCoactiva) {
		ActoCoactiva = actoCoactiva;
	}

}
