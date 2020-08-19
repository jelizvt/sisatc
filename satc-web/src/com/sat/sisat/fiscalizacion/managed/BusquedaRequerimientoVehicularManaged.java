package com.sat.sisat.fiscalizacion.managed;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.richfaces.component.html.HtmlComboBox;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.controlycobranza.business.ControlyCobranzaBoRemote;
import com.sat.sisat.controlycobranza.dto.FindCcLote;
import com.sat.sisat.determinacion.exception.DeterminacionVehicularException;
import com.sat.sisat.determinacion.vehicular.calculo.ImpuestoVehicular;
import com.sat.sisat.determinacion.vehicular.dto.DatosNecesariosDeterDTO;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.fiscalizacion.business.FiscalizacionBo;
import com.sat.sisat.fiscalizacion.business.FiscalizacionBoRemote;
import com.sat.sisat.fiscalizacion.dto.DatosNecesariosDeclaracionDTO;
import com.sat.sisat.fiscalizacion.dto.FindInspeccionHistorial;
import com.sat.sisat.persistence.entity.DtTasaVehicular;
import com.sat.sisat.persistence.entity.NoMotivoNotificacion;
import com.sat.sisat.persistence.entity.NoNotificacion;
import com.sat.sisat.persistence.entity.NoNotificador;
import com.sat.sisat.persistence.entity.RvCategoriaVehiculo;
import com.sat.sisat.persistence.entity.RvClaseVehiculo;
import com.sat.sisat.persistence.entity.RvDjvehicular;
import com.sat.sisat.persistence.entity.RvFiscalizacionInspeccion;
import com.sat.sisat.persistence.entity.RvFiscalizacionNotificacion;
import com.sat.sisat.persistence.entity.RvMarca;
import com.sat.sisat.persistence.entity.RvModeloVehiculo;
import com.sat.sisat.persistence.entity.RvModeloVehiculoPK;
import com.sat.sisat.persistence.entity.RvOmisosDetalleVehicular;
import com.sat.sisat.persistence.entity.RvSustentoVehicular;
import com.sat.sisat.persistence.entity.RvSustentoVehicularPK;
import com.sat.sisat.persistence.entity.RvVehiculo;

@ManagedBean
@ViewScoped
public class BusquedaRequerimientoVehicularManaged extends BaseManaged {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	FiscalizacionBoRemote ficalizacionBo;
	@EJB
	ControlyCobranzaBoRemote controlycobranzaBo;

	private FindCcLote findCcLoteItem;
	private Integer lote_id;
	private List<FindInspeccionHistorial> inspeccion = new ArrayList<FindInspeccionHistorial>();
//	private List<FindInspeccionHistorial> lstInspeccion = new ArrayList<FindInspeccionHistorial>();
	private FindInspeccionHistorial detalleInspeccion = new FindInspeccionHistorial();
	private String codicionAdministrado;
	private HtmlComboBox cmbCondicionAdministrado;
	private HashMap<String, Integer> mapNoMotivoNotificacion = new HashMap<String, Integer>();
	private String cmbValueNoMotivoNotificacion;
	private Integer noMotivoNotificacionId;
	private HtmlComboBox cmbNoMotivoNotificacion;
	private List<SelectItem> lstNoMotivoNotificacion = new ArrayList<SelectItem>();

	private List<SelectItem> lstSelectItemsNotificador = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapNotificador = new HashMap<String, Integer>();
	private Integer notificadorId;
	private String cmbNotificador;
	private HtmlComboBox cmbHtmlNotificador;
	private java.util.Date fechaNotificacion;
	private RvFiscalizacionNotificacion notificacion = new RvFiscalizacionNotificacion();
	private Integer tipoAccion = Constante.TIPO_ACCION_NUEVO;
	
	/**Detalle
	 */
	private List<SelectItem> listaAnio = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapAnio = new HashMap<String, Integer>();
	private String cmbAnio;
	private HtmlComboBox cmbxAnio;
	private Integer reqDetId;//Id-Requerimiento-Detalle
	private Integer anioAf;
	private Integer anioFab;
	private Boolean sinImpuesto=false;
	private boolean disableBtnGeneraDj = false;
	private String anioDecla = DateUtil.obtenerAnioActual();
	private Integer vehiculoId = null;
	private Integer djId;
	private boolean enablePanel = false;
	
	/**Clase
	 */
	private String selectedClaseVehic;
	private List<SelectItem> lstClaseVehiculo = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRvClaseVehiculo = new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIRvClaseVehiculo = new HashMap<Integer, String>();
	
	/**Categoria
	 */
	private String selectedCategoriaVehic;
	private List<SelectItem> lstCategoriaVehiculo = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRvCategoriaVehiculo = new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIRvCategoriaVehiculo = new HashMap<Integer, String>();
	
	/**Modelo
	 */
	private List<SelectItem> lstModelo = new ArrayList<SelectItem>();
	private String selectedModeloVehic;
	private HashMap<String, RvModeloVehiculoPK> mapRvModelo = new HashMap<String, RvModeloVehiculoPK>();
	private HashMap<RvModeloVehiculoPK, String> mapIRvModelo = new HashMap<RvModeloVehiculoPK, String>();
	
	/**Marca
	 */
	private List<SelectItem> lstMarcaVehiculo = new ArrayList<SelectItem>();
	private HashMap<String, Integer> mapRvMarca = new HashMap<String, Integer>();
	private HashMap<Integer, String> mapIRvMarca = new HashMap<Integer, String>();
	private String selectedMarcaVehic;
	
	
	public BusquedaRequerimientoVehicularManaged() throws Exception {
	}

	@PostConstruct
	public void init() throws Exception {
		try {
			FindCcLote findCcLoteItem = (FindCcLote) getSessionMap().get(
					"findCcLoteItm1");
			if (findCcLoteItem != null) {
				setFindCcLoteItem(findCcLoteItem);
				setLote_id(findCcLoteItem.getLoteId());
				inspeccion = ficalizacionBo.getAllInspeccionesVehicular(findCcLoteItem.getLoteId());
			} else {
				addErrorMessage("No se encontró información del Lote elegido.");
			}
			List<NoNotificador> lstNotificador = controlycobranzaBo
					.getAllNotificador();
			for (NoNotificador noNotificador : lstNotificador) {
				lstSelectItemsNotificador.add(new SelectItem(noNotificador
						.getApellidosNombres(), String.valueOf(noNotificador
						.getNotificadorId())));
				mapNotificador.put(noNotificador.getApellidosNombres().trim(),
						noNotificador.getNotificadorId());
			}
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void loadTMotivoNotificacionById(ValueChangeEvent event) {
		try {
			HtmlComboBox combo = (HtmlComboBox) event.getComponent();
			String value = combo.getValue().toString();
			if (value != null) {
				setNoMotivoNotificacionId((Integer) mapNoMotivoNotificacion
						.get(value));
				setCmbValueNoMotivoNotificacion(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void loadFormaNotificacionById(ValueChangeEvent event) {
		try {

			HtmlComboBox combo = (HtmlComboBox) event.getComponent();
			String value = combo.getValue().toString();
			Integer flagUbicacion;
			if (value.equals("Ubicables")) {
				flagUbicacion = 1;
			} else if (value.equals("Inubicables")) {
				flagUbicacion = 2;
			} else {
				flagUbicacion = 3;
			}
			List<NoMotivoNotificacion> lsNoMotivoNotificacion = controlycobranzaBo
					.getAlNoMotivoNotificacion(flagUbicacion);
			Iterator<NoMotivoNotificacion> it1 = lsNoMotivoNotificacion
					.iterator();
			lstNoMotivoNotificacion = new ArrayList<SelectItem>();
			while (it1.hasNext()) {
				NoMotivoNotificacion obj01 = it1.next();
				lstNoMotivoNotificacion.add(new SelectItem(obj01
						.getDescripcion(), String.valueOf(obj01
						.getMotivoNotificacionId())));
				mapNoMotivoNotificacion.put(obj01.getDescripcion().trim(),
						obj01.getMotivoNotificacionId());
			}
			// verFormasNotificacion()
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void loadNotificador(ValueChangeEvent event) {
		try {
			HtmlComboBox combo = (HtmlComboBox) event.getComponent();
			String value = combo.getValue().toString();
			if (value != null) {
				notificadorId = (Integer) mapNotificador.get(value);
				setCmbNotificador(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void notificarRequerimiento() throws Exception {
		try {
			if (validar()) {
				notificacion.setNotificadorId(notificadorId);
				notificacion.setMotivoNotificacionId(noMotivoNotificacionId);
				// ***set RequerimientoId****//
				if (detalleInspeccion.getInspeccionId() != null
						&& detalleInspeccion.getInspeccionId() > 0) {
					notificacion.setRequerimientoId(detalleInspeccion
							.getInspeccionId());
				}

				// notificacion.setNroCargo(findCcActoItem.getNroCargoNotificacion());//el
				// cargo se genera automaticamente
				notificacion.setFechaNotificacion(DateUtil
						.dateToSqlTimestamp(fechaNotificacion));
				notificacion.setEstado(Constante.ESTADO_ACTIVO);
			    controlycobranzaBo.create(notificacion);
				tipoAccion = null;
				fechaNotificacion = null;
		        controlycobranzaBo.actualizarRequerimientoVehicular(notificacion);
				notificacion = new RvFiscalizacionNotificacion();
				setInspeccion(ficalizacionBo.getAllInspeccionesVehicular(findCcLoteItem.getLoteId()));
				cmbCondicionAdministrado=null;
				codicionAdministrado=null;
				noMotivoNotificacionId = null;				
				cmbValueNoMotivoNotificacion = null;
				cmbNotificador=null;
				cmbHtmlNotificador=null;

			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void salir() {
		notificacion = new RvFiscalizacionNotificacion();
	}

	public boolean validar() throws Exception {
		try {
			NoNotificador not = controlycobranzaBo
					.findNoNotificador(notificadorId);
			if (not == null) {
				addErrorMessage(getMsg("no.errornotificador"));
				return false;
			}
			if (detalleInspeccion.getFechaEmision().compareTo(
					DateUtil.dateToSqlTimestamp(fechaNotificacion)) >= 0) {
				addErrorMessage(getMsg("no.errorfechanotificacion"));
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		return true;
	}
	
	public String listarDetalleOmisosVehicular(){
		try{
			getSessionMap().put("FindInspecHistorial", detalleInspeccion);
			}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		return sendRedirectPrincipal();
	}	
	
	public void mostrar() {
		try {
			listaAnio=new ArrayList<SelectItem>();
			lstCategoriaVehiculo= new ArrayList<SelectItem>();
			lstClaseVehiculo= new ArrayList<SelectItem>();
			
			//detalleInspeccion
			/* COMBOBOX:: Años de Afectación */
			List<RvOmisosDetalleVehicular> lstCcTipoRec = ficalizacionBo.getAllDetalleVerificacion(detalleInspeccion.getInspeccionId());
			Iterator<RvOmisosDetalleVehicular> it1 = lstCcTipoRec.iterator();
			listaAnio = new ArrayList<SelectItem>();
			while (it1.hasNext()) {
				RvOmisosDetalleVehicular obj = it1.next();
				listaAnio.add(new SelectItem(obj.getAnnoDeterminacion(),String.valueOf(obj.getVehicularOmisosDetalleId())));
				mapAnio.put(obj.getAnnoDeterminacion().toString(),obj.getVehicularOmisosDetalleId());
			}
			/* COMBOBOX:: Años de Afectación */
			
			/* COMBOBOX::Clase de vehículo */
			List<RvClaseVehiculo> lst = new ArrayList<RvClaseVehiculo>();
			lst = ficalizacionBo.getAllClaseVehiculos();
			Iterator<RvClaseVehiculo> it = lst.iterator();
			while (it.hasNext()) {
				RvClaseVehiculo obj = it.next();
				lstClaseVehiculo.add(new SelectItem(obj.getDescripcion()));
				mapRvClaseVehiculo.put(obj.getDescripcion(),
						obj.getClaseVehiculoId());
				mapIRvClaseVehiculo.put(obj.getClaseVehiculoId(),
						obj.getDescripcion());
			}
			
			/* COMBOBOX::Categoría de vehículo */
			List<RvCategoriaVehiculo> lst2 = new ArrayList<RvCategoriaVehiculo>();
			lst2 = ficalizacionBo.getAllCategoriaVehiculos();
			Iterator<RvCategoriaVehiculo> it2 = lst2.iterator();
			while (it2.hasNext()) {
				RvCategoriaVehiculo obj2 = it2.next();
				lstCategoriaVehiculo.add(new SelectItem(obj2.getDescripcion()));
				mapRvCategoriaVehiculo.put(obj2.getDescripcion(),
						obj2.getCategoriaVehiculoId());
				mapIRvCategoriaVehiculo.put(obj2.getCategoriaVehiculoId(),
						obj2.getDescripcion());

			}


		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void loadTipoRecById(ValueChangeEvent event) {
		try {
			HtmlComboBox combo = (HtmlComboBox) event.getComponent();
			String value = combo.getValue().toString();
			if (value != null) {
				reqDetId = (Integer) mapAnio.get(value);
				anioAf=Integer.parseInt(value);
				setAnioAf(anioAf);
				setCmbAnio(value);
				setEnablePanel(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public void changeCategoria(ActionEvent ev) {
		fillMarcaVehic();
		lstModelo = new ArrayList<SelectItem>();
		selectedModeloVehic = null;
	}

	private void fillMarcaVehic() {
		lstMarcaVehiculo = new ArrayList<SelectItem>();
		try {
			List<RvMarca> lst3 = new ArrayList<RvMarca>();
			int categId = -1;

			if (selectedCategoriaVehic != null) {
				categId = mapRvCategoriaVehiculo.get(selectedCategoriaVehic);
			}

			lst3 = ficalizacionBo.findRvMarca(categId);

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
			System.out.println("ERROR: " + ex);
		}
		selectedMarcaVehic = null;
	}
	
	public void changeMarca(ActionEvent ev) {
		fillModelo();
	}
	
	private void fillModelo() {
		lstModelo = new ArrayList<SelectItem>();

		Integer categId = mapRvCategoriaVehiculo.get(selectedCategoriaVehic);
		Integer marcaId = mapRvMarca.get(selectedMarcaVehic);

		try {
			if (categId != null && marcaId != null) {
				List<RvModeloVehiculo> lst = new ArrayList<RvModeloVehiculo>();
				lst = ficalizacionBo.getAllRvModeloVehiculo(marcaId, categId);
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
	
		
	public void generarImpuesto() throws Exception {
		try {
			DatosNecesariosDeterDTO datos = new DatosNecesariosDeterDTO();
					
					// marca_vehiculo_id
					int marcId = mapRvMarca.get(selectedMarcaVehic);
					datos.setMarcaVehicId(marcId);

		
					// categoria_vehiculo_id
					int categId = mapRvCategoriaVehiculo.get(selectedCategoriaVehic);
					datos.setCategVehicId(categId);

		
					// modelo_vehiculo_id
					RvModeloVehiculoPK modId = mapRvModelo.get(selectedModeloVehic);
					datos.setModeloVehicId(modId.getModeloVehiculoId());
					
					//Años: Afectación y Fabricación
					DatosNecesariosDeterDTO datosAnio=ficalizacionBo.getAllAnioFabricacion(detalleInspeccion.getInspeccionId(),anioAf );
					Integer aniof=datosAnio.getAnioFabric();
					datos.setAnioFabric(aniof);
					datos.setAnioAfec(anioAf);

					//Montos de Adquisición
					BigDecimal valor=new BigDecimal("0");
					datos.setValorAdquiSoles(valor);
					datos.setValorAdquiOtraMoneda(valor);
					
					//Porcentaje de Adquisición
					BigDecimal porcentaje=new BigDecimal("100");
					datos.setPorcentajePropiedad(porcentaje);

			DtTasaVehicular tv = ficalizacionBo.getTasaVehicular(datos.getAnioAfec());

			BigDecimal uit = ficalizacionBo.getUitAnio(datos.getAnioAfec());

			BigDecimal tasaAnual = tv.getTasaAnual().divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
			BigDecimal impuestoMin = tv.getPorcUitMin().divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
			impuestoMin = impuestoMin.multiply(uit);

			BigDecimal porcentajeExoner = null;

			// Si es null, utilizará el ajuste MEF
			BigDecimal valorMEF = ficalizacionBo.getValorMEF(datos.getCategVehicId(),
															 datos.getMarcaVehicId(),
															 datos.getModeloVehicId(),
															 datos.getAnioAfec(),
															 datos.getAnioFabric());

			ImpuestoVehicular iv = null;
			if (valorMEF == null) {
				iv = new ImpuestoVehicular(null, datos.getAnioAfec() - 1,
				ficalizacionBo.getMontoAnioMenorAntigDetalle
					(datos.getCategVehicId(),datos.getMarcaVehicId(),datos.getModeloVehicId(),datos.getAnioAfec()),
					datos.getAnioFabric(),datos.getValorAdquiSoles(),porcentajeExoner, tasaAnual, impuestoMin,
					datos.getPorcentajePropiedad());
				if(iv.getBaseImponible()==null){
					/**	 * Generamos impuesto y base en cero,luego cambiar el estado del requerimiento a 4. */
					setSinImpuesto(Boolean.TRUE);
					addErrorMessage((new StringBuffer()).append("No se puede calcular porque no existe valor ajustado.Verificar valor referencial para categoría ").append(datos.getCategVehicId())
							.append(", marca ").append(datos.getMarcaVehicId())
							.append(" y modelo ").append(datos.getModeloVehicId())
							.append(" para el vehículo en el año de afectacion ").append(datos.getAnioAfec())
							.append(" y año fabricacion ").append(datos.getAnioFabric()).toString());
					setEnablePanel(false);
				}
			} else {
				iv = new ImpuestoVehicular(valorMEF, 0, null,datos.getAnioFabric(), datos.getValorAdquiSoles(),
						porcentajeExoner, tasaAnual, impuestoMin,datos.getPorcentajePropiedad());
				if(iv.getBaseImponible()==null){
					setSinImpuesto(Boolean.TRUE);
					//throw new DeterminacionVehicularException("No se ha podido calcular el impuesto");
					addErrorMessage("No se ha podido calcular el impuesto");
					setEnablePanel(false);
				}
			}
			
			// Determinación
			RvOmisosDetalleVehicular dtr = new RvOmisosDetalleVehicular();
			if (getSinImpuesto()==true){
				BigDecimal monto = new BigDecimal("0.0"); 
				dtr.setRequerimientoId(detalleInspeccion.getInspeccionId());
				dtr.setAnnoDeterminacion(datos.getAnioAfec());
				dtr.setTasa(tasaAnual);
				dtr.setBaseImponible(monto);
				dtr.setBaseExonerada(monto);
				dtr.setBaseAfecta(monto);
				dtr.setImpuesto(monto);
				dtr.setMarcaId(datos.getMarcaVehicId());
				dtr.setCategoriaId(datos.getCategVehicId());
				dtr.setModeloId(datos.getModeloVehicId());
				dtr.setEstado(Constante.ESTADO_PENDIENTE_ACTUALIZACION);
				dtr.setUsuarioId(getSessionManaged().getUsuarioLogIn().getUsuarioId());
				dtr.setFechaRegistro(DateUtil.getCurrentDate());
				dtr.setTerminal(getSessionManaged().getTerminalLogIn());
				dtr.setVehicularOmisosDetalleId(reqDetId);
				//ficalizacionBo.actualizaEstadoRequerimiento(detalleInspeccion.getInspeccionId(), omisoId);
			}else{
				dtr.setRequerimientoId(detalleInspeccion.getInspeccionId());
				dtr.setAnnoDeterminacion(datos.getAnioAfec());
				dtr.setTasa(tasaAnual);
				dtr.setBaseImponible(iv.getBaseImponible());
				dtr.setBaseExonerada(iv.getBaseExonerada());
				dtr.setBaseAfecta(iv.getBaseAfecta());
				dtr.setImpuesto(iv.getImpuestoParcial());
				dtr.setMarcaId(datos.getMarcaVehicId());
				dtr.setCategoriaId(datos.getCategVehicId());
				dtr.setModeloId(datos.getModeloVehicId());
				dtr.setEstado(Constante.ESTADO_ACTIVO);
				dtr.setUsuarioId(getSessionManaged().getUsuarioLogIn().getUsuarioId());
				dtr.setFechaRegistro(DateUtil.getCurrentDate());
				dtr.setTerminal(getSessionManaged().getTerminalLogIn());
				dtr.setVehicularOmisosDetalleId(reqDetId);
			}

			ficalizacionBo.actualizarImpuestoVehicular(dtr);
			setDisableBtnGeneraDj(true);

		} 
		 catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void generarDj() throws Exception {
		try {
			//VERIFICAR SI VEHICULO Y REQ.YA SE ENCUENTRA COMO DJ
			Integer vehiculoF= 	ficalizacionBo.getVehiculoFiscalizado(detalleInspeccion.getInspeccionId());
			  if (vehiculoF==null){
				DatosNecesariosDeclaracionDTO datosDj=ficalizacionBo.getDatosNecesariosDeclaracionDetalle(reqDetId,detalleInspeccion.getInspeccionId(),anioAf);
				if (datosDj !=null){
						// Create objects
						RvVehiculo vehiculo = new RvVehiculo();
						RvDjvehicular djvehicular = new RvDjvehicular();
						
						//vehiculo y los similares con djvehicular
						//condicion_vehiculo_id
						int condVehicId = datosDj.getCondicionVehiculoId();
						vehiculo.setCondicionVehiculoId(condVehicId);
						
						//tipo_transmision_id
						int tipoTransmId = datosDj.getTipoTransmisionId();
						vehiculo.setTipoTransmisionId(tipoTransmId);
						djvehicular.setTipoTransmisionId(tipoTransmId);
						
						//tipo_traccion_id
						int tipoTracId = datosDj.getTipoTraccionId();
						vehiculo.setTipoTraccionId(tipoTracId);
						djvehicular.setTipoTraccionId(tipoTracId);
						
						//marca_vehiculo_id
						int marcId = datosDj.getMarcaVehicId();
						vehiculo.setMarcaVehiculoId(marcId);
						djvehicular.setMarcaVehiculoId(marcId);
						
						//categoria_vehiculo_id
						int categId = datosDj.getCategVehicId();
						vehiculo.setCategoriaVehiculoId(categId);
						djvehicular.setCategoriaVehiculoId(categId);
						
						//modelo_vehiculo_id
						int modId =datosDj.getModeloVehicId();
						vehiculo.setModeloVehiculoId(modId);
						djvehicular.setModeloVehiculoId(modId);
						
						//tipo_motor_id
						int tipoMotId = datosDj.getTipoMotorId();
						vehiculo.setTipoMotorId(tipoMotId);
						djvehicular.setTipoMotorId(tipoMotId);
						
						//placa
						vehiculo.setPlaca(datosDj.getPlaca());
					
						//num_motor
						vehiculo.setNumMotor(datosDj.getNumMotor());
						djvehicular.setNumMotor(datosDj.getNumMotor());
						
						//anno_fabricacion
						vehiculo.setAnnoFabricacion(datosDj.getAnioFabric());
						djvehicular.setAnnoFabricacion(datosDj.getAnioFabric());
						
						//fecha_ins_registros
						vehiculo.setFechaInsRegistros(DateUtil.dateToSqlTimestamp(datosDj.getFechaInsRegistros()));
						djvehicular.setFechaInsRegistros(DateUtil.dateToSqlTimestamp(datosDj.getFechaInsRegistros()));
						
						//num_cilindros
						vehiculo.setNumCilindros(datosDj.getNumCilindros());
						djvehicular.setNumCilindros(datosDj.getNumCilindros());
							
						//peso_bruto
						vehiculo.setPesoBruto(datosDj.getPesoBruto());
						djvehicular.setPesoBruto(datosDj.getPesoBruto());
						
						//tipo_carroceria_id
						int tipoCarrId = datosDj.getTipoCarroceriaId();
						vehiculo.setTipoCarroceriaId(tipoCarrId);
						djvehicular.setTipoCarroceriaId(tipoCarrId);
						
						//clase_vehiculo_id
						int claseVehiId = mapRvClaseVehiculo.get(selectedClaseVehic);
						vehiculo.setClaseVehiculoId(claseVehiId);
						djvehicular.setClaseVehiculoId(claseVehiId);

					//RESTANTE djvehicular 
						
						//tipo_moneda_id
						int tipoMonId = datosDj.getTipoMonedaId();
						djvehicular.setTipoMonedaId(tipoMonId);
						
						//tipo_propiedad_id
						int tipoPropId = datosDj.getTipoPropiedadId();
						djvehicular.setTipoPropiedadId(tipoPropId);
						
						//persona_id
						djvehicular.setPersonaId(datosDj.getPersonaId());
						
						//tipo_adquisicion_id
						int tpAdquiId = datosDj.getTipoAdquisicionId();
						djvehicular.setTipoAdquisicionId(tpAdquiId);
						
						//rv_motivo_declaracion_id
						int motivoDeclaId =datosDj.getMotivoDeclaracionId();
						djvehicular.setRvMotivoDeclaracionId(motivoDeclaId);
						
						//condicion_vehiculo_id
						djvehicular.setCondicionVehiculoId(condVehicId);
						
						//notaria_id					
						int notarId = datosDj.getNotariaId();
						djvehicular.setNotariaId(notarId);
										
						//fecha_declaracion
						djvehicular.setFechaDeclaracion(DateUtil.getCurrentDate());
				
						//anno_ini_afectacion
						djvehicular.setAnnoIniAfectacion(datosDj.getAnioIniAfec());
						//anno_fin_afectacion
						djvehicular.setAnnoFinAfectacion(datosDj.getAnioFinAfec());
						//anno_afectacion
						djvehicular.setAnnoAfectacion(datosDj.getAnioAfec());	
						
						//num_tarjeta_propiedad
						djvehicular.setNumTarjetaPropiedad(datosDj.getNumTarjetaPropiedad());
						
						//fecha_adquisicion
						djvehicular.setFechaAdquisicion(DateUtil.dateToSqlTimestamp(datosDj.getFechaAdquisicion()));
						
						//val_adq_soles
						djvehicular.setValAdqSoles(datosDj.getValorAdquiSoles());
						
						//porc_propiedad
						djvehicular.setPorcPropiedad(datosDj.getPorcentajePropiedad());
						
						//anno_declaracion
						djvehicular.setAnnoDeclaracion(anioDecla);
						
						//estado					
						djvehicular.setEstado(datosDj.getEstado());
				
						//glosa					
						djvehicular.setGlosa(datosDj.getGlosa());
				
						//guardar vehiculo
							vehiculo.setUsuarioId(getSessionManaged().getUsuarioLogIn().getUsuarioId());
							vehiculo.setFechaActualizacion(DateUtil.getCurrentDate());
							vehiculo.setFechaRegistro(DateUtil.getCurrentDate());
							vehiculo.setTerminal(getSessionManaged().getTerminalLogIn());
				
							setVehiculoId(ficalizacionBo.guardarVehiculo(vehiculo));
						
						//guardar dj
							//vehiculo_id
							djvehicular.setVehiculoId(vehiculoId);						
							djvehicular.setFlagDjAnno(Constante.FLAG_ACTIVO);							
							//GUARDAR DJ VEHICULAR y saca la djvehicularId
							djvehicular.setUsuarioId(getSessionManaged().getUsuarioLogIn().getUsuarioId());
							djvehicular.setFechaActualizacion(DateUtil.getCurrentDate());
							djvehicular.setFechaRegistro(DateUtil.getCurrentDate());
							djvehicular.setTerminal(getSessionManaged().getTerminalLogIn());
							djvehicular.setRequerimientoId(detalleInspeccion.getInspeccionId());
							djvehicular.setFiscalizado(Constante.FISCALIZADO_SI);
							djvehicular.setFiscaAceptada(Constante.FISCA_ACEPTADA_NO);
							djvehicular.setFiscaCerrada(Constante.FISCA_CERRADA_NO);
							RvDjvehicular djs=ficalizacionBo.guardarDJVehicular(djvehicular);
							setDjId(djs.getDjvehicularId());
						
						//guardar anexos
							// Anexos
							RvSustentoVehicular sv = new RvSustentoVehicular();
							RvSustentoVehicularPK svp = new RvSustentoVehicularPK();
							svp.setDjvehicularId(djs.getDjvehicularId());
							sv.setId(svp);
							sv.setFechaRegistro(DateUtil.dateToSqlTimestamp(new Date()));
							sv.setUsuarioId(getSessionManaged().getUsuarioLogIn().getUsuarioId());
							sv.setTerminal(getSessionManaged().getTerminalLogIn());
							sv.setDocSustentatorioId(Constante.DOCUMENTO_OTROS);
							sv.setNroDocumento("Generación Masiva-Cartera Vehicular");
							ficalizacionBo.guardarDocAnexosDjVehicular(sv);

					}
					else{
						addErrorMessage("No se encontró datos para crear la Declaración Jurada");
					}
				}else{
					DatosNecesariosDeclaracionDTO datosDj=ficalizacionBo.getDatosNecesariosDeclaracionDetalle(reqDetId,detalleInspeccion.getInspeccionId(),anioAf);
					if (datosDj !=null){
								// Create objects
								RvDjvehicular djvehicular = new RvDjvehicular();
								
								//condicion_vehiculo_id
								int condVehicId = datosDj.getCondicionVehiculoId();
								
								//tipo_transmision_id
								int tipoTransmId = datosDj.getTipoTransmisionId();
								djvehicular.setTipoTransmisionId(tipoTransmId);
								
								//tipo_traccion_id
								int tipoTracId = datosDj.getTipoTraccionId();
								djvehicular.setTipoTraccionId(tipoTracId);
								
								//marca_vehiculo_id
								int marcId = datosDj.getMarcaVehicId();
								djvehicular.setMarcaVehiculoId(marcId);
								
								//categoria_vehiculo_id
								int categId = datosDj.getCategVehicId();
								djvehicular.setCategoriaVehiculoId(categId);
								
								//modelo_vehiculo_id
								int modId =datosDj.getModeloVehicId();
								djvehicular.setModeloVehiculoId(modId);
								
								//tipo_motor_id
								int tipoMotId = datosDj.getTipoMotorId();
								djvehicular.setTipoMotorId(tipoMotId);
							
								//num_motor
								djvehicular.setNumMotor(datosDj.getNumMotor());
								
								//anno_fabricacion
								djvehicular.setAnnoFabricacion(datosDj.getAnioFabric());
								
								//fecha_ins_registros
								djvehicular.setFechaInsRegistros(DateUtil.dateToSqlTimestamp(datosDj.getFechaInsRegistros()));
								
								//num_cilindros
								djvehicular.setNumCilindros(datosDj.getNumCilindros());
									
								//peso_bruto
								djvehicular.setPesoBruto(datosDj.getPesoBruto());
								
								//tipo_carroceria_id
								int tipoCarrId = datosDj.getTipoCarroceriaId();
								djvehicular.setTipoCarroceriaId(tipoCarrId);
								
								//clase_vehiculo_id
								int claseVehiId = datosDj.getClaseVehiculoId();
								djvehicular.setClaseVehiculoId(claseVehiId);
								
							//RESTANTE djvehicular 
								
								//tipo_moneda_id
								int tipoMonId = datosDj.getTipoMonedaId();
								djvehicular.setTipoMonedaId(tipoMonId);
								
								//tipo_propiedad_id
								int tipoPropId = datosDj.getTipoPropiedadId();
								djvehicular.setTipoPropiedadId(tipoPropId);
								
								//persona_id
								djvehicular.setPersonaId(datosDj.getPersonaId());
								
								//tipo_adquisicion_id
								int tpAdquiId = datosDj.getTipoAdquisicionId();
								djvehicular.setTipoAdquisicionId(tpAdquiId);
								
								//rv_motivo_declaracion_id
								int motivoDeclaId =datosDj.getMotivoDeclaracionId();
								djvehicular.setRvMotivoDeclaracionId(motivoDeclaId);
								
								//condicion_vehiculo_id
								djvehicular.setCondicionVehiculoId(condVehicId);
								
								//notaria_id					
								int notarId = datosDj.getNotariaId();
								djvehicular.setNotariaId(notarId);
												
								//fecha_declaracion
								djvehicular.setFechaDeclaracion(DateUtil.getCurrentDate());
						
								//anno_ini_afectacion
								djvehicular.setAnnoIniAfectacion(datosDj.getAnioIniAfec());
								//anno_fin_afectacion
								djvehicular.setAnnoFinAfectacion(datosDj.getAnioFinAfec());
								//anno_afectacion
								djvehicular.setAnnoAfectacion(datosDj.getAnioAfec());	
								
								//num_tarjeta_propiedad
								djvehicular.setNumTarjetaPropiedad(datosDj.getNumTarjetaPropiedad());
								
								//fecha_adquisicion
								djvehicular.setFechaAdquisicion(DateUtil.dateToSqlTimestamp(datosDj.getFechaAdquisicion()));
								
								//val_adq_soles
								djvehicular.setValAdqSoles(datosDj.getValorAdquiSoles());
								
								//porc_propiedad
								djvehicular.setPorcPropiedad(datosDj.getPorcentajePropiedad());
								
								//anno_declaracion
								djvehicular.setAnnoDeclaracion(anioDecla);
								
								//estado					
								djvehicular.setEstado(datosDj.getEstado());
						
								//glosa					
								djvehicular.setGlosa(datosDj.getGlosa());
						
								
								//guardar dj
									//vehiculo_id
									djvehicular.setVehiculoId(vehiculoF);						
									djvehicular.setFlagDjAnno(Constante.FLAG_ACTIVO);							
									//GUARDAR DJ VEHICULAR y saca la djvehicularId
									djvehicular.setUsuarioId(getSessionManaged().getUsuarioLogIn().getUsuarioId());
									djvehicular.setFechaActualizacion(DateUtil.getCurrentDate());
									djvehicular.setFechaRegistro(DateUtil.getCurrentDate());
									djvehicular.setTerminal(getSessionManaged().getTerminalLogIn());
									djvehicular.setRequerimientoId(detalleInspeccion.getInspeccionId());
									djvehicular.setFiscalizado(Constante.FISCALIZADO_SI);
									djvehicular.setFiscaAceptada(Constante.FISCA_ACEPTADA_NO);
									djvehicular.setFiscaCerrada(Constante.FISCA_CERRADA_NO);
									RvDjvehicular djs=ficalizacionBo.guardarDJVehicular(djvehicular);
									setDjId(djs.getDjvehicularId());
								
								//guardar anexos
									// Anexos
									RvSustentoVehicular sv = new RvSustentoVehicular();
									RvSustentoVehicularPK svp = new RvSustentoVehicularPK();
									svp.setDjvehicularId(djs.getDjvehicularId());
									sv.setId(svp);
									sv.setFechaRegistro(DateUtil.dateToSqlTimestamp(new Date()));
									sv.setUsuarioId(getSessionManaged().getUsuarioLogIn().getUsuarioId());
									sv.setTerminal(getSessionManaged().getTerminalLogIn());
									sv.setDocSustentatorioId(Constante.DOCUMENTO_OTROS);
									sv.setNroDocumento("Generación Masiva-Cartera Vehicular");
									ficalizacionBo.guardarDocAnexosDjVehicular(sv);

							}
						else{
							addErrorMessage("No se encontró datos para crear la Declaración Jurada");
						}
				}
				//VERIFICANDO SI NO TIENE NINGUN DETALLE PENDENTE Y DJ ENTONCES ACTUALIZAMOS A 1 EL ESTADO DEL REQ. Y CARTERA:
			  List<RvOmisosDetalleVehicular> lstdetalle = ficalizacionBo.getAllDetalleVerificacion(detalleInspeccion.getInspeccionId());
			  if (lstdetalle.size()==0){
				  ficalizacionBo.actualizaEstadoRequerimiento(detalleInspeccion.getInspeccionId(), detalleInspeccion.getOmisoId(),Constante.ESTADO_ACTIVOS);
			  }
		}catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		
	}
	
	public List<FindInspeccionHistorial> getInspeccion() {
		return inspeccion;
	}

	public void setInspeccion(List<FindInspeccionHistorial> inspeccion) {
		this.inspeccion = inspeccion;
	}

	public FindCcLote getFindCcLoteItem() {
		return findCcLoteItem;
	}

	public void setFindCcLoteItem(FindCcLote findCcLoteItem) {
		this.findCcLoteItem = findCcLoteItem;
	}

	public Integer getLote_id() {
		return lote_id;
	}

	public void setLote_id(Integer lote_id) {
		this.lote_id = lote_id;
	}

	public FindInspeccionHistorial getDetalleInspeccion() {
		return detalleInspeccion;
	}

	public void setDetalleInspeccion(FindInspeccionHistorial detalleInspeccion) {
		this.detalleInspeccion = detalleInspeccion;
	}

	public String getCodicionAdministrado() {
		return codicionAdministrado;
	}

	public void setCodicionAdministrado(String codicionAdministrado) {
		this.codicionAdministrado = codicionAdministrado;
	}

	public HtmlComboBox getCmbCondicionAdministrado() {
		return cmbCondicionAdministrado;
	}

	public void setCmbCondicionAdministrado(
			HtmlComboBox cmbCondicionAdministrado) {
		this.cmbCondicionAdministrado = cmbCondicionAdministrado;
	}

	public HashMap<String, Integer> getMapNoMotivoNotificacion() {
		return mapNoMotivoNotificacion;
	}

	public void setMapNoMotivoNotificacion(
			HashMap<String, Integer> mapNoMotivoNotificacion) {
		this.mapNoMotivoNotificacion = mapNoMotivoNotificacion;
	}

	public String getCmbValueNoMotivoNotificacion() {
		return cmbValueNoMotivoNotificacion;
	}

	public void setCmbValueNoMotivoNotificacion(
			String cmbValueNoMotivoNotificacion) {
		this.cmbValueNoMotivoNotificacion = cmbValueNoMotivoNotificacion;
	}

	public Integer getNoMotivoNotificacionId() {
		return noMotivoNotificacionId;
	}

	public void setNoMotivoNotificacionId(Integer noMotivoNotificacionId) {
		this.noMotivoNotificacionId = noMotivoNotificacionId;
	}

	public HtmlComboBox getCmbNoMotivoNotificacion() {
		return cmbNoMotivoNotificacion;
	}

	public void setCmbNoMotivoNotificacion(HtmlComboBox cmbNoMotivoNotificacion) {
		this.cmbNoMotivoNotificacion = cmbNoMotivoNotificacion;
	}

	public List<SelectItem> getLstNoMotivoNotificacion() {
		return lstNoMotivoNotificacion;
	}

	public void setLstNoMotivoNotificacion(
			List<SelectItem> lstNoMotivoNotificacion) {
		this.lstNoMotivoNotificacion = lstNoMotivoNotificacion;
	}

	public Integer getNotificadorId() {
		return notificadorId;
	}

	public void setNotificadorId(Integer notificadorId) {
		this.notificadorId = notificadorId;
	}

	public List<SelectItem> getLstSelectItemsNotificador() {
		return lstSelectItemsNotificador;
	}

	public void setLstSelectItemsNotificador(
			List<SelectItem> lstSelectItemsNotificador) {
		this.lstSelectItemsNotificador = lstSelectItemsNotificador;
	}

	public HashMap<String, Integer> getMapNotificador() {
		return mapNotificador;
	}

	public void setMapNotificador(HashMap<String, Integer> mapNotificador) {
		this.mapNotificador = mapNotificador;
	}

	public String getCmbNotificador() {
		return cmbNotificador;
	}

	public void setCmbNotificador(String cmbNotificador) {
		this.cmbNotificador = cmbNotificador;
	}

	public HtmlComboBox getCmbHtmlNotificador() {
		return cmbHtmlNotificador;
	}

	public void setCmbHtmlNotificador(HtmlComboBox cmbHtmlNotificador) {
		this.cmbHtmlNotificador = cmbHtmlNotificador;
	}

	public java.util.Date getFechaNotificacion() {
		return fechaNotificacion;
	}

	public void setFechaNotificacion(java.util.Date fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}

	public Integer getTipoAccion() {
		return tipoAccion;
	}

	public void setTipoAccion(Integer tipoAccion) {
		this.tipoAccion = tipoAccion;
	}

	public List<SelectItem> getListaAnio() {
		return listaAnio;
	}

	public void setListaAnio(List<SelectItem> listaAnio) {
		this.listaAnio = listaAnio;
	}

	public HashMap<String, Integer> getMapAnio() {
		return mapAnio;
	}

	public void setMapAnio(HashMap<String, Integer> mapAnio) {
		this.mapAnio = mapAnio;
	}

	public String getCmbAnio() {
		return cmbAnio;
	}

	public void setCmbAnio(String cmbAnio) {
		this.cmbAnio = cmbAnio;
	}

	public HtmlComboBox getCmbxAnio() {
		return cmbxAnio;
	}

	public void setCmbxAnio(HtmlComboBox cmbxAnio) {
		this.cmbxAnio = cmbxAnio;
	}

	public Integer getAnioAf() {
		return anioAf;
	}

	public void setAnioAf(Integer anioAf) {
		this.anioAf = anioAf;
	}

	public String getSelectedClaseVehic() {
		return selectedClaseVehic;
	}

	public void setSelectedClaseVehic(String selectedClaseVehic) {
		this.selectedClaseVehic = selectedClaseVehic;
	}

	public List<SelectItem> getLstClaseVehiculo() {
		return lstClaseVehiculo;
	}

	public void setLstClaseVehiculo(List<SelectItem> lstClaseVehiculo) {
		this.lstClaseVehiculo = lstClaseVehiculo;
	}

	public String getSelectedCategoriaVehic() {
		return selectedCategoriaVehic;
	}

	public void setSelectedCategoriaVehic(String selectedCategoriaVehic) {
		this.selectedCategoriaVehic = selectedCategoriaVehic;
	}

	public List<SelectItem> getLstCategoriaVehiculo() {
		return lstCategoriaVehiculo;
	}

	public void setLstCategoriaVehiculo(List<SelectItem> lstCategoriaVehiculo) {
		this.lstCategoriaVehiculo = lstCategoriaVehiculo;
	}

	public List<SelectItem> getLstModelo() {
		return lstModelo;
	}

	public void setLstModelo(List<SelectItem> lstModelo) {
		this.lstModelo = lstModelo;
	}

	public String getSelectedModeloVehic() {
		return selectedModeloVehic;
	}

	public void setSelectedModeloVehic(String selectedModeloVehic) {
		this.selectedModeloVehic = selectedModeloVehic;
	}

	public List<SelectItem> getLstMarcaVehiculo() {
		return lstMarcaVehiculo;
	}

	public void setLstMarcaVehiculo(List<SelectItem> lstMarcaVehiculo) {
		this.lstMarcaVehiculo = lstMarcaVehiculo;
	}

	public HashMap<String, Integer> getMapRvCategoriaVehiculo() {
		return mapRvCategoriaVehiculo;
	}

	public void setMapRvCategoriaVehiculo(
			HashMap<String, Integer> mapRvCategoriaVehiculo) {
		this.mapRvCategoriaVehiculo = mapRvCategoriaVehiculo;
	}

	public HashMap<String, Integer> getMapRvMarca() {
		return mapRvMarca;
	}

	public void setMapRvMarca(HashMap<String, Integer> mapRvMarca) {
		this.mapRvMarca = mapRvMarca;
	}

	public HashMap<Integer, String> getMapIRvMarca() {
		return mapIRvMarca;
	}

	public void setMapIRvMarca(HashMap<Integer, String> mapIRvMarca) {
		this.mapIRvMarca = mapIRvMarca;
	}

	public String getSelectedMarcaVehic() {
		return selectedMarcaVehic;
	}

	public void setSelectedMarcaVehic(String selectedMarcaVehic) {
		this.selectedMarcaVehic = selectedMarcaVehic;
	}

	public HashMap<String, RvModeloVehiculoPK> getMapRvModelo() {
		return mapRvModelo;
	}

	public void setMapRvModelo(HashMap<String, RvModeloVehiculoPK> mapRvModelo) {
		this.mapRvModelo = mapRvModelo;
	}

	public HashMap<RvModeloVehiculoPK, String> getMapIRvModelo() {
		return mapIRvModelo;
	}

	public void setMapIRvModelo(HashMap<RvModeloVehiculoPK, String> mapIRvModelo) {
		this.mapIRvModelo = mapIRvModelo;
	}

	public HashMap<String, Integer> getMapRvClaseVehiculo() {
		return mapRvClaseVehiculo;
	}

	public void setMapRvClaseVehiculo(HashMap<String, Integer> mapRvClaseVehiculo) {
		this.mapRvClaseVehiculo = mapRvClaseVehiculo;
	}

	public HashMap<Integer, String> getMapIRvClaseVehiculo() {
		return mapIRvClaseVehiculo;
	}

	public void setMapIRvClaseVehiculo(HashMap<Integer, String> mapIRvClaseVehiculo) {
		this.mapIRvClaseVehiculo = mapIRvClaseVehiculo;
	}

	public HashMap<Integer, String> getMapIRvCategoriaVehiculo() {
		return mapIRvCategoriaVehiculo;
	}

	public void setMapIRvCategoriaVehiculo(
			HashMap<Integer, String> mapIRvCategoriaVehiculo) {
		this.mapIRvCategoriaVehiculo = mapIRvCategoriaVehiculo;
	}

	public Integer getAnioFab() {
		return anioFab;
	}

	public void setAnioFab(Integer anioFab) {
		this.anioFab = anioFab;
	}

	public Boolean getSinImpuesto() {
		return sinImpuesto;
	}

	public void setSinImpuesto(Boolean sinImpuesto) {
		this.sinImpuesto = sinImpuesto;
	}

	public Integer getReqDetId() {
		return reqDetId;
	}

	public void setReqDetId(Integer reqDetId) {
		this.reqDetId = reqDetId;
	}

	public boolean isDisableBtnGeneraDj() {
		return disableBtnGeneraDj;
	}

	public void setDisableBtnGeneraDj(boolean disableBtnGeneraDj) {
		this.disableBtnGeneraDj = disableBtnGeneraDj;
	}

	public String getAnioDecla() {
		return anioDecla;
	}

	public void setAnioDecla(String anioDecla) {
		this.anioDecla = anioDecla;
	}

	public Integer getVehiculoId() {
		return vehiculoId;
	}

	public void setVehiculoId(Integer vehiculoId) {
		this.vehiculoId = vehiculoId;
	}

	public Integer getDjId() {
		return djId;
	}

	public void setDjId(Integer djId) {
		this.djId = djId;
	}

	public boolean isEnablePanel() {
		return enablePanel;
	}

	public void setEnablePanel(boolean enablePanel) {
		this.enablePanel = enablePanel;
	}
}
