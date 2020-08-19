package com.sat.sisat.fiscalizacion.managed;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
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
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.richfaces.component.html.HtmlComboBox;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.SATWEBParameterFactory;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.controlycobranza.dto.FindCcLote;
import com.sat.sisat.controlycobranza.dto.FindCcLoteDetalleActo;
import com.sat.sisat.determinacion.exception.DeterminacionVehicularException;
import com.sat.sisat.determinacion.vehicular.calculo.BaseExonerada;
import com.sat.sisat.determinacion.vehicular.calculo.ImpuestoVehicular;
import com.sat.sisat.determinacion.vehicular.dao.DeterminacionVehicularDao;
import com.sat.sisat.determinacion.vehicular.dto.DatosDeterminacionValoresDTO;
import com.sat.sisat.determinacion.vehicular.dto.DatosExisteDeterminacionDTO;
import com.sat.sisat.determinacion.vehicular.dto.DatosInafecDTO;
import com.sat.sisat.determinacion.vehicular.dto.DatosNecesariosDeterDTO;
import com.sat.sisat.determinacion.vehicular.dto.DeudaPagosDTO;
import com.sat.sisat.determinacion.vehicular.dto.DeudaValoresDTO;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.fiscalizacion.business.FiscalizacionBo;
import com.sat.sisat.fiscalizacion.business.FiscalizacionBoRemote;
import com.sat.sisat.fiscalizacion.dto.DatosNecesariosDeclaracionDTO;
import com.sat.sisat.fiscalizacion.dto.FindInspeccionHistorial;
import com.sat.sisat.persistence.CrudServiceBean;
import com.sat.sisat.persistence.entity.CcLote;
import com.sat.sisat.persistence.entity.CdDeuda;
import com.sat.sisat.persistence.entity.CdDeudaHistorica;
import com.sat.sisat.persistence.entity.CdDeudaHistoricaPK;
import com.sat.sisat.persistence.entity.DtCuotaConcepto;
import com.sat.sisat.persistence.entity.DtDeterminacion;
import com.sat.sisat.persistence.entity.DtDeterminacionValor;
import com.sat.sisat.persistence.entity.DtDeterminacionVehicular;
import com.sat.sisat.persistence.entity.DtFechaVencimiento;
import com.sat.sisat.persistence.entity.DtTasaVehicular;
import com.sat.sisat.persistence.entity.RpDjpredial;
import com.sat.sisat.persistence.entity.RpFiscalizacionPrograma;
import com.sat.sisat.persistence.entity.RvCategoriaVehiculo;
import com.sat.sisat.persistence.entity.RvClaseVehiculo;
import com.sat.sisat.persistence.entity.RvDjvehicular;
import com.sat.sisat.persistence.entity.RvMarca;
import com.sat.sisat.persistence.entity.RvModeloVehiculoPK;
import com.sat.sisat.persistence.entity.RvOmisosDetalleVehicular;
import com.sat.sisat.persistence.entity.RvOmisosVehicular;
import com.sat.sisat.persistence.entity.RvSustentoVehicular;
import com.sat.sisat.persistence.entity.RvSustentoVehicularPK;
import com.sat.sisat.persistence.entity.RvTipoCarroceria;
import com.sat.sisat.persistence.entity.RvTipoMotor;
import com.sat.sisat.persistence.entity.RvTransferenciaPropiedad;
import com.sat.sisat.persistence.entity.RvVehiculo;
import com.sat.sisat.vehicular.cns.VehicularCns;
import com.sat.sisat.vehicular.dto.AnexosDeclaVehicDTO;

@ManagedBean
@ViewScoped
public class BusquedaDetalleLoteCarteraVehicularManaged extends BaseManaged {

	private static final long serialVersionUID = 1L;

	@EJB
	FiscalizacionBoRemote ficalizacionBo;

	// BUSQUEDA:
		private FindCcLote findCcLoteItem = new FindCcLote();
		private Integer lote_id;
		private List<RvOmisosVehicular> lstloteDetalle = null;
		private RvOmisosVehicular omisoItem=new RvOmisosVehicular();	
	//ComboBox:
		private String cmbTipoProgramaRequerimiento;
		private HtmlComboBox cmbxTipoProgramaRequerimiento;
		private Integer tipoProgramaRequerimientoId;
		private HashMap<String, Integer> mapTipoProgramaRequerimiento = new HashMap<String, Integer>();
		private List<SelectItem> listaTipoProgramaRequerimiento = new ArrayList<SelectItem>();
		
		//Combo Box Clase
				private String cmbClaseVehicular;
				private HtmlComboBox cmbClaseVehiculo;
				private Integer claseVehicularId;
				private HashMap<String, Integer> mapClaseVehicular = new HashMap<String, Integer>();
				private List<SelectItem> listaClaseVehiculos = new ArrayList<SelectItem>();
				
				//Combo Box Categoria de vehiculo
				private String cmbCategoriaVehicular;
				private HtmlComboBox cmbCategoriaVehiculo;
				private Integer categoriaVehicularId;
				private List<SelectItem> listaCategoriaVehiculos = new ArrayList<SelectItem>();
				private HashMap<String, Integer> mapCategoriaVehicular = new HashMap<String, Integer>();
				private HashMap<Integer, String> mapIRvCategoriaVehiculo = new HashMap<Integer, String>();
				private String selectedCategoriaVehic;
				
				//Combo Box Marca de vehiculo
				private String cmbMarcaVehicular;
				private HtmlComboBox cmbMarcaVehiculo;
				private Integer marcaVehicularId;
				private HashMap<String, Integer> mapMarcaVehicular = new HashMap<String, Integer>();
				private List<SelectItem> listaMarcaVehiculos = new ArrayList<SelectItem>();		
				
				//Combo Box Motor de vehiculo
				private String cmbMotorVehicular;
				private HtmlComboBox cmbMotorVehiculo;
				private Integer motorVehicularId;
				private HashMap<String, Integer> mapMotorVehicular = new HashMap<String, Integer>();
				private List<SelectItem> listaMotorVehiculos = new ArrayList<SelectItem>();
				
				//Combo Box Carroceria de vehiculo
				private String cmbCarroceriaVehicular;
				private HtmlComboBox cmbCarroceriaVehiculo;
				private Integer carroceriaVehicularId;
				private HashMap<String, Integer> mapCarroceriaVehicular = new HashMap<String, Integer>();
				private List<SelectItem> listaCarroceriaVehiculos = new ArrayList<SelectItem>();	
				
				//Combo Box Modelo de vehiculo
				private String cmbModeloVehicular;
				private HtmlComboBox cmbModeloVehiculo;
				private Integer modeloVehicularId;
				private HashMap<String, Integer> mapModeloVehicular = new HashMap<String, Integer>();
				private List<SelectItem> listaModeloVehiculos = new ArrayList<SelectItem>();	
		
	//Generación del Impuesto Vehicular:
		private Boolean sinImpuesto=false;
	//Generación de DJ:
		private String anioDecla = DateUtil.obtenerAnioActual();
		private Integer vehiculoId = null;
		private List<AnexosDeclaVehicDTO> lstAnexos = new ArrayList<AnexosDeclaVehicDTO>();
		private Integer djId;
		
		private Boolean conRegistro=false;

	public BusquedaDetalleLoteCarteraVehicularManaged() throws Exception {
	}

	@PostConstruct
	public void init() {
		try {
			FindCcLote findCcLoteItem = (FindCcLote) getSessionMap().get("findCcLoteItm");
			if (findCcLoteItem != null) {
				setFindCcLoteItem(findCcLoteItem);
				setLote_id(findCcLoteItem.getLoteId());
				mostrarRegistros();
			}
			
			/* COMBOBOX:: TIPO DE PROGRAMA */
			List<RpFiscalizacionPrograma> lstRpTipoPrograma = ficalizacionBo.getAllTipoProgramaVehicular();
			Iterator<RpFiscalizacionPrograma> it2 = lstRpTipoPrograma.iterator();
			listaTipoProgramaRequerimiento = new ArrayList<SelectItem>();

			while (it2.hasNext()) {
				RpFiscalizacionPrograma obj = it2.next();
				listaTipoProgramaRequerimiento.add(new SelectItem(obj.getNombrePrograma(), String.valueOf(obj.getProgramaId())));
				mapTipoProgramaRequerimiento.put(obj.getNombrePrograma().trim(),obj.getProgramaId());
			}
			/*- COMBOBOX:: TIPO DE PROGRAMA -*/
			
			/**
			 * nuevo**************************************************************************
			 */
			/* COMBOBOX:: CLASE DE VEHICULOS */
			List<RvClaseVehiculo> lstRpClaseVehiculo = ficalizacionBo.getAllClaseVehiculos();
			Iterator<RvClaseVehiculo> it3 = lstRpClaseVehiculo.iterator();
			listaClaseVehiculos = new ArrayList<SelectItem>();									
			while (it3.hasNext()) {
				RvClaseVehiculo obj = it3.next();
				listaClaseVehiculos.add(new SelectItem(obj.getDescripcion(), String.valueOf(obj.getClaseVehiculoId())));
				mapClaseVehicular.put(obj.getDescripcion().trim(),obj.getClaseVehiculoId());
			}			
						
			/* FIN COMBOBOX:: CLASE DE VEHICULOS */
			
			/* COMBOBOX:: CATEGORIA DE VEHICULOS */
			List<RvCategoriaVehiculo> lstRpCategoriaVehiculo = ficalizacionBo.getAllCategoriaVehiculos();
			Iterator<RvCategoriaVehiculo> it4 = lstRpCategoriaVehiculo.iterator();
			listaCategoriaVehiculos = new ArrayList<SelectItem>();									
			while (it4.hasNext()) {
				RvCategoriaVehiculo obj = it4.next();
				listaCategoriaVehiculos.add(new SelectItem(obj.getDescripcion(), String.valueOf(obj.getCategoriaVehiculoId())));
				mapCategoriaVehicular.put(obj.getDescripcion().trim(),obj.getCategoriaVehiculoId());
				mapIRvCategoriaVehiculo.put(obj.getCategoriaVehiculoId(), obj.getDescripcion());
			}	
												
			/* FIN COMBOBOX:: CATEGORIA DE VEHICULOS */	
			
			/* COMBOBOX:: MARCA DE VEHICULOS */
			List<RvMarca> lstRpMarcaVehiculo = ficalizacionBo.getAllMarcaVehiculos();
			Iterator<RvMarca> it5 = lstRpMarcaVehiculo.iterator();
			listaMarcaVehiculos = new ArrayList<SelectItem>();									
			while (it5.hasNext()) {
				RvMarca obj = it5.next();
				listaMarcaVehiculos.add(new SelectItem(obj.getDescripcion(), String.valueOf(obj.getMarcaVehiculoId())));
				mapMarcaVehicular.put(obj.getDescripcion().trim(),obj.getMarcaVehiculoId());
			}			
						
			/* FIN COMBOBOX:: MARCA DE VEHICULOS */			

			
			/* COMBOBOX:: MOTOR DE VEHICULOS */
			List<RvTipoMotor> lstRpMotorVehiculo = ficalizacionBo.getAllMotorVehiculos();
			Iterator<RvTipoMotor> it6 = lstRpMotorVehiculo.iterator();
			listaMotorVehiculos = new ArrayList<SelectItem>();									
			while (it6.hasNext()) {
				RvTipoMotor obj = it6.next();
				listaMotorVehiculos.add(new SelectItem(obj.getDescripcion(), String.valueOf(obj.getTipoMotorId())));
				mapMotorVehicular.put(obj.getDescripcion().trim(),obj.getTipoMotorId());
			}			
						
			/* FIN COMBOBOX:: MARCA DE VEHICULOS */	

			
			/* COMBOBOX:: CARROCERIA DE VEHICULOS */
			List<RvTipoCarroceria> lstRpCarroceriaVehiculo = ficalizacionBo.getAllCarroceriaVehiculos();
			Iterator<RvTipoCarroceria> it7 = lstRpCarroceriaVehiculo.iterator();
			listaCarroceriaVehiculos = new ArrayList<SelectItem>();									
			while (it7.hasNext()) {
				RvTipoCarroceria obj = it7.next();
				listaCarroceriaVehiculos.add(new SelectItem(obj.getDescripcion(), String.valueOf(obj.getTipoCarroceriaId())));
				mapCarroceriaVehicular.put(obj.getDescripcion().trim(),obj.getTipoCarroceriaId());
			}			
						
			/* FIN COMBOBOX:: CARROCERIA DE VEHICULOS */

			
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	public String descargar(){
		try{
			getSessionMap().put("omisoDetItm", omisoItem);
			}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		return sendRedirectPrincipal();
	}
	public String descargarOmiso(){
		try{			
			ficalizacionBo.decargarOmniso(omisoItem.getVehicularOmisosId(),omisoItem.getGlosa());			
		}catch(Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
		return sendRedirectPrincipal();
	}
	public void mostrarRegistros() throws Exception {
		if (findCcLoteItem != null) {
			lstloteDetalle = ficalizacionBo.getAllDetalleLotes(findCcLoteItem.getLoteId());
		}
	}

	public String salir() {
		getSessionMap().remove("findCcLoteItm");
		return sendRedirectPrincipal();
	}
	
	public void generar() throws Exception {
		ficalizacionBo.guardarRequerimientoVehicular(getLote_id(),tipoProgramaRequerimientoId);
		generarDetalle();
		setConRegistro(true);
	}
	
	public void generarDetalle() throws Exception{
		/** * Generamos impuesto vehicular...*/
			List<RvOmisosVehicular> lDjPredial=ficalizacionBo.getAllDetalleLotes2(getLote_id(),Constante.ESTADO_CON_INSPECCION);
			for(int i=0;i<lDjPredial.size();i++){
				  RvOmisosVehicular rvOmisos=lDjPredial.get(i);	
				  generarImpuesto(rvOmisos.getVehicularOmisosId(),rvOmisos.getLoteId(),rvOmisos.getCarroceriaId(),getSessionManaged().getUsuarioLogIn().getUsuarioId(), getSessionManaged().getTerminalLogIn());
					//System.out.println(rpDjPredial.getVehicularOmisosId());
		/** * Generamos dj vehicular...*/
				  generarDj(rvOmisos.getVehicularOmisosId(),rvOmisos.getLoteId(),rvOmisos.getCarroceriaId(),getSessionManaged().getUsuarioLogIn().getUsuarioId(), getSessionManaged().getTerminalLogIn());
			}
	}

	/*/*
	 * IMPUESTO VEHICULAR-INICIO
	 */
	
	public void generarImpuesto(int omisoId,int loteId,int requerimientoId,int usuarioId,String terminal) throws Exception {

		try {
	
			DatosNecesariosDeterDTO datos = ficalizacionBo.getDatosNecesariosDeterminar(omisoId,loteId);
					
		 for(int i=datos.getAnioAfec();i<=datos.getAnioFinAfec();i++){

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
				ficalizacionBo.getMontoAnioMenorAntig
					(datos.getCategVehicId(),datos.getMarcaVehicId(),datos.getModeloVehicId(),datos.getAnioAfec()),
					datos.getAnioFabric(),datos.getValorAdquiSoles(),porcentajeExoner, tasaAnual, impuestoMin,
					datos.getPorcentajePropiedad());
				
				if(iv.getBaseImponible()==null){
					/**	 * Generamos impuesto y base en cero,luego cambiar el estado del requerimiento a 4. */
					setSinImpuesto(Boolean.TRUE);
					//ficalizacionBo.actualizaEstadoRequerimiento(requerimientoId, omisoId);
				}
			} 
			else {
				iv = new ImpuestoVehicular(valorMEF, 0, null,datos.getAnioFabric(), datos.getValorAdquiSoles(),
						porcentajeExoner, tasaAnual, impuestoMin,datos.getPorcentajePropiedad());
				if(iv.getBaseImponible()==null){
					setSinImpuesto(Boolean.TRUE);
					//ficalizacionBo.actualizaEstadoRequerimiento(requerimientoId, omisoId);
				}
			}

			//BigDecimal montoCuota = iv.getImpuestoParcial().divide(new BigDecimal(String.valueOf(cuotas.getNroCuotas())), 2,RoundingMode.HALF_UP);

			// Determinación
			RvOmisosDetalleVehicular dtr = new RvOmisosDetalleVehicular();
			if (getSinImpuesto()==true){
				BigDecimal monto = new BigDecimal("0.0"); 
				dtr.setRequerimientoId(requerimientoId);
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
				dtr.setUsuarioId(usuarioId);
				dtr.setFechaRegistro(DateUtil.getCurrentDate());
				dtr.setTerminal(terminal);
				
				ficalizacionBo.actualizaEstadoRequerimiento(requerimientoId, omisoId,Constante.ESTADO_PENDIENTE_VERIFICACION);
			}else{
				dtr.setRequerimientoId(requerimientoId);
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
				dtr.setUsuarioId(usuarioId);
				dtr.setFechaRegistro(DateUtil.getCurrentDate());
				dtr.setTerminal(terminal);
			}

			ficalizacionBo.guardarImpuestoVehicular(dtr);

			Integer anioAfect=datos.getAnioAfec() +1;
			datos.setAnioAfec(anioAfect);
		}
		
		}catch (Exception e) {
				e.printStackTrace();
				WebMessages.messageFatal(e);
			}

	}

	/*/*
	 * IMPUESTO VEHICULAR-FIN
	 */
	
	/*/*
	 * DECLARACIÓN JURADA-INICIO
	 */
	public void generarDj(int omisoId,int loteId,int requerimientoId,int usuarioId,String terminal) throws Exception {
		try {
		DatosNecesariosDeclaracionDTO datosDj=ficalizacionBo.getDatosNecesariosDeclaracion(omisoId,loteId);
		if (datosDj !=null){
			Integer primeraDj=1;
		 for(int i=datosDj.getAnioAfec();i<=datosDj.getAnioFinAfec();i++){
		     if (primeraDj==1){
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
				int claseVehiId = datosDj.getClaseVehiculoId();
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
					vehiculo.setUsuarioId(usuarioId);
					vehiculo.setFechaActualizacion(DateUtil.getCurrentDate());
					vehiculo.setFechaRegistro(DateUtil.getCurrentDate());
					vehiculo.setTerminal(terminal);
		
					setVehiculoId(ficalizacionBo.guardarVehiculo(vehiculo));
				
				//guardar dj
					//vehiculo_id
					djvehicular.setVehiculoId(vehiculoId);						
					djvehicular.setFlagDjAnno(Constante.FLAG_ACTIVO);							
					//GUARDAR DJ VEHICULAR y saca la djvehicularId
					djvehicular.setUsuarioId(usuarioId);
					djvehicular.setFechaActualizacion(DateUtil.getCurrentDate());
					djvehicular.setFechaRegistro(DateUtil.getCurrentDate());
					djvehicular.setTerminal(terminal);
					djvehicular.setRequerimientoId(requerimientoId);
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
			    	 ficalizacionBo.copiarDjvAOtroAnio(getDjId(), datosDj.getAnioAfec(), datosDj.getPersonaId(),  getSessionManaged().getUsuarioLogIn().getUsuarioId(), getSessionManaged().getTerminalLogIn());
			     }
					Integer anioAfect=datosDj.getAnioAfec() +1;
					datosDj.setAnioAfec(anioAfect);
					primeraDj++;
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	/*/*
	 * DECLARACIÓN JURADA-FIN
	 */
	
	public void loadTipoProgramaRequerimiento(ValueChangeEvent event) {
		try {
			HtmlComboBox combo = (HtmlComboBox) event.getComponent();
			String value = combo.getValue().toString();
			if (value != null) {
				tipoProgramaRequerimientoId= (Integer) mapTipoProgramaRequerimiento.get(value);
				setCmbTipoProgramaRequerimiento(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void imprimirCarteraXLS() {
		java.sql.Connection connection=null;
		try {
			CrudServiceBean connj=CrudServiceBean.getInstance();
			connection=connj.connectJasper();
			    
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			
			Integer val = getLote_id();

			parameters.put("lote_id", val);
			
			JasperPrint jasperPrint = JasperFillManager.fillReport((SATWEBParameterFactory.getPathReporte()+"reporte_inspeccion_rv_cartera.jasper"),parameters,connection);
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			
			JRXlsExporter exporterXls = new JRXlsExporter();
			    exporterXls.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);  
			    exporterXls.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, true);  
			    exporterXls.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, true);  
			    exporterXls.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, false);  
			    exporterXls.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, true);  
			    exporterXls.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, true);  
			    exporterXls.setParameter(JRXlsExporterParameter.MAXIMUM_ROWS_PER_SHEET, 20000); 
			    exporterXls.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);  
			    exporterXls.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, System.currentTimeMillis() + "Cartera_Vehicular" + ".xls");  
			    exporterXls.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, output);  
			    exporterXls.exportReport();

			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			response.setContentType("application/vnd.ms-excel");  
		    response.setHeader("Content-Disposition", "attachment;filename=" + System.currentTimeMillis() + "Cartera_Vehicular.xls");  
			response.setContentLength(output.size());
			ServletOutputStream servletOutputStream = response.getOutputStream();
			servletOutputStream.write(output.toByteArray(), 0, output.size());
			servletOutputStream.flush();
			servletOutputStream.close();
			FacesContext.getCurrentInstance().responseComplete();
			
		} catch (Exception jre) {
			jre.printStackTrace();
		}finally{
	    	 try{
	    		 if(connection!=null){
	    			 connection.close();
	    			 connection=null;
	    		 }
	    	 }catch(Exception e){}
	     }
	}
	
	public void loadClaseVehiculos(ValueChangeEvent event) {
		try {
			HtmlComboBox combo = (HtmlComboBox) event.getComponent();
			String value = combo.getValue().toString();
			if (value != null) {
				claseVehicularId = (Integer) mapClaseVehicular.get(value);				
				setCmbClaseVehicular(value);				
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	public void loadCategoriaVehiculos(ValueChangeEvent event) {
		try {
			HtmlComboBox combo = (HtmlComboBox) event.getComponent();
			String value = combo.getValue().toString();
			if (value != null) {
				categoriaVehicularId = (Integer) mapCategoriaVehicular.get(value);				
				setCmbCategoriaVehicular(value);
				loadModeloVehiculos();
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}	
	public void loadModeloVehiculos(){
		try{
			
		}catch (Exception e){
			e.printStackTrace();
			WebMessages.messageFatal(e);			
		}
	}

	public void loadMarcaVehiculos(ValueChangeEvent event) {
		try {
			HtmlComboBox combo = (HtmlComboBox) event.getComponent();
			String value = combo.getValue().toString();
			if (value != null) {
				marcaVehicularId = (Integer) mapMarcaVehicular.get(value);				
				setCmbMarcaVehicular(value);				
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}

	public void loadMotorVehiculos(ValueChangeEvent event) {
		try {
			HtmlComboBox combo = (HtmlComboBox) event.getComponent();
			String value = combo.getValue().toString();
			if (value != null) {
				motorVehicularId = (Integer) mapMotorVehicular.get(value);				
				setCmbMotorVehicular(value);				
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}	
	public void loadCarroceriaVehiculos(ValueChangeEvent event) {
		try {
			HtmlComboBox combo = (HtmlComboBox) event.getComponent();
			String value = combo.getValue().toString();
			if (value != null) {
				carroceriaVehicularId = (Integer) mapCarroceriaVehicular.get(value);				
				setCmbCarroceriaVehicular(value);				
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
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

	public List<RvOmisosVehicular> getLstloteDetalle() {
		return lstloteDetalle;
	}

	public void setLstloteDetalle(List<RvOmisosVehicular> lstloteDetalle) {
		this.lstloteDetalle = lstloteDetalle;
	}

	public String getCmbTipoProgramaRequerimiento() {
		return cmbTipoProgramaRequerimiento;
	}

	public void setCmbTipoProgramaRequerimiento(String cmbTipoProgramaRequerimiento) {
		this.cmbTipoProgramaRequerimiento = cmbTipoProgramaRequerimiento;
	}

	public HtmlComboBox getCmbxTipoProgramaRequerimiento() {
		return cmbxTipoProgramaRequerimiento;
	}

	public void setCmbxTipoProgramaRequerimiento(
			HtmlComboBox cmbxTipoProgramaRequerimiento) {
		this.cmbxTipoProgramaRequerimiento = cmbxTipoProgramaRequerimiento;
	}

	public Integer getTipoProgramaRequerimientoId() {
		return tipoProgramaRequerimientoId;
	}

	public void setTipoProgramaRequerimientoId(Integer tipoProgramaRequerimientoId) {
		this.tipoProgramaRequerimientoId = tipoProgramaRequerimientoId;
	}

	public HashMap<String, Integer> getMapTipoProgramaRequerimiento() {
		return mapTipoProgramaRequerimiento;
	}

	public void setMapTipoProgramaRequerimiento(
			HashMap<String, Integer> mapTipoProgramaRequerimiento) {
		this.mapTipoProgramaRequerimiento = mapTipoProgramaRequerimiento;
	}

	public List<SelectItem> getListaTipoProgramaRequerimiento() {
		return listaTipoProgramaRequerimiento;
	}

	public void setListaTipoProgramaRequerimiento(
			List<SelectItem> listaTipoProgramaRequerimiento) {
		this.listaTipoProgramaRequerimiento = listaTipoProgramaRequerimiento;
	}
	
	public RvOmisosVehicular getOmisoItem() {
		return omisoItem;
	}

	public void setOmisoItem(RvOmisosVehicular omisoItem) {
		this.omisoItem = omisoItem;
	}

	public Integer getVehiculoId() {
		return vehiculoId;
	}

	public void setVehiculoId(Integer vehiculoId) {
		this.vehiculoId = vehiculoId;
	}

	public List<AnexosDeclaVehicDTO> getLstAnexos() {
		return lstAnexos;
	}

	public void setLstAnexos(List<AnexosDeclaVehicDTO> lstAnexos) {
		this.lstAnexos = lstAnexos;
	}

	public Boolean getSinImpuesto() {
		return sinImpuesto;
	}

	public void setSinImpuesto(Boolean sinImpuesto) {
		this.sinImpuesto = sinImpuesto;
	}

	public Boolean getConRegistro() {
		return conRegistro;
	}

	public void setConRegistro(Boolean conRegistro) {
		this.conRegistro = conRegistro;
	}

	public FiscalizacionBoRemote getFicalizacionBo() {
		return ficalizacionBo;
	}

	public void setFicalizacionBo(FiscalizacionBoRemote ficalizacionBo) {
		this.ficalizacionBo = ficalizacionBo;
	}

	public String getCmbClaseVehicular() {
		return cmbClaseVehicular;
	}

	public void setCmbClaseVehicular(String cmbClaseVehicular) {
		this.cmbClaseVehicular = cmbClaseVehicular;
	}

	public HtmlComboBox getCmbClaseVehiculo() {
		return cmbClaseVehiculo;
	}

	public void setCmbClaseVehiculo(HtmlComboBox cmbClaseVehiculo) {
		this.cmbClaseVehiculo = cmbClaseVehiculo;
	}

	public Integer getClaseVehicularId() {
		return claseVehicularId;
	}

	public void setClaseVehicularId(Integer claseVehicularId) {
		this.claseVehicularId = claseVehicularId;
	}

	public HashMap<String, Integer> getMapClaseVehicular() {
		return mapClaseVehicular;
	}

	public void setMapClaseVehicular(HashMap<String, Integer> mapClaseVehicular) {
		this.mapClaseVehicular = mapClaseVehicular;
	}

	public List<SelectItem> getListaClaseVehiculos() {
		return listaClaseVehiculos;
	}

	public void setListaClaseVehiculos(List<SelectItem> listaClaseVehiculos) {
		this.listaClaseVehiculos = listaClaseVehiculos;
	}

	public String getCmbCategoriaVehicular() {
		return cmbCategoriaVehicular;
	}

	public void setCmbCategoriaVehicular(String cmbCategoriaVehicular) {
		this.cmbCategoriaVehicular = cmbCategoriaVehicular;
	}

	public HtmlComboBox getCmbCategoriaVehiculo() {
		return cmbCategoriaVehiculo;
	}

	public void setCmbCategoriaVehiculo(HtmlComboBox cmbCategoriaVehiculo) {
		this.cmbCategoriaVehiculo = cmbCategoriaVehiculo;
	}

	public Integer getCategoriaVehicularId() {
		return categoriaVehicularId;
	}

	public void setCategoriaVehicularId(Integer categoriaVehicularId) {
		this.categoriaVehicularId = categoriaVehicularId;
	}

	public List<SelectItem> getListaCategoriaVehiculos() {
		return listaCategoriaVehiculos;
	}

	public void setListaCategoriaVehiculos(List<SelectItem> listaCategoriaVehiculos) {
		this.listaCategoriaVehiculos = listaCategoriaVehiculos;
	}

	public HashMap<String, Integer> getMapCategoriaVehicular() {
		return mapCategoriaVehicular;
	}

	public void setMapCategoriaVehicular(
			HashMap<String, Integer> mapCategoriaVehicular) {
		this.mapCategoriaVehicular = mapCategoriaVehicular;
	}

	public HashMap<Integer, String> getMapIRvCategoriaVehiculo() {
		return mapIRvCategoriaVehiculo;
	}

	public void setMapIRvCategoriaVehiculo(
			HashMap<Integer, String> mapIRvCategoriaVehiculo) {
		this.mapIRvCategoriaVehiculo = mapIRvCategoriaVehiculo;
	}

	public String getSelectedCategoriaVehic() {
		return selectedCategoriaVehic;
	}

	public void setSelectedCategoriaVehic(String selectedCategoriaVehic) {
		this.selectedCategoriaVehic = selectedCategoriaVehic;
	}

	public String getCmbMarcaVehicular() {
		return cmbMarcaVehicular;
	}

	public void setCmbMarcaVehicular(String cmbMarcaVehicular) {
		this.cmbMarcaVehicular = cmbMarcaVehicular;
	}

	public HtmlComboBox getCmbMarcaVehiculo() {
		return cmbMarcaVehiculo;
	}

	public void setCmbMarcaVehiculo(HtmlComboBox cmbMarcaVehiculo) {
		this.cmbMarcaVehiculo = cmbMarcaVehiculo;
	}

	public Integer getMarcaVehicularId() {
		return marcaVehicularId;
	}

	public void setMarcaVehicularId(Integer marcaVehicularId) {
		this.marcaVehicularId = marcaVehicularId;
	}

	public HashMap<String, Integer> getMapMarcaVehicular() {
		return mapMarcaVehicular;
	}

	public void setMapMarcaVehicular(HashMap<String, Integer> mapMarcaVehicular) {
		this.mapMarcaVehicular = mapMarcaVehicular;
	}

	public List<SelectItem> getListaMarcaVehiculos() {
		return listaMarcaVehiculos;
	}

	public void setListaMarcaVehiculos(List<SelectItem> listaMarcaVehiculos) {
		this.listaMarcaVehiculos = listaMarcaVehiculos;
	}

	public String getCmbMotorVehicular() {
		return cmbMotorVehicular;
	}

	public void setCmbMotorVehicular(String cmbMotorVehicular) {
		this.cmbMotorVehicular = cmbMotorVehicular;
	}

	public HtmlComboBox getCmbMotorVehiculo() {
		return cmbMotorVehiculo;
	}

	public void setCmbMotorVehiculo(HtmlComboBox cmbMotorVehiculo) {
		this.cmbMotorVehiculo = cmbMotorVehiculo;
	}

	public Integer getMotorVehicularId() {
		return motorVehicularId;
	}

	public void setMotorVehicularId(Integer motorVehicularId) {
		this.motorVehicularId = motorVehicularId;
	}

	public HashMap<String, Integer> getMapMotorVehicular() {
		return mapMotorVehicular;
	}

	public void setMapMotorVehicular(HashMap<String, Integer> mapMotorVehicular) {
		this.mapMotorVehicular = mapMotorVehicular;
	}

	public List<SelectItem> getListaMotorVehiculos() {
		return listaMotorVehiculos;
	}

	public void setListaMotorVehiculos(List<SelectItem> listaMotorVehiculos) {
		this.listaMotorVehiculos = listaMotorVehiculos;
	}

	public String getCmbCarroceriaVehicular() {
		return cmbCarroceriaVehicular;
	}

	public void setCmbCarroceriaVehicular(String cmbCarroceriaVehicular) {
		this.cmbCarroceriaVehicular = cmbCarroceriaVehicular;
	}

	public HtmlComboBox getCmbCarroceriaVehiculo() {
		return cmbCarroceriaVehiculo;
	}

	public void setCmbCarroceriaVehiculo(HtmlComboBox cmbCarroceriaVehiculo) {
		this.cmbCarroceriaVehiculo = cmbCarroceriaVehiculo;
	}

	public Integer getCarroceriaVehicularId() {
		return carroceriaVehicularId;
	}

	public void setCarroceriaVehicularId(Integer carroceriaVehicularId) {
		this.carroceriaVehicularId = carroceriaVehicularId;
	}

	public HashMap<String, Integer> getMapCarroceriaVehicular() {
		return mapCarroceriaVehicular;
	}

	public void setMapCarroceriaVehicular(
			HashMap<String, Integer> mapCarroceriaVehicular) {
		this.mapCarroceriaVehicular = mapCarroceriaVehicular;
	}

	public List<SelectItem> getListaCarroceriaVehiculos() {
		return listaCarroceriaVehiculos;
	}

	public void setListaCarroceriaVehiculos(
			List<SelectItem> listaCarroceriaVehiculos) {
		this.listaCarroceriaVehiculos = listaCarroceriaVehiculos;
	}

	public String getCmbModeloVehicular() {
		return cmbModeloVehicular;
	}

	public void setCmbModeloVehicular(String cmbModeloVehicular) {
		this.cmbModeloVehicular = cmbModeloVehicular;
	}

	public HtmlComboBox getCmbModeloVehiculo() {
		return cmbModeloVehiculo;
	}

	public void setCmbModeloVehiculo(HtmlComboBox cmbModeloVehiculo) {
		this.cmbModeloVehiculo = cmbModeloVehiculo;
	}

	public Integer getModeloVehicularId() {
		return modeloVehicularId;
	}

	public void setModeloVehicularId(Integer modeloVehicularId) {
		this.modeloVehicularId = modeloVehicularId;
	}

	public HashMap<String, Integer> getMapModeloVehicular() {
		return mapModeloVehicular;
	}

	public void setMapModeloVehicular(HashMap<String, Integer> mapModeloVehicular) {
		this.mapModeloVehicular = mapModeloVehicular;
	}

	public List<SelectItem> getListaModeloVehiculos() {
		return listaModeloVehiculos;
	}

	public void setListaModeloVehiculos(List<SelectItem> listaModeloVehiculos) {
		this.listaModeloVehiculos = listaModeloVehiculos;
	}

	public Integer getDjId() {
		return djId;
	}

	public void setDjId(Integer djId) {
		this.djId = djId;
	}
}
