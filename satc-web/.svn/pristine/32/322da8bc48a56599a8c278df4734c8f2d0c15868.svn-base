package com.sat.sisat.papeletas.managed;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlInputHidden;

import com.sat.sisat.common.util.BaseManaged;
import com.sat.sisat.common.util.Constante;
import com.sat.sisat.common.util.DateUtil;
import com.sat.sisat.common.util.FacesUtil;
import com.sat.sisat.common.util.ValidateInput;
import com.sat.sisat.common.util.WebMessages;
import com.sat.sisat.exception.SisatException;
import com.sat.sisat.menus.business.MenuBoRemote;
import com.sat.sisat.menus.dto.SimpleMenuDTO;
import com.sat.sisat.papeleta.business.CalculoPapeleta;
import com.sat.sisat.papeleta.business.PapeletaBoRemote;
import com.sat.sisat.papeleta.dto.UbicacionFiscalDTO;
import com.sat.sisat.persistence.entity.MpTipoDocuIdentidad;
import com.sat.sisat.persistence.entity.PaDireccion;
import com.sat.sisat.persistence.entity.PaIncidencia;
import com.sat.sisat.persistence.entity.PaPapeleta;
import com.sat.sisat.persistence.entity.PaPersona;
import com.sat.sisat.predial.business.CalculoPredialBoRemote;
import com.sat.sisat.vehicular.cns.ReusoFormCns;
import com.sat.sisat.vehicular.dto.BuscarPersonaDTO;

@ManagedBean
@ViewScoped
public class RegistroDosajeEtilicoManaged extends BaseManaged {

	@EJB
	PapeletaBoRemote papeletaBo;
	
	@EJB
	CalculoPredialBoRemote calculoPredialBo;
	
	@EJB
	private MenuBoRemote menuBo;
	
	private BuscarPersonaDTO datosInfractor;
	
	private Integer papeletaId;
	
	private String nroPlaca;
	private String numeroLicencia;
	
	private String nroPapeleta;
	private java.util.Date fechaInfraccion;
	
	private PaIncidencia incidencia;
	private PaPapeleta papeleta;
	private PaPersona paPersona;
	private Map<Integer,String> mapITipoDocumento = new HashMap<Integer,String>();
	private BuscarPersonaDTO infractorOriginal = new BuscarPersonaDTO();
	
	private HtmlInputHidden txtNumeroLicencia = new HtmlInputHidden();
	
	// INICIO PERMISOS PARA EL MODULO -=CRAMIREZ=-
		private List<SimpleMenuDTO> listPermisosSubmenu = new ArrayList<SimpleMenuDTO>();
		private boolean permisoBuscar;
		private boolean permisoAgregarRegistrar;
		private boolean permisoModificarActualizar;
	// FIN PERMISOS PARA EL MODULO
			
	public RegistroDosajeEtilicoManaged(){
		
	}
	
	@PostConstruct
	public void init(){
		permisosMenu();
		try {
	        Integer papeletaId=(Integer)getSessionMap().get("papeletaId");
	        if(papeletaId!=null&&papeletaId!=Constante.RESULT_PENDING){
	        	//Registro por actualizar
	        	setPapeletaId(papeletaId);
	        	//cargar();
	        }else{
	        	//Nuevo registro
	        	papeleta=new PaPapeleta();
	        	setPapeletaId(Constante.RESULT_PENDING);
		        incidencia=new PaIncidencia();
		        incidencia.setIncidenciaId(Constante.RESULT_PENDING);
	        }
	        
	        List<MpTipoDocuIdentidad> lstTD = new ArrayList<MpTipoDocuIdentidad>();
			lstTD = papeletaBo.getAllMpTipoDocumento();
			Iterator<MpTipoDocuIdentidad> itTD = lstTD.iterator();
			while (itTD.hasNext()) {
				MpTipoDocuIdentidad objTD = itTD.next();
				mapITipoDocumento.put(objTD.getTipoDocIdentidadId(), objTD.getDescrpcionCorta());
			}
			
		}catch(Exception e ) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public void permisosMenu() {	
		try {
			int submenuId = Constante.REGISTRO_DOSAJE_ETILICO;
			
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
	
	public void limpiar(){
		setNroPapeleta("");
		setFechaInfraccion(null);
		papeletaId=Constante.RESULT_PENDING;
		setNroPlaca("");
		setNumeroLicencia("");
		setFechaInfraccion(null);
		
		getTxtNumeroLicencia().setValue(null);
		datosInfractor=new BuscarPersonaDTO();
	}
	
	public void validarUltimosDigitosPapeletas() throws Exception{
		if (nroPapeleta != null && !nroPapeleta.trim().isEmpty()) {
			if (nroPapeleta.equals("_______-__") || nroPapeleta.equals(null) || nroPapeleta.substring(8).equals("__")
					|| nroPapeleta.substring(8, 9).equals("_") || nroPapeleta.substring(9).equals("_")) {
				setNroPapeleta(null);
			} else {
				int valor = Integer.parseInt(String.valueOf(this.getNroPapeleta().substring(7)));
				Calendar c = new GregorianCalendar();
				int annio = Integer.parseInt(String.valueOf(c.get(Calendar.YEAR)).substring(2));
				if (valor > annio) {// aca los dos ultimos digitos del a�o en curso
					WebMessages.messageError("El número de papeleta no es valida; Verifique los datos del año");
				} else {
					PaPapeleta papeleta = papeletaBo.getPaPapeletaByNumeroPapeleta1(getNroPapeleta().trim(),
							getPapeletaId() == null ? Constante.RESULT_PENDING : getPapeletaId());
					if (papeleta != null) {
						WebMessages.messageError("El número de papeleta : " + papeleta.getNroPapeleta()
								+ " ya se encuentra registrado con fecha de infracción "
								+ papeleta.getFechaInfraccion());
					}
				}
			}
		}
	}
	
	public void setPersonaPapeleta(){
		String destinoRefresh = FacesUtil.getRequestParameter("destinoRefresh");
		
		BuscarPersonaPapeletasManaged buscarPersonaManaged=(BuscarPersonaPapeletasManaged)getManaged("buscarPersonaPapeletasManaged");
		buscarPersonaManaged.setPantallaUso(ReusoFormCns.DOSAJE_INFR_PAPELETAS);
		buscarPersonaManaged.setDestinoRefresh(destinoRefresh);
	}
	
	public void copiaPersona(BuscarPersonaDTO persona){		
		setDatosInfractor(persona);	
		
		infractorOriginal = new BuscarPersonaDTO();
		infractorOriginal=persona;
		
	}
	
	public void setDireccionDesdeMpPersona(Integer personaId,BuscarPersonaDTO persona){
		try{
			UbicacionFiscalDTO direccion=papeletaBo.getUbicacionFiscal(personaId);
			if(direccion!=null){
				persona.setDireccionCompleta(direccion.getDireccionCompleta());
				persona.setDireccionId(Constante.RESULT_PENDING);//Siempre se registra una direccion por papeleta
				persona.setTipoViaId(direccion.getTipoViaId());
				persona.setViaId(direccion.getViaId());
				persona.setLugarId(direccion.getLugarId());
				persona.setNumero(direccion.getNumero());
			}else{
				PaDireccion paDireccion=papeletaBo.getPaDireccion(personaId);
				if(paDireccion!=null){
					persona.setDireccionId(Constante.RESULT_PENDING);//Siempre se registra una direccion por papeleta
					persona.setTipoViaId(paDireccion.getTipoViaId());
					persona.setViaId(paDireccion.getViaId());
					persona.setLugarId(paDireccion.getLugarId());
					persona.setNumero(paDireccion.getNumero());
					persona.setDireccionCompleta(paDireccion.getDireccionCompleta());
				}	
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	 
	public void guardar() {
		try {
			if (validaDatosPapeleta()) {
				if (validaDatosInfractor()) {
					if (validaNumeroPapeleta()) {
//						papeleta=new PaPapeleta();
						if (getDatosInfractor() != null && getDatosInfractor().getNroDocuIdentidad() != null
								&& getDatosInfractor().getNroDocuIdentidad().trim().length() > 0) {
							PaPersona infractor = getPaPersona(getDatosInfractor());
							
							//Integer Id = papeletaBo.guardarPaPersona(infractor);
							Integer Id = papeletaBo.guardarPaPersona(infractor,infractorOriginal,getSessionManaged().getUsuarioLogIn().getTerminal(),getSessionManaged().getUsuarioLogIn().getUsuarioId());
							
							
							if (Id > Constante.RESULT_PENDING) {
								getDatosInfractor().setPersonaId(Id);
								papeleta.setPersonaInfractorId(Id);
							}
						}
						// Registra datos del propietario
						papeleta.setPersonaPropietarioId(null);
						// Guardar datos generales de la papeleta
						if (getPapeletaId() != null) {
							papeleta.setPapeletaId(getPapeletaId());
						} else {
							papeleta.setPapeletaId(Constante.RESULT_PENDING);
						}
						papeleta.setNroPapeleta(getNroPapeleta());

						if (getTxtNumeroLicencia() != null && getTxtNumeroLicencia().getValue() != null
								&& getTxtNumeroLicencia().getValue().toString().trim().length() > 0) {
							papeleta.setSinLicencia("N");
							papeleta.setNumLicencia(getTxtNumeroLicencia().getValue().toString());
						} else {
							papeleta.setSinLicencia("S");
							papeleta.setNumLicencia(null);
						}
						papeleta.setClaseLicenciaId(null);

						papeleta.setFechaInfraccion(DateUtil.dateToSqlTimestamp(getFechaInfraccion()));
						papeleta.setHoraInfraccion(null);

						papeleta.setMontoMulta(null);

						papeleta.setLeyId(1);
						papeleta.setInfraccionId(Constante.INFRACCION_DOSAJE_ETILICO);// Por defecto
																						// DE
						papeleta.setEstado(Constante.ESTADO_PAPELETA_DEFINITIVO);// 3 Pendiente de
																					// pago

						papeleta.setPlaca(getNroPlaca());
						papeleta.setNroTarjetaPropiedad(null);

						Integer papeletaId = papeletaBo.guardarPapeleta(papeleta);
						setPapeletaId(papeletaId);

						if (papeletaId > Constante.RESULT_PENDING) {
							papeleta.setPapeletaId(papeletaId);
							CalculoPapeleta calculo = new CalculoPapeleta(calculoPredialBo, papeletaBo);
							BigDecimal montoTotal = calculo.generarDeterminacionDE(papeleta,
									incidencia.getIncidenciaId());
						}

						incidencia = papeletaBo.getIncidencia(papeleta.getPersonaInfractorId(),
								papeleta.getInfraccionId(),
								papeleta.getPapeletaId());
						if (incidencia == null) {
							incidencia = new PaIncidencia();
							incidencia.setIncidenciaId(Constante.RESULT_PENDING);
						}
						limpiar();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebMessages.messageFatal(e);
		}
	}
	
	public PaPersona getPaPersona(BuscarPersonaDTO persona){
		PaPersona paPersona=new PaPersona();
		paPersona.setPersonaId(persona.getPersonaId());
		paPersona.setTipoDocumentoId(persona.getTipodocumentoIdentidadId());
		paPersona.setNroDocIdentidad(persona.getNroDocuIdentidad());
		paPersona.setPrimerNombre(persona.getPrimerNombre());
		paPersona.setSegundoNombre(persona.getSegundoNombre());
		paPersona.setApePaterno(persona.getApellidoPaterno());
		paPersona.setApeMaterno(persona.getApellidoMaterno());
		paPersona.setEstado(Constante.ESTADO_ACTIVO);
		return paPersona;
	}
	
	public PaDireccion getPaDireccion(BuscarPersonaDTO persona){
		PaDireccion paDireccion=new PaDireccion();
		paDireccion.setDireccionCompleta(persona.getDireccionCompleta());
		paDireccion.setDireccionId(persona.getDireccionId());
		paDireccion.setTipoViaId(persona.getTipoViaId());
		paDireccion.setViaId(persona.getViaId());
		//paDireccion.setLugarId(persona.getLugarId());
		paDireccion.setNumero(persona.getNumero());
		paDireccion.setEstado(Constante.ESTADO_ACTIVO);
		paDireccion.setPersonaId(persona.getPersonaId());
		return paDireccion;
	}
	
	public Boolean validaDatosInfractor(){
		if(!(getDatosInfractor()!=null&&
				getDatosInfractor().getPersonaId()!=null&&
				validaApellidosNombres(getDatosInfractor()))){
			WebMessages.messageError("Registre los datos del infractor");
			return Boolean.FALSE;
		}
		
		//No se requiere la validacion de direccion
		
		if(getDatosInfractor().getTipodocumentoIdentidadId()!=null&&
				getDatosInfractor().getTipodocumentoIdentidadId()>0&&
				getDatosInfractor().getNroDocuIdentidad()!=null&&
				!getDatosInfractor().getNroDocuIdentidad().isEmpty()&&
				getDatosInfractor().getNroDocuIdentidad().trim().length()>0){
			;
		}else{
			WebMessages.messageError("Registre el documento de identidad del infractor");
			return Boolean.FALSE;
		}
		
		return Boolean.TRUE;
	}
	
	
	public boolean validaApellidosNombres(BuscarPersonaDTO persona){
		if(persona.getPrimerNombre()!=null&&ValidateInput.validateFirstName(persona.getPrimerNombre().trim())&&
			persona.getApellidoPaterno()!=null&&ValidateInput.validateLastName(persona.getApellidoPaterno().trim())&&
			persona.getApellidoMaterno()!=null&&ValidateInput.validateLastName(persona.getApellidoMaterno().trim())){
			
			if(persona.getSegundoNombre()!=null&&persona.getSegundoNombre().trim().length()>0){
				if(ValidateInput.validateFirstName(persona.getSegundoNombre().trim())){
					return true;
				}else{
					return false;
				}
			}else{
				return true;
			}
			
		}else{
			return false;
		}
	}
	
	public Boolean validaDatosPapeleta(){
		Calendar now=Calendar.getInstance();
		Calendar fechaInfraccion=Calendar.getInstance();
		fechaInfraccion.setTime(getFechaInfraccion());
		if(DateUtil.diferenciaFechas(fechaInfraccion, now, 1)<0){
			WebMessages.messageError("La fecha de infraccion es invalida");
			return false;
		}
		return true;
	}	
	
	public Boolean validaNumeroPapeleta(){
		Boolean result=Boolean.FALSE;
		try{
			
			Calendar fechaInfraccion=Calendar.getInstance();
			fechaInfraccion.setTime(getFechaInfraccion());
			
			PaPapeleta papeleta =papeletaBo.getPaPapeletaByNumeroPapeleta(getNroPapeleta().trim(),getPapeletaId()==null?Constante.RESULT_PENDING:getPapeletaId(),fechaInfraccion.get(Calendar.YEAR));
			if(papeleta!=null){
				WebMessages.messageError("El numero de papeleta : "+papeleta.getNroPapeleta()+" ya se encuentra registrado con fecha de infraccion : "+DateUtil.convertDateToString(papeleta.getFechaInfraccion()));
				result=Boolean.FALSE;
			}else{
				result=Boolean.TRUE;
			}
		}catch(Exception e){
			result=Boolean.FALSE;
		}
		return result;
	}
	
	public void actualizarPersona(){
		String personaPapeleta = FacesUtil.getRequestParameter("personaPapeleta");
		if(personaPapeleta!=null&&personaPapeleta.equals("I")){
			ActualizaPersonasDosajeEtilicoManaged registro = (ActualizaPersonasDosajeEtilicoManaged) getManaged("actualizaPersonasDosajeEtilicoManaged");
			registro.setProperty(datosInfractor);
			registro.setActualizaPersona("S");
			getSessionMap().put("personaPapeleta",personaPapeleta);
		}
	}
	
	public void registrarPersona(){
		String personaPapeleta = FacesUtil.getRequestParameter("personaPapeleta");
		
		if(personaPapeleta!=null&&personaPapeleta.equals("I")){
			ActualizaPersonasDosajeEtilicoManaged registro = (ActualizaPersonasDosajeEtilicoManaged) getManaged("actualizaPersonasDosajeEtilicoManaged");
			registro.setProperty(new BuscarPersonaDTO());
			registro.setActualizaPersona("N");
			getSessionMap().put("personaPapeleta",personaPapeleta);
		}
	}
	
	public BuscarPersonaDTO getDatosInfractor() {
		return datosInfractor;
	}

	/**
	 * Desde ActualizarPersonaPapeletasManaged
	 * @param datosInfractor
	 */
	public void setDatosInfractor(BuscarPersonaDTO datosInfractor) {
		this.datosInfractor = datosInfractor;
	}

	public PapeletaBoRemote getPapeletaBo() {
		return papeletaBo;
	}

	public void setPapeletaBo(PapeletaBoRemote papeletaBo) {
		this.papeletaBo = papeletaBo;
	}

	public String getNroPapeleta() {
		return nroPapeleta;
	}

	public void setNroPapeleta(String nroPapeleta) {
		this.nroPapeleta = nroPapeleta;
	}

	public String getNroPlaca() {
		return nroPlaca;
	}

	public void setNroPlaca(String nroPlaca) {
		this.nroPlaca = nroPlaca;
	}

	public void setFechaInfraccion(Timestamp fechaInfraccion) {
		this.fechaInfraccion = fechaInfraccion;
	}

	public Integer getPapeletaId() {
		return papeletaId;
	}

	public void setPapeletaId(Integer papeletaId) {
		this.papeletaId = papeletaId;
	}
	
	public java.util.Date getFechaInfraccion() {
		return fechaInfraccion;
	}

	public void setFechaInfraccion(java.util.Date fechaInfraccion) {
		this.fechaInfraccion = fechaInfraccion;
	}
	
	public PaIncidencia getIncidencia() {
		return incidencia;
	}

	public void setIncidencia(PaIncidencia incidencia) {
		this.incidencia = incidencia;
	}
	
	public PaPapeleta getPapeleta() {
		return papeleta;
	}

	public void setPapeleta(PaPapeleta papeleta) {
		this.papeleta = papeleta;
	}

	public PaPersona getPaPersona() {
		return paPersona;
	}

	public void setPaPersona(PaPersona paPersona) {
		this.paPersona = paPersona;
	}

	public HtmlInputHidden getTxtNumeroLicencia() {
		return txtNumeroLicencia;
	}

	public void setTxtNumeroLicencia(HtmlInputHidden txtNumeroLicencia) {
		this.txtNumeroLicencia = txtNumeroLicencia;
	}
	
	public String getNumeroLicencia() {
		return numeroLicencia;
	}

	public void setNumeroLicencia(String numeroLicencia) {
		this.numeroLicencia = numeroLicencia;
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
