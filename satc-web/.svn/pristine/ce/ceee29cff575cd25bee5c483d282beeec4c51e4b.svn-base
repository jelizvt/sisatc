package com.sat.sisat.predial.managed;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import com.sat.sisat.common.business.GeneralBoRemote;
import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.menus.business.MenuBoRemote;
import com.sat.sisat.menus.dto.SimpleMenuDTO;
import com.sat.sisat.predial.business.CalculoPredialBoRemote;
import com.sat.sisat.predial.business.RegistroPrediosBoRemote;
import com.sat.sisat.predial.dto.FindRpDjPredial;
import com.sat.sisat.predial.dto.DtDeterminacionDTO;

/**
 * @author Jakelinne
 * @version 0.1
 * @since 02/07/2012 La clase GenerarArbitriosManaged.java encargada de generar determinacion y deuda de arbitrios anos antes del 2012
 */
@ManagedBean
@ViewScoped
public class GenerarArbitriosManaged extends BaseManaged {


	/**
	 * 
	 */
	private static final long serialVersionUID = -7079115396564698443L;
	
	@EJB
	CalculoPredialBoRemote calculoPredialBo;
	
	@EJB
	RegistroPrediosBoRemote registroPrediosBo;

	@EJB
	GeneralBoRemote generalBo;
	
	@EJB
	MenuBoRemote menuBo;
	
	private ArrayList<DtDeterminacionDTO> records;
		
	private BigDecimal limpieza = new BigDecimal(0);
	private BigDecimal parques = new BigDecimal(0);
	private BigDecimal serenazgo = new BigDecimal(0);
	private Integer predioId;
	private Integer djId;
	
	private Integer cmbPredioId;
	private List<Integer> lstPrediosId = new ArrayList<Integer>();
	private List<SelectItem> lstSelectItemPredios = new ArrayList<SelectItem>();
	
	private Integer anio;
	
	private String usuario;
	private String terminal;
	private Integer personaId;
	private ArrayList<FindRpDjPredial> lstPredios;
	private boolean porCuota;
	
	// INICIO PERMISOS PARA EL MODULO -=CRAMIREZ=-
		private List<SimpleMenuDTO> listPermisosSubmenu = new ArrayList<SimpleMenuDTO>();
		private boolean permisoGenerar;
	// FIN PERMISOS PARA EL MODULO	
	
	public GenerarArbitriosManaged() {

	}

	@PostConstruct
	public void init() {
		permisosMenu();
		personaId = getSessionManaged().getContribuyente().getPersonaId();		
		
		try {
			
			lstPredios = registroPrediosBo.getRpDjpredial(null,null,null,null,null,null,null,null,null,null,null,personaId,Boolean.TRUE);	
			if(lstPredios != null && lstPredios.size()>0){
				for(FindRpDjPredial predio : lstPredios){
					lstPrediosId.add(predio.getPredioId());
					lstSelectItemPredios.add(new SelectItem(predio.getPredioId()));
				}
			}
			anio = 2011;
			limpieza = new BigDecimal(0);
			parques = new BigDecimal(0);
			serenazgo = new BigDecimal(0);
			
			buscarArbitrios();

		} catch (Exception e) {

			WebMessages.messageError(e.getMessage());
		}

	}
	
	public void permisosMenu() {	
		try {
			int submenuId = Constante.GENERACION_ARBITRIOS;
			
			int permisoGenerarId = Constante.GENENRAR;
			
			permisoGenerar = false;
			
			listPermisosSubmenu  = menuBo.getAccesosSubmenuUsuario( getSessionManaged().getUsuarioLogIn().getUsuarioId() , submenuId);
			
			Iterator<SimpleMenuDTO> menuIterar = listPermisosSubmenu.iterator();
			while (menuIterar.hasNext()) {
				SimpleMenuDTO lsm = menuIterar.next();
				if(lsm.getItemId() == permisoGenerarId) {
					permisoGenerar = true;
				}
			}
			
		} catch (SisatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void generarArbitrios() {

		try {
			/** Generando las Arbitrios años anteriores al 2012 */
			
			Boolean respuesta = Boolean.FALSE;
			djId = calculoPredialBo.getDjByPredioId(personaId, predioId, anio);
			
			if(djId != null && djId > 0){
				respuesta = calculoPredialBo.generarDeudaAribtrios(personaId, djId, anio, limpieza, parques, serenazgo,  getSessionManaged().getUsuarioLogIn().getUsuarioId(),  getSessionManaged().getTerminalLogIn());
			}else{
				addErrorMessage("El Predio "+predioId+ " para el año "+anio+" no tiene una DDJJ relacionada.");
			}
						
			if(respuesta){
				addInfoMessage("Generacion y Registro de Arbitrios para el predio "+predioId+" y el año "+anio);
				
			}else{
				addErrorMessage("No se genero el calculo de arbitrios");
			}
			
			mostrarGenerado(respuesta);
			
		} catch (SisatException e) {
			WebMessages.messageError(e.getMessage());
		}

	}

	
	
	public void changeListenerComboBoxPredio(ValueChangeEvent event) {

		Integer cmbValueSelect = (Integer) event.getNewValue();

		if (cmbValueSelect != null && cmbValueSelect > 0) {
			predioId=cmbValueSelect;
		}
	}
	
	public void changeListenerComboBoxAnio(ValueChangeEvent event) {

		Integer cmbValueSelect = (Integer) event.getNewValue();

		if (cmbValueSelect != null && cmbValueSelect > 0) {
			anio=cmbValueSelect;
		}
	}
	
	public void mostrarGenerado( Boolean respuesta){
		try {
			if(respuesta){
				records = calculoPredialBo.getArbitriosGenerados(personaId, djId);
				
				if(records == null){
					addInfoMessage("No hay Arbitrios Generados");
				}
			}
			
		} catch (SisatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void buscarArbitrios(){
		try {
			if(getPredioId() != null && getAnio() != null){
				
				djId = calculoPredialBo.getDjByPredioId(personaId, predioId, anio);
				
				if(djId != null && djId > 0){
					records = calculoPredialBo.getArbitriosGenerados(personaId,	djId);
				}else{
					records = null;
				}
				
				if (records == null || records.size() == 0) {
					addInfoMessage("No hay Deuda de Arbitrios para el predio "+predioId+ " en el año "+anio);
				}
	
			}else{
				addErrorMessage("Debe escoger un Predio y un anio");
			}
			
		} catch (SisatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void checkPorCuotas(ValueChangeEvent event) {
		Boolean check = (Boolean) event.getNewValue();
		if (check == Boolean.TRUE) {
			setPorCuota(Boolean.TRUE);			
		} else {
			setPorCuota(Boolean.FALSE);
		}
	}
	
	public BigDecimal getLimpieza() {
		return limpieza;
	}

	public void setLimpieza(BigDecimal limpieza) {
		this.limpieza = limpieza;
	}

	public BigDecimal getParques() {
		return parques;
	}

	public void setParques(BigDecimal parques) {
		this.parques = parques;
	}

	public BigDecimal getSerenazgo() {
		return serenazgo;
	}

	public void setSerenazgo(BigDecimal serenazgo) {
		this.serenazgo = serenazgo;
	}

	public Integer getPredioId() {
		return predioId;
	}

	public void setPredioId(Integer predioId) {
		this.predioId = predioId;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public Integer getPersonaId() {
		return personaId;
	}

	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}

	public List<Integer> getLstPrediosId() {
		return lstPrediosId;
	}

	public void setLstPrediosId(List<Integer> lstPrediosId) {
		this.lstPrediosId = lstPrediosId;
	}

	public ArrayList<FindRpDjPredial> getLstPredios() {
		return lstPredios;
	}

	public void setLstPredios(ArrayList<FindRpDjPredial> lstPredios) {
		this.lstPredios = lstPredios;
	}

	public Integer getCmbPredioId() {
		return cmbPredioId;
	}

	public void setCmbPredioId(Integer cmbPredioId) {
		this.cmbPredioId = cmbPredioId;
	}

	public List<SelectItem> getLstSelectItemPredios() {
		return lstSelectItemPredios;
	}

	public void setLstSelectItemPredios(List<SelectItem> lstSelectItemPredios) {
		this.lstSelectItemPredios = lstSelectItemPredios;
	}

	public ArrayList<DtDeterminacionDTO> getRecords() {
		return records;
	}

	public void setRecords(ArrayList<DtDeterminacionDTO> records) {
		this.records = records;
	}

	public boolean isPorCuota() {
		return porCuota;
	}

	public void setPorCuota(boolean porCuota) {
		this.porCuota = porCuota;
	}

	public Integer getDjId() {
		return djId;
	}

	public void setDjId(Integer djId) {
		this.djId = djId;
	}

	public List<SimpleMenuDTO> getListPermisosSubmenu() {
		return listPermisosSubmenu;
	}

	public void setListPermisosSubmenu(List<SimpleMenuDTO> listPermisosSubmenu) {
		this.listPermisosSubmenu = listPermisosSubmenu;
	}

	public boolean isPermisoGenerar() {
		return permisoGenerar;
	}

	public void setPermisoGenerar(boolean permisoGenerar) {
		this.permisoGenerar = permisoGenerar;
	}
	

}

