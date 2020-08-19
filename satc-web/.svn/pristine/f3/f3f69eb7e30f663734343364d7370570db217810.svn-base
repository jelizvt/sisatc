package com.sat.sisat.fiscalizacion.managed;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import com.sat.sisat.controlycobranza.dto.MpFiscalizador;
import com.sat.sisat.controlycobranza.dto.MpFiscalizadorArea;
import com.sat.sisat.controlycobranza.dto.MpFiscalizadorDto;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.fiscalizacion.business.FiscalizacionBoRemote;
import com.sat.sisat.menus.business.MenuBoRemote;
import com.sat.sisat.menus.dto.PerfilesDTO;
import com.sat.sisat.menus.dto.SimpleMenuDTO;
import com.sat.sisat.persistence.entity.RpFiscalizacionPrograma;
import com.sat.sisat.persistence.entity.RpFiscalizacionProgramaDetalle;

@ManagedBean
@ViewScoped
public class FiscalizadorManaged extends BaseManaged {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	FiscalizacionBoRemote ficalizacionBo;
	
	@EJB
	private MenuBoRemote menuBo;
	

	private String cmbTipoUnidad;	
	private HtmlComboBox cmbxUnidad;
	private Integer unidadId;
	private HashMap<String, Integer> mapUnidad = new HashMap<String, Integer>();
	private HashMap<Integer,String> mapUnidadR=new HashMap<Integer,String>();
	private List<SelectItem> listaUnidad = new ArrayList<SelectItem>();
	

	
//	private List<MpFiscalizadorDto> listarFiscalizadores;
	private List<MpFiscalizadorDto> listarFiscalizadores = new ArrayList<MpFiscalizadorDto>();
	private List<MpFiscalizadorDto> listarFiscalizador = new ArrayList<MpFiscalizadorDto>();
	
	
	//private RpFiscalizacionPrograma currentItem;
	//private RpFiscalizacionPrograma findPrograma = new RpFiscalizacionPrograma();
	
	private MpFiscalizadorDto currentItem;
	private MpFiscalizadorDto findFiscalizador = new MpFiscalizadorDto();
	
	public String codigo;
	public String nombresApellidos;
	public String dni;
	public String direccion;
	public Date fini;
	public Date ffin;
	public int unidad_id;
	public String nombrearea;
	public int estado ;
	public String celular;
	private Boolean editable;
	

	//actualizar:
	private Integer inspectorId;
	
	//busqueda:
	private String descripcionInspector;
	// INICIO PERMISOS PARA EL MODULO -=CRAMIREZ=-
		private List<SimpleMenuDTO> listPermisosSubmenu = new ArrayList<SimpleMenuDTO>();
		private boolean permisoBuscar;
		private boolean permisoAgregarRegistrar;
		private boolean permisoModificarActualizar;
	// FIN PERMISOS PARA EL MODULO
		
	// Inicio Para Perfiles
		private List<PerfilesDTO> listPerfiles = new ArrayList<PerfilesDTO>();
		private PerfilesDTO selectPerfil;
		

	// Fin Para Perfiles 
	
	
			public FiscalizadorManaged() throws Exception {
				
			}
			
			@PostConstruct
			public void init() {
				permisosMenu();
				try {
					/* COMBOBOX:: TIPO DE UNIDAD */
					List<MpFiscalizadorArea> lstRpTipoPrograma = ficalizacionBo.getAllTipoArea();
					Iterator<MpFiscalizadorArea> it2 = lstRpTipoPrograma.iterator();
					listaUnidad = new ArrayList<SelectItem>();

					while (it2.hasNext()) {
						MpFiscalizadorArea obj = it2.next();
						listaUnidad.add(new SelectItem(obj.getDescripcion(), String.valueOf(obj.getUnidad_id())));
						mapUnidad.put(obj.getDescripcion().trim(),obj.getUnidad_id());
						//mapTipoProgramaRequerimientoR.put(obj.getProgramaId(), obj.getNombrePrograma().trim());
					}
					/*- COMBOBOX:: TIPO DE UNIDAD -*/
					
//					resultado=ficalizacionBo.getAllPrograma();
					
					listarFiscalizadores=ficalizacionBo.getAllInspectores();

					Integer programaid = (Integer) getSessionMap().get("fiscalizadorId");
					if (programaid != null && programaid != Constante.RESULT_PENDING) {
						// Registro por actualizar
						setInspectorId(programaid);
						setEditable(false);
						cargar();
					} else {
						// Nuevo registro
						currentItem = new MpFiscalizadorDto();
						setCodigo("");
						setEditable(true);
				
						
					}
										
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
			}
			
			public void permisosMenu() {	
				try {
					int submenuId = Constante.REGISTRO_DE_FISCALIZADORES;
					
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
					if (getInspectorId() != null && getInspectorId().intValue() > 0) {

						
						listarFiscalizador= ficalizacionBo.getAllInspectoresById(getInspectorId());
						setCodigo(listarFiscalizador.get(0).getCodigo());
						setNombresApellidos(listarFiscalizador.get(0).getNombresApellidos());
						setDni(listarFiscalizador.get(0).getDni());
						setCelular(listarFiscalizador.get(0).getCelular());
						setDireccion(listarFiscalizador.get(0).getDireccion());
						setFini(listarFiscalizador.get(0).getFini());
						setFfin(listarFiscalizador.get(0).getFfin());

						String valueTipoDocumento=listarFiscalizador.get(0).getTermninal();
		                setCmbTipoUnidad(valueTipoDocumento);
		             
					
					}	
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}

			public void guardar() throws Exception {
				try {
					MpFiscalizadorDto p = new MpFiscalizadorDto();
					
					p.setCodigo(codigo);
					p.setNombresApellidos(nombresApellidos);
					p.setDni(dni);
					p.setDireccion(direccion);
					p.setFini(new java.sql.Date((fini.getTime())));
					p.setFfin(new java.sql.Date((ffin.getTime())));
					p.setUnidad_id(unidadId);
					p.setCelular(celular);				
					p.setEstado("1");
					p.setTermninal(getSessionManaged().getUser().getTerminal());
					p.setUsuarioId(getSessionManaged().getUser().getUsuarioId());
					p.setFechaRegistro(Calendar.getInstance().getTime());
					
					ficalizacionBo.crearInspector(p);
					
					limpiar();
					listarFiscalizadores=ficalizacionBo.getAllInspectores();
				
					
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

					listarFiscalizadores=new ArrayList<MpFiscalizadorDto>();

					if(descripcionInspector!=null){

						listarFiscalizadores=ficalizacionBo.findInspector(descripcionInspector);
						
					}
	

					
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
			 public void limpiar()throws Exception{
				
					setCodigo("");
					setNombresApellidos("");
					setDireccion("");
					setDni("");
					setFini(null);
					setFfin(null);
					setCelular(null);
					setCmbTipoUnidad("");
				 	setDescripcionInspector("");
			}
			 
			
			 public void editar() throws Exception{
					
			
					Integer area;
					
					if (unidadId==null){
						listarFiscalizador.get(0).setUnidad_id(mapUnidad.get(cmbTipoUnidad));
						unidadId=listarFiscalizador.get(0).getUnidad_id();
						
						area=unidadId;
						
					}else{
						area=unidadId;
					}

					
					ficalizacionBo.actualizarInspector(codigo, nombresApellidos, dni, celular, direccion, fini, ffin, area, getInspectorId());
					
					limpiar();
				}
			 
			 public String inscripcion(){
					getSessionMap().put("fiscalizadorId", Constante.RESULT_PENDING);
					getSessionMap().put("pageReturn", "/sisat/fiscalizacion/buscarinspector.xhtml");
					closeSession("FiscalizadorManaged");
					return sendRedirectPrincipal();
				}
				
				 public String actualizacion(){
						if(currentItem!=null){
							getSessionMap().put("fiscalizadorId", currentItem.getIdfiscalizador());
							getSessionMap().put("pageReturn", "/sisat/fiscalizacion/buscarprograma.xhtml");
							getSessionMap().put("findPrograma", findFiscalizador);
						}
						closeSession("FiscalizadorManaged");
						return sendRedirectPrincipal();
					}
			
				 public void loadUnidad(ValueChangeEvent event) {
						try {
							HtmlComboBox combo = (HtmlComboBox) event.getComponent();
							String value = combo.getValue().toString();
							if (value != null) {
								unidadId= (Integer) mapUnidad.get(value);
								setCmbTipoUnidad(value);
							}
						} catch (Exception e) {
							e.printStackTrace();
							WebMessages.messageFatal(e);
						}
					}

				 

			public String getCmbTipoUnidad() {
				return cmbTipoUnidad;
			}

			public void setCmbTipoUnidad(String cmbTipoUnidad) {
				this.cmbTipoUnidad = cmbTipoUnidad;
			}

			public HtmlComboBox getCmbxUnidad() {
				return cmbxUnidad;
			}

			public void setCmbxUnidad(HtmlComboBox cmbxUnidad) {
				this.cmbxUnidad = cmbxUnidad;
			}

			public Integer getUnidadId() {
				return unidadId;
			}

			public void setUnidadId(Integer unidadId) {
				this.unidadId = unidadId;
			}

			public HashMap<String, Integer> getMapUnidad() {
				return mapUnidad;
			}

			public void setMapUnidad(HashMap<String, Integer> mapUnidad) {
				this.mapUnidad = mapUnidad;
			}

			public HashMap<Integer, String> getMapUnidadR() {
				return mapUnidadR;
			}

			public void setMapUnidadR(HashMap<Integer, String> mapUnidadR) {
				this.mapUnidadR = mapUnidadR;
			}

			public List<SelectItem> getListaUnidad() {
				return listaUnidad;
			}

			public void setListaUnidad(List<SelectItem> listaUnidad) {
				this.listaUnidad = listaUnidad;
			}

			public MpFiscalizadorDto getFindFiscalizador() {
				return findFiscalizador;
			}

			public void setFindFiscalizador(MpFiscalizadorDto findFiscalizador) {
				this.findFiscalizador = findFiscalizador;
			}

			public void setCurrentItem(MpFiscalizadorDto currentItem) {
				this.currentItem = currentItem;
			}

			
			public List<MpFiscalizadorDto> getListarFiscalizador() {
				return listarFiscalizador;
			}

			public void setListarFiscalizador(List<MpFiscalizadorDto> listarFiscalizador) {
				this.listarFiscalizador = listarFiscalizador;
			}

			public Integer getInspectorId() {
				return inspectorId;
			}

			public void setInspectorId(Integer inspectorId) {
				this.inspectorId = inspectorId;
			}

			public String getDescripcionInspector() {
				return descripcionInspector;
			}

			public void setDescripcionInspector(String descripcionInspector) {
				this.descripcionInspector = descripcionInspector;
			}

			
			public String getCodigo() {
				return codigo;
			}

			public void setCodigo(String codigo) {
				this.codigo = codigo;
			}

			public String getNombresApellidos() {
				return nombresApellidos;
			}

			public void setNombresApellidos(String nombresApellidos) {
				this.nombresApellidos = nombresApellidos;
			}

			public String getDni() {
				return dni;
			}

			public void setDni(String dni) {
				this.dni = dni;
			}

			public String getDireccion() {
				return direccion;
			}

			public void setDireccion(String direccion) {
				this.direccion = direccion;
			}

			public Date getFini() {
				return fini;
			}

			public void setFini(Date fini) {
				this.fini = fini;
			}

			public Date getFfin() {
				return ffin;
			}

			public void setFfin(Date ffin) {
				this.ffin = ffin;
			}

			public int getUnidad_id() {
				return unidad_id;
			}

			public void setUnidad_id(int unidad_id) {
				this.unidad_id = unidad_id;
			}

			public String getNombrearea() {
				return nombrearea;
			}

			public void setNombrearea(String nombrearea) {
				this.nombrearea = nombrearea;
			}

			public int getEstado() {
				return estado;
			}

			public void setEstado(int estado) {
				this.estado = estado;
			}

			public List<MpFiscalizadorDto> getListarFiscalizadores() {
				return listarFiscalizadores;
			}

			public void setListarFiscalizadores(List<MpFiscalizadorDto> listarFiscalizadores) {
				this.listarFiscalizadores = listarFiscalizadores;
			}

			public void setEditable(Boolean editable) {
				this.editable = editable;
			}
			
			public Boolean getEditable() {
				return editable;
			}

			public void setCelular(String celular) {
				this.celular = celular;
			}
			
			public String getCelular() {
				return celular;
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
