package com.sat.sisat.vehicular.managed;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.FacesUtil;
import com.sat.sisat.persistence.entity.GnNotaria;
import com.sat.sisat.persistence.entity.RvCategoriaVehiculo;
import com.sat.sisat.persistence.entity.RvClaseVehiculo;
import com.sat.sisat.persistence.entity.RvCondicionVehiculo;
import com.sat.sisat.persistence.entity.RvDjvehicular;
import com.sat.sisat.persistence.entity.RvMarca;
import com.sat.sisat.persistence.entity.RvModeloVehiculo;
import com.sat.sisat.persistence.entity.RvModeloVehiculoPK;
import com.sat.sisat.persistence.entity.RvMotivoDeclaracion;
import com.sat.sisat.persistence.entity.RvMotivoDescargo;
import com.sat.sisat.persistence.entity.RvTipoCarroceria;
import com.sat.sisat.persistence.entity.RvTipoMotor;
import com.sat.sisat.persistence.entity.RvTipoTraccion;
import com.sat.sisat.persistence.entity.RvTipoTransmision;
import com.sat.sisat.persistence.entity.RvVehiculo;
import com.sat.sisat.persona.managed.RegistroPersonaManaged;
import com.sat.sisat.vehicular.business.VehicularBoRemote;
import com.sat.sisat.vehicular.cns.ReusoFormCns;
import com.sat.sisat.vehicular.cns.VehicularCns;
import com.sat.sisat.vehicular.dto.AnexosDeclaVehicDTO;
import com.sat.sisat.vehicular.dto.BuscarPersonaDTO;
import com.sat.sisat.vehicular.dto.DocumentoSustentatorioDTO;
import com.sat.sisat.vehicular.dto.PreliminarDTO;


@ManagedBean
@ViewScoped
public class DescargoVehicularManaged extends BaseManaged {

	private static final long serialVersionUID = -5798168408790726467L;

	@EJB
	VehicularBoRemote vehicularBo;
	DateUtil formatoTime;
	// Datos declaración
	private String numeroDecla;

	private List<SelectItem> lstMotivoDecla = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRvMotivoDecla = new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIRvMotivoDecla = new HashMap<Integer, String>();
	private String selectedMotivoDecla;

	private Date fechaDecla = new Date();
	private String anioDecla = DateUtil.obtenerAnioActual();
	private String estadoDecla;
	private Date fechaAdquisicion;
	// Datos vehículo
	private String nroMotorVehic;
	private String placaVehic;
	private Date fechaPrimeraInsReg = new Date();
	private String anioFabricVehic;

	private List<SelectItem> lstClaseVehiculo = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRvClaseVehiculo = new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIRvClaseVehiculo = new HashMap<Integer, String>();
	private String selectedClaseVehic;

	private List<SelectItem> lstCategoriaVehiculo = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRvCategoriaVehiculo = new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIRvCategoriaVehiculo = new HashMap<Integer, String>();
	private String selectedCategoriaVehic;

	private List<SelectItem> lstMarcaVehiculo = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRvMarca = new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIRvMarca = new HashMap<Integer, String>();
	private String selectedMarcaVehic;

	private List<SelectItem> lstModelo = new ArrayList<SelectItem>();
	private HashMap<String, RvModeloVehiculoPK> mapRvModelo = new HashMap<String, RvModeloVehiculoPK>();
	private HashMap<RvModeloVehiculoPK, String> mapIRvModelo = new HashMap<RvModeloVehiculoPK, String>();
	private String selectedModeloVehic;

	private List<SelectItem> lstCondicionVehic = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRvCondicionVehiculo = new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIRvCondicionVehiculo = new HashMap<Integer, String>();
	private String selectedCondicionVehic;

	private List<SelectItem> lstTipoMotor = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRvTipoMotor = new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIRvTipoMotor = new HashMap<Integer, String>();
	private String selectedTipoMotorVehic;

	private List<SelectItem> lstCarroceria = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRvCarroceria = new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIRvCarroceria = new HashMap<Integer, String>();
	private String selectedCarroceriaVehic;

	private List<SelectItem> lstTransmision = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRvTransmision = new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIRvTransmision = new HashMap<Integer, String>();
	private String selectedTransmisionVehic;

	private List<SelectItem> lstTraccion = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRvTraccion = new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIRvTraccion = new HashMap<Integer, String>();
	private String selectedTraccionVehic;

	private String pesoBrutoVehic;
	private String cilindrosVehic;

	// Datos adquisición
	private List<SelectItem> lstMotivoDescargo = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRvMotivoDescargo = new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIRvMotivoDescargo = new HashMap<Integer, String>();
	private String selectedMotivoDescargo;
	private BigDecimal valAdqSoles;

	// Datos de transferentes
	private List<SelectItem> lstNotarias = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapGnNotaria = new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIGnNotaria = new HashMap<Integer, String>();
	private String selectedNotaria;

	private List<BuscarPersonaDTO> lstTransferentes = new ArrayList<BuscarPersonaDTO>();

	// Datos de anexos
	private List<DocumentoSustentatorioDTO> lstDocSusten = new ArrayList<DocumentoSustentatorioDTO>();

	private List<AnexosDeclaVehicDTO> lstAnexos = new ArrayList<AnexosDeclaVehicDTO>();

	private PreliminarDTO pre;

	// Controller
	private boolean datosCorrectos = false;
	private boolean djGeneradoCorrect = false;
	private int djvehicularId = 0;
	private RvDjvehicular oldDjv = null;
	private String ACCION;
	
	private String glosa;
	private Date fechaDescargo = new Date();
	
	public DescargoVehicularManaged() {
		getSessionManaged().setLinkRegresar(
				"/sisat/vehicular/historicovehiculodj.xhtml");
	}

	@PostConstruct
	public void init() {
		inicioBasicos();
		cargarDatos();
	}

	private void cargarDatos() {
		try {
			Object obj = getSessionMap().get("descargovehicular.djvId");
			if (obj != null) {
				int djvId = Integer.parseInt(String.valueOf(obj));
				
				//DjBase
				oldDjv = vehicularBo.findDjVehicularById(djvId);
				
				numeroDecla = null;
				//DESCARGO
				selectedMotivoDecla = mapIRvMotivoDecla.get(Integer.parseInt(getMsg("param.rv.motivodecla.descargo")));
				
				estadoDecla = oldDjv.getEstado();
				
				
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.YEAR, oldDjv.getAnnoAfectacion());
				fechaDecla = cal.getTime(); 
				anioDecla = oldDjv.getAnnoDeclaracion();

				cargarDatosVehiculo(oldDjv.getVehiculoId());
				
				/** Cargando data para completar la DJ descargo */
				if(oldDjv.getFechaDescargo() != null){
					fechaDescargo = new Date(oldDjv.getFechaDescargo().getTime());
				}				

				datosCorrectos = true;
				

				ACCION = String.valueOf(getSessionMap().get("descargovehicular.accion"));
				
				if (ACCION.equals(VehicularCns.ACCION_DESCARGOV_PEND)) {
					/** DJ lista para la confirmacion de descargo de vehicular*/
					getSessionManaged().setLinkRegresar("/sisat/vehicular/buscarvehicular.xhtml");
					
				} else if (ACCION.equals(VehicularCns.ACCION_DESCARGOV_VIEW)) {
					
					numeroDecla = oldDjv.getDjvehicularNro();
					selectedMotivoDescargo = mapIRvMotivoDescargo.get(oldDjv.getRvMotivoDescargo().getMotivoDescargoId());
					selectedNotaria = mapIGnNotaria.get(oldDjv.getNotariaId());
					lstTransferentes = vehicularBo.findTransferentes(oldDjv.getDjvehicularId());				
					lstAnexos = vehicularBo.findAnexos(oldDjv.getDjvehicularId());
				}
			}
		} catch (Exception ex) {
			// TODO: Controller exception
			System.out.println("ERROR: " + ex);
		}
	}

	private void cargarDatosVehiculo(int vehiculoId) {
		
		RvVehiculo ve = vehicularBo.findVehiculoById(vehiculoId);
		
		placaVehic = ve.getPlaca();
		nroMotorVehic = ve.getNumMotor();
		fechaPrimeraInsReg = ve.getFechaInsRegistros();
		anioFabricVehic = String.valueOf(ve.getAnnoFabricacion());
		selectedClaseVehic = mapIRvClaseVehiculo.get(ve.getClaseVehiculoId());
		selectedCategoriaVehic = mapIRvCategoriaVehiculo.get(ve.getCategoriaVehiculoId());
		
		fillMarcaVehic();
		selectedMarcaVehic = mapIRvMarca.get(ve.getMarcaVehiculoId());
		lstModelo = new ArrayList<SelectItem>();
		selectedModeloVehic = null;
		fillModelo();
		
		RvModeloVehiculoPK id = new RvModeloVehiculoPK();
		
		id.setCategoriaVehiculoId(ve.getCategoriaVehiculoId());
		id.setMarcaVehiculoId(ve.getMarcaVehiculoId());
		id.setModeloVehiculoId(ve.getModeloVehiculoId());
		
		selectedModeloVehic = mapIRvModelo.get(id);
		selectedTipoMotorVehic = mapIRvTipoMotor.get(ve.getTipoMotorId());
		selectedCarroceriaVehic = mapIRvCarroceria.get(ve.getTipoCarroceriaId());
		selectedTransmisionVehic = mapIRvTransmision.get(ve.getTipoTransmisionId());
		selectedTraccionVehic = mapIRvTraccion.get(ve.getTipoTraccionId());
		pesoBrutoVehic = ve.getPesoBruto() == 0 ? null : String.valueOf(ve.getPesoBruto());
		cilindrosVehic = ve.getNumCilindros() == 0 ? null : String.valueOf(ve.getNumCilindros());
		selectedCondicionVehic = mapIRvCondicionVehiculo.get(ve.getCondicionVehiculoId());
	}

	public BuscarPersonaManaged getBuscarPersonaManaged() {
		return (BuscarPersonaManaged) getManaged("buscarPersonaManaged");
	}

	private void fillMarcaVehic() {
		lstMarcaVehiculo = new ArrayList<SelectItem>();
		try {
			List<RvMarca> lst3 = new ArrayList<RvMarca>();
			int categId = -1;

			if (selectedCategoriaVehic != null) {
				categId = mapRvCategoriaVehiculo.get(selectedCategoriaVehic);
			}

			lst3 = vehicularBo.findRvMarca(categId);

			Iterator<RvMarca> it3 = lst3.iterator();
			while (it3.hasNext()) {
				RvMarca obj3 = it3.next();
				lstMarcaVehiculo.add(new SelectItem(obj3.getDescripcion()));
				mapRvMarca
						.put(obj3.getDescripcion(), obj3.getMarcaVehiculoId());
				mapIRvMarca.put(obj3.getMarcaVehiculoId(),
						obj3.getDescripcion());
			}
		} catch (Exception ex) {
			// TODO : Controller exception
		}
		selectedMarcaVehic = null;
	}

	private void fillModelo() {
		lstModelo = new ArrayList<SelectItem>();

		Integer categId = mapRvCategoriaVehiculo.get(selectedCategoriaVehic);
		Integer marcaId = mapRvMarca.get(selectedMarcaVehic);

		try {
			if (categId != null && marcaId != null) {
				List<RvModeloVehiculo> lst = new ArrayList<RvModeloVehiculo>();
				lst = vehicularBo.getAllRvModeloVehiculo(marcaId, categId);
				Iterator<RvModeloVehiculo> it = lst.iterator();
				while (it.hasNext()) {
					RvModeloVehiculo obj = it.next();
					lstModelo.add(new SelectItem(obj.getDescripcion()));
					mapRvModelo.put(obj.getDescripcion(), obj.getId());
					mapIRvModelo.put(obj.getId(), obj.getDescripcion());
				}
			}
		} catch (Exception ex) {
			// TODO : Controller exception
			System.out.println("EXCEPCION: " + ex);
		}
		selectedModeloVehic = null;
	}

	public void agregarTransferente(ActionEvent ev) {
		getBuscarPersonaManaged().setPantallaUso(ReusoFormCns.DESCARGO_VEHICULAR);
		getBuscarPersonaManaged().setDestinoRefresh("tblTransferentes");
	}

	/**
	 * Verifica que no se agregue un adquiriente que ya ha sido agregado y
	 * siempre sea diferente al contribuyente declarante
	 * 
	 * @param personaId
	 *            Identificador de persona a agregar
	 * @return
	 */
	public boolean existeTransfEnLista(int personaId) {
		boolean existe = false;
		int max = lstTransferentes.size();
		for (int i = 0; i < max; i++) {
			if (lstTransferentes.get(i).getPersonaId() == personaId) {
				existe = true;
				break;
			}
		}

		if (getSessionManaged().getContribuyente().getPersonaId().intValue() == personaId) {
			existe = true;
		}
		return existe;
	}

	// public void igualarPorcentajes() {
	// try {
	// int max = lstTransferentes.size();
	// if (max == 1) {
	// lstTransferentes.get(0).setPorcentajeDescargo(
	// new BigDecimal("100"));
	// } else if (max > 1) {
	// lstTransferentes.get(0).setPorcentajeDescargo(BigDecimal.ZERO);
	// }
	// // BigDecimal porc = new BigDecimal("100");
	// // porc = porc.divide(new BigDecimal(max), 2, RoundingMode.HALF_UP);
	// // for (int i = 0; i < max; i++) {
	// // lstTransferentes.get(i).setPorcentajeDescargo(porc);
	// // }
	// } catch (Exception ex) {
	// // TODO : Controller exception
	// System.out.println("ERROR: " + ex);
	// }
	// }

	public void eliminarTransfDeLista(ActionEvent ev) {
		try {
			UIComponent comp = ev.getComponent().getParent().getParent();
			if (comp != null) {
				UIData uiData = (UIData) comp;
				BuscarPersonaDTO bpd = (BuscarPersonaDTO) uiData.getRowData();
				lstTransferentes.remove(bpd);
				// igualarPorcentajes();
			}
		} catch (Exception ex) {
			// TODO : Controller exception
		}
	}

	public void mostrarPanelDocSustent(ActionEvent ev) {
		lstAnexos = new ArrayList<AnexosDeclaVehicDTO>();
		for (DocumentoSustentatorioDTO dsd : lstDocSusten) {
			dsd.setGlosa(null);
			dsd.setSelected(false);
		}
	}

	public void agregarAnexos(ActionEvent ev) {
		try {
			for (DocumentoSustentatorioDTO dsd : lstDocSusten) {
				if (dsd.isSelected()) {
					// INICIO ITANTAMANGO
					lstAnexos.add(new AnexosDeclaVehicDTO(dsd
							.getDocSustentatorioId(), dsd.getDescripcion(), dsd
							.getGlosa(), null, null));
					// FIN ITANTAMANGO
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void eliminarAnexoDeLista(ActionEvent ev) {
		try {
			UIComponent comp = ev.getComponent().getParent().getParent();
			if (comp != null) {
				UIData uiData = (UIData) comp;
				AnexosDeclaVehicDTO adv = (AnexosDeclaVehicDTO) uiData
						.getRowData();
				lstAnexos.remove(adv);
			}
		} catch (Exception ex) {
			// TODO : Controller exception
		}
	}

	public void verPreview(ActionEvent ev) {
		if (validaDatosNulos()) {
			if (validaLogicaNegocio()) {
				cargaDatosPreliminar();
				datosCorrectos = true;
				estadoDecla = Constante.ESTADO_PENDIENTE;
				addInfoMessage("Datos correctos");// Required
			}
		}
	}

	private void cargaDatosPreliminar() {
		pre = new PreliminarDTO();
		pre.setNumeroDecla(numeroDecla);
		pre.setMotivoDecla(selectedMotivoDecla);
		pre.setFechaDecla(fechaDecla);
		pre.setPlacaVehic(placaVehic);
		pre.setNroMotorVehic(nroMotorVehic);
		pre.setFechaPrimeraInsReg(fechaPrimeraInsReg);
		pre.setFechaAdqui(fechaAdquisicion);
		pre.setAnioFabric(anioFabricVehic);
		pre.setCategoriaVehic(selectedCategoriaVehic);
		pre.setMarcaVehic(selectedMarcaVehic);
		pre.setModeloVehic(selectedModeloVehic);
		pre.setClaseVehic(selectedClaseVehic);
		pre.setTipoAdqui(selectedMotivoDescargo);
		pre.setUsuario(getSessionManaged().getUsuarioLogIn().getNombreUsuario());
		pre.setFechaDescargo(fechaDescargo);
		pre.setGlosa(glosa);
		
		getSessionMap().put("preliminarDescargoDTO", pre);
		getSessionMap().put("lstAdquirientes", lstTransferentes);
		getSessionMap().put("lstAnexosDescargo", lstAnexos);
	}

	public void generarDJ(ActionEvent ev) {
		if (validaDatosNulos()) {
			if (validaLogicaNegocio()) {
				try {
					
					/** INICIO - Creando la DJ de descargo para el contribuyente actual */
					// Create objects
					RvDjvehicular djvehicular = new RvDjvehicular();
					if (ACCION.equals(VehicularCns.ACCION_DESCARGOV_PEND)) {
						djvehicular = oldDjv;
					}					
					
					//tipoTracccionId	
					if (!selectedTraccionVehic.isEmpty()) {
						int tipoTracId = mapRvTraccion.get(selectedTraccionVehic);
						djvehicular.setTipoTraccionId(tipoTracId);
					}
					//tipoTransmId
					if (!selectedTransmisionVehic.isEmpty()) {
						int tipoTransmId = mapRvTransmision.get(selectedTransmisionVehic);
						djvehicular.setTipoTransmisionId(tipoTransmId);
					}
					//TipoMotorId
					if (!selectedTipoMotorVehic.isEmpty()) {
						int tipoMotId = mapRvTipoMotor.get(selectedTipoMotorVehic);
						djvehicular.setTipoMotorId(tipoMotId);
					}
					//tipo_moneda_id
					djvehicular.setTipoMonedaId(oldDjv.getTipoMonedaId()); // soles					
					//tipo_propiedad_id
					djvehicular.setTipoPropiedadId(1);					
					//marca_vehiculo_id
					int marcId = mapRvMarca.get(selectedMarcaVehic);
					djvehicular.setMarcaVehiculoId(marcId);					
					//categoria_vehiculo_id
					int categId = mapRvCategoriaVehiculo.get(selectedCategoriaVehic);
					djvehicular.setCategoriaVehiculoId(categId);					
					//modelo_vehiculo_id
					RvModeloVehiculoPK modId = mapRvModelo.get(selectedModeloVehic);					
					djvehicular.setModeloVehiculoId(modId.getModeloVehiculoId());
					//num motor
					djvehicular.setNumMotor(nroMotorVehic);					
					//anio_fabricacion
					djvehicular.setAnnoFabricacion(Integer.parseInt(anioFabricVehic));					
					//fechaInsRegistros
					djvehicular.setFechaInsRegistros(DateUtil.dateToSqlTimestamp(fechaPrimeraInsReg));					
					//num_cilindros
					if (cilindrosVehic != null && !cilindrosVehic.isEmpty()) {
						djvehicular.setNumCilindros(Integer.parseInt(cilindrosVehic));
					}
					// peso_bruto
					if (pesoBrutoVehic != null && !pesoBrutoVehic.isEmpty()) {
						djvehicular.setPesoBruto(Integer.parseInt(pesoBrutoVehic));
					}
					//tipo_carroceria_id
					Integer tipoCarroceriaId = mapRvCarroceria.get(selectedCarroceriaVehic);
					if(tipoCarroceriaId != null){
						djvehicular.setTipoCarroceriaId(tipoCarroceriaId);
					}					
					//clase_vehiculo_id
					int claseVehiId = mapRvClaseVehiculo.get(selectedClaseVehic);
					djvehicular.setClaseVehiculoId(claseVehiId);					
					//persona_id
					djvehicular.setPersonaId(getSessionManaged().getContribuyente().getPersonaId());					
					//rv_motivo_declaracion_id
					int motivoDeclaId = mapRvMotivoDecla.get(selectedMotivoDecla);
					djvehicular.setRvMotivoDeclaracionId(motivoDeclaId);					
					//condicion_vehiculo_id
					int condVehicId = mapRvCondicionVehiculo.get(selectedCondicionVehic);
					djvehicular.setCondicionVehiculoId(condVehicId);					
					//notaria_id
					int notarId = mapGnNotaria.get(selectedNotaria);
					djvehicular.setNotariaId(notarId);					
					//motivo_descargo_id
					int motDescargoId = mapRvMotivoDescargo.get(selectedMotivoDescargo);
					RvMotivoDescargo md = new RvMotivoDescargo();
					md.setMotivoDescargoId(motDescargoId);					
					djvehicular.setRvMotivoDescargo(md);					
					//fecha_declaracion
					djvehicular.setFechaDeclaracion(DateUtil.getCurrentDate());					
					//anno_declaracion
					djvehicular.setAnnoDeclaracion(anioDecla);	// verificar si colocamos el anho de descargo				
					//usuario_id
					djvehicular.setUsuarioId(getSessionManaged().getUsuarioLogIn().getUsuarioId());					
					//estado
					djvehicular.setEstado(Constante.ESTADO_ACTIVO);					
					//fecha_actualizacion					
					djvehicular.setFechaActualizacion(DateUtil.getCurrentDate());					
					//fecha_registro
					djvehicular.setFechaRegistro(DateUtil.getCurrentDate());					
					//terminal
					djvehicular.setTerminal(getSessionManaged().getTerminalLogIn());					
					//glosa
					djvehicular.setGlosa(glosa);					
					/** El año de descargo esta contenido en la variable fecha de adquision*/
					djvehicular.setAnnoAfectacion(DateUtil.getAnhoFecha(fechaDescargo));					
					//fecha_descargo
					djvehicular.setFechaDescargo(DateUtil.dateToSqlTimestamp(fechaDescargo));

					// val_adq_soles
					djvehicular.setValAdqSoles(this.getValAdqSoles());
					
					if (oldDjv != null) {
						// tipo_adquisicion_id
						djvehicular.setTipoAdquisicionId(oldDjv.getTipoAdquisicionId());
						// num_tarjeta_propiedad
						djvehicular.setNumTarjetaPropiedad(oldDjv.getNumTarjetaPropiedad());
						// fecha_adquisicion
						djvehicular.setFechaAdquisicion(oldDjv.getFechaAdquisicion());
						// djvehicular_id
						djvehicular.setVehiculoId(oldDjv.getVehiculoId());
						
						djvehicular.setAnnoIniAfectacion(oldDjv.getAnnoIniAfectacion());
						djvehicular.setAnnoFinAfectacion(oldDjv.getAnnoFinAfectacion());

						if (ACCION.equals(VehicularCns.ACCION_DESCARGOV_PEND)) {
							/** Actualizando la DJ pendiente Descargo*/
							djvehicularId = vehicularBo.guardarPendienteDescargoDJVehicular(djvehicular, lstTransferentes, lstAnexos);
						}else{
							// Guardando y generando las DJ's de Descargo
							djvehicularId = vehicularBo.guardarDescargoDJVehicular(oldDjv, djvehicular, lstTransferentes, lstAnexos);
						}
					}
					
					/** FIN - Creando la DJ de descargo para el contribuyente actual */
					/*************************************************************************************************/
					
					if (djvehicularId != 0) {
						// Necessary for historicovehiculodj.jsf
						getSessionMap().put("hitoricovehiculodj.djvId",djvehicularId);
						
//						vehicularBo.cambiarFlagDjAnio(djvehicularId,Constante.ESTADO_ACTIVO);
						
//						vehicularBo.cambiarFlagDjAnio(oldDjv.getDjvehicularId(),Constante.ESTADO_INACTIVO);						
//						vehicularBo.cambiarEstadoDjv(oldDjv.getDjvehicularId(),Constante.ESTADO_INACTIVO);
							
						// Guardar Adquirientes
//						RvTransferenciaPropiedad ad = new RvTransferenciaPropiedad();
//						ad.setDjvehicularId(djvehicularId);
//						ad.setTipo("A");
//						ad.setFechaActualizacion(DateUtil.dateToSqlTimestamp(new Date()));
//						ad.setUsuarioId(getSessionManaged().getUsuarioLogIn().getUsuarioId());
//						ad.setFechaRegistro(DateUtil.dateToSqlTimestamp(new Date()));
//						ad.setEstado("1");
//						ad.setTerminal(getSessionManaged().getTerminalLogIn());
//						vehicularBo.guardarTransferenteAdquiriente(lstTransferentes, ad);
//						
						// Anexos
//						RvSustentoVehicular sv = new RvSustentoVehicular();
//						RvSustentoVehicularPK svp = new RvSustentoVehicularPK();
//						svp.setDjvehicularId(djvehicularId);
//						sv.setId(svp);
//						sv.setFechaRegistro(DateUtil.dateToSqlTimestamp(new Date()));
//						sv.setUsuarioId(getSessionManaged().getUsuarioLogIn().getUsuarioId());
//						sv.setTerminal(getSessionManaged().getTerminalLogIn());
//						vehicularBo.guardarDocAnexosDjVehicular(lstAnexos, sv);
						
						// desde aqui capturamos todos los datos anteriores y le
						// asignamos al comprador generandole su DJ pendiente
						// descargo
//						RvDjvehicular djveh = new RvDjvehicular();
//						//tipo_traccion_id
//						if (!selectedTraccionVehic.isEmpty()) {
//							int tipoTracId = mapRvTraccion.get(selectedTraccionVehic);
//							djveh.setTipoTraccionId(tipoTracId);
//						}
//						//tipo_transmision_id
//						if (!selectedTransmisionVehic.isEmpty()) {
//							int tipoTransmId = mapRvTransmision.get(selectedTransmisionVehic);
//							djveh.setTipoTransmisionId(tipoTransmId);
//						}
//						//tipo_motor_id
//						if (!selectedTipoMotorVehic.isEmpty()) {
//							int tipoMotId = mapRvTipoMotor.get(selectedTipoMotorVehic);
//							djveh.setTipoMotorId(tipoMotId);
//						}
//						//marca_vehiculo_id
//						int marcaId = mapRvMarca.get(selectedMarcaVehic);
//						djveh.setMarcaVehiculoId(marcaId);
//
//						//categoria_vehiculo_id
//						int categoId = mapRvCategoriaVehiculo.get(selectedCategoriaVehic);
//						djveh.setCategoriaVehiculoId(categoId);
//						
//						//modelo_vehiculo_id
//						djveh.setModeloVehiculoId(modId.getModeloVehiculoId());
//						
//						//num_motor
//						djveh.setNumMotor(nroMotorVehic);
//						
//						//anno_fabricacion
//						djveh.setAnnoFabricacion(Integer.parseInt(anioFabricVehic));
//						
//						//fecha_ins_registros
//						djveh.setFechaInsRegistros(DateUtil.dateToSqlTimestamp(fechaPrimeraInsReg));
//						
//						//num_cilindros
//						if (cilindrosVehic != null && !cilindrosVehic.isEmpty()) {
//							djveh.setNumCilindros(Integer.parseInt(cilindrosVehic));
//						}
//
//						//peso_bruto
//						if (pesoBrutoVehic != null && !pesoBrutoVehic.isEmpty()) {
//							djveh.setPesoBruto(Integer.parseInt(pesoBrutoVehic));
//						}
//						
//						//tipo_carroceria_id
//						tipoCarroceriaId = mapRvCarroceria.get(selectedCarroceriaVehic);
//						if(tipoCarroceriaId != null){
//							djveh.setTipoCarroceriaId(tipoCarroceriaId);
//						}
//						
//						
//						// clase_vehiculo_id 
//						int classVehiId = mapRvClaseVehiculo.get(selectedClaseVehic);
//						djveh.setClaseVehiculoId(classVehiId);
//						
						
//						/** Asignamos el adquiriente */
						
//						if(lstTransferentes!=null&&lstTransferentes.size()!=0){
//							//persona_id
//							djveh.setPersonaId(lstTransferentes.get(0).getPersonaId());
//							
//							//tipo_adquisicion_id
//							djveh.setTipoAdquisicionId(1);
//							
//							//rv_motivo_declaracion_id
//							djveh.setRvMotivoDeclaracionId(1);
//							
//							//condicion_vehiculo_id
//							int condiVehicId = mapRvCondicionVehiculo.get(selectedCondicionVehic);
//							djveh.setCondicionVehiculoId(condiVehicId);
//							
//							//notaria_id
//							int notariId = mapGnNotaria.get(selectedNotaria);
//							djveh.setNotariaId(notariId);
//							
//							//vehiculo_id
//							djveh.setVehiculoId(oldDjv.getVehiculoId());
//							
//							//motivo_descargo_id NULL
//							
//							//fecha_declaracion
//							djveh.setFechaDeclaracion(DateUtil.getCurrentDate());
//							
//							//anno_ini_afectacion	anno_fin_afectacion null
//							
//							//anno_afectacion
//							djveh.setAnnoDeclaracion(anioDecla);
//													
//							djveh.setUsuarioId(getSessionManaged().getUsuarioLogIn().getUsuarioId());
//							
//							//fecha_adquisicion
//							/** Fecha de adquisicion o fecha de venta */
//							//djveh.setFechaAdquisicion(formatoTime.dateToSqlTimestamp(getFechaAdquisicion()));
//							djveh.setFechaAdquisicion(DateUtil.dateToSqlTimestamp(fechaDescargo));
//
//							//val_adq_soles
//							djveh.setValAdqSoles(getValAdqSoles());
//							
//							//tipo_moneda_id
//							/** solo esta considerado para soles */
//							djveh.setTipoMonedaId(1);
//							
//							//tipo_propiedad_id
//							djveh.setTipoPropiedadId(1);
//							djveh.setPorcPropiedad(BigDecimal.valueOf(100.00));
//							
//							djveh.setFechaRegistro(DateUtil.getCurrentDate());
//							djveh.setFechaActualizacion(DateUtil.getCurrentDate());
//							
//							djveh.setEstado(Constante.ESTADO_PENDIENTE);
//							djveh.setFlagDjAnno(Constante.ESTADO_ACTIVO);
//							
//							djveh.setTerminal(getSessionManaged().getTerminalLogIn());
//							
//							//djveh.setDjvehicularPrevioId(djvehicularId);
//							int idDjComprador = vehicularBo.guardarDJVehicular(djveh);
//							
//							// GUARDAR TRANSFERENTE								
//							RvTransferenciaPropiedad tp = new RvTransferenciaPropiedad();
//							
//							/**Seleccionamos la nueva dj ingresada*/							
//							tp.setDjvehicularId(idDjComprador);							
//							tp.setTipo("T");
//							tp.setPersonaId(getSessionManaged().getContribuyente().getPersonaId());
//							tp.setFechaActualizacion(DateUtil.dateToSqlTimestamp(new Date()));
//							tp.setUsuarioId(getSessionManaged().getUsuarioLogIn().getUsuarioId());
//							tp.setFechaRegistro(DateUtil.dateToSqlTimestamp(new Date()));
//							tp.setEstado("1");
//							tp.setTerminal(getSessionManaged().getTerminalLogIn());
//							vehicularBo.guardarTransferente(tp);
//							
//						}
						// Mensaje de éxito
						addInfoMessage(getMsg("rv.djgeneradasatisfactoriamente"));
						djGeneradoCorrect = true;
					}
 				} catch (Exception ex) {
					addErrorMessage(getMsg("rv.djnogenerada").concat(". ").concat(ex.getMessage()));					
				}
			}
		}
	}

	public void nuevoAdquiriente() {
		String destinoRefresh = FacesUtil.getRequestParameter("destinoRefresh");
		RegistroPersonaManaged registroPersonaManaged = (RegistroPersonaManaged) getManaged("registroPersonaManaged");
		registroPersonaManaged.setPantallaUso(ReusoFormCns.DESCARGO_VEHICULAR);
		registroPersonaManaged.setDestinoRefresh(destinoRefresh);
		registroPersonaManaged.salirPersonaBasico();
	}

	public void changeDatosCorrectos(ActionEvent ev) {
		datosCorrectos = true;
	}

	private boolean validaDatosNulos() {
		boolean valido = true;
		try {
			if (selectedMotivoDescargo.isEmpty()) {
				valido = false;
				addErrorMessage(getMsg("rv.motivodescargo") + ": " + getMsg("rv.campovacioverifique"));
			}
			if (selectedNotaria.isEmpty()) {
				valido = false;
				addErrorMessage(getMsg("rv.notaria") + ": " + getMsg("rv.campovacioverifique"));
			}
			// if (!selectedMotivoDescargo.isEmpty()) {
			// int motDescargoId = mapRvMotivoDescargo
			// .get(selectedMotivoDescargo);
			// if (motDescargoId != VehicularCns.MOT_DESCARGO_DESTRUC_SINIESTRO)
			// {
			// if (lstTransferentes == null || lstTransferentes.isEmpty()) {
			// addErrorMessage(getMsg("rv.debeingresaradquiriente"));
			// valido = false;
			// }
			// }
			// }
			if (lstAnexos.isEmpty()) {
				valido = false;
				addErrorMessage(getMsg("rv.debeingresardocanexo"));
			}
		} catch (Exception ex) {
			valido = false;
			addErrorMessage(getMsg("rv.errorvalidadatos"));
		}
		return valido;
	}

	private boolean validaLogicaNegocio() {
		boolean valido = true;
		try {
			// Fecha adquisición <= Fecha declaración <= Fecha actual
			// (sistema)
			Calendar fechaDec = new GregorianCalendar();
			fechaDec.setTime(fechaDecla);
			Long fechaDecL = fechaDec.getTimeInMillis();

			Calendar fechaSis = new GregorianCalendar();
			fechaSis.setTime(new Date());
			Long fechaSisL = fechaSis.getTimeInMillis();

			if (fechaDecL > fechaSisL) {
				addErrorMessage(getMsg("rv.fechadeclamayorfechactual"));
				valido = false;
			}
		} catch (Exception ex) {
			valido = false;
			addErrorMessage(getMsg("rv.errorvalidadatos"));
		}
		return valido;
	}

	public void inicioBasicos() {
		try {
			// Datos declaración
			List<RvMotivoDescargo> lstAd = new ArrayList<RvMotivoDescargo>();
			lstAd = vehicularBo.getAllRvMotivoDescargo();
			Iterator<RvMotivoDescargo> itAd = lstAd.iterator();
			while (itAd.hasNext()) {
				RvMotivoDescargo objAd = itAd.next();
				lstMotivoDescargo.add(new SelectItem(objAd.getDescripcion()));
				mapRvMotivoDescargo.put(objAd.getDescripcion(),
						objAd.getMotivoDescargoId());
				mapIRvMotivoDescargo.put(objAd.getMotivoDescargoId(),
						objAd.getDescripcion());
			}

			// Motivo decla
			List<RvMotivoDeclaracion> lstMd = new ArrayList<RvMotivoDeclaracion>();
			lstMd = vehicularBo.getAllRvMotivoDeclaracion();
			Iterator<RvMotivoDeclaracion> itMd = lstMd.iterator();
			while (itMd.hasNext()) {
				RvMotivoDeclaracion objMd = itMd.next();
				lstMotivoDecla.add(new SelectItem(objMd.getDescripcion()));
				mapRvMotivoDecla.put(objMd.getDescripcion(),
						objMd.getMotivoDeclaracionId());
				mapIRvMotivoDecla.put(objMd.getMotivoDeclaracionId(),
						objMd.getDescripcion());
			}

			// Clase vehículo
			List<RvClaseVehiculo> lst = new ArrayList<RvClaseVehiculo>();
			lst = vehicularBo.getAllRvClaseVehiculo();
			Iterator<RvClaseVehiculo> it = lst.iterator();
			while (it.hasNext()) {
				RvClaseVehiculo obj = it.next();
				lstClaseVehiculo.add(new SelectItem(obj.getDescripcion()));
				mapRvClaseVehiculo.put(obj.getDescripcion(),
						obj.getClaseVehiculoId());
				mapIRvClaseVehiculo.put(obj.getClaseVehiculoId(),
						obj.getDescripcion());
			}

			// Categoría vehículo
			List<RvCategoriaVehiculo> lst2 = new ArrayList<RvCategoriaVehiculo>();
			lst2 = vehicularBo.getAllRvCategoriaVehiculo();
			Iterator<RvCategoriaVehiculo> it2 = lst2.iterator();
			while (it2.hasNext()) {
				RvCategoriaVehiculo obj2 = it2.next();
				lstCategoriaVehiculo.add(new SelectItem(obj2.getDescripcion()));
				mapRvCategoriaVehiculo.put(obj2.getDescripcion(),
						obj2.getCategoriaVehiculoId());
				mapIRvCategoriaVehiculo.put(obj2.getCategoriaVehiculoId(),
						obj2.getDescripcion());

			}

			// Condicion vehículo
			List<RvCondicionVehiculo> lstCV = new ArrayList<RvCondicionVehiculo>();
			lstCV = vehicularBo.getAllRvCondicionVehiculo();
			Iterator<RvCondicionVehiculo> itCV = lstCV.iterator();
			while (itCV.hasNext()) {
				RvCondicionVehiculo objCV = itCV.next();
				lstCondicionVehic.add(new SelectItem(objCV.getDescripcion()));
				mapRvCondicionVehiculo.put(objCV.getDescripcion(),
						objCV.getCondicionVehiculoId());
				mapIRvCondicionVehiculo.put(objCV.getCondicionVehiculoId(),
						objCV.getDescripcion());
			}
			selectedCondicionVehic = mapIRvCondicionVehiculo.get(Integer
					.parseInt(getMsg("param.rv.condicionvehic.ninguno")));

			// Carrocería
			List<RvTipoCarroceria> lst4 = new ArrayList<RvTipoCarroceria>();
			lst4 = vehicularBo.getAllRvTipoCarroceria();
			Iterator<RvTipoCarroceria> it4 = lst4.iterator();
			while (it4.hasNext()) {
				RvTipoCarroceria obj4 = it4.next();
				lstCarroceria.add(new SelectItem(obj4.getDescripcion()));
				mapRvCarroceria.put(obj4.getDescripcion(),
						obj4.getTipoCarroceriaId());
				mapIRvCarroceria.put(obj4.getTipoCarroceriaId(),
						obj4.getDescripcion());
			}

			// Transmisión
			List<RvTipoTransmision> lst5 = new ArrayList<RvTipoTransmision>();
			lst5 = vehicularBo.getAllRvTipoTransmision();
			Iterator<RvTipoTransmision> it5 = lst5.iterator();
			while (it5.hasNext()) {
				RvTipoTransmision obj5 = it5.next();
				lstTransmision.add(new SelectItem(obj5.getDescripcion()));
				mapRvTransmision.put(obj5.getDescripcion(),
						obj5.getTipoTransmisionId());
				mapIRvTransmision.put(obj5.getTipoTransmisionId(),
						obj5.getDescripcion());
			}

			// Tracción
			List<RvTipoTraccion> lst6 = new ArrayList<RvTipoTraccion>();
			lst6 = vehicularBo.getAllRvTipoTraccion();
			Iterator<RvTipoTraccion> it6 = lst6.iterator();
			while (it6.hasNext()) {
				RvTipoTraccion obj6 = it6.next();
				lstTraccion.add(new SelectItem(obj6.getDescripcion()));
				mapRvTraccion.put(obj6.getDescripcion(),
						obj6.getTipoTraccionId());
				mapIRvTraccion.put(obj6.getTipoTraccionId(),
						obj6.getDescripcion());
			}

			// Tipo motor
			List<RvTipoMotor> lst7 = new ArrayList<RvTipoMotor>();
			lst7 = vehicularBo.getAllRvTipoMotor();
			Iterator<RvTipoMotor> it7 = lst7.iterator();
			while (it7.hasNext()) {
				RvTipoMotor obj7 = it7.next();
				lstTipoMotor.add(new SelectItem(obj7.getDescripcion()));
				mapRvTipoMotor
						.put(obj7.getDescripcion(), obj7.getTipoMotorId());
				mapIRvTipoMotor.put(obj7.getTipoMotorId(),
						obj7.getDescripcion());
			}

			List<GnNotaria> lstN = new ArrayList<GnNotaria>();
			lstN = vehicularBo.getAllGnNotaria();
			Iterator<GnNotaria> itN = lstN.iterator();
			while (itN.hasNext()) {
				GnNotaria objN = itN.next();
				lstNotarias.add(new SelectItem(objN.getNombreNotaria()));
				mapGnNotaria.put(objN.getNombreNotaria(), objN.getNotariaId());
				mapIGnNotaria.put(objN.getNotariaId(), objN.getNombreNotaria());
			}

			lstDocSusten = vehicularBo.getAllRvDocumentoSustentatorio();
			glosa="";
			fechaDescargo=null;
		} catch (Exception ex) {
			// TODO : controller exception
		}
	}

	public void cancelar(ActionEvent ev) {
		if (ACCION.equals(VehicularCns.ACCION_DESCARGOV_PEND)) {
			getSessionManaged().setPage(
					"/sisat/vehicular/buscarvehicular.xhtml");
			sendRedirectPrincipalListener();
		} else {
			getSessionManaged().setPage(
					"/sisat/vehicular/historicovehiculodj.xhtml");
			sendRedirectPrincipalListener();
		}
	}

	public List<SelectItem> getLstClaseVehiculo() {
		return lstClaseVehiculo;
	}

	public List<SelectItem> getLstCategoriaVehiculo() {
		return lstCategoriaVehiculo;
	}

	public List<SelectItem> getLstMarcaVehiculo() {
		return lstMarcaVehiculo;
	}

	public List<SelectItem> getLstCarroceria() {
		return lstCarroceria;
	}

	public List<SelectItem> getLstTransmision() {
		return lstTransmision;
	}

	public List<SelectItem> getLstTraccion() {
		return lstTraccion;
	}

	public List<SelectItem> getLstTipoMotor() {
		return lstTipoMotor;
	}

	public List<SelectItem> getLstModelo() {
		return lstModelo;
	}

	public String getSelectedCondicionVehic() {
		return selectedCondicionVehic;
	}

	public void setSelectedCondicionVehic(String selectedCondicionVehic) {
		this.selectedCondicionVehic = selectedCondicionVehic;
	}

	public List<SelectItem> getLstCondicionVehic() {
		return lstCondicionVehic;
	}

	public Date getFechaPrimeraInsReg() {
		return fechaPrimeraInsReg;
	}

	public void setFechaPrimeraInsReg(Date fechaPrimeraInsReg) {
		this.fechaPrimeraInsReg = fechaPrimeraInsReg;
	}

	public Date getFechaDecla() {
		return fechaDecla;
	}

	public void setFechaDecla(Date fechaDecla) {
		this.fechaDecla = fechaDecla;
	}

	public List<SelectItem> getLstMotivoDecla() {
		return lstMotivoDecla;
	}

	public String getSelectedMotivoDecla() {
		return selectedMotivoDecla;
	}

	public void setSelectedMotivoDecla(String selectedMotivoDecla) {
		this.selectedMotivoDecla = selectedMotivoDecla;
	}

	public String getNumeroDecla() {
		return numeroDecla;
	}

	public void setNumeroDecla(String numeroDecla) {
		this.numeroDecla = numeroDecla;
	}

	public String getAnioDecla() {
		return anioDecla;
	}

	public void setAnioDecla(String anioDecla) {
		this.anioDecla = anioDecla;
	}

	public String getSelectedMotivoDescargo() {
		return selectedMotivoDescargo;
	}

	public void setSelectedMotivoDescargo(String selectedMotivoDescargo) {
		this.selectedMotivoDescargo = selectedMotivoDescargo;
	}

	public List<SelectItem> getLstMotivoDescargo() {
		return lstMotivoDescargo;
	}

	public String getNroMotorVehic() {
		return nroMotorVehic;
	}

	public void setNroMotorVehic(String nroMotorVehic) {
		this.nroMotorVehic = nroMotorVehic;
	}

	public String getPlacaVehic() {
		return placaVehic;
	}

	public void setPlacaVehic(String placaVehic) {
		this.placaVehic = placaVehic;
	}

	public String getAnioFabricVehic() {
		return anioFabricVehic;
	}

	public void setAnioFabricVehic(String anioFabricVehic) {
		this.anioFabricVehic = anioFabricVehic;
	}

	public String getSelectedClaseVehic() {
		return selectedClaseVehic;
	}

	public void setSelectedClaseVehic(String selectedClaseVehic) {
		this.selectedClaseVehic = selectedClaseVehic;
	}

	public String getSelectedCategoriaVehic() {
		return selectedCategoriaVehic;
	}

	public void setSelectedCategoriaVehic(String selectedCategoriaVehic) {
		this.selectedCategoriaVehic = selectedCategoriaVehic;
	}

	public String getSelectedMarcaVehic() {
		return selectedMarcaVehic;
	}

	public void setSelectedMarcaVehic(String selectedMarcaVehic) {
		this.selectedMarcaVehic = selectedMarcaVehic;
	}

	public String getSelectedModeloVehic() {
		return selectedModeloVehic;
	}

	public void setSelectedModeloVehic(String selectedModeloVehic) {
		this.selectedModeloVehic = selectedModeloVehic;
	}

	public String getSelectedTipoMotorVehic() {
		return selectedTipoMotorVehic;
	}

	public void setSelectedTipoMotorVehic(String selectedTipoMotorVehic) {
		this.selectedTipoMotorVehic = selectedTipoMotorVehic;
	}

	public String getSelectedCarroceriaVehic() {
		return selectedCarroceriaVehic;
	}

	public void setSelectedCarroceriaVehic(String selectedCarroceriaVehic) {
		this.selectedCarroceriaVehic = selectedCarroceriaVehic;
	}

	public String getSelectedTransmisionVehic() {
		return selectedTransmisionVehic;
	}

	public void setSelectedTransmisionVehic(String selectedTransmisionVehic) {
		this.selectedTransmisionVehic = selectedTransmisionVehic;
	}

	public String getSelectedTraccionVehic() {
		return selectedTraccionVehic;
	}

	public void setSelectedTraccionVehic(String selectedTraccionVehic) {
		this.selectedTraccionVehic = selectedTraccionVehic;
	}

	public String getPesoBrutoVehic() {
		return pesoBrutoVehic;
	}

	public void setPesoBrutoVehic(String pesoBrutoVehic) {
		this.pesoBrutoVehic = pesoBrutoVehic;
	}

	public String getCilindrosVehic() {
		return cilindrosVehic;
	}

	public void setCilindrosVehic(String cilindrosVehic) {
		this.cilindrosVehic = cilindrosVehic;
	}

	public String getSelectedNotaria() {
		return selectedNotaria;
	}

	public void setSelectedNotaria(String selectedNotaria) {
		this.selectedNotaria = selectedNotaria;
	}

	public List<SelectItem> getLstNotarias() {
		return lstNotarias;
	}

	public List<BuscarPersonaDTO> getLstTransferentes() {
		return lstTransferentes;
	}

	public List<AnexosDeclaVehicDTO> getLstAnexos() {
		return lstAnexos;
	}

	public String getEstadoDecla() {
		return estadoDecla;
	}

	public boolean isDatosCorrectos() {
		return datosCorrectos;
	}

	public void setDatosCorrectos(boolean datosCorrectos) {
		this.datosCorrectos = datosCorrectos;
	}

	public boolean isDjGeneradoCorrect() {
		return djGeneradoCorrect;
	}

	public List<DocumentoSustentatorioDTO> getLstDocSusten() {
		return lstDocSusten;
	}

	public BigDecimal getValAdqSoles() {
		return valAdqSoles;
	}

	public void setValAdqSoles(BigDecimal valAdqSoles) {
		this.valAdqSoles = valAdqSoles;
	}

	public Date getFechaAdquisicion() {
		return fechaAdquisicion;
	}

	public void setFechaAdquisicion(Date fechaAdquisicion) {
		this.fechaAdquisicion = fechaAdquisicion;
	}

	public String getGlosa() {
		return glosa;
	}

	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}

	public Date getFechaDescargo() {
		return fechaDescargo;
	}

	public void setFechaDescargo(Date fechaDescargo) {
		this.fechaDescargo = fechaDescargo;
	}
}
