package com.sat.sisat.fiscalizacion.managed;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.fiscalizacion.business.FiscalizacionBoRemote;
import com.sat.sisat.menus.business.MenuBoRemote;
import com.sat.sisat.menus.dto.SimpleMenuDTO;
import com.sat.sisat.persistence.entity.RpFiscalizacionPrograma;
import com.sat.sisat.persistence.entity.RpFiscalizacionProgramaDetalle;

@ManagedBean
@ViewScoped
public class RegistrarProgramaManaged extends BaseManaged {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	FiscalizacionBoRemote ficalizacionBo;
	
	@EJB
	private MenuBoRemote menuBo;

	private String descripcionPrograma;
	private String[] lstAnios = null;
	private List<RpFiscalizacionProgramaDetalle> lstAniosInspc = null;
	private List<RpFiscalizacionProgramaDetalle> lstAniosInspcBd = null;
	private HashMap<String, Integer> mapMpAnioSeleccionados = new HashMap<String, Integer>();
	
	private List<RpFiscalizacionPrograma> resultado = new ArrayList<RpFiscalizacionPrograma>();
	private List<RpFiscalizacionPrograma> resultados = new ArrayList<RpFiscalizacionPrograma>();
	
	private RpFiscalizacionPrograma currentItem;
	private RpFiscalizacionPrograma findPrograma = new RpFiscalizacionPrograma();
	
	
	
//busqueda:
	private String descripcionProgramaB;
//actualizar:
	private Integer programaId;
	
	// INICIO PERMISOS PARA EL MODULO -=CRAMIREZ=-
			private List<SimpleMenuDTO> listPermisosSubmenu = new ArrayList<SimpleMenuDTO>();
			private boolean permisoBuscar;
			private boolean permisoAgregarRegistrar;
			private boolean permisoModificarActualizar;
		// FIN PERMISOS PARA EL MODULO
			
	
			public RegistrarProgramaManaged() throws Exception {
				
			}
			
			@PostConstruct
			public void init() throws Exception {
				permisosMenu();
				try {
					lstAnios = new String[] {};
					lstAniosInspc = ficalizacionBo.getAllAniosReq();
					Iterator<RpFiscalizacionProgramaDetalle> it2 = lstAniosInspc.iterator();
					String temp2 = "";
					while (it2.hasNext()) 
					{
						RpFiscalizacionProgramaDetalle obj = it2.next();
						mapMpAnioSeleccionados.put(obj.getAnioFiscalizacion(),obj.getProgramaDetalleId());
						temp2 = temp2 + obj.getAnioFiscalizacion()+ ",";
					}
					lstAnios = temp2.split(",");
					
					resultados=ficalizacionBo.getAllPrograma();

					Integer programaid = (Integer) getSessionMap().get("programaId");
					if (programaid != null && programaid != Constante.RESULT_PENDING) {
						// Registro por actualizar
						setProgramaId(programaid);

						cargar();
					} else {
						// Nuevo registro
						currentItem = new RpFiscalizacionPrograma();
						setDescripcionProgramaB("");
						
				
						
					}
										
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
			}
			
			public void permisosMenu() {	
				try {
					int submenuId = Constante.REGISTROR_DE_PROGRAMA;
					
					int permisoBuscarId = Constante.BUSCAR;
					int permisoAgregarRegistrarId = Constante.AGREGAR_REGISTRAR;
					int permisoModificarActualizarId = Constante.MODIFICAR_ACTUALIZAR;
					
					permisoBuscar = false;
					permisoAgregarRegistrar = false;
					permisoModificarActualizar = false;
					
					listPermisosSubmenu  = menuBo.getAccesosSubmenuUsuario( getSessionManaged().getUsuarioLogIn().getUsuarioId() , submenuId);
					
					Iterator<SimpleMenuDTO> menuIterar = listPermisosSubmenu.iterator();
					while (menuIterar.hasNext()) {
						SimpleMenuDTO lsm = menuIterar.next();
						if(lsm.getItemId() == permisoBuscarId) {
							permisoBuscar = true;
						}
						if(lsm.getItemId() == permisoAgregarRegistrarId) {
							permisoAgregarRegistrar = true;
						}
						if(lsm.getItemId() == permisoModificarActualizarId) {
							permisoModificarActualizar = true;
						}
					}
					
				} catch (SisatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			public void cargar() {
				try{
					if (getProgramaId() != null && getProgramaId().intValue() > 0) {
						currentItem = ficalizacionBo.getAllProgramaById(getProgramaId());//cambiar metodo
						setDescripcionPrograma(currentItem.getNombrePrograma());
						
						//--- recuperando y listando años:
						
						lstAniosInspcBd =ficalizacionBo.getAllProgramaAniosById(getProgramaId());//cambiar metodo
						String cuotasRecuperadas = lstAniosInspcBd.get(0).getAnioFiscalizacion();
						 Iterator<RpFiscalizacionProgramaDetalle> it = lstAniosInspcBd.iterator();
						 lstAnios = cuotasRecuperadas.split(",");
						          String temp2 = "";
						          while (it.hasNext()) {
						        	  RpFiscalizacionProgramaDetalle obj = it.next();
						        	  mapMpAnioSeleccionados.put(obj.getAnioFiscalizacion().trim(),obj.getProgramaId());
										temp2 = temp2 + obj.getAnioFiscalizacion()+ ",";
									}
						
						          lstAnios = temp2.split(",");
						   
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}

			public void guardar() throws Exception {
				try {
					
				if (getProgramaId()!=null){
					
					ficalizacionBo.actualizarPrograma(descripcionPrograma,getProgramaId());
					
					
					//Aqui registra el/los años:
					if (lstAnios != null && lstAnios.length > 0) {
						for (String p1 : lstAnios) {
							
							
							Integer programa_id = getProgramaId();
							RpFiscalizacionProgramaDetalle rpProgramaDet = new  RpFiscalizacionProgramaDetalle();
							
							rpProgramaDet.setProgramaId(programa_id);
//							rpProgramaDet.setAnioFiscalizacion(mapMpAnioSeleccionados.get(p1).toString());
							rpProgramaDet.setAnioFiscalizacion(p1);
							rpProgramaDet.setEstado("1");

							
							ficalizacionBo.crearProgramaDetalle(rpProgramaDet);
							

						}
					}
					
				}else{
					
					RpFiscalizacionPrograma rpPrograma = new  RpFiscalizacionPrograma();
					rpPrograma.setNombrePrograma(descripcionPrograma);
					rpPrograma.setEstado("1");
					ficalizacionBo.crearPrograma(rpPrograma);
					
					//Aqui registra el/los años:
					if (lstAnios != null && lstAnios.length > 0) {
						for (String p1 : lstAnios) {
							
							
							Integer programa_id = ficalizacionBo.getUltimoPrograma();
							RpFiscalizacionProgramaDetalle rpProgramaDet = new  RpFiscalizacionProgramaDetalle();
							
							rpProgramaDet.setProgramaId(programa_id);
//							rpProgramaDet.setAnioFiscalizacion(mapMpAnioSeleccionados.get(p1).toString());
							rpProgramaDet.setAnioFiscalizacion(p1);
							rpProgramaDet.setEstado("1");

							
							ficalizacionBo.crearProgramaDetalle(rpProgramaDet);
							

						}
					}
				}	
					
				} catch (Exception e) {
					e.printStackTrace();
					WebMessages.messageFatal(e);
				}
			}

			public String salir(){
				return sendRedirectPrincipal();
			}
			
			public void buscar() throws Exception{
				try{
					resultados=new ArrayList<RpFiscalizacionPrograma>();

					if(descripcionProgramaB!=null){

						resultados=ficalizacionBo.findPrograma(descripcionProgramaB);
						
					}

					
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
			 public void limpiar()throws Exception{
				
				 		setDescripcionProgramaB("");
				 		resultados=new ArrayList<RpFiscalizacionPrograma>();

				 		
			}
			 
			 public String actualizacion(){
					if(currentItem!=null){
						getSessionMap().put("programaId", currentItem.getProgramaId());
						getSessionMap().put("pageReturn", "/sisat/fiscalizacion/buscarprograma.xhtml");
						getSessionMap().put("findPrograma", findPrograma);
					}
					closeSession("RegistrarProgramaManaged");
					return sendRedirectPrincipal();
				}
			 
			 public String inscripcion(){
					getSessionMap().put("programaId", Constante.RESULT_PENDING);
					getSessionMap().put("pageReturn", "/sisat/fiscalizacion/buscarprograma.xhtml");
					closeSession("RegistrarProgramaManaged");
					return sendRedirectPrincipal();
				}
			
			public String[] getLstAnios() {
				return lstAnios;
			}

			public void setLstAnios(String[] lstAnios) {
				this.lstAnios = lstAnios;
			}

			public List<RpFiscalizacionProgramaDetalle> getLstAniosInspc() {
				return lstAniosInspc;
			}

			public void setLstAniosInspc(List<RpFiscalizacionProgramaDetalle> lstAniosInspc) {
				this.lstAniosInspc = lstAniosInspc;
			}

			public HashMap<String, Integer> getMapMpAnioSeleccionados() {
				return mapMpAnioSeleccionados;
			}

			public void setMapMpAnioSeleccionados(
					HashMap<String, Integer> mapMpAnioSeleccionados) {
				this.mapMpAnioSeleccionados = mapMpAnioSeleccionados;
			}

			public String getDescripcionPrograma() {
				return descripcionPrograma;
			}

			public void setDescripcionPrograma(String descripcionPrograma) {
				this.descripcionPrograma = descripcionPrograma;
			}

			public List<RpFiscalizacionPrograma> getResultado() {
				return resultado;
			}

			public void setResultado(List<RpFiscalizacionPrograma> resultado) {
				this.resultado = resultado;
			}

			public String getDescripcionProgramaB() {
				return descripcionProgramaB;
			}

			public void setDescripcionProgramaB(String descripcionProgramaB) {
				this.descripcionProgramaB = descripcionProgramaB;
			}

			public List<RpFiscalizacionPrograma> getResultados() {
				return resultados;
			}

			public void setResultados(List<RpFiscalizacionPrograma> resultados) {
				this.resultados = resultados;
			}

			public RpFiscalizacionPrograma getCurrentItem() {
				return currentItem;
			}

			public void setCurrentItem(RpFiscalizacionPrograma currentItem) {
				this.currentItem = currentItem;
			}

			public RpFiscalizacionPrograma getFindPrograma() {
				return findPrograma;
			}

			public void setFindPrograma(RpFiscalizacionPrograma findPrograma) {
				this.findPrograma = findPrograma;
			}

			public Integer getProgramaId() {
				return programaId;
			}

			public void setProgramaId(Integer programaId) {
				this.programaId = programaId;
			}

			public List<RpFiscalizacionProgramaDetalle> getLstAniosInspcBd() {
				return lstAniosInspcBd;
			}

			public void setLstAniosInspcBd(
					List<RpFiscalizacionProgramaDetalle> lstAniosInspcBd) {
				this.lstAniosInspcBd = lstAniosInspcBd;
			}

			public List<SimpleMenuDTO> getListPermisosSubmenu() {
				return listPermisosSubmenu;
			}

			public void setListPermisosSubmenu(List<SimpleMenuDTO> listPermisosSubmenu) {
				this.listPermisosSubmenu = listPermisosSubmenu;
			}

			public boolean isPermisoBuscar() {
				return permisoBuscar;
			}

			public void setPermisoBuscar(boolean permisoBuscar) {
				this.permisoBuscar = permisoBuscar;
			}

			public boolean isPermisoAgregarRegistrar() {
				return permisoAgregarRegistrar;
			}

			public void setPermisoAgregarRegistrar(boolean permisoAgregarRegistrar) {
				this.permisoAgregarRegistrar = permisoAgregarRegistrar;
			}

			public boolean isPermisoModificarActualizar() {
				return permisoModificarActualizar;
			}

			public void setPermisoModificarActualizar(boolean permisoModificarActualizar) {
				this.permisoModificarActualizar = permisoModificarActualizar;
			}
			
			

}
